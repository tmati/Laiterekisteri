/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author tmati
 */
public class View extends Application {
    
    public void start(Stage stage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("Loginwindow.fxml"));
       Scene scene = new Scene(root);
        
    
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to visible bounds of the main screen
        /*stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
        */
        stage.setTitle("Laiterekisteri");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
    
}
