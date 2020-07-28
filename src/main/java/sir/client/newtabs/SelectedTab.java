package sir.client.newtabs;

import javafx.scene.control.*;
import sir.client.home.TabPaneService;

import java.util.Map;

public class SelectedTab {



    public static TreeView<String> getList() {
        Tab tab = TabPaneService.getSelectedTab();
        return (TreeView<String>) tab.getContent().lookup("#list");
    }

    public static Label getTableTitle() {
        Tab tab = TabPaneService.getSelectedTab();
        return (Label) tab.getContent().lookup("#tableTitle");
    }

    public static TableView<Map<Integer, String>> getTable()   {
        Tab tab = TabPaneService.getSelectedTab();
      return (TableView<Map<Integer, String>>)  tab.getContent().lookup("#table");
    }

    public static TabPane getQueryTabPane () {
        Tab tab = TabPaneService.getSelectedTab();
        return (TabPane) tab.getContent().lookup("#queryTabPane");
    }
}
