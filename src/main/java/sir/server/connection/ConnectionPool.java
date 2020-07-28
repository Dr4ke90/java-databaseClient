package sir.server.connection;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import sir.client.home.HomeObjects;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class ConnectionPool {

    private static final Map<String, Connection> connectionPool = new HashMap<>();


    public static Map<String, Connection> getConnectionPool() {
        return connectionPool;
    }



}


