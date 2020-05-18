package sir.server.postgres;

import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import sir.server.connection.ActionsCollector;
import sir.server.connection.ConnectionPool;
import sir.server.connection.ImageController;
import sir.server.connection.Messages;
import java.sql.*;

public class PostGresList {


    public static ResultSet resultSet;
    public static Connection connection;
    public static ActionsCollector actionsCollector;
    public static PostGresConnection postGresConnection;
    private static ImageController imageController;


    public PostGresList() {
        connection = ConnectionPool.connection;
        actionsCollector = new ActionsCollector();
        postGresConnection = new PostGresConnection();
        imageController = new ImageController();
    }


    public void getList(TreeView<String> list, TableView<Messages> actions) {
        TreeItem<String> databases = new TreeItem<>("Databases");
        list.setRoot(databases);
        list.setShowRoot(false);
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT datname FROM pg_database WHERE datistemplate = false;");
            while (resultSet.next()) {
                String db = resultSet.getString(1);
                TreeItem<String> dbItem = new TreeItem<>(db, imageController.addDatabasesIcon());
                databases.getChildren().add(dbItem);
                dbConnect(db);
                getSchemas(dbItem);
            }
            actions.setItems(ActionsCollector.collector);
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }
    }


    public void dbConnect(String db) {
        try {
            String url = "jdbc:postgresql://" + PostGresConnection.ip + ":" + PostGresConnection.port + "/" + db;
            connection = DriverManager.getConnection(url, PostGresConnection.user, PostGresConnection.pass);
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }
    }


    public void getSchemas(TreeItem<String> dbItem) throws SQLException {
        TreeItem<String> schemas = new TreeItem<>("Schemas",imageController.addBatchImage());
        Statement statement = connection.createStatement();
        ResultSet resultSchema = statement.executeQuery("select schema_name from information_schema.schemata where schema_name \n" +
                "not in ('information_schema', 'pg_catalog', 'pg_toast', 'pg_temp_1', 'pg_toast_temp_1');");
        while (resultSchema.next()) {
            String schemaName = resultSchema.getString(1);
            TreeItem<String> schemaItem = new TreeItem<>(schemaName,imageController.addSchemaImage());
            schemas.getChildren().add(schemaItem);
           getTables(schemaItem,schemaName);
           getViews(schemaName,schemaItem);
        }
        dbItem.getChildren().add(schemas);
    }


    public void getTables(TreeItem<String> schemaItem, String schemaName) throws SQLException {
        TreeItem<String> tables = new TreeItem<>("Tables", imageController.addTablesImage());
        Statement statement = connection.createStatement();
        ResultSet resultSchema = statement.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = '" + schemaName + "';");
        while (resultSchema.next()) {
            String table = resultSchema.getString(1);
            TreeItem<String> tableItem = new TreeItem<>(table,imageController.addTableImage());
            tables.getChildren().add(tableItem);
        }
        schemaItem.getChildren().add(tables);
    }

    public void getViews(String schemaName, TreeItem<String> schemaItem) throws SQLException {
        TreeItem<String> views = new TreeItem<>("Views",imageController.addViewIcon());
        Statement statement = connection.createStatement();
        ResultSet resultSchema = statement.executeQuery("SELECT table_name view_name FROM information_schema.tables \n" +
                "                WHERE table_type = 'VIEW' AND table_schema like '" + schemaName + "';");
        while (resultSchema.next()) {
            String view = resultSchema.getString(1);
            TreeItem<String> tableItem = new TreeItem<>(view,imageController.addViewIcon());
            views.getChildren().add(tableItem);
        }
        schemaItem.getChildren().add(views);
    }




}
