package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.*;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class OwnerHotelManagerAddController implements NewHotelInfoProvider {
    private static final Logger log = Logger.getLogger(OwnerHotelManagerAddController.class);
    private final UserService userService = UserService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField managerNameField;
    @FXML
    private TextField managerSurnameField;
    @FXML
    private TextField managerEGNField;
    @FXML
    private TextField managerPhoneField;
    @FXML
    private TextField managerUsernameField;
    @FXML
    private TextField managerEmailField;
    @FXML
    private PasswordField managerPasswordField;
    @FXML
    private Button addHotelManagerButton;


    public void addHotelManager() throws IOException {
        if (userService.validateFields(new String[]{managerNameField.getText(), managerSurnameField.getText(), managerEGNField.getText(), managerPhoneField.getText(), managerUsernameField.getText(), managerEmailField.getText(), managerPasswordField.getText()})
                && !userService.checkForExistingUserData(new String[]{managerEGNField.getText(), managerPhoneField.getText(), managerUsernameField.getText(), managerEmailField.getText()}))
        {
            NewHotelInformation.transferHotelInformation(new UserModel(managerEGNField.getText(), managerNameField.getText(), managerSurnameField.getText(), managerPhoneField.getText(), managerUsernameField.getText(), managerEmailField.getText(), managerPasswordField.getText(), Hasher.SHA512.hash(managerPasswordField.getText()), "мениджър", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), "потвърден", new ArrayList<>()),this);
            log.info("New data for manager added.");
            AlertManager.showAlert(Alert.AlertType.INFORMATION, "Информация", "✅ Успешно добавени данни за на нов мениджър.");
            ViewManager.closeDialogBox();
            ViewManager.changeView(Constants.View.OWNER_ADD_NEW_HOTEL_AND_NEW_MANAGER_VIEW, ViewManager.getPrimaryStage(), this.getClass(), "Owner Add New Hotel And New Manager", 800, 500);
        }
    }


    public void closePage() {
        ViewManager.closeDialogBox();
    }

    public void initialize()
    {
        if(NewHotelInformation.getHotelManagerInformation()!=null)
        {
            managerNameField.setText(NewHotelInformation.getHotelManagerInformation().getFirstName());
            managerSurnameField.setText(NewHotelInformation.getHotelManagerInformation().getLastName());
            managerEGNField.setText(NewHotelInformation.getHotelManagerInformation().getId());
            managerPhoneField.setText(NewHotelInformation.getHotelManagerInformation().getPhone());
            managerPasswordField.setText(NewHotelInformation.getHotelManagerInformation().getPassword());
            managerUsernameField.setText(NewHotelInformation.getHotelManagerInformation().getUsername());
            managerEmailField.setText(NewHotelInformation.getHotelManagerInformation().getEmail());
        }

        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                addHotelManagerButton.fire();
                keyEvent.consume();
            }
        });
    }
}