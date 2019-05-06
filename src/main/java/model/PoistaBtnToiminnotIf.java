/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author jukka
 */
public interface PoistaBtnToiminnotIf {

    /**
     * Toiminnallisuus varauksen poisto napeille
     *
     * @param toDelete poistettava varaus
     * @return true jos poisto onnistuu
     */
    boolean varauksetPoistaBtn(Varaukset toDelete);
    
}
