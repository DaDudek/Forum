package forum.dao;

import forum.model.Comment;

import java.util.List;

public interface CommentDAO extends GenericDAO<Comment, Integer> {

    List<Comment> readAllPostComment(int post_id);
}
