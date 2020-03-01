package admin.admin_controller;

import admin.admin_ui.AddEmployeeUI;
import admin.admin_ui.AdminMainUI;
import collections.DataCollection;
import collections.Employee;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

@SuppressWarnings("Duplicates")
public class EmployeeController {
    private DataCollection collection;

    public EmployeeController(DataCollection collection) {
        this.collection = collection;
        this.collection.readEmployeeList();
    }

    public ObservableList<Employee> getEmployeeList() {
        return this.collection.getEmployeeList();
    }

    public void loadMainUI(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_ui/AdminMainUI.fxml"));
            stage.setScene(new Scene(loader.load()));
            AdminMainUI ami = loader.getController();
            ami.initializeData(collection);
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAddUI() {
        try {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_ui/AddEmployeeUI.fxml"));
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setScene(new Scene(loader.load()));
            AddEmployeeUI aei = loader.getController();
            aei.initializeData(collection);
            newStage.setResizable(false);
            newStage.sizeToScene();
            newStage.showAndWait();
            collection.readEmployeeList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}