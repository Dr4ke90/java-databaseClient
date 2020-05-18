package sir.server.connection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import sir.client.MainController;

import java.util.HashMap;
import java.util.Map;
import java.util.PrimitiveIterator;

public class ActionsCollector {

    private static final Map<String, ObservableList<Messages>> collectorPool = new HashMap<>();
    public static ObservableList<Messages> collector;
    private static TabPane mainTab;



    public void createCollector() {
        Tab tab = mainTab.getSelectionModel().getSelectedItem();
        ObservableList<Messages> collector = FXCollections.observableArrayList();
        collectorPool.put(tab.getId(), collector);
        ActionsCollector.collector = collector;
    }



    public static void switchConnection(Tab tab) {
        String tabId = tab.getId();
        if (collectorPool.containsKey(tabId)) {
            collector = collectorPool.get(tabId);
        }
    }

    public void add(String text) {
        Messages mess = new Messages(text);
        collector.add(mess);
    }


    public static void getTabPane (TabPane tabPane) {
        mainTab = tabPane;
    }


}
