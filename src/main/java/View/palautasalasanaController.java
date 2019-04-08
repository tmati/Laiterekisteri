/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;


/**
 * FXML Controller class
 *
 * @author tmati
 */
public class palautasalasanaController implements Initializable {

    @FXML
    private Label uusisalasanaLabel;
    @FXML
    private Button palautaemailNappi;
    @FXML
    private Label titleLabel;
    @FXML
    private Button sulkuNappi;
    @FXML
    private TextField emailTextField;
    @FXML
    private Label vanhasalasanaLabel;
    @FXML
    private Label virheLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void palautaemail(MouseEvent event) {
        //Tähän email toiminnallisuus
        Popup popup = (Popup) sulkuNappi.getScene().getWindow();
        popup.hide();
    }
    
    /**
     * Sulkee näkymän
     * @param event 
     */
    @FXML
    private void sulkuNappiPainettu(ActionEvent event) throws IOException {
        Popup popup = (Popup) sulkuNappi.getScene().getWindow();
        popup.hide();
    }
}
