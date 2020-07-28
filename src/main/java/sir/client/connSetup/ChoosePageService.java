package sir.client.connSetup;

public class ChoosePageService {


    public void setMysqlConnection() throws Exception {
        ConnectionSetup connectionSetup = new ConnectionSetup();
        connectionSetup.loadCredentialsScene();
        Credentials.setJdbc("jdbc:mysql://");
        CredentialsObjects.getDatabase().setDisable(false);
    }


    public void setPostGresConnection() throws Exception {
        ConnectionSetup connectionSetup = new ConnectionSetup();
        connectionSetup.loadCredentialsScene();
        Credentials.setJdbc("jdbc:postgresql://");
      CredentialsObjects.getDatabase().setDisable(false);
    }


    public void setOracleConnection() throws Exception {
        ConnectionSetup connectionSetup = new ConnectionSetup();
        connectionSetup.loadCredentialsScene();
        Credentials.setJdbc("jdbc:oracle:");
        CredentialsObjects.getDriver().setDisable(false);
        CredentialsObjects.getSid().setDisable(false);
    }
}
