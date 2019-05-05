/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Controller;

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, controller.getConfigTeksti("alertConfirmationRemoveReservation"), ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            if (!controller.onkoVarausAlkanut(toDelete)) {
                controller.poistaVaraus(toDelete.getId());
                controller.lahetaSahkoposti(toDelete.getKayttaja().getSahkoposti(), controller.getVarausAikaString(toDelete) + controller.getConfigTeksti("emailFordeletingReservation"));
                return true;
            } else {
                alert = new Alert(Alert.AlertType.WARNING, controller.getConfigTeksti("alertRemoveOldReservation"));
                alert.showAndWait();
                return false;
            }
        }
        return false;
    }
}
