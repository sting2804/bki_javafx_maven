package desktopclient.gui.main;

import desktopclient.dao.ClientDao;
import desktopclient.entities.ISearchable;
import desktopclient.gui.changeclient.ChangeInfoWindowController;
import desktopclient.gui.newclient.NewClientWindowController;
import desktopclient.gui.searchuser.ClientSearchWindowController;
import desktopclient.entities.LoanInfo;
import desktopclient.entities.Person;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by sting on 10/29/14.
 */
public class MainModel {

    private Person person;
    private NewClientWindowController ncController;
    private ClientSearchWindowController csController;
    private ChangeInfoWindowController ciController;
    private ClientDao dao;

    public MainModel() {
        initComponents();
    }

    private void initComponents() {
        dao = new ClientDao();
    }

    public ArrayList<LoanInfo> getAllRecords(){
        return dao.getAllRecords();
    }

    public void callSearchWindow() throws NullPointerException {
        Stage primaryStage = new Stage();
        FXMLLoader  root;
        root = new FXMLLoader(getClass().getResource("/fxml/ClientSearchScene.fxml"));
        try {
            primaryStage.setScene(new Scene(root.load()));
        } catch (IOException ex) {
            Logger.getLogger(MainModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        csController = root.<ClientSearchWindowController>getController();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> primaryStage.close());
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();
        Person searchPerson = csController.getSearchablePerson();
        if(searchPerson == null){
            throw new NullPointerException();
        }
        //что-то сделать с объектом
    }
    
    public void callAddClientWindow() throws NullPointerException {
        Stage primaryStage = new Stage();
        FXMLLoader  root;
        root = new FXMLLoader(getClass().getResource("/fxml/NewClientScene.fxml"));
        try {
            primaryStage.setScene(new Scene(root.load()));
        } catch (IOException ex) {
            Logger.getLogger(MainModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        ncController = root.<NewClientWindowController>getController();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> primaryStage.close());
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();
        Person searchPerson = ncController.getPerson();
        if(searchPerson == null){
            throw new NullPointerException();
        }
        //что-то сделать с объектом
    }

    //вызов окна изменения информации о кредите клиента
    void callChangeClientWindow(ISearchable curInfo) throws Exception {
        final Stage primaryStage = new Stage();
        FXMLLoader root;
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        root = new FXMLLoader(getClass().getResource("/fxml/InfoScene.fxml"));
        try {
            primaryStage.setScene(new Scene(root.load()));
        } catch (IOException ex) {
            Logger.getLogger(MainModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        //передача информации в контроллер 
        ciController = root.<ChangeInfoWindowController>getController();
        ciController.setSomeObject(curInfo);
        primaryStage.setOnCloseRequest(event -> primaryStage.close());
        primaryStage.setResizable(false);
        primaryStage.showAndWait();
        //получение информации из контроллера
        LoanInfo newInfo = ciController.getSearchInfo();
        if(newInfo==null){
            throw new NullPointerException("");
        }
        if (curInfo.equals(newInfo)){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Данные совпадают и не будут изменениы");
            a.showAndWait();
        }
        else {
            //что-то сделать с обёектом
            dao.changeLoanInfo((LoanInfo) curInfo, newInfo);
        }
    }
}
