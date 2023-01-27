package bg.tu_varna.sit.hotel.presentation.controllers.receptionist.custom;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

//code source: https://stackoverflow.com/questions/30593193/creating-sliding-on-off-switch-button-in-javafx
public class SwitchButton extends StackPane {
    private final Rectangle back = new Rectangle(180, 50, Color.RED);
    private final Button button = new Button();
    private final String buttonStyleOff = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: #800000;";
    private final String buttonStyleOn = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: #155115;";
    private boolean state = true;
    private Label ratingLabel = new Label("Добра");

    private void init() {
        ratingLabel.setStyle("-fx-text-fill: White; -fx-font-family: Arial; -fx-font-size: 22; -fx-alignment: center");
        ratingLabel.setPrefWidth(120);
        ratingLabel.setPrefHeight(23);
        getChildren().addAll(back, button,ratingLabel);
        setAlignment(ratingLabel, Pos.CENTER_RIGHT);
        back.prefWidth(100);
        back.prefHeight(100);
        back.setArcHeight(back.getHeight());
        back.setArcWidth(back.getHeight());
        back.setFill(Color.valueOf("#00893d"));
        double r = 30.0;
        button.setShape(new Circle(r));
        setAlignment(button, Pos.CENTER_LEFT);
        button.setMaxSize(50, 50);
        button.setMinSize(50, 50);
        button.setStyle(buttonStyleOn);
    }

    public SwitchButton() {
        init();
        EventHandler<Event> click = new EventHandler<Event>() {
            @Override
            public void handle(Event e) {
                if (state)
                {
                    button.setStyle(buttonStyleOff);
                    back.setFill(Color.valueOf("#e60000"));
                    ratingLabel.setText("Лоша");
                    setAlignment(button, Pos.CENTER_RIGHT);
                    setAlignment(ratingLabel, Pos.CENTER_LEFT);
                    state = false;
                }
                else
                {
                    button.setStyle(buttonStyleOn);
                    back.setFill(Color.valueOf("#00893d"));
                    ratingLabel.setText("Добра");
                    setAlignment(button, Pos.CENTER_LEFT);
                    setAlignment(ratingLabel, Pos.CENTER_RIGHT);
                    state = true;
                }
            }
        };

        button.setFocusTraversable(false);
        setOnMouseClicked(click);
        button.setOnMouseClicked(click);
    }

    //true = good customer rating(everything was OK)
    //false = bad customer rating(the customer caused problems or damage or sth else)
    public boolean getState() {return state;}
}