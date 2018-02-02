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
package com.mla.weave;

import com.mla.dictionary.DictionaryAction;
import com.mla.main.IDGenerator;
import com.mla.main.Logging;
import com.mla.main.MessageView;
import com.mla.main.WindowView;
import com.sun.media.jai.codec.ByteArraySeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;

/**
 *
 * @Designing GUI window for weave import
 * @author Amit Kumar Singh
 * 
 */
public class WeaveImportView {
    
   Weave objWeave;
   WeaveAction objWeaveAction;
   DictionaryAction objDictionaryAction;
   
   private BorderPane root;
   private Scene scene;
   private Stage weaveStage;
   
   private Label L_filename;
   private TextField TF_filename;
   private Label L_fileSort;
   private ComboBox fileSort;
   private Label L_fileSearch;
   private ComboBox fileSearch;
   private GridPane GP_container;
  
   public WeaveImportView(final Stage primaryStage) {  }
    
   public WeaveImportView(Weave objWeaveCall) {
        this.objWeave = objWeaveCall;
        objDictionaryAction = new DictionaryAction(objWeave.getObjConfiguration());
        
        weaveStage = new Stage();
        weaveStage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL
        //weaveStage.initOwner(parentStage);
        weaveStage.initStyle(StageStyle.UTILITY);
        root = new BorderPane();
        scene = new Scene(root, 480, 350, Color.WHITE);
        scene.getStylesheets().add(WeaveImportView.class.getResource(objWeave.getObjConfiguration().getStrTemplate()+"/style.css").toExternalForm());
        GridPane topContainer = new GridPane();
        topContainer.setId("subpopup");
        topContainer.setVgap(2);
        topContainer.setHgap(2);
        
        L_filename = new Label(objDictionaryAction.getWord("NAME"));
        L_filename.setId("filename");
        topContainer.add(L_filename, 0, 0);
        
        TF_filename = new TextField();
        TF_filename.setPromptText(objDictionaryAction.getWord("PROMPTNAME"));
        topContainer.add(TF_filename, 1, 0);
        
        L_fileSort = new Label(objDictionaryAction.getWord("SORTBY"));
        L_fileSort.setId("fileSort");
        topContainer.add(L_fileSort, 0, 1);
        
        fileSort = new ComboBox();
        fileSort.getItems().addAll(
            "Name",
            "Date",
            "Shaft",
            "Treadles",
            "Float X",
            "Float Y"
        );
        fileSort.setPromptText(objDictionaryAction.getWord("SORTBY"));
        fileSort.setEditable(false); 
        fileSort.setValue("Name"); 
        topContainer.add(fileSort, 1, 1); 
        
        L_fileSearch = new Label(objDictionaryAction.getWord("SEARCHBY"));
        L_fileSearch.setId("fileSearch");
        topContainer.add(L_fileSearch, 2, 0);
        
        fileSearch = new ComboBox();
        fileSearch.getItems().addAll(
            "Plain",
            "Twill",
            "Satin",
            "Basket",
            "Sateen",
            "All"
        );
        fileSearch.setPromptText(objDictionaryAction.getWord("SEARCHBY"));
        fileSearch.setEditable(false); 
        fileSearch.setValue("All");
        topContainer.add(fileSearch, 3, 0);
        
        GP_container = new GridPane();         
        GP_container.setAlignment(Pos.CENTER);
        GP_container.setHgap(5);
        GP_container.setVgap(5);
        GP_container.setPadding(new Insets(1, 1, 1, 1));
       
        objWeave.setStrCondition("");
        objWeave.setStrOrderBy("Name");
        objWeave.setStrSearchBy("All");
        objWeave.setStrDirection("Ascending");
        
        populateGPCoanainer(); 
        
        fileSort.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objWeave.setStrOrderBy(newValue);
                populateGPCoanainer(); 
            }    
        });
        fileSearch.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objWeave.setStrSearchBy(newValue);
                populateGPCoanainer(); 
            }    
        });   
        TF_filename.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                objWeave.setStrCondition(TF_filename.getText());
                populateGPCoanainer(); 
            }
        });
         
        ScrollPane container = new ScrollPane();
        container.setId("popup");
        container.setContent(GP_container);
        
        root.setTop(topContainer);  
        root.setCenter(container);
        root.setBottom(new Label(objDictionaryAction.getWord("HOVERITEM")));
        weaveStage.setScene(scene);
        weaveStage.getIcons().add(new Image("/media/icon.png"));
        weaveStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWWEAVEIMPORT")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        weaveStage.setIconified(false);
        weaveStage.setResizable(false);
        weaveStage.showAndWait();
    }

    /*
    @author Amit Singh
    Function used to paint grid of iamge with their name    
    */  
    public void populateGPCoanainer(){        
        GP_container.getChildren().clear();
        
        List lstWeaveDeatails=null, lstWeave;
        //String strWeaveName = null;
       // byte[] bytWeaveThumbnil;
        BufferedImage bufferedImage = null;
        try {
            objWeaveAction = new WeaveAction();
	    lstWeaveDeatails = objWeaveAction.lstImportWeave(objWeave);
            if(lstWeaveDeatails.size()==0){
                GP_container.add(new Text(objDictionaryAction.getWord("NOVALUE")), 0, 0);
            }else{
                for (int i=0, j = lstWeaveDeatails.size(); i<j;i++){
                    lstWeave = (ArrayList)lstWeaveDeatails.get(i);
                    try{   
                        SeekableStream stream = new ByteArraySeekableStream((byte[])lstWeave.get(2));
                        String[] names = ImageCodec.getDecoderNames(stream);
                        ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                        RenderedImage im = dec.decodeAsRenderedImage();
                        bufferedImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
                        Image image=SwingFXUtils.toFXImage(bufferedImage, null);
                        String strAccess=new IDGenerator().getUserAcessValueData("WEAVE_LIBRARY",lstWeave.get(15).toString());
                        final ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(111);
                        imageView.setFitWidth(111);
                        imageView.setId(strAccess);
                        imageView.setUserData(lstWeave.get(0));
                        String liftPlan = "Yes";
                        if(lstWeave.get(5).toString().equalsIgnoreCase("2"))
                            liftPlan = "No";
                        String color  = "Yes";
                        if(lstWeave.get(6).toString().equalsIgnoreCase("2"))
                            color = "No";
                        String strTooltip = 
                                    objDictionaryAction.getWord("NAME")+": "+lstWeave.get(1).toString()+"\n"+
                                    objDictionaryAction.getWord("SHAFT")+": "+lstWeave.get(7).toString()+"\n"+
                                    objDictionaryAction.getWord("TRADLE")+": "+lstWeave.get(8).toString()+"\n"+
                                    objDictionaryAction.getWord("WEFTREPEAT")+": "+lstWeave.get(9).toString()+"\n"+
                                    objDictionaryAction.getWord("WARPREPEAT")+": "+lstWeave.get(10).toString()+"\n"+
                                    objDictionaryAction.getWord("WEFTFLOAT")+": "+lstWeave.get(11).toString()+"\n"+
                                    objDictionaryAction.getWord("WARPFLOAT")+": "+lstWeave.get(12).toString()+"\n"+
                                    objDictionaryAction.getWord("WEAVECATEGORY")+": "+lstWeave.get(4).toString()+"\n"+
                                    objDictionaryAction.getWord("TYPE")+": "+lstWeave.get(3).toString()+"\n"+
                                    objDictionaryAction.getWord("ISLIFTPLAN")+": "+liftPlan+"\n"+
                                    objDictionaryAction.getWord("ISCOLOR")+": "+color+"\n"+
                                    objDictionaryAction.getWord("PERMISSION")+": "+strAccess+"\n"+
                                    objDictionaryAction.getWord("BY")+": "+lstWeave.get(14).toString()+"\n"+
                                    objDictionaryAction.getWord("DATE")+": "+lstWeave.get(13).toString();
                        Tooltip toolTip = new Tooltip(strTooltip);
                        Tooltip.install(imageView, toolTip);
                        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {                                  
                                if(imageView.getId().equalsIgnoreCase("Public")){
                                    objWeave.getObjConfiguration().setServicePasswordValid(true);
                                }
                                else{
                                    new MessageView(objWeave.getObjConfiguration());
                                }
                                if(objWeave.getObjConfiguration().getServicePasswordValid()){
                                    objWeave.getObjConfiguration().setServicePasswordValid(false);
                                    try {
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        ImageIO.write( SwingFXUtils.fromFXImage(imageView.getImage(), null), "png", baos );
                                        byte[] imageInByte = baos.toByteArray();                                
                                        objWeave.setBytWeaveThumbnil(imageInByte);                                
                                        objWeave.setStrWeaveID(imageView.getUserData().toString());
                                        if(event.isAltDown() || event.isControlDown()){
                                            Weave objWeaveNew = new Weave();
                                            objWeaveNew.setObjConfiguration(objWeave.getObjConfiguration());
                                            objWeaveNew.setStrWeaveID(objWeave.getStrWeaveID());  
                                            WeaveEditView objWeaveEditView = new WeaveEditView(objWeaveNew);
                                            if(objWeaveNew.getStrWeaveID()!=null){
                                                objWeave.setBytWeaveThumbnil(objWeaveNew.getBytWeaveThumbnil());
                                                objWeave.setStrWeaveID(objWeaveNew.getStrWeaveID());
                                                objWeaveNew = null;
                                            } else{
                                                System.err.println("weave edit: Your last action to assign weave pattern was not completed"+objWeave.getStrWeaveID());
                                            }
                                        }
                                        objWeaveAction = new WeaveAction();
                                        objWeaveAction.getWeave(objWeave);
                                        objWeaveAction.extractWeaveContent(objWeave);  
                                        System.out.println("1 point "+objWeave.getStrWeaveAccess());
                                        imageInByte = null;
                                        baos.close();
                                        System.gc();
                                    } catch (IOException ex) {
                                        Logger.getLogger(WeaveImportView.class.getName()).log(Level.SEVERE, null, ex);
                                        new Logging("SEVERE",WeaveImportView.class.getName(),"WeaveImportView()"+ex.getMessage(),ex);
                                    } catch (Exception ex) {
                                        Logger.getLogger(WeaveImportView.class.getName()).log(Level.SEVERE, null, ex);
                                        new Logging("SEVERE",WeaveImportView.class.getName(),"WeaveImportView()"+ex.getMessage(),ex);
                                    } 
                                    weaveStage.close();
                                    System.gc();
                                }
                            }

                        });
                        GP_container.add(imageView, i%4, i/4);
                    } catch (Exception ex){
                        new Logging("SEVERE",WeaveImportView.class.getName(),"WeaveImportView()",ex);
                    }
                }
            }
	} catch (Exception ex) {
            new Logging("SEVERE",WeaveImportView.class.getName(),"WeaveImportView()",ex);
	}
    }
   
    public void start(Stage stage) throws Exception {
        stage.initOwner(WindowView.windowStage);
        new WeaveImportView(stage);        
        new Logging("WARNING",WeaveImportView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    public static void main(String[] args) {   
      launch(args);    
    }
}
