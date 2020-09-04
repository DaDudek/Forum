package forum.controller;

import forum.model.Post;
import forum.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("")
public class HomeController extends HttpServlet {
    public static final int HOW_MANY_POSTS_IN_ONE_PAGE = 5;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PostService postService = new PostService();
            List<Post> allPosts = postService.readPosts("ORDER_BY_NEWEST");
            int pageNumber = 0;
            if (request.getParameter("page") == null) {
                request.setAttribute("pageNumber", 1);
                pageNumber = 1;
            } else {
                pageNumber = Integer.parseInt(request.getParameter("page"));
                request.setAttribute("pageNumber", pageNumber);
            }
            List<Integer> pages = initPagesNumber(allPosts);
            if (pageNumber > pages.size() || pageNumber <= 0){
                response.sendError(404);
            } else{
                List<Post> posts = postService.readPostWithPageSize(HOW_MANY_POSTS_IN_ONE_PAGE, pageNumber);
                request.setAttribute("lastPageNumber", pages.size());
                request.setAttribute("posts", posts);
                request.setAttribute("pages", pages);
                request.getSession().setAttribute("url", "?page=" + pageNumber);
                request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
            }
        }catch (NumberFormatException e){
            response.sendError(404);
        }
    }
    private List<Integer> initPagesNumber(List<Post> posts){
        int counter = 1;
        int size = posts.size();
        List<Integer> pages = new ArrayList<>();
        pages.add(counter);
        size = size - HOW_MANY_POSTS_IN_ONE_PAGE;
        while (size > 0){
            counter++;
            pages.add(counter);
            size = size - HOW_MANY_POSTS_IN_ONE_PAGE;
        }
        return pages;
    }
}
