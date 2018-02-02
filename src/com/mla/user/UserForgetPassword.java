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
package com.mla.user;

import com.mla.dictionary.DictionaryAction;
import com.mla.fabric.FabricView;
import com.mla.main.AboutView;
import com.mla.main.Configuration;
import com.mla.main.ContactView;
import com.mla.main.HelpView;
import com.mla.main.IDGenerator;
import com.mla.main.Logging;
import com.mla.main.TechnicalView;
import com.mla.main.WindowView;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage; 
/**
 *
 * @Designing GUI Window for user
 * @author Aatif Ahmad Khan
 * 
 */
public class UserForgetPassword extends Application {

    public static Stage userStage;
    private TextField txtUsername;
    private TextField txtEmail;
    User objUser = null;
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    FabricView objFabricView = null;
    WindowView objWindowView = null;
    private Text lblStatus;
    
  public UserForgetPassword(final Stage primaryStage) { }
  
  public UserForgetPassword(Configuration objConfigurationCall) {
    userStage = new Stage();
    BorderPane root = new BorderPane();
    Scene scene = new Scene(root, 700, 500, Color.WHITE);
    scene.getStylesheets().add(UserForgetPassword.class.getResource("/media/login.css").toExternalForm());
    
    objUser = new User();
    objConfiguration = objConfigurationCall;
    objDictionaryAction = new DictionaryAction(objConfiguration);
        
    GridPane grid = new GridPane();   
    grid.setId("grid");

    HBox hbox = new HBox();
    Text scenetitle = new Text(objDictionaryAction.getWord("WELCOME"));
    scenetitle.setFont(Font.font(objConfiguration.getStrBFont(), FontWeight.NORMAL, objConfiguration.getIntBFontSize()));
    scenetitle.setId("welcome-text");
    ImageView sceneicon = new ImageView("/media/icon.png");
    sceneicon.setFitHeight(32);
    sceneicon.setFitWidth(32);
    hbox.getChildren().addAll(sceneicon,scenetitle);
    grid.add(hbox, 0, 0, 2, 1);

    Label lblUsername = new Label(objDictionaryAction.getWord("USERNAME")+ ":");
    lblUsername.setId("username");
    grid.add(lblUsername, 0, 1);

    txtUsername = new TextField();
    txtUsername.setPromptText(objDictionaryAction.getWord("PROMPTUSERNAME"));
    final Tooltip usernameTT = new Tooltip();
    usernameTT.setText(objDictionaryAction.getWord("TOOLTIPUSERNAME"));
    usernameTT.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
    txtUsername.setTooltip(usernameTT);
    grid.add(txtUsername, 1, 1);

    Label lblEmail = new Label(objDictionaryAction.getWord("EMAIL")+":");
    lblEmail.setId("email");
    grid.add(lblEmail, 0, 2);

    txtEmail = new TextField();
    txtEmail.setPromptText(objDictionaryAction.getWord("PROMPTEMAIL"));
    final Tooltip emailTT = new Tooltip();
    emailTT.setText(objDictionaryAction.getWord("TOOLTIPEMAIL"));
    emailTT.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
    txtEmail.setTooltip(emailTT);
    grid.add(txtEmail, 1, 2);
    
    Button btnSubmit = new Button(objDictionaryAction.getWord("SUBMIT"));
    btnSubmit.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
    btnSubmit.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSUBMIT")));
    btnSubmit.setDefaultButton(true);
    btnSubmit.setFocusTraversable(true);
    Button btnCancel=new Button(objDictionaryAction.getWord("BACK"));
    btnCancel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
    btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));

    HBox P_buttons = new HBox(10);
    P_buttons.setAlignment(Pos.BOTTOM_RIGHT);
    P_buttons.getChildren().addAll(btnCancel,btnSubmit);
    grid.add(P_buttons, 0, 3, 2, 1);

    lblStatus = new Text(objDictionaryAction.getWord("FORGETPASSWORD"));
    grid.add(lblStatus, 0, 5, 2, 1);
    lblStatus.setId("actiontarget");  
    
    btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
         @Override
        public void handle(ActionEvent e) {
             try {
                lblStatus.setFill(Color.FIREBRICK);
                if(txtUsername.getText().length()==0 || txtEmail.getText().length()==0){    // empty username or email field
                        lblStatus.setText(objDictionaryAction.getWord("NOFORGETPASSWORD"));
                } else{
                    UserAction objUserAction=new UserAction();
                    // match username & email from "mla_users" database table, returns true when validated else false
                    boolean found=objUserAction.verifyUsernameEmail(txtUsername.getText(), txtEmail.getText());
                    // checking if email is sent successfully
                    if(found){
                        boolean sent=new com.mla.main.Email().send(txtEmail.getText(), "Password Reset Steps", "password");
                        if(sent){
                            lblStatus.setText(objDictionaryAction.getWord("VALIDFORGETPASSWORD")+txtEmail.getText());
                        } else{
                            lblStatus.setText(objDictionaryAction.getWord("ERRORFORGETPASSWORD"));
                        }
                    } else{
                        lblStatus.setText(objDictionaryAction.getWord("INVALIDFORGETPASSWORD"));    // if username & email are not matching
                    }
                }
            } catch (Exception ex) {
                new Logging("SEVERE",UserView.class.getName(),"ForgotPassword -> matchUsernameAndEmail(): error validating data from database",ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
    });
    btnCancel.setOnAction(new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            userStage.close();
            System.gc();
            UserView objUserView = new UserView(objConfiguration);
        }
    });
    
    final ImageView helpIV = new ImageView(objConfiguration.getStrColour()+"/help.png");
    helpIV.setOnMouseEntered(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            helpIV.setImage(new Image(objConfiguration.getStrColourDimmed()+"/help.png"));
            helpIV.setScaleX(1.5);
            helpIV.setScaleY(1.5);
            helpIV.setScaleZ(1.5);
        }
    });
    helpIV.setOnMouseExited(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            helpIV.setImage(new Image(objConfiguration.getStrColour()+"/help.png"));
            helpIV.setScaleX(1);
            helpIV.setScaleY(1);
            helpIV.setScaleZ(1);
        }
    });
    helpIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            HelpView objHelpView = new HelpView(objConfiguration);
        }
    });
    final ImageView technicalIV = new ImageView(objConfiguration.getStrColour()+"/technical_info.png");
    technicalIV.setOnMouseEntered(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            technicalIV.setImage(new Image(objConfiguration.getStrColourDimmed()+"/technical_info.png"));
            technicalIV.setScaleX(1.5);
            technicalIV.setScaleY(1.5);
            technicalIV.setScaleZ(1.5);
        }
    });
    technicalIV.setOnMouseExited(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            technicalIV.setImage(new Image(objConfiguration.getStrColour()+"/technical_info.png"));
            technicalIV.setScaleX(1);
            technicalIV.setScaleY(1);
            technicalIV.setScaleZ(1);
        }
    });
    technicalIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            TechnicalView objTechnicalView = new TechnicalView(objConfiguration);
        }
    });
    final ImageView aboutIV = new ImageView(objConfiguration.getStrColour()+"/about_software.png");
    aboutIV.setOnMouseEntered(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            aboutIV.setImage(new Image(objConfiguration.getStrColourDimmed()+"/about_software.png"));
            aboutIV.setScaleX(1.5);
            aboutIV.setScaleY(1.5);
            aboutIV.setScaleZ(1.5);
        }
    });
    aboutIV.setOnMouseExited(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            aboutIV.setImage(new Image(objConfiguration.getStrColour()+"/about_software.png"));
            aboutIV.setScaleX(1);
            aboutIV.setScaleY(1);
            aboutIV.setScaleZ(1);
        }
    });
    aboutIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            AboutView objAboutView = new AboutView(objConfiguration);
        }
    });
    final ImageView contactIV = new ImageView(objConfiguration.getStrColour()+"/contact_us.png");
    contactIV.setOnMouseEntered(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            contactIV.setImage(new Image(objConfiguration.getStrColourDimmed()+"/contact_us.png"));
            contactIV.setScaleX(1.5);
            contactIV.setScaleY(1.5);
            contactIV.setScaleZ(1.5);
        }
    });
    contactIV.setOnMouseExited(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            contactIV.setImage(new Image(objConfiguration.getStrColour()+"/contact_us.png"));
            contactIV.setScaleX(1);
            contactIV.setScaleY(1);
            contactIV.setScaleZ(1);
        }
    });
    contactIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            ContactView objContactView = new ContactView(objConfiguration);
        }
    });

    HBox P_links = new HBox(10);
    P_links.setAlignment(Pos.BOTTOM_CENTER);
    P_links.getChildren().addAll(helpIV, technicalIV, aboutIV, contactIV);
    grid.add(P_links, 0, 8, 2, 1);

    root.setCenter(grid);
    userStage.getIcons().add(new Image("/media/icon.png"));
    userStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWLOGIN")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
    userStage.setResizable(false);
    userStage.setScene(scene);
    userStage.showAndWait();
  }
  
   @Override
   public void start(Stage stage) throws Exception {
        new UserForgetPassword(stage); 
        new Logging("WARNING",UserForgetPassword.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
  public static void main(String[] args) {   
      launch(args);    
  }
  
}