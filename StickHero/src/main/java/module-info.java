module com.approject.stickhero {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.approject.stickhero to javafx.fxml;
    exports com.approject.stickhero;
}