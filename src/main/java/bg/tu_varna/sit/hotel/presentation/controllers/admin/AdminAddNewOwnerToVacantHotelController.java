package bg.tu_varna.sit.hotel.presentation.controllers.admin;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.*;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class AdminAddNewOwnerToVacantHotelController {
    private static final Logger log = Logger.getLogger(AdminMainController.class);
    private final UserService userService = UserService.getInstance();
    private final HotelService hotelService = HotelService.getInstance();

    @FXML
    public AnchorPane anchorPane;
    @FXML
    public TextField ownerNameField;
    @FXML
    public TextField ownerSurnameField;
    @FXML
    public TextField ownerEGNField;
    @FXML
    public TextField ownerPhoneField;
    @FXML
    public TextField ownerUsernameField;
    @FXML
    public TextField ownerEmailField;
    @FXML
    public PasswordField ownerPasswordField;
    @FXML
    public Button addNewOwnerButton;
    @FXML
    public ComboBox comboBox;

    @FXML
    public void showAdminMainView() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Main", 800, 500);
    }

    @FXML
    public void addOwner() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_ADD_OWNER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Add Owner", 800, 500);
    }

    @FXML
    public void showOwnersInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_OWNERS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Owners Info", 800, 500);
    }

    @FXML
    public void showManagersInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_MANAGERS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Managers Info", 800, 500);
    }

    @FXML
    public void showReceptionistsInfo() throws IOException{
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_RECEPTIONISTS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Receptionists Info", 800, 500);
    }

    @FXML
    public void showHotelsInfo() throws IOException{
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_HOTELS_INFO_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Admin Hotels Info",800,500);
    }

    @FXML
    public void showNewlyRegisteredAdmins() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMINS_NEW_REGISTRATIONS_INFO, ViewManager.getPrimaryStage(),this.getClass(),"Admins New Registrations Info", 800, 500);
    }

    @FXML
    public void addNewOwnerToVacantHotel() throws IOException {

            if (userService.validateFields(new String[]{ownerNameField.getText(), ownerSurnameField.getText(), ownerEGNField.getText(), ownerPhoneField.getText(), ownerUsernameField.getText(), ownerEmailField.getText(), ownerPasswordField.getText()})
                    && !userService.checkForExistingData(new String[]{ownerEGNField.getText(), ownerPhoneField.getText(), ownerUsernameField.getText(), ownerEmailField.getText()}))
            {
                if(comboBox.getValue()!=null)
                {
                    UserModel userModel = new UserModel(ownerEGNField.getText(), ownerNameField.getText(), ownerSurnameField.getText(), ownerPhoneField.getText(), ownerUsernameField.getText(), ownerEmailField.getText(), ownerPasswordField.getText(), Hasher.SHA512.hash(ownerPasswordField.getText()), "собственик", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), "потвърден", new ArrayList<>());
                    if (userService.addUser(userModel))
                    {
                        log.info("New owner has been added successfully.");
                        if(userService.addHotel(userModel,hotelService.getHotelByName(comboBox.getValue().toString())))
                        {
                            if(hotelService.getAllHotelsWithoutOwner()!=null)
                            {
                                ViewManager.closeDialogBox();
                                ViewManager.changeView(Constants.View.ADMIN_ADD_NEW_OWNER_TO_VACANT_HOTEL_VIEW, ViewManager.getPrimaryStage(), this.getClass(), "Admin Add New Owner To Vacant Hotel", 800, 500);
                            }
                            else
                            {
                                ViewManager.closeDialogBox();
                                ViewManager.changeView(Constants.View.ADMIN_ADD_OWNER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Add Owner", 800, 500);
                            }
                        }
                        else
                        {
                            userService.deleteUser(userModel);
                            ViewManager.closeDialogBox();
                            ViewManager.changeView(Constants.View.ADMIN_ADD_NEW_OWNER_TO_VACANT_HOTEL_VIEW, ViewManager.getPrimaryStage(), this.getClass(), "Admin Add New Owner To Vacant Hotel", 800, 500);
                            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Неуспешно добавяне на нов собственик към хотел \""+hotelService.getHotelByName(comboBox.getValue().toString()).getName()+"\".");
                        }
                    }
                    else
                    {
                        log.error("Owner has not been added successfully.");
                        AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Неуспешно добавяне на нов собственик.");
                        ViewManager.closeDialogBox();
                        ViewManager.changeView(Constants.View.ADMIN_ADD_NEW_OWNER_TO_VACANT_HOTEL_VIEW, ViewManager.getPrimaryStage(), this.getClass(), "Admin Add New Owner To Vacant Hotel", 800, 500);
                    }
                }
                else
                {
                    AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Моля изберете хотел.");
                }
            }
    }

    @FXML
    public void backToAdminAddOwner() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_ADD_OWNER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Add Owner", 800, 500);
    }

    @FXML
    public void logout() throws IOException {
        ViewManager.closeDialogBox();
        if(UserSession.user!=null)
        {
            log.info("Admin \""+UserSession.user.getUsername()+"\" successfully logged out.");
            UserSession.user=null;//pri logout dannite za nastoqshta user sesiq se iztrivat, za da ne sa nali4ni otvun
        }
        ViewManager.changeView(Constants.View.ADMIN_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Login", 800, 500);
    }

    @FXML
    public void showAccountInformation() throws IOException {
        if(UserSession.user==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Няма заредени данни за администратор.");
        }
        else
        {
            ViewManager.closeDialogBox();
            ViewManager.openDialogBox(Constants.View.ADMIN_INFO_VIEW, null,this.getClass(),"Admin Info", 652, 352);
        }
    }

    public void initialize()
    {
        comboBox.setItems(hotelService.getAllHotelNamesWithoutOwner());

        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                addNewOwnerButton.fire();
                keyEvent.consume();
            }
        });
    }
}