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

import com.mla.main.Logging;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.lang.StringUtils;
/**
 *
 * @Designing GUI window for pattern import
 * @author Amit Kumar Singh
 * 
 */
public class PatternImportView {
    
    private FabricAction objFabricAction;
    private Pattern objPattern;
    private Stage patternStage;
    private BorderPane root;
    private Scene scene;
    GridPane container;
    ArrayList<Pattern> lstPattern=new ArrayList<Pattern>();
    ImageView patternIV;
    
    GraphicsDevice objGraphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    double WIDTH = objGraphicsDevice.getDisplayMode().getWidth()/2;
    double HEIGHT = objGraphicsDevice.getDisplayMode().getHeight()/2;
    
    private TableView<Pattern> patternTable ;
    private ObservableList<Pattern> patternData = null;
    private String[] threadPaletes; //= new String[]{"585858", "B40431", "B4045F", "B40486", "B404AE", "8904B1", "5F04B4", "3104B4", "0404B4", "0431B4", "045FB4", "0489B1", "04B4AE", "04B486", "04B45F", "04B431", "04B404", "31B404", "5FB404", "86B404", "AEB404", "B18904", "B45F04", "B43104", "B40404", "FFFFFF", "585858", "B40431", "B4045F", "B40486", "B404AE", "8904B1", "5F04B4", "3104B4", "0404B4", "0431B4", "045FB4", "0489B1", "04B4AE", "04B486", "04B45F", "04B431", "04B404", "31B404", "5FB404", "86B404", "AEB404", "B18904", "B45F04", "B43104", "B40404", "FFFFFF"}; 
    byte[][] threadMatrix = new byte[][]{{1,0},{0,1}};
    Map<String,String> warpPattern = new HashMap<>();
    Map<String,String> weftPattern = new HashMap<>();
    
    byte xindex =-1, yindex=-1;
    
    public PatternImportView(final Stage primaryStage) {  }

    public PatternImportView(Pattern objPatternCall, byte type, String[] threadPalete) {   
        this.objPattern = objPatternCall;
        this.threadPaletes = threadPalete;
        initilizePattern();
        patternStage = new Stage();
        patternStage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL
        patternStage.initStyle(StageStyle.UTILITY);
        root = new BorderPane();
        scene = new Scene(root, WIDTH, HEIGHT, Color.WHITE);
        //scene.getStylesheets().add(PatternImportView.class.getResource(objFabric.getObjConfiguration().getStrTemplate()+"/style.css").toExternalForm());
        patternTable = new TableView<Pattern>();
        try {
            objFabricAction = new FabricAction();
            lstPattern = objFabricAction.getAllPattern(type);
        } catch (SQLException ex) {
            new Logging("SEVERE",PatternImportView.class.getName(),ex.toString(),ex);
        } catch (Exception ex) {
            new Logging("SEVERE",PatternImportView.class.getName(),ex.toString(),ex);
        }  
        patternData = FXCollections.observableArrayList(lstPattern);
        TableColumn codeCol = new TableColumn("Code");
        TableColumn patternCol = new TableColumn("Pattern");
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
                objPattern = t1;
                patternIV.setImage(SwingFXUtils.toFXImage(getMatrix(t1.getStrPattern().trim(),(byte)1), null));
            }
        });
        patternTable.setPrefSize(WIDTH, HEIGHT);
        ScrollPane patternSP = new ScrollPane();
        patternSP.setPrefSize(WIDTH, HEIGHT);
        patternSP.setContent(patternTable);
        patternSP.setId("popup");
        root.setCenter(patternSP);
        
        patternIV = new ImageView();
        if(type==1){
            patternIV.setFitWidth(WIDTH);
            patternIV.setFitHeight(111);
            root.setBottom(patternIV);
        }else{
            patternIV.setFitWidth(111);
            patternIV.setFitHeight(HEIGHT);
            root.setRight(patternIV);
        }
        
        patternStage.setScene(scene);
        patternStage.getIcons().add(new Image("/media/icon.png"));
        patternStage.setTitle("Bunkar Saathi: Thread Pattern");
        patternStage.showAndWait();
        
    }
    
    public BufferedImage getMatrix(String pattern, byte type){
        int w = 111;
        int h = 111; 
        BufferedImage newImage = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
        if(type==1){
           String[] colorPattern = getMyPattern(pattern, type);
           int repeat = colorPattern.length;
           w = (int)WIDTH;
           h = 111; 
           newImage = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
           for(int x = 0; x<h; x++){
              for(int y = 0; y<w; y++){
                  int rgb = new java.awt.Color((float)Color.web("#"+colorPattern[y%repeat]).getRed(),(float)Color.web("#"+colorPattern[y%repeat]).getGreen(),(float)Color.web("#"+colorPattern[y%repeat]).getBlue()).getRGB(); 
                  newImage.setRGB(y, x, rgb);
              }
           }
        }else if(type==2){
           String[] colorPattern = getMyPattern(pattern, type);
           int repeat = colorPattern.length;                       
           w = 111;
           h = (int)HEIGHT; 
           newImage = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
           for(int x = 0; x<h; x++){
              for(int y = 0; y<w; y++){
                  int rgb = new java.awt.Color((float)Color.web("#"+colorPattern[x%repeat]).getRed(),(float)Color.web("#"+colorPattern[x%repeat]).getGreen(),(float)Color.web("#"+colorPattern[x%repeat]).getBlue()).getRGB(); 
                  newImage.setRGB(y, x, rgb);
              } 
           }
        }
        return newImage;
    }
    
    
    public String[] getMyPattern(String pattern, byte type){
        int repeat = 0;
        String[] patternArray = new String[pattern.length()];
        for ( int i=0; i < pattern.length(); i++ ) {
            char c = pattern.charAt( i );
            if(Character.isDigit(c)) {
                if(patternArray[repeat]==null)
                    patternArray[repeat] = String.valueOf(c);         
                else
                    patternArray[repeat] += String.valueOf(c); 
            }else if(Character.isLetter(c)){
                if(repeat==0 && patternArray[repeat]==null)
                   patternArray[repeat] = "1"; 
                repeat++;
                patternArray[repeat++] = String.valueOf(c);                    
            }  
        }                           
        repeat = 0;
        for ( int i = 0; i < patternArray.length; i++ ) {
            if(patternArray[i]!=null && StringUtils.isNumeric(patternArray[i]))
                repeat +=Integer.parseInt(patternArray[i]);
        }
        String[] colorPattern = new String[repeat];
        repeat = 0;
        for ( int i = 0; i < patternArray.length; i++ ) {
            if(patternArray[i]!=null && StringUtils.isNumeric(patternArray[i]))
                for ( int j = 0; j < Integer.parseInt(patternArray[i]); j++,repeat++ ) {
                    if(type==1){            
                        colorPattern[repeat] = warpPattern.get(patternArray[i+1]);
                    }else if(type==2){
                        colorPattern[repeat] = weftPattern.get(patternArray[i+1]);                    
                    }
                }
        }        
        patternArray = null;
        return colorPattern;
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
    
}