package sir.client;


import javafx.scene.control.TextField;

public class ChooseController {

    private static TextField database;
    private static TextField driverType;
    private static TextField sid;


    public void setMysqlConnection() throws Exception {
        ConnectionSetup connectionSetup = new ConnectionSetup();
        connectionSetup.loadCredentialsScene();
        Credentials.setJdbc("jdbc:mysql://");
        database.setDisable(false);
    }


    public void setPostGresConnection() throws Exception {
        ConnectionSetup connectionSetup = new ConnectionSetup();
        connectionSetup.loadCredentialsScene();
        Credentials.setJdbc("jdbc:postgresql://");
        database.setDisable(false);
    }


    public void setOracleConnection() throws Exception {
        ConnectionSetup connectionSetup = new ConnectionSetup();
        connectionSetup.loadCredentialsScene();
        Credentials.setJdbc("jdbc:oracle:");
        driverType.setDisable(false);
        sid.setDisable(false);
    }

    public static void set(TextField database, TextField driverType, TextField sid) {
        ChooseController.database = database;
        ChooseController.driverType = driverType;
        ChooseController.sid = sid;

    }

}
