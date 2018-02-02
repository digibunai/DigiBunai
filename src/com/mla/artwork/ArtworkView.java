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

import com.mla.colour.ColourSelector;
import com.mla.dictionary.DictionaryAction;
import com.mla.fabric.FabricView;
import com.mla.main.AboutView;
import com.mla.main.Configuration;
import com.mla.main.ContactView;
import com.mla.main.EncryptZip;
import com.mla.main.HelpView;
import com.mla.main.Logging;
import com.mla.main.TechnicalView;
import com.mla.main.WindowView;
import com.mla.main.IDGenerator;
import com.mla.main.MessageView;
import com.mla.main.UndoRedo;
//import com.mla.main.UndoRedoFile;
import com.mla.secure.Security;
import com.mla.user.UserAction;
import com.mla.utility.Device;
import com.mla.utility.SimulatorEditView;
import com.mla.utility.UtilityAction;
import com.mla.weave.Weave;
import com.mla.weave.WeaveAction;
import com.mla.weave.WeaveEditView;
import com.mla.weave.WeaveImportView;
import com.sun.media.jai.codec.ByteArraySeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.ColorPicker;
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
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
//import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;
/**
 * ArtworkView Class
 * <p>
 * This class is used for defining GUI methods for artwork editor.
 *
 * @author Amit Kumar Singh
 * @version %I%, %G%
 * @since   1.0
 * @date 07/01/2016
 * @Designing GUI method class for artwork editor
 * @see javafx.stage.*;
 */
public class ArtworkView {
    
    Artwork objArtwork;
    ArtworkAction objArtworkAction;
    Configuration objConfiguration;
    DictionaryAction objDictionaryAction = null;
    UndoRedo objUR;
    //UndoRedoFile objURF;
    Weave objWeave;
    
    PrinterJob objPrinterJob = PrinterJob.getPrinterJob();
    javax.print.attribute.HashPrintRequestAttributeSet objPrinterAttribute = new javax.print.attribute.HashPrintRequestAttributeSet();
    //Process objProcess_ProcessBuilder;
    
    private Stage artworkStage;
    private Stage artworkChildStage;
    private BorderPane root;    
    final private Scene scene;
    private ToolBar toolBar;
    private MenuBar menuBar; 
    //private ScrollPane bodyContainer;
    //private StackPane stackPane;
    //private ScrollPane scrollPane;
    private ScrollPane container;
    private GridPane bodyContainer;
    
   /* 
    private RadioButton strokeRB,fillRB;
    private ColorPicker colorPick;
    private Slider sizeSlider;

    private GraphicsContext backgroundGC,foregroundGC, rulerGC;
    private ScrollPane backgroundSP, foregroundSP, rulerSP;
    private Canvas background,foreground,ruler;
    
    private GraphicsContext editLayerGC,gridLayerGC;
    private ScrollPane editLayerSP, gridLayerSP;
    private Canvas editLayer,gridLayer;
    */
    private BufferedImage bufferedImage = null;
    private BufferedImage editBufferedImage = null;
    private ImageView artwork;
    private Graphics2D objGraphics2D;
    //private Canvas objCanvas;
    //private GraphicsContext objGraphicsContext;
    
    private Label hRuler;
    private Label vRuler;
    private ContextMenu contextMenu;
    
    private Menu homeMenu;
    private Menu fileMenu;
    private Menu editMenu;
    private Menu transformMenu;
    private Menu toolBoxMenu;
    private Menu viewMenu;
    private Menu helpMenu;    
    private Menu runMenu;
    private Label fileMenuLabel;
    private Label editMenuLabel;
    private Label transformMenuLabel;
    private Label toolBoxMenuLabel;    
    private Label viewMenuLabel;
    private Label runMenuLabel;
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;
        
    private VBox saveFileVB;    
    private VBox saveAsFileVB;
    private VBox refreshFileVB;
    
    double startX, startY, lastX, lastY, oldX, oldY, width, height, angle;
    float zoomfactor = 1;
    byte graphFactor = 2;
    /* composite = 1, grid = 2, graph = 3, frontside = 4, flipside = 5, switchside = 6, frontcut = 7, rearcut = 8, simulation 9 */
    byte plotViewActionMode = 0; 
    /* editgraph = 1, filltool = 2, freehanddesign = 3, eraser = 4,
    drawline = 5, drawarc = 6, beziercurve = 7, drawoval = 8, drawrectangle = 9, drawtriangle = 10, drawrighttriangle = 11, drawdiamond = 12,
    drawpentagon = 13, drawhexagone = 14, drawheart = 15, drawfourpointstar = 16, drawfivepointstar = 17, drawsixpointstar = 18, drawploygon = 19,
    selectdata = 20, noselectdata = 21, dragselectdata = 22, copydata = 23, pastedata = 24, cutdata = 25,
    mirrorverticaldata = 26, mirrorhorizentadata = 27, rotateclockwisedata = 28, rotateanticlockwisedata = 29;*/
    byte plotEditActionMode = 1; 
    int editThreadValue=1;
    byte resizeLockValue = 2; //1=Pixel 2=Inches 3=density
    byte intFillBorder = 0;
    byte intColor=1;
    boolean isNew=true;
    boolean isWorkingMode = false;
    String strFillType = "Weave Fill";
    String strFillArea = "Color All Instance";
    String strFillBorder = "All Inner Border";
    String filePath;
    ArrayList<java.awt.Color> colors;
    
    /**
    * ArtworkView
    * <p>
    * This constructed is used for defining GUI methods for artwork editor.
    *
    * @author       Amit Kumar Singh
    * @version      %I%, %G%
    * @since        1.0
    * @date         07/01/2016
    * @Designing    GUI method class for artwork editor
    * @see          javafx.stage.*;
    * @param        objConfigurationCall Object Configuration 
    */
    public ArtworkView(Configuration objConfigurationCall) {
        this.objConfiguration  = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);
        objArtwork = new Artwork();
        objArtwork.setObjConfiguration(objConfiguration);
        objUR = new UndoRedo();
        //objURF = new UndoRedoFile();
        //objURF.clear();
        artworkStage = new Stage();
        root = new BorderPane();
        scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(ArtworkView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());

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
        
