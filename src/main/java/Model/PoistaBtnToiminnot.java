/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Controller;
import View.View;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Tommi
 *
 * Luokka eri poistanappi toimintoja varten
 */
public class PoistaBtnToiminnot {

    private Controller controller;

    /**
     * Konstruktori
     *
     * @param controller viittaus controller luokkaan
     */
    public PoistaBtnToiminnot(Controller controller) {
        this.controller = controller;
    }

    /**
     * Toiminnallisuus varauksen poisto napeille
     *
     * @param toDelete poistettava varaus
     * @return true jos poisto onnistuu
     */
    public boolean varauksetPoistaBtn(Varaukset toDelete) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Oletko varma, että haluat poistaa varauksen?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            if (!controller.OnkoVarausAlkanut(toDelete)) {
                controller.poistaVaraus(toDelete.getId());
                System.out.println("poistetaan varaus");
                controller.lahetaSahkoposti(toDelete.getKayttaja().getSahkoposti(), controller.getVarausAikaString(toDelete) + " on poistettu esimiehen tai ylläpitäjän toimesta."
                        + "\n \nTämä on automaattinen viesti, johon ei tarvitse vastata.");
                return true;
            } else {
                alert = new Alert(Alert.AlertType.WARNING, "Et voi poistaa varausta, joka on vanhentunut tai alkanut!");
                alert.showAndWait();
                return false;
            }
        }
        return false;
    }
}
