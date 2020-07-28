package sir.server.oracle;

import javafx.scene.control.*;
import sir.client.newtabs.NewTabObjects;
import sir.server.connection.CollectorPoolManager;
import sir.server.connection.ConnectionPoolManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleList {

    private static ResultSet resultSet;
    private static Connection connection;


    public OracleList() {
        connection = ConnectionPoolManager.getConnectionFromPool();
    }


    public void getList() {
        TreeItem<String> databases = new TreeItem<>("Databases");
        TreeItem<String> tables = new TreeItem<>("Tables");
        databases.getChildren().add(tables);
        NewTabObjects.getList().setRoot(databases);
        NewTabObjects.getList().setShowRoot(false);
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT table_name FROM user_tables ORDER BY table_name");
            while (resultSet.next()) {
                String db = resultSet.getString(1);
                TreeItem<String> dbItem = new TreeItem<>(db);
                tables.getChildren().add(dbItem);
            }
            getTables(databases);
            NewTabObjects.getTableMessage().setItems(CollectorPoolManager.getCollectorFromPool());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void getTables(TreeItem<String> user) {
        TreeItem<String> tables = new TreeItem<>("Other Users");
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT username FROM all_users");
            while (resultSet.next()) {
                String users = resultSet.getString(1);
                TreeItem<String> dbItem = new TreeItem<>(users);
                tables.getChildren().add(dbItem);
            }
            user.getChildren().add(tables);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}