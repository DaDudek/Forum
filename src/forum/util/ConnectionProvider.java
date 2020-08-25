package forum.util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class ConnectionProvider {

    private static DataSource dataSource;

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            try {
                Context initialContext = new InitialContext();
                Context envContext = (Context) initialContext
                        .lookup("java:comp/env");
                DataSource ds = (DataSource) envContext.lookup("jdbc/forum");
                dataSource = ds;
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }

        return dataSource;
    }
}