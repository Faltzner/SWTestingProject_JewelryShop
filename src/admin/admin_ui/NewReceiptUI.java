package admin.admin_ui;

import admin.admin_controller.NewReceiptController;
import collections.DataCollection;
import collections.NewReceipt;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@SuppressWarnings("Duplicates")
public class NewReceiptUI {
    @FXML
    AnchorPane paneMini;

    @FXML
    TableView<NewReceipt> tableView;

    @FXML
    TableColumn selection, buyOrderID, productName, quantity, date;

    @FXML
    Button cancelButton, okButton;

    private NewReceiptController controller;

    public void initializeData(DataCollection collection) {
        controller = new NewReceiptController(collection);
        setCellTable();
        tableView.setItems(controller.getNewReceiptList());
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

    private void setCellTable() {
        selection.setCellValueFactory(new PropertyValueFactory<>("Checkbox"));
        buyOrderID.setCellValueFactory(new PropertyValueFactory<>("BuyOrderID"));
        productName.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        date.setCellValueFactory(new PropertyValueFactory<>("Date"));
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