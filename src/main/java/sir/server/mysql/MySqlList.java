package sir.server.mysql;

import javafx.scene.control.*;
import sir.server.connection.ActionsCollector;
import sir.server.connection.ConnectionPool;
import sir.server.connection.ImageController;
import sir.server.connection.Messages;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlList {


    public static ResultSet resultSet;
    private static Connection connection;
    private static ActionsCollector actionsCollector;
    private static ImageController imageController;


    public MySqlList() {
        connection = ConnectionPool.connection;
        actionsCollector = new ActionsCollector();
        imageController = new ImageController();
    }


    public void getList(TreeView<String> list, TableView<Messages> actions) {
        TreeItem<String> databases = new TreeItem<>("Databases");
        list.setRoot(databases);
        list.setShowRoot(false);
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT `schema_name` from INFORMATION_SCHEMA.SCHEMATA  WHERE `schema_name` NOT IN('information_schema', 'mysql', 'performance_schema', 'sys');");
            while (resultSet.next()) {
                String db = resultSet.getString(1);
                TreeItem<String> dbItem = new TreeItem<>(db,imageController.addSchemaImage());
                databases.getChildren().add(dbItem);
                getTables(db, dbItem);
                getViews(db, dbItem);
            }
            actions.setItems(ActionsCollector.collector);
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }
    }

    public void getTables(String db, TreeItem<String> dbItem) throws SQLException {
        TreeItem<String> tables = new TreeItem<>("Tables",imageController.addTablesImage());
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = " + "'" + db + "';");
        while (result.next()) {
            String table = result.getString(1);
            TreeItem<String> tableItem = new TreeItem<>(table,imageController.addTableImage());
            tables.getChildren().add(tableItem);
        }
        dbItem.getChildren().add(tables);
    }


    public void getViews(String db, TreeItem<String> dbItem) throws SQLException {
        TreeItem<String> views = new TreeItem<>("Views",imageController.addViewIcon());
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT table_name view_name FROM information_schema.tables \n" +
                "WHERE table_type = 'VIEW' AND table_schema like " + "'" + db + "';");
        while (result.next()) {
            String view = result.getString(1);
            TreeItem<String> viewsItem = new TreeItem<>(view,imageController.addViewIcon());
            views.getChildren().add(viewsItem);
        }
        dbItem.getChildren().add(views);
    }

}
