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

    void teeLuettava(ChoiceBox cb);

    boolean tulkitseBooleanBox(String cb);

    /**
     * Tulkitsee ChoiceBoxin valinnan resurssiparametrin kaipaamaksi numeroksi.
     *
     * @param cb Käsiteltävä choicebox.
     * @return Luvanvaraisuustasoa vastaava numeroarvo.
     */
    int tulkitseChoiceBox(ChoiceBox cb);
    
}
