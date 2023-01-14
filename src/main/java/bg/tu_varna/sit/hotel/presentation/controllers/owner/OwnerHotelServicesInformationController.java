package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.ServiceService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.controllers.admin.AdminRegistrationController;
import bg.tu_varna.sit.hotel.presentation.controllers.owner.cache.NewHotelInformation;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.ServiceModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OwnerHotelServicesInformationController {
    private static final Logger log = Logger.getLogger(OwnerHotelServicesInformationController.class);
    private final ServiceService serviceService = ServiceService.getInstance();

    @FXML
    private TextField serviceTextField;
    @FXML
    private TableView<ServiceModel> servicesTable;
    @FXML
    private TableColumn<ServiceModel,String> serviceColumn;
    @FXML
    private TableColumn<ServiceModel, String> seasonColumn;
    @FXML
    private TableColumn actionColumn;
    @FXML
    private ComboBox<String> seasonsComboBox;

    public void initialize(){

        servicesTable.getColumns().forEach(column -> column.setReorderable(false));//prevents custom reordering of columns in order to avoid icon bugs
        servicesTable.getColumns().forEach(column -> column.setSortable(false));//prevents custom sorting of columns in order to avoid icon bugs

        Label label = new Label("Все още няма добавени услуги.");
        label.setStyle("-fx-text-fill: black;" + "-fx-background-color: white;" + "-fx-font-size: 20;");
        servicesTable.setPlaceholder(label); //shows text when there are no owners in the database

        seasonsComboBox.setItems(FXCollections.observableArrayList("пролет", "лято", "есен", "зима" , "цяла година"));


        if(NewHotelInformation.getHotelServicesInformation()!=null)//ako ima cache na hotelski uslugi
        {
            //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/cell/PropertyValueFactory.html
            serviceColumn.setCellValueFactory(new PropertyValueFactory<ServiceModel,String>("type"));
            seasonColumn.setCellValueFactory(new PropertyValueFactory<ServiceModel,String>("season"));

            serviceColumn.setStyle("-fx-alignment:center");
            seasonColumn.setStyle("-fx-alignment:center");
            actionColumn.setStyle("-fx-alignment:center");

            servicesTable.setItems(FXCollections.observableList(NewHotelInformation.getHotelServicesInformation()));// Inserts all services in TableView
            createActionButtons();//insert dynamically created action buttons in every row of TableView
        }
    }

    public void addHotelServicesInformation() throws IOException {

      if(serviceService.validateServiceField(serviceTextField.getText()) && checkForSelectedSeason() && checkForExistingHotelService(serviceTextField.getText()))
      {
          if(NewHotelInformation.getHotelServicesInformation()==null)
          {
              List<ServiceModel> tempServicesList = new ArrayList<>();
              tempServicesList.add(new ServiceModel(1L,serviceTextField.getText(),seasonsComboBox.getValue(),0,new HotelModel()));
              NewHotelInformation.transferNewServicesInformation(tempServicesList,this);
              log.info("New data for service added.");
              AlertManager.showAlert(Alert.AlertType.INFORMATION, "Информация", "✅ Успешно добавени данни за хотелска услуга.");
              ViewManager.closeDialogBox();
              ViewManager.changeView(Constants.View.OWNER_ADD_NEW_HOTEL_AND_NEW_MANAGER_VIEW, ViewManager.getPrimaryStage(), this.getClass(), "Owner Add New Hotel And New Manager", 800, 500);
          }
          else
          {
              NewHotelInformation.getHotelServicesInformation().add(new ServiceModel(1L,serviceTextField.getText(),seasonsComboBox.getValue(),0,new HotelModel()));
              log.info("New data for service added.");
              AlertManager.showAlert(Alert.AlertType.INFORMATION, "Информация", "✅ Успешно добавени данни за хотелска услуга.");
              ViewManager.closeDialogBox();
              ViewManager.changeView(Constants.View.OWNER_ADD_NEW_HOTEL_AND_NEW_MANAGER_VIEW, ViewManager.getPrimaryStage(), this.getClass(), "Owner Add New Hotel And New Manager", 800, 500);
          }
      }
    }

    public void closePage(){ViewManager.closeDialogBox();}

    //https://github.com/Rakib-Hasan-455/Hotel-Management-System-JavaFx/blob/master/src/sample/zadmin/AdminPages/AdminEmployeeInfo.java
    private void createActionButtons() {
        Callback<TableColumn<ServiceModel, String>, TableCell<ServiceModel, String>> cellCallback =
                new Callback<>() {
                    @Override
                    public TableCell<ServiceModel, String> call(TableColumn<ServiceModel, String> param) {

                        TableCell<ServiceModel, String> cell = new TableCell<ServiceModel, String>() {

                            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);

                            public HBox hBox = new HBox(25, deleteIcon);

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
                                    deleteIcon.setStyle("-glyph-size:15px;");

                                    deleteIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("изтрий");
                                        Tooltip.install(deleteIcon,tooltip);
                                    });

                                    deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                                        ServiceModel serviceModel = getTableView().getItems().get(getIndex());

                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setHeaderText("Потвърждение");
                                        alert.initStyle(StageStyle.UNDECORATED);
                                        alert.setContentText("Наистина ли искате да изтриете хотелска услуга \""+serviceModel.getType()+"\"?");
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
                                                log.info("Successfully removed service from serviceList(cached)");
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

    private void deleteRow(ServiceModel serviceModel) throws IOException {

        NewHotelInformation.getHotelServicesInformation().removeIf(service -> service.getType().equals(serviceModel.getType()));

        if(NewHotelInformation.getHotelServicesInformation().isEmpty())
        {
            NewHotelInformation.transferNewServicesInformation(null,this);
            ViewManager.closeDialogBox();
            ViewManager.changeView(Constants.View.OWNER_ADD_NEW_HOTEL_AND_NEW_MANAGER_VIEW, ViewManager.getPrimaryStage(), this.getClass(), "Owner Add New Hotel And New Manager", 800, 500);
        }
        else
        {
            ViewManager.closeDialogBox();
        }
    }

    private boolean checkForExistingHotelService(String serviceName){
        if(NewHotelInformation.getHotelServicesInformation() != null)
        {
            for(ServiceModel service : NewHotelInformation.getHotelServicesInformation())
            {
                if(service.getType().equals(serviceName))
                {
                    AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Хотелска услуга \""+serviceName+"\" вече съществува.");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkForSelectedSeason(){
        if(seasonsComboBox.getValue()!=null)
        {
            return true;
        }
        else
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Моля изберете сезон за хотелската услуга.");
            return false;
        }
    }

}