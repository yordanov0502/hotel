package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.RoomService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.controllers.owner.cache.NewHotelInformation;
import bg.tu_varna.sit.hotel.presentation.controllers.owner.cache.RoomsInformation;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.io.IOException;
import java.util.*;

public class OwnerHotelRoomsInformationController {
    private static final Logger log = Logger.getLogger(OwnerHotelRoomsInformationController.class);
    private final RoomService roomService = RoomService.getInstance();

    @FXML
    private Pane grayPane;

    @FXML
    private TextField floorsField;

    @FXML
    private Button enterHotelFloorsButton;

    @FXML
    private Button addHotelRoomsInformationButton;

    @FXML
    private Button removeHotelRoomsInformationButton;

    @FXML
    private Label noTabPaneLabel;

    @FXML
    private Label infoLabel1;
    @FXML
    private Label infoLabel2;
    @FXML
    private Label infoLabel3;

    @FXML
    private CheckBox singleCheckBox;
    @FXML
    private CheckBox doubleCheckBox;
    @FXML
    private CheckBox tripleCheckBox;
    @FXML
    private CheckBox quadCheckBox;
    @FXML
    private CheckBox studioCheckBox;
    @FXML
    private CheckBox mezonetCheckBox;
    @FXML
    private CheckBox apartmentCheckBox;

    @FXML
    private TextField singleAreaField;
    @FXML
    private TextField doubleAreaField;
    @FXML
    private TextField tripleAreaField;
    @FXML
    private TextField quadAreaField;
    @FXML
    private TextField studioAreaField;
    @FXML
    private TextField mezonetAreaField;
    @FXML
    private TextField apartmentAreaField;

    @FXML
    private TextField singlePriceField;
    @FXML
    private TextField doublePriceField;
    @FXML
    private TextField triplePriceField;
    @FXML
    private TextField quadPriceField;
    @FXML
    private TextField studioPriceField;
    @FXML
    private TextField mezonetPriceField;
    @FXML
    private TextField apartmentPriceField;

    @FXML
    private TextField singleBedsField;
    @FXML
    private TextField doubleBedsField;
    @FXML
    private TextField tripleBedsField;
    @FXML
    private TextField quadBedsField;
    @FXML
    private TextField studioBedsField;
    @FXML
    private TextField mezonetBedsField;
    @FXML
    private TextField apartmentBedsField;

    private TabPane tabPane = new TabPane();

