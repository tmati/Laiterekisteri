package View;

import Controller.Controller;
import Model.Kayttaja;
import Model.Resurssit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Sovelluksen laukaisu.
 *
 * @author tmati
 */
public class View extends Application {

    /**
     * Sovelluksen käytäjän tiedot.
     *
     */
    public static Kayttaja loggedIn = null;

    /**
     * Yrityksen nimi
     */
    public static String BizName = "Long beach Skateboards";

    /**
     * Resurssit
     */
    public static Resurssit booking = null;

    /**
     * Controller - ilmentymä
     */
    public static Controller controller;

    /**
     *
     * @param stage
     * @throws Exception Varauduttava poikkeus
     */
    @Override
    public void start(Stage stage) throws Exception {
        controller = new Controller();
        /*Fullscreen sovellusnäkymää varten poista alkta kommenteista.
          Pitäisi tehdä windowsin ikkunatoimintoja vastaavat painikkeet johonkin.
         */
        //stage.initStyle(StageStyle.TRANSPARENT);
        stage.setMaximized(true);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Loginwindow.fxml"));
        Scene scene = new Scene(root, Color.BLACK);
        Image icon = new Image("Taskbar.png");
        stage.getIcons().add(icon);
        stage.setTitle("Laiterekisteri");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }

}
