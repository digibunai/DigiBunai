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
package com.mla.fabric;

import com.mla.colour.ColourPalettePopup;
import com.mla.colour.ColourSelector;
import com.mla.dictionary.DictionaryAction;
import com.mla.main.IDGenerator;
import com.mla.main.Logging;
import com.mla.yarn.Yarn;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.lang.StringUtils;
/**
 *
 * @Designing GUI window for weave editor
 * @author Amit Kumar Singh
 * 
 */
public class PatternView {
   
    private Fabric objFabric;
    private FabricAction objFabricAction;
    DictionaryAction objDictionaryAction;
    
    private Stage patternStage;
    private ScrollPane root;
    private Scene scene;
    GridPane container;
    
    private Button btnApply;
    private Button btnCancel;
    private Button btnClear;
    private Button switchBtn;
    private Button weftBrowseBtn;
    private Button warpBrowseBtn;
    
    private TextField weftTF;
    private TextField warpTF;
    private ImageView threadIV;
    ImageView warpIV;
    ImageView weftIV;
    Yarn[] yarnPattern;
    
    private TableView<Pattern> patternTable ;
    private ObservableList<Pattern> patternData = null;
    private String[] threadPaletes; //= new String[]{"585858", "B40431", "B4045F", "B40486", "B404AE", "8904B1", "5F04B4", "3104B4", "0404B4", "0431B4", "045FB4", "0489B1", "04B4AE", "04B486", "04B45F", "04B431", "04B404", "31B404", "5FB404", "86B404", "AEB404", "B18904", "B45F04", "B43104", "B40404", "FFFFFF", "585858", "B40431", "B4045F", "B40486", "B404AE", "8904B1", "5F04B4", "3104B4", "0404B4", "0431B4", "045FB4", "0489B1", "04B4AE", "04B486", "04B45F", "04B431", "04B404", "31B404", "5FB404", "86B404", "AEB404", "B18904", "B45F04", "B43104", "B40404", "FFFFFF"}; 
    private String[][] colorPaletes;
    Map<String,String> warpPattern = new HashMap<>();
    Map<String,String> weftPattern = new HashMap<>();
    
    byte xindex =-1, yindex=-1;
    
    public PatternView(final Stage primaryStage) {  }

