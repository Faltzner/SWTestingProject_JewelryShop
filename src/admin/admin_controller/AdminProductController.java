package admin.admin_controller;

import admin.admin_ui.AdminMainUI;
import admin.admin_ui.NewProductUI;
import collections.DataCollection;
import collections.Product;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

@SuppressWarnings("Duplicates")
public class AdminProductController {
    private DataCollection collection;

    public AdminProductController(DataCollection collection) {
        this.collection = collection;
        this.collection.readProductList();
    }

    public ObservableList<Product> getProductList() {
        return this.collection.getProductList();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_ui/NewProductUI.fxml"));
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setScene(new Scene(loader.load()));
            NewProductUI npi = loader.getController();
            npi.initializeData(collection);
            newStage.setResizable(false);
            newStage.sizeToScene();
            newStage.showAndWait();
            collection.readProductList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}