package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.RoomService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.RoomModel;
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

public class OwnerHotelsInfoRoomsInformationController {
    private static final Logger log = Logger.getLogger(OwnerHotelsInfoRoomsInformationController.class);
    private final RoomService roomService = RoomService.getInstance();
    private HotelModel selectedHotel;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<RoomModel> roomsTable;
    @FXML
    private TableColumn<RoomModel,String> numberColumn;
    @FXML
    private TableColumn<RoomModel,String> typeColumn;
    @FXML
    private TableColumn<RoomModel, String> areaColumn;
    @FXML
    private TableColumn<RoomModel,String> priceColumn;
    @FXML
    private TableColumn<RoomModel, String> ratingColumn;
    @FXML
    private TableColumn actionColumn;

    public void initialize() {

            roomsTable.getColumns().forEach(column -> column.setReorderable(false));//prevents custom reordering of columns in order to avoid icon bugs
            roomsTable.getColumns().forEach(column -> column.setSortable(false));//prevents custom sorting of columns in order to avoid icon bugs

            Label label = new Label("Няма информация за стаи.");
            label.setStyle("-fx-text-fill: black;" + "-fx-background-color: white;" + "-fx-font-size: 20;");
            roomsTable.setPlaceholder(label); //shows text when there are no rooms in the database

            //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/cell/PropertyValueFactory.html
            numberColumn.setCellValueFactory(new PropertyValueFactory<RoomModel, String>("number"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<RoomModel, String>("type"));
            areaColumn.setCellValueFactory(new PropertyValueFactory<RoomModel, String>("size"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<RoomModel, String>("price"));
            ratingColumn.setCellValueFactory(new PropertyValueFactory<RoomModel, String>("rating"));


            numberColumn.setStyle("-fx-alignment:center");
            typeColumn.setStyle("-fx-alignment:center");
            areaColumn.setStyle("-fx-alignment:center");
            priceColumn.setStyle("-fx-alignment:center");
            ratingColumn.setStyle("-fx-alignment:center");
            actionColumn.setStyle("-fx-alignment:center");


            selectedHotel=UserSession.selectedHotel;
            UserSession.selectedHotel=null;
            OwnerRoomEditInfoController.setSelectedHotel(selectedHotel,this);//set hotel in case I decide to edit room information using the OwnerRoomEditInfoController (I will use the hotel) in the controller as using its hotel name in a query
            OwnerAddRoomController.setSelectedHotel(selectedHotel,this);//set hotel in case I decide to add new room  using the OwnerAddRoomController (I will use the hotel in the controller as take its hotel name in a query and eventually creating the new room)
            roomsTable.setItems(roomService.getAllHotelRooms(selectedHotel));// Inserts all rooms of hotel in TableView
            createActionButtons();//insert dynamically created action buttons in every row of TableView

            Label hotelName = new Label("\""+selectedHotel.getName()+"\"");
            hotelName.setText("\""+selectedHotel.getName()+"\"");
            hotelName.setStyle("-fx-text-fill: white;" + "-fx-font-size: 20;" + "-fx-alignment: center;" + "-fx-font-family: Arial;");
            hotelName.setLayoutX(192);
            hotelName.setLayoutY(33);
            hotelName.setPrefWidth(364);
            hotelName.setPrefHeight(28);
            anchorPane.getChildren().add(hotelName);
    }

    private void createActionButtons() {
        Callback<TableColumn<RoomModel, String>, TableCell<RoomModel, String>> cellCallback =
                new Callback<>() {
                    @Override
                    public TableCell<RoomModel, String> call(TableColumn<RoomModel, String> param) {

                        TableCell<RoomModel, String> cell = new TableCell<RoomModel, String>() {

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
                                    editIcon.setStyle("-glyph-size:15px; -fx-font-family: FontAwesome;");
                                    deleteIcon.setStyle("-glyph-size:15px; -fx-font-family: FontAwesome;");


                                    editIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("редактирай");
                                        Tooltip.install(editIcon,tooltip);
                                    });

                                    editIcon.setOnMouseClicked((MouseEvent event) -> {

                                        RoomModel roomModel = getTableView().getItems().get(getIndex());
                                        try
                                        {
                                            editRow(roomModel);//edits row in TableView
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

                                        RoomModel roomModel = getTableView().getItems().get(getIndex());

                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setHeaderText("Потвърждение");
                                        alert.initStyle(StageStyle.UNDECORATED);
                                        alert.setContentText("Наистина ли искате да изтриете данните за стая "+roomModel.getNumber()+"?");
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
                                                deleteRow(roomModel);//deletes row from TableView
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

    private void editRow(RoomModel roomModel) throws IOException {
        UserSession.selectedRoom=roomModel;
        ViewManager.openDialogBox(Constants.View.OWNER_ROOM_EDIT_INFO_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Owner Room Edit Info",652,352);
    }

    private void deleteRow(RoomModel roomModel) throws IOException {

        if(roomService.deleteRoom(roomModel))
        {
            log.info("Information for room has been successfully deleted.");
            AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Успешно изтрихте данни за стая.");
            ViewManager.closeDialogBox();
            ViewManager.changeView(Constants.View.OWNER_HOTELS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Hotels Info", 800, 500);
        }
        else
        {
            log.info("Information for room has NOT been deleted.");
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Изтриването на данни за стая е неуспешно.");
        }
    }

    public void addNewRoom() throws IOException {
        ViewManager.openDialogBox(Constants.View.OWNER_ADD_ROOM_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Owner Add Room",652,352);
    }

    public void closeWindow(){
        ViewManager.closeDialogBox();
    }
}