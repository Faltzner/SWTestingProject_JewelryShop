package login_gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static int WIDTH = 1200, HEIGHT = 750;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login_gui/LoginPageUI.fxml"));
        Scene scene = new Scene(loader.load(), WIDTH, HEIGHT);
        primaryStage.setTitle("Jewelry Shop");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}