package sir.client.home;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import sir.server.connection.ConnectionPoolManager;

import java.io.FileInputStream;
import java.sql.SQLException;

public class StageService {



    public void setScene (Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/home.fxml"));
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(600);
        primaryStage.setResizable(true);
        primaryStage.setTitle("SIR Universal Workbench");
        primaryStage.show();
    }



    public void close (Stage primaryStage) {
        TabPane tabPane = HomeObjects.getTabPane();
        primaryStage.setOnCloseRequest(event -> {
            try {
                if (ConnectionPoolManager.getConnectionFromPool() != null) {
                    if (tabPane.getTabs().size() > 2) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("Close all connection?");
                        alert.setTitle("Confirmation!");
                        ButtonType buttonType = alert.showAndWait().get();
                        if (buttonType == ButtonType.OK) {
                            primaryStage.close();
                        } else if (buttonType == ButtonType.CANCEL) {
                            event.consume();
                        }
                    } else if (ConnectionPoolManager.getConnectionFromPool().isClosed()) {
                        primaryStage.close();
                    }else {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("Close connection?");
                        alert.setTitle("Confirmation!");
                        ButtonType buttonType = alert.showAndWait().get();
                        if (buttonType == ButtonType.OK) {
                            primaryStage.close();
                        } else if (buttonType == ButtonType.CANCEL) {
                            event.consume();
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }
}
