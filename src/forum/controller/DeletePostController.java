package forum.controller;

import forum.model.Comment;
import forum.model.Post;
import forum.model.Role;
import forum.model.User;
import forum.service.CommentService;
import forum.service.CommentVoteService;
import forum.service.PostService;
import forum.service.VoteService;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.List;

@WebServlet("/delete-post")
public class DeletePostController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User user = (User) request.getSession().getAttribute("user");
            PostService postService = new PostService();

            int userId = user.getUserId();
            int postId = Integer.parseInt(request.getParameter("post_id"));

            Post post = postService.readPost(postId);

            if ((post.getUser().getUserId() == userId) || (user.getRole().equals(Role.valueOf("ADMIN"))))
            {
                deletePost(postId);
                response.sendRedirect(request.getContextPath() + "/");
            }else
                {
                response.sendError(403);
            }
        } catch (NumberFormatException | EmptyResultDataAccessException e) {
            response.sendError(404);
        }
    }

    private void deletePost(int postId) {
        VoteService voteService = new VoteService();
        CommentService commentService = new CommentService();
        CommentVoteService commentVoteService = new CommentVoteService();
        PostService postService = new PostService();


        List<Comment> commentList = commentService.readPostAllComments(postId);
        for (int i = 0; i < commentList.size(); i++)
        {
            commentVoteService.deleteCommentAllVotes(commentList.get(i).getCommentId());
        }
        commentService.deleteAllPostComment(postId);
        voteService.deletePostAllVotes(postId);
        postService.deletePost(postId);
    }
}
