/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;

/**
 *
 * @author jukka
 */
public class VarauksenAikaLaskuri implements VarauksenAikaLaskuriInterface{

    private int erotusk;
    private int erotusp;

    private int KuukausiKesto(LocalDateTime alkupvm, LocalDateTime paatymispvm) {
        erotusk = 0;
        erotusp = 0;
        if (alkupvm.getMonthValue() > paatymispvm.getMonthValue()) {
            erotusk = alkupvm.getMonthValue() - paatymispvm.getMonthValue();
        } else if (paatymispvm.getMonthValue() > alkupvm.getMonthValue()) {
            erotusk = paatymispvm.getMonthValue() - alkupvm.getMonthValue();
            for (int y = 0; y < erotusk; y++) {
                switch (alkupvm.getMonthValue() + y) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        erotusp = erotusp + 31;
                        break;
                    case 2:
                        erotusp = erotusp + 28;
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        erotusp = erotusp + 30;
                        break;
                }
            }
        } else {
            erotusk = 0;
        }
        return erotusk;
    }

    public int PaivaKesto(LocalDateTime alkupvm, LocalDateTime paatymispvm) {
        erotusk = KuukausiKesto(alkupvm, paatymispvm);
        //System.out.println("Erotusp = " + erotusp);
        if (alkupvm.getDayOfMonth() > paatymispvm.getDayOfMonth()) {
            if (erotusk == 0) {
                erotusp = alkupvm.getDayOfMonth() - paatymispvm.getDayOfMonth() + erotusp;
                //System.out.println(erotusp);
            } else {
                erotusp = paatymispvm.getDayOfMonth() - alkupvm.getDayOfMonth() + erotusp;
                //System.out.println(erotusp);
            }

        } else if (erotusk == 0) {
            erotusp = paatymispvm.getDayOfMonth() - alkupvm.getDayOfMonth() + erotusp;
            //System.out.println(erotusp);

        } else {
            erotusp = paatymispvm.getDayOfMonth() - alkupvm.getDayOfMonth() + erotusp;
            //System.out.println(erotusp);

        }
        //System.out.println(" Ennen palautamista Erotusp = " + erotusp);
        return erotusp;
    }

}
