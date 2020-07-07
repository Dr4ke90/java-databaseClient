package sir.client;

import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import sir.server.connection.Messages;
import sir.server.mysql.MySqlList;
import sir.server.oracle.OracleList;
import sir.server.postgres.PostGresList;

public class AplicationService {

    public void setSchemas(TreeView<String> list, TableView<Messages> tableMessage) {
        if (Credentials.getJdbc().contains("mysql")) {
            MySqlList mySqlList = new MySqlList();
            mySqlList.getList(list, tableMessage);
        } else if (Credentials.getJdbc().contains("oracle")) {
            OracleList oracle = new OracleList();
            oracle.getList(list, tableMessage);
        } else if (Credentials.getJdbc().contains("postgres")) {
            PostGresList postGresList = new PostGresList();
            postGresList.getList(list, tableMessage);
        }
    }
}
