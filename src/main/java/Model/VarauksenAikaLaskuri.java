/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;

/**
 * Luoka jonka tehtävänä on laskea kahden päivän välisen päivien määrä.
 * @author jukka
 */

public class VarauksenAikaLaskuri implements VarauksenAikaLaskuriInterface{

    private int erotusk;
    private int erotusp;
    private int erotusv;

    /**
     * Laskee kuinka monta kuukauta menee ja mitkä kuukauta. Lisää ne sitten päiviin
     * @param alkupvm aloitamis päivä
     * @param paatymispvm lopetus päivä
     * @return alku ja paatymispvm valilla olevat kuukauksien määrän.
     */
    private int KuukausiKesto(LocalDateTime alkupvm, LocalDateTime paatymispvm, int vuosiEro) {
        erotusv = vuosiEro;
        erotusk = 0;
        erotusp = 0;
        int vuosi = 0;
       
            erotusk = erotusv * 12 + paatymispvm.getMonthValue() - alkupvm.getMonthValue();
            for (int y = 1; y < erotusk; y++) {
                switch ((alkupvm.getMonthValue() + y) % 12) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                        erotusp = erotusp + 31;
                        break;
                    case 0:
                        erotusp = erotusp + 31;
                        vuosi++;
                        break;
                    case 2:
                        if ((((alkupvm.getYear()+vuosi) % 4 == 0) && ((alkupvm.getYear()+vuosi) % 100 != 0)) || ((alkupvm.getYear()+vuosi) % 400 == 0)) {
                            erotusp = erotusp + 29;
                        } else {
                            erotusp = erotusp + 28;
                        }
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        erotusp = erotusp + 30;
                        break;
                }
            }
        return erotusk;
    }

    /**
     * Laskee alkupvm ja paatymispvm erotuksen
     * @param alkupvm alkamis päivä 
     * @param paatymispvm loppumispäivä
     * @return niiden kahden erotus
     */
    public int PaivaKesto(LocalDateTime alkupvm, LocalDateTime paatymispvm) {
        erotusk = KuukausiKesto(alkupvm, paatymispvm, VuodenKesto(alkupvm, paatymispvm));
        if(erotusk == 0){
            erotusp = paatymispvm.getDayOfMonth() - alkupvm.getDayOfYear() + erotusp; 
        }else{
            switch ((alkupvm.getMonthValue())) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        erotusp = erotusp + 31 - alkupvm.getDayOfMonth();
                        break;
                    case 2:
                        if ((((alkupvm.getYear()) % 4 == 0) && ((alkupvm.getYear()) % 100 != 0)) || ((alkupvm.getYear()) % 400 == 0)) {
                            erotusp = erotusp + 29 - alkupvm.getDayOfMonth();
                        } else {
                            erotusp = erotusp + 28 - alkupvm.getDayOfMonth();
                        }
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        erotusp = erotusp + 30 - alkupvm.getDayOfMonth();
                        break;
                }
            switch ((paatymispvm.getMonthValue())) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        erotusp = erotusp + paatymispvm.getDayOfMonth();
                        break;
                    case 2:
                        if ((((alkupvm.getYear()) % 4 == 0) && ((alkupvm.getYear()) % 100 != 0)) || ((alkupvm.getYear()) % 400 == 0)) {
                            erotusp = erotusp + paatymispvm.getDayOfMonth();
                        } else {
                            erotusp = erotusp + paatymispvm.getDayOfMonth();
                        }
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        erotusp = erotusp + paatymispvm.getDayOfMonth();
                        break;
                }
        }
        return erotusp;
    }
    
    /**
     * Laskee paatymis ja alkupvm erotuksen 
     * @param alkupvm Aloitus päivä
     * @param paatymispvm lopetus päivä
     * @return 
     */
    private int VuodenKesto(LocalDateTime alkupvm, LocalDateTime paatymispvm){

        return paatymispvm.getYear() - alkupvm.getYear();
    }

}
