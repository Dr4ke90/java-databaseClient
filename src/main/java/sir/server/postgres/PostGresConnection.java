package sir.server.postgres;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import sir.server.connection.ActionsCollector;
import sir.server.connection.ConnectionPool;
import sir.client.CredentialsController;
import sir.client.MainController;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;


public class PostGresConnection {


    private static ActionsCollector actionsCollector;
    private static TabPane mainTab;
    public static String ip;
    public static String port;
    public static String user;
    public static String pass;


    public PostGresConnection() {
        actionsCollector = new ActionsCollector();
    }

    public void getCredential(String unIp, String unPort, String unUser, String passs) {
        user = unUser;
        pass = passs;
        ip = unIp;
        port = unPort;
    }


    public void connect(String name) {
        final String url = "jdbc:postgresql://" + ip + ":" + port + "/";
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            ConnectionPool.add(connection);
            ConnectionPool.connection = connection;
            actionsCollector.createCollector();
            if (connection != null) {
                actionsCollector.add("Connection Succesfull");
                setAppPage(name);
            }
        } catch (SQLException e) {
            CredentialsController.getError("Access Denied");
            System.out.println(e.getMessage());
        }
    }



    private void setAppPage(String name) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/aplication.fxml"));
            Tab tab = mainTab.getSelectionModel().getSelectedItem();
            tab.setText(tab.getText() + name);
            tab.setContent(parent);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getTabPane (TabPane tabPane) {
        mainTab = tabPane;
    }


}
