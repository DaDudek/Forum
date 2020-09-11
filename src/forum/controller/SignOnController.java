package forum.controller;

import forum.logic.InputLengthHandler;
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
        InputLengthHandler inputLengthHandler = new InputLengthHandler();
        UserService userService = new UserService();
        String username = inputLengthHandler.checkLengthAndReturnValue(request.getParameter("inputUsername"),InputLengthHandler.USERNAME_SIZE);
        String password = request.getParameter("inputPassword");
        String email = request.getParameter("inputEmail");
        setRequestAttribute(username,email,request);
        if (userService.checkUsername(username) && userService.checkEmail(email)){
            userService.createUser(username, email, password);
            response.sendRedirect(request.getContextPath()+ "/" + (String) request.getSession().getAttribute("url"));
        }
        else {
            request.getRequestDispatcher("/WEB-INF/signon.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("username", "Username (max 60 character - more will be cut)");
        request.setAttribute("email", "Email");
        request.getRequestDispatcher("/WEB-INF/signon.jsp").forward(request,response);
    }

    private void setRequestAttribute(String username, String email, HttpServletRequest request){
        UserService userService = new UserService();
        if (userService.checkUsername(username)){
            request.setAttribute("username", "Username (max 60 character - more will be cut)");
        }
        else {
            request.setAttribute("username", "This username is already used");
        }
        if (userService.checkEmail(email)){
            request.setAttribute("email", "Email");
        }
        else {
            request.setAttribute("email", "This email is already used");
        }

    }
}
