package forum.service;

import forum.dao.DAOFactory;
import forum.dao.UserDAO;
import forum.model.User;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService {

    public void createUser(String username, String email, String password){
        User user = initializeUser(username,email,password);
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDAO = factory.getUserDAO();
        userDAO.create(user);
    }

    public boolean checkUsername(String username){
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDAO = factory.getUserDAO();
        return userDAO.checkUsername(username);
    }

    public boolean checkEmail(String email){
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDAO = factory.getUserDAO();
        return userDAO.checkEmail(email);
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
        digest.update(password.getBytes(StandardCharsets.UTF_8));
        String md5Passwrod = new BigInteger(1, digest.digest()).toString(16);
        return md5Passwrod;
    }
}
