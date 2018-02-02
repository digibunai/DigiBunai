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
import com.mla.artwork.ArtworkEditView;
import com.mla.dictionary.DictionaryAction;
import com.mla.fabric.DensityView;
import com.mla.fabric.Fabric;
import com.mla.fabric.FabricAction;
import com.mla.fabric.FabricImportView;
import com.mla.fabric.FabricView;
import com.mla.fabric.PatternView;
import com.mla.fabric.TmpFabricAction;
import com.mla.yarn.Yarn;
import com.mla.yarn.YarnView;
import com.mla.main.AboutView;
import com.mla.main.Configuration;
import com.mla.main.ContactView;
import com.mla.main.EncryptZip;
import com.mla.main.HelpView;
import com.mla.main.IDGenerator;
import com.mla.main.Logging;
import com.mla.main.TechnicalView;
import com.mla.main.WindowView;
import com.mla.secure.Security;
import com.mla.user.UserAction;
import com.mla.utility.SimulatorEditView;
import com.mla.weave.Weave;
import com.mla.weave.WeaveAction;
import com.mla.yarn.YarnAction;
import com.sun.media.jai.codec.ByteArraySeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;
import static flashplayer.application.demo.SimpleFlashPlayer.newFunction;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
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
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;
/**
 *
 * @Designing GUI window for cloth editor
 * @author Aatif Ahmad Khan
 * 
 */
public class ClothView  extends Application {

    public static Stage clothStage;
    Cloth objCloth=null;
    Fabric objFabric = null;
    Fabric bodyFabric=null;
    ArtworkAction objArtworkAction = null;
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction;
    
    private BorderPane root;
    private Scene scene;
    private ScrollPane container;
    private ToolBar toolBar;
    private MenuBar menuBar;
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;
    
    private GridPane containerGP;
    private Menu homeMenu;
    private Menu fileMenu;
    private Menu sareeMenu;
    private Menu dressMenu;
    private Menu stoleMenu;
    private Menu dupattaMenu;
    private Menu shawlMenu;
    private Menu brocadeMenu;
    private Menu helpMenu;
    private Label fileMenuLabel;
    private Label sareeMenuLabel;
    private Label dressMenuLabel;
    private Label stoleMenuLabel;
    private Label dupattaMenuLabel;
    private Label shawlMenuLabel;
    private Label brocadeMenuLabel;
    
    private VBox openFileVB;
    private VBox loadFileVB; 
    private VBox saveFileVB;    
    private VBox saveAsFileVB;
    private VBox saveTextureFileVB;
    private VBox printFileVB;
    private VBox simulationViewVB;
    private VBox simulation2DViewVB;
    private VBox simulation3DViewVB;
    private VBox clearFileVB;
    private VBox repeatsVB;
    private VBox fullViewVB;
    private VBox quitFileVB; 
    
    private VBox customVB;
    private VBox paluSareeVB;
    private VBox borderSareeVB;
    private VBox crossBorderSareeVB;
    private VBox skartSareeVB;
    private VBox koniaSareeVB;
    private VBox blouseSareeVB;
    private VBox bodyDressVB;
    private VBox borderDressVB;
    private VBox borderSingleDressVB;
    private VBox bodyStoleVB;
    private VBox crossBorderLeftStoleVB;
    private VBox crossBorderRightStoleVB;
    private VBox paluStoleVB;
    private VBox bodyDupattaVB;
    private VBox crossBorderLeftDupattaVB;
    private VBox crossBorderRightDupattaVB;
    private VBox paluDupattaVB;
    private VBox bodyShawlVB;
    private VBox bodyBrocadeVB;
    private VBox allComponentVB;
    
    ScrollPane lefttopKoniaPane;
    ScrollPane leftbottomKoniaPane;
    ScrollPane righttopKoniaPane;
    ScrollPane rightbottomKoniaPane;
    ScrollPane leftCrossBorderPane;
    ScrollPane rightCrossBorderPane;
    ScrollPane leftBorderPane;
    ScrollPane rightBorderPane;
    ScrollPane skartPane;
    ScrollPane blousePane;
    ScrollPane leftPalluPane;
    ScrollPane rightPalluPane;
    ScrollPane bodyPane;
    ScrollPane rightsideLeftCrossBorderPane;
    ScrollPane rightsideRightCrossBorderPane;
    
    ImageView lefttopKonia;
    ImageView leftbottomKonia;
    ImageView righttopKonia;
    ImageView rightbottomKonia;
    ImageView leftCrossBorder;
    ImageView rightCrossBorder;
    ImageView leftBorder;
    ImageView rightBorder;
    ImageView skart;
    ImageView blouse;
    ImageView leftPallu;
    ImageView rightPallu;
    ImageView body;
    ImageView rightsideLeftCrossBorder;
    ImageView rightsideRightCrossBorder;
    
    int gpRowCount, gpColumnCount;
    int[][] pos;
    
    // these arrays will contain current height/width of components
    int widths[], heights[];
    
    // this array will contain user input #repeats of components
    int repeats[];
    
    int pinch=12;
    final int MARGIN=5;
    double zoomFactor=1;
    ArrayList<ScrollPane> upper;
    ArrayList<ScrollPane> lower;
    ArrayList<ScrollPane> right;
    ArrayList<ScrollPane> left;
    boolean drag, BorderX, BorderY;
    double xGP;
    double yGP;
    public Map<String,String> mapFabricId=new HashMap<String,String>();
    
    ComboBox zoomCB;
    
    private CheckBox linkBorderCB;
    private CheckBox linkCrossBorderCB;
    private CheckBox linkPalluCB;
    
    private CheckBox leftBorderMultiRepeatCB;
    private CheckBox rightBorderMultiRepeatCB;
    private CheckBox leftCrossBorderMultiRepeatCB;
    private CheckBox rightCrossBorderMultiRepeatCB;
    private CheckBox rightsideLeftCrossBorderMultiRepeatCB;
    private CheckBox rightsideRightCrossBorderMultiRepeatCB;
    private CheckBox leftPalluMultiRepeatCB;
    private CheckBox rightPalluMultiRepeatCB;
    private CheckBox blouseMultiRepeatCB;
    private CheckBox skartMultiRepeatCB;
    
    TextField txtLeftBorderRepeats;
    TextField txtRightBorderRepeats;
    TextField txtLeftCrossBorderRepeats;
    TextField txtRightCrossBorderRepeats;
    TextField txtRightsideLeftCrossBorderRepeats;
    TextField txtRightsideRightCrossBorderRepeats;
    TextField txtLeftPalluRepeats;
    TextField txtRightPalluRepeats;
    TextField txtBlouseRepeats;
    TextField txtSkartRepeats;
    
    boolean borderLinkPolicy;
    boolean crossBorderLinkPolicy;
    boolean palluLinkPolicy;
    
    boolean leftBorderMultiRepeatPolicy;
    boolean rightBorderMultiRepeatPolicy;
    boolean leftCrossBorderMultiRepeatPolicy;
    boolean rightCrossBorderMultiRepeatPolicy;
    boolean rightsideLeftCrossBorderMultiRepeatPolicy;
    boolean rightsideRightCrossBorderMultiRepeatPolicy;
    boolean leftPalluMultiRepeatPolicy;
    boolean rightPalluMultiRepeatPolicy;
    boolean blouseMultiRepeatPolicy;
    boolean skartMultiRepeatPolicy;
    
    boolean leftBorderEnabled;
    boolean rightBorderEnabled;
    boolean leftCrossBorderEnabled;
    boolean rightCrossBorderEnabled;
    boolean leftPalluEnabled;
    boolean rightPalluEnabled;
    boolean blouseEnabled;
    boolean skartEnabled;
    boolean rightsideLeftCrossBorderEnabled;
    boolean rightsideRightCrossBorderEnabled;
	
    TextField widthNewTxt;
    TextField heightNewTxt;
    ChoiceBox repeatModeCB;
    ChoiceBox rotationCB;
    String currentClothMode;
    
    boolean sareeLayout1, sareeLayout2, sareeLayout3, sareeLayout4, sareeLayout5, sareeLayout6;
    boolean dressLayout1, dressLayout2;
    final int RESIZE_MARGIN=5;
    
    final int MINSIZE=3; // minimum size of components in pixels
    final int MAXBORDER=12*pinch; // maximum size of Borders, Cross Borders, Skart (in pixels)
    final int MAXPALLU=40*pinch; // maximum size of Pallu(s) (in pixels)
    final int MAXBLOUSE=42*pinch; // maximum size of Blouse (in pixels)

    private boolean isPaneDragable;
    private double xInitialPane;
    private double yInitialPane;
    
    private boolean isNew;
    
    public ClothView(final Stage primaryStage) { }
  
    public ClothView(Configuration objConfigurationCall) {
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);
        objCloth=new Cloth();
        objCloth.setObjConfiguration(objConfiguration);
    
        clothStage = new Stage();    
        root = new BorderPane();
        scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(ClothView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        
        getMapFabricId();
        
        borderLinkPolicy=true;
        crossBorderLinkPolicy=true;
        palluLinkPolicy=true;
        
        linkBorderCB =new CheckBox(objDictionaryAction.getWord("CAPTIONBORDERS"));
        linkBorderCB.setSelected(borderLinkPolicy);
        linkCrossBorderCB=new CheckBox(objDictionaryAction.getWord("CAPTIONCROSSBORDERS"));
        linkCrossBorderCB.setSelected(crossBorderLinkPolicy);
        linkPalluCB=new CheckBox(objDictionaryAction.getWord("CAPTIONPALLUS"));
        linkPalluCB.setSelected(palluLinkPolicy);
        leftBorderMultiRepeatCB=new CheckBox(objDictionaryAction.getWord("LEFTBORDER"));
        rightBorderMultiRepeatCB=new CheckBox(objDictionaryAction.getWord("RIGHTBORDER"));
        leftCrossBorderMultiRepeatCB=new CheckBox(objDictionaryAction.getWord("LEFTCROSSBORDER"));
        rightCrossBorderMultiRepeatCB=new CheckBox(objDictionaryAction.getWord("RIGHTCROSSBORDER"));
        rightsideLeftCrossBorderMultiRepeatCB=new CheckBox(objDictionaryAction.getWord("RIGHTSIDELEFTCROSSBORDER"));
        rightsideRightCrossBorderMultiRepeatCB=new CheckBox(objDictionaryAction.getWord("RIGHTSIDERIGHTCROSSBORDER"));
        leftPalluMultiRepeatCB=new CheckBox(objDictionaryAction.getWord("LEFTPALLU"));
        rightPalluMultiRepeatCB=new CheckBox(objDictionaryAction.getWord("RIGHTPALLU"));
        blouseMultiRepeatCB=new CheckBox(objDictionaryAction.getWord("BLOUSE"));
        skartMultiRepeatCB=new CheckBox(objDictionaryAction.getWord("SKART"));
        
        txtLeftBorderRepeats=new TextField();
        txtRightBorderRepeats=new TextField();
        txtLeftCrossBorderRepeats=new TextField();
        txtRightCrossBorderRepeats=new TextField();
        txtRightsideLeftCrossBorderRepeats=new TextField();
        txtRightsideRightCrossBorderRepeats=new TextField();
        txtLeftPalluRepeats=new TextField();
        txtRightPalluRepeats=new TextField();
        txtBlouseRepeats=new TextField();
        txtSkartRepeats=new TextField();
    
        
        linkBorderCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    borderLinkPolicy=t1;
            }
        });
        linkCrossBorderCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    crossBorderLinkPolicy=t1;
            }
        });
        linkPalluCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                palluLinkPolicy=t1;
            }
        });
        leftBorderMultiRepeatCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                leftBorderMultiRepeatPolicy=t1;
                txtLeftBorderRepeats.setDisable(!t1);
            }
        });
        rightBorderMultiRepeatCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                rightBorderMultiRepeatPolicy=t1;
                txtRightBorderRepeats.setDisable(!t1);
            }
        });
        leftCrossBorderMultiRepeatCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                leftCrossBorderMultiRepeatPolicy=t1;
                txtLeftCrossBorderRepeats.setDisable(!t1);
            }
        });
        rightCrossBorderMultiRepeatCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                rightCrossBorderMultiRepeatPolicy=t1;
                txtRightCrossBorderRepeats.setDisable(!t1);
            }
        });
        rightsideLeftCrossBorderMultiRepeatCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                rightsideLeftCrossBorderMultiRepeatPolicy=t1;
                txtRightsideLeftCrossBorderRepeats.setDisable(!t1);
            }
        });
        rightsideRightCrossBorderMultiRepeatCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                rightsideRightCrossBorderMultiRepeatPolicy=t1;
                txtRightsideRightCrossBorderRepeats.setDisable(!t1);
            }
        });
        leftPalluMultiRepeatCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                leftPalluMultiRepeatPolicy=t1;
                txtLeftPalluRepeats.setDisable(!t1);
            }
        });
        rightPalluMultiRepeatCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                rightPalluMultiRepeatPolicy=t1;
                txtRightPalluRepeats.setDisable(!t1);
            }
        });
        blouseMultiRepeatCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                blouseMultiRepeatPolicy=t1;
                txtBlouseRepeats.setDisable(!t1);
            }
        });
        skartMultiRepeatCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                skartMultiRepeatPolicy=t1;
                txtSkartRepeats.setDisable(!t1);
            }
        });
        
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
        menuBar.prefWidthProperty().bind(clothStage.widthProperty());
        topContainer.getChildren().add(menuBar);
        topContainer.getChildren().add(toolBar); 
        topContainer.setId("topContainer");
        root.setTop(topContainer);

        upper=new ArrayList<ScrollPane>();
        lower=new ArrayList<ScrollPane>();
        right=new ArrayList<ScrollPane>();
        left=new ArrayList<ScrollPane>();
        
        /* { 0 leftborder, 1 blouse, 2 leftcrossborder, 3 leftpallu, 4 rightcrossborder
            , 5 body, 6 skart, 7 rightsideleftcrossborder, 8 rightpallu
            , 9 rightsiderightcrossborder, 10 right border} */
        widths=new int[11];
        heights=new int[11];
        repeats=new int[11];
        // if no user input, repeats=1 will be assumed
        for(int a=0; a<11; a++)
            repeats[a]=1;
        repeats[0]=repeats[10]=2;
        repeats[2]=repeats[4]=repeats[7]=repeats[9]=2;
        repeats[3]=repeats[8]=5;
        repeats[6]=2;
        
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
        // Saree menu - new, save, save as, open, load recent.
        sareeMenuLabel = new Label(objDictionaryAction.getWord("SAREE"));
        sareeMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAREEMENU")));
        sareeMenu = new Menu();
        sareeMenu.setGraphic(sareeMenuLabel);
        sareeMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sareeMenuAction();
            }
        });
        // Saree menu - new, save, save as, open, load recent.
        dressMenuLabel = new Label(objDictionaryAction.getWord("DRESS"));
        dressMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDRESSMENU")));
        dressMenu = new Menu();
        dressMenu.setGraphic(dressMenuLabel);
        dressMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dressMaterialMenuAction();
            }
        });
        // Saree menu - new, save, save as, open, load recent.
        stoleMenuLabel = new Label(objDictionaryAction.getWord("STOLE"));
        stoleMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSTOLEMENU")));
        stoleMenu = new Menu();
        stoleMenu.setGraphic(stoleMenuLabel);
        stoleMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONSTOLEMENU"));
                menuHighlight("STOLE");            
            }
        });
        // Saree menu - new, save, save as, open, load recent.
        dupattaMenuLabel = new Label(objDictionaryAction.getWord("DUPATTA"));
        dupattaMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDUPATTAMENU")));
        dupattaMenu = new Menu();
        dupattaMenu.setGraphic(dupattaMenuLabel);
        dupattaMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONDUPATTAMENU"));
                menuHighlight("DUPATTA");            
            }
        });
        // Saree menu - new, save, save as, open, load recent.
        shawlMenuLabel = new Label(objDictionaryAction.getWord("SHAWL"));
        shawlMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSHAWLMENU")));
        shawlMenu = new Menu();
        shawlMenu.setGraphic(shawlMenuLabel);
        shawlMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONSHAWLMENU"));
                menuHighlight("SHAWL");            
            }
        });
        // Saree menu - new, save, save as, open, load recent.
        brocadeMenuLabel = new Label(objDictionaryAction.getWord("GAISARBROCADE"));
        brocadeMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPGAISARBROCADEMENU")));
        brocadeMenu = new Menu();
        brocadeMenu.setGraphic(brocadeMenuLabel);
        brocadeMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gaisarMenuAction();           
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
                lblStatus.setText(objDictionaryAction.getWord("ACTIONHELP"));
                HelpView objHelpView = new HelpView(objConfiguration);
            }
        });        
        MenuItem technicalMenuItem = new MenuItem(objDictionaryAction.getWord("TECHNICAL"));
        technicalMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/technical_info.png"));
        technicalMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {       
                lblStatus.setText(objDictionaryAction.getWord("ACTIONTECHNICAL"));
                TechnicalView objTechnicalView = new TechnicalView(objConfiguration);
            }
        });
        MenuItem aboutMenuItem = new MenuItem(objDictionaryAction.getWord("ABOUTUS"));
        aboutMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/about_software.png"));
        aboutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONABOUTUS"));
                AboutView objAboutView = new AboutView(objConfiguration);
            }
        });
        MenuItem contactMenuItem = new MenuItem(objDictionaryAction.getWord("CONTACTUS"));
        contactMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/contact_us.png"));
        contactMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCONTACTUS"));
                ContactView objContactView = new ContactView(objConfiguration);
            }
        });
        MenuItem exitMenuItem = new MenuItem(objDictionaryAction.getWord("EXIT"));
        exitMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/quit.png"));
        exitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.gc();
                clothStage.close();    
            }
        });    
        helpMenu.getItems().addAll(helpMenuItem, technicalMenuItem, aboutMenuItem, contactMenuItem, new SeparatorMenuItem(), exitMenuItem);        
        menuBar.getMenus().addAll(homeMenu, fileMenu, sareeMenu, dressMenu, stoleMenu, brocadeMenu, helpMenu);

        container = new ScrollPane();
        container.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);

        containerGP = new GridPane();
        //containerGP.setMinSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        containerGP.setVgap(0);
        containerGP.setHgap(0);
        Label lblZoom=new Label();
        lblZoom.setText(objDictionaryAction.getWord("ZOOMLEVEL"));
        
        zoomCB=new ComboBox();
        //zoomCB.getItems().addAll("12", "18", "24", "30", "36");
        zoomCB.getItems().addAll("100", "150", "200", "250", "300");
        //zoomCB.setValue("12");
        if(objConfiguration.getGarmentPinch()==12)
            zoomCB.setValue("100");
        else
            zoomCB.setValue(String.valueOf((int)(((double)objConfiguration.getGarmentPinch()/12.0)*100)));
        zoomCB.valueProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                saveContext();
                zoomFactor=Double.parseDouble((String)t1)/(Double.parseDouble((String)t));
                pinch = (int)(12*((Double.parseDouble((String)t1))/100.0));
                objConfiguration.setGarmentZoom(zoomFactor);
                objConfiguration.setGarmentPinch(pinch);
                System.err.println(zoomFactor+"+++++++++"+pinch);
                loadContext();
            }
        });
		
        container.setContent(containerGP);
        allComponentVB=new VBox();
        allComponentVB.setSpacing(3);
        HBox hBoxLink=new HBox();
        hBoxLink.setSpacing(5);
        Label lblLinkComponents=new Label(objDictionaryAction.getWord("CAPTIONADDSYMMETRY"));
        lblLinkComponents.setUnderline(true);
        hBoxLink.getChildren().addAll(lblLinkComponents, linkBorderCB, linkCrossBorderCB, linkPalluCB, lblZoom, zoomCB);
        HBox hBoxMulti=new HBox();
        hBoxMulti.setSpacing(3);
        Label lblMultiRepeat=new Label(objDictionaryAction.getWord("CAPTIONPROVISIONMULTIREPEAT"));
        lblMultiRepeat.setUnderline(true);
        //hBoxMulti.getChildren().addAll(lblMultiRepeat, leftBorderMultiRepeatCB
        //        , rightBorderMultiRepeatCB, leftCrossBorderMultiRepeatCB, rightCrossBorderMultiRepeatCB, leftPalluMultiRepeatCB, rightPalluMultiRepeatCB
        //        , blouseMultiRepeatCB, skartMultiRepeatCB, rightsideLeftCrossBorderMultiRepeatCB, rightsideRightCrossBorderMultiRepeatCB);
        allComponentVB.getChildren().addAll(container, hBoxLink, hBoxMulti);
        root.setCenter(allComponentVB);

        
        menuHighlight(null);
        currentClothMode="SAREE";
        // initialize components and Panes
        initDesign();
        // setting initial heights and widths
        enableAllComponents(true);
        setInitialWidthHeight();
        setComponentWidthHeight();
        enableAllComponents(false);
        clothLayout("sareeLayout1");
        
        loadContext();

        clothStage.getIcons().add(new Image("/media/icon.png"));
        clothStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWCLOTHEDITOR")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        clothStage.setX(-5);
        clothStage.setY(0);
        clothStage.setResizable(false);
        clothStage.setScene(scene);
        clothStage.show();
        clothStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                final Stage dialogStage = new Stage();
                dialogStage.initStyle(StageStyle.UTILITY);
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.setResizable(false);
                dialogStage.setIconified(false);
                dialogStage.setFullScreen(false);
                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ALERT"));
                BorderPane root = new BorderPane();
                Scene scene = new Scene(root, 300, 100, Color.WHITE);
                scene.getStylesheets().add(ClothView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        saveContext();
                        objConfiguration.setGarmentZoom(1.0);
                        objConfiguration.setGarmentPinch(pinch);
                        dialogStage.close();
                        clothStage.close();
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
        
        // shortcuts
        final KeyCodeCombination kchA = new KeyCodeCombination(KeyCode.H, KeyCombination.ALT_DOWN); // Home Menu
        final KeyCodeCombination kcfA = new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN); // File Menu
        final KeyCodeCombination kcvS = new KeyCodeCombination(KeyCode.V, KeyCombination.SHIFT_DOWN); // Saree Menu
        final KeyCodeCombination kcdCS = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN); // Dress Material Menu
        final KeyCodeCombination kcqC = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN); // Shawl/Gaisar Brocade Menu
        final KeyCodeCombination kcsA = new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN); // Support Menu
        final KeyCodeCombination kceC = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN); // Export
        final KeyCodeCombination kctA = new KeyCodeCombination(KeyCode.T, KeyCombination.ALT_DOWN); // Print Texture
        final KeyCodeCombination kcmCS = new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN); // Simulation 2D
        final KeyCodeCombination kccA = new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN); // Clear
        final KeyCodeCombination kc1C = new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.CONTROL_DOWN); // Layout border across saree
        final KeyCodeCombination kc2C = new KeyCodeCombination(KeyCode.DIGIT2, KeyCombination.CONTROL_DOWN); // Layout border across body
        final KeyCodeCombination kc3C = new KeyCodeCombination(KeyCode.DIGIT3, KeyCombination.CONTROL_DOWN); // Layout single cross border
        final KeyCodeCombination kc4C = new KeyCodeCombination(KeyCode.DIGIT4, KeyCombination.CONTROL_DOWN); // Layout saree with skirt
        final KeyCodeCombination kc5C = new KeyCodeCombination(KeyCode.DIGIT5, KeyCombination.CONTROL_DOWN); // Layout saree with blouse
        final KeyCodeCombination kc6C = new KeyCodeCombination(KeyCode.DIGIT6, KeyCombination.CONTROL_DOWN); // Layout body with pallu
        final KeyCodeCombination kc7C = new KeyCodeCombination(KeyCode.DIGIT7, KeyCombination.CONTROL_DOWN); // Layout body without pallu
        final KeyCodeCombination kc8C = new KeyCodeCombination(KeyCode.DIGIT8, KeyCombination.CONTROL_DOWN); // Layout pallu with inner CB
        final KeyCodeCombination kc9C = new KeyCodeCombination(KeyCode.DIGIT9, KeyCombination.CONTROL_DOWN); // Layout pallu with outer CB
        final KeyCodeCombination kczA = new KeyCodeCombination(KeyCode.Z, KeyCombination.ALT_DOWN); // Repeats
        final KeyCodeCombination kcjA = new KeyCodeCombination(KeyCode.J, KeyCombination.ALT_DOWN); // Custom Layout
        final KeyCodeCombination kcdAC = new KeyCodeCombination(KeyCode.D, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN); // dress material body
        scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if(kchA.match(t)){
                    homeMenuAction();
                }
                else if(kcfA.match(t)){
                    fileMenuAction();
                }
                else if(kcvS.match(t)){
                    sareeMenuAction();
                }
                else if(kcdCS.match(t)){
                    dressMaterialMenuAction();
                }
                else if(kcqC.match(t)){
                    gaisarMenuAction();
                }
                else if(kcsA.match(t)){
                    
                }
                else if(kceC.match(t)){
                    exportFileAction();
                }
                else if(kctA.match(t)){
                    printTextureAction();
                }
                else if(kcmCS.match(t)){
                    simulation2DAction();
                }
                else if(kccA.match(t)){
                    clearMenuAction();
                }
                else if(kczA.match(t)){
                    repeatsMenuAction();
                }
                else if(kcjA.match(t)){
                    customLayoutMenuAction();
                }
                else if(kc1C.match(t)){
                    sareeLayout1Action();
                }
                else if(kc2C.match(t)){
                    sareeLayout2Action();
                }
                else if(kc3C.match(t)){
                    sareeLayout3Action();
                }
                else if(kc4C.match(t)){
                    sareeLayout4Action();
                }
                else if(kc5C.match(t)){
                    sareeLayout5Action();
                }
                else if(kc6C.match(t)){
                    stoleLayout1Action();
                }
                else if(kc7C.match(t)){
                    stoleLayout4Action();
                }
                else if(kc8C.match(t)){
                    stoleLayout2Action();
                }
                else if(kc9C.match(t)){
                    stoleLayout3Action();
                }
                else if(kcdAC.match(t)){
                    dressLayout1Action();
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
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ALERT"));
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 300, 100, Color.WHITE);
        scene.getStylesheets().add(ClothView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                saveContext();
                objConfiguration.setGarmentZoom(1.0);
                objConfiguration.setGarmentPinch(pinch);
                dialogStage.close();
                clothStage.close();
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
    
    public void sareeMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONSAREEMENU"));
        menuHighlight("SAREE");
    }
    
    public void dressMaterialMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONDRESSMENU"));
        menuHighlight("DRESS");
    }
    
    public void gaisarMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONGAISARBROCADEMENU"));
        menuHighlight("GAISARBROCADE"); 
    }
    
    public void exportFileAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONEXPORTFILE"));
        ScreenShoot("export");
    }
    
    public void printTextureAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONPRINTFILE"));
        ScreenShoot("print");
    }
    
    public void simulation2DAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONSIMULATION3DVIEW"));
        ScreenShoot("ClothSimulation");
    }
    
    public void clearMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONCLEAR"));
        clearContext();
        initDesign();
    }
    
    public void repeatsMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONREPEATS"));
        setRepeats();
    }
    
    public void sareeLayout1Action(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONBORDERACROSSBODY"));
        clothLayout("sareeLayout1");
    }
    
    public void sareeLayout2Action(){
        
    }
    
    public void sareeLayout3Action(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONSINGLECROSSBORDER"));
        clothLayout("sareeLayout3");
    }
    
    public void sareeLayout4Action(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONSKARTBORDER"));
        clothLayout("sareeLayout4");
    }
    
    public void sareeLayout5Action(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONBLOUSESAREE"));
        clothLayout("sareeLayout5");
    }
    
    public void customLayoutMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONCUSTOMCLOTH"));
        customLayout();
    }
    
    public void stoleLayout1Action(){
        enableAllComponents(false);
        leftBorderEnabled=true;
        rightBorderEnabled=true;
        leftPalluEnabled=true;
        rightPalluEnabled=true;
        recalculateBody();
        //setInitialWidthHeight();
        setComponentWidthHeight();
        plotFullCustomLayout();
    }
    
    public void stoleLayout2Action(){
        enableAllComponents(false);
        leftBorderEnabled=true;
        rightBorderEnabled=true;
        rightCrossBorderEnabled=true;
        rightsideLeftCrossBorderEnabled=true;
        leftPalluEnabled=true;
        rightPalluEnabled=true;
        recalculateBody();
        //setInitialWidthHeight();
        setComponentWidthHeight();
        plotFullCustomLayout();
    }
    
    public void stoleLayout3Action(){
        enableAllComponents(false);
        leftBorderEnabled=true;
        rightBorderEnabled=true;
        leftCrossBorderEnabled=true;
        rightsideRightCrossBorderEnabled=true;
        leftPalluEnabled=true;
        rightPalluEnabled=true;
        recalculateBody();
        //setInitialWidthHeight();
        setComponentWidthHeight();
        plotFullCustomLayout();
    }
    
    public void stoleLayout4Action(){
        enableAllComponents(false);
        leftBorderEnabled=true;
        rightBorderEnabled=true;
        recalculateBody();
        //setInitialWidthHeight();
        setComponentWidthHeight();
        plotFullCustomLayout();
    }
    
    public void dressLayout1Action(){
        enableAllComponents(false);
        recalculateBody();
        //setInitialWidthHeight();
        setComponentWidthHeight();
        plotFullCustomLayout();
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
        if(strMenu == "GAISARBROCADE"){
            fileMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            sareeMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            dressMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            stoleMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            dupattaMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            shawlMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            brocadeMenu.setStyle("-fx-background-color: linear-gradient(#FFD765,#FFFC92);");
            fileMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            sareeMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            dressMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            stoleMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            brocadeMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            dupattaMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            shawlMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            brocadeMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            toolBar.getItems().clear();
            populateBrocadeToolbar();
        } else if(strMenu == "SHAWL"){
            fileMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            sareeMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            dressMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            stoleMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            dupattaMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            shawlMenu.setStyle("-fx-background-color: linear-gradient(#FFD765,#FFFC92);");
            brocadeMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            fileMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            sareeMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            dressMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            stoleMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            dupattaMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            shawlMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            brocadeMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            toolBar.getItems().clear();
            populateShawlToolbar();
        } else if(strMenu == "DUPATTA"){
            fileMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            sareeMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            dressMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            stoleMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            dupattaMenu.setStyle("-fx-background-color: linear-gradient(#FFD765,#FFFC92);");
            shawlMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            brocadeMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            fileMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            sareeMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            dressMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            stoleMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            dupattaMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            shawlMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            brocadeMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            toolBar.getItems().clear();
            populateDupattaToolbar();
        } else if(strMenu == "STOLE"){
            fileMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            sareeMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            dressMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            stoleMenu.setStyle("-fx-background-color: linear-gradient(#FFD765,#FFFC92);");
            dupattaMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            shawlMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            brocadeMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            fileMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            sareeMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            dressMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            stoleMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            dupattaMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            shawlMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            brocadeMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            toolBar.getItems().clear();
            populateStoleToolbar();
        } else if(strMenu == "DRESS"){
            fileMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            sareeMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            dressMenu.setStyle("-fx-background-color: linear-gradient(#FFD765,#FFFC92);");
            stoleMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            dupattaMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            shawlMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            brocadeMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            fileMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            sareeMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            dressMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            stoleMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            dupattaMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            shawlMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            brocadeMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            toolBar.getItems().clear();
            populateDressToolbar();
        } else if(strMenu == "SAREE"){
            fileMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            sareeMenu.setStyle("-fx-background-color: linear-gradient(#FFD765,#FFFC92);");
            dressMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            stoleMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            dupattaMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            shawlMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            brocadeMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            fileMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            sareeMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            dressMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            stoleMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            dupattaMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            shawlMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            brocadeMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            toolBar.getItems().clear();
            populateSareeToolbar();
        } else{
            fileMenu.setStyle("-fx-background-color: linear-gradient(#FFD765,#FFFC92);");
            sareeMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            dressMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            stoleMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            dupattaMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            shawlMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            brocadeMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            fileMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            sareeMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            dressMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            stoleMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            dupattaMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            shawlMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            brocadeMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            toolBar.getItems().clear();
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
        // Open file item
        openFileVB = new VBox(); 
        Label openFileLbl= new Label(objDictionaryAction.getWord("OPENFILE"));
        openFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOPENFILE")));
        openFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/open.png"), openFileLbl);
        openFileVB.setPrefWidth(80);
        openFileVB.getStyleClass().addAll("VBox");
        // load recent file item
        loadFileVB = new VBox(); 
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
        // Save As file item
        saveAsFileVB = new VBox(); 
        Label saveAsFileLbl= new Label(objDictionaryAction.getWord("SAVEASFILE"));
        saveAsFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVEASFILE")));
        saveAsFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/save_as.png"), saveAsFileLbl);
        saveAsFileVB.setPrefWidth(80);
        saveAsFileVB.getStyleClass().addAll("VBox");
        // Save Texture file item
        saveTextureFileVB = new VBox(); 
        Label saveTextureFileLbl= new Label(objDictionaryAction.getWord("SAVETEXTUREFILE"));
        saveTextureFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVETEXTUREFILE")));
        saveTextureFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/export_texture_as.png"), saveTextureFileLbl);
        saveTextureFileVB.setPrefWidth(80);
        saveTextureFileVB.getStyleClass().addAll("VBox");
        // print File menu
        printFileVB = new VBox(); 
        Label printFileLbl= new Label(objDictionaryAction.getWord("PRINTTEXTUREFILE"));
        printFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPRINTTEXTUREFILE")));
        printFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/print.png"), printFileLbl);
        printFileVB.setPrefWidth(80);
        printFileVB.getStyleClass().addAll("VBox");
        // Simulation View menu
        simulationViewVB = new VBox(); 
        Label simulationViewLbl= new Label(objDictionaryAction.getWord("SIMULATION"));
        simulationViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSIMULATION")));
        simulationViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/simulation.png"), simulationViewLbl);
        simulationViewVB.setPrefWidth(80);
        simulationViewVB.getStyleClass().addAll("VBox");        
        // Simulation2D View item;
        //simulation2DViewVB = new VBox(); 
        //Label simulation2DViewLbl= new Label(objDictionaryAction.getWord("SIMULATION2DVIEW"));
        //simulation2DViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSIMULATION2DVIEW")));
        //simulation2DViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/2Dsimulation.png"), simulation2DViewLbl);
        //simulation2DViewVB.setPrefWidth(80);
        //simulation2DViewVB.getStyleClass().addAll("VBox");
        // Simulation3D View item;
        //simulation3DViewVB = new VBox(); 
        //Label simulation3DViewLbl= new Label(objDictionaryAction.getWord("SIMULATION3DVIEW"));
        //simulation3DViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSIMULATION3DVIEW")));
        //simulation3DViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/3Dsimulation.png"), simulation3DViewLbl);
        //simulation3DViewVB.setPrefWidth(80);
        //simulation3DViewVB.getStyleClass().addAll("VBox");
        // punch card File menu
        clearFileVB = new VBox();
        Label clearFileLbl= new Label(objDictionaryAction.getWord("CLEAR"));
        clearFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLEAR")));
        clearFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/clear.png"), clearFileLbl);
        clearFileVB.setPrefWidth(80);
        clearFileVB.getStyleClass().addAll("VBox"); 
        repeatsVB = new VBox();
        Label repeatsLbl= new Label(objDictionaryAction.getWord("REPEATS"));
        clearFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPREPEATS")));
        repeatsVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_body_only.png"), repeatsLbl);
        repeatsVB.setPrefWidth(80);
        repeatsVB.getStyleClass().addAll("VBox"); 
        
        // quit File menu
        //quitFileVB = new VBox(); 
        //Label quitFileLbl= new Label(objDictionaryAction.getWord("QUITFILE"));
        //quitFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPQUITFILE")));
        //quitFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/quit.png"), quitFileLbl);
        //quitFileVB.setPrefWidth(80);
        //quitFileVB.getStyleClass().addAll("VBox"); 
        //Add the action to Buttons.
        openFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONOPENFILE"));
                lblStatus.setText(objDictionaryAction.getWord("ACTIONOPENFILE"));
                objCloth = new Cloth();
                objCloth.setObjConfiguration(objConfiguration);
                ClothImportView objClothImportView= new ClothImportView(objCloth);
                if(objCloth.getStrClothId()!=null){
                    loadCloth(objCloth);
                }
                System.gc();
            }
        });
        loadFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONLOADFILE")); 
                ScreenShoot("load");
            }
        });
        saveFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEFILE"));
                isNew=false;
                saveUpdateCloth();
                //ScreenShoot("save");
            }
        });
        saveAsFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEASFILE"));
                isNew=true;
                saveUpdateCloth();
                //ScreenShoot("saveAs");
            }
        }); 
        saveTextureFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exportFileAction();                
            }
        });
        printFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                printTextureAction();
            }
        });
        simulationViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                simulation2DAction();
            }
        });
        /*simulation2DViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONSIMULATION2DVIEW"));
                ScreenShoot("Simulation");
            }
        });
        simulation3DViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONSIMULATION3DVIEW"));
                ScreenShoot("ClothSimulation");
            }
        });*/
        clearFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clearMenuAction();
            }
        });
        repeatsVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                repeatsMenuAction();
            }
        });
        /*quitFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONQUITFILE"));
                clothStage.close();
                System.gc();
                WindowView objWindowView = new WindowView(objConfiguration);
            }
        });*/
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        //openFileVB,loadFileVB,saveFileVB,saveAsFileVB,saveTextureFileVB,printFileVB,simulation2DViewVB,simulation3DViewVB,clearFileVB
        flow.getChildren().addAll(openFileVB,saveAsFileVB,saveTextureFileVB,printFileVB,simulationViewVB/*,simulation2DViewVB*/,clearFileVB, repeatsVB);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
    }
    
 /**
 * populateSareeToolbar
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
     private void populateSareeToolbar(){
        // New file item
        paluSareeVB = new VBox();
        Label paluSareeLbl= new Label(objDictionaryAction.getWord("BORDERACROSSSAREE"));
        paluSareeLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBORDERACROSSSAREE")));
        paluSareeVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_border_across_pallu.png"), paluSareeLbl);
        paluSareeVB.setPrefWidth(80);
        paluSareeVB.getStyleClass().addAll("VBox");    
        // Open file item
        borderSareeVB = new VBox(); 
        Label borderSareeLbl= new Label(objDictionaryAction.getWord("BORDERACROSSBODY"));
        borderSareeLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBORDERACROSSBODY")));
        borderSareeVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_pallu_accross_border.png"), borderSareeLbl);
        borderSareeVB.setPrefWidth(80);
        borderSareeVB.getStyleClass().addAll("VBox");
        // Open file item
        crossBorderSareeVB = new VBox(); 
        Label crossBorderSareeLbl= new Label(objDictionaryAction.getWord("SINGLECROSSBORDER"));
        crossBorderSareeLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSINGLECROSSBORDER")));
        crossBorderSareeVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_single_cross_border.png"), crossBorderSareeLbl);
        crossBorderSareeVB.setPrefWidth(80);
        crossBorderSareeVB.getStyleClass().addAll("VBox");
        // Open file item
        skartSareeVB = new VBox(); 
        Label skartSareeLbl= new Label(objDictionaryAction.getWord("SKARTBORDER"));
        skartSareeLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSKARTBORDER")));
        skartSareeVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_border_with_skart.png"), skartSareeLbl);
        skartSareeVB.setPrefWidth(80);
        skartSareeVB.getStyleClass().addAll("VBox");
        // Open file item
        blouseSareeVB = new VBox(); 
        Label blouseSareeLbl= new Label(objDictionaryAction.getWord("BLOUSESAREE"));
        blouseSareeLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBLOUSESAREE")));
        blouseSareeVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_with_blouse.png"), blouseSareeLbl);
        blouseSareeVB.setPrefWidth(80);
        blouseSareeVB.getStyleClass().addAll("VBox");
        // Open file item
        koniaSareeVB = new VBox(); 
        Label koniaSareeLbl= new Label(objDictionaryAction.getWord("KONIASAREE"));
        koniaSareeLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPKONIASAREE")));
        koniaSareeVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_single_cross_border.png"), koniaSareeLbl);
        koniaSareeVB.setPrefWidth(80);
        koniaSareeVB.getStyleClass().addAll("VBox");
        // custom layout item
        customVB = new VBox(); 
        Label customLbl= new Label(objDictionaryAction.getWord("CUSTOMCLOTH"));
        customLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCUSTOMCLOTH")));
        customVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_custom.png"), customLbl);
        customVB.setPrefWidth(80);
        customVB.getStyleClass().addAll("VBox");
		// full view
        fullViewVB = new VBox();
        Label fullViewLbl= new Label(objDictionaryAction.getWord("FULLVIEW"));
        fullViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFULLVIEW")));
        fullViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_layout.png"), fullViewLbl);
        fullViewVB.setPrefWidth(80);
        fullViewVB.getStyleClass().addAll("VBox"); 
        //Add the action to Buttons.
        paluSareeVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sareeLayout1Action();
            }
        });        
        borderSareeVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONBORDERACROSSSAREE"));
                clothLayout("sareeLayout2");
            }
        });
        crossBorderSareeVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sareeLayout3Action();
            }
        });
        skartSareeVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sareeLayout4Action();
            }
        });
        blouseSareeVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sareeLayout5Action();
            }
        });
        koniaSareeVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONKONIASAREE"));
                clothLayout("sareeLayout6");
            }
        });
        customVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCUSTOMCLOTH"));
                //clothLayout("");
                customLayout();
            }
        });
        fullViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONFULLVIEW"));
                clothLayout("FullView");
            }
        });
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(paluSareeVB/*,borderSareeVB*/,crossBorderSareeVB,skartSareeVB,blouseSareeVB,customVB/*, fullViewVB*/);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
        
        if(!currentClothMode.equalsIgnoreCase("SAREE")){
            currentClothMode = "SAREE";
            clothLayout("sareeLayout1");
        }
     }
     
 /**
 * populateDressToolbar
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
     private void populateDressToolbar(){
        // New file item
        bodyDressVB = new VBox();
        Label bodyDressLbl= new Label(objDictionaryAction.getWord("BODYDRESS"));
        bodyDressLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBODYDRESS")));
        bodyDressVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_body_only.png"), bodyDressLbl);
        bodyDressVB.setPrefWidth(80);
        bodyDressVB.getStyleClass().addAll("VBox");    
        // Open file item
        borderSingleDressVB = new VBox(); 
        Label borderSingleDressLbl= new Label(objDictionaryAction.getWord("BORDERSINGLEDRESS"));
        borderSingleDressLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBORDERSINGLEDRESS")));
        borderSingleDressVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_body_with_single_border.png"), borderSingleDressLbl);
        borderSingleDressVB.setPrefWidth(80);
        borderSingleDressVB.getStyleClass().addAll("VBox");
        // Open file item
        borderDressVB = new VBox(); 
        Label borderDressLbl= new Label(objDictionaryAction.getWord("BORDERDRESS"));
        borderDressLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBORDERDRESS")));
        borderDressVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_body_with_borders.png"), borderDressLbl);
        borderDressVB.setPrefWidth(80);
        borderDressVB.getStyleClass().addAll("VBox");
        // custom layout item
        customVB = new VBox(); 
        Label customLbl= new Label(objDictionaryAction.getWord("CUSTOMCLOTH"));
        customLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCUSTOMCLOTH")));
        customVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_custom.png"), customLbl);
        customVB.setPrefWidth(80);
        customVB.getStyleClass().addAll("VBox");
		// full view
        fullViewVB = new VBox();
        Label fullViewLbl= new Label(objDictionaryAction.getWord("FULLVIEW"));
        fullViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFULLVIEW")));
        fullViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_layout.png"), fullViewLbl);
        fullViewVB.setPrefWidth(80);
        fullViewVB.getStyleClass().addAll("VBox"); 
        //Add the action to Buttons.
        bodyDressVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONBODYDRESS"));
                clothLayout("dressLayout1");
            }
        });        
        borderSingleDressVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONBORDERDRESS"));
                clothLayout("dressLayout2");
            }
        });
        borderDressVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONBORDERSINGLEDRESS"));
                clothLayout("dressLayout3");
            }
        });
        customVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCUSTOMCLOTH"));
                //clothLayout("");
                otherMaterialCustomLayout();
            }
        });
		fullViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONFULLVIEW"));
                clothLayout("FullView");
            }
        });
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(bodyDressVB,borderSingleDressVB,borderDressVB,customVB/*, fullViewVB*/);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
        
        if(!currentClothMode.equalsIgnoreCase("DRESS")){
            currentClothMode = "DRESS";
            clothLayout("dressLayout1");
        }
     }
 
     /**
 * populateStoleToolbar
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
     private void populateStoleToolbar(){
        // New file item
        bodyStoleVB = new VBox();
        Label bodyStoleLbl= new Label(objDictionaryAction.getWord("BODYSTOLE"));
        bodyStoleLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBODYSTOLE")));
        bodyStoleVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_body_without_crossborder.png"), bodyStoleLbl);
        bodyStoleVB.setPrefWidth(80);
        bodyStoleVB.getStyleClass().addAll("VBox");    
        // New file item
        paluStoleVB = new VBox();
        Label paluStoleLbl= new Label(objDictionaryAction.getWord("PALUSTOLE"));
        paluStoleLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPALUSTOLE")));
        paluStoleVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_without_pallu.png"), paluStoleLbl);
        paluStoleVB.setPrefWidth(80);
        paluStoleVB.getStyleClass().addAll("VBox");    
        // Open file item
        crossBorderLeftStoleVB = new VBox(); 
        Label crossBorderLeftStoleLbl= new Label(objDictionaryAction.getWord("CROSSBORDERLEFTSTOLE"));
        crossBorderLeftStoleLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCROSSBORDERLEFTSTOLE")));
        crossBorderLeftStoleVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_inner_cross_border.png"), crossBorderLeftStoleLbl);
        crossBorderLeftStoleVB.setPrefWidth(80);
        crossBorderLeftStoleVB.getStyleClass().addAll("VBox");
        // Open file item
        crossBorderRightStoleVB = new VBox(); 
        Label crossBorderRightStoleLbl= new Label(objDictionaryAction.getWord("CROSSBORDERRIGHTSTOLE"));
        crossBorderRightStoleLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCROSSBORDERRIGHTSTOLE")));
        crossBorderRightStoleVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_outer_cross_border.png"), crossBorderRightStoleLbl);
        crossBorderRightStoleVB.setPrefWidth(80);
        crossBorderRightStoleVB.getStyleClass().addAll("VBox");
        // custom layout item
        customVB = new VBox(); 
        Label customLbl= new Label(objDictionaryAction.getWord("CUSTOMCLOTH"));
        customLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCUSTOMCLOTH")));
        customVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_custom.png"), customLbl);
        customVB.setPrefWidth(80);
        customVB.getStyleClass().addAll("VBox");
		// full view
        fullViewVB = new VBox();
        Label fullViewLbl= new Label(objDictionaryAction.getWord("FULLVIEW"));
        fullViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFULLVIEW")));
        fullViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_layout.png"), fullViewLbl);
        fullViewVB.setPrefWidth(80);
        fullViewVB.getStyleClass().addAll("VBox"); 
        //Add the action to Buttons.
        bodyStoleVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONBODYSTOLE"));
                clothLayout("stoleLayout1");
            }
        });        
        crossBorderLeftStoleVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCROSSBORDERLEFTSTOLE"));
                clothLayout("stoleLayout2");
            }
        });        
        crossBorderRightStoleVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCROSSBORDERRIGHTSTOLE"));
                clothLayout("stoleLayout3");
            }
        });        
        customVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCUSTOMCLOTH"));
                //clothLayout("");
                otherMaterialCustomLayout();
            }
        });
        paluStoleVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONPALUSTOLE"));
                clothLayout("stoleLayout4");
            }
        });
        fullViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONFULLVIEW"));
                clothLayout("FullView");
            }
        });   
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(bodyStoleVB,paluStoleVB,crossBorderLeftStoleVB,crossBorderRightStoleVB,customVB/*, fullViewVB*/);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
        
        if(!currentClothMode.equalsIgnoreCase("STOLE")){
            currentClothMode = "STOLE";
            clothLayout("stoleLayout1");
        }
     }
     
     /**
 * populateDressToolbar
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
     private void populateDupattaToolbar(){
        // New file item
        bodyDupattaVB = new VBox();
        Label bodyDupattaLbl= new Label(objDictionaryAction.getWord("BODYDUPATTA"));
        bodyDupattaLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBODYDUPATTA")));
        bodyDupattaVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_body_without_crossborder.png"), bodyDupattaLbl);
        bodyDupattaVB.setPrefWidth(80);
        bodyDupattaVB.getStyleClass().addAll("VBox");    
        // New file item
        paluDupattaVB = new VBox();
        Label paluDupattaLbl= new Label(objDictionaryAction.getWord("PALUDUPATTA"));
        paluDupattaLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPALUDUPATTA")));
        paluDupattaVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_without_pallu.png"), paluDupattaLbl);
        paluDupattaVB.setPrefWidth(80);
        paluDupattaVB.getStyleClass().addAll("VBox");    
        // Open file item
        crossBorderLeftDupattaVB = new VBox(); 
        Label crossBorderLeftDupattaLbl= new Label(objDictionaryAction.getWord("CROSSBORDERLEFTDUPATTA"));
        crossBorderLeftDupattaLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCROSSBORDERLEFTDUPATTA")));
        crossBorderLeftDupattaVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_inner_cross_border.png"), crossBorderLeftDupattaLbl);
        crossBorderLeftDupattaVB.setPrefWidth(80);
        crossBorderLeftDupattaVB.getStyleClass().addAll("VBox");
        // Open file item
        crossBorderRightDupattaVB = new VBox(); 
        Label crossBorderRightDupattaLbl= new Label(objDictionaryAction.getWord("CROSSBORDERRIGHTDUPATTA"));
        crossBorderRightDupattaLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCROSSBORDERRIGHTDUPATTA")));
        crossBorderRightDupattaVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_outer_cross_border.png"), crossBorderRightDupattaLbl);
        crossBorderRightDupattaVB.setPrefWidth(80);
        crossBorderRightDupattaVB.getStyleClass().addAll("VBox");
        // custom layout item
        customVB = new VBox(); 
        Label customLbl= new Label(objDictionaryAction.getWord("CUSTOMCLOTH"));
        customLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCUSTOMCLOTH")));
        customVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_custom.png"), customLbl);
        customVB.setPrefWidth(80);
        customVB.getStyleClass().addAll("VBox");
		// full view
        fullViewVB = new VBox();
        Label fullViewLbl= new Label(objDictionaryAction.getWord("FULLVIEW"));
        fullViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFULLVIEW")));
        fullViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_layout.png"), fullViewLbl);
        fullViewVB.setPrefWidth(80);
        fullViewVB.getStyleClass().addAll("VBox"); 
        //Add the action to Buttons.
        bodyDupattaVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONBODYDUPATTA"));
                clothLayout("dupattaLayout1");
            }
        });        
        crossBorderLeftDupattaVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCRSSBORDERLEFTDUPATTA"));
                clothLayout("dupttaLayout2");
            }
        });        
        crossBorderRightDupattaVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCRSSBORDERRIGHTDUPATTA"));
                clothLayout("dupattaLayout3");
            }
        });        
        paluDupattaVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONPALUDUPATTA"));
                clothLayout("dupattaLayout4");
            }
        });        
        customVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCUSTOMCLOTH"));
                //clothLayout("");
                otherMaterialCustomLayout();
            }
        });
        fullViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONFULLVIEW"));
                clothLayout("FullView");
            }
        });
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(bodyDupattaVB,paluDupattaVB,crossBorderLeftDupattaVB,crossBorderRightDupattaVB,customVB/*, fullViewVB*/);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
        
        if(!currentClothMode.equalsIgnoreCase("DUPATTA")){
            currentClothMode = "DUPATTA";
            clothLayout("dupattaLayout1");
        }
     }

