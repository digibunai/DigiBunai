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
package com.mla.main;

import com.mla.artwork.Artwork;
import com.mla.artwork.ArtworkAction;
import com.mla.artwork.ArtworkView;
import com.mla.dictionary.DictionaryAction;
import com.mla.fabric.Fabric;
import com.mla.fabric.FabricAction;
import com.mla.fabric.FabricSettingView;
import com.mla.fabric.FabricView;
import com.mla.user.UserAction;
import com.mla.user.UserPrefrenceView;
import com.mla.user.UserProfileView;
import com.mla.user.UserSettingView;
import com.mla.user.UserView;
import com.mla.cloth.ClothView;
import com.mla.utility.SimulatorView;
import com.mla.utility.UtilityView;
import com.mla.weave.Weave;
import com.mla.weave.WeaveAction;
import com.mla.weave.WeaveView;
import com.sun.media.jai.codec.ByteArraySeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.Mnemonic;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javax.media.jai.PlanarImage;
/**
 *
 * @Designing GUI window for dashboard
 * @author Amit Kumar Singh
 * 
 */
public class WindowView extends Application {
    public static Stage windowStage;
   
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    List lstData=null;
    
 /**
 * WindowView(Stage)
 * <p>
 * This constructor is used for individual call of class. 
 * 
 * @param       Stage primaryStage
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         javafx.stage.*;
 * @link        FabricView
 */
     public WindowView(final Stage primaryStage) {}
    
