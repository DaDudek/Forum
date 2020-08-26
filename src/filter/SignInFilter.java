package filter;

import forum.model.User;
import forum.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class SignInFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        if (request.getUserPrincipal() != null && request.getSession().getAttribute("user") == null){
            saveSignInUser(request);
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

    private void saveSignInUser(HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        UserService userService = new UserService();
        User user = userService.readUserByUsername(username);
        request.getSession(true).setAttribute("user",user);
    }

}
