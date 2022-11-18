package bg.tu_varna.sit.hotel.common;

import bg.tu_varna.sit.hotel.application.Main;
import javafx.application.Platform;
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
public final class CommonTask {

    private static final Logger log = Logger.getLogger(CommonTask.class);

    private CommonTask(){}

    public static void pageNavigation(String to, Stage stage, Class<?> classes, String title, int width, int height) throws IOException {

        if(stage==null)
        {
            Main.stage = new Stage();
            PropertyConfigurator.configure(Main.class.getResource(Constants.Configurations.LOG4J_PROPERTIES));
            URL path = Main.class.getResource(Constants.View.WELCOME_VIEW);

            if(path != null)
            {
                Parent root = FXMLLoader.load(path);
                Main.stage.initStyle(StageStyle.UNDECORATED);//removes the bar with minimize,maximize and exit buttons
                Main.stage.setTitle("Hotel Management System");
                Main.stage.setScene(new Scene(root, 800, 500));
                Main.stage.setX(380);
                Main.stage.setY(160);
                Main.x = Main.stage.getX();
                Main.y = Main.stage.getY();

                root.setOnMousePressed(event -> {
                    Main.xx = event.getSceneX();
                    Main.yy = event.getSceneY();
                });

                root.setOnMouseDragged(event -> {
                    Main.stage.setX(event.getScreenX() - Main.xx);
                    Main.stage.setY(event.getScreenY() - Main.yy);
                    Main.x = Main.stage.getX();
                    Main.y = Main.stage.getY();

                });

                Main.stage.show();
            }
            else
            {
                log.error("Error when starting the application!(WelcomeView.fxml was not found)");
                Platform.exit();//Exits the application, because no window was loaded
            }
        }

        else
        {

            //Here the "stage" is actually the "Main.stage", because in every controller I pass only the "Main.stage"
            //Main.stage = stage;
            Parent root = FXMLLoader.load(Objects.requireNonNull(classes.getResource(to)));
            stage.setTitle(title);
            stage.setX(Main.x);
            stage.setY(Main.y);
            stage.setScene(new Scene(root, width, height));

            root.setOnMousePressed(event -> {
                Main.xx = event.getSceneX();
                Main.yy = event.getSceneY();
            });

            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - Main.xx);
                stage.setY(event.getScreenY() - Main.yy);
                Main.x = stage.getX();
                Main.y = stage.getY();
            });

            stage.show();
        }

    }

}