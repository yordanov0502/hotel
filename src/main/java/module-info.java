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
    requires java.persistence;
    requires java.naming;
    requires java.sql;
    requires org.apache.commons.lang3;
    requires de.jensd.fx.glyphs.fontawesome;



    opens bg.tu_varna.sit.hotel.application to javafx.fxml;
    exports bg.tu_varna.sit.hotel.application;
    exports bg.tu_varna.sit.hotel.presentation.controllers.application;
    opens bg.tu_varna.sit.hotel.presentation.controllers.application to javafx.fxml;

    exports bg.tu_varna.sit.hotel.presentation.controllers.admin;
    opens bg.tu_varna.sit.hotel.presentation.controllers.admin to javafx.fxml;

    exports bg.tu_varna.sit.hotel.presentation.controllers.owner;
    opens bg.tu_varna.sit.hotel.presentation.controllers.owner to javafx.fxml;

    exports bg.tu_varna.sit.hotel.presentation.controllers.manager;
    opens bg.tu_varna.sit.hotel.presentation.controllers.manager to javafx.fxml;

    exports bg.tu_varna.sit.hotel.presentation.controllers.receptionist;
    opens bg.tu_varna.sit.hotel.presentation.controllers.receptionist to javafx.fxml;


    exports bg.tu_varna.sit.hotel.data.access;
    opens bg.tu_varna.sit.hotel.data.access to org.hibernate.orm.core;
    exports bg.tu_varna.sit.hotel.data.entities;
    opens bg.tu_varna.sit.hotel.data.entities to org.hibernate.orm.core;

    exports bg.tu_varna.sit.hotel.presentation.models;
    opens bg.tu_varna.sit.hotel.presentation.models to java.base;
}