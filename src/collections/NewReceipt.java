package collections;

import javafx.scene.control.CheckBox;

import java.util.Date;

public class NewReceipt {
    private String buyOrderID;
    private String productName;
    private String productID;
    private int quantity;
    private String date;
    private int price;
    private CheckBox checkbox;

    private Date trueDate;

    public NewReceipt(String buyOrderID, String productName, String productID, int quantity, String date, int price, Date trueDate) {
        this.buyOrderID = buyOrderID;
        this.productName = productName;
        this.productID = productID;
        this.quantity = quantity;
        this.date = date;
        this.price = price;
        this.checkbox = new CheckBox();
        this.trueDate = trueDate;
    }

    public String getBuyOrderID() {
        return buyOrderID;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductID() {
        return productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }

    public int getPrice() {
        return price;
    }

    public CheckBox getCheckbox() {
        return checkbox;
    }

    public Date getTrueDate() {
        return trueDate;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}