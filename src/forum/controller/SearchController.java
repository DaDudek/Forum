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

@WebServlet("/search")
public class SearchController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.sendRedirect(request.getContextPath()+"/search?sort="+request.getParameter("sort")+"&keywords="+request.getParameter("inputKeywords"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("sort") == null || request.getParameter("keywords") == null){
            request.getRequestDispatcher("WEB-INF/search.jsp").forward(request, response);
        }
        else {
            PostService postService = new PostService();
            String postSort = request.getParameter("sort");
            String keywords = request.getParameter("keywords");
            List<Post> posts = postService.searchPostsByKeywords(keywords, returnPostSortName(postSort));
            request.setAttribute("posts", posts);
            request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
        }
    }

    private String returnPostSortName(String pattern){
        if (pattern.equals("best-vote")){
            return "ORDER_BY_BEST_VOTE";
        }
        if (pattern.equals("oldest")){
            return "ORDER_BY_OLDEST";
        }
        if (pattern.equals("newest")){
            return "ORDER_BY_NEWEST";
        }
        return null;
    }
}
