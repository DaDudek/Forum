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

    public boolean updateComment(Comment comment){
        DAOFactory factory = DAOFactory.getDAOFactory();
        CommentDAO commentDAO = factory.getCommentDAO();
        return commentDAO.update(comment);
    }

    public Comment readComment(int commentId){
        DAOFactory factory = DAOFactory.getDAOFactory();
        CommentDAO commentDAO = factory.getCommentDAO();
        return commentDAO.read(commentId);
    }

    public void createComment(Post post, User user, String message){
        Comment comment = initializeComment(post,user,message, -1);
        DAOFactory factory = DAOFactory.getDAOFactory();
        CommentDAO commentDAO = factory.getCommentDAO();
        commentDAO.create(comment);

    }

    public List<Comment> readPostAllRootComments(int post_id){
        DAOFactory factory = DAOFactory.getDAOFactory();
        CommentDAO commentDAO = factory.getCommentDAO();
        return commentDAO.readAllPostRootComments(post_id);
    }

    public List<Comment> readPostAllComments(int post_id){
        DAOFactory factory = DAOFactory.getDAOFactory();
        CommentDAO commentDAO = factory.getCommentDAO();
        return commentDAO.readAllPostComments(post_id);
    }

    public List<Comment> readUserAllComment(int userId){
        DAOFactory factory = DAOFactory.getDAOFactory();
        CommentDAO commentDAO = factory.getCommentDAO();
        return commentDAO.readUserAllComments(userId);
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

    public List<Comment> findCommentFirstChildrenList(int parentId){
        DAOFactory factory =DAOFactory.getDAOFactory();
        CommentDAO commentDAO = factory.getCommentDAO();
        return commentDAO.findCommentFirstChildrenList(parentId);
    }


    private Comment initializeComment(Post post, User user, String message, int parent_id){
        Comment comment = new Comment();
        comment.setDate(new Timestamp(new Date().getTime()));
        comment.setMessage(message);
        comment.setPositiveVote(0);
        comment.setNegativeVote(0);
        comment.setPostId(post.getPostId());
        comment.setUserId(user.getUserId());
        comment.setParentId(parent_id);
        return comment;
    }
}
