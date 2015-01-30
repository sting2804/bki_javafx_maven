package desktopclient.dao.util;

import com.sybase.jdbc4.jdbc.SybDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by sting on 16.01.15.
 */
public class ConnectionManager implements IConnectionManager{
    private static Connection connection;
    private static Properties prop;

    private String url;
    private String userid;
    private String password;
    private String driver;


    public ConnectionManager() {
        prop = new Properties();
        try (InputStream is = Files.newInputStream(Paths.get(this.getClass().getResource("/properties/connectionProperties.prop").toURI()))) {
            prop.load(is);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public ConnectionManager(String driver, String url, String userid, String password) {
        this.driver = driver;
        this.url = url;
        this.userid = userid;
        this.password = password;
        //createConnection();
    }

    /*private void createConnection(Properties properties) throws DriverNotFoundException {
        this.driver = properties.getProperty("jdbc.drivers");
        if (driver == null) throw new DriverNotFoundException();
        this.url = properties.getProperty("jdbc.url");
        this.userid = properties.getProperty("jdbc.userid");
        this.password = properties.getProperty("jdbc.password");
        createConnection();
    }
    private void createConnection(){
        try {
            Class.forName(driver);
            System.setProperty("jdbc.drivers", driver);
            //dataSource = new DriverManagerDataSource(url,userid,password);
            connection = DriverManager.getConnection(url, userid, password);
        }
        catch (Exception e) {
            System.out.println("ololo vsyo ploho");
            e.printStackTrace();
        }
    }*/

    /*private Properties readConnectionProperties(){
        Properties properties = new Properties();
        try(InputStream fis = Files.newInputStream(Paths.get("/Users/sting/GitHub/bki_javafx_maven/demoMavenIntellij/src/main/java/desktopclient/dao/util/database.propetries"))) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }*/

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null) {
            SybDataSource dataSource = new SybDataSource();
            dataSource.setUser(prop.getProperty("jdbc.user"));
            dataSource.setPassword(prop.getProperty("jdbc.password"));
            dataSource.setServerName(prop.getProperty("jdbc.serverName"));
            dataSource.setPortNumber(Integer.parseInt(prop.getProperty("jdbc.portNumber")));
            dataSource.setDatabaseName(prop.getProperty("jdbc.database"));
            dataSource.setAPPLICATIONNAME("javafx_bki");
            connection = dataSource.getConnection();
        }
        return connection;
    }
}
