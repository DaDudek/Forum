package forum.dao;

import forum.model.Comment;

import java.util.List;

public class CommentDAOMysql  implements CommentDAO{
    @Override
    public Comment create(Comment newObject) {
        return null;
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

    @Override
    public List<Comment> getAll() {
        return null;
    }
}
