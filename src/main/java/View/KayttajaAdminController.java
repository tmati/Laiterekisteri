/**
 *
 */
package View;

import Controller.Controller;
import Model.Kayttaja;
import Model.KayttajaAccessObject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.IntegerStringConverter;

/**
 * Käyttäjänäkymään liittyvät toiminnot.
 *
 * @author tmati
 */
public class KayttajaAdminController implements Initializable {

    @FXML
    private Label usernameLabel;
    @FXML
    private Button LogoutBtn;
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
    private Button tallennaBtn;
    @FXML
    private Button lisaaBtn;
    @FXML
    private Button muutaBtn;
    @FXML
    private Button poistaBtn;
    @FXML
    private DatePicker datePicker;
    @FXML
    private GridPane bgPane;
    @FXML
    private TableColumn kayttajatunnusColumn;
    Popup popup;
    @FXML
    private Button updateBtn;

    private Controller kontrolleri;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        kontrolleri = new Controller();

        //NÄISSÄ TUON STRING-PARAMETRIN PITÄÄ VASTATA OLION PARAMETRIÄ. MUUTEN EI NÄY!
        nimiColumn.setCellValueFactory(new PropertyValueFactory<Kayttaja, String>("nimi"));
        nimiColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        emailColumn.setCellValueFactory(new PropertyValueFactory<Kayttaja, String>("sahkoposti"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        valtuudetColumn.setCellValueFactory(new PropertyValueFactory<Kayttaja, Integer>("valtuudet"));
        valtuudetColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        kayttajatunnusColumn.setCellValueFactory(new PropertyValueFactory<Kayttaja, String>("kayttajatunnus"));
        kayttajatunnusColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        kayttajaTableView.getItems().addAll(kontrolleri.haeKaikkiKayttajat());
        bizName.setText(View.BizName);
        usernameLabel.setText(View.loggedIn.getNimi());
    }

    @FXML
    public void updateBtnPainettu(MouseEvent event) {
        kayttajaTableView.getItems().clear();
        kayttajaTableView.getItems().addAll(kontrolleri.haeKaikkiKayttajat());
    }

    /**
     * Logout. Palauttaa käyttäjän kirjautumisnäkymään ja kirjaa tämän ulos.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void logout(MouseEvent event) throws IOException {
        System.out.println("Logout");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Loginwindow.fxml"));
        Stage stage = (Stage) LogoutBtn.getScene().getWindow();
        Parent root = loader.load();
        stage.getScene().setRoot(root);
        View.loggedIn = null;
    }

    /**
     * Käyttäjän painaessa takaisin - painiketta tämä palautetaan takaisin
     * päänäkymään.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void takaisinBtnPainettu(MouseEvent event) throws IOException {
        System.out.println("Logout");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/nakyma.fxml"));
        Stage stage = (Stage) LogoutBtn.getScene().getWindow();
        Parent root = loader.load();
        stage.getScene().setRoot(root);
    }

    @FXML
    private void tallennaBtnPainettu(MouseEvent event) {
        //TODO Lopputoimet tietokantaan
        System.out.println("Tallennus");
    }

    /**
     * Avaa uuden käyttäjän lisäämisnäkymän.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void lisaaBtnPainettu(MouseEvent event) throws IOException {
        if (popup == null || !popup.isShowing()) {
            popup = new Popup();
            Object source = event.getSource();
            Node node = (Node) source;
            Scene scene = node.getScene();
            Window window = scene.getWindow();
            Stage stage = (Stage) window;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/uusiKayttaja.fxml"));
            popup.getContent().add((Parent) loader.load());
            popup.show(window);
        }
    }

    /**
     * Poistaa valitun rivin tietokannasta.
     *
     * @param event
     */
    @FXML
    private void poistaBtnPainettu(MouseEvent event) {
        Kayttaja K = kayttajaTableView.getSelectionModel().getSelectedItem();
        kontrolleri.poistaKayttaja(K.getId());
        this.updateBtnPainettu(event);
        System.out.println("Poistetaan rivi");
    }

    /**
     * Toiminnallisuus nimi-columnin muokkaamisen päättyessä.
     *
     * @param event
     */
    @FXML
    private void nimiEditCommit(TableColumn.CellEditEvent<Kayttaja, String> event) {
        Kayttaja J = kayttajaTableView.getSelectionModel().getSelectedItem();
        J.setNimi(event.getNewValue());
        System.out.println("Uusi nimi: " + J.getNimi());
        kontrolleri.paivitaKayttaja(J);
    }

    /**
     * Toiminnalisuus sähköposticolumnin muokkaamisen päättyessä
     *
     * @param event
     */
    @FXML
    private void emailEditCommit(TableColumn.CellEditEvent<Kayttaja, String> event) {
        if (!kontrolleri.tarkistaEmail(event.getNewValue())) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Sähköposti on jo käytössä!");
            alert.showAndWait();
            kayttajaTableView.getItems().clear();
            kayttajaTableView.getItems().addAll(kontrolleri.haeKaikkiKayttajat());
        } else {
            Kayttaja J = kayttajaTableView.getSelectionModel().getSelectedItem();
            J.setSahkoposti(event.getNewValue());
            System.out.println("Uusi email: " + J.getSahkoposti());
            kontrolleri.paivitaKayttaja(J);
        }
    }

    /**
     * Toiminnallisuus valtuudet-columnin muokkaamisen päättyessä.
     *
     * @param event
     */
    @FXML
    private void valtuudetEditCommit(TableColumn.CellEditEvent<Kayttaja, Integer> event) {
        Kayttaja J = kayttajaTableView.getSelectionModel().getSelectedItem();
        J.setValtuudet(event.getNewValue());
        System.out.println("Uudet valtuudet: " + J.getValtuudet());
        kontrolleri.paivitaKayttaja(J);
    }

    /**
     * Toiminnallisuus käyttäjätunnus-columnin muokkaamisen päättyessä.
     *
     * @param event
     */
    @FXML
    private void kayttajatunnusEditCommit(TableColumn.CellEditEvent<Kayttaja, String> event) {
        if (!kontrolleri.tarkistaUsername(event.getNewValue())) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Käyttäjätunnus on jo käytössä!");
            alert.showAndWait();
            kayttajaTableView.getItems().clear();
            kayttajaTableView.getItems().addAll(kontrolleri.haeKaikkiKayttajat());
        } else {
            Kayttaja J = kayttajaTableView.getSelectionModel().getSelectedItem();
            J.setKayttajatunnus(event.getNewValue());
            System.out.println("Uusi tunnus: " + J.getKayttajatunnus());
            kontrolleri.paivitaKayttaja(J);
        }
    }
}
