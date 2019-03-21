/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Model.*;

/**
 * Controlleri
 * @author Tommi
 */
public class Controller {

    private KayttajaAccessObject kayttajaDAO;
    private ResurssitAccessObject resurssiDAO;
    private VarauksetAccessObject varausDAO;
    private KayttajaTarkistus kayttajaTarkistus;
    private PasswordConverterInterface salasananVaihto = new PasswordConverter();
    private LoginUtils login;

    /**
     *Controllerin konstruktio
     */
    public Controller() {
        kayttajaDAO = new KayttajaAccessObject();
        kayttajaTarkistus = new KayttajaTarkistus(this);
        login = new LoginUtils(this);
    }
    
    /**
     * Vie salasanan encryptattavaksi modeliin.
     * @param password Encryptattava salasana.
     * @return Palautaa encryptattattu salasanan.
     */
    public String salasananVaihto(String password){
        return salasananVaihto.passwordConverter(password);
    }
    
    /**
     * Hakee modelista kaikki käytäjät
     * @return
     */
    public Kayttaja[] haeKaikkiKayttajat(){
        return kayttajaDAO.readKayttajat();
    }
    
    /**
     * hakee modelista käytäjän
     * @param id
     * @return
     */
    public Kayttaja haeKayttaja(int id){
        return kayttajaDAO.readKayttaja(id);
    }
    
    /**
     * vie uuden käytäjän modeliin
     * @param kayttaja
     * @return
     */
    public boolean vieUusiKayttajaTietokantaan(Kayttaja kayttaja){
        return kayttajaDAO.createKayttaja(kayttaja);
    }
    
    /**
     * vie käytäjän modeliin
     * @param kayttaja
     * @return
     */
    public boolean paivitaKayttaja(Kayttaja kayttaja){
        return kayttajaDAO.updateKayttaja(kayttaja);
    }
    
    /**
     * vie käytäjän poistettavaksi
     * @param id
     * @return
     */
    public boolean poistaKayttaja(int id){
        return kayttajaDAO.deleteKayttaja(id);
    }
    
    /**
     * tarkistaa modelista käyttäjätunnuksen.
     * @param username
     * @return
     */
    public boolean tarkistaUsername(String username){
        return kayttajaTarkistus.usernameTarkastus(username);
    }
    
    /**
     * tarkistaa modelista sähköpostin.
     * @param email
     * @return
     */
    public boolean tarkistaEmail(String email){
        return kayttajaTarkistus.emailTarkastus(email);
    }
    
    /**
     * Tarlostaa modelista käyttäjänimen ja salasanan.
     * @param userName
     * @param passWord
     * @return 
     */
    public boolean login(String userName, String passWord) {
        System.out.println("Controller");
        return login.loginProcess(userName, passWord);
    }
}
