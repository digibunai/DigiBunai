/*
 * Copyright (C) 2016 Media Lab Asia
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

import com.mla.dictionary.DictionaryAction;
import com.mla.fabric.FabricAction;
import com.mla.fabric.FabricSettingView;
import com.mla.main.Configuration;
import com.mla.main.IDGenerator;
import com.mla.main.Logging;
import com.mla.main.WindowView;
import com.mla.utility.Palette;
import com.mla.utility.UtilityAction;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 *
 * @Designing GUI window for color pallete
 * @author Amit Kumar Singh
 * 
 */
public class ColorPaletteView {
    public static Stage colorStage;
    BorderPane root;
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;
    
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    
    private Menu homeMenu;
    private Menu helpMenu;

    private Palette objPalette;

    GridPane container;
    ScrollPane colorSP;
    
    private Button btnSave;
    private Button btnSaveAs;
    private Button btnReset;
    private Button btnCancel;
    
    
    private Label pLbl[];
    private ComboBox paletteCB;
    private String[] threadPaletes; //= new String[]{"585858", "B40431", "B4045F", "B40486", "B404AE", "8904B1", "5F04B4", "3104B4", "0404B4", "0431B4", "045FB4", "0489B1", "04B4AE", "04B486", "04B45F", "04B431", "04B404", "31B404", "5FB404", "86B404", "AEB404", "B18904", "B45F04", "B43104", "B40404", "FFFFFF", "585858", "B40431", "B4045F", "B40486", "B404AE", "8904B1", "5F04B4", "3104B4", "0404B4", "0431B4", "045FB4", "0489B1", "04B4AE", "04B486", "04B45F", "04B431", "04B404", "31B404", "5FB404", "86B404", "AEB404", "B18904", "B45F04", "B43104", "B40404", "FFFFFF"}; 
    //private ArrayList tempPalette;
    private String[][] colorPaletes;
    Map<String,String> warpPattern = new HashMap<>();
    Map<String,String> weftPattern = new HashMap<>();
    GridPane colorGP;
    GridPane weftGP;
    GridPane warpGP;
    byte xindex =-1, yindex=-1;
    ArrayList lstColor=null;
    
    public ColorPaletteView(final Stage primaryStage) {  }

    public ColorPaletteView(Configuration objConfigurationCall) throws SQLException, Exception {   
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);

        colorStage = new Stage(); 
        root = new BorderPane();
        Scene scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(FabricSettingView.class.getResource(objConfiguration.getStrTemplate()+"/setting.css").toExternalForm());
        
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

        objPalette = new Palette();
        objPalette.setStrCondition("");
        objPalette.setStrOrderBy("Type");
        objPalette.setStrSearchBy("");
        objPalette.setStrDirection("Ascending");
        objPalette.setObjConfiguration(objConfiguration);
        
        threadPaletes = objConfiguration.getColourPalette();
        
        
        container = new GridPane();
        container.setVgap(5);
        container.setAlignment(Pos.CENTER);
        //container.setGridLinesVisible(true);
        container.autosize();
        
