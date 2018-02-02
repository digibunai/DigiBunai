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

import com.mla.artwork.Artwork;
import com.mla.artwork.ArtworkAction;
import com.mla.artwork.ArtworkEditView;
import com.mla.artwork.ArtworkView;
import com.mla.colour.ColorPaletteEditView;
import com.mla.colour.ColorPaletteView;
import com.mla.dictionary.DictionaryAction;
import com.mla.main.AboutView;
import com.mla.main.Configuration;
import com.mla.main.ContactView;
import com.mla.main.EncryptZip;
import com.mla.main.HelpView;
import com.mla.main.IDGenerator;
import com.mla.main.Logging;
import com.mla.main.MessageView;
import com.mla.main.TechnicalView;
import com.mla.main.UndoRedo;
import com.mla.main.WindowView;
import com.mla.secure.Security;
import com.mla.user.UserAction;
import com.mla.cloth.ClothView;
import com.mla.utility.Device;
import com.mla.utility.Simulator;
import com.mla.utility.SimulatorImportView;
import com.mla.utility.SimulatorEditView;
import com.mla.utility.UtilityAction;
import com.mla.weave.WeaveImportView;
import com.mla.weave.Weave;
import com.mla.weave.WeaveAction;
import com.mla.weave.WeaveView;
import com.mla.yarn.Yarn;
import com.mla.yarn.YarnAction;
import com.mla.yarn.YarnView;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import javax.media.jai.PlanarImage;
import com.sun.media.jai.codec.ByteArraySeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
/**
 *
 * @Designing GUI window for fabric editor
 * @author Amit Kumar Singh
 * 
 */
public class FabricView extends Application {
  
    PrinterJob objPrinterJob = PrinterJob.getPrinterJob();
    javax.print.attribute.HashPrintRequestAttributeSet objPrinterAttribute = new javax.print.attribute.HashPrintRequestAttributeSet();
    Process objProcess_ProcessBuilder;
                        
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    Fabric objFabric;
    Weave objWeave;
    Weave objWeaveR;
    UndoRedo objUR;
    
    FabricAction objFabricAction;
    ArtworkAction objArtworkAction;
    WeaveAction objWeaveAction;
    public static Stage fabricStage;

    boolean isVisibleLeftPane = true;
    
    Yarn[] warpYarn;
    Yarn[] weftYarn;
    Yarn[] warpExtraYarn;
    Yarn[] weftExtraYarn;
    ArrayList<Yarn> lstYarn = null;
    Map<String, String> warpColorPalete = new HashMap<>();
    Map<String, String> weftColorPalete = new HashMap<>();
    String[] threadPaletes;
    float zoomfactor = 1;
    byte plotViewActionMode = 1; //1= composite, 2 = grid, 3 = graph, 4 = graph m/c, 5 = visulization, 6 = flip, 7 = switch, 8 = simulation 2D, 9 = simulation 3D
    byte graphFactor = 2;
    int editThreadValue=1;
    boolean locked = true;
    boolean isNew = false;
    boolean isWorkingMode = false;
    boolean isEditingMode = false;
    boolean isEditWeave = false;
    boolean isEditGraph = false;
    PerspectiveCamera camera;
    double cameraDistance = 450;
    
    private ToolBar toolBar;
    private MenuBar menuBar;    
    private BorderPane root;
    private Scene scene;
    ScrollPane container;
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;
    
    ImageView fabric;
    GridPane containerGP;
    GridPane editToolPane;
    private Label hRuler;
    private Label vRuler;
    private Menu homeMenu;
    private Menu fileMenu;
    private Menu editMenu;
    private Menu viewMenu;
    private Menu utilityMenu;
    private Menu helpMenu;
    private Label fileMenuLabel;
    private Label editMenuLabel;
    private Label viewMenuLabel;
    private Label utilityMenuLabel;
        
    private VBox saveFileVB;    
    private VBox saveAsFileVB;
    private VBox patternLockedEditVB;
    private VBox patternUnlockEditVB;
    
    private ContextMenu contextMenu;
    
 /**
 * FabricView(Stage)
 * <p>
 * This constructor is used for individual call of class. 
 * 
 * @param       Stage primaryStage
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         javafx.stage.*;
 * @link        WindowView
 */
    public FabricView(final Stage primaryStage) {}
 /**
 * FabricView(Configuration)
 * <p>
 * This class is used for prompting fabric editor. 
 * 
 * @param       Configuration objConfigurationCall
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         javafx.event.*;
 * @link        WindowView
 */
    public FabricView(Configuration objConfigurationCall) {
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);
        objUR = new UndoRedo();
        //System.err.println("10: "+objConfiguration.mapRecentFabric.get(objConfiguration.getStrClothType()));
        
        objFabric= new Fabric();
        objFabric.setObjConfiguration(objConfiguration);
        
        fabricStage = new Stage();
        root = new BorderPane();
        scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(FabricView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
    
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
        
        VBox topContainer = new VBox();
        toolBar = new ToolBar();
        toolBar.setMinHeight(95);
        menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(fabricStage.widthProperty());
        topContainer.getChildren().add(menuBar);
        topContainer.getChildren().add(toolBar); 
        topContainer.setId("topContainer");
        root.setTop(topContainer);

        homeMenu  = new Menu();
        final HBox homeMenuHB = new HBox();
        homeMenuHB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/home.png"),new Label(objDictionaryAction.getWord("HOME")));
        homeMenu.setGraphic(homeMenuHB);
        homeMenuHB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                homeMenuAction();  
                me.consume();
            }
        });       
        // File menu - new, save, save as, open, load recent.
        fileMenuLabel = new Label(objDictionaryAction.getWord("FILE"));
        fileMenu = new Menu();
        fileMenu.setGraphic(fileMenuLabel);
        fileMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                fileMenuAction();            
            }
        });
        // Edit menu - Toolbar, Color, Composite View, Support Lines, Ruler, Zoom-in, zoom-out
        editMenuLabel = new Label(objDictionaryAction.getWord("EDIT"));
        editMenu = new Menu();
        editMenu.setGraphic(editMenuLabel);
        editMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                editMenuAction();
            }
        });  
        // View menu - Toolbar, Color, Composite View, Support Lines, Ruler, Zoom-in, zoom-out
        viewMenuLabel = new Label(objDictionaryAction.getWord("VIEW"));
        viewMenu = new Menu();
        viewMenu.setGraphic(viewMenuLabel);
        viewMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                viewMenuAction();
            }
        });  
        // Utility menu - Weave, Calculation, Language
        utilityMenuLabel = new Label(objDictionaryAction.getWord("UTILITY"));
        utilityMenu = new Menu();
        utilityMenu.setGraphic(utilityMenuLabel);
        utilityMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                utilityMenuAction();
            }
        });
        Label helpMenuLabel = new Label(objDictionaryAction.getWord("SUPPORT"));
        helpMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSUPPORT")));
        Menu helpMenu = new Menu();
        helpMenu.setGraphic(helpMenuLabel);
        MenuItem helpMenuItem = new MenuItem(objDictionaryAction.getWord("HELP"));
        helpMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        helpMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {       
                helpMenuAction();
            }
        });        
        MenuItem technicalMenuItem = new MenuItem(objDictionaryAction.getWord("TECHNICAL"));
        technicalMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/technical_info.png"));
        technicalMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {       
                technicalMenuAction();
            }
        });
        MenuItem aboutMenuItem = new MenuItem(objDictionaryAction.getWord("ABOUTUS"));
        aboutMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/about_software.png"));
        aboutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                aboutMenuAction();
            }
        });
        MenuItem contactMenuItem = new MenuItem(objDictionaryAction.getWord("CONTACTUS"));
        contactMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/contact_us.png"));
        contactMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                contactMenuAction();
            }
        });
        MenuItem exitMenuItem = new MenuItem(objDictionaryAction.getWord("EXIT"));
        exitMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/quit.png"));
        exitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                exitMenuAction();
            }
        });        
        helpMenu.getItems().addAll(helpMenuItem, technicalMenuItem, aboutMenuItem, contactMenuItem, new SeparatorMenuItem(), exitMenuItem);
        menuBar.getMenus().addAll(homeMenu,fileMenu, editMenu, viewMenu, utilityMenu, helpMenu);
        
        contextMenu = new ContextMenu();
        MenuItem editArtwork = new MenuItem(objDictionaryAction.getWord("ARTWORKASSINGMENTCM"));
        editArtwork.setGraphic(new ImageView(objConfiguration.getStrColour()+"/artwork_editor.png"));
        MenuItem editWeave = new MenuItem(objDictionaryAction.getWord("WEAVINGPATTERNCM"));
        editWeave.setGraphic(new ImageView(objConfiguration.getStrColour()+"/weave_editor.png"));
        MenuItem editGraph = new MenuItem(objDictionaryAction.getWord("GRAPHPATTERNCM"));
        editGraph.setGraphic(new ImageView(objConfiguration.getStrColour()+"/graph_editor.png"));
        MenuItem editPattern = new MenuItem(objDictionaryAction.getWord("THREADPATTERNCM"));
        editPattern.setGraphic(new ImageView(objConfiguration.getStrColour()+"/thread_pattern.png"));        
        MenuItem editYarn = new MenuItem(objDictionaryAction.getWord("YARNCM"));
        editYarn.setGraphic(new ImageView(objConfiguration.getStrColour()+"/yarn.png"));
        MenuItem switchColor = new MenuItem(objDictionaryAction.getWord("SWITCHCOLORCM"));
        switchColor.setGraphic(new ImageView(objConfiguration.getStrColour()+"/switch_color.png"));
        MenuItem density = new MenuItem(objDictionaryAction.getWord("DENSITYEDIT"));
        density.setGraphic(new ImageView(objConfiguration.getStrColour()+"/density.png"));
        contextMenu.getItems().addAll(editArtwork, editWeave, editGraph, editPattern, editYarn, switchColor, density);
        editArtwork.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONARTWORKASSINGMENTEDIT"));
                editArtwork();
            }
        });
        editWeave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONWEAVINGPATTERNEDIT"));
                editWeave();
            }
        });
        editGraph.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONGRAPHPATTERNEDIT"));
                editGraph();
            }
        });
        editPattern.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONTHREADPATTERNEDIT"));
                editPattern();
            }
        });
        editYarn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!locked){
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONYARNEDIT"));
                    editYarn();
                }else{
                    MessageView objMessageView = new MessageView("alert","Editing Locked !","Hello! We are very sorry,\n This feature is disable becuse you have pattern lock enabled.");
                }
            }
        });
        switchColor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONSWITCHCOLOREDIT"));
                switchColor();
            }
        });
        density.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONDENSITYEDIT"));
                editDensity();
            }
        });
        container = new ScrollPane();
        container.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        container.setContextMenu(contextMenu);
        
        fabric = new ImageView(); 
        fabric.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getEventType() == MouseEvent.MOUSE_CLICKED && isEditWeave){
                    /*if(objWeave.getDesignMatrix()[(int)(event.getY()/objConfiguration.getIntBoxSize())][(int)(event.getX()/objConfiguration.getIntBoxSize())]==0)
                        objWeave.getDesignMatrix()[(int)(event.getY()/objConfiguration.getIntBoxSize())][(int)(event.getX()/objConfiguration.getIntBoxSize())] = 1;
                    else if(objWeave.getDesignMatrix()[(int)(event.getY()/objConfiguration.getIntBoxSize())][(int)(event.getX()/objConfiguration.getIntBoxSize())]==1)
                        objWeave.getDesignMatrix()[(int)(event.getY()/objConfiguration.getIntBoxSize())][(int)(event.getX()/objConfiguration.getIntBoxSize())] = 0;

                    if(objWeave.getDesignMatrix()[(int)(event.getY()/objConfiguration.getIntBoxSize())][(int)(event.getX()/objConfiguration.getIntBoxSize())]>1)
                        objWeave.getDesignMatrix()[(int)(event.getY()/objConfiguration.getIntBoxSize())][(int)(event.getX()/objConfiguration.getIntBoxSize())] = 1;
                    */
                    objWeave.getDesignMatrix()[(int)(event.getY()/(int)(objFabric.getObjConfiguration().getIntBoxSize()*zoomfactor))][(int)(event.getX()/(int)(objFabric.getObjConfiguration().getIntBoxSize()*zoomfactor))] = (byte)editThreadValue;
                    objWeaveR.getDesignMatrix()[(int)(event.getY()/(int)(objFabric.getObjConfiguration().getIntBoxSize()*zoomfactor))][(int)(event.getX()/(int)(objFabric.getObjConfiguration().getIntBoxSize()*zoomfactor))] = (editThreadValue==1)?(byte)0:(byte)1;
                    plotEditWeave();
                    /*
                    System.out.println("In clicked event"+ "," + objConfiguration.getIntBoxSize() + "," +zoomfactor);
                    System.out.println(event.getX() + "," + event.getY());
                    System.out.println(event.getSceneX() + "," + event.getSceneY());
                    System.out.println(event.getScreenX() + "," + event.getScreenY());
                    System.out.println(container.getBoundsInLocal());
                    System.out.println(fabric.getBoundsInParent());
                    System.out.println(fabric.getBoundsInLocal());
                    */
                }else if(event.getEventType() == MouseEvent.MOUSE_CLICKED && isEditGraph){
                    String[] data = objFabric.getObjConfiguration().getStrGraphSize().split("x");
                    objFabric.getFabricMatrix()[(int)(event.getY()/(Integer.parseInt(data[0])*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()/graphFactor))][(int)(event.getX()/(Integer.parseInt(data[1])*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()/graphFactor))] = (byte)editThreadValue;
                    objFabric.getReverseMatrix()[(int)(event.getY()/(Integer.parseInt(data[0])*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()/graphFactor))][(int)(event.getX()/(Integer.parseInt(data[1])*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()/graphFactor))] = (editThreadValue==1)?(byte)0:(byte)1;
                    plotEditGraph();
                }
            }
        });
        container.setContent(fabric);
                
        editToolPane = new GridPane();
        editToolPane.autosize();
        editToolPane.setId("leftPane");
        
        HBox bodyContainer = new HBox();
        bodyContainer.getChildren().add(editToolPane);
        bodyContainer.getChildren().add(container);
        root.setCenter(bodyContainer);
        
        containerGP = new GridPane();
        containerGP.setMinSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        containerGP.setVgap(0);
        containerGP.setHgap(0);
        
        camera = new PerspectiveCamera();
        cameraDistance = 450;
        /*camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-cameraDistance);
        */
        scene.setCamera(camera);
        
        menuHighlight(null);
        
        fabricStage.getIcons().add(new Image("/media/icon.png"));
        fabricStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWFABRICEDITOR")+"( "+objConfiguration.getStrClothType()+" ) \u00A9 "+objDictionaryAction.getWord("TITLE"));
        fabricStage.setX(-5);
        fabricStage.setY(0);
        //fabricStage.setFocused(true);
        //fabricStage.initModality(Modality.WINDOW_MODAL);
        //fabricStage.initOwner(fabricStage);
        //fabricStage.setIconified(true);
        //fabricStage.setFullScreen(true);
        fabricStage.setResizable(false);
        fabricStage.setScene(scene);
        fabricStage.show();
        fabricStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
                scene.getStylesheets().add(FabricView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        objConfiguration.mapRecentFabric.put(objConfiguration.getStrClothType(),objFabric.getStrFabricID());
                        dialogStage.close();
                        fabricStage.close();
                        System.gc();
                        WindowView objWindowView = new WindowView(objConfiguration);
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
        if(objConfiguration.getStrRecentArtwork()!=null && objConfiguration.strWindowFlowContext.equalsIgnoreCase("ArtworkEditor")){
            objFabric.setStrBaseWeaveID(objConfiguration.getStrDefaultWeave());
            newFabric();
        } else if(objConfiguration.getStrRecentWeave()!=null && objConfiguration.strWindowFlowContext.equalsIgnoreCase("WeaveEditor")){
            objFabric.setStrBaseWeaveID(objConfiguration.getStrRecentWeave());
            newFabric();
        } else if(objConfiguration.mapRecentFabric.get(objConfiguration.getStrClothType())!=null && (objConfiguration.strWindowFlowContext.equalsIgnoreCase("FabricEditor") || objConfiguration.strWindowFlowContext.equalsIgnoreCase("ClothEditor"))){
            objFabric.setStrFabricID(objConfiguration.mapRecentFabric.get(objConfiguration.getStrClothType()));
            try {
                FabricAction objFabricAction = new FabricAction();        
                objFabricAction.getFabric(objFabric);
                objFabric.setObjConfiguration(objConfiguration);
				/*if(objFabric.getStrFabricName()==null){
                    TmpFabricAction objTmpFabricAction = new TmpFabricAction();        
                    objTmpFabricAction.getTmpFabric(objFabric);
                    loadTmpFabric();
                }else*/
                loadFabric();
            } catch (SQLException ex) {
                new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            } catch (Exception ex) {
                new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        } else if(objConfiguration.getStrRecentArtwork()!=null && objConfiguration.getStrRecentArtwork()!=""){
            final Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            dialogStage.setIconified(false);
            dialogStage.setFullScreen(false);
            dialogStage.setTitle(objDictionaryAction.getWord("ALERT"));
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 300, 100, Color.WHITE);
            final GridPane popup=new GridPane();
            popup.setHgap(5);
            popup.setVgap(5);
            popup.setPadding(new Insets(25, 25, 25, 25));
            popup.add(new ImageView(objConfiguration.getStrColour()+"/stop.png"), 0, 0);
            Label lblAlert = new Label(objDictionaryAction.getWord("ALERTWHAT"));
            lblAlert.setStyle("-fx-wrap-text:true;");
            lblAlert.setPrefWidth(250);
            popup.add(lblAlert, 1, 0);
            Button btnYes = new Button(objDictionaryAction.getWord("RECENT")+" "+objDictionaryAction.getWord("ARTWORK"));
            btnYes.setPrefWidth(150);
            btnYes.setStyle("-fx-text-fill: #ffffff; -fx-padding: 1; -fx-font-size: 13px; -fx-font-weight: bold; -fx-background-color: linear-gradient(#00ff00, #009900); -fx-effect: dropshadow( three-pass-box , rgba(0,1,1,0.6) , 5, 0.0 , 0 , 1 );");
            btnYes.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    dialogStage.close();
                    System.gc();
                    objFabric.setStrBaseWeaveID(objConfiguration.getStrDefaultWeave());
                    newFabric();
                }
            });
            Button btnNo = new Button(objDictionaryAction.getWord("RECENT")+" "+objDictionaryAction.getWord("FABRIC"));
            btnNo.setPrefWidth(150);
            btnNo.setStyle("-fx-text-fill: #ffffff; -fx-padding: 1; -fx-font-size: 13px; -fx-font-weight: bold; -fx-background-color: linear-gradient(#0000ff, #0000ff); -fx-effect: dropshadow( three-pass-box , rgba(0,1,1,0.6) , 5, 0.0 , 0 , 1 );");
            btnNo.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    if(objConfiguration.mapRecentFabric.get(objConfiguration.getStrClothType())!=null && objConfiguration.mapRecentFabric.get(objConfiguration.getStrClothType())!="")
                        objFabric.setStrFabricID(objConfiguration.mapRecentFabric.get(objConfiguration.getStrClothType()));
                    else
                        objFabric.setStrFabricID(objConfiguration.getStrDefaultFabric());
                    try {
                        FabricAction objFabricAction = new FabricAction();        
                        objFabricAction.getFabric(objFabric);
                        objFabric.setObjConfiguration(objConfiguration);
                    } catch (SQLException ex) {
                        new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    } catch (Exception ex) {
                        new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    }     
                    loadFabric();
                    dialogStage.close();
                    System.gc();
                }
            });                
            HBox hbbtn =new HBox();
            hbbtn.getChildren().addAll(btnYes,btnNo);
            popup.add(hbbtn, 0, 1, 2, 1);
            root.setCenter(popup);
            dialogStage.setScene(scene);
            dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    if(objConfiguration.mapRecentFabric.get(objConfiguration.getStrClothType())!=null && objConfiguration.mapRecentFabric.get(objConfiguration.getStrClothType())!="")
                        objFabric.setStrFabricID(objConfiguration.mapRecentFabric.get(objConfiguration.getStrClothType()));
                    else
                        objFabric.setStrFabricID(objConfiguration.getStrDefaultFabric());
                    try {
                        FabricAction objFabricAction = new FabricAction();        
                        objFabricAction.getFabric(objFabric);
                        objFabric.setObjConfiguration(objConfiguration);
                    } catch (SQLException ex) {
                        new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    } catch (Exception ex) {
                        new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    }     
                    loadFabric();
                    dialogStage.close();
                    System.gc();
                }
            });
            dialogStage.showAndWait();
        } else{       
            objFabric.setStrFabricID(objConfiguration.mapRecentFabric.get(objConfiguration.getStrClothType()));
            if(objFabric.getStrFabricID()==null || objFabric.getStrFabricID()=="")
                objFabric.setStrFabricID(objConfiguration.getStrDefaultFabric());
            try {
                FabricAction objFabricAction = new FabricAction();        
                objFabricAction.getFabric(objFabric);
            } catch (SQLException ex) {
                new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            } catch (Exception ex) {
                new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }   
            loadFabric();
        }
        populateLeftPane();
        //objConfiguration.strWindowFlowContext = "ClothEditor";
		final KeyCodeCombination kchA = new KeyCodeCombination(KeyCode.H, KeyCombination.ALT_DOWN); // Home Menu
        final KeyCodeCombination kcfA = new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN); // File Menu
        final KeyCodeCombination kceA = new KeyCodeCombination(KeyCode.E, KeyCombination.ALT_DOWN); // Edit Menu
        final KeyCodeCombination kcvA = new KeyCodeCombination(KeyCode.V, KeyCombination.ALT_DOWN); // View Menu
        final KeyCodeCombination kcuA = new KeyCodeCombination(KeyCode.U, KeyCombination.ALT_DOWN); // Utility Menu
        final KeyCodeCombination kcsA = new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN); // Support Menu
        final KeyCodeCombination kcnC = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN); // Create Fabric
        final KeyCodeCombination kcoC = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN); // Open Fabric
        final KeyCodeCombination kclS = new KeyCodeCombination(KeyCode.L, KeyCombination.SHIFT_DOWN); // Load Recent Fabric
        final KeyCodeCombination kcsC = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN); // Save Fabric
        final KeyCodeCombination kcsCS = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN); // Save As Fabric
        final KeyCodeCombination kchCS = new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN); // Export as Html
        final KeyCodeCombination kctCS = new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN); // Export as Texture
        final KeyCodeCombination kcgCS = new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN); // Export as Graph
        final KeyCodeCombination kcsAC = new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN); // Print Setup
        final KeyCodeCombination kcpA = new KeyCodeCombination(KeyCode.P, KeyCombination.ALT_DOWN); // Print Screen
        final KeyCodeCombination kcgAC = new KeyCodeCombination(KeyCode.G, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN); // Print Grid
        final KeyCodeCombination kcpAC = new KeyCodeCombination(KeyCode.P, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN); // Print Graph
        final KeyCodeCombination kctA = new KeyCodeCombination(KeyCode.T, KeyCombination.ALT_DOWN); // Print Texture
        final KeyCodeCombination kcrC = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN); // Punch Application
        final KeyCodeCombination kczC = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN); // Undo
        final KeyCodeCombination kcyC = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN); // Redo
        final KeyCodeCombination kcaC = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN); // Artwork Assignment
        final KeyCodeCombination kcwC = new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN); // Weaving Pattern
        final KeyCodeCombination kcgC = new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN); // Graph Design
        final KeyCodeCombination kcyCS = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN); // Yarn Properties
        final KeyCodeCombination kcdC = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN); // Density
        final KeyCodeCombination kctC = new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN); // Thread Sequence
        final KeyCodeCombination kclC = new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN); // Switch Color
        final KeyCodeCombination kcrAC = new KeyCodeCombination(KeyCode.R, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN); // Pattern Lock
        final KeyCodeCombination kcrA = new KeyCodeCombination(KeyCode.R, KeyCombination.ALT_DOWN); // Pattern Unlock
        final KeyCodeCombination kcenterCS = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN); // Composite View
        final KeyCodeCombination kcenterAC = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN); // Grid View
        final KeyCodeCombination kcenterS = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.SHIFT_DOWN); // Graph View
        final KeyCodeCombination kcupCS = new KeyCodeCombination(KeyCode.UP, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN); // Front Side View
        final KeyCodeCombination kcrightCS = new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN); // Flip Side View
        final KeyCodeCombination kcleftCS = new KeyCodeCombination(KeyCode.LEFT, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN); // Back Side View
        final KeyCodeCombination kcrightA = new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.ALT_DOWN); // Front Cut
        final KeyCodeCombination kcleftA = new KeyCodeCombination(KeyCode.LEFT, KeyCombination.ALT_DOWN); // Rear Cut
        final KeyCodeCombination kcmC = new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_DOWN); // Simulation
        final KeyCodeCombination kcplusC = new KeyCodeCombination(KeyCode.EQUALS, KeyCombination.CONTROL_DOWN); // Zoom In
        final KeyCodeCombination kcenterA = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.ALT_DOWN); // Normal Zoom
        final KeyCodeCombination kcminusC = new KeyCodeCombination(KeyCode.MINUS, KeyCombination.CONTROL_DOWN); // Zoom Out
        final KeyCodeCombination kcfAC = new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN); // Fabric Settings
        final KeyCodeCombination kckA = new KeyCodeCombination(KeyCode.K, KeyCombination.ALT_DOWN); // Consumption Calculator
        final KeyCodeCombination kckS = new KeyCodeCombination(KeyCode.K, KeyCombination.SHIFT_DOWN); // Price Calculator
        final KeyCodeCombination kchC = new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN); // Help
        final KeyCodeCombination kciA = new KeyCodeCombination(KeyCode.I, KeyCombination.ALT_DOWN); // Technical Information
        final KeyCodeCombination kcenterC = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN); // About Software
        final KeyCodeCombination kcuC = new KeyCodeCombination(KeyCode.U, KeyCombination.CONTROL_DOWN); // Contact Us
        final KeyCodeCombination kcxA = new KeyCodeCombination(KeyCode.X, KeyCombination.ALT_DOWN); // Exit
        
        scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if(kchA.match(t)){
                    homeMenuAction();
                }
                else if(kcfA.match(t)){
                    fileMenuAction();
                }
                else if(kceA.match(t)){
                    editMenuAction();
                }
                else if(kcvA.match(t)){
                    viewMenuAction();
                }
                else if(kcuA.match(t)){
                    utilityMenuAction();
                }
                else if(kcsA.match(t)){
                    
                }
                else if(kcnC.match(t)){
                    createMenuAction();
                }
                else if(kcoC.match(t)){
                    openMenuAction();
                }
                else if(kclS.match(t)){
                    loadRecentMenuAction();
                }
                else if(kcsC.match(t)){
                    saveMenuAction();
                }
                else if(kcsCS.match(t)){
                    saveAsMenuAction();
                }
                else if(kchCS.match(t)){
                    exportHtmlAction();
                }
                else if(kctCS.match(t)){
                    exportTextureAction();
                }
                else if(kcgCS.match(t)){
                    exportGraphAction();
                }
                else if(kcsAC.match(t)){
                    printSetupAction();
                }
                else if(kcpA.match(t)){
                    printScreenAction();
                }
                else if(kcgAC.match(t)){
                    printGridAction();
                }
                else if(kcpAC.match(t)){
                    printGraphAction();
                }
                else if(kctA.match(t)){
                    printTextureAction();
                }
                else if(kcrC.match(t)){
                    punchApplicationAction();
                }
                else if(kczC.match(t)){
                    undoAction();
                }
                else if(kcyC.match(t)){
                    redoAction();
                }
                else if(kcaC.match(t)){
                    artworkAssignmentAction();
                }
                else if(kcwC.match(t)){
                    weavingPatternAction();
                }
                else if(kcgC.match(t)){
                    graphDesignAction();
                }
                else if(kcyCS.match(t)){
                    yarnPropertiesAction();
                }
                else if(kcdC.match(t)){
                    densityAction();
                }
                else if(kctC.match(t)){
                    threadSequenceAction();
                }
                else if(kclC.match(t)){
                    switchColorAction();
                }
                else if(kcrAC.match(t)){
                    patternLockAction();
                }
                else if(kcrA.match(t)){
                    patternUnlockAction();
                }
                else if(kcenterCS.match(t)){
                    compositeViewAction();
                }
                else if(kcenterAC.match(t)){
                    gridViewAction();
                }
                else if(kcenterS.match(t)){
                    graphViewAction();
                }
                else if(kcupCS.match(t)){
                    frontSideViewAction();
                }
                else if(kcrightCS.match(t)){
                    flipSideViewAction();
                }
                else if(kcleftCS.match(t)){
                    backSideViewAction();
                }
                else if(kcrightA.match(t)){
                    frontCutAction();
                }
                else if(kcleftA.match(t)){
                    rearCutAction();
                }
                else if(kcmC.match(t)){
                    simulationAction();
                }
                else if(kcplusC.match(t)){
                    zoomInAction();
                }
                else if(kcenterA.match(t)){
                    normalAction();
                }
                else if(kcminusC.match(t)){
                    zoomOutAction();
                }
                else if(kcfAC.match(t)){
                    fabricSettingAction();
                }
                else if(kckA.match(t)){
                    consumptionCalculatorAction();
                }
                else if(kckS.match(t)){
                    priceCalculatorAction();
                }
                else if(kchC.match(t)){
                    helpMenuAction();
                }
                else if(kciA.match(t)){
                    technicalMenuAction();
                }
                else if(kcenterC.match(t)){
                    aboutMenuAction();
                }
                else if(kcuC.match(t)){
                    contactMenuAction();
                }
                else if(kcxA.match(t)){
                    exitMenuAction();
                }
            }
        });
    }
    
    public void homeMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONHOME"));
        final Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setResizable(false);
        dialogStage.setIconified(false);
        dialogStage.setFullScreen(false);
        dialogStage.setTitle(objDictionaryAction.getWord("ALERT"));
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 300, 100, Color.WHITE);
        scene.getStylesheets().add(FabricView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                if(objFabric.getStrFabricID()!=null && !isNew)
                    objConfiguration.mapRecentFabric.put(objConfiguration.getStrClothType(),objFabric.getStrFabricID());
                dialogStage.close();
                fabricStage.close();
                System.gc();
                WindowView objWindowView = new WindowView(objConfiguration);
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
    }
    
    public void fileMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONFILE"));
        menuHighlight("FILE");
    }
    
    public void editMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONEDIT"));
        menuHighlight("EDIT");
    }
    
    public void viewMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONVIEW"));
        menuHighlight("VIEW");
    }
    
    public void utilityMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("TOOLTIPUTILITY"));
        menuHighlight("UTILITY");
    }
    
    public void createMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONNEWFILE"));
        objWeave = new Weave();
        objWeave.setObjConfiguration(objConfiguration);
        WeaveImportView objWeaveImportView= new WeaveImportView(objWeave);
        if(objWeave.getStrWeaveID()!=null){
            objFabric.setStrBaseWeaveID(objWeave.getStrWeaveID());
            objWeave = null;
            newFabric();
        }else{
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            plotViewAction();
        }
    }
    
    public void openMenuAction(){
        try {
            lblStatus.setText(objDictionaryAction.getWord("ACTIONOPENFILE"));
            Fabric objFabricNew = new Fabric();
            objFabricNew.setObjConfiguration(objConfiguration);
            objFabricNew.setStrSearchBy(objConfiguration.getStrClothType());
            FabricImportView objFabricImportView= new FabricImportView(objFabricNew);
            if(objFabricNew.getStrFabricID()!=null){
                objFabric = objFabricNew;
                FabricAction objFabricAction = new FabricAction();
                objFabricAction.getFabric(objFabric);
                loadFabric();
            } else{
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                plotViewAction();
            }
        } catch (SQLException ex) {
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
        } catch (Exception ex) {
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
        }
        System.gc();
    }
    
    public void loadRecentMenuAction(){
        try {
            lblStatus.setText(objDictionaryAction.getWord("ACTIONLOADFILE"));
            Fabric objFabricNew = new Fabric();
            objFabricNew.setObjConfiguration(objConfiguration);
            if(objConfiguration.mapRecentFabric.get(objConfiguration.getStrClothType())!=null){
                objFabricNew.setStrFabricID(objConfiguration.mapRecentFabric.get(objConfiguration.getStrClothType()));
            }else{
                objFabricNew.setStrOrderBy("Date");
                objFabricNew.setStrDirection("Descending");
                objFabricNew.setStrCondition("");
                objFabricNew.setStrSearchBy(objConfiguration.getStrClothType());                        
                FabricAction objFabricAction = new FabricAction();
                List lstFabric = (ArrayList)objFabricAction.lstImportFabric(objFabricNew).get(0);
                if(lstFabric!=null && lstFabric.size()>0)
                    objFabricNew.setStrFabricID(lstFabric.get(0).toString());
            }
            if(objFabricNew.getStrFabricID()!=null && !isNew){
                FabricAction objFabricAction = new FabricAction();
                objFabricAction.getFabric(objFabricNew);
                if(objFabricNew.getStrFabricAccess().equalsIgnoreCase("Public")){
                    objConfiguration.setServicePasswordValid(true);
                }
                new MessageView(objConfiguration);
                if(objConfiguration.getServicePasswordValid()){
                    objFabric = objFabricNew;
                    objConfiguration.setServicePasswordValid(false);
                    loadFabric();
                }
                objFabricNew = null;
                System.gc();
            } else{
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                plotViewAction();
            }
        } catch (SQLException ex) {
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
        } catch (Exception ex) {
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
        }
        System.gc();
    }
    
    public void saveMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEFILE"));
        isNew=false;
        saveUpdateFabric();
    }
    
    public void saveAsMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEASFILE"));
        objFabric.setStrFabricAccess(objConfiguration.getObjUser().getUserAccess("FABRIC_LIBRARY"));
        isNew = true;
        saveUpdateFabric();
    }
    
    public void exportHtmlAction(){
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEXMLFILE"));
            saveAsHtml();
        }
    }
    
    public void exportTextureAction(){
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVETEXTUREFILE"));
            saveAsImage();
            lblStatus.setText(objDictionaryAction.getWord("DATASAVED"));
        }
    }
    
    public void exportGraphAction(){
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEGRAPHFILE"));
            saveAsGraph();
            lblStatus.setText(objDictionaryAction.getWord("DATASAVED"));
        }
    }
    
    public void printSetupAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONPRINTSETUPFILE"));
        objPrinterJob.pageDialog(objPrinterAttribute);
    }
    
    public void printScreenAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONPRINTSCREENFILE"));
        printScreen();
    }
    
    public void printGridAction(){
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONPRINTGRIDFILE"));
            printGrid();
        }
    }
    
    public void printGraphAction(){
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONPRINTGRAPHFILE"));
            printGraph();
        }
    }
    
    public void printTextureAction(){
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONPRINTTEXTUREFILE"));
            printTexture();
        }
    }
    
    public void punchApplicationAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONPUNCHAPPLICATION"));
        //runDeviceOLD();
        runDevice();
    }
    
    public void undoAction(){
        if(!locked){
            lblStatus.setText(objDictionaryAction.getWord("ACTIONUNDO"));
            try {
                if(objUR.canUndo()){
                    objUR.undo();
                    objFabric = (Fabric)objUR.getObjData();
                    objFabric.setObjConfiguration(objConfiguration);
                    initFabricValue();
                    //System.out.println("Undo Fabric Name: "+objConfiguration.WIDTH);
                    lblStatus.setText(objUR.getStrTag()+" "+objDictionaryAction.getWord("UNDO")+":"+objDictionaryAction.getWord("SUCCESS"));
                }else{
                    lblStatus.setText(objDictionaryAction.getWord("MAXUNDO"));
                }
            } catch (Exception ex) {
                Logger.getLogger(FabricView.class.getName()).log(Level.SEVERE, null, ex);

                new Logging("SEVERE",ArtworkView.class.getName(),"Operation Undo",ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            } 
        }else{
            MessageView objMessageView = new MessageView("alert","Editing Locked !","Hello! We are very sorry,\n This feature is disable becuse you have pattern lock enabled.");
        }
    }
    
    public void redoAction(){
        if(!locked){
            lblStatus.setText(objDictionaryAction.getWord("ACTIONREDO"));
            try {
                if(objUR.canRedo()){
                    objUR.redo();
                    objFabric = (Fabric)objUR.getObjData();
                    objFabric.setObjConfiguration(objConfiguration);
                    initFabricValue();
                    //System.out.println("Redo Fabric Name: "+objConfiguration.WIDTH);
                    lblStatus.setText(objUR.getStrTag()+" "+objDictionaryAction.getWord("REDO")+":"+objDictionaryAction.getWord("SUCCESS"));
                }else{
                    lblStatus.setText(objDictionaryAction.getWord("MAXREDO"));
                }
            } catch (Exception ex) {
                new Logging("SEVERE",ArtworkView.class.getName(),"Operation Redo",ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }else{
            MessageView objMessageView = new MessageView("alert","Editing Locked !","Hello! We are very sorry,\n This feature is disable becuse you have pattern lock enabled.");
        }
    }
    
    public void artworkAssignmentAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONARTWORKASSINGMENTEDIT"));
        editArtwork();
    }
    
    public void weavingPatternAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONWEAVINGPATTERNEDIT"));
        editWeave();
    }
    
    public void graphDesignAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONGRAPHPATTERNEDIT"));
        editGraph();
    }
    
    public void yarnPropertiesAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONYARNEDIT"));
        editYarn();
    }
    
    public void densityAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONDENSITYEDIT"));
        editDensity();
    }
    
    public void threadSequenceAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONTHREADPATTERNEDIT"));
        editPattern();
    }
    
    public void switchColorAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONSWITCHCOLOREDIT"));
        switchColor();
    }
    
    public void patternLockAction(){
        locked = true;
        lblStatus.setText(objDictionaryAction.getWord("ACTIONPATTERNLOCKEDEDIT"));
        patternLockedEditVB.getChildren().set(0,new ImageView(objConfiguration.getStrColourDimmed()+"/pattern_lock.png"));
        patternUnlockEditVB.getChildren().set(0,new ImageView(objConfiguration.getStrColour()+"/pattern_unlock.png"));
        //patternLockedEditVB.setVisible(false);
        //patternUnlockEditVB.setVisible(true);
        patternLockedEditVB.setDisable(true);
        patternUnlockEditVB.setDisable(false);
    }
    
    public void patternUnlockAction(){
        locked = false;
        lblStatus.setText(objDictionaryAction.getWord("ACTIONPATTERNUNLOCKEDEDIT"));
        patternLockedEditVB.getChildren().set(0,new ImageView(objConfiguration.getStrColour()+"/pattern_lock.png"));
        patternUnlockEditVB.getChildren().set(0,new ImageView(objConfiguration.getStrColourDimmed()+"/pattern_unlock.png"));
        //patternLockedEditVB.setVisible(true);
        //patternUnlockEditVB.setVisible(false);
        patternLockedEditVB.setDisable(false);
        patternUnlockEditVB.setDisable(true);
    }
    
    public void compositeViewAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONCOMPOSITEVIEW"));
        plotViewActionMode = 1;
        plotViewAction();
    }
    
    public void gridViewAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONGRIDVIEW"));
        plotViewActionMode = 2;
        plotViewAction();
    }
    
    public void graphViewAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONGRAPHVIEW"));
        plotViewActionMode = 3;
        plotViewAction();
    }
    
    public void frontSideViewAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONVISULIZATIONVIEW"));
        plotViewActionMode = 4;
        plotViewAction();
    }
    
    public void flipSideViewAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONFLIPSIDEVIEW"));
        plotViewActionMode = 5;
        plotViewAction();
    }
    
    public void backSideViewAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONSWITCHSIDEVIEW"));
        plotViewActionMode = 6;
        plotViewAction();
    }
    
    public void frontCutAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONCROSSSECTIONFRONTVIEW"));
        plotViewActionMode = 7;
        plotViewAction();
    }
    
    public void rearCutAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONCROSSSECTIONREARVIEW"));
        plotViewActionMode = 8;
        plotViewAction();
    }
    
    public void simulationAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONSIMULATION"));
        plotSimulationView();
    }
    
    public void zoomInAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONZOOMINVIEW"));
        //fabric.setScaleX(zoomfactor*=2);
        //fabric.setScaleY(zoomfactor);
        //container.setContent(fabric);
        double nonGraph=zoomfactor*2*objFabric.getIntWarp()*objFabric.getIntWeft()*objConfiguration.getIntVRepeat()*objConfiguration.getIntHRepeat();
        double yesGraph=zoomfactor*2*objFabric.getIntWarp()*objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize();
        if(nonGraph<3333333 && yesGraph<3333333)//3840000.0
            zoomfactor*=2;
        else
            lblStatus.setText(objDictionaryAction.getWord("MAXZOOMIN"));
        if(isEditingMode){
            if(isEditGraph)
                plotEditGraph();
            else if(isEditWeave)
                plotEditWeave();
            else
                plotViewAction();
        }else{
            plotViewAction();
        }
    }
    
    public void normalAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONZOOMNORMALVIEW"));
        zoomfactor=1;
        if(isEditingMode){
            if(isEditGraph)
                plotEditGraph();
            else if(isEditWeave)
                plotEditWeave();
            else
                plotViewAction();
        }else{
            plotViewAction();
        }
    }
    
    public void zoomOutAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONZOOMOUTVIEW"));
        //fabric.setScaleX(zoomfactor/=2);
        //fabric.setScaleY(zoomfactor);
        double nonGraphWarp=zoomfactor*objFabric.getIntWarp()/2;
        double nonGraphWeft=zoomfactor*objFabric.getIntWeft()/2;
        double yesGraphWarp=zoomfactor*objFabric.getIntWarp()*objFabric.getObjConfiguration().getIntBoxSize()/2;
        double yesGraphWeft=zoomfactor*objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()/2;
        if(nonGraphWarp>0 && nonGraphWeft>0 && yesGraphWarp>0 && yesGraphWeft>0)
            zoomfactor/=2;
        else
            lblStatus.setText(objDictionaryAction.getWord("MAXZOOMOUT"));
        if(isEditingMode){
            if(isEditGraph)
                plotEditGraph();
            else if(isEditWeave)
                plotEditWeave();
            else
                plotViewAction();
        }else{
            plotViewAction();
        }
    }
    
    public void fabricSettingAction(){
        try {
            lblStatus.setText(objDictionaryAction.getWord("ACTIONINFOSETTINGSUTILITY"));
            infoSettingsPane();
        } catch (Exception ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public void consumptionCalculatorAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONCONSUMPTIONCALCULATIONUTILITY"));
        ConsumptionView objConsumptionView = new ConsumptionView(objFabric); 
    }
    
    public void priceCalculatorAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONPRICECALCULATORUTILITY"));
        PriceView objPriceView = new PriceView(objFabric);
    }
    
    public void helpMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONHELP"));
        HelpView objHelpView = new HelpView(objConfiguration);
    }
    
    public void technicalMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONTECHNICAL"));
        TechnicalView objTechnicalView = new TechnicalView(objConfiguration);
    }
    
    public void aboutMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONABOUTUS"));
        AboutView objAboutView = new AboutView(objConfiguration);
    }
    
    public void contactMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONCONTACTUS"));
        ContactView objContactView = new ContactView(objConfiguration);
    }
    
    public void exitMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONEXIT"));
        System.gc();
        fabricStage.close();
    }
    
