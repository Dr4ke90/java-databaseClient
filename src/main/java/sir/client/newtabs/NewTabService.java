package sir.client.newtabs;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import sir.client.connSetup.Credentials;
import sir.client.contextMenu.RightClickMenu;
import sir.client.home.HomeObjects;
import sir.server.connection.ConnectionPoolManager;
import sir.server.connection.MetaData;
import sir.server.mysql.MySqlList;
import sir.server.oracle.OracleList;
import sir.server.postgres.PostGresList;

import java.text.SimpleDateFormat;
import java.util.Date;


public class NewTabService {



    public void setSchemas() {
        if (Credentials.getJdbc().contains("mysql")) {
            MySqlList mySqlList = new MySqlList();
            mySqlList.getList();
        } else if (Credentials.getJdbc().contains("oracle")) {
            OracleList oracle = new OracleList();
            oracle.getList();
        } else if (Credentials.getJdbc().contains("postgres")) {
            PostGresList postGresList = new PostGresList();
            postGresList.getList();
        }
    }




    public void setClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            Date currentTime = new Date();
            SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss       MMMM dd yyyy");
            NewTabObjects.getDateLabel().setText(formater.format(currentTime));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }


    public void activateMenu() {
        if (ConnectionPoolManager.getConnectionFromPool() != null) {
            Menu file = HomeObjects.getMenuBar().getMenus().get(0);
            MenuItem close = file.getItems().get(1);
            MenuItem save = file.getItems().get(2);
            close.setDisable(false);
            save.setDisable(false);
            Menu edit = HomeObjects.getMenuBar().getMenus().get(1);
            edit.setDisable(false);
        }
    }

    public void setTableActions() {
        TableColumn date = NewTabObjects.getTableMessage().getColumns().get(0);
        date.setCellValueFactory(new PropertyValueFactory<>("time"));
        TableColumn message = NewTabObjects.getTableMessage().getColumns().get(1);
        message.setCellValueFactory(new PropertyValueFactory<>("mess"));
    }


    public void setInfo() {
        MetaData metaData = new MetaData();
        metaData.getInfo();
    }


    public void setContextMenu () {
        NewTabObjects.getList().setContextMenu(RightClickMenu.handleDatabaseMenu());
    }


    
}