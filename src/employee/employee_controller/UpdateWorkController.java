package employee.employee_controller;

import collections.DataCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

@SuppressWarnings("Duplicates")
public class UpdateWorkController {
    private DataCollection collection;

    private Image image;

    private String status = "", path = "", workerName = "", productName = "";
    private String workOrderID = "", orderID = "", employeeID = "", localDate = "", dueDate = "", workStatus = "";

    public UpdateWorkController(DataCollection collection) {
        this.collection = collection;
    }

    public void setData(String workOrderID, String orderID, String employeeID, String productName, String localDate,
                        String dueDate, String workStatus, String workerName) {
        this.workOrderID = workOrderID;
        this.orderID = orderID;
        this.employeeID = employeeID;
        this.localDate = localDate;
        this.dueDate = dueDate;
        this.workStatus = workStatus;
        this.workerName = workerName;
        this.productName = productName;
    }

    public void setTextField(Text work, Text from, Text employee, Text product) {
        work.setText(workOrderID);
        from.setText(orderID);
        employee.setText(workerName);
        product.setText(productName);
    }

    public void chooseFile(Text pathField, Stage stage, ImageView imageView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG Files", "*.jpg"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            this.path = file.getAbsolutePath();
            this.image = new Image(file.toURI().toString());
            imageView.setFitHeight(160);
            imageView.setFitWidth(160);
            imageView.setImage(this.image);
            pathField.setText(file.getName());
        }
    }

    public String dataVerification() {
        System.out.println("Data Verification is Processing...");
        return result();
    }

    public void initializeComboBox(ComboBox<String> statusChoice) {
        try {
            ObservableList<String> statusList = FXCollections.observableArrayList();
            statusList.clear();
            statusList.add("Submit");
            statusList.add("Unsubmit");
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
        } else if (path.equals("") && workStatus.equals("working")) {
            System.out.println("Please Choose `File`.");
            return "Invalid File|You didn't choose `File`.";
        } else {
            if (status.equals("Submit")) {
                String fileType = path.substring(path.indexOf("."));
                workStatus = "waiting";
                collection.updateWorkOrder(workOrderID, workStatus);
                collection.updateWorkOrderPic(image, fileType.substring(1), workOrderID);
            } else if (status.equals("Unsubmit")){
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