package sir.server.postgres;

import sir.client.connSetup.CredentialsObjects;
import sir.client.home.TabPaneService;
import sir.client.connSetup.Credentials;
import sir.server.connection.CollectorPoolManager;

import java.sql.*;


public class PostGresConnection {

    public static String ip;
    public static String port;
    public static String user;
    public static String pass;



    public void getCredential(String unIp, String unPort, String unUser, String passs) {
        user = unUser;
        pass = passs;
        ip = unIp;
        port = unPort;
    }


    public void connect() {
        String url = Credentials.getJdbc() + ip + ":" + port + "/";
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            if (connection != null) {
                TabPaneService tabPaneService = new TabPaneService();
                tabPaneService.createNewTab(connection);
                CollectorPoolManager collectorPool = new CollectorPoolManager();
                collectorPool.addAction("Connection succesfull");
            }
        } catch (SQLException e) {
            CredentialsObjects.getErrorMessage().setText("Access denied");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
