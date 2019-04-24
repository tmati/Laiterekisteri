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
import Model.LuvanvaraisuusConverter;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
    private TableColumn hyvaksyntaColumn;
    @FXML
    private TableColumn<Varaukset, Varaukset> poistoColumn;
    @FXML
    private Button historiaBtn;

    private Controller controller;
    private DatePicker picker;
    private DatePickerSkin DPS;
    private static Node calContent;
    private Resurssit[] kalenterinPienentamaResurssiLista;

    /**
     * Initializes the controller class.
     *
     * @param url url
     * @param rb rb
     */
    @Transactional
    public void initialize(URL url, ResourceBundle rb) {
        controller = View.controller;
        kalenterinPienentamaResurssiLista = controller.haeKaikkiResurssit();
        BooleanConverter VarattavissaController = new BooleanConverter(controller, "Varattavissa", "Ei varattavissa");
        BooleanConverter AktiivisuusController = new BooleanConverter(controller, "Aktiivinen", "Ei aktiivinen");
        BooleanConverter HyvaksyntaController = new BooleanConverter(controller, "HYVÄKSYTTY", "KÄSITTELYSSÄ");
        LuvanvaraisuusConverter ResLC = new LuvanvaraisuusConverter(controller, "Vapaa käyttö", "Esimiehen hyväksyttävä", "Ylläpitäjän hyväksyttävä");
        Image image = new Image(getClass().getResourceAsStream("/Long beach.png"));
        logoView.setImage(image);

        //Resurssitaulun columnien live-edit
        nimiColumn.setCellValueFactory(new PropertyValueFactory<Resurssit, String>("nimi"));
        nimiColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        tyyppiColumn.setCellValueFactory(new PropertyValueFactory<Resurssit, String>("tyyppi"));
        tyyppiColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        kuvausColumn.setCellValueFactory(new PropertyValueFactory<Resurssit, String>("kuvaus"));
        kuvausColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        ChoiceBoxTableCell CC = new ChoiceBoxTableCell();
        CC.setConverter(ResLC);
        luvanvaraisuusColumn.setCellValueFactory(new PropertyValueFactory<Resurssit, Integer>("luvanvaraisuus"));
        luvanvaraisuusColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(CC.getConverter(), 0, 1, 2));

        CC.setConverter(VarattavissaController);
        tilaColumn.setCellValueFactory(new PropertyValueFactory<Resurssit, Boolean>("status"));
        tilaColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(CC.getConverter(), true, false));

        //Omat varaukset -taulun live-edit
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

        poistoColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        poistoColumn.setCellFactory(param -> new TableCell<Varaukset, Varaukset>() {
            private final Button deleteButton = new Button("Poista");

            @Override
            protected void updateItem(Varaukset V, boolean empty) {

                if (V == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(deleteButton);
                deleteButton.setOnAction(
                        event -> completeRemove(V)
                );
            }

        });

        this.categorySelect.setTooltip(new Tooltip("Hakukriteeri"));
        this.omatTable.setTooltip(new Tooltip("Omat varauksesi"));
        this.kaikkiTableView.setTooltip(new Tooltip("Kaikki järjestelmässä olevat resurssit"));
        this.LogoutBtn.setTooltip(new Tooltip("Ulos kirjautuminen"));
        this.hallnnoiBtn.setTooltip(new Tooltip("Avaa varauksien hallinta -näkymän"));
        this.henkilostoBtn.setTooltip(new Tooltip("Avaa käyttäjien hallinta -näkymän"));
        this.lisaaresurssiBtn.setTooltip(new Tooltip("Avaa popupin resurssin lisäämistä varten"));
        this.poistaresurssiBtn.setTooltip(new Tooltip("Poistaa valitun resurssin järjestelmästä"));
        this.salasananvaihtoBtn.setTooltip(new Tooltip("Avaa popupin salasanan vaihto varten"));
        this.searchBar.setTooltip(new Tooltip("Hakukenttä resursseja tai varauksia varten"));
        this.varausBtn.setTooltip(new Tooltip("Avaa popupin varauksen luontia varten, valitse resurssi ensin"));
        this.kalenteriPane.setTooltip(new Tooltip("Voit hakea resursseja päivän mukaan\n valitsemalla halutun päivän kalenterissa. Sen mukaan kalenteri näytää resurssit,\n jotka ovat sinäpäivinnä vapaina."));
        
        usernameLabel.setText(View.loggedIn.getNimi());
        bizName.setText(View.BizName);

        Resurssit[] resurssit = controller.haeKaikkiResurssit();
        kaikkiTableView.getItems().addAll(resurssit);
        Varaukset[] varauksetArr = controller.haeKayttajanVaraukset(View.loggedIn);
        omatTable.getItems().addAll(varauksetArr);

        picker = picker();
        //Kuuntelija jos taulukosta valitaan varausta
        kaikkiTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) { //Etsii resursin kaikki varaukset.

                Varaukset[] varaukset = controller.haeKaikkiVaraukset();
                picker = null;

                ArrayList<Varaukset> aVaraukset = controller.ResurssinVaraukset(kaikkiTableView.getSelectionModel().getSelectedItem().getId(), varaukset);

                Varaukset[] varaus = controller.getVarausTaulukko(aVaraukset);

                // luo uuden datepickerin johon laitetaan day cell factorin
                Callback<DatePicker, DateCell> dayCellFactory = controller.dayCellFactory(varaus, LocalDate.now());
                picker = picker();
                picker.setDayCellFactory(dayCellFactory);
                DPS.dispose();
                DPS = new DatePickerSkin(picker);
                calContent = DPS.getPopupContent();
                kalenteriStackPane.getChildren().set(0, calContent);
            }
        });

        // Luodaan datepicker skin ensimmäisen kerran
        if (picker != null) {
            DPS = new DatePickerSkin(picker);
        } else {
            DPS = new DatePickerSkin(picker());
        }

        calContent = DPS.getPopupContent();
        kalenteriStackPane.getChildren().add(calContent);

    }

    /**
     * Palautaa uuden datepickerin, jolla on muutoksiin kuuntelia. Kuuntelia
     * rajaa varauksista siten että ne, jotka eivät voi varata sinä päivänä ei
     * ilmesty tulukkoon.
     *
     * @return uusi datepicker, jolla on muutoksiin kuuntelia.
     */
    public DatePicker picker() {
        DatePicker picker = new DatePicker();
        picker.valueProperty().addListener((ov, oldValue, newValue) -> {
            String tabText = tabPane.getSelectionModel().getSelectedItem().getText();
            if (tabText.equals("Resurssit") && oldValue != newValue) {
                kaikkiTableView.getItems().clear();
                LocalDate paiva = picker.getValue();
                Resurssit[] resurssit = controller.haeKaikkiResurssit();
                Varaukset[] varaukset = controller.haeKaikkiVaraukset();

                ChronoLocalDateTime paivaAlku = paiva.atTime(LocalTime.MIN);
                ChronoLocalDateTime paivaLoppu = paiva.atTime(LocalTime.MAX);
                for (Resurssit resurssi : resurssit) {
                    if (controller.Onnistuu(controller.ResurssinVaraukset(resurssi.getId(), varaukset), paivaLoppu, paivaAlku)) {
                        kaikkiTableView.getItems().add(resurssi);
                        kaikkiTableView.refresh();
                    }
                }
                kalenterinPienentamaResurssiLista = new Resurssit[kaikkiTableView.getItems().size()];
                int i = 0;
                for (Resurssit resurssi : kaikkiTableView.getItems()) {
                    kalenterinPienentamaResurssiLista[i] = resurssi;
                    i++;
                }
            }
        });
        return picker;
    }

    /**
     * Poistaa taulukosta ja tietokannasta sekä päivittää taulukon
     *
     * @param V poistettava varaus
     */
    public void completeRemove(Varaukset V) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Oletko varma, että haluat poistaa varauksen?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            omatTable.getItems().remove(V);
            controller.poistaVaraus(V.getId());
            omatTable.refresh();
        }
    }

    /**
     * Päivittää taulukot näkymässä.
     *
     */
    @FXML
    public void update() {
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
        if (View.booking != null) {
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
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Valitse resurssi!");
            alert.showAndWait();
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

    @FXML
    public void historiaBtnPainettu(MouseEvent event) throws IOException {
        View.booking = kaikkiTableView.getSelectionModel().getSelectedItem();
        if (View.booking != null) {
            System.out.println("Historia");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ResurssiHistoria.fxml"));
            Stage stage = (Stage) LogoutBtn.getScene().getWindow();
            Parent root = loader.load();
            stage.getScene().setRoot(root);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Valitse resurssi!");
            alert.showAndWait();
        }
    }

    /**
     * Poistetaan resurssi
     *
     * @param event Hiiren painallus painikkeen kohdalla.
     * @throws IOException Tiedostoja käsiteltäessä varauduttava poikkeus.
     */
    @FXML
    public void poistaresurssiNappiPainettu(MouseEvent event) throws IOException {
        Resurssit toDelete = kaikkiTableView.getSelectionModel().getSelectedItem();
        if (toDelete != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Oletko varma, että haluat poistaa resurssin?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                controller.poistaResurssi(toDelete);
                System.out.println("poistetaan resurssi");
                this.update();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Valitse resurssi!");
            alert.showAndWait();
        }
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
    private void luvanvaraisuusOnEditCommit(TableColumn.CellEditEvent<Resurssit, Integer> event) {
        Resurssit R = kaikkiTableView.getSelectionModel().getSelectedItem();
        R.setLuvanvaraisuus((event.getNewValue()));
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
     * Hakutoiminnallisuus. Tulokset haetaan valittuna olevan välilehden ja
     * kategorian mukaan.
     *
     * @param event Näppäimen painallus hakukentän ollessa aktiivinen.
     */
    @FXML
    private void searchFunction(Event event) {
        String query = searchBar.getText().toLowerCase();
        String tabText = tabPane.getSelectionModel().getSelectedItem().getText();
        String selectedCategory = categorySelect.getSelectionModel().getSelectedItem().toString();
        if (tabText.equals("Resurssit") && selectedCategory.equals("NIMI")) {
            kaikkiTableView.getItems().clear();
            for (Resurssit resurssi : kalenterinPienentamaResurssiLista) {
                String resurssiPienella = resurssi.getNimi().toLowerCase();
                if (resurssiPienella.indexOf(query) != -1) {
                    kaikkiTableView.getItems().add(resurssi);
                    kaikkiTableView.refresh();

                }
            }
        } else if (tabText.equals("Resurssit") && selectedCategory.equals("KATEGORIA")) {
            kaikkiTableView.getItems().clear();
            for (Resurssit resurssi : kalenterinPienentamaResurssiLista) {
                String resurssiPienella = resurssi.getTyyppi().toLowerCase();

                if (resurssiPienella.indexOf(query) != -1) {
                    kaikkiTableView.getItems().add(resurssi);
                    kaikkiTableView.refresh();
                }
            }
        }

        if (tabText.equals("Omat varaukset") && selectedCategory.equals("NIMI")) {
            Varaukset[] omatVaraukset = controller.haeKayttajanVaraukset(View.loggedIn);
            omatTable.getItems().clear();
            for (Varaukset varaus : omatVaraukset) {
                String resurssiPienella = varaus.getResurssit().getNimi().toLowerCase();
                if (resurssiPienella.indexOf(query) != -1) {
                    omatTable.getItems().add(varaus);
                    omatTable.refresh();

                }
            }
        } else if (tabText.equals("Omat varaukset") && selectedCategory.equals("KATEGORIA")) {
            Varaukset[] omatVaraukset = controller.haeKayttajanVaraukset(View.loggedIn);
            omatTable.getItems().clear();
            for (Varaukset varaus : omatVaraukset) {
                String resurssiPienella = varaus.getResurssit().getTyyppi().toLowerCase();
                if (resurssiPienella.indexOf(query) != -1) {
                    omatTable.getItems().add(varaus);
                    omatTable.refresh();
                }
            }
        }
    }
}
