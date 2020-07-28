package sir.client.home;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sir.client.connSetup.ConnectionSetup;


public class HomeController {

    @FXML
    private MenuBar menuBar;
    @FXML
    private TabPane tabPane;
    private MenuBarService menuBarService;


    public void initialize() {
        HomeObjects homeObjects = new HomeObjects();
        homeObjects.setObjects(menuBar, tabPane);
        TabPaneService tabPaneService = new TabPaneService();
        tabPaneService.setHomeTabGraphic();

        menuBarService = new MenuBarService();
    }


    public void setPlusButton() throws Exception {
        ConnectionSetup connectionSetup = new ConnectionSetup();
        connectionSetup.loadChooseScene();
    }


    public void newConnection() throws Exception {
        menuBarService.newConnection();
    }

    public void closeConnection() {
        menuBarService.closeConnection();
    }


    public void quit() {
        menuBarService.quit();
    }


    public void undo() {
        menuBarService.undo();
    }

    public void redo() {
        menuBarService.redo();
    }

    public void cut() {
        menuBarService.cut();
    }


    public void copy() {
        menuBarService.copy();
    }

    public void paste() {
        menuBarService.paste();
    }

    public void delete() {
        menuBarService.delete();
    }


    public void selectAll() {
        menuBarService.selectAll();
    }

    public void deselect() {
        menuBarService.deselect();

    }
}
