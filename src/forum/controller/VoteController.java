package forum.controller;

import forum.model.Post;
import forum.model.User;
import forum.model.Vote;
import forum.service.PostService;
import forum.service.VoteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vote")
public class VoteController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null){
            int userId = user.getUserId();
            int postId = Integer.parseInt(request.getParameter("post_id"));
            boolean isPositive = Boolean.parseBoolean(request.getParameter("is_positive"));
            updateVote(postId,userId, isPositive);
        }
        response.sendRedirect(request.getContextPath()+"/");
    }

    private void updateVote(int postId, int userId, boolean isPositive){
        VoteService voteService = new VoteService();
        Vote pastVote = voteService.getVoteByIds(postId,userId);
        Vote newVote = voteService.createOrUpdateVote(postId,userId, isPositive);
        updatePost(postId, pastVote, newVote);

    }

    private void updatePost(int postId, Vote pastVote, Vote newVote){
        PostService postService = new PostService();
        Post post =postService.readPost(postId);
        System.out.println(post);
        Post updatedPost = null;
        if (pastVote == null && newVote != null){
            updatedPost = addVote(post, newVote.isPositive());
        }else {
            updatedPost = removeVote(post, pastVote.isPositive());
            updatedPost = addVote(updatedPost, newVote.isPositive());
        }
        postService.updatePost(updatedPost);
    }

    private Post addVote(Post post, boolean isPositive) {
        Post newPost = new Post(post);
        if(isPositive) {
            newPost.setPositiveVote(newPost.getPositiveVote() + 1);
        } else{
            newPost.setNegativeVote(newPost.getNegativeVote() + 1);
        }
        return newPost;
    }

    private Post removeVote(Post post, boolean isPositive) {
        Post newPost = new Post(post);
        if(isPositive) {
            newPost.setPositiveVote(newPost.getPositiveVote() - 1);
        } else{
            newPost.setNegativeVote(newPost.getNegativeVote() - 1);
        }
        return newPost;
    }
}

