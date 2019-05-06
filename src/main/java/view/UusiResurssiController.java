/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ControllerIf;
import model.Resurssit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Window;

/**
 * Uuden resurssin luomiseen liittyvä toiminnalisuus. Toteutus Popup-ikkunassa.
 *
 * @author tmati
 */
public class UusiResurssiController implements UusiResurssiControllerIf {

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
    private ChoiceBox<String> luvanvaraisuusChoiceBox;
    @FXML
    private Label virheLabel;
    @FXML
    private Label nimiLabel;
    @FXML
    private Label kuvausLabel;
    @FXML
    private Label tyyppiLabel;
    @FXML
    private Label lupaLabel;
    
    private String choose = "choose";
    ControllerIf controller;

   
    /**
     * Initializes the CONTROLLER class.
     *
     * @param url URL
     * @param rb ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = View.CONTROLLER;
        
        itemLabel.setText(controller.getConfigTeksti("giveInfo"));
        uusiresurssiNappi.setText(controller.getConfigTeksti("createNewResource"));
        titleLabel.setText(controller.getConfigTeksti("newResource"));
        kuvausTextbox.setPromptText(controller.getConfigTeksti("description"));
        nimiTextField.setPromptText(controller.getConfigTeksti("name"));
        tyyppiTextField.setPromptText(controller.getConfigTeksti("type"));
        luvanvaraisuusChoiceBox.getItems().setAll(controller.getConfigTeksti(choose), controller.getConfigTeksti("freeUse"), controller.getConfigTeksti("supApproved"), controller.getConfigTeksti("adApproved")) ;
        luvanvaraisuusChoiceBox.setValue(controller.getConfigTeksti(choose));
        nimiLabel.setText(controller.getConfigTeksti("name").toUpperCase());
        tyyppiLabel.setText(controller.getConfigTeksti("type").toUpperCase());
        lupaLabel.setText(controller.getConfigTeksti("permission").toUpperCase());
        kuvausLabel.setText(controller.getConfigTeksti("description").toUpperCase());
        virheLabel.setText(controller.getConfigTeksti("passwordChangeErrormsg").toUpperCase());
        
        this.luvanvaraisuusChoiceBox.setTooltip(new Tooltip(controller.getConfigTeksti("newResourceChoiceBoxInfo")));
        this.kuvausTextbox.setTooltip(new Tooltip(controller.getConfigTeksti("newResourceTextBoxInfo")));
        this.nimiTextField.setTooltip(new Tooltip(controller.getConfigTeksti("newResourceNameInfo")));
        this.sulkuNappi.setTooltip(new Tooltip(controller.getConfigTeksti("closePopup")));
        this.tyyppiTextField.setTooltip(new Tooltip(controller.getConfigTeksti("newResourceTypeInfo")));
        this.uusiresurssiNappi.setTooltip(new Tooltip(controller.getConfigTeksti("newResourceInfo")));
       
    }

    /**
     * Tulkitsee ChoiceBoxin valinnan resurssiparametrin kaipaamaksi numeroksi.
     *
     * @param cb Käsiteltävä choicebox.
     * @return Luvanvaraisuustasoa vastaava numeroarvo.
     */
    int tulkitseChoiceBox(ChoiceBox cb) {
        int selectedOption = -1;
        if (cb.getValue().equals(controller.getConfigTeksti("freeUse"))) {
            selectedOption = 0;
        } else if (cb.getValue().equals(controller.getConfigTeksti("supApproved"))) {
            selectedOption = 1;
        } else if (cb.getValue().equals(controller.getConfigTeksti("adApproved"))) {
            selectedOption = 2;
        }
        return selectedOption;
    }

  

    /**

     * Luodaan uusi resurssi ikkunaan annetuista parametreistä. Tietojen puuttuessa heitetään herja. Onnistuneen luonnin yhteysessä suljeataan popup.
     * @param event Hiiren klikkaus painikkeesta.
     */
    @FXML
    private void uusiresurssiNappiPainettu(MouseEvent event) {
        if (nimiTextField.getText() != null && tyyppiTextField.getText() != null && !luvanvaraisuusChoiceBox.getValue().equals(controller.getConfigTeksti(choose)) && kuvausTextbox.getText() != null) {
            Resurssit resurssi = new Resurssit(true, nimiTextField.getText(), tyyppiTextField.getText(), controller.readCb(luvanvaraisuusChoiceBox), kuvausTextbox.getText());
      
            controller.luoResurssi(resurssi);
            virheLabel.setDisable(true);
            virheLabel.setOpacity(0);
            Resurssit[] resurssit = controller.haeKaikkiResurssit();
            Popup popup = (Popup) sulkuNappi.getScene().getWindow();
            Window nakyma = popup.getOwnerWindow();
            TableView resurssitTableView = (TableView) nakyma.getScene().lookup("#kaikkiTableView");
            resurssitTableView.getItems().clear();
            resurssitTableView.getItems().addAll(resurssit);
            popup.hide();

        } else {
            virheLabel.setDisable(false);
            virheLabel.setOpacity(100);
        }
    }

       
    /**
     * Sulkee popupin. Myös taulukon päivitys.
     * @param event Hiiren klikkaus painikkeesta.
     */
    @FXML
    private void sulkuNappiPainettu(ActionEvent event) {
        Popup popup = (Popup) sulkuNappi.getScene().getWindow();
        Resurssit[] resurssit = controller.haeKaikkiResurssit();
        Window nakyma = popup.getOwnerWindow();
        TableView resurssitTableView = (TableView) nakyma.getScene().lookup("#kaikkiTableView");
        resurssitTableView.getItems().clear();
        resurssitTableView.getItems().addAll(resurssit);
        popup.hide();


}

    }    


