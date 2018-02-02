/*
 * Copyright (C) 2017 HP
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

package com.mla.utility;

import com.mla.dictionary.DictionaryAction;
import com.mla.main.AboutView;
import com.mla.main.Configuration;
import com.mla.main.ContactView;
import com.mla.main.HelpView;
import com.mla.main.Logging;
import com.mla.main.TechnicalView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
/**
 *
 * @Designing GUI window for fabric preferences
 * @author Amit Kumar Singh
 * 
 */
public class TranslatorView extends Application {
    public static Stage translatorStage;
    BorderPane root;
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;
    Task copyWorker;
    
    GridPane translatorGP;
    GridPane GP_container;
    
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    
    private Menu homeMenu;
    private Menu helpMenu;

    private Button btnCreate;
    private Button btnSave;
    private Button btnSaveAs;
    private Button btnDelete;
    private Button btnClear;

    private ComboBox languageCB;

    boolean isNew = false;
    boolean isLoaded = false;
    String strLanguage="ENGLISH";

    public TranslatorView(final Stage primaryStage) {}
    
    public TranslatorView(Configuration objConfigurationCall) {
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);

        translatorStage = new Stage(); 
        root = new BorderPane();
        Scene scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(TranslatorView.class.getResource(objConfiguration.getStrTemplate()+"/setting.css").toExternalForm());
        
