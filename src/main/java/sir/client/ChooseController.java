package sir.client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import java.io.FileInputStream;

public class ChooseController {


    public void setMysqlConnection() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/credentials.fxml"));
        Tab tab = MainController.tabPane.getSelectionModel().getSelectedItem();
        tab.setContent(parent);
        tab.setText("MySQL/");

    }


    public void setPostGresConnection() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/credentials.fxml"));
        Tab tab = MainController.tabPane.getSelectionModel().getSelectedItem();
        tab.setContent(parent);
        tab.setText("PostGreSQL/");
    }

    public void setOracleConnection() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/credentials.fxml"));
        Tab tab = MainController.tabPane.getSelectionModel().getSelectedItem();
        tab.setContent(parent);
        tab.setText("Oracle/");
    }



}
