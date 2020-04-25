package sir.maipage;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import sir.aplication.AppController;

import java.io.*;
import java.sql.SQLException;


public class MainController  {


    @FXML
    private VBox vbox;
    public static TabPane tabPane;


    public void initialize() throws Exception {
        addTabPane();
        createTab();
    }

    private void addTabPane() {
        tabPane = new TabPane();
        tabPane.setPrefHeight(630);
        vbox.getChildren().add(tabPane);
    }

    public void createTab() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/choose.fxml"));
        Tab tab = new Tab("New Connection");
        tab.setContent(parent);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().selectLast();
        tab.setClosable(tabPane.getTabs().size() != 1);
        tab.setOnCloseRequest(event -> {
            if(ServerConnection.connection != null) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setHeaderText("Connection is active! Do you close it?");
            ButtonType buttonType =  confirm.showAndWait().get();
            if (buttonType == ButtonType.OK) {
                int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
                try {
                    ServerConnection.connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                tabPane.getTabs().remove(tabIndex);
            }else if (buttonType == ButtonType.CANCEL) {
                event.consume();
            }
            }
        });
    }


    public void saveConnection() throws Exception{

    }

    public void closeConnection() throws SQLException {
        assert false;
        if (ServerConnection.connection == null || ServerConnection.connection.isClosed()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No connection detected");
            alert.setTitle("Information");
            alert.show();
        } else if (ServerConnection.connection != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Do you want to close connection?");
            alert.setTitle("Confirmation");
            ButtonType buttonType = alert.showAndWait().get();
            if (buttonType == ButtonType.OK) {
                try {
                    ServerConnection.connection.close();
                    if (tabPane.getTabs().size() > 1) {
                        int selected = tabPane.getSelectionModel().getSelectedIndex();
                        tabPane.getTabs().remove(selected);
                    } else {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/choose.fxml"));
                        Tab tab = tabPane.getSelectionModel().getSelectedItem();
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
        Tab tab = AppController.queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.undo();
    }

    public void redo() {
        Tab tab = AppController.queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.redo();
    }

    public void cut() {
        Tab tab = AppController.queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.cut();
    }


    public void copy() {
        Tab tab = AppController.queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.copy();
    }

    public void paste() {
        Tab tab = AppController.queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.paste();
    }

    public void delete() {
        Tab tab = AppController.queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.replaceSelection("");

    }


    public void selectAll() {
        Tab tab = AppController.queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.selectAll();
    }

    public void deselect() {
        Tab tab = AppController.queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.deselect();
    }


}