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
import com.mla.main.AboutView;
import com.mla.main.Configuration;
import com.mla.main.ContactView;
import com.mla.main.HelpView;
import com.mla.main.Logging;
import com.mla.main.MessageView;
import com.mla.main.TechnicalView;
import com.mla.main.WindowView;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//import javafx.scene.control.DatePicker;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
/**
 *
 * @Designing GUI window for user preferences
 * @author Amit Kumar Singh
 * 
 */
public class UserProfileView extends Application {
    public static Stage userProfileStage;
    BorderPane root;
    
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    
    private Menu homeMenu;
    private Menu helpMenu;
    
    private ComboBox<Label> countryCB= new ComboBox<Label>();
    private ComboBox<Label> stateCB= new ComboBox<Label>();
    private ComboBox<Label> cityCB= new ComboBox<Label>();
        
    List lstCountry;
    List lstState;
    List lstCity;
    
 // indices of selected city, state & country in cityCB, stateCB & countryCB respectively
    int intcountry=0;
    int intstate=0;
    int intcity=0;
	
    public UserProfileView(final Stage primaryStage) {}
    
    public UserProfileView(Configuration objConfigurationCall) {
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);

        userProfileStage = new Stage();
        root = new BorderPane();
        Scene scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(UserProfileView.class.getResource(objConfiguration.getStrTemplate()+"/setting.css").toExternalForm());

        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(userProfileStage.widthProperty());

