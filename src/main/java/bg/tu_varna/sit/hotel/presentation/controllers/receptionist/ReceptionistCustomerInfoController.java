package bg.tu_varna.sit.hotel.presentation.controllers.receptionist;

import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.models.CustomerModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ReceptionistCustomerInfoController {

    private CustomerModel selectedCustomer;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label egnLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label ratingLabel;


    public void closeInfoPage(){
        ViewManager.closeDialogBox();
    }

    public void initialize()
    {
        selectedCustomer= UserSession.selectedCustomer;
        UserSession.selectedCustomer=null;

        nameLabel.setText(selectedCustomer.getFirstName());
        surnameLabel.setText(selectedCustomer.getLastName());
        egnLabel.setText(selectedCustomer.getEgn());
        phoneLabel.setText(selectedCustomer.getPhone());
        ratingLabel.setText(selectedCustomer.getRating());
    }
}