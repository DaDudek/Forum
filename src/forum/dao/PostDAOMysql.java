package forum.dao;

import forum.model.Post;

import java.util.List;

public class PostDAOMysql implements PostDAO{
    @Override
    public Post create(Post newObject) {
        return null;
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
}
