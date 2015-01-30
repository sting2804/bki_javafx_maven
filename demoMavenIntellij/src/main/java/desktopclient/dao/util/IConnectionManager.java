package desktopclient.dao.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by sting on 16.01.15.
 */
public interface IConnectionManager {
    public Connection getConnection() throws SQLException;
}
