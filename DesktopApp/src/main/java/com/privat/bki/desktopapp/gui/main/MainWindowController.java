package com.privat.bki.desktopapp.gui.main;

import com.privat.bki.entities.Bank;
import com.privat.bki.entities.Currency;
import com.privat.bki.entities.LoanInfo;
import com.privat.bki.entities.Person;
import com.privat.bki.utils.DateConverter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author sting
 */
public class MainWindowController implements Initializable {

    @FXML
    public Button newClientButton;
    @FXML
    public Button changeInfoButton;
    @FXML
    public Button addInfoButton;
    @FXML
    public Button resetButton;

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
    public Button findClientButton;
    @FXML
    public GridPane rootScene;

    private MainModel mainModel;

    public MainWindowController() {
        infoList = FXCollections.observableArrayList();
        tableView.sortPolicyProperty().set(t -> {
            Comparator<LoanInfo> comparator = (r1, r2)
                    -> r1 == rowTotal ? 1 //rowTotal at the bottom
                    : r2 == rowTotal ? -1 //rowTotal at the bottom
                    : t.getComparator() == null ? 0 //no column sorted: don't change order
                    : t.getComparator().compare(r1, r2); //columns are sorted: sort accordingly
            FXCollections.sort(table.getItems(), comparator);
            return true;
        });
    }

    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
        refreshTable(tableView);

        List<LoanInfo> tempList = mainModel.getAllRecords();
        if (tempList != null) infoList.addAll(tempList);
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Отсутсвует связь с сервером. " +
                    "Устраните неисправность и повторите запуск программы", ButtonType.OK);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.exit(0);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPropertyValueFactory();
    }

    @FXML
    public void newClientOnClick(ActionEvent actionEvent) {
        try {
            mainModel.callNewClientInfoWindow();
            refreshTable(tableView);
            List<LoanInfo> tmpList = mainModel.getAllRecords();
            if (tmpList != null) infoList.addAll(tmpList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setPropertyValueFactory() {
        DateConverter converter = new DateConverter();
        fioColumn.setSortType(TableColumn.SortType.ASCENDING);
        fioColumn.setCellValueFactory(new PropertyValueFactory<>("person"));
        fioColumn.setCellFactory((TableColumn<LoanInfo, Person> param) -> {
            TableCell<LoanInfo, Person> fioCell = new TableCell<LoanInfo, Person>() {
                @Override
                protected void updateItem(Person item, boolean empty) {
                    setGraphic(null);
                    if (item != null) {
                        String text = item.getSurname().concat(" ").concat(item.getName());
                        if (item.getPatronymic() != null) text += (" " + item.getPatronymic());
                        setGraphic(new Label(text));
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
                        SimpleStringProperty property = new SimpleStringProperty();
                        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                        property.setValue(dateFormat.format(converter.to(item.getBirthday())));
                        Label bdLabel = new Label(property.get());
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
        initDateColumn.setCellValueFactory(item -> {
            ObjectProperty property = new SimpleObjectProperty();
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            property.setValue(dateFormat.format(converter.to(item.getValue().getInitDate())));
            return property;
        });
        finishDateColumn.setCellValueFactory(item -> {
            ObjectProperty property = new SimpleObjectProperty();
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            property.setValue(dateFormat.format(converter.to(item.getValue().getFinishDate())));
            return property;
        });
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

            List<LoanInfo> tmpList = mainModel.getFoundRecords();
            if (tmpList != null) infoList.addAll(tmpList);

        } catch (Exception e) {
        }
    }

    /**
     * сброс записей таблицы
     *
     * @param tableView
     */
    private void refreshTable(TableView tableView) {
        infoList = FXCollections.observableArrayList();
        tableView.setItems(infoList);
    }

    @FXML
    public void addInfoOnClick(ActionEvent actionEvent) {
        int curRow = -1;
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            curRow = tableView.getSelectionModel().getSelectedIndex();
        }
        try {
            Person curInfo = infoList.get(curRow).getPerson();
            mainModel.callAddInfoWindow(curInfo);
            refreshTable(tableView);

            List<LoanInfo> tmpList = mainModel.getAllRecords();
            if (tmpList != null) infoList.addAll(tmpList);

        } catch (ArrayIndexOutOfBoundsException ex) {
            new Alert(Alert.AlertType.WARNING, "Выберите поле в таблице").showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void changeInfoOnClick(ActionEvent actionEvent) {
        int curRow = -1;
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            curRow = tableView.getSelectionModel().getSelectedIndex();
        }
        try {
            LoanInfo curInfo = infoList.get(curRow);
            mainModel.callChangeClientWindow(curInfo);
            refreshTable(tableView);

            List<LoanInfo> tmpList = mainModel.getAllRecords();
            if (tmpList != null) infoList.addAll(tmpList);

        } catch (ArrayIndexOutOfBoundsException ex) {
            new Alert(Alert.AlertType.WARNING, "Выберите поле в таблице").showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetTableRecords(ActionEvent actionEvent) {
        refreshTable(tableView);
        List<LoanInfo> tmpList = mainModel.getAllRecords();
        if (tmpList != null) infoList.addAll(tmpList);
    }
}
