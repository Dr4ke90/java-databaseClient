package sir.client;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sir.server.connection.ConnectionPool;
import sir.server.mysql.MySqlConnection;
import sir.server.oracle.OracleConnection;
import sir.server.postgres.PostGresConnection;


public class CredentialsController {

    @FXML
    private TextField ip;
    @FXML
    private TextField port;
    @FXML
    private TextField user;
    @FXML
    private PasswordField pass;
    @FXML
    private TextField serverName;
    @FXML
    public TextField database;
    @FXML
    private TextField driver;
    @FXML
    private TextField sid;
    @FXML
    private Label errorMessage;



    public void initialize() {
        MySqlConnection.getLabel(errorMessage);
        PostGresConnection.getLabel(errorMessage);
        OracleConnection.getLabel(errorMessage);
        ChooseController.set(database,driver,sid);
    }


    public void connect() {
        getCredentials();
        if (Credentials.getJdbc().contains("mysql")) {
            MySqlConnection mysql = new MySqlConnection();
            mysql.connect(serverName.getText());
            if (ConnectionPool.connection != null) {
                ConnectionSetup.closeStage();
            }
        } else if (Credentials.getJdbc().contains("postgres")) {
            PostGresConnection postgres = new PostGresConnection();
            postgres.getCredential(ip.getText(), port.getText(), user.getText(), pass.getText());
            postgres.connect(serverName.getText());
            if (ConnectionPool.connection != null) {
                ConnectionSetup.closeStage();
            }
        } else if (Credentials.getJdbc().contains("oracle")) {
            OracleConnection oracle = new OracleConnection();
            oracle.connect(serverName.getText());
            if (ConnectionPool.connection != null) {
                ConnectionSetup.closeStage();
            }
        }
    }

    public void back() throws Exception {
            ConnectionSetup connectionSetup = new ConnectionSetup();
            connectionSetup.loadChooseScene();
            desableField();
    }

    public void desableField () {
        database.setDisable(true);
        driver.setDisable(true);
        sid.setDisable(true);
    }


    public void getCredentials() {
            Credentials.setIp(ip.getText());
            Credentials.setPort(port.getText());
            Credentials.setUser(user.getText());
            Credentials.setPass(pass.getText());
            Credentials.setServerName(serverName.getText());
            Credentials.setDriverType(driver.getText());
            Credentials.setSid(sid.getText());
            Credentials.setDatabase(database.getText());
        }




        }







