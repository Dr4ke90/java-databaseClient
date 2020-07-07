package sir.server.connection;

import javafx.scene.control.*;
import sir.server.postgres.PostGresList;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class Querys {

    private static String query;
    private static Statement statement = null;
    private final ActionsCollector actionsCollector;
    public static Result result;
    private static MetaData metaData;
    private static PostGresList postGresList;


    public Querys() {
        actionsCollector = new ActionsCollector();
        result = new Result();
        metaData = new MetaData();
        postGresList = new PostGresList();
        try {
            statement = ConnectionPool.connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getQuery() {
        return query;
    }

    private void getQueryFromSelectetTab(TabPane tabPane){
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        query = textArea.getText().toLowerCase();
    }

    public void executeQuery(Label tableTitle, TableView<Map<Integer, String>> table, TabPane tabPane, TabPane mainTabPane) {
        getQueryFromSelectetTab(tabPane);
        if (query.startsWith("use")) {
            use(mainTabPane);
        } else if (query.startsWith("select")) {
            select(tableTitle, table);
        } else if (query.startsWith("create")) {
            create();
        } else if (query.startsWith("drop")) {
            drop();
        } else if (query.startsWith("alter")) {
            alter();
        } else if (query.startsWith("rename")) {
            rename(tableTitle);
        } else if (query.startsWith("truncate")) {
            truncate();
        } else if (query.startsWith("update") || query.startsWith("delete") || query.startsWith("insert")) {
            update(tableTitle, table);
        } else if (query.startsWith("show")) {
            show(tableTitle, table);
        } else if (query.startsWith("set")) {
            set();
        } else if (query.startsWith("clear")) {
            clearTable(table, tableTitle);
        }
    }


    public void use(TabPane tabPane) {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        if (tab.getText().toLowerCase().contains("postgres")) {
            String dbName = query.substring(4);
            postGresList.dbConnect(dbName);
            actionsCollector.add("Connected to " + dbName);
        } else if (tab.getText().toLowerCase().contains("mysql")) {
            try {
                statement.executeUpdate(query);
                actionsCollector.add("Use " + query.substring(4));
            } catch (SQLException e) {
                actionsCollector.add(e.getMessage());
            }
        }
        String tabText = tab.getText().substring(0, tab.getText().indexOf("/") + 1);
        tab.setText(tabText + query.substring(4));
    }


    private void set() {
        String schemaName = query.substring(11);
        try {
            statement.executeUpdate("set search_path to " + schemaName);
            actionsCollector.add("Set schema to " + schemaName);
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }
    }

    public void select(Label tableTitle, TableView<Map<Integer, String>> table) {
        try {
            Result.resultSet = statement.executeQuery(query);
            clearTable(table, tableTitle);
            result.getTableResult(table, tableTitle);
            actionsCollector.add("Select " + query.substring(7));
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }
    }

    public void create() {
        try {
            statement.executeUpdate(query);
            if (query.contains("database")) {
                actionsCollector.add("Create database " + query.substring(16));
            } else if (query.contains("table")) {
                actionsCollector.add("Create " + query.substring(7, query.indexOf("(")));
            }

        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }
    }


    public void drop() {
        try {
            statement.executeUpdate(query);
                actionsCollector.add("Drop " + query.substring(5));
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }
    }

    public void alter() {
        try {
            statement.executeUpdate(query);
            actionsCollector.add("Alter " + query.substring(6));
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }
    }

    public void rename(Label tableTitle) {
        try {
            statement.executeUpdate(query);
            actionsCollector.add("Rename " + query.substring(7));
            if (!tableTitle.getText().equals(metaData.getTableName())) {
                tableTitle.setText(metaData.getTableName());
            }
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }
    }

    public void truncate() {
        try {
            statement.executeUpdate(query);
            actionsCollector.add("Truncate " + query.substring(9));
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }
    }

    public void update(Label tableTitle, TableView<Map<Integer, String>> table) {
        try {
            statement.executeUpdate(query);
            if (tableTitle.getText().equals(metaData.getTableName()))
                Result.resultSet = statement.executeQuery("select * from " + metaData.getTableName());
            result.getTableResult(table, tableTitle);
            actionsCollector.add("Update executate");
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }
    }

    public void show(Label tableTitle, TableView<Map<Integer, String>> table) {
        try {
            Result.resultSet = statement.executeQuery(query);
            result.getTableResult(table, tableTitle);
            actionsCollector.add("Show " + query.substring(5));
        } catch (SQLException e) {
            actionsCollector.add(e.getMessage());
        }
    }


    private void clearTable(TableView<Map<Integer, String>> table, Label tableTitle) {
        table.getColumns().clear();
        table.getItems().clear();
        tableTitle.setText("");
    }
}