        homeMenu  = new Menu();
        final HBox homeMenuHB = new HBox();
        homeMenuHB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/home.png"),new Label(objDictionaryAction.getWord("HOME")));
        homeMenu.setGraphic(homeMenuHB);
        homeMenuHB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                final Stage dialogStage = new Stage();
                dialogStage.initStyle(StageStyle.UTILITY);
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.setResizable(false);
                dialogStage.setIconified(false);
                dialogStage.setFullScreen(false);
                dialogStage.setTitle(objDictionaryAction.getWord("ALERT"));
                BorderPane root = new BorderPane();
                Scene scene = new Scene(root, 300, 100, Color.WHITE);
                scene.getStylesheets().add(UserProfileView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                final GridPane popup=new GridPane();
                popup.setId("popup");
                popup.setHgap(5);
                popup.setVgap(5);
                popup.setPadding(new Insets(25, 25, 25, 25));
                popup.add(new ImageView(objConfiguration.getStrColour()+"/stop.png"), 0, 0);
                Label lblAlert = new Label(objDictionaryAction.getWord("ALERTCLOSE"));
                lblAlert.setStyle("-fx-wrap-text:true;");
                lblAlert.setPrefWidth(250);
                popup.add(lblAlert, 1, 0);
                Button btnYes = new Button(objDictionaryAction.getWord("YES"));
                btnYes.setPrefWidth(50);
                btnYes.setId("btnYes");
                btnYes.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        dialogStage.close();
                        userProfileStage.close();
                        System.gc();
                        WindowView objWindowView = new WindowView(objConfiguration);
                    }
                });
                popup.add(btnYes, 0, 1);
                Button btnNo = new Button(objDictionaryAction.getWord("NO"));
                btnNo.setPrefWidth(50);
                btnNo.setId("btnNo");
                btnNo.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        dialogStage.close();
                        System.gc();
                    }
                });
                popup.add(btnNo, 1, 1);
                root.setCenter(popup);
                dialogStage.setScene(scene);
                dialogStage.showAndWait();
                me.consume();
            }
        });
        Label helpMenuLabel = new Label(objDictionaryAction.getWord("SUPPORT"));
        helpMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSUPPORT")));
        Menu helpMenu = new Menu();
        helpMenu.setGraphic(helpMenuLabel);
        MenuItem helpMenuItem = new MenuItem(objDictionaryAction.getWord("HELP"));
        helpMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        helpMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                HelpView objHelpView = new HelpView(objConfiguration);
            }
        });
        MenuItem technicalMenuItem = new MenuItem(objDictionaryAction.getWord("TECHNICAL"));
        technicalMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/technical_info.png"));
        technicalMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                TechnicalView objTechnicalView = new TechnicalView(objConfiguration);
            }
        });
        MenuItem aboutMenuItem = new MenuItem(objDictionaryAction.getWord("ABOUTUS"));
        aboutMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/about_software.png"));
        aboutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                AboutView objAboutView = new AboutView(objConfiguration);
            }
        });
        MenuItem contactMenuItem = new MenuItem(objDictionaryAction.getWord("CONTACTUS"));
        contactMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/contact_us.png"));
        contactMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ContactView objContactView = new ContactView(objConfiguration);
            }
        });
        MenuItem exitMenuItem = new MenuItem(objDictionaryAction.getWord("EXIT"));
        exitMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/quit.png"));
        exitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.gc();
                userProfileStage.close();
            }
        });
        helpMenu.getItems().addAll(helpMenuItem, technicalMenuItem, aboutMenuItem, contactMenuItem, new SeparatorMenuItem(), exitMenuItem);
        menuBar.getMenus().addAll(homeMenu, helpMenu);
        root.setTop(menuBar);

        ScrollPane mycon = new ScrollPane();
        GridPane container = new GridPane();
        container.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        container.setId("container");
        mycon.setContent(container);
        root.setCenter(mycon);

        Label caption = new Label(objDictionaryAction.getWord("USER")+" "+objDictionaryAction.getWord("DEMOGRAPHIC"));
        caption.setId("caption");
        GridPane.setConstraints(caption, 0, 0);
        GridPane.setColumnSpan(caption, 5);
        container.getChildren().add(caption);

        final Separator sepHor1 = new Separator();
        sepHor1.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor1, 0, 1);
        GridPane.setColumnSpan(sepHor1, 5);
        container.getChildren().add(sepHor1);

        final Separator sepVert1 = new Separator();
        sepVert1.setOrientation(Orientation.VERTICAL);
        sepVert1.setValignment(VPos.CENTER);
        sepVert1.setPrefHeight(80);
        GridPane.setConstraints(sepVert1, 2, 2);
        GridPane.setRowSpan(sepVert1, 13);
        container.getChildren().add(sepVert1);

        final Separator sepHor2 = new Separator();
        sepHor2.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor2, 0, 15);
        GridPane.setColumnSpan(sepHor2, 5);
        container.getChildren().add(sepHor2);
        
        Label name= new Label(objDictionaryAction.getWord("NAME")+" :");
        //name.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(name, 0, 2);
        final TextField nameTF = new TextField();
        nameTF.setPromptText(objDictionaryAction.getWord("PROMPTNAME"));
        nameTF.setText(objConfiguration.getObjUser().getStrName());
        nameTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNAME")));
        container.add(nameTF, 1, 2);

        Label email= new Label(objDictionaryAction.getWord("EMAILID")+" :");
        //email.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(email, 0, 3);
        final TextField emailTF = new TextField();
        emailTF.setPromptText(objDictionaryAction.getWord("PROMPTEMAILID"));
        emailTF.setText(objConfiguration.getObjUser().getStrEmail());
        emailTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEMAILID")));
        container.add(emailTF, 1, 3);

        Label contact= new Label(objDictionaryAction.getWord("CONTACTNUMBER")+" :");
        //contact.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(contact, 0, 4);
        final TextField contactTF = new TextField();
        contactTF.setPromptText(objDictionaryAction.getWord("PROMPTCONTACTNUMBER"));
        contactTF.setText(objConfiguration.getObjUser().getStrContactNumber());
        contactTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCONTACTNUMBER")));
        container.add(contactTF, 1, 4);

        Label address= new Label(objDictionaryAction.getWord("ADDRESS")+" :");
        //address.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(address, 0, 5);
        final TextField addressTF = new TextField();
        addressTF.setPromptText(objDictionaryAction.getWord("PROMPTADDRESS"));
        addressTF.setText(objConfiguration.getObjUser().getStrAddress());
        addressTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPADDRESS")));
        container.add(addressTF, 1, 5);

        Label countryAddress= new Label(objDictionaryAction.getWord("COUNTRY")+" :");
        //countryAddress.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(countryAddress, 0, 6);
        //countryCB = new ComboBox<Label>();
        loadCountry();
        countryCB.setCellFactory(new Callback<ListView<Label>, ListCell<Label>>() {
            @Override
            public ListCell<Label> call(ListView<Label> p) {
                return new ListCell<Label>() {
                    private final Label cell=new Label();
                    
                    @Override
                    protected void updateItem(Label item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getText());
                        }
                    }
                };
            }
        });
        countryCB.valueProperty().addListener(new ChangeListener<Label>() {
            @Override
            public void changed(ObservableValue<? extends Label> ov, Label t, Label t1) {
                Label lblTemp = (Label)t1;
                loadState(Integer.parseInt(lblTemp.getUserData().toString()));
            }
        });
        container.add(countryCB, 1, 6);

        Label stateAddress= new Label(objDictionaryAction.getWord("STATE")+" :");
        //countryAddress.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(stateAddress, 0, 7);
        //stateCB = new ComboBox<Label>();
        loadState(0);
        stateCB.setCellFactory(new Callback<ListView<Label>, ListCell<Label>>() {
            @Override
            public ListCell<Label> call(ListView<Label> p) {
                return new ListCell<Label>() {
                    private final Label cell=new Label();
                    
                    @Override
                    protected void updateItem(Label item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getText());
                        }
                    }
                };
            }
        });
        
        stateCB.valueProperty().addListener(new ChangeListener<Label>() {
            @Override
            public void changed(ObservableValue<? extends Label> ov, Label t, Label t1) {
                Label lblTemp = (Label)t1;
                loadCity(Integer.parseInt(lblTemp.getUserData().toString()));
            }
        });
        container.add(stateCB, 1, 7);

        Label cityAddress= new Label(objDictionaryAction.getWord("CITY")+" :");
        //countryAddress.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(cityAddress, 0, 8);
        //cityCB = new ComboBox<Label>();
        loadCity(0);
        cityCB.setCellFactory(new Callback<ListView<Label>, ListCell<Label>>() {
            @Override
            public ListCell<Label> call(ListView<Label> p) {
                return new ListCell<Label>() {
                    private final Label cell=new Label();
                    
                    @Override
                    protected void updateItem(Label item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getText());
                        }
                    }
                };
            }
        });
        container.add(cityCB, 1, 8);

        Label pincodeAddress= new Label(objDictionaryAction.getWord("PINCODEADDRESS")+" :");
        //address.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(pincodeAddress, 0, 9);
        final TextField pincodeTF = new TextField();
        pincodeTF.setPromptText(objDictionaryAction.getWord("PROMPTPINCODEADDRESS"));
        pincodeTF.setText(objConfiguration.getObjUser().getStrPincodeAddress());
        pincodeTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPINCODEADDRESS")));
        container.add(pincodeTF, 1, 9);

        Label geoAddress= new Label(objDictionaryAction.getWord("NEARBY")+" :");
        //address.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(geoAddress, 0, 10);
        final TextField geoTF = new TextField();
        geoTF.setPromptText(objDictionaryAction.getWord("PROMPTNEARBY"));
        geoTF.setText(objConfiguration.getObjUser().getStrGeoCity());
        geoTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNEARBY")));
        container.add(geoTF, 1, 10);

        Label dob= new Label(objDictionaryAction.getWord("DOB")+" :");
        //dob.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(dob, 0, 11);
        //DatePicker checkInDatePicker = new DatePicker(); 
        final TextField dobTF = new TextField();
        dobTF.setPromptText(objDictionaryAction.getWord("PROMPTDOB")); 
        dobTF.setText(objConfiguration.getObjUser().getStrDOB());
        dobTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDOB")));
        container.add(dobTF, 1, 11);

        Label gender= new Label(objDictionaryAction.getWord("GENDER")+" :");
        //gender.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(gender, 0, 12);
        final ComboBox genderCB = new ComboBox();
        genderCB.getItems().addAll("M","F","O");   
        genderCB.setValue(objConfiguration.getObjUser().getStrGender());        
        genderCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPGENDER")));
        container.add(genderCB, 1, 12);

        Label education= new Label(objDictionaryAction.getWord("EDUCATION")+" :");
        //education.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(education, 0, 13);
        final ComboBox educationCB = new ComboBox();
        educationCB.getItems().addAll("Illiterate","Matriculation","Intermediate","Bachelor","Master");   
        educationCB.setValue(objConfiguration.getObjUser().getStrEducation());        
        educationCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEDUCATION")));
        container.add(educationCB, 1, 13);

        Label occupation= new Label(objDictionaryAction.getWord("OCCUPATION")+" :");
        //address.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(occupation, 0, 14);
        final TextField occupationTF = new TextField();
        occupationTF.setPromptText(objDictionaryAction.getWord("PROMPTOCCUPATION"));
        occupationTF.setText(objConfiguration.getObjUser().getStrOccupation());
        occupationTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOCCUPATION")));
        container.add(occupationTF, 1, 14);

        /*Label organization= new Label(objDictionaryAction.getWord("ORGANIZATION")+" :");
        //organization.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(organization, 0, 15);
        final TextField organizationTF = new TextField();
        organizationTF.setPromptText(objDictionaryAction.getWord("PROMPTORGANIZATION")); 
        organizationTF.setText(objConfiguration.getObjUser().getStrOrganization());
        organizationTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPORGANIZATION")));
        container.add(organizationTF, 1, 15);
        */

        ImageView profilePic = new ImageView("/media/user.png");
        profilePic.setFitHeight(200);
        profilePic.setFitWidth(200);
        container.add(profilePic, 3, 2, 2, 13);

        //action == events
        Button B_apply = new Button(objDictionaryAction.getWord("APPLY"));
        B_apply.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
        B_apply.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPAPPLY")));
        container.add(B_apply, 2, 16); 

        Button B_skip = new Button(objDictionaryAction.getWord("CANCEL"));
        B_skip.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
        B_skip.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
        container.add(B_skip, 4, 16);  

        B_apply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                new MessageView(objConfiguration);
                if(objConfiguration.getServicePasswordValid()){
                    objConfiguration.setServicePasswordValid(false);
                    objConfiguration.getObjUser().setStrName(nameTF.getText());
                    objConfiguration.getObjUser().setStrEmailID(emailTF.getText());
                    objConfiguration.getObjUser().setStrContactNumber(contactTF.getText());
                    objConfiguration.getObjUser().setStrAddress(addressTF.getText());
                    objConfiguration.getObjUser().setStrCityAddress(((Label)cityCB.getValue()).getUserData().toString());
                    objConfiguration.getObjUser().setStrStateAddress(((Label)stateCB.getValue()).getUserData().toString());
                    objConfiguration.getObjUser().setStrCountryAddress(((Label)countryCB.getValue()).getUserData().toString());
                    objConfiguration.getObjUser().setStrPincodeAddress(pincodeTF.getText());
                    objConfiguration.getObjUser().setStrGeoCity(geoTF.getText());
                    objConfiguration.getObjUser().setDblGeoLat(0.1);
                    objConfiguration.getObjUser().setDblGeoLng(0.1);
                    objConfiguration.getObjUser().setStrDOB(dobTF.getText());
                    objConfiguration.getObjUser().setStrGender(genderCB.getValue().toString());
                    objConfiguration.getObjUser().setStrEducation(educationCB.getValue().toString());
                    objConfiguration.getObjUser().setStrOrganization(occupationTF.getText());
                    try {
                        UserAction objUserAction = new UserAction();
                        objUserAction.updateUserProfile(objConfiguration);
                    } catch (Exception ex) {
                        new Logging("SEVERE",UserProfileView.class.getName(),"save configuration",ex);
                    }
                    System.gc();
                    userProfileStage.close();
                    WindowView objWindowView = new WindowView(objConfiguration);
                }
            }
        });
        B_skip.setOnAction(new EventHandler<ActionEvent>() {
             @Override
            public void handle(ActionEvent e) {
                System.gc();
                userProfileStage.close();
                WindowView objWindowView = new WindowView(objConfiguration);
            }
        });
        
         /**
         * Description:
         *  Adds "Change Password" button to Edit Profile (UserProfileView). When it is pressed, change password stage pops up.
         *  User is asked to enter OLD password, NEW password & CONFIRM NEW password. OLD password is verified with database table using "com.mla.user.UserAction.verifyLogin()".
         *  If verified, NEW password is (firstly hashed using "com.mla.user.UserAction.updatePassword()") updated in database table "mla_users".
         * Date Added: 9 Feb 2017
         * Author: Aatif Ahmad Khan
         */
        Button B_changePassword=new Button(objDictionaryAction.getWord("CHANGEPASSWORD"));
        B_changePassword.setGraphic(new ImageView(objConfiguration.getStrColour()+"/settings.png"));
        B_changePassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCHANGEPASSWORD")));
        container.add(B_changePassword, 1, 16);
        B_changePassword.setOnAction(new EventHandler<ActionEvent>() {
             @Override
            public void handle(ActionEvent e) {
                final Stage popUpStage=new Stage();
                Label caption=new Label("Enter Password Details: ");
                final Label message=new Label("");
                Label captionOldPassword= new Label(objDictionaryAction.getWord("OLD")+" "+objDictionaryAction.getWord("PASSWORD"));
                final PasswordField textOldPassword=new PasswordField();
                Label captionNewPassword= new Label(objDictionaryAction.getWord("NEW")+" "+objDictionaryAction.getWord("PASSWORD"));
                final PasswordField textNewPassword=new PasswordField();
                Label captionConfirmPassword=new Label(objDictionaryAction.getWord("CONFIRMPASSWORD"));
                final PasswordField textConfirmPassword=new PasswordField();
                Button btnChange=new Button(objDictionaryAction.getWord("CHANGEPASSWORD"));
                final Button btnCancel=new Button(objDictionaryAction.getWord("CANCEL"));
                final GridPane root=new GridPane();
                root.setVgap(10);
                root.setHgap(5);
                root.setAlignment(Pos.CENTER);
                root.add(caption, 0, 0, 2, 1);
                root.add(captionOldPassword, 0, 1, 1, 1);
                root.add(textOldPassword, 1, 1, 1, 1);
                root.add(captionNewPassword, 0, 2, 1, 1);
                root.add(textNewPassword, 1, 2, 1, 1);
                root.add(captionConfirmPassword, 0, 3, 1, 1);
                root.add(textConfirmPassword, 1, 3, 1, 1);
                root.add(btnChange, 0, 4, 1, 1);
                root.add(btnCancel, 1, 4, 1, 1);
                root.add(message, 0, 5, 2, 1);
                btnChange.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event) {
                        if(textOldPassword.getText().length()==0||textNewPassword.getText().length()==0||textConfirmPassword.getText().length()==0){
                            message.setText("Enter Details First!");    // if either of the password fields is empty
                        }
                        else{
                            if(!textNewPassword.getText().equals(textConfirmPassword.getText())){
                                // NEW password & CONFIRM NEW password do not match
                                message.setText("New Password and Confirm New Password doesn't match");
                            }
                            else{
                                try{
                                    UserAction userAction=new UserAction();
                                    // temporarily, store getStrPassword() as we will be overwriting it with User Entered OLD (TO BE VALIDATED) password.
                                    String backupStrPassword=objConfiguration.getObjUser().getStrPassword();
                                    // overwriting getStrPassword() for validation
                                    objConfiguration.getObjUser().setStrPassword(textOldPassword.getText());
                                    // validating User Entered OLD password using "com.mla.user.UserAction.verifyLogin()".
                                    boolean passwordMatch=userAction.verifyLogin(objConfiguration.getObjUser());
                                    // setting back original getStrPassword() from backupStrPassword
                                    objConfiguration.getObjUser().setStrPassword(backupStrPassword);
                                    if(passwordMatch){  // OLD password validated successfully
                                        // NEW password being hashed and updated in "com.mla.user.UserAction.updatePassword()".
                                        objConfiguration.getObjUser().setStrPassword(textNewPassword.getText());
                                        boolean changed=new UserAction().updatePassword(objConfiguration.getObjUser());
                                        if(changed){    // password modified successfully
                                            root.getChildren().clear();
                                            root.add(message, 0, 0);
                                            btnCancel.setText("Close");
                                            root.add(btnCancel, 1, 1, 1, 1);
                                            message.setText("Password Changed Successfully!");
                                        }
                                        else{       // password updation failed
                                            message.setText("Unexpected Error! Password is NOT updated.");
                                        }
                                    }
                                    else{           // OLD password not validated
                                        objConfiguration.getObjUser().setStrPassword(backupStrPassword);
                                        message.setText("Old Password is not valid!");
                                    }
                                }
                                catch(Exception ex){
                                        new Logging("SEVERE",UserProfileView.class.getName(),"ChangePassword -> verifyLogin() or updatePassword(): error validating/updating data from database",ex);
                                }
                            }
                        }
                    }
                });
                btnCancel.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event) {
                        popUpStage.close();
                    }
                });
                Scene scene=new Scene(root, 500, 300);
                popUpStage.setScene(scene);
                popUpStage.setTitle("Change Password");
                popUpStage.showAndWait();
                System.gc();
            }
        });

        
        userProfileStage.getIcons().add(new Image("/media/icon.png"));
        userProfileStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWUSERSETTINGS")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        //userSettingStage.setIconified(true);
        userProfileStage.setResizable(false);
        userProfileStage.setScene(scene);
        userProfileStage.setX(0);
        userProfileStage.setY(0);
        userProfileStage.show();
        userProfileStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                final Stage dialogStage = new Stage();
                dialogStage.initStyle(StageStyle.UTILITY);
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.setResizable(false);
                dialogStage.setIconified(false);
                dialogStage.setFullScreen(false);
                dialogStage.setTitle(objDictionaryAction.getWord("ALERT"));
                BorderPane root = new BorderPane();
                Scene scene = new Scene(root, 300, 100, Color.WHITE);
                scene.getStylesheets().add(UserProfileView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                final GridPane popup=new GridPane();
                popup.setId("popup");
                popup.setHgap(5);
                popup.setVgap(5);
                popup.setPadding(new Insets(25, 25, 25, 25));
                popup.add(new ImageView(objConfiguration.getStrColour()+"/stop.png"), 0, 0);
                Label lblAlert = new Label(objDictionaryAction.getWord("ALERTCLOSE"));
                lblAlert.setStyle("-fx-wrap-text:true;");
                lblAlert.setPrefWidth(250);
                popup.add(lblAlert, 1, 0);
                Button btnYes = new Button(objDictionaryAction.getWord("YES"));
                btnYes.setPrefWidth(50);
                btnYes.setId("btnYes");
                btnYes.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        dialogStage.close();
                        userProfileStage.close();
                        System.gc();
                        WindowView objWindowView = new WindowView(objConfiguration);
                    }
                });
                popup.add(btnYes, 0, 1);
                Button btnNo = new Button(objDictionaryAction.getWord("NO"));
                btnNo.setPrefWidth(50);
                btnNo.setId("btnNo");
                btnNo.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        dialogStage.close();
                        System.gc();
                    }
                });
                popup.add(btnNo, 1, 1);
                root.setCenter(popup);
                dialogStage.setScene(scene);
                dialogStage.showAndWait();  
                we.consume();
            }
        });
    }

    private void loadCountry(){
        try {
            countryCB.getItems().clear();
            Label lblTemp = new Label("Select");
            lblTemp.setUserData(0);
            countryCB.getItems().add(lblTemp);
            countryCB.setValue(lblTemp);
            
            UserAction objUserAction = new UserAction();
            lstCountry = objUserAction.getLstCountry();
            for(int i=0; i<lstCountry.size(); i++){
                List lstTemp = (ArrayList)lstCountry.get(i);
                lblTemp = new Label(lstTemp.get(1).toString());
                lblTemp.setUserData(lstTemp.get(0));
                countryCB.getItems().add(lblTemp);
            }
        } catch (SQLException ex) {
            new Logging("SEVERE",UserProfileView.class.getName(),"SQLException: Listing Country",ex);
        } catch (Exception ex) {
            new Logging("SEVERE",UserProfileView.class.getName(),"Exception: Listing Country",ex);
        }
    }
    
    private void loadState(int intCountry){
        try {
            stateCB.getItems().clear();
            Label lblTemp = new Label("Select");
            lblTemp.setUserData(0);
            stateCB.getItems().add(lblTemp);
            stateCB.setValue(lblTemp);
            
            UserAction objUserAction = new UserAction();
            lstState = objUserAction.getLstState(intCountry);
            for(int i=0; i<lstState.size(); i++){
                List lstTemp = (ArrayList)lstState.get(i);
                lblTemp = new Label(lstTemp.get(1).toString());
                lblTemp.setUserData(lstTemp.get(0).toString());
                stateCB.getItems().add(lblTemp);
            }
        } catch (SQLException ex) {
            new Logging("SEVERE",UserProfileView.class.getName(),"SQLException: Listing State",ex);
        } catch (Exception ex) {
            new Logging("SEVERE",UserProfileView.class.getName(),"Exception: Listing State",ex);
        }
    }
    
    private void loadCity(int intState){
        try {
            cityCB.getItems().clear();
            Label lblTemp = new Label("Select");
            lblTemp.setUserData(0);
            cityCB.getItems().add(lblTemp);
            cityCB.setValue(lblTemp);
            
            UserAction objUserAction = new UserAction();
            lstCity = objUserAction.getLstCity(intState);
            for(int i=0; i<lstCity.size(); i++){
                List lstTemp = (ArrayList)lstCity.get(i);
                lblTemp = new Label(lstTemp.get(1).toString());
                lblTemp.setUserData(lstTemp.get(0).toString());
                cityCB.getItems().add(lblTemp);
            }
        } catch (SQLException ex) {
            new Logging("SEVERE",UserProfileView.class.getName(),"SQLException: Listing city",ex);
        } catch (Exception ex) {
            new Logging("SEVERE",UserProfileView.class.getName(),"Exception: Listing city",ex);
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        new UserProfileView(stage);
        new Logging("WARNING",UserProfileView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static void main(String[] args) {   
        launch(args);    
    }
}

