package sir.server.oracle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import sir.client.CredentialsController;
import sir.client.MainController;
import sir.server.connection.ActionsCollector;
import sir.server.connection.ConnectionPool;
import sir.server.connection.Credentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnection {

   public static ActionsCollector actionsCollector ;

    public OracleConnection () {
        actionsCollector = new ActionsCollector();
    }

    public void connect(String name) {
        String sid = Credentials.getSid();
        String driverType = Credentials.getDriverType();
        String ip = Credentials.getIp();
        String port = Credentials.getPort();
        final String url = "jdbc:oracle:" + driverType + ":@" + ip + ":" + port + ":" + sid;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection(url, Credentials.getUser(), Credentials.getPass());
            ConnectionPool.add(connection);
            ConnectionPool.connection = connection;
            actionsCollector.createCollector();
            if (connection != null) {
                actionsCollector.add("Connection succesfull");
                setAppPage(name);
            }
        } catch (SQLException | ClassNotFoundException e) {
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
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }




}
