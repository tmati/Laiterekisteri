/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Controller;
import javafx.scene.control.ChoiceBox;

/**
 *
 * @author tmati
 */
public class ChoiceboxUtils {

    private Controller kontrolleri;


    public ChoiceboxUtils(Controller kontrolleri) {
        this.kontrolleri = kontrolleri;
    }
    
        /**
     * Tulkitsee ChoiceBoxin valinnan resurssiparametrin kaipaamaksi numeroksi.
     * @param cb Käsiteltävä choicebox.
     * @return Luvanvaraisuustasoa vastaava numeroarvo.
     */
    public int tulkitseChoiceBox(ChoiceBox cb) {
        int selectedOption = -1;
        if (cb.getValue().equals("Vapaa käyttö") || cb.getValue().equals("Työntekijä")) {
            selectedOption = 0;
        }else if (cb.getValue().equals("Esimiehen hyväksyttävä") || cb.getValue().equals("Esimies")) {
            selectedOption = 1;
        }else if (cb.getValue().equals("Ylläpitäjän hyväksyttävä") || cb.getValue().equals("Ylläpitäjä")) {
            selectedOption = 2;
        }  
        return selectedOption;
    } 
}
