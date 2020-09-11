package forum.controller;

import forum.logic.InputLengthHandler;
import forum.model.Post;
import forum.model.User;
import forum.service.CommentService;
import forum.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add-comment")
public class AddCommentController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommentService commentService = new CommentService();
        InputLengthHandler inputLengthHandler = new InputLengthHandler();
        PostService postService = new PostService();
        User user =(User) request.getSession().getAttribute("user");
        Post post = postService.readPost(Integer.parseInt(request.getParameter("post-id")));
        commentService.createComment(post, user, inputLengthHandler.checkLengthAndReturnValue(request.getParameter("inputMessage"), InputLengthHandler.MESSAGE_SIZE));

        response.sendRedirect(request.getContextPath() + "/post?post-id="+request.getParameter("post-id"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(403);
    }
}
