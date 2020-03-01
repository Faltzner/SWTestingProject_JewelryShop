package employee.employee_controller;

import collections.DataCollection;
import collections.WorkOrder;
import employee.employee_ui.EmployeeMainUI;
import employee.employee_ui.UpdateWorkUI;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("Duplicates")
public class EmployeeWorkController {
    private DataCollection collection;

    private String id;
    private String workIDRow = "", buyOrderIDRow = "", employeeIDRow = "", productName = "", assignedDateRow = "", dueDateRow = "";

    public EmployeeWorkController(DataCollection collection, String employeeID) {
        this.collection = collection;
        this.id = employeeID;
        this.collection.readWorkList(this.id);
    }

    public ObservableList<WorkOrder> getWorkList() {
        return this.collection.getWorkList();
    }

    public void loadMainUI(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/employee/employee_ui/EmployeeMainUI.fxml"));
            stage.setScene(new Scene(loader.load()));
            EmployeeMainUI emi = loader.getController();
            emi.initializeData(collection, id);
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEditPopup(ActionEvent event, int size, Button[] button) {
        String statusRow = "";
        String workerName = "";
        String productName = "";
        for (int i = 0; i < size; i++) {
            if (event.getSource() == button[i]) {
                for (WorkOrder workOrder: collection.getWorkList()) {
                    if (workOrder.getButton() == button[i]) {
                        workIDRow = workOrder.getWorkID();
                        employeeIDRow = workOrder.getEmployeeID();
                        buyOrderIDRow = workOrder.getBuyOrderID();
                        productName = workOrder.getProductName();
                        assignedDateRow = workOrder.getAssignedDate();
                        dueDateRow = workOrder.getDueDate();
                        statusRow = workOrder.getStatus();
                        workerName = workOrder.getWorkerName();
                    }
                }
            }
        }

        switch (statusRow) {
            case "assigned":
                acceptProcess();
                break;
            case "working":
            case "waiting":
                workingProcess(statusRow, workerName, productName);
                break;
            default:
                collection.readWorkList(employeeIDRow);
                break;
        }
    }

    private void workingProcess(String statusRow, String workerName, String productName) {
        try {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/employee/employee_ui/UpdateWorkUI.fxml"));
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setScene(new Scene(loader.load()));
            UpdateWorkUI uwui = loader.getController();
            uwui.initializeData(collection, workIDRow, buyOrderIDRow, employeeIDRow, productName, assignedDateRow
                    , dueDateRow, statusRow, workerName);
            newStage.setResizable(false);
            newStage.sizeToScene();
            newStage.showAndWait();
            collection.readWorkList(employeeIDRow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void acceptProcess() {
        List<String> choices = new ArrayList<>();
        choices.clear();
        choices.add("Accept");
        choices.add("Decline");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("--------", choices);
        dialog.getDialogPane().setMaxWidth(Region.USE_PREF_SIZE);
        dialog.setTitle("Edit Dialog");
        dialog.setHeaderText("Will you accept this work?\n\nWork ID:\t\t  " + workIDRow + "\nFrom:\t\t  " + buyOrderIDRow
                + "\nEmployee ID:\t  " + employeeIDRow + "\nProduct ID:\t  " + productName + "\nAssigned Date:  "
                + assignedDateRow + "\nDue Date:\t\t  " + dueDateRow);
        dialog.setContentText("Status: ");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String changingStatus = result.get();
            System.out.println("Your choice: " + changingStatus);
            if (changingStatus.equals("Accept")) {
                collection.updateWorkOrder(workIDRow, "working");
                System.out.println("Accept.");
            } else if (changingStatus.equals("Decline")) {
                collection.deleteWorkOrder(workIDRow);
                collection.updateEmployee(employeeIDRow, "-1");
                collection.updateBuyOrderAssigned(buyOrderIDRow, "-1");
                System.out.println("Decline.");
            }
            this.collection.readWorkList(employeeIDRow);
            dialog.close();
        } else {
            dialog.close();
        }
    }

}