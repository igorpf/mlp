package com.mlp;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLControllerOO controller = new FXMLControllerOO();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/view.fxml"));
        fxmlLoader.setController(controller);
        fxmlLoader.load();

        Scene scene = new Scene(fxmlLoader.getRoot());
        scene.getStylesheets().add("/styles/view.css");

        stage.setTitle("Tetris");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
