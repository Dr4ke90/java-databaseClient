package sir.server.oracle;

import sir.client.connSetup.CredentialsObjects;
import sir.client.home.TabPaneService;
import sir.client.connSetup.Credentials;
import sir.server.connection.CollectorPoolManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnection {



    public void connect() {
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
                TabPaneService tabPaneService = new TabPaneService();
                tabPaneService.createNewTab(connection);
                CollectorPoolManager collectorPool = new CollectorPoolManager();
                collectorPool.addAction("Connection succesfull");
            }
        } catch (SQLException | ClassNotFoundException e) {
          CredentialsObjects.getErrorMessage().setText("Access denied");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}


