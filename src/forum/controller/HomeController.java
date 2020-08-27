package forum.controller;

import forum.model.Post;
import forum.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("")
public class HomeController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PostService postService = new PostService();
        String postSort = request.getParameter("postSort");
        if (postSort == null){
            postSort = "ORDER_BY_VOTE";
        }
        List<Post> posts = postService.readPosts(postSort);
        request.setAttribute("posts",posts);
        request.getRequestDispatcher("WEB-INF/index.jsp").forward(request,response);
    }
}
