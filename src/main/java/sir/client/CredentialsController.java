package sir.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import sir.server.connection.ConnectionPool;
import sir.server.connection.Credentials;
import sir.server.mysql.MySqlConnection;
import sir.server.postgres.PostGresConnection;
import sir.server.postgres.PostGresQuerys;

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
    private AnchorPane anchor;
    private static Label error;


    public void initialize() {

        setLabel();
    }




    public void connect() throws IOException {
        getCredentials();
        String serverName = getTabName();
        if (serverName.contains("mysql")){
            MySqlConnection mysql = new MySqlConnection();
            mysql.connect();
            setAppPage();
        } else if (serverName.contains("postgres")) {
            PostGresConnection postgres = new PostGresConnection();
            postgres.connect();
            setAppPage();
            PostGresQuerys.crdMemory(user.getText(),pass.getText(),ip.getText(),port.getText());
        }
    }

    public void back () {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/choose.fxml"));
            Tab tab = MainController.tabPane.getSelectionModel().getSelectedItem();
            tab.setContent(parent);
            tab.setText("New Connection");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }



    private void setAppPage() throws IOException {
        if (ConnectionPool.connection != null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/aplication.fxml"));
            Tab tab = MainController.tabPane.getSelectionModel().getSelectedItem();
            tab.setText(tab.getText() + serverName.getText());
            tab.setContent(parent);
        }
    }


    private void getCredentials() {
            Credentials.setIp(ip.getText());
            Credentials.setPort(port.getText());
            Credentials.setUser(user.getText());
            Credentials.setPass(pass.getText());
            Credentials.setName(serverName.getText());
    }



    public String getTabName() {
        Tab tab = MainController.tabPane.getSelectionModel().getSelectedItem();
        return tab.getText().toLowerCase();
    }


    private void setLabel() {
        error = new Label();
        error.setStyle("-fx-text-fill:red");
        error.setAlignment(Pos.CENTER);
        anchor.getChildren().add(error);
        AnchorPane.setRightAnchor(error, 100.0);
        AnchorPane.setLeftAnchor(error, 100.0);
        AnchorPane.setBottomAnchor(error, 180.0);
    }

    public static void getError(String error) {
        CredentialsController.error.setText(error);
    }


}
