package sir.client.contextMenu;


import javafx.scene.control.TreeItem;
import sir.client.newtabs.SelectedTab;

public class ContextMenuService {



    public void read () {
     TreeItem<String> item = SelectedTab.getList().getSelectionModel().getSelectedItem();
     String value = item.getValue();

    }

}
