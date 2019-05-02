/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import Model.BooleanConverter;
import Model.Kayttaja;
import Model.Resurssit;
import Model.Varaukset;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javax.mail.MessagingException;

/**
 * Varausten tarkasteluun käytettävän näkymän toiminnot
 *
 * @author tmati
 */
public class VarausAdminController implements Initializable {

    @FXML
    private Label usernameLabel;
    @FXML
    private Button LogoutBtn;
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
    private Button poistaBtn;
    
    private Controller controller;
    
    @FXML
    private TabPane tabPane;

    /**
     * Initializes the controller class.
     *
     * @param url URL
     * @param rb ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        poistaBtn.setOpacity(0);
        poistaBtn.setDisable(true);
        controller = View.controller;
        BooleanConverter AktiivisuusController = new BooleanConverter(controller, "Aktiivinen", "Ei aktiivinen");
        BooleanConverter HyvaksyntaController = new BooleanConverter(controller, "HYVÄKSYTTY", "HYLÄTTY");
        
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
                    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                    return timestamp;
                } catch (Exception e) {
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
                    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                    return timestamp;
                } catch (Exception e) {
                }
                return null;
            }
        }));

        kuvausColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, String>("kuvaus"));
        kuvausColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        bizName.setText(View.BizName);
        usernameLabel.setText(View.loggedIn.getNimi());

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
        
        ChoiceBoxTableCell CC = new ChoiceBoxTableCell();
        
        CC.setConverter(AktiivisuusController);
        palautettuColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Boolean>("palautettu"));
        palautettuColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(CC.getConverter(), true, false));
        
        hyvaksyntaColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Boolean>("hyvaksytty"));
        hyvaksyntaColumn.setCellFactory(TextFieldTableCell.forTableColumn(HyvaksyntaController));
                    
        kaikkiTable.getItems().addAll(controller.haeKaikkiVaraukset());
        
        this.LogoutBtn.setTooltip(new Tooltip("Ulos kirjautuminen"));
        this.hylkaaBtn.setTooltip(new Tooltip("Hylkää valitun varauksen"));
        this.hyvaksyBtn.setTooltip(new Tooltip("Hyväksyy valitun varauksen"));
        this.takaisinBtn.setTooltip(new Tooltip("Palauttaa päänäkymään"));
        
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>(){
            
            @Override
            public void changed(ObservableValue<? extends Tab> ov, Tab kasittelemattomatTab, Tab kaikkiTab) {
                if (tabPane.getSelectionModel().getSelectedItem().getText().equals("Kaikki varaukset")) {
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
     *
     * @param event Painikkeen klikkaus.
     */
    public void updateBtnPainettu(MouseEvent event) {
        varauksetTableView.getItems().clear();
        Varaukset[] varaukset = controller.haeKasittelemattomatVaraukset();
        varauksetTableView.getItems().addAll(varaukset);
    }

    /*
     * Kirjaa käyttäjän ulos.
     *
     * @param event Painikkeen klikkaus
     * @throws IOException IOException
     */
    public void logout(MouseEvent event) throws IOException {
        System.out.println("Logout");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Loginwindow.fxml"));
        Stage stage = (Stage) LogoutBtn.getScene().getWindow();
        Parent root = loader.load();
        stage.getScene().setRoot(root);
        View.loggedIn = null;
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
        Stage stage = (Stage) LogoutBtn.getScene().getWindow();
        Parent root = loader.load();
        stage.getScene().setRoot(root);
    }

    /**
     * Toiminta varauksen hyväksyntänapin painalluksen jälkeen. Hyväksyy
     * varauksen ja päivittää sen tietokantaan.
     *
     * @param event Hiiren klikkaus painikkeeseen
     */
    @FXML
    private void hyvaksyBtnPainettu(MouseEvent event){
        Varaukset V = varauksetTableView.getSelectionModel().getSelectedItem();
        if (V != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Oletko varma, että haluat hyväksyä varauksen?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                V.setHyvaksytty(true);
                controller.paivitaVaraus(V);
                controller.lahetaSahkoposti(V.getKayttaja().getSahkoposti(), controller.getVarausAikaString(V) + " on hyväksytty."
                        + "\n \nTämä on automaattinen viesti, johon ei tarvitse vastata.");
                this.updateBtnPainettu(event);
                System.out.println("Varaus hyväksytty!");
                
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING, "Valitse käsiteltävä varaus!");
            alert.showAndWait();
        }
    }

    /**
     * Toiminta varauksen hylkäysnapin painalluksen jälkeen. Hylkää varauksen ja
     * päivittää sen tietokantaan.
     *
     * @param event Hiiren klikkaus painikkeeseen
     */
    @FXML
    private void hylkaaBtnPainettu(MouseEvent event) throws MessagingException {
        Varaukset V = varauksetTableView.getSelectionModel().getSelectedItem();
        if (V != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Oletko varma, että haluat hylätä varauksen?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                controller.poistaVaraus(V.getId());
                controller.lahetaSahkoposti(V.getKayttaja().getSahkoposti(), controller.getVarausAikaString(V) + " on hylätty."
                        + "\n \nTämä on automaattinen viesti, johon ei tarvitse vastata.");
                System.out.println("Varaus hylätty!");
                this.updateBtnPainettu(event);
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING, "Valitse käsiteltävä varaus!");
            alert.showAndWait();
        }

    }

    /**
     * Alkupvm Taulun edit commit - toiminto. Tapahtuu kun varauksen päivämäärää
     * muutetaan.
     *
     * @param event Arvon muuttamisen jälkeen tapahtuva Enter-painallus
     */
    @FXML
    private void alkupvmEditCommit(TableColumn.CellEditEvent<Varaukset, LocalDateTime> event) {
        Varaukset V = varauksetTableView.getSelectionModel().getSelectedItem();
        V.setAlkuAika(event.getNewValue());
        System.out.println("Uusi alkupvm: " + V.getAlkupvm().toString());
        controller.paivitaVaraus(V);
    }

    /**
     * Päättymispvm taulun edit commit - toiminto. Tapahtuu kun varauksen
     * päivämäärää muutetaan.
     *
     * @param event Arvon muuttumisen jälkeen tapahtuva Enter-painallus
     */
    @FXML
    private void paattymispvmEditCommit(TableColumn.CellEditEvent<Varaukset, LocalDateTime> event) {
        Varaukset V = varauksetTableView.getSelectionModel().getSelectedItem();
        V.setLoppuAika(event.getNewValue());
        System.out.println("Uusi päättymispvm: " + V.getPaattymispvm().toString());
        controller.paivitaVaraus(V);
    }
    
    /**
     * Kuvauksen edit commit - toiminto. Tapahtuu kun varauksen kuvausta
     * muutetaan.
     *
     * @param event Arvon muuttamisen jälkeen tapahtuva Enter - painallus.
     */
    @FXML
    private void kuvausEditCommit(TableColumn.CellEditEvent<Varaukset, String> event) {
        Varaukset V = varauksetTableView.getSelectionModel().getSelectedItem();
        V.setKuvaus(event.getNewValue());
        System.out.println("Uusi Kuvaus: " + V.getKuvaus());
        controller.paivitaVaraus(V);
    }
    
    @FXML
    private void poistaBtnPainettu(MouseEvent event) throws IOException {
            Varaukset V = kaikkiTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Oletko varma, että haluat poistaa varauksen?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                kaikkiTable.getItems().remove(V);
                controller.poistaVaraus(V.getId());
                kaikkiTable.refresh();
            }
    }
}
