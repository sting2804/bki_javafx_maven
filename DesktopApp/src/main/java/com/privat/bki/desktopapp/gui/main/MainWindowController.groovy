package com.privat.bki.desktopapp.gui.main
import com.privat.bki.business.entities.Bank
import com.privat.bki.business.entities.Currency
import com.privat.bki.business.entities.LoanInfo
import com.privat.bki.business.entities.Person
import com.privat.bki.business.utils.SqlDateConverter
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.CheckBoxTableCell
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.GridPane
import javafx.util.Callback

import java.sql.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
/**
 * @author sting
 */
class MainWindowController implements Initializable {

    @FXML
    Button newClientButton
    @FXML
    Button changeInfoButton
    @FXML
    Button addInfoButton
    @FXML
    Button resetButton

    private ObservableList<LoanInfo> infoList

    @FXML
    TableView tableView
    @FXML
    TableColumn<LoanInfo, Person> fioColumn
    @FXML
    TableColumn<LoanInfo, Person> bdColumn
    @FXML
    TableColumn<LoanInfo, Person> innColumn
    @FXML
    TableColumn<LoanInfo, Person> passportColumn
    @FXML
    TableColumn<LoanInfo, Double> initAmountColumn
    @FXML
    TableColumn<LoanInfo, Date> initDateColumn
    @FXML
    TableColumn<LoanInfo, Date> finishDateColumn
    @FXML
    TableColumn<LoanInfo, Double> balanceColumn
    @FXML
    TableColumn<LoanInfo, Boolean> arrearsColumn
    @FXML
    TableColumn<LoanInfo, Bank> bankColumn
    @FXML
    TableColumn<LoanInfo, Currency> currencyColumn
    @FXML
    Button findClientButton
    @FXML
    GridPane rootScene

    private MainModel mainModel

    MainWindowController() {
        infoList = FXCollections.observableArrayList()
        //tableView.sortPolicyProperty().set(t -> {
        //Comparator<LoanInfo> comparator = (r1, r2)
        //-> r1 == rowTotal ? 1 //rowTotal at the bottom
        //: r2 == rowTotal ? -1 //rowTotal at the bottom
        //: t.getComparator() == null ? 0 //no column sorted: don't change order
        //: t.getComparator().compare(r1, r2) //columns are sorted: sort accordingly
        //FXCollections.sort(table.getItems(), comparator)
        //return true
        //})
    }

    void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel
        refreshTable(tableView)

