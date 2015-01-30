package desktopclient.gui.main;

import com.sun.rowset.internal.Row;
import desktopclient.entities.Bank;
import desktopclient.entities.Currency;
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
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.scene.control.cell.CheckBoxTableCell;

/**
 * @author sting
 */
public class MainWindowController implements Initializable {


    public Button newClientButton;
    public Button changeInfoButton;
    public Button addInfoButton;

    private ObservableList<LoanInfo> infoList;

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
    public TableColumn<LoanInfo, Bank> bankColumn;
    @FXML
    public TableColumn<LoanInfo, Currency> currencyColumn;
    @FXML
    public Button addNewButton;
    @FXML
    public Button findClientButton;
    @FXML
    public GridPane rootScene;

    private MainModel mainModel;

    public MainWindowController() {
        infoList = FXCollections.observableArrayList();
       /* tableView.sortPolicyProperty().set(t -> {
            Comparator<LoanInfo> comparator = (r1, r2)
                    -> r1 == rowTotal ? 1 //rowTotal at the bottom
                    : r2 == rowTotal ? -1 //rowTotal at the bottom
                    : t.getComparator() == null ? 0 //no column sorted: don't change order
                    : t.getComparator().compare(r1, r2); //columns are sorted: sort accordingly
            FXCollections.sort(table.getItems(), comparator);
            return true;
        });*/
    }

    public void setMainModel(MainModel mainModel){
        this.mainModel=mainModel;
        refreshTable(tableView);
        infoList.addAll(mainModel.getAllRecords());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPropertyValueFactory();
    }

    @FXML
    public void newClientOnClick(ActionEvent actionEvent) {
//        try {
//            mainModel.callNewClientWindow();
//            refreshTable(tableView);
//            infoList.addAll(mainModel.getAllRecords());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            mainModel.callNewClientInfoWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setPropertyValueFactory() {

        fioColumn.setSortType(TableColumn.SortType.ASCENDING);
        fioColumn.setCellValueFactory(new PropertyValueFactory<>("person"));
        fioColumn.setCellFactory((TableColumn<LoanInfo, Person> param) -> {
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
        });
        bdColumn.setCellValueFactory(new PropertyValueFactory<>("person"));
        bdColumn.setCellFactory((TableColumn<LoanInfo, Person> param) -> {
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
        });
        innColumn.setCellValueFactory(new PropertyValueFactory<>("person"));
        innColumn.setCellFactory((TableColumn<LoanInfo, Person> param) -> {
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
        });
        passportColumn.setCellValueFactory(new PropertyValueFactory<>("person"));
        passportColumn.setCellFactory((TableColumn<LoanInfo, Person> param) -> {
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
        });
        initAmountColumn.setCellValueFactory(new PropertyValueFactory<>("initAmount"));
        initDateColumn.setCellValueFactory(new PropertyValueFactory<>("initDate"));
        finishDateColumn.setCellValueFactory(new PropertyValueFactory<>("finishDate"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        arrearsColumn.setCellValueFactory(new PropertyValueFactory<>("arrears"));
        arrearsColumn.setCellFactory(CheckBoxTableCell.forTableColumn(arrearsColumn));
        bankColumn.setCellValueFactory(new PropertyValueFactory<>("bank"));
        bankColumn.setCellFactory((TableColumn<LoanInfo, Bank> param) -> {
            TableCell<LoanInfo, Bank> bankCell = new TableCell<LoanInfo, Bank>() {
                @Override
                protected void updateItem(Bank item, boolean empty) {
                    setGraphic(null);
                    if (item != null) {
                        Label bankLabel = new Label(item.getCode());
                        setGraphic(bankLabel);
                    }
                }
            };
            return bankCell;
        });
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));
        currencyColumn.setCellFactory((TableColumn<LoanInfo, Currency> param) -> {
            TableCell<LoanInfo, Currency> currencyCell = new TableCell<LoanInfo, Currency>() {
                @Override
                protected void updateItem(Currency item, boolean empty) {
                    setGraphic(null);
                    if (item != null) {
                        Label currencyLabel = new Label(item.getCode());
                        setGraphic(currencyLabel);
                    }
                }
            };
            return currencyCell;
        });
        tableView.setItems(infoList);
    }

    @FXML
    public void findClientOnClick(ActionEvent actionEvent) {
        try {
            mainModel.callSearchWindow();
            refreshTable(tableView);
            infoList.addAll(mainModel.getFoundInfo());
            tableView.setItems(infoList);
        } catch (Exception e) {
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
        try {
            Person curInfo = infoList.get(curRow).getPerson();
            mainModel.callAddInfoWindow(curInfo);
        }catch (ArrayIndexOutOfBoundsException ex){
            new Alert(Alert.AlertType.WARNING, "Выберите поле в таблице").showAndWait();
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
        try {
            LoanInfo curInfo = infoList.get(curRow);
            mainModel.callChangeClientWindow(curInfo);
        } catch (ArrayIndexOutOfBoundsException ex) {
            new Alert(Alert.AlertType.WARNING, "Выберите поле в таблице").showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public void onSort(SortEvent<TableView<S>> tableViewSortEvent) {

    }*/
}
