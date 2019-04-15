/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Controller;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.MonthDay;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;

/**
 * Luoka joka muokaa datepickerin päiviä joko punaisiksi tai oransiksi.
 * @author jukka
 */
public class DayCellFactory {

    private int erotusp = 0;
    private int erotusk = 0;
    private int seuraavaKuukausi = 0;
    private int liikaPaivat = 0;
    private LocalDateTime alkupvm;
    private LocalDateTime loppumispvm;

    /**
     * Muokaa datepickerin päiviä niin että varatut ovat ounaisia ja reuna päivät ovat oranseja.
     * @param controller Kuka kutsui tätä
     * @param varaukset lista mistä katsotaan mitkä päivät ovat varattuja
     * @return päivät jotka ovat muokattu
     */
    public Callback dayCellFactory(Controller controller, Varaukset[] varaukset) {
        
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            LocalDate today = LocalDate.now();
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        for (int y = 0; y < varaukset.length; y++) {
                            erotusp = 0;
                            erotusk = 0;
                            seuraavaKuukausi = 0;
                            liikaPaivat = 0;
                            alkupvm = varaukset[y].getAlkuAika();
                            loppumispvm = varaukset[y].getLoppuAika();
                            erotusp = controller.paivaLaskuri(alkupvm, loppumispvm);
                            //System.out.print(erotusp);
                            for (int i = 0; i <= erotusp; i++) {
                                if ((alkupvm.getDayOfMonth() + i + liikaPaivat) == 29 || (alkupvm.getDayOfMonth() + i + liikaPaivat) == 31 || (alkupvm.getDayOfMonth() + i + liikaPaivat) == 32) {
                                    switch (alkupvm.getMonthValue() + seuraavaKuukausi) {
                                        case 1:
                                        case 3:
                                        case 5:
                                        case 7:
                                        case 8:
                                        case 10:
                                        case 12:
                                            if ((alkupvm.getDayOfMonth() + i + liikaPaivat) == 32) {
                                                seuraavaKuukausi = seuraavaKuukausi + 1;
                                                liikaPaivat = liikaPaivat - 31;
                                            }
                                            break;
                                        case 4:
                                        case 6:
                                        case 9:
                                        case 11:
                                            if ((alkupvm.getDayOfMonth() + i + liikaPaivat) == 31) {
                                                seuraavaKuukausi = seuraavaKuukausi + 1;
                                                liikaPaivat = liikaPaivat - 30;
                                            }
                                            break;
                                        case 2:
                                            if ((alkupvm.getDayOfMonth() + i + liikaPaivat) == 29) {
                                                seuraavaKuukausi = seuraavaKuukausi + 1;
                                                liikaPaivat = liikaPaivat - 28;
                                            }
                                            break;
                                    }
                                }
                                if ( MonthDay.from(item).equals(MonthDay.of((alkupvm.getMonthValue() + seuraavaKuukausi), (alkupvm.getDayOfMonth() + i + liikaPaivat)))) {
                                    if ((getStyle() == ("-fx-background-color: #FFA500;"))){
                                        if( erotusp == 0){
                                            setTooltip(new Tooltip("\n" + getTooltip().getText() + "Varaus alkaa " + varaukset[y].getAlkuAika().getHour() + ":0"+ varaukset[y].getAlkuAika().getMinute() + "\nVaraus päätyy " + varaukset[y].getLoppuAika().getHour() + ":0"+ varaukset[y].getLoppuAika().getMinute()));
                                        }else{
                                            setTooltip(new Tooltip("\n" + getTooltip().getText() + "Varaus alkaa " + varaukset[y].getAlkuAika().getHour() + ":0"+ varaukset[y].getAlkuAika().getMinute()));

                                        }

                                    }else if (erotusp == 0){
                                        setStyle("-fx-background-color: #FFA500;");
                                        setTooltip(new Tooltip("Varaus alkaa " + varaukset[y].getAlkuAika().getHour() + ":0"+ varaukset[y].getAlkuAika().getMinute() + "\nVaraus päätyy " + varaukset[y].getLoppuAika().getHour() + ":0"+ varaukset[y].getLoppuAika().getMinute()));
                                    }else if (i == 0) {
                                        setStyle("-fx-background-color: #FFA500;");
                                        setTooltip(new Tooltip("Varaus alkaa " + varaukset[y].getAlkuAika().getHour() + ":0"+ varaukset[y].getAlkuAika().getMinute()));
                                    } else if(i==erotusp){
                                        setStyle("-fx-background-color: #FFA500;");
                                        setTooltip(new Tooltip("Varaus päätyy " + varaukset[y].getLoppuAika().getHour() + ":0"+ varaukset[y].getLoppuAika().getMinute()));
                                    }else{
                                        setDisable(true);
                                        setStyle("-fx-background-color: #ff4444;");
                                    }
                                }                               
                            }                             
                        }setDisable(empty || item.compareTo(today) < 0 );  
                    }
            };
        }
        };
        return dayCellFactory;

    }

    /**
     * Muokaa datepickerin päiviä niin että varatut ovat ounaisia ja reuna päivät ovat oranseja.
     * @param controller Kuka kutsui tätä
     * @param varaukset lista mistä katsotaan mitkä päivät ovat varattuja
     * @param today mistä päivästä alkaen ei voi valita
     * @return päivät jotka ovat muokattu
     */
    public Callback dayCellFactoryEnd(Controller controller, Varaukset[] varaukset, LocalDate today) {
        
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        for (int y = 0; y < varaukset.length; y++) {
                            erotusp = 0;
                            erotusk = 0;
                            seuraavaKuukausi = 0;
                            liikaPaivat = 0;
                            alkupvm = varaukset[y].getAlkuAika();
                            loppumispvm = varaukset[y].getLoppuAika();
                            erotusp = controller.paivaLaskuri(alkupvm, loppumispvm);
                            //System.out.print(erotusp);
                            for (int i = 0; i <= erotusp; i++) {
                                if ((alkupvm.getDayOfMonth() + i + liikaPaivat) == 29 || (alkupvm.getDayOfMonth() + i + liikaPaivat) == 31 || (alkupvm.getDayOfMonth() + i + liikaPaivat) == 32) {
                                    switch (alkupvm.getMonthValue() + seuraavaKuukausi) {
                                        case 1:
                                        case 3:
                                        case 5:
                                        case 7:
                                        case 8:
                                        case 10:
                                        case 12:
                                            if ((alkupvm.getDayOfMonth() + i + liikaPaivat) == 32) {
                                                seuraavaKuukausi = seuraavaKuukausi + 1;
                                                liikaPaivat = liikaPaivat - 31;
                                            }
                                            break;
                                        case 4:
                                        case 6:
                                        case 9:
                                        case 11:
                                            if ((alkupvm.getDayOfMonth() + i + liikaPaivat) == 31) {
                                                seuraavaKuukausi = seuraavaKuukausi + 1;
                                                liikaPaivat = liikaPaivat - 30;
                                            }
                                            break;
                                        case 2:
                                            if ((alkupvm.getDayOfMonth() + i + liikaPaivat) == 29) {
                                                seuraavaKuukausi = seuraavaKuukausi + 1;
                                                liikaPaivat = liikaPaivat - 28;
                                            }
                                            break;
                                    }
                                }
                                if ( MonthDay.from(item).equals(MonthDay.of((alkupvm.getMonthValue() + seuraavaKuukausi), (alkupvm.getDayOfMonth() + i + liikaPaivat)))) {
                                    if ((getStyle() == ("-fx-background-color: #FFA500;"))){
                                        if( erotusp == 0){
                                            setTooltip(new Tooltip("\n" + getTooltip().getText() + "Varaus alkaa " + varaukset[y].getAlkuAika().getHour() + ":0"+ varaukset[y].getAlkuAika().getMinute() + "\nVaraus päätyy " + varaukset[y].getLoppuAika().getHour() + ":0"+ varaukset[y].getLoppuAika().getMinute()));
                                        }else{
                                            setTooltip(new Tooltip("\n" + getTooltip().getText() + "Varaus alkaa " + varaukset[y].getAlkuAika().getHour() + ":0"+ varaukset[y].getAlkuAika().getMinute()));

                                        }

                                    }else if (erotusp == 0){
                                        setStyle("-fx-background-color: #FFA500;");
                                        setTooltip(new Tooltip("Varaus alkaa " + varaukset[y].getAlkuAika().getHour() + ":0"+ varaukset[y].getAlkuAika().getMinute() + "\nVaraus päätyy " + varaukset[y].getLoppuAika().getHour() + ":0"+ varaukset[y].getLoppuAika().getMinute()));
                                    }else if (i == 0) {
                                        setStyle("-fx-background-color: #FFA500;");
                                        setTooltip(new Tooltip("Varaus alkaa " + varaukset[y].getAlkuAika().getHour() + ":0"+ varaukset[y].getAlkuAika().getMinute()));
                                    } else if(i==erotusp){
                                        setStyle("-fx-background-color: #FFA500;");
                                        setTooltip(new Tooltip("Varaus päätyy " + varaukset[y].getLoppuAika().getHour() + ":0"+ varaukset[y].getLoppuAika().getMinute()));
                                       }else{
                                        setDisable(true);
                                        setStyle("-fx-background-color: #ff4444;");
                                    }
                                }
                                setDisable(empty || item.compareTo(today) < 0 );
                            }
                        }
                    }
            };
        }
        };
        return dayCellFactory;

    }

}
