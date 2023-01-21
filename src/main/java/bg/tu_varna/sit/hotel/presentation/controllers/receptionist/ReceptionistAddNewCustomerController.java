package bg.tu_varna.sit.hotel.presentation.controllers.receptionist;

import bg.tu_varna.sit.hotel.business.CustomerService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.*;
import bg.tu_varna.sit.hotel.presentation.controllers.manager.ManagerAddNewReceptionistController;
import bg.tu_varna.sit.hotel.presentation.models.CustomerModel;
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

public class ReceptionistAddNewCustomerController {
    private static final Logger log = Logger.getLogger(ReceptionistAddNewCustomerController.class);
    private final CustomerService customerService = CustomerService.getInstance();
    private final UserService userService = UserService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerSurnameField;
    @FXML
    private TextField customerEGNField;
    @FXML
    private TextField customerPhoneField;
    @FXML
    private Button addCustomerButton;



    public void showReceptionistMainView() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Main", 800, 500);
    }

    public void addNewReservation() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_ADD_NEW_RESERVATION_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Add New Reservation",800,500);
    }

    public void addNewService() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_ADD_NEW_SERVICE_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Add New Service",800,500);
    }


    public void addNewCustomer() throws IOException {
        if (customerService.validateFields(new String[]{customerNameField.getText(), customerSurnameField.getText(), customerEGNField.getText(), customerPhoneField.getText()})
                && !customerService.checkForExistingUserData(new String[]{customerEGNField.getText(), customerPhoneField.getText()},userService.getUserById(UserSession.user.getId()).getHotels().get(0).toModel())) //receptionist works only for 1 hotel so from his hotel list I get the hotel at index 0
        {
            if (customerService.addCustomer(new CustomerModel(1L, customerEGNField.getText(), customerNameField.getText(), customerSurnameField.getText(), customerPhoneField.getText(),new Timestamp(System.currentTimeMillis()),"неопределен",userService.getUserById(UserSession.user.getId()).getHotels().get(0).toModel(),0)))
            {
                log.info("New customer has been added successfully.");
                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешно добавяне на клиент "+customerNameField.getText()+" "+customerSurnameField.getText()+" към хотел \""+userService.getUserById(UserSession.user.getId()).getHotels().get(0).getName()+"\".");
                ViewManager.closeDialogBox();
                ViewManager.changeView(Constants.View.RECEPTIONIST_ADD_NEW_CUSTOMER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Add New Customer", 800, 500);
            }
            else
            {
                log.error("Customer has not been added successfully.");
                AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Неуспешно добавяне на нов клиент.");
            }
        }
    }


    public void logout() throws IOException {
        ViewManager.closeDialogBox();
        if(UserSession.user!=null)
        {
            log.info("Receptionist \""+UserSession.user.getUsername()+"\" successfully logged out.");
            UserSession.user=null;//pri logout dannite za nastoqshta user sesiq se iztrivat, za da ne sa nali4ni otvun
        }
        ViewManager.changeView(Constants.View.RECEPTIONIST_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Login", 800, 500);
    }


    public void showAccountInformation() throws IOException {
        if(UserSession.user==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Няма заредени данни за рецепционист.");
        }
        else
        {
            ViewManager.closeDialogBox();
            ViewManager.openDialogBox(Constants.View.RECEPTIONIST_INFO_VIEW, null,this.getClass(),"Receptionist Info", 652, 352);
        }
    }


    public void initialize()
    {
        if(UserSession.user==null)
        {
            customerNameField.setDisable(true);
            customerSurnameField.setDisable(true);
            customerEGNField.setDisable(true);
            customerPhoneField.setDisable(true);
            addCustomerButton.setDisable(true);
        }

        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                addCustomerButton.fire();
                keyEvent.consume();
            }
        });
    }
}