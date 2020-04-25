package sir.maipage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import sir.aplication.AppController;
import sir.credentialpage.CredentialsController;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ServerConnection {

    private static String ip;
    private static String port;
    private static String user;
    private static String pass;
    private static String driver;
    public static Connection connection;


    public void setDriver(String driver) {
        ServerConnection.driver = driver;
    }

    public void setIp(String ip) {
        ServerConnection.ip = ip;
    }

    public void setPort(String port) {
        ServerConnection.port = port;
    }

    public void setUser(String user) {
        ServerConnection.user = user;
    }

    public void setPass(String pass) {
        ServerConnection.pass = pass;
    }


    public static void connect() {
        connection = null;
        String url = driver + ip + ":" + port + "/";
        FXMLLoader fxmlLoader = new FXMLLoader();

        try {
            connection = DriverManager.getConnection(url, user, pass);
            Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/aplication.fxml"));
            Tab tab = MainController.tabPane.getSelectionModel().getSelectedItem();
            tab.setContent(parent);
            AppController app = new AppController();
            app.setMessages("Connection succesfull");
        } catch (SQLException | IOException e) {
            CredentialsController.getError("Access Denied");
        }
    }

}
