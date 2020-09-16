package forum.controller;

import forum.logic.ColorVoteButtonHandler;
import forum.model.Comment;
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
import java.util.List;

@WebServlet("/history")
public class HistoryController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PostService postService = new PostService();
        CommentService commentService  = new CommentService();
        User user = (User) request.getSession().getAttribute("user");

        List<Post> posts = postService.readUserPostHistory(user.getUserId());
        ColorVoteButtonHandler<Post> colorVoteButtonHandler = new ColorVoteButtonHandler<>();
        colorVoteButtonHandler.initPublicationVoteStatus(request, posts);

        List<Comment> comments = commentService.readUserAllComment(user.getUserId());
        ColorVoteButtonHandler<Comment> commentColorVoteButtonHandler = new ColorVoteButtonHandler<>();
        commentColorVoteButtonHandler.initPublicationVoteStatus(request, comments);

        request.setAttribute("posts",posts);
        request.setAttribute("comments", comments);
        request.getRequestDispatcher("WEB-INF/history.jsp").forward(request,response);
    }
}