    public PatternView(Fabric objFabricCall) throws SQLException, Exception {   
        this.objFabric = objFabricCall;
        objDictionaryAction = new DictionaryAction(objFabric.getObjConfiguration());
        
        threadPaletes = objFabric.getThreadPaletes();
        initilizePattern();
        colorPaletes = new String[52][2];
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
       
        container = new GridPane();
        
        container.setAlignment(Pos.CENTER);
        //container.setGridLinesVisible(true);
        container.autosize();       
         
        switchBtn = new Button(objDictionaryAction.getWord("SWITCHPATTERN"));
        switchBtn.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSWITCHPATTERN")));
        //switchBtn.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColour()+"/switch_color.png"));
        switchBtn.setAlignment(Pos.CENTER);
        container.add(switchBtn, 0, 0, 5, 1); 
        btnClear = new Button(objDictionaryAction.getWord("CLEARPATTERN"));
        btnClear.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLEARPATTERN")));
        //clearBtn.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColour()+"/clear.png"));
        container.add(btnClear, 5, 0, 5, 1); 
        
        Label warpCL = new Label(objDictionaryAction.getWord("WARP")+" "+objDictionaryAction.getWord("PATTERN"));
        warpCL.setFont(Font.font("Arial", 14));
        container.add(warpCL, 0, 1);
        objFabricAction = new FabricAction();
        warpTF = new TextField(objFabricAction.getPattern(objFabric.getStrWarpPatternID()).getStrPattern().toUpperCase()){
            @Override public void replaceText(int start, int end, String text) {
              if (text.matches("[0-9A-Z]*")) {
                super.replaceText(start, end, text);
              }
            }
            @Override public void replaceSelection(String text) {
              if (text.matches("[0-9A-Z]*")) {
                super.replaceSelection(text);
              }
            }
        };
        //warpTF.setPrefWidth(400);
        warpTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
               warpIV.setImage(SwingFXUtils.toFXImage(getMatrix(objFabric.getStrWarpPatternID(), warpTF.getText(),(byte)1), null));
               threadIV.setImage(SwingFXUtils.toFXImage(getMatrix(null, null,(byte)0), null));
               getMyPattern(new Pattern(objFabric.getStrWarpPatternID(),warpTF.getText(), 1, 1, 1, new IDGenerator().setUserAcessKey("PATTERN_LIBRARY", objFabric.getObjConfiguration().getObjUser()),objFabric.getObjConfiguration().getObjUser().getStrUserID(), null));
            }
        });
        container.add(warpTF, 1, 1, 12, 1); 
        warpBrowseBtn = new Button(objDictionaryAction.getWord("BROWSE"));
        warpBrowseBtn.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBROWSE")));
        warpBrowseBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) { 
                Stage dialogStage = new Stage();
                dialogStage.initStyle(StageStyle.UTILITY);
                //dialogStage.initModality(Modality.WINDOW_MODAL);
                final ImageView patternIV = new ImageView();
                patternIV.setFitWidth(objFabric.getObjConfiguration().WIDTH/2);
                patternIV.setFitHeight(111);
                
                patternTable = new TableView<Pattern>();
                try {
                    objFabricAction = new FabricAction();
                    patternData = FXCollections.observableArrayList(objFabricAction.getAllPattern((byte)1));
                } catch (SQLException ex) {
                    new Logging("SEVERE",PatternView.class.getName(),ex.toString(),ex);
                } catch (Exception ex) {
                    new Logging("SEVERE",PatternView.class.getName(),ex.toString(),ex);
                }                
                TableColumn codeCol = new TableColumn("Code");
                TableColumn patternCol = new TableColumn("Warp Pattern");
                TableColumn repeatCol = new TableColumn("Repeat");
                TableColumn usedCol = new TableColumn("Used");
                TableColumn dateCol = new TableColumn("Date");
                codeCol.setCellValueFactory(new PropertyValueFactory<Pattern,String>("strPatternID"));
                patternCol.setCellValueFactory(new PropertyValueFactory<Pattern,String>("strPattern"));
                repeatCol.setCellValueFactory(new PropertyValueFactory<Pattern,String>("intPatternRepeat"));
                usedCol.setCellValueFactory(new PropertyValueFactory<Pattern,String>("intPatternUsed"));
                dateCol.setCellValueFactory(new PropertyValueFactory<Pattern,String>("strPatternDate"));
                
                patternTable.setItems(patternData);                
                patternTable.setEditable(true);
                patternTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                patternTable.getColumns().addAll(codeCol, patternCol, repeatCol, usedCol, dateCol);
                
                patternTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pattern>() {
                    @Override
                    public void changed(ObservableValue<? extends Pattern> ov, Pattern t, Pattern t1) {
                        warpTF.setText(t1.getStrPattern());
                        patternIV.setImage(SwingFXUtils.toFXImage(getMatrix(t1.getStrPatternID(), t1.getStrPattern().trim(),(byte)1), null));
                    }
                });
                patternTable.setPrefSize(objFabric.getObjConfiguration().WIDTH/2, (objFabric.getObjConfiguration().HEIGHT/2)-111);
                ScrollPane patternSP = new ScrollPane();
                patternSP.setPrefSize(objFabric.getObjConfiguration().WIDTH/2, (objFabric.getObjConfiguration().HEIGHT/2)-111);
                patternSP.setContent(patternTable);
                
                VBox container = new VBox();
                container.getChildren().addAll(patternSP,patternIV);
                dialogStage.setScene(new Scene(container));
                dialogStage.getIcons().add(new Image("/media/icon.png"));
                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WARP")+" "+objDictionaryAction.getWord("PATTERN"));
                dialogStage.showAndWait();
            }
        });
        container.add(warpBrowseBtn, 12, 1, 3, 1);
        
        Label weftCL = new Label(objDictionaryAction.getWord("WEFT")+" "+objDictionaryAction.getWord("PATTERN"));
        weftCL.setFont(Font.font("Arial", 14));
        container.add(weftCL, 0, 6);
        objFabricAction = new FabricAction();
        weftTF = new TextField(objFabricAction.getPattern(objFabric.getStrWeftPatternID()).getStrPattern()){
            @Override public void replaceText(int start, int end, String text) {
              if (text.matches("[0-9a-z]*")) {
                super.replaceText(start, end, text);
              }
            }
            @Override public void replaceSelection(String text) {
              if (text.matches("[0-9a-z]*")) {
                super.replaceSelection(text);
              }
            }
          };
        //weftTF.setPrefWidth(300);
        weftTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                weftIV.setImage(SwingFXUtils.toFXImage(getMatrix(objFabric.getStrWeftPatternID(), weftTF.getText(),(byte)2), null));
                threadIV.setImage(SwingFXUtils.toFXImage(getMatrix(null, null,(byte)0), null));
                getMyPattern(new Pattern(objFabric.getStrWeftPatternID(),weftTF.getText(), 2, 1, 1,new IDGenerator().setUserAcessKey("PATTERN_LIBRARY", objFabric.getObjConfiguration().getObjUser()),objFabric.getObjConfiguration().getObjUser().getStrUserID(), null));
            }
        }); 
        container.add(weftTF, 1, 6, 12, 1);
        weftBrowseBtn = new Button(objDictionaryAction.getWord("BROWSE"));
        weftBrowseBtn.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBROWSE")));
        weftBrowseBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) { 
                Stage dialogStage = new Stage();
                dialogStage.initStyle(StageStyle.UTILITY);
                //dialogStage.initModality(Modality.WINDOW_MODAL);
                final ImageView patternIV = new ImageView();
                patternIV.setFitWidth(111);
                patternIV.setFitHeight(objFabric.getObjConfiguration().HEIGHT/2);
                
                patternTable = new TableView<Pattern>();
                try {
                    objFabricAction = new FabricAction();
                    patternData = FXCollections.observableArrayList(objFabricAction.getAllPattern((byte)2));
                } catch (SQLException ex) {
                    new Logging("SEVERE",PatternView.class.getName(),ex.toString(),ex);
                } catch (Exception ex) {
                    new Logging("SEVERE",PatternView.class.getName(),ex.toString(),ex);
                }   
                TableColumn codeCol = new TableColumn("Code");
                TableColumn patternCol = new TableColumn("Weft Pattern");
                TableColumn repeatCol = new TableColumn("Repeat");
                TableColumn usedCol = new TableColumn("Used");
                TableColumn dateCol = new TableColumn("Date");
                codeCol.setCellValueFactory(new PropertyValueFactory<Pattern,String>("strPatternID"));
                patternCol.setCellValueFactory(new PropertyValueFactory<Pattern,String>("strPattern"));
                repeatCol.setCellValueFactory(new PropertyValueFactory<Pattern,String>("intPatternRepeat"));
                usedCol.setCellValueFactory(new PropertyValueFactory<Pattern,String>("intPatternUsed"));
                dateCol.setCellValueFactory(new PropertyValueFactory<Pattern,String>("strPatternDate"));
                
                patternTable.setItems(patternData);                
                patternTable.setEditable(true);
                patternTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                patternTable.getColumns().addAll(codeCol, patternCol, repeatCol, usedCol, dateCol);
                
                patternTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pattern>() {
                    @Override
                    public void changed(ObservableValue<? extends Pattern> ov, Pattern t, Pattern t1) {
                        weftTF.setText(t1.getStrPattern());                        
                        //generateWeftImage(t1.getPattern());
                        patternIV.setImage(SwingFXUtils.toFXImage(getMatrix(t1.getStrPatternID(), t1.getStrPattern().trim(),(byte)2), null));
                    }
                });
                patternTable.setPrefSize((objFabric.getObjConfiguration().WIDTH/2)-111, objFabric.getObjConfiguration().HEIGHT/2);
                ScrollPane patternSP = new ScrollPane();
                patternSP.setPrefSize((objFabric.getObjConfiguration().WIDTH/2)-111, objFabric.getObjConfiguration().HEIGHT/2);
                patternSP.setContent(patternTable);
                
                HBox container = new HBox();
                container.getChildren().addAll(patternSP,patternIV);
                dialogStage.setScene(new Scene(container));
                dialogStage.getIcons().add(new Image("/media/icon.png"));
                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WEFT")+" "+objDictionaryAction.getWord("PATTERN"));
                dialogStage.showAndWait();
            }
        });
        container.add(weftBrowseBtn, 12, 6, 3, 1);
                
        warpIV= new ImageView();
        warpIV.setFitWidth(99);
        warpIV.setFitHeight(44);
        container.add(warpIV, 0, 2, 1, 2);  
                
        weftIV= new ImageView();
        weftIV.setFitWidth(99);
        weftIV.setFitHeight(44);
        container.add(weftIV, 0, 4, 1, 2); 
        
        for(byte i=0; i<(byte)threadPaletes.length; i++){
            Label lblC= new Label(colorPaletes[i][0]);
            lblC.setUserData(i);
            setColorLabelProperty(lblC, threadPaletes[i], (byte)((i/26)+1));
            container.add(lblC, (i%13)+1, (i/13)+2);
        }
        
        final Label lblWarp= new Label("#");
        lblWarp.setUserData(-1);
        addLabelAction(lblWarp);
        container.add(lblWarp, 14, 3);
        /*final ColorPicker warpCP = new ColorPicker();
        warpCP.setStyle("-fx-color-label-visible: false ;");
        //weftCP.getStyleClass().add("preview");
        container.add(warpCP, 14, 2); 
        warpCP.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) { 
                Color color = warpCP.getValue();
                String rgb = String.format( "#%02X%02X%02X",(int)( color.getRed() * 255 ),(int)( color.getGreen() * 255 ),(int)( color.getBlue() * 255 ) );
                lblWarp.setStyle("-fx-background-color: "+rgb+"; -fx-font-size: 10px;");
            }
		});*/
		//<Added Date="25/10/2017">
        final ComboBox warpColorCB=new ComboBox();
        warpColorCB.setOnShown(new EventHandler() {
            @Override
            public void handle(Event t) {
                try {
                    ColourSelector objColourSelector=new ColourSelector(objFabric.getObjConfiguration());
                    if(objColourSelector.colorCode!=null&&objColourSelector.colorCode.length()>0){
                        warpColorCB.setStyle("-fx-background-color:#"+objColourSelector.colorCode+";");
                        lblWarp.setStyle("-fx-background-color: #"+objColourSelector.colorCode+"; -fx-font-size: 10px;");
                    }
                    warpColorCB.hide();
                    t.consume();
                } catch (Exception ex) {
                    new Logging("SEVERE",PatternView.class.getName(),"colourPalettePopup: warp",ex);
                }
            }
        });
        warpColorCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOLOR")));
        container.add(warpColorCB, 14, 2);
        final Label lblWeft= new Label("#");
        lblWeft.setUserData(-1);
        addLabelAction(lblWeft);
        container.add(lblWeft, 14, 5);        
        /*final ColorPicker weftCP = new ColorPicker();
        weftCP.setStyle("-fx-color-label-visible: false ;");
        //weftCP.getStyleClass().add("preview");
        container.add(weftCP, 14, 4); 
        weftCP.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) { 
                Color color = weftCP.getValue();
                String rgb = String.format( "#%02X%02X%02X",(int)( color.getRed() * 255 ),(int)( color.getGreen() * 255 ),(int)( color.getBlue() * 255 ) );
                lblWeft.setStyle("-fx-background-color: "+rgb+"; -fx-font-size: 10px;");
            }
		});*/
		//<Added Date="25/10/2017">
		final ComboBox weftColorCB=new ComboBox();
        weftColorCB.setOnShown(new EventHandler() {
            @Override
            public void handle(Event t) {
                try {
                    ColourSelector objColourSelector=new ColourSelector(objFabric.getObjConfiguration());
                    if(objColourSelector.colorCode!=null&&objColourSelector.colorCode.length()>0){
                        weftColorCB.setStyle("-fx-background-color:#"+objColourSelector.colorCode+";");
                        lblWeft.setStyle("-fx-background-color: #"+objColourSelector.colorCode+"; -fx-font-size: 10px;");
                    }
                    weftColorCB.hide();
                    t.consume();
                } catch (Exception ex) {
                    new Logging("SEVERE",PatternView.class.getName(),"colourPalettePopup: weft",ex);
                }
            }
        });
        weftColorCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOLOR")));
        container.add(weftColorCB, 14, 4);
        
        btnApply = new Button(objDictionaryAction.getWord("APPLY"));
        btnApply.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPAPPLY")));
        //btnApply.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColour()+"/save.png"));
        container.add(btnApply, 0, 7, 7, 1); 
        btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
        btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
        //btnCancel.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColour()+"/close.png"));
        container.add(btnCancel, 7, 7, 7, 1);
        
        threadIV = new ImageView();
        threadIV.setFitWidth(450);
        threadIV.setFitHeight(150);
        warpIV.setImage(SwingFXUtils.toFXImage(getMatrix(objFabric.getStrWarpPatternID(), warpTF.getText(),(byte)1), null));
        weftIV.setImage(SwingFXUtils.toFXImage(getMatrix(objFabric.getStrWeftPatternID(), weftTF.getText(),(byte)2), null));
        threadIV.setImage(SwingFXUtils.toFXImage(getMatrix(null, null,(byte)0), null));
        
        switchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String weftColor = weftTF.getText().toUpperCase();
                String warpColor = warpTF.getText().toLowerCase();
                weftTF.setText(warpColor);
                warpTF.setText(weftColor);
                weftColor = warpColor = null;
            }
        });
        btnClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                weftTF.setText("1a");
                warpTF.setText("1A");
            }
        });
        btnApply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if(weftTF.getText().matches("([0-9][0-9]*[a-z])*") && warpTF.getText().matches("([0-9][0-9]*[A-Z])*")){
                    try {
                        String patternID = new IDGenerator().getIDGenerator("PATTERN_LIBRARY", objFabric.getObjConfiguration().getObjUser().getStrUserID());
                        Pattern objPattern = new Pattern(patternID,warpTF.getText(), 1, 1, 1,new IDGenerator().setUserAcessKey("PATTERN_LIBRARY", objFabric.getObjConfiguration().getObjUser()),objFabric.getObjConfiguration().getObjUser().getStrUserID(), null);
                        objPattern.setObjConfiguration(objFabric.getObjConfiguration());
                        objFabric.setWarpYarn(getMyPattern(objPattern));
                        objFabricAction = new FabricAction();
                        objFabricAction.setPattern(objPattern);
                        objFabricAction = new FabricAction();
                        objFabric.setStrWarpPatternID(objFabricAction.getPatternAvibilityID(warpTF.getText(), 1));
                        objFabricAction = new FabricAction();
                        patternID = new IDGenerator().getIDGenerator("PATTERN_LIBRARY", objFabric.getObjConfiguration().getObjUser().getStrUserID());
                        objPattern = new Pattern(patternID,weftTF.getText(), 2, 1, 1,new IDGenerator().setUserAcessKey("PATTERN_LIBRARY", objFabric.getObjConfiguration().getObjUser()),objFabric.getObjConfiguration().getObjUser().getStrUserID(), null);
                        objPattern.setObjConfiguration(objFabric.getObjConfiguration());
                        objFabric.setWeftYarn(getMyPattern(objPattern));
                        objFabricAction = new FabricAction();
                        objFabricAction.setPattern(objPattern);
                        objFabricAction = new FabricAction();
                        objFabric.setStrWeftPatternID(objFabricAction.getPatternAvibilityID(weftTF.getText(), 2));
                        
                        objFabric.setThreadPaletes(threadPaletes);
                        System.gc();
                        patternStage.close();
                    } catch (SQLException ex) {
                        new Logging("SEVERE",PatternView.class.getName(),ex.toString(),ex);
                    } catch (Exception ex) {
                        new Logging("SEVERE",PatternView.class.getName(),ex.toString(),ex);
                    }
                }                        
            }
        });
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.gc();
                patternStage.close();
            }
        });        
        
        VBox root = new VBox();
        root.getChildren().addAll(container,threadIV);
        scene = new Scene(root, 450, 350, Color.WHITE);
        scene.getStylesheets().add(PatternView.class.getResource(objFabric.getObjConfiguration().getStrTemplate()+"/style.css").toExternalForm());
        
        patternStage = new Stage(); 
        patternStage.setScene(scene);
        patternStage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL
        patternStage.initStyle(StageStyle.UTILITY); 
        patternStage.getIcons().add(new Image("/media/icon.png"));
        patternStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWPATTERNEDITOR")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        patternStage.setIconified(false);
        patternStage.setResizable(false);
        patternStage.showAndWait();
    }
    
    public BufferedImage getMatrix(String strId, String strPattern, byte type){
        int w = 111;
        int h = 111; 
        Pattern objPattern = null;
        BufferedImage newImage = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
           
        if(type==0){
            byte[][] threadMatrix = objFabric.getBaseWeaveMatrix();
            objPattern = new Pattern(objFabric.getStrWarpPatternID(),warpTF.getText(), 1, 1, 1,new IDGenerator().setUserAcessKey("PATTERN_LIBRARY", objFabric.getObjConfiguration().getObjUser()),objFabric.getObjConfiguration().getObjUser().getStrUserID(), null);
            objPattern.setObjConfiguration(objFabric.getObjConfiguration());
            Yarn[] warpYarn = getMyPattern(objPattern);
            objPattern = new Pattern(objFabric.getStrWeftPatternID(),weftTF.getText(), 2, 1, 1,new IDGenerator().setUserAcessKey("PATTERN_LIBRARY", objFabric.getObjConfiguration().getObjUser()),objFabric.getObjConfiguration().getObjUser().getStrUserID(), null);
            objPattern.setObjConfiguration(objFabric.getObjConfiguration());
            Yarn[] weftYarn = getMyPattern(objPattern);
            int warpCount = warpYarn.length;
            int weftCount = weftYarn.length;
            int j = threadMatrix[0].length;
            int i = threadMatrix.length; 
            w = (int)objFabric.getObjConfiguration().WIDTH/2;
            h = 111;
            newImage = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
            for(int x = 0; x<h; x++){
                for(int y = 0; y<w; y++){
                    int rgb = 0;
                    if(threadMatrix[x%i][y%j]==1)
                        rgb = new java.awt.Color((float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).getRGB(); 
                    else
                        rgb = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRGB(); 
                    newImage.setRGB(y, x, rgb);
                }
            }            
        }else if(type==1){
            objPattern = new Pattern(strId,strPattern, 1, 1, 1,new IDGenerator().setUserAcessKey("PATTERN_LIBRARY", objFabric.getObjConfiguration().getObjUser()),objFabric.getObjConfiguration().getObjUser().getStrUserID(), null);
            objPattern.setObjConfiguration(objFabric.getObjConfiguration());
            Yarn[] yarnPattern = getMyPattern(objPattern);
            int repeat = yarnPattern.length;
            w = (int)objFabric.getObjConfiguration().WIDTH/2;
            h = 111; 
            newImage = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
            for(int x = 0; x<h; x++){
              for(int y = 0; y<w; y++){
                  int rgb = new java.awt.Color((float)Color.web(yarnPattern[y%repeat].getStrYarnColor()).getRed(),(float)Color.web(yarnPattern[y%repeat].getStrYarnColor()).getGreen(),(float)Color.web(yarnPattern[y%repeat].getStrYarnColor()).getBlue()).getRGB(); 
                  newImage.setRGB(y, x, rgb);
              }
           }
        }else if(type==2){
            objPattern = new Pattern(strId,strPattern, 2, 1, 1,new IDGenerator().setUserAcessKey("PATTERN_LIBRARY", objFabric.getObjConfiguration().getObjUser()),objFabric.getObjConfiguration().getObjUser().getStrUserID(), null);
            objPattern.setObjConfiguration(objFabric.getObjConfiguration());
            Yarn[] yarnPattern = getMyPattern(objPattern);
           int repeat = yarnPattern.length;                       
           w = 111;
           h = (int)objFabric.getObjConfiguration().HEIGHT/2; 
           newImage = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
           for(int x = 0; x<h; x++){
              for(int y = 0; y<w; y++){
                  int rgb = new java.awt.Color((float)Color.web(yarnPattern[x%repeat].getStrYarnColor()).getRed(),(float)Color.web(yarnPattern[x%repeat].getStrYarnColor()).getGreen(),(float)Color.web(yarnPattern[x%repeat].getStrYarnColor()).getBlue()).getRGB(); 
                  newImage.setRGB(y, x, rgb);
              }
           }
        }
        return newImage;
    }
    
    public Yarn[] getMyPattern(Pattern objPattern){
        int repeat = 0;
        objPattern.setObjConfiguration(objFabric.getObjConfiguration());
        String[] patternArray = new String[objPattern.getStrPattern().length()];
        for ( int i=0; i < objPattern.getStrPattern().length(); i++ ) {
            char c = objPattern.getStrPattern().charAt( i );
            if(Character.isDigit(c)) {
                if(patternArray[repeat]==null)
                    patternArray[repeat] = String.valueOf(c);         
                else
                    patternArray[repeat] += String.valueOf(c); 
            }else if(Character.isLetter(c)){
                if(repeat==0 && patternArray[repeat]==null)
                   patternArray[repeat] = "0"; 
                repeat++;
                patternArray[repeat++] = String.valueOf(c);                    
            }  
        }                           
        repeat = 0;
        int symbol = 0;
        for ( int i = 0; i < patternArray.length; i++ ) {
            if(patternArray[i]!=null && StringUtils.isNumeric(patternArray[i]))
                repeat +=Integer.parseInt(patternArray[i]);
            else if(patternArray[i]!=null && StringUtils.isAlpha(patternArray[i]))
                symbol++;                
        }
        objPattern.setIntPatternUsed(symbol);
        objPattern.setIntPatternRepeat(repeat);
        yarnPattern = new Yarn[repeat];
        int count = 0;
        for ( int i = 0; i < patternArray.length; i++ ) {
            if(patternArray[i]!=null && StringUtils.isNumeric(patternArray[i]))
                for ( int j = 0; j < Integer.parseInt(patternArray[i]); j++,count++ ) {
                    Yarn objYarnNew = null;
                    if(objPattern.getIntPatternType()==1){            
                        objYarnNew = new Yarn(null, "Warp", objFabric.getObjConfiguration().getStrWarpName(), "#"+warpPattern.get(patternArray[i+1]), repeat, patternArray[i+1], objFabric.getObjConfiguration().getIntWarpCount(), objFabric.getObjConfiguration().getStrWarpUnit(), objFabric.getObjConfiguration().getIntWarpPly(), objFabric.getObjConfiguration().getIntWarpFactor(), objFabric.getObjConfiguration().getDblWarpDiameter(), objFabric.getObjConfiguration().getIntWarpTwist(), objFabric.getObjConfiguration().getStrWarpSence(), objFabric.getObjConfiguration().getIntWarpHairness(), objFabric.getObjConfiguration().getIntWarpDistribution(), objFabric.getObjConfiguration().getDblWarpPrice(), objFabric.getObjConfiguration().getObjUser().getUserAccess("YARN_LIBRARY"),objFabric.getObjConfiguration().getObjUser().getStrUserID(),null);
                    }else if(objPattern.getIntPatternType()==2){
                        objYarnNew = new Yarn(null, "Weft", objFabric.getObjConfiguration().getStrWeftName(), "#"+weftPattern.get(patternArray[i+1]), repeat, patternArray[i+1], objFabric.getObjConfiguration().getIntWeftCount(), objFabric.getObjConfiguration().getStrWeftUnit(), objFabric.getObjConfiguration().getIntWeftPly(), objFabric.getObjConfiguration().getIntWeftFactor(), objFabric.getObjConfiguration().getDblWeftDiameter(), objFabric.getObjConfiguration().getIntWeftTwist(), objFabric.getObjConfiguration().getStrWeftSence(), objFabric.getObjConfiguration().getIntWeftHairness(), objFabric.getObjConfiguration().getIntWeftDistribution(), objFabric.getObjConfiguration().getDblWeftPrice(), objFabric.getObjConfiguration().getObjUser().getUserAccess("YARN_LIBRARY"),objFabric.getObjConfiguration().getObjUser().getStrUserID(),null);
                    }
                    objYarnNew.setObjConfiguration(objFabric.getObjConfiguration());
                    yarnPattern[count] = objYarnNew;
                }
        }        
        patternArray = null;
        return yarnPattern;
    }
    
    public void setColorLabelProperty(final Label lblC, String color, byte type){
        lblC.setStyle("-fx-background-color: #"+color+"; -fx-font-size: 10; -fx-width:25px; -fx-height:25px;");
        if(type==2){
            lblC.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    weftTF.setText(weftTF.getText()+"1"+lblC.getText().trim().substring(0,1).toLowerCase());
                    lblC.setText(lblC.getText().trim().substring(0,1)+"*");
                    String colorbg = lblC.getStyle().substring(lblC.getStyle().lastIndexOf("-fx-background-color:")+21,lblC.getStyle().indexOf(";")).trim();
               }
           });
        }else if(type==1){
            lblC.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    warpTF.setText(warpTF.getText()+"1"+lblC.getText().trim().substring(0,1).toUpperCase());
                    lblC.setText(lblC.getText().trim().substring(0,1)+"*");
                    String colorbg = lblC.getStyle().substring(lblC.getStyle().lastIndexOf("-fx-background-color:")+21,lblC.getStyle().indexOf(";")).trim();
               }
           });
        }
        addLabelAction(lblC);
    }
    
    private void addLabelAction(final Label lblC){
        lblC.setPrefSize(25, 25);
        lblC.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                Dragboard db = lblC.startDragAndDrop(TransferMode.ANY);

                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(lblC.getStyle().substring(lblC.getStyle().lastIndexOf("-fx-background-color:")+21,lblC.getStyle().indexOf(";")).trim());
                db.setContent(content);
                xindex = (byte)Integer.parseInt(lblC.getUserData().toString());
                event.consume();
            }
        });
        lblC.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                /* accept it only if it is not dragged from the same node 
                 * and if it has a string data */
                if (event.getGestureSource() != lblC && event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });
        lblC.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
            /* the drag-and-drop gesture entered the target */
            /* show to the user that it is an actual gesture target */
                 if (event.getGestureSource() != lblC && event.getDragboard().hasString()) {
                     lblC.setTextFill(Color.GREEN);
                 }
                 event.consume();
            }
        });
        lblC.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
                lblC.setTextFill(Color.BLACK);
                event.consume();
            }
        });
        lblC.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {                    
                   ClipboardContent content = new ClipboardContent();
                   content.putString(lblC.getStyle().substring(lblC.getStyle().lastIndexOf("-fx-background-color:")+21,lblC.getStyle().indexOf(";")).trim());                
                   lblC.setStyle("-fx-background-color: "+db.getString()+"; -fx-font-size: 10; -fx-width:25px; -fx-height:25px;");
                   yindex = (byte)Integer.parseInt(lblC.getUserData().toString());
                   if(xindex==-1 && yindex>-1)
                       threadPaletes[yindex]=db.getString().substring(db.getString().lastIndexOf("#")+1);
                   else if(xindex>-1 && yindex==-1)
                       threadPaletes[xindex]=content.getString().substring(content.getString().lastIndexOf("#")+1);
                   db.setContent(content);                   
                   success = true;                   
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);
                event.consume();
             }
        });
        lblC.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag and drop gesture ended */
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    Dragboard db = event.getDragboard();
                    // swapThreadPatterns(lblC.getStyle().substring(lblC.getStyle().lastIndexOf("-fx-background-color:")+21,lblC.getStyle().indexOf(";")).trim(),db.getString());              
                    lblC.setStyle("-fx-background-color: "+db.getString()+"; -fx-font-size: 10; -fx-width:25px; -fx-height:25px;");
                    swapThreadPaltes();
                }
                event.consume();
            }
        });
    }
    
    private void swapThreadPaltes(){
        String temp =null;
        if(xindex>-1 && yindex>-1){
            temp = threadPaletes[xindex];
            threadPaletes[xindex] = threadPaletes[yindex];
            threadPaletes[yindex] = temp;
        }
        initilizePattern();
    }
    
    private void initilizePattern(){
        warpPattern.put("A", threadPaletes[0]);
        warpPattern.put("B", threadPaletes[1]);
        warpPattern.put("C", threadPaletes[2]);
        warpPattern.put("D", threadPaletes[3]);
        warpPattern.put("E", threadPaletes[4]);
        warpPattern.put("F", threadPaletes[5]);
        warpPattern.put("G", threadPaletes[6]);
        warpPattern.put("H", threadPaletes[7]);
        warpPattern.put("I", threadPaletes[8]);
        warpPattern.put("J", threadPaletes[9]);
        warpPattern.put("K", threadPaletes[10]);
        warpPattern.put("L", threadPaletes[11]);
        warpPattern.put("M", threadPaletes[12]);
        warpPattern.put("N", threadPaletes[13]);
        warpPattern.put("O", threadPaletes[14]);
        warpPattern.put("P", threadPaletes[15]);
        warpPattern.put("Q", threadPaletes[16]);
        warpPattern.put("R", threadPaletes[17]);
        warpPattern.put("S", threadPaletes[18]);
        warpPattern.put("T", threadPaletes[19]);
        warpPattern.put("U", threadPaletes[20]);
        warpPattern.put("V", threadPaletes[21]);
        warpPattern.put("W", threadPaletes[22]);
        warpPattern.put("X", threadPaletes[23]);
        warpPattern.put("Y", threadPaletes[24]);
        warpPattern.put("Z", threadPaletes[25]);
        weftPattern.put("a", threadPaletes[26]);
        weftPattern.put("b", threadPaletes[27]);
        weftPattern.put("c", threadPaletes[28]);
        weftPattern.put("d", threadPaletes[29]);
        weftPattern.put("e", threadPaletes[30]);
        weftPattern.put("f", threadPaletes[31]);
        weftPattern.put("g", threadPaletes[32]);
        weftPattern.put("h", threadPaletes[33]);
        weftPattern.put("i", threadPaletes[34]);
        weftPattern.put("j", threadPaletes[35]);
        weftPattern.put("k", threadPaletes[36]);
        weftPattern.put("l", threadPaletes[37]);
        weftPattern.put("m", threadPaletes[38]);
        weftPattern.put("n", threadPaletes[39]);
        weftPattern.put("o", threadPaletes[40]);
        weftPattern.put("p", threadPaletes[41]);
        weftPattern.put("q", threadPaletes[42]);
        weftPattern.put("r", threadPaletes[43]);
        weftPattern.put("s", threadPaletes[44]);
        weftPattern.put("t", threadPaletes[45]);
        weftPattern.put("u", threadPaletes[46]);
        weftPattern.put("v", threadPaletes[47]);
        weftPattern.put("w", threadPaletes[48]);
        weftPattern.put("x", threadPaletes[49]);
        weftPattern.put("y", threadPaletes[50]);
        weftPattern.put("z", threadPaletes[51]);
    }
    public void start(Stage stage) throws Exception {
        stage.initOwner(FabricView.fabricStage);
        new PatternView(stage);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    public static void main(String[] args) {   
      launch(args);    
    }
}
