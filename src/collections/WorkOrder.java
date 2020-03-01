package collections;

import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WorkOrder {
    private String workID;
    private String buyOrderID;
    private String employeeID;
    private String employeeName;
    private String productID;
    private String productName;
    private String assignedDate;
    private String dueDate;
    private String status;

    private Button statusButton;

    private String person;
    private Image image;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    WorkOrder(String workID, String employeeID, String productID, String buyOrderID, Date assignedDate,
              Date dueDate, String status, String person, Image image, String employeeName, String productName) {
        this.workID = workID;
        this.buyOrderID = buyOrderID;
        this.employeeID = employeeID;
        this.productID = productID;
        this.assignedDate = dateFormat.format(assignedDate);
        this.dueDate = dateFormat.format(dueDate);
        this.status = status;
        this.person = person;

        this.employeeName = employeeName;
        this.productName = productName;

        this.image = image;
    }

    public String getWorkerName() {
        return employeeName;
    }

    public String getWorkID() {
        return workID;
    }

    public String getBuyOrderID() {
        return buyOrderID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getProductID() {
        return productID;
    }

    public String getAssignedDate() {
        return assignedDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }

    public String getProductName() { return productName; }

    public Button getButton() {
        if (person.equals("admin") && status.equals("waiting")) {
            return statusButton;
        } else if (person.equals("employee") && (!(status.equals("done")))) {
            return statusButton;
        } else {
            return null;
        }
    }

    public Image getImage() {
        return image;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setButton(Button button) {
        this.statusButton = button;
    }

}