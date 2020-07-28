package sir.server.mysql;

import sir.client.connSetup.Credentials;
import sir.client.connSetup.CredentialsObjects;
import sir.client.home.ImageController;
import sir.client.home.TabPaneService;
import sir.client.newtabs.NewTabService;
import sir.client.newtabs.TextAreaService;
import sir.server.connection.CollectorPoolManager;

import java.sql.*;


public class MySqlConnection {


    public void connect() {
        final String url = Credentials.getJdbc() + Credentials.getIp() + ":" + Credentials.getPort() + "/" + Credentials.getDatabase();
        try {
            Connection connection = DriverManager.getConnection(url, Credentials.getUser(), Credentials.getPass());
            setConnection(connection);
        } catch (SQLException e) {
            CredentialsObjects.getErrorMessage().setText("Access denied");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setConnection (Connection connection) throws Exception {
        if (connection != null) {
            TabPaneService tabPaneService = new TabPaneService();
            tabPaneService.createNewTab(connection);
            CollectorPoolManager collectorPoolManager = new CollectorPoolManager();
            collectorPoolManager.addAction("Connection succesfull");
        }
    }



}

