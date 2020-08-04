package sir.client.connSetup;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CredentialsObjects {

    private static TextField ip;
    private static TextField port;
    private static TextField user;
    private static PasswordField pass;
    private static TextField serverName;
    private static TextField database;
    private static TextField driver;
    private static TextField sid;
    private static Label errorMessage;

    public void getObjects(TextField ip, TextField port, TextField user, PasswordField pass, TextField serverName,
                           TextField database, TextField driver, TextField sid, Label errorMessage) {
        CredentialsObjects.ip = ip;
        CredentialsObjects.port = port;
        CredentialsObjects.user =user;
        CredentialsObjects.pass = pass;
        CredentialsObjects.serverName = serverName;
        CredentialsObjects.database = database;
        CredentialsObjects.driver = driver;
        CredentialsObjects.sid = sid;
        CredentialsObjects.errorMessage = errorMessage;
    }

    public static TextField getIp() {
        return ip;
    }


    public static TextField getPort() {
        return port;
    }


    public static TextField getUser() {
        return user;
    }


    public static PasswordField getPass() {
        return pass;
    }


    public static TextField getServerName() {
        return serverName;
    }


    public static TextField getDatabase() {
        return database;
    }


    public static TextField getDriver() {
        return driver;
    }


    public static TextField getSid() {
        return sid;
    }


    public static Label getErrorMessage() {
        return errorMessage;
    }

}
