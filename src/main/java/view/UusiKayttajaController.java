/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.*;
import model.Kayttaja;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javax.mail.MessagingException;
import javafx.stage.Window;

/**
 * Uuden käyttäjän luontipopupin toiminnallisuus
 *
 * @author tmati
 */
public class UusiKayttajaController implements UusiKayttajaControllerIf {

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
    @FXML
    private TableView<Kayttaja> kayttajaTableView;
    @FXML
    private Label uusisalasanaLabel;
    @FXML
    private Label vanhasalasanaLabel;
    @FXML
    private Label choiceBoxLabel;
    @FXML
    private Label vanhasalasanaLabel1;
    @FXML
    private Label vanhasalasanaLabel11;

    private String choose = "choose";

    /**
     * Controller-ilmentymä
     */
    private ControllerIf controller;

    /**
     * Initializes the CONTROLLER class.
     *
     * @param url URL
     * @param rb ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = Controller.getInstance();

        luokayttajaNappi.setText(controller.getConfigTeksti("createUser").toUpperCase());
        titleLabel.setText(controller.getConfigTeksti("newUser").toUpperCase());
        emailTextField.setPromptText(controller.getConfigTeksti("emailLabel"));
        uusisalasanaLabel.setText(controller.getConfigTeksti("userName").toUpperCase());
        vanhasalasanaLabel.setText(controller.getConfigTeksti("emailLabel").toUpperCase());
        virheLabel.setText(controller.getConfigTeksti("formError").toUpperCase());
        nimiTextField.setPromptText(controller.getConfigTeksti("name"));
        valtuudetChoiceBox.getItems().setAll(controller.getConfigTeksti(choose), controller.getConfigTeksti("employee"), controller.getConfigTeksti("superior"), controller.getConfigTeksti("administrator"));
        valtuudetChoiceBox.setValue(controller.getConfigTeksti(choose));
        choiceBoxLabel.setText(controller.getConfigTeksti("userAut"));
        salasanaTextField.setPromptText(controller.getConfigTeksti("passwordLabel"));
        vanhasalasanaLabel1.setText(controller.getConfigTeksti("passwordLabel").toUpperCase());
        kayttajatunnusTextField.setPromptText(controller.getConfigTeksti("kayttajaTunnus"));
        vanhasalasanaLabel11.setText(controller.getConfigTeksti("kayttajaTunnus"));

        this.emailTextField.setTooltip(new Tooltip(controller.getConfigTeksti("newUserEmailInfo")));
        this.kayttajatunnusTextField.setTooltip(new Tooltip(controller.getConfigTeksti("newUserIdInfo")));
        this.nimiTextField.setTooltip(new Tooltip(controller.getConfigTeksti("newUserNameInfo")));
        this.salasanaTextField.setTooltip(new Tooltip(controller.getConfigTeksti("newUserPasswordInfo")));
        this.sulkuNappi.setTooltip(new Tooltip(controller.getConfigTeksti("closePopup")));
        this.valtuudetChoiceBox.setTooltip(new Tooltip(controller.getConfigTeksti("chooseUserAutInfo")));
        this.luokayttajaNappi.setTooltip(new Tooltip(controller.getConfigTeksti("createNewUserInfo")));

    }

    /**
     * Käännetään choiceboxin arvo tietokantaan sopivaksi
     *
     * @param cb
     * @return
     */
    int tulkitseChoiceBox(ChoiceBox cb) {
        int selectedOption = -1;
        if (cb.getValue().equals(controller.getConfigTeksti("employee"))) {
            selectedOption = 0;
        } else if (cb.getValue().equals(controller.getConfigTeksti("superior"))) {
            selectedOption = 1;
        } else if (cb.getValue().equals(controller.getConfigTeksti("administrator"))) {
            selectedOption = 2;
        }
        return selectedOption;
    }

    /**
     * Luo uuden käyttäjän annettujen ehtojen täsmätessä. Herjaa jos ehdot ei
     * täsmää. Tarkastaa myös sähköpostin ja käyttäjätunnuksen tietokantaa
     * vasten (ei saa olla samat)
     *
     * @param event Hiiren klikkaus painikkeesta.
     */
    @FXML
    private void luokayttajaNappiPainettu(MouseEvent event) throws MessagingException {
        if (nimiTextField.getText().isEmpty() || emailTextField.getText().isEmpty() || salasanaTextField.getText().isEmpty() || kayttajatunnusTextField.getText().isEmpty() || valtuudetChoiceBox.getValue().equals(controller.getConfigTeksti(choose))) {
            virheLabel.setText(controller.getConfigTeksti("newUserErrorLabel1"));
            virheLabel.setDisable(false);
            virheLabel.setOpacity(100);
        } else if (!controller.tarkistaUsername(kayttajatunnusTextField.getText())) {
            virheLabel.setText(controller.getConfigTeksti("newUserErrorLabel2"));
            virheLabel.setDisable(false);
            virheLabel.setOpacity(100);
        } else if (!controller.tarkistaEmail(emailTextField.getText())) {
            virheLabel.setText(controller.getConfigTeksti("newUserErrorLabel3"));
            virheLabel.setDisable(false);
            virheLabel.setOpacity(100);
        } else {
            Kayttaja kayttaja = new Kayttaja(nimiTextField.getText(), controller.salasananCryptaus(salasanaTextField.getText()), kayttajatunnusTextField.getText(), emailTextField.getText(), controller.readCb(valtuudetChoiceBox));
            controller.luoKayttaja(kayttaja);

            controller.lahetaSahkoposti(kayttaja.getSahkoposti(), controller.getConfigTeksti("newUserEmail")
                    + kayttaja.getKayttajatunnus() + "\n"
                    + controller.getConfigTeksti("newUserEmailP")
                    + controller.getConfigTeksti("newUserEmailCon"));

            Kayttaja[] kayttajat = controller.haeKaikkiKayttajat();

            Popup popup = (Popup) sulkuNappi.getScene().getWindow();
            Window nakyma = popup.getOwnerWindow();
            TableView kaytTableView = (TableView) nakyma.getScene().lookup("#kayttajaTableView");
            kaytTableView.getItems().clear();
            kaytTableView.getItems().addAll(kayttajat);
            popup.hide();
        }
    }

    /**
     * Sulkee popupin.
     *
     * @param event Hiiren klikkaus painikkeesta.
     */
    @FXML
    private void sulkuNappiPainettu(ActionEvent event) {

        Kayttaja[] kayttajat = controller.haeKaikkiKayttajat();
        Popup popup = (Popup) sulkuNappi.getScene().getWindow();
        Window nakyma = popup.getOwnerWindow();
        TableView kaytTableView = (TableView) nakyma.getScene().lookup("#kayttajaTableView");
        kaytTableView.getItems().clear();
        kaytTableView.getItems().addAll(kayttajat);
        popup.hide();
    }
}
