package forum.dao;

import forum.model.Vote;
import forum.model.VoteType;
import forum.util.ConnectionProvider;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
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
    private static final String CREATE = "INSERT INTO vote(post_id, user_id, date, is_positive, vote_type) "
            + "VALUES (:post_id, :user_id, :date, :is_positive, :vote_type);";

    private static final String READ= "SELECT vote_id, post_id, user_id,date, is_positive, vote_type FROM vote WHERE vote_id = :vode_id";

    private static final String READ_VOTE_BY_IDS = "SELECT vote_id, post_id, user_id,date, is_positive, vote_type " +
            "FROM vote WHERE user_id = :user_id AND post_id = :post_id " ;

    private static final String UPDATE = "UPDATE vote SET date=:date, is_positive=:is_positive, vote_type=:vote_type WHERE vote_id=:vote_id;";

    private static final String DELETE_POST_ALL_VOTES = "DELETE FROM vote WHERE post_id = :post_id";

    private static final String GET_USER_POST_VOTE_TYPE = "SELECT vote_type FROM vote WHERE user_id = :user_id " +
            "AND post_id = :post_id ";

    private NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());


    @Override
    public Vote create(Vote newObject) {
        Vote vote = new Vote(newObject);
        KeyHolder holder = new GeneratedKeyHolder();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("post_id", vote.getPostId());
        paramMap.put("user_id", vote.getUserId());
        paramMap.put("date", vote.getDate());
        paramMap.put("is_positive", vote.isPositive());
        paramMap.put("vote_type",vote.getVoteType().name());
        SqlParameterSource parameterSource = new MapSqlParameterSource(paramMap);
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
    public boolean update(Vote vote) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("vote_id", vote.getVoteId());
        paramMap.put("date", vote.getDate());
        paramMap.put("is_positive", vote.isPositive());
        paramMap.put("vote_type",vote.getVoteType().name());
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
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("user_id", userId);
        paramMap.put("post_id", postId);
        SqlParameterSource parameterSource = new MapSqlParameterSource(paramMap);
        try{
            return template.queryForObject(READ_VOTE_BY_IDS,parameterSource,new VoteRowMapper());
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public boolean removeAllPostVotes(int postId) {
        Map<String, Object> map = new HashMap<>();
        map.put("post_id", postId);
        SqlParameterSource parameterSource = new MapSqlParameterSource(map);
        return template.update(DELETE_POST_ALL_VOTES,parameterSource) > 0;
    }

    @Override
    public String getUserPostVoteType(int postId, int userId) {
        try{
            if (userId == -1){
                return VoteType.RETURNED.name();
            }
            Map<String, Object> map = new HashMap<>();
            map.put("post_id", postId);
            map.put("user_id",userId);
            SqlParameterSource parameterSource = new MapSqlParameterSource(map);
            List<String> score = template.query(GET_USER_POST_VOTE_TYPE,parameterSource, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("vote_type");
                }
            });
           if (score.size() > 0){
               return score.get(0);
           }
           else {
               return VoteType.RETURNED.name();
           }
        }catch (EmptyResultDataAccessException e){
            return VoteType.RETURNED.name();
        }
    }


    private class VoteRowMapper implements RowMapper<Vote>{

        @Override
        public Vote mapRow(ResultSet resultSet, int i) throws SQLException {
            Vote vote = new Vote();
            vote.setVoteId(resultSet.getInt("vote_id"));
            vote.setDate(resultSet.getTimestamp("date"));
            vote.setPositive(resultSet.getBoolean("is_positive"));
            vote.setPostId(resultSet.getInt("post_id"));
            vote.setUserId(resultSet.getInt("user_id"));
            vote.setVoteType(VoteType.valueOf(resultSet.getString("vote_type")));
            return vote;
        }
    }

}
