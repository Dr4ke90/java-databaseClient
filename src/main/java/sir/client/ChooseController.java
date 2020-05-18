package sir.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import sir.server.connection.ImageController;
import java.io.FileInputStream;

public class ChooseController {

    private static TabPane mainTab;


    public void setMysqlConnection() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/credentials.fxml"));
        Tab tab = mainTab.getSelectionModel().getSelectedItem();
        tab.setContent(parent);
        tab.setGraphic(ImageController.loadMysqlImage());
        tab.setText("MySQL/");


    }


    public void setPostGresConnection() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/credentials.fxml"));
        Tab tab = mainTab.getSelectionModel().getSelectedItem();
        tab.setContent(parent);
        tab.setGraphic(ImageController.loadPostgresImage());
        tab.setText("PostGreSQL/");
    }


    public void setOracleConnection() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/credentials.fxml"));
        Tab tab = mainTab.getSelectionModel().getSelectedItem();
        tab.setContent(parent);
        tab.setGraphic(ImageController.loadOracleImage());
        tab.setText("Oracle/");
    }


    public static void getTabPane(TabPane tabPane) {
       mainTab = tabPane;
    }

}
