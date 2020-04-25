package sir.choosepage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import sir.maipage.MainController;
import sir.maipage.ServerConnection;

import java.io.FileInputStream;

public class ChooseServerController {
    
    @FXML
    Button mysqlButton;
    @FXML
    Button posgreButton;



    public void getMySqlConn() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/credentials.fxml"));
        Tab tab = MainController.tabPane.getSelectionModel().getSelectedItem();
        tab.setContent(parent);
        ServerConnection sc = new ServerConnection();
        sc.setDriver("jdbc:mysql://");
        tab.setText(mysqlButton.getText());
    }

    public void getPosGreConn() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/credentials.fxml"));
        Tab tab = MainController.tabPane.getSelectionModel().getSelectedItem();
        tab.setContent(parent);
        ServerConnection sc = new ServerConnection();
        sc.setDriver("jdbc:posgresql://");
        tab.setText(posgreButton.getText());
    }




}
