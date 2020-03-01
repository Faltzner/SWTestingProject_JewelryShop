package admin.admin_ui;

import admin.admin_controller.EmployeeController;
import collections.DataCollection;
import collections.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@SuppressWarnings("Duplicates")
public class EmployeeUI {
    @FXML
    AnchorPane pane;

    @FXML
    TableView<Employee> tableView;

    @FXML
    TableColumn id, name, numPhone, password, numOfWork, necklace, earrings, bracelet, ring;

    EmployeeController controller;

    public void initializeData(DataCollection collection) {
        controller = new EmployeeController(collection);
        setCellTable();
        tableView.setItems(controller.getEmployeeList());
    }

    @FXML
    public void handleBackButton() {
        System.out.println("You handled `Back` button.");
        controller.loadMainUI(getStage());
    }

    @FXML
    public void handleAddButton() {
        System.out.println("You handled `Add` button.");
        controller.loadAddUI();
        tableView.setItems(controller.getEmployeeList());
    }

    private void setCellTable() {
        id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        numPhone.setCellValueFactory(new PropertyValueFactory<>("NumPhone"));
        password.setCellValueFactory(new PropertyValueFactory<>("Password"));
        numOfWork.setCellValueFactory(new PropertyValueFactory<>("NumOfWork"));
        necklace.setCellValueFactory(new PropertyValueFactory<>("NecklaceSkill"));
        earrings.setCellValueFactory(new PropertyValueFactory<>("EarringsSkill"));
        ring.setCellValueFactory(new PropertyValueFactory<>("RingSkill"));
        bracelet.setCellValueFactory(new PropertyValueFactory<>("BraceletSkill"));
    }

    public Stage getStage() {
        return (Stage) pane.getScene().getWindow();
    }

}