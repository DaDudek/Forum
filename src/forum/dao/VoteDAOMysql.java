package forum.dao;

import forum.model.Vote;

import java.util.List;

public class VoteDAOMysql implements VoteDAO{
    @Override
    public Vote create(Vote newObject) {
        return null;
    }

    @Override
    public Vote read(Integer primaryKey) {
        return null;
    }

    @Override
    public boolean update(Vote updateObject) {
        return false;
    }

    @Override
    public boolean delete(Integer key) {
        return false;
    }

}
