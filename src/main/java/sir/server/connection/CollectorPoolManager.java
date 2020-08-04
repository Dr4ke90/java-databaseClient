package sir.server.connection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sir.client.home.TabPaneService;

public class CollectorPoolManager {





    public static ObservableList<Messages> createNewCollector () {
        return FXCollections.observableArrayList();
    }

    public void addAction(String text) {
        getCollectorFromPool().add(new Messages(text));
    }



    public static ObservableList<Messages> getCollectorFromPool () {
        String id = TabPaneService.getSelectedTab().getId();
        return CollectorPool.getCollectorPool().get(id);
    }


}
