package admin.admin_ui;

import admin.admin_controller.ReceiptController;
import collections.DataCollection;
import collections.Receipt;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@SuppressWarnings("Duplicates")
public class ReceiptUI {
    @FXML
    AnchorPane pane;

    @FXML
    private TableView<Receipt> tableView;

    @FXML
    TableColumn receiptID, date, view, totalPrice, buyOrderID;

    ReceiptController controller;

    public void initializeData(DataCollection collection) {
        controller = new ReceiptController(collection);
        setCellTable();
        tableView.setItems(controller.getReceiptList());
    }

    @FXML
    public void handleBackButton() {
        System.out.println("You handled `Back` button.");
        controller.loadMainUI(getStage());
    }

    @FXML
    public void handleCreateButton() {
        System.out.println("You handled `Create` Button.");
        controller.loadCreateUI();
        tableView.setItems(controller.getReceiptList());
    }

    private void setCellTable() {
        receiptID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("TotalPrice"));
        date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        buyOrderID.setCellValueFactory(new PropertyValueFactory<>("BuyOrderID"));
    }

    public Stage getStage() {
        return (Stage) pane.getScene().getWindow();
    }

}