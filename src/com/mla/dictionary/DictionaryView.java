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

import com.mla.fabric.FabricView;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Set;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Amit Kumar Singh
 */
public class DictionaryView {
    private static DictionaryView defObjDictionaryView;
    private Stage dictonaryStage;
     private BorderPane root;
    private Scene scene;
    private MenuBar menuBar; 
    private Menu fileMenu;
    private MenuItem newFileMenuItem;
    private MenuItem openFileMenuItem;
    private MenuItem saveFileMenuItem;
    private MenuItem exitFileMenuItem;
    private GridPane GP_container;
    String languageName, dictionaryType;
	
        
        private Dictionary objDictionary=null;
        private DictionaryAction objDictionaryAction=null;
        private DictionaryView(Stage stage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     public DictionaryView(String dictionaryType) {
         this.dictionaryType=dictionaryType;
        dictonaryStage = new Stage();
        dictonaryStage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL
        dictonaryStage.initStyle(StageStyle.UTILITY);
        GraphicsDevice objGraphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        root = new BorderPane();
        scene = new Scene(root, objGraphicsDevice.getDisplayMode().getWidth()/2, objGraphicsDevice.getDisplayMode().getHeight()/2, Color.WHITE);
        GP_container = new GridPane();
        GP_container.setAlignment(Pos.CENTER);
        GP_container.setHgap(10);
        GP_container.setVgap(10);
        GP_container.setMaxSize(root.getWidth(), root.getHeight());
        //GP_container.setPadding(new Insets(25, 25, 25, 25));
       ScrollPane scrollPane = new ScrollPane();
       scrollPane.setContent(GP_container);
                createMenu();
          root.setTop(menuBar);  
        root.setCenter(scrollPane);
       // dictonaryStage.setFullScreen(true);
        dictonaryStage.setScene(scene);
        dictonaryStage.setTitle("Bunkar Saathi:  \u00A9 Media Lab Asia\'s Open Source CAD Tool for Weaving of Banarasi Saree");
        dictonaryStage.setIconified(false);
        dictonaryStage.setResizable(false);
        
        dictonaryStage.show();  
	}

    public void createMenu(){	
        menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(dictonaryStage.widthProperty());
        // File menu - new, save, save as, open, load recent.
        fileMenu = new Menu("File");
        newFileMenuItem = initMenuItem("New");
        openFileMenuItem = initMenuItem("Open");
        saveFileMenuItem = initMenuItem("Save");
        exitFileMenuItem = initMenuItem("Quit");
        saveFileMenuItem.setDisable(true);        
        menuBar.getMenus().add(fileMenu);
    }
    
    private MenuItem initMenuItem(final String text) {
        MenuItem item=new MenuItem(text);
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dictioanryAction(text);
            }
        });
        fileMenu.getItems().add(item);
        return item;
    }
    
    public void dictioanryAction(String text) {
        if (text.equalsIgnoreCase("New")) {
            
            String workingDir = System.getProperty("user.dir");
            languageName = "ENGLISH";
            dictonaryStage.setTitle("Bunkar Saathi: [NEW]");
            saveFileMenuItem.setDisable(false); 
            
            objDictionary = new Dictionary();
            objDictionary.setLanguageName(languageName);

            objDictionaryAction=new DictionaryAction(objDictionary);
            if(dictionaryType.equalsIgnoreCase("Alphabet")){
                objDictionaryAction.getAlphabetFile();                
                createAlphabetPanel();
            } else if(dictionaryType.equalsIgnoreCase("Word")){
                objDictionaryAction.getWordFile();            
                createWordPanel();
            }            
        }
        if (text.equalsIgnoreCase("Open")) {
            
            String workingDir = System.getProperty("user.dir");
            languageName = "HINDI";
            dictonaryStage.setTitle("Bunkar Saathi: ["+languageName+"]");
            saveFileMenuItem.setDisable(false); 
            
            objDictionary = new Dictionary();
            objDictionary.setLanguageName(languageName);

            objDictionaryAction=new DictionaryAction(objDictionary);
            if(dictionaryType.equalsIgnoreCase("Alphabet")){
                objDictionaryAction.getAlphabetFile();
                createAlphabetPanel();
            } else if(dictionaryType.equalsIgnoreCase("Word")){
                objDictionaryAction.getWordFile();            
                createWordPanel();
            }            
        }
        if (text.equalsIgnoreCase("Save")) {
            FileChooser saveFileName=new FileChooser();
            saveFileName.setTitle("Bunkar Saathi: Save File As");            
            saveFileName.setInitialDirectory(new File(Paths.get("src").toAbsolutePath().normalize().toString()+"/language/"));
            File chosenOption=saveFileName.showSaveDialog(dictonaryStage);
            if(chosenOption==null)
		return;
            if(chosenOption.getAbsoluteFile().exists()) {
                /*Alert confirmOption = new Alert(Alert.AlertType.CONFIRMATION);
                confirmOption.setContentText("File already exists. Replace existing file ?");
                confirmOption.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
                confirmOption.showAndWait();
                if (confirmOption.getResult() != null && confirmOption.getResult() == ButtonType.YES) {
                    
                }else{
                    return;
                }*/	
            }
            dictonaryStage.setTitle("Bunai: ["+chosenOption.getAbsoluteFile().getName()+"]");            
            saveFile(chosenOption.getAbsoluteFile());		
        }       
        if (text.equalsIgnoreCase("Quit")) {
            dictonaryStage.close();
        }	
    }
        
    public void saveFile(File fileName) {
        try {
            FileWriter f=new FileWriter(fileName);
            //Writer f = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
            String key=null,value=null,msg=null;
            
            for(int i=0;i<GP_container.getChildren().size();i=i+3) {
                Node keyNode = GP_container.getChildren().get(i);
                Node valueNode = GP_container.getChildren().get(i+1);                
                if (keyNode instanceof Label) {
                    key = ((Label)keyNode).getText();
                }
                if (valueNode instanceof TextField) {
                    value = ((TextField)valueNode).getText();
                }
                if(dictionaryType.equalsIgnoreCase("Alphabet")){
                    msg=key+"\t"+value;
                } else if(dictionaryType.equalsIgnoreCase("Word")){                    
                    msg=key+"="+Charset.forName("UTF-8").encode(value);
                }
                f.write(msg+"\n");
            }
            /*Alert infoOption = new Alert(Alert.AlertType.INFORMATION);
            infoOption.setContentText("Keymap generated successfully");
            infoOption.showAndWait();    */    
            System.out.println(fileName+" Map file Written Successfully");
            f.close();
	} catch (IOException e1)  {
            e1.printStackTrace();
	}
    }
    public void saveWFile(File fileName) {
    
        try {
            Properties prop = new Properties();
            Writer f = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
            String key=null,value=null,msg=null;
            
            for(int i=0;i<GP_container.getChildren().size();i=i+3) {
                Node keyNode = GP_container.getChildren().get(i);
                Node valueNode = GP_container.getChildren().get(i+1);                
                if (keyNode instanceof Label) {
                    key = ((Label)keyNode).getText();
                }
                if (valueNode instanceof TextField) {
                    value = ((TextField)valueNode).getText();
                }
                if(dictionaryType.equalsIgnoreCase("Word")){
                    prop.setProperty(key, value);
                }                
            }
             
            prop.store(f, null);
            System.out.println(fileName+" Map file Written Successfully");
            f.close();
	} catch (IOException e1)  {
            e1.printStackTrace();
	}
    }
        
    public void createAlphabetPanel() {
        GP_container.getChildren().clear();
        Set<String> keys = objDictionaryAction.dictionary.keySet();
        int i=0;
        for(String key: keys){
        System.out.println(key+":"+objDictionaryAction.getWord(key));
            Label lbl_key=new Label(key);
                lbl_key.setMinWidth(root.getWidth()/6);
                lbl_key.setTextFill(Color.BLUE);

            Label lbl_unikey=new Label(""+(char)i);
                lbl_unikey.setMinWidth(root.getWidth()/6);
                lbl_unikey.setFont(Font.font("Tahoma", FontWeight.NORMAL, 11));
                lbl_unikey.setTextFill(Color.MAGENTA);

            TextField txt_key=new TextField((String)objDictionaryAction.dictionary.get(key));
                txt_key.setMinWidth(root.getWidth()/6);
                
            char[] the_unicode_char = new char[1];
            try {
                the_unicode_char[0] = (char)Integer.parseInt((String)objDictionaryAction.dictionary.get(key),16);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
            lbl_unikey.setText(new String(the_unicode_char));

            GP_container.add(lbl_key, 1, i+1);
            GP_container.add(txt_key, 2, i+1);
            GP_container.add(lbl_unikey, 3, i+1);
            i++;
        }        	
    }
    
    public void createWordPanel() {
        GP_container.getChildren().clear();
        Set<String> keys = objDictionaryAction.dictionary.keySet();
        int i=0;
        for(String key: keys){
            Label lbl_key=new Label(key);
                lbl_key.setMinWidth(root.getWidth()/3.5);
                lbl_key.setTextFill(Color.BLUE);

            Label lbl_unikey=new Label((String)objDictionaryAction.dictionary.get(key));
                lbl_unikey.setMinWidth(root.getWidth()/3.5);
                lbl_unikey.setFont(Font.font("Tahoma", FontWeight.NORMAL, 11));
                lbl_unikey.setTextFill(Color.MAGENTA);

            TextField txt_key=new TextField((String)objDictionaryAction.dictionary.get(key));
                txt_key.setMinWidth(root.getWidth()/3.5);
                
            GP_container.add(lbl_key, 1, i+1);
            GP_container.add(txt_key, 2, i+1);
            GP_container.add(lbl_unikey, 3, i+1);
            i++;
        }        	
    }
    
      public void start(Stage stage) throws Exception {
        stage.initOwner(FabricView.fabricStage);
        new DictionaryView(stage);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    public static void main(String[] args) {   
      launch(args);    
    }
}
