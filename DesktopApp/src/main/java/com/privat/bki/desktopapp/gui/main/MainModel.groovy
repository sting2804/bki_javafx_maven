package com.privat.bki.desktopapp.gui.main
import com.privat.bki.business.entities.Bank
import com.privat.bki.business.entities.Currency
import com.privat.bki.business.entities.LoanInfo
import com.privat.bki.business.entities.Person
import com.privat.bki.desktopapp.gui.changeclient.ChangeInfoWindowController
import com.privat.bki.desktopapp.gui.directories.BankWindowController
import com.privat.bki.desktopapp.gui.directories.CurrencyWindowController
import com.privat.bki.desktopapp.gui.login.LoginController
import com.privat.bki.desktopapp.gui.searchuser.ClientSearchWindowController
import com.privat.bki.desktopapp.utils.DaoRestTemplateService
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.WindowEvent
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
/**
 * Класс для связи контроллера главного окна с остальными окнами,
 * включает обработку некоторых событий по открытию новых окон и передаче туда неких данных
 */
@Component
class MainModel {

    @Autowired
    DaoRestTemplateService service

    private static final Logger log = Logger.getLogger(MainModel.class)
    /**
     * ncController контроллер окна создания нового клиента
     * csController контроллер окна писка клиента
     * ciController контроллен окна изменения и добавления информации клиенту
     * mwController контроллер главного окна
     * bwController контроллер окна добавления банка
     * cwController контроллер окна добавления валюты
     */
    private ClientSearchWindowController csController
    private ChangeInfoWindowController ciController
    private MainWindowController mwController
    private BankWindowController bwController
    private CurrencyWindowController cwController
    private LoginController liController

    /**
     * foundInfo хранит информацию об искомом клиенте
     */
    private List<LoanInfo> foundInfo

    MainModel() {
    }

    /**
     * вход в систему
     * @param username
     * @param password
     */
    boolean authenticateUser(String username, String password){
        return service.restAuthenticate(username,password)
    }

