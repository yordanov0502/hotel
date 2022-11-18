package bg.tu_varna.sit.hotel.application;

import bg.tu_varna.sit.hotel.common.CommonTask;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.data.repositories.UserRepository;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    public static Stage stage;//initial and main stage
    public static double x, y;//initial and current coordinates
    public static double xx, yy;//temporary coordinates(when moving the window)

    @Override
    public void start(Stage stageParameter) throws IOException
    {
        CommonTask.pageNavigation(Constants.View.WELCOME_VIEW,Main.stage,this.getClass(),"Hotel Management System",800,500);

        User user1 = new User();

        //user1.setId(55L);
        user1.setFirstName("Катя");
        user1.setLastName("aaa");

        UserRepository userRepository = new UserRepository();
        userRepository.save(user1);



    }

    public static void main(String[] args) {launch();}
}