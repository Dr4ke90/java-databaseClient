package sir.client;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import sir.server.connection.ConnectionPool;
import sir.server.connection.ImageController;
import sir.server.connection.Messages;
import sir.server.connection.Querys;
import sir.server.mysql.MySqlList;
import sir.server.oracle.OracleList;
import sir.server.postgres.PostGresList;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


public class AppManager {

    @FXML
    private Label dateLabel;
    @FXML
    private TreeView<String> list;
    @FXML
    private TableView<Map<Integer, String>> table;
    @FXML
    private Label tableTitle;
    @FXML
    private Label name;
    @FXML
    private Label host;
    @FXML
    private Label user;
    @FXML
    private TabPane tabPane;
    @FXML
    private TableView<Messages> tableMessage;
    @FXML
    private Button send;
    @FXML
    private Button open;
    @FXML
    private Button save;
    @FXML
    private Button newTab;


    private int tabIndex = 0;
    private static TabPane mainTab;


    public void initialize() {
        setTableActions();
        setSchemas();
        setStage();
        setClock();
        createTab();
        ConnectionPool.getInfo(name, host, user);
        MainController.getTabPane(tabPane);
        ImageController.addButtonsImage(send,open,save,newTab);
    }

    private void setStage() {
        Main.stage.setMinWidth(800);
        Main.stage.setMinHeight(500);
        Main.stage.setResizable(true);
        MenuBar menuBar = (MenuBar) Main.stage.getScene().lookup("#menuBar");
        menuBar.getMenus().get(1).setDisable(false);
        menuBar.getMenus().get(0).getItems().get(4).setDisable(false);

    }


    private void setClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            Date currentTime = new Date();
            SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss       MMMM dd yyyy");
            dateLabel.setText(formater.format(currentTime));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }


    public void setSchemas() {
        String server = mainTab.getSelectionModel().getSelectedItem().getText().toLowerCase();
        if (server.contains("mysql")) {
            MySqlList mySqlList = new MySqlList();
            mySqlList.getList(list, tableMessage);
        } else if (server.contains("oracle")) {
            OracleList oracle = new OracleList();
            oracle.getList(list,tableMessage);
        } else if (server.contains("postgres")) {
            PostGresList postGresList = new PostGresList();
            postGresList.getList(list, tableMessage);
        }
    }

    public void setTableActions() {
        TableColumn date = tableMessage.getColumns().get(0);
        date.setCellValueFactory(new PropertyValueFactory<>("time"));
        TableColumn message = tableMessage.getColumns().get(1);
        message.setCellValueFactory(new PropertyValueFactory<>("mess"));
    }


    public void createTab() {
        tabIndex++;
        Tab tab = new Tab("Query " + tabIndex);
        tab.setGraphic(ImageController.addSqlIcon());
        TextArea textArea = new TextArea();
        textArea.setId("textArea");
        textArea.setStyle("-fx-text-fill:green;-fx-font-weight:bold");
        tab.setContent(textArea);
        tab.setClosable(tabPane.getTabs().size() >= 1);
        tab.setOnCloseRequest(event -> tabIndex--);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().selectLast();

    }


    public void send() {
        Querys querys = new Querys();
        querys.executeQuery(tableTitle,table,tabPane);

    }


    public void saveQuery() {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQL file", "*.sql"));
        File file = fileChooser.showSaveDialog(stage);
        write(textArea.getText(), file);
    }

    private void write(String content, File file) {
        try {
            FileWriter fileWriter;
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void openFile() {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                textArea.setText(scanner.nextLine());
            }
        } catch (FileNotFoundException f) {
            System.out.println(f.getMessage());
        }
    }

    public static void getTabPane (TabPane tabPane) {
        mainTab = tabPane;
    }



    public void setSearch() {

    }


}

