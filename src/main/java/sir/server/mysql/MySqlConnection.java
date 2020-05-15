package sir.server.mysql;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import sir.client.MainController;
import sir.server.connection.*;
import sir.client.CredentialsController;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;


public class MySqlConnection {

    private static ActionsCollector actionsCollector;

    public MySqlConnection() {
        actionsCollector = new ActionsCollector();
    }

    public void connect(String name) {
        final String url = "jdbc:mysql://" + Credentials.getIp() + ":" + Credentials.getPort() + "/";
        try {
            Connection connection = DriverManager.getConnection(url, Credentials.getUser(), Credentials.getPass());
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
            Tab tab = MainController.tabPane.getSelectionModel().getSelectedItem();
            tab.setText(tab.getText() + name);
            tab.setContent(parent);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



}
