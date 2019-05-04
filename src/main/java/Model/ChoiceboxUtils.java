/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Controller;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

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
     *
     * @param cb Käsiteltävä choicebox.
     * @return Luvanvaraisuustasoa vastaava numeroarvo.
     */
    public int tulkitseChoiceBox(ChoiceBox cb) {
        int selectedOption = -1;
        if (cb.getValue().equals(kontrolleri.getConfigTeksti("freeUse")) || cb.getValue().equals(kontrolleri.getConfigTeksti("employee"))) {
            selectedOption = 0;
        } else if (cb.getValue().equals(kontrolleri.getConfigTeksti("supApproved")) || cb.getValue().equals(kontrolleri.getConfigTeksti("superior"))) {
            selectedOption = 1;
        } else if (cb.getValue().equals(kontrolleri.getConfigTeksti("adApproved")) || cb.getValue().equals(kontrolleri.getConfigTeksti("administrator"))) {
            selectedOption = 2;
        }
        return selectedOption;
    }

    public boolean tulkitseBooleanBox(String cb) {
        return cb.equals(kontrolleri.getConfigTeksti("saatavilla"));
    }

    public void teeLuettava(ChoiceBox cb) {
        if (cb.getValue().equals("true")) {
            cb.setValue(kontrolleri.getConfigTeksti("notBookable"));
        } else if (cb.getValue().equals("false")) {
            cb.setValue(kontrolleri.getConfigTeksti("bookable"));

        }
    }
}
