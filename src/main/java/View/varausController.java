package View;

import Controller.Controller;
import Model.Resurssit;
import Model.Varaukset;
import Model.VarauksetAccessObject;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.util.Callback;
import javafx.util.converter.LocalTimeStringConverter;

/**
 * Varaus-popupin toiminnot.
 *
 * @author tmati
 */
public class varausController implements Initializable {

    @FXML
    private Pane varausPane;
    @FXML
    private Label itemLabel;
    @FXML
    private DatePicker mistaDp;
    @FXML
    private Button varausNappi;
    @FXML
    private Label titleLabel;
    @FXML
    private Spinner<?> mistaSpinner;
    @FXML
    private DatePicker mihinDp;
    @FXML
    private Label mihinLabel;
    @FXML
    private Spinner<?> mihinSpinner;
    @FXML
    private Label mistaLabel;
    @FXML
    private TextArea lisatiedotTextbox;
    @FXML
    private Button sulkuNappi;
    @FXML
    private Label lisatiedotLabel;

    private Controller controller;
    
    private ArrayList<Varaukset> aVaraukset;

    /**
     * Initializes the controller class.
     *
     * @param url URL
     * @param rb ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
        controller = View.controller; 
        

        //Katsoo kaikki varaaukset sille tuoteelle.
        ArrayList<Varaukset> aVaraukset = new ArrayList<Varaukset>();

        int resurssiId = View.booking.getId();
        System.out.println(resurssiId);
        Varaukset[] varaukset = controller.haeKaikkiVaraukset();
        for(int i=0; i<varaukset.length; i++){
            if(varaukset[i].getResurssit().getId() == resurssiId){
                aVaraukset.add(varaukset[i]);   
                System.out.println("Valitun resursin Id = " + resurssiId + " varauksen Id =  " + varaukset[i].getResurssit().getId());

            }
        }
        Varaukset[] varaus = new Varaukset[aVaraukset.size()];
        for(int i=0; i<aVaraukset.size(); i++){
            varaus[i] = aVaraukset.get(i);
        }
        //Muokaa DatePickereita jotta näkyy varaukset.
        mihinDp.setDayCellFactory(controller.dayCellFactory(varaus));
        mistaDp.setDayCellFactory(controller.dayCellFactory(varaus));
        
        // Aikaformaatti
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        //Uusi SpinnerValueFactory-olio. Näitä tarvitaan joka spinnerille.
        SpinnerValueFactory mistaFactory = new SpinnerValueFactory<LocalTime>() {
            {
                setConverter(new LocalTimeStringConverter(formatter, null));
            }

            //Toiminta spinneriä alaspäin klikattaessa.
            @Override
            public void decrement(int steps) {
                if (getValue() == null) {
                    setValue(LocalTime.NOON);
                } else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.minusMinutes(time.getMinute()));
                    setValue(time.minusHours(steps));

                }
            }

            //Spinneriä ylöspäin klikatessa.
            @Override
            public void increment(int steps) {
                if (getValue() == null) {
                    setValue(LocalTime.NOON);
                } else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.minusMinutes(time.getMinute()));
                    setValue(time.plusHours(steps));
                }
            }

        };
        //Mihin - Spinnerin factory
        SpinnerValueFactory mihinFactory = new SpinnerValueFactory<LocalTime>() {
            {
                setConverter(new LocalTimeStringConverter(formatter, null));
            }

            @Override
            public void decrement(int steps) {
                if (getValue() == null) {
                    setValue(LocalTime.NOON);
                } else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.minusHours(steps));

                }
            }

            @Override
            public void increment(int steps) {
                if (getValue() == null) {
                    setValue(LocalTime.NOON);
                } else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.plusHours(steps));
                }
            }

        };

        //TODO Tähän varattavan tuotteen nimi jotakin kautta.
        itemLabel.setText(View.booking.getNimi());

        //ValueFactoryiden määrittäminen spinnereilleen.
        mistaSpinner.setValueFactory(mistaFactory);
        mihinSpinner.setValueFactory(mihinFactory);
    }

    /**
     * Toiminta varausnappia painettaessa.
     *
     * @param event Hiiren klikkaus painikkeeseen.
     */
    @FXML
    private void varausNappiPainettu(ActionEvent event) {
        LocalDate startDate = mistaDp.getValue();
        LocalTime startTime = (LocalTime) mistaSpinner.getValue();

        LocalDateTime startStamp = LocalDateTime.of(startDate, startTime);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Timestamp ts1 = Timestamp.valueOf(startStamp.format(dtf));
        
        //Loppuen
        LocalDate endDate = mihinDp.getValue();
        LocalTime endTime = (LocalTime) mihinSpinner.getValue();
        LocalDateTime endStamp = LocalDateTime.of(endDate, endTime);
        Timestamp ts2 = Timestamp.valueOf(endStamp.format(dtf));
        boolean onnistuu = true;
        // Katsoo miten menee siirän tän myöhemmin tod näk modeliin.
        for (Varaukset varaukset : aVaraukset) {
            //if (varaukset.getAlkuAika().getMonthValue() <= endDate.getMonthValue() || varaukset.getLoppuAika().getDayOfMonth() >= startDate.getDayOfMonth()) {
                if (varaukset.getAlkuAika().getMonthValue() == startDate.getMonthValue() && varaukset.getAlkuAika().getMonthValue() == endDate.getMonthValue() && varaukset.getLoppuAika().getMonthValue() == startDate.getMonthValue()) {
                    if (varaukset.getAlkuAika().getDayOfMonth() == startDate.getDayOfMonth() && varaukset.getAlkuAika().getDayOfMonth() == endDate.getDayOfMonth()) {
                        if (varaukset.getAlkuAika().getHour() > startTime.getHour() && varaukset.getAlkuAika().getHour() < endTime.getHour()) {
                            onnistuu = false;
                        }
                    }else if (varaukset.getLoppuAika().getDayOfMonth() == startDate.getDayOfMonth()) {
                        if (varaukset.getLoppuAika().getHour() > startTime.getHour()) {
                            onnistuu = false;
                        }
                    }else if (varaukset.getAlkuAika().getDayOfMonth() == endDate.getDayOfMonth()) {
                        if (varaukset.getAlkuAika().getHour() < endTime.getHour()) {
                            onnistuu = false;
                        }
                    }else if (varaukset.getLoppuAika().getDayOfMonth() > startDate.getDayOfMonth() && varaukset.getLoppuAika().getDayOfMonth() < endDate.getDayOfMonth()) {
                        onnistuu = false;
                    } else if (varaukset.getAlkuAika().getDayOfMonth() > startDate.getDayOfMonth() && varaukset.getAlkuAika().getDayOfMonth() < endDate.getDayOfMonth()) {
                        onnistuu = false;
                    }
                }else if(varaukset.getAlkuAika().getMonthValue() == endDate.getMonthValue() || varaukset.getAlkuAika().getMonthValue() == startDate.getMonthValue() ){
                    if(varaukset.getAlkuAika().getDayOfMonth() == startDate.getDayOfMonth()){
                        onnistuu = false;
                    }else if(varaukset.getAlkuAika().getDayOfMonth() == endDate.getDayOfMonth()){
                        if (varaukset.getAlkuAika().getHour() < endTime.getHour()) {
                            onnistuu = false;
                        }
                    }
                }else if(varaukset.getLoppuAika().getMonthValue() == endDate.getMonthValue() || varaukset.getLoppuAika().getMonthValue() == startDate.getMonthValue() ){
                    if(varaukset.getLoppuAika().getDayOfMonth() == endDate.getDayOfMonth()){
                        onnistuu = false;
                    }else if(varaukset.getLoppuAika().getDayOfMonth() == startDate.getDayOfMonth()){
                        if (varaukset.getLoppuAika().getHour() > startTime.getHour()) {
                            onnistuu = false;
                        }
                    }
                }else if(varaukset.getLoppuAika().getMonthValue() < endDate.getMonthValue() && varaukset.getLoppuAika().getMonthValue() > startDate.getMonthValue() ){
                    onnistuu = false;
                }else if(varaukset.getAlkuAika().getMonthValue() < endDate.getMonthValue() && varaukset.getAlkuAika().getMonthValue() > startDate.getMonthValue() ){
                    onnistuu = false;
                }else if(varaukset.getAlkuAika().getDayOfMonth() > varaukset.getLoppuAika().getDayOfMonth() && varaukset.getAlkuAika().getMonthValue() == varaukset.getLoppuAika().getMonthValue() ){
                    onnistuu = false;
                }else if(varaukset.getAlkuAika().getMonthValue() > varaukset.getLoppuAika().getMonthValue() ){
                    onnistuu = false;
                }
            }
        //}
        

        
        if(onnistuu){
            System.out.println("Alku: " + startDate.toString() + " | " + startTime.truncatedTo(ChronoUnit.MINUTES).toString());
            System.out.println("Loppu: " + endDate.toString() + " | " + endTime.truncatedTo(ChronoUnit.MINUTES).toString());

            //Lisätiedot
            String info = lisatiedotTextbox.getText();
            System.out.println(info);

            Varaukset V = new Varaukset(View.loggedIn, View.booking, startStamp, endStamp, info, false, View.booking.getNimi(), false);
            controller.luoVaraus(V);
            View.booking = null;
            this.sulkuNappiPainettu(event);
        }else{
            Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText("Varauksen teko epäonnistui, koska varauksen aika meni toisen varauksen kanssa päälekäin.");
            a.setX(0);
            a.setY(0);
            a.show();            
        }
        
    }

    /**
     * Sulkee popup-näkymän.
     *
     * @param event Hiiren klikkaus.
     */
    @FXML
    private void sulkuNappiPainettu(ActionEvent event) {
        Popup popup = (Popup) sulkuNappi.getScene().getWindow();
        popup.hide();
    }

}
