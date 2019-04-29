/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;

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
    private Controller controller;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
        controller = View.controller;
        
        this.emailTextField.setTooltip(new Tooltip(controller.getConfigTeksti("emailTextfield")));
        this.sulkuNappi.setTooltip(new Tooltip(controller.getConfigTeksti("closePopup")));
        this.palautaemailNappi.setTooltip(new Tooltip(controller.getConfigTeksti("returnEmailTooltip")));
        palautaemailNappi.setText(controller.getConfigTeksti("returnPasswordButton").toUpperCase());
        titleLabel.setText(controller.getConfigTeksti("returnPasswordButton").toUpperCase());
        uusisalasanaLabel.setText(controller.getConfigTeksti("newPasswordLabel"));
        vanhasalasanaLabel.setText(controller.getConfigTeksti("emailLabel"));
        virheLabel.setText(controller.getConfigTeksti("formError").toUpperCase());
        emailTextField.setPromptText(controller.getConfigTeksti("emailLabel"));
    }    

    @FXML
    private void palautaemail(MouseEvent event) {
      
        if(emailTextField.getText() != null){
        controller.palautaSalasana(emailTextField.getText());
        Popup popup = (Popup) sulkuNappi.getScene().getWindow();
        popup.hide();
        }
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
