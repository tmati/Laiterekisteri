/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author tmati
 */
public class loginWindowController implements Initializable {

    @FXML
    private ImageView loginInactive;
    @FXML
    private ImageView loginActive;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ImageView logoView;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button testBtn;
    
    private boolean loginPossible;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image(getClass().getResourceAsStream("/res/Metro.png"));
        logoView.setImage(image);
        centerImage(logoView);
    }
    
    
    /**
     * Keskittää kuvan imageviewissä. Netistä haettu.
     * @param i 
     */
    public void centerImage(ImageView i) {
        Image img = i.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = i.getFitWidth() / img.getWidth();
            double ratioY = i.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            i.setX((i.getFitWidth() - w) / 2);
            i.setY((i.getFitHeight() - h) / 2);

        }
    }
    
    /**
     * Testi popup-ikkunan näyttämistä ja jatkokehittämistä varten. Tähän pitää muuttaa sisäänkirjautuneen käyttäjän näkymä kohteeksi kun valmistuu.
     * Nyt tämä ajetaan vain tavallisesta painikkeesta login-ikkunassa
     * @param event
     * @throws IOException 
     */
    @FXML
    private void test(MouseEvent event) throws IOException {
        Popup popup = new Popup();
        Object source = event.getSource();
        Node node = (Node) source ;
        Scene scene = node.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Varausikkuna.fxml"));
        popup.getContent().add((Parent)loader.load());
        popup.show(window);
    }
    
    /**
     * Heittää herjan jos käyttäjä yrittää kirjautua asettamatta vaadittavia tietoja.
     * @param event 
     */
    @FXML
    private void herjaaPuuttuvasta(MouseEvent event) {
        Alert alert = new Alert(AlertType.WARNING, "Tunnus tai salasana puuttuu!");
        alert.showAndWait();
    }
    
    /**
     * Hiirieventti login-painikkeen klikkaamiseen.
     * @param event 
     */
    @FXML
    private void loginAttempt(MouseEvent event) {
        loginProcess();
    }
    
    
    /**
     * TODO Login-painikkeen painamisen jälkeen tapahtuva toiminta. Puutteellinen ilman tietokantaa.
     */
    private void loginProcess() {
        if (loginPossible) {
            String userName = usernameField.getText();
            String passWord = passwordField.getText();
            try {
                //if (Login(userName, passWord)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("nakyma.fxml"));
                Stage stage = (Stage) loginActive.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //väärä käyttäjätunnus tai salasana ---- tämä siis Login-metodiin liittyvä else
        }
    }
    
    /**
     * Enter-painikkeen painaminen login-näkymässä.
     * @param ke
     * @throws IOException 
     */
    @FXML
    private void handle(KeyEvent ke) throws IOException {
        if (ke.getCode() == KeyCode.ENTER && loginPossible) {
            System.out.println("ENTER");
            loginProcess();
        } else if (ke.getCode() == KeyCode.ENTER && !loginPossible) {
            Alert alert = new Alert(AlertType.WARNING, "Tunnus tai salasana puuttuu!");
            alert.showAndWait();
        }
    }
    
    /**
     * Tarkastaa login-kenttien sisällön ja muuttaa kirjautumispainikkeen aktiivisuutta sen mukaan.
     * @param event
     * @throws IOException 
     */
    @FXML 
    private void checkVal (KeyEvent event) throws IOException {
        if(!usernameField.getText().trim().equals("")  && !passwordField.getText().trim().equals("")) {
            loginInactive.setOpacity(0);
            loginInactive.setDisable(true);
            loginPossible = true;
        } else {
            loginInactive.setOpacity(1);
            loginInactive.setDisable(false);
            loginPossible = false;
        }
    } 
}
