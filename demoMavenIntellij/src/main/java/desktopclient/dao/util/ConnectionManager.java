package desktopclient.dao.util;

import com.sybase.jdbc4.jdbc.SybDataSource;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Properties;

/**
 *
 */
public class ConnectionManager {
    private static Connection connection;
    private static Properties prop;

    private String url;
    private String userid;
    private String password;
    private String driver;

    private static final Logger log = Logger.getLogger(ConnectionManager.class);

    private ConnectionManager() {
    }

    private static void initilize() {
        prop = new Properties();
        try (InputStream is = Files.newInputStream(Paths.get(ConnectionManager.class.getResource("/properties/connectionProperties.properties").toURI()))) {
            prop.load(is);
        } catch (IOException | URISyntaxException e) {
            log.error(e.getMessage());
        }
    }

    public ConnectionManager(String driver, String url, String userid, String password) {
        this.driver = driver;
        this.url = url;
        this.userid = userid;
        this.password = password;
    }

    public static Connection getConnection() {
        initilize();
        if (connection == null) {
            SybDataSource dataSource = new SybDataSource();
            dataSource.setUser(prop.getProperty("jdbc.user"));
            dataSource.setPassword(prop.getProperty("jdbc.password"));
            dataSource.setServerName(prop.getProperty("jdbc.serverName"));
            dataSource.setPortNumber(Integer.parseInt(prop.getProperty("jdbc.portNumber")));
            dataSource.setDatabaseName(prop.getProperty("jdbc.database"));
            dataSource.setAPPLICATIONNAME("javafx_bki");
            try {
                dataSource.setLoginTimeout(5);
                connection = dataSource.getConnection();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return connection;
    }
}