    public void generateFloorsSpecifications(){

        if(roomService.validateRoomsInformationFields(floorsField.getText(),Arrays.asList(singleCheckBox,doubleCheckBox,tripleCheckBox,quadCheckBox,studioCheckBox,mezonetCheckBox,apartmentCheckBox),Arrays.asList(singleAreaField,doubleAreaField,tripleAreaField,quadAreaField,studioAreaField,mezonetAreaField,apartmentAreaField),Arrays.asList(singlePriceField,doublePriceField,triplePriceField,quadPriceField,studioPriceField,mezonetPriceField,apartmentPriceField),Arrays.asList(singleBedsField,doubleBedsField,tripleBedsField,quadBedsField,studioBedsField,mezonetBedsField,apartmentBedsField)))
        {
            //changes the color of the selected room type checkBoxes which are present in the dynamic tabs
            roomService.changePropertiesOfRightCheckBoxesAndFields(Arrays.asList(singleCheckBox,doubleCheckBox,tripleCheckBox,quadCheckBox,studioCheckBox,mezonetCheckBox,apartmentCheckBox),Arrays.asList(singleAreaField,doubleAreaField,tripleAreaField,quadAreaField,studioAreaField,mezonetAreaField,apartmentAreaField),Arrays.asList(singlePriceField,doublePriceField,triplePriceField,quadPriceField,studioPriceField,mezonetPriceField,apartmentPriceField),Arrays.asList(singleBedsField,doubleBedsField,tripleBedsField,quadBedsField,studioBedsField,mezonetBedsField,apartmentBedsField));

            grayPane.getChildren().remove(this.tabPane);//removes the previous tabPane from the grayPane(if there was a tabPane) so new tabPane can take its place.
            this.tabPane = new TabPane();



            this.tabPane.setLayoutX(6);
            this.tabPane.setLayoutY(101);
            this.tabPane.setPrefWidth(390);
            this.tabPane.setPrefHeight(259);

            //creates list filled only with the selected checkBoxes from the controller
            List<CheckBox> selectedRoomTypesCheckBoxesList = roomService.getSelectedRoomTypesCheckBoxes(Arrays.asList(singleCheckBox,doubleCheckBox,tripleCheckBox,quadCheckBox,studioCheckBox,mezonetCheckBox,apartmentCheckBox));

            for(int i=0;i<Integer.parseInt(floorsField.getText());i++)
            {
                Tab tab = new Tab();
                tab.setText("Етаж "+(i+1));
                tab.setClosable(false);

                Pane pane = new Pane();
                pane.setPrefWidth(200);
                pane.setPrefHeight(180);
                pane.setStyle("-fx-background-color:  rgba(41, 41, 41, 0.8)");

                int initialCheckBoxY=-16;
                if(selectedRoomTypesCheckBoxesList.size()==1)
                {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected(true);
                    checkBox.setText(selectedRoomTypesCheckBoxesList.get(0).getText());
                    pane.getChildren().add(checkBox);
                    pane.getChildren().get(0).setLayoutX(212);
                    pane.getChildren().get(0).setLayoutY(initialCheckBoxY+30);
                    pane.getChildren().get(0).setStyle("-fx-text-fill: White; -fx-font-size: 15; -fx-font-family: Arial");
                }
                else
                {
                    for(int j=0;j<selectedRoomTypesCheckBoxesList.size();j++)
                    {
                        CheckBox checkBox = new CheckBox();
                        checkBox.setText(selectedRoomTypesCheckBoxesList.get(j).getText());
                        pane.getChildren().add(checkBox);
                        pane.getChildren().get(j).setLayoutX(212);
                        pane.getChildren().get(j).setLayoutY(initialCheckBoxY+30);
                        pane.getChildren().get(j).setStyle("-fx-text-fill: White; -fx-font-size: 15; -fx-font-family: Arial");
                        initialCheckBoxY+=30;
                    }
                }


                //icon for rooms per floor
                //https://www.youtube.com/watch?v=7zXyqUMfmSE
                int iconIndex=selectedRoomTypesCheckBoxesList.size();
                FontAwesome.Glyph glyph = FontAwesome.Glyph.SORT_NUMERIC_ASC;
                Glyph graphic = Glyph.create("FontAwesome|"+glyph.name()).size(25).color(Color.web("#ffffff"));
                pane.getChildren().add(graphic);
                pane.getChildren().get(iconIndex).setLayoutX(10);
                pane.getChildren().get(iconIndex).setLayoutY(90);

                //textField for rooms per floor
                TextField textField = new TextField();
                textField.setPromptText("Брой стаи");
                textField.setPrefWidth(150);
                textField.setPrefHeight(31);
                pane.getChildren().add(textField);
                pane.getChildren().get(iconIndex+1).setStyle("-fx-background-color:  rgba(41, 41, 41, 0.8); -fx-background-radius: 15; -fx-text-fill: White;-fx-alignment: center;");
                pane.getChildren().get(iconIndex+1).setLayoutX(45);
                pane.getChildren().get(iconIndex+1).setLayoutY(90);

                tab.setContent(pane);
                this.tabPane.getTabs().add(tab);
            }
             noTabPaneLabel.setVisible(false);
             grayPane.getChildren().add(this.tabPane);
        }
    }

