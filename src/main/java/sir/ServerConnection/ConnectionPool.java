package sir.ServerConnection;
import javafx.scene.control.Tab;
import sir.clientSide.MainController;
import java.sql.Connection;
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
}

