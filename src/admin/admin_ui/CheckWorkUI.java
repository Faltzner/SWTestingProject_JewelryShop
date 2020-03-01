package admin.admin_ui;

import admin.admin_controller.CheckWorkController;
import collections.DataCollection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@SuppressWarnings("Duplicates")
public class CheckWorkUI {
    @FXML
    AnchorPane paneMini;

    @FXML
    Text productID, workerName, from , workID;

    @FXML
    ImageView imageView;

    @FXML
    ComboBox<String> statusChoice;

    @FXML
    Button okButton, cancelButton;

    private CheckWorkController controller;

    public void initializeData(DataCollection collection, String workOrder, String order, String employee, String product
            , String localDate, String dueDate, String statusRow, Image image, String employeeName) {
        controller = new CheckWorkController(collection);
        controller.setData(workOrder, order, employee, product, localDate, dueDate, statusRow, image, employeeName);
        controller.setField(workID, from, this.workerName, productID, imageView);
        controller.initializeComboBox(statusChoice);
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
