package sir.client.home;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import sir.client.connSetup.Credentials;
import sir.server.connection.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

public class TabPaneService {


    public void setHomeTabGraphic() {
        Tab homeTab = HomeObjects.getTabPane().getTabs().get(0);
        homeTab.setGraphic(ImageController.loadHomeImage());
    }


    public void createNewTab(Connection connection) throws Exception{
        Tab tab = new Tab("");
        setTabId(tab);
        setTabName(tab);
        setTabGrafics(tab);
        setOnTabCloseRequest(tab);
        addTabToTabPane(tab);
        ConnectionPool.getConnectionPool().put(tab.getId(),connection);
        CollectorPool.getCollectorPool().put(tab.getId(), CollectorPoolManager.createNewCollector());
        setTabContent(tab);
    }

    private void setTabName(Tab tab) {
        if (Credentials.getJdbc().toLowerCase().contains("mysql")) {
            tab.setText("MySQL/" + Credentials.getServerName());
        } else if (Credentials.getJdbc().toLowerCase().contains("postgres")) {
            tab.setText("PostGres/" + Credentials.getServerName());
        } else if (Credentials.getJdbc().toLowerCase().contains("oracle")) {
            tab.setText("Oracle/" + Credentials.getServerName());
        }
    }

    private void setTabGrafics (Tab tab) {
        if (Credentials.getJdbc().toLowerCase().contains("mysql")) {
            tab.setGraphic(ImageController.loadMysqlImage());
        } else if (Credentials.getJdbc().toLowerCase().contains("postgres")) {
            tab.setGraphic(ImageController.loadPostgresImage());
        } else if (Credentials.getJdbc().toLowerCase().contains("oracle")) {
            tab.setGraphic(ImageController.loadOracleImage());
        }
    }

    private void setTabId(Tab tab) {
        Random random = new Random();
        int id = random.nextInt();
        tab.setId(String.valueOf(id));
    }

    private void setTabContent(Tab tab) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/newtab.fxml"));
        tab.setContent(parent);
    }



    private void addTabToTabPane(Tab tab) {
        HomeObjects.getTabPane().getTabs().add(tab);
        HomeObjects.getTabPane().getSelectionModel().selectLast();
    }


    private void setOnTabCloseRequest(Tab tab) {
        tab.setOnCloseRequest(event -> {
            Connection connection = ConnectionPoolManager.getConnectionFromPool();
            if (connection != null) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setHeaderText("Connection is active! Do you close it?");
                ButtonType buttonType = confirm.showAndWait().get();
                if (buttonType == ButtonType.OK) {
                    try {
                        connection.close();
                        String id = getSelectedTab().getId();
                        ConnectionPool.getConnectionPool().remove(id);
                        CollectorPool.getCollectorPool().remove(id);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else if (buttonType == ButtonType.CANCEL) {
                    event.consume();
                }
            }
        });
    }



    public static Tab getSelectedTab () {
        return HomeObjects.getTabPane().getSelectionModel().getSelectedItem();
    }
}
