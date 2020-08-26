package forum.service;

import forum.dao.DAOFactory;
import forum.dao.PostDAO;
import forum.model.Post;
import forum.model.User;

import java.util.Date;
import java.sql.Timestamp;

public class PostService {

    public void createPost(String title, String description, String message, User user){
        Post post = initializePost(title,description,message,user);
        DAOFactory factory = DAOFactory.getDAOFactory();
        PostDAO postDAO = factory.getPostDAO();
        postDAO.create(post);
    }

    private Post initializePost(String title, String description, String message, User user){
        Post post = new Post();
        post.setTitle(title);
        post.setDescription(description);
        post.setMessage(message);
        post.setUser(new User(user));
        post.setDate(new Timestamp(new Date().getTime()));
        return post;
    }
}
