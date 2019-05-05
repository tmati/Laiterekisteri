/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
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
    private static final String UUSISALASANA = "newPassword";
    Controller controller;

    /**
     * Initializes the CONTROLLER class.
     *
     * @param url URL
     * @param rb ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usernameLabel.setText(View.loggedIn.getNimi());
        controller = View.CONTROLLER;
        
        vaihdasalasanaNappi.setText(controller.getConfigTeksti("changePassword").toUpperCase());
        titleLabel.setText(controller.getConfigTeksti("changePasswordTitle").toUpperCase());
        vanhasalasanaTextField.setPromptText(controller.getConfigTeksti("oldPassword"));
        uusisalasana1TextField.setPromptText(controller.getConfigTeksti(UUSISALASANA));
        uusisalasanaLabel.setText(controller.getConfigTeksti(UUSISALASANA).toUpperCase());
        vanhasalasanaLabel.setText(controller.getConfigTeksti("oldPassword").toUpperCase());
        uusisalasana2TextField.setPromptText(controller.getConfigTeksti(UUSISALASANA));
        uusisalasana2Label.setText(controller.getConfigTeksti(UUSISALASANA).toUpperCase());
        virheLabel.setText(controller.getConfigTeksti("passwordChangeErrormsg").toUpperCase());
        
        this.sulkuNappi.setTooltip(new Tooltip(controller.getConfigTeksti("closePopup")));
        this.uusisalasana1TextField.setTooltip(new Tooltip(controller.getConfigTeksti("newPswFieldInfo")));
        this.uusisalasana2TextField.setTooltip(new Tooltip(controller.getConfigTeksti("repeatPassword")));
        this.vanhasalasanaTextField.setTooltip(new Tooltip(controller.getConfigTeksti("oldPswFieldInfo")));
        this.vaihdasalasanaNappi.setTooltip(new Tooltip(controller.getConfigTeksti("changePasswordInfo")));
        
    }

    /**
     * Käsittelee salasanan vaihtoa popup-ikkunassa.
     *
     * @param event Hiiren klikkaus painikkeeseen.
     */
    @FXML
    private void vaihdasalasanaNappiPainettu(MouseEvent event) {
        //Sisään kirjautuneena oleva käyttäjä tähän
        if (controller.salasananCryptaus(vanhasalasanaTextField.getText()).equals(View.loggedIn.getSalasana()) && uusisalasana1TextField.getText().equals(uusisalasana2TextField.getText())) {
            View.loggedIn.setSalasana(controller.salasananCryptaus(uusisalasana2TextField.getText()));
            controller.paivitaKayttaja(View.loggedIn);
            virheLabel.setDisable(true);
            virheLabel.setOpacity(0);
            Popup popup = (Popup) sulkuNappi.getScene().getWindow();
            popup.hide();
        } else if (!uusisalasana1TextField.getText().equals(uusisalasana2TextField.getText())) {
            virheLabel.setText(controller.getConfigTeksti("newPasswordError1"));
            virheLabel.setDisable(false);
            virheLabel.setOpacity(100);
        } else if (!vanhasalasanaTextField.getText().equals(View.loggedIn.getSalasana())) {
            virheLabel.setText(controller.getConfigTeksti("newPasswordError2"));
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
