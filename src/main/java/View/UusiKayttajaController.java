/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Kayttaja;
import Model.KayttajaAccessObject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;

/**
 * Uuden käyttäjän luontipopupin toiminnallisuus
 *
 * @author tmati
 */
public class UusiKayttajaController implements Initializable {

    @FXML
    private Button luokayttajaNappi;
    @FXML
    private Label titleLabel;
    @FXML
    private Button sulkuNappi;
    @FXML
    private TextField emailTextField;
    @FXML
    private Label virheLabel;
    @FXML
    private TextField nimiTextField;
    @FXML
    private TextField salasanaTextField;
    @FXML 
    private TextField kayttajatunnusTextField;
    @FXML
    private ChoiceBox<String> valtuudetChoiceBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
        /**
         * Käännetään choiceboxin arvo tietokantaan sopivaksi
         * @param cb
         * @return 
         */
        int tulkitseChoiceBox(ChoiceBox cb) {
        int selectedOption = -1;
        if (cb.getValue().equals("Työntekijä")) {
            selectedOption = 0;
        }else if (cb.getValue().equals("Esimies")) {
            selectedOption = 1;
        }else if (cb.getValue().equals("Ylläpitäjä")) {
            selectedOption = 2;
        }  
        return selectedOption;
    } 
    /**
     * TODO Hae käyttäjät tietokannasta ja vertaa niiden sähköposteja ja tunnuksia 
     * @param tarkastettavaSyote uuden käyttäjän parametri
     * @param vertailukohde Verrattava syöte. Iteroi tässä koko tietokannan läpi.
     * @return true jos uniikki
     */    
    boolean emailTarkastus(String tarkastettavaSyote) {
        KayttajaAccessObject KAO = new KayttajaAccessObject();
        Kayttaja[] kayttajat = KAO.readKayttajat();
        for (Kayttaja kayttaja : kayttajat) {
            if (kayttaja.getSahkoposti().equals(tarkastettavaSyote)) {
                virheLabel.setText("Sähköposti on jo varattu. Kokeile toista osoitetta.");
                virheLabel.setDisable(false);
                virheLabel.setOpacity(100);
                return false;
            }
        }
        return true;
    }
    
        /**
     * TODO Hae käyttäjät tietokannasta ja vertaa niiden tunnuksia 
     * @param tarkastettavaSyote uuden käyttäjän parametri
     * @param vertailukohde Verrattava syöte. Iteroi tässä koko tietokannan läpi.
     * @return true jos uniikki
     */    
    boolean usernameTarkastus(String tarkastettavaSyote) {
        KayttajaAccessObject KAO = new KayttajaAccessObject();
        Kayttaja[] kayttajat = KAO.readKayttajat();
        for (Kayttaja kayttaja : kayttajat) {
            if (kayttaja.getKayttajatunnus().equals(tarkastettavaSyote)) {
                virheLabel.setText("Käyttäjätunnus on jo varattu. Kokeile toista tunnusta.");
                virheLabel.setDisable(false);
                virheLabel.setOpacity(100);
                return false;
            }
        }
        return true;
    }
    
    /**
     * Luo uuden käyttäjän annettujen ehtojen täsmätessä. Herjaa jos ehdot ei täsmää.
     * @param event 
     */
    @FXML
    private void luokayttajaNappiPainettu(MouseEvent event) {
        //Sähköpostin pitää olla uniikki. Tarkasta tietokannasta.
        if (nimiTextField.getText() != null && emailTextField.getText() != null && salasanaTextField.getText() != null && kayttajatunnusTextField.getText() != null && !valtuudetChoiceBox.getValue().equals("Valitse...") && emailTarkastus(emailTextField.getText()) && usernameTarkastus(kayttajatunnusTextField.getText())) {
            Kayttaja J = new Kayttaja(nimiTextField.getText(), salasanaTextField.getText(), kayttajatunnusTextField.getText(), emailTextField.getText(), tulkitseChoiceBox(valtuudetChoiceBox));
            System.out.println(J.getNimi() + " | " + J.getSalasana() + " | " + J.getKayttajatunnus() + " | " + J.getSahkoposti() + " | " + J.getValtuudet());
            KayttajaAccessObject KAO = new KayttajaAccessObject();
            KAO.createKayttaja(J);
            Popup popup = (Popup) sulkuNappi.getScene().getWindow();
            popup.hide();
            
        } else {
            virheLabel.setText("Tietoja puuttuu. Täytä kaikki kohdat ja yritä uudelleen.");
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
