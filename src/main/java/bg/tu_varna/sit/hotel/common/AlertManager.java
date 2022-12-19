package bg.tu_varna.sit.hotel.common;

import bg.tu_varna.sit.hotel.application.Main;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

public final class AlertManager {
    private static final Logger log = Logger.getLogger(AlertManager.class);

    private AlertManager(){}

    public static void showAlert(Alert.AlertType type,String header, String message){
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText(message);
        alert.setX(ViewManager.getPrimaryStage().getX()+220);
        alert.setY(ViewManager.getPrimaryStage().getY()+180);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}
