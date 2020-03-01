package collections;

import button.ViewReceiptButton;
import javafx.scene.control.Button;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Receipt {
    private String id;
    private String totalPrice;
    private String date;
    private String buyOrderID;
    private Button button;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private DecimalFormat decimalFormat = new DecimalFormat("#.00");

    public Receipt(String id, int totalPrice, Date date, String buyOrderID) {
        this.id = id;
        this.totalPrice = decimalFormat.format(totalPrice);
        this.date = dateFormat.format(date);
        this.buyOrderID = buyOrderID;
        this.button = new Button("View");

        this.button.setOnAction(event -> {
            System.out.println("You handled `View` Button.");
            ViewReceiptButton pdb = new ViewReceiptButton(this.id,
                    decimalFormat.format(this.totalPrice), this.date, this.buyOrderID);
            pdb.createAlert();
        });
    }

    public String getID() {
        return id;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getDate() {
        return date;
    }

    public String getBuyOrderID() {
        return buyOrderID;
    }

    public Button getButton() {
        return button;
    }

}