/**
 * menuHighlight
 * <p>
 * Function use for drawing menu bar for menu item,
 * and binding events for each menus with style. 
 * 
 * @param       String strMenu
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         javafx.event.*;
 * @link        FabricView
 */
    public void menuHighlight(String strMenu){
        if(strMenu == "UTILITY"){
            fileMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            editMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            viewMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            utilityMenu.setStyle("-fx-background-color: linear-gradient(#FFD765,#FFFC92);");
            fileMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            editMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            viewMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            utilityMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            toolBar.getItems().clear();
            populateUtilityToolbar();
        } else if(strMenu == "VIEW"){
            fileMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            editMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            viewMenu.setStyle("-fx-background-color: linear-gradient(#FFD765,#FFFC92);");
            utilityMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            fileMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            editMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            viewMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            utilityMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            toolBar.getItems().clear();
            populateViewToolbar();
        } else if(strMenu == "EDIT"){
            fileMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            editMenu.setStyle("-fx-background-color: linear-gradient(#FFD765,#FFFC92);");
            viewMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            utilityMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            fileMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            editMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            viewMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            utilityMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            toolBar.getItems().clear();
            populateEditToolbar();
        } else{
            fileMenu.setStyle("-fx-background-color: linear-gradient(#FFD765,#FFFC92);");
            editMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            viewMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            utilityMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            fileMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            editMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            viewMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            utilityMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            toolBar.getItems().clear();
            //removeAll(newFileBtn, openFileBtn, loadFileBtn, saveFileBtn, saveAsnewFileBtn, printFileBtn);
            populateFileToolbar();
        }               
    }  
    
 /**
 * populateFileToolbar
 * <p>
 * Function use for drawing tool bar for menu item File,
 * and binding events for each tools. 
 * 
 * @exception   (@throws SQLException)
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         javafx.event.*;
 * @link        FabricView
 */
     private void populateFileToolbar(){
        // New file item
        VBox newFileVB = new VBox();
        Label newFileLbl= new Label(objDictionaryAction.getWord("NEWFILE"));
        newFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNEWFILE")));
        newFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/new.png"), newFileLbl);
        newFileVB.setPrefWidth(80);
        newFileVB.getStyleClass().addAll("VBox");    
        // Open file item
        VBox openFileVB = new VBox(); 
        Label openFileLbl= new Label(objDictionaryAction.getWord("OPENFILE"));
        openFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOPENFILE")));
        openFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/open.png"), openFileLbl);
        openFileVB.setPrefWidth(80);
        openFileVB.getStyleClass().addAll("VBox");
        // load recent file item
        VBox loadFileVB = new VBox(); 
        Label loadFileLbl= new Label(objDictionaryAction.getWord("LOADFILE"));
        loadFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPLOADFILE")));
        loadFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/open_recent.png"), loadFileLbl);
        loadFileVB.setPrefWidth(80);
        loadFileVB.getStyleClass().addAll("VBox");
        // Save file item
        saveFileVB = new VBox(); 
        Label saveFileLbl= new Label(objDictionaryAction.getWord("SAVEFILE"));
        saveFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVEFILE")));
        saveFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/save.png"), saveFileLbl);
        saveFileVB.setPrefWidth(80);
        saveFileVB.getStyleClass().addAll("VBox");
        if(isNew){
            saveFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/save.png"));
            //saveFileVB.setVisible(false);
            saveFileVB.setDisable(true);
        }
        // Save As file item
        saveAsFileVB = new VBox(); 
        Label saveAsFileLbl= new Label(objDictionaryAction.getWord("SAVEASFILE"));
        saveAsFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVEASFILE")));
        saveAsFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/save_as.png"), saveAsFileLbl);
        saveAsFileVB.setPrefWidth(80);
        saveAsFileVB.getStyleClass().addAll("VBox");
        // Save As file item
        VBox saveXMLFileVB = new VBox(); 
        Label saveXMLFileLbl= new Label(objDictionaryAction.getWord("SAVEXMLFILE"));
        saveXMLFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVEXMLFILE")));
        saveXMLFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/export_html_as.png"), saveXMLFileLbl);
        saveXMLFileVB.setPrefWidth(80);
        saveXMLFileVB.getStyleClass().addAll("VBox");
        // Save Texture file item
        VBox saveTextureFileVB = new VBox(); 
        Label saveTextureFileLbl= new Label(objDictionaryAction.getWord("SAVETEXTUREFILE"));
        saveTextureFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVETEXTUREFILE")));
        saveTextureFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/export_texture_as.png"), saveTextureFileLbl);
        saveTextureFileVB.setPrefWidth(80);
        saveTextureFileVB.getStyleClass().addAll("VBox");
        // Save grid file item
        VBox saveGridFileVB = new VBox(); 
        Label saveGridFileLbl= new Label(objDictionaryAction.getWord("SAVEGRIDFILE"));
        saveGridFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVEGRIDFILE")));
        saveGridFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/export_graph_as.png"), saveGridFileLbl);
        saveGridFileVB.setPrefWidth(80);
        saveGridFileVB.getStyleClass().addAll("VBox");
        // Save graph file item
        VBox saveGraphFileVB = new VBox(); 
        Label saveGraphFileLbl= new Label(objDictionaryAction.getWord("SAVEGRAPHFILE"));
        saveGraphFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVEGRAPHFILE")));
        saveGraphFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/export_graph_as.png"), saveGraphFileLbl);
        saveGraphFileVB.setPrefWidth(80);
        saveGraphFileVB.getStyleClass().addAll("VBox");
        // print File menu
        VBox printSetupFileVB = new VBox(); 
        Label printSetupFileLbl= new Label(objDictionaryAction.getWord("PRINTSETUPFILE"));
        printSetupFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPRINTSETUPFILE")));
        printSetupFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/print_setup.png"), printSetupFileLbl);
        printSetupFileVB.setPrefWidth(80);
        printSetupFileVB.getStyleClass().addAll("VBox");
        // print File menu
        VBox printTextureFileVB = new VBox(); 
        Label printTextureFileLbl= new Label(objDictionaryAction.getWord("PRINTTEXTUREFILE"));
        printTextureFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPRINTTEXTUREFILE")));
        printTextureFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/print_preview.png"), printTextureFileLbl);
        printTextureFileVB.setPrefWidth(80);
        printTextureFileVB.getStyleClass().addAll("VBox");  
        // print File menu
        VBox printGridFileVB = new VBox(); 
        Label printGridFileLbl= new Label(objDictionaryAction.getWord("PRINTGRIDFILE"));
        printGridFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPRINTGRIDFILE")));
        printGridFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/print_part.png"), printGridFileLbl);
        printGridFileVB.setPrefWidth(80);
        printGridFileVB.getStyleClass().addAll("VBox");
        // print File menu
        VBox printGraphFileVB = new VBox(); 
        Label printGraphFileLbl= new Label(objDictionaryAction.getWord("PRINTGRAPHFILE"));
        printGraphFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPRINTGRAPHFILE")));
        printGraphFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/print_graph.png"), printGraphFileLbl);
        printGraphFileVB.setPrefWidth(80);
        printGraphFileVB.getStyleClass().addAll("VBox");        
        // print File menu
        VBox printScreenFileVB = new VBox(); 
        Label printScreenFileLbl= new Label(objDictionaryAction.getWord("PRINTSCREENFILE"));
        printScreenFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPRINTSCREENFILE")));
        printScreenFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/print.png"), printScreenFileLbl);
        printScreenFileVB.setPrefWidth(80);
        printScreenFileVB.getStyleClass().addAll("VBox");
        // punch card File menu
        VBox deviceFileVB = new VBox();
        Label deviceFileLbl= new Label(objDictionaryAction.getWord("PUNCHAPPLICATION"));
        deviceFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPUNCHAPPLICATION")));
        deviceFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/punch_card.png"), deviceFileLbl);
        deviceFileVB.setPrefWidth(80);
        deviceFileVB.getStyleClass().addAll("VBox"); 
		// garment viewer File menu
        VBox garmentFileVB = new VBox();
        Label garmentFileLbl= new Label(objDictionaryAction.getWord("CLOTHEDITOR"));
        garmentFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLOTHEDITOR")));
        garmentFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_editor.png"), garmentFileLbl);
        garmentFileVB.setPrefWidth(80);
        garmentFileVB.getStyleClass().addAll("VBox"); 
        // quit File menu
        VBox quitFileVB = new VBox(); 
        Label quitFileLbl= new Label(objDictionaryAction.getWord("QUITFILE"));
        quitFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPQUITFILE")));
        quitFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/quit.png"), quitFileLbl);
        quitFileVB.setPrefWidth(80);
        quitFileVB.getStyleClass().addAll("VBox"); 
        //Add the action to Buttons.
        newFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                createMenuAction();
            }
        });        
        openFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                openMenuAction();
            }
        });
        loadFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loadRecentMenuAction();
            }
        });
        saveFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                saveMenuAction();
            }
        });
        saveAsFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                saveAsMenuAction();
            }
        });
        saveXMLFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exportHtmlAction();
            }
        }); 
        saveTextureFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exportTextureAction();
            }
        });
        saveGridFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new MessageView(objConfiguration);
                if(objConfiguration.getServicePasswordValid()){
                    objConfiguration.setServicePasswordValid(false);
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEGRIDFILE"));
                    saveAsGrid();
                    lblStatus.setText(objDictionaryAction.getWord("DATASAVED"));
                }
            }
        });
        saveGraphFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exportGraphAction();
            }
        });
        printSetupFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               printSetupAction();
            }
        });
        printTextureFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                printTextureAction();
            }
        }); 
        printGridFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                printGridAction();
            }
        });
        printGraphFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                printGraphAction();
            }
        }); 
        printScreenFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                printScreenAction();
            }
        });  
        deviceFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                punchApplicationAction();
            }
        });
        garmentFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCLOTHEDITORUTILITY"));
                goToGarmentViewer();
            }
        });
        quitFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONQUITFILE"));
                fabricStage.close();
                System.gc();
                WindowView objWindowView = new WindowView(objConfiguration);
            }
        });
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(newFileVB,openFileVB,loadFileVB,saveFileVB,saveAsFileVB,saveXMLFileVB,saveTextureFileVB,saveGridFileVB,saveGraphFileVB,printSetupFileVB,printTextureFileVB,printGridFileVB,printGraphFileVB,printScreenFileVB,deviceFileVB,garmentFileVB);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
    }

/**
 * populateEditToolbar
 * <p>
 * Function use for drawing tool bar for menu item Edit,
 * and binding events for each tools. 
 * 
 * @exception   (@throws SQLException)
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         javafx.event.*;
 * @link        FabricView
 */
    private void populateEditToolbar(){
        // Undo edit item
        VBox undoEditVB = new VBox(); 
        Label undoEditLbl= new Label(objDictionaryAction.getWord("UNDO"));
        undoEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPUNDO")));
        undoEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/undo.png"), undoEditLbl);
        undoEditVB.setPrefWidth(80);
        undoEditVB.getStyleClass().addAll("VBox");   
        // Redo edit item 
        VBox redoEditVB = new VBox(); 
        Label redoEditLbl= new Label(objDictionaryAction.getWord("REDO"));
        redoEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPREDO")));
        redoEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/redo.png"), redoEditLbl);
        redoEditVB.setPrefWidth(80);
        redoEditVB.getStyleClass().addAll("VBox"); 
        // artwork edit item
        VBox artworkEditVB = new VBox(); 
        Label artworkEditLbl= new Label(objDictionaryAction.getWord("ARTWORKASSINGMENTEDIT"));
        artworkEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPARTWORKASSINGMENTEDIT")));
        artworkEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/artwork_editor.png"), artworkEditLbl);
        artworkEditVB.setPrefWidth(80);
        artworkEditVB.getStyleClass().addAll("VBox");
        // weave edit item
        VBox weavingPatternEditVB = new VBox(); 
        Label weavingPatternEditLbl= new Label(objDictionaryAction.getWord("WEAVINGPATTERNEDIT"));
        weavingPatternEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWEAVINGPATTERNEDIT")));
        weavingPatternEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/weave_editor.png"), weavingPatternEditLbl);
        weavingPatternEditVB.setPrefWidth(80);
        weavingPatternEditVB.getStyleClass().addAll("VBox"); 
        // graph edit item
        VBox graphPatternEditVB = new VBox(); 
        Label graphPatternEditLbl= new Label(objDictionaryAction.getWord("GRAPHPATTERNEDIT"));
        graphPatternEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPGRAPHPATTERNEDIT")));
        graphPatternEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/graph_editor.png"), graphPatternEditLbl);
        graphPatternEditVB.setPrefWidth(80);
        graphPatternEditVB.getStyleClass().addAll("VBox"); 
        // yarn Calculation item
        VBox yarnEditVB = new VBox(); 
        Label yarnEditLbl= new Label(objDictionaryAction.getWord("YARNEDIT"));
        yarnEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNEDIT")));
        yarnEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/yarn.png"), yarnEditLbl);
        yarnEditVB.setPrefWidth(80);
        yarnEditVB.getStyleClass().addAll("VBox");
        // density edit item
        VBox densityEditVB = new VBox(); 
        Label densityEditLbl= new Label(objDictionaryAction.getWord("DENSITYEDIT"));
        densityEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDENSITYEDIT")));
        densityEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/density.png"), densityEditLbl);
        densityEditVB.setPrefWidth(80);
        densityEditVB.getStyleClass().addAll("VBox"); 
        // Pattern edit item
        VBox threadPatternEditVB = new VBox(); 
        Label threadPatternEditLbl= new Label(objDictionaryAction.getWord("THREADPATTERNEDIT"));
        threadPatternEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTHREADPATTERNEDIT")));
        threadPatternEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/thread_pattern.png"), threadPatternEditLbl);
        threadPatternEditVB.setPrefWidth(80);
        threadPatternEditVB.getStyleClass().addAll("VBox");    
        // switch color edit item
        VBox switchColorEditVB = new VBox(); 
        Label switchColorEditLbl= new Label(objDictionaryAction.getWord("SWITCHCOLOREDIT"));
        switchColorEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSWITCHCOLOREDIT")));
        switchColorEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/switch_color.png"), switchColorEditLbl);
        switchColorEditVB.setPrefWidth(80);
        switchColorEditVB.getStyleClass().addAll("VBox");
        // Pattern Locked item
        patternLockedEditVB = new VBox(); 
        Label patternLockedEditLbl= new Label(objDictionaryAction.getWord("PATTERNLOCKEDEDIT"));
        patternLockedEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPATTERNLOCKEDEDIT")));
        patternLockedEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/pattern_lock.png"), patternLockedEditLbl);
        patternLockedEditVB.setPrefWidth(80);
        patternLockedEditVB.getStyleClass().addAll("VBox"); 
        // Pattern Locked item
        patternUnlockEditVB = new VBox(); 
        Label patternUnlockEditLbl= new Label(objDictionaryAction.getWord("PATTERNUNLOCKEDEDIT"));
        patternUnlockEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPATTERNUNLOCKEDEDIT")));
        patternUnlockEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/pattern_unlock.png"), patternUnlockEditLbl);
        patternUnlockEditVB.setPrefWidth(80);
        patternUnlockEditVB.getStyleClass().addAll("VBox"); 
        if(locked){
            patternLockedEditVB.getChildren().set(0,new ImageView(objConfiguration.getStrColourDimmed()+"/pattern_lock.png"));
            //patternLockedEditVB.setVisible(false);
            patternLockedEditVB.setDisable(true);
            patternUnlockEditVB.getChildren().set(0,new ImageView(objConfiguration.getStrColour()+"/pattern_unlock.png"));
            //patternUnlockEditVB.setVisible(true);
            patternUnlockEditVB.setDisable(false);
        }else{
            patternLockedEditVB.getChildren().set(0,new ImageView(objConfiguration.getStrColour()+"/pattern_lock.png"));
            //patternLockedEditVB.setVisible(true);
            patternLockedEditVB.setDisable(false);
            patternUnlockEditVB.getChildren().set(0,new ImageView(objConfiguration.getStrColourDimmed()+"/pattern_unlock.png"));
            //patternUnlockEditVB.setVisible(false);
            patternUnlockEditVB.setDisable(true);
        }
        //Add the action to Buttons.
        undoEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
				undoAction();
            }
        });
        redoEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                redoAction();
            }
        });
        artworkEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artworkAssignmentAction();
            }
        });
        weavingPatternEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                weavingPatternAction();
            }
        });
        graphPatternEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                graphDesignAction();
            }
        });
        yarnEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                yarnPropertiesAction();
            }
        }); 
        densityEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {                
                densityAction();
            }
        });  
        threadPatternEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {                
                threadSequenceAction();
            }
        });   
        switchColorEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {                
                switchColorAction();
            }
        });
        patternLockedEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                patternLockAction();
            }
        });
        patternUnlockEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                patternUnlockAction();
            }
        });
        patternUnlockEditVB.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {  
                locked = false;
                patternLockedEditVB.getChildren().set(0,new ImageView(objConfiguration.getStrColour()+"/pattern_lock.png"));
                patternUnlockEditVB.getChildren().set(0,new ImageView(objConfiguration.getStrColourDimmed()+"/pattern_unlock.png"));
                patternLockedEditVB.setDisable(false);
                patternUnlockEditVB.setDisable(true);
                //patternLockedEditVB.setVisible(true);
                //patternUnlockEditVB.setVisible(false);
            }
        });
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(undoEditVB,redoEditVB,artworkEditVB,weavingPatternEditVB,graphPatternEditVB,yarnEditVB,densityEditVB,threadPatternEditVB,switchColorEditVB,patternLockedEditVB,patternUnlockEditVB);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
    }

/**
 * populateViewToolbar
 * <p>
 * Function use for drawing tool bar for menu item View,
 * and binding events for each tools. 
 * 
 * @exception   (@throws SQLException)
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         javafx.event.*;
 * @link        FabricView
 */    
    private void populateViewToolbar(){
        // real time View item
        VBox realtimeViewVB = new VBox();
        Label realtimeViewLbl= new Label(objDictionaryAction.getWord("REALTIMEVIEW"));
        realtimeViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPREALTIMEVIEW")));
        realtimeViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/orientation.png"), realtimeViewLbl);
        realtimeViewVB.setPrefWidth(80);
        realtimeViewVB.getStyleClass().addAll("VBox");
        // Composite View item
        VBox compositeViewVB = new VBox(); 
        Label compositeViewLbl= new Label(objDictionaryAction.getWord("COMPOSITEVIEW"));
        compositeViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOMPOSITEVIEW")));
        compositeViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/composite_view.png"), compositeViewLbl);
        compositeViewVB.setPrefWidth(80);
        compositeViewVB.getStyleClass().addAll("VBox");        
        // Grid View item
        VBox gridViewVB = new VBox(); 
        Label gridViewLbl= new Label(objDictionaryAction.getWord("GRIDVIEW"));
        gridViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPGRIDVIEW")));
        gridViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/grid_view.png"), gridViewLbl);
        gridViewVB.setPrefWidth(80);
        gridViewVB.getStyleClass().addAll("VBox");
        // Graph View item
        VBox graphViewVB = new VBox(); 
        Label graphViewLbl= new Label(objDictionaryAction.getWord("GRAPHVIEW"));
        graphViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPGRAPHVIEW")));
        graphViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/graph_view.png"), graphViewLbl);
        graphViewVB.setPrefWidth(80);
        graphViewVB.getStyleClass().addAll("VBox");
        // Visulization View item;
        VBox frontSideViewVB = new VBox(); 
        Label frontSideViewLbl= new Label(objDictionaryAction.getWord("VISULIZATIONVIEW"));
        frontSideViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPVISULIZATIONVIEW")));
        frontSideViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/front_side.png"), frontSideViewLbl);
        frontSideViewVB.setPrefWidth(80);
        frontSideViewVB.getStyleClass().addAll("VBox");
        // Switch Side View item;
        VBox flipSideViewVB = new VBox(); 
        Label flipSideViewLbl= new Label(objDictionaryAction.getWord("FLIPSIDEVIEW"));
        flipSideViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFLIPSIDEVIEW")));
        flipSideViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/flip_side.png"), flipSideViewLbl);
        flipSideViewVB.setPrefWidth(80);
        flipSideViewVB.getStyleClass().addAll("VBox");
        // Switch Side View item;
        VBox switchSideViewVB = new VBox(); 
        Label switchSideViewLbl= new Label(objDictionaryAction.getWord("SWITCHSIDEVIEW"));
        switchSideViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSWITCHSIDEVIEW")));
        switchSideViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/switch_side.png"), switchSideViewLbl);
        switchSideViewVB.setPrefWidth(80);
        switchSideViewVB.getStyleClass().addAll("VBox");
        // cross Section View item;
        VBox crossSectionFrontViewVB = new VBox(); 
        Label crossSectionFrontViewLbl= new Label(objDictionaryAction.getWord("CROSSSECTIONFRONTVIEW"));
        crossSectionFrontViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCROSSSECTIONFRONTVIEW")));
        crossSectionFrontViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/front_cut.png"), crossSectionFrontViewLbl);
        crossSectionFrontViewVB.setPrefWidth(80);
        crossSectionFrontViewVB.getStyleClass().addAll("VBox");
        // cross Section View item;
        VBox crossSectionRearViewVB = new VBox(); 
        Label crossSectionRearViewLbl= new Label(objDictionaryAction.getWord("CROSSSECTIONREARVIEW"));
        crossSectionRearViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCROSSSECTIONREARVIEW")));
        crossSectionRearViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/rear_cut.png"), crossSectionRearViewLbl);
        crossSectionRearViewVB.setPrefWidth(80);
        crossSectionRearViewVB.getStyleClass().addAll("VBox");
        // Simulation View menu
        VBox simulationViewVB = new VBox(); 
        Label simulationViewLbl= new Label(objDictionaryAction.getWord("SIMULATION"));
        simulationViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSIMULATION")));
        simulationViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/simulation.png"), simulationViewLbl);
        simulationViewVB.setPrefWidth(80);
        simulationViewVB.getStyleClass().addAll("VBox");        
        // Ruler item
        VBox rulerVB = new VBox(); 
        Label rulerLbl= new Label(objDictionaryAction.getWord("RULERVIEW"));
        rulerLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPRULERVIEW")));
        rulerVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/ruler.png"), rulerLbl);
        rulerVB.setPrefWidth(80);
        rulerVB.getStyleClass().addAll("VBox");        
        // Zoom-In item
        VBox zoomInViewVB = new VBox(); 
        Label zoomInViewLbl= new Label(objDictionaryAction.getWord("ZOOMINVIEW"));
        zoomInViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPZOOMINVIEW")));
        zoomInViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/zoom_in.png"), zoomInViewLbl);
        zoomInViewVB.setPrefWidth(80);
        zoomInViewVB.getStyleClass().addAll("VBox");
        // Normal item
        VBox normalViewVB = new VBox(); 
        Label normalViewLbl= new Label(objDictionaryAction.getWord("ZOOMNORMALVIEW"));
        normalViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPZOOMNORMALVIEW")));
        normalViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/zoom_normal.png"), normalViewLbl);
        normalViewVB.setPrefWidth(80);
        normalViewVB.getStyleClass().addAll("VBox");
        // Zoom-Out item
        VBox zoomOutViewVB = new VBox(); 
        Label zoomOutViewLbl= new Label(objDictionaryAction.getWord("ZOOMOUTVIEW"));
        zoomOutViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPZOOMOUTVIEW")));
        zoomOutViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/zoom_out.png"), zoomOutViewLbl);
        zoomOutViewVB.setPrefWidth(80);
        zoomOutViewVB.getStyleClass().addAll("VBox");
        
        //Add the action to Buttons.
        realtimeViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONREALTIMEVIEW"));
                plotPreviewIcon();
            }
        });
        compositeViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                compositeViewAction();
            }
        });
        gridViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
				gridViewAction();
            }
        });
        graphViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                graphViewAction();
            }
        });
        frontSideViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                frontSideViewAction();
            }
        });
        flipSideViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                flipSideViewAction();
            }
        });
        switchSideViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                backSideViewAction();
            }
        });
        crossSectionFrontViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                frontCutAction();
            }
        });
        crossSectionRearViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rearCutAction();
            }
        });
        simulationViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                simulationAction();
                //plotViewActionMode = 9;
                //plotViewAction();
            }
        }); 
        rulerVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONRULERVIEW"));
                hRuler = new Label();
                vRuler = new Label();
        
                containerGP.add(hRuler, 1, 0);
                containerGP.add(vRuler, 0, 1);
                
            }
        });
        zoomInViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                zoomInAction();
            }
        });
        zoomOutViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                zoomOutAction();
            }
        });
        normalViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                normalAction();
            }
        });
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow"); 
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(realtimeViewVB,simulationViewVB,zoomInViewVB,normalViewVB,zoomOutViewVB);
        //flow.getChildren().addAll(compositeViewVB,gridViewVB,graphViewVB,frontSideViewVB,flipSideViewVB,switchSideViewVB,crossSectionFrontViewVB,crossSectionRearViewVB,simulationViewVB,zoomInViewVB,normalViewVB,zoomOutViewVB);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);        
    }
  
