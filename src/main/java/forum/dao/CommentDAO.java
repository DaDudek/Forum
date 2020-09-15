package forum.dao;

import forum.model.Comment;

import java.util.List;

public interface CommentDAO extends GenericDAO<Comment, Integer> {

    List<Comment> readAllPostComments(int postId);

    List<Comment> readAllPostRootComments(int postId);

    List<Comment> readUserAllComments(int userId);

    List<Comment> findCommentFirstChildrenList(int parentId);


    boolean deleteAllPostComment(int postId);
}
