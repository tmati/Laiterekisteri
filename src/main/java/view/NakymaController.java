/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.*;
import model.BooleanConverter;
import model.LuvanvaraisuusConverter;
import model.Resurssit;
import model.Varaukset;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.Popup;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javax.transaction.Transactional;
import model.Istunto;

/**
 * Päänäkymän ohjaintoiminnot.
 *
 * @author tmati
 */
public class NakymaController implements NakymaControllerIf {

    @FXML
    private Label usernameLabel;
    @FXML
    private Button logoutBtn;
    @FXML
    private ImageView logoView;
    @FXML
    private TextField searchBar;
    @FXML
    private Label bizName;
    @FXML
    private ChoiceBox<String> categorySelect;
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

    private ControllerIf controller;
    private DatePicker picker;
    private DatePickerSkin dps;
    private static Node calContent;
    private Resurssit[] kalenterinPienentamaResurssiLista;
    private static final String CATEGORY = "category";
    private static final String RESOURCES = "resources";
    private static final String OMAT = "ownReservations";
    private static final String CHOOSERESOURCE = "chooseResource";
    /**
     * Initializes the CONTROLLER class.
     *
     * @param url url
     * @param rb rb
     */
    @Transactional
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = Controller.getInstance();
        kalenterinPienentamaResurssiLista = controller.haeKaikkiResurssit();
        BooleanConverter varattavissaController = new BooleanConverter(controller.getConfigTeksti("bookable"), controller.getConfigTeksti("notBookable"));
        BooleanConverter aktiivisuusController = new BooleanConverter(controller.getConfigTeksti("isActive"), controller.getConfigTeksti("isnActive"));
        BooleanConverter hyvaksyntaController = new BooleanConverter(controller.getConfigTeksti("acknowledged"), controller.getConfigTeksti("inProgress"));
        LuvanvaraisuusConverter resLC = new LuvanvaraisuusConverter(controller.getConfigTeksti("freeUse"), controller.getConfigTeksti("supApproved"), controller.getConfigTeksti("adApproved"));
        Image image = new Image(getClass().getResourceAsStream("/keychain.png"));
        logoView.setImage(image);

