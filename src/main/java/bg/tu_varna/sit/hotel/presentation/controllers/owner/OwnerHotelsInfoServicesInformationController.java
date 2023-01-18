package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.RoomService;
import bg.tu_varna.sit.hotel.business.ServiceService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.RoomModel;
import bg.tu_varna.sit.hotel.presentation.models.ServiceModel;
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

public class OwnerHotelsInfoServicesInformationController {
    private static final Logger log = Logger.getLogger(OwnerHotelsInfoServicesInformationController.class);
    private final ServiceService serviceService = ServiceService.getInstance();
    private HotelModel selectedHotel;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<ServiceModel> serviceTable;
    @FXML
    private TableColumn<ServiceModel,String> typeColumn;
    @FXML
    private TableColumn<ServiceModel,String> seasonColumn;
    @FXML
    private TableColumn<ServiceModel, String> timesUsedColumn;
    @FXML
    private TableColumn deleteColumn;

    public void initialize() {

        serviceTable.getColumns().forEach(column -> column.setReorderable(false));//prevents custom reordering of columns in order to avoid icon bugs
        serviceTable.getColumns().forEach(column -> column.setSortable(false));//prevents custom sorting of columns in order to avoid icon bugs

        Label label = new Label("Няма информация за услуги.");
        label.setStyle("-fx-text-fill: black;" + "-fx-background-color: white;" + "-fx-font-size: 20;");
        serviceTable.setPlaceholder(label); //shows text when there are no services in the database

        //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/cell/PropertyValueFactory.html
        typeColumn.setCellValueFactory(new PropertyValueFactory<ServiceModel, String>("type"));
        seasonColumn.setCellValueFactory(new PropertyValueFactory<ServiceModel, String>("season"));
        timesUsedColumn.setCellValueFactory(new PropertyValueFactory<ServiceModel, String>("times_used"));

        typeColumn.setStyle("-fx-alignment:center");
        seasonColumn.setStyle("-fx-alignment:center");
        timesUsedColumn.setStyle("-fx-alignment:center");
        deleteColumn.setStyle("-fx-alignment:center");


        selectedHotel=UserSession.selectedHotel;
        UserSession.selectedHotel=null;
        OwnerAddServiceController.setSelectedHotel(selectedHotel,this);//set hotel name to an attribute(String) in case I decide to add new service using the OwnerAddServiceController (I will use the attribute(String) in the controller as hotel name in a query and eventually creating the new service)
        serviceTable.setItems(serviceService.getAllServicesOfHotel(selectedHotel));// Inserts all services of hotel in TableView
        createActionButtons();//insert dynamically created action buttons in every row of TableView

        Label hotelName = new Label("\""+selectedHotel.getName()+"\"");
        hotelName.setText("\""+selectedHotel.getName()+"\"");
        hotelName.setStyle("-fx-text-fill: white;" + "-fx-font-size: 20;" + "-fx-alignment: center;" + "-fx-font-family: Arial;");
        hotelName.setLayoutX(192);
        hotelName.setLayoutY(37);
        hotelName.setPrefWidth(364);
        hotelName.setPrefHeight(28);
        anchorPane.getChildren().add(hotelName);
    }

    private void createActionButtons() {
        Callback<TableColumn<ServiceModel, String>, TableCell<ServiceModel, String>> cellCallback =
                new Callback<>() {
                    @Override
                    public TableCell<ServiceModel, String> call(TableColumn<ServiceModel, String> param) {

                        TableCell<ServiceModel, String> cell = new TableCell<ServiceModel, String>() {

                            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);

                            public HBox hBox = new HBox(deleteIcon);

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
                                    deleteIcon.setStyle("-glyph-size:15px; -fx-font-family: FontAwesome;");

                                    deleteIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("изтрий");
                                        Tooltip.install(deleteIcon,tooltip);
                                    });

                                    deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                                        ServiceModel serviceModel = getTableView().getItems().get(getIndex());

                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setHeaderText("Потвърждение");
                                        alert.initStyle(StageStyle.UNDECORATED);
                                        alert.setContentText("Наистина ли искате да изтриете данните за услуга \""+serviceModel.getType()+"\"?");
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
                                                deleteRow(serviceModel);//deletes row from TableView
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
        deleteColumn.setCellFactory(cellCallback);
    }

    private void deleteRow(ServiceModel serviceModel) throws IOException {

        if(serviceService.deleteService(serviceModel))
        {
            log.info("Information for service has been successfully deleted.");
            AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Успешно изтрихте данни за услуга.");
            ViewManager.closeDialogBox();
            UserSession.selectedHotel=selectedHotel;
            ViewManager.openDialogBox(Constants.View.OWNER_HOTELS_INFO_SERVICES_INFORMATION_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Owner Hotels Info Services Information",750,450);
        }
        else
        {
            log.info("Information for service has NOT been deleted.");
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Изтриването на данни за услуга е неуспешно.");
        }
    }

    public void addNewService() throws IOException {
        ViewManager.openDialogBox(Constants.View.OWNER_ADD_SERVICE_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Owner Add Service",652,352);
    }

    public void closeWindow(){
        ViewManager.closeDialogBox();
    }
}