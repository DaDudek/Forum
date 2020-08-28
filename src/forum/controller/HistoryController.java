package forum.controller;

import forum.model.Post;
import forum.model.User;
import forum.service.PostService;
import forum.service.UserService;

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
        UserService userService = new UserService();
        User user = (User) request.getSession().getAttribute("user");
        List<Post> posts = postService.readUserPostHistory(user.getUserId());
        request.setAttribute("posts",posts);
        request.getRequestDispatcher("WEB-INF/index.jsp").forward(request,response);
    }
}
