package button;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

@SuppressWarnings("Duplicates")
public class ViewReceiptButton {
    private String id;
    private String totalPrice;
    private String date;
    private String buyOrderID;

    public ViewReceiptButton(String id, String totalPrice, String date, String buyOrderID) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.date = date;
        this.buyOrderID = buyOrderID;
    }

    public void createAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Information", ButtonType.OK);
        alert.getDialogPane().setMaxWidth(Region.USE_PREF_SIZE);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Receipt");
        alert.setContentText("Receipt ID:\t " + id + "\n\nOrder ID:\t\t " + buyOrderID + "\n\nPrice:\t\t " + totalPrice
                + "\nDate:\t\t " + date);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }

}