        // UI Portion for header content as menu and toolbar
        VBox topContainer = new VBox();
        toolBar = new ToolBar();
        toolBar.setMinHeight(95);
        menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(artworkStage.widthProperty());
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
        fileMenu = new Menu();
        fileMenuLabel = new Label(objDictionaryAction.getWord("FILE"));
        fileMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFILE")));
        fileMenu.setGraphic(fileMenuLabel);
        fileMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                fileMenuAction();
            }
        });
        // Edit menu - Toolbar, Color, Composite View, Support Lines, Ruler, Zoom-in, zoom-out
        editMenu = new Menu();
        editMenuLabel = new Label(objDictionaryAction.getWord("EDIT"));
        editMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEDIT")));
        editMenu.setGraphic(editMenuLabel);
        editMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                editMenuAction();
            }
        });
        // Edit menu - Toolbar, Color, Composite View, Support Lines, Ruler, Zoom-in, zoom-out
        transformMenu = new Menu();
        transformMenuLabel = new Label(objDictionaryAction.getWord("TRANSFORM"));
        transformMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTRANSFORM")));
        transformMenu.setGraphic(transformMenuLabel);
        transformMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONTRANSFORM"));
                menuHighlight("TRANSFORM");
            }
        });        
        // Edit menu - Toolbar, Color, Composite View, Support Lines, Ruler, Zoom-in, zoom-out
        toolBoxMenu = new Menu();
        toolBoxMenuLabel = new Label(objDictionaryAction.getWord("TOOLBOX"));
        toolBoxMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTOOLBOX")));
        toolBoxMenu.setGraphic(toolBoxMenuLabel);
        toolBoxMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONTRANSFORM"));
                menuHighlight("TOOLBOX");
            }
        });        
        // View menu - Toolbar, Color, Composite View, Support Lines, Ruler, Zoom-in, zoom-out
        viewMenu = new Menu();
        viewMenuLabel = new Label(objDictionaryAction.getWord("VIEW"));
        viewMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPVIEW")));
        viewMenu.setGraphic(viewMenuLabel);
        viewMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                viewMenuAction();
            }
        });
        Menu helpMenu = new Menu();
        Label helpMenuLabel = new Label(objDictionaryAction.getWord("SUPPORT"));
        helpMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSUPPORT")));
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
        runMenuLabel = new Label();
        runMenuLabel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/run.png"));
        runMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("SIMULATION")));
        runMenu  = new Menu();
        runMenu.setGraphic(runMenuLabel);
        runMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if(isWorkingMode){
                    //ArtworkViewer objArtworkViewer=new ArtworkViewer(objArtwork);
                }
            }
        });
        helpMenu.getItems().addAll(helpMenuItem, technicalMenuItem, aboutMenuItem, contactMenuItem, new SeparatorMenuItem(), exitMenuItem);
        menuBar.getMenus().addAll(homeMenu, fileMenu, editMenu, transformMenu, viewMenu, helpMenu);
    
        container = new ScrollPane();
        container.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        
        //setting context menus
        addContextMenu();
        
        // Code Added for ShortCuts
        addAccelratorKey();
        
        bodyContainer = new GridPane();
        bodyContainer.setMinSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        bodyContainer.setVgap(0);
        bodyContainer.setHgap(0);

        root.setCenter(container);
        
        artwork = new ImageView();
        /*
        objCanvas = new Canvas();
        objGraphicsContext = objCanvas.getGraphicsContext2D();
        //scrollPane.setContent(objCanvas);
        
        stackPane = new StackPane();
        stackPane.getChildren().addAll(artwork);//,objCanvas);
        container.setContent(stackPane);
        root.setCenter(container);
        */
        
        //Manage Events
        addEventHandler();
        
        menuHighlight(null);
        artworkStage.getIcons().add(new Image("/media/icon.png"));
        artworkStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWARTWORKEDITOR")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        artworkStage.setX(-5);
        artworkStage.setY(0);
        artworkStage.setResizable(false);
        artworkStage.setScene(scene);
        artworkStage.show();
        artworkStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                homeMenuAction(); 
                we.consume();
            }
        });
        //Managing contxt flow
        /*if(objConfiguration.getStrRecentArtwork()!=null && objConfiguration.strWindowFlowContext.equalsIgnoreCase("Dashboard")){
            recentArtwork();
        }else{
            newArtwork();
        }*/
        
        if(objConfiguration.getStrRecentArtwork()!=null && objConfiguration.strWindowFlowContext.equalsIgnoreCase("Dashboard")){
            objArtwork = new Artwork();
            objArtwork.setStrArtworkId(objConfiguration.getStrRecentArtwork());
            objArtwork.setObjConfiguration(objConfiguration);
            loadArtwork();
        }
    }
    
    /**
     * homeMenuAction
     * <p>
     * Function use for exit menu item. 
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        ArtworkView
     */
    public void homeMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONHOME"));
        final Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setResizable(false);
        dialogStage.setIconified(false);
        dialogStage.setFullScreen(false);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ALERT"));
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 300, 100, Color.WHITE);
        scene.getStylesheets().add(ArtworkView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        final GridPane popup=new GridPane();
        popup.setId("popup");
        popup.setHgap(5);
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
                artworkStage.close();
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
    /**
     * fileMenuAction
     * <p>
     * Function use for file menu item. 
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        ArtworkView
     */
    public void fileMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONFILE"));
        menuHighlight("FILE");
    }
    /**
     * editMenuAction
     * <p>
     * Function use for edit menu item. 
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        ArtworkView
     */
    public void editMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONEDIT"));
        menuHighlight("EDIT");
    }
    /**
     * viewMenuAction
     * <p>
     * Function use for view menu item. 
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        ArtworkView
     */
    public void viewMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONVIEW"));
        menuHighlight("VIEW");
    }
    /**
     * helpMenuAction
     * <p>
     * Function use for help menu item. 
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        ArtworkView
     */
    public void helpMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONHELP"));
        HelpView objHelpView = new HelpView(objConfiguration);
    }
    /**
     * technicalMenuAction
     * <p>
     * Function use for technical menu item. 
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        ArtworkView
     */
    public void technicalMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONTECHNICAL"));
        TechnicalView objTechnicalView = new TechnicalView(objConfiguration);
    }
    /**
     * aboutMenuAction
     * <p>
     * Function use for about menu item. 
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        ArtworkView
     */
    public void aboutMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONABOUTUS"));
        AboutView objAboutView = new AboutView(objConfiguration);
    }
    /**
     * contactMenuAction
     * <p>
     * Function use for contact menu item. 
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        ArtworkView
     */
    public void contactMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONCONTACTUS"));
        ContactView objContactView = new ContactView(objConfiguration);
    }
    /**
     * exitMenuAction
     * <p>
     * Function use for exit menu item. 
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        ArtworkView
     */
    public void exitMenuAction(){
        System.gc();
        artworkStage.close();
    }
    /**
     * addContextMenu
     * <p>
     * Function use for drawing menu bar for menu item,
     * and binding events for each menus with style. 
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        ArtworkView
     */
    private void addContextMenu(){
        contextMenu = new ContextMenu();
        try{
            UtilityAction objUtilityAction = new UtilityAction();
            Device objDevice = new Device(null, "Designing S/W", null, null);
            objDevice.setObjConfiguration(objConfiguration);
            Device[] devices = objUtilityAction.getDevices(objDevice);
            contextMenu = new ContextMenu();
            for(int i=0; i<devices.length; i++){
                final MenuItem editApplication = new MenuItem(devices[i].getStrDeviceName());
                editApplication.setUserData(devices[i].getStrDeviceId());
                editApplication.setGraphic(new ImageView(objConfiguration.getStrColour()+"/application_integration.png"));
                contextMenu.getItems().add(editApplication);
                editApplication.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        lblStatus.setText(objDictionaryAction.getWord("ARTWORKEDIT"));
                        try {
                            UtilityAction objUtilityAction = new UtilityAction();
                            Device objDevice = new Device(editApplication.getUserData().toString(), null, editApplication.getText(), null);
                            objDevice.setObjConfiguration(objConfiguration);
                            objUtilityAction.getDevice(objDevice);
                            if(objDevice.getStrDevicePath()!=null){
                                Runtime rt = Runtime.getRuntime();
                                if(objArtwork.getObjConfiguration().getObjProcessProcessBuilder()!=null)
                                    objArtwork.getObjConfiguration().getObjProcessProcessBuilder().destroy();
                                File file = new File(objDevice.getStrDevicePath());
                                if(file.exists() && !file.isDirectory()) { 
                                    if(bufferedImage!=null){
                                        filePath = System.getProperty("user.dir") + "\\mla\\tempdesign.png";
                                        refreshFileVB.setDisable(false);
                                        refreshFileVB.setCursor(Cursor.HAND);
                                        file = new File(filePath);
                                        ImageIO.write(bufferedImage, "png", file);
                                        objArtwork.getObjConfiguration().setObjProcessProcessBuilder(new ProcessBuilder(objDevice.getStrDevicePath(),filePath).start());
                                    }else{
                                        objArtwork.getObjConfiguration().setObjProcessProcessBuilder(new ProcessBuilder(objDevice.getStrDevicePath()).start());
                                        importArtwork();
                                    }
                                }else{
                                    lblStatus.setText(objDictionaryAction.getWord("NOITEM"));
                                }
                            }else{
                                lblStatus.setText(objDictionaryAction.getWord("NOVALUE"));
                            }
                        } catch (SQLException ex) {
                            new Logging("SEVERE",ArtworkView.class.getName(),"SQLException:Operation edit artwork"+ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        } catch (IOException ex) {
                            new Logging("SEVERE",ArtworkView.class.getName(),"IOException:Operation edit artwork"+ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        } catch (Exception ex) {
                            new Logging("SEVERE",ArtworkView.class.getName(),"Exception:Operation edit artwork"+ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });
            }
            container.setContextMenu(contextMenu);
            //contextMenu.hide();
            container.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                @Override
                public void handle(ContextMenuEvent event) {
                    if(bufferedImage!=null)
                        event.consume();
                }
            });
        } catch (SQLException ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"artwork window close",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"artwork window close",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    /**
     * addAccelratorKey
     * <p>
     * Function use for adding shortcut key combinations. 
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        ArtworkView
     */
    private void addAccelratorKey(){
        // variable names: kc + Alphabet + ALT|CONTROL|SHIFT // ACS alphabetical
        final KeyCodeCombination kchA = new KeyCodeCombination(KeyCode.H, KeyCombination.ALT_DOWN); // Home Menu
        final KeyCodeCombination kcfA = new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN); // File Menu
        final KeyCodeCombination kceA = new KeyCodeCombination(KeyCode.E, KeyCombination.ALT_DOWN); // Edit Menu
        final KeyCodeCombination kcvA = new KeyCodeCombination(KeyCode.V, KeyCombination.ALT_DOWN); // View Menu
        final KeyCodeCombination kcsA = new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN); // Support Menu
        final KeyCodeCombination kcnC = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN); // Create Artwork
        final KeyCodeCombination kcoC = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN); // Open Artwork
        final KeyCodeCombination kclS = new KeyCodeCombination(KeyCode.L, KeyCombination.SHIFT_DOWN); // Load Recent Artwork
        final KeyCodeCombination kcsC = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN); // Save Artwork
        final KeyCodeCombination kcsCS = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN); // Save As Artwork
        final KeyCodeCombination kciC = new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN); // Import Artwork
        final KeyCodeCombination kceC = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN); // Export Artwork
        final KeyCodeCombination kciCS = new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN); // Import Refresh Artwork
        final KeyCodeCombination kcrCS = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN); // Application
        final KeyCodeCombination kcsAC = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN); // Print Setup
        final KeyCodeCombination kcpCS = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN); // Print Preview/Screen
        final KeyCodeCombination kctA = new KeyCodeCombination(KeyCode.T, KeyCombination.ALT_DOWN); // Print Texture
        final KeyCodeCombination kcfS = new KeyCodeCombination(KeyCode.F, KeyCombination.SHIFT_DOWN); // Fabric Creator
        final KeyCodeCombination kczC = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN); // Undo
        final KeyCodeCombination kcyC = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN); // Redo
        final KeyCodeCombination kcgA = new KeyCodeCombination(KeyCode.G, KeyCombination.ALT_DOWN); // Graph Correction
        final KeyCodeCombination kcmS = new KeyCodeCombination(KeyCode.M, KeyCombination.SHIFT_DOWN); // Fill Tool
        final KeyCodeCombination kcdS = new KeyCodeCombination(KeyCode.D, KeyCombination.SHIFT_DOWN); // Reduce Dimension
        final KeyCodeCombination kccS = new KeyCodeCombination(KeyCode.C, KeyCombination.SHIFT_DOWN); // Reduce Color
        final KeyCodeCombination kcminusA = new KeyCodeCombination(KeyCode.MINUS, KeyCombination.ALT_DOWN); // Color Adjustment
        final KeyCodeCombination kcvAC = new KeyCodeCombination(KeyCode.V, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN); // Vertical Mirror
        final KeyCodeCombination kchAC = new KeyCodeCombination(KeyCode.H, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN); // Horizontal Mirror
        final KeyCodeCombination kccCS = new KeyCodeCombination(KeyCode.C, KeyCombination.SHIFT_DOWN, KeyCombination.CONTROL_DOWN); // Clockwise Rotation
        final KeyCodeCombination kccAC = new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN); // Anti Clockwise Rotation
        final KeyCodeCombination kczS = new KeyCodeCombination(KeyCode.Z, KeyCombination.SHIFT_DOWN); // Repeat Orientation
        final KeyCodeCombination kcplusC = new KeyCodeCombination(KeyCode.EQUALS, KeyCombination.CONTROL_DOWN); // Zoom In
        final KeyCodeCombination kcenterA = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.ALT_DOWN); // Normal Zoom
        final KeyCodeCombination kcminusC = new KeyCodeCombination(KeyCode.MINUS, KeyCombination.CONTROL_DOWN); // Zoom Out
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
                else if(kcsA.match(t)){
                    
                }
                else if(kcnC.match(t)){
                    newArtwork();
                }
                else if(kcoC.match(t)){
                    openArtwork();
                }
                else if(kclS.match(t)){
                    recentArtwork();
                }
                else if(kcsC.match(t)){
                    saveArtwork();
                }
                else if(kcsCS.match(t)){
                    saveAsArtwork();
                }
                else if(kciC.match(t)){
                    importArtwork();
                }
                else if(kceC.match(t)){
                    exportArtwork();
                }
                else if(kciCS.match(t)){
                    refreshArtwork();
                }
                else if(kcrCS.match(t)){
                    artworkApplication();
                }
                else if(kcsAC.match(t)){
                    printSetupAction();
                }
                else if(kcpCS.match(t)){
                    printScreen();
                }
                else if(kctA.match(t)){
                    printArtwork();
                }
                else if(kcfS.match(t)){
                    openFabricEditor();
                }
                else if(kczC.match(t)){
                    undoArtwork();
                }
                else if(kcyC.match(t)){
                    redoArtwork();
                }
                else if(kcgA.match(t)){
                    correctGraph();
                }
                else if(kcmS.match(t)){
                    artworkColorWeaveFill();
                }
                else if(kcdS.match(t)){
                    resizeArtwork();
                }
                else if(kccS.match(t)){
                    artworkColorReduction();
                }
                else if(kcminusA.match(t)){
                    artworkColorProperties();
                }
                else if(kcvAC.match(t)){
                    artworkVerticalMirror();
                }
                else if(kchAC.match(t)){
                    artworkHorizentalMirror();
                }
                else if(kccCS.match(t)){
                    artworkClockRotation();
                }
                else if(kccAC.match(t)){
                    artworkAntiClockRotation();
                }
                else if(kczS.match(t)){
                    repeatArtwork();
                }
                else if(kcplusC.match(t)){
                    artworkZoomIn();
                }
                else if(kcenterA.match(t)){
                    artworkNormalZoom();
                }
                else if(kcminusC.match(t)){
                    artworkZoomOut();
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
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                System.out.println("key pressed");
                KeyCode key = t.getCode();
                if (key == KeyCode.DOWN){
                     artworkZoomOut();
                } else if (key == KeyCode.UP){
                     artworkZoomIn();
                } else if (key == KeyCode.ENTER){
                     artworkNormalZoom();
                } else if (key == KeyCode.U){
                     undoArtwork();
                } else if (key == KeyCode.Y){
                     redoArtwork();
                }
            }
        });
    
    }
    /**
     * addEventHandler
     * <p>
     * Function use for drawing menu bar for menu item,
     * and binding events for each menus with style. 
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        ArtworkView
     */
    private void addEventHandler(){
        artwork.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //System.out.println("Clicked");
                onMouseClickedListener(event);
            }
        });
        artwork.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){ 
            @Override
            public void handle(MouseEvent event) {
                //System.out.println("Presed");
                onMousePressedListener(event);
            }
        });
        artwork.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>(){ 
            @Override
            public void handle(MouseEvent event) {
                //System.out.println("Dragged");
                onMouseDraggedListener(event);
            }
        });
        artwork.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>(){ 
            @Override
            public void handle(MouseEvent event) {
                //System.out.println("Relesed");
                onMouseReleaseListener(event);
            }
        });
    }
    
    private void onMouseClickedListener(MouseEvent e){
        if(!e.isSecondaryButtonDown()){
             if(plotEditActionMode == 1){// editgraph = 1
                artworkGraphEditEvent(e);
            } else if(plotEditActionMode == 2){ // filltool = 2
                artworkFillToolEvent(e);
            }
        }
    }
    private void onMousePressedListener(MouseEvent e){
        this.startX = e.getX();
        this.startY = e.getY();
        this.oldX = e.getX();
        this.oldY = e.getY();
    }
    private void freeDrawing()
    {
        //foregroundGC.clearRect(0, 0, foreground.getWidth(), foreground.getHeight());
        BasicStroke bs = new BasicStroke(1);
        objGraphics2D.setStroke(bs);
        objGraphics2D.setColor(java.awt.Color.RED);
        objGraphics2D.drawLine((int)oldX, (int)oldY, (int)lastX, (int)lastY);
        oldX = lastX;
        oldY = lastY;
     //   initArtworkValue();
    }
    private void onMouseDraggedListener(MouseEvent e){
        this.lastX = e.getX();
        this.lastY = e.getY();
        if(plotEditActionMode==3)
            freeDrawing();
        
        /*        
        if(drawsixpointstar)
            drawSixPointStarEffect();
        if(drawfivepointstar)
            drawFivePointStarEffect();
        if(drawfourpointstar)
            drawFourPointStarEffect();
        if(drawhexagone)
            drawHexagonEffect();
        if(drawpentagon)
            drawPentagonEffect();
        if(drawheart)
            drawHeartEffect();
        if(drawdiamond)
            drawDiamondEffect();        
        if(drawtriangle)
            drawTriangleEffect();
        if(drawrectangle)
            drawRectEffect();
        if(drawoval)
            drawOvalEffect();
        if(beziercurve)
            drawBezierCurveEffect();
        if(drawarc)
            drawArcEffect();
        if(drawline)
            drawLineEffect();
        if(eraser)
            eraserDrawing();
        if(selectdata)
            drawSelectDataEffect();
        if(dragselectdata)
            drawDragSelectDataEffect();
        if(mirrorverticaldata)
            drawVerticalMirrorEffect();
        if(mirrorhorizentadata)
            freeDrawing();
        if(rotateclockwisedata)
            drawRotateClockWiseEffect();
        if(rotateanticlockwisedata)
            drawRotateAntiClockWiseEffect();
        */
    }
    private void onMouseReleaseListener(MouseEvent e){
        /*
        if(drawsixpointstar)
            drawSixPointStar();
        if(drawfivepointstar)
            drawFivePointStar();
        if(drawfourpointstar)
            drawFourPointStar();
        if(drawhexagone)
            drawHexagon();
        if(drawpentagon)
            drawPentagon();
        if(drawheart)
            drawHeart();
        if(drawdiamond)
            drawDiamond();
        if(drawtriangle)
            drawTriangle();
        if(drawrectangle)
            drawRect();
        if(drawoval)
            drawOval();
        if(beziercurve)
            drawBezierCurve();
        if(drawarc)
            drawArc();
        if(drawline)
            drawLine();
        
        if(selectdata)
            drawSelectData();
        if(mirrorverticaldata)
            freeDrawing();
        if(mirrorhorizentadata)
            freeDrawing();
        if(rotateclockwisedata)
            drawRotateClockWise();
        if(rotateanticlockwisedata)
            drawRotateAntiClockWise();
        */
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
     * @link        ArtworkView
     */
    public void menuHighlight(String strMenu){
        fileMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
        editMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
        transformMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
        toolBoxMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
        viewMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
        fileMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
        editMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
        transformMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
        toolBoxMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
        viewMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
        toolBar.getItems().clear();
        System.gc();
        if(strMenu == "VIEW"){
            viewMenu.setStyle("-fx-background-color: linear-gradient(#FFD765,#FFFC92);");
            viewMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            populateViewToolbar();
        } else if(strMenu == "TOOLBOX"){
            toolBoxMenu.setStyle("-fx-background-color: linear-gradient(#FFD765,#FFFC92);");
            toolBoxMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            populateEquipmentToolbar();
        } else if(strMenu == "TRANSFORM"){
            transformMenu.setStyle("-fx-background-color: linear-gradient(#FFD765,#FFFC92);");
            transformMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            System.gc();
            populateOperationToolbar();
        } else if(strMenu == "EDIT"){
            editMenu.setStyle("-fx-background-color: linear-gradient(#FFD765,#FFFC92);");
            editMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            populateEditToolbar();
        } else {
            fileMenu.setStyle("-fx-background-color: linear-gradient(#FFD765,#FFFC92);");
            fileMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
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
        newFileVB.setCursor(Cursor.HAND); 
        newFileVB.getStyleClass().addAll("VBox");    
        // Open file item
        VBox openFileVB = new VBox(); 
        Label openFileLbl= new Label(objDictionaryAction.getWord("OPENFILE"));
        openFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOPENFILE")));
        openFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/open.png"), openFileLbl);
        openFileVB.setPrefWidth(80);
        openFileVB.setCursor(Cursor.HAND); 
        openFileVB.getStyleClass().addAll("VBox");        
        // load recent file item
        VBox loadFileVB = new VBox(); 
        Label loadFileLbl= new Label(objDictionaryAction.getWord("LOADFILE"));
        loadFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPLOADFILE")));
        loadFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/open_recent.png"), loadFileLbl);
        loadFileVB.setPrefWidth(80);
        loadFileVB.setCursor(Cursor.HAND); 
        loadFileVB.getStyleClass().addAll("VBox");
        // Save file item
        saveFileVB = new VBox(); 
        Label saveFileLbl= new Label(objDictionaryAction.getWord("SAVEFILE"));
        saveFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVEFILE")));
        saveFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/save.png"), saveFileLbl);
        saveFileVB.setPrefWidth(80);
        saveFileVB.setCursor(Cursor.HAND); 
        saveFileVB.getStyleClass().addAll("VBox");
        // Save As file item
        saveAsFileVB = new VBox(); 
        Label saveAsFileLbl= new Label(objDictionaryAction.getWord("SAVEASFILE"));
        saveAsFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVEASFILE")));
        saveAsFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/save_as.png"), saveAsFileLbl);
        saveAsFileVB.setPrefWidth(80);
        saveAsFileVB.setCursor(Cursor.HAND); 
        saveAsFileVB.getStyleClass().addAll("VBox");
        // Import file item
        VBox importFileVB = new VBox();
        Label importFileLbl= new Label(objDictionaryAction.getWord("IMPORTDESIGN"));
        importFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPIMPORTDESIGN")));
        importFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/import.png"), importFileLbl);
        importFileVB.setPrefWidth(80);
        importFileVB.setCursor(Cursor.HAND); 
        importFileVB.getStyleClass().addAll("VBox");    
        // Refresh file item
        refreshFileVB = new VBox(); 
        Label refreshFileLbl= new Label(objDictionaryAction.getWord("IMPORTREFRESH"));
        refreshFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPIMPORTREFRESH")));
        refreshFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/update.png"), refreshFileLbl);
        refreshFileVB.setPrefWidth(80);
        refreshFileVB.setCursor(Cursor.WAIT); 
        refreshFileVB.getStyleClass().addAll("VBox");
        refreshFileVB.setDisable(true);
        // Export file item
        VBox exportFileVB = new VBox(); 
        Label exportFileLbl= new Label(objDictionaryAction.getWord("EXPORTDESIGN"));
        exportFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEXPORTDESIGN")));
        exportFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/export.png"), exportFileLbl);
        exportFileVB.setPrefWidth(80);
        exportFileVB.setCursor(Cursor.HAND); 
        exportFileVB.getStyleClass().addAll("VBox");
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
        // Device file item
        VBox applicationFileVB = new VBox();
        Label applicationFileLbl= new Label(objDictionaryAction.getWord("PAINTAPPLICATION"));
        applicationFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPAINTAPPLICATION")));
        applicationFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/application_integration.png"), applicationFileLbl);
        applicationFileVB.setPrefWidth(80);
        applicationFileVB.setCursor(Cursor.HAND); 
        applicationFileVB.getStyleClass().addAll("VBox");    
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
        // fabric editor File menu
        VBox fabricFileVB = new VBox(); 
        Label fabricFileLbl= new Label(objDictionaryAction.getWord("USEINFABRIC"));
        fabricFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPAPUSEINFABRIC")));
        fabricFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/fabric_editor.png"), fabricFileLbl);
        fabricFileVB.setPrefWidth(80);
        fabricFileVB.setCursor(Cursor.HAND); 
        fabricFileVB.getStyleClass().addAll("VBox"); 
        // quit File menu
        VBox quitFileVB = new VBox(); 
        Label quitFileLbl= new Label(objDictionaryAction.getWord("CLOSE"));
        quitFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLOSE")));
        quitFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/close.png"), quitFileLbl);
        quitFileVB.setPrefWidth(80);
        quitFileVB.setCursor(Cursor.HAND);
        quitFileVB.getStyleClass().addAll("VBox"); 
        //add enable disable condition
        if(!isWorkingMode){
            saveFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/save.png"));
            saveFileVB.setDisable(true);
            saveFileVB.setCursor(Cursor.WAIT);
            saveAsFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/save_as.png"));
            saveAsFileVB.setDisable(true);
            saveAsFileVB.setCursor(Cursor.WAIT);
            refreshFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/update.png"));
            refreshFileVB.setDisable(true);
            refreshFileVB.setCursor(Cursor.WAIT);
            exportFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/export.png"));
            exportFileVB.setDisable(true);
            exportFileVB.setCursor(Cursor.WAIT);
            saveGridFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/export_graph_as.png"));
            saveGridFileVB.setDisable(true);
            saveGridFileVB.setCursor(Cursor.WAIT);
            saveGraphFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/export_graph_as.png"));
            saveGraphFileVB.setDisable(true);
            saveGraphFileVB.setCursor(Cursor.WAIT);
            printSetupFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/print_setup.png"));
            printSetupFileVB.setDisable(true);
            printSetupFileVB.setCursor(Cursor.WAIT);
            printTextureFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/print_preview.png"));
            printTextureFileVB.setDisable(true);
            printTextureFileVB.setCursor(Cursor.WAIT);
            printGridFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/print_part.png"));
            printGridFileVB.setDisable(true);
            printGridFileVB.setCursor(Cursor.WAIT);
            printGraphFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/print_graph.png"));
            printGraphFileVB.setDisable(true);
            printGraphFileVB.setCursor(Cursor.WAIT);
            printScreenFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/print.png"));
            printScreenFileVB.setDisable(true);  
            printScreenFileVB.setCursor(Cursor.WAIT);
            deviceFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/punch_card.png"));
            deviceFileVB.setDisable(true);
            deviceFileVB.setCursor(Cursor.WAIT);
            fabricFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/fabric_editor.png"));
            fabricFileVB.setDisable(true);
            fabricFileVB.setCursor(Cursor.WAIT);
        }else{
            saveFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/save.png"));
            saveFileVB.setDisable(false);
            saveFileVB.setCursor(Cursor.HAND); 
            saveAsFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/save_as.png"));
            saveAsFileVB.setDisable(false);
            saveAsFileVB.setCursor(Cursor.HAND); 
            refreshFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/update.png"));
            refreshFileVB.setDisable(false);
            refreshFileVB.setCursor(Cursor.HAND); 
            exportFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/export.png"));
            exportFileVB.setDisable(false);
            exportFileVB.setCursor(Cursor.HAND); 
            saveGridFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/export_graph_as.png"));
            saveGridFileVB.setDisable(false);
            saveGridFileVB.setCursor(Cursor.HAND);
            saveGraphFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/export_graph_as.png"));
            saveGraphFileVB.setDisable(false);
            saveGraphFileVB.setCursor(Cursor.HAND);
            printSetupFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/print_setup.png"));
            printSetupFileVB.setDisable(false);
            printSetupFileVB.setCursor(Cursor.HAND);
            printTextureFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/print_preview.png"));
            printTextureFileVB.setDisable(false);
            printTextureFileVB.setCursor(Cursor.HAND);
            printGridFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/print_part.png"));
            printGridFileVB.setDisable(false);
            printGridFileVB.setCursor(Cursor.HAND);
            printGraphFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/print_graph.png"));
            printGraphFileVB.setDisable(false);
            printGraphFileVB.setCursor(Cursor.HAND);
            printScreenFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/print.png"));
            printScreenFileVB.setDisable(false);  
            printScreenFileVB.setCursor(Cursor.HAND);
            deviceFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/punch_card.png"));
            deviceFileVB.setDisable(false);
            deviceFileVB.setCursor(Cursor.HAND);
            fabricFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/fabric_editor.png"));
            fabricFileVB.setDisable(false);
            fabricFileVB.setCursor(Cursor.HAND); 
            if(isNew){
                //saveFileVB.setVisible(false);
                saveFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/save.png"));
                saveFileVB.setDisable(true);
                saveFileVB.setCursor(Cursor.WAIT);
            }
        } 
        //Add the action to Buttons.
        newFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                newArtwork();                
            }
        });        
        openFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                openArtwork();
            }
        });
        loadFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                recentArtwork();
            }
        });
        saveFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                saveArtwork();
            }
        });
        saveAsFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                saveAsArtwork();
            }
        });
        importFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                importArtwork();
            }
        });        
        refreshFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                refreshArtwork();
            }
        });
        exportFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exportArtwork();
            }
        });
        saveGridFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exportGrid();
            }
        });
        saveGraphFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exportGraph();
            }
        });
        applicationFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artworkApplication();
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
                printArtwork();
            }
        }); 
        printGridFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                printGrid();
            }
        });
        printGraphFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                printGraph();
            }
        }); 
        printScreenFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                printScreen();
            }
        });  
        deviceFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                punchApplication();
            }
        });
        fabricFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                openFabricEditor();
            }
        });        
        quitFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artworkStage.close();
                System.gc();
                WindowView objWindowView = new WindowView(objConfiguration);
            }
        });
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(newFileVB,openFileVB,loadFileVB,saveFileVB,saveAsFileVB,importFileVB,refreshFileVB,exportFileVB,saveGraphFileVB,applicationFileVB,printSetupFileVB,printTextureFileVB,printGraphFileVB,printScreenFileVB,deviceFileVB,fabricFileVB);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
    }    
    //////////////////////////// File Menu Action ///////////////////////////
    /**
     * newArtwork
     * <p>
     * This method is used for creating GUI for artwork.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for initializing GUI for artwork.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void newArtwork(){
        if(maxSizeCheck(objConfiguration.getIntEnds(),objConfiguration.getIntPixs())){
            lblStatus.setText(objDictionaryAction.getWord("ACTIONNEWFILE"));
            try{
                bufferedImage = new BufferedImage(objConfiguration.getIntEnds(), objConfiguration.getIntPixs(), BufferedImage.TYPE_INT_ARGB);
                objGraphics2D = bufferedImage.createGraphics();
                objGraphics2D.drawImage(bufferedImage, 0, 0, objConfiguration.getIntEnds(), objConfiguration.getIntPixs(), null);
                objGraphics2D.setColor(java.awt.Color.WHITE);
                objGraphics2D.fillRect(0, 0, objConfiguration.getIntEnds(), objConfiguration.getIntPixs());

                //objConfiguration.setStrRecentArtwork(null);
                objArtwork = new Artwork();
                objArtwork.setObjConfiguration(objConfiguration);
                objArtwork.setStrArtworkId(null);

                isNew = true;
                isWorkingMode = true;            
                menuHighlight(null);

                initArtworkValue();
            } catch (Exception ex) {
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                new Logging("SEVERE",ArtworkView.class.getName(),ArtworkView.class.getEnclosingMethod().getName()+ex.getMessage(),ex);
            }
        }else{
            recentArtwork();
        }
        System.gc();
    }
    /**
     * openArtwork
     * <p>
     * This method is used for initializing GUI for artwork.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for initializing GUI for artwork.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void openArtwork(){
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
        
            lblStatus.setText(objDictionaryAction.getWord("ACTIONOPENFILE"));
            Artwork objArtworkNew = new Artwork();
            objArtworkNew.setObjConfiguration(objConfiguration);
            ArtworkImportView objArtworkImportView= new ArtworkImportView(objArtworkNew);
            if(objArtworkNew.getStrArtworkId()!=null){
                objArtwork = objArtworkNew;
                loadArtwork();
            }
        }
        System.gc();
    }
    /**
     * recentArtwork
     * <p>
     * This method is used for initializing GUI for artwork.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for initializing GUI for artwork.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void recentArtwork(){
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONLOADFILE"));
            if(objConfiguration.getStrRecentArtwork()!=null){
                objArtwork = new Artwork();
                objArtwork.setObjConfiguration(objConfiguration);
                objArtwork.setStrArtworkId(objConfiguration.getStrRecentArtwork());
                loadArtwork();
            }
        }else{
            newArtwork();
        }
        System.gc();
    }
    /**
     * SaveArtwork
     * <p>
     * This method is used for updating artwork to library.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for updating artwork to library.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void saveArtwork(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEFILE"));
        isNew=false;
        savingArtwork();
        //updateArtwork();
        System.gc();
    }
    /**
     * setArtwork
     * <p>
     * This method is used for inserting artwork to library.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for inserting artwork to library.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void setArtwork(){
        try {
            objArtwork.setObjConfiguration(objConfiguration);
            String strArtworkID = new IDGenerator().getIDGenerator("ARTWORK_LIBRARY", objConfiguration.getObjUser().getStrUserID());
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();  
            objArtwork.setBytArtworkThumbnil(imageInByte);
            imageInByte = null;
            baos.close();
            objArtwork.setStrArtworkId(strArtworkID);
            objArtworkAction = new ArtworkAction();
            if(objArtworkAction.setArtwork(objArtwork)!=0){
                objConfiguration.setStrRecentArtwork(objArtwork.getStrArtworkId());
                lblStatus.setText(objArtwork.getStrArtworkName()+" : "+objDictionaryAction.getWord("DATASAVED"));
                isNew = false;
                menuHighlight(null);
            }else{
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        } catch (IOException ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"Operation save",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"Operation save",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
        System.gc();    
    }
    /**
     * saveAsArtwork
     * <p>
     * This method is used for inserting artwork to library.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for inserting artwork to library.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void saveAsArtwork(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEASFILE"));
        objArtwork.setStrArtworkAccess(objConfiguration.getObjUser().getUserAccess("ARTWORK_LIBRARY"));
        isNew=true;
        savingArtwork();
        //saveArtwork();
        System.gc();
    }
    /**
     * resetArtwork
     * <p>
     * This method is used for updating artwork to library.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for updating artwork to library.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void resetArtwork(){
        try {
            String strBackground = String.format("#%02X%02X%02X",colors.get(0).getRed(),colors.get(0).getGreen(),colors.get(0).getBlue());
            objArtwork.setStrArtworkBackground(strBackground);
                        
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();  
            objArtwork.setBytArtworkThumbnil(imageInByte);
            imageInByte = null;
            baos.close();
            objArtworkAction = new ArtworkAction();
            if(objArtworkAction.resetArtwork(objArtwork)!=0){
                objConfiguration.setStrRecentArtwork(objArtwork.getStrArtworkId());
                lblStatus.setText(objArtwork.getStrArtworkName()+" : "+objDictionaryAction.getWord("DATASAVED"));
            }else{
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        } catch (IOException ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"Operation save",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"Operation save",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
        System.gc();
    }
    /**
     * savingArtwork
     * <p>
     * This method is used for initializing GUI for artwork.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for initializing GUI for artwork.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    public void savingArtwork(){
        if(colors.size()<=objConfiguration.getIntColorLimit()){
            // if artwork access is Public, we need not show dialog for Save
            if(!isNew && objArtwork.getStrArtworkAccess().equalsIgnoreCase("Public")){
                isNew = true;
            }
            final Stage artworkPopupStage = new Stage();
            artworkPopupStage.initModality(Modality.APPLICATION_MODAL);
            BorderPane root = new BorderPane();
            Scene popupScene = new Scene(root, 350, 300, Color.WHITE);
            popupScene.getStylesheets().add(ArtworkView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());

            GridPane popup=new GridPane();
            popup.setId("popup");
            popup.setAlignment(Pos.CENTER);
            popup.setHgap(10);
            popup.setVgap(10);
            popup.setPadding(new Insets(25, 25, 25, 25));

            final TextField txtName = new TextField();
            final ComboBox backgroundCB = new ComboBox();
            // if save As, show TextField to Enter Name and choose Background Color
            if(isNew){
                Label lblName = new Label(objDictionaryAction.getWord("NAME"));
                lblName.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNAME")));
                popup.add(lblName, 0, 0);
                txtName.setPromptText(objDictionaryAction.getWord("TOOLTIPNAME"));
                txtName.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNAME")));
                popup.add(txtName, 1, 0, 2, 1);

                Label artworkBackground = new Label(objDictionaryAction.getWord("BACKGROUNDCOLOR")+":");
                artworkBackground.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBACKGROUNDCOLOR")));
                popup.add(artworkBackground, 0, 1);
                backgroundCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBACKGROUNDCOLOR")));
                for(int i=0; i<intColor; i++){
                    String webColor = String.format("#%02X%02X%02X",colors.get(i).getRed(),colors.get(i).getGreen(),colors.get(i).getBlue());
                    backgroundCB.getItems().add(webColor);
                    if(i==0)
                        backgroundCB.setValue(webColor);
                }
                popup.add(backgroundCB, 1, 1, 2, 1);
            }

            final ToggleGroup artworkTG = new ToggleGroup();
            RadioButton artworkPublicRB = new RadioButton(objDictionaryAction.getWord("PUBLIC"));
            artworkPublicRB.setToggleGroup(artworkTG);
            artworkPublicRB.setUserData("Public");
            popup.add(artworkPublicRB, 0, 2);
            RadioButton artworkPrivateRB = new RadioButton(objDictionaryAction.getWord("PRIVATE"));
            artworkPrivateRB.setToggleGroup(artworkTG);
            artworkPrivateRB.setUserData("Private");
            popup.add(artworkPrivateRB, 1, 2);
            RadioButton artworkProtectedRB = new RadioButton(objDictionaryAction.getWord("PROTECTED"));
            artworkProtectedRB.setToggleGroup(artworkTG);
            artworkProtectedRB.setUserData("Protected");
            popup.add(artworkProtectedRB, 2, 2);
            if(objConfiguration.getObjUser().getUserAccess("ARTWORK_LIBRARY").equalsIgnoreCase("Public"))
                artworkTG.selectToggle(artworkPublicRB);
            else if(objConfiguration.getObjUser().getUserAccess("ARTWORK_LIBRARY").equalsIgnoreCase("Protected"))
                artworkTG.selectToggle(artworkProtectedRB);
            else
                artworkTG.selectToggle(artworkPrivateRB);

            final PasswordField passPF= new PasswordField();
            final Label lblAlert = new Label();
            if(objConfiguration.getBlnAuthenticateService()){
                Label lblPassword=new Label(objDictionaryAction.getWord("SERVICEPASSWORD"));
                lblPassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                popup.add(lblPassword, 0, 3);
                passPF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                passPF.setPromptText(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
                popup.add(passPF, 1, 3, 2, 1);
                lblAlert.setStyle("-fx-wrap-text:true;");
                lblAlert.setPrefWidth(250);
                popup.add(lblAlert, 0, 5, 3, 1);
            }

            Button btnSubmit = new Button(objDictionaryAction.getWord("SUBMIT"));
            btnSubmit.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSUBMIT")));
            btnSubmit.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
            btnSubmit.setDefaultButton(true);
            btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    objArtwork.setStrArtworkAccess(artworkTG.getSelectedToggle().getUserData().toString());
                    if(objConfiguration.getBlnAuthenticateService()){
                        if(passPF.getText().length()==0){
                            lblAlert.setText(objDictionaryAction.getWord("NOSERVICEPASSWORD"));
                        } else{
                            if(Security.SecurePassword(passPF.getText(), objConfiguration.getObjUser().getStrUsername()).equals(objConfiguration.getObjUser().getStrAppPassword())){
                                if(isNew){
                                    if(txtName.getText().trim().length()==0)
                                        objArtwork.setStrArtworkName("Unknown Artwork");
                                    else
                                        objArtwork.setStrArtworkName(txtName.getText());
                                    objArtwork.setStrArtworkBackground(backgroundCB.getValue().toString());
                                }
                                objArtwork.setStrArtworkAccess(artworkTG.getSelectedToggle().getUserData().toString());
                                if(isNew)
                                    setArtwork();
                                else
                                    resetArtwork();
                                artworkPopupStage.close();
                            } else{
                                lblAlert.setText(objDictionaryAction.getWord("INVALIDSERVICEPASSWORD"));
                            }
                        }
                    } else{   // service password is disabled
                        if(isNew){
                            if(txtName.getText().trim().length()==0)
                                objArtwork.setStrArtworkName("Unknown Artwork");
                            else
                                objArtwork.setStrArtworkName(txtName.getText());
                            objArtwork.setStrArtworkBackground(backgroundCB.getValue().toString());
                        }
                        objArtwork.setStrArtworkAccess(artworkTG.getSelectedToggle().getUserData().toString());
                        if(isNew)
                            setArtwork();
                        else
                            resetArtwork();
                        artworkPopupStage.close();
                    }
                }
            });
            popup.add(btnSubmit, 1, 4);
            Button btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
            btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
            btnCancel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
            btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {  
                    artworkPopupStage.close();
                    lblStatus.setText(objDictionaryAction.getWord("TOOLTIPCANCEL"));
                }
            });
            popup.add(btnCancel, 0, 4);
            root.setCenter(popup);
            artworkPopupStage.setScene(popupScene);
            artworkPopupStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("SAVE"));
            artworkPopupStage.showAndWait();
        } else {
            lblStatus.setText(objDictionaryAction.getWord("MANYCOLOUR"));
        }
    }
    /**
     * importArtwork
     * <p>
     * This method is used for importing artwork.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for importing artwork.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void importArtwork(){     
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONIMPORTDESIGN"));
            try {
                if(bufferedImage!=null)
                    objUR.doCommand("Import", bufferedImage);
                    //objURF.store(bufferedImage);
            
                FileChooser fileChooser = new FileChooser();             
                //Set extension filter
                FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
                FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
                FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.BMP");
                FileChooser.ExtensionFilter extFilterTIF = new FileChooser.ExtensionFilter("TIFF files (*.tif)", "*.TIF");
                fileChooser.getExtensionFilters().addAll(extFilterPNG, extFilterJPG, extFilterBMP, extFilterTIF);
                //fileChooser.setInitialDirectory(new File(objFabric.getObjConfiguration().strRoot));
                fileChooser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("SELECTTO")+" "+objDictionaryAction.getWord("DESIGN"));
                //Show open file dialog
                File file = fileChooser.showOpenDialog(null);
                filePath = file.getCanonicalPath();
                bufferedImage = ImageIO.read(file);

                objArtworkAction = new ArtworkAction(false);
                lblStatus.setText(objDictionaryAction.getWord("COLORINFO"));
                bufferedImage = objArtworkAction.rectifyImage(bufferedImage,objConfiguration.getIntColorLimit());
            
                objArtwork = new Artwork();
                objArtwork.setObjConfiguration(objConfiguration);
                objArtwork.setStrArtworkId(null);
                isNew = true;
                isWorkingMode = true;            
                menuHighlight(null);
                initArtworkValue();
                lblStatus.setText(objDictionaryAction.getWord("ACTIONIMPORTDESIGN"));
            } catch (IOException ex) {
                new Logging("SEVERE",ArtworkView.class.getName(),"Operation import"+ex.getMessage(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            } catch (SQLException ex) {
                new Logging("SEVERE",ArtworkView.class.getName(),"Operation import"+ex.getMessage(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            } catch(OutOfMemoryError ex){
                recentArtwork();
            }
        } 
        System.gc();
    }
    /**
     * refreshArtwork
     * <p>
     * This method is used for refreshing or reloading design changed or imported to library.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for refreshing or reloading design changed or imported to library.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void refreshArtwork(){
        if(filePath!=null){
            lblStatus.setText(objDictionaryAction.getWord("ACTIONIMPORTREFRESH"));
            try {
                objUR.doCommand("Refresh", bufferedImage);
                //objURF.store(bufferedImage);
                File file = new File(filePath);
                bufferedImage = ImageIO.read(file);
                objArtworkAction = new ArtworkAction(false);
                bufferedImage = objArtworkAction.rectifyImage(bufferedImage,objConfiguration.getIntColorLimit());

                initArtworkValue();
                lblStatus.setText(objDictionaryAction.getWord("ACTIONIMPORTDESIGN"));
            } catch (IOException ex) {
                new Logging("SEVERE",ArtworkView.class.getName(),"Operation import",ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            } catch (SQLException ex) {
                new Logging("SEVERE",ArtworkView.class.getName(),"Operation device",ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            } catch(OutOfMemoryError ex){
                undoArtwork();
            }
        } else{
            lblStatus.setText(objDictionaryAction.getWord("NODATA"));
        }
        System.gc();
    }
    /**
     * exportArtwork
     * <p>
     * This method is used for exporting artwork.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for exporting artwork.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void exportArtwork(){
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONEXPORTDESIGN"));                
            try {
                FileChooser artworkExport=new FileChooser();
                artworkExport.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTPNG")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
                FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
                artworkExport.getExtensionFilters().add(extFilterPNG);
                File file=artworkExport.showSaveDialog(artworkStage);
                if(file==null)
                    return;
                else
                    artworkStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+file.getAbsoluteFile().getName()+"]");   
                if (!file.getName().endsWith("png")) 
                    file = new File(file.getPath() +".png");
                ImageIO.write(bufferedImage, "png", file);    

                if(objConfiguration.getBlnAuthenticateService()){
                    ArrayList<File> filesToZip=new ArrayList<>();
                    filesToZip.add(file);
                    String zipFilePath=file.getAbsolutePath()+".zip";
                    String passwordToZip = file.getName();
                    new EncryptZip(zipFilePath, filesToZip, passwordToZip);
                    file.delete();
                }
                lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
            } catch (IOException ex) {
                new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            } catch (Exception ex) {
                new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        System.gc();
    }
    /**
     * exportGrid
     * <p>
     * This method is used for exporting artwork.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for exporting artwork.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void exportGrid() { 
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEGRIDFILE"));
            try {
                BufferedImage editBufferedImage = null;
            
                FileChooser artworkExport=new FileChooser();
                artworkExport.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTPNG")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
                FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
                artworkExport.getExtensionFilters().add(extFilterPNG);
                File file=artworkExport.showSaveDialog(artworkStage);
                if(file==null)
                    return;
                else
                    artworkStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+file.getAbsoluteFile().getName()+"]");   
                if (!file.getName().endsWith("png")) 
                    file = new File(file.getPath() +".png");
                ImageIO.write(getArtworkGrid(editBufferedImage), "png", file);    

                if(objConfiguration.getBlnAuthenticateService()){
                    ArrayList<File> filesToZip=new ArrayList<>();
                    filesToZip.add(file);
                    String zipFilePath=file.getAbsolutePath()+".zip";
                    String passwordToZip = file.getName();
                    new EncryptZip(zipFilePath, filesToZip, passwordToZip);
                    file.delete();
                }
                editBufferedImage = null;
                lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
            } catch (IOException ex) {
                new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            } catch (Exception ex) {
                new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        System.gc();
    }
    /**
     * getArtworkGraph
     * <p>
     * This method is used for exporting artwork.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for exporting artwork.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private BufferedImage getArtworkGrid(BufferedImage editBufferedImage){
        lblStatus.setText(objDictionaryAction.getWord("GETGRIDVIEW"));
        int intHeight=(int)bufferedImage.getHeight();
        int intLength=(int)bufferedImage.getWidth();
        String[] data =objArtwork.getObjConfiguration().getStrGraphSize().split("x");
        editBufferedImage = new BufferedImage((int)(intLength*zoomfactor*Integer.parseInt(data[1])/graphFactor), (int)(intHeight*zoomfactor*Integer.parseInt(data[0])/graphFactor),BufferedImage.TYPE_INT_RGB);
        Graphics2D g = editBufferedImage.createGraphics();
        g.drawImage(bufferedImage, 0, 0, (int)(intLength*zoomfactor*Integer.parseInt(data[1])/graphFactor), (int)(intHeight*zoomfactor*Integer.parseInt(data[0])/graphFactor), null);
        g.setColor(java.awt.Color.BLACK);
        
        try {
            BasicStroke bs = new BasicStroke(2);
            g.setStroke(bs);
            VLines vlines = new VLines(g, intHeight, intLength, graphFactor, data, zoomfactor, (byte)2);
            /*
            //For vertical line
            for(int j = 0; j < intLength; j++) {
                bs = new BasicStroke(1*zoomfactor);
                g.setStroke(bs);
                g.drawLine((int)(j*zoomfactor*Integer.parseInt(data[1])/graphFactor), 0,  (int)(j*zoomfactor*Integer.parseInt(data[1])/graphFactor), (int)(intHeight*zoomfactor*Integer.parseInt(data[0])/graphFactor));
            }
            //For horizental line
            for(int i = 0; i < intHeight; i++) {
                bs = new BasicStroke(1*zoomfactor);
                g.setStroke(bs);
                g.drawLine(0, (int)(i*zoomfactor*Integer.parseInt(data[0])/graphFactor), (int)(intLength*zoomfactor*Integer.parseInt(data[1])/graphFactor), (int)(i*zoomfactor*Integer.parseInt(data[0])/graphFactor));
            }
           */
            while(vlines.isAlive()) {
                new Logging("INFO",ArtworkView.class.getName(),"Main thread will be alive till the child thread is live",null);
                Thread.sleep(100);
            }
            new Logging("INFO",ArtworkView.class.getName(),"Main thread run is over",null);
            //vlines = null;
        } catch(InterruptedException ex) {
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            new Logging("SEVERE",ArtworkView.class.getName(),"InterruptedException: Main thread interrupted",ex);
        } catch(Exception ex){
            new Logging("SEVERE",ArtworkView.class.getName(),"Exception: operation failed",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
        /*
        BasicStroke bs = new BasicStroke(2);
        g.setStroke(bs);

        for(int i = 0; i < intHeight; i++) {
            for(int j = 0; j < intLength; j++) {
                if((j%(Integer.parseInt(data[0])/graphFactor))==0){
                    bs = new BasicStroke(1*(float)zoomfactor);
                    g.setStroke(bs);
                }else{
                    bs = new BasicStroke(1);
                    g.setStroke(bs);
                }
                g.drawLine((int)(j*zoomfactor*Integer.parseInt(data[1])/graphFactor), 0,  (int)(j*zoomfactor*Integer.parseInt(data[1])/graphFactor), (int)(intHeight*Integer.parseInt(data[0])/graphFactor));
            }
            if((i%(Integer.parseInt(data[1])/graphFactor))==0){
                bs = new BasicStroke(1*(float)zoomfactor);
                g.setStroke(bs);
            }else{
                bs = new BasicStroke(1);
                g.setStroke(bs);
            }
            g.drawLine(0, (int)(i*zoomfactor*Integer.parseInt(data[0])/graphFactor), (int)(intLength*Integer.parseInt(data[1])/graphFactor), (int)(i*zoomfactor*Integer.parseInt(data[0])/graphFactor));
        }
        */
        g.dispose();
        lblStatus.setText(objDictionaryAction.getWord("GOTGRIDVIEW"));
        return editBufferedImage;
    }
    /**
     * exportGraph
     * <p>
     * This method is used for exporting artwork.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for exporting artwork.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void exportGraph() { 
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEGRAPHFILE"));
            try {
                BufferedImage editBufferedImage = null;
                
                FileChooser fileChoser=new FileChooser();
                fileChoser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTBMP")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
                FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP (.bmp)", "*.bmp");
                fileChoser.getExtensionFilters().add(extFilterBMP);
                File file=fileChoser.showSaveDialog(artworkStage);
                if(file==null)
                    return;
                else
                    artworkStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+file.getAbsoluteFile().getName()+"]");
                if (!file.getName().endsWith("bmp")) 
                    file = new File(file.getPath() +".bmp");
                ImageIO.write(getArtworkGraph(editBufferedImage), "BMP", file);
                
                if(objConfiguration.getBlnAuthenticateService()){
                    ArrayList<File> filesToZip=new ArrayList<>();
                    filesToZip.add(file);
                    String zipFilePath=file.getAbsolutePath()+".zip";
                    String passwordToZip = file.getName();
                    new EncryptZip(zipFilePath, filesToZip, passwordToZip);
                    file.delete();
                }
                editBufferedImage = null;
                lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
            } catch (IOException ex) {
                new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            } catch (Exception ex) {
                new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        System.gc();
    }
    /**
     * getArtworkGraph
     * <p>
     * This method is used for exporting artwork.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for exporting artwork.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private BufferedImage getArtworkGraph(BufferedImage editBufferedImage){
        try{
            lblStatus.setText(objDictionaryAction.getWord("GETGRAPHVIEW"));
            int intHeight=(int)bufferedImage.getHeight();
            int intLength=(int)bufferedImage.getWidth();

            objArtwork.getObjConfiguration().setIntBoxSize(1);
            String[] data =objArtwork.getObjConfiguration().getStrGraphSize().split("x");
            editBufferedImage = new BufferedImage((int)(intLength*zoomfactor*Integer.parseInt(data[1])/graphFactor), (int)(intHeight*zoomfactor*Integer.parseInt(data[0])/graphFactor),BufferedImage.TYPE_INT_RGB);
            Graphics2D g = editBufferedImage.createGraphics();
            g.drawImage(bufferedImage, 0, 0, (int)(intLength*zoomfactor*Integer.parseInt(data[1])/graphFactor), (int)(intHeight*zoomfactor*Integer.parseInt(data[0])/graphFactor), null);
            g.setColor(java.awt.Color.BLACK);
            
            try {
                BasicStroke bs = new BasicStroke(2);
                g.setStroke(bs);
                g.drawLine(0, (int)(intHeight*zoomfactor*Integer.parseInt(data[0])/graphFactor), (int)(intLength*zoomfactor*Integer.parseInt(data[1])/graphFactor), (int)(intHeight*zoomfactor*Integer.parseInt(data[0])/graphFactor));
                g.drawLine((int)(intLength*zoomfactor*Integer.parseInt(data[1])/graphFactor), 0,  (int)(intLength*zoomfactor*Integer.parseInt(data[1])/graphFactor), (int)(intHeight*zoomfactor*Integer.parseInt(data[0])/graphFactor));
                VLines vlines = new VLines(g, intHeight, intLength, graphFactor, data, zoomfactor, (byte)3);
                /*
                //For vertical line
                for(int j = 0; j < intLength; j++) {
                    if((j%(Integer.parseInt(data[0])))==0){
                        bs = new BasicStroke(2*zoomfactor);
                        g.setStroke(bs);
                    }else{
                        bs = new BasicStroke(1*zoomfactor);
                        g.setStroke(bs);
                    }
                    g.drawLine((int)(j*zoomfactor*Integer.parseInt(data[1])/graphFactor), 0,  (int)(j*zoomfactor*Integer.parseInt(data[1])/graphFactor), (int)(intHeight*zoomfactor*Integer.parseInt(data[0])/graphFactor));
                }
                //For horizental line
                for(int i = 0; i < intHeight; i++) {
                    if((i%(Integer.parseInt(data[1])))==0){
                        bs = new BasicStroke(2*zoomfactor);
                        g.setStroke(bs);
                    }else{
                        bs = new BasicStroke(1*zoomfactor);
                        g.setStroke(bs);
                    }
                    g.drawLine(0, (int)(i*zoomfactor*Integer.parseInt(data[0])/graphFactor), (int)(intLength*zoomfactor*Integer.parseInt(data[1])/graphFactor), (int)(i*zoomfactor*Integer.parseInt(data[0])/graphFactor));
                }    
               */
                while(vlines.isAlive()) {
                    new Logging("INFO",ArtworkView.class.getName(),"Main thread will be alive till the child thread is live",null);
                    Thread.sleep(100);
                }
                new Logging("INFO",ArtworkView.class.getName(),"Main thread run is over",null);
                //vlines = null;
             } catch(InterruptedException ex) {
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                new Logging("SEVERE",ArtworkView.class.getName(),"InterruptedException: Main thread interrupted",ex);
             } catch(Exception ex){
                new Logging("SEVERE",ArtworkView.class.getName(),"Exception: operation failed",ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
             }
            /*
            BasicStroke bs = new BasicStroke(2*(float)zoomfactor);
            g.setStroke(bs);
            for(int i = 0; i < (int)bufferedImage.getHeight(); i++) {
                for(int j = 0; j < intLength; j++) {
                    if((j%(Integer.parseInt(data[0])/graphFactor))==0){
                        bs = new BasicStroke(2*(float)zoomfactor);
                        g.setStroke(bs);
                    }else{
                        bs = new BasicStroke(1*(float)zoomfactor);
                        g.setStroke(bs);
                    }
                    g.drawLine((int)(j*zoomfactor*objArtwork.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])/graphFactor), 0,  (int)(j*zoomfactor*objArtwork.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[1])/graphFactor), (int)((int)bufferedImage.getHeight()*zoomfactor*objArtwork.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/graphFactor));
                }
                if((i%(Integer.parseInt(data[1])/graphFactor))==0){
                    bs = new BasicStroke(2*(float)zoomfactor);
                    g.setStroke(bs);
                }else{
                    bs = new BasicStroke(1*(float)zoomfactor);
                    g.setStroke(bs);
                }
                g.drawLine(0, (int)(i*zoomfactor*objArtwork.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/graphFactor), (int)(intLength*zoomfactor*objArtwork.getObjConfiguration().getIntBoxSize())*Integer.parseInt(data[1])/graphFactor, (int)(i*zoomfactor*objArtwork.getObjConfiguration().getIntBoxSize()*Integer.parseInt(data[0])/graphFactor));
            }
            */
            g.dispose();
            lblStatus.setText(objDictionaryAction.getWord("GOTGRAPHVIEW"));
            return editBufferedImage;
        } catch(OutOfMemoryError ex){
            return bufferedImage;
        }        
    }
    private BufferedImage getArtworkGraphold1(BufferedImage editBufferedImage){
        lblStatus.setText(objDictionaryAction.getWord("GETGRAPHVIEW"));
        int intHeight=(int)bufferedImage.getHeight();
        int intLength=(int)bufferedImage.getWidth();

        String[] data =objArtwork.getObjConfiguration().getStrGraphSize().split("x");
        editBufferedImage = new BufferedImage((int)(intLength*zoomfactor*Integer.parseInt(data[1])/10), (int)(intHeight*zoomfactor*Integer.parseInt(data[0])/10),BufferedImage.TYPE_INT_RGB);
        Graphics2D g = editBufferedImage.createGraphics();
        g.drawImage(bufferedImage, 0, 0, (int)(intLength*zoomfactor*Integer.parseInt(data[1])/10), (int)(intHeight*zoomfactor*Integer.parseInt(data[0])/10), null);
        g.setColor(java.awt.Color.DARK_GRAY);
        BasicStroke bs = new BasicStroke(zoomfactor*2/10);
        g.setStroke(bs);
        for(int j = 0, q = 0; j <= intLength; j++, q+=(int)(zoomfactor*Integer.parseInt(data[1])/10)) {
            if((j%Integer.parseInt(data[0]))==0){
                bs = new BasicStroke(zoomfactor*2/10);
                g.setStroke(bs);
            }else{
                bs = new BasicStroke(zoomfactor*1/10);
                g.setStroke(bs);
            }
            g.drawLine(q, 0, q, (int)(intHeight*zoomfactor*Integer.parseInt(data[0])/10));
        }
        for(int i = 0, p=0; i <= intHeight; i++, p+=(int)(zoomfactor*Integer.parseInt(data[0])/10)) {
            if((i%Integer.parseInt(data[1]))==0){
                bs = new BasicStroke(zoomfactor*2/10);
                g.setStroke(bs);
            }else{
                bs = new BasicStroke(zoomfactor*1/10);
                g.setStroke(bs);
            }
            g.drawLine(0, p, (int)(intLength*zoomfactor*Integer.parseInt(data[1])/10), p);
        }
        /*for(int i = 0; i < (int)bufferedImage.getHeight(); i++) {
            for(int j = 0; j < intLength; j++) {
                if((j%Integer.parseInt(data[0]))==0){
                    bs = new BasicStroke(2*(float)zoomfactor/10);
                    g.setStroke(bs);
                }else{
                    bs = new BasicStroke(1*(float)zoomfactor/10);
                    g.setStroke(bs);
                }
                g.drawLine((int)(j*zoomfactor*Integer.parseInt(data[1])/10), 0,  (int)(j*zoomfactor*Integer.parseInt(data[1])/10), (int)((int)bufferedImage.getHeight()*zoomfactor*Integer.parseInt(data[0])/10));
            }
            if((i%Integer.parseInt(data[1]))==0){
                bs = new BasicStroke(2*(float)zoomfactor/10);
                g.setStroke(bs);
            }else{
                bs = new BasicStroke(1*(float)zoomfactor/10);
                g.setStroke(bs);
            }
            g.drawLine(0, (int)(i*zoomfactor*Integer.parseInt(data[0])/10), (int)(intLength*zoomfactor*Integer.parseInt(data[1])/10), (int)(i*zoomfactor*Integer.parseInt(data[0])/10));
        }*/
        g.dispose();
        lblStatus.setText(objDictionaryAction.getWord("GOTGRAPHVIEW"));
        return editBufferedImage;
    }
    private BufferedImage getArtworkGraphold2(BufferedImage editBufferedImage){
        lblStatus.setText(objDictionaryAction.getWord("GETGRAPHVIEW"));
        int intHeight=(int)bufferedImage.getHeight();
        int intLength=(int)bufferedImage.getWidth();

        String[] data =objArtwork.getObjConfiguration().getStrGraphSize().split("x");
        editBufferedImage = new BufferedImage((int)(intLength*zoomfactor*Integer.parseInt(data[1])/10), (int)(intHeight*zoomfactor*Integer.parseInt(data[0])/10),BufferedImage.TYPE_INT_RGB);
        Graphics2D g = editBufferedImage.createGraphics();
        g.drawImage(bufferedImage, 0, 0, (int)(intLength*zoomfactor*Integer.parseInt(data[1])/10), (int)(intHeight*zoomfactor*Integer.parseInt(data[0])/10), null);
        g.setColor(java.awt.Color.DARK_GRAY);
        BasicStroke bs = new BasicStroke(zoomfactor*2/10);
        g.setStroke(bs);
        for(int j = 0, q = 0; j <= intLength; j++, q+=(int)(zoomfactor*Integer.parseInt(data[1])/10)) {
            if((j%Integer.parseInt(data[0]))==0){
                bs = new BasicStroke(zoomfactor*2/10);
                g.setStroke(bs);
            }else{
                bs = new BasicStroke(zoomfactor*1/10);
                g.setStroke(bs);
            }
            g.drawLine(q, 0, q, (int)(intHeight*zoomfactor*Integer.parseInt(data[0])/10));
        }
        for(int i = 0, p=0; i <= intHeight; i++, p+=(int)(zoomfactor*Integer.parseInt(data[0])/10)) {
            if((i%Integer.parseInt(data[1]))==0){
                bs = new BasicStroke(zoomfactor*2/10);
                g.setStroke(bs);
            }else{
                bs = new BasicStroke(zoomfactor*1/10);
                g.setStroke(bs);
            }
            g.drawLine(0, p, (int)(intLength*zoomfactor*Integer.parseInt(data[1])/10), p);
        }
        /*for(int i = 0; i < (int)bufferedImage.getHeight(); i++) {
            for(int j = 0; j < intLength; j++) {
                if((j%Integer.parseInt(data[0]))==0){
                    bs = new BasicStroke(2*(float)zoomfactor/10);
                    g.setStroke(bs);
                }else{
                    bs = new BasicStroke(1*(float)zoomfactor/10);
                    g.setStroke(bs);
                }
                g.drawLine((int)(j*zoomfactor*Integer.parseInt(data[1])/10), 0,  (int)(j*zoomfactor*Integer.parseInt(data[1])/10), (int)((int)bufferedImage.getHeight()*zoomfactor*Integer.parseInt(data[0])/10));
            }
            if((i%Integer.parseInt(data[1]))==0){
                bs = new BasicStroke(2*(float)zoomfactor/10);
                g.setStroke(bs);
            }else{
                bs = new BasicStroke(1*(float)zoomfactor/10);
                g.setStroke(bs);
            }
            g.drawLine(0, (int)(i*zoomfactor*Integer.parseInt(data[0])/10), (int)(intLength*zoomfactor*Integer.parseInt(data[1])/10), (int)(i*zoomfactor*Integer.parseInt(data[0])/10));
        }*/
        g.dispose();
        lblStatus.setText(objDictionaryAction.getWord("GOTGRAPHVIEW"));
        return editBufferedImage;
    }
    /**
     * artworkApplication
     * <p>
     * <p>
     * This method is used for creating GUI and running third party application.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating GUI and running third party application.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void artworkApplication(){     
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONPAINTAPPLICATION"));
            try {
                UtilityAction objUtilityAction = new UtilityAction();
                Device objDevice = new Device(null, "Designing S/W", null, null);
                objDevice.setObjConfiguration(objConfiguration);
                Device[] devices = objUtilityAction.getDevices(objDevice);
                if(devices.length>0){            
                    final Stage artworkPopupStage = new Stage();
                    artworkPopupStage.initStyle(StageStyle.UTILITY);
                    //dialogStage.initModality(Modality.WINDOW_MODAL);
                    ComboBox deviceCB = new ComboBox();
                    deviceCB.getItems().add("Select");
                    for(int i=0; i<devices.length; i++)
                        deviceCB.getItems().add(devices[i].getStrDeviceName());
                    deviceCB.setValue("Select");
                    deviceCB.valueProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                            try {
                                UtilityAction objUtilityAction = new UtilityAction();
                                Device objDevice = new Device(null, null, t1, null);
                                objDevice.setObjConfiguration(objConfiguration);
                                objUtilityAction.getDevice(objDevice);
                                Runtime rt = Runtime.getRuntime();
                                if(objArtwork.getObjConfiguration().getObjProcessProcessBuilder()!=null)
                                    objArtwork.getObjConfiguration().getObjProcessProcessBuilder().destroy();
                                objArtwork.getObjConfiguration().setObjProcessProcessBuilder(new ProcessBuilder(objDevice.getStrDevicePath()).start());
                                artworkPopupStage.close();
                                importArtwork();
                            } catch (SQLException ex) {
                                new Logging("SEVERE",ArtworkView.class.getName(),"Operation import",ex);
                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                            } catch (Exception ex) {
                                new Logging("SEVERE",ArtworkView.class.getName(),"Operation import",ex);
                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                            }
                        }
                    });
                    artworkPopupStage.setScene(new Scene(deviceCB,200,50));
                    artworkPopupStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("SELECTTO")+" "+objDictionaryAction.getWord("RUN"));
                    artworkPopupStage.showAndWait();
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONIMPORTDESIGN"));
                }else{
                    lblStatus.setText(objDictionaryAction.getWord("NODEVICE"));
                }
            } catch (Exception ex) {
                new Logging("SEVERE",ArtworkView.class.getName(),"Operation import",ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }   
        System.gc();
    }
    /**
     * initArtworkValue
     * <p>
     * This method is used for initializing artwork image to panel.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for initializing artwork image to panel.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void initArtworkValue(){
        try {
            //Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            //artwork.setImage(image);
            //container.setContent(artwork);
            objArtworkAction = new ArtworkAction();
            colors = objArtworkAction.getImageColor(bufferedImage);
            intColor = (byte)colors.size();
            //contextMenu.show(contextMenu);
            if(isNew){                
                refreshFileVB.setDisable(false);
                refreshFileVB.setCursor(Cursor.HAND);
                saveFileVB.setCursor(Cursor.WAIT);
                saveFileVB.setDisable(true);
                saveFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/save.png"));
            }
            plotViewActionMode = 1;
            plotViewAction();
        } catch (SQLException ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    /**
     * loadArtwork
     * <p>
     * This method is used for initializing GUI for artwork.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for initializing GUI for artwork.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void loadArtwork(){
        try{
            objArtworkAction = new ArtworkAction();
            objArtworkAction.getArtwork(objArtwork);
            objArtworkAction = new ArtworkAction();
            if(objArtworkAction.countArtworkUsage(objArtwork.getStrArtworkId())>0 || new IDGenerator().getUserAcessValueData("ARTWORK_LIBRARY", objArtwork.getStrArtworkAccess()).equalsIgnoreCase("Public")){
                isNew = true;
                isWorkingMode = true;                        
            }else{
                isNew = false;
                isWorkingMode = true;                        
            }
            menuHighlight(null);
            byte[] bytArtworkThumbnil=objArtwork.getBytArtworkThumbnil();
            SeekableStream stream = new ByteArraySeekableStream(bytArtworkThumbnil);
            String[] names = ImageCodec.getDecoderNames(stream);
            ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
            RenderedImage im = dec.decodeAsRenderedImage();
            bufferedImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
            bytArtworkThumbnil=null;
            System.gc();
            objConfiguration.setStrRecentArtwork(objArtwork.getStrArtworkId());
            initArtworkValue();
        } catch (Exception ex) {
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
        }
    }
    /**
     * printSetupAction
     * <p>
     * Function use for print menu item. 
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        ArtworkView
     */
    public void printSetupAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONPRINTSETUPFILE"));
        objPrinterJob.pageDialog(objPrinterAttribute);
        System.gc();
    }
    /**
     * printArtwork
     * <p>
     * <p>
     * This method is used for printing artwork
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating GUI and running third party application.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void printArtwork(){
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONPRINTTEXTUREFILE"));
            try {            
                if(objPrinterJob.printDialog()){
                    objPrinterJob.setPrintable(new Printable() {
                        @Override
                        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                            if (pageIndex != 0) {
                                return NO_SUCH_PAGE;
                            }
                            int intLength = (int)(((objConfiguration.getIntDPI()*bufferedImage.getWidth())/objConfiguration.getIntEPI())*zoomfactor);
                            int intHeight = (int)(((objConfiguration.getIntDPI()*bufferedImage.getHeight())/objConfiguration.getIntPPI())*zoomfactor);
                            Graphics2D g2d=(Graphics2D)graphics;
                            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                            graphics.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
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
    }
    /**
     * printGrid
     * <p>
     * This method is used for creating GUI and printing artwork.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating GUI and printing artwork.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void printGrid() {   
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONPRINTGRIDFILE"));
            try {            
                if(objPrinterJob.printDialog()){
                    objPrinterJob.setPrintable(new Printable() {
                        @Override
                        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                            if (pageIndex != 0) {
                                return NO_SUCH_PAGE;
                            }
                            BufferedImage editBufferedImage = null;
                            Graphics2D g2d=(Graphics2D)graphics;
                            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                            graphics.drawImage(getArtworkGrid(editBufferedImage), 0, 0, null);
                            editBufferedImage = null;
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
    }
    /**
     * printGraph
     * <p>
     * This method is used for creating GUI of print preview.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating GUI  of print preview.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void printGraph(){
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONPRINTGRAPHFILE"));                    
            try {
                if(objPrinterJob.printDialog()){
                    objPrinterJob.setPrintable(new Printable() {
                        @Override
                        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                            if (pageIndex != 0) {
                                return NO_SUCH_PAGE;
                            }
                            BufferedImage editBufferedImage = null;
                            Graphics2D g2d=(Graphics2D)graphics;
                            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                            graphics.drawImage(getArtworkGraph(editBufferedImage), 0, 0, (int)pageFormat.getImageableWidth(), (int)pageFormat.getImageableHeight(), null);
                            editBufferedImage = null;
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
    }
    /**
     * printScreen
     * <p>
     * This method is used for creating GUI of print preview.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating GUI  of print preview.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    public void printScreen() { 
        lblStatus.setText(objDictionaryAction.getWord("ACTIONPRINTSCREENFILE"));
        final Stage artworkPopupStage = new Stage();
        artworkPopupStage.initStyle(StageStyle.UTILITY);
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
                                    BufferedImage texture = SwingFXUtils.fromFXImage(artwork.getImage(), null);
                                    //graphics.drawImage(texture, 0, 0, texture.getWidth(), texture.getHeight(), null);
                                    Graphics2D g2d=(Graphics2D)graphics;
                                    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                                    graphics.drawImage(texture, 0, 0, (int)pageFormat.getImageableWidth(), (int)pageFormat.getImageableWidth(), null);
                                    return PAGE_EXISTS;                    
                                }
                            });    
                            objPrinterJob.print();
                        }
                        artworkPopupStage.close();
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
                artworkPopupStage.close();
           }
        });
        popup.add(btnCancel, 1, 0);
        
        BufferedImage texture = SwingFXUtils.fromFXImage(artwork.getImage(), null);
        ImageView printImage = new ImageView();
        printImage.setImage(SwingFXUtils.toFXImage(texture, null));        
        popup.add(printImage, 0, 1, 2, 1);

        Scene popupScene = new Scene(popup);
        popupScene.getStylesheets().add(ArtworkView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        artworkPopupStage.setScene(popupScene);
        artworkPopupStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("PRINT")+" "+objDictionaryAction.getWord("PREVIEW"));
        artworkPopupStage.showAndWait();   
    }
    /**
     * punchApplication
     * <p>
     * This method is used for creating GUI of punch application.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating GUI  of punch application.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void punchApplication(){     
        try {
            lblStatus.setText(objDictionaryAction.getWord("ACTIONPUNCHAPPLICATION"));
            final Stage artworkPopupStage = new Stage();
            artworkPopupStage.initStyle(StageStyle.UTILITY);
            artworkPopupStage.initModality(Modality.APPLICATION_MODAL);
            artworkPopupStage.setResizable(false);
            artworkPopupStage.setIconified(false);
            artworkPopupStage.setFullScreen(false);
            artworkPopupStage.setTitle(objDictionaryAction.getWord("ALERT"));
            BorderPane root = new BorderPane();
            Scene popupScene = new Scene(root, 300, 220, Color.WHITE);
            popupScene.getStylesheets().add(FabricView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
            final GridPane popup=new GridPane();
            popup.setId("popup");
            popup.setHgap(5);
            popup.setVgap(5);
            popup.setPadding(new Insets(25, 25, 25, 25));
            
            popup.add(new Label(objDictionaryAction.getWord("SELECT")+" "+objDictionaryAction.getWord("PUNCHTYPE")), 0, 0);
            final ComboBox graphCB = new ComboBox();
            graphCB.getItems().addAll("Single page-Single graph","Multi page- Single graph","Multi page- Multi graph","Superimposed Single graph","Superimposed Single base graph");
            graphCB.setValue("Single page-Single graph");
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
                            } else if(graphCB.getValue().toString().equalsIgnoreCase("Superimposed Single base graph")){
                                getSuperBaseGraphMC();
                            } else { //if(graphCB.getValue().toString().equalsIgnoreCase("Single page-Single graph"))
                                getSinglePageSingleGraphMC();
                            }
                            UtilityAction objUtilityAction = new UtilityAction();
                            Device objDevice = new Device(null, null, deviceCB.getValue().toString(), null);
                            objDevice.setObjConfiguration(objConfiguration);
                            objUtilityAction.getDevice(objDevice);
                            Runtime rt = Runtime.getRuntime();
                            if(objArtwork.getObjConfiguration().getObjProcessProcessBuilder()!=null)
                                objArtwork.getObjConfiguration().getObjProcessProcessBuilder().destroy();
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
                        artworkPopupStage.close();
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
                    artworkPopupStage.close();
                }
            });
            popup.add(btnNo, 1, 3);
            
            root.setCenter(popup);
            artworkPopupStage.setScene(popupScene);
            artworkPopupStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("SELECTTO")+" "+objDictionaryAction.getWord("RUN")+" "+objDictionaryAction.getWord("PUNCHAPPLICATION"));
            artworkPopupStage.showAndWait();
            lblStatus.setText(objDictionaryAction.getWord("ACTIONPUNCHAPPLICATION"));
        } catch (SQLException ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
        System.gc();
    }    
    public void getSinglePageSingleGraphMC(){
        try {
            FileChooser fileChoser=new FileChooser();
            fileChoser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTBMP")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
            FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP (.bmp)", "*.bmp");
            fileChoser.getExtensionFilters().add(extFilterBMP);
            File file=fileChoser.showSaveDialog(artworkStage);
            if(file==null)
                return;
            else
                artworkStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+file.getAbsoluteFile().getName()+"]");
            if (!file.getName().endsWith("bmp"))
                file = new File(file.getPath() +".bmp");
            ImageIO.write(bufferedImage, "BMP", file);
            
            ArrayList<File> filesToZip=new ArrayList<>();
            filesToZip.add(file);
            String zipFilePath=file.getAbsolutePath()+".zip";
            String passwordToZip = file.getName();
            new EncryptZip(zipFilePath, filesToZip, passwordToZip);
            file.delete();
                
            lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
        } catch (IOException ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }    
    public void getMultiPageSingleGraphMC(){
        try {
            int intHeight=(int)bufferedImage.getHeight();
            int intLength=(int)bufferedImage.getWidth();
            
            BufferedImage graphImage = new BufferedImage(intLength*colors.size(), intHeight,BufferedImage.TYPE_INT_RGB);
            for(int k=0; k<colors.size(); k++){
                for(int i = 0; i < intHeight; i++) {
                    for(int j = 0; j < intLength; j++) {
                        if(bufferedImage.getRGB(j, i)==colors.get(k).getRGB())
                            graphImage.setRGB(j+(k*intLength), i, colors.get(k).getRGB());
                        else
                            graphImage.setRGB(j+(k*intLength), i, 16777215);
                    }
                }
            }
            
            FileChooser fileChoser=new FileChooser();
            fileChoser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTBMP")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
            FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP (.bmp)", "*.bmp");
            fileChoser.getExtensionFilters().add(extFilterBMP);
            File file=fileChoser.showSaveDialog(artworkStage);
            if(file==null)
                return;
            else
                artworkStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+file.getAbsoluteFile().getName()+"]");
            if (!file.getName().endsWith("bmp"))
                file = new File(file.getPath() +".bmp");
            ImageIO.write(graphImage, "BMP", file);
            graphImage = null;
            
            ArrayList<File> filesToZip=new ArrayList<>();
            filesToZip.add(file);
            String zipFilePath=file.getAbsolutePath()+".zip";
            String passwordToZip = file.getName();
            new EncryptZip(zipFilePath, filesToZip, passwordToZip);
            file.delete();

            lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
        } catch (IOException ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }    
    public void getMultiPageMultiGraphMC(){
        try {
            DirectoryChooser directoryChooser=new DirectoryChooser();
            artworkStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTBMP")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
            File selectedDirectory = directoryChooser.showDialog(artworkStage);
            if(selectedDirectory == null)
                return;
            else
                artworkStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+selectedDirectory.getAbsolutePath()+"]");
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            String path = selectedDirectory.getPath()+"\\Artwork_"+currentDate+"\\";
            File file = new File(path);
            ArrayList<File> filesToZip=new ArrayList<>();
            if (!file.exists()) {
                if (!file.mkdir())
                    path = selectedDirectory.getPath();
            }
            
            int intHeight=(int)bufferedImage.getHeight();
            int intLength=(int)bufferedImage.getWidth();
            
            BufferedImage graphImage;
            int rgb = 0;
            for(int k=0; k<colors.size(); k++){
                graphImage = new BufferedImage(intLength, intHeight,BufferedImage.TYPE_INT_RGB);
                for(int i = 0; i < intHeight; i++) {
                    for(int j = 0; j < intLength; j++) {
                        if(bufferedImage.getRGB(j, i)==colors.get(k).getRGB())
                            graphImage.setRGB(j, i, colors.get(k).getRGB());
                        else
                            graphImage.setRGB(j, i, 16777215);
                    }
                }
                file = new File(path + k +".bmp");
                ImageIO.write(graphImage, "BMP", file);
                filesToZip.add(file);
            }
            String zipFilePath=selectedDirectory.getPath()+"\\Artwork_"+currentDate+".zip";
            String passwordToZip = "Artwork_"+currentDate;
            new EncryptZip(zipFilePath, filesToZip, passwordToZip);
            
            //delete folder recursively
            recursiveDelete(new File(path));
            graphImage = null;
            lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+path);
        } catch (IOException ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }    
    public void getSuperGraphMC(){
        try {
            int intHeight=(int)bufferedImage.getHeight();
            int intLength=(int)bufferedImage.getWidth();
            
            List lstLines = new ArrayList();
            ArrayList<Integer> lstEntry = null;
            int lineCount = 0;
            for (int i = 0; i < intHeight; i++){
                lstEntry = new ArrayList();
                for (int j = 0; j < intLength; j++){
                    //add the first color on array
                    if(lstEntry.size()==0)
                        lstEntry.add(bufferedImage.getRGB(j, i));
                    //check for redudancy
                    else {                
                        if(!lstEntry.contains(bufferedImage.getRGB(j, i)))
                            lstEntry.add(bufferedImage.getRGB(j, i));
                    }
                }
                lstLines.add(lstEntry);
                lineCount+=lstEntry.size();
            }
            BufferedImage graphImage = new BufferedImage(intLength, lineCount,BufferedImage.TYPE_INT_RGB);
            
            lineCount=0;
            int rgb = 0;
            for (int i = 0; i < intHeight; i++){
                lstEntry = (ArrayList)lstLines.get(i);
                for (int k = 0; k < lstEntry.size(); k++, lineCount++){
                    rgb = (int)lstEntry.get(k);
                    for (int j = 0; j < intLength; j++){
                        if(rgb==bufferedImage.getRGB(j, i))
                            graphImage.setRGB(j, lineCount, rgb);
                        else
                            graphImage.setRGB(j, lineCount, 16777215);
                    }
                }
            }
            FileChooser fileChoser=new FileChooser();
            fileChoser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTBMP")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
            FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP (.bmp)", "*.bmp");
            fileChoser.getExtensionFilters().add(extFilterBMP);
            File file=fileChoser.showSaveDialog(artworkStage);
            if(file==null)
                return;
            else
                artworkStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+file.getAbsoluteFile().getName()+"]");
            if (!file.getName().endsWith("bmp"))
                file = new File(file.getPath() +".bmp");
            ImageIO.write(graphImage, "BMP", file);
            graphImage = null;
            
            ArrayList<File> filesToZip=new ArrayList<>();
            filesToZip.add(file);
            String zipFilePath=file.getAbsolutePath()+".zip";
            String passwordToZip = file.getName();
            new EncryptZip(zipFilePath, filesToZip, passwordToZip);
            file.delete();

            lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
        } catch (IOException ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
            
    public void getSuperBaseGraphMC(){
        try {
            int intHeight=(int)bufferedImage.getHeight();
            int intLength=(int)bufferedImage.getWidth();
            
            List lstLines = new ArrayList();
            ArrayList<Integer> lstEntry = null;
            int lineCount = 0;
            
            for (int i = 0; i < intHeight; i++){
                lstEntry = new ArrayList();
                for (int j = 0; j < intLength; j++){
                    // for ashish
                    if(lstEntry.size()==0){
                        lstEntry.add(16777215);          
                    }
                    //check for redudancy
                    else {                
                        if(!lstEntry.contains(bufferedImage.getRGB(j, i))){
                            lstEntry.add(bufferedImage.getRGB(j, i));
                        }
                    }
                    // for ashish
                    /*
                    //add the first color on array
                    if(lstEntry.size()==0)
                        lstEntry.add(bufferedImage.getRGB(j, i));
                    //check for redudancy
                    else {                
                        if(!lstEntry.contains(bufferedImage.getRGB(j, i)))
                            lstEntry.add(bufferedImage.getRGB(j, i));
                    }
                    */
                }
                Collections.sort(lstEntry);
                lstLines.add(lstEntry);
                lineCount+=lstEntry.size();
            }
            BufferedImage graphImage = new BufferedImage(intLength, lineCount,BufferedImage.TYPE_INT_RGB);
            
            lineCount=0;
            int rgb = 0;
            for (int i = 0; i < intHeight; i++){
                lstEntry = (ArrayList)lstLines.get(i);
                for (int k = 0; k < lstEntry.size(); k++, lineCount++){
                    rgb = (int)lstEntry.get(k);
                    for (int j = 0; j < intLength; j++){
                        if(rgb==16777215){
                            System.out.print(" "+((j+i)%2));
                            if((j+i)%2==0)
                                graphImage.setRGB(j, lineCount, 16777215);
                            else
                                graphImage.setRGB(j, lineCount, 0);
                        } else{
                            if(rgb==bufferedImage.getRGB(j, i))
                                graphImage.setRGB(j, lineCount, rgb);
                            else
                                graphImage.setRGB(j, lineCount, 16777215);
                        }
                    }
                }
                System.out.println("");
            }
            /*System.out.println("Row"+lineCount);
            for (int i = 0 ; i < lineCount; i++){
                for (int j = 0; j < intLength; j++){
                    System.err.print(" "+fontMatrix[i][j]);
                }
                System.err.println();
            }*/
            
            FileChooser fileChoser=new FileChooser();
            fileChoser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTBMP")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
            FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP (.bmp)", "*.bmp");
            fileChoser.getExtensionFilters().add(extFilterBMP);
            File file=fileChoser.showSaveDialog(artworkStage);
            if(file==null)
                return;
            else
                artworkStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+file.getAbsoluteFile().getName()+"]");
            if (!file.getName().endsWith("bmp"))
                file = new File(file.getPath() +".bmp");
            ImageIO.write(graphImage, "BMP", file);
            graphImage = null;
            
            ArrayList<File> filesToZip=new ArrayList<>();
            filesToZip.add(file);
            String zipFilePath=file.getAbsolutePath()+".zip";
            String passwordToZip = file.getName();
            new EncryptZip(zipFilePath, filesToZip, passwordToZip);
            file.delete();

            lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
        } catch (IOException ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
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
        //System.out.println("Deleted file/folder: "+file.getAbsolutePath());
    }

    /**
     * openFabricEditor
     * <p>
     * This method is used for creating GUI of opening artwork in fabric editor.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating GUI of opening artwork in fabric editor.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void openFabricEditor(){
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONUSEINFABRIC"));
    
            final Stage artworkPopupStage = new Stage();
            artworkPopupStage.initStyle(StageStyle.UTILITY);
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
            clothTypeCB.setValue(objConfiguration.getStrClothType());
            clothTypeCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLOTHTYPE")));
            clothTypeCB.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov, final String st, final String st1) {
                   try {
                        objConfiguration.setStrClothType(st1);
                        UserAction objUserAction = new UserAction();
                        objUserAction.getConfiguration(objConfiguration);
                        objConfiguration.clothRepeat();
                        System.gc();
                    } catch (SQLException ex) {
                        new Logging("SEVERE",ArtworkView.class.getName(),"Cloth type change",ex);
                        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    } catch (Exception ex) {
                        new Logging("SEVERE",ArtworkView.class.getName(),"Cloth type change",ex);
                        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    }
                }
            });    
            popup.add(clothTypeCB, 1, 0);
        
            Button btnApply = new Button(objDictionaryAction.getWord("APPLY"));
            btnApply.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
            btnApply.setTooltip(new Tooltip(objDictionaryAction.getWord("ACTIONAPPLY")));
            btnApply.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        objConfiguration.strWindowFlowContext = "ArtworkEditor";
                        if(colors.size()<=objConfiguration.getIntColorLimit()){
                            if(isNew){
                                objArtwork.setStrArtworkAccess(objConfiguration.getObjUser().getUserAccess("ARTWORK_LIBRARY"));
                                saveAsArtwork();
                            }
                            else
                                saveArtwork();
                            if(objArtwork.getStrArtworkId()!=null && !isNew){
                                objConfiguration.setStrRecentArtwork(objArtwork.getStrArtworkId());
                                artworkStage.close();
                                System.gc();
                                FabricView objFabricView = new FabricView(objConfiguration);
                            }
                        }else{
                            lblStatus.setText(objDictionaryAction.getWord("MANYCOLOUR"));
                        }
                    } catch (Exception ex) {
                        new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
                        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    }  
                    System.gc();
                    artworkPopupStage.close();
                }
            });
            popup.add(btnApply, 0, 1);

            Button btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
            btnCancel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
            btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
            btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {  
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONCANCEL"));
                    artworkPopupStage.close();
                }
            });
            popup.add(btnCancel, 1, 1);

            Scene popupScene = new Scene(popup, 300, 200, Color.WHITE);
            popupScene.getStylesheets().add(ArtworkView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
            artworkPopupStage.setScene(popupScene);
            artworkPopupStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("FABRICSETTINGS"));
            artworkPopupStage.showAndWait();        
        }
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
     * @link        ArtworkView
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
        // Tilt Left
        VBox selectEditVB = new VBox(); 
        Label selectEditLbl= new Label(objDictionaryAction.getWord("SELECT"));
        selectEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSELECT")));
        selectEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/select.png"), selectEditLbl);
        selectEditVB.setPrefWidth(80);
        selectEditVB.getStyleClass().addAll("VBox");    
        // Tilt Up
        VBox noSelectEditVB = new VBox(); 
        Label noSelectEditLbl= new Label(objDictionaryAction.getWord("NOSELECT"));
        noSelectEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNOSELECT")));
        noSelectEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/crop.png"), noSelectEditLbl);
        noSelectEditVB.setPrefWidth(80);
        noSelectEditVB.getStyleClass().addAll("VBox");    
        // Tilt Down
        VBox copyEditVB = new VBox(); 
        Label copyEditLbl= new Label(objDictionaryAction.getWord("COPY"));
        copyEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOPY")));
        copyEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/copy.png"), copyEditLbl);
        copyEditVB.setPrefWidth(80);
        copyEditVB.getStyleClass().addAll("VBox");
        // Tilt Down
        VBox pasteEditVB = new VBox(); 
        Label pasteEditLbl= new Label(objDictionaryAction.getWord("PASTE"));
        pasteEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPASTE")));
        pasteEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/paste.png"), pasteEditLbl);
        pasteEditVB.setPrefWidth(80);
        pasteEditVB.getStyleClass().addAll("VBox");
        // Tilt Down
        VBox cutEditVB = new VBox(); 
        Label cutEditLbl= new Label(objDictionaryAction.getWord("CUT"));
        cutEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCUT")));
        cutEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cut.png"), cutEditLbl);
        cutEditVB.setPrefWidth(80);
        cutEditVB.getStyleClass().addAll("VBox");
        //add enable disbale condition
        if(!isWorkingMode){
            undoEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/undo.png"));
            undoEditVB.setDisable(true);
            undoEditVB.setCursor(Cursor.WAIT);
            redoEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/redo.png"));
            redoEditVB.setDisable(true);
            redoEditVB.setCursor(Cursor.WAIT);
            selectEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/select.png"));
            selectEditVB.setDisable(true);
            selectEditVB.setCursor(Cursor.WAIT);
            noSelectEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/crop.png"));
            noSelectEditVB.setDisable(true);
            noSelectEditVB.setCursor(Cursor.WAIT);
            copyEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/copy.png"));
            copyEditVB.setDisable(true);
            copyEditVB.setCursor(Cursor.WAIT);
            pasteEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/paste.png"));
            pasteEditVB.setDisable(true);
            pasteEditVB.setCursor(Cursor.WAIT);
            cutEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/cut.png"));
            cutEditVB.setDisable(true);
            cutEditVB.setCursor(Cursor.WAIT);
        }else{
            undoEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/undo.png"));
            undoEditVB.setDisable(false);
            undoEditVB.setCursor(Cursor.HAND); 
            redoEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/redo.png"));
            redoEditVB.setDisable(false);
            redoEditVB.setCursor(Cursor.HAND);
            selectEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/select.png"));
            selectEditVB.setDisable(false);
            selectEditVB.setCursor(Cursor.HAND);
            noSelectEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/crop.png"));
            noSelectEditVB.setDisable(false);
            noSelectEditVB.setCursor(Cursor.HAND);
            copyEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/copy.png"));
            copyEditVB.setDisable(false);
            copyEditVB.setCursor(Cursor.HAND);
            pasteEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/paste.png"));
            pasteEditVB.setDisable(false);
            pasteEditVB.setCursor(Cursor.HAND);
            cutEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/cut.png"));
            cutEditVB.setDisable(false);
            cutEditVB.setCursor(Cursor.HAND);
        }
        //Add the action to Buttons.
        undoEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                undoArtwork();
            }
        });
        redoEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                redoArtwork();
            }
        });
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(undoEditVB,redoEditVB);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
    }
//////////////////////////// Edit Menu Action ///////////////////////////
    /**
     * undoArtwork
     * <p>
     * This method is used for creating GUI of artwork reduce color pane.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating GUI of artwork reduce color pane.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void undoArtwork(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONUNDO"));
        try {
            if(objUR.canUndo()){
            //if(objURF.canUndo()==1){
                objUR.undo();
                bufferedImage = (BufferedImage)objUR.getObjData();
            //    bufferedImage = objURF.undo();
                initArtworkValue();
                lblStatus.setText(objDictionaryAction.getWord("UNDO")+": "+objDictionaryAction.getWord("SUCCESS"));
            }else{
                lblStatus.setText(objDictionaryAction.getWord("MAXUNDO"));
            }
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"Operation Undo",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }   
    }
    /**
     * redoArtwork
     * <p>
     * This method is used for creating GUI of artwork reduce color pane.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void redoArtwork(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONREDO"));
        try {
            if(objUR.canRedo()){
            //if(objURF.canRedo()==1){
                objUR.redo();
                bufferedImage = (BufferedImage)objUR.getObjData();
                //bufferedImage = objURF.redo();
                initArtworkValue();
        lblStatus.setText(objDictionaryAction.getWord("REDO")+": "+objDictionaryAction.getWord("SUCCESS"));
            }else{
                lblStatus.setText(objDictionaryAction.getWord("MAXREDO"));
            }
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"Operation Redo",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }

    
/**
 * populateOperationToolbar
 * <p>
 * Function use for editing tool bar for menu item Edit,
 * and binding events for each tools. 
 * 
 * @exception   (@throws SQLException)
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         javafx.event.*;
 * @link        WeaveView
 */
  private void populateOperationToolbar(){
        // artwork resize edit item
        VBox resizeEditVB = new VBox(); 
        Label resizeEditLbl= new Label(objDictionaryAction.getWord("RESIZESCALE"));
        resizeEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPRESIZESCALE")));
        resizeEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/select.png"), resizeEditLbl);
        resizeEditVB.setPrefWidth(80);
        resizeEditVB.getStyleClass().addAll("VBox");
        // repaet
        VBox repeatEditVB = new VBox(); 
        Label repeatEditLbl= new Label(objDictionaryAction.getWord("REPEATORIENTATION"));
        repeatEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPREPEATORIENTATION")));
        repeatEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/tiled_view.png"), repeatEditLbl);
        repeatEditVB.setPrefWidth(80);
        repeatEditVB.getStyleClass().addAll("VBox");
        // graph edit item
        VBox graphPatternEditVB = new VBox(); 
        Label graphPatternEditLbl= new Label(objDictionaryAction.getWord("GRAPHPATTERNEDIT"));
        graphPatternEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPGRAPHPATTERNEDIT")));
        graphPatternEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/graph_editor.png"), graphPatternEditLbl);
        graphPatternEditVB.setPrefWidth(80);
        graphPatternEditVB.getStyleClass().addAll("VBox"); 
        // Pattern edit item
        VBox fillColorWeaveEditVB = new VBox(); 
        Label fillColorWeaveEditLbl= new Label(objDictionaryAction.getWord("FILLTOOL"));
        fillColorWeaveEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFILLTOOL")));
        fillColorWeaveEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/fill_tool.png"), fillColorWeaveEditLbl);
        fillColorWeaveEditVB.setPrefWidth(80);
        fillColorWeaveEditVB.getStyleClass().addAll("VBox");   
        // color reduce edit item
        VBox colorPropertiesEditVB = new VBox(); 
        Label colorPropertiesEditLbl= new Label(objDictionaryAction.getWord("COLORADJUST"));
        colorPropertiesEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOLORADJUST")));
        colorPropertiesEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/color_palette.png"), colorPropertiesEditLbl);
        colorPropertiesEditVB.setPrefWidth(80);
        colorPropertiesEditVB.getStyleClass().addAll("VBox");    
        // color reduce edit item
        VBox reduceColorEditVB = new VBox(); 
        Label reduceColorEditLbl= new Label(objDictionaryAction.getWord("REDUCECOLOR"));
        reduceColorEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPREDUCECOLOR")));
        reduceColorEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/color_editor.png"), reduceColorEditLbl);
        reduceColorEditVB.setPrefWidth(80);
        reduceColorEditVB.getStyleClass().addAll("VBox");    
        // artwork resize edit item
        VBox sketchEditVB = new VBox(); 
        Label sketchEditLbl= new Label(objDictionaryAction.getWord("ARTWORKSKETCH"));
        sketchEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPARTWORKSKETCH")));
        sketchEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/sketch_edittool.png"), sketchEditLbl);
        sketchEditVB.setPrefWidth(80);
        sketchEditVB.getStyleClass().addAll("VBox");
        // Vertical mirror edit item
        VBox verticalMirrorEditVB = new VBox(); 
        Label verticalMirrorEditLbl= new Label(objDictionaryAction.getWord("VERTICALMIRROR"));
        verticalMirrorEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPVERTICALMIRROR")));
        verticalMirrorEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/vertical_mirror.png"), verticalMirrorEditLbl);
        verticalMirrorEditVB.setPrefWidth(80);
        verticalMirrorEditVB.getStyleClass().addAll("VBox");   
        // horizontal mirros edit item
        VBox horizentalMirrorEditVB = new VBox(); 
        Label horizentalMirrorEditLbl= new Label(objDictionaryAction.getWord("HORIZENTALMIRROR"));
        horizentalMirrorEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPHORIZENTALMIRROR")));
        horizentalMirrorEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/horizontal_mirror.png"), horizentalMirrorEditLbl);
        horizentalMirrorEditVB.setPrefWidth(80);
        horizentalMirrorEditVB.getStyleClass().addAll("VBox");   
        // clockwise rotation edit item
        VBox clockwiseRotationEditVB = new VBox(); 
        Label clockwiseRotationEditLbl= new Label(objDictionaryAction.getWord("CLOCKROTATION"));
        clockwiseRotationEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLOCKROTATION")));
        clockwiseRotationEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/rotate_90.png"), clockwiseRotationEditLbl);
        clockwiseRotationEditVB.setPrefWidth(80);
        clockwiseRotationEditVB.getStyleClass().addAll("VBox");   
        // Anti clock wise rotation edit item
        VBox anticlockwiseRotationEditVB = new VBox(); 
        Label anticlockwiseRotationEditLbl= new Label(objDictionaryAction.getWord("ANTICLOCKROTATION"));
        anticlockwiseRotationEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPANTICLOCKROTATION")));
        anticlockwiseRotationEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/rotate_anti_90.png"), anticlockwiseRotationEditLbl);
        anticlockwiseRotationEditVB.setPrefWidth(80);
        anticlockwiseRotationEditVB.getStyleClass().addAll("VBox"); 
        // move Right
        VBox moveRightEditVB = new VBox(); 
        Label moveRightEditLbl= new Label(objDictionaryAction.getWord("MOVERIGHT"));
        moveRightEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVERIGHT")));
        moveRightEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/move_right.png"), moveRightEditLbl);
        moveRightEditVB.setPrefWidth(80);
        moveRightEditVB.getStyleClass().addAll("VBox");    
        // move Left
        VBox moveLeftEditVB = new VBox(); 
        Label moveLeftEditLbl= new Label(objDictionaryAction.getWord("MOVELEFT"));
        moveLeftEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVELEFT")));
        moveLeftEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/move_left.png"), moveLeftEditLbl);
        moveLeftEditVB.setPrefWidth(80);
        moveLeftEditVB.getStyleClass().addAll("VBox");    
        // move Up
        VBox moveUpEditVB = new VBox(); 
        Label moveUpEditLbl= new Label(objDictionaryAction.getWord("MOVEUP"));
        moveUpEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVEUP")));
        moveUpEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/move_up.png"), moveUpEditLbl);
        moveUpEditVB.setPrefWidth(80);
        moveUpEditVB.getStyleClass().addAll("VBox");    
        // move Down
        VBox moveDownEditVB = new VBox(); 
        Label moveDownEditLbl= new Label(objDictionaryAction.getWord("MOVEDOWN"));
        moveDownEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVEDOWN")));
        moveDownEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/move_down.png"), moveDownEditLbl);
        moveDownEditVB.setPrefWidth(80);
        moveDownEditVB.getStyleClass().addAll("VBox");
        // move Right 8
        VBox moveRight8EditVB = new VBox(); 
        Label moveRight8EditLbl= new Label(objDictionaryAction.getWord("MOVERIGHT8"));
        moveRight8EditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVERIGHT8")));
        moveRight8EditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/move_right_by_8.png"), moveRight8EditLbl);
        moveRight8EditVB.setPrefWidth(80);
        moveRight8EditVB.getStyleClass().addAll("VBox");    
        // move Left 8
        VBox moveLeft8EditVB = new VBox(); 
        Label moveLeft8EditLbl= new Label(objDictionaryAction.getWord("MOVELEFT8"));
        moveLeft8EditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVELEFT8")));
        moveLeft8EditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/move_left_by_8.png"), moveLeft8EditLbl);
        moveLeft8EditVB.setPrefWidth(80);
        moveLeft8EditVB.getStyleClass().addAll("VBox");    
        // move Up 8
        VBox moveUp8EditVB = new VBox(); 
        Label moveUp8EditLbl= new Label(objDictionaryAction.getWord("MOVEUP8"));
        moveUp8EditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVEUP8")));
        moveUp8EditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/move_up_by_8.png"), moveUp8EditLbl);
        moveUp8EditVB.setPrefWidth(80);
        moveUp8EditVB.getStyleClass().addAll("VBox");    
        // move Down 8
        VBox moveDown8EditVB = new VBox(); 
        Label moveDown8EditLbl= new Label(objDictionaryAction.getWord("MOVEDOWN8"));
        moveDown8EditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVEDOWN8")));
        moveDown8EditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/move_down_by_8.png"), moveDown8EditLbl);
        moveDown8EditVB.setPrefWidth(80);
        moveDown8EditVB.getStyleClass().addAll("VBox");
        // Tilt Right
        VBox tiltRightEditVB = new VBox(); 
        Label tiltRightEditLbl= new Label(objDictionaryAction.getWord("TILTRIGHT"));
        tiltRightEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTILTRIGHT")));
        tiltRightEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/tilt_right.png"), tiltRightEditLbl);
        tiltRightEditVB.setPrefWidth(80);
        tiltRightEditVB.getStyleClass().addAll("VBox");    
        // Tilt Left
        VBox tiltLeftEditVB = new VBox(); 
        Label tiltLeftEditLbl= new Label(objDictionaryAction.getWord("TILTLEFT"));
        tiltLeftEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTILTLEFT")));
        tiltLeftEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/tilt_left.png"), tiltLeftEditLbl);
        tiltLeftEditVB.setPrefWidth(80);
        tiltLeftEditVB.getStyleClass().addAll("VBox");    
        // Tilt Up
        VBox tiltUpEditVB = new VBox(); 
        Label tiltUpEditLbl= new Label(objDictionaryAction.getWord("TILTUP"));
        tiltUpEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTILTUP")));
        tiltUpEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/tilt_up.png"), tiltUpEditLbl);
        tiltUpEditVB.setPrefWidth(80);
        tiltUpEditVB.getStyleClass().addAll("VBox");    
        // Tilt Down
        VBox tiltDownEditVB = new VBox(); 
        Label tiltDownEditLbl= new Label(objDictionaryAction.getWord("TILTDOWN"));
        tiltDownEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTILTDOWN")));
        tiltDownEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/tilt_down.png"), tiltDownEditLbl);
        tiltDownEditVB.setPrefWidth(80);
        tiltDownEditVB.getStyleClass().addAll("VBox");
        //add enable disable condtion
        if(!isWorkingMode){
            resizeEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/select.png"));
            resizeEditVB.setDisable(true);
            resizeEditVB.setCursor(Cursor.WAIT);
            repeatEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/tiled_view.png"));
            repeatEditVB.setDisable(true);
            repeatEditVB.setCursor(Cursor.WAIT);
            graphPatternEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/graph_editor.png"));
            graphPatternEditVB.setDisable(true);
            graphPatternEditVB.setCursor(Cursor.WAIT);
            fillColorWeaveEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/fill_tool.png"));
            fillColorWeaveEditVB.setDisable(true);
            fillColorWeaveEditVB.setCursor(Cursor.WAIT);
            colorPropertiesEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/color_palette.png"));
            colorPropertiesEditVB.setDisable(true);
            colorPropertiesEditVB.setCursor(Cursor.WAIT);
            reduceColorEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/color_editor.png"));
            reduceColorEditVB.setDisable(true);
            reduceColorEditVB.setCursor(Cursor.WAIT);
            sketchEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/sketch_edittool.png"));
            sketchEditVB.setDisable(true);
            sketchEditVB.setCursor(Cursor.WAIT);
            verticalMirrorEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/vertical_mirror.png"));
            verticalMirrorEditVB.setDisable(true);
            verticalMirrorEditVB.setCursor(Cursor.WAIT);
            horizentalMirrorEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/horizontal_mirror.png"));
            horizentalMirrorEditVB.setDisable(true);
            horizentalMirrorEditVB.setCursor(Cursor.WAIT);
            clockwiseRotationEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/rotate_90.png"));
            clockwiseRotationEditVB.setDisable(true);
            clockwiseRotationEditVB.setCursor(Cursor.WAIT);
            anticlockwiseRotationEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/rotate_anti_90.png"));
            anticlockwiseRotationEditVB.setDisable(true);
            anticlockwiseRotationEditVB.setCursor(Cursor.WAIT);
            moveRightEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/move_right.png"));
            moveRightEditVB.setDisable(true);
            moveRightEditVB.setCursor(Cursor.WAIT);
            moveLeftEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/move_left.png"));
            moveLeftEditVB.setDisable(true);
            moveLeftEditVB.setCursor(Cursor.WAIT);
            moveUpEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/move_up.png"));
            moveUpEditVB.setDisable(true);
            moveUpEditVB.setCursor(Cursor.WAIT);
            moveDownEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/move_down.png"));
            moveDownEditVB.setDisable(true);
            moveDownEditVB.setCursor(Cursor.WAIT);
            moveRight8EditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/move_right_by_8.png"));
            moveRight8EditVB.setDisable(true);
            moveRight8EditVB.setCursor(Cursor.WAIT);
            moveLeft8EditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/move_left_by_8.png"));
            moveLeft8EditVB.setDisable(true);
            moveLeft8EditVB.setCursor(Cursor.WAIT);
            moveUp8EditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/move_up_by_8.png"));
            moveUp8EditVB.setDisable(true);
            moveUp8EditVB.setCursor(Cursor.WAIT);
            moveDown8EditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/move_down_by_8.png"));
            moveDown8EditVB.setDisable(true);
            moveDown8EditVB.setCursor(Cursor.WAIT);
            tiltRightEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/tilt_right.png"));
            tiltRightEditVB.setDisable(true);
            tiltRightEditVB.setCursor(Cursor.WAIT);
            tiltLeftEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/tilt_left.png"));
            tiltLeftEditVB.setDisable(true);
            tiltLeftEditVB.setCursor(Cursor.WAIT);
            tiltUpEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/tilt_up.png"));
            tiltUpEditVB.setDisable(true);
            tiltUpEditVB.setCursor(Cursor.WAIT);
            tiltDownEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/tilt_down.png"));
            tiltDownEditVB.setDisable(true);
            tiltDownEditVB.setCursor(Cursor.WAIT);
        }else{
            resizeEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/select.png"));
            resizeEditVB.setDisable(false);
            resizeEditVB.setCursor(Cursor.HAND); 
            repeatEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/tiled_view.png"));
            repeatEditVB.setDisable(false);
            repeatEditVB.setCursor(Cursor.HAND); 
            graphPatternEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/graph_editor.png"));
            graphPatternEditVB.setDisable(false);
            graphPatternEditVB.setCursor(Cursor.HAND);
            fillColorWeaveEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/fill_tool.png"));
            fillColorWeaveEditVB.setDisable(false);
            fillColorWeaveEditVB.setCursor(Cursor.HAND); 
            colorPropertiesEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/color_palette.png"));
            colorPropertiesEditVB.setDisable(false);
            colorPropertiesEditVB.setCursor(Cursor.HAND); 
            reduceColorEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/color_editor.png"));
            reduceColorEditVB.setDisable(false);
            reduceColorEditVB.setCursor(Cursor.HAND); 
            sketchEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/sketch_edittool.png"));
            sketchEditVB.setDisable(false);
            sketchEditVB.setCursor(Cursor.HAND);
            verticalMirrorEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/vertical_mirror.png"));
            verticalMirrorEditVB.setDisable(false);
            verticalMirrorEditVB.setCursor(Cursor.HAND); 
            horizentalMirrorEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/horizontal_mirror.png"));
            horizentalMirrorEditVB.setDisable(false);
            horizentalMirrorEditVB.setCursor(Cursor.HAND); 
            clockwiseRotationEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/rotate_90.png"));
            clockwiseRotationEditVB.setDisable(false);
            clockwiseRotationEditVB.setCursor(Cursor.HAND); 
            anticlockwiseRotationEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/rotate_anti_90.png"));
            anticlockwiseRotationEditVB.setDisable(false);
            anticlockwiseRotationEditVB.setCursor(Cursor.HAND); 
            moveRightEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/move_right.png"));
            moveRightEditVB.setDisable(false);
            moveRightEditVB.setCursor(Cursor.HAND);
            moveLeftEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/move_left.png"));
            moveLeftEditVB.setDisable(false);
            moveLeftEditVB.setCursor(Cursor.HAND);
            moveUpEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/move_up.png"));
            moveUpEditVB.setDisable(false);
            moveUpEditVB.setCursor(Cursor.HAND);
            moveDownEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/move_down.png"));
            moveDownEditVB.setDisable(false);
            moveDownEditVB.setCursor(Cursor.HAND);
            moveRight8EditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/move_right_by_8.png"));
            moveRight8EditVB.setDisable(false);
            moveRight8EditVB.setCursor(Cursor.HAND);
            moveLeft8EditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/move_left_by_8.png"));
            moveLeft8EditVB.setDisable(false);
            moveLeft8EditVB.setCursor(Cursor.HAND);
            moveUp8EditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/move_up_by_8.png"));
            moveUp8EditVB.setDisable(false);
            moveUp8EditVB.setCursor(Cursor.HAND);
            moveDown8EditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/move_down_by_8.png"));
            moveDown8EditVB.setDisable(false);
            moveDown8EditVB.setCursor(Cursor.HAND);
            tiltRightEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/tilt_right.png"));
            tiltRightEditVB.setDisable(false);
            tiltRightEditVB.setCursor(Cursor.HAND);
            tiltLeftEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/tilt_left.png"));
            tiltLeftEditVB.setDisable(false);
            tiltLeftEditVB.setCursor(Cursor.HAND);
            tiltUpEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/tilt_up.png"));
            tiltUpEditVB.setDisable(false);
            tiltUpEditVB.setCursor(Cursor.HAND);
            tiltDownEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/tilt_down.png"));
            tiltDownEditVB.setDisable(false);
            tiltDownEditVB.setCursor(Cursor.HAND);
        }
        //Add the action to Buttons.
        resizeEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resizeArtwork();
            }
        });
        repeatEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                repeatArtwork();
            }
        });
        
        graphPatternEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                correctGraph();
            }
        });
        fillColorWeaveEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artworkColorWeaveFill();
            }
        });
        colorPropertiesEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artworkColorProperties();
            }
        });
        reduceColorEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artworkColorReduction();
            }
        });
        sketchEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artworkSketch();
            }
        });
        verticalMirrorEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {                
                artworkVerticalMirror();
            }
        });        
        horizentalMirrorEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {                
                artworkHorizentalMirror();
            }
        });
        clockwiseRotationEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {                
                artworkClockRotation();
            }
        });
        anticlockwiseRotationEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {                
                artworkAntiClockRotation();
            }
        });
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(resizeEditVB,repeatEditVB,graphPatternEditVB,fillColorWeaveEditVB,colorPropertiesEditVB,reduceColorEditVB,sketchEditVB,verticalMirrorEditVB,horizentalMirrorEditVB,clockwiseRotationEditVB,anticlockwiseRotationEditVB);
        //flow.getChildren().addAll(undoEditVB,redoEditVB);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
  }
