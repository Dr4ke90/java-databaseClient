package sir.server.connection;

import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;


public class CollectorPool {

    private static final Map<String, ObservableList<Messages>> collectorPool = new HashMap<>();


    public static Map<String, ObservableList<Messages>> getCollectorPool() {
        return collectorPool;
    }
}
