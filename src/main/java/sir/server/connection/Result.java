package sir.server.connection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import sir.client.newtabs.NewTabObjects;
import sir.client.newtabs.SelectedTab;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Result {

    public static ResultSet resultSet;
    private static MetaData metaData;

    public Result() {
        metaData = new MetaData();
    }


    public void setResultOnTable() {
        Querys.clearTable();
        try {
            TableColumn<Map<Integer, String>, String> tableColumn;
            ObservableList<Map<Integer, String>> alldata = FXCollections.observableArrayList();
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String name = resultSet.getMetaData().getColumnName(i);
                tableColumn = new TableColumn<>(name);
                tableColumn.setCellValueFactory(new MapValueFactory(i));
                tableColumn.setPrefWidth(150);
                SelectedTab.getTable().getColumns().addAll(tableColumn);
            }
            while (resultSet.next()) {
                Map<Integer, String> dataRow = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String value = resultSet.getString(i);
                    dataRow.put(i, value);
                }
                alldata.add(dataRow);
            }
            SelectedTab.getTable().setItems(alldata);
            SelectedTab.getTableTitle().setText(metaData.getTableName());
        } catch (SQLException e) {
            e.getMessage();
        }
    }

}