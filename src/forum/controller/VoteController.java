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
        String responseAdress = "";
        if (user != null){
            int userId = user.getUserId();
            int postId = Integer.parseInt(request.getParameter("post_id"));
            boolean isPositive = Boolean.parseBoolean(request.getParameter("is_positive"));
            updateVote(postId,userId, isPositive);
            if (request.getParameter("post-page") != null) {
                responseAdress = request.getContextPath() + "/post?post-id="+postId;
            }
            else {
                responseAdress = request.getContextPath()+"/";
            }
        }
        else {
            responseAdress = request.getContextPath()+"/";
        }
        response.sendRedirect(responseAdress);
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
        Post updatedPost = choosePostUpdateOption(post, pastVote, newVote);
        postService.updatePost(updatedPost);
    }

    private Post choosePostUpdateOption(Post post, Vote pastVote, Vote newVote){
        if (pastVote == null && newVote != null){
            return updatePostWithNullVote(post, newVote.isPositive());
        }
        switch (pastVote.getVoteType()){
            case POSITIVE:
                return updatePostWithPositiveVote(post, newVote.isPositive());
            case RETURNED:
                return updatePostWithReturnedVote(post, newVote.isPositive());
            case NEGATIVE:
                return updatePostWithNegativeVote(post,newVote.isPositive());
        }
        return null;
    }

    private Post updatePostWithNullVote(Post post, boolean isPositive){
        if (isPositive){
            post.setPositiveVote(post.getPositiveVote() + 1);
        }else {
            post.setNegativeVote(post.getNegativeVote() + 1);
        }
        return post;
    }

    private Post updatePostWithPositiveVote(Post post, boolean isPositive){
        if (isPositive){
            post.setPositiveVote(post.getPositiveVote() - 1);
        }else {
            post.setPositiveVote(post.getPositiveVote() - 1);
            post.setNegativeVote(post.getNegativeVote() + 1);
        }
        return post;
    }

    private Post updatePostWithNegativeVote(Post post, boolean isPositive){
        if (isPositive){
            post.setPositiveVote(post.getPositiveVote() + 1);
            post.setNegativeVote(post.getNegativeVote() - 1);
        }
        else {
            post.setNegativeVote(post.getNegativeVote() - 1);
        }
        return post;
    }

    private Post updatePostWithReturnedVote(Post post, boolean isPositive){
        if (isPositive){
            post.setPositiveVote(post.getPositiveVote() + 1);
        }
        else {
            post.setNegativeVote(post.getNegativeVote() + 1);
        }
        return post;
    }
}

