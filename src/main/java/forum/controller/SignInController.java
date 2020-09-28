package forum.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/sign-in")
public class SignInController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("page") != null) {
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            if (request.getUserPrincipal() != null) {
                response.sendRedirect(request.getContextPath() + (String) request.getSession().getAttribute("url"));
            } else {
                response.sendError(403);
            }
        }
    }
}
