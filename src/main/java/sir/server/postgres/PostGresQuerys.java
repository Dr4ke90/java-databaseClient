package sir.server.postgres;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import sir.server.connection.ActionsCollector;
import sir.server.connection.Messages;
import sir.server.connection.ConnectionPool;
import sir.server.connection.MetaData;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class PostGresQuerys {

    protected String query;
    protected static Statement statement = null;
    protected static ResultSet resultSet;
    protected final PostGresConnection postGresConnection;
    protected static ActionsCollector actionsCollector;
    private  static MetaData metaData;
    protected static String user;
    protected static String pass;
    protected static String ip;
    protected static String port;


    public PostGresQuerys() {
        postGresConnection = new PostGresConnection();
        actionsCollector = new ActionsCollector();
        metaData = new MetaData();
        try {
            statement = ConnectionPool.connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void crdMemory(String user, String pass, String ip, String port) {
        PostGresQuerys.pass = pass;
        PostGresQuerys.user = user;
        PostGresQuerys.ip = ip;
        PostGresQuerys.port = port;
    }

    protected void getQuery(TabPane tabPane) {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        query = textArea.getText().toLowerCase();
    }

    public void executeQuery(Label listTitle, Label tableTitle, ListView<String> list, TableView<Map<Integer, String>> table,
                             TabPane tabPane, TableView<Messages> actions) {
        getQuery(tabPane);
        if (query.startsWith("use")) {
            use(listTitle, list);
        } else if (query.startsWith("select")) {
            select(tableTitle, table);
        } else if (query.startsWith("create")) {
            create(listTitle, list, actions);
        } else if (query.startsWith("alter")) {
            alter(tableTitle, table);
        } else if (query.startsWith("drop")) {
            drop(listTitle, tableTitle, list, table, actions);
        } else if (query.startsWith("show")) {
            show(listTitle, list, actions);
        } else if (query.startsWith("truncate")) {
            truncate(tableTitle, table);
        } else if (query.startsWith("rename")) {
            rename(listTitle, tableTitle, list, actions);
        } else if (query.startsWith("update") || query.startsWith("delete") || query.startsWith("insert")) {
            update(tableTitle, table);
        } else if (query.startsWith("set")) {
            set(listTitle, list);
        }
    }

    private void use(Label listTitle, ListView<String> list) {
        String dbName = query.substring(4);
        String url = "jdbc:postgresql://" + ip + ":" + port + "/";
        try {
            ConnectionPool.connection = DriverManager.getConnection(url + dbName, user, pass);
            list.getItems().clear();
            postGresConnection.getSchemas(list);
            listTitle.setText("Schemas");
            actionsCollector.add("Use " + dbName);
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }
    }

    private void set(Label listTitle, ListView<String> listView) {
        String schemaName = query.substring(4);
        try {
            statement.executeUpdate("set search_path to " + schemaName);
            clearList(listView, listTitle);
            postGresConnection.getTables(listView);
            listTitle.setText(schemaName);
            actionsCollector.add("Set schema to " + schemaName);
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }
    }


    private void select(Label tableTitle, TableView<Map<Integer, String>> table) {
        try {
            resultSet = statement.executeQuery(query);
            clearTable(table, tableTitle);
            getResult(table, tableTitle);
            actionsCollector.add("Select from " + metaData.getTableName());
        } catch (SQLException e) {
           actionsCollector.add(e.getMessage());
        }
    }


    private void create(Label listTitle, ListView<String> list, TableView<Messages> actions) {
        if (query.contains("database")) {
            try {
                statement.executeUpdate(query);
                actionsCollector.add("Create database " + query.substring(16));
            } catch (SQLException e) {
               actionsCollector.add(e.getMessage());
            }
            if (listTitle.getText().equals("Databases")) {
                PostGresConnection.getDatabases(listTitle, list, actions);
            }
        } else if (query.contains("schema")) {
            try {
                statement.executeQuery(query);
                actionsCollector.add("Create schema " + query.substring(14));
            } catch (SQLException e) {
                actionsCollector.add(e.getMessage());
            }
            if (listTitle.getText().equals("Schemas")) {
                postGresConnection.getSchemas(list);
            }
        } else if (query.contains("table")) {
            if (!listTitle.getText().equals("Schemas") && !listTitle.getText().equals("Databases")) {
                try {
                    statement.executeUpdate(query);
                    postGresConnection.getTables(list);
                    actionsCollector.add("Create table " + (query.substring(13, query.indexOf("("))));
                } catch (SQLException e) {
                    actionsCollector.add(e.getMessage());
                }
            } else {
                actionsCollector.add("No schema selected");
            }
        }
    }

    private void alter(Label tableTitle, TableView<Map<Integer, String>> table) {
        if (query.contains("table")) {
            if (tableTitle.getText().equals(metaData.getTableName())) {
                try {
                    statement.executeUpdate(query);
                   actionsCollector.add("Alter table " + metaData.getTableName());
                    resultSet = statement.executeQuery("select * from " + metaData.getTableName());
                    getResult(table, tableTitle);
                } catch (SQLException e) {
                    actionsCollector.add(e.getMessage());
                }
            } else {
                actionsCollector.add("Select table first");
            }
        } else if (query.contains("database")) {
            try {
                statement.executeUpdate(query);
                actionsCollector.add("Alter database " + query.substring(16));
            } catch (SQLException e) {
                actionsCollector.add(e.getMessage());
            }
        } else if (query.contains("schema")) {
            try {
                statement.executeUpdate(query);
                actionsCollector.add("Alter shcema " + query.substring(16));
            } catch (SQLException e) {
                actionsCollector.add(e.getMessage());
            }
        }
    }


    private void rename(Label listTitle, Label tableTitle, ListView<String> list,
                        TableView<Messages> actions) {
        if (query.contains("database")) {
            if (listTitle.getText().equals("Databases")) {
                try {
                    statement.executeUpdate(query);
                    PostGresConnection.getDatabases(listTitle, list, actions);
                    actionsCollector.add("Rename " + query.substring(7));
                } catch (SQLException e) {
                    actionsCollector.add(e.getMessage());
                }
            } else {
                try {
                    statement.executeUpdate(query);
                    actionsCollector.add("Rename " + query.substring(7));
                } catch (SQLException e) {
                    actionsCollector.add(e.getMessage());
                }
            }
        } else if (query.contains("schemas")) {
            if (listTitle.getText().equals("Schemas")) {
                try {
                    statement.executeUpdate(query);
                    list.getItems().clear();
                    postGresConnection.getSchemas(list);
                   actionsCollector.add("Rename " + query.substring(7));
                } catch (SQLException e) {
                   actionsCollector.add(e.getMessage());
                }
            } else {
                try {
                    statement.executeUpdate(query);
                    actionsCollector.add("Rename " + query.substring(7));
                } catch (SQLException e) {
                    actionsCollector.add(e.getMessage());
                }
            }
        } else if (query.contains("table")) {
            if (!listTitle.getText().equals("Schemas") || !listTitle.getText().equals("Databases")) {
                try {
                    statement.executeUpdate(query);
                    list.getItems().clear();
                    postGresConnection.getTables(list);
                    actionsCollector.add("Rename " + query.substring(7));
                } catch (SQLException e) {
                    actionsCollector.add(e.getMessage());
                }
            } else {
                try {
                    statement.executeUpdate(query);
                    actionsCollector.add("Rename " + query.substring(7));
                } catch (SQLException e) {
                    actionsCollector.add(e.getMessage());
                }
            }
            if (!tableTitle.getText().equals(metaData.getTableName())) {
                tableTitle.setText(metaData.getTableName());
            }
        }
    }


    private void truncate(Label tableTitle, TableView<Map<Integer, String>> table) {
        if (tableTitle.getText().equals(metaData.getTableName())) {
            try {
                statement.executeUpdate(query);
                actionsCollector.add("Truncate executate");
                resultSet = statement.executeQuery("select * from " + metaData.getTableName());
                clearTable(table, tableTitle);
                getResult(table, tableTitle);
            } catch (SQLException e) {
                actionsCollector.add(e.getMessage());
            }
        } else {
            actionsCollector.add("Select table first");
        }
    }

    private void drop(Label listTitle, Label tableTitle, ListView<String> list, TableView<Map<Integer,
            String>> table, TableView<Messages> actions) {
        if (query.contains("database")) {
            if (listTitle.getText().equals("Databases")) {
                try {
                    statement.executeUpdate(query);
                    clearList(list, listTitle);
                    PostGresConnection.getDatabases(listTitle, list, actions);
                    actionsCollector.add("Drop database " + query.substring(14));
                } catch (SQLException e) {
                    actionsCollector.add(e.getMessage());
                }
            } else {
                try {
                    statement.executeUpdate(query);
                    actionsCollector.add("Drop database " + query.substring(14));
                } catch (SQLException e) {
                  actionsCollector.add(e.getMessage());
                }
            }
        } else if (query.contains("schemas")) {
            if (listTitle.getText().equals("Schemas")) {
                try {
                    statement.executeUpdate(query);
                    list.getItems().clear();
                    postGresConnection.getSchemas(list);
                    actionsCollector.add("Drop schema " + query.substring(12));
                } catch (SQLException e) {
                    actionsCollector.add(e.getMessage());
                }
            } else {
                try {
                    statement.executeUpdate(query);
                    actionsCollector.add("Drop schema " + query.substring(12));
                } catch (SQLException e) {
                    actionsCollector.add(e.getMessage());
                }
            }
        } else if (query.contains("table")) {
            if (!listTitle.getText().equals("Schemas") && !listTitle.getText().equals("Databases")) {
                try {
                    statement.executeUpdate(query);
                    list.getItems().clear();
                    postGresConnection.getTables(list);
                    actionsCollector.add("Drop table " + metaData.getTableName());
                } catch (SQLException e) {
                   actionsCollector.add(e.getMessage());
                }
            } else {
                try {
                    statement.executeUpdate(query);
                    actionsCollector.add("Drop table " + metaData.getTableName());
                } catch (SQLException e) {
                    actionsCollector.add(e.getMessage());
                }
            }
            if (tableTitle.getText().equals(metaData.getTableName())) {
                clearTable(table, tableTitle);
            }
        } else {
            actionsCollector.add("Syntax error");
        }
    }


    private void update(Label tableTitle, TableView<Map<Integer, String>> table) {
        if (tableTitle.getText().equals(metaData.getTableName())) {
            try {
                statement.executeUpdate(query);
                clearTable(table, tableTitle);
                resultSet = statement.executeQuery("select * from " + metaData.getTableName());
                clearTable(table, tableTitle);
                getResult(table, tableTitle);
                actionsCollector.add("Update executate");
            } catch (SQLException e) {
              actionsCollector.add(e.getMessage());
            }
        } else {
            actionsCollector.add("Select table first");
        }
    }


    private void show(Label listTitle, ListView<String> list, TableView<Messages> actions) {
        if (query.contains("databases")) {
            if (!listTitle.getText().equals("Databases")) {
                PostGresConnection.getDatabases(listTitle, list, actions);
                actionsCollector.add("Database has been succesfuly loaded");
            } else {
                actionsCollector.add("Database are already listed");
            }
        } else if (query.contains("schemas")) {
            if (!listTitle.getText().equals("Schemas")) {
                list.getItems().clear();
                postGresConnection.getSchemas(list);
                listTitle.setText("Schemas");
                actionsCollector.add("Schemas has been succesfuly loaded");
            } else {
                actionsCollector.add("Schemas are already listed");
            }
        } else if (query.contains("tables")) {
            if (listTitle.getText().equals("Databases") || listTitle.getText().equals("Schemas")) {
                list.getItems().clear();
                postGresConnection.getTables(list);
                listTitle.setText("Tables");
               actionsCollector.add("Tables has been succesfuly loaded");
            }
        } else {
            actionsCollector.add("Tables are already listed");
        }
    }

    private void getResult(TableView<Map<Integer, String>> table, Label tableTitle) {
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
            tableTitle.setText(metaData.getTableName());
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }
    }


    private void clearTable(TableView<Map<Integer, String>> table, Label tableTitle) {
        table.getColumns().clear();
        table.getItems().clear();
        tableTitle.setText("");
    }

    private void clearList(ListView<String> listView, Label listTitle) {
        listView.getItems().clear();
        listTitle.setText("");

    }
}
