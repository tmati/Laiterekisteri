/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Resource;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 *
 * @author jukka holopainen
 */
public class KayttajaUI extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        // Logout nappi
        Button logout = new Button();
        logout.setText("Logout");
        logout.setMaxSize(60, 10);
        logout.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Loging out");
            }
        });
        
        //Hakukentta
        TextField field = new TextField(); 
        field.setMinSize(80, 10);
        field.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){
                    System.out.println("Searching "+field.getText());
                }
                
            }
        });
        
        //hakunappi
        Button search = new Button();
        search.setText("Search");
        search.setMaxSize(60, 10);
        search.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Searching "+field.getText());
            }
        });
        
        //varaus nappi
        Button reservation = new Button();
        reservation.setText("Reservation");
        reservation.setMaxSize(100, 10);
        reservation.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Making reservation "+field.getText());
            }
        });
        
        //kayttajanimi
        Text nimi = new Text();
        nimi.setText("Username");
        nimi.setStyle("-fx-font: 18 arial;");
        nimi.setOnMouseReleased(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event) {
                System.out.println("käyttäjä tapahtuma");
            }
        });
        
        //kuvaus lainauksesta alue
        TextArea lainausKuvaus = new TextArea();
        lainausKuvaus.setText("Lainaukset");
        lainausKuvaus.setEditable(false);
        
        //kuvaus tavarasta alue
        TextArea tavaranKuvaus = new TextArea();
        tavaranKuvaus.setText("Resurssin desc.");
        tavaranKuvaus.setEditable(false);
        
        //lista resurseista
        Resource resurssi = new Resource(7, "nimi", "tyypi", 0, "kuvaus");
        ObservableList<Model.Resource> data = FXCollections.observableArrayList(
                resurssi,resurssi);
        
        TableView<Model.Resource> table = new TableView<Model.Resource>();
        table.setMinSize(270, 500);
   
        TableColumn name = new TableColumn("Name");
        name.setMinWidth(100);
        name.setCellValueFactory(new PropertyValueFactory<Model.Resource, String>("nimi"));
        
        TableColumn tyyppi = new TableColumn("Type");
        tyyppi.setMinWidth(100);
        tyyppi.setCellValueFactory(new PropertyValueFactory<Model.Resource, String>("type"));

        TableColumn lupa = new TableColumn("Permission");
        lupa.setMinWidth(50);
        lupa.setCellValueFactory(new PropertyValueFactory<Model.Resource, String>("access"));

        TableColumn tila = new TableColumn("Avaible");
        tila.setMinWidth(20);
        tila.setCellValueFactory(new PropertyValueFactory<Model.Resource, String>("details"));

        table.getColumns().addAll(name, tyyppi, tila, lupa);
        table.setItems(data);
        TableRow cell = new TableRow();

        table.setOnMousePressed(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event) {
                cell.setOnMouseClicked(this);
                System.out.println("Rivi " + cell);
            }
        });

        table.addEventFilter(MouseEvent.MOUSE_PRESSED, (event) -> {
            if(event.isShortcutDown() || event.isShiftDown())
                event.consume();
        });


        table.getFocusModel().focusedCellProperty().addListener((obs, oldVal, newVal) -> {

            if(newVal.getTableColumn() != null){
                System.out.println("Selected row index: "+ newVal.getRow() + table.getColumns().get(0).getCellObservableValue(newVal.getRow()).getValue());
            }
        });

        table.addEventFilter(MouseEvent.MOUSE_PRESSED, (event) -> {
            if(event.isShortcutDown() || event.isShiftDown())
                event.consume();
        });
        
        //kalenteri
        DatePicker datePicker = new DatePicker(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node popupContent = datePickerSkin.getPopupContent();

        datePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                LocalDate selectedDate = datePicker.getValue();
                System.out.println("Kalenteri päivä " + selectedDate);
            }
        });
    
        //tavaroiden laitaminen grid panniin
        GridPane grid = new GridPane();
        grid.add(nimi, 0, 0);
        grid.add(logout, 2, 0);
        grid.add(field, 4,0);
        grid.add(search, 5, 0);
        grid.add(table, 0, 1, 3,3);
        grid.add(popupContent, 4, 3);
        grid.add(tavaranKuvaus, 4, 1);
        grid.add(lainausKuvaus, 4, 2);
        grid.add(reservation, 2, 4);
        Scene scene = new Scene(grid, 820, 680);
        
        primaryStage.setTitle("User Interface demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
