package db_util;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
    public static Connection connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/gemdb", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Database not found.");
            alert.setContentText("This program cannot find database.\nPlease check your database.");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                Platform.exit();
            }
        }
        return null;
    }

}