package systems.kinau.fishingbot.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import systems.kinau.fishingbot.FishingBot;

@NoArgsConstructor
public class MainGUI extends Application {

    public MainGUI(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fishingbot.fxml"));
        Parent root = loader.load();
        stage.setTitle("FishingBot");
        stage.getIcons().add(new Image(MainGUI.class.getClassLoader().getResourceAsStream("icon.png")));
        stage.setScene(new Scene(root, 600, 500));
        stage.setMinHeight(500);
        stage.setMinWidth(600);
        stage.show();

        // init logger
        FishingBot.getLog().addHandler(new GUILogHandler((TextArea) loader.getNamespace().get("consoleTextArea")));

        // Scene Builder does not accept this as fxml
        ((Accordion)loader.getNamespace().get("enchantmentsAccordion")).setExpandedPane((TitledPane)loader.getNamespace().get("booksPane"));
    }

}
