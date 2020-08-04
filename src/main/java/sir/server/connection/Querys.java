package sir.server.connection;

import javafx.scene.control.*;
import sir.client.home.TabPaneService;
import sir.client.newtabs.NewTabObjects;
import sir.client.newtabs.SelectedTab;
import sir.server.postgres.PostGresList;

import java.sql.SQLException;
import java.sql.Statement;

public class Querys {

    private static String query;
    private static Statement statement = null;
    private final CollectorPoolManager collectorPoolManager;
    private static Result result;
    private static MetaData metaData;
    private static PostGresList postGresList;


    public Querys() {
        collectorPoolManager = new CollectorPoolManager();
        result = new Result();
        metaData = new MetaData();
        postGresList = new PostGresList();
        try {
            statement = ConnectionPoolManager.getConnectionFromPool().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void getQueryFromSelectetTab() {
        Tab tab = TabPaneService.getSelectedTab();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        query = textArea.getText().toLowerCase();
    }


    public void executeQuery() {
        getQueryFromSelectetTab();
        if (query.startsWith("use")) {
            use();
        } else if (query.startsWith("select")) {
            select();
        } else if (query.startsWith("create")) {
            create();
        } else if (query.startsWith("drop")) {
            drop();
        } else if (query.startsWith("alter")) {
            alter();
        } else if (query.startsWith("rename")) {
            rename();
        } else if (query.startsWith("truncate")) {
            truncate();
        } else if (query.startsWith("update") || query.startsWith("delete") || query.startsWith("insert")) {
            update();
        } else if (query.startsWith("show")) {
            show();
        } else if (query.startsWith("set")) {
            set();
        } else if (query.startsWith("clear")) {
            clearTable();
        }
    }


    public void use() {
        Tab tab = TabPaneService.getSelectedTab();
        if (tab.getText().toLowerCase().contains("postgres")) {
            try {
                String dbName = query.substring(4);
                postGresList.dbConnect(dbName);
                collectorPoolManager.addAction("Connected to " + dbName);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (tab.getText().toLowerCase().contains("mysql")) {
            try {
                statement.executeUpdate(query);
                collectorPoolManager.addAction("Use " + query.substring(4));
            } catch (SQLException e) {
                collectorPoolManager.addAction(e.getMessage());
            }
        }
        String tabText = tab.getText().substring(0, tab.getText().indexOf("/") + 1);
        tab.setText(tabText + query.substring(4));
    }


    private void set() {
        String schemaName = query.substring(11);
        try {
            statement.executeUpdate("set search_path to " + schemaName);
            collectorPoolManager.addAction("Set schema to " + schemaName);
        } catch (SQLException e) {
            collectorPoolManager.addAction(e.getMessage());
        }
    }

    public void select() {
        try {
            Result.resultSet = statement.executeQuery(query);
            result.setResultOnTable();
            collectorPoolManager.addAction("Select " + query.substring(7));
        } catch (SQLException e) {
            collectorPoolManager.addAction(e.getMessage());
        }
    }

    public void create() {
        try {
            statement.executeUpdate(query);
            if (query.contains("database")) {
                collectorPoolManager.addAction("Create database " + query.substring(16));
            } else if (query.contains("table")) {
                collectorPoolManager.addAction("Create " + query.substring(7, query.indexOf("(")));
            }

        } catch (SQLException e) {
            collectorPoolManager.addAction(e.getMessage());
        }
    }


    public void drop() {
        try {
            statement.executeUpdate(query);
            collectorPoolManager.addAction("Drop " + query.substring(5));
        } catch (SQLException e) {
            collectorPoolManager.addAction(e.getMessage());
        }
    }

    public void alter() {
        try {
            statement.executeUpdate(query);
            collectorPoolManager.addAction("Alter " + query.substring(6));
        } catch (SQLException e) {
            collectorPoolManager.addAction(e.getMessage());
        }
    }

    public void rename() {
        try {
            statement.executeUpdate(query);
            collectorPoolManager.addAction("Rename " + query.substring(7));
            if (!NewTabObjects.getTableTitle().getText().equals(metaData.getTableName())) {
                NewTabObjects.getTableTitle().setText(metaData.getTableName());
            }
        } catch (SQLException e) {
            collectorPoolManager.addAction(e.getMessage());
        }
    }

    public void truncate() {
        try {
            statement.executeUpdate(query);
            collectorPoolManager.addAction("Truncate " + query.substring(9));
        } catch (SQLException e) {
            collectorPoolManager.addAction(e.getMessage());
        }
    }

    public void update() {
        try {
            statement.executeUpdate(query);
            if (SelectedTab.getTableTitle().getText().equals(metaData.getTableName())) {
                Result.resultSet = statement.executeQuery("select * from " + metaData.getTableName());
                result.setResultOnTable();
            }
            collectorPoolManager.addAction("Update executate");
        } catch (SQLException e) {
            collectorPoolManager.addAction(e.getMessage());
        }
    }

    public void show() {
        try {
            Result.resultSet = statement.executeQuery(query);
            result.setResultOnTable();
            collectorPoolManager.addAction("Show " + query.substring(5));
        } catch (SQLException e) {
            collectorPoolManager.addAction(e.getMessage());
        }
    }


    public static void clearTable() {
        SelectedTab.getTable().getColumns().clear();
        SelectedTab.getTable().getItems().clear();
        SelectedTab.getTableTitle().setText("");
    }
}
