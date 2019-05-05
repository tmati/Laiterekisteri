/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Controller;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Luokka sähköposti ilmoitusten lähettämistä varten.
 * @author Tommi
 */
public class Sahkoposti {

    private Properties emailProperties;
    private static final String EMAILHOST = "smtp.gmail.com";
    private static final String FROMUSER = "keychainems@gmail.com";
    private final String fromUserEmailPassword;
    private static final String EMAILSUBJECT = "KeyChain automatic message";

 
    /**
     * Konstruktori
     * Kutsuu setMailServerProperties()
     * @param controller viittaus controller -luokkaan
     */
    public Sahkoposti(Controller controller) {
        fromUserEmailPassword = controller.getConfigTeksti("kissatkoiria");
        setMailServerProperties();
    }
    
    /**
     * Setuppaa MailServer tiedot.
     */
    private void setMailServerProperties() {
        String emailPort = "587";//gmail's smtp port

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
    }
    
    /**
     * Kasaa ja lähettää sähköpostin
     * @param vastaanottaja vastaanottajan sähköposti
     * @param viesti lähetettävä viesti
     * @return true jos lähetys onnistui
     */
    private boolean sendEmail(String vastaanottaja, String viesti) {

        MimeMessage emailMessage;
        Session mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);

        try {
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(vastaanottaja));
            emailMessage.setSubject(EMAILSUBJECT);
            emailMessage.setText(viesti);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(EMAILHOST, FROMUSER, fromUserEmailPassword);
            transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            return false;
        } 
        return true;
    }
    
    
    
    /**
     * Metodi joka kutsuu sähköpostin lähettähää säikeessä. 
     * Vähentää viivettä joka tulisi ilman säiettä
     * @param vastaanottaja vastaanottajan sähköposti
     * @param viesti lähetettävä viesti
     * @return true jos lähetys onnistuu
     */
    public boolean lahetaSahkoposti(String vastaanottaja, String viesti){
        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        
        emailExecutor.execute(() -> {
            try {
                sendEmail(vastaanottaja, viesti);
            } catch (Exception e) {
                emailExecutor.shutdown();
            }
        });
        emailExecutor.shutdown();
        return true;
    }

}
