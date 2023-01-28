package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CryptoApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.getIcons().add(new Image("icon_logo_cesar.png"));
            stage.setTitle("СryptoCesar");
            stage.show();
            stage.setOnCloseRequest(event -> mainFileExit(stage));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mainFileExit(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You will exit the program.");
        alert.setContentText("Do you want to save before exiting?: ");

        if(alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("You successfully exiting");
            System.out.println("Выход через крестик успешен");
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
