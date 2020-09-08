package forum.controller;

import forum.logic.CommentResponseHandler;
import forum.logic.PaginationHandler;
import forum.model.Comment;
import forum.model.Post;
import forum.model.User;
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

@WebServlet("/response")
public class AddResponseController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CommentService commentService = new CommentService();
        PostService postService = new PostService();
        User user =(User) request.getSession().getAttribute("user");
        Post post = postService.readPost(Integer.parseInt(request.getParameter("post-id")));
        commentService.createResponse(post, user, request.getParameter("inputMessage"),Integer.parseInt(request.getParameter("parent-id")));
        request.removeAttribute("responseParentId");

        response.sendRedirect(request.getContextPath() + "/post?post-id="+request.getParameter("post-id")+"&page="+request.getParameter("page"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            int commentId = Integer.parseInt(request.getParameter("parent-id"));
            int postId = Integer.parseInt(request.getParameter("post-id"));

            PostService postService = new PostService();
            CommentService commentService = new CommentService();
            User user = (User) request.getSession().getAttribute("user");
            PaginationHandler<Comment> paginationHandler = new PaginationHandler<>();
            CommentResponseHandler commentResponseHandler = new CommentResponseHandler();

            Post post = postService.readPost(postId);
            Comment comment = commentService.readComment(commentId);
            List<Comment> comments = commentService.readPostAllRootComments(postId);

            int pageNumber = paginationHandler.initPageNumber(request.getParameter("page"));
            List<Integer> pages = paginationHandler.setPagesList(comments);

            if (pageNumber > pages.size() || pageNumber <= 0) {
                response.sendError(404);
            } else {
                List<Comment> commentsInPage = paginationHandler.setPublicationOnPage(comments, pageNumber);
                for (Comment commentChild : commentsInPage) {
                    commentResponseHandler.setCommentsChildren(commentChild);
                }
                request.setAttribute("responseParentId",Integer.parseInt(request.getParameter("parent-id")));
                System.out.println("here");
                request.setAttribute("post", post);
                request.setAttribute("comments", commentsInPage);
                request.setAttribute("pageNumber", pageNumber);
                request.setAttribute("lastPageNumber", pages.size());
                request.setAttribute("pages", pages);
                if (comment.getUserId() == user.getUserId()) {
                    request.getRequestDispatcher("WEB-INF/post.jsp").forward(request, response);
                } else {
                    response.sendError(403);
                }
            }
        } catch(NumberFormatException | EmptyResultDataAccessException e) {
            response.sendError(404);
        }
    }
}
