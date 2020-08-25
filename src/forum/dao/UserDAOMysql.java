package forum.dao;

import forum.model.User;

import java.util.List;

public class UserDAOMysql implements UserDAO {
    @Override
    public User create(User newObject) {
        return null;
    }

    @Override
    public User read(Integer primaryKey) {
        return null;
    }

    @Override
    public boolean update(User updateObject) {
        return false;
    }

    @Override
    public boolean delete(Integer key) {
        return false;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
