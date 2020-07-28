package sir.client.home;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sir.client.newtabs.NewTabObjects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageController {



    public void addButtonsImage () {
        NewTabObjects.getSend().setGraphic(loadPlayImage());
        NewTabObjects.getOpen().setGraphic(loadOpenIcon());
        NewTabObjects.getSave().setGraphic(loadSaveIcon());
        NewTabObjects.getNewTab().setGraphic(loadNewTabIcon());
        NewTabObjects.getRefresh().setGraphic(loadRefreshIcon());
    }



    public static ImageView addDatabasesIcon() {
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

    public static ImageView addBatchImage() {
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


    public static ImageView addSchemaImage() {
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



    public static ImageView addTablesImage() {
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

    public static ImageView addTableImage() {
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


    public static ImageView addViewIcon() {
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


    public static ImageView loadHomeImage() {
        try {
            Image image = new Image(new FileInputStream("src/main/java/sir/icons/home.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(25);
            imageView.setFitHeight(25);
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


    private static ImageView loadRefreshIcon() {
        try {
            Image image = new Image(new FileInputStream("src/main/java/sir/icons/refresh.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(23);
            imageView.setFitHeight(25);
            return imageView;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return null;
    }



}
