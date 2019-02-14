/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Model.Kayttaja;
import Model.KayttajaAccessObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tommi
 */
public class CRUDTest {
    
   @Test
   public void nakoisTesti(){
        
        KayttajaAccessObject dao = new KayttajaAccessObject();
        Kayttaja k = dao.testi(1);
        Kayttaja k2 = dao.testi(2);
        
        assertEquals("Jokke", k.getNimi());
        assertEquals("Jakke", k2.getNimi());
    
   }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
