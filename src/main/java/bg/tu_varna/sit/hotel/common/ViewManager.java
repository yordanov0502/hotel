package bg.tu_varna.sit.hotel.common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;


//This is a utility class which methods are often used in the application for page navigation
public final class ViewManager {
    private static Stage primaryStage;//initial and primary stage
    private static double px, py;//initial and current coordinates of primary stage
    private static double pxx, pyy;//temporary coordinates(when moving the window) of primary stage
    private static Stage secondaryStage;
    private static double sx, sy;//initial and current coordinates of secondary stage
    private static double sxx;
    private static double syy;//temporary coordinates(when moving the window) of secondary stage
    private static final Logger log = Logger.getLogger(ViewManager.class);

    private ViewManager(){}

    public static void changeView(String to, Stage stage, Class<?> classes, String title, double width, double height) throws IOException {

        if(stage==null)
        {
            primaryStage = new Stage();
            PropertyConfigurator.configure(ViewManager.class.getResource(Constants.Configurations.LOG4J_PROPERTIES));
            URL path = ViewManager.class.getResource(to);

            if(path != null)
            {
                Parent root = FXMLLoader.load(path);
                primaryStage.initStyle(StageStyle.UNDECORATED);//removes the bar with minimize,maximize and exit buttons
                primaryStage.setTitle("Hotel Management System");
                primaryStage.setScene(new Scene(root, width, height));
                primaryStage.setX(380);
                primaryStage.setY(160);
                px = primaryStage.getX();
                py = primaryStage.getY();

                root.setOnMousePressed(event -> {
                    pxx = event.getSceneX();
                    pyy = event.getSceneY();
                });

                root.setOnMouseDragged(event -> {
                    primaryStage.setX(event.getScreenX() - pxx);
                    primaryStage.setY(event.getScreenY() - pyy);
                    px = primaryStage.getX();
                    py = primaryStage.getY();
                });

                primaryStage.show();
            }
            else
            {
                log.error("Error when starting the application!(WelcomeView.fxml was not found)");
                System.exit(1);//Exits the application, because no window was loaded
            }
        }

        else
        {
            //Here the "stage" is actually the "primaryStage", because in every controller where I call this method I pass only the "primaryStage"
            //primaryStage = stage;
            Parent root = FXMLLoader.load(Objects.requireNonNull(classes.getResource(to)));
            stage.setTitle(title);
            stage.setX(px);
            stage.setY(py);
            stage.setScene(new Scene(root, width, height));

            root.setOnMousePressed(event -> {
                pxx = event.getSceneX();
                pyy = event.getSceneY();
            });

            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - pxx);
                stage.setY(event.getScreenY() - pyy);
                px = stage.getX();
                py = stage.getY();
            });

            stage.show();
        }

    }


    public static void openDialogBox(String to, Stage stage, Class<?> classes, String title, double width, double height) throws IOException {

        if(stage==null)
        {
            secondaryStage = new Stage();
            PropertyConfigurator.configure(ViewManager.class.getResource(Constants.Configurations.LOG4J_PROPERTIES));
            URL path = ViewManager.class.getResource(to);

            if(path != null)
            {
                Parent root = FXMLLoader.load(path);
                secondaryStage.initStyle(StageStyle.UNDECORATED);//removes the bar with minimize,maximize and exit buttons
                secondaryStage.setTitle(title);
                secondaryStage.setScene(new Scene(root, width, height));
                secondaryStage.setX(primaryStage.getX()+75);
                secondaryStage.setY(primaryStage.getY()+75);
                sx = secondaryStage.getX();
                sy = secondaryStage.getY();

                root.setOnMousePressed(event -> {
                    sxx = event.getSceneX();
                    syy = event.getSceneY();
                });

                root.setOnMouseDragged(event -> {
                    secondaryStage.setX(event.getScreenX() - sxx);
                    secondaryStage.setY(event.getScreenY() - syy);
                    sx = secondaryStage.getX();
                    sy = secondaryStage.getY();
                });

                secondaryStage.show();
            }
            else
            {
                log.error("Error when loading secondaryStage!(.fxml was not found)");
                System.exit(1);//Exits the application, because no window was loaded
            }
        }

        else
        {
            //Here the "stage" is actually the "secondaryStage", because in every controller where I call this method I pass only the "secondaryStage"
            //secondaryStage = stage;
            Parent root = FXMLLoader.load(Objects.requireNonNull(classes.getResource(to)));
            stage.setTitle(title);
            stage.setX(sx);
            stage.setY(sy);
            stage.setScene(new Scene(root, width, height));

            root.setOnMousePressed(event -> {
                sxx = event.getSceneX();
                syy = event.getSceneY();
            });

            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - sxx);
                stage.setY(event.getScreenY() - syy);
                sx = stage.getX();
                sy = stage.getY();
            });

            stage.show();
        }

    }

    public static void closeDialogBox()
    {
        if(ViewManager.getSecondaryStage()!=null)
        {
            ViewManager.getSecondaryStage().close();
            ViewManager.secondaryStage=null;//setSecondaryStage(null);
        }
    }

    //public static void transferHotelInformation(NewHotelInformation newHotelInformation){}

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static double getPx() {
        return px;
    }

    public static double getPy() {
        return py;
    }

    public static double getPxx() {
        return pxx;
    }

    public static double getPyy() {
        return pyy;
    }

    public static Stage getSecondaryStage() {
        return secondaryStage;
    }

    //public static void setSecondaryStage(Stage secondaryStage) {ViewManager.secondaryStage = secondaryStage;}

    public static double getSx() {
        return sx;
    }

    public static double getSy() {
        return sy;
    }

    public static double getSxx() {
        return sxx;
    }

    public static double getSyy() {return syy;}

}