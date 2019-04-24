/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
     * @param url URL
     * @param rb ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usernameLabel.setText(View.loggedIn.getNimi());
        controller = View.controller;
        this.sulkuNappi.setTooltip(new Tooltip("Sulkee popupin"));
        this.uusisalasana1TextField.setTooltip(new Tooltip("Kenttä uutta salasanaa varten"));
        this.uusisalasana2TextField.setTooltip(new Tooltip("Toista salasana"));
        this.vanhasalasanaTextField.setTooltip(new Tooltip("Kenttä vanhaa salasanaa varten"));
        this.vaihdasalasanaNappi.setTooltip(new Tooltip("Vaihtaa salasanan"));
        
    }

    /**
     * Käsittelee salasanan vaihtoa popup-ikkunassa.
     *
     * @param event Hiiren klikkaus painikkeeseen.
     */
    @FXML
    private void vaihdasalasanaNappiPainettu(MouseEvent event) {
        //Sisään kirjautuneena oleva käyttäjä tähän
        if (controller.SalasananCryptaus(vanhasalasanaTextField.getText()).equals(View.loggedIn.getSalasana()) && uusisalasana1TextField.getText().equals(uusisalasana2TextField.getText())) {
            View.loggedIn.setSalasana(controller.SalasananCryptaus(uusisalasana2TextField.getText()));
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
