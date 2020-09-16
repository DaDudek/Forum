package forum.controller;

import forum.logic.ColorVoteButtonHandler;
import forum.logic.PaginationHandler;
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
    public static final int HOW_MANY_POSTS_IN_ONE_PAGE = 5;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PostService postService = new PostService();
            PaginationHandler<Post> paginationHandler = new PaginationHandler<>();

            List<Post> posts= postService.readPosts("ORDER_BY_NEWEST");
            int pageNumber = paginationHandler.initPageNumber(request.getParameter("page"));
            List<Integer> pages = paginationHandler.setPagesList(posts);

            if (pageNumber > pages.size() || pageNumber <= 0){
                response.sendError(404);
            } else{
                List<Post> postOnPage = paginationHandler.setPublicationOnPage(posts,pageNumber);
                ColorVoteButtonHandler<Post> colorVoteButtonHandler = new ColorVoteButtonHandler<>();
                colorVoteButtonHandler.initPublicationVoteStatus(request, postOnPage);

                request.setAttribute("pageNumber", pageNumber);
                request.setAttribute("lastPageNumber", pages.size());
                request.setAttribute("posts", postOnPage);
                request.setAttribute("pages", pages);
                request.getSession().setAttribute("url", "?page=" + pageNumber);
                request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
            }
        }catch (NumberFormatException e){
            response.sendError(404);
        }
    }


}
