/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Salasanan kääntäjän interface
 *
 * @author jukka
 */
public interface PasswordConverterInterface {

    /**
     * Metodi joka encryptaa salasanan.
     *
     * @param password Salasana joka muutetaan
     * @return salasanan muutetussa muodossa.
     */
    public String passwordConverter(String password);
}
