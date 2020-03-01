package admin.admin_ui;

import admin.admin_controller.BuyOrderController;
import collections.BuyOrder;
import collections.DataCollection;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@SuppressWarnings("Duplicates")
public class BuyOrderUI {
    @FXML
    AnchorPane pane;

    @FXML
    TableView<BuyOrder> tableView;

    @FXML
    TableColumn buyOrderID, assignedDate, dueDate, productID, quantity, pricePerUnit, assignedNum, status, action;

    private BuyOrderController controller;

    public void initializeData(DataCollection collection) {
        controller = new BuyOrderController(collection);
        setCellTable();
        tableView.setItems(controller.getBuyOrderList());
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
        tableView.setItems(controller.getBuyOrderList());
    }

    private void setCellTable() {
        buyOrderID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        assignedDate.setCellValueFactory(new PropertyValueFactory<>("AssignedDate"));
        dueDate.setCellValueFactory(new PropertyValueFactory<>("DueDate"));
        productID.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        pricePerUnit.setCellValueFactory(new PropertyValueFactory<>("PricePerUnit"));
        assignedNum.setCellValueFactory(new PropertyValueFactory<>("AssignedNum"));
        status.setCellValueFactory(new PropertyValueFactory<>("Status"));
    }

    public Stage getStage(){
        return (Stage) pane.getScene().getWindow();
    }

}