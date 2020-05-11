package sir.server.mysql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import sir.server.connection.ConnectionPool;
import sir.server.connection.Credentials;
import sir.client.CredentialsController;
import sir.client.MainController;
import sir.server.connection.Messages;
import java.sql.*;




public class MySqlConnection {


    protected static ResultSet resultSet;
    protected static MySqlActions mysqlActions;

    public MySqlConnection() {
        mysqlActions = new MySqlActions();
    }

    public void connect() {
        final String url = "jdbc:mysql://" + Credentials.getIp() + ":" + Credentials.getPort() + "/";
        try {
            Connection connection = DriverManager.getConnection(url, Credentials.getUser(), Credentials.getPass());
            ConnectionPool.getConnection(connection);
            ConnectionPool.connection = connection;
            mysqlActions.colect("Connection Succesfull");
        } catch (SQLException e) {
            CredentialsController.getError("Access Denied");
            System.out.println(e.getMessage());
        }
    }


    public static void getSchemas(Label listTitle, ListView<String> listView, TableView<Messages> actions) {
       final String serverName = MainController.tabPane.getSelectionModel().getSelectedItem().getText().toLowerCase();
        if (serverName.contains("mysql")) {
            try {
                Statement statement = ConnectionPool.connection.createStatement();
                ResultSet resultSet = statement.executeQuery("show databases");
                ObservableList<String> result = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    result.add(resultSet.getString(1));
                }
                listView.setItems(result);
                listTitle.setText("Schemas");
                actions.setItems(mysqlActions.actions);
            } catch (SQLException e) {
                mysqlActions.colect(e.getMessage());

            }
        }
    }


    public void getTables(ListView<String> listView) {
        try {
            Statement statement = ConnectionPool.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("show tables");
            ObservableList<String> result = FXCollections.observableArrayList();
            while (resultSet.next()) {
                result.add(resultSet.getString(1) + "\n");
            }
            listView.setItems(result);
        } catch (SQLException e) {
            mysqlActions.colect(e.getMessage());
        }
    }





    public String getTableName() {
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            return resultSetMetaData.getTableName(1);
        } catch (SQLException e) {
            mysqlActions.colect(e.getMessage());
        }
        return null;
    }




}

