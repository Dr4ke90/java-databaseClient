package sir.client.newtabs;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sir.client.contextMenu.RightClickMenu;
import sir.client.home.ImageController;
import sir.server.connection.Messages;

import java.util.Map;

public class NewTabController {

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
    private TabPane queryTabPane;
    @FXML
    private TableView<Messages> tableMessage;
    @FXML
    private Button send;
    @FXML
    private Button open;
    @FXML
    private Button save;
    @FXML
    private Button newQueryTab;
    @FXML
    private Button refresh;


    public void initialize() {
        NewTabObjects newTabObjects = new NewTabObjects();
        newTabObjects.setObjects(dateLabel,list,table,tableTitle,name,host,user,queryTabPane,tableMessage,
                send,open,save, newQueryTab, refresh);

        NewTabService newTabService = new NewTabService();
        newTabService.setSchemas();
        newTabService.setTableActions();
        newTabService.setClock();
        newTabService.setInfo();
        newTabService.activateMenu();
        newTabService.setContextMenu();
        ImageController imageController = new ImageController();
        imageController.addButtonsImage();

        newQueryTab();
    }


    public void newQueryTab() {
        TextAreaService textAreaService = new TextAreaService();
        textAreaService.createTab();
    }


    public void send() {
        TextAreaService textAreaService = new TextAreaService();
        textAreaService.send();
    }


    public void saveQuery() {
        TextAreaService textAreaService = new TextAreaService();
        textAreaService.saveQuery();
    }

    public void openFile() {
        TextAreaService textAreaService = new TextAreaService();
        textAreaService.openFile();
    }

    public void refresh () {
        NewTabService newTabService = new NewTabService();
        newTabService.setSchemas();
    }

}

