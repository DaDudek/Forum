package forum.controller;

import forum.logic.PaginationHandler;
import forum.model.Comment;
import forum.model.Post;
import forum.service.CommentService;
import forum.service.PostService;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/post")
public class PostViewController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int postId = Integer.parseInt(request.getParameter("post-id"));
            PostService postService = new PostService();
            CommentService commentService = new CommentService();
            PaginationHandler<Comment> paginationHandler = new PaginationHandler<>();

            Post post = postService.readPost(postId);
            List<Comment> comments = commentService.readPostAllComment(postId);

            int pageNumber = paginationHandler.initPageNumber(request.getParameter("page"));
            List<Integer> pages = paginationHandler.setPagesList(comments);


            if (pageNumber > pages.size() || pageNumber <= 0){
                response.sendError(404);
            } else {
                List<Comment> commentInPage = paginationHandler.setPublicationOnPage(comments, pageNumber);

                request.setAttribute("pageNumber", pageNumber);
                request.setAttribute("lastPageNumber",pages.size());
                request.setAttribute("post", post);
                request.setAttribute("comments", commentInPage);
                request.setAttribute("pages",pages);
                request.getSession().setAttribute("url","/post?post-id="+request.getParameter("post-id")+"&page="+pageNumber);
                request.getRequestDispatcher("WEB-INF/post.jsp").forward(request, response);
            }
        } catch (EmptyResultDataAccessException e){
            response.sendError(404);
        }
    }


}