//////////////////////////// Trasnform Menu Action ///////////////////////////
    /**
     * resizeArtwork
     * <p>
     * This method is used for creating GUI of artwork resize pane.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating GUI of artwork resize pane.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void resizeArtwork(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONRESIZESCALE"));
        final Stage artworkPopupStage = new Stage();
        artworkPopupStage.initOwner(artworkStage);
        artworkPopupStage.initStyle(StageStyle.UTILITY);
        //artworkPopupStage.initModality(Modality.WINDOW_MODAL);
        GridPane popup=new GridPane();
        popup.setId("popup");
        popup.setHgap(10);
        popup.setVgap(10);

        popup.add(new Label("Artwork Size:"), 0, 0);
        popup.add(new Label((int)bufferedImage.getWidth()+" x "+(int)bufferedImage.getHeight()), 1, 0);
        popup.add(new Label("Loom Prefrence:"), 0, 1);
        popup.add(new Label(objConfiguration.getIntEnds()+" x "+objConfiguration.getIntPixs()), 1, 1);
        popup.add(new Label(objConfiguration.getStrClothType()), 2, 1);
        
        Label artworkWidth = new Label(objDictionaryAction.getWord("ARTWORKWIDTH")+" ("+objDictionaryAction.getWord("ENDS")+"):");
        popup.add(artworkWidth, 0, 2);
        final TextField txtWidth = new TextField(){
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
        txtWidth.setText(Integer.toString((int)bufferedImage.getWidth()));
        popup.add(txtWidth, 1, 2);
        
        Label artworkWidthHooks = new Label(objDictionaryAction.getWord("ARTWORKWIDTH")+" ("+objDictionaryAction.getWord("HOOKS")+"):");
        popup.add(artworkWidthHooks, 0, 3);
        final TextField txtWidthHooks = new TextField();
        txtWidthHooks.setText(Integer.toString((int) Math.ceil(bufferedImage.getWidth()/objConfiguration.getIntTPD())));
        popup.add(txtWidthHooks, 1, 3);
        
        Label artworkHeight = new Label(objDictionaryAction.getWord("ARTWORKLENGTH")+" ("+objDictionaryAction.getWord("PICKS")+"):");
        popup.add(artworkHeight, 0, 4);
        final TextField txtHeight = new TextField(){
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
        txtHeight.setText(Integer.toString((int)bufferedImage.getHeight()));
        popup.add(txtHeight, 1, 4);
        
        popup.add(new Label("Pixels"), 2, 2);
        final ImageView pixelIV = new ImageView(objConfiguration.getStrColour()+"/pattern_unlock.png");
        popup.add(pixelIV, 2, 3);
        
        Label artworkWidthMesaure = new Label(objDictionaryAction.getWord("ARTWORKWIDTH")+":");
        popup.add(artworkWidthMesaure, 0, 5);
        final TextField txtWidthMesaure = new TextField(){
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
        txtWidthMesaure.setText(Double.toString(bufferedImage.getWidth()/(float)objConfiguration.getIntEPI()));
        popup.add(txtWidthMesaure, 1, 5);
        
        Label artworkHeightMeasure = new Label(objDictionaryAction.getWord("ARTWORKLENGTH")+":");
        popup.add(artworkHeightMeasure, 0, 6);
        final TextField txtHeightMesaure = new TextField(){
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
        txtHeightMesaure.setText(Double.toString(bufferedImage.getHeight()/(float)objConfiguration.getIntPPI())); 
        popup.add(txtHeightMesaure, 1, 6);
        
        popup.add(new Label("Inches"), 2, 5);
        final ImageView linearIV = new ImageView(objConfiguration.getStrColour()+"/pattern_lock.png");
        popup.add(linearIV, 2, 6);
        
        Label artworkWidthDensity = new Label(objDictionaryAction.getWord("DENSITYWIDTH")+" ("+objDictionaryAction.getWord("EPI")+"):");
        popup.add(artworkWidthDensity, 0, 7);
        final TextField txtWidthDensity = new TextField(){
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
        txtWidthDensity.setText(Integer.toString(objConfiguration.getIntEPI()));
        popup.add(txtWidthDensity, 1, 7);
        
        Label artworkWidthDensityHooks = new Label(objDictionaryAction.getWord("DENSITYWIDTH")+" ("+objDictionaryAction.getWord("HPI")+"):");
        popup.add(artworkWidthDensityHooks, 0, 8);
        final TextField txtWidthDensityHooks = new TextField();
        txtWidthDensityHooks.setText(Integer.toString(objConfiguration.getIntHPI()));
        popup.add(txtWidthDensityHooks, 1, 8);
        
        Label artworkHeightDensity = new Label(objDictionaryAction.getWord("DENSITYLENGTH")+" ("+objDictionaryAction.getWord("PPI")+"):");
        popup.add(artworkHeightDensity, 0, 9);
        final TextField txtHeightDensity = new TextField(){
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
        txtHeightDensity.setText(Integer.toString(objConfiguration.getIntPPI()));
        popup.add(txtHeightDensity, 1, 9);
        
        popup.add(new Label("Threads per Inch"), 2, 7);
        final ImageView densityIV = new ImageView(objConfiguration.getStrColour()+"/pattern_unlock.png");
        popup.add(densityIV, 2, 8);
        
        txtWidthMesaure.setDisable(true);
        txtHeightMesaure.setDisable(true);
        txtWidthHooks.setDisable(true);
        txtWidthDensityHooks.setDisable(true);
        
        final CheckBox aspectRatioCB = new CheckBox(objDictionaryAction.getWord("ASPECTRATIO"));
        aspectRatioCB.setSelected(false);
        aspectRatioCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
           public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
          }
        });
        popup.add(aspectRatioCB, 1, 10);
        
        pixelIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(resizeLockValue==1){
                    resizeLockValue=2; 
                    pixelIV.setImage(new Image(objConfiguration.getStrColour()+"/pattern_unlock.png"));
                    linearIV.setImage(new Image(objConfiguration.getStrColour()+"/pattern_lock.png"));
                    densityIV.setImage(new Image(objConfiguration.getStrColour()+"/pattern_unlock.png"));
                    txtWidth.setDisable(false);
                    txtHeight.setDisable(false);
                    txtWidthMesaure.setDisable(true);
                    txtHeightMesaure.setDisable(true);
                    txtWidthDensity.setDisable(false);
                    txtHeightDensity.setDisable(false);
                }else{
                    resizeLockValue=1;
                    pixelIV.setImage(new Image(objConfiguration.getStrColour()+"/pattern_lock.png"));
                    linearIV.setImage(new Image(objConfiguration.getStrColour()+"/pattern_unlock.png"));
                    densityIV.setImage(new Image(objConfiguration.getStrColour()+"/pattern_unlock.png"));
                    txtWidth.setDisable(true);
                    txtHeight.setDisable(true);
                    txtWidthMesaure.setDisable(false);
                    txtHeightMesaure.setDisable(false);
                    txtWidthDensity.setDisable(false);
                    txtHeightDensity.setDisable(false);
                }
            }
        });
        
        linearIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(resizeLockValue==2){
                    resizeLockValue=1; 
                    pixelIV.setImage(new Image(objConfiguration.getStrColour()+"/pattern_lock.png"));
                    linearIV.setImage(new Image(objConfiguration.getStrColour()+"/pattern_unlock.png"));
                    densityIV.setImage(new Image(objConfiguration.getStrColour()+"/pattern_unlock.png"));
                    txtWidth.setDisable(true);
                    txtHeight.setDisable(true);
                    txtWidthMesaure.setDisable(false);
                    txtHeightMesaure.setDisable(false);
                    txtWidthDensity.setDisable(false);
                    txtHeightDensity.setDisable(false);
                }else{
                    resizeLockValue=2;
                    pixelIV.setImage(new Image(objConfiguration.getStrColour()+"/pattern_unlock.png"));
                    linearIV.setImage(new Image(objConfiguration.getStrColour()+"/pattern_lock.png"));
                    densityIV.setImage(new Image(objConfiguration.getStrColour()+"/pattern_unlock.png"));
                    txtWidth.setDisable(false);
                    txtHeight.setDisable(false);
                    txtWidthMesaure.setDisable(true);
                    txtHeightMesaure.setDisable(true);
                    txtWidthDensity.setDisable(false);
                    txtHeightDensity.setDisable(false);
                }
            }
        });
        
        densityIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(resizeLockValue==3){
                    resizeLockValue=2; 
                    pixelIV.setImage(new Image(objConfiguration.getStrColour()+"/pattern_unlock.png"));
                    linearIV.setImage(new Image(objConfiguration.getStrColour()+"/pattern_lock.png"));
                    densityIV.setImage(new Image(objConfiguration.getStrColour()+"/pattern_unlock.png"));
                    txtWidth.setDisable(false);
                    txtHeight.setDisable(false);
                    txtWidthMesaure.setDisable(true);
                    txtHeightMesaure.setDisable(true);
                    txtWidthDensity.setDisable(false);
                    txtHeightDensity.setDisable(false);
                }else{
                    resizeLockValue=3;
                    pixelIV.setImage(new Image(objConfiguration.getStrColour()+"/pattern_unlock.png"));
                    linearIV.setImage(new Image(objConfiguration.getStrColour()+"/pattern_unlock.png"));
                    densityIV.setImage(new Image(objConfiguration.getStrColour()+"/pattern_lock.png"));
                    txtWidth.setDisable(false);
                    txtHeight.setDisable(false);
                    txtWidthMesaure.setDisable(false);
                    txtHeightMesaure.setDisable(false);
                    txtWidthDensity.setDisable(true);
                    txtHeightDensity.setDisable(true);
                }
            }
        });
        
        txtWidth.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(!txtWidth.isFocused())
                    return;
                try {
                    if(t1.trim().equals("") || Integer.parseInt(t1)<0){
                        lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                        //txtHeight.setText(t1);
                    } else {
                        if(aspectRatioCB.isSelected()){
                            int intHeight = Integer.parseInt(txtWidth.getText())*bufferedImage.getHeight()/bufferedImage.getWidth();
                            if(intHeight<1){
                                lblStatus.setText(objDictionaryAction.getWord("WRONGINPUT"));
                                intHeight = 1;
                                txtWidth.setText(Integer.toString(intHeight*bufferedImage.getWidth()/bufferedImage.getHeight()));
                            }                            
                            txtHeight.setText(Integer.toString(intHeight));
                        }
                        txtWidthHooks.setText(Integer.toString((int)Math.ceil(Integer.parseInt(txtWidth.getText())/(double)objConfiguration.getIntTPD())));
                        if(resizeLockValue==2){
                            txtWidthDensity.setText(Integer.toString((int)(Integer.parseInt(txtWidth.getText())/Double.parseDouble(txtWidthMesaure.getText()))));
                            txtHeightDensity.setText(Integer.toString((int)(Integer.parseInt(txtHeight.getText())/Double.parseDouble(txtHeightMesaure.getText()))));
                            txtWidthDensityHooks.setText(Integer.toString((int)Math.ceil(Integer.parseInt(txtWidthDensity.getText())/(double)objConfiguration.getIntTPD())));
                        }else{
                            txtWidthMesaure.setText(Double.toString((float)Integer.parseInt(txtWidth.getText())/Integer.parseInt(txtWidthDensity.getText())));
                            txtHeightMesaure.setText(Double.toString((float)Integer.parseInt(txtHeight.getText())/Integer.parseInt(txtHeightDensity.getText()))); 
                        }
                    }
                } catch (Exception ex) {
                    new Logging("SEVERE",ArtworkView.class.getName(),"width property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });    
        txtHeight.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(!txtHeight.isFocused())
                    return;
                try {
                    if(t1.trim().equals("") || Integer.parseInt(t1)<0){
                        lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                        //txtWidth.setText(t1);
                    } else {
                        if(aspectRatioCB.isSelected()){
                            int intWidth = Integer.parseInt(txtHeight.getText())*bufferedImage.getWidth()/bufferedImage.getHeight();
                            if(intWidth<1){
                                lblStatus.setText(objDictionaryAction.getWord("WRONGINPUT"));
                                intWidth = 1;
                                txtHeight.setText(Integer.toString(intWidth*bufferedImage.getHeight()/bufferedImage.getWidth()));
                            }                            
                            txtWidth.setText(Integer.toString(intWidth));
                        }
                        txtWidthHooks.setText(Integer.toString((int)Math.ceil(Integer.parseInt(txtWidth.getText())/(double)objConfiguration.getIntTPD())));
                        if(resizeLockValue==2){
                            txtWidthDensity.setText(Integer.toString((int)(Integer.parseInt(txtWidth.getText())/Double.parseDouble(txtWidthMesaure.getText()))));
                            txtHeightDensity.setText(Integer.toString((int)(Integer.parseInt(txtHeight.getText())/Double.parseDouble(txtHeightMesaure.getText()))));
                            txtWidthDensityHooks.setText(Integer.toString((int)Math.ceil(Integer.parseInt(txtWidthDensity.getText())/(double)objConfiguration.getIntTPD())));
                        }else{
                            txtWidthMesaure.setText(Double.toString((float)Integer.parseInt(txtWidth.getText())/Integer.parseInt(txtWidthDensity.getText())));
                            txtHeightMesaure.setText(Double.toString((float)Integer.parseInt(txtHeight.getText())/Integer.parseInt(txtHeightDensity.getText())));
                        }
                    }
                } catch (Exception ex) {
                    new Logging("SEVERE",ArtworkView.class.getName(),"height property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });        

        txtWidthMesaure.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(!txtWidthMesaure.isFocused())
                    return;
                try {
                    if(t1.trim().equals("") || Double.parseDouble(t1)<0){
                        lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                        //txtHeight.setText(t1);
                    } else{
                        if(aspectRatioCB.isSelected()){
                            double dblHeight = Double.parseDouble(txtWidthMesaure.getText())*bufferedImage.getHeight()/bufferedImage.getWidth();
                            if(dblHeight<0){
                                lblStatus.setText(objDictionaryAction.getWord("WRONGINPUT"));
                                dblHeight = 1;
                                txtWidthMesaure.setText(Double.toString(dblHeight*bufferedImage.getWidth()/bufferedImage.getHeight()));
                            }                            
                            txtHeightMesaure.setText(Double.toString(dblHeight));
                        }
                        if(resizeLockValue==1){
                            txtWidthDensity.setText(Integer.toString((int)(Integer.parseInt(txtWidth.getText())/Double.parseDouble(txtWidthMesaure.getText()))));
                            txtHeightDensity.setText(Integer.toString((int)(Integer.parseInt(txtHeight.getText())/Double.parseDouble(txtHeightMesaure.getText()))));
                            txtWidthDensityHooks.setText(Integer.toString((int)Math.ceil(Integer.parseInt(txtWidthDensity.getText())/(double)objConfiguration.getIntTPD())));
                        }else{
                            txtWidth.setText(Integer.toString((int)(Double.parseDouble(txtWidthMesaure.getText())*Integer.parseInt(txtWidthDensity.getText()))));
                            txtHeight.setText(Integer.toString((int)(Double.parseDouble(txtHeightMesaure.getText())*Integer.parseInt(txtHeightDensity.getText()))));
                            txtWidthHooks.setText(Integer.toString((int)Math.ceil(Integer.parseInt(txtWidth.getText())/(double)objConfiguration.getIntTPD())));
                        }
                    }
                } catch (Exception ex) {
                    new Logging("SEVERE",ArtworkView.class.getName(),"width property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        txtHeightMesaure.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(!txtHeightMesaure.isFocused())
                    return;
                try {
                    if(t1.trim().equals("") || Double.parseDouble(t1)<0){
                        lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                        //txtWidth.setText(t1);
                    } else {
                        if(aspectRatioCB.isSelected()){
                            double dblWidth = Double.parseDouble(txtHeightMesaure.getText())*bufferedImage.getWidth()/bufferedImage.getHeight();
                            if(dblWidth<0){
                                lblStatus.setText(objDictionaryAction.getWord("WRONGINPUT"));
                                dblWidth = 1;
                                txtHeightMesaure.setText(Double.toString(dblWidth*bufferedImage.getHeight()/bufferedImage.getWidth()));
                            }                            
                            txtWidthMesaure.setText(Double.toString(dblWidth));
                        }
                        if(resizeLockValue==1){
                            txtWidthDensity.setText(Integer.toString((int)(Integer.parseInt(txtWidth.getText())/Double.parseDouble(txtWidthMesaure.getText()))));
                            txtHeightDensity.setText(Integer.toString((int)(Integer.parseInt(txtHeight.getText())/Double.parseDouble(txtHeightMesaure.getText()))));
                            txtWidthDensityHooks.setText(Integer.toString((int)Math.ceil(Integer.parseInt(txtWidthDensity.getText())/(double)objConfiguration.getIntTPD())));
                        }else{
                            txtWidth.setText(Integer.toString((int)(Double.parseDouble(txtWidthMesaure.getText())*Integer.parseInt(txtWidthDensity.getText()))));
                            txtHeight.setText(Integer.toString((int)(Double.parseDouble(txtHeightMesaure.getText())*Integer.parseInt(txtHeightDensity.getText()))));
                            txtWidthHooks.setText(Integer.toString((int)Math.ceil(Integer.parseInt(txtWidth.getText())/(double)objConfiguration.getIntTPD())));
                        }
                    }
                } catch (Exception ex) {
                    new Logging("SEVERE",ArtworkView.class.getName(),"height property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });        
        
        txtWidthDensity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(!txtWidthDensity.isFocused())
                    return;
                try {
                    if(t1.trim().equals("") || Integer.parseInt(t1)<0){
                        lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                        //txtHeight.setText(t1);
                    } else {
                        if(aspectRatioCB.isSelected()){
                            int intHeight = Integer.parseInt(txtWidthDensity.getText())*bufferedImage.getHeight()/bufferedImage.getWidth();
                            if(intHeight<1){
                                lblStatus.setText(objDictionaryAction.getWord("WRONGINPUT"));
                                intHeight = 1;
                                txtWidthDensity.setText(Integer.toString(intHeight*bufferedImage.getWidth()/bufferedImage.getHeight()));
                            }                            
                            txtHeightDensity.setText(Integer.toString(intHeight));
                        }
                        txtWidthDensityHooks.setText(Integer.toString((int)Math.ceil(Integer.parseInt(txtWidthDensity.getText())/(double)objConfiguration.getIntTPD())));
                        if(resizeLockValue==1){
                            txtWidthMesaure.setText(Double.toString((float)Integer.parseInt(txtWidth.getText())/Integer.parseInt(txtWidthDensity.getText())));
                            txtHeightMesaure.setText(Double.toString((float)Integer.parseInt(txtHeight.getText())/Integer.parseInt(txtHeightDensity.getText()))); 
                        }else{
                            txtWidth.setText(Integer.toString((int)(Double.parseDouble(txtWidthMesaure.getText())*Integer.parseInt(txtWidthDensity.getText()))));
                            txtHeight.setText(Integer.toString((int)(Double.parseDouble(txtHeightMesaure.getText())*Integer.parseInt(txtHeightDensity.getText()))));
                            txtWidthHooks.setText(Integer.toString((int)Math.ceil(Integer.parseInt(txtWidth.getText())/(double)objConfiguration.getIntTPD())));
                        }
                    }
                } catch (Exception ex) {
                    new Logging("SEVERE",ArtworkView.class.getName(),"width property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });    
        txtHeightDensity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(!txtHeightDensity.isFocused())
                    return;
                try {
                    if(t1.equals("") && Integer.parseInt(t1)<0){
                        lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                        //txtWidth.setText(t1);
                    } else {
                        if(aspectRatioCB.isSelected()){
                            int intWidth = Integer.parseInt(txtHeightDensity.getText())*bufferedImage.getWidth()/bufferedImage.getHeight();
                            if(intWidth<1){
                                lblStatus.setText(objDictionaryAction.getWord("WRONGINPUT"));
                                intWidth = 1;
                                txtHeightDensity.setText(Integer.toString(intWidth*bufferedImage.getHeight()/bufferedImage.getWidth()));
                            }                            
                            txtWidthDensity.setText(Integer.toString(intWidth));
                        }
                        txtWidthDensityHooks.setText(Integer.toString((int)Math.ceil(Integer.parseInt(txtWidthDensity.getText())/(double)objConfiguration.getIntTPD())));
                        if(resizeLockValue==1){
                            txtWidthMesaure.setText(Double.toString((float)Integer.parseInt(txtWidth.getText())/Integer.parseInt(txtWidthDensity.getText())));
                            txtHeightMesaure.setText(Double.toString((float)Integer.parseInt(txtHeight.getText())/Integer.parseInt(txtHeightDensity.getText()))); 
                        }else{
                            txtWidth.setText(Integer.toString((int)(Double.parseDouble(txtWidthMesaure.getText())*Integer.parseInt(txtWidthDensity.getText()))));
                            txtHeight.setText(Integer.toString((int)(Double.parseDouble(txtHeightMesaure.getText())*Integer.parseInt(txtHeightDensity.getText()))));
                            txtWidthHooks.setText(Integer.toString((int)Math.ceil(Integer.parseInt(txtWidth.getText())/(double)objConfiguration.getIntTPD())));
                        }
                    }
                } catch (Exception ex) {
                    new Logging("SEVERE",ArtworkView.class.getName(),"height property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });        
        
        Button btnApply = new Button(objDictionaryAction.getWord("APPLY"));
        btnApply.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
        btnApply.setTooltip(new Tooltip(objDictionaryAction.getWord("ACTIONAPPLY")));
        //btnApply.setMaxWidth(Double.MAX_VALUE);
        btnApply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                objUR.doCommand("Apply", bufferedImage);
                //objURF.store(bufferedImage);
                //objArtworkAction = new ArtworkAction();
                //bufferedImage = objArtworkAction.getImageFromMatrix(artworkMatrix, colors);
                int width = Integer.parseInt(txtWidth.getText());
                int height = Integer.parseInt(txtHeight.getText());
                if(maxSizeCheck(width,height)){
                    //bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    BufferedImage bufferedImageesize = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
                    Graphics2D g = bufferedImageesize.createGraphics();
                    g.drawImage(bufferedImage, 0, 0, width, height, null);
                    g.dispose();
                    bufferedImage = bufferedImageesize;
                    bufferedImageesize = null;
                    initArtworkValue();
                    objConfiguration.setIntPPI(Integer.parseInt(txtHeightDensity.getText()));
                    objConfiguration.setIntEPI(Integer.parseInt(txtWidthDensity.getText()));
                    objConfiguration.setIntHPI(Integer.parseInt(txtWidthDensityHooks.getText()));
                    lblStatus.setText(objDictionaryAction.getWord("SUCCESS"));
                    artworkPopupStage.close();
                }else{
                    lblStatus.setText(objDictionaryAction.getWord("WRONGINPUT"));
                }
                System.gc();
            }
        });
        popup.add(btnApply, 0, 11);

        Button btnReset = new Button(objDictionaryAction.getWord("RESET"));
        btnReset.setGraphic(new ImageView(objConfiguration.getStrColour()+"/clear.png"));
        btnReset.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPRESET")));
        //btnReset.setMaxWidth(Double.MAX_VALUE);
        btnReset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {  
                lblStatus.setText(objDictionaryAction.getWord("ACTIONRESET"));
                txtWidth.setText(Integer.toString(bufferedImage.getWidth()));
                txtHeight.setText(Integer.toString(bufferedImage.getHeight()));
                txtWidthHooks.setText(Integer.toString((int)Math.ceil(bufferedImage.getWidth()/(double)objConfiguration.getIntTPD())));
                txtWidthDensity.setText(Integer.toString(objConfiguration.getIntEPI()));
                txtHeightDensity.setText(Integer.toString(objConfiguration.getIntPPI()));
                txtWidthDensityHooks.setText(Integer.toString(objConfiguration.getIntHPI()));
                txtWidthMesaure.setText(Double.toString((double)Integer.parseInt(txtWidth.getText())/Integer.parseInt(txtWidthDensity.getText())));
                txtHeightMesaure.setText(Double.toString((double)Integer.parseInt(txtHeight.getText())/Integer.parseInt(txtHeightDensity.getText()))); 
            }
        });
        popup.add(btnReset, 1, 11);

        Button btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
        btnCancel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
        btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
        btnCancel.setMaxWidth(Double.MAX_VALUE);
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {  
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCANCEL"));
                artworkPopupStage.close();
            }
        });
        popup.add(btnCancel, 2, 11);

        Scene popupScene = new Scene(popup);
        popupScene.getStylesheets().add(ArtworkView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        artworkPopupStage.setScene(popupScene);
        artworkPopupStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("WINDOWRESIZEARTWORK"));
        artworkPopupStage.showAndWait();        
    }    
    /**
     * repeatArtwork
     * <p>
     * This method is used for creating GUI of artwork resize pane.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating GUI of artwork resize pane.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void repeatArtwork(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONRESIZEORIENTATION"));                
        final Stage artworkPopupStage = new Stage();
        artworkPopupStage.initOwner(artworkStage);
        artworkPopupStage.initStyle(StageStyle.UTILITY);
        //artworkPopupStage.initModality(Modality.WINDOW_MODAL);
        GridPane popup=new GridPane();
        popup.setId("popup");
        popup.setHgap(10);
        popup.setVgap(10);

        Label lblRepeatMode=new Label(objDictionaryAction.getWord("REPEATMODE"));
        lblRepeatMode.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPREPEATMODE")));
        popup.add(lblRepeatMode, 0, 0);
        final ChoiceBox repeatModeCB=new ChoiceBox();
        repeatModeCB.getItems().add(0, "Rectangular (Default)");
        repeatModeCB.setValue(repeatModeCB.getItems().get(0));
        repeatModeCB.getItems().add(1, "1/2 Horizontal");
        repeatModeCB.getItems().add(2, "1/2 Vertical");
        repeatModeCB.getItems().add(3, "1/3 Horizontal");
        repeatModeCB.getItems().add(4, "1/3 Vertical");
        repeatModeCB.getItems().add(5, "1/4 Horizontal");
        repeatModeCB.getItems().add(6, "1/4 Vertical");
        repeatModeCB.getItems().add(7, "1/5 Horizontal");
        repeatModeCB.getItems().add(8, "1/5 Vertical");
        repeatModeCB.getItems().add(9, "1/6 Horizontal");
        repeatModeCB.getItems().add(10, "1/6 Vertical");
        popup.add(repeatModeCB, 1, 0);
                
        Label vertical= new Label(objDictionaryAction.getWord("VREPEAT")+" :");
        //vertical.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        vertical.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPVREPEAT")));
        popup.add(vertical, 0, 1);
        final TextField verticalTF = new TextField(Integer.toString(objConfiguration.getIntVRepeat())){
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
        verticalTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPVREPEAT")));
        popup.add(verticalTF, 1, 1);
        
        Label horizental= new Label(objDictionaryAction.getWord("HREPEAT")+" :");
        //horizental.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        horizental.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPHREPEAT")));
        popup.add(horizental, 0, 2);
        final TextField horizentalTF = new TextField(Integer.toString(objConfiguration.getIntHRepeat())){
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
        horizentalTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPHREPEAT")));
        popup.add(horizentalTF, 1, 2);
        
        Button btnApply = new Button(objDictionaryAction.getWord("APPLY"));
        btnApply.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
        btnApply.setTooltip(new Tooltip(objDictionaryAction.getWord("ACTIONAPPLY")));
        //btnApply.setMaxWidth(Double.MAX_VALUE);
        btnApply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    objUR.doCommand("Apply", bufferedImage);
                    //objURF.store(bufferedImage);
                    objConfiguration.setIntHRepeat(Integer.parseInt(horizentalTF.getText()));
                    objConfiguration.setIntVRepeat(Integer.parseInt(verticalTF.getText()));
     
                    int vAling = 1;
                    int hAling = 1;
                    if(repeatModeCB.getValue().toString().contains("Vertical")){
                        String temp = repeatModeCB.getValue().toString();
                        vAling = Integer.parseInt(temp.substring(0, temp.indexOf("/")).trim());
                        hAling = Integer.parseInt(temp.substring(2, temp.indexOf("Vertical")).trim());
                    }else if(repeatModeCB.getValue().toString().contains("Horizontal")){
                        String temp = repeatModeCB.getValue().toString();
                        hAling = Integer.parseInt(temp.substring(0, temp.indexOf("/")).trim());
                        vAling = Integer.parseInt(temp.substring(2, temp.indexOf("Horizontal")).trim());
                    }else{
                        vAling = 1;
                        hAling = 1;
                    }
                    
                    objArtworkAction = new ArtworkAction(false);
                    bufferedImage = objArtworkAction.designRepeat(bufferedImage, vAling, hAling, objConfiguration.getIntVRepeat(), objConfiguration.getIntHRepeat());
                    //Image image=SwingFXUtils.toFXImage(bufferedImage, null);  
                    //artwork.setImage(image);
                    initArtworkValue();
                    
                    lblStatus.setText(objDictionaryAction.getWord("SUCCESS"));
                } catch (SQLException ex) {               
                    new Logging("SEVERE",ArtworkView.class.getName(),"Operation apply",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch(OutOfMemoryError ex){
                    undoArtwork();
                }
                System.gc();
                artworkPopupStage.close();
            }
        });
        popup.add(btnApply, 0, 11);

        Button btnReset = new Button(objDictionaryAction.getWord("RESET"));
        btnReset.setGraphic(new ImageView(objConfiguration.getStrColour()+"/clear.png"));
        btnReset.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPRESET")));
        //btnReset.setMaxWidth(Double.MAX_VALUE);
        btnReset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {  
                lblStatus.setText(objDictionaryAction.getWord("ACTIONRESET"));
                horizentalTF.setText(Integer.toString(objConfiguration.getIntHRepeat()));
                verticalTF.setText(Integer.toString(objConfiguration.getIntVRepeat()));
                repeatModeCB.setValue(repeatModeCB.getItems().get(0));
            }
        });
        popup.add(btnReset, 0, 3);

        Button btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
        btnCancel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
        btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
        btnCancel.setMaxWidth(Double.MAX_VALUE);
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {  
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCANCEL"));
                artworkPopupStage.close();
            }
        });
        popup.add(btnCancel, 1, 3);

        Scene popupScene = new Scene(popup);
        popupScene.getStylesheets().add(ArtworkView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        artworkPopupStage.setScene(popupScene);
        artworkPopupStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("REPEATORIENTATION"));
        artworkPopupStage.showAndWait();
        artworkPopupStage.showAndWait();        
    }
    /**
     * correctGraph
     * <p>
     * This method is used for creating GUI of artwork reduce color pane.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void correctGraph(){
        try {
            lblStatus.setText(objDictionaryAction.getWord("ACTIONGRAPHPATTERNEDIT"));
            ArtworkAction objFabricAction = new ArtworkAction();
            setCurrentShape();
            plotEditActionMode = 1; //editgraph = 1
            plotEditGraph();
            populateEditGraphPane();
            lblStatus.setText(objDictionaryAction.getWord("GOTGRAPHVIEW"));
        } catch (SQLException ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"editGraph() : Error while editing garph view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    private void plotEditGraph(){
        try {
            objUR.doCommand("WeaveFillArea", bufferedImage);
            //objURF.store(bufferedImage);
            lblStatus.setText(objDictionaryAction.getWord("GETGRIDVIEW"));
            int intHeight=(int)bufferedImage.getHeight();
            int intLength=(int)bufferedImage.getWidth();
            /////////////// for error correction  //////////////////
            if(objConfiguration.getBlnCorrectGraph()){
                BufferedImage editBufferedImage = new BufferedImage(intLength, intHeight,BufferedImage.TYPE_INT_RGB);
                Graphics2D g = editBufferedImage.createGraphics();
                g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
                g.dispose();
                objUR.doCommand("CorrectGraph", editBufferedImage);
                //objURF.store(editBufferedImage);
                for(int x = 0; x < intHeight; x++) {
                    int count = 1;
                    int temp = bufferedImage.getRGB(0, x);
                    for(int y = 0; y < intLength; y++) {
                        if(temp==bufferedImage.getRGB(y, x))
                            count++;
                        else{
                            count = 1;
                            temp = bufferedImage.getRGB(y, x);
                        }
                        if(count>objConfiguration.getIntFloatBind() && temp!=bufferedImage.getRGB(0, 0)) {//colors.get(0).getRGB()
                            bufferedImage.setRGB(y, x, bufferedImage.getRGB(0, 0));
                            count = 0;
                        }
                    }
                }
                //objConfiguration.setBlnCorrectGraph(false);
            }
            String[] data =objArtwork.getObjConfiguration().getStrGraphSize().split("x");
            editBufferedImage= new BufferedImage((int)(intLength*zoomfactor*Integer.parseInt(data[1])/graphFactor), (int)(intHeight*zoomfactor*Integer.parseInt(data[0])/graphFactor),BufferedImage.TYPE_INT_RGB);
            objGraphics2D = editBufferedImage.createGraphics();
            objGraphics2D.drawImage(bufferedImage, 0, 0, (int)(intLength*zoomfactor*Integer.parseInt(data[1])/graphFactor), (int)(intHeight*zoomfactor*Integer.parseInt(data[0])/graphFactor), null);
            objGraphics2D.setColor(java.awt.Color.BLACK);
            
            try {
                BasicStroke bs = new BasicStroke(2);
                objGraphics2D.setStroke(bs);
                VLines vlines = new VLines(objGraphics2D, intHeight, intLength, graphFactor, data, zoomfactor, (byte)3);
                /*
                //For vertical line
                for(int j = 0; j < intLength; j++) {
                    if((j%(Integer.parseInt(data[0])))==0){
                        bs = new BasicStroke(2*zoomfactor);
                        objGraphics2D.setStroke(bs);
                    }else{
                        bs = new BasicStroke(1*zoomfactor);
                        objGraphics2D.setStroke(bs);
                    }
                    objGraphics2D.drawLine((int)(j*zoomfactor*Integer.parseInt(data[1])/graphFactor), 0,  (int)(j*zoomfactor*Integer.parseInt(data[1])/graphFactor), (int)(intHeight*zoomfactor*Integer.parseInt(data[0])/graphFactor));
                }
                //For horizental line
                for(int i = 0; i < intHeight; i++) {
                    if((i%(Integer.parseInt(data[1])))==0){
                        bs = new BasicStroke(2*zoomfactor);
                        objGraphics2D.setStroke(bs);
                    }else{
                        bs = new BasicStroke(1*zoomfactor);
                        objGraphics2D.setStroke(bs);
                    }
                    objGraphics2D.drawLine(0, (int)(i*zoomfactor*Integer.parseInt(data[0])/graphFactor), (int)(intLength*zoomfactor*Integer.parseInt(data[1])/graphFactor), (int)(i*zoomfactor*Integer.parseInt(data[0])/graphFactor));
                }
               */
                while(vlines.isAlive()) {
                    new Logging("INFO",ArtworkView.class.getName(),"Main thread will be alive till the child thread is live",null);
                    Thread.sleep(100);
                }
                new Logging("INFO",ArtworkView.class.getName(),"Main thread run is over",null);
                //vlines = null;
            } catch(InterruptedException ex) {
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                new Logging("SEVERE",ArtworkView.class.getName(),"InterruptedException: Main thread interrupted",ex);
            } catch(Exception ex){
                new Logging("SEVERE",ArtworkView.class.getName(),"Exception: operation failed",ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }            
            /////////////// for error box  //////////////////
            if(objConfiguration.getBlnErrorGraph()){
                BufferedImage errorImage = ImageIO.read(new File(System.getProperty("user.dir")+"/mla/error.png"));
                for(int x = 0, p = 0; x < intHeight; x++,p+=(Integer.parseInt(data[0])*zoomfactor/graphFactor)) {
                    int count = 1;
                    int temp = bufferedImage.getRGB(0, x);
                    for(int y = 0, q = 0; y < intLength; y++,q+=(Integer.parseInt(data[1])*zoomfactor/graphFactor)) {
                        if(temp==bufferedImage.getRGB(y, x))
                            count++;
                        else{
                            count = 1;
                            temp = bufferedImage.getRGB(y, x);
                        }
                        if(count>objConfiguration.getIntFloatBind() && temp!=bufferedImage.getRGB(0, 0)) //colors.get(0).getRGB()
                            objGraphics2D.drawImage(errorImage, q, p, (int)(Integer.parseInt(data[1])*zoomfactor/graphFactor), (int)(Integer.parseInt(data[0])*zoomfactor/graphFactor), null);  
                    }
                }
            }
            //g.dispose();
            artwork.setImage(SwingFXUtils.toFXImage(editBufferedImage, null));
            container.setContent(artwork);
            //bufferedImageesize = null;
            System.gc();
            lblStatus.setText(objDictionaryAction.getWord("GOTGRIDVIEW"));
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"Exception:plotGridView() : Error while viewing grid view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch(OutOfMemoryError ex){
            new Logging("SEVERE",ArtworkView.class.getName(),"OutOfMemoryError:plotGridView() : Error while viewing grid view",null);
        }
    }
    private void artworkGraphEditEvent(MouseEvent event){
        String[] data = objArtwork.getObjConfiguration().getStrGraphSize().split("x");
        bufferedImage.setRGB((int)(event.getX()/((Integer.parseInt(data[1])/graphFactor)*zoomfactor)), (int)(event.getY()/((Integer.parseInt(data[0])/graphFactor)*zoomfactor)), editThreadValue);
        objGraphics2D.setColor(new java.awt.Color(editThreadValue));
        objGraphics2D.fillRect((int)((int)(event.getX()/((Integer.parseInt(data[1])/graphFactor)*zoomfactor))*((Integer.parseInt(data[1])/graphFactor)*zoomfactor)), (int)((int)(event.getY()/((Integer.parseInt(data[0])/graphFactor)*zoomfactor))*((Integer.parseInt(data[0])/graphFactor)*zoomfactor)), (int)((Integer.parseInt(data[1])/graphFactor)*zoomfactor), (int)((Integer.parseInt(data[0])/graphFactor)*zoomfactor));
        objGraphics2D.setColor(java.awt.Color.BLACK);
        objGraphics2D.drawRect((int)((int)(event.getX()/((Integer.parseInt(data[1])/graphFactor)*zoomfactor))*((Integer.parseInt(data[1])/graphFactor)*zoomfactor)), (int)((int)(event.getY()/((Integer.parseInt(data[0])/graphFactor)*zoomfactor))*((Integer.parseInt(data[0])/graphFactor)*zoomfactor)), (int)((Integer.parseInt(data[1])/graphFactor)*zoomfactor), (int)((Integer.parseInt(data[0])/graphFactor)*zoomfactor));
        //objFabric.getFabricMatrix()[(int)(event.getY()/(Integer.parseInt(data[0])*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()))][(int)(event.getX()/(Integer.parseInt(data[1])*zoomfactor*objFabric.getObjConfiguration().getIntBoxSize()))] = (byte)editThreadValue;
        //plotEditGraph();
        artwork.setImage(SwingFXUtils.toFXImage(editBufferedImage, null));
        container.setContent(artwork);
    }
    private void populateEditGraphPane(){
        if(artworkChildStage!=null){
            artworkChildStage.close();
            artworkChildStage = null;
            System.gc();
        }
        artworkChildStage = new Stage();
        artworkChildStage.initOwner(artworkStage);
        artworkChildStage.initStyle(StageStyle.UTILITY);
        //dialogStage.initModality(Modality.WINDOW_MODAL);
        GridPane popup=new GridPane();
        popup.setId("popup");
        popup.setHgap(10);
        popup.setVgap(10);
        popup.add(new Label(objDictionaryAction.getWord("EDITTHREADTYPE")+" : "), 0, 0, 2, 1);
        GridPane editThreadCB = new GridPane();
        editThreadCB.setAlignment(Pos.CENTER);
        editThreadCB.setHgap(5);
        editThreadCB.setVgap(5);
        editThreadCB.setCursor(Cursor.HAND);
        //editThreadCB.getChildren().clear();
        for(int i=0; i<intColor; i++){
            String webColor = String.format("#%02X%02X%02X",colors.get(i).getRed(),colors.get(i).getGreen(),colors.get(i).getBlue());
            final Label  lblColor  = new Label ();
            lblColor.setPrefSize(33, 33);
            lblColor.setStyle("-fx-background-color:"+webColor+"; -fx-border-color: #FFFFFF;");
            lblColor.setTooltip(new Tooltip("Color:"+i+"\n"+webColor+"\nR:"+colors.get(i).getRed()+"\nG:"+colors.get(i).getGreen()+"\nB:"+colors.get(i).getBlue()));
            lblColor.setUserData(colors.get(i).getRGB());
            lblColor.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    editThreadValue=Integer.parseInt(lblColor.getUserData().toString());
                }
            });
            editThreadCB.add(lblColor, i%6, i/6);
        }
        editThreadValue=colors.get(0).getRGB();
        popup.add(editThreadCB, 0, 1, 2, 1);
        
        final CheckBox errorGraphCB = new CheckBox(objDictionaryAction.getWord("SHOWERRORGRAPH"));
        errorGraphCB.setSelected(objConfiguration.getBlnErrorGraph());
        errorGraphCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
           public void changed(ObservableValue<? extends Boolean> ov,
             Boolean old_val, Boolean new_val) {
                objConfiguration.setBlnErrorGraph(errorGraphCB.isSelected());
                plotEditGraph();
            }
        });
        popup.add(errorGraphCB, 0, 2, 2, 1);
                
        final CheckBox correctGraphCB = new CheckBox(objDictionaryAction.getWord("SHOWCORRECTGRAPH"));
        correctGraphCB.setSelected(objConfiguration.getBlnCorrectGraph());
        correctGraphCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
           public void changed(ObservableValue<? extends Boolean> ov,
             Boolean old_val, Boolean new_val) {
                objConfiguration.setBlnCorrectGraph(correctGraphCB.isSelected());
                plotEditGraph();
            }
        });
        popup.add(correctGraphCB, 0, 3, 2, 1);
                
        popup.add(new Label(objDictionaryAction.getWord("FLOATBINDSIZE")+" : "), 0, 4);
        final TextField floatBindTF = new TextField(Integer.toString(objConfiguration.getIntFloatBind())){
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
        floatBindTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(floatBindTF.getText()!=null && floatBindTF.getText()!="" && Integer.parseInt(floatBindTF.getText())>0){
                    objConfiguration.setIntFloatBind(Integer.parseInt(floatBindTF.getText()));
                    plotEditGraph();
                }
            }
        });
        floatBindTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFLOATBINDSIZE")));
        popup.add(floatBindTF, 1, 4);
        
        Button btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
        btnCancel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
        btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {  
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCANCEL"));
                artworkChildStage.close();
                plotViewActionMode = 2;
                plotViewAction();
            }
        });
        popup.add(btnCancel, 1, 5, 2, 1);

        Scene popupScene = new Scene(popup);
        popupScene.getStylesheets().add(ArtworkView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        artworkChildStage.setScene(popupScene);
        artworkChildStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("WINDOWEDITGRAPH"));
        artworkChildStage.showAndWait();        
    }
    /**
     * artworkColorWeaveFill
     * <p>
     * This method is used for creating GUI of artwork reduce color pane.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating GUI of artwork reduce color pane.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void artworkColorWeaveFill(){
        if(plotViewActionMode==1){
            lblStatus.setText(objDictionaryAction.getWord("ACTIONFILLTOOL"));
            final Stage artworkPopupStage = new Stage();
            artworkPopupStage.initOwner(artworkStage);
            artworkPopupStage.initStyle(StageStyle.UTILITY);
            //artworkPopupStage.initModality(Modality.WINDOW_MODAL);
            GridPane popup=new GridPane();
            popup.setId("popup");
            popup.setHgap(10);
            popup.setVgap(10);

            Label fillToolType = new Label(objDictionaryAction.getWord("FILLTOOLTYPE")+" : ");
            fillToolType.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFILLTOOLTYPE")));
            popup.add(fillToolType, 0, 0);
            final ComboBox fillToolTypeCB = new ComboBox();
            fillToolTypeCB.getItems().addAll("Color Fill","Weave Fill");
            fillToolTypeCB.setValue(strFillType);
            fillToolTypeCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFILLTOOLTYPE")));
            popup.add(fillToolTypeCB, 1, 0);        

            Label fillToolArea = new Label(objDictionaryAction.getWord("FILLTOOLAREA")+" : ");
            fillToolArea.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFILLTOOLAREA")));
            popup.add(fillToolArea, 0, 1);
            final ComboBox fillToolAreaCB = new ComboBox();
            fillToolAreaCB.getItems().addAll("Color All Instance","Color Single Instance");
            fillToolAreaCB.setValue(strFillArea);
            fillToolAreaCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFILLTOOLAREA")));
            popup.add(fillToolAreaCB, 1, 1);        

            Separator sepHor1 = new Separator();
            sepHor1.setValignment(VPos.CENTER);
            GridPane.setConstraints(sepHor1, 0, 2);
            GridPane.setColumnSpan(sepHor1, 2);
            popup.getChildren().add(sepHor1);

            Label fillToolBorderWidth = new Label(objDictionaryAction.getWord("FILLTOOLBORDERWIDTH")+" : ");
            fillToolBorderWidth.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFILLTOOLBORDERWIDTH")));
            popup.add(fillToolBorderWidth, 0, 3);
            final TextField fillToolBorderWidthTF= new TextField(String.valueOf(intFillBorder)){
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
            fillToolBorderWidthTF.setPromptText(objDictionaryAction.getWord("TOOLTIPFILLTOOLBORDERWIDTH"));
            fillToolBorderWidthTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFILLTOOLBORDERWIDTH")));
            popup.add(fillToolBorderWidthTF, 1, 3);        

            Label fillToolBorderType = new Label(objDictionaryAction.getWord("FILLTOOLBORDERTYPE")+" : ");
            fillToolBorderType.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFILLTOOLBORDERTYPE")));
            popup.add(fillToolBorderType, 0, 4);
            final ComboBox fillToolBorderTypeCB = new ComboBox();
            fillToolBorderTypeCB.getItems().addAll("All Inner Border","All Outer Border", "Left-Right Border", "Top-Bottom Border","Left Only Border", "Right Only Border","Top Only Border", "Bottom Only Border");
            fillToolBorderTypeCB.setValue(strFillBorder);
            fillToolBorderTypeCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFILLTOOLBORDERTYPE")));
            popup.add(fillToolBorderTypeCB, 1, 4);        

            Label fillToolColor = new Label(objDictionaryAction.getWord("FILLTOOLCOLOR")+" : ");
            fillToolColor.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFILLTOOLCOLOR")));
            popup.add(fillToolColor, 0, 5);
            /*
            final ColorPicker fillToolColorPicker = new ColorPicker();
            fillToolColorPicker.setStyle("-fx-color-label-visible: false ;");
            fillToolColorPicker.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFILLTOOLCOLOR")));
            popup.add(fillToolColorPicker, 1, 5); 
            */
            final ComboBox colorCB=new ComboBox();
            colorCB.setOnShown(new EventHandler() {
                @Override
                public void handle(Event t) {
                    try {
                        ColourSelector objColourSelector=new ColourSelector(objArtwork.getObjConfiguration());
                        if(objColourSelector.colorCode!=null&&objColourSelector.colorCode.length()>0){
                            colorCB.setStyle("-fx-background-color:#"+objColourSelector.colorCode+";");
                        }
                        colorCB.hide();
                        t.consume();
                    } catch (Exception ex) {
                        new Logging("SEVERE",ArtworkView.class.getName(),"colourPalettePopup: warp",ex);
                    }
                }
            });
            colorCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOLOR")));
            popup.add(colorCB, 1, 5);


            //fillToolTypeCB.setDisable(true);
            if(fillToolTypeCB.getValue().toString().equalsIgnoreCase("Color Fill")){
                //fillToolColorPicker.setDisable(false);
                colorCB.setDisable(false);
                fillToolBorderWidthTF.setDisable(true);
                fillToolBorderTypeCB.setDisable(true);
            }else{
                //fillToolColorPicker.setDisable(true);
                colorCB.setDisable(true);
                fillToolBorderWidthTF.setDisable(false);
                fillToolBorderTypeCB.setDisable(false);
            }
            fillToolTypeCB.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov, final String st, final String st1) {
                    if(st1.equalsIgnoreCase("Color Fill")){
                        //fillToolColorPicker.setDisable(false);
                        colorCB.setDisable(false);
                        fillToolBorderWidthTF.setDisable(true);
                        fillToolBorderTypeCB.setDisable(true);
                    }else{
                        //fillToolColorPicker.setDisable(true);
                        colorCB.setDisable(true);
                        fillToolBorderWidthTF.setDisable(false);
                        fillToolBorderTypeCB.setDisable(false);
                    }
                }
            });
            
            Button btnApply = new Button(objDictionaryAction.getWord("APPLY"));
            btnApply.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
            btnApply.setTooltip(new Tooltip(objDictionaryAction.getWord("ACTIONAPPLY")));
            btnApply.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    artwork.setCursor(Cursor.HAND);
                    setCurrentShape();
                    plotEditActionMode = 2;//filltool = 2 
                    strFillType = fillToolTypeCB.getValue().toString();
                    strFillArea = fillToolAreaCB.getValue().toString();
                    intFillBorder = Byte.parseByte(fillToolBorderWidthTF.getText());
                    strFillBorder = fillToolBorderTypeCB.getValue().toString();
                    if(fillToolBorderWidthTF.getText()!=""){
                        intFillBorder = Byte.parseByte(fillToolBorderWidthTF.getText());
                    }else {
                        intFillBorder = 0;
                    }
                    //artwork.setCursor(new ImageCursor(new Image(objConfiguration.getStrMouse()+"/fill_tool.png")));
                    //artwork.setCursor(new ImageCursor(new Image(objConfiguration.getStrMouse()+"/fill_tool.png"), 4, 4));
                    //artwork.setCursor(Cursor.cursor(objConfiguration.getStrMouse()+"/z_fill_tool.png"));
                    //System.err.println("size+"+ImageCursor.getBestSize(0, 0));
                    if(strFillType.equalsIgnoreCase("Weave Fill")){
                        objWeave= new Weave();
                        objWeave.setObjConfiguration(objArtwork.getObjConfiguration());
                        WeaveImportView objWeaveImportView= new WeaveImportView(objWeave);
                    } else if(strFillType.equalsIgnoreCase("Color Fill")){
                        String hex=colorCB.getStyle().substring(colorCB.getStyle().lastIndexOf("#")+1, colorCB.getStyle().lastIndexOf("#")+7);
                        editThreadValue = Integer.parseInt(hex.substring(0, 2), 16);//(int)(fillToolColorPicker.getValue().getRed()*255);
                        editThreadValue = (editThreadValue << 8) + Integer.parseInt(hex.substring(2, 4), 16);//(int)(fillToolColorPicker.getValue().getGreen()*255);
                        editThreadValue = (editThreadValue << 8) + Integer.parseInt(hex.substring(4, 6), 16);//(int)(fillToolColorPicker.getValue().getBlue()*255);
                        //System.out.println((int)(fillToolColorPicker.getValue().getRed()*255)+"="+editThreadValue+"="+(fillToolColorPicker.getValue().getRed()*255));
                        //System.out.println((int)(fillToolColorPicker.getValue().getGreen()*255)+"="+editThreadValue+"="+(fillToolColorPicker.getValue().getGreen()*255));
                        //System.out.println((int)(fillToolColorPicker.getValue().getBlue()*255)+"="+editThreadValue+"="+(fillToolColorPicker.getValue().getBlue()*255));
                    } else{
                        artwork.setCursor(Cursor.HAND);
                        lblStatus.setText(objDictionaryAction.getWord("WRONGINPUT"));
                    }
                    System.gc();
                    artworkPopupStage.close();
                }
            });
            popup.add(btnApply, 0, 6);

            Button btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
            btnCancel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
            btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
            btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {  
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONCANCEL"));
                    artworkPopupStage.close();
                }
            });
            popup.add(btnCancel, 1, 6);

            Separator sepHor2 = new Separator();
            sepHor2.setValignment(VPos.CENTER);
            GridPane.setConstraints(sepHor2, 0, 7);
            GridPane.setColumnSpan(sepHor2, 2);
            popup.getChildren().add(sepHor2);

            Label lblNote = new Label(objDictionaryAction.getWord("HELP"));
            lblNote.setWrapText(true);
            lblNote.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
            lblNote.setTooltip(new Tooltip(objDictionaryAction.getWord("HELPFILLTOOL")));
            //lblNote.setPrefWidth(objConfiguration.WIDTH*0.15);
            lblNote.setAlignment(Pos.CENTER);
            lblNote.setStyle("-fx-font: bold italic 11pt Arial; -fx-text-fill: #FF0000;");
            popup.add(lblNote, 0, 8, 2, 1); 
    
            Scene popupScene = new Scene(popup);
            popupScene.getStylesheets().add(ArtworkView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
            artworkPopupStage.setScene(popupScene);
            artworkPopupStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("WINDOWFILLTOOLARTWORK"));
            artworkPopupStage.showAndWait();
        }else{
            lblStatus.setText("Please addjust to composite view to perform this operation");
        }
    }
    private void artworkFillToolEvent(MouseEvent event){
        try {
            artwork.setCursor(Cursor.HAND);
            int evx=(int) (event.getX()/zoomfactor);
            int evy=(int) (event.getY()/zoomfactor);
            if(plotViewActionMode==2 || plotViewActionMode==3){
                String[] data = objArtwork.getObjConfiguration().getStrGraphSize().split("x");
                evx=(int)(event.getX()/(Integer.parseInt(data[1])*zoomfactor));
                evy=(int)(event.getY()/(Integer.parseInt(data[0])*zoomfactor));
            }
            int clickColor = bufferedImage.getRGB(evx, evy);
            byte[][] shapeMatrix = new byte[bufferedImage.getWidth()][bufferedImage.getHeight()];

            if(strFillType.equalsIgnoreCase("Weave Fill")){
                if(event.isAltDown() || event.isControlDown()){
                    Weave objWeaveNew = new Weave();
                    objWeaveNew.setObjConfiguration(objWeave.getObjConfiguration());
                    objWeaveNew.setStrWeaveID(objWeave.getStrWeaveID());  
                    WeaveEditView objWeaveEditView = new WeaveEditView(objWeaveNew);
                    if(objWeaveNew.getStrWeaveID()!=null){
                        objWeave.setBytWeaveThumbnil(objWeaveNew.getBytWeaveThumbnil());
                        objWeave.setStrWeaveID(objWeaveNew.getStrWeaveID());
                        objWeaveNew = null;
                        WeaveAction objWeaveAction = new WeaveAction();
                        objWeaveAction.getWeave(objWeave);  
                        objWeaveAction.extractWeaveContent(objWeave);
                    } else{
                        lblStatus.setText("weave edit: Your last action to assign weave pattern was not completed"+objWeave.getStrWeaveID());
                    }
                    System.gc();
                }
                if(objWeave.getStrWeaveID()!=null){
                    WeaveAction objWeaveAction = new WeaveAction();
                    objWeaveAction.extractWeaveContent(objWeave);
                    int h= objWeave.getDesignMatrix().length;
                    int w= objWeave.getDesignMatrix()[0].length;

                    BufferedImage editBufferedImage= new BufferedImage((int)(bufferedImage.getWidth()), (int)(bufferedImage.getHeight()),BufferedImage.TYPE_INT_RGB);
                    Graphics2D g = editBufferedImage.createGraphics();
                    g.drawImage(bufferedImage, 0, 0, (int)(bufferedImage.getWidth()), (int)(bufferedImage.getHeight()), null);
                
                    if(strFillArea.equalsIgnoreCase("Color Single Instance")){
                        objUR.doCommand("WeaveFillArea", bufferedImage);
                        //objURF.store(bufferedImage);
                        objArtworkAction= new ArtworkAction();
                        shapeMatrix=objArtworkAction.getArtworkBoundary(evx, evy, editBufferedImage);

                        editBufferedImage = new BufferedImage((int)(bufferedImage.getWidth()), (int)(bufferedImage.getHeight()),BufferedImage.TYPE_INT_RGB);
                        g = editBufferedImage.createGraphics();
                        g.drawImage(bufferedImage, 0, 0, (int)(bufferedImage.getWidth()), (int)(bufferedImage.getHeight()), null);
                        g.dispose();

                        // printing boundary matrix
                        for(int x=0; x<shapeMatrix.length; x++){
                            for(int y=0; y<shapeMatrix[0].length; y++){
                                if(shapeMatrix[x][y]==1){
                                    if(objWeave.getDesignMatrix()[x%w][y%h]==1)
                                        editBufferedImage.setRGB(x, y, -1);
                                    else
                                        editBufferedImage.setRGB(x, y, editBufferedImage.getRGB(x, y));
                                }
                            }
                        }
                    }else{
                        objUR.doCommand("WeaveFillColor", bufferedImage);
                        //objURF.store(bufferedImage);
                        // printing boundary matrix
                        for(int x=0; x<shapeMatrix.length; x++){
                            for(int y=0; y<shapeMatrix[0].length; y++){
                                if(clickColor==editBufferedImage.getRGB(x,y)){
                                    shapeMatrix[x][y]=1;
                                    if(objWeave.getDesignMatrix()[x%w][y%h]==1)
                                        editBufferedImage.setRGB(x, y, -1);
                                    else
                                        editBufferedImage.setRGB(x, y, editBufferedImage.getRGB(x, y));
                                }else{
                                    shapeMatrix[x][y]=0;
                                }
                            }
                        }
                    }
                    //overlapping boundary                    
                    if(strFillBorder.equalsIgnoreCase("Left-Right Border")){            
                        for(int i=0;i<intFillBorder;i++) { 
                            for(int y=0; y<shapeMatrix[0].length; y++){
                                boolean check = true;
                                for(int x=0; x<shapeMatrix.length; x++){
                                    if(shapeMatrix[x][y]==1){
                                        if(check){
                                            shapeMatrix[x][y]=-1;//boundry point
                                            check = false;
                                        }
                                    }else{
                                        check = true;
                                    }
                                }
                            }
                            for(int y=0; y<shapeMatrix[0].length; y++){
                                boolean check = true;
                                for(int x=shapeMatrix.length-1; x>=0; x--){
                                    if(shapeMatrix[x][y]==1){
                                        if(check){
                                            shapeMatrix[x][y]=-1;//boundry point
                                            check = false;
                                        }
                                    }else{
                                        check = true;
                                    }
                                }
                            }
                            for(int x=0; x<shapeMatrix.length; x++){
                                for(int y=0; y<shapeMatrix[0].length; y++){
                                    if(shapeMatrix[x][y]==-1){
                                        shapeMatrix[x][y]=0;
                                        editBufferedImage.setRGB(x, y, clickColor);
                                    }
                                }
                            }
                        }
                        /*int b=0;
                        for(int y=0; y<shapeMatrix[0].length; y++){
                            b=0;
                            for(int x=0; x<shapeMatrix.length; x++){
                                if(shapeMatrix[x][y]==1)
                                    b++;
                                else
                                    b=0;
                                if(b==1){
                                    for(int c=0; (c<intFillBorder)&&((x+c)<shapeMatrix.length); c++){
                                        if(shapeMatrix[x+c][y]==1)
                                            shapeMatrix[x+c][y]=-1;
                                        else
                                            break;
                                    }
                                }
                            }
                        }*/
                    } else if(strFillBorder.equalsIgnoreCase("Top-Bottom Border")){
                        for(int i=0;i<intFillBorder;i++) { 
                            for(int x=0; x<shapeMatrix.length; x++){
                                boolean check = true;
                                for(int y=0; y<shapeMatrix[0].length; y++){
                                    if(shapeMatrix[x][y]==1){
                                        if(check){
                                            shapeMatrix[x][y]=-1;//boundry point
                                            check = false;
                                        }
                                    }else{
                                        check = true;
                                    }
                                }                                
                            }
                            for(int x=0; x<shapeMatrix.length; x++){
                                boolean check = true;
                                for(int y=shapeMatrix[0].length-1; y>=0; y--){
                                    if(shapeMatrix[x][y]==1){
                                        if(check){
                                            shapeMatrix[x][y]=-1;//boundry point
                                            check = false;
                                        }
                                    }else{
                                        check = true;
                                    }
                                }
                            }
                            for(int x=0; x<shapeMatrix.length; x++){
                                for(int y=0; y<shapeMatrix[0].length; y++){
                                    if(shapeMatrix[x][y]==-1){
                                        shapeMatrix[x][y]=0;
                                        editBufferedImage.setRGB(x, y, clickColor);
                                    }
                                }
                            }
                        }
                    } else if(strFillBorder.equalsIgnoreCase("Left Only Border")){
                        for(int i=0;i<intFillBorder;i++) { 
                            for(int y=0; y<shapeMatrix[0].length; y++){
                                boolean check = true;
                                for(int x=0; x<shapeMatrix.length; x++){
                                    if(shapeMatrix[x][y]==1){
                                        if(check){
                                            shapeMatrix[x][y]=-1;//boundry point
                                            check = false;
                                        }
                                    }else{
                                        check = true;
                                    }
                                }
                            }
                            for(int x=0; x<shapeMatrix.length; x++){
                                for(int y=0; y<shapeMatrix[0].length; y++){
                                    if(shapeMatrix[x][y]==-1){
                                        shapeMatrix[x][y]=0;
                                        editBufferedImage.setRGB(x, y, clickColor);
                                    }
                                }
                            }
                        }
                    } else if(strFillBorder.equalsIgnoreCase("Right Only Border")){
                        for(int i=0;i<intFillBorder;i++) { 
                            for(int y=0; y<shapeMatrix[0].length; y++){
                                boolean check = true;
                                for(int x=shapeMatrix.length-1; x>=0; x--){
                                    if(shapeMatrix[x][y]==1){
                                        if(check){
                                            shapeMatrix[x][y]=-1;//boundry point
                                            check = false;
                                        }
                                    }else{
                                        check = true;
                                    }
                                }
                            }
                            for(int x=0; x<shapeMatrix.length; x++){
                                for(int y=0; y<shapeMatrix[0].length; y++){
                                    if(shapeMatrix[x][y]==-1){
                                        shapeMatrix[x][y]=0;
                                        editBufferedImage.setRGB(x, y, clickColor);
                                    }
                                }
                            }
                        }
                    }else if(strFillBorder.equalsIgnoreCase("Top Only Border")){
                        for(int i=0;i<intFillBorder;i++) { 
                            for(int x=0; x<shapeMatrix.length; x++){
                                boolean check = true;
                                for(int y=0; y<shapeMatrix[0].length; y++){
                                    if(shapeMatrix[x][y]==1){
                                        if(check){
                                            shapeMatrix[x][y]=-1;//boundry point
                                            check = false;
                                        }
                                    }else{
                                        check = true;
                                    }
                                }                                
                            }
                            for(int x=0; x<shapeMatrix.length; x++){
                                for(int y=0; y<shapeMatrix[0].length; y++){
                                    if(shapeMatrix[x][y]==-1){
                                        shapeMatrix[x][y]=0;
                                        editBufferedImage.setRGB(x, y, clickColor);
                                    }
                                }
                            }
                        }
                    } else if(strFillBorder.equalsIgnoreCase("Bottom Only Border")){
                        for(int i=0;i<intFillBorder;i++) { 
                            for(int x=0; x<shapeMatrix.length; x++){
                                boolean check = true;
                                for(int y=shapeMatrix[0].length-1; y>=0; y--){
                                    if(shapeMatrix[x][y]==1){
                                        if(check){
                                            shapeMatrix[x][y]=-1;//boundry point
                                            check = false;
                                        }
                                    }else{
                                        check = true;
                                    }
                                }
                            }
                            for(int x=0; x<shapeMatrix.length; x++){
                                for(int y=0; y<shapeMatrix[0].length; y++){
                                    if(shapeMatrix[x][y]==-1){
                                        shapeMatrix[x][y]=0;
                                        editBufferedImage.setRGB(x, y, clickColor);
                                    }
                                }
                            }
                        }
                    } else if(strFillBorder.equalsIgnoreCase("All Inner Border")){
                        for(int i=0;i<intFillBorder;i++) { 
                            /// [2] setting a border with bordercolour
                            for(int x=0; x<shapeMatrix.length; x++){
                                for(int y=0; y<shapeMatrix[0].length; y++){
                                    if(shapeMatrix[x][y]==1){
                                        if((x!=0)&&(y!=0)&&(x!=(int)(bufferedImage.getWidth()-1))&&(y!=(int)(bufferedImage.getHeight()-1))) {
                                            if(shapeMatrix[x][y-1]==0 || shapeMatrix[x][y+1]==0 || shapeMatrix[x-1][y]==0 || shapeMatrix[x+1][y]==0) {
                                                shapeMatrix[x][y]=-1;//boundry point
                                            }
                                        }
                                    }
                                }
                            }
                            for(int x=0; x<shapeMatrix.length; x++){
                                for(int y=0; y<shapeMatrix[0].length; y++){
                                    if(shapeMatrix[x][y]==-1){
                                        shapeMatrix[x][y]=0;
                                        editBufferedImage.setRGB(x, y, clickColor);
                                    }
                                }
                            }
                        }
                    } else{
                        for(int i=0;i<intFillBorder;i++) { 
                            for(int x=0; x<shapeMatrix.length; x++){
                                for(int y=0; y<shapeMatrix[0].length; y++){
                                    if(shapeMatrix[x][y]==1){
                                        if(x!=0 && y!=0) { //for (x-1.y-1)
                                            if(shapeMatrix[x-1][y-1]==0)
                                                shapeMatrix[x-1][y-1] = -1;
                                        }
                                        if(y!=0) { //for (x,y-1)
                                            if(shapeMatrix[x][y-1]==0)
                                                shapeMatrix[x-1][y-1] = -1;
                                        }
                                        if(x<(int)(bufferedImage.getWidth()-1) && y!=0) { //for (x+1,y-1)
                                            if(shapeMatrix[x+1][y-1]==0)
                                                shapeMatrix[x+1][y-1] = -1;
                                        }
                                        if(x!=0) { //for (x-1,y)
                                            if(shapeMatrix[x-1][y]==0)
                                                shapeMatrix[x-1][y] = -1;
                                        }
                                        if(x<(int)(bufferedImage.getWidth()-1)) { //for (x+1,y)
                                            if(shapeMatrix[x+1][y]==0)
                                                shapeMatrix[x+1][y] = -1;
                                        }
                                        if(x!=0 && y<(int)(bufferedImage.getHeight()-1)) { //for (x-1,y+1)
                                            if(shapeMatrix[x-1][y+1]==0)
                                                shapeMatrix[x-1][y+1] = -1;
                                        }
                                        if(y<(int)(bufferedImage.getHeight()-1)) { //for (x,y+1)
                                            if(shapeMatrix[x][y+1]==0)
                                                shapeMatrix[x][y+1] = -1;
                                        }
                                        if(x<(int)(bufferedImage.getWidth()-1) && y<(int)(bufferedImage.getHeight()-1)) { //for (x+1,y+1)
                                            if(shapeMatrix[x+1][y+1]==0)
                                                shapeMatrix[x+1][y+1] = -1;
                                        }
                                    }
                                }
                            }
                            for(int x=0; x<shapeMatrix.length; x++){
                                for(int y=0; y<shapeMatrix[0].length; y++){
                                    if(shapeMatrix[x][y]==-1){
                                        shapeMatrix[x][y]=1;
                                        editBufferedImage.setRGB(x, y, clickColor);
                                    }
                                }
                            }
                        }
                    }

                    bufferedImage = editBufferedImage;
                    shapeMatrix = null;
                    editBufferedImage = null;
                    System.gc();
                    
                    //initArtworkValue();
                    plotCompositeView();
                }else{
                    lblStatus.setText("Your last action to assign weave pattern was not completed");
                }
                System.gc();
            } else {
                System.err.println("color+"+editThreadValue);
                lblStatus.setText("color fill"+editThreadValue);
                BufferedImage editBufferedImage= new BufferedImage((int)(bufferedImage.getWidth()), (int)(bufferedImage.getHeight()),BufferedImage.TYPE_INT_RGB);
                Graphics2D g = editBufferedImage.createGraphics();
                g.drawImage(bufferedImage, 0, 0, (int)(bufferedImage.getWidth()), (int)(bufferedImage.getHeight()), null);

                if(strFillArea.equalsIgnoreCase("Color Single Instance")){
                    objUR.doCommand("WeaveFillArea", bufferedImage);
                    //objURF.store(bufferedImage);
                    lblStatus.setText("WeaveFillArea"+editThreadValue);

                    objArtworkAction= new ArtworkAction();
                    shapeMatrix=objArtworkAction.getArtworkBoundary(evx, evy, editBufferedImage);

                    editBufferedImage = new BufferedImage((int)(bufferedImage.getWidth()), (int)(bufferedImage.getHeight()),BufferedImage.TYPE_INT_RGB);
                    g = editBufferedImage.createGraphics();
                    g.drawImage(bufferedImage, 0, 0, (int)(bufferedImage.getWidth()), (int)(bufferedImage.getHeight()), null);
                    g.dispose();

                    // printing boundary matrix
                    for(int x=0; x<shapeMatrix.length; x++){
                        for(int y=0; y<shapeMatrix[0].length; y++){
                            if(shapeMatrix[x][y]==1){
                                editBufferedImage.setRGB(x, y, editThreadValue);
                            }
                        }
                    }
                }else{
                    objUR.doCommand("WeaveFillColor", bufferedImage);
                    //objURF.store(bufferedImage);
                    lblStatus.setText("WeaveFillColor"+editThreadValue);
                    // printing boundary matrix
                    for(int x=0; x<shapeMatrix.length; x++){
                        for(int y=0; y<shapeMatrix[0].length; y++){
                            if(clickColor==editBufferedImage.getRGB(x,y)){
                                shapeMatrix[x][y]=1;
                                editBufferedImage.setRGB(x, y, editThreadValue);
                            }else{
                                shapeMatrix[x][y]=0;
                            }
                            //System.out.print(y);
                        }
                        //System.out.println(x);
                    }
                }
                bufferedImage = editBufferedImage;
                shapeMatrix = null;
                editBufferedImage = null;
                System.gc();
                initArtworkValue();
                plotEditActionMode = 2;//filltool = 2 
            }
        } catch (SQLException ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"SQLException:artworkfilltool() : Error while viewing grid view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }   
    }
    /**
     * artworkColorProperties
     * <p>
     * This method is used for creating GUI of artwork reduce color pane.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating GUI of artwork reduce color pane.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void artworkColorProperties(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONCOLORADJUST"));
        final Stage artworkPopupStage = new Stage();
        artworkPopupStage.initOwner(artworkStage);
        artworkPopupStage.initStyle(StageStyle.UTILITY);
        //artworkPopupStage.initModality(Modality.WINDOW_MODAL);
        GridPane popup=new GridPane();
        popup.setId("popup");
        popup.setHgap(10);
        popup.setVgap(10);
        
        final ColorAdjust colorAdjust = new ColorAdjust();
        
        Label lblColorBrightness= new Label(objDictionaryAction.getWord("BRIGHTNESS")+" :");
        //lblColorBrightness.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblColorBrightness.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBRIGHTNESS")));
        final Slider colorBrightnessSlider = new Slider(-1.0, 1.0, 0.0);    
        colorBrightnessSlider.setShowTickLabels(true);
        colorBrightnessSlider.setShowTickMarks(true);
        colorBrightnessSlider.setBlockIncrement(0.1);
        colorBrightnessSlider.setMajorTickUnit(0.5);
        //colorBrightnessSlider.setMinorTickCount(0.5);
        final Label colorBrightnessTF = new Label();
        colorBrightnessTF.setText(Double.toString(colorBrightnessSlider.getValue()));
        colorBrightnessTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBRIGHTNESS")));
        popup.add(lblColorBrightness, 0, 0);
        popup.add(colorBrightnessSlider, 1, 0);    
        popup.add(colorBrightnessTF, 2, 0);   
        colorBrightnessSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                colorBrightnessTF.setText(String.format("%.2f", new_val));
                colorAdjust.setBrightness(new_val.doubleValue());
            }
        });
        
        Label lblColorContrast= new Label(objDictionaryAction.getWord("CONTRAST")+" :");
        //lblColorContrast.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblColorContrast.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCONTRAST")));
        final Slider colorContrastSlider = new Slider(-1.0, 1.0, 0.0);    
        colorContrastSlider.setShowTickLabels(true);
        colorContrastSlider.setShowTickMarks(true);
        colorContrastSlider.setBlockIncrement(0.1);
        colorContrastSlider.setMajorTickUnit(0.5);
        //colorContrastSlider.setMinorTickCount(0.1);
        final Label colorContrastTF = new Label();
        colorContrastTF.setText(Double.toString(colorContrastSlider.getValue()));
        colorContrastTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCONTRAST")));
        popup.add(lblColorContrast, 0, 1);
        popup.add(colorContrastSlider, 1, 1);    
        popup.add(colorContrastTF, 2, 1);   
        colorContrastSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                colorContrastTF.setText(String.format("%.2f", new_val));
                colorAdjust.setContrast(new_val.doubleValue());
            }
        });
        
        Label lblColorHue= new Label(objDictionaryAction.getWord("HUE")+" :");
        //lblColorHue.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblColorHue.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPHUE")));
        final Slider colorHueSlider = new Slider(-1.0, 1.0, 0.0);    
        colorHueSlider.setShowTickLabels(true);
        colorHueSlider.setShowTickMarks(true);
        colorHueSlider.setBlockIncrement(0.1);
        colorHueSlider.setMajorTickUnit(0.5);
        //colorHueSlider.setMinorTickCount(0.1);
        final Label colorHueTF = new Label();
        colorHueTF.setText(Double.toString(colorHueSlider.getValue()));
        colorHueTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPHUE")));
        popup.add(lblColorHue, 0, 2);
        popup.add(colorHueSlider, 1, 2);    
        popup.add(colorHueTF, 2, 2);   
        colorHueSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                colorHueTF.setText(String.format("%.2f", new_val));
                colorAdjust.setHue(new_val.doubleValue());
            }
        });
        
        Label lblColorSaturation= new Label(objDictionaryAction.getWord("SATURATION")+" :");
        //lblColorSaturation.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblColorSaturation.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSATURATION")));
        final Slider colorSaturationSlider = new Slider(-1.0, 1.0, 0.0);    
        colorSaturationSlider.setShowTickLabels(true);
        colorSaturationSlider.setShowTickMarks(true);
        colorSaturationSlider.setBlockIncrement(0.1);
        colorSaturationSlider.setMajorTickUnit(0.5);
        //colorSaturationSlider.setMinorTickCount(0.1);
        final Label colorSaturationTF = new Label();
        colorSaturationTF.setText(Double.toString(colorSaturationSlider.getValue()));
        colorSaturationTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSATURATION")));
        popup.add(lblColorSaturation, 0, 3);
        popup.add(colorSaturationSlider, 1, 3);    
        popup.add(colorSaturationTF, 2, 3);   
        colorSaturationSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                colorSaturationTF.setText(String.format("%.2f", new_val));
                colorAdjust.setSaturation(new_val.doubleValue());
            }
        });
    
        Button btnApply = new Button(objDictionaryAction.getWord("APPLY"));
        btnApply.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
        btnApply.setTooltip(new Tooltip(objDictionaryAction.getWord("ACTIONAPPLY")));
        btnApply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    objUR.doCommand("Apply", bufferedImage);
                    //objURF.store(bufferedImage);
                    // convert the input buffered image to a JavaFX image and load it into a JavaFX ImageView.
                    ImageView imageView = new ImageView(SwingFXUtils.toFXImage(bufferedImage, null));
                    // apply the color adjustment.
                    imageView.setEffect(colorAdjust);
                    // snapshot the color adjusted JavaFX image, convert it back to a Swing buffered image and return it.
                    SnapshotParameters snapshotParameters = new SnapshotParameters();
                    snapshotParameters.setFill(Color.TRANSPARENT);
                    bufferedImage=SwingFXUtils.fromFXImage(imageView.snapshot(snapshotParameters,null),null);
                    
                    initArtworkValue();
                    lblStatus.setText(objDictionaryAction.getWord("SUCCESS"));
                } catch (Exception ex) {               
                    new Logging("SEVERE",ArtworkView.class.getName(),"Operation apply",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }  
                System.gc();
                artworkPopupStage.close();
            }
        });
        popup.add(btnApply, 0, 4);

        Button btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
        btnCancel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
        btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {  
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCANCEL"));
                artworkPopupStage.close();
            }
        });
        popup.add(btnCancel, 1, 4);

        Separator sepHor1 = new Separator();
        sepHor1.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor1, 0, 5);
        GridPane.setColumnSpan(sepHor1, 2);
        popup.getChildren().add(sepHor1);
        
        Label lblNote = new Label(objDictionaryAction.getWord("HELP"));
        lblNote.setWrapText(true);
        lblNote.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        lblNote.setTooltip(new Tooltip(objDictionaryAction.getWord("HELPCOLORADJUST")));
        //lblNote.setPrefWidth(objConfiguration.WIDTH*0.15);
        lblNote.setAlignment(Pos.CENTER);
        lblNote.setStyle("-fx-font: bold italic 11pt Arial; -fx-text-fill: #FF0000;");
        popup.add(lblNote, 0, 6, 2, 1); 
        
        Scene popupScene = new Scene(popup);
        popupScene.getStylesheets().add(ArtworkView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        artworkPopupStage.setScene(popupScene);
        artworkPopupStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("COLORADJUST"));
        artworkPopupStage.showAndWait();
    }
    /**
     * artworkColorReduction
     * <p>
     * This method is used for creating GUI of artwork reduce color pane.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating GUI of artwork reduce color pane.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void artworkColorReduction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONREDUCECOLOR"));
        final Stage artworkPopupStage = new Stage();
        artworkPopupStage.initOwner(artworkStage);
        artworkPopupStage.initStyle(StageStyle.UTILITY);
        //dialogStage.initModality(Modality.WINDOW_MODAL);
        GridPane popup=new GridPane();
        popup.setId("popup");
        popup.setHgap(10);
        popup.setVgap(10);
    
        final ToggleGroup colorTG = new ToggleGroup();
        RadioButton byDragRB = new RadioButton(objDictionaryAction.getWord("Reduce using Drag & Drop"));
        byDragRB.setToggleGroup(colorTG);
        byDragRB.setUserData("dragdrop");
        popup.add(byDragRB, 0, 0, 2, 1);
        colorTG.selectToggle(byDragRB);
        
        final GridPane colorGP = new GridPane();
        colorGP.setAlignment(Pos.CENTER);
        colorGP.setHgap(10);
        colorGP.setVgap(10);
        colorGP.setCursor(Cursor.HAND);
        popup.add(colorGP, 0, 1, 2, 1);
        for(int i=0; i<intColor; i++){
            String webColor = String.format("#%02X%02X%02X",colors.get(i).getRed(),colors.get(i).getGreen(),colors.get(i).getBlue());
            final Label  lblColor  = new Label ();
            lblColor.setPrefSize(33, 33);
            lblColor.setStyle("-fx-background-color:rgb("+colors.get(i).getRed()+","+colors.get(i).getGreen()+","+colors.get(i).getBlue()+"); -fx-border-color: #FFFFFF;");
            lblColor.setTooltip(new Tooltip("Color:"+i+"\n"+webColor+"\nR:"+colors.get(i).getRed()+"\nG:"+colors.get(i).getGreen()+"\nB:"+colors.get(i).getBlue()));
            lblColor.setUserData(i);
            lblColor.setOnDragDetected(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    /* drag was detected, start a drag-and-drop gesture*/
                    /* allow any transfer mode */
                    Dragboard db = lblColor.startDragAndDrop(TransferMode.ANY);
                    /* Put a string on a dragboard */
                    ClipboardContent content = new ClipboardContent();
                    int index = Integer.parseInt(lblColor.getUserData().toString());
                    String webColor = String.format("#%02X%02X%02X",colors.get(index).getRed(),colors.get(index).getGreen(),colors.get(index).getBlue());
                    content.putString(webColor);
                    db.setContent(content);
                    event.consume();
                }
            });
            lblColor.setOnDragOver(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    /* data is dragged over the target */
                    /* accept it only if it is not dragged from the same node 
                     * and if it has a string data */
                    if (event.getGestureSource() != lblColor && event.getDragboard().hasString()) {
                        /* allow for both copying and moving, whatever user chooses */
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
                    event.consume();
                }
            });
            lblColor.setOnDragEntered(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */
                /* show to the user that it is an actual gesture target */
                     if (event.getGestureSource() != lblColor && event.getDragboard().hasString()) {
                         lblColor.setTextFill(Color.GREEN);
                     }
                     event.consume();
                }
            });
            lblColor.setOnDragExited(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    /* mouse moved away, remove the graphical cues */
                    lblColor.setTextFill(Color.BLACK);
                    event.consume();
                }
            });
            lblColor.setOnDragDropped(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    /* data dropped */
                    /* if there is a string data on dragboard, read it and use it */
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    if (db.hasString()) {                    
                       lblColor.setStyle("-fx-background-color: "+db.getString()+";");
                       //lblColor.
                       int index = Integer.parseInt(lblColor.getUserData().toString());
                       colors.set(index, new java.awt.Color((float)Color.web(db.getString()).getRed(),(float)Color.web(db.getString()).getGreen(),(float)Color.web(db.getString()).getBlue()));
                       success = true;                   
                    }
                    /* let the source know whether the string was successfully 
                     * transferred and used */
                    event.setDropCompleted(success);
                    event.consume();
                 }
            });
            lblColor.setOnDragDone(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    /* the drag and drop gesture ended */
                    /* if the data was successfully moved, clear it */
                    if (event.getTransferMode() == TransferMode.MOVE) {                    
                    }
                    event.consume();
                }
            });
            colorGP.add(lblColor, i%5, i/5);
        }
        
        RadioButton byNumberRB = new RadioButton(objDictionaryAction.getWord("Reduce using number of colors"));
        byNumberRB.setToggleGroup(colorTG);
        byNumberRB.setUserData("number");
        popup.add(byNumberRB, 0, 2, 2, 1);
        
        final TextField txtColor = new TextField(Integer.toString(colors.size())){
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
        txtColor.setPromptText(objDictionaryAction.getWord("COLORLIMIT"));
        txtColor.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOLORLIMIT")));
        popup.add(txtColor, 0, 3, 2, 1);
        
        RadioButton byGrayRB = new RadioButton(objDictionaryAction.getWord("Convert to Gray Scale"));
        byGrayRB.setToggleGroup(colorTG);
        byGrayRB.setUserData("grayscale");
        popup.add(byGrayRB, 0, 4, 2, 1);
        
        RadioButton byBlackWhiteRB = new RadioButton(objDictionaryAction.getWord("Convert to Balck & White"));
        byBlackWhiteRB.setToggleGroup(colorTG);
        byBlackWhiteRB.setUserData("blackwhite");
        popup.add(byBlackWhiteRB, 0, 5, 2, 1);
        
        colorTG.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                if (colorTG.getSelectedToggle() != null) {
                    if(colorTG.getSelectedToggle().getUserData().toString().equalsIgnoreCase("number")){
                        txtColor.setDisable(false);
                        colorGP.setDisable(true);
                    } else if(colorTG.getSelectedToggle().getUserData().toString().equalsIgnoreCase("dragdrop")){
                        colorGP.setDisable(false);
                        txtColor.setDisable(true);
                    } else {
                        txtColor.setDisable(true);
                        colorGP.setDisable(true);
                    }
                }
            }
        });
        
        Button btnApply = new Button(objDictionaryAction.getWord("APPLY"));
        btnApply.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
        btnApply.setTooltip(new Tooltip(objDictionaryAction.getWord("ACTIONAPPLY")));
        btnApply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    objUR.doCommand("Apply", bufferedImage);
                    //objURF.store(bufferedImage);
                    objArtworkAction = new ArtworkAction(false);
                    if(colorTG.getSelectedToggle().getUserData().toString().equalsIgnoreCase("dragdrop")){
                        int [][] artworkMatrix = objArtworkAction.getImageToMatrix(bufferedImage);   
                        bufferedImage = objArtworkAction.getImageFromMatrix(artworkMatrix, colors);
                        artworkMatrix = null;
                    } else if(colorTG.getSelectedToggle().getUserData().toString().equalsIgnoreCase("grayscale")){
                        bufferedImage = objArtworkAction.getImageGray(bufferedImage);
                    } else if(colorTG.getSelectedToggle().getUserData().toString().equalsIgnoreCase("blackwhite")){
                        bufferedImage = objArtworkAction.getImageBlackWhite(bufferedImage);
                    } else if(colorTG.getSelectedToggle().getUserData().toString().equalsIgnoreCase("number")){
                        if(txtColor.getText().trim().equalsIgnoreCase(""))
                            if(Integer.parseInt(txtColor.getText())<1)
                                txtColor.setText("1");
                        bufferedImage = objArtworkAction.reduceColors(bufferedImage,Integer.parseInt(txtColor.getText()));
                    }
                    initArtworkValue();
                    lblStatus.setText(objDictionaryAction.getWord("SUCCESS"));
                } catch (SQLException ex) {               
                    new Logging("SEVERE",ArtworkView.class.getName(),"Operation apply",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (IOException ex) {
                    new Logging("SEVERE",ArtworkView.class.getName(),"IOException: operation failed",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }  
                System.gc();
                artworkPopupStage.close();
            }
        });
        popup.add(btnApply, 0, 6);

        Button btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
        btnCancel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
        btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {  
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCANCEL"));
                artworkPopupStage.close();
            }
        });
        popup.add(btnCancel, 1, 6);

        Separator sepHor1 = new Separator();
        sepHor1.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor1, 0, 7);
        GridPane.setColumnSpan(sepHor1, 2);
        popup.getChildren().add(sepHor1);
        
        Label lblNote = new Label(objDictionaryAction.getWord("HELP"));
        lblNote.setWrapText(true);
        lblNote.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        lblNote.setTooltip(new Tooltip(objDictionaryAction.getWord("HELPCOLORREDUCE")+objConfiguration.getIntColorLimit()));
        //lblNote.setPrefWidth(objConfiguration.WIDTH*0.15);
        lblNote.setAlignment(Pos.CENTER);
        lblNote.setStyle("-fx-font: bold italic 11pt Arial; -fx-text-fill: #FF0000;");
        popup.add(lblNote, 0, 8, 2, 1); 
        
        Scene popupScene = new Scene(popup);
        popupScene.getStylesheets().add(ArtworkView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        artworkPopupStage.setScene(popupScene);
        artworkPopupStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("WINDOWREDUCECOLORARTWORK"));
        artworkPopupStage.showAndWait();        
    }
    /**
     * artworkSketch
     * <p>
     * This method is used for creating GUI of artwork reduce color pane.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating GUI of artwork reduce color pane.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void artworkSketch() {    
        lblStatus.setText(objDictionaryAction.getWord("ACTIONARTWORKSKETCH"));
        try {
            objUR.doCommand("sketch", bufferedImage);
            //objURF.store(bufferedImage);
            objArtworkAction = new ArtworkAction(false);
            bufferedImage=objArtworkAction.getImageColorBorderSketch(bufferedImage);
            /*try {
                mageIO.write(artworkBorder, "png", new File(System.getProperty("user.dir")+"/mla/test.png"));
            } catch (IOException ex) {
                Logger.getLogger(ArtworkEditView.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            initArtworkValue();
        } catch (SQLException ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"Operation sketch",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    /**
     * artworkVerticalMirror
     * <p>
     * This method is used for creating GUI of artwork reduce color pane.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating GUI of artwork reduce color pane.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void artworkVerticalMirror() {    
        lblStatus.setText(objDictionaryAction.getWord("ACTIONHORIZENTALMIRROR"));
        try {
            objUR.doCommand("VMirror", bufferedImage);
            //objURF.store(bufferedImage);
            objArtworkAction = new ArtworkAction();
            bufferedImage = objArtworkAction.getImageVerticalMirror(bufferedImage);
            initArtworkValue();
        } catch (SQLException ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"Operation Black and white",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }  catch(OutOfMemoryError ex){
            undoArtwork();
        }
    }
    /**
     * artworkHorizentalMirror
     * <p>
     * This method is used for creating GUI of artwork reduce color pane.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating GUI of artwork reduce color pane.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void artworkHorizentalMirror() {    
        lblStatus.setText(objDictionaryAction.getWord("ACTIONVERTICALMIRROR"));
        try {
            objUR.doCommand("HMirror", bufferedImage);
            //objURF.store(bufferedImage);
            objArtworkAction = new ArtworkAction();
            bufferedImage = objArtworkAction.getImageHorizontalMirror(bufferedImage);
            initArtworkValue();
        } catch (IOException ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"Operation Black and white",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (SQLException ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"Operation Black and white",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch(OutOfMemoryError ex){
            undoArtwork();
        }
    }
    /**
     * artworkClockRotation
     * <p>
     * This method is used for creating GUI of artwork reduce color pane.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating GUI of artwork reduce color pane.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void artworkClockRotation() {    
        lblStatus.setText(objDictionaryAction.getWord("ACTIONCLOCKROTATION"));
        try {
            objUR.doCommand("CRotate", bufferedImage);
            //objURF.store(bufferedImage);
            objArtworkAction = new ArtworkAction();
            bufferedImage = objArtworkAction.getImageRotation(bufferedImage,"CLOCKWISE");
            initArtworkValue();
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"Operation clockwise rotation",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    /**
     * artworkAntiClockRotation
     * <p>
     * This method is used for creating GUI of artwork reduce color pane.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating GUI of artwork reduce color pane.
     * @see         javafx.stage.*;
     * @link        com.mla.artwork.Artwork
     */
    private void artworkAntiClockRotation() {    
        lblStatus.setText(objDictionaryAction.getWord("ACTIONANTICLOCKROTATION"));
        try {
            objUR.doCommand("Counter Clockwise Rotation", bufferedImage);
            //objURF.store(bufferedImage);
            objArtworkAction = new ArtworkAction();
            bufferedImage = objArtworkAction.getImageRotation(bufferedImage,"COUNTERCLOCKWISE");
            initArtworkValue();
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"Operation counter clockwise rotation",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    /**
 * populateEquipmentToolbar
 * <p>
 * Function use for editing tool bar for menu item Edit,
 * and binding events for each tools. 
 * 
 * @exception   (@throws SQLException)
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         javafx.event.*;
 * @link        WeaveView
 */
  private void populateEquipmentToolbar(){
        // artwork resize edit item
        VBox pencilEditVB = new VBox(); 
        Label pencilEditLbl= new Label(objDictionaryAction.getWord("PENCIL"));
        pencilEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPENCIL")));
        pencilEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/pencil.png"), pencilEditLbl);
        pencilEditVB.setPrefWidth(80);
        pencilEditVB.getStyleClass().addAll("VBox");
        // repaet
        VBox eraserEditVB = new VBox(); 
        Label eraserEditLbl= new Label(objDictionaryAction.getWord("ERASER"));
        eraserEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPERASER")));
        eraserEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/eraser.png"), eraserEditLbl);
        eraserEditVB.setPrefWidth(80);
        eraserEditVB.getStyleClass().addAll("VBox");
        // artwork resize edit item
        VBox lineEditVB = new VBox(); 
        Label lineEditLbl= new Label(objDictionaryAction.getWord("LINE"));
        lineEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPLINE")));
        lineEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/line.png"), lineEditLbl);
        lineEditVB.setPrefWidth(80);
        lineEditVB.getStyleClass().addAll("VBox");
        // Vertical mirror edit item
        VBox arcEditVB = new VBox(); 
        Label arcEditLbl= new Label(objDictionaryAction.getWord("ARC"));
        arcEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPARC")));
        arcEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/oval.png"), arcEditLbl);
        arcEditVB.setPrefWidth(80);
        arcEditVB.getStyleClass().addAll("VBox");   
        // horizontal mirros edit item
        VBox bezierCurveEditVB = new VBox(); 
        Label bezierCurveEditLbl= new Label(objDictionaryAction.getWord("BEZIERCURVE"));
        bezierCurveEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBEZIERCURVE")));
        bezierCurveEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/oval.png"), bezierCurveEditLbl);
        bezierCurveEditVB.setPrefWidth(80);
        bezierCurveEditVB.getStyleClass().addAll("VBox");   
        // clockwise rotation edit item
        VBox ovalEditVB = new VBox(); 
        Label ovalEditLbl= new Label(objDictionaryAction.getWord("OVAL"));
        ovalEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOVAL")));
        ovalEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/oval.png"), ovalEditLbl);
        ovalEditVB.setPrefWidth(80);
        ovalEditVB.getStyleClass().addAll("VBox");   
        // Anti clock wise rotation edit item
        VBox rectangleEditVB = new VBox(); 
        Label rectangleEditLbl= new Label(objDictionaryAction.getWord("RECTANGLE"));
        rectangleEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPRECTANGLE")));
        rectangleEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/rectangle.png"), rectangleEditLbl);
        rectangleEditVB.setPrefWidth(80);
        rectangleEditVB.getStyleClass().addAll("VBox"); 
        // move Right
        VBox triangleEditVB = new VBox(); 
        Label triangleEditLbl= new Label(objDictionaryAction.getWord("TRIANGLE"));
        triangleEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTRIANGLE")));
        triangleEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/triangle.png"), triangleEditLbl);
        triangleEditVB.setPrefWidth(80);
        triangleEditVB.getStyleClass().addAll("VBox");    
        // move Left
        VBox heartEditVB = new VBox(); 
        Label heartEditLbl= new Label(objDictionaryAction.getWord("HEART"));
        heartEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPHEART")));
        heartEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/heart.png"), heartEditLbl);
        heartEditVB.setPrefWidth(80);
        heartEditVB.getStyleClass().addAll("VBox");    
        // move Up
        VBox diamondEditVB = new VBox(); 
        Label diamondEditLbl= new Label(objDictionaryAction.getWord("DIAMOND"));
        diamondEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDIAMOND")));
        diamondEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/diamond.png"), diamondEditLbl);
        diamondEditVB.setPrefWidth(80);
        diamondEditVB.getStyleClass().addAll("VBox");    
        // move Down
        VBox pentagonEditVB = new VBox(); 
        Label pentagonEditLbl= new Label(objDictionaryAction.getWord("PENTAGONE"));
        pentagonEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPENTAGONE")));
        pentagonEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/pentagon.png"), pentagonEditLbl);
        pentagonEditVB.setPrefWidth(80);
        pentagonEditVB.getStyleClass().addAll("VBox");
        // move Right 8
        VBox hexagonEditVB = new VBox(); 
        Label hexagonEditLbl= new Label(objDictionaryAction.getWord("HEXAGONE"));
        hexagonEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPHEXAGONE")));
        hexagonEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/hexagon.png"), hexagonEditLbl);
        hexagonEditVB.setPrefWidth(80);
        hexagonEditVB.getStyleClass().addAll("VBox");    
        // move Left 8
        VBox fourPointStarEditVB = new VBox(); 
        Label fourPointStarEditLbl= new Label(objDictionaryAction.getWord("FOURPOINTSTAR"));
        fourPointStarEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFOURPOINTSTAR")));
        fourPointStarEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/four_star.png"), fourPointStarEditLbl);
        fourPointStarEditVB.setPrefWidth(80);
        fourPointStarEditVB.getStyleClass().addAll("VBox");    
        // move Up 8
        VBox fivePointStarEditVB = new VBox(); 
        Label fivePointStarEditLbl= new Label(objDictionaryAction.getWord("FIVEPOINTSTAR"));
        fivePointStarEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFIVEPOINTSTAR")));
        fivePointStarEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/five_star.png"), fivePointStarEditLbl);
        fivePointStarEditVB.setPrefWidth(80);
        fivePointStarEditVB.getStyleClass().addAll("VBox");    
        // move Down 8
        VBox sixPointStarEditVB = new VBox(); 
        Label sixPointStarEditLbl= new Label(objDictionaryAction.getWord("SIXPOINTSTAR"));
        sixPointStarEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSIXPOINTSTAR")));
        sixPointStarEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/six_star.png"), sixPointStarEditLbl);
        sixPointStarEditVB.setPrefWidth(80);
        sixPointStarEditVB.getStyleClass().addAll("VBox");
        // Tilt Right
        VBox ploygonEditVB = new VBox(); 
        Label ploygonEditLbl= new Label(objDictionaryAction.getWord("POLYGON"));
        ploygonEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPOLYGON")));
        ploygonEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/rhombus.png"), ploygonEditLbl);
        ploygonEditVB.setPrefWidth(80);
        ploygonEditVB.getStyleClass().addAll("VBox");    
        //add enable disable condition
        if(!isWorkingMode){
            pencilEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/pencil.png"));
            pencilEditVB.setDisable(true);
            pencilEditVB.setCursor(Cursor.WAIT);
            eraserEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/eraser.png"));
            eraserEditVB.setDisable(true);
            eraserEditVB.setCursor(Cursor.WAIT);
            lineEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/line.png"));
            lineEditVB.setDisable(true);
            lineEditVB.setCursor(Cursor.WAIT);
            arcEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/oval.png"));
            arcEditVB.setDisable(true);
            arcEditVB.setCursor(Cursor.WAIT);
            bezierCurveEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/oval.png"));
            bezierCurveEditVB.setDisable(true);
            bezierCurveEditVB.setCursor(Cursor.WAIT);
            ovalEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/oval.png"));
            ovalEditVB.setDisable(true);
            ovalEditVB.setCursor(Cursor.WAIT);
            rectangleEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/rectangle.png"));
            rectangleEditVB.setDisable(true);
            rectangleEditVB.setCursor(Cursor.WAIT);
            triangleEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/triangle.png"));
            triangleEditVB.setDisable(true);
            triangleEditVB.setCursor(Cursor.WAIT);
            heartEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/heart.png"));
            heartEditVB.setDisable(true);
            heartEditVB.setCursor(Cursor.WAIT);
            diamondEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/diamond.png"));
            diamondEditVB.setDisable(true);
            diamondEditVB.setCursor(Cursor.WAIT);
            pentagonEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/pentagon.png"));
            pentagonEditVB.setDisable(true);
            pentagonEditVB.setCursor(Cursor.WAIT);
            hexagonEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/hexagon.png"));
            hexagonEditVB.setDisable(true);
            hexagonEditVB.setCursor(Cursor.WAIT);
            fourPointStarEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/four_star.png"));
            fourPointStarEditVB.setDisable(true);
            fourPointStarEditVB.setCursor(Cursor.WAIT);
            fivePointStarEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/five_star.png"));
            fivePointStarEditVB.setDisable(true);
            fivePointStarEditVB.setCursor(Cursor.WAIT);
            sixPointStarEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/six_star.png"));
            sixPointStarEditVB.setDisable(true);
            sixPointStarEditVB.setCursor(Cursor.WAIT);
            ploygonEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/rhombus.png"));
            ploygonEditVB.setDisable(true);
            ploygonEditVB.setCursor(Cursor.WAIT);
        }else{
            pencilEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/pencil.png"));
            pencilEditVB.setDisable(false);
            pencilEditVB.setCursor(Cursor.HAND);
            eraserEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/eraser.png"));
            eraserEditVB.setDisable(false);
            eraserEditVB.setCursor(Cursor.HAND);
            lineEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/line.png"));
            lineEditVB.setDisable(false);
            lineEditVB.setCursor(Cursor.HAND);
            arcEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/oval.png"));
            arcEditVB.setDisable(false);
            arcEditVB.setCursor(Cursor.HAND);
            bezierCurveEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/oval.png"));
            bezierCurveEditVB.setDisable(false);
            bezierCurveEditVB.setCursor(Cursor.HAND);
            ovalEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/oval.png"));
            ovalEditVB.setDisable(false);
            ovalEditVB.setCursor(Cursor.HAND);
            rectangleEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/rectangle.png"));
            rectangleEditVB.setDisable(false);
            rectangleEditVB.setCursor(Cursor.HAND);
            triangleEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/triangle.png"));
            triangleEditVB.setDisable(false);
            triangleEditVB.setCursor(Cursor.HAND);
            heartEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/heart.png"));
            heartEditVB.setDisable(false);
            heartEditVB.setCursor(Cursor.HAND);
            diamondEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/diamond.png"));
            diamondEditVB.setDisable(false);
            diamondEditVB.setCursor(Cursor.HAND);
            pentagonEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/pentagon.png"));
            pentagonEditVB.setDisable(false);
            pentagonEditVB.setCursor(Cursor.HAND);
            hexagonEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/hexagon.png"));
            hexagonEditVB.setDisable(false);
            hexagonEditVB.setCursor(Cursor.HAND);
            fourPointStarEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/four_star.png"));
            fourPointStarEditVB.setDisable(false);
            fourPointStarEditVB.setCursor(Cursor.HAND);
            fivePointStarEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/five_star.png"));
            fivePointStarEditVB.setDisable(false);
            fivePointStarEditVB.setCursor(Cursor.HAND);
            sixPointStarEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/six_star.png"));
            sixPointStarEditVB.setDisable(false);
            sixPointStarEditVB.setCursor(Cursor.HAND);
            ploygonEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/rhombus.png"));
            ploygonEditVB.setDisable(false);
            ploygonEditVB.setCursor(Cursor.HAND);
        }
        //Add the action to Buttons.
        pencilEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONPENCIL"));
                plotEditActionMode = 3; //freehanddesign = 3
            }
        });
        eraserEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONPENCIL"));
                setCurrentShape();
                plotEditActionMode = 4; //eraser = 4
            }
        });
        lineEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONPENCIL"));
                setCurrentShape();
                plotEditActionMode = 5; //drawline = 5
            }
        });
        ovalEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONPENCIL"));
                setCurrentShape();
                plotEditActionMode = 8; //drawoval = 8
            }
        });
        rectangleEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONPENCIL"));
                setCurrentShape();
                plotEditActionMode = 9; //drawrectangle = 9
            }
        });
        triangleEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONPENCIL"));
                setCurrentShape();
                plotEditActionMode = 10; //drawtriangle = 10
            }
        });
        heartEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONPENCIL"));
                setCurrentShape();
                plotEditActionMode = 15; //drawheart = 15
            }
        });
        diamondEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONPENCIL"));
                setCurrentShape();
                plotEditActionMode = 12; //drawdiamond = 12
            }
        });
        pentagonEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONPENCIL"));
                setCurrentShape();
                plotEditActionMode = 13; //drawpentagon = 13
            }
        });
        hexagonEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONPENCIL"));
                setCurrentShape();
                plotEditActionMode = 14; //drawhexagone = 14
            }
        });
        fourPointStarEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONPENCIL"));
                setCurrentShape();
                plotEditActionMode = 16; //drawfourpointstar = 16
            }
        });
        fivePointStarEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONPENCIL"));
                setCurrentShape();
                plotEditActionMode = 17; //drawfivepointstar = 17
            }
        });
        sixPointStarEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONPENCIL"));
                setCurrentShape();
                plotEditActionMode = 18; //drawsixpointstar = 18
            }
        });
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(pencilEditVB,eraserEditVB,lineEditVB,ovalEditVB,rectangleEditVB,triangleEditVB,heartEditVB,diamondEditVB,pentagonEditVB,hexagonEditVB,fourPointStarEditVB,fivePointStarEditVB,sixPointStarEditVB);
        //flow.getChildren().addAll(arcEditVB,bezierCurveEditVB,ploygonEditVB);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
    }
