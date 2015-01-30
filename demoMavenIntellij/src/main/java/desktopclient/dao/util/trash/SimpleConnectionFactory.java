
package desktopclient.dao.util.trash;

import desktopclient.dao.util.ConnectionManager;
import desktopclient.dao.util.IConnectionManager;
import desktopclient.dao.util.trash.IConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * author sting2804
 */
public class SimpleConnectionFactory implements IConnectionFactory {
    IConnectionManager connector;
    Connection connection;

    @Override
    public Connection createConnection() throws SQLException {
        connector = new ConnectionManager();
        connection = connector.getConnection();
        return connection;
    }
}
