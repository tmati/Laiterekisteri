/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author jukka
 */
public interface LoginUtilsIf {

    /**
     * Sisäänkirjautuminen
     *
     * @param userName username-kentän sisältö
     * @param passWord password-kentän sisältö
     * @return true jos käyttäjä/salasanapari on oikea.
     */
    boolean loginProcess(String userName, String passWord);
    
}
