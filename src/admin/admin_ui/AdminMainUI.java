package admin.admin_ui;

import admin.admin_controller.AdminMainController;
import collections.DataCollection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@SuppressWarnings("Duplicates")
public class AdminMainUI {
    @FXML
    AnchorPane pane;

    @FXML
    Button buyOrder, workOrder, product, receipt, employee, logout;

    private AdminMainController controller;

    public void initializeData(DataCollection collection) {
        controller = new AdminMainController(collection);
    }

    public void handleBuyOrderButton() {
        controller.loadBuyOrderUI(getStage());
        System.out.println("You handled `Buy Order` button.");
    }

    public void handleWorkOrderButton() {
        controller.loadWorkOrderUI(getStage());
        System.out.println("You handled `Work Order` button.");
    }

    public void handleProductButton() {
        controller.loadProductUI(getStage());
        System.out.println("You handled `Product` button.");
    }

    public void handleReceiptButton() {
        controller.loadReceiptUI(getStage());
        System.out.println("You handled `Receipt` button.");
    }

    public void handleEmployeeButton() {
        controller.loadEmployeeUI(getStage());
        System.out.println("You handled `Employee` button.");
    }

    public void handleLogoutButton() {
        controller.logout(getStage());
        System.out.println("You handled `Logout` button.");
    }

    private Stage getStage() {
        return (Stage) pane.getScene().getWindow();
    }

}