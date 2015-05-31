package com.privat.bki.desktopapp.gui.statistic;

import com.privat.bki.desktopapp.gui.main.MainModel;
import com.privat.bki.desktopapp.utils.ChartDrawer;
import javafx.event.ActionEvent;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.util.List;
import java.util.Map;

/**
 * Created by sting on 5/31/15.
 */
public class StatisticWindowController {

    public ComboBox<String> bankNameComboBox;
    public AreaChart<Number,Number> areaChart;
    public Button applyButton;
    private MainModel mainModel;
    private Map<String, String> bankMap;

    public void drawChart(ActionEvent actionEvent) {
        String bankName = bankNameComboBox.getValue();
        Map<String,List> stat = mainModel.getStatisticForBank(bankName);
        if(bankName!=null)
            areaChart= (AreaChart<Number, Number>) ChartDrawer.drawStatisticForBankByYears(stat,bankName, areaChart);

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
