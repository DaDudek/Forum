package forum.dao;

public class MysqlDAOFactory extends DAOFactory{
    @Override
    public PostDAO getPostDAO() {
        return new PostDAOMysql();
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOMysql();
    }

    @Override
    public VoteDAO getVoteDAO() {
        return new VoteDAOMysql();
    }

    @Override
    public CommentDAO getCommentDAO() {
        return new CommentDAOMysql();
    }
}
