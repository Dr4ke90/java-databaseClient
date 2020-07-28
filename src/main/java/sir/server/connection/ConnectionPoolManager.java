package sir.server.connection;

import sir.client.home.TabPaneService;

import java.sql.Connection;

public class ConnectionPoolManager {




    public static Connection getConnectionFromPool () {
        String id = TabPaneService.getSelectedTab().getId();
        return ConnectionPool.getConnectionPool().get(id);
    }



}
