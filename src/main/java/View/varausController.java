package View;

import Controller.Controller;
import Model.Resurssit;
import Model.Varaukset;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.converter.LocalTimeStringConverter;
import javafx.scene.control.Tooltip;

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
        Varaukset[] varaukset = controller.haeKaikkiVaraukset();
        aVaraukset = controller.ResursinVaraukset(View.booking.getId(), varaukset);

        Varaukset[] varaus = controller.getVaraus(aVaraukset);
       
        //Muokaa DatePickereita jotta näkyy varaukset.
        mihinDp.setDayCellFactory(controller.dayCellFactory(varaus, LocalDate.now()));
        mistaDp.setDayCellFactory(controller.dayCellFactory(varaus, LocalDate.now()));
        
        
        
        mistaDp.addEventHandler(ActionEvent.ACTION, 
                    new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               // System.out.println("event handler toimii");
                if(mistaDp.getValue() != null){
                                   // System.out.println("If lause toimii");

                    mihinDp.setDayCellFactory(controller.dayCellFactory(varaus, mistaDp.getValue()));
                }else{
                    mihinDp.setDayCellFactory(controller.dayCellFactory(varaus, LocalDate.now()));
                }
            }

                    });
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
        ChronoLocalDateTime alku = startDate.atTime(startTime);
        ChronoLocalDateTime loppu = endDate.atTime(endTime);
        
        if(controller.Onnistuu(aVaraukset, loppu, alku)){
            //Lisätiedot
            String info = lisatiedotTextbox.getText();
            //System.out.println(info);
            boolean b = false;
            if(View.booking.getLuvanvaraisuus() == 0){
                b = true;
            }
            Varaukset V = new Varaukset(View.loggedIn, View.booking, startStamp, endStamp, info, false, View.booking.getNimi(), b);
            controller.luoVaraus(V);
            Varaukset[] varaukset = controller.haeKayttajanVaraukset(View.loggedIn);
            Popup popup = (Popup) sulkuNappi.getScene().getWindow();
            Window nakyma = popup.getOwnerWindow();
            TableView omatTableView = (TableView) nakyma.getScene().lookup("#omatTable");
            omatTableView.getItems().clear();
            omatTableView.getItems().addAll(varaukset);
            View.booking = null;
            this.sulkuNappiPainettu(event);
        }else{
            Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText("Varauksen teko epäonnistui, koska varauksen aika meni toisen varauksen kanssa päälekäin.");
            a.show();            
        }
        
        this.lisatiedotTextbox.setTooltip(new Tooltip("Kerro varauksen syy"));
        this.mihinDp.setTooltip(new Tooltip("Valtise varauksen päättymispäivä"));
        this.mihinSpinner.setTooltip(new Tooltip("Valitse varauksen päättymisaika"));
        this.mistaDp.setTooltip(new Tooltip("Valitse varauksen alkamispäivä"));
        this.mistaSpinner.setTooltip(new Tooltip("Valitse varauksen alkamisaika"));
        this.varausNappi.setTooltip(new Tooltip("Luo varauksen"));
        this.sulkuNappi.setTooltip(new Tooltip("Sulkee popupin"));
    }

    /**
     * Sulkee popup-näkymän.
     *
     * @param event Hiiren klikkaus.
     */
    @FXML
    private void sulkuNappiPainettu(ActionEvent event) {
        Popup popup = (Popup) sulkuNappi.getScene().getWindow();
        Varaukset[] varaukset = controller.haeKayttajanVaraukset(View.loggedIn);
        Window nakyma = popup.getOwnerWindow();
        TableView omatTableView = (TableView) nakyma.getScene().lookup("#omatTable");
        omatTableView.getItems().clear();
        omatTableView.getItems().addAll(varaukset);
        popup.hide();
    }

}
