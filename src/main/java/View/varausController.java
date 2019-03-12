/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Aikaformaatti
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        //Uusi SpinnerValueFactory-olio. Näitä tarvitaan joka spinnerille.
        SpinnerValueFactory mistaFactory = new SpinnerValueFactory<LocalTime>() {{
            setConverter(new LocalTimeStringConverter(formatter,null));
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
                SpinnerValueFactory mihinFactory = new SpinnerValueFactory<LocalTime>() {{
            setConverter(new LocalTimeStringConverter(formatter,null));
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
     * 
     * @param event 
     */
    @FXML
    private void varausNappiPainettu(ActionEvent event) {
        LocalDate startDate = mistaDp.getValue();
        LocalTime startTime = (LocalTime) mistaSpinner.getValue();
        
        LocalDateTime startStamp = LocalDateTime.of(startDate, startTime);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Timestamp ts1 = Timestamp.valueOf(startStamp.format(dtf));
        
        System.out.println("Alku: " + startDate.toString() + " | " + startTime.truncatedTo(ChronoUnit.MINUTES).toString());
        
        //Loppuen
        LocalDate endDate = mihinDp.getValue();
        LocalTime endTime = (LocalTime) mihinSpinner.getValue();
        LocalDateTime endStamp = LocalDateTime.of(endDate, endTime);
        Timestamp ts2 = Timestamp.valueOf(endStamp.format(dtf));
        
        System.out.println("Loppu: " + endDate.toString() + " | " + endTime.truncatedTo(ChronoUnit.MINUTES).toString());
        
        //Lisätiedot
        String info = lisatiedotTextbox.getText();
        System.out.println(info);
        
        
        Varaukset V = new Varaukset(View.loggedIn, View.booking, startStamp, endStamp, info, false, View.booking.getNimi(), false);
        VarauksetAccessObject VAO = new VarauksetAccessObject();
        VAO.createVaraus(V);
        View.booking = null;
        
    }

    /**
     * Sulkee popup-näkymän.
     * @param event 
     */
    @FXML
    private void sulkuNappiPainettu(ActionEvent event) {
    Popup popup = (Popup) sulkuNappi.getScene().getWindow();
    popup.hide();
    }
    
}
