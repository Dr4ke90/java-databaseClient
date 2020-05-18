package sir.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import sir.server.connection.ActionsCollector;
import sir.server.connection.ConnectionPool;
import sir.server.connection.Credentials;
import sir.server.connection.Querys;
import sir.server.mysql.MySqlConnection;
import sir.server.oracle.OracleConnection;
import sir.server.postgres.PostGresConnection;

import java.io.*;
import java.sql.SQLException;
import java.util.Random;


public class MainController {


    @FXML
    private MenuBar menuBar;
    @FXML
    private TabPane mainTab;
    public static TabPane queryTab;


    public void initialize() throws Exception {
        menuBar.getMenus().get(1).setDisable(true);
        createTab();
        AppManager.getTabPane(mainTab);
        ChooseController.getTabPane(mainTab);
        CredentialsController.getMainTab(mainTab);
        ConnectionPool.getTabPane(mainTab);
        ActionsCollector.getTabPane(mainTab);
        Querys.getTabPane(mainTab);
        MySqlConnection.getTabPane(mainTab);
        OracleConnection.getTabPane(mainTab);
        PostGresConnection.getTabPane(mainTab);
    }


    public void createTab() throws Exception {
        Random random = new Random();
        int id = random.nextInt();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/choose.fxml"));
        Tab tab = new Tab("New Connection");
        tab.setContent(parent);
        tab.setId(String.valueOf(id));
        mainTab.getTabs().add(tab);
        mainTab.getSelectionModel().selectLast();
        setOnTabCloseRequest(tab);
        setOnSelectionChanged(tab);
    }


    private void setOnTabCloseRequest(Tab tab) {
        tab.setOnCloseRequest(event -> {
            if (ConnectionPool.connection != null) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setHeaderText("Connection is active! Do you close it?");
                ButtonType buttonType = confirm.showAndWait().get();
                if (buttonType == ButtonType.OK) {
                    try {
                        ConnectionPool.connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (mainTab.getTabs().size() > 1) {
                        int tabIndex = mainTab.getSelectionModel().getSelectedIndex();
                        mainTab.getTabs().remove(tabIndex);
                    }
                } else if (buttonType == ButtonType.CANCEL) {
                    event.consume();
                }
            }
        });
    }


    public void setOnSelectionChanged(Tab tab) {
        tab.setOnSelectionChanged(event -> {
            ConnectionPool.switchConnection(tab);
            ActionsCollector.switchConnection(tab);
        });
    }


    public void saveConnection() {

    }

    public void closeConnection() throws SQLException {
        assert false;
        if (ConnectionPool.connection == null || ConnectionPool.connection.isClosed()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No connection detected");
            alert.setTitle("Information");
            alert.show();
        } else if (ConnectionPool.connection != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Do you want to close connection?");
            alert.setTitle("Confirmation");
            ButtonType buttonType = alert.showAndWait().get();
            if (buttonType == ButtonType.OK) {
                try {
                    ConnectionPool.connection.close();
                    if (mainTab.getTabs().size() > 1) {
                        int selected = mainTab.getSelectionModel().getSelectedIndex();
                        mainTab.getTabs().remove(selected);
                    } else {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/choose.fxml"));
                        Tab tab = mainTab.getSelectionModel().getSelectedItem();
                        tab.setContent(parent);
                    }
                } catch (SQLException | FileNotFoundException e) {
                    e.getMessage();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }


    public void quit() {
        Platform.exit();
    }


    public void undo() {
        Tab tab = queryTab.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.undo();
    }

    public void redo() {
        Tab tab = queryTab.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.redo();
    }

    public void cut() {
        Tab tab = queryTab.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.cut();
    }


    public void copy() {
        Tab tab = queryTab.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.copy();
    }

    public void paste() {
        Tab tab = queryTab.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.paste();
    }

    public void delete() {
        Tab tab = queryTab.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.replaceSelection("");

    }


    public void selectAll() {
        Tab tab = queryTab.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.selectAll();
    }

    public void deselect() {
        Tab tab = queryTab.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.deselect();
    }


    public static void getTabPane(TabPane tabPane) {
        queryTab = tabPane;
    }
}