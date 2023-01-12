package bg.tu_varna.sit.hotel.presentation.controllers.admin;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

public class AdminHotelUsersInfoController {
    private static final Logger log = Logger.getLogger(AdminHotelUsersInfoController.class);
    private final HotelService hotelService = HotelService.getInstance();
    private final UserService userService = UserService.getInstance();
    private HotelModel selectedHotel;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<UserModel> hotelUsersTable;
    @FXML
    private TableColumn<UserModel,String> roleColumn;
    @FXML
    private TableColumn<UserModel,String> egnColumn;
    @FXML
    private TableColumn<UserModel, String> nameColumn;
    @FXML
    private TableColumn<UserModel,String> surnameColumn;
    @FXML
    private TableColumn<UserModel, String> phoneColumn;
    @FXML
    private TableColumn<UserModel,String> usernameColumn;
    @FXML
    private TableColumn<UserModel, String> emailColumn;
    @FXML
    private TableColumn<UserModel,String> statusColumn;
    @FXML
    private TableColumn actionColumn;
    @FXML
    private Button removeOwnerButton;

    public void initialize() {

        if(UserSession.user!=null)
        {
            hotelUsersTable.getColumns().forEach(column -> column.setReorderable(false));//prevents custom reordering of columns in order to avoid icon bugs
            hotelUsersTable.getColumns().forEach(column -> column.setSortable(false));//prevents custom sorting of columns in order to avoid icon bugs

            Label label = new Label("Няма информация за служители.");
            label.setStyle("-fx-text-fill: black;" + "-fx-background-color: white;" + "-fx-font-size: 20;");
            hotelUsersTable.setPlaceholder(label); //shows text when there are no owners in the database

            //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/cell/PropertyValueFactory.html
            roleColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("role"));
            egnColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("firstName"));
            surnameColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("lastName"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("phone"));
            usernameColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("username"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("email"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("status"));

            roleColumn.setStyle("-fx-alignment:center");
            egnColumn.setStyle("-fx-alignment:center");
            nameColumn.setStyle("-fx-alignment:center");
            surnameColumn.setStyle("-fx-alignment:center");
            phoneColumn.setStyle("-fx-alignment:center");
            usernameColumn.setStyle("-fx-alignment:center");
            emailColumn.setStyle("-fx-alignment:center");
            statusColumn.setStyle("-fx-alignment:center");
            actionColumn.setStyle("-fx-alignment:center");


            selectedHotel=UserSession.selectedHotel;
            UserSession.selectedHotel=null;
            hotelUsersTable.setItems(hotelService.getAllHotelUsers(selectedHotel));// Inserts all users of hotel in TableView
            createActionButtons();//insert dynamically created action buttons in every row of TableView

            Label hotelName = new Label("\""+selectedHotel.getName()+"\"");
            hotelName.setText("\""+selectedHotel.getName()+"\"");
            hotelName.setStyle("-fx-text-fill: white;" + "-fx-background-color: blue;" + "-fx-font-size: 20;" + "-fx-background-radius: 25;"+"-fx-alignment: center;"+"-fx-font-family: Arial;");
            hotelName.setLayoutX(192);
            hotelName.setLayoutY(45);
            hotelName.setPrefWidth(364);
            hotelName.setPrefHeight(28);
            anchorPane.getChildren().add(hotelName);

            if(!selectedHotel.getHasOwner()) {removeOwnerButton.setDisable(true);}
        }
    }

    private void createActionButtons() {
        Callback<TableColumn<UserModel, String>, TableCell<UserModel, String>> cellCallback =
                new Callback<>() {
                    @Override
                    public TableCell<UserModel, String> call(TableColumn<UserModel, String> param) {

                        TableCell<UserModel, String> cell = new TableCell<UserModel, String>() {

                            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                            FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);

                            public HBox hBox = new HBox(25, editIcon, deleteIcon);

                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty)
                                {
                                    setGraphic(null);
                                    setText(null);
                                }
                                else
                                {
                                    editIcon.setStyle("-glyph-size:15px;");
                                    deleteIcon.setStyle("-glyph-size:15px;");


                                    editIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("редактирай");
                                        Tooltip.install(editIcon,tooltip);
                                    });

                                    editIcon.setOnMouseClicked((MouseEvent event) -> {

                                        UserModel userModel = getTableView().getItems().get(getIndex());
                                        try
                                        {
                                            editRow(userModel);//edits row in TableView
                                        }
                                        catch (IOException e)
                                        {
                                            e.printStackTrace();
                                        }
                                    });



                                    deleteIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("изтрий");
                                        Tooltip.install(deleteIcon,tooltip);
                                    });

                                    deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                                        UserModel userModel = getTableView().getItems().get(getIndex());

                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setHeaderText("Потвърждение");
                                        alert.initStyle(StageStyle.UNDECORATED);
                                        alert.setContentText("Наистина ли искате да изтриете данните за "+userModel.getRole()+"\n \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" от системата ?");
                                        alert.setX(ViewManager.getPrimaryStage().getX()+220);
                                        alert.setY(ViewManager.getPrimaryStage().getY()+180);
                                        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                        ButtonType yesButton = new ButtonType("Да", ButtonBar.ButtonData.YES);
                                        ButtonType noButton = new ButtonType("Не", ButtonBar.ButtonData.NO);
                                        alert.getButtonTypes().setAll(yesButton, noButton);
                                        Optional<ButtonType> answer = alert.showAndWait();

                                        if(answer.isPresent() && answer.get()==yesButton)
                                        {
                                            try
                                            {
                                                deleteRow(userModel);//deletes row from TableView
                                            }
                                            catch (IOException e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }
                                    });


                                    hBox.setStyle("-fx-alignment:center");
                                    setGraphic(hBox);
                                }
                            }
                        };

                        return cell;
                    }
                };
        actionColumn.setCellFactory(cellCallback);
    }

    private void editRow(UserModel userModel) throws IOException {

        UserSession.selectedUser=userModel;
        ViewManager.openDialogBox(Constants.View.USER_EDIT_INFO_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"User Edit Info",652,352);
    }

    private void deleteRow(UserModel userModel) throws IOException {

        //this loop may have more than 1 iteration because the user can be owner, manager or receptionist
        if(!userModel.getHotels().isEmpty())
        {
            for (HotelModel h : userService.getAllHotelsOfUser(userModel))
            {
                userService.removeHotel(userModel, h, ViewManager.getPrimaryStage().getTitle());//removes user from certain hotel's list and vice versa, and updates hotel's hasOwner and hasManager attributes if the user is owner or manager of a hotel, but his account in the system remains
            }
        }
        if(userService.deleteUser(userModel))
        {
            log.info("Information for "+userModel.getRole()+" \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" has been successfully deleted.");
            AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Успешно изтрихте данни за "+userModel.getRole()+" \""+userModel.getFirstName()+" "+userModel.getLastName()+"\".");
            ViewManager.closeDialogBox();
            ViewManager.changeView(Constants.View.ADMIN_HOTELS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Owners Info", 800, 500);
        }
        else
        {
            log.info("Information for "+userModel.getRole()+" \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" has NOT been deleted.");
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Изтриването на данни е неуспешно.");
        }
    }


    public void removeOwner() throws IOException{

        if(selectedHotel.getHasOwner())
        {
            UserModel hotelOwner = hotelService.getHotelOwner(selectedHotel);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Потвърждение");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Наистина ли искате да премахнете "+hotelOwner.getRole()+" "+hotelOwner.getFirstName()+" "+hotelOwner.getLastName()+" на хотел \""+selectedHotel.getName()+"\"?");
            alert.setX(ViewManager.getPrimaryStage().getX()+220);
            alert.setY(ViewManager.getPrimaryStage().getY()+180);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            ButtonType yesButton = new ButtonType("Да", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("Не", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> answer = alert.showAndWait();

            if(answer.isPresent() && answer.get()==yesButton)
            {
                userService.removeHotel(hotelOwner,selectedHotel,ViewManager.getPrimaryStage().getTitle());//removes user(owner) from certain hotel's list and vice versa, but his account in the system remains,just in case(if) he is owner of other hotels
                ViewManager.closeDialogBox();
                ViewManager.changeView(Constants.View.ADMIN_HOTELS_INFO_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Admin Hotels Info",800,500);
            }
        }
    }


    public void closeWindow(){
        ViewManager.closeDialogBox();
    }

}