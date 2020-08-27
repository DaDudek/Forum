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

    private static final String READ_ALL_AND_SORT_BY_VOTE =
            "SELECT post_id, post.user_id, title, description, message, date, positive_vote, negative_vote, " +
                    "username, email, account_active, password FROM post INNER JOIN user ON post.user_id = user.user_id " +
                    "ORDER BY (positive_vote - negative_vote) DESC"; // the best score post will be first;

    private static final String READ_ALL_AND_SORT_BY_NEWEST =
            "SELECT post_id, post.user_id, title, description, message, date, positive_vote, negative_vote, " +
                    "username, email, account_active, password FROM post INNER JOIN user ON post.user_id = user.user_id " +
                    "ORDER BY date DESC"; //the newest post will be first

    private static final String READ_ALL_AND_SORT_BY_OLDEST =
            "SELECT post_id, post.user_id, title, description, message, date, positive_vote, negative_vote, " +
                    "username, email, account_active, password FROM post INNER JOIN user ON post.user_id = user.user_id " +
                    "ORDER BY date "; //the oldest post will be first

    private static final String READ_BY_KEYWORDS_AND_SORT_BY_VOTE =
            "SELECT post_id, post.user_id, title, description, message, date, positive_vote, negative_vote, " +
                    "username, email, account_active, password FROM post INNER JOIN user ON post.user_id = user.user_id " +
                    "WHERE title COLLATE UTF8_GENERAL_CI LIKE :keywords OR  description COLLATE UTF8_GENERAL_CI LIKE :keywords "+
                    "ORDER BY (positive_vote - negative_vote) DESC"; // the best score post will be first also ignore character case;

    private static final String READ_BY_KEYWORDS_AND_SORT_BY_NEWEST =
            "SELECT post_id, post.user_id, title, description, message, date, positive_vote, negative_vote, " +
                    "username, email, account_active, password FROM post INNER JOIN user ON post.user_id = user.user_id " +
                    "WHERE title COLLATE UTF8_GENERAL_CI LIKE :keywords OR  description COLLATE UTF8_GENERAL_CI LIKE :keywords "+
                    "ORDER BY date DESC"; //the newest post will be first

    private static final String READ_BY_KEYWORDS_AND_SORT_BY_OLDEST =
            "SELECT post_id, post.user_id, title, description, message, date, positive_vote, negative_vote, " +
                    "username, email, account_active, password FROM post INNER JOIN user ON post.user_id = user.user_id " +
                    "WHERE title COLLATE UTF8_GENERAL_CI LIKE :keywords OR  description COLLATE UTF8_GENERAL_CI LIKE :keywords "+
                    "ORDER BY date "; //the oldest post will be first

    private static final String UPDATE =
            "UPDATE post SET user_id =:user_id, title = :title, description = :description , message = :message , date = :date, " +
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
            case ORDER_BY_OLDEST:
                return template.query(READ_ALL_AND_SORT_BY_OLDEST, new PostRowMapper());
            case ORDER_BY_NEWEST:
                return template.query(READ_ALL_AND_SORT_BY_NEWEST, new PostRowMapper());
            case ORDER_BY_BEST_VOTE:
                return template.query(READ_ALL_AND_SORT_BY_VOTE, new PostRowMapper());
            default:
                return null;
        }
    }

    @Override
    public List<Post> getByKeywords(String keywords, PostSort postSort) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("keywords","%"+keywords+"%");
        SqlParameterSource parameterSource = new MapSqlParameterSource(paramMap);
        switch (postSort){
            case ORDER_BY_NEWEST:
                return template.query(READ_BY_KEYWORDS_AND_SORT_BY_NEWEST,parameterSource,new PostRowMapper());
            case ORDER_BY_BEST_VOTE:
                return template.query(READ_BY_KEYWORDS_AND_SORT_BY_VOTE,parameterSource,new PostRowMapper());
            case ORDER_BY_OLDEST:
                return template.query(READ_BY_KEYWORDS_AND_SORT_BY_OLDEST,parameterSource,new PostRowMapper());
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
        map.put("post_id", post.getPostId());
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
            post.setDescription(resultSet.getString("description"));
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
