package com.privat.bki.desktopapp.gui.statistic;

import com.privat.bki.desktopapp.gui.main.MainModel;
import com.privat.bki.desktopapp.utils.ChartDrawer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.privat.bki.desktopapp.utils.ChartDrawer.*;

public class StatisticWindowController {

    public ComboBox<String> bankNameComboBox;
    public Button applyButton;
    public Pane panel;
    public TextField yearTextField;
    public Button applyPrognosButton;
    public TextField resultTextField;
    public ComboBox<String> statisticTypeComboBox;
    private MainModel mainModel;
    private Map<String, String> bankMap;
    private Alert alert = new Alert(Alert.AlertType.ERROR);


    public void drawChart(ActionEvent actionEvent) {
        String bankName = bankNameComboBox.getValue();
        Map<String, List> stat;
        int statNumber = statisticTypeComboBox.getSelectionModel().getSelectedIndex();
        if (statNumber == 0)
            stat = mainModel.getStatisticForBank(bankName);
        else if (statNumber == 1)
            stat = mainModel.getStatisticOfClientAgesByBank(bankName);
        else
            return;
        drawChart(bankName, stat, statNumber);
    }

    private void drawChart(String bankName, Map<String, List> stat, int statNumber) {
        Chart areaChart;
        if (bankName != null) {
            if (statNumber == 0)
                areaChart = drawStatisticForBankByYears(stat, bankName);
            else if (statNumber == 1)
                areaChart = drawStatisticForCreditAgesByBankAndYears(stat, bankName);
            else
                return;
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
        List<String> statisticTypesList = new ArrayList<>();
        statisticTypesList.add("Количество кредитов");
        statisticTypesList.add("Кредитируемый возраст");
        ObservableList<String> statisticTypes = FXCollections.observableArrayList(statisticTypesList);
        bankNameComboBox.setItems(bankNames);
        statisticTypeComboBox.setItems(statisticTypes);
    }

    public void setBankMap(Map<String, String> bankMap) {
        this.bankMap = bankMap;
    }

    public void drawPrognosChart(ActionEvent actionEvent) {
        String bankName = bankNameComboBox.getValue();
        Integer prognosYear;
        try {
            prognosYear =
                    Integer.parseInt(yearTextField.getText());
        } catch (Exception e) {
            prognosYear = null;
        }
        Map<String, List> stat;
        int statNumber = statisticTypeComboBox.getSelectionModel().getSelectedIndex();
        if (statNumber == 0)
            stat = mainModel.getStatisticForBank(bankName);
        else if (statNumber == 1)
            stat = mainModel.getStatisticOfClientAgesByBank(bankName);
        else
            return;
        drawPrognosChart(bankName, stat, prognosYear, statNumber);
    }

    public void drawPrognosChart(String bankName, Map<String, List> stat, Integer prognosYear, int statNumber) {
        Double prognosticationValue = null;
        String prognosticationValueForAges = null;
        try {
            prognosYear = Integer.parseInt(yearTextField.getText());
            if (statNumber == 0)
                prognosticationValue = mainModel.getPrognosticationValue(bankName, prognosYear);
            else if (statNumber == 1)
                prognosticationValueForAges = mainModel.getPrognosticationValueForCreditAges(bankName, prognosYear);
            else
                return;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            alert.setContentText("Неверный формат прогнозируемого года");
            alert.showAndWait();
        }
        if (bankName != null && prognosYear != null) {
            Chart areaChart;
            Map prognosticationMap = new HashMap<>();
            prognosticationMap.put("year", prognosYear);
            if (statNumber == 0) {
                prognosticationMap.put("value", prognosticationValue);
                areaChart = drawPrognosticationForBankForYears(stat, bankName, prognosticationMap);
            }
            else if (statNumber == 1) {
                prognosticationMap.put("value", prognosticationValueForAges);
                areaChart = drawPrognosticationForCreaditAgeByBankForYears(stat, bankName, prognosticationMap);
            }
            else
                return;
            panel.getChildren().clear();
            panel.getChildren().addAll(areaChart);
            if (prognosticationValue != null)
                resultTextField.setText(prognosticationValue.toString());
        }
    }
}
