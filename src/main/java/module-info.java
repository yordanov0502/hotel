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

    exports bg.tu_varna.sit.hotel.run_application;
    opens bg.tu_varna.sit.hotel.run_application to javafx.fxml;
}