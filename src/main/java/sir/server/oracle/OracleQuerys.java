package sir.server.oracle;

import javafx.scene.control.*;
import sir.client.MainController;
import sir.server.connection.ActionsCollector;
import sir.server.connection.ConnectionPool;
import sir.server.connection.Messages;
import sir.server.connection.Result;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class OracleQuerys {

    private static String query;
    private static Statement statement = null;
    private final OracleConnection oracleConnection;
    private final ActionsCollector actionsCollector;
    private static Result result;


    public OracleQuerys() {
        oracleConnection = new OracleConnection();
        actionsCollector = new ActionsCollector();
        result = new Result();
        try {
            statement = ConnectionPool.connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void getQuery(TabPane tabPane) {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        query = textArea.getText().toLowerCase();
    }

    public void executeQuery(Label listTitle, Label tableTitle, ListView<String> list, TableView<Map<Integer, String>> table,
                             TabPane tabPane, TableView<Messages> actions) {
        getQuery(tabPane);
        if (query.startsWith("use")) {
            use(listTitle, list, actions);
        } else if (query.startsWith("select")) {
            select(tableTitle, table);
        } else if (query.startsWith("create")) {
            create(listTitle, list, actions);
        } else if (query.startsWith("alter")) {
            alter(tableTitle, table);
        } else if (query.startsWith("drop")) {
            drop(listTitle, tableTitle, list, table, actions);
        } else if (query.startsWith("show")) {
            show(list, actions, listTitle);
        } else if (query.startsWith("truncate")) {
            truncate(tableTitle, table);
        } else if (query.startsWith("rename")) {
            rename(listTitle, tableTitle, list, actions);
        } else if (query.startsWith("update") || query.startsWith("delete") || query.startsWith("insert")) {
            update(tableTitle, table);
        }
    }

    private void use(Label listTitle, ListView<String> list, TableView<Messages> actions) {
        try {
            statement.executeUpdate(query);
            listTitle.setText(query.substring(4));
            actionsCollector.add("Use " + query.substring(4));
            show(list, actions, listTitle);
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }
    }

    private void select(Label tableTitle, TableView<Map<Integer, String>> table) {
        try {
            Result.resultSet = statement.executeQuery(query);
            clearTable(table, tableTitle);
            result.getTableResult(table, tableTitle);
            actionsCollector.add("select");
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }
    }


    private void create(Label listTitle, ListView<String> list, TableView<Messages> actions) {
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }
        if (listTitle.getText().equals("Schemas")) {
            showDatabases(list, actions);
            actionsCollector.add("Create " + query.substring(7));
        } else {
            showTables(list);
        }

    }


    private void alter(Label tableTitle, TableView<Map<Integer, String>> table) {
        if (query.contains("table")) {
            try {
                statement.executeUpdate(query);
                actionsCollector.add("Alter table " );
                Result.resultSet = statement.executeQuery("select * from " );
                result.getTableResult(table, tableTitle);
            } catch (SQLException e) {
                actionsCollector.add(e.getMessage());
            }
        } else {
            try {
                statement.executeUpdate(query);
                actionsCollector.add("Alter databse " + query.substring(7));
            } catch (SQLException e) {
                actionsCollector.add(e.getMessage());
            }
        }
    }


    private void rename(Label listTitle, Label tableTitle, ListView<String> list, TableView<Messages> actions) {
        if (query.contains("table")) {
            if (!listTitle.getText().equals("Schemas")) {
                try {
                    statement.executeUpdate(query);
                    list.getItems().clear();
                    show(list, actions, listTitle);
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
        }
    }

    private void truncate(Label tableTitle, TableView<Map<Integer, String>> table) {
        try {
            statement.executeUpdate(query);
            actionsCollector.add("Truncate " + query.substring(10));
            Result.resultSet = statement.executeQuery("select * from ");
            result.getTableResult(table, tableTitle);
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
            actionsCollector.add("Truncate " + query.substring(10));
        }
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }
    }


    private void drop(Label listTitle, Label tableTitle, ListView<String> list, TableView<Map<Integer,
            String>> table, TableView<Messages> actions) {
        if (query.contains("database")) {
            if (listTitle.getText().equals("Schemas")) {
                try {
                    statement.executeUpdate(query);
                    list.getItems().clear();
                    show(list, actions, listTitle);
                    actionsCollector.add("Drop " + query.substring(5));
                } catch (SQLException e) {
                    actionsCollector.add(e.getMessage());
                }
            } else if (listTitle.getText().equals(query.substring(14))) {
                try {
                    statement.executeUpdate(query);
                    list.getItems().clear();
                    show(list, actions, listTitle);
                    clearTable(table, tableTitle);
                    actionsCollector.add("Drop " + query.substring(5));
                } catch (SQLException e) {
                    actionsCollector.add(e.getMessage());
                }
            } else {
                try {
                    statement.executeUpdate(query);
                    actionsCollector.add("Drop " + query.substring(5));
                } catch (SQLException e) {
                    actionsCollector.add(e.getMessage());
                }
            }
        } else if (query.contains("table")) {
            if (!listTitle.getText().equals("Schemas")) {
                try {
                    statement.executeUpdate(query);
                    list.getItems().clear();
                    show(list, actions, listTitle);
                    actionsCollector.add("Drop table " );
                } catch (SQLException e) {
                    actionsCollector.add(e.getMessage());
                }
            } else {
                try {
                    statement.executeUpdate(query);
                    actionsCollector.add("Drop table ");
                } catch (SQLException e) {
                    actionsCollector.add(e.getMessage());
                }
            }
            if (tableTitle.getText().equals(query.substring(11))) {
                clearTable(table, tableTitle);
            }
        }
    }


    private void update(Label tableTitle, TableView<Map<Integer, String>> tableView) {
            try {
                statement.executeUpdate(query);
                clearTable(tableView, tableTitle);
                Result.resultSet = statement.executeQuery("select * from ");
                result.getTableResult(tableView, tableTitle);
                actionsCollector.add("Update executate");
            } catch (SQLException e) {
                actionsCollector.add(e.getMessage());
            }
            try {
                statement.executeUpdate(query);
                actionsCollector.add("Update executate");
            } catch (SQLException e) {
                actionsCollector.add(e.getMessage());
            }
    }

    public void show(ListView<String> list, TableView<Messages> actions, Label listTitle) {
        final String serverName = MainController.tabPane.getSelectionModel().getSelectedItem().getText().toLowerCase();
        assert false;
        if (serverName.contains("oracle")) {
            if (query != null) {
                if (query.contains("databases")) {
                    showDatabases(list, actions);
                } else if (query.contains("tables")) {
                    showTables(list);
                } else {
                    showTables(list);
                }
            } else if (query == null) {
                showDatabases(list, actions);
                listTitle.setText("Schemas");
            } else {
                showDatabases(list, actions);
            }
        }

    }


    private void showTables(ListView<String> list) {
        try {
            list.getItems().clear();
            Result.resultSet = statement.executeQuery("show tables");
            result.getListResult(list);
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());

        }
    }


    public void showDatabases(ListView<String> list, TableView<Messages> actions) {
        final String serverName = MainController.tabPane.getSelectionModel().getSelectedItem().getText().toLowerCase();
        assert false;
        if (serverName.contains("oracle")) {
            try {
                list.getItems().clear();
                Result.resultSet = statement.executeQuery("SELECT username FROM dba_users");
                result.getListResult(list);
                actions.setItems(ActionsCollector.collector);
            } catch (SQLException e) {
                actionsCollector.add(e.getMessage());
            }
        }
    }


    private void clearTable(TableView<Map<Integer, String>> table, Label tableTitle) {
        table.getColumns().clear();
        table.getItems().clear();
        tableTitle.setText("");
    }


}