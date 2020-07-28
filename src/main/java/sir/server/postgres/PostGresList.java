package sir.server.postgres;

import javafx.scene.control.TreeItem;
import sir.client.newtabs.NewTabObjects;
import sir.server.connection.CollectorPoolManager;
import sir.client.home.ImageController;
import sir.server.connection.ConnectionPoolManager;
import java.sql.*;

public class PostGresList {


    private static Connection connection;


    public PostGresList() {
        connection = ConnectionPoolManager.getConnectionFromPool();
    }


    public void getList() {
        TreeItem<String> databases = new TreeItem<>("Databases");
        NewTabObjects.getList().setRoot(databases);
        NewTabObjects.getList().setShowRoot(false);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT datname FROM pg_database WHERE datistemplate = false;");
            while (resultSet.next()) {
                String db = resultSet.getString(1);
                TreeItem<String> dbItem = new TreeItem<>(db, ImageController.addDatabasesIcon());
                databases.getChildren().add(dbItem);
                dbConnect(db);
                getSchemas(dbItem);
            }
            NewTabObjects.getTableMessage().setItems(CollectorPoolManager.getCollectorFromPool());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void dbConnect(String db) throws Exception{
            String url = "jdbc:postgresql://" + PostGresConnection.ip + ":" + PostGresConnection.port + "/" + db;
            connection = DriverManager.getConnection(url, PostGresConnection.user, PostGresConnection.pass);
    }


    private void getSchemas(TreeItem<String> dbItem) throws SQLException {
        TreeItem<String> schemas = new TreeItem<>("Schemas", ImageController.addBatchImage());
        Statement statement = connection.createStatement();
        ResultSet resultSchema = statement.executeQuery("select schema_name from information_schema.schemata where schema_name \n" +
                "not in ('information_schema', 'pg_catalog', 'pg_toast', 'pg_temp_1', 'pg_toast_temp_1');");
        while (resultSchema.next()) {
            String schemaName = resultSchema.getString(1);
            TreeItem<String> schemaItem = new TreeItem<>(schemaName, ImageController.addSchemaImage());
            schemas.getChildren().add(schemaItem);
           getTables(schemaItem,schemaName);
           getViews(schemaName,schemaItem);
        }
        dbItem.getChildren().add(schemas);
    }


    private void getTables(TreeItem<String> schemaItem, String schemaName) throws SQLException {
        TreeItem<String> tables = new TreeItem<>("Tables", ImageController.addTablesImage());
        Statement statement = connection.createStatement();
        ResultSet resultSchema = statement.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = '" + schemaName + "';");
        while (resultSchema.next()) {
            String table = resultSchema.getString(1);
            TreeItem<String> tableItem = new TreeItem<>(table, ImageController.addTableImage());
            tables.getChildren().add(tableItem);
        }
        schemaItem.getChildren().add(tables);
    }

    private void getViews(String schemaName, TreeItem<String> schemaItem) throws SQLException {
        TreeItem<String> views = new TreeItem<>("Views", ImageController.addViewIcon());
        Statement statement = connection.createStatement();
        ResultSet resultSchema = statement.executeQuery("SELECT table_name view_name FROM information_schema.tables \n" +
                "                WHERE table_type = 'VIEW' AND table_schema like '" + schemaName + "';");
        while (resultSchema.next()) {
            String view = resultSchema.getString(1);
            TreeItem<String> tableItem = new TreeItem<>(view, ImageController.addViewIcon());
            views.getChildren().add(tableItem);
        }
        schemaItem.getChildren().add(views);
    }




}