    public void addHotelRoomsInformation() throws IOException {

          if(roomService.validateRoomsInformationFields(floorsField.getText(),Arrays.asList(singleCheckBox,doubleCheckBox,tripleCheckBox,quadCheckBox,studioCheckBox,mezonetCheckBox,apartmentCheckBox),Arrays.asList(singleAreaField,doubleAreaField,tripleAreaField,quadAreaField,studioAreaField,mezonetAreaField,apartmentAreaField),Arrays.asList(singlePriceField,doublePriceField,triplePriceField,quadPriceField,studioPriceField,mezonetPriceField,apartmentPriceField),Arrays.asList(singleBedsField,doubleBedsField,tripleBedsField,quadBedsField,studioBedsField,mezonetBedsField,apartmentBedsField))
           && validateDynmaicTabsInformation())
        {
            NewHotelInformation.transferNewRoomsInformation(new RoomsInformation(this,this.tabPane,Arrays.asList(singleCheckBox,doubleCheckBox,tripleCheckBox,quadCheckBox,studioCheckBox,mezonetCheckBox,apartmentCheckBox),Arrays.asList(singleAreaField,doubleAreaField,tripleAreaField,quadAreaField,studioAreaField,mezonetAreaField,apartmentAreaField),Arrays.asList(singlePriceField,doublePriceField,triplePriceField,quadPriceField,studioPriceField,mezonetPriceField,apartmentPriceField),Arrays.asList(singleBedsField,doubleBedsField,tripleBedsField,quadBedsField,studioBedsField,mezonetBedsField,apartmentBedsField),this.floorsField.getText()),this);//transfers the rooms information to a static class which is used as a cache for all new hotel information during the whole process of creating[new hotel + new manager]
            log.info("New data for rooms added.");
            AlertManager.showAlert(Alert.AlertType.INFORMATION, "Информация", "✅ Успешно добавени данни за стаи.");
            ViewManager.closeDialogBox();
            ViewManager.changeView(Constants.View.OWNER_ADD_NEW_HOTEL_AND_NEW_MANAGER_VIEW, ViewManager.getPrimaryStage(), this.getClass(), "Owner Add New Hotel And New Manager", 800, 500);
        }
    }

    public void removeHotelRoomsInformation() throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Потвърждение");
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText("Наистина ли искате да изтриете въведените данни за стаи на вашия нов хотел?");
        alert.setX(ViewManager.getPrimaryStage().getX()+220);
        alert.setY(ViewManager.getPrimaryStage().getY()+180);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        ButtonType yesButton = new ButtonType("Да", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("Не", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> answer = alert.showAndWait();

        if(answer.isPresent() && answer.get()==yesButton)
        {
            NewHotelInformation.transferNewRoomsInformation(null,this);
            log.info("Current data for rooms deleted.");
            ViewManager.closeDialogBox();
            ViewManager.changeView(Constants.View.OWNER_ADD_NEW_HOTEL_AND_NEW_MANAGER_VIEW, ViewManager.getPrimaryStage(), this.getClass(), "Owner Add New Hotel And New Manager", 800, 500);
            ViewManager.openDialogBox(Constants.View.OWNER_HOTEL_ROOMS_INFORMATION_VIEW, null,this.getClass(),"Owner Hotel Rooms Information", 800, 450);
        }
    }

    //used to validate the whole hotel rooms information provided when creating a new hotel + new manager
    private boolean validateDynmaicTabsInformation(){

        if(this.tabPane==null || this.tabPane.getTabs().isEmpty())
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Моля задайте стаи за всеки етаж.");
            return false;
        }
        else
        {
            int floor =1;
            //I am absolutely sure that the content of every dynamically created tab is a Pane, because of the method "generateFloorsSpecifications"
            for(Tab tab: this.tabPane.getTabs())
            {

                Node component = tab.getContent();
                List <CheckBox> checkBoxesOfTabList = new ArrayList<>();
                String roomsNumberPerFloor;

                for(int i=0;i<((Pane)component).getChildren().size()-2;i++)//size is minus 2 because we count only the number of each checkboxes per dynamically created Tab and we don't count the icon and the text field
                {
                    //Here I am absolutely sure that every children of ((Pane)component) is CheckBox because of the method "generateFloorsSpecifications"
                  checkBoxesOfTabList.add((CheckBox)((((Pane)component).getChildren()).get(i)));
                }
                  //Here I put in a map number of floor and number of rooms entered for the specific floor
                 //roomsNumberPerFloorMap.put( floor, ((TextField)((((Pane)component).getChildren()).get(checkBoxesOfTabList.size()+1))).getText() );
                 roomsNumberPerFloor=((TextField)((((Pane)component).getChildren()).get(checkBoxesOfTabList.size()+1))).getText();

                //check whether at least 1 checkBox for room type is selected for each dynamic tab
                if(!roomService.validateRoomTypesCheckBoxesSelection(floor,checkBoxesOfTabList) || !roomService.validateRoomsNumberPerFloor(floor,roomsNumberPerFloor))
                {
                    return false;
                }

                //check whether number of rooms per floor is less than the number of room types for the same floor
                if(Integer.parseInt(roomsNumberPerFloor)<roomService.getSelectedRoomTypesCheckBoxes(checkBoxesOfTabList).size())
                {
                    AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Броят стаи на етаж "+floor+" трябва да е по-голям от броя избрани типове на стаи за същия етаж.");
                    return false;
                }
                floor++;
            }
            return true;//everything is OK (All validations and checks in the loops went smooth)
        }
    }

