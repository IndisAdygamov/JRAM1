package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;

public class CryptoApplication extends Application {

    @Override
    public void start(Stage stage) {

        try {
            //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainScene.fxml")));
            URL url = this.getClass().getResource("/mainScene.fxml");
            URL ob = Objects.requireNonNull(url);
            Parent root = FXMLLoader.load(ob);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.getIcons().add(new Image("icon_logo_cesar.png"));
            stage.setTitle("CryptoCesar");
            stage.show();
            stage.setOnCloseRequest(event -> {
                event.consume();
                mainFileExit(stage);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mainFileExit(Stage stage) {
        System.out.println("========== System log: Exit via Alt+F4 ============");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You will exit the program.");
        alert.setContentText("Do you want to exit?");
        Optional<ButtonType> button = alert.showAndWait();
        if(button.orElse(ButtonType.OK) == ButtonType.OK) {
            System.out.println("You successfully exiting");
            System.out.println("Выход через крестик успешен");
            stage.close();
        }
        else {
            System.out.println("========== System log: Cancel ============");
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
