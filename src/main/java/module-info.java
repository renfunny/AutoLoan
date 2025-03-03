module com.example.autoloan {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.example.autoloan to javafx.fxml;
    exports com.example.autoloan;
    exports com.example.autoloan.controller;
    opens com.example.autoloan.controller to javafx.fxml;
    opens com.example.autoloan.model to javafx.base;
}