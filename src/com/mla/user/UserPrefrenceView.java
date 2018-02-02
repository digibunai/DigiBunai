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
import com.mla.utility.UtilityView;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
/**
 *
 * @Designing GUI window for user preferences
 * @author Amit Kumar Singh
 * 
 */
public class UserPrefrenceView extends Application {
    public static Stage userPrefrenceStage;
    BorderPane root;
    
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    
    private Menu homeMenu;
    private Menu helpMenu;
    private String strResolution;
    private int intDPI;
        
    public UserPrefrenceView(final Stage primaryStage) {}
    
    public UserPrefrenceView(Configuration objConfigurationCall) {
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);

        userPrefrenceStage = new Stage(); 
        root = new BorderPane();
        Scene scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(UserPrefrenceView.class.getResource(objConfiguration.getStrTemplate()+"/setting.css").toExternalForm());

        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(userPrefrenceStage.widthProperty());
       
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
                scene.getStylesheets().add(UserPrefrenceView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        objConfiguration.setStrResolution(strResolution);
                        objConfiguration.setIntDPI(intDPI);
                        dialogStage.close();
                        userPrefrenceStage.close();
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
                userPrefrenceStage.close();    
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
        GridPane.setColumnSpan(caption, 8);
        container.getChildren().add(caption);
 
        final Separator sepHor1 = new Separator();
        sepHor1.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor1, 0, 1);
        GridPane.setColumnSpan(sepHor1, 8);
        container.getChildren().add(sepHor1);
        
        final Separator sepVert1 = new Separator();
        sepVert1.setOrientation(Orientation.VERTICAL);
        sepVert1.setValignment(VPos.CENTER);
        sepVert1.setPrefHeight(80);
        GridPane.setConstraints(sepVert1, 2, 2);
        GridPane.setRowSpan(sepVert1, 7);
        container.getChildren().add(sepVert1);
 
        final Separator sepVert2 = new Separator();
        sepVert2.setOrientation(Orientation.VERTICAL);
        sepVert2.setValignment(VPos.CENTER);
        sepVert2.setPrefHeight(80);
        GridPane.setConstraints(sepVert2, 5, 2);
        GridPane.setRowSpan(sepVert2, 7);
        container.getChildren().add(sepVert2);
        
