module com.example.sample {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sample to javafx.fxml;
    exports com.example.sample;
}