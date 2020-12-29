package sir.client.newtabs;

import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sir.client.home.ImageController;
import sir.server.connection.ConnectionPool;
import sir.server.connection.Querys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TextAreaService {


    public void createTab() {
        int index = countTabs() + 1;
        Tab tab = new Tab("Query " + index);
        tab.setGraphic(ImageController.addSqlIcon());
        TextArea textArea = new TextArea();
        textArea.setId("textArea");
        textArea.setStyle("-fx-text-fill:green;-fx-font-weight:bold");
        tab.setContent(textArea);
        tab.setClosable(SelectedTab.getQueryTabPane().getTabs().size() >= 1);
        if (ConnectionPool.getConnectionPool().size() != 0) {
            SelectedTab.getQueryTabPane().getTabs().add(tab);
            SelectedTab.getQueryTabPane().getSelectionModel().selectLast();
        }
    }



    private Integer countTabs() {
        return SelectedTab.getQueryTabPane().getTabs().size();
    }

    public void send() {
        Querys querys = new Querys();
        querys.executeQuery();
    }


    public void saveQuery() {
        Tab tab = NewTabObjects.getQueryTabPane().getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQL file", "*.sql"));
        File file = fileChooser.showSaveDialog(stage);
        write(textArea.getText(), file);
    }

    private void write(String content, File file) {
        try {
            if (file != null) {
                FileWriter fileWriter;
                fileWriter = new FileWriter(file);
                fileWriter.write(content);
                fileWriter.close();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void openFile() {
        Tab tab = NewTabObjects.getQueryTabPane().getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        try {
            if (file != null) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    textArea.setText(scanner.nextLine());
                }
            }
        } catch (FileNotFoundException f) {
            System.out.println(f.getMessage());
        }
    }
}
