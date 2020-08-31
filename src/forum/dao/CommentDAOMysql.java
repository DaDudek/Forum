package forum.dao;

import forum.model.Comment;
import forum.model.Post;
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

public class CommentDAOMysql  implements CommentDAO{

    private final static String CREATE =
            "INSERT INTO comment(post_id, user_id, date, message, positive_vote, negative_vote) " +
                    "VALUES(:post_id, :user_id, :date, :message, :positive_vote, :negative_vote) ";

    private final static String READ_POST_ALL_COMMENTS =
            "SELECT comment_id, post_id, comment.user_id, date, message, positive_vote, negative_vote, username " +
                    "FROM comment INNER JOIN user ON comment.user_id = user.user_id " +
                    " WHERE post_id = :post_id ORDER BY date DESC";

    private static final String DELETE = "DELETE FROM comment where comment_id = :comment_id";


    private NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());


    @Override
    public Comment create(Comment newObject) {
        Comment comment = new Comment(newObject);
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource(mapComment(comment));
        if (template.update(CREATE, parameterSource,holder) == 1){
            comment.setCommentId(holder.getKey().intValue());
        }
        return comment;
    }

    @Override
    public Comment read(Integer primaryKey) {
        return null;
    }

    @Override
    public boolean update(Comment updateObject) {
        return false;
    }

    @Override
    public boolean delete(Integer key) {
        Map<String, Object> map = new HashMap<>();
        map.put("comment_id",key);
        SqlParameterSource parameterSource = new MapSqlParameterSource(map);
        return (template.update(DELETE,parameterSource) == 1);

    }

    private Map<String, Object> mapComment(Comment comment){
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", comment.getUserId());
        map.put("message", comment.getMessage());
        map.put("date", comment.getDate());
        map.put("positive_vote", comment.getPositiveVote());
        map.put("negative_vote", comment.getNegativeVote());
        map.put("post_id", comment.getPostId());
        map.put("comment_id",comment.getCommentId());
        return map;
    }

    @Override
    public List<Comment> readAllPostComment(int post_id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("post_id",post_id);
        SqlParameterSource parameterSource = new MapSqlParameterSource(paramMap);
        return template.query(READ_POST_ALL_COMMENTS,parameterSource,new CommentRowMapper());
    }

    private class CommentRowMapper implements RowMapper<Comment>{

        @Override
        public Comment mapRow(ResultSet resultSet, int i) throws SQLException {
            Comment comment = new Comment();
            comment.setUserId(resultSet.getInt("user_id"));
            comment.setPostId(resultSet.getInt("post_id"));
            comment.setNegativeVote(resultSet.getInt("negative_vote"));
            comment.setPositiveVote(resultSet.getInt("positive_vote"));
            comment.setMessage(resultSet.getString("message"));
            comment.setCommentId(resultSet.getInt("comment_id"));
            comment.setDate(resultSet.getTimestamp("date"));
            comment.setAuthor(resultSet.getString("username"));
            return comment;
        }
    }
}