        final Separator sepHor2 = new Separator();
        sepHor2.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor2, 0, 9);
        GridPane.setColumnSpan(sepHor2, 8);
        container.getChildren().add(sepHor2);
        
        Label language= new Label(objDictionaryAction.getWord("LANGUAGE")+" :");
        //language.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(language, 0, 2);
        final ComboBox languageCB = new ComboBox();
		try{
            InputStream resourceAsStream = new FileInputStream(System.getProperty("user.dir")+"/mla/language/langs.properties"); //UserPrefrenceView.class.getClassLoader().getResourceAsStream("language/langs.properties");;
            Properties properties=new Properties();
            if (resourceAsStream != null) {
                properties.load(resourceAsStream);
                languageCB.getItems().clear();
                for(Object k:properties.keySet()){
                    String langName=(String)k;
                    languageCB.getItems().add(langName);
                }
            }
        }catch(Exception ex){
            languageCB.getItems().clear();
            languageCB.getItems().addAll("HINDI","ENGLISH");
        }   
        languageCB.setValue(objConfiguration.getStrLanguage());        
        languageCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPLANGUAGE")));
        container.add(languageCB, 1, 2);

        Label resolution= new Label(objDictionaryAction.getWord("RESOLUTION")+" :");
        //resolution.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(resolution, 0, 3);
        final ComboBox resolutionCB = new ComboBox();
        resolutionCB.getItems().addAll("800x600","1024x768","1152x864","1280x960","1280x1024");   
        strResolution = objConfiguration.getStrResolution();
        resolutionCB.setValue(strResolution);
        resolutionCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPRESOLUTION")));
        container.add(resolutionCB, 1, 3);
        
        Label dpi= new Label(objDictionaryAction.getWord("DPI")+" :");
        //dpi.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(dpi, 0, 4);
        intDPI = objConfiguration.getIntDPI();
        final TextField dpiV= new TextField(Integer.toString(intDPI));
        //dpiV.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        //dpiV.setDisable(true);
        dpiV.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDPI")));
        container.add(dpiV, 1, 4);

        Label measurement= new Label(objDictionaryAction.getWord("MEASUREMENT")+" :");
        //measurement.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(measurement, 0, 5);
        final ComboBox measurementCB = new ComboBox();
        measurementCB.getItems().addAll("Metric (m-kg)","Imperial (in-lb)");   
        measurementCB.setValue(objConfiguration.getStrMeasurement());
        measurementCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMEASUREMENT")));
        container.add(measurementCB, 1, 5);

        Label currency= new Label(objDictionaryAction.getWord("CURRENCY")+" :");
        //currency.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(currency, 0, 6);
        final ComboBox currencyCB = new ComboBox();
        currencyCB.getItems().addAll("Indian Rupees","Us Dolor","British Pound");
        currencyCB.setValue(objConfiguration.getStrCurrency());
        currencyCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCURRENCY")));
        container.add(currencyCB, 1, 6);

        Label timeLimit= new Label(objDictionaryAction.getWord("TIMELIMIT")+" (ms"
                + "):");
        //timeLimit.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(timeLimit, 0, 7);
        final TextField timeLimitTF = new TextField();
        timeLimitTF.setText(String.valueOf(objConfiguration.getIntTimeLimit()));
        timeLimitTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTIMELIMIT")));
        container.add(timeLimitTF, 1, 7);
        
        final CheckBox authenticateService=new CheckBox();
        authenticateService.setText(objDictionaryAction.getWord("AUTHENTICATESERVICE"));
        authenticateService.setSelected(objConfiguration.getBlnAuthenticateService());
        container.add(authenticateService, 0, 8);
        
        resolutionCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                objConfiguration.setStrResolution(t1);
                int dpi = objConfiguration.findIntDPI();
                dpiV.setText(Integer.toString(dpi));
            }
        });
        
        Label template= new Label(objDictionaryAction.getWord("TEMPLATE")+" :");
        //currency.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(template, 3, 2);
        final ComboBox templateCB = new ComboBox();
        templateCB.getItems().addAll("Default","Gold","Silver");
        templateCB.setValue(objConfiguration.getStrTemplate().substring(objConfiguration.getStrTemplate().lastIndexOf("/")+1));
        templateCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTEMPLATE")));
        container.add(templateCB, 4, 2);

        Label iColor= new Label(objDictionaryAction.getWord("ICONCOLOR")+" :");
        //currency.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(iColor, 3, 3);
        final ComboBox iColorCB = new ComboBox();
        iColorCB.getItems().addAll("blue","black","gray");
        iColorCB.setValue(objConfiguration.getStrColour().substring(objConfiguration.getStrColour().lastIndexOf("/")+1));
        iColorCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPICONCOLOR")));
        container.add(iColorCB, 4, 3);

        Label idColor= new Label(objDictionaryAction.getWord("ICONDCOLOR")+" :");
        //currency.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(idColor, 3, 4);
        final ComboBox idColorCB = new ComboBox();
        idColorCB.getItems().addAll("blue","black","gray");
        idColorCB.setValue(objConfiguration.getStrColourDimmed().substring(objConfiguration.getStrColourDimmed().lastIndexOf("/")+1));
        idColorCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPICONDCOLOR")));
        container.add(idColorCB, 4, 4);
        
        Label fontB= new Label(objDictionaryAction.getWord("BIGFONT")+" :");
        //fontB.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(fontB, 3, 5);
        final ComboBox fontBCB = new ComboBox();
        fontBCB.getItems().addAll("Arial","Tahoma","Calibri", "Times New Roman");
        fontBCB.setValue(objConfiguration.getStrBFont());
        fontBCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBIGFONT")));
        container.add(fontBCB, 4, 5);
    
        Label fontSizeB= new Label(objDictionaryAction.getWord("BIGFONTSIZE")+" :");
        //fontSizeB.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(fontSizeB, 3, 6);
        final ComboBox fontSizeBCB = new ComboBox();
        fontSizeBCB.getItems().addAll("7","9","11","13","15","17","19","21"); 
        fontSizeBCB.setValue(Integer.toString(objConfiguration.getIntBFontSize()));
        fontSizeBCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBIGFONTSIZE")));
        container.add(fontSizeBCB, 4, 6);
    
        Label fontS= new Label(objDictionaryAction.getWord("SMALLFONT")+" :");
        //fontS.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(fontS, 3, 7);
        final ComboBox fontSCB = new ComboBox();
        fontSCB.getItems().addAll("Arial","Tahoma","Calibri", "Times New Roman"); 
        fontSCB.setValue(objConfiguration.getStrSFont());
        fontSCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSMALLFONT")));
        container.add(fontSCB, 4, 7);
    
        Label fontSizeS= new Label(objDictionaryAction.getWord("SMALLFONTSIZE")+" :");
        //fontSizeS.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(fontSizeS, 3, 8);
        final ComboBox fontSizeSCB = new ComboBox();
        fontSizeSCB.getItems().addAll("7","9","11","13","15","17","19","21");  
        fontSizeSCB.setValue(Integer.toString(objConfiguration.getIntSFontSize()));
        fontSizeSCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSMALLFONTSIZE")));
        container.add(fontSizeSCB, 4, 8);
    
        Label dataPath = new Label(objDictionaryAction.getWord("DATAPATH")+" :");
        //dataPath.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(dataPath, 6, 2);
        final Label dataPathTF = new Label();
        dataPathTF.setText(objConfiguration.getStrDataPath());
        dataPathTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDATAPATH")));
        container.add(dataPathTF, 7, 2);

        Label helpPath = new Label(objDictionaryAction.getWord("HELPPATH")+" :");
        //helpPath.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(helpPath, 6, 3);
        final Label helpPathTF = new Label();
        helpPathTF.setText(objConfiguration.getStrHelpPath());
        helpPathTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPHELPPATH")));
        container.add(helpPathTF, 7, 3);

        Label savePath = new Label(objDictionaryAction.getWord("SAVEPATH")+" :");
        //savePath.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(savePath, 6, 4);
        final Label savePathTF = new Label();
        savePathTF.setText(objConfiguration.getStrSavePath());
        savePathTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVEPATH")));
        container.add(savePathTF, 7, 4);

        Label logPath = new Label(objDictionaryAction.getWord("LOGPATH")+" :");
        //logPath.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(logPath, 6, 5);
        final Label logPathTF = new Label();
        logPathTF.setText(objConfiguration.getStrLogPath());
        logPathTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPLOGPATH")));
        container.add(logPathTF, 7, 5);

        Label errorPath = new Label(objDictionaryAction.getWord("ERRORPATH")+" :");
        //errorPath.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        container.add(errorPath, 6, 6);
        final Label errorPathTF = new Label();
        errorPathTF.setText(objConfiguration.getStrErrorPath());
        errorPathTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPERRORPATH")));
        container.add(errorPathTF, 7, 6);
    
        //action == events
        Button B_apply = new Button(objDictionaryAction.getWord("APPLY"));
        B_apply.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
        B_apply.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPAPPLY")));
        container.add(B_apply, 4, 10); 

        Button B_skip = new Button(objDictionaryAction.getWord("CANCEL"));
        B_skip.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
        B_skip.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
        container.add(B_skip, 6, 10);  
        
        B_apply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if(authenticateService.isSelected())
                    objConfiguration.setBlnAuthenticateService(authenticateService.isSelected());
                new MessageView(objConfiguration);
                if(objConfiguration.getServicePasswordValid()){
                    objConfiguration.setServicePasswordValid(false);
                    objConfiguration.setStrLanguage(languageCB.getValue().toString()); 
                    objConfiguration.setStrResolution(resolutionCB.getValue().toString());
                    objConfiguration.setIntDPI(Integer.parseInt(dpiV.getText().toString()));
                    objConfiguration.setStrMeasurement(measurementCB.getValue().toString());
                    objConfiguration.setStrCurrency(currencyCB.getValue().toString());
                    objConfiguration.setIntTimeLimit(Integer.parseInt(timeLimitTF.getText()));
                    objConfiguration.setBlnAuthenticateService(authenticateService.isSelected());
                    objConfiguration.setStrTemplate("/media/template/"+templateCB.getValue().toString().toLowerCase());
                    objConfiguration.setStrColour("/media/"+iColorCB.getValue().toString().toLowerCase());
                    objConfiguration.setStrColourDimmed("/media/"+idColorCB.getValue().toString().toLowerCase());
                    objConfiguration.setStrBFont(fontBCB.getValue().toString());
                    objConfiguration.setIntBFontSize(Integer.parseInt(fontSizeBCB.getValue().toString()));
                    objConfiguration.setStrSFont(fontSCB.getValue().toString());
                    objConfiguration.setIntSFontSize(Integer.parseInt(fontSizeSCB.getValue().toString()));
                    objConfiguration.setStrDataPath(dataPathTF.getText().toString());
                    objConfiguration.setStrHelpPath(helpPathTF.getText().toString());
                    objConfiguration.setStrSavePath(savePathTF.getText().toString());
                    objConfiguration.setStrLogPath(logPathTF.getText().toString());
                    objConfiguration.setStrErrorPath(errorPathTF.getText().toString());
                    try {
                        UserAction objUserAction = new UserAction();
                        objUserAction.updateUserPrefrence(objConfiguration);
                    } catch (Exception ex) {
                        new Logging("SEVERE",UserPrefrenceView.class.getName(),"save configuration",ex);
                    }
                    System.gc();
                    userPrefrenceStage.close();
                    WindowView objWindowView = new WindowView(objConfiguration);
                }else{
                    if(authenticateService.isSelected())
                        objConfiguration.setBlnAuthenticateService(!authenticateService.isSelected());
                }
            }
        });
        B_skip.setOnAction(new EventHandler<ActionEvent>() {
             @Override
            public void handle(ActionEvent e) {
                objConfiguration.setStrResolution(strResolution);
                objConfiguration.setIntDPI(intDPI);
                System.gc();
                userPrefrenceStage.close();
                WindowView objWindowView = new WindowView(objConfiguration);
            }
        });
        userPrefrenceStage.getIcons().add(new Image("/media/icon.png"));
        userPrefrenceStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWUSERPREFERENCE")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        //userSettingStage.setIconified(true);
        userPrefrenceStage.setResizable(false);
        userPrefrenceStage.setScene(scene);
        userPrefrenceStage.setX(0);
        userPrefrenceStage.setY(0);
        userPrefrenceStage.show();
        userPrefrenceStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
                scene.getStylesheets().add(UserPrefrenceView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        userPrefrenceStage.close();
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
        new UserPrefrenceView(stage);
        new Logging("WARNING",UserPrefrenceView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static void main(String[] args) {   
        launch(args);    
    }
}
