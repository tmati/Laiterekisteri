/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.MonthDay;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;

/**
 * Luokka, joka muokkaa datepickerin päiviä joko punaisiksi tai oranssiksi.
 *
 * @author jukka
 */
public class DayCellFactory implements DayCellFactoryIf {

    private int erotusp;
    private int seuraavaKuukausi;
    private int liikaPaivat;
    private LocalDateTime alkupvm;
    private LocalDateTime loppumispvm;
    private int seuraavaVuosi;
    private String alkaa = "Varaus alkaa ";

    /**
     * Muokaa datepickerin päiviä niin että varatut ovat ounaisia ja reuna
     * päivät ovat oranseja.
     *
     * @param controller Kuka kutsui tätä
     * @param varaukset lista mistä katsotaan mitkä päivät ovat varattuja
     * @param today mistä päivästä alkaen ei voi valita
     * @return päivät jotka ovat muokattu
     */
    @Override
    public Callback dayCellFactory(ControllerIf controller, Varaukset[] varaukset, LocalDate today) {
        String oranssi = "-fx-background-color: #FFA500;";
        return new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        for (int y = 0; y < varaukset.length; y++) {
                            seuraavaKuukausi = 0;
                            liikaPaivat = 0;
                            seuraavaVuosi = 0;
                            alkupvm = varaukset[y].getAlkuAika();
                            loppumispvm = varaukset[y].getLoppuAika();
                            erotusp = controller.paivaLaskuri(alkupvm, loppumispvm);
                            for (int i = 0; i <= erotusp; i++) {
                                if ((alkupvm.getDayOfMonth() + i + liikaPaivat) == 29 || (alkupvm.getDayOfMonth() + i + liikaPaivat) == 31 || (alkupvm.getDayOfMonth() + i + liikaPaivat) == 32 || (alkupvm.getDayOfMonth() + i + liikaPaivat) == 30) {
                                    switch ((alkupvm.getMonthValue() + seuraavaKuukausi) % 13) {
                                        case 1:
                                        case 3:
                                        case 5:
                                        case 7:
                                        case 8:
                                        case 10:
                                            if ((alkupvm.getDayOfMonth() + i + liikaPaivat) == 32) {
                                                seuraavaKuukausi++;
                                                liikaPaivat = liikaPaivat - 31;
                                            }
                                            break;
                                        case 12:
                                            if ((alkupvm.getDayOfMonth() + i + liikaPaivat) == 32) {
                                                seuraavaKuukausi = +2;
                                                liikaPaivat = liikaPaivat - 31;
                                                seuraavaVuosi++;
                                            }
                                            break;
                                        case 4:
                                        case 6:
                                        case 9:
                                        case 11:
                                            if ((alkupvm.getDayOfMonth() + i + liikaPaivat) == 31) {
                                                seuraavaKuukausi++;
                                                liikaPaivat = liikaPaivat - 30;
                                            }
                                            break;
                                        case 2:
                                            if (((alkupvm.getDayOfMonth() + i + liikaPaivat) == 29 && !((alkupvm.getYear() + seuraavaVuosi) % 100 != 0)) || ((alkupvm.getYear() + seuraavaVuosi) % 400 == 0)) {
                                                seuraavaKuukausi++;
                                                liikaPaivat = liikaPaivat - 28;
                                            } else if ((alkupvm.getDayOfMonth() + i + liikaPaivat) == 30) {
                                                if ((((alkupvm.getYear() + seuraavaVuosi) % 4 == 0) && ((alkupvm.getYear() + seuraavaVuosi) % 100 != 0)) || ((alkupvm.getYear() + seuraavaVuosi) % 400 == 0)) {
                                                    seuraavaKuukausi++;
                                                    liikaPaivat = liikaPaivat - 29;
                                                }
                                            }
                                            break;
                                        default:
                                    }
                                
                                
                                }
                                if (MonthDay.from(item).equals(MonthDay.of(((alkupvm.getMonthValue() + seuraavaKuukausi) % 13), (alkupvm.getDayOfMonth() + i + liikaPaivat))) && item.getYear() == (alkupvm.getYear() + seuraavaVuosi)) {
                                    if ((getStyle().equals(oranssi))) {
                                        if (erotusp == 0) {
                                            setTooltip(new Tooltip("\n" + getTooltip().getText() + alkaa + varaukset[y].getAlkuAika().getHour() + ":0" + varaukset[y].getAlkuAika().getMinute() + "\nVaraus päätyy " + varaukset[y].getLoppuAika().getHour() + ":0" + varaukset[y].getLoppuAika().getMinute()));
                                        } else {
                                            setTooltip(new Tooltip("\n" + getTooltip().getText() + alkaa + varaukset[y].getAlkuAika().getHour() + ":0" + varaukset[y].getAlkuAika().getMinute()));
                                        }
                                    } else if (erotusp == 0) {
                                        setStyle(oranssi);
                                        setTooltip(new Tooltip(alkaa  + varaukset[y].getAlkuAika().getHour() + ":0" + varaukset[y].getAlkuAika().getMinute() + "\nVaraus päätyy " + varaukset[y].getLoppuAika().getHour() + ":0" + varaukset[y].getLoppuAika().getMinute()));
                                    } else if (i == 0) {
                                        setStyle(oranssi);
                                        setTooltip(new Tooltip(alkaa  + varaukset[y].getAlkuAika().getHour() + ":0" + varaukset[y].getAlkuAika().getMinute()));
                                    } else if (i == erotusp) {
                                        setStyle(oranssi);
                                        setTooltip(new Tooltip("Varaus päätyy " + varaukset[y].getLoppuAika().getHour() + ":0" + varaukset[y].getLoppuAika().getMinute()));
                                    } else {
                                        setStyle("-fx-background-color: #ff4444;");
                                        setDisable(true);
                                    }
                                }
                            }
                        }
                        setDisable(item.compareTo(today) < 0 || isDisable());
                    }
                };
            }
        };

    }

}
