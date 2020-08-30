package forum.service;

import forum.dao.CommentDAO;
import forum.dao.DAOFactory;
import forum.model.Comment;
import forum.model.Post;
import forum.model.User;

import java.sql.Timestamp;
import java.util.Date;

public class CommentService {

    public void createComment(Post post, User user, String message){
        Comment comment = initializeComment(post,user,message);
        DAOFactory factory = DAOFactory.getDAOFactory();
        CommentDAO commentDAO = factory.getCommentDAO();
        commentDAO.create(comment);

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
