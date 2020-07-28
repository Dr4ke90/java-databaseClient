package sir.server.mysql;

import javafx.scene.control.*;
import sir.client.newtabs.NewTabObjects;
import sir.server.connection.CollectorPoolManager;
import sir.client.home.ImageController;
import sir.server.connection.ConnectionPoolManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlList {


    private static Connection connection;


    public MySqlList() {
        connection = ConnectionPoolManager.getConnectionFromPool();
    }


    public void getList() {
        TreeItem<String> databases = new TreeItem<>("Databases");
        NewTabObjects.getList().setRoot(databases);
        NewTabObjects.getList().setShowRoot(false);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT `schema_name` from INFORMATION_SCHEMA.SCHEMATA  WHERE `schema_name` NOT IN('information_schema', 'mysql', 'performance_schema', 'sys');");
            while (resultSet.next()) {
                String db = resultSet.getString(1);
                TreeItem<String> dbItem = new TreeItem<>(db, ImageController.addSchemaImage());
                databases.getChildren().add(dbItem);
                getTables(db, dbItem);
                getViews(db, dbItem);
            }
            NewTabObjects.getTableMessage().setItems(CollectorPoolManager.getCollectorFromPool());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getTables(String db, TreeItem<String> dbItem) throws SQLException {
        TreeItem<String> tables = new TreeItem<>("Tables", ImageController.addTablesImage());
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = " + "'" + db + "';");
        while (result.next()) {
            String table = result.getString(1);
            TreeItem<String> tableItem = new TreeItem<>(table, ImageController.addTableImage());
            tables.getChildren().add(tableItem);
        }
        dbItem.getChildren().add(tables);
    }


    public void getViews(String db, TreeItem<String> dbItem) throws SQLException {
        TreeItem<String> views = new TreeItem<>("Views", ImageController.addViewIcon());
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT table_name view_name FROM information_schema.tables \n" +
                "WHERE table_type = 'VIEW' AND table_schema like " + "'" + db + "';");
        while (result.next()) {
            String view = result.getString(1);
            TreeItem<String> viewsItem = new TreeItem<>(view, ImageController.addViewIcon());
            views.getChildren().add(viewsItem);
        }
        dbItem.getChildren().add(views);
    }

}
