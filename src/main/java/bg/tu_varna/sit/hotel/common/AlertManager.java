package bg.tu_varna.sit.hotel.common;

import bg.tu_varna.sit.hotel.application.Main;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

public final class AlertManager {
    private static final Logger log = Logger.getLogger(AlertManager.class);

    private AlertManager(){}

    public static void showAlert(Alert.AlertType type, String title, String header){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText("✅ Регистрирахте се успешно.");
        alert.setX((Main.x+Main.stage.getWidth())/2);
        alert.setY((Main.y+Main.stage.getHeight())/2);
        alert.showAndWait();
    }
}
