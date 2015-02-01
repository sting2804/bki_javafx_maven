package desktopclient;

import desktopclient.bussines.trash.SimpleServiceFactory;
import desktopclient.dao.IBkiDao;
import desktopclient.dao.JdbcDao;
import desktopclient.gui.main.MainModel;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.log4j.xml.DOMConfigurator;


public class MainApp extends Application {
    private IBkiDao daoType;
    private MainModel mainModel;
    @Override
    public void start(Stage stage) throws Exception {
        daoType = new JdbcDao();
        mainModel = new MainModel(new SimpleServiceFactory(daoType));
        mainModel.callMainWindow(stage);
    }

    public static void main(String[] args) {
        String logPropertyPath = "/Users/sting/GitHub/bki_javafx_maven/demoMavenIntellij/src/main/resources/log4j.xml";
        DOMConfigurator.configureAndWatch(logPropertyPath);
        launch(args);
    }

}
