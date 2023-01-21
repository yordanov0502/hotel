package bg.tu_varna.sit.hotel.presentation.controllers.receptionist;

import bg.tu_varna.sit.hotel.business.CustomerService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.*;
import bg.tu_varna.sit.hotel.presentation.controllers.admin.UserEditInfoController;
import bg.tu_varna.sit.hotel.presentation.models.CustomerModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Timestamp;

public class CustomerEditInfoController {
    private static final Logger log = Logger.getLogger(CustomerEditInfoController.class);
    private final CustomerService customerService = CustomerService.getInstance();
    private final UserService userService = UserService.getInstance();
    private CustomerModel selectedCustomer;

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
    private TextField customerRatingField;
    @FXML
    private Button customerEditInfoButton;
    @FXML
    private Button closeButton;


    public void editCustomerInfo() throws IOException {

        if (customerService.validateFields(new String[]{customerNameField.getText(), customerSurnameField.getText(), customerEGNField.getText(), customerPhoneField.getText()})
                && customerService.checkForCorrectCustomerDataUpdate(new String[]{customerNameField.getText(),customerSurnameField.getText(),customerEGNField.getText(), customerPhoneField.getText()},selectedCustomer))
        {
            if (customerService.updateCustomer(new CustomerModel(selectedCustomer.getId(), customerEGNField.getText(), customerNameField.getText(), customerSurnameField.getText(), customerPhoneField.getText(),selectedCustomer.getCreatedAt(),selectedCustomer.getRating(), selectedCustomer.getHotel(), selectedCustomer.getNightsStayed())))
            {
                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешно редактиране на данните за клиент "+customerNameField.getText()+" "+customerSurnameField.getText()+".");
                ViewManager.closeDialogBox();
                ViewManager.changeView(Constants.View.RECEPTIONIST_ADD_NEW_RESERVATION_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Add New Reservation",800,500);
            }
            else
            {
                AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Редактирането на данни е неуспешно.");
            }
        }
    }


    public void closeEditInfoPage(){
        ViewManager.closeDialogBox();
    }

    public void showCustomerInfo(){
        customerNameField.setText(selectedCustomer.getFirstName());
        customerSurnameField.setText(selectedCustomer.getLastName());
        customerEGNField.setText(selectedCustomer.getEgn());
        customerPhoneField.setText(selectedCustomer.getPhone());
        customerRatingField.setText(selectedCustomer.getRating());
    }

    public void initialize()
    {
        selectedCustomer= UserSession.selectedCustomer;
        UserSession.selectedCustomer=null;
        showCustomerInfo();

        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                customerEditInfoButton.fire();
                keyEvent.consume();
            }
        });
    }

}