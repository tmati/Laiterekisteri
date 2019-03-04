/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Resurssit;
import Model.Varaukset;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;

/**
 * FXML Controller class
 *
 * @author tmati
 */
public class VarausAdminController implements Initializable {

    @FXML
    private Label usernameLabel;
    @FXML
    private Button LogoutBtn;
    @FXML
    private Label bizName;
    @FXML
    private TableView<Varaukset> varauksetTableView;
    @FXML
    private TableColumn nimiColumn;
    @FXML
    private TableColumn tavaraColumn;
    @FXML
    private TableColumn alkupvmColumn;
    @FXML
    private TableColumn paattymispvmColumn;
    @FXML
    private TableColumn kuvausColumn;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button takaisinBtn;
    @FXML
    private Button hyvaksyBtn;
    @FXML
    private Button hylkaaBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
        nimiColumn.setCellValueFactory(new PropertyValueFactory<Varaukset,String>("Varaajan nimi"));
        nimiColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        tavaraColumn.setCellValueFactory(new PropertyValueFactory<Varaukset,Resurssit>("Varattava tavara"));
        tavaraColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        alkupvmColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Date>("Varauksen alkupäivämäärä"));
        alkupvmColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        
        paattymispvmColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, Date>("Varauksen päättymispäivämäärä"));
        paattymispvmColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        
        kuvausColumn.setCellValueFactory(new PropertyValueFactory<Varaukset, String>("Varauksen kuvaus"));
        kuvausColumn.setCellFactory(TextFieldTableCell.forTableColumn());
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
        Scene scene = new Scene((Parent) loader.load());
        stage.setScene(scene);
    }

    @FXML
    private void hyvaksyBtnPainettu(MouseEvent event) {
        System.out.println("Varaus hyväksytty!");
    }

    @FXML
    private void hylkaaBtnPainettu(MouseEvent event) {
        System.out.println("Varaus hylätty!");
    }
    
    @FXML
    private void nimiEditCommit(TableColumn.CellEditEvent<Varaukset, String> event) {
        Varaukset V = varauksetTableView.getSelectionModel().getSelectedItem();
        V.setNimi(event.getNewValue());
        System.out.println("Uusi nimi: " + V.getNimi());
    }
    
    @FXML
    private void tavaraEditCommit(TableColumn.CellEditEvent<Varaukset, Resurssit> event) {
        Varaukset V = varauksetTableView.getSelectionModel().getSelectedItem();
        V.setResurssit(event.getNewValue());
        System.out.println("Uusi tavara: " + V.getResurssit().getNimi());
    }
    
    @FXML
    private void alkupvmEditCommit(TableColumn.CellEditEvent<Varaukset, Date> event) {
        Varaukset V = varauksetTableView.getSelectionModel().getSelectedItem();
        V.setAlkupvm(event.getNewValue());
        System.out.println("Uusi alkupvm: " + V.getAlkupvm().toString());
    }
    
    @FXML
    private void paattymispvmEditCommit(TableColumn.CellEditEvent<Varaukset, Date> event) {
        Varaukset V = varauksetTableView.getSelectionModel().getSelectedItem();
        V.setPaattymispvm(event.getNewValue());
        System.out.println("Uusi päättymispvm: " + V.getPaattymispvm().toString());
    }
    
    @FXML
    private void kuvausEditCommit(TableColumn.CellEditEvent<Varaukset, String> event) {
        Varaukset V = varauksetTableView.getSelectionModel().getSelectedItem();
        V.setKuvaus(event.getNewValue());
        System.out.println("Uusi Kuvaus: " + V.getKuvaus());
    }
    
    //TEE ON EDIT COMMITIT
}
