/*
 * Copyright (C) 2017 Digital India Corpoartion (formerly Media Lab Asia)
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
import com.mla.fabric.Fabric;
import com.mla.yarn.Yarn;
import com.mla.main.IDGenerator;
import com.mla.main.Logging;
import com.mla.utility.Device;
import com.mla.utility.UtilityAction;
import com.mla.weave.WeaveImportView;
import com.mla.weave.Weave;
import com.mla.weave.WeaveAction;
import com.mla.weave.WeaveEditView;
import com.sun.media.jai.codec.ByteArraySeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;
/**
 * ArtworkEditView Class
 * <p>
 * This class is used for defining UI for artwork assignment in fabric editor.
 *
 * @author Amit Kumar Singh
 * @version %I%, %G%
 * @since   1.0
 * @date 07/01/2016
 * @Designing UI class for artwork assignment in fabric editor.
 * @see java.stage.*;
 * @link com.mla.fabric.FabricView
 */
public class ArtworkEditView {
 
    Artwork objArtwork;
    Fabric objFabric;
    Yarn objYarn;
    ArtworkAction objArtworkAction;
    DictionaryAction objDictionaryAction;
    
    private Stage artworkStage;
    private BorderPane root;    
    private Scene scene;    
    private Label lblStatus;
    private ProgressBar progressB;
    private ProgressIndicator progressI;
    private VBox container;
    private ScrollPane imageSP;
    private ScrollPane previewSP;
    private GridPane containerGP;
    private ScrollPane colorSP;
    private ScrollPane yarnSP;
    private ScrollPane weaveSP;
    private GridPane weaveGP;
    private HBox colorHB;
    private VBox yarnVB;
    private ContextMenu contextMenu;
    
    //Process objProcess_ProcessBuilder;
    
    Label lblDimension;
    Label lblBackground;
    Button btnBrowse;
    Button btnImport;
    Button btnRefresh;
    Button btnRemove;
    Button btnPreview;
    Button btnApply;
    Button btnCancel;
    CheckBox artworkOutlineCB;
    CheckBox artworkSizeCB;
    ComboBox weftModeCB;
    ComboBox warpModeCB;
    ComboBox fabricTypeCB;
    TextField bindingTF;
    TextField protectionTF;
    
    private BufferedImage bufferedImage;
    private List lstArtworkDeatails=null, lstArtwork=null;
    private ArrayList<java.awt.Color> colors;
    private List lstColorDeatils;
    private String[][] colorWeave=null;
    private byte[][][] fontPattern=null;
    private byte[][][] reversePattern=null;
    private Yarn[] warpExtraYarn;
    private Yarn[] weftExtraYarn;
    private String backgroundColor;
    private String filePath;
    int intColor=1;
    int intExtraWeft= 0;
    int xindex = 0;
    int yindex = 0;
    int rowCount = 0;
    