        //Resurssitaulun columnien live-edit
        nimiColumn.setCellValueFactory(new PropertyValueFactory<Resurssit, String>("nimi"));
        nimiColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        tyyppiColumn.setCellValueFactory(new PropertyValueFactory<Resurssit, String>("tyyppi"));
        tyyppiColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        kuvausColumn.setCellValueFactory(new PropertyValueFactory<Resurssit, String>("kuvaus"));
        kuvausColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        ChoiceBoxTableCell cc = new ChoiceBoxTableCell();
        cc.setConverter(resLC);
        luvanvaraisuusColumn.setCellValueFactory(new PropertyValueFactory<Resurssit, Integer>("luvanvaraisuus"));
        luvanvaraisuusColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(cc.getConverter(), 0, 1, 2));

        cc.setConverter(varattavissaController);
        tilaColumn.setCellValueFactory(new PropertyValueFactory<Resurssit, Boolean>("status"));
        tilaColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(cc.getConverter(), true, false));

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
                    return new java.sql.Timestamp(parsedDate.getTime());
                    
                } catch (Exception e) {
                     Istunto.LOGGER.log(Level.SEVERE, e.getMessage());
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
                    return new java.sql.Timestamp(parsedDate.getTime());
                } catch (Exception e) {
                      Istunto.LOGGER.log(Level.SEVERE, e.getMessage());
                }
                return null;
            }
        }));

        varausidColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Integer>("id"));
        varausidColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        varauskuvausColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, String>("kuvaus"));
        varauskuvausColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        cc.setConverter(aktiivisuusController);
        palautettuColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Boolean>("palautettu"));
        palautettuColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(cc.getConverter(), true, false));

        hyvaksyntaColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Boolean>("hyvaksytty"));
        hyvaksyntaColumn.setCellFactory(TextFieldTableCell.forTableColumn(hyvaksyntaController));

        poistoColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        poistoColumn.setCellFactory(param -> new TableCell<Varaukset, Varaukset>() {
            
            private final Button deleteButton = new Button(controller.getConfigTeksti("remove"));

            @Override
            protected void updateItem(Varaukset varaukset, boolean empty) {


                if (varaukset == null) {

                    setGraphic(null);
                    return;
                }
                setGraphic(deleteButton);
                deleteButton.setOnAction(event -> completeRemove(varaukset)
                        
                );
            }
        });

        searchBar.setPromptText(controller.getConfigTeksti("search"));
        categorySelect.getItems().setAll(controller.getConfigTeksti("name").toUpperCase(),controller.getConfigTeksti(CATEGORY).toUpperCase()) ;
        categorySelect.setValue(controller.getConfigTeksti("name").toUpperCase());
        kaikkiTab.setText(controller.getConfigTeksti(RESOURCES));
        nimiColumn.setText(controller.getConfigTeksti("name").toUpperCase());
        tyyppiColumn.setText(controller.getConfigTeksti("type").toUpperCase());
        luvanvaraisuusColumn.setText(controller.getConfigTeksti("permission").toUpperCase());
        kuvausColumn.setText(controller.getConfigTeksti("description").toUpperCase());
        tilaColumn.setText(controller.getConfigTeksti("state").toUpperCase());
        omatTab.setText(controller.getConfigTeksti(OMAT));
        laitenimiColumn.setText(controller.getConfigTeksti("resourceName").toUpperCase());
        alkupvmColumn.setText(controller.getConfigTeksti("reservationStartdate").toUpperCase());
        paattymispvmColumn.setText(controller.getConfigTeksti("reservationEnddate").toUpperCase());
        varausidColumn.setText(controller.getConfigTeksti("reservationId").toUpperCase());
        varauskuvausColumn.setText(controller.getConfigTeksti("description").toUpperCase());
        palautettuColumn.setText(controller.getConfigTeksti("activity").toUpperCase());
        hyvaksyntaColumn.setText(controller.getConfigTeksti("approval").toUpperCase());
        poistoColumn.setText(controller.getConfigTeksti("removeReservation").toUpperCase());
        kalenteriPane.setText(controller.getConfigTeksti("calendar").toUpperCase());
        lisaaresurssiBtn.setText(controller.getConfigTeksti("addResource").toUpperCase());
        poistaresurssiBtn.setText(controller.getConfigTeksti("removeResource").toUpperCase());
        hallnnoiBtn.setText(controller.getConfigTeksti("manageReservations").toUpperCase());
        historiaBtn.setText(controller.getConfigTeksti("resourceHistory").toUpperCase());
        henkilostoBtn.setText(controller.getConfigTeksti("manageUsers").toUpperCase());
        logoutBtn.setText(controller.getConfigTeksti("Logout").toUpperCase());
        salasananvaihtoBtn.setText(controller.getConfigTeksti("passwordChange").toUpperCase());
        varausBtn.setText(controller.getConfigTeksti("makeReservation").toUpperCase());
        
        this.categorySelect.setTooltip(new Tooltip(controller.getConfigTeksti("searchBy")));
        this.omatTable.setTooltip(new Tooltip(controller.getConfigTeksti(OMAT)));
        this.kaikkiTableView.setTooltip(new Tooltip(controller.getConfigTeksti("ownTableTooltip")));
        this.logoutBtn.setTooltip(new Tooltip(controller.getConfigTeksti("logoutInfo")));
        this.hallnnoiBtn.setTooltip(new Tooltip(controller.getConfigTeksti("hallinnoiBtnTooltip")));
        this.henkilostoBtn.setTooltip(new Tooltip(controller.getConfigTeksti("henkilostoBtnTooltip")));
        this.lisaaresurssiBtn.setTooltip(new Tooltip(controller.getConfigTeksti("lisaaRTooltip")));
        this.poistaresurssiBtn.setTooltip(new Tooltip(controller.getConfigTeksti("poistaRTooltip")));
        this.salasananvaihtoBtn.setTooltip(new Tooltip(controller.getConfigTeksti("salasanaVaihtoBtn")));
        this.searchBar.setTooltip(new Tooltip(controller.getConfigTeksti("searchBarTooltip")));
        this.varausBtn.setTooltip(new Tooltip(controller.getConfigTeksti("varausBtnTooltip")));
        this.kalenteriPane.setTooltip(new Tooltip(controller.getConfigTeksti("kalenteriStackPaneTooltip")));
        
        
        
        usernameLabel.setText(controller.getLoggedIn().getNimi());
        bizName.setText(controller.getBizname());

        Resurssit[] res = controller.haeKaikkiResurssit();
        kaikkiTableView.getItems().addAll(res);
        Varaukset[] varauksetArr = controller.haeKayttajanVaraukset(controller.getLoggedIn());
        omatTable.getItems().addAll(varauksetArr);

        picker = picker();
        //Kuuntelija jos taulukosta valitaan varausta
        kaikkiTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) { //Etsii resursin kaikki varaukset.

                Varaukset[] varaukset = controller.haeKaikkiVaraukset();
                picker = null;

                List<Varaukset> aVaraukset = controller.resurssinVaraukset(kaikkiTableView.getSelectionModel().getSelectedItem().getId(), varaukset);

                Varaukset[] varaus = controller.getVarausTaulukko(aVaraukset);

                // luo uuden datepickerin johon laitetaan day cell factorin
                Callback<DatePicker, DateCell> dayCellFactory = controller.dayCellFactory(varaus, LocalDate.now());
                picker = picker();
                picker.setDayCellFactory(dayCellFactory);
                dps.dispose();
                dps = new DatePickerSkin(picker);
                calContent = dps.getPopupContent();
                kalenteriStackPane.getChildren().set(0, calContent);
            }
        });

        // Luodaan datepicker skin ensimmäisen kerran
        if (picker != null) {
            dps = new DatePickerSkin(picker);
        } else {
            dps = new DatePickerSkin(picker());
        }

        calContent = dps.getPopupContent();
        kalenteriStackPane.getChildren().add(calContent);
        
        //Rajoittaa käyttöliittymän elementtejä eritason käyttäjille
        
        //Työntekijä
       if(controller.getLoggedIn().getValtuudet()==0){
           this.henkilostoBtn.setDisable(true);
           this.henkilostoBtn.setOpacity(0);
           this.hallnnoiBtn.setDisable(true);
           this.hallnnoiBtn.setOpacity(0);
           this.lisaaresurssiBtn.setDisable(true);
           this.lisaaresurssiBtn.setOpacity(0);
           this.poistaresurssiBtn.setDisable(true);
           this.poistaresurssiBtn.setOpacity(0);
           this.historiaBtn.setDisable(true);
           this.historiaBtn.setOpacity(0);
           this.kuvausColumn.setEditable(false);
           this.nimiColumn.setEditable(false);
           this.tilaColumn.setEditable(false);
           this.luvanvaraisuusColumn.setEditable(false);
           this.tyyppiColumn.setEditable(false);
           
           //Esimies
       }else if(controller.getLoggedIn().getValtuudet()==1){
           this.henkilostoBtn.setDisable(true);
           this.henkilostoBtn.setOpacity(0);
       }
       

    }

    /**
     * Palautaa uuden datepickerin, jolla on muutoksiin kuuntelia. Kuuntelia
     * rajaa varauksista siten että ne, jotka eivät voi varata sinä päivänä ei
     * ilmesty tulukkoon.
     *
     * @return uusi datepicker, jolla on muutoksiin kuuntelia.
     */
    @Override
    public DatePicker picker() {
        DatePicker p = new DatePicker();
        p.valueProperty().addListener((ov, oldValue, newValue) -> {
            String tabText = tabPane.getSelectionModel().getSelectedItem().getText();
            if (tabText.equals(controller.getConfigTeksti(RESOURCES)) && oldValue != newValue) {
                kaikkiTableView.getItems().clear();
                LocalDate paiva = p.getValue();
                Resurssit[] res = controller.haeKaikkiResurssit();
                Varaukset[] varaukset = controller.haeKaikkiVaraukset();

                ChronoLocalDateTime paivaAlku = paiva.atTime(LocalTime.MIN);
                ChronoLocalDateTime paivaLoppu = paiva.atTime(LocalTime.MAX);
                for (Resurssit resurssi : res) {
                    if (controller.onnistuu(controller.resurssinVaraukset(resurssi.getId(), varaukset), paivaLoppu, paivaAlku)) {
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
        return p;
    }

    /**
     * Poistaa taulukosta ja tietokannasta sekä päivittää taulukon
     *
     * @param varaus poistettava varaus
     */
    @Override
    public void completeRemove(Varaukset varaus) {
        if (controller.poistaVarausBtnToiminto(varaus)) {
            omatTable.getItems().remove(varaus);
            omatTable.refresh();
        }
    }

    /**
     * Päivittää taulukot näkymässä.
     *
     */
    @FXML
    @Override
    public void update() {
        kaikkiTableView.getItems().clear();
        omatTable.getItems().clear();
        Resurssit[] res = controller.haeKaikkiResurssit();
        kaikkiTableView.getItems().addAll(res);
        Varaukset[] varauksetArr = controller.haeKayttajanVaraukset(controller.getLoggedIn());
        omatTable.getItems().addAll(varauksetArr);
    }

    /**
     * Avaa varaus-popupin
     *
     * @param event Hiiren painallus.
     * @throws IOException Tiedostoja luettaessa varauduttava poikkeus.
     */
    @FXML
    @Override
    public void varausNappiPainettu(MouseEvent event) throws IOException {
        controller.setBooking(kaikkiTableView.getSelectionModel().getSelectedItem());
        if (controller.getBooking() != null) {
            if (controller.getBooking().isStatus()) {
                if (popup == null || !popup.isShowing()) {
                    popup = new Popup();
                    Object source = event.getSource();
                    Node node = (Node) source;
                    Scene scene = node.getScene();
                    Window window = scene.getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Varausikkuna.fxml"));
                    popup.getContent().add((Parent) loader.load());
                    popup.show(window);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, controller.getConfigTeksti("resourceNotAbleReservation"));
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, controller.getConfigTeksti(CHOOSERESOURCE));
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
    @Override
    public void lisaaresurssiNappiPainettu(MouseEvent event) throws IOException {
        if (popup == null || !popup.isShowing()) {
            popup = new Popup();
            Object source = event.getSource();
            Node node = (Node) source;
            Scene scene = node.getScene();
            Window window = scene.getWindow();
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
    @Override
    public void salasananvaihtoNappiPainettu(MouseEvent event) throws IOException {
        if (popup == null || !popup.isShowing()) {
            popup = new Popup();
            Object source = event.getSource();
            Node node = (Node) source;
            Scene scene = node.getScene();
            Window window = scene.getWindow();
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
    @Override
    public void logout(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Loginwindow.fxml"));
        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        Parent root = loader.load();
        stage.getScene().setRoot(root);
        controller.setLoggedIn(null);
    }

    /**
     * Siirtyy varausten hallintanäkymään
     *
     * @param event Hiiren painallus painikkeesta.
     * @throws IOException Tiedostoja käsitellessä varauduttava poikkeus.
     */
    @FXML
    @Override
    public void hallinnoiBtnPainettu(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VarausAdmin.fxml"));
        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        Parent root = loader.load();
        stage.getScene().setRoot(root);
    }

    /**
     * Siirtyy henkilöstön hallintanäkymään
     *
     * @param event Hiiren painallus käyttöliittymässä vastaavasta painikkeesta.
     * @throws IOException Tiedostoja käsiteltäessä varauduttava poikkeus.
     */
    @FXML
    @Override
    public void henkilostoBtnPainettu(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/KayttajaAdmin.fxml"));
        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        Parent root = loader.load();
        stage.getScene().setRoot(root);
    }

    @FXML
    @Override
    public void historiaBtnPainettu(MouseEvent event) throws IOException {
        controller.setBooking(kaikkiTableView.getSelectionModel().getSelectedItem());
        if (controller.getBooking() != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ResurssiHistoria.fxml"));
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            Parent root = loader.load();
            stage.getScene().setRoot(root);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, controller.getConfigTeksti(CHOOSERESOURCE));
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
    @Override
    public void poistaresurssiNappiPainettu(MouseEvent event) throws IOException {
        Resurssit toDelete = kaikkiTableView.getSelectionModel().getSelectedItem();
        if (toDelete != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, controller.getConfigTeksti("alertConfirmationRemoveResource"), ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                controller.poistaResurssi(toDelete);
                this.update();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, controller.getConfigTeksti(CHOOSERESOURCE));
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
        Resurssit resurssi = kaikkiTableView.getSelectionModel().getSelectedItem();
        resurssi.setNimi(event.getNewValue());
        controller.paivitaResurssi(resurssi);
    }

    /**
     * Toiminnallisuus tyyppi-columnin muokkaamisen päättyessä.
     *
     * @param event ENTER-painallus tyyppi-sarakkeen muokkaamisen jälkeen.
     */
    @FXML
    private void tyyppiOnEditCommit(TableColumn.CellEditEvent<Resurssit, String> event) {
        Resurssit resurssi = kaikkiTableView.getSelectionModel().getSelectedItem();
        resurssi.setTyyppi(event.getNewValue());
        controller.paivitaResurssi(resurssi);
    }

    /**
     * Toiminnallisuus luvanvaraisuus-columnin muokkaamisen päättyessä.
     *
     * @param event ENTER-painallus luvanvaraisuus-sarakkeen muokkaamisen
     * jälkeen.
     */
    @FXML
    private void luvanvaraisuusOnEditCommit(TableColumn.CellEditEvent<Resurssit, Integer> event) {
        Resurssit resurssi = kaikkiTableView.getSelectionModel().getSelectedItem();
        resurssi.setLuvanvaraisuus((event.getNewValue()));
        controller.paivitaResurssi(resurssi);
    }

    /**
     * Toiminnallisuus kuvaus-columnin muokkaamisen päättyessä.
     *
     * @param event ENTER-painallus kuvaus-sarakkeen muokkaamisen jölkeen.
     */
    @FXML
    private void kuvausOnEditCommit(TableColumn.CellEditEvent<Resurssit, String> event) {
        Resurssit resurssi = kaikkiTableView.getSelectionModel().getSelectedItem();
        resurssi.setKuvaus(event.getNewValue());
        controller.paivitaResurssi(resurssi);
    }

    /**
     * Toiminnallisuus tila-columnin muokkaamisen päättyessä.
     *
     * @param event ENTER-painallus tila-sarakkeen muokkaamisen jälkeen.
     */
    @FXML
    private void tilaOnEditCommit(TableColumn.CellEditEvent<Resurssit, Boolean> event) {
        Resurssit resurssi = kaikkiTableView.getSelectionModel().getSelectedItem();
        resurssi.setStatus(event.getNewValue());
        controller.paivitaResurssi(resurssi);
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
        if (tabText.equals(controller.getConfigTeksti(RESOURCES)) && selectedCategory.equals(controller.getConfigTeksti("name").toUpperCase())) {
            kaikkiTableView.getItems().clear();
            for (Resurssit resurssi : kalenterinPienentamaResurssiLista) {
                String resurssiPienella = resurssi.getNimi().toLowerCase();
                if (resurssiPienella.indexOf(query) != -1) {
                    kaikkiTableView.getItems().add(resurssi);
                    kaikkiTableView.refresh();

                }
            }
        } else if (tabText.equals(controller.getConfigTeksti(RESOURCES)) && selectedCategory.equals(controller.getConfigTeksti(CATEGORY).toUpperCase())) {
            kaikkiTableView.getItems().clear();
            for (Resurssit resurssi : kalenterinPienentamaResurssiLista) {
                String resurssiPienella = resurssi.getTyyppi().toLowerCase();

                if (resurssiPienella.indexOf(query) != -1) {
                    kaikkiTableView.getItems().add(resurssi);
                    kaikkiTableView.refresh();
                }
            }
        }

        if (tabText.equals(controller.getConfigTeksti(OMAT)) && selectedCategory.equals(controller.getConfigTeksti("name").toUpperCase())) {
            Varaukset[] omatVaraukset = controller.haeKayttajanVaraukset(controller.getLoggedIn());
            omatTable.getItems().clear();
            for (Varaukset varaus : omatVaraukset) {
                String resurssiPienella = varaus.getResurssit().getNimi().toLowerCase();
                if (resurssiPienella.indexOf(query) != -1) {
                    omatTable.getItems().add(varaus);
                    omatTable.refresh();

                }
            }
        } else if (tabText.equals(controller.getConfigTeksti(OMAT)) && selectedCategory.equals(controller.getConfigTeksti(CATEGORY).toUpperCase())) {
            Varaukset[] omatVaraukset = controller.haeKayttajanVaraukset(controller.getLoggedIn());
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
