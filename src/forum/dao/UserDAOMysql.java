package forum.dao;

import forum.model.Role;
import forum.model.User;
import forum.util.ConnectionProvider;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOMysql implements UserDAO {
    private static final String CREATE =
            "INSERT INTO user(username, email, account_active, password) VALUES(:username, :email, :accountActive, :password)";
    private static final String SET_ROLE =
            "INSERT INTO user_role(username) VALUES(:username)";
    private static final String READ =
            "SELECT user_id, user.username, email, password, account_active, role_name FROM user INNER JOIN user_role ON user.username = user_role.username WHERE user_id = :userId";
    private static final String READ_BY_USERNAME =
            "SELECT user_id, user.username, email, password, account_active, role_name FROM user INNER JOIN user_role ON user.username = user_role.username WHERE user.username = :username";



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
        SqlParameterSource parameterSource = new MapSqlParameterSource("userId", primaryKey);
        return template.queryForObject(READ, parameterSource, new UserRowMapper());

    }

    @Override
    public boolean update(User updateObject) {
        return false;
    }

    @Override
    public boolean delete(Integer key) {
        return false;
    }

    public User readByUsername(String username){
        SqlParameterSource parameterSource = new MapSqlParameterSource("username", username);
        return template.queryForObject(READ_BY_USERNAME,parameterSource,new UserRowMapper());
    }

    private void setRole(User user){
        SqlParameterSource source = new BeanPropertySqlParameterSource(user);
        template.update(SET_ROLE, source);
    }

    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(resultSet.getInt("user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setAccountActive(resultSet.getBoolean("account_active"));
            user.setRole(Role.valueOf(resultSet.getString("role_name").toUpperCase()));
            return user;
        }

    }
}
