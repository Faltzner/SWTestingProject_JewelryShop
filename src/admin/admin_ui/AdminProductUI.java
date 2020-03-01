package admin.admin_ui;

import admin.admin_controller.AdminProductController;
import collections.DataCollection;
import collections.Product;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@SuppressWarnings("Duplicates")
public class AdminProductUI {
    @FXML
    AnchorPane pane;

    @FXML
    TableView<Product> tableView;

    @FXML
    TableColumn productID, name, type, description, rank, picture;

    private AdminProductController controller;

    public void initializeData(DataCollection collection) {
        controller = new AdminProductController(collection);
        setCellTable();
        tableView.setItems(controller.getProductList());
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
        tableView.setItems(controller.getProductList());
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