/**
 * populateShawlToolbar
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
     private void populateShawlToolbar(){
        // New file item
        bodyShawlVB = new VBox();
        Label bodyShawlLbl= new Label(objDictionaryAction.getWord("BODYSHAWL"));
        bodyShawlLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBODYSHAWL")));
        bodyShawlVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_body_only.png"), bodyShawlLbl);
        bodyShawlVB.setPrefWidth(80);
        bodyShawlVB.getStyleClass().addAll("VBox");    
        // custom layout item
        customVB = new VBox(); 
        Label customLbl= new Label(objDictionaryAction.getWord("CUSTOMCLOTH"));
        customLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCUSTOMCLOTH")));
        customVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_custom.png"), customLbl);
        customVB.setPrefWidth(80);
        customVB.getStyleClass().addAll("VBox");
		// full view
        fullViewVB = new VBox();
        Label fullViewLbl= new Label(objDictionaryAction.getWord("FULLVIEW"));
        fullViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFULLVIEW")));
        fullViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_layout.png"), fullViewLbl);
        fullViewVB.setPrefWidth(80);
        fullViewVB.getStyleClass().addAll("VBox"); 
        //Add the action to Buttons.
        bodyShawlVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONBODYSHAWL"));
                clothLayout("shawlLayout1");
            }
        });
        customVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCUSTOMCLOTH"));
                //clothLayout("");
                otherMaterialCustomLayout();
            }
        });
		fullViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONFULLVIEW"));
                clothLayout("FullView");
            }
        });
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(bodyShawlVB,customVB/*, fullViewVB*/);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
        
        if(!currentClothMode.equalsIgnoreCase("SHAWL")){
            currentClothMode = "SHAWL";
            clothLayout("shawlLayout1");
        }
     }
     
