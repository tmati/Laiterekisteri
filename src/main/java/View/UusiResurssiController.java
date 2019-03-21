/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Resurssit;
import Model.ResurssitAccessObject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;

/**
 * Uuden resurssin luomiseen liittyvä toiminnalisuus. Toteutus Popup-ikkunassa.
 *
 * @author tmati
 */
public class UusiResurssiController implements Initializable {

    @FXML
    private Label itemLabel;
    @FXML
    private Button uusiresurssiNappi;
    @FXML
    private Label titleLabel;
    @FXML
    private TextArea kuvausTextbox;
    @FXML
    private Button sulkuNappi;
    @FXML
    private TextField nimiTextField;
    @FXML
    private TextField tyyppiTextField;
    @FXML
    private ChoiceBox<String> LuvanvaraisuusChoiceBox;
    @FXML
    private Label virheLabel;
    
    //ResurssitAccessObject RAO = new ResurssitAccessObject();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    /**
     * Tulkitsee ChoiceBoxin valinnan resurssiparametrin kaipaamaksi numeroksi.
     * @param cb Käsiteltävä choicebox.
     * @return Luvanvaraisuustasoa vastaava numeroarvo.
     */
    int tulkitseChoiceBox(ChoiceBox cb) {
        int selectedOption = -1;
        if (cb.getValue().equals("Vapaa käyttö")) {
            selectedOption = 0;
        }else if (cb.getValue().equals("Esimiehen hyväksyttävä")) {
            selectedOption = 1;
        }else if (cb.getValue().equals("Ylläpitäjän hyväksyttävä")) {
            selectedOption = 2;
        }  
        return selectedOption;
    } 
    /**
     * Luodaan uusi resurssi ikkunaan annetuista parametreistä. Tietojen puuttuessa heitetään herja. Onnistuneen luonnin yhteysessä suljeataan popup.
     * @param event 
     */
    @FXML
    private void uusiresurssiNappiPainettu(MouseEvent event) {
        if (nimiTextField.getText() != null && tyyppiTextField.getText() != null && !LuvanvaraisuusChoiceBox.getValue().equals("Valitse...") && kuvausTextbox.getText() != null) {
            System.out.println("Luodaan uusi resurssi!");
            Resurssit R = new Resurssit(false, nimiTextField.getText(), tyyppiTextField.getText(), tulkitseChoiceBox(LuvanvaraisuusChoiceBox), kuvausTextbox.getText());
            System.out.println(R.getNimi() + " | " + R.getTyyppi() + " | " + R.getLuvanvaraisuus() + " | " + R.getKuvaus());
            ResurssitAccessObject RAO = new ResurssitAccessObject();
            RAO.createResurssi(R);
            virheLabel.setDisable(true);
            virheLabel.setOpacity(0);
            Popup popup = (Popup) sulkuNappi.getScene().getWindow();
            popup.hide();
            
        } else {
            virheLabel.setDisable(false);
            virheLabel.setOpacity(100);
        }
    }
    
    
    /**
     * Sulkee popupin.
     * @param event 
     */
    @FXML
    private void sulkuNappiPainettu(ActionEvent event) {
        Popup popup = (Popup) sulkuNappi.getScene().getWindow();
        popup.hide();
    }
    
}
