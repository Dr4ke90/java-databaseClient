package sir.server.postgres;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import sir.server.connection.ActionsCollector;
import sir.server.connection.ConnectionPool;
import sir.server.connection.Credentials;
import sir.client.CredentialsController;
import sir.client.MainController;
import sir.server.connection.Messages;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;


public class PostGresConnection {


    protected static ActionsCollector actionsCollector;


   public PostGresConnection () {
        actionsCollector = new ActionsCollector();
    }



    public void connect(String name) {
        final String url = "jdbc:postgresql://" + Credentials.getIp() + ":" + Credentials.getPort() + "/";
        try {
            Connection connection = DriverManager.getConnection(url, Credentials.getUser(), Credentials.getPass());
            ConnectionPool.add(connection);
            ConnectionPool.connection = connection;
            actionsCollector.createCollector();
            if (connection != null) {
                actionsCollector.add("Connection Succesfull");
                setAppPage(name);
            }
        } catch (SQLException e) {
            CredentialsController.getError("Access Denied");
            System.out.println(e.getMessage());
        }
    }

    private void setAppPage(String name) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/aplication.fxml"));
            Tab tab = MainController.tabPane.getSelectionModel().getSelectedItem();
            tab.setText(tab.getText() + name);
            tab.setContent(parent);
        }catch (IOException e) {
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
                actions.setItems(ActionsCollector.collector);
            } catch (SQLException e) {
                actionsCollector.add(e.getMessage());
            }
        }
    }


    protected void getSchemas(ListView<String> listView) {
        try {
            Statement statement = ConnectionPool.connection.createStatement();
            PostGresQuerys.resultSet = statement.executeQuery("select schema_name from information_schema.schemata");
            final ObservableList<String> result = FXCollections.observableArrayList();
            while (PostGresQuerys.resultSet.next()) {
                result.add(PostGresQuerys.resultSet.getString(1) + "\n");
            }
            listView.setItems(result);
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }
    }


    protected void getTables(ListView<String> listView) {
        try {
            Statement statement = ConnectionPool.connection.createStatement();
           PostGresQuerys.resultSet = statement.executeQuery("select tablename from pg_tables where schemaname='public';");
            final ObservableList<String> result = FXCollections.observableArrayList();
            while (PostGresQuerys.resultSet.next()) {
                result.add(PostGresQuerys.resultSet.getString(1) + "\n");
            }
            listView.setItems(result);
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }

    }



}
