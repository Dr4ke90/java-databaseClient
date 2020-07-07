package sir.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class ConnectionSetup  {

    private static final Stage stage = new Stage();


    public void loadChooseScene() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/choose.fxml"));
        Scene scene = new Scene(parent, 400, 500);
        stage.setMinWidth(400);
        stage.setMinHeight(500);
        stage.setResizable(false);
        stage.setTitle("Connection Setup");
        stage.setScene(scene);
        stage.show();
    }

    public void loadCredentialsScene() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/credential.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }


    public static void closeStage () {
        stage.close();
    }

}