        Label colorType= new Label(objDictionaryAction.getWord("COLORTYPE")+" :");
        //colorType.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColor()+"/icontooltip.png"));
        colorType.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOLORTYPE")));
        container.add(colorType, 0, 0);
        final ComboBox colourTypeCB = new ComboBox();
        colourTypeCB.getItems().addAll("Pantone"/*,"Web Color"*/);
        colourTypeCB.setPromptText("Pantone");
        colourTypeCB.setDisable(true);
        String strColorType = "Pantone";//objFabric.getStrColorType();
        colourTypeCB.setValue(strColorType);
        colourTypeCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOLORTYPE")));
        colourTypeCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objPalette.setStrCondition(newValue);
                populateColour(); 
            }    
        });
        container.add(colourTypeCB, 1, 0);
        
        Label colourSort = new Label(objDictionaryAction.getWord("SORTBY"));
        colorType.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSORTBY")));
        container.add(colourSort,2,0);
        final ComboBox  colourSortCB = new ComboBox();
        colourSortCB.getItems().addAll(/*"Name",*/"Date","Type","Hex Code","Pantone Code","Red","Green","Blue");
        colourSortCB.setPromptText(objDictionaryAction.getWord("SORTBY"));
        colourSortCB.setEditable(false); 
        //colourSortCB.setValue("Name"); 
        colourSortCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objPalette.setStrOrderBy(newValue);
                populateColour(); 
            }    
        });
        container.add(colourSortCB,3,0);
        
        TextField txtSearch=new TextField();
        txtSearch.setPromptText("Search by Name/Hex Code/Pantone Code");
        container.add(txtSearch, 4, 0);
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                objPalette.setStrCondition(t1);
                populateColour();
            }
        });
        
        pLbl=new Label[52];
        for(int i=0; i<52; i++){
            pLbl[i]=new Label();
            addDragNDrop(pLbl[i]);
        }
        
        colorSP=new ScrollPane();
        colorGP = new GridPane();
        colorSP.setPrefHeight(400);
        populateColour();
        colorSP.setContent(colorGP);
        container.add(colorSP, 0, 1, 5, 1);
        
        //threadPaletes = objFabric.getThreadPaletes();
        //threadPaletes = objFabric.getThreadPaletes();
        colorPaletes = new String[52][2];
        setColorPaletes();
        
        // added Palette Name
        Label paletteLbl= new Label(objDictionaryAction.getWord("PALETTE")+" :");
        paletteLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPALETTE")));
        container.add(paletteLbl, 0, 2, 1, 1);
        paletteCB = new ComboBox();
        paletteCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPALETTE")));
        container.add(paletteCB, 1, 2, 2, 1);
        loadPaletteNames();
        paletteCB.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                reloadColour((String)t1);
                btnSave.setDisable(false);
            }
        });
        
        Label warpCL = new Label("Warp Palette");
        warpCL.setFont(Font.font("Arial", 14));
        container.add(warpCL, 0, 3);
        warpGP = new GridPane();        
        container.add(warpGP, 1, 3, 4, 1);
        
        Label weftCL = new Label("Weft Pallate");
        weftCL.setFont(Font.font("Arial", 14));
        container.add(weftCL, 0, 4);
        weftGP = new GridPane();        
        container.add(weftGP, 1, 4, 4, 1);
        
        populateYarnPalette();
        
        btnSave=new Button("Save");
        btnSave.setTooltip(new Tooltip("Click to update Color Palatte"));
        container.add(btnSave, 0, 5, 1, 1);
        btnSave.setDisable(true);
        btnSaveAs=new Button("SaveAs");
        btnSaveAs.setTooltip(new Tooltip("Click to save new Color Palatte"));
        container.add(btnSaveAs, 1, 5, 1, 1);
        btnCancel = new Button("Cancel");
        btnCancel.setTooltip(new Tooltip("Click to close for fabric"));
        //btnCancel.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColor()+"/close.png"));
        container.add(btnCancel, 3, 5, 1, 1);
        btnReset = new Button("Reset");
        btnReset.setTooltip(new Tooltip("Click to Reset for fabric"));
        //btnReset.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColor()+"/clear.png"));
        container.add(btnReset, 2, 5, 1, 1);
        
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                for(int i = 0; i<52; i++){
                    colorPaletes[i][1]=pLbl[i].getId().substring(1); // removing '#'
                    threadPaletes[i]=colorPaletes[i][1];
                }
                objConfiguration.setColourPalette(threadPaletes);
                objPalette.setStrThreadPalette(threadPaletes);
                try{
                    UtilityAction objUtilityAction=new UtilityAction();
                    objUtilityAction.resetPalette(objPalette);
                }
                catch(SQLException sqlEx){
                    Logger.getLogger(ColorPaletteView.class.getName()).log(Level.SEVERE, null, sqlEx);
                }
                loadPaletteNames();
                System.gc();
                colorStage.close();
            }
        });
        btnSaveAs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                final Stage dialogStage = new Stage();
                dialogStage.initStyle(StageStyle.UTILITY);
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                GridPane popup=new GridPane();
                popup.setId("popup");
                popup.setHgap(5);
                popup.setVgap(5);

                final TextField txtName = new TextField();
                Label lblName = new Label(objDictionaryAction.getWord("NAME"));
                lblName.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNAME")));
                popup.add(lblName, 0, 0);
                txtName.setPromptText(objDictionaryAction.getWord("TOOLTIPNAME"));
                txtName.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNAME")));
                popup.add(txtName, 1, 0, 2, 1);
                
                final ToggleGroup paletteTG = new ToggleGroup();
                RadioButton palettePublicRB = new RadioButton(objDictionaryAction.getWord("PUBLIC"));
                palettePublicRB.setToggleGroup(paletteTG);
                palettePublicRB.setUserData("Public");
                popup.add(palettePublicRB, 0, 1);
                RadioButton palettePrivateRB = new RadioButton(objDictionaryAction.getWord("PRIVATE"));
                palettePrivateRB.setToggleGroup(paletteTG);
                palettePrivateRB.setUserData("Private");
                popup.add(palettePrivateRB, 1, 1);
                RadioButton paletteProtectedRB = new RadioButton(objDictionaryAction.getWord("PROTECTED"));
                paletteProtectedRB.setToggleGroup(paletteTG);
                paletteProtectedRB.setUserData("Protected");
                popup.add(paletteProtectedRB, 2, 1);
                if(objConfiguration.getObjUser().getUserAccess("PALETTE_LIBRARY").equalsIgnoreCase("Public"))
                    paletteTG.selectToggle(palettePublicRB);
                else if(objConfiguration.getObjUser().getUserAccess("PALETTE_LIBRARY").equalsIgnoreCase("Protected"))
                    paletteTG.selectToggle(paletteProtectedRB);
                else
                    paletteTG.selectToggle(palettePrivateRB);

                Button btnOK = new Button(objDictionaryAction.getWord("SAVE"));
                btnOK.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVE")));
                btnOK.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
                btnOK.setDefaultButton(true);
                btnOK.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) { 
                        objPalette.setStrPaletteID(new IDGenerator().getIDGenerator("PALETTE_LIBRARY", objConfiguration.getObjUser().getStrUserID()));
                        for(int i = 0; i<52; i++){
                            colorPaletes[i][1]=pLbl[i].getId().substring(1); // removing '#'
                            threadPaletes[i]=colorPaletes[i][1];
                        }
                        objPalette.setStrThreadPalette(threadPaletes);
                        objPalette.setStrPaletteAccess(paletteTG.getSelectedToggle().getUserData().toString());
                        if(txtName.getText()!=null && txtName.getText()!="" && txtName.getText().trim().length()!=0)
                                objPalette.setStrPaletteName(txtName.getText().toString());
                        else
                            objPalette.setStrPaletteName("My Palette");                        
                        try{
                            UtilityAction objUtilityAction=new UtilityAction();
                            objUtilityAction.setPalette(objPalette);
                        }
                        catch(SQLException sqlEx){
                            Logger.getLogger(ColorPaletteView.class.getName()).log(Level.SEVERE, null, sqlEx);
                        }
                        dialogStage.close();
                        loadPaletteNames();
                        objConfiguration.setColourPalette(threadPaletes);               
                    }
                });
                popup.add(btnOK, 1, 3);
                Button btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
                btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
                btnCancel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
                btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {  
                        dialogStage.close();
                        lblStatus.setText(objDictionaryAction.getWord("TOOLTIPCANCEL"));
                   }
                });
                popup.add(btnCancel, 0, 3);
                Scene scene = new Scene(popup, 300, 220);
                scene.getStylesheets().add(ColorPaletteView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                dialogStage.setScene(scene);
                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("SAVE"));
                dialogStage.showAndWait();
            }
        });
        btnReset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                setColorPaletes();
                populateColour();
                populateYarnPalette();
                System.gc();
            }
        });
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.gc();
                colorStage.close();
            }
        });        
        
        root.setCenter(container);
        colorStage = new Stage(); 
        colorStage.setScene(scene);
        colorStage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL
        colorStage.initStyle(StageStyle.UTILITY); 
        colorStage.getIcons().add(new Image("/media/icon.png"));
        colorStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWCOLORPALETTE")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        colorStage.setIconified(false);
        colorStage.setResizable(false);
        colorStage.showAndWait();
    }
    
    /*public void drawPalette(){
        String temp = "";
        for(int i = 0, j=tempPalette.size(); i<j; i++)
            temp+=tempPalette.get(i).toString();
    }*/
    public void setColorPaletes(){
        colorPaletes[0][0]="A";
        colorPaletes[0][1]=threadPaletes[0];
        colorPaletes[1][0]="B";
        colorPaletes[1][1]=threadPaletes[1];
        colorPaletes[2][0]="C";
        colorPaletes[2][1]=threadPaletes[2];
        colorPaletes[3][0]="D";
        colorPaletes[3][1]=threadPaletes[3];
        colorPaletes[4][0]="E";
        colorPaletes[4][1]=threadPaletes[4];
        colorPaletes[5][0]="F";
        colorPaletes[5][1]=threadPaletes[5];
        colorPaletes[6][0]="G";
        colorPaletes[6][1]=threadPaletes[6];
        colorPaletes[7][0]="H";
        colorPaletes[7][1]=threadPaletes[7];
        colorPaletes[8][0]="I";
        colorPaletes[8][1]=threadPaletes[8];
        colorPaletes[9][0]="J";
        colorPaletes[9][1]=threadPaletes[9];
        colorPaletes[10][0]="K";
        colorPaletes[10][1]=threadPaletes[10];
        colorPaletes[11][0]="L";
        colorPaletes[11][1]=threadPaletes[11];
        colorPaletes[12][0]="M";
        colorPaletes[12][1]=threadPaletes[12];
        colorPaletes[13][0]="N";
        colorPaletes[13][1]=threadPaletes[13];
        colorPaletes[14][0]="O";
        colorPaletes[14][1]=threadPaletes[14];
        colorPaletes[15][0]="P";
        colorPaletes[15][1]=threadPaletes[15];
        colorPaletes[16][0]="Q";
        colorPaletes[16][1]=threadPaletes[16];
        colorPaletes[17][0]="R";
        colorPaletes[17][1]=threadPaletes[17];
        colorPaletes[18][0]="S";
        colorPaletes[18][1]=threadPaletes[18];
        colorPaletes[19][0]="T";
        colorPaletes[19][1]=threadPaletes[19];
        colorPaletes[20][0]="U";
        colorPaletes[20][1]=threadPaletes[20];
        colorPaletes[21][0]="V";
        colorPaletes[21][1]=threadPaletes[21];
        colorPaletes[22][0]="W";
        colorPaletes[22][1]=threadPaletes[22];
        colorPaletes[23][0]="X";
        colorPaletes[23][1]=threadPaletes[23];
        colorPaletes[24][0]="Y";
        colorPaletes[24][1]=threadPaletes[24];
        colorPaletes[25][0]="Z";
        colorPaletes[25][1]=threadPaletes[25];
        colorPaletes[26][0]="a";
        colorPaletes[26][1]=threadPaletes[26];
        colorPaletes[27][0]="b";
        colorPaletes[27][1]=threadPaletes[27];
        colorPaletes[28][0]="c";
        colorPaletes[28][1]=threadPaletes[28];
        colorPaletes[29][0]="d";
        colorPaletes[29][1]=threadPaletes[29];
        colorPaletes[30][0]="e";
        colorPaletes[30][1]=threadPaletes[30];
        colorPaletes[31][0]="f";
        colorPaletes[31][1]=threadPaletes[31];
        colorPaletes[32][0]="g";
        colorPaletes[32][1]=threadPaletes[32];
        colorPaletes[33][0]="h";
        colorPaletes[33][1]=threadPaletes[33];
        colorPaletes[34][0]="i";
        colorPaletes[34][1]=threadPaletes[34];
        colorPaletes[35][0]="j";
        colorPaletes[35][1]=threadPaletes[35];
        colorPaletes[36][0]="k";
        colorPaletes[36][1]=threadPaletes[36];
        colorPaletes[37][0]="l";
        colorPaletes[37][1]=threadPaletes[37];
        colorPaletes[38][0]="m";
        colorPaletes[38][1]=threadPaletes[38];
        colorPaletes[39][0]="n";
        colorPaletes[39][1]=threadPaletes[39];
        colorPaletes[40][0]="o";
        colorPaletes[40][1]=threadPaletes[40];
        colorPaletes[41][0]="p";
        colorPaletes[41][1]=threadPaletes[41];
        colorPaletes[42][0]="q";
        colorPaletes[42][1]=threadPaletes[42];
        colorPaletes[43][0]="r";
        colorPaletes[43][1]=threadPaletes[43];
        colorPaletes[44][0]="s";
        colorPaletes[44][1]=threadPaletes[44];
        colorPaletes[45][0]="t";
        colorPaletes[45][1]=threadPaletes[45];
        colorPaletes[46][0]="u";
        colorPaletes[46][1]=threadPaletes[46];
        colorPaletes[47][0]="v";
        colorPaletes[47][1]=threadPaletes[47];
        colorPaletes[48][0]="w";
        colorPaletes[48][1]=threadPaletes[48];
        colorPaletes[49][0]="x";
        colorPaletes[49][1]=threadPaletes[49];
        colorPaletes[50][0]="y";
        colorPaletes[50][1]=threadPaletes[50];
        colorPaletes[51][0]="z";
        colorPaletes[51][1]=threadPaletes[51];
    }
    public void populateColour(){
        try {
            colorGP.getChildren().clear();
            System.gc();
            List lstColor=null;
        
            Colour objColour = new Colour();
            objColour.setObjConfiguration(objConfiguration);
            
            FabricAction objFabricAction = new FabricAction();
            List lstColorDeatails = new ArrayList();
            lstColorDeatails = objFabricAction.lstImportColor(objColour);
            if(lstColorDeatails.size()==0){
                colorGP.add(new Text(objDictionaryAction.getWord("NOVALUE")), 0, 0);
            }else{            
                for (int i=0, k=0; i<lstColorDeatails.size();i++, k+=2){
                    lstColor = (ArrayList)lstColorDeatails.get(i);

                    final Label cLbl = new Label(""+(i+1));
                    cLbl.setPrefSize(25,25);
                    cLbl.setStyle("-fx-background-color:rgb("+lstColor.get(3).toString()+","+lstColor.get(4).toString()+","+lstColor.get(5).toString()+");");
                    cLbl.setUserData(lstColor.get(0).toString());
                    cLbl.setId(lstColor.get(6).toString());
                    String strTooltip = 
                            objDictionaryAction.getWord("NAME")+": "+lstColor.get(1).toString()+"\n"+
                            objDictionaryAction.getWord("TYPE")+": "+lstColor.get(2).toString()+"\n"+
                            objDictionaryAction.getWord("RGB")+": "+lstColor.get(3).toString()+", "+lstColor.get(4).toString()+", "+lstColor.get(5).toString()+"\n"+
                            objDictionaryAction.getWord("HEX")+": "+lstColor.get(6).toString()+"\n"+
                            objDictionaryAction.getWord("CODE")+": "+lstColor.get(7).toString()+"\n"+
                            objDictionaryAction.getWord("BY")+": "+lstColor.get(9).toString()+"\n"+
                            objDictionaryAction.getWord("DATE")+": "+lstColor.get(10).toString();
                    Tooltip tpLbl = new Tooltip(strTooltip);
                    
                    cLbl.setTooltip(tpLbl);
                    cLbl.setOnDragDetected(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent t) {
                            Dragboard dragboard=cLbl.startDragAndDrop(TransferMode.COPY);
                            ClipboardContent content=new ClipboardContent();
                            content.putString(cLbl.getId());
                            dragboard.setContent(content);
                            t.consume();
                        }
                    });
                    colorGP.add(cLbl, i%30, i/30);
                }
            }
        } catch (SQLException ex) {   
            Logger.getLogger(ColorPaletteView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ColorPaletteView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addDragNDrop(final Label lbl){
        lbl.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                if(t.getDragboard().hasString()){
                    t.acceptTransferModes(TransferMode.COPY);
                }
                t.consume();
            }
        });
        lbl.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                Dragboard dragboard=t.getDragboard();
                boolean dropped=false;
                if(dragboard.hasString()){
                    lbl.setId(dragboard.getString());
                    lbl.setStyle("-fx-background-color: "+dragboard.getString()+"; -fx-font-size: 10px; -fx-width:25px; -fx-height:25px;");
                    dropped=true;
                }
                t.setDropCompleted(dropped);
                t.consume();
            }
        });
    }
    
    public void reloadColour(String paletteName){
        try{
            UtilityAction objUtilityAction=new UtilityAction();
            String id=objUtilityAction.getPaletteIdFromName(paletteName);
            if(id!=null){
                objPalette.setStrPaletteID(id);
                objUtilityAction=new UtilityAction();
                objUtilityAction.getPalette(objPalette);
                if(objPalette.getStrThreadPalette()!=null){
                    threadPaletes=objPalette.getStrThreadPalette();
                }
            }
            /*String[] tPalette=objColourAction.getPaletteFromName(paletteName);
            if(tPalette!=null)
                threadPaletes=tPalette;*/
            setColorPaletes(); // it sets colorPalates[][] using threadPalettes
            populateYarnPalette(); // it adds pLbl to container with new values
        }
        catch(SQLException sqlEx){
            new Logging("SEVERE",ColorPaletteView.class.getName(),"loadColour() : ", sqlEx);
        }
    }
    
    public void loadPaletteNames(){
        paletteCB.getItems().clear();
        try{
            Palette objPalette = new Palette();
            objPalette.setObjConfiguration(objConfiguration);
            objPalette.setStrPaletteType("Colour");
            UtilityAction objUtilityAction = new UtilityAction();
            String[][] paletteNames=objUtilityAction.getPaletteName(objPalette);
            if(paletteNames!=null){
                for(String[] s:paletteNames){
                    paletteCB.getItems().add(s[1]);
                }
            }
        }
        catch(SQLException sqlEx){
            new Logging("SEVERE",ColorPaletteView.class.getName(),"loadPaletteNames() : ", sqlEx);
        }
    }
    
    public void populateYarnPalette(){
        warpGP.getChildren().clear();
        weftGP.getChildren().clear();
        for (int i=0; i<26;i++){
            pLbl[i].setText(colorPaletes[i][0]);
            pLbl[i].setPrefSize(25, 25);
            pLbl[i].setId("#"+colorPaletes[i][1]);
            pLbl[i].setStyle("-fx-background-color: #"+colorPaletes[i][1]+"; -fx-font-size: 10px; -fx-width:25px; -fx-height:25px;");
            warpGP.add(pLbl[i], i, 0);
        }
        for (int i=26; i<52;i++){
            pLbl[i].setText(colorPaletes[i][0]);
            pLbl[i].setPrefSize(25, 25);
            pLbl[i].setId("#"+colorPaletes[i][1]);
            pLbl[i].setStyle("-fx-background-color: #"+colorPaletes[i][1]+"; -fx-font-size: 10px; -fx-width:25px; -fx-height:25px;");
            weftGP.add(pLbl[i], i, 0);
        }
    }
    public void start(Stage stage) throws Exception {
        stage.initOwner(WindowView.windowStage);
        new ColorPaletteView(stage);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    public static void main(String[] args) {   
      launch(args);    
    }
}
