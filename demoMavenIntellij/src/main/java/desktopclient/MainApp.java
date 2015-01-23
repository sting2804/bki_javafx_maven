package desktopclient;

import desktopclient.bussines.SimpleServiceFactory;
import desktopclient.dao.IBkiDao;
import desktopclient.dao.JdbcDao;
import desktopclient.gui.main.MainModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {
    private IBkiDao dapType;
    private MainModel mainModel;
    @Override
    public void start(Stage stage) throws Exception {
        dapType = new JdbcDao();
        mainModel = new MainModel(new SimpleServiceFactory(dapType));
        mainModel.callMainWindow(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
