package sir.server.connection;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageController {





    public static void addButtonsImage (Button send,Button open,Button save, Button newTab) {
        send.setGraphic(loadPlayImage());
        open.setGraphic(loadOpenIcon());
        save.setGraphic(loadSaveIcon());
        newTab.setGraphic(loadNewTabIcon());

    }



    public ImageView addDatabasesIcon() {
        try {
            Image image = new Image(new FileInputStream("src/main/java/sir/icons/batch_process.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            return imageView;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return null;
    }

    public ImageView addBatchImage() {
        try {
            Image image = new Image(new FileInputStream("src/main/java/sir/icons/batch.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            return imageView;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return null;
    }

    public ImageView addSchemaImage() {
        try {
            Image image = new Image(new FileInputStream("src/main/java/sir/icons/database.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            return imageView;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return null;
    }



    public ImageView addTablesImage() {
        try {
            Image image = new Image(new FileInputStream("src/main/java/sir/icons/tables.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(15);
            imageView.setFitHeight(15);
            return imageView;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return null;
    }

    public ImageView addTableImage() {
        try {
            Image image = new Image(new FileInputStream("src/main/java/sir/icons/table.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(15);
            imageView.setFitHeight(15);
            return imageView;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return null;
    }


    public ImageView addViewIcon() {
        try {
            Image image = new Image(new FileInputStream("src/main/java/sir/icons/view.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            return imageView;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return null;
    }






    public static ImageView addSqlIcon() {
        try {
            Image image = new Image(new FileInputStream("src/main/java/sir/icons/sql_query.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(25);
            imageView.setFitHeight(25);
            return imageView;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return null;
    }






    public static ImageView loadMysqlImage() {
        try {
            Image image = new Image(new FileInputStream("src/main/java/sir/icons/mysql.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);
            return imageView;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return null;
    }


    public static ImageView loadPostgresImage() {
        try {
            Image image = new Image(new FileInputStream("src/main/java/sir/icons/postgresql.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            return imageView;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return null;
    }

    public static ImageView loadOracleImage() {
        try {
            Image image = new Image(new FileInputStream("src/main/java/sir/icons/oracle.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);
            return imageView;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return null;
    }


    private static ImageView loadPlayImage() {
        try {
            Image image = new Image(new FileInputStream("src/main/java/sir/icons/play.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(25);
            imageView.setFitHeight(25);
            return imageView;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return null;
    }


    private static ImageView loadNewTabIcon() {
        try {
            Image image = new Image(new FileInputStream("src/main/java/sir/icons/add.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);
            return imageView;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return null;
    }


    private static ImageView loadOpenIcon() {
        try {
            Image image = new Image(new FileInputStream("src/main/java/sir/icons/open.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(25);
            imageView.setFitHeight(25);
            return imageView;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return null;
    }


    private static ImageView loadSaveIcon() {
        try {
            Image image = new Image(new FileInputStream("src/main/java/sir/icons/diskette.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(25);
            imageView.setFitHeight(25);
            return imageView;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return null;
    }




}
