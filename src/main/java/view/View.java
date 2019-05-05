package view;

import controller.Controller;
import model.Kayttaja;
import model.Resurssit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
    public static String bizName = "Long beach Skateboards";
    
    /**
     * Käyttäjän varausten tarkastelua varten tallennettava olio
     */
    public static Kayttaja selected = null;
    
    /**
     * Varaukseen vietävä resurssi
     */
    public static Resurssit booking = null;

    /**
     * Controller - ilmentymä
     */
    public static Controller controller;

    /**
     *
     * @param stage Stage
     * @throws Exception Varauduttava poikkeus
     */

    @Override
    public void start(Stage stage) throws Exception {
        controller = new Controller();

        stage.setMaximized(true);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Loginwindow.fxml"));
        Scene scene = new Scene(root, Color.BLACK);
        Image icon = new Image("Taskbar.png");
        stage.getIcons().add(icon);
        stage.setTitle("KeyChain EMS - version 0.221");
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
