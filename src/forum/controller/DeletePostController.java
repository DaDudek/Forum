package forum.controller;

import forum.model.Post;
import forum.model.User;
import forum.service.PostService;
import forum.service.VoteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete-post")
public class DeletePostController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        PostService postService = new PostService();
        VoteService voteService = new VoteService();
        if (user != null && request.getParameter("post_id") != null) {
            int userId = user.getUserId();
            int postId = Integer.parseInt(request.getParameter("post_id"));
            Post post = postService.readPost(postId);
            if (post != null && post.getUser().getUserId() == userId) {
                voteService.deletePostAllComments(postId);
                postService.deletePost(postId);
            }
        }
        response.sendRedirect(request.getContextPath() + "/");
    }
}
