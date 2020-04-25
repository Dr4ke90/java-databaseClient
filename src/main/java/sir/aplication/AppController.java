package sir.aplication;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import sir.maipage.Main;
import sir.maipage.ServerConnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class AppController {

    @FXML
    private Label dateLabel;
    @FXML
    private ListView<String> list;
    @FXML
    private Label listTitle;
    @FXML
    private TableView<Map<Integer, String>> table;
    @FXML
    private Label tableTitle;
    @FXML
    private AnchorPane textAnchor;
    @FXML
    private AnchorPane tableAnchor;
    @FXML
    private TextField searchField;
    @FXML
    private Label name;
    @FXML
    private Label host;
    @FXML
    private Label port;
    @FXML
    private Label user;


    private static TableView<Messages> messages;
    private ResultSet resultSet;
    private ObservableList<String> obs = FXCollections.observableArrayList();
    private String query;
    private int tabIndex = 0;
    public static TabPane queryTabPane;


    public void initialize() {
        Main.stage.setMinWidth(900);
        Main.stage.setMinHeight(600);
        Main.stage.setResizable(true);
        getSchemas();
        getClock();
        setTabPane();
        createTab();
        setTableMessage();
    }





    /*




    Methods and settings for ListView



    */

    public void getSchemas() {
        Connection con = ServerConnection.connection;
        list.getItems().clear();
        try {
            Statement st = con.createStatement();
            resultSet = st.executeQuery("show databases");
            while (resultSet.next()) {
                obs.addAll(resultSet.getString(1));
            }
            list.setItems(obs);
            listTitle.setText("Schemas");
        } catch (SQLException e) {
            setMessages(e.getMessage());
        }
    }

    private void getTables() {
        list.getItems().clear();
        try {
            Statement statement = ServerConnection.connection.createStatement();
            resultSet = statement.executeQuery("show tables");
            while (resultSet.next()) {
                obs.addAll(resultSet.getString(1));
            }
            list.setItems(obs);

        } catch (SQLException e) {
            setMessages(e.getMessage());
        }
    }


    public String getTableName() {
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            return resultSetMetaData.getTableName(1);
        } catch (SQLException e) {
            setMessages(e.getMessage());
        }
        return null;
    }


    public void search() {
        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (searchField.getText() != null) {
                    if (obs.contains(searchField.getText())) {
                        list.getItems().add(searchField.getText());
                    } else {
                        list.getItems().add("No result");
                    }
                } else {
                    list.setItems(obs);

                }
            }
        });
    }





    /*



    Methods and settings for TableView



    */

    private void getResult() {
        clearTable();
        try {
            TableColumn<Map<Integer, String>, String> tableColumn;
            ObservableList<Map<Integer, String>> alldata = FXCollections.observableArrayList();
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String name = resultSet.getMetaData().getColumnName(i);
                tableColumn = new TableColumn<>(name);
                tableColumn.setCellValueFactory(new MapValueFactory(i));
                tableColumn.setPrefWidth(100);
                table.getColumns().addAll(tableColumn);
            }
            while (resultSet.next()) {
                Map<Integer, String> dataRow = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String value = resultSet.getString(i);
                    dataRow.put(i, value);
                }
                alldata.add(dataRow);
            }
            table.setItems(alldata);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            String name = resultSetMetaData.getTableName(1);
            tableTitle.setText(name);
        } catch (SQLException e) {
            setMessages(e.getMessage());
        }
    }


    private void clearTable() {
        table.getColumns().clear();
        table.getItems().clear();
        tableTitle.setText("");
    }





    /*





    Methods and setting for Querys Area


    */

    public void setTabPane() {
        queryTabPane = new TabPane();
        textAnchor.getChildren().add(queryTabPane);
        AnchorPane.setBottomAnchor(queryTabPane, 0.0);
        AnchorPane.setLeftAnchor(queryTabPane, 41.0);
        AnchorPane.setTopAnchor(queryTabPane, 30.0);
        AnchorPane.setRightAnchor(queryTabPane, 0.0);
    }

    public void createTab() {
        tabIndex++;
        Tab tab = new Tab("Query " + tabIndex);
        TextArea textArea = new TextArea();
        textArea.setId("textArea");
        textArea.setStyle("-fx-text-fill:green;-fx-font-weight:bold");
        tab.setContent(textArea);
        tab.setOnCloseRequest(event -> {
            tabIndex--;
        });
        queryTabPane.getTabs().add(tab);
        queryTabPane.getSelectionModel().selectLast();
        tab.setClosable(queryTabPane.getTabs().size() != 1);
    }

    private void getQuery() {
        Tab tab = queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        query = textArea.getText().toLowerCase();
    }

    public void sendQuery() {
        executeQuery();
    }


    public void copyText() {
        Tab tab = queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.copy();
    }


    public void cutText() {
        Tab tab = queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.cut();
    }


    public void pasteText() {
        Tab tab = queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        textArea.paste();
    }

    public void saveQuery() {
        Tab tab = queryTabPane.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) tab.getContent().lookup("#textArea");
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT file", "*.txt"));
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
            setMessages(ex.getMessage());
        }
    }

    public void openFile() {
        Tab tab = queryTabPane.getSelectionModel().getSelectedItem();
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
            setMessages(f.getMessage());
        }
    }


    private void executeQuery() {

        getQuery();
        try {
            Statement statement = ServerConnection.connection.createStatement();
            if (query.startsWith("use")) {
                if (listTitle.getText().equals("Schemas")) {
                    statement.executeUpdate(query);
                    list.getItems().clear();
                    getTables();
                    String schema = query.substring(4);
                    listTitle.setText(schema);
                    setMessages("Use " + schema);
                } else {
                    setMessages("Use statement is not allowed here");
                }
            } else if (query.startsWith("select")) {
                if (!listTitle.getText().equals("Schemas")) {
                    resultSet = statement.executeQuery(query);
                    getResult();
                    setMessages("Select from " + getTableName());
                } else {
                    setMessages("No database selected");
                }
            } else if (query.startsWith("create")) {
                if (query.contains("database")) {
                    if (listTitle.getText().equals("Schemas")) {
                        statement.executeUpdate(query);
                        getSchemas();
                        setMessages("Create database " + query.substring(16));
                        list.getSelectionModel().select(query.substring(16));
                    } else {
                        setMessages("Create database is not allowed here");
                    }
                } else if (query.contains("table")) {
                    if (!listTitle.getText().equals("Schemas")) {
                        statement.executeUpdate(query);
                        getTables();
                        setMessages("Create table " + (query.substring(13, query.indexOf("("))));
                        list.getSelectionModel().select(query.substring(13));
                    } else {
                        setMessages("No database selected");
                    }
                }
            } else if (query.startsWith("alter table") && tableTitle.getText().equalsIgnoreCase(query.substring(11))) {
                statement.executeUpdate(query);
                setMessages("Alter table " + getTableName());
                resultSet = statement.executeQuery("select * from " + getTableName());
                getResult();

            } else if (query.startsWith("rename")) {
                if (query.contains("database")) {
                    if (listTitle.getText().equals("Schemas")) {
                        statement.executeUpdate(query);
                        getSchemas();
                        setMessages("Database renamed succesfuly");
                    } else {
                        setMessages("Rename database is not allowed here ! Need to list Databases");
                    }
                } else if (query.contains("table")) {
                    if (!listTitle.getText().equals("Schemas")) {
                        statement.executeUpdate(query);
                        getTables();
                        setMessages("Table has been succesfuly rename");
                    } else if (tableTitle.getText().equals(query.substring(13))) {
                        tableTitle.setText(getTableName());
                    } else {
                        setMessages("No database selected");
                    }
                }
            } else if (query.startsWith("truncate")) {
                if (tableTitle.getText().equalsIgnoreCase(query.substring(16))) {
                    statement.executeUpdate(query);
                    setMessages("Truncate executate");
                    resultSet = statement.executeQuery("select * from " + query.substring(16));
                    getResult();
                } else if (listTitle.getText().equals("Schemas")) {
                    setMessages("No database selected");
                } else {
                    setMessages("Truncate is not allowed here");
                }
            } else if (query.startsWith("drop")) {
                if (query.contains("database")) {
                    if (listTitle.getText().equals("Schemas")) {
                        statement.executeUpdate(query);
                        getSchemas();
                        setMessages("Drop database " + query.substring(14));
                    } else if (listTitle.getText().equals(query.substring(14))) {
                        clearTable();
                    } else {
                        setMessages("No database selected");
                    }
                } else if (query.contains("table")) {
                    if (!listTitle.getText().equals("Schemas")) {
                        statement.executeUpdate(query);
                        getTables();
                        setMessages("Drop table " + query.substring(11));
                    }
                    if (tableTitle.getText().equals(query.substring(11))) {
                        clearTable();
                    } else {
                        setMessages("No database selected");
                    }
                }
            } else if (query.startsWith("insert") || query.startsWith("update") || query.startsWith("delete") ||
                    query.startsWith("merge") || query.startsWith("call")) {
                if (!listTitle.getText().equals("Schemas")) {
                    if (tableTitle.getText().equals(getTableName())) {
                        statement.executeUpdate(query);
                        setMessages("Update executate");
                        resultSet = statement.executeQuery("select * from " + getTableName());
                        getResult();
                    } else {
                        setMessages("No table selected");
                    }
                } else {
                    setMessages("No database selected");
                }
            } else if (query.startsWith("show")) {
                if (query.contains("databases")) {
                    if (!listTitle.getText().equals("Schemas")) {
                        clearTable();
                        getSchemas();
                        setMessages("Database has been succesfuly loaded");
                    } else {
                        setMessages("Database are already listed");
                    }
                } else if (query.contains("tables")) {
                    if (!listTitle.getText().equals("Schemas")) {
                        getTables();
                        setMessages("Tables already listed");
                    } else {
                        setMessages("Not allowed here");
                    }
                }
            } else if (!listTitle.getText().equals("Schemas") && tableTitle.getText() != null) {
                resultSet = statement.executeQuery(query);
                getResult();
            }
        } catch (SQLException e) {
            setMessages(e.getMessage());
        }
    }
    






    /*




    Methods and setting for sir.aplication.Messages Area




    */

    public void setMessages(String text) {
        Messages mess = new Messages(text);
        ObservableList<Messages> data = FXCollections.observableArrayList();

        data.add(mess);

        messages.setItems(data);

    }


    public void setTableMessage() {
        TableColumn<Messages, Integer> id = new TableColumn<>("#");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setMinWidth(50);
        id.setMaxWidth(50);
        TableColumn<Messages, String> date = new TableColumn<>("Time");
        date.setCellValueFactory(new PropertyValueFactory<>("time"));
        date.setMaxWidth(150);
        date.setMinWidth(150);
        TableColumn<Messages, String> message = new TableColumn<>("Action");
        message.setCellValueFactory(new PropertyValueFactory<>("mess"));
        message.setMinWidth(600);

        messages = new TableView();
        messages.getColumns().addAll(id, date, message);

        tableAnchor.getChildren().addAll(messages);
        AnchorPane.setBottomAnchor(messages, 0.0);
        AnchorPane.setLeftAnchor(messages, 0.0);
        AnchorPane.setRightAnchor(messages, 0.0);
        AnchorPane.setTopAnchor(messages, 0.0);
    }








    /*


    Set Date and Time


    */

    private void getClock() {
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



}

