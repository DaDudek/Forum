package forum.logic;

import forum.model.Comment;
import forum.service.CommentService;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CommentResponseHandler {

    public void setCommentsChildren(HttpServletRequest request, Comment comment) {
        try {
            CommentService commentService = new CommentService();
            List<Comment> commentFirstChildren = commentService.findCommentFirstChildrenList(comment.getCommentId());
            comment.setFirstChildrenList(commentFirstChildren);
            ColorVoteButtonHandler<Comment> commentColorVoteButtonHandler = new ColorVoteButtonHandler<>();
            for (Comment commentFirstChild : commentFirstChildren) {
                setCommentsChildren(request,commentFirstChild);
                commentColorVoteButtonHandler.initPublicationVoteStatus(request,commentFirstChild.getFirstChildrenList());
            }
        } catch (EmptyResultDataAccessException e) {

        }
    }
}
