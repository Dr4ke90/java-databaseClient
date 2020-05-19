package sir.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Main extends Application {
    public static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.stage = primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(new FileInputStream("src/main/java/sir/fxml/main.fxml"));

        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(600);
        primaryStage.setResizable(false);


    }
}
