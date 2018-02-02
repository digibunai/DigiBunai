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

import com.mla.artwork.Artwork;
import com.mla.artwork.ArtworkAction;
import com.mla.colour.Colour;
import com.mla.colour.ColourAction;
import com.mla.colour.ColourChoserView;
import com.mla.dictionary.DictionaryAction;
import com.mla.fabric.Fabric;
import com.mla.fabric.FabricAction;
import com.mla.main.AboutView;
import com.mla.main.Configuration;
import com.mla.main.ContactView;
import com.mla.main.DbUtility;
import com.mla.main.EncryptZip;
import com.mla.main.HelpView;
import com.mla.main.IDGenerator;
import com.mla.main.Logging;
import com.mla.main.MessageView;
import com.mla.main.TechnicalView;
import com.mla.weave.Weave;
import com.mla.weave.WeaveAction;
import com.mla.yarn.Yarn;
import com.mla.yarn.YarnAction;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @Designing GUI window for fabric preferences
 * @author Amit Kumar Singh
 * 
 */
public class ExportImportView extends Application {
    public static Stage exportImportStage;
    BorderPane root;
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;
    
    private FabricAction objFabricAction;
    private ArtworkAction objArtworkAction;
    private WeaveAction objWeaveAction;
    private Fabric objFabric;
    private Artwork objArtwork;
    private Weave objWeave;
    private Yarn objYarn;
    
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    
    private Menu homeMenu;
    private Menu helpMenu;

    CheckBox fabricDataCB;
    CheckBox artworkDataCB;
    CheckBox weaveDataCB;
    TextField pathExport;
    TextField pathImport;
    TextField txtCollectionName;
    String colorFilePath;
    
    private Button btnExport;
    private Button btnImport;
    private Button btnImportColor;
    private Button btnDelete;
    private Button btnExportDelete;
    private Button btnClearExport;
    private Button btnClearImport;
    private Button btnBrowseExport;
    private Button btnBrowseImport;
    private Button btnBrowseImportColor;
        
    public ExportImportView(final Stage primaryStage) {}
    
    public ExportImportView(Configuration objConfigurationCall) {
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);

