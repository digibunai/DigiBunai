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

package com.mla.utility;

import com.mla.dictionary.DictionaryAction;
import com.mla.main.Logging;
import com.mla.main.MessageView;
import com.sun.media.jai.codec.ByteArraySeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.sql.SQLException;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.media.jai.PlanarImage;

/**
 *
 * @Designing GUI window for fabric import
 * @author Amit Kumar Singh
 * 
 */
public class SimulatorImportView {
    
   Simulator objSimulator=null;
   DictionaryAction objDictionaryAction;
   
   private BorderPane root;
   private Scene scene;
   private Stage simulationStage;
   
   private GridPane GP_container;
   public SimulatorImportView(final Stage primaryStage) {  }
    /*
    @author Amit Singh
    function for creating weave import window     
    */
   public SimulatorImportView(Simulator objSimulatorCall) {
        objSimulator = objSimulatorCall;
        objDictionaryAction = new DictionaryAction(objSimulator.getObjConfiguration());
        
        simulationStage = new Stage();
        simulationStage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL
        simulationStage.initStyle(StageStyle.UTILITY);
        root = new BorderPane();
        scene = new Scene(root, 450, 350, Color.WHITE);
        scene.getStylesheets().add(SimulatorImportView.class.getResource(objSimulator.getObjConfiguration().getStrTemplate()+"/style.css").toExternalForm());
        GridPane topContainer = new GridPane();
        
        Label L_name = new Label(objDictionaryAction.getWord("NAME"));
        L_name.setId("filename");
        topContainer.add(L_name, 0, 0);
        
        final TextField TF_name = new TextField();
        TF_name.setPromptText(objDictionaryAction.getWord("PROMPTNAME"));
        topContainer.add(TF_name, 1, 0);
        
        Label L_fabricType = new Label(objDictionaryAction.getWord("FABRICTYPE"));
        L_fabricType.setId("fileSearch");
        topContainer.add(L_fabricType, 2, 0);
        
        ComboBox CB_fabricType = new ComboBox();
        CB_fabricType.getItems().addAll("Plain","Kadhua","Fekuwa-Float","Fekuwa-Cutwork","Binding-Irregular","Binding-Regular","Tanchoi","Tissue","All");
        CB_fabricType.setPromptText(objDictionaryAction.getWord("FABRICTYPE"));
        CB_fabricType.setEditable(false); 
        CB_fabricType.setValue("All");
        topContainer.add(CB_fabricType, 3, 0);
        
        Label L_EPI = new Label(objDictionaryAction.getWord("EPI"));
        L_EPI.setId("fileSearch");
        topContainer.add(L_EPI, 0, 1);
        
        final TextField TF_EPI = new TextField();
        TF_EPI.setPromptText(objDictionaryAction.getWord("PROMPTEPI"));
        topContainer.add(TF_EPI, 1, 1);
        
        Label L_PPI = new Label(objDictionaryAction.getWord("PPI"));
        L_PPI.setId("fileSearch");
        topContainer.add(L_PPI, 2, 1);
        
        final TextField TF_PPI = new TextField();
        TF_PPI.setPromptText(objDictionaryAction.getWord("PROMPTPPI"));
        topContainer.add(TF_PPI, 3, 1);
        
        Label L_sortBy = new Label(objDictionaryAction.getWord("SORTBY"));
        L_sortBy.setId("fileSort");
        topContainer.add(L_sortBy, 0, 2);
        
        ComboBox CB_sortBy = new ComboBox();
        CB_sortBy.getItems().addAll(
            "Name",
            "Date"
        );
        CB_sortBy.setPromptText(objDictionaryAction.getWord("SORTBY"));
        CB_sortBy.setEditable(false); 
        CB_sortBy.setValue("Name"); 
        topContainer.add(CB_sortBy, 1, 2); 
                
        Label L_sortDirection = new Label(objDictionaryAction.getWord("SORTDIRCTION"));
        L_sortDirection.setId("fileSort");
        topContainer.add(L_sortDirection, 2, 2);
        
        ComboBox CB_sortDirction = new ComboBox();
        CB_sortDirction.getItems().addAll(
            "Ascending",
            "Descending"
        );
        CB_sortDirction.setPromptText(objDictionaryAction.getWord("SORTDIRCTION"));
        CB_sortDirction.setEditable(false); 
        CB_sortDirction.setValue("Ascending"); 
        topContainer.add(CB_sortDirction, 3, 2); 
        
        CB_sortBy.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objSimulator.setStrOrderBy(newValue);
                populateContainer(); 
            }    
        });
        CB_sortDirction.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objSimulator.setStrDirection(newValue);
                populateContainer(); 
            }    
        });
        CB_fabricType.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objSimulator.setStrFabricType(newValue);
                populateContainer(); 
            }    
        });   
        TF_name.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                objSimulator.setStrBaseFSName(TF_name.getText());
                populateContainer(); 
            }
        });
        TF_EPI.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if(!TF_EPI.getText().trim().equalsIgnoreCase("") && TF_EPI.getText()!="" && TF_EPI.getText()!=null)
                    objSimulator.setIntEPI(Integer.parseInt(TF_EPI.getText()));                    
                else
                    objSimulator.setIntEPI(0);
                populateContainer(); 
            }
        });
        TF_PPI.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if(!TF_PPI.getText().trim().equalsIgnoreCase("") && TF_PPI.getText()!="" && TF_PPI.getText()!=null)
                    objSimulator.setIntPPI(Integer.parseInt(TF_PPI.getText()));                    
                else
                    objSimulator.setIntPPI(0);
                populateContainer(); 
            }
        });
        
        GP_container = new GridPane();         
        GP_container.setAlignment(Pos.CENTER);
        GP_container.setHgap(1);
        GP_container.setVgap(1);
        GP_container.setPadding(new Insets(1, 1, 1, 1));

        objSimulator.setStrBaseFSName("");
        objSimulator.setIntEPI(0);
        objSimulator.setIntPPI(0);
        objSimulator.setStrFabricType("All");
        objSimulator.setStrCondition("");
        objSimulator.setStrOrderBy("Name");
        objSimulator.setStrSearchBy("All");
        objSimulator.setStrDirection("Ascending");
        
        populateContainer(); 
        
        ScrollPane container = new ScrollPane();
        container.setId("popup");
        container.setContent(GP_container);
        
        root.setTop(topContainer);  
        root.setCenter(container);
        root.setBottom(new Label(objDictionaryAction.getWord("HOVERITEM")));
        simulationStage.setScene(scene);
        simulationStage.getIcons().add(new Image("/media/icon.png"));
        simulationStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWFABRICIMPORT")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        simulationStage.setIconified(false);
        simulationStage.setResizable(false);
        simulationStage.showAndWait();
    }

    /*
    @author Amit Singh
    Function used to paint grid of image with their name    
    */  
    public void populateContainer(){        
        GP_container.getChildren().clear();
        Simulator lstBaseFSDeatails[]=null;
        Simulator lstBaseFS;
        BufferedImage bufferedImage = null;
        try {
            UtilityAction objUtilityAction = new UtilityAction();
            //lstBaseFSDeatails = objUtilityAction.lstBaseFabricSimultion(objSimulator);
            lstBaseFSDeatails = objUtilityAction.lstBaseFabricSimultion(objSimulator);
            if(lstBaseFSDeatails.length==0){
                GP_container.add(new Text(objDictionaryAction.getWord("NOVALUE")), 0, 0);
            }else{
                for (int i=0, j = lstBaseFSDeatails.length; i<j;i++){
                    lstBaseFS = (Simulator)lstBaseFSDeatails[i];
                    SeekableStream stream = new ByteArraySeekableStream((byte[])lstBaseFS.getBytBaseFSIcon());
                    String[] names = ImageCodec.getDecoderNames(stream);
                    ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                    RenderedImage im = dec.decodeAsRenderedImage();
                    bufferedImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
                    Image image=SwingFXUtils.toFXImage(bufferedImage, null);
                    final ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(111);
                    imageView.setFitWidth(111);
                    imageView.setUserData(lstBaseFS.getStrBaseFSID());
                    String strTooltip =
                            objDictionaryAction.getWord("NAME")+": "+lstBaseFS.getStrBaseFSName()+"\n"+
                            objDictionaryAction.getWord("FABRICTYPE")+": "+lstBaseFS.getStrBaseFSType()+"\n"+
                            objDictionaryAction.getWord("EPI")+": "+lstBaseFS.getIntEPI()+"\n"+
                            objDictionaryAction.getWord("PPI")+": "+lstBaseFS.getIntPPI()+"\n"+
                            objDictionaryAction.getWord("DPI")+": "+lstBaseFS.getIntDPI()+"\n"+
                            objDictionaryAction.getWord("YARNID")+": "+lstBaseFS.getStrYarnID()+"\n"+
                            objDictionaryAction.getWord("PERMISSION")+": "+lstBaseFS.getStrBaseFSAccess()+"\n"+
                            objDictionaryAction.getWord("BY")+": "+lstBaseFS.getStrUserId()+"\n"+
                            objDictionaryAction.getWord("DATE")+": "+lstBaseFS.getStrBaseFSDate();
                    Tooltip toolTip = new Tooltip(strTooltip);
                    Tooltip.install(imageView, toolTip);
                    imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            new MessageView(objSimulator.getObjConfiguration());
                            if(objSimulator.getObjConfiguration().getServicePasswordValid()){
                                objSimulator.getObjConfiguration().setServicePasswordValid(false);
                                objSimulator.setStrBaseFSID(imageView.getUserData().toString());
                                System.gc();
                                simulationStage.close();
                            }
                        }
                    });
                    GP_container.add(imageView, i%4, i/4);
                }
            }
        } catch (SQLException ex) {
            //Logger.getLogger(SimulatorImportView.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",SimulatorImportView.class.getName(),"SimulatorImportView()",ex);
        } catch (Exception ex){
            new Logging("SEVERE",SimulatorImportView.class.getName(),"SimulatorImportView()",ex);
        }
    }
   
    public void start(Stage stage) throws Exception {
        stage.initOwner(UtilityView.utilityStage);
        new SimulatorImportView(stage);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    public static void main(String[] args) {   
      launch(args);    
    }
}

