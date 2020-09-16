package forum.dao;

import forum.model.CommentVote;

public interface CommentVoteDAO extends GenericDAO<CommentVote, Integer> {

    CommentVote getCommentVoteByIds(int commentId, int userId);

    boolean removeAllCommentVotes(int commentId);

    String getUserPostVoteType(int postId, int userId);


}
