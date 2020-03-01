package login_gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

@SuppressWarnings("Duplicates")
public class LoginPageUI {
    @FXML
    ImageView imageViewBG;

    @FXML
    TextField usernameField;

    @FXML
    PasswordField passwordField;

    private LoginPageController controller;

    @FXML
    public void initialize() {
        Image imageBG = new Image(getClass().getResourceAsStream("/pic/login.jpg"));
        imageViewBG.setImage(imageBG);
        controller = new LoginPageController();
    }

    @FXML
    public void handleLoginButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (controller.getCollection().getConnection() != null) {
            try {
                String message = controller.loginSession(username, password);
                switch (message) {
                    case "admin":
                        System.out.println("Admin: Login Successful!");
                        controller.loadAdminPage(getStage());
                        controller.setPage(getStage(), usernameField, passwordField);
                        getStage().show();
                        break;
                    case "employee":
                        System.out.println(username + ": Login Successful!");
                        controller.loadEmployeePage(getStage(), username);
                        controller.setPage(getStage(), usernameField, passwordField);
                        getStage().show();
                        break;
                    default:
                        showWarningAlert(message);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Database not found.");
        }
    }

    @FXML
    public void handleExitButton() {
        System.out.println("You're exit the program.");
        System.exit(0);
    }

    private void showWarningAlert(String messageStr) {
        Alert alert = controller.createWarningAlert(messageStr);
        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }

    private Stage getStage(){
        return (Stage) imageViewBG.getScene().getWindow();
    }

}