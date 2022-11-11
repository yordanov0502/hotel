module bg.tu_varna.sit.hotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires log4j;
    requires org.hibernate.orm.core;

    exports bg.tu_varna.sit.hotel.back_end.application;
    opens bg.tu_varna.sit.hotel.back_end.application to javafx.fxml;
    exports bg.tu_varna.sit.hotel.front_end.presentation.application.controllers;
    opens bg.tu_varna.sit.hotel.front_end.presentation.application.controllers to javafx.fxml;

    exports bg.tu_varna.sit.hotel.front_end.presentation.admin.controllers;
    opens bg.tu_varna.sit.hotel.front_end.presentation.admin.controllers to javafx.fxml;

    exports bg.tu_varna.sit.hotel.front_end.presentation.owner.controllers;
    opens bg.tu_varna.sit.hotel.front_end.presentation.owner.controllers to javafx.fxml;

    exports bg.tu_varna.sit.hotel.front_end.presentation.manager.controllers;
    opens bg.tu_varna.sit.hotel.front_end.presentation.manager.controllers to javafx.fxml;

    exports bg.tu_varna.sit.hotel.front_end.presentation.receptionist.controllers;
    opens bg.tu_varna.sit.hotel.front_end.presentation.receptionist.controllers to javafx.fxml;
}