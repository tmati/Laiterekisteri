/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
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
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Kirjautumisikkunan toiminnallisuus.
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
    private Button testBtn;
    @FXML
    private ImageView bgView;

    private boolean loginPossible;
    
    Popup popup;
    
    private Controller controller;

    /**
     * Initializes the controller class.
     *
     * @param url URL
     * @param rb ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Image image = new Image(getClass().getResourceAsStream("/Long beach.png"));
        logoView.setImage(image);
        centerImage(logoView);
        controller = new Controller();
        usernameField.setId("usernameField");
        
        this.passwordField.setTooltip(new Tooltip("Kenttä salasanaa varten"));
        this.usernameField.setTooltip(new Tooltip("Kenttä käyttäjänimeä varten"));
   
       
    }

    /**
     * Keskittää kuvan imageviewissä. Netistä haettu.
     * @param i Kuva joka halutaan keskittää.
     */
    public void centerImage(ImageView i) {
        Image img = i.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = i.getFitWidth() / img.getWidth();
            double ratioY = i.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if (ratioX >= ratioY) {
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
     * Heittää herjan jos käyttäjä yrittää kirjautua asettamatta vaadittavia
     * tietoja.
     * @param event Hiiren klikkaus
     */
    @FXML
    private void herjaaPuuttuvasta(MouseEvent event) {
        Alert alert = new Alert(AlertType.WARNING, "Tunnus tai salasana puuttuu!");
        alert.showAndWait();
    }

    /**
     * Hiirieventti login-painikkeen klikkaamiseen.
     * @param event Hiiren klikkaus kirjaudu-sisään painikkeeseen.
     */
    @FXML
    private void loginAttempt(MouseEvent event) {
        System.out.println(usernameField.getText() + " " + passwordField.getText());
        if (controller.login(usernameField.getText(), passwordField.getText())) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/nakyma.fxml"));
                Stage stage = (Stage) logoView.getScene().getWindow();
                Parent root = loader.load();
                stage.getScene().setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Väärä käyttäjätunnus tai salasana");
            alert.showAndWait();
        }
    }

    /**
     * Enter-painikkeen painaminen login-näkymässä.
     * @param ke Kuunneltava näppäimistön painallus
     * @throws IOException Keyeventiin liittyvä poikkeus, johon pitää varautua.
     */
    @FXML
    private void handle(KeyEvent ke) throws IOException {
        if (ke.getCode() == KeyCode.ENTER && loginPossible) {
            System.out.println("ENTER");
            if (controller.login(usernameField.getText(), passwordField.getText())) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/nakyma.fxml"));
                    Stage stage = (Stage) logoView.getScene().getWindow();
                    Parent root = loader.load();
                    stage.getScene().setRoot(root);
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        } else if (ke.getCode() == KeyCode.ENTER && !loginPossible) {
            Alert alert = new Alert(AlertType.WARNING, "Tunnus tai salasana puuttuu!");
            alert.showAndWait();
        }
    }

    /**
     * Tarkastaa login-kenttien sisällön ja muuttaa kirjautumispainikkeen
     * aktiivisuutta sen mukaan.
     * @param event Kuunneltavan näppäimistön painallus
     * @throws IOException KeyEventiin liittyvä poikkeus.
     */
    @FXML
    private void checkVal(KeyEvent event) throws IOException {
        if (!usernameField.getText().trim().equals("") && !passwordField.getText().trim().equals("")) {
            loginInactive.setOpacity(0);
            loginInactive.setDisable(true);
            loginPossible = true;
        } else {
            loginInactive.setOpacity(1);
            loginInactive.setDisable(false);
            loginPossible = false;
        }
    }
    
    @FXML
    private void avaaPalautaSalasana(MouseEvent event) throws IOException {
                if (popup == null || !popup.isShowing()) {
            popup = new Popup();
            Object source = event.getSource();
            Node node = (Node) source;
            Scene scene = node.getScene();
            Window window = scene.getWindow();
            Stage stage = (Stage) window;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/palautasalasana.fxml"));
            popup.getContent().add((Parent) loader.load());
            popup.show(window);
    }
}
}
