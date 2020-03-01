package admin.admin_controller;

import admin.admin_ui.AdminMainUI;
import admin.admin_ui.CheckWorkUI;
import admin.admin_ui.NewWorkOrderUI;
import collections.DataCollection;
import collections.WorkOrder;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

@SuppressWarnings("Duplicates")
public class WorkOrderController {
    private DataCollection collection;

    private String workIDRow = "", buyOrderIDRow = "", employeeIDRow = "", productIDRow = "", assignedDateRow = "", dueDateRow = "";

    public WorkOrderController(DataCollection collection) {
        this.collection = collection;
        this.collection.readWorkList("admin");
    }

    public ObservableList<WorkOrder> getWorkOrderList() {
        return this.collection.getWorkList();
    }

    public void loadMainUI(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_ui/AdminMainUI.fxml"));
            stage.setScene(new Scene(loader.load()));
            AdminMainUI ami = loader.getController();
            ami.initializeData(collection);
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCreateUI() {
        try {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_ui/NewWorkOrderUI.fxml"));
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setScene(new Scene(loader.load()));
            NewWorkOrderUI nwoi = loader.getController();
            nwoi.initializeData(collection);
            newStage.setResizable(false);
            newStage.sizeToScene();
            newStage.showAndWait();
            collection.readWorkList("admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEditPopup(ActionEvent event, int size, Button button[]) {
        Image image = null;
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
                        assignedDateRow = workOrder.getAssignedDate();
                        dueDateRow = workOrder.getDueDate();
                        statusRow = workOrder.getStatus();
                        image = workOrder.getImage();
                        workerName = workOrder.getWorkerName();
                        productName = workOrder.getProductName();
                    }
                }
            }
        }

        workChecking(image, statusRow, workerName, productName);
    }

    private void workChecking(Image image, String statusRow, String workerName, String productName) {
        try {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_ui/CheckWorkUI.fxml"));
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setScene(new Scene(loader.load()));
            CheckWorkUI cwui = loader.getController();
            cwui.initializeData(collection, workIDRow, buyOrderIDRow, employeeIDRow, productName, assignedDateRow
                    , dueDateRow, statusRow, image, workerName);
            newStage.setResizable(false);
            newStage.sizeToScene();
            newStage.showAndWait();
            collection.readWorkList("admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}