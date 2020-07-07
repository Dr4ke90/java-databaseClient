package sir.server.oracle;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import sir.client.HomeController;
import sir.client.ImageController;
import sir.server.connection.ActionsCollector;
import sir.client.Credentials;
import sir.server.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;

public class OracleConnection {

    private static ActionsCollector actionsCollector;

    private static Label error;

    public OracleConnection() {
        actionsCollector = new ActionsCollector();
    }


    public void connect(String serverName) {
        final String sid = Credentials.getSid();
        final String driverType = Credentials.getDriverType();
        final String ip = Credentials.getIp();
        final String port = Credentials.getPort();
        final String jdbc = Credentials.getJdbc();
        final String url = jdbc + driverType + ":@" + ip + ":" + port + ":" + sid;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection(url, Credentials.getUser(), Credentials.getPass());
            if (connection != null) {
                Random random = new Random();
                int tabId = random.nextInt();
                Tab tab = new Tab("Oracle/" + serverName);
                tab.setId(String.valueOf(tabId));
                tab.setGraphic(ImageController.loadOracleImage());
                ActionsCollector.createCollector(tab);
                ConnectionPool.add(connection, tab);
                actionsCollector.add("Connection succesfull");
                HomeController homeController = new HomeController();
                homeController.addTabContent(tab);
            }
        } catch (SQLException | ClassNotFoundException e) {
            error.setText("Access denied");
        }
    }


    public static void getLabel(Label label) {
        error = label;
    }

}


