package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.*;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.Reservation;
import bg.tu_varna.sit.hotel.presentation.controllers.owner.cache.MajorOwnerController;
import bg.tu_varna.sit.hotel.presentation.controllers.owner.cache.NewHotelInformation;
import bg.tu_varna.sit.hotel.presentation.models.CustomerModel;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.RoomModel;
import bg.tu_varna.sit.hotel.presentation.models.custom.CustomerRowModel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class OwnerCustomersController implements MajorOwnerController {
    private static final Logger log = Logger.getLogger(OwnerCustomersController.class);
    private final UserService userService = UserService.getInstance();
    private final ServiceService serviceService = ServiceService.getInstance();
    private final RoomService roomService = RoomService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();
    private final HotelService hotelService = HotelService.getInstance();
    private final CustomerService customerService = CustomerService.getInstance();
    private HotelModel hotelModel;
    private List<Hotel> hotelsOfOwner;
    private ObservableList<CustomerModel> allCustomers;

    @FXML
    private TableView<CustomerRowModel> customersTable;
    @FXML
    private TableColumn<CustomerRowModel,String> egnColumn;
    @FXML
    private TableColumn<CustomerRowModel, String> nameColumn;
    @FXML
    private TableColumn<CustomerRowModel,String> surnameColumn;
    @FXML
    private TableColumn<CustomerRowModel,String> phoneColumn;
    @FXML
    private TableColumn<CustomerRowModel,String> ratingColumn;
    @FXML
    private TableColumn actionColumn;

    @FXML
    private ComboBox<String> hotelsComboBox;
    @FXML
    private Button customersQueryButton;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Label timeLabel;

    public void showOwnerMainView() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Main", 800, 500);
    }

    public void addHotelAndManager() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_ADD_HOTEL_AND_MANAGER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Add Hotel And Manager", 800, 500);
    }

    public void showHotelsInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_HOTELS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Hotels Info", 800, 500);
    }

    public void showReceptionistsReservations() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_RECEPTIONISTS_RESERVATIONS_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Receptionists Reservations Info", 800, 500);
    }

    public void showRegistrationsInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_REGISTRATIONS_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Registrations Info", 800, 500);
    }

    public void showRoomRatings() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_ROOMS_RATINGS_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Room Ratings", 800, 500);
    }

    public void logout() throws IOException {
        ViewManager.closeDialogBox();
        if(UserSession.user!=null)
        {
            log.info("Owner \""+UserSession.user.getUsername()+"\" successfully logged out.");
            UserSession.user=null;//pri logout dannite za nastoqshta user sesiq se iztrivat, za da ne sa nali4ni otvun
        }
        ViewManager.changeView(Constants.View.OWNER_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Login", 800, 500);
    }


    public void showAccountInformation() throws IOException {
        if(UserSession.user==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Няма заредени данни за собственик.");
        }
        else
        {
            ViewManager.closeDialogBox();
            ViewManager.openDialogBox(Constants.View.OWNER_INFO_VIEW, null,this.getClass(),"Owner Info", 652, 352);
        }
    }



    public void initialize()
    {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        };
        timer.start();

        if(UserSession.user!=null)
        {
            NewHotelInformation.makeRefreshedFalse(this);

            hotelsOfOwner = userService.getUserById(UserSession.user.getId()).getHotels();


            if(hotelsOfOwner!=null && hotelsOfOwner.size()>0)
            {
                hotelsComboBox.getItems().addAll(hotelsOfOwner.stream().map(Hotel::getName).collect(Collectors.toList()));
            }
            else
            {
                hotelsComboBox.setDisable(true);
            }



            hotelsComboBox.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                    if(customersTable.getItems()!=null)
                    {
                        customersTable.getItems().clear();
                    }
                    customersQueryButton.setDisable(startDatePicker.getValue() == null || endDatePicker.getValue() == null);
                }
            });

            customersTable.getColumns().forEach(column -> column.setReorderable(false));//prevents custom reordering of columns in order to avoid icon bugs
            customersTable.getColumns().forEach(column -> column.setSortable(false));//prevents custom sorting of columns in order to avoid icon bugs

            Label label = new Label("Няма информация за клиенти.");
            label.setStyle("-fx-text-fill: black;"+"-fx-background-color: white;"+"-fx-font-size: 20;");
            customersTable.setPlaceholder(label); //shows text when there are no customer in the database

            egnColumn.setCellValueFactory(new PropertyValueFactory<CustomerRowModel,String>("egn"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<CustomerRowModel,String>("firstName"));
            surnameColumn.setCellValueFactory(new PropertyValueFactory<CustomerRowModel,String>("lastName"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<CustomerRowModel,String>("phone"));
            ratingColumn.setCellValueFactory(new PropertyValueFactory<CustomerRowModel,String>("rating"));

            egnColumn.setStyle("-fx-alignment:center");
            nameColumn.setStyle("-fx-alignment:center");
            surnameColumn.setStyle("-fx-alignment:center");
            phoneColumn.setStyle("-fx-alignment:center");
            actionColumn.setStyle("-fx-alignment:center");

            customersQueryButton.setDisable(startDatePicker.getValue() == null || endDatePicker.getValue() == null);

            //allCustomers = customerService.getAllCustomersOfHotel(hotelModel);
        }
    }


    private void createActionButtons() {
        Callback<TableColumn<CustomerRowModel, String>, TableCell<CustomerRowModel, String>> cellCallback =
                new Callback<>() {
                    @Override
                    public TableCell<CustomerRowModel, String> call(TableColumn<CustomerRowModel, String> param) {

                        TableCell<CustomerRowModel, String> cell = new TableCell<CustomerRowModel, String>() {

                            FontAwesomeIconView serviceIcon = new FontAwesomeIconView(FontAwesomeIcon.FILE_TEXT);

                            public HBox hBox = new HBox(serviceIcon);

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
                                    serviceIcon.setStyle("-glyph-size:15px; -fx-font-family: FontAwesome;");


                                    serviceIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("списък с използвани услуги");
                                        Tooltip.install(serviceIcon,tooltip);
                                    });

                                    serviceIcon.setOnMouseClicked((MouseEvent event) -> {

                                        CustomerRowModel customerRowModel = getTableView().getItems().get(getIndex());
                                        try
                                        {
                                            showServicesInformation(customerRowModel);//shows information for customers's used services
                                        }
                                        catch (IOException e)
                                        {
                                            e.printStackTrace();
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


    private void showServicesInformation(CustomerRowModel customerRowModel) throws IOException {

        UserSession.selectedCustomerRowModel=customerRowModel;
        ViewManager.openDialogBox(Constants.View.OWNER_SERVICES_USED_INFO,ViewManager.getSecondaryStage(),this.getClass(),"Owner Services Used",750,450);
    }


    public void showCustomersQuery() {

        if (validateDates() && validateChosenHotel())
        {
            hotelModel = hotelService.getHotelByName(hotelsComboBox.getValue());
            allCustomers = customerService.getAllCustomersOfHotel(hotelModel);


            if(allCustomers!=null && allCustomers.size()>0)
            {
                String startDateHour = startDatePicker.getValue() + " 00:00:00.000000000";
                Timestamp startDate = Timestamp.valueOf(startDateHour);

                String endDateHour = endDatePicker.getValue() + " 23:59:59.000000000";
                Timestamp endDate = Timestamp.valueOf(endDateHour);

                ObservableList<CustomerRowModel> customerRowModels = FXCollections.observableArrayList();


                for(CustomerModel customer : allCustomers)
                {
                    List<Reservation> reservationsOfCustomerForPeriod = reservationService.getReservationsOfCustomerForPeriod(hotelModel,startDate,endDate,customer.getId());

                    Set<String> serviceSet = new HashSet<>();//set to store all used hotel services of customer

                    List<Reservation> finishedReservationsOfCustomer = new LinkedList<>();

                    if(reservationsOfCustomerForPeriod !=null)
                    {
                        for (Reservation reservation : reservationsOfCustomerForPeriod)
                        {
                            if (reservation.getCustomer().getId().equals(customer.getId()) && reservation.getStatus().equals("обработена"))
                            {
                                finishedReservationsOfCustomer.add(reservation);
                            }
                        }
                    }



                    if(finishedReservationsOfCustomer.size()>0)
                    {
                        for(Reservation reservation: finishedReservationsOfCustomer)
                        {
                            String[] services = new String[]{};
                            if(reservation.getServiceList().length()>2)
                            {
                                String string = reservation.getServiceList();
                                services = string.split(",");
                            }
                            serviceSet.addAll(Arrays.asList(services));
                        }
                    }


                    Map<Integer,RoomModel> reservedRooms = new HashMap();//list to store all rooms which have been reserved for specific period (start date - end date)



                    if(finishedReservationsOfCustomer.size()>0)
                    {
                        for(Reservation reservation : finishedReservationsOfCustomer)
                        {
                            RoomModel tempRoom = reservation.getRoom().toModel();

                            //1st case
                            if(reservation.getStartDate().before(startDate) && reservation.getEndDate().after(startDate) && reservation.getEndDate().before(endDate))
                            {
                                int daysAfterStartDate = (Days.daysBetween(new DateTime(startDate.getTime()),new DateTime(reservation.getEndDate().getTime())).getDays());
                                tempRoom.setNightsOccupied(daysAfterStartDate);
                            }
                            //2nd case
                            else if(reservation.getStartDate().after(startDate) && reservation.getStartDate().before(endDate) && reservation.getEndDate().after(startDate) && reservation.getEndDate().before(endDate))
                            {
                                tempRoom.setNightsOccupied(reservation.getNightsOccupied());
                            }
                            //3rd case
                            else if(reservation.getStartDate().after(startDate) && reservation.getStartDate().before(endDate) && reservation.getEndDate().after(startDate) && reservation.getEndDate().after(endDate))
                            {
                                int daysBeforeEndDate = (Days.daysBetween(new DateTime(reservation.getStartDate().getTime()),new DateTime(endDate.getTime())).getDays());
                                tempRoom.setNightsOccupied(daysBeforeEndDate);
                            }
                            //4th case
                            else if(reservation.getStartDate().before(startDate) && reservation.getEndDate().after(endDate))
                            {
                                int daysBetweenPeriod = (Days.daysBetween(new DateTime(startDate.getTime()),new DateTime(endDate.getTime())).getDays());
                                tempRoom.setNightsOccupied(daysBetweenPeriod);
                            }



                            if(!(reservation.getEndDate().before(startDate) || reservation.getStartDate().after(endDate)))
                            {

                                if(!reservedRooms.containsKey(tempRoom.getNumber()))
                                {
                                    reservedRooms.put(tempRoom.getNumber(),tempRoom);
                                }
                                else
                                {
                                    int oldNightsOccupied = reservedRooms.get(tempRoom.getNumber()).getNightsOccupied();
                                    reservedRooms.get(tempRoom.getNumber()).setNightsOccupied(oldNightsOccupied+tempRoom.getNightsOccupied());
                                }

                            }



                        }


                        int nightsStayed = 0;
                        for(RoomModel currRoom : reservedRooms.values())
                        {
                            nightsStayed+=currRoom.getNightsOccupied();
                        }

                        customer.setNightsStayed(nightsStayed);
                        customer.setRating(determineCustomerRating(customer.getNightsStayed()));
                    }




                    if(finishedReservationsOfCustomer.size()>0)
                    {
                        String[] usedServices = Arrays.copyOf(serviceSet.toArray(), serviceSet.size(), String[].class);
                        customerRowModels.add(customer.toReservationRowModel(usedServices));
                    }
                    else
                    {
                        String[] servicesArr = new String[]{};
                        CustomerRowModel customerRowModel = customer.toReservationRowModel(servicesArr);
                        customerRowModel.setRating("неопределен");
                        customerRowModels.add(customerRowModel);
                    }
                }



                if(customerRowModels!=null && customerRowModels.size()>0)
                {
                    customersTable.setItems(customerRowModels);
                    createActionButtons();
                }
            }

        }

    }

    private String determineCustomerRating(int nightsStayed){
        if(nightsStayed<0) {return "слаб";}
        else if(nightsStayed==0) {return "неопределен";}
        else if(nightsStayed>0 && nightsStayed<10) {return "нормален";}
        else if(nightsStayed>=10 && nightsStayed<30) {return "добър";}
        else if(nightsStayed>=30 && nightsStayed<50) {return "много добър";}
        else {return "отличен";} //50+ nightsStayed
    }




    private boolean validateChosenHotel(){
        if(hotelsComboBox.getValue()!=null)
        {
            return true;
        }
        else
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Моля изберете хотел.");
            return false;
        }
    }

    private boolean validateDates(){
        if(startDatePicker.getValue().equals(endDatePicker.getValue()))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Началната и крайната дата не могат да съвпадат.");
            return false;
        }
        else if(startDatePicker.getValue().compareTo(endDatePicker.getValue()) > 0)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Началната дата не може да е след крайната дата.");
            return false;
        }
        else {return true;}//everything is OK with the dates
    }

    public void startDatePickerChanged()
    {
        if(customersTable.getItems()!=null)
        {
            customersTable.getItems().clear();
        }
        if(hotelsComboBox.getValue()!=null)
        {
            hotelsComboBox.setValue(null);
        }
        customersQueryButton.setDisable(startDatePicker.getValue() == null || endDatePicker.getValue() == null);
    }
    public void endDatePickerChanged()
    {
        if(customersTable.getItems()!=null)
        {
            customersTable.getItems().clear();
        }
        if(hotelsComboBox.getValue()!=null)
        {
            hotelsComboBox.setValue(null);
        }
        customersQueryButton.setDisable(startDatePicker.getValue() == null || endDatePicker.getValue() == null);
    }



}