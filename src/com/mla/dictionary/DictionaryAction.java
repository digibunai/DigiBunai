/*
 * Copyright (C) 2017 Media Lab Asia
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mla.dictionary;

import com.mla.main.Configuration;
import com.mla.main.DbConnect;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Amit Kumar Singh
 */
public class DictionaryAction {
    public Hashtable reverseDictionary=new Hashtable();
    public Hashtable dictionary=new Hashtable();
    String languageName;
    
    Dictionary objDictoanry;
    Configuration objConfiguration;
    /*
    public DictionaryAction() {
        objDictoanry=new Dictionary();
        objDictoanry.getDictionary(objUser);
    }
    */
    public DictionaryAction() {
    
    }
    
    public DictionaryAction(Dictionary objDictoanry) {
        languageName = objDictoanry.getLanguageName();
        System.out.println(languageName);            
    }
    
    public DictionaryAction(Configuration objConfiguration) {
        languageName = objConfiguration.getStrLanguage();
        getWordFile();
    }
    
    public void getAlphabetFile() {
        try{ 
            System.out.println(languageName);
            
            String k;
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir")+"/mla/language/"+languageName+".properties")));
                while ((k=in.readLine())!=null) {
                    String[] keyArray=k.split("\t");
                    setHashes(keyArray[0],keyArray[1]);
                }
                in.close();
        }catch (IOException ex){}
    }
    
    public void getWordFile() {
        Properties properties = null;
        try {
            System.out.println(languageName);
            
            properties = new Properties();
            InputStream resourceAsStream = new FileInputStream(System.getProperty("user.dir")+"/mla/language/"+languageName+".properties");
            if (resourceAsStream != null) {
                properties.load(resourceAsStream);
            }
            for(Object k:properties.keySet()){
                String keyName = (String)k;
                setHashes( keyName.toUpperCase(),properties.getProperty(keyName) );
            }                
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void getWordDatabase() {
        Connection con=null;
        ResultSet rs=null;
        try {
            Connection connection = DbConnect.getConnection();
            Statement st = con.createStatement();
            String query  = new String("select * from `"+removeSpecialCharacters(objConfiguration.getStrLanguage())+"`");
            rs = st.executeQuery( query );

            String keyName=new String();
            String keyValue=new String();

            while (rs.next()) {  
                keyName=rs.getString(1);
                keyValue=rs.getString(2);
                if(keyName!=null)
                    setHashes( keyName.toUpperCase(),keyValue );
            }//end of while
            if(!con.isClosed())con.close();
		rs.close();
         } catch(Exception e) {
            System.err.println(objConfiguration.getStrLanguage()+": table not found");
         } finally {
            con=null;
            rs=null;
	}
    }
    
    public void setHashes( String name, String value ) {
    //System.out.println(name+":"+value);
        if (!dictionary.containsKey( name)) {
            if(!value.equals(null)) {
                dictionary.put( name, value );
                reverseDictionary.put(  value,name );
            }
        }
    }
    
    public Hashtable getHashes() {
        return dictionary;
    }

    public String getWord(String word)  {
        if(word!=null) {
            if(dictionary.containsKey(word.toUpperCase())) {
                return (String)dictionary.get(word.toUpperCase());
            } else return word;
        } else return word;
    }

    public String getWordKey(String word) {
        if(word!=null) {
            if(reverseDictionary.containsKey(word.toUpperCase())) {
                return (String)reverseDictionary.get(word.toUpperCase());
            } else return word;
        } else return word;
    }
   
    
    public static String removeSpecialCharacters(String inString) {
        // INPUT to this function is a string 
        // OUTPUT of this function is a string that contains the escape sequences of quotes and backslashes.
        if(inString == null) return null;
        String outString="";
        for(int i = 0 ;i< inString.length() ;i++)  {
            if(inString.charAt(i) =='\'' || inString.charAt(i) == '\"' || inString.charAt(i) =='\\') {
                outString += '\\';
            }
            outString += inString.charAt(i);
        }
        return(outString);
    }
    
    public List lstImportLanguage(String strLanguage){
        List lstLanguageDeatails=null, lstLanguage;
        Properties properties = null;
        try {
            System.out.println(strLanguage);
            properties = new Properties();
            InputStream resourceAsStream =  new FileInputStream(System.getProperty("user.dir")+"/mla/language/"+strLanguage+".properties");//DictionaryView.class.getClassLoader().getResourceAsStream("language/"+strLanguage+".properties");;
            if (resourceAsStream != null) {
                properties.load(resourceAsStream);
            }
            lstLanguageDeatails = new ArrayList();
            for(Object k:properties.keySet()){
                lstLanguage = new ArrayList();
                String keyName = (String)k;
                lstLanguage.add(keyName.toUpperCase());
                lstLanguage.add(properties.getProperty(keyName));
                lstLanguageDeatails.add(lstLanguage);                
            }                
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lstLanguageDeatails;
    }
    
}
