package admin.admin_ui;

import admin.admin_controller.NewProductController;
import collections.DataCollection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@SuppressWarnings("Duplicates")
public class NewProductUI {
    @FXML
    AnchorPane paneMini;

    @FXML
    TextField nameField, descriptionField;

    @FXML
    Text pathField;

    @FXML
    Button okButton, cancelButton;

    @FXML
    ComboBox<String> typeChoice, rankChoice;

    private NewProductController controller;

    public void initializeData(DataCollection collection) {
        controller = new NewProductController(collection);
        controller.initializeComboBox(typeChoice, rankChoice);
    }

    @FXML
    public void handleCancelButton() {
        System.out.println("You handled `Cancel` Button.");
        Stage newStage = (Stage) cancelButton.getScene().getWindow();
        newStage.close();
    }

    @FXML
    public void handleOKButton() {
        System.out.println("You handled `OK` Button.");
        controller.setDataFromField(nameField, descriptionField);
        String result = controller.dataVerification();
        if (result.equals("success")) {
            showInformationAlert();
            Stage newStage = (Stage) okButton.getScene().getWindow();
            newStage.close();
        } else {
            showWarningAlert(result);
        }
    }

    @FXML
    public void handleChooseButton() {
        System.out.println("You handled `Choose` Button.");
        controller.chooseFile(pathField, getStage());
    }

    private void showWarningAlert(String messageStr) {
        Alert alert = controller.createWarningAlert(messageStr);
        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }

    private void showInformationAlert() {
        Alert alert = controller.createInformationAlert();
        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }

    private Stage getStage() {
        return (Stage) paneMini.getScene().getWindow();
    }

}