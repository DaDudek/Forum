package forum.logic;

import forum.model.Comment;
import forum.service.CommentService;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public class CommentResponseHandler {

    public void setCommentsChildren(Comment comment) {
        try {
            CommentService commentService = new CommentService();
            List<Comment> commentFirstChildren = commentService.findCommentFirstChildrenList(comment.getCommentId());
            comment.setFirstChildrenList(commentFirstChildren);
            for (Comment commentFirstChild : commentFirstChildren) {
                setCommentsChildren(commentFirstChild);
            }
        } catch (EmptyResultDataAccessException e) {

        }
    }
}
