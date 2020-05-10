package sir.ServerConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import org.postgresql.jdbc.PgResultSetMetaData;
import sir.clientSide.CredentialsController;
import sir.clientSide.MainController;
import sir.clientSide.Messages;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PostGresConnection {

    public static ObservableList<Messages> postgresActions = FXCollections.observableArrayList();
    private static ResultSet resultSet;
    private static String query;
    private static String url;
    private static String user;
    private static String pass;


    public static void connect(String userr, String passw) {
        user = userr;
        pass = passw;
        url = "jdbc:postgresql://" + Credentials.getIp() + ":" + Credentials.getPort() + "/";
        try {
            Connection connection = DriverManager.getConnection(url, Credentials.getUser(), Credentials.getPass());
            colect("Connection Succesfull");
            ConnectionPool.getConnection(connection);
            ConnectionPool.connection = connection;
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
                actions.setItems(postgresActions);
            } catch (SQLException e) {
                colect(e.getMessage());
            }
        }
    }


    public void getSchemas(ListView<String> listView) {
        listView.getItems().clear();
        try {
            Statement statement = ConnectionPool.connection.createStatement();
            resultSet = statement.executeQuery("select schema_name from information_schema.schemata");
            final ObservableList<String> result = FXCollections.observableArrayList();
            while (resultSet.next()) {
                result.add(resultSet.getString(1) + "\n");
            }
            listView.setItems(result);
        } catch (SQLException e) {
            colect(e.getMessage());
        }
    }


    public void getTables(ListView<String> listView) {
        listView.getItems().clear();
        try {
            Statement statement = ConnectionPool.connection.createStatement();
            resultSet = statement.executeQuery("select tablename from pg_tables where schemaname='public';");
            final ObservableList<String> result = FXCollections.observableArrayList();
            while (resultSet.next()) {
                result.add(resultSet.getString(1) + "\n");
            }
            listView.setItems(result);
        } catch (SQLException e) {
            colect(e.getMessage());
        }

    }

    public void getQuery(TabPane tabPane) {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        query = textArea.getText().toLowerCase();

    }

    public void executeQuery(Label listTitle, Label tableTitle, ListView<String> list, TableView<Map<Integer, String>> table,
                             TabPane tabPane, TableView<Messages> actions) {
        getQuery(tabPane);
        try {
            Statement statement = ConnectionPool.connection.createStatement();
            if (query.startsWith("use")) {
                if (listTitle.getText().equals("Databases")) {
                    String dbName = query.substring(4);
                    ConnectionPool.connection = DriverManager.getConnection(url + dbName, user, pass);
                    getSchemas(list);
                    listTitle.setText("Schemas");
                    colect("Use " + dbName);
                } else {
                    colect("Use statement is not allowed here");
                }
            } else if (query.startsWith("set search_path to")) {
                if (listTitle.getText().equalsIgnoreCase("Schemas")) {
                    String schemaName = query.substring(19);
                    getTables(list);
                    listTitle.setText(schemaName);
                    colect("Set schema to " + schemaName);
                } else {
                    colect("Not allowed here");
                }
            } else if (query.startsWith("select")) {
                resultSet = statement.executeQuery(query);
                getResult(table, tableTitle);
                colect("Select from " + getTableName());
            } else if (query.startsWith("create")) {
                if (query.contains("database")) {
                    if (listTitle.getText().equals("Databases")) {
                        statement.executeUpdate(query);
                        getDatabases(listTitle, list, actions);
                        colect("Create database " + query.substring(16));
                        list.getSelectionModel().select(Integer.parseInt(query.substring(16)));
                    } else {
                        colect("Create database is not allowed here");
                    }
                } else if (query.contains("schema")) {
                    if (listTitle.getText().equals("Schemas")) {
                        statement.executeQuery(query);
                        getSchemas(list);
                        colect("Create schema " + query.substring(14));
                        list.getSelectionModel().select(Integer.parseInt(query.substring(16)));
                    } else {
                        colect("No database selected");
                    }
                } else if (query.contains("table")) {
                    if (!listTitle.getText().equals("Databases") && !listTitle.getText().equals("Schemas")) {
                        statement.executeUpdate(query);
                        getTables(list);
                        colect("Create table " + (query.substring(13, query.indexOf("("))));
                    } else {
                        colect("No schema selected");
                    }
                }
            } else if (query.startsWith("alter table") && tableTitle.getText().equalsIgnoreCase(query.substring(11))) {
                statement.executeUpdate(query);
                colect("Alter table " + getTableName());
                resultSet = statement.executeQuery("select * from " + getTableName());
                getResult(table, tableTitle);

            } else if (query.startsWith("rename")) {
                if (query.contains("database")) {
                    if (listTitle.getText().equals("Databases")) {
                        statement.executeUpdate(query);
                        getDatabases(listTitle, list, actions);
                        colect("Database renamed succesfuly");
                    } else {
                        colect("Rename database is not allowed here ! Need to list Databases");
                    }
                } else if (query.contains("table")) {
                    if (!listTitle.getText().equals("Schemas")) {
                        statement.executeUpdate(query);
                        getTables(list);
                        colect("Table has been succesfuly rename");
                    } else if (tableTitle.getText().equals(query.substring(13))) {
                        tableTitle.setText(getTableName());
                    } else {
                        colect("No database selected");
                    }
                }
            } else if (query.startsWith("truncate")) {
                if (tableTitle.getText().equalsIgnoreCase(query.substring(16))) {
                    statement.executeUpdate(query);
                    colect("Truncate executate");
                    resultSet = statement.executeQuery("select * from " + query.substring(16));
                    getResult(table, tableTitle);
                } else if (listTitle.getText().equals("Schemas")) {
                    colect("No database selected");
                } else {
                    colect("Truncate is not allowed here");
                }
            } else if (query.startsWith("drop")) {
                if (query.contains("database")) {
                    if (listTitle.getText().equals("Databases")) {
                        statement.executeUpdate(query);
                        getDatabases(listTitle, list, actions);
                        colect("Drop database " + query.substring(14));
                    } else if (listTitle.getText().equals(query.substring(14))) {
                        getDatabases(listTitle, list, actions);
                        clearTable(table, tableTitle);
                    } else {
                        colect("No database selected");
                    }
                } else if (query.contains("table")) {
                    if (!listTitle.getText().equals("Schemas")) {
                        statement.executeUpdate(query);
                        getTables(list);
                        colect("Drop table " + query.substring(11));
                    } else if (tableTitle.getText().equals(query.substring(11))) {
                        clearTable(table, tableTitle);
                    } else {
                        colect("No database selected");
                    }
                }
            } else if (query.startsWith("insert") || query.startsWith("update") || query.startsWith("delete") ||
                    query.startsWith("merge") || query.startsWith("call")) {
                if (!listTitle.getText().equals("Schemas")) {
                    if (tableTitle.getText().equals(getTableName())) {
                        statement.executeUpdate(query);
                        colect("Update executate");
                        resultSet = statement.executeQuery("select * from " + getTableName());
                        getResult(table, tableTitle);
                    } else {
                        colect("No table selected");
                    }
                } else {
                    colect("No database selected");
                }
            } else if (query.startsWith("show")) {
                if (query.contains("databases")) {
                    if (!listTitle.getText().equals("Databases")) {
                        clearTable(table, tableTitle);
                        getDatabases(listTitle, list, actions);
                        colect("Database has been succesfuly loaded");
                    } else if (listTitle.getText().equals("Databases")) {
                        colect("Database are already listed");
                    }
                } else if (query.contains("Schemas")) {
                    if (!listTitle.getText().equals("Schemas")) {
                        clearTable(table, tableTitle);
                        getSchemas(list);
                        colect("Schemas has been succesfuly loaded");
                    } else if (listTitle.getText().equals("Schemas")) {
                        colect("Schemas are already listed");
                    }
                }
            } else if (!listTitle.getText().equals("Schemas") && tableTitle.getText() != null) {
                resultSet = statement.executeQuery(query);
                getResult(table, tableTitle);
            }
        } catch (
                SQLException e) {
            colect(e.getMessage());
        }

    }

    private void getResult(TableView<Map<Integer, String>> table, Label tableTitle) {
        clearTable(table, tableTitle);
        try {
            TableColumn<Map<Integer, String>, String> tableColumn;
            ObservableList<Map<Integer, String>> alldata = FXCollections.observableArrayList();
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String name = resultSet.getMetaData().getColumnName(i);
                tableColumn = new TableColumn<>(name);
                tableColumn.setCellValueFactory(new MapValueFactory(i));
                tableColumn.setPrefWidth(100);
                table.getColumns().addAll(tableColumn);
            }
            while (resultSet.next()) {
                Map<Integer, String> dataRow = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String value = resultSet.getString(i);
                    dataRow.put(i, value);
                }
                alldata.add(dataRow);
            }
            table.setItems(alldata);
            tableTitle.setText(getTableName());
        } catch (SQLException e) {
            colect(e.getMessage());
        }
    }


    private void clearTable(TableView<Map<Integer, String>> table, Label tableTitle) {
        table.getColumns().clear();
        table.getItems().clear();
        tableTitle.setText("");
    }

    private String getTableName() {
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            return resultSetMetaData.getTableName(1);
        } catch (SQLException e) {
            colect(e.getMessage());
        }
        return null;
    }

    public String getSchemaName() {
        try {
            PgResultSetMetaData pgrs = (PgResultSetMetaData) resultSet.getMetaData();
            return pgrs.getBaseSchemaName(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void getInfo(Label name, Label host, Label user) {
        String server = MainController.tabPane.getSelectionModel().getSelectedItem().getText();
        if (server.contains("postgres")) {
            try {
                DatabaseMetaData dbm = ConnectionPool.connection.getMetaData();
                name.setText(dbm.getDatabaseProductName() + ":" + Credentials.getName());
                user.setText(dbm.getUserName());
                host.setText(Credentials.getIp() + ":" + Credentials.getPort());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private static void colect(String text) {
        Messages messages = new Messages(text);
        postgresActions.add(messages);
    }


}
