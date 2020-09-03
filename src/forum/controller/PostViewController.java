package forum.controller;

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
            PostService postService = new PostService();
            Post post = postService.readPost(Integer.parseInt(request.getParameter("post-id")));
            CommentService commentService = new CommentService();
            List<Comment> comments = commentService.readPostAllComment(Integer.parseInt(request.getParameter("post-id")));
            request.setAttribute("post", post);
            request.setAttribute("comments", comments);
            request.getSession().setAttribute("url","/post?post-id="+request.getParameter("post-id"));
            request.getRequestDispatcher("WEB-INF/post.jsp").forward(request, response);
        } catch (EmptyResultDataAccessException e){
            response.sendError(404);
        }
    }
}
