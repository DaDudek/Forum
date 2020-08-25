package forum.dao;

import forum.model.User;
import forum.util.ConnectionProvider;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;

public class UserDAOMysql implements UserDAO {
    private static final String CREATE =
            "INSERT INTO user(username, email, account_active, password) VALUES(:username, :email, :accountActive, :password)";
    private static final String SET_ROLE =
            "INSERT INTO user_role(username) VALUES(:username)";



    private NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());


    @Override
    public User create(User user) {
        User newUser = new User(user);
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource source = new BeanPropertySqlParameterSource(user);
        int update = template.update(CREATE, source, holder);
        if (update == 1){
            newUser.setUserId(holder.getKey().intValue());
            setRole(user);
        }
        return newUser;
    }

    @Override
    public User read(Integer primaryKey) {
        return null;
    }

    @Override
    public boolean update(User updateObject) {
        return false;
    }

    @Override
    public boolean delete(Integer key) {
        return false;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    private void setRole(User user){
        SqlParameterSource source = new BeanPropertySqlParameterSource(user);
        template.update(SET_ROLE, source);
    }
}
