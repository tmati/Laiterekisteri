/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import Model.Kayttaja;
import Model.Resurssit;
import Model.Varaukset;
import Model.VarauksetAccessObject;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;
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

    private Controller controller;

    /**
     * Initializes the controller class.
     *
     * @param url URL
     * @param rb ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = View.controller;
        nimiColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Kayttaja>("kayttaja"));
        nimiColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Kayttaja>() {
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

        alkupvmColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Date>("alkupvm"));
        alkupvmColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));

        paattymispvmColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Date>("paattymispvm"));
        paattymispvmColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));

        kuvausColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, String>("kuvaus"));
        kuvausColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        bizName.setText(View.BizName);
        usernameLabel.setText(View.loggedIn.getNimi());

        Varaukset[] varaukset = controller.haeKasittelemattomatVaraukset();
        varauksetTableView.getItems().addAll(varaukset);
        
        this.LogoutBtn.setTooltip(new Tooltip("Ulos kirjautuminen"));
        this.hylkaaBtn.setTooltip(new Tooltip("Hylkää valitun varauksen"));
        this.hyvaksyBtn.setTooltip(new Tooltip("Hyväksyy valitun varauksen"));
        this.takaisinBtn.setTooltip(new Tooltip("Palauttaa päänäkymään"));
        
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
        System.out.println("Logout");
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
}
