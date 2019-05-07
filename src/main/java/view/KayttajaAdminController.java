/**
 *
 */
package view;

import controller.*;
import model.Kayttaja;
import model.LuvanvaraisuusConverter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Käyttäjänäkymään liittyvät toiminnot.
 *
 * @author tmati
 */
public class KayttajaAdminController implements KayttajaAdminControllerIf {

    @FXML
    private Label usernameLabel;
    @FXML
    private Button logoutBtn;
    @FXML
    private Label bizName;
    @FXML
    private TableView<Kayttaja> kayttajaTableView;
    @FXML
    private TableColumn nimiColumn;
    @FXML
    private TableColumn emailColumn;
    @FXML
    private TableColumn valtuudetColumn;
    @FXML
    private Button takaisinBtn;
    @FXML
    private Button lisaaBtn;
    @FXML
    private Button poistaBtn;
    @FXML
    private DatePicker datePicker;
    @FXML
    private GridPane bgPane;
    @FXML
    private TableColumn kayttajatunnusColumn;
    @FXML
    private Label bizName1;
    
    Popup popup;
 

    private ControllerIf controller;
    
    @FXML
    private Button kayttajanvarauksetNappi;

    /**
     * Initializes the CONTROLLER class.
     *
     * @param url url
     * @param rb resourcebundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Kontrollerin ilmentymä
         */
        controller = Controller.getInstance();

        LuvanvaraisuusConverter kayLC = new LuvanvaraisuusConverter(controller.getConfigTeksti("employee"), controller.getConfigTeksti("superior"), controller.getConfigTeksti("administrator"));

        //NÄISSÄ TUON STRING-PARAMETRIN PITÄÄ VASTATA OLION PARAMETRIÄ. MUUTEN EI NÄY!
        nimiColumn.setCellValueFactory(new PropertyValueFactory<Kayttaja, String>("nimi"));
        nimiColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nimiColumn.setText(controller.getConfigTeksti("name").toUpperCase());
        
        emailColumn.setCellValueFactory(new PropertyValueFactory<Kayttaja, String>("sahkoposti"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setText(controller.getConfigTeksti("emailLabel").toUpperCase());
        
        ChoiceBoxTableCell cc = new ChoiceBoxTableCell();
        cc.setConverter(kayLC);
        valtuudetColumn.setCellValueFactory(new PropertyValueFactory<Kayttaja, Integer>("valtuudet"));
        valtuudetColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(cc.getConverter(), 0,1,2));
        valtuudetColumn.setText(controller.getConfigTeksti("authorization").toUpperCase());
        
        kayttajatunnusColumn.setCellValueFactory(new PropertyValueFactory<Kayttaja, String>("kayttajatunnus"));
        kayttajatunnusColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        kayttajatunnusColumn.setText(controller.getConfigTeksti("accountName").toUpperCase());
        
        kayttajaTableView.getItems().addAll(controller.haeKaikkiKayttajat());
        bizName.setText(controller.getBizname());
        usernameLabel.setText(controller.getLoggedIn().getNimi());
        
        lisaaBtn.setText(controller.getConfigTeksti("newUser").toUpperCase());
        kayttajanvarauksetNappi.setText(controller.getConfigTeksti("userReservation").toUpperCase());
        poistaBtn.setText(controller.getConfigTeksti("removeUser").toUpperCase());
        takaisinBtn.setText(controller.getConfigTeksti("returnButton").toUpperCase());
        logoutBtn.setText(controller.getConfigTeksti("Logout").toUpperCase());
        bizName1.setText(controller.getConfigTeksti("user").toUpperCase());
        
        this.logoutBtn.setTooltip(new Tooltip(controller.getConfigTeksti("LogoutInfo")));
        this.lisaaBtn.setTooltip(new Tooltip(controller.getConfigTeksti("addUser")));
        this.poistaBtn.setTooltip(new Tooltip(controller.getConfigTeksti("removeUser")));
        this.takaisinBtn.setTooltip((new Tooltip(controller.getConfigTeksti("returnButtonInfo"))));
        this.kayttajanvarauksetNappi.setTooltip(new Tooltip(controller.getConfigTeksti("userReservationTooltip")));
       
    }

    /**
     * Päivittää käyttäjä -taulun.
     *
     */
    @FXML
    @Override
    public void updateBtnPainettu() {
        kayttajaTableView.getItems().clear();
        kayttajaTableView.getItems().addAll(controller.haeKaikkiKayttajat());
    }

    /**
     * Logout. Palauttaa käyttäjän kirjautumisnäkymään ja kirjaa tämän ulos.
     *
     * @param event MouseEvent
     * @throws IOException IOException
     */
    @FXML
    @Override
    public void logout(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Loginwindow.fxml"));
        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        Parent root = loader.load();
        stage.getScene().setRoot(root);
        controller.setLoggedIn(null);
    }
    
