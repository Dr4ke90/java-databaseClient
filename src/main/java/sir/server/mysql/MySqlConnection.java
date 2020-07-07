package sir.server.mysql;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import sir.client.Credentials;
import sir.client.HomeController;
import sir.client.ImageController;
import sir.server.connection.*;

import java.sql.*;
import java.util.Random;


public class MySqlConnection {

    private static ActionsCollector actionsCollector;
    private static Label error;


    public MySqlConnection() {
        actionsCollector = new ActionsCollector();
    }


    public void connect(String serverName) {
        final String url = Credentials.getJdbc() + Credentials.getIp() + ":" + Credentials.getPort() + "/" + Credentials.getDatabase();
        try {
            Connection connection = DriverManager.getConnection(url, Credentials.getUser(), Credentials.getPass());
            if (connection != null) {
                Random random = new Random();
                int tabId = random.nextInt();
                Tab tab = new Tab("MySQL/" + serverName);
                tab.setId(String.valueOf(tabId));
                tab.setGraphic(ImageController.loadMysqlImage());
                ActionsCollector.createCollector(tab);
                ConnectionPool.add(connection,tab);
                actionsCollector.add("Connection succesfull");
                HomeController homeController = new HomeController();
                homeController.addTabContent(tab);
            }
        } catch (SQLException e) {
            error.setText("Access denied");
        }
    }

    public static void getLabel (Label label) {
        error = label;
    }




}