        HBox footContainer = new HBox();
        progressB = new ProgressBar(0);
        progressB.setVisible(false);
        progressI = new ProgressIndicator(0);
        progressI.setVisible(false);
        lblStatus = new Label(objDictionaryAction.getWord("WELCOMETOCADTOOL"));
        lblStatus.setId("message");
        footContainer.getChildren().addAll(lblStatus,progressB,progressI);
        footContainer.setId("footContainer");
        root.setBottom(footContainer);
        
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(translatorStage.widthProperty());
        
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
                scene.getStylesheets().add(TranslatorView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        translatorStage.close();
                        System.gc();
                        UtilityView objUtilityView = new UtilityView(objConfiguration);
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
                translatorStage.close();    
            }
        });    
        helpMenu.getItems().addAll(helpMenuItem, technicalMenuItem, aboutMenuItem, contactMenuItem, new SeparatorMenuItem(), exitMenuItem);        
        menuBar.getMenus().addAll(homeMenu, helpMenu);
        root.setTop(menuBar);
        
        TabPane tabPane = new TabPane();

        Tab translatorTab = new Tab();
        
        translatorTab.setClosable(false);
        
        translatorTab.setText(objDictionaryAction.getWord("TRANSLATOR"));
        translatorTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTRANSLATOR")));
        //translatorTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/translation.png"));
        
        translatorGP = new GridPane();
        translatorGP.setId("container");
        //translatorGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //translatorGP.setAlignment(Pos.TOP_CENTER);
        translatorTab.setContent(translatorGP);
        tabPane.getTabs().add(translatorTab);
        
        //---- translator --//
        Label translator = new Label(objDictionaryAction.getWord("TRANSLATOR"));
        translator.setGraphic(new ImageView(objConfiguration.getStrColour()+"/translation.png"));
        translator.setId("caption");
        GridPane.setConstraints(translator, 0, 0);
        GridPane.setColumnSpan(translator, 5);
        translatorGP.getChildren().add(translator);
        
        translatorGP.add(new Text(objDictionaryAction.getWord("LANGUAGE")+" "+objDictionaryAction.getWord("NAME")),0,1,2,1);
        languageCB = new ComboBox();
        try{
            InputStream resourceAsStream = new FileInputStream(System.getProperty("user.dir")+"/mla/language/langs.properties"); //SimulatorView.class.getClassLoader().getResourceAsStream("language/langs.properties");;
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
        strLanguage="ENGLISH";
        languageCB.setPromptText(objDictionaryAction.getWord("SEARCHBY"));
        languageCB.setEditable(false); 
        languageCB.setValue("ENGLISH"); 
        languageCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                strLanguage=newValue;
                if(strLanguage=="ENGLISH" || strLanguage=="HINDI"){
                    btnSave.setDisable(true);
                    btnDelete.setDisable(true);
                } else {
                    btnSave.setDisable(false);
                    btnDelete.setDisable(false);
                }
                translatorAction("OPEN");
            }    
        });
        translatorGP.add(languageCB,2,1,3,1);
        
        Separator sepHor = new Separator();
        sepHor.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor, 0, 2);
        GridPane.setColumnSpan(sepHor, 5);
        translatorGP.getChildren().add(sepHor);
    
        GP_container = new GridPane();         
        GP_container.setAlignment(Pos.TOP_LEFT);
        GP_container.setHgap(2);
        GP_container.setVgap(2);
        GP_container.setPadding(new Insets(1, 3, 1, 1));
        
        translatorGP.add(new ImageView(objConfiguration.getStrColour()+"/loader.gif"),0,2,5,1);                                          
        //translatorGP.add(GP_container,0,2,5,1); 
        
        //MultiThread Start's here
        copyWorker = createWorker();
        /*
        progressB.setProgress(0);
        progressB.progressProperty().unbind();
        progressB.progressProperty().bind(copyWorker.progressProperty());
        copyWorker.messageProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
            }
        });
        */
        new Thread(copyWorker).start();
        
        Separator sepHor2 = new Separator();
        sepHor2.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor2, 0, 4);
        GridPane.setColumnSpan(sepHor2, 5);
        translatorGP.getChildren().add(sepHor2);
            
        btnCreate = new Button(objDictionaryAction.getWord("NEW"));
        btnSave = new Button(objDictionaryAction.getWord("SAVE"));
        btnSaveAs = new Button(objDictionaryAction.getWord("SAVEAS"));
        btnDelete = new Button(objDictionaryAction.getWord("DELETE"));
        btnClear = new Button(objDictionaryAction.getWord("CLEAR"));
        
        btnCreate.setGraphic(new ImageView(objConfiguration.getStrColour()+"/new.png"));
        btnSave.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
        btnSaveAs.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save_as.png"));
        btnDelete.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
        btnClear.setGraphic(new ImageView(objConfiguration.getStrColour()+"/clear.png"));

        btnCreate.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNEW")));
        btnSave.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVE")));
        btnSaveAs.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVEAS")));
        btnDelete.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDELETE")));
        btnClear.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLEAR")));

        btnCreate.setDisable(false);
        btnSave.setDisable(true);
        btnSaveAs.setDisable(false);
        btnDelete.setDisable(true);
        btnClear.setDisable(false);
        if(strLanguage=="ENGLISH" || strLanguage=="HINDI"){
            btnSave.setDisable(true);
            btnDelete.setDisable(true);
        } else {
            btnSave.setDisable(false);
        }
                
        translatorGP.add(btnCreate, 0, 5);
        translatorGP.add(btnSave, 1, 5);
        translatorGP.add(btnSaveAs, 2, 5);
        translatorGP.add(btnDelete, 3, 5);
        translatorGP.add(btnClear, 4, 5);
            
        btnCreate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                btnSave.setDisable(true);
                btnDelete.setDisable(true);
                strLanguage = "ENGLISH";
                translatorAction("CREATE");
            }
        });
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                translatorDbAction("SAVE");
            }
        });
        btnSaveAs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                translatorDbAction("SAVEAS");
            }
        });
        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.gc();
                translatorDbAction("DELETE");
            }
        });       
        btnClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.gc();
                translatorAction("CLEAR");
            }
        });       
        
        GridPane bodyContainer = new GridPane();
        bodyContainer.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        bodyContainer.setId("container");
        
        Label caption = new Label(objDictionaryAction.getWord("LANGUAGE")+" "+objDictionaryAction.getWord("TRANSLATOR")+" "+objDictionaryAction.getWord("UTILITY"));
        caption.setId("caption");
        bodyContainer.add(caption, 0, 0, 1, 1);
        
        ScrollPane container = new ScrollPane();
        tabPane.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        container.setContent(bodyContainer);
        
        bodyContainer.add(tabPane, 0, 1, 1, 1);
        /*
        final Separator sepHor = new Separator();
        sepHor.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor, 0, 2);
        GridPane.setColumnSpan(sepHor, 1);
        bodyContainer.getChildren().add(sepHor);
        */
        root.setCenter(container);
        
        translatorStage.getIcons().add(new Image("/media/icon.png"));
        translatorStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWFABRICSETTINGS")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        //translatorStage.setIconified(true);
        translatorStage.setResizable(false);
        translatorStage.setScene(scene);
        translatorStage.setX(0);
        translatorStage.setY(0);
        translatorStage.show();
        translatorStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
                scene.getStylesheets().add(TranslatorView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        translatorStage.close();
                        System.gc();
                        UtilityView objUtilityView = new UtilityView(objConfiguration);
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
 
    private void translatorAction(String strAction){
        try {
            GP_container.getChildren().clear();
            List lstLanguage=null;
            List lstLanguageDeatails = new ArrayList();
            lstLanguageDeatails = new DictionaryAction().lstImportLanguage(strLanguage);
            if(lstLanguageDeatails.size()==0){
                GP_container.getChildren().add(new Text(objDictionaryAction.getWord("LANGUAGE")+" - "+objDictionaryAction.getWord("NOVALUE")));
            }else{  
                for (int i=0, k=0; i<lstLanguageDeatails.size();i++, k+=2){
                    lstLanguage = (ArrayList)lstLanguageDeatails.get(i);
                    
                    Label lbl_key=new Label(lstLanguage.get(0).toString());
                    lbl_key.setTextFill(Color.BLUE);

                    Label lbl_unikey=new Label(objDictionaryAction.getWord(lstLanguage.get(0).toString()));
                    lbl_unikey.setFont(Font.font("Tahoma", FontWeight.NORMAL, 11));
                    lbl_unikey.setTextFill(Color.MAGENTA);

                    TextField txt_key=txt_key=new TextField("");
                    if(strAction.equalsIgnoreCase("OPEN")){
                        txt_key.setText(lstLanguage.get(1).toString());
                    } else if(strAction.equalsIgnoreCase("CLEAR")){
                        txt_key.setText("");
                    } else if(strAction.equalsIgnoreCase("CREATE")){
                        txt_key.setText("");
                    }
                    /*char[] the_unicode_char = new char[1];
                    try {
                        the_unicode_char[0] = (char)Integer.parseInt((String)objDictionaryAction.dictionary.get(key),16);
                        System.out.println(the_unicode_char);
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                    lbl_unikey.setText(new String(the_unicode_char));
                    */
                    GP_container.add(lbl_key, 1, i+1);
                    GP_container.add(txt_key, 2, i+1);
                    GP_container.add(lbl_unikey, 3, i+1);
                }
            }            
        } catch (Exception ex) {
            new Logging("SEVERE",SimulatorView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    private void translatorDbAction(String strAction){
        try {
            if(strAction.equalsIgnoreCase("DELETE")){
                // popup warn delete language
                final Stage popup=new Stage();
                popup.setTitle(objDictionaryAction.getWord("DELETE")+" "+objDictionaryAction.getWord("LANGUAGE"));
                GridPane popupGP=new GridPane();
                popupGP.setAlignment(Pos.CENTER);
                popupGP.setHgap(5);
                popupGP.setVgap(10);
                Label lblLang=new Label(objDictionaryAction.getWord("WARNDELETE")+" : "+strLanguage);
                Button btnOK=new Button(objDictionaryAction.getWord("DELETE"));
                Button btnCancel=new Button(objDictionaryAction.getWord("CANCEL"));
                popupGP.add(lblLang, 0, 0, 2, 1);
                popupGP.add(btnOK, 0, 1, 1, 1);
                popupGP.add(btnCancel, 1, 1, 1, 1);
                final Properties properties=new Properties();
                btnOK.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try{
                            InputStream resourceAsStream = new FileInputStream(System.getProperty("user.dir")+"/mla/language/langs.properties"); //SimulatorView.class.getClassLoader().getResourceAsStream("language/langs.properties");;
                            if (resourceAsStream != null) {
                                properties.load(resourceAsStream);
                                languageCB.getItems().clear();
                                for(Object k:properties.keySet()){
                                    String langName=(String)k;
                                    if(langName.equalsIgnoreCase(strLanguage)){
                                        properties.remove(k);
                                    }else{
                                        languageCB.getItems().add(langName);
                                    }
                                }
                            }
                            
                            File f=new File(System.getProperty("user.dir")+"/mla/language/"+strLanguage.toUpperCase()+".properties");
                            f.delete();
                            
                            f=new File(System.getProperty("user.dir")+"/mla/language/langs.properties");
                            f.delete();
                            
                            f=new File(System.getProperty("user.dir")+"/mla/language/langs.properties");
                            f.createNewFile();
                            //properties.store(new FileOutputStream(SimulatorView.class.getClassLoader().getResource("language/"+txtLang.getText()+".properties").getPath()), null);
                            properties.store(new FileOutputStream(f), null);
                            popup.close();
                            
                            strLanguage = "ENGLISH";
                            translatorAction("OPEN");
                        }catch(Exception ex){
                            new Logging("SEVERE",SimulatorView.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                            popup.close();
                        }
                    }
                });
                btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        popup.close();
                    }
                });
                Scene scene=new Scene(popupGP, 300, 200);
                popup.setScene(scene);
                popup.showAndWait();
            } else if(strAction.equalsIgnoreCase("SAVEAS")){
                // popup enter new language name
                final Stage popup=new Stage();
                popup.setTitle(objDictionaryAction.getWord("SAVE")+" "+objDictionaryAction.getWord("LANGUAGE"));
                GridPane popupGP=new GridPane();
                popupGP.setAlignment(Pos.CENTER);
                popupGP.setHgap(5);
                popupGP.setVgap(10);
                Label lblLang=new Label(objDictionaryAction.getWord("NAME"));
                Button btnOK=new Button(objDictionaryAction.getWord("SAVE"));
                Button btnCancel=new Button(objDictionaryAction.getWord("CANCEL"));
                final TextField txtLang=new TextField("");
                popupGP.add(lblLang, 0, 0, 2, 1);
                popupGP.add(txtLang, 0, 1, 2, 1);
                popupGP.add(btnOK, 0, 2, 1, 1);
                popupGP.add(btnCancel, 1, 2, 1, 1);
                final Properties properties=new Properties();
                btnOK.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try{
                            if(txtLang.getText().length()==0)
                                return;
                            String key="", value="";
                            for(int a=0; a<GP_container.getChildren().size(); a++){
                                if(GP_container.getChildren().get(a) instanceof TextField){
                                    value=((TextField)GP_container.getChildren().get(a)).getText();
                                    if(value.trim().length()!=0){
                                        key=((Label)GP_container.getChildren().get(a-1)).getText();
                                    }
                                    else{
                                        key=((Label)GP_container.getChildren().get(a-1)).getText();
                                        value=objDictionaryAction.getWord(key);
                                    }
                                    properties.setProperty(key, value);
                                }
                            }                        
                            File f=new File(System.getProperty("user.dir")+"/mla/language/"+txtLang.getText().toUpperCase()+".properties");
                            f.createNewFile();
                            //properties.store(new FileOutputStream(SimulatorView.class.getClassLoader().getResource("language/"+txtLang.getText()+".properties").getPath()), null);
                            properties.store(new FileOutputStream(f), null);
                            Files.write(Paths.get(System.getProperty("user.dir")+"/mla/language/langs.properties"), ("\n"+txtLang.getText().toUpperCase()).getBytes(), StandardOpenOption.APPEND);
                            popup.close();
                            languageCB.getItems().add(txtLang.getText());
                            languageCB.setValue(txtLang.getText());
                        }catch(Exception ex){
                            new Logging("SEVERE",SimulatorView.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                            popup.close();
                        }
                    }
                });
                btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        popup.close();
                    }
                });
                Scene scene=new Scene(popupGP, 300, 200);
                popup.setScene(scene);
                popup.showAndWait();
            } else if(strAction.equalsIgnoreCase("SAVE")){
                try{
                    Properties properties=new Properties();
                    String key="", value="";
                    for(int a=0; a<GP_container.getChildren().size(); a++){
                        if(GP_container.getChildren().get(a) instanceof TextField){
                            value=((TextField)GP_container.getChildren().get(a)).getText();
                            if(value.trim().length()!=0){
                                key=((Label)GP_container.getChildren().get(a-1)).getText();
                            }
                            else{
                                key=((Label)GP_container.getChildren().get(a-1)).getText();
                                value=objDictionaryAction.getWord(key);
                            }
                            properties.setProperty(key, value);
                        }
                    }
                    File f=new File(System.getProperty("user.dir")+"/mla/language/"+strLanguage+".properties");
                    properties.store(new FileOutputStream(f), null);
                }catch(Exception ex){
                    new Logging("SEVERE",SimulatorView.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        } catch (Exception ex) {
            new Logging("SEVERE",SimulatorView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        new TranslatorView(stage);
        new Logging("WARNING",TranslatorView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static void main(String[] args) {   
        launch(args);    
    }
    
    public Task createWorker() {
        return new Task() {
            @Override
            protected Object call() {
                try{
                    /*
                    int i=0;
                    while(isLoaded){
                        Thread.sleep(2000);
                        updateMessage("2000 milliseconds");
                        updateProgress(i + 1, 10);
                        i++;
                    }
                    */
                    translatorAction("OPEN");
                    succeeded();
                }catch(Exception ex){
                    new Logging("SEVERE",SimulatorView.class.getName(),ex.toString(),ex);
                    //lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
                return true;
            }
            @Override
            protected void succeeded() {
                translatorGP.add(GP_container,0,2,5,1);
            }
        };
    }
}