/**
 * populateBrocadeToolbar
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
     private void populateBrocadeToolbar(){
        // New file item
        bodyBrocadeVB = new VBox();
        Label bodyBrocadeLbl= new Label(objDictionaryAction.getWord("BODYBROCADE"));
        bodyBrocadeLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBODYBROCADE")));
        bodyBrocadeVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_body_only.png"), bodyBrocadeLbl);
        bodyBrocadeVB.setPrefWidth(80);
        bodyBrocadeVB.getStyleClass().addAll("VBox");    
        // custom layout item
        customVB = new VBox(); 
        Label customLbl= new Label(objDictionaryAction.getWord("CUSTOMCLOTH"));
        customLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCUSTOMCLOTH")));
        customVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_custom.png"), customLbl);
        customVB.setPrefWidth(80);
        customVB.getStyleClass().addAll("VBox");
		// full view
        fullViewVB = new VBox();
        Label fullViewLbl= new Label(objDictionaryAction.getWord("FULLVIEW"));
        fullViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFULLVIEW")));
        fullViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/cloth_layout.png"), fullViewLbl);
        fullViewVB.setPrefWidth(80);
        fullViewVB.getStyleClass().addAll("VBox"); 
        //Add the action to Buttons.
        bodyBrocadeVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONBODYBROCADE"));
                clothLayout("brocadeLayout1");
            }
        });
        customVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCUSTOMCLOTH"));
                //clothLayout("");
                otherMaterialCustomLayout();
            }
        });
        fullViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONFULLVIEW"));
                clothLayout("FullView");
            }
        });
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(bodyBrocadeVB,customVB/*, fullViewVB*/);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
        
        if(!currentClothMode.equalsIgnoreCase("BROCADE")){
            currentClothMode = "BROCADE";
            clothLayout("brocadeLayout1");
        }
     }
     
     private void initDesign(){
        lefttopKoniaPane = new ScrollPane();
        leftbottomKoniaPane = new ScrollPane();
        righttopKoniaPane = new ScrollPane();
        rightbottomKoniaPane = new ScrollPane();
        leftCrossBorderPane = new ScrollPane();
        rightCrossBorderPane = new ScrollPane();
        leftBorderPane = new ScrollPane();
        rightBorderPane = new ScrollPane();
        skartPane = new ScrollPane();
        blousePane = new ScrollPane();
        rightsideLeftCrossBorderPane=new ScrollPane();
        rightsideRightCrossBorderPane=new ScrollPane();
        leftPalluPane = new ScrollPane();
        rightPalluPane = new ScrollPane();
        bodyPane = new ScrollPane();
        leftBorderPane.setMinWidth(5);
        leftBorderPane.setMinHeight(5);
        rightBorderPane.setMinWidth(5);
        rightBorderPane.setMinHeight(5);
        leftCrossBorderPane.setMinWidth(5);
        leftCrossBorderPane.setMinHeight(5);
        rightCrossBorderPane.setMinWidth(5);
        rightCrossBorderPane.setMinHeight(5);
        leftPalluPane.setMinWidth(5);
        leftPalluPane.setMinHeight(5);
        rightPalluPane.setMinWidth(5);
        rightPalluPane.setMinHeight(5);
        blousePane.setMinWidth(5);
        blousePane.setMinHeight(5);
        skartPane.setMinWidth(5);
        skartPane.setMinHeight(5);
        bodyPane.setMinWidth(5);
        bodyPane.setMinHeight(5);
        rightsideLeftCrossBorderPane.setMinWidth(5);
        rightsideLeftCrossBorderPane.setMinHeight(5);
        rightsideRightCrossBorderPane.setMinWidth(5);
        rightsideRightCrossBorderPane.setMinHeight(5);
                
        lefttopKoniaPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        leftbottomKoniaPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        righttopKoniaPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rightbottomKoniaPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        leftCrossBorderPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rightCrossBorderPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        leftBorderPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rightBorderPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        skartPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        blousePane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        leftPalluPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rightPalluPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        bodyPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rightsideLeftCrossBorderPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rightsideRightCrossBorderPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        lefttopKoniaPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        leftbottomKoniaPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        righttopKoniaPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rightbottomKoniaPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        leftCrossBorderPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rightCrossBorderPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        leftBorderPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rightBorderPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        skartPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        blousePane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        leftPalluPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rightPalluPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        bodyPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rightsideLeftCrossBorderPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rightsideRightCrossBorderPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        componentContextMenu(lefttopKoniaPane,"Konia");
        componentContextMenu(leftbottomKoniaPane,"Konia");
        componentContextMenu(righttopKoniaPane,"Konia");
        componentContextMenu(rightbottomKoniaPane,"Konia");
        componentContextMenu(leftCrossBorderPane,"Cross Border");
        componentContextMenu(rightCrossBorderPane,"Cross Border");
        componentContextMenu(leftBorderPane,"Border");
        componentContextMenu(rightBorderPane,"Border");
        componentContextMenu(skartPane,"Skart");
        componentContextMenu(blousePane,"Blouse");
        componentContextMenu(leftPalluPane,"Palu");
        componentContextMenu(rightPalluPane,"Palu");
        componentContextMenu(bodyPane,"Body");
        componentContextMenu(rightsideLeftCrossBorderPane,"Cross Border");
        componentContextMenu(rightsideRightCrossBorderPane,"Cross Border");
        
        lefttopKonia = new ImageView("/media/cloth_konia.png");
        leftbottomKonia = new ImageView("/media/cloth_konia.png");
        righttopKonia = new ImageView("/media/cloth_konia.png");
        rightbottomKonia = new ImageView("/media/cloth_konia.png");
        leftCrossBorder = new ImageView("/media/cloth_crossborder.png");
        rightCrossBorder = new ImageView("/media/cloth_crossborder.png");
        leftBorder = new ImageView("/media/cloth_border.png");
        rightBorder = new ImageView("/media/cloth_border.png");
        skart = new ImageView("/media/cloth_skart.png");
        blouse = new ImageView("/media/cloth_blouse.png");
        leftPallu = new ImageView("/media/cloth_pallu.png");
        rightPallu = new ImageView("/media/cloth_pallu.png");
        body = new ImageView("/media/cloth_body.png");
        rightsideLeftCrossBorder = new ImageView("/media/cloth_crossborder.png");
        rightsideRightCrossBorder = new ImageView("/media/cloth_crossborder.png");
        
        isPaneDragable=false;
        xInitialPane=0;
        yInitialPane=0;
        
        //componentDragAndSave(lefttopKonia);
        //componentDragAndSave(leftbottomKonia);
        //componentDragAndSave(righttopKonia);
        //componentDragAndSave(rightbottomKonia);
        componentDragAndSave(rightsideLeftCrossBorder);
        componentDragAndSave(rightsideRightCrossBorder);
        componentDragAndSave(leftCrossBorder);
        componentDragAndSave(rightCrossBorder);
        componentDragAndSave(leftBorder);
        componentDragAndSave(rightBorder);
        componentDragAndSave(skart);
        componentDragAndSave(blouse);
        componentDragAndSave(leftPallu);
        componentDragAndSave(rightPallu);
        componentDragAndSave(body);
        
        lefttopKonia.setId(null);
        leftbottomKonia.setId(null);
        righttopKonia.setId(null);
        rightbottomKonia.setId(null);
        leftCrossBorder.setId(null);
        rightCrossBorder.setId(null);
        leftBorder.setId(null);
        rightBorder.setId(null);
        skart.setId(null);
        blouse.setId(null);
        leftPallu.setId(null);
        rightPallu.setId(null);
        body.setId(null);
        rightsideLeftCrossBorder.setId(null);
        rightsideRightCrossBorder.setId(null);
        
        lefttopKoniaPane.setContent(lefttopKonia);
        leftbottomKoniaPane.setContent(leftbottomKonia);
        righttopKoniaPane.setContent(lefttopKonia);
        rightbottomKoniaPane.setContent(leftbottomKonia);
        leftCrossBorderPane.setContent(leftCrossBorder);
        rightCrossBorderPane.setContent(rightCrossBorder);
        leftBorderPane.setContent(leftBorder);
        rightBorderPane.setContent(rightBorder);
        skartPane.setContent(skart);
        blousePane.setContent(blouse);
        leftPalluPane.setContent(leftPallu);
        rightPalluPane.setContent(rightPallu);
        bodyPane.setContent(body);
        rightsideLeftCrossBorderPane.setContent(rightsideLeftCrossBorder);
        rightsideRightCrossBorderPane.setContent(rightsideRightCrossBorder);
        
        if(currentClothMode.equalsIgnoreCase("SAREE")){
            clothLayout("sareeLayout1");
        } else if(currentClothMode.equalsIgnoreCase("DRESS")){
            clothLayout("dressLayout1");
        } else if(currentClothMode.equalsIgnoreCase("SHAWL")){
            clothLayout("shawlLayout1");
        } else if(currentClothMode.equalsIgnoreCase("BROCADE")){
            clothLayout("brocadeLayout1");
        } else
            clothLayout("sareeLayout1");
        
        /*if(objConfiguration.getMapRecentFabric().get("Konia")!=null){
            objFabric = new Fabric();
            objFabric.setObjConfiguration(objConfiguration);
            objFabric.setStrSearchBy("Konia");
            objFabric.setStrFabricID(objConfiguration.getMapRecentFabric().get("Konia"));
            //assignFabric(lefttopKonia,objFabric);
            objFabric = null;
        }else{
            lefttopKonia.setImage(new Image("/media/cloth_konia.png"));
            leftbottomKonia.setImage(new Image("/media/cloth_konia.png"));
            lefttopKonia.setId(null);
            leftbottomKonia.setId(null);
        } 
        if(objConfiguration.getMapRecentFabric().get("Cross Border")!=null){
            objFabric = new Fabric();
            objFabric.setObjConfiguration(objConfiguration);
            objFabric.setStrSearchBy("Cross Border");
            objFabric.setStrFabricID(objConfiguration.getMapRecentFabric().get("Cross Border"));
            try{
                FabricAction objFabricAction = new FabricAction();
                objFabricAction.getFabric(objFabric);
            }
            catch(Exception ex){}
            assignFabricToComponent(leftCrossBorder,objFabric);
            objFabric = null;
        }else{
            leftCrossBorder.setImage(new Image("/media/cloth_crossborder.png"));
            rightCrossBorder.setImage(new Image("/media/cloth_crossborder.png"));
            leftCrossBorder.setId(null);
            rightCrossBorder.setId(null);
        } 
        if(objConfiguration.getMapRecentFabric().get("Border")!=null){
            objFabric = new Fabric();
            objFabric.setObjConfiguration(objConfiguration);
            objFabric.setStrSearchBy("Border");
            objFabric.setStrFabricID(objConfiguration.getMapRecentFabric().get("Border"));
            try{
                FabricAction objFabricAction = new FabricAction();
                objFabricAction.getFabric(objFabric);
            }
            catch(Exception ex){}
            assignFabricToComponent(leftBorder,objFabric);
            objFabric = null;
        }else{
            leftBorder.setImage(new Image("/media/cloth_border.png"));
            rightBorder.setImage(new Image("/media/cloth_border.png"));
            leftBorder.setId(null);
            rightBorder.setId(null);
        }
        if(objConfiguration.getMapRecentFabric().get("Skart")!=null){
            objFabric = new Fabric();
            objFabric.setObjConfiguration(objConfiguration);
            objFabric.setStrSearchBy("Skart");
            objFabric.setStrFabricID(objConfiguration.getMapRecentFabric().get("Skart"));
            try{
                FabricAction objFabricAction = new FabricAction();
                objFabricAction.getFabric(objFabric);
            }
            catch(Exception ex){}
            assignFabricToComponent(skart,objFabric);
            objFabric = null;
        }else{
            skart.setImage(new Image("/media/cloth_skart.png"));
            skart.setId(null);
        }
        if(objConfiguration.getMapRecentFabric().get("Blouse")!=null){
            objFabric = new Fabric();
            objFabric.setObjConfiguration(objConfiguration);
            objFabric.setStrSearchBy("Blouse");
            objFabric.setStrFabricID(objConfiguration.getMapRecentFabric().get("Blouse"));
            try{
                FabricAction objFabricAction = new FabricAction();
                objFabricAction.getFabric(objFabric);
            }
            catch(Exception ex){}
            assignFabricToComponent(blouse,objFabric);
            objFabric = null;
        }else{
            blouse.setImage(new Image("/media/cloth_blouse.png"));
            blouse.setId(null);
        }
        if(objConfiguration.getMapRecentFabric().get("Palu")!=null){
            objFabric = new Fabric();
            objFabric.setObjConfiguration(objConfiguration);
            objFabric.setStrSearchBy("Palu");
            objFabric.setStrFabricID(objConfiguration.getMapRecentFabric().get("Palu"));
            try{
                FabricAction objFabricAction = new FabricAction();
                objFabricAction.getFabric(objFabric);
            }
            catch(Exception ex){}
            assignFabricToComponent(leftPallu,objFabric);
            objFabric = null;
        }else{
            leftPallu.setImage(new Image("/media/cloth_pallu.png"));
            rightPallu.setImage(new Image("/media/cloth_pallu.png"));
            leftPallu.setId(null);
        }
        if(objConfiguration.getMapRecentFabric().get("Body")!=null){
            objFabric = new Fabric();
            objFabric.setObjConfiguration(objConfiguration);
            objFabric.setStrSearchBy("Body");
            objFabric.setStrFabricID(objConfiguration.getMapRecentFabric().get("Body"));
            try{
                FabricAction objFabricAction = new FabricAction();
                objFabricAction.getFabric(objFabric);
            }
            catch(Exception ex){}
            assignFabricToComponent(body,objFabric);
            objFabric = null;
        }else{
            body.setImage(new Image("/media/cloth_body.png"));
            body.setId(null);
        }*/
        
        /*componentAddMouseEvent(leftCrossBorderPane);
        componentAddMouseEvent(rightCrossBorderPane);
        componentAddMouseEvent(leftBorderPane);
        componentAddMouseEvent(rightBorderPane);
        componentAddMouseEvent(skartPane);
        componentAddMouseEvent(blousePane);
        componentAddMouseEvent(leftPalluPane);
        componentAddMouseEvent(rightPalluPane);
        componentAddMouseEvent(bodyPane);
        componentAddMouseEvent(rightsideLeftCrossBorderPane);
        componentAddMouseEvent(rightsideRightCrossBorderPane);*/
        
        // Border Drag Code
        /*containerGP.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(!isResizeCursor())
                    return;
                drag=true;
                double x=event.getX();
                double y=event.getY();
                upper.clear();
                lower.clear();
                left.clear();
                right.clear();
                int upperIndex=-1, lowerIndex=-1, rightIndex=-1, leftIndex=-1;
                int childCount=containerGP.getChildren().size();
                if(BorderX){
                    // upper side component
                    for(int a=0; a<childCount; a++){
                        ScrollPane comp=(ScrollPane) containerGP.getChildren().get(a);
                        // Y
                        // disable border detection for lowmost component
                        if(comp.getBoundsInParent().getMaxY()!=(containerGP.getHeight()-1)){
                            // detecting component at upper side of border
                            if((y >= comp.getBoundsInParent().getMaxY() - MARGIN)
                                    && (y <= comp.getBoundsInParent().getMaxY() + MARGIN)
                                    && (x >= comp.getBoundsInParent().getMinX())
                                    && (x <= comp.getBoundsInParent().getMaxX())){
                                upper.clear();
                                upperIndex=a;
                                upper.add(comp);
                                break;
                            }
                        }
                    }
                    // lower side component
                    for(int a=0; a<childCount; a++){
                        ScrollPane comp=(ScrollPane) containerGP.getChildren().get(a);
                        // Y
                        // disable border detection for topmost component
                        if(comp.getBoundsInParent().getMinY()!=0){
                            // detecting component at lower side of border
                            if((y >= comp.getBoundsInParent().getMinY() - MARGIN)
                                    && (y <= comp.getBoundsInParent().getMinY() + MARGIN)
                                    && (x >= comp.getBoundsInParent().getMinX())
                                    && (x <= comp.getBoundsInParent().getMaxX())){
                                lower.clear();
                                lowerIndex=a;
                                lower.add(comp);
                                break;
                            }
                        }
                    }
                    
                    // now both upper and lower should have one element each
                    if(!upper.isEmpty()&&!lower.isEmpty()){
                        double upperX=((ScrollPane)upper.get(0)).getBoundsInParent().getMaxX()-((ScrollPane)upper.get(0)).getBoundsInParent().getMinX();
                        double lowerX=((ScrollPane)lower.get(0)).getBoundsInParent().getMaxX()-((ScrollPane)lower.get(0)).getBoundsInParent().getMinX();
                        if(upperX>lowerX){ // upper component spans longer
                            for(int a=0; a<childCount; a++){
                                if(a==lowerIndex)
                                    continue;
                                ScrollPane comp=(ScrollPane) containerGP.getChildren().get(a);
                                // disable border detection for topmost component
                                if(comp.getBoundsInParent().getMinY()!=0){
                                    // detecting component at lower side of border
                                    if((y >= comp.getBoundsInParent().getMinY() - MARGIN)
                                            && (y <= comp.getBoundsInParent().getMinY() + MARGIN)
                                            && (comp.getBoundsInParent().getMinX()>=((ScrollPane)upper.get(0)).getBoundsInParent().getMinX())
                                            && (comp.getBoundsInParent().getMaxX()<=((ScrollPane)upper.get(0)).getBoundsInParent().getMaxX())){
                                        lower.add(comp);
                                    }
                                }
                            }
                        }
                        else{ // lower component spans longer
                            for(int a=0; a<childCount; a++){
                                if(a==upperIndex)
                                    continue;
                                ScrollPane comp=(ScrollPane) containerGP.getChildren().get(a);
                                // disable border detection for lowmost component
                                if(comp.getBoundsInParent().getMaxY()!=(containerGP.getHeight()-1)){
                                    // detecting component at upper side of border
                                    if((y >= comp.getBoundsInParent().getMaxY() - MARGIN)
                                            && (y <= comp.getBoundsInParent().getMaxY() + MARGIN)
                                            && (comp.getBoundsInParent().getMinX()>=((ScrollPane)lower.get(0)).getBoundsInParent().getMinX())
                                            && (comp.getBoundsInParent().getMaxX()<=((ScrollPane)lower.get(0)).getBoundsInParent().getMaxX())){
                                        upper.add(comp);
                                    }
                                }
                            }
                        }
                    }
                }
                else if(BorderY){
                    // left side component
                    for(int a=0; a<childCount; a++){
                        ScrollPane comp=(ScrollPane) containerGP.getChildren().get(a);
                        // X
                        // disable border detection for rightside component
                        if(comp.getBoundsInParent().getMaxX()!=(containerGP.getWidth()-1)){
                            // detecting component at left side of border
                            if((x >= comp.getBoundsInParent().getMaxX() - MARGIN)
                                    && (x <= comp.getBoundsInParent().getMaxX() + MARGIN)
                                    && (y >= comp.getBoundsInParent().getMinY())
                                    && (y <= comp.getBoundsInParent().getMaxY())){
                                left.clear();
                                leftIndex=a;
                                left.add(comp);
                                break;
                            }
                        }
                    }
                    // right side component
                    for(int a=0; a<childCount; a++){
                        ScrollPane comp=(ScrollPane) containerGP.getChildren().get(a);
                        // X 
                        // disable border detection for leftmost component
                        if(comp.getBoundsInParent().getMinX()!=0){
                            // detecting component at right side of border
                            if((x >= comp.getBoundsInParent().getMinX() - MARGIN)
                                    && (x <= comp.getBoundsInParent().getMinX() + MARGIN)
                                    && (y >= comp.getBoundsInParent().getMinY())
                                    && (y <= comp.getBoundsInParent().getMaxY())){
                                right.clear();
                                rightIndex=a;
                                right.add(comp);
                                break;
                            }
                        }
                    }
                    
                    // now both upper and lower should have one element each
                    if(!left.isEmpty()&&!right.isEmpty()){
                        double leftY=((ScrollPane)left.get(0)).getBoundsInParent().getMaxY()-((ScrollPane)left.get(0)).getBoundsInParent().getMinY();
                        double rightY=((ScrollPane)right.get(0)).getBoundsInParent().getMaxY()-((ScrollPane)right.get(0)).getBoundsInParent().getMinY();
                        if(leftY>rightY){ // left component spans longer
                            for(int a=0; a<childCount; a++){
                                if(a==rightIndex)
                                    continue;
                                ScrollPane comp=(ScrollPane) containerGP.getChildren().get(a);
                                // disable border detection for leftmost component
                                if(comp.getBoundsInParent().getMinX()!=0){
                                    // detecting component at right side of border
                                    if((x >= comp.getBoundsInParent().getMinX() - MARGIN)
                                            && (x <= comp.getBoundsInParent().getMinX() + MARGIN)
                                            && (comp.getBoundsInParent().getMinY()>=((ScrollPane)left.get(0)).getBoundsInParent().getMinY())
                                            && (comp.getBoundsInParent().getMaxY()<=((ScrollPane)left.get(0)).getBoundsInParent().getMaxY())){
                                        right.add(comp);
                                    }
                                }
                            }
                        }
                        else{ // right component spans longer
                            for(int a=0; a<childCount; a++){
                                if(a==leftIndex)
                                    continue;
                                ScrollPane comp=(ScrollPane) containerGP.getChildren().get(a);
                                // X
                                // disable border detection for rightside component
                                if(comp.getBoundsInParent().getMaxX()!=(containerGP.getWidth()-1)){
                                    // detecting component at left side of border
                                    if((x >= comp.getBoundsInParent().getMaxX() - MARGIN)
                                            && (x <= comp.getBoundsInParent().getMaxX() + MARGIN)
                                            && (comp.getBoundsInParent().getMinY()>=((ScrollPane)right.get(0)).getBoundsInParent().getMinY())
                                            && (comp.getBoundsInParent().getMaxY()>=((ScrollPane)right.get(0)).getBoundsInParent().getMaxY())){
                                        left.add(comp);
                                    }
                                }
                            }
                        }
                    }
                }
                xGP=x;
                yGP=y;
            }
        });
        
        containerGP.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(!drag)
                    return;
                if(BorderX){
                    if(upper.isEmpty()||lower.isEmpty())
                        return;
                    double changeY=event.getY()-yGP;
                    if(event.getY()<10){
                        drag=false;
                        containerGP.setCursor(Cursor.DEFAULT);
                        event.consume();
                    }
                    else if(event.getY()>(containerGP.getHeight()-1)-10){
                        drag=false;
                        containerGP.setCursor(Cursor.DEFAULT);
                        event.consume();
                    }
                    
                    for(int a=0; a<upper.size(); a++){
                        ScrollPane comp=(ScrollPane)upper.get(a);
                        if(comp.getPrefHeight()<=10){
                            comp.setPrefHeight(10);
                            drag=false;
                            containerGP.setCursor(Cursor.DEFAULT);
                            event.consume();
                        }
                        else{
                            comp.setPrefHeight(comp.getPrefHeight()+changeY);
                        }
                        try{
                            if(((ImageView)comp.getContent()).getUserData()!=null){
                                ((ImageView)comp.getContent()).setFitHeight(comp.getPrefHeight());
                            }else{
                                lblStatus.setText(objDictionaryAction.getWord("NOCLOTHPART"));
                            }
                        }
                        catch(Exception ex){}
                    }

                    for(int a=0; a<lower.size(); a++){
                        ScrollPane comp=(ScrollPane)lower.get(a);
                        if(comp.getPrefHeight()<=10){
                            comp.setPrefHeight(10);
                            drag=false;
                            containerGP.setCursor(Cursor.DEFAULT);
                            event.consume();
                        }
                        else{
                            comp.setPrefHeight(comp.getPrefHeight()-changeY);
                        }
                        try{
                            if(((ImageView)comp.getContent()).getUserData()!=null){
                                ((ImageView)comp.getContent()).setFitHeight(comp.getPrefHeight());
                                
                            }else{
                                lblStatus.setText(objDictionaryAction.getWord("NOCLOTHPART"));
                            }
                        }
                        catch(Exception ex){}
                    }
                }
                else if(BorderY){
                    if(left.isEmpty()||right.isEmpty())
                        return;
                    double changeX=event.getX()-xGP;
                    if(event.getX()<10){
                        drag=false;
                        containerGP.setCursor(Cursor.DEFAULT);
                        event.consume();
                    }
                    else if(event.getX()>(containerGP.getWidth()-1)-10){
                        drag=false;
                        containerGP.setCursor(Cursor.DEFAULT);
                        event.consume();
                    }
                    
                    for(int a=0; a<left.size(); a++){
                        ScrollPane comp=(ScrollPane)left.get(a);
                        if(comp.getPrefWidth()<=10){
                            comp.setPrefWidth(10);
                            drag=false;
                            containerGP.setCursor(Cursor.DEFAULT);
                            event.consume();
                        }
                        else{
                            comp.setPrefWidth(comp.getPrefWidth()+changeX);
                        }
                        try{
                            if(((ImageView)comp.getContent()).getUserData()!=null){
                                ((ImageView)comp.getContent()).setFitWidth(comp.getPrefWidth());
                            }else{
                                lblStatus.setText(objDictionaryAction.getWord("NOCLOTHPART"));
                            }
                        }
                        catch(Exception ex){}
                    }

                    for(int a=0; a<right.size(); a++){
                        ScrollPane comp=(ScrollPane)right.get(a);
                        if(comp.getPrefWidth()<=10){
                            comp.setPrefWidth(10);
                            drag=false;
                            containerGP.setCursor(Cursor.DEFAULT);
                            event.consume();
                        }
                        else{
                            comp.setPrefWidth(comp.getPrefWidth()-changeX);
                        }
                        try{
                            if(((ImageView)comp.getContent()).getUserData()!=null){
                                ((ImageView)comp.getContent()).setFitWidth(comp.getPrefWidth());
                            }else{
                                lblStatus.setText(objDictionaryAction.getWord("NOCLOTHPART"));
                            }
                        }
                        catch(Exception ex){}
                    }
                }
                xGP=event.getX();
                yGP=event.getY();
            }
        });
        
        containerGP.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(drag){
                    containerGP.setCursor(Cursor.DEFAULT);
                    drag=false;
                    BorderX=false;
                    BorderY=false;
                    for(int a=0; a<containerGP.getChildren().size(); a++){
                        ScrollPane comp=(ScrollPane)containerGP.getChildren().get(a);
                        if(((ImageView)comp.getContent()).getUserData()!=null){
                            objFabric = (Fabric) ((ImageView)comp.getContent()).getUserData();
                            ((ImageView)comp.getContent()).setFitHeight(comp.getPrefHeight());
                            ((ImageView)comp.getContent()).setFitWidth(comp.getPrefWidth());
                            assignFabricToComponent((ImageView)comp.getContent(), objFabric);
                            BufferedImage bufferedImage=getFabricIcon(objFabric);
                            //if(comp.equals(rightBorderPane)){
                            //    bufferedImage=getRightRotatedImage(bufferedImage, 180);
                            //}
                            if(comp.getUserData()!=null){
                                bufferedImage=resizeFabricIcon(bufferedImage, ((BufferedImage)comp.getUserData()).getWidth(), ((BufferedImage)comp.getUserData()).getHeight());
                            }
                            bufferedImage=reOrientImage((ImageView)comp.getContent(), bufferedImage, (int)((ImageView)comp.getContent()).getFitWidth(), (int)((ImageView)comp.getContent()).getFitHeight());
                            Image image=SwingFXUtils.toFXImage(bufferedImage, null);  
                            if(((ImageView)comp.getContent()).equals(leftBorder)||((ImageView)comp.getContent()).equals(rightBorder)){
                                //((ImageView)comp.getContent())
                                leftBorder.setImage(image);
                                bufferedImage = getRightRotatedImage(bufferedImage, 180);
                                rightBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                                //if(((ImageView)comp.getContent()).equals(leftBorder))
                                //    rightBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                                //else
                                //    leftBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                                //
                            } else if(((ImageView)comp.getContent()).equals(leftCrossBorder)||((ImageView)comp.getContent()).equals(rightCrossBorder)||((ImageView)comp.getContent()).equals(rightsideLeftCrossBorder)||((ImageView)comp.getContent()).equals(rightsideRightCrossBorder)){
                                leftCrossBorder.setImage(image);
                                rightCrossBorder.setImage(image);
                                rightsideLeftCrossBorder.setImage(image);
                                rightsideRightCrossBorder.setImage(image);
                            } else if(((ImageView)comp.getContent()).equals(leftPallu)||((ImageView)comp.getContent()).equals(rightPallu)){
                                leftPallu.setImage(image);
                                bufferedImage = getRightRotatedImage(bufferedImage, 180);
                                rightPallu.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                            } else{
                                ((ImageView)comp.getContent()).setImage(image);
                            }
                            bufferedImage = null;
                        }
                    }
                }
            }
        });*/
    }
     
    private void componentAddMouseEvent(final ScrollPane component){
        component.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if(t.getX()<=MARGIN||t.getX()<=component.getPrefWidth()-MARGIN){
                    containerGP.setCursor(Cursor.S_RESIZE);
                    BorderX=true;
                    BorderY=false;
                }
                else if(t.getY()<=MARGIN||t.getY()<=component.getPrefHeight()-MARGIN){
                    containerGP.setCursor(Cursor.E_RESIZE);
                    BorderX=false;
                    BorderY=true;
                }
            }
        });
        component.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if(!(t.getX()<=MARGIN||t.getX()<=component.getPrefWidth()-MARGIN))
                    return;
                else if(!(t.getY()<=MARGIN||t.getY()<=component.getPrefHeight()-MARGIN))
                    return;
                else{
                    containerGP.setCursor(Cursor.DEFAULT);
                    BorderX=false;
                    BorderY=false;
                }
            }
        });
    }
    
    private boolean isResizeCursor(){
        if(containerGP.getCursor()==Cursor.S_RESIZE||containerGP.getCursor()==Cursor.E_RESIZE)
            return true;
        else
            return false;
    }
    
    public void setRepeats(){
        final Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        GridPane popup=new GridPane();
        popup.setId("popup");
        popup.setHgap(5);
        popup.setVgap(5);

        popup.add(new Label(objDictionaryAction.getWord("REPEATS")), 1, 0, 1, 1);
        
        popup.add(leftBorderMultiRepeatCB, 0, 1, 1, 1);
        popup.add(rightBorderMultiRepeatCB, 0, 2, 1, 1);
        popup.add(leftCrossBorderMultiRepeatCB, 0, 3, 1, 1);
        popup.add(rightCrossBorderMultiRepeatCB, 0, 4, 1, 1);
        popup.add(rightsideLeftCrossBorderMultiRepeatCB, 0, 5, 1, 1);
        popup.add(rightsideRightCrossBorderMultiRepeatCB, 0, 6, 1, 1);
        popup.add(leftPalluMultiRepeatCB, 0, 7, 1, 1);
        popup.add(rightPalluMultiRepeatCB, 0, 8, 1, 1);
        popup.add(blouseMultiRepeatCB, 0, 9, 1, 1);
        popup.add(skartMultiRepeatCB, 0, 10, 1, 1);
        
        popup.add(txtLeftBorderRepeats, 1, 1, 1, 1);
        popup.add(txtRightBorderRepeats, 1, 2, 1, 1);
        popup.add(txtLeftCrossBorderRepeats, 1, 3, 1, 1);
        popup.add(txtRightCrossBorderRepeats, 1, 4, 1, 1);
        popup.add(txtRightsideLeftCrossBorderRepeats, 1, 5, 1, 1);
        popup.add(txtRightsideRightCrossBorderRepeats, 1, 6, 1, 1);
        popup.add(txtLeftPalluRepeats, 1, 7, 1, 1);
        popup.add(txtRightPalluRepeats, 1, 8, 1, 1);
        popup.add(txtBlouseRepeats, 1, 9, 1, 1);
        popup.add(txtSkartRepeats, 1, 10, 1, 1);
        
        //leftBorderMultiRepeatCB.setSelected(leftBorderMultiRepeatPolicy);
        txtLeftBorderRepeats.setText(String.valueOf(repeats[0]));
        txtRightBorderRepeats.setText(String.valueOf(repeats[10]));
        txtLeftCrossBorderRepeats.setText(String.valueOf(repeats[2]));
        txtRightCrossBorderRepeats.setText(String.valueOf(repeats[4]));
        txtRightsideLeftCrossBorderRepeats.setText(String.valueOf(repeats[7]));
        txtRightsideRightCrossBorderRepeats.setText(String.valueOf(repeats[9]));
        txtLeftPalluRepeats.setText(String.valueOf(repeats[3]));
        txtRightPalluRepeats.setText(String.valueOf(repeats[8]));
        txtBlouseRepeats.setText(String.valueOf(repeats[1]));
        txtSkartRepeats.setText(String.valueOf(repeats[6]));
        
        txtLeftBorderRepeats.setDisable(!leftBorderMultiRepeatCB.isSelected());
        txtRightBorderRepeats.setDisable(!rightBorderMultiRepeatCB.isSelected());
        txtLeftCrossBorderRepeats.setDisable(!leftCrossBorderMultiRepeatCB.isSelected());
        txtRightCrossBorderRepeats.setDisable(!rightCrossBorderMultiRepeatCB.isSelected());
        txtRightsideLeftCrossBorderRepeats.setDisable(!rightsideLeftCrossBorderMultiRepeatCB.isSelected());
        txtRightsideRightCrossBorderRepeats.setDisable(!rightsideRightCrossBorderMultiRepeatCB.isSelected());
        txtLeftPalluRepeats.setDisable(!leftPalluMultiRepeatCB.isSelected());
        txtRightPalluRepeats.setDisable(!rightPalluMultiRepeatCB.isSelected());
        txtBlouseRepeats.setDisable(!blouseMultiRepeatCB.isSelected());
        txtSkartRepeats.setDisable(!skartMultiRepeatCB.isSelected());
        
        Button btnSave = new Button(objDictionaryAction.getWord("SAVEAS"));
        btnSave.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save_as.png"));
        btnSave.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVEAS")));
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e){
                try{
                    repeats[0]=(Integer.parseInt(txtLeftBorderRepeats.getText())>0?Integer.parseInt(txtLeftBorderRepeats.getText()):2);
                    repeats[10]=(Integer.parseInt(txtRightBorderRepeats.getText())>0?Integer.parseInt(txtRightBorderRepeats.getText()):2);
                    repeats[2]=(Integer.parseInt(txtLeftCrossBorderRepeats.getText())>0?Integer.parseInt(txtLeftCrossBorderRepeats.getText()):2);
                    repeats[4]=(Integer.parseInt(txtRightCrossBorderRepeats.getText())>0?Integer.parseInt(txtRightCrossBorderRepeats.getText()):2);
                    repeats[7]=(Integer.parseInt(txtRightsideLeftCrossBorderRepeats.getText())>0?Integer.parseInt(txtRightsideLeftCrossBorderRepeats.getText()):2);
                    repeats[9]=(Integer.parseInt(txtRightsideRightCrossBorderRepeats.getText())>0?Integer.parseInt(txtRightsideRightCrossBorderRepeats.getText()):2);
                    repeats[3]=(Integer.parseInt(txtLeftPalluRepeats.getText())>0?Integer.parseInt(txtLeftPalluRepeats.getText()):2);
                    repeats[8]=(Integer.parseInt(txtRightPalluRepeats.getText())>0?Integer.parseInt(txtRightPalluRepeats.getText()):2);
                    repeats[1]=(Integer.parseInt(txtBlouseRepeats.getText())>0?Integer.parseInt(txtBlouseRepeats.getText()):2);
                    repeats[6]=(Integer.parseInt(txtSkartRepeats.getText())>0?Integer.parseInt(txtSkartRepeats.getText()):2);
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEAS"));
                    System.gc();
                    dialogStage.close();
                } catch(Exception ex){
                    new Logging("SEVERE",ClothView.class.getName(),"setRepeats(): "+ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        popup.add(btnSave, 0, 11);

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
        popup.add(btnCancel, 1, 11);

        Scene scene = new Scene(popup);
        scene.getStylesheets().add(ClothView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        dialogStage.setScene(scene);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("SAVE"));
        dialogStage.showAndWait();        
    }
    
    private void otherMaterialCustomLayout(){
        final Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        GridPane popup=new GridPane();
        popup.setId("popup");
        popup.setHgap(5);
        popup.setVgap(5);
        
        detectEnabledComponents();
        
        popup.add(new Label(objDictionaryAction.getWord("NAME")), 0, 0, 2, 1);
        
        final CheckBox bodyCB = new CheckBox(objDictionaryAction.getWord("BODYCB"));
        bodyCB.setSelected(true);
        bodyCB.setDisable(true);
        popup.add(bodyCB, 0, 1, 2, 1);
        
        final CheckBox rightLeftCrossBorderCB = new CheckBox(objDictionaryAction.getWord("RIGHTLEFTCROSSBORDERCB"));
        rightLeftCrossBorderCB.setSelected(rightsideLeftCrossBorderEnabled);
        popup.add(rightLeftCrossBorderCB, 0, 2, 2, 1);
        
        final CheckBox rightRightCrossBorderCB = new CheckBox(objDictionaryAction.getWord("RIGHTRIGHTCROSSBORDERCB"));
        rightRightCrossBorderCB.setSelected(rightsideRightCrossBorderEnabled);
        popup.add(rightRightCrossBorderCB, 0, 3, 2, 1);
       
        popup.add(new Label(objDictionaryAction.getWord("PALLU")), 0, 4, 2, 1);
        
        final CheckBox leftPaluCB = new CheckBox(objDictionaryAction.getWord("LEFTPALUCB"));
        leftPaluCB.setSelected(leftPalluEnabled);
        popup.add(leftPaluCB, 1, 5, 1, 1);
        
        final CheckBox rightPaluCB = new CheckBox(objDictionaryAction.getWord("RIGHTPALUCB"));
        rightPaluCB.setSelected(rightPalluEnabled);
        popup.add(rightPaluCB, 1, 6, 1, 1);
        
        popup.add(new Label(objDictionaryAction.getWord("CROSSBORDER")), 0, 7, 2, 1);
        
        final CheckBox leftCrosssBorderCB = new CheckBox(objDictionaryAction.getWord("LEFTCROSSBORDERCB"));
        leftCrosssBorderCB.setSelected(leftCrossBorderEnabled);
        popup.add(leftCrosssBorderCB, 1, 8, 1, 1);
        
        final CheckBox rightCrosssBorderCB = new CheckBox(objDictionaryAction.getWord("RIGHTCROSSBORDERCB"));
        rightCrosssBorderCB.setSelected(rightCrossBorderEnabled);
        popup.add(rightCrosssBorderCB, 1, 9, 1, 1);
        
        popup.add(new Label(objDictionaryAction.getWord("BORDER")), 0, 10, 2, 1);
        
        final CheckBox leftBorderCB = new CheckBox(objDictionaryAction.getWord("LEFTBORDERCB"));
        leftBorderCB.setSelected(leftBorderEnabled);
        popup.add(leftBorderCB, 1, 11, 1, 1);
        
        final CheckBox rightBorderCB = new CheckBox(objDictionaryAction.getWord("RIGHTBORDERCB"));
        rightBorderCB.setSelected(rightBorderEnabled);
        popup.add(rightBorderCB, 1, 12, 1, 1);
        
        popup.add(new Label(objDictionaryAction.getWord("KONIA")), 0, 13, 2, 1);
        
        final CheckBox leftTopKoniaCB = new CheckBox(objDictionaryAction.getWord("LEFTTOPKONIACB"));
        leftTopKoniaCB.setSelected(false);
        popup.add(leftTopKoniaCB, 1, 14, 1, 1);
        leftTopKoniaCB.setDisable(true);
        
        final CheckBox rightTopKoniaCB = new CheckBox(objDictionaryAction.getWord("RIGHTTOPKONIACB"));
        rightTopKoniaCB.setSelected(false);
        popup.add(rightTopKoniaCB, 1, 15, 1, 1);
        rightTopKoniaCB.setDisable(true);
        
        final CheckBox leftBottomKoniaCB = new CheckBox(objDictionaryAction.getWord("LEFTBOTTOMKONIACB"));
        leftBottomKoniaCB.setSelected(false);
        popup.add(leftBottomKoniaCB, 1, 16, 1, 1);
        leftBottomKoniaCB.setDisable(true);
        
        final CheckBox rightBottomKoniaCB = new CheckBox(objDictionaryAction.getWord("RIGHTBOTTOMKONIACB"));
        rightBottomKoniaCB.setSelected(false);
        popup.add(rightBottomKoniaCB, 1, 17, 1, 1);
        rightBottomKoniaCB.setDisable(true);
        
        Button btnSave = new Button(objDictionaryAction.getWord("SAVEAS"));
        btnSave.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save_as.png"));
        btnSave.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVEAS")));
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e){
                containerGP.getChildren().clear();
                leftBorderEnabled=leftBorderCB.isSelected();
                rightBorderEnabled=rightBorderCB.isSelected();
                leftCrossBorderEnabled=leftCrosssBorderCB.isSelected();
                rightCrossBorderEnabled=rightCrosssBorderCB.isSelected();
                leftPalluEnabled=leftPaluCB.isSelected();
                rightPalluEnabled=rightPaluCB.isSelected();
                rightsideLeftCrossBorderEnabled=rightLeftCrossBorderCB.isSelected();
                rightsideRightCrossBorderEnabled=rightRightCrossBorderCB.isSelected();
                //setInitialWidthHeight();
                recalculateBody();
                setComponentWidthHeight();
                plotFullCustomLayout();
                
                try{
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEAS"));
                    System.gc();
                    dialogStage.close();
                } catch(Exception ex){
                    new Logging("SEVERE",ClothView.class.getName(),"otherMaterialCustomLayout(): "+ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        popup.add(btnSave, 0, 18);

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
        popup.add(btnCancel, 1, 18);

        Scene scene = new Scene(popup);
        scene.getStylesheets().add(ClothView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        dialogStage.setScene(scene);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("SAVE"));
        dialogStage.showAndWait();        
    }

    private void customLayout(){
        final Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        GridPane popup=new GridPane();
        popup.setId("popup");
        popup.setHgap(5);
        popup.setVgap(5);
        
        detectEnabledComponents();
        
        popup.add(new Label(objDictionaryAction.getWord("NAME")), 0, 0, 2, 1);
        
        final CheckBox bodyCB = new CheckBox(objDictionaryAction.getWord("BODYCB"));
        bodyCB.setSelected(true);
        bodyCB.setDisable(true);
        popup.add(bodyCB, 0, 1, 2, 1);
        
        final CheckBox blouseCB = new CheckBox(objDictionaryAction.getWord("BLOUSECB"));
        blouseCB.setSelected(blouseEnabled);
        popup.add(blouseCB, 0, 2, 2, 1);
        
        final CheckBox skartCB = new CheckBox(objDictionaryAction.getWord("SKARTCB"));
        //skartCB.setSelected(objConfiguration.getBlnMRepeat());
        skartCB.setSelected(skartEnabled);
        popup.add(skartCB, 0, 3, 2, 1);
       
        popup.add(new Label(objDictionaryAction.getWord("PALLU")), 0, 4, 2, 1);
        
        final CheckBox leftPaluCB = new CheckBox(objDictionaryAction.getWord("LEFTPALUCB"));
        leftPaluCB.setSelected(leftPalluEnabled);
        popup.add(leftPaluCB, 1, 5, 1, 1);
        
        final CheckBox rightPaluCB = new CheckBox(objDictionaryAction.getWord("RIGHTPALUCB"));
        rightPaluCB.setSelected(rightPalluEnabled);
        popup.add(rightPaluCB, 1, 6, 1, 1);
        
        popup.add(new Label(objDictionaryAction.getWord("CROSSBORDER")), 0, 7, 2, 1);
        
        final CheckBox leftCrosssBorderCB = new CheckBox(objDictionaryAction.getWord("LEFTCROSSBORDERCB"));
        leftCrosssBorderCB.setSelected(leftCrossBorderEnabled);
        popup.add(leftCrosssBorderCB, 1, 8, 1, 1);
        
        final CheckBox rightCrosssBorderCB = new CheckBox(objDictionaryAction.getWord("RIGHTCROSSBORDERCB"));
        rightCrosssBorderCB.setSelected(rightCrossBorderEnabled);
        popup.add(rightCrosssBorderCB, 1, 9, 1, 1);
        
        popup.add(new Label(objDictionaryAction.getWord("BORDER")), 0, 10, 2, 1);
        
        final CheckBox leftBorderCB = new CheckBox(objDictionaryAction.getWord("LEFTBORDERCB"));
        leftBorderCB.setSelected(leftBorderEnabled);
        popup.add(leftBorderCB, 1, 11, 1, 1);
        
        final CheckBox rightBorderCB = new CheckBox(objDictionaryAction.getWord("RIGHTBORDERCB"));
        rightBorderCB.setSelected(rightBorderEnabled);
        popup.add(rightBorderCB, 1, 12, 1, 1);
        
        popup.add(new Label(objDictionaryAction.getWord("KONIA")), 0, 13, 2, 1);
        
        final CheckBox leftTopKoniaCB = new CheckBox(objDictionaryAction.getWord("LEFTTOPKONIACB"));
        leftTopKoniaCB.setSelected(false);
        popup.add(leftTopKoniaCB, 1, 14, 1, 1);
        leftTopKoniaCB.setDisable(true);
        
        final CheckBox rightTopKoniaCB = new CheckBox(objDictionaryAction.getWord("RIGHTTOPKONIACB"));
        rightTopKoniaCB.setSelected(false);
        popup.add(rightTopKoniaCB, 1, 15, 1, 1);
        rightTopKoniaCB.setDisable(true);
        
        final CheckBox leftBottomKoniaCB = new CheckBox(objDictionaryAction.getWord("LEFTBOTTOMKONIACB"));
        leftBottomKoniaCB.setSelected(false);
        popup.add(leftBottomKoniaCB, 1, 16, 1, 1);
        leftBottomKoniaCB.setDisable(true);
        
        final CheckBox rightBottomKoniaCB = new CheckBox(objDictionaryAction.getWord("RIGHTBOTTOMKONIACB"));
        rightBottomKoniaCB.setSelected(false);
        popup.add(rightBottomKoniaCB, 1, 17, 1, 1);
        rightBottomKoniaCB.setDisable(true);
        
        Button btnSave = new Button(objDictionaryAction.getWord("SAVEAS"));
        btnSave.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save_as.png"));
        btnSave.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVEAS")));
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e){
                containerGP.getChildren().clear();
                leftBorderEnabled=leftBorderCB.isSelected();
                rightBorderEnabled=rightBorderCB.isSelected();
                leftCrossBorderEnabled=leftCrosssBorderCB.isSelected();
                rightCrossBorderEnabled=rightCrosssBorderCB.isSelected();
                leftPalluEnabled=leftPaluCB.isSelected();
                rightPalluEnabled=rightPaluCB.isSelected();
                blouseEnabled=blouseCB.isSelected();
                skartEnabled=skartCB.isSelected();
                //setInitialWidthHeight();
                recalculateBody();
                setComponentWidthHeight();
                plotFullCustomLayout();
                
                try{
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEAS"));
                    System.gc();
                    dialogStage.close();
                } catch(Exception ex){
                    new Logging("SEVERE",ClothView.class.getName(),"customLayout(): "+ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        popup.add(btnSave, 0, 18);

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
        popup.add(btnCancel, 1, 18);

        Scene scene = new Scene(popup);
        scene.getStylesheets().add(ClothView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        dialogStage.setScene(scene);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("SAVE"));
        dialogStage.showAndWait();        
    }
     
    private void clothLayout(String strLayout){
        if(strLayout!="FullView"){
            containerGP.getChildren().clear();
            allComponentVB.setMouseTransparent(false);
        }
        if(strLayout.equalsIgnoreCase("sareeLayout6")){
            // You are trying to see Saree with konia
            leftCrossBorder.setFitHeight(400);
            leftCrossBorder.setFitWidth(50);
            rightCrossBorder.setFitHeight(400);
            rightCrossBorder.setFitWidth(50);
            leftPallu.setFitHeight(400);
            leftPallu.setFitWidth(200);
            leftBorder.setFitHeight(50);
            leftBorder.setFitWidth(objConfiguration.WIDTH);
            rightBorder.setFitHeight(50);
            rightBorder.setFitWidth(objConfiguration.WIDTH);
            rightBorder.setRotate(180);
            lefttopKonia.setFitHeight(50);
            lefttopKonia.setFitWidth(50);
            leftbottomKonia.setFitHeight(50);
            leftbottomKonia.setFitWidth(50);
            body.setFitHeight(400);
            body.setFitWidth(objConfiguration.WIDTH-(leftPallu.getFitWidth()+rightCrossBorder.getFitWidth()+leftCrossBorder.getFitWidth()));
            ImageView blank = new ImageView();
            blank.setFitHeight(300);
            blank.setFitWidth(body.getFitWidth());
            
            containerGP.getChildren().clear();
            containerGP.add(leftCrossBorder,0,1,1,3);
            containerGP.add(leftPallu,1,1,1,3);
            containerGP.add(rightCrossBorder,2,1,1,3);
            containerGP.add(leftBorder,0,0,5,1);
            containerGP.add(blank,3,2,2,1);
            containerGP.add(body,3,1,2,3);
            containerGP.add(lefttopKonia,3,1,1,1);
            containerGP.add(leftbottomKonia,3,3,1,1);
            containerGP.add(rightBorder,0,4,5,1);            
        }else if(strLayout.equalsIgnoreCase("sareeLayout5")){
            // You are trying to see Saree with blouse
            enableAllComponents(false);
            leftBorderEnabled=true;
            blouseEnabled=true;
            leftCrossBorderEnabled=true;
            leftPalluEnabled=true;
            rightCrossBorderEnabled=true;
            rightBorderEnabled=true;
            //setInitialWidthHeight();
            recalculateBody();
            setComponentWidthHeight();
            plotFullCustomLayout();
        }else if(strLayout.equalsIgnoreCase("sareeLayout4")){
            // You are trying to see Saree with skart
            enableAllComponents(false);
            leftBorderEnabled=true;
            leftCrossBorderEnabled=true;
            leftPalluEnabled=true;
            rightCrossBorderEnabled=true;
            skartEnabled=true;
            rightBorderEnabled=true;
            recalculateBody();
            //setInitialWidthHeight();
            setComponentWidthHeight();
            plotFullCustomLayout();
        }else if(strLayout.equalsIgnoreCase("sareeLayout3")){
            // You are trying to see Saree with single side cross border
            enableAllComponents(false);
            leftBorderEnabled=true;
            leftCrossBorderEnabled=true;
            leftPalluEnabled=true;
            rightBorderEnabled=true;
            recalculateBody();
            //setInitialWidthHeight();
            setComponentWidthHeight();
            plotFullCustomLayout();
        }else if(strLayout.equalsIgnoreCase("sareeLayout2")){
            // You are trying to see Saree with cross border across right
            /*leftCrossBorder.setFitHeight(500);
            leftCrossBorder.setFitWidth(50);
            rightCrossBorder.setFitHeight(500);
            rightCrossBorder.setFitWidth(50);
            leftPallu.setFitHeight(500);
            leftPallu.setFitWidth(200);
            leftBorder.setFitHeight(50);
            leftBorder.setFitWidth(objConfiguration.WIDTH-(leftPallu.getFitWidth()+rightCrossBorder.getFitWidth()+leftCrossBorder.getFitWidth()));
            rightBorder.setFitHeight(50);
            rightBorder.setFitWidth(objConfiguration.WIDTH-(leftPallu.getFitWidth()+rightCrossBorder.getFitWidth()+leftCrossBorder.getFitWidth()));
            rightBorder.setRotate(180);
            body.setFitHeight(400);
            body.setFitWidth(objConfiguration.WIDTH-(leftPallu.getFitWidth()+rightCrossBorder.getFitWidth()+leftCrossBorder.getFitWidth()));
            
            leftCrossBorderPane.setPrefHeight(500);
            leftCrossBorderPane.setPrefWidth(50);
            rightCrossBorderPane.setPrefHeight(500);
            rightCrossBorderPane.setPrefWidth(50);
            leftPalluPane.setPrefHeight(500);
            leftPalluPane.setPrefWidth(200);
            leftBorderPane.setPrefHeight(50);
            leftBorderPane.setPrefWidth(objConfiguration.WIDTH-(leftPallu.getFitWidth()+rightCrossBorder.getFitWidth()+leftCrossBorder.getFitWidth()));
            rightBorderPane.setPrefHeight(50);
            rightBorderPane.setPrefWidth(objConfiguration.WIDTH-(leftPallu.getFitWidth()+rightCrossBorder.getFitWidth()+leftCrossBorder.getFitWidth()));
            rightBorderPane.setRotate(180);
            bodyPane.setPrefHeight(400);
            bodyPane.setPrefWidth(objConfiguration.WIDTH-(leftPallu.getFitWidth()+rightCrossBorder.getFitWidth()+leftCrossBorder.getFitWidth()));
            
            leftCrossBorderPane.setContent(leftCrossBorder);
            leftPalluPane.setContent(leftPallu);
            rightCrossBorderPane.setContent(rightCrossBorder);
            leftBorderPane.setContent(leftBorder);
            bodyPane.setContent(body);
            rightBorderPane.setContent(rightBorder);
            
            containerGP.getChildren().clear();
            containerGP.add(leftCrossBorderPane,0,0,1,3);
            containerGP.add(leftPalluPane,1,0,1,3);
            containerGP.add(rightCrossBorderPane,2,0,1,3);
            containerGP.add(leftBorderPane,3,0,1,1);
            containerGP.add(bodyPane,3,1,1,1);
            containerGP.add(rightBorderPane,3,2,1,1);
            repaintComponents();*/
        }else if(strLayout.equalsIgnoreCase("sareeLayout1")){
            // You are trying to see Saree with border across length
            // You are trying to see Saree with border across length
            enableAllComponents(false);
            leftBorderEnabled=true;
            leftCrossBorderEnabled=true;
            leftPalluEnabled=true;
            rightCrossBorderEnabled=true;
            rightBorderEnabled=true;
            recalculateBody();
            //setInitialWidthHeight();
            setComponentWidthHeight();
            plotFullCustomLayout();
        }else if(strLayout.equalsIgnoreCase("dressLayout3")||strLayout.equalsIgnoreCase("stoleLayout4")||strLayout.equalsIgnoreCase("dupattaLayout4")){
            stoleLayout4Action();
        }else if(strLayout.equalsIgnoreCase("dressLayout2")){
            // You are trying to see Saree with cross border across right
            enableAllComponents(false);
            rightBorderEnabled=true;
            recalculateBody();
            //setInitialWidthHeight();
            setComponentWidthHeight();
            plotFullCustomLayout();
        }else if(strLayout.equalsIgnoreCase("dressLayout1")||strLayout.equalsIgnoreCase("shawlLayout1")||strLayout.equalsIgnoreCase("brocadeLayout1")){
            dressLayout1Action();
        }else if(strLayout.equalsIgnoreCase("stoleLayout3")||strLayout.equalsIgnoreCase("dupattaLayout3")){
            stoleLayout3Action();
        }else if(strLayout.equalsIgnoreCase("stoleLayout2")||strLayout.equalsIgnoreCase("dupttaLayout2")){
            stoleLayout2Action();
        }else if(strLayout.equalsIgnoreCase("stoleLayout1")||strLayout.equalsIgnoreCase("dupattaLayout1")){
            stoleLayout1Action();
        }else if(strLayout.equalsIgnoreCase("FullView")){
            // save current data if existing in imageviews
            allComponentVB.setMouseTransparent(true);
            for(int a=0; a<containerGP.getChildren().size(); a++){
                ScrollPane compSP=(ScrollPane)containerGP.getChildren().get(a);
                ImageView component=(ImageView)compSP.getContent();
                if(component.getUserData()!=null){
                    /* i.e. some fabric is assigned */
                    Fabric tempFabric=(Fabric)component.getUserData();
                    BufferedImage bufferedImage=getFabricIcon(tempFabric);
                    double ratio=(double)bufferedImage.getWidth()/(double)bufferedImage.getHeight();
                    int repeatCount=(int)(tempFabric.getDblFabricWidth()/tempFabric.getDblArtworkWidth());
                    
                    /* number of repeats calculated may be zero (or <1) */
                    if(repeatCount<1)
                        repeatCount=1;
                    
                    if(component.equals(leftBorder)&&!leftBorderMultiRepeatPolicy)
                        repeatCount=1;
                    else if(component.equals(rightBorder)&&!rightBorderMultiRepeatPolicy)
                        repeatCount=1;
                    else if(component.equals(leftCrossBorder)&&!leftCrossBorderMultiRepeatPolicy)
                        repeatCount=1;
                    else if(component.equals(rightCrossBorder)&&!rightCrossBorderMultiRepeatPolicy)
                        repeatCount=1;
                    else if(component.equals(rightsideLeftCrossBorder)&&!rightsideLeftCrossBorderMultiRepeatPolicy)
                        repeatCount=1;
                    else if(component.equals(rightsideRightCrossBorder)&&!rightsideRightCrossBorderMultiRepeatPolicy)
                        repeatCount=1;
                    else if(component.equals(leftPallu)&&!leftPalluMultiRepeatPolicy)
                        repeatCount=1;
                    else if(component.equals(rightPallu)&&!rightPalluMultiRepeatPolicy)
                        repeatCount=1;
                    else if(component.equals(blouse)&&!blouseMultiRepeatPolicy)
                        repeatCount=1;
                    else if(component.equals(skart)&&!skartMultiRepeatPolicy)
                        repeatCount=1;
                    
                    if(component.equals(leftBorder)||component.equals(rightBorder)||component.equals(body)||component.equals(skart)){
                        bufferedImage=resizeFabricIcon(bufferedImage, (int)((component.getFitHeight()/repeatCount)*ratio)
                                , (int)(component.getFitHeight()/repeatCount));
                    }else{
                        //bufferedImage=resizeFabricIcon(bufferedImage, (int)((component.getFitWidth()/repeatCount)/ratio)
                        //        , (int)(component.getFitWidth()/repeatCount));
                        bufferedImage=resizeFabricIcon(bufferedImage, (int)(component.getFitWidth()/repeatCount)
                                , (int)((component.getFitWidth()/repeatCount)/ratio));
                    }
                    // repaint on image view repaint(bufferedImage, iv.len, iv.wid);
                    bufferedImage=reOrientImage(component, bufferedImage, (int)component.getFitWidth(), (int)component.getFitHeight());
                    Image image=SwingFXUtils.toFXImage(bufferedImage, null);
                    if( component.equals(leftBorder)|| component.equals(rightBorder)){
                        if(borderLinkPolicy){
                            leftBorder.setImage(image);
                            bufferedImage = getRightRotatedImage(bufferedImage, 180);
                            rightBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                        }
                        else{
                            if(component.equals(leftBorder)){
                                leftBorder.setImage(image);
                            }
                            else{
                                bufferedImage = getRightRotatedImage(bufferedImage, 180);
                                rightBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                            }
                        }
                    } else if( component.equals(leftCrossBorder)|| component.equals(rightCrossBorder)|| component.equals(rightsideLeftCrossBorder)|| component.equals(rightsideRightCrossBorder)){
                        if(crossBorderLinkPolicy){
                            leftCrossBorder.setImage(image);
                            rightCrossBorder.setImage(image);
                            rightsideLeftCrossBorder.setImage(image);
                            rightsideRightCrossBorder.setImage(image);
                        }
                        else{
                            component.setImage(image);
                        }
                    } else if( component.equals(leftPallu)|| component.equals(rightPallu)){
                        if(palluLinkPolicy){
                            leftPallu.setImage(image);
                            bufferedImage = getRightRotatedImage(bufferedImage, 180);
                            rightPallu.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                        }
                        else{
                            if(component.equals(leftPallu)){
                                leftPallu.setImage(image);
                            }
                            else{
                                bufferedImage = getRightRotatedImage(bufferedImage, 180);
                                rightPallu.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                            }
                        }
                    } else{
                         component.setImage(image);
                    }
                    bufferedImage = null;
                }
            }
            
        }else{
            enableAllComponents(true);
            recalculateBody();
            //setInitialWidthHeight();
            setComponentWidthHeight();
            plotFullCustomLayout();
        }
    }
 //===========================================================================================
    public void ScreenShoot(String actionName) {
        try {
            final WritableImage image = containerGP.snapshot(new SnapshotParameters(), null);
            if(actionName=="print"){
                PrinterJob objPrinterJob = PrinterJob.getPrinterJob();
                if(objPrinterJob.printDialog()){
                    objPrinterJob.setPrintable(new Printable() {
                        @Override
                        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                            if (pageIndex != 0) {
                                return NO_SUCH_PAGE;
                            }
                            Graphics2D g2d=(Graphics2D)graphics;
                            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                            BufferedImage texture = SwingFXUtils.fromFXImage(image, null);
                            int wd= (texture.getWidth()<(int)pageFormat.getImageableWidth())?texture.getWidth():(int)pageFormat.getImageableWidth();
                            int ht= (texture.getHeight()<(int)pageFormat.getImageableHeight())?texture.getHeight():(int)pageFormat.getImageableHeight();
                            if((texture.getWidth()>(int)pageFormat.getImageableWidth())||(texture.getHeight()>(int)pageFormat.getImageableHeight())){
                                BufferedImage newBI = new BufferedImage(wd, ht, BufferedImage.TYPE_INT_ARGB);
                                for(int a=0; a<newBI.getWidth(); a++)
                                    for(int b=0; b<newBI.getHeight(); b++)
                                        newBI.setRGB(a, b, texture.getRGB(a, b));
                                texture=newBI;
                            }
                            graphics.drawImage(texture, 0, 0, wd, ht, null);
                            return PAGE_EXISTS;                    
                        }
                    });    
                    objPrinterJob.print();
                }
                objPrinterJob = null;
            } else if(actionName=="export"){
                FileChooser fileChoser=new FileChooser();
                fileChoser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTPNG")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
                FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
                fileChoser.getExtensionFilters().add(extFilterPNG);
                File file=fileChoser.showSaveDialog(clothStage);
                if(file==null)
                    return;
                else
                    clothStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+file.getAbsoluteFile().getName()+"]");   
                if (!file.getName().endsWith("png")) 
                    file = new File(file.getPath() +".png");
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                
                ArrayList<File> filesToZip=new ArrayList<>();
                filesToZip.add(file);
                String zipFilePath=file.getAbsolutePath()+".zip";
                String passwordToZip = file.getName();
                new EncryptZip(zipFilePath, filesToZip, passwordToZip);
                file.delete();
                
                lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
            } else if(actionName == "Simulation"){
                BufferedImage myimage = SwingFXUtils.fromFXImage(image, null);
                int w = myimage.getWidth();
                int h = myimage.getHeight();
                int type = BufferedImage.TYPE_INT_RGB; 
                BufferedImage DaImage = new BufferedImage(h, w, type);
                Graphics2D g2 = DaImage.createGraphics();
                double x = (h - w)/2.0;
                double y = (w - h)/2.0;
                int angle = 270;
                AffineTransform at = AffineTransform.getTranslateInstance(x, y);
                at.rotate(Math.toRadians(angle), w/2.0, h/2.0);
                g2.drawImage(myimage, at, null);
                g2.dispose();
            
                File file = new File(System.getProperty("user.dir")+"\\mla\\Untitled12.png");
                file.delete();
                file = new File(System.getProperty("user.dir")+"\\mla\\Untitled12.png");
                ImageIO.write(DaImage, "png", file);
                newFunction(System.getProperty("user.dir")+"\\mla\\EnvelopeDistordTest.swf");
                lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
            }  else if(actionName == "ClothSimulation"){
                SimulatorEditView objSimulatorEditView = new SimulatorEditView(objConfiguration, SwingFXUtils.fromFXImage(image, null));
                lblStatus.setText(objDictionaryAction.getWord("GOTSIMULATION"));
            }   else if(actionName == "save"){
                
            }            
        } catch (IOException ex) {
            new Logging("SEVERE",ClothView.class.getName(),"ScreenShoot(): "+actionName+": "+ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (PrinterException ex) {
            new Logging("SEVERE",ClothView.class.getName(),"ScreenShoot(): "+actionName+": "+ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
        System.gc();
    }

  private BufferedImage editYarn(ImageView clothIV){
      //BufferedImage bufferedImage = new BufferedImage((int)clothIV.getFitHeight(), (int)clothIV.getFitWidth(), BufferedImage.TYPE_INT_ARGB);
      BufferedImage bufferedImage=null;
      try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONYARNEDIT"));
        objConfiguration.strWindowFlowContext = "ClothEditor";                        
        UserAction objUserAction = new UserAction();
        objUserAction.getConfiguration(objConfiguration);
        objConfiguration.clothRepeat();
        objFabric=(Fabric) clothIV.getUserData();
        
        objFabric.getObjConfiguration().setWarpYarn(objFabric.getWarpYarn());
        objFabric.getObjConfiguration().setWeftYarn(objFabric.getWeftYarn());
        objFabric.getObjConfiguration().setWarpExtraYarn(objFabric.getWarpExtraYarn());
        objFabric.getObjConfiguration().setWeftExtraYarn(objFabric.getWeftExtraYarn());
        objFabric.getObjConfiguration().setIntExtraWeft(objFabric.getIntExtraWeft());
        objFabric.getObjConfiguration().setIntExtraWarp(objFabric.getIntExtraWarp());
        objFabric.getObjConfiguration().setColourPalette(objFabric.getThreadPaletes());

        YarnView objYarnView = new YarnView(objFabric.getObjConfiguration());

        objFabric.setWarpYarn(objFabric.getObjConfiguration().getWarpYarn());
        objFabric.setWeftYarn(objFabric.getObjConfiguration().getWeftYarn());
        objFabric.setWarpExtraYarn(objFabric.getObjConfiguration().getWarpExtraYarn());
        objFabric.setWeftExtraYarn(objFabric.getObjConfiguration().getWeftExtraYarn());
        objFabric.setIntExtraWeft(objFabric.getObjConfiguration().getIntExtraWeft());
        objFabric.setIntExtraWarp(objFabric.getObjConfiguration().getIntExtraWarp());
        objFabric.setThreadPaletes(objFabric.getObjConfiguration().getColourPalette());
            
        FabricAction objFabricAction = new FabricAction();
        bufferedImage = objFabricAction.plotCompositeView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), objFabric.getIntWarp(), objFabric.getIntWeft());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( bufferedImage, "png", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        // updates fabric icon (changed yarn) of objFabric
        objFabric.setBytFabricIcon(imageInByte);
        /* don't set image in ImageView component here */
        clothIV.setUserData(objFabric);
        //ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(objConfiguration.getStrClothType()+".ser"));
        //oos.writeObject(objFabric);
        System.gc();
    } catch (SQLException ex) {
        new Logging("SEVERE",ClothView.class.getName(),"editYarn() : Error while viewing composte view",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }   catch (Exception ex) {
        ex.printStackTrace();
        new Logging("SEVERE",ClothView.class.getName(),"editYarn() : Error while viewing composte view",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
    return bufferedImage;
  }
  private BufferedImage editArtwork(ImageView clothIV){
      BufferedImage bufferedImage = new BufferedImage((int)clothIV.getFitHeight(), (int)clothIV.getFitWidth(), BufferedImage.TYPE_INT_ARGB);
      try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONARTWORKASSINGMENTEDIT"));
        objConfiguration.strWindowFlowContext = "FabricEditor";                        
        UserAction objUserAction = new UserAction();
        objUserAction.getConfiguration(objConfiguration);
        objConfiguration.clothRepeat();
        objFabric=(Fabric) clothIV.getUserData();
        ArtworkEditView objArtworkEditView= new ArtworkEditView(objFabric);
        if(objFabric.getStrArtworkID()==null){
            byte[][] baseWeaveMatrix = objFabric.getBaseWeaveMatrix();
            try {            
                objArtworkAction= new ArtworkAction();
                objFabric.setFabricMatrix(objArtworkAction.repeatMatrix(baseWeaveMatrix,objFabric.getIntWeft(),objFabric.getIntWarp()));
                objArtworkAction= new ArtworkAction();
                objFabric.setReverseMatrix(objArtworkAction.invertMatrix(objFabric.getFabricMatrix()));
            } catch (SQLException ex) {
                new Logging("SEVERE",FabricView.class.getName(),"editArtwork(): Fabric Artwork was null "+ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
            objFabric.setWarpExtraYarn(null);
            objFabric.setIntExtraWarp(objConfiguration.getIntExtraWarp());
            objFabric.setWeftExtraYarn(null);
            objFabric.setIntExtraWeft(objConfiguration.getIntExtraWeft());
        }else{
            objFabric.setIntWeft(objFabric.getArtworkMatrix().length);        
            objFabric.setIntWarp(objFabric.getArtworkMatrix()[0].length);
            objFabric.setFabricMatrix(objFabric.getArtworkMatrix());
            objConfiguration.setStrRecentArtwork(objFabric.getObjConfiguration().getStrRecentArtwork());
        }        
        FabricAction objFabricAction = new FabricAction();
        bufferedImage = objFabricAction.plotCompositeView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), objFabric.getIntWarp(), objFabric.getIntWeft());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( bufferedImage, "png", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        objFabric.setBytFabricIcon(imageInByte);
        clothIV.setUserData(objFabric);
        System.gc();
    } catch (SQLException ex) {
        new Logging("SEVERE",ClothView.class.getName(),"editArtwork() : Error while viewing composte view",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }   catch (Exception ex) {
        new Logging("SEVERE",ClothView.class.getName(),"editArtwork() : Error while viewing composte view",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
    return bufferedImage;
  }
  private BufferedImage editPattern(ImageView clothIV){
    BufferedImage bufferedImage = new BufferedImage((int)clothIV.getFitHeight(), (int)clothIV.getFitWidth(), BufferedImage.TYPE_INT_ARGB);
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONTHREADPATTERNEDIT"));
        objConfiguration.strWindowFlowContext = "FabricEditor";                        
        UserAction objUserAction = new UserAction();
        objUserAction.getConfiguration(objConfiguration);
        objConfiguration.clothRepeat();
        objFabric=(Fabric) clothIV.getUserData();
        PatternView objPatternView = new PatternView(objFabric);
        FabricAction objFabricAction = new FabricAction();
        bufferedImage = objFabricAction.plotCompositeView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), objFabric.getIntWarp(), objFabric.getIntWeft());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( bufferedImage, "png", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        objFabric.setBytFabricIcon(imageInByte);
        clothIV.setUserData(objFabric);
        System.gc();
    } catch (SQLException ex) {
        new Logging("SEVERE",ClothView.class.getName(),"editPattern() : Error while viewing composte view",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }   catch (Exception ex) {
        new Logging("SEVERE",ClothView.class.getName(),"editPattern() : Error while viewing composte view",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
    return bufferedImage;
}
  private BufferedImage editDensity(ImageView clothIV){
    BufferedImage bufferedImage = new BufferedImage((int)clothIV.getFitHeight(), (int)clothIV.getFitWidth(), BufferedImage.TYPE_INT_ARGB);
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONDENSITYEDIT"));
        objConfiguration.strWindowFlowContext = "FabricEditor";                        
        UserAction objUserAction = new UserAction();
        objUserAction.getConfiguration(objConfiguration);
        objConfiguration.clothRepeat();
        objFabric=(Fabric) clothIV.getUserData();
        DensityView objDensityView = new DensityView(objFabric);
        objFabric.setIntHPI(objFabric.getIntEPI());
        FabricAction objFabricAction = new FabricAction();
        bufferedImage = objFabricAction.plotCompositeView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), objFabric.getIntWarp(), objFabric.getIntWeft());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( bufferedImage, "png", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        objFabric.setBytFabricIcon(imageInByte);
        clothIV.setUserData(objFabric);
        System.gc();
    } catch (SQLException ex) {
        new Logging("SEVERE",ClothView.class.getName(),"editDensity() : Error while viewing composte view",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }   catch (Exception ex) {
        new Logging("SEVERE",ClothView.class.getName(),"editDensity() : Error while viewing composte view",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
    return bufferedImage;
} 
private BufferedImage getFabricIcon(Fabric objFabricCall){
      BufferedImage bufferedImage = null;
        try {
            FabricAction objFabricAction = new FabricAction();
            //objFabricAction.getFabric(objFabricCall);
            byte[] bytFabricThumbnil = (byte[])objFabricCall.getBytFabricIcon();
            SeekableStream stream = new ByteArraySeekableStream(bytFabricThumbnil);
            String[] names = ImageCodec.getDecoderNames(stream);
            ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
            RenderedImage im = dec.decodeAsRenderedImage();
            bufferedImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
            objArtworkAction = new ArtworkAction();
            bufferedImage=objArtworkAction.getImageRotation(bufferedImage, "CLOCKWISE");
            bytFabricThumbnil = null;
        } catch (SQLException ex) {
            new Logging("SEVERE",ClothView.class.getName(),"getFabricIcon(): "+ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",ClothView.class.getName(),"getFabricIcon(): "+ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }    
        return bufferedImage;
    }
    
    private BufferedImage resizeFabricIcon(BufferedImage bufferedImageCall, int w, int h){
        BufferedImage bufferedImageesize = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);        
        Graphics2D g = bufferedImageesize.createGraphics();
        g.drawImage(bufferedImageCall, 0, 0, w, h, null);
        g.dispose();
        return bufferedImageesize;
    }
    
    private BufferedImage getImage(BufferedImage bufferedImage, int w, int h){
        try{
            if(bufferedImage.getHeight()>h){
                double ratio = (double)bufferedImage.getWidth()/bufferedImage.getHeight();
                BufferedImage bufferedImageesize = new BufferedImage((int)(h*ratio), (int)(h),BufferedImage.TYPE_INT_RGB);        
                Graphics2D g = bufferedImageesize.createGraphics();
                g.drawImage(bufferedImage, 0, 0, (int)(h*ratio), (int)(h), null);
                g.dispose();
                bufferedImage = bufferedImageesize;
                bufferedImageesize = null;        
            } 
            if(bufferedImage.getWidth()>w){
                double ratio = (double)bufferedImage.getWidth()/bufferedImage.getHeight();
                BufferedImage bufferedImageesize = new BufferedImage((int)(w), (int)(w/ratio),BufferedImage.TYPE_INT_RGB);        
                Graphics2D g = bufferedImageesize.createGraphics();
                g.drawImage(bufferedImage, 0, 0, (int)(w), (int)(w/ratio), null);
                g.dispose();
                bufferedImage = bufferedImageesize;
                bufferedImageesize = null;
            }
            objArtworkAction = new ArtworkAction();
            ArrayList colors = objArtworkAction.getImageColor(bufferedImage);
            objArtworkAction = new ArtworkAction();
            int[][] imageMatrix = objArtworkAction.getImageToMatrix(bufferedImage);
            int height=(int)imageMatrix.length;
            int width=(int)imageMatrix[0].length;
            int[][] repeatMatrix = new int[h][w];
            for(int i = 0; i < h; i++) {
                for(int j = 0; j < w; j++) {
                    if(i>=height && j<width){
                        repeatMatrix[i][j] = imageMatrix[i%height][j];
                    }else if(i<height && j>=width){
                        repeatMatrix[i][j] = imageMatrix[i][j%width];
                    }else if(i>=height && j>=width){
                        repeatMatrix[i][j] = imageMatrix[i%height][j%width];
                    }else{
                        repeatMatrix[i][j] = imageMatrix[i][j];
                    }                
                }
            }
            bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            objArtworkAction = new ArtworkAction();
            bufferedImage = objArtworkAction.getImageFromMatrix(repeatMatrix, colors);
        } catch (SQLException ex) {
            new Logging("SEVERE",ClothView.class.getName(),"getImage(): "+ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",ClothView.class.getName(),"getImage(): "+ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }    
        System.gc();
        return bufferedImage;
    }
 
    public BufferedImage redesign(BufferedImage myimage, int vAlign, int hAling, int nw, int nh, int width, int height){
        try {
            //redesign(bufferedImage, 1,1, Integer.parseInt(widthNewTxt.getText()),Integer.parseInt(heightNewTxt.getText()),300,300);
            String mypath=Paths.get("src").toAbsolutePath().normalize().toString()+"\\media";
            //myimage = ImageIO.read(new File(mypath+"\\stop1.jpg"));
            
            // get image resize
            BufferedImage texture = new BufferedImage(nw,nh,BufferedImage.TYPE_INT_RGB);
            Graphics2D g1 = texture.createGraphics();
            g1.drawImage(myimage, 0, 0, nw, nh, null);
            g1.dispose();
            // get image to matrix
            int [][] bufferRGB=new int[nw][nh];
            for(int x=0; x<nw; x++){
                for(int y=0; y<nh; y++){
                    bufferRGB[x][y]=texture.getRGB(x, y);
                }
            }
            //orentation and alingment
            int WIDTH=nw*vAlign;
            int HEIGHT=nh*hAling;
            int[][] bufferRGBN=new int[WIDTH][HEIGHT];
            if(vAlign < hAling){// 1/2 1/3 1/4 1/5 1/6
                WIDTH=nw*(1+(int)(vAlign/hAling));
                HEIGHT=nh*hAling;
                bufferRGBN=new int[WIDTH][HEIGHT];
                int ny=0;
                for(double z=0; z<1; z=z+((double)vAlign/hAling)){
                    for(int y=0; y<nh; y++,ny++){
                        for(int x=0; x<WIDTH; x++){
                            bufferRGBN[x%WIDTH][ny%HEIGHT]=bufferRGB[(x+(int)(nw*z))%nw][y%nh];
                        }
                    }
                }   
            }else if(vAlign > hAling){ // 2/1 3/1 4/1 5/1 6/1
                WIDTH=nw*vAlign;
                HEIGHT=nh*(1+(int)(hAling/vAlign));
                bufferRGBN=new int[WIDTH][HEIGHT];
                int nx=0;
                for(double z=0; z<1; z=z+((double)hAling/vAlign)){
                    for(int x=0; x<nw; x++,nx++){
                        for(int y=0; y<HEIGHT; y++){                    
                            bufferRGBN[nx%WIDTH][y%HEIGHT]=bufferRGB[x%nw][(y+(int)(nh*z))%nh];
                        }
                    }
                }   
            }else{// 1/1
                for(int x=0; x<WIDTH; x++){
                    for(int y=0; y<HEIGHT; y++){
                        bufferRGBN[x%WIDTH][y%HEIGHT]=bufferRGB[x%nw][y%nh];
                    }
                }
            }
            //repeat matrix
            bufferRGB=new int[width][height];
            for(int i = 0; i < width; i++) {
                for(int j = 0; j < height; j++) {
                    if(i>=WIDTH && j<HEIGHT){
                        bufferRGB[i][j] = bufferRGBN[i%WIDTH][j];
                    }else if(i<WIDTH && j>=HEIGHT){
                        bufferRGB[i][j] = bufferRGBN[i][j%HEIGHT];
                    }else if(i>=WIDTH && j>=HEIGHT){
                        bufferRGB[i][j] = bufferRGBN[i%WIDTH][j%HEIGHT];
                    }else{
                        bufferRGB[i][j] = bufferRGBN[i][j];
                    }
                }
            }
            //get image from matrix
            myimage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            for(int x=0; x<width; x++){
                for(int y=0; y<height; y++){
                    myimage.setRGB(x, y,bufferRGB[x][y]);
                }                
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ClothView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myimage;
    }
    
    public BufferedImage plotCompositeView(Fabric objFabric){
        try {
            lblStatus.setText(objDictionaryAction.getWord("GETCOMPOSITEVIEW"));
            int intHeight = objFabric.getIntWeft();
            int intLength = objFabric.getIntWarp();
            BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            FabricAction objFabricAction = new FabricAction();
            bufferedImage = objFabricAction.plotCompositeView(objFabric, objFabric.getIntWarp(), objFabric.getIntWeft(), intLength, intHeight);
            /*if((objFabric.getIntEPI()/objFabric.getIntPPI())<=0){
                intHeight = (int)(intLength*(objFabric.getIntPPI()/objFabric.getIntEPI())*zoomfactor);
                intLength = (int)(intLength*zoomfactor);
            }else{
                intLength = (int)(intHeight*(objFabric.getIntEPI()/objFabric.getIntPPI())*zoomfactor);
                intHeight = (int)(intHeight*zoomfactor);
            }*/
            intLength = (int)(((objConfiguration.getIntDPI()*objFabric.getIntWarp())/objFabric.getIntEPI()));
            intHeight = (int)(((objConfiguration.getIntDPI()*objFabric.getIntWeft())/objFabric.getIntPPI()));
            BufferedImage bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            bufferedImage = bufferedImageesize;
            if(objConfiguration.getBlnMRepeat()){
                objArtworkAction = new ArtworkAction();
                bufferedImageesize = objArtworkAction.getImageRepeat(bufferedImage, objConfiguration.getIntVRepeat(), objConfiguration.getIntHRepeat());
            }
            return bufferedImageesize;
        } catch (SQLException ex) {
            new Logging("SEVERE",ClothView.class.getName(),"plotCompositeView() : Error while viewing composte view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",ClothView.class.getName(),"plotCompositeView() : Error while viewing composte view",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
        return null;
    }
    
    public void componentDragAndSave(final ImageView component){
        component.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                isPaneDragable=true;
                xInitialPane=event.getX();
                yInitialPane=event.getY();
            }
        });
        component.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(!isPaneDragable)
                    return;                
                if(event.getX()<0||event.getX()>(component.getFitWidth()-1)){
                    isPaneDragable=false;
                    return;
                }                
                if(event.getY()<0||event.getY()>(component.getFitHeight()-1)){
                    isPaneDragable=false;
                    return;
                }
                
                int partWidth=(int)Math.abs(event.getX()-xInitialPane);
                int partHeight=(int)Math.abs(event.getY()-yInitialPane);
                if(partWidth>0&&partHeight>0){
                    if(xInitialPane>=0 && event.getX()>=0 && yInitialPane>=0 && event.getY()>=0){
                        PixelReader reader = component.getImage().getPixelReader();
                        WritableImage tempImage = new WritableImage(reader, (int)xInitialPane, (int)yInitialPane, partWidth, partHeight);
                        
                        final Stage dialogStage = new Stage();
                        dialogStage.initStyle(StageStyle.UTILITY);
                        dialogStage.initModality(Modality.APPLICATION_MODAL);
                        GridPane popup=new GridPane();
                        popup.setId("popup");
                        popup.setHgap(5);
                        popup.setVgap(5);

                        final ImageView tempIV=new ImageView();
                        tempIV.setImage(tempImage);
                        tempIV.setFitWidth(partWidth);
                        tempIV.setFitHeight(partHeight);
                        popup.add(tempIV, 0, 0, 2, 1);
                        
                        Label lblName = new Label(objDictionaryAction.getWord("NAME"));
                        lblName.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNAME")));
                        popup.add(lblName, 0, 1);
                        final TextField txtName = new TextField();
                        txtName.setPromptText(objDictionaryAction.getWord("TOOLTIPNAME"));
                        txtName.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNAME")));
                        popup.add(txtName, 1, 1);

                        Button btnSave = new Button(objDictionaryAction.getWord("SAVEAS"));
                        btnSave.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save_as.png"));
                        btnSave.setTooltip(new Tooltip(txtName.getText()+": "+objDictionaryAction.getWord("TOOLTIPSAVEAS")));
                        btnSave.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                try{
                                    lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEAS"));
                                    BufferedImage bufferedImage=SwingFXUtils.fromFXImage(tempIV.getImage(), null);    
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    ImageIO.write(bufferedImage, "png", baos);
                                    
                                    //baos.flush();
                                    byte[] imageInByte = baos.toByteArray();
                                    Artwork objArtwork=new Artwork();
                                    objArtwork.setObjConfiguration(objConfiguration);
                                    String strArtworkID = new IDGenerator().getIDGenerator("ARTWORK_LIBRARY", objConfiguration.getObjUser().getStrUserID());
                                    objArtwork.setStrArtworkId(strArtworkID);
                                    objArtwork.setStrArtworkName(txtName.getText());
                                    objArtwork.setBytArtworkThumbnil(imageInByte);
                                    
                                    int pixel = bufferedImage.getRGB(0, 0); 
                                    int red   = (pixel & 0x00ff0000) >> 16;
                                    int green = (pixel & 0x0000ff00) >> 8;
                                    int blue  =  pixel & 0x000000ff;                    
                                    
                                    java.awt.Color color = new java.awt.Color(red,green,blue);
                                    String strColor = String.format( "#%02X%02X%02X",
                                                    (int)( color.getRed() ),
                                                    (int)( color.getGreen() ),
                                                    (int)( color.getBlue() ) );
                                    objArtwork.setStrArtworkBackground(strColor);
                                    ArtworkAction objArtworkAction=new ArtworkAction();
                                    if(objArtworkAction.setTempArtwork(objArtwork)!=0){
                                        lblStatus.setText(objArtwork.getStrArtworkName()+" : "+objDictionaryAction.getWord("DATASAVED"));
                                    }else{
                                        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                    }
                                    imageInByte = null;
                                    bufferedImage=null;
                                    baos.close();
                                    System.gc();
                                    dialogStage.close();
                                } catch(Exception ex){
                                    new Logging("SEVERE",ClothView.class.getName(),"componentDragAndSave(): Mouse Released: "+ex.toString(),ex);
                                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                }
                            }
                        });
                        popup.add(btnSave, 0, 2);
                        
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
                        popup.add(btnCancel, 1, 2);
                        
                        Scene scene = new Scene(popup);
                        scene.getStylesheets().add(ClothView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                        dialogStage.setScene(scene);
                        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("SAVE"));
                        dialogStage.showAndWait();        
                    }
                }
                isPaneDragable=false;
            }
        });
    }
    
    public void componentContextMenu(final ScrollPane component,final String strSearchBy){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem componentFabricAssignment = new MenuItem(objDictionaryAction.getWord("FABRICASSINGMENTCM")); //simple
        componentFabricAssignment.setGraphic(new ImageView(objConfiguration.getStrColour()+"/open.png"));
        MenuItem componentFabricCreator = new MenuItem(objDictionaryAction.getWord("FABRICCREATORCM")); //simple
        componentFabricCreator.setGraphic(new ImageView(objConfiguration.getStrColour()+"/new.png"));
        MenuItem componentFabricEditor = new MenuItem(objDictionaryAction.getWord("USEINFABRIC")); //simple
        componentFabricEditor.setGraphic(new ImageView(objConfiguration.getStrColour()+"/fabric_editor.png"));
        MenuItem componentArtworkAssignment = new MenuItem(objDictionaryAction.getWord("ARTWORKASSINGMENTCM"));
        componentArtworkAssignment.setGraphic(new ImageView(objConfiguration.getStrColour()+"/artwork_editor.png"));
        MenuItem componentYarnAssignment = new MenuItem(objDictionaryAction.getWord("YARNCM"));
        componentYarnAssignment.setGraphic(new ImageView(objConfiguration.getStrColour()+"/yarn.png"));
        MenuItem componentThreadPattern = new MenuItem(objDictionaryAction.getWord("THREADPATTERNCM"));
        componentThreadPattern.setGraphic(new ImageView(objConfiguration.getStrColour()+"/thread_pattern.png"));
        MenuItem componentThreadDensity = new MenuItem(objDictionaryAction.getWord("DENSITYEDIT"));
        componentThreadDensity.setGraphic(new ImageView(objConfiguration.getStrColour()+"/density.png"));
        MenuItem componentOrientation = new MenuItem(objDictionaryAction.getWord("FABRICORIENTATIONCM")); //alt
        componentOrientation.setGraphic(new ImageView(objConfiguration.getStrColour()+"/orientation.png"));
        MenuItem componentDescription = new MenuItem(objDictionaryAction.getWord("FABRICDESCRIPTIONCM")); //alt
        componentDescription.setGraphic(new ImageView(objConfiguration.getStrColour()+"/technical_info.png"));
        contextMenu.getItems().addAll(componentFabricAssignment,componentFabricEditor, componentArtworkAssignment, componentYarnAssignment, componentThreadPattern, componentOrientation, componentDescription);
        component.setContextMenu(contextMenu);
        
        componentFabricAssignment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONFABRICASSINGMENTEDIT"));
                componentFabricAssignment((ImageView)component.getContent(),strSearchBy);
            }
        });
        componentFabricEditor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("TOOLTIPAPUSEINFABRIC"));
                componentFabricEditor((ImageView)component.getContent(),strSearchBy);
            }
        });
        componentArtworkAssignment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONARTWORKASSINGMENTEDIT"));
                componentProperty((ImageView)component.getContent(),strSearchBy, "Artwork");
            }
        });
        componentYarnAssignment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONYARNEDIT"));
                componentProperty((ImageView)component.getContent(),strSearchBy, "Yarn");
            }
        });
        componentThreadPattern.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONTHREADPATTERNEDIT"));
                componentProperty((ImageView)component.getContent(),strSearchBy, "Thread");
            }
        });
        componentThreadDensity.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONTHREADPATTERNEDIT"));
                componentProperty((ImageView)component.getContent(),strSearchBy, "Density");
            }
        });
        componentOrientation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONFABRICORIENTATIONEDIT"));
                componentOrientation((ImageView)component.getContent(),strSearchBy);
            }
        });    
        componentDescription.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONFABRICDESCRIPTIONEDIT"));
                componentDescription((ImageView)component.getContent(),strSearchBy);
            }
        });    
    }

    // previously simple click 
    public void componentFabricAssignment(ImageView component, String strSearchBy){
        try{
            objFabric = new Fabric();
            String fabId="";
            objFabric.setStrSearchBy(strSearchBy);
            if(component.getUserData()!=null){
                fabId=((Fabric)component.getUserData()).getStrFabricID();
                /* Bug Fixed: Change Yarn Properties. Assign Fabric (close). -> Yarns Reverted Back 
                (due to again fetching fabric from database, getFabric()) */
                objFabric.setStrFabricID((((Fabric)component.getUserData())).getStrFabricID());
            }
            objFabric.setObjConfiguration(objConfiguration);
            FabricImportView objFabricImportView= new FabricImportView(objFabric);
            FabricAction objFabricAction=new FabricAction();
            if(component.getUserData()!=null&&(fabId==objFabric.getStrFabricID())){
                /* This is the case when a fabric was previously assigned and
                during re-assignment user closes/cancels the dialog without assigning a fabric.
                In such a case previous fabric should be retained as it is. */
                objFabric=(Fabric)component.getUserData();
            }
            else if(component.getUserData()==null&&objFabric.getStrFabricID()==null){
                /* DO NOTHING */
                /* This is the case when no fabric was assigned previously and
                during assignment user closes/cancels the dialog without assigning a fabric.
                In such a case no fabric should be assigned */
            }
            else{
                objFabricAction.getFabric(objFabric);
                if(objFabric.getStrFabricID()!=null){
                    assignFabricToComponent(component,objFabric);
                }
                objFabric = null;
            }
            System.gc();
        } catch(Exception ex){
            new Logging("SEVERE",ClothView.class.getName(),"componentFabricAssignment(): "+getComponentName(component),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    /**
     * This function is needed to eliminate fractional repeats in components.
     * @param newSize this is width/height of a repeat from assignFabricToComponent()
     * @param componentSize this is width/height of component
     * @return updated width/height so as to produce integral repeats
     */
    public int roundIntegralRepeatSize(int newSize, int componentSize){
        if(newSize > componentSize){
            /* If size of repeat exceeds component, fit one repeat only to component */
            return componentSize;
        }
        else{
            /* As repeats > 1 (may be 1.1 etc.), need to round to nearest integer */
            double dblRepeats = (double)componentSize/(double)newSize;
            int intRepeats = (int) Math.round(dblRepeats);
            return (int)(componentSize/intRepeats);
        }
    }
	
    public void assignFabricToComponent(ImageView component, Fabric objFabricCall){
        if(objFabricCall.getBytFabricIcon()==null)
            return;
        //----------------------
        try{
            objFabricCall.setObjConfiguration(objConfiguration);
            FabricAction objFabricAction = new FabricAction();
            Weave objWeave = new Weave();
            objWeave.setObjConfiguration(objConfiguration);
            objWeave.setStrWeaveID(objFabricCall.getStrBaseWeaveID());
            WeaveAction objWeaveAction = new WeaveAction();
            objWeaveAction.getWeave(objWeave);
            objWeaveAction = new WeaveAction(objWeave,true); 
            objWeaveAction.extractWeaveContent(objWeave);             
            objFabricCall.setBaseWeaveMatrix(objWeave.getDesignMatrix());
            if(objFabricCall.getStrArtworkID()!=null){                
                Artwork objArtwork = new Artwork();
                objArtwork.setObjConfiguration(objConfiguration);
                objArtwork.setStrArtworkId(objFabricCall.getStrArtworkID());
                objFabricCall.setArtworkMatrix(objFabricCall.getFabricMatrix());
                objFabricAction = new FabricAction();
                objFabricAction.getFabricArtwork(objFabricCall);
                YarnAction objYarnAction = new YarnAction();                
                objFabricCall.setWarpExtraYarn(objYarnAction.getFabricYarn(objFabricCall.getStrFabricID(), "Extra Warp"));
                objYarnAction = new YarnAction();
                objFabricCall.setWeftExtraYarn(objYarnAction.getFabricYarn(objFabricCall.getStrFabricID(), "Extra Weft"));
            }else{
                objFabricCall.setWarpExtraYarn(null);
                objFabricCall.setIntExtraWarp(objConfiguration.getIntExtraWarp());
                objFabricCall.setWeftExtraYarn(null);
                objFabricCall.setIntExtraWeft(objConfiguration.getIntExtraWeft());
            }
            objFabricAction = new FabricAction();
            objFabricCall.setThreadPaletes(objFabricAction.getFabricPallets(objFabricCall));
            YarnAction objYarnAction = new YarnAction();
            objFabricCall.setWarpYarn(objYarnAction.getFabricYarn(objFabricCall.getStrFabricID(), "Warp"));
            objYarnAction = new YarnAction();
            objFabricCall.setWeftYarn(objYarnAction.getFabricYarn(objFabricCall.getStrFabricID(), "Weft"));
            objFabricCall.setObjConfiguration(objConfiguration);

        }catch(Exception ex){}
        // this assigned id will be used for reOrienting Image
        if(component.getUserData()!=null){
            if(((Fabric)component.getUserData()).equals(objFabricCall)){/*-do- nothing, retain original Id*/}
            else
                component.setId("RMRectangular (Default)/RM:WP"+objFabricCall.getIntWarp()+"/WP:WF"+objFabricCall.getIntWeft()+"/WF:RA0/RA");
        }
        else{
            component.setId("RMRectangular (Default)/RM:WP"+objFabricCall.getIntWarp()+"/WP:WF"+objFabricCall.getIntWeft()+"/WF:RA0/RA");
        }
        BufferedImage  bufferedImage = null;
        BufferedImage bufferedImageTwin = null;
        if((int)(objFabricCall.getDblArtworkWidth()*pinch)<MINSIZE){
            lblStatus.setText(objDictionaryAction.getWord("TOOSMALLFABRIC"));
            return;
        }
        //if(component.equals(leftBorder)||component.equals(tBorder))
        if(component.equals(leftBorder)){
            this.mapFabricId.put("Border", objFabric.getStrFabricID());
            // getting original icon of fabric
            bufferedImage=getFabricIcon(objFabricCall);
            // calculating aspect ratio of original image
            double ratio = (double)bufferedImage.getWidth()/bufferedImage.getHeight();
            if((int)(objFabricCall.getDblArtworkWidth()*pinch)>MAXBORDER){
                lblStatus.setText(objDictionaryAction.getWord("TOOLARGEFABRIC"));
                return;
            }
            int newWidthOfRepeat=(int)(objFabricCall.getDblArtworkWidth()*pinch);
            heights[0]=newWidthOfRepeat;
            if(leftBorderMultiRepeatPolicy){
                if((newWidthOfRepeat*repeats[0])>MAXBORDER){
                    lblStatus.setText(objDictionaryAction.getWord("TOOMANYREPEATS"));
                    return;
                }
                heights[0]=newWidthOfRepeat*repeats[0];
            }
            detectEnabledComponents();
            updateWidthHeight("Left Border");
            recalculateBody();
            setComponentWidthHeight();
            enableAllComponents(false); // needed to disable components enabled by detectEnabledComponents()
            // calculating updated length of single repeat based on aspect ratio
            ///int newLengthOfRepeat=(int) (newWidthOfRepeat*ratio);
            int newLengthOfRepeat=(int)(objFabricCall.getDblArtworkLength()*pinch);
            // resize original icon so as to fit in resized component
            bufferedImage=resizeFabricIcon(bufferedImage, newLengthOfRepeat, newWidthOfRepeat);
            // saving this icon for making repeats later
            leftBorderPane.setUserData(bufferedImage);

            if(borderLinkPolicy){
                // setting same icon (rotated bu 180 degrees) for rightBorder
                bufferedImageTwin=getRightRotatedImage(bufferedImage, 180);
                rightBorderPane.setUserData(bufferedImageTwin);
            }
    
            // getting repeats to fit into border width (ImageView.getHeight())
            bufferedImage=getImage(bufferedImage, (int)component.getFitWidth(), (int)component.getFitHeight());
            // saving entire fabric object to use later (in yarn assignment and orientation)
            component.setUserData(objFabricCall);
            // setting image to component
            component.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            if(borderLinkPolicy){
                // ripple effect on rightBorder
                rightBorderPane.setPrefHeight(component.getFitHeight());
                rightBorderPane.setPrefWidth(component.getFitWidth());
                rightBorder.setFitHeight(component.getFitHeight());
                rightBorder.setFitWidth(component.getFitWidth());
                rightBorder.setUserData(objFabricCall);
                rightBorder.setId(component.getId());
                //Tooltip.install(rightBorder, new Tooltip(((rightBorder.getUserData()!=null)?("Showing "+String.valueOf((int)(((rightBorder.getFitHeight()/newWidthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Border in this View.\nFor Complete Border switch to Full View."):"")));
            }
            //Tooltip.install(component, new Tooltip(((component.getUserData()!=null)?("Showing "+String.valueOf((int)(((component.getFitHeight()/newWidthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Border in this View.\nFor Complete Border switch to Full View."):"")));
            if(borderLinkPolicy){
                // setting same image (rotated bu 180 degrees) for rightBorder
                bufferedImageTwin=getRightRotatedImage(bufferedImage, 180);
                rightBorder.setImage(SwingFXUtils.toFXImage(bufferedImageTwin, null));
            }
        }
        else if(component.equals(rightBorder)){
            this.mapFabricId.put("Border", objFabric.getStrFabricID());
            bufferedImage=getFabricIcon(objFabricCall);
            double ratio = (double)bufferedImage.getWidth()/bufferedImage.getHeight();
            if((int)(objFabricCall.getDblArtworkWidth()*pinch)>MAXBORDER){
                lblStatus.setText(objDictionaryAction.getWord("TOOLARGEFABRIC"));
                return;
            }
            int newWidthOfRepeat=(int)(objFabricCall.getDblArtworkWidth()*pinch);
            heights[10]=newWidthOfRepeat;
            if(rightBorderMultiRepeatPolicy){
                if((newWidthOfRepeat*repeats[10])>MAXBORDER){
                    lblStatus.setText(objDictionaryAction.getWord("TOOMANYREPEATS"));
                    return;
                }
                heights[10]=newWidthOfRepeat*repeats[10];
            }
            detectEnabledComponents();
            updateWidthHeight("Right Border");
            recalculateBody();
            setComponentWidthHeight();
            enableAllComponents(false);
            
            ///int newLengthOfRepeat=(int) (newWidthOfRepeat*ratio);
            int newLengthOfRepeat=(int)(objFabricCall.getDblArtworkLength()*pinch);
            bufferedImage=resizeFabricIcon(bufferedImage, newLengthOfRepeat, newWidthOfRepeat);
            if(borderLinkPolicy){
                leftBorderPane.setUserData(bufferedImage);
                bufferedImageTwin=getRightRotatedImage(bufferedImage, 180);
                rightBorderPane.setUserData(bufferedImageTwin);
            }
            else{
                bufferedImageTwin=getRightRotatedImage(bufferedImage, 180);
                rightBorderPane.setUserData(bufferedImageTwin);
            }
            bufferedImage=getImage(bufferedImage, (int)component.getFitWidth(), (int)component.getFitHeight());
            component.setUserData(objFabricCall);
            if(borderLinkPolicy){
                leftBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                leftBorderPane.setPrefHeight(component.getFitHeight());
                leftBorderPane.setPrefWidth(component.getFitWidth());
                leftBorder.setFitHeight(component.getFitHeight());
                leftBorder.setFitWidth(component.getFitWidth());
                leftBorder.setUserData(objFabricCall);
                leftBorder.setId(component.getId());
                //Tooltip.install(leftBorder, new Tooltip(((leftBorder.getUserData()!=null)?("Showing "+String.valueOf((int)(((leftBorder.getFitHeight()/newWidthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Border in this View.\nFor Complete Border switch to Full View."):"")));
            }
            else{
                bufferedImageTwin=getRightRotatedImage(bufferedImage, 180);
                rightBorder.setImage(SwingFXUtils.toFXImage(bufferedImageTwin, null));
            }
            //Tooltip.install(component, new Tooltip(((component.getUserData()!=null)?("Showing "+String.valueOf((int)(((component.getFitHeight()/newWidthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Border in this View.\nFor Complete Border switch to Full View."):"")));
            if(borderLinkPolicy){
                bufferedImageTwin=getRightRotatedImage(bufferedImage, 180);
                rightBorder.setImage(SwingFXUtils.toFXImage(bufferedImageTwin, null));
            }
        }
        else if(component.equals(leftCrossBorder)){
            this.mapFabricId.put("Cross Border", objFabric.getStrFabricID());
            // getting original icon of fabric
            bufferedImage=getFabricIcon(objFabricCall);
            // calculating aspect ratio of original image
            double ratio = (double)bufferedImage.getWidth()/bufferedImage.getHeight();
            if((int)(objFabricCall.getDblArtworkLength()*pinch)>MAXBORDER){
                lblStatus.setText(objDictionaryAction.getWord("TOOLARGEFABRIC"));
                return;
            }
            int newLengthOfRepeat=(int)(objFabricCall.getDblArtworkLength()*pinch);
            widths[2]=newLengthOfRepeat;
            if(leftCrossBorderMultiRepeatPolicy){
                if((newLengthOfRepeat*repeats[2])>MAXBORDER){
                    lblStatus.setText(objDictionaryAction.getWord("TOOMANYREPEATS"));
                    return;
                }
                widths[2]=newLengthOfRepeat*repeats[2];
            }
            detectEnabledComponents();
            updateWidthHeight("Left Cross Border");
            recalculateBody();
            setComponentWidthHeight();
            enableAllComponents(false);
            // calculating updated length of single repeat based on aspect ratio
            ///int newWidthOfRepeat=(int) (newLengthOfRepeat/ratio);
            int newWidthOfRepeat=(int) (objFabricCall.getDblArtworkWidth()*pinch);
            // resize original icon so as to fit in resized component
            bufferedImage=resizeFabricIcon(bufferedImage, newLengthOfRepeat, newWidthOfRepeat);
            // saving this icon for making repeats later
            leftCrossBorderPane.setUserData(bufferedImage);
            if(crossBorderLinkPolicy){
                rightCrossBorderPane.setUserData(bufferedImage);
                rightsideLeftCrossBorderPane.setUserData(bufferedImage);
                rightsideRightCrossBorderPane.setUserData(bufferedImage);
            } 
            // getting repeats
            bufferedImage=getImage(bufferedImage, (int)component.getFitWidth(), (int)component.getFitHeight());
            // saving entire fabric object to use later (in yarn assignment and orientation)
            component.setUserData(objFabricCall);
            // setting image to component
            component.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            if(crossBorderLinkPolicy){
                // ripple effect on rightBorder
                rightCrossBorderPane.setPrefHeight(component.getFitHeight());
                rightCrossBorderPane.setPrefWidth(component.getFitWidth());
                rightCrossBorder.setFitHeight(component.getFitHeight());
                rightCrossBorder.setFitWidth(component.getFitWidth());
                rightCrossBorder.setUserData(objFabricCall);
                rightCrossBorder.setId(component.getId());
                //Tooltip.install(rightCrossBorder, new Tooltip(((rightCrossBorder.getUserData()!=null)?("Showing "+String.valueOf((int)(((rightCrossBorder.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Cross Border in this View.\nFor Complete Cross Border switch to Full View."):"")));
                // ripple effect on rightsideLeftCrossBorder
                rightsideLeftCrossBorderPane.setPrefHeight(component.getFitHeight());
                rightsideLeftCrossBorderPane.setPrefWidth(component.getFitWidth());
                rightsideLeftCrossBorder.setFitHeight(component.getFitHeight());
                rightsideLeftCrossBorder.setFitWidth(component.getFitWidth());
                rightsideLeftCrossBorder.setUserData(objFabricCall);
                rightsideLeftCrossBorder.setId(component.getId());
                //Tooltip.install(rightsideLeftCrossBorder, new Tooltip(((rightsideLeftCrossBorder.getUserData()!=null)?("Showing "+String.valueOf((int)(((rightsideLeftCrossBorder.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Cross Border in this View.\nFor Complete Cross Border switch to Full View."):"")));
                // ripple effect on rightsideRightCrossBorder
                rightsideRightCrossBorderPane.setPrefHeight(component.getFitHeight());
                rightsideRightCrossBorderPane.setPrefWidth(component.getFitWidth());
                rightsideRightCrossBorder.setFitHeight(component.getFitHeight());
                rightsideRightCrossBorder.setFitWidth(component.getFitWidth());
                rightsideRightCrossBorder.setUserData(objFabricCall);
                rightsideRightCrossBorder.setId(component.getId());
                //Tooltip.install(rightsideRightCrossBorder, new Tooltip(((rightsideRightCrossBorder.getUserData()!=null)?("Showing "+String.valueOf((int)(((rightsideRightCrossBorder.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Cross Border in this View.\nFor Complete Cross Border switch to Full View."):"")));
            }
            //Tooltip.install(component, new Tooltip(((component.getUserData()!=null)?("Showing "+String.valueOf((int)(((component.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Cross Border in this View.\nFor Complete Cross Border switch to Full View."):"")));
            if(crossBorderLinkPolicy){
                rightCrossBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                rightsideLeftCrossBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                rightsideRightCrossBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            }
        }
        else if(component.equals(rightCrossBorder)){
            this.mapFabricId.put("Cross Border", objFabric.getStrFabricID());
            bufferedImage=getFabricIcon(objFabricCall);
            double ratio = (double)bufferedImage.getWidth()/bufferedImage.getHeight();
            if((int)(objFabricCall.getDblArtworkLength()*pinch)>MAXBORDER){
                lblStatus.setText(objDictionaryAction.getWord("TOOLARGEFABRIC"));
                return;
            }
            int newLengthOfRepeat=(int)(objFabricCall.getDblArtworkLength()*pinch);
            widths[4]=newLengthOfRepeat;
            if(rightCrossBorderMultiRepeatPolicy){
                if((newLengthOfRepeat*repeats[4])>MAXBORDER){
                    lblStatus.setText(objDictionaryAction.getWord("TOOMANYREPEATS"));
                    return;
                }
                widths[4]=newLengthOfRepeat*repeats[4];
            }
            detectEnabledComponents();
            updateWidthHeight("Right Cross Border");
            recalculateBody();
            setComponentWidthHeight();
            enableAllComponents(false);
            ///int newWidthOfRepeat=(int) (newLengthOfRepeat/ratio);
            int newWidthOfRepeat=(int) (objFabricCall.getDblArtworkWidth()*pinch);
            bufferedImage=resizeFabricIcon(bufferedImage, newLengthOfRepeat, newWidthOfRepeat);
            rightCrossBorderPane.setUserData(bufferedImage);
            if(crossBorderLinkPolicy){
                leftCrossBorderPane.setUserData(bufferedImage);
                rightsideLeftCrossBorderPane.setUserData(bufferedImage);
                rightsideRightCrossBorderPane.setUserData(bufferedImage);
            }
            bufferedImage=getImage(bufferedImage, (int)component.getFitWidth(), (int)component.getFitHeight());
            component.setUserData(objFabricCall);
            component.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            if(crossBorderLinkPolicy){
                leftCrossBorderPane.setPrefHeight(component.getFitHeight());
                leftCrossBorderPane.setPrefWidth(component.getFitWidth());
                leftCrossBorder.setFitHeight(component.getFitHeight());
                leftCrossBorder.setFitWidth(component.getFitWidth());
                leftCrossBorder.setUserData(objFabricCall);
                leftCrossBorder.setId(component.getId());
                //Tooltip.install(leftCrossBorder, new Tooltip(((leftCrossBorder.getUserData()!=null)?("Showing "+String.valueOf((int)(((leftCrossBorder.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Cross Border in this View.\nFor Complete Cross Border switch to Full View."):"")));
                rightsideLeftCrossBorderPane.setPrefHeight(component.getFitHeight());
                rightsideLeftCrossBorderPane.setPrefWidth(component.getFitWidth());
                rightsideLeftCrossBorder.setFitHeight(component.getFitHeight());
                rightsideLeftCrossBorder.setFitWidth(component.getFitWidth());
                rightsideLeftCrossBorder.setUserData(objFabricCall);
                rightsideLeftCrossBorder.setId(component.getId());
                //Tooltip.install(rightsideLeftCrossBorder, new Tooltip(((rightsideLeftCrossBorder.getUserData()!=null)?("Showing "+String.valueOf((int)(((rightsideLeftCrossBorder.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Cross Border in this View.\nFor Complete Cross Border switch to Full View."):"")));
                rightsideRightCrossBorderPane.setPrefHeight(component.getFitHeight());
                rightsideRightCrossBorderPane.setPrefWidth(component.getFitWidth());
                rightsideRightCrossBorder.setFitHeight(component.getFitHeight());
                rightsideRightCrossBorder.setFitWidth(component.getFitWidth());
                rightsideRightCrossBorder.setUserData(objFabricCall);
                rightsideRightCrossBorder.setId(component.getId());
                //Tooltip.install(rightsideRightCrossBorder, new Tooltip(((rightsideRightCrossBorder.getUserData()!=null)?("Showing "+String.valueOf((int)(((rightsideRightCrossBorder.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Cross Border in this View.\nFor Complete Cross Border switch to Full View."):"")));
            }
            //Tooltip.install(component, new Tooltip(((component.getUserData()!=null)?("Showing "+String.valueOf((int)(((component.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Cross Border in this View.\nFor Complete Cross Border switch to Full View."):"")));
            if(crossBorderLinkPolicy){
                leftCrossBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                rightsideLeftCrossBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                rightsideRightCrossBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            }
        }
        else if(component.equals(rightsideLeftCrossBorder)){
            this.mapFabricId.put("Cross Border", objFabric.getStrFabricID());
            bufferedImage=getFabricIcon(objFabricCall);
            double ratio = (double)bufferedImage.getWidth()/bufferedImage.getHeight();
            if((int)(objFabricCall.getDblArtworkLength()*pinch)>MAXBORDER){
                lblStatus.setText(objDictionaryAction.getWord("TOOLARGEFABRIC"));
                return;
            }
            int newLengthOfRepeat=(int)(objFabricCall.getDblArtworkLength()*pinch);
            widths[7]=newLengthOfRepeat;
            if(rightsideLeftCrossBorderMultiRepeatPolicy){
                if((newLengthOfRepeat*repeats[7])>MAXBORDER){
                    lblStatus.setText(objDictionaryAction.getWord("TOOMANYREPEATS"));
                    return;
                }
                widths[7]=newLengthOfRepeat*repeats[7];
            }
            detectEnabledComponents();
            updateWidthHeight("Rightside Left Cross Border");
            recalculateBody();
            setComponentWidthHeight();
            enableAllComponents(false);
            ///int newWidthOfRepeat=(int) (newLengthOfRepeat/ratio);
            int newWidthOfRepeat=(int) (objFabricCall.getDblArtworkWidth()*pinch);
            bufferedImage=resizeFabricIcon(bufferedImage, newLengthOfRepeat, newWidthOfRepeat);
            rightsideLeftCrossBorderPane.setUserData(bufferedImage);
            if(crossBorderLinkPolicy){
                leftCrossBorderPane.setUserData(bufferedImage);
                rightCrossBorderPane.setUserData(bufferedImage);
                rightsideRightCrossBorderPane.setUserData(bufferedImage);
            }
            bufferedImage=getImage(bufferedImage, (int)component.getFitWidth(), (int)component.getFitHeight());
            component.setUserData(objFabricCall);
            component.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            if(crossBorderLinkPolicy){
                leftCrossBorderPane.setPrefHeight(component.getFitHeight());
                leftCrossBorderPane.setPrefWidth(component.getFitWidth());
                leftCrossBorder.setFitHeight(component.getFitHeight());
                leftCrossBorder.setFitWidth(component.getFitWidth());
                leftCrossBorder.setUserData(objFabricCall);
                leftCrossBorder.setId(component.getId());
                //Tooltip.install(leftCrossBorder, new Tooltip(((leftCrossBorder.getUserData()!=null)?("Showing "+String.valueOf((int)(((leftCrossBorder.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Cross Border in this View.\nFor Complete Cross Border switch to Full View."):"")));
                rightCrossBorderPane.setPrefHeight(component.getFitHeight());
                rightCrossBorderPane.setPrefWidth(component.getFitWidth());
                rightCrossBorder.setFitHeight(component.getFitHeight());
                rightCrossBorder.setFitWidth(component.getFitWidth());
                rightCrossBorder.setUserData(objFabricCall);
                rightCrossBorder.setId(component.getId());
                //Tooltip.install(rightCrossBorder, new Tooltip(((rightCrossBorder.getUserData()!=null)?("Showing "+String.valueOf((int)(((rightCrossBorder.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Cross Border in this View.\nFor Complete Cross Border switch to Full View."):"")));
                rightsideRightCrossBorderPane.setPrefHeight(component.getFitHeight());
                rightsideRightCrossBorderPane.setPrefWidth(component.getFitWidth());
                rightsideRightCrossBorder.setFitHeight(component.getFitHeight());
                rightsideRightCrossBorder.setFitWidth(component.getFitWidth());
                rightsideRightCrossBorder.setUserData(objFabricCall);
                rightsideRightCrossBorder.setId(component.getId());
                //Tooltip.install(rightsideRightCrossBorder, new Tooltip(((rightsideRightCrossBorder.getUserData()!=null)?("Showing "+String.valueOf((int)(((rightsideRightCrossBorder.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Cross Border in this View.\nFor Complete Cross Border switch to Full View."):"")));
            }
            //Tooltip.install(component, new Tooltip(((component.getUserData()!=null)?("Showing "+String.valueOf((int)(((component.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Cross Border in this View.\nFor Complete Cross Border switch to Full View."):"")));
            if(crossBorderLinkPolicy){
                leftCrossBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                rightCrossBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                rightsideRightCrossBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            }
        }
        else if(component.equals(rightsideRightCrossBorder)){
            this.mapFabricId.put("Cross Border", objFabric.getStrFabricID());
            bufferedImage=getFabricIcon(objFabricCall);
            double ratio = (double)bufferedImage.getWidth()/bufferedImage.getHeight();
            if((int)(objFabricCall.getDblArtworkLength()*pinch)>MAXBORDER){
                lblStatus.setText(objDictionaryAction.getWord("TOOLARGEFABRIC"));
                return;
            }
            int newLengthOfRepeat=(int)(objFabricCall.getDblArtworkLength()*pinch);
            widths[9]=newLengthOfRepeat;
            if(rightsideRightCrossBorderMultiRepeatPolicy){
                if((newLengthOfRepeat*repeats[9])>MAXBORDER){
                    lblStatus.setText(objDictionaryAction.getWord("TOOMANYREPEATS"));
                    return;
                }
                widths[9]=newLengthOfRepeat*repeats[9];
            }
            detectEnabledComponents();
            updateWidthHeight("Rightside Right Cross Border");
            recalculateBody();
            setComponentWidthHeight();
            enableAllComponents(false);
            ///int newWidthOfRepeat=(int) (newLengthOfRepeat/ratio);
            int newWidthOfRepeat=(int) (objFabricCall.getDblArtworkWidth()*pinch);
            bufferedImage=resizeFabricIcon(bufferedImage, newLengthOfRepeat, newWidthOfRepeat);
            rightsideRightCrossBorderPane.setUserData(bufferedImage);
            if(crossBorderLinkPolicy){
                leftCrossBorderPane.setUserData(bufferedImage);
                rightCrossBorderPane.setUserData(bufferedImage);
                rightsideLeftCrossBorderPane.setUserData(bufferedImage);
            }
            bufferedImage=getImage(bufferedImage, (int)component.getFitWidth(), (int)component.getFitHeight());
            component.setUserData(objFabricCall);
            component.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            if(crossBorderLinkPolicy){
                leftCrossBorderPane.setPrefHeight(component.getFitHeight());
                leftCrossBorderPane.setPrefWidth(component.getFitWidth());
                leftCrossBorder.setFitHeight(component.getFitHeight());
                leftCrossBorder.setFitWidth(component.getFitWidth());
                leftCrossBorder.setUserData(objFabricCall);
                leftCrossBorder.setId(component.getId());
                //Tooltip.install(leftCrossBorder, new Tooltip(((leftCrossBorder.getUserData()!=null)?("Showing "+String.valueOf((int)(((leftCrossBorder.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Cross Border in this View.\nFor Complete Cross Border switch to Full View."):"")));
                rightCrossBorderPane.setPrefHeight(component.getFitHeight());
                rightCrossBorderPane.setPrefWidth(component.getFitWidth());
                rightCrossBorder.setFitHeight(component.getFitHeight());
                rightCrossBorder.setFitWidth(component.getFitWidth());
                rightCrossBorder.setUserData(objFabricCall);
                rightCrossBorder.setId(component.getId());
                //Tooltip.install(rightCrossBorder, new Tooltip(((rightCrossBorder.getUserData()!=null)?("Showing "+String.valueOf((int)(((rightCrossBorder.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Cross Border in this View.\nFor Complete Cross Border switch to Full View."):"")));
                rightsideLeftCrossBorderPane.setPrefHeight(component.getFitHeight());
                rightsideLeftCrossBorderPane.setPrefWidth(component.getFitWidth());
                rightsideLeftCrossBorder.setFitHeight(component.getFitHeight());
                rightsideLeftCrossBorder.setFitWidth(component.getFitWidth());
                rightsideLeftCrossBorder.setUserData(objFabricCall);
                rightsideLeftCrossBorder.setId(component.getId());
                //Tooltip.install(rightsideLeftCrossBorder, new Tooltip(((rightsideLeftCrossBorder.getUserData()!=null)?("Showing "+String.valueOf((int)(((rightsideLeftCrossBorder.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Cross Border in this View.\nFor Complete Cross Border switch to Full View."):"")));
            }
            //Tooltip.install(component, new Tooltip(((component.getUserData()!=null)?("Showing "+String.valueOf((int)(((component.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Cross Border in this View.\nFor Complete Cross Border switch to Full View."):"")));
            if(crossBorderLinkPolicy){
                leftCrossBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                rightCrossBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                rightsideLeftCrossBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            }
        }
        else if(component.equals(leftPallu)){
            this.mapFabricId.put("Palu", objFabric.getStrFabricID());
            // getting original icon of fabric
            bufferedImage=getFabricIcon(objFabricCall);
            // calculating aspect ratio of original image
            double ratio = (double)bufferedImage.getWidth()/bufferedImage.getHeight();
            if((int)(objFabricCall.getDblArtworkLength()*pinch)>MAXPALLU){
                lblStatus.setText(objDictionaryAction.getWord("TOOLARGEFABRIC"));
                return;
            }
            int newLengthOfRepeat=(int)(objFabricCall.getDblArtworkLength()*pinch);
            widths[3]=newLengthOfRepeat;
            if(leftPalluMultiRepeatPolicy){
                if((newLengthOfRepeat*repeats[3])>MAXPALLU){
                    lblStatus.setText(objDictionaryAction.getWord("TOOMANYREPEATS"));
                    return;
                }
                widths[3]=newLengthOfRepeat*repeats[3];
            }
            detectEnabledComponents();
            updateWidthHeight("Left Pallu");
            recalculateBody();
            setComponentWidthHeight();
            enableAllComponents(false);
            // calculating updated length of single repeat based on aspect ratio
            ///int newWidthOfRepeat=(int) (newLengthOfRepeat/ratio);
            int newWidthOfRepeat=(int) (objFabricCall.getDblArtworkWidth()*pinch);
            // resize original icon so as to fit in resized component
            bufferedImage=resizeFabricIcon(bufferedImage, newLengthOfRepeat, newWidthOfRepeat);
            // saving this icon for making repeats later
            leftPalluPane.setUserData(bufferedImage);
            if(palluLinkPolicy){
                bufferedImageTwin=getRightRotatedImage(bufferedImage, 180);
                rightPalluPane.setUserData(bufferedImageTwin);
            }
            // getting repeats
            bufferedImage=getImage(bufferedImage, (int)component.getFitWidth(), (int)component.getFitHeight());
            // saving entire fabric object to use later (in yarn assignment and orientation)
            component.setUserData(objFabricCall);
            // setting image to component
            component.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            if(palluLinkPolicy){
                rightPalluPane.setPrefHeight(component.getFitHeight());
                rightPalluPane.setPrefWidth(component.getFitWidth());
                rightPallu.setFitHeight(component.getFitHeight());
                rightPallu.setFitWidth(component.getFitWidth());
                rightPallu.setUserData(objFabricCall);
                rightPallu.setId(component.getId());
                //Tooltip.install(rightPallu, new Tooltip(((rightPallu.getUserData()!=null)?("Showing "+String.valueOf((int)(((rightPallu.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Pallu in this View.\nFor Complete Pallu switch to Full View."):"")));
            }
            //Tooltip.install(component, new Tooltip(((component.getUserData()!=null)?("Showing "+String.valueOf((int)(((component.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Pallu in this View.\nFor Complete Pallu switch to Full View."):"")));
            if(palluLinkPolicy){
                bufferedImageTwin=getRightRotatedImage(bufferedImage, 180);
                rightPallu.setImage(SwingFXUtils.toFXImage(bufferedImageTwin, null));
            }
        }
        else if(component.equals(rightPallu)){
            this.mapFabricId.put("Palu", objFabric.getStrFabricID());
            bufferedImage=getFabricIcon(objFabricCall);
            double ratio = (double)bufferedImage.getWidth()/bufferedImage.getHeight();
            if((int)(objFabricCall.getDblArtworkLength()*pinch)>MAXPALLU){
                lblStatus.setText(objDictionaryAction.getWord("TOOLARGEFABRIC"));
                return;
            }
            int newLengthOfRepeat=(int)(objFabricCall.getDblArtworkLength()*pinch);
            widths[8]=newLengthOfRepeat;
            if(rightPalluMultiRepeatPolicy){
                if((newLengthOfRepeat*repeats[8])>MAXPALLU){
                    lblStatus.setText(objDictionaryAction.getWord("TOOMANYREPEATS"));
                    return;
                }
                widths[8]=newLengthOfRepeat*repeats[8];
            }
            detectEnabledComponents();
            updateWidthHeight("Right Pallu");
            recalculateBody();
            setComponentWidthHeight();
            enableAllComponents(false);
            ///int newWidthOfRepeat=(int) (newLengthOfRepeat/ratio);
            int newWidthOfRepeat=(int) (objFabricCall.getDblArtworkWidth()*pinch);
            bufferedImage=resizeFabricIcon(bufferedImage, newLengthOfRepeat, newWidthOfRepeat);
            if(palluLinkPolicy){
                leftPalluPane.setUserData(bufferedImage);
                bufferedImageTwin=getRightRotatedImage(bufferedImage, 180);
                rightPalluPane.setUserData(bufferedImageTwin);
            }else{
                bufferedImageTwin=getRightRotatedImage(bufferedImage, 180);
                rightPalluPane.setUserData(bufferedImageTwin);
            }
            bufferedImage=getImage(bufferedImage, (int)component.getFitWidth(), (int)component.getFitHeight());
            component.setUserData(objFabricCall);
            if(palluLinkPolicy){
                leftPallu.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                leftPalluPane.setPrefHeight(component.getFitHeight());
                leftPalluPane.setPrefWidth(component.getFitWidth());
                leftPallu.setFitHeight(component.getFitHeight());
                leftPallu.setFitWidth(component.getFitWidth());
                leftPallu.setUserData(objFabricCall);
                leftPallu.setId(component.getId());
                //Tooltip.install(leftPallu, new Tooltip(((leftPallu.getUserData()!=null)?("Showing "+String.valueOf((int)(((leftPallu.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Pallu in this View.\nFor Complete Pallu switch to Full View."):"")));
            }
            else{
                bufferedImageTwin=getRightRotatedImage(bufferedImage, 180);
                rightPallu.setImage(SwingFXUtils.toFXImage(bufferedImageTwin, null));
            }
            //Tooltip.install(component, new Tooltip(((component.getUserData()!=null)?("Showing "+String.valueOf((int)(((component.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Pallu in this View.\nFor Complete Pallu switch to Full View."):"")));
            if(palluLinkPolicy){
                bufferedImageTwin=getRightRotatedImage(bufferedImage, 180);
                rightPallu.setImage(SwingFXUtils.toFXImage(bufferedImageTwin, null));
            }
        }
        else if(component.equals(blouse)){
            this.mapFabricId.put("Blouse", objFabric.getStrFabricID());
            // getting original icon of fabric
            bufferedImage=getFabricIcon(objFabricCall);
            // calculating aspect ratio of original image
            double ratio = (double)bufferedImage.getWidth()/bufferedImage.getHeight();
            if((int)(objFabricCall.getDblArtworkLength()*pinch)>MAXBLOUSE){
                lblStatus.setText(objDictionaryAction.getWord("TOOLARGEFABRIC"));
                return;
            }
            int newLengthOfRepeat=(int)(objFabricCall.getDblArtworkLength()*pinch);
            widths[1]=newLengthOfRepeat;
            if(blouseMultiRepeatPolicy){
                if((newLengthOfRepeat*repeats[1])>MAXBLOUSE){
                    lblStatus.setText(objDictionaryAction.getWord("TOOMANYREPEATS"));
                    return;
                }
                widths[1]=newLengthOfRepeat*repeats[1];
            }
            detectEnabledComponents();
            updateWidthHeight("ceBlouse");
            recalculateBody();
            setComponentWidthHeight();
            enableAllComponents(false);
            // calculating updated length of single repeat based on aspect ratio
            ///int newWidthOfRepeat=(int) (newLengthOfRepeat/ratio);
            int newWidthOfRepeat=(int) (objFabricCall.getDblArtworkWidth()*pinch);
            // resize original icon so as to fit in resized component
            bufferedImage=resizeFabricIcon(bufferedImage, newLengthOfRepeat, newWidthOfRepeat);
            // saving this icon for making repeats later
            blousePane.setUserData(bufferedImage);
            // getting repeats (widthwise)
            bufferedImage=getImage(bufferedImage, (int)component.getFitWidth(), (int)component.getFitHeight());
            // saving entire fabric object to use later (in yarn assignment and orientation)
            component.setUserData(objFabricCall);
            //Tooltip.install(component, new Tooltip(((component.getUserData()!=null)?("Showing "+String.valueOf((int)(((component.getFitWidth()/newLengthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Blouse in this View.\nFor Complete Blouse switch to Full View."):"")));
            // setting image to component
            component.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        }
        else if(component.equals(skart)){
            this.mapFabricId.put("Skart", objFabric.getStrFabricID());
            // getting original icon of fabric
            bufferedImage=getFabricIcon(objFabricCall);
            // calculating aspect ratio of original image
            double ratio = (double)bufferedImage.getWidth()/bufferedImage.getHeight();
            if((int)(objFabricCall.getDblArtworkWidth()*pinch)>MAXBORDER){
                lblStatus.setText(objDictionaryAction.getWord("TOOLARGEFABRIC"));
                return;
            }
            int newWidthOfRepeat=(int)(objFabricCall.getDblArtworkWidth()*pinch);
            heights[6]=newWidthOfRepeat;
            if(skartMultiRepeatPolicy){
                if((newWidthOfRepeat*repeats[6])>MAXBORDER){
                    lblStatus.setText(objDictionaryAction.getWord("TOOMANYREPEATS"));
                    return;
                }
                heights[6]=newWidthOfRepeat*repeats[6];
            }
            detectEnabledComponents();
            updateWidthHeight("ceSkart");
            recalculateBody();
            setComponentWidthHeight();
            enableAllComponents(false);
            // calculating updated length of single repeat based on aspect ratio
            ///int newLengthOfRepeat=(int) (newWidthOfRepeat*ratio);
            int newLengthOfRepeat=(int)(objFabricCall.getDblArtworkLength()*pinch);
            // resize original icon so as to fit in resized component
            bufferedImage=resizeFabricIcon(bufferedImage, newLengthOfRepeat, newWidthOfRepeat);
            // saving this icon for making repeats later
            skartPane.setUserData(bufferedImage);
            // getting repeats (widthwise)
            bufferedImage=getImage(bufferedImage, (int)component.getFitWidth(), (int)component.getFitHeight());
            // saving entire fabric object to use later (in yarn assignment and orientation)
            component.setUserData(objFabricCall);
            //Tooltip.install(component, new Tooltip(((component.getUserData()!=null)?("Showing "+String.valueOf((int)(((component.getFitHeight()/newWidthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Skart in this View.\nFor Complete Skart switch to Full View."):"")));
            // setting image to component
            component.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        }
        else if(component.equals(body)){
            this.mapFabricId.put("Body", objFabric.getStrFabricID());
            // getting original icon of fabric
            bufferedImage=getFabricIcon(objFabricCall);
            // calculating aspect ratio of original image
            double ratio = (double)bufferedImage.getWidth()/bufferedImage.getHeight();
            int newWidthOfRepeat=(int)(objFabricCall.getDblArtworkWidth()*pinch);
            int temp=newWidthOfRepeat;
            newWidthOfRepeat=roundIntegralRepeatSize(newWidthOfRepeat, (int)component.getFitHeight());
            if(newWidthOfRepeat<MINSIZE)
                newWidthOfRepeat=temp;
            // calculating updated length of single repeat based on aspect ratio
            ///int newLengthOfRepeat=(int) (newWidthOfRepeat*ratio);
            int newLengthOfRepeat=(int)(objFabricCall.getDblArtworkLength()*pinch);
            // resize original icon so as to fit in resized component
            bufferedImage=resizeFabricIcon(bufferedImage, newLengthOfRepeat, newWidthOfRepeat);
            // saving this icon for making repeats later
            bodyPane.setUserData(bufferedImage);
            // getting repeats
            bufferedImage=getImage(bufferedImage, (int)component.getFitWidth(), (int)component.getFitHeight());
            // saving entire fabric object to use later (in yarn assignment and orientation)
            component.setUserData(objFabricCall);
            //Tooltip.install(component, new Tooltip(((component.getUserData()!=null)?("Showing "+String.valueOf((int)(((component.getFitHeight()/newWidthOfRepeat)/(objFabricCall.getDblFabricWidth()/objFabricCall.getDblArtworkWidth()))*pinch0))+"% of Body in this View.\nFor Complete Body switch to Full View."):"")));
            // setting image to component
            component.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            /*try{
                ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("FabBody.ser"));
                oos.writeObject(objFabricCall);
                ObjectInputStream ois=new ObjectInputStream(new FileInputStream("FabBody.ser"));
                bodyFabric=(Fabric)ois.readObject();
                component.setUserData(bodyFabric);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }*/
        }
    }
    
    /**
     * @author Aatif Ahmad Khan
     * @see reOrientImage()
     * repaintComponents() detects all ScrollPanes (& their corresponding ImageViews)
     * which are currently present in containerGP GridPane
     * All ScrollPanes have last painted BufferedImage (needed for getting width
     * and height of last painted motif) in their UserData
     * All ImageViews have assigned Fabric Object in their UserData
     * repaintComponents() performs following steps:
     * 1: Gets Fabric Icon (using getFabricIcon()) from updated Fabric Object
     * 2: Orients image as per last information on Warp, Weft, Rotation Angle, Repeat Mode
     * (These 4 parameters were present in ImageViews Id) using reOrientImage()
     * 3: These images after reorientation are painted on respective ImageViews subject to
     * following constraints:
     * c1: BorderLinkPolicy is true (Right Border is mirrored image of Left Border)
     * c2: CrossBorderLinkPolicy is true (Right Cross Border is same as Left Cross Border)
     * c3: PalluLinkPolicy is true (Right Pallu is mirrored image of Left Pallu)
     * 
     * NOTE: This function doesn't modifies Height/Width of any components, nor adds/removes any
     * So constraint of MultiRepeatPolicy is ignored (as repeat size is inclusive of this policy)
     */
    public void repaintComponents(){
        for(int a=0; a<containerGP.getChildren().size(); a++){
            ScrollPane comp=(ScrollPane)containerGP.getChildren().get(a);
            if(((ImageView)comp.getContent()).getUserData()!=null){
                objFabric = (Fabric) ((ImageView)comp.getContent()).getUserData();
                BufferedImage bufferedImage=getFabricIcon(objFabric);
                bufferedImage=resizeFabricIcon(bufferedImage, ((BufferedImage)comp.getUserData()).getWidth(), ((BufferedImage)comp.getUserData()).getHeight());

                // repaint on image view repaint(bufferedImage, iv.len, iv.wid);
                bufferedImage=reOrientImage(((ImageView)comp.getContent()), bufferedImage, (int)((ImageView)comp.getContent()).getFitWidth(), (int)((ImageView)comp.getContent()).getFitHeight());

                Image image=SwingFXUtils.toFXImage(bufferedImage, null);
                if( ((ImageView)comp.getContent()).equals(leftBorder)|| ((ImageView)comp.getContent()).equals(rightBorder)){
                    if(borderLinkPolicy){
                        leftBorder.setImage(image);
                        bufferedImage = getRightRotatedImage(bufferedImage, 180);
                        rightBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    }
                    else{
                        if(((ImageView)comp.getContent()).equals(leftBorder)){
                            leftBorder.setImage(image);
                        }
                        else{
                            bufferedImage = getRightRotatedImage(bufferedImage, 180);
                            rightBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                        }
                    }
                } else if( ((ImageView)comp.getContent()).equals(leftCrossBorder)|| ((ImageView)comp.getContent()).equals(rightCrossBorder)|| ((ImageView)comp.getContent()).equals(rightsideLeftCrossBorder)|| ((ImageView)comp.getContent()).equals(rightsideRightCrossBorder)){
                    if(crossBorderLinkPolicy){
                        leftCrossBorder.setImage(image);
                        rightCrossBorder.setImage(image);
                        rightsideLeftCrossBorder.setImage(image);
                        rightsideRightCrossBorder.setImage(image);
                    }
                    else{
                        ((ImageView)comp.getContent()).setImage(image);
                    }
                } else if( ((ImageView)comp.getContent()).equals(leftPallu)|| ((ImageView)comp.getContent()).equals(rightPallu)){
                    if(palluLinkPolicy){
                        leftPallu.setImage(image);
                        bufferedImage = getRightRotatedImage(bufferedImage, 180);
                        rightPallu.setImage(SwingFXUtils.toFXImage(bufferedImage, null));


                    }
                    else{
                        if(((ImageView)comp.getContent()).equals(leftPallu)){
                            leftPallu.setImage(image);
                        }
                        else{
                            bufferedImage = getRightRotatedImage(bufferedImage, 180);
                            rightPallu.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                        }
                    }
                } else{
                     ((ImageView)comp.getContent()).setImage(image);
                }

                bufferedImage = null;
            }
        }
    }
    
    /**
     * @author Aatif Ahmad Khan
     * Migrating fabric assigned to a garment component to Fabric Editor
     * @param component ImageView for garment component
     * @param strSearchBy  String description of cloth type to put in Configuration MapRecent
     */
    public void componentFabricEditor(ImageView component, String strSearchBy){
        try{
            if(component.getUserData()!=null){
                objConfiguration.setStrClothType(strSearchBy);
                objConfiguration.strWindowFlowContext = "ClothEditor";                        
                UserAction objUserAction = new UserAction();
                objUserAction.getConfiguration(objConfiguration);
                objConfiguration.clothRepeat();
                // storing id before generating a new (for temp database)
                String originalFabricId=((Fabric)component.getUserData()).getStrFabricID();
                saveContext();
                objConfiguration.setGarmentZoom(1.0);
                objConfiguration.setGarmentPinch(pinch);
                System.gc();
                //objConfiguration.mapRecentFabric.put(objConfiguration.getStrClothType()/*getComponentName(component)*/,originalFabricId/*((Fabric)component.getUserData()).getStrFabricID()*/);
                objConfiguration.mapRecentFabric.put(objConfiguration.getStrClothType(), this.mapFabricId.get(getComponentType(getComponentName(component))));
                clothStage.close();
                System.gc();
                FabricView objFabricView = new FabricView(objConfiguration);
            } else{
                final Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                BorderPane root = new BorderPane();
                Scene scene = new Scene(root, 400, 100, Color.WHITE);
                GridPane popup1=new GridPane();
                popup1.setHgap(5);
                popup1.setVgap(5);
                Label lblMsg = new Label(objDictionaryAction.getWord("NOCLOTHPART"));
                popup1.add(lblMsg, 0, 0);
                Button btnOK1 = new Button(objDictionaryAction.getWord("OK"));
                btnOK1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        dialogStage.close  ();
                        System.gc();
                    }
                });
                popup1.add(btnOK1, 1, 1);
                dialogStage.setScene(new Scene(popup1));
                dialogStage.showAndWait();
                lblStatus.setText(objDictionaryAction.getWord("NOCLOTHPART"));
            }
        } catch(SQLException ex){
            new Logging("SEVERE",ClothView.class.getName(),"componentFabricEditor(): ",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",ClothView.class.getName(),"componentFabricEditor(): ",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    // mode Yarn, Thread, Artwork, Density
    public void componentProperty(ImageView component, String strSearchBy, String mode){
        try{
            BufferedImage bufferedImage=null;
            if(component.getUserData()!=null){
                objConfiguration.setStrClothType(strSearchBy);
                if(mode.equalsIgnoreCase("Yarn")){
                    byte[] oldFabricBytes=((Fabric)component.getUserData()).getBytFabricIcon();
                    bufferedImage = editYarn(component);
                    byte[] newFabricBytes=((Fabric)component.getUserData()).getBytFabricIcon();
                    boolean fabricIconNotModified=Arrays.equals(oldFabricBytes, newFabricBytes);
                    /* If fabric icon not modified, simpy return */
                    if(fabricIconNotModified)
                        return;
                }
                else if(mode.equalsIgnoreCase("Artwork")){
                    bufferedImage=editArtwork(component);
                }
                else if(mode.equalsIgnoreCase("Thread")){
                    bufferedImage=editPattern(component);
                }
                else if(mode.equalsIgnoreCase("Density")){
                    bufferedImage = editDensity(component);
                }
                // rotate image
                bufferedImage=objArtworkAction.getImageRotation(bufferedImage, "CLOCKWISE");
                // resize icon
                if(component.equals(leftBorder))
                    bufferedImage=resizeFabricIcon(bufferedImage, ((BufferedImage)leftBorderPane.getUserData()).getWidth(), ((BufferedImage)leftBorderPane.getUserData()).getHeight());
                else if(component.equals(rightBorder))
                    bufferedImage=resizeFabricIcon(bufferedImage, ((BufferedImage)rightBorderPane.getUserData()).getWidth(), ((BufferedImage)rightBorderPane.getUserData()).getHeight());
                else if(component.equals(leftCrossBorder))
                    bufferedImage=resizeFabricIcon(bufferedImage, ((BufferedImage)leftCrossBorderPane.getUserData()).getWidth(), ((BufferedImage)leftCrossBorderPane.getUserData()).getHeight());
                else if(component.equals(rightCrossBorder))
                    bufferedImage=resizeFabricIcon(bufferedImage, ((BufferedImage)rightCrossBorderPane.getUserData()).getWidth(), ((BufferedImage)rightCrossBorderPane.getUserData()).getHeight());
                else if(component.equals(leftPallu))
                    bufferedImage=resizeFabricIcon(bufferedImage, ((BufferedImage)leftPalluPane.getUserData()).getWidth(), ((BufferedImage)leftPalluPane.getUserData()).getHeight());
                else if(component.equals(rightPallu))
                    bufferedImage=resizeFabricIcon(bufferedImage, ((BufferedImage)rightPalluPane.getUserData()).getWidth(), ((BufferedImage)rightPalluPane.getUserData()).getHeight());
                else if(component.equals(body))
                    bufferedImage=resizeFabricIcon(bufferedImage, ((BufferedImage)bodyPane.getUserData()).getWidth(), ((BufferedImage)bodyPane.getUserData()).getHeight());
                else if(component.equals(blouse))
                    bufferedImage=resizeFabricIcon(bufferedImage, ((BufferedImage)blousePane.getUserData()).getWidth(), ((BufferedImage)blousePane.getUserData()).getHeight());
                else if(component.equals(skart))
                    bufferedImage=resizeFabricIcon(bufferedImage, ((BufferedImage)skartPane.getUserData()).getWidth(), ((BufferedImage)skartPane.getUserData()).getHeight());
                else if(component.equals(rightsideLeftCrossBorder))
                    bufferedImage=resizeFabricIcon(bufferedImage, ((BufferedImage)rightsideLeftCrossBorderPane.getUserData()).getWidth(), ((BufferedImage)rightsideLeftCrossBorderPane.getUserData()).getHeight());
                else if(component.equals(rightsideRightCrossBorder))
                    bufferedImage=resizeFabricIcon(bufferedImage, ((BufferedImage)rightsideRightCrossBorderPane.getUserData()).getWidth(), ((BufferedImage)rightsideRightCrossBorderPane.getUserData()).getHeight());
                
                // repaint on image view repaint(bufferedImage, iv.len, iv.wid);
                bufferedImage=reOrientImage(component, bufferedImage, (int)component.getFitWidth(), (int)component.getFitHeight());
                //bufferedImage = getImage(bufferedImage, (int)component.getFitWidth(), (int)component.getFitHeight());
                Image image=SwingFXUtils.toFXImage(bufferedImage, null);  
                if(component.equals(leftBorder)||component.equals(rightBorder)){
                    if(borderLinkPolicy){
                        leftBorder.setUserData(component.getUserData());
                        rightBorder.setUserData(component.getUserData());
                        leftBorder.setImage(image);
                        bufferedImage = getRightRotatedImage(bufferedImage, 180);
                        rightBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    }
                    else{
                        if(component.equals(leftBorder)){
                            leftBorder.setImage(image);
                        }
                        else{
                            bufferedImage = getRightRotatedImage(bufferedImage, 180);
                            rightBorder.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                        }
                    }
                } else if(component.equals(leftCrossBorder)||component.equals(rightCrossBorder)||component.equals(rightsideLeftCrossBorder)||component.equals(rightsideRightCrossBorder)){
                    if(crossBorderLinkPolicy){
                        leftCrossBorder.setImage(image);
                        rightCrossBorder.setImage(image);
                        rightsideLeftCrossBorder.setImage(image);
                        rightsideRightCrossBorder.setImage(image);
                    }
                    else{
                        component.setImage(image);
                    }
                } else if(component.equals(leftPallu)||component.equals(rightPallu)){
                    if(palluLinkPolicy){
                        leftPallu.setUserData(component.getUserData());
                        rightPallu.setUserData(component.getUserData());
                        leftPallu.setImage(image);
                        bufferedImage = getRightRotatedImage(bufferedImage, 180);
                        rightPallu.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    }
                    else{
                        if(component.equals(leftPallu)){
                            leftPallu.setImage(image);
                        }
                        else{
                            bufferedImage = getRightRotatedImage(bufferedImage, 180);
                            rightPallu.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                        }
                    }
                } else{
                    component.setImage(image);
                }
                bufferedImage = null;
            } else{
                final Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                BorderPane root = new BorderPane();
                Scene scene = new Scene(root, 400, 100, Color.WHITE);
                GridPane popup1=new GridPane();
                popup1.setHgap(5);
                popup1.setVgap(5);
                Label lblMsg = new Label(objDictionaryAction.getWord("NOCLOTHPART"));
                popup1.add(lblMsg, 0, 0);
                Button btnOK1 = new Button(objDictionaryAction.getWord("OK"));
                btnOK1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        dialogStage.close  ();
                        System.gc();
                    }
                });
                popup1.add(btnOK1, 1, 1);
                dialogStage.setScene(new Scene(popup1));
                dialogStage.showAndWait();
                lblStatus.setText(objDictionaryAction.getWord("NOCLOTHPART"));
            }
        } catch(Exception ex){
            if(mode.equalsIgnoreCase("Yarn"))
                new Logging("SEVERE",ClothView.class.getName(),"editing yarn properties",ex);
            else if(mode.equalsIgnoreCase("Artwork")){
                new Logging("SEVERE",ClothView.class.getName(),"editing artwork assignment",ex);
            }
            else if(mode.equalsIgnoreCase("Thread"))
                new Logging("SEVERE",ClothView.class.getName(),"editing thread sequence",ex);
            else if(mode.equalsIgnoreCase("Density"))
                new Logging("SEVERE",ClothView.class.getName(),"editing density",ex);
            else
                new Logging("SEVERE",ClothView.class.getName(),"componentProperty(): "+ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    // previously using SHIFT+Click
    public void componentOrientation(final ImageView component, String strSearchBy){
        try{
            if(component.getUserData()!=null){
                ///objFabric = new Fabric();
                objFabric=(Fabric) component.getUserData();
                ///objFabric.setStrFabricID(component.getId());
                objFabric.setStrSearchBy(strSearchBy);
                objFabric.setObjConfiguration(objConfiguration);
                FabricAction objFabricAction = new FabricAction();
                ///objFabricAction.getFabric(objFabric);
                            
                byte[] bytFabricThumbnil = (byte[])objFabric.getBytFabricIcon();
                SeekableStream stream = new ByteArraySeekableStream(bytFabricThumbnil);
                String[] names = ImageCodec.getDecoderNames(stream);
                ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                RenderedImage im = dec.decodeAsRenderedImage();
                objArtworkAction = new ArtworkAction();
                BufferedImage bufferedImag = objArtworkAction.getImageRotation(PlanarImage.wrapRenderedImage(im).getAsBufferedImage(), "CLOCKWISE");            
                if(component.equals(rightBorder)||component.equals(rightPallu))
                    bufferedImag=getRightRotatedImage(bufferedImag, 180);
                bytFabricThumbnil = null;
                
                // resize icon
                if(component.equals(leftBorder))
                    bufferedImag=resizeFabricIcon(bufferedImag, ((BufferedImage)leftBorderPane.getUserData()).getWidth(), ((BufferedImage)leftBorderPane.getUserData()).getHeight());
                else if(component.equals(rightBorder))
                    bufferedImag=resizeFabricIcon(bufferedImag, ((BufferedImage)rightBorderPane.getUserData()).getWidth(), ((BufferedImage)rightBorderPane.getUserData()).getHeight());
                else if(component.equals(leftCrossBorder))
                    bufferedImag=resizeFabricIcon(bufferedImag, ((BufferedImage)leftCrossBorderPane.getUserData()).getWidth(), ((BufferedImage)leftCrossBorderPane.getUserData()).getHeight());
                else if(component.equals(rightCrossBorder))
                    bufferedImag=resizeFabricIcon(bufferedImag, ((BufferedImage)rightCrossBorderPane.getUserData()).getWidth(), ((BufferedImage)rightCrossBorderPane.getUserData()).getHeight());
                else if(component.equals(leftPallu))
                    bufferedImag=resizeFabricIcon(bufferedImag, ((BufferedImage)leftPalluPane.getUserData()).getWidth(), ((BufferedImage)leftPalluPane.getUserData()).getHeight());
                else if(component.equals(rightPallu))
                    bufferedImag=resizeFabricIcon(bufferedImag, ((BufferedImage)rightPalluPane.getUserData()).getWidth(), ((BufferedImage)rightPalluPane.getUserData()).getHeight());
                else if(component.equals(body))
                    bufferedImag=resizeFabricIcon(bufferedImag, ((BufferedImage)bodyPane.getUserData()).getWidth(), ((BufferedImage)bodyPane.getUserData()).getHeight());
                else if(component.equals(blouse))
                    bufferedImag=resizeFabricIcon(bufferedImag, ((BufferedImage)blousePane.getUserData()).getWidth(), ((BufferedImage)blousePane.getUserData()).getHeight());
                else if(component.equals(skart))
                    bufferedImag=resizeFabricIcon(bufferedImag, ((BufferedImage)skartPane.getUserData()).getWidth(), ((BufferedImage)skartPane.getUserData()).getHeight());
                else if(component.equals(rightsideLeftCrossBorder))
                    bufferedImag=resizeFabricIcon(bufferedImag, ((BufferedImage)rightsideLeftCrossBorderPane.getUserData()).getWidth(), ((BufferedImage)rightsideLeftCrossBorderPane.getUserData()).getHeight());
                else if(component.equals(rightsideRightCrossBorder))
                    bufferedImag=resizeFabricIcon(bufferedImag, ((BufferedImage)rightsideRightCrossBorderPane.getUserData()).getWidth(), ((BufferedImage)rightsideRightCrossBorderPane.getUserData()).getHeight());
                                
                final BufferedImage bufferedImage=bufferedImag;
                final double aspectRatio=(double)bufferedImage.getWidth()/(double)bufferedImage.getHeight();
                
                Image image=SwingFXUtils.toFXImage(bufferedImage, null);  
                final Stage dialogStage = new Stage();
                dialogStage.initStyle(StageStyle.UTILITY);
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                GridPane popup=new GridPane();
                popup.setId("popup");
                popup.setHgap(5);
                popup.setVgap(5);

                Label lblWidthNew = new Label(objDictionaryAction.getWord("LENGTH"));
                lblWidthNew.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPLENGTH")));
                popup.add(lblWidthNew, 0, 0);
                widthNewTxt = new TextField();
                widthNewTxt.setPromptText(objDictionaryAction.getWord("TOOLTIPLENGTH"));
                widthNewTxt.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPLENGTH")));
                widthNewTxt.setText(String.valueOf(objFabric.getIntWarp()));
                popup.add(widthNewTxt, 1, 0);

                Label lblHeightNew = new Label(objDictionaryAction.getWord("WIDTH"));
                lblHeightNew.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWIDTH")));
                popup.add(lblHeightNew, 0, 1);
                heightNewTxt = new TextField();
                heightNewTxt.setPromptText(objDictionaryAction.getWord("TOOLTIPWIDTH"));
                heightNewTxt.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWIDTH")));
                heightNewTxt.setText(String.valueOf(objFabric.getIntWeft()));
                popup.add(heightNewTxt, 1, 1);

                // Added (15 Feb 2017) for Rotation (degree) of atrwork
                Label lblRotationNew = new Label(objDictionaryAction.getWord("ROTATION"));
                lblRotationNew.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPROTATION")));
                popup.add(lblRotationNew, 0, 2);
                rotationCB=new ChoiceBox();
                rotationCB.getItems().add(0, "0");
                rotationCB.getItems().add(1, "90");
                rotationCB.getItems().add(2, "180");
                rotationCB.getItems().add(3, "270");
                rotationCB.setValue(rotationCB.getItems().get(0));
                //final TextField rotationNewTxt = new TextField();
                //rotationNewTxt.setPromptText("TOOLTIPWIDTH");
                //rotationNewTxt.setTooltip(new Tooltip("TOOLTIPROTATION"));
                //rotationNewTxt.setText("0");
                //popup.add(rotationNewTxt, 1, 2);
                popup.add(rotationCB, 1, 2);
                
                widthNewTxt.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                        if(!widthNewTxt.isFocused()||widthNewTxt.getText().length()==0)
                            return;
                        if (!t1.matches("\\d*")) 
                            widthNewTxt.setText(t1.replaceAll("[^\\d]", ""));
                        else{
                            heightNewTxt.setText(String.valueOf((int)(Double.parseDouble(widthNewTxt.getText())*aspectRatio)));
                        }
                    }
                });
                heightNewTxt.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                        if(!heightNewTxt.isFocused()||heightNewTxt.getText().length()==0)
                            return;
                        if (!t1.matches("\\d*")) 
                            heightNewTxt.setText(t1.replaceAll("[^\\d]", ""));
                        else{
                            widthNewTxt.setText(String.valueOf((int)(Double.parseDouble(heightNewTxt.getText())/aspectRatio)));
                        }
                    }
                });

                Label lblRepeatMode=new Label(objDictionaryAction.getWord("REPEATMODE"));
                lblRepeatMode.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPREPEATMODE")));
                popup.add(lblRepeatMode, 0, 3);
                repeatModeCB=new ChoiceBox();
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
                popup.add(repeatModeCB, 1, 3);
                
                final ImageView tempIV = new ImageView();
                tempIV.setFitHeight(300);
                tempIV.setFitWidth(300);
                popup.add(tempIV, 0, 4, 2, 1);
                
                if(component.getId()!=null){
                    try{
                        rotationCB.setValue(String.valueOf((Integer.parseInt(component.getId().substring(component.getId().indexOf("RA")+2, component.getId().indexOf("/RA")).toString()))%360));
                        heightNewTxt.setText((component.getId().substring(component.getId().indexOf("WF")+2, component.getId().indexOf("/WF")).toString()));
                        widthNewTxt.setText((component.getId().substring(component.getId().indexOf("WP")+2, component.getId().indexOf("/WP")).toString()));
                        repeatModeCB.setValue((component.getId().substring(component.getId().indexOf("RM")+2, component.getId().indexOf("/RM")).toString()));
                        /*int vAling = 1;
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
                        */    
                        BufferedImage bi=reOrientImage(component, bufferedImage, 300, 300);
                        image=SwingFXUtils.toFXImage(bi, null);  
                        System.gc();
                    }
                    catch(NumberFormatException nfEx){}
                }
                
                tempIV.setImage(image);

                Button btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
                btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
                btnCancel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
                btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {  
                        lblStatus.setText(objDictionaryAction.getWord("TOOLTIPCANCEL"));
                        dialogStage.close();
                    }
                });
                popup.add(btnCancel, 0, 5);
                
                Button btnPreview = new Button(objDictionaryAction.getWord("PREVIEW"));
                btnPreview.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPREVIEW")));
                btnPreview.setGraphic(new ImageView(objConfiguration.getStrColour()+"/preview.png"));
                btnPreview.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {  
                        try {
                            lblStatus.setText(objFabric.getStrFabricName()+" : "+objDictionaryAction.getWord("DATASAVED"));
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
                            double zoomWidthFactor=Double.parseDouble(widthNewTxt.getText())/objFabric.getIntWarp();
                            double zoomHeightFactor=Double.parseDouble(heightNewTxt.getText())/objFabric.getIntWeft();
                            
                            BufferedImage bufferedImageRotated=getRightRotatedImage(bufferedImage, (Integer.parseInt(rotationCB.getValue().toString())+(Integer.parseInt(component.getId().substring(component.getId().indexOf("RA")+2, component.getId().indexOf("/RA"))))));
                            BufferedImage bufferedImage1 = redesign(bufferedImageRotated, vAling, hAling
                                    /*, Integer.parseInt(widthNewTxt.getText()),Integer.parseInt(heightNewTxt.getText())*/
                                    ,(int)(zoomWidthFactor*bufferedImageRotated.getWidth()), (int)(zoomHeightFactor*bufferedImageRotated.getHeight())
                                    ,300,300);
                            bufferedImageRotated = null;
                            Image image=SwingFXUtils.toFXImage(bufferedImage1, null);  
                            tempIV.setImage(image);
                            bufferedImage1=null;
                            System.gc();

                        } catch(Exception ex){
                            new Logging("SEVERE",FabricView.class.getName(),"componentOrientation(): Preview Button: "+ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });
                popup.add(btnPreview, 1, 5);
                            
                Button btnApply = new Button(objDictionaryAction.getWord("APPLY"));
                btnApply.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPAPPLY")));
                btnApply.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
                btnApply.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {  
                        try {
                            lblStatus.setText(objFabric.getStrFabricName()+" : "+objDictionaryAction.getWord("Process")+" "+objDictionaryAction.getWord("Completed."));
                            int vAling = 1;
                            int hAling = 1;
                            if(repeatModeCB.getValue().toString().contains("Vertical")){
                                String temp = repeatModeCB.getValue().toString();
                                vAling = Integer.parseInt(temp.substring(0, temp.indexOf("/")).trim());
                                hAling = Integer.parseInt(temp.substring(2, temp.indexOf("Vertical")).trim());
                            } else if(repeatModeCB.getValue().toString().contains("Horizontal")){
                                String temp = repeatModeCB.getValue().toString();
                                hAling = Integer.parseInt(temp.substring(0, temp.indexOf("/")).trim());
                                vAling = Integer.parseInt(temp.substring(2, temp.indexOf("Horizontal")).trim());
                            } else{
                                vAling = 1;
                                hAling = 1;
                            }
                            
                            double zoomWidthFactor=Double.parseDouble(widthNewTxt.getText())/((Fabric)component.getUserData()).getIntWarp();
                            double zoomHeightFactor=Double.parseDouble(heightNewTxt.getText())/((Fabric)component.getUserData()).getIntWeft();
                            
                            if(component.equals(leftBorder)||component.equals(rightBorder)||component.equals(skart)){
                                System.out.println("BiW"+bufferedImage.getHeight());
                                System.out.println("ZoomHei"+zoomWidthFactor);
                                int repeatWidth=(int)(zoomWidthFactor*bufferedImage.getHeight());
                                System.out.println("RepeatW"+repeatWidth);
                                if(repeatWidth<MINSIZE){
                                    lblStatus.setText(objDictionaryAction.getWord("TOOSMALLFABRIC"));
                                    return;
                                }
                                else if(repeatWidth>MAXBORDER){
                                    lblStatus.setText(objDictionaryAction.getWord("TOOLARGEFABRIC"));
                                    return;
                                }
                                heights[getComponentIndex(component)]=repeatWidth;
                                if((getComponentIndex(component)==0&&leftBorderMultiRepeatPolicy)
                                        ||(getComponentIndex(component)==10&&rightBorderMultiRepeatPolicy)
                                        ||(getComponentIndex(component)==6&&skartMultiRepeatPolicy)){
                                    if(repeatWidth*repeats[getComponentIndex(component)]>MAXBORDER){
                                        lblStatus.setText(objDictionaryAction.getWord("TOOMANYREPEATS"));
                                        return;
                                    }
                                    heights[getComponentIndex(component)]=repeatWidth*repeats[getComponentIndex(component)];
                                }
                            }
                            else if(component.equals(body)){
                                
                            }
                            else{
                                int repeatWidth=(int)(zoomHeightFactor*bufferedImage.getWidth());
                                if(repeatWidth<MINSIZE){
                                    lblStatus.setText(objDictionaryAction.getWord("TOOSMALLFABRIC"));
                                    return;
                                }
                                else if(getComponentIndex(component)==1&&repeatWidth>MAXBLOUSE){
                                    lblStatus.setText(objDictionaryAction.getWord("TOOLARGEFABRIC"));
                                    return;
                                }
                                else if((getComponentIndex(component)==2||getComponentIndex(component)==4
                                        ||getComponentIndex(component)==7||getComponentIndex(component)==9)&&repeatWidth>MAXBORDER){
                                    lblStatus.setText(objDictionaryAction.getWord("TOOLARGEFABRIC"));
                                    return;
                                }
                                else if((getComponentIndex(component)==3||getComponentIndex(component)==8)&&repeatWidth>MAXPALLU){
                                    lblStatus.setText(objDictionaryAction.getWord("TOOLARGEFABRIC"));
                                    return;
                                }
                                widths[getComponentIndex(component)]=repeatWidth;
                                if((getComponentIndex(component)==1&&blouseMultiRepeatPolicy)
                                        ||(getComponentIndex(component)==2&&leftCrossBorderMultiRepeatPolicy)
                                        ||(getComponentIndex(component)==4&&rightCrossBorderMultiRepeatPolicy)
                                        ||(getComponentIndex(component)==7&&rightsideLeftCrossBorderMultiRepeatPolicy)
                                        ||(getComponentIndex(component)==9&&rightsideRightCrossBorderMultiRepeatPolicy)
                                        ||(getComponentIndex(component)==3&&leftPalluMultiRepeatPolicy)
                                        ||(getComponentIndex(component)==8&&rightPalluMultiRepeatPolicy)){
                                    if((getComponentIndex(component)==1&&repeatWidth*repeats[getComponentIndex(component)]>MAXBLOUSE)
                                            ||(getComponentIndex(component)==2&&repeatWidth*repeats[getComponentIndex(component)]>MAXBORDER)
                                            ||(getComponentIndex(component)==4&&repeatWidth*repeats[getComponentIndex(component)]>MAXBORDER)
                                            ||(getComponentIndex(component)==7&&repeatWidth*repeats[getComponentIndex(component)]>MAXBORDER)
                                            ||(getComponentIndex(component)==9&&repeatWidth*repeats[getComponentIndex(component)]>MAXBORDER)
                                            ||(getComponentIndex(component)==3&&repeatWidth*repeats[getComponentIndex(component)]>MAXPALLU)
                                            ||(getComponentIndex(component)==8&&repeatWidth*repeats[getComponentIndex(component)]>MAXPALLU)){
                                        lblStatus.setText(objDictionaryAction.getWord("TOOMANYREPEATS"));
                                        return;
                                    }
                                    widths[getComponentIndex(component)]=repeatWidth*repeats[getComponentIndex(component)];
                                }
                            }
                            detectEnabledComponents();
                            updateWidthHeight(getComponentName(component));
                            recalculateBody();
                            setComponentWidthHeight();
                            enableAllComponents(false);
                            
                            BufferedImage bufferedImageRotated=getRightRotatedImage(bufferedImage, (Integer.parseInt(rotationCB.getValue().toString())+(Integer.parseInt(component.getId().substring(component.getId().indexOf("RA")+2, component.getId().indexOf("/RA"))))));
                            BufferedImage bufferedImage1=redesign(bufferedImageRotated, vAling, hAling
                                    /*,Integer.parseInt(widthNewTxt.getText()),Integer.parseInt(heightNewTxt.getText())*/
                                    ,(int)(zoomWidthFactor*bufferedImageRotated.getWidth()), (int)(zoomHeightFactor*bufferedImageRotated.getHeight())
                                    ,(int)component.getFitWidth(), (int)component.getFitHeight());
                            int oldRotationAngle=Integer.parseInt(component.getId().substring(component.getId().indexOf("RA")+2, component.getId().indexOf("/RA")));
                            int newRotationAngle=Integer.parseInt(rotationCB.getValue().toString());
                            component.setId("RM"+repeatModeCB.getSelectionModel().getSelectedItem().toString()+"/RM:"
                            +"WP"+Integer.parseInt(widthNewTxt.getText())+"/WP:"
                            +"WF"+Integer.parseInt(heightNewTxt.getText())+"/WF:"
                            +"RA"+(oldRotationAngle+newRotationAngle)+"/RA");
                            //objFabric.setIntWarp(Integer.parseInt(widthNewTxt.getText()));
                            //objFabric.setIntWeft(Integer.parseInt(heightNewTxt.getText()));
                            //objFabric.setBytFabricIcon(imageInByte);
                            //component.setUserData(objFabric);
                            if(component.equals(leftBorder)||component.equals(rightBorder)){
                                if(borderLinkPolicy){
                                    leftBorder.setId(component.getId());
                                    /*component*/leftBorder.setImage(SwingFXUtils.toFXImage(bufferedImage1, null));
                                    bufferedImageRotated = getRightRotatedImage(bufferedImage1, 180);
                                    rightBorder.setId(component.getId());
                                    rightBorder.setImage(SwingFXUtils.toFXImage(bufferedImageRotated, null));
                                }
                                else{
                                    if(component.equals(leftBorder)){
                                        leftBorder.setImage(SwingFXUtils.toFXImage(bufferedImage1, null));
                                    }
                                    else{
                                        bufferedImageRotated = getRightRotatedImage(bufferedImage1, 180);
                                        rightBorder.setImage(SwingFXUtils.toFXImage(bufferedImageRotated, null));
                                    }
                                }
                                /*if(component.equals(leftBorder)){
                                    rightBorder.setId(component.getId());
                                    rightBorder.setImage(SwingFXUtils.toFXImage(bufferedImageRotated, null));
                                }
                                else{
                                    leftBorder.setId(component.getId());
                                    leftBorder.setImage(SwingFXUtils.toFXImage(bufferedImageRotated, null));
                                }*/
                                //rightBorder.setRotationAxis(Rotate.Z_AXIS);                    
                                //rightBorder.setRotate(180);
                                //rightBorder.setImage(SwingFXUtils.toFXImage(bufferedImage1, null));
                            } else if(component.equals(leftCrossBorder)||component.equals(rightCrossBorder)||component.equals(rightsideLeftCrossBorder)||component.equals(rightsideRightCrossBorder)){
                                if(crossBorderLinkPolicy){
                                    leftCrossBorder.setId(component.getId());
                                    rightCrossBorder.setId(component.getId());
                                    rightsideLeftCrossBorder.setId(component.getId());
                                    rightsideRightCrossBorder.setId(component.getId());
                                    leftCrossBorder.setImage(SwingFXUtils.toFXImage(bufferedImage1, null));
                                    rightCrossBorder.setImage(SwingFXUtils.toFXImage(bufferedImage1, null));
                                    rightsideLeftCrossBorder.setImage(SwingFXUtils.toFXImage(bufferedImage1, null));
                                    rightsideRightCrossBorder.setImage(SwingFXUtils.toFXImage(bufferedImage1, null));
                                }
                                else{
                                    component.setImage(SwingFXUtils.toFXImage(bufferedImage1, null));
                                }
                            } else if(component.equals(leftPallu)||component.equals(rightPallu)){
                                if(palluLinkPolicy){
                                    leftPallu.setId(component.getId());
                                    leftPallu.setImage(SwingFXUtils.toFXImage(bufferedImage1, null));
                                    bufferedImageRotated = getRightRotatedImage(bufferedImage1, 180);
                                    rightPallu.setId(component.getId());
                                    rightPallu.setImage(SwingFXUtils.toFXImage(bufferedImageRotated, null));
                                }
                                else{
                                    if(component.equals(leftPallu)){
                                        leftPallu.setImage(SwingFXUtils.toFXImage(bufferedImage1, null));
                                    }
                                    else{
                                        bufferedImageRotated = getRightRotatedImage(bufferedImage1, 180);
                                        rightPallu.setImage(SwingFXUtils.toFXImage(bufferedImageRotated, null));
                                    }
                                }
                            } else{
                                component.setImage(SwingFXUtils.toFXImage(bufferedImage1, null));
                            }
                            bufferedImage1=null;
                            dialogStage.close();
                            System.gc();
                        } catch(Exception ex){
                            new Logging("SEVERE",ClothView.class.getName(),"componentOrientation(): Apply Button "+ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });
                popup.add(btnApply, 1, 6);

                Scene scene = new Scene(popup);
                scene.getStylesheets().add(FabricView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                dialogStage.setScene(scene);
                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("SAVE"));
                dialogStage.showAndWait();        
            } else{
                final Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                BorderPane root = new BorderPane();
                Scene scene = new Scene(root, 400, 100, Color.WHITE);
                GridPane popup1=new GridPane();
                popup1.setHgap(5);
                popup1.setVgap(5);
                Label lblMsg = new Label(objDictionaryAction.getWord("NOCLOTHPART"));
                popup1.add(lblMsg, 0, 0);
                Button btnOK1 = new Button(objDictionaryAction.getWord("OK"));
                btnOK1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        dialogStage.close  ();
                        System.gc();
                    }
                });
                popup1.add(btnOK1, 1, 1);
                dialogStage.setScene(new Scene(popup1));
                dialogStage.showAndWait();
                lblStatus.setText(objDictionaryAction.getWord("NOCLOTHPART"));
            }
        } catch(Exception ex){
            new Logging("SEVERE",ClothView.class.getName(),"componentOrientation(): ",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    /**
     * Reorients (apply resizing, rotation and repeat mode) an image (using getId() of component)
     * and then fills this image in width w and height h (by making repeats)
     * @author Aatif Ahmad Khan
     * @see redesign()
     * @param component ImageView on which image needs to be reoriented
     * @param bufferedImage Repeat Image (motif) to be reoriented
     * @param w total width on which image needs to be reoriented
     * @param h total height on which image needs to be reoriented
     * @return reoriented image filled to width w & height h
     */
    public BufferedImage reOrientImage(ImageView component, BufferedImage bufferedImage, int w, int h){
        if(component.getId()!=null){
            String repeatMode=component.getId().substring(component.getId().indexOf("RM")+2, component.getId().indexOf("/RM"));
            String orientationWarp=component.getId().substring(component.getId().indexOf("WP")+2, component.getId().indexOf("/WP"));
            String orientationWeft=component.getId().substring(component.getId().indexOf("WF")+2, component.getId().indexOf("/WF"));
            String rotationAngle=component.getId().substring(component.getId().indexOf("RA")+2, component.getId().indexOf("/RA"));
            
            double zoomWidthFactor=Double.parseDouble(orientationWarp)/((Fabric)component.getUserData()).getIntWarp();
            double zoomHeightFactor=Double.parseDouble(orientationWeft)/((Fabric)component.getUserData()).getIntWeft();
            
            int vAling = 1;
            int hAling = 1;
            if(repeatMode.contains("Vertical")){
                String temp = repeatMode;
                vAling = Integer.parseInt(temp.substring(0, temp.indexOf("/")).trim());
                hAling = Integer.parseInt(temp.substring(2, temp.indexOf("Vertical")).trim());
            } else if(repeatMode.contains("Horizontal")){
                String temp = repeatMode;
                hAling = Integer.parseInt(temp.substring(0, temp.indexOf("/")).trim());
                vAling = Integer.parseInt(temp.substring(2, temp.indexOf("Horizontal")).trim());
            } else{
                vAling = 1;
                hAling = 1;
            }
            try{
                ArtworkAction objArtworkAction=new ArtworkAction();
                bufferedImage=getRightRotatedImage(bufferedImage, Integer.parseInt(rotationAngle));
                bufferedImage=redesign(bufferedImage, vAling, hAling
                    /*,Integer.parseInt(orientationWarp),Integer.parseInt(orientationWeft)*/
                    ,(int)(zoomWidthFactor*bufferedImage.getWidth()), (int)(zoomHeightFactor*bufferedImage.getHeight())
                    , w, h);
            }
            catch(SQLException sqlEx){}
        }                    
        return bufferedImage;
    }
    
    public void componentDescription(ImageView component, String strSearchBy){
        try{
            final Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root);
            GridPane popup1=new GridPane();
            popup1.setHgap(5);
            popup1.setVgap(5);
            Label lblMsg = new Label();
                
            if(component.getUserData()!=null){
                ///objFabric = new Fabric();
                objFabric=(Fabric) component.getUserData();
                ///objFabric.setStrFabricID(component.getId());
                objFabric.setStrSearchBy(strSearchBy);
                objFabric.setObjConfiguration(objConfiguration);
                //FabricAction objFabricAction = new FabricAction();
                ///objFabricAction.getFabric(objFabric);
                objFabric.getObjConfiguration().setStrGraphSize(12+"x"+(int)((12*objFabric.getIntPPI())/objFabric.getIntHPI()));
                lblMsg.setText(objDictionaryAction.getWord("NAME")+": "+objFabric.getStrFabricName()
                        +"\n"+objDictionaryAction.getWord("CLOTHTYPE")+": "+objFabric.getStrClothType()
                        +"\n"+objDictionaryAction.getWord("FABRICTYPE")+": "+objFabric.getStrFabricType()
                        +"\n"+objDictionaryAction.getWord("FABRICLENGTH")+"(inch): "+objFabric.getDblFabricLength()
                        +"\n"+objDictionaryAction.getWord("FABRICWIDTH")+"(inch): "+objFabric.getDblFabricWidth()
                        +"\n"+objDictionaryAction.getWord("HOOKS")+": "+objFabric.getIntHooks()
                        +"\n"+objDictionaryAction.getWord("PICKS")+": "+objFabric.getIntWeft()
                        +"\n"+objDictionaryAction.getWord("SHAFT")+": "+objFabric.getIntShaft()
                        +"\n"+objDictionaryAction.getWord("REEDCOUNT")+": "+objFabric.getIntReedCount()
                        +"\n"+objDictionaryAction.getWord("DENTS")+": "+objFabric.getIntDents()
                        +"\n"+objDictionaryAction.getWord("TPD")+": "+objFabric.getIntTPD()
                        +"\n"+objDictionaryAction.getWord("HPI")+": "+objFabric.getIntHPI()
                        +"\n"+objDictionaryAction.getWord("EPI")+": "+objFabric.getIntEPI()
                        +"\n"+objDictionaryAction.getWord("PPI")+": "+objFabric.getIntPPI()
                        +"\n"+objDictionaryAction.getWord("GRAPHSIZE")+": "+objFabric.getObjConfiguration().getStrGraphSize()
                        +"\n"+objDictionaryAction.getWord("DATE")+": "+objFabric.getStrFabricDate()
                );
                lblStatus.setText(objDictionaryAction.getWord("ACTIONFABRICDESCRIPTIONEDIT"));
            } else{
                lblMsg.setText(objDictionaryAction.getWord("NOCLOTHPART"));
                lblStatus.setText(objDictionaryAction.getWord("NOCLOTHPART"));
            }
            popup1.add(lblMsg, 0, 0);
            Button btnOK1 = new Button(objDictionaryAction.getWord("OK"));
            btnOK1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    dialogStage.close  ();
                    System.gc();
                }
            });
            popup1.add(btnOK1, 1, 1);
            dialogStage.setScene(new Scene(popup1));
            dialogStage.showAndWait();
            System.gc();
        } catch (Exception ex) {
            //Logger.getLogger(ClothView.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",ClothView.class.getName(),"componentDescription(): ",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    /**
     * Rotates an image rightwards (clockwise) specified by an angle. Angle should be an integral multiple of 90.
     * @since 0.8.2
     * @added 03 August 2017
     * @author Aatif Ahmad Khan
     * @param inputImage - input BufferedImage
     * @param angle - angle in degrees by which image needs to be rotated
     * @return outputImage - right (clockwise) rotated image by given angle
     */
    public BufferedImage getRightRotatedImage(BufferedImage inputImage, int angle){
        int times=angle/90;
        BufferedImage outputImage=inputImage;
        for(int a=0; a<times; a++){
            outputImage=getRightRotatedImage(outputImage);
        }
        return outputImage;
    }
	
    /**
     * Rotates an image by 90 degrees clockwise (towards right).
     * @since 0.8.2
     * @added 03 August 2017
     * @author Aatif Ahmad Khan
     * @param inputImage - input BufferedImage
     * @return outputImage - right (clockwise) rotated image by 90 degrees
     */
    public BufferedImage getRightRotatedImage(BufferedImage inputImage){
        int w=inputImage.getWidth();
        int h=inputImage.getHeight();
        BufferedImage outputImage=new BufferedImage(h, w, BufferedImage.TYPE_INT_ARGB);
        for(int a=0; a<w; a++){
            for(int b=0; b<h; b++){
                outputImage.setRGB((h-1)-b, a, inputImage.getRGB(a, b));
            }
        }
        return outputImage;
    }
    
    /**
     * Returns actual (formal name) of a garment component.
     * @since 0.8.3
     * @added 21 August 2017
     * @author Aatif Ahmad Khan
     * @param component - ImageView of a garment component
     * @return  Formal Name of that garment component
     */
    public String getComponentName(ImageView component){
        if(component.equals(leftBorder))
            return "Left Border";
        else if(component.equals(blouse))
            return "ceBlouse";//"Blouse";
        else if(component.equals(leftCrossBorder))
            return "Left Cross Border";
        else if(component.equals(leftPallu))
            return "Left Pallu";
        else if(component.equals(rightCrossBorder))
            return "Right Cross Border";
        else if(component.equals(body))
            return "ceBody";//"Body";
        else if(component.equals(skart))
            return "ceSkart";//"Skart";
        else if(component.equals(rightsideLeftCrossBorder))
            return "Rightside Left Cross Border";
        else if(component.equals(rightPallu))
            return "Right Pallu";
        else if(component.equals(rightsideRightCrossBorder))
            return "Rightside Right Cross Border";
        else if(component.equals(rightBorder))
            return "Right Border";
        else
            return null;
    }
    
    public String getComponentType(String componentName){
        if(componentName.equalsIgnoreCase("Left Border")||componentName.equalsIgnoreCase("Right Border"))
            return "Border";
        else if(componentName.equalsIgnoreCase("Left Cross Border")||componentName.equalsIgnoreCase("Right Cross Border")
                ||componentName.equalsIgnoreCase("Rightside Left Cross Border")||componentName.equalsIgnoreCase("Rightside Right Cross Border"))
            return "Cross Border";
        else if(componentName.equalsIgnoreCase("Left Pallu")||componentName.equalsIgnoreCase("Right Pallu"))
            return "Palu";
        else if(componentName.equalsIgnoreCase("ceBody"))
            return "Body";
        else if(componentName.equalsIgnoreCase("ceBlouse"))
            return "Blouse";
        else if(componentName.equalsIgnoreCase("ceSkart"))
            return "Skart";
        else
            return null;
    }
    
    public ImageView getComponentByName(String componentName){
        if(componentName.equals("Left Border"))
            return leftBorder;
        else if(componentName.equals("Blouse"))
            return blouse;//"Blouse";
        else if(componentName.equals("Left Cross Border"))
            return leftCrossBorder;
        else if(componentName.equals("Left Pallu"))
            return leftPallu;
        else if(componentName.equals("Right Cross Border"))
            return rightCrossBorder;
        else if(componentName.equals("Body"))
            return body;//"Body";
        else if(componentName.equals("Skart"))
            return skart;//"Skart";
        else if(componentName.equals("Rightside Left Cross Border"))
            return rightsideLeftCrossBorder;
        else if(componentName.equals("Right Pallu"))
            return rightPallu;
        else if(componentName.equals("Rightside Right Cross Border"))
            return rightsideRightCrossBorder;
        else if(componentName.equals("Right Border"))
            return rightBorder;
        else
            return null;
    }
    
    public int getComponentIndex(ImageView component){
        if(component.equals(leftBorder))
            return 0;
        else if(component.equals(blouse))
            return 1;
        else if(component.equals(leftCrossBorder))
            return 2;
        else if(component.equals(leftPallu))
            return 3;
        else if(component.equals(rightCrossBorder))
            return 4;
        else if(component.equals(body))
            return 5;
        else if(component.equals(skart))
            return 6;
        else if(component.equals(rightsideLeftCrossBorder))
            return 7;
        else if(component.equals(rightPallu))
            return 8;
        else if(component.equals(rightsideRightCrossBorder))
            return 9;
        else if(component.equals(rightBorder))
            return 10;
        else
            return -1;
    }
    
    public void clearContext(){
        objConfiguration.mapRecentFabric.put("ceBody", null);
        objConfiguration.mapRecentFabric.put("Left Border", null);
        objConfiguration.mapRecentFabric.put("Right Border", null);
        objConfiguration.mapRecentFabric.put("Left Cross Border", null);
        objConfiguration.mapRecentFabric.put("Right Cross Border", null);
        objConfiguration.mapRecentFabric.put("Rightside Left Cross Border", null);
        objConfiguration.mapRecentFabric.put("Rightside Right Cross Border", null);
        objConfiguration.mapRecentFabric.put("Left Pallu", null);
        objConfiguration.mapRecentFabric.put("Right Pallu", null);
        objConfiguration.mapRecentFabric.put("ceBlouse", null);
        objConfiguration.mapRecentFabric.put("ceSkart", null);
        
        objConfiguration.mapRecentFabric.put("Body", null);
        objConfiguration.mapRecentFabric.put("Blouse", null);
        objConfiguration.mapRecentFabric.put("Skart", null);
        objConfiguration.mapRecentFabric.put("Border", null);
        objConfiguration.mapRecentFabric.put("Palu", null);
        objConfiguration.mapRecentFabric.put("Cross Border", null);
        objConfiguration.mapRecentFabric.put("Konia", null);
    }
    
    public void updateMapFabricId(){
        for(String key: mapFabricId.keySet()){
            if(mapFabricId.get(key)!=null)
                objConfiguration.mapRecentFabric.put(key, mapFabricId.get(key));
        }
    }
    
    public void getMapFabricId(){
        if(objConfiguration.mapRecentFabric.get("Body")!=null)
            this.mapFabricId.put("Body", objConfiguration.mapRecentFabric.get("Body"));
        if(objConfiguration.mapRecentFabric.get("Border")!=null)
            this.mapFabricId.put("Border", objConfiguration.mapRecentFabric.get("Border"));
        if(objConfiguration.mapRecentFabric.get("Cross Border")!=null)
            this.mapFabricId.put("Cross Border", objConfiguration.mapRecentFabric.get("Cross Border"));
        if(objConfiguration.mapRecentFabric.get("Palu")!=null)
            this.mapFabricId.put("Palu", objConfiguration.mapRecentFabric.get("Palu"));
        if(objConfiguration.mapRecentFabric.get("Blouse")!=null)
            this.mapFabricId.put("Blouse", objConfiguration.mapRecentFabric.get("Blouse"));
        if(objConfiguration.mapRecentFabric.get("Skart")!=null)
            this.mapFabricId.put("Skart", objConfiguration.mapRecentFabric.get("Skart"));
    }
    
    public void saveContext(){
        clearContext();
        updateMapFabricId();
        // get current components on layout, and save their context
        for(int a=0; a<containerGP.getChildren().size(); a++){
            ScrollPane comp=(ScrollPane)containerGP.getChildren().get(a);
            if(((ImageView)comp.getContent()).getUserData()!=null){
                
                // i.e. some fabric is assigned to this component
                // save fabric from ImageView.getUserData() to make entries in tmp tables
                saveTmpFabric(((Fabric)((ImageView)comp.getContent()).getUserData()));
                
                // now make corresponding entries for same fabric in mla_fabric_orientation
                // for this we will use ImageView.getId() and (BufferedImage)ScrollPane.getUserData
                saveTmpFabricOrientation(comp);
                
                // put Fabric in MapRecent
                objConfiguration.mapRecentFabric.put(getComponentName((ImageView)comp.getContent()), ((Fabric)((ImageView)comp.getContent()).getUserData()).getStrFabricID());
            }
            else{
                objConfiguration.mapRecentFabric.put(getComponentName((ImageView)comp.getContent()), getComponentName((ImageView)comp.getContent()));
            }
        }
        // save layout
        /* for saving context we only need to put currently enabled components to MapRecent */
        
    }
    
    public void loadCloth(Cloth objCloth){
        try{
            ClothAction objClothAction=new ClothAction();
            objClothAction.getCloth(objCloth);
            enableAllComponents(false);
            for(String componentName:objCloth.mapComponentFabric.keySet()){
                if(objCloth.mapComponentFabric.get(componentName)!=null){
                    // component was enabled
                    if(componentName.equals("Left Border"))
                        leftBorderEnabled=true;
                    else if(componentName.equals("Blouse"))
                        blouseEnabled=true;
                    else if(componentName.equals("Left Cross Border"))
                        leftCrossBorderEnabled=true;
                    else if(componentName.equals("Left Pallu"))
                        leftPalluEnabled=true;
                    else if(componentName.equals("Right Cross Border"))
                        rightCrossBorderEnabled=true;
                    else if(componentName.equals("Skart"))
                        skartEnabled=true;
                    else if(componentName.equals("Rightside Left Cross Border"))
                        rightsideLeftCrossBorderEnabled=true;
                    else if(componentName.equals("Right Pallu"))
                        rightPalluEnabled=true;
                    else if(componentName.equals("Rightside Right Cross Border"))
                        rightsideRightCrossBorderEnabled=true;
                    else if(componentName.equals("Right Border"))
                        rightBorderEnabled=true;
                }
            }
            // plot layout
            plotFullCustomLayout();
            detectEnabledComponents();
            for(int a=0; a<containerGP.getChildren().size(); a++){
                ScrollPane comp=(ScrollPane)containerGP.getChildren().get(a);
                String componentName=getComponentName((ImageView)comp.getContent());
                if(componentName.equalsIgnoreCase("ceBody"))
                    componentName="Body";
                else if(componentName.equalsIgnoreCase("ceBlouse"))
                    componentName="Blouse";
                else if(componentName.equalsIgnoreCase("ceSkart"))
                    componentName="Skart";
                if(objCloth.mapComponentFabric.get(componentName).equalsIgnoreCase(componentName)){
                    continue;
                }
                System.err.println(componentName+":"+objCloth.mapComponentFabric.get(componentName));
                Fabric objFabric=new Fabric();
                objFabric.setObjConfiguration(objConfiguration);
                objFabric.setStrFabricID(objCloth.mapComponentFabric.get(componentName));
                loadFabric(objFabric);
                if(objFabric!=null){
                    ((ImageView)comp.getContent()).setUserData(objFabric);
                    loadTmpFabricOrientation(comp);
                }
            }
            if((heights[0]==heights[10])&&(widths[0]==widths[10])){
                if(leftBorder.getUserData()!=null&&rightBorder.getUserData()!=null){
                    if(((Fabric)leftBorder.getUserData()).getStrFabricID().equalsIgnoreCase(((Fabric)rightBorder.getUserData()).getStrFabricID())){
                        borderLinkPolicy=true;
                        linkBorderCB.setSelected(borderLinkPolicy);
                    }
                }
            }
            if((heights[3]==heights[8])&&(widths[3]==widths[8])){
                if(leftPallu.getUserData()!=null&&rightPallu.getUserData()!=null){
                    if(((Fabric)leftPallu.getUserData()).getStrFabricID().equalsIgnoreCase(((Fabric)rightPallu.getUserData()).getStrFabricID())){
                        palluLinkPolicy=true;
                        linkPalluCB.setSelected(palluLinkPolicy);
                    }
                }
            }
            if((heights[2]==heights[4])&&(widths[2]==widths[4])){
                if(leftCrossBorder.getUserData()!=null&&rightCrossBorder.getUserData()!=null){
                    if(((Fabric)leftCrossBorder.getUserData()).getStrFabricID().equalsIgnoreCase(((Fabric)rightCrossBorder.getUserData()).getStrFabricID())){
                        crossBorderLinkPolicy=true;
                        linkCrossBorderCB.setSelected(crossBorderLinkPolicy);
                    }
                }
            }

            recalculateBody();
            setComponentWidthHeight();
            plotFullCustomLayout();
        }
        catch(Exception ex){
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            new Logging("SEVERE",ClothView.class.getName(),"loadCloth(): ",ex);
        }
    }
    
    public void loadContext(){
        getMapFabricId();
        leftBorderEnabled=false;
        rightBorderEnabled=false;
        leftCrossBorderEnabled=false;
        rightCrossBorderEnabled=false;
        rightsideLeftCrossBorderEnabled=false;
        rightsideRightCrossBorderEnabled=false;
        // body is always enabled, hence no variable bodyEnabled
        leftPalluEnabled=false;
        rightPalluEnabled=false;
        blouseEnabled=false;
        skartEnabled=false;
        // get components from MapRecent on layout, and load their context
        if(objConfiguration.mapRecentFabric.get("ceBody")!=null){
            // body is always enabled
        }
        if(objConfiguration.mapRecentFabric.get("Left Border")!=null){
            leftBorderEnabled=true;
        }
        if(objConfiguration.mapRecentFabric.get("Right Border")!=null){
            rightBorderEnabled=true;
        }
        if(objConfiguration.mapRecentFabric.get("Left Cross Border")!=null){
            leftCrossBorderEnabled=true;
        }
        if(objConfiguration.mapRecentFabric.get("Right Cross Border")!=null){
            rightCrossBorderEnabled=true;
        }
        if(objConfiguration.mapRecentFabric.get("Rightside Left Cross Border")!=null){
            rightsideLeftCrossBorderEnabled=true;
        }
        if(objConfiguration.mapRecentFabric.get("Rightside Right Cross Border")!=null){
            rightsideRightCrossBorderEnabled=true;
        }
        if(objConfiguration.mapRecentFabric.get("Left Pallu")!=null){
            leftPalluEnabled=true;
        }
        if(objConfiguration.mapRecentFabric.get("Right Pallu")!=null){
            rightPalluEnabled=true;
        }
        if(objConfiguration.mapRecentFabric.get("ceBlouse")!=null){
            blouseEnabled=true;
        }
        if(objConfiguration.mapRecentFabric.get("ceSkart")!=null){
            skartEnabled=true;
        }
        //else if(objConfiguration.mapRecentFabric.get("Konia")!=null){}
        
        // plot layout
        plotFullCustomLayout();
        
        detectEnabledComponents();
        pinch=objConfiguration.getGarmentPinch();
        zoomFactor=objConfiguration.getGarmentZoom();
        /*int z=(int)(((double)pinch/12.0)*100);
        System.err.println("Z:"+z);
        for(int a=0; a<zoomCB.getItems().size(); a++){
            if(((String)zoomCB.getItems().get(a)).equals(String.valueOf(z))){
                zoomCB.setValue(zoomCB.getItems().get(a));
                System.err.println("Selected:"+zoomCB.getItems().get(a));
            }
        }*/
        
        // get current components on layout, and save their context
        for(int a=0; a<containerGP.getChildren().size(); a++){
            ScrollPane comp=(ScrollPane)containerGP.getChildren().get(a);
            Fabric objFabric=loadTmpFabric(getComponentName((ImageView)comp.getContent()));
            if(objFabric!=null){
                ((ImageView)comp.getContent()).setUserData(objFabric);
                loadTmpFabricOrientation(comp);
                if(comp.getUserData()==null){
                    if(((ImageView)comp.getContent()).equals(leftBorder)||((ImageView)comp.getContent()).equals(rightBorder)||((ImageView)comp.getContent()).equals(skart)){
                        widths[getComponentIndex(((ImageView)comp.getContent()))]=(int)((ImageView)comp.getContent()).getFitWidth();//(int)(Integer.parseInt(values[2]));
                        heights[getComponentIndex(((ImageView)comp.getContent()))]=(int)(((ImageView)comp.getContent()).getFitHeight()*(objConfiguration.getGarmentPinch()/12.0));
                    }else{
                        widths[getComponentIndex(((ImageView)comp.getContent()))]=(int)(((ImageView)comp.getContent()).getFitWidth()*(objConfiguration.getGarmentPinch()/12.0));
                        heights[getComponentIndex(((ImageView)comp.getContent()))]=(int)(((ImageView)comp.getContent()).getFitHeight()*(objConfiguration.getGarmentPinch()/12.0));
                    }
                    comp.setUserData(resizeFabricIcon(getFabricIcon(objFabric), (int)(objFabric.getDblArtworkLength()*objConfiguration.getGarmentPinch()), (int)(objFabric.getDblArtworkWidth()*objConfiguration.getGarmentPinch())));
                    ((ImageView)comp.getContent()).setId("RMRectangular (Default)/RM:WP"+objFabric.getIntWarp()+"/WP:WF"+objFabric.getIntWeft()+"/WF:RA0/RA");
                }
            }
        }
        if((heights[0]==heights[10])&&(widths[0]==widths[10])){
            if(leftBorder.getUserData()!=null&&rightBorder.getUserData()!=null){
                if(((Fabric)leftBorder.getUserData()).getStrFabricID().equalsIgnoreCase(((Fabric)rightBorder.getUserData()).getStrFabricID())){
                    borderLinkPolicy=true;
                    linkBorderCB.setSelected(borderLinkPolicy);
                }
            }
        }
        if((heights[3]==heights[8])&&(widths[3]==widths[8])){
            if(leftPallu.getUserData()!=null&&rightPallu.getUserData()!=null){
                if(((Fabric)leftPallu.getUserData()).getStrFabricID().equalsIgnoreCase(((Fabric)rightPallu.getUserData()).getStrFabricID())){
                    palluLinkPolicy=true;
                    linkPalluCB.setSelected(palluLinkPolicy);
                }
            }
        }
        if((heights[2]==heights[4])&&(widths[2]==widths[4])){
            if(leftCrossBorder.getUserData()!=null&&rightCrossBorder.getUserData()!=null){
                if(((Fabric)leftCrossBorder.getUserData()).getStrFabricID().equalsIgnoreCase(((Fabric)rightCrossBorder.getUserData()).getStrFabricID())){
                    crossBorderLinkPolicy=true;
                    linkCrossBorderCB.setSelected(crossBorderLinkPolicy);
                }
            }
        }
        
        recalculateBody();
        setComponentWidthHeight();
        plotFullCustomLayout();
    }
    
    public void loadFabric(Fabric objFabric){
        try {
            FabricAction objFabricAction = new FabricAction();
            objFabricAction.getFabric(objFabric);
            System.err.println("Conf:"+objFabric.getObjConfiguration().toString());
            //intVRepeat = (int)(objConfiguration.getDblFabricLength()/objConfiguration.getDblArtworkLength());
            //intHRepeat = (int)(objConfiguration.getDblFabricWidth()/objConfiguration.getDblArtworkWidth());
            Weave objWeave = new Weave();
            objWeave.setObjConfiguration(objFabric.getObjConfiguration());
            objWeave.setStrWeaveID(objFabric.getStrBaseWeaveID());
            WeaveAction objWeaveAction = new WeaveAction(); 
            objWeaveAction.getWeave(objWeave);
            objWeaveAction = new WeaveAction(); 
            objWeaveAction.extractWeaveContent(objWeave);
            objFabric.setBaseWeaveMatrix(objWeave.getDesignMatrix());
            
            if(objFabric.getStrArtworkID()!=null){                
                Artwork objArtwork = new Artwork();
                objArtwork.setObjConfiguration(objFabric.getObjConfiguration());
                objArtwork.setStrArtworkId(objFabric.getStrArtworkID());
                objFabric.setArtworkMatrix(objFabric.getFabricMatrix());
                objFabricAction = new FabricAction();
                objFabricAction.getFabricArtwork(objFabric);
                YarnAction objYarnAction = new YarnAction();                
                Yarn[] warpExtraYarn = objYarnAction.getFabricYarn(objFabric.getStrFabricID(), "Extra Warp");
                objFabric.setWarpExtraYarn(warpExtraYarn);
                objYarnAction = new YarnAction();
                Yarn[] weftExtraYarn = objYarnAction.getFabricYarn(objFabric.getStrFabricID(), "Extra Weft");
                objFabric.setWeftExtraYarn(weftExtraYarn);
            }else{
                Yarn[] warpExtraYarn = null;
                objFabric.setWarpExtraYarn(warpExtraYarn);
                objFabric.setIntExtraWarp(objConfiguration.getIntExtraWarp());
                Yarn[] weftExtraYarn = null;
                objFabric.setWeftExtraYarn(weftExtraYarn);
                objFabric.setIntExtraWeft(objConfiguration.getIntExtraWeft());
            }
            objFabricAction = new FabricAction();
            String[] threadPaletes = objFabricAction.getFabricPallets(objFabric);
            objFabric.setThreadPaletes(threadPaletes);
            
            YarnAction objYarnAction = new YarnAction();                
            Yarn[] warpYarn = objYarnAction.getFabricYarn(objFabric.getStrFabricID(), "Warp");
            objFabric.setWarpYarn(warpYarn);
            objYarnAction = new YarnAction();
            Yarn[] weftYarn = objYarnAction.getFabricYarn(objFabric.getStrFabricID(), "Weft");
            objFabric.setWeftYarn(weftYarn);
            
            if(objFabric.getStrArtworkID()!=null){
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
                for(int i=0;i<objFabric.getColorWeave().length;i++){
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
            
        } catch (SQLException ex) {
            new Logging("SEVERE",ClothView.class.getName(),"loadFabric(): "+ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            Logger.getLogger(ClothView.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",ClothView.class.getName(),"loadFabric(): "+ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public void saveFabric(Fabric objFabric){
        try{
            // use objconfiguration specific to current fabric via objFabric.getObjConfiguration()
            objFabric.setStrFabricID(new IDGenerator().getIDGenerator("FABRIC_LIBRARY", objFabric.getObjConfiguration().getObjUser().getStrUserID()));
            FabricAction objFabricAction = new FabricAction();
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
            //saveFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/save.png"));
            //saveFileVB.setDisable(false);
            //lblStatus.setText(objFabric.getStrFabricName()+" : "+objDictionaryAction.getWord("DATASAVED"));
        } catch (SQLException ex) {
            //Logger.getLogger(ClothView.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",ClothView.class.getName(),"saveFabric(): "+ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            //Logger.getLogger(ClothView.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",ClothView.class.getName(),"saveFabric(): "+ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public void saveTmpFabric(Fabric objFabric){
        try{
            // use objconfiguration specific to current fabric via objFabric.getObjConfiguration()
            objFabric.setStrFabricID(new IDGenerator().getIDGenerator("FABRIC_LIBRARY", objFabric.getObjConfiguration().getObjUser().getStrUserID()));
            TmpFabricAction objTmpFabricAction = new TmpFabricAction();
            objTmpFabricAction.fabricImage(objFabric,objFabric.getIntWeft(),objFabric.getIntWarp());
            objTmpFabricAction = new TmpFabricAction();
            if(objTmpFabricAction.setTmpFabric(objFabric)>0){
                objTmpFabricAction = new TmpFabricAction();
                objTmpFabricAction.setTmpFabricPallets(objFabric);
                String yarnId = null;
                for(int i = 0; i<objFabric.getWarpYarn().length; i++){
                    objTmpFabricAction = new TmpFabricAction();
                    yarnId = new IDGenerator().getIDGenerator("YARN_LIBRARY", objFabric.getObjConfiguration().getObjUser().getStrUserID());
                    objFabric.getWarpYarn()[i].setStrYarnId(yarnId);
                    objTmpFabricAction = new TmpFabricAction();
                    objFabric.getWarpYarn()[i].setObjConfiguration(objFabric.getObjConfiguration());
                    objTmpFabricAction.setYarn(objFabric.getWarpYarn()[i]);
                    objTmpFabricAction = new TmpFabricAction();
                    objTmpFabricAction.setTmpFabricYarn(objFabric.getStrFabricID(), objFabric.getWarpYarn()[i],i);
                }
                for(int i = 0; i<objFabric.getWeftYarn().length; i++){
                    objTmpFabricAction = new TmpFabricAction();
                    yarnId = new IDGenerator().getIDGenerator("YARN_LIBRARY", objFabric.getObjConfiguration().getObjUser().getStrUserID());
                    objFabric.getWeftYarn()[i].setStrYarnId(yarnId);
                    objTmpFabricAction = new TmpFabricAction();
                    objFabric.getWeftYarn()[i].setObjConfiguration(objFabric.getObjConfiguration());
                    objTmpFabricAction.setYarn(objFabric.getWeftYarn()[i]);
                    objTmpFabricAction = new TmpFabricAction();
                    objTmpFabricAction.setTmpFabricYarn(objFabric.getStrFabricID(), objFabric.getWeftYarn()[i],i);
                }
                if(objFabric.getWarpExtraYarn()!=null){
                    for(int i = 0; i<objFabric.getWarpExtraYarn().length; i++){
                        objTmpFabricAction = new TmpFabricAction();
                        yarnId = new IDGenerator().getIDGenerator("YARN_LIBRARY", objFabric.getObjConfiguration().getObjUser().getStrUserID());
                        objFabric.getWarpExtraYarn()[i].setStrYarnId(yarnId);
                        objTmpFabricAction = new TmpFabricAction();
                        objFabric.getWarpExtraYarn()[i].setObjConfiguration(objFabric.getObjConfiguration());
                        objTmpFabricAction.setYarn(objFabric.getWarpExtraYarn()[i]);
                        objTmpFabricAction = new TmpFabricAction();
                        objTmpFabricAction.setTmpFabricYarn(objFabric.getStrFabricID(), objFabric.getWarpExtraYarn()[i],i);
                    }
                }
                if(objFabric.getWeftExtraYarn()!=null){
                    for(int i = 0; i<objFabric.getWeftExtraYarn().length; i++){
                        yarnId = new IDGenerator().getIDGenerator("YARN_LIBRARY", objFabric.getObjConfiguration().getObjUser().getStrUserID());
                        objFabric.getWeftExtraYarn()[i].setStrYarnId(yarnId);
                        objTmpFabricAction = new TmpFabricAction();
                        objFabric.getWeftExtraYarn()[i].setObjConfiguration(objFabric.getObjConfiguration());
                        objTmpFabricAction.setYarn(objFabric.getWeftExtraYarn()[i]);
                        objTmpFabricAction = new TmpFabricAction();
                        objTmpFabricAction.setTmpFabricYarn(objFabric.getStrFabricID(), objFabric.getWeftExtraYarn()[i],i);
                    }
                }                        
                if(objFabric.getStrArtworkID()!=null){
                    objTmpFabricAction = new TmpFabricAction();
                    objTmpFabricAction.setTmpFabricArtwork(objFabric);
                }
            }
            //saveFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/save.png"));
            //saveFileVB.setDisable(false);
            //lblStatus.setText(objFabric.getStrFabricName()+" : "+objDictionaryAction.getWord("DATASAVED"));
        } catch (SQLException ex) {
            //Logger.getLogger(ClothView.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",ClothView.class.getName(),"saveTmpFabric(): "+ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            //Logger.getLogger(ClothView.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",ClothView.class.getName(),"saveTmpFabric(): "+ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public void saveTmpFabricOrientation(ScrollPane comp){
        String componentName=getComponentName((ImageView)comp.getContent());
        int componentWidth=(int)((ImageView)comp.getContent()).getFitWidth();
        int componentHeight=(int)((ImageView)comp.getContent()).getFitHeight();
        int repeatWidth=((BufferedImage)comp.getUserData()).getWidth();
        int repeatHeight=((BufferedImage)comp.getUserData()).getHeight();
        String id=((ImageView)comp.getContent()).getId();
        int rotationAngle=Integer.parseInt(id.substring(id.indexOf("RA")+2, id.indexOf("/RA")));
        String repeatMode=id.substring(id.indexOf("RM")+2, id.indexOf("/RM"));
        int tmpWeft=Integer.parseInt(id.substring(id.indexOf("WF")+2, id.indexOf("/WF")));//heightNewTxt
        int tmpWarp=Integer.parseInt(id.substring(id.indexOf("WP")+2, id.indexOf("/WP")));//widthNewTxt
        String fabricId=((Fabric)(((ImageView)comp.getContent()).getUserData())).getStrFabricID();
        try{
            TmpFabricAction objTmpFabricAction=new TmpFabricAction();
            objTmpFabricAction.setTmpFabricOrientation(fabricId, componentName, componentWidth, componentHeight, repeatWidth, repeatHeight, tmpWarp, tmpWeft, rotationAngle, repeatMode);
        }
        catch(SQLException sqlEx){
            new Logging("SEVERE",ClothView.class.getName(),"saveTmpFabricOrientation(): "+sqlEx.toString(),sqlEx);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public void loadTmpFabricOrientation(ScrollPane comp){
        String fabricId=((Fabric)(((ImageView)comp.getContent()).getUserData())).getStrFabricID();
        try{
            TmpFabricAction objTmpFabricAction=new TmpFabricAction();
            String[] values=objTmpFabricAction.getTmpFabricOrientation(fabricId, getComponentName(((ImageView)comp.getContent())));
            if(values!=null){
                if(values[0]==null||values[1]==null||values[2]==null||values[3]==null
                        ||values[4]==null||values[5]==null||values[6]==null||values[7]==null
                        ||values[8]==null||values[9]==null)
                    return;
                if(((ImageView)comp.getContent()).equals(leftBorder)||((ImageView)comp.getContent()).equals(rightBorder)||((ImageView)comp.getContent()).equals(skart)){
                    widths[getComponentIndex(((ImageView)comp.getContent()))]=(int)(Integer.parseInt(values[2]));
                    heights[getComponentIndex(((ImageView)comp.getContent()))]=(int)(Integer.parseInt(values[3])*zoomFactor);
                }else{
                    widths[getComponentIndex(((ImageView)comp.getContent()))]=(int)(Integer.parseInt(values[2])*zoomFactor);
                    heights[getComponentIndex(((ImageView)comp.getContent()))]=(int)(Integer.parseInt(values[3])*zoomFactor);
                }
                BufferedImage bufferedImage=getFabricIcon((Fabric)(((ImageView)comp.getContent()).getUserData()));
                bufferedImage=resizeFabricIcon(bufferedImage, (int)(Integer.parseInt(values[4])*zoomFactor), (int)(Integer.parseInt(values[5])*zoomFactor));
                comp.setUserData(bufferedImage);
                String id="RM"+values[9]+"/RM:"+"WP"+values[6]+"/WP:"+"WF"+values[7]+"/WF:"+"RA"+values[8]+"/RA";
                ((ImageView)comp.getContent()).setId(id);
            }
        }
        catch(SQLException sqlEx){
            new Logging("SEVERE",ClothView.class.getName(),"loadTmpFabricOrientation(): "+sqlEx.toString(),sqlEx);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public Fabric loadTmpFabric(String componentName){
        try{
            Fabric objFabric=null;
            TmpFabricAction objTmpFabricAction=new TmpFabricAction();
            if(objConfiguration.mapRecentFabric.get(componentName)!=null){
                // component was enabled before
                objFabric=new Fabric();
                if(objConfiguration.mapRecentFabric.get(componentName)==componentName){
                    // if no fabric was assigned previously but component was present in layout
                    if(objConfiguration.mapRecentFabric.get(getComponentType(componentName))!=null){
                        // coming from FE
                        objFabric.setObjConfiguration(objConfiguration);
                        objFabric.setStrFabricID(objConfiguration.mapRecentFabric.get(getComponentType(componentName)));
                        this.mapFabricId.put(getComponentType(componentName), objFabric.getStrFabricID());
                        loadFabric(objFabric);
                        return objFabric;
                    }
                    else
                        return null;
                }
                else{
                    objFabric.setStrFabricID(objConfiguration.mapRecentFabric.get(componentName));
                    // fetch fabric from tmp_fabric_library
                    objTmpFabricAction.getTmpFabric(objFabric);
                }
            }
            
            Weave objWeave = new Weave();
            objWeave.setObjConfiguration(objConfiguration);
            objWeave.setStrWeaveID(objFabric.getStrBaseWeaveID());
            WeaveAction objWeaveAction = new WeaveAction(); 
            objWeaveAction.getWeave(objWeave);
            objWeaveAction = new WeaveAction(); 
            objWeaveAction.extractWeaveContent(objWeave);
            byte[][] baseWeaveMatrix= objWeave.getDesignMatrix();
            objFabric.setBaseWeaveMatrix(baseWeaveMatrix);
            
            if(objFabric.getStrArtworkID()!=null){                
                Artwork objArtwork = new Artwork();
                objArtwork.setObjConfiguration(objFabric.getObjConfiguration());
                objArtwork.setStrArtworkId(objFabric.getStrArtworkID());
                objFabric.setArtworkMatrix(objFabric.getFabricMatrix());
                objTmpFabricAction = new TmpFabricAction();
                objTmpFabricAction.getTmpFabricArtwork(objFabric);
                objTmpFabricAction = new TmpFabricAction();                
                Yarn[] warpExtraYarn = objTmpFabricAction.getTmpFabricYarn(objFabric, "Extra Warp");
                objFabric.setWarpExtraYarn(warpExtraYarn);
                objTmpFabricAction = new TmpFabricAction();
                Yarn[] weftExtraYarn = objTmpFabricAction.getTmpFabricYarn(objFabric, "Extra Weft");
                objFabric.setWeftExtraYarn(weftExtraYarn);
            }else{
                Yarn[] warpExtraYarn = null;
                objFabric.setWarpExtraYarn(warpExtraYarn);
                objFabric.setIntExtraWarp(objConfiguration.getIntExtraWarp());
                Yarn[] weftExtraYarn = null;
                objFabric.setWeftExtraYarn(weftExtraYarn);
                objFabric.setIntExtraWeft(objConfiguration.getIntExtraWeft());
            }
            objTmpFabricAction = new TmpFabricAction();
            String[] threadPaletes = objTmpFabricAction.getTmpFabricPallets(objFabric);
            objFabric.setThreadPaletes(threadPaletes);
            
            objTmpFabricAction = new TmpFabricAction();
            Yarn[] warpYarn = objTmpFabricAction.getTmpFabricYarn(objFabric, "Warp");
            objFabric.setWarpYarn(warpYarn);
            objTmpFabricAction = new TmpFabricAction();
            Yarn[] weftYarn = objTmpFabricAction.getTmpFabricYarn(objFabric, "Weft");
            objFabric.setWeftYarn(weftYarn);
            
            if(objFabric.getStrArtworkID()!=null){
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
                for(int i=0;i<objFabric.getColorWeave().length;i++){
                    colorWeave[i][0] = objFabric.getColorWeave()[i][0];
                    colorWeave[i][1] = objFabric.getColorWeave()[i][1];
                    colorWeave[i][2] = objFabric.getColorWeave()[i][2];
                    //colorWeave[i][3] = objFabric.getColorWeave()[i][3];
                }
                objFabric.setColorWeave(colorWeave);
                colorWeave = null;
            }
            objFabric.setObjConfiguration(objConfiguration);
            return objFabric;
        }
        catch(SQLException sqlEx){
            // fabric failed to load from database
            new Logging("SEVERE",ClothView.class.getName(),"loadTmpFabric(): "+sqlEx.toString(),sqlEx);
            return null;
        }
        catch(IOException ioEx){
            // fabric rendered image was not generated
            new Logging("SEVERE",ClothView.class.getName(),"loadTmpFabric(): "+ioEx.toString(),ioEx);
            return null;
        }
        catch(Exception ex){
            new Logging("SEVERE",ClothView.class.getName(),"loadTmpFabric(): "+ex.toString(),ex);
            return null;
        }
    }
    
    /**
     * Setting initial widths & heights of all components as per full custom view
     * Note: this function modifies arrays only (not W/H of actual components)
     * @added 29 August 2017
     * @author Aatif Ahmad Khan
     * @since 0.8.3
     */
    public void setInitialWidthHeight(){
        // widths & heights are subject to availability of componets (i.e. whether they are enabled or not)
        // initially setting all widths/heights to zero
        for(int a=0; a<11; a++){
            widths[a]=0;
            heights[a]=0;
        }
        
        /* Needed Parameters */
        // Left Border
        if(leftBorderEnabled){
            widths[0]=(int)objConfiguration.WIDTH;
            heights[0]=3*pinch;
        }
        // Right Border
        if(rightBorderEnabled){
            widths[10]=(int)objConfiguration.WIDTH;
            heights[10]=3*pinch;
        }
        // Left Cross Border
        if(leftCrossBorderEnabled)
            widths[2]=3*pinch;
        if(rightCrossBorderEnabled)
            widths[4]=3*pinch;
        if(rightsideLeftCrossBorderEnabled)
            widths[7]=3*pinch;
        if(rightsideRightCrossBorderEnabled)
            widths[9]=3*pinch;
        
        // Skart
        if(skartEnabled)
            heights[6]=6*pinch;
        // Blouse
        if(blouseEnabled)
            widths[1]=38*pinch;
        // Left Pallu
        if(leftPalluEnabled)
            widths[3]=20*pinch;
        // Right Pallu
        if(rightPalluEnabled)
            widths[8]=20*pinch;
        
        /* Calculated Parameters */
        // Blouse
        if(blouseEnabled)
            heights[1]=46*pinch-(heights[0]+heights[10]);
        // Left Cross Border
        if(leftCrossBorderEnabled)
            heights[2]=46*pinch-(heights[0]+heights[10]);
        // Left Pallu
        if(leftPalluEnabled)
            heights[3]=46*pinch-(heights[0]+heights[10]);
        // Right Cross Border
        if(rightCrossBorderEnabled)
            heights[4]=46*pinch-(heights[0]+heights[10]);
        // Rightside Left Cross Border
        if(rightsideLeftCrossBorderEnabled)
            heights[7]=46*pinch-(heights[0]+heights[10]);
        // Right Pallu
        if(rightPalluEnabled)
            heights[8]=46*pinch-(heights[0]+heights[10]);
        // Rightside Right Cross Border
        if(rightsideRightCrossBorderEnabled)
            heights[9]=46*pinch-(heights[0]+heights[10]);
        
        // Body (always enabled)
        heights[5]=46*pinch-(heights[0]+heights[10]+heights[6]);
        widths[5]=(int)objConfiguration.WIDTH-(widths[1]+widths[2]+widths[3]+widths[4]+widths[7]+widths[8]+widths[9]);
        
        // Skart
        if(skartEnabled)
            widths[6]=(int)objConfiguration.WIDTH-(widths[1]+widths[2]+widths[3]+widths[4]+widths[7]+widths[8]+widths[9]);
    }
    
    public void recalculateBody(){
        heights[5]=46*pinch;
        if(leftBorderEnabled)
            heights[5]-=heights[0];
        if(rightBorderEnabled)
            heights[5]-=heights[10];
        if(skartEnabled)
            heights[5]-=heights[6];
        
        widths[5]=(int)objConfiguration.WIDTH;
        if(blouseEnabled)
            widths[5]-=widths[1];
        if(leftCrossBorderEnabled)
            widths[5]-=widths[2];
        if(leftPalluEnabled)
            widths[5]-=widths[3];
        if(rightCrossBorderEnabled)
            widths[5]-=widths[4];
        if(rightsideLeftCrossBorderEnabled)
            widths[5]-=widths[7];
        if(rightPalluEnabled)
            widths[5]-=widths[8];
        if(rightsideRightCrossBorderEnabled)
            widths[5]-=widths[9];
        
        if(skartEnabled)
            widths[6]=widths[5];
    }
    
    /**
     * This function actually sets Fit/Preferred W/H of components based on widths & heights array
     * @added 30 August 2017
     * @author Aatif Ahmad Khan
     * @since 0.8.3
     */
    public void setComponentWidthHeight(){
        // min/max height of Borders (width will always be objConfiguration.WIDTH if enabled)
        if(leftBorderEnabled){
            if(heights[0]<MINSIZE||heights[0]>MAXBORDER){
                return;
            }
        }
        if(rightBorderEnabled){
            if(heights[10]<MINSIZE||heights[10]>MAXBORDER){
                return;
            }
        }
        // min/max height of Skart (width will be calculated dynamically)
        if(skartEnabled){
            if(heights[6]<MINSIZE||heights[6]>MAXBORDER){
                return;
            }
        }
        
        // min/max width of Cross Borders (height will be calculated dynamically)
        if(leftCrossBorderEnabled){
            if(widths[2]<MINSIZE||widths[2]>MAXBORDER){
                return;
            }
        }
        if(rightCrossBorderEnabled){
            if(widths[4]<MINSIZE||widths[4]>MAXBORDER){
                return;
            }
        }
        if(rightsideLeftCrossBorderEnabled){
            if(widths[7]<MINSIZE||widths[7]>MAXBORDER){
                return;
            }
        }
        if(rightsideRightCrossBorderEnabled){
            if(widths[9]<MINSIZE||widths[9]>MAXBORDER){
                return;
            }
        }
        
        // min/max width of Pallus (height will be calculated dynamically)
        if(leftPalluEnabled){
            if(widths[3]<MINSIZE||widths[3]>MAXPALLU){
                return;
            }
        }
        if(rightPalluEnabled){
            if(widths[8]<MINSIZE||widths[8]>MAXPALLU){
                return;
            }
        }
        // min/max width of blouse (height will be calculated dynamically)
        if(blouseEnabled){
            if(widths[1]<MINSIZE||widths[1]>MAXBLOUSE){
                return;
            }
        }
        /* body
        if(heights[5]<100||widths[5]<100){
            System.err.println("Body size violation, it's width or height should not be less than 100 pixel.");
            return;
        }*/
        
        leftBorder.setFitWidth(widths[0]);
        leftBorder.setFitHeight(heights[0]);
        blouse.setFitWidth(widths[1]);
        blouse.setFitHeight(heights[1]);
        leftCrossBorder.setFitWidth(widths[2]);
        leftCrossBorder.setFitHeight(heights[2]);
        leftPallu.setFitWidth(widths[3]);
        leftPallu.setFitHeight(heights[3]);
        rightCrossBorder.setFitWidth(widths[4]);
        rightCrossBorder.setFitHeight(heights[4]);
        body.setFitWidth(widths[5]);
        body.setFitHeight(heights[5]);
        skart.setFitWidth(widths[6]);
        skart.setFitHeight(heights[6]);
        rightsideLeftCrossBorder.setFitWidth(widths[7]);
        rightsideLeftCrossBorder.setFitHeight(heights[7]);
        rightPallu.setFitWidth(widths[8]);
        rightPallu.setFitHeight(heights[8]);
        rightsideRightCrossBorder.setFitWidth(widths[9]);
        rightsideRightCrossBorder.setFitHeight(heights[9]);
        rightBorder.setFitWidth(widths[10]);
        rightBorder.setFitHeight(heights[10]);
        
        leftBorderPane.setPrefWidth(widths[0]);
        leftBorderPane.setPrefHeight(heights[0]);
        blousePane.setPrefWidth(widths[1]);
        blousePane.setPrefHeight(heights[1]);
        leftCrossBorderPane.setPrefWidth(widths[2]);
        leftCrossBorderPane.setPrefHeight(heights[2]);
        leftPalluPane.setPrefWidth(widths[3]);
        leftPalluPane.setPrefHeight(heights[3]);
        rightCrossBorderPane.setPrefWidth(widths[4]);
        rightCrossBorderPane.setPrefHeight(heights[4]);
        bodyPane.setPrefWidth(widths[5]);
        bodyPane.setPrefHeight(heights[5]);
        skartPane.setPrefWidth(widths[6]);
        skartPane.setPrefHeight(heights[6]);
        rightsideLeftCrossBorderPane.setPrefWidth(widths[7]);
        rightsideLeftCrossBorderPane.setPrefHeight(heights[7]);
        rightPalluPane.setPrefWidth(widths[8]);
        rightPalluPane.setPrefHeight(heights[8]);
        rightsideRightCrossBorderPane.setPrefWidth(widths[9]);
        rightsideRightCrossBorderPane.setPrefHeight(heights[9]);
        rightBorderPane.setPrefWidth(widths[10]);
        rightBorderPane.setPrefHeight(heights[10]);
    }
    
    /**
     * This functions sets ripple effect on other components W/H, based on updated W/H of a given component
     * @added 30 August 2017
     * @author Aatif Ahmad Khan
     * @since 0.8.3
     * @param componentName component which is last modified (needed to maintain symmetry if enabled)
     */
    public void updateWidthHeight(String componentName){
        /* initial widths and heights values are already set
        , now as one or more W/H changes, these values are recalculated
        This function assumes W/H of disabled components is 0 */
        
        // min/max height of Borders (width will always be objConfiguration.WIDTH if enabled)
        if(leftBorderEnabled){
            if(heights[0]<MINSIZE||heights[0]>MAXBORDER){
                return;
            }
        }
        if(rightBorderEnabled){
            if(heights[10]<MINSIZE||heights[10]>MAXBORDER){
                return;
            }
        }
        // min/max height of Skart (width will be calculated dynamically)
        if(skartEnabled){
            if(heights[6]<MINSIZE||heights[6]>MAXBORDER){
                return;
            }
        }
        
        // min/max width of Cross Borders (height will be calculated dynamically)
        if(leftCrossBorderEnabled){
            if(widths[2]<MINSIZE||widths[2]>MAXBORDER){
                return;
            }
        }
        if(rightCrossBorderEnabled){
            if(widths[4]<MINSIZE||widths[4]>MAXBORDER){
                return;
            }
        }
        if(rightsideLeftCrossBorderEnabled){
            if(widths[7]<MINSIZE||widths[7]>MAXBORDER){
                return;
            }
        }
        if(rightsideRightCrossBorderEnabled){
            if(widths[9]<MINSIZE||widths[9]>MAXBORDER){
                return;
            }
        }
        
        // min/max width of Pallus (height will be calculated dynamically)
        if(leftPalluEnabled){
            if(widths[3]<MINSIZE||widths[3]>MAXPALLU){
                return;
            }
        }
        if(rightPalluEnabled){
            if(widths[8]<MINSIZE||widths[8]>MAXPALLU){
                return;
            }
        }
        // min/max width of blouse (height will be calculated dynamically)
        if(blouseEnabled){
            if(widths[1]<MINSIZE||widths[1]>MAXBLOUSE){
                return;
            }
        }
        /* body
        if(heights[5]<100||widths[5]<100){
            System.err.println("Body size violation, it's width or height should not be less than 100 pixel.");
            return;
        }*/
        
        /* updating heights */
        // if H of Left/Right Border Changes
        if(borderLinkPolicy){
            if(componentName.equalsIgnoreCase("Left Border"))
                // left border height is modified, ripple effect on Right Border if enabled
                if(rightBorderEnabled)
                    heights[10]=heights[0];
            else if(componentName.equalsIgnoreCase("Right Border"))
                // right border height is modified, ripple effect on Left Border if enabled
                if(leftBorderEnabled)
                    heights[0]=heights[10];
        }
        // now heights of Left Border, Right Border and Skart are updated
        // H Blouse, Left Cross Border, Left Pallu, Right Cross Border
        // , Rightside Left Cross Border, Right Pallu, Rightside Right Cross Border
        // 46*pinch is the total height of GridPane 'containerGP'
        int hBorders=0;
        if(leftBorderEnabled)
            hBorders+=heights[0];
        if(rightBorderEnabled)
            hBorders+=heights[10];
        
        if(blouseEnabled)
            heights[1]=46*pinch-hBorders;//-(heights[0]+heights[10]);
        if(leftCrossBorderEnabled)
            heights[2]=46*pinch-hBorders;//-(heights[0]+heights[10]);
        if(leftPalluEnabled)
            heights[3]=46*pinch-hBorders;//-(heights[0]+heights[10]);
        if(rightCrossBorderEnabled)
            heights[4]=46*pinch-hBorders;//-(heights[0]+heights[10]);
        if(rightsideLeftCrossBorderEnabled)
            heights[7]=46*pinch-hBorders;//-(heights[0]+heights[10]);
        if(rightPalluEnabled)
            heights[8]=46*pinch-hBorders;//-(heights[0]+heights[10]);
        if(rightsideRightCrossBorderEnabled)
            heights[9]=46*pinch-hBorders;//-(heights[0]+heights[10]);
        
        // update height of Body
        int hSkart=0;
        if(skartEnabled)
            hSkart=heights[6];
        heights[5]=46*pinch-(hBorders+hSkart);//-(heights[0]+heights[10]+heights[6]);
        
        /* updating widths */
        if(crossBorderLinkPolicy){
            if(componentName.equalsIgnoreCase("Left Cross Border")){
                // left Cross Border width is modified, ripple effect on other Cross Borders
                if(rightCrossBorderEnabled)
                    widths[4]=widths[2];
                if(rightsideLeftCrossBorderEnabled)
                    widths[7]=widths[2];
                if(rightsideRightCrossBorderEnabled)
                    widths[9]=widths[2];
            }
            else if(componentName.equalsIgnoreCase("Right Cross Border")){
                // right Cross Border width is modified, ripple effect on other Cross Borders
                if(leftCrossBorderEnabled)
                    widths[2]=widths[4];
                if(rightsideLeftCrossBorderEnabled)
                    widths[7]=widths[4];
                if(rightsideRightCrossBorderEnabled)
                    widths[9]=widths[4];
            }
            else if(componentName.equalsIgnoreCase("Rightside Left Cross Border")){
                // rightside left Cross Border width is modified, ripple effect on other Cross Borders
                if(leftCrossBorderEnabled)
                    widths[2]=widths[7];
                if(rightCrossBorderEnabled)
                    widths[4]=widths[7];
                if(rightsideRightCrossBorderEnabled)
                    widths[9]=widths[7];
            }
            else if(componentName.equalsIgnoreCase("Rightside Right Cross Border")){
                // left Cross Border width is modified, ripple effect on other Cross Borders
                if(leftCrossBorderEnabled)
                    widths[2]=widths[9];
                if(rightCrossBorderEnabled)
                    widths[4]=widths[9];
                if(rightsideLeftCrossBorderEnabled)
                    widths[7]=widths[9];
            }
        }
        
        if(palluLinkPolicy){
            if(componentName.equalsIgnoreCase("Left Pallu"))
                // left pallu width is modified, ripple effect on Right Pallu if enabled
                if(rightPalluEnabled)
                    widths[8]=widths[3];
            else if(componentName.equalsIgnoreCase("Right Border"))
                // right pallu width is modified, ripple effect on Left Pallu if enabled
                if(leftPalluEnabled)
                    widths[3]=widths[8];
        }
        
        widths[5]=(int)objConfiguration.WIDTH-(widths[1]+widths[2]+widths[3]+widths[4]+widths[7]+widths[8]+widths[9]);
        
        if(skartEnabled)
            widths[6]=(int)objConfiguration.WIDTH-(widths[1]+widths[2]+widths[3]+widths[4]+widths[7]+widths[8]+widths[9]);
    }
    
    /**
     * Plots the enabled components based on Full Custom Layout
     * @added 28 August 2017
     * @author Aatif Ahmad Khan
     * @since 0.8.3
     */
    public void plotFullCustomLayout(){
	/**
         * pos  StartColumn StartRow    ColumnSpan  RowSpan
         * leftBorder (0)
	 * blouse (1)
         * leftCrossBorder (2)
         * leftPallu (3)
         * rightCrossBorder (4)
         * body (5)
	 * skart (6)
         * rightsideLeftCrossBorder (7)
         * rightPallu (8)
         * rightsideRightCrossBorder (9)
         * rightBorder (10)
         */
		 
	pos=new int[11][4];
        gpRowCount=1;
        gpColumnCount=1;
        pos[5][0]=0;
        pos[5][1]=0;
        pos[5][2]=1;
        pos[5][3]=1;
		
	containerGP.getChildren().clear();
		
        if(leftBorderEnabled){
            gpRowCount++;
            // update body pos
            pos[5][1]=gpRowCount-1;
            // add leftBorder pos
            pos[0][0]=0;
            pos[0][1]=0;
            pos[0][2]=1;
            pos[0][3]=1;
        }
        if(blouseEnabled){
            gpColumnCount++;
            // update column span of leftBorder
            if(leftBorderEnabled)
                pos[0][2]=gpColumnCount;
            // update body pos
            pos[5][0]=gpColumnCount-1;
            // add blouse pos
            pos[1][0]=0;
            pos[1][1]=gpRowCount-1;
            pos[1][2]=1;
            pos[1][3]=1;
        }
        if(leftCrossBorderEnabled){
            gpColumnCount++;
            // update column span of leftBorder
            if(leftBorderEnabled)
                pos[0][2]=gpColumnCount;
            // update body pos
            pos[5][0]=gpColumnCount-1;
            // add leftCrossBorder pos
            pos[2][0]=gpColumnCount-2;
            pos[2][1]=gpRowCount-1;
            pos[2][2]=1;
            pos[2][3]=1;
        }
        if(leftPalluEnabled){
            gpColumnCount++;
            // update column span of leftBorder
            if(leftBorderEnabled)
                pos[0][2]=gpColumnCount;
            // update body pos
            pos[5][0]=gpColumnCount-1;
            // add leftPallu pos
            pos[3][0]=gpColumnCount-2;
            pos[3][1]=gpRowCount-1;
            pos[3][2]=1;
            pos[3][3]=1;
        }
        if(rightCrossBorderEnabled){
            gpColumnCount++;
            // update column span of leftBorder
            if(leftBorderEnabled)
                pos[0][2]=gpColumnCount;
            // update body pos
            pos[5][0]=gpColumnCount-1;
            // add rightCrossBorder pos
            pos[4][0]=gpColumnCount-2;
            pos[4][1]=gpRowCount-1;
            pos[4][2]=1;
            pos[4][3]=1;
        }
		
	if(skartEnabled){
            gpRowCount++;
            // update row span of blouse
            if(blouseEnabled)
                pos[1][3]=2;
            // update row span of leftCrossBorder
            if(leftCrossBorderEnabled)
                pos[2][3]=2;
            // update row span of leftPallu
            if(leftPalluEnabled)
                pos[3][3]=2;
            // update row span of righrCrossBorder
            if(rightCrossBorderEnabled)
                pos[4][3]=2;
            // add skart pos
            pos[6][0]=gpColumnCount-1;
            pos[6][1]=gpRowCount-1;
            pos[6][2]=1;
            pos[6][3]=1;
        }
		
	if(rightsideLeftCrossBorderEnabled){
            gpColumnCount++;
            // update column span of leftBorder
            if(leftBorderEnabled)
                pos[0][2]=gpColumnCount;
            // add rightsideLeftCrossBorder pos
            pos[7][0]=gpColumnCount-1;
			pos[7][2]=1;
            if(leftBorderEnabled)
                pos[7][1]=1;
            else
                pos[7][1]=0;
            if(skartEnabled)
                pos[7][3]=2;
            else
                pos[7][3]=1;
        }
        if(rightPalluEnabled){
            gpColumnCount++;
            // update column span of leftBorder
            if(leftBorderEnabled)
                pos[0][2]=gpColumnCount;
            // add rightPallu pos
            pos[8][0]=gpColumnCount-1;
			pos[8][2]=1;
            if(leftBorderEnabled)
                pos[8][1]=1;
            else
                pos[8][1]=0;
            if(skartEnabled)
                pos[8][3]=2;
            else
                pos[8][3]=1;
        }
        if(rightsideRightCrossBorderEnabled){
            gpColumnCount++;
            // update column span of leftBorder
            if(leftBorderEnabled)
                pos[0][2]=gpColumnCount;
            // add rightsideRightCrossBorder pos
            pos[9][0]=gpColumnCount-1;
			pos[9][2]=1;
            if(leftBorderEnabled)
                pos[9][1]=1;
            else
                pos[9][1]=0;
            if(skartEnabled)
                pos[9][3]=2;
            else
                pos[9][3]=1;
        }
	if(rightBorderEnabled){
            gpRowCount++;
            pos[10][0]=0;
            pos[10][1]=gpRowCount-1;
            pos[10][2]=gpColumnCount;
            pos[10][3]=1;
        }
		
	/* updating fit heights */
        body.setFitHeight(46*pinch);
        bodyPane.setPrefHeight(46*pinch);
        if(blouseEnabled){
            blouse.setFitHeight(46*pinch);
            blousePane.setPrefHeight(46*pinch);
        }
	if(leftCrossBorderEnabled){
            leftCrossBorder.setFitHeight(46*pinch);
            leftCrossBorderPane.setPrefHeight(46*pinch);
        }
	if(leftPalluEnabled){
            leftPallu.setFitHeight(46*pinch);
            leftPalluPane.setPrefHeight(46*pinch);
        }
        if(rightCrossBorderEnabled){
            rightCrossBorder.setFitHeight(46*pinch);
            rightCrossBorderPane.setPrefHeight(46*pinch);
        }
        if(rightsideLeftCrossBorderEnabled){
            rightsideLeftCrossBorder.setFitHeight(46*pinch);
            rightsideLeftCrossBorderPane.setPrefHeight(46*pinch);
        }
        if(rightPalluEnabled){
            rightPallu.setFitHeight(46*pinch);
            rightPalluPane.setPrefHeight(46*pinch);
        }
        if(rightsideRightCrossBorderEnabled){
            rightsideRightCrossBorder.setFitHeight(46*pinch);
            rightsideRightCrossBorderPane.setPrefHeight(46*pinch);
        }
		
	if(leftBorderEnabled){
            body.setFitHeight(body.getFitHeight()-leftBorder.getFitHeight());
            bodyPane.setPrefHeight(bodyPane.getPrefHeight()-leftBorderPane.getPrefHeight());
            if(blouseEnabled){
                blouse.setFitHeight(blouse.getFitHeight()-leftBorder.getFitHeight());
                blousePane.setPrefHeight(blousePane.getPrefHeight()-leftBorderPane.getPrefHeight());
            }
            if(leftCrossBorderEnabled){
                leftCrossBorder.setFitHeight(leftCrossBorder.getFitHeight()-leftBorder.getFitHeight());
                leftCrossBorderPane.setPrefHeight(leftCrossBorderPane.getPrefHeight()-leftBorderPane.getPrefHeight());
            }
            if(leftPalluEnabled){
                leftPallu.setFitHeight(leftPallu.getFitHeight()-leftBorder.getFitHeight());
                leftPalluPane.setPrefHeight(leftPalluPane.getPrefHeight()-leftBorderPane.getPrefHeight());
            }
            if(rightCrossBorderEnabled){
                rightCrossBorder.setFitHeight(rightCrossBorder.getFitHeight()-leftBorder.getFitHeight());
                rightCrossBorderPane.setPrefHeight(rightCrossBorderPane.getPrefHeight()-leftBorderPane.getPrefHeight());
            }
            if(rightsideLeftCrossBorderEnabled){
                rightsideLeftCrossBorder.setFitHeight(rightsideLeftCrossBorder.getFitHeight()-leftBorder.getFitHeight());
                rightsideLeftCrossBorderPane.setPrefHeight(rightsideLeftCrossBorderPane.getPrefHeight()-leftBorderPane.getPrefHeight());
            }
            if(rightPalluEnabled){
                rightPallu.setFitHeight(rightPallu.getFitHeight()-leftBorder.getFitHeight());
                rightPalluPane.setPrefHeight(rightPalluPane.getPrefHeight()-leftBorderPane.getPrefHeight());
            }
            if(rightsideRightCrossBorderEnabled){
                rightsideRightCrossBorder.setFitHeight(rightsideRightCrossBorder.getFitHeight()-leftBorder.getFitHeight());
                rightsideRightCrossBorderPane.setPrefHeight(rightsideRightCrossBorderPane.getPrefHeight()-leftBorderPane.getPrefHeight());
            }
        }
        if(rightBorderEnabled){
            body.setFitHeight(body.getFitHeight()-rightBorder.getFitHeight());
            bodyPane.setPrefHeight(bodyPane.getPrefHeight()-rightBorderPane.getPrefHeight());
            if(blouseEnabled){
                blouse.setFitHeight(blouse.getFitHeight()-rightBorder.getFitHeight());
                blousePane.setPrefHeight(blousePane.getPrefHeight()-rightBorderPane.getPrefHeight());
            }
            if(leftCrossBorderEnabled){
                leftCrossBorder.setFitHeight(leftCrossBorder.getFitHeight()-rightBorder.getFitHeight());
                leftCrossBorderPane.setPrefHeight(leftCrossBorderPane.getPrefHeight()-rightBorderPane.getPrefHeight());
            }
            if(leftPalluEnabled){
                leftPallu.setFitHeight(leftPallu.getFitHeight()-rightBorder.getFitHeight());
                leftPalluPane.setPrefHeight(leftPalluPane.getPrefHeight()-rightBorderPane.getPrefHeight());
            }
            if(rightCrossBorderEnabled){
                rightCrossBorder.setFitHeight(rightCrossBorder.getFitHeight()-rightBorder.getFitHeight());
                rightCrossBorderPane.setPrefHeight(rightCrossBorderPane.getPrefHeight()-rightBorderPane.getPrefHeight());
            }
            if(rightsideLeftCrossBorderEnabled){
                rightsideLeftCrossBorder.setFitHeight(rightsideLeftCrossBorder.getFitHeight()-rightBorder.getFitHeight());
                rightsideLeftCrossBorderPane.setPrefHeight(rightsideLeftCrossBorderPane.getPrefHeight()-rightBorderPane.getPrefHeight());
            }
            if(rightPalluEnabled){
                rightPallu.setFitHeight(rightPallu.getFitHeight()-rightBorder.getFitHeight());
                rightPalluPane.setPrefHeight(rightPalluPane.getPrefHeight()-rightBorderPane.getPrefHeight());
            }
            if(rightsideRightCrossBorderEnabled){
                rightsideRightCrossBorder.setFitHeight(rightsideRightCrossBorder.getFitHeight()-rightBorder.getFitHeight());
                rightsideRightCrossBorderPane.setPrefHeight(rightsideRightCrossBorderPane.getPrefHeight()-rightBorderPane.getPrefHeight());
            }
        }
        if(skartEnabled){
            body.setFitHeight(body.getFitHeight()-skart.getFitHeight());
            bodyPane.setPrefHeight(bodyPane.getPrefHeight()-skartPane.getPrefHeight());
        }
		
	/* updating fit widths */
        body.setFitWidth(objConfiguration.WIDTH);
        bodyPane.setPrefWidth(objConfiguration.WIDTH);
        if(skartEnabled){
            skart.setFitWidth(objConfiguration.WIDTH);
            skartPane.setPrefWidth(objConfiguration.WIDTH);
        }
	if(blouseEnabled){
            body.setFitWidth(body.getFitWidth()-blouse.getFitWidth());
            bodyPane.setPrefWidth(bodyPane.getPrefWidth()-blousePane.getPrefWidth());
            if(skartEnabled){
                skart.setFitWidth(skart.getFitWidth()-blouse.getFitWidth());
                skartPane.setPrefWidth(skartPane.getPrefWidth()-blousePane.getPrefWidth());
            }
        }
	if(leftCrossBorderEnabled){
            body.setFitWidth(body.getFitWidth()-leftCrossBorder.getFitWidth());
            bodyPane.setPrefWidth(bodyPane.getPrefWidth()-leftCrossBorderPane.getPrefWidth());
            if(skartEnabled){
                skart.setFitWidth(skart.getFitWidth()-leftCrossBorder.getFitWidth());
                skartPane.setPrefWidth(skartPane.getPrefWidth()-leftCrossBorderPane.getPrefWidth());
            }
        }
	if(leftPalluEnabled){
            body.setFitWidth(body.getFitWidth()-leftPallu.getFitWidth());
            bodyPane.setPrefWidth(bodyPane.getPrefWidth()-leftPalluPane.getPrefWidth());
            if(skartEnabled){
                skart.setFitWidth(skart.getFitWidth()-leftPallu.getFitWidth());
                skartPane.setPrefWidth(skartPane.getPrefWidth()-leftPalluPane.getPrefWidth());
            }
        }
	if(rightCrossBorderEnabled){
            body.setFitWidth(body.getFitWidth()-rightCrossBorder.getFitWidth());
            bodyPane.setPrefWidth(bodyPane.getPrefWidth()-rightCrossBorderPane.getPrefWidth());
            if(skartEnabled){
                skart.setFitWidth(skart.getFitWidth()-rightCrossBorder.getFitWidth());
                skartPane.setPrefWidth(skartPane.getPrefWidth()-rightCrossBorderPane.getPrefWidth());
            }
        }
	if(rightsideLeftCrossBorderEnabled){
            body.setFitWidth(body.getFitWidth()-rightsideLeftCrossBorder.getFitWidth());
            bodyPane.setPrefWidth(bodyPane.getPrefWidth()-rightsideLeftCrossBorderPane.getPrefWidth());
            if(skartEnabled){
                skart.setFitWidth(skart.getFitWidth()-rightsideLeftCrossBorder.getFitWidth());
                skartPane.setPrefWidth(skartPane.getPrefWidth()-rightsideLeftCrossBorderPane.getPrefWidth());
            }
        }
        if(rightPalluEnabled){
            body.setFitWidth(body.getFitWidth()-rightPallu.getFitWidth());
            bodyPane.setPrefWidth(bodyPane.getPrefWidth()-rightPalluPane.getPrefWidth());
            if(skartEnabled){
                skart.setFitWidth(skart.getFitWidth()-rightPallu.getFitWidth());
                skartPane.setPrefWidth(skartPane.getPrefWidth()-rightPalluPane.getPrefWidth());
            }
        }
        if(rightsideRightCrossBorderEnabled){
            body.setFitWidth(body.getFitWidth()-rightsideRightCrossBorder.getFitWidth());
            bodyPane.setPrefWidth(bodyPane.getPrefWidth()-rightsideRightCrossBorderPane.getPrefWidth());
            if(skartEnabled){
                skart.setFitWidth(skart.getFitWidth()-rightsideRightCrossBorder.getFitWidth());
                skartPane.setPrefWidth(skartPane.getPrefWidth()-rightsideRightCrossBorderPane.getPrefWidth());
            }
        }
	if(pos[0][3]!=0)
            containerGP.add(leftBorderPane, pos[0][0], pos[0][1], pos[0][2], pos[0][3]);
        if(pos[1][3]!=0)
            containerGP.add(blousePane, pos[1][0], pos[1][1], pos[1][2], pos[1][3]);
        if(pos[2][3]!=0)
            containerGP.add(leftCrossBorderPane, pos[2][0], pos[2][1], pos[2][2], pos[2][3]);
        if(pos[3][3]!=0)
            containerGP.add(leftPalluPane, pos[3][0], pos[3][1], pos[3][2], pos[3][3]);
        if(pos[4][3]!=0)
            containerGP.add(rightCrossBorderPane, pos[4][0], pos[4][1], pos[4][2], pos[4][3]);
        if(pos[5][3]!=0)
            containerGP.add(bodyPane, pos[5][0], pos[5][1], pos[5][2], pos[5][3]);
        if(pos[6][3]!=0)
            containerGP.add(skartPane, pos[6][0], pos[6][1], pos[6][2], pos[6][3]);
        if(pos[7][3]!=0)
            containerGP.add(rightsideLeftCrossBorderPane, pos[7][0], pos[7][1], pos[7][2], pos[7][3]);
	if(pos[8][3]!=0)
            containerGP.add(rightPalluPane, pos[8][0], pos[8][1], pos[8][2], pos[8][3]);
	if(pos[9][3]!=0)
            containerGP.add(rightsideRightCrossBorderPane, pos[9][0], pos[9][1], pos[9][2], pos[9][3]);
        if(pos[10][3]!=0)
            containerGP.add(rightBorderPane, pos[10][0], pos[10][1], pos[10][2], pos[10][3]);
        repaintComponents();
        //containerGP.autosize();
        enableAllComponents(false);
    }
    
    public void enableAllComponents(boolean value){
        leftBorderEnabled=value;
        rightBorderEnabled=value;
        leftCrossBorderEnabled=value;
        rightCrossBorderEnabled=value;
        rightsideLeftCrossBorderEnabled=value;
        rightsideRightCrossBorderEnabled=value;
        leftPalluEnabled=value;
        rightPalluEnabled=value;
        blouseEnabled=value;
        skartEnabled=value;
    }
    
    public void showCurrentMultiRepeatCB(){
        for(int a=0; a<containerGP.getChildren().size(); a++){
            String componentName=getComponentName((ImageView)(((ScrollPane)containerGP.getChildren().get(a)).getContent()));
            if(componentName.equalsIgnoreCase("Left Border"))
                leftBorderMultiRepeatCB.setDisable(false);
            else if(componentName.equalsIgnoreCase("Right Border"))
                rightBorderMultiRepeatCB.setDisable(false);
            else if(componentName.equalsIgnoreCase("Left Cross Border"))
                leftCrossBorderMultiRepeatCB.setDisable(false);
            else if(componentName.equalsIgnoreCase("Right Cross Border"))
                rightCrossBorderMultiRepeatCB.setDisable(false);
            else if(componentName.equalsIgnoreCase("Rightside Left Cross Border"))
                rightsideLeftCrossBorderMultiRepeatCB.setDisable(false);
            else if(componentName.equalsIgnoreCase("Rightside Right Cross Border"))
                rightsideRightCrossBorderMultiRepeatCB.setDisable(false);
            else if(componentName.equalsIgnoreCase("Left Pallu"))
                leftPalluMultiRepeatCB.setDisable(false);
            else if(componentName.equalsIgnoreCase("Right Pallu"))
                rightPalluMultiRepeatCB.setDisable(false);
            else if(componentName.equalsIgnoreCase("ceBlouse"))
                blouseMultiRepeatCB.setDisable(false);
            else if(componentName.equalsIgnoreCase("ceSkart"))
                skartMultiRepeatCB.setDisable(false);
        }
    }
    
    public void detectEnabledComponents(){
        enableAllComponents(false);
        for(int a=0; a<containerGP.getChildren().size(); a++){
            String componentName=getComponentName((ImageView)(((ScrollPane)containerGP.getChildren().get(a)).getContent()));
            if(componentName.equalsIgnoreCase("Left Border"))
                leftBorderEnabled=true;
            else if(componentName.equalsIgnoreCase("Right Border"))
                rightBorderEnabled=true;
            else if(componentName.equalsIgnoreCase("Left Cross Border"))
                leftCrossBorderEnabled=true;
            else if(componentName.equalsIgnoreCase("Right Cross Border"))
                rightCrossBorderEnabled=true;
            else if(componentName.equalsIgnoreCase("Rightside Left Cross Border"))
                rightsideLeftCrossBorderEnabled=true;
            else if(componentName.equalsIgnoreCase("Rightside Right Cross Border"))
                rightsideRightCrossBorderEnabled=true;
            else if(componentName.equalsIgnoreCase("Left Pallu"))
                leftPalluEnabled=true;
            else if(componentName.equalsIgnoreCase("Right Pallu"))
                rightPalluEnabled=true;
            else if(componentName.equalsIgnoreCase("ceBlouse"))
                blouseEnabled=true;
            else if(componentName.equalsIgnoreCase("ceSkart"))
                skartEnabled=true;
        }
    }
    
    public void saveClothPart(ImageView component, String clothType){
        
        
    }
    
    /**
     * Sets Cloth Id, snapshot
     */
    public void saveAsCloth(){
        String id=new IDGenerator().getIDGenerator("CLOTH_LIBRARY", objConfiguration.getObjUser().getStrUserID());
        objCloth.setStrClothId(id);
        try{
            final WritableImage image = containerGP.snapshot(new SnapshotParameters(), null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            objCloth.setBytClothIcon(imageInByte);
        }
        catch(IOException ioEx){
            new Logging("SEVERE",ClothView.class.getName(),"saveAsCloth(): IO error while generating snapshot of cloth.",ioEx);
        }
        detectEnabledComponents();
        // save components one by one
        if(body.getUserData()!=null){
            saveFabric((Fabric)body.getUserData());
            objCloth.mapComponentFabric.put("Body", ((Fabric)body.getUserData()).getStrFabricID());
            saveTmpFabricOrientation(bodyPane);
        }
        else
            objCloth.mapComponentFabric.put("Body", "Body");
        
        if(leftBorderEnabled){
            if(leftBorder.getUserData()!=null){
                saveFabric((Fabric)leftBorder.getUserData());
                objCloth.mapComponentFabric.put("Left Border", ((Fabric)leftBorder.getUserData()).getStrFabricID());
                saveTmpFabricOrientation(leftBorderPane);
            }
            else
                objCloth.mapComponentFabric.put("Left Border", "Left Border");
        }
        if(rightBorderEnabled){
            if(rightBorder.getUserData()!=null){
                saveFabric((Fabric)rightBorder.getUserData());
                objCloth.mapComponentFabric.put("Right Border", ((Fabric)rightBorder.getUserData()).getStrFabricID());
                saveTmpFabricOrientation(rightBorderPane);
            }
            else
                objCloth.mapComponentFabric.put("Right Border", "Right Border");
        }
        if(leftCrossBorderEnabled){
            if(leftCrossBorder.getUserData()!=null){
                saveFabric((Fabric)leftCrossBorder.getUserData());
                objCloth.mapComponentFabric.put("Left Cross Border", ((Fabric)leftCrossBorder.getUserData()).getStrFabricID());
                saveTmpFabricOrientation(leftCrossBorderPane);
            }
            else
                objCloth.mapComponentFabric.put("Left Cross Border", "Left Cross Border");
        }
        if(rightCrossBorderEnabled){
            if(rightCrossBorder.getUserData()!=null){
                saveFabric((Fabric)rightCrossBorder.getUserData());
                objCloth.mapComponentFabric.put("Right Cross Border", ((Fabric)rightCrossBorder.getUserData()).getStrFabricID());
                saveTmpFabricOrientation(rightCrossBorderPane);
            }
            else
                objCloth.mapComponentFabric.put("Right Cross Border", "Right Cross Border");
        }
        if(rightsideLeftCrossBorderEnabled){
            if(rightsideLeftCrossBorder.getUserData()!=null){
                saveFabric((Fabric)rightsideLeftCrossBorder.getUserData());
                objCloth.mapComponentFabric.put("Rightside Left Cross Border", ((Fabric)rightsideLeftCrossBorder.getUserData()).getStrFabricID());
                saveTmpFabricOrientation(rightsideLeftCrossBorderPane);
            }
            else
                objCloth.mapComponentFabric.put("Rightside Left Cross Border", "Rightside Left Cross Border");
        }
        if(rightsideRightCrossBorderEnabled){
            if(rightsideRightCrossBorder.getUserData()!=null){
                saveFabric((Fabric)rightsideRightCrossBorder.getUserData());
                objCloth.mapComponentFabric.put("Rightside Right Cross Border", ((Fabric)rightsideRightCrossBorder.getUserData()).getStrFabricID());
                saveTmpFabricOrientation(rightsideRightCrossBorderPane);
            }
            else
                objCloth.mapComponentFabric.put("Rightside Right Cross Border", "Rightside Right Cross Border");
        }
        if(leftPalluEnabled){
            if(leftPallu.getUserData()!=null){
                saveFabric((Fabric)leftPallu.getUserData());
                objCloth.mapComponentFabric.put("Left Pallu", ((Fabric)leftPallu.getUserData()).getStrFabricID());
                saveTmpFabricOrientation(leftPalluPane);
            }
            else
                objCloth.mapComponentFabric.put("Left Pallu", "Left Pallu");
        }
        if(rightPalluEnabled){
            if(rightPallu.getUserData()!=null){
                saveFabric((Fabric)rightPallu.getUserData());
                objCloth.mapComponentFabric.put("Right Pallu", ((Fabric)rightPallu.getUserData()).getStrFabricID());
                saveTmpFabricOrientation(rightPalluPane);
            }
            else
                objCloth.mapComponentFabric.put("Right Pallu", "Right Pallu");
        }
        if(blouseEnabled){
            if(blouse.getUserData()!=null){
                saveFabric((Fabric)blouse.getUserData());
                objCloth.mapComponentFabric.put("Blouse", ((Fabric)blouse.getUserData()).getStrFabricID());
                saveTmpFabricOrientation(blousePane);
            }
            else
                objCloth.mapComponentFabric.put("Blouse", "Blouse");
        }
        if(skartEnabled){
            if(skart.getUserData()!=null){
                saveFabric((Fabric)skart.getUserData());
                objCloth.mapComponentFabric.put("Skart", ((Fabric)skart.getUserData()).getStrFabricID());
                saveTmpFabricOrientation(skartPane);
            }
            else
                objCloth.mapComponentFabric.put("Skart", "Skart");
        }
        try{
            ClothAction objClothAction=new ClothAction();
            byte result=objClothAction.setCloth(objCloth);
            if(result==1)
                lblStatus.setText(objCloth.getStrClothName()+" : "+objDictionaryAction.getWord("DATASAVED"));
            else
                lblStatus.setText(objCloth.getStrClothName()+" : "+objDictionaryAction.getWord("ERROR"));
        }
        catch(SQLException sqlEx){
            new Logging("SEVERE",ClothView.class.getName(),"saveAsCloth(): SQL error while saving cloth.",sqlEx);
        }
    }
    
    public void updateCloth(){
        
    }
    
    /**
     * Cloth Name, Region, Description, Access (Public/Private/Protected) is set here
     */
    public void saveUpdateCloth(){
        // if cloth access is Public, we need not show dialog for Save
        if(!isNew && objCloth.getStrClothAccess().equalsIgnoreCase("Public")){
            updateCloth();
        } else {
            final Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            GridPane popup=new GridPane();
            popup.setId("popup");
            popup.setHgap(5);
            popup.setVgap(5);
            
            final TextField txtName = new TextField();
            if(isNew){
                Label lblName = new Label(objDictionaryAction.getWord("NAME"));
                lblName.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNAME")));
                popup.add(lblName, 0, 0);
                txtName.setPromptText(objDictionaryAction.getWord("TOOLTIPNAME"));
                txtName.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNAME")));
                popup.add(txtName, 1, 0, 2, 1);
            }
            
            final TextField txtDescription = new TextField();
            if(isNew){
                Label lblDescription = new Label(objDictionaryAction.getWord("CLOTHDESCRIPTION"));
                lblDescription.setTooltip(new Tooltip(objDictionaryAction.getWord("CLOTHDESCRIPTION")));
                popup.add(lblDescription, 0, 1);
                txtDescription.setPromptText(objDictionaryAction.getWord("TOOLTIPCLOTHDESCRIPTION"));
                txtDescription.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLOTHDESCRIPTION")));
                popup.add(txtDescription, 1, 1, 2, 1);
            }
            
            final TextField txtCity = new TextField();
            if(isNew){
                Label lblCity = new Label(objDictionaryAction.getWord("CITY"));
                lblCity.setTooltip(new Tooltip(objDictionaryAction.getWord("CITY")));
                popup.add(lblCity, 0, 2);
                txtCity.setPromptText(objDictionaryAction.getWord("CITY"));
                txtCity.setTooltip(new Tooltip(objDictionaryAction.getWord("CITY")));
                popup.add(txtCity, 1, 2, 2, 1);
            }
            
            final ToggleGroup clothTG = new ToggleGroup();
            RadioButton clothPublicRB = new RadioButton(objDictionaryAction.getWord("PUBLIC"));
            clothPublicRB.setToggleGroup(clothTG);
            clothPublicRB.setUserData("Public");
            popup.add(clothPublicRB, 0, 3);
            RadioButton clothPrivateRB = new RadioButton(objDictionaryAction.getWord("PRIVATE"));
            clothPrivateRB.setToggleGroup(clothTG);
            clothPrivateRB.setUserData("Private");
            popup.add(clothPrivateRB, 1, 3);
            RadioButton clothProtectedRB = new RadioButton(objDictionaryAction.getWord("PROTECTED"));
            clothProtectedRB.setToggleGroup(clothTG);
            clothProtectedRB.setUserData("Protected");
            popup.add(clothProtectedRB, 2, 3);
            if(objConfiguration.getObjUser().getUserAccess("CLOTH_LIBRARY").equalsIgnoreCase("Public"))
                clothTG.selectToggle(clothPublicRB);
            else if(objConfiguration.getObjUser().getUserAccess("CLOTH_LIBRARY").equalsIgnoreCase("Protected"))
                clothTG.selectToggle(clothProtectedRB);
            else
                clothTG.selectToggle(clothPrivateRB);

            final PasswordField passPF= new PasswordField();
            final Label lblAlert = new Label();
            if(objConfiguration.getBlnAuthenticateService()){
                Label lblPassword=new Label(objDictionaryAction.getWord("SERVICEPASSWORD"));
                lblPassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                popup.add(lblPassword, 0, 4);
                passPF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                passPF.setPromptText(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
                popup.add(passPF, 1, 4, 2, 1);
                lblAlert.setStyle("-fx-wrap-text:true;");
                lblAlert.setPrefWidth(250);
                popup.add(lblAlert, 0, 6, 3, 1);
            }

            Button btnOK = new Button(objDictionaryAction.getWord("SAVE"));
            btnOK.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVE")));
            btnOK.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
            btnOK.setDefaultButton(true);
            btnOK.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {  
                    objCloth.setStrClothAccess(clothTG.getSelectedToggle().getUserData().toString());
                    if(objConfiguration.getBlnAuthenticateService()){
                        if(passPF.getText()!=null && passPF.getText()!="" && passPF.getText().trim().length()!=0){
                            if(Security.SecurePassword(passPF.getText(), objConfiguration.getObjUser().getStrUsername()).equals(objConfiguration.getObjUser().getStrAppPassword())){
                                if(isNew){
                                    if(txtName.getText()!=null && txtName.getText()!="" && txtName.getText().trim().length()!=0)
                                        objCloth.setStrClothName(txtName.getText().toString());
                                    else
                                        objCloth.setStrClothName("My Cloth");
                                    if(txtCity.getText()!=null && txtCity.getText()!="" && txtCity.getText().trim().length()!=0)
                                        objCloth.setStrClothRegion(txtCity.getText().toString());
                                    else
                                        objCloth.setStrClothRegion("Varansi");
                                    if(txtDescription.getText()!=null && txtDescription.getText()!="" && txtDescription.getText().trim().length()!=0)
                                        objCloth.setStrClothDescription(txtDescription.getText().toString());
                                    else
                                        objCloth.setStrClothDescription("No Description Available");
                                    saveAsCloth();
                                } else{
                                    updateCloth();
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
                                objCloth.setStrClothName(txtName.getText().toString());
                            else
                                objCloth.setStrClothName("My fabric");
                            if(txtCity.getText()!=null && txtCity.getText()!="" && txtCity.getText().trim().length()!=0)
                                objCloth.setStrClothRegion(txtCity.getText().toString());
                            else
                                objCloth.setStrClothRegion("Varanasi");
                            if(txtDescription.getText()!=null && txtDescription.getText()!="" && txtDescription.getText().trim().length()!=0)
                                objCloth.setStrClothDescription(txtDescription.getText().toString());
                            else
                                objCloth.setStrClothDescription("No Description Available");
                            saveAsCloth();
                        } else {
                            updateCloth();
                        }
                        dialogStage.close();
                    }
                }
            });
            popup.add(btnOK, 1, 5);
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
            popup.add(btnCancel, 0, 5);
            Scene scene = new Scene(popup, 300, 220);
            scene.getStylesheets().add(FabricView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
            dialogStage.setScene(scene);
            dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("SAVE"));
            dialogStage.showAndWait();
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.initOwner(WindowView.windowStage);
        new ClothView(stage); 
        new Logging("WARNING",ClothView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    public static void main(String[] args) {   
        launch(args);    
    }
}