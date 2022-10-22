package bg.tu_varna.sit.hotel.back_end.common;

import bg.tu_varna.sit.hotel.back_end.application.Welcome;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class CommonTask extends Welcome {

    public static Stage newStage;
    public static double xx, yy;

    public static void pageNavigation(String to, Stage stage, Class<?> classes, String title, int width, int height) throws IOException {
        double xTemp = x;
        double yTemp = y;

        if (stage == null) {
            xTemp = x + (width/5.0);
            yTemp = y + (height/7.0);
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
            x = finalStage.getX();
            y = finalStage.getY();

        });
        x = finalStage.getX();
        y = finalStage.getY();

        stage.show();

    }

}