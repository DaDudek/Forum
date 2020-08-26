package forum.dao;

import forum.model.User;

public interface UserDAO extends GenericDAO<User, Integer>{

    User readByUsername(String username);
}
