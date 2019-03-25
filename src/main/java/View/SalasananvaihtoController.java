/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import Model.Kayttaja;
import Model.KayttajaAccessObject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;

/**
 * Salasanan vaihdon ohjaintoiminnot
 *
 * @author tmati
 */
public class SalasananvaihtoController implements Initializable {

    @FXML
    private Label usernameLabel;
    @FXML
    private Button vaihdasalasanaNappi;
    @FXML
    private Label titleLabel;
    @FXML
    private Button sulkuNappi;
    @FXML
    private TextField vanhasalasanaTextField;
    @FXML
    private TextField uusisalasana1TextField;
    @FXML
    private Label uusisalasanaLabel;
    @FXML
    private Label vanhasalasanaLabel;
    @FXML
    private Label virheLabel;
    @FXML
    private TextField uusisalasana2TextField;
    @FXML
    private Label uusisalasana2Label;

    Controller controller;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Hae käyttäjän nimi tähän
        usernameLabel.setText("");
        controller = View.controller;
    }

    /**
     * Käsittelee salasanan vaihtoa popup-ikkunassa.
     *
     * @param event Hiiren klikkaus painikkeeseen.
     */
    @FXML
    private void vaihdasalasanaNappiPainettu(MouseEvent event) {
        //Sisään kirjautuneena oleva käyttäjä tähän
        if (vanhasalasanaTextField.getText().equals(View.loggedIn.getSalasana()) && uusisalasana1TextField.getText().equals(uusisalasana2TextField.getText())) {
            View.loggedIn.setSalasana(uusisalasana2TextField.getText());
            controller.paivitaKayttaja(View.loggedIn);
            virheLabel.setDisable(true);
            virheLabel.setOpacity(0);
            Popup popup = (Popup) sulkuNappi.getScene().getWindow();
            popup.hide();
        } else if (!uusisalasana1TextField.getText().equals(uusisalasana2TextField.getText())) {
            virheLabel.setText("Uusi salasana ei täsmää tekstikentissä.");
            virheLabel.setDisable(false);
            virheLabel.setOpacity(100);
        } else if (!vanhasalasanaTextField.getText().equals(View.loggedIn.getSalasana())) {
            virheLabel.setText("Vanha salasana väärin.");
            virheLabel.setDisable(false);
            virheLabel.setOpacity(100);

        }
    }

    /**
     * Sulkee popupin.
     * @param event Hiiren klikkaus painikkeeseen.
     */
    @FXML
    private void sulkuNappiPainettu(ActionEvent event) {
        Popup popup = (Popup) sulkuNappi.getScene().getWindow();
        popup.hide();
    }
}
