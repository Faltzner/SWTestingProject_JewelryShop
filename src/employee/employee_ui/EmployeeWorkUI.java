package employee.employee_ui;

import collections.DataCollection;
import collections.WorkOrder;
import employee.employee_controller.EmployeeWorkController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@SuppressWarnings("Duplicates")
public class EmployeeWorkUI {
    @FXML
    AnchorPane pane;

    @FXML
    TableView<WorkOrder> tableView;

    @FXML
    TableColumn workID, productID, assignedDate, dueDate, status, acceptButton, action;

    private EmployeeWorkController controller;

    private Button button[];

    public void initializeData(DataCollection collection, String employeeID) {
        controller = new EmployeeWorkController(collection, employeeID);
        initializeButton();
        setCellTable();
        tableView.setItems(controller.getWorkList());
    }

    @FXML
    public void handleBackButton() {
        System.out.println("You handled `Back` button.");
        controller.loadMainUI(getStage());
    }

    private void initializeButton() {
        int size = controller.getWorkList().size();
        if (size > 0) {
            button = new Button[size];
            int index = 0;
            for (int i = 0; i < size; i++) {
                button[i] = new Button();
                button[i].setText("Edit");
                button[i].setOnAction(event -> {
                    controller.showEditPopup(event, size, button);
                    this.initializeButton();
                    tableView.setItems(controller.getWorkList());
                });
            }
            for (WorkOrder workOrder : controller.getWorkList()) {
                workOrder.setButton(button[index]);
                index++;
            }
        }
    }

    private void setCellTable() {
        workID.setCellValueFactory(new PropertyValueFactory<>("WorkID"));
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