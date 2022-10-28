package bg.tu_varna.sit.hotel.back_end.common;

import bg.tu_varna.sit.hotel.back_end.application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

//This is a utility class which methods are often used in the application
public final class CommonTask {

    public static Stage newStage; //current stage of the application
    public static double xx, yy; //temporary coordinates(when moving the current window)

    private CommonTask(){}

    public static void pageNavigation(String to, Stage stage, Class<?> classes, String title, int width, int height) throws IOException {
        double xTemp = Main.x;
        double yTemp = Main.y;

        if (stage == null) {
            xTemp = Main.x + (width/5.0);
            yTemp = Main.y + (height/7.0);
            stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            newStage = stage;
        }

        newStage = stage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(classes.getResource(to)));
        stage.setTitle(title);
        stage.setX(xTemp);
        stage.setY(yTemp);
        stage.setScene(new Scene(root, width, height));

        root.setOnMousePressed(event -> {
            xx = event.getSceneX();
            yy = event.getSceneY();
        });

        Stage finalStage = stage;
        root.setOnMouseDragged(event -> {
            finalStage.setX(event.getScreenX() - xx);
            finalStage.setY(event.getScreenY() - yy);
            Main.x = finalStage.getX();
            Main.y = finalStage.getY();

        });
        Main.x = finalStage.getX();
        Main.y = finalStage.getY();

        stage.show();
    }

}