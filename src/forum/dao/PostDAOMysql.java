package forum.dao;

import forum.model.Post;
import forum.model.PostSort;
import forum.model.User;
import forum.model.Vote;
import forum.util.ConnectionProvider;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostDAOMysql implements PostDAO{
    private static final String CREATE =
            "INSERT INTO post(user_id, title, description, message, date, positive_vote, negative_vote) " +
                    "VALUES(:user_id, :title, :description, :message, :date, :positive_vote, :negative_vote)";

    private static final String READ_ALL_BY_VOTE =
            "SELECT post_id, post.user_id, title, description, message, date, positive_vote, negative_vote, " +
                    "username, email, account_active, password FROM post INNER JOIN user ON post.user_id = user.user_id " +
                    "ORDER BY (positive_vote - negative_vote)";

    private static final String READ_ALL_BY_DATE =
            "SELECT post_id, post.user_id, title, description, message, date, positive_vote, negative_vote, " +
                    "username, email, account_active, password FROM post INNER JOIN user ON post.user_id = user.user_id " +
                    "ORDER BY date";

    private static final String UPDATE =
            "UPDATE post SET user_id =:user_id title = :title, description = :description , message = :message , date = :date, " +
                    "positive_vote = :positive_vote, negative_vote = :negative_vote WHERE post_id = :post_id";

    private static final String READ =
            "SELECT post_id, post.user_id, title, description, message, date, positive_vote, negative_vote, " +
                    "username, email, account_active, password FROM post INNER JOIN user ON post.user_id = user.user_id "
                    + "WHERE post_id = :post_id";


    private NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());


    @Override
    public Post create(Post newObject) {
        Post post = new Post(newObject);
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource(mapPost(post));
        if (template.update(CREATE,parameterSource,holder) == 1){
            post.setPostId(holder.getKey().intValue());
        }
        return post;
    }

    @Override
    public Post read(Integer primaryKey) {

        Map<String, Object> map = new HashMap<>();
        map.put("post_id",primaryKey);
        SqlParameterSource parameterSource = new MapSqlParameterSource(map);
        Post post = template.queryForObject(READ,parameterSource, new PostRowMapper());
        return post;
    }

    @Override
    public boolean update(Post updateObject) {
        Map<String, Object> map = mapPost(updateObject);
        SqlParameterSource parameterSource = new MapSqlParameterSource(map);
        if (template.update(UPDATE, parameterSource) == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer key) {
        return false;
    }

    @Override
    public List<Post> getAll(PostSort postSort)
    {
        switch (postSort) {
            case ORDER_BY_DATE:
                return template.query(READ_ALL_BY_DATE, new PostRowMapper());
            case ORDER_BY_VOTE:
                return template.query(READ_ALL_BY_VOTE, new PostRowMapper());
            default:
                return null;
        }
    }

    private Map<String, Object> mapPost(Post post){
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", post.getUser().getUserId());
        map.put("title", post.getTitle());
        map.put("description",post.getDescription());
        map.put("message", post.getMessage());
        map.put("date", post.getDate());
        map.put("positive_vote", post.getPositiveVote());
        map.put("negative_vote", post.getNegativeVote());
        return map;
    }

    private class PostRowMapper implements RowMapper<Post>{

        @Override
        public Post mapRow(ResultSet resultSet, int i) throws SQLException {
            Post post = new Post();
            post.setPostId(resultSet.getInt("post_id"));
            post.setTitle(resultSet.getString("title"));
            post.setMessage(resultSet.getString("message"));
            post.setDate(resultSet.getTimestamp("date"));
            post.setPositiveVote(resultSet.getInt("positive_vote"));
            post.setNegativeVote(resultSet.getInt("negative_vote"));
            User user = new User();
            user.setUserId(resultSet.getInt("user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            user.setAccountActive(resultSet.getBoolean("account_active"));
            user.setPassword(resultSet.getString("password"));
            post.setUser(user);
            return post;
        }
    }
}