//////////////////////////// toolbox Menu Action ///////////////////////////
    private void setCurrentShape() {
    /* editgraph = 1, filltool = 2, freehanddesign = 3, eraser = 4,
    drawline = 5, drawarc = 6, beziercurve = 7, drawoval = 8, drawrectangle = 9, drawtriangle = 10, drawrighttriangle = 11, drawdiamond = 12,
    drawpentagon = 13, drawhexagone = 14, drawheart = 15, drawfourpointstar = 16, drawfivepointstar = 17, drawsixpointstar = 18, drawploygon = 19,
    selectdata = 20, noselectdata = 21, dragselectdata = 22, copydata = 23, pastedata = 24, cutdata = 25,
    mirrorverticaldata = 26, mirrorhorizentadata = 27, rotateclockwisedata = 28, rotateanticlockwisedata = 29;*/
    plotEditActionMode = 0;
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
        VBox visualizationViewVB = new VBox(); 
        Label visualizationViewLbl= new Label(objDictionaryAction.getWord("VISULIZATIONVIEW"));
        visualizationViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPVISULIZATIONVIEW")));
        visualizationViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/front_side.png"), visualizationViewLbl);
        visualizationViewVB.setPrefWidth(80);
        visualizationViewVB.getStyleClass().addAll("VBox");
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
        VBox rulerViewVB = new VBox(); 
        Label rulerViewLbl= new Label(objDictionaryAction.getWord("RULERVIEW"));
        rulerViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPRULERVIEW")));
        rulerViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/ruler.png"), rulerViewLbl);
        rulerViewVB.setPrefWidth(80);
        rulerViewVB.getStyleClass().addAll("VBox");        
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
        //enable disable conditions
        if(!isWorkingMode){
            compositeViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/composite_view.png"));
            compositeViewVB.setDisable(true);
            compositeViewVB.setCursor(Cursor.WAIT);
            gridViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/grid_view.png"));
            gridViewVB.setDisable(true);
            gridViewVB.setCursor(Cursor.WAIT);
            rulerViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/ruler.png"));
            rulerViewVB.setDisable(true);
            rulerViewVB.setCursor(Cursor.WAIT);
            zoomInViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/zoom_in.png"));
            zoomInViewVB.setDisable(true);
            zoomInViewVB.setCursor(Cursor.WAIT);
            normalViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/zoom_normal.png"));
            normalViewVB.setDisable(true);
            normalViewVB.setCursor(Cursor.WAIT);
            zoomOutViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/zoom_out.png"));
            zoomOutViewVB.setDisable(true);
            zoomOutViewVB.setCursor(Cursor.WAIT);                
        }else{
            compositeViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/composite_view.png"));
            compositeViewVB.setDisable(false);
            compositeViewVB.setCursor(Cursor.HAND);rulerViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/ruler.png"));
            gridViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/grid_view.png"));
            gridViewVB.setDisable(false);
            gridViewVB.setCursor(Cursor.HAND); 
            rulerViewVB.setDisable(false);
            rulerViewVB.setCursor(Cursor.HAND); 
            zoomInViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/zoom_in.png"));
            zoomInViewVB.setDisable(false);
            zoomInViewVB.setCursor(Cursor.HAND); 
            normalViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/zoom_normal.png"));
            normalViewVB.setDisable(false);
            normalViewVB.setCursor(Cursor.HAND); 
            zoomOutViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/zoom_out.png"));
            zoomOutViewVB.setDisable(false);
            zoomOutViewVB.setCursor(Cursor.HAND); 
        }
        //Add the action to Buttons.
        compositeViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artworkCompositeView();
            }
        });
        gridViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artworkGridView();
            }
        });
        graphViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artworkGraphView();
            }
        });
        visualizationViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artworkFrontSideView();
            }
        });
        flipSideViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artworkFlipSideView();
            }
        });
        switchSideViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artworkSwitchSideView();
            }
        });
        crossSectionFrontViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artworkCrossSectionFrontView();
            }
        });
        crossSectionRearViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artworkCrossSectionRearView();
            }
        });
        simulationViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artworkSimulationView();
            }
        }); 
        rulerViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artworkRulerView();
            }
        });
        zoomInViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artworkZoomIn();
            }
        });
        zoomOutViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artworkZoomOut();
            }
        });
        normalViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artworkNormalZoom();
            }
        });
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow"); 
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(compositeViewVB,gridViewVB,graphViewVB,visualizationViewVB,crossSectionFrontViewVB, simulationViewVB,zoomInViewVB,normalViewVB,zoomOutViewVB);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
    }
