package sir.server.postgres;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sir.server.connection.Messages;

class PostGresActions {

    protected static final ObservableList<Messages> actions = FXCollections.observableArrayList();


    protected void colect(String text) {
        Messages mess = new Messages(text);
        actions.add(mess);
    }
}
