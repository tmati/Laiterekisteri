/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import Model.BooleanConverter;
import Model.Kayttaja;
import Model.KayttajaAccessObject;
import Model.Resurssit;
import Model.ResurssitAccessObject;
import Model.Varaukset;
import Model.VarauksetAccessObject;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.Popup;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javax.transaction.Transactional;

/**
 * Päänäkymän ohjaintoiminnot.
 *
 * @author tmati
 */
public class NakymaController implements Initializable {

    @FXML
    private Label usernameLabel;
    @FXML
    private Button LogoutBtn;
    @FXML
    private ImageView logoView;
    @FXML
    private TextField searchBar;
    @FXML
    private Label bizName;
    @FXML
    private ChoiceBox<?> categorySelect;
    @FXML
    private Button searchBtn;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab kaikkiTab;
    @FXML
    private AnchorPane kaikkiAnchor;
    @FXML
    private StackPane kaikkiStack;
    @FXML
    private TableView<Resurssit> kaikkiTableView;
    @FXML
    private TableColumn nimiColumn;
    @FXML
    private TableColumn tyyppiColumn;
    @FXML
    private TableColumn luvanvaraisuusColumn;
    @FXML
    private TableColumn kuvausColumn;
    @FXML
    private TableColumn tilaColumn;
    @FXML
    private Tab omatTab;
    @FXML
    private AnchorPane omatAnchor;
    @FXML
    private StackPane omatStack;
    @FXML
    private TableView<Varaukset> omatTable;
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
    private TableColumn laitenimiColumn;
    @FXML
    private Button varausBtn;
    @FXML
    private TitledPane kalenteriPane;
    @FXML
    private StackPane kalenteriStackPane;
    @FXML
    private Button lisaaresurssiBtn;
    @FXML
    private Button poistaresurssiBtn;
    Popup popup;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button hallnnoiBtn;
    @FXML
    private Button henkilostoBtn;
    @FXML
    private Button salasananvaihtoBtn;
    @FXML
    private Button updateBtn;

    private Controller controller;
    private DatePicker picker;
    private DatePickerSkin DPS;
    private static Node calContent;

    /**
     * Initializes the controller class.
     *
     * @param url url
     * @param rb resource bundle
     */
    @Transactional
    public void initialize(URL url, ResourceBundle rb) {
        controller = View.controller;
        BooleanConverter BC = new BooleanConverter(controller);
        Image image = new Image(getClass().getResourceAsStream("/Long beach.png"));
        logoView.setImage(image);

        //Resurssitaulun columnien live-edit
        nimiColumn.setCellValueFactory(new PropertyValueFactory<Resurssit, String>("nimi"));
        nimiColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        tyyppiColumn.setCellValueFactory(new PropertyValueFactory<Resurssit, String>("tyyppi"));
        tyyppiColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        kuvausColumn.setCellValueFactory(new PropertyValueFactory<Resurssit, String>("kuvaus"));
        kuvausColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        luvanvaraisuusColumn.setCellValueFactory(new PropertyValueFactory<Resurssit, Integer>("luvanvaraisuus"));
        luvanvaraisuusColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        ChoiceBoxTableCell CC = new ChoiceBoxTableCell();
        CC.setConverter(BC);
        tilaColumn.setCellValueFactory(new PropertyValueFactory<Resurssit, Boolean>("status"));
        tilaColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(CC.getConverter(), true, false));
        
        //Omat varaukset -taulun live-edit
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

        palautettuColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Boolean>("palautettu"));
        palautettuColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));

        categorySelect.setTooltip(new Tooltip("Hakukriteeri"));

        usernameLabel.setText(View.loggedIn.getNimi());
        bizName.setText(View.BizName);

        Resurssit[] resurssit = controller.haeKaikkiResurssit();
        kaikkiTableView.getItems().addAll(resurssit);
        Varaukset[] varauksetArr = controller.haeKayttajanVaraukset(View.loggedIn);
        omatTable.getItems().addAll(varauksetArr);
        
        picker = new DatePicker();
        //Kuuntelija jos taulukosta valitaan varausta
        kaikkiTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) { //Etsii resursin kaikki varaukset.
                
                Varaukset[] varaukset = controller.haeKaikkiVaraukset();
                picker = null;
                ArrayList<Varaukset> aVaraukset = controller.ResursinVaraukset(kaikkiTableView.getSelectionModel().getSelectedItem().getId(), varaukset);
                Varaukset[] varaus = controller.getVaraus(aVaraukset);
                
               // luo uuden datepickerin johon laitetaan day cell factorin
                Callback<DatePicker, DateCell> dayCellFactory = controller.dayCellFactory(varaus); 
                picker = new DatePicker();
                picker.setDayCellFactory(dayCellFactory);
                DPS.dispose();
                DPS = new DatePickerSkin(picker);
                calContent = DPS.getPopupContent();
                kalenteriStackPane.getChildren().set(0, calContent);
            }
        });
        
        // Luodaan datepicker skin ensimmäisen kerran
        if(picker != null){
           DPS = new DatePickerSkin(picker);
       }else{
           DPS = new DatePickerSkin(new DatePicker());
       }
        
        calContent = DPS.getPopupContent();
        kalenteriStackPane.getChildren().add(calContent);
    }

    /**
     * Päivittää miltä nappi näytää kun se on painettu.
     *
     * @param event Hiiren painallus.
     */
    @FXML
    public void updateBtnPainettu(MouseEvent event) {
        kaikkiTableView.getItems().clear();
        omatTable.getItems().clear();
        Resurssit[] resurssit = controller.haeKaikkiResurssit();
        kaikkiTableView.getItems().addAll(resurssit);
        Varaukset[] varauksetArr = controller.haeKayttajanVaraukset(View.loggedIn);
        omatTable.getItems().addAll(varauksetArr);
    }

    /**
     * Avaa varaus-popupin
     *
     * @param event Hiiren painallus.
     * @throws IOException Tiedostoja luettaessa varauduttava poikkeus.
     */
    @FXML
    public void varausNappiPainettu(MouseEvent event) throws IOException {
        View.booking = kaikkiTableView.getSelectionModel().getSelectedItem();
        if (popup == null || !popup.isShowing()) {
            popup = new Popup();
            Object source = event.getSource();
            Node node = (Node) source;
            Scene scene = node.getScene();
            Window window = scene.getWindow();
            Stage stage = (Stage) window;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Varausikkuna.fxml"));
            popup.getContent().add((Parent) loader.load());
            popup.show(window);
        }
    }

    /**
     * Avaa "lisää resurssi" -popupin.
     *
     * @param event hiiren painallus.
     * @throws IOException Tiedostoja luettaessa varauduttava poikkeus.
     */
    @FXML
    public void lisaaresurssiNappiPainettu(MouseEvent event) throws IOException {
        if (popup == null || !popup.isShowing()) {
            popup = new Popup();
            Object source = event.getSource();
            Node node = (Node) source;
            Scene scene = node.getScene();
            Window window = scene.getWindow();
            Stage stage = (Stage) window;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/uusiResurssi.fxml"));
            popup.getContent().add((Parent) loader.load());
            popup.show(window);
        }
    }

    /**
     * Avaa vaihda salasana-popupin
     *
     * @param event Hiiren painallus
     * @throws IOException Tiedostoja käsitellessä varauduttava poikkeus.
     */
    @FXML
    public void salasananvaihtoNappiPainettu(MouseEvent event) throws IOException {
        if (popup == null || !popup.isShowing()) {
            popup = new Popup();
            Object source = event.getSource();
            Node node = (Node) source;
            Scene scene = node.getScene();
            Window window = scene.getWindow();
            Stage stage = (Stage) window;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SalasananvaihtoIkkuna.fxml"));
            popup.getContent().add((Parent) loader.load());
            popup.show(window);
        }
    }

    /**
     * Kirjaa käyttäjän ulos ja siirtyy kirjautumisnäkymään.
     *
     * @param event Hiiren painallus painikkeesta.
     * @throws IOException Tiedostoja käsiteltäessä varauduttava poikkeus.
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
     * Siirtyy varausten hallintanäkymään TODO Näytä vain jos tarpeeksi
     * oikeuksia
     *
     * @param event Hiiren painallus painikkeesta.
     * @throws IOException Tiedostoja käsitellessä varauduttava poikkeus.
     */
    @FXML
    public void hallinnoiBtnPainettu(MouseEvent event) throws IOException {
        System.out.println("Hallinoidaan varauksia!");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VarausAdmin.fxml"));
        Stage stage = (Stage) LogoutBtn.getScene().getWindow();
        Parent root = loader.load();
        stage.getScene().setRoot(root);
    }

    /**
     * Siirtyy henkilöstön hallintanäkymään TODO Näytä vain jos tarpeeksi
     * oikeuksia.
     *
     * @param event Hiiren painallus käyttöliittymässä vastaavasta painikkeesta.
     * @throws IOException Tiedostoja käsiteltäessä varauduttava poikkeus.
     */
    @FXML
    public void henkilostoBtnPainettu(MouseEvent event) throws IOException {
        System.out.println("Hallinoidaan henkilöstöä!");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/KayttajaAdmin.fxml"));
        Stage stage = (Stage) LogoutBtn.getScene().getWindow();
        Parent root = loader.load();
        stage.getScene().setRoot(root);
    }

    /**
     * Poistetaan resurssi
     *
     * @param event Hiiren painallus painikkeen kohdalla.
     * @throws IOException Tiedostoja käsiteltäessä varauduttava poikkeus.
     */
    @FXML
    public void poistaresurssiNappiPainettu(MouseEvent event) throws IOException {
        System.out.println("poistetaan resurssi");
        //Tähän joku dialogi jos jää aikaa
        Resurssit toDelete = kaikkiTableView.getSelectionModel().getSelectedItem();
        controller.poistaResurssi(toDelete);
    }

    /**
     * Toiminnallisuus nimi-columnin muokkaamisen päättyessä.
     *
     * @param event ENTER-painallus nimisarakkeen muokkaamisen jälkeen.
     */
    @FXML
    private void nimiOnEditCommit(TableColumn.CellEditEvent<Resurssit, String> event) {
        Resurssit R = kaikkiTableView.getSelectionModel().getSelectedItem();
        R.setNimi(event.getNewValue());
        System.out.println("Uusi nimi: " + R.getNimi());
        controller.paivitaResurssi(R);
    }

    /**
     * Toiminnallisuus tyyppi-columnin muokkaamisen päättyessä.
     *
     * @param event ENTER-painallus tyyppi-sarakkeen muokkaamisen jälkeen.
     */
    @FXML
    private void tyyppiOnEditCommit(TableColumn.CellEditEvent<Resurssit, String> event) {
        Resurssit R = kaikkiTableView.getSelectionModel().getSelectedItem();
        R.setTyyppi(event.getNewValue());
        System.out.println("Uusi Tyyppi: " + R.getTyyppi());
        controller.paivitaResurssi(R);
    }

    /**
     * Toiminnallisuus luvanvaraisuus-columnin muokkaamisen päättyessä.
     *
     * @param event ENTER-painallus luvanvaraisuus-sarakkeen muokkaamisen
     * jälkeen.
     */
    @FXML
    private void luvanvaraisuusOnEditCommit(TableColumn.CellEditEvent<Resurssit, Long> event) {
        Resurssit R = kaikkiTableView.getSelectionModel().getSelectedItem();
        R.setLuvanvaraisuus(event.getNewValue().intValue());
        System.out.println("Uusi luvanvaraisuusarvo: " + R.getLuvanvaraisuus());
        controller.paivitaResurssi(R);
    }

    /**
     * Toiminnallisuus kuvaus-columnin muokkaamisen päättyessä.
     *
     * @param event ENTER-painallus kuvaus-sarakkeen muokkaamisen jölkeen.
     */
    @FXML
    private void kuvausOnEditCommit(TableColumn.CellEditEvent<Resurssit, String> event) {
        Resurssit R = kaikkiTableView.getSelectionModel().getSelectedItem();
        R.setKuvaus(event.getNewValue());
        System.out.println("Uusi kuvaus: " + R.getKuvaus());
        controller.paivitaResurssi(R);
    }

    /**
     * Toiminnallisuus tila-columnin muokkaamisen päättyessä.
     *
     * @param event ENTER-painallus tila-sarakkeen muokkaamisen jälkeen.
     */
    @FXML
    private void tilaOnEditCommit(TableColumn.CellEditEvent<Resurssit, Boolean> event) {
        Resurssit R = kaikkiTableView.getSelectionModel().getSelectedItem();
        R.setStatus(event.getNewValue());
        System.out.println("Uusi tila: " + " " + R.isStatus());
        controller.paivitaResurssi(R);
    }

    /**
     * Toiminnallisuus alkupvm-columnin muokkaamisen päättyessä.
     *
     * @param event ENTER-painallus alkupvm-sarakkeen muokkaamisen päätteeksi.
     */
    @FXML
    private void alkupvmOnEditCommit(TableColumn.CellEditEvent<Varaukset, Timestamp> event) {
        Varaukset V = omatTable.getSelectionModel().getSelectedItem();
        V.setAlkupvm(event.getNewValue());
        System.out.println("Uusi alkupäivämäärä:" + V.getAlkupvm());
        controller.paivitaVaraus(V);
    }

    /**
     * Toiminnallisuus paattymispvm-columnin muokkaamisen päättyessä.
     *
     * @param event ENTER-painalus paattymispvm-sarakkeeen muokkaamisen jälkeen.
     */
    @FXML
    private void paattymispvmOnEditCommit(TableColumn.CellEditEvent<Varaukset, Timestamp> event) {
        Varaukset V = omatTable.getSelectionModel().getSelectedItem();
        V.setPaattymispvm(event.getNewValue());
        System.out.println("Uusi päättymispäivä:" + V.getPaattymispvm());
        controller.paivitaVaraus(V);
    }

    /**
     * Toiminnallisuus palautettu-columnin muokkaamisen päättyessä.
     *
     * @param event ENTER-painallus palautettu-sarakkeen muokkaamisen jälkeen.
     */
    @FXML
    private void palautettuOnEditCommit(TableColumn.CellEditEvent<Varaukset, Boolean> event) {
        Varaukset V = omatTable.getSelectionModel().getSelectedItem();
        V.setPalautettu(event.getNewValue());
        System.out.println("Palautettu:" + V.isPalautettu());
        controller.paivitaVaraus(V);
    }

    /**
     * Hakutoiminnallisuus. Tulokset haetaan valittuna olevan välilehden ja
     * kategorian mukaan.
     *
     * @param event Näppäimen painallus hakukentän ollessa aktiivinen.
     */
    @FXML
    private void searchFunction(KeyEvent event) {
        /* String query = searchBar.getText();
        System.out.println(query);
        String tabText = tabPane.getSelectionModel().getSelectedItem().getText();
        System.out.println(tabText);
        String selectedCategory = categorySelect.getSelectionModel().getSelectedItem().toString();
        System.out.println(selectedCategory);
        
        if (tabText.equals("Kaikki") && selectedCategory.equals("NIMI")) {
            ResurssitAccessObject RAO = new ResurssitAccessObject();
            Resurssit [] resurssit = RAO.readResurssit();
            for (Resurssit resurssi :resurssit) {
                if (resurssi.getNimi().equals(query));
                kaikkiTableView.getItems().add(resurssi);
            }
        } else if (tabText.equals("Kaikki") && selectedCategory.equals("Kategoria")) {
            ResurssitAccessObject RAO = new ResurssitAccessObject();
            Resurssit[] resurssit = RAO.readResurssit();
            for (Resurssit resurssi : resurssit) {
                if (resurssi.equals(query));
                kaikkiTableView.getItems().add(resurssi);
            }
        }
         */
    }
}