    public void closePage() {
        ViewManager.closeDialogBox();
    }

    //used to color room type check boxes on the right when there is cache for rooms information
    private void changeFeaturesOfdRoomTypesCheckBoxesOnTheRightSide(List<CheckBox> roomTypesCheckBoxes,List<CheckBox> cacheCheckBoxes){

        for(int i=0;i<roomTypesCheckBoxes.size();i++)
        {
            if(cacheCheckBoxes.get(i).isSelected())
            {
                roomTypesCheckBoxes.get(i).setSelected(true);
                roomTypesCheckBoxes.get(i).setStyle("-fx-text-fill:  #e68a00;");
            }
        }
    }

    private void changeFeaturesOfdRoomTypesAreaFieldsOnTheRightSide(List<TextField> roomTypesAreaFields,List<TextField> cacheRoomTypesAreaFields){

        for(int i=0;i<roomTypesAreaFields.size();i++)
        {
            if(!Objects.equals(cacheRoomTypesAreaFields.get(i).getText(), ""))
            {
                roomTypesAreaFields.get(i).setText(cacheRoomTypesAreaFields.get(i).getText());
            }
        }
    }

    private void changeFeaturesOfdRoomTypesPriceFieldsOnTheRightSide(List<TextField> roomTypesPriceFields,List<TextField> cacheRoomTypesPriceFields){

        for(int i=0;i<roomTypesPriceFields.size();i++)
        {
            if(!Objects.equals(cacheRoomTypesPriceFields.get(i).getText(), ""))
            {
                roomTypesPriceFields.get(i).setText(cacheRoomTypesPriceFields.get(i).getText());
            }
        }
    }

    private void changeFeaturesOfdRoomTypesBedsFieldsOnTheRightSide(List<TextField> roomTypesBedsFields,List<TextField> cacheRoomTypesBedsFields){

        for(int i=0;i<roomTypesBedsFields.size();i++)
        {
            if(!Objects.equals(cacheRoomTypesBedsFields.get(i).getText(), ""))
            {
                roomTypesBedsFields.get(i).setText(cacheRoomTypesBedsFields.get(i).getText());
            }
        }
    }

    private void disableAll(TextField floorsField,List<CheckBox> roomTypesCheckBoxes,List<TextField> roomTypesAreaFields,List<TextField> roomTypesPriceFields,List<TextField> roomTypesBedsFields,TabPane dynamicTabPane){

        floorsField.setDisable(true);
        this.enterHotelFloorsButton.setDisable(true);

        for(CheckBox checkBox: roomTypesCheckBoxes)
        {
            checkBox.setDisable(true);
        }
       for(TextField areaField: roomTypesAreaFields)
       {
           areaField.setDisable(true);
       }
       for(TextField priceField: roomTypesPriceFields)
       {
           priceField.setDisable(true);
       }
       for(TextField bedField: roomTypesBedsFields)
       {
           bedField.setDisable(true);
       }

       //disable all elements of dynamic tabs
       Pane pane;
       for(Tab dynamicTab: tabPane.getTabs())
       {
           pane=(Pane) dynamicTab.getContent();
           for(int i=0;i<pane.getChildren().size();i++)
           {
               pane.getChildren().get(i).setDisable(true);
           }
       }
    }

    public void checkSingleCheckBoxState()
    {
        singleAreaField.setDisable(!singleCheckBox.isSelected());
        singlePriceField.setDisable(!singleCheckBox.isSelected());
    }
    public void checkDoubleCheckBoxState()
    {
        doubleAreaField.setDisable(!doubleCheckBox.isSelected());
        doublePriceField.setDisable(!doubleCheckBox.isSelected());
    }
    public void checkTripleCheckBoxState()
    {
        tripleAreaField.setDisable(!tripleCheckBox.isSelected());
        triplePriceField.setDisable(!tripleCheckBox.isSelected());
    }
    public void checkQuadCheckBoxState()
    {
        quadAreaField.setDisable(!quadCheckBox.isSelected());
        quadPriceField.setDisable(!quadCheckBox.isSelected());
    }
    public void checkStudioCheckBoxState()
    {
        studioAreaField.setDisable(!studioCheckBox.isSelected());
        studioPriceField.setDisable(!studioCheckBox.isSelected());
        studioBedsField.setDisable(!studioCheckBox.isSelected());
    }
    public void checkMezonetCheckBoxState()
    {
        mezonetAreaField.setDisable(!mezonetCheckBox.isSelected());
        mezonetPriceField.setDisable(!mezonetCheckBox.isSelected());
        mezonetBedsField.setDisable(!mezonetCheckBox.isSelected());
    }
    public void checkApartmentCheckBoxState()
    {
        apartmentAreaField.setDisable(!apartmentCheckBox.isSelected());
        apartmentPriceField.setDisable(!apartmentCheckBox.isSelected());
        apartmentBedsField.setDisable(!apartmentCheckBox.isSelected());
    }

