package forum.service;

import forum.dao.DAOFactory;
import forum.dao.UserDAO;
import forum.model.User;

public class UserService {

    public void createUser(String username, String email, String password){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setAccountActive(true); //in future change to send email with activate link
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDAO = factory.getUserDAO();
        userDAO.create(user);
    }
}
