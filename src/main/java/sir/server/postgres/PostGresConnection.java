package sir.server.postgres;

import javafx.scene.control.*;
import sir.client.HomeController;
import sir.client.ImageController;
import sir.server.connection.ActionsCollector;
import sir.client.Credentials;
import sir.server.connection.ConnectionPool;

import java.sql.*;
import java.util.Random;


public class PostGresConnection {

    private static Label error;
    private static ActionsCollector actionsCollector;
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


    public void connect(String serverName) {
        final String url = Credentials.getJdbc() + ip + ":" + port + "/";
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            if (connection != null) {
                Tab tab = new Tab("PostGreSQL/" + serverName);
                tab.setGraphic(ImageController.loadPostgresImage());
                Random random = new Random();
                int tabId = random.nextInt();
                tab.setId(String.valueOf(tabId));
                tab.setGraphic(ImageController.loadPostgresImage());
                ActionsCollector.createCollector(tab);
                ConnectionPool.add(connection, tab);
                actionsCollector.add("Connection succesfull");
                HomeController homeController = new HomeController();
                homeController.addTabContent(tab);
            }
        } catch (SQLException e) {
            error.setText("Access denied");
        }
    }


    public static void getLabel(Label label) {
        error = label;
    }

}
