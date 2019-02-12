/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

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
    private ChoiceBox<?> caregorySelect;
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
    private TableView<?> kaikkiTableView;
    @FXML
    private TableColumn<?, ?> idColumn;
    @FXML
    private TableColumn<?, ?> nimiColumn;
    @FXML
    private TableColumn<?, ?> valmistajaColumn;
    @FXML
    private TableColumn<?, ?> tyyppiColumn;
    @FXML
    private TableColumn<?, ?> kuvausColumn;
    @FXML
    private TableColumn<?, ?> tilaColumn;
    @FXML
    private Tab omatTab;
    @FXML
    private AnchorPane omatAnchor;
    @FXML
    private StackPane omatStack;
    @FXML
    private TableView<?> omatTable;
    @FXML
    private TableColumn<?, ?> laiteidColumn;
    @FXML
    private TableColumn<?, ?> alkupvmColumn;
    @FXML
    private TableColumn<?, ?> paattumispvmColumn;
    @FXML
    private TableColumn<?, ?> varausidColumn;
    @FXML
    private TableColumn<?, ?> varauskuvausColumn;
    @FXML
    private TableColumn<?, ?> palautettuColumn;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button varausBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
