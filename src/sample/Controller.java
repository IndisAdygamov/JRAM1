package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.nio.file.Files;

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
    private AnchorPane anchorPaneMain;
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
    private TextArea keyText;
    @FXML
    private ProgressBar progress;
    private String leftString;
    private Stage stage;
    private File fileSource;

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
        System.out.println("========== System log: Menu->File->Exit =========="+ rightText.getText()+"1");

        if(!rightText.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit");
            alert.setHeaderText("You will exit the program.");
            alert.setContentText("Do you want to save before exiting?: ");
            ButtonType button = alert.showAndWait().get();
            if (button == ButtonType.OK) {
                System.out.println("========== System log: OK ==========");
                if(actionFileSaveAs()==0) {
                    System.out.println("You successfully exiting");
                    System.out.println("Выход через File->Exit успешен");
                    stage = (Stage) anchorPaneMain.getScene().getWindow();
                    stage.close();
                }
                else if(actionFileSaveAs()==1) {
                    System.out.println("File not saved, canceled.");
                }
            }
            else if(button == ButtonType.CANCEL) {
                System.out.println("========== System log: Cancel ==========");
            }
        }
        else {
            System.out.println("You successfully exiting");
            System.out.println("Выход через File->Exit успешен");
            stage = (Stage) anchorPaneMain.getScene().getWindow();
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
            fileSource = file;
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
    private int actionFileSaveAs() {
        System.out.println("========== System log: Menu->File->Save As ==========");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file as");
        File file = fileChooser.showSaveDialog(stage);

        System.out.println("file for save: " + file);
        if(file == null) {
            return 1;
        }

        try(FileWriter fileWriter = new FileWriter(file)) {
            String string = rightText.getText();
            fileWriter.write(string.toCharArray());
            System.out.println("========== System log: Save file as " + file + " done. ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
        showStatus(file + " Saved.");
        return 0;
    }

    @FXML
    private void actionFileSave() {
        System.out.println("========== System log: Menu->File->Save ==========");
        if(Files.exists(fileSource.toPath())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save");
            alert.setHeaderText("File " + fileSource + " is exist!");
            alert.setContentText("Do you want to overwrite?");
            ButtonType button = alert.showAndWait().get();
            if (button == ButtonType.OK) {
                System.out.println("========== System log: OK ==========");
                try(FileWriter fileWriter = new FileWriter(fileSource)) {
                    String string = rightText.getText();
                    fileWriter.write(string.toCharArray());
                    System.out.println("========== System log: Save file " + fileSource + " done. ==========");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                showStatus(fileSource + " Saved.");
            }
            else if(button == ButtonType.CANCEL) {
                System.out.println("========== System log: Cancel ==========");
            }
        }
    }

    @FXML
    private void actionFileClose() {
        System.out.println("========== System log: Menu->File->Close ==========");
        leftText.setText("");
        rightText.setText("");
        keyText.setText("");
        showStatus("Closed");
    }

    @FXML
    private void actionEditEncrypt() {
        System.out.println("========== System log: Menu->Edit->Encrypt ==========");
        boolean validKey = false;
        int number = 0;
        String key = keyText.getText();
        if(leftText.getText().equals("")) {
            System.out.println("========== System log: Missing text to process ==========");
            return;
        }
        else {
            System.out.println("========== System log: Text to process found ==========");
        }

        if(!key.equals("")) {
            try{
                number = Integer.parseInt(key);
                validKey = true;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.out.println("========== System log: Encrypt failed, key is not valid ==========");
            }
            if(validKey) {
                String rightString = TextEngine.encodeString(leftString, Integer.parseInt(key));
                System.out.println(rightString);
                rightText.setText(rightString);
                showStatus("Encrypted");
            }
        }
        else {
            showStatus("Not Encrypted, key is not valid");
        }
    }

    @FXML
    private void actionEditDecrypt() {
        System.out.println("========== System log: Menu->Edit->Decrypt ==========");
        boolean validKey = false;
        String key = keyText.getText();
        int number = 0;
        if(!key.equals(""))  {
            try{
                number = Integer.parseInt(key);
                validKey = true;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.out.println("========== System log: Decrypt failed, key is not valid ==========");
            }
            if(validKey) {
                String rightString = TextEngine.decodeString(leftString, number);
                System.out.println(rightString);
                rightText.setText(rightString);
                showStatus("Decrypted");
            }
        }
        else {
            showStatus("Not Decrypted, key is not valid");
        }
    }

    @FXML
    private void actionEditDecryptAuto() {
        System.out.println("========== System log: Menu->Edit->DecryptAuto ==========");
        String result = TextEngine.decodeStringAuto(leftText.getText());

        if(result == null) {
            System.out.println("========== System log: AutoDecrypt fault");
            showStatus("Fault");
        }
        else {
            rightText.setText(result);
            showStatus("Decrypted");
        }
        //System.out.println(result);
    }

    @FXML
    private void actionEditDecryptManually() {
        System.out.println("========== System log: Menu->Edit->DecryptManually ==========");
    }

    @FXML
    private void showStatus(String newStatus) {
        System.out.println("========== System log: Change status " + newStatus + " ==========");
        String stringStatus = "status: ";
        status.setText(stringStatus + newStatus);
    }
}