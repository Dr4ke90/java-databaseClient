package sir.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import sir.server.connection.ImageController;
import java.io.FileInputStream;

public class ChooseController {

    private static TabPane mainTab;


    public void setMysqlConnection() throws Exception {
        Tab tab = mainTab.getSelectionModel().getSelectedItem();
        tab.setGraphic(ImageController.loadMysqlImage());
        tab.setText("MySQL/");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/credential.fxml"));
        tab.setContent(parent);

    }


    public void setPostGresConnection() throws Exception {
        Tab tab = mainTab.getSelectionModel().getSelectedItem();
        tab.setGraphic(ImageController.loadPostgresImage());
        tab.setText("PostGreSQL/");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/credential.fxml"));
        tab.setContent(parent);

    }


    public void setOracleConnection() throws Exception {
        Tab tab = mainTab.getSelectionModel().getSelectedItem();
        tab.setGraphic(ImageController.loadOracleImage());
        tab.setText("Oracle/");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/credential.fxml"));
        tab.setContent(parent);

    }


    public static void getTabPane(TabPane tabPane) {
       mainTab = tabPane;
    }




}
