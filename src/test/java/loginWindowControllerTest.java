
import view.View;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.TAB;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
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
    public void setUp() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(View.class);
        View.loggedIn = null;
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
    public void testLoginAttemptEnter() {
        write("testi");
        type(TAB);
        write("testi");
        clickOn("#loginActive");
        assertEquals("testi",View.loggedIn.getNimi());
    }

    @Test
    public void testLoginAttemptClick() {
        robot.clickOn("#usernameField");
        robot.write("testi");
        robot.type(TAB);
        robot.write("testi");
        robot.type(ENTER);
        assertEquals("testi",View.loggedIn.getNimi());

    }
    
    @Test
    public void testLoginAttemptClickVääräSalasana() {
        robot.clickOn("#usernameField");
        robot.write("testi");
        robot.type(TAB);
        robot.write("nslf");
        clickOn("#loginActive");
        verifyThat(".warning", Node::isVisible); 
        type(ENTER);
        assertEquals(null, View.loggedIn);
    }

    @Test
    public void testLoginAttemptTyhjäSalasanaKenttäEnter() throws InterruptedException {
        write("testi");
        type(ENTER);
        verifyThat(".warning", Node::isVisible); 
        type(ENTER);
        assertEquals(null, View.loggedIn);

    }
    
    @Test
    public void testLoginAttemptTyhjäSalasanaKenttäMouseclick() throws InterruptedException {
        write("testi");
        clickOn("#loginInactive");
        verifyThat(".warning", Node::isVisible); 
        type(ENTER);
        assertEquals(null, View.loggedIn);

    }

}
