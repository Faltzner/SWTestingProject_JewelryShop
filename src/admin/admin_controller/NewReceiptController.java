package admin.admin_controller;

import collections.BuyOrder;
import collections.DataCollection;
import collections.NewReceipt;
import collections.Product;
import collections.Receipt;
import file_writer.ReceiptWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Integer.parseInt;

@SuppressWarnings("Duplicates")
public class NewReceiptController {
    private DataCollection collection;

    private ObservableList<NewReceipt> tableReceipt;
    private List<String> previousReceipt, newReceipt;

    private DecimalFormat receiptFormat = new DecimalFormat("00000");

    private String receiptID;
    private int latestReceiptID = 0;

    public NewReceiptController(DataCollection collection) {
        this.collection = collection;
        previousReceipt = new ArrayList<>();
        newReceipt = new CopyOnWriteArrayList<>();
        tableReceipt = FXCollections.observableArrayList();
        initializeList();
        initializeReceiptID();
        initializeTable();
        System.out.println("Table Receipt Size: " + tableReceipt.size());
    }

    public ObservableList<NewReceipt> getNewReceiptList() {
        return this.tableReceipt;
    }

    private void initializeList() {
        collection.readBuyOrderList();
        collection.readReceiptList();
        previousReceipt.clear();
        newReceipt.clear();
        for (Receipt receipt: collection.getReceiptList()) {
            previousReceipt.add(receipt.getBuyOrderID());
        }
        for (BuyOrder buyOrder: collection.getBuyOrderList()) {
            int completeProducts = buyOrder.getQuantity() - buyOrder.getStatus();
            if (completeProducts == 0) {
                newReceipt.add(buyOrder.getID());
            }
        }
        newReceipt.removeAll(previousReceipt);
        System.out.println("Buy Order List: " + newReceipt);
    }

    private void initializeReceiptID() {
        try {
            for (Receipt receipt: collection.getReceiptList()) {
                int currentID = parseInt(receipt.getID());
                if (currentID > latestReceiptID) {
                    latestReceiptID = currentID;
                }
            }
            latestReceiptID++;
            receiptID = receiptFormat.format(latestReceiptID);
            System.out.println("Receipt ID is: " + receiptID);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void initializeTable() {
        for (BuyOrder buyOrder: collection.getBuyOrderList()) {
            for (String string: newReceipt) {
                if (string.equals(buyOrder.getID())) {
                    addObject(buyOrder);
                    break;
                }
            }
        }
    }

    private void addObject(BuyOrder order) {
        String productID = order.getProductID();
        collection.readProductList();
        String name = "";
        for (Product product: collection.getProductList()) {
            if (productID.equals(product.getID())) {
                name = product.getName();
                break;
            }
        }
        tableReceipt.add(new NewReceipt(order.getID(), capitalize(name), productID, order.getQuantity(),
                order.getAssignedDate(), (int) Double.parseDouble(order.getPricePerUnit()), order.getDate()));
    }

    public String dataVerification() {
        ObservableList<NewReceipt> getData = FXCollections.observableArrayList();
        for (NewReceipt newReceipt: tableReceipt) {
            if (newReceipt.getCheckbox().isSelected()) {
                getData.add(newReceipt);
            }
        }
        if (getData.size() == 0) {
            return "Choice didn't selected.-You didn't choose any choice.\nPlease choose choice(s).";
        } else {
            System.out.println("You choose data.");
            for (NewReceipt nrc: getData) {
                try {
                    System.out.println("Receipt ID is: " + receiptID);
                    ReceiptWriter writer = new ReceiptWriter(nrc, receiptID);
                    writer.createReceipt();
                    collection.createReceipt(receiptID, nrc.getQuantity(), nrc.getPrice()
                            , new Date(nrc.getTrueDate().getTime()), nrc.getBuyOrderID());
                } catch (Exception e) {
                    return "Cannot create file.-Folder `gemshop` doesn't exist.";
                }
                System.out.println("Create: " + receiptID + " Order ID: " + nrc.getBuyOrderID());

                int increaseID = Integer.parseInt(receiptID);
                increaseID++;
                receiptID = receiptFormat.format(increaseID);
            }
            return "success";
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
        alert.getDialogPane().setMaxWidth(Region.USE_PREF_SIZE);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText(message[0]);
        alert.setContentText(message[1]);
        alert.showAndWait();

        return alert;
    }

    public Alert createInformationAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Information", ButtonType.OK);
        alert.getDialogPane().setMaxWidth(Region.USE_PREF_SIZE);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Successful!");
        alert.setContentText("Create receipt successful!\nNon-error detected.");
        alert.showAndWait();

        return alert;
    }

}