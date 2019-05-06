/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.ControllerIf;
import java.time.LocalDate;
import javafx.util.Callback;

/**
 *
 * @author jukka
 */
public interface DayCellFactoryIf {

    /**
     * Muokaa datepickerin päiviä niin että varatut ovat ounaisia ja reuna
     * päivät ovat oranseja.
     *
     * @param controller Kuka kutsui tätä
     * @param varaukset lista mistä katsotaan mitkä päivät ovat varattuja
     * @param today mistä päivästä alkaen ei voi valita
     * @return päivät jotka ovat muokattu
     */
    Callback dayCellFactory(ControllerIf controller, Varaukset[] varaukset, LocalDate today);
    
}
