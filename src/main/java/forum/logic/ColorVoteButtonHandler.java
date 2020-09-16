package forum.logic;

import forum.model.Comment;
import forum.model.Post;
import forum.model.User;
import forum.service.CommentVoteService;
import forum.service.VoteService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ColorVoteButtonHandler<T> {

    public void initPublicationVoteStatus(HttpServletRequest request, List<T> publications){
        for (int i = 0; i <publications.size() ; i++) {
            T publication = publications.get(i);
            User user = (User) request.getSession().getAttribute("user");
            int userId = -1;
            if (user != null){
                userId = user.getUserId();
            }
            if (publication instanceof Post){
                VoteService voteService = new VoteService();
                Post post = (Post) publication;
                post.setPostVoteType(voteService.getUserPostVoteType(post.getPostId(),userId));
            }
            else{
                CommentVoteService commentVoteService = new CommentVoteService();
                Comment comment = (Comment) publication;
                comment.setCommentVoteType(commentVoteService.getUserCommentVoteType(comment.getCommentId(),userId));
            }
        }
    }
}
