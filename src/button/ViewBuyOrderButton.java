package button;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@SuppressWarnings("Duplicates")
public class ViewBuyOrderButton {
    private String id;
    private String assignedDate;
    private String dueDate;
    private String productID;
    private String quantity;
    private String pricePerUnit;
    private String status;
    private String assignedNumber;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ViewBuyOrderButton(String id, String assignedDate, String dueDate, String productID, int quantity,
                              String pricePerUnit, int status, int assignedNumber) {
        this.id = id;
        this.assignedDate = dateFormat.format(assignedDate);
        this.dueDate = dueDate;
        this.productID = productID;
        this.quantity = Integer.toString(quantity);
        this.pricePerUnit = pricePerUnit;
        this.status = Integer.toString(status);
        this.assignedNumber = Integer.toString(assignedNumber);
    }

    public void showViewPopUp() {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION, "Information", ButtonType.OK);
        dialog.getDialogPane().setMaxWidth(Region.USE_PREF_SIZE);
        dialog.setTitle("Information Dialog");
        dialog.setHeaderText("Buy Order Information");
        dialog.setContentText("ID:\t\t\t\t  " + id + "\nAssigned Date:\t\t  " + assignedDate + "\nDue Date:\t\t\t  "
                + dueDate + "\nProduct ID:\t\t  " + productID + "\nQuantity:\t\t\t  " + quantity + "\nPrice / Unit:\t\t  "
                + pricePerUnit + "\nAssigned Number:\t  " + assignedNumber + "\nStatus:\t\t\t  " + status);
        dialog.showAndWait();
        if (dialog.getResult() == ButtonType.OK) {
            dialog.close();
        }
    }

}