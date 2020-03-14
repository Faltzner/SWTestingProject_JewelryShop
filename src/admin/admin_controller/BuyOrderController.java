package admin.admin_controller;

import admin.admin_ui.AdminMainUI;
import admin.admin_ui.NewBuyOrderUI;
import collections.BuyOrder;
import collections.DataCollection;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

@SuppressWarnings("Duplicates")
public class BuyOrderController {
    private DataCollection collection;

    public BuyOrderController(DataCollection collection) {
        this.collection = collection;
        this.collection.readBuyOrderList();
    }

    public ObservableList<BuyOrder> getBuyOrderList() {
        return this.collection.getBuyOrderList();
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
        if (collection.reconnect() != null) {
            try {
                Stage newStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_ui/NewBuyOrderUI.fxml"));
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.setScene(new Scene(loader.load()));
                NewBuyOrderUI nboi = loader.getController();
                nboi.initializeData(collection);
                newStage.setResizable(false);
                newStage.sizeToScene();
                newStage.showAndWait();
                collection.readBuyOrderList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}