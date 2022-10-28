package bg.tu_varna.sit.hotel.back_end.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.stage.Screen;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    public static Stage stage;//initial and main stage
    public static double x, y;//initial and current coordinates
    public static double xxx, yyy;//temporary coordinates(when moving the window)
    @Override
    public void start(Stage primaryStage) throws IOException {

        stage = primaryStage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/bg/tu_varna/sit/hotel/front_end/presentation/application/pages/WelcomeView.fxml")));
        primaryStage.initStyle(StageStyle.UNDECORATED);//removes the bar with minimize,maximize and exit buttons
        primaryStage.setTitle("Hotel Management System");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.setX(380);
        primaryStage.setY(160);
        x = primaryStage.getX();
        y = primaryStage.getY();

        root.setOnMousePressed(event -> {
            xxx = event.getSceneX();
            yyy = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xxx);
            primaryStage.setY(event.getScreenY() - yyy);
            x = primaryStage.getX();
            y = primaryStage.getY();

        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}