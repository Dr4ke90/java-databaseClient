package sir.client.home;

import javafx.application.Platform;
import javafx.scene.control.*;
import sir.client.connSetup.ConnectionSetup;
import sir.client.newtabs.SelectedTab;
import sir.server.connection.ConnectionPool;
import sir.server.connection.ConnectionPoolManager;

import java.sql.SQLException;

public class MenuBarService {



    public void newConnection() throws Exception {
        ConnectionSetup connectionSetup = new ConnectionSetup();
        connectionSetup.loadChooseScene();
    }



    public void closeConnection() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText("Close Connection?");
        confirm.setTitle("Confirmation!");
        ButtonType buttonType = confirm.showAndWait().get();
        if (buttonType == ButtonType.OK) {
            try {
                ConnectionPoolManager.getConnectionFromPool().close();
                ConnectionPool.getConnectionPool().remove(TabPaneService.getSelectedTab().getId());
                ConnectionPool.getConnectionPool().remove(TabPaneService.getSelectedTab().getId());
                int selectedTab = HomeObjects.getTabPane().getSelectionModel().getSelectedIndex();
                HomeObjects.getTabPane().getTabs().remove(selectedTab);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void undo() {
        TabPane queryTabPane = SelectedTab.getQueryTabPane();
        Tab tab = queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.undo();
    }

    public void redo() {
        TabPane queryTabPane = SelectedTab.getQueryTabPane();
        Tab tab = queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.redo();
    }

    public void cut() {
        TabPane queryTabPane = SelectedTab.getQueryTabPane();
        Tab tab = queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.cut();
    }

    public void copy() {
        TabPane queryTabPane = SelectedTab.getQueryTabPane();
        Tab tab = queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.copy();
    }

    public void paste() {
        TabPane queryTabPane = SelectedTab.getQueryTabPane();
        Tab tab = queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.paste();
    }

    public void delete() {
        TabPane queryTabPane = SelectedTab.getQueryTabPane();
        Tab tab = queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.replaceSelection("");
    }

    public void selectAll() {
        TabPane queryTabPane = SelectedTab.getQueryTabPane();
        Tab tab = queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.selectAll();
    }

    public void deselect() {
        TabPane queryTabPane = SelectedTab.getQueryTabPane();
        Tab tab = queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.deselect();
    }

    public void quit() {
        Platform.exit();
    }
}
