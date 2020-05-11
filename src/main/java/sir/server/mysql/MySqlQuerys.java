package sir.server.mysql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import sir.server.connection.Messages;
import sir.server.connection.ConnectionPool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MySqlQuerys {

    protected  String query;
    protected static Statement statement = null;
    protected final MySqlConnection mysqlConnection;
    protected final MySqlActions mySqlActions;
    protected static ResultSet resultSet;


    public MySqlQuerys() {
        mysqlConnection = new MySqlConnection();
        mySqlActions = new MySqlActions();
        try {
            statement = ConnectionPool.connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
            select(listTitle, tableTitle, table);
        } else if (query.startsWith("create")) {
            create(listTitle, list, actions);
        } else if (query.startsWith("alter")) {
            alter(tableTitle, table, listTitle, list, actions);
        } else if (query.startsWith("drop")) {
            drop(listTitle, tableTitle, list, table, actions);
        } else if (query.startsWith("show")) {
            show(listTitle, tableTitle, list, table, actions);
        } else if (query.startsWith("truncate")) {
            truncate(tableTitle, table);
        } else if (query.startsWith("rename")) {
            rename(listTitle, tableTitle, list, actions);
        } else if (query.startsWith("update") ||query.startsWith("delete") ||query.startsWith("insert")) {
            update(tableTitle,table);
        }
    }

    private void use(Label listTitle, ListView<String> list) {
        if (listTitle.getText().equals("Schemas")) {
            try {
                statement.executeUpdate(query);
                mysqlConnection.getTables(list);
                String schema = query.substring(4);
                listTitle.setText(schema);
                mySqlActions.colect("Use " + schema);
            } catch (SQLException e) {
                mySqlActions.colect(e.getMessage());
            }
        } else {
            mySqlActions.colect("List databases first");
        }
    }

    private void select(Label listTitle, Label tableTitle, TableView<Map<Integer, String>> table) {
        if (!listTitle.getText().equals("Schemas")) {
            try {
                MySqlConnection.resultSet = statement.executeQuery(query);
                clearTable(table, tableTitle);
                getResult(table, tableTitle);
                mySqlActions.colect("Select from " + mysqlConnection.getTableName());
            } catch (SQLException e) {
                mySqlActions.colect(e.getMessage());
            }
        } else {
            mySqlActions.colect("Select database first");
        }
    }


    private void create(Label listTitle, ListView<String> list, TableView<Messages> actions) {
        if (query.contains("database")) {
            if (listTitle.getText().equals("Schemas")) {
                try {
                    statement.executeUpdate(query);
                    clearList(list,listTitle);
                    MySqlConnection.getSchemas(listTitle, list, actions);
                    mySqlActions.colect("Create database " + query.substring(16));
                    list.getSelectionModel().select(Integer.parseInt(query.substring(16)));
                } catch (SQLException e) {
                    mySqlActions.colect(e.getMessage());
                }
            } else {
                mySqlActions.colect("Create database is not allowed here");
            }
        } else if (query.contains("table")) {
            if (!listTitle.getText().equals("Schemas")) {
                try {
                    statement.executeUpdate(query);
                    mysqlConnection.getTables(list);
                    mySqlActions.colect("Create table " + (query.substring(13, query.indexOf("("))));
                    list.getSelectionModel().select(Integer.parseInt(query.substring(13)));
                } catch (SQLException e) {
                    mySqlActions.colect(e.getMessage());
                }
            } else {
                mySqlActions.colect("No database selected");
            }
        }
    }

    private void alter(Label tableTitle, TableView<Map<Integer, String>> table, Label listTitle, ListView<String> list, TableView<Messages> actions) {
        if (query.contains("table")) {
            if (tableTitle.getText().equals(mysqlConnection.getTableName())) {
                try {
                    statement.executeUpdate(query);
                    mySqlActions.colect("Alter table " + mysqlConnection.getTableName());
                    MySqlConnection.resultSet = statement.executeQuery("select * from " + mysqlConnection.getTableName());
                    getResult(table, tableTitle);
                } catch (SQLException e) {
                    mySqlActions.colect(e.getMessage());
                }
            } else {
                mySqlActions.colect("Select table first");
            }
        } else if (query.contains("database")) {
            if (listTitle.getText().equals("Schemas")) {
                try {
                    statement.executeQuery(query);
                    MySqlConnection.getSchemas(listTitle, list, actions);
                    mySqlActions.colect("Alter database " + query.substring(16, query.indexOf("(")));
                } catch (SQLException e) {
                    mySqlActions.colect(e.getMessage());
                }
            } else {
                mySqlActions.colect("Not allowed here");
            }
        }
    }

    private void rename(Label listTitle, Label tableTitle, ListView<String> list, TableView<Messages> actions) {
        if (query.contains("database")) {
            if (listTitle.getText().equals("Schemas")) {
                try {
                    statement.executeUpdate(query);
                    MySqlConnection.getSchemas(listTitle, list, actions);
                    mySqlActions.colect("Database renamed succesfuly");
                } catch (SQLException e) {
                    mySqlActions.colect(e.getMessage());
                }
            } else {
                mySqlActions.colect("Rename database is not allowed here ! Need to list Databases");
            }
        } else if (query.contains("table")) {
            if (!listTitle.getText().equals("Schemas")) {
                try {
                    statement.executeUpdate(query);
                    mysqlConnection.getTables(list);
                    mySqlActions.colect("Table has been succesfuly rename");
                } catch (SQLException e) {
                    mySqlActions.colect(e.getMessage());
                }
            } else {
                mySqlActions.colect("No database selected");
            }
            if (!tableTitle.getText().equals(mysqlConnection.getTableName())) {
                tableTitle.setText(mysqlConnection.getTableName());
            }
        }
    }

    private void truncate(Label tableTitle, TableView<Map<Integer, String>> table) {
        if (tableTitle.getText().equals(mysqlConnection.getTableName())) {
            try {
                statement.executeUpdate(query);
                mySqlActions.colect("Truncate executate");
                MySqlConnection.resultSet = statement.executeQuery("select * from " + mysqlConnection.getTableName());
                getResult(table, tableTitle);
            } catch (SQLException e) {
                mySqlActions.colect(e.getMessage());
            }
        } else {
            mySqlActions.colect("Select table first");
        }
    }

    private void drop(Label listTitle, Label tableTitle, ListView<String> list, TableView<Map<Integer,
            String>> table, TableView<Messages> actions) {
        if (query.contains("database")) {
            if (listTitle.getText().equals("Schemas")) {
                try {
                    statement.executeUpdate(query);
                    clearList(list, listTitle);
                    MySqlConnection.getSchemas(listTitle, list, actions);
                    mySqlActions.colect("Drop database " + query.substring(14));
                } catch (SQLException e) {
                    mySqlActions.colect(e.getMessage());
                }
            } else if (listTitle.getText().equals(query.substring(14))) {
                try {
                    statement.executeUpdate(query);
                    clearList(list, listTitle);
                    clearTable(table, tableTitle);
                } catch (SQLException e) {
                    mySqlActions.colect(e.getMessage());
                }
            } else {
                mySqlActions.colect("Not allowed here");
            }
        } else if (query.contains("table")) {
            if (!listTitle.getText().equals("Schemas")) {
                try {
                    statement.executeUpdate(query);
                    list.getItems().clear();
                    mysqlConnection.getTables(list);
                    mySqlActions.colect("Drop table " + mysqlConnection.getTableName());
                } catch (SQLException e) {
                    mySqlActions.colect(e.getMessage());
                }
            } else {
                mySqlActions.colect("No database selected");
            }
            if (tableTitle.getText().equals(query.substring(11))) {
                clearTable(table, tableTitle);
            }
        }
    }



    private void update (Label tableTitle, TableView<Map<Integer,String>> tableView) {
        if (tableTitle.getText().equals(mysqlConnection.getTableName())) {
            try {
                statement.executeUpdate(query);
                clearTable(tableView,tableTitle);
                MySqlConnection.resultSet = statement.executeQuery("select * from " + mysqlConnection.getTableName());
                getResult(tableView,tableTitle);
                mySqlActions.colect("Update executate");
            }catch (SQLException e) {
                mySqlActions.colect(e.getMessage());
            }
        } else {
            mySqlActions.colect("Select table first");
        }
    }




    private void show(Label listTitle, Label
            tableTitle, ListView<String> list, TableView<Map<Integer, String>> table,
                     TableView<Messages> actions) {
            if (query.contains("databases")) {
                if (!listTitle.getText().equals("Schemas")) {
                    clearTable(table, tableTitle);
                    MySqlConnection.getSchemas(listTitle, list, actions);
                    mySqlActions.colect("Database has been succesfuly loaded");
                } else {
                    mySqlActions.colect("Database are already listed");
                }
            }
        }


    public void getResult(TableView<Map<Integer, String>> table, Label tableTitle) {
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
            tableTitle.setText(mysqlConnection.getTableName());
        } catch (SQLException e) {
            mySqlActions.colect(e.getMessage());
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