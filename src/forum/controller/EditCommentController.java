package forum.controller;

import forum.model.Comment;
import forum.model.Post;
import forum.model.User;
import forum.service.CommentService;
import forum.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@WebServlet("/edit-comment")
public class EditCommentController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = request.getParameter("inputMessage");
        User user = (User) request.getSession().getAttribute("user");
        CommentService commentService = new CommentService();
        Comment comment = commentService.readComment(Integer.parseInt(request.getParameter("comment-id")));
        Comment updatedComment = new Comment();
        updatedComment.setAuthor(user.getUsername());
        updatedComment.setDate(comment.getDate());
        updatedComment.setCommentId(Integer.parseInt(request.getParameter("comment-id")));
        updatedComment.setMessage(message);
        updatedComment.setPositiveVote(comment.getPositiveVote());
        updatedComment.setNegativeVote(comment.getNegativeVote());
        updatedComment.setPostId(Integer.parseInt(request.getParameter("post-id")));
        updatedComment.setUserId(user.getUserId());
        commentService.updateComment(updatedComment);
        request.removeAttribute("commentEditingId");
        response.sendRedirect(request.getContextPath()+"/post?post-id="+request.getParameter("post-id"));


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("commentEditingId",Integer.valueOf(request.getParameter("comment-id")));
        PostService postService = new PostService();
        CommentService commentService = new CommentService();
        Post post = postService.readPost(Integer.parseInt(request.getParameter("post-id")));
        List<Comment> comments = commentService.readPostAllComment(Integer.parseInt(request.getParameter("post-id")));
        request.setAttribute("post",post);
        request.setAttribute("comments",comments);
        request.getRequestDispatcher("WEB-INF/post.jsp").forward(request,response);

    }
}
