package admin.admin_ui;

import admin.admin_controller.AddEmployeeController;
import collections.DataCollection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@SuppressWarnings("Duplicates")
public class AddEmployeeUI {
    @FXML
    AnchorPane paneMini;

    @FXML
    TextField nameField, passwordField, numPhoneField;

    @FXML
    CheckBox necklaceSkillCheck, earringsSkillCheck, ringSkillCheck, braceletSkillCheck;

    @FXML
    Button cancelButton, okButton;

    private AddEmployeeController controller;

    public void initializeData(DataCollection collection) {
        controller = new AddEmployeeController(collection);
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
        controller.setDataFromField(nameField, passwordField, numPhoneField);
        controller.setSkill(necklaceSkillCheck, earringsSkillCheck, braceletSkillCheck, ringSkillCheck);
        String result = controller.dataVerification();
        if (result.equals("success")) {
            showInformationAlert();
            Stage newStage = (Stage) okButton.getScene().getWindow();
            newStage.close();
        } else {
            showWarningAlert(result);
        }
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

}