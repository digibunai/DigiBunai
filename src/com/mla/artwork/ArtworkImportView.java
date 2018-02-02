/*
 * Copyright (C) 2017 Digital India Corporation (formerly Media Lab Asia)
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
package com.mla.artwork;

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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.media.jai.PlanarImage;
/**
 * ArtworkImportView Class
 * <p>
 * This class is used for defining GUI methods for artwork library import.
 *
 * @author Amit Kumar Singh
 * @version %I%, %G%
 * @since   1.0
 * @date 07/01/2016
 * @Designing model method class for artwork
 * @see javafx.stage.*;
 */
public class ArtworkImportView {
    
   Artwork objArtwork=null;
   DictionaryAction objDictionaryAction;
   
   private BorderPane root;
   private Scene scene;
   private Stage artworkStage;
   
   private Label L_filename;
   private TextField TF_filename;
   private Label L_fileSort;
   private ComboBox fileSort;
   private GridPane GP_container;
    
    /**
     * ArtworkImportView
     * <p>
     * This constructor is used for creating database connection.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   constructor is used for creating database connection.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     * @param       objArtworkCall Object of Artwork
     */
    public ArtworkImportView(Artwork objArtworkCall) {
        this.objArtwork = objArtworkCall;
        objDictionaryAction = new DictionaryAction(objArtwork.getObjConfiguration());
        
        artworkStage = new Stage();
        artworkStage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL
        artworkStage.initStyle(StageStyle.UTILITY);
        root = new BorderPane();
        scene = new Scene(root, 480, 350, Color.WHITE);
        scene.getStylesheets().add(FabricView.class.getResource(objArtwork.getObjConfiguration().getStrTemplate()+"/style.css").toExternalForm());
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
       
        objArtwork.setStrCondition("");
        objArtwork.setStrOrderBy("Name");
        objArtwork.setStrDirection("Ascending");
        populateGPCoanainer(); 
        
        fileSort.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objArtwork.setStrOrderBy(newValue);
                populateGPCoanainer(); 
            }    
        });
        
        TF_filename.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                objArtwork.setStrCondition(TF_filename.getText());
                populateGPCoanainer(); 
            }
        });
         
        ScrollPane container = new ScrollPane();
        container.setId("popup");
        container.setContent(GP_container);
        
        root.setTop(topContainer);  
        root.setCenter(container);
        root.setBottom(new Label(objDictionaryAction.getWord("HOVERITEM")));
        artworkStage.setScene(scene);
        artworkStage.getIcons().add(new Image("/media/icon.png"));
        artworkStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWARTWORKIMPORT")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        artworkStage.setIconified(false);
        artworkStage.setResizable(false);
        artworkStage.showAndWait();
    }
    /**
     * populateGPCoanainer
     * <p>
     * This method is used for creating GUI of artwork grid with image and details.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   constructor is used for creating GUI of artwork grid with image and details.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    public void populateGPCoanainer(){        
        GP_container.getChildren().clear();
       
        List lstArtworkDeatails=null, lstArtwork;
        BufferedImage bufferedImage = null;
        try {
            ArtworkAction objArtworkAction = new ArtworkAction();
	    lstArtworkDeatails = objArtworkAction.lstImportArtwork(objArtwork);
            if(lstArtworkDeatails.size()==0){
                GP_container.add(new Text(objDictionaryAction.getWord("NOVALUE")), 0, 0);
            }else{ 
                for (int i=0, j = lstArtworkDeatails.size(); i<j;i++){
                    lstArtwork = (ArrayList)lstArtworkDeatails.get(i);
                    try{
                        SeekableStream stream = new ByteArraySeekableStream((byte[])lstArtwork.get(2));
                        String[] names = ImageCodec.getDecoderNames(stream);
                        ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                        RenderedImage im = dec.decodeAsRenderedImage();
                        bufferedImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
                        Image image=SwingFXUtils.toFXImage(bufferedImage, null);
                        String strAccess=new IDGenerator().getUserAcessValueData("ARTWORK_LIBRARY",lstArtwork.get(6).toString());
                        
                        final ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(111);
                        imageView.setFitWidth(111);
                        imageView.setId(strAccess);
                        imageView.setUserData(lstArtwork.get(0).toString());
                        String strTooltip = 
                            objDictionaryAction.getWord("NAME")+": "+lstArtwork.get(1).toString()+"\n"+
                            objDictionaryAction.getWord("ARTWORKLENGTH")+": "+bufferedImage.getHeight()+"\n"+
                            objDictionaryAction.getWord("ARTWORKWIDTH")+": "+bufferedImage.getWidth()+"\n"+
                            objDictionaryAction.getWord("BACKGROUND")+": "+lstArtwork.get(3).toString()+"\n"+
                            objDictionaryAction.getWord("PERMISSION")+": "+strAccess+"\n"+
                            objDictionaryAction.getWord("BY")+": "+lstArtwork.get(5).toString()+"\n"+
                            objDictionaryAction.getWord("DATE")+": "+lstArtwork.get(4).toString();
                        Tooltip toolTip = new Tooltip(strTooltip);
                        Tooltip.install(imageView, toolTip);
                        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {                  
                                if(imageView.getId().equalsIgnoreCase("Public")){
                                    objArtwork.getObjConfiguration().setServicePasswordValid(true);
                                } else{
                                    new MessageView(objArtwork.getObjConfiguration());
                                }
                                if(objArtwork.getObjConfiguration().getServicePasswordValid()){
                                    objArtwork.getObjConfiguration().setServicePasswordValid(false);
                                    objArtwork.setStrArtworkId(imageView.getUserData().toString());
                                    System.gc();
                                    artworkStage.close();
                                }
                            }
                        });
                        GP_container.add(imageView, i%4, i/4);
                    } catch (Exception ex){
                        new Logging("SEVERE",ArtworkImportView.class.getName(),"ArtworkImportView()",ex);
                    }
                }
            }
	} catch (Exception ex) {
            new Logging("SEVERE",ArtworkImportView.class.getName(),"ArtworkImportView()",ex);
	}
    }   
}