package forum.controller;

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
        PostService postService = new PostService();
        Post post = postService.readPost(Integer.parseInt(request.getParameter("post-id")));
        Post updatedPost = new Post();
        updatedPost.setTitle(title);
        updatedPost.setDescription(description);
        updatedPost.setNegativeVote(post.getNegativeVote());
        updatedPost.setPositiveVote(post.getPositiveVote());
        updatedPost.setUser(new User(user));
        updatedPost.setDate(new Timestamp(new Date().getTime()));
        updatedPost.setPostId(Integer.parseInt(request.getParameter("post-id")));
        updatedPost.setMessage(message);

        postService.updatePost(updatedPost);
        request.removeAttribute("isEditing");
        response.sendRedirect(request.getContextPath()+"/post?post-id="+request.getParameter("post-id"));
        }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PostService postService = new PostService();
            CommentService commentService = new CommentService();
            Post post = postService.readPost(Integer.parseInt(request.getParameter("post-id")));
            User user = (User) request.getSession().getAttribute("user");
            List<Comment> comments = commentService.readPostAllComment(Integer.parseInt(request.getParameter("post-id")));
            request.setAttribute("post", post);
            request.setAttribute("comments", comments);
            if (user.getUserId() == post.getUser().getUserId()){
                request.setAttribute("isEditing", "true");
                request.getRequestDispatcher("WEB-INF/post.jsp").forward(request, response);
            }
            else {
                response.sendError(403);
            }
        }
        catch (NumberFormatException | EmptyResultDataAccessException e){
            response.sendError(404);
        }
    }
}
