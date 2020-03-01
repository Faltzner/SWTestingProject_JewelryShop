package login_gui;

import admin.admin_ui.AdminMainUI;
import collections.DataCollection;
import collections.Employee;
import employee.employee_ui.EmployeeMainUI;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("Duplicates")
public class LoginPageController {
    private DataCollection collection;

    private AtomicBoolean isUserFound = new AtomicBoolean(false);
    private AtomicBoolean isCorrectPassword = new AtomicBoolean(false);

    LoginPageController() {
        try {
            collection = new DataCollection();
            collection.readEmployeeList();
        } catch (Exception e) {
            Platform.exit();
        }
    }

    public DataCollection getCollection() {
        return collection;
    }

    void loadAdminPage(Stage stage) {
        try {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_ui/AdminMainUI.fxml"));
            newStage.setScene(new Scene(loader.load()));
            AdminMainUI ami = loader.getController();
            ami.initializeData(collection);
            newStage.setResizable(false);
            newStage.sizeToScene();
            stage.hide();
            newStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadEmployeePage(Stage stage, String username) {
        try {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/employee/employee_ui/EmployeeMainUI.fxml"));
            newStage.setScene(new Scene(loader.load()));
            EmployeeMainUI emi = loader.getController();
            emi.initializeData(collection, username);
            newStage.setResizable(false);
            newStage.sizeToScene();
            stage.hide();
            newStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String loginSession(String username, String password) {
        isUserFound.set(false);
        isCorrectPassword.set(false);
        if (!(username.equals("") && password.equals(""))) {
            ObservableList<Employee> empList = collection.getEmployeeList();
            if (username.equals("")) {
                System.out.println("You weren't fill in username field.");
                return "Field Blank/You weren't fill in username field.";
            } else if (password.equals("")) {
                System.out.println("You weren't fill in password field.");
                return "Field Blank/You weren't fill in password field.";
            } else {
                if (!empList.isEmpty()) {
                    empList.forEach((employee)-> {
                        if (employee.getID().equals(username)) {
                            if (employee.getPassword().equals(password)) {
                                isCorrectPassword.set(true); }
                            isUserFound.set(true);
                        }
                    });
                    if (!isUserFound.get()) {
                        if (username.equals("admin") && password.equals("admin")) {
                            return "admin";
                        } else if (username.equals("admin") && !password.equals("admin")) {
                            System.out.println("Admin's password is incorrect");
                            return "Incorrect/Your username or password incorrect.";
                        } else {
                            System.out.println("User not found.");
                            return "Not Found/User not found.";
                        }
                    } else if (isUserFound.get() && isCorrectPassword.get()) {
                        return "employee";
                    } else {
                        System.out.println("Password Incorrect.");
                        return "Incorrect/Your username or password incorrect.";
                    }
                } else {
                    System.out.println("Employee database was blank.");
                    return "Database Blank/Employee database was blank.";
                }
            }
        } else {
            System.out.println("You were not fill any field.");
            return "Field Blank/You were not fill any field.";
        }
    }

    Alert createWarningAlert(String messageStr) {
        String message[] = messageStr.split("/");

        Alert alert = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
        alert.getDialogPane().setMaxWidth(Region.USE_PREF_SIZE);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText(message[0]);
        alert.setContentText(message[1]);
        alert.showAndWait();

        return alert;
    }

    void setPage(Stage stage, TextField usernameField, TextField passwordField) {
        usernameField.setText("");
        passwordField.setText("");
        stage.setWidth(1200);
        stage.setHeight(750);
    }

}