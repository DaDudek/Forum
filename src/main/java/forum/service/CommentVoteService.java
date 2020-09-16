package forum.service;

import forum.dao.CommentVoteDAO;
import forum.dao.DAOFactory;
import forum.dao.VoteDAO;
import forum.model.CommentVote;
import forum.model.VoteType;

import java.sql.Timestamp;
import java.util.Date;

public class CommentVoteService {


    public String getUserCommentVoteType(int commentId, int userId){
        DAOFactory factory = DAOFactory.getDAOFactory();
        CommentVoteDAO commentVoteDAO = factory.getCommentVoteDAO();
        return commentVoteDAO.getUserPostVoteType(commentId,userId);
    }

    public CommentVote createOrUpdateCommentVote(int commentId, int userId, boolean isPositive){
        DAOFactory factory = DAOFactory.getDAOFactory();
        CommentVoteDAO commentVoteDAO = factory.getCommentVoteDAO();
        CommentVote commentVote = commentVoteDAO.getCommentVoteByIds(commentId,userId);
        if (commentVote == null){
            return createCommentVote(commentId, userId,isPositive);
        }
        return updateCommentVote(commentId, userId,isPositive);
    }

    public CommentVote getCommentVoteByIds(int commentId, int userId){
        DAOFactory factory = DAOFactory.getDAOFactory();
        CommentVoteDAO commentVoteDAO = factory.getCommentVoteDAO();
        return commentVoteDAO.getCommentVoteByIds(commentId,userId);
    }
    public boolean deleteCommentAllVotes(int commentId){
        DAOFactory factory =DAOFactory.getDAOFactory();
        CommentVoteDAO commentVoteDAO = factory.getCommentVoteDAO();
        return commentVoteDAO.removeAllCommentVotes(commentId);
    }

    private CommentVote createCommentVote(int commentId, int userId, boolean isPositive){
        CommentVote commentVote = new CommentVote();
        DAOFactory factory = DAOFactory.getDAOFactory();
        CommentVoteDAO commentVoteDAO = factory.getCommentVoteDAO();
        commentVote.setCommentId(commentId);
        commentVote.setUserId(userId);
        commentVote.setPositive(isPositive);
        commentVote.setDate(new Timestamp(new Date().getTime()));
        if (isPositive){
            commentVote.setVoteType(VoteType.POSITIVE);
        } else {
            commentVote.setVoteType(VoteType.NEGATIVE);
        }
        return commentVoteDAO.create(commentVote);
    }

    private CommentVote updateCommentVote(int commentId, int userId, boolean isPositive){
        DAOFactory factory = DAOFactory.getDAOFactory();
        CommentVoteDAO commentVoteDAO = factory.getCommentVoteDAO();
        CommentVote commentVote = commentVoteDAO.getCommentVoteByIds(commentId,userId);
        commentVote.setPositive(isPositive);
        commentVote.setVoteType(updateCommentVoteType(commentVote));
        commentVoteDAO.update(commentVote);
        return commentVote;
    }

    private VoteType updateCommentVoteType(CommentVote commentVote){
        VoteType voteType = null;
        switch (commentVote.getVoteType()){
            case POSITIVE:
                if (commentVote.isPositive()){
                    voteType = VoteType.RETURNED;
                }
                else {
                    voteType = VoteType.NEGATIVE;
                }
                break;
            case NEGATIVE:
                if (commentVote.isPositive()){
                    voteType = VoteType.POSITIVE;
                }
                else {
                    voteType = VoteType.RETURNED;
                }
                break;
            case RETURNED:
                if (commentVote.isPositive()){
                    voteType = VoteType.POSITIVE;
                }
                else {
                    voteType = VoteType.NEGATIVE;
                }
        }
        return voteType;
        }
}

