package forum.dao;

import forum.model.Vote;

public interface VoteDAO extends GenericDAO<Vote, Integer> {

    Vote getVoteByIds(int userId, int postId);

    boolean removeAllPostComments(int postId);

}

