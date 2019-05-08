/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.*;
import model.BooleanConverter;
import model.Kayttaja;
import model.Resurssit;
import model.Varaukset;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javax.mail.MessagingException;
import model.Istunto;

/**
 * Varausten tarkasteluun käytettävän näkymän toiminnot
 *
 * @author tmati
 */
public class VarausAdminController implements VarausAdminControllerIf {

    @FXML
    private Label usernameLabel;
    @FXML
    private Button logoutBtn;
    @FXML
    private Label bizName;
    @FXML
    private TableView<Varaukset> varauksetTableView;
    @FXML
    private TableColumn nimiColumn;
    @FXML
    private TableColumn tavaraColumn;
    @FXML
    private TableColumn alkupvmColumn;
    @FXML
    private TableColumn paattymispvmColumn;
    @FXML
    private TableColumn kuvausColumn;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button takaisinBtn;
    @FXML
    private Button hyvaksyBtn;
    @FXML
    private Button hylkaaBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private TableView<Varaukset> kaikkiTable;
    @FXML
    private TableColumn varaajannimiColumn;
    @FXML
    private TableColumn laitenimiColumn;
    @FXML
    private TableColumn kaikkialkupvmColumn;
    @FXML
    private TableColumn kaikkipaattymispvmColumn;
    @FXML
    private TableColumn varausidColumn;
    @FXML
    private TableColumn varauskuvausColumn;
    @FXML
    private TableColumn palautettuColumn;
    @FXML
    private TableColumn hyvaksyntaColumn;
    @FXML
    private TableColumn cbColumn;
    @FXML
    private Tab kasittelemattomatTab;
    @FXML
    private Tab kaikkiTab;
    @FXML
    private Button poistaBtn;
    private ControllerIf controller;
    
    @FXML
    private TabPane tabPane;

