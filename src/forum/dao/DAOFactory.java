package forum.dao;

import java.util.InputMismatchException;

public abstract class DAOFactory {
    public static final int MYSQL_FACTORY = 1;

    public abstract PostDAO getPostDAO();

    public abstract UserDAO getUserDAO();

    public abstract VoteDAO getVoteDAO();

    public abstract CommentDAO getCommentDAO();

    public abstract CommentVoteDAO getCommentVoteDAO();

    public static DAOFactory getDAOFactory() {
        DAOFactory factory = null;
        try {
            factory = getDAOFactory(MYSQL_FACTORY);
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
        return factory;
    }

    private static DAOFactory getDAOFactory(int type){
        switch (type) {
            case MYSQL_FACTORY:
                return new MysqlDAOFactory();
            default:
                throw new InputMismatchException();
        }
    }
}

