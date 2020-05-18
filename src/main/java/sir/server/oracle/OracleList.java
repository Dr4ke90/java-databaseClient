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


    public void getList (TreeView<String> list, TableView<Messages> actions) {
        TreeItem<String> databases = new TreeItem<>("Databases");
        list.setRoot(databases);
        list.setShowRoot(true);
        try {
            Statement statement = ConnectionPool.connection.createStatement();
            resultSet = statement.executeQuery("SELECT username FROM all_users");
            while (resultSet.next()) {
                String db = resultSet.getString(1);
                TreeItem<String> dbItem = new TreeItem<>(db);
                databases.getChildren().add(dbItem);
            }
            actions.setItems(ActionsCollector.collector);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}