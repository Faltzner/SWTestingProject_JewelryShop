package employee.employee_controller;

import collections.DataCollection;
import collections.Product;
import employee.employee_ui.EmployeeMainUI;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

@SuppressWarnings("Duplicates")
public class EmployeeProductController {
    private DataCollection collection;

    private String id;

    public EmployeeProductController(DataCollection collection, String employeeID) {
        this.collection = collection;
        this.id = employeeID;
        this.collection.readProductList();
    }

    public ObservableList<Product> getProductList() {
        return this.collection.getProductList();
    }

    public void loadMainUI(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/employee/employee_ui/EmployeeMainUI.fxml"));
            stage.setScene(new Scene(loader.load()));
            EmployeeMainUI emi = loader.getController();
            emi.initializeData(collection, id);
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}