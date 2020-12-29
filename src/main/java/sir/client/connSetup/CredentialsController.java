package sir.client.connSetup;

import javafx.fxml.FXML;
import javafx.scene.control.*;


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
       CredentialsObjects credentialsObjects = new CredentialsObjects();
       credentialsObjects.getObjects(ip,port,user,pass,serverName,database,driver,sid,errorMessage);
    }


    public void connect() {
        CredentialsService credentialsService = new CredentialsService();
        credentialsService.setCredentials();
        credentialsService.connect();

    }

    public void back() throws Exception {
       CredentialsService credentialsService = new CredentialsService();
       credentialsService.back();
    }


}







