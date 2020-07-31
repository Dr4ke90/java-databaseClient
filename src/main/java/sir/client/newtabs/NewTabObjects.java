package sir.client.newtabs;

import javafx.scene.control.*;
import sir.server.connection.Messages;

import java.util.Map;

public class NewTabObjects {

    private static Label dateLabel;
    private static TreeView<String> list;
    private static TableView<Map<Integer, String>> table;
    private static Label tableTitle;
    private static Label name;
    private static Label host;
    private static Label user;
    private static TabPane queryTabPane;
    private static TableView<Messages> tableMessage;
    private static Button send;
    private static Button open;
    private static Button save;
    private static Button newTab;
    private static Button refresh;
    private static TextField search;


    public void setObjects(Label dateLabel, TreeView<String> list, TableView<Map<Integer, String>> table,
                           Label tableTitle, Label name, Label host, Label user, TabPane queryTabPane,
                           TableView<Messages> tableMessage, Button send, Button open, Button save,
                           Button newTab, Button refresh, TextField search) {

        NewTabObjects.dateLabel = dateLabel;
        NewTabObjects.list = list;
        NewTabObjects.table = table;
        NewTabObjects.tableTitle = tableTitle;
        NewTabObjects.name = name;
        NewTabObjects.host = host;
        NewTabObjects.user = user;
        NewTabObjects.queryTabPane = queryTabPane;
        NewTabObjects.tableMessage = tableMessage;
        NewTabObjects.send = send;
        NewTabObjects.open = open;
        NewTabObjects.save = save;
        NewTabObjects.newTab = newTab;
        NewTabObjects.refresh = refresh;
        NewTabObjects.search = search;
    }


    public static Label getDateLabel() {
        return dateLabel;
    }

    public static TreeView<String> getList() {
        return list;
    }

    public static TableView<Map<Integer, String>> getTable() {
        return table;
    }

    public static Label getTableTitle() {
        return tableTitle;
    }

    public static Label getName() {
        return name;
    }

    public static Label getHost() {
        return host;
    }

    public static Label getUser() {
        return user;
    }

    public static TabPane getQueryTabPane() {
        return queryTabPane;
    }

    public static TableView<Messages> getTableMessage() {
        return tableMessage;
    }

    public static Button getSend() {
        return send;
    }

    public static Button getOpen() {
        return open;
    }

    public static Button getSave() {
        return save;
    }

    public static Button getNewTab() {
        return newTab;
    }

    public static Button getRefresh() {
        return refresh;
    }

    public static TextField getSearch() {
        return search;
    }
}
