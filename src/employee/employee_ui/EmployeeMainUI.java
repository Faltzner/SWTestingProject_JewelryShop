package employee.employee_ui;

import collections.DataCollection;
import employee.employee_controller.EmployeeMainController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@SuppressWarnings("Duplicates")
public class EmployeeMainUI {
    @FXML
    AnchorPane pane;

    @FXML
    Button workOrder, product, logout;

    @FXML
    Text userID;

    private EmployeeMainController controller;

    public void initializeData(DataCollection collection, String string) {
        this.userID.setText(string);
        controller = new EmployeeMainController(collection, string);
    }

    @FXML
    public void handleWorkOrderButton() {
        System.out.println("You handled `Work Order` button.");
        controller.loadWorkOrderUI(getStage());
    }

    @FXML
    public void handleProductButton() {
        System.out.println("You handled `Product` button.");
        controller.loadProductUI(getStage());
    }

    @FXML
    public void handleLogoutButton() {
        System.out.println("You handled `Logout` button.");
        controller.logout(getStage());
    }

    private Stage getStage() {
        return (Stage) pane.getScene().getWindow();
    }

}