/**
 * populateUtilityToolbar
 * <p>
 * Function use for drawing tool bar for menu item Utility,
 * and binding events for each tools. 
 * 
 * @exception   (@throws SQLException)
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         javafx.event.*;
 * @link        FabricView
 */  
    private void populateUtilityToolbar(){        
        // price Calculation item
        VBox infoUtilityVB = new VBox(); 
        Label infoUtilityLbl= new Label(objDictionaryAction.getWord("INFOSETTINGSUTILITY"));
        infoUtilityLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPINFOSETTINGSUTILITY")));
        infoUtilityVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/settings.png"), infoUtilityLbl);
        infoUtilityVB.setPrefWidth(80);
        infoUtilityVB.getStyleClass().addAll("VBox");        
        // price Calculation item
        VBox palleteUtilityVB = new VBox(); 
        Label palleteUtilityLbl= new Label(objDictionaryAction.getWord("PALETTEGENERATIONUTILITY"));
        palleteUtilityLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPALETTEGENERATIONUTILITY")));
        palleteUtilityVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/color_palette.png"), palleteUtilityLbl);
        palleteUtilityVB.setPrefWidth(80);
        palleteUtilityVB.getStyleClass().addAll("VBox");        
        // price Calculation item
        VBox priceUtilityVB = new VBox(); 
        Label priceUtilityLbl= new Label(objDictionaryAction.getWord("PRICECALCULATORUTILITY"));
        priceUtilityLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPRICECALCULATORUTILITY")));
        priceUtilityVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/price.png"), priceUtilityLbl);
        priceUtilityVB.setPrefWidth(80);
        priceUtilityVB.getStyleClass().addAll("VBox");
        // consumption Calculation item
        VBox consumptionUtilityVB = new VBox(); 
        Label consumptionUtilityLbl= new Label(objDictionaryAction.getWord("CONSUMPTIONCALCULATIONUTILITY"));
        consumptionUtilityLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCONSUMPTIONCALCULATIONUTILITY")));
        consumptionUtilityVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/consumption.png"), consumptionUtilityLbl);
        consumptionUtilityVB.setPrefWidth(80);
        consumptionUtilityVB.getStyleClass().addAll("VBox");
         // fabric Calculation item
        VBox weaveUtilityVB = new VBox(); 
        Label weaveUtilityLbl= new Label(objDictionaryAction.getWord("WEAVEEDITORUTILITY"));
        weaveUtilityLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWEAVEEDITORUTILITY")));
        weaveUtilityVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/weave_editor.png"), weaveUtilityLbl);
        weaveUtilityVB.setPrefWidth(80);
        weaveUtilityVB.getStyleClass().addAll("VBox");
        // fabric Calculation item
        VBox artworkUtilityVB = new VBox(); 
        Label artworkUtilityLbl= new Label(objDictionaryAction.getWord("ARTWORKEDITORUTILITY"));
        artworkUtilityLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPARTWORKEDITORUTILITY")));
        artworkUtilityVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/artwork_editor.png"), artworkUtilityLbl);
        artworkUtilityVB.setPrefWidth(80);
        artworkUtilityVB.getStyleClass().addAll("VBox");
        // fabric Calculation item
        VBox clothUtilityVB = new VBox(); 
        Label clothUtilityLbl= new Label(objDictionaryAction.getWord("CLOTHEDITORUTILITY"));
        clothUtilityLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLOTHEDITORUTILITY")));
        clothUtilityVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_editor.png"), clothUtilityLbl);
        clothUtilityVB.setPrefWidth(80);
        clothUtilityVB.getStyleClass().addAll("VBox");
        
        //Add the action to Buttons.
        infoUtilityVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                fabricSettingAction();
            }
        });
        palleteUtilityVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONPALETTEGENERATIONUTILITY"));
                    ColorPaletteEditView objColorPaletteEditView = new ColorPaletteEditView(objFabric.getObjConfiguration());
                    //ColorPaletteView objColorPaletteView = new ColorPaletteView(objFabric.getObjConfiguration());
                    objFabric.setThreadPaletes(objFabric.getObjConfiguration().getColourPalette());
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        priceUtilityVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                priceCalculatorAction();
            }
        });
        consumptionUtilityVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                consumptionCalculatorAction();
            }
        });
        weaveUtilityVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONWEAVEEDITORUTILITY"));
                //fabricStage.setOpacity(.5);
                fabricStage.setIconified(true);
                WeaveView objWeaveView = new WeaveView(objConfiguration);
                fabricStage.setIconified(false);
                //fabricStage.setOpacity(1);
            }
        });
        artworkUtilityVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONARTWORKEDITORUTILITY"));
                //fabricStage.setOpacity(.5);
                fabricStage.setIconified(true);
                ArtworkView objArtworkView = new ArtworkView(objConfiguration);
                fabricStage.setIconified(false);
                //fabricStage.setOpacity(1);
            }
        });
        clothUtilityVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCLOTHEDITORUTILITY"));
                //fabricStage.setOpacity(.5);
                fabricStage.setIconified(true);
                ClothView objClothView = new ClothView(objConfiguration);
                fabricStage.setIconified(false);
                //fabricStage.setOpacity(1);
            }
        });
         
        //Create some Buttons. 
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95); // preferred width allows for two columns
        flow.getStyleClass().addAll("flow");                
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(palleteUtilityVB,infoUtilityVB,consumptionUtilityVB,priceUtilityVB);//,weaveUtilityVB,artworkUtilityVB,clothUtilityVB);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
    }
    
    public void plotGridView(){
        try {
            lblStatus.setText(objDictionaryAction.getWord("GETGRIDVIEW"));
            containerGP.getChildren().clear();
            BufferedImage bufferedImage = new BufferedImage(objFabric.getIntWarp()*3, objFabric.getIntWeft()*3,BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotGridView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
            BufferedImage bufferedImageesize = new BufferedImage((int)(objFabric.getIntWarp()*zoomfactor*3), (int)(objFabric.getIntWeft()*zoomfactor*3),BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, (int)(objFabric.getIntWarp()*zoomfactor*3), (int)(objFabric.getIntWeft()*zoomfactor*3), null);
            g.dispose();
            bufferedImage = null;
            fabric.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));
            container.setContent(fabric);
            bufferedImageesize = null;
            lblStatus.setText(objDictionaryAction.getWord("GOTGRIDVIEW"));
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),"plotGridView() : Error while viewing grid view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }  
    public void plotGraphMachineView(){
        try {
            lblStatus.setText(objDictionaryAction.getWord("GETGRAPHVIEW"));
            BufferedImage bufferedImage = new BufferedImage(objFabric.getIntWarp()*(objFabric.getIntExtraWeft()+2), objFabric.getIntWeft(),BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            
            if(objFabric.getIntExtraWarp()>0 || objFabric.getIntExtraWeft()>0){
                if(objConfiguration.getBlnPunchCard()){
                    bufferedImage = objFabricAction.plotGraphJaquardMachineView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
                }
            }
            //================================ For Outer to be square ===============================
            String[] data = objFabric.getObjConfiguration().getStrGraphSize().split("x");
            BufferedImage bufferedImageesize = new BufferedImage((int)(objFabric.getIntWarp()*(objFabric.getIntExtraWeft()+2)*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])/graphFactor), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/graphFactor),BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, (int)(objFabric.getIntWarp()*(objFabric.getIntExtraWeft()+2)*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])/graphFactor), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/graphFactor), null);
            g.setColor(java.awt.Color.BLACK);
            BasicStroke bs = new BasicStroke(2);
            g.setStroke(bs);
            
            for(int i = 0; i < objFabric.getIntWeft(); i++) {
                for(int j = 0; j < objFabric.getIntWarp()*(objFabric.getIntExtraWeft()+2); j++) {
                    if((j%Integer.parseInt(data[0]))==0){
                        bs = new BasicStroke(2);
                         g.setStroke(bs);
                    }else{
                        bs = new BasicStroke(1);
                        g.setStroke(bs);
                    }
                    g.drawLine((int)(j*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])/graphFactor), 0,  (int)(j*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])/graphFactor), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/graphFactor));
                }
                if((i%Integer.parseInt(data[1]))==0){
                    bs = new BasicStroke(2);
                    g.setStroke(bs);
                }else{
                    bs = new BasicStroke(1);
                    g.setStroke(bs);
                }
                g.drawLine(0, (int)(i*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/graphFactor), (int)(objFabric.getIntWarp()*(objFabric.getIntExtraWeft()+2)*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize())*Integer.parseInt(data[1])/graphFactor, (int)(i*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/graphFactor));
            }
                
            /*
            ============================= For Inner to be square ===============================
            BufferedImage bufferedImageesize = new BufferedImage((int)(intWeft*(objFabric.getIntExtraWeft()+2)*zoomfactor*objConfiguration.getIntBoxSize()), (int)(intWarp*zoomfactor*objConfiguration.getIntBoxSize()),BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, (int)(intWeft*(objFabric.getIntExtraWeft()+2)*zoomfactor*objConfiguration.getIntBoxSize()), (int)(intWarp*zoomfactor*objConfiguration.getIntBoxSize()), null);
            g.setColor(java.awt.Color.BLACK);
            BasicStroke bs = new BasicStroke(2);
            g.setStroke(bs);
            
            String[] data = objConfiguration.getStrGraphSize().split("x");
            System.out.println(Arrays.toString(data));
            
            for(int x = 0; x < (int)(intWarp*zoomfactor*objConfiguration.getIntBoxSize()); x+=(int)(zoomfactor*objConfiguration.getIntBoxSize())) {
                for(int y = 0; y < (int)(intWeft*(objFabric.getIntExtraWeft()+2)*zoomfactor*objConfiguration.getIntBoxSize()); y+=(int)(zoomfactor*objConfiguration.getIntBoxSize())) {
                    if(y%(int)(Integer.parseInt(data[0])*zoomfactor*objConfiguration.getIntBoxSize())==0){
                        bs = new BasicStroke(2);
                        g.setStroke(bs);
                    }else{
                        bs = new BasicStroke(1);
                        g.setStroke(bs);
                    }
                    g.drawLine(y, 0,  y, (int)(intWarp*zoomfactor*objConfiguration.getIntBoxSize()));
                }
                if(x%(int)(Integer.parseInt(data[1])*zoomfactor*objConfiguration.getIntBoxSize())==0){
                    bs = new BasicStroke(2);
                    g.setStroke(bs);
                }else{
                    bs = new BasicStroke(1);
                    g.setStroke(bs);
                }
                g.drawLine(0, x, (int)(intWeft*(objFabric.getIntExtraWeft()+2)*zoomfactor*objConfiguration.getIntBoxSize()), x);
            }
            */
            g.dispose();
            bufferedImage = null;
            fabric.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));
            container.setContent(fabric);
            bufferedImageesize = null;
            lblStatus.setText(objDictionaryAction.getWord("GOTGRAPHVIEW"));
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),"plotGraphDobbyView() : Error while viewing dobby garph view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    public void plotGraphView(){
        try {
            int intHeight=objFabric.getIntWeft();
            int intLength=objFabric.getIntWarp();
            lblStatus.setText(objDictionaryAction.getWord("GETGRAPHVIEW"));
            BufferedImage bufferedImage = new BufferedImage(objFabric.getIntWarp(), objFabric.getIntWeft(),BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            
            if(objFabric.getIntExtraWarp()>0 || objFabric.getIntExtraWeft()>0){
                if(objConfiguration.getBlnPunchCard()){
                    bufferedImage = objFabricAction.plotGraphJaquardMachineView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
                    intLength=objFabric.getIntWarp()*(objFabric.getIntExtraWeft()+2);
                } else{
                    bufferedImage = objFabricAction.plotGraphJaquardView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
                }
            }else{
                bufferedImage = objFabricAction.plotGraphDobbyView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
            }
            
            //================================ For Outer to be square ===============================
            
            String[] data = objFabric.getObjConfiguration().getStrGraphSize().split("x");
            BufferedImage bufferedImageesize = new BufferedImage((int)(intLength*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])/graphFactor), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/graphFactor),BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, (int)(intLength*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])/graphFactor), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/graphFactor), null);
            g.setColor(java.awt.Color.BLACK);
            BasicStroke bs = new BasicStroke(2);
            g.setStroke(bs);
            
            for(int i = 0; i < objFabric.getIntWeft(); i++) {
                for(int j = 0; j < intLength; j++) {
                    if((j%Integer.parseInt(data[0]))==0){
                        bs = new BasicStroke(2);
                        g.setStroke(bs);
                    }else{
                        bs = new BasicStroke(1);
                        g.setStroke(bs);
                    }
                    g.drawLine((int)(j*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])/graphFactor), 0,  (int)(j*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])/graphFactor), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/graphFactor));
                }
                if((i%Integer.parseInt(data[1]))==0){
                    bs = new BasicStroke(2);
                    g.setStroke(bs);
                }else{
                    bs = new BasicStroke(1);
                    g.setStroke(bs);
                }
                g.drawLine(0, (int)(i*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/graphFactor), (int)(intLength*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize())*Integer.parseInt(data[1])/graphFactor, (int)(i*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/graphFactor));
            }
            
            g.dispose();
            bufferedImage = null;
            fabric.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));
            container.setContent(fabric);
            bufferedImageesize = null;
            lblStatus.setText(objDictionaryAction.getWord("GOTGRAPHVIEW"));
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),"plotGraphDobbyView() : Error while viewing dobby garph view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    public void plotCompositeView(){
        try {
            lblStatus.setText(objDictionaryAction.getWord("GETCOMPOSITEVIEW"));
            int intHeight = objFabric.getIntWeft();
            int intLength = objFabric.getIntWarp();
            BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotCompositeView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), intLength, intHeight);
            /*if((objFabric.getIntEPI()/objFabric.getIntPPI())<=0){
                intHeight = (int)(intLength*(objFabric.getIntPPI()/objFabric.getIntEPI())*zoomfactor);
                intLength = (int)(intLength*zoomfactor);
            }else{
                intLength = (int)(intHeight*(objFabric.getIntEPI()/objFabric.getIntPPI())*zoomfactor);
                intHeight = (int)(intHeight*zoomfactor);
            }*/
            intLength = (int)(((objConfiguration.getIntDPI()*objFabric.getIntWarp())/objFabric.getIntEPI())*zoomfactor);
            intHeight = (int)(((objConfiguration.getIntDPI()*objFabric.getIntWeft())/objFabric.getIntPPI())*zoomfactor);
            
            BufferedImage bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = bufferedImageesize;
            if(objConfiguration.getBlnMRepeat() && objConfiguration.getIntVRepeat()>0 && objConfiguration.getIntHRepeat()>0){
                objArtworkAction = new ArtworkAction();
                bufferedImageesize = objArtworkAction.getImageRepeat(bufferedImage, objConfiguration.getIntVRepeat(), objConfiguration.getIntHRepeat());
            }
            bufferedImage = null;            
            //ImageIO.write(bufferedImageesize, "png", new File(System.getProperty("user.dir")+"/mla/visulization.png"));
            fabric.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));
            container.setContent(fabric);
            bufferedImageesize = null;
            lblStatus.setText(objDictionaryAction.getWord("GOTCOMPOSITEVIEW"));
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),"plotCompositeView() : Error while viewing composte view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    public void plotFrontSideView(){
        try{
            lblStatus.setText(objDictionaryAction.getWord("GETVISULIZATIONVIEW"));
            int intHeight = objFabric.getIntWeft();
            int intLength = objFabric.getIntWarp();
            BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotFrontSideView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), intLength, intHeight);
            /*if((objFabric.getIntEPI()/objFabric.getIntPPI())<=0){
                intHeight = (int)(intLength*(objFabric.getIntPPI()/objFabric.getIntEPI())*zoomfactor*3);
                intLength = (int)(intLength*zoomfactor*3);
            }else{
                intLength = (int)(intHeight*(objFabric.getIntEPI()/objFabric.getIntPPI())*zoomfactor*3);
                intHeight = (int)(intHeight*zoomfactor*3);
            }*/
            intLength = (int)(((objConfiguration.getIntDPI()*objFabric.getIntWarp())/objFabric.getIntEPI())*zoomfactor*3);
            intHeight = (int)(((objConfiguration.getIntDPI()*objFabric.getIntWeft())/objFabric.getIntPPI())*zoomfactor*3);
            
            BufferedImage bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = bufferedImageesize;
            if(objConfiguration.getBlnMRepeat()){
                objArtworkAction = new ArtworkAction();
                bufferedImageesize = objArtworkAction.getImageRepeat(bufferedImage, objConfiguration.getIntVRepeat(), objConfiguration.getIntHRepeat());
            }
            bufferedImage = null;            
            fabric.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));
            container.setContent(fabric);
            bufferedImageesize = null;
            lblStatus.setText(objDictionaryAction.getWord("GOTVISULIZATIONVIEW"));
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),"plotFrontSideView() : Error while viewing visulization view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } 
    }
    public void plotFlipSideView(){
        try{
            lblStatus.setText(objDictionaryAction.getWord("GETFLIPSIDEVIEW"));
            int intHeight = objFabric.getIntWeft();
            int intLength = objFabric.getIntWarp();
            BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotFlipSideView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), intLength, intHeight);
            /*if((objFabric.getIntEPI()/objFabric.getIntPPI())<=0){
                intHeight = (int)(intLength*(objFabric.getIntPPI()/objFabric.getIntEPI())*zoomfactor*3);
                intLength = (int)(intLength*zoomfactor*3);
            }else{
                intLength = (int)(intHeight*(objFabric.getIntEPI()/objFabric.getIntPPI())*zoomfactor*3);
                intHeight = (int)(intHeight*zoomfactor*3);
            }*/
            intLength = (int)(((objConfiguration.getIntDPI()*objFabric.getIntWarp())/objFabric.getIntEPI())*zoomfactor*3);
            intHeight = (int)(((objConfiguration.getIntDPI()*objFabric.getIntWeft())/objFabric.getIntPPI())*zoomfactor*3);
            
            BufferedImage bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = bufferedImageesize;
            if(objConfiguration.getBlnMRepeat()){
                objArtworkAction = new ArtworkAction();
                bufferedImageesize = objArtworkAction.getImageRepeat(bufferedImage, objConfiguration.getIntVRepeat(), objConfiguration.getIntHRepeat());
            }
            bufferedImage = null;            
            fabric.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));        
            container.setContent(fabric); 
            bufferedImageesize = null;
            lblStatus.setText(objDictionaryAction.getWord("GOTFLIPSIDEVIEW"));
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),"plotFlipSideView() : Error while viewing flip view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }  
    }
    public void plotSwitchSideView(){
        try{
            lblStatus.setText(objDictionaryAction.getWord("GETSWITCHSIDEVIEW"));
            int intHeight = objFabric.getIntWeft();
            int intLength = objFabric.getIntWarp();
            BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotSwitchSideView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), intLength, intHeight);
            /*if((objFabric.getIntEPI()/objFabric.getIntPPI())<=0){
                intHeight = (int)(intLength*(objFabric.getIntPPI()/objFabric.getIntEPI())*zoomfactor*3);
                intLength = (int)(intLength*zoomfactor*3);
            }else{
                intLength = (int)(intHeight*(objFabric.getIntEPI()/objFabric.getIntPPI())*zoomfactor*3);
                intHeight = (int)(intHeight*zoomfactor*3);
            }*/
            intLength = (int)(((objConfiguration.getIntDPI()*objFabric.getIntWarp())/objFabric.getIntEPI())*zoomfactor*3);
            intHeight = (int)(((objConfiguration.getIntDPI()*objFabric.getIntWeft())/objFabric.getIntPPI())*zoomfactor*3);
            
            BufferedImage bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = bufferedImageesize;
            if(objConfiguration.getBlnMRepeat()){
                objArtworkAction = new ArtworkAction();
                bufferedImageesize = objArtworkAction.getImageRepeat(bufferedImage, objConfiguration.getIntVRepeat(), objConfiguration.getIntHRepeat());
            }
            bufferedImage = null;            
            fabric.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));        
            container.setContent(fabric); 
            bufferedImageesize = null;
            lblStatus.setText(objDictionaryAction.getWord("GOTSWITCHSIDEVIEW"));
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),"plotSwitchSideView() : Error while viewing reverse view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } 
    }
    public void plotCrossSectionFrontView(){
        try{
            lblStatus.setText(objDictionaryAction.getWord("GETCROSSSECTIONFRONTVIEW"));
            int intLength = objFabric.getIntWarp();
            int intHeight = objFabric.getIntWeft();
            byte[][] newMatrix = objFabric.getFabricMatrix();
            List lstLines = new ArrayList();
            ArrayList<Byte> lstEntry = null;
            int lineCount = 0;
            for (int i = 0; i < intHeight; i++){
                lstEntry = new ArrayList();
                for (int j = 0; j < intLength; j++){
                    //add the first color on array
                    if(lstEntry.size()==0 && newMatrix[i][j]!=1)
                        lstEntry.add(newMatrix[i][j]);
                    //check for redudancy
                    else {                
                        if(!lstEntry.contains(newMatrix[i][j]) && newMatrix[i][j]!=1)
                            lstEntry.add(newMatrix[i][j]);
                    }
                }
                lstLines.add(lstEntry);
                lineCount+=lstEntry.size();
            }
            byte[][] baseMatrix = objArtworkAction.repeatMatrix(objFabric.getBaseWeaveMatrix(),lineCount,intLength);
            byte[][] fontMatrix = new byte[lineCount][intLength];
            lineCount=0;
            byte init = 0;
            for (int i = 0 ; i < intHeight; i++){
                lstEntry = (ArrayList)lstLines.get(i);
                for(int k=0; k<lstEntry.size(); k++, lineCount++){
                    init = (byte)lstEntry.get(k);
                    for (int j = 0; j < intLength; j++){
                        if(init==0){
                            fontMatrix[lineCount][j] = baseMatrix[i][j];
                        }else{
                            if(newMatrix[i][j]==init){
                                fontMatrix[lineCount][j] = init;
                            }else{
                                fontMatrix[lineCount][j] = (byte)1;
                            }
                        }   
                    }
                }
            }
            /*System.out.println("Row"+lineCount);
            for (int i = 0 ; i < lineCount; i++){
                for (int j = 0; j < intLength; j++){
                    System.err.print(" "+fontMatrix[i][j]);
                }
                System.err.println();
            }*/
            
            newMatrix = null;
            baseMatrix = null;
            intHeight = lineCount;
            BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotCrossSectionView(objFabric, fontMatrix, intLength, intHeight);
            intHeight = (int)(intHeight*zoomfactor*3);
            intLength = (int)(intLength*zoomfactor*3);
            BufferedImage bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = bufferedImageesize;
            if(objConfiguration.getBlnMRepeat()){
                objArtworkAction = new ArtworkAction();
                bufferedImageesize = objArtworkAction.getImageRepeat(bufferedImage, objConfiguration.getIntVRepeat(), objConfiguration.getIntHRepeat());
            }
            bufferedImage = null;            
            fabric.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));
            container.setContent(fabric);
            bufferedImageesize = null;
            System.gc();
            lblStatus.setText(objDictionaryAction.getWord("GETCROSSSECTIONFRONTVIEW"));
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),"plotCrossSectionFrontView() : Error while viewing  cross section view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",FabricView.class.getName(),"plotCrossSectionFrontView() : Error while viewing  cross section view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    public void plotCrossSectionRearView(){
        try{
            lblStatus.setText(objDictionaryAction.getWord("GETCROSSSECTIONREARVIEW"));
            int intLength = objFabric.getIntWarp();
            int intHeight = objFabric.getIntWeft();
            byte[][] newMatrix = objFabric.getFabricMatrix();
            List lstLines = new ArrayList();
            ArrayList<Byte> lstEntry = null;
            int lineCount = 0;
            for (int i = 0; i < intHeight; i++){
                lstEntry = new ArrayList();
                for (int j = 0; j < intLength; j++){
                    //add the first color on array
                    if(lstEntry.size()==0 && newMatrix[i][j]!=1)
                        lstEntry.add(newMatrix[i][j]);
                    //check for redudancy
                    else {                
                        if(!(lstEntry.contains(newMatrix[i][j]))  && newMatrix[i][j]!=1)
                            lstEntry.add(newMatrix[i][j]);
                    }
                }
                lstLines.add(lstEntry);
                lineCount+=lstEntry.size();
            }
            byte[][] baseMatrix = objArtworkAction.repeatMatrix(objFabric.getBaseWeaveMatrix(),lineCount,intLength);
            baseMatrix=objArtworkAction.invertMatrix(baseMatrix);
            byte[][] fontMatrix = new byte[lineCount][intLength];
            lineCount=0;
            byte init = 0;
            for (int i = 0 ; i < intHeight; i++){
                lstEntry = (ArrayList)lstLines.get(i);
                for(int k=0; k<lstEntry.size(); k++, lineCount++){
                    init = (byte)lstEntry.get(k);
                    for (int j = 0; j < intLength; j++){
                        if(init==0)
                            fontMatrix[lineCount][j] = baseMatrix[i][j];
                        else{
                            if(newMatrix[i][j]==init){
                                fontMatrix[lineCount][j] = (byte)1;
                            }else{
                                fontMatrix[lineCount][j] = init;
                            }
                        }   
                    }
                }
            }
                
            intHeight = lineCount;
            BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotCrossSectionView(objFabric, fontMatrix, intLength, intHeight);
            intHeight = (int)(intHeight*zoomfactor*3);
            intLength = (int)(intLength*zoomfactor*3);
            BufferedImage bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = bufferedImageesize;
            if(objConfiguration.getBlnMRepeat()){
                objArtworkAction = new ArtworkAction();
                bufferedImageesize = objArtworkAction.getImageRepeat(bufferedImage, objConfiguration.getIntVRepeat(), objConfiguration.getIntHRepeat());
            }
            bufferedImage = null;            
            fabric.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));
            container.setContent(fabric);
            bufferedImageesize = null;
            lblStatus.setText(objDictionaryAction.getWord("GETCROSSSECTIONREARVIEW"));
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),"plotCrossSectionRearView() : Error while viewing  cross section view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",FabricView.class.getName(),"plotCrossSectionRearView() : Error while viewing cross section view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public void plotSimulationView(){
        try {
            int intHeight = objFabric.getIntWeft();
            int intLength = objFabric.getIntWarp();
            BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotCompositeView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), intLength, intHeight);
            /*if((objFabric.getIntEPI()/objFabric.getIntPPI())<=0){
                intHeight = (int)(intLength*(objFabric.getIntPPI()/objFabric.getIntEPI())*zoomfactor);
                intLength = (int)(intLength*zoomfactor);
            }else{
                intLength = (int)(intHeight*(objFabric.getIntEPI()/objFabric.getIntPPI())*zoomfactor);
                intHeight = (int)(intHeight*zoomfactor);
            }*/
            intLength = (int)(((objConfiguration.getIntDPI()*objFabric.getIntWarp())/objFabric.getIntEPI())*zoomfactor);
            intHeight = (int)(((objConfiguration.getIntDPI()*objFabric.getIntWeft())/objFabric.getIntPPI())*zoomfactor);
            BufferedImage bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = bufferedImageesize;
            if(objConfiguration.getBlnMRepeat()){
                objArtworkAction = new ArtworkAction();
                bufferedImageesize = objArtworkAction.getImageRepeat(bufferedImage, objConfiguration.getIntVRepeat(), objConfiguration.getIntHRepeat());
            }
            bufferedImage = null;            

            SimulatorEditView objBaseSimulationView = new SimulatorEditView(objConfiguration, bufferedImageesize);                    
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    public void plotViewSimulation(){
        try {
            Simulator objSimulator = new Simulator();
            objSimulator.setObjConfiguration(objConfiguration);
            objSimulator.setStrCondition("");
            objSimulator.setStrSearchBy("");
            objSimulator.setStrDirection("Ascending");
            objSimulator.setStrOrderBy("Name");
            
            //fabricTypeCB.getItems().addAll("default","cotton","nylon","silk","woolen");
            SimulatorImportView objSimulatorImportView = new SimulatorImportView(objSimulator);
            
            if(objSimulator.getStrBaseFSID()!=null){
                lblStatus.setText(objDictionaryAction.getWord("GETSIMULATION2DVIEW"));
            
                int intHeight = objFabric.getIntWeft();
                int intLength = objFabric.getIntWarp();
                weftYarn = objFabric.getWeftYarn();
                warpYarn = objFabric.getWarpYarn();
                weftExtraYarn = objFabric.getWeftExtraYarn();
                warpExtraYarn = objFabric.getWarpExtraYarn();
                        
                int warpCount = warpYarn.length;
                int weftCount = weftYarn.length;
                int weftExtraCount = objFabric.getIntExtraWeft();
                int warpExtraCount = objFabric.getIntExtraWarp();

                byte[][] repeatMatrix = new byte[intHeight][intLength];
                int dpi = objConfiguration.getIntDPI();
                int warpFactor = dpi/objFabric.getIntEPI();
                int weftFactor = dpi/objFabric.getIntPPI();
                        
                for(int i = 0; i < intHeight; i++) {
                    for(int j = 0; j < intLength; j++) {
                        if(i>=objFabric.getIntWeft() && j<objFabric.getIntWarp()){
                            repeatMatrix[i][j] = objFabric.getFabricMatrix()[i%objFabric.getIntWeft()][j];
                        }else if(i<objFabric.getIntWeft() && j>=objFabric.getIntWarp()){
                            repeatMatrix[i][j] = objFabric.getFabricMatrix()[i][j%objFabric.getIntWarp()];
                        }else if(i>=objFabric.getIntWeft() && j>=objFabric.getIntWarp()){
                            repeatMatrix[i][j] = objFabric.getFabricMatrix()[i%objFabric.getIntWeft()][j%objFabric.getIntWarp()];
                        }else{                
                            repeatMatrix[i][j] = objFabric.getFabricMatrix()[i][j];
                        }
                    }
                }
            
                UtilityAction objUtilityAction = new UtilityAction();
                objUtilityAction.getBaseFabricSimultion(objSimulator);
                
                byte[] bytBaseThumbnil=objSimulator.getBytBaseFSIcon();
                SeekableStream stream = new ByteArraySeekableStream(bytBaseThumbnil);
                String[] names = ImageCodec.getDecoderNames(stream);
                ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                RenderedImage im = dec.decodeAsRenderedImage();
                BufferedImage srcImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
                bytBaseThumbnil=null;
            
                String srcImageName=System.getProperty("user.dir")+"/mla/";  //fabric colored red_tshirt white-cloth green-fabric
               
                BufferedImage myImage = new BufferedImage((int)(intLength), (int)(intHeight),BufferedImage.TYPE_INT_RGB);
                        
                for(int i = 0; i < intHeight; i++) {
                    for(int j = 0; j < intLength; j++) {
                        if(i>=srcImage.getHeight() && j<srcImage.getWidth()){
                            myImage.setRGB(j, i, srcImage.getRGB(j, i%srcImage.getHeight()));
                        }else if(i<srcImage.getHeight() && j>=srcImage.getWidth()){
                            myImage.setRGB(j, i, srcImage.getRGB(j%srcImage.getWidth(), i));
                        }else if(i>=srcImage.getHeight() && j>=srcImage.getWidth()){
                            myImage.setRGB(j, i, srcImage.getRGB(j%srcImage.getWidth(), i%srcImage.getHeight()));
                        }else{
                            myImage.setRGB(j, i, srcImage.getRGB(j, i));
                        }
                    }
                }
                        
                /*
                Graphics2D g = myImage.createGraphics();
                g.drawImage(srcImage, 0, 0, (int)(intLength), (int)(intHeight), null);
                g.dispose();
                srcImage = null;
                */
                BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);

                int rgb = 0;
                for(int x = 0; x < intHeight; x++) {
                    for(int y = 0; y < intLength; y++) {
                        int pixel = myImage.getRGB(y, x);
                        int alpha = (pixel >> 24) & 0xff;
                        int red   = (pixel >>16) & 0xff;
                        int green = (pixel >> 8) & 0xff;
                        int blue  =  pixel & 0xff;
                        //if(red!=255 || green!=255 || blue!=255){
                        // Convert RGB to HSB
                        float[] hsb = java.awt.Color.RGBtoHSB(red, green, blue, null);
                        float hue = hsb[0];
                        float saturation = hsb[1];
                        float brightness = hsb[2];
                        hsb = null;

                        if(repeatMatrix[x][y]==1){
                            int nred = new java.awt.Color((float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).getRed();
                            int ngreen = new java.awt.Color((float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).getGreen();
                            int nblue = new java.awt.Color((float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).getBlue();
                            hsb = java.awt.Color.RGBtoHSB(nred, ngreen, nblue, null);
                        }else if(repeatMatrix[x][y]==0){
                            int nred = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRed();
                            int ngreen = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getGreen();
                            int nblue = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getBlue();
                            hsb = java.awt.Color.RGBtoHSB(nred, ngreen, nblue, null);
                        }else if(weftExtraCount>0){
                            for(int a=0; a<weftExtraCount; a++){
                                if(repeatMatrix[x][y]==(a+2)){
                                    int nred = new java.awt.Color((float)Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getRed();
                                    int ngreen = new java.awt.Color((float)Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getGreen();
                                    int nblue = new java.awt.Color((float)Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getBlue();
                                    hsb = java.awt.Color.RGBtoHSB(nred, ngreen, nblue, null);
                                }
                            }
                        }else{
                            int nred = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRed();
                            int ngreen = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getGreen();
                            int nblue = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getBlue();
                            hsb = java.awt.Color.RGBtoHSB(nred, ngreen, nblue, null);
                        }
                        hue = hsb[0];
                        saturation = hsb[1];
                        // Convert HSB to RGB value
                        rgb = java.awt.Color.HSBtoRGB(hue, saturation, brightness);
                        //}else{
                        //  rgb = pixel;
                        //}
                        bufferedImage.setRGB(y, x, rgb);
                    }
                }
                /*if((objFabric.getIntEPI()/objFabric.getIntPPI())<=0){
                intHeight = (int)(intLength*(objFabric.getIntPPI()/objFabric.getIntEPI())*zoomfactor);
                intLength = (int)(intLength*zoomfactor);
                }else{
                intLength = (int)(intHeight*(objFabric.getIntEPI()/objFabric.getIntPPI())*zoomfactor);
                intHeight = (int)(intHeight*zoomfactor);
                }*/
                intLength = (int)(((objConfiguration.getIntDPI()*objFabric.getIntWarp())/objFabric.getIntEPI())*zoomfactor);
                intHeight = (int)(((objConfiguration.getIntDPI()*objFabric.getIntWeft())/objFabric.getIntPPI())*zoomfactor);
                BufferedImage bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = bufferedImageesize.createGraphics();
                g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
                g.dispose();
                bufferedImage = bufferedImageesize;
                if(objConfiguration.getBlnMRepeat()){
                    objArtworkAction = new ArtworkAction();
                    bufferedImageesize = objArtworkAction.getImageRepeat(bufferedImage, objConfiguration.getIntVRepeat(), objConfiguration.getIntHRepeat());
                }
                bufferedImage = null;
                repeatMatrix = null;

                ImageIO.write(bufferedImageesize, "png", new File(System.getProperty("user.dir")+"/mla/simulation.png"));

                fabric.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));
                container.setContent(fabric);
                bufferedImageesize=null;
                lblStatus.setText(objDictionaryAction.getWord("GOTSIMULATION2DVIEW"));                
            }else{
                lblStatus.setText(objFabric.getStrFabricName()+" : "+objDictionaryAction.getWord("DATASAVED"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(FabricView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }         
    }
    
    public void plotSimulation2DView(){
        try {
            lblStatus.setText(objDictionaryAction.getWord("GETSIMULATION2DVIEW"));
            int intHeight = (int)objConfiguration.HEIGHT;//intWeft * objConfiguration.getIntVRepeat();
            int intLength = (int)objConfiguration.WIDTH;//intWarp * objConfiguration.getIntHRepeat();
            weftYarn = objFabric.getWeftYarn();
            warpYarn = objFabric.getWarpYarn();
            weftExtraYarn = objFabric.getWeftExtraYarn();
            warpExtraYarn = objFabric.getWarpExtraYarn();
            
            int warpCount = warpYarn.length;
            int weftCount = weftYarn.length;
            int weftExtraCount = objFabric.getIntExtraWeft();
            int warpExtraCount = objFabric.getIntExtraWarp();
            
            byte[][] repeatMatrix = new byte[intHeight][intLength];
            int dpi = objConfiguration.getIntDPI();
            int warpFactor = dpi/objFabric.getIntEPI();
            int weftFactor = dpi/objFabric.getIntPPI();
            
            for(int i = 0; i < intHeight; i++) {
                for(int j = 0; j < intLength; j++) {
                    if(i>=objFabric.getIntWeft() && j<objFabric.getIntWarp()){
                        repeatMatrix[i][j] = objFabric.getFabricMatrix()[i%objFabric.getIntWeft()][j];
                    }else if(i<objFabric.getIntWeft() && j>=objFabric.getIntWarp()){
                        repeatMatrix[i][j] = objFabric.getFabricMatrix()[i][j%objFabric.getIntWarp()];
                    }else if(i>=objFabric.getIntWeft() && j>=objFabric.getIntWarp()){
                        repeatMatrix[i][j] = objFabric.getFabricMatrix()[i%objFabric.getIntWeft()][j%objFabric.getIntWarp()];
                    }else{
                        repeatMatrix[i][j] = objFabric.getFabricMatrix()[i][j];                
                    }
                }
            }
            BufferedImage srcImage = ImageIO.read(new File(System.getProperty("user.dir")+"/mla/fabric.jpg")); //colored red_tshirt white-cloth green-fabric
            BufferedImage myImage = new BufferedImage((int)(intLength), (int)(intHeight),BufferedImage.TYPE_INT_RGB);
            Graphics2D g = myImage.createGraphics();
            g.drawImage(srcImage, 0, 0, (int)(intLength), (int)(intHeight), null);
            g.dispose();
            srcImage = null;
            
            BufferedImage bufferedImage = new BufferedImage(intLength, intHeight,BufferedImage.TYPE_INT_RGB);
            int rgb = 0;
            for(int x = 0; x < intHeight; x++) {
                for(int y = 0; y < intLength; y++) {
                    int pixel = myImage.getRGB(y, x);     
                    int alpha = (pixel >> 24) & 0xff;
                    int red   = (pixel >>16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue  =  pixel & 0xff;                  
                    //if(red!=255 || green!=255 || blue!=255){
                        // Convert RGB to HSB
                        float[] hsb = java.awt.Color.RGBtoHSB(red, green, blue, null);
                        float hue = hsb[0];
                        float saturation = hsb[1];
                        float brightness = hsb[2];
                        hsb = null;

                        if(repeatMatrix[x][y]==1){
                            int nred = new java.awt.Color((float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).getRed();
                            int ngreen = new java.awt.Color((float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).getGreen();
                            int nblue = new java.awt.Color((float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).getBlue();
                            hsb = java.awt.Color.RGBtoHSB(nred, ngreen, nblue, null);
                        }else if(repeatMatrix[x][y]==0){
                            int nred = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRed();
                            int ngreen = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getGreen();
                            int nblue = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getBlue();
                            hsb = java.awt.Color.RGBtoHSB(nred, ngreen, nblue, null);
                        }else if(weftExtraCount>0){
                            for(int a=0; a<weftExtraCount; a++){
                                if(repeatMatrix[x][y]==(a+2)){
                                    int nred = new java.awt.Color((float)Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getRed();
                                    int ngreen = new java.awt.Color((float)Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getGreen();
                                    int nblue = new java.awt.Color((float)Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getBlue();
                                    hsb = java.awt.Color.RGBtoHSB(nred, ngreen, nblue, null);
                                }
                            }
                        }else{
                            int nred = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRed();
                            int ngreen = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getGreen();
                            int nblue = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getBlue();
                            hsb = java.awt.Color.RGBtoHSB(nred, ngreen, nblue, null);
                        }
                        hue = hsb[0];
                        saturation = hsb[1];
                        // Convert HSB to RGB value
                        rgb = java.awt.Color.HSBtoRGB(hue, saturation, brightness);
                    //}else{
                      //  rgb = pixel;
                    //}
                    bufferedImage.setRGB(y, x, rgb);
                }
            }
            repeatMatrix = null;
            /*
            BufferedImage bufferedImagesize = new BufferedImage((int)(width*zoomfactor*3), (int)(height*zoomfactor*3),BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImagesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, (int)(width*zoomfactor*3), (int)(height*zoomfactor*3), null);
            g.dispose();
            bufferedImage = null;
            */
            fabric.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            
            /*Light.Distant light = new Light.Distant();
            light.setAzimuth(-135.0f);
            Lighting l = new Lighting();
            l.setLight(light);
            l.setSurfaceScale(5.0f);
            fabric.setEffect(l);
            */
           /*fabric.setTranslateX(0);
            fabric.setTranslateY(0);
            fabric.setTranslateZ(222);
            */
            container.setContent(fabric);
            //bufferedImagesize = null;
            lblStatus.setText(objDictionaryAction.getWord("GOTSIMULATION2DVIEW"));
        } catch (IOException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    public void plotSimulation3DView1(){
        lblStatus.setText(objDictionaryAction.getWord("GETSIMULATION3DVIEW"));
        int intHeight = objFabric.getIntWeft() * objConfiguration.getIntVRepeat();//objConfiguration.HEIGHT
        int intLength = objFabric.getIntWarp() * objConfiguration.getIntHRepeat();//objConfiguration.WIDTH
        weftYarn = objFabric.getWeftYarn();
        warpYarn = objFabric.getWarpYarn();
        weftExtraYarn = objFabric.getWeftExtraYarn();
        warpExtraYarn = objFabric.getWarpExtraYarn();
        
        int warpCount = warpYarn.length;
        int weftCount = weftYarn.length;
        int weftExtraCount = objFabric.getIntExtraWeft();
        int warpExtraCount = objFabric.getIntExtraWarp();
        byte[][] repeatMatrix = new byte[intHeight][intLength];
        int dpi = objConfiguration.getIntDPI();
        int warpFactor = dpi/objFabric.getIntEPI();
        int weftFactor = dpi/objFabric.getIntPPI();
        for(int i = 0; i < intHeight; i++) {
            for(int j = 0; j < intLength; j++) {
                if(i>=objFabric.getIntWeft() && j<objFabric.getIntWarp()){
                    repeatMatrix[i][j] = objFabric.getFabricMatrix()[i%objFabric.getIntWeft()][j];
                }else if(i<objFabric.getIntWeft() && j>=objFabric.getIntWarp()){
                    repeatMatrix[i][j] = objFabric.getFabricMatrix()[i][j%objFabric.getIntWarp()];
                }else if(i>=objFabric.getIntWeft() && j>=objFabric.getIntWarp()){
                    repeatMatrix[i][j] = objFabric.getFabricMatrix()[i%objFabric.getIntWeft()][j%objFabric.getIntWarp()];
                }else{
                    repeatMatrix[i][j] = objFabric.getFabricMatrix()[i][j];                
                }
            }
        }
        BufferedImage bufferedImageesize = new BufferedImage((int)(intLength*zoomfactor*3), (int)(intHeight*zoomfactor*3),BufferedImage.TYPE_INT_RGB);        
        Graphics2D g = bufferedImageesize.createGraphics();
        int rgb = 0;
        java.awt.Color rgbcolor = null;
        for(int x = 0; x < intHeight; x++) {
            for(int y = 0; y < intLength; y++) {
                if(repeatMatrix[x][y]==1){
                    rgbcolor = new java.awt.Color((float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue());
                    rgb = rgbcolor.getRGB(); 
                } else if(repeatMatrix[x][y]==0){
                    rgbcolor = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue());
                    rgb = rgbcolor.getRGB(); 
                } else if(weftExtraCount>0){
                    for(int a=0; a<weftExtraCount; a++)
                        if(repeatMatrix[x][y]==(a+2)){
                            rgbcolor = new java.awt.Color((float)Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue());
                            rgb = rgbcolor.getRGB(); 
                            
                        } 
                } else{
                    rgbcolor = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()); 
                    rgb = rgbcolor.getRGB(); 
                }
                //bufferedImageesize.setRGB(y, x, rgb);
                g.setColor(rgbcolor);
                g.fillOval(x, y, (int)zoomfactor*3, (int)zoomfactor*3);
            }
        }
        g.dispose();
        
        /*
        BufferedImage bufferedImage = new BufferedImage(width*3, height*3,BufferedImage.TYPE_INT_RGB);        
        int bands = 3;
        int rgb = 0;
        for(int x = 0, p = 0; x < height; x++) {
            for(int y = 0, q = 0; y < width; y++) {
                for(int i = 0; i < bands; i++) {
                    for(int j = 0; j < bands; j++) {                        
                        if(repeatMatrix[x][y]==1){
                            if(j==0)
                                rgb = new java.awt.Color((float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).brighter().getRGB();
                            else if(j==2)
                                rgb = new java.awt.Color((float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).darker().getRGB();
                            else
                                rgb = new java.awt.Color((float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).getRGB();
                        } else if(repeatMatrix[x][y]==0){
                            if(i==0)
                                rgb = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).brighter().getRGB();
                            else if(i==2)
                                rgb = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).darker().getRGB();
                            else
                                rgb = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRGB();
                        } else if(weftExtraCount>0){
                            for(int a=0; a<weftExtraCount; a++){
                                if(repeatMatrix[x][y]==(a+2)){
                                    if(i==0)
                                        rgb = new java.awt.Color((float)Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).brighter().getRGB();
                                    else if(i==2)
                                        rgb = new java.awt.Color((float)Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).darker().getRGB();
                                    else
                                        rgb = new java.awt.Color((float)Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getRGB();
                                }
                            }
                        } else{
                            if(i==0)
                                rgb = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).brighter().getRGB();
                            else if(i==2)
                                rgb = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).darker().getRGB();
                            else
                                rgb = new java.awt.Color((float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRGB();
                        }
                        bufferedImage.setRGB(q+j, p+i, rgb);
                    }
                }
                q+=bands;
            }
            p+=bands;
        }
        repeatMatrix = null;
        BufferedImage bufferedImageesize = new BufferedImage((int)(width*zoomfactor*3), (int)(height*zoomfactor*3),BufferedImage.TYPE_INT_RGB);        
        Graphics2D g = bufferedImageesize.createGraphics();
        g.drawImage(bufferedImage, 0, 0, (int)(width*zoomfactor*3), (int)(height*zoomfactor*3), null);
        g.dispose();
        bufferedImage = null;
        */
        fabric.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));
        container.setContent(fabric); 
        bufferedImageesize = null; 
        lblStatus.setText(objDictionaryAction.getWord("GOTSIMULATION3DVIEW"));
        
        
        
                ///////////////// new code start /////////////////////
                
/*  
//clean weave
        int removeRow = 0;
        for(int x=0; x<height; x++){
            byte temp = 0;
            for(int y=0; y<width; y++){
                if(matrix[x][y]!=1){
                    temp = 1;
                }
            }
            if(temp == 0){
                removeRow++;
                for(int z=x; z<height-1; z++){
                    for(int y=0; y<width; y++){
                        matrix[z][y]=matrix[z+1][y];
                    }
                }
            }
        }
        System.err.println("removeRow-"+removeRow);
        if(removeRow>0){
            for(int p = 0; p < height; p++) {
                for(int q = 0; q < width; q++) {
                    if(p>=height-removeRow && q<width){
                        matrix[p][q] = matrix[p%(height-removeRow)][q];  
                    }else if(p<(height-removeRow) && q>=width){
                        matrix[p][q] = matrix[p][q%width];  
                    }else if(p>=(height-removeRow) && q>=width){
                        matrix[p][q] = matrix[p%(height-removeRow)][q%width];  
                    }else{
                        matrix[p][q] = matrix[p][q]; 
                    }
                }
            }
        }
  */          
        ///////////////// new code end /////////////////////
    }
    public void plotSimulation3DView(){
        lblStatus.setText(objDictionaryAction.getWord("GETSIMULATION3DVIEW"));
        int intHeight = objFabric.getIntWeft();// objConfiguration.getIntVRepeat();//objConfiguration.HEIGHT
        int intLength = objFabric.getIntWarp();// * objConfiguration.getIntHRepeat();//objConfiguration.WIDTH
        
        weftYarn = objFabric.getWeftYarn();
        warpYarn = objFabric.getWarpYarn();
        weftExtraYarn = objFabric.getWeftExtraYarn();
        warpExtraYarn = objFabric.getWarpExtraYarn();
        
        int warpCount = warpYarn.length;
        int weftCount = weftYarn.length;
        int weftExtraCount = objFabric.getIntExtraWeft();
        int warpExtraCount = objFabric.getIntExtraWarp();
        byte[][] repeatMatrix = new byte[intHeight][intLength];
        int dpi = objConfiguration.getIntDPI();
        int warpFactor = dpi/objFabric.getIntEPI();
        int weftFactor = dpi/objFabric.getIntPPI();
        
        for(int i = 0; i < intHeight; i++) {
            for(int j = 0; j < intLength; j++) {
                if(i>=objFabric.getIntWarp() && j<objFabric.getIntWeft()){
                     repeatMatrix[i][j] = objFabric.getFabricMatrix()[i%objFabric.getIntWarp()][j];  
                }else if(i<objFabric.getIntWarp() && j>=objFabric.getIntWeft()){
                     repeatMatrix[i][j] = objFabric.getFabricMatrix()[i][j%objFabric.getIntWeft()];  
                }else if(i>=objFabric.getIntWarp() && j>=objFabric.getIntWeft()){
                     repeatMatrix[i][j] = objFabric.getFabricMatrix()[i%objFabric.getIntWarp()][j%objFabric.getIntWeft()];  
                }else{
                     repeatMatrix[i][j] = objFabric.getFabricMatrix()[i][j]; 
                }                
            }
        }
        
        containerGP.getChildren().clear();
        for(int x = 0; x < intHeight; x++) {
            for(int y = 0; y < intLength; y++) {
                Label lblbox = new Label("");
                lblbox.setFont(new Font("Arial", objConfiguration.intBoxSize));
                lblbox.setPrefSize(objConfiguration.intBoxSize,objConfiguration.intBoxSize);
                //lblbox.setAlignment(Pos.CENTER);
                ImageView objIV ;
                if(repeatMatrix[x][y]==1 ){
                    objIV = new ImageView("/media/yarn/thread1.png");                    
                    //lblbox.setStyle("-fx-background-color: #000000; -fx-border-width: 1;  -fx-border-color: black;");
                }else{
                    objIV = new ImageView("/media/yarn/thread6.png");                   
                   // lblbox.setStyle("-fx-background-color: #ffffff; -fx-border-width: 1;  -fx-border-color: black;");                
                }
                objIV.setScaleX(zoomfactor);
                objIV.setScaleY(zoomfactor);
                lblbox.setGraphic(objIV);
                containerGP.add(lblbox,y,x);
           }
        }
        container.setContent(containerGP);
        lblStatus.setText(objDictionaryAction.getWord("GOTSIMULATION3DVIEW"));
    }
    
    public void plotRulerLine(){ 
        NumberAxis axisX = new NumberAxis(0, 400, 25);
        /*
        NumberAxis axisY = new NumberAxis(0, 333, 25);
        axisY.setRotate(90);
        axisY.setScaleY(-1);
        container.add(axisY, 0, 7);
        */
        int intLength = objFabric.getIntWarp();
        int intHeight = objFabric.getIntWeft(); 
        int dpi = objConfiguration.getIntDPI();
        int warpFactor = dpi/objFabric.getIntEPI();
        int weftFactor = dpi/objFabric.getIntPPI();
        byte[][] baseWeaveMatrix = objFabric.getBaseWeaveMatrix();
        int warpCount = warpYarn.length;
        int weftCount = weftYarn.length;
        int j = baseWeaveMatrix[0].length;
        int i = baseWeaveMatrix.length;
        BufferedImage newImage = new BufferedImage(intLength, intHeight,BufferedImage.TYPE_INT_RGB);   
        for(int x = 0; x<intHeight; x++){
            int weft = x/weftFactor;
            if(x%weftFactor==0){
                for(int y = 0; y<intLength; y++){
                    int rgb = 0;
                    int warp = y/warpFactor;
                    if(y%warpFactor==0){
                        if(baseWeaveMatrix[weft%i][warp%j]==1)
                            rgb = new java.awt.Color((float)Color.web(warpYarn[warp%warpCount].getStrYarnColor()).getRed(),(float)Color.web(warpYarn[warp%warpCount].getStrYarnColor()).getGreen(),(float)Color.web(warpYarn[warp%warpCount].getStrYarnColor()).getBlue()).getRGB(); 
                        else
                            rgb = new java.awt.Color((float)Color.web(weftYarn[weft%weftCount].getStrYarnColor()).getRed(),(float)Color.web(weftYarn[weft%weftCount].getStrYarnColor()).getGreen(),(float)Color.web(weftYarn[weft%weftCount].getStrYarnColor()).getBlue()).getRGB(); 
                    }else{
                        rgb = new java.awt.Color((float)Color.LIGHTGRAY.getRed(),(float)Color.LIGHTGRAY.getGreen(),(float)Color.LIGHTGRAY.getBlue()).getRGB();
                    }
                    newImage.setRGB(y, x, rgb);
              }
            }else{
                for(int y = 0; y<intLength; y++){
                    int rgb = new java.awt.Color((float)Color.LIGHTGRAY.getRed(),(float)Color.LIGHTGRAY.getGreen(),(float)Color.LIGHTGRAY.getBlue()).getRGB();
                    newImage.setRGB(y, x, rgb);
              }
            }
        } 
        BufferedImage bufferedImageesize = new BufferedImage(intLength*objConfiguration.getIntBoxSize()*(int)zoomfactor, intHeight*objConfiguration.getIntBoxSize()*(int)zoomfactor,BufferedImage.TYPE_INT_RGB);        
        Graphics2D g = bufferedImageesize.createGraphics();
        g.drawImage(newImage, 0, 0, intLength*objConfiguration.getIntBoxSize()*(int)zoomfactor, intHeight*objConfiguration.getIntBoxSize()*(int)zoomfactor, null);
        g.dispose();
        
        fabric.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));
        container.setContent(fabric);        
    }

    public void plotViewAction(){
        //menuHighlight("VIEW");
        objFabric.getObjConfiguration().setIntBoxSize(1);
        isEditingMode = false;
        isEditWeave = false;
        isEditGraph = false;
        populateLeftPane();
        
        if(plotViewActionMode==1)
            plotCompositeView();
        else if(plotViewActionMode==2)
            plotGridView();
        else if(plotViewActionMode==3)
            plotGraphView();
        else if(plotViewActionMode==4)
            plotFrontSideView();
        else if(plotViewActionMode==5)
            plotFlipSideView();
        else if(plotViewActionMode==6)
            plotSwitchSideView();
        else if(plotViewActionMode==7)
            plotCrossSectionFrontView();
        else if(plotViewActionMode==8)
            plotCrossSectionRearView();
        else if(plotViewActionMode==9)
            plotSimulationView();
        
        else if(plotViewActionMode==11)
            plotSimulation2DView();
        else if(plotViewActionMode==12)
            plotSimulation3DView();
        else if(plotViewActionMode==13)
            plotGraphMachineView();        
        else if(plotViewActionMode==14)
            plotRulerLine();      
        else
            plotCompositeView();
        System.err.println("zoomfactor:"+zoomfactor+"boxsize:"+objConfiguration.getIntBoxSize()+"intWeft:"+objFabric.getIntWeft()+" intWarp:"+objFabric.getIntWarp()+" intVRepeat:"+objConfiguration.getIntVRepeat()+" intHRepeat:"+objConfiguration.getIntHRepeat()+" weft diameter:"+objFabric.getIntEPI()+" Warp diameter:"+objFabric.getIntPPI());
        System.gc();
    }
    
    public void plotMenuIcon(){
        try {
            int intHeight = objFabric.getIntWeft();
            int intLength = objFabric.getIntWarp();
            BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotCompositeView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), intLength, intHeight);
            /*if((objFabric.getIntEPI()/objFabric.getIntPPI())<=0){
            intHeight = (int)(intLength*(objFabric.getIntPPI()/objFabric.getIntEPI())*zoomfactor);
            intLength = (int)(intLength*zoomfactor);
            }else{
            intLength = (int)(intHeight*(objFabric.getIntEPI()/objFabric.getIntPPI())*zoomfactor);
            intHeight = (int)(intHeight*zoomfactor);
            }*/
            intLength = 111;//(int)(((objConfiguration.getIntDPI()*objFabric.getIntWarp())/objFabric.getIntEPI())*zoomfactor);
            intHeight = 111;//(int)(((objConfiguration.getIntDPI()*objFabric.getIntWeft())/objFabric.getIntPPI())*zoomfactor);
            BufferedImage bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = null;
            //ImageIO.write(bufferedImageesize, "png", new File(System.getProperty("user.dir")+"/mla/menu_composite.png"));
            //compositeViewVB.getChildren().set(0, new ImageView(SwingFXUtils.toFXImage(bufferedImageesize, null)));

            intHeight = objFabric.getIntWeft()*3;
            intLength = objFabric.getIntWarp()*3;
            bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotGridView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
            intLength = 111;//(int)(objFabric.getIntWarp()*zoomfactor*3);
            intHeight = 111;//(int)(objFabric.getIntWeft()*zoomfactor*3);
            bufferedImageesize = new BufferedImage(intLength, intHeight,BufferedImage.TYPE_INT_RGB);
            g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = null;
            //ImageIO.write(bufferedImageesize, "png", new File(System.getProperty("user.dir")+"/mla/menu_grid.png"));
            //gridViewVB.getChildren().set(0, new ImageView(SwingFXUtils.toFXImage(bufferedImageesize, null)));
            
            intHeight=objFabric.getIntWeft();
            intLength=objFabric.getIntWarp();
            bufferedImage = new BufferedImage(objFabric.getIntWarp(), objFabric.getIntWeft(),BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            if(objFabric.getIntExtraWarp()>0 || objFabric.getIntExtraWeft()>0){
                if(objConfiguration.getBlnPunchCard()){
                    bufferedImage = objFabricAction.plotGraphJaquardMachineView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
                    intLength=objFabric.getIntWarp()*(objFabric.getIntExtraWeft()+2);
                } else{
                    bufferedImage = objFabricAction.plotGraphJaquardView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
                }
            }else{
                bufferedImage = objFabricAction.plotGraphDobbyView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
            }
            //================================ For Outer to be square ===============================
            String[] data = objFabric.getObjConfiguration().getStrGraphSize().split("x");
            bufferedImageesize = new BufferedImage((int)(intLength*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])),BufferedImage.TYPE_INT_RGB);
            g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, (int)(intLength*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])), null);
            g.setColor(java.awt.Color.BLACK);
            BasicStroke bs = new BasicStroke(2);
            g.setStroke(bs);
            for(int i = 0; i < objFabric.getIntWeft(); i++) {
                for(int j = 0; j < intLength; j++) {
                    if((j%Integer.parseInt(data[0]))==0){
                        bs = new BasicStroke(2);
                        g.setStroke(bs);
                    }else{
                        bs = new BasicStroke(1);
                        g.setStroke(bs);
                    }
                    g.drawLine((int)(j*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])), 0,  (int)(j*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])));
                }
                if((i%Integer.parseInt(data[1]))==0){
                    bs = new BasicStroke(2);
                    g.setStroke(bs);
                }else{
                    bs = new BasicStroke(1);
                    g.setStroke(bs);
                }
                g.drawLine(0, (int)(i*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])), (int)(intLength*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize())*Integer.parseInt(data[1]), (int)(i*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])));
            }
            g.dispose();
            bufferedImage = bufferedImageesize;
            intLength = 111;//(int)(objFabric.getIntWarp()*zoomfactor*3);
            intHeight = 111;//(int)(objFabric.getIntWeft()*zoomfactor*3);
            bufferedImageesize = new BufferedImage(intLength, intHeight,BufferedImage.TYPE_INT_RGB);
            g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = null;
            //ImageIO.write(bufferedImageesize, "png", new File(System.getProperty("user.dir")+"/mla/menu_graph.png"));
            //graphViewVB.getChildren().set(0, new ImageView(SwingFXUtils.toFXImage(bufferedImageesize, null)));
            
            intHeight = objFabric.getIntWeft();
            intLength = objFabric.getIntWarp();
            bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotFlipSideView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), intLength, intHeight);
            /*if((objFabric.getIntEPI()/objFabric.getIntPPI())<=0){
                intHeight = (int)(intLength*(objFabric.getIntPPI()/objFabric.getIntEPI())*zoomfactor*3);
                intLength = (int)(intLength*zoomfactor*3);
            }else{
                intLength = (int)(intHeight*(objFabric.getIntEPI()/objFabric.getIntPPI())*zoomfactor*3);
                intHeight = (int)(intHeight*zoomfactor*3);
            }*/
            intLength = 111; //(int)(((objConfiguration.getIntDPI()*objFabric.getIntWarp())/objFabric.getIntEPI())*zoomfactor*3);
            intHeight = 111; //(int)(((objConfiguration.getIntDPI()*objFabric.getIntWeft())/objFabric.getIntPPI())*zoomfactor*3);
            bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = null;            
            //ImageIO.write(bufferedImageesize, "png", new File(System.getProperty("user.dir")+"/mla/menu_flipside.png"));
            //flipSideViewVB.getChildren().set(0, new ImageView(SwingFXUtils.toFXImage(bufferedImageesize, null)));
            
            intHeight = objFabric.getIntWeft();
            intLength = objFabric.getIntWarp();
            bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotSwitchSideView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), intLength, intHeight);
            /*if((objFabric.getIntEPI()/objFabric.getIntPPI())<=0){
                intHeight = (int)(intLength*(objFabric.getIntPPI()/objFabric.getIntEPI())*zoomfactor*3);
                intLength = (int)(intLength*zoomfactor*3);
            }else{
                intLength = (int)(intHeight*(objFabric.getIntEPI()/objFabric.getIntPPI())*zoomfactor*3);
                intHeight = (int)(intHeight*zoomfactor*3);
            }*/
            intLength = 111; //(int)(((objConfiguration.getIntDPI()*objFabric.getIntWarp())/objFabric.getIntEPI())*zoomfactor*3);
            intHeight = 111; //(int)(((objConfiguration.getIntDPI()*objFabric.getIntWeft())/objFabric.getIntPPI())*zoomfactor*3);
            bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = null;            
            //ImageIO.write(bufferedImageesize, "png", new File(System.getProperty("user.dir")+"/mla/menu_switchside.png"));
            //switchSideViewVB.getChildren().set(0, new ImageView(SwingFXUtils.toFXImage(bufferedImageesize, null)));
            
            intHeight = objFabric.getIntWeft();
            intLength = objFabric.getIntWarp();
            bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotFrontSideView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), intLength, intHeight);
            /*if((objFabric.getIntEPI()/objFabric.getIntPPI())<=0){
                intHeight = (int)(intLength*(objFabric.getIntPPI()/objFabric.getIntEPI())*zoomfactor*3);
                intLength = (int)(intLength*zoomfactor*3);
            }else{
                intLength = (int)(intHeight*(objFabric.getIntEPI()/objFabric.getIntPPI())*zoomfactor*3);
                intHeight = (int)(intHeight*zoomfactor*3);
            }*/
            intLength = 111; //(int)(((objConfiguration.getIntDPI()*objFabric.getIntWarp())/objFabric.getIntEPI())*zoomfactor*3);
            intHeight = 111; //(int)(((objConfiguration.getIntDPI()*objFabric.getIntWeft())/objFabric.getIntPPI())*zoomfactor*3);
            bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = null;            
            //ImageIO.write(bufferedImageesize, "png", new File(System.getProperty("user.dir")+"/mla/menu_visulization.png"));
            //frontSideViewVB.getChildren().set(0, new ImageView(SwingFXUtils.toFXImage(bufferedImageesize, null)));
            
            intLength = objFabric.getIntWarp();
            intHeight = objFabric.getIntWeft();
            byte[][] newMatrix = objFabric.getFabricMatrix();
            List lstLines = new ArrayList();
            ArrayList<Byte> lstEntry = null;
            int lineCount = 0;
            for (int i = 0; i < intHeight; i++){
                lstEntry = new ArrayList();
                for (int j = 0; j < intLength; j++){
                    //add the first color on array
                    if(lstEntry.size()==0 && newMatrix[i][j]!=1)
                        lstEntry.add(newMatrix[i][j]);
                    //check for redudancy
                    else {                
                        if(!lstEntry.contains(newMatrix[i][j]) && newMatrix[i][j]!=1)
                            lstEntry.add(newMatrix[i][j]);
                    }
                }
                lstLines.add(lstEntry);
                lineCount+=lstEntry.size();
            }
            byte[][] baseMatrix = objArtworkAction.repeatMatrix(objFabric.getBaseWeaveMatrix(),lineCount,intLength);
            byte[][] fontMatrix = new byte[lineCount][intLength];
            lineCount=0;
            byte init = 0;
            for (int i = 0 ; i < intHeight; i++){
                lstEntry = (ArrayList)lstLines.get(i);
                for(int k=0; k<lstEntry.size(); k++, lineCount++){
                    init = (byte)lstEntry.get(k);
                    for (int j = 0; j < intLength; j++){
                        if(init==0){
                            fontMatrix[lineCount][j] = baseMatrix[i][j];
                        }else{
                            if(newMatrix[i][j]==init){
                                fontMatrix[lineCount][j] = init;
                            }else{
                                fontMatrix[lineCount][j] = (byte)1;
                            }
                        }   
                    }
                }
            }
            /*System.out.println("Row"+lineCount);
            for (int i = 0 ; i < lineCount; i++){
                for (int j = 0; j < intLength; j++){
                    System.err.print(" "+fontMatrix[i][j]);
                }
                System.err.println();
            }*/
            newMatrix = null;
            baseMatrix = null;
            intHeight = lineCount;
            bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotCrossSectionView(objFabric, fontMatrix, intLength, intHeight);
            intHeight = 111; //(int)(intHeight*zoomfactor*3);
            intLength = 111; //(int)(intLength*zoomfactor*3);
            bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = null;            
            //ImageIO.write(bufferedImageesize, "png", new File(System.getProperty("user.dir")+"/mla/menu_crosssectionfont.png"));
            //crossSectionFrontViewVB.getChildren().set(0, new ImageView(SwingFXUtils.toFXImage(bufferedImageesize, null)));
            
            intLength = objFabric.getIntWarp();
            intHeight = objFabric.getIntWeft();
            newMatrix = objFabric.getFabricMatrix();
            lstLines = new ArrayList();
            lstEntry = null;
            lineCount = 0;
            for (int i = 0; i < intHeight; i++){
                lstEntry = new ArrayList();
                for (int j = 0; j < intLength; j++){
                    //add the first color on array
                    if(lstEntry.size()==0 && newMatrix[i][j]!=1)
                        lstEntry.add(newMatrix[i][j]);
                    //check for redudancy
                    else {                
                        if(!(lstEntry.contains(newMatrix[i][j]))  && newMatrix[i][j]!=1)
                            lstEntry.add(newMatrix[i][j]);
                    }
                }
                lstLines.add(lstEntry);
                lineCount+=lstEntry.size();
            }
            baseMatrix = objArtworkAction.repeatMatrix(objFabric.getBaseWeaveMatrix(),lineCount,intLength);
            baseMatrix=objArtworkAction.invertMatrix(baseMatrix);
            fontMatrix = new byte[lineCount][intLength];
            lineCount=0;
            init = 0;
            for (int i = 0 ; i < intHeight; i++){
                lstEntry = (ArrayList)lstLines.get(i);
                for(int k=0; k<lstEntry.size(); k++, lineCount++){
                    init = (byte)lstEntry.get(k);
                    for (int j = 0; j < intLength; j++){
                        if(init==0)
                            fontMatrix[lineCount][j] = baseMatrix[i][j];
                        else{
                            if(newMatrix[i][j]==init){
                                fontMatrix[lineCount][j] = (byte)1;
                            }else{
                                fontMatrix[lineCount][j] = init;
                            }
                        }   
                    }
                }
            }
            
            intHeight = lineCount;
            bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotCrossSectionView(objFabric, fontMatrix, intLength, intHeight);
            intHeight = 111; //(int)(intHeight*zoomfactor*3);
            intLength = 111; //(int)(intLength*zoomfactor*3);
            bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = null;
            //ImageIO.write(bufferedImageesize, "png", new File(System.getProperty("user.dir")+"/mla/menu_crosssectionrear.png"));
            //crossSectionRearViewVB.getChildren().set(0, new ImageView(SwingFXUtils.toFXImage(bufferedImageesize, null)));
            
            //simulationViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/grid_view.png"));
            bufferedImageesize = null;
        } catch (SQLException ex) {
            Logger.getLogger(FabricView.class.getName()).log(Level.SEVERE, null, ex);
        //} catch (IOException ex) {
          //  Logger.getLogger(FabricView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void plotPreviewIcon(){
        try {
            final Stage dialogStage = new Stage();
            dialogStage.initOwner(fabricStage);
            dialogStage.initStyle(StageStyle.UTILITY);
            //dialogStage.initModality(Modality.WINDOW_MODAL);
            GridPane popup=new GridPane();
            popup.setId("popup");
            popup.setHgap(10);
            popup.setVgap(10);
            
            // Composite View item
            int intHeight = objFabric.getIntWeft();
            int intLength = objFabric.getIntWarp();
            BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotCompositeView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), intLength, intHeight);
            /*if((objFabric.getIntEPI()/objFabric.getIntPPI())<=0){
            intHeight = (int)(intLength*(objFabric.getIntPPI()/objFabric.getIntEPI())*zoomfactor);
            intLength = (int)(intLength*zoomfactor);
            }else{
            intLength = (int)(intHeight*(objFabric.getIntEPI()/objFabric.getIntPPI())*zoomfactor);
            intHeight = (int)(intHeight*zoomfactor);
            }*/
            intLength = 111;//(int)(((objConfiguration.getIntDPI()*objFabric.getIntWarp())/objFabric.getIntEPI())*zoomfactor);
            intHeight = 111;//(int)(((objConfiguration.getIntDPI()*objFabric.getIntWeft())/objFabric.getIntPPI())*zoomfactor);
            BufferedImage bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = null;
            //ImageIO.write(bufferedImageesize, "png", new File(System.getProperty("user.dir")+"/mla/menu_composite.png"));
            VBox compositeViewVB = new VBox(); 
            Label compositeViewLbl= new Label(objDictionaryAction.getWord("COMPOSITEVIEW"));
            compositeViewLbl.setWrapText(true);
            compositeViewLbl.autosize();
            compositeViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOMPOSITEVIEW")));
            //objConfiguration.getStrColour()+"/composite_view.png"
            compositeViewVB.getChildren().addAll(new ImageView(SwingFXUtils.toFXImage(bufferedImageesize, null)), compositeViewLbl);
            compositeViewVB.setPrefWidth(111);
            compositeViewVB.getStyleClass().addAll("myBox");        
            compositeViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONCOMPOSITEVIEW"));
                    plotViewActionMode = 1;
                    plotViewAction();
                }
            });
            popup.add(compositeViewVB, 0, 0);
            
            // Grid View item
            intHeight = objFabric.getIntWeft()*3;
            intLength = objFabric.getIntWarp()*3;
            bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotGridView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
            intLength = 111;//(int)(objFabric.getIntWarp()*zoomfactor*3);
            intHeight = 111;//(int)(objFabric.getIntWeft()*zoomfactor*3);
            bufferedImageesize = new BufferedImage(intLength, intHeight,BufferedImage.TYPE_INT_RGB);
            g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = null;
            //ImageIO.write(bufferedImageesize, "png", new File(System.getProperty("user.dir")+"/mla/menu_grid.png"));
            VBox gridViewVB = new VBox(); 
            Label gridViewLbl= new Label(objDictionaryAction.getWord("GRIDVIEW"));
            gridViewLbl.setWrapText(true);
            gridViewLbl.autosize();
            gridViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPGRIDVIEW")));
            //objConfiguration.getStrColour()+"/grid_view.png"
            gridViewVB.getChildren().addAll(new ImageView(SwingFXUtils.toFXImage(bufferedImageesize, null)), gridViewLbl);
            gridViewVB.setPrefWidth(111);
            gridViewVB.getStyleClass().addAll("myBox");
            gridViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONGRIDVIEW"));
                    plotViewActionMode = 2;
                    plotViewAction();
                }
            });
            popup.add(gridViewVB, 1, 0);
            
            // Graph View item
            intHeight=objFabric.getIntWeft();
            intLength=objFabric.getIntWarp();
            bufferedImage = new BufferedImage(objFabric.getIntWarp(), objFabric.getIntWeft(),BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            if(objFabric.getIntExtraWarp()>0 || objFabric.getIntExtraWeft()>0){
                if(objConfiguration.getBlnPunchCard()){
                    bufferedImage = objFabricAction.plotGraphJaquardMachineView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
                    intLength=objFabric.getIntWarp()*(objFabric.getIntExtraWeft()+2);
                } else{
                    bufferedImage = objFabricAction.plotGraphJaquardView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
                }
            }else{
                bufferedImage = objFabricAction.plotGraphDobbyView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
            }
            //================================ For Outer to be square ===============================
            String[] data = objFabric.getObjConfiguration().getStrGraphSize().split("x");
            bufferedImageesize = new BufferedImage((int)(intLength*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])),BufferedImage.TYPE_INT_RGB);
            g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, (int)(intLength*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])), null);
            g.setColor(java.awt.Color.BLACK);
            BasicStroke bs = new BasicStroke(2);
            g.setStroke(bs);
            for(int i = 0; i < objFabric.getIntWeft(); i++) {
                for(int j = 0; j < intLength; j++) {
                    if((j%Integer.parseInt(data[0]))==0){
                        bs = new BasicStroke(2);
                        g.setStroke(bs);
                    }else{
                        bs = new BasicStroke(1);
                        g.setStroke(bs);
                    }
                    g.drawLine((int)(j*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])), 0,  (int)(j*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])));
                }
                if((i%Integer.parseInt(data[1]))==0){
                    bs = new BasicStroke(2);
                    g.setStroke(bs);
                }else{
                    bs = new BasicStroke(1);
                    g.setStroke(bs);
                }
                g.drawLine(0, (int)(i*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])), (int)(intLength*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize())*Integer.parseInt(data[1]), (int)(i*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])));
            }
            g.dispose();
            bufferedImage = bufferedImageesize;
            intLength = 111;//(int)(objFabric.getIntWarp()*zoomfactor*3);
            intHeight = 111;//(int)(objFabric.getIntWeft()*zoomfactor*3);
            bufferedImageesize = new BufferedImage(intLength, intHeight,BufferedImage.TYPE_INT_RGB);
            g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = null;
            //ImageIO.write(bufferedImageesize, "png", new File(System.getProperty("user.dir")+"/mla/menu_graph.png"));
            VBox graphViewVB = new VBox(); 
            Label graphViewLbl= new Label(objDictionaryAction.getWord("GRAPHVIEW"));
            graphViewLbl.setWrapText(true);
            graphViewLbl.autosize();
            graphViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPGRAPHVIEW")));
            //objConfiguration.getStrColour()+"/graph_view.png"
            graphViewVB.getChildren().addAll(new ImageView(SwingFXUtils.toFXImage(bufferedImageesize, null)), graphViewLbl);
            graphViewVB.setPrefWidth(111);
            graphViewVB.getStyleClass().addAll("myBox");
            graphViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONGRAPHVIEW"));
                    plotViewActionMode = 3;
                    plotViewAction();
                }
            });
            popup.add(graphViewVB, 2, 0);
            
            // Visulization View item;
            intHeight = objFabric.getIntWeft();
            intLength = objFabric.getIntWarp();
            bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotFrontSideView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), intLength, intHeight);
            /*if((objFabric.getIntEPI()/objFabric.getIntPPI())<=0){
                intHeight = (int)(intLength*(objFabric.getIntPPI()/objFabric.getIntEPI())*zoomfactor*3);
                intLength = (int)(intLength*zoomfactor*3);
            }else{
                intLength = (int)(intHeight*(objFabric.getIntEPI()/objFabric.getIntPPI())*zoomfactor*3);
                intHeight = (int)(intHeight*zoomfactor*3);
            }*/
            intLength = 111; //(int)(((objConfiguration.getIntDPI()*objFabric.getIntWarp())/objFabric.getIntEPI())*zoomfactor*3);
            intHeight = 111; //(int)(((objConfiguration.getIntDPI()*objFabric.getIntWeft())/objFabric.getIntPPI())*zoomfactor*3);
            bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = null;            
            //ImageIO.write(bufferedImageesize, "png", new File(System.getProperty("user.dir")+"/mla/menu_visulization.png"));
            VBox frontSideViewVB = new VBox(); 
            Label frontSideViewLbl= new Label(objDictionaryAction.getWord("VISULIZATIONVIEW"));
            frontSideViewLbl.setWrapText(true);
            frontSideViewLbl.autosize();
            frontSideViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPVISULIZATIONVIEW")));
            //objConfiguration.getStrColour()+"/front_side.png"
            frontSideViewVB.getChildren().addAll(new ImageView(SwingFXUtils.toFXImage(bufferedImageesize, null)), frontSideViewLbl);
            frontSideViewVB.setPrefWidth(111);
            frontSideViewVB.getStyleClass().addAll("myBox");
            frontSideViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONVISULIZATIONVIEW"));
                    plotViewActionMode = 4;
                    plotViewAction();
                }
            });
            popup.add(frontSideViewVB, 3, 0);
            
            // Switch Side View item;
            intHeight = objFabric.getIntWeft();
            intLength = objFabric.getIntWarp();
            bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotFlipSideView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), intLength, intHeight);
            /*if((objFabric.getIntEPI()/objFabric.getIntPPI())<=0){
                intHeight = (int)(intLength*(objFabric.getIntPPI()/objFabric.getIntEPI())*zoomfactor*3);
                intLength = (int)(intLength*zoomfactor*3);
            }else{
                intLength = (int)(intHeight*(objFabric.getIntEPI()/objFabric.getIntPPI())*zoomfactor*3);
                intHeight = (int)(intHeight*zoomfactor*3);
            }*/
            intLength = 111; //(int)(((objConfiguration.getIntDPI()*objFabric.getIntWarp())/objFabric.getIntEPI())*zoomfactor*3);
            intHeight = 111; //(int)(((objConfiguration.getIntDPI()*objFabric.getIntWeft())/objFabric.getIntPPI())*zoomfactor*3);
            bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = null;            
            //ImageIO.write(bufferedImageesize, "png", new File(System.getProperty("user.dir")+"/mla/menu_flipside.png"));
            VBox flipSideViewVB = new VBox(); 
            Label flipSideViewLbl= new Label(objDictionaryAction.getWord("FLIPSIDEVIEW"));
            flipSideViewLbl.setWrapText(true);
            flipSideViewLbl.autosize();
            flipSideViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFLIPSIDEVIEW")));
            //objConfiguration.getStrColour()+"/flip_side.png"
            flipSideViewVB.getChildren().addAll(new ImageView(SwingFXUtils.toFXImage(bufferedImageesize, null)), flipSideViewLbl);
            flipSideViewVB.setPrefWidth(111);
            flipSideViewVB.getStyleClass().addAll("myBox");
            flipSideViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONFLIPSIDEVIEW"));
                    plotViewActionMode = 5;
                    plotViewAction();
                }
            });
            popup.add(flipSideViewVB, 0, 1);
            
            // Switch Side View item;
            intHeight = objFabric.getIntWeft();
            intLength = objFabric.getIntWarp();
            bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotSwitchSideView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), intLength, intHeight);
            /*if((objFabric.getIntEPI()/objFabric.getIntPPI())<=0){
                intHeight = (int)(intLength*(objFabric.getIntPPI()/objFabric.getIntEPI())*zoomfactor*3);
                intLength = (int)(intLength*zoomfactor*3);
            }else{
                intLength = (int)(intHeight*(objFabric.getIntEPI()/objFabric.getIntPPI())*zoomfactor*3);
                intHeight = (int)(intHeight*zoomfactor*3);
            }*/
            intLength = 111; //(int)(((objConfiguration.getIntDPI()*objFabric.getIntWarp())/objFabric.getIntEPI())*zoomfactor*3);
            intHeight = 111; //(int)(((objConfiguration.getIntDPI()*objFabric.getIntWeft())/objFabric.getIntPPI())*zoomfactor*3);
            bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = null;            
            //ImageIO.write(bufferedImageesize, "png", new File(System.getProperty("user.dir")+"/mla/menu_switchside.png"));
            VBox switchSideViewVB = new VBox(); 
            Label switchSideViewLbl= new Label(objDictionaryAction.getWord("SWITCHSIDEVIEW"));
            switchSideViewLbl.setWrapText(true);
            switchSideViewLbl.autosize();
            switchSideViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSWITCHSIDEVIEW")));
            //objConfiguration.getStrColour()+"/switch_side.png"
            switchSideViewVB.getChildren().addAll(new ImageView(SwingFXUtils.toFXImage(bufferedImageesize, null)), switchSideViewLbl);
            switchSideViewVB.setPrefWidth(111);
            switchSideViewVB.getStyleClass().addAll("myBox");
            switchSideViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONSWITCHSIDEVIEW"));
                    plotViewActionMode = 6;
                    plotViewAction();
                }
            });
            popup.add(switchSideViewVB, 1, 1);
                
            // cross Section View item;
            intLength = objFabric.getIntWarp();
            intHeight = objFabric.getIntWeft();
            byte[][] newMatrix = objFabric.getFabricMatrix();
            List lstLines = new ArrayList();
            ArrayList<Byte> lstEntry = null;
            int lineCount = 0;
            for (int i = 0; i < intHeight; i++){
                lstEntry = new ArrayList();
                for (int j = 0; j < intLength; j++){
                    //add the first color on array
                    if(lstEntry.size()==0 && newMatrix[i][j]!=1)
                        lstEntry.add(newMatrix[i][j]);
                    //check for redudancy
                    else {                
                        if(!lstEntry.contains(newMatrix[i][j]) && newMatrix[i][j]!=1)
                            lstEntry.add(newMatrix[i][j]);
                    }
                }
                lstLines.add(lstEntry);
                lineCount+=lstEntry.size();
            }
            byte[][] baseMatrix = objArtworkAction.repeatMatrix(objFabric.getBaseWeaveMatrix(),lineCount,intLength);
            byte[][] fontMatrix = new byte[lineCount][intLength];
            lineCount=0;
            byte init = 0;
            for (int i = 0 ; i < intHeight; i++){
                lstEntry = (ArrayList)lstLines.get(i);
                for(int k=0; k<lstEntry.size(); k++, lineCount++){
                    init = (byte)lstEntry.get(k);
                    for (int j = 0; j < intLength; j++){
                        if(init==0){
                            fontMatrix[lineCount][j] = baseMatrix[i][j];
                        }else{
                            if(newMatrix[i][j]==init){
                                fontMatrix[lineCount][j] = init;
                            }else{
                                fontMatrix[lineCount][j] = (byte)1;
                            }
                        }   
                    }
                }
            }
            
            newMatrix = null;
            baseMatrix = null;
            intHeight = lineCount;
            bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotCrossSectionView(objFabric, fontMatrix, intLength, intHeight);
            intHeight = 111; //(int)(intHeight*zoomfactor*3);
            intLength = 111; //(int)(intLength*zoomfactor*3);
            bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = null;            
            //ImageIO.write(bufferedImageesize, "png", new File(System.getProperty("user.dir")+"/mla/menu_crosssectionfont.png"));
            VBox crossSectionFrontViewVB = new VBox(); 
            Label crossSectionFrontViewLbl= new Label(objDictionaryAction.getWord("CROSSSECTIONFRONTVIEW"));
            crossSectionFrontViewLbl.setWrapText(true);
            crossSectionFrontViewLbl.autosize();
            crossSectionFrontViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCROSSSECTIONFRONTVIEW")));
            //objConfiguration.getStrColour()+"/front_cut.png"
            crossSectionFrontViewVB.getChildren().addAll(new ImageView(SwingFXUtils.toFXImage(bufferedImageesize, null)), crossSectionFrontViewLbl);
            crossSectionFrontViewVB.setPrefWidth(111);
            crossSectionFrontViewVB.getStyleClass().addAll("myBox");
            crossSectionFrontViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONCROSSSECTIONFRONTVIEW"));
                    plotViewActionMode = 7;
                    plotViewAction();
                }
            }); 
            popup.add(crossSectionFrontViewVB, 2, 1);
            
            // cross Section View item;
            intLength = objFabric.getIntWarp();
            intHeight = objFabric.getIntWeft();
            newMatrix = objFabric.getFabricMatrix();
            lstLines = new ArrayList();
            lstEntry = null;
            lineCount = 0;
            for (int i = 0; i < intHeight; i++){
                lstEntry = new ArrayList();
                for (int j = 0; j < intLength; j++){
                    //add the first color on array
                    if(lstEntry.size()==0 && newMatrix[i][j]!=1)
                        lstEntry.add(newMatrix[i][j]);
                    //check for redudancy
                    else {                
                        if(!(lstEntry.contains(newMatrix[i][j]))  && newMatrix[i][j]!=1)
                            lstEntry.add(newMatrix[i][j]);
                    }
                }
                lstLines.add(lstEntry);
                lineCount+=lstEntry.size();
            }
            baseMatrix = objArtworkAction.repeatMatrix(objFabric.getBaseWeaveMatrix(),lineCount,intLength);
            baseMatrix=objArtworkAction.invertMatrix(baseMatrix);
            fontMatrix = new byte[lineCount][intLength];
            lineCount=0;
            init = 0;
            for (int i = 0 ; i < intHeight; i++){
                lstEntry = (ArrayList)lstLines.get(i);
                for(int k=0; k<lstEntry.size(); k++, lineCount++){
                    init = (byte)lstEntry.get(k);
                    for (int j = 0; j < intLength; j++){
                        if(init==0)
                            fontMatrix[lineCount][j] = baseMatrix[i][j];
                        else{
                            if(newMatrix[i][j]==init){
                                fontMatrix[lineCount][j] = (byte)1;
                            }else{
                                fontMatrix[lineCount][j] = init;
                            }
                        }   
                    }
                }
            }
            intHeight = lineCount;
            bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotCrossSectionView(objFabric, fontMatrix, intLength, intHeight);
            intHeight = 111; //(int)(intHeight*zoomfactor*3);
            intLength = 111; //(int)(intLength*zoomfactor*3);
            bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = null;
            //ImageIO.write(bufferedImageesize, "png", new File(System.getProperty("user.dir")+"/mla/menu_crosssectionrear.png"));
            VBox crossSectionRearViewVB = new VBox(); 
            Label crossSectionRearViewLbl= new Label(objDictionaryAction.getWord("CROSSSECTIONREARVIEW"));
            crossSectionRearViewLbl.setWrapText(true);
            crossSectionRearViewLbl.autosize();
            crossSectionRearViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCROSSSECTIONREARVIEW")));
            //objConfiguration.getStrColour()+"/rear_cut.png"
            crossSectionRearViewVB.getChildren().addAll(new ImageView(SwingFXUtils.toFXImage(bufferedImageesize, null)), crossSectionRearViewLbl);
            crossSectionRearViewVB.setPrefWidth(111);
            crossSectionRearViewVB.getStyleClass().addAll("myBox");
            crossSectionRearViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONCROSSSECTIONREARVIEW"));
                    plotViewActionMode = 8;
                    plotViewAction();
                }
            });    
            popup.add(crossSectionRearViewVB, 3, 1);
            
            bufferedImageesize = null;
            System.gc();
            
            Button btnCancel = new Button(objDictionaryAction.getWord("CLOSE"));
            btnCancel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
            btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLOSE")));
            btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {  
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONCLOSE"));
                    dialogStage.close();
                }
            });
            popup.add(btnCancel, 3, 2);

            Scene scene = new Scene(popup);
            scene.getStylesheets().add(FabricView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
            dialogStage.setScene(scene);
            dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("VIEW"));
            dialogStage.showAndWait();
        } catch (SQLException ex) {
            Logger.getLogger(FabricView.class.getName()).log(Level.SEVERE, null, ex);
        //} catch (IOException ex) {
          //  Logger.getLogger(FabricView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /* context menu operation */
    public void editArtwork(){
        if(!locked){
            
            long cloneTime = 0L;
            long start = System.currentTimeMillis();
            Fabric objFabricCopy = new Fabric();
            cloneFabric(objFabricCopy);
            cloneTime += (System.currentTimeMillis() - start);
            //System.out.println("EditArtwork"+cloneTime);
            objFabricCopy.setStrFabricName("EditArtwork");
            objUR.doCommand("EditArtwork", objFabricCopy);
            
            ArtworkEditView objArtworkEditView= new ArtworkEditView(objFabric);
            if(objFabric.getStrArtworkID()==null||objFabric.getStrArtworkID().equalsIgnoreCase("null")){
                
                byte[][] baseWeaveMatrix = objFabric.getBaseWeaveMatrix();
                try {
                    objArtworkAction= new ArtworkAction();
                    objFabric.setFabricMatrix(objArtworkAction.repeatMatrix(baseWeaveMatrix,objFabric.getIntWeft(),objFabric.getIntWarp()));
                    objArtworkAction= new ArtworkAction();
                    objFabric.setReverseMatrix(objArtworkAction.invertMatrix(objFabric.getFabricMatrix()));
                } catch (SQLException ex) {
                    new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
                warpExtraYarn = null;
                objFabric.setWarpExtraYarn(warpExtraYarn);
                objFabric.setIntExtraWarp(objConfiguration.getIntExtraWarp());
                weftExtraYarn = null;
                objFabric.setWeftExtraYarn(weftExtraYarn);
                objFabric.setIntExtraWeft(objConfiguration.getIntExtraWeft());
            }else{
                objFabric.setIntWeft(objFabric.getArtworkMatrix().length);        
                objFabric.setIntWarp(objFabric.getArtworkMatrix()[0].length);
                objFabric.setIntHooks((int) Math.ceil(objFabric.getIntWarp()/(double)objFabric.getIntTPD()));
                objFabric.setFabricMatrix(objFabric.getArtworkMatrix());
                objConfiguration.setStrRecentArtwork(objFabric.getObjConfiguration().getStrRecentArtwork());
            }
            plotViewActionMode = 1;
            plotViewAction();
        }else{
            MessageView objMessageView = new MessageView("alert","Editing Locked !","Hello! We are very sorry,\n This feature is disable becuse you have pattern lock enabled.");
        }
    }
    
    public void editWeave(){
        if(!locked){
            
            long cloneTime = 0L;
            long start = System.currentTimeMillis();
            Fabric objFabricCopy = new Fabric();
            cloneFabric(objFabricCopy);
            cloneTime += (System.currentTimeMillis() - start);
            //System.out.println("EditWeavePattern"+cloneTime);
            objFabricCopy.setStrFabricName("EditWeavePattern");
            objUR.doCommand("EditWeavePattern", objFabricCopy);
            
            try {
                objWeave = new Weave();
                objWeave.setObjConfiguration(objFabric.getObjConfiguration());
                objWeave.setDesignMatrix(objFabric.getFabricMatrix());
                objWeave.setIntWeft(objFabric.getIntWeft());
                objWeave.setIntWarp(objFabric.getIntWarp());
                
                objWeaveR = new Weave();
                objWeave.setObjConfiguration(objFabric.getObjConfiguration());
                objWeaveR.setDesignMatrix(objFabric.getReverseMatrix());
                objWeaveR.setIntWeft(objFabric.getIntWeft());
                objWeaveR.setIntWarp(objFabric.getIntWarp());
                
                objWeaveAction = new WeaveAction();
                isEditingMode = true;
                isEditWeave = true;
                isEditGraph = false;
                populateLeftPane();
                plotEditWeave();
            } catch (SQLException ex) {
                new Logging("SEVERE",FabricView.class.getName(),"editWeave "+ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            } catch (Exception ex) {
                new Logging("SEVERE",FabricView.class.getName(),"editWeave "+ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }else{
            MessageView objMessageView = new MessageView("alert","Editing Locked !","Hello! We are very sorry,\n This feature is disable becuse you have pattern lock enabled.");
        }
    }

    public void editGraph(){
        if(!locked){
            
            long cloneTime = 0L;
            long start = System.currentTimeMillis();
            Fabric objFabricCopy = new Fabric();
            cloneFabric(objFabricCopy);
            cloneTime += (System.currentTimeMillis() - start);
            //System.out.println("EditGraph"+cloneTime);
            objFabricCopy.setStrFabricName("EditGraph");
            objUR.doCommand("EditGraph", objFabricCopy);
            
            try {
                lblStatus.setText(objDictionaryAction.getWord("GETGRAPHVIEW"));
                objFabricAction = new FabricAction();
                isEditingMode = true;
                isEditWeave = false;
                isEditGraph = true;
                populateLeftPane();
                plotEditGraph();
                lblStatus.setText(objDictionaryAction.getWord("GOTGRAPHVIEW"));
            } catch (SQLException ex) {
                new Logging("SEVERE",FabricView.class.getName(),"editGraph() : Error while editing garph view",ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }else{
            MessageView objMessageView = new MessageView("alert","Editing Locked !","Hello! We are very sorry,\n This feature is disable becuse you have pattern lock enabled.");
        }
    }
    
    
    public void plotEditWeave(){
        try {
            objFabric.setFabricMatrix(objWeave.getDesignMatrix());
            objFabric.setReverseMatrix(objWeaveR.getDesignMatrix());
            objFabric.setIntWeft(objWeave.getIntWeft());
            objFabric.setIntWarp(objWeave.getIntWarp());
            BufferedImage bufferedImage = new BufferedImage(objWeave.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize(), objWeave.getIntWarp()*objFabric.getObjConfiguration().getIntBoxSize(),BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            //bufferedImage = objFabricAction.plotFabricEditOLD(objWeave.getDesignMatrix(),objConfiguration.getIntBoxSize(),objWeave.getIntWarp(),objWeave.getIntWeft()); 
            bufferedImage = objFabricAction.plotFabricEdit(objFabric, objWeave.getDesignMatrix(),objFabric.getObjConfiguration().getIntBoxSize(),objWeave.getIntWarp(),objWeave.getIntWeft()); 
            int intLength = (int)(objFabric.getIntWarp()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize());
            int intHeight = (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize());
            
            BufferedImage bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = bufferedImageesize;
            bufferedImageesize = null;
            fabric.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            container.setContent(fabric);
            bufferedImage = null;
            System.gc();
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),"plotEditWeave "+ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    public void plotEditGraph(){
        try {
            /////////////// for error correction  //////////////////
            if(objConfiguration.getBlnCorrectGraph()){
                objFabricAction.plotGraphErrorCorrection(objFabric,objConfiguration.getIntFloatBind());
                //objConfiguration.setBlnCorrectGraph(false);
            }         
            
            int intHeight=objFabric.getIntWeft();
            int intLength=objFabric.getIntWarp();
            BufferedImage bufferedImage = new BufferedImage(objFabric.getIntWarp(), objFabric.getIntWeft(),BufferedImage.TYPE_INT_RGB);
            if(objFabric.getIntExtraWarp()>0 || objFabric.getIntExtraWeft()>0){
                bufferedImage = objFabricAction.plotGraphJaquardView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
            }else{
                bufferedImage = objFabricAction.plotGraphDobbyView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
            }
   
            //================================ For Outer to be square ===============================
            String[] data = objFabric.getObjConfiguration().getStrGraphSize().split("x");
            BufferedImage bufferedImageesize = new BufferedImage((int)(intLength*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])/graphFactor), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/graphFactor),BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, (int)(intLength*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])/graphFactor), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/graphFactor), null);
            g.setColor(java.awt.Color.BLACK);
            BasicStroke bs = new BasicStroke(2);
            g.setStroke(bs);

            for(int i = 0; i < objFabric.getIntWeft(); i++) {
                for(int j = 0; j < intLength; j++) {
                    if((j%Integer.parseInt(data[0]))==0){
                        bs = new BasicStroke(2);
                        g.setStroke(bs);
                    }else{
                        bs = new BasicStroke(1);
                        g.setStroke(bs);
                    }
                    g.drawLine((int)(j*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])/graphFactor), 0,  (int)(j*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])/graphFactor), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/graphFactor));
                }
                if((i%Integer.parseInt(data[1]))==0){
                    bs = new BasicStroke(2);
                    g.setStroke(bs);
                }else{
                    bs = new BasicStroke(1);
                    g.setStroke(bs);
                }
                g.drawLine(0, (int)(i*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/graphFactor), (int)(intLength*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize())*Integer.parseInt(data[1])/graphFactor, (int)(i*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/graphFactor));
            }

            /////////////// for error box  //////////////////
            if(objConfiguration.getBlnErrorGraph()){
                byte[][] errorMatrix;
                errorMatrix = objFabricAction.plotGraphErrorMatrix(objFabric,objConfiguration.getIntFloatBind());
                BufferedImage errorImage = ImageIO.read(new File(System.getProperty("user.dir")+"/mla/error.png"));
                for(int x = 0, p = 0; x < objFabric.getIntWeft(); x++,p+=(Integer.parseInt(data[0])*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()/graphFactor)) {
                    for(int y = 0, q = 0; y < objFabric.getIntWarp(); y++,q+=(Integer.parseInt(data[1])*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()/graphFactor)) {
                        if(errorMatrix[x][y]==-1)
                            g.drawImage(errorImage, q, p, (int)(Integer.parseInt(data[1])*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()/graphFactor), (int)(Integer.parseInt(data[0])*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()/graphFactor), null);  
                    }
                }
                errorMatrix = null;
            }
    
            g.dispose();
            bufferedImage = null;
            
            fabric.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));
            container.setContent(fabric);
            bufferedImageesize = null;
            System.gc();
        } catch (Exception ex) {
            new Logging("SEVERE",FabricView.class.getName(),"plotEditGraph "+ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public void editPattern(){
        if(!locked){
            
            long cloneTime = 0L;
            long start = System.currentTimeMillis();
            Fabric objFabricCopy = new Fabric();
            cloneFabric(objFabricCopy);
            cloneTime += (System.currentTimeMillis() - start);
            //System.out.println("EditThreadSequence"+cloneTime);
            objFabricCopy.setStrFabricName("EditThreadSequence");
            objUR.doCommand("EditThreadSequence", objFabricCopy);
            try {
                PatternView objPatternView = new PatternView(objFabric);
                warpYarn = objFabric.getWarpYarn();
                weftYarn = objFabric.getWeftYarn();
                threadPaletes = objFabric.getThreadPaletes();
                plotViewAction();
            } catch (Exception ex) {
                Logger.getLogger(WeaveView.class.getName()).log(Level.SEVERE, null, ex);
            
                new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }else{
            MessageView objMessageView = new MessageView("alert","Editing Locked !","Hello! We are very sorry,\n This feature is disable becuse you have pattern lock enabled.");
        }
    }
    
    public void editDensity(){
        if(!locked){
            
            long cloneTime = 0L;
            long start = System.currentTimeMillis();
            Fabric objFabricCopy = new Fabric();
            cloneFabric(objFabricCopy);
            cloneTime += (System.currentTimeMillis() - start);
            //System.out.println("EditDensity"+cloneTime);
            objFabricCopy.setStrFabricName("EditDensity");
            objUR.doCommand("EditDensity", objFabricCopy);
            DensityView objDensityView = new DensityView(objFabric);
            objFabric.setIntHPI((int) Math.ceil(objFabric.getIntEPI()/(double)objFabric.getIntTPD()));
            objFabric.getObjConfiguration().setStrGraphSize(12+"x"+(int)((12*objFabric.getIntPPI())/objFabric.getIntEPI()));
            plotViewAction();
        }else{
            MessageView objMessageView = new MessageView("alert","Editing Locked !","Hello! We are very sorry,\n This feature is disable becuse you have pattern lock enabled.");
        }
    }
    
    public void editYarn(){
        if(!locked){
            
            long cloneTime = 0L;
            long start = System.currentTimeMillis();
            Fabric objFabricCopy = new Fabric();
            cloneFabric(objFabricCopy);
            cloneTime += (System.currentTimeMillis() - start);
            //System.out.println("EditYarn"+cloneTime);
            objFabricCopy.setStrFabricName("EditYarn");
            objUR.doCommand("EditYarn", objFabricCopy);
            
            objFabric.getObjConfiguration().setWarpYarn(objFabric.getWarpYarn());
            objFabric.getObjConfiguration().setWeftYarn(objFabric.getWeftYarn());
            objFabric.getObjConfiguration().setWarpExtraYarn(objFabric.getWarpExtraYarn());
            objFabric.getObjConfiguration().setWeftExtraYarn(objFabric.getWeftExtraYarn());
            objFabric.getObjConfiguration().setIntExtraWeft(objFabric.getIntExtraWeft());
            objFabric.getObjConfiguration().setIntExtraWarp(objFabric.getIntExtraWarp());
            objFabric.getObjConfiguration().setColourPalette(objFabric.getThreadPaletes());
            try{
            YarnView objYarnView = new YarnView(objFabric.getObjConfiguration());
            }catch(Exception ex){
                Logger.getLogger(WeaveView.class.getName()).log(Level.SEVERE, null, ex);
            }
            objFabric.setWarpYarn(objFabric.getObjConfiguration().getWarpYarn());
            objFabric.setWeftYarn(objFabric.getObjConfiguration().getWeftYarn());
            objFabric.setWarpExtraYarn(objFabric.getObjConfiguration().getWarpExtraYarn());
            objFabric.setWeftExtraYarn(objFabric.getObjConfiguration().getWeftExtraYarn());
            objFabric.setIntExtraWeft(objFabric.getObjConfiguration().getIntExtraWeft());
            objFabric.setIntExtraWarp(objFabric.getObjConfiguration().getIntExtraWarp());
            objFabric.setThreadPaletes(objFabric.getObjConfiguration().getColourPalette());
            
            plotViewAction();
        }else{
            MessageView objMessageView = new MessageView("alert","Editing Locked !","Hello! We are very sorry,\n This feature is disable becuse you have pattern lock enabled.");
        }
    }
    public void switchColor(){
        if(!locked){
            
            long cloneTime = 0L;
            long start = System.currentTimeMillis();
            Fabric objFabricCopy = new Fabric();
            cloneFabric(objFabricCopy);
            cloneTime += (System.currentTimeMillis() - start);
            //System.out.println("EditSwitchColor"+cloneTime);
            objFabricCopy.setStrFabricName("EditSwitchColor");
            objUR.doCommand("EditSwitchColor", objFabricCopy);
            threadPaletes = objFabric.getThreadPaletes(); //new String[]{"161616", "B40431", "B4045F", "B40486", "B404AE", "8904B1", "5F04B4", "3104B4", "0404B4", "0431B4", "045FB4", "0489B1", "04B4AE", "04B486", "04B45F", "04B431", "04B404", "31B404", "5FB404", "86B404", "AEB404", "B18904", "B45F04", "B43104", "B40404", "FFFFFF", "BBA677", "B40431", "B4045F", "B40486", "B404AE", "8904B1", "5F04B4", "3104B4", "0404B4", "0431B4", "045FB4", "0489B1", "04B4AE", "04B486", "04B45F", "04B431", "04B404", "31B404", "5FB404", "86B404", "AEB404", "B18904", "B45F04", "B43104", "B40404", "FFFFFF"}; 
            objFabric.getThreadPaletes();
            String temp;
            for(int i=0; i<threadPaletes.length/2;i++){
                temp = threadPaletes[i];
                threadPaletes[i] = threadPaletes[i+26];
                threadPaletes[i+26] = temp;
            }
            
            weftYarn = objFabric.getWeftYarn();
            warpYarn = objFabric.getWarpYarn();
            char character;
            for(int i=0; i<warpYarn.length;i++){
                //65 - 96
                character = warpYarn[i].getStrYarnSymbol().trim().toUpperCase().charAt(0); // This gives the character 'a'
                int ascii = (int) character; 
                warpYarn[i].setStrYarnColor(threadPaletes[ascii-65]);
            }
            for(int i=0; i<weftYarn.length;i++){
                //97 - 113
                character = weftYarn[i].getStrYarnSymbol().trim().toLowerCase().charAt(0); // This gives the character 'a'
                int ascii = (int) character; 
                weftYarn[i].setStrYarnColor(threadPaletes[ascii-71]);
            }
            objFabric.setWarpYarn(warpYarn);
            objFabric.setWeftYarn(weftYarn);
            objFabric.setThreadPaletes(threadPaletes);
            plotViewAction();
        }else{
            MessageView objMessageView = new MessageView("alert","Editing Locked !","Hello! We are very sorry,\n This feature is disable becuse you have pattern lock enabled.");
        }
    }
    
    public void switchThreadSequence(){
        if(!locked){
            
            long cloneTime = 0L;
            long start = System.currentTimeMillis();
            Fabric objFabricCopy = new Fabric();
            cloneFabric(objFabricCopy);
            cloneTime += (System.currentTimeMillis() - start);
            //System.out.println("EditThreadSequence"+cloneTime);
            objFabricCopy.setStrFabricName("EditThreadSequence");
            objUR.doCommand("EditThreadSequence", objFabricCopy);
            warpYarn = objFabric.getWeftYarn();
            weftYarn = objFabric.getWarpYarn();
            for(int i=0; i<warpYarn.length;i++){
                warpYarn[i].setStrYarnType("Warp");
                warpYarn[i].setStrYarnName("Warp"+i);
                warpYarn[i].setStrYarnSymbol(warpYarn[i].getStrYarnSymbol().toUpperCase());
            }
            for(int i=0; i<weftYarn.length;i++){
                weftYarn[i].setStrYarnType("Weft");
                weftYarn[i].setStrYarnName("Weft"+i);
                weftYarn[i].setStrYarnSymbol(weftYarn[i].getStrYarnSymbol().toLowerCase());
            }
            objFabric.setWarpYarn(warpYarn);
            objFabric.setWeftYarn(weftYarn);
            String pid = objFabric.getStrWarpPatternID();
            objFabric.setStrWarpPatternID(objFabric.getStrWeftPatternID());
            objFabric.setStrWeftPatternID(pid);
            threadPaletes = objFabric.getThreadPaletes();
            plotViewAction();
        }else{
            MessageView objMessageView = new MessageView("alert","Editing Locked !","Hello! We are very sorry,\n This feature is disable becuse you have pattern lock enabled.");
        }
    }
    
    private void runDeviceOLD(){     
        try {
            lblStatus.setText(objDictionaryAction.getWord("GETGRAPHVIEW"));
            BufferedImage bufferedImage = new BufferedImage(objFabric.getIntWarp(), objFabric.getIntWeft(),BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            
            if(objFabric.getIntExtraWarp()>0 || objFabric.getIntExtraWeft()>0){
                if(objConfiguration.getBlnPunchCard()){
                    bufferedImage = objFabricAction.plotGraphJaquardMachineView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
                } else{
                    bufferedImage = objFabricAction.plotGraphJaquardView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
                }
            }else{
                bufferedImage = objFabricAction.plotGraphDobbyView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
            }            
            FileChooser fileChoser=new FileChooser();
            fileChoser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTBMP")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
            FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP (.bmp)", "*.bmp");
            fileChoser.getExtensionFilters().add(extFilterBMP);
            File file=fileChoser.showSaveDialog(fabricStage);
            if(file==null)
                return;
            else
                fabricStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+file.getAbsoluteFile().getName()+"]");
            if (!file.getName().endsWith("bmp")) 
                file = new File(file.getPath() +".bmp");
            ImageIO.write(bufferedImage, "BMP", file);
            bufferedImage = null;
            
            ArrayList<File> filesToZip=new ArrayList<>();
            filesToZip.add(file);
            String zipFilePath=file.getAbsolutePath()+".zip";
            String passwordToZip = file.getName();
            new EncryptZip(zipFilePath, filesToZip, passwordToZip);
            file.delete();
                
            lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
        
            final Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            dialogStage.setIconified(false);
            dialogStage.setFullScreen(false);
            dialogStage.setTitle(objDictionaryAction.getWord("ALERT"));
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 300, 100, Color.WHITE);
            scene.getStylesheets().add(FabricView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
            final GridPane popup=new GridPane();
            popup.setId("popup");
            popup.setHgap(5);
            popup.setVgap(5);
            popup.setPadding(new Insets(25, 25, 25, 25));
            Label lblAlert = new Label(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
            lblAlert.setStyle("-fx-wrap-text:true;");
            lblAlert.setPrefWidth(300);
            popup.add(lblAlert, 0, 0, 2, 1);
            popup.add(new Label(objDictionaryAction.getWord("SELECT")+" "+objDictionaryAction.getWord("PUNCHAPPLICATION")), 0, 1);
            ComboBox deviceCB = new ComboBox();
            UtilityAction objUtilityAction = new UtilityAction();
            Device objDevice = new Device(null, "Punching M/C", null, null);
            objDevice.setObjConfiguration(objConfiguration);
            Device[] devices = objUtilityAction.getDevices(objDevice);
            deviceCB.getItems().add("Select");
            for(int i=0; i<devices.length; i++)
                deviceCB.getItems().add(devices[i].getStrDeviceName());
            deviceCB.setValue("Select");
            popup.add(deviceCB, 1, 1);
            deviceCB.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                    try {
                        UtilityAction objUtilityAction = new UtilityAction();
                        Device objDevice = new Device(null, null, t1, null);
                        objDevice.setObjConfiguration(objConfiguration);
                        objUtilityAction.getDevice(objDevice);
                        Runtime rt = Runtime.getRuntime();
                        if(objProcess_ProcessBuilder!=null)
                                objProcess_ProcessBuilder.destroy();
                        //objProcess_ProcessBuilder =new ProcessBuilder(objDevice.getDevicePath()).start();
                        //objProcess_ProcessBuilder=new ProcessBuilder(objDevice.getDevicePath(),filePath).start();
                        String exeDir=objDevice.getStrDevicePath().substring(0, objDevice.getStrDevicePath().lastIndexOf("\\")+1);
                        String exeName=objDevice.getStrDevicePath().substring(objDevice.getStrDevicePath().lastIndexOf("\\")+1,objDevice.getStrDevicePath().length());
                        File projDir = new File(exeDir);
                        Runtime.getRuntime().exec(new String[]{"cmd","/c",exeName}, null, projDir);
                    } catch (SQLException ex) {
                        new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    } catch (Exception ex) {
                        new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    }
                    dialogStage.close();
                    System.gc();
                }
            });
            Button btnNo = new Button(objDictionaryAction.getWord("CANCEL"));
            btnNo.setPrefWidth(50);
            btnNo.setId("btnNo");
            btnNo.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    dialogStage.close();
                    System.gc();
                }
            });
            popup.add(btnNo, 0, 2, 2, 1);
            root.setCenter(popup);
            dialogStage.setScene(scene);
            dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("SELECTTO")+" "+objDictionaryAction.getWord("RUN"));
            dialogStage.showAndWait();
            lblStatus.setText(objDictionaryAction.getWord("ACTIONPUNCHAPPLICATION"));
        } catch (IOException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }                  
    }
    
    private void runDevice(){     
        try {
            final Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            dialogStage.setIconified(false);
            dialogStage.setFullScreen(false);
            dialogStage.setTitle(objDictionaryAction.getWord("ALERT"));
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 300, 220, Color.WHITE);
            scene.getStylesheets().add(FabricView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
            final GridPane popup=new GridPane();
            popup.setId("popup");
            popup.setHgap(5);
            popup.setVgap(5);
            popup.setPadding(new Insets(25, 25, 25, 25));
            
            popup.add(new Label(objDictionaryAction.getWord("SELECT")+" "+objDictionaryAction.getWord("PUNCHTYPE")), 0, 0);
            final ComboBox graphCB = new ComboBox();
            graphCB.getItems().add("Single page-Single graph");
            graphCB.setValue("Single page-Single graph");
            if(objFabric.getIntExtraWeft()>0)
                graphCB.getItems().addAll("Multi page- Single graph","Multi page- Multi graph","Superimposed Single graph","Superimposed single base graph");
            popup.add(graphCB, 1, 0);
            
            popup.add(new Label(objDictionaryAction.getWord("SELECT")+" "+objDictionaryAction.getWord("PUNCHAPPLICATION")), 0, 1);
            final ComboBox deviceCB = new ComboBox();
            deviceCB.setValue("Select");
            
            UtilityAction objUtilityAction = new UtilityAction();
            Device objDevice = new Device(null, "Punching M/C", null, null);
            objDevice.setObjConfiguration(objConfiguration);
            Device[] devices = objUtilityAction.getDevices(objDevice);
            deviceCB.getItems().add("Select");
            for(int i=0; i<devices.length; i++)
                deviceCB.getItems().add(devices[i].getStrDeviceName());
            deviceCB.setValue("Select");
            popup.add(deviceCB, 1, 1);
            
            Label lblPassword=new Label(objDictionaryAction.getWord("SERVICEPASSWORD"));
            lblPassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
            popup.add(lblPassword, 0, 2);
            final PasswordField passPF =new PasswordField();
            passPF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
            passPF.setPromptText(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
            popup.add(passPF, 1, 2);
            final Label lblAlert=new Label("");
            lblAlert.setStyle("-fx-wrap-text:true;");
            lblAlert.setPrefWidth(250);
            popup.add(lblAlert, 0, 4, 2, 1);
            
            Button btnYes = new Button(objDictionaryAction.getWord("RUN"));
            btnYes.setPrefWidth(50);
            btnYes.setId("btnYes");
            btnYes.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    if(Security.SecurePassword(passPF.getText(), objConfiguration.getObjUser().getStrUsername()).equals(objConfiguration.getObjUser().getStrAppPassword())){
                        try {
                            if(graphCB.getValue().toString().equalsIgnoreCase("Multi page- Single graph")){
                                getMultiPageSingleGraphMC();
                            } else if(graphCB.getValue().toString().equalsIgnoreCase("Multi page- Multi graph")){
                                getMultiPageMultiGraphMC();
                            } else if(graphCB.getValue().toString().equalsIgnoreCase("Superimposed Single graph")){
                                getSuperGraphMC();
                            } else if(graphCB.getValue().toString().equalsIgnoreCase("Superimposed single base graph")){
                                getSuperBaseGraphMC();
                            }else { //if(graphCB.getValue().toString().equalsIgnoreCase("Single page-Single graph"))
                                getSinglePageSingleGraphMC();
                            }
                            UtilityAction objUtilityAction = new UtilityAction();
                            Device objDevice = new Device(null, null, deviceCB.getValue().toString(), null);
                            objDevice.setObjConfiguration(objConfiguration);
                            objUtilityAction.getDevice(objDevice);
                            Runtime rt = Runtime.getRuntime();
                            if(objProcess_ProcessBuilder!=null)
                                objProcess_ProcessBuilder.destroy();
                            //objProcess_ProcessBuilder =new ProcessBuilder(objDevice.getDevicePath()).start();
                            //objProcess_ProcessBuilder=new ProcessBuilder(objDevice.getDevicePath(),filePath).start();
                            String exeDir=objDevice.getStrDevicePath().substring(0, objDevice.getStrDevicePath().lastIndexOf("\\")+1);
                            String exeName=objDevice.getStrDevicePath().substring(objDevice.getStrDevicePath().lastIndexOf("\\")+1,objDevice.getStrDevicePath().length());
                            File projDir = new File(exeDir);
                            Runtime.getRuntime().exec(new String[]{"cmd","/c",exeName}, null, projDir);
                        } catch (SQLException ex) {
                            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        } catch (Exception ex) {
                            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                        dialogStage.close();
                        System.gc();
                    }
                    else if(passPF.getText().length()==0){
                        lblAlert.setText(objDictionaryAction.getWord("NOSERVICEPASSWORD"));
                    }
                    else{
                        lblAlert.setText(objDictionaryAction.getWord("INVALIDSERVICEPASSWORD"));
                    }
                }
            });
            popup.add(btnYes, 0, 3);
            
            Button btnNo = new Button(objDictionaryAction.getWord("CANCEL"));
            btnNo.setPrefWidth(50);
            btnNo.setId("btnNo");
            btnNo.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    dialogStage.close();
                    System.gc();
                }
            });
            popup.add(btnNo, 1, 3);
            
            root.setCenter(popup);
            dialogStage.setScene(scene);
            dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("SELECTTO")+" "+objDictionaryAction.getWord("RUN")+" "+objDictionaryAction.getWord("PUNCHAPPLICATION"));
            dialogStage.showAndWait();
            
            lblStatus.setText(objDictionaryAction.getWord("ACTIONPUNCHAPPLICATION"));
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }                  
    }
    
	public void goToGarmentViewer(){
        if(objFabric.getStrFabricID()!=null){
            if(objFabric.getStrClothType()!=null){
                if(objFabric.getStrClothType().equalsIgnoreCase("Body")){
                    objConfiguration.mapRecentFabric.put("ceBody", "ceBody");
                    objConfiguration.mapRecentFabric.put("Body", objFabric.getStrFabricID());
                }
                else if(objFabric.getStrClothType().equalsIgnoreCase("Blouse")){
                    if(objConfiguration.mapRecentFabric.get("ceBlouse")!=null)
                        objConfiguration.mapRecentFabric.put("ceBlouse", "ceBlouse");
                    objConfiguration.mapRecentFabric.put("Blouse", objFabric.getStrFabricID());
                }
                else if(objFabric.getStrClothType().equalsIgnoreCase("Skart")){
                    if(objConfiguration.mapRecentFabric.get("ceSkart")!=null)
                        objConfiguration.mapRecentFabric.put("ceSkart", "ceSkart");
                    objConfiguration.mapRecentFabric.put("Skart", objFabric.getStrFabricID());
                }
                else if(objFabric.getStrClothType().equalsIgnoreCase("Border")){
                    if(objConfiguration.mapRecentFabric.get("Left Border")!=null)
                        objConfiguration.mapRecentFabric.put("Left Border", "Left Border");
                    if(objConfiguration.mapRecentFabric.get("Right Border")!=null)
                        objConfiguration.mapRecentFabric.put("Right Border", "Right Border");
                    objConfiguration.mapRecentFabric.put("Border", objFabric.getStrFabricID());
                }
                else if(objFabric.getStrClothType().equalsIgnoreCase("Cross Border")){
                    if(objConfiguration.mapRecentFabric.get("Left Cross Border")!=null)
                        objConfiguration.mapRecentFabric.put("Left Cross Border", "Left Cross Border");
                    if(objConfiguration.mapRecentFabric.get("Right Cross Border")!=null)
                        objConfiguration.mapRecentFabric.put("Right Cross Border", "Right Cross Border");
                    if(objConfiguration.mapRecentFabric.get("Rightside Left Cross Border")!=null)
                        objConfiguration.mapRecentFabric.put("Rightside Left Cross Border", "Rightside Left Cross Border");
                    if(objConfiguration.mapRecentFabric.get("Rightside Right Cross Border")!=null)
                        objConfiguration.mapRecentFabric.put("Rightside Right Cross Border", "Rightside Right Cross Border");
                    objConfiguration.mapRecentFabric.put("Cross Border", objFabric.getStrFabricID());
                }
                else if(objFabric.getStrClothType().equalsIgnoreCase("Palu")){
                    if(objConfiguration.mapRecentFabric.get("Left Pallu")!=null)
                        objConfiguration.mapRecentFabric.put("Left Pallu", "Left Pallu");
                    if(objConfiguration.mapRecentFabric.get("Right Pallu")!=null)
                        objConfiguration.mapRecentFabric.put("Right Pallu", "Right Pallu");
                    objConfiguration.mapRecentFabric.put("Palu", objFabric.getStrFabricID());
                }
                fabricStage.close();
                ClothView objClothView=new ClothView(objConfiguration);
            }
        }
        else{
            new MessageView("error", objDictionaryAction.getWord("NOVALUE"), objDictionaryAction.getWord("NOVALUE"));
            lblStatus.setText(objDictionaryAction.getWord("NOVALUE"));
        }
    }
    public void getSinglePageSingleGraphMC(){
        try {
            lblStatus.setText(objDictionaryAction.getWord("GETGRAPHVIEW"));
            BufferedImage bufferedImage = new BufferedImage(objFabric.getIntWeft(), objFabric.getIntWarp(),BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            
            if(objFabric.getIntExtraWarp()>0 || objFabric.getIntExtraWeft()>0){
                if(objConfiguration.getBlnPunchCard()){
                    bufferedImage = objFabricAction.plotGraphJaquardMachineView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
                } else{
                    bufferedImage = objFabricAction.plotGraphJaquardView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
                }
            }else{
                bufferedImage = objFabricAction.plotGraphDobbyView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
            }
            FileChooser fileChoser=new FileChooser();
            fileChoser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTBMP")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
            FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP (.bmp)", "*.bmp");
            fileChoser.getExtensionFilters().add(extFilterBMP);
            File file=fileChoser.showSaveDialog(fabricStage);
            if(file==null)
                return;
            else
                fabricStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+file.getAbsoluteFile().getName()+"]");
            if (!file.getName().endsWith("bmp"))
                file = new File(file.getPath() +".bmp");
            ImageIO.write(bufferedImage, "BMP", file);
            bufferedImage = null;
            
            ArrayList<File> filesToZip=new ArrayList<>();
            filesToZip.add(file);
            String zipFilePath=file.getAbsolutePath()+".zip";
            String passwordToZip = file.getName();
            new EncryptZip(zipFilePath, filesToZip, passwordToZip);
            file.delete();
                
            lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
        } catch (IOException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public void getMultiPageSingleGraphMC(){
        try {
            lblStatus.setText(objDictionaryAction.getWord("GETGRAPHVIEW"));
            BufferedImage bufferedImage = new BufferedImage(objFabric.getIntWeft(), objFabric.getIntWarp(),BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotGraphJaquardMachineView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
            
            FileChooser fileChoser=new FileChooser();
            fileChoser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTBMP")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
            FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP (.bmp)", "*.bmp");
            fileChoser.getExtensionFilters().add(extFilterBMP);
            File file=fileChoser.showSaveDialog(fabricStage);
            if(file==null)
                return;
            else
                fabricStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+file.getAbsoluteFile().getName()+"]");
            if (!file.getName().endsWith("bmp"))
                file = new File(file.getPath() +".bmp");
            ImageIO.write(bufferedImage, "BMP", file);
            bufferedImage = null;
            
            ArrayList<File> filesToZip=new ArrayList<>();
            filesToZip.add(file);
            String zipFilePath=file.getAbsolutePath()+".zip";
            String passwordToZip = file.getName();
            new EncryptZip(zipFilePath, filesToZip, passwordToZip);
            file.delete();

            lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
        } catch (IOException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public void getMultiPageMultiGraphMC(){
        try {
            DirectoryChooser directoryChooser=new DirectoryChooser();
            fabricStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTBMP")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
            File selectedDirectory = directoryChooser.showDialog(fabricStage);
            if(selectedDirectory == null)
                return;
            else
                fabricStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+selectedDirectory.getAbsolutePath()+"]");
            
            lblStatus.setText(objDictionaryAction.getWord("GETGRAPHVIEW"));
            BufferedImage bufferedImage = new BufferedImage(objFabric.getIntWarp(), objFabric.getIntWeft(),BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            String path = selectedDirectory.getPath()+"\\Fabric_"+currentDate+"\\";
            File file = new File(path);
            
            ArrayList<File> filesToZip=new ArrayList<>();
            
            if (!file.exists()) {
                if (!file.mkdir())
                    path = selectedDirectory.getPath();
            }
            for(int i=0; i<objFabric.getIntExtraWeft(); i++){
                bufferedImage = objFabricAction.plotJaquardGraphView(objFabric, i);
                file = new File(path + i +".bmp");
                ImageIO.write(bufferedImage, "BMP", file);
                filesToZip.add(file);
            }
            String zipFilePath=selectedDirectory.getPath()+"\\Fabric_"+currentDate+".zip";
            String passwordToZip = "Fabric_"+currentDate;
            new EncryptZip(zipFilePath, filesToZip, passwordToZip);
            
            //delete folder recursively
            recursiveDelete(new File(path));
            bufferedImage = null;
            lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+path);
        } catch (IOException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
public static void recursiveDelete(File file) {
        //to end the recursive loop
        if (!file.exists())
            return;
         
        //if directory, go inside and call recursively
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                //call recursively
                recursiveDelete(f);
            }
        }
        //call delete to delete files and empty directory
        file.delete();
        System.out.println("Deleted file/folder: "+file.getAbsolutePath());
    }

    public void getSuperGraphMC(){
        try {
            byte[][] newMatrix = objFabric.getFabricMatrix();
            List lstLines = new ArrayList();
            ArrayList<Byte> lstEntry = null;
            int lineCount = 0;
            for (int i = 0; i < objFabric.getIntWeft(); i++){
                lstEntry = new ArrayList();
                for (int j = 0; j < objFabric.getIntWarp(); j++){
                    //add the first color on array
                    if(lstEntry.size()==0 && newMatrix[i][j]!=1)
                        lstEntry.add(newMatrix[i][j]);
                    //check for redudancy
                    else {                
                        if(!lstEntry.contains(newMatrix[i][j]) && newMatrix[i][j]!=1)
                            lstEntry.add(newMatrix[i][j]);
                    }
                }
                lstLines.add(lstEntry);
                lineCount+=lstEntry.size();
            }
            byte[][] baseMatrix = objArtworkAction.repeatMatrix(objFabric.getBaseWeaveMatrix(),lineCount,objFabric.getIntWarp());
            byte[][] fontMatrix = new byte[lineCount][objFabric.getIntWarp()];
            lineCount=0;
            byte init = 0;
            for (int i = 0 ; i < objFabric.getIntWeft(); i++){
                lstEntry = (ArrayList)lstLines.get(i);
                for(int k=0; k<lstEntry.size(); k++, lineCount++){
                    init = (byte)lstEntry.get(k);
                    for (int j = 0; j < objFabric.getIntWarp(); j++){
                        if(init==0){
                            fontMatrix[lineCount][j] = baseMatrix[i][j];
                        }else{
                            if(newMatrix[i][j]==init){
                                fontMatrix[lineCount][j] = init;
                            }else{
                                fontMatrix[lineCount][j] = (byte)1;
                            }
                        }   
                    }
                }
            }
            /*System.out.println("Row"+lineCount);
            for (int i = 0 ; i < lineCount; i++){
                for (int j = 0; j < intLength; j++){
                    System.err.print(" "+fontMatrix[i][j]);
                }
                System.err.println();
            }*/
            
            newMatrix = null;
            baseMatrix = null;
            BufferedImage bufferedImage = new BufferedImage(objFabric.getIntWarp(), lineCount, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotCrossSectionGraphView(objFabric, fontMatrix, objFabric.getIntWarp(), lineCount);
            System.gc();
            
            FileChooser fileChoser=new FileChooser();
            fileChoser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTBMP")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
            FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP (.bmp)", "*.bmp");
            fileChoser.getExtensionFilters().add(extFilterBMP);
            File file=fileChoser.showSaveDialog(fabricStage);
            if(file==null)
                return;
            else
                fabricStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+file.getAbsoluteFile().getName()+"]");
            if (!file.getName().endsWith("bmp"))
                file = new File(file.getPath() +".bmp");
            ImageIO.write(bufferedImage, "BMP", file);
            bufferedImage = null;
            
            ArrayList<File> filesToZip=new ArrayList<>();
            filesToZip.add(file);
            String zipFilePath=file.getAbsolutePath()+".zip";
            String passwordToZip = file.getName();
            new EncryptZip(zipFilePath, filesToZip, passwordToZip);
            file.delete();

            lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
        } catch (IOException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    public void getSuperBaseGraphMC(){
        try {
            byte[][] newMatrix = objFabric.getFabricMatrix();
            List lstLines = new ArrayList();
            ArrayList<Byte> lstEntry = null;
            int lineCount = 0;
            byte temp = 1;                                      //added for ashis
            for (int i = 0; i < objFabric.getIntWeft(); i++){
                lstEntry = new ArrayList();
                for (int j = 0; j < objFabric.getIntWarp(); j++){
                    /*
                    //add the first color on array
                    if(lstEntry.size()==0 && newMatrix[i][j]!=1){
                        if(temp!=1 && temp==newMatrix[i][j]){   //added for ashis
                            lstEntry.add((byte)0);              //added for ashis
                            System.out.println(temp);           //added for ashis
                        }                                       //added for ashis
                        lstEntry.add(newMatrix[i][j]);
                    }
                    //check for redudancy
                    else {                
                        if(!lstEntry.contains(newMatrix[i][j]) && newMatrix[i][j]!=1){
                            temp = newMatrix[i][j];             //added for ashis
                            lstEntry.add(newMatrix[i][j]);                            
                        }
                    }
                    */
                    //-------//added for ashis
                    //add the first color on array
                    if(lstEntry.size()==0){
                        lstEntry.add((byte)0);                        
                    }
                    //check for redudancy
                    else {                
                        if(!lstEntry.contains(newMatrix[i][j]) && newMatrix[i][j]!=1){
                            lstEntry.add(newMatrix[i][j]);                            
                        }
                    }
                    //-------//added for ashis
                }
                Collections.sort(lstEntry);
                lstLines.add(lstEntry);
                lineCount+=lstEntry.size();
            }
            byte[][] baseMatrix = objArtworkAction.repeatMatrix(objFabric.getBaseWeaveMatrix(),lineCount,objFabric.getIntWarp());
            byte[][] fontMatrix = new byte[lineCount][objFabric.getIntWarp()];
            lineCount=0;
            byte init = 0;
            for (int i = 0 ; i < objFabric.getIntWeft(); i++){
                lstEntry = (ArrayList)lstLines.get(i);
                for(int k=0; k<lstEntry.size(); k++, lineCount++){
                    init = (byte)lstEntry.get(k);
                    for (int j = 0; j < objFabric.getIntWarp(); j++){
                        if(init==0){
                            fontMatrix[lineCount][j] = baseMatrix[i][j];
                        }else{
                            if(newMatrix[i][j]==init){
                                fontMatrix[lineCount][j] = init;
                            }else{
                                fontMatrix[lineCount][j] = (byte)1;
                            }
                        }   
                    }
                }
            }
            /*System.out.println("Row"+lineCount);
            for (int i = 0 ; i < lineCount; i++){
                for (int j = 0; j < intLength; j++){
                    System.err.print(" "+fontMatrix[i][j]);
                }
                System.err.println();
            }*/
            
            newMatrix = null;
            baseMatrix = null;
            BufferedImage bufferedImage = new BufferedImage(objFabric.getIntWarp(), lineCount, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotCrossSectionBaseGraphView(objFabric, fontMatrix, objFabric.getIntWarp(), lineCount);
            System.gc();
            
            FileChooser fileChoser=new FileChooser();
            fileChoser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTBMP")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
            FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP (.bmp)", "*.bmp");
            fileChoser.getExtensionFilters().add(extFilterBMP);
            File file=fileChoser.showSaveDialog(fabricStage);
            if(file==null)
                return;
            else
                fabricStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+file.getAbsoluteFile().getName()+"]");
            if (!file.getName().endsWith("bmp"))
                file = new File(file.getPath() +".bmp");
            ImageIO.write(bufferedImage, "BMP", file);
            bufferedImage = null;
            
            ArrayList<File> filesToZip=new ArrayList<>();
            filesToZip.add(file);
            String zipFilePath=file.getAbsolutePath()+".zip";
            String passwordToZip = file.getName();
            new EncryptZip(zipFilePath, filesToZip, passwordToZip);
            file.delete();

            lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
        } catch (IOException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    public void printScreen() {   
        final Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        //dialogStage.initModality(Modality.WINDOW_MODAL);
        GridPane popup=new GridPane();
        popup.setId("popup");
        popup.setHgap(5);
        popup.setVgap(5);
        popup.setMinSize(objConfiguration.WIDTH/2, objConfiguration.HEIGHT/2);

        Button btnPrint = new Button(objDictionaryAction.getWord("PRINT"));
        btnPrint.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPRINT")));
        btnPrint.setGraphic(new ImageView(objConfiguration.getStrColour()+"/print.png"));
        btnPrint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {  
                new MessageView(objConfiguration);
                if(objConfiguration.getServicePasswordValid()){
                    objConfiguration.setServicePasswordValid(false);
                    try {
                        if(objPrinterJob.printDialog()){
                            objPrinterJob.setPrintable(new Printable() {
                                @Override
                                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                                    if (pageIndex != 0) {
                                        return NO_SUCH_PAGE;
                                    }
                                    /*
                                    //objPrinterJob.getJobSettings().setPageRanges(new PageRange(1, numPages));
                                    JobSettings js = job.getJobSettings();
                                        for (PageRange pr : js.getPageRanges()) {
                                            for (int p = pr.getStartPage(); p <= pr.getEndPage(); p++) {
                                                boolean ok = job.printPage(...code to get your node for the page...);
                                                ...take action on success/failure etc.
                                            }
                                        }
                                    */
                                    BufferedImage texture = SwingFXUtils.fromFXImage(fabric.getImage(), null);
                                    Graphics2D g2d=(Graphics2D)graphics;
                                    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                                    
                                    //graphics.drawImage(texture, 0, 0, texture.getWidth(), texture.getHeight(), null);
                                    graphics.drawImage(texture, 0, 0, (int)pageFormat.getImageableWidth(), (int)pageFormat.getImageableWidth(), null);
                                    return PAGE_EXISTS;                    
                                }
                            });    
                            objPrinterJob.print();
                        }
                        dialogStage.close();
                    } catch (PrinterException ex) {
                        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                    } catch (Exception ex) {
                        new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    }
                }
            }
        });
        popup.add(btnPrint, 0, 0);
        Button btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
        btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
        btnCancel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {  
                dialogStage.close();
           }
        });
        popup.add(btnCancel, 1, 0);
        
        BufferedImage texture = SwingFXUtils.fromFXImage(fabric.getImage(), null);
        ImageView printImage = new ImageView();
        printImage.setImage(SwingFXUtils.toFXImage(texture, null));        
        popup.add(printImage, 0, 1, 2, 1);

        Scene scene=new Scene(popup);
        scene.getStylesheets().add(FabricView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        dialogStage.setScene(scene);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("PRINT")+" "+objDictionaryAction.getWord("PREVIEW"));
        dialogStage.showAndWait();   
    }
    public void captureFrame(String fileName) {
        BufferedImage capture = new BufferedImage((int)fabricStage.getWidth(), (int)fabricStage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        capture.getGraphics();
        try {
            FileChooser fileChoser=new FileChooser();
            fileChoser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTPNG")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG (*.png)", "*.png");
            fileChoser.getExtensionFilters().add(extFilterPNG);
            File file=fileChoser.showSaveDialog(fabricStage);
            if(file==null)
                return;
            else
                fabricStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : ["+file.getAbsoluteFile().getName()+"]");   
            if (!file.getName().endsWith("png")) 
                file = new File(file.getPath() +".png");
            ImageIO.write(capture, "png", file);
            
            ArrayList<File> filesToZip=new ArrayList<>();
            filesToZip.add(file);
            String zipFilePath=file.getAbsolutePath()+".zip";
            String passwordToZip = file.getName();
            new EncryptZip(zipFilePath, filesToZip, passwordToZip);
            file.delete();
                
            lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
        } catch (IOException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } 
    }
    public void ScreenShoot() {
        WritableImage image = container.snapshot(new SnapshotParameters(), null);
        
        FileChooser fileChoser=new FileChooser();
        fileChoser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTPNG")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChoser.getExtensionFilters().add(extFilterPNG);
        File file=fileChoser.showSaveDialog(fabricStage);
        if(file==null)
            return;
        else
            fabricStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+file.getAbsoluteFile().getName()+"]");
        if (!file.getName().endsWith("png")) 
            file = new File(file.getPath() +".png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            
            ArrayList<File> filesToZip=new ArrayList<>();
            filesToZip.add(file);
            String zipFilePath=file.getAbsolutePath()+".zip";
            String passwordToZip = file.getName();
            new EncryptZip(zipFilePath, filesToZip, passwordToZip);
            file.delete();
                
            lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
        } catch (IOException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    public void printGraph(){
        try {
            int intLength = objFabric.getIntWarp();
            int intHeight = objFabric.getIntWeft(); 
            lblStatus.setText(objDictionaryAction.getWord("GETGRAPHVIEW"));
            BufferedImage bufferedImage = new BufferedImage(objFabric.getIntWarp(), objFabric.getIntWeft(),BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            
            //================================ For Outer to be square ===============================
            if(objFabric.getIntExtraWarp()>0 || objFabric.getIntExtraWeft()>0){
                if(objConfiguration.getBlnPunchCard()){
                    bufferedImage = objFabricAction.plotGraphJaquardMachineView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
                    intLength=objFabric.getIntWarp()*(objFabric.getIntExtraWeft()+2);
                } else{
                    bufferedImage = objFabricAction.plotGraphJaquardView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
                }
            }else{
                bufferedImage = objFabricAction.plotGraphDobbyView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
            }
            
            String[] data = objFabric.getObjConfiguration().getStrGraphSize().split("x");
            final BufferedImage texture = new BufferedImage((int)(intLength*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0]))+100,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = texture.createGraphics();
            g.drawImage(bufferedImage, 0, 0, (int)(intLength*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])), null);
            g.setColor(java.awt.Color.BLACK);
            BasicStroke bs = new BasicStroke(2);
            g.setStroke(bs);
            
            for(int i = 0; i < objFabric.getIntWeft(); i++) {
                for(int j = 0; j < intLength; j++) {
                    if((j%Integer.parseInt(data[0]))==0){
                        bs = new BasicStroke(2);
                        g.setStroke(bs);
                    }else{
                        bs = new BasicStroke(1);
                        g.setStroke(bs);
                    }
                    g.drawLine((int)(j*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])), 0,  (int)(j*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])));
                }
                if((i%Integer.parseInt(data[1]))==0){
                    bs = new BasicStroke(2);
                    g.setStroke(bs);
                }else{
                    bs = new BasicStroke(1);
                    g.setStroke(bs);
                }
                g.drawLine(0, (int)(i*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])), (int)(intLength*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize())*Integer.parseInt(data[1]), (int)(i*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])));
            }
            /*
            ============================= For Inner to be square ===============================
            if(objFabric.getIntExtraWarp()>0 || objFabric.getIntExtraWeft()>0){
                bufferedImage = objFabricAction.plotGraphJaquardView(objFabric, intWarp, intWeft);
            }else{
                bufferedImage = objFabricAction.plotGraphDobbyView(objFabric, intWarp, intWeft);
            }
            final BufferedImage texture = new BufferedImage((int)(intWeft*objConfiguration.getIntBoxSize()), (int)(intWarp*objConfiguration.getIntBoxSize())+100,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = texture.createGraphics();
            g.drawImage(bufferedImage, 0, 0, (int)(intWeft*objConfiguration.getIntBoxSize()), (int)(intWarp*objConfiguration.getIntBoxSize()), null);
            g.setColor(java.awt.Color.BLACK);
            BasicStroke bs = new BasicStroke(2);
            g.setStroke(bs);
            String[] data = objConfiguration.getStrGraphSize().split("x");
            for(int x = 0; x < (int)(intWarp*objConfiguration.getIntBoxSize()); x+=(int)(objConfiguration.getIntBoxSize())) {
                for(int y = 0; y < (int)(intWeft*objConfiguration.getIntBoxSize()); y+=(int)(objConfiguration.getIntBoxSize())) {
                    if(y%(int)(Integer.parseInt(data[0])*objConfiguration.getIntBoxSize())==0){
                        bs = new BasicStroke(2);
                        g.setStroke(bs);
                    }else{
                        bs = new BasicStroke(1);
                        g.setStroke(bs);
                    }
                    g.drawLine(y, 0,  y, (int)(intWarp*objConfiguration.getIntBoxSize()));
                }
                if(x%(int)(Integer.parseInt(data[1])*objConfiguration.getIntBoxSize())==0){
                    bs = new BasicStroke(2);
                    g.setStroke(bs);
                }else{
                    bs = new BasicStroke(1);
                    g.setStroke(bs);
                }
                g.drawLine(0, x, (int)(intWeft*objConfiguration.getIntBoxSize()), x);
            }
            */
            g.setColor(java.awt.Color.WHITE);
            bs = new BasicStroke(3);
            g.setStroke(bs);
            g.drawString(objDictionaryAction.getWord("LOOM")+" "+objDictionaryAction.getWord("SPECIFICATIONS")+" : ", 10, (int)(objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1]))+(1*g.getFontMetrics().getHeight()));
            g.drawString(objDictionaryAction.getWord("REEDCOUNT")+" : "+objFabric.getIntReedCount(), (int)(intLength*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/2), (int)(objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1]))+(1*g.getFontMetrics().getHeight()));
            g.drawString(objDictionaryAction.getWord("DENTS")+" : "+objFabric.getIntDents(), 10, (int)(objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1]))+(2*g.getFontMetrics().getHeight()));
            g.drawString(objDictionaryAction.getWord("PPI")+" : "+objFabric.getIntPPI(), (int)(intLength*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/2), (int)(objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1]))+(2*g.getFontMetrics().getHeight()));
            g.drawString(objDictionaryAction.getWord("PICKS")+" : "+objFabric.getIntWeft(), 10, (int)(objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1]))+(3*g.getFontMetrics().getHeight()));
            g.drawString(objDictionaryAction.getWord("HPI")+" : "+objFabric.getIntHPI(), (int)(intLength*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/2), (int)(objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1]))+(3*g.getFontMetrics().getHeight()));
            g.drawString(objDictionaryAction.getWord("HOOKS")+" : "+objFabric.getIntHooks(), 10, (int)(objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1]))+(4*g.getFontMetrics().getHeight()));
            g.drawString(objDictionaryAction.getWord("EPI")+" : "+objFabric.getIntEPI(), (int)(intLength*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/2), (int)(objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1]))+(4*g.getFontMetrics().getHeight()));
            g.drawString(objDictionaryAction.getWord("ENDS")+" : "+objFabric.getIntWarp(), 10, (int)(objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1]))+(5*g.getFontMetrics().getHeight()));
            g.drawString(objDictionaryAction.getWord("TPD")+" : "+objFabric.getIntTPD(), (int)(intLength*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/2), (int)(objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1]))+(5*g.getFontMetrics().getHeight()));
            g.dispose();
            bufferedImage = null;
            if(objPrinterJob.printDialog()){                
                if(objConfiguration.getBlnMPrint()){
                    //System.err.println(objPrinterJob.getPageFormat(objPrinterAttribute).getImageableX()+""+objPrinterJob.getPageFormat(objPrinterAttribute).getImageableX());
                    double h = objPrinterJob.getPageFormat(objPrinterAttribute).getImageableHeight();
                    double w = objPrinterJob.getPageFormat(objPrinterAttribute).getImageableWidth();
                    BufferedImage texture1 = new BufferedImage((int)(w*Math.ceil(texture.getWidth()/w)), (int)(h*Math.ceil(texture.getHeight()/h)),BufferedImage.TYPE_INT_RGB);
                    Graphics2D g1 = texture1.createGraphics();
                    g1.drawImage(texture, 0, 0, (int)(w*Math.ceil(texture.getWidth()/w)), (int)(h*Math.ceil(texture.getHeight()/h)), null);
                    g1.dispose();
                    
                    final BufferedImage page = new BufferedImage((int)w, (int)h, BufferedImage.TYPE_INT_RGB);
                    for(int i=0; i<Math.ceil(texture.getHeight()/h);i++){
                        for(int j=0; j<Math.ceil(texture.getWidth()/w);j++){
                            page.getGraphics().drawImage(texture1, 0, 0, (int)w, (int)h, (int)(j*w), (int)(i*h), (int)((j+1)*w), (int)((i+1)*h), null);
                            objPrinterJob.setPrintable(new Printable() {
                                @Override
                                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                                    if (pageIndex != 0) {
                                        return NO_SUCH_PAGE;
                                    }
                                    //graphics.drawImage(texture, 0, 0, texture.getWidth(), texture.getHeight(), null);
                                    Graphics2D g2d=(Graphics2D)graphics;
                                    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                                    graphics.drawImage(page, 0, 0, (int)pageFormat.getImageableWidth(), (int)pageFormat.getImageableHeight(), null);
                                    return PAGE_EXISTS;                    
                                }
                            });     
                            objPrinterJob.print();
                        }
                    }
                }else{
                    objPrinterJob.setPrintable(new Printable() {
                        @Override
                        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                            if (pageIndex != 0) {
                                return NO_SUCH_PAGE;
                            }
                            //graphics.drawImage(texture, 0, 0, texture.getWidth(), texture.getHeight(), null);
                            Graphics2D g2d=(Graphics2D)graphics;
                            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                            graphics.drawImage(texture, 0, 0, (int)pageFormat.getImageableWidth(), (int)pageFormat.getImageableHeight(), null);
                            return PAGE_EXISTS;                    
                        }
                    });     
                    objPrinterJob.print();
                }
            }
        } catch (PrinterException ex) {             
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);  
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    public void printGrid() {   
        try {            
            if(objPrinterJob.printDialog()){
                objPrinterJob.setPrintable(new Printable() {
                    @Override
                    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                        if (pageIndex != 0) {
                            return NO_SUCH_PAGE;
                        }
                        BufferedImage texture = new BufferedImage(objFabric.getIntWarp(), objFabric.getIntWeft(),BufferedImage.TYPE_INT_RGB);
                        for(int x = 0; x < objFabric.getIntWeft(); x++) {
                            for(int y = 0; y < objFabric.getIntWarp(); y++) {
                                if(objFabric.getFabricMatrix()[x][y]==0)
                                    texture.setRGB(y, x, java.awt.Color.WHITE.getRGB());
                                else
                                    texture.setRGB(y, x, java.awt.Color.BLACK.getRGB());
                            }
                        }
                        Graphics2D g2d=(Graphics2D)graphics;
                        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                    
                        graphics.drawImage(texture, 0, 0, texture.getWidth(), texture.getHeight(), null);
                        return PAGE_EXISTS;                    
                    }
                });     
                objPrinterJob.print();
            }
        } catch (PrinterException ex) {             
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);  
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }        
    public void printTexture() {   
        try {            
            if(objPrinterJob.printDialog()){
                objPrinterJob.setPrintable(new Printable() {
                    @Override
                    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                        if (pageIndex != 0) {
                            return NO_SUCH_PAGE;
                        }
                        int intLength = objFabric.getIntWarp();
                        int intHeight = objFabric.getIntWeft(); 
                        BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
                        try {
                            objFabricAction = new FabricAction();
                        } catch (SQLException ex) {
                            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);  
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                        bufferedImage = objFabricAction.plotCompositeView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), objFabric.getIntWarp(), objFabric.getIntWeft());
                        
                        intLength = (int)(((objConfiguration.getIntDPI()*objFabric.getIntWarp())/objFabric.getIntEPI())*zoomfactor);
                        intHeight = (int)(((objConfiguration.getIntDPI()*objFabric.getIntWeft())/objFabric.getIntPPI())*zoomfactor);
                        BufferedImage texture = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            
                        Graphics2D g = texture.createGraphics();
                        g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
                        g.dispose();
                        /*
                        if((objFabric.getIntEPI()/objFabric.getIntPPI())<=0){
                            intHeight = (int)(intLength*(objFabric.getIntPPI()/objFabric.getIntEPI())*zoomfactor);
                            intLength = (int)(intLength*zoomfactor);
                        }else{
                            intLength = (int)(intHeight*(objFabric.getIntEPI()/objFabric.getIntPPI())*zoomfactor);
                            intHeight = (int)(intHeight*zoomfactor);
                        }                        
                        BufferedImage texture = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
                        Graphics2D g = texture.createGraphics();
                        g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
                        g.dispose();
                        */
                        Graphics2D g2d=(Graphics2D)graphics;
                        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                    
                        graphics.drawImage(texture, 0, 0, intLength, intHeight, null);
                        return PAGE_EXISTS;
                    }
                });     
                objPrinterJob.print();
            }
        } catch (PrinterException ex) {             
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);  
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    public void saveAsGraph(){
        try {
            int intLength = objFabric.getIntWarp();
            int intHeight = objFabric.getIntWeft();
            lblStatus.setText(objDictionaryAction.getWord("GETGRAPHVIEW"));
            BufferedImage bufferedImage = new BufferedImage(intHeight, intLength, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            
            //================================ For Outer to be square ===============================
            if(objFabric.getIntExtraWarp()>0 || objFabric.getIntExtraWeft()>0){
                if(objConfiguration.getBlnPunchCard()){
                    bufferedImage = objFabricAction.plotGraphJaquardMachineView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
                    intLength=objFabric.getIntWarp()*(objFabric.getIntExtraWeft()+2);
                } else{
                    bufferedImage = objFabricAction.plotGraphJaquardView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
                }
            }else{
                bufferedImage = objFabricAction.plotGraphDobbyView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft());
            }
            
            String[] data = objFabric.getObjConfiguration().getStrGraphSize().split("x");
            BufferedImage texture = new BufferedImage((int)(intLength*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0]))+100,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = texture.createGraphics();
            g.drawImage(bufferedImage, 0, 0, (int)(intLength*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])), null);
            g.setColor(java.awt.Color.BLACK);
            BasicStroke bs = new BasicStroke(2);
            g.setStroke(bs);
            
            for(int i = 0; i < objFabric.getIntWeft(); i++) {
                for(int j = 0; j < intLength; j++) {
                    if((j%Integer.parseInt(data[0]))==0){
                        bs = new BasicStroke(2);
                        g.setStroke(bs);
                    }else{
                        bs = new BasicStroke(1);
                        g.setStroke(bs);
                    }
                    g.drawLine((int)(j*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])), 0,  (int)(j*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])), (int)(objFabric.getIntWeft()*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])));
                }
                if((i%Integer.parseInt(data[1]))==0){
                    bs = new BasicStroke(2);
                    g.setStroke(bs);
                }else{
                    bs = new BasicStroke(1);
                    g.setStroke(bs);
                }
                g.drawLine(0, (int)(i*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])), (int)(intLength*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize())*Integer.parseInt(data[1]), (int)(i*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])));
            }
            /*
            ============================= For Inner to be square ===============================
            if(objFabric.getIntExtraWarp()>0 || objFabric.getIntExtraWeft()>0){
                bufferedImage = objFabricAction.plotGraphJaquardView(objFabric, intWarp, intWeft);
            }else{
                bufferedImage = objFabricAction.plotGraphDobbyView(objFabric, intWarp, intWeft);
            }
            BufferedImage texture = new BufferedImage((int)(intWeft*objConfiguration.getIntBoxSize()), (int)(intWarp*objConfiguration.getIntBoxSize())+100,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = texture.createGraphics();
            g.drawImage(bufferedImage, 0, 0, (int)(intWeft*objConfiguration.getIntBoxSize()), (int)(intWarp*objConfiguration.getIntBoxSize()), null);
            g.setColor(java.awt.Color.BLACK);
            BasicStroke bs = new BasicStroke(2);
            g.setStroke(bs);
            String[] data = objConfiguration.getStrGraphSize().split("x");
            for(int x = 0; x < (int)(intWarp*objConfiguration.getIntBoxSize()); x+=(int)(objConfiguration.getIntBoxSize())) {
                for(int y = 0; y < (int)(intWeft*objConfiguration.getIntBoxSize()); y+=(int)(objConfiguration.getIntBoxSize())) {
                    if(y%(int)(Integer.parseInt(data[0])*objConfiguration.getIntBoxSize())==0){
                        bs = new BasicStroke(2);
                        g.setStroke(bs);
                    }else{
                        bs = new BasicStroke(1);
                        g.setStroke(bs);
                    }
                    g.drawLine(y, 0,  y, (int)(intWarp*objConfiguration.getIntBoxSize()));
                }
                if(x%(int)(Integer.parseInt(data[1])*objConfiguration.getIntBoxSize())==0){
                    bs = new BasicStroke(2);
                    g.setStroke(bs);
                }else{
                    bs = new BasicStroke(1);
                    g.setStroke(bs);
                }
                g.drawLine(0, x, (int)(intWeft*objConfiguration.getIntBoxSize()), x);
            }
            */
            g.setColor(java.awt.Color.WHITE);
            bs = new BasicStroke(3);
            g.setStroke(bs);
            g.drawString(objDictionaryAction.getWord("LOOM")+" "+objDictionaryAction.getWord("SPECIFICATIONS")+" : ", 10, (int)(objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1]))+(1*g.getFontMetrics().getHeight()));
            g.drawString(objDictionaryAction.getWord("REEDCOUNT")+" : "+objFabric.getIntReedCount(), (int)(intLength*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/2), (int)(objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1]))+(1*g.getFontMetrics().getHeight()));
            g.drawString(objDictionaryAction.getWord("DENTS")+" : "+objFabric.getIntDents(), 10, (int)(objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1]))+(2*g.getFontMetrics().getHeight()));
            g.drawString(objDictionaryAction.getWord("PPI")+" : "+objFabric.getIntPPI(), (int)(intLength*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/2), (int)(objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1]))+(2*g.getFontMetrics().getHeight()));
            g.drawString(objDictionaryAction.getWord("PICKS")+" : "+objFabric.getIntWeft(), 10, (int)(objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1]))+(3*g.getFontMetrics().getHeight()));
            g.drawString(objDictionaryAction.getWord("HPI")+" : "+objFabric.getIntHPI(), (int)(intLength*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/2), (int)(objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1]))+(3*g.getFontMetrics().getHeight()));
            g.drawString(objDictionaryAction.getWord("HOOKS")+" : "+objFabric.getIntHooks(), 10, (int)(objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1]))+(4*g.getFontMetrics().getHeight()));
            g.drawString(objDictionaryAction.getWord("EPI")+" : "+objFabric.getIntEPI(), (int)(intLength*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/2), (int)(objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1]))+(4*g.getFontMetrics().getHeight()));
            g.drawString(objDictionaryAction.getWord("ENDS")+" : "+objFabric.getIntWarp(), 10, (int)(objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1]))+(5*g.getFontMetrics().getHeight()));
            g.drawString(objDictionaryAction.getWord("TPD")+" : "+objFabric.getIntTPD(), (int)(intLength*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/2), (int)(objFabric.getIntWeft()*objFabric.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1]))+(5*g.getFontMetrics().getHeight()));
            g.dispose();
            bufferedImage = null;
            FileChooser fileChoser=new FileChooser();
            fileChoser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTBMP")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
            FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP (.bmp)", "*.bmp");
            fileChoser.getExtensionFilters().add(extFilterBMP);
            File file=fileChoser.showSaveDialog(fabricStage);
            if(file==null)
                return;
            else
                fabricStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+file.getAbsoluteFile().getName()+"]");
            if (!file.getName().endsWith("bmp")) 
                file = new File(file.getPath() +".bmp");
            ImageIO.write(texture, "BMP", file);
            
            ArrayList<File> filesToZip=new ArrayList<>();
            filesToZip.add(file);
            String zipFilePath=file.getAbsolutePath()+".zip";
            String passwordToZip = file.getName();
            new EncryptZip(zipFilePath, filesToZip, passwordToZip);
            file.delete();

            lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
        } catch (IOException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    public void saveAsGrid() {   
        try {            
            int intLength = objFabric.getIntWarp();
            int intHeight = objFabric.getIntWeft();
            lblStatus.setText(objDictionaryAction.getWord("GETGRIDVIEW"));
            BufferedImage bufferedImage = new BufferedImage(intHeight, intLength, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            
            BufferedImage texture = new BufferedImage(objFabric.getIntWarp(), objFabric.getIntWeft(),BufferedImage.TYPE_INT_RGB);
            for(int x = 0; x < objFabric.getIntWeft(); x++) {
                for(int y = 0; y < objFabric.getIntWarp(); y++) {
                    if(objFabric.getFabricMatrix()[x][y]==0)
                        texture.setRGB(y, x, java.awt.Color.WHITE.getRGB());
                    else
                        texture.setRGB(y, x, java.awt.Color.BLACK.getRGB());
                }
            }
            FileChooser fileChoser=new FileChooser();
            fileChoser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTBMP")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
            FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP (.bmp)", "*.bmp");
            fileChoser.getExtensionFilters().add(extFilterBMP);
            File file=fileChoser.showSaveDialog(fabricStage);
            if(file==null)
                return;
            else
                fabricStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+file.getAbsoluteFile().getName()+"]");
            if (!file.getName().endsWith("bmp")) 
                file = new File(file.getPath() +".bmp");
            ImageIO.write(texture, "BMP", file);
            
            ArrayList<File> filesToZip=new ArrayList<>();
            filesToZip.add(file);
            String zipFilePath=file.getAbsolutePath()+".zip";
            String passwordToZip = file.getName();
            new EncryptZip(zipFilePath, filesToZip, passwordToZip);
            file.delete();

            lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
        } catch (IOException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }   
    public void saveAsImage() {   
        try {
            int intLength = objFabric.getIntWarp();
            int intHeight = objFabric.getIntWeft(); 
            BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotCompositeView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), objFabric.getIntWarp(), objFabric.getIntWeft());
            intLength = (int)(((objConfiguration.getIntDPI()*objFabric.getIntWarp())/objFabric.getIntEPI())*zoomfactor);
            intHeight = (int)(((objConfiguration.getIntDPI()*objFabric.getIntWeft())/objFabric.getIntPPI())*zoomfactor);
            BufferedImage texture = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = texture.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();

            FileChooser fileChoser=new FileChooser();
            fileChoser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTPNG")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG (*.png)", "*.png");
            fileChoser.getExtensionFilters().add(extFilterPNG);
            File file=fileChoser.showSaveDialog(fabricStage);
            if(file==null)
                return;
            else
                fabricStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+file.getAbsoluteFile().getName()+"]");
            if (!file.getName().endsWith("png")) 
                file = new File(file.getPath() +".png");
            ImageIO.write(texture, "png", file);
            
            ArrayList<File> filesToZip=new ArrayList<>();
            filesToZip.add(file);
            String zipFilePath=file.getAbsolutePath()+".zip";
            String passwordToZip = file.getName();
            new EncryptZip(zipFilePath, filesToZip, passwordToZip);
            file.delete();

            lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
            /*
            BufferedImage bufferedImage = new BufferedImage(objFabric.getIntWarp(), objFabric.getIntWeft(),BufferedImage.TYPE_INT_RGB);
            objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotCompositeView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), objFabric.getIntWarp(), objFabric.getIntWeft());
            FileChooser fileChoser=new FileChooser();
            fileChoser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTPNG")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG (*.png)", "*.png");
            fileChoser.getExtensionFilters().add(extFilterPNG);
            File file=fileChoser.showSaveDialog(fabricStage);
            if(file==null)
                return;
            else
                fabricStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+file.getAbsoluteFile().getName()+"]");
            if (!file.getName().endsWith("png")) 
                file = new File(file.getPath() +".png");
            ImageIO.write(bufferedImage, "png", file);
            lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
            */
        } catch (IOException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public void saveAsHtml() {   
        try {
            FileChooser fileChoser=new FileChooser();
            fileChoser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTHTML")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
            FileChooser.ExtensionFilter extFilterHTML = new FileChooser.ExtensionFilter("HTML (*.html)", "*.html");
            fileChoser.getExtensionFilters().add(extFilterHTML);
            File ifile=fileChoser.showSaveDialog(fabricStage);
            if(ifile==null)
                return;
            else
                fabricStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+ifile.getAbsoluteFile().getName()+"]");
            
            BufferedImage texture = new BufferedImage(objFabric.getIntWarp(), objFabric.getIntWeft(),BufferedImage.TYPE_INT_RGB);        
            for(int x = 0; x < objFabric.getIntWeft(); x++) {
                for(int y = 0; y < objFabric.getIntWarp(); y++) {
                    if(objFabric.getFabricMatrix()[x][y]==0)
                        texture.setRGB(y, x, java.awt.Color.WHITE.getRGB());
                    else
                        texture.setRGB(y, x, java.awt.Color.BLACK.getRGB());
                }
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            String path = ifile.getPath()+"Fabric_"+objFabric.getStrFabricID()+"_"+currentDate;
            File pngFile = new File(path+".png");
            ImageIO.write(texture, "png", pngFile);
            File htmlFile = new File(path+".html");
            
            String INITIAL_TEXT = " This is coumputer generated, So no signature required";
            String IMAGE_TEXT = "<img src=\"file:\\\\" + 
                path+".png" + 
             "\" width=\""+objFabric.getIntWeft()+"\" height=\""+objFabric.getIntWarp()+"\" >";
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(htmlFile, true))) {
                bw.write("<table border=0 width=\"100%\">");
                    bw.newLine();
                    bw.write("<tr>");
                        bw.newLine();
                        bw.write("<td><h1>Media Lab Asia</h1></td>");
                        bw.newLine();
                        bw.write("<td><h6 align=right>");
                            bw.newLine();
                            bw.write(objDictionaryAction.getWord("PROJECT")+": Fabric_"+objFabric.getStrFabricID()+"_"+currentDate+"<br>");
                            bw.newLine();
                            bw.write("<a href=\"http://www.medialabasia.in\">http://www.medialabasia.in</a><br>");
                            bw.newLine();
                            bw.write("&copy; 2015-17 Media Lab Asia; New Delhi, India<br><br>"+date);
                            bw.newLine();
                        bw.write("</h6></td>");
                    bw.newLine();
                    bw.write("</tr>");
                bw.newLine();
                bw.write("</table>");
                bw.newLine();
                bw.write("<table border=1 cellspacing=0>");
                bw.newLine();
                bw.write("<tr align=right><th>Fabric Category</th><td>"+objFabric.getStrClothType()+"</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("FABRICTYPE")+"</th><td>"+objFabric.getStrFabricType()+"</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("NAME")+"</th><td>"+objFabric.getStrFabricName()+"</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("FABRICLENGTH")+"</th><td>"+objFabric.getDblFabricLength()+" inch</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("FABRICWIDTH")+"</th><td>"+objFabric.getDblFabricWidth()+" inch</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("REEDCOUNT")+"</th><td>"+objFabric.getIntReedCount()+"</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("DENTS")+"</th><td>"+objFabric.getIntDents()+"</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("TPD")+"</th><td>"+objFabric.getIntTPD()+"</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("PICKS")+"</th><td>"+objFabric.getIntWeft()+"</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("HOOKS")+"</th><td>"+objFabric.getIntHooks()+"</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("ENDS")+"</th><td>"+objFabric.getIntWarp()+"</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("PPI")+"</th><td>"+objFabric.getIntPPI()+" / inch</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("HPI")+"</th><td>"+objFabric.getIntHPI()+"</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("EPI")+"</th><td>"+objFabric.getIntEPI()+" / inch</td></tr>");
                bw.newLine();
                if(objFabric.getStrArtworkID()!=null){
                    bw.newLine();
                    bw.write("<tr align=right><th>"+objDictionaryAction.getWord("ARTWORKLENGTH")+"</th><td>"+objFabric.getDblArtworkLength()+" inch</td></tr>");
                    bw.newLine();
                    bw.write("<tr align=right><th>"+objDictionaryAction.getWord("ARTWORKWIDTH")+"</th><td>"+objFabric.getDblArtworkWidth()+" inch</td></tr>");
                }   
                bw.newLine();
                objFabricAction = new FabricAction(false);
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("WARPYARNCONSUMPTION")+"</th><td>"+objFabricAction.getWarpNumber(objFabric)+"</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("WARPYARNWEIGHT")+"</th><td>"+objFabricAction.getWarpWeight(objFabric)+"gram</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("WARPYARNLONG")+"</th><td>"+objFabricAction.getWarpLong(objFabric)+"meter</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("WEFTYARNCONSUMPTION")+"</th><td>"+objFabricAction.getWeftNumber(objFabric)+"</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("WEFTYARNWEIGHT")+"</th><td>"+objFabricAction.getWeftWeight(objFabric)+"gram</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("WEFTYARNLONG")+"</th><td>"+objFabricAction.getWeftLong(objFabric)+"meter</td></tr>");
                bw.newLine();
                bw.write("</table>");                
            bw.newLine();
            bw.write("");
            if(objFabric.getWarpYarn().length>0){
                bw.newLine();
                bw.write("<table border=1 cellspacing=0>");
                    bw.newLine();
                    objFabricAction = new FabricAction();
                    bw.write("<caption>"+objDictionaryAction.getWord("WARP")+" "+objDictionaryAction.getWord("YARNPATTERN")+" \t"+objFabricAction.getPattern(objFabric.getStrWarpPatternID()).getStrPattern()+"</caption>");
                    bw.newLine();
                    bw.write("<tr align=right><th>"+objDictionaryAction.getWord("WARP")+"</th><th>"+objDictionaryAction.getWord("NAME")+"</th><th>"+objDictionaryAction.getWord("YARNTYPE")+"</th><th>"+objDictionaryAction.getWord("YARNREPEAT")+"</th><th>"+objDictionaryAction.getWord("YARNCOUNT")+"</th><th>"+objDictionaryAction.getWord("YARNUNIT")+"</th><th>"+objDictionaryAction.getWord("YARNPLY")+"</th><th>"+objDictionaryAction.getWord("YARNFACTOR")+"</th><th>"+objDictionaryAction.getWord("YARNDIAMETER")+"(mm)</th><th>"+objDictionaryAction.getWord("YARNWEIGHT")+"(kg)</th><th>"+objDictionaryAction.getWord("YARNPRICE")+"(kg)</th><th>"+objDictionaryAction.getWord("YARNTWIST")+"/ inch</th><th>"+objDictionaryAction.getWord("YARNSENCE")+"</th><th>"+objDictionaryAction.getWord("YARNHAIRNESS")+"</th><th>"+objDictionaryAction.getWord("YARNDISTRIBUTION")+"</th><th>"+objDictionaryAction.getWord("YARNCOLOR")+"</th></tr>");
                    for(int i = 0; i<objFabric.getWarpYarn().length;i++){
                        bw.newLine();
                        bw.write("<tr align=right><td>"+objFabric.getWarpYarn()[i].getStrYarnSymbol()+"</td><td>"+objFabric.getWarpYarn()[i].getStrYarnName()+"</td><td>"+objFabric.getWarpYarn()[i].getStrYarnType()+"</td><td>"+objFabric.getWarpYarn()[i].getIntYarnRepeat()+"</td><td>"+objFabric.getWarpYarn()[i].getIntYarnCount()+"</td><td>"+objFabric.getWarpYarn()[i].getStrYarnCountUnit()+"</td><td>"+objFabric.getWarpYarn()[i].getIntYarnPly()+"</td><td>"+objFabric.getWarpYarn()[i].getIntYarnDFactor()+"</td><td>"+objFabric.getWarpYarn()[i].getDblYarnDiameter()+"</td><td>-</td><td>"+objFabric.getWarpYarn()[i].getDblYarnPrice()+"</td><td>"+objFabric.getWarpYarn()[i].getIntYarnTwist()+"</td><td>"+objFabric.getWarpYarn()[i].getStrYarnTModel()+"</td><td>"+objFabric.getWarpYarn()[i].getIntYarnHairness()+"</td><td>"+objFabric.getWarpYarn()[i].getIntYarnHProbability()+"</td><th bgcolor=\""+objFabric.getWarpYarn()[i].getStrYarnColor()+"\">"+objFabric.getWarpYarn()[i].getStrYarnColor()+"</th></tr>");
                    }
                bw.newLine();
                bw.write("</table>");
            }
            if(objFabric.getWeftYarn().length>0){
                bw.newLine();
                bw.write("<table border=1 cellspacing=0>");
                    bw.newLine();
                    objFabricAction = new FabricAction();
                    bw.write("<caption>"+objDictionaryAction.getWord("WEFT")+" "+objDictionaryAction.getWord("YARNPATTERN")+" \t "+objFabricAction.getPattern(objFabric.getStrWeftPatternID()).getStrPattern()+"</caption>");
                    bw.newLine();
                    bw.write("<tr align=right><th>"+objDictionaryAction.getWord("WEFT")+"</th><th>"+objDictionaryAction.getWord("NAME")+"</th><th>"+objDictionaryAction.getWord("YARNTYPE")+"</th><th>"+objDictionaryAction.getWord("YARNREPEAT")+"</th><th>"+objDictionaryAction.getWord("YARNCOUNT")+"</th><th>"+objDictionaryAction.getWord("YARNUNIT")+"</th><th>"+objDictionaryAction.getWord("YARNPLY")+"</th><th>"+objDictionaryAction.getWord("YARNFACTOR")+"</th><th>"+objDictionaryAction.getWord("YARNDIAMETER")+"(mm)</th><th>"+objDictionaryAction.getWord("YARNWEIGHT")+"(kg)</th><th>"+objDictionaryAction.getWord("YARNPRICE")+"(kg)</th><th>"+objDictionaryAction.getWord("YARNTWIST")+"/ inch</th><th>"+objDictionaryAction.getWord("YARNSENCE")+"</th><th>"+objDictionaryAction.getWord("YARNHAIRNESS")+"</th><th>"+objDictionaryAction.getWord("YARNDISTRIBUTION")+"</th><th>"+objDictionaryAction.getWord("YARNCOLOR")+"</th></tr>");
                    for(int i = 0; i<objFabric.getWeftYarn().length;i++){
                        bw.newLine();
                        bw.write("<tr align=right><td>"+objFabric.getWeftYarn()[i].getStrYarnSymbol()+"</td><td>"+objFabric.getWeftYarn()[i].getStrYarnName()+"</td><td>"+objFabric.getWeftYarn()[i].getStrYarnType()+"</td><td>"+objFabric.getWeftYarn()[i].getIntYarnRepeat()+"</td><td>"+objFabric.getWeftYarn()[i].getIntYarnCount()+"</td><td>"+objFabric.getWeftYarn()[i].getStrYarnCountUnit()+"</td><td>"+objFabric.getWeftYarn()[i].getIntYarnPly()+"</td><td>"+objFabric.getWeftYarn()[i].getIntYarnDFactor()+"</td><td>"+objFabric.getWeftYarn()[i].getDblYarnDiameter()+"</td><td>-</td><td>"+objFabric.getWeftYarn()[i].getDblYarnPrice()+"</td><td>"+objFabric.getWeftYarn()[i].getIntYarnTwist()+"</td><td>"+objFabric.getWeftYarn()[i].getStrYarnTModel()+"</td><td>"+objFabric.getWeftYarn()[i].getIntYarnHairness()+"</td><td>"+objFabric.getWeftYarn()[i].getIntYarnHProbability()+"</td><th bgcolor=\""+objFabric.getWeftYarn()[i].getStrYarnColor()+"\">"+objFabric.getWeftYarn()[i].getStrYarnColor()+"</th></tr>");
                    }
                bw.newLine();
                bw.write("</table>");
            }
            if(objFabric.getWarpExtraYarn().length>0){
                bw.newLine();
                bw.write("<table border=1 cellspacing=0>");
                    bw.newLine();
                    bw.write("<caption>"+objDictionaryAction.getWord("EXTRA")+" "+objDictionaryAction.getWord("WARP")+"</caption>");
                    bw.newLine();
                    bw.write("<tr align=right><th>"+objDictionaryAction.getWord("EXTRA")+" "+objDictionaryAction.getWord("WARP")+"</th><th>"+objDictionaryAction.getWord("NAME")+"</th><th>"+objDictionaryAction.getWord("YARNTYPE")+"</th><th>"+objDictionaryAction.getWord("YARNREPEAT")+"</th><th>"+objDictionaryAction.getWord("YARNCOUNT")+"</th><th>"+objDictionaryAction.getWord("YARNUNIT")+"</th><th>"+objDictionaryAction.getWord("YARNPLY")+"</th><th>"+objDictionaryAction.getWord("YARNFACTOR")+"</th><th>"+objDictionaryAction.getWord("YARNDIAMETER")+"(mm)</th><th>"+objDictionaryAction.getWord("YARNWEIGHT")+"(kg)</th><th>"+objDictionaryAction.getWord("YARNPRICE")+"(kg)</th><th>"+objDictionaryAction.getWord("YARNTWIST")+"/ inch</th><th>"+objDictionaryAction.getWord("YARNSENCE")+"</th><th>"+objDictionaryAction.getWord("YARNHAIRNESS")+"</th><th>"+objDictionaryAction.getWord("YARNDISTRIBUTION")+"</th><th>"+objDictionaryAction.getWord("YARNCOLOR")+"</th></tr>");
                    for(int i=0; i<objFabric.getIntExtraWarp(); i++){
                        bw.newLine();
                        bw.write("<tr align=right><td>"+objFabric.getWarpExtraYarn()[i].getStrYarnSymbol()+"</td><td>"+objFabric.getWarpExtraYarn()[i].getStrYarnName()+"</td><td></td><td>"+objFabric.getWarpExtraYarn()[i].getStrYarnType()+"</td><td>"+objFabric.getWarpExtraYarn()[i].getIntYarnRepeat()+"</td><td>"+objFabric.getWarpExtraYarn()[i].getIntYarnCount()+"</td><td>"+objFabric.getWarpExtraYarn()[i].getStrYarnCountUnit()+"</td><td>"+objFabric.getWarpExtraYarn()[i].getIntYarnPly()+"</td><td>"+objFabric.getWarpExtraYarn()[i].getIntYarnDFactor()+"</td><td>"+objFabric.getWarpExtraYarn()[i].getDblYarnDiameter()+"</td><td>-</td><td>"+objFabric.getWarpExtraYarn()[i].getDblYarnPrice()+"</td><td>"+objFabric.getWarpExtraYarn()[i].getIntYarnTwist()+"</td><td>"+objFabric.getWarpExtraYarn()[i].getStrYarnTModel()+"</td><td>"+objFabric.getWarpExtraYarn()[i].getIntYarnHairness()+"</td><td>"+objFabric.getWarpExtraYarn()[i].getIntYarnHProbability()+"</td><th bgcolor=\""+objFabric.getWarpExtraYarn()[i].getStrYarnColor()+"\">"+objFabric.getWarpExtraYarn()[i].getStrYarnColor()+"</th></tr>");
                    }
                bw.newLine();
                bw.write("</table>");
            }
            if(objFabric.getWeftExtraYarn().length>0){
                bw.newLine();
                bw.write("<table border=1 cellspacing=0>");
                    bw.newLine();
                    bw.write("<caption>"+objDictionaryAction.getWord("EXTRA")+" "+objDictionaryAction.getWord("WEFT")+"</caption>");
                    bw.newLine();
                    bw.write("<tr align=right><th>"+objDictionaryAction.getWord("EXTRA")+" "+objDictionaryAction.getWord("WEFT")+"</th><th>"+objDictionaryAction.getWord("NAME")+"</th><th>"+objDictionaryAction.getWord("YARNTYPE")+"</th><th>"+objDictionaryAction.getWord("YARNREPEAT")+"</th><th>"+objDictionaryAction.getWord("YARNCOUNT")+"</th><th>"+objDictionaryAction.getWord("YARNUNIT")+"</th><th>"+objDictionaryAction.getWord("YARNPLY")+"</th><th>"+objDictionaryAction.getWord("YARNFACTOR")+"</th><th>"+objDictionaryAction.getWord("YARNDIAMETER")+"(mm)</th><th>"+objDictionaryAction.getWord("YARNWEIGHT")+"(kg)</th><th>"+objDictionaryAction.getWord("YARNPRICE")+"(kg)</th><th>"+objDictionaryAction.getWord("YARNTWIST")+"/ inch</th><th>"+objDictionaryAction.getWord("YARNSENCE")+"</th><th>"+objDictionaryAction.getWord("YARNHAIRNESS")+"</th><th>"+objDictionaryAction.getWord("YARNDISTRIBUTION")+"</th><th>"+objDictionaryAction.getWord("YARNCOLOR")+"</th></tr>");
                    for(int i=0; i<objFabric.getIntExtraWeft(); i++){
                        bw.newLine();
                        bw.write("<tr align=right><td>"+objFabric.getWeftExtraYarn()[i].getStrYarnSymbol()+"</td><td>"+objFabric.getWeftExtraYarn()[i].getStrYarnName()+"</td><td>"+objFabric.getWeftExtraYarn()[i].getStrYarnType()+"</td><td>"+objFabric.getWeftExtraYarn()[i].getIntYarnRepeat()+"</td><td>"+objFabric.getWeftExtraYarn()[i].getIntYarnCount()+"</td><td>"+objFabric.getWeftExtraYarn()[i].getStrYarnCountUnit()+"</td><td>"+objFabric.getWeftExtraYarn()[i].getIntYarnPly()+"</td><td>"+objFabric.getWeftExtraYarn()[i].getIntYarnDFactor()+"</td><td>"+objFabric.getWeftExtraYarn()[i].getDblYarnDiameter()+"</td><td>-</td><td>"+objFabric.getWeftExtraYarn()[i].getDblYarnPrice()+"</td><td>"+objFabric.getWeftExtraYarn()[i].getIntYarnTwist()+"</td><td>"+objFabric.getWeftExtraYarn()[i].getStrYarnTModel()+"</td><td>"+objFabric.getWeftExtraYarn()[i].getIntYarnHairness()+"</td><td>"+objFabric.getWeftExtraYarn()[i].getIntYarnHProbability()+"</td><th bgcolor=\""+objFabric.getWeftExtraYarn()[i].getStrYarnColor()+"\">"+objFabric.getWeftExtraYarn()[i].getStrYarnColor()+"</th></tr>");
                    }
                bw.newLine();
                bw.write("</table>");
            }
            bw.newLine();
            bw.write("<br><b>"+objDictionaryAction.getWord("GRAPHVIEW")+"<b><br>");
            bw.newLine();
            bw.write(IMAGE_TEXT);
            bw.newLine();
            bw.write("<br>"+INITIAL_TEXT+"<br>");
            bw.newLine();
            } catch (IOException ex) {
                new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            } catch (SQLException ex) {
                new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            } catch (Exception ex) {
                new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
            
            ArrayList<File> filesToZip=new ArrayList<>();
            filesToZip.add(pngFile);
            filesToZip.add(htmlFile);
            String zipFilePath=path+".zip";
            String passwordToZip = ifile.getName()+"Fabric_"+objFabric.getStrFabricID()+"_"+currentDate;
            new EncryptZip(zipFilePath, filesToZip, passwordToZip);
            pngFile.delete();
            htmlFile.delete();
            
            lblStatus.setText("HTML "+objDictionaryAction.getWord("EXPORTEDTO")+" "+path+".html");
        } catch (IOException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
 /**
 * saveFabric
 * <p>
 * Function use for saving fabric.
 * 
 * @exception (@throws )
 * @value
 * @author Amit Kumar Singh
 * @version     %I%, %G%
 * @since 1.0
 */    
public void saveFabric(){
    try{
        objFabric.setStrFabricID(new IDGenerator().getIDGenerator("FABRIC_LIBRARY", objConfiguration.getObjUser().getStrUserID()));
        locked = true;
        objFabricAction = new FabricAction();
        objFabricAction.fabricImage(objFabric,objFabric.getIntWeft(),objFabric.getIntWarp());
        objFabricAction = new FabricAction();
        if(objFabricAction.setFabric(objFabric)>0){
            objFabricAction = new FabricAction();
            objFabricAction.setFabricPallets(objFabric);
            //objFabricAction = new FabricAction();
            //objFabricAction.setFabricYarn(objFabric);
            //objFabricAction.clearFabricYarn(objFabric.getStrFabricID());
            String yarnId = null;
            YarnAction objYarnAction;
            for(int i = 0; i<objFabric.getWarpYarn().length; i++){
                yarnId = new IDGenerator().getIDGenerator("YARN_LIBRARY", objConfiguration.getObjUser().getStrUserID());
                objFabric.getWarpYarn()[i].setStrYarnId(yarnId);
                objFabric.getWarpYarn()[i].setObjConfiguration(objConfiguration);
                objYarnAction = new YarnAction();
                objYarnAction.setYarn(objFabric.getWarpYarn()[i]);
                /*objFabricAction = new FabricAction();
                yarnId = objFabricAction.getYarnID(objFabric.getWarpYarn()[i]);
                objFabric.getWarpYarn()[i].setThreadId(yarnId);
                */objYarnAction = new YarnAction();
                objYarnAction.setFabricYarn(objFabric.getStrFabricID(), objFabric.getWarpYarn()[i],i);
            }
            for(int i = 0; i<objFabric.getWeftYarn().length; i++){
                yarnId = new IDGenerator().getIDGenerator("YARN_LIBRARY", objConfiguration.getObjUser().getStrUserID());
                objFabric.getWeftYarn()[i].setStrYarnId(yarnId);
                objFabric.getWeftYarn()[i].setObjConfiguration(objConfiguration);
                objYarnAction = new YarnAction();
                objYarnAction.setYarn(objFabric.getWeftYarn()[i]);
                /*objYarnAction = new YarnAction();
                yarnId = objYarnAction.getYarnID(objFabric.getWarpYarn()[i]);
                objFabric.getWarpYarn()[i].setThreadId(yarnId);
                */objYarnAction = new YarnAction();
                objYarnAction.setFabricYarn(objFabric.getStrFabricID(), objFabric.getWeftYarn()[i],i);
            }
            if(objFabric.getWarpExtraYarn()!=null){
                for(int i = 0; i<objFabric.getWarpExtraYarn().length; i++){
                    yarnId = new IDGenerator().getIDGenerator("YARN_LIBRARY", objConfiguration.getObjUser().getStrUserID());
                    objFabric.getWarpExtraYarn()[i].setStrYarnId(yarnId);
                    objFabric.getWarpExtraYarn()[i].setObjConfiguration(objConfiguration);
                    objYarnAction = new YarnAction();
                    objYarnAction.setYarn(objFabric.getWarpExtraYarn()[i]);
                    /*objFabricAction = new FabricAction();
                    yarnId = objFabricAction.getYarnID(objFabric.getWarpYarn()[i]);
                    objFabric.getWarpYarn()[i].setThreadId(yarnId);
                    */objYarnAction = new YarnAction();
                    objYarnAction.setFabricYarn(objFabric.getStrFabricID(), objFabric.getWarpExtraYarn()[i],i);
                }
            }
            if(objFabric.getWeftExtraYarn()!=null){
                for(int i = 0; i<objFabric.getWeftExtraYarn().length; i++){
                    yarnId = new IDGenerator().getIDGenerator("YARN_LIBRARY", objConfiguration.getObjUser().getStrUserID());
                    objFabric.getWeftExtraYarn()[i].setStrYarnId(yarnId);
                    objFabric.getWeftExtraYarn()[i].setObjConfiguration(objConfiguration);
                    objYarnAction = new YarnAction();
                    objYarnAction.setYarn(objFabric.getWeftExtraYarn()[i]);
                    /*objFabricAction = new FabricAction();
                    yarnId = objFabricAction.getYarnID(objFabric.getWarpYarn()[i]);
                    objFabric.getWarpYarn()[i].setThreadId(yarnId);
                    */objYarnAction = new YarnAction();
                    objYarnAction.setFabricYarn(objFabric.getStrFabricID(), objFabric.getWeftExtraYarn()[i],i);
                }
            }                        
            if(objFabric.getStrArtworkID()!=null){
                objFabricAction = new FabricAction();
                objFabricAction.setFabricArtwork(objFabric);
            }
        }
        isNew = false;
        saveFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/save.png"));
        //saveFileVB.setVisible(true);
        saveFileVB.setDisable(false);
        lblStatus.setText(objFabric.getStrFabricName()+" : "+objDictionaryAction.getWord("DATASAVED"));
    } catch (SQLException ex) {
        Logger.getLogger(FabricView.class.getName()).log(Level.SEVERE, null, ex);
        new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    } catch (Exception ex) {
        Logger.getLogger(FabricView.class.getName()).log(Level.SEVERE, null, ex);
        new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}

 /**
 * updateFabric
 * <p>
 * Function use for saving fabric.
 * 
 * @exception (@throws )
 * @value
 * @author Amit Kumar Singh
 * @version     %I%, %G%
 * @since 1.0
 */    
    public void updateFabric(){
        try{
            locked = true;
            objFabricAction = new FabricAction();
            objFabricAction.fabricImage(objFabric,objFabric.getIntWeft(),objFabric.getIntWarp());
            objFabricAction = new FabricAction();
            objFabricAction.resetFabric(objFabric);
            objFabricAction = new FabricAction();
            objFabricAction.resetFabricPallets(objFabric);
            
            YarnAction objYarnAction = new YarnAction();
            //objFabricAction.setFabricYarn(objFabric);
            objYarnAction.clearFabricYarn(objFabric.getStrFabricID());
            String yarnId = null;
            for(int i = 0; i<objFabric.getWarpYarn().length; i++){
                yarnId = new IDGenerator().getIDGenerator("YARN_LIBRARY", objConfiguration.getObjUser().getStrUserID());
                objFabric.getWarpYarn()[i].setStrYarnId(yarnId);
                objFabric.getWarpYarn()[i].setObjConfiguration(objConfiguration);
                objYarnAction = new YarnAction();
                objYarnAction.setYarn(objFabric.getWarpYarn()[i]);
                objYarnAction = new YarnAction();
                objYarnAction.setFabricYarn(objFabric.getStrFabricID(), objFabric.getWarpYarn()[i],i);
            }
            for(int i = 0; i<objFabric.getWeftYarn().length; i++){
                yarnId = new IDGenerator().getIDGenerator("YARN_LIBRARY", objConfiguration.getObjUser().getStrUserID());
                objFabric.getWeftYarn()[i].setStrYarnId(yarnId);
                objFabric.getWeftYarn()[i].setObjConfiguration(objConfiguration);
                objYarnAction = new YarnAction();
                objYarnAction.setYarn(objFabric.getWeftYarn()[i]);
                objYarnAction = new YarnAction();
                objYarnAction.setFabricYarn(objFabric.getStrFabricID(), objFabric.getWeftYarn()[i],i);
            }
            if(objFabric.getWarpExtraYarn()!=null){
                for(int i = 0; i<objFabric.getWarpExtraYarn().length; i++){
                    yarnId = new IDGenerator().getIDGenerator("YARN_LIBRARY", objConfiguration.getObjUser().getStrUserID());
                    objFabric.getWarpExtraYarn()[i].setStrYarnId(yarnId);
                    objFabric.getWarpExtraYarn()[i].setObjConfiguration(objConfiguration);
                    objYarnAction = new YarnAction();
                    objYarnAction.setYarn(objFabric.getWarpExtraYarn()[i]);
                    objYarnAction = new YarnAction();
                    objYarnAction.setFabricYarn(objFabric.getStrFabricID(), objFabric.getWarpExtraYarn()[i],i);
                }
            }
            if(objFabric.getWeftExtraYarn()!=null){
                for(int i = 0; i<objFabric.getWeftExtraYarn().length; i++){
                    yarnId = new IDGenerator().getIDGenerator("YARN_LIBRARY", objConfiguration.getObjUser().getStrUserID());
                    objFabric.getWeftExtraYarn()[i].setStrYarnId(yarnId);
                    objFabric.getWeftExtraYarn()[i].setObjConfiguration(objConfiguration);
                    objYarnAction = new YarnAction();
                    objYarnAction.setYarn(objFabric.getWeftExtraYarn()[i]);
                    objYarnAction = new YarnAction();
                    objYarnAction.setFabricYarn(objFabric.getStrFabricID(), objFabric.getWeftExtraYarn()[i],i);
                }
            }
            if(objFabric.getStrArtworkID()!=null){
                objFabricAction = new FabricAction();
                objFabricAction.clearFabricArtwork(objFabric.getStrFabricID());
                objFabricAction = new FabricAction();
                objFabricAction.setFabricArtwork(objFabric);
            }
            lblStatus.setText(objFabric.getStrFabricName()+" : "+objDictionaryAction.getWord("DATASAVED"));
        } catch (SQLException ex) {
            Logger.getLogger(FabricView.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            Logger.getLogger(FabricView.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }    
    }
    public void saveUpdateFabric(){
        // if fabric access is Public, we need not show dialog for Save
        if(!isNew && objFabric.getStrFabricAccess().equalsIgnoreCase("Public")){
            updateFabric();
        } else {
            final Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            GridPane popup=new GridPane();
            popup.setId("popup");
            popup.setHgap(5);
            popup.setVgap(5);
            
            final TextField txtName = new TextField(objFabric.getStrFabricName());
            if(isNew){
                Label lblName = new Label(objDictionaryAction.getWord("NAME"));
                lblName.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNAME")));
                popup.add(lblName, 0, 0);
                txtName.setPromptText(objDictionaryAction.getWord("TOOLTIPNAME"));
                txtName.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNAME")));
                popup.add(txtName, 1, 0, 2, 1);
            }
            
            final ToggleGroup fabricTG = new ToggleGroup();
            RadioButton fabricPublicRB = new RadioButton(objDictionaryAction.getWord("PUBLIC"));
            fabricPublicRB.setToggleGroup(fabricTG);
            fabricPublicRB.setUserData("Public");
            popup.add(fabricPublicRB, 0, 1);
            RadioButton fabricPrivateRB = new RadioButton(objDictionaryAction.getWord("PRIVATE"));
            fabricPrivateRB.setToggleGroup(fabricTG);
            fabricPrivateRB.setUserData("Private");
            popup.add(fabricPrivateRB, 1, 1);
            RadioButton fabricProtectedRB = new RadioButton(objDictionaryAction.getWord("PROTECTED"));
            fabricProtectedRB.setToggleGroup(fabricTG);
            fabricProtectedRB.setUserData("Protected");
            popup.add(fabricProtectedRB, 2, 1);
            if(objConfiguration.getObjUser().getUserAccess("FABRIC_LIBRARY").equalsIgnoreCase("Public"))
                fabricTG.selectToggle(fabricPublicRB);
            else if(objConfiguration.getObjUser().getUserAccess("FABRIC_LIBRARY").equalsIgnoreCase("Protected"))
                fabricTG.selectToggle(fabricProtectedRB);
            else
                fabricTG.selectToggle(fabricPrivateRB);

            final PasswordField passPF= new PasswordField();
            final Label lblAlert = new Label();
            if(objConfiguration.getBlnAuthenticateService()){
                Label lblPassword=new Label(objDictionaryAction.getWord("SERVICEPASSWORD"));
                lblPassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                popup.add(lblPassword, 0, 2);
                passPF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                passPF.setPromptText(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
                popup.add(passPF, 1, 2, 2, 1);
                lblAlert.setStyle("-fx-wrap-text:true;");
                lblAlert.setPrefWidth(250);
                popup.add(lblAlert, 0, 4, 3, 1);
            }

            Button btnOK = new Button(objDictionaryAction.getWord("SAVE"));
            btnOK.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVE")));
            btnOK.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
            btnOK.setDefaultButton(true);
            btnOK.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {  
                    objFabric.setStrFabricAccess(fabricTG.getSelectedToggle().getUserData().toString());
                    //System.out.println(objFabric.getStrFabricAccess());
                    if(objConfiguration.getBlnAuthenticateService()){
                        if(passPF.getText()!=null && passPF.getText()!="" && passPF.getText().trim().length()!=0){
                            if(Security.SecurePassword(passPF.getText(), objConfiguration.getObjUser().getStrUsername()).equals(objConfiguration.getObjUser().getStrAppPassword())){
                                if(isNew){
                                    if(txtName.getText()!=null && txtName.getText()!="" && txtName.getText().trim().length()!=0)
                                        objFabric.setStrFabricName(txtName.getText().toString());
                                    else
                                        objFabric.setStrFabricName("My Fabric");
                                    saveFabric();
                                } else{
                                    updateFabric();
                                }
                                dialogStage.close();
                            } else{
                                lblAlert.setText(objDictionaryAction.getWord("INVALIDSERVICEPASSWORD"));
                            }
                        } else {                            
                            lblAlert.setText(objDictionaryAction.getWord("NOSERVICEPASSWORD"));
                        }
                    } else{   // service password is disabled
                        if(isNew){
                            if(txtName.getText()!=null && txtName.getText()!="" && txtName.getText().trim().length()!=0)
                                objFabric.setStrFabricName(txtName.getText().toString());
                            else
                                objFabric.setStrFabricName("My fabric");                        
                            saveFabric();
                        } else {
                            updateFabric();
                        }
                        dialogStage.close();
                    }
                }
            });
            popup.add(btnOK, 1, 3);
            Button btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
            btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
            btnCancel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
            btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {  
                    dialogStage.close();
                    lblStatus.setText(objDictionaryAction.getWord("TOOLTIPCANCEL"));
               }
            });
            popup.add(btnCancel, 0, 3);
            Scene scene = new Scene(popup, 300, 220);
            scene.getStylesheets().add(FabricView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
            dialogStage.setScene(scene);
            dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("SAVE"));
            dialogStage.showAndWait();
        }
    }
    
/**
 * initFabricValue
 * <p>
 * Function use for saving fabric.
 * 
 * @exception (@throws )
 * @value
 * @author Amit Kumar Singh
 * @version     %I%, %G%
 * @since 1.0
 */    
    public void initFabricValue(){
         try {
            locked = false;
            objConfiguration.mapRecentFabric.put(objConfiguration.getStrClothType(),objFabric.getStrFabricID());
            //intVRepeat = (int)(objConfiguration.getDblFabricLength()/objConfiguration.getDblArtworkLength());
            //intHRepeat = (int)(objConfiguration.getDblFabricWidth()/objConfiguration.getDblArtworkWidth());
            warpExtraYarn = objFabric.getWarpExtraYarn();
            weftExtraYarn = objFabric.getWeftExtraYarn();
            threadPaletes = objFabric.getThreadPaletes();
            warpYarn = objFabric.getWarpYarn();
            weftYarn = objFabric.getWeftYarn();
            //objConfiguration = objFabric.getObjConfiguration();
            objFabric.getObjConfiguration().setStrGraphSize(12+"x"+(int)((12*objFabric.getIntPPI())/objFabric.getIntEPI()));
            plotViewActionMode = 1;
            plotViewAction();
        } catch (Exception ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }         
    }
    
    /**
 * loadFabric
 * <p>
 * Function use for saving fabric.
 * 
 * @exception (@throws )
 * @value
 * @author Amit Kumar Singh
 * @version     %I%, %G%
 * @since 1.0
 */    
    public void cloneFabric(Fabric objCFabric){
        try {
            objCFabric.setStrFabricID(objFabric.getStrFabricID());
            objCFabric.setStrFabricName(objFabric.getStrFabricName());
            objCFabric.setStrFabricType(objFabric.getStrFabricType());
            objCFabric.setStrClothType(objFabric.getStrClothType());
            objCFabric.setStrColourType(objFabric.getStrColourType());
            objCFabric.setDblFabricLength(objFabric.getDblFabricLength());
            objCFabric.setDblFabricWidth(objFabric.getDblFabricWidth());
            objCFabric.setDblArtworkLength(objFabric.getDblArtworkLength());
            objCFabric.setDblArtworkWidth(objFabric.getDblArtworkWidth());
            objCFabric.setStrArtworkID(objFabric.getStrArtworkID());
            objCFabric.setStrBaseWeaveID(objFabric.getStrBaseWeaveID());
            objCFabric.setIntWeft(objFabric.getIntWeft());
            objCFabric.setIntWarp(objFabric.getIntWarp());
            objCFabric.setIntExtraWeft(objFabric.getIntExtraWeft());
            objCFabric.setIntExtraWarp(objFabric.getIntExtraWarp());
            objCFabric.setIntPPI(objFabric.getIntPPI());
            objCFabric.setIntEPI(objFabric.getIntEPI());
            objCFabric.setIntHooks(objFabric.getIntHooks());
            objCFabric.setIntHPI(objFabric.getIntHPI());
            objCFabric.setIntReedCount(objFabric.getIntReedCount());
            objCFabric.setIntDents(objFabric.getIntDents());
            objCFabric.setIntTPD(objFabric.getIntTPD());
            objCFabric.setIntShaft(objFabric.getIntShaft());
            objCFabric.setIntProtection(objFabric.getIntProtection());
            objCFabric.setIntBinding(objFabric.getIntBinding());
            objCFabric.setBlnArtworkAssingmentSize(objFabric.getBlnArtworkAssingmentSize());
            objCFabric.setBlnArtworkOutline(objFabric.getBlnArtworkOutline());
            objCFabric.setBlnArtworkAspectRatio(objFabric.getBlnArtworkAspectRatio());
            objCFabric.setStrWarpPatternID(objFabric.getStrWarpPatternID());
            objCFabric.setStrWeftPatternID(objFabric.getStrWeftPatternID());
            objCFabric.setStrFabricFile(objFabric.getStrFabricFile());
            objCFabric.setStrFabricRData(objFabric.getStrFabricRData());
            objCFabric.setStrFabricDate(objFabric.getStrFabricDate());
            objCFabric.setColorArtwork(objFabric.getColorArtwork());
            objCFabric.setColorCountArtwork(objFabric.getColorCountArtwork());
            objCFabric.setStrFabricAccess(objFabric.getStrFabricAccess());
            
            byte[] bytFabricIcon = new byte[objFabric.getBytFabricIcon().length];
            for(int i=0; i<objFabric.getBytFabricIcon().length; i++)
                bytFabricIcon[i] = objFabric.getBytFabricIcon()[i];
            objCFabric.setBytFabricIcon(bytFabricIcon);
            
            byte[][] baseWeaveMatrix = new byte[objFabric.getBaseWeaveMatrix().length][objFabric.getBaseWeaveMatrix()[0].length];
            for(int i=0; i<objFabric.getBaseWeaveMatrix().length; i++)
                for(int j=0; j<objFabric.getBaseWeaveMatrix()[0].length; j++)
                    baseWeaveMatrix[i][j] = objFabric.getBaseWeaveMatrix()[i][j];
            objCFabric.setBaseWeaveMatrix(baseWeaveMatrix);
            
            if(objFabric.getArtworkMatrix()!=null){
                byte[][] artworkMatrix = new byte[objFabric.getArtworkMatrix().length][objFabric.getArtworkMatrix()[0].length];
                for(int i=0; i<objFabric.getArtworkMatrix().length; i++)
                    for(int j=0; j<objFabric.getArtworkMatrix()[0].length; j++)
                        artworkMatrix[i][j] = objFabric.getArtworkMatrix()[i][j];
                objCFabric.setArtworkMatrix(artworkMatrix);
            }
            byte[][] reverseMatrix = new byte[objFabric.getReverseMatrix().length][objFabric.getReverseMatrix()[0].length];
            for(int i=0; i<objFabric.getReverseMatrix().length; i++)
                for(int j=0; j<objFabric.getReverseMatrix()[0].length; j++)
                    reverseMatrix[i][j] = objFabric.getReverseMatrix()[i][j];
            objCFabric.setReverseMatrix(reverseMatrix);
            
            byte[][] fabricMatrix = new byte[objFabric.getFabricMatrix().length][objFabric.getFabricMatrix()[0].length];
            for(int i=0; i<objFabric.getFabricMatrix().length; i++)
                for(int j=0; j<objFabric.getFabricMatrix()[0].length; j++)
                    fabricMatrix[i][j] = objFabric.getFabricMatrix()[i][j];
            objCFabric.setFabricMatrix(fabricMatrix);

            Yarn[] warpYarn = new Yarn[objFabric.getWarpYarn().length];
            for(int i=0; i<objFabric.getWarpYarn().length; i++)
                warpYarn[i] = new Yarn(objFabric.getWarpYarn()[i].getStrYarnId(), objFabric.getWarpYarn()[i].getStrYarnType(), objFabric.getWarpYarn()[i].getStrYarnName(), objFabric.getWarpYarn()[i].getStrYarnColor(), objFabric.getWarpYarn()[i].getIntYarnRepeat(), objFabric.getWarpYarn()[i].getStrYarnSymbol(), objFabric.getWarpYarn()[i].getIntYarnCount(), objFabric.getWarpYarn()[i].getStrYarnCountUnit(), objFabric.getWarpYarn()[i].getIntYarnPly(), objFabric.getWarpYarn()[i].getIntYarnDFactor(), objFabric.getWarpYarn()[i].getDblYarnDiameter(), objFabric.getWarpYarn()[i].getIntYarnTwist(), objFabric.getWarpYarn()[i].getStrYarnTModel(), objFabric.getWarpYarn()[i].getIntYarnHairness(), objFabric.getWarpYarn()[i].getIntYarnHProbability(), objFabric.getWarpYarn()[i].getDblYarnPrice(), objFabric.getWarpYarn()[i].getStrYarnAccess(), objFabric.getWarpYarn()[i].getStrYarnUser(), objFabric.getWarpYarn()[i].getStrYarnDate());
            objCFabric.setWarpYarn(warpYarn);
            
            Yarn[] weftYarn = new Yarn[objFabric.getWeftYarn().length];
            for(int i=0; i<objFabric.getWeftYarn().length; i++)
                weftYarn[i] = new Yarn(objFabric.getWeftYarn()[i].getStrYarnId(), objFabric.getWeftYarn()[i].getStrYarnType(), objFabric.getWeftYarn()[i].getStrYarnName(), objFabric.getWeftYarn()[i].getStrYarnColor(), objFabric.getWeftYarn()[i].getIntYarnRepeat(), objFabric.getWeftYarn()[i].getStrYarnSymbol(), objFabric.getWeftYarn()[i].getIntYarnCount(), objFabric.getWeftYarn()[i].getStrYarnCountUnit(), objFabric.getWeftYarn()[i].getIntYarnPly(), objFabric.getWeftYarn()[i].getIntYarnDFactor(), objFabric.getWeftYarn()[i].getDblYarnDiameter(), objFabric.getWeftYarn()[i].getIntYarnTwist(), objFabric.getWeftYarn()[i].getStrYarnTModel(), objFabric.getWeftYarn()[i].getIntYarnHairness(), objFabric.getWeftYarn()[i].getIntYarnHProbability(), objFabric.getWeftYarn()[i].getDblYarnPrice(), objFabric.getWeftYarn()[i].getStrYarnAccess(), objFabric.getWeftYarn()[i].getStrYarnUser(), objFabric.getWeftYarn()[i].getStrYarnDate());
            objCFabric.setWeftYarn(weftYarn);
            
            if(objFabric.getWarpExtraYarn()!=null){
                Yarn[] warpExtraYarn = new Yarn[objFabric.getWarpExtraYarn().length];
                for(int i=0; i<objFabric.getWarpExtraYarn().length; i++)
                    warpExtraYarn[i] = new Yarn(objFabric.getWarpExtraYarn()[i].getStrYarnId(), objFabric.getWarpExtraYarn()[i].getStrYarnType(), objFabric.getWarpExtraYarn()[i].getStrYarnName(), objFabric.getWarpExtraYarn()[i].getStrYarnColor(), objFabric.getWarpExtraYarn()[i].getIntYarnRepeat(), objFabric.getWarpExtraYarn()[i].getStrYarnSymbol(), objFabric.getWarpExtraYarn()[i].getIntYarnCount(), objFabric.getWarpExtraYarn()[i].getStrYarnCountUnit(), objFabric.getWarpExtraYarn()[i].getIntYarnPly(), objFabric.getWarpExtraYarn()[i].getIntYarnDFactor(), objFabric.getWarpExtraYarn()[i].getDblYarnDiameter(), objFabric.getWarpExtraYarn()[i].getIntYarnTwist(), objFabric.getWarpExtraYarn()[i].getStrYarnTModel(), objFabric.getWarpExtraYarn()[i].getIntYarnHairness(), objFabric.getWarpExtraYarn()[i].getIntYarnHProbability(), objFabric.getWarpExtraYarn()[i].getDblYarnPrice(), objFabric.getWarpExtraYarn()[i].getStrYarnAccess(), objFabric.getWarpExtraYarn()[i].getStrYarnUser(), objFabric.getWarpExtraYarn()[i].getStrYarnDate());
                objCFabric.setWarpExtraYarn(warpExtraYarn);
            }
            
            if(objFabric.getWeftExtraYarn()!=null){            
                Yarn[] weftExtraYarn = new Yarn[objFabric.getWeftExtraYarn().length];
                for(int i=0; i<objFabric.getWeftExtraYarn().length; i++)
                    weftExtraYarn[i] = new Yarn(objFabric.getWeftExtraYarn()[i].getStrYarnId(), objFabric.getWeftExtraYarn()[i].getStrYarnType(), objFabric.getWeftExtraYarn()[i].getStrYarnName(), objFabric.getWeftExtraYarn()[i].getStrYarnColor(), objFabric.getWeftExtraYarn()[i].getIntYarnRepeat(), objFabric.getWeftExtraYarn()[i].getStrYarnSymbol(), objFabric.getWeftExtraYarn()[i].getIntYarnCount(), objFabric.getWeftExtraYarn()[i].getStrYarnCountUnit(), objFabric.getWeftExtraYarn()[i].getIntYarnPly(), objFabric.getWeftExtraYarn()[i].getIntYarnDFactor(), objFabric.getWeftExtraYarn()[i].getDblYarnDiameter(), objFabric.getWeftExtraYarn()[i].getIntYarnTwist(), objFabric.getWeftExtraYarn()[i].getStrYarnTModel(), objFabric.getWeftExtraYarn()[i].getIntYarnHairness(), objFabric.getWeftExtraYarn()[i].getIntYarnHProbability(), objFabric.getWeftExtraYarn()[i].getDblYarnPrice(), objFabric.getWeftExtraYarn()[i].getStrYarnAccess(), objFabric.getWeftExtraYarn()[i].getStrYarnUser(), objFabric.getWeftExtraYarn()[i].getStrYarnDate());
                objCFabric.setWeftExtraYarn(weftExtraYarn);
            }
            
            if(objFabric.getColorWeave()!=null){
                String[][] colorWeave = new String[objFabric.getColorWeave().length][objFabric.getColorWeave()[0].length];
                for(int i=0; i<objFabric.getColorWeave().length; i++)
                    for(int j=0; j<objFabric.getColorWeave()[0].length; j++)
                        colorWeave[i][j] = objFabric.getColorWeave()[i][j];
                objCFabric.setColorWeave(colorWeave);
            }
            String[] threadPaletes = new String[objFabric.getThreadPaletes().length];
            for(int i=0; i<objFabric.getThreadPaletes().length; i++)
                threadPaletes[i] = objFabric.getThreadPaletes()[i];
            objCFabric.setThreadPaletes(threadPaletes);
            
            if(objFabric.getExtraThreadPaletes()!=null){
                String[] threadExtraPaletes = new String[objFabric.getExtraThreadPaletes().length];
                for(int i=0; i<objFabric.getExtraThreadPaletes().length; i++)
                    threadExtraPaletes[i] = objFabric.getExtraThreadPaletes()[i];
                objCFabric.setExtraThreadPaletes(threadExtraPaletes);
            }
            if(objFabric.getLstYarn()!=null){
                ArrayList<Yarn> lstYarn = new ArrayList<Yarn>();
                for(int i=0; i<objFabric.getLstYarn().size(); i++)
                    lstYarn.set(i, new Yarn(objFabric.getLstYarn().get(i).getStrYarnId(), objFabric.getLstYarn().get(i).getStrYarnType(), objFabric.getLstYarn().get(i).getStrYarnName(), objFabric.getLstYarn().get(i).getStrYarnColor(), objFabric.getLstYarn().get(i).getIntYarnRepeat(), objFabric.getLstYarn().get(i).getStrYarnSymbol(), objFabric.getLstYarn().get(i).getIntYarnCount(), objFabric.getLstYarn().get(i).getStrYarnCountUnit(), objFabric.getLstYarn().get(i).getIntYarnPly(), objFabric.getLstYarn().get(i).getIntYarnDFactor(), objFabric.getLstYarn().get(i).getDblYarnDiameter(), objFabric.getLstYarn().get(i).getIntYarnTwist(), objFabric.getLstYarn().get(i).getStrYarnTModel(), objFabric.getLstYarn().get(i).getIntYarnHairness(), objFabric.getLstYarn().get(i).getIntYarnHProbability(), objFabric.getLstYarn().get(i).getDblYarnPrice(), objFabric.getLstYarn().get(i).getStrYarnAccess(), objFabric.getLstYarn().get(i).getStrYarnUser(), objFabric.getLstYarn().get(i).getStrYarnDate()));
                objCFabric.setLstYarn(lstYarn);
            }
            if(objFabric.getLstPattern()!=null){
                ArrayList<Pattern> lstPattern = new ArrayList<Pattern>();
                for(int i=0; i<objFabric.getLstPattern().size(); i++)
                    lstPattern.set(i, new Pattern(objFabric.getLstPattern().get(i).getStrPatternID(), objFabric.getLstPattern().get(i).getStrPattern(), objFabric.getLstPattern().get(i).getIntPatternType(), objFabric.getLstPattern().get(i).getIntPatternRepeat(), objFabric.getLstPattern().get(i).getIntPatternUsed(), objFabric.getLstPattern().get(i).getStrPatternAccess(), objFabric.getLstPattern().get(i).getStrPatternUser(), objFabric.getLstPattern().get(i).getStrPatternDate()));
                objCFabric.setLstPattern(lstPattern);
            }
            if(objFabric.getYarnBI()!=null){
                BufferedImage yarnBI = new BufferedImage(objFabric.getYarnBI().getWidth(), objFabric.getYarnBI().getHeight(), objFabric.getYarnBI().getType());
                Graphics2D g = yarnBI.createGraphics();
                g.drawImage(objFabric.getYarnBI(), 0, 0, objFabric.getYarnBI().getWidth(), objFabric.getYarnBI().getHeight(), null);
                g.dispose();
                objCFabric.setYarnBI(yarnBI);
            }
            objCFabric.setObjConfiguration(objConfiguration);
            
        } catch (Exception ex) {
            Logger.getLogger(WeaveView.class.getName()).log(Level.SEVERE, null, ex);
            
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }

/**
 * loadFabric
 * <p>
 * Function use for saving fabric.
 * 
 * @exception (@throws )
 * @value
 * @author Amit Kumar Singh
 * @version     %I%, %G%
 * @since 1.0
 */    
    public void loadFabric(){
        try {
            locked = false;
            isNew = false;
            //saveFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/save.png"));
            //saveFileVB.setDisable(false);
            objConfiguration.mapRecentFabric.put(objConfiguration.getStrClothType(),objFabric.getStrFabricID());
            objFabricAction = new FabricAction();
            //intVRepeat = (int)(objConfiguration.getDblFabricLength()/objConfiguration.getDblArtworkLength());
            //intHRepeat = (int)(objConfiguration.getDblFabricWidth()/objConfiguration.getDblArtworkWidth());
            Weave objWeave = new Weave();
            objWeave.setObjConfiguration(objFabric.getObjConfiguration());
            objWeave.setStrWeaveID(objFabric.getStrBaseWeaveID());
            objWeaveAction = new WeaveAction(); 
            objWeaveAction.getWeave(objWeave);
            objWeaveAction = new WeaveAction(); 
            objWeaveAction.extractWeaveContent(objWeave);
            objFabric.setBaseWeaveMatrix(objWeave.getDesignMatrix());
            
            if(objFabric.getStrArtworkID()!=null&&!objFabric.getStrArtworkID().equalsIgnoreCase("null")){                
                Artwork objArtwork = new Artwork();
                objArtwork.setObjConfiguration(objFabric.getObjConfiguration());
                objArtwork.setStrArtworkId(objFabric.getStrArtworkID());
                objFabric.setArtworkMatrix(objFabric.getFabricMatrix());
                objFabricAction = new FabricAction();
                objFabricAction.getFabricArtwork(objFabric);
                YarnAction objYarnAction = new YarnAction();                
                warpExtraYarn = objYarnAction.getFabricYarn(objFabric.getStrFabricID(), "Extra Warp");
                objFabric.setWarpExtraYarn(warpExtraYarn);
                objYarnAction = new YarnAction();
                weftExtraYarn = objYarnAction.getFabricYarn(objFabric.getStrFabricID(), "Extra Weft");
                objFabric.setWeftExtraYarn(weftExtraYarn);
            }else{
                warpExtraYarn = null;
                objFabric.setWarpExtraYarn(warpExtraYarn);
                objFabric.setIntExtraWarp(objConfiguration.getIntExtraWarp());
                weftExtraYarn = null;
                objFabric.setWeftExtraYarn(weftExtraYarn);
                objFabric.setIntExtraWeft(objConfiguration.getIntExtraWeft());
            }
            objFabricAction = new FabricAction();
            threadPaletes = objFabricAction.getFabricPallets(objFabric);
            objFabric.setThreadPaletes(threadPaletes);
            
            YarnAction objYarnAction = new YarnAction();                
            warpYarn = objYarnAction.getFabricYarn(objFabric.getStrFabricID(), "Warp");
            objFabric.setWarpYarn(warpYarn);
            objYarnAction = new YarnAction();
            weftYarn = objYarnAction.getFabricYarn(objFabric.getStrFabricID(), "Weft");
            objFabric.setWeftYarn(weftYarn);
            
            if(objFabric.getStrArtworkID()!=null&&!objFabric.getStrArtworkID().equalsIgnoreCase("null")){
                Artwork objArtwork=new Artwork();
                objArtwork.setObjConfiguration(objFabric.getObjConfiguration());
                objArtwork.setStrArtworkId(objFabric.getStrArtworkID());
                objArtworkAction = new ArtworkAction();
                objArtworkAction.getArtwork(objArtwork);
                byte[] bytArtworkThumbnil=objArtwork.getBytArtworkThumbnil();
                SeekableStream stream = new ByteArraySeekableStream(bytArtworkThumbnil);
                String[] names = ImageCodec.getDecoderNames(stream);
                ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                RenderedImage im = dec.decodeAsRenderedImage();
                BufferedImage bufferedImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
                bytArtworkThumbnil=null;
                System.gc();                        
                ArrayList<java.awt.Color> colors = objArtworkAction.getImageColor(bufferedImage);
                objFabric.setColorCountArtwork(colors.size());
                String[][] colorWeave = new String[colors.size()*colors.size()][4];
                for(int i=0, j=(objFabric.getColorWeave().length<colorWeave.length?objFabric.getColorWeave().length:colorWeave.length);i<j;i++){
                    colorWeave[i][0] = objFabric.getColorWeave()[i][0];
                    colorWeave[i][1] = objFabric.getColorWeave()[i][1];
                    colorWeave[i][2] = objFabric.getColorWeave()[i][2];
                    if(objFabric.getColorWeave()[i][1]!=null)
                        colorWeave[i][3] = Double.toString(objArtworkAction.getImageColorPercentage(bufferedImage,objFabric.getColorWeave()[i][0]));
                    else
                        colorWeave[i][3] = null;
                }
                objFabric.setColorWeave(colorWeave);
                colorWeave = null;
            }
            objFabric.setObjConfiguration(objConfiguration);
            objFabric.getObjConfiguration().setStrGraphSize(12+"x"+(int)((12*objFabric.getIntPPI())/objFabric.getIntEPI()));
            initFabricValue();
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            Logger.getLogger(FabricView.class.getName()).log(Level.SEVERE, null, ex);

            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
 /**
 * newFabric
 * <p>
 * Function use for drawing tool bar for menu item File,
 * and binding events for each tools.
 * 
 * @param  
 * @return
 * @exception (@throws )
 * @value
 * @author Amit Kumar Singh
 * @version     %I%, %G%
 * @since 1.0
 * @see java.awt
 * @link
 * @serial (or @serialField or @serialData)
 * @serialField
 * @serialData  
 * //@deprecated (see How and When To Deprecate APIs)
 */    
    public void newFabric(){
        try {
            locked = false;
            isNew = true;            
            saveFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/save.png"));
            //saveFileVB.setVisible(false);
            saveFileVB.setDisable(true);
            objFabricAction = new FabricAction();
            objFabric.setStrFabricName("My Fabric");
            //objFabric.setStrFabricID(new IDGenerator().getIDGenerator("FABRIC_LIBRARY", objConfiguration.getObjUser().getStrUserID()));
            objFabric.setStrFabricType(objConfiguration.getStrFabricType());
            objFabric.setStrColourType(objConfiguration.getStrColourType());
            objFabric.setStrClothType(objConfiguration.getStrClothType());
            objFabric.setDblFabricLength(objConfiguration.getDblFabricLength());
            objFabric.setDblFabricWidth(objConfiguration.getDblFabricWidth());
            objFabric.setDblArtworkLength(((double) objConfiguration.getIntPixs())/objConfiguration.getIntPPI());//objConfiguration.getDblArtworkLength());
            objFabric.setDblArtworkWidth(((double) objConfiguration.getIntEnds())/objConfiguration.getIntEPI());//objConfiguration.getDblArtworkWidth());
            objFabric.setIntWarp(objConfiguration.getIntEnds());
            objFabric.setIntWeft(objConfiguration.getIntPixs());
            objFabric.setIntHooks(objConfiguration.getIntHooks());
            objFabric.setIntShaft(0);
            objFabric.setIntExtraWarp(objConfiguration.getIntExtraWarp());
            objFabric.setIntExtraWeft(objConfiguration.getIntExtraWeft());
            objFabric.setIntReedCount(objConfiguration.getIntReedCount());
            objFabric.setIntDents(objConfiguration.getIntDents());
            objFabric.setIntTPD(objConfiguration.getIntTPD());
            objFabric.setIntEPI(objConfiguration.getIntEPI());
            objFabric.setIntHPI(objConfiguration.getIntHPI());
            objFabric.setIntPPI(objConfiguration.getIntPPI());
            objFabric.setIntProtection(objConfiguration.getIntProtection());
            objFabric.setIntBinding(objConfiguration.getIntBinding());
            objFabric.setBlnArtworkAssingmentSize(objConfiguration.getBlnArtworkAssingmentSize());
            objFabric.setBlnArtworkOutline(objConfiguration.getBlnArtworkOutline());
            objFabric.setBlnArtworkAspectRatio(objConfiguration.getBlnArtworkAspectRatio());
            //intVRepeat = (int)(objConfiguration.getDblFabricLength()/objConfiguration.getDblArtworkLength());
            //intHRepeat = (int)(objConfiguration.getDblFabricWidth()/objConfiguration.getDblArtworkWidth());
            objFabric.setStrArtworkID(null);
            Weave objWeave = new Weave();
            objWeave.setObjConfiguration(objFabric.getObjConfiguration());
            objWeave.setStrWeaveID(objFabric.getStrBaseWeaveID());
            objWeaveAction = new WeaveAction(); 
            objWeaveAction.getWeave(objWeave);
            objWeaveAction = new WeaveAction(objWeave,true); 
            objWeaveAction.extractWeaveContent(objWeave);
            objFabric.setBaseWeaveMatrix(objWeave.getDesignMatrix());
            objArtworkAction= new ArtworkAction();            
            byte[][] fabricMatrix = new byte[objFabric.getIntWeft()][objFabric.getIntWarp()];
            fabricMatrix = objArtworkAction.repeatMatrix(objWeave.getDesignMatrix(),objFabric.getIntWeft(),objFabric.getIntWarp());
            byte[][] reverseMatrix = new byte[objFabric.getIntWeft()][objFabric.getIntWarp()];
            for(int i = 0; i < objFabric.getIntWeft(); i++) {
                for(int j = 0; j < objFabric.getIntWarp(); j++) {
                    if(fabricMatrix[i][j]==0){
                         reverseMatrix[i][j] = 1;  
                    }else{
                         reverseMatrix[i][j] = 0;
                    }                
                }
            } 
            objFabric.setFabricMatrix(fabricMatrix);
            objFabric.setReverseMatrix(reverseMatrix);
            reverseMatrix=null;
            fabricMatrix = null;            
            objFabric.setThreadPaletes(objConfiguration.getColourPalette());
            Yarn objYarn = null;
            objYarn = new Yarn(null, "Warp", objConfiguration.getStrWarpName(), "#"+objConfiguration.getStrWarpColor(), objConfiguration.getIntWarpRepeat(), "A", objConfiguration.getIntWarpCount(), objConfiguration.getStrWarpUnit(), objConfiguration.getIntWarpPly(), objConfiguration.getIntWarpFactor(), objConfiguration.getDblWarpDiameter(), objConfiguration.getIntWarpTwist(), objConfiguration.getStrWarpSence(), objConfiguration.getIntWarpHairness(), objConfiguration.getIntWarpDistribution(), objFabric.getObjConfiguration().getDblWarpPrice(), objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"),objConfiguration.getObjUser().getStrUserID(),null);
            objYarn.setObjConfiguration(objConfiguration);
            warpYarn = new Yarn[]{objYarn};
            objYarn = new Yarn(null, "Weft", objConfiguration.getStrWeftName(), "#"+objConfiguration.getStrWeftColor(), objConfiguration.getIntWeftRepeat(), "a", objConfiguration.getIntWeftCount(), objConfiguration.getStrWeftUnit(), objConfiguration.getIntWeftPly(), objConfiguration.getIntWeftFactor(), objConfiguration.getDblWeftDiameter(), objConfiguration.getIntWeftTwist(), objConfiguration.getStrWeftSence(), objConfiguration.getIntWeftHairness(), objConfiguration.getIntWeftDistribution(), objFabric.getObjConfiguration().getDblWeftPrice(), objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"),objConfiguration.getObjUser().getStrUserID(),null);
            objYarn.setObjConfiguration(objConfiguration);
            weftYarn = new Yarn[]{objYarn};
            weftExtraYarn = null;
            warpExtraYarn = null;
            objFabric.setWarpYarn(warpYarn);
            objFabric.setWeftYarn(weftYarn);
            objFabric.setWarpExtraYarn(warpExtraYarn);
            objFabric.setWeftExtraYarn(weftExtraYarn);
            objFabricAction = new FabricAction();
            objFabric.setStrWarpPatternID(objFabricAction.getPatternAvibilityID(objConfiguration.getStrWarpPattern(),(byte)1));
            objFabricAction = new FabricAction();
            objFabric.setStrWeftPatternID(objFabricAction.getPatternAvibilityID(objConfiguration.getStrWeftPattern(),(byte)2));
            objFabric.setObjConfiguration(objConfiguration);
            objFabric.getObjConfiguration().setStrGraphSize(12+"x"+(int)((12*objFabric.getIntPPI())/objFabric.getIntEPI()));
            
            initFabricValue();
            if(objConfiguration.getStrRecentArtwork()!=null && objConfiguration.getStrRecentArtwork()!="" && objConfiguration.strWindowFlowContext.equalsIgnoreCase("ArtworkEditor")){
                editArtwork();
            }
        } catch (SQLException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
 
    private void infoSettingsPane(){
        final Stage dialogStage = new Stage();
        dialogStage.initOwner(fabricStage);
        dialogStage.initStyle(StageStyle.UTILITY);
        //dialogStage.initModality(Modality.WINDOW_MODAL);
        GridPane popup=new GridPane();
        popup.setId("popup");
        popup.setHgap(10);
        popup.setVgap(10);

        Label clothType = new Label(objDictionaryAction.getWord("CLOTHTYPE")+" : ");
        clothType.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLOTHTYPE")));
        popup.add(clothType, 0, 0);
        final ComboBox clothTypeCB = new ComboBox();
        clothTypeCB.getItems().addAll("Body","Palu","Border","Cross Border","Blouse","Skart","Konia");
        clothTypeCB.setValue(objFabric.getStrClothType());
        clothTypeCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLOTHTYPE")));
        popup.add(clothTypeCB, 1, 0);        
        
        final CheckBox multiplePrintCB = new CheckBox(objDictionaryAction.getWord("MPRINT"));
        multiplePrintCB.setSelected(objConfiguration.getBlnMPrint());
        multiplePrintCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
           public void changed(ObservableValue<? extends Boolean> ov,
             Boolean old_val, Boolean new_val) {
             objConfiguration.setBlnMPrint(multiplePrintCB.isSelected());
          }
        });
        popup.add(multiplePrintCB, 0, 1, 2, 1);
        
        final CheckBox punchCardCB = new CheckBox(objDictionaryAction.getWord("PUNCHCARDCB"));
        punchCardCB.setSelected(objConfiguration.getBlnPunchCard());
        punchCardCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
           public void changed(ObservableValue<? extends Boolean> ov,
             Boolean old_val, Boolean new_val) {
             objConfiguration.setBlnPunchCard(punchCardCB.isSelected());
             plotViewAction();
          }
        });
        popup.add(punchCardCB, 0, 2, 2, 1);
        
        final CheckBox multipleRepeatCB = new CheckBox(objDictionaryAction.getWord("MREPEAT"));
        multipleRepeatCB.setSelected(objConfiguration.getBlnMRepeat());
        multipleRepeatCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
           public void changed(ObservableValue<? extends Boolean> ov,
             Boolean old_val, Boolean new_val) {
             objConfiguration.setBlnMRepeat(multipleRepeatCB.isSelected());
             plotViewAction();
          }
        });
        popup.add(multipleRepeatCB, 0, 3, 2, 1);
       
        punchCardCB.setDisable(true);
        multipleRepeatCB.setDisable(true);
        
        if(plotViewActionMode==3)
            punchCardCB.setDisable(false);
        else if(plotViewActionMode!=2)
            multipleRepeatCB.setDisable(false);
                
        Separator sepHor1 = new Separator();
        sepHor1.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor1, 0, 4);
        GridPane.setColumnSpan(sepHor1, 2);
        popup.getChildren().add(sepHor1);
        
        final Label fabricDetails = new Label();
        objFabric.getObjConfiguration().setStrGraphSize(12+"x"+(int)((12*objFabric.getIntPPI())/objFabric.getIntEPI()));
        fabricDetails.setText(objDictionaryAction.getWord("FABRICLENGTH")+"(inch):"+objFabric.getDblFabricLength()
                        +"\n"+objDictionaryAction.getWord("FABRICWIDTH")+"(inch):"+objFabric.getDblFabricWidth()
                        +"\n"+objDictionaryAction.getWord("REEDCOUNT")+":"+objFabric.getIntReedCount()
                        +"\n"+objDictionaryAction.getWord("DENTS")+":"+objFabric.getIntDents()
                        +"\n"+objDictionaryAction.getWord("TPD")+":"+objFabric.getIntTPD()
                        +"\n"+objDictionaryAction.getWord("HOOKS")+":"+objFabric.getIntHooks()
                        +"\n"+objDictionaryAction.getWord("ENDS")+":"+objFabric.getIntWarp()
                        +"\n"+objDictionaryAction.getWord("PICKS")+":"+objFabric.getIntWeft()
                        +"\n"+objDictionaryAction.getWord("SHAFT")+":"+objFabric.getIntShaft()
                        +"\n"+objDictionaryAction.getWord("HPI")+":"+objFabric.getIntHPI()
                        +"\n"+objDictionaryAction.getWord("EPI")+":"+objFabric.getIntEPI()
                        +"\n"+objDictionaryAction.getWord("PPI")+":"+objFabric.getIntPPI()
                        +"\n"+objDictionaryAction.getWord("GRAPHSIZE")+":"+objFabric.getObjConfiguration().getStrGraphSize());
        popup.add(fabricDetails, 0, 5, 2, 1);
       
        clothTypeCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, final String st, final String st1) {
               try {
                    final Stage dialogStage = new Stage();
                    dialogStage.initStyle(StageStyle.UTILITY);
                    dialogStage.initModality(Modality.APPLICATION_MODAL);
                    dialogStage.setResizable(false);
                    dialogStage.setIconified(false);
                    dialogStage.setFullScreen(false);
                    dialogStage.setTitle(objDictionaryAction.getWord("ALERT"));
                    BorderPane root = new BorderPane();
                    Scene scene = new Scene(root, 300, 100, Color.WHITE);
                    scene.getStylesheets().add(FabricView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                    final GridPane popup=new GridPane();
                    popup.setId("popup");
                    popup.setHgap(5);
                    popup.setVgap(5);
                    popup.setPadding(new Insets(25, 25, 25, 25));
                    popup.add(new ImageView(objConfiguration.getStrColour()+"/stop.png"), 0, 0);
                    Label lblAlert = new Label(objDictionaryAction.getWord("ALERTCHANGE"));
                    lblAlert.setStyle("-fx-wrap-text:true;");
                    lblAlert.setPrefWidth(250);
                    popup.add(lblAlert, 1, 0);
                    Button btnYes = new Button(objDictionaryAction.getWord("YES"));
                    btnYes.setPrefWidth(50);
                    btnYes.setId("btnYes");
                    btnYes.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent t) {
                            try {
                                if(objFabric.getStrFabricID()!=null && !isNew)
                                    objConfiguration.mapRecentFabric.put(objConfiguration.getStrClothType(),objFabric.getStrFabricID());
                                dialogStage.close();
                                objConfiguration.setStrClothType(st1);
                                UserAction objUserAction = new UserAction();
                                objUserAction.getConfiguration(objConfiguration);
                                if(objConfiguration.mapRecentFabric.get(objConfiguration.getStrClothType())!=null)
                                    objFabric.setStrFabricID(objConfiguration.mapRecentFabric.get(objConfiguration.getStrClothType()));
                                else
                                    objFabric.setStrFabricID(objConfiguration.getStrDefaultFabric());
                                FabricAction objFabricAction = new FabricAction();        
                                objFabricAction.getFabric(objFabric);
                                objFabric.setObjConfiguration(objConfiguration);
                                objConfiguration.clothRepeat();
                                objFabric.getObjConfiguration().setStrGraphSize(12+"x"+(int)((12*objFabric.getIntPPI())/objFabric.getIntEPI()));
                                fabricDetails.setText(objDictionaryAction.getWord("FABRICLENGTH")+"(inch):"+objFabric.getDblFabricLength()
                                                +"\n"+objDictionaryAction.getWord("FABRICWIDTH")+"(inch):"+objFabric.getDblFabricWidth()
                                                +"\n"+objDictionaryAction.getWord("REEDCOUNT")+":"+objFabric.getIntReedCount()
                                                +"\n"+objDictionaryAction.getWord("DENTS")+":"+objFabric.getIntDents()
                                                +"\n"+objDictionaryAction.getWord("TPD")+":"+objFabric.getIntTPD()
                                                +"\n"+objDictionaryAction.getWord("HOOKS")+":"+objFabric.getIntHooks()
                                                +"\n"+objDictionaryAction.getWord("ENDS")+":"+objFabric.getIntWarp()
                                                +"\n"+objDictionaryAction.getWord("PICKS")+":"+objFabric.getIntWeft()
                                                +"\n"+objDictionaryAction.getWord("SHAFT")+":"+objFabric.getIntShaft()
                                                +"\n"+objDictionaryAction.getWord("HPI")+":"+objFabric.getIntHPI()
                                                +"\n"+objDictionaryAction.getWord("EPI")+":"+objFabric.getIntEPI()
                                                +"\n"+objDictionaryAction.getWord("PPI")+":"+objFabric.getIntPPI()
                                                +"\n"+objDictionaryAction.getWord("GRAPHSIZE")+":"+objFabric.getObjConfiguration().getStrGraphSize());
                                punchCardCB.setSelected(objConfiguration.getBlnPunchCard());
                                loadFabric();
                                System.gc();
                            } catch (SQLException ex) {
                                new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                            } catch (Exception ex) {
                                new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                            }
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
                            objConfiguration.setStrClothType(st);
                            plotViewAction();
                            //clothTypeCB.setValue(st);
                            System.gc();
                        }
                    });
                    popup.add(btnNo, 1, 1);
                    root.setCenter(popup);
                    dialogStage.setScene(scene);
                    dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {                                
                            dialogStage.close();
                            objConfiguration.setStrClothType(st);
                            plotViewAction();
                            //clothTypeCB.setValue(st);
                            System.gc();
                        }
                    });
                    dialogStage.showAndWait();
                    //ov.removeListener(st);
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricView.class.getName(),"cloth type property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });  
    
        Button btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
        btnCancel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
        btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {  
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCANCEL"));
                dialogStage.close();
            }
        });
        popup.add(btnCancel, 1, 8);

        Scene scene = new Scene(popup);
        scene.getStylesheets().add(FabricView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        dialogStage.setScene(scene);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("FABRICSETTINGS"));
        dialogStage.showAndWait();        
    }
    
    public void populateLeftPane(){
        editToolPane.getChildren().clear();
        editToolPane.setVisible(true);
        editToolPane.setPrefWidth(200);
        editToolPane.setVgap(20);
        objConfiguration.setIntBoxSize(1);
        System.gc();
        
        if(!locked && (isEditGraph || isEditWeave)){
            editToolPane.add(new Label(objDictionaryAction.getWord("EDITTHREADTYPE")+" : "), 0, 0);

            GridPane editThreadCB = new GridPane();
            editThreadCB.setAlignment(Pos.CENTER);
            editThreadCB.setHgap(5);
            editThreadCB.setVgap(5);
            editThreadCB.setCursor(Cursor.HAND);
            //editThreadCB.getChildren().clear();
            for(int i=0;i<=objFabric.getIntExtraWeft()+1;i++){
                final Label lblColor  = new Label ();
                lblColor.setPrefSize(33, 33);
                String webColor = "#FFFFFF";
                if(i==0){
                    if(objFabric.getIntExtraWeft()<1){
                        webColor = "#FFFFFF";
                    }
                    lblColor.setTooltip(new Tooltip("Weft \n Color:"+webColor));
                } else if(i==1){
                    if(objFabric.getIntExtraWeft()<1){
                        webColor = "#000000";
                    }
                    lblColor.setTooltip(new Tooltip("Warp \n Color:"+webColor));
                }else{
                    webColor = objFabric.getWeftExtraYarn()[i-2].getStrYarnColor();
                    lblColor.setTooltip(new Tooltip("Extra Weft #"+(i-1)+"\n Color:"+webColor));
                }
                lblColor.setStyle("-fx-background-color:"+webColor+"; -fx-border-color: #FFFFFF;");
                lblColor.setUserData(i);
                lblColor.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        editThreadValue=Integer.parseInt(lblColor.getUserData().toString());
                    }
                });
                editThreadCB.add(lblColor, i%3, i/3);
            }
            editThreadValue=1;
            editToolPane.add(editThreadCB, 0, 1);
        
            if(isEditGraph){
                final CheckBox errorGraphCB = new CheckBox(objDictionaryAction.getWord("SHOWERRORGRAPH"));
                errorGraphCB.setWrapText(true);
                errorGraphCB.setSelected(objConfiguration.getBlnErrorGraph());
                errorGraphCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
                   public void changed(ObservableValue<? extends Boolean> ov,
                     Boolean old_val, Boolean new_val) {
                        objConfiguration.setBlnErrorGraph(errorGraphCB.isSelected());
                        plotEditGraph();
                    }
                });
                editToolPane.add(errorGraphCB, 0, 2);
                
                final CheckBox correctGraphCB = new CheckBox(objDictionaryAction.getWord("SHOWCORRECTGRAPH"));
                correctGraphCB.setWrapText(true);
                correctGraphCB.setSelected(objConfiguration.getBlnCorrectGraph());
                correctGraphCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
                   public void changed(ObservableValue<? extends Boolean> ov,
                     Boolean old_val, Boolean new_val) {
                        objConfiguration.setBlnCorrectGraph(correctGraphCB.isSelected());
                        plotEditGraph();
                        //objConfiguration.setBlnCorrectGraph(false);
                    }
                });
                editToolPane.add(correctGraphCB, 0, 3);
                
                editToolPane.add(new Label(objDictionaryAction.getWord("FLOATBINDSIZE")+" : "), 0, 4);
                final TextField doubleBindTF = new TextField(Integer.toString(objConfiguration.getIntFloatBind())){
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
                doubleBindTF.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                        if(doubleBindTF.getText()!=null && doubleBindTF.getText()!="" && Integer.parseInt(doubleBindTF.getText())>0){
                            objConfiguration.setIntFloatBind(Integer.parseInt(doubleBindTF.getText()));
                            plotEditGraph();
                        }
                    }
                });
                doubleBindTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFLOATBINDSIZE")));
                editToolPane.add(doubleBindTF, 0, 5);
                
                Button btnApply = new Button(objDictionaryAction.getWord("APPLY"));
                btnApply.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
                btnApply.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPAPPLY")));
                btnApply.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {  
                        lblStatus.setText(objDictionaryAction.getWord("ACTIONAPPLY"));
                        
                    }
                });            
                //editToolPane.add(btnApply, 0, 6);
            }
            
            if(isEditWeave){
                editToolPane.setVgap(0);
                editToolPane.add(new Label(objDictionaryAction.getWord("EDITTHREADTOOL")+" : "), 0, 2, 2, 1);
                
                Button mirrorVerticalTP = new Button();
                Button mirrorHorizontalTP = new Button();
                Button inversionTP = new Button();
                Button rotateTP = new Button();
                Button rotateAntiTP = new Button();
                Button moveRightTP = new Button();
                Button moveLeftTP = new Button();
                Button moveUpTP = new Button();
                Button moveDownTP = new Button();
                Button moveRight8TP = new Button();
                Button moveLeft8TP = new Button();
                Button moveUp8TP = new Button();
                Button moveDown8TP = new Button();
                Button tiltRightTP = new Button();
                Button tiltLeftTP = new Button();
                Button tiltUpTP = new Button();
                Button tiltDownTP = new Button();
                
                mirrorVerticalTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/vertical_mirror.png"));
                mirrorHorizontalTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/horizontal_mirror.png"));
                rotateTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/rotate_90.png"));
                rotateAntiTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/rotate_anti_90.png"));
                moveRightTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/move_right.png"));
                moveLeftTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/move_left.png"));
                moveUpTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/move_up.png"));
                moveDownTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/move_down.png"));
                moveRight8TP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/move_right_by_8.png"));
                moveLeft8TP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/move_left_by_8.png"));
                moveUp8TP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/move_up_by_8.png"));
                moveDown8TP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/move_down_by_8.png"));
                tiltRightTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/tilt_right.png"));
                tiltLeftTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/tilt_left.png"));
                tiltUpTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/tilt_up.png"));
                tiltDownTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/tilt_down.png"));
                inversionTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/switch_side.png"));
                
                mirrorVerticalTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPVERTICALMIRROR")));
                mirrorHorizontalTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPHORIZENTALMIRROR")));
                rotateTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLOCKROTATION")));
                rotateAntiTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPANTICLOCKROTATION")));
                moveRightTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVERIGHT")));
                moveLeftTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVELEFT")));
                moveUpTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVEUP")));
                moveDownTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVEDOWN")));
                moveRight8TP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVERIGHT8")));
                moveLeft8TP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVELEFT8")));
                moveUp8TP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVEUP8")));
                moveDown8TP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVEDOWN8")));
                tiltRightTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTILTRIGHT")));
                tiltLeftTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTILTLEFT")));
                tiltUpTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTILTUP")));
                tiltDownTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTILTDOWN")));
                inversionTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPINVERSION")));
                
                mirrorVerticalTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                mirrorHorizontalTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                rotateTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                rotateAntiTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                moveRightTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                moveLeftTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                moveUpTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                moveDownTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                moveRight8TP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                moveLeft8TP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                moveUp8TP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                moveDown8TP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                tiltRightTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                tiltLeftTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                tiltUpTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                tiltDownTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);                    
                inversionTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                
                editToolPane.add(mirrorVerticalTP, 0, 3);
                editToolPane.add(mirrorHorizontalTP, 1, 3);
                editToolPane.add(rotateTP, 0, 4);
                editToolPane.add(rotateAntiTP, 1, 4);
                editToolPane.add(moveRightTP, 0, 5);
                editToolPane.add(moveLeftTP, 1, 5);
                editToolPane.add(moveUpTP, 0, 6);
                editToolPane.add(moveDownTP, 1, 6);
                editToolPane.add(moveRight8TP, 0, 7);
                editToolPane.add(moveLeft8TP, 1, 7);
                editToolPane.add(moveUp8TP, 0, 8);
                editToolPane.add(moveDown8TP, 1, 8);
                editToolPane.add(tiltRightTP, 0, 9);
                editToolPane.add(tiltLeftTP, 1, 9);
                editToolPane.add(tiltUpTP, 0, 10);
                editToolPane.add(tiltDownTP, 1, 10);
                editToolPane.add(inversionTP, 0, 11);

                mirrorVerticalTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {                       
                            lblStatus.setText(objDictionaryAction.getWord("ACTIONVERTICALMIRROR"));
                            objWeaveAction.mirrorVertical(objWeave);
                            objWeaveAction.mirrorVertical(objWeaveR);
                            plotEditWeave();
                        } catch (Exception ex) {
                            new Logging("SEVERE",FabricView.class.getName(),"Mirror Vertical",ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });  
                mirrorHorizontalTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            lblStatus.setText(objDictionaryAction.getWord("ACTIONHORIZENTALMIRROR"));
                            objWeaveAction.mirrorHorizontal(objWeave);
                            objWeaveAction.mirrorHorizontal(objWeaveR);
                            plotEditWeave();
                        } catch (Exception ex) {
                          new Logging("SEVERE",FabricView.class.getName(),"Mirror Vertical",ex);
                          lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                }); 
                rotateTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            lblStatus.setText(objDictionaryAction.getWord("ACTIONCLOCKROTATION"));
                            objWeaveAction.rotation(objWeave);
                            objWeaveAction.rotation(objWeaveR);
                            plotEditWeave();
                        } catch (Exception ex) {
                            new Logging("SEVERE",FabricView.class.getName(),"Rotate Clock Wise ",ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                }); 
                rotateAntiTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            lblStatus.setText(objDictionaryAction.getWord("ACTIONANTICLOCKROTATION"));
                            objWeaveAction.rotationAnti(objWeave);
                            objWeaveAction.rotationAnti(objWeaveR);
                            plotEditWeave();
                        } catch (Exception ex) {
                            new Logging("SEVERE",FabricView.class.getName(),"Anti Rotate Clock Wise ",ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });
                moveRightTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVERIGHT"));
                            objWeaveAction.moveRight(objWeave);
                            objWeaveAction.moveRight(objWeaveR);
                            plotEditWeave();
                        } catch (Exception ex) {
                            new Logging("SEVERE",FabricView.class.getName(),"Move Right",ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });
                moveLeftTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVELEFT"));
                            objWeaveAction.moveLeft(objWeave);
                            objWeaveAction.moveLeft(objWeaveR);
                            plotEditWeave();
                        } catch (Exception ex) {
                            new Logging("SEVERE",FabricView.class.getName(),"Move Left",ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });
                moveUpTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVEUP"));
                            objWeaveAction.moveUp(objWeave);
                            objWeaveAction.moveUp(objWeaveR);
                            plotEditWeave();
                        } catch (Exception ex) {
                            new Logging("SEVERE",FabricView.class.getName(),"Move Up",ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });
                moveDownTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVEDOWN"));
                            objWeaveAction.moveDown(objWeave);
                            objWeaveAction.moveDown(objWeaveR);
                            plotEditWeave();
                        } catch (Exception ex) {
                            new Logging("SEVERE",FabricView.class.getName(),"Move Down",ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });
                 moveRight8TP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVERIGHT8"));
                            objWeaveAction.moveRight8(objWeave);
                            objWeaveAction.moveRight8(objWeaveR);
                            plotEditWeave();
                        } catch (Exception ex) {
                            new Logging("SEVERE",FabricView.class.getName(),"Move Right",ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });
                moveLeft8TP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVELEFT8"));
                            objWeaveAction.moveLeft8(objWeave);
                            objWeaveAction.moveLeft8(objWeaveR);
                            plotEditWeave();
                        } catch (Exception ex) {
                            new Logging("SEVERE",FabricView.class.getName(),"Move Left",ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });
                moveUp8TP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVEUP8"));
                            objWeaveAction.moveUp8(objWeave);
                            objWeaveAction.moveUp8(objWeaveR);
                            plotEditWeave();
                        } catch (Exception ex) {
                            new Logging("SEVERE",FabricView.class.getName(),"Move Up",ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });
                moveDown8TP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVEDOWN8"));
                            objWeaveAction.moveDown8(objWeave);
                            objWeaveAction.moveDown8(objWeaveR);
                            plotEditWeave();
                        } catch (Exception ex) {
                            new Logging("SEVERE",FabricView.class.getName(),"Move Down",ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });
                tiltRightTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            lblStatus.setText(objDictionaryAction.getWord("ACTIONTILTRIGHT"));
                            objWeaveAction.tiltRight(objWeave);
                            objWeaveAction.tiltRight(objWeaveR);
                            plotEditWeave();
                        } catch (Exception ex) {
                            new Logging("SEVERE",FabricView.class.getName(),"Tilt Right ",ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });
                tiltLeftTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            lblStatus.setText(objDictionaryAction.getWord("ACTIONTILTLEFT"));
                            objWeaveAction.tiltLeft(objWeave);
                            objWeaveAction.tiltLeft(objWeaveR);
                            plotEditWeave();
                        } catch (Exception ex) {
                            new Logging("SEVERE",FabricView.class.getName(),"Tilt Left ",ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });
                tiltUpTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            lblStatus.setText(objDictionaryAction.getWord("ACTIONTILTUP"));
                            objWeaveAction.tiltUp(objWeave);
                            objWeaveAction.tiltUp(objWeaveR);
                            plotEditWeave();
                        } catch (Exception ex) {
                            new Logging("SEVERE",FabricView.class.getName(),"Tilt Up ",ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });
                tiltDownTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            lblStatus.setText(objDictionaryAction.getWord("ACTIONTILTDOWN"));
                            objWeaveAction.tiltDown(objWeave);
                            objWeaveAction.tiltDown(objWeaveR);
                            plotEditWeave();
                        } catch (Exception ex) {
                           new Logging("SEVERE",FabricView.class.getName(),"Tilt Down ",ex);
                           lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });         
                inversionTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            lblStatus.setText(objDictionaryAction.getWord("ACTIONINVERSION"));
                            objWeaveAction.inversion(objWeave);
                            objWeaveAction.inversion(objWeaveR);
                            plotEditWeave();
                        } catch (Exception ex) {
                            new Logging("SEVERE",FabricView.class.getName(),"Inversion ",ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });
            }
        } else{
            editToolPane.setVisible(false);
            editToolPane.setPrefWidth(1);
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        new FabricView(stage);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    public static void main(String[] args) {   
      launch(args);    
    }
}