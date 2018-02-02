/*
 * Copyright (C) 2018 HP
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

package com.mla.colour;

import com.mla.colour.Colour;
import com.mla.colour.ColourAction;
import com.mla.dictionary.DictionaryAction;
import com.mla.main.AboutView;
import com.mla.main.Configuration;
import com.mla.main.ContactView;
import com.mla.main.DbUtility;
import com.mla.main.HelpView;
import com.mla.main.IDGenerator;
import com.mla.main.Logging;
import com.mla.main.MessageView;
import com.mla.main.TechnicalView;
import com.mla.utility.Color_Space_Converter;
import com.mla.utility.UtilityView;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
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
public class ColourChoserView extends Application {
    public static Stage exportImportStage;
    BorderPane root;
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;
    
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    
    private Menu homeMenu;
    private Menu helpMenu;

    TextField txtCollectionName;
    String colorFilePath;
    GridPane colorGP;
    
    private Button btnImportColor;
    private Button btnClearImport;
    private Button btnBrowseImport;
    private Button btnPreviewImport;

    String regExDouble="[0-9]{1,13}(\\.[0-9]*)?";
    List lstColour = null;
    
    public ColourChoserView(final Stage primaryStage) {}
    
    public ColourChoserView(Configuration objConfigurationCall) {
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);

        exportImportStage = new Stage(); 
        root = new BorderPane();
        Scene scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(ColourChoserView.class.getResource(objConfiguration.getStrTemplate()+"/setting.css").toExternalForm());
        
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
                scene.getStylesheets().add(ColourChoserView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
        menuBar.getMenus().addAll(homeMenu, helpMenu);
        root.setTop(menuBar);
        
        GridPane bodyContainer = new GridPane();
        bodyContainer.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        bodyContainer.setId("container");
        
        bodyContainer.add(new Label(objDictionaryAction.getWord("COLLECTIONNAME")), 0, 0);
        txtCollectionName = new TextField("");
        bodyContainer.add(txtCollectionName, 1, 0);
        
        Label importFile = new Label(objDictionaryAction.getWord("IMPORTFILE"));
        importFile.setGraphic(new ImageView(objConfiguration.getStrColour()+"/import.png"));
        importFile.setId("caption");
        bodyContainer.add(importFile,0,1);
        
        final TextField importPath = new TextField(objConfiguration.strRoot);
        importPath.setEditable(false);
        bodyContainer.add(importPath, 1, 1);
        
        btnBrowseImport = new Button(objDictionaryAction.getWord("BROWSE"));
        btnBrowseImport.setGraphic(new ImageView(objConfiguration.getStrColour()+"/browse.png"));
        bodyContainer.add(btnBrowseImport, 2, 1);
        
        btnPreviewImport = new Button(objDictionaryAction.getWord("PREVIEW"));
        btnPreviewImport.setGraphic(new ImageView(objConfiguration.getStrColour()+"/preview.png"));
        bodyContainer.add(btnPreviewImport, 3, 1);
        
        Separator sepHor1 = new Separator();
        sepHor1.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor1, 0, 2);
        GridPane.setColumnSpan(sepHor1, 4);
        bodyContainer.getChildren().add(sepHor1);
        
        ScrollPane container = new ScrollPane();
        colorGP = new GridPane();        
        
        container.setContent(colorGP);
        bodyContainer.add(container, 0, 3, 4, 1);
        
        Separator sepHor2 = new Separator();
        sepHor2.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor2, 0, 4);
        GridPane.setColumnSpan(sepHor2, 4);
        bodyContainer.getChildren().add(sepHor2);
        
        btnClearImport = new Button(objDictionaryAction.getWord("CLEAR"));
        btnClearImport.setGraphic(new ImageView(objConfiguration.getStrColour()+"/clear.png"));
        btnClearImport.setDisable(false);
        bodyContainer.add(btnClearImport, 0, 5, 2, 1);
        
        btnImportColor = new Button(objDictionaryAction.getWord("IMPORT"));
        btnImportColor.setGraphic(new ImageView(objConfiguration.getStrColour()+"/import.png"));
        btnImportColor.setDisable(false);
        bodyContainer.add(btnImportColor, 2, 5, 2, 1);
        
        Separator sepHor3 = new Separator();
        sepHor3.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor3, 0, 6);
        GridPane.setColumnSpan(sepHor3, 4);
        bodyContainer.getChildren().add(sepHor3);
        
        Label lblColorFileFormat=new Label();
        String strFormat="Acceptable Import Color File Formats:"
                +"\nComma Separated CSV: l,a,b"
                +"\nXLS Column Values: l, a, b"
                +"\nl,a,b values range: 0-255, For example: 20,2,5";
        lblColorFileFormat.setText(strFormat);
        bodyContainer.add(lblColorFileFormat, 0, 7, 4, 1);
        
        btnBrowseImport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                FileChooser objFileChooser = new FileChooser();
                objFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV(.csv),XLS(.xls),XLSX(.xlsx)","*.csv", "*.xls", "*.xlsx"));
                File selectedFile = objFileChooser.showOpenDialog(exportImportStage);
                if(selectedFile == null){
                    return;
                }else{
                    exportImportStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+selectedFile.getAbsolutePath()+"]");                        
                    btnClearImport.setDisable(false);
                    colorFilePath=selectedFile.getAbsolutePath();
                }
                importPath.setText(selectedFile.getAbsolutePath());
            }
        });       
        btnPreviewImport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if(txtCollectionName.getText().length()!=0&&colorFilePath.length()!=0){
                    if(insertColorsFromFile(colorFilePath, txtCollectionName.getText())){
                        colorGP.getChildren().clear();
                        lblStatus.setText(objDictionaryAction.getWord("FILEIMPORTED"));
                        btnClearImport.setDisable(false);
                    }else{
                        lblStatus.setText(objDictionaryAction.getWord("INVALIDDATA"));
                        btnClearImport.setDisable(true);
                    }
                }
                else{
                    lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                    btnClearImport.setDisable(true);
                }
            }
        });
        
        btnImportColor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.gc();
                /*
                objColourAction=new ColourAction();
                byte resultCode=objColourAction.setColour(objColour);
                if(resultCode==1)
                    fileValid=true;
                */
                colorGP.getChildren().clear();
                btnImportColor.setDisable(true);
                btnClearImport.setDisable(true);
            }
        });
        btnClearImport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.gc();
                colorGP.getChildren().clear();
                btnImportColor.setDisable(true);
                btnClearImport.setDisable(true);
            }
        });
        
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
                scene.getStylesheets().add(ColourChoserView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
    }
    
    public boolean insertColorsFromFile(String filePath, String collectionName){
        boolean fileValid=true;
        Colour objColour=null;
        ColourAction objColourAction=null;
        final Color_Space_Converter c=new Color_Space_Converter();
        final Color_Space_Converter.ColorSpaceConverter csc=c.new  ColorSpaceConverter();
        try{
            lstColour = new ArrayList();
            if(filePath.endsWith(".csv")||filePath.endsWith(".CSV")){
                //create BufferedReader to read csv file
                BufferedReader br = new BufferedReader(new FileReader(filePath));
                String line = "";
                StringTokenizer st = null;
                String token="";    

                int lineNumber = 0; 
                int tokenNumber = 0;

                int count=0,pos=0;
                //read comma separated file line by line
                while ((line = br.readLine()) != null) {
                    
                    lineNumber++;
                    String l="",a="",b="";
                    //use comma as token separator
                    st = new StringTokenizer(line, ",");
                    
                    if(st.hasMoreTokens())//L_CODE
                        l=st.nextToken();
                    else 
                        fileValid=false;
                    
                    if(st.hasMoreTokens())//A_CODE
                        a=st.nextToken();
                    else
                        fileValid=false;

                    if(st.hasMoreTokens())//B_CODE
                        b=st.nextToken();
                    else
                        fileValid=false;

                    if(l.matches(regExDouble) && a.matches(regExDouble) && b.matches(regExDouble) && (Double.parseDouble(l)>=0.0 || Double.parseDouble(l)<=100.0) && Double.parseDouble(a)>=-128.0 && Double.parseDouble(a)<=127.0 && Double.parseDouble(b)>=-128.0 && Double.parseDouble(b)<=127.0){
                        double[] lab=new double[]{Double.parseDouble(l), Double.parseDouble(a), Double.parseDouble(b)};
                        int[] rgb=csc.LABtoRGB(lab);
                        String txtHex = rgb2hex(rgb).toUpperCase();
                            
                        objColour=new Colour();
                        objColour.setObjConfiguration(objConfiguration);
                        objColour.setStrColorType(collectionName);
                        objColour.setStrColorName("lab-"+lineNumber);
                        objColour.setIntR(rgb[0]);
                        objColour.setIntG(rgb[0]);
                        objColour.setIntB(rgb[0]);
                        objColour.setStrColorHex(txtHex);
                        lstColour.add(objColour);
                        
                        final CheckBox checkColor = new CheckBox();
                        checkColor.setUserData(objColour);
                        checkColor.setSelected(true);
                        checkColor.selectedProperty().addListener(new ChangeListener<Boolean>() {
                            public void changed(ObservableValue<? extends Boolean> ov,
                                Boolean old_val, Boolean new_val) {
                                if(new_val){
                                    System.out.println("selected"+lstColour.size());
                                } else{
                                    System.out.println("not selected");
                                    System.out.println(lstColour.contains((Colour)checkColor.getUserData()));
                                    if(lstColour.contains((Colour)checkColor.getUserData()))
                                        lstColour.remove((Colour)checkColor.getUserData());
                                }
                            }
                        });
                        /*
                        BufferedImage bufferedImage = new BufferedImage(111,111,BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2d = bufferedImage.createGraphics();
                        g2d.setColor(java.awt.Color.red);
                        g2d.fillRect(0, 0, 111, 111);
                        g2d.dispose();
                        ImageView imageColor = new ImageView();
                        */
                        Label labelColor = new Label("hi"+count);
                        labelColor.setPrefSize(66, 66);
                        labelColor.setStyle("-fx-background-color:"+objColour.getStrColorHex());
                        colorGP.add(checkColor, (pos%6), count/3);
                        pos++;
                        colorGP.add(labelColor, (pos%6), count/3);
                        pos++;
                        count++;
                    } else{
                        fileValid=false;
                    }
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
                int count =0, pos=0;
                while (rowIterator.hasNext())
                {
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    Cell cell = null;
                    String l="",a="",b="";
                    
                    if(cellIterator.hasNext()){//L_CODE
                        cell=cellIterator.next();
                        l = (String)cell.getStringCellValue();
                    }
                    else
                        fileValid=false;

                    if(cellIterator.hasNext()){//A_CODE
                        cell=cellIterator.next();
                        a = (String)cell.getStringCellValue();
                    }
                    else
                        fileValid=false;

                    if(cellIterator.hasNext()){//B_CODE
                        cell=cellIterator.next();
                        b = (String)cell.getStringCellValue();
                    }
                    else
                        fileValid=false;
                    
                    if(l.matches(regExDouble) && a.matches(regExDouble) && b.matches(regExDouble) && (Double.parseDouble(l)>=0.0 || Double.parseDouble(l)<=100.0) && Double.parseDouble(a)>=-128.0 && Double.parseDouble(a)<=127.0 && Double.parseDouble(b)>=-128.0 && Double.parseDouble(b)<=127.0){
                        double[] lab=new double[]{Double.parseDouble(l), Double.parseDouble(a), Double.parseDouble(b)};
                        int[] rgb=csc.LABtoRGB(lab);
                        String txtHex = rgb2hex(rgb).toUpperCase();
                            
                        objColour=new Colour();
                        objColour.setObjConfiguration(objConfiguration);
                        objColour.setStrColorType(collectionName);
                        objColour.setStrColorName("lab-"+count);
                        objColour.setIntR(rgb[0]);
                        objColour.setIntG(rgb[0]);
                        objColour.setIntB(rgb[0]);
                        objColour.setStrColorHex(txtHex);
                        lstColour.add(objColour);
                        
                        final CheckBox checkColor = new CheckBox();
                        checkColor.setUserData(objColour);
                        checkColor.setSelected(true);
                        checkColor.selectedProperty().addListener(new ChangeListener<Boolean>() {
                            public void changed(ObservableValue<? extends Boolean> ov,
                                Boolean old_val, Boolean new_val) {
                                if(new_val){
                                    System.out.println("selected"+lstColour.size());
                                } else{
                                    System.out.println("not selected");
                                    System.out.println(lstColour.contains((Colour)checkColor.getUserData()));
                                    if(lstColour.contains((Colour)checkColor.getUserData()))
                                        lstColour.remove((Colour)checkColor.getUserData());
                                }
                            }
                        });
        
                        /*
                        BufferedImage bufferedImage = new BufferedImage(111,111,BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2d = bufferedImage.createGraphics();
                        g2d.setColor(java.awt.Color.red);
                        g2d.fillRect(0, 0, 111, 111);
                        g2d.dispose();
                        ImageView imageColor = new ImageView();
                        */
                        Label labelColor = new Label("hi"+count);
                        labelColor.setPrefSize(66, 66);
                        labelColor.setStyle("-fx-background-color:"+objColour.getStrColorHex());
                        colorGP.add(checkColor, (pos%6), count/3);
                        pos++;
                        colorGP.add(labelColor, (pos%6), count/3);
                        pos++;
                        count++;
                    }else{
                        fileValid=false;
                    }
                }
                file.close();
            }

        } catch (Exception ex) {
            new Logging("SEVERE",ColourChoserView.class.getName(),ex.toString(),ex);
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
    
    public String rgb2hex(int[] rgb){
        if(isRGBinvalid(rgb))
            return "#000000";
        else
            return String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
    }
        
    public boolean isRGBinvalid(int[] rgb){
        if(rgb==null || rgb.length!=3 || rgb[0]<0 || rgb[0]>255 || rgb[1]<0 || rgb[1]>255 || rgb[2]<0 || rgb[2]>255)
            return true;
        else
            return false;
    }
 
    @Override
    public void start(Stage stage) throws Exception {
        new ColourChoserView(stage);
        new Logging("WARNING",ColourChoserView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
/*
  public static void main(String[] args) {   
      launch(args);    
  }
  */
}
