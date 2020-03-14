package admin.admin_controller;

import admin.admin_ui.BuyOrderUI;
import admin.admin_ui.EmployeeUI;
import admin.admin_ui.AdminProductUI;
import admin.admin_ui.ReceiptUI;
import admin.admin_ui.WorkOrderUI;
import collections.DataCollection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

@SuppressWarnings("Duplicates")
public class AdminMainController {
    private DataCollection collection;

    public AdminMainController(DataCollection collection) {
        this.collection = collection;
    }

    public void loadBuyOrderUI(Stage stage) {
        System.out.println("Load `Buy Order` UI...");
        if (collection.reconnect() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_ui/BuyOrderUI.fxml"));
                stage.setScene(new Scene(loader.load()));
                BuyOrderUI boi = loader.getController();
                boi.initializeData(collection);
                stage.setResizable(false);
                stage.sizeToScene();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadWorkOrderUI(Stage stage) {
        System.out.println("Load `Work Order` UI...");
        if (collection.reconnect() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_ui/WorkOrderUI.fxml"));
                stage.setScene(new Scene(loader.load()));
                WorkOrderUI woai = loader.getController();
                woai.initializeData(collection);
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
        if (collection.reconnect() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_ui/AdminProductUI.fxml"));
                stage.setScene(new Scene(loader.load()));
                AdminProductUI pai = loader.getController();
                pai.initializeData(collection);
                stage.setResizable(false);
                stage.sizeToScene();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadReceiptUI(Stage stage) {
        System.out.println("Load `Receipt` UI...");
        if (collection.reconnect() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_ui/ReceiptUI.fxml"));
                stage.setScene(new Scene(loader.load()));
                ReceiptUI rui = loader.getController();
                rui.initializeData(collection);
                stage.setResizable(false);
                stage.sizeToScene();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadEmployeeUI(Stage stage) {
        System.out.println("Load `Employee` UI...");
        if (collection.reconnect() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_ui/EmployeeUI.fxml"));
                stage.setScene(new Scene(loader.load()));
                EmployeeUI eai = loader.getController();
                eai.initializeData(collection);
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
        try {
            collection.getConnection().close();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}