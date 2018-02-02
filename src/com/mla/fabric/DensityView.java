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

import com.mla.artwork.ArtworkAction;
import com.mla.dictionary.DictionaryAction;
import com.mla.main.Configuration;
import com.mla.main.Logging;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 *
 * @Designing GUI window for weave editor
 * @author Amit Kumar Singh
 * 
 */
public class DensityView {
   
    private Fabric objFabric;
    private Configuration objConfiguration;
    DictionaryAction objDictionaryAction;
    
    private Stage densityStage;
    GridPane container; 
    
    private Button btnApply;
    private Button btnCancel;
    
    private TextField weftDTF;
    private TextField weftRTF;
    private TextField warpDTF;
    private TextField warpRTF;
    private ImageView threadIV;
    ImageView warpIV;
    ImageView weftIV;
    
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;
    
    public DensityView(final Stage primaryStage) {  }

    public DensityView(Fabric objFabricCall) {   
        this.objFabric = objFabricCall;
        objDictionaryAction = new DictionaryAction(objFabric.getObjConfiguration());
        objConfiguration = objFabric.getObjConfiguration();
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 350, 350, Color.WHITE);
        scene.getStylesheets().add(DensityView.class.getResource(objFabric.getObjConfiguration().getStrTemplate()+"/style.css").toExternalForm());
        HBox footContainer = new HBox();
        progressB = new ProgressBar(0);
        progressB.setVisible(false);
        progressI = new ProgressIndicator(0);
        progressI.setVisible(false);
        lblStatus = new Label(objDictionaryAction.getWord("WELCOME"));
        lblStatus.setId("message");
        footContainer.getChildren().addAll(lblStatus,progressB,progressI);
        
        container = new GridPane();
        container.setId("popup");
        container.setVgap(5);
        container.setHgap(5);
        container.autosize();
        
        Label LabelDL = new Label("Density (Thread/inch)");
        LabelDL.setFont(Font.font("Arial", 15));
        container.add(LabelDL, 0, 0, 2, 1);
        Label repeatDL = new Label("Repeat Size (inch)");
        repeatDL.setFont(Font.font("Arial", 15));
        container.add(repeatDL, 2, 0, 2, 1);
        