    /**
     * Initializes the CONTROLLER class.
     *
     * @param url URL
     * @param rb ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        poistaBtn.setOpacity(0);
        poistaBtn.setDisable(true);
        controller = Controller.getInstance();
        BooleanConverter aktiivisuusController = new BooleanConverter(controller.getConfigTeksti("isActive").toUpperCase(), controller.getConfigTeksti("isnActive").toUpperCase());
        BooleanConverter hyvaksyntaController = new BooleanConverter(controller.getConfigTeksti("acknowledged").toUpperCase(), controller.getConfigTeksti("hylatty").toUpperCase());
        
        
        nimiColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Kayttaja>("kayttaja"));
        nimiColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Kayttaja>() {
            @Override
            public String toString(Kayttaja k) {
                return k.getNimi();
            }

            @Override
            public Kayttaja fromString(String string) {
                Kayttaja kayttaja = (Kayttaja) nimiColumn.getCellData(this);
                kayttaja.setNimi(string);
                return kayttaja;
            }
        }));

        tavaraColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Resurssit>("resurssit"));
        tavaraColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Resurssit>() {
            public String toString(Resurssit r) {
                return r.getNimi();
            }

            @Override
            public Resurssit fromString(String string) {
                Resurssit resurssit = (Resurssit) tavaraColumn.getCellData(this);
                resurssit.setNimi(string);
                return resurssit;
            }
        }));

        alkupvmColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Timestamp>("alkupvm"));
        alkupvmColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Timestamp>(){
            @Override
            public String toString(Timestamp object) {
                return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(object);
            }

            @Override
            public Timestamp fromString(String string) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                    java.sql.Date parsedDate = (java.sql.Date) dateFormat.parse(string);
                    return new java.sql.Timestamp(parsedDate.getTime());
                
                } catch (Exception e) {
                      Istunto.LOGGER.log(Level.SEVERE, e.getMessage());
                }
                return null;
            }
        }));

        

        paattymispvmColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Timestamp>("paattymispvm"));
        paattymispvmColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Timestamp>(){
            @Override
            public String toString(Timestamp object) {
                return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(object);
            }

            @Override
            public Timestamp fromString(String string) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                    java.sql.Date parsedDate = (java.sql.Date) dateFormat.parse(string);
                    return new java.sql.Timestamp(parsedDate.getTime());
                    
                } catch (Exception e) {
                     Istunto.LOGGER.log(Level.SEVERE, e.getMessage());
                }
                return null;
            }
        }));

        kuvausColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, String>("kuvaus"));
        kuvausColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        bizName.setText(controller.getBizname());
        usernameLabel.setText(controller.getLoggedIn().getNimi());

        Varaukset[] varaukset = controller.haeKasittelemattomatVaraukset();
        varauksetTableView.getItems().addAll(varaukset);
        
        
        
        varaajannimiColumn.setCellValueFactory(new PropertyValueFactory <Varaukset, Kayttaja>("kayttaja"));
        varaajannimiColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Kayttaja>() {
            @Override
            public String toString(Kayttaja object) {
                return object.getNimi();
            }
            
            @Override
            public Kayttaja fromString(String string) {
                Kayttaja kayttaja2 = (Kayttaja) tavaraColumn.getCellData(this);
                kayttaja2.setNimi(string);
                return kayttaja2;
            }
        }));
        
        laitenimiColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Resurssit>("resurssit"));
        laitenimiColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Resurssit>() {
            public String toString(Resurssit r) {
                return r.getNimi();
            }

            @Override
            public Resurssit fromString(String string) {
                Resurssit resurssit = (Resurssit) tavaraColumn.getCellData(this);
                resurssit.setNimi(string);
                return resurssit;
            }
        }));
        
        kaikkialkupvmColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Date>("alkupvm"));
        kaikkialkupvmColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));

        kaikkipaattymispvmColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Date>("paattymispvm"));
        kaikkipaattymispvmColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        
        varausidColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Integer>("id"));
        varausidColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        varauskuvausColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, String>("kuvaus"));
        varauskuvausColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        ChoiceBoxTableCell cc = new ChoiceBoxTableCell();
        
        cc.setConverter(aktiivisuusController);
        palautettuColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Boolean>("palautettu"));
        palautettuColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(cc.getConverter(), true, false));
        
        hyvaksyntaColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Boolean>("hyvaksytty"));
        hyvaksyntaColumn.setCellFactory(TextFieldTableCell.forTableColumn(hyvaksyntaController));
                    
        kaikkiTable.getItems().addAll(controller.haeKaikkiVaraukset());
        
        logoutBtn.setText(controller.getConfigTeksti("Logout").toUpperCase());
        takaisinBtn.setText(controller.getConfigTeksti("returnButton").toUpperCase());
        hyvaksyBtn.setText(controller.getConfigTeksti("accept").toUpperCase());
        hylkaaBtn.setText(controller.getConfigTeksti("hylkaa").toUpperCase());
        kasittelemattomatTab.setText(controller.getConfigTeksti("kasVaraukset"));
        nimiColumn.setText(controller.getConfigTeksti("varaajaNimi").toUpperCase());
        tavaraColumn.setText(controller.getConfigTeksti("item").toUpperCase());
        alkupvmColumn.setText(controller.getConfigTeksti("reservationStartdate").toUpperCase());
        paattymispvmColumn.setText(controller.getConfigTeksti("reservationEnddate").toUpperCase());
        kuvausColumn.setText(controller.getConfigTeksti("description").toUpperCase());
        kaikkiTab.setText(controller.getConfigTeksti("kaikkiVar"));
        varaajannimiColumn.setText(controller.getConfigTeksti("varaajaNimi").toUpperCase());
        laitenimiColumn.setText(controller.getConfigTeksti("resourceName").toUpperCase());
        kaikkialkupvmColumn.setText(controller.getConfigTeksti("reservationStartdate").toUpperCase());
        kaikkipaattymispvmColumn.setText(controller.getConfigTeksti("reservationEnddate").toUpperCase());
        varausidColumn.setText(controller.getConfigTeksti("reservationId").toUpperCase());
        varauskuvausColumn.setText(controller.getConfigTeksti("description").toUpperCase());
        palautettuColumn.setText(controller.getConfigTeksti("activity").toUpperCase());
        hyvaksyntaColumn.setText(controller.getConfigTeksti("approval").toUpperCase());
        poistaBtn.setText(controller.getConfigTeksti("remove").toUpperCase());
        
        this.logoutBtn.setTooltip(new Tooltip(controller.getConfigTeksti("logoutInfo")));
        this.hylkaaBtn.setTooltip(new Tooltip(controller.getConfigTeksti("hylkaaBtn")));
        this.hyvaksyBtn.setTooltip(new Tooltip(controller.getConfigTeksti("hyvaksyBtn")));
        this.takaisinBtn.setTooltip(new Tooltip(controller.getConfigTeksti("returnButton")));
        
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>(){
            
            @Override
            public void changed(ObservableValue<? extends Tab> ov, Tab kasittelemattomatTab, Tab kaikkiTab) {
                if (tabPane.getSelectionModel().getSelectedItem().getText().equals("Kaikki varaukset") 
                        || tabPane.getSelectionModel().getSelectedItem().getText().equals("All reservations")
                        || tabPane.getSelectionModel().getSelectedItem().getText().equals("Todas as reservas")){
                    hylkaaBtn.setDisable(true);
                    hylkaaBtn.setOpacity(0);
                    hyvaksyBtn.setOpacity(0);
                    hyvaksyBtn.setDisable(true);
                    poistaBtn.setOpacity(1);
                    poistaBtn.setDisable(false);
                } else {
                    hylkaaBtn.setDisable(false);
                    hylkaaBtn.setOpacity(1);
                    hyvaksyBtn.setOpacity(1);
                    hyvaksyBtn.setDisable(false);
                    poistaBtn.setOpacity(0);
                    poistaBtn.setDisable(true);
                }   
            }
        });
    }

    /**
     * Päivittää napin ulkonäön.
     * @param event Painikkeen klikkaus.
     */
    @Override
    public void updateBtnPainettu(MouseEvent event) {
        varauksetTableView.getItems().clear();
        Varaukset[] varaukset = controller.haeKasittelemattomatVaraukset();
        varauksetTableView.getItems().addAll(varaukset);
    }

