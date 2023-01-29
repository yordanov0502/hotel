package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.ServiceService;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.controllers.manager.ManagerServicesUsedController;
import bg.tu_varna.sit.hotel.presentation.models.ServiceModel;
import bg.tu_varna.sit.hotel.presentation.models.custom.CustomerRowModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class OwnerServicesUsedController {
    private static final Logger log = Logger.getLogger(OwnerServicesUsedController.class);
    private final ServiceService serviceService = ServiceService.getInstance();
    private CustomerRowModel customerRowModel;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<ServiceModel> serviceTable;
    @FXML
    private TableColumn<ServiceModel,String> typeColumn;


    public void initialize() {

        customerRowModel = UserSession.selectedCustomerRowModel;
        UserSession.selectedCustomerRowModel = null;

        List<ServiceModel> allUsedServices = new LinkedList<>();
        for(String name: customerRowModel.getUsedServicesList())
        {
            allUsedServices.add(serviceService.getServiceByType(name,customerRowModel.getHotel()));
        }

        serviceTable.getColumns().forEach(column -> column.setReorderable(false));//prevents custom reordering of columns in order to avoid icon bugs
        serviceTable.getColumns().forEach(column -> column.setSortable(false));//prevents custom sorting of columns in order to avoid icon bugs

        Label label = new Label("Няма информация за услуги.");
        label.setStyle("-fx-text-fill: black;" + "-fx-background-color: white;" + "-fx-font-size: 20;");
        serviceTable.setPlaceholder(label); //shows text when there are no services in the database

        //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/cell/PropertyValueFactory.html
        typeColumn.setCellValueFactory(new PropertyValueFactory<ServiceModel, String>("type"));

        typeColumn.setStyle("-fx-alignment:center");


        if(allUsedServices.size()>0)
        {
            serviceTable.setItems(FXCollections.observableList(allUsedServices));// Inserts all services of hotel in TableView
        }
    }

    public void closeWindow(){
        ViewManager.closeDialogBox();
    }
}