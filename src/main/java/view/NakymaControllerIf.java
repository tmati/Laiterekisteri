/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javax.transaction.Transactional;
import model.Varaukset;

/**
 *
 * @author jukka
 */
public interface NakymaControllerIf extends Initializable {

    /**
     * Poistaa taulukosta ja tietokannasta sekä päivittää taulukon
     *
     * @param varaus poistettava varaus
     */
    void completeRemove(Varaukset varaus);

    /**
     * Siirtyy varausten hallintanäkymään
     *
     * @param event Hiiren painallus painikkeesta.
     * @throws IOException Tiedostoja käsitellessä varauduttava poikkeus.
     */
    @FXML
    void hallinnoiBtnPainettu(MouseEvent event) throws IOException;

    /**
     * Siirtyy henkilöstön hallintanäkymään
     *
     * @param event Hiiren painallus käyttöliittymässä vastaavasta painikkeesta.
     * @throws IOException Tiedostoja käsiteltäessä varauduttava poikkeus.
     */
    @FXML
    void henkilostoBtnPainettu(MouseEvent event) throws IOException;

    @FXML
    void historiaBtnPainettu(MouseEvent event) throws IOException;

    /**
     * Initializes the CONTROLLER class.
     *
     * @param url url
     * @param rb rb
     */
    @Transactional
    void initialize(URL url, ResourceBundle rb);

    /**
     * Avaa "lisää resurssi" -popupin.
     *
     * @param event hiiren painallus.
     * @throws IOException Tiedostoja luettaessa varauduttava poikkeus.
     */
    @FXML
    void lisaaresurssiNappiPainettu(MouseEvent event) throws IOException;

    /**
     * Kirjaa käyttäjän ulos ja siirtyy kirjautumisnäkymään.
     *
     * @param event Hiiren painallus painikkeesta.
     * @throws IOException Tiedostoja käsiteltäessä varauduttava poikkeus.
     */
    @FXML
    void logout(MouseEvent event) throws IOException;

    /**
     * Palautaa uuden datepickerin, jolla on muutoksiin kuuntelia. Kuuntelia
     * rajaa varauksista siten että ne, jotka eivät voi varata sinä päivänä ei
     * ilmesty tulukkoon.
     *
     * @return uusi datepicker, jolla on muutoksiin kuuntelia.
     */
    DatePicker picker();

    /**
     * Poistetaan resurssi
     *
     * @param event Hiiren painallus painikkeen kohdalla.
     * @throws IOException Tiedostoja käsiteltäessä varauduttava poikkeus.
     */
    @FXML
    void poistaresurssiNappiPainettu(MouseEvent event) throws IOException;

    /**
     * Avaa vaihda salasana-popupin
     *
     * @param event Hiiren painallus
     * @throws IOException Tiedostoja käsitellessä varauduttava poikkeus.
     */
    @FXML
    void salasananvaihtoNappiPainettu(MouseEvent event) throws IOException;

    /**
     * Päivittää taulukot näkymässä.
     *
     */
    @FXML
    void update();

    /**
     * Avaa varaus-popupin
     *
     * @param event Hiiren painallus.
     * @throws IOException Tiedostoja luettaessa varauduttava poikkeus.
     */
    @FXML
    void varausNappiPainettu(MouseEvent event) throws IOException;
    
}
