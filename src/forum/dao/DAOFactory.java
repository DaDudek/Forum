package forum.dao;

public abstract class DAOFactory {

    public abstract PostDAO getPostDAO();

    public abstract UserDAO getUserDAO();

    public abstract VoteDAO getVoteDAO();

    public abstract CommentDAO getCommentDAO();
}
