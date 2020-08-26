package forum.service;

import forum.dao.DAOFactory;
import forum.dao.VoteDAO;
import forum.model.Vote;

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

    private Vote createVote(int postId, int userId, boolean isPositive){
        Vote vote = new Vote(postId,userId,isPositive,new Timestamp(new Date().getTime()));
        DAOFactory factory = DAOFactory.getDAOFactory();
        VoteDAO voteDAO = factory.getVoteDAO();
        return voteDAO.create(vote);
    }

    private Vote updateVote(int postId, int userId, boolean isPositive){
        DAOFactory factory = DAOFactory.getDAOFactory();
        VoteDAO voteDAO = factory.getVoteDAO();
        Vote vote = voteDAO.getVoteByIds(postId,userId);
        if (vote != null){
            vote.setPositive(isPositive);
            voteDAO.update(vote);
        }
        return vote;
    }

}
