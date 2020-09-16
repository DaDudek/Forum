package forum.controller;

import forum.model.*;
import forum.service.CommentService;
import forum.service.CommentVoteService;
import forum.service.PostService;
import forum.service.VoteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/comment-vote")
public class CommentVoteController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User user = (User) request.getSession().getAttribute("user");
            int userId = user.getUserId();
            int comment_id = Integer.parseInt(request.getParameter("comment_id"));
            int post_id = Integer.parseInt(request.getParameter("post_id"));
            boolean isPositive = Boolean.parseBoolean(request.getParameter("is_positive"));
            updateVote(comment_id,userId, isPositive);
            boolean fromHistory = Boolean.parseBoolean(request.getParameter("history"));
            if (fromHistory){
                request.getRequestDispatcher("/history").forward(request,response);
            }
            else {
                response.sendRedirect(request.getContextPath() + "/post?post-id=" + request.getParameter("post_id") + "&page=" + request.getParameter("page"));
            }
        } catch (NumberFormatException numberFormatException){
            response.sendError(404);
        }

    }

    private void updateVote(int commentId, int userId, boolean isPositive){
        CommentVoteService commentVoteService = new CommentVoteService();
        CommentVote pastVote = commentVoteService.getCommentVoteByIds(commentId,userId);
        CommentVote newVote = commentVoteService.createOrUpdateCommentVote(commentId,userId, isPositive);
        updateComment(commentId, pastVote, newVote);

    }

    private void updateComment(int commentId, CommentVote pastVote, CommentVote newVote){
        CommentService commentService = new CommentService();
        Comment comment =commentService.readComment(commentId);
        Comment updatedComment = choosePostUpdateOption(comment, pastVote, newVote);
        commentService.updateComment(updatedComment);
    }

    private Comment choosePostUpdateOption(Comment comment, CommentVote pastVote, CommentVote newVote){
        if (pastVote == null && newVote != null){
            return updateCommentWithNullVote(comment, newVote.isPositive());
        }
        switch (pastVote.getVoteType()){
            case POSITIVE:
                return updateCommentWithPositiveVote(comment, newVote.isPositive());
            case RETURNED:
                return updateCommentWithReturnedVote(comment, newVote.isPositive());
            case NEGATIVE:
                return updateCommentWithNegativeVote(comment, newVote.isPositive());
        }
        return null;
    }

    private Comment updateCommentWithNullVote(Comment comment, boolean isPositive){
        if (isPositive){
            comment.setPositiveVote(comment.getPositiveVote() + 1);
        }else {
            comment.setNegativeVote(comment.getNegativeVote() + 1);
        }
        return comment;
    }

    private Comment updateCommentWithPositiveVote(Comment comment, boolean isPositive){
        if (isPositive){
            comment.setPositiveVote(comment.getPositiveVote() - 1);
        }else {
            comment.setPositiveVote(comment.getPositiveVote() - 1);
            comment.setNegativeVote(comment.getNegativeVote() + 1);
        }
        return comment;
    }

    private Comment updateCommentWithNegativeVote(Comment comment, boolean isPositive){
        if (isPositive){
            comment.setPositiveVote(comment.getPositiveVote() + 1);
            comment.setNegativeVote(comment.getNegativeVote() - 1);
        }
        else {
            comment.setNegativeVote(comment.getNegativeVote() - 1);
        }
        return comment;
    }

    private Comment updateCommentWithReturnedVote(Comment comment, boolean isPositive){
        if (isPositive){
            comment.setPositiveVote(comment.getPositiveVote() + 1);
        }
        else {
            comment.setNegativeVote(comment.getNegativeVote() + 1);
        }
        return comment;
    }
}
