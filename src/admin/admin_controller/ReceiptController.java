package admin.admin_controller;

import admin.admin_ui.AdminMainUI;
import admin.admin_ui.NewReceiptUI;
import collections.DataCollection;
import collections.Receipt;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

@SuppressWarnings("Duplicates")
public class ReceiptController {
    private DataCollection collection;

    public ReceiptController(DataCollection collection) {
        this.collection = collection;
        this.collection.readReceiptList();
    }

    public ObservableList<Receipt> getReceiptList() {
        return this.collection.getReceiptList();
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

    public void loadCreateUI() {
        try {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_ui/NewReceiptUI.fxml"));
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setScene(new Scene(loader.load()));
            NewReceiptUI nri = loader.getController();
            nri.initializeData(collection);
            newStage.setResizable(false);
            newStage.sizeToScene();
            newStage.showAndWait();
            collection.readReceiptList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}