 /**
 * WindowView(Configuration)
 * <p>
 * This class is used for prompting about software information. 
 * 
 * @param       Configuration objConfigurationCall
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         javafx.scene.control.*;
 * @link        Configuration
 */
    public WindowView(Configuration objConfigurationCall) {
        
    objConfiguration = objConfigurationCall;
    objDictionaryAction = new DictionaryAction(objConfiguration);
    
    windowStage = new Stage(); 
    BorderPane root = new BorderPane();
    Scene scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
    scene.getStylesheets().add(WindowView.class.getResource(objConfiguration.getStrTemplate()+"/dashboard.css").toExternalForm());
    
    HBox container = new HBox();
    container.setId("container");
    
    //Recent Pane
    Accordion recentPane = new Accordion (); 
    recentPane.setPrefSize(objConfiguration.WIDTH*0.25, objConfiguration.HEIGHT);
    recentPane.setId("recentPane");
    
    final TitledPane fabricTP = new TitledPane();
    fabricTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/fabric_library.png"));
    GridPane fabricList = new GridPane();
    fabricList.setVgap(objConfiguration.WIDTH*0.02);
    fabricList.setHgap(objConfiguration.WIDTH*0.01);
    fabricList.setAlignment(Pos.TOP_CENTER);
    fabricList.getChildren().clear();
    try {
        Fabric objFabric = new Fabric();
        objFabric.setObjConfiguration(objConfiguration);
        objFabric.setStrSearchBy("");
        objFabric.setStrCondition("");
        objFabric.setStrOrderBy("Date");
        objFabric.setStrDirection("Ascending");
        FabricAction objFabricAction = new FabricAction();
        List lstFabricDeatails = new ArrayList();
        lstFabricDeatails = objFabricAction.lstImportFabric(objFabric);
        if(lstFabricDeatails.size()==0){
            fabricList.getChildren().add(new Text(objDictionaryAction.getWord("FABRIC")+" - "+objDictionaryAction.getWord("NOVALUE")));
        }else{         
            SeekableStream stream = null;           
            Rectangle preview;
            for (int i=0, j = lstFabricDeatails.size(); i<j && i<15; i++){
                lstData = (ArrayList)lstFabricDeatails.get(i);
                
                final Label summary = new Label();
                preview = new Rectangle(objConfiguration.WIDTH*0.06,objConfiguration.WIDTH*0.06);
                preview.setArcHeight(10);
                preview.setArcWidth(10);
                preview.setTranslateX(1);
                preview.setTranslateY(1);
                preview.setEffect(new DropShadow(3, Color.BLACK));
                try {
                    byte[] bytWeaveThumbnil = (byte[])lstData.get(26);
                    stream = new ByteArraySeekableStream(bytWeaveThumbnil);
                    String[] names = ImageCodec.getDecoderNames(stream);
                    ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                    RenderedImage im = dec.decodeAsRenderedImage();
                    BufferedImage bufferedImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
                    Image image=SwingFXUtils.toFXImage(bufferedImage, null);
                    String strTooltip = 
                            objDictionaryAction.getWord("NAME")+": "+lstData.get(1).toString()+"\n"+
                            objDictionaryAction.getWord("CLOTHTYPE")+": "+lstData.get(2)+"\n"+
                            objDictionaryAction.getWord("FABRICTYPE")+": "+lstData.get(3)+"\n"+
                            objDictionaryAction.getWord("FABRICLENGTH")+": "+lstData.get(4)+"\n"+
                            objDictionaryAction.getWord("FABRICWIDTH")+": "+lstData.get(5)+"\n"+
                            objDictionaryAction.getWord("ARTWORKLENGTH")+": "+lstData.get(6)+"\n"+
                            objDictionaryAction.getWord("ARTWORKWIDTH")+": "+lstData.get(7)+"\n"+
                            objDictionaryAction.getWord("WEFT")+": "+lstData.get(10)+"\n"+
                            objDictionaryAction.getWord("WARP")+": "+lstData.get(11)+"\n"+
                            objDictionaryAction.getWord("SHAFT")+": "+lstData.get(14)+"\n"+
                            objDictionaryAction.getWord("HOOKS")+": "+lstData.get(15)+"\n"+
                            objDictionaryAction.getWord("HPI")+": "+lstData.get(16)+"\n"+
                            objDictionaryAction.getWord("REEDCOUNT")+": "+lstData.get(17)+"\n"+
                            objDictionaryAction.getWord("DENTS")+": "+lstData.get(18)+"\n"+
                            objDictionaryAction.getWord("TPD")+": "+lstData.get(19)+"\n"+                            
                            objDictionaryAction.getWord("EPI")+": "+lstData.get(20)+"\n"+
                            objDictionaryAction.getWord("PPI")+": "+lstData.get(21)+"\n"+
                            objDictionaryAction.getWord("PROTECTION")+": "+lstData.get(22)+"\n"+
                            objDictionaryAction.getWord("BINDING")+": "+lstData.get(23)+"\n"+                            
                            objDictionaryAction.getWord("PERMISSION")+": "+lstData.get(29)+"\n"+
                            objDictionaryAction.getWord("BY")+": "+lstData.get(31).toString()+"\n"+
                            objDictionaryAction.getWord("DATE")+": "+lstData.get(30).toString();
                    
                    preview.setFill(new ImagePattern(image));
                    summary.setGraphic(preview);
                    summary.setCursor(Cursor.HAND);
                    summary.setTooltip(new Tooltip(strTooltip));
                    summary.setEllipsisString(lstData.get(2).toString());
                    summary.setId(lstData.get(0).toString());
                    summary.setUserData(lstData.get(29).toString());
                    summary.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {              
                            if(summary.getUserData().toString().equalsIgnoreCase("Public")){
                                objConfiguration.setServicePasswordValid(true);
                            } else{
                                new MessageView(objConfiguration);
                            }
                            if(objConfiguration.getServicePasswordValid()){
                                objConfiguration.setServicePasswordValid(false);
                                try {
                                    objConfiguration.setStrClothType(summary.getEllipsisString());
                                    objConfiguration.strWindowFlowContext = "FabricEditor";
                                    UserAction objUserAction = new UserAction();
                                    objUserAction.getConfiguration(objConfiguration);
                                    objConfiguration.clothRepeat();
                                    System.gc();
                                    objConfiguration.mapRecentFabric.put(objConfiguration.getStrClothType(),summary.getId());
                                    windowStage.close();
                                    System.gc();
                                    FabricView objFabricView = new FabricView(objConfiguration);
                                } catch (SQLException ex) {
                                    new Logging("SEVERE",WindowView.class.getName(),"Load Fabric in Editor"+ex.toString(),ex);
                                }
                            }
                        }
                    });                    
                } catch (IOException ex) {
                    new Logging("SEVERE",WindowView.class.getName(),ex.toString(),ex);
                } finally {
                    try {
                        stream.close();
                    } catch (Exception ex) {
                        new Logging("SEVERE",WindowView.class.getName(),ex.toString(),ex);
                    }
                }                
                fabricList.add(summary,i%3,i/3);
            }
        }
        lstFabricDeatails = null;
    } catch (Exception ex) {
        new Logging("SEVERE",WindowView.class.getName(),ex.toString(),ex);
    }
    fabricTP.setText(objDictionaryAction.getWord("RECENT")+" "+objDictionaryAction.getWord("FABRIC"));
    fabricTP.setContent(fabricList);
    
    final TitledPane weaveTP = new TitledPane();
    weaveTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/weave_library.png"));
    GridPane weaveList = new GridPane();
    weaveList.setVgap(objConfiguration.WIDTH*0.02);
    weaveList.setHgap(objConfiguration.WIDTH*0.01);
    weaveList.setAlignment(Pos.TOP_CENTER);
    weaveList.getChildren().clear();
    try {
        Weave objWeave = new Weave();
        objWeave.setObjConfiguration(objConfiguration);
        objWeave.setStrCondition("");
        objWeave.setStrOrderBy("Date");
        objWeave.setStrSearchBy("All");
        objWeave.setStrDirection("Descending");
        WeaveAction objWeaveAction = new WeaveAction();
        List lstWeaveDeatails = new ArrayList();
        lstWeaveDeatails = objWeaveAction.lstImportWeave(objWeave);
        if(lstWeaveDeatails.size()==0){
            weaveList.getChildren().add(new Text(objDictionaryAction.getWord("WEAVE")+" - "+objDictionaryAction.getWord("NOVALUE")));
        }else{            
            SeekableStream stream = null;                        
            Rectangle preview;
            for (int i=0, j=lstWeaveDeatails.size(); i<j && i<15; i++){
                lstData = (ArrayList)lstWeaveDeatails.get(i);
                final Label summary = new Label();
                preview = new Rectangle(objConfiguration.WIDTH*0.06,objConfiguration.WIDTH*0.06);
                preview.setArcHeight(10);
                preview.setArcWidth(10);
                preview.setTranslateX(1);
                preview.setTranslateY(1);
                preview.setEffect(new InnerShadow(3, Color.BLUE));
                try {
                    byte[] bytWeaveThumbnil = (byte[])lstData.get(2);
                    stream = new ByteArraySeekableStream(bytWeaveThumbnil);
                    String[] names = ImageCodec.getDecoderNames(stream);
                    ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                    RenderedImage im = dec.decodeAsRenderedImage();
                    BufferedImage bufferedImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
                    Image image=SwingFXUtils.toFXImage(bufferedImage, null);
                    String strAccess = new IDGenerator().getUserAcessValueData("WEAVE_LIBRARY",lstData.get(15).toString());
                    String strTooltip = 
                            objDictionaryAction.getWord("NAME")+": "+lstData.get(1).toString()+"\n"+
                            objDictionaryAction.getWord("SHAFT")+": "+lstData.get(7).toString()+"\n"+
                            objDictionaryAction.getWord("TRADELES")+": "+lstData.get(8).toString()+"\n"+
                            objDictionaryAction.getWord("WEFTREPEAT")+": "+lstData.get(9).toString()+"\n"+
                            objDictionaryAction.getWord("WARPREPEAT")+": "+lstData.get(10).toString()+"\n"+
                            objDictionaryAction.getWord("WEFTFLOAT")+": "+lstData.get(11).toString()+"\n"+
                            objDictionaryAction.getWord("WARPFLOAT")+": "+lstData.get(12).toString()+"\n"+
                            objDictionaryAction.getWord("WEAVECATEGORY")+": "+lstData.get(4).toString()+"\n"+
                            objDictionaryAction.getWord("WEAVETYPE")+": "+lstData.get(3).toString()+"\n"+
                            objDictionaryAction.getWord("ISLIFTPLAN")+": "+lstData.get(5).toString()+"\n"+
                            objDictionaryAction.getWord("ISCOLOR")+": "+lstData.get(6).toString()+"\n"+
                            objDictionaryAction.getWord("PERMISSION")+": "+strAccess+"\n"+
                            objDictionaryAction.getWord("BY")+": "+lstData.get(14).toString()+"\n"+
                            objDictionaryAction.getWord("DATE")+": "+lstData.get(13).toString();    
                    preview.setFill(new ImagePattern(image));
                    summary.setGraphic(preview);
                    summary.setTooltip(new Tooltip(strTooltip));
                    summary.setCursor(Cursor.HAND);
                    summary.setId(lstData.get(0).toString());
                    summary.setUserData(strAccess);
                    summary.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {              
                            if(summary.getUserData().toString().equalsIgnoreCase("Public")){
                                objConfiguration.setServicePasswordValid(true);
                            } else{
                                new MessageView(objConfiguration);
                            }
                            if(objConfiguration.getServicePasswordValid()){
                                objConfiguration.setServicePasswordValid(false);
                                objConfiguration.strWindowFlowContext = "Dashboard";
                                objConfiguration.setStrRecentWeave(summary.getId());
                                windowStage.close();
                                System.gc();
                                WeaveView objWeaveView = new WeaveView(objConfiguration);
                            }
                        }
                    });
                    } catch (IOException ex) {
                        Logger.getLogger(WindowView.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            stream.close();
                        } catch (IOException ex) {
                            Logger.getLogger(WindowView.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                weaveList.add(summary,i%3,i/3);
            }
        }
        lstWeaveDeatails = null;
    } catch (Exception ex) {
        Logger.getLogger(WindowView.class.getName()).log(Level.SEVERE, null, ex);
    }
    weaveTP.setText(objDictionaryAction.getWord("RECENT")+" "+objDictionaryAction.getWord("WEAVE"));
    weaveTP.setContent(weaveList);
    
    final TitledPane artworkTP = new TitledPane();
    artworkTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/artwork_library.png"));
    GridPane artworkList = new GridPane();
    artworkList.setVgap(objConfiguration.WIDTH*0.02);
    artworkList.setHgap(objConfiguration.WIDTH*0.01);
    artworkList.setAlignment(Pos.TOP_CENTER);
    artworkList.getChildren().clear();
    try {
        Artwork objArtwork = new Artwork();
        objArtwork.setObjConfiguration(objConfiguration);
        objArtwork.setStrCondition("");
        objArtwork.setStrOrderBy("Date");
        objArtwork.setStrDirection("Descending");
        ArtworkAction objArtworkAction = new ArtworkAction();
        List lstArtworkDeatails = new ArrayList();
        lstArtworkDeatails = objArtworkAction.lstImportArtwork(objArtwork);
        if(lstArtworkDeatails.size()==0){
            artworkList.getChildren().add(new Text(objDictionaryAction.getWord("ARTWORK")+" - "+objDictionaryAction.getWord("NOVALUE")));
        }else{
            SeekableStream stream = null;
            Rectangle preview;
            for (int i=0, j = lstArtworkDeatails.size(); i<j && i<15;i++){
                lstData = (ArrayList)lstArtworkDeatails.get(i);
                final Label summary = new Label();
                preview = new Rectangle(objConfiguration.WIDTH*0.06,objConfiguration.WIDTH*0.06);
                preview.setArcHeight(10);
                preview.setArcWidth(10);
                preview.setTranslateX(1);
                preview.setTranslateY(1);
                preview.setEffect(new DropShadow(3, Color.BLACK));
                try {
                    byte[] bytArtworkThumbnil = (byte[])lstData.get(2);
                    stream = new ByteArraySeekableStream(bytArtworkThumbnil);
                    String[] names = ImageCodec.getDecoderNames(stream);
                    ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                    RenderedImage im = dec.decodeAsRenderedImage();
                    BufferedImage bufferedImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
                    Image image=SwingFXUtils.toFXImage(bufferedImage, null);
                    String strAccess = new IDGenerator().getUserAcessValueData("ARTWORK_LIBRARY",lstData.get(6).toString());
                    String strTooltip = 
                            objDictionaryAction.getWord("NAME")+": "+lstData.get(1).toString()+"\n"+
                            objDictionaryAction.getWord("ARTWORKLENGTH")+": "+bufferedImage.getHeight()+"\n"+
                            objDictionaryAction.getWord("ARTWORKWIDTH")+": "+bufferedImage.getWidth()+"\n"+
                            objDictionaryAction.getWord("BACKGROUND")+": "+lstData.get(3).toString()+"\n"+
                            objDictionaryAction.getWord("PERMISSION")+": "+strAccess+"\n"+
                            objDictionaryAction.getWord("BY")+": "+lstData.get(5).toString()+"\n"+
                            objDictionaryAction.getWord("DATE")+": "+lstData.get(4).toString();
                    
                    preview.setFill(new ImagePattern(image));
                    summary.setGraphic(preview);
                    summary.setCursor(Cursor.HAND);
                    summary.setTooltip(new Tooltip(strTooltip));
                    summary.setId(lstData.get(0).toString());
                    summary.setUserData(strAccess);
                    summary.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {              
                            if(summary.getUserData().toString().equalsIgnoreCase("Public")){
                                objConfiguration.setServicePasswordValid(true);
                            } else{
                                new MessageView(objConfiguration);
                            }
                            if(objConfiguration.getServicePasswordValid()){
                                objConfiguration.setServicePasswordValid(false);
                                objConfiguration.strWindowFlowContext = "Dashboard";
                                objConfiguration.setStrRecentArtwork(summary.getId());
                                windowStage.close();
                                System.gc();
                                ArtworkView objArtworkView = new ArtworkView(objConfiguration);
                            }
                        }
                    });
                } catch (IOException ex) {
                    new Logging("SEVERE",WindowView.class.getName(),ex.toString(),ex);
                } finally {
                    try {
                        stream.close();
                    } catch (IOException ex) {
                        new Logging("SEVERE",WindowView.class.getName(),ex.toString(),ex);
                    }
                }
                artworkList.add(summary,i%3,i/3);   
            }
        }
        lstArtworkDeatails = null;
    } catch (Exception ex) {
        new Logging("SEVERE",WindowView.class.getName(),ex.toString(),ex);
    }
    artworkTP.setText(objDictionaryAction.getWord("RECENT")+" "+objDictionaryAction.getWord("ARTWORK"));
    artworkTP.setContent(artworkList);
        
    recentPane.getPanes().addAll(fabricTP, weaveTP, artworkTP);
    recentPane.setExpandedPane(fabricTP);
    
    //Main Pane
    VBox mainPane = new VBox();
    mainPane.setId("mainPane");
    mainPane.setPrefSize(objConfiguration.WIDTH*0.75, objConfiguration.HEIGHT);
    
    HBox topPane = new HBox();
    //topPane.setAlignment(Pos.TOP_RIGHT);
    topPane.setPrefWidth(objConfiguration.WIDTH*0.75);
    topPane.setStyle("-fx-width:"+objConfiguration.WIDTH+";");
    
    HBox logoPane = new HBox();
    logoPane.setAlignment(Pos.TOP_LEFT);
    ImageView logoImage = new ImageView("/media/logo.png");
    logoImage.setFitHeight(objConfiguration.HEIGHT*0.1);
    logoPane.getChildren().add(logoImage);
    //logoPane.setLayoutX(objConfiguration.WIDTH*0.25);
    
    HBox userPane = new HBox();
    //userPane.setPrefWidth(d);
    userPane.setAlignment(Pos.TOP_RIGHT);
    Image userImage = new Image("/media/user.png");
    Circle clip = new Circle(20);
    clip.setTranslateX(1);
    clip.setTranslateY(1);
    clip.setCenterX(10);
    clip.setCenterY(10);
    clip.setFill(new ImagePattern(userImage));
    DropShadow borderGlow= new DropShadow();
    borderGlow.setOffsetY(0f);
    borderGlow.setOffsetX(0f);
    borderGlow.setColor(Color.RED);
    borderGlow.setWidth(50);
    borderGlow.setHeight(50);
    clip.setEffect(borderGlow);
    userPane.getChildren().add(clip);
    userPane.getChildren().add(new Label("   "+objDictionaryAction.getWord("WELCOME")+","));
    MenuButton editUserMB = new MenuButton(objConfiguration.getObjUser().getStrName().trim());
    final MenuItem editProfileMI = new MenuItem(objDictionaryAction.getWord("EDITPROFILE"));
    final MenuItem editPreferenceMI = new MenuItem(objDictionaryAction.getWord("EDITPREFERENCE"));
    final MenuItem editSettingMI = new MenuItem(objDictionaryAction.getWord("EDITSETTING"));
    final MenuItem helpMI = new MenuItem(objDictionaryAction.getWord("HELP"));
    final MenuItem logoutMI = new MenuItem(objDictionaryAction.getWord("LOGOUT"));
    editProfileMI.setGraphic(new ImageView(objConfiguration.getStrColour()+"/profile.png"));
    editPreferenceMI.setGraphic(new ImageView(objConfiguration.getStrColour()+"/orientation.png"));
    editSettingMI.setGraphic(new ImageView(objConfiguration.getStrColour()+"/settings.png"));
    helpMI.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
    logoutMI.setGraphic(new ImageView(objConfiguration.getStrColour()+"/logout.png"));
    editProfileMI.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {                
            windowStage.close();
            System.gc();
            UserProfileView objUserProfileView = new UserProfileView(objConfiguration);
        }
    }); 
    editPreferenceMI.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {                
            windowStage.close();
            System.gc();
            UserPrefrenceView objUserPrefrenceView = new UserPrefrenceView(objConfiguration);            
        }
    });
    editSettingMI.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {                
            windowStage.close();
            System.gc();
            UserSettingView objUserSettingView = new UserSettingView(objConfiguration);
        }
    });
    helpMI.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            HelpView objHelpView = new HelpView(objConfiguration);
        }
    });
    logoutMI.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                UserAction objUserAction= new UserAction();
                objUserAction.trackUser(objConfiguration.getObjUser(), "LOGOUT", "Log-Out done", null);                    
            } catch (SQLException ex) {
                new Logging("SEVERE",UserProfileView.class.getName(),"Logout Tracking",ex);
            }
            windowStage.close();
            System.gc();
            UserView objUserView=new UserView(objConfiguration);                
        }
    });
    editUserMB.getItems().addAll(editProfileMI, editPreferenceMI, editSettingMI, helpMI, logoutMI); 
    editUserMB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPUSER")));
    //editUserMB.setPrefWidth(objConfiguration.WIDTH/5);
    editUserMB.setAlignment(Pos.TOP_RIGHT);
    userPane.getChildren().add(editUserMB);
    
    topPane.getChildren().addAll(logoPane,userPane);
    
    GridPane containerGP = new GridPane();
    containerGP.setAlignment(Pos.CENTER);
    // fabric editor
    final VBox fabricVB = new VBox(); 
    Label fabricLbl= new Label(objDictionaryAction.getWord("FABRICEDITOR"));
    fabricLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFABRICEDITOR")));
    fabricVB.getChildren().addAll(new ImageView(objConfiguration.getStrTemplate()+"/img/d_fabric_editor.png"), fabricLbl);
    fabricVB.setPrefWidth(objConfiguration.WIDTH*0.20);
    fabricVB.getStyleClass().addAll("VBox");
    fabricVB.setCursor(Cursor.CROSSHAIR);
    containerGP.add(fabricVB, 0, 0);
    // artwork editor
    VBox artworkVB = new VBox(); 
    Label artworkLbl= new Label(objDictionaryAction.getWord("ARTWORKEDITOR"));
    artworkLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPARTWORKEDITOR")));
    artworkVB.getChildren().addAll(new ImageView(objConfiguration.getStrTemplate()+"/img/d_artwork_editor.png"), artworkLbl);
    artworkVB.setPrefWidth(objConfiguration.WIDTH*0.20);
    artworkVB.getStyleClass().addAll("VBox");
    artworkVB.setCursor(Cursor.CROSSHAIR);
    containerGP.add(artworkVB, 1, 0);
    // weave editor
    VBox weaveVB = new VBox(); 
    Label weaveLbl= new Label(objDictionaryAction.getWord("WEAVEEDITOR"));
    weaveLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWEAVEEDITOR")));
    weaveVB.getChildren().addAll(new ImageView(objConfiguration.getStrTemplate()+"/img/d_weave_editor.png"), weaveLbl);
    weaveVB.setPrefWidth(objConfiguration.WIDTH*0.20);
    weaveVB.getStyleClass().addAll("VBox");
    weaveVB.setCursor(Cursor.CROSSHAIR);
    containerGP.add(weaveVB, 2, 0);       
    // Save As file item
    VBox systemVB = new VBox(); 
    Label systemLbl= new Label(objDictionaryAction.getWord("FABRICSETTINGS"));
    systemLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFABRICSETTINGS")));
    systemVB.getChildren().addAll(new ImageView(objConfiguration.getStrTemplate()+"/img/d_fabric_preferences.png"), systemLbl);
    systemVB.setPrefWidth(objConfiguration.WIDTH*0.20);
    systemVB.getStyleClass().addAll("VBox");
    systemVB.setCursor(Cursor.CROSSHAIR);
    containerGP.add(systemVB, 0, 1);
    // Cloth editor item
    VBox clothVB = new VBox(); 
    Label clothLbl= new Label(objDictionaryAction.getWord("CLOTHEDITOR"));
    clothLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLOTHEDITOR")));
    clothVB.getChildren().addAll(new ImageView(objConfiguration.getStrTemplate()+"/img/d_cloth_editor.png"), clothLbl);
    clothVB.setPrefWidth(objConfiguration.WIDTH*0.20);
    clothVB.getStyleClass().addAll("VBox");
    clothVB.setCursor(Cursor.CROSSHAIR);
    containerGP.add(clothVB, 1, 1);
    // Save Texture file item
    VBox utilityVB = new VBox(); 
    Label utilityLbl= new Label(objDictionaryAction.getWord("UTILITYEDITOR"));
    utilityLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPUTILITYEDITOR")));
    utilityVB.getChildren().addAll(new ImageView(objConfiguration.getStrTemplate()+"/img/d_utilities.png"), utilityLbl);
    utilityVB.setPrefWidth(objConfiguration.WIDTH*0.20);
    utilityVB.getStyleClass().addAll("VBox");
    utilityVB.setCursor(Cursor.CROSSHAIR);
    containerGP.add(utilityVB, 2, 1);
    
    //Add the action to Buttons.
    fabricVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
			fabricVBAction();
        }
    });
    artworkVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
			artworkVBAction();
        }
    });
    weaveVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
			weaveVBAction();
        }
    });
    systemVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
			preferencesVBAction();
        }
    });
    clothVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
			garmentVBAction();
        }
    });
    utilityVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
			utilityVBAction();
            //SimulatorView objSimulatorView = new SimulatorView(objConfiguration);
        }
    });
    
    HBox copyrightPane = new HBox();
    
    copyrightPane.setAlignment(Pos.BOTTOM_RIGHT);
    copyrightPane.getChildren().add(new Label(objDictionaryAction.getWord("COPYRIGHT")));
    //containerGP.setAlignment(Pos.CENTER);
    mainPane.getChildren().addAll(userPane,containerGP, copyrightPane);
    container.getChildren().addAll(recentPane,mainPane);
    root.setCenter(container);
    windowStage.getIcons().add(new Image("/media/icon.png"));
    windowStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWDASHBOARD")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
    //configurationStage.setIconified(true);
    windowStage.setResizable(false);
    windowStage.setScene(scene);
    windowStage.setX(-5);
    windowStage.setY(0);
    windowStage.show();  
    windowStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                final Stage dialogStage = new Stage();
                dialogStage.initStyle(StageStyle.UTILITY);
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.setResizable(false);
                dialogStage.setIconified(false);
                dialogStage.setFullScreen(false);
                dialogStage.setTitle(objDictionaryAction.getWord("ALERT"));
                BorderPane root = new BorderPane();
                Scene scene = new Scene(root, 300, 100, Color.WHITE);
                scene.getStylesheets().add(WindowView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                final GridPane popup=new GridPane();
                popup.setId("popup");
                popup.setHgap(5);
                popup.setVgap(5);
                popup.setPadding(new Insets(25, 25, 25, 25));
                popup.add(new ImageView(objConfiguration.getStrColour()+"/stop.png"), 0, 0);  
                Label lblAlert = new Label(objDictionaryAction.getWord("ALERTCLOSE"));
                lblAlert.setStyle("-fx-wrap-text:true;");
                lblAlert.setPrefWidth(250);
                popup.add(lblAlert, 1, 0);
                Button btnYes = new Button(objDictionaryAction.getWord("YES"));
                btnYes.setPrefWidth(50);
                btnYes.setId("btnYes");
                btnYes.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        dialogStage.close();
                        System.gc();
                        windowStage.close();
                    }
                });
                popup.add(btnYes, 0, 1);
                Button btnNo = new Button(objDictionaryAction.getWord("NO"));
                btnNo.setPrefWidth(50);
                btnNo.setId("btnNo");
                btnNo.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        dialogStage.close();
                        System.gc();
                    }
                });
                popup.add(btnNo, 1, 1);
                root.setCenter(popup);
                dialogStage.setScene(scene);
                dialogStage.showAndWait();  
                we.consume();
            }
        });
		// Code Added for ShortCuts
        final KeyCodeCombination kcuCS = new KeyCodeCombination(KeyCode.U, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
        final KeyCodeCombination kcqCS = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
        final KeyCodeCombination kcqAC = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN);
        final KeyCodeCombination kchC = new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN);
        final KeyCodeCombination kcxCS = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
        final KeyCodeCombination kcfS = new KeyCodeCombination(KeyCode.F, KeyCombination.SHIFT_DOWN);
        final KeyCodeCombination kcaS = new KeyCodeCombination(KeyCode.A, KeyCombination.SHIFT_DOWN);
        final KeyCodeCombination kcwAC = new KeyCodeCombination(KeyCode.W, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN);
        final KeyCodeCombination kcgS = new KeyCodeCombination(KeyCode.G, KeyCombination.SHIFT_DOWN);
        final KeyCodeCombination kcpS = new KeyCodeCombination(KeyCode.P, KeyCombination.SHIFT_DOWN);
        final KeyCodeCombination kcuS = new KeyCodeCombination(KeyCode.U, KeyCombination.SHIFT_DOWN);
        final KeyCodeCombination kcfCS = new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
        final KeyCodeCombination kcaCS = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
        final KeyCodeCombination kcwCS = new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if(kcuCS.match(t))
                    editProfileMI.fire();
                else if(kcqCS.match(t))
                    editPreferenceMI.fire();
                else if(kcqAC.match(t))
                    editSettingMI.fire();
                else if(kchC.match(t))
                    helpMI.fire();
                else if(kcxCS.match(t))
                    logoutMI.fire();
                else if(kcfS.match(t)){
                    fabricVBAction();
                }
                else if(kcaS.match(t)){
                    artworkVBAction();
                }
                else if(kcwAC.match(t)){
                    weaveVBAction();
                }
                else if(kcgS.match(t)){
                    garmentVBAction();
                }
                else if(kcpS.match(t)){
                    preferencesVBAction();
                }
                else if(kcuS.match(t)){
                    utilityVBAction();
                }
                else if(kcfCS.match(t)){
                    fabricTP.setExpanded(!fabricTP.isExpanded());
                }
                else if(kcaCS.match(t)){
                    artworkTP.setExpanded(!artworkTP.isExpanded());
                }
                else if(kcwCS.match(t)){
                    weaveTP.setExpanded(!weaveTP.isExpanded());
                }
            }
        });
        
    }
    
    public void fabricVBAction(){
        objConfiguration.strWindowFlowContext = "Dashboard";
        System.gc();
        windowStage.close();
        FabricView objFabricView = new FabricView(objConfiguration);
    }
    
    public void artworkVBAction(){
        System.gc();
        windowStage.close();
        ArtworkView objArtworkView= new ArtworkView(objConfiguration);
    }
    
    public void weaveVBAction(){
        System.gc();
        windowStage.close();
        WeaveView objWeaveWindow= new WeaveView(objConfiguration);
    }
    
    public void preferencesVBAction(){
        System.gc();
        windowStage.close();
        FabricSettingView objFabricSettingView = new FabricSettingView(objConfiguration);
    }
    
    public void garmentVBAction(){
        objConfiguration.setStrRecentArtwork(null);
        System.gc();
        windowStage.close();
        ClothView objClothView = new ClothView(objConfiguration);
    }
    
    public void utilityVBAction(){
        System.gc();
        windowStage.close();
        UtilityView objUtilityView = new UtilityView(objConfiguration);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        new WindowView(stage);
        new Logging("WARNING",WindowView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
 /*
  public static void main(String[] args) {   
      launch(args);    
  }
  */
}