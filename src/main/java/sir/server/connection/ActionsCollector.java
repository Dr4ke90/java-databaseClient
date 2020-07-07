package sir.server.connection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;

import java.util.HashMap;
import java.util.Map;

public class ActionsCollector {

    private static final Map<String, ObservableList<Messages>> collectorPool = new HashMap<>();
    public static ObservableList<Messages> collector;


    public static void createCollector(Tab tab) {
        ObservableList<Messages> collector = FXCollections.observableArrayList();
        ActionsCollector.collector = collector;
        collectorPool.put(tab.getId(), collector);

    }


    public static void switchCollector(Tab tab) {
        String tabId = tab.getId();
        if (collectorPool.containsKey(tabId)) {
            collector = collectorPool.get(tabId);
        }
    }

    public static void removeCollector(Tab tab) {
        String tabId = tab.getId();
        if (collectorPool.containsKey(tabId)) ;
        collectorPool.remove(tabId);
    }


    public void add(String text) {
        Messages mess = new Messages(text);
        collector.add(mess);
    }


}
