package admin.admin_controller;

import collections.DataCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

@SuppressWarnings("Duplicates")
public class CheckWorkController {
    private DataCollection collection;

    private Image image;

    private String status = "";
    private String workOrderID = "", orderID = "", employeeID = "", productName = "", localDate = "", dueDate = "", workStatus = "", workerName = "";

    public CheckWorkController(DataCollection collection) {
        this.collection = collection;
    }

    public void setData(String workOrderID, String orderID, String employeeID, String productName, String localDate
            , String dueDate, String workStatus, Image image, String workerName) {
        this.workOrderID = workOrderID;
        this.orderID = orderID;
        this.employeeID = employeeID;
        this.productName = productName;
        this.localDate = localDate;
        this.dueDate = dueDate;
        this.workStatus = workStatus;
        this.image = image;
        this.workerName = workerName;
    }

    public void setField(Text work, Text from, Text employee, Text product, ImageView imageView) {
        work.setText(workOrderID);
        from.setText(orderID);
        employee.setText(workerName);
        product.setText(productName);
        imageView.setImage(image);
    }

    public String dataVerification() {
        System.out.println("Data Verification is Processing...");
        return result();
    }

    public void initializeComboBox(ComboBox<String> statusChoice) {
        try {
            ObservableList<String> statusList = FXCollections.observableArrayList();
            statusList.clear();
            statusList.add("Pass");
            statusList.add("Not Pass");
            statusChoice.setItems(statusList);
            statusChoice.valueProperty().addListener((obsProduct, oldValProduct, newValProduct) -> {
                if (newValProduct == null) {
                    this.status = "";
                } else {
                    this.status = newValProduct;
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private String result() {
        if (status.equals("")) {
            System.out.println("Please Choose `Status`.");
            return "Invalid Status|You didn't choose `Status`.";
        } else {
            if (status.equals("Pass")) {
                workStatus = "done";
                collection.updateWorkOrder(workOrderID, workStatus);
                collection.updateEmployee(employeeID, "-1");
                collection.updateBuyOrderStatus(orderID);
            } else if (status.equals("Not Pass")){
                workStatus = "working";
                collection.updateWorkOrder(workOrderID, workStatus);
            }
            return "success";
        }
    }

    public Alert createWarningAlert(String messageStr) {
        String message[] = messageStr.split("[|]");

        Alert alert = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText(message[0]);
        alert.setContentText(message[1]);
        alert.showAndWait();

        return alert;
    }

    public Alert createInformationAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Information", ButtonType.OK);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Work Order Information");
        alert.setContentText("Work ID:\t\t  " + workOrderID + "\nFrom:\t\t  " + orderID + "\nEmployee:\t  " + workerName
                + "\nProduct:\t\t  " + productName + "\nAssigned Date:  " + localDate + "\nDue Date:\t\t  " + dueDate
                + "\nStatus:\t\t  " + workStatus);
        alert.showAndWait();

        return alert;
    }

}