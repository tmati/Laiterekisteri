/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.TAB;
import javafx.stage.Stage;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.loadui.testfx.GuiTest;
import static org.loadui.testfx.GuiTest.find;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

/**
 *
 * @author jukka
 */
@Ignore
public class loginWindowControllerTest extends ApplicationTest {

    
    FxRobot robot = new FxRobot();
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Loginwindow.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    
    public loginWindowControllerTest() {
    }
    
    
    @BeforeClass
    public static void setUpClass() {

    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testStart() {
        lookup((ImageView i) -> {
            System.out.println("Toimiiko?" + i.isVisible());
            return i.isVisible();
        });
    }
    
    @Test
    public void testLoginAttempt1(){
        robot.clickOn("#usernameField");
        robot.write("testi");
        robot.type(TAB);
        robot.write("testi1");
        robot.type(ENTER);
        
        assertEquals(View.loggedIn.getNimi(), "testi");
        
    }
    
    @Test
    public void testLoginAttempt2(){
        write("testi");
        type(TAB);
        write("testi1");
        clickOn("#loginActive");
        assertEquals(View.loggedIn.getNimi(), "testi");
        
    }
    
}