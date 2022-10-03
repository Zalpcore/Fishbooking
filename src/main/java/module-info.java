module com.alperovich.fishbook.management.fishbookproj {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;



    opens com.alperovich.fishbook.management to javafx.fxml;
    exports com.alperovich.fishbook.management;
    exports com.alperovich.fishbook.management.controllers;
    opens com.alperovich.fishbook.management.controllers to javafx.fxml;
    exports com.alperovich.fishbook.management.models;
    opens com.alperovich.fishbook.management.models to javafx.fxml;


}