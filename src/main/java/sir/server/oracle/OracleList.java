package sir.server.oracle;

import javafx.scene.control.*;
import sir.server.connection.ActionsCollector;
import sir.server.connection.ConnectionPool;
import sir.server.connection.Messages;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleList {

    public static ResultSet resultSet;
    public static Connection connection;
    public static ActionsCollector actionsCollector;


    public OracleList() {
        connection = ConnectionPool.connection;
        actionsCollector = new ActionsCollector();
    }


    public void getList(TreeView<String> list, TableView<Messages> actions) {
        TreeItem<String> databases = new TreeItem<>("Databases");
        TreeItem<String> tables = new TreeItem<>("Tables");
        databases.getChildren().add(tables);
        list.setRoot(databases);
        list.setShowRoot(false);
        try {
            Statement statement = ConnectionPool.connection.createStatement();
            resultSet = statement.executeQuery("SELECT table_name FROM user_tables ORDER BY table_name");
            while (resultSet.next()) {
                String db = resultSet.getString(1);
                TreeItem<String> dbItem = new TreeItem<>(db);
                tables.getChildren().add(dbItem);
            }
            getTables(databases);
            actions.setItems(ActionsCollector.collector);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void getTables(TreeItem<String> user) {
        TreeItem<String> tables = new TreeItem<>("Other Users");
        try {
            Statement statement = ConnectionPool.connection.createStatement();
            resultSet = statement.executeQuery("SELECT username FROM all_users");
            while (resultSet.next()) {
                String users = resultSet.getString(1);
                TreeItem<String> dbItem = new TreeItem<>(users);
                tables.getChildren().add(dbItem);
            }
            user.getChildren().add(tables);
        } catch (SQLException e) {

        }
    }
}