    /*
     * Kirjaa käyttäjän ulos.
     * @param event Painikkeen klikkaus
     * @throws IOException IOException
     */
    @Override
    public void logout(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Loginwindow.fxml"));
        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        Parent root = loader.load();
        stage.getScene().setRoot(root);
        controller.setLoggedIn(null);
    }

    /**
     * palauttaa käyttäjän pääsivulle.
     *
     * @param event MouseEvent
     * @throws IOException IOException
     */
    @FXML
    private void takaisinBtnPainettu(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/nakyma.fxml"));
        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        Parent root = loader.load();
        stage.getScene().setRoot(root);
    }

    /**
     * Toiminta varauksen hyväksyntänapin painalluksen jälkeen. Hyväksyy
     * varauksen ja päivittää sen tietokantaan.
     * @param event Hiiren klikkaus painikkeeseen
     */
    @FXML
    private void hyvaksyBtnPainettu(MouseEvent event){
        Varaukset varaus = varauksetTableView.getSelectionModel().getSelectedItem();
        if (varaus != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION, controller.getConfigTeksti("hyvaksyVarausConf"), ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                varaus.setHyvaksytty(true);
                controller.paivitaVaraus(varaus);
                controller.lahetaSahkoposti(varaus.getKayttaja().getSahkoposti(), controller.getVarausAikaString(varaus) + controller.getConfigTeksti("hyvaksyVarausEmail"));
                this.updateBtnPainettu(event);

            }
        } else {
            Alert alert = new Alert(AlertType.WARNING, controller.getConfigTeksti("valitseKasVaraus"));
            alert.showAndWait();
        }
    }

    /**
     * Toiminta varauksen hylkäysnapin painalluksen jälkeen. Hylkää varauksen ja
     * päivittää sen tietokantaan.
     * @param event Hiiren klikkaus painikkeeseen
     */
    @FXML
    private void hylkaaBtnPainettu(MouseEvent event) throws MessagingException {
        Varaukset varaus = varauksetTableView.getSelectionModel().getSelectedItem();
        if (varaus != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION, controller.getConfigTeksti("hylataVarausConf"), ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                controller.poistaVaraus(varaus.getId());
                controller.lahetaSahkoposti(varaus.getKayttaja().getSahkoposti(), controller.getVarausAikaString(varaus) + controller.getConfigTeksti("hylataVarausEmail"));
                this.updateBtnPainettu(event);
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING, controller.getConfigTeksti("valitseKasVaraus"));
            alert.showAndWait();
        }

    }

    /**
     * Alkupvm Taulun edit commit - toiminto. Tapahtuu kun varauksen päivämäärää
     * muutetaan.
     * @param event Arvon muuttamisen jälkeen tapahtuva Enter-painallus
     */
    @FXML
    private void alkupvmEditCommit(TableColumn.CellEditEvent<Varaukset, LocalDateTime> event) {
        Varaukset varaus = varauksetTableView.getSelectionModel().getSelectedItem();
        varaus.setAlkuAika(event.getNewValue());
        controller.paivitaVaraus(varaus);
    }

    /**
     * Päättymispvm taulun edit commit - toiminto. Tapahtuu kun varauksen
     * päivämäärää muutetaan.
     * @param event Arvon muuttumisen jälkeen tapahtuva Enter-painallus
     */
    @FXML
    private void paattymispvmEditCommit(TableColumn.CellEditEvent<Varaukset, LocalDateTime> event) {
        Varaukset varaus = varauksetTableView.getSelectionModel().getSelectedItem();
        varaus.setLoppuAika(event.getNewValue());
        controller.paivitaVaraus(varaus);
    }
    
    /**
     * Kuvauksen edit commit - toiminto. Tapahtuu kun varauksen kuvausta
     * muutetaan.
     * @param event Arvon muuttamisen jälkeen tapahtuva Enter - painallus.
     */
    @FXML
    private void kuvausEditCommit(TableColumn.CellEditEvent<Varaukset, String> event) {
        Varaukset varaus = varauksetTableView.getSelectionModel().getSelectedItem();
        varaus.setKuvaus(event.getNewValue());
        controller.paivitaVaraus(varaus);
    }
    
    /**
     * Varauksen poisto-painikkeen painallus.
     * @param event Hiiren klikkaus painikkeeseen.
     * @throws IOException Tiedostonkäsittelypoikkeus.
     */
    @FXML
    private void poistaBtnPainettu(MouseEvent event) throws IOException {
            Varaukset varaus = kaikkiTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, controller.getConfigTeksti("confirmationRemoveReservation"), ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                kaikkiTable.getItems().remove(varaus);
                controller.poistaVaraus(varaus.getId());
                kaikkiTable.refresh();
            }
    }
}
