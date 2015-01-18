
package desktopclient.dao.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Novomlinov Aleksandr
 */
public interface IConnectionFactory {
    Connection createConnection() throws SQLException;
}
