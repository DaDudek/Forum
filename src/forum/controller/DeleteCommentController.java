package forum.controller;

import forum.model.Comment;
import forum.model.Post;
import forum.model.User;
import forum.service.CommentService;
import forum.service.CommentVoteService;
import forum.service.PostService;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete-comment")
public class DeleteCommentController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            int commentId = Integer.parseInt(request.getParameter("comment-id"));
            int postId = Integer.parseInt(request.getParameter("post-id"));
            User user = (User) request.getSession().getAttribute("user");
            CommentVoteService commentVoteService = new CommentVoteService();
            PostService postService = new PostService();
            CommentService commentService = new CommentService();
            Comment comment = commentService.readComment(commentId);
            Post post = postService.readPost(postId);
            if (comment.getUserId() == user.getUserId()) {
                commentVoteService.deleteCommentAllVotes(Integer.parseInt(request.getParameter("comment-id")));
                commentService.deleteComment(Integer.parseInt(request.getParameter("comment-id")));
                response.sendRedirect(request.getContextPath()+"/post?post-id="+request.getParameter("post-id"));
            }
            else {
                response.sendError(403);
            }
        } catch (NumberFormatException | EmptyResultDataAccessException e){
            response.sendError(404);
        }

    }
}
