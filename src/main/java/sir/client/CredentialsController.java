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
import sir.server.oracle.OracleConnection;
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
    @FXML
    private TextField driver;
    @FXML
    private TextField sid;
    private static Label error;


    public void initialize() {

        setLabel();
    }




    public void connect() {
        getCredentials();
        String tabName = getTabName();
        if (tabName.contains("mysql")){
            MySqlConnection mysql = new MySqlConnection();
            mysql.connect(serverName.getText());
        } else if (tabName.contains("postgres")) {
            PostGresConnection postgres = new PostGresConnection();
            postgres.connect(serverName.getText());
            PostGresQuerys.crdMemory(user.getText(),pass.getText(),ip.getText(),port.getText());
        } else if (tabName.contains("oracle")) {
            OracleConnection oracle = new OracleConnection();
            oracle.connect(serverName.getText());
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






    private void getCredentials() {
            Credentials.setIp(ip.getText());
            Credentials.setPort(port.getText());
            Credentials.setUser(user.getText());
            Credentials.setPass(pass.getText());
            Credentials.setServerName(serverName.getText());
            Credentials.setDriverType(driver.getText());
            Credentials.setSid(sid.getText());
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
        AnchorPane.setRightAnchor(error, 20.0);
        AnchorPane.setLeftAnchor(error, 20.0);
        AnchorPane.setBottomAnchor(error, 20.0);
    }

    public static void getError(String error) {
        CredentialsController.error.setText(error);
    }


}
