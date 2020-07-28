package sir.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import sir.client.home.HomeObjects;
import sir.client.home.StageService;
import sir.server.connection.ConnectionPool;
import sir.server.connection.ConnectionPoolManager;

import java.io.FileInputStream;
import java.sql.SQLException;

public class Start extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StageService stageService = new StageService();
        stageService.setScene(primaryStage);
        stageService.close(primaryStage);

    }

}
