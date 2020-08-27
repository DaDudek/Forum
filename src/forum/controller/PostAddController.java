package forum.controller;

import forum.model.User;
import forum.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/new-post")
public class PostAddController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("inputTitle");
        String description = request.getParameter("inputDescription");
        String message = request.getParameter("inputMessage");
        User user = (User) request.getSession().getAttribute("user");
        if (request.getUserPrincipal() != null){
            PostService postService = new PostService();
            postService.createPost(title,description,message,user);
            response.sendRedirect(request.getContextPath()+"/");
        }else {
            response.sendError(403);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getUserPrincipal() != null){
            request.getRequestDispatcher("WEB-INF/addpost.jsp").forward(request, response);
        } else {
            response.sendError(403);
        }
    }
}
