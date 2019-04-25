/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 *
 * @author jukka
 */
public class LanguageText {
    
    private static LanguageText INSTANCE = null;
    private Properties properties = new Properties();
    private ResourceBundle testi;
    private String maa = "fi";
    
    private LanguageText() {
        
    }
    
    public static LanguageText getInstance() {
        if(INSTANCE == null){
            INSTANCE = new LanguageText();
            INSTANCE.PropertiesConstructor();
        }
        return INSTANCE;
    }
    
    private void PropertiesConstructor(){
        try{
            properties.load(new FileInputStream("config.properties.txt"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public String getText(String whichText){
        return properties.getProperty(whichText.concat(maa));
    }
    
    public void setMaa(String maa){
        this.maa = maa;
    }
}
