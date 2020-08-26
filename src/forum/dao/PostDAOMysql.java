package forum.dao;

import forum.model.Post;
import forum.util.ConnectionProvider;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostDAOMysql implements PostDAO{
    public static final String CREATE =
            "INSERT INTO post(user_id, title, description, message, date, positive_vote, negative_vote) " +
                    "VALUES(:user_id, :title, :description, :message, :date, :positive_vote, :negative_vote)";

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
        return null;
    }

    @Override
    public boolean update(Post updateObject) {
        return false;
    }

    @Override
    public boolean delete(Integer key) {
        return false;
    }

    @Override
    public List<Post> getAll() {
        return null;
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
}
