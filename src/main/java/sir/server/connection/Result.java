package sir.server.connection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;

import sir.client.MainController;
import sir.server.mysql.MySqlConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Result {

    public static ResultSet resultSet;
    public static MetaData metaData = new MetaData();
    public static Connection connection = ConnectionPool.connection;


    public void getTableResult(TableView<Map<Integer, String>> table, Label tableTitle) {
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
            e.getMessage();
        }
    }

    public void getListResult(ListView<String> listView) throws SQLException {
        ObservableList<String> result = FXCollections.observableArrayList();
        while (resultSet.next()) {
            result.add(resultSet.getString(1));
        }
        listView.setItems(result);
    }


    public void getList(TreeView<String> list) {
        Statement statement = null;
        try {
            connection.createStatement();
            while (resultSet.next()) {
                TreeItem<String> database = new TreeItem<>(resultSet.getString(1));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}