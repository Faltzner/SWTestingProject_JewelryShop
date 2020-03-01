package collections;

import button.ViewImageButton;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class Product {
    private String id;
    private String name;
    private String type;
    private String description;
    private int rank;
    private Button button;
    private Image image;

    public Product(String id, String name, String description, String type, int rank, Image image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.rank = rank;
        this.image = image;
        this.button = new Button("View");

        this.button.setOnAction(event -> {
            System.out.println("You handled `View` Button.");
            ViewImageButton pdb = new ViewImageButton(this.image, this.name);
            pdb.showImagePopup();
        });
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getRank() {
        return rank;
    }

    public Button getButton() {
        return button;
    }

}