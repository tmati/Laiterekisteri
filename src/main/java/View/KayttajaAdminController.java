/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Kayttaja;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        
        Kayttaja K = new Kayttaja("Testi", "tESTI","testi","testi", 1);
        nimiColumn.setCellValueFactory(new PropertyValueFactory<Kayttaja,String>("Nimi"));
        nimiColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        emailColumn.setCellValueFactory(new PropertyValueFactory<Kayttaja,String>("Sähköposti"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        valtuudetColumn.setCellValueFactory(new PropertyValueFactory<Kayttaja, Integer>("Valtuudet"));
        valtuudetColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        kayttajatunnusColumn.setCellValueFactory(new PropertyValueFactory<Kayttaja, String>("Käyttäjätunnus"));
        kayttajatunnusColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        kayttajaTableView.getItems().add(K);
    }    

    public void logout(MouseEvent event) throws IOException {
        System.out.println("Logout");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Loginwindow.fxml"));
        Stage stage = (Stage) LogoutBtn.getScene().getWindow();
        Parent root = loader.load();
        stage.getScene().setRoot(root);
    }

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

    @FXML
    private void muutaBtnPainettu(MouseEvent event) {
        //TODO
        System.out.println("Muutetaan riviä");
    }

    @FXML
    private void poistaBtnPainettu(MouseEvent event) {
        //TODO
        System.out.println("Poistetaan rivi");
    }
    
    
    @FXML
    private void nimiEditCommit(TableColumn.CellEditEvent<Kayttaja, String> event) {
        Kayttaja J = kayttajaTableView.getSelectionModel().getSelectedItem();
        J.setNimi(event.getNewValue());
        System.out.println("Uusi nimi: " + J.getNimi());
    }

    @FXML
    private void emailEditCommit(TableColumn.CellEditEvent<Kayttaja, String> event) {
        /*Kayttaja J = kayttajaTableView.getSelectionModel().getSelectedItem();
        J.setEmail(event.getNewValue());*/
    }

    @FXML
    private void valtuudetEditCommit(TableColumn.CellEditEvent<Kayttaja, Integer> event) {
        Kayttaja J = kayttajaTableView.getSelectionModel().getSelectedItem();
        J.setValtuudet(event.getNewValue());
        System.out.println("Uudet valtuudet: " + J.getValtuudet());
    }
    
    @FXML
    private void kayttajatunnusEditCommit(TableColumn.CellEditEvent<Kayttaja, String> event) {
        Kayttaja J = kayttajaTableView.getSelectionModel().getSelectedItem();
        J.setKayttajatunnus(event.getNewValue());
        System.out.println("Uusi tunnus: " + J.getKayttajatunnus());
    }
}
