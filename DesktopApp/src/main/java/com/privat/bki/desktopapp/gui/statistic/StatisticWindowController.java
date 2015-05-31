package com.privat.bki.desktopapp.gui.statistic;

import com.privat.bki.desktopapp.gui.main.MainModel;
import com.privat.bki.desktopapp.utils.ChartDrawer;
import javafx.event.ActionEvent;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Map;

public class StatisticWindowController {

    public ComboBox<String> bankNameComboBox;
    public BarChart<Number,Number> areaChart;
    public Button applyButton;
    public Pane panel;
    private MainModel mainModel;
    private Map<String, String> bankMap;

    public void drawChart(ActionEvent actionEvent) {
        String bankName = bankNameComboBox.getValue();
        Map<String,List> stat = mainModel.getStatisticForBank(bankName);
        if(bankName!=null) {
            AreaChart<Number, Number> areaChart = ChartDrawer.drawStatisticForBankByYears(stat, bankName);
            panel.getChildren().clear();
            panel.getChildren().addAll(areaChart);
        }
    }

    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    public void setBankMap(Map<String, String> bankMap) {
        this.bankMap = bankMap;
    }

    public void setScreenForms() {

    }
}
