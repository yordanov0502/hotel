package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.RoomService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.apache.log4j.Logger;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.util.*;

public class OwnerHotelRoomsInformationController implements NewHotelInfoProvider{
    private static final Logger log = Logger.getLogger(OwnerHotelRoomsInformationController.class);
    private final RoomService roomService = RoomService.getInstance();

    @FXML
    private Pane grayPane;

    @FXML
    private TextField floorsField;

    @FXML
    private Label noTabPaneLabel;

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



    public void generateFloorsSpecifications(){

        if(roomService.validateRoomsInformationFields(floorsField.getText(),Arrays.asList(singleCheckBox,doubleCheckBox,tripleCheckBox,quadCheckBox,studioCheckBox,mezonetCheckBox,apartmentCheckBox),Arrays.asList(singleAreaField,doubleAreaField,tripleAreaField,quadAreaField,studioAreaField,mezonetAreaField,apartmentAreaField),Arrays.asList(singlePriceField,doublePriceField,triplePriceField,quadPriceField,studioPriceField,mezonetPriceField,apartmentPriceField)))
        {
            //changes the color of the selected room type checkBoxes which are present in the dynamic tabs
            roomService.changeColorOfSelectedRoomTypesCheckBoxes(roomService.getSelectedRoomTypesCheckBoxes(Arrays.asList(singleCheckBox,doubleCheckBox,tripleCheckBox,quadCheckBox,studioCheckBox,mezonetCheckBox,apartmentCheckBox)));

            grayPane.getChildren().remove(HotelRoomsInformation.getTabPane());

            HotelRoomsInformation.setTabPane(null,this);
            HotelRoomsInformation.setTabPane(new TabPane(),this);

           // HotelRoomsInformation.setTabList(null,this);
           // HotelRoomsInformation.setTabList(new ArrayList<>(),this);

            HotelRoomsInformation.getTabPane().setLayoutX(6);
            HotelRoomsInformation.getTabPane().setLayoutY(101);
            HotelRoomsInformation.getTabPane().setPrefWidth(390);
            HotelRoomsInformation.getTabPane().setPrefHeight(259);

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
                HotelRoomsInformation.getTabPane().getTabs().add(tab);
                //HotelRoomsInformation.getTabList().add(tab);////////////////////////////////
            }
             noTabPaneLabel.setVisible(false);
             grayPane.getChildren().add(HotelRoomsInformation.getTabPane());
        }
    }


    public void addHotelRoomsInformation(){

        if(roomService.validateRoomsInformationFields(floorsField.getText(),Arrays.asList(singleCheckBox,doubleCheckBox,tripleCheckBox,quadCheckBox,studioCheckBox,mezonetCheckBox,apartmentCheckBox),Arrays.asList(singleAreaField,doubleAreaField,tripleAreaField,quadAreaField,studioAreaField,mezonetAreaField,apartmentAreaField),Arrays.asList(singlePriceField,doublePriceField,triplePriceField,quadPriceField,studioPriceField,mezonetPriceField,apartmentPriceField))
           && validateDynmaicTabsInformation())
        {
           System.out.println("everything is OK");
        }
        else
        {
            System.out.println("no enough information");
        }
    }


    //used to validate the whole hotel rooms information provided when creating a new hotel + new manager
    private boolean validateDynmaicTabsInformation(){

        //if(HotelRoomsInformation.getTabList()==null || HotelRoomsInformation.getTabList().isEmpty())
        if(HotelRoomsInformation.getTabPane()==null || HotelRoomsInformation.getTabPane().getTabs().isEmpty())
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Моля задайте стаи за всеки етаж.");
            return false;
        }
        else
        {
            int floor =1;
            //I am absolutely sure that the content of every dynamically created tab is a Pane, because of the method "generateFloorsSpecifications"
            for(Tab tab: HotelRoomsInformation.getTabPane().getTabs())
            {

                Node component = tab.getContent();
                List <CheckBox> checkBoxesOfTabList = new ArrayList<>();
                List<String> roomsNumberPerFloorList = new ArrayList<>();

                for(int i=0;i<((Pane)component).getChildren().size()-2;i++)//size is minus 2 because we count only the number of each checkboxes per dynamically created Tab and we don't count the icon and the text field
                {
                    //Here I am absolutely sure that every children of ((Pane)component) is CheckBox because of the method "generateFloorsSpecifications"
                  checkBoxesOfTabList.add((CheckBox)((((Pane)component).getChildren()).get(i)));
                }
                  //Here I put in a map number of floor and number of rooms entered for the specific floor
                 //roomsNumberPerFloorMap.put( floor, ((TextField)((((Pane)component).getChildren()).get(checkBoxesOfTabList.size()+1))).getText() );
                 roomsNumberPerFloorList.add(((TextField)((((Pane)component).getChildren()).get(checkBoxesOfTabList.size()+1))).getText());

                //System.out.println("Iteration: "+ floor);
                //System.out.println(roomsNumberPerFloorList);

                //check whether at least 1 checkBox for room type is selected for each dynamic tab
                if(!roomService.validateRoomTypesCheckBoxesSelection(floor,checkBoxesOfTabList) || !roomService.validateRoomsNumberPerFloor(floor,roomsNumberPerFloorList))
                {
                    return false;
                }
                  //this will be useful when initializing the view cache information exists!!!!!!!!!!!!!!!!!!!!!!!!
                   // for up ^ List<CheckBox> selectedCheckBoxesOfTabList = roomService.getSelectedRoomTypesCheckBoxes();
                floor++;
            }
            return true;//everything is OK (All validations and checks in the loops went smooth)
        }
    }


    public void closePage() {
        ViewManager.closeDialogBox();
    }

    public void a()
    {
        //singleAreaField.setDisable(!singleCheckBox.isSelected());/ / / /
        //singlePriceField.setDisable(!singleCheckBox.isSelected());/ / / /
    }

    public void initialize(){

        //changes the color of the selected room type checkBoxes which are present in the dynamic tabs
        //roomService.changeColorOfSelectedRoomTypesCheckBoxes(roomService.getSelectedRoomTypesCheckBoxes(Arrays.asList(singleCheckBox,doubleCheckBox,tripleCheckBox,quadCheckBox,studioCheckBox,mezonetCheckBox,apartmentCheckBox)));


        //singleAreaField.setDisable(true);/ / / /
     //singlePriceField.setDisable(true);/ / / /
    }

}