package forum.controller;

import forum.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/sign-on")
public class SignOnController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        UserService userService = new UserService();
        String username = request.getParameter("inputUsername");
        String password = request.getParameter("inputPassword");
        String email = request.getParameter("inputEmail");
        userService.createUser(username, email, password);
        response.sendRedirect(request.getContextPath() + "/");    // in future add message with feedback
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("signon.jsp").forward(request,response);
    }
}