        List<LoanInfo> tempList = mainModel.getAllRecords()
        if (tempList != null) infoList.addAll(tempList)
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Отсутсвует связь с сервером. " +
                    "Повторите запуск программы позже", ButtonType.OK)
            Optional<ButtonType> result = alert.showAndWait()
            if (result.get() == ButtonType.OK) {
                System.exit(0)
            }
        }
    }

    @Override
    void initialize(URL location, ResourceBundle resources) {
        setPropertyValueFactory()
    }

    @FXML
    void newClientOnClick(ActionEvent actionEvent) {
        try {
            mainModel.callNewClientInfoWindow()
            refreshTable(tableView)
            List<LoanInfo> tmpList = mainModel.getAllRecords()
            if (tmpList != null) infoList.addAll(tmpList)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }


    private void setPropertyValueFactory() {
        SqlDateConverter converter = new SqlDateConverter()
        fioColumn.setSortType(TableColumn.SortType.ASCENDING)
        fioColumn.setCellValueFactory(new PropertyValueFactory<>("person"))
        fioColumn.setCellFactory(new Callback<TableColumn<LoanInfo, Person>, TableCell<LoanInfo, Person>>() {
            @Override
            TableCell<LoanInfo, Person> call(TableColumn<LoanInfo, Person> param) {
                TableCell<LoanInfo, Person> fioCell = new TableCell<LoanInfo, Person>() {
                    @Override
                    protected void updateItem(Person item, boolean empty) {
                        setGraphic(null)
                        if (item != null) {
                            String text = item.getSurname().concat(" ").concat(item.getName())
                            if (item.getPatronymic() != null) text += (" " + item.getPatronymic())
                            setGraphic(new Label(text))
                        }
                    }
                }
                return fioCell
            }
        })

        bdColumn.setCellValueFactory(new PropertyValueFactory<>("person"))
        bdColumn.setCellFactory(new Callback<TableColumn<LoanInfo, Person>, TableCell<LoanInfo, Person>>() {
            @Override
            TableCell<LoanInfo, Person> call(TableColumn<LoanInfo, Person> param) {
                TableCell<LoanInfo, Person> bdCell = new TableCell<LoanInfo, Person>() {
                    @Override
                    protected void updateItem(Person item, boolean empty) {
                        setGraphic(null)
                        if (item != null) {
                            SimpleStringProperty property = new SimpleStringProperty()
                            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy")
                            property.setValue(dateFormat.format(converter.to(item.getBirthday())))
                            Label bdLabel = new Label(property.get())
                            setGraphic(bdLabel)
                        }
                    }
                }
                return bdCell
            }
        })

        innColumn.setCellValueFactory(new PropertyValueFactory<>("person"))
        innColumn.setCellFactory(new Callback<TableColumn<LoanInfo, Person>, TableCell<LoanInfo, Person>>() {
            @Override
            TableCell<LoanInfo, Person> call(TableColumn<LoanInfo, Person> param) {
                TableCell<LoanInfo, Person> innCell = new TableCell<LoanInfo, Person>() {
                    @Override
                    protected void updateItem(Person item, boolean empty) {
                        setGraphic(null)
                        if (item != null) {
                            Label innLabel = new Label(item.getInn())
                            setGraphic(innLabel)
                        }
                    }
                }
                return innCell
            }
        })
        passportColumn.setCellValueFactory(new PropertyValueFactory<>("person"))
        passportColumn.setCellFactory(new Callback<TableColumn<LoanInfo, Person>, TableCell<LoanInfo, Person>>() {
            @Override
            TableCell<LoanInfo, Person> call(TableColumn<LoanInfo, Person> param) {
                TableCell<LoanInfo, Person> passportCell = new TableCell<LoanInfo, Person>() {
                    @Override
                    protected void updateItem(Person item, boolean empty) {
                        setGraphic(null)
                        if (item != null) {
                            Label passportLabel = new Label(item.getPassSerial().concat(" ").concat(item.getPassNumber()))
                            setGraphic(passportLabel)
                        }
                    }
                }
                return passportCell
            }
        })
        initAmountColumn.setCellValueFactory(new PropertyValueFactory<>("initAmount"))
        initDateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LoanInfo, Date>, ObservableValue<Date>>() {
            @Override
            ObservableValue<Date> call(TableColumn.CellDataFeatures<LoanInfo, Date> item) {
                ObjectProperty property = new SimpleObjectProperty()
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy")
                property.setValue(dateFormat.format(converter.to(item.getValue().getInitDate())))
                return property
            }
        })
        finishDateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LoanInfo, Date>, ObservableValue<Date>>() {
            @Override
            ObservableValue<Date> call(TableColumn.CellDataFeatures<LoanInfo, Date> item) {
                ObjectProperty property = new SimpleObjectProperty()
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy")
                try {
                    property.setValue(dateFormat.format(converter.to(item.getValue().getFinishDate())))
                } catch (Exception e) {

                }
                return property
            }
        })
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"))
        arrearsColumn.setCellValueFactory(new PropertyValueFactory<>("arrears"))
        arrearsColumn.setCellFactory(CheckBoxTableCell.forTableColumn(arrearsColumn))
        bankColumn.setCellValueFactory(new PropertyValueFactory<>("bank"))
        bankColumn.setCellFactory(new Callback<TableColumn<LoanInfo, Bank>, TableCell<LoanInfo, Bank>>() {
            @Override
            TableCell<LoanInfo, Bank> call(TableColumn<LoanInfo, Bank> param) {
                TableCell<LoanInfo, Bank> bankCell = new TableCell<LoanInfo, Bank>() {
                    @Override
                    protected void updateItem(Bank item, boolean empty) {
                        setGraphic(null)
                        if (item != null) {
                            Label bankLabel = new Label(item.getCode())
                            setGraphic(bankLabel)
                        }
                    }
                }
                return bankCell
            }
        })
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"))
        currencyColumn.setCellFactory(new Callback<TableColumn<LoanInfo, Currency>, TableCell<LoanInfo, Currency>>() {
            @Override
            TableCell<LoanInfo, Currency> call(TableColumn<LoanInfo, Currency> param) {
                TableCell<LoanInfo, Currency> currencyCell = new TableCell<LoanInfo, Currency>() {
                    @Override
                    protected void updateItem(Currency item, boolean empty) {
                        setGraphic(null)
                        if (item != null) {
                            Label currencyLabel = new Label(item.getCode())
                            setGraphic(currencyLabel)
                        }
                    }
                }
                return currencyCell
            }
        })
        tableView.setItems(infoList)
    }

    @FXML
    void findClientOnClick(ActionEvent actionEvent) {
        try {
            mainModel.callSearchWindow()
            refreshTable(tableView)

            List<LoanInfo> tmpList = mainModel.getFoundRecords()
            if (tmpList != null) infoList.addAll(tmpList)

        } catch (Exception e) {
        }
    }

    /**
     * сброс записей таблицы
     *
     * @param tableView
     */
    private void refreshTable(TableView tableView) {
        infoList = FXCollections.observableArrayList()
        tableView.setItems(infoList)
    }

    @FXML
    void addInfoOnClick(ActionEvent actionEvent) {
        int curRow = -1
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            curRow = tableView.getSelectionModel().getSelectedIndex()
        }
        try {
            Person curInfo = infoList.get(curRow).getPerson()
            mainModel.callAddInfoWindow(curInfo)
            refreshTable(tableView)

            List<LoanInfo> tmpList = mainModel.getAllRecords()
            if (tmpList != null) infoList.addAll(tmpList)

        } catch (ArrayIndexOutOfBoundsException ex) {
            new Alert(Alert.AlertType.WARNING, "Выберите поле в таблице").showAndWait()
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    @FXML
    void changeInfoOnClick(ActionEvent actionEvent) {
        int curRow = -1
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            curRow = tableView.getSelectionModel().getSelectedIndex()
        }
        try {
            LoanInfo curInfo = infoList.get(curRow)
            mainModel.callChangeClientWindow(curInfo)
            refreshTable(tableView)

            List<LoanInfo> tmpList = mainModel.getAllRecords()
            if (tmpList != null) infoList.addAll(tmpList)

        } catch (ArrayIndexOutOfBoundsException ex) {
            new Alert(Alert.AlertType.WARNING, "Выберите поле в таблице").showAndWait()
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    void resetTableRecords(ActionEvent actionEvent) {
        refreshTable(tableView)
        List<LoanInfo> tmpList = mainModel.getAllRecords()
        if (tmpList != null) infoList.addAll(tmpList)
    }
}
