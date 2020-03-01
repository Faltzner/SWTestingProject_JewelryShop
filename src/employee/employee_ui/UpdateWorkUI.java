package employee.employee_ui;

import collections.DataCollection;
import employee.employee_controller.UpdateWorkController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@SuppressWarnings("Duplicates")
public class UpdateWorkUI {
    @FXML
    AnchorPane paneMini;

    @FXML
    Text pathText, productName, workerName, from , workID;

    @FXML
    ImageView imageView;

    @FXML
    ComboBox<String> statusChoice;

    @FXML
    Button okButton, cancelButton;

    private UpdateWorkController controller;

    public void initializeData(DataCollection collection, String workOrder, String order, String employee, String product
            , String localDate, String dueDate, String statusRow, String workerName) {
        controller = new UpdateWorkController(collection);
        controller.setData(workOrder, order, employee, product, localDate, dueDate, statusRow, workerName);
        controller.setTextField(workID, from, this.workerName, this.productName);
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

    @FXML
    public void handleChooseButton() {
        System.out.println("You handled `Choose` Button.");
        controller.chooseFile(pathText, getStage(), imageView);
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