        Label warpDL = new Label(objDictionaryAction.getWord("EPI"));
        container.add(warpDL, 0, 1);
        warpDTF = new TextField(Integer.toString(objFabric.getIntEPI())){
            @Override public void replaceText(int start, int end, String text) {
              if (text.matches("[0-9]*")) {
                super.replaceText(start, end, text);
              }
            }
            @Override public void replaceSelection(String text) {
              if (text.matches("[0-9]*")) {
                super.replaceSelection(text);
              }
            }
        };
        warpDTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(warpDTF.getText()==null || warpDTF.getText()=="")
                    warpDTF.setText(Integer.toString(objFabric.getIntEPI()));
                else if(Integer.parseInt(warpDTF.getText())<=0)
                    warpDTF.setText(Integer.toString(objFabric.getIntEPI()));
                double dblArtworkWidth = ((double) objFabric.getIntWarp())/Integer.parseInt(warpDTF.getText());
                dblArtworkWidth = dblArtworkWidth*10000;
                dblArtworkWidth = Math.round(dblArtworkWidth);
                dblArtworkWidth = dblArtworkWidth/10000;
                warpRTF.setText(Double.toString(dblArtworkWidth));
                threadIV.setImage(SwingFXUtils.toFXImage(getMatrix(), null));
            }
        });
        container.add(warpDTF, 1, 1); 
        Label warpRL = new Label(objDictionaryAction.getWord("ARTWORKWIDTH"));
        container.add(warpRL, 2, 1);
        warpRTF = new TextField(Double.toString(((double) objFabric.getIntWarp())/objFabric.getIntEPI())){//objFabric.getDblArtworkWidth())){
            @Override public void replaceText(int start, int end, String text) {
              if (text.matches("[0-9]*")) {
                super.replaceText(start, end, text);
              }
            }
            @Override public void replaceSelection(String text) {
              if (text.matches("[0-9]*")) {
                super.replaceSelection(text);
              }
            }
        };
        /*warpRTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(warpRTF.getText()==null || warpRTF.getText()=="")
                    warpRTF.setText(Float.toString(1));
                else if(Float.parseFloat(warpRTF.getText())<=0)
                    warpRTF.setText(Float.toString(1));
                warpDTF.setText(String.valueOf((int)(objFabric.getIntWarp()/Float.parseFloat(warpRTF.getText().toString())))); 
                threadIV.setImage(SwingFXUtils.toFXImage(getMatrix(), null));
            }
        });*/
        warpRTF.setDisable(true);
        container.add(warpRTF, 3, 1);
        
        Label weftDL = new Label(objDictionaryAction.getWord("PPI"));
        container.add(weftDL, 0, 2);
        weftDTF = new TextField(Integer.toString(objFabric.getIntPPI())){
            @Override public void replaceText(int start, int end, String text) {
              if (text.matches("[0-9]*")) {
                super.replaceText(start, end, text);
              }
            }
            @Override public void replaceSelection(String text) {
              if (text.matches("[0-9]*")) {
                super.replaceSelection(text);
              }
            }
          };
        weftDTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(weftDTF.getText()==null || weftDTF.getText()=="")
                    weftDTF.setText(Integer.toString(objFabric.getIntPPI()));
                else if(Integer.parseInt(weftDTF.getText())<=0)
                    weftDTF.setText(Integer.toString(objFabric.getIntPPI()));
                
                double dblArtworkLength = ((double) objFabric.getIntWeft())/Integer.parseInt(weftDTF.getText());
                dblArtworkLength = dblArtworkLength*10000;
                dblArtworkLength = Math.round(dblArtworkLength);
                dblArtworkLength = dblArtworkLength/10000;
                weftRTF.setText(Double.toString(dblArtworkLength)); 
                threadIV.setImage(SwingFXUtils.toFXImage(getMatrix(), null));
            }
        }); 
        container.add(weftDTF, 1, 2);
        Label weftRL = new Label(objDictionaryAction.getWord("ARTWORKLENGTH"));
        container.add(weftRL, 2, 2);
        weftRTF = new TextField(Double.toString(((double) objFabric.getIntWeft())/objFabric.getIntPPI())){//objFabric.getDblArtworkLength())){
            @Override public void replaceText(int start, int end, String text) {
              if (text.matches("[0-9]*.[0-9]*")) {
                super.replaceText(start, end, text);
              }
            }
            @Override public void replaceSelection(String text) {
              if (text.matches("[0-9]*.[0-9]*")) {
                super.replaceSelection(text);
              }
            }
          };
        /*weftRTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(weftRTF.getText()==null || weftRTF.getText()=="")
                    weftRTF.setText(Float.toString(1));
                else if(Float.parseFloat(weftRTF.getText())<=0)
                    weftRTF.setText(Float.toString(1));
                weftDTF.setText(String.valueOf((int)(objFabric.getIntWeft()/Float.parseFloat(weftRTF.getText().toString())))); 
                threadIV.setImage(SwingFXUtils.toFXImage(getMatrix(), null));
            }
        });*/ 
        weftRTF.setDisable(true);
        container.add(weftRTF, 3, 2);
        
        btnApply = new Button(objDictionaryAction.getWord("APPLY"));
        btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));

        //btnUpdate.setMaxWidth(Double.MAX_VALUE);
        //btnClose.setMaxWidth(Double.MAX_VALUE);        

        btnApply.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColour()+"/save.png"));
        btnCancel.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColour()+"/close.png"));
        
        btnApply.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPAPPLY")));
        btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
        
        btnCancel.setDefaultButton(true);
        //btnApply.setDisable(false);
        
        btnApply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                                        
               objFabric.setIntEPI(Integer.parseInt(warpDTF.getText()));
               objFabric.setIntHPI((int) Math.ceil(objFabric.getIntEPI()/(double)objFabric.getIntTPD()));
               objFabric.setIntPPI(Integer.parseInt(weftDTF.getText()));
               objFabric.setDblArtworkLength(Float.parseFloat(weftRTF.getText()));
               objFabric.setDblArtworkWidth(Float.parseFloat(warpRTF.getText()));
               densityStage.close();
            }
        });
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                densityStage.close();
            }
        });
        
        container.add(btnApply, 1, 3); 
        container.add(btnCancel, 3, 3);
        
        threadIV = new ImageView(objFabric.getObjConfiguration().getStrColour()+"/help.png");
        threadIV.setFitWidth(350);
        threadIV.setFitHeight(150);
        
        threadIV.setImage(SwingFXUtils.toFXImage(getMatrix(), null));
        //container.add(threadIV, 0, 4, 4, 1);
        lblStatus.setText("Most monitors display images at approximately 75DPI");
        
        root.setTop(container);
        root.setCenter(threadIV);
        root.setBottom(footContainer);
        densityStage = new Stage(); 
        densityStage.setScene(scene);
        densityStage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL
        densityStage.initStyle(StageStyle.UTILITY); 
        densityStage.getIcons().add(new Image("/media/icon.png"));
        densityStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWDENSITY")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        densityStage.setIconified(false);
        densityStage.setResizable(false);
        densityStage.showAndWait();
    }
    
    public BufferedImage getMatrix(){
        int intLength = objFabric.getIntWarp();
        int intWidth = objFabric.getIntWeft();
            
        int dpi = objConfiguration.getIntDPI();
        int warpFactor = dpi/Integer.parseInt(warpDTF.getText());
        int weftFactor = dpi/Integer.parseInt(weftDTF.getText());
        
        BufferedImage bufferedImage = new BufferedImage(intLength, intWidth,BufferedImage.TYPE_INT_RGB);
        FabricAction objFabricAction = null;
        try {
            objFabricAction = new FabricAction();
        } catch (SQLException ex) {
            new Logging("SEVERE",DensityView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
        bufferedImage = objFabricAction.plotCompositeView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), intLength, intWidth);
        if((Integer.parseInt(warpDTF.getText())/Integer.parseInt(weftDTF.getText()))<=0)
            intWidth = intLength * (Integer.parseInt(weftDTF.getText())/Integer.parseInt(warpDTF.getText()));
        else
            intLength = intWidth * (Integer.parseInt(warpDTF.getText())/Integer.parseInt(weftDTF.getText()));
        
        BufferedImage newImage = new BufferedImage(intLength, intWidth, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(bufferedImage, 0, 0, intLength, intWidth, null);
        g.dispose();
        bufferedImage = newImage;
        if(objConfiguration.getBlnMRepeat()){
            try {
                ArtworkAction objArtworkAction = new ArtworkAction();
                newImage = objArtworkAction.getImageRepeat(bufferedImage, 1, ((int)(350/intWidth)>0)?(int)(350/intWidth):1);
            } catch (SQLException ex) {
                new Logging("SEVERE",DensityView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        bufferedImage = null;
        System.gc();
        return newImage;
     }
    /*
     public BufferedImage getMatrix(){
        int w = 333;
        int h = 111; 
        int dpi = objConfiguration.getIntDPI();
        int warpFactor = dpi/Integer.parseInt(warpDTF.getText());
        int weftFactor = dpi/Integer.parseInt(weftDTF.getText());
        byte[][] threadMatrix = objFabric.getBaseWeaveMatrix();        
        Yarn[] warpYarn = objFabric.getWeftYarn();
        Yarn[] weftYarn = objFabric.getWarpYarn();
        int warpCount = warpYarn.length;
        int weftCount = weftYarn.length;
        int j = threadMatrix[0].length;
        int i = threadMatrix.length;
        BufferedImage newImage = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);   
        for(int x = 0; x<h; x++){
            int weft = x/weftFactor;
            if(x%weftFactor==0){
                for(int y = 0; y<w; y++){
                    int rgb = 0;
                    int warp = y/warpFactor;
                    if(y%warpFactor==0){
                        if(threadMatrix[weft%i][warp%j]==0)
                            rgb = new java.awt.Color((float)Color.web(weftYarn[weft%weftCount].getThreadColor()).getRed(),(float)Color.web(weftYarn[weft%weftCount].getThreadColor()).getGreen(),(float)Color.web(weftYarn[weft%weftCount].getThreadColor()).getBlue()).getRGB(); 
                        else
                            rgb = new java.awt.Color((float)Color.web(warpYarn[warp%warpCount].getThreadColor()).getRed(),(float)Color.web(warpYarn[warp%warpCount].getThreadColor()).getGreen(),(float)Color.web(warpYarn[warp%warpCount].getThreadColor()).getBlue()).getRGB();
                    }else{
                        rgb = new java.awt.Color((float)Color.LIGHTGRAY.getRed(),(float)Color.LIGHTGRAY.getGreen(),(float)Color.LIGHTGRAY.getBlue()).getRGB();
                    }
                    newImage.setRGB(y, x, rgb);
              }
            }else{
                for(int y = 0; y<w; y++){
                    int rgb = new java.awt.Color((float)Color.LIGHTGRAY.getRed(),(float)Color.LIGHTGRAY.getGreen(),(float)Color.LIGHTGRAY.getBlue()).getRGB();
                    newImage.setRGB(y, x, rgb);
              }
            }
        }  
        return newImage;
     }
      */  
    public void start(Stage stage) throws Exception {
        stage.initOwner(FabricView.fabricStage);
        new DensityView(stage);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    public static void main(String[] args) {   
      launch(args);    
    } 
}
