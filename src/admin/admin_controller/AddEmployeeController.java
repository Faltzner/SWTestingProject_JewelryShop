package admin.admin_controller;

import collections.DataCollection;
import collections.Employee;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.text.DecimalFormat;

@SuppressWarnings("Duplicates")
public class AddEmployeeController {
    private DataCollection collection;

    private DecimalFormat employeeFormat = new DecimalFormat("00000");

    private String firstPattern = "[0][8-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]";
    private String secondPattern = "[0][8-9][0-9][-][0-9][0-9][0-9][-][0-9][0-9][0-9][0-9]";

    private String employeeID = "", name = "", password = "", numPhone = "";
    private int necklaceSkill, earringsSkill, braceletSkill, ringSkill;
    private int latestEmployeeID = 0;

    public AddEmployeeController(DataCollection collection) {
        this.collection = collection;
        this.necklaceSkill = 0;
        this.earringsSkill = 0;
        this.braceletSkill = 0;
        this.ringSkill = 0;
        initializeEmployeeID();
    }

    public void setDataFromField(TextField nameField, TextField passwordField, TextField numPhoneField) {
        this.name = nameField.getText();
        this.password = passwordField.getText();
        this.numPhone = numPhoneField.getText();
    }

    public void setSkill(CheckBox necklaceCheck, CheckBox earringsCheck, CheckBox braceletCheck, CheckBox ringCheck) {
        if (necklaceCheck.isSelected()) necklaceSkill = 1;
        if (earringsCheck.isSelected()) earringsSkill = 1;
        if (braceletCheck.isSelected()) braceletSkill = 1;
        if (ringCheck.isSelected()) ringSkill = 1;
    }

    public String dataVerification() {
        System.out.println("New Employee ID: " + employeeID);
        return result();
    }

    private void initializeEmployeeID() {
        collection.readEmployeeList();
        for (Employee employee: collection.getEmployeeList()) {
            int currentID = Integer.parseInt(employee.getID());
            if (currentID > latestEmployeeID) {
                latestEmployeeID = currentID;
            }
        }
        latestEmployeeID++;
        employeeID = employeeFormat.format(latestEmployeeID);
        System.out.println("Employee ID is: " + employeeID);
    }

    private String result() {
        if (name.equals("")) {
            System.out.println("Invalid Data in `Name` field.");
            return "Invalid Name|You didn't filled `Name` field.";
        } else if (password.equals("")) {
            System.out.println("Invalid Data in `Password` field.");
            return "Invalid Password|You didn't filled `Password` field.";
        } else if (numPhone.equals("")) {
            System.out.println("Invalid Data in `Phone Number` field.");
            return "Invalid Phone Number|You didn't filled `Phone Number` field.";
        } else {
            if (password.length() < 8 || password.length() > 10) {
                System.out.println("`Password` length is Invalid.");
                return "Invalid Password|Password's length should be 8-10 characters.";
            } else if (numPhone.length() == 10) {
                if (numPhone.matches(firstPattern)) {
                    collection.addEmployee(employeeID, name, numPhone, password);
                    collection.addSkill(employeeID, necklaceSkill, earringsSkill, braceletSkill, ringSkill);
                } else {
                    System.out.println("Invalid Pattern in `Phone Number` field.");
                    return "Invalid Phone Number|Phone Number should be in Pattern\n\"08xxxxxxxx\" or \"09xxxxxxxx\".";
                }
            } else if (numPhone.length() == 12) {
                if (numPhone.matches(secondPattern)) {
                    numPhone = numPhone.replace("-", "");
                    collection.addEmployee(employeeID, name, numPhone, password);
                    collection.addSkill(employeeID, necklaceSkill, earringsSkill, braceletSkill, ringSkill);
                } else {
                    System.out.println("Invalid Pattern in `Phone Number` field.");
                    return "Invalid Phone Number|Phone Number should be in Pattern\n\"08x-xxx-xxxx\" or \"09x-xxx-xxxx\".";
                }
            } else {
                System.out.println("Invalid Pattern in `Phone Number` field.");
                return "Invalid Pattern|Pattern of Phone Number should be\n\"08xxxxxxxx\" or \"09xxxxxxxx\"\nor \"08x-xxx-xxxx\" or \"09x-xxx-xxxx\".";
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
        alert.setHeaderText("Buy Order Information");
        alert.setContentText("Employee ID:\t\t " + employeeID + "\nName:\t\t\t " + name + "\nPhone Number:\t " + numPhone
                + "\nNecklace Skill:\t\t " + necklaceSkill + "\nEarrings Skill:\t\t " + earringsSkill + "\nBracelet Skill:\t\t "
                + braceletSkill + "\nRing Skill:\t\t\t " + ringSkill);
        alert.showAndWait();

        return alert;
    }

}