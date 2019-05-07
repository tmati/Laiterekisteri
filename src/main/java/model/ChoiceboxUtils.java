/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.*;
import javafx.scene.control.ChoiceBox;

/**
 *
 * @author tmati
 */
public class ChoiceboxUtils implements ChoiceboxUtilsIf {
    private ControllerIf controller;
    

    public ChoiceboxUtils(Controller controller) {
        this.controller = controller;
    }

    /**
     * Tulkitsee ChoiceBoxin valinnan resurssiparametrin kaipaamaksi numeroksi.
     *
     * @param cb Käsiteltävä choicebox.
     * @return Luvanvaraisuustasoa vastaava numeroarvo.
     */
    @Override
    public int tulkitseChoiceBox(ChoiceBox cb) {
        int selectedOption = -1;
        if (cb.getValue().equals(controller.getConfigTeksti("freeUse")) || cb.getValue().equals(controller.getConfigTeksti("employee"))) {
            selectedOption = 0;
        } else if (cb.getValue().equals(controller.getConfigTeksti("supApproved")) || cb.getValue().equals(controller.getConfigTeksti("superior"))) {
            selectedOption = 1;
        } else if (cb.getValue().equals(controller.getConfigTeksti("adApproved")) || cb.getValue().equals(controller.getConfigTeksti("administrator"))) {
            selectedOption = 2;
        }
        return selectedOption;
    }

    @Override
    public boolean tulkitseBooleanBox(String cb) {
        return cb.equals(controller.getConfigTeksti("saatavilla"));
    }

    @Override
    public void teeLuettava(ChoiceBox cb) {
        if (cb.getValue().equals("true")) {
            cb.setValue(controller.getConfigTeksti("notBookable"));
        } else if (cb.getValue().equals("false")) {
            cb.setValue(controller.getConfigTeksti("bookable"));

        }
    }
}
