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
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
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
/**
 *
 * @Designing GUI window for user preferences
 * @author Amit Kumar Singh
 * 
 */
public class UserSettingView extends Application {
    public static Stage userSettingStage;
    BorderPane root;
    
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    
    private Menu homeMenu;
    private Menu helpMenu;
        
    public UserSettingView(final Stage primaryStage) {}
    
    public UserSettingView(Configuration objConfigurationCall) {
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);

        userSettingStage = new Stage(); 
        root = new BorderPane();
        Scene scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(UserSettingView.class.getResource(objConfiguration.getStrTemplate()+"/setting.css").toExternalForm());

        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(userSettingStage.widthProperty());
       
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
                scene.getStylesheets().add(UserSettingView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        userSettingStage.close();
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
                userSettingStage.close();    
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
 
        Label caption = new Label(objDictionaryAction.getWord("USER")+" "+objDictionaryAction.getWord("CONFIGURATION"));
        caption.setId("caption");
        GridPane.setConstraints(caption, 0, 0);
        GridPane.setColumnSpan(caption, 4);
        container.getChildren().add(caption);
 
        final Separator sepHor1 = new Separator();
        sepHor1.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor1, 0, 1);
        GridPane.setColumnSpan(sepHor1, 4);
        container.getChildren().add(sepHor1);
        
