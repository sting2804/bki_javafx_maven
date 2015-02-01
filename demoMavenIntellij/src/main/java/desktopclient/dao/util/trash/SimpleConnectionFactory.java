
package desktopclient.dao.util.trash;

import desktopclient.dao.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * author sting2804
 */
public class SimpleConnectionFactory implements IConnectionFactory {
    Connection connection;

    @Override
    public Connection createConnection() throws SQLException {
        connection = ConnectionManager.getConnection();
        return connection;
    }
}
