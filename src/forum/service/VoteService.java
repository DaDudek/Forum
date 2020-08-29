package forum.service;

import forum.dao.DAOFactory;
import forum.dao.VoteDAO;
import forum.model.Vote;
import forum.model.VoteType;

import java.sql.Timestamp;
import java.util.Date;

public class VoteService {

    public Vote createOrUpdateVote(int postId, int userId, boolean isPositive){
        DAOFactory factory = DAOFactory.getDAOFactory();
        VoteDAO voteDAO = factory.getVoteDAO();
        Vote vote = voteDAO.getVoteByIds(postId, userId);
        if (vote == null){
            return createVote(postId,userId,isPositive);
        }
        return updateVote(postId,userId,isPositive);
    }

    public Vote getVoteByIds(int postId, int userId){
        DAOFactory factory = DAOFactory.getDAOFactory();
        VoteDAO voteDAO = factory.getVoteDAO();
        return voteDAO.getVoteByIds(postId,userId);
    }
    public boolean deletePostAllComments(int postId){
        DAOFactory factory = DAOFactory.getDAOFactory();
        VoteDAO voteDAO = factory.getVoteDAO();
        return voteDAO.removeAllPostComments(postId);
    }

    private Vote createVote(int postId, int userId, boolean isPositive){
        Vote vote = new Vote();
        DAOFactory factory = DAOFactory.getDAOFactory();
        VoteDAO voteDAO = factory.getVoteDAO();
        vote.setPostId(postId);
        vote.setUserId(userId);
        vote.setPositive(isPositive);
        vote.setDate(new Timestamp(new Date().getTime()));
        if (isPositive){
            vote.setVoteType(VoteType.POSITIVE);
        }else {
            vote.setVoteType(VoteType.NEGATIVE);
        };
        return voteDAO.create(vote);
    }

    private Vote updateVote(int postId, int userId, boolean isPositive){
        DAOFactory factory = DAOFactory.getDAOFactory();
        VoteDAO voteDAO = factory.getVoteDAO();
        Vote vote = voteDAO.getVoteByIds(postId,userId);
        vote.setPositive(isPositive);
        vote.setVoteType(updateVoteType(vote));
        voteDAO.update(vote);
        return vote;
    }

    private VoteType updateVoteType(Vote vote){
        VoteType voteType = null;
        switch (vote.getVoteType()){
            case POSITIVE:
                if (vote.isPositive()){
                    voteType = VoteType.RETURNED;
                }
                else {
                    voteType = VoteType.NEGATIVE;
                }
                break;
            case NEGATIVE:
                if (vote.isPositive()){
                    voteType = VoteType.POSITIVE;
                }
                else {
                    voteType = VoteType.RETURNED;
                }
                break;
            case RETURNED:
                if (vote.isPositive()){
                    voteType = VoteType.POSITIVE;
                }
                else {
                    voteType = VoteType.NEGATIVE;
                }
        }
        return voteType;

    }
}
