package sir.server.connection;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import sir.client.MainController;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ConnectionPool {

    public static Connection connection;
    public static final Map<String, Connection> pool = new HashMap<>();



    public static void getConnection(Connection connection) {
        Tab tab = MainController.tabPane.getSelectionModel().getSelectedItem();
        pool.put(tab.getText() + Credentials.getName(), connection);
    }


    public static void switchConnection(Tab tab) {
        if (connection != null) {
            String server = tab.getText();
            if (pool.containsKey(server)) {
                connection = pool.get(server);
            }
        }
    }


    public static void getInfo(Label name, Label host, Label user) {
            try {
                DatabaseMetaData dbm = connection.getMetaData();
                name.setText(dbm.getDatabaseProductName() + ":" + Credentials.getName());
                user.setText(dbm.getUserName());
                host.setText(Credentials.getIp() + ":" + Credentials.getPort());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }




    }


