/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.Popup;

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
    private Button varausBtn;
    @FXML
    private TitledPane kalenteriPane;
    @FXML
    private AnchorPane kalenteriAnchorPane;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categorySelect.setTooltip(new Tooltip("Hakukriteeri"));
        DatePickerSkin DPS = new DatePickerSkin(new DatePicker());
        Node calContent = DPS.getPopupContent();
        kalenteriAnchorPane.getChildren().add(calContent);
    }
    
    /**
     * Avaa varaus-popupin
     * @param event
     * @throws IOException 
     */
    public void varausNappiPainettu(MouseEvent event) throws IOException {
        Popup popup = new Popup();
        Object source = event.getSource();
        Node node = (Node) source;
        Scene scene = node.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Varausikkuna.fxml"));
        popup.getContent().add((Parent) loader.load());
        popup.show(window);
    }
    
    /**
     * Kirjaa käyttäjän ulos ja siirtyy kirjautumisnäkymään.
     * @param event
     * @throws IOException 
     */
    public void logout(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Loginwindow.fxml"));
        Stage stage = (Stage) LogoutBtn.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load());
        stage.setScene(scene);
    }
}
