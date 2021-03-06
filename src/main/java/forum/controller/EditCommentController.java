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
import java.util.List;

@WebServlet("/edit-comment")
public class EditCommentController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String message = request.getParameter("inputMessage");
        User user = (User) request.getSession().getAttribute("user");
        int postId = Integer.parseInt(request.getParameter("post-id"));
        int commentId = Integer.parseInt(request.getParameter("comment-id"));
        updateComment(user,message,postId,commentId);
        request.removeAttribute("commentEditingId");
        response.sendRedirect(request.getContextPath()+"/post?post-id="+request.getParameter("post-id")+"&page="+request.getParameter("page"));


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int commentId = Integer.parseInt(request.getParameter("comment-id"));
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
                    commentResponseHandler.setCommentsChildren(request,commentChild);
                }
                request.setAttribute("post", post);
                request.setAttribute("comments", commentsInPage);
                request.setAttribute("pageNumber", pageNumber);
                request.setAttribute("lastPageNumber", pages.size());
                request.setAttribute("pages", pages);
                if (comment.getUserId() == user.getUserId()) {
                    request.setAttribute("commentEditingId", commentId);
                    request.getRequestDispatcher("WEB-INF/post.jsp").forward(request, response);
                } else {
                    response.sendError(403);
                }
            }
        } catch(NumberFormatException | EmptyResultDataAccessException e) {
            response.sendError(404);
        }
    }

    private void updateComment(User user, String message, int postId, int commentId){
        CommentService commentService = new CommentService();
        InputLengthHandler lengthHandler = new InputLengthHandler();
        Comment comment = commentService.readComment(commentId);
        Comment updatedComment = new Comment();
        updatedComment.setAuthor(user.getUsername());
        updatedComment.setDate(comment.getDate());
        updatedComment.setCommentId(commentId);
        updatedComment.setMessage(lengthHandler.checkLengthAndReturnValue(message, InputLengthHandler.MESSAGE_SIZE));
        updatedComment.setPositiveVote(comment.getPositiveVote());
        updatedComment.setNegativeVote(comment.getNegativeVote());
        updatedComment.setPostId(postId);
        updatedComment.setUserId(user.getUserId());
        commentService.updateComment(updatedComment);
    }
}
