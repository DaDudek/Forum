package forum.controller;

import forum.logic.CommentResponseHandler;
import forum.logic.InputLengthHandler;
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
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/edit-post")
public class EditPostController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("inputTitle");
        String description = request.getParameter("inputDescription");
        String message = request.getParameter("inputMessage");
        User user = (User) request.getSession().getAttribute("user");
        int postId = Integer.parseInt(request.getParameter("post-id"));
        updatePost(title,description,message,user,postId);
        request.removeAttribute("isEditing");
        response.sendRedirect(request.getContextPath()+"/post?post-id="+request.getParameter("post-id"));
        }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int postId = Integer.parseInt(request.getParameter("post-id"));
            PostService postService = new PostService();
            CommentService commentService = new CommentService();
            User user = (User) request.getSession().getAttribute("user");
            PaginationHandler<Comment> paginationHandler = new PaginationHandler<>();
            CommentResponseHandler commentResponseHandler = new CommentResponseHandler();

            Post post = postService.readPost(postId);
            List<Comment> comments = commentService.readPostAllRootComments(postId);

            int pageNumber = paginationHandler.initPageNumber(request.getParameter("page"));
            List<Integer> pages = paginationHandler.setPagesList(comments);

            if (pageNumber > pages.size() || pageNumber <= 0) {
                response.sendError(404);
            } else {
                List<Comment> commentsInPage = paginationHandler.setPublicationOnPage(comments, pageNumber);
                for (Comment comment : commentsInPage) {
                    commentResponseHandler.setCommentsChildren(comment);
                }
                request.setAttribute("post", post);
                request.setAttribute("comments", commentsInPage);
                request.setAttribute("pageNumber", pageNumber);
                request.setAttribute("lastPageNumber", pages.size());
                request.setAttribute("pages", pages);


                if (user.getUserId() == post.getUser().getUserId()) {
                    request.setAttribute("isEditing", "true");
                    request.getRequestDispatcher("WEB-INF/post.jsp").forward(request, response);
                } else {
                    response.sendError(403);
                }
            }
        } catch (NumberFormatException | EmptyResultDataAccessException e){
            response.sendError(404);
        }
    }

    private void updatePost(String title, String description, String message, User user, int postId){
        PostService postService = new PostService();
        Post post = postService.readPost(postId);
        InputLengthHandler inputLengthHandler = new InputLengthHandler();
        Post updatedPost = new Post();
        updatedPost.setTitle(inputLengthHandler.checkLengthAndReturnValue(title,InputLengthHandler.TITLE_SIZE));
        updatedPost.setDescription(inputLengthHandler.checkLengthAndReturnValue(description, InputLengthHandler.DESCRIPTION_SIZE));
        updatedPost.setNegativeVote(post.getNegativeVote());
        updatedPost.setPositiveVote(post.getPositiveVote());
        updatedPost.setUser(new User(user));
        updatedPost.setDate(new Timestamp(new Date().getTime()));
        updatedPost.setPostId(postId);
        updatedPost.setMessage(inputLengthHandler.checkLengthAndReturnValue(message, InputLengthHandler.MESSAGE_SIZE));

        postService.updatePost(updatedPost);
    }
}
