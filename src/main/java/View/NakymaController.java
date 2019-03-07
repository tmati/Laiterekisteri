/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Resurssit;
import Model.Varaukset;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
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
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
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
    private TableColumn idColumn;
    @FXML
    private TableColumn nimiColumn;
    @FXML
    private TableColumn valmistajaColumn;
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
    private TableColumn laiteidColumn;
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
    
    Popup popup;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button hallnnoiBtn;
    @FXML
    private Button henkilostoBtn;
    @FXML
    private Button salasananvaihtoBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Image image = new Image(getClass().getResourceAsStream("/Long beach.png"));
        logoView.setImage(image);
        
        //Resurssitaulun columnien live-edit
        idColumn.setCellValueFactory(new PropertyValueFactory<Resurssit, Integer>("ID"));
        idColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        nimiColumn.setCellValueFactory(new PropertyValueFactory<Resurssit,String>("Nimi"));
        nimiColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        valmistajaColumn.setCellValueFactory(new PropertyValueFactory<Resurssit,String>("Valmistaja"));
        valmistajaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        tyyppiColumn.setCellValueFactory(new PropertyValueFactory<Resurssit,String>("Tyyppi"));
        tyyppiColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        kuvausColumn.setCellValueFactory(new PropertyValueFactory<Resurssit, String>("Kuvaus"));
        kuvausColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        luvanvaraisuusColumn.setCellValueFactory(new PropertyValueFactory<Resurssit, Integer>("Luvanvaraisuus"));
        luvanvaraisuusColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        tilaColumn.setCellValueFactory(new PropertyValueFactory<Resurssit, String>("Saatavilla"));
        tilaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        //Omat varaukset -taulun live-edit
        laiteidColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Integer>("LaiteID"));
        kuvausColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        laitenimiColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, String>("Varauksella olevan laitteen nimi"));
        laitenimiColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        alkupvmColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Date>("Varauksen alkupäivämäärä"));
        alkupvmColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        
        paattymispvmColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Date>("Varauksen päättymispäivämäärä"));
        paattymispvmColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        
        varausidColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Integer>("Varauksen ID"));
        varausidColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        varauskuvausColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, String>("Varauksen kuvaus"));
        varauskuvausColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        palautettuColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Boolean>("Varauksen palautuminen"));
        alkupvmColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        categorySelect.setTooltip(new Tooltip("Hakukriteeri"));
        DatePickerSkin DPS = new DatePickerSkin(new DatePicker());
        Node calContent = DPS.getPopupContent();
        
        kalenteriStackPane.getChildren().add(calContent);
    }
    
    /**
     * Avaa varaus-popupin
     * @param event
     * @throws IOException 
     */
    @FXML
    public void varausNappiPainettu(MouseEvent event) throws IOException {

        if(popup == null || !popup.isShowing()){
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
     * Avaa lisää resurssi-popupin
     * @param event
     * @throws IOException 
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
     * @param event
     * @throws IOException 
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
    }
    
    /**
     * Siirtyy varausten hallintanäkymään
     * TODO Näytä vain jos tarpeeksi oikeuksia
     * @param event
     * @throws IOException 
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
     * Siirtyy henkilöstön hallintanäkymään
     * TODO Näytä vain jos tarpeeksi oikeuksia.
     * @param event
     * @throws IOException
     */
    @FXML
    public void henkilostoBtnPainettu(MouseEvent event) throws IOException {
        System.out.println("Hallinoidaan henkilöstöä!");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/KayttajaAdmin.fxml"));
        Stage stage = (Stage) LogoutBtn.getScene().getWindow();
        Parent root = loader.load();
        stage.getScene().setRoot(root);
    }

    
    //Resurssit
    @FXML
    private void idOnEditCommit(TableColumn.CellEditEvent<Resurssit, Integer> event) {
        Resurssit R = kaikkiTableView.getSelectionModel().getSelectedItem();
        R.setId(event.getNewValue());
        System.out.println("Uusi ID: " + R.getId());
    }

    @FXML
    private void nimiOnEditCommit(TableColumn.CellEditEvent<Resurssit, String> event) {
        Resurssit R = kaikkiTableView.getSelectionModel().getSelectedItem();
        R.setNimi(event.getNewValue());
        System.out.println("Uusi nimi: " + R.getNimi());
    }

    @FXML
    private void valmistajaOnEditCommit(TableColumn.CellEditEvent<Resurssit, String> event) {
        Resurssit R = kaikkiTableView.getSelectionModel().getSelectedItem();
        //R.setValmistaja(event.getNewValue());
        //System.out.println("Uusi valmistaja: " + R.getValmistaja());
    }

    @FXML
    private void tyyppiOnEditCommit(TableColumn.CellEditEvent<Resurssit, String> event) {
        Resurssit R = kaikkiTableView.getSelectionModel().getSelectedItem();
        R.setTyyppi(event.getNewValue());
        System.out.println("Uusi Tyyppi: " + R.getTyyppi());
    }

    @FXML
    private void luvanvaraisuusOnEditCommit(TableColumn.CellEditEvent<Resurssit, Integer> event) {
        Resurssit R = kaikkiTableView.getSelectionModel().getSelectedItem();
        R.setLuvanvaraisuus(event.getNewValue());
        System.out.println("Uusi luvanvaraisuusarvo: " + R.getLuvanvaraisuus());
    }

    @FXML
    private void kuvausOnEditCommit(TableColumn.CellEditEvent<Resurssit, String> event) {
        Resurssit R = kaikkiTableView.getSelectionModel().getSelectedItem();
        R.setKuvaus(event.getNewValue());
        System.out.println("Uusi kuvaus: " + R.getKuvaus());
    }

    //Varaukset
    
    @FXML
    private void laiteidOneEditCommit(TableColumn.CellEditEvent<Varaukset, Resurssit> event) {
        Varaukset V = omatTable.getSelectionModel().getSelectedItem();
        V.setResurssit(event.getNewValue());
        System.out.println("Uusi ID:" + V.getResurssit().getId() + " " + V.getResurssit().getNimi());
    }

    @FXML
    private void laitenimiOnEditCommit(TableColumn.CellEditEvent<Varaukset, String> event) {
        Varaukset V = omatTable.getSelectionModel().getSelectedItem();
        V.setNimi(event.getNewValue());
        System.out.println("Uusi Nimi:" + V.getNimi());
    }
    
    @FXML
    private void alkupvmOnEditCommit(TableColumn.CellEditEvent<Varaukset, LocalDateTime> event) {
        Varaukset V = omatTable.getSelectionModel().getSelectedItem();
        V.setAlkuAika(event.getNewValue());
        System.out.println("Uusi alkupäivämäärä:" + V.getAlkupvm().toString());
    }

    @FXML
    private void paattymispvmOnEditCommit(TableColumn.CellEditEvent<Varaukset, LocalDateTime> event) {
        Varaukset V = omatTable.getSelectionModel().getSelectedItem();
        V.setLoppuAika(event.getNewValue());
        System.out.println("Uusi ID:" + V.getPaattymispvm());
    }

    @FXML
    private void varausidOnEditCommit(TableColumn.CellEditEvent<Varaukset, Integer> event) {
        Varaukset V = omatTable.getSelectionModel().getSelectedItem();
        V.setId(event.getNewValue());
        System.out.println("Uusi ID:" + V.getId());
    }

    @FXML
    private void palautettuOnEditCommit(TableColumn.CellEditEvent<Varaukset, Boolean> event) {
        Varaukset V = omatTable.getSelectionModel().getSelectedItem();
        V.setPalautettu(event.getNewValue());
        System.out.println("Palautettu:" + V.isPalautettu());
    }
}
