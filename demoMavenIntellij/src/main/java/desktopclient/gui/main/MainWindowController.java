package desktopclient.gui.main;

import desktopclient.entities.LoanInfo;
import desktopclient.entities.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * @author sting
 */
public class MainWindowController implements Initializable {


    public Button newClientButton;
    public Button changeInfoButton;
    public Button addInfoButton;
    private ObservableList<LoanInfo> personsList = FXCollections.observableArrayList();
    @FXML
    public TableView tableView;
    @FXML
    public TableColumn <LoanInfo, Person>fioColumn;
    @FXML
    public TableColumn<LoanInfo, Person> bdColumn;
    @FXML
    public TableColumn<LoanInfo, Person> innColumn;
    @FXML
    public TableColumn<LoanInfo, Person> passportColumn;
    @FXML
    public TableColumn<LoanInfo, Double> initAmountColumn;
    @FXML
    public TableColumn<LoanInfo, Date> initDateColumn;
    @FXML
    public TableColumn<LoanInfo, Date> finishDateColumn;
    @FXML
    public TableColumn<LoanInfo, Double> balanceColumn;
    @FXML
    public TableColumn<LoanInfo, Boolean> arrearsColumn;
    @FXML
    public TableColumn<LoanInfo, String> bankColumn;
    @FXML
    public TableColumn<LoanInfo, String> currencyColumn;
    @FXML
    public Button addNewButton;
    @FXML
    public Button findClientButton;
    @FXML
    public GridPane rootScene;

    private MainModel mainModel;

    public MainWindowController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainModel = new MainModel();
        setPropertyValueFactory();
        personsList.addAll(mainModel.getAllRecords());
    }

    @FXML
    public void newClientOnClick(ActionEvent actionEvent) {
        try {
            mainModel.callAddClientWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setPropertyValueFactory() {
        fioColumn.setCellValueFactory(new PropertyValueFactory<>("person"));
        fioColumn.setCellFactory(new Callback<TableColumn<LoanInfo, Person>, TableCell<LoanInfo, Person>>() {
            @Override
            public TableCell<LoanInfo, Person> call(TableColumn<LoanInfo, Person> param) {
                TableCell<LoanInfo, Person> fioCell = new TableCell<LoanInfo, Person>() {

                    @Override
                    protected void updateItem(Person item, boolean empty) {
                        if (item != null) {
                            Label fioLabel = new Label(item.getSurname().concat(" ").concat(item.getName()).concat(" ").concat(item.getPatronymic()));
                            setGraphic(fioLabel);
                        }
                    }

                };
                return fioCell;
            }
        });
        bdColumn.setCellValueFactory(new PropertyValueFactory<>("person"));
        bdColumn.setCellFactory(new Callback<TableColumn<LoanInfo, Person>, TableCell<LoanInfo, Person>>() {
            @Override
            public TableCell<LoanInfo, Person> call(TableColumn<LoanInfo, Person> param) {
                TableCell<LoanInfo, Person> bdCell = new TableCell<LoanInfo, Person>() {

                    @Override
                    protected void updateItem(Person item, boolean empty) {
                        if (item != null) {
                            Label bdLabel = new Label(item.getBirthday().toString());
                            setGraphic(bdLabel);
                        }
                    }

                };
                return bdCell;
            }
        });
        innColumn.setCellValueFactory(new PropertyValueFactory<>("person"));
        innColumn.setCellFactory(new Callback<TableColumn<LoanInfo, Person>, TableCell<LoanInfo, Person>>() {
            @Override
            public TableCell<LoanInfo, Person> call(TableColumn<LoanInfo, Person> param) {
                TableCell<LoanInfo, Person> innCell = new TableCell<LoanInfo, Person>() {

                    @Override
                    protected void updateItem(Person item, boolean empty) {
                        if (item != null) {
                            Label innLabel = new Label(item.getInn());
                            setGraphic(innLabel);
                        }
                    }

                };
                return innCell;
            }
        });
        passportColumn.setCellValueFactory(new PropertyValueFactory<>("person"));
        passportColumn.setCellFactory(new Callback<TableColumn<LoanInfo, Person>, TableCell<LoanInfo, Person>>() {
            @Override
            public TableCell<LoanInfo, Person> call(TableColumn<LoanInfo, Person> param) {
                TableCell<LoanInfo, Person> passportCell = new TableCell<LoanInfo, Person>() {

                    @Override
                    protected void updateItem(Person item, boolean empty) {
                        if (item != null) {
                            Label passportLabel = new Label(item.getPassSerial().concat(" ").concat(item.getPassNumber()));
                            setGraphic(passportLabel);
                        }
                    }

                };
                return passportCell;
            }
        });
        initAmountColumn.setCellValueFactory(new PropertyValueFactory<>("initAmount"));
        initDateColumn.setCellValueFactory(new PropertyValueFactory<>("initDate"));
        finishDateColumn.setCellValueFactory(new PropertyValueFactory<>("finishDate"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        arrearsColumn.setCellValueFactory(new PropertyValueFactory<>("arrears"));
        bankColumn.setCellValueFactory(new PropertyValueFactory<>("bank"));
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));
        tableView.setItems(personsList);
    }

    @FXML
    public void changeClientOnClick(ActionEvent actionEvent) {
        int curRow=-1;
        if (tableView.getSelectionModel().getSelectedItem() != null) {
                  curRow = tableView.getSelectionModel().getSelectedIndex();
        }
        LoanInfo curInfo = personsList.get(curRow);
        try {
            mainModel.callChangeClientWindow(curInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void findClientOnClick(ActionEvent actionEvent) {
        try {
            mainModel.callSearchWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void addInfoOnClick(ActionEvent actionEvent) {
        int curRow=-1;
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            curRow = tableView.getSelectionModel().getSelectedIndex();
        }
        Person curInfo = personsList.get(curRow).getPerson();
        try {
            mainModel.callChangeClientWindow(curInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void changeInfoOnClick(ActionEvent actionEvent) {
        int curRow=-1;
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            curRow = tableView.getSelectionModel().getSelectedIndex();
        }
        LoanInfo curInfo = personsList.get(curRow);
        try {
            mainModel.callChangeClientWindow(curInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
