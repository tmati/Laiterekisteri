/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Varaukset;
import java.net.URL;
import java.time.LocalDate;
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
 * FXML Controller class
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
        itemLabel.setText("TESTINGGGG");
        
        //ValueFactoryiden määrittäminen spinnereilleen.
        mistaSpinner.setValueFactory(mistaFactory);
        mihinSpinner.setValueFactory(mihinFactory);
    }
        
    /**
     * TODO varauksen tallettaminen tietokantaan. Ei voi tehdä ennenkuin tietokanta toimii.
     * @param event 
     */
    @FXML
    private void varausNappiPainettu(ActionEvent event) {
        //Vie tietokantaan:
        /*
        KäyttäjäID
        LaiteID
        */
        
        //Alkaen
        LocalDate startDate = mistaDp.getValue();
        LocalTime startTime = (LocalTime) mistaSpinner.getValue();
        
        System.out.println("Alku: " + startDate.toString() + " | " + startTime.truncatedTo(ChronoUnit.MINUTES).toString());
        
        //Loppuen
        LocalDate endDate = mihinDp.getValue();
        LocalTime endTime = (LocalTime) mihinSpinner.getValue();
        
        System.out.println("Loppu: " + endDate.toString() + " | " + endTime.truncatedTo(ChronoUnit.MINUTES).toString());
        
        //Lisätiedot
        String info = lisatiedotTextbox.getText();
        System.out.println(info);
        
        Boolean palautettu = false;
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
