package sir.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import sir.server.connection.ActionsCollector;
import sir.server.connection.ConnectionPool;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;


public class HomeController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private MenuBar menuBar;
    private static TabPane tabPane;

    public static TabPane getTabPane() {
        return tabPane;
    }



    public void initialize() throws Exception {
        AplicationController.getMenuBar(menuBar);
        setTabPane();
    }



    private void setTabPane() throws Exception {
        tabPane = new TabPane();
        tabPane.setId("tabPane");
        anchorPane.getChildren().add(tabPane);
        AnchorPane.setTopAnchor(tabPane, 25.0);
        AnchorPane.setBottomAnchor(tabPane, 0.0);
        AnchorPane.setLeftAnchor(tabPane, 0.0);
        AnchorPane.setRightAnchor(tabPane, 0.0);

        Tab home = new Tab();
        home.setClosable(false);
        home.setGraphic(ImageController.loadHomeImage());
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/homeTab.fxml"));
        home.setContent(parent);

        tabPane.getTabs().add(home);

        Button button = (Button) parent.lookup("#newConnection");
        setButton(button);


    }


    private void setButton(Button button) {
        button.setOnMouseClicked(event -> {
            ConnectionSetup connectionSetup = new ConnectionSetup();
            try {
                connectionSetup.loadChooseScene();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }


    public void addTabContent(Tab tab) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = null;
        try {
            parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/aplication.fxml"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        tab.setContent(parent);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().selectLast();

        setOnTabCloseRequest(tab);

        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        setOnSelectionChanged(selectedTab);


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
                } else if (buttonType == ButtonType.CANCEL) {
                    event.consume();
                }
            }
        });
    }


    private void setOnSelectionChanged(Tab tab) {
        tab.setOnSelectionChanged(event -> {
            ConnectionPool.switchConnection(tab);
            ActionsCollector.switchCollector(tab);
        });
    }


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
                ConnectionPool.connection.close();
                int selectedTab = tabPane.getSelectionModel().getSelectedIndex();
                Tab tab = tabPane.getSelectionModel().getSelectedItem();
                tabPane.getTabs().remove(selectedTab);
                ConnectionPool.removeFromPool(tab);
                ActionsCollector.removeCollector(tab);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void quit() {
        Platform.exit();
    }


    public void undo() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        TabPane queryTab = (TabPane) selectedTab.getContent().lookup("#queryTabPane");
        Tab textTab = queryTab.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) textTab.getContent().lookup("#textArea");
        textArea.undo();
    }

    public void redo() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        TabPane queryTab = (TabPane) selectedTab.getContent().lookup("#queryTabPane");
        Tab textTab = queryTab.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) textTab.getContent().lookup("#textArea");
        textArea.redo();
    }

    public void cut() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        TabPane queryTab = (TabPane) selectedTab.getContent().lookup("#queryTabPane");
        Tab textTab = queryTab.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) textTab.getContent().lookup("#textArea");
        textArea.cut();
    }


    public void copy() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        TabPane queryTab = (TabPane) selectedTab.getContent().lookup("#queryTabPane");
        Tab textTab = queryTab.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) textTab.getContent().lookup("#textArea");
        textArea.copy();
    }

    public void paste() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        TabPane queryTab = (TabPane) selectedTab.getContent().lookup("#queryTabPane");
        Tab textTab = queryTab.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) textTab.getContent().lookup("#textArea");
        textArea.paste();
    }

    public void delete() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        TabPane queryTab = (TabPane) selectedTab.getContent().lookup("#queryTabPane");
        Tab textTab = queryTab.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) textTab.getContent().lookup("#textArea");
        textArea.replaceSelection("");

    }


    public void selectAll() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        TabPane queryTab = (TabPane) selectedTab.getContent().lookup("#queryTabPane");
        Tab textTab = queryTab.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) textTab.getContent().lookup("#textArea");
        textArea.selectAll();
    }

    public void deselect() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        TabPane queryTab = (TabPane) selectedTab.getContent().lookup("#queryTabPane");
        Tab textTab = queryTab.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) textTab.getContent().lookup("#textArea");
        textArea.deselect();
    }


}
