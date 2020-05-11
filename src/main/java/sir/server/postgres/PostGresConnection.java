package sir.server.postgres;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import sir.server.connection.ConnectionPool;
import sir.server.connection.Credentials;
import sir.client.CredentialsController;
import sir.client.MainController;
import sir.server.connection.Messages;

import java.sql.*;


public class PostGresConnection {


    protected static ResultSet resultSet;
    protected static PostGresActions postGresActions;


   public PostGresConnection () {
        postGresActions = new PostGresActions();
    }



    public void connect() {
        final String url = "jdbc:postgresql://" + Credentials.getIp() + ":" + Credentials.getPort() + "/";
        try {
            Connection connection = DriverManager.getConnection(url, Credentials.getUser(), Credentials.getPass());
            ConnectionPool.getConnection(connection);
            ConnectionPool.connection = connection;
            postGresActions.colect("Connection Succesfull");
        } catch (SQLException e) {
            CredentialsController.getError("Access Denied");
            System.out.println(e.getMessage());
        }
    }

    public static void getDatabases(Label listTitle, ListView<String> listView, TableView<Messages> actions) {
        String serverName = MainController.tabPane.getSelectionModel().getSelectedItem().getText().toLowerCase();
        if (serverName.contains("postgres")) {
            listView.getItems().clear();
            try {
                Statement statement = ConnectionPool.connection.createStatement();
                final ObservableList<String> result = FXCollections.observableArrayList();
                ResultSet resultSet = statement.executeQuery("SELECT datname FROM pg_database WHERE datistemplate = false;");
                while (resultSet.next()) {
                    result.add(resultSet.getString(1));
                }
                listView.setItems(result);
                listTitle.setText("Databases");
                actions.setItems(PostGresActions.actions);
            } catch (SQLException e) {
                postGresActions.colect(e.getMessage());
            }
        }
    }


    protected static void getSchemas(ListView<String> listView) {
        try {
            Statement statement = ConnectionPool.connection.createStatement();
            resultSet = statement.executeQuery("select schema_name from information_schema.schemata");
            final ObservableList<String> result = FXCollections.observableArrayList();
            while (resultSet.next()) {
                result.add(resultSet.getString(1) + "\n");
            }
            listView.setItems(result);
        } catch (SQLException e) {
            postGresActions.colect(e.getMessage());
        }
    }


    protected void getTables(ListView<String> listView) {
        try {
            Statement statement = ConnectionPool.connection.createStatement();
            resultSet = statement.executeQuery("select tablename from pg_tables where schemaname='public';");
            final ObservableList<String> result = FXCollections.observableArrayList();
            while (resultSet.next()) {
                result.add(resultSet.getString(1) + "\n");
            }
            listView.setItems(result);
        } catch (SQLException e) {
            postGresActions.colect(e.getMessage());
        }

    }


    protected String getTableName() {
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            return resultSetMetaData.getTableName(1);
        } catch (SQLException e) {
            postGresActions.colect(e.getMessage());
        }
        return null;
    }

}
