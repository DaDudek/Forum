package forum.controller;

import forum.logic.ColorVoteButtonHandler;
import forum.logic.PaginationHandler;
import forum.model.Post;
import forum.model.PostSort;
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
    public static final int HOW_MANY_POSTS_IN_ONE_PAGE = 5;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.sendRedirect(request.getContextPath()+"/search?sort="+request.getParameter("sort")+"&keywords="+request.getParameter("inputKeywords"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("sort") == null || request.getParameter("keywords") == null){
            request.getRequestDispatcher("WEB-INF/search.jsp").forward(request, response);
        }
        else {
            try {
                PostService postService = new PostService();
                String postSort = request.getParameter("sort");
                String keywords = request.getParameter("keywords");
                PostSort.valueOf(returnPostSortName(postSort));
                PaginationHandler<Post> paginationHandler = new PaginationHandler<>();

                List<Post> posts = postService.searchPostsByKeywords(keywords, returnPostSortName(postSort));
                int pageNumber = paginationHandler.initPageNumber(request.getParameter("page"));
                request.setAttribute("pageNumber", pageNumber);

                List<Integer> pages = paginationHandler.setPagesList(posts
                );
                if (pageNumber > pages.size() || pageNumber <= 0){
                    response.sendError(404);
                } else {
                    List<Post> postsOnPage = paginationHandler.setPublicationOnPage(posts,pageNumber);
                    ColorVoteButtonHandler<Post> colorVoteButtonHandler = new ColorVoteButtonHandler<>();
                    colorVoteButtonHandler.initPublicationVoteStatus(request, postsOnPage);
                    request.setAttribute("posts", postsOnPage);
                    request.setAttribute("postSort",postSort);
                    request.setAttribute("keywords",keywords);
                    request.setAttribute("pages",pages);
                    request.setAttribute("lastPageNumber",pages.size());
                    request.getSession().setAttribute("url", "/search?sort=" + postSort + "&keywords=" + keywords+"&page="+pageNumber);
                    request.getRequestDispatcher("WEB-INF/showsearch.jsp").forward(request, response);
                }
            } catch (IllegalArgumentException e){
                response.sendError(404);
            }

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
