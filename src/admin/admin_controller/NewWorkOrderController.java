package admin.admin_controller;

import collections.BuyOrder;
import collections.DataCollection;
import collections.Employee;
import collections.Product;
import collections.WorkOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;

import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("Duplicates")
public class NewWorkOrderController {
    private DataCollection collection;

    private ObservableList<String> employeeList;

    private DecimalFormat buyOrderFormat = new DecimalFormat("0000");
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String workOrderID = "", orderID = "", productID = "", rank = "", type = "", product = "", employeeID = "";
    private LocalDate dueDate, localDate, dueOrderDate;
    private int latestWorkID = 0;

    public NewWorkOrderController(DataCollection collection) {
        this.collection = collection;
        initializeWorkOrderID();
    }

    public void setDateAndLocalDate(DatePicker datePicker) {
        dueDate = datePicker.getValue();
        localDate = LocalDate.now();
    }

    public String dataVerification() {
        System.out.println("Employee ID: " + employeeID);
        return result();
    }

    private void initializeWorkOrderID() {
        try {
            collection.readWorkList("admin");
            for (WorkOrder workOrder : collection.getWorkList()) {
                String getID = workOrder.getWorkID().replace("w","0");
                int currentID = Integer.parseInt(getID);
                if (currentID > latestWorkID) {
                    latestWorkID = currentID;
                }
            }
            latestWorkID++;
            workOrderID = "w" + buyOrderFormat.format(latestWorkID);
            System.out.println("Work Order ID is: " + workOrderID);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void initializeComboBox(ComboBox<String> fromChoice, ComboBox<String> employeeChoice, Text typeText, Text productText) {
        try {
            collection.readBuyOrderList();
            ObservableList<String> fromList = FXCollections.observableArrayList();
            fromList.clear();
            for (BuyOrder buyOrder: collection.getBuyOrderList()) {
                int amount = buyOrder.getQuantity() - buyOrder.getAssignedNum();
                if (amount > 0) {
                    fromList.add(buyOrder.getID() + " ---- Order Left: " + amount);
                }
            }
            System.out.println("Size of Order List: " + fromList.size());
            fromChoice.setItems(fromList);
            employeeChoice.setItems(FXCollections.observableArrayList("--Please Select Order--"));
            fromChoice.valueProperty().addListener((obsOrder, oldValOrder, newValOrder) -> {
                if (newValOrder != null) {
                    this.orderID = newValOrder.substring(0, 5);
                    System.out.println("Order ID: " + this.orderID);
                    for (BuyOrder buyOrder: collection.getBuyOrderList()) {
                        if (orderID.equals(buyOrder.getID())) {
                            productID = buyOrder.getProductID();
                            dueOrderDate = LocalDate.parse(buyOrder.getDueDate(), dateTimeFormatter);
                        }
                    }
                    System.out.println("Product ID: " + productID + " -- Due Order Date: " + dueOrderDate);

                    collection.readProductList();
                    for (Product product: collection.getProductList()) {
                        if (productID.equals(product.getID())) {
                            this.type = product.getType();
                            this.product = product.getName();
                            this.rank = Integer.toString(product.getRank());
                        }
                    }
                    System.out.println("Type: " + capitalize(this.type) + " -- Product: " + capitalize(this.product)
                            + " -- Rank: " + this.rank);
                    typeText.setText(capitalize(this.type));
                    productText.setText(capitalize(this.product));

                    collection.readEmployeeList();
                    employeeList = FXCollections.observableArrayList();
                    employeeList.clear();
                    if (this.rank.equals("1")) {
                        switch (type) {
                            case "necklace":
                                for (Employee employee : collection.getEmployeeList()) {
                                    if (employee.getNecklaceSkill().equals("1")) {
                                        employeeList.add(employee.getID() + " Num of Work: "
                                                + Integer.toString(employee.getNumOfWork()));
                                    }
                                }
                                break;
                            case "earrings":
                                for (Employee employee : collection.getEmployeeList()) {
                                    if (employee.getEarringsSkill().equals("1")) {
                                        employeeList.add(employee.getID() + " Num of Work: "
                                                + Integer.toString(employee.getNumOfWork()));
                                    }
                                }
                                break;
                            case "ring":
                                for (Employee employee : collection.getEmployeeList()) {
                                    if (employee.getRingSkill().equals("1")) {
                                        employeeList.add(employee.getID() + " Number of Work: "
                                                + Integer.toString(employee.getNumOfWork()));
                                    }
                                }
                                break;
                            case "bracelet":
                                for (Employee employee : collection.getEmployeeList()) {
                                    if (employee.getBraceletSkill().equals("1")) {
                                        employeeList.add(employee.getID() + " Num of Work: "
                                                + Integer.toString(employee.getNumOfWork()));
                                    }
                                }
                                break;
                        }
                    } else if (this.rank.equals("0")) {
                        for (Employee employee : collection.getEmployeeList()) {
                            employeeList.add(employee.getID() + " Num of Work: "
                                    + Integer.toString(employee.getNumOfWork()));
                        }
                    }
                    System.out.println("Size of Employee List: " + employeeList.size());
                    employeeChoice.setItems(employeeList);
                    employeeChoice.valueProperty().addListener((obsEmp, oldValEmp, newValEmp) -> {
                        if (newValEmp == null) {
                            this.employeeID = "";
                        } else {
                            this.employeeID = newValEmp.substring(0, 5);
                        }
                    });
                } else {
                    this.orderID = "";
                }
                });
            } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private String result() {
        if (orderID.equals("")) {
            System.out.println("Please Choose `Order ID`.");
            return "Invalid Order-You didn't choose `Order ID`.";
        } else if (employeeID.equals("") || orderID == null) {
            System.out.println("Please Choose `Employee`.");
            return "Invalid Employee-You didn't choose `Employee`.";
        } else if (dueDate == null){
            System.out.println("Please Choose `Date`.");
            return "Invalid Date-You didn't choose `Date`.";
        } else {
            if (dueDate.isBefore(localDate) || dueDate.isAfter(dueOrderDate)) {
                System.out.println("You cannot choose this `Date`.");
                return "Invalid Date-You cannot choose this `Date`.";
            } else {
                System.out.println("From: " + orderID + "\nEmployee: " + employeeID);
                collection.createWorkOrder(workOrderID, orderID, employeeID, productID, Date.valueOf(localDate),
                        Date.valueOf(dueDate), "assigned");
                collection.updateBuyOrderAssigned(orderID, "1");
                collection.updateEmployee(employeeID, "1");
                return "success";
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
        alert.setHeaderText("Work Order Information");
        alert.setContentText("Work ID:\t\t  " + workOrderID + "\nFrom:\t\t  " + orderID + "\nEmployee ID:\t  " + employeeID
                + "\nProduct ID:\t  " + productID + "\nAssigned Date:  " + localDate + "\nDue Date:\t\t  " + dueDate);
        alert.showAndWait();

        return alert;
    }

}