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
import com.mla.utility.PaletteView;
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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * UI & operations on Custom Color Palette
 * @author Aatif Ahmad Khan
 */

public class ColourPaletteCustom {
    public Stage colorStage;
    BorderPane root;
    private Label lblStatus;
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    public String colorCode;
    public Colour objColour;
    ScrollPane allColorSP;
    GridPane allColorGP;
    TabPane colorTP;
    GridPane container;
    GridPane hexGP;
    GridPane rgbGP;
    GridPane hsbGP;
    GridPane labGP;
    int currentPage;
    int perPage;
    
    public ColourPaletteCustom(Configuration objConfigurationCall) {   
        try{
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);
        currentPage=0;
        perPage=128;
        colorCode="";
        colorStage = new Stage();
        colorStage.initModality(Modality.APPLICATION_MODAL);
        root = new BorderPane();
        Scene scene = new Scene(root, 410, objConfiguration.HEIGHT/2, Color.GREY);
        scene.getStylesheets().add(ColourPaletteCustom.class.getResource(objConfiguration.getStrTemplate()+"/setting.css").toExternalForm());
        
        lblStatus = new Label();
        //lblStatus.setId("message");
        
        container=new GridPane();
        container.setVgap(2);
        
        allColorGP=new GridPane();
        allColorSP=new ScrollPane();
        allColorSP.setPrefHeight(300);
        allColorSP.setPrefWidth(410);
        allColorSP.setContent(allColorGP);
        
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
        container.add(colorTP, 0, 0, 2, 1);
        Tab rgbTab=new Tab(objDictionaryAction.getWord("RGB"));
        rgbTab.setClosable(false);
        Tab hexTab=new Tab(objDictionaryAction.getWord("HEXCODE"));
        hexTab.setClosable(false);
        Tab hsbTab=new Tab(objDictionaryAction.getWord("HSB"));
        hsbTab.setClosable(false);
        Tab labTab=new Tab(objDictionaryAction.getWord("LAB"));
        labTab.setClosable(false);
        colorTP.getTabs().addAll(hexTab, rgbTab, hsbTab/*, labTab*/);
        
        
        
        // hex pane
        hexGP=new GridPane();
        hexGP.setHgap(5);
        hexGP.setVgap(3);
        Label lblHex=new Label(objDictionaryAction.getWord("HEXCODE"));
        final TextField txtHex=new TextField();
        txtHex.setPromptText(objDictionaryAction.getWord("HEXCODE"));
        
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
        
        hexGP.add(lblHex, 0, 0);
        hexGP.add(txtHex, 1, 0);
        hexGP.add(btnHexSearch, 0, 1, 2, 1);
        
        hexTab.setContent(hexGP);
        // rgb pane
        rgbGP=new GridPane();
        rgbGP.setHgap(5);
        rgbGP.setVgap(3);
        Label lblRed=new Label(objDictionaryAction.getWord("RED"));
        final TextField txtRed=new TextField();
        txtRed.setPromptText(objDictionaryAction.getWord("RED"));
        Label lblGreen=new Label(objDictionaryAction.getWord("GREEN"));
        final TextField txtGreen=new TextField();
        txtGreen.setPromptText(objDictionaryAction.getWord("GREEN"));
        Label lblBlue=new Label(objDictionaryAction.getWord("BLUE"));
        final TextField txtBlue=new TextField();
        txtBlue.setPromptText(objDictionaryAction.getWord("BLUE"));
        
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
                            new Logging("SEVERE",ColourPaletteCustom.class.getName(),"Add custom rgb: ", ex);
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
        
        rgbGP.add(lblRed, 0, 0);
        rgbGP.add(txtRed, 1, 0);
        rgbGP.add(lblGreen, 0, 1);
        rgbGP.add(txtGreen, 1, 1);
        rgbGP.add(lblBlue, 0, 2);
        rgbGP.add(txtBlue, 1, 2);
        rgbGP.add(btnRgbSearch, 0, 3);
        rgbGP.add(btnRgbAdd, 1, 3);
        
        rgbTab.setContent(rgbGP);
        
        // hsb pane
        hsbGP=new GridPane();
        hsbGP.setHgap(5);
        hsbGP.setVgap(3);
        Label lblHue=new Label(objDictionaryAction.getWord("HUE"));
        final TextField txtHue=new TextField();
        txtHue.setPromptText(objDictionaryAction.getWord("HUE"));
        Label lblSaturation=new Label(objDictionaryAction.getWord("SATURATION"));
        final TextField txtSaturation=new TextField();
        txtSaturation.setPromptText(objDictionaryAction.getWord("SATURATION"));
        Label lblBrightness=new Label(objDictionaryAction.getWord("BRIGHTNESS"));
        final TextField txtBrightness=new TextField();
        txtBrightness.setPromptText(objDictionaryAction.getWord("BRIGHTNESS"));
        
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
        
        hsbGP.add(lblHue, 0, 0);
        hsbGP.add(txtHue, 1, 0);
        hsbGP.add(lblSaturation, 0, 1);
        hsbGP.add(txtSaturation, 1, 1);
        hsbGP.add(lblBrightness, 0, 2);
        hsbGP.add(txtBrightness, 1, 2);
        hsbGP.add(btnHsbSearch, 0, 3, 2, 1);
        
        hsbTab.setContent(hsbGP);
        
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
        
        container.add(colourTypeCB, 0, 1, 1, 1);
        container.add(lblStatus, 1, 1, 1, 1);
        container.add(allColorSP, 0, 2, 2, 1);
        
        container.add(btnPrev, 0, 3, 1, 1);
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
        
        
        container.add(btnNext, 1, 3, 1, 1);
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
        
        Button btnUse=new Button();
        btnUse.setText(objDictionaryAction.getWord("USE"));
        container.add(btnUse, 0, 4, 1, 1);
        btnUse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if(colorCode.length()==6)
                    colorStage.close();// return colorCode
                else
                    lblStatus.setText(objDictionaryAction.getWord("TOOLTIPFILLTOOLCOLOR"));
            }
        });
        Button btnCancel=new Button();
        btnCancel.setText(objDictionaryAction.getWord("CANCEL"));
        container.add(btnCancel, 1, 4, 1, 1);
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
            Logger.getLogger(ColourPaletteCustom.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            new Logging("SEVERE",ColourPaletteCustom.class.getName(),"getColourType() : ", sqlEx);
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
                            objColour.setStrOrderBy("HEX_CODE");
                            objColour.setStrColorHex(("#"+colorCode).toUpperCase());
                            try {
                                new ColourAction().isColorAvailable(objColour);
                            } catch (SQLException ex) {
                                Logger.getLogger(ColourPaletteCustom.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    allColorGP.add(cLbl, i%16, i/16);
                }
            }
        } catch (SQLException ex) {   
            Logger.getLogger(ColourPaletteCustom.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ColourPaletteCustom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
