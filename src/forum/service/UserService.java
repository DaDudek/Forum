package forum.service;

import forum.dao.DAOFactory;
import forum.dao.UserDAO;
import forum.model.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService {

    public void createUser(String username, String email, String password){
        User user = initializeUser(username,email,password);
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDAO = factory.getUserDAO();
        userDAO.create(user);
    }

    public User readUser(int userId){
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDAO = factory.getUserDAO();
        User user = userDAO.read(userId);
        return user;
    }

    public User readUserByUsername(String username){
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDAO = factory.getUserDAO();
        User user = userDAO.readByUsername(username);
        return user;
    }

    private User initializeUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encryptPassword(password));
        user.setAccountActive(true); //in future change to send email with activate link
        return user;
    }

    private String encryptPassword(String password){
        MessageDigest digest = null;
        try{
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        digest.update(password.getBytes());
        String md5Passwrod = new BigInteger(1, digest.digest()).toString(16);
        return md5Passwrod;
    }
}