    /**
     * callLoginWindow() вызывается из загрузчика для откытия окна
     */
    void callLoginWindow() {
        Stage primaryStage = new Stage()
        Scene scene = null
        FXMLLoader root
        root = new FXMLLoader(getClass().getResource("/fxml/LoginScene.fxml"))
        try {
            scene = new Scene(root.load())
            primaryStage.setScene(scene)
        } catch (IOException e) {
            log.error(e.getMessage())
        }
        liController = root.<LoginController>getController()
        liController.setMainModel(this)
        scene.getStylesheets().add("/styles/Styles.css")
        primaryStage.setTitle("JavaFX")
        primaryStage.setScene(scene)
        primaryStage.setResizable(false)
        primaryStage.showAndWait()
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            void handle(WindowEvent event) {
                Platform.exit()
                System.exit(0)
            }
        })
    }

    /**
     * callMainWindow() вызывается из загрузчика для откытия окна
     */
    void callMainWindow(Stage primaryStage) {
        callLoginWindow()
        Scene scene = null
        FXMLLoader root
        root = new FXMLLoader(getClass().getResource("/fxml/MainScene.fxml"))
        try {
            scene = new Scene(root.load())
            primaryStage.setScene(scene)
        } catch (IOException e) {
            log.error(e.getMessage())
        }
        mwController = root.<MainWindowController>getController()
        mwController.setMainModel(this)
        scene.getStylesheets().add("/styles/Styles.css")
        primaryStage.setTitle("BKI on JavaFX")
        primaryStage.setScene(scene)
        primaryStage.setResizable(false)
        primaryStage.show()
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            void handle(WindowEvent event) {
                Platform.exit()
                System.exit(0)
            }
        })
    }

    List<LoanInfo> getAllRecords() {
        return service.listAll()
    }

    /**
     * callSearchWindow() открывает окно поиска
     *
     * @throws NullPointerException выбрасывает в случае не установки ни одного поля для поиска
     */
    void callSearchWindow() throws NullPointerException {
        Stage primaryStage = new Stage()
        FXMLLoader root
        root = new FXMLLoader(getClass().getResource("/fxml/ClientSearchScene.fxml"))
        try {
            primaryStage.setScene(new Scene(root.load()))
        } catch (IOException e) {
            log.error(e.getMessage())
        }
        csController = root.<ClientSearchWindowController>getController()
        primaryStage.setResizable(false)
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            void handle(WindowEvent event) {
                primaryStage.close()
            }
        })
        primaryStage.initModality(Modality.APPLICATION_MODAL)
        primaryStage.showAndWait()
        Person searchPerson = csController.getSearchablePerson()
        if (searchPerson != null) {
            if ((foundInfo = service.listSpecificInfo(searchPerson)) == null)
                new Alert(Alert.AlertType.WARNING, "Клиент не найден").showAndWait()
        }

    }

    /**
     * callChangeClientWindow() открывает окно  изменения информации о кредитах выбранного клиента
     *
     * @param curInfo выбранная сущность из таблицы
     * @throws Exception
     */
    void callChangeClientWindow(LoanInfo curInfo) throws Exception {
        final Stage primaryStage = new Stage()
        FXMLLoader root
        primaryStage.initModality(Modality.APPLICATION_MODAL)
        root = new FXMLLoader(getClass().getResource("/fxml/InfoScene.fxml"))
        try {
            primaryStage.setScene(new Scene(root.load()))
        } catch (IOException e) {
            log.error(e.getMessage())
        }
        //передача информации в контроллер
        ciController = root.<ChangeInfoWindowController>getController()
        ciController.setMainModel(this)
        ciController.setCurrencyMap(getCurrencyMap())
        ciController.setBankMap(getBankMap())
        ciController.setLoanInfo(curInfo)
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            void handle(WindowEvent event) {
                primaryStage.close()
            }
        })
        primaryStage.setResizable(false)
        primaryStage.showAndWait()
        //получение информации из контроллера
        LoanInfo newInfo = ciController.getNewInfo()
        if (curInfo.equals(newInfo)) {
            new Alert(Alert.AlertType.WARNING, "Данные совпадают, менять нечего").showAndWait()
            return
        }
        if (newInfo != null) {
            int clientId = service.isClientExists(newInfo.getPerson())
            if (clientId != -1) {
                service.modify(curInfo, newInfo)
            }
        }
    }

    /**
     * callAddInfoWindow() открывает окно добавления информации о кредитах выбранного клиента
     *
     * @param curInfo выбранная сущность из таблицы
     * @throws Exception
     */
    void callAddInfoWindow(Person curInfo) throws Exception {
        final Stage primaryStage = new Stage()
        FXMLLoader root
        primaryStage.initModality(Modality.APPLICATION_MODAL)
        root = new FXMLLoader(getClass().getResource("/fxml/InfoScene.fxml"))
        try {
            primaryStage.setScene(new Scene(root.load()))
        } catch (IOException e) {
            log.error(e.getMessage())
        }
        //передача информации в контроллер
        ciController = root.<ChangeInfoWindowController>getController()
        ciController.setMainModel(this)
        ciController.setCurrencyMap(getCurrencyMap())
        ciController.setBankMap(getBankMap())
        ciController.setPerson(curInfo)
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            void handle(WindowEvent event) {
                primaryStage.close()
            }
        })
        primaryStage.setResizable(false)
        primaryStage.showAndWait()
        //получение информации из контроллера
        LoanInfo newInfo = ciController.getNewInfo()
        if (newInfo != null) {
            int clientId = service.isClientExists(newInfo.getPerson())
            if (clientId != -1) {
                service.addInfo(newInfo, clientId)
            } else
                service.addNewClient(newInfo)
        }
    }

    void callNewClientInfoWindow() {
        final Stage primaryStage = new Stage()
        FXMLLoader root
        primaryStage.initModality(Modality.APPLICATION_MODAL)
        root = new FXMLLoader(getClass().getResource("/fxml/InfoScene.fxml"))
        try {
            primaryStage.setScene(new Scene(root.load()))
        } catch (IOException e) {
            log.error(e.getMessage())
        }
        //передача информации в контроллер
        ciController = root.<ChangeInfoWindowController>getController()
        ciController.setMainModel(this)
        ciController.setCurrencyMap(getCurrencyMap())
        ciController.setBankMap(getBankMap())

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            void handle(WindowEvent event) {
                primaryStage.close()
            }
        })
        primaryStage.setResizable(false)
        primaryStage.showAndWait()
        //получение информации из контроллера
        LoanInfo newInfo = ciController.getNewInfo()
        if (newInfo != null) {
            int clientId = service.isClientExists(newInfo.getPerson())
            if (clientId != -1) {
                service.addInfo(newInfo, clientId)
            } else
                service.addNewClient(newInfo)
        }
    }

    Bank callNewBankWindow(String bankCode) {
        final Stage primaryStage = new Stage()
        FXMLLoader root
        primaryStage.initModality(Modality.APPLICATION_MODAL)
        root = new FXMLLoader(getClass().getResource("/fxml/BankScene.fxml"))
        try {
            primaryStage.setScene(new Scene(root.load()))
        } catch (IOException e) {
            log.error(e.getMessage())
        }
        //передача информации в контроллер
        bwController = root.<BankWindowController>getController()
        bwController.setMainModel(this)
        bwController.setScreenForms(bankCode)
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            void handle(WindowEvent event) {
                primaryStage.close()
            }
        })
        primaryStage.setResizable(false)
        primaryStage.showAndWait()
        Bank b = bwController.getBank()
        if (b != null) {
            if (service.addBank(b))
                new Alert(Alert.AlertType.INFORMATION, "Банк добавлен").showAndWait()
            else new Alert(Alert.AlertType.WARNING, "Банк не добавлен").showAndWait()
        } else {
            new Alert(Alert.AlertType.WARNING, "Банк не добавлен").showAndWait()
        }
        return b
    }

    Currency callNewCurrencyWindow(String currencyCode) {
        final Stage primaryStage = new Stage()
        FXMLLoader root
        primaryStage.initModality(Modality.APPLICATION_MODAL)
        root = new FXMLLoader(getClass().getResource("/fxml/CurrencyScene.fxml"))
        try {
            primaryStage.setScene(new Scene(root.load()))
        } catch (IOException e) {
            log.error(e.getMessage())
        }
        //передача информации в контроллер
        cwController = root.<CurrencyWindowController>getController()
        cwController.setMainModel(this)
        cwController.setScreenForms(currencyCode)

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            void handle(WindowEvent event) {
                primaryStage.close()
            }
        })
        primaryStage.setResizable(false)
        primaryStage.showAndWait()

        Currency c = cwController.getCurrency()
        if (c != null) {
            if (service.addCurrency(c))
                new Alert(Alert.AlertType.INFORMATION, "Банк добавлен").showAndWait()
            else new Alert(Alert.AlertType.WARNING, "Банк не добавлен").showAndWait()
        } else {
            new Alert(Alert.AlertType.WARNING, "Банк не добавлен").showAndWait()
        }
        return c
    }

    //работа со справочниками

    Map<String, String> getCurrencyMap() {
        return service.getCurrenciesMap()
    }

    Map<String, String> getBankMap() {
        return service.getBanksMap()
    }

    List<LoanInfo> getFoundRecords() {
        return foundInfo
    }

    List<LoanInfo> getFoundInfo() {
        return foundInfo
    }

    void setFoundInfo(List<LoanInfo> foundInfo) {
        this.foundInfo = foundInfo
    }
}
