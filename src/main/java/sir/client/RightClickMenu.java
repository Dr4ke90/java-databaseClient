package sir.client;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import sir.server.connection.Messages;


public class RightClickMenu {

    private static TreeView<String> list;
    private static TableView<Messages> tableMessages;


    public static ContextMenu handleDatabaseMenu () {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction(event -> {
           AplicationService aplicationService = new AplicationService();
           aplicationService.setSchemas(list,tableMessages);
        });
        MenuItem createDatabase = new MenuItem("Create database");
        MenuItem createTable = new MenuItem("Create table");
        MenuItem createSchema = new MenuItem("Create schema");
        contextMenu.getItems().addAll(createDatabase,createTable,createSchema, refresh);

        return contextMenu;
    }




    public void getList (TreeView<String> list, TableView<Messages> tableMessages) {
        RightClickMenu.list = list;
        RightClickMenu.tableMessages = tableMessages;

    }

}
