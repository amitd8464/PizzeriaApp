module org.example.project4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jshell;
    requires java.desktop;


    opens org.example.project4 to javafx.fxml;
    exports org.example.project4;
}