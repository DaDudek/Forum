package forum.service;

import forum.dao.CommentDAO;
import forum.dao.DAOFactory;
import forum.model.Comment;
import forum.model.Post;
import forum.model.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class CommentService {

    public void createComment(Post post, User user, String message){
        Comment comment = initializeComment(post,user,message);
        DAOFactory factory = DAOFactory.getDAOFactory();
        CommentDAO commentDAO = factory.getCommentDAO();
        commentDAO.create(comment);

    }

    public List<Comment> readPostAllComment(int post_id){
        DAOFactory factory = DAOFactory.getDAOFactory();
        CommentDAO commentDAO = factory.getCommentDAO();
        return commentDAO.readAllPostComment(post_id);
    }

    public boolean deleteComment(int comment_id){
        DAOFactory factory = DAOFactory.getDAOFactory();
        CommentDAO commentDAO = factory.getCommentDAO();
        return commentDAO.delete(comment_id);
    }

    public boolean deleteAllPostComment(int postId){
        DAOFactory factory = DAOFactory.getDAOFactory();
        CommentDAO commentDAO = factory.getCommentDAO();
        return commentDAO.deleteAllPostComment(postId);
    }


    private Comment initializeComment(Post post, User user, String message){
        Comment comment = new Comment();
        comment.setDate(new Timestamp(new Date().getTime()));
        comment.setMessage(message);
        comment.setPositiveVote(0);
        comment.setNegativeVote(0);
        comment.setPostId(post.getPostId());
        comment.setUserId(user.getUserId());
        return comment;
    }
}
