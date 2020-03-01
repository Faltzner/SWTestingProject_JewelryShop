package collections;

import button.ViewBuyOrderButton;
import javafx.scene.control.Button;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BuyOrder {
    private String id;
    private String assignedDate;
    private String dueDate;
    private String productID;
    private int quantity;
    private String pricePerUnit;
    private int status;
    private int assignedNum;
    private Button button;

    private Date trueDate;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private DecimalFormat decimalFormat = new DecimalFormat("#.00");

    BuyOrder(String id, String productID, int quantity, int pricePerUnit, Date assignedDate, Date dueDate,
             int assignedNum, int status) {
        this.id = id;
        this.assignedDate = dateFormat.format(assignedDate);
        this.dueDate = dateFormat.format(dueDate);
        this.productID = productID;
        this.quantity = quantity;
        this.pricePerUnit = decimalFormat.format(pricePerUnit);
        this.status = status;
        this.assignedNum = assignedNum;

        this.trueDate = assignedDate;

        this.button = new Button("View");

        this.button.setOnAction(event -> {
            System.out.println("You handled `View` Button.");
            ViewBuyOrderButton ebob = new ViewBuyOrderButton(this.id, this.assignedDate, this.dueDate, this.productID,
                    this.quantity, this.pricePerUnit, this.status, this.assignedNum);
            ebob.showViewPopUp();
        });
    }

    public String getID() {
        return id;
    }

    public String getAssignedDate() {
        return assignedDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getProductID() {
        return productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPricePerUnit() {
        return pricePerUnit;
    }

    public int getStatus() {
        return status;
    }

    public int getAssignedNum() { return assignedNum; }

    public Button getButton() {
        return button;
    }

    public Date getDate() {
        return trueDate;
    }

}