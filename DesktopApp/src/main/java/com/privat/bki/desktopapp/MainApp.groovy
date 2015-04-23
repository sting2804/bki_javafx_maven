package com.privat.bki.desktopapp


import com.privat.bki.desktopapp.gui.main.MainModel
import javafx.application.Application
import javafx.stage.Stage
import org.apache.log4j.xml.DOMConfigurator
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

class MainApp extends Application {

    private static final ApplicationContext context = new ClassPathXmlApplicationContext("/spring/spring-config.xml")

    private MainModel mainModel
    @Override
    void start(Stage stage) throws Exception {


        mainModel = context.getBean(MainModel.class)
        mainModel.callMainWindow(stage)
    }

    static void main(String[] args) {
        String logPropertyPath = "/Users/sting/GitHub/bki_javafx_maven/demoMavenIntellij/src/main/resources/log4j.xml"
        DOMConfigurator.configureAndWatch(logPropertyPath)
        launch(args)
    }

}
