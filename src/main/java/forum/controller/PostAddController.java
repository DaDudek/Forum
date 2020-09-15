package forum.controller;

import forum.logic.InputLengthHandler;
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
        InputLengthHandler inputLengthHandler = new InputLengthHandler();
        String title = inputLengthHandler.checkLengthAndReturnValue(request.getParameter("inputTitle"), InputLengthHandler.TITLE_SIZE);
        String description = inputLengthHandler.checkLengthAndReturnValue(request.getParameter("inputDescription"), InputLengthHandler.DESCRIPTION_SIZE);
        String message = inputLengthHandler.checkLengthAndReturnValue(request.getParameter("inputMessage"), InputLengthHandler.MESSAGE_SIZE);
        User user = (User) request.getSession().getAttribute("user");
        PostService postService = new PostService();
        postService.createPost(title,description,message,user);
        response.sendRedirect(request.getContextPath()+"/");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getUserPrincipal() != null){
            request.getRequestDispatcher("WEB-INF/addpost.jsp").forward(request, response);
        } else {
            response.sendError(403);
        }
    }
}
