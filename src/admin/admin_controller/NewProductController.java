package admin.admin_controller;

import collections.DataCollection;
import collections.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.text.DecimalFormat;

@SuppressWarnings("Duplicates")
public class NewProductController {
    private DataCollection collection;

    Image image;

    private ObservableList<String> rankList;

    private DecimalFormat productFormat = new DecimalFormat("0000");

    private String name = "", type = "", rank = "", description = "", path = "";
    private String productID = "";
    private int latestProductID = 0;

    public NewProductController(DataCollection collection) {
        this.collection = collection;
    }

    public void setDataFromField(TextField name, TextField description) {
        this.name = name.getText();
        this.description = description.getText();
    }

    public void chooseFile(Text pathField, Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG Files", "*.jpg"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            this.path = file.getAbsolutePath();
            this.image = new Image(file.toURI().toString());
            pathField.setText(file.getName());
        }
    }

    public String dataVerification() {
        System.out.println("Data Verification is Processing...");
        return result();
    }

    private void generateProductID(String type) {
        ObservableList<String> productList = FXCollections.observableArrayList();
        productList.clear();
        switch (type) {
            case "necklace":
                for (Product product: collection.getProductList()) {
                    if (product.getID().charAt(0) == 'n') {
                        productList.add(product.getID());
                    }
                }
                for (String id: productList) {
                    String getID = id.replace("n","0");
                    int currentID = Integer.parseInt(getID);
                    if (currentID > latestProductID) {
                        latestProductID = currentID;
                    }
                }
                latestProductID++;
                productID = "n" + productFormat.format(latestProductID);
                System.out.println("Product ID is: " + productID);
                break;
            case "earrings":
                for (Product product: collection.getProductList()) {
                    if (product.getID().charAt(0) == 'e') {
                        productList.add(product.getID());
                    }
                }
                for (String id: productList) {
                    String getID = id.replace("e","0");
                    int currentID = Integer.parseInt(getID);
                    if (currentID > latestProductID) {
                        latestProductID = currentID;
                    }
                }
                latestProductID++;
                productID = "e" + productFormat.format(latestProductID);
                System.out.println("Product ID is: " + productID);
                break;
            case "ring":
                for (Product product: collection.getProductList()) {
                    if (product.getID().charAt(0) == 'r') {
                        productList.add(product.getID());
                    }
                }
                for (String id: productList) {
                    String getID = id.replace("r","0");
                    int currentID = Integer.parseInt(getID);
                    if (currentID > latestProductID) {
                        latestProductID = currentID;
                    }
                }
                latestProductID++;
                productID = "r" + productFormat.format(latestProductID);
                System.out.println("Product ID is: " + productID);
                break;
            case "bracelet":
                for (Product product: collection.getProductList()) {
                    if (product.getID().charAt(0) == 'b') {
                        productList.add(product.getID());
                    }
                }
                for (String id: productList) {
                    String getID = id.replace("b","0");
                    int currentID = Integer.parseInt(getID);
                    if (currentID > latestProductID) {
                        latestProductID = currentID;
                    }
                }
                latestProductID++;
                productID = "b" + productFormat.format(latestProductID);
                System.out.println("Product ID is: " + productID);
                break;
        }
    }

    public void initializeComboBox(ComboBox<String> typeChoice, ComboBox<String> rankChoice) {
        try {
            collection.readProductList();
            ObservableList<String> typeList = FXCollections.observableArrayList();
            typeList.clear();
            for (Product product : collection.getProductList()) {
                if (!typeList.contains(capitalize(product.getType()))) {
                    typeList.add(capitalize(product.getType()));
                }
            }
            System.out.println("Size of Type List: " + typeList.size());
            typeChoice.setItems(typeList);
            rankChoice.setItems(FXCollections.observableArrayList("--Please Select Type--"));
            typeChoice.valueProperty().addListener((obsType, oldValType, newValType) -> {
                if (newValType != null) {
                    this.type = newValType.toLowerCase();
                    rankList = FXCollections.observableArrayList();
                    rankList.clear();
                    rankList.add("Rank 0");
                    rankList.add("Rank 1");
                    System.out.println("Size of Product List: " + rankList.size());
                    rankChoice.setItems(rankList);
                    rankChoice.valueProperty().addListener((obsProduct, oldValProduct, newValProduct) -> {
                        if (newValProduct == null) {
                            this.rank = "";
                        } else {
                            this.rank = newValProduct.substring(5);
                        }
                    });
                } else {
                    this.type = "";
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private String result() {
        if (this.name.equals("")) {
            System.out.println("Please Filled `Name`.");
            return "Invalid Name|You didn't fill in `Name` Field.";
        } else if (this.type.equals("")) {
            System.out.println("Please Choose `Type`.");
            return "Invalid Type|You didn't choose `Type`.";
        } else if (this.rank.equals("") || this.type == null) {
            System.out.println("Please Choose `Rank`.");
            return "Invalid Rank|You didn't choose `Rank`.";
        } else if (this.description.equals("")) {
            System.out.println("Please Filled `Description`.");
            return "Invalid Description|You didn't fill in `Description` Field.";
        } else if (this.path.equals("")) {
            System.out.println("Please Choose `File`.");
            return "Invalid File|You didn't choose `File`.";
        } else {
            generateProductID(type);
            String fileType = path.substring(path.indexOf("."));
            collection.addProduct(productID, name, description, type, Integer.parseInt(rank), image, fileType.substring(1));
            return "success";
        }
    }

    private String capitalize(String str) {
        String words[] = str.split(" ");
        for (int i = 0; i < words.length; i++) {
            if (!(words[i].toUpperCase().equals("MADE") || words[i].toUpperCase().equals("FROM"))) {
                words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
            }
        }
        return String.join(" ", words);
    }

    public Alert createWarningAlert(String messageStr) {
        String message[] = messageStr.split("[|]");

        Alert alert = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText(message[0]);
        alert.setContentText(message[1]);
        alert.showAndWait();

        return alert;
    }

    public Alert createInformationAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Information", ButtonType.OK);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Product Information");
        alert.setContentText("Product ID:\t " + productID + "\nType:\t\t " + capitalize(type) + "\nProduct:\t\t "
                + capitalize(name) + "\nDescription:\t " + capitalize(description) + "\nPath:\t\t " + path);
        alert.showAndWait();

        return alert;
    }

}