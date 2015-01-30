package desktopclient.gui.main;

import desktopclient.bussines.ILoanInfoService;
import desktopclient.bussines.trash.IPersonService;
import desktopclient.bussines.IServiceFactory;
import desktopclient.entities.*;
import desktopclient.gui.changeclient.ChangeInfoWindowController;
import desktopclient.gui.directories.BankWindowController;
import desktopclient.gui.directories.CurrencyWindowController;
import desktopclient.gui.trash.newclient.NewClientWindowController;
import desktopclient.gui.searchuser.ClientSearchWindowController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Класс для связи контроллера главного окна с остальными окнами,
 * включает обработку некоторых событий по открытию новых окон и передаче туда неких данных
 */
public class MainModel {

    /**
     * ncController контроллер окна создания нового клиента
     * csController контроллер окна писка клиента
     * ciController контроллен окна изменения и добавления информации клиенту
     * mwController контроллер главного окна
     * bwController контроллер окна добавления банка
     * cwController контроллер окна добавления валюты
     */
    private NewClientWindowController ncController;
    private ClientSearchWindowController csController;
    private ChangeInfoWindowController ciController;
    private MainWindowController mwController;
    private BankWindowController bwController;
    private CurrencyWindowController cwController;
    /**
     * loanInfoService объект для связи с кредитной частью дао
     * personService объект для связи с пользовательской частью дао
     * serviceFactory объект для получения конкретной реализации кредитных и клиентских сервисов
     */
    private ILoanInfoService loanInfoService;
    private IPersonService personService;
    private IServiceFactory serviceFactory;
    /**
     * foundInfo хранит информацию об искомом клиенте
     */
    private List <LoanInfo> foundInfo;

    public MainModel() {
    }

    public MainModel( IServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
        initComponents();
    }

    private void initComponents() {
        loanInfoService = serviceFactory.createLoanService();
        personService = serviceFactory.createPersonService();
    }

    /**
     * callMainWindow() вызывается из загрузчика для откытия окна
     */
    public void callMainWindow(Stage primaryStage){
        Scene scene = null;
        FXMLLoader  root;
        root = new FXMLLoader(getClass().getResource("/fxml/MainScene.fxml"));
        try {
             scene = new Scene(root.load());
            primaryStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(MainModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        mwController = root.<MainWindowController>getController();
        mwController.setMainModel(this);
        scene.getStylesheets().add("/styles/Styles.css");
        primaryStage.setTitle("BKI on JavaFX and Maven");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> Platform.exit());
    }

    public List<LoanInfo> getAllRecords(){
        return loanInfoService.listAll();
    }

    /**
     * callSearchWindow() открывает окно поиска
     * @throws NullPointerException выбрасывает в случае не установки ни одного поля для поиска
     */
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
        foundInfo = loanInfoService.listSpecificInfo(searchPerson);
    }

    /**
     * callNewClientWindow() открывает окно создания нового клиента
     * @throws NullPointerException
     */
    public void callNewClientWindow() throws NullPointerException {
        Stage primaryStage = new Stage();
        FXMLLoader  root;
        root = new FXMLLoader(getClass().getResource("/fxml/trash/NewClientScene.fxml"));
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
        Person newPerson = ncController.getPerson();
        if(newPerson != null){
            //что-то сделать с объектом
            Alert a;
            if(loanInfoService.isClientExists(newPerson)){
                a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("Клиент существует");
                a.showAndWait();
            }
            /*else if (loanInfoService.add(newPerson)){
                a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("Клиент добавлен");
                a.showAndWait();
            }
            else {
                a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Клиент не добавлен");
                a.showAndWait();
            }*/
        }

    }

