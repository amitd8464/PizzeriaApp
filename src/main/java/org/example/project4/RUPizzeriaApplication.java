package org.example.project4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * class to run the RU pizza application
 * @author Andrew Ho, Amit Deshpande
 */
public class RUPizzeriaApplication extends Application {
    /**
     * Starter for the stage
     * @param stage - stage to be built
     * @throws IOException - if resource is not found
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/project4/add-order-view.fxml"));
        Scene scene = new Scene(loader.load()); // 759,440
        stage.setTitle("RUPizzeria");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main
     * @param args - none needed
     */
    public static void main(String[] args) {
        launch();
    }
}