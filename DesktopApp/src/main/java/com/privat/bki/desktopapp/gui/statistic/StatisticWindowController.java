package com.privat.bki.desktopapp.gui.statistic;

import com.privat.bki.desktopapp.gui.main.MainModel;
import com.privat.bki.desktopapp.utils.ChartDrawer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.privat.bki.desktopapp.utils.ChartDrawer.drawStatisticForBankByYears;

public class StatisticWindowController {

    public ComboBox<String> bankNameComboBox;
    public Button applyButton;
    public Pane panel;
    public TextField yearTextField;
    public Button applyPrognosButton;
    public TextField resultTextField;
    private MainModel mainModel;
    private Map<String, String> bankMap;
    private Alert alert = new Alert(Alert.AlertType.ERROR);


    public void drawChart(ActionEvent actionEvent) {
        String bankName = bankNameComboBox.getValue();
        Map<String,List> stat = mainModel.getStatisticForBank(bankName);
        if(bankName!=null) {
            LineChart<Number, Number> areaChart = (LineChart<Number, Number>) drawStatisticForBankByYears(stat, bankName);
            panel.getChildren().clear();
            panel.getChildren().addAll(areaChart);
        }
    }

    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
        initCollections();
    }

    private void initCollections() {
        bankMap = mainModel.getBankMap();
        if (bankMap == null) return;
        ObservableList<String> bankNames = FXCollections.observableArrayList(bankMap.values());
        bankNameComboBox.setItems(bankNames);
    }

    public void setBankMap(Map<String, String> bankMap) {
        this.bankMap = bankMap;
    }

    public void drawPrognosChart(ActionEvent actionEvent) {
        String bankName = bankNameComboBox.getValue();
        Map<String,List> stat = mainModel.getStatisticForBank(bankName);
        Integer prognosYear = null;
        Double prognosticationValue = null;
        try{
            prognosYear = Integer.parseInt(yearTextField.getText());
            prognosticationValue = mainModel.getPrognosticationValue(bankName,prognosYear);
        } catch (NumberFormatException e){
            e.printStackTrace();
            alert.setContentText("Неверный формат прогнозируемого года");
            alert.showAndWait();
        }
        if(bankName!=null && prognosYear!=null) {
            Map<String,Number> prognosticationMap = new HashMap<>();
            prognosticationMap.put("year",prognosYear);
            prognosticationMap.put("value",prognosticationValue);
            LineChart<Number, Number> areaChart = (LineChart<Number, Number>) ChartDrawer.drawPrognosticationForBankForYears(stat, bankName, prognosticationMap);
            panel.getChildren().clear();
            panel.getChildren().addAll(areaChart);
            resultTextField.setText(prognosticationValue.toString());
        }
    }
}
