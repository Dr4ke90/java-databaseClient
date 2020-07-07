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


public class AplicationController {

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

    private int tabIndex;
    private static MenuBar menuBar;


    public void initialize() {
        setSchemas();
        setClock();
        setTableActions();
        createTab();
        activateMenu();
        ConnectionPool.getInfo(name, host, user);
        ImageController.addButtonsImage(send, open, save, newTab);
        list.setContextMenu(RightClickMenu.handleDatabaseMenu());
    }


    public void setSchemas() {
      AplicationService aplicationService = new AplicationService();
      aplicationService.setSchemas(list,tableMessage);
      RightClickMenu rightClickMenu = new RightClickMenu();
      rightClickMenu.getList(list,tableMessage);
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

    private void setTableActions() {
        TableColumn date = tableMessage.getColumns().get(0);
        date.setCellValueFactory(new PropertyValueFactory<>("time"));
        TableColumn message = tableMessage.getColumns().get(1);
        message.setCellValueFactory(new PropertyValueFactory<>("mess"));
    }

    public void send() {
        Querys querys = new Querys();
        querys.executeQuery(tableTitle, table, tabPane, HomeController.getTabPane());
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

    private void activateMenu() {
        if (ConnectionPool.connection != null) {
            Menu file = menuBar.getMenus().get(0);
            MenuItem close = file.getItems().get(1);
            MenuItem save = file.getItems().get(2);
            close.setDisable(false);
            save.setDisable(false);
            Menu edit = menuBar.getMenus().get(1);
            edit.setDisable(false);
        }
    }

    public static void getMenuBar(MenuBar menuBarr) {
        menuBar = menuBarr;
    }


}

