package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class Controller {
    @FXML
    private MenuItem menuFileExit;
    @FXML
    private MenuItem menuFileOpen;
    @FXML
    private MenuItem menuFileSave;
    @FXML
    private MenuItem menuFileSaveAs;
    @FXML
    private MenuItem menuFileClose;
    @FXML
    private MenuItem menuEditEncrypt;
    @FXML
    private MenuItem menuEditDecrypt;
    @FXML
    private MenuItem menuEditDecryptAuto;
    @FXML
    private MenuItem menuEditDecryptManually;
    @FXML
    private MenuItem menuHelpAbout;
    @FXML
    private VBox vboxMain;
    Stage stage;

    @FXML
    private Label alphabet;
    @FXML
    private TextArea alphabetText;
    @FXML
    private TextArea leftText;
    @FXML
    private TextArea rightText;
    @FXML
    private Label status;
    @FXML
    private ProgressBar progress;
    private String leftString;
    private String rightString;

    public Controller() {
    }

    public void initialize() {
        // TODO
        System.out.println("========== System log: Initialization ==========");
        String alphabet = "";
        for(Character ch : TextEngine.getAlphabet()) {
            alphabet = alphabet +" " + ch.toString();
        }

        alphabetText.setText(alphabet);
    }

    @FXML
    private void actionFileExit() {
        System.out.println("========== System log: Menu->File->Exit ==========");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You will exit the program.");
        alert.setContentText("Do you want to save before exiting?: ");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("========== System log: OK ==========");
            stage = (Stage) vboxMain.getScene().getWindow();
            System.out.println("You successfully exiting");
            System.out.println("Выход через File->Exit успешен");
            stage.close();
        }
    }

    @FXML
    private void actionHelpAbout() {
        System.out.println("========== System log: Menu->Help->About ==========");
        Alert alertAbout = new Alert(Alert.AlertType.INFORMATION);
        alertAbout.setTitle("About");
        alertAbout.setHeaderText("Разработано в НИИ JavaRush");
        alertAbout.setContentText("Программа для шифрования/дешифрования текстовых файлов алгоритмом Цезаря\n" +
                                    "версия 0.1\n" +
                                    "лицензия GNU GPL3\n" +
                                    "Автор: Indis Adygamov, email: i.adygamov@gmail.com");
        alertAbout.setGraphic(new ImageView(new Image("icon48.png")));
        alertAbout.showAndWait();
    }

    @FXML
    private void actionFileOpen() throws InterruptedException {
        System.out.println("========== System log: Main->File->Open ==========");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File file = fileChooser.showOpenDialog(stage);

        if(file != null) {
            StringBuilder stringBuilder = new StringBuilder();
            try (FileReader fileReader = new FileReader(file)) {
                int character;
                while ((character = fileReader.read()) != -1) {
                    System.out.print((char) (character));
                    stringBuilder.append((char) character);
                }
                System.out.println("========== System log: Read from " + file + " done. ==========");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            leftString = stringBuilder.toString();
            leftText.setText(leftString);
            showStatus(file + " Opened.");
        }
        else {
            System.out.println("========== System log : Menu->File->Open->Cancel, file path is null ==========");
        }
    }

    @FXML
    private void actionFileSaveAs() {
        System.out.println("========== System log: Menu->File->Save As ==========");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file as");
        File file = fileChooser.showSaveDialog(stage);
        System.out.println(file);

        try(FileWriter fileWriter = new FileWriter(file)) {
            String string = rightText.getText();
            fileWriter.write(string.toCharArray());
            System.out.println("Save file as " + file + " done.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        showStatus(file + " Saved.");
    }

    @FXML
    private void actionFileSave() {
        System.out.println("========== System log: Menu->File->Save ==========");
    }

    @FXML
    private void actionFileClose() {
        System.out.println("========== System log: Menu->File->Close ==========");
        leftText.setText("");
        rightText.setText("");
        showStatus("Closed");
    }

    @FXML
    private void actionEditEncrypt() {
        System.out.println("========== System log: Menu->Edit->Encrypt ==========");
        rightString = TextEngine.encodeString(leftString.toString(), 1);
        System.out.println(rightString);
        rightText.setText(rightString);
        showStatus("Encrypted");
    }

    @FXML
    private void actionEditDecrypt() {
        System.out.println("========== System log: Menu->Edit->Decrypt ==========");
        String rightString = TextEngine.decodeString(leftString.toString(), 1);
        System.out.println(rightString);
        rightText.setText(rightString);
        showStatus("Decrypted");
    }

    @FXML
    private void actionEditDecryptAuto() {
        System.out.println("========== System log: Menu->Edit->Decrypt Auto ==========");
    }

    @FXML
    private void actionEditDecryptManually() {
        System.out.println("========== System log: Menu->Edit->Decrypt Manually ==========");
    }

    @FXML
    private void showStatus(String newStatus) {
        System.out.println("========== System log: Change status " + newStatus + " ==========");
        String stringStatus = "status: ";
        status.setText(stringStatus + newStatus);
    }
}
