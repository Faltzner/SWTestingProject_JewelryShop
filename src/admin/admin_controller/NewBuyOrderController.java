package admin.admin_controller;

import collections.BuyOrder;
import collections.DataCollection;
import collections.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;

@SuppressWarnings("Duplicates")
public class NewBuyOrderController {
    private DataCollection collection;

    private ObservableList<String> productList;

    private DecimalFormat buyOrderFormat = new DecimalFormat("00000");

    private String buyOrderID = "", type = "", product = "", quantity = "", price = "";
    private String productID = "";
    private LocalDate dueDate, localDate;
    private int latestBuyOrderID = 0;

    public NewBuyOrderController(DataCollection collection) {
        this.collection = collection;
        initializeBuyOrderID();
    }

    public void setDataFromField(TextField price, TextField quantity, DatePicker date) {
        this.price = price.getText();
        this.quantity = quantity.getText();
        dueDate = date.getValue();
        localDate = LocalDate.now();
    }

    public String dataVerification() {
        System.out.println("Buy Order ID: " + buyOrderID);
        return result();
    }

    private void initializeBuyOrderID() {
        collection.readBuyOrderList();
        for (BuyOrder buyOrder : collection.getBuyOrderList()) {
            int currentID = Integer.parseInt(buyOrder.getID());
            if (currentID > latestBuyOrderID) {
                latestBuyOrderID = currentID;
            }
        }
        latestBuyOrderID++;
        buyOrderID = buyOrderFormat.format(latestBuyOrderID);
        System.out.println("Buy Order ID is: " + buyOrderID);
    }

    public void initializeComboBox(ComboBox<String> typeChoice, ComboBox<String> productChoice) {
        try {
            collection.readProductList();
            ObservableList<String> typeList = FXCollections.observableArrayList();
            typeList.clear();
            for (Product product : collection.getProductList()) {
                if (!typeList.contains(capitalize(product.getType()))) {
                    typeList.add(capitalize(product.getType()));
                }
            }
            System.out.println("Size of Type List: " + typeList.size());
            typeChoice.setItems(typeList);
            productChoice.setItems(FXCollections.observableArrayList("--Please Select Type--"));
            typeChoice.valueProperty().addListener((obsType, oldValType, newValType) -> {
                if (newValType != null) {
                    this.type = newValType.toLowerCase();
                    productList = FXCollections.observableArrayList();
                    productList.clear();
                    for (Product product : collection.getProductList()) {
                        if (this.type.equals(product.getType())) {
                            productList.add(capitalize(product.getName()));
                        }
                    }
                    System.out.println("Size of Product List: " + productList.size());
                    productChoice.setItems(productList);
                    productChoice.valueProperty().addListener((obsProduct, oldValProduct, newValProduct) -> {
                        if (newValProduct == null) {
                            this.product = "";
                            this.productID = "";
                        } else {
                            this.product = newValProduct.toLowerCase();
                            for (Product product: collection.getProductList()) {
                                if (product.getName().equals(this.product)) {
                                    this.productID = product.getID();
                                }
                            }
                        }
                    });
                } else {
                    this.type = "";
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private String result() {
        if (type.equals("")) {
            System.out.println("Please Choose `Type`.");
            return "Invalid Type-You didn't choose `Type`.";
        } else if (product.equals("") || type == null) {
            System.out.println("Please Choose `Product`.");
            return "Invalid Product-You didn't choose `Product`.";
        } else if (price.equals("")) {
            System.out.println("Please Filled `Price` Field.");
            return "Invalid Price-You didn't filled `Price` field.";
        } else if (quantity.equals("")) {
            System.out.println("Please Filled `Quantity` Field.");
            return "Invalid Quantity-You didn't filled `Quantity` field.";
        } else if (dueDate == null) {
            System.out.println("Please Choose `Date`.");
            return "Invalid Date-You didn't choose `Date`.";
        } else {
            try {
                int priceNum = Integer.parseInt(price);
                int quantityNum = Integer.parseInt(quantity);
                int cost = Math.multiplyExact(priceNum, quantityNum);
                System.out.println(cost);
                if (priceNum <= 0 && quantityNum <= 0) {
                    System.out.println("Invalid Data in `Price` & `Quantity` field.");
                    return "Invalid Data-Invalid Data in `Price` & `Quantity` field.";
                } else if (priceNum <= 0) {
                    System.out.println("Invalid Data in `Price` field");
                    return "Invalid Price-Invalid Data in `Price` field.";
                } else if (quantityNum <= 0) {
                    System.out.println("Invalid Data in `Quantity` field.");
                    return "Invalid Quantity-Invalid Data in `Quantity` field.";
                } else if (dueDate.isBefore(localDate) || dueDate.isEqual(localDate)) {
                    System.out.println("You cannot choose this `Date`.");
                    return "Invalid Date-You cannot choose this `Date`.";
                } else {
                    System.out.println("Type: " + type + "\nProduct: " + product);
                    collection.createBuyOrder(buyOrderID, productID, quantityNum, priceNum, Date.valueOf(localDate),
                            Date.valueOf(dueDate), 0, 0);
                    return "success";
                }
            } catch (ArithmeticException e) {
                System.out.println("Integer Overflow.");
                return "Invalid Value-Result of `Price` multiply by `Quantity` is too much.\nTry again.";
            } catch (NumberFormatException e) {
                System.out.println("Cannot Convert `STRING` to `INTEGER`.");
                return "Invalid Data-Cannot convert data from `Price` or `Quantity` field.";
            }
        }
    }

    private String capitalize(String str) {
        String words[] = str.split(" ");
        for (int i = 0; i < words.length; i++) {
            if (!(words[i].toUpperCase().equals("MADE") || words[i].toUpperCase().equals("FROM"))) {
                words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
            }
        }
        return String.join(" ", words);
    }

    public Alert createWarningAlert(String messageStr) {
        String message[] = messageStr.split("-");

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
        alert.setHeaderText("Buy Order Information");
        alert.setContentText("ID:\t\t\t " + buyOrderID + "\nProduct ID:\t " + productID + "\nType:\t\t " + capitalize(type)
                + "\nProduct:\t\t " + capitalize(product) + "\nPrice:\t\t " + price + "\nQuantity:\t\t " + quantity +
                "\nAssigned Date: " + localDate + "\nDue Date:\t\t " + dueDate);
        alert.showAndWait();

        return alert;
    }

}