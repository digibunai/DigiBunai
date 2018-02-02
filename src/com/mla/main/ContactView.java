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
package com.mla.main;

import com.mla.dictionary.DictionaryAction;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 *
 * @Designing accessor and mutator function for fabric
 * @author Amit Kumar Singh
 * 
 */
public class ContactView extends Application {
    private Configuration objConfiguration;
    DictionaryAction objDictionaryAction;
    
 /**
 * ContactView(Stage)
 * <p>
 * This constructor is used for individual call of class. 
 * 
 * @param       Stage primaryStage
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         javafx.stage.*;
 * @link        FabricView
 */
    public ContactView(final Stage primaryStage) {  }

 /**
 * ContactView(Configuration)
 * <p>
 * This class is used for prompting about contact information. 
 * 
 * @param       Configuration objConfigurationCall
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         javafx.scene.control.*;
 * @link        Configuration
 */
    public ContactView(Configuration objConfigurationCall) { 
        this.objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);
        Stage contactStage = new Stage();
        contactStage.initStyle(StageStyle.UTILITY);

        GridPane container = new GridPane();
        
        Label caption = new Label(objDictionaryAction.getWord("CONTACTUS"));
        caption.setId("caption");
        GridPane.setConstraints(caption, 0, 0);
        GridPane.setColumnSpan(caption, 4);
        container.getChildren().add(caption);
 
        final Separator sepHor1 = new Separator();
        sepHor1.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor1, 0, 1);
        GridPane.setColumnSpan(sepHor1, 4);
        container.getChildren().add(sepHor1);
        
        final Separator sepVert1 = new Separator();
        sepVert1.setOrientation(Orientation.VERTICAL);
        sepVert1.setValignment(VPos.CENTER);
        sepVert1.setPrefHeight(80);
        GridPane.setConstraints(sepVert1, 1, 2);
        GridPane.setRowSpan(sepVert1, 4);
        container.getChildren().add(sepVert1);
 
        Label map = new Label(objDictionaryAction.getWord("MAP"));
        map.setId("label");
        map.setGraphic(new ImageView(objConfiguration.getStrColour()+"/settings.png"));
        container.add(map, 0, 2);
        
        WebView mapWV = new WebView();
        mapWV.setPrefSize(objConfiguration.HEIGHT/4,objConfiguration.WIDTH/4);
        mapWV.setCursor(Cursor.CLOSED_HAND);
        mapWV.getEngine().load("https://www.google.co.in/maps/place/Media+Lab+Asia/@28.5472992,77.2501071,16z/data=!4m5!3m4!1s0x390ce3cfc1317033:0x6a15fe09f9b2f99f!8m2!3d28.5473039!4d77.2502573"); 
        GridPane.setConstraints(mapWV, 0, 3);
        GridPane.setRowSpan(mapWV, 3);
        container.getChildren().add(mapWV);
 
        Label email = new Label(objDictionaryAction.getWord("EMAILID"));
        email.setId("label");
        email.setGraphic(new ImageView(objConfiguration.getStrColour()+"/about_software.png"));
        container.add(email, 2, 2);
        
        Label contact = new Label(objDictionaryAction.getWord("CONTACTNUMBER")+"\n\n");
        contact.setId("label");
        contact.setGraphic(new ImageView(objConfiguration.getStrColour()+"/contact_us.png"));
        contact.setAlignment(Pos.TOP_LEFT);
        container.add(contact, 2, 3);
        
        Label address = new Label(objDictionaryAction.getWord("ADDRESS")+"\n\n\n\n\n.");
        address.setId("label");
        address.setGraphic(new ImageView(objConfiguration.getStrColour()+"/profile.png"));
        address.setAlignment(Pos.TOP_LEFT);
        container.add(address, 2, 4);
        
        Label web = new Label(objDictionaryAction.getWord("WEBURL"));
        web.setId("label");
        web.setGraphic(new ImageView(objConfiguration.getStrColour()+"/orientation.png"));
        container.add(web, 2, 5);
        
        container.add(new Label("cadsupport@medialabasia.in"), 3, 2);
        container.add(new Label("011 - 2644 3266 \n 011 - 2628 8192"), 3, 3);
        container.add(new Label(objDictionaryAction.getWord("PROJECT")+" Team, \n Media Lab Asia, \n 708-723, 7th Floor Devika Tower, \n 6 Nehru Place, \n New Delhi - 110019, \n India."), 3, 4);
        container.add(new Hyperlink("http://medialabasia.in"), 3, 5);
        
        Hyperlink link = new Hyperlink("http://www.medialabasia.in");
        link.setText(objDictionaryAction.getWord("MEDIALABASIA"));
        link.setFont(Font.font("Arial", 14));        
        
        contactStage.setScene(new Scene(VBoxBuilder.create().children(container).alignment(Pos.CENTER_LEFT).spacing(5).cursor(Cursor.CROSSHAIR).padding(new Insets(5)).build()));
        contactStage.getIcons().add(new Image("/media/icon.png"));
        contactStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWCONTACTUS")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        contactStage.showAndWait();
    }
     
    public void start(Stage stage) throws Exception {
        stage.initOwner(WindowView.windowStage);
        new ContactView(stage);        
        new Logging("WARNING",ContactView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void main(String[] args) {   
      launch(args);    
    }  
}