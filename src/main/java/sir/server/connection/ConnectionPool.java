package sir.server.connection;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import sir.client.MainController;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ConnectionPool {

    public static Connection connection;
    private static final Map<String, Connection> pool = new HashMap<>();
    private static TabPane mainTab;




    public static void add(Connection connection) {
        Tab tab = mainTab.getSelectionModel().getSelectedItem();
        pool.put(tab.getId(), connection);
    }


    public static void switchConnection(Tab tab) {
        if (connection != null) {
            String server = tab.getId();
            if (pool.containsKey(server)) {
                connection = pool.get(server);
            }
        }
    }


    public static void getInfo(Label name, Label host, Label user) {
            try {
                DatabaseMetaData dbm = connection.getMetaData();
                name.setText(dbm.getDatabaseProductName() + ":" + Credentials.getServerName());
                user.setText(dbm.getUserName());
                host.setText(Credentials.getIp() + ":" + Credentials.getPort());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }


    public static void getTabPane (TabPane tabPane) {
        mainTab = tabPane;
    }



    }


