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

import com.mla.dictionary.DictionaryAction;
import com.mla.main.IDGenerator;
import com.mla.main.Logging;
import com.mla.main.MessageView;
import com.sun.media.jai.codec.ByteArraySeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import javax.media.jai.PlanarImage;
/**
 *
 * @Designing GUI window for fabric import
 * @author Amit Kumar Singh
 * 
 */
public class FabricImportView {
    
   Fabric objFabric=null;
   DictionaryAction objDictionaryAction;
   
   private BorderPane root;
   private Scene scene;
   private Stage fabricStage;
   
   private Label lblName;
   private TextField txtName;
   private Label lblSorting;
   private ComboBox fileSort;
   private GridPane GP_container;
	 
   public FabricImportView(final Stage primaryStage) {  }
    /*
    @author Amit Singh
    function for creating weave import window     
    */
   public FabricImportView(Fabric objFabricCall) {
        objFabric = objFabricCall;
        objDictionaryAction = new DictionaryAction(objFabric.getObjConfiguration());
        
        fabricStage = new Stage();
        fabricStage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL
        fabricStage.initStyle(StageStyle.UTILITY);
        root = new BorderPane();
        scene = new Scene(root, 480, 350, Color.WHITE);
        scene.getStylesheets().add(FabricImportView.class.getResource(objFabric.getObjConfiguration().getStrTemplate()+"/style.css").toExternalForm());
        GridPane topContainer = new GridPane();
        topContainer.setId("subpopup");
        topContainer.setVgap(2);
        topContainer.setHgap(2);
        
        lblName = new Label(objDictionaryAction.getWord("NAME"));
        topContainer.add(lblName, 0, 0);
        
        txtName = new TextField();
        txtName.setPromptText(objDictionaryAction.getWord("PROMPTNAME"));
        txtName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                objFabric.setStrCondition(txtName.getText());
                populateContainer(); 
            }
        });
        objFabric.setStrCondition("");
        topContainer.add(txtName, 1, 0);
        
        lblSorting = new Label(objDictionaryAction.getWord("SORTBY"));
        topContainer.add(lblSorting, 0, 1);
        
        fileSort = new ComboBox();
        fileSort.getItems().addAll(
            "Name",
            "Date"
        );
        fileSort.setPromptText(objDictionaryAction.getWord("SORTBY"));
        fileSort.setEditable(false); 
        fileSort.setValue("Name"); 
        fileSort.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                    objFabric.setStrOrderBy(newValue);
                    populateContainer(); 
            }    
        });
        objFabric.setStrOrderBy("Name");
        topContainer.add(fileSort, 1, 1); 
        objFabric.setStrDirection("Ascending");
        
        GP_container = new GridPane();         
        GP_container.setAlignment(Pos.CENTER);
        GP_container.setHgap(5);
        GP_container.setVgap(5);
        GP_container.setPadding(new Insets(1, 1, 1, 1));
       
        populateContainer(); 
        
        ScrollPane container = new ScrollPane();
        container.setId("popup");
        container.setContent(GP_container);
        
        root.setTop(topContainer);  
        root.setCenter(container);
        root.setBottom(new Label(objDictionaryAction.getWord("HOVERITEM")));
        fabricStage.setScene(scene);
        fabricStage.getIcons().add(new Image("/media/icon.png"));
        fabricStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWFABRICIMPORT")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        fabricStage.setIconified(false);
        fabricStage.setResizable(false);
        fabricStage.showAndWait();
    }

    /*
    @author Amit Singh
    Function used to paint grid of iamge with their name    
    */  
    public void populateContainer(){        
        GP_container.getChildren().clear();
        List lstFabricDeatails=null, lstFabric;
        BufferedImage bufferedImage = null;
        try {
            FabricAction objFabricAction = new FabricAction();
            lstFabricDeatails = objFabricAction.lstImportFabric(objFabric);
            if(lstFabricDeatails.size()==0){
                GP_container.add(new Text(objDictionaryAction.getWord("NOVALUE")), 0, 0);
            }else{
                for (int i=0, j = lstFabricDeatails.size(); i<j;i++){
                    lstFabric = (ArrayList)lstFabricDeatails.get(i);
                    SeekableStream stream = new ByteArraySeekableStream((byte[])lstFabric.get(26));
                    String[] names = ImageCodec.getDecoderNames(stream);
                    ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                    RenderedImage im = dec.decodeAsRenderedImage();
                    bufferedImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
                    Image image=SwingFXUtils.toFXImage(bufferedImage, null);
                    
                    final ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(111);
                    imageView.setFitWidth(111);
                    imageView.setId(lstFabric.get(29).toString());
                    imageView.setUserData(lstFabric.get(0).toString());
                    String strTooltip =
                            objDictionaryAction.getWord("NAME")+": "+lstFabric.get(1).toString()+"\n"+
                            objDictionaryAction.getWord("CLOTHTYPE")+": "+lstFabric.get(2)+"\n"+
                            objDictionaryAction.getWord("FABRICTYPE")+": "+lstFabric.get(3)+"\n"+
                            objDictionaryAction.getWord("FABRICLENGTH")+": "+lstFabric.get(4)+"\n"+
                            objDictionaryAction.getWord("FABRICWIDTH")+": "+lstFabric.get(5)+"\n"+
                            objDictionaryAction.getWord("ARTWORKLENGTH")+": "+lstFabric.get(6)+"\n"+
                            objDictionaryAction.getWord("ARTWORKWIDTH")+": "+lstFabric.get(7)+"\n"+
                            objDictionaryAction.getWord("WEFT")+": "+lstFabric.get(10)+"\n"+
                            objDictionaryAction.getWord("WARP")+": "+lstFabric.get(11)+"\n"+
                            objDictionaryAction.getWord("SHAFT")+": "+lstFabric.get(14)+"\n"+
                            objDictionaryAction.getWord("HOOKS")+": "+lstFabric.get(15)+"\n"+
                            objDictionaryAction.getWord("HPI")+": "+lstFabric.get(16)+"\n"+
                            objDictionaryAction.getWord("REEDCOUNT")+": "+lstFabric.get(17)+"\n"+
                            objDictionaryAction.getWord("DENTS")+": "+lstFabric.get(18)+"\n"+
                            objDictionaryAction.getWord("TPD")+": "+lstFabric.get(19)+"\n"+
                            objDictionaryAction.getWord("EPI")+": "+lstFabric.get(20)+"\n"+
                            objDictionaryAction.getWord("PPI")+": "+lstFabric.get(21)+"\n"+
                            objDictionaryAction.getWord("PROTECTION")+": "+lstFabric.get(22)+"\n"+
                            objDictionaryAction.getWord("BINDING")+": "+lstFabric.get(23)+"\n"+
                            objDictionaryAction.getWord("PERMISSION")+": "+lstFabric.get(29)+"\n"+
                            objDictionaryAction.getWord("BY")+": "+lstFabric.get(31).toString()+"\n"+
                            objDictionaryAction.getWord("DATE")+": "+lstFabric.get(30).toString();
                    Tooltip toolTip = new Tooltip(strTooltip);
                    Tooltip.install(imageView, toolTip);
                    imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if(imageView.getId().equalsIgnoreCase("Public")){
                                objFabric.getObjConfiguration().setServicePasswordValid(true);
                            }
                            else{
                                new MessageView(objFabric.getObjConfiguration());
                            }
                            if(objFabric.getObjConfiguration().getServicePasswordValid()){
                                objFabric.getObjConfiguration().setServicePasswordValid(false);
                                objFabric.setStrFabricID(imageView.getUserData().toString());
                                System.gc();
                                fabricStage.close();
                            }
                        }
                    });
                    GP_container.add(imageView, i%4, i/4);
                }
            }
        } catch (SQLException ex) {
           //Logger.getLogger(FabricImportView.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",FabricImportView.class.getName(),"FabricImportView()",ex);
        } catch (Exception ex){
            new Logging("SEVERE",FabricImportView.class.getName(),"FabricImportView()",ex);
        }
    }
   
    public void start(Stage stage) throws Exception {
        stage.initOwner(FabricView.fabricStage);
        new FabricImportView(stage);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    public static void main(String[] args) {   
      launch(args);    
    }
}
