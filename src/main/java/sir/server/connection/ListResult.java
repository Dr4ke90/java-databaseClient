package sir.server.connection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.sql.ResultSet;

public class ListResult {

    public static ResultSet resultSet;
    public static ObservableList<ObservableList<String>> databases = FXCollections.observableArrayList();
    public static ObservableList<String> tables = FXCollections.observableArrayList();



    public void listItems () throws Exception {
        while (resultSet.next()) {
            TreeItem<String> database = new TreeItem<>(resultSet.getString(1));

        }
    }
}
