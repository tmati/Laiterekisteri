/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.scene.control.ChoiceBox;

/**
 *
 * @author jukka
 */
public interface ChoiceboxUtilsIf {

    /**
     * Laitaa choice boxiin valuen riipuen siitä jos se on true tai false
     * @param cb jonka arvon muutetaan
     */
    void teeLuettava(ChoiceBox cb);

    /**
     * Tulkitsee onko cb saatavilla jos on palautaa true
     * @param cb Stringi jota verataan
     * @return truen jos sen on saatavilla
     */
    boolean tulkitseBooleanBox(String cb);

    /**
     * Tulkitsee ChoiceBoxin valinnan resurssiparametrin kaipaamaksi numeroksi.
     *
     * @param cb Käsiteltävä choicebox.
     * @return Luvanvaraisuustasoa vastaava numeroarvo.
     */
    int tulkitseChoiceBox(ChoiceBox cb);
    
}
