package desktopclient.dao.util;

import java.sql.Connection;

/**
 * Created by sting on 16.01.15.
 */
public interface IConnector {
    Connection getConnection();
}