    public void initialize(){

        if(NewHotelInformation.getHotelRoomsInformation()!=null)//ako ima cache na staite
        {
            addHotelRoomsInformationButton.setDisable(true);
            addHotelRoomsInformationButton.setVisible(false);

            removeHotelRoomsInformationButton.setDisable(false);
            removeHotelRoomsInformationButton.setVisible(true);

            noTabPaneLabel.setVisible(false);
            infoLabel1.setVisible(false);
            infoLabel2.setVisible(false);
            infoLabel3.setVisible(false);

            this.tabPane=NewHotelInformation.getHotelRoomsInformation().getTabPane();//vuv tekushtiq tabPane na prozoreca slagame dannite ot keshiraniq tabPane
            grayPane.getChildren().add(this.tabPane);

            //change features of the room type checkBoxes on the right side which are present in the dynamic tabs
            changeFeaturesOfdRoomTypesCheckBoxesOnTheRightSide(Arrays.asList(singleCheckBox,doubleCheckBox,tripleCheckBox,quadCheckBox,studioCheckBox,mezonetCheckBox,apartmentCheckBox),NewHotelInformation.getHotelRoomsInformation().getRoomTypeCheckBoxes());


            changeFeaturesOfdRoomTypesAreaFieldsOnTheRightSide(Arrays.asList(singleAreaField,doubleAreaField,tripleAreaField,quadAreaField,studioAreaField,mezonetAreaField,apartmentAreaField),NewHotelInformation.getHotelRoomsInformation().getRoomTypeAreas());

            changeFeaturesOfdRoomTypesPriceFieldsOnTheRightSide(Arrays.asList(singlePriceField,doublePriceField,triplePriceField,quadPriceField,studioPriceField,mezonetPriceField,apartmentPriceField),NewHotelInformation.getHotelRoomsInformation().getRoomTypePrices());

            changeFeaturesOfdRoomTypesBedsFieldsOnTheRightSide(Arrays.asList(singleBedsField,doubleBedsField,tripleBedsField,quadBedsField,studioBedsField,mezonetBedsField,apartmentBedsField),NewHotelInformation.getHotelRoomsInformation().getRoomTypeBeds());

            this.floorsField.setText(NewHotelInformation.getHotelRoomsInformation().getFloorsNumber());

            disableAll(this.floorsField,Arrays.asList(singleCheckBox,doubleCheckBox,tripleCheckBox,quadCheckBox,studioCheckBox,mezonetCheckBox,apartmentCheckBox),Arrays.asList(singleAreaField,doubleAreaField,tripleAreaField,quadAreaField,studioAreaField,mezonetAreaField,apartmentAreaField),Arrays.asList(singlePriceField,doublePriceField,triplePriceField,quadPriceField,studioPriceField,mezonetPriceField,apartmentPriceField),Arrays.asList(singleBedsField,doubleBedsField,tripleBedsField,quadBedsField,studioBedsField,mezonetBedsField,apartmentBedsField),NewHotelInformation.getHotelRoomsInformation().getTabPane());
        }
        else//ako nqma cache na staite
        {
            checkSingleCheckBoxState();
            checkDoubleCheckBoxState();
            checkTripleCheckBoxState();
            checkQuadCheckBoxState();
            checkStudioCheckBoxState();
            checkMezonetCheckBoxState();
            checkApartmentCheckBoxState();

            removeHotelRoomsInformationButton.setDisable(true);
            removeHotelRoomsInformationButton.setVisible(false);

            addHotelRoomsInformationButton.setDisable(false);
            addHotelRoomsInformationButton.setVisible(true);

            noTabPaneLabel.setVisible(true);

            infoLabel1.setVisible(true);
            infoLabel2.setVisible(true);
            infoLabel3.setVisible(true);
        }

    }

}