package forum.controller;

import forum.model.User;
import forum.service.CommentService;
import forum.service.CommentVoteService;

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
        CommentVoteService commentVoteService = new CommentVoteService();
        commentVoteService.deleteCommentAllVotes(Integer.parseInt(request.getParameter("comment-id")));
        CommentService commentService = new CommentService();
        commentService.deleteComment(Integer.parseInt(request.getParameter("comment-id")));
        response.sendRedirect(request.getContextPath()+"/post?post-id="+request.getParameter("post-id"));
    }
}
