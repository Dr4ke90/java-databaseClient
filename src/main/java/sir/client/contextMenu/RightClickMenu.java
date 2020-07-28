package sir.client.contextMenu;

import javafx.scene.control.*;
import sir.client.newtabs.NewTabService;


public class RightClickMenu {



    public static ContextMenu handleDatabaseMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction(event -> {
            NewTabService newTabService = new NewTabService();
            newTabService.setSchemas();
        });
        SeparatorMenuItem separator = new SeparatorMenuItem();
        MenuItem createDatabase = new MenuItem("Create database");
        createDatabase.setOnAction(event -> {
        });
        MenuItem createTable = new MenuItem("Create table");
        MenuItem createSchema = new MenuItem("Create schema");
        contextMenu.getItems().addAll(refresh, separator, createDatabase, createTable, createSchema);

        return contextMenu;
    }




}
