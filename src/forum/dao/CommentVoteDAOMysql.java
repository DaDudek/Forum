package forum.dao;

import forum.model.Comment;
import forum.model.CommentVote;
import forum.model.Vote;
import forum.model.VoteType;
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
import java.util.Map;

public class CommentVoteDAOMysql implements CommentVoteDAO {

    private final static String CREATE =
            "INSERT INTO comment_vote(comment_id, user_id, date, is_positive, vote_type) " +
                    "VALUES (:comment_id, :user_id, :date, :is_positive, :vote_type)";

    private static final String READ =
            "SELECT comment_vote_id, comment_id, user_id, date, is_positive, vote_type FROM " +
            "comment_vote WHERE comment_vote_id = :comment_vote_id";

    private static final String READ_VOTE_BY_IDS =
            "SELECT comment_vote_id, comment_id, user_id,date, is_positive, vote_type " +
            "FROM vote WHERE user_id = :user_id AND comment_id = :comment_id " ;

    private static final String UPDATE =
            "UPDATE comment_vote SET date =:date, is_positive=:is_positive, vote_type=:vote_type " +
                    "WHERE comment_vote_id = :comment_vote_id";

    private static final String DELETE_COMMENT_ALL_VOTES = "DELETE FROM comment_vote WHERE comment_id = :comment_id";

    private NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());


    @Override
    public CommentVote create(CommentVote newObject) {
        CommentVote commentVote = new CommentVote(newObject);
        KeyHolder holder = new GeneratedKeyHolder();
        Map<String, Object> map = new HashMap<>();
        map.put("comment_id", commentVote.getCommentId());
        map.put("user_id", commentVote.getUserId());
        map.put("date", commentVote.getDate());
        map.put("is_positive", commentVote.isPositive());
        map.put("vote_type", commentVote.getVoteType());
        SqlParameterSource parameterSource = new MapSqlParameterSource(map);
        if (template.update(CREATE,parameterSource,holder) == 1){
            commentVote.setCommentVoteId(holder.getKey().intValue());
        }
        return commentVote;
    }

    @Override
    public CommentVote read(Integer primaryKey){
            Map<String, Object> map = new HashMap<>();
            map.put("comment_vote_id", primaryKey);
            SqlParameterSource parameterSource = new MapSqlParameterSource(map);
            CommentVote commentVote = template.queryForObject(READ, parameterSource, new CommentVoteRowMapper());
            return commentVote;
    }

    @Override
    public boolean update(CommentVote commentVote) {

        Map<String, Object> map = new HashMap<>();
        map.put("comment_vote_id", commentVote.getCommentVoteId());
        map.put("date", commentVote.getDate());
        map.put("is_positive", commentVote.isPositive());
        map.put("vote_type",commentVote.getVoteType().name());
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

    public CommentVote getCommentVoteByIds(int commentId, int userId){
        Map<String, Object> map = new HashMap<>();
        map.put("user_id",userId);
        map.put("comment_id",commentId);
        SqlParameterSource parameterSource = new MapSqlParameterSource(map);
        try {
            return template.queryForObject(READ_VOTE_BY_IDS,parameterSource, new CommentVoteRowMapper());
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public boolean removeAllCommentVotes(int commentId){
        Map<String, Object> map = new HashMap<>();
        map.put("comment_id", commentId);
        SqlParameterSource parameterSource = new MapSqlParameterSource(map);
        return template.update(DELETE_COMMENT_ALL_VOTES, parameterSource) > 0;
    }

    private class CommentVoteRowMapper implements RowMapper<CommentVote>{

        @Override
        public CommentVote mapRow(ResultSet resultSet, int i) throws SQLException {
            CommentVote commentVote = new CommentVote();
            commentVote.setCommentVoteId(resultSet.getInt("comment_vote_id"));
            commentVote.setDate(resultSet.getTimestamp("date"));
            commentVote.setPositive(resultSet.getBoolean("is_positive"));
            commentVote.setUserId(resultSet.getInt("user_id"));
            commentVote.setVoteType(VoteType.valueOf(resultSet.getString("vote_type")));
            commentVote.setCommentId(resultSet.getInt("comment_id"));
            return commentVote;
        }
    }
}
