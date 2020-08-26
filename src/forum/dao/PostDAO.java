package forum.dao;

import forum.model.Post;
import forum.model.PostSort;

import java.util.List;

public interface PostDAO extends GenericDAO<Post, Integer>{

    //place for more specific method than CRUD
    List<Post> getAll(PostSort postSort);

}
