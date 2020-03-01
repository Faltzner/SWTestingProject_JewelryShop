package admin.admin_ui;

import admin.admin_controller.WorkOrderController;
import collections.DataCollection;
import collections.WorkOrder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@SuppressWarnings("Duplicates")
public class WorkOrderUI {
    @FXML
    AnchorPane pane;

    @FXML
    TableView<WorkOrder> tableView;

    @FXML
    TableColumn workID, from, employeeID, productID, assignedDate, dueDate, status, action;

    private WorkOrderController controller;

    private Button button[];

    public void initializeData(DataCollection collection) {
        controller = new WorkOrderController(collection);
        initializeButton();
        setCellTable();
        tableView.setItems(controller.getWorkOrderList());
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
        initializeButton();
        tableView.setItems(controller.getWorkOrderList());
    }

    private void initializeButton() {
        int size = controller.getWorkOrderList().size();
        if (size > 0) {
            button = new Button[size];
            int index = 0;
            for (int i = 0; i < size; i++) {
                button[i] = new Button();
                button[i].setText("Edit");
                button[i].setOnAction(event -> {
                    controller.showEditPopup(event, size, button);
                    initializeButton();
                    tableView.setItems(controller.getWorkOrderList());
                });
            }
            for (WorkOrder workOrder : controller.getWorkOrderList()) {
                workOrder.setButton(button[index]);
                index++;
            }
        }
    }

    private void setCellTable() {
        workID.setCellValueFactory(new PropertyValueFactory<>("WorkID"));
        from.setCellValueFactory(new PropertyValueFactory<>("BuyOrderID"));
        employeeID.setCellValueFactory(new PropertyValueFactory<>("EmployeeID"));
        productID.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        assignedDate.setCellValueFactory(new PropertyValueFactory<>("AssignedDate"));
        dueDate.setCellValueFactory(new PropertyValueFactory<>("DueDate"));
        status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        action.setCellValueFactory(new PropertyValueFactory<>("Button"));
    }

    public Stage getStage(){
        return (Stage) pane.getScene().getWindow();
    }

}