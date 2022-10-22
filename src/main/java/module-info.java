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

    exports bg.tu_varna.sit.hotel.back_end.application;
    opens bg.tu_varna.sit.hotel.back_end.application to javafx.fxml;
    exports bg.tu_varna.sit.hotel.front_end.presentation.application.controllers;
    opens bg.tu_varna.sit.hotel.front_end.presentation.application.controllers to javafx.fxml;

    exports bg.tu_varna.sit.hotel.front_end.presentation.admin.controllers;
    opens bg.tu_varna.sit.hotel.front_end.presentation.admin.controllers to javafx.fxml;
}