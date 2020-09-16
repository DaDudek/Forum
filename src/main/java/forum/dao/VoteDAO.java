package forum.dao;

import forum.model.Vote;
import forum.model.VoteType;

public interface VoteDAO extends GenericDAO<Vote, Integer> {

    Vote getVoteByIds(int userId, int postId);

    boolean removeAllPostVotes(int postId);

    String getUserPostVoteType(int postId, int userId);

}

