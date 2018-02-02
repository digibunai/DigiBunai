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

package com.mla.colour;

import com.mla.dictionary.DictionaryAction;
import com.mla.fabric.FabricAction;
import com.mla.main.Configuration;
import com.mla.main.Logging;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Aatif Ahmad Khan
 */
public class ColourSelector {
    public Stage colorStage;
    BorderPane root;
    private Label lblStatus;
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    public String colorCode;
    public Colour objColour;
    ScrollPane allColorSP;
    GridPane dbColorGP;
    GridPane allColorGP;
    GridPane useColorGP;
    TabPane colorTP;
    GridPane container;
    GridPane searchGP;
    GridPane hexGP;
    GridPane rgbGP;
    GridPane hsbGP;
    GridPane labGP;
    TextField txtHex;
    TextField txtRed;
    TextField txtGreen;
    TextField txtBlue;
    TextField txtHue;
    TextField txtSaturation;
    TextField txtBrightness;
    int currentPage;
    int perPage;
    int currentIndex;
    Label lblShowColor;
    final String regExInt="[0-9]+";
    final String regExDouble="[0-9]{1,13}(\\.[0-9]*)?";
    
    public ColourSelector(Configuration objConfigurationCall) {   
        try{
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);
        currentPage=0;
        currentIndex=0;
        perPage=104;
        colorCode="";
        colorStage = new Stage();
        colorStage.initModality(Modality.APPLICATION_MODAL);
        root = new BorderPane();
        Scene scene = new Scene(root, 600, objConfiguration.HEIGHT/2, Color.GREY);
        scene.getStylesheets().add(ColourSelector.class.getResource(objConfiguration.getStrTemplate()+"/setting.css").toExternalForm());
        
        lblStatus = new Label();
        //lblStatus.setId("message");
        
        container=new GridPane();
        container.setPadding(new Insets(5, 5, 5, 5));
        container.setVgap(5);
        container.setHgap(5);
        
        useColorGP=new GridPane();
        useColorGP.setVgap(1);
        useColorGP.setHgap(1);
        useColorGP.setPrefSize(335, 55);
        for(int i=0; i<objConfiguration.getColourPalette().length; i++){
            final Label cLbl=new Label();
            cLbl.setPrefSize(25,25);
            cLbl.setStyle("-fx-background-color:#"+objConfiguration.getColourPalette()[i]+";");
            cLbl.setId(String.valueOf(i));
            useColorGP.add(cLbl, (i%13), (i/13));
            
            int[] rgb=hex2rgb(cLbl.getStyle().substring(cLbl.getStyle().lastIndexOf("#"), cLbl.getStyle().lastIndexOf("#")+7));
            String strTooltip = 
                    objDictionaryAction.getWord("RGB")+": "+rgb[0]+", "+rgb[1]+", "+rgb[2]+"\n"+
                    objDictionaryAction.getWord("HEX")+": "+(cLbl.getStyle().substring(cLbl.getStyle().lastIndexOf("#"), cLbl.getStyle().lastIndexOf("#")+7));
            cLbl.setTooltip(new Tooltip(strTooltip));
            cLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    currentIndex=Integer.parseInt(cLbl.getId());
                    colorCode=cLbl.getStyle().substring(cLbl.getStyle().lastIndexOf("#")+1, cLbl.getStyle().lastIndexOf("#")+7);
                    displayColor("#"+colorCode);
                    displayColorValue("#"+colorCode);
                }
            });
        }
        colorCode=((Label)useColorGP.getChildren().get(0)).getStyle().substring(((Label)useColorGP.getChildren().get(0)).getStyle().lastIndexOf("#")+1, ((Label)useColorGP.getChildren().get(0)).getStyle().lastIndexOf("#")+7);
        if(colorCode==null||colorCode.length()<6)
            colorCode="FFFFFF";
        
        lblShowColor=new Label();
        lblShowColor.setPrefSize(275, 55);
        searchGP=new GridPane();
        dbColorGP=new GridPane();
        
        allColorGP=new GridPane();
        allColorSP=new ScrollPane();
        allColorSP.setPrefHeight(200);
        allColorSP.setPrefWidth(330);
        allColorSP.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        allColorSP.setContent(allColorGP);
        
        container.add(useColorGP, 0, 0, 1, 1);
        container.add(lblShowColor, 1, 0, 1, 1);

        // ToggleGroup Radio Buttons
        final ToggleGroup colorTG = new ToggleGroup();
        final RadioButton hexRB = new RadioButton(objDictionaryAction.getWord("HEXCODE"));
        hexRB.setToggleGroup(colorTG);
        searchGP.add(hexRB, 0, 0, 2, 1);
        final RadioButton rgbRB = new RadioButton(objDictionaryAction.getWord("RGB"));
        rgbRB.setToggleGroup(colorTG);
        searchGP.add(rgbRB, 0, 2, 2, 1);
        final RadioButton hsbRB = new RadioButton(objDictionaryAction.getWord("HSB"));
        hsbRB.setToggleGroup(colorTG);
        searchGP.add(hsbRB, 0, 6, 2, 1);
        hexRB.setSelected(true);
        
        Label lblHex=new Label(objDictionaryAction.getWord("HEXCODE"));
        txtHex=new TextField();
        txtHex.setPromptText(objDictionaryAction.getWord("HEXCODE"));
        searchGP.add(lblHex, 0, 1, 1, 1);
        searchGP.add(txtHex, 1, 1, 1, 1);
        
        Label lblRed=new Label(objDictionaryAction.getWord("RED"));
        txtRed=new TextField();
        txtRed.setPromptText(objDictionaryAction.getWord("RED"));
        Label lblGreen=new Label(objDictionaryAction.getWord("GREEN"));
        txtGreen=new TextField();
        txtGreen.setPromptText(objDictionaryAction.getWord("GREEN"));
        Label lblBlue=new Label(objDictionaryAction.getWord("BLUE"));
        txtBlue=new TextField();
        txtBlue.setPromptText(objDictionaryAction.getWord("BLUE"));
        searchGP.add(lblRed, 0, 3, 1, 1);
        searchGP.add(txtRed, 1, 3, 1, 1);
        searchGP.add(lblGreen, 0, 4, 1, 1);
        searchGP.add(txtGreen, 1, 4, 1, 1);
        searchGP.add(lblBlue, 0, 5, 1, 1);
        searchGP.add(txtBlue, 1, 5, 1, 1);
        
        Label lblHue=new Label(objDictionaryAction.getWord("HUE"));
        txtHue=new TextField();
        txtHue.setPromptText(objDictionaryAction.getWord("HUE"));
        Label lblSaturation=new Label(objDictionaryAction.getWord("SATURATION"));
        txtSaturation=new TextField();
        txtSaturation.setPromptText(objDictionaryAction.getWord("SATURATION"));
        Label lblBrightness=new Label(objDictionaryAction.getWord("BRIGHTNESS"));
        txtBrightness=new TextField();
        txtBrightness.setPromptText(objDictionaryAction.getWord("BRIGHTNESS"));
        searchGP.add(lblHue, 0, 7, 1, 1);
        searchGP.add(txtHue, 1, 7, 1, 1);
        searchGP.add(lblSaturation, 0, 8, 1, 1);
        searchGP.add(txtSaturation, 1, 8, 1, 1);
        searchGP.add(lblBrightness, 0, 9, 1, 1);
        searchGP.add(txtBrightness, 1, 9, 1, 1);
        
        displayColor("#"+colorCode);
        displayColorValue("#"+colorCode);
        
        objColour = new Colour();
        objColour.setObjConfiguration(objConfiguration);
        objColour.setStrCondition("");
        objColour.setStrSearchBy("");
        objColour.setStrOrderBy("Type");
        objColour.setStrDirection("Ascending");
        objColour.setStrLimit((currentPage*perPage)+","+perPage);
        populateColour();
        
        colorTP=new TabPane();
        colorTP.setPrefHeight(200);
        Tab rgbTab=new Tab(objDictionaryAction.getWord("RGB"));
        rgbTab.setClosable(false);
        Tab hexTab=new Tab(objDictionaryAction.getWord("HEXCODE"));
        hexTab.setClosable(false);
        Tab hsbTab=new Tab(objDictionaryAction.getWord("HSB"));
        hsbTab.setClosable(false);
        Tab labTab=new Tab(objDictionaryAction.getWord("LAB"));
        labTab.setClosable(false);
        colorTP.getTabs().addAll(hexTab, rgbTab, hsbTab/*, labTab*/);
        
        txtHex.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(hexRB.isSelected()){
                    if(isHexCodeValid(t1)){
                        int[] rgb=hex2rgb(t1);
                        txtRed.setText(String.valueOf(rgb[0]));
                        txtGreen.setText(String.valueOf(rgb[1]));
                        txtBlue.setText(String.valueOf(rgb[2]));
                        double[] hsb=hex2hsb(t1);
                        txtHue.setText(String.valueOf(hsb[0]));
                        txtSaturation.setText(String.valueOf(hsb[1]));
                        txtBrightness.setText(String.valueOf(hsb[2]));
                        displayColor(t1);
                    }
                }
            }
        });
        txtRed.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(rgbRB.isSelected()){
                    if(t1!=null && t1.matches(regExInt) && txtGreen.getText().matches(regExInt) && txtBlue.getText().matches(regExInt)){
                        if(Integer.parseInt(t1)>=0 && Integer.parseInt(t1)<=255 && Integer.parseInt(txtGreen.getText())>=0 && Integer.parseInt(txtGreen.getText())<=255 && Integer.parseInt(txtBlue.getText())>=0 && Integer.parseInt(txtBlue.getText())<=255){
                            int[] rgb=new int[]{Integer.parseInt(t1), Integer.parseInt(txtGreen.getText()), Integer.parseInt(txtBlue.getText())};
                            txtHex.setText(rgb2hex(rgb).toUpperCase());
                            double[] hsb=hex2hsb(txtHex.getText());
                            txtHue.setText(String.valueOf(hsb[0]));
                            txtSaturation.setText(String.valueOf(hsb[1]));
                            txtBrightness.setText(String.valueOf(hsb[2]));
                            displayColor(rgb2hex(rgb).toUpperCase());
                        }
                    }
                }
            }
        });
        txtGreen.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(rgbRB.isSelected()){
                    if(t1!=null && txtRed.getText().matches(regExInt) && t1.matches(regExInt) && txtBlue.getText().matches(regExInt)){
                        if(Integer.parseInt(txtRed.getText())>=0 && Integer.parseInt(txtRed.getText())<=255 && Integer.parseInt(t1)>=0 && Integer.parseInt(t1)<=255 && Integer.parseInt(txtBlue.getText())>=0 && Integer.parseInt(txtBlue.getText())<=255){
                            int[] rgb=new int[]{Integer.parseInt(txtRed.getText()), Integer.parseInt(t1), Integer.parseInt(txtBlue.getText())};
                            txtHex.setText(rgb2hex(rgb).toUpperCase());
                            double[] hsb=hex2hsb(txtHex.getText());
                            txtHue.setText(String.valueOf(hsb[0]));
                            txtSaturation.setText(String.valueOf(hsb[1]));
                            txtBrightness.setText(String.valueOf(hsb[2]));
                            displayColor(rgb2hex(rgb).toUpperCase());
                        }
                    }
                }
            }
        });
        txtBlue.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(rgbRB.isSelected()){
                    if(t1!=null && txtRed.getText().matches(regExInt) && txtGreen.getText().matches(regExInt) && t1.matches(regExInt)){
                        if(Integer.parseInt(txtRed.getText())>=0 && Integer.parseInt(txtRed.getText())<=255 && Integer.parseInt(txtGreen.getText())>=0 && Integer.parseInt(txtGreen.getText())<=255 && Integer.parseInt(t1)>=0 && Integer.parseInt(t1)<=255){
                            int[] rgb=new int[]{Integer.parseInt(txtRed.getText()), Integer.parseInt(txtGreen.getText()), Integer.parseInt(t1)};
                            txtHex.setText(rgb2hex(rgb).toUpperCase());
                            double[] hsb=hex2hsb(txtHex.getText());
                            txtHue.setText(String.valueOf(hsb[0]));
                            txtSaturation.setText(String.valueOf(hsb[1]));
                            txtBrightness.setText(String.valueOf(hsb[2]));
                            displayColor(rgb2hex(rgb).toUpperCase());
                        }
                    }
                }
            }
        });
        txtHue.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(hsbRB.isSelected()){
                    if(t1!=null && t1.matches(regExDouble) && txtSaturation.getText().matches(regExDouble) && txtBrightness.getText().matches(regExDouble) && (Double.parseDouble(t1)<0.0 || Double.parseDouble(t1)>=0.0) && Double.parseDouble(txtSaturation.getText())>=0.0 && Double.parseDouble(txtSaturation.getText())<=1.0 && Double.parseDouble(txtBrightness.getText())>=0.0 && Double.parseDouble(txtBrightness.getText())<=1.0){
                        double[] hsb=new double[]{Double.parseDouble(t1), Double.parseDouble(txtSaturation.getText()), Double.parseDouble(txtBrightness.getText())};
                        txtHex.setText(hsb2hex(hsb).toUpperCase());
                        int[] rgb=hex2rgb(txtHex.getText());
                        txtRed.setText(String.valueOf(rgb[0]));
                        txtGreen.setText(String.valueOf(rgb[1]));
                        txtBlue.setText(String.valueOf(rgb[2]));
                        displayColor(hsb2hex(hsb).toUpperCase());
                    }
                }
            }
        });
        txtSaturation.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(hsbRB.isSelected()){
                    if(t1!=null && txtHue.getText().matches(regExDouble) && t1.matches(regExDouble) && txtBrightness.getText().matches(regExDouble) && (Double.parseDouble(txtHue.getText())<0.0 || Double.parseDouble(txtHue.getText())>=0.0) && Double.parseDouble(t1)>=0.0 && Double.parseDouble(t1)<=1.0 && Double.parseDouble(txtBrightness.getText())>=0.0 && Double.parseDouble(txtBrightness.getText())<=1.0){
                        double[] hsb=new double[]{Double.parseDouble(txtHue.getText()), Double.parseDouble(t1), Double.parseDouble(txtBrightness.getText())};
                        txtHex.setText(hsb2hex(hsb).toUpperCase());
                        int[] rgb=hex2rgb(txtHex.getText());
                        txtRed.setText(String.valueOf(rgb[0]));
                        txtGreen.setText(String.valueOf(rgb[1]));
                        txtBlue.setText(String.valueOf(rgb[2]));
                        displayColor(hsb2hex(hsb).toUpperCase());
                    }
                }
            }
        });
        txtBrightness.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(hsbRB.isSelected()){
                    if(t1!=null && txtHue.getText().matches(regExDouble) && txtSaturation.getText().matches(regExDouble) && t1.matches(regExDouble) && (Double.parseDouble(txtHue.getText())<0.0 || Double.parseDouble(txtHue.getText())>=0.0) && Double.parseDouble(txtSaturation.getText())>=0.0 && Double.parseDouble(txtSaturation.getText())<=1.0 && Double.parseDouble(t1)>=0.0 && Double.parseDouble(t1)<=1.0){
                        double[] hsb=new double[]{Double.parseDouble(txtHue.getText()), Double.parseDouble(txtSaturation.getText()), Double.parseDouble(t1)};
                        txtHex.setText(hsb2hex(hsb).toUpperCase());
                        int[] rgb=hex2rgb(txtHex.getText());
                        txtRed.setText(String.valueOf(rgb[0]));
                        txtGreen.setText(String.valueOf(rgb[1]));
                        txtBlue.setText(String.valueOf(rgb[2]));
                        displayColor(hsb2hex(hsb).toUpperCase());
                    }
                }
            }
        });
        
        Button btnHexSearch=new Button(objDictionaryAction.getWord("SEARCH"));
        btnHexSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if(txtHex.getText().length()>0){
                    if(isHexCodeValid(txtHex.getText().toUpperCase())){
                        objColour.setStrOrderBy("HEX_CODE");
                        objColour.setStrColorHex(txtHex.getText().toUpperCase());
                        try{
                            ColourAction objColourAction=new ColourAction();
                            boolean isAvailable=objColourAction.isColorAvailable(objColour);
                            allColorGP.getChildren().clear();
                            if(isAvailable){
                                final Label cLbl = new Label();
                                cLbl.setPrefSize(25,25);
                                cLbl.setStyle("-fx-background-color:"+objColour.getStrColorHex()+";");
                                String strTooltip = 
                                    objDictionaryAction.getWord("NAME")+": "+objColour.getStrColorName()+"\n"+
                                    objDictionaryAction.getWord("TYPE")+": "+objColour.getStrColorType()+"\n"+
                                    objDictionaryAction.getWord("RGB")+": "+objColour.getIntR()+", "+objColour.getIntG()+", "+objColour.getIntB()+"\n"+
                                    objDictionaryAction.getWord("HEX")+": "+objColour.getStrColorHex()+"\n"+
                                    objDictionaryAction.getWord("CODE")+": "+objColour.getStrColorHex()+"\n"+
                                    objDictionaryAction.getWord("DATE")+": "+objColour.getStrColorDate();
                                    Tooltip tpLbl = new Tooltip(strTooltip);
                                    cLbl.setTooltip(tpLbl);
                                allColorGP.add(cLbl, 0, 0);
                                cLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent t) {
                                        colorCode=cLbl.getStyle().substring(cLbl.getStyle().lastIndexOf("#")+1, cLbl.getStyle().lastIndexOf("#")+7);
                                    }
                                });
                            }
                            else
                                allColorGP.add(new Text(objDictionaryAction.getWord("NOVALUE")), 0, 0);
                        }
                        catch(Exception Ex){
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                    else
                        lblStatus.setText(objDictionaryAction.getWord("WRONGINPUT"));
                }
                else{ // blankInput
                    lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                }
            }
        });
        
        Button btnRgbSearch=new Button(objDictionaryAction.getWord("SEARCH"));
        btnRgbSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if(txtRed.getText().length()>0&&txtGreen.getText().length()>0&&txtBlue.getText().length()>0){
                    if(isColorValueValid(txtRed.getText())&&isColorValueValid(txtGreen.getText())&&isColorValueValid(txtBlue.getText())){
                        objColour.setStrOrderBy("R_CODE");
                        objColour.setIntR(Integer.parseInt(txtRed.getText()));
                        objColour.setIntG(Integer.parseInt(txtGreen.getText()));
                        objColour.setIntB(Integer.parseInt(txtBlue.getText()));
                        try{
                            ColourAction objColourAction=new ColourAction();
                            boolean isAvailable=objColourAction.isColorAvailable(objColour);
                            allColorGP.getChildren().clear();
                            if(isAvailable){
                                final Label cLbl = new Label();
                                cLbl.setPrefSize(25,25);
                                cLbl.setStyle("-fx-background-color:"+objColour.getStrColorHex()+";");
                                String strTooltip = 
                                    objDictionaryAction.getWord("NAME")+": "+objColour.getStrColorName()+"\n"+
                                    objDictionaryAction.getWord("TYPE")+": "+objColour.getStrColorType()+"\n"+
                                    objDictionaryAction.getWord("RGB")+": "+objColour.getIntR()+", "+objColour.getIntG()+", "+objColour.getIntB()+"\n"+
                                    objDictionaryAction.getWord("HEX")+": "+objColour.getStrColorHex()+"\n"+
                                    objDictionaryAction.getWord("CODE")+": "+objColour.getStrColorHex()+"\n"+
                                    objDictionaryAction.getWord("DATE")+": "+objColour.getStrColorDate();
                                    Tooltip tpLbl = new Tooltip(strTooltip);
                                    cLbl.setTooltip(tpLbl);
                                allColorGP.add(cLbl, 0, 0);
                                cLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent t) {
                                        colorCode=cLbl.getStyle().substring(cLbl.getStyle().lastIndexOf("#")+1, cLbl.getStyle().lastIndexOf("#")+7);
                                    }
                                });
                            }
                            else
                                allColorGP.add(new Text(objDictionaryAction.getWord("NOVALUE")), 0, 0);
                        }
                        catch(Exception Ex){
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                    else
                        lblStatus.setText(objDictionaryAction.getWord("WRONGINPUT"));
                }
                else{ // blankInput
                    lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                }
            }
        });
        
        Button btnRgbAdd=new Button(objDictionaryAction.getWord("ADD"));
        btnRgbAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if(txtRed.getText().length()>0&&txtGreen.getText().length()>0&&txtBlue.getText().length()>0){
                    if(isColorValueValid(txtRed.getText())&&isColorValueValid(txtGreen.getText())&&isColorValueValid(txtBlue.getText())){
                        String strHex=String.format("#%02x%02x%02x", Integer.parseInt(txtRed.getText()), Integer.parseInt(txtGreen.getText()), Integer.parseInt(txtBlue.getText())).toUpperCase();
                        Colour objColour=new Colour();
                        objColour.setObjConfiguration(objConfiguration);
                        objColour.setStrColorName("");
                        objColour.setStrColorType("Web Color");
                        objColour.setIntR(Integer.parseInt(txtRed.getText()));
                        objColour.setIntG(Integer.parseInt(txtGreen.getText()));
                        objColour.setIntB(Integer.parseInt(txtBlue.getText()));
                        objColour.setStrColorHex(strHex);
                        objColour.setStrColorCode("");
                        try{
                            ColourAction objColourAction=new ColourAction();
                            byte resultCode=objColourAction.setColour(objColour);
                            if(resultCode==1){
                                allColorGP.getChildren().clear();
                                final Label cLbl = new Label();
                                cLbl.setPrefSize(25,25);

                                cLbl.setStyle("-fx-background-color:"+strHex+";");
                                String strTooltip = 
                                    objDictionaryAction.getWord("TYPE")+": "+objColour.getStrColorType()+"\n"+
                                    objDictionaryAction.getWord("RGB")+": "+objColour.getIntR()+", "+objColour.getIntG()+", "+objColour.getIntB()+"\n"+
                                    objDictionaryAction.getWord("HEX")+": "+objColour.getStrColorHex();
                                    Tooltip tpLbl = new Tooltip(strTooltip);
                                    cLbl.setTooltip(tpLbl);
                                allColorGP.add(cLbl, 0, 0);
                                cLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent t) {
                                        colorCode=cLbl.getStyle().substring(cLbl.getStyle().lastIndexOf("#")+1, cLbl.getStyle().lastIndexOf("#")+7);
                                    }
                                });
                            }
                        }catch(Exception ex){
                            new Logging("SEVERE",ColourSelector.class.getName(),"Add custom rgb: ", ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }               
                    else
                        lblStatus.setText(objDictionaryAction.getWord("WRONGINPUT"));
                }
                else{ // blankInput
                    lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                }
            }
        });
        
        Button btnHsbSearch=new Button(objDictionaryAction.getWord("SEARCH"));
        btnHsbSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if(txtHue.getText().length()>0&&txtSaturation.getText().length()>0&&txtBrightness.getText().length()>0){
                    float hueValue=Float.parseFloat(txtHue.getText());
                    float saturationValue=Float.parseFloat(txtSaturation.getText());
                    float brightnessValue=Float.parseFloat(txtBrightness.getText());
                    if(hueValue>=0&&saturationValue>=0.0&&saturationValue<=1.0&&brightnessValue>=0.0&&brightnessValue<=1.0){
                        Color color = Color.hsb(hueValue, saturationValue, brightnessValue);
                        objColour.setStrOrderBy("R_CODE");
                        objColour.setIntR((int)(color.getRed()*255));
                        objColour.setIntG((int)(color.getGreen()*255));
                        objColour.setIntB((int)(color.getBlue()*255));
                        try{
                            ColourAction objColourAction=new ColourAction();
                            boolean isAvailable=objColourAction.isColorAvailable(objColour);
                            allColorGP.getChildren().clear();
                            if(isAvailable){
                                final Label cLbl = new Label();
                                cLbl.setPrefSize(25,25);
                                cLbl.setStyle("-fx-background-color:"+objColour.getStrColorHex()+";");
                                String strTooltip = 
                                    objDictionaryAction.getWord("NAME")+": "+objColour.getStrColorName()+"\n"+
                                    objDictionaryAction.getWord("TYPE")+": "+objColour.getStrColorType()+"\n"+
                                    objDictionaryAction.getWord("RGB")+": "+objColour.getIntR()+", "+objColour.getIntG()+", "+objColour.getIntB()+"\n"+
                                    objDictionaryAction.getWord("HEX")+": "+objColour.getStrColorHex()+"\n"+
                                    objDictionaryAction.getWord("CODE")+": "+objColour.getStrColorHex()+"\n"+
                                    objDictionaryAction.getWord("DATE")+": "+objColour.getStrColorDate();
                                    Tooltip tpLbl = new Tooltip(strTooltip);
                                    cLbl.setTooltip(tpLbl);
                                allColorGP.add(cLbl, 0, 0);
                                cLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent t) {
                                        colorCode=cLbl.getStyle().substring(cLbl.getStyle().lastIndexOf("#")+1, cLbl.getStyle().lastIndexOf("#")+7);
                                    }
                                });
                            }
                            else
                                allColorGP.add(new Text(objDictionaryAction.getWord("NOVALUE")), 0, 0);
                        }
                        catch(Exception Ex){
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                    else
                        lblStatus.setText(objDictionaryAction.getWord("WRONGINPUT"));
                }
                else{ // blankInput
                    lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                }
            }
        });
        
        // LAB pane
        labGP=new GridPane();
        labGP.setHgap(5);
        labGP.setVgap(3);
        Label lblLightness=new Label(objDictionaryAction.getWord("LIGHTNESS"));
        final TextField txtLightness=new TextField();
        txtLightness.setPromptText(objDictionaryAction.getWord("LIGHTNESS"));
        Label lblParamA=new Label(objDictionaryAction.getWord("PARAMETERA"));
        final TextField txtParamA=new TextField();
        txtParamA.setPromptText(objDictionaryAction.getWord("PARAMETERA"));
        Label lblParamB=new Label(objDictionaryAction.getWord("PARAMETERB"));
        final TextField txtParamB=new TextField();
        txtParamB.setPromptText(objDictionaryAction.getWord("PARAMETERB"));
        
        Button btnLabSearch=new Button(objDictionaryAction.getWord("SEARCH"));
        btnLabSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if(txtLightness.getText().length()>0&&txtParamA.getText().length()>0&&txtParamB.getText().length()>0){
                    float lightnessValue=Float.parseFloat(txtLightness.getText());
                    float aValue=Float.parseFloat(txtParamA.getText());
                    float bValue=Float.parseFloat(txtParamB.getText());
                    if(lightnessValue>=0&&aValue>=0.0&&aValue<=1.0&&bValue>=0.0&&bValue<=1.0){
                        Color color = Color.hsb(lightnessValue, aValue, bValue);
                        objColour.setStrOrderBy("R_CODE");
                        objColour.setIntR((int)(color.getRed()*255));
                        objColour.setIntG((int)(color.getGreen()*255));
                        objColour.setIntB((int)(color.getBlue()*255));
                        try{
                            ColourAction objColourAction=new ColourAction();
                            boolean isAvailable=objColourAction.isColorAvailable(objColour);
                            allColorGP.getChildren().clear();
                            if(isAvailable){
                                final Label cLbl = new Label();
                                cLbl.setPrefSize(25,25);
                                cLbl.setStyle("-fx-background-color:"+objColour.getStrColorHex()+";");
                                String strTooltip = 
                                    objDictionaryAction.getWord("NAME")+": "+objColour.getStrColorName()+"\n"+
                                    objDictionaryAction.getWord("TYPE")+": "+objColour.getStrColorType()+"\n"+
                                    objDictionaryAction.getWord("RGB")+": "+objColour.getIntR()+", "+objColour.getIntG()+", "+objColour.getIntB()+"\n"+
                                    objDictionaryAction.getWord("HEX")+": "+objColour.getStrColorHex()+"\n"+
                                    objDictionaryAction.getWord("CODE")+": "+objColour.getStrColorHex()+"\n"+
                                    objDictionaryAction.getWord("DATE")+": "+objColour.getStrColorDate();
                                    Tooltip tpLbl = new Tooltip(strTooltip);
                                    cLbl.setTooltip(tpLbl);
                                allColorGP.add(cLbl, 0, 0);
                                cLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent t) {
                                        colorCode=cLbl.getStyle().substring(cLbl.getStyle().lastIndexOf("#")+1, cLbl.getStyle().lastIndexOf("#")+7);
                                    }
                                });
                            }
                            else
                                allColorGP.add(new Text(objDictionaryAction.getWord("NOVALUE")), 0, 0);
                        }
                        catch(Exception Ex){
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                    else
                        lblStatus.setText(objDictionaryAction.getWord("WRONGINPUT"));
                }
                else{ // blankInput
                    lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                }
            }
        });
        
        labGP.add(lblLightness, 0, 0);
        labGP.add(txtLightness, 1, 0);
        labGP.add(lblParamA, 0, 1);
        labGP.add(txtParamA, 1, 1);
        labGP.add(lblParamB, 0, 2);
        labGP.add(txtParamB, 1, 2);
        labGP.add(btnLabSearch, 0, 3, 2, 1);
        
        labTab.setContent(labGP);
        
        final Button btnPrev=new Button("<");
        btnPrev.setDisable(true);
        final Button btnNext=new Button(">");
        
        final ComboBox colourTypeCB = new ComboBox();
        colourTypeCB.getItems().addAll(getColourType());//("Pantone"/*,"Web Color"*/);
        colourTypeCB.setPromptText("Pantone");
        colourTypeCB.setDisable(false);
        String strColorType = "Pantone";//objFabric.getStrColorType();
        colourTypeCB.setValue(strColorType);
        colourTypeCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOLORTYPE")));
        colourTypeCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objColour.setStrSearchBy(newValue);
                populateColour();
                currentPage=0;
                btnPrev.setDisable(true);
            }    
        });
        
        dbColorGP.add(colourTypeCB, 0, 0, 1, 1);
        dbColorGP.add(lblStatus, 1, 0, 1, 1);
        dbColorGP.add(allColorSP, 0, 1, 2, 1);
        
        dbColorGP.add(btnPrev, 0, 2, 1, 1);
        btnPrev.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if(currentPage!=0){
                    currentPage--;
                    objColour.setStrLimit((currentPage*perPage)+","+perPage);
                    populateColour();
                    if(btnNext.isDisabled())
                        btnNext.setDisable(false);
                }
                else
                    btnPrev.setDisable(true);
            }
        });
        
        
        dbColorGP.add(btnNext, 1, 2, 1, 1);
        btnNext.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if(!btnNext.isDisabled()){
                    currentPage++;
                    objColour.setStrLimit((currentPage*perPage)+","+perPage);
                    populateColour();
                    if(btnPrev.isDisabled())
                        btnPrev.setDisable(false);
                    if(allColorGP.getChildren().size()<=1){
                        currentPage--;
                        objColour.setStrLimit((currentPage*perPage)+","+perPage);
                        populateColour();
                        btnNext.setDisable(true);
                    }
                }
            }
        });
        
        container.add(dbColorGP, 0, 1, 1, 1);
        container.add(searchGP, 1, 1, 1, 1);
        
        Button btnUse=new Button();
        btnUse.setText(objDictionaryAction.getWord("USE"));
        container.add(btnUse, 0, 2, 1, 1);
        btnUse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                // added 27 12 2017
                if(!isHexCodeValid(txtHex.getText()))
                    return;
                Colour objColour=new Colour();
                objColour.setObjConfiguration(objConfiguration);
                objColour.setStrColorName("");
                objColour.setStrColorType("Web Color");
                objColour.setStrColorHex(txtHex.getText().toUpperCase());
                int rgb[]=hex2rgb(txtHex.getText().toUpperCase());
                objColour.setIntR(rgb[0]);
                objColour.setIntG(rgb[1]);
                objColour.setIntB(rgb[2]);
                objColour.setStrColorCode("");
                try{
                    ColourAction objColourAction=new ColourAction();
                    if(objColourAction.isColorAvailable(objColour)){
                        
                    }else{
                        objColourAction=new ColourAction();
                        byte resultCode=objColourAction.setColour(objColour);
                    }
                }catch(Exception ex){
                    new Logging("SEVERE",ColourSelector.class.getName(),"Add custom rgb: ", ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
                objConfiguration.getColourPalette()[currentIndex]=txtHex.getText().substring(1);
                colorCode=txtHex.getText().substring(1);
                colorStage.close();
                /*
                if(colorCode.length()==6){
                    //objConfiguration.getColourPalette()[currentIndex]=col
                    colorStage.close();// return colorCode
                }
                else
                    lblStatus.setText(objDictionaryAction.getWord("TOOLTIPFILLTOOLCOLOR"));*/
            }
        });
        Button btnCancel=new Button();
        btnCancel.setText(objDictionaryAction.getWord("CANCEL"));
        container.add(btnCancel, 1, 2, 1, 1);
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                colorCode="";
                colorStage.close();
            }
        });
        
        root.setCenter(container);
        colorStage.setScene(scene);
        colorStage.showAndWait();
        }
        catch(Exception ex){
            Logger.getLogger(ColourSelector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void displayColorValue(String hexcode){
        txtHex.setText(hexcode);
        int[] rgb=hex2rgb(hexcode);
        txtRed.setText(String.valueOf(rgb[0]));
        txtGreen.setText(String.valueOf(rgb[1]));
        txtBlue.setText(String.valueOf(rgb[2]));
        double[] hsb=hex2hsb(hexcode);
        txtHue.setText(String.valueOf(hsb[0]));
        txtSaturation.setText(String.valueOf(hsb[1]));
        txtBrightness.setText(String.valueOf(hsb[2]));
    }
    
    public void displayColor(String hexcode){
        lblShowColor.setStyle("-fx-background-color:"+hexcode+";");
        int[] rgb=hex2rgb(hexcode);
        lblShowColor.setText(hexcode+"\nRGB("+rgb[0]+","+rgb[1]+","+rgb[2]+")");
    }
    
    public boolean isColorValueValid(String value){
        boolean isValid=false;
        try{
            int i=Integer.parseInt(value);
            if(i>=0&&i<=255)
                isValid=true;
        }
        catch(Exception ex){System.err.println(ex);}
        return isValid;
    }
    
    public boolean isHexCodeValid(String value){
        boolean isValid=false;
        if(value!=null&&value.length()==7){
            int i=0;
            if(value.startsWith("#")){
                for(i=1; i<7; i++){
                    if(!(value.substring(i, i+1).matches("[0-9]*")||value.charAt(i)=='a'
                            ||value.charAt(i)=='b'||value.charAt(i)=='c'||value.charAt(i)=='d'
                            ||value.charAt(i)=='e'||value.charAt(i)=='f'||value.charAt(i)=='A'
                            ||value.charAt(i)=='B'||value.charAt(i)=='C'||value.charAt(i)=='D'
                            ||value.charAt(i)=='E'||value.charAt(i)=='F'))
                        break;
                }
                if(i==7)
                    isValid=true;
            }
        }
        return isValid;
    }
    
    private ArrayList<String> getColourType(){
        ArrayList<String> types=new ArrayList<>();
        try{
            types=new ColourAction().getColourType();
        }
        catch(SQLException sqlEx){
            new Logging("SEVERE",ColourSelector.class.getName(),"getColourType() : ", sqlEx);
        }
        return types;
    }
    
    public void populateColour(){
        try {
            allColorGP.getChildren().clear();
            System.gc();
            List lstColor=null;
            FabricAction objFabricAction = new FabricAction();
            List lstColorDeatails = new ArrayList();
            lstColorDeatails = objFabricAction.lstImportColor(objColour);
            if(lstColorDeatails.size()==0){
                allColorGP.add(new Text(objDictionaryAction.getWord("NOVALUE")), 0, 0);
            }else{
                for (int i=0; i<lstColorDeatails.size();i++){
                    
                    lstColor = (ArrayList)lstColorDeatails.get(i);

                    final Label cLbl = new Label(""+(i+1));
                    cLbl.setPrefSize(25,25);
                    cLbl.setStyle("-fx-background-color:"+lstColor.get(6).toString()+";");
                    //System.err.println("cLbl:"+(i+1)+cLbl.getStyle());
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
                    cLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent t) {
                            colorCode=cLbl.getStyle().substring(cLbl.getStyle().lastIndexOf("#")+1, cLbl.getStyle().lastIndexOf("#")+7);
                            displayColor("#"+colorCode);
                            displayColorValue("#"+colorCode);
                            objColour.setStrOrderBy("HEX_CODE");
                            objColour.setStrColorHex(("#"+colorCode).toUpperCase());
                            try {
                                new ColourAction().isColorAvailable(objColour);
                            } catch (SQLException ex) {
                                Logger.getLogger(ColourSelector.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    allColorGP.add(cLbl, i%13, i/13);
                }
            }
        } catch (SQLException ex) {   
            Logger.getLogger(ColourSelector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ColourSelector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int[] hex2rgb(String hexcode){
        if(!isHexCodeValid(hexcode))
            return new int[]{0, 0, 0};
        else
            return new int[]{Integer.valueOf( hexcode.substring( 1, 3 ), 16 )
                    , Integer.valueOf( hexcode.substring( 3, 5 ), 16 )
                    , Integer.valueOf( hexcode.substring( 5, 7 ), 16 )};
    }
    
    public String rgb2hex(int[] rgb){
        if(isRGBinvalid(rgb))
            return "#000000";
        else
            return String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
    }
    
    public String hsb2hex(double[] hsb){
        if(isHSBinvalid(hsb))
            return "#000000";
        else{
            Color hsbColor = Color.hsb(hsb[0], hsb[1], hsb[2]);
            return rgb2hex(new int[]{(int)(hsbColor.getRed()*255)
                    , (int)(hsbColor.getGreen()*255)
                    , (int)(hsbColor.getBlue()*255)});
        }
    }
    
    public double[] hex2hsb(String hexcode){
        if(!isHexCodeValid(hexcode))
            return new double[]{0.0, 0.0, 0.0};
        else{
            int[] rgb=hex2rgb(hexcode);
            Color color=Color.web(hexcode);
            return new double[]{color.getHue(), color.getSaturation(), color.getBrightness()};
        }    
    }
    
    public boolean isRGBinvalid(int[] rgb){
        if(rgb==null || rgb.length!=3 || rgb[0]<0 || rgb[0]>255 || rgb[1]<0 || rgb[1]>255 || rgb[2]<0 || rgb[2]>255)
            return true;
        else
            return false;
    }
    
    public boolean isHSBinvalid(double[] hsb){
        if(hsb==null || hsb.length!=3 || hsb[1]<0.0 || hsb[1]>1.0 || hsb[2]<0.0 || hsb[2]>1.0)
            return true;
        else
            return false;
    }
    
}
