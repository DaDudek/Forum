package forum.dao;

import forum.model.Comment;
import forum.model.Post;
import forum.util.ConnectionProvider;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentDAOMysql  implements CommentDAO{

    private final static String CREATE =
            "INSERT INTO comment(post_id, user_id, date, message, positive_vote, negative_vote) " +
                    "VALUES(:post_id, :user_id, :date, :message, :positive_vote, :negative_vote) ";

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
        return false;
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
}