    /**
     * callChangeClientWindow() открывает окно  изменения информации о кредитах выбранного клиента
     * @param curInfo выбранная сущность из таблицы
     * @throws Exception
     */
    public void callChangeClientWindow(LoanInfo curInfo) throws Exception {
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
        ciController.setMainModel(this);
        ciController.setCurrencyMap(getCurrencyMap());
        ciController.setBankMap(getBankMap());
        ciController.setLoanInfo(curInfo);
        primaryStage.setOnCloseRequest(event -> primaryStage.close());
        primaryStage.setResizable(false);
        primaryStage.showAndWait();
        //получение информации из контроллера
        LoanInfo newInfo = ciController.getNewInfo();
        if(newInfo!=null){
            if (curInfo.equals(newInfo)){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Данные совпадают и не будут изменениы");
                a.showAndWait();
            }
            else {
                //что-то сделать с обёектом
                loanInfoService.modify(newInfo);
            }
        }
    }
    /**
     * callAddInfoWindow() открывает окно добавления информации о кредитах выбранного клиента
     * @param curInfo выбранная сущность из таблицы
     * @throws Exception
     */
    public void callAddInfoWindow(Person curInfo) throws Exception {
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
        ciController.setMainModel(this);
        ciController.setCurrencyMap(getCurrencyMap());
        ciController.setBankMap(getBankMap());
        ciController.setPerson(curInfo);
        primaryStage.setOnCloseRequest(event -> primaryStage.close());
        primaryStage.setResizable(false);
        primaryStage.showAndWait();
        //получение информации из контроллера
        LoanInfo newInfo = ciController.getNewInfo();
        if(newInfo!=null){
            if (curInfo.equals(newInfo)){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Данные совпадают и не будут изменениы");
                a.showAndWait();
            }
            else {
                //что-то сделать с обёектом
                loanInfoService.modify(newInfo);
            }
        }
    }


    public List<LoanInfo> getFoundInfo() {
        return foundInfo;
    }
    public Map<String,String> getCurrencyMap(){
        return loanInfoService.getCurrenciesMap();
    }
    public Map<String,String> getBankMap(){
        return loanInfoService.getBanksMap();
    }

    public Bank callNewBankWindow(String bankCode) {
        final Stage primaryStage = new Stage();
        FXMLLoader root;
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        root = new FXMLLoader(getClass().getResource("/fxml/BankScene.fxml"));
        try {
            primaryStage.setScene(new Scene(root.load()));
        } catch (IOException ex) {
            Logger.getLogger(MainModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        //передача информации в контроллер
        bwController = root.<BankWindowController>getController();
        bwController.setMainModel(this);
        bwController.setScreenForms(bankCode);
        primaryStage.setOnCloseRequest(event -> primaryStage.close());
        primaryStage.setResizable(false);
        primaryStage.showAndWait();
        Alert alert;
        Bank b = bwController.getBank();
        if(b!=null) {
            insertBankIntoDB(b);
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Банк добавлен");
            alert.showAndWait();
        }
        else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Банк не добавлен");
            alert.showAndWait();
        }
        return b;
    }

    private void insertBankIntoDB(Bank bank) {
        loanInfoService.addBank(bank);
    }
    private void insertCurrencyIntoDB(Currency currency) {
        loanInfoService.addCurrency(currency);
    }

    public Currency callNewCurrencyWindow(String currencyCode) {
        final Stage primaryStage = new Stage();
        FXMLLoader root;
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        root = new FXMLLoader(getClass().getResource("/fxml/CurrencyScene.fxml"));
        try {
            primaryStage.setScene(new Scene(root.load()));
        } catch (IOException ex) {
            Logger.getLogger(MainModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        //передача информации в контроллер
        cwController = root.<CurrencyWindowController>getController();
        cwController.setMainModel(this);
        cwController.setScreenForms(currencyCode);

        primaryStage.setOnCloseRequest(event -> primaryStage.close());
        primaryStage.setResizable(false);
        primaryStage.showAndWait();

        Alert alert;
        Currency c = cwController.getCurrency();
        if (c != null) {
            insertCurrencyIntoDB(c);
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Валюта добавлена");
            alert.showAndWait();
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Валюта не добавлена");
            alert.showAndWait();
        }
        return c;
    }

    public void callNewClientInfoWindow() {
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
        ciController.setMainModel(this);
        ciController.setCurrencyMap(getCurrencyMap());
        ciController.setBankMap(getBankMap());

        primaryStage.setOnCloseRequest(event -> primaryStage.close());
        primaryStage.setResizable(false);
        primaryStage.showAndWait();
        //получение информации из контроллера
        LoanInfo newInfo = ciController.getNewInfo();
        if(newInfo!=null){
            if (loanInfoService.isClientExists(newInfo.getPerson())){
                loanInfoService.addInfo(newInfo);
            }
            else
                loanInfoService.addNewClient(newInfo);
        }
    }
}
