package button;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

@SuppressWarnings("Duplicates")
public class ViewImageButton {
    private Image image;
    private String name;

    public ViewImageButton(Image image, String name) {
        this.image = image;
        this.name = capitalize(name);
    }

    public void showImagePopup() {
        Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);

        ImageView imageView = new ImageView(this.image);
        imageView.setFitWidth(500);
        imageView.setFitHeight(500);

        VBox imageVBox = new VBox();
        imageVBox.getChildren().add(imageView);

        Scene stageScene = new Scene(imageVBox, 490, 490);
        newStage.setScene(stageScene);
        newStage.setResizable(false);
        newStage.setTitle(this.name);
        newStage.show();
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

}