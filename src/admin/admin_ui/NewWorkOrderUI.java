package admin.admin_ui;

import admin.admin_controller.NewWorkOrderController;
import collections.DataCollection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@SuppressWarnings("Duplicates")
public class NewWorkOrderUI {
    @FXML
    AnchorPane paneMini;

    @FXML
    Text typeText, productText;

    @FXML
    ComboBox<String> fromChoice, employeeChoice;

    @FXML
    DatePicker datePicker;

    @FXML
    Button cancelButton, okButton;

    private NewWorkOrderController controller;

    public void initializeData(DataCollection collection) {
        controller = new NewWorkOrderController(collection);
        controller.initializeComboBox(fromChoice, employeeChoice, typeText, productText);
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
        controller.setDateAndLocalDate(datePicker);
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