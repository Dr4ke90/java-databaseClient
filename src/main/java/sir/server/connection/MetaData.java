package sir.server.connection;

import sir.client.connSetup.Credentials;
import sir.client.newtabs.NewTabObjects;

import java.sql.DatabaseMetaData;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MetaData {


    private static CollectorPoolManager collectorPoolManager;


   public MetaData () {
        collectorPoolManager = new CollectorPoolManager();
    }


    public String getTableName() {
        try {
            ResultSetMetaData resultSetMetaData = Result.resultSet.getMetaData();
            return resultSetMetaData.getTableName(1);
        } catch (SQLException e) {
            collectorPoolManager.addAction(e.getMessage());
        }
        return null;
    }


    public void getInfo() {
        try {
            DatabaseMetaData dbm = ConnectionPoolManager.getConnectionFromPool().getMetaData();
            NewTabObjects.getName().setText(dbm.getDatabaseProductName() + ":" + Credentials.getServerName());
            NewTabObjects.getUser().setText(dbm.getUserName());
            NewTabObjects.getHost().setText(Credentials.getIp() + ":" + Credentials.getPort());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
