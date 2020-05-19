package sir.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sir.server.connection.Credentials;
import sir.server.mysql.MySqlConnection;
import sir.server.oracle.OracleConnection;
import sir.server.postgres.PostGresConnection;

import java.io.FileInputStream;
import java.io.IOException;


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
    private TextField serverName;
    @FXML
    private TextField database;
    @FXML
    private TextField driver;
    @FXML
    private TextField sid;
    private static Label error;
    private static TabPane mainTab;


    public void initialize() {
        activateField();
        setLabel();
    }


    public void connect() {
        getCredentials();
        String tabName = mainTab.getSelectionModel().getSelectedItem().getText().toLowerCase();
        if (tabName.contains("mysql")) {
            MySqlConnection mysql = new MySqlConnection();
            mysql.connect(serverName.getText());
        } else if (tabName.contains("postgres")) {
            PostGresConnection postgres = new PostGresConnection();
            postgres.getCredential(ip.getText(), port.getText(), user.getText(), pass.getText());
            postgres.connect(serverName.getText());
        } else if (tabName.contains("oracle")) {
            OracleConnection oracle = new OracleConnection();
            oracle.connect(serverName.getText());
        }
    }

    public void back() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/choose.fxml"));
            Tab tab = mainTab.getSelectionModel().getSelectedItem();
            tab.setContent(parent);
            tab.setGraphic(new ImageView());
            tab.setText("New Connection");
            desableField();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }


    private void getCredentials() {
            Credentials.setIp(ip.getText());
            Credentials.setPort(port.getText());
            Credentials.setUser(user.getText());
            Credentials.setPass(pass.getText());
            Credentials.setServerName(serverName.getText());
            Credentials.setDriverType(driver.getText());
            Credentials.setSid(sid.getText());
            Credentials.setDatabase(database.getText());
    }

    public void activateField () {
        String tabName = mainTab.getSelectionModel().getSelectedItem().getText().toLowerCase();
        if (tabName.contains("mysql")) {
            database.setDisable(false);
        } else if (tabName.contains("oracle")) {
            driver.setDisable(false);
            sid.setDisable(false);
        }
    }

    public void desableField () {
            database.setDisable(true);
            driver.setDisable(true);
            sid.setDisable(true);
        }


    public static void getMainTab(TabPane tabPane) {
        mainTab = tabPane;
    }


    private void setLabel() {
        error = new Label();
        error.setStyle("-fx-text-fill:red");
        error.setAlignment(Pos.CENTER);
        AnchorPane.setRightAnchor(error, 20.0);
        AnchorPane.setLeftAnchor(error, 20.0);
        AnchorPane.setBottomAnchor(error, 20.0);
    }

    public static void getError(String error) {
        CredentialsController.error.setText(error);
    }


}