    /**
     * ArtworkEditView
     * <p>
     * This constructor is used for UX initialization.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   constructor is used for UX initialization.
     * @see         javafx.stage.*;
     * @link        com.mla.fabric.FabricView
     * @throws      SQLException
     * @param       objFabricCall Object of Fabric Class
     */        
    public ArtworkEditView(Fabric objFabricCall) {   
        this.objFabric = objFabricCall;
        objDictionaryAction = new DictionaryAction(objFabric.getObjConfiguration());

        artworkStage = new Stage();
        artworkStage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL
        artworkStage.initStyle(StageStyle.UTILITY);
        VBox parent =new VBox();
        root = new BorderPane();
        root.setId("popup");
        scene = new Scene(root, objFabric.getObjConfiguration().WIDTH, objFabric.getObjConfiguration().HEIGHT, Color.WHITE);
        scene.getStylesheets().add(ArtworkEditView.class.getResource(objFabric.getObjConfiguration().getStrTemplate()+"/style.css").toExternalForm());
        
        HBox footContainer = new HBox();
        progressB = new ProgressBar(0);
        progressB.setVisible(false);
        progressI = new ProgressIndicator(0);
        progressI.setVisible(false);
        lblStatus = new Label(objDictionaryAction.getWord("WELCOMETOCADTOOL"));
        lblStatus.setId("message");
        footContainer.getChildren().addAll(lblStatus,progressB,progressI);
        footContainer.setId("footContainer");
        root.setBottom(footContainer);
        
        container = new VBox();
        container.setPrefSize(root.getWidth(),root.getHeight());
        HBox imageHB = new HBox();
        imageHB.setId("subpopup");
        imageHB.setPrefSize(objFabric.getObjConfiguration().WIDTH,objFabric.getObjConfiguration().HEIGHT/3);
        
        imageSP = new ScrollPane();
        imageSP.setId("subpopup");
        imageSP.setPrefSize(objFabric.getObjConfiguration().WIDTH/2,objFabric.getObjConfiguration().HEIGHT/3);
        imageSP.setTooltip(new Tooltip(objDictionaryAction.getWord("ACTUALARTWORK")));
        previewSP = new ScrollPane();
        previewSP.setId("subpopup");
        previewSP.setPrefSize(objFabric.getObjConfiguration().WIDTH/2,objFabric.getObjConfiguration().HEIGHT/3);
        previewSP.setTooltip(new Tooltip(objDictionaryAction.getWord("PREVIEWARTWORK")));
        
        DoubleProperty viPosition = new SimpleDoubleProperty();
        viPosition.bind(imageSP.vvalueProperty());
        viPosition.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                 previewSP.setVvalue((double) arg2);
            }
        }); 
        DoubleProperty vpPosition = new SimpleDoubleProperty();
        vpPosition.bind(previewSP.vvalueProperty());
        vpPosition.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                 imageSP.setVvalue((double) arg2);
            }
        }); 
        
        containerGP = new GridPane();
        containerGP.setId("subpopup");
        containerGP.setVgap(10);
        containerGP.setHgap(10);
        containerGP.setPrefSize(objFabric.getObjConfiguration().WIDTH,objFabric.getObjConfiguration().HEIGHT*2/3);
        
        colorSP = new ScrollPane();
        yarnSP = new ScrollPane();
        weaveSP = new ScrollPane();
        
        colorSP.setPrefWidth(objFabric.getObjConfiguration().WIDTH);
        yarnSP.setPrefHeight(objFabric.getObjConfiguration().HEIGHT*2/3);
        //weaveSP.setPrefSize(objFabric.getObjConfiguration().WIDTH-150,(objFabric.getObjConfiguration().HEIGHT*2/3)-150);
        
        yarnSP.setVbarPolicy(ScrollBarPolicy.NEVER);
        yarnSP.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        colorSP.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        colorSP.setHbarPolicy(ScrollBarPolicy.NEVER);
        
        weaveSP.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        
        //yarnSP.setDisable(true);
        //colorSP.setDisable(true);
        /*
        DoubleProperty vPosition = new SimpleDoubleProperty();
        vPosition.bind(yarnSP.vvalueProperty());
        vPosition.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                 weaveSP.setVvalue((double) arg2);
            }
        }); 
        DoubleProperty hPosition = new SimpleDoubleProperty();
        hPosition.bind(colorSP.hvalueProperty());
        hPosition.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                 weaveSP.setHvalue((double) arg2);
            }
        }); 
        */    
        DoubleProperty vwPosition = new SimpleDoubleProperty();
        vwPosition.bind(weaveSP.vvalueProperty());
        vwPosition.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                 yarnSP.setVvalue((double) arg2);
            }
        }); 
        
        DoubleProperty hwPosition = new SimpleDoubleProperty();
        hwPosition.bind(weaveSP.hvalueProperty());
        hwPosition.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                 colorSP.setHvalue((double) arg2);
            }
        }); 
  
        //setting context menus
        try{
            UtilityAction objUtilityAction = new UtilityAction();
            Device objDevice = new Device(null, "Designing S/W", null, null);
            objDevice.setObjConfiguration(objFabric.getObjConfiguration());
            Device[] devices = objUtilityAction.getDevices(objDevice);
            contextMenu = new ContextMenu();
            for(int i=0; i<devices.length; i++){
                final MenuItem editApplication = new MenuItem(devices[i].getStrDeviceName());
                editApplication.setUserData(devices[i].getStrDeviceId());
                editApplication.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColour()+"/application_integration.png"));
                contextMenu.getItems().add(editApplication);
                editApplication.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        lblStatus.setText(objDictionaryAction.getWord("ARTWORKEDIT"));
                        try {
                            UtilityAction objUtilityAction = new UtilityAction();
                            Device objDevice = new Device(editApplication.getUserData().toString(), null, editApplication.getText(), null);
                            objDevice.setObjConfiguration(objFabric.getObjConfiguration());
                            objUtilityAction.getDevice(objDevice);
                            if(objDevice.getStrDevicePath()!=null){
                                Runtime rt = Runtime.getRuntime();
                                if(objFabric.getObjConfiguration().getObjProcessProcessBuilder()!=null)
                                    objFabric.getObjConfiguration().getObjProcessProcessBuilder().destroy();
                                File file = new File(objDevice.getStrDevicePath());
                                if(file.exists() && !file.isDirectory()) { 
                                    if(bufferedImage!=null){
                                        filePath = System.getProperty("user.dir") + "\\mla\\tempdesign.png";
                                        btnRefresh.setDisable(false);
                                        btnRefresh.setCursor(Cursor.HAND);
                                        file = new File(filePath);
                                        ImageIO.write(bufferedImage, "png", file);
                                        objFabric.getObjConfiguration().setObjProcessProcessBuilder(new ProcessBuilder(objDevice.getStrDevicePath(),filePath).start());
                                    }else{
                                        lblStatus.setText(objDictionaryAction.getWord("NOITEM"));
                                    }
                                }
                            }else{
                                lblStatus.setText(objDictionaryAction.getWord("NOVALUE"));
                            }
                        } catch (SQLException ex) {
                            new Logging("SEVERE",ArtworkEditView.class.getName(),"SQLException:Operation edit artwork"+ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        } catch (IOException ex) {
                            new Logging("SEVERE",ArtworkEditView.class.getName(),"IOException:Operation edit artwork"+ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        } catch (Exception ex) {
                            new Logging("SEVERE",ArtworkEditView.class.getName(),"Exception:Operation edit artwork"+ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });
            }
            imageSP.setContextMenu(contextMenu);
            //contextMenu.hide();
            imageSP.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                    @Override
                    public void handle(ContextMenuEvent event) {
                        if(bufferedImage!=null)
                            event.consume();
                    }
            });
        } catch (SQLException ex) {
            new Logging("SEVERE",ArtworkEditView.class.getName(),"artwork window close",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkEditView.class.getName(),"artwork window close",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
        
        colorHB = new HBox();
        colorHB.setAlignment(Pos.CENTER);
        colorHB.setSpacing(10);
        colorHB.setPadding(Insets.EMPTY);
        colorSP.setContent(colorHB);
        
        yarnVB = new VBox();
        yarnVB.setAlignment(Pos.CENTER);
        yarnVB.setSpacing(10);
        yarnVB.setPadding(Insets.EMPTY);
        yarnSP.setContent(yarnVB);
        
        weaveGP = new GridPane();
        weaveGP.setAlignment(Pos.CENTER);
        weaveGP.setHgap(10);
        weaveGP.setVgap(10);
        weaveSP.setContent(weaveGP);
        
        containerGP.add(new ImageView("/media/artwork_assingment.png"), 0, 0, 1, 1);
        containerGP.add(colorSP, 1, 0, 1, 1);
        containerGP.add(yarnSP, 0, 1, 1, 1);
        containerGP.add(weaveSP, 1, 1, 1, 1);
        
        imageHB.getChildren().addAll(imageSP,previewSP);
        container.getChildren().addAll(imageHB, containerGP);
        
        //Adding Buttons
        VBox rightPane = new VBox();
        rightPane.setSpacing(10);
        rightPane.setPadding(new Insets(0, 20, 10, 20)); 
        
        lblDimension = new Label("Loom Dimension: "+objFabric.getIntWarp()+" X "+objFabric.getIntWeft()+"\nArtwork Size: 0 X 0 \nNumber of Colors: 0 \nPlease select a design !");
        lblDimension.setWrapText(true);
        lblDimension.setPrefWidth(180);
        lblDimension.setStyle("-fx-font: bold italic 11pt Arial; -fx-text-alignment: justify; -fx-text-fill: #0000FF;");
        Tooltip lblNote = new Tooltip("NOTE: \n\n\t If you want to use extra weft then fill weave patterns to corresponding color only, leave other empty, and fill base weave with same weave patterns for all. \n\n\t If you want to create tanchoi, then you just need to put weave at different level diagonaly, and levae base weave empty.\n\nAssign/Reassign Weave - Click \n\nRemove Weave - ALT+Click \n\nEdit/Create Weave - CTRL+Click");
        lblNote.setWrapText(true);
        lblNote.setPrefWidth(150);
        lblNote.setStyle("-fx-font: bold italic 11pt Arial; -fx-text-alignment: justify; -fx-text-fill: #FF0000;");
        lblDimension.setTooltip(lblNote);
        lblDimension.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColour()+"/icontooltip.png"));
        
        lblBackground = new Label();
        lblBackground.setText("Background Color: n/a");
        //lblBackground.setPrefSize(10, 10);
        lblBackground.setStyle("-fx-border-color: #000000; -fx-background-color: #ffffff;");
        
        //Adding check box and combo box
        artworkSizeCB = new CheckBox(objDictionaryAction.getWord("ARTWORKSIZECB"));
        artworkSizeCB.setSelected(objFabric.getBlnArtworkAssingmentSize());
        artworkSizeCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
           public void changed(ObservableValue<? extends Boolean> ov,
             Boolean old_val, Boolean new_val) {
             mergeWeaveImage();
          }
        });
        
        artworkOutlineCB = new CheckBox(objDictionaryAction.getWord("ARTWORKOUTLINECB"));
        artworkOutlineCB.setSelected(objFabric.getBlnArtworkOutline());
        artworkOutlineCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
           public void changed(ObservableValue<? extends Boolean> ov,
             Boolean old_val, Boolean new_val) {
             mergeWeaveImage();
          }
        });
        
        Label weftMode = new Label(objDictionaryAction.getWord("WEFTMODE")+" :");
        weftMode.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWEFTMODE")));
        weftModeCB = new ComboBox();
        weftModeCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWEFTMODE")));
        
        Label fabricType = new Label(objDictionaryAction.getWord("FABRICTYPE")+" :");
        fabricType.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFABRICTYPE")));
        fabricTypeCB = new ComboBox();
        //fabricTypeCB.getItems().addAll("Plain","Kadhua","Fekuwa-Float","Fekuwa-Cutwork","Binding-Irregular","Binding-Regular","Tanchoi","Tissue");   
        fabricTypeCB.getItems().addAll("Plain","Kadhua","Fekuwa-Float","Fekuwa-Cutwork","Binding-Regular","Tanchoi","Tissue");   
        fabricTypeCB.setValue("Plain");
        fabricTypeCB.setDisable(true);
        fabricTypeCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFABRICTYPE")));
        
        Label protection= new Label(objDictionaryAction.getWord("CUTOFF")+" :");
        protection.setTooltip(new Tooltip(objDictionaryAction.getWord("CUTOFF")));
        protectionTF = new TextField(Integer.toString(objFabric.getIntProtection())){
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
        protectionTF.setTooltip(new Tooltip(objDictionaryAction.getWord("CUTOFF")));
        
        Label binding= new Label(objDictionaryAction.getWord("BINDING")+" / "+objDictionaryAction.getWord("PROTECTION")+" :");
        binding.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBINDING")));
        bindingTF = new TextField(Integer.toString(objFabric.getIntBinding())){
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
        bindingTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBINDING")));
        
        //Adding buttons
        btnBrowse = new Button(objDictionaryAction.getWord("BROWSE"));
        btnImport = new Button(objDictionaryAction.getWord("IMPORT"));
        btnRefresh = new Button(objDictionaryAction.getWord("REFRESH"));
        btnRemove = new Button(objDictionaryAction.getWord("REMOVE"));
        btnApply = new Button(objDictionaryAction.getWord("APPLY"));
        btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));

        btnBrowse.setMaxWidth(Double.MAX_VALUE);
        btnImport.setMaxWidth(Double.MAX_VALUE);
        btnRefresh.setMaxWidth(Double.MAX_VALUE);
        btnRemove.setMaxWidth(Double.MAX_VALUE);
        btnApply.setMaxWidth(Double.MAX_VALUE);
        btnCancel.setMaxWidth(Double.MAX_VALUE);        

        btnBrowse.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColour()+"/browse.png"));
        btnImport.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColour()+"/import.png"));
        btnRefresh.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColour()+"/update.png"));
        btnRemove.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColour()+"/clear.png"));
        btnApply.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColour()+"/save.png"));
        btnCancel.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColour()+"/close.png"));
        
        btnBrowse.setDefaultButton(true);
        btnImport.setDefaultButton(true);
        btnRefresh.setDisable(true);
        btnRemove.setDefaultButton(true);
        btnApply.setDisable(true);
        btnCancel.setDisable(false);
        
        btnBrowse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                try {                
                    artworkAction("Browse");
                } catch (SQLException ex) {
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    artworkStage.setOpacity(1);
                    new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
                }
            }
        });
        btnImport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                try {                
                    artworkAction("Import");
                } catch (SQLException ex) {
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    artworkStage.setOpacity(1);
                    new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
                }
            }
        });
        btnRefresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                try {
                    artworkAction("Refresh");
                } catch (SQLException ex) {
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    artworkStage.setOpacity(1);
                    new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
                }
            }
        });
        btnRemove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                try {
                    artworkAction("Remove");
                } catch (SQLException ex) {
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    artworkStage.setOpacity(1);
                    new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
                }
            }
        });
        btnApply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                try {                
                    artworkAction("Apply");
                } catch (SQLException ex) {
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    artworkStage.setOpacity(1);
                    new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
                }              
            }
        });
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                try {                
                    artworkAction("Cancel");
                } catch (SQLException ex) {
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    artworkStage.setOpacity(1);
                    new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
                }               
            }
        });
        
        rightPane.getChildren().addAll(lblDimension, lblBackground, artworkSizeCB, artworkOutlineCB, weftMode, weftModeCB, fabricType, fabricTypeCB, protection, protectionTF, binding, bindingTF, 
                btnBrowse, btnImport, btnRefresh, btnRemove, btnApply, btnCancel);
        
        if(objFabric.getStrArtworkID()!=null){
            try {
                initializeArtwork();
            } catch (Exception ex) {
                new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }else if(objFabric.getObjConfiguration().getStrRecentArtwork()!=null){
            try {
                objArtwork=new Artwork();
                objArtwork.setObjConfiguration(objFabric.getObjConfiguration());
                objArtwork.setStrArtworkId(objFabric.getObjConfiguration().getStrRecentArtwork());
                loadArtwork();
            } catch (Exception ex) {
                new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        
        //root.setLeft(leftPane);
        root.setRight(rightPane);        
        root.setCenter(container);
        root.setBottom(footContainer);
        
        artworkStage.setScene(scene);
        artworkStage.getIcons().add(new Image("/media/icon.png"));
        artworkStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWARTWORKASSIGNMENT")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        artworkStage.setIconified(false);
        artworkStage.setResizable(false);
        artworkStage.setX(-5);
        artworkStage.setY(0);
        artworkStage.showAndWait();
    }
    /**
     * artworkAction
     * <p>
     * This method is used for execute action triggered by buttons.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for execute action triggered by buttons.
     * @link        com.mla.fabric.Fabric
     * @link        com.mla.main.Logging
     * @throws      SQLException
     * @exception   Exception
     * @param       actionName [String] name of the action
     */
    public void artworkAction(String actionName) throws SQLException {        
        if (actionName.equalsIgnoreCase("Browse")) {  
            lblStatus.setText(objDictionaryAction.getWord("ACTIONBROWSE"));
            artworkStage.setOpacity(0.8);
            objArtwork=new Artwork();
            objArtwork.setObjConfiguration(objFabric.getObjConfiguration());
            ArtworkImportView objArtworkImportView= new ArtworkImportView(objArtwork);            
            if(objArtwork.getStrArtworkId()!=null){
                objFabric.getObjConfiguration().setStrRecentArtwork(objArtwork.getStrArtworkId());
                loadArtwork();
            }else{
                lblStatus.setText(objDictionaryAction.getWord("NOITEM"));
            }
            artworkStage.setOpacity(1);
        }
        if (actionName.equalsIgnoreCase("Import")) {  
            lblStatus.setText(objDictionaryAction.getWord("ACTIONREFRESH"));
            artworkStage.setOpacity(0.8);
            try {
                FileChooser fileChooser = new FileChooser();             
                //Set extension filter
                FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
                fileChooser.getExtensionFilters().addAll(extFilterPNG);
                //fileChooser.setInitialDirectory(new File(objFabric.getObjConfiguration().strRoot));
                fileChooser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("SELECTTO")+" "+objDictionaryAction.getWord("DESIGN"));
                //Show open file dialog
                File file = fileChooser.showOpenDialog(null);
                if(file!=null){
                    filePath = file.getCanonicalPath();
                    bufferedImage = ImageIO.read(file);
                    /*
                    objArtworkAction = new ArtworkAction();
                    if(file.length()>10480 || bufferedImage.getHeight()>1000 || bufferedImage.getWidth()>1000){
                        lblStatus.setText(objDictionaryAction.getWord("COLORINFO"));
                        BufferedImage bufferedImageesize;
                        if(bufferedImage.getHeight()>bufferedImage.getWidth()){
                            bufferedImageesize = new BufferedImage((int)1000*(bufferedImage.getWidth()/bufferedImage.getHeight()),1000,BufferedImage.TYPE_INT_RGB);
                            Graphics2D g = bufferedImageesize.createGraphics();
                            g.drawImage(bufferedImage, 0, 0, (int)1000*(bufferedImage.getWidth()/bufferedImage.getHeight()),1000, null);
                            g.dispose();
                        }else{
                            bufferedImageesize = new BufferedImage(1000, (int)1000*(bufferedImage.getHeight()/bufferedImage.getWidth()),BufferedImage.TYPE_INT_RGB);
                            Graphics2D g = bufferedImageesize.createGraphics();
                            g.drawImage(bufferedImage, 0, 0, 1000, (int)1000*(bufferedImage.getHeight()/bufferedImage.getWidth()), null);
                            g.dispose();
                        }
                        bufferedImage = bufferedImageesize;
                        bufferedImageesize = null;
                    }
                    if(objArtworkAction.getImageColor(bufferedImage).size()>8 || file.length()>1048576){
                        lblStatus.setText(objDictionaryAction.getWord("COLORINFO"));
                        objArtworkAction = new ArtworkAction();
                        bufferedImage = objArtworkAction.reduceColors(bufferedImage,8);
                    }
                    */
                    objArtworkAction = new ArtworkAction();
                    if(objArtworkAction.getImageColor(bufferedImage).size()>objFabric.getObjConfiguration().getIntColorLimit()){
                        lblStatus.setText(objDictionaryAction.getWord("COLORINFO"));
                        objArtworkAction = new ArtworkAction();
                        bufferedImage = objArtworkAction.reduceColors(bufferedImage,objFabric.getObjConfiguration().getIntColorLimit());
                    }
                    objArtwork = new Artwork();
                    objArtwork.setObjConfiguration(objFabric.getObjConfiguration());
                    String strArtworkID = new IDGenerator().getIDGenerator("ARTWORK_LIBRARY", objFabric.getObjConfiguration().getObjUser().getStrUserID());
                    objArtwork.setStrArtworkId(strArtworkID);
                    objArtwork.setStrArtworkName("External Artwork");
                    objArtwork.setStrArtworkAccess(objFabric.getObjConfiguration().getObjUser().getUserAccess("ARTWORK_LIBRARY"));

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, "png", baos);
                    baos.flush();
                    byte[] imageInByte = baos.toByteArray();  
                    objArtwork.setBytArtworkThumbnil(imageInByte);
                    imageInByte = null;
                    baos.close();

                    colors = objArtworkAction.getImageColor(bufferedImage);
                    objArtwork.setStrArtworkBackground(String.format("#%02X%02X%02X",colors.get(0).getRed(),colors.get(0).getGreen(),colors.get(0).getBlue()));

                    objArtworkAction = new ArtworkAction();
                    if(objArtworkAction.setArtwork(objArtwork)!=0){
                        objFabric.getObjConfiguration().setStrRecentArtwork(objArtwork.getStrArtworkId());
                        lblStatus.setText(objArtwork.getStrArtworkName()+" : "+objDictionaryAction.getWord("DATASAVED"));
                        System.gc();
                        loadArtwork();
                        btnRefresh.setDisable(false);
                        lblStatus.setText(objDictionaryAction.getWord("ACTIONIMPORTDESIGN"));
                    }else{
                        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    }
                }else{
                    lblStatus.setText(objDictionaryAction.getWord("NOITEM"));
                }
            } catch (IOException ex) {
                new Logging("SEVERE",ArtworkEditView.class.getName(),"Operation import",ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            } catch (SQLException ex) {
                new Logging("SEVERE",ArtworkEditView.class.getName(),"Operation device",ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
            artworkStage.setOpacity(1);
        }
        if (actionName.equalsIgnoreCase("Refresh")) {  
            lblStatus.setText(objDictionaryAction.getWord("ACTIONREFRESH"));
            artworkStage.setOpacity(0.8);
            try {
                File file = new File(filePath);
                bufferedImage = ImageIO.read(file);
                objArtworkAction = new ArtworkAction();
                colors = objArtworkAction.getImageColor(bufferedImage);
                if(colors.size()>intColor){
                    lblStatus.setText(objDictionaryAction.getWord("COLORINFO"));
                    objArtworkAction = new ArtworkAction();
                    bufferedImage = objArtworkAction.reduceColors(bufferedImage,intColor);
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", baos);
                baos.flush();
                byte[] imageInByte = baos.toByteArray();  
                objArtwork=new Artwork();
                objArtwork.setBytArtworkThumbnil(imageInByte);
                imageInByte = null;
                baos.close();
                
                colors = objArtworkAction.getImageColor(bufferedImage);
                
                objArtwork.setObjConfiguration(objFabric.getObjConfiguration());
                String strArtworkID = new IDGenerator().getIDGenerator("ARTWORK_LIBRARY", objFabric.getObjConfiguration().getObjUser().getStrUserID());
                objArtwork.setStrArtworkId(strArtworkID);
                objArtwork.setStrArtworkName(objFabric.getStrFabricName());
                objArtwork.setStrArtworkBackground(String.format("#%02X%02X%02X",colors.get(0).getRed(),colors.get(0).getGreen(),colors.get(0).getBlue()));
                objArtwork.setStrArtworkAccess(objFabric.getObjConfiguration().getObjUser().getUserAccess("ARTWORK_LIBRARY"));
                                    
                objArtworkAction = new ArtworkAction();
                if(objArtworkAction.setArtwork(objArtwork)!=0){
                    objFabric.getObjConfiguration().setStrRecentArtwork(objArtwork.getStrArtworkId());
                    lblStatus.setText(objArtwork.getStrArtworkName()+" : "+objDictionaryAction.getWord("DATASAVED"));
                    System.gc();
                }else{
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
                
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageSP.setContent(new ImageView(image));
                previewSP.setContent(new ImageView(image));
                colors = objArtworkAction.getImageColor(bufferedImage);
                lstColorDeatils = objArtworkAction.getImageColorDetails(bufferedImage);
                intColor = colors.size();
                weaveGP.getChildren().clear();
                fontPattern = new byte[intColor][][];
                reversePattern = new byte[intColor][][];
                //weftExtraYarn = new Yarn[intColor-1];
                //colorWeave = new String[intColor*intColor][4];
                /*for(int i = 0; i<weftExtraYarn.length; i++){
                    objYarn = new Yarn(null, "Extra Weft", weftExtraYarn[i].getStrYarnName(), weftExtraYarn[i].getStrYarnColor(), weftExtraYarn[i].getIntYarnRepeat(), weftExtraYarn[i].getStrYarnSymbol(), weftExtraYarn[i].getIntYarnCount(), weftExtraYarn[i].getStrYarnCountUnit(), weftExtraYarn[i].getIntYarnPly(), weftExtraYarn[i].getIntYarnDFactor(), weftExtraYarn[i].getDblYarnDiameter(), weftExtraYarn[i].getIntYarnTwist(), weftExtraYarn[i].getStrYarnTModel(), weftExtraYarn[i].getIntYarnHairness(), weftExtraYarn[i].getIntYarnHProbability(), weftExtraYarn[i].getDblYarnPrice(), objFabric.getObjConfiguration().getObjUser().getUserAccess("YARN_LIBRARY"),objFabric.getObjConfiguration().getObjUser().getStrUserID(),null);
                    objYarn.setObjConfiguration(objFabric.getObjConfiguration());
                    weftExtraYarn[i] = objYarn;
                }*/
                //fabricTypeCB.setValue(objFabric.getStrFabricType());
                //colorWeave = objFabric.getColorWeave();
                for(int i=0; i<intColor; i++){
                    colorWeave[i][0] = String.format("#%02X%02X%02X",colors.get(i).getRed(),colors.get(i).getGreen(),colors.get(i).getBlue());
                    colorWeave[i][3] = ((ArrayList)lstColorDeatils.get(i)).get(1).toString();
                }
          
                //intExtraWeft = objFabric.getIntExtraWeft();
                //plot all UX for panels
                colorPanel();
                weavePanel();
                DropDownWeft();
                //intExtraWeft = objFabric.getIntExtraWeft();
                weftModeCB.setValue(intExtraWeft+" Extra Weft");
                if(intExtraWeft>0)
                    fabricTypeCB.setDisable(false);
                lblDimension.setText("Loom Dimension: "+objFabric.getIntWarp()+" X "+objFabric.getIntWeft()+"\nArtwork Size: "+bufferedImage.getWidth()+" X "+bufferedImage.getHeight()+"\nNumber of Colors: "+intColor);
                lblBackground.setText("Background Color: "+backgroundColor);
                lblBackground.setStyle("-fx-border-color: #000000; -fx-background-color: "+backgroundColor+";");
                btnApply.setDisable(false);
                btnRemove.setDisable(false);
                lblStatus.setText(objDictionaryAction.getWord("ACTIONIMPORTDESIGN"));
            } catch (IOException ex) {
                new Logging("SEVERE",ArtworkEditView.class.getName(),"Operation import",ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            } catch (SQLException ex) {
                new Logging("SEVERE",ArtworkEditView.class.getName(),"Operation device",ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
            artworkStage.setOpacity(1);
        }
        if (actionName.equalsIgnoreCase("Remove")) { 
            lblStatus.setText(objDictionaryAction.getWord("ACTIONREMOVE"));
            objFabric.setStrArtworkID(null);
            fontPattern =null;
            reversePattern =null;
            colorWeave = null;
            bufferedImage = null;
            colors.clear();
            lstColorDeatils.clear();
            System.gc();
            artworkStage.close();
        }
        if (actionName.equalsIgnoreCase("Apply")) {
            lblStatus.setText(objDictionaryAction.getWord("ACTIONAPPLY"));
            if(fontPattern.length==colors.size() && reversePattern.length==colors.size()){
                artworkStage.setOpacity(0.8);
                objFabric.setStrArtworkID(objArtwork.getStrArtworkId());
                objFabric.setColorWeave(colorWeave);
                objFabric.setIntExtraWeft(intExtraWeft);
                objFabric.setColorArtwork(backgroundColor);
                objFabric.setColorCountArtwork(colors.size());
                objFabric.setWeftExtraYarn(weftExtraYarn);
                objFabric.setWarpExtraYarn(warpExtraYarn);
                objFabric.setStrFabricType(fabricTypeCB.getValue().toString());
                objFabric.setIntProtection(Integer.parseInt(protectionTF.getText()));
                objFabric.setIntBinding(Integer.parseInt(bindingTF.getText()));
                objFabric.setBlnArtworkAssingmentSize(artworkSizeCB.isSelected());
                objFabric.setBlnArtworkOutline(artworkOutlineCB.isSelected());
                if(colorWeave[0][1]!=null){//base weave id
                    Weave objWeave = new Weave();
                    objWeave.setObjConfiguration(objFabric.getObjConfiguration());
                    objWeave.setStrWeaveID(colorWeave[0][1]);
                    WeaveAction objWeaveAction = new WeaveAction(); 
                    objWeaveAction.getWeave(objWeave);
                    objWeaveAction = new WeaveAction(); 
                    objWeaveAction.extractWeaveContent(objWeave);
                    objFabric.setStrBaseWeaveID(objWeave.getStrWeaveID());
                    objFabric.setBaseWeaveMatrix(objWeave.getDesignMatrix());
                }
                //objFabric.setBlnArtworkAspectRatio(artworkRatioCB.isSelected());
                if(objFabric.getBlnArtworkAssingmentSize()){
                    objFabric.setIntWarp(bufferedImage.getWidth());
                    objFabric.setIntWeft(bufferedImage.getHeight());
                    objFabric.setIntHooks((int) Math.ceil(objFabric.getIntWarp()/(double)objFabric.getIntTPD()));
                }
                BufferedImage artwork = new BufferedImage(objFabric.getIntWarp(), objFabric.getIntWeft(),BufferedImage.TYPE_INT_RGB);        
                Graphics2D g = artwork.createGraphics();
                g.drawImage(bufferedImage, 0, 0, objFabric.getIntWarp(), objFabric.getIntWeft(), null);
                g.dispose();

                /*
                BufferedImage convertedImg = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
                convertedImg.getGraphics().drawImage(bufferedImage, 0, 0, null);
                bufferedImage=objArtworkAction.getImageColorBorderOwn(convertedImg);
                */
                
                objArtworkAction = new ArtworkAction();
                BufferedImage artworkBorder=objArtworkAction.getImageColorBorder(artwork);
                
                /*try {
                    mageIO.write(artworkBorder, "png", new File(System.getProperty("user.dir")+"/mla/test.png"));
                } catch (IOException ex) {
                    Logger.getLogger(ArtworkEditView.class.getName()).log(Level.SEVERE, null, ex);
                }*/
                
                int aw = artwork.getWidth();
                int ah = artwork.getHeight();        
                byte[][] fontMatrix = new byte[ah][aw];
                byte[][] reverseMatrix = new byte[ah][aw];
                byte[][] borderMatrix = new byte[ah][aw];

                for (int i = 0; i < ah; i++){
                    for (int j = 0; j < aw; j++){
                        int pixel = artwork.getRGB(j, i);
                        int red   = (pixel & 0x00ff0000) >> 16;
                        int green = (pixel & 0x0000ff00) >> 8;
                        int blue  =  pixel & 0x000000ff;                    
                        java.awt.Color color = new java.awt.Color(red,green,blue); 
                        
                        int pixelB = artworkBorder.getRGB(j, i);
                        int redB   = (pixelB & 0x00ff0000) >> 16;
                        int greenB = (pixelB & 0x0000ff00) >> 8;
                        int blueB  =  pixelB & 0x000000ff;              
                        java.awt.Color colorB = new java.awt.Color(redB,greenB,blueB); 
                        
                        for(int k=0; k<colors.size(); k++){
                            if(colors.get(k).equals(color)){
                                fontMatrix[i][j] = (byte)(k+10);
                                reverseMatrix[i][j] = (byte)(k+10);
                            }
                            if(colors.get(k).equals(colorB)){
                                if(k>0)
                                    borderMatrix[i][j] = (byte)(k+1);
                            }
                        }
                    }
                }
                
                for (int i = 0; i < ah; i++){
                    for (int j = 0; j < aw; j++){
                        int level = 0;
                        for (int k = 0; k < intExtraWeft; k++){                     
                            objFabric.setColorWeave(colorWeave);
                        }                
                    }
                }
                
                // Reverse Side Matrix Preparation
                for(int c=0; c<reversePattern.length; c++){
                    byte[][] patternMatrix =  reversePattern[c]; 
                    int ph=patternMatrix.length;
                    int pw=patternMatrix[0].length;
                    byte[][] repeatMatrix = new byte[ah][aw];           
                    for(int i=0;i<ah;i++) {
                        for(int j=0;j<aw;j++) {
                            if(i>=ph && j<pw){
                                 repeatMatrix[i][j] = patternMatrix[i%ph][j];  
                            }else if(i<ph && j>=pw){
                                 repeatMatrix[i][j] = patternMatrix[i][j%pw];  
                            }else if(i>=ph && j>=pw){
                                 repeatMatrix[i][j] = patternMatrix[i%ph][j%pw];  
                            }else{
                                repeatMatrix[i][j] = patternMatrix[i][j]; 
                            }
                        }
                    }                        
                    for (int i = 0; i < ah; i++){
                        for (int j = 0; j < aw; j++){
                            if(reverseMatrix[i][j]==(c+10)){
                                reverseMatrix[i][j] = repeatMatrix[i][j];
                                if(borderMatrix[i][j]>1 && objFabric.getBlnArtworkOutline())
                                    reverseMatrix[i][j] = 1;
                            }
                        }
                    }
                    repeatMatrix = null;
                    patternMatrix = null;
                }
                // Font Side Matrix Preparation
                for(int c=0; c<fontPattern.length; c++){
                    byte[][] patternMatrix =  fontPattern[c]; 
                    int ph=patternMatrix.length;
                    int pw=patternMatrix[0].length;
                    byte[][] repeatMatrix = new byte[ah][aw];
                    int border = 0;
                    
                    for(int i=0;i<ah;i++) {
                        for(int j=0;j<aw;j++) {
                            if(i>=ph && j<pw){
                                repeatMatrix[i][j] = patternMatrix[i%ph][j];  
                            }else if(i<ph && j>=pw){
                                repeatMatrix[i][j] = patternMatrix[i][j%pw];  
                            }else if(i>=ph && j>=pw){
                                repeatMatrix[i][j] = patternMatrix[i%ph][j%pw];  
                            }else{
                                repeatMatrix[i][j] = patternMatrix[i][j]; 
                            }
                            border = (border<repeatMatrix[i][j])?repeatMatrix[i][j]:border;
                        }
                    }
                    /*
                    for (int i = 0; i < ah; i++){
                        for (int j = 0; j < aw; j++){
                            border = (border<repeatMatrix[i][j])?repeatMatrix[i][j]:border;
                        }
                    }
                    */
                    for (int i = 0; i < ah; i++){
                        for (int j = 0; j < aw; j++){
                            if(fontMatrix[i][j]==(c+10)){
                                fontMatrix[i][j] = repeatMatrix[i][j];
                                if(borderMatrix[i][j]>1 && objFabric.getBlnArtworkOutline())
                                    fontMatrix[i][j] = (byte)(border); //borderMatrix[i][j];
                            }
                        }
                    }
                    repeatMatrix = null;
                    patternMatrix = null;
                }
//------------------- New border code start here --------------------//                
                //Weave type action "Plain","Kadhua","Fekuwa-Float","Fekuwa-Cutwork","Binding-Regular","Binding-Irregular","Tanchoi","Tissue"
                if(objFabric.getStrFabricType().equalsIgnoreCase("Fekuwa-Float")){
                    for (int i = 0; i < ah; i++){
                        byte c=0;
                        for (int j = 0; j < aw; j++){
                            if(borderMatrix[i][j]>1)
                                c=fontMatrix[i][j];
                        }
                        if(c!=0){
                            for (int j = aw-1; j >= 0; j--){
                                if(borderMatrix[i][j]>1)
                                    c=fontMatrix[i][j];
                                if(fontMatrix[i][j]<2)
                                    reverseMatrix[i][j] = c;
                            }
                        }
                    }
                } else if(objFabric.getStrFabricType().equalsIgnoreCase("Fekuwa-Cutwork")){
                /*
                    //for(int k=0; k<objFabric.getIntProtection(); k++){
                        for (int i = 0; i < ah; i++){
                            for (int j = 0; j < aw; j++){
                                if(borderMatrix[i][j]>1 && i%6==0){
                                    //reverseMatrix[i][j] = fontMatrix[i][j];
                                    if(i>0 && j>0)
                                        reverseMatrix[i-1][j-1] = fontMatrix[i][j];
                                    if(i>0 && j<aw-2)
                                        reverseMatrix[i-1][j+1] = fontMatrix[i][j];
                                    if(i<ah-2 && j>0)
                                        reverseMatrix[i+1][j-1] = fontMatrix[i][j];
                                    if(i<ah-2 && j<aw-2)
                                        reverseMatrix[i+1][j+1] = fontMatrix[i][j];
                                }
                            }
                        }
                    //}
                    */
                    String[] newBorderMatrix = new String[fontMatrix.length];
                    String tab;
                    for(int x = 0; x < ah; x++) {
                        tab="";
                        for(int y = 0; y < aw; y++) {
                            tab+=fontMatrix[x][y];
                        }
                        newBorderMatrix[x] = tab;
                    }
                
                    for(int c=1; c<=intExtraWeft; c++){
                        for(int x = 0; x < ah; x++) {
                            int y=newBorderMatrix[x].indexOf(Integer.toString(c+1));
                            int z=newBorderMatrix[x].lastIndexOf(Integer.toString(c+1));
                            if(y>=0 && z>=y){
                                reverseMatrix[x][y]=(byte)(c+1);
                                reverseMatrix[x][z]=(byte)(c+1);                        
                                for(int j=y;j<=z;j++){
                                    if(fontMatrix[x][j]<=1)
                                        reverseMatrix[x][j]=(byte)(c+1);
                                }
                                for(int j=0; j<objFabric.getIntBinding(); j++){
                                    //if(x%2==0){
                                        if(x>0 && y-j>0){
                                            if((x+y-j)%2==0)
                                                reverseMatrix[x][y-j] = (byte)(c+1);
                                            else
                                                fontMatrix[x][y-j] = (byte)(c+1);
                                        }
                                        if(x>0 && z+j<aw){
                                            if((x+z+j)%2==0)
                                                reverseMatrix[x][z+j] = (byte)(c+1);
                                            else
                                                fontMatrix[x][z+j] = (byte)(c+1);
                                        }
                                    //}
                                }
                                for(int j=objFabric.getIntBinding(); j<objFabric.getIntProtection()+objFabric.getIntBinding(); j++){
                                    //if(x%2==0){
                                        if(x>0 && y-j>0)
                                            reverseMatrix[x][y-j] = (byte)(c+1);
                                        if(x>0 && z+j<aw)
                                            reverseMatrix[x][z+j] = (byte)(c+1);
                                        
                                    //}
                                }
                            }
                        }
                    }
                
                } else if(objFabric.getStrFabricType().equalsIgnoreCase("Kadhua")){
                    for (int i = 0; i < ah; i++){
                        for (int j = 0; j < aw; j++){
                            if(borderMatrix[i][j]>1)
                                reverseMatrix[i][j] = fontMatrix[i][j];
                        }
                    }
                } else if(objFabric.getStrFabricType().equalsIgnoreCase("Tanchoi")){
                 ArrayList<java.awt.Color> lstColors = new ArrayList<java.awt.Color>();
                    for(byte i=0; i<intColor;i++){
                        if(colorWeave[i][1]!=null){
                            //lstColors.add(colors.get(i).getRGB());
                            lstColors.add(colors.get(i));
                        }
                    }
                    
                    // Font Side Matrix Preparation
                    int m =0;
                    for (int i = 0,n=0; i < ah; i++){
                        for (int j = 0; j < aw; j++){
                            if(fontMatrix[i][j]==0){
                                if(bufferedImage.getRGB(j%bufferedImage.getWidth(), i%bufferedImage.getHeight())==colors.get(0).getRGB()){
                                    //base color
                                    do{
                                        m=(m+1)%lstColors.size();
                                    }while(m==0);
                                    fontMatrix[i][j]= (byte)(colors.indexOf(lstColors.get(m))+1);
                                }else{
                                    //motif color
                                    for(int p=0; p<lstColors.size(); p++)
                                        if(lstColors.get(p).getRGB() == bufferedImage.getRGB(j%bufferedImage.getWidth(), i%bufferedImage.getHeight()))
                                            fontMatrix[i][j]= (byte)(colors.indexOf(lstColors.get(p))+1);
                                }
                            }
                        }
                    }
                    //Back
                    int n =0;
                    for (int i = 0; i < ah; i++){
                        for (int j = 0; j < aw; j++){
                            if(bufferedImage.getRGB(j%bufferedImage.getWidth(), i%bufferedImage.getHeight())==colors.get(0).getRGB()){
                                //base color
                                if(reverseMatrix[i][j]==0){
                                    do{
                                        n=(n+1)%lstColors.size();
                                    }while(n==0);
                                    reverseMatrix[i][j]= (byte)(colors.indexOf(lstColors.get(n))+1);
                                }
                            }else{
                                //motif color
                                if(reverseMatrix[i][j]==1){
                                    int p=0;
                                    for(; p<lstColors.size(); p++)
                                        if(lstColors.get(p).getRGB() == bufferedImage.getRGB(j%bufferedImage.getWidth(), i%bufferedImage.getHeight()))
                                             break;
                                    do{
                                        n=(n+1)%lstColors.size();
                                    }while(n==0 || n==p);
                                    reverseMatrix[i][j]= (byte)(colors.indexOf(lstColors.get(n))+1);
                                }else{
                                    reverseMatrix[i][j]= (byte)1;
                                }
                            }
                        }
                    }
                } else if(objFabric.getStrFabricType().equalsIgnoreCase("Tissue")){
                    
                    
                } else if(objFabric.getStrFabricType().equalsIgnoreCase("Binding-Regular")){
                    for (int i = 0; i < ah; i++){
                        byte c=0;
                        for (int j = 0; j < aw; j++){
                            if(borderMatrix[i][j]>1)
                                c=fontMatrix[i][j];
                        }
                        if(c!=0){
                           for (int j = 0, k=0; j < aw; j++){
                                if(borderMatrix[i][j]>1){
                                    c=fontMatrix[i][j];
                                    k=(objFabric.getIntBinding()+k+j)%aw;
                                }
                                if(c!=0 && fontMatrix[i][j]<2){
                                    reverseMatrix[i][j] = c;
                                    if(k>0){
                                        reverseMatrix[i][k] = 1;
                                        fontMatrix[i][k] = c;
                                    }
                                }
                            }
                        }
                    }
                } else if(objFabric.getStrFabricType().equalsIgnoreCase("Binding-Irregular")){
                    for (int i = 0; i < ah; i++){
                        byte c=0;
                        for (int j = 0; j < aw; j++){
                            if(borderMatrix[i][j]>1)
                                c=fontMatrix[i][j];
                        }
                        if(c!=0){
                            for (int j = 0, k=0; j < aw; j++){
                                if(borderMatrix[i][j]>1){
                                    c=fontMatrix[i][j];
                                    k=(objFabric.getIntBinding()+ThreadLocalRandom.current().nextInt(k, aw))%aw;
                                }
                                if(c!=0 && fontMatrix[i][j]<2){
                                    reverseMatrix[i][j] = c;
                                    if(k>0){
                                        reverseMatrix[i][k] = 1;
                                        fontMatrix[i][k] = c;
                                    }
                                }
                            }
                        }
                    }
                } else{
                    
                }
//------------------- New border code end here --------------------//                
                
//------------------- Old border code start here --------------------//
/*
                // Border Point Preparation
                String[] borderMatrix = new String[fontMatrix.length];
                String tab;
                for(int x = 0; x < ah; x++) {
                    tab="";
                    for(int y = 0; y < aw; y++) {
                        tab+=fontMatrix[x][y];
                    }
                    borderMatrix[x] = tab;
                }
                
                //Weave type action "Plain","Kadhua","Binding-Regular","Fekuwa-Float","Fekuwa-Cutwork","Binding-Irregular","Tanchoi","Tissue"
                if(objFabric.getStrFabricType().equalsIgnoreCase("Fekuwa-Float")){
                    for(int c=1; c<=intWeft; c++){
                        for(int x = 0; x < ah; x++) {
                            int y=borderMatrix[x].indexOf(Integer.toString(c+1));
                            int z=borderMatrix[x].lastIndexOf(Integer.toString(c+1));
                            if(y>=0)
                                for(int p=0;p<=y;p++)
                                    reverseMatrix[x][p]=(byte)(c+1);
                            if(z>=0)
                                for(int p=aw-1;p>=z;p--)
                                    reverseMatrix[x][p]=(byte)(c+1);
                        }
                    }
                } else if(objFabric.getStrFabricType().equalsIgnoreCase("Fekuwa-Cutwork")){
                    for(int c=1; c<=intWeft; c++){
                        for(int x = 0; x < ah; x++) {
                            int y=borderMatrix[x].indexOf(Integer.toString(c+1));
                            int z=borderMatrix[x].lastIndexOf(Integer.toString(c+1));
                            for(int p=0; p<intProtection; p++){
                                if(y-p>=0){
                                    if((ah+p)%2==0)
                                        reverseMatrix[x][y-p]=(byte)(c+1);
                                    else
                                        fontMatrix[x][y-p]=(byte)(c+1);                                     
                                }
                                if(z+p<aw && z>=0){
                                    if((ah+p)%2==0)
                                        reverseMatrix[x][z+p]=(byte)(c+1);
                                    else
                                        fontMatrix[x][z+p]=(byte)(c+1);
                                }
                            }
                        }
                    }    
                } else if(objFabric.getStrFabricType().equalsIgnoreCase("Binding-Irregular")){
                    for(int c=1; c<=intWeft; c++){
                        for(int x = 0; x < ah; x++) {
                            int y=borderMatrix[x].indexOf(Integer.toString(c+1));
                            int z=borderMatrix[x].lastIndexOf(Integer.toString(c+1));
                            if(y>=0)
                                for(int p=0;p<=y;p++){
                                    if(p==objFabric.getIntBinding()+ThreadLocalRandom.current().nextInt(0, y))
                                        fontMatrix[x][p]=(byte)(c+1);
                                    else
                                        reverseMatrix[x][p]=(byte)(c+1);
                                }                                    
                            if(z>=0)
                                for(int p=aw-1;p>=z;p--){
                                    if(p==objFabric.getIntBinding()+ThreadLocalRandom.current().nextInt(z, aw))
                                        fontMatrix[x][p]=(byte)(c+1);
                                    else
                                        reverseMatrix[x][p]=(byte)(c+1);
                                }
                        }
                    }
                } else if(objFabric.getStrFabricType().equalsIgnoreCase("Tanchoi")){
                    Yarn[] weftYarn = new Yarn[weftExtraYarn.length];
                    Yarn objYarn=null;
                    for(int x=0; x<weftExtraYarn.length; x++){
                        char character = (char) (97+x);
                        objYarn = new Yarn(null, "Weft", objFabric.getObjConfiguration().getStrWeftName(), weftExtraYarn[x].getStrYarnColor(), weftExtraYarn[x].getIntYarnRepeat(), ""+character, weftExtraYarn[x].getIntYarnCount(), weftExtraYarn[x].getStrYarnCountUnit(), weftExtraYarn[x].getIntYarnPly(), weftExtraYarn[x].getIntYarnDFactor(), weftExtraYarn[x].getDblYarnDiameter(), weftExtraYarn[x].getIntYarnTwist(), weftExtraYarn[x].getStrYarnTModel(), weftExtraYarn[x].getIntYarnHairness(), weftExtraYarn[x].getIntYarnHProbability(), weftExtraYarn[x].getDblYarnPrice(), objFabric.getObjConfiguration().getObjUser().getUserAccess("YARN_LIBRARY"),objFabric.getObjConfiguration().getObjUser().getStrUserID(),null);
                        objYarn.setObjConfiguration(objFabric.getObjConfiguration());
                        weftYarn[x] = objYarn;
                    }
                    objYarn = new Yarn(null, "Warp", objFabric.getObjConfiguration().getStrWarpName(), "#"+objFabric.getObjConfiguration().getStrWarpColor(), objFabric.getObjConfiguration().getIntWarpRepeat(), "A", objFabric.getObjConfiguration().getIntWarpCount(), objFabric.getObjConfiguration().getStrWarpUnit(), objFabric.getObjConfiguration().getIntWarpPly(), objFabric.getObjConfiguration().getIntWarpFactor(), objFabric.getObjConfiguration().getDblWarpDiameter(), objFabric.getObjConfiguration().getIntWarpTwist(), objFabric.getObjConfiguration().getStrWarpSence(), objFabric.getObjConfiguration().getIntWarpHairness(), objFabric.getObjConfiguration().getIntWarpDistribution(), objFabric.getObjConfiguration().getDblWarpPrice(), objFabric.getObjConfiguration().getObjUser().getUserAccess("YARN_LIBRARY"),objFabric.getObjConfiguration().getObjUser().getStrUserID(),null);
                    objYarn.setObjConfiguration(objFabric.getObjConfiguration());
                    Yarn[] warpYarn = new Yarn[]{objYarn};
                    objFabric.setWeftYarn(weftYarn);
                    objFabric.setWarpYarn(warpYarn);
                } else if(objFabric.getStrFabricType().equalsIgnoreCase("Kadhua")){
                    for(int c=1; c<=intWeft; c++){
                        for(int x = 0; x < ah; x++) {
                            int y=borderMatrix[x].indexOf(Integer.toString(c+1));
                            int z=borderMatrix[x].lastIndexOf(Integer.toString(c+1));
                            if(y>=0)
                                reverseMatrix[x][y]=(byte)(c+1);
                            if(z>=0)
                                reverseMatrix[x][z]=(byte)(c+1);
                        }
                    }
                } else if(objFabric.getStrFabricType().equalsIgnoreCase("Binding-Regular")){
                    for(int c=1; c<=intWeft; c++){
                        for(int x = 0; x < ah; x++) {
                            int y=borderMatrix[x].indexOf(Integer.toString(c+1));
                            int z=borderMatrix[x].lastIndexOf(Integer.toString(c+1));
                            int d=intProtection;
                            if(y>=0)
                                for(int p=y-intProtection;p>=0 && p<=y;p++)
                                    reverseMatrix[x][p]=(byte)0;
                            if(z>=0)
                                for(int p=z+intProtection;p<=aw-1 && p>=z;p--)
                                    reverseMatrix[x][p]=(byte)0;
                        }
                    }
                } else if(objFabric.getStrFabricType().equalsIgnoreCase("Plain")){
                    for(int c=1; c<=intWeft; c++){
                        for(int x = 0; x < ah; x++) {
                            int y=borderMatrix[x].indexOf(Integer.toString(c+1));
                            int z=borderMatrix[x].lastIndexOf(Integer.toString(c+1));
                            if(y>=0)
                                reverseMatrix[x][y]=(byte)(c+intColor+1);
                            if(z>=0)
                                reverseMatrix[x][z]=(byte)(c+intColor+1);
                        }
                    }
                }
  */              
//------------------- Old border code end here --------------------//                
                
                objFabric.setArtworkMatrix(fontMatrix);
                objFabric.setReverseMatrix(reverseMatrix);
                fontMatrix = null;
                reverseMatrix = null;
                fontPattern = null;
                reversePattern =null;
            }else{
                artworkStage.setOpacity(1);
                lblStatus.setText("Please assing proper weaving patterns");
            }
            fontPattern =null;
            reversePattern =null;
            colorWeave = null;
            bufferedImage = null;
            colors.clear();
            lstColorDeatils.clear();
            System.gc();
            artworkStage.close();
            artworkStage.setOpacity(1);
        }       
        if (actionName.equalsIgnoreCase("Cancel")) {
            lblStatus.setText(objDictionaryAction.getWord("ACTIONCANCEL"));
            weftExtraYarn = null;
            fontPattern =null;
            reversePattern =null;
            colorWeave = null;
            bufferedImage = null;
            colors.clear();
            lstColorDeatils.clear();
            System.gc();
            artworkStage.close();
        }
        artworkStage.setOpacity(1);
    }    
    /**
     * initializeArtwork
     * <p>
     * This method is used for initialization of artwork used in  fabric.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for initialization of artwork used in  fabric.
     * @link        com.mla.fabric.Fabric
     * @link        com.mla.main.Logging
     * @throws      SQLException
     * @throws      IOException
     */
    public void initializeArtwork() throws SQLException, IOException {
        objArtwork=new Artwork();
        objArtwork.setObjConfiguration(objFabric.getObjConfiguration());
        if(objFabric.getStrArtworkID()==null||objFabric.getStrArtworkID().equalsIgnoreCase("null"))
            return;
        objArtwork.setStrArtworkId(objFabric.getStrArtworkID());
        objArtworkAction = new ArtworkAction();
        objArtworkAction.getArtwork(objArtwork);
        byte[] bytArtworkThumbnil=objArtwork.getBytArtworkThumbnil();
        SeekableStream stream = new ByteArraySeekableStream(bytArtworkThumbnil);
        String[] names = ImageCodec.getDecoderNames(stream);
        ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
        RenderedImage im = dec.decodeAsRenderedImage();
        bufferedImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
        bytArtworkThumbnil=null;
        System.gc();                        
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        imageSP.setContent(new ImageView(image));
        previewSP.setContent(new ImageView(image));
        colors = objArtworkAction.getImageColor(bufferedImage);
        lstColorDeatils = objArtworkAction.getImageColorDetails(bufferedImage);
        intColor = colors.size();
        weaveGP.getChildren().clear();
        fontPattern = new byte[intColor][][];
        reversePattern = new byte[intColor][][];
        weftExtraYarn = new Yarn[intColor-1];
        colorWeave = new String[intColor*intColor][4];
        for(int i = 0, j=(objFabric.getWeftExtraYarn().length<weftExtraYarn.length?objFabric.getWeftExtraYarn().length:weftExtraYarn.length);i<j; i++){
            objYarn = new Yarn(null, "Extra Weft", objFabric.getWeftExtraYarn()[i].getStrYarnName(), objFabric.getWeftExtraYarn()[i].getStrYarnColor(), objFabric.getWeftExtraYarn()[i].getIntYarnRepeat(), objFabric.getWeftExtraYarn()[i].getStrYarnSymbol(), objFabric.getWeftExtraYarn()[i].getIntYarnCount(), objFabric.getWeftExtraYarn()[i].getStrYarnCountUnit(), objFabric.getWeftExtraYarn()[i].getIntYarnPly(), objFabric.getWeftExtraYarn()[i].getIntYarnDFactor(), objFabric.getWeftExtraYarn()[i].getDblYarnDiameter(), objFabric.getWeftExtraYarn()[i].getIntYarnTwist(), objFabric.getWeftExtraYarn()[i].getStrYarnTModel(), objFabric.getWeftExtraYarn()[i].getIntYarnHairness(), objFabric.getWeftExtraYarn()[i].getIntYarnHProbability(), objFabric.getWeftExtraYarn()[i].getDblYarnPrice(), objFabric.getObjConfiguration().getObjUser().getUserAccess("YARN_LIBRARY"),objFabric.getObjConfiguration().getObjUser().getStrUserID(),null);
            objYarn.setObjConfiguration(objFabric.getObjConfiguration());
            weftExtraYarn[i] = objYarn;
        }
        fabricTypeCB.setValue(objFabric.getStrFabricType());
        //colorWeave = objFabric.getColorWeave();
        for(int i=0, j=(objFabric.getColorWeave().length<colorWeave.length?objFabric.getColorWeave().length:colorWeave.length);i<j;i++){
            colorWeave[i][0] = objFabric.getColorWeave()[i][0];
            colorWeave[i][1] = objFabric.getColorWeave()[i][1];
            colorWeave[i][2] = objFabric.getColorWeave()[i][2];
            colorWeave[i][3] = ((ArrayList)lstColorDeatils.get(i%intColor)).get(1).toString();
            intExtraWeft = i/intColor;
        }
        for(int i=0;i<intColor;i++){
            colors.set(i, java.awt.Color.decode(colorWeave[i][0])); 
        }
        intExtraWeft = objFabric.getIntExtraWeft();
        //plot all UX for panels
        colorPanel();
        weavePanel();
        DropDownWeft();
        intExtraWeft = objFabric.getIntExtraWeft();
        weftModeCB.setValue(intExtraWeft+" Extra Weft");
        if(intExtraWeft>0)
            fabricTypeCB.setDisable(false);
        lblDimension.setText("Loom Dimension: "+objFabric.getIntWarp()+" X "+objFabric.getIntWeft()+"\nArtwork Size: "+bufferedImage.getWidth()+" X "+bufferedImage.getHeight()+"\nNumber of Colors: "+intColor);
        lblBackground.setText("Background Color: "+backgroundColor);
        lblBackground.setStyle("-fx-border-color: #000000; -fx-background-color: "+backgroundColor+";");
        btnApply.setDisable(false);
        btnRemove.setDisable(false);
    }    
    /**
     * loadArtwork
     * <p>
     * This method is used for load artwork image used in  fabric.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for initialization of artwork image used in  fabric.
     * @link        com.mla.fabric.Fabric
     * @link        com.mla.main.Logging
     * @throws      SQLException
     */
    public void loadArtwork() {
        try {                                      
            //colors.removeAll(colors);
            //if(!colors.isEmpty()) colors.clear();
            //colors.clear();
            objArtworkAction = new ArtworkAction();
            objArtworkAction.getArtwork(objArtwork);
            byte[] bytArtworkThumbnil=objArtwork.getBytArtworkThumbnil();
            SeekableStream stream = new ByteArraySeekableStream(bytArtworkThumbnil);
            String[] names = ImageCodec.getDecoderNames(stream);
            ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
            RenderedImage im = dec.decodeAsRenderedImage();
            bufferedImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
            bytArtworkThumbnil=null;
            System.gc();
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageSP.setContent(new ImageView(image));
            previewSP.setContent(new ImageView(image));
            colors = objArtworkAction.getImageColor(bufferedImage);
            lstColorDeatils = objArtworkAction.getImageColorDetails(bufferedImage);
            intColor = colors.size();
            weaveGP.getChildren().clear();
            fontPattern = new byte[intColor][][];
            reversePattern = new byte[intColor][][];
            colorWeave = new String[intColor*intColor][4];  
            weftExtraYarn = new Yarn[intColor-1];
            for(int i=0; i<intColor; i++){
                colorWeave[i][0] = String.format("#%02X%02X%02X",colors.get(i).getRed(),colors.get(i).getGreen(),colors.get(i).getBlue());
                colorWeave[i][1] = null;
                colorWeave[i][2] = Integer.toString(i);
                colorWeave[i][3] = ((ArrayList)lstColorDeatils.get(i)).get(1).toString();
                if(i==0)
                    colorWeave[0][1]=objFabric.getStrBaseWeaveID();
            }
            intExtraWeft=0;
            colorPanel();
            weavePanel();
            DropDownWeft();       
            lblDimension.setText("Loom Dimension: "+objFabric.getIntWarp()+" X "+objFabric.getIntWeft()+"\nArtwork Size: "+bufferedImage.getWidth()+" X "+bufferedImage.getHeight()+"\nNumber of Colors: "+intColor);
            lblBackground.setText("Background Color: "+backgroundColor);
            lblBackground.setStyle("-fx-border-color: #000000; -fx-background-color: "+backgroundColor+";");
            btnApply.setDisable(false);
        } catch (IOException ex) {
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
            btnApply.setDisable(true);
        }   catch (Exception ex) {
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
        }
    }
    /**
     * colorPanel
     * <p>
     * This method is used for creating UX for color panel.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating UX for color panel.
     * @link        com.mla.fabric.Fabric
     * @link        com.mla.main.Logging
     */
    public void colorPanel(){
        colorHB.getChildren().clear();
        backgroundColor = colorWeave[0][0];
        for(int i=0; i<intColor; i++){
            if(i>0){
                objYarn = new Yarn(null, "Extra Weft", objFabric.getObjConfiguration().getStrWeftName(), colorWeave[i][0], objFabric.getObjConfiguration().getIntWeftRepeat(), "#", objFabric.getObjConfiguration().getIntWeftCount(), objFabric.getObjConfiguration().getStrWeftUnit(), objFabric.getObjConfiguration().getIntWeftPly(), objFabric.getObjConfiguration().getIntWeftFactor(), objFabric.getObjConfiguration().getDblWeftDiameter(), objFabric.getObjConfiguration().getIntWeftTwist(), objFabric.getObjConfiguration().getStrWeftSence(), objFabric.getObjConfiguration().getIntWeftHairness(), objFabric.getObjConfiguration().getIntWeftDistribution(), objFabric.getObjConfiguration().getDblWeftPrice(), objFabric.getObjConfiguration().getObjUser().getUserAccess("YARN_LIBRARY"),objFabric.getObjConfiguration().getObjUser().getStrUserID(),null);
                objYarn.setObjConfiguration(objFabric.getObjConfiguration());
                weftExtraYarn[i-1] = objYarn;
            }
            double percent = Double.parseDouble(colorWeave[i][3]);
            percent = percent*100;
            percent = Math.round(percent);
            percent = percent/100;
            
            String colorText = "Color:"+(i+1)+"\n"+percent+"%\n"+colorWeave[i][0]+"\nR:"+java.awt.Color.decode(colorWeave[i][0]).getRed()+"\nG:"+java.awt.Color.decode(colorWeave[i][0]).getGreen()+"\nB:"+java.awt.Color.decode(colorWeave[i][0]).getBlue();
            final ImageView colorLink = new ImageView();
            colorLink.setFitHeight(111);
            colorLink.setFitWidth(111);
            //colorLink.setTooltip(new Tooltip(colorText));
            Tooltip.install(colorLink, new Tooltip(colorText));
            BufferedImage tempImage = new BufferedImage(111, 111,BufferedImage.TYPE_INT_RGB);        
            Graphics2D g1 = tempImage.createGraphics();
            g1.drawImage(bufferedImage, 0, 0, 111, 111, null);
            g1.dispose();
            
            for(int x=0; x<111; x++){
                for(int y=0; y<111; y++){
                    int pixel = tempImage.getRGB(y, x);     
                    int red   = (pixel & 0x00ff0000) >> 16;
                    int green = (pixel & 0x0000ff00) >> 8;
                    int blue  =  pixel & 0x000000ff; 
                    if(!colorWeave[i][0].equalsIgnoreCase(String.format("#%02X%02X%02X",red,green,blue)))
                        /*if(colorWeave[i][0].equalsIgnoreCase("#FFFFFF"))
                            tempImage.setRGB(y,x,java.awt.Color.BLACK.getRGB()); 
                         else
                         */   tempImage.setRGB(y,x,java.awt.Color.WHITE.getRGB());
                }
            }
            /*
            BufferedImage colorImage = new BufferedImage(111, 111,BufferedImage.TYPE_INT_RGB);        
            Graphics2D g3 = colorImage.createGraphics();
            g3.drawImage(tempImage, 0, 0, 111, 111, null);
            g3.setColor(java.awt.Color.BLACK);
             if(colorWeave[i][0].equalsIgnoreCase("#000000") || colorWeave[i][0].equalsIgnoreCase("#FFFFFF"))
                g3.setColor(java.awt.Color.RED);
            g3.setFont(new Font("Arial Black", Font.BOLD, 11 ));
            g3.drawString("Color:"+(i+1), 10, 10+(0*g3.getFontMetrics().getHeight()));
            g3.drawString(percent+"%", 10, 10+(1*g3.getFontMetrics().getHeight()));
            g3.drawString(colorWeave[i][0], 10, 10+(2*g3.getFontMetrics().getHeight()));
            g3.drawString("R:"+java.awt.Color.decode(colorWeave[i][0]).getRed(), 10, 10+(3*g3.getFontMetrics().getHeight()));
            g3.drawString("G:"+java.awt.Color.decode(colorWeave[i][0]).getGreen(), 10, 10+(4*g3.getFontMetrics().getHeight()));
            g3.drawString("B:"+java.awt.Color.decode(colorWeave[i][0]).getBlue(), 10, 10+(5*g3.getFontMetrics().getHeight()));
            g3.dispose();
            */
            Image image = SwingFXUtils.toFXImage(tempImage, null);
            colorLink.setImage(image);
            tempImage = null;
            //colorImage = null;
            image = null;
            
            colorLink.setStyle("-fx-background-color:"+colorWeave[i][0]+"; -fx-border-color:#000000;");
            colorLink.setUserData(i);
            colorLink.setOnDragDetected(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    /* drag was detected, start a drag-and-drop gesture*/
                    /* allow any transfer mode */
                    Dragboard db = colorLink.startDragAndDrop(TransferMode.ANY);
                    /* Put a string on a dragboard */
                    ClipboardContent content = new ClipboardContent();
                    //int xindex = Integer.parseInt(colorlink.getUserData().toString());
                    content.putString(colorLink.getUserData().toString());
                    db.setContent(content);                    
                    event.consume();
                }
            });
            colorLink.setOnDragOver(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    /* data is dragged over the target */
                    /* accept it only if it is not dragged from the same node 
                     * and if it has a string data */
                    if (event.getGestureSource() != colorLink && event.getDragboard().hasString()) {
                        /* allow for both copying and moving, whatever user chooses */
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
                    event.consume();
                }
            });
            colorLink.setOnDragEntered(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */
                /* show to the user that it is an actual gesture target */
                     if (event.getGestureSource() != colorLink && event.getDragboard().hasString()) {
                         //colorLink.setTextFill(Color.GREEN);
                     }
                     event.consume();
                }
            });
            colorLink.setOnDragExited(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    /* mouse moved away, remove the graphical cues */
                    //colorLink.setTextFill(Color.BLACK);
                    event.consume();
                }
            });
            colorLink.setOnDragDropped(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    /* data dropped */
                    /* if there is a string data on dragboard, read it and use it */
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    if (db.hasString()) {                    
                       ClipboardContent content = new ClipboardContent();
                       content.putString(colorLink.getUserData().toString());                
                       xindex = Integer.parseInt(db.getString());
                       yindex = Integer.parseInt(colorLink.getUserData().toString());
                       colorLink.setStyle("-fx-background-color:"+colorWeave[xindex][0]+"; -fx-border-color:#000000;");
                       //colorLink.setText("Color:"+(yindex+1)+"\n"+colorWeave[xindex][0]+"\nR:"+java.awt.Color.decode(colorWeave[xindex][0]).getRed()+"\nG:"+java.awt.Color.decode(colorWeave[xindex][0]).getGreen()+"\nB:"+java.awt.Color.decode(colorWeave[xindex][0]).getBlue());
                       db.setContent(content);                   
                       success = true;                   
                    }
                    /* let the source know whether the string was successfully 
                     * transferred and used */
                    event.setDropCompleted(success);
                    event.consume();
                 }
            });
            colorLink.setOnDragDone(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    /* the drag and drop gesture ended */
                    /* if the data was successfully moved, clear it */
                    if (event.getTransferMode() == TransferMode.MOVE) {
                        Dragboard db = event.getDragboard();
                        colorLink.setStyle("-fx-background-color:"+colorWeave[yindex][0]+"; -fx-border-color:#000000;");
                        //colorLink.setText("Color:"+(xindex+1)+"\n"+colorWeave[yindex][0]+"\nR:"+java.awt.Color.decode(colorWeave[yindex][0]).getRed()+"\nG:"+java.awt.Color.decode(colorWeave[yindex][0]).getGreen()+"\nB:"+java.awt.Color.decode(colorWeave[yindex][0]).getBlue());
                        swapColor();
                    }
                    event.consume();                    
                    colorPanel();
                }
            });
            colorHB.getChildren().add(colorLink);
        }
    }
    /**
     * swapColor
     * <p>
     * This method is used for swapping color blocks.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for swapping color blocks.
     * @link        com.mla.fabric.Fabric
     * @link        com.mla.main.Logging
     */
    public void swapColor(){
        for(int i=0, x = xindex%intColor, y = yindex%intColor; i<intColor; i++){
            String strTemp = colorWeave[(i*intColor)+x][0];
            colorWeave[(i*intColor)+x][0] = colorWeave[(i*intColor)+y][0];
            colorWeave[(i*intColor)+y][0] = strTemp;
        }
        java.awt.Color colTemp = colors.get(xindex%intColor);
        colors.set(xindex%intColor, colors.get(yindex%intColor));
        colors.set(yindex%intColor, colTemp);
        weavePanelLabel();                
        fontPattern[xindex%intColor]= mergeFontWeave(xindex%intColor);
        reversePattern[yindex%intColor]= mergeReverseWeave(yindex%intColor);
        mergeWeaveImage();         
    }
    
    /**
     * weavePanelLabel
     * <p>
     * This method is used for creating UX for weave panel
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating UX for weave panel.
     * @link        com.mla.fabric.Fabric
     * @link        com.mla.main.Logging
     */
    public void weavePanelLabel(){
        yarnVB.getChildren().clear();
        for(int j=0; j<intColor; j++){
            if(j<=intExtraWeft){
                ImageView yarnLink = new ImageView();
                BufferedImage colorImage = new BufferedImage(111, 111,BufferedImage.TYPE_INT_RGB);        
                Graphics2D g3 = colorImage.createGraphics();
                g3.setColor(new java.awt.Color(colors.get(j).getRGB()));
                g3.fillRect(0, 0, 111, 111);
                g3.setColor(java.awt.Color.BLACK);
                g3.setFont(new Font("Arial Black", Font.BOLD, 9 ));
                if(j==0)
                    g3.drawString("Base Weft", 10, 10+(0*g3.getFontMetrics().getHeight()));
                else
                    g3.drawString("Extra Weft-"+j, 10, 10+(0*g3.getFontMetrics().getHeight()));
                g3.drawString("Yarn", 10, 10+(1*g3.getFontMetrics().getHeight()));
                g3.dispose();
            
                Image image = SwingFXUtils.toFXImage(colorImage, null);
                yarnLink.setImage(image);
                yarnLink.setRotate(-90);
                colorImage = null;
                image = null;            
                yarnVB.getChildren().add(yarnLink);
            }
        }
        ImageView yarnLink = new ImageView();
        BufferedImage colorImage = new BufferedImage(111, 111,BufferedImage.TYPE_INT_RGB);        
        Graphics2D g2 = colorImage.createGraphics();
        g2.setColor(java.awt.Color.WHITE);                
        g2.setFont(new Font("Arial Black", Font.BOLD, 9 ));
        g2.drawString("Font Merge", 10, 10+(0*g2.getFontMetrics().getHeight()));
        g2.drawString("Weave", 10, 10+(1*g2.getFontMetrics().getHeight()));
        g2.dispose();

        Image image = SwingFXUtils.toFXImage(colorImage, null);
        yarnLink.setImage(image);
        yarnLink.setRotate(-90);
        colorImage = null;
        image = null;
        yarnVB.getChildren().add(yarnLink);
        
        yarnLink = new ImageView();
        colorImage = new BufferedImage(111, 111,BufferedImage.TYPE_INT_RGB);        
        Graphics2D g3 = colorImage.createGraphics();
        g3.setColor(java.awt.Color.WHITE);                
        g3.setFont(new Font("Arial Black", Font.BOLD, 9 ));
        g3.drawString("Back Merge", 10, 10+(0*g3.getFontMetrics().getHeight()));
        g3.drawString("Weave", 10, 10+(1*g3.getFontMetrics().getHeight()));
        g3.dispose();

        image = SwingFXUtils.toFXImage(colorImage, null);
        yarnLink.setImage(image);
        yarnLink.setRotate(-90);
        colorImage = null;
        image = null;
        yarnVB.getChildren().add(yarnLink);
    }
    
    /**
     * weavePanel
     * <p>
     * This method is used for creating UX for weave panel
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating UX for weave panel.
     * @link        com.mla.fabric.Fabric
     * @link        com.mla.main.Logging
     */
    public void weavePanel(){
        weavePanelLabel();
        lblStatus.setText("Ploting weave panel");
        weaveGP.getChildren().clear();
        for(int i=0, j=0; j<intColor; j++){
            for(int k=0; k<intColor; i++,k++){
                if(j<=intExtraWeft){
                    Pane weaveP = new Pane();
                    final ImageView weaveIV  = new ImageView("/media/assign_weave.png");
                    weaveIV.setFitHeight(111);              
                    weaveIV.setFitWidth(111);
                    weaveIV.setStyle("-fx-background-color: #123456; -fx-border-color: "+colorWeave[k][0]+"; -fx-border-insets: 5; -fx-border-width: 3; -fx-border-style: dashed;");
                    weaveIV.getStyleClass().addAll("myBox");
                    weaveIV.setUserData(i);
                    weaveIV.setId(Integer.toString(k));
                    if(colorWeave[i][1]!=null && !colorWeave[i][1].equalsIgnoreCase("null")){
                        try {
                            Weave objWeave= new Weave();
                            objWeave.setObjConfiguration(objFabric.getObjConfiguration());
                            objWeave.setStrWeaveID(colorWeave[i][1].toString());  
                            WeaveAction objWeaveAction = new WeaveAction();
                            objWeaveAction.getWeave(objWeave);
                            SeekableStream stream = new ByteArraySeekableStream(objWeave.getBytWeaveThumbnil());
                            String[] names = ImageCodec.getDecoderNames(stream);
                            ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                            RenderedImage im = dec.decodeAsRenderedImage();
                            BufferedImage weaveImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
                            fontPattern[k]= mergeFontWeave(k);
                            reversePattern[k]= mergeReverseWeave(k);
                            mergeWeaveImage();         
                            Image pattern=SwingFXUtils.toFXImage(weaveImage, null);
                            weaveIV.setImage(pattern);
                            objWeave = null;
                        } catch (SQLException ex) {
                            new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        } catch (Exception ex) {
                            new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }else{
                        colorWeave[i][0]=colorWeave[i%intColor][0];
                        colorWeave[i][2]=Integer.toString(i);
                    }
                    ContextMenu weaveCM = new ContextMenu();
                    MenuItem assingWeave = new MenuItem(objDictionaryAction.getWord("ASSINGWEAVECM"));
                    assingWeave.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColour()+"/weave_library.png"));
                    MenuItem removeWeave = new MenuItem(objDictionaryAction.getWord("REMOVEWEAVECM"));
                    removeWeave.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColour()+"/clear.png"));
                    MenuItem editWeave = new MenuItem(objDictionaryAction.getWord("EDITWEAVECM"));
                    editWeave.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColour()+"/weave_editor.png"));
                    
                    weaveIV.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            SeekableStream stream=null;
                            try {
                                weaveIV.setCursor(Cursor.HAND);
                                if(event.isAltDown()){
                                    colorWeave[Integer.parseInt(weaveIV.getUserData().toString())][1]= null;
                                    fontPattern[Integer.parseInt(weaveIV.getId())]= mergeFontWeave(Integer.parseInt(weaveIV.getId()));
                                    reversePattern[Integer.parseInt(weaveIV.getId())]= mergeReverseWeave(Integer.parseInt(weaveIV.getId()));
                                    mergeWeaveImage();         
                                    weaveIV.setImage(new Image("/media/assign_weave.png"));
                                    System.gc();
                                }else if(event.isControlDown()){
                                    Weave objWeave = new Weave();
                                    objWeave.setObjConfiguration(objFabric.getObjConfiguration());
                                    objWeave.setStrWeaveID(colorWeave[Integer.parseInt(weaveIV.getUserData().toString())][1]);  
                                    WeaveEditView objWeaveEditView = new WeaveEditView(objWeave);
                                    if(objWeave.getStrWeaveID()!=null){
                                        stream = new ByteArraySeekableStream(objWeave.getBytWeaveThumbnil());
                                        String[] names = ImageCodec.getDecoderNames(stream);
                                        ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                                        RenderedImage im = dec.decodeAsRenderedImage();
                                        BufferedImage weaveImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
                                        Image pattern=SwingFXUtils.toFXImage(weaveImage, null);
                                        weaveIV.setImage(pattern);
                                        colorWeave[Integer.parseInt(weaveIV.getUserData().toString())][1]= objWeave.getStrWeaveID();
                                        fontPattern[Integer.parseInt(weaveIV.getId())]= mergeFontWeave(Integer.parseInt(weaveIV.getId()));
                                        reversePattern[Integer.parseInt(weaveIV.getId())]= mergeReverseWeave(Integer.parseInt(weaveIV.getId()));
                                        mergeWeaveImage();         
                                        objWeave = null;
                                        names = null;
                                        dec = null;
                                        im = null;
                                        weaveImage = null;
                                        pattern = null;
                                        stream.close();
                                    }else{
                                        lblStatus.setText("Your last action to assign weave pattern was not completed");
                                    }
                                    System.gc();
                                }else{
                                    Weave objWeave= new Weave();
                                    objWeave.setObjConfiguration(objFabric.getObjConfiguration());
                                    WeaveImportView objWeaveImportView= new WeaveImportView(objWeave);
                                    if(objWeave.getStrWeaveID()!=null){
                                        stream = new ByteArraySeekableStream(objWeave.getBytWeaveThumbnil());
                                        String[] names = ImageCodec.getDecoderNames(stream);
                                        ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                                        RenderedImage im = dec.decodeAsRenderedImage();
                                        BufferedImage weaveImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
                                        Image pattern=SwingFXUtils.toFXImage(weaveImage, null);
                                        weaveIV.setImage(pattern);
                                        colorWeave[Integer.parseInt(weaveIV.getUserData().toString())][1]= objWeave.getStrWeaveID();
                                        fontPattern[Integer.parseInt(weaveIV.getId())]= mergeFontWeave(Integer.parseInt(weaveIV.getId()));
                                        reversePattern[Integer.parseInt(weaveIV.getId())]= mergeReverseWeave(Integer.parseInt(weaveIV.getId()));
                                        mergeWeaveImage();         
                                        objWeave = null;
                                        names = null;
                                        dec = null;
                                        im = null;
                                        weaveImage = null;
                                        pattern = null;
                                        stream.close();
                                    }else{
                                        lblStatus.setText("Your last action to assign weave pattern was not completed");
                                    }
                                }                        
                            } catch (IOException ex) {
                                new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                            }
                        }
                    });
                    weaveIV.setOnDragDetected(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event) {
                            /* drag was detected, start a drag-and-drop gesture*/
                            /* allow any transfer mode */
                            Dragboard db = weaveIV.startDragAndDrop(TransferMode.ANY);
                            /* Put a string on a dragboard */
                            ClipboardContent content = new ClipboardContent();
                            //int xindex = Integer.parseInt(colorlink.getUserData().toString());
                            content.putString(weaveIV.getUserData().toString());
                            db.setContent(content);                    
                            event.consume();
                        }
                    });
                    weaveIV.setOnDragOver(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            /* data is dragged over the target */
                            /* accept it only if it is not dragged from the same node 
                             * and if it has a string data */
                            if (event.getGestureSource() != weaveIV && event.getDragboard().hasString()) {
                                /* allow for both copying and moving, whatever user chooses */
                                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                            }
                            event.consume();
                        }
                    });
                    weaveIV.setOnDragEntered(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                        /* the drag-and-drop gesture entered the target */
                        /* show to the user that it is an actual gesture target */
                             if (event.getGestureSource() != weaveIV && event.getDragboard().hasString()) {
                                 //colorLink.setTextFill(Color.GREEN);
                             }
                             event.consume();
                        }
                    });
                    weaveIV.setOnDragExited(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            /* mouse moved away, remove the graphical cues */
                            //colorLink.setTextFill(Color.BLACK);
                            event.consume();
                        }
                    });
                    weaveIV.setOnDragDropped(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            /* data dropped */
                            /* if there is a string data on dragboard, read it and use it */
                            Dragboard db = event.getDragboard();
                            boolean success = false;
                            if (db.hasString()) {
                                Weave objWeave = new Weave();
                                objWeave.setObjConfiguration(objFabric.getObjConfiguration());                                    
                                objWeave.setStrWeaveID(colorWeave[Integer.parseInt(db.getString())][1]);
                                if(objWeave.getStrWeaveID()!=null && !objWeave.getStrWeaveID().equalsIgnoreCase("null")){
                                    try {
                                        WeaveAction objWeaveAction = new WeaveAction();
                                        objWeaveAction.getWeave(objWeave);
                                        
                                        SeekableStream stream=null;
                                        stream = new ByteArraySeekableStream(objWeave.getBytWeaveThumbnil());
                                        String[] names = ImageCodec.getDecoderNames(stream);
                                        ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                                        RenderedImage im = dec.decodeAsRenderedImage();
                                        BufferedImage weaveImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
                                        Image pattern=SwingFXUtils.toFXImage(weaveImage, null);
                                        weaveIV.setImage(pattern);
                                        colorWeave[Integer.parseInt(weaveIV.getUserData().toString())][1]= objWeave.getStrWeaveID();
                                        fontPattern[Integer.parseInt(weaveIV.getId())]= mergeFontWeave(Integer.parseInt(weaveIV.getId()));
                                        reversePattern[Integer.parseInt(weaveIV.getId())]= mergeReverseWeave(Integer.parseInt(weaveIV.getId()));
                                        mergeWeaveImage();         
                                        objWeave = null;
                                        names = null;
                                        dec = null;
                                        im = null;
                                        weaveImage = null;
                                        pattern = null;
                                        stream.close();
                                    } catch (IOException ex) {
                                        new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
                                        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                    } catch (SQLException ex) {
                                        new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
                                        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                    }
                                }else{
                                    lblStatus.setText("Your last action to assign weave pattern was not completed");
                                }
                                success = true;
                            }
                            /* let the source know whether the string was successfully 
                             * transferred and used */
                            event.setDropCompleted(success);
                            event.consume();
                         }
                    });
                    weaveIV.setOnDragDone(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            /* the drag and drop gesture ended */
                            /* if the data was successfully moved, clear it */
                            if (event.getTransferMode() == TransferMode.MOVE) {
                            
                            }
                            event.consume();
                        }
                    });
                    weaveIV.setCursor(Cursor.HAND);
                    weaveGP.add(weaveIV, k, j);
                }else{
                    //colorWeave[i]=null;
                    colorWeave[i][0]=null;
                    colorWeave[i][1]=null;
                    colorWeave[i][2]=null;
                    colorWeave[i][3]=null;
                }
            }
        }
    }
    /**
     * DropDownWeft
     * <p>
     * This method is used for creating UX of extra weft drop down.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating UX of extra weft drop down.
     * @link        com.mla.fabric.Fabric
     * @link        com.mla.main.Logging
     */
    public void DropDownWeft(){
        weftModeCB.getItems().clear();
        for(int i = 0; i<intColor; i++){
            weftModeCB.getItems().add(i+" Extra Weft"); 
        }
        weftModeCB.setValue(intExtraWeft+" Extra Weft");
        weftModeCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(t1!=null){
                    intExtraWeft = Integer.parseInt(t1.substring(0,t1.indexOf("Extra")).trim());
                    weaveGP.getChildren().clear();
                    if(intExtraWeft>0)
                        fabricTypeCB.setDisable(false);
                    else
                        fabricTypeCB.setDisable(true);
                    colorPanel();
                    weavePanel();
                }
            }
        });
    }
    /**
     * mergeFontWeave
     * <p>
     * This method is used for merge font weave.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for merge font weave.
     * @link        com.mla.fabric.Fabric
     * @link        com.mla.main.Logging
     * @param       i Integer block position
     * @return      matrix byte array contains merged values
     */    
    public byte[][] mergeFontWeave(int i){
        int width = objFabric.getIntWarp();
        int height = objFabric.getIntWeft();
        byte[][] matrix = new byte[height][width];
        byte[][] matrix_temp = new byte[height][width];
        for(int init =0; init<height; init++){
            Arrays.fill( matrix[init], (byte) 1 );
            Arrays.fill( matrix_temp[init], (byte) 1 );
        }
        
        /*
        BufferedImage tempImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);        
        Graphics2D g1 = tempImage.createGraphics();
        g1.drawImage(bufferedImage, 0, 0, 111, 111, null);
        g1.dispose();

        for(int x=0; x<111; x++){
            for(int y=0; y<111; y++){
                int pixel = tempImage.getRGB(y, x);     
                int red   = (pixel & 0x00ff0000) >> 16;
                int green = (pixel & 0x0000ff00) >> 8;
                int blue  =  pixel & 0x000000ff; 
                if(!colorWeave[i][0].equalsIgnoreCase(String.format("#%02X%02X%02X",red,green,blue)))
                    tempImage.setRGB(y,x,java.awt.Color.WHITE.getRGB());
            }
        }
        objArtworkAction = new ArtworkAction();
        BufferedImage tempImageBorder=objArtworkAction.getImageColorBorderOwn(tempImage);

                int aw = artwork.getWidth();
                int ah = artwork.getHeight();        
                byte[][] fontMatrix = new byte[ah][aw];
                byte[][] reverseMatrix = new byte[ah][aw];
                byte[][] borderMatrix = new byte[ah][aw];

                for (int i = 0; i < ah; i++){
                    for (int j = 0; j < aw; j++){
                
                    }
                }        
        */
        
        for(int j = 0; j<colorWeave.length; j++){
            if((j%intColor)==i && colorWeave[j][1]!=null){
				if(colorWeave[j][1].equalsIgnoreCase("null"))
                    continue;
                try {
                    Weave objWeave = new Weave();
                    objWeave.setObjConfiguration(objFabric.getObjConfiguration());
                    objWeave.setStrWeaveID(colorWeave[j][1]);
                    WeaveAction objWeaveAction = new WeaveAction();
                    objWeaveAction.getWeave(objWeave);
                    objWeaveAction.extractWeaveContent(objWeave);
                    objArtworkAction= new ArtworkAction();
                    byte[][] weaveMatrix = objArtworkAction.repeatMatrix(objWeave.getDesignMatrix(),height,width);
                    for(int x=0; x<height; x++){
                        for(int y=0; y<width; y++){
                            if(weaveMatrix[x][y] == 0)
                                matrix[x][y]=(byte)(((j/intColor)==0)?0:(j/intColor)+1);
                        }
                    }
                    for(int x=0,z=(j/intColor); z<height; x++,z+=intExtraWeft+1){
                        for(int y=0; y<width; y++){
                            if(weaveMatrix[x][y] == 0)
                                matrix_temp[z][y]=(byte)(((j/intColor)==0)?0:(j/intColor)+1);
                        }
                    }
                    weaveMatrix = null;
                    BufferedImage tempImage = new BufferedImage(111, 111,BufferedImage.TYPE_INT_RGB); 
                    objArtworkAction= new ArtworkAction();
                    byte[][] imageMatrix = objArtworkAction.repeatMatrix(matrix_temp,111,111);
                    int rgb = 0;
                    for(int x = 0; x < 111; x++) {
                        for(int y = 0; y < 111; y++) {
                            if(imageMatrix[x][y]==1)
                                rgb = java.awt.Color.BLACK.getRGB(); 
                            else
                                rgb = java.awt.Color.WHITE.getRGB(); 
                            tempImage.setRGB(y, x, rgb);
                        }
                    }
                    imageMatrix = null;
                    ImageView imageCombine = new ImageView(SwingFXUtils.toFXImage(tempImage, null));          
                    weaveGP.add(imageCombine, i, intExtraWeft+1);
                } catch (SQLException ex) {
                    new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (Exception ex) {
                    new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        }
        return matrix;
    }
    /**
     * mergeReverseWeave
     * <p>
     * This method is used for merge reverse weave.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for merge reverse weave.
     * @link        com.mla.fabric.Fabric
     * @link        com.mla.main.Logging
     * @param       i Integer block position
     * @return      matrix byte array contains merged values
     */
    public byte[][] mergeReverseWeave(int i){
        int width = objFabric.getIntWarp();
        int height = objFabric.getIntWeft();
        byte[][] matrix = new byte[height][width];
        byte[][] matrix_temp = new byte[height][width];
        for(int init =0; init<height; init++){
            Arrays.fill( matrix[init], (byte) 1 );
            // Arrays.fill( matrix_temp[init], (byte) 1 );
        }
        for(int j = 0; j<colorWeave.length; j++){
            if((j%intColor)==i && colorWeave[j][1]!=null){
				if(colorWeave[j][1].equalsIgnoreCase("null"))
                    continue;
                try {
                    Weave objWeave = new Weave();
                    objWeave.setObjConfiguration(objFabric.getObjConfiguration());
                    objWeave.setStrWeaveID(colorWeave[j][1]);
                    WeaveAction objWeaveAction = new WeaveAction();
                    objWeaveAction.getWeave(objWeave);
                    objWeaveAction.extractWeaveContent(objWeave);
                    objArtworkAction= new ArtworkAction();
                    byte[][] weaveMatrix = objArtworkAction.repeatMatrix(objWeave.getDesignMatrix(),height,width);
                    for(int x=0; x<height; x++){
                        for(int y=0; y<width; y++){
                            if(weaveMatrix[x][y] == 1)
                                matrix[x][y]=(byte)(((j/intColor)==0)?0:(j/intColor)+1);
                        }
                    }
                    for(int x=0,z=(j/intColor); z<height; x++,z+=intExtraWeft+1){
                        Arrays.fill( matrix_temp[z], (byte) (((j/intColor)==0)?0:(j/intColor)+1) );
                        for(int y=0; y<width; y++){
                            if(weaveMatrix[x][y] == 0)
                                matrix_temp[z][y]=(byte)1;
                        }
                    }
                    weaveMatrix = null;
                    BufferedImage tempImage = new BufferedImage(111, 111,BufferedImage.TYPE_INT_RGB); 
                    objArtworkAction= new ArtworkAction();
                    byte[][] imageMatrix = objArtworkAction.repeatMatrix(matrix_temp,111,111);
                    int rgb = 0;
                    for(int x = 0; x < 111; x++) {
                        for(int y = 0; y < 111; y++) {
                            if(imageMatrix[x][y]==1)
                                rgb = java.awt.Color.BLACK.getRGB(); 
                            else
                                rgb = java.awt.Color.WHITE.getRGB(); 
                            tempImage.setRGB(y, x, rgb);
                        }
                    }
                    imageMatrix = null;
                    ImageView imageCombine = new ImageView(SwingFXUtils.toFXImage(tempImage, null));
                    weaveGP.add(imageCombine, i, intExtraWeft+2);
                } catch (SQLException ex) {
                    new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (Exception ex) {
                    new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        }
        return matrix;
    }
    
    /**
     * mergeWeaveImage
     * <p>
     * This method is used for merge reverse weave.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for merge reverse weave.
     * @link        com.mla.fabric.Fabric
     * @link        com.mla.main.Logging
     * @param       i Integer block position
     * @return      matrix byte array contains merged values
     */
    public void mergeWeaveImage(){
        try{
            int width = objFabric.getIntWarp();
            int height = objFabric.getIntWeft();
            if(artworkSizeCB.isSelected()){
                width = bufferedImage.getWidth();
                height = bufferedImage.getHeight();
            }

            BufferedImage artwork = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);        
            Graphics2D g = artwork.createGraphics();
            g.drawImage(bufferedImage, 0, 0, width, height, null);
            g.dispose();

            objArtworkAction = new ArtworkAction();
            BufferedImage artworkBorder=objArtworkAction.getImageColorBorder(artwork);

            byte[][] fontMatrix = new byte[height][width];
            byte[][] borderMatrix = new byte[height][width];

            for (int i = 0; i < height; i++){
                for (int j = 0; j < width; j++){
                    int pixel = artwork.getRGB(j, i);
                    int red   = (pixel & 0x00ff0000) >> 16;
                    int green = (pixel & 0x0000ff00) >> 8;
                    int blue  =  pixel & 0x000000ff;                    
                    java.awt.Color color = new java.awt.Color(red,green,blue); 

                    int pixelB = artworkBorder.getRGB(j, i);
                    int redB   = (pixelB & 0x00ff0000) >> 16;
                    int greenB = (pixelB & 0x0000ff00) >> 8;
                    int blueB  =  pixelB & 0x000000ff;                    
                    java.awt.Color colorB = new java.awt.Color(redB,greenB,blueB); 

                    for(int k=0; k<colors.size(); k++){
                        if(colors.get(k).equals(color)){
                            fontMatrix[i][j] = (byte)(k+10);
                        }
                        if(colors.get(k).equals(colorB)){
                            if(k>0)
                                borderMatrix[i][j] = (byte)(k+1);
                        }
                    }
                }
            }

            // Font Side Matrix Preparation
            for(int c=0; c<fontPattern.length; c++){
                byte[][] patternMatrix =  fontPattern[c]; 
                if(patternMatrix == null)
                    patternMatrix = new byte[height][width];
                int ph=patternMatrix.length;
                int pw=patternMatrix[0].length;
                byte[][] repeatMatrix = new byte[height][width];           
                for(int i=0;i<height;i++) {
                    for(int j=0;j<width;j++) {
                        if(i>=ph && j<pw){
                            repeatMatrix[i][j] = patternMatrix[i%ph][j];  
                        }else if(i<ph && j>=pw){
                            repeatMatrix[i][j] = patternMatrix[i][j%pw];  
                        }else if(i>=ph && j>=pw){
                            repeatMatrix[i][j] = patternMatrix[i%ph][j%pw];  
                        }else{
                            repeatMatrix[i][j] = patternMatrix[i][j]; 
                        }
                    }
                }
                for (int i = 0; i < height; i++){
                    for (int j = 0; j < width; j++){
                        if(fontMatrix[i][j]==(c+10))
                            fontMatrix[i][j] = repeatMatrix[i][j];
                        if(borderMatrix[i][j]>1 && artworkOutlineCB.isSelected())
                            fontMatrix[i][j] = borderMatrix[i][j];
                    }
                }
                repeatMatrix = null;
                patternMatrix = null;
            }
            
            //create image from integer matrix and color list
            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {                       
                    if(fontMatrix[y][x] > 0 && fontMatrix[y][x] <= colors.size()){
                        artwork.setRGB(x, y, colors.get(fontMatrix[y][x]-1).getRGB());
                    }
                }
            }
            Image image = SwingFXUtils.toFXImage(artwork, null);
            previewSP.setContent(new ImageView(image));
            
            artwork = null;
            fontMatrix = null;
            System.gc();
        } catch (SQLException ex) {
            new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkEditView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }

}