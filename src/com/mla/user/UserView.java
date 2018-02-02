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
import com.mla.main.Logging;
import com.mla.main.TechnicalView;
import com.mla.main.WindowView;
import java.sql.SQLException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.scene.Cursor;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.WindowEvent;
/**
 *
 * @Designing GUI Window for user
 * @author Amit Kumar Singh
 * 
 */
public class UserView extends Application {

    public static Stage userStage;
    private TextField txtUsername;
    private PasswordField txtPassword;
    User objUser = null;
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    FabricView objFabricView = null;
    WindowView objWindowView = null;
    private Text lblStatus;
    
  public UserView(final Stage primaryStage) { }
  
  public UserView(Configuration objConfigurationCall) {
    objUser = new User();
    objConfiguration = objConfigurationCall;
    objDictionaryAction = new DictionaryAction(objConfiguration);
    
    userStage = new Stage();
    BorderPane root = new BorderPane();
    Scene scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
    scene.getStylesheets().add(UserView.class.getResource("/media/login.css").toExternalForm());
    root.setId("borderpane");
    
    GridPane grid = new GridPane();   
    grid.setId("grid");
    grid.getColumnConstraints().add(new ColumnConstraints(100)); // column 1 is 100 wide
    grid.getColumnConstraints().add(new ColumnConstraints(250)); // column 2 is 200 wide
    grid.setAlignment(Pos.TOP_LEFT);
    grid.setMaxSize(350, 200);
    grid.relocate(10, 10);
    //grid.setLayoutX(30);
    //grid.setLayoutY(10);
        
    Text scenetitle = new Text(objDictionaryAction.getWord("WELCOME"));
    scenetitle.setFont(Font.font(objConfiguration.getStrBFont(), FontWeight.NORMAL, objConfiguration.getIntBFontSize()));
    scenetitle.setId("welcome-text");
    grid.add(scenetitle, 0, 0, 2, 1);

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

    Label lblPassword = new Label(objDictionaryAction.getWord("PASSWORD")+":");
    lblPassword.setId("password");
    grid.add(lblPassword, 0, 2);

    txtPassword = new PasswordField();
    txtPassword.setPromptText(objDictionaryAction.getWord("PROMPTPASSWORD"));
    final PasswordField pf = new PasswordField();
    final Tooltip passwordTT = new Tooltip();
    passwordTT.setText(objDictionaryAction.getWord("TOOLTIPPASSWORD"));
    passwordTT.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
    txtPassword.setTooltip(passwordTT);
    grid.add(txtPassword, 1, 2);
    
    Button btnLogin = new Button(objDictionaryAction.getWord("LOGIN"));
    btnLogin.setGraphic(new ImageView(objConfiguration.getStrColour()+"/login.png"));
    btnLogin.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPLOGIN")));
    btnLogin.setDefaultButton(true);
    btnLogin.setFocusTraversable(true);
    
    /**
     * Description: Adds "Register" button on Login Screen (UserView). When it is pressed User Registration stage pops up.
     * Date Added: 4 Feb 2017
     * Author: Aatif Ahmad Khan
     */
    Button btnRegister = new Button(objDictionaryAction.getWord("REGISTRATION"));
    btnRegister.setGraphic(new ImageView(objConfiguration.getStrColour()+"/profile.png"));
    btnRegister.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPREGISTRATION")));
    /**
     * Description:
     *  Adds "Forgot Password" button on Login Screen (UserView). When it is pressed Forgot Password Stage pops up.
     *  User is asked to enter "username" & "registered email".
     *  On clicking "Retrieve Password" button, these two fields are validated from database table "mla_users".
     *  After validation, an email containing Password Reset Steps is sent to registered email address using "send()" function in "com.mla.utility.Email" class.
     * Date Added: 8 Feb 2017
     * Author: Aatif Ahmad Khan
     */
    Button btnPassword = new Button(objDictionaryAction.getWord("FORGETPASSWORD"));
    btnPassword.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
    btnPassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFORGETPASSWORD")));
    
    HBox P_buttons = new HBox(10);
    P_buttons.setAlignment(Pos.BOTTOM_RIGHT);
    P_buttons.getChildren().addAll(btnLogin,btnRegister,btnPassword);
    grid.add(P_buttons, 0, 3, 2, 1);

    lblStatus = new Text();
    grid.add(lblStatus, 0, 5, 2, 1);
    lblStatus.setId("actiontarget");      
    
    btnLogin.setOnAction(new EventHandler<ActionEvent>() {
         @Override
        public void handle(ActionEvent e) {
             try {
                 lblStatus.setFill(Color.FIREBRICK);
                 lblStatus.setText(objDictionaryAction.getWord("ACTIONLOGIN"));
                 String a=txtUsername.getText();
                 objUser.setStrUsername(a);
                 String b=txtPassword.getText();
                 //objUser.setStrPassword(Security.SecurePassword(b,a));
                 objUser.setStrPassword(b);
                 UserAction objUserAction= new UserAction();
                 if(objUserAction.verifyLogin(objUser)){
                     objUserAction= new UserAction();
                     if(!objUserAction.isUserLicenseIdLatest(objUser.getStrUserID())){
                         userStage.close();
                         updateUserLicenseTerms();
                     }else{
                        //objUser.setStrSeesionID(new IDGenerator().getIDGenerator("USER_TRACKING", objUser.getStrUserID()));
                        //objUserAction= new UserAction();
                        //objUserAction.trackUser(objUser, "LOGIN", "Log-In done", Security.getUserTrackDetails());
                        objConfiguration.setStrClothType("Body");
                        objUserAction= new UserAction();
                        objConfiguration.setObjUser(objUser);                         
                        objUserAction.getConfiguration(objConfiguration);
                        objConfiguration.clothRepeat();
                        System.gc();
                        userStage.close();
                        objWindowView = new WindowView(objConfiguration);
                     }
                 }else{
                     lblStatus.setText(objDictionaryAction.getWord("INCORRELOGIN")+" , "+objDictionaryAction.getWord("TRYAGAIN"));
                 }
             } catch (SQLException ex) {
                 new Logging("SEVERE",UserView.class.getName(),"Error in login"+ex.toString(),ex);
                 lblStatus.setText(objDictionaryAction.getWord("ERROR"));
             } catch (Exception ex) {
                 new Logging("SEVERE",UserView.class.getName(),ex.toString(),ex);
                 lblStatus.setText(objDictionaryAction.getWord("ERROR"));
             }
        }
    });
   
    btnRegister.setOnAction(new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            userStage.close();
            System.gc();
            UserRegistration objUserRegistration = new UserRegistration(objConfiguration);
        }
    });
    
    btnPassword.setOnAction(new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            userStage.close();
            System.gc();
            UserForgetPassword objUserForgetPassword = new UserForgetPassword(objConfiguration);
        }
    });
    
    final ImageView helpIV = new ImageView(objConfiguration.getStrColour()+"/help.png");
    Tooltip.install(helpIV, new Tooltip(objDictionaryAction.getWord("TOOLTIPHELP")));
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
    Tooltip.install(technicalIV, new Tooltip(objDictionaryAction.getWord("TOOLTIPTECHNICAL")));
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
    Tooltip.install(aboutIV, new Tooltip(objDictionaryAction.getWord("TOOLTIPABOUTUS")));
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
    Tooltip.install(contactIV, new Tooltip(objDictionaryAction.getWord("TOOLTIPCONTACTUS")));
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
    P_links.setAlignment(Pos.BOTTOM_RIGHT);
    P_links.getChildren().addAll(helpIV, technicalIV, aboutIV, contactIV);
    //grid.add(P_links, 0, 7, 2, 1);

    Label info = new Label(objDictionaryAction.getWord("VERSIONINFO"));
    info.setId("version-text");
    P_links.setLayoutX(100);
    P_links.setLayoutY(200);
    VBox vbox = new VBox();
    
    vbox.setAlignment(Pos.BOTTOM_RIGHT);
    
    vbox.getChildren().addAll(P_links,info);
    //grid.add(vbox, 0, 8, 2, 1);
    //grid.setMargin(chip5, new Insets(5, 0, 0, 0));
    
    vbox.setStyle("-fx-padding: 0 30 30 0;");
    
    root.setStyle("-fx-padding: 262 0 0 111;");
    root.setLeft(grid);
    root.setBottom(vbox);
    userStage.getIcons().add(new Image("/media/icon.png"));
    userStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWLOGIN")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
    userStage.setResizable(false);
    userStage.setScene(scene);
    userStage.showAndWait();
    userStage.setX(-5);
    userStage.setY(0);
  }
  
    private void updateUserLicenseTerms(){
        final Stage primaryStage=new Stage();
        primaryStage.setTitle(objDictionaryAction.getWord("LICENSEAGREEMENTTITLE"));
        GridPane grid=new GridPane();
        grid.setVgap(10);
        grid.setHgap(5);
        Label caption=new Label(objDictionaryAction.getWord("LICENSEAGREEMENTCAPTION"));
        grid.add(caption, 0, 0, 2, 1);
        WebView license=new WebView();
        license.setPrefSize(600, 440);
        license.setCursor(Cursor.DEFAULT);
        //TextArea license=new TextArea();
        //license.setPrefColumnCount(50);
        //license.setPrefRowCount(15);
        //license.setWrapText(true);
        //license.setEditable(false);
        try{
            UserAction objUserAction=new UserAction();
            license.getEngine().loadContent(new Scanner(objUserAction.getLatestLicenseContent()).useDelimiter("\\Z").next());
            //license.getEngine().load("file:///"+objUserAction.getLatestLicenseContent().getAbsolutePath());
            //latestTnCId=objUserAction.getLatestLicenseId();
        }
        catch(SQLException ex){
            new Logging("ERROR", UserView.class.getName(), "Error fetching database data"+ex.getMessage(), ex);
        } catch (FileNotFoundException ex) {
            new Logging("ERROR", UserView.class.getName(), "Error fetching database data"+ex.getMessage(), ex);
        }
        
        grid.add(license, 0, 1, 2, 1);
        final CheckBox chkAccept=new CheckBox(objDictionaryAction.getWord("AGREELICENSETERMS"));
        grid.add(chkAccept, 0, 2);
        final Label message=new Label();
        grid.add(message, 0, 4, 2, 1);
        
        Button btnAccept=new Button(objDictionaryAction.getWord("ACCEPT"));
        btnAccept.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(chkAccept.isSelected()){
                    try {
                        UserAction objUserAction = new UserAction();
                        objUserAction.addUserTnC(objUser.getStrUserID());
                        //objUser.setStrSeesionID(new IDGenerator().getIDGenerator("USER_TRACKING", objUser.getStrUserID()));
                        //objUserAction= new UserAction();
                        //objUserAction.trackUser(objUser, "LOGIN", "Log-In done", Security.getUserTrackDetails());
                        objConfiguration.setStrClothType("Body");
                        objUserAction= new UserAction();
                        objConfiguration.setObjUser(objUser);                         
                        objUserAction.getConfiguration(objConfiguration);
                        objConfiguration.clothRepeat();
                        System.gc();
                        objWindowView = new WindowView(objConfiguration);
                        primaryStage.close();
                    } catch (SQLException ex) {
                        new Logging("SEVERE",UserProfileView.class.getName(),"Logout Tracking",ex);
                    }
                }
                else{
                    message.setText(objDictionaryAction.getWord("ACCEPTLICENSEAGREEMENT"));
                }
            }
        });
        grid.add(btnAccept, 0, 3);
        
        Button btnCancel=new Button(objDictionaryAction.getWord("SKIP"));
        btnCancel.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    UserAction objUserAction= new UserAction();
                    objUserAction.trackUser(objConfiguration.getObjUser(), "LOGOUT", "Log-Out done", null);                    
                    objConfiguration.setObjUser(null);
                } catch (SQLException ex) {
                    new Logging("SEVERE",UserView.class.getName(),"Logout Tracking",ex);
                }
                primaryStage.close();
                System.gc();
                UserView objUserView=new UserView(objConfiguration);   
            }
        });
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                try {
                    UserAction objUserAction= new UserAction();
                    objUserAction.trackUser(objConfiguration.getObjUser(), "LOGOUT", "Log-Out done", null);                    
                    objConfiguration.setObjUser(null);
                } catch (SQLException ex) {
                    new Logging("SEVERE",UserView.class.getName(),"Logout Tracking",ex);
                }
                primaryStage.close();
                System.gc();
                UserView objUserView=new UserView(objConfiguration);   
            }
        });
        
        grid.add(btnCancel, 1, 3);
        BorderPane root=new BorderPane();
        root.setCenter(grid);
        Scene scene=new Scene(root, 500, 400, Color.WHITE);
        scene.getStylesheets().add(UserView.class.getResource("/media/login.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }
  
  @Override
   public void start(Stage stage) throws Exception {
        new UserView(stage); 
        new Logging("WARNING",UserView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
 /*
  public static void main(String[] args) {   
      launch(args);    
  }
  */ 
}