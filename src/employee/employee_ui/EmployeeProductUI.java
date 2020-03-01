package employee.employee_ui;

import collections.DataCollection;
import collections.Product;
import employee.employee_controller.EmployeeProductController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@SuppressWarnings("Duplicates")
public class EmployeeProductUI {
    @FXML
    AnchorPane pane;

    @FXML
    private TableView<Product> tableView;

    @FXML
    TableColumn productID, name, type, description, rank, picture;

    private EmployeeProductController controller;

    public void initializeData(DataCollection collection, String string) {
        controller = new EmployeeProductController(collection, string);
        setCellTable();
        tableView.setItems(controller.getProductList());
    }

    @FXML
    public void handleBackButton() {
        System.out.println("You handled `Back` button.");
        controller.loadMainUI(getStage());
    }

    private void setCellTable() {
        productID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        rank.setCellValueFactory(new PropertyValueFactory<>("Rank"));
        picture.setCellValueFactory(new PropertyValueFactory<>("Button"));
    }

    public Stage getStage(){
        return (Stage) pane.getScene().getWindow();
    }

}