        exportImportStage = new Stage(); 
        root = new BorderPane();
        Scene scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(ExportImportView.class.getResource(objConfiguration.getStrTemplate()+"/setting.css").toExternalForm());
        
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
        menuBar.prefWidthProperty().bind(exportImportStage.widthProperty());
        
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
                scene.getStylesheets().add(ExportImportView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        exportImportStage.close();
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
        Menu apiMenu  = new Menu();
        final HBox apiMenuHB = new HBox();
        apiMenuHB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/color_palette.png"),new Label(objDictionaryAction.getWord("COLOR")));
        apiMenu.setGraphic(apiMenuHB);
        apiMenuHB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                ColourChoserView objColourChoserView = new ColourChoserView(objConfiguration);
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
                exportImportStage.close();    
            }
        });    
        helpMenu.getItems().addAll(helpMenuItem, technicalMenuItem, aboutMenuItem, contactMenuItem, new SeparatorMenuItem(), exitMenuItem);        
        menuBar.getMenus().addAll(homeMenu, apiMenu, helpMenu);
        root.setTop(menuBar);
        
        ScrollPane container = new ScrollPane();
        
        TabPane tabPane = new TabPane();

        Tab exportTab = new Tab();
        Tab importTab = new Tab();
        Tab importColorTab = new Tab();
        Tab synchTab = new Tab();
        
        exportTab.setClosable(false);
        importTab.setClosable(false);
        importColorTab.setClosable(false);
        synchTab.setClosable(false);
        
        exportTab.setText(objDictionaryAction.getWord("EXPORTFILE"));
        importTab.setText(objDictionaryAction.getWord("IMPORTFILE"));
        importColorTab.setText(objDictionaryAction.getWord("IMPORTCOLORFILE"));
        synchTab.setText(objDictionaryAction.getWord("SYNCHFILE"));
        
        exportTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEXPORTFILE")));
        importTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPIMPORTFILE")));
        importColorTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPIMPORTCOLORFILE")));
        synchTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSYNCHFILE")));
        
        //exportTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/export.png"));
        //importTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/import.png"));
        //synchTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
        
        GridPane exportGP = new GridPane();
        GridPane importGP = new GridPane();
        GridPane importColorGP = new GridPane();
        GridPane synchGP = new GridPane();
        
        exportGP.setId("container");
        importGP.setId("container");
        importColorGP.setId("container");
        synchGP.setId("container");
        
        //exportGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //importGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //synchGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        
        //exportGP.setAlignment(Pos.TOP_CENTER);
        //importGP.setAlignment(Pos.TOP_CENTER);
        //synchGP.setAlignment(Pos.TOP_CENTER);
        
        exportTab.setContent(exportGP);
        importTab.setContent(importGP);
        importColorTab.setContent(importColorGP);
        synchTab.setContent(synchGP);
        
        tabPane.getTabs().add(exportTab);
        tabPane.getTabs().add(importTab);
        tabPane.getTabs().add(importColorTab);
        //tabPane.getTabs().add(synchTab);
        
        container.setContent(tabPane);
        
        //////---------Export Data-------//////
        Label exportFile = new Label(objDictionaryAction.getWord("EXPORTFILE"));
        exportFile.setGraphic(new ImageView(objConfiguration.getStrColour()+"/export.png"));
        exportFile.setId("caption");
        GridPane.setConstraints(exportFile, 0, 0);
        GridPane.setColumnSpan(exportFile, 4);
        exportGP.getChildren().add(exportFile);
        
        fabricDataCB = new CheckBox(objDictionaryAction.getWord("FABRIC")+" "+objDictionaryAction.getWord("DATA"));
        artworkDataCB = new CheckBox(objDictionaryAction.getWord("ARTWORK")+" "+objDictionaryAction.getWord("DATA"));
        weaveDataCB = new CheckBox(objDictionaryAction.getWord("WEAVE")+" "+objDictionaryAction.getWord("DATA"));
            
        fabricDataCB.setGraphic(new ImageView(objConfiguration.getStrColour()+"/fabric_library.png"));
        artworkDataCB.setGraphic(new ImageView(objConfiguration.getStrColour()+"/artwork_library.png"));
        weaveDataCB.setGraphic(new ImageView(objConfiguration.getStrColour()+"/weave_library.png"));
        
        fabricDataCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                Boolean old_val, Boolean new_val) {
                if(fabricDataCB.isSelected() || artworkDataCB.isSelected() || weaveDataCB.isSelected()){
                    btnExport.setDisable(false);
                    btnDelete.setDisable(false);
                    btnExportDelete.setDisable(false);
                    btnClearExport.setDisable(false);
                } else{
                    btnExport.setDisable(true);
                    btnDelete.setDisable(true);
                    btnExportDelete.setDisable(true);
                    btnClearExport.setDisable(false);
                    lblStatus.setText(objDictionaryAction.getWord("NOITEM"));
                }
            }
        });
        artworkDataCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                Boolean old_val, Boolean new_val) {
                if(fabricDataCB.isSelected() || artworkDataCB.isSelected() || weaveDataCB.isSelected()){
                    btnExport.setDisable(false);
                    btnDelete.setDisable(false);
                    btnExportDelete.setDisable(false);
                    btnClearExport.setDisable(false);
                } else{
                    btnExport.setDisable(true);
                    btnDelete.setDisable(true);
                    btnExportDelete.setDisable(true);
                    btnClearExport.setDisable(false);
                    lblStatus.setText(objDictionaryAction.getWord("NOITEM"));
                }
            }
        });            
        weaveDataCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                Boolean old_val, Boolean new_val) {
                if(fabricDataCB.isSelected() || artworkDataCB.isSelected() || weaveDataCB.isSelected()){
                    btnExport.setDisable(false);
                    btnDelete.setDisable(false);
                    btnExportDelete.setDisable(false);
                    btnClearExport.setDisable(false);
                } else{
                    btnExport.setDisable(true);
                    btnDelete.setDisable(true);
                    btnExportDelete.setDisable(true);
                    btnClearExport.setDisable(false);
                    lblStatus.setText(objDictionaryAction.getWord("NOITEM"));
                }
            }
        });
        
        exportGP.add(fabricDataCB, 0, 1);
        exportGP.add(artworkDataCB, 1, 1);
        exportGP.add(weaveDataCB, 2, 1, 2, 1);
            
        exportGP.add(new Label(objDictionaryAction.getWord("SAVEPATH")), 0, 2);
        pathExport = new TextField(objConfiguration.strRoot);
        pathExport.setEditable(false);
        exportGP.add(pathExport, 1, 2, 2, 1);
        btnBrowseExport = new Button(objDictionaryAction.getWord("BROWSE"));
        btnBrowseExport.setGraphic(new ImageView(objConfiguration.getStrColour()+"/browse.png"));
        exportGP.add(btnBrowseExport, 3, 2);
            
        btnBrowseExport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                DirectoryChooser directoryChooser=new DirectoryChooser();
                exportImportStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORT")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
                File selectedDirectory = directoryChooser.showDialog(exportImportStage);
                if(selectedDirectory == null)
                    return;
                else
                    exportImportStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+selectedDirectory.getAbsolutePath()+"]");    
                pathExport.setText(selectedDirectory.getPath());
            }
        });            

        Separator sepHor1 = new Separator();
        sepHor1.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor1, 0, 3);
        GridPane.setColumnSpan(sepHor1, 4);
        exportGP.getChildren().add(sepHor1);
            
        btnExport = new Button(objDictionaryAction.getWord("EXPORT"));
        btnDelete = new Button(objDictionaryAction.getWord("DELETE"));
        btnExportDelete = new Button(objDictionaryAction.getWord("EXPORTDELETE"));
        btnClearExport = new Button(objDictionaryAction.getWord("CLEAR"));
            
        btnExport.setGraphic(new ImageView(objConfiguration.getStrColour()+"/export.png"));
        btnExportDelete.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
        btnDelete.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
        btnClearExport.setGraphic(new ImageView(objConfiguration.getStrColour()+"/clear.png"));
                       
        btnExport.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEXPORT")));
        btnExportDelete.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEXPORTDELETE")));
        btnDelete.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDELETE")));
        btnClearExport.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLEAR")));
            
        btnExport.setDisable(true);
        btnExportDelete.setDisable(true);
        btnDelete.setDisable(true);
        btnClearExport.setDisable(false);
            
        btnExport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                new MessageView(objConfiguration);
                if(objConfiguration.getServicePasswordValid()){
                    objConfiguration.setServicePasswordValid(false);
                    if(fabricDataCB.isSelected() || artworkDataCB.isSelected() || weaveDataCB.isSelected()){
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                        Date date = new Date();
                        String currentDate = dateFormat.format(date);
                        String savePath = System.getProperty("user.dir");
                        if(pathExport.getText()!=null)
                            savePath = pathExport.getText();
                        savePath = savePath+"\\"+objDictionaryAction.getWord("PROJECT")+"_"+currentDate+"\\";
                        File file = new File(savePath);
                        if (!file.exists()) {
                            if (!file.mkdir())
                                savePath = System.getProperty("user.dir");
                        }
                        //savePath = savePath + "\\"+currentDate+".sql";

                        String zipFilePath=savePath+"Exported.bun";
                        savePath = savePath + "Exported.sql";
                        ArrayList<File> filesToZip=new ArrayList<>();

                        System.err.println("Path: "+savePath);                     
                        try (BufferedWriter bw = new BufferedWriter(new FileWriter(savePath))) {
                            btnExport.setDisable(false);
                            btnDelete.setDisable(false);
                            btnExportDelete.setDisable(false);
                            btnClearExport.setDisable(false);
							StringBuffer strExport=new StringBuffer();
                            strExport.append("SET NAMES utf8; \n USE bunai; \n SET autocommit = 0; \n");
                            //String content = "SET NAMES utf8; \n USE bunai; \n SET autocommit = 0; \n";
                            if(fabricDataCB.isSelected())
                                strExport.append(new DbUtility().reExportFabric(objConfiguration, savePath.substring(0, savePath.indexOf("Exported.sql")), filesToZip));
                                //content += new DbUtility().reExportFabric(objConfiguration, savePath.substring(0, savePath.indexOf("Exported.sql")), filesToZip);
                                //content += new DbUtility().exportFabric(objConfiguration);
                            if(artworkDataCB.isSelected())
                                strExport.append(new DbUtility().reExportDesign(objConfiguration, savePath.substring(0, savePath.indexOf("Exported.sql")), filesToZip));
                                //content += new DbUtility().reExportDesign(objConfiguration, savePath.substring(0, savePath.indexOf("Exported.sql")), filesToZip);
                                //content += new DbUtility().exportArtwork(objConfiguration);
                            if(weaveDataCB.isSelected())
                                strExport.append(new DbUtility().reExportWeave(objConfiguration, savePath.substring(0, savePath.indexOf("Exported.sql")), filesToZip));
                                //content += new DbUtility().reExportWeave(objConfiguration, savePath.substring(0, savePath.indexOf("Exported.sql")), filesToZip);
                                //content += new DbUtility().exportWeave(objConfiguration);
                            strExport.append("COMMIT;");
                            String content=strExport.toString();

                            bw.write(content);
                            // no need to close it.
                            bw.close();
                            filesToZip.add(new File(savePath));
                            new EncryptZip(zipFilePath, filesToZip, "somePassword");
                            for(int a=0; a<filesToZip.size(); a++)
                                filesToZip.get(a).delete();
                            System.out.println("Done");
                        } catch (IOException ex) {
                            new Logging("SEVERE",UtilityView.class.getName(),"Export Data"+ex.getMessage(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        } catch (SQLException ex) {
                            new Logging("SEVERE",UtilityView.class.getName(),"Export Data"+ex.getMessage(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                        lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+savePath);
                    } else{
                        btnExport.setDisable(true);
                        btnDelete.setDisable(true);
                        btnExportDelete.setDisable(true);
                        btnClearExport.setDisable(false);
                        lblStatus.setText(objDictionaryAction.getWord("NOITEM"));
                    }
                }
            }
        });
        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
               new MessageView(objConfiguration);
                if(objConfiguration.getServicePasswordValid()){
                    objConfiguration.setServicePasswordValid(false);
                    if(fabricDataCB.isSelected() || artworkDataCB.isSelected() || weaveDataCB.isSelected()){
                        try {
                            btnExport.setDisable(false);
                            btnDelete.setDisable(false);
                            btnExportDelete.setDisable(false);
                            btnClearExport.setDisable(false);

                            if(fabricDataCB.isSelected()){
                                objFabric = new Fabric();
                                objFabric.setObjConfiguration(objConfiguration);
                                objFabric.setStrCondition("");
                                objFabric.setStrSearchBy("");
                                objFabric.setStrOrderBy("Name");
                                objFabric.setStrDirection("Ascending");
                                List lstFabric=null;
                                List lstFabricDeatails = new ArrayList();
                                objFabricAction = new FabricAction();
                                lstFabricDeatails = objFabricAction.lstImportFabric(objFabric);
                                if(lstFabricDeatails.size()>0){
                                    for (int i=0, j = lstFabricDeatails.size(); i<j; i++){
                                        lstFabric = (ArrayList)lstFabricDeatails.get(i);
                                        String strFabricID = lstFabric.get(0).toString();
                                        String strFabricAccess = lstFabric.get(29).toString();
                                        if(!strFabricAccess.equalsIgnoreCase("Public")){
                                            objFabricAction = new FabricAction();
                                            objFabricAction.clearFabricArtwork(strFabricID);
                                            YarnAction objYarnAction = new YarnAction();
                                            objYarnAction.clearFabricYarn(strFabricID);
                                            objFabricAction = new FabricAction();
                                            objFabricAction.clearFabricPallets(strFabricID);
                                            objFabricAction = new FabricAction();
                                            objFabricAction.clearFabric(strFabricID);
                                        }
                                    }
                                }
                            }if(artworkDataCB.isSelected()){
                                objArtwork = new Artwork();
                                objArtwork.setObjConfiguration(objConfiguration);
                                objArtwork.setStrCondition("");
                                objArtwork.setStrSearchBy("");
                                objArtwork.setStrOrderBy("Name");
                                objArtwork.setStrDirection("Ascending");
                                List lstArtwork=null;
                                List lstArtworkDeatails = new ArrayList();
                                objArtworkAction = new ArtworkAction();
                                lstArtworkDeatails = objArtworkAction.lstImportArtwork(objArtwork);
                                if(lstArtworkDeatails.size()>0){
                                    for (int i=0, j = lstArtworkDeatails.size(); i<j;i++){
                                        lstArtwork = (ArrayList)lstArtworkDeatails.get(i);
                                        String strArtworkID = lstArtwork.get(0).toString();
                                        String strArtworkAccess = lstArtwork.get(6).toString();
                                        objArtworkAction = new ArtworkAction();
                                        System.err.println("ID= "+strArtworkID+"\nAccess= "+strArtworkAccess+"\nDefualt= "+new IDGenerator().getUserAcess("ARTWORK_LIBRARY")+"\nSize= "+objArtworkAction.countArtworkUsage(strArtworkID));
                                        if(objArtworkAction.countArtworkUsage(strArtworkID)==0 && !strArtworkAccess.equalsIgnoreCase(new IDGenerator().getUserAcess("ARTWORK_LIBRARY"))){
                                            objArtworkAction = new ArtworkAction();
                                            objArtworkAction.clearArtwork(strArtworkID);
                                        }
                                    }
                                }
                            }if(weaveDataCB.isSelected()){
                                objWeave = new Weave();
                                objWeave.setObjConfiguration(objConfiguration);
                                objWeave.setStrCondition("");
                                objWeave.setStrSearchBy("All");
                                objWeave.setStrOrderBy("Name");
                                objWeave.setStrDirection("Ascending");
                                List lstWeave=null;
                                List lstWeaveDeatails = new ArrayList();
                                objWeaveAction = new WeaveAction(objWeave,true);
                                lstWeaveDeatails = objWeaveAction.lstImportWeave(objWeave);
                                if(lstWeaveDeatails.size()>0){
                                    for (int i=0, j=lstWeaveDeatails.size();i<j;i++){
                                        lstWeave = (ArrayList)lstWeaveDeatails.get(i);
                                        String strWeaveID = lstWeave.get(0).toString();
                                        String strWeaveAccess = lstWeave.get(15).toString();
                                        objWeaveAction = new WeaveAction();
                                        System.err.println("ID= "+strWeaveID+"\nAccess= "+strWeaveAccess+"\nDefualt= "+new IDGenerator().getUserAcess("WEAVE_LIBRARY")+"\nSize= "+objWeaveAction.countWeaveUsage(strWeaveID));
                                        objWeaveAction = new WeaveAction();
                                        if(objWeaveAction.countWeaveUsage(strWeaveID)==0 && !strWeaveAccess.equalsIgnoreCase(new IDGenerator().getUserAcess("WEAVE_LIBRARY"))){
                                            objWeaveAction = new WeaveAction();
                                            objWeaveAction.clearWeave(strWeaveID);
                                        }
                                    }
                                }
                            }
                            lblStatus.setText(objDictionaryAction.getWord("ACTIONDELETE"));
                        } catch (SQLException ex) {
                            new Logging("SEVERE",UtilityView.class.getName(),"Delete Data"+ex.getMessage(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    } else{
                        btnExport.setDisable(true);
                        btnDelete.setDisable(true);
                        btnExportDelete.setDisable(true);
                        btnClearExport.setDisable(false);
                        lblStatus.setText(objDictionaryAction.getWord("NOITEM"));
                    }
                }
            }
        });
        btnExportDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                new MessageView(objConfiguration);
                if(objConfiguration.getServicePasswordValid()){
                    objConfiguration.setServicePasswordValid(false);
                    if(fabricDataCB.isSelected() || artworkDataCB.isSelected() || weaveDataCB.isSelected()){
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                        Date date = new Date();
                        String currentDate = dateFormat.format(date);
                        String savePath = System.getProperty("user.dir");
                        if(pathExport.getText()!=null)
                            savePath = pathExport.getText();
                        savePath = savePath+"\\"+objDictionaryAction.getWord("PROJECT")+"_"+currentDate+"\\";
                        File file = new File(savePath);
                        if (!file.exists()) {
                            if (!file.mkdir())
                                savePath = System.getProperty("user.dir");
                        }
                        //savePath = savePath + "\\"+currentDate+".sql";
                        String zipFilePath=savePath+"Exported.bun";
                        savePath = savePath + "Exported.sql";
                        ArrayList<File> filesToZip=new ArrayList<>();

                        System.err.println("Path: "+savePath);                     
                        try (BufferedWriter bw = new BufferedWriter(new FileWriter(savePath))) {
                            btnExport.setDisable(false);
                            btnDelete.setDisable(false);
                            btnExportDelete.setDisable(false);
                            btnClearExport.setDisable(false);
                            String content = "SET autocommit = 0;\n";
                            if(fabricDataCB.isSelected())
                                content += new DbUtility().reExportFabric(objConfiguration, savePath.substring(0, savePath.indexOf("Exported.sql")), filesToZip);
                                //content += new DbUtility().exportFabric(objConfiguration);
                            if(artworkDataCB.isSelected())
                                content += new DbUtility().reExportDesign(objConfiguration, savePath.substring(0, savePath.indexOf("Exported.sql")), filesToZip);
                                //content += new DbUtility().exportArtwork(objConfiguration);
                            if(weaveDataCB.isSelected())
                                content += new DbUtility().reExportWeave(objConfiguration, savePath.substring(0, savePath.indexOf("Exported.sql")), filesToZip);
                                //content += new DbUtility().exportWeave(objConfiguration);
                            content+="COMMIT;";
                            bw.write(content);
                            // no need to close it.
                            bw.close();
                            filesToZip.add(new File(savePath));
                            new EncryptZip(zipFilePath, filesToZip, "somePassword");
                            for(int a=0; a<filesToZip.size(); a++)
                                filesToZip.get(a).delete();
                            System.out.println("Backup Done");

                            if(fabricDataCB.isSelected()){
                                objFabric = new Fabric();
                                objFabric.setObjConfiguration(objConfiguration);
                                objFabric.setStrCondition("");
                                objFabric.setStrSearchBy("");
                                objFabric.setStrOrderBy("Name");
                                objFabric.setStrDirection("Ascending");
                                List lstFabric=null;
                                List lstFabricDeatails = new ArrayList();
                                objFabricAction = new FabricAction();
                                lstFabricDeatails = objFabricAction.lstImportFabric(objFabric);
                                if(lstFabricDeatails.size()>0){
                                    for (int i=0, j = lstFabricDeatails.size(); i<j; i++){
                                        lstFabric = (ArrayList)lstFabricDeatails.get(i);
                                        String strFabricID = lstFabric.get(0).toString();
                                        String strFabricAccess = lstFabric.get(29).toString();
                                        if(!strFabricAccess.equalsIgnoreCase("Public")){
                                            objFabricAction = new FabricAction();
                                            objFabricAction.clearFabricArtwork(strFabricID);
                                            YarnAction objYarnAction = new YarnAction();
                                            objYarnAction.clearFabricYarn(strFabricID);
                                            objFabricAction = new FabricAction();
                                            objFabricAction.clearFabricPallets(strFabricID);
                                            objFabricAction = new FabricAction();
                                            objFabricAction.clearFabric(strFabricID);
                                        }
                                    }
                                }
                            }if(artworkDataCB.isSelected()){
                                objArtwork = new Artwork();
                                objArtwork.setObjConfiguration(objConfiguration);
                                objArtwork.setStrCondition("");
                                objArtwork.setStrSearchBy("");
                                objArtwork.setStrOrderBy("Name");
                                objArtwork.setStrDirection("Ascending");
                                List lstArtwork=null;
                                List lstArtworkDeatails = new ArrayList();
                                objArtworkAction = new ArtworkAction();
                                lstArtworkDeatails = objArtworkAction.lstImportArtwork(objArtwork);
                                if(lstArtworkDeatails.size()>0){
                                    for (int i=0, j = lstArtworkDeatails.size(); i<j;i++){
                                        lstArtwork = (ArrayList)lstArtworkDeatails.get(i);
                                        String strArtworkID = lstArtwork.get(0).toString();
                                        String strArtworkAccess = lstArtwork.get(6).toString();
                                        objArtworkAction = new ArtworkAction();
                                        System.err.println("ID= "+strArtworkID+"\nAccess= "+strArtworkAccess+"\nDefualt= "+new IDGenerator().getUserAcess("ARTWORK_LIBRARY")+"\nSize= "+objArtworkAction.countArtworkUsage(strArtworkID));
                                        if(objArtworkAction.countArtworkUsage(strArtworkID)==0 && !strArtworkAccess.equalsIgnoreCase(new IDGenerator().getUserAcess("ARTWORK_LIBRARY"))){
                                            objArtworkAction = new ArtworkAction();
                                            objArtworkAction.clearArtwork(strArtworkID);
                                        }
                                    }
                                }
                            }if(weaveDataCB.isSelected()){
                                objWeave = new Weave();
                                objWeave.setObjConfiguration(objConfiguration);
                                objWeave.setStrCondition("");
                                objWeave.setStrSearchBy("All");
                                objWeave.setStrOrderBy("Name");
                                objWeave.setStrDirection("Ascending");  
                                List lstWeave=null;
                                List lstWeaveDeatails = new ArrayList();
                                objWeaveAction = new WeaveAction(objWeave,true);
                                lstWeaveDeatails = objWeaveAction.lstImportWeave(objWeave);
                                if(lstWeaveDeatails.size()>0){
                                    for (int i=0, j=lstWeaveDeatails.size();i<j;i++){
                                        lstWeave = (ArrayList)lstWeaveDeatails.get(i);
                                        String strWeaveID = lstWeave.get(0).toString();
                                        String strWeaveAccess = lstWeave.get(15).toString();
                                        objWeaveAction = new WeaveAction();
                                        System.err.println("ID= "+strWeaveID+"\nAccess= "+strWeaveAccess+"\nDefualt= "+new IDGenerator().getUserAcess("WEAVE_LIBRARY")+"\nSize= "+objWeaveAction.countWeaveUsage(strWeaveID));
                                        objWeaveAction = new WeaveAction();
                                        if(objWeaveAction.countWeaveUsage(strWeaveID)==0 && !strWeaveAccess.equalsIgnoreCase(new IDGenerator().getUserAcess("WEAVE_LIBRARY"))){
                                            objWeaveAction = new WeaveAction();
                                            objWeaveAction.clearWeave(strWeaveID);
                                        }
                                    }
                                }
                            }
                            lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+savePath);
                        } catch (IOException ex) {
                            new Logging("SEVERE",UtilityView.class.getName(),"Export and delete Data"+ex.getMessage(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        } catch (SQLException ex) {
                            new Logging("SEVERE",UtilityView.class.getName(),"Export and Delete Data"+ex.getMessage(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    } else{
                        btnExport.setDisable(true);
                        btnDelete.setDisable(true);
                        btnExportDelete.setDisable(true);
                        btnClearExport.setDisable(false);
                        lblStatus.setText(objDictionaryAction.getWord("NOITEM"));
                    }
                }
            }
        });
        btnClearExport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.gc();
                fabricDataCB.setSelected(false);
                artworkDataCB.setSelected(false);
                weaveDataCB.setSelected(false);
                btnExport.setDisable(true);
                btnDelete.setDisable(true);
                btnExportDelete.setDisable(true);
                btnClearExport.setDisable(false);
            }
        });
        exportGP.add(btnExport, 0, 4);
        exportGP.add(btnExportDelete, 1, 4);
        exportGP.add(btnDelete, 2, 4);
        exportGP.add(btnClearExport, 3, 4); 
        
        //////--------- import -------//////
        Label importFile = new Label(objDictionaryAction.getWord("IMPORTFILE"));
        importFile.setGraphic(new ImageView(objConfiguration.getStrColour()+"/import.png"));
        importFile.setId("caption");
        GridPane.setConstraints(importFile, 0, 0);
        GridPane.setColumnSpan(importFile, 3);
        importGP.getChildren().add(importFile);
        
        importGP.add(new Label(objDictionaryAction.getWord("DATAPATH")), 0, 1);
        pathImport = new TextField(objConfiguration.strRoot);
        pathImport.setEditable(false);
        importGP.add(pathImport, 1, 1);
        btnBrowseImport = new Button(objDictionaryAction.getWord("BROWSE"));
        btnBrowseImport.setGraphic(new ImageView(objConfiguration.getStrColour()+"/browse.png"));
        importGP.add(btnBrowseImport, 2, 1);
            
        Separator sepHor2 = new Separator();
        sepHor2.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor2, 0, 2);
        GridPane.setColumnSpan(sepHor2, 3);
        importGP.getChildren().add(sepHor2);
            
        btnImport = new Button(objDictionaryAction.getWord("IMPORT"));
        btnClearImport = new Button(objDictionaryAction.getWord("CLEAR"));

        btnImport.setGraphic(new ImageView(objConfiguration.getStrColour()+"/import.png"));
        btnClearImport.setGraphic(new ImageView(objConfiguration.getStrColour()+"/clear.png"));

        btnImport.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPIMPORT")));
        btnClearImport.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLEAR")));

        btnImport.setDisable(true);
        btnClearImport.setDisable(false);

        importGP.add(btnImport, 1, 3);
        importGP.add(btnClearImport, 2, 3);
            
        btnBrowseImport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                FileChooser objFileChooser = new FileChooser();
                objFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BUN(.bun)","*.bun"));
                File selectedFile = objFileChooser.showOpenDialog(exportImportStage);
                if(selectedFile == null){
                    return;
                }else{
                    exportImportStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+selectedFile.getAbsolutePath()+"]");                        
                    btnImport.setDisable(false);
                }
                pathImport.setText(selectedFile.getAbsolutePath());
            }
        });            
            
        btnImport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                new MessageView(objConfiguration);
                if(objConfiguration.getServicePasswordValid()){
                    objConfiguration.setServicePasswordValid(false);
                    if(pathImport.getText()!=null && pathImport.getText().toString().endsWith(".bun")){
                        try{
                            // unzip password protected zip with password "somePassword"
                            String source=pathImport.getText();
                            String destination="c:\\mla_cad\\archive\\";
                            String password="somePassword";
                            File f=new File(destination);
                            if(!f.exists()){
                                if(!f.mkdir()){
                                    return;
                                }
                            }
                            ZipFile zipFile = new ZipFile(source);
                            if (zipFile.isEncrypted()) {
                                zipFile.setPassword(password);
                            }
                            zipFile.extractAll(destination);
                            String[] files =getFolderContents(destination);
                            if(files!=null){
                                for(String sqlFile:files){
                                    if(sqlFile.endsWith("Exported.sql")){
                                        if(!new DbUtility().importSQLFileData(sqlFile)){
                                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                            return;
                                        }
                                        new File(sqlFile).delete();
                                        System.out.println("SQL File successfully imported!");
                                    }
                                }
                                for(String p:files){
                                    String q=p.substring(p.lastIndexOf("\\")+1);
                                    File fil=new File(p);
                                    if(q.startsWith("ARTWORK")){
                                        new DbUtility().updateArtworkBlob(fil);
                                    }
                                    else if(q.startsWith("WEAVE")){
                                        new DbUtility().updateWeaveBlob(fil);
                                    }
                                    else if(q.startsWith("FABRIC")){
                                        new DbUtility().updateFabricBlob(fil);
                                    }
                                    else if(q.startsWith("YARN")){
                                        new DbUtility().updateYarnBlob(fil);
                                    }
                                    fil.delete();
                                }
                                lblStatus.setText(objDictionaryAction.getWord("FILEIMPORTED"));
                            }
                        } catch (ZipException zipEx) {
                            new Logging("SEVERE",UtilityView.class.getName(),zipEx.toString(),zipEx);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        } catch (Exception ex) {
                            new Logging("SEVERE",UtilityView.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }else{
                        lblStatus.setText(objDictionaryAction.getWord("INVALIDDATA"));
                    }
                }
            }
        });
        btnClearImport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.gc();
                btnImport.setDisable(true);
                btnClearImport.setDisable(false);
            }
        });       
        //===== Synch ====//
        Label synchFile = new Label(objDictionaryAction.getWord("SYNCHFILE"));
        synchFile.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
        synchFile.setId("caption");
        GridPane.setConstraints(synchFile, 0, 0);
        GridPane.setColumnSpan(synchFile, 1);
        synchGP.getChildren().add(synchFile);
        
        
        GridPane bodyContainer = new GridPane();
        bodyContainer.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        bodyContainer.setId("container");
        
        Label caption = new Label(objDictionaryAction.getWord("EXPORT")+"-"+objDictionaryAction.getWord("IMPORT")+" "+objDictionaryAction.getWord("UTILITY"));
        caption.setId("caption");
        bodyContainer.add(caption, 0, 0, 1, 1);
        
        bodyContainer.add(tabPane, 0, 1, 1, 1);
        /*
        final Separator sepHor = new Separator();
        sepHor.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor, 0, 2);
        GridPane.setColumnSpan(sepHor, 1);
        bodyContainer.getChildren().add(sepHor);
        */
        root.setCenter(bodyContainer);
        
        exportImportStage.getIcons().add(new Image("/media/icon.png"));
        exportImportStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWFABRICSETTINGS")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        //exportImportStage.setIconified(true);
        exportImportStage.setResizable(false);
        exportImportStage.setScene(scene);
        exportImportStage.setX(0);
        exportImportStage.setY(0);
        exportImportStage.show();
        exportImportStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
                scene.getStylesheets().add(ExportImportView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        exportImportStage.close();
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
        
        // Import Color File Added 11 Oct 2017
        Label importColorFile = new Label(objDictionaryAction.getWord("IMPORTCOLORFILE"));
        importColorFile.setGraphic(new ImageView(objConfiguration.getStrColour()+"/import.png"));
        importColorFile.setId("caption");
        GridPane.setConstraints(importColorFile, 0, 0);
        GridPane.setColumnSpan(importColorFile, 2);
        importColorGP.getChildren().add(importColorFile);
        
        importColorGP.add(new Label(objDictionaryAction.getWord("COLLECTIONNAME")), 0, 1);
        txtCollectionName = new TextField("");
        importColorGP.add(txtCollectionName, 1, 1);
        btnBrowseImportColor = new Button(objDictionaryAction.getWord("BROWSE"));
        btnBrowseImportColor.setGraphic(new ImageView(objConfiguration.getStrColour()+"/browse.png"));
        importColorGP.add(btnBrowseImportColor, 0, 2);
         
        btnImportColor = new Button(objDictionaryAction.getWord("SUBMIT"));
        btnImportColor.setGraphic(new ImageView(objConfiguration.getStrColour()+"/import.png"));
        btnImportColor.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSUBMIT")));
        
        btnImportColor.setDisable(true);
        
        importColorGP.add(btnImportColor, 1, 3);
        colorFilePath="";
        btnBrowseImportColor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                FileChooser objFileChooser = new FileChooser();
                objFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV(.csv),XLS(.xls),XLSX(.xlsx)","*.csv", "*.xls", "*.xlsx"));
                File selectedFile = objFileChooser.showOpenDialog(exportImportStage);
                if(selectedFile == null){
                    return;
                }else{
                    exportImportStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+selectedFile.getAbsolutePath()+"]");                        
                    btnImportColor.setDisable(false);
                    colorFilePath=selectedFile.getAbsolutePath();
                }
            }
        });
        
        btnImportColor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if(txtCollectionName.getText().length()!=0&&colorFilePath.length()!=0){
                    if(insertColorsFromFile(colorFilePath, txtCollectionName.getText()))
                        lblStatus.setText(objDictionaryAction.getWord("FILEIMPORTED"));
                    else
                        lblStatus.setText(objDictionaryAction.getWord("INVALIDDATA"));
                }
                else{
                    lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                }
            }
        });
        
        Separator sepHor3 = new Separator();
        sepHor3.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor3, 0, 4);
        GridPane.setColumnSpan(sepHor3, 2);
        importColorGP.getChildren().add(sepHor3);
        
        Label lblColorFileFormat=new Label();
        String strFormat="Acceptable Import Color File Formats:"
                +"\nComma Separated CSV: Name,Red_Value,Green_Value,Blue_Value,HexCode,Code"
                +"\nXLS Column Values: Name, Red_Value, Green_Value, Blue_Value, HexCode, Code"
                +"\nRed,Green,Blue values range: 0-255, Hexcode example: #30fe18";
        lblColorFileFormat.setText(strFormat);
        importColorGP.add(lblColorFileFormat, 0, 5, 2, 3);
    }
    
    public boolean insertColorsFromFile(String filePath, String collectionName){
        boolean fileValid=false;
        Colour objColour=null;
        ColourAction objColourAction=null;
        try{
            if(filePath.endsWith(".csv")||filePath.endsWith(".CSV")){
                //create BufferedReader to read csv file
                BufferedReader br = new BufferedReader(new FileReader(filePath));
                String line = "";
                StringTokenizer st = null;
                String token="";    

                int lineNumber = 0; 
                int tokenNumber = 0;

                //read comma separated file line by line
                while ((line = br.readLine()) != null) {
                    objColour=new Colour();
                    objColour.setObjConfiguration(objConfiguration);
                    objColour.setStrColorType(collectionName);
                    lineNumber++;

                    //use comma as token separator
                    st = new StringTokenizer(line, ",");
                    if(st.hasMoreTokens()){
                        //NAME
                        token=st.nextToken();
                        objColour.setStrColorName((token!=null)?token:"");
                    }
                    else
                        return false;

                    if(st.hasMoreTokens()){//R_CODE
                        token=st.nextToken();
                        if(isColorValueValid(token))
                            objColour.setIntR(Integer.parseInt(token));
                        else
                            return false;
                    }
                    else
                        return false;

                    if(st.hasMoreTokens()){//G_CODE
                        token=st.nextToken();
                        if(isColorValueValid(token))
                            objColour.setIntG(Integer.parseInt(token));
                        else
                            return false;
                    }
                    else
                        return false;

                    if(st.hasMoreTokens()){//B_CODE
                        token=st.nextToken();
                        if(isColorValueValid(token))
                            objColour.setIntB(Integer.parseInt(token));
                        else
                            return false;
                    }
                    else
                        return false;

                    if(st.hasMoreTokens()){//HEXCODE
                        token=st.nextToken();
                        if(isHexCodeValid(token))
                            objColour.setStrColorHex(token);
                        else
                            objColour.setStrColorHex(toRGBCode(objColour));
                    }
                    else
                        return false;

                    if(st.hasMoreTokens()){//CODE
                        token=st.nextToken();
                        objColour.setStrColorCode((token!=null)?token:"");
                    }
                    else
                        return false;

                    /*while (st.hasMoreTokens()) {
                        tokenNumber++;

                        //display csv values
                        System.out.print(st.nextToken() + "  ");
                    }
                    System.out.println();
                    //reset token number
                    tokenNumber = 0;*/
                    objColourAction=new ColourAction();
                    byte resultCode=objColourAction.setColour(objColour);
                    if(resultCode==1)
                        fileValid=true;
                }
            }
            else if(filePath.endsWith(".xls")||filePath.endsWith(".XLS")||filePath.endsWith(".xlsx")||filePath.endsWith(".XLSX")){
                FileInputStream file = new FileInputStream(filePath);
                Iterator<Row> rowIterator=null;
                String token="";
                int i=0;
                if(filePath.endsWith(".xls")||filePath.endsWith(".XLS")){
                    //Create Workbook instance holding reference to .xls file
                    HSSFWorkbook workbook = new HSSFWorkbook(file);
                    //Get first/desired sheet from the workbook
                    HSSFSheet sheet = workbook.getSheetAt(0);
                    rowIterator = sheet.iterator();
                }
                else{
                    //Create Workbook instance holding reference to .xlsx file
                    XSSFWorkbook workbook = new XSSFWorkbook(file);
                    //Get first/desired sheet from the workbook
                    XSSFSheet sheet = workbook.getSheetAt(0);
                    rowIterator = sheet.iterator();
                }

                while (rowIterator.hasNext())
                {
                    Row row = rowIterator.next();
                    objColour=new Colour();
                    objColour.setObjConfiguration(objConfiguration);
                    objColour.setStrColorType(collectionName);
                    Iterator<Cell> cellIterator = row.cellIterator();
                    
                    Cell cell = null;
                    if(cellIterator.hasNext()){
                        //NAME
                        cell=cellIterator.next();
                        token = cell.getStringCellValue();
                        objColour.setStrColorName((token!=null)?token:"");
                    }
                    else
                        return false;

                    if(cellIterator.hasNext()){//R_CODE
                        cell=cellIterator.next();
                        i = (int)cell.getNumericCellValue();
                        if(i>=0&&i<=255)
                            objColour.setIntR(i);
                        else
                            return false;
                    }
                    else
                        return false;

                    if(cellIterator.hasNext()){//G_CODE
                        cell=cellIterator.next();
                        i = (int)cell.getNumericCellValue();
                        if(i>=0&&i<=255)
                            objColour.setIntG(i);
                        else
                            return false;
                    }
                    else
                        return false;

                    if(cellIterator.hasNext()){//B_CODE
                        cell=cellIterator.next();
                        i = (int)cell.getNumericCellValue();
                        if(i>=0&&i<=255)
                            objColour.setIntB(i);
                        else
                            return false;
                    }
                    else
                        return false;

                    if(cellIterator.hasNext()){//HEXCODE
                        cell=cellIterator.next();
                        token = cell.getStringCellValue();
                        if(isHexCodeValid(token))
                            objColour.setStrColorHex(token);
                        else
                            objColour.setStrColorHex(toRGBCode(objColour));
                    }
                    else
                        return false;

                    if(cellIterator.hasNext()){//CODE
                        cell=cellIterator.next();
                        token = cell.getStringCellValue();
                        objColour.setStrColorCode((token!=null)?token:"");
                    }
                    else
                        return false;
                    
                    //For each row, iterate through all the columns
                    /*Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) 
                    {
                        Cell cell = cellIterator.next();
                        //Check the cell type and format accordingly
                        switch (cell.getCellType()) 
                        {
                            case Cell.CELL_TYPE_NUMERIC:
                                System.out.print(cell.getNumericCellValue() + "\t");
                                break;
                            case Cell.CELL_TYPE_STRING:
                                System.out.print(cell.getStringCellValue() + "\t");
                                break;
                        }
                    }*/
                    objColourAction=new ColourAction();
                    byte resultCode=objColourAction.setColour(objColour);
                    if(resultCode==1)
                        fileValid=true;
                }
                file.close();
            }

        } catch (SQLException sqlEx) {
            new Logging("SEVERE",ExportImportView.class.getName(),sqlEx.toString(),sqlEx);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",ExportImportView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
        
        return fileValid;
    }
    
    public String toRGBCode( Colour objColour ){
        return String.format( "#%02X%02X%02X",
            (int)( objColour.getIntR()),
            (int)( objColour.getIntG()),
            (int)( objColour.getIntB()) );
    }
    
    public boolean isHexCodeValid(String value){
        boolean isValid=false;
        if(value!=null&&value.length()==7){
            int i=0;
            if(value.startsWith("#")){
                for(i=1; i<7; i++){
                    if(!(value.substring(i, i+1).matches("[0-9]*")||value.charAt(i)=='a'
                            ||value.charAt(i)=='b'||value.charAt(i)=='c'||value.charAt(i)=='d'
                            ||value.charAt(i)=='e'||value.charAt(i)=='f'))
                        break;
                }
                if(i==7)
                    isValid=true;
            }
        }
        return isValid;
    }
    
    public boolean isColorValueValid(String value){
        boolean isValid=false;
        try{
            int i=Integer.parseInt(value);
            if(i>=0&&i<=255)
                isValid=true;
        }
        catch(Exception ex){}
        return isValid;
    }
 
    /* Get all files from destination Extracted folder */
    public String[] getFolderContents(String folder){
	// returns absolute path of files
        File dir = new File(folder);
        final String[] exts={".sql", ".png"};
	String[] files=dir.list(new FilenameFilter() { 
            public boolean accept(File dir, String filename){
                for(String e: exts){
                    if(filename.endsWith(e))
                            return true;
                }
                return false;
            }
	});
	
	if(files!=null){
            // returns complete path of files
            for(int i=0; i<files.length; i++){
                    files[i]=folder+files[i];
            }
	}
	return files;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        new ExportImportView(stage);
        new Logging("WARNING",ExportImportView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 /*
  public static void main(String[] args) {   
      launch(args);    
  }
  */
}