
package desktopclient.dao.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * author sting2804
 */
public class SimpleConnectionFactory implements IConnectionFactory{
    IConnector connector;
    Connection connection;

    @Override
    public Connection createConnection() throws SQLException {
        connector = new AnyConnector();
        connection = connector.getConnection();
        return connection;
    }
}
