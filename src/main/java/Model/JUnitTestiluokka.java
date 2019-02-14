/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Tommi
 */
public class JUnitTestiluokka {
    
    public JUnitTestiluokka(){
}
    
     public Kayttaja testi(int id) {

        Kayttaja k = new Kayttaja("Jokke", "passu", 1);
        Kayttaja j = new Kayttaja("Jakke", "p4ssu", 2);
        if (id == 1) {
            return k;
        } else {
            return j;

        }
    }
}