    /**
     * Käyttäjän painaessa takaisin - painiketta tämä palautetaan takaisin
     * päänäkymään.
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
     * Avaa uuden käyttäjän lisäämisnäkymän.
     *
     * @param event Hiiren klikkaus painikkeeseen.
     * @throws IOException Tiedostosta lukemisen vuoksi varauduttava poikkeus
     */
    @FXML
    private void lisaaBtnPainettu(MouseEvent event) throws IOException {
        if (popup == null || !popup.isShowing()) {
            popup = new Popup();
            Object source = event.getSource();
            Node node = (Node) source;
            Scene scene = node.getScene();
            Window window = scene.getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/uusiKayttaja.fxml"));
            popup.getContent().add((Parent) loader.load());
            popup.show(window);
        }
    }

    /**
     * Poistaa valitun rivin tietokannasta.
     *
     * @param event Hiiren klikkaus painikkeeseen.
     */
    @FXML
    private void poistaBtnPainettu(MouseEvent event) {
        Kayttaja k = kayttajaTableView.getSelectionModel().getSelectedItem();
        if (k != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, controller.getConfigTeksti("confDeleteUser") , ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                controller.poistaKayttaja(k.getId());
                this.updateBtnPainettu();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, controller.getConfigTeksti("chooseUser"));
            alert.showAndWait();
        }
    }
    
    @FXML private void kayttajanvarauksetNappiPainettu(MouseEvent event) throws IOException {
    Kayttaja kayttaja = kayttajaTableView.getSelectionModel().getSelectedItem();
        if (kayttaja != null) {
            controller.setSelected(kayttaja);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/KayttajanVaraukset.fxml"));
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            Parent root = loader.load();
            stage.getScene().setRoot(root);
    }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, controller.getConfigTeksti("chooseUser"));
            alert.showAndWait();
        }
    }
    
    /**
     * Toiminnallisuus nimi-columnin muokkaamisen päättyessä.
     *
     * @param event Toiminta tapahtuu taulukon solun muokkauksen varmistuessa
     * ENTER - painalluksella.
     */
    @FXML
    private void nimiEditCommit(TableColumn.CellEditEvent<Kayttaja, String> event) {
        Kayttaja kayttaja = kayttajaTableView.getSelectionModel().getSelectedItem();
        kayttaja.setNimi(event.getNewValue());
        controller.paivitaKayttaja(kayttaja);
    }

    /**
     * Toiminnalisuus sähköposticolumnin muokkaamisen päättyessä.
     *
     * @param event Toiminta tapahtuu taulukon solun muokkauksen varmistuessa
     * ENTER - painalluksella.
     */
    @FXML
    private void emailEditCommit(TableColumn.CellEditEvent<Kayttaja, String> event) {
        if (!controller.tarkistaEmail(event.getNewValue())) {
            Alert alert = new Alert(Alert.AlertType.WARNING, controller.getConfigTeksti("emailDublicate"));
            alert.showAndWait();
            kayttajaTableView.getItems().clear();
            kayttajaTableView.getItems().addAll(controller.haeKaikkiKayttajat());
        } else {
            Kayttaja kayttaja = kayttajaTableView.getSelectionModel().getSelectedItem();
            kayttaja.setSahkoposti(event.getNewValue());
            controller.paivitaKayttaja(kayttaja);
        }
    }

    /**
     * Toiminnallisuus valtuudet-columnin muokkaamisen päättyessä.
     *
     * @param event Toiminta tapahtuu taulukon solun muokkauksen varmistuessa
     * ENTER - painalluksella.
     */
    @FXML
    private void valtuudetEditCommit(TableColumn.CellEditEvent<Kayttaja, Integer> event) {
        Kayttaja kayttaja = kayttajaTableView.getSelectionModel().getSelectedItem();
        kayttaja.setValtuudet(event.getNewValue());
        controller.paivitaKayttaja(kayttaja);
    }

    /**
     * Toiminnallisuus käyttäjätunnus-columnin muokkaamisen päättyessä.
     *
     * @param event Toiminta tapahtuu taulukon solun muokkauksen varmistuessa
     * ENTER - painalluksella
     */
    @FXML
    private void kayttajatunnusEditCommit(TableColumn.CellEditEvent<Kayttaja, String> event) {
        if (!controller.tarkistaUsername(event.getNewValue())) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Käyttäjätunnus on jo käytössä!");
            alert.showAndWait();
            kayttajaTableView.getItems().clear();
            kayttajaTableView.getItems().addAll(controller.haeKaikkiKayttajat());
        } else {
            Kayttaja kayttaja = kayttajaTableView.getSelectionModel().getSelectedItem();
            kayttaja.setKayttajatunnus(event.getNewValue());
            controller.paivitaKayttaja(kayttaja);
        }
    }
}
