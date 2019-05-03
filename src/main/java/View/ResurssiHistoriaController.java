/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import Model.BooleanConverter;
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
 *
 * @author tmati
 */
public class ResurssiHistoriaController implements Initializable {

    @FXML
    private Button takaisinBtn;
    @FXML
    private Label usernameLabel;
    @FXML
    private Button LogoutBtn;
    @FXML
    private Label bizName;
    @FXML
    private Label varausString;
    @FXML
    private TableView<Varaukset> varausTable;
    @FXML
    private TableColumn varaajaColumn;
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
    private Button poistavarausBtn;
    
    private Controller controller;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     controller = new Controller();
        ChoiceBoxTableCell CC = new ChoiceBoxTableCell();
        BooleanConverter AktiivisuusController = new BooleanConverter(controller, controller.getConfigTeksti("isActive"), controller.getConfigTeksti("isnActive"));
        BooleanConverter HyvaksyntaController = new BooleanConverter(controller, controller.getConfigTeksti("acknowledged"), controller.getConfigTeksti("inProgress"));

        varaajaColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, String>("nimi"));
        varaajaColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        alkupvmColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Timestamp>("alkupvm"));
        alkupvmColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Timestamp>() {
            @Override
            public String toString(Timestamp object) {
                String tString = object.toString();
                return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(object);
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
                return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(object);
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

        //System.out.println(View.BizName);
        
        varaajaColumn.setText(controller.getConfigTeksti("varaaja").toUpperCase());
        alkupvmColumn.setText(controller.getConfigTeksti("reservationStartdate").toUpperCase());
        paattymispvmColumn.setText(controller.getConfigTeksti("reservationEnddate").toUpperCase());
        varausidColumn.setText(controller.getConfigTeksti("reservationId").toUpperCase());
        varauskuvausColumn.setText(controller.getConfigTeksti("description").toUpperCase());
        palautettuColumn.setText(controller.getConfigTeksti("activity").toUpperCase());
        hyvaksyntaColumn.setText(controller.getConfigTeksti("approval").toUpperCase());
        takaisinBtn.setText(controller.getConfigTeksti("returnButton").toUpperCase());
        usernameLabel.setText(controller.getConfigTeksti("userInfo").toUpperCase());
        LogoutBtn.setText(controller.getConfigTeksti("Logout").toUpperCase());

        poistavarausBtn.setText(controller.getConfigTeksti("removeReservation").toUpperCase());
        varausString.setText(controller.getConfigTeksti("resursin") + " " + View.booking.getNimi() + " " + controller.getConfigTeksti("reservations").toLowerCase());
        varausTable.getItems().addAll(controller.getVarausTaulukko(controller.resurssinVaraukset(View.booking.getId(), controller.haeKaikkiVaraukset())));

        this.LogoutBtn.setTooltip(new Tooltip(controller.getConfigTeksti("logoutInfo")));
        this.takaisinBtn.setTooltip(new Tooltip(controller.getConfigTeksti("returnButton")));

    }    

    /**
     * Palauttaa käyttäjän edelliseen näkymään
     * @param event hiiren painallus painikkeesta
     * @throws IOException Tiedostonkäsittelypoikkeus
     */
    @FXML
    private void takaisinBtnPainettu(MouseEvent event) throws IOException {
        View.booking = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/nakyma.fxml"));
        Stage stage = (Stage) varausString.getScene().getWindow();
        Parent root = loader.load();
        stage.getScene().setRoot(root);
    }
    
    
    /**
     * Kirjaa käyttäjän ulos sovelluksesta
     * @param event hiiren painallus näkymästä
     * @throws IOException Tiedostoja käsiteltäessä varauduttava poikkeus
     */
    @FXML
    private void logout(MouseEvent event) throws IOException {
        //System.out.println("Logout");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Loginwindow.fxml"));
        Stage stage = (Stage) LogoutBtn.getScene().getWindow();
        Parent root = loader.load();
        stage.getScene().setRoot(root);
        View.loggedIn = null;
        View.booking = null;
    }

    /**
     * Poistaa varauksen, jos se ei vielä ole alkanut
     * @param event Hiiren painallus painikkeeseen.
     */
    @FXML
    private void poistavarausBtnPainettu (MouseEvent event) {
        Varaukset toDelete = varausTable.getSelectionModel().getSelectedItem();
        if (toDelete != null) {
            controller.poistaVarausBtnToiminto(toDelete);
            varausTable.getItems().clear();
            varausTable.getItems().addAll(controller.getVarausTaulukko(controller.resurssinVaraukset(View.booking.getId(), controller.haeKaikkiVaraukset())));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, controller.getConfigTeksti("errorChooseReservation"));
            alert.showAndWait();
        }
        
    }
}
