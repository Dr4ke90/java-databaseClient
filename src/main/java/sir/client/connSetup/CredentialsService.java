package sir.client.connSetup;

import sir.server.connection.ConnectionPoolManager;
import sir.server.mysql.MySqlConnection;
import sir.server.oracle.OracleConnection;
import sir.server.postgres.PostGresConnection;

public class CredentialsService {


    public void setCredentials () {
        Credentials.setServerName(CredentialsObjects.getServerName().getText());
        Credentials.setIp(CredentialsObjects.getIp().getText());
        Credentials.setPort(CredentialsObjects.getPort().getText());
        Credentials.setDatabase(CredentialsObjects.getDatabase().getText());
        Credentials.setUser(CredentialsObjects.getUser().getText());
        Credentials.setPass(CredentialsObjects.getPass().getText());
        Credentials.setDriverType(CredentialsObjects.getDriver().getText());
        Credentials.setSid(CredentialsObjects.getSid().getText());
    }


    public void connect() {
        if (Credentials.getJdbc().contains("mysql")) {
            MySqlConnection mysql = new MySqlConnection();
            mysql.connect();
            if (ConnectionPoolManager.getConnectionFromPool() != null) {
                ConnectionSetup.closeStage();
            }
        } else if (Credentials.getJdbc().contains("postgres")) {
            PostGresConnection postgres = new PostGresConnection();
            postgres.getCredential(Credentials.getIp(), Credentials.getPort(), Credentials.getUser(), Credentials.getPass());
            postgres.connect();
            if (ConnectionPoolManager.getConnectionFromPool() != null) {
                ConnectionSetup.closeStage();
            }
        } else if (Credentials.getJdbc().contains("oracle")) {
            OracleConnection oracle = new OracleConnection();
            oracle.connect();
            if (ConnectionPoolManager.getConnectionFromPool() != null) {
                ConnectionSetup.closeStage();
            }
        }
    }


    public void back() throws Exception {
        ConnectionSetup connectionSetup = new ConnectionSetup();
        connectionSetup.loadChooseScene();
        CredentialsObjects.getDatabase().setDisable(true);
        CredentialsObjects.getDriver().setDisable(true);
        CredentialsObjects.getSid().setDisable(true);
    }



}
