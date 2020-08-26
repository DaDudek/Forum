package forum.dao;

import forum.model.Post;
import forum.model.User;
import forum.model.Vote;
import forum.util.ConnectionProvider;
import org.springframework.dao.EmptyResultDataAccessException;
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

public class VoteDAOMysql implements VoteDAO{
    private static final String CREATE = "INSERT INTO vote(post_id, user_id, date, is_positive) "
            + "VALUES (:post_id, :user_id, :date, :is_positive);";

    private static final String READ = "SELECT vote_id, vote.post_id, vote.user_id, vote.date, is_positive, " +
            "post.post_id, post.user_id, title, description, message, post.date, positive_vote, negative_vote, " +
            "user.user_id, username, email, account_active, password " +
            "FROM vote INNER JOIN post ON vote.post_id = post.post_id" +
            "INNER JOIN user ON vote.user_id = user.user_id";

    private static final String READ_VOTE_BY_IDS = "SELECT vote_id, vote.post_id, vote.user_id, vote.date, is_positive, "+
            "post.post_id, post.user_id, title, description, message, post.date, positive_vote, negative_vote, " +
            "user.user_id, username, email, account_active, password " +
            "FROM vote INNER JOIN post ON vote.post_id = post.post_id " +
            "INNER JOIN user ON vote.user_id = user.user_id " +
            "WHERE vote.user_id = :user_id AND vote.post_id = :post_id " ;

    private static final String UPDATE = "UPDATE vote SET date=:date, is_positive=:is_positive WHERE vote_id=:vote_id;";

    private NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());


    @Override
    public Vote create(Vote newObject) {
        System.out.println(newObject);
        Vote vote = new Vote(newObject);
        Map<String, Object> map = new HashMap<>();
        map.put("post_id", vote.getPost().getPostId());
        map.put("user_id",vote.getUser().getUserId());
        map.put("date",vote.getDate());
        map.put("is_positive", vote.isPositive());
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource(map);
        if (template.update(CREATE,parameterSource,holder) == 1){
            vote.setVoteId(holder.getKey().intValue());
        }
        return vote;
    }

    @Override
    public Vote read(Integer primaryKey) {
        Map<String, Object> map = new HashMap<>();
        map.put("vote_id",primaryKey);
        SqlParameterSource parameterSource = new MapSqlParameterSource(map);
        Vote vote = template.queryForObject(READ,parameterSource, new VoteRowMapper());
        return vote;
    }

    @Override
    public boolean update(Vote updateObject) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("date",updateObject.getDate());
        paramMap.put("vote_id",updateObject.getVoteId());
        paramMap.put("is_positive", updateObject.isPositive());
        SqlParameterSource parameterSource = new MapSqlParameterSource(paramMap);
        if (template.update(UPDATE,parameterSource) == 1){
            return true;
        }
        return false;

    }

    @Override
    public boolean delete(Integer key) {
        return false;
    }

    public Vote getVoteByIds(int postId, int userId){
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        map.put("post_id", postId);
        SqlParameterSource parameterSource = new MapSqlParameterSource(map);
        try{
            return template.queryForObject(READ_VOTE_BY_IDS,parameterSource,new VoteRowMapper());
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }


    private class VoteRowMapper implements RowMapper<Vote>{

        @Override
        public Vote mapRow(ResultSet resultSet, int i) throws SQLException {
            Vote vote = new Vote();
            vote.setVoteId(resultSet.getInt("vote_id"));
            vote.setDate(resultSet.getTimestamp("date"));
            vote.setPositive(resultSet.getBoolean("is_positive"));
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
            vote.setUser(user);
            vote.setPost(post);
            return vote;
        }
    }

}
