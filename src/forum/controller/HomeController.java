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
        request.getSession().setAttribute("url","/");
        PostService postService = new PostService();
        List<Post> posts = postService.readPosts("ORDER_BY_NEWEST");
        request.setAttribute("posts",posts);
        request.setAttribute("pages",initPagesNumber(posts));
        System.out.println(initPagesNumber(posts));
        request.getRequestDispatcher("WEB-INF/index.jsp").forward(request,response);
    }
    private List<Integer> initPagesNumber(List<Post> posts){
        int counter = 1;
        int size = posts.size();
        List<Integer> pages = new ArrayList<>();
        pages.add(counter);
        while (size / HOW_MANY_POSTS_IN_ONE_PAGE > 0){
            counter++;
            pages.add(counter);
            size = size / HOW_MANY_POSTS_IN_ONE_PAGE;
        }
        return pages;
    }
}
