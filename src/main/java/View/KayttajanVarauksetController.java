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
 * FXML Controller class Käyttäjien varausten tutkimiseen käytettävän näkymän
 * controller-luokka
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
        BooleanConverter AktiivisuusController = new BooleanConverter(controller, controller.getConfigTeksti("isActive"), controller.getConfigTeksti("isnActive"));
        BooleanConverter HyvaksyntaController = new BooleanConverter(controller, controller.getConfigTeksti("acknowlwdge"), controller.getConfigTeksti("inProgress"));

        laitenimiColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, String>("nimi"));
        laitenimiColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        alkupvmColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Timestamp>("alkupvm"));
        alkupvmColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Timestamp>() {
            @Override
            public String toString(Timestamp object) {
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
        kayttajaString.setText(controller.getConfigTeksti("user1") + " " + View.selected.getNimi() + " " + controller.getConfigTeksti("reservations"));
        kayttajaTable.getItems().addAll(View.selected.getVarauksets());

        takaisinBtn.setText(controller.getConfigTeksti("returnButton").toUpperCase());
        usernameLabel.setText(controller.getConfigTeksti("userInfo").toUpperCase());
        LogoutBtn.setText(controller.getConfigTeksti("Logout").toUpperCase());
        kayttajaString.setText(controller.getConfigTeksti("user").toUpperCase());
        laitenimiColumn.setText(controller.getConfigTeksti("resourceName").toUpperCase());
        alkupvmColumn.setText(controller.getConfigTeksti("reservationStartdate").toUpperCase());
        paattymispvmColumn.setText(controller.getConfigTeksti("reservationEnddate").toUpperCase());
        varausidColumn.setText(controller.getConfigTeksti("reservationId").toUpperCase());
        varauskuvausColumn.setText(controller.getConfigTeksti("description").toUpperCase());
        palautettuColumn.setText(controller.getConfigTeksti("activity").toUpperCase());
        hyvaksyntaColumn.setText(controller.getConfigTeksti("approval").toUpperCase());
        poistaBtn.setText(controller.getConfigTeksti("removeReservation").toUpperCase());
        this.LogoutBtn.setTooltip(new Tooltip(controller.getConfigTeksti("logoutInfo")));
        this.poistaBtn.setTooltip(new Tooltip(controller.getConfigTeksti("removeReservationInfo")));
        this.takaisinBtn.setTooltip(new Tooltip(controller.getConfigTeksti("returnUsersWindow")));

    }

    /**
     * Edelliseen näkymään palaaminen
     *
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
     *
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
     * Poista-painike. Poistaa taulukosta valittuna olevan rivin ja päivittää
     * tiedon tietokantaan. Myös taulukon uudelleenlataus.
     *
     * @param event Hiiren klikkaus
     * @throws IOException Varauduttava tietovirtapoikkeus
     */
    @FXML
    private void poistaBtnPainettu(MouseEvent event) throws IOException {
        Varaukset toDelete = kayttajaTable.getSelectionModel().getSelectedItem();
        if (toDelete != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, controller.getConfigTeksti("confirmationRemoveReservation"), ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                if (!controller.OnkoVarausAlkanut(toDelete)) {
                    controller.poistaVaraus(toDelete.getId());
                    //System.out.println("poistetaan varaus");
                    controller.lahetaSahkoposti(toDelete.getKayttaja().getSahkoposti(), controller.getVarausAikaString(toDelete) + controller.getConfigTeksti("emailFordeletingReservation"));
                    kayttajaTable.getItems().clear();
                    kayttajaTable.getItems().addAll(controller.haeKayttajanVaraukset(View.selected));
                } else {
                    alert = new Alert(Alert.AlertType.WARNING, "Et voi poistaa varausta, joka on vanhentunut tai alkanut!");
                    alert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, controller.getConfigTeksti("errorChooseReservation"));
            alert.showAndWait();
        }
    }
}
