/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import Model.BooleanConverter;
import Model.Resurssit;
import Model.Varaukset;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *  Käyttäjien varausten tutkimiseen käytettävän näkymän controller-luokka
 *
 * @author tmati
 */
public class KayttajanVarauksetController implements Initializable {

    @FXML
    private Button takaisinBtn;
    @FXML
    private Label usernameLabel;
    @FXML
    private Button LogoutBtn;
    @FXML
    private Label bizName;
    @FXML
    private Label kayttajaString;
    @FXML
    private TableView<Varaukset> kayttajaTable;
    @FXML
    private TableColumn laitenimiColumn;
    @FXML
    private TableColumn alkupvmColumn;
    @FXML
    private TableColumn paattymispvmColumn;
    @FXML
    private TableColumn varausidColumn;
    @FXML
    private TableColumn varauskuvausColumn;
    @FXML
    private TableColumn palautettuColumn;
    @FXML
    private TableColumn hyvaksyntaColumn;
    @FXML
    private Button poistaBtn;
    
    Controller controller;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = new Controller();
        ChoiceBoxTableCell CC = new ChoiceBoxTableCell();
        BooleanConverter AktiivisuusController = new BooleanConverter(controller, "Aktiivinen", "Ei aktiivinen");
        BooleanConverter HyvaksyntaController = new BooleanConverter(controller, "HYVÄKSYTTY", "KÄSITTELYSSÄ");


        laitenimiColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, String>("nimi"));
        laitenimiColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        alkupvmColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Timestamp>("alkupvm"));
        alkupvmColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Timestamp>() {
            @Override
            public String toString(Timestamp object) {
                String tString = object.toString();
                return tString;
            }

            @Override
            public Timestamp fromString(String string) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                    Date parsedDate = (Date) dateFormat.parse(string);
                    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                    return timestamp;
                } catch (Exception e) {
                }
                return null;
            }
        }
        ));

        paattymispvmColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Timestamp>("paattymispvm"));
        paattymispvmColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Timestamp>() {

            @Override
            public String toString(Timestamp object) {
                String tString = object.toString();
                return tString;
            }

            @Override
            public Timestamp fromString(String string) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                    Date parsedDate = (Date) dateFormat.parse(string);
                    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                    return timestamp;
                } catch (Exception e) {
                }
                return null;
            }
        }));

        varausidColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Integer>("id"));
        varausidColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        varauskuvausColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, String>("kuvaus"));
        varauskuvausColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        CC.setConverter(AktiivisuusController);
        palautettuColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Boolean>("palautettu"));
        palautettuColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(CC.getConverter(), true, false));
        
        hyvaksyntaColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Boolean>("hyvaksytty"));
        hyvaksyntaColumn.setCellFactory(TextFieldTableCell.forTableColumn(HyvaksyntaController));

        usernameLabel.setText(View.loggedIn.getNimi());
        bizName.setText(View.BizName);
        kayttajaString.setText("Käyttäjän " + View.selected.getNimi() + " varaukset");
        kayttajaTable.getItems().addAll(View.selected.getVarauksets());
        
        this.LogoutBtn.setTooltip(new Tooltip("Ulos kirjautuminen"));
        this.poistaBtn.setTooltip(new Tooltip("Poistaa valitun varauksen ja lähettää ilmoituksen käyttäjälle"));
        this.takaisinBtn.setTooltip(new Tooltip("Palauttaa hallinnoi henkilöitä -näkymään"));
        
    }  
    
    /**
     * Edelliseen näkymään palaaminen
     * @param event hiiren klikkaus.
     * @throws IOException Varauduttava tietovirtapoikkeus
     */
    @FXML
    private void takaisinBtnPainettu(MouseEvent event) throws IOException {
        View.selected = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/KayttajaAdmin.fxml"));
        Stage stage = (Stage) kayttajaString.getScene().getWindow();
        Parent root = loader.load();
        stage.getScene().setRoot(root);
        
    }
    
    /**
     * Logout-toiminto
     * @param event hiiren klikkaus
     * @throws IOException Varauduttava tietovirtapoikkeus
     */
    @FXML
    private void logout(MouseEvent event) throws IOException {
        System.out.println("Logout");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Loginwindow.fxml"));
        Stage stage = (Stage) LogoutBtn.getScene().getWindow();
        Parent root = loader.load();
        stage.getScene().setRoot(root);
        View.loggedIn = null;
        View.selected = null;
    }
    
    /**
     * Taulukon kuvaus-columnin livemuokkaukseen liittyvä toiminto
     * @param event hiiren klikkaus
     * 
     */
    @FXML
    private void kuvausOnEditCommit(TableColumn.CellEditEvent<Varaukset, String> event) {
        Varaukset R = kayttajaTable.getSelectionModel().getSelectedItem();
        R.setKuvaus(event.getNewValue());
        System.out.println("Uusi kuvaus: " + R.getKuvaus());
        controller.paivitaVaraus(R);
    }

    
    /**
     * Poista-painike. Poistaa taulukosta valittuna olevan rivin ja päivittää tiedon tietokantaan. Myös taulukon uudelleenlataus.
     * @param event Hiiren klikkaus
     * @throws IOException Varauduttava tietovirtapoikkeus
     */
    @FXML
    private void poistaBtnPainettu(MouseEvent event) throws IOException {
        Varaukset toDelete = kayttajaTable.getSelectionModel().getSelectedItem();
        if (toDelete != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Oletko varma, että haluat poistaa varauksen?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                controller.poistaVaraus(toDelete.getId());
                System.out.println("poistetaan varaus");
                controller.lahetaSahkoposti(toDelete.getKayttaja().getSahkoposti(), controller.getVarausAikaString(toDelete) + " on poistettu esimiehen tai ylläpitäjän toimesta."
                        + "\n \nTämä on automaattinen viesti, johon ei tarvitse vastata.");
                kayttajaTable.getItems().clear(); 
                kayttajaTable.getItems().addAll(controller.haeKayttajanVaraukset(View.selected));
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Valitse resurssi!");
            alert.showAndWait();
        }
    }
}
