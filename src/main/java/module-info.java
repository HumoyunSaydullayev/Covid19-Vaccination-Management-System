module com.example.covid_19_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;
    requires java.sql;
    requires itextpdf;

    opens com.example.covid_19_system to javafx.fxml;
    exports com.example.covid_19_system;
    exports Admin;
    opens Admin to javafx.fxml;
    exports User;
    opens User to javafx.fxml;
    exports Classes;
    opens Classes to javafx.fxml;
}