package sir.server.mysql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import sir.server.connection.Messages;

class MySqlActions {


    protected static final ObservableList<Messages> actions = FXCollections.observableArrayList();


    protected void colect(String text) {
        Messages mess = new Messages(text);
        actions.add(mess);
    }

}
