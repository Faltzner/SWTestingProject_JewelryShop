package employee.employee_controller;

import collections.DataCollection;
import employee.employee_ui.EmployeeProductUI;
import employee.employee_ui.EmployeeWorkUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

@SuppressWarnings("Duplicates")
public class EmployeeMainController {
    private DataCollection collection;

    private String employeeID;

    public EmployeeMainController(DataCollection collection, String employeeID) {
        this.collection = collection;
        this.employeeID = employeeID;
    }

    public void loadWorkOrderUI(Stage stage) {
        System.out.println("Load `Work Order` UI...");
        if (collection.getConnection() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/employee/employee_ui/EmployeeWorkUI.fxml"));
                stage.setScene(new Scene(loader.load()));
                EmployeeWorkUI woei = loader.getController();
                woei.initializeData(collection, employeeID);
                stage.setResizable(false);
                stage.sizeToScene();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadProductUI(Stage stage) {
        System.out.println("Load `Product` UI...");
        if (collection.getConnection() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/employee/employee_ui/EmployeeProductUI.fxml"));
                stage.setScene(new Scene(loader.load()));
                EmployeeProductUI pei = loader.getController();
                pei.initializeData(collection, employeeID);
                stage.setResizable(false);
                stage.sizeToScene();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void logout(Stage stage) {
        System.out.println("`Logout` Successful!");
        if (collection.getConnection() != null) {
            stage.close();
        }
    }

}