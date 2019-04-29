/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Luokka sähköposti ilmoitusten lähettämistä varten.
 * @author Tommi
 */
public class Sahkoposti {

    private Properties emailProperties;
    private Session mailSession;
    private final String emailHost = "smtp.gmail.com";
    private final String fromUser = "keychainems@gmail.com";//just the id alone without @gmail.com
    private final String fromUserEmailPassword = "kissatkoiria";
    private final String emailSubject = "KeyChain automatic message";
    /**
     * Konstruktori
     * Kutsuu setMailServerProperties()
     */
    public Sahkoposti() {
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
        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);

        try {
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(vastaanottaja));
            emailMessage.setSubject(emailSubject);
            emailMessage.setText(viesti);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(emailHost, fromUser, fromUserEmailPassword);
            transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
            transport.close();
            System.out.println("Email sent successfully.");
        } catch (AddressException ex) {
            return false;
        } catch (MessagingException ex) {
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
        boolean tulos = false;
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    sendEmail(vastaanottaja, viesti);
                } catch (Exception e) {
                    System.out.println("säie fail" + e);
                }
            }
        });
        emailExecutor.shutdown();
        return tulos;
    }

}
