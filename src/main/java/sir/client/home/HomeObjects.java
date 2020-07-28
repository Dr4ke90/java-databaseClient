package sir.client.home;

import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;

public class HomeObjects {

    private static MenuBar menuBar;
    private static TabPane tabPane;

    public void setObjects(MenuBar menuBar, TabPane tabPane) {
        HomeObjects.menuBar = menuBar;
        HomeObjects.tabPane = tabPane;
    }

    public static MenuBar getMenuBar() {
        return menuBar;
    }


    public static TabPane  getTabPane() {
        return tabPane;
    }


}
