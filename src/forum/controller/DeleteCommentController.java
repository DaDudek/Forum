package forum.controller;

import forum.model.Comment;
import forum.model.Post;
import forum.model.Role;
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


            CommentService commentService = new CommentService();
            User user = (User) request.getSession().getAttribute("user");

            Comment comment = commentService.readComment(commentId);

            if((comment.getUserId() == user.getUserId()) || (user.getRole().equals(Role.valueOf("ADMIN")))){
                deleteComment(commentId);
                response.sendRedirect(request.getContextPath()+"/post?post-id="+request.getParameter("post-id"));
            }
            else {
                response.sendError(403);
            }
        } catch (NumberFormatException | EmptyResultDataAccessException e){
            response.sendError(404);
        }

    }
    private void deleteComment(int commentId){
        CommentVoteService commentVoteService = new CommentVoteService();
        CommentService commentService = new CommentService();

        commentVoteService.deleteCommentAllVotes(commentId);
        commentService.deleteComment(commentId);
    }
}