//////////////////////////// View Menu Action ///////////////////////////
    /**
     * artworkCompositeView
     * <p>
     * Function use for plotting composite View,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private void artworkCompositeView(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONCOMPOSITEVIEW"));
        plotViewActionMode = 1;
        plotViewAction();
    }
    /**
     * artworkGridView
     * <p>
     * Function use for plotting grid View,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private void artworkGridView(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONGRIDVIEW"));
        plotViewActionMode = 2;
        plotViewAction();
    }
     /**
     * artworkGraphView
     * <p>
     * Function use for plotting graph View,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private void artworkGraphView(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONGRAPHVIEW"));
        plotViewActionMode = 3;
        plotViewAction();
    }
     /**
     * artworkFrontSideView
     * <p>
     * Function use for plotting front side View,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private void artworkFrontSideView(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONVISULIZATIONVIEW"));
        plotViewActionMode = 4;
        plotViewAction();
    }
     /**
     * artworkFlipSideView
     * <p>
     * Function use for plotting flip side View,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private void artworkFlipSideView(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONFLIPSIDEVIEW"));
        plotViewActionMode = 5;
        plotViewAction();
    }
     /**
     * artworkSwitchSideView
     * <p>
     * Function use for plotting switch side View,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private void artworkSwitchSideView(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONSWITCHSIDEVIEW"));
        plotViewActionMode = 6;
        plotViewAction();
    }
     /**
     * artworkCrossSectionFrontView
     * <p>
     * Function use for plotting cross section front View,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private void artworkCrossSectionFrontView(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONCROSSSECTIONFRONTVIEW"));
        plotViewActionMode = 7;
        plotViewAction();
    }
     /**
     * artworkCrossSectionRearView
     * <p>
     * Function use for plotting cross section rear View,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private void artworkCrossSectionRearView(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONCROSSSECTIONREARVIEW"));
        plotViewActionMode = 8;
        plotViewAction();
    }
     /**
     * artworkSimulationView
     * <p>
     * Function use for plotting simulation View,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private void artworkSimulationView(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONSIMULATION"));
        plotSimulationView();
        //plotViewActionMode = 9;
        //plotViewAction();
    } 
     /**
     * artworkRulerView
     * <p>
     * Function use for plotting ruler View,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private void artworkRulerView(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONRULERVIEW"));
        hRuler = new Label();
        vRuler = new Label();
        //containerGP.add(hRuler, 1, 0);
        //containerGP.add(vRuler, 0, 1);
    }
    /**
     * artworkZoomIn
     * <p>
     * Function use for plotting zoom in View,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private void artworkZoomIn(){
        
        if(maxSizeCheck(bufferedImage.getHeight(),bufferedImage.getWidth())){
            //zoomfactor*=2;
            zoomfactor+=.1;
            plotViewAction();
        } else{
            lblStatus.setText(objDictionaryAction.getWord("MAXZOOMIN"));
        }
        System.gc();        
    }
     /**
     * artworkZoomOut
     * <p>
     * Function use for plotting zoom out View,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private void artworkZoomOut(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONZOOMOUTVIEW"));
        //fabric.setScaleX(zoomfactor/=2);
        //fabric.setScaleY(zoomfactor);
        if(minSizeCheck(bufferedImage.getWidth(),bufferedImage.getHeight())){
            //zoomfactor/=2;
            zoomfactor-=.1;
            plotViewAction();
        } else{
            lblStatus.setText(objDictionaryAction.getWord("MAXZOOMOUT"));
        }
        System.gc();
    }
    /**
     * artworkNormalZoom
     * <p>
     * Function use for plotting zoom normal View,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private void artworkNormalZoom(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONZOOMNORMALVIEW"));
        zoomfactor=1;
        plotViewAction();
        System.gc();
    }
    /**
     * maxSizeCheck
     * <p>
     * Function use for check artwork size constraints,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private boolean maxSizeCheck(int intEnds, int intPixs){
        String[] data =objArtwork.getObjConfiguration().getStrGraphSize().split("x");
        return true;//((intEnds*intPixs*zoomfactor*(Double.parseDouble(data[0])/graphFactor)*(Double.parseDouble(data[1])/graphFactor))<50000000)?false:true;//3840000.0
    }
    /**
     * minSizeCheck
     * <p>
     * Function use for check artwork size constraints,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private boolean minSizeCheck(int intEnds, int intPixs){
        String[] data =objArtwork.getObjConfiguration().getStrGraphSize().split("x");
        return ((intEnds*(zoomfactor-.1))>1 && (intPixs*(zoomfactor-.1))>1 && (zoomfactor-.1)>0 && (Double.parseDouble(data[0])*zoomfactor/graphFactor)>1 && (Double.parseDouble(data[1])*zoomfactor/graphFactor)>1)?false:true;               
    }

    /**
     * plotViewAction
     * <p>
     * Function use for controlling different plots for menu item View,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */
    private void plotViewAction(){
        if(artworkChildStage!=null){
            artworkChildStage.close();
        }
        artworkChildStage=null;
        setCurrentShape();
        objArtwork.getObjConfiguration().setIntBoxSize(1);
        if(plotViewActionMode==1)
            plotCompositeView();
        else if(plotViewActionMode==2)
            plotGridView();
        else if(plotViewActionMode==3)
            plotGraphView();
        else if(plotViewActionMode==4)
            plotFrontSideView();
        else if(plotViewActionMode==7)
            plotCrossSectionFrontView();
        else if(plotViewActionMode==9)
            plotSimulationView();
        else
            plotCompositeView();
    }
    /**
     * plotCompositeView
     * <p>
     * Function use for plotting composite View,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private void plotCompositeView(){
        try {
            lblStatus.setText(objDictionaryAction.getWord("GETCOMPOSITEVIEW"));
            BufferedImage editBufferedImage = new BufferedImage((int)(bufferedImage.getWidth()*zoomfactor), (int)(bufferedImage.getHeight()*zoomfactor),BufferedImage.TYPE_INT_RGB);
            Graphics2D g = editBufferedImage.createGraphics();
            g.drawImage(bufferedImage, 0, 0, (int)(bufferedImage.getWidth()*zoomfactor), (int)(bufferedImage.getHeight()*zoomfactor), null);
            g.dispose();
            artwork.setImage(SwingFXUtils.toFXImage(editBufferedImage, null)); 
            
        /*            
            BufferedImage bufferedDataImage;
            objCanvas.setWidth(bufferedImage.getWidth());
            objCanvas.setHeight(bufferedImage.getHeight());

            try {
                bufferedDataImage = ImageIO.read(new File(System.getProperty("user.dir")+"/mla/ruler.png"));

                Image image = SwingFXUtils.toFXImage(bufferedDataImage,null);
                System.out.println(bufferedDataImage.getWidth()+"=R="+bufferedDataImage.getHeight());
                objGraphicsContext.drawImage(image, 0, 0, bufferedDataImage.getWidth(), bufferedDataImage.getHeight());
            } catch (IOException ex) {
                Logger.getLogger(ArtworkView.class.getName()).log(Level.SEVERE, null, ex);
            }        
        */          
        
            //stackPane.getChildren().addAll(artwork,objCanvas);
            //container.setContent(stackPane);
        
            container.setContent(artwork); 
            editBufferedImage=null;
            lblStatus.setText(objDictionaryAction.getWord("GOTCOMPOSITEVIEW"));
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"plotCompositeView() : Error while viewing composte view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
        System.gc();
    }
    /**
     * plotGridView
     * <p>
     * Function use for plotting grid View,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private void plotGridView(){
        try {
            BufferedImage editBufferedImage = null;
            artwork.setImage(SwingFXUtils.toFXImage(getArtworkGrid(editBufferedImage), null));
            container.setContent(artwork);
            editBufferedImage = null;
            lblStatus.setText(objDictionaryAction.getWord("GOTGRIDVIEW"));
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"plotGridView() : Error while viewing grid view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
        System.gc();
    }
    /**
     * plotGraphView
     * <p>
     * Function use for plotting graph View,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private void plotGraphView(){
        try {
            BufferedImage editBufferedImage = null;
            artwork.setImage(SwingFXUtils.toFXImage(getArtworkGraph(editBufferedImage), null));
            container.setContent(artwork);
            editBufferedImage = null;
            lblStatus.setText(objDictionaryAction.getWord("GOTGRAPHVIEW"));
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"plotGraphView() : Error while viewing dobby garph view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
        System.gc();
    }
    /**
     * plotFrontSideView
     * <p>
     * Function use for plotting front side View,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private void plotFrontSideView(){
        try{
            lblStatus.setText(objDictionaryAction.getWord("GETVISULIZATIONVIEW"));
            int intHeight=(int)bufferedImage.getHeight();
            int intLength=(int)bufferedImage.getWidth();
            
            int bands = 3;
            int rgb = 0;
            BufferedImage editBufferedImage = new BufferedImage((int)(intLength*bands), (int)(intHeight*bands),BufferedImage.TYPE_INT_RGB);
            for(int x=0, p=0, t=0; x<intHeight; x++){
                for(int y=0, q=0; y<intLength; y++){
                    rgb = bufferedImage.getRGB(y, x);
                    int red   = (rgb & 0x00ff0000) >> 16;
                    int green = (rgb & 0x0000ff00) >> 8;
                    int blue  =  rgb & 0x000000ff;
                    if(rgb==bufferedImage.getRGB(0, 0))
                        t++;
                    else
                        t=1;
                    for(int i = 0; i < bands; i++) {
                        for(int j = 0; j < bands; j++) {                        
                            if(t%2==0){
                                if(j==0)
                                    rgb = new java.awt.Color(red,green,blue).brighter().getRGB();
                                else if(j==2)
                                    rgb = new java.awt.Color(red,green,blue).darker().getRGB();
                                else
                                    rgb = new java.awt.Color(red,green,blue).getRGB();
                            } else if(t%2==1){
                                if(i==0)
                                    rgb = new java.awt.Color(red,green,blue).brighter().getRGB();
                                else if(i==2)
                                    rgb = new java.awt.Color(red,green,blue).darker().getRGB();
                                else
                                    rgb = new java.awt.Color(red,green,blue).getRGB();
                            }
                        editBufferedImage.setRGB(q+j, p+i, rgb);
                        }
                    }
                    q+=bands;
                }
                p+=bands;
            }
            //intLength = (int)(((objConfiguration.getIntDPI()*intLength)/objConfiguration.getIntEPI())*zoomfactor*3);
            //intHeight = (int)(((objConfiguration.getIntDPI()*intHeight)/objConfiguration.getIntPPI())*zoomfactor*3);
            intLength = (int)(intLength*3*zoomfactor);
            intHeight = (int)(intHeight*3*zoomfactor);
            BufferedImage bufferedImageresize = new BufferedImage(intLength, intHeight,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageresize.createGraphics();
            g.drawImage(editBufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            
            artwork.setImage(SwingFXUtils.toFXImage(bufferedImageresize, null));
            container.setContent(artwork);
            editBufferedImage = null;
            bufferedImageresize = null;
            System.gc();
            lblStatus.setText(objDictionaryAction.getWord("GOTVISULIZATIONVIEW"));
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"plotVisulizationView() : Error while viewing visulization view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } 
    }
    /**
     * plotCrossSectionFrontView
     * <p>
     * Function use for plotting cross section front side View,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private void plotCrossSectionFrontView(){
        try{
            lblStatus.setText(objDictionaryAction.getWord("GETCROSSSECTIONFRONTVIEW"));
            int intHeight=(int)bufferedImage.getHeight();
            int intLength=(int)bufferedImage.getWidth();
            
            List lstLines = new ArrayList();
            ArrayList<Integer> lstEntry = null;
            int lineCount = 0;
            for (int i = 0; i < intHeight; i++){
                lstEntry = new ArrayList();
                for (int j = 0; j < intLength; j++){
                    int rgb = bufferedImage.getRGB(j, i);
                    //add the first color on array
                    if(lstEntry.size()==0)
                        lstEntry.add(rgb);
                    //check for redudancy
                    else {                
                        if(!lstEntry.contains(rgb))
                            lstEntry.add(rgb);
                    }
                }
                lstLines.add(lstEntry);
                lineCount+=lstEntry.size();
            }
            BufferedImage bufferedImageesize = new BufferedImage(intLength, lineCount, BufferedImage.TYPE_INT_RGB);
            lineCount =0;
            int init = 0;
            for (int i = 0 ; i < intHeight; i++){
                lstEntry = (ArrayList)lstLines.get(i);
                for(int k=0; k<lstEntry.size(); k++, lineCount++){
                    init = (Integer)lstEntry.get(k);
                    for (int j = 0; j < intLength; j++){
                        if(init==bufferedImage.getRGB(j, i)){
                            bufferedImageesize.setRGB(j, lineCount, init);
                        }else{
                            bufferedImageesize.setRGB(j, lineCount, -1);
                        }   
                    }
                }
            }
            
            intHeight = (int)(lineCount*zoomfactor);
            intLength = (int)(intLength*zoomfactor);
            BufferedImage bufferedImageresize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageresize.createGraphics();
            g.drawImage(bufferedImageesize, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImageesize = null;            
            artwork.setImage(SwingFXUtils.toFXImage(bufferedImageresize, null));
            container.setContent(artwork);
            bufferedImageresize = null;
            System.gc();
            lblStatus.setText(objDictionaryAction.getWord("GETCROSSSECTIONFRONTVIEW"));
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),"plotCrossSectionFrontView() : Error while viewing  cross section view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    /**
     * plotSimulationView
     * <p>
     * Function use for plotting simulation View,
     * 
     * @exception   (@throws SQLException)
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @see         javafx.event.*;
     * @link        FabricView
     */    
    private void plotSimulationView(){
        try {
            SimulatorEditView objBaseSimulationView = new SimulatorEditView(objConfiguration, bufferedImage);                    
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
}    

/*
public void printDesign() {   
        try {            
            if(objPrinterJob.printDialog()){
                objPrinterJob.setPrintable(new Printable() {
                    @Override
                    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                        if (pageIndex != 0) {
                            return NO_SUCH_PAGE;
                        }                        
                        return PAGE_EXISTS;                    
                    }
                });     
                objPrinterJob.print();
            }
        } catch (PrinterException ex) {             
            new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);  
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    public void printPreview() {   
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
                try {
                    printDesign();
                    dialogStage.close();
                } catch (Exception ex) {
                    new Logging("SEVERE",ArtworkView.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
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
        
        ImageView printImage = new ImageView();
        printImage.setImage(SwingFXUtils.toFXImage(bufferedImage, null));        
        popup.add(printImage, 0, 1, 2, 1);

        Scene popupScene = new Scene(popup);
        popupScene.getStylesheets().add(ArtworkView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        artworkChildStage.setScene(popupScene);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("TITLE"));
        dialogStage.showAndWait();   
    }
*/

class VLines extends Thread
{
    Graphics2D objGraphics2D;
    int intHeight, intLength, graphFactor;
    String [] data;
    float zoomfactor;
    byte type;
    VLines(Graphics2D g, int intHeight, int intLength, int graphFactor, String[] data, float zoomfactor, byte type)
    {
        super("my extending thread");
        this.objGraphics2D = g;
        this.intHeight = intHeight;
        this.intLength = intLength;
        this.graphFactor = graphFactor;
        this.data = data;
        this.zoomfactor = zoomfactor;
        this.type = type;
        //System.out.println("my thread created" + this);
        start();
    }
    public void run()
    {
        try
        {
            BasicStroke bs = new BasicStroke(2);
            objGraphics2D.setStroke(bs);
            //For vertical line
            for(int j = 0; j < intLength; j++) {
                if((j%(Integer.parseInt(data[0])))==0 && type==3){
                    bs = new BasicStroke(2*zoomfactor);
                    objGraphics2D.setStroke(bs);
                }else{
                    bs = new BasicStroke(1*zoomfactor);
                    objGraphics2D.setStroke(bs);
                }
                objGraphics2D.drawLine((int)(j*zoomfactor*Integer.parseInt(data[1])/graphFactor), 0,  (int)(j*zoomfactor*Integer.parseInt(data[1])/graphFactor), (int)(intHeight*zoomfactor*Integer.parseInt(data[0])/graphFactor));
            }
            //For horizental line
            for(int i = 0; i < intHeight; i++) {
                if((i%(Integer.parseInt(data[1])))==0 && type==3){
                    bs = new BasicStroke(2*zoomfactor);
                    objGraphics2D.setStroke(bs);
                }else{
                    bs = new BasicStroke(1*zoomfactor);
                    objGraphics2D.setStroke(bs);
                }
                objGraphics2D.drawLine(0, (int)(i*zoomfactor*Integer.parseInt(data[0])/graphFactor), (int)(intLength*zoomfactor*Integer.parseInt(data[1])/graphFactor), (int)(i*zoomfactor*Integer.parseInt(data[0])/graphFactor));
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //System.out.println("my thread interrupted"+ex.getCause().toString());
            //Logger.getLogger(VLines.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("My thread run is over" );
    }
}