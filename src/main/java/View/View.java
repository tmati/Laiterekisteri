/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import Model.Kayttaja;
import Model.Resurssit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    public static String BizName = "Long beach Skateboards";

    /**
     * Resurssit
     */
    public static Resurssit booking = null;

    public static Controller controller;

    @Override
    public void start(Stage stage) throws Exception {
        controller = new Controller();
        stage.setMaximized(true);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Loginwindow.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Laiterekisteri");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
