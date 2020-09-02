package forum.dao;

import forum.model.Comment;

import java.util.List;

public interface CommentDAO extends GenericDAO<Comment, Integer> {

    List<Comment> readAllPostComment(int postId);

    List<Comment> readUserAllComments(int userId);

    boolean deleteAllPostComment(int postId);
}
