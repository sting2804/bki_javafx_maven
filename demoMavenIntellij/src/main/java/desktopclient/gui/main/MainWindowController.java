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
import java.util.ResourceBundle;

/**
 * @author sting
 */
public class MainWindowController implements Initializable {


    public Button newClientButton;
    public Button changeInfoButton;
    public Button addInfoButton;

    private ObservableList<LoanInfo> infoList;
    //private ObservableList<Person> personList = FXCollections.observableArrayList();

    @FXML
    public TableView tableView;
    @FXML
    public TableColumn<LoanInfo, Person> fioColumn;
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
        infoList = FXCollections.observableArrayList();
    }

    public void setMainModel(MainModel mainModel){
        this.mainModel=mainModel;
        infoList.addAll(mainModel.getAllRecords());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPropertyValueFactory();
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
        fioColumn.setCellValueFactory(new PropertyValueFactory<LoanInfo, Person>("person"));
        fioColumn.setCellFactory(new Callback<TableColumn<LoanInfo, Person>, TableCell<LoanInfo, Person>>() {
            @Override
            public TableCell<LoanInfo, Person> call(TableColumn<LoanInfo, Person> param) {
                TableCell<LoanInfo, Person> fioCell = new TableCell<LoanInfo, Person>() {

                    @Override
                    protected void updateItem(Person item, boolean empty) {
                        setGraphic(null);
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
                        setGraphic(null);
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
                        setGraphic(null);
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
                        setGraphic(null);
                        if (item != null) {
                            Label passportLabel = new Label(item.getPassSerial().concat(" ").concat(item.getPassNumber()));
                            setGraphic(passportLabel);
                        }
                    }

                };
                return passportCell;
            }
        });
        /*fioColumn.setCellValueFactory(new PropertyValueFactory<>("fio"));
        bdColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        innColumn.setCellValueFactory(new PropertyValueFactory<>("inn"));
        passportColumn.setCellValueFactory(new PropertyValueFactory<>("passport"));*/
        initAmountColumn.setCellValueFactory(new PropertyValueFactory<>("initAmount"));
        initDateColumn.setCellValueFactory(new PropertyValueFactory<>("initDate"));
        finishDateColumn.setCellValueFactory(new PropertyValueFactory<>("finishDate"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        arrearsColumn.setCellValueFactory(new PropertyValueFactory<>("arrears"));
        bankColumn.setCellValueFactory(new PropertyValueFactory<>("bank"));
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));
        tableView.setItems(infoList);
        //tableView.setItems(personList);
    }

    private void setTableViewCellFactory(TableView tableView){
        tableView.getColumns().add(fioColumn);
        tableView.getColumns().add(bdColumn);
        tableView.getColumns().add(innColumn);
        tableView.getColumns().add(passportColumn);
        tableView.getColumns().add(initAmountColumn);
        tableView.getColumns().add(initDateColumn);
        tableView.getColumns().add(finishDateColumn);
        tableView.getColumns().add(balanceColumn);
        tableView.getColumns().add(arrearsColumn);
        tableView.getColumns().add(bankColumn);
        tableView.getColumns().add(currencyColumn);
    }

    @FXML
    public void changeClientOnClick(ActionEvent actionEvent) {
        int curRow=-1;
        if (tableView.getSelectionModel().getSelectedItem() != null) {
                  curRow = tableView.getSelectionModel().getSelectedIndex();
        }
        LoanInfo curInfo = infoList.get(curRow);
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
            refreshTable(tableView);
            infoList.addAll(mainModel.getFoundInfo());
            tableView.setItems(infoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * сброс записей таблицы
     * @param tableView
     */
    private void refreshTable(TableView tableView) {
        infoList = FXCollections.observableArrayList();
        tableView.setItems(infoList);
    }

    @FXML
    public void addInfoOnClick(ActionEvent actionEvent) {
        int curRow=-1;
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            curRow = tableView.getSelectionModel().getSelectedIndex();
        }
        Person curInfo = infoList.get(curRow).getPerson();
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
        LoanInfo curInfo = infoList.get(curRow);
        try {
            mainModel.callChangeClientWindow(curInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
