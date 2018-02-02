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

package com.mla.cloth;

import com.mla.artwork.Artwork;
import com.mla.artwork.ArtworkAction;
import com.mla.artwork.ArtworkImportView;
import com.mla.dictionary.DictionaryAction;
import com.mla.fabric.FabricView;
import com.mla.main.IDGenerator;
import com.mla.main.Logging;
import com.mla.main.MessageView;
import com.sun.media.jai.codec.ByteArraySeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Aatif Ahmad Khan
 */
public class ClothImportView {
    Cloth objCloth=null;
    DictionaryAction objDictionaryAction;
   
    private BorderPane root;
    private Scene scene;
    private Stage clothStage;
   
    private Label L_filename;
    private TextField TF_filename;
    private Label L_fileSort;
    private ComboBox fileSort;
    private GridPane GP_container;
    
    public ClothImportView(Cloth objClothCall) {
        this.objCloth = objClothCall;
        objDictionaryAction = new DictionaryAction(objCloth.getObjConfiguration());
        
        clothStage = new Stage();
        clothStage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL
        clothStage.initStyle(StageStyle.UTILITY);
        root = new BorderPane();
        scene = new Scene(root, 480, 350, Color.WHITE);
        scene.getStylesheets().add(ClothView.class.getResource(objCloth.getObjConfiguration().getStrTemplate()+"/style.css").toExternalForm());
        GridPane topContainer = new GridPane();
        topContainer.setId("subpopup");
        topContainer.setVgap(2);
        topContainer.setHgap(2);
        
        L_filename = new Label(objDictionaryAction.getWord("NAME"));
        L_filename.setId("filename");
        topContainer.add(L_filename,0,0);
        
        TF_filename = new TextField();
        TF_filename.setPromptText(objDictionaryAction.getWord("PROMPTNAME"));
        topContainer.add(TF_filename,1,0);
        
        L_fileSort = new Label(objDictionaryAction.getWord("SORTBY"));
        L_fileSort.setId("fileSort");
        topContainer.add(L_fileSort,0,1);
        
        fileSort = new ComboBox();
        fileSort.getItems().addAll(
            "Name",
            "Date"
        );
        fileSort.setPromptText(objDictionaryAction.getWord("SORTBY"));
        fileSort.setEditable(false); 
        fileSort.setValue("Name"); 
        topContainer.add(fileSort,1,1); 
        
        GP_container = new GridPane();         
        GP_container.setAlignment(Pos.CENTER);
        GP_container.setHgap(5);
        GP_container.setVgap(5);
        GP_container.setPadding(new Insets(1, 1, 1, 1));
       
        objCloth.setStrCondition("");
        objCloth.setStrOrderBy("Name");
        objCloth.setStrDirection("Ascending");
        populateGPCoanainer(); 
        
        fileSort.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objCloth.setStrOrderBy(newValue);
                populateGPCoanainer(); 
            }    
        });
        
        TF_filename.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                objCloth.setStrCondition(TF_filename.getText());
                populateGPCoanainer(); 
            }
        });
         
        ScrollPane container = new ScrollPane();
        container.setId("popup");
        container.setContent(GP_container);
        
        root.setTop(topContainer);  
        root.setCenter(container);
        root.setBottom(new Label(objDictionaryAction.getWord("HOVERITEM")));
        clothStage.setScene(scene);
        clothStage.getIcons().add(new Image("/media/icon.png"));
        clothStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWCLOTHIMPORT")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        clothStage.setIconified(false);
        clothStage.setResizable(false);
        clothStage.showAndWait();
    }
    
    public void populateGPCoanainer(){        
        GP_container.getChildren().clear();
       
        List lstClothDeatails=null, lstCloth;
        BufferedImage bufferedImage = null;
        try {
            ClothAction objClothAction = new ClothAction();
	    lstClothDeatails = objClothAction.lstImportArtwork(objCloth);
            if(lstClothDeatails.size()==0){
                GP_container.add(new Text(objDictionaryAction.getWord("NOVALUE")), 0, 0);
            }else{ 
                for (int i=0, j = lstClothDeatails.size(); i<j;i++){
                    lstCloth = (ArrayList)lstClothDeatails.get(i);
                    try{
                        SeekableStream stream = new ByteArraySeekableStream((byte[])lstCloth.get(3));
                        String[] names = ImageCodec.getDecoderNames(stream);
                        ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                        RenderedImage im = dec.decodeAsRenderedImage();
                        bufferedImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
                        Image image=SwingFXUtils.toFXImage(bufferedImage, null);
                        String strAccess=new IDGenerator().getUserAcessValueData("CLOTH_LIBRARY",lstCloth.get(5).toString());
                        
                        final ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(111);
                        imageView.setFitWidth(111);
                        imageView.setId(strAccess);
                        imageView.setUserData(lstCloth.get(0).toString());
                        String strTooltip = 
                            objDictionaryAction.getWord("NAME")+": "+lstCloth.get(1).toString()+"\n"+
                            objDictionaryAction.getWord("DESCRIPTION")+": "+lstCloth.get(2)+"\n"+
                            objDictionaryAction.getWord("REGION")+": "+lstCloth.get(4)+"\n"+
                            objDictionaryAction.getWord("PERMISSION")+": "+strAccess+"\n"+
                            objDictionaryAction.getWord("BY")+": "+lstCloth.get(6).toString()+"\n"+
                            objDictionaryAction.getWord("DATE")+": "+lstCloth.get(7).toString();
                        Tooltip toolTip = new Tooltip(strTooltip);
                        Tooltip.install(imageView, toolTip);
                        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {                  
                                if(imageView.getId().equalsIgnoreCase("Public")){
                                    objCloth.getObjConfiguration().setServicePasswordValid(true);
                                } else{
                                    new MessageView(objCloth.getObjConfiguration());
                                }
                                if(objCloth.getObjConfiguration().getServicePasswordValid()){
                                    objCloth.getObjConfiguration().setServicePasswordValid(false);
                                    objCloth.setStrClothId(imageView.getUserData().toString());
                                    System.gc();
                                    clothStage.close();
                                }
                            }
                        });
                        GP_container.add(imageView, i%4, i/4);
                    } catch (Exception ex){
                        Logger.getLogger(ClothImportView.class.getName()).log(Level.SEVERE, null, ex);
                        new Logging("SEVERE",ClothImportView.class.getName(),"ClothImportView()",ex);
                    }
                }
            }
	} catch (Exception ex) {
            Logger.getLogger(ClothImportView.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",ClothImportView.class.getName(),"ClothImportView()",ex);
	}
    }
}
