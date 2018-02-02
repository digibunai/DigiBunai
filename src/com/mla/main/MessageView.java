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
import com.mla.secure.Security;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
/**
 *
 * @Designing GUI window for message popup
 * @author Amit Kumar Singh
 * 
 */
public class MessageView extends Application {
    
    Logging objLogging = null;
    
 /**
 * MessageView(Stage)
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
    public MessageView(final Stage primaryStage) {  }
/**
 * MessageView(String,String,String)
 * <p>
 * This class is used for prompting about message information. 
 * 
 * @param       String type
 * @param       String subject
 * @param       String message
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         javafx.scene.control.*;
 * @link        Configuration
 */
    public MessageView(String type, String subject, final String message) {  
        Stage messageStage = new Stage();
        messageStage.initModality(Modality.WINDOW_MODAL);
        messageStage.setWidth(222);
        messageStage.setHeight(111); 
        messageStage.getIcons().add(new Image("/media/icon.png"));
        switch(type){
            case "error" :  messageStage.setTitle("Error : "+subject);
                            messageStage.getIcons().add(new Image("/media/man_stop.jpg"));
                            break;
            case "warning": messageStage.setTitle("Warning : "+subject);
                            messageStage.getIcons().add(new Image("/media/man_stop.jpg"));
                            break;
            case "alert" :  messageStage.setTitle("Alert : "+subject);
                            messageStage.getIcons().add(new Image("/media/man_stop.jpg"));
                            break;
            case "apology" :  messageStage.setTitle("Alert : "+subject);
                            messageStage.getIcons().add(new Image("/media/man_stop.jpg"));
                            break;
            default:        messageStage.setTitle("Notification : "+subject);
                            messageStage.getIcons().add(new Image("/media/man_stop.jpg"));
                            break;
        }
        Scene scene = new Scene(new Group());        
        VBox vbox = new VBox();
        vbox.setLayoutX(20);
        vbox.setLayoutY(20);
        final Text text = new Text(10, 20, "");    
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(2000));
            }
            protected void interpolate(double frac) {
                final int length = message.length();
                final int n = Math.round(length * (float) frac);
                text.setText(message.substring(0, n));
            }
        };
        animation.play();
        vbox.getChildren().add(text);
        vbox.setSpacing(10);
        ((Group) scene.getRoot()).getChildren().add(vbox);
        messageStage.setScene(scene);
        messageStage.showAndWait();
    }
/**
 * MessageView(Configuration)
 * <p>
 * This class is used for prompting about message information. 
 * 
 * @param       Configuration objConfiguration
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         javafx.scene.control.*;
 * @link        Configuration
 */
    public MessageView(final Configuration objConfiguration){
        if(objConfiguration.getBlnAuthenticateService() && !objConfiguration.getServicePasswordValid()){
            final DictionaryAction objDictionaryAction = new DictionaryAction(objConfiguration);
            System.gc();
            final Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            dialogStage.setIconified(false);
            dialogStage.setFullScreen(false);
            dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ALERT"));
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 300, 200, Color.WHITE);
            scene.getStylesheets().add(MessageView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
            final GridPane popup=new GridPane();
            popup.setId("popup");
            popup.setHgap(5);
            popup.setVgap(5);
            popup.setPadding(new Insets(5, 5, 5, 5));
            Label lblPassword=new Label(objDictionaryAction.getWord("SERVICEPASSWORD"));
            lblPassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
            popup.add(lblPassword, 0, 0, 2, 1);
            final PasswordField passPF =new PasswordField();
            passPF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
            passPF.setPromptText(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
            popup.add(passPF, 0, 1, 2, 1);
            final Label lblAlert = new Label(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
            lblAlert.setStyle("-fx-wrap-text:true;");
            lblAlert.setPrefWidth(250);
            popup.add(lblAlert, 0, 3, 2, 1);
            Button btnYes = new Button(objDictionaryAction.getWord("OK"));
            btnYes.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOK")));
            btnYes.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
            btnYes.setDefaultButton(true);
            btnYes.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    if(Security.SecurePassword(passPF.getText(), objConfiguration.getObjUser().getStrUsername()).equals(objConfiguration.getObjUser().getStrAppPassword())){
                        dialogStage.close();
                        System.gc();        
                        objConfiguration.setServicePasswordValid(true);
                    }
                    else if(passPF.getText().length()==0){
                        lblAlert.setText(objDictionaryAction.getWord("NOSERVICEPASSWORD"));
                    }
                    else{
                        lblAlert.setText(objDictionaryAction.getWord("INVALIDSERVICEPASSWORD"));
                    }
                }
            });
            popup.add(btnYes, 0, 2, 1, 1);
            Button btnNo = new Button(objDictionaryAction.getWord("CANCEL"));
            btnNo.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
            btnNo.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
            btnNo.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    dialogStage.close();
                    System.gc();
                }
            });
            popup.add(btnNo, 1, 2, 1, 1);
            root.setCenter(popup);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();  
            System.gc();
        }else{
            objConfiguration.setServicePasswordValid(true);
        }
    }

    public void start(Stage stage) throws Exception {
        stage.initOwner(WindowView.windowStage);
        new MessageView(stage); 
        new Logging("WARNING",MessageView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    public static void main(String[] args) {   
      launch(args);    
    }            
}

