package forum.service;

import forum.dao.DAOFactory;
import forum.dao.PostDAO;
import forum.model.Post;
import forum.model.PostSort;
import forum.model.User;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

public class PostService {

    public void createPost(String title, String description, String message, User user){
        Post post = initializePost(title,description,message,user);
        DAOFactory factory = DAOFactory.getDAOFactory();
        PostDAO postDAO = factory.getPostDAO();
        postDAO.create(post);
    }

    public List<Post> readPostWithPageSize(int pageSize, int pageNumber){
        DAOFactory factory = DAOFactory.getDAOFactory();
        PostDAO postDAO =factory.getPostDAO();
        return postDAO.readPostWithPageSize(pageSize, pageNumber);
    }

    public List<Post> readPosts(String postSort){
        DAOFactory factory = DAOFactory.getDAOFactory();
        PostDAO postDAO = factory.getPostDAO();
        return postDAO.getAll(PostSort.valueOf(postSort));
    }

    public boolean deletePost(int user_id){
        DAOFactory factory = DAOFactory.getDAOFactory();
        PostDAO postDAO = factory.getPostDAO();
        return postDAO.delete(user_id);
    }

    public List<Post> readUserPostHistory(int user_id){
        DAOFactory factory = DAOFactory.getDAOFactory();
        PostDAO postDAO = factory.getPostDAO();
        return postDAO.getUserPosts(user_id);
    }

    public Post readPost(int postId){
        DAOFactory factory = DAOFactory.getDAOFactory();
        PostDAO postDAO = factory.getPostDAO();
        return postDAO.read(postId);
    }

    public boolean updatePost(Post post){
        DAOFactory factory =DAOFactory.getDAOFactory();
        PostDAO postDAO = factory.getPostDAO();
        return postDAO.update(post);
    }

    public List<Post> searchPostsByKeywords(String keywords, String postSort){
        DAOFactory factory = DAOFactory.getDAOFactory();
        PostDAO postDAO = factory.getPostDAO();
        return postDAO.getByKeywords(keywords,PostSort.valueOf(postSort));
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
