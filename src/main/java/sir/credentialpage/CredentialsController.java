package sir.credentialpage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import sir.maipage.MainController;
import sir.maipage.ServerConnection;

import java.io.FileInputStream;


public class CredentialsController {

    @FXML
    private TextField ip;
    @FXML
    private TextField port;
    @FXML
    private TextField user;
    @FXML
    private PasswordField pass;
    @FXML
    private AnchorPane anchor;
    private static Label error;


    public void initialize() {
        setLabel();
    }


    public void setConnection() {
        ServerConnection sc = new ServerConnection();
        sc.setIp(ip.getText());
        sc.setUser(user.getText());
        sc.setPort(port.getText());
        sc.setPass(pass.getText());
        ServerConnection.connect();
    }


    public void goBack() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/choose.fxml"));
        Tab tab = MainController.tabPane.getSelectionModel().getSelectedItem();
        tab.setContent(parent);
        tab.setText("New Connection");
    }


    private void setLabel() {
        error = new Label();
        error.setStyle("-fx-text-fill:red");
        error.setAlignment(Pos.CENTER);
        anchor.getChildren().add(error);
        AnchorPane.setRightAnchor(error, 200.0);
        AnchorPane.setLeftAnchor(error, 200.0);
        AnchorPane.setBottomAnchor(error, 130.0);
    }

    public static void getError(String error) {
        CredentialsController.error.setText(error);
    }


}