        final Separator sepHor2 = new Separator();
        sepHor2.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor2, 0, 12);
        GridPane.setColumnSpan(sepHor2, 4);
        container.getChildren().add(sepHor2);
        
        final ToggleGroup fabricTG = new ToggleGroup();
        Label fabricLbl = new Label(objDictionaryAction.getWord("FABRIC LIBRARY"));
        fabricLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/fabric_library.png"));
        fabricLbl.setId("label");
        container.add(fabricLbl, 0, 2);
        RadioButton fabricPublicRB = new RadioButton(objDictionaryAction.getWord("PUBLIC"));
        fabricPublicRB.setToggleGroup(fabricTG);
        fabricPublicRB.setUserData("Public");
        container.add(fabricPublicRB, 1, 2);
        RadioButton fabricPrivateRB = new RadioButton(objDictionaryAction.getWord("PRIVATE"));
        fabricPrivateRB.setToggleGroup(fabricTG);
        fabricPrivateRB.setUserData("Private");
        container.add(fabricPrivateRB, 2, 2);
        RadioButton fabricProtectedRB = new RadioButton(objDictionaryAction.getWord("PROTECTED"));
        fabricProtectedRB.setToggleGroup(fabricTG);
        fabricProtectedRB.setUserData("Protected");
        container.add(fabricProtectedRB, 3, 2);
        if(objConfiguration.getObjUser().getUserAccess("FABRIC_LIBRARY").equalsIgnoreCase("Public"))
            fabricTG.selectToggle(fabricPublicRB);
        else if(objConfiguration.getObjUser().getUserAccess("FABRIC_LIBRARY").equalsIgnoreCase("Protected"))
            fabricTG.selectToggle(fabricProtectedRB);
        else
            fabricTG.selectToggle(fabricPrivateRB);
        
        final ToggleGroup artworkTG = new ToggleGroup();
        Label artworkLbl = new Label(objDictionaryAction.getWord("ARTWORK LIBRARY"));
        artworkLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/artwork_library.png"));
        artworkLbl.setId("label");
        container.add(artworkLbl, 0, 3);
        RadioButton artworkPublicRB = new RadioButton(objDictionaryAction.getWord("PUBLIC"));
        artworkPublicRB.setToggleGroup(artworkTG);
        artworkPublicRB.setUserData("Public");
        container.add(artworkPublicRB, 1, 3);
        RadioButton artworkPrivateRB = new RadioButton(objDictionaryAction.getWord("PRIVATE"));
        artworkPrivateRB.setToggleGroup(artworkTG);
        artworkPrivateRB.setUserData("Private");
        container.add(artworkPrivateRB, 2, 3);
        RadioButton artworkProtectedRB = new RadioButton(objDictionaryAction.getWord("PROTECTED"));
        artworkProtectedRB.setToggleGroup(artworkTG);
        artworkProtectedRB.setUserData("Protected");
        container.add(artworkProtectedRB, 3, 3);
        if(objConfiguration.getObjUser().getUserAccess("ARTWORK_LIBRARY").equalsIgnoreCase("Public"))
            artworkTG.selectToggle(artworkPublicRB);
        else if(objConfiguration.getObjUser().getUserAccess("ARTWORK_LIBRARY").equalsIgnoreCase("Protected"))
            artworkTG.selectToggle(artworkProtectedRB);
        else
            artworkTG.selectToggle(artworkPrivateRB);
        
        final ToggleGroup weaveTG = new ToggleGroup();
        Label weaveLbl = new Label(objDictionaryAction.getWord("WEAVE LIBRARY"));
        weaveLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/weave_library.png"));
        weaveLbl.setId("label");
        container.add(weaveLbl, 0, 4);
        RadioButton weavePublicRB = new RadioButton(objDictionaryAction.getWord("PUBLIC"));
        weavePublicRB.setToggleGroup(weaveTG);
        weavePublicRB.setUserData("Public");
        container.add(weavePublicRB, 1, 4);
        RadioButton weavePrivateRB = new RadioButton(objDictionaryAction.getWord("PRIVATE"));
        weavePrivateRB.setToggleGroup(weaveTG);
        weavePrivateRB.setUserData("Private");
        container.add(weavePrivateRB, 2, 4);
        RadioButton weaveProtectedRB = new RadioButton(objDictionaryAction.getWord("PROTECTED"));
        weaveProtectedRB.setToggleGroup(weaveTG);
        weaveProtectedRB.setUserData("Protected");
        container.add(weaveProtectedRB, 3, 4);
        if(objConfiguration.getObjUser().getUserAccess("WEAVE_LIBRARY").equalsIgnoreCase("Public"))
            weaveTG.selectToggle(weavePublicRB);
        else if(objConfiguration.getObjUser().getUserAccess("WEAVE_LIBRARY").equalsIgnoreCase("Protected"))
            weaveTG.selectToggle(weaveProtectedRB);
        else
            weaveTG.selectToggle(weavePrivateRB);
        
        final ToggleGroup clothTG = new ToggleGroup();
        Label clothLbl = new Label(objDictionaryAction.getWord("CLOTH LIBRARY"));
        clothLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/simulation.png"));
        clothLbl.setId("label");
        container.add(clothLbl, 0, 5);
        RadioButton clothPublicRB = new RadioButton(objDictionaryAction.getWord("PUBLIC"));
        clothPublicRB.setToggleGroup(clothTG);
        clothPublicRB.setUserData("Public");
        container.add(clothPublicRB, 1, 5);
        RadioButton clothPrivateRB = new RadioButton(objDictionaryAction.getWord("PRIVATE"));
        clothPrivateRB.setToggleGroup(clothTG);
        clothPrivateRB.setUserData("Private");
        container.add(clothPrivateRB, 2, 5);
        RadioButton clothProtectedRB = new RadioButton(objDictionaryAction.getWord("PROTECTED"));
        clothProtectedRB.setToggleGroup(clothTG);
        clothProtectedRB.setUserData("Protected");
        container.add(clothProtectedRB, 3, 5);
        clothTG.selectToggle(clothProtectedRB);
        if(objConfiguration.getObjUser().getUserAccess("CLOTH_LIBRARY").equalsIgnoreCase("Public"))
            clothTG.selectToggle(clothPublicRB);
        else if(objConfiguration.getObjUser().getUserAccess("CLOTH_LIBRARY").equalsIgnoreCase("Protected"))
            clothTG.selectToggle(clothProtectedRB);
        else
            clothTG.selectToggle(clothPrivateRB);
        
        final ToggleGroup yarnTG = new ToggleGroup();
        Label yarnLbl = new Label(objDictionaryAction.getWord("YARN LIBRARY"));
        yarnLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/yarn.png"));
        yarnLbl.setId("label");
        container.add(yarnLbl, 0, 6);
        RadioButton yarnPublicRB = new RadioButton(objDictionaryAction.getWord("PUBLIC"));
        yarnPublicRB.setToggleGroup(yarnTG);
        yarnPublicRB.setUserData("Public");
        container.add(yarnPublicRB, 1, 6);
        RadioButton yarnPrivateRB = new RadioButton(objDictionaryAction.getWord("PRIVATE"));
        yarnPrivateRB.setToggleGroup(yarnTG);
        yarnPrivateRB.setUserData("Private");
        container.add(yarnPrivateRB, 2, 6);
        RadioButton yarnProtectedRB = new RadioButton(objDictionaryAction.getWord("PROTECTED"));
        yarnProtectedRB.setToggleGroup(yarnTG);
        yarnProtectedRB.setUserData("Protected");
        container.add(yarnProtectedRB, 3, 6);
        if(objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY").equalsIgnoreCase("Public"))
            yarnTG.selectToggle(yarnPublicRB);
        else if(objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY").equalsIgnoreCase("Protected"))
            yarnTG.selectToggle(yarnProtectedRB);
        else
            yarnTG.selectToggle(yarnPrivateRB);
        
        final ToggleGroup patternTG = new ToggleGroup();
        Label patternLbl = new Label(objDictionaryAction.getWord("YARN PATTERN LIBRARY"));
        patternLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/thread_pattern.png"));
        patternLbl.setId("label");
        container.add(patternLbl, 0, 7);
        RadioButton patternPublicRB = new RadioButton(objDictionaryAction.getWord("PUBLIC"));
        patternPublicRB.setToggleGroup(patternTG);
        patternPublicRB.setUserData("Public");
        container.add(patternPublicRB, 1, 7);
        RadioButton patternPrivateRB = new RadioButton(objDictionaryAction.getWord("PRIVATE"));
        patternPrivateRB.setToggleGroup(patternTG);
        patternPrivateRB.setUserData("Private");
        container.add(patternPrivateRB, 2, 7);
        RadioButton patternProtectedRB = new RadioButton(objDictionaryAction.getWord("PROTECTED"));
        patternProtectedRB.setToggleGroup(patternTG);
        patternProtectedRB.setUserData("Protected");
        container.add(patternProtectedRB, 3, 7);
        if(objConfiguration.getObjUser().getUserAccess("PATTERN_LIBRARY").equalsIgnoreCase("Public"))
            patternTG.selectToggle(patternPublicRB);
        else if(objConfiguration.getObjUser().getUserAccess("PATTERN_LIBRARY").equalsIgnoreCase("Protected"))
            patternTG.selectToggle(patternProtectedRB);
        else
            patternTG.selectToggle(patternPrivateRB);
        
        final ToggleGroup deviceTG = new ToggleGroup();
        Label deviceLbl = new Label(objDictionaryAction.getWord("DEVICE LIBRARY"));
        deviceLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/application_integration.png"));
        deviceLbl.setId("label");
        container.add(deviceLbl, 0, 8);
        RadioButton devicePublicRB = new RadioButton(objDictionaryAction.getWord("PUBLIC"));
        devicePublicRB.setToggleGroup(deviceTG);
        devicePublicRB.setUserData("Public");
        container.add(devicePublicRB, 1, 8);
        RadioButton devicePrivateRB = new RadioButton(objDictionaryAction.getWord("PRIVATE"));
        devicePrivateRB.setToggleGroup(deviceTG);
        devicePrivateRB.setUserData("Private");
        container.add(devicePrivateRB, 2, 8);
        RadioButton deviceProtectedRB = new RadioButton(objDictionaryAction.getWord("PROTECTED"));
        deviceProtectedRB.setToggleGroup(deviceTG);
        deviceProtectedRB.setUserData("Protected");
        container.add(deviceProtectedRB, 3, 8);
        if(objConfiguration.getObjUser().getUserAccess("DEVICE_LIBRARY").equalsIgnoreCase("Public"))
            deviceTG.selectToggle(devicePublicRB);
        else if(objConfiguration.getObjUser().getUserAccess("DEVICE_LIBRARY").equalsIgnoreCase("Protected"))
            deviceTG.selectToggle(deviceProtectedRB);
        else
            deviceTG.selectToggle(devicePrivateRB);
        
        final ToggleGroup colourTG = new ToggleGroup();
        Label colourLbl = new Label(objDictionaryAction.getWord("YARN COLOUR LIBRARY"));
        colourLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/color_palette.png"));
        colourLbl.setId("label");
        container.add(colourLbl, 0, 9);
        RadioButton colourPublicRB = new RadioButton(objDictionaryAction.getWord("PUBLIC"));
        colourPublicRB.setToggleGroup(colourTG);
        colourPublicRB.setUserData("Public");
        container.add(colourPublicRB, 1, 9);
        RadioButton colourPrivateRB = new RadioButton(objDictionaryAction.getWord("PRIVATE"));
        colourPrivateRB.setToggleGroup(colourTG);
        colourPrivateRB.setUserData("Private");
        container.add(colourPrivateRB, 2, 9);
        RadioButton colourProtectedRB = new RadioButton(objDictionaryAction.getWord("PROTECTED"));
        colourProtectedRB.setToggleGroup(colourTG);
        colourProtectedRB.setUserData("Protected");
        container.add(colourProtectedRB, 3, 9);
        if(objConfiguration.getObjUser().getUserAccess("COLOUR_LIBRARY").equalsIgnoreCase("Public"))
            colourTG.selectToggle(colourPublicRB);
        else if(objConfiguration.getObjUser().getUserAccess("COLOUR_LIBRARY").equalsIgnoreCase("Protected"))
            colourTG.selectToggle(colourProtectedRB);
        else
            colourTG.selectToggle(colourPrivateRB);
        
        final ToggleGroup languageTG = new ToggleGroup();
        Label languageLbl = new Label(objDictionaryAction.getWord("LANGUAGE LIBRARY"));
        languageLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/translation.png"));
        languageLbl.setId("label");
        container.add(languageLbl, 0, 10);
        RadioButton languagePublicRB = new RadioButton(objDictionaryAction.getWord("PUBLIC"));
        languagePublicRB.setToggleGroup(languageTG);
        languagePublicRB.setUserData("Public");
        container.add(languagePublicRB, 1, 10);
        RadioButton languagePrivateRB = new RadioButton(objDictionaryAction.getWord("PRIVATE"));
        languagePrivateRB.setToggleGroup(languageTG);
        languagePrivateRB.setUserData("Private");
        container.add(languagePrivateRB, 2, 10);
        RadioButton languageProtectedRB = new RadioButton(objDictionaryAction.getWord("PROTECTED"));
        languageProtectedRB.setToggleGroup(languageTG);
        languageProtectedRB.setUserData("Protected");
        container.add(languageProtectedRB, 3, 10);
        if(objConfiguration.getObjUser().getUserAccess("LANGUAGE_LIBRARY").equalsIgnoreCase("Public"))
            languageTG.selectToggle(languagePublicRB);
        else if(objConfiguration.getObjUser().getUserAccess("LANGUAGE_LIBRARY").equalsIgnoreCase("Protected"))
            languageTG.selectToggle(languageProtectedRB);
        else
            languageTG.selectToggle(languagePrivateRB);
        
        final ToggleGroup simulationTG = new ToggleGroup();
        Label simulationLbl = new Label(objDictionaryAction.getWord("SIMULATION LIBRARY"));
        simulationLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/simulation.png"));
        simulationLbl.setId("label");
        container.add(simulationLbl, 0, 11);
        RadioButton simulationPublicRB = new RadioButton(objDictionaryAction.getWord("PUBLIC"));
        simulationPublicRB.setToggleGroup(languageTG);
        simulationPublicRB.setUserData("Public");
        container.add(simulationPublicRB, 1, 11);
        RadioButton simulationPrivateRB = new RadioButton(objDictionaryAction.getWord("PRIVATE"));
        simulationPrivateRB.setToggleGroup(languageTG);
        simulationPrivateRB.setUserData("Private");
        container.add(simulationPrivateRB, 2, 11);
        RadioButton simulationProtectedRB = new RadioButton(objDictionaryAction.getWord("PROTECTED"));
        simulationProtectedRB.setToggleGroup(languageTG);
        simulationProtectedRB.setUserData("Protected");
        container.add(simulationProtectedRB, 3, 11);
        if(objConfiguration.getObjUser().getUserAccess("FABRIC_BASE_LIBRARY").equalsIgnoreCase("Public"))
            simulationTG.selectToggle(simulationPublicRB);
        else if(objConfiguration.getObjUser().getUserAccess("FABRIC_BASE_LIBRARY").equalsIgnoreCase("Protected"))
            simulationTG.selectToggle(simulationProtectedRB);
        else
            simulationTG.selectToggle(simulationPrivateRB);
        
        //action == events
        Button B_apply = new Button(objDictionaryAction.getWord("APPLY"));
        B_apply.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
        B_apply.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPAPPLY")));
        container.add(B_apply, 2, 13); 

        Button B_skip = new Button(objDictionaryAction.getWord("CANCEL"));
        B_skip.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
        B_skip.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
        container.add(B_skip, 3, 13);  
        
        B_apply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                new MessageView(objConfiguration);
                if(objConfiguration.getServicePasswordValid()){
                    objConfiguration.setServicePasswordValid(false);
                    try {
                        objConfiguration.getObjUser().setUserAccess("FABRIC_LIBRARY", fabricTG.getSelectedToggle().getUserData().toString());
                        objConfiguration.getObjUser().setUserAccess("ARTWORK_LIBRARY", artworkTG.getSelectedToggle().getUserData().toString());
                        objConfiguration.getObjUser().setUserAccess("WEAVE_LIBRARY", weaveTG.getSelectedToggle().getUserData().toString());
                        objConfiguration.getObjUser().setUserAccess("CLOTH_LIBRARY", clothTG.getSelectedToggle().getUserData().toString());
                        objConfiguration.getObjUser().setUserAccess("PATTERN_LIBRARY", patternTG.getSelectedToggle().getUserData().toString());
                        objConfiguration.getObjUser().setUserAccess("YARN_LIBRARY", yarnTG.getSelectedToggle().getUserData().toString());
                        objConfiguration.getObjUser().setUserAccess("COLOUR_LIBRARY", colourTG.getSelectedToggle().getUserData().toString());
                        objConfiguration.getObjUser().setUserAccess("LANGUAGE_LIBRARY", languageTG.getSelectedToggle().getUserData().toString());
                        objConfiguration.getObjUser().setUserAccess("DEVICE_LIBRARY", deviceTG.getSelectedToggle().getUserData().toString()); 
                        objConfiguration.getObjUser().setUserAccess("FABRIC_BASE_LIBRARY", simulationTG.getSelectedToggle().getUserData().toString()); 
                        UserAction objUserAction = new UserAction();
                        objUserAction.updateUserAccess(objConfiguration);
                    } catch (Exception ex) {
                        new Logging("SEVERE",UserSettingView.class.getName(),"save configuration",ex);
                    }
                    System.gc();
                    userSettingStage.close();
                    WindowView objWindowView = new WindowView(objConfiguration);
                }
            }
        });
        B_skip.setOnAction(new EventHandler<ActionEvent>() {
             @Override
            public void handle(ActionEvent e) {
                System.gc();
                userSettingStage.close();
                WindowView objWindowView = new WindowView(objConfiguration);
            }
        });
        userSettingStage.getIcons().add(new Image("/media/icon.png"));
        userSettingStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWUSERSETTINGS")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        //userSettingStage.setIconified(true);
        userSettingStage.setResizable(false);
        userSettingStage.setScene(scene);
        userSettingStage.setX(0);
        userSettingStage.setY(0);
        userSettingStage.show();
        userSettingStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
                scene.getStylesheets().add(UserSettingView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        userSettingStage.close();
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
    
    @Override
    public void start(Stage stage) throws Exception {
        new UserSettingView(stage);
        new Logging("WARNING",UserSettingView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  /*
  public static void main(String[] args) {   
      launch(args);    
  }
  */
}
