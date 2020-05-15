package sir.server.connection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import sir.client.MainController;

import java.util.HashMap;
import java.util.Map;

public class ActionsCollector {

    private static final Map<String, ObservableList<Messages>> collectorPool = new HashMap<>();
    public static ObservableList<Messages> collector;



    public void createCollector() {
        Tab tab = MainController.tabPane.getSelectionModel().getSelectedItem();
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



}
