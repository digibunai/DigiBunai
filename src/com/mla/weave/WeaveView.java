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
package com.mla.weave;

import com.mla.colour.Colour;
import com.mla.colour.ColourAction;
import com.mla.dictionary.DictionaryAction;
import com.mla.fabric.FabricView;
import com.mla.main.AboutView;
import com.mla.main.Configuration;
import com.mla.main.ContactView;
import com.mla.main.EncryptZip;
import com.mla.main.HelpView;
import com.mla.main.IDGenerator;
import com.mla.main.Logging;
import com.mla.main.MessageView;
import com.mla.main.TechnicalView;
import com.mla.main.WindowView;
import com.mla.secure.Security;
import com.mla.user.UserAction;
import com.mla.utility.Palette;
import com.mla.utility.SimulatorEditView;
import com.mla.utility.UtilityAction;
import com.mla.yarn.Yarn;
import com.mla.yarn.YarnPaletteView;
import com.mla.yarn.YarnView;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Skin;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javax.imageio.ImageIO;
/**
 *
 * @Designing GUI window for weave editor
 * @author Amit Kumar Singh
 * 
 */
public class WeaveView {
      
    Weave objWeave;
    WeaveAction objWeaveAction;
    Configuration objConfiguration;
    DictionaryAction objDictionaryAction;
            
    public static final String WIF_EXTENSION = ".wif";
    private static final String DRAFT_EXTENSION = ".wsml";
    
    private Stage weaveStage;
    private Stage weaveChildStage;
	private boolean isweaveChildStageOn;
    private BorderPane root;    
    private Scene scene;
    private ScrollPane container;
    private ToolBar toolBar;
    private MenuBar menuBar;
    private GridPane bodyContainer;
    private GridPane editThreadGP;
    
    private ScrollPane shaftSP;
    private ScrollPane automaticSP;
    private ScrollPane pegSP;
    private ScrollPane tieUpSP;
    private ScrollPane tradelesSP;
    private ScrollPane designSP;
    private ScrollPane dentingSP;
    private ScrollPane warpColorSP;
    private ScrollPane weftColorSP;
    private ScrollPane paletteColorSP;
    
    private GridPane containerGP;
    private GridPane shaftGP;
    private GridPane automaticGP;
    private GridPane pegGP;
    private GridPane tieUpGP;
    private GridPane tradelesGP;
    private GridPane designGP;
    private GridPane dentingGP;
    private GridPane warpColorGP;
    private GridPane weftColorGP;
    private GridPane paletteColorGP;
    
    private Menu homeMenu;
    private Menu fileMenu;
    private Menu editMenu;
    private Menu transformMenu;
    private Menu viewMenu;
    private Menu helpMenu;
    private Menu runMenu;
    private Label fileMenuLabel;
    private Label editMenuLabel;
    private Label transformMenuLabel;
    private Label viewMenuLabel;
    private Label runMenuLabel;
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;
        
    private TextField txtWeft;
    private TextField txtWarp;
    private TextField txtShaft;
    private TextField txtTreadles;
    private TextField txtPaddle;
    private CheckBox liftplan;
    private FileChooser weaveFileName;
    
    private VBox newFileVB;
    private VBox openFileVB; 
    private VBox loadFileVB;
    private VBox importFileVB;
    private VBox saveFileVB;    
    private VBox saveAsFileVB;
    private VBox saveXMLFileVB;
    private VBox exportFileVB;
    private VBox printFileVB;
    private VBox fabricFileVB;
    private VBox toolkitVB;
    private VBox quitFileVB; 
    
    private VBox undoEditVB;
    private VBox redoEditVB;
    private VBox propertiesEditVB;
    private VBox yarnEditVB;
    private VBox switchColorEditVB;     
    private VBox threadPatternEditVB;
    private VBox insertWarpEditVB;
    private VBox deleteWarpEditVB;
    private VBox insertWeftEditVB;
    private VBox deleteWeftEditVB;
    private VBox selectEditVB;
    private VBox copyEditVB;    
    private VBox cutEditVB;
    private VBox pasteEditVB; 
    private VBox mirrorVerticalVB;
    private VBox mirrorHorizontalVB;
    private VBox rotateClockwiseEditVB;
    private VBox rotateAnticlockwiseEditVB;
    private VBox moveRightEditVB;
    private VBox moveLeftEditVB;
    private VBox moveUpEditVB;
    private VBox moveDownEditVB;
    private VBox moveRight8EditVB;
    private VBox moveLeft8EditVB;
    private VBox moveUp8EditVB;
    private VBox moveDown8EditVB;
    private VBox tiltRightEditVB;
    private VBox tiltLeftEditVB;
    private VBox tiltUpEditVB;
    private VBox tiltDownEditVB;
    private VBox inversionEditVB;
    private VBox clearEditVB;
    private VBox compositeViewVB;
    private VBox gridViewVB;
    private VBox graphViewVB;
    private VBox switchSideViewVB;
    private VBox frontSideViewVB;
    private VBox simulationViewVB;
    private VBox tilledViewVB;
    private VBox colourwaysVB;
    private VBox normalViewVB;
    private VBox zoomInViewVB;
    private VBox zoomOutViewVB;
    
    Yarn[] warpYarn;
    Yarn[] weftYarn;
    
    int current_row=0;
    int current_col=0;
    int initial_row=0;
    int final_row=0;
    int initial_col=0;
    int final_col=0;
    int boxSize = 15;
    byte isDragBox=0;
    boolean isNew = true;
    boolean isWorkingMode = false;
    int intWarpColor=0;
    int intWeftColor=26;
    String editMode = "all";
    
    private ImageView designIV;
    private ImageView shaftIV;
    private ImageView pegIV;
    private ImageView tieUpIV;
    private ImageView tradelesIV;
    private ImageView dentingIV;
    private ImageView warpColorIV;
    private ImageView weftColorIV;
    private BufferedImage designImage;
    private BufferedImage shaftImage;
    private BufferedImage pegImage;
    private BufferedImage tieUpImage;
    private BufferedImage tradelesImage;
    private BufferedImage dentingImage;
    private BufferedImage warpColorImage;
    private BufferedImage weftColorImage;
    
    final int INT_SOLID_BLACK=-16777216;
    final int INT_SOLID_WHITE=-1;
    final int INT_SOLID_RED=-65536;
    final int INT_SOLID_GREY=-8355712;
    final int INT_TRANSPARENT=0;
    final int MAX_GRID_ROW=50;
    final int MAX_GRID_COL=100;
    int SELECTED_ROW=0;
    int SELECTED_COL=0;
    boolean isRegionSelected=false;
    boolean isSelectionMode=false;
    boolean isPasteMode=false;
    int activeGridRow=8;
    int activeGridCol=8;
    double currentZoomFactor=1;
    private Slider hSlider;
    private Slider vSlider;
    private ScrollPane vSliderSP;
    private ScrollPane hSliderSP;
    ArrayList<String> dragOverDots;
    ArrayList<String> dentDragOverDots;
    ArrayList<String> designDragOverDots;
    private CheckBox showRepeatsCB;
    
    public WeaveView(Configuration objConfigurationCall) {
        this.objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);
        objWeave = new Weave();
        objWeave.setObjConfiguration(objConfiguration);
        //objWeave.setStrThreadPalettes(objConfiguration.strThreadPalettes);
		isweaveChildStageOn=false;
        objWeave.setIntEPI(objConfiguration.getIntEPI());
        objWeave.setIntPPI(objConfiguration.getIntPPI());
        populateColorPalette();
        weaveStage = new Stage();
        root = new BorderPane();
        scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(WeaveView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm()); 
        
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
        toolBar.autosize();
        menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(weaveStage.widthProperty());
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
        // Edit menu - Toolbar, Color, Composite View, Support Lines, Ruler, Zoom-in, zoom-out
        transformMenuLabel = new Label(objDictionaryAction.getWord("OPERATION"));
        transformMenu = new Menu();
        transformMenu.setGraphic(transformMenuLabel);
        transformMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
				operationMenuAction();
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
        runMenuLabel = new Label();
        runMenuLabel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/run.png"));
        runMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("SIMULATION")));
        runMenu  = new Menu();
        runMenu.setGraphic(runMenuLabel);
        runMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if(isWorkingMode){
                    WeaveViewer objWeaveViewer=new WeaveViewer(objWeave);
                }
            }
        });
        
        helpMenu.getItems().addAll(helpMenuItem, technicalMenuItem, aboutMenuItem, contactMenuItem, new SeparatorMenuItem(), exitMenuItem);        
        menuBar.getMenus().addAll(homeMenu, fileMenu, editMenu, transformMenu, viewMenu, helpMenu, runMenu);
    
        container = new ScrollPane();
        container.setMaxSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        
        paletteColorSP = new ScrollPane(); 
        paletteColorSP.setPrefSize(objConfiguration.WIDTH*.08, objConfiguration.HEIGHT*0.60);
        //paletteColorSP.autosize();
        paletteColorSP.setId("subpopup");
        paletteColorGP=new GridPane();
        paletteColorSP.setContent(paletteColorGP);
                
        shaftSP = new ScrollPane(); 
        shaftSP.setPrefSize(objConfiguration.WIDTH*.52, objConfiguration.HEIGHT*0.15);
        shaftSP.setId("subpopup");
        shaftSP.setTooltip(new Tooltip(objDictionaryAction.getWord("DRAFTPANE")));
        //shaftGP=new GridPane();
        //shaftGP.setAlignment(Pos.BOTTOM_LEFT);
        //shaftSP.setContent(shaftGP);
        shaftIV=new ImageView();
        shaftIV.setImage(new Image("/media/FullGrid.png"));
        shaftSP.setContent(shaftIV);
        shaftImage=SwingFXUtils.fromFXImage(shaftIV.getImage(), null);
        shaftSP.setVmin(0);
        shaftSP.setVmax(shaftIV.getImage().getHeight());
        shaftIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                int mouseX=(int)t.getX();
                int mouseY=(int)t.getY();
                Point dot=getDotFromPixels(mouseX, mouseY);
                int dotRow=dot.y;
                if(!isGridDotActive(dot))
                    return;
                checkDotInCol(dot);
                dot.y=dotRow;
                invertDotColor(dot, shaftImage);
                if(getDotColor(dot, shaftImage)==INT_SOLID_BLACK)
                    objWeave.getShaftMatrix()[(objWeave.getShaftMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y)][dot.x]=1;
                else
                    objWeave.getShaftMatrix()[(objWeave.getShaftMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y)][dot.x]=0;
                
                refreshShaftImage();
                if(editMode.equalsIgnoreCase("none")){
                    objWeaveAction.populateShaftDesign(objWeave, (objWeave.getShaftMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y),dot.x);
                    objWeaveAction.populateTreadles(objWeave);
                    objWeave.setIntWeaveTreadles(objWeave.getIntWarp());
                    initTreadles();
                    objWeaveAction.populateTieUp(objWeave);
                    objWeaveAction.populatePegPlan(objWeave);
                    objWeave.setIntWeaveShaft(objWeave.getIntWeft());
                    initPeg();
                    plotTradeles();
                    plotTieUp();
                    plotPeg();
                    populateDplot();
                } else if(editMode.equalsIgnoreCase("all")){

                } else{
                    objWeaveAction.populateShaftDesign(objWeave, (objWeave.getShaftMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y),dot.x);
                    populateDplot();
                }
            }
        });

        dentingSP = new ScrollPane();
        dentingSP.setPrefSize(objConfiguration.WIDTH*.52, objConfiguration.HEIGHT*0.06);
        dentingSP.setId("subpopup");
        dentingSP.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        dentingSP.setVbarPolicy(ScrollBarPolicy.NEVER);
        dentingSP.setTooltip(new Tooltip(objDictionaryAction.getWord("DENTINGPANE")));
        //dentingGP=new GridPane();
        //dentingGP.setAlignment(Pos.CENTER_LEFT);
        //dentingSP.setContent(dentingGP);
        dentingIV=new ImageView();
        dentingIV.setImage(new Image("/media/Grid2.png"));
        dentingSP.setContent(dentingIV);
        dentingImage=SwingFXUtils.fromFXImage(dentingIV.getImage(), null);
        dentDragOverDots=new ArrayList<>();
        
        dentingIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                int mouseX=(int)t.getX();
                int mouseY=(int)t.getY();
                Point dot=getDotFromPixels(mouseX, mouseY);
                if(dot.y>=0&&dot.y<=1&&dot.x>=0&&dot.x<activeGridCol){
                    invertDotColor(dot, dentingImage);
                    if(getDotColor(dot, dentingImage)==INT_SOLID_BLACK){
                        objWeave.getDentMatrix()[dot.y][dot.x]=1;
                        // Added as only one dot in a col
                        dot.y=1-dot.y;
                        fillImageDotPixels(dot, INT_SOLID_WHITE, dentingImage);
                        objWeave.getDentMatrix()[dot.y][dot.x]=0;
                    }
                    else{
                        objWeave.getDentMatrix()[dot.y][dot.x]=0;
                        // Added as only one dot in a col
                        dot.y=1-dot.y;
                        fillImageDotPixels(dot, INT_SOLID_BLACK, dentingImage);
                        objWeave.getDentMatrix()[dot.y][dot.x]=1;
                    }
                    refreshDentingImage();
                }
            }
        });
        dentingIV.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                int mouseX=(int)t.getX();
                int mouseY=(int)t.getY();
                Point dot=getDotFromPixels(mouseX, mouseY);
                if(dot.y>=0&&dot.y<=1&&dot.x>=0&&dot.x<activeGridCol){
                    if(dentDragOverDots.contains((dot.x+","+dot.y).toString()))
                        return;
                    dentDragOverDots.add(dot.x+","+dot.y);
                    invertDotColor(dot, dentingImage);
                    if(getDotColor(dot, dentingImage)==INT_SOLID_BLACK){
                        objWeave.getDentMatrix()[dot.y][dot.x]=1;
                        // Added as only one dot in a col
                        dot.y=1-dot.y;
                        fillImageDotPixels(dot, INT_SOLID_WHITE, dentingImage);
                        objWeave.getDentMatrix()[dot.y][dot.x]=0;
                    }
                    else{
                        objWeave.getDentMatrix()[dot.y][dot.x]=0;
                        // Added as only one dot in a col
                        dot.y=1-dot.y;
                        fillImageDotPixels(dot, INT_SOLID_BLACK, dentingImage);
                        objWeave.getDentMatrix()[dot.y][dot.x]=1;
                    }
                    refreshDentingImage();
                }
            }
        });
        dentingIV.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                dentDragOverDots.clear();
            }
        });
        
        automaticSP = new ScrollPane();
        automaticSP.setPrefSize(objConfiguration.WIDTH*.30, objConfiguration.HEIGHT*0.21);
        automaticSP.setId("subpopup");
        automaticGP=new GridPane();
        automaticSP.setContent(automaticGP);
        
        weftColorSP = new ScrollPane();
        weftColorSP.setPrefSize(objConfiguration.WIDTH*.02, objConfiguration.HEIGHT*0.35);
        weftColorSP.setId("subpopup");
        weftColorSP.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        weftColorSP.setVbarPolicy(ScrollBarPolicy.NEVER);
        weftColorSP.setTooltip(new Tooltip(objDictionaryAction.getWord("WEFTCOLORPANE")));
        //weftColorGP=new GridPane();
        //weftColorGP.setAlignment(Pos.BOTTOM_CENTER);
        //weftColorSP.setContent(weftColorGP);
        weftColorIV=new ImageView();
        weftColorIV.setImage(new Image("/media/GridV.png"));
        weftColorSP.setContent(weftColorIV);
        weftColorImage=SwingFXUtils.fromFXImage(weftColorIV.getImage(), null);
        weftColorSP.setVmin(0);
        weftColorSP.setVmax(weftColorIV.getImage().getHeight());
    
        designSP = new ScrollPane();
        designSP.setPrefSize(objConfiguration.WIDTH*.52, objConfiguration.HEIGHT*0.38);
        designSP.setId("subpopup");
        designSP.setTooltip(new Tooltip(objDictionaryAction.getWord("DESIGNPANE")));
        //designGP=new GridPane();  
        //designGP.setAlignment(Pos.BOTTOM_LEFT);
        //designSP.setContent(designGP);
        designIV=new ImageView();
        designIV.setImage(new Image("/media/FullGrid.png"));
        designSP.setContent(designIV);
        designImage=SwingFXUtils.fromFXImage(designIV.getImage(), null);
        
        // set initial 24x24 grid white rest as it is
        resetActiveDesignGrid(activeGridCol, activeGridRow);
        refreshDesignImage();
        designIV.setPickOnBounds(true);
        isSelectionMode=false;
        designIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if(isSelectionMode&&isRegionSelected){
                    isRegionSelected=false;
                    return;
                }
                int mouseX=(int)t.getX();
                int mouseY=(int)t.getY();
                Point dot=getDotFromPixels(mouseX, mouseY);
                if(!isGridDotActive(dot)){
                    return;
                }
                current_row=getDesignMatrixRow(dot.y);
                current_col=dot.x;
                //int cX=dot.x;
                //int cY=dot.y;
                if(t.getButton()==MouseButton.SECONDARY){
                    pasteAction();
                    //objWeaveAction.paste(objWeave, getDesignMatrixRow(dot.y), dot.x);
                    drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
                    refreshDesignImage();
                    return;
                    /*if(isSelectionMode&&(!dragOverDots.isEmpty())){
                    // click to (circular) paste, copied dots
                    System.out.println(dragOverDots.size());
                    int color=-1;
                    String s="";
                    for(int r=0; r<SELECTED_ROW; r++){
                        for(int c=0; c<SELECTED_COL; c++){
                            s=dragOverDots.get((r*SELECTED_COL)+c);
                            dot.x=Integer.parseInt(s.substring(0, s.indexOf(",")));
                            dot.y=Integer.parseInt(s.substring(s.indexOf(",")+1, s.length()));
                            color=getDotColor(dot, designImage);
                            System.out.println("Dot from: "+dot.x+", "+dot.y);
                            fillImageDotPixels(dot, color, designImage);
                            dot.x=(cX+c)%activeGridCol;
                            dot.y=(MAX_GRID_ROW-activeGridRow)+((activeGridRow-(MAX_GRID_ROW-cY)+r)%activeGridRow);
                            System.out.println("Dot to: "+dot.x+", "+dot.y);
                            fillImageDotPixels(dot, color, designImage);
                        }
                    }
                    dragOverDots.clear();
                    // also deselect the selected dots
                }*/
                }
                
                /* For Testing Matrix to Draw Image
                if(!isGridDotActive(dot)){
                    byte[][] designMatrix=new byte[7][];
                    designMatrix[0]=designMatrix[2]=designMatrix[4]=designMatrix[6]=new byte[]{0, 1, 1};
                    designMatrix[1]=designMatrix[3]=designMatrix[5]=new byte[]{1, 1, 0};
                    drawImageFromMatrix(designMatrix, designImage);
                }*/
                invertDotColor(dot, designImage);
                if(getDotColor(dot, designImage)==INT_SOLID_BLACK)
                    objWeave.getDesignMatrix()[(objWeave.getDesignMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y)][dot.x]=1;
                else
                    objWeave.getDesignMatrix()[(objWeave.getDesignMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y)][dot.x]=0;
                drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
                refreshDesignImage();
                populateNplot();
                if(showRepeatsCB.isSelected()){
                    drawRepeatImageFromMatrix(objWeave.getDesignMatrix(), designImage);
                    drawRepeatImageFromMatrix(objWeave.getShaftMatrix(), shaftImage);
                    drawRepeatImageFromMatrix(objWeave.getPegMatrix(), pegImage);
                    drawRepeatImageFromMatrix(objWeave.getTreadlesMatrix(), tradelesImage);
                    drawRepeatImageFromMatrix(objWeave.getDentMatrix(), dentingImage);
                }
                System.gc();
                t.consume();
            }
        });
        // drag code
        // all dragged over points will be stored in this arraylist in the form (x,y)
        dragOverDots=new ArrayList<>();
        designDragOverDots=new ArrayList<>();
        designIV.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                int mouseX=(int)t.getX();
                int mouseY=(int)t.getY();
                Point dot=getDotFromPixels(mouseX, mouseY);
                if(!isGridDotActive(dot)){
                    return;
                }
                drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
                refreshDesignImage();
            }
        });
        designIV.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                int mouseX=(int)t.getX();
                int mouseY=(int)t.getY();
                Point dot=getDotFromPixels(mouseX, mouseY);
                //System.err.println("Mouse being dragged at: "+dot.x+", "+dot.y);
                String value=(int)dot.x+","+(int)dot.y;
                if(isSelectionMode){
                    if(isGridDotActive(dot)){
                        if(dragOverDots.isEmpty()){
                            dragOverDots.add(value); // this is initial point
                        }
                    }
                }
                else{
                    /*if(dragOverDots.contains(value))
                        return;
                    if(isGridDotActive(dot)){
                        dragOverDots.add(value);
                        if(getDotColor(dot, designImage)==INT_SOLID_BLACK)
                            objWeave.getDesignMatrix()[(objWeave.getDesignMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y)][dot.x]=0;
                        else
                            objWeave.getDesignMatrix()[(objWeave.getDesignMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y)][dot.x]=1;
                
                    }*/
                    if(designDragOverDots.contains(value))
                        return;
                    if(isGridDotActive(dot)){
                        designDragOverDots.add(value);
                        invertDotColor(dot, designImage);
                        if(getDotColor(dot, designImage)==INT_SOLID_BLACK)
                            objWeave.getDesignMatrix()[(objWeave.getDesignMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y)][dot.x]=1;
                        else
                            objWeave.getDesignMatrix()[(objWeave.getDesignMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y)][dot.x]=0;
                        refreshDesignImage();
                    }
                }
                t.consume();
            }
        });
        
        designIV.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if(isSelectionMode){
                    int mouseX=(int)t.getX();
                    int mouseY=(int)t.getY();
                    Point dot=getDotFromPixels(mouseX, mouseY);
                    //System.err.println("Mouse Released at: "+dot.x+", "+dot.y);
                    if(dragOverDots.isEmpty())
                        return;
                    String s=dragOverDots.get(0);
                    if(isGridDotActive(dot)){
                        int[][] rectCorners=getRectCorner(Integer.parseInt(s.substring(0, s.indexOf(",")))
                                , Integer.parseInt(s.substring(s.indexOf(",")+1, s.length())), (int)dot.x, (int)dot.y);
                        //System.out.println(rectCorners[0][0]+":"+rectCorners[0][1]+"\n"
                        //+rectCorners[1][0]+":"+rectCorners[1][1]+"\n"
                        //+rectCorners[2][0]+":"+rectCorners[2][1]+"\n"
                        //+rectCorners[3][0]+":"+rectCorners[3][1]+"\n");
                        //System.out.println(getIntRgbFromColor(255, 255, 0, 0));
                        initial_row=getDesignMatrixRow(Integer.parseInt(s.substring(s.indexOf(",")+1, s.length())));
                        initial_col=Integer.parseInt(s.substring(0, s.indexOf(",")));
                        final_row=getDesignMatrixRow(dot.y);
                        final_col=dot.x;
                        //objWeaveAction.copy(objWeave, getDesignMatrixRow(Integer.parseInt(s.substring(s.indexOf(",")+1, s.length()))), Integer.parseInt(s.substring(0, s.indexOf(","))), getDesignMatrixRow(dot.y), dot.x);
                        dragOverDots.clear();
                        SELECTED_ROW=rectCorners[1][1]-rectCorners[0][1]+1;
                        SELECTED_COL=rectCorners[3][0]-rectCorners[0][0]+1;
                        //System.out.println("R "+SELECTED_ROW+" C "+SELECTED_COL);
                        for(int r=rectCorners[0][1]; r<=rectCorners[1][1]; r++){
                            for(int c=rectCorners[0][0]; c<=rectCorners[3][0]; c++){
                                dot.x=c;
                                dot.y=r;
                                fillDotBorder(dot, designImage);
                                //dragOverDots.add(c+","+r);
                            }
                        }
                        refreshDesignImage();
                    }
                }
                else{
                    /*for(String s: dragOverDots){
                        invertDotColor(new Point(Integer.parseInt(s.substring(0, s.indexOf(",")))
                                ,Integer.parseInt(s.substring(s.indexOf(",")+1, s.length()))), designImage);
                    }
                    refreshDesignImage();
                    dragOverDots.clear();*/
                    designDragOverDots.clear();
                    populateNplot();
                }
                t.consume();
                isRegionSelected=true;
            }
        });
        
        
        hSliderSP=new ScrollPane();
        hSliderSP.setPrefHeight(30);
        hSliderSP.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        hSlider=new Slider(0, 100, 1);
        hSlider.setShowTickMarks(true);
        hSlider.setMajorTickUnit(1);
        hSlider.setMinorTickCount(0);
        hSlider.setPrefWidth(1000);
        hSlider.setPrefHeight(30);
        hSlider.setShowTickLabels(true);
        hSlider.setValue(activeGridCol);
        hSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                if(hSlider.isFocused()){
                    updateActiveDesignGrid(t1.intValue(), activeGridRow);
                    refreshDesignImage();
                    objWeave.setDesignMatrix(getDotDesignMatrix());
                    populateNplot();
                    //plotWarpColor();
                    System.gc();
                }
            }
        });
        //hSlider.setTooltip(new Tooltip(String.valueOf(hSlider.getValue())));
        hSliderSP.setContent(hSlider);
        
        vSliderSP=new ScrollPane();
        vSliderSP.setPrefWidth(30);
        vSliderSP.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        vSliderSP.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        vSlider=new Slider(0, 50, 1);
        vSlider.setOrientation(Orientation.VERTICAL);
        vSlider.setShowTickMarks(true);
        vSlider.setMajorTickUnit(1);
        vSlider.setMinorTickCount(0);
        vSlider.setShowTickLabels(true);
        vSlider.setPrefWidth(30);
        vSlider.setPrefHeight(500);
        vSlider.setValue(activeGridRow);
        vSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                if(vSlider.isFocused()){
                    updateActiveDesignGrid(activeGridCol, t1.intValue());
                    refreshDesignImage();
                    objWeave.setDesignMatrix(getDotDesignMatrix());
                    populateNplot();
                    System.gc();
                }
            }
        });
        //vSlider.setTooltip(new Tooltip(String.valueOf(vSlider.getValue())));
        vSliderSP.setContent(vSlider);
        vSliderSP.setVmax(designIV.getImage().getHeight());
        
        designSP.setVmin(0);
        designSP.setVmax(designIV.getImage().getHeight());
        
        tradelesSP = new ScrollPane();
        tradelesSP.setPrefSize(objConfiguration.WIDTH*.10, objConfiguration.HEIGHT*0.38);
        tradelesSP.setId("subpopup");
        tradelesSP.setTooltip(new Tooltip(objDictionaryAction.getWord("TRADLEPANE")));
        //tradelesGP=new GridPane();
        //tradelesGP.setAlignment(Pos.BOTTOM_LEFT);
        //tradelesSP.setContent(tradelesGP);
        tradelesIV=new ImageView();
        tradelesIV.setImage(new Image("/media/FullGrid.png"));
        tradelesSP.setContent(tradelesIV);
        tradelesImage=SwingFXUtils.fromFXImage(tradelesIV.getImage(), null);
        tradelesSP.setVmin(0);
        tradelesSP.setVmax(tradelesIV.getImage().getHeight());
        tradelesIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                int mouseX=(int)t.getX();
                int mouseY=(int)t.getY();
                Point dot=getDotFromPixels(mouseX, mouseY);
                int dotCol=dot.x;
                if(!isGridDotActive(dot)){
                    return;
                }
                checkDotInRow(dot);
                dot.x=dotCol;
                invertDotColor(dot, tradelesImage);
                if(getDotColor(dot, tradelesImage)==INT_SOLID_BLACK)
                    objWeave.getTreadlesMatrix()[(objWeave.getTreadlesMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y)][dot.x]=1;
                else
                    objWeave.getTreadlesMatrix()[(objWeave.getTreadlesMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y)][dot.x]=0;
                refreshTradelesImage();
                if(editMode.equalsIgnoreCase("none")){
                    objWeaveAction.populateTradelesDesign(objWeave, (objWeave.getTreadlesMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y),dot.x);
                    objWeaveAction.populateShaft(objWeave);
                    objWeave.setIntWeaveShaft(objWeave.getIntWeft());
                    initShaft();
                    objWeaveAction.populateTieUp(objWeave);
                    objWeaveAction.populatePegPlan(objWeave);
                    objWeave.setIntWeaveShaft(objWeave.getIntWeft());
                    initPeg();
                    populateDplot();
                    plotShaft();
                    plotPeg();
                    plotTieUp();
                } else if(editMode.equalsIgnoreCase("all")){

                } else{
                    objWeaveAction.populateTradelesDesign(objWeave, (objWeave.getTreadlesMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y),dot.x);
                    populateDplot();
                }
            }
        });
        
        tieUpSP = new ScrollPane();
        tieUpSP.setPrefSize(objConfiguration.WIDTH*.10, objConfiguration.HEIGHT*0.38);
        tieUpSP.setId("subpopup");
        tieUpSP.setTooltip(new Tooltip(objDictionaryAction.getWord("TIEUPPANE")));
        //tieUpGP=new GridPane();
        //tieUpGP.setAlignment(Pos.BOTTOM_LEFT);
        //tieUpSP.setContent(tieUpGP);
        tieUpIV=new ImageView();
        tieUpIV.setImage(new Image("/media/FullGrid.png"));
        tieUpSP.setContent(tieUpIV);
        tieUpImage=SwingFXUtils.fromFXImage(tieUpIV.getImage(), null);
        tieUpSP.setVmin(0);
        tieUpSP.setVmax(tieUpIV.getImage().getHeight());
        tieUpIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                int mouseX=(int)t.getX();
                int mouseY=(int)t.getY();
                Point dot=getDotFromPixels(mouseX, mouseY);
                if(!isGridDotActive(dot)){
                    return;
                }
                invertDotColor(dot, tieUpImage);
                if(getDotColor(dot, tieUpImage)==INT_SOLID_BLACK)
                    objWeave.getTieupMatrix()[(objWeave.getTieupMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y)][dot.x]=1;
                else
                    objWeave.getTieupMatrix()[(objWeave.getTieupMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y)][dot.x]=0;
                refreshTieUpImage();
                if(editMode.equalsIgnoreCase("none")){
                    objWeaveAction.populateTieUpDesign(objWeave, (objWeave.getTieupMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y),dot.x);
                    objWeaveAction.populateShaft(objWeave);
                    objWeave.setIntWeaveShaft(objWeave.getIntWeft());
                    initShaft();
                    objWeaveAction.populateTreadles(objWeave);
                    objWeave.setIntWeaveTreadles(objWeave.getIntWarp());
                    initTreadles();
                    objWeaveAction.populatePegPlan(objWeave);
                    objWeave.setIntWeaveShaft(objWeave.getIntWeft());
                    initPeg();
                    populateDplot();
                    plotShaft();
                    plotPeg();
                    plotTradeles();
                } else if(editMode.equalsIgnoreCase("all")){

                } else{
                    objWeaveAction.populateTieUpDesign(objWeave, (objWeave.getTieupMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y),dot.x);
                    populateDplot();
                }
            }
        });
        
        pegSP = new ScrollPane();
        pegSP.setPrefSize(objConfiguration.WIDTH*.10, objConfiguration.HEIGHT*0.38);
        pegSP.setId("subpopup");
        pegSP.setTooltip(new Tooltip(objDictionaryAction.getWord("PEGPANE")));
        //pegGP=new GridPane();
        //pegGP.setAlignment(Pos.BOTTOM_LEFT);
        //pegSP.setContent(pegGP);
        pegIV=new ImageView();
        pegIV.setImage(new Image("/media/FullGrid.png"));
        pegSP.setContent(pegIV);
        pegImage=SwingFXUtils.fromFXImage(pegIV.getImage(), null);
        pegSP.setVmin(0);
        pegSP.setVmax(pegIV.getImage().getHeight());
        pegIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                int mouseX=(int)t.getX();
                int mouseY=(int)t.getY();
                Point dot=getDotFromPixels(mouseX, mouseY);
                if(!isGridDotActive(dot))
                    return;
                invertDotColor(dot, pegImage);
                if(getDotColor(dot, pegImage)==INT_SOLID_BLACK)
                    objWeave.getPegMatrix()[(objWeave.getPegMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y)][dot.x]=1;
                else
                    objWeave.getPegMatrix()[(objWeave.getPegMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y)][dot.x]=0;
                refreshPegImage();
                if(editMode.equalsIgnoreCase("none")){
                    objWeaveAction.populatePegDesign(objWeave, (objWeave.getPegMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y),dot.x);
                    objWeaveAction.populateShaft(objWeave);
                    objWeave.setIntWeaveShaft(objWeave.getIntWeft());
                    initShaft();
                    objWeaveAction.populateTieUp(objWeave);
                    objWeaveAction.populateTreadles(objWeave);
                    objWeave.setIntWeaveTreadles(objWeave.getIntWarp());
                    initTreadles();
                    populateDplot();
                    plotShaft();
                    plotTieUp();
                    plotTradeles();
                } else if(editMode.equalsIgnoreCase("all")){

                } else{
                    objWeaveAction.populatePegDesign(objWeave, (objWeave.getPegMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y),dot.x);
                    populateDplot();
                }
            }
        });
    
        warpColorSP = new ScrollPane();
        warpColorSP.setPrefSize(objConfiguration.WIDTH*.52, objConfiguration.HEIGHT*0.04);
        warpColorSP.setId("subpopup");
        warpColorSP.setHbarPolicy(ScrollBarPolicy.NEVER);
        warpColorSP.setVbarPolicy(ScrollBarPolicy.NEVER);
        warpColorSP.setTooltip(new Tooltip(objDictionaryAction.getWord("WARPCOLORPANE")));
        //warpColorGP=new GridPane();
        //warpColorGP.setAlignment(Pos.CENTER_LEFT);
        //warpColorSP.setContent(warpColorGP);
        warpColorIV=new ImageView();
        warpColorIV.setImage(new Image("/media/GridH.png"));
        warpColorSP.setContent(warpColorIV);
        warpColorImage=SwingFXUtils.fromFXImage(warpColorIV.getImage(), null);
        
        warpColorIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                int mouseX=(int)t.getX();
                int mouseY=(int)t.getY();
                Point dot=getDotFromPixels(mouseX, mouseY);
                if(dot.x>=activeGridCol)
                    return;
                if(t.getClickCount()>1){
                    editYarn();
                }
                else{
                    int intColor=getIntRgbFromColor(255
                        , Integer.parseInt(objWeave.getObjConfiguration().getColourPalette()[intWarpColor].substring(0, 2), 16)
                        , Integer.parseInt(objWeave.getObjConfiguration().getColourPalette()[intWarpColor].substring(2, 4), 16)
                        , Integer.parseInt(objWeave.getObjConfiguration().getColourPalette()[intWarpColor].substring(4, 6), 16));
                    fillImageDotPixels(dot, intColor, warpColorImage);
                    objWeave.getWarpYarn()[dot.x]= objWeave.getObjConfiguration().getYarnPalette()[intWarpColor];
                    refreshImage(warpColorImage);
                }
            }
        });
        weftColorIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                int mouseX=(int)t.getX();
                int mouseY=(int)t.getY();
                Point dot=getDotFromPixels(mouseX, mouseY);
                if(!isGridDotActive(dot))
                    return;
                if(t.getClickCount()>1){
                    editYarn();
                }
                else{
                    int intColor=getIntRgbFromColor(255
                        , Integer.parseInt(objWeave.getObjConfiguration().getColourPalette()[intWeftColor].substring(0, 2), 16)
                        , Integer.parseInt(objWeave.getObjConfiguration().getColourPalette()[intWeftColor].substring(2, 4), 16)
                        , Integer.parseInt(objWeave.getObjConfiguration().getColourPalette()[intWeftColor].substring(4, 6), 16));
                    fillImageDotPixels(dot, intColor, weftColorImage);
                    objWeave.getWeftYarn()[((activeGridRow-1)-((MAX_GRID_ROW-1)-dot.y))]= objWeave.getObjConfiguration().getYarnPalette()[intWeftColor];
                    refreshImage(weftColorImage);
                }
            }
        });
        warpColorIV.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                int mouseX=(int)t.getX();
                int mouseY=(int)t.getY();
                Point dot=getDotFromPixels(mouseX, mouseY);
                if(dot.x>=activeGridCol||dot.x<0||dot.y!=0)
                    return;
                int intColor=getIntRgbFromColor(255
                    , Integer.parseInt(objWeave.getObjConfiguration().getColourPalette()[intWarpColor].substring(0, 2), 16)
                    , Integer.parseInt(objWeave.getObjConfiguration().getColourPalette()[intWarpColor].substring(2, 4), 16)
                    , Integer.parseInt(objWeave.getObjConfiguration().getColourPalette()[intWarpColor].substring(4, 6), 16));
                fillImageDotPixels(dot, intColor, warpColorImage);
                objWeave.getWarpYarn()[dot.x]= objWeave.getObjConfiguration().getYarnPalette()[intWarpColor];
                refreshImage(warpColorImage);
            }
        });
        weftColorIV.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                int mouseX=(int)t.getX();
                int mouseY=(int)t.getY();
                Point dot=getDotFromPixels(mouseX, mouseY);
                //if(!isGridDotActive(dot))
                if(dot.y<(MAX_GRID_ROW-activeGridRow)||dot.y>(MAX_GRID_ROW-1)||dot.x!=0)
                    return;
                int intColor=getIntRgbFromColor(255
                    , Integer.parseInt(objWeave.getObjConfiguration().getColourPalette()[intWeftColor].substring(0, 2), 16)
                    , Integer.parseInt(objWeave.getObjConfiguration().getColourPalette()[intWeftColor].substring(2, 4), 16)
                    , Integer.parseInt(objWeave.getObjConfiguration().getColourPalette()[intWeftColor].substring(4, 6), 16));
                fillImageDotPixels(dot, intColor, weftColorImage);
                objWeave.getWeftYarn()[((activeGridRow-1)-((MAX_GRID_ROW-1)-dot.y))]= objWeave.getObjConfiguration().getYarnPalette()[intWeftColor];
                refreshImage(weftColorImage);
            }
        });
        
        automaticGP.getChildren().clear();
        automaticGP.setStyle("-fx-wrap-text:true;");
        automaticGP.add(new Label(objDictionaryAction.getWord("AUTOMATICEDIT")), 0, 0);
        final ToggleGroup automaticTG = new ToggleGroup();
        RadioButton shaftEditRB = new RadioButton(objDictionaryAction.getWord("Fix Peg, Tradeles, Tie-up Pane"));
        shaftEditRB.setToggleGroup(automaticTG);
        shaftEditRB.setUserData("shaft");
        automaticGP.add(shaftEditRB, 0, 1);
        RadioButton tieupEditRB = new RadioButton(objDictionaryAction.getWord("Fix Peg, Tradeles, Drafting Pane"));
        tieupEditRB.setToggleGroup(automaticTG);
        tieupEditRB.setUserData("tieup");
        automaticGP.add(tieupEditRB, 0, 2);
        RadioButton tradelesEditRB = new RadioButton(objDictionaryAction.getWord("Fix Peg, Tie-up, Drafting Pane"));
        tradelesEditRB.setToggleGroup(automaticTG);
        tradelesEditRB.setUserData("tradeles");
        automaticGP.add(tradelesEditRB, 0, 3);
        RadioButton pegEditRB = new RadioButton(objDictionaryAction.getWord("Fix Tradeles, Tie-up, Drafting Pane"));
        pegEditRB.setToggleGroup(automaticTG);
        pegEditRB.setUserData("peg");
        automaticGP.add(pegEditRB, 0, 4);
        RadioButton allEditRB = new RadioButton(objDictionaryAction.getWord("Fix All Pane"));
        allEditRB.setToggleGroup(automaticTG);
        allEditRB.setUserData("all");
        automaticGP.add(allEditRB, 0, 5);
        RadioButton noneEditRB = new RadioButton(objDictionaryAction.getWord("Fix None"));
        noneEditRB.setToggleGroup(automaticTG);
        noneEditRB.setUserData("none");
        automaticGP.add(noneEditRB, 0, 6);
        automaticGP.setVgap(2);
        Separator sepRepeat = new Separator();
        sepRepeat.setOrientation(Orientation.HORIZONTAL);
        sepRepeat.setPrefWidth(automaticGP.getPrefWidth());
        automaticGP.add(sepRepeat, 0, 7);
        showRepeatsCB=new CheckBox(objDictionaryAction.getWord("REPEATS"));
        automaticGP.add(showRepeatsCB, 0, 8);
        if(editMode.equalsIgnoreCase("shaft"))
            automaticTG.selectToggle(shaftEditRB);
        else if(editMode.equalsIgnoreCase("tieup"))
            automaticTG.selectToggle(tieupEditRB);
        else if(editMode.equalsIgnoreCase("tradeles"))
            automaticTG.selectToggle(tradelesEditRB);
        else if(editMode.equalsIgnoreCase("peg"))
            automaticTG.selectToggle(pegEditRB);
        else if(editMode.equalsIgnoreCase("all"))
            automaticTG.selectToggle(allEditRB);
        else
            automaticTG.selectToggle(noneEditRB);
  
        automaticTG.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                if (automaticTG.getSelectedToggle() != null) {
                    editMode = automaticTG.getSelectedToggle().getUserData().toString();
                }
            }
        });
        
        showRepeatsCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if(t1.booleanValue()==true){
                    isWorkingMode=false;
                    menuHighlight(null);
					if(isweaveChildStageOn)
                        weaveChildStage.hide();
                    // disable sliders
                    vSlider.setDisable(true);
                    hSlider.setDisable(true);
                    designIV.setMouseTransparent(true);
                    tradelesIV.setMouseTransparent(true);
                    tieUpIV.setMouseTransparent(true);
                    shaftIV.setMouseTransparent(true);
                    pegIV.setMouseTransparent(true);
                    // show repeats
                    drawRepeatImageFromMatrix(objWeave.getDesignMatrix(), designImage);
                    drawRepeatImageFromMatrix(objWeave.getShaftMatrix(), shaftImage);
                    drawRepeatImageFromMatrix(objWeave.getPegMatrix(), pegImage);
                    drawRepeatImageFromMatrix(objWeave.getTreadlesMatrix(), tradelesImage);
                    drawRepeatImageFromMatrix(objWeave.getDentMatrix(), dentingImage);
                }
                else{
                    isWorkingMode=true;
                    menuHighlight(null);
					if(isweaveChildStageOn)
                        weaveChildStage.show();
                    // enable sliders
                    vSlider.setDisable(false);
                    hSlider.setDisable(false);
                    designIV.setMouseTransparent(false);
                    tradelesIV.setMouseTransparent(false);
                    tieUpIV.setMouseTransparent(false);
                    shaftIV.setMouseTransparent(false);
                    pegIV.setMouseTransparent(false);
                    // hide repeats
                    drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
                    populateNplot();
                }
            }
        });

        DoubleProperty hsPosition = new SimpleDoubleProperty();
        hsPosition.bind(shaftSP.hvalueProperty());
        hsPosition.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                 designSP.setHvalue((double) arg2);
                 warpColorSP.setHvalue((double) arg2);
                 dentingSP.setHvalue((double) arg2);
                 hSliderSP.setHvalue((double) arg2);
            }
        }); 
        DoubleProperty hdPosition = new SimpleDoubleProperty();
        hdPosition.bind(designSP.hvalueProperty());
        hdPosition.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                 shaftSP.setHvalue((double) arg2);
                 warpColorSP.setHvalue((double) arg2);
                 dentingSP.setHvalue((double) arg2);
                 hSliderSP.setHvalue((double) arg2);
            }
        }); 
        DoubleProperty hwarpPosition = new SimpleDoubleProperty();
        hwarpPosition.bind(warpColorSP.hvalueProperty());
        hwarpPosition.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                 shaftSP.setHvalue((double) arg2);
                 designSP.setHvalue((double) arg2);
                 dentingSP.setHvalue((double) arg2);
                 hSliderSP.setHvalue((double) arg2);
            }
        });
        DoubleProperty hdentPosition = new SimpleDoubleProperty();
        hdentPosition.bind(dentingSP.hvalueProperty());
        hdentPosition.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                 shaftSP.setHvalue((double) arg2);
                 warpColorSP.setHvalue((double) arg2);
                 designSP.setHvalue((double) arg2);
                 hSliderSP.setHvalue((double) arg2);
            }
        }); 
        DoubleProperty hSliderPosition = new SimpleDoubleProperty();
        hSliderPosition.bind(hSliderSP.hvalueProperty());
        hSliderPosition.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                 shaftSP.setHvalue((double) arg2);
                 warpColorSP.setHvalue((double) arg2);
                 designSP.setHvalue((double) arg2);
                 dentingSP.setHvalue((double) arg2);
            }
        });
        
        DoubleProperty vdPosition = new SimpleDoubleProperty();
        vdPosition.bind(designSP.vvalueProperty());
        vdPosition.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                 pegSP.setVvalue((double) arg2);
                 tieUpSP.setVvalue((double) arg2);
                 tradelesSP.setVvalue((double) arg2);
                 weftColorSP.setVvalue((double) arg2);
                 vSliderSP.setVvalue((double) arg2);
            }
        });
        DoubleProperty vpPosition = new SimpleDoubleProperty();
        vpPosition.bind(pegSP.vvalueProperty());
        vpPosition.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                 designSP.setVvalue((double) arg2);
                 tieUpSP.setVvalue((double) arg2);
                 tradelesSP.setVvalue((double) arg2);
                 weftColorSP.setVvalue((double) arg2);
                 vSliderSP.setVvalue((double) arg2);
            }
        });
        DoubleProperty vtiePosition = new SimpleDoubleProperty();
        vtiePosition.bind(tieUpSP.vvalueProperty());
        vtiePosition.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                 pegSP.setVvalue((double) arg2);
                 designSP.setVvalue((double) arg2);
                 tradelesSP.setVvalue((double) arg2);
                 weftColorSP.setVvalue((double) arg2);
                 vSliderSP.setVvalue((double) arg2);
            }
        });
        DoubleProperty vtPosition = new SimpleDoubleProperty();
        vtPosition.bind(tradelesSP.vvalueProperty());
        vtPosition.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                 pegSP.setVvalue((double) arg2);
                 tieUpSP.setVvalue((double) arg2);
                 designSP.setVvalue((double) arg2);
                 weftColorSP.setVvalue((double) arg2);
                 vSliderSP.setVvalue((double) arg2);
            }
        });
        DoubleProperty vweftPosition = new SimpleDoubleProperty();
        vweftPosition.bind(weftColorSP.vvalueProperty());
        vweftPosition.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                 pegSP.setVvalue((double) arg2);
                 tieUpSP.setVvalue((double) arg2);
                 designSP.setVvalue((double) arg2);
                 tradelesSP.setVvalue((double) arg2);
                 vSliderSP.setVvalue((double) arg2);
            }
        });
        DoubleProperty vSliderPosition = new SimpleDoubleProperty();
        vSliderPosition.bind(vSliderSP.vvalueProperty());
        vSliderPosition.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                 pegSP.setVvalue((double) arg2);
                 tieUpSP.setVvalue((double) arg2);
                 designSP.setVvalue((double) arg2);
                 tradelesSP.setVvalue((double) arg2);
                 weftColorSP.setVvalue((double) arg2);
            }
        });
        
        objWeave = new Weave();
        objWeave.setObjConfiguration(objConfiguration);
        objWeave.setIntEPI(objConfiguration.getIntEPI());
        objWeave.setIntPPI(objConfiguration.getIntPPI());
        //assignKeyAction();
        containerGP=new GridPane();
        containerGP.setHgap(5);
        containerGP.setVgap(5);
        containerGP.setPadding(new Insets(10));
        
        ScrollPane dummyShaftSP = new ScrollPane();
        dummyShaftSP.setPrefSize(objConfiguration.WIDTH*.02, objConfiguration.HEIGHT*0.21);
        dummyShaftSP.setId("dummypopup");
        dummyShaftSP.setHbarPolicy(ScrollBarPolicy.NEVER);
        dummyShaftSP.setVbarPolicy(ScrollBarPolicy.NEVER);
        
        ScrollPane dummyPegSP = new ScrollPane();
        dummyPegSP.setPrefSize(objConfiguration.WIDTH*.10, objConfiguration.HEIGHT*0.04);
        dummyPegSP.setId("dummypopup");
        dummyPegSP.setHbarPolicy(ScrollBarPolicy.NEVER);
        dummyPegSP.setVbarPolicy(ScrollBarPolicy.NEVER);
        Label lblPeg=new Label();
        lblPeg.setText(objDictionaryAction.getWord("PEGPANE"));
        lblPeg.setId("message");
        lblPeg.setVisible(true);
        dummyPegSP.setContent(lblPeg);
        
        ScrollPane dummyTieUpSP = new ScrollPane();
        dummyTieUpSP.setPrefSize(objConfiguration.WIDTH*.10, objConfiguration.HEIGHT*0.04);
        dummyTieUpSP.setId("dummypopup");
        dummyTieUpSP.setHbarPolicy(ScrollBarPolicy.NEVER);
        dummyTieUpSP.setVbarPolicy(ScrollBarPolicy.NEVER);
        Label lblTieup=new Label();
        lblTieup.setText(objDictionaryAction.getWord("TIEUPPANE"));
        lblTieup.setId("message");
        lblTieup.setVisible(true);
        dummyTieUpSP.setContent(lblTieup);
        
        ScrollPane dummyTradleSP = new ScrollPane();
        dummyTradleSP.setPrefSize(objConfiguration.WIDTH*.10, objConfiguration.HEIGHT*0.04);
        dummyTradleSP.setId("dummypopup");
        dummyTradleSP.setHbarPolicy(ScrollBarPolicy.NEVER);
        dummyTradleSP.setVbarPolicy(ScrollBarPolicy.NEVER);
        Label lblTreadles=new Label();
        lblTreadles.setText(objDictionaryAction.getWord("TRADLEPANE"));
        lblTreadles.setId("message");
        lblTreadles.setVisible(true);
        dummyTradleSP.setContent(lblTreadles);
        
        containerGP.add(paletteColorSP,0,0,1,5);
        
        containerGP.add(dummyShaftSP,1,0,2,2);
        containerGP.add(weftColorSP, 1,2,1,1);
        
        containerGP.add(vSliderSP,2,2,1,1); // added
        containerGP.add(hSliderSP,3,3,1,1); // added
        
        containerGP.add(shaftSP,3,0,1,1);  /// col+1
        containerGP.add(dentingSP,3,1,1,1); /// col+1
        containerGP.add(designSP,3,2,1,1);  /// col+1
        containerGP.add(warpColorSP, 3,4,1,1); /// col+1 row+1
        
        containerGP.add(automaticSP,4,0,3,2); /// col+1
        containerGP.add(pegSP,4,2,1,1); /// col+1
        containerGP.add(tieUpSP,5,2,1,1); /// col+1
        containerGP.add(tradelesSP,6,2,1,1); /// col+1
        containerGP.add(dummyPegSP,4,3,1,2); /// col+1
        containerGP.add(dummyTieUpSP,5,3,1,2); /// col+1
        containerGP.add(dummyTradleSP,6,3,1,2);/// col+1
        
        //containerGP.setStyle("-fx-padding: 10; -fx-background-color: #5F5E5E;");Id("popup");
        containerGP.setAlignment(Pos.CENTER);
        container.setContent(containerGP);
        root.setCenter(container);
        isWorkingMode=true;
        menuHighlight(null);
        weaveStage.getIcons().add(new Image("/media/icon.png"));
        weaveStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWWEAVEEDITOR")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        weaveStage.setX(-5);
        weaveStage.setY(0);
        weaveStage.setIconified(false);
        weaveStage.setResizable(false);
        weaveStage.setScene(scene);
        weaveStage.show();   
        weaveStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                final Stage dialogStage = new Stage();
                dialogStage.initStyle(StageStyle.UTILITY);
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.setResizable(false);
                dialogStage.setIconified(false);
                dialogStage.setFullScreen(false);
                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("ALERT"));
                BorderPane root = new BorderPane();
                Scene scene = new Scene(root, 300, 100, Color.WHITE);
                scene.getStylesheets().add(WeaveView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        weaveStage.close();
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
        initObjWeave(false);
        populateNplot();
        plotColorPalette();
        plotWarpColor();
        plotWeftColor();
        weftColorSP.setVvalue(weftColorSP.getVmax());
        vSliderSP.setVvalue(vSliderSP.getVmax());
        designSP.setVvalue(designSP.getVmax());
        shaftSP.setVvalue(shaftSP.getVmax());
        pegSP.setVvalue(pegSP.getVmax());
        tieUpSP.setVvalue(tieUpSP.getVmax());
        tradelesSP.setVvalue(tradelesSP.getVmax());
        if(objConfiguration.getStrRecentWeave()!=null && objConfiguration.strWindowFlowContext.equalsIgnoreCase("Dashboard")){
            try {
                objWeave = new Weave();
                objWeave.setObjConfiguration(objConfiguration);
                objWeave.setIntEPI(objConfiguration.getIntEPI());
                objWeave.setIntPPI(objConfiguration.getIntPPI());
                objWeave.setStrWeaveID(objConfiguration.getStrRecentWeave());
                loadWeave();
            } catch (Exception ex) {
                new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
		// shortcuts
        final KeyCodeCombination kchA = new KeyCodeCombination(KeyCode.H, KeyCombination.ALT_DOWN); // Home Menu
        final KeyCodeCombination kcfA = new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN); // File Menu
        final KeyCodeCombination kceA = new KeyCodeCombination(KeyCode.E, KeyCombination.ALT_DOWN); // Edit Menu
        final KeyCodeCombination kcoA = new KeyCodeCombination(KeyCode.O, KeyCombination.ALT_DOWN); // Operation Menu
        final KeyCodeCombination kcvA = new KeyCodeCombination(KeyCode.V, KeyCombination.ALT_DOWN); // View Menu
        final KeyCodeCombination kcsA = new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN); // Support Menu
        final KeyCodeCombination kcnC = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN); // Create Weave
        final KeyCodeCombination kcoC = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN); // Open Weave
        final KeyCodeCombination kclS = new KeyCodeCombination(KeyCode.L, KeyCombination.SHIFT_DOWN); // Load Recent Weave
        final KeyCodeCombination kcsC = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN); // Save Weave
        final KeyCodeCombination kcsCS = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN); // Save As Weave
        final KeyCodeCombination kciC = new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN); // Import Weave
        final KeyCodeCombination kceC = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN); // Export Weave
        final KeyCodeCombination kcpC = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN); // Print Weave
        final KeyCodeCombination kcfS = new KeyCodeCombination(KeyCode.F, KeyCombination.SHIFT_DOWN); // Fabric Creator
        final KeyCodeCombination kczCS = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN); // Toolkit
        final KeyCodeCombination kcinsertC = new KeyCodeCombination(KeyCode.INSERT, KeyCombination.CONTROL_DOWN); // Insert Warp
        final KeyCodeCombination kcdeleteC = new KeyCodeCombination(KeyCode.DELETE, KeyCombination.CONTROL_DOWN); // Delete Warp
        final KeyCodeCombination kcinsertS = new KeyCodeCombination(KeyCode.INSERT, KeyCombination.SHIFT_DOWN); // Insert Weft
        final KeyCodeCombination kcdeleteS = new KeyCodeCombination(KeyCode.DELETE, KeyCombination.SHIFT_DOWN); // Delete Weft
        final KeyCodeCombination kcsS = new KeyCodeCombination(KeyCode.S, KeyCombination.SHIFT_DOWN); // Select
        final KeyCodeCombination kccC = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN); // copy
        final KeyCodeCombination kcvC = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN); // paste
        final KeyCodeCombination kcvAC = new KeyCodeCombination(KeyCode.V, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN); // vertical mirror
        final KeyCodeCombination kchAC = new KeyCodeCombination(KeyCode.H, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN); // horizontal mirror
        final KeyCodeCombination kccA = new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN); // clear
        final KeyCodeCombination kcyS = new KeyCodeCombination(KeyCode.Y, KeyCombination.SHIFT_DOWN); // properties
        final KeyCodeCombination kcrightC = new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.CONTROL_DOWN); // move right
        final KeyCodeCombination kcleftC = new KeyCodeCombination(KeyCode.LEFT, KeyCombination.CONTROL_DOWN); // move left
        final KeyCodeCombination kcupC = new KeyCodeCombination(KeyCode.UP, KeyCombination.CONTROL_DOWN); // move up
        final KeyCodeCombination kcdownC = new KeyCodeCombination(KeyCode.DOWN, KeyCombination.CONTROL_DOWN); // move down
        final KeyCodeCombination kcrightS = new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.SHIFT_DOWN); // move 8 right
        final KeyCodeCombination kcleftS = new KeyCodeCombination(KeyCode.LEFT, KeyCombination.SHIFT_DOWN); // move 8 left
        final KeyCodeCombination kcupS = new KeyCodeCombination(KeyCode.UP, KeyCombination.SHIFT_DOWN); // move 8 up
        final KeyCodeCombination kcdownS = new KeyCodeCombination(KeyCode.DOWN, KeyCombination.SHIFT_DOWN); // move 8 down
        final KeyCodeCombination kcrightAC = new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN); // tilt right
        final KeyCodeCombination kcleftAC = new KeyCodeCombination(KeyCode.LEFT, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN); // tilt left
        final KeyCodeCombination kcupAC = new KeyCodeCombination(KeyCode.UP, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN); // tilt up
        final KeyCodeCombination kcdownAC = new KeyCodeCombination(KeyCode.DOWN, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN); // tilt down
        final KeyCodeCombination kccCS = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN); // clockwise rotation
        final KeyCodeCombination kccAC = new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN); // anti-clockwise rotation
        final KeyCodeCombination kciAC = new KeyCodeCombination(KeyCode.I, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN); // inversion
        final KeyCodeCombination kctAC = new KeyCodeCombination(KeyCode.T, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN); // tilled view
        final KeyCodeCombination kcenterCS = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN); // composite view
        final KeyCodeCombination kcplusC = new KeyCodeCombination(KeyCode.EQUALS, KeyCombination.CONTROL_DOWN); // Zoom In
        final KeyCodeCombination kcenterA = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.ALT_DOWN); // Normal Zoom
        final KeyCodeCombination kcminusC = new KeyCodeCombination(KeyCode.MINUS, KeyCombination.CONTROL_DOWN); // Zoom Out
        final KeyCodeCombination kczAC = new KeyCodeCombination(KeyCode.Z, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN); // Colorways
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
                else if(kcoA.match(t)){
                    operationMenuAction();
                }
                else if(kcvA.match(t)){
                    viewMenuAction();
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
                else if(kciC.match(t)){
                    importMenuAction();
                }
                else if(kceC.match(t)){
                    exportMenuAction();
                }
                else if(kcpC.match(t)){
                    printMenuAction();
                }
                else if(kcfS.match(t)){
                    fabricCreatorAction();
                }
                else if(kczCS.match(t)){
                    populateToolKit();
                }
                else if(kcinsertC.match(t)){
                    insertWarpAction();
                }
                else if(kcdeleteC.match(t)){
                    deleteWarpAction();
                }
                else if(kcinsertS.match(t)){
                    insertWeftAction();
                }
                else if(kcdeleteS.match(t)){
                    deleteWeftAction();
                }
                else if(kcsS.match(t)){
                    selectAction();
                }
                else if(kccC.match(t)){
                    copyAction();
                }
                else if(kcvC.match(t)){
                    pasteAction();
                }
                else if(kcvAC.match(t)){
                    mirrorVerticalAction();
                }
                else if(kchAC.match(t)){
                    mirrorHorizontalAction();
                }
                else if(kccA.match(t)){
                    clearAction();
                }
                else if(kcyS.match(t)){
                    populateProperties();
                }
                else if(kcrightC.match(t)){
                    moveRightAction();
                }
                else if(kcleftC.match(t)){
                    moveLeftAction();
                }
                else if(kcupC.match(t)){
                    moveUpAction();
                }
                else if(kcdownC.match(t)){
                    moveDownAction();
                }
                else if(kcrightS.match(t)){
                    moveRight8Action();
                }
                else if(kcleftS.match(t)){
                    moveLeft8Action();
                }
                else if(kcupS.match(t)){
                    moveUp8Action();
                }
                else if(kcdownS.match(t)){
                    moveDown8Action();
                }
                else if(kcrightAC.match(t)){
                    tiltRightAction();
                }
                else if(kcleftAC.match(t)){
                    tiltLeftAction();
                }
                else if(kcupAC.match(t)){
                    tiltUpAction();
                }
                else if(kcdownAC.match(t)){
                    tiltDownAction();
                }
                else if(kccCS.match(t)){
                    rotateClockwiseAction();
                }
                else if(kccAC.match(t)){
                    rotateAntiClockwiseAction();
                }
                else if(kciAC.match(t)){
                    inversionAction();
                }
                else if(kctAC.match(t)){
                    tilledViewAction();
                }
                else if(kcenterCS.match(t)){
                    compositeViewAction();
                }
                else if(kcplusC.match(t)){
                    zoomInAction();
                }
                else if(kcenterA.match(t)){
                    ZoomNormalAction();
                }
                else if(kcminusC.match(t)){
                    zoomOutAction();
                }
                else if(kczAC.match(t)){
                    colourwaysAction();
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
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("ALERT"));
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 300, 100, Color.WHITE);
        scene.getStylesheets().add(WeaveView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                weaveStage.close();
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
    
    public void operationMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONOPERATION"));
        menuHighlight("OPERATION");
    }
    
    public void viewMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONVIEW"));
        menuHighlight("VIEW");
    }
    
    public void createMenuAction(){
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONNEWFILE"));
            newAction();
        }
    }
    
    public void openMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONOPENFILE"));
        openAction();
    }
    
    public void loadRecentMenuAction(){
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONLOADFILE"));
            loadAction();
        }
    }
    
    public void saveMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEFILE"));
        isNew=false;
        saveUpdateAction();//saveAction();
        System.gc();
    }
    
    public void saveAsMenuAction(){
        lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEASFILE"));
        objWeave.setStrWeaveAccess(objConfiguration.getObjUser().getUserAccess("WEAVE_LIBRARY"));
        isNew=true;
        saveUpdateAction();//saveAsAction();
        System.gc();
    }
    
    public void importMenuAction(){
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONIMPORTFILE"));
            importAction();
        }
    }
    
    public void exportMenuAction(){
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONEXPORTFILE"));
            exportAction();  
        }
    }
    
    public void printMenuAction(){
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONPRINTFILE"));
            printAction();
        }
    }
    
    public void fabricCreatorAction(){
        new MessageView(objConfiguration);
        if(objConfiguration.getServicePasswordValid()){
            objConfiguration.setServicePasswordValid(false);
            lblStatus.setText(objDictionaryAction.getWord("ACTIONUSEINFABRIC"));
            openFabricEditorAction();
        }
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
        System.gc();
        weaveStage.close();
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
 * @link        WeaveView
 */
    public void menuHighlight(String strMenu){
        /*fileMenu.getStyleClass().add("menudimmed");
        editMenu.getStyleClass().add("menudimmed");
        transformMenu.getStyleClass().add("menudimmed");
        viewMenu.getStyleClass().add("menudimmed");
        fileMenuLabel.getStyleClass().add("menulbldimmed");
        editMenuLabel.getStyleClass().add("menulbldimmed");
        transformMenuLabel.getStyleClass().add("menulbldimmed");
        viewMenuLabel.getStyleClass().add("menulbldimmed");
          */  
        if(strMenu == "VIEW"){
            fileMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            editMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            transformMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            viewMenu.setStyle("-fx-background-color: linear-gradient(#FFD765,#FFFC92);");
            fileMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            editMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            transformMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            viewMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            toolBar.getItems().clear();
            populateViewToolbar();
            if(!isWorkingMode){
                compositeViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/composite_view.png"));
                compositeViewVB.setDisable(true);
                compositeViewVB.setCursor(Cursor.WAIT);
                gridViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/grid_view.png"));
                gridViewVB.setDisable(true);
                gridViewVB.setCursor(Cursor.WAIT);
                graphViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/graph_view.png"));
                graphViewVB.setDisable(true);
                graphViewVB.setCursor(Cursor.WAIT);
                frontSideViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/front_side.png"));
                frontSideViewVB.setDisable(true);
                frontSideViewVB.setCursor(Cursor.WAIT);
                switchSideViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/switch_side.png"));
                switchSideViewVB.setDisable(true);
                switchSideViewVB.setCursor(Cursor.WAIT);
                simulationViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/simulation.png"));
                simulationViewVB.setDisable(true);
                simulationViewVB.setCursor(Cursor.WAIT);
                tilledViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/tiled_view.png"));
                tilledViewVB.setDisable(true);
                tilledViewVB.setCursor(Cursor.WAIT);
                colourwaysVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/colourways.png"));
                colourwaysVB.setDisable(true);
                colourwaysVB.setCursor(Cursor.WAIT);
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
                compositeViewVB.setCursor(Cursor.HAND);
                gridViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/grid_view.png"));
                gridViewVB.setDisable(false);
                gridViewVB.setCursor(Cursor.HAND);
                graphViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/graph_view.png"));
                graphViewVB.setDisable(false);
                graphViewVB.setCursor(Cursor.HAND);
                frontSideViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/front_side.png"));
                frontSideViewVB.setDisable(false);
                frontSideViewVB.setCursor(Cursor.HAND);
                switchSideViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/switch_side.png"));
                switchSideViewVB.setDisable(false);
                switchSideViewVB.setCursor(Cursor.HAND);
                simulationViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/simulation.png"));
                simulationViewVB.setDisable(false);
                simulationViewVB.setCursor(Cursor.HAND);
                tilledViewVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/tiled_view.png"));
                tilledViewVB.setDisable(false);
                tilledViewVB.setCursor(Cursor.HAND);
                colourwaysVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/colourways.png"));
                colourwaysVB.setDisable(false);
                colourwaysVB.setCursor(Cursor.HAND);
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
        } else if(strMenu == "OPERATION"){
            fileMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            editMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            transformMenu.setStyle("-fx-background-color: linear-gradient(#FFD765,#FFFC92);");
            viewMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            fileMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            editMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            transformMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            viewMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            toolBar.getItems().clear();
            populateOperationToolbar();
            if(!isWorkingMode){
                rotateClockwiseEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/rotate_90.png"));
                rotateClockwiseEditVB.setDisable(true);
                rotateClockwiseEditVB.setCursor(Cursor.WAIT);
                rotateAnticlockwiseEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/rotate_anti_90.png"));
                rotateAnticlockwiseEditVB.setDisable(true);
                rotateAnticlockwiseEditVB.setCursor(Cursor.WAIT);
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
                inversionEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/switch_side.png"));
                inversionEditVB.setDisable(true);
                inversionEditVB.setCursor(Cursor.WAIT);
            }else{
                rotateClockwiseEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/rotate_90.png"));
                rotateClockwiseEditVB.setDisable(false);
                rotateClockwiseEditVB.setCursor(Cursor.HAND);
                rotateAnticlockwiseEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/rotate_anti_90.png"));
                rotateAnticlockwiseEditVB.setDisable(false);
                rotateAnticlockwiseEditVB.setCursor(Cursor.HAND);
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
                inversionEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/switch_side.png"));
                inversionEditVB.setDisable(false);
                inversionEditVB.setCursor(Cursor.HAND);
            }
        } else if(strMenu == "EDIT"){
            fileMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            editMenu.setStyle("-fx-background-color: linear-gradient(#FFD765,#FFFC92);");
            transformMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            viewMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            fileMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            editMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            transformMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            viewMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            toolBar.getItems().clear();
            populateEditToolbar();
            if(!isWorkingMode){
                undoEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/undo.png"));
                undoEditVB.setDisable(true);
                undoEditVB.setCursor(Cursor.WAIT);
                redoEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/redo.png"));
                redoEditVB.setDisable(true);
                redoEditVB.setCursor(Cursor.WAIT);
                propertiesEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/settings.png"));
                propertiesEditVB.setDisable(true);
                propertiesEditVB.setCursor(Cursor.WAIT);
                yarnEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/yarn.png"));
                yarnEditVB.setDisable(true);
                yarnEditVB.setCursor(Cursor.WAIT);
                insertWarpEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/insert_warp.png"));
                insertWarpEditVB.setDisable(true);
                insertWarpEditVB.setCursor(Cursor.WAIT);
                deleteWarpEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/delete_warp.png"));
                deleteWarpEditVB.setDisable(true);
                deleteWarpEditVB.setCursor(Cursor.WAIT);
                insertWeftEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/insert_weft.png"));
                insertWeftEditVB.setDisable(true);
                insertWeftEditVB.setCursor(Cursor.WAIT);
                deleteWeftEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/delete_weft.png"));
                deleteWeftEditVB.setDisable(true);
                deleteWeftEditVB.setCursor(Cursor.WAIT);
                selectEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/select.png"));
                selectEditVB.setDisable(true);
                selectEditVB.setCursor(Cursor.WAIT);
                copyEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/copy.png"));
                copyEditVB.setDisable(true);
                copyEditVB.setCursor(Cursor.WAIT);
                pasteEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/paste.png"));
                pasteEditVB.setDisable(true);
                pasteEditVB.setCursor(Cursor.WAIT);
                mirrorVerticalVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/vertical_mirror.png"));
                mirrorVerticalVB.setDisable(true);
                mirrorVerticalVB.setCursor(Cursor.WAIT);
                mirrorHorizontalVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/horizontal_mirror.png"));
                mirrorHorizontalVB.setDisable(true);
                mirrorHorizontalVB.setCursor(Cursor.WAIT);
                clearEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/clear.png"));
                clearEditVB.setDisable(true);
                clearEditVB.setCursor(Cursor.WAIT);
            }else{
                undoEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/undo.png"));
                undoEditVB.setDisable(false);
                undoEditVB.setCursor(Cursor.HAND);
                redoEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/redo.png"));
                redoEditVB.setDisable(false);
                redoEditVB.setCursor(Cursor.HAND);
                propertiesEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/settings.png"));
                propertiesEditVB.setDisable(false);
                propertiesEditVB.setCursor(Cursor.HAND);
                yarnEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/yarn.png"));
                yarnEditVB.setDisable(false);
                yarnEditVB.setCursor(Cursor.HAND);
                insertWarpEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/insert_warp.png"));
                insertWarpEditVB.setDisable(false);
                insertWarpEditVB.setCursor(Cursor.HAND);
                deleteWarpEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/delete_warp.png"));
                deleteWarpEditVB.setDisable(false);
                deleteWarpEditVB.setCursor(Cursor.HAND);
                insertWeftEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/insert_weft.png"));
                insertWeftEditVB.setDisable(false);
                insertWeftEditVB.setCursor(Cursor.HAND);
                deleteWeftEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/delete_weft.png"));
                deleteWeftEditVB.setDisable(false);
                deleteWeftEditVB.setCursor(Cursor.HAND);
                selectEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/select.png"));
                selectEditVB.setDisable(false);
                selectEditVB.setCursor(Cursor.HAND);
                copyEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/copy.png"));
                copyEditVB.setDisable(false);
                copyEditVB.setCursor(Cursor.HAND);
                pasteEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/paste.png"));
                pasteEditVB.setDisable(false);
                pasteEditVB.setCursor(Cursor.HAND);
                mirrorVerticalVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/vertical_mirror.png"));
                mirrorVerticalVB.setDisable(false);
                mirrorVerticalVB.setCursor(Cursor.HAND);
                mirrorHorizontalVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/horizontal_mirror.png"));
                mirrorHorizontalVB.setDisable(false);
                mirrorHorizontalVB.setCursor(Cursor.HAND);
                clearEditVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/clear.png"));
                clearEditVB.setDisable(false);
                clearEditVB.setCursor(Cursor.HAND);
            }
        } else {
            fileMenu.setStyle("-fx-background-color: linear-gradient(#FFD765,#FFFC92);");
            editMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            transformMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            viewMenu.setStyle("-fx-background-color: linear-gradient(#000000,#5C594A);");
            fileMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            editMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            transformMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            viewMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            toolBar.getItems().clear();
            populateFileToolbar();
            if(!isWorkingMode){
                saveFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/save.png"));
                saveFileVB.setDisable(true);
                saveFileVB.setCursor(Cursor.WAIT);
                saveAsFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/save_as.png"));
                saveAsFileVB.setDisable(true);
                saveAsFileVB.setCursor(Cursor.WAIT);
                exportFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/export.png"));
                exportFileVB.setDisable(true);
                exportFileVB.setCursor(Cursor.WAIT);
                saveXMLFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/export_html_as.png"));
                saveXMLFileVB.setDisable(true);
                saveXMLFileVB.setCursor(Cursor.WAIT);
                printFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/print.png"));
                printFileVB.setDisable(true);
                printFileVB.setCursor(Cursor.WAIT);
                fabricFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/fabric_editor.png"));
                fabricFileVB.setDisable(true);
                fabricFileVB.setCursor(Cursor.WAIT);
                toolkitVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/technical_info.png"));
                toolkitVB.setDisable(true);
                toolkitVB.setCursor(Cursor.WAIT);
            }else{
                saveFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/save.png"));
                saveFileVB.setDisable(false);
                saveFileVB.setCursor(Cursor.HAND);
                saveAsFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/save_as.png"));
                saveAsFileVB.setDisable(false);
                saveAsFileVB.setCursor(Cursor.HAND);
                exportFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/export.png"));
                exportFileVB.setDisable(false);
                exportFileVB.setCursor(Cursor.HAND);
                saveXMLFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/export_html_as.png"));
                saveXMLFileVB.setDisable(false);
                saveXMLFileVB.setCursor(Cursor.HAND);
                printFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/print.png"));
                printFileVB.setDisable(false);
                printFileVB.setCursor(Cursor.HAND);
                fabricFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/fabric_editor.png"));
                fabricFileVB.setDisable(false);
                fabricFileVB.setCursor(Cursor.HAND); 
                toolkitVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/technical_info.png"));
                toolkitVB.setDisable(false);
                toolkitVB.setCursor(Cursor.HAND);         
                if(isNew){
                    //saveFileVB.setVisible(false);
                    saveFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/save.png"));
                    saveFileVB.setDisable(true);
                    saveFileVB.setCursor(Cursor.WAIT);
                }
            }
        }
        System.gc();
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
    * @link        WeaveView
    */
    private void populateFileToolbar(){
        // New file item
        newFileVB = new VBox();
        Label newFileLbl= new Label(objDictionaryAction.getWord("NEWFILE"));
        newFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNEWFILE")));
        newFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/new.png"), newFileLbl);
        newFileVB.setPrefWidth(80);
        newFileVB.setCursor(Cursor.HAND);
        newFileVB.getStyleClass().addAll("VBox");    
        // Open file item
        openFileVB = new VBox(); 
        Label openFileLbl= new Label(objDictionaryAction.getWord("OPENFILE"));
        openFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOPENFILE")));
        openFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/open.png"), openFileLbl);
        openFileVB.setPrefWidth(80);
        openFileVB.setCursor(Cursor.HAND);
        openFileVB.getStyleClass().addAll("VBox");
        // load recent file item
        loadFileVB = new VBox(); 
        Label loadFileLbl= new Label(objDictionaryAction.getWord("LOADFILE"));
        loadFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPLOADFILE")));
        loadFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/open_recent.png"), loadFileLbl);
        loadFileVB.setPrefWidth(80);
        loadFileVB.setCursor(Cursor.HAND);
        loadFileVB.getStyleClass().addAll("VBox");
        // import file item
        importFileVB = new VBox();
        Label importFileLbl= new Label(objDictionaryAction.getWord("IMPORTFILE"));
        importFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPIMPORTFILE")));
        importFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/import.png"), importFileLbl);
        importFileVB.setPrefWidth(80);
        importFileVB.setCursor(Cursor.HAND);
        importFileVB.getStyleClass().addAll("VBox");
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
        // export file item
        exportFileVB = new VBox(); 
        Label exportFileLbl= new Label(objDictionaryAction.getWord("EXPORTFILE"));
        exportFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEXPORTFILE")));
        exportFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/export.png"), exportFileLbl);
        exportFileVB.setPrefWidth(80);
        exportFileVB.setCursor(Cursor.HAND);
        exportFileVB.getStyleClass().addAll("VBox");
        // Save As file item
        saveXMLFileVB = new VBox(); 
        Label saveXMLFileLbl= new Label(objDictionaryAction.getWord("SAVEXMLFILE"));
        saveXMLFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVEXMLFILE")));
        saveXMLFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/export_html_as.png"), saveXMLFileLbl);
        saveXMLFileVB.setPrefWidth(80);
        saveXMLFileVB.getStyleClass().addAll("VBox");
        // print File menu
        printFileVB = new VBox(); 
        Label printFileLbl= new Label(objDictionaryAction.getWord("PRINTFILE"));
        printFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPRINTFILE")));
        printFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/print.png"), printFileLbl);
        printFileVB.setPrefWidth(80);
        printFileVB.setCursor(Cursor.HAND);
        printFileVB.getStyleClass().addAll("VBox");        
        // fabric editor File menu
        fabricFileVB = new VBox(); 
        Label fabricFileLbl= new Label(objDictionaryAction.getWord("USEINFABRIC"));
        fabricFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPAPUSEINFABRIC")));
        fabricFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/fabric_editor.png"), fabricFileLbl);
        fabricFileVB.setPrefWidth(80);
        fabricFileVB.setCursor(Cursor.HAND); 
        fabricFileVB.getStyleClass().addAll("VBox"); 
        // fabric editor File menu
        toolkitVB = new VBox(); 
        Label toolkitLbl= new Label(objDictionaryAction.getWord("TOOLKIT"));
        toolkitLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTOOLKIT")));
        toolkitVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/technical_info.png"), toolkitLbl);
        toolkitVB.setPrefWidth(80);
        toolkitVB.setCursor(Cursor.HAND); 
        toolkitVB.getStyleClass().addAll("VBox"); 
        // quit File menu
        quitFileVB = new VBox(); 
        Label quitFileLbl= new Label(objDictionaryAction.getWord("QUITFILE"));
        quitFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPQUITFILE")));
        quitFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/close.png"), quitFileLbl);
        quitFileVB.setPrefWidth(80);
        quitFileVB.setCursor(Cursor.HAND);
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
        importFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
				importMenuAction();
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
        exportFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
				exportMenuAction();
             }
        });
        saveXMLFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new MessageView(objConfiguration);
                if(objConfiguration.getServicePasswordValid()){
                    objConfiguration.setServicePasswordValid(false);
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEXMLFILE"));
                    saveAsHtml();
                    //lblStatus.setText(objDictionaryAction.getWord("DATASAVED"));
                }
             }
        });
        printFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
				printMenuAction();
            }
        });  
        fabricFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
				fabricCreatorAction();
            }
        });
        toolkitVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    populateToolKit();
                } catch (Exception ex) {
                new Logging("SEVERE",WeaveView.class.getName(),"Undo",ex);
                ex.printStackTrace();
                }
            }
        });  
        quitFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                weaveStage.close();
            }
        });
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(newFileVB,openFileVB,loadFileVB,importFileVB,saveFileVB,saveAsFileVB,exportFileVB,saveXMLFileVB,printFileVB,fabricFileVB,toolkitVB);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow); 
    }
    
    
/**
 * populateEditToolbar
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
    private void populateEditToolbar(){
        // undo edit item
        undoEditVB = new VBox(); 
        Label undoEditLbl= new Label(objDictionaryAction.getWord("UNDO"));
        undoEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPUNDO")));
        undoEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/undo.png"), undoEditLbl);
        undoEditVB.setPrefWidth(80);
        undoEditVB.getStyleClass().addAll("VBox");   
        // redo edit item 
        redoEditVB = new VBox(); 
        Label redoEditLbl= new Label(objDictionaryAction.getWord("REDO"));
        redoEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPREDO")));
        redoEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/redo.png"), redoEditLbl);
        redoEditVB.setPrefWidth(80);
        redoEditVB.getStyleClass().addAll("VBox"); 
        // settings user  warp 
        propertiesEditVB = new VBox(); 
        Label propertiesEditLbl= new Label(objDictionaryAction.getWord("PROPERTIES"));
        propertiesEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPROPERTIES")));
        propertiesEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/settings.png"), propertiesEditLbl);
        propertiesEditVB.setPrefWidth(80);
        propertiesEditVB.getStyleClass().addAll("VBox"); 
        // yarn Calculation item
        yarnEditVB = new VBox(); 
        Label yarnEditLbl= new Label(objDictionaryAction.getWord("YARNEDIT"));
        yarnEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNEDIT")));
        yarnEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/yarn.png"), yarnEditLbl);
        yarnEditVB.setPrefWidth(80);
        yarnEditVB.getStyleClass().addAll("VBox");
        // Insert warp 
        insertWarpEditVB = new VBox(); 
        Label insertWarpEditLbl= new Label(objDictionaryAction.getWord("INSERTWARP"));
        insertWarpEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPINSERTWARP")));
        insertWarpEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/insert_warp.png"), insertWarpEditLbl);
        insertWarpEditVB.setPrefWidth(80);
        insertWarpEditVB.getStyleClass().addAll("VBox"); 
        // Delete Warp 
        deleteWarpEditVB = new VBox(); 
        Label deleteWarpEditLbl= new Label(objDictionaryAction.getWord("DELETEWARP"));
        deleteWarpEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDELETEWARP")));
        deleteWarpEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/delete_warp.png"), deleteWarpEditLbl);
        deleteWarpEditVB.setPrefWidth(80);
        deleteWarpEditVB.getStyleClass().addAll("VBox"); 
        // Insert weft 
        insertWeftEditVB = new VBox(); 
        Label insertWeftEditLbl= new Label(objDictionaryAction.getWord("INSERTWEFT"));
        insertWeftEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPINSERTWEFT")));
        insertWeftEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/insert_weft.png"), insertWeftEditLbl);
        insertWeftEditVB.setPrefWidth(80);
        insertWeftEditVB.getStyleClass().addAll("VBox");   
        // Delete Weft 
        deleteWeftEditVB = new VBox(); 
        Label deleteWeftEditLbl= new Label(objDictionaryAction.getWord("DELETEWEFT"));
        deleteWeftEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDELETEWEFT")));
        deleteWeftEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/delete_weft.png"), deleteWeftEditLbl);
        deleteWeftEditVB.setPrefWidth(80);
        deleteWeftEditVB.getStyleClass().addAll("VBox");
        // select edit item
        selectEditVB = new VBox(); 
        Label selectEditLbl= new Label(objDictionaryAction.getWord("SELECT"));
        selectEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSELECT")));
        selectEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/select.png"), selectEditLbl);
        selectEditVB.setPrefWidth(80);
        selectEditVB.getStyleClass().addAll("VBox");
        // Copy item
        copyEditVB = new VBox(); 
        Label copyEditLbl= new Label(objDictionaryAction.getWord("COPY"));
        copyEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOPY")));
        copyEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/copy.png"), copyEditLbl);
        copyEditVB.setPrefWidth(80);
        copyEditVB.getStyleClass().addAll("VBox");    
        // Paste item
        pasteEditVB = new VBox(); 
        Label pasteEditLbl= new Label(objDictionaryAction.getWord("PASTE"));
        pasteEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPASTE")));
        pasteEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/paste.png"), pasteEditLbl);
        pasteEditVB.setPrefWidth(80);
        pasteEditVB.getStyleClass().addAll("VBox"); 
        // mirror edit item
        mirrorVerticalVB = new VBox(); 
        Label mirrorVerticalEditLbl= new Label(objDictionaryAction.getWord("VERTICALMIRROR"));
        mirrorVerticalEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPVERTICALMIRROR")));
        mirrorVerticalVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/vertical_mirror.png"), mirrorVerticalEditLbl);
        mirrorVerticalVB.setPrefWidth(80);
        mirrorVerticalVB.getStyleClass().addAll("VBox");   
        // mirror edit item
        mirrorHorizontalVB = new VBox(); 
        Label mirrorHorizontalEditLbl= new Label(objDictionaryAction.getWord("HORIZENTALMIRROR"));
        mirrorHorizontalEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPHORIZENTALMIRROR")));
        mirrorHorizontalVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/horizontal_mirror.png"), mirrorHorizontalEditLbl);
        mirrorHorizontalVB.setPrefWidth(80);
        mirrorHorizontalVB.getStyleClass().addAll("VBox");   
        // Clear
        clearEditVB = new VBox(); 
        Label clearEditLbl= new Label(objDictionaryAction.getWord("CLEARWEAVE"));
        clearEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLEARWEAVE")));
        clearEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/clear.png"), clearEditLbl);
        clearEditVB.setPrefWidth(80);
        clearEditVB.getStyleClass().addAll("VBox"); 
        
        //Add the action to Buttons.
        undoEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    //undoAction();
                    populateNplot();
                    populateDplot();
                } catch (Exception ex) {
                new Logging("SEVERE",WeaveView.class.getName(),"Undo",ex);
                ex.printStackTrace();
                }
            }
        });       
        redoEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    //redoAction();
                    populateNplot();
                    populateDplot();
                } catch (Exception ex) {
                new Logging("SEVERE",WeaveView.class.getName(),"Redo ",ex);
                }
            }
        });
        propertiesEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONPROPERTIES"));
                    populateProperties();
                } catch (Exception ex) {
                new Logging("SEVERE",WeaveView.class.getName(),"Undo",ex);
                ex.printStackTrace();
                }
            }
        });
        yarnEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONYARNEDIT"));
                editYarn();
            }
        }); 
        selectEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectAction();
            }
        });
        copyEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                copyAction();
            }
        });
        pasteEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pasteAction();
            }
        }); 
        mirrorVerticalVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mirrorVerticalAction();
            }
        });  
        mirrorHorizontalVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mirrorHorizontalAction();
            }
        }); 
        insertWarpEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                insertWarpAction();
            }
        });  
        deleteWarpEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                deleteWarpAction();
            }
        });  
        insertWeftEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                insertWeftAction();
            }
        });  
        deleteWeftEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                deleteWeftAction();
            }
        });
        clearEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clearAction();
            }
        });

        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(yarnEditVB, propertiesEditVB,insertWarpEditVB,deleteWarpEditVB,insertWeftEditVB,deleteWeftEditVB,selectEditVB,copyEditVB,pasteEditVB,mirrorVerticalVB,mirrorHorizontalVB,clearEditVB);
        //flow.getChildren().addAll(undoEditVB,redoEditVB,propertiesEditVB);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
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
        // Pattern edit item
        rotateClockwiseEditVB = new VBox(); 
        Label rotateClockwiseEditLbl= new Label(objDictionaryAction.getWord("CLOCKROTATION"));
        rotateClockwiseEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLOCKROTATION")));
        rotateClockwiseEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/rotate_90.png"), rotateClockwiseEditLbl);
        rotateClockwiseEditVB.setPrefWidth(80);
        rotateClockwiseEditVB.getStyleClass().addAll("VBox");   
        // Pattern edit item
        rotateAnticlockwiseEditVB = new VBox(); 
        Label rotateAnticlockwiseEditLbl= new Label(objDictionaryAction.getWord("ANTICLOCKROTATION"));
        rotateAnticlockwiseEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPANTICLOCKROTATION")));
        rotateAnticlockwiseEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/rotate_anti_90.png"), rotateAnticlockwiseEditLbl);
        rotateAnticlockwiseEditVB.setPrefWidth(80);
        rotateAnticlockwiseEditVB.getStyleClass().addAll("VBox"); 
        // move Right
        moveRightEditVB = new VBox(); 
        Label moveRightEditLbl= new Label(objDictionaryAction.getWord("MOVERIGHT"));
        moveRightEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVERIGHT")));
        moveRightEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/move_right.png"), moveRightEditLbl);
        moveRightEditVB.setPrefWidth(80);
        moveRightEditVB.getStyleClass().addAll("VBox");    
        // move Left
        moveLeftEditVB = new VBox(); 
        Label moveLeftEditLbl= new Label(objDictionaryAction.getWord("MOVELEFT"));
        moveLeftEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVELEFT")));
        moveLeftEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/move_left.png"), moveLeftEditLbl);
        moveLeftEditVB.setPrefWidth(80);
        moveLeftEditVB.getStyleClass().addAll("VBox");    
        // move Up
        moveUpEditVB = new VBox(); 
        Label moveUpEditLbl= new Label(objDictionaryAction.getWord("MOVEUP"));
        moveUpEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVEUP")));
        moveUpEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/move_up.png"), moveUpEditLbl);
        moveUpEditVB.setPrefWidth(80);
        moveUpEditVB.getStyleClass().addAll("VBox");    
        // move Down
        moveDownEditVB = new VBox(); 
        Label moveDownEditLbl= new Label(objDictionaryAction.getWord("MOVEDOWN"));
        moveDownEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVEDOWN")));
        moveDownEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/move_down.png"), moveDownEditLbl);
        moveDownEditVB.setPrefWidth(80);
        moveDownEditVB.getStyleClass().addAll("VBox");
        // move Right 8
        moveRight8EditVB = new VBox(); 
        Label moveRight8EditLbl= new Label(objDictionaryAction.getWord("MOVERIGHT8"));
        moveRight8EditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVERIGHT8")));
        moveRight8EditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/move_right_by_8.png"), moveRight8EditLbl);
        moveRight8EditVB.setPrefWidth(80);
        moveRight8EditVB.getStyleClass().addAll("VBox");    
        // move Left 8
        moveLeft8EditVB = new VBox(); 
        Label moveLeft8EditLbl= new Label(objDictionaryAction.getWord("MOVELEFT8"));
        moveLeft8EditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVELEFT8")));
        moveLeft8EditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/move_left_by_8.png"), moveLeft8EditLbl);
        moveLeft8EditVB.setPrefWidth(80);
        moveLeft8EditVB.getStyleClass().addAll("VBox");    
        // move Up 8
        moveUp8EditVB = new VBox(); 
        Label moveUp8EditLbl= new Label(objDictionaryAction.getWord("MOVEUP8"));
        moveUp8EditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVEUP8")));
        moveUp8EditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/move_up_by_8.png"), moveUp8EditLbl);
        moveUp8EditVB.setPrefWidth(80);
        moveUp8EditVB.getStyleClass().addAll("VBox");    
        // move Down 8
        moveDown8EditVB = new VBox(); 
        Label moveDown8EditLbl= new Label(objDictionaryAction.getWord("MOVEDOWN8"));
        moveDown8EditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMOVEDOWN8")));
        moveDown8EditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/move_down_by_8.png"), moveDown8EditLbl);
        moveDown8EditVB.setPrefWidth(80);
        moveDown8EditVB.getStyleClass().addAll("VBox");
        // Tilt Right
        tiltRightEditVB = new VBox(); 
        Label tiltRightEditLbl= new Label(objDictionaryAction.getWord("TILTRIGHT"));
        tiltRightEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTILTRIGHT")));
        tiltRightEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/tilt_right.png"), tiltRightEditLbl);
        tiltRightEditVB.setPrefWidth(80);
        tiltRightEditVB.getStyleClass().addAll("VBox");    
        // Tilt Left
        tiltLeftEditVB = new VBox(); 
        Label tiltLeftEditLbl= new Label(objDictionaryAction.getWord("TILTLEFT"));
        tiltLeftEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTILTLEFT")));
        tiltLeftEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/tilt_left.png"), tiltLeftEditLbl);
        tiltLeftEditVB.setPrefWidth(80);
        tiltLeftEditVB.getStyleClass().addAll("VBox");    
        // Tilt Up
        tiltUpEditVB = new VBox(); 
        Label tiltUpEditLbl= new Label(objDictionaryAction.getWord("TILTUP"));
        tiltUpEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTILTUP")));
        tiltUpEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/tilt_up.png"), tiltUpEditLbl);
        tiltUpEditVB.setPrefWidth(80);
        tiltUpEditVB.getStyleClass().addAll("VBox");    
        // Tilt Down
        tiltDownEditVB = new VBox(); 
        Label tiltDownEditLbl= new Label(objDictionaryAction.getWord("TILTDOWN"));
        tiltDownEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTILTDOWN")));
        tiltDownEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/tilt_down.png"), tiltDownEditLbl);
        tiltDownEditVB.setPrefWidth(80);
        tiltDownEditVB.getStyleClass().addAll("VBox");
        // Inversion
        inversionEditVB = new VBox(); 
        Label inversionEditLbl= new Label(objDictionaryAction.getWord("INVERSION"));
        inversionEditLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPINVERSION")));
        inversionEditVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/switch_side.png"), inversionEditLbl);
        inversionEditVB.setPrefWidth(80);
        inversionEditVB.getStyleClass().addAll("VBox"); 
        //Add the action to Buttons.
        moveRightEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                moveRightAction();
            }
        });
        moveLeftEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                moveLeftAction();
            }
        });
        moveUpEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                moveUpAction();
            }
        });
        moveDownEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                moveDownAction();
            }
        });
        moveRight8EditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                moveRight8Action();
            }
        });
        moveLeft8EditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                moveLeft8Action();
            }
        });
        moveUp8EditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                moveUp8Action();
            }
        });
        moveDown8EditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                moveDown8Action();
            }
        });
        tiltRightEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tiltRightAction();
            }
        });
        tiltLeftEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tiltLeftAction();
            }
        });
        tiltUpEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tiltUpAction();
            }
        });
        tiltDownEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tiltDownAction();
            }
        });
        rotateClockwiseEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rotateClockwiseAction();
            }
        }); 
        rotateAnticlockwiseEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rotateAntiClockwiseAction();
            }
        }); 
        inversionEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                inversionAction();
            }
        });
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(moveRightEditVB,moveLeftEditVB,moveUpEditVB,moveDownEditVB,moveRight8EditVB,moveLeft8EditVB,moveUp8EditVB,moveDown8EditVB,tiltRightEditVB,tiltLeftEditVB,tiltUpEditVB,tiltDownEditVB,rotateClockwiseEditVB,rotateAnticlockwiseEditVB,inversionEditVB);
        //flow.getChildren().addAll(undoEditVB,redoEditVB);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
  }
    
    private void populateViewToolbar(){
        // Composite View item
        compositeViewVB = new VBox(); 
        Label compositeViewLbl= new Label(objDictionaryAction.getWord("COMPOSITEVIEW"));
        compositeViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOMPOSITEVIEW")));
        compositeViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/composite_view.png"), compositeViewLbl);
        compositeViewVB.setPrefWidth(80);
        compositeViewVB.getStyleClass().addAll("VBox");        
        // Grid View item
        gridViewVB = new VBox(); 
        Label gridViewLbl= new Label(objDictionaryAction.getWord("GRIDVIEW"));
        gridViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPGRIDVIEW")));
        gridViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/grid_view.png"), gridViewLbl);
        gridViewVB.setPrefWidth(80);
        gridViewVB.getStyleClass().addAll("VBox");
        // Graph View item
        graphViewVB = new VBox(); 
        Label graphViewLbl= new Label(objDictionaryAction.getWord("GRAPHVIEW"));
        graphViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPGRAPHVIEW")));
        graphViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/graph_view.png"), graphViewLbl);
        graphViewVB.setPrefWidth(80);
        graphViewVB.getStyleClass().addAll("VBox");
        // Visulization View item;
        frontSideViewVB = new VBox(); 
        Label frontSideViewLbl= new Label(objDictionaryAction.getWord("VISULIZATIONVIEW"));
        frontSideViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPVISULIZATIONVIEW")));
        frontSideViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/front_side.png"), frontSideViewLbl);
        frontSideViewVB.setPrefWidth(80);
        frontSideViewVB.getStyleClass().addAll("VBox");
        // Switch Side View item;
        switchSideViewVB = new VBox(); 
        Label switchSideViewLbl= new Label(objDictionaryAction.getWord("SWITCHSIDEVIEW"));
        switchSideViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSWITCHSIDEVIEW")));
        switchSideViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/switch_side.png"), switchSideViewLbl);
        switchSideViewVB.setPrefWidth(80);
        switchSideViewVB.getStyleClass().addAll("VBox");
        // Simulation View menu
        simulationViewVB = new VBox(); 
        Label simulationViewLbl= new Label(objDictionaryAction.getWord("SIMULATION"));
        simulationViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSIMULATION")));
        simulationViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/simulation.png"), simulationViewLbl);
        simulationViewVB.setPrefWidth(80);
        simulationViewVB.getStyleClass().addAll("VBox");        
        // Tiled View
        tilledViewVB = new VBox(); 
        Label tilledViewLbl= new Label(objDictionaryAction.getWord("TILLEDVIEW"));
        tilledViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTILLEDVIEW")));
        tilledViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/tiled_view.png"), tilledViewLbl);
        tilledViewVB.setPrefWidth(65);
        tilledViewVB.getStyleClass().addAll("VBox");    
        // colour ways
        colourwaysVB = new VBox(); 
        Label colourwaysLbl= new Label(objDictionaryAction.getWord("COLOURWAYS"));
        colourwaysLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOLOURWAYS")));
        colourwaysVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/colourways.png"), colourwaysLbl);
        colourwaysVB.setPrefWidth(65);
        colourwaysVB.getStyleClass().addAll("VBox");    
        // Normal View 
        normalViewVB = new VBox(); 
        Label normalViewLbl= new Label(objDictionaryAction.getWord("ZOOMNORMALVIEW"));
        normalViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPZOOMNORMALVIEW")));
        normalViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/zoom_normal.png"), normalViewLbl);
        normalViewVB.setPrefWidth(65);
        normalViewVB.getStyleClass().addAll("VBox");
        // Zoom In
        zoomInViewVB = new VBox(); 
        Label zoomInViewLbl= new Label(objDictionaryAction.getWord("ZOOMINVIEW"));
        zoomInViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPZOOMINVIEW")));
        zoomInViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/zoom_in.png"), zoomInViewLbl);
        zoomInViewVB.setPrefWidth(65);
        zoomInViewVB.getStyleClass().addAll("VBox");
        // Zoom Out
        zoomOutViewVB = new VBox(); 
        Label zoomOutViewLbl= new Label(objDictionaryAction.getWord("ZOOMOUTVIEW"));
        zoomOutViewLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPZOOMOUTVIEW")));
        zoomOutViewVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/zoom_out.png"), zoomOutViewLbl);
        zoomOutViewVB.setPrefWidth(65);
        zoomOutViewVB.getStyleClass().addAll("VBox");
        
        //Add the action to Buttons.
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
                frontViewAction();
            }
        });
        switchSideViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                switchViewAction();
            }
        });
        simulationViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONSIMULATION"));
                simulationViewAction();
            }
        }); 
        tilledViewVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tilledViewAction();
            }
        });
        colourwaysVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                colourwaysAction();
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
                ZoomNormalAction();
            }
        });
        
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(compositeViewVB,gridViewVB,frontSideViewVB,switchSideViewVB,simulationViewVB,tilledViewVB, colourwaysVB,zoomInViewVB,normalViewVB,zoomOutViewVB);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
     } 
    
    public void initWeaveValue(){
        try {
            if(!objWeave.getStrWeaveFile().equals("") && objWeave.getStrWeaveFile()!=null){
                objWeaveAction = new WeaveAction();
                objWeaveAction.extractWeaveContent(objWeave);
                //objWeave.setStrThreadPalettes(objWeave.getObjConfiguration().strThreadPalettes);
                objWeave.setDentMatrix(new byte[2][objWeave.getIntWarp()]);
                for(int i=0; i<2; i++)
                    for(int j=0; j<objWeave.getIntWarp(); j++)
                        objWeave.getDentMatrix()[i][j]=(byte)((i+j)%2);
                populateYarnPalette();
                isWorkingMode = true;
                plotDesign();
                plotShaft();
                plotPeg();
                plotTieUp();  
                plotTradeles();
                plotWarpColor();
                plotWeftColor();
                plotDenting();
                plotColorPalette();
            } else{
                lblStatus.setText(objDictionaryAction.getWord("INVALIDWEAVE"));
                isWorkingMode = false;
            }
            menuHighlight(null);
            System.gc();
        } catch (SQLException ex) {
            Logger.getLogger(WeaveView.class.getName()).log(Level.SEVERE, null, ex);
            
            new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            Logger.getLogger(WeaveView.class.getName()).log(Level.SEVERE, null, ex);
            
            new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    private void loadWeave(){
        try{
            objWeave.setBytIsLiftPlan((byte)0);    
            objWeaveAction = new WeaveAction();
            objWeaveAction.getWeave(objWeave);
            objWeaveAction = new WeaveAction();
            if(objWeaveAction.countWeaveUsage(objWeave.getStrWeaveID())>0 || new IDGenerator().getUserAcessValueData("ARTWORK_LIBRARY", objWeave.getStrWeaveAccess()).equalsIgnoreCase("Public")){
                isNew = true;
            }else{
                isNew = false;
            }
            System.gc();
            objConfiguration.setStrRecentWeave(objWeave.getStrWeaveID());
            initWeaveValue();
        } catch (Exception ex) {
            Logger.getLogger(WeaveView.class.getName()).log(Level.SEVERE, null, ex);
            
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
        }
    }
    
    public void newAction(){
        final Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 300, 200, Color.WHITE);
        scene.getStylesheets().add(WeaveView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());

        GridPane popup=new GridPane();
        popup.setId("popup");
        popup.setAlignment(Pos.CENTER);
        popup.setHgap(10);
        popup.setVgap(10);
        popup.setPadding(new Insets(25, 25, 25, 25));
        
        Label lblWarp = new Label(objDictionaryAction.getWord("WARP"));
        lblWarp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWARP")));
        lblWarp.setStyle("-fx-font-size: 14px;");
        popup.add(lblWarp, 0, 0);
        txtWarp = new TextField(){
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
        txtWarp.setPromptText(objDictionaryAction.getWord("TOOLTIPWARP"));
        txtWarp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWARP")));
        popup.add(txtWarp, 1, 0);
        
        Label lblWeft = new Label(objDictionaryAction.getWord("WEFT"));
        lblWeft.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWEFT")));
        lblWeft.setStyle("-fx-font-size: 14px;");
        popup.add(lblWeft, 0, 1);
        txtWeft = new TextField(){
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
        txtWeft.setPromptText(objDictionaryAction.getWord("TOOLTIPWEFT"));
        txtWeft.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWEFT")));
        popup.add(txtWeft, 1, 1);
        
        Label lblShaft = new Label(objDictionaryAction.getWord("SHAFT"));
        lblShaft.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSHAFT")));
        lblShaft.setStyle("-fx-font-size: 14px;");
        popup.add(lblShaft, 0, 2);
        txtShaft = new TextField(){
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
        txtShaft.setPromptText(objDictionaryAction.getWord("TOOLTIPSHAFT"));
        txtShaft.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSHAFT")));
        popup.add(txtShaft, 1, 2);
        
        Label lblTreadles = new Label(objDictionaryAction.getWord("TRADLE"));
        lblTreadles.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTRADLE")));
        lblTreadles.setStyle("-fx-font-size: 14px;");
        popup.add(lblTreadles, 0, 3);
        txtTreadles = new TextField(){
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
        txtTreadles.setPromptText(objDictionaryAction.getWord("TOOLTIPTRADLE"));
        txtTreadles.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTRADLE")));
        popup.add(txtTreadles, 1, 3);
        
        Button btnOK = new Button(objDictionaryAction.getWord("SUBMIT"));
        btnOK.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSUBMIT")));
        btnOK.setGraphic(new ImageView(objConfiguration.getStrColour()+"/skip.png"));
        btnOK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {  
                try {
                    objWeave = new Weave();
                    objWeaveAction = new WeaveAction();
                    objWeave.setObjConfiguration(objConfiguration);
                    objWeave.setIntEPI(objConfiguration.getIntEPI());
                    objWeave.setIntPPI(objConfiguration.getIntPPI());
                    //objWeave.setStrThreadPalettes(objConfiguration.strThreadPalettes);
                    objWeave.setStrWeaveID(new IDGenerator().getIDGenerator("WEAVE_LIBRARY", objConfiguration.getObjUser().getStrUserID()));
                    objWeave.setStrWeaveType("other");
                    if(txtWarp.getText().length()>0&&txtWeft.getText().length()>0&&txtShaft.getText().length()>0&&txtTreadles.getText().length()>0){
                        int intWeft=Integer.parseInt(txtWeft.getText());
                        int intWarp = Integer.parseInt(txtWarp.getText());
                        int intShaft =Integer.parseInt(txtShaft.getText());
                        int intTreadles =Integer.parseInt(txtTreadles.getText());
                        if(intWeft>0 && intWarp>0 && intShaft>0 && intTreadles>0){
                            clearImage(designImage);
                            objWeave.setIntWarp(intWarp);
                            objWeave.setIntWeft(intWeft);
                            objWeave.setIntWeaveShaft(intShaft);
                            objWeave.setIntWeaveTreadles(intTreadles);
                            updateActiveDesignGrid(intWarp, intWeft);
                            objWeave.setWarpYarn(new Yarn[intWarp]);
                            objWeave.setWeftYarn(new Yarn[intWeft]);
                            initObjWeave(true);
                            populateNplot();
                        }
                        dialogStage.close();
                    }
                    /*designGP.getChildren().clear();
                    shaftGP.getChildren().clear();
                    pegGP.getChildren().clear();
                    tieUpGP.getChildren().clear();
                    tradelesGP.getChildren().clear();
                    int intWeft=Integer.parseInt(txtWeft.getText());
                    int intWarp = Integer.parseInt(txtWarp.getText());
                    int intShaft =Integer.parseInt(txtShaft.getText());
                    int intTreadles =Integer.parseInt(txtTreadles.getText());
                    if(intWeft>30 || intWarp>30 || intShaft>30 || intTreadles>30){
                        lblStatus.setText(objDictionaryAction.getWord("MAXVALUE"));
                    }else if(intWeft>0 && intWarp>0 && intShaft>0 && intTreadles>0){
                        objWeave.setShaftMatrix(new byte[intShaft][intWarp]);
                        objWeave.setTreadlesMatrix(new byte[intWeft][intTreadles]);
                        objWeave.setTieupMatrix(new byte[intShaft][intTreadles]);
                        objWeave.setPegMatrix(new byte[intWeft][intShaft]);
                        objWeave.setDesignMatrix(new byte[intWeft][intWarp]);
                        objWeave.setDentMatrix(new byte[2][intWarp]);
                        objWeave.setWeftYarn(new Yarn[intWeft]);
                        objWeave.setWarpYarn(new Yarn[intWarp]);
                        
                        warpYarn = new Yarn[intWarp];
                        for(int i=0; i<intWarp; i++)

                        warpYarn[i] = objWeave.getObjConfiguration().getYarnPalette()[0];
                        objWeave.setWarpYarn(warpYarn);
                        weftYarn = new Yarn[intWeft];
                        for(int i=0; i<intWeft; i++)

                            weftYarn[i] = objWeave.getObjConfiguration().getYarnPalette()[26];
                        objWeave.setWeftYarn(weftYarn);
                        for(int i=0; i<2; i++)
                            for(int j=0; j<objWeave.getIntWarp(); j++)
                                objWeave.getDentMatrix()[i][j]=(byte)((i+j)%2);


                        populateColorPalette();
                        
                        objWeave.setIntWeaveShaft(intShaft);
                        objWeave.setIntWeaveTreadles(intTreadles);
                        objWeave.setIntWarp(intWarp);
                        objWeave.setIntWeft(intWeft);

                        //objWeave.setShaftRepeat(new String[intWeft]);
                        isNew = true;
                        isWorkingMode = true;
                        menuHighlight(null);
                        plotDesign();
                        plotShaft();
                        plotPeg();
                        plotTieUp();
                        plotTradeles();
                        plotWarpColor();
                        plotWeftColor();
                        plotDenting();
                        plotColorPalette();
                        dialogStage.close();
                    }else{
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                    }
                    */
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        popup.add(btnOK, 1, 4);
        root.setCenter(popup);
        dialogStage.setScene(scene);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WEAVINGDETAILS"));
        dialogStage.showAndWait();
    }
    
    public void openAction(){
        try {
            Weave tempWeave = new Weave();
            tempWeave.setObjConfiguration(objConfiguration);
            tempWeave.setIntEPI(objConfiguration.getIntEPI());
            tempWeave.setIntPPI(objConfiguration.getIntPPI());
            WeaveImportView objWeaveImportView= new WeaveImportView(tempWeave);
            if(tempWeave.getStrWeaveID()!=null){
                objWeave=tempWeave;
                loadWeave();
            }
        } catch (Exception ex) {
            Logger.getLogger(WeaveView.class.getName()).log(Level.SEVERE, null, ex);
            
            new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public void loadAction(){
        try {
            if(objConfiguration.getStrRecentWeave()!=null){
                objWeave = new Weave();
                objWeave.setObjConfiguration(objConfiguration);
                objWeave.setIntEPI(objConfiguration.getIntEPI());
                objWeave.setIntPPI(objConfiguration.getIntPPI());
                objWeave.setStrWeaveID(objConfiguration.getStrRecentWeave());
                loadWeave();
            }else{
                objWeave.setBytIsLiftPlan((byte)0);
                objWeave.setStrCondition("");
                objWeave.setStrOrderBy("Date");
                objWeave.setStrSearchBy("All");
                objWeave.setStrDirection("Descending");
                objWeaveAction = new WeaveAction();
                List lstWeave = (ArrayList)(objWeaveAction.lstImportWeave(objWeave)).get(0);
                if(lstWeave!=null){
                    objWeave = new Weave();
                    objWeave.setObjConfiguration(objConfiguration);
                    objWeave.setIntEPI(objConfiguration.getIntEPI());
                    objWeave.setIntPPI(objConfiguration.getIntPPI());
                    objWeave.setStrWeaveID(lstWeave.get(0).toString());
                    loadWeave();
                }
            }
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public void importAction(){
        try {
            FileChooser fileChooser = new FileChooser();             
            //Set extension filter
            FileChooser.ExtensionFilter extFilterWIF = new FileChooser.ExtensionFilter("WIF files (*.wif)", "*.wif");
            fileChooser.getExtensionFilters().add( extFilterWIF);
            //fileChooser.setInitialDirectory(new File(objFabric.getObjConfiguration().strRoot));
            fileChooser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("SELECTTO")+" "+objDictionaryAction.getWord("WEAVE"));
            //Show open file dialog
            File file=fileChooser.showOpenDialog(weaveStage);
            if(file!=null){
                InputStream in = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                String content="";
                while ((line = reader.readLine()) != null) {
                    content+="\n"+line;
                }
                reader.close();
                in.close();
                objWeaveAction = new WeaveAction();
                if(objWeaveAction.isValidWeaveContent(content)){
                    objWeave = new Weave();
                    objWeave.setObjConfiguration(objConfiguration);
                    objWeave.setIntEPI(objConfiguration.getIntEPI());
                    objWeave.setIntPPI(objConfiguration.getIntPPI());
                    objWeave.setStrWeaveID(new IDGenerator().getIDGenerator("WEAVE_LIBRARY", objConfiguration.getObjUser().getStrUserID()));
                    objWeave.setStrWeaveFile(content);
                    isNew = true;
                    initWeaveValue();
                } else{
                    lblStatus.setText(objDictionaryAction.getWord("INVALIDWEAVE"));
                }
            }else{
                lblStatus.setText(objDictionaryAction.getWord("NOITEM"));
            }            
        } catch (FileNotFoundException ex) {
            new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (IOException ex) {
            new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (SQLException ex) {
            new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } 
    }
    
    public void exportAction(){
        try{
            objWeaveAction=new WeaveAction();
            if(objWeaveAction.isValidWeave(objWeave)){
                char ch='S'; // S,L
                int Tcount=0;
                for(int i=0;i<objWeave.getIntWeaveTreadles();i++)
                    for(int j=0;j<objWeave.getIntWeft();j++)
                        if(objWeave.getTreadlesMatrix()[j][i]==1)
                           Tcount++;
                if(Tcount!=0) {       
                    try {
                        FileChooser artworkExport=new FileChooser();
                        artworkExport.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTPNG")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
                        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("WIF files (*.wif)", "*.WIF");
                        artworkExport.getExtensionFilters().add(extFilterPNG);
                        File file=artworkExport.showSaveDialog(weaveStage);
                        if(file==null)
                            return;
                        else
                            weaveStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+file.getAbsoluteFile().getName()+"]");   
                        if (!file.getName().endsWith("png")) 
                            file = new File(file.getPath() + objWeave.getStrWeaveID()+".wif");
                        objWeaveAction = new WeaveAction();
                        objWeaveAction.injectWeaveContent(objWeave);
                        FileWriter writer = new FileWriter(file);
                        writer.write(objWeave.getStrWeaveFile());
                        writer.close();

                        ArrayList<File> filesToZip=new ArrayList<>();
                        filesToZip.add(file);
                        String zipFilePath=file.getAbsolutePath()+".zip";
                        String passwordToZip = file.getName();
                        new EncryptZip(zipFilePath, filesToZip, passwordToZip);
                        file.delete();

                        lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+file.getCanonicalPath());
                    } catch (IOException ex) {
                        new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
                        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    } catch (Exception ex) {
                        new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
                        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    } 
                }else{
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            } else{
                lblStatus.setText(objDictionaryAction.getWord("INVALIDWEAVE"));
            }
        }
        catch(SQLException sqlEx){
            new Logging("SEVERE",WeaveView.class.getName(),sqlEx.toString(),sqlEx);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public void saveAction(){
        try {
            objWeave.setBytIsLiftPlan((byte)0);
            objWeaveAction = new WeaveAction();
            if(objWeaveAction.isValidWeave(objWeave)){
                objWeaveAction.singleWeaveContent(objWeave);
                objWeaveAction.injectWeaveContent(objWeave);
                objWeaveAction.getWeaveImage(objWeave);
                //objWeaveAction.getSingleWeaveImage(objWeave);
                objWeaveAction.resetWeave(objWeave);
                
                /*
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
                */
                
                
                lblStatus.setText(objWeave.getStrWeaveName()+":"+objDictionaryAction.getWord("DATASAVED"));
            } else{
                lblStatus.setText(objDictionaryAction.getWord("INVALIDWEAVE"));
            }
        } catch (SQLException ex) {
            new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public void saveAsAction(){
        try {
            objWeave.setBytIsLiftPlan((byte)0);
            objWeave.setStrWeaveType("wif");
            objWeaveAction = new WeaveAction();
            objWeave.setStrWeaveID(new IDGenerator().getIDGenerator("WEAVE_LIBRARY", objConfiguration.getObjUser().getStrUserID()));
            objWeaveAction = new WeaveAction();
            if(objWeaveAction.isValidWeave(objWeave)){
                objWeaveAction.singleWeaveContent(objWeave);
                objWeaveAction.injectWeaveContent(objWeave);
                objWeaveAction.getWeaveImage(objWeave);
                //objWeaveAction.getSingleWeaveImage(objWeave);
                objWeaveAction.setWeave(objWeave);
                lblStatus.setText(objWeave.getStrWeaveName()+":"+objDictionaryAction.getWord("DATASAVED"));
                isNew=false;
                saveFileVB.getChildren().set(0, new ImageView(objConfiguration.getStrColour()+"/save.png"));
                //saveFileVB.setVisible(false);
                saveFileVB.setDisable(false);
            } else{
                new MessageView("error", objDictionaryAction.getWord("INVALIDWEAVE"), objDictionaryAction.getWord("BLANKWARPWEFT"));
                lblStatus.setText(objDictionaryAction.getWord("INVALIDWEAVE"));
            }
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    // Added 7 Mar 2017 -----------------------------
    public void saveUpdateAction(){
        // if fabric access is Public, we need not show dialog for Save
        if(!isNew && objWeave.getStrWeaveAccess().equalsIgnoreCase("Public")){
            saveAction();
        } else{
            final Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 350, 300, Color.WHITE);
            scene.getStylesheets().add(WeaveView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());

            GridPane popup=new GridPane();
            popup.setId("popup");
            popup.setAlignment(Pos.CENTER);
            popup.setHgap(10);
            popup.setVgap(10);
            popup.setPadding(new Insets(25, 25, 25, 25));

            final TextField txtName = new TextField(objWeave.getStrWeaveID());
            final ComboBox cbType = new ComboBox();
            if(isNew){
                Label lblName = new Label(objDictionaryAction.getWord("NAME"));
                lblName.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNAME")));
                popup.add(lblName, 0, 0);
                txtName.setPromptText(objDictionaryAction.getWord("TOOLTIPNAME"));
                txtName.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNAME")));
                popup.add(txtName, 1, 0, 2, 1);
                Label lblType = new Label(objDictionaryAction.getWord("TYPE"));
                lblType.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTYPE")));
                popup.add(lblType, 0, 1);
                cbType.getItems().addAll("plain","twill","satin","basket","sateen","other");
                cbType.setValue("other");
                cbType.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTYPE")));
                popup.add(cbType, 1, 1, 2, 1);
            }

            final ToggleGroup weaveTG = new ToggleGroup();
            RadioButton weavePublicRB = new RadioButton(objDictionaryAction.getWord("PUBLIC"));
            weavePublicRB.setToggleGroup(weaveTG);
            weavePublicRB.setUserData("Public");
            popup.add(weavePublicRB, 0, 2);
            RadioButton weavePrivateRB = new RadioButton(objDictionaryAction.getWord("PRIVATE"));
            weavePrivateRB.setToggleGroup(weaveTG);
            weavePrivateRB.setUserData("Private");
            popup.add(weavePrivateRB, 1, 2);
            RadioButton weaveProtectedRB = new RadioButton(objDictionaryAction.getWord("PROTECTED"));
            weaveProtectedRB.setToggleGroup(weaveTG);
            weaveProtectedRB.setUserData("Protected");
            popup.add(weaveProtectedRB, 2, 2);
            if(objConfiguration.getObjUser().getUserAccess("WEAVE_LIBRARY").equalsIgnoreCase("Public"))
                weaveTG.selectToggle(weavePublicRB);
            else if(objConfiguration.getObjUser().getUserAccess("WEAVE_LIBRARY").equalsIgnoreCase("Protected"))
                weaveTG.selectToggle(weaveProtectedRB);
            else
                weaveTG.selectToggle(weavePrivateRB);

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
            
            Button btnOK = new Button(objDictionaryAction.getWord("SAVE"));
            btnOK.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVE")));
            btnOK.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
            btnOK.setDefaultButton(true);
            btnOK.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    objWeave.setStrWeaveAccess(weaveTG.getSelectedToggle().getUserData().toString());
                    if(objConfiguration.getBlnAuthenticateService()){
                        if(passPF.getText().length()==0){
                            lblAlert.setText(objDictionaryAction.getWord("NOSERVICEPASSWORD"));
                        } else{
                            if(Security.SecurePassword(passPF.getText(), objConfiguration.getObjUser().getStrUsername()).equals(objConfiguration.getObjUser().getStrAppPassword())){   
                                if(isNew){
                                    if(txtName.getText().trim().length()==0)
                                        objWeave.setStrWeaveName("my weave");
                                    else
                                        objWeave.setStrWeaveName(txtName.getText());
                                    objWeave.setStrWeaveCategory(cbType.getValue().toString());
                                    saveAsAction();
                                } else {
                                    saveAction();
                                }
                                dialogStage.close();
                            }
                            else{
                                lblAlert.setText(objDictionaryAction.getWord("INVALIDSERVICEPASSWORD"));
                            }
                        }
                    } else{   // service password is disabled
                        if(isNew){
                            if(txtName.getText().trim().length()==0)
                                objWeave.setStrWeaveName("my weave");
                            else
                                objWeave.setStrWeaveName(txtName.getText());
                            objWeave.setStrWeaveCategory(cbType.getValue().toString());
                            saveAsAction();
                        } else {
                            saveAction();
                        }
                        dialogStage.close();
                    }
                }
            });
            popup.add(btnOK, 1, 4);
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
            popup.add(btnCancel, 0, 4);
            root.setCenter(popup);
            dialogStage.setScene(scene);
            dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("SAVE"));
            dialogStage.showAndWait();
        }
    }
    
    
    public void printAction(){
        try {  
            PrinterJob printjob = PrinterJob.getPrinterJob();
            printjob.setJobName(objDictionaryAction.getWord("PRINT"));
            if(printjob.printDialog()){
                printjob.setPrintable(new Printable() {
                    @Override
                    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                        if (pageIndex != 0) {
                            return NO_SUCH_PAGE;
                        }
                        BufferedImage texture = new BufferedImage(objWeave.getIntWarp(), objWeave.getIntWeft(), BufferedImage.TYPE_INT_RGB);        
                        for(int x = 0; x < objWeave.getIntWeft(); x++) {
                            for(int y = 0; y < objWeave.getIntWarp(); y++) {
                                if(objWeave.getDesignMatrix()[x][y]==0)
                                    texture.setRGB(y, x, java.awt.Color.WHITE.getRGB());
                                else
                                    texture.setRGB(y, x, java.awt.Color.BLACK.getRGB());
                            }
                        }
                        try{
                            int widthDesign=objWeave.getIntWeft()*boxSize;
                            int widthCompositeView=objWeave.getIntWeft()*boxSize;
                            if((objWeave.getIntWeft()*boxSize)<(graphics.getFont().getSize()*5))
                                widthDesign=(graphics.getFont().getSize()*5);
                            if((objWeave.getIntWeft()*boxSize)<(graphics.getFont().getSize()*14))
                                widthCompositeView=(graphics.getFont().getSize()*14);
                            // 10 is added for providing space between design and composite view
                            int totalWidth=widthDesign+widthCompositeView+10;
                            BufferedImage bufferedImageesize = new BufferedImage(totalWidth, (int)(objWeave.getIntWarp()*boxSize)+(graphics.getFont().getSize()*2),BufferedImage.TYPE_INT_RGB);
                            // set white background
                            Graphics2D g = bufferedImageesize.createGraphics();
                            g.setColor(java.awt.Color.WHITE);
                            g.fillRect(0, 0, bufferedImageesize.getWidth(), bufferedImageesize.getHeight());
                            
                            BasicStroke bs = new BasicStroke(1);
                            g.setStroke(bs);
                            g.setColor(java.awt.Color.BLACK);
                            g.drawImage(texture, 0, 0, (int)(objWeave.getIntWeft()*boxSize), (int)(objWeave.getIntWarp()*boxSize), null);
                            
                            texture = new BufferedImage( (int)(objWeave.getIntWeft()*boxSize), (int)(objWeave.getIntWarp()*boxSize), BufferedImage.TYPE_INT_RGB);
                            WeaveAction objWeaveAction = new WeaveAction();
                            texture = objWeaveAction.plotCompositeView(objWeave, objWeave.getIntWarp(), objWeave.getIntWeft(),  (int)(objWeave.getIntWeft()*boxSize), (int)(objWeave.getIntWarp()*boxSize));
                            g.drawImage(texture, widthDesign+10, 0, (int)(objWeave.getIntWeft()*boxSize), (int)(objWeave.getIntWarp()*boxSize), null);

                            g.drawString(objDictionaryAction.getWord("COMPOSITEVIEW"), widthDesign+10, (int)(objWeave.getIntWarp()*boxSize)+graphics.getFont().getSize());
                            // added for grid lines
                            for(int r=0; r<objWeave.getIntWeft(); r++)
                                g.drawLine(0, r*boxSize, (int)(objWeave.getIntWeft()*boxSize)-1, r*boxSize);
                            g.drawLine(0, (int)(objWeave.getIntWarp()*boxSize)-1, (int)(objWeave.getIntWeft()*boxSize)-1, (int)(objWeave.getIntWarp()*boxSize)-1);
                            for(int c=0; c<objWeave.getIntWarp(); c++)
                                g.drawLine(c*boxSize, 0, c*boxSize, (int)(objWeave.getIntWarp()*boxSize)-1);
                            g.drawString(objDictionaryAction.getWord("DESIGN"), 0, objWeave.getIntWeft()*boxSize+graphics.getFont().getSize());
                            g.drawLine((int)(objWeave.getIntWeft()*boxSize)-1, 0, (int)(objWeave.getIntWeft()*boxSize)-1, (int)(objWeave.getIntWarp()*boxSize)-1);
                            g.dispose();
                            
                            //ImageIO.write(bufferedImageesize, "png", new File("d:\\i.png"));
                            texture = null;
                            Graphics2D g2d=(Graphics2D)graphics;
                            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                            graphics.drawImage(bufferedImageesize, 0, 0, bufferedImageesize.getWidth(), bufferedImageesize.getHeight(), null);                        
                            bufferedImageesize = null;
                            System.gc();
                        }
                        catch(Exception ex){
                            new Logging("SEVERE",WeaveView.class.getName(),"printAction() : Print Error",ex);
                        }
                        return PAGE_EXISTS;                    
                    }
                });
                printjob.print();
            }
        } catch (PrinterException ex) {             
            new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);  
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }    
   
   private void openFabricEditorAction(){
        final Stage dialogStageParent = new Stage();
        dialogStageParent.initStyle(StageStyle.UTILITY);
        //dialogStage.initModality(Modality.WINDOW_MODAL);
        GridPane popupParent=new GridPane();
        popupParent.setId("popup");
        popupParent.setHgap(10);
        popupParent.setVgap(10);

        Label clothType = new Label(objDictionaryAction.getWord("CLOTHTYPE")+" : ");
        clothType.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLOTHTYPE")));
        popupParent.add(clothType, 0, 0);
        final ComboBox clothTypeCB = new ComboBox();
        clothTypeCB.getItems().addAll("Body","Palu","Border","Cross Border","Blouse","Skart","Konia");
        clothTypeCB.setValue(objConfiguration.getStrClothType());
        clothTypeCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLOTHTYPE")));
        clothTypeCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, final String st, final String st1) {
               try {
                    final Stage dialogStage = new Stage();
                    dialogStage.initStyle(StageStyle.UTILITY);
                    dialogStage.initModality(Modality.APPLICATION_MODAL);
                    BorderPane root = new BorderPane();
                    Scene scene = new Scene(root, 300, 200, Color.WHITE);
                    scene.getStylesheets().add(WeaveView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                    final GridPane popup=new GridPane();
                    popup.setId("popup");
                    popup.setHgap(10);
                    popup.setVgap(10);
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
                                dialogStage.close();
                                objConfiguration.setStrClothType(st1);
                                UserAction objUserAction = new UserAction();
                                objUserAction.getConfiguration(objConfiguration);
                                objConfiguration.clothRepeat();
                                System.gc();
                            } catch (SQLException ex) {
                                new Logging("SEVERE",WeaveView.class.getName(),"Cloth type change",ex);
                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                            } catch (Exception ex) {
                                new Logging("SEVERE",WeaveView.class.getName(),"Cloth type change",ex);
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
                            //clothTypeCB.setValue(st);
                            System.gc();
                        }
                    });
                    root.setCenter(popup);
                    dialogStage.setScene(scene);
                    dialogStage.showAndWait();
                    //ov.removeListener(st);
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"cloth type property change"+ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });    
        popupParent.add(clothTypeCB, 1, 0);        
        
        Button btnApply = new Button(objDictionaryAction.getWord("APPLY"));
        btnApply.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
        btnApply.setTooltip(new Tooltip(objDictionaryAction.getWord("ACTIONAPPLY")));
        btnApply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    objConfiguration.strWindowFlowContext = "WeaveEditor";
                    if(isNew){
                        objWeave.setStrWeaveAccess(objConfiguration.getObjUser().getUserAccess("WEAVE_LIBRARY"));
                        saveUpdateAction();//saveAsAction();
                    }
                    else
                        saveUpdateAction();//saveAction();
                    System.gc();
                    if(objWeave.getStrWeaveID()!=null&&!isNew){
                        objConfiguration.setStrRecentWeave(objWeave.getStrWeaveID());
                        weaveStage.close();
                        System.gc();
                        FabricView objFabricView = new FabricView(objConfiguration);
                    }
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }  
                System.gc();
                dialogStageParent.close();
            }
        });
        popupParent.add(btnApply, 0, 1);

        Button btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
        btnCancel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
        btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {  
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCANCEL"));
                dialogStageParent.close();
            }
        });
        popupParent.add(btnCancel, 1, 1);

        Scene scene = new Scene(popupParent);
        scene.getStylesheets().add(WeaveView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        dialogStageParent.setScene(scene);
        dialogStageParent.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("FABRICSETTINGS"));
        dialogStageParent.showAndWait();        
    }
    public void closeAction(){
        weaveStage.close();
    }
   
    /*
    public void plotShaft(){
        try {
            shaftGP.getChildren().clear();
            System.gc();
            objWeaveAction = new WeaveAction(objWeave,false);
            for(int i=0;i<objWeave.getIntWeaveShaft();i++){
                for(int j=0;j<objWeave.getIntWarp();j++) {
                    final int p=i;
                    final int q=j;
                    final Label lblbox = new Label("");
                    lblbox.setPrefSize(boxSize,boxSize);
                    if(objWeave.getShaftMatrix()[i][j]==1 ){
                        lblbox.setStyle("-fx-background-color: #000000;-fx-border-width: 1;-fx-border-color: black;");
                        lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, boxSize));                        
                    }else{
                        lblbox.setStyle("-fx-background-color: #ffffff; -fx-border-width: 1;  -fx-border-color: black;");
                        lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                    }
                    lblbox.setOnMouseClicked(new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent arg0) {
                            if(objWeave.getShaftMatrix()[p][q]==1 ){
                             //   Label lblbox1=shaftGP.getChildren().get()
                                lblbox.setStyle("-fx-background-color: #ffffff; -fx-border-width: 1;  -fx-border-color: black;");
                                lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                                objWeave.getShaftMatrix()[p][q]=0;                                
                            } else {
                               *//* for(int r=0;r<objWeave.getIntWeaveShaft();r++){
                                    lblbox.setStyle("-fx-background-color: #ffffff; -fx-border-width: 1;  -fx-border-color: black;");
                                    lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                                    objWeave.getShaftMatrix()[r][q]=0;
                                    if(editMode.equalsIgnoreCase("none")){
                                        objWeaveAction.populateShaftDesign(objWeave, r,q);
                                        objWeaveAction.populateTreadles(objWeave);
                                        objWeaveAction.populateTieUp(objWeave);
                                        objWeaveAction.populatePegPlan(objWeave);
                                    } else if(editMode.equalsIgnoreCase("all")){

                                    } else{
                                        objWeaveAction.populateShaftDesign(objWeave, r,q);
                                    }
                                }
                                lblbox.setStyle("-fx-background-color: #000000;-fx-border-width: 1;-fx-border-color: black;");
                                lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, boxSize));
                                objWeave.getShaftMatrix()[p][q]=1;
                                */
                                /*boolean shaftPresent = false;
                                for(int r=0;r<objWeave.getIntWeaveShaft();r++)
                                    if(objWeave.getShaftMatrix()[r][q]==1)
                                        shaftPresent = true;
                                if(!shaftPresent){
                                    lblbox.setStyle("-fx-background-color: #000000;-fx-border-width: 1;-fx-border-color: black;");
                                    lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, boxSize));
                                    objWeave.getShaftMatrix()[p][q]=1;
                                }*/
                                /*
                                for(int r=0;r<objWeave.getIntWeaveShaft();r++){
                                    System.err.println(p+":"+q+"="+shaftMatrix[p][q]);
                                    if(p!=r){
                                        lblbox.setStyle("-fx-background-color: #ffffff; -fx-border-width: 1;  -fx-border-color: black;");
                                        lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                                        shaftMatrix[r][q]=0;                                        
                                    }
                                    System.err.println(r+":"+q+"="+shaftMatrix[r][q]);
                                }
                                
                
                            }*/
                            /*if(editMode.equalsIgnoreCase("none")){
                                objWeaveAction.populateShaftDesign(objWeave, p,q);
                                objWeaveAction.populateTreadles(objWeave);
                                objWeaveAction.populateTieUp(objWeave);
                                objWeaveAction.populatePegPlan(objWeave);
                                plotTradeles();
                                plotTieUp();
                                plotPeg();
                                populateDplot();
                            } else if(editMode.equalsIgnoreCase("all")){
                              
                            } else{
                                objWeaveAction.populateShaftDesign(objWeave, p,q);
                                populateDplot();
                            }
                            //plotShaft();
                        }
                    });
                    shaftGP.add(lblbox,j,i);
                }
            }
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveView.class.getName(),"Ploting Shaft",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }                   
    }*/

    /*
   public void plotTieUp(){
        try {
            tieUpGP.getChildren().clear();
            System.gc();
            objWeaveAction = new WeaveAction(objWeave,false);
            for(int i=0;i<objWeave.getIntWeaveShaft();i++){
                for(int j=0;j<objWeave.getIntWeaveTreadles();j++) {
                    final int p=i;
                    final int q=j;
                    final Label lblbox = new Label("");
                    lblbox.setPrefSize(boxSize,boxSize);
                    if(objWeave.getTieupMatrix()[i][j]==1 ){
                        lblbox.setStyle("-fx-background-color: #000000;-fx-border-width: 1;-fx-border-color: black;");
                        lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                    }
                    else{
                        lblbox.setStyle("-fx-background-color: #ffffff; -fx-border-width: 1;  -fx-border-color: black;");
                        lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                    }
                    lblbox.setOnMouseClicked(new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent arg0) {
                            if(objWeave.getTieupMatrix()[p][q]==1 ){
                                lblbox.setStyle("-fx-background-color: #ffffff; -fx-border-width: 1;  -fx-border-color: black;");
                                lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                                objWeave.getTieupMatrix()[p][q]=0;
                            } else {
                                lblbox.setStyle("-fx-background-color: #000000;-fx-border-width: 1;-fx-border-color: black;");
                                lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                                objWeave.getTieupMatrix()[p][q]=1;
                            }
                            if(editMode.equalsIgnoreCase("none")){
                                objWeaveAction.populateTieUpDesign(objWeave, p,q);
                                objWeaveAction.populateShaft(objWeave);
                                objWeaveAction.populateTreadles(objWeave);
                                objWeaveAction.populatePegPlan(objWeave);
                                populateDplot();
                                plotShaft();
                                plotPeg();
                                plotTradeles();
                            } else if(editMode.equalsIgnoreCase("all")){
                              
                            } else{
                                objWeaveAction.populateTieUpDesign(objWeave, p,q);
                                populateDplot();
                            }
                        }
                    });
                    tieUpGP.add(lblbox,objWeave.getIntWeaveShaft()-i-1,objWeave.getIntWeaveTreadles()-j-1);
                }
            }
        } catch (SQLException ex) {                   
            new Logging("SEVERE",WeaveView.class.getName(),"Ploting Tieup",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
   
    public void plotTradeles() {
        try {
            tradelesGP.getChildren().clear();
            System.gc();
            objWeaveAction = new WeaveAction(objWeave,false);
            for(int i=0;i<objWeave.getIntWeft();i++){
                for(int j=0;j<objWeave.getIntWeaveTreadles();j++) {
                    final int p=i;
                    final int q=j;
                    final Label lblbox = new Label("");
                    lblbox.setPrefSize(boxSize,boxSize);
                    if(objWeave.getTreadlesMatrix()[i][j]==1 ){
                        lblbox.setStyle("-fx-background-color: #000000;-fx-border-width: 1;-fx-border-color: black;");
                        lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                    } else{
                        lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                        lblbox.setStyle("-fx-background-color: #ffffff; -fx-border-width: 1;  -fx-border-color: black;");
                    }
                    lblbox.setOnMouseClicked(new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent arg0) {
                            if(objWeave.getTreadlesMatrix()[p][q]==1 ){
                                lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                                lblbox.setStyle("-fx-background-color: #ffffff; -fx-border-width: 1;  -fx-border-color: black;");
                                objWeave.getTreadlesMatrix()[p][q]=0;
                            } else {
                                boolean treadlePresent = false;
                                for(int r=0;r<objWeave.getIntWeaveTreadles();r++)
                                    if(objWeave.getTreadlesMatrix()[p][r]==1 )
                                        treadlePresent = true;
                                if(!treadlePresent){
                                    lblbox.setStyle("-fx-background-color: #000000;-fx-border-width: 1;-fx-border-color: black;");
                                    lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                                    objWeave.getTreadlesMatrix()[p][q]=1;
                                }
                            }
                            if(editMode.equalsIgnoreCase("none")){
                                objWeaveAction.populateTradelesDesign(objWeave, p,q);
                                objWeaveAction.populateShaft(objWeave);
                                objWeaveAction.populateTieUp(objWeave);
                                objWeaveAction.populatePegPlan(objWeave);
                                populateDplot();
                                plotShaft();
                                plotPeg();
                                plotTieUp();
                            } else if(editMode.equalsIgnoreCase("all")){
                              
                            } else{
                                objWeaveAction.populateTradelesDesign(objWeave, p,q);
                                populateDplot();
                            }
                        }
                    });
                    tradelesGP.add(lblbox,j,i);
                }
            }    
        } catch (SQLException ex) {
            new Logging("SEVERE",WeaveView.class.getName(),"Ploting Treadles",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public void plotPeg() {
        try {
            pegGP.getChildren().clear();
            System.gc();
            objWeaveAction = new WeaveAction(objWeave,false);
            for(int i=0;i<objWeave.getIntWeft();i++){
                for(int j=0;j<objWeave.getIntWeaveShaft();j++) {
                    final int p=i;
                    final int q=j;
                    final Label lblbox = new Label("");
                    lblbox.setPrefSize(boxSize,boxSize);
                    if(objWeave.getPegMatrix()[i][j]==1 ){
                        lblbox.setStyle("-fx-background-color: #000000;-fx-border-width: 1;-fx-border-color: black;");
                        lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                    } else{
                        lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                        lblbox.setStyle("-fx-background-color: #ffffff; -fx-border-width: 1;  -fx-border-color: black;");
                    }
                    lblbox.setOnMouseClicked(new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent arg0) {
                            if(objWeave.getPegMatrix()[p][q]==1 ){
                                lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                                lblbox.setStyle("-fx-background-color: #ffffff; -fx-border-width: 1;  -fx-border-color: black;");
                                objWeave.getPegMatrix()[p][q]=0;
                            } else {
                                lblbox.setStyle("-fx-background-color: #000000;-fx-border-width: 1;-fx-border-color: black;");
                                lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                                objWeave.getPegMatrix()[p][q]=1;
                            }
                            if(editMode.equalsIgnoreCase("none")){
                                objWeaveAction.populatePegDesign(objWeave, p,q);
                                objWeaveAction.populateShaft(objWeave);
                                objWeaveAction.populateTieUp(objWeave);
                                objWeaveAction.populateTreadles(objWeave);
                                populateDplot();
                                plotShaft();
                                plotTieUp();
                                plotTradeles();
                            } else if(editMode.equalsIgnoreCase("all")){
                                
                            } else{
                                objWeaveAction.populatePegDesign(objWeave, p,q);
                                populateDplot();
                            }
                        }
                    });
                    pegGP.add(lblbox,j,i);
                }
            }    
        } catch (SQLException ex) {
            new Logging("SEVERE",WeaveView.class.getName(),"Ploting Treadles",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }

   public void plotDesign() {
        try {
            designGP.getChildren().clear();
            System.gc();
            objWeaveAction = new WeaveAction(objWeave,false);
            for(int i=0;i<objWeave.getIntWeft();i++){
                for(int j=0;j<objWeave.getIntWarp();j++) {
                    final int p=i;
                    final int q=j;
                    final Label lblbox = new Label("");
                    lblbox.setPrefSize(boxSize,boxSize);
                    if(objWeave.getDesignMatrix()[i][j]==1 ){
                        lblbox.setStyle("-fx-background-color: #000000;-fx-border-width: 1;  -fx-border-color: black");
                        lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                    } else{
                        lblbox.setStyle("-fx-background-color: #ffffff; -fx-border-width: 1;  -fx-border-color: black;");
                        lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                    }
                    if(isDragBox==0)
                        selectionLineArea(lblbox,p,q);
                    else
                        selectionBoxArea(lblbox, p, q);
                    lblbox.setOnMouseClicked(new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent arg0) {
                            try{
                                if(isDragBox==0)
                                    selectionLineArea(lblbox,p,q);
                                else
                                    selectionBoxArea(lblbox, p, q);
                                if(objWeave.getDesignMatrix()[p][q]==1){
                                    lblbox.setStyle("-fx-background-color: #ffffff; -fx-border-width: 1;  -fx-border-color: black;");
                                    lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                                    objWeave.getDesignMatrix()[p][q]=0;
                                } else {
                                    lblbox.setStyle("-fx-background-color: #000000;-fx-border-width: 1;  -fx-border-color: black");
                                    lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                                    objWeave.getDesignMatrix()[p][q]=1;
                                }
                                current_row=p;
                                current_col=q;
                                populateNplot();
                            } catch (Exception ex) {
                                Logger.getLogger(WeaveView.class.getName()).log(Level.SEVERE, null, ex);
                                new Logging("SEVERE",WeaveView.class.getName(),"Ploting weave Design",ex);
                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                            }
                        }
                    });
                    designGP.add(lblbox,j,i);
                }    
            }
        } catch (SQLException ex) {
            new Logging("SEVERE",WeaveView.class.getName(),"Ploting weave Design",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
   
   public void plotDenting() {
        dentingGP.getChildren().clear();
        System.gc();
        for(int i=0;i<2;i++){
            for(int j=0;j<objWeave.getIntWarp();j++) {
                final int p=i;
                final int q=j;
                final Label lblbox = new Label("");
                lblbox.setPrefSize(boxSize,boxSize);
                if(objWeave.getDentMatrix()[i][j]==1 ){
                    lblbox.setStyle("-fx-background-color: #fedcba;-fx-border-width: 1;  -fx-border-color: black");
                    lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                } else{
                    lblbox.setStyle("-fx-background-color: #ffffff; -fx-border-width: 1;  -fx-border-color: black;");
                    lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                }
                lblbox.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent arg0) {
                        try{
                            if(objWeave.getDentMatrix()[p][q]==1){
                                lblbox.setStyle("-fx-background-color: #ffffff; -fx-border-width: 1;  -fx-border-color: black;");
                                lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                                objWeave.getDentMatrix()[p][q]=0;
                                objWeave.getDentMatrix()[(p+1)%2][q]=1;
                            } else {
                                lblbox.setStyle("-fx-background-color: #fedcba;-fx-border-width: 1;  -fx-border-color: black");
                                lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                                objWeave.getDentMatrix()[p][q]=1;
                                objWeave.getDentMatrix()[(p+1)%2][q]=0;
                            }
                            plotDenting();
                        } catch (Exception ex) {
                            Logger.getLogger(WeaveView.class.getName()).log(Level.SEVERE, null, ex);
                            new Logging("SEVERE",WeaveView.class.getName(),"Ploting weave Design",ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });
                dentingGP.add(lblbox,j,i);
            }    
        }
    }*/

    public void plotDesign(){
        if(objWeave.getDesignMatrix()!=null){
            drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
            refreshDesignImage();
        }
    }
    
    public void initShaft(){
        if(objWeave.getShaftMatrix().length>objWeave.getIntWeaveShaft())
            return;
        byte[][] matrix=new byte[objWeave.getIntWeaveShaft()][objWeave.getIntWarp()];
        int toAdd=objWeave.getIntWeaveShaft()-objWeave.getShaftMatrix().length;
        for(int j=0;j<objWeave.getShaftMatrix().length;j++)
            for(int k=0;k<objWeave.getIntWarp(); k++)
                matrix[toAdd+j][k]=objWeave.getShaftMatrix()[j][k];
        for(int j=0;j<toAdd;j++)
            for(int k=0;k<objWeave.getIntWarp(); k++)
                matrix[j][k]=0;
        objWeave.setShaftMatrix(matrix);
    }
    
    public void plotShaft(){
        if(objWeave.getShaftMatrix()!=null){
            byte[][] matrix=new byte[objWeave.getShaftMatrix().length][objWeave.getShaftMatrix()[0].length];
            //int start=0;
            //if(activeGridRow>objWeave.getShaftMatrix().length)
            //    start=activeGridRow-objWeave.getShaftMatrix().length;
            for(int r=0; r<objWeave.getShaftMatrix().length; r++){
                for(int c=0; c<objWeave.getShaftMatrix()[0].length; c++){
                    matrix[r/*+start*/][c]=objWeave.getShaftMatrix()[r][c];
                }
            }
            drawImageFromMatrix(matrix, shaftImage);
            refreshShaftImage();
        }
    }
    
    public void initPeg(){
        if(objWeave.getPegMatrix()[0].length>objWeave.getIntWeaveShaft())
            return;
        byte[][] matrix=new byte[objWeave.getIntWeft()][objWeave.getIntWeaveShaft()];
        int toAdd=objWeave.getIntWeaveShaft()-objWeave.getPegMatrix()[0].length;
        for(int j=0;j<objWeave.getIntWeft();j++)
            for(int k=0;k<objWeave.getPegMatrix()[0].length; k++)
                matrix[j][k]=objWeave.getPegMatrix()[j][k];
        for(int j=0;j<objWeave.getIntWeft();j++)
            for(int k=0;k<toAdd; k++)
                matrix[j][k+objWeave.getPegMatrix()[0].length]=0;
        objWeave.setPegMatrix(matrix);
    }

    public void plotPeg(){
        if(objWeave.getPegMatrix()!=null){
            byte[][] matrix=new byte[objWeave.getPegMatrix().length][objWeave.getPegMatrix()[0].length];
            //new byte[activeGridRow][activeGridCol];
            for(int r=0; r<objWeave.getPegMatrix().length; r++){
                for(int c=0; c<objWeave.getPegMatrix()[0].length; c++){
                    matrix[r][c]=objWeave.getPegMatrix()[r][c];
                }
            }
            drawImageFromMatrix(matrix, pegImage);
            refreshPegImage();
        }
    }

    public void plotTieUp(){
        if(objWeave.getTieupMatrix()!=null){
            byte[][] matrix=new byte[objWeave.getTieupMatrix().length][objWeave.getTieupMatrix()[0].length];
            //new byte[activeGridRow][activeGridCol];
            //int start=0;
            //if(activeGridRow>objWeave.getTieupMatrix().length)
            //    start=activeGridRow-objWeave.getTieupMatrix().length;
            for(int r=0; r<objWeave.getTieupMatrix().length; r++){
                for(int c=0; c<objWeave.getTieupMatrix()[0].length; c++){
                    matrix[r/*+start*/][c]=objWeave.getTieupMatrix()[r][c];
                }
            }
            drawImageFromMatrix(matrix, tieUpImage);
            refreshTieUpImage();
        }
    }
    
    public void initTreadles(){
        if(objWeave.getTreadlesMatrix()[0].length>objWeave.getIntWeaveTreadles())
            return;
        byte[][] matrix=new byte[objWeave.getIntWeft()][objWeave.getIntWeaveTreadles()];
        int toAdd=objWeave.getIntWeaveTreadles()-objWeave.getTreadlesMatrix()[0].length;
        for(int j=0;j<objWeave.getIntWeft();j++)
            for(int k=0;k<objWeave.getTreadlesMatrix()[0].length; k++)
                matrix[j][k]=objWeave.getTreadlesMatrix()[j][k];
        for(int j=0;j<objWeave.getIntWeft();j++)
            for(int k=0;k<toAdd; k++)
                matrix[j][objWeave.getTreadlesMatrix()[0].length+k]=0;
        objWeave.setTreadlesMatrix(matrix);
    }

    public void plotTradeles(){
        if(objWeave.getTreadlesMatrix()!=null){
            byte[][] matrix=new byte[objWeave.getTreadlesMatrix().length][objWeave.getTreadlesMatrix()[0].length];
                    //new byte[activeGridRow][activeGridCol];
            for(int r=0; r<objWeave.getTreadlesMatrix().length; r++){
                for(int c=0; c<objWeave.getTreadlesMatrix()[0].length; c++){
                    matrix[r][c]=objWeave.getTreadlesMatrix()[r][c];
                }
            }
            drawImageFromMatrix(matrix, tradelesImage);
            refreshTradelesImage();
        }
    }

    public void plotDenting(){
        if(objWeave.getDentMatrix()!=null){
            byte[][] matrix=new byte[2][objWeave.getDentMatrix()[0].length];
            for(int r=0; r<objWeave.getDentMatrix().length; r++){
                for(int c=0; c<objWeave.getDentMatrix()[0].length; c++){
                    matrix[r][c]=objWeave.getDentMatrix()[r][c];
                }
            }
            drawImageFromMatrix(matrix, dentingImage);
            refreshDentingImage();
        }
    }
    
    public void plotWarpColor(){
        clearImage(warpColorImage);
        Point dot=new Point();
        int intColor=-1;
        for(int i=0;i<objWeave.getIntWarp();i++) {
            intColor=getIntRgbFromColor(255
                    , Integer.parseInt(objWeave.getWarpYarn()[i].getStrYarnColor().substring(1, 3), 16)
                    , Integer.parseInt(objWeave.getWarpYarn()[i].getStrYarnColor().substring(3, 5), 16)
                    , Integer.parseInt(objWeave.getWarpYarn()[i].getStrYarnColor().substring(5, 7), 16));
            dot.x=i;
            dot.y=0;
            //System.err.println(intColor+"Yarn"+objWeave.getWarpYarn()[i].getStrYarnColor());
            fillImageDotPixels(dot, intColor, warpColorImage);
        }
        refreshImage(warpColorImage);
    }
    
    public void plotWeftColor(){
        clearImage(weftColorImage);
        Point dot=new Point();
        int intColor=-1;
        for(int i=0;i<objWeave.getIntWeft();i++) {
            intColor=getIntRgbFromColor(255
                    , Integer.parseInt(objWeave.getWeftYarn()[i].getStrYarnColor().substring(1, 3), 16)
                    , Integer.parseInt(objWeave.getWeftYarn()[i].getStrYarnColor().substring(3, 5), 16)
                    , Integer.parseInt(objWeave.getWeftYarn()[i].getStrYarnColor().substring(5, 7), 16));
            dot.x=0;
            dot.y=(MAX_GRID_ROW-1)-(activeGridRow-1)+i;
            fillImageDotPixels(dot, intColor, weftColorImage);
        }
        refreshImage(weftColorImage);
    }

    /*
   public void plotWarpColor() {
        warpColorGP.getChildren().clear();
        System.gc();
        for(int i=0;i<objWeave.getIntWarp();i++) {
            final int p=i;
            final Label lblbox = new Label("");
            lblbox.setPrefSize(boxSize,boxSize);
            lblbox.setStyle("-fx-background-color: "+objWeave.getWarpYarn()[i].getStrYarnColor()+";-fx-border-width: 1;  -fx-border-color: black");
            lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
            
            lblbox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() >1) {
                        editYarn();
                    } else {
                        lblbox.setStyle("-fx-background-color: #"+objWeave.getStrThreadPalettes()[intWarpColor]+";-fx-border-width: 1;  -fx-border-color: black");
                        lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                        objWeave.getWarpYarn()[p]= new Yarn(null, "Warp", objConfiguration.getStrWarpName(), "#"+objWeave.getStrThreadPalettes()[intWarpColor], objConfiguration.getIntWarpRepeat(), Character.toString((char)(65+intWarpColor)), objConfiguration.getIntWarpCount(), objConfiguration.getStrWarpUnit(), objConfiguration.getIntWarpPly(), objConfiguration.getIntWarpFactor(), objConfiguration.getDblWarpDiameter(), objConfiguration.getIntWarpTwist(), objConfiguration.getStrWarpSence(), objConfiguration.getIntWarpHairness(), objConfiguration.getIntWarpDistribution(), objConfiguration.getDblWarpPrice(), objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"),objConfiguration.getObjUser().getStrUserID(),null);
                    }
                }
            });
            lblbox.setOnDragDetected(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    // drag was detected, start a drag-and-drop gesture
                    // allow any transfer mode 
                    Dragboard db = lblbox.startDragAndDrop(TransferMode.ANY);
                    // Put a string on a dragboard
                    ClipboardContent content = new ClipboardContent();
                    content.putString(objWeave.getStrThreadPalettes()[intWarpColor]);
                    db.setContent(content);
        
                    lblbox.setStyle("-fx-background-color: #"+objWeave.getStrThreadPalettes()[intWarpColor]+";-fx-border-width: 1;  -fx-border-color: black");
                    lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                    objWeave.getWarpYarn()[p]= new Yarn(null, "Warp", objConfiguration.getStrWarpName(), "#"+objWeave.getStrThreadPalettes()[intWarpColor], objConfiguration.getIntWarpRepeat(), Character.toString((char)(65+intWarpColor)), objConfiguration.getIntWarpCount(), objConfiguration.getStrWarpUnit(), objConfiguration.getIntWarpPly(), objConfiguration.getIntWarpFactor(), objConfiguration.getDblWarpDiameter(), objConfiguration.getIntWarpTwist(), objConfiguration.getStrWarpSence(), objConfiguration.getIntWarpHairness(), objConfiguration.getIntWarpDistribution(), objConfiguration.getDblWarpPrice(), objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"),objConfiguration.getObjUser().getStrUserID(),null);

                    event.consume();
                }
            });
            lblbox.setOnDragOver(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    // data is dragged over the target
                    // accept it only if it is not dragged 
                    if (event.getGestureSource() != lblbox && event.getDragboard().hasString()) {
                        lblbox.setStyle("-fx-background-color: #"+objWeave.getStrThreadPalettes()[intWarpColor]+";-fx-border-width: 1;  -fx-border-color: black");
                        lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                        objWeave.getWarpYarn()[p]= new Yarn(null, "Warp", objConfiguration.getStrWarpName(), "#"+objWeave.getStrThreadPalettes()[intWarpColor], objConfiguration.getIntWarpRepeat(), Character.toString((char)(65+intWarpColor)), objConfiguration.getIntWarpCount(), objConfiguration.getStrWarpUnit(), objConfiguration.getIntWarpPly(), objConfiguration.getIntWarpFactor(), objConfiguration.getDblWarpDiameter(), objConfiguration.getIntWarpTwist(), objConfiguration.getStrWarpSence(), objConfiguration.getIntWarpHairness(), objConfiguration.getIntWarpDistribution(), objConfiguration.getDblWarpPrice(), objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"),objConfiguration.getObjUser().getStrUserID(),null);
                        
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
                    event.consume();
                }
            });
            lblbox.setOnDragEntered(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    // the drag-and-drop gesture entered the target
                    // show to the user that it is an actual gesture target
                    if (event.getGestureSource() != lblbox && event.getDragboard().hasString()) {
                    
                    }
                    event.consume();
                }
            });
            lblbox.setOnDragExited(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    // mouse moved away, remove the graphical cues
                    event.consume();
                }
            });
            lblbox.setOnDragDropped(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    // data dropped
                    // if there is a string data on dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    // let the source know whether the string was successfully
                    // transferred and used 
                    lblbox.setStyle("-fx-background-color: #"+objWeave.getStrThreadPalettes()[intWarpColor]+";-fx-border-width: 1;  -fx-border-color: black");
                    lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                    objWeave.getWarpYarn()[p]= new Yarn(null, "Warp", objConfiguration.getStrWarpName(), "#"+objWeave.getStrThreadPalettes()[intWarpColor], objConfiguration.getIntWarpRepeat(), Character.toString((char)(65+intWarpColor)), objConfiguration.getIntWarpCount(), objConfiguration.getStrWarpUnit(), objConfiguration.getIntWarpPly(), objConfiguration.getIntWarpFactor(), objConfiguration.getDblWarpDiameter(), objConfiguration.getIntWarpTwist(), objConfiguration.getStrWarpSence(), objConfiguration.getIntWarpHairness(), objConfiguration.getIntWarpDistribution(), objConfiguration.getDblWarpPrice(), objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"),objConfiguration.getObjUser().getStrUserID(),null);

                    event.setDropCompleted(success);
                    event.consume();
                 }
            });
            lblbox.setOnDragDone(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    // the drag and drop gesture ended 
                    // if the data was successfully moved, clear it 
                    if (event.getTransferMode() == TransferMode.MOVE) {
                        Dragboard db = event.getDragboard();
                    }
                    event.consume();
                }
            });
            warpColorGP.add(lblbox,i,0);
        }    
    }
   
   public void plotWeftColor() {
        weftColorGP.getChildren().clear();
        System.gc();
        for(int i=0;i<objWeave.getIntWeft();i++) {
            final int p=i;
            final Label lblbox = new Label("");
            lblbox.setPrefSize(boxSize,boxSize);

            lblbox.setStyle("-fx-background-color: " + objWeave.getWeftYarn()[i].getStrYarnColor() + ";-fx-border-width: 1;  -fx-border-color: black");
            lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));

            lblbox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() >1) {
                        editYarn();
                    } else{
                        lblbox.setStyle("-fx-background-color: #" + objWeave.getStrThreadPalettes()[intWeftColor] + ";-fx-border-width: 1;  -fx-border-color: black");
                        lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                        objWeave.getWeftYarn()[p]= new Yarn(null, "Weft", objConfiguration.getStrWeftName(), "#"+objWeave.getStrThreadPalettes()[intWeftColor], objConfiguration.getIntWeftRepeat(), Character.toString((char)(97+intWeftColor-26)), objConfiguration.getIntWeftCount(), objConfiguration.getStrWeftUnit(), objConfiguration.getIntWeftPly(), objConfiguration.getIntWeftFactor(), objConfiguration.getDblWeftDiameter(), objConfiguration.getIntWeftTwist(), objConfiguration.getStrWeftSence(), objConfiguration.getIntWeftHairness(), objConfiguration.getIntWeftDistribution(), objConfiguration.getDblWeftPrice(), objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"),objConfiguration.getObjUser().getStrUserID(),null);
                    }
                }
            });
            lblbox.setOnDragDetected(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    // drag was detected, start a drag-and-drop gesture
                    // allow any transfer mode 
                    Dragboard db = lblbox.startDragAndDrop(TransferMode.ANY);
                    // Put a string on a dragboard 
                    ClipboardContent content = new ClipboardContent();
                    content.putString(objWeave.getStrThreadPalettes()[intWeftColor]);
                    db.setContent(content);
        
                    lblbox.setStyle("-fx-background-color: #"+objWeave.getStrThreadPalettes()[intWeftColor]+";-fx-border-width: 1;  -fx-border-color: black");
                    lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                    objWeave.getWeftYarn()[p]= new Yarn(null, "Weft", objConfiguration.getStrWeftName(), "#"+objWeave.getStrThreadPalettes()[intWeftColor], objConfiguration.getIntWeftRepeat(), Character.toString((char)(97+intWeftColor-26)), objConfiguration.getIntWeftCount(), objConfiguration.getStrWeftUnit(), objConfiguration.getIntWeftPly(), objConfiguration.getIntWeftFactor(), objConfiguration.getDblWeftDiameter(), objConfiguration.getIntWeftTwist(), objConfiguration.getStrWeftSence(), objConfiguration.getIntWeftHairness(), objConfiguration.getIntWeftDistribution(), objConfiguration.getDblWeftPrice(), objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"),objConfiguration.getObjUser().getStrUserID(),null);
                    event.consume();
                }
            });
            lblbox.setOnDragOver(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    // data is dragged over the target 
                    // accept it only if it is not dragged 
                    if (event.getGestureSource() != lblbox && event.getDragboard().hasString()) {
                        lblbox.setStyle("-fx-background-color: #"+objWeave.getStrThreadPalettes()[intWeftColor]+";-fx-border-width: 1;  -fx-border-color: black");
                        lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                        objWeave.getWeftYarn()[p]= new Yarn(null, "Weft", objConfiguration.getStrWeftName(), "#"+objWeave.getStrThreadPalettes()[intWeftColor], objConfiguration.getIntWeftRepeat(), Character.toString((char)(97+intWeftColor-26)), objConfiguration.getIntWeftCount(), objConfiguration.getStrWeftUnit(), objConfiguration.getIntWeftPly(), objConfiguration.getIntWeftFactor(), objConfiguration.getDblWeftDiameter(), objConfiguration.getIntWeftTwist(), objConfiguration.getStrWeftSence(), objConfiguration.getIntWeftHairness(), objConfiguration.getIntWeftDistribution(), objConfiguration.getDblWeftPrice(), objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"),objConfiguration.getObjUser().getStrUserID(),null);
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
                    event.consume();
                }
            });
            lblbox.setOnDragEntered(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    // the drag-and-drop gesture entered the target 
                    // show to the user that it is an actual gesture target
                    if (event.getGestureSource() != lblbox && event.getDragboard().hasString()) {
                    
                    }
                    event.consume();
                }
            });
            lblbox.setOnDragExited(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    // mouse moved away, remove the graphical cues
                    event.consume();
                }
            });
            lblbox.setOnDragDropped(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    // data dropped
                    // if there is a string data on dragboard, read it and use it 
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    // let the source know whether the string was successfully
                    // transferred and used 
                    lblbox.setStyle("-fx-background-color: #"+objWeave.getStrThreadPalettes()[intWeftColor]+";-fx-border-width: 1;  -fx-border-color: black");
                    lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                    objWeave.getWeftYarn()[p]= new Yarn(null, "Weft", objConfiguration.getStrWeftName(), "#"+objWeave.getStrThreadPalettes()[intWeftColor], objConfiguration.getIntWeftRepeat(), Character.toString((char)(97+intWeftColor-26)), objConfiguration.getIntWeftCount(), objConfiguration.getStrWeftUnit(), objConfiguration.getIntWeftPly(), objConfiguration.getIntWeftFactor(), objConfiguration.getDblWeftDiameter(), objConfiguration.getIntWeftTwist(), objConfiguration.getStrWeftSence(), objConfiguration.getIntWeftHairness(), objConfiguration.getIntWeftDistribution(), objConfiguration.getDblWeftPrice(), objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"),objConfiguration.getObjUser().getStrUserID(),null);
                    event.setDropCompleted(success);
                    event.consume();
                 }
            });
            lblbox.setOnDragDone(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    // the drag and drop gesture ended 
                    // if the data was successfully moved, clear it 
                    if (event.getTransferMode() == TransferMode.MOVE) {
                        Dragboard db = event.getDragboard();
                    }
                    event.consume();
                }
            });
            weftColorGP.add(lblbox,0,i);
        }    
    }
    */
    
    private byte[][] getUpdatedDentMatrix(){
        byte[][] dent_temp= new byte[2][objWeave.getIntWarp()];
        int remaining=0;
        int to=(objWeave.getDentMatrix()[0].length<objWeave.getIntWarp()?objWeave.getDentMatrix()[0].length:objWeave.getIntWarp());
        if(to==objWeave.getDentMatrix()[0].length)
            remaining=objWeave.getIntWarp()-objWeave.getDentMatrix()[0].length;
        for(int j=0;j<to;j++){
            dent_temp[0][j]=objWeave.getDentMatrix()[0][j];
            dent_temp[1][j]=objWeave.getDentMatrix()[1][j];
        }
        for(int k=0; k<remaining; k++){
            dent_temp[0][k+objWeave.getDentMatrix()[0].length]=(byte)((k+objWeave.getDentMatrix()[0].length)%2);
            dent_temp[1][k+objWeave.getDentMatrix()[0].length]=(byte)((k+objWeave.getDentMatrix()[0].length+1)%2);
        }
        return dent_temp;
    }
    
    private Yarn[] getUpdatedWarpColor(){
        Yarn[] warpYarn=new Yarn[objWeave.getIntWarp()];
        int to=(objWeave.getWarpYarn().length<objWeave.getIntWarp()?objWeave.getWarpYarn().length:objWeave.getIntWarp());
        for(int j=0;j<to;j++)
            warpYarn[j]=objWeave.getWarpYarn()[j];
        int newYarnCount=(objWeave.getWarpYarn().length<objWeave.getIntWarp()?(objWeave.getIntWarp()-objWeave.getWarpYarn().length):0);
        for(int j=to;j<to+newYarnCount;j++)
            warpYarn[j]=new Yarn(null, "Warp", objConfiguration.getStrWarpName(), "#ffffff", objConfiguration.getIntWarpRepeat(), "A", objConfiguration.getIntWarpCount(), objConfiguration.getStrWarpUnit(), objConfiguration.getIntWarpPly(), objConfiguration.getIntWarpFactor(), objConfiguration.getDblWarpDiameter(), objConfiguration.getIntWarpTwist(), objConfiguration.getStrWarpSence(), objConfiguration.getIntWarpHairness(), objConfiguration.getIntWarpDistribution(), objConfiguration.getDblWarpPrice(), objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"),objConfiguration.getObjUser().getStrUserID(),null);
        return warpYarn;
    }
    
    private Yarn[] getUpdatedWeftColor(){
        Yarn[] weftYarn=new Yarn[objWeave.getIntWeft()];
        int to=(objWeave.getWeftYarn().length<objWeave.getIntWeft()?objWeave.getWeftYarn().length:objWeave.getIntWeft());
        if(objWeave.getWeftYarn().length<objWeave.getIntWeft()){
            int addedYarnCount=objWeave.getIntWeft()-objWeave.getWeftYarn().length;
            for(int j=0;j<to;j++)
                weftYarn[addedYarnCount+j]=objWeave.getWeftYarn()[j];
            for(int j=0;j<addedYarnCount;j++)
                weftYarn[j]=new Yarn(null, "Weft", objConfiguration.getStrWeftName(), "#ffffff", objConfiguration.getIntWeftRepeat(), "a", objConfiguration.getIntWeftCount(), objConfiguration.getStrWeftUnit(), objConfiguration.getIntWeftPly(), objConfiguration.getIntWeftFactor(), objConfiguration.getDblWeftDiameter(), objConfiguration.getIntWeftTwist(), objConfiguration.getStrWeftSence(), objConfiguration.getIntWeftHairness(), objConfiguration.getIntWeftDistribution(), objConfiguration.getDblWeftPrice(), objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"),objConfiguration.getObjUser().getStrUserID(),null);
        }
        else{
            int deletedYarnCount=objWeave.getWeftYarn().length-objWeave.getIntWeft();
            for(int j=0;j<to;j++)
                weftYarn[j]=objWeave.getWeftYarn()[deletedYarnCount+j];
        }
        
        return weftYarn;
    }
   
   private void populateNplot(){
        try{
            objWeaveAction=new WeaveAction();
            objWeaveAction.populateShaft(objWeave);
            objWeave.setIntWeaveShaft(objWeave.getIntWeft());
            initShaft();
            objWeaveAction.populateTreadles(objWeave);
            objWeave.setIntWeaveTreadles(objWeave.getIntWarp());
            initTreadles();
            objWeaveAction.populatePegPlan(objWeave);
            objWeave.setIntWeaveShaft(objWeave.getIntWeft());
            initPeg();
            objWeaveAction.populateTieUp(objWeave);
            objWeave.setDentMatrix(getUpdatedDentMatrix());
            objWeave.setWarpYarn(getUpdatedWarpColor());
            objWeave.setWeftYarn(getUpdatedWeftColor());
            //drawImageFromMatrix(objWeave.getShaftMatrix(), shaftImage);
            //drawImageFromMatrix(objWeave.getPegMatrix(), pegImage);
            //drawImageFromMatrix(objWeave.getTieupMatrix(), tieUpImage);
            //drawImageFromMatrix(objWeave.getTreadlesMatrix(), tradelesImage);
            //drawImageFromMatrix(objWeave.getDentMatrix(), dentingImage);
            //refreshShaftImage();
            //refreshPegImage();
            //refreshTieUpImage();
            //refreshTradelesImage();
            //refreshDentingImage();
            plotShaft();
            plotPeg();
            plotTieUp();
            plotTradeles();
            plotDenting();
            plotWarpColor();
            plotWeftColor();
        }catch(Exception ex){
            Logger.getLogger(WeaveView.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
   }
   
   private void populateDplot(){
        try{
            //objWeaveAction.populateWarpColor(objWeave);
            //objWeaveAction.populateWeftColor(objWeave);
            //objWeaveAction.populateDenting(objWeave);
            plotDesign();
            plotWarpColor();
            plotWeftColor();
            plotDenting();
        }catch(Exception ex){
            Logger.getLogger(WeaveView.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
   }
 
   public void saveAsHtml() {   
        try {
            FileChooser fileChoser=new FileChooser();
            fileChoser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORTHTML")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
            FileChooser.ExtensionFilter extFilterHTML = new FileChooser.ExtensionFilter("HTML (*.html)", "*.html");
            fileChoser.getExtensionFilters().add(extFilterHTML);
            File ifile=fileChoser.showSaveDialog(weaveStage);
            if(ifile==null)
                return;
            else
                weaveStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+ifile.getAbsoluteFile().getName()+"]");
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            String path = ifile.getPath()+"Weave_"+currentDate;
            
            int blocksize = 10;
            //System.out.println(blocksize+":"+objWeave.getIntWarp()+":"+objWeave.getIntWeft());
            BufferedImage texture = new BufferedImage(objWeave.getIntWarp(), objWeave.getIntWeft(),BufferedImage.TYPE_INT_RGB);        
            for(int x = 0; x < objWeave.getIntWeft(); x++) {
                for(int y = 0; y < objWeave.getIntWarp(); y++) {
                    if(objWeave.getDesignMatrix()[x][y]==0)
                        texture.setRGB(y, x, java.awt.Color.WHITE.getRGB());
                    else
                        texture.setRGB(y, x, java.awt.Color.BLACK.getRGB());
                }
            }
            BufferedImage bufferedImageesize = new BufferedImage( objWeave.getIntWarp()*blocksize, objWeave.getIntWeft()*blocksize, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageesize.createGraphics();
            BasicStroke bs = new BasicStroke(1);
            g.setStroke(bs);
            g.setColor(java.awt.Color.BLACK);
            g.drawImage(texture, 0, 0, objWeave.getIntWarp()*blocksize, objWeave.getIntWeft()*blocksize, null);
            // added for grid lines
            for(int r=0; r<objWeave.getIntWeft(); r++)
                g.drawLine(0, r*blocksize, bufferedImageesize.getWidth()-1, r*blocksize);
            g.drawLine(0, bufferedImageesize.getHeight()-1, bufferedImageesize.getWidth()-1, bufferedImageesize.getHeight()-1);
            for(int c=0; c<objWeave.getIntWarp(); c++)
                g.drawLine(c*blocksize, 0, c*blocksize, bufferedImageesize.getHeight()-1);
            g.drawLine(bufferedImageesize.getWidth()-1, 0, bufferedImageesize.getWidth()-1, bufferedImageesize.getHeight()-1);
            g.dispose();
            File designFile = new File(path+"_design.png");
            ImageIO.write(bufferedImageesize, "png", designFile);
            
            texture = new BufferedImage(objWeave.getIntWarp(), objWeave.getIntWeaveShaft(),BufferedImage.TYPE_INT_RGB);        
            for(int x = 0; x < objWeave.getIntWeaveShaft(); x++) {
                for(int y = 0; y < objWeave.getIntWarp(); y++) {
                    if(objWeave.getShaftMatrix()[x][y]==0)
                        texture.setRGB(y, x, java.awt.Color.WHITE.getRGB());
                    else
                        texture.setRGB(y, x, java.awt.Color.BLACK.getRGB());
                }
            }
            bufferedImageesize = new BufferedImage( objWeave.getIntWarp()*blocksize, objWeave.getIntWeaveShaft()*blocksize, BufferedImage.TYPE_INT_RGB);
            g = bufferedImageesize.createGraphics();
            g.setStroke(bs);
            g.setColor(java.awt.Color.BLACK);
            
            g.drawImage(texture, 0, 0, objWeave.getIntWarp()*blocksize, objWeave.getIntWeaveShaft()*blocksize, null);
            // added for grid lines
            for(int r=0; r<objWeave.getIntWeaveShaft(); r++)
                g.drawLine(0, r*blocksize, bufferedImageesize.getWidth()-1, r*blocksize);
            g.drawLine(0, bufferedImageesize.getHeight()-1, bufferedImageesize.getWidth()-1, bufferedImageesize.getHeight()-1);
            for(int c=0; c<objWeave.getIntWarp(); c++)
                g.drawLine(c*blocksize, 0, c*blocksize, bufferedImageesize.getHeight()-1);
            g.drawLine(bufferedImageesize.getWidth()-1, 0, bufferedImageesize.getWidth()-1, bufferedImageesize.getHeight()-1);
            g.dispose();
            File shaftFile = new File(path+"_drafting.png");
            ImageIO.write(bufferedImageesize, "png", shaftFile);
            
            texture = new BufferedImage(objWeave.getIntWeaveTreadles(), objWeave.getIntWeft(),BufferedImage.TYPE_INT_RGB);        
            for(int x = 0; x < objWeave.getIntWeft(); x++) {
                for(int y = 0; y < objWeave.getIntWeaveTreadles(); y++) {
                    if(objWeave.getTreadlesMatrix()[x][y]==0)
                        texture.setRGB(y, x, java.awt.Color.WHITE.getRGB());
                    else
                        texture.setRGB(y, x, java.awt.Color.BLACK.getRGB());
                }
            }
            bufferedImageesize = new BufferedImage( objWeave.getIntWeaveTreadles()*blocksize, objWeave.getIntWeft()*blocksize, BufferedImage.TYPE_INT_RGB);
            g = bufferedImageesize.createGraphics();
            g.setStroke(bs);
            g.setColor(java.awt.Color.BLACK);
            
            g.drawImage(texture, 0, 0, objWeave.getIntWeaveTreadles()*blocksize, objWeave.getIntWeft()*blocksize, null);
            // added for grid lines
            for(int r=0; r<objWeave.getIntWeft(); r++)
                g.drawLine(0, r*blocksize, bufferedImageesize.getWidth()-1, r*blocksize);
            g.drawLine(0, bufferedImageesize.getHeight()-1, bufferedImageesize.getWidth()-1, bufferedImageesize.getHeight()-1);
            for(int c=0; c<objWeave.getIntWeaveTreadles(); c++)
                g.drawLine(c*blocksize, 0, c*blocksize, bufferedImageesize.getHeight()-1);
            g.drawLine(bufferedImageesize.getWidth()-1, 0, bufferedImageesize.getWidth()-1, bufferedImageesize.getHeight()-1);
            g.dispose();
            File treadleFile = new File(path+"_treadles.png");
            ImageIO.write(bufferedImageesize, "png", treadleFile);
            
            texture = new BufferedImage(objWeave.getIntWeaveTreadles(), objWeave.getIntWeaveShaft(),BufferedImage.TYPE_INT_RGB);        
            for(int x = 0; x < objWeave.getIntWeaveShaft(); x++) {
                for(int y = 0; y < objWeave.getIntWeaveTreadles(); y++) {
                    if(objWeave.getTieupMatrix()[x][y]==0)
                        texture.setRGB(y, x, java.awt.Color.WHITE.getRGB());
                    else
                        texture.setRGB(y, x, java.awt.Color.BLACK.getRGB());
                }
            }
            bufferedImageesize = new BufferedImage( objWeave.getIntWeaveTreadles()*blocksize, objWeave.getIntWeaveShaft()*blocksize, BufferedImage.TYPE_INT_RGB);
            g = bufferedImageesize.createGraphics();
            g.setStroke(bs);
            g.setColor(java.awt.Color.BLACK);
            
            g.drawImage(texture, 0, 0, objWeave.getIntWeaveTreadles()*blocksize, objWeave.getIntWeaveShaft()*blocksize, null);
            // added for grid lines
            for(int r=0; r<objWeave.getIntWeaveShaft(); r++)
                g.drawLine(0, r*blocksize, bufferedImageesize.getWidth()-1, r*blocksize);
            g.drawLine(0, bufferedImageesize.getHeight()-1, bufferedImageesize.getWidth()-1, bufferedImageesize.getHeight()-1);
            for(int c=0; c<objWeave.getIntWeaveTreadles(); c++)
                g.drawLine(c*blocksize, 0, c*blocksize, bufferedImageesize.getHeight()-1);
            g.drawLine(bufferedImageesize.getWidth()-1, 0, bufferedImageesize.getWidth()-1, bufferedImageesize.getHeight()-1);
            g.dispose();
            File tieupFile = new File(path+"_tieup.png");
            ImageIO.write(bufferedImageesize, "png", tieupFile);
            
            texture = new BufferedImage(objWeave.getIntWeaveShaft(), objWeave.getIntWeft(),BufferedImage.TYPE_INT_RGB);        
            for(int x = 0; x < objWeave.getIntWeft(); x++) {
                for(int y = 0; y < objWeave.getIntWeaveShaft(); y++) {
                    if(objWeave.getPegMatrix()[x][y]==0)
                        texture.setRGB(y, x, java.awt.Color.WHITE.getRGB());
                    else
                        texture.setRGB(y, x, java.awt.Color.BLACK.getRGB());
                }
            }
            bufferedImageesize = new BufferedImage( objWeave.getIntWeaveShaft()*blocksize, objWeave.getIntWeft()*blocksize, BufferedImage.TYPE_INT_RGB);
            g = bufferedImageesize.createGraphics();
            g.setStroke(bs);
            g.setColor(java.awt.Color.BLACK);
            
            g.drawImage(texture, 0, 0, objWeave.getIntWeaveShaft()*blocksize, objWeave.getIntWeft()*blocksize, null);
            // added for grid lines
            for(int r=0; r<objWeave.getIntWeft(); r++)
                g.drawLine(0, r*blocksize, bufferedImageesize.getWidth()-1, r*blocksize);
            g.drawLine(0, bufferedImageesize.getHeight()-1, bufferedImageesize.getWidth()-1, bufferedImageesize.getHeight()-1);
            for(int c=0; c<objWeave.getIntWeaveShaft(); c++)
                g.drawLine(c*blocksize, 0, c*blocksize, bufferedImageesize.getHeight()-1);
            g.drawLine(bufferedImageesize.getWidth()-1, 0, bufferedImageesize.getWidth()-1, bufferedImageesize.getHeight()-1);
            g.dispose();
            File pegliftFile = new File(path+"_peg.png");
            ImageIO.write(bufferedImageesize, "png", pegliftFile);
            
            File htmlFile = new File(path+".html");
            
            String INITIAL_TEXT = " This is coumputer generated, So no signature required";
            String IMAGE_TEXT ="<table border=0 width=\"50%\">"
                                    +"<tr>"
                                    +"<td>"
                                    +"<div><img src=\"file:\\\\"+path+"_drafting.png" + 
             "\" title=\"Drafting\" width=\""+objWeave.getIntWeaveShaft()*blocksize+"\" height=\""+objWeave.getIntWarp()*blocksize+"\"><br>Drafting</div>"
                                    +"</td>"
                                    +"<td>"
                                    +"<div><img src=\"file:\\\\"+path+"_tieup.png" + 
             "\" title=\"Tie Up\" width=\""+objWeave.getIntWeaveShaft()*blocksize+"\" height=\""+objWeave.getIntWeaveTreadles()*blocksize+"\"><br>Tie Up</div>"
                                    +"</td>"
                                    +"</tr>"
                                    +"<tr>"
                                    +"<td>"
                                    +"<div><img src=\"file:\\\\"+path+"_design.png" + 
             "\" title=\"Design\" width=\""+objWeave.getIntWeft()*blocksize+"\" height=\""+objWeave.getIntWarp()*blocksize+"\"><br>Design</div>"
                                    +"</td>"
                                    +"<td>"+
                                    "<div><img src=\"file:\\\\"+path+"_peg.png" + 
             "\" title=\"Peg Plan\" width=\""+objWeave.getIntWeft()*blocksize+"\" height=\""+objWeave.getIntWeaveShaft()*blocksize+"\"><br>Peg Plan</div>"
                                    +"</td>"
                                    +"<td>"
                                    +"<div><img src=\"file:\\\\"+path+"_treadles.png" + 
             "\" title=\"Treadling Order\" width=\""+objWeave.getIntWeft()*blocksize+"\" height=\""+objWeave.getIntWeaveTreadles()*blocksize+"\"><br>Treadling Order</div>"
                                    +"</td>"
                                    +"</tr>"
                                    +"</table>";

            
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(htmlFile, true))) {
                bw.write("<table border=0 width=\"100%\">");
                    bw.newLine();
                    bw.write("<tr>");
                        bw.newLine();
                        bw.write("<td><h1>Media Lab Asia</h1></td>");
                        bw.newLine();
                        bw.write("<td><h6 align=right>");
                            bw.newLine();
                            bw.write(objDictionaryAction.getWord("PROJECT")+": Weave_"+objWeave.getStrWeaveID()+"_"+currentDate+"<br>");
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
                bw.write("<tr align=right><th>Weave Category</th><td>"+objWeave.getStrWeaveCategory()+"</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("TYPE")+"</th><td>"+objWeave.getStrWeaveType()+"</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("NAME")+"</th><td>"+objWeave.getStrWeaveName()+"</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("WARP")+"</th><td>"+objWeave.getIntWarp()+"</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("WEFT")+"</th><td>"+objWeave.getIntWeft()+"</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("SHAFT")+"</th><td>"+objWeave.getIntWeaveShaft()+"</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("TRADLE")+"</th><td>"+objWeave.getIntWeaveTreadles()+"</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("PPI")+"</th><td>"+objWeave.getIntPPI()+" / inch</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("EPI")+"</th><td>"+objWeave.getIntEPI()+" / inch</td></tr>");
                bw.newLine();
                bw.newLine();
                bw.newLine();
                bw.write("</table>");                
            bw.newLine();
            bw.write("");
            
            ArrayList<String> lstEntry;
            if(objWeave.getWarpYarn().length>0){

                bw.write("<table border=1 cellspacing=0>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("WARP")+"</th><th>"+objDictionaryAction.getWord("NAME")+"</th><th>"+objDictionaryAction.getWord("YARNTYPE")+"</th><th>"+objDictionaryAction.getWord("YARNREPEAT")+"</th><th>"+objDictionaryAction.getWord("YARNCOUNT")+"</th><th>"+objDictionaryAction.getWord("YARNUNIT")+"</th><th>"+objDictionaryAction.getWord("YARNPLY")+"</th><th>"+objDictionaryAction.getWord("YARNFACTOR")+"</th><th>"+objDictionaryAction.getWord("YARNDIAMETER")+"(mm)</th><th>"+objDictionaryAction.getWord("YARNWEIGHT")+"(kg)</th><th>"+objDictionaryAction.getWord("YARNPRICE")+"(kg)</th><th>"+objDictionaryAction.getWord("YARNTWIST")+"/ inch</th><th>"+objDictionaryAction.getWord("YARNSENCE")+"</th><th>"+objDictionaryAction.getWord("YARNHAIRNESS")+"</th><th>"+objDictionaryAction.getWord("YARNDISTRIBUTION")+"</th><th>"+objDictionaryAction.getWord("YARNCOLOR")+"</th></tr>");
                bw.newLine();
            
                lstEntry = new ArrayList();
                for(int i=0; i<objWeave.getWarpYarn().length; i++){
                    if(lstEntry.size()==0){
                        lstEntry.add(objWeave.getWarpYarn()[i].getStrYarnColor());
                        bw.newLine();
                        bw.write("<tr align=right><td>"+objWeave.getWarpYarn()[i].getStrYarnSymbol()+"</td><td>"+objWeave.getWarpYarn()[i].getStrYarnName()+"</td><td>"+objWeave.getWarpYarn()[i].getStrYarnType()+"</td><td>"+objWeave.getWarpYarn()[i].getIntYarnRepeat()+"</td><td>"+objWeave.getWarpYarn()[i].getIntYarnCount()+"</td><td>"+objWeave.getWarpYarn()[i].getStrYarnCountUnit()+"</td><td>"+objWeave.getWarpYarn()[i].getIntYarnPly()+"</td><td>"+objWeave.getWarpYarn()[i].getIntYarnDFactor()+"</td><td>"+objWeave.getWarpYarn()[i].getDblYarnDiameter()+"</td><td>-</td><td>"+objWeave.getWarpYarn()[i].getDblYarnPrice()+"</td><td>"+objWeave.getWarpYarn()[i].getIntYarnTwist()+"</td><td>"+objWeave.getWarpYarn()[i].getStrYarnTModel()+"</td><td>"+objWeave.getWarpYarn()[i].getIntYarnHairness()+"</td><td>"+objWeave.getWarpYarn()[i].getIntYarnHProbability()+"</td><th bgcolor=\""+objWeave.getWarpYarn()[i].getStrYarnColor()+"\">"+objWeave.getWarpYarn()[i].getStrYarnColor()+"</th></tr>");
                    }
                    //check for redudancy
                    else {                
                        if(!lstEntry.contains(objWeave.getWarpYarn()[i].getStrYarnColor())){
                            lstEntry.add(objWeave.getWarpYarn()[i].getStrYarnColor());
                            bw.newLine();
                            bw.write("<tr align=right><td>"+objWeave.getWarpYarn()[i].getStrYarnSymbol()+"</td><td>"+objWeave.getWarpYarn()[i].getStrYarnName()+"</td><td>"+objWeave.getWarpYarn()[i].getStrYarnType()+"</td><td>"+objWeave.getWarpYarn()[i].getIntYarnRepeat()+"</td><td>"+objWeave.getWarpYarn()[i].getIntYarnCount()+"</td><td>"+objWeave.getWarpYarn()[i].getStrYarnCountUnit()+"</td><td>"+objWeave.getWarpYarn()[i].getIntYarnPly()+"</td><td>"+objWeave.getWarpYarn()[i].getIntYarnDFactor()+"</td><td>"+objWeave.getWarpYarn()[i].getDblYarnDiameter()+"</td><td>-</td><td>"+objWeave.getWarpYarn()[i].getDblYarnPrice()+"</td><td>"+objWeave.getWarpYarn()[i].getIntYarnTwist()+"</td><td>"+objWeave.getWarpYarn()[i].getStrYarnTModel()+"</td><td>"+objWeave.getWarpYarn()[i].getIntYarnHairness()+"</td><td>"+objWeave.getWarpYarn()[i].getIntYarnHProbability()+"</td><th bgcolor=\""+objWeave.getWarpYarn()[i].getStrYarnColor()+"\">"+objWeave.getWarpYarn()[i].getStrYarnColor()+"</th></tr>");
                        }
                    }
                }
                bw.newLine();
                bw.write("</table>");
            }
            
            if(objWeave.getWeftYarn().length>0){
                bw.newLine();
                bw.write("<table border=1 cellspacing=0>");
                    bw.newLine();
                    bw.write("<tr align=right><th>"+objDictionaryAction.getWord("WEFT")+"</th><th>"+objDictionaryAction.getWord("NAME")+"</th><th>"+objDictionaryAction.getWord("YARNTYPE")+"</th><th>"+objDictionaryAction.getWord("YARNREPEAT")+"</th><th>"+objDictionaryAction.getWord("YARNCOUNT")+"</th><th>"+objDictionaryAction.getWord("YARNUNIT")+"</th><th>"+objDictionaryAction.getWord("YARNPLY")+"</th><th>"+objDictionaryAction.getWord("YARNFACTOR")+"</th><th>"+objDictionaryAction.getWord("YARNDIAMETER")+"(mm)</th><th>"+objDictionaryAction.getWord("YARNWEIGHT")+"(kg)</th><th>"+objDictionaryAction.getWord("YARNPRICE")+"(kg)</th><th>"+objDictionaryAction.getWord("YARNTWIST")+"/ inch</th><th>"+objDictionaryAction.getWord("YARNSENCE")+"</th><th>"+objDictionaryAction.getWord("YARNHAIRNESS")+"</th><th>"+objDictionaryAction.getWord("YARNDISTRIBUTION")+"</th><th>"+objDictionaryAction.getWord("YARNCOLOR")+"</th></tr>");
                lstEntry = new ArrayList();
                for(int i=0; i<objWeave.getWeftYarn().length; i++){
                    if(lstEntry.size()==0){
                        lstEntry.add(objWeave.getWeftYarn()[i].getStrYarnColor());
                        bw.newLine();
                        bw.write("<tr align=right><td>"+objWeave.getWeftYarn()[i].getStrYarnSymbol()+"</td><td>"+objWeave.getWeftYarn()[i].getStrYarnName()+"</td><td>"+objWeave.getWeftYarn()[i].getStrYarnType()+"</td><td>"+objWeave.getWeftYarn()[i].getIntYarnRepeat()+"</td><td>"+objWeave.getWeftYarn()[i].getIntYarnCount()+"</td><td>"+objWeave.getWeftYarn()[i].getStrYarnCountUnit()+"</td><td>"+objWeave.getWeftYarn()[i].getIntYarnPly()+"</td><td>"+objWeave.getWeftYarn()[i].getIntYarnDFactor()+"</td><td>"+objWeave.getWeftYarn()[i].getDblYarnDiameter()+"</td><td>-</td><td>"+objWeave.getWeftYarn()[i].getDblYarnPrice()+"</td><td>"+objWeave.getWeftYarn()[i].getIntYarnTwist()+"</td><td>"+objWeave.getWeftYarn()[i].getStrYarnTModel()+"</td><td>"+objWeave.getWeftYarn()[i].getIntYarnHairness()+"</td><td>"+objWeave.getWeftYarn()[i].getIntYarnHProbability()+"</td><th bgcolor=\""+objWeave.getWeftYarn()[i].getStrYarnColor()+"\">"+objWeave.getWeftYarn()[i].getStrYarnColor()+"</th></tr>");
                    }
                    //check for redudancy
                    else {                
                        if(!lstEntry.contains(objWeave.getWeftYarn()[i].getStrYarnColor())){
                            lstEntry.add(objWeave.getWeftYarn()[i].getStrYarnColor());
                            bw.newLine();
                            bw.write("<tr align=right><td>"+objWeave.getWeftYarn()[i].getStrYarnSymbol()+"</td><td>"+objWeave.getWeftYarn()[i].getStrYarnName()+"</td><td>"+objWeave.getWeftYarn()[i].getStrYarnType()+"</td><td>"+objWeave.getWeftYarn()[i].getIntYarnRepeat()+"</td><td>"+objWeave.getWeftYarn()[i].getIntYarnCount()+"</td><td>"+objWeave.getWeftYarn()[i].getStrYarnCountUnit()+"</td><td>"+objWeave.getWeftYarn()[i].getIntYarnPly()+"</td><td>"+objWeave.getWeftYarn()[i].getIntYarnDFactor()+"</td><td>"+objWeave.getWeftYarn()[i].getDblYarnDiameter()+"</td><td>-</td><td>"+objWeave.getWeftYarn()[i].getDblYarnPrice()+"</td><td>"+objWeave.getWeftYarn()[i].getIntYarnTwist()+"</td><td>"+objWeave.getWeftYarn()[i].getStrYarnTModel()+"</td><td>"+objWeave.getWeftYarn()[i].getIntYarnHairness()+"</td><td>"+objWeave.getWeftYarn()[i].getIntYarnHProbability()+"</td><th bgcolor=\""+objWeave.getWeftYarn()[i].getStrYarnColor()+"\">"+objWeave.getWeftYarn()[i].getStrYarnColor()+"</th></tr>");
                        }
                    }
                }
                bw.newLine();
                bw.write("</table>");
            }
            //bw.newLine();
            //bw.write("<br><b>"+objDictionaryAction.getWord("GRAPHVIEW")+"<b><br>");
            bw.newLine();
            bw.newLine();
            bw.write(IMAGE_TEXT);
            bw.newLine();
            bw.write("<br>"+INITIAL_TEXT+"<br>");
            bw.newLine();
            } catch (IOException ex) {
                new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            } catch (Exception ex) {
                new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
            
            ArrayList<File> filesToZip=new ArrayList<>();
            filesToZip.add(designFile);
            filesToZip.add(shaftFile);
            filesToZip.add(treadleFile);
            filesToZip.add(tieupFile);
            filesToZip.add(pegliftFile);
            filesToZip.add(htmlFile);
            String zipFilePath=path+".zip";
            String passwordToZip = ifile.getName()+"Weave_"+currentDate;
            new EncryptZip(zipFilePath, filesToZip, passwordToZip);
            designFile.delete();
            shaftFile.delete();
            treadleFile.delete();
            tieupFile.delete();
            pegliftFile.delete();
            htmlFile.delete();
            
            lblStatus.setText("HTML "+objDictionaryAction.getWord("EXPORTEDTO")+" "+path+".html");
        } catch (IOException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
   private void populateToolKit(){
        if(weaveChildStage!=null){
            weaveChildStage.close();
			isweaveChildStageOn=false;
            weaveChildStage = null;
            System.gc();
        }
        weaveChildStage = new Stage();
        weaveChildStage.initOwner(weaveStage);
        weaveChildStage.initStyle(StageStyle.UTILITY);
        //dialogStage.initModality(Modality.WINDOW_MODAL);
        
        GridPane popup=new GridPane();
        popup.setId("popup");
        popup.setHgap(0);
        popup.setVgap(0);

        Button insertWeftTP = new Button();
        Button deleteWeftTP = new Button();
        Button insertWarpTP = new Button();
        Button deleteWarpTP = new Button();
        Button selectTP = new Button();
        Button copyTP = new Button();
        Button pasteTP = new Button();
        Button clearTP = new Button();
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
        Button normalTP = new Button();
        Button zoomInTP = new Button();
        Button ZoomOutTP = new Button();
        
        insertWeftTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/insert_weft.png"));
        deleteWeftTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/delete_weft.png"));
        insertWarpTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/insert_warp.png"));
        deleteWarpTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/delete_warp.png"));
        selectTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/select.png"));
        copyTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/copy.png"));
        pasteTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/paste.png"));
        clearTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/clear.png"));
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
        normalTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/zoom_normal.png"));
        zoomInTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/zoom_in.png"));
        ZoomOutTP.setGraphic(new ImageView(objConfiguration.getStrColour()+"/zoom_out.png"));
        
        insertWeftTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPINSERTWEFT")));
        deleteWeftTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDELETEWEFT")));
        insertWarpTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPINSERTWARP")));
        deleteWarpTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDELETEWARP")));
        selectTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSELECT")));
        copyTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOPY")));
        pasteTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPASTE")));
        clearTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLEARWEAVE")));
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
        normalTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPZOOMNORMALVIEW")));
        zoomInTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPZOOMINVIEW")));
        ZoomOutTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPZOOMOUTVIEW")));
        
        insertWeftTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        deleteWeftTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        insertWarpTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        deleteWarpTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        selectTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        copyTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        pasteTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        clearTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
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
        normalTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        zoomInTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        ZoomOutTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        
        popup.add(insertWeftTP, 0, 0);
        popup.add(deleteWeftTP, 1, 0);
        popup.add(insertWarpTP, 0, 1);
        popup.add(deleteWarpTP, 1, 1);
        popup.add(selectTP, 0, 2);
        popup.add(copyTP, 1, 2);
        popup.add(pasteTP, 0, 3);
        popup.add(clearTP, 1, 3);
        popup.add(mirrorVerticalTP, 0, 4);
        popup.add(mirrorHorizontalTP, 1, 4);
        popup.add(rotateTP, 0, 5);
        popup.add(rotateAntiTP, 1, 5);
        popup.add(moveRightTP, 0, 6);
        popup.add(moveLeftTP, 1, 6);
        popup.add(moveUpTP, 0, 7);
        popup.add(moveDownTP, 1, 7);
        popup.add(moveRight8TP, 0, 8);
        popup.add(moveLeft8TP, 1, 8);
        popup.add(moveUp8TP, 0, 9);
        popup.add(moveDown8TP, 1, 9);
        popup.add(tiltRightTP, 0, 10);
        popup.add(tiltLeftTP, 1, 10);
        popup.add(tiltUpTP, 0, 11);
        popup.add(tiltDownTP, 1, 11);
        popup.add(inversionTP, 0, 12);
        popup.add(normalTP, 1, 12);
        popup.add(zoomInTP, 0, 13);
        popup.add(ZoomOutTP, 1, 13);
        
        insertWeftTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {                       
                    insertWeftAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Insert Weft",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });  
        deleteWeftTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {                       
                    deleteWeftAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Delete Weft",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        insertWarpTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {                       
                    insertWarpAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Insert Warp",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });  
        deleteWarpTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {                       
                    deleteWarpAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Delete Warp",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });  
        selectTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {                       
                    selectAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Select Weave",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        copyTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {                       
                    copyAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Copy Weave",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        pasteTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {                       
                    pasteAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Paste Weave",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });  
        clearTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {                       
                    clearAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Clear Weave",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });  
        mirrorVerticalTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {                       
                    mirrorVerticalAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Mirror Vertical",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });  
        mirrorHorizontalTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    mirrorHorizontalAction();
                } catch (Exception ex) {
                  new Logging("SEVERE",WeaveView.class.getName(),"Mirror Vertical",ex);
                  lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        }); 
        rotateTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    rotateClockwiseAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Rotate Clock Wise ",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        }); 
        rotateAntiTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    rotateAntiClockwiseAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Anti Rotate Clock Wise ",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        moveRightTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    moveRightAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Move Right",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        moveLeftTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    moveLeftAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Move Left",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        moveUpTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    moveUpAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Move Up",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        moveDownTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    moveDownAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Move Down",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
         moveRight8TP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    moveRight8Action();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Move Right",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        moveLeft8TP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    moveLeft8Action();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Move Left",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        moveUp8TP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    moveUp8Action();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Move Up",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        moveDown8TP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    moveDown8Action();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Move Down",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        tiltRightTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    tiltRightAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Tilt Right ",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        tiltLeftTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    tiltLeftAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Tilt Left ",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        tiltUpTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    tiltUpAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Tilt Up ",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        tiltDownTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    tiltDownAction();
                } catch (Exception ex) {
                   new Logging("SEVERE",WeaveView.class.getName(),"Tilt Down ",ex);
                   lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });         
        inversionTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    inversionAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Inversion ",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        normalTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    ZoomNormalAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Zoom Normal ",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        zoomInTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    zoomInAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Zoom In ",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        ZoomOutTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    zoomOutAction();
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),"Zoom out ",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        
		weaveChildStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                isweaveChildStageOn=false;
                weaveChildStage.close();
            }
        });
        
        Scene scene = new Scene(popup);
        scene.getStylesheets().add(WeaveView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        weaveChildStage.setScene(scene);
        weaveChildStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("OPERATION"));
		isweaveChildStageOn=true;
        weaveChildStage.showAndWait();        
    }

   private void plotColorPalette(){
        paletteColorGP.getChildren().clear();
        paletteColorGP.setVgap(5);
        paletteColorGP.setHgap(5);
        //paletteColorGP.autosize();
        //paletteColorGP.setId("leftPane");
        

        paletteColorGP.add(new Label(objDictionaryAction.getWord("PALETTE")), 0, 0, 1, 1);
        
        final ComboBox paletteCB = new ComboBox();
        paletteCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPALETTE")));
        try{
            Palette objPalette = new Palette();
            objPalette.setObjConfiguration(objConfiguration);
            objPalette.setStrPaletteType("Colour");
            UtilityAction objUtilityAction = new UtilityAction();
            String[][] paletteNames=objUtilityAction.getPaletteName(objPalette);
            if(paletteNames!=null){
                for(String[] s:paletteNames){
                    paletteCB.getItems().add(s[1]);
                }
            }
        } catch(SQLException sqlEx){
            new Logging("SEVERE",WeaveView.class.getName(),"loadPaletteNames() : ", sqlEx);
        }
        paletteColorGP.add(paletteCB, 0, 1, 1, 1);
        
        paletteCB.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                try{
                    UtilityAction objUtilityAction=new UtilityAction();
                    String id=objUtilityAction.getPaletteIdFromName(t1.toString());
                    if(id!=null){
                        Palette objPalette = new Palette();
                        objPalette.setStrPaletteID(id);
                        objPalette.setObjConfiguration(objConfiguration);
                        objUtilityAction=new UtilityAction();
                        objUtilityAction.getPalette(objPalette);
                        if(objPalette.getStrThreadPalette()!=null){
                            objWeave.getObjConfiguration().setColourPalette(objPalette.getStrThreadPalette());
                        }
                    }
                    /*String[] tPalette=objColorPaletteAction.getPaletteFromName(paletteName);
                    if(tPalette!=null)
                        threadPaletes=tPalette;*/
                    populateYarnPalette();
                    loadColorPalettes(); // it sets colorPalates[][] using threadPalettes
                    
                } catch(SQLException sqlEx){
                    new Logging("SEVERE",WeaveView.class.getName(),"loadColorPalette() : ", sqlEx);
                }
            }
        });
        
        editThreadGP = new GridPane();
        editThreadGP.setAlignment(Pos.CENTER);
        editThreadGP.setHgap(5);
        editThreadGP.setVgap(5);
        editThreadGP.setCursor(Cursor.HAND);
        //editThreadGP.getChildren().clear();
        loadColorPalettes(); // it sets colorPalates[][] using threadPalettes
        paletteColorGP.add(editThreadGP, 0, 2, 1, 1);
        
        Button btnSwap = new Button();//objDictionaryAction.getWord("SWAPCOLOR"));
        btnSwap.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSWAPCOLOR")));
        btnSwap.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
        btnSwap.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {  

                Yarn temp;String type, symbol; 
                for(int i =0; i<26; i++){

                    type = objWeave.getObjConfiguration().getYarnPalette()[i].getStrYarnType();
                    symbol = objWeave.getObjConfiguration().getYarnPalette()[i].getStrYarnSymbol();
                    
                    objWeave.getObjConfiguration().getYarnPalette()[i].setStrYarnType(objWeave.getObjConfiguration().getYarnPalette()[i+26].getStrYarnType());
                    objWeave.getObjConfiguration().getYarnPalette()[i].setStrYarnSymbol(objWeave.getObjConfiguration().getYarnPalette()[i+26].getStrYarnSymbol());
                    
                    objWeave.getObjConfiguration().getYarnPalette()[i+26].setStrYarnType(type);
                    objWeave.getObjConfiguration().getYarnPalette()[i+26].setStrYarnSymbol(symbol);
                    
                    temp = objWeave.getObjConfiguration().getYarnPalette()[i];
                    objWeave.getObjConfiguration().getYarnPalette()[i]=objWeave.getObjConfiguration().getYarnPalette()[i+26];
                    objWeave.getObjConfiguration().getYarnPalette()[i+26]=temp;
                }
				for(int i=0; i<52; i++){
                    objWeave.getObjConfiguration().getColourPalette()[i]=objWeave.getObjConfiguration().getYarnPalette()[i].getStrYarnColor().substring(1); // removing #
                }
                populateColorPalette();
                loadColorPalettes();
            }
        });
        paletteColorGP.add(btnSwap, 0, 3, 1, 1);
        
        Button btnWarpWeft = new Button();//objDictionaryAction.getWord("WARPTOWEFT"));
        btnWarpWeft.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWARPTOWEFT")));
        btnWarpWeft.setGraphic(new ImageView(objConfiguration.getStrColour()+"/move_right.png"));
        btnWarpWeft.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {  
                String type, symbol;
                for(int i =0; i<26; i++){
                    Yarn objYarn = objConfiguration.getYarnPalette()[i+26];

                    objYarn.setStrYarnType(objConfiguration.getYarnPalette()[i].getStrYarnType());
                    objYarn.setStrYarnName(objConfiguration.getYarnPalette()[i+26].getStrYarnName());
                    objYarn.setStrYarnColor(objConfiguration.getYarnPalette()[i].getStrYarnColor());
                    objYarn.setIntYarnRepeat(objConfiguration.getYarnPalette()[i].getIntYarnRepeat());
                    objYarn.setStrYarnSymbol(objConfiguration.getYarnPalette()[i+26].getStrYarnSymbol());
                    objYarn.setIntYarnCount(objConfiguration.getYarnPalette()[i].getIntYarnCount());
                    objYarn.setStrYarnCountUnit(objConfiguration.getYarnPalette()[i].getStrYarnCountUnit());
                    objYarn.setIntYarnPly(objConfiguration.getYarnPalette()[i].getIntYarnPly());
                    objYarn.setIntYarnDFactor(objConfiguration.getYarnPalette()[i].getIntYarnDFactor());
                    objYarn.setDblYarnDiameter(objConfiguration.getYarnPalette()[i].getDblYarnDiameter());
                    objYarn.setIntYarnTwist(objConfiguration.getYarnPalette()[i].getIntYarnTwist());
                    objYarn.setStrYarnTModel(objConfiguration.getYarnPalette()[i].getStrYarnTModel());
                    objYarn.setIntYarnHairness(objConfiguration.getYarnPalette()[i].getIntYarnHairness());
                    objYarn.setIntYarnHProbability(objConfiguration.getYarnPalette()[i].getIntYarnHProbability());
                    objYarn.setDblYarnPrice(objConfiguration.getYarnPalette()[i].getDblYarnPrice());
                    objYarn.setStrYarnAccess(objConfiguration.getYarnPalette()[i].getStrYarnAccess());
                    objYarn.setStrYarnUser(objConfiguration.getYarnPalette()[i].getStrYarnUser());
                    objYarn.setStrYarnDate(objConfiguration.getYarnPalette()[i].getStrYarnDate());
                    
                    objConfiguration.getYarnPalette()[i+26]=objYarn;
                }
				for(int i=0; i<52; i++){
                    objWeave.getObjConfiguration().getColourPalette()[i]=objWeave.getObjConfiguration().getYarnPalette()[i].getStrYarnColor().substring(1); // removing #
                }
                populateColorPalette();
                loadColorPalettes();
            }
        });
        paletteColorGP.add(btnWarpWeft, 0, 4, 1, 1);
        
        Button btnWeftWarp = new Button();//objDictionaryAction.getWord("WEFTTOWARP"));
        btnWeftWarp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWEFTTOWARP")));
        btnWeftWarp.setGraphic(new ImageView(objConfiguration.getStrColour()+"/move_left.png"));
        btnWeftWarp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {  
                String type, symbol;
                for(int i =0; i<26; i++){
                    Yarn objYarn = objConfiguration.getYarnPalette()[i];

                    objYarn.setStrYarnType(objConfiguration.getYarnPalette()[i+26].getStrYarnType());
                    objYarn.setStrYarnName(objConfiguration.getYarnPalette()[i].getStrYarnName());
                    objYarn.setStrYarnColor(objConfiguration.getYarnPalette()[i+26].getStrYarnColor());
                    objYarn.setIntYarnRepeat(objConfiguration.getYarnPalette()[i+26].getIntYarnRepeat());
                    objYarn.setStrYarnSymbol(objConfiguration.getYarnPalette()[i].getStrYarnSymbol());
                    objYarn.setIntYarnCount(objConfiguration.getYarnPalette()[i+26].getIntYarnCount());
                    objYarn.setStrYarnCountUnit(objConfiguration.getYarnPalette()[i+26].getStrYarnCountUnit());
                    objYarn.setIntYarnPly(objConfiguration.getYarnPalette()[i+26].getIntYarnPly());
                    objYarn.setIntYarnDFactor(objConfiguration.getYarnPalette()[i+26].getIntYarnDFactor());
                    objYarn.setDblYarnDiameter(objConfiguration.getYarnPalette()[i+26].getDblYarnDiameter());
                    objYarn.setIntYarnTwist(objConfiguration.getYarnPalette()[i+26].getIntYarnTwist());
                    objYarn.setStrYarnTModel(objConfiguration.getYarnPalette()[i+26].getStrYarnTModel());
                    objYarn.setIntYarnHairness(objConfiguration.getYarnPalette()[i+26].getIntYarnHairness());
                    objYarn.setIntYarnHProbability(objConfiguration.getYarnPalette()[i+26].getIntYarnHProbability());
                    objYarn.setDblYarnPrice(objConfiguration.getYarnPalette()[i+26].getDblYarnPrice());
                    objYarn.setStrYarnAccess(objConfiguration.getYarnPalette()[i+26].getStrYarnAccess());
                    objYarn.setStrYarnUser(objConfiguration.getYarnPalette()[i+26].getStrYarnUser());
                    objYarn.setStrYarnDate(objConfiguration.getYarnPalette()[i+26].getStrYarnDate());
                    
                    objConfiguration.getYarnPalette()[i]=objYarn;
                }
				for(int i=0; i<52; i++){
                    objWeave.getObjConfiguration().getColourPalette()[i]=objWeave.getObjConfiguration().getYarnPalette()[i].getStrYarnColor().substring(1); // removing #
                }
                populateColorPalette();
                loadColorPalettes();
            }
        });
        paletteColorGP.add(btnWeftWarp, 0, 5, 1, 1);
        
    }
   
    private void populateYarnPalette(){
        for(int i=0; i<52; i++){
            objWeave.getObjConfiguration().getYarnPalette()[i].setStrYarnColor("#"+objWeave.getObjConfiguration().getColourPalette()[i]);
        }
    }
    private void populateColorPalette(){
        /*for(int i=0; i<52; i++){
            objWeave.getObjConfiguration().getColourPalette()[i]=objWeave.getObjConfiguration().getYarnPalette()[i].getStrYarnColor().substring(1, 7);
        }*/
    }

   private void loadColorPalettes(){

        //objWeave.getObjConfiguration().strThreadPalettes = objWeave.getObjConfiguration().strThreadPalettes;
        String[][] colorPaletes = new String[52][2];
        colorPaletes[0][0]="A";
        colorPaletes[0][1]=objWeave.getObjConfiguration().getColourPalette()[0];
        colorPaletes[1][0]="B";
        colorPaletes[1][1]=objWeave.getObjConfiguration().getColourPalette()[1];
        colorPaletes[2][0]="C";
        colorPaletes[2][1]=objWeave.getObjConfiguration().getColourPalette()[2];
        colorPaletes[3][0]="D";
        colorPaletes[3][1]=objWeave.getObjConfiguration().getColourPalette()[3];
        colorPaletes[4][0]="E";
        colorPaletes[4][1]=objWeave.getObjConfiguration().getColourPalette()[4];
        colorPaletes[5][0]="F";
        colorPaletes[5][1]=objWeave.getObjConfiguration().getColourPalette()[5];
        colorPaletes[6][0]="G";
        colorPaletes[6][1]=objWeave.getObjConfiguration().getColourPalette()[6];
        colorPaletes[7][0]="H";
        colorPaletes[7][1]=objWeave.getObjConfiguration().getColourPalette()[7];
        colorPaletes[8][0]="I";
        colorPaletes[8][1]=objWeave.getObjConfiguration().getColourPalette()[8];
        colorPaletes[9][0]="J";
        colorPaletes[9][1]=objWeave.getObjConfiguration().getColourPalette()[9];
        colorPaletes[10][0]="K";
        colorPaletes[10][1]=objWeave.getObjConfiguration().getColourPalette()[10];
        colorPaletes[11][0]="L";
        colorPaletes[11][1]=objWeave.getObjConfiguration().getColourPalette()[11];
        colorPaletes[12][0]="M";
        colorPaletes[12][1]=objWeave.getObjConfiguration().getColourPalette()[12];
        colorPaletes[13][0]="N";
        colorPaletes[13][1]=objWeave.getObjConfiguration().getColourPalette()[13];
        colorPaletes[14][0]="O";
        colorPaletes[14][1]=objWeave.getObjConfiguration().getColourPalette()[14];
        colorPaletes[15][0]="P";
        colorPaletes[15][1]=objWeave.getObjConfiguration().getColourPalette()[15];
        colorPaletes[16][0]="Q";
        colorPaletes[16][1]=objWeave.getObjConfiguration().getColourPalette()[16];
        colorPaletes[17][0]="R";
        colorPaletes[17][1]=objWeave.getObjConfiguration().getColourPalette()[17];
        colorPaletes[18][0]="S";
        colorPaletes[18][1]=objWeave.getObjConfiguration().getColourPalette()[18];
        colorPaletes[19][0]="T";
        colorPaletes[19][1]=objWeave.getObjConfiguration().getColourPalette()[19];
        colorPaletes[20][0]="U";
        colorPaletes[20][1]=objWeave.getObjConfiguration().getColourPalette()[20];
        colorPaletes[21][0]="V";
        colorPaletes[21][1]=objWeave.getObjConfiguration().getColourPalette()[21];
        colorPaletes[22][0]="W";
        colorPaletes[22][1]=objWeave.getObjConfiguration().getColourPalette()[22];
        colorPaletes[23][0]="X";
        colorPaletes[23][1]=objWeave.getObjConfiguration().getColourPalette()[23];
        colorPaletes[24][0]="Y";
        colorPaletes[24][1]=objWeave.getObjConfiguration().getColourPalette()[24];
        colorPaletes[25][0]="Z";
        colorPaletes[25][1]=objWeave.getObjConfiguration().getColourPalette()[25];
        colorPaletes[26][0]="a";
        colorPaletes[26][1]=objWeave.getObjConfiguration().getColourPalette()[26];
        colorPaletes[27][0]="b";
        colorPaletes[27][1]=objWeave.getObjConfiguration().getColourPalette()[27];
        colorPaletes[28][0]="c";
        colorPaletes[28][1]=objWeave.getObjConfiguration().getColourPalette()[28];
        colorPaletes[29][0]="d";
        colorPaletes[29][1]=objWeave.getObjConfiguration().getColourPalette()[29];
        colorPaletes[30][0]="e";
        colorPaletes[30][1]=objWeave.getObjConfiguration().getColourPalette()[30];
        colorPaletes[31][0]="f";
        colorPaletes[31][1]=objWeave.getObjConfiguration().getColourPalette()[31];
        colorPaletes[32][0]="g";
        colorPaletes[32][1]=objWeave.getObjConfiguration().getColourPalette()[32];
        colorPaletes[33][0]="h";
        colorPaletes[33][1]=objWeave.getObjConfiguration().getColourPalette()[33];
        colorPaletes[34][0]="i";
        colorPaletes[34][1]=objWeave.getObjConfiguration().getColourPalette()[34];
        colorPaletes[35][0]="j";
        colorPaletes[35][1]=objWeave.getObjConfiguration().getColourPalette()[35];
        colorPaletes[36][0]="k";
        colorPaletes[36][1]=objWeave.getObjConfiguration().getColourPalette()[36];
        colorPaletes[37][0]="l";
        colorPaletes[37][1]=objWeave.getObjConfiguration().getColourPalette()[37];
        colorPaletes[38][0]="m";
        colorPaletes[38][1]=objWeave.getObjConfiguration().getColourPalette()[38];
        colorPaletes[39][0]="n";
        colorPaletes[39][1]=objWeave.getObjConfiguration().getColourPalette()[39];
        colorPaletes[40][0]="o";
        colorPaletes[40][1]=objWeave.getObjConfiguration().getColourPalette()[40];
        colorPaletes[41][0]="p";
        colorPaletes[41][1]=objWeave.getObjConfiguration().getColourPalette()[41];
        colorPaletes[42][0]="q";
        colorPaletes[42][1]=objWeave.getObjConfiguration().getColourPalette()[42];
        colorPaletes[43][0]="r";
        colorPaletes[43][1]=objWeave.getObjConfiguration().getColourPalette()[43];
        colorPaletes[44][0]="s";
        colorPaletes[44][1]=objWeave.getObjConfiguration().getColourPalette()[44];
        colorPaletes[45][0]="t";
        colorPaletes[45][1]=objWeave.getObjConfiguration().getColourPalette()[45];
        colorPaletes[46][0]="u";
        colorPaletes[46][1]=objWeave.getObjConfiguration().getColourPalette()[46];
        colorPaletes[47][0]="v";
        colorPaletes[47][1]=objWeave.getObjConfiguration().getColourPalette()[47];
        colorPaletes[48][0]="w";
        colorPaletes[48][1]=objWeave.getObjConfiguration().getColourPalette()[48];
        colorPaletes[49][0]="x";
        colorPaletes[49][1]=objWeave.getObjConfiguration().getColourPalette()[49];
        colorPaletes[50][0]="y";
        colorPaletes[50][1]=objWeave.getObjConfiguration().getColourPalette()[50];
        colorPaletes[51][0]="z";
        colorPaletes[51][1]=objWeave.getObjConfiguration().getColourPalette()[51];

        editThreadGP.getChildren().clear();
        editThreadGP.add(new Label(objDictionaryAction.getWord("WARP")), 0, 0, 2, 1);
        editThreadGP.add(new Label(objDictionaryAction.getWord("WEFT")), 3, 0, 2, 1);
        
        Separator sepVertFabric = new Separator();
        sepVertFabric.setOrientation(Orientation.VERTICAL);
        sepVertFabric.setValignment(VPos.CENTER);
        sepVertFabric.setPrefHeight(80);
        GridPane.setConstraints(sepVertFabric, 2, 0);
        GridPane.setRowSpan(sepVertFabric, 14);
        editThreadGP.getChildren().add(sepVertFabric);
        

        for(int i=0; i<(int)objWeave.getObjConfiguration().getColourPalette().length; i++){
            final Label lblC= new Label(colorPaletes[i][0]);
            lblC.setUserData(i);
            lblC.setPrefSize(boxSize,boxSize);
            lblC.setStyle("-fx-background-color: #"+objWeave.getObjConfiguration().getColourPalette()[i]+"; -fx-font-size: 10; -fx-width:25px; -fx-height:25px;-fx-border-width: 1;  -fx-border-color: black;");
            if((byte)((i/26)+1)==2){
                lblC.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getClickCount() >1) {
                            editYarnPalette();
                        } else {
                            lblC.setText(lblC.getText().trim().substring(0,1)+"*");
                            String strWeftColor = lblC.getStyle().substring(lblC.getStyle().lastIndexOf("-fx-background-color:")+21,lblC.getStyle().indexOf(";")).trim();
                            intWeftColor = Integer.parseInt(lblC.getUserData().toString());
                        }
                   }
               });
            }else if((byte)((i/26)+1)==1){
                lblC.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getClickCount() >1) {
                            editYarnPalette();
                        } else {
                            lblC.setText(lblC.getText().trim().substring(0,1)+"*");
                            String strWarpColor = lblC.getStyle().substring(lblC.getStyle().lastIndexOf("-fx-background-color:")+21,lblC.getStyle().indexOf(";")).trim();
                            intWarpColor = Integer.parseInt(lblC.getUserData().toString());

                       }
                    }
               });
            }
            int colp = (i>25)?(i/13)+1:(i/13);
            editThreadGP.add(lblC, colp, (i%13)+1);
        }
   }

   public void editYarn(){

        objWeave.getObjConfiguration().setWarpYarn(objWeave.getWarpYarn());
        objWeave.getObjConfiguration().setWeftYarn(objWeave.getWeftYarn());
        objWeave.getObjConfiguration().setWarpExtraYarn(null);
        objWeave.getObjConfiguration().setWeftExtraYarn(null);
        objWeave.getObjConfiguration().setIntExtraWeft(0);
        objWeave.getObjConfiguration().setIntExtraWarp(0);
        
        YarnView objYarnView = new YarnView(objWeave.getObjConfiguration());


        objWeave.setWarpYarn(objWeave.getObjConfiguration().getWarpYarn());
        objWeave.setWeftYarn(objWeave.getObjConfiguration().getWeftYarn());
        plotWarpColor();
        plotWeftColor();
        populateYarnPalette();
        loadColorPalettes();
    }
    
   public void editYarnPalette(){
        YarnPaletteView objYarnPaletteView=new YarnPaletteView(objWeave.getObjConfiguration());
        
        populateColorPalette();
        loadColorPalettes();
        for(int i=0; i<objWeave.getWarpYarn().length; i++){
            for(int j=0; j<26; j++)
                if(objWeave.getWarpYarn()[i].getStrYarnSymbol().equals(objWeave.getObjConfiguration().getYarnPalette()[j].getStrYarnSymbol()))
                    objWeave.getWarpYarn()[i]=objWeave.getObjConfiguration().getYarnPalette()[j];
        }
        for(int i=0; i<objWeave.getWeftYarn().length; i++){
            for(int j=26; j<52; j++)
                if(objWeave.getWeftYarn()[i].getStrYarnSymbol().equals(objWeave.getObjConfiguration().getYarnPalette()[j].getStrYarnSymbol()))
                    objWeave.getWeftYarn()[i]=objWeave.getObjConfiguration().getYarnPalette()[j];
        }
        plotWarpColor();
        plotWeftColor();
    }
    
   public void populateProperties(){
        final Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        GridPane root = new GridPane();
        Scene scene = new Scene(root, 300, 300, Color.WHITE);
        scene.getStylesheets().add(WeaveView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));
        
        TabPane tabPane=new TabPane();
        Tab weaveTab=new Tab();
        Tab fabricTab=new Tab();
        
        weaveTab.setClosable(false);
        fabricTab.setClosable(false);
        
        weaveTab.setText(objDictionaryAction.getWord("WEAVE"));
        fabricTab.setText(objDictionaryAction.getWord("FABRIC"));
        
        weaveTab.setTooltip(new Tooltip(objDictionaryAction.getWord("WEAVE")));
        fabricTab.setTooltip(new Tooltip(objDictionaryAction.getWord("FABRIC")));
        
        GridPane fabricGP=new GridPane();
        GridPane popup=new GridPane();
        popup.setId("popup");
        popup.setAlignment(Pos.CENTER);
        popup.setHgap(10);
        popup.setVgap(10);
        popup.setPadding(new Insets(25, 25, 25, 25));
        fabricGP.setId("popup");
        fabricGP.setAlignment(Pos.CENTER);
        fabricGP.setHgap(10);
        fabricGP.setVgap(10);
        fabricGP.setPadding(new Insets(25, 25, 25, 25));
        
        Label lblWarp = new Label(objDictionaryAction.getWord("WARP"));
        lblWarp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWARP")));
        lblWarp.setStyle("-fx-font-size: 14px;");
        popup.add(lblWarp, 0, 0);
        final TextField txtWarp = new TextField(){
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
        txtWarp.setPromptText(objDictionaryAction.getWord("TOOLTIPWARP"));
        txtWarp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWARP")));
        if(objWeave!=null)
            txtWarp.setText(String.valueOf(objWeave.getIntWarp()));
        popup.add(txtWarp, 1, 0);
        
        Label lblWeft = new Label(objDictionaryAction.getWord("WEFT"));
        lblWeft.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWEFT")));
        lblWeft.setStyle("-fx-font-size: 14px;");
        popup.add(lblWeft, 0, 1);
        final TextField txtWeft = new TextField(){
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
        txtWeft.setPromptText(objDictionaryAction.getWord("TOOLTIPWEFT"));
        txtWeft.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWEFT")));
        if(objWeave!=null)
            txtWeft.setText(String.valueOf(objWeave.getIntWeft()));
        popup.add(txtWeft, 1, 1);
        
        Label lblShaft = new Label(objDictionaryAction.getWord("SHAFT"));
        lblShaft.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSHAFT")));
        lblShaft.setStyle("-fx-font-size: 14px;");
        popup.add(lblShaft, 0, 2);
        final TextField txtShaft = new TextField(){
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
        txtShaft.setPromptText(objDictionaryAction.getWord("TOOLTIPSHAFT"));
        txtShaft.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSHAFT")));
        if(objWeave!=null)
            txtShaft.setText(String.valueOf(objWeave.getIntWeaveShaft()));
        popup.add(txtShaft, 1, 2);
        
        Label lblTreadles = new Label(objDictionaryAction.getWord("TRADLE"));
        lblTreadles.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTRADLE")));
        lblTreadles.setStyle("-fx-font-size: 14px;");
        popup.add(lblTreadles, 0, 3);
        final TextField txtTreadles = new TextField(){
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
        txtTreadles.setPromptText(objDictionaryAction.getWord("TOOLTIPTRADLE"));
        txtTreadles.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTRADLE")));
        if(objWeave!=null)
            txtTreadles.setText(String.valueOf(objWeave.getIntWeaveTreadles()));
        popup.add(txtTreadles, 1, 3);
        
        Label lblWeaveType = new Label(objDictionaryAction.getWord("TYPE"));
        lblWeaveType.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTYPE")));
        lblWeaveType.setStyle("-fx-font-size: 14px;");
        popup.add(lblWeaveType, 0, 4);
        final ComboBox weaveTypeCB = new ComboBox();
        weaveTypeCB.getItems().addAll("plain","twill","satin","basket","sateen","other");
        weaveTypeCB.setValue(objWeave.getStrWeaveType());
        weaveTypeCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTRADLE")));
        popup.add(weaveTypeCB, 1, 4);
        
        // fabric tab
        Label lblWarpDensity = new Label(objDictionaryAction.getWord("EPI"));
        lblWarpDensity.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEPI")));
        lblWarpDensity.setStyle("-fx-font-size: 14px;");
        fabricGP.add(lblWarpDensity, 0, 0);
        final TextField txtWarpDensity = new TextField(){
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
        txtWarpDensity.setPromptText(objDictionaryAction.getWord("TOOLTIPEPI"));
        txtWarpDensity.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEPI")));
        if(objWeave!=null)
            txtWarpDensity.setText(String.valueOf(objWeave.getIntEPI()));
        fabricGP.add(txtWarpDensity, 1, 0);
        
        Label lblWeftDensity = new Label(objDictionaryAction.getWord("PPI"));
        lblWeftDensity.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPPI")));
        lblWeftDensity.setStyle("-fx-font-size: 14px;");
        fabricGP.add(lblWeftDensity, 0, 1);
        final TextField txtWeftDensity = new TextField(){
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
        txtWeftDensity.setPromptText(objDictionaryAction.getWord("TOOLTIPPPI"));
        txtWeftDensity.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPPI")));
        if(objWeave!=null)
            txtWeftDensity.setText(String.valueOf(objWeave.getIntPPI()));
        fabricGP.add(txtWeftDensity, 1, 1);
        
        Label lblWarpSelvage = new Label(objDictionaryAction.getWord("WARP")+" "+objDictionaryAction.getWord("SELVAGE"));
        lblWarpSelvage.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSELVAGE")));
        lblWarpSelvage.setStyle("-fx-font-size: 14px;");
        fabricGP.add(lblWarpSelvage, 0, 2);
        final TextField txtWarpSelvage = new TextField(){
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
        txtWarpSelvage.setPromptText(objDictionaryAction.getWord("TOOLTIPSELVAGE"));
        txtWarpSelvage.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSELVAGE")));
        if(objWeave!=null)
            txtWarpSelvage.setText("0"/*String.valueOf(objWeave.getObjConfiguration().getIntWarpSelvage())*/);
        fabricGP.add(txtWarpSelvage, 1, 2);
        
        Label lblWeftSelvage = new Label(objDictionaryAction.getWord("WEFT")+" "+objDictionaryAction.getWord("SELVAGE"));
        lblWeftSelvage.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSELVAGE")));
        lblWeftSelvage.setStyle("-fx-font-size: 14px;");
        fabricGP.add(lblWeftSelvage, 0, 3);
        final TextField txtWeftSelvage = new TextField(){
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
        txtWeftSelvage.setPromptText(objDictionaryAction.getWord("TOOLTIPSELVAGE"));
        txtWeftSelvage.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSELVAGE")));
        if(objWeave!=null)
            txtWeftSelvage.setText("0"/*String.valueOf(objWeave.getObjConfiguration().getIntWeftSelvage())*/);
        fabricGP.add(txtWeftSelvage, 1, 3);
        
        Label lblWarpShrinkage = new Label(objDictionaryAction.getWord("WARP")+" "+objDictionaryAction.getWord("SHRINKAGE"));
        lblWarpShrinkage.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSHRINKAGE")));
        lblWarpShrinkage.setStyle("-fx-font-size: 14px;");
        fabricGP.add(lblWarpShrinkage, 0, 4);
        final TextField txtWarpShrinkage = new TextField(){
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
        txtWarpShrinkage.setPromptText(objDictionaryAction.getWord("TOOLTIPSHRINKAGE"));
        txtWarpShrinkage.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSHRINKAGE")));
        if(objWeave!=null)
            txtWarpShrinkage.setText(String.valueOf(objWeave.getObjConfiguration().getIntWarpCrimp()));
        fabricGP.add(txtWarpShrinkage, 1, 4);
        
        Label lblWeftShrinkage = new Label(objDictionaryAction.getWord("WEFT")+" "+objDictionaryAction.getWord("SHRINKAGE"));
        lblWeftShrinkage.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSHRINKAGE")));
        lblWeftShrinkage.setStyle("-fx-font-size: 14px;");
        fabricGP.add(lblWeftShrinkage, 0, 5);
        final TextField txtWeftShrinkage = new TextField(){
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
        txtWeftShrinkage.setPromptText(objDictionaryAction.getWord("TOOLTIPSHRINKAGE"));
        txtWeftShrinkage.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSHRINKAGE")));
        if(objWeave!=null)
            txtWeftShrinkage.setText(String.valueOf(objWeave.getObjConfiguration().getIntWeftCrimp()));
        fabricGP.add(txtWeftShrinkage, 1, 5);
        //
        weaveTab.setContent(popup);
        fabricTab.setContent(fabricGP);
        
        tabPane.getTabs().addAll(weaveTab, fabricTab);
        
        root.add(tabPane, 0, 0, 2, 1);
        
        Button btnOK = new Button(objDictionaryAction.getWord("SUBMIT"));
        btnOK.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSUBMIT")));
        btnOK.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
        btnOK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {  
                if(!(txtWarpDensity.getText().length()==0))
                    objWeave.setIntEPI(Integer.parseInt(txtWarpDensity.getText()));
                if(!(txtWarpDensity.getText().length()==0))
                    objWeave.setIntPPI(Integer.parseInt(txtWeftDensity.getText()));
                if(!(txtWarp.getText().length()==0))
                    objWeave.setIntWarp(Integer.parseInt(txtWarp.getText()));
                if(!(txtWeft.getText().length()==0))
                    objWeave.setIntWeft(Integer.parseInt(txtWeft.getText()));
                if(!(txtShaft.getText().length()==0)){
                    objWeave.setIntWeaveShaft(Integer.parseInt(txtShaft.getText()));
                }
                if(!(txtTreadles.getText().length()==0)){
                    objWeave.setIntWeaveTreadles(Integer.parseInt(txtTreadles.getText()));
                }
                updateActiveDesignGrid(objWeave.getIntWarp(), objWeave.getIntWeft());
                refreshDesignImage();
                initObjWeave(true);
                populateNplot();
                dialogStage.close();
            }
        });
        root.add(btnOK, 0, 1, 1, 1);
        Button btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
        btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
        btnCancel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {  
                dialogStage.close();
            }
        });
        root.add(btnCancel, 1, 1, 1, 1);
        
        dialogStage.setScene(scene);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WEAVINGDETAILS"));
        dialogStage.showAndWait();
    }
   
   public void selectionBoxArea(final Label lblC,final int r,final int col){
         lblC.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
           }
        });
        lblC.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                Dragboard db = lblC.startDragAndDrop(TransferMode.ANY);
                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(lblC.getStyle().substring(lblC.getStyle().lastIndexOf("-fx-background-color:")+21,lblC.getStyle().indexOf(";")).trim());
                db.setContent(content);
                for(int i=initial_row,k=0;i<=final_row;i++){
                    for(int j=initial_col;j<=final_col;j++){
                        int m=(i*objWeave.getIntWarp())+j;
                        designGP.getChildren().get(m).setStyle("-fx-border-color: black;-fx-border-width: 1;");     
                    }
                }     
                initial_row=r;
                initial_col=col;
                event.consume();
            }
        });
        lblC.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                /* accept it only if it is not dragged */ 
                if (event.getGestureSource() != lblC && event.getDragboard().hasString()) {
                     event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                      }
                event.consume();
            }
        });
        lblC.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
            /* the drag-and-drop gesture entered the target */
            /* show to the user that it is an actual gesture target */
                 if (event.getGestureSource() != lblC && event.getDragboard().hasString()) {
                 }
                     event.consume();
            }
        });
        lblC.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
                  event.consume();
            }
        });
        lblC.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                try {
                    /* data dropped */
                    /* if there is a string data on dragboard, read it and use it */
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    /* let the source know whether the string was successfully
                    * transferred and used */
                    final_row=r;
                    final_col=col;
                    int size_x=initial_row-final_row;
                    int size_y=final_col-initial_col;        
          
                    final_row=initial_row-size_x;
                    final_col=initial_col+size_y;
                     int m=0,p=0;
                     for(int i=initial_row,k=0;i<=final_row;i++){
                        for(int j=initial_col;j<=final_col;j++,m++){
                            m=(i*objWeave.getIntWarp())+j;
                            if(objWeave.getDesignMatrix()[i][j]==0)
                                designGP.getChildren().get(m).setStyle("-fx-background-color: #FFFFFF;-fx-border-width: 1;  -fx-border-color: red");
                            else
                                designGP.getChildren().get(m).setStyle("-fx-background-color: #000000;-fx-border-width: 1;  -fx-border-color: red");
                        }
                    }
                    event.setDropCompleted(success);
                    event.consume();
                } catch (Exception ex) {
                   new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
                   lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
             }
        });
        lblC.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag and drop gesture ended */
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    Dragboard db = event.getDragboard();
                }
                event.consume();
            }
        });
    }
   public void selectionLineArea(final Label lblC,final int row,final int col){
        lblC.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
           }
        });
        lblC.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                byte [][]designPaste=new byte[objWeave.getIntWeft()][objWeave.getIntWarp()];
                for(int i=0;i<objWeave.getIntWeft();i++){
                    for(int j=0;j<objWeave.getIntWarp();j++){
                       designPaste[i][j]=objWeave.getDesignMatrix()[i][j];   
                    }
               }
             
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                Dragboard db = lblC.startDragAndDrop(TransferMode.ANY);
                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                initial_row=row;
                initial_col=col;
                if(event.isSecondaryButtonDown()){
                    lblC.setStyle("-fx-background-color: #ffffff;-fx-border-width: 1;  -fx-border-color: black");
                    lblC.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                    objWeave.getDesignMatrix()[row][col]=0;
                    content.putString("isSecondaryButtonDown");
                }else{
                    lblC.setStyle("-fx-background-color: #000000;-fx-border-width: 1;  -fx-border-color: black");
                    lblC.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                    objWeave.getDesignMatrix()[row][col]=1;
                    content.putString("isPrimaryButtonDown");
                }
                db.setContent(content);
                event.consume();
            }
        });
        lblC.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                /* accept it only if it is not dragged */ 
                if (event.getGestureSource() != lblC && event.getDragboard().hasString()) {
                    if(event.getDragboard().getString().equalsIgnoreCase("isSecondaryButtonDown")){
                        lblC.setStyle("-fx-background-color: #ffffff;-fx-border-width: 1;  -fx-border-color: black");
                        lblC.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                        objWeave.getDesignMatrix()[row][col]=0;
                    }else{
                        lblC.setStyle("-fx-background-color: #000000;-fx-border-width: 1;  -fx-border-color: black");
                        lblC.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                        objWeave.getDesignMatrix()[row][col]=1;
                    }
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });
        lblC.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
            /* the drag-and-drop gesture entered the target */
            /* show to the user that it is an actual gesture target */
                 if (event.getGestureSource() != lblC && event.getDragboard().hasString()) {
                 }
                     event.consume();
            }
        });
        lblC.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
                  event.consume();
            }
        });
        lblC.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                try {
                    /* data dropped */
                    /* if there is a string data on dragboard, read it and use it */
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    /* let the source know whether the string was successfully
                    * transferred and used */
                    if(event.getDragboard().getString().equalsIgnoreCase("isSecondaryButtonDown")){
                        lblC.setStyle("-fx-background-color: #ffffff;-fx-border-width: 1;  -fx-border-color: black");
                        lblC.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                        objWeave.getDesignMatrix()[row][col]=0;
                    }else{
                        lblC.setStyle("-fx-background-color: #000000;-fx-border-width: 1;  -fx-border-color: black");
                        lblC.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                        objWeave.getDesignMatrix()[row][col]=1;
                    }
                    final_row=row;
                    final_col=col;
                    populateNplot();
                    
                    event.setDropCompleted(success);
                    event.consume();
                } catch (Exception ex) {
                   new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
                   lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
             }
        });
        lblC.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag and drop gesture ended */
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    Dragboard db = event.getDragboard();
                }
                event.consume();
            }
        });
    }
 
   private void selectAction(){
        try {
             lblStatus.setText(objDictionaryAction.getWord("ACTIONSELECT"));
             isSelectionMode=!isSelectionMode;
             if(isSelectionMode)
                 scene.setCursor(Cursor.HAND);
             else
                 scene.setCursor(Cursor.DEFAULT);
             /*if(isDragBox==0){
                 isDragBox=1;
                 scene.setCursor(Cursor.HAND);
             }else{
                 isDragBox=0;
                 scene.setCursor(Cursor.DEFAULT);
             }
             designGP.getChildren().clear();
             populateDplot();*/
         } catch (Exception ex) {
           new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
           lblStatus.setText(objDictionaryAction.getWord("ERROR"));
         }
    }
    private void copyAction(){
        try {
            if(initial_row==final_row && initial_col==final_col){
                lblStatus.setText(objDictionaryAction.getWord("NOREGION"));
                new MessageView("alert", "alert", objDictionaryAction.getWord("NOREGION"));                        
            } else{                   
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCOPY"));
                objWeaveAction.copy(objWeave,initial_row,initial_col,final_row,final_col);
                //populateNplot();
                //populateDplot();
            }
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveView.class.getName(),"Move Right",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
private void pasteAction(){
    try {
        if(objWeave.getClipMatrix()==null){
            lblStatus.setText(objDictionaryAction.getWord("NOREGION"));
            new MessageView("alert", "alert", objDictionaryAction.getWord("NOREGION"));
        } else{ 
            lblStatus.setText(objDictionaryAction.getWord("ACTIONPASTE"));
            objWeaveAction.paste(objWeave,current_row,current_col);
            populateNplot();
            populateDplot();
            isSelectionMode=false;
            scene.setCursor(Cursor.DEFAULT);
        }
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"Move Right",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void mirrorVerticalAction(){
    try {               
        if(initial_row==final_row && initial_col==final_col){
            lblStatus.setText(objDictionaryAction.getWord("NOREGION"));
            new MessageView("alert", "alert", objDictionaryAction.getWord("NOREGION"));
        } else{                         
            lblStatus.setText(objDictionaryAction.getWord("ACTIONVERTICALMIRROR"));
            objWeaveAction.mirrorVertical(objWeave, initial_row, initial_col, final_row, final_col);
            drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
            populateNplot();
            populateDplot();
            isSelectionMode=false;
            scene.setCursor(Cursor.DEFAULT);
       }
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"Mirror Vertical",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void mirrorHorizontalAction(){        
    try {
        if(initial_row==final_row && initial_col==final_col){
            lblStatus.setText(objDictionaryAction.getWord("NOREGION"));
            new MessageView("alert", "alert", objDictionaryAction.getWord("NOREGION"));
        } else{                   
            lblStatus.setText(objDictionaryAction.getWord("ACTIONHORIZENTALMIRROR"));
            objWeaveAction.mirrorHorizontal(objWeave, initial_row, initial_col, final_row, final_col);
            drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
            populateNplot();
            populateDplot();
            isSelectionMode=false;
            scene.setCursor(Cursor.DEFAULT);
       }
    } catch (Exception ex) {
      new Logging("SEVERE",WeaveView.class.getName(),"Mirror Vertical",ex);
      lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void insertWarpAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONINSERTWARP"));
        objWeaveAction.insertWarp(objWeave, current_col,'W');
        drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
        populateNplot();
        populateDplot();
        if(objWeave.getIntWarp()>2){
            deleteWarpEditVB.setDisable(false);
        }
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"insert warp Right",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void deleteWarpAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONDELETEWARP"));
        if(objWeave.getIntWarp()!=current_col+1){
            objWeaveAction.deleteWarp(objWeave, current_col,'W');
            drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
            populateNplot();
            populateDplot();
        }else{
            lblStatus.setText(objDictionaryAction.getWord("WRONGINPUT"));
        }
        if(objWeave.getIntWarp()<3){
            deleteWarpEditVB.setDisable(true);
            lblStatus.setText(objDictionaryAction.getWord("MAXDELETE"));
        }
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"delete warp",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void insertWeftAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONINSERTWEFT"));
        objWeaveAction.insertWeft(objWeave, current_row,'W');
        drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
        populateNplot();
        populateDplot();
        if(objWeave.getIntWeft()>2){
            deleteWeftEditVB.setDisable(false);
        }
    } catch (Exception ex) {
        //Logger.getLogger(FabricView.class.getName()).log(Level.SEVERE, null, ex);
        new Logging("SEVERE",WeaveView.class.getName(),"insert weft",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void deleteWeftAction(){        
    try {
        if(objWeave.getIntWeft()!=current_row+1){
            lblStatus.setText(objDictionaryAction.getWord("ACTIONDELETEWEFT"));
            objWeaveAction.deleteWeft(objWeave, current_row,'W');
            drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
            populateNplot();
            populateDplot();
        }else{
            lblStatus.setText(objDictionaryAction.getWord("WRONGINPUT"));
        }
        if(objWeave.getIntWeft()<3){
            deleteWeftEditVB.setDisable(true);
            lblStatus.setText(objDictionaryAction.getWord("MAXDELETE"));
        }
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"delete weft",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}

private void clearAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONCLEARWEAVE"));
        objWeaveAction.clear(objWeave);
        drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
        populateNplot();
        populateDplot();                        
     } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"Clear ",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void moveRightAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVERIGHT"));
        objWeaveAction.moveRight(objWeave);
        drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"Move Right",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void moveLeftAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVELEFT"));
        objWeaveAction.moveLeft(objWeave);
        drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"Move Left",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void moveUpAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVEUP"));
        objWeaveAction.moveUp(objWeave);
        drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"Move Up",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void moveDownAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVEDOWN"));
        objWeaveAction.moveDown(objWeave);
        drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"Move Down",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void moveRight8Action(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVERIGHT8"));
        objWeaveAction.moveRight8(objWeave);
        drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"Move Right",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void moveLeft8Action(){        
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVELEFT8"));
        objWeaveAction.moveLeft8(objWeave);
        drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"Move Left",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void moveUp8Action(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVEUP8"));
        objWeaveAction.moveUp8(objWeave);
        drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"Move Up",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void moveDown8Action(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVEDOWN8"));
        objWeaveAction.moveDown8(objWeave);
        drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"Move Down",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void tiltRightAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONTILTRIGHT"));
        objWeaveAction.tiltRight(objWeave);
        drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"Tilt Right ",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void tiltLeftAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONTILTLEFT"));
        objWeaveAction.tiltLeft(objWeave);
        drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"Tilt Left ",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void tiltUpAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONTILTUP"));
        objWeaveAction.tiltUp(objWeave);
        drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"Tilt Up ",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void tiltDownAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONTILTDOWN"));
        objWeaveAction.tiltDown(objWeave);
        drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
       new Logging("SEVERE",WeaveView.class.getName(),"Tilt Down ",ex);
       lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void rotateClockwiseAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONCLOCKROTATION"));
        objWeaveAction.rotation(objWeave);
        drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"Rotate Clock Wise ",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void rotateAntiClockwiseAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONANTICLOCKROTATION"));
        objWeaveAction.rotationAnti(objWeave);
        drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"Anti Rotate Clock Wise ",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void inversionAction(){        
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONINVERSION"));
        objWeaveAction.inversion(objWeave);
        drawImageFromMatrix(objWeave.getDesignMatrix(), designImage);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"Inversion ",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
 
private void compositeViewAction(){
    try{    
        objWeaveAction = new WeaveAction();
        //objWeaveAction.populateDenting(objWeave);
        lblStatus.setText(objDictionaryAction.getWord("ACTIONCOMPOSITEVIEW"));
        int intHeight = (int)(objConfiguration.HEIGHT);
        int intLength = (int)(objConfiguration.WIDTH);

        BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
        objWeaveAction = new WeaveAction();
        bufferedImage = objWeaveAction.plotCompositeView(objWeave, objWeave.getIntWarp(), objWeave.getIntWeft(), intLength, intHeight);

        final Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        BorderPane popup=new BorderPane();
        final ImageView weaveIV=new ImageView();
        
        intLength = (int)(((objWeave.getObjConfiguration().getIntDPI()*bufferedImage.getWidth())/objWeave.getIntEPI()));
        intHeight = (int)(((objWeave.getObjConfiguration().getIntDPI()*bufferedImage.getHeight())/objWeave.getIntPPI()));
        intHeight=(int)(intHeight*(objWeave.getObjConfiguration().WIDTH/intLength));
        intLength=(int)objWeave.getObjConfiguration().WIDTH;
        BufferedImage bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g1 = bufferedImageesize.createGraphics();
        g1.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
        g1.dispose();

        weaveIV.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));        
        weaveIV.setFitHeight(objConfiguration.HEIGHT);
        weaveIV.setFitWidth(objConfiguration.WIDTH);
        popup.setCenter(weaveIV);        
        popup.setId("popup");
        Scene popupScene = new Scene(popup);

        //For zoom Out weaving pattern
        final KeyCombination zoomOutKC = new KeyCodeCombination(KeyCode.SUBTRACT,KeyCombination.ALT_DOWN);
        //For undo weaving pattern
        final KeyCombination zoomInKC = new KeyCodeCombination(KeyCode.ADD,KeyCombination.ALT_DOWN);
        //For redo weaving pattern
        final KeyCombination normalKC = new KeyCodeCombination(KeyCode.EQUALS,KeyCombination.ALT_DOWN);
        //For redo weaving pattern
        final KeyCombination closeKC = new KeyCodeCombination(KeyCode.ESCAPE,KeyCombination.ALT_DOWN);

        popupScene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler() {
            @Override
            public void handle(Event t) {
                if (closeKC.match((KeyEvent) t)){
                    dialogStage.close();
                } else if (normalKC.match((KeyEvent) t)){
                    weaveIV.setScaleX(1);
                    weaveIV.setScaleY(1);
                    weaveIV.setScaleZ(1);
                } else if (zoomInKC.match((KeyEvent) t)){
                    weaveIV.setScaleX(weaveIV.getScaleX()*2);
                    weaveIV.setScaleY(weaveIV.getScaleY()*2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()*2);
                } else if (zoomOutKC.match((KeyEvent) t)){
                    weaveIV.setScaleX(weaveIV.getScaleX()/2);
                    weaveIV.setScaleY(weaveIV.getScaleY()/2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()/2);
                }
            }
        });
        popupScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                KeyCode key = t.getCode();
                if (key == KeyCode.ESCAPE){
                    dialogStage.close();
                } else if (key == KeyCode.ENTER){
                    weaveIV.setScaleX(1);
                    weaveIV.setScaleY(1);
                    weaveIV.setScaleZ(1);
                } else if (key == KeyCode.UP){
                    weaveIV.setScaleX(weaveIV.getScaleX()*2);
                    weaveIV.setScaleY(weaveIV.getScaleY()*2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()*2);
                } else if (key == KeyCode.DOWN){
                    weaveIV.setScaleX(weaveIV.getScaleX()/2);
                    weaveIV.setScaleY(weaveIV.getScaleY()/2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()/2);
                }
            }
        });

        popupScene.getStylesheets().add(WeaveView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        dialogStage.setScene(popupScene);
        dialogStage.setX(-5);
        dialogStage.setY(0);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("COMPOSITEVIEW"));
        dialogStage.showAndWait();

        bufferedImage = null;
        System.gc();
    } catch (SQLException ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"compositeViewAction() : Error while viewing composite view",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}

private void gridViewAction(){
    try{    
        lblStatus.setText(objDictionaryAction.getWord("ACTIONGRIDVIEW"));        
        int intHeight = (int)(objConfiguration.HEIGHT/boxSize);
        int intLength = (int)(objConfiguration.WIDTH/boxSize);

        BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
        objWeaveAction = new WeaveAction();
        bufferedImage = objWeaveAction.plotCompositeView(objWeave, objWeave.getIntWarp(), objWeave.getIntWeft(), intLength, intHeight);

        BufferedImage bufferedImageesize = new BufferedImage((int)(intLength*boxSize), (int)(intHeight*boxSize),BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImageesize.createGraphics();
        g.drawImage(bufferedImage, 0, 0, (int)(intLength*boxSize), (int)(intHeight*boxSize), null);
        g.setColor(java.awt.Color.BLACK);
        BasicStroke bs = new BasicStroke(2);
        g.setStroke(bs);
            
        for(int i = 0; i < intHeight; i++) {
            for(int j = 0; j < intLength; j++) {
                if((j%boxSize)==0){
                    bs = new BasicStroke(2);
                     g.setStroke(bs);
                }else{
                    bs = new BasicStroke(1);
                    g.setStroke(bs);
                }
                g.drawLine((int)(j*boxSize), 0,  (int)(j*boxSize), (int)(intHeight*boxSize));
            }
            if((i%boxSize)==0){
                bs = new BasicStroke(2);
                g.setStroke(bs);
            }else{
                bs = new BasicStroke(1);
                g.setStroke(bs);
            }
            g.drawLine(0, (int)(i*boxSize), (int)(intLength*boxSize), (int)(i*boxSize));
        }
        
        final Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        BorderPane popup=new BorderPane();
        final ImageView weaveIV=new ImageView();
        weaveIV.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));        
        weaveIV.setFitHeight(objConfiguration.HEIGHT);
        weaveIV.setFitWidth(objConfiguration.WIDTH);
        popup.setCenter(weaveIV);        
        popup.setId("popup");
        Scene popupScene = new Scene(popup);

        //For zoom Out weaving pattern
        final KeyCombination zoomOutKC = new KeyCodeCombination(KeyCode.SUBTRACT,KeyCombination.ALT_DOWN);
        //For undo weaving pattern
        final KeyCombination zoomInKC = new KeyCodeCombination(KeyCode.ADD,KeyCombination.ALT_DOWN);
        //For redo weaving pattern
        final KeyCombination normalKC = new KeyCodeCombination(KeyCode.EQUALS,KeyCombination.ALT_DOWN);
        //For redo weaving pattern
        final KeyCombination closeKC = new KeyCodeCombination(KeyCode.ESCAPE,KeyCombination.ALT_DOWN);

        popupScene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler() {
            @Override
            public void handle(Event t) {
                if (closeKC.match((KeyEvent) t)){
                    dialogStage.close();
                } else if (normalKC.match((KeyEvent) t)){
                    weaveIV.setScaleX(1);
                    weaveIV.setScaleY(1);
                    weaveIV.setScaleZ(1);
                } else if (zoomInKC.match((KeyEvent) t)){
                    weaveIV.setScaleX(weaveIV.getScaleX()*2);
                    weaveIV.setScaleY(weaveIV.getScaleY()*2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()*2);
                } else if (zoomOutKC.match((KeyEvent) t)){
                    weaveIV.setScaleX(weaveIV.getScaleX()/2);
                    weaveIV.setScaleY(weaveIV.getScaleY()/2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()/2);
                }
            }
        });
        popupScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                KeyCode key = t.getCode();
                if (key == KeyCode.ESCAPE){
                    dialogStage.close();
                } else if (key == KeyCode.ENTER){
                    weaveIV.setScaleX(1);
                    weaveIV.setScaleY(1);
                    weaveIV.setScaleZ(1);
                } else if (key == KeyCode.UP){
                    weaveIV.setScaleX(weaveIV.getScaleX()*2);
                    weaveIV.setScaleY(weaveIV.getScaleY()*2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()*2);
                } else if (key == KeyCode.DOWN){
                    weaveIV.setScaleX(weaveIV.getScaleX()/2);
                    weaveIV.setScaleY(weaveIV.getScaleY()/2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()/2);
                }
            }
        });

        popupScene.getStylesheets().add(WeaveView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        dialogStage.setScene(popupScene);
        dialogStage.setX(-5);
        dialogStage.setY(0);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("GRIDVIEW"));
        dialogStage.showAndWait();

        bufferedImage = null;
        System.gc();
    } catch (SQLException ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"gridViewAction() : Error while viewing grid view",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}

private void graphViewAction(){
    try{    
        lblStatus.setText(objDictionaryAction.getWord("ACTIONGRAPHVIEW"));
        int intHeight = (int)(objConfiguration.HEIGHT/boxSize);
        int intLength = (int)(objConfiguration.WIDTH/boxSize);

        BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
        objWeaveAction = new WeaveAction();
        bufferedImage = objWeaveAction.plotCompositeView(objWeave, objWeave.getIntWarp(), objWeave.getIntWeft(), intLength, intHeight);

        BufferedImage bufferedImageesize = new BufferedImage((int)(intLength*boxSize), (int)(intHeight*boxSize),BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImageesize.createGraphics();
        g.drawImage(bufferedImage, 0, 0, (int)(intLength*boxSize), (int)(intHeight*boxSize), null);
        g.setColor(java.awt.Color.BLACK);
        BasicStroke bs = new BasicStroke(2);
        g.setStroke(bs);
            
        for(int i = 0; i < intHeight; i++) {
            for(int j = 0; j < intLength; j++) {
                if((j%boxSize)==0){
                    bs = new BasicStroke(2);
                     g.setStroke(bs);
                }else{
                    bs = new BasicStroke(1);
                    g.setStroke(bs);
                }
                g.drawLine((int)(j*boxSize), 0,  (int)(j*boxSize), (int)(intHeight*boxSize));
            }
            if((i%boxSize)==0){
                bs = new BasicStroke(2);
                g.setStroke(bs);
            }else{
                bs = new BasicStroke(1);
                g.setStroke(bs);
            }
            g.drawLine(0, (int)(i*boxSize), (int)(intLength*boxSize), (int)(i*boxSize));
        }
        
        final Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        BorderPane popup=new BorderPane();
        final ImageView weaveIV=new ImageView();
        weaveIV.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));        
        weaveIV.setFitHeight(objConfiguration.HEIGHT);
        weaveIV.setFitWidth(objConfiguration.WIDTH);
        popup.setCenter(weaveIV);        
        popup.setId("popup");
        Scene popupScene = new Scene(popup);

        //For zoom Out weaving pattern
        final KeyCombination zoomOutKC = new KeyCodeCombination(KeyCode.SUBTRACT,KeyCombination.ALT_DOWN);
        //For undo weaving pattern
        final KeyCombination zoomInKC = new KeyCodeCombination(KeyCode.ADD,KeyCombination.ALT_DOWN);
        //For redo weaving pattern
        final KeyCombination normalKC = new KeyCodeCombination(KeyCode.EQUALS,KeyCombination.ALT_DOWN);
        //For redo weaving pattern
        final KeyCombination closeKC = new KeyCodeCombination(KeyCode.ESCAPE,KeyCombination.ALT_DOWN);

        popupScene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler() {
            @Override
            public void handle(Event t) {
                if (closeKC.match((KeyEvent) t)){
                    dialogStage.close();
                } else if (normalKC.match((KeyEvent) t)){
                    weaveIV.setScaleX(1);
                    weaveIV.setScaleY(1);
                    weaveIV.setScaleZ(1);
                } else if (zoomInKC.match((KeyEvent) t)){
                    weaveIV.setScaleX(weaveIV.getScaleX()*2);
                    weaveIV.setScaleY(weaveIV.getScaleY()*2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()*2);
                } else if (zoomOutKC.match((KeyEvent) t)){
                    weaveIV.setScaleX(weaveIV.getScaleX()/2);
                    weaveIV.setScaleY(weaveIV.getScaleY()/2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()/2);
                }
            }
        });
        popupScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                KeyCode key = t.getCode();
                if (key == KeyCode.ESCAPE){
                    dialogStage.close();
                } else if (key == KeyCode.ENTER){
                    weaveIV.setScaleX(1);
                    weaveIV.setScaleY(1);
                    weaveIV.setScaleZ(1);
                } else if (key == KeyCode.UP){
                    weaveIV.setScaleX(weaveIV.getScaleX()*2);
                    weaveIV.setScaleY(weaveIV.getScaleY()*2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()*2);
                } else if (key == KeyCode.DOWN){
                    weaveIV.setScaleX(weaveIV.getScaleX()/2);
                    weaveIV.setScaleY(weaveIV.getScaleY()/2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()/2);
                }
            }
        });

        popupScene.getStylesheets().add(WeaveView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        dialogStage.setScene(popupScene);
        dialogStage.setX(-5);
        dialogStage.setY(0);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("GRAPHVIEW"));
        dialogStage.showAndWait();

        bufferedImage = null;
        System.gc();
    } catch (SQLException ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"graphViewAction() : Error while viewing graph view",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void frontViewAction(){
    try{    
        lblStatus.setText(objDictionaryAction.getWord("ACTIONVISULIZATIONVIEW"));
        int intHeight = (int)(objConfiguration.HEIGHT/3);
        int intLength = (int)(objConfiguration.WIDTH/3);

        BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
        objWeaveAction = new WeaveAction();
        bufferedImage = objWeaveAction.plotFrontSideView(objWeave, objWeave.getIntWarp(), objWeave.getIntWeft(), intLength, intHeight);

 
        final Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        BorderPane popup=new BorderPane();
        final ImageView weaveIV=new ImageView();
        
        intLength = (int)(((objWeave.getObjConfiguration().getIntDPI()*bufferedImage.getWidth())/objWeave.getIntEPI()));
        intHeight = (int)(((objWeave.getObjConfiguration().getIntDPI()*bufferedImage.getHeight())/objWeave.getIntPPI()));
        intHeight=(int)(intHeight*(objWeave.getObjConfiguration().WIDTH/intLength));
        intLength=(int)objWeave.getObjConfiguration().WIDTH;
        BufferedImage bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g1 = bufferedImageesize.createGraphics();
        g1.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
        g1.dispose();
        
        weaveIV.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));        
        weaveIV.setFitHeight(objConfiguration.HEIGHT);
        weaveIV.setFitWidth(objConfiguration.WIDTH);
        popup.setCenter(weaveIV);        
        popup.setId("popup");
        Scene popupScene = new Scene(popup);

        //For Front weaving pattern
        final KeyCombination frontKC = new KeyCodeCombination(KeyCode.F,KeyCombination.ALT_DOWN);
        //For Rear weaving pattern
        final KeyCombination rearKC = new KeyCodeCombination(KeyCode.R,KeyCombination.ALT_DOWN);
        //For zoom Out weaving pattern
        final KeyCombination zoomOutKC = new KeyCodeCombination(KeyCode.SUBTRACT,KeyCombination.ALT_DOWN);
        //For undo weaving pattern
        final KeyCombination zoomInKC = new KeyCodeCombination(KeyCode.ADD,KeyCombination.ALT_DOWN);
        //For redo weaving pattern
        final KeyCombination normalKC = new KeyCodeCombination(KeyCode.EQUALS,KeyCombination.ALT_DOWN);
        //For redo weaving pattern
        final KeyCombination closeKC = new KeyCodeCombination(KeyCode.ESCAPE,KeyCombination.ALT_DOWN);

        popupScene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler() {
            @Override
            public void handle(Event t) {
                if (closeKC.match((KeyEvent) t)){
                    dialogStage.close();
                } else if (normalKC.match((KeyEvent) t)){
                    weaveIV.setScaleX(1);
                    weaveIV.setScaleY(1);
                    weaveIV.setScaleZ(1);
                } else if (zoomInKC.match((KeyEvent) t)){
                    weaveIV.setScaleX(weaveIV.getScaleX()*2);
                    weaveIV.setScaleY(weaveIV.getScaleY()*2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()*2);
                } else if (zoomOutKC.match((KeyEvent) t)){
                    weaveIV.setScaleX(weaveIV.getScaleX()/2);
                    weaveIV.setScaleY(weaveIV.getScaleY()/2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()/2);
                }
            }
        });
        popupScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                KeyCode key = t.getCode();
                if (key == KeyCode.ESCAPE){
                    dialogStage.close();
                } else if (key == KeyCode.ENTER){
                    weaveIV.setScaleX(1);
                    weaveIV.setScaleY(1);
                    weaveIV.setScaleZ(1);
                } else if (key == KeyCode.UP){
                    weaveIV.setScaleX(weaveIV.getScaleX()*2);
                    weaveIV.setScaleY(weaveIV.getScaleY()*2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()*2);
                } else if (key == KeyCode.DOWN){
                    weaveIV.setScaleX(weaveIV.getScaleX()/2);
                    weaveIV.setScaleY(weaveIV.getScaleY()/2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()/2);
                }
            }
        });

        popupScene.getStylesheets().add(WeaveView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        dialogStage.setScene(popupScene);
        dialogStage.setX(-5);
        dialogStage.setY(0);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("VISULIZATIONVIEW"));
        dialogStage.showAndWait();

        bufferedImage = null;
        System.gc();
    } catch (SQLException ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"frontViewAction() : Error while viewing front view",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}

private void switchViewAction(){
    try{    
        lblStatus.setText(objDictionaryAction.getWord("ACTIONSWITCHSIDEVIEW"));
        int intHeight = (int)(objConfiguration.HEIGHT/3);
        int intLength = (int)(objConfiguration.WIDTH/3);

        BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
        objWeaveAction = new WeaveAction();
        bufferedImage = objWeaveAction.plotFlipSideView(objWeave, objWeave.getIntWarp(), objWeave.getIntWeft(), intLength, intHeight);

        final Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        BorderPane popup=new BorderPane();
        final ImageView weaveIV=new ImageView();
        
        intLength = (int)(((objWeave.getObjConfiguration().getIntDPI()*bufferedImage.getWidth())/objWeave.getIntEPI()));
        intHeight = (int)(((objWeave.getObjConfiguration().getIntDPI()*bufferedImage.getHeight())/objWeave.getIntPPI()));
        intHeight=(int)(intHeight*(objWeave.getObjConfiguration().WIDTH/intLength));
        intLength=(int)objWeave.getObjConfiguration().WIDTH;
        BufferedImage bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g1 = bufferedImageesize.createGraphics();
        g1.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
        g1.dispose();

        weaveIV.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));        
        weaveIV.setFitHeight(objConfiguration.HEIGHT);
        weaveIV.setFitWidth(objConfiguration.WIDTH);
        popup.setCenter(weaveIV);        
        popup.setId("popup");
        Scene popupScene = new Scene(popup);

        //For zoom Out weaving pattern
        final KeyCombination zoomOutKC = new KeyCodeCombination(KeyCode.SUBTRACT,KeyCombination.ALT_DOWN);
        //For undo weaving pattern
        final KeyCombination zoomInKC = new KeyCodeCombination(KeyCode.ADD,KeyCombination.ALT_DOWN);
        //For redo weaving pattern
        final KeyCombination normalKC = new KeyCodeCombination(KeyCode.EQUALS,KeyCombination.ALT_DOWN);
        //For redo weaving pattern
        final KeyCombination closeKC = new KeyCodeCombination(KeyCode.ESCAPE,KeyCombination.ALT_DOWN);

        popupScene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler() {
            @Override
            public void handle(Event t) {
                if (closeKC.match((KeyEvent) t)){
                    dialogStage.close();
                } else if (normalKC.match((KeyEvent) t)){
                    weaveIV.setScaleX(1);
                    weaveIV.setScaleY(1);
                    weaveIV.setScaleZ(1);
                } else if (zoomInKC.match((KeyEvent) t)){
                    weaveIV.setScaleX(weaveIV.getScaleX()*2);
                    weaveIV.setScaleY(weaveIV.getScaleY()*2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()*2);
                } else if (zoomOutKC.match((KeyEvent) t)){
                    weaveIV.setScaleX(weaveIV.getScaleX()/2);
                    weaveIV.setScaleY(weaveIV.getScaleY()/2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()/2);
                }
            }
        });
        popupScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                KeyCode key = t.getCode();
                if (key == KeyCode.ESCAPE){
                    dialogStage.close();
                } else if (key == KeyCode.ENTER){
                    weaveIV.setScaleX(1);
                    weaveIV.setScaleY(1);
                    weaveIV.setScaleZ(1);
                } else if (key == KeyCode.UP){
                    weaveIV.setScaleX(weaveIV.getScaleX()*2);
                    weaveIV.setScaleY(weaveIV.getScaleY()*2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()*2);
                } else if (key == KeyCode.DOWN){
                    weaveIV.setScaleX(weaveIV.getScaleX()/2);
                    weaveIV.setScaleY(weaveIV.getScaleY()/2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()/2);
                }
            }
        });

        popupScene.getStylesheets().add(WeaveView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        dialogStage.setScene(popupScene);
        dialogStage.setX(-5);
        dialogStage.setY(0);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("SWITCHSIDEVIEW"));
        dialogStage.showAndWait();

        bufferedImage = null;
        System.gc();
    } catch (SQLException ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"switchViewAction() : Error while viewing back view",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}

private void simulationViewAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONSIMULATION"));
        int intHeight = (int)objWeave.getObjConfiguration().HEIGHT;
        int intLength = (int)objWeave.getObjConfiguration().WIDTH;
        BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
        objWeaveAction = new WeaveAction();
        bufferedImage = objWeaveAction.plotCompositeView(objWeave, objWeave.getIntWarp(), objWeave.getIntWeft(), intLength, intHeight);
        SimulatorEditView objBaseSimulationView = new SimulatorEditView(objConfiguration, bufferedImage);                    
    } catch (SQLException ex) {
        new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    } catch (Exception ex) {
        new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}

private void tilledViewAction(){
    lblStatus.setText(objDictionaryAction.getWord("ACTIONTILLEDVIEW"));
        GridPane popup=new GridPane();
        int height=(int)(objConfiguration.HEIGHT/boxSize);
        int width=(int)(objConfiguration.WIDTH/boxSize);
        byte[][] tilledMatrix = new byte[height][width];
        for(int x = 0; x < height; x++) {
            for(int y = 0; y < width; y++) {
                if(x>=objWeave.getIntWeft() && y<objWeave.getIntWarp()){
                     tilledMatrix[x][y] = objWeave.getDesignMatrix()[x%objWeave.getIntWeft()][y];  
                }else if(x<objWeave.getIntWeft() && y>=objWeave.getIntWarp()){
                     tilledMatrix[x][y] = objWeave.getDesignMatrix()[x][y%objWeave.getIntWarp()];  
                }else if(x>=objWeave.getIntWeft() && y>=objWeave.getIntWarp()){
                     tilledMatrix[x][y] = objWeave.getDesignMatrix()[x%objWeave.getIntWeft()][y%objWeave.getIntWarp()];  
                }else{
                     tilledMatrix[x][y] = objWeave.getDesignMatrix()[x][y]; 
                }
                Label lblbox = new Label("");
                lblbox.setPrefSize(boxSize,boxSize);
                if(tilledMatrix[x][y]==1 )
                    lblbox.setStyle("-fx-background-color: #000000;");
                else
                    lblbox.setStyle("-fx-background-color: #ffffff; -fx-border-width: 1;  -fx-border-color: black;");               
                popup.add(lblbox,y,x);
            }
        }   
        final Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        popup.setId("popup");
        Scene scene = new Scene(popup);
        scene.getStylesheets().add(WeaveView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        dialogStage.setScene(scene);
        dialogStage.setX(-5);
        dialogStage.setY(0);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("ACTIONTILLEDVIEW"));
        dialogStage.showAndWait();    
        
        tilledMatrix=null;
        System.gc();
}

public static int[] convertIntegers(List<Integer> integers)
{
    int[] ret = new int[integers.size()];
    for (int i=0; i < ret.length; i++)
    {
        ret[i] = integers.get(i).intValue();
    }
    return ret;
}

private int[][] getColorIndex(ArrayList yarnColor, int warpColorCount){
    int[][] colorIndex=new int[objWeave.getIntWeft()][objWeave.getIntWarp()];
    Point dot=new Point(); 
    for(int wf=0; wf<objWeave.getIntWeft(); wf++){
        for(int wp=0; wp<objWeave.getIntWarp(); wp++){
            if(objWeave.getDesignMatrix()[wf][wp]==0){ // weft
                for(int c=warpColorCount; c<yarnColor.size(); c++){
                    if(yarnColor.get(c)==objWeave.getWeftYarn()[wf].getStrYarnColor())
                        colorIndex[wf][wp]=c;
                }
            }
            else if(objWeave.getDesignMatrix()[wf][wp]==1){ // warp
                for(int c=0; c<warpColorCount; c++){
                    if(yarnColor.get(c)==objWeave.getWarpYarn()[wp].getStrYarnColor())
                        colorIndex[wf][wp]=c;
                }
            }
        }
    }
    /*for(int wf=0; wf<objWeave.getIntWeft(); wf++){
        for(int wp=0; wp<objWeave.getIntWarp(); wp++){
            System.out.print(colorIndex[wf][wp]+" ");
        }
        System.out.println("");
    }*/
    return colorIndex;
}

private void colourwaysAction(){
    if(objWeave.getWarpYarn()==null||objWeave.getWeftYarn()==null)
        return;
    int warpCount=objWeave.getWarpYarn().length;
    int weftCount=objWeave.getWeftYarn().length;
    ArrayList<String> yarnColor=new ArrayList<>();
    for(int c=0; c<warpCount; c++){
        if(!yarnColor.contains(objWeave.getWarpYarn()[c].getStrYarnColor()))
            yarnColor.add(objWeave.getWarpYarn()[c].getStrYarnColor());
    }
    warpCount=yarnColor.size();
    for(int c=0; c<weftCount; c++){
        if(!yarnColor.contains(objWeave.getWeftYarn()[c].getStrYarnColor()))
            yarnColor.add(objWeave.getWeftYarn()[c].getStrYarnColor());
    }
    weftCount=yarnColor.size()-warpCount;
    final int intWeftCount=objWeave.getIntWeft();
    final int intWarpCount=objWeave.getIntWarp();
    final int totalColor=yarnColor.size();
	if(totalColor>7){
        lblStatus.setText(objDictionaryAction.getWord("MANYCOLOUR"));
        return;
    }
    final int[][] colorIndex=getColorIndex(yarnColor, warpCount);
    final int totalComb=factorial(yarnColor.size());
    ArrayList<List<String>> list=new ArrayList<>();
    permute(yarnColor, 0, list);
    int count=0;
    final String[][] colorPermutation=new String[totalComb][yarnColor.size()];
    for(List<String> l:list){
        int sCount=0;
        for(String s: l){
            colorPermutation[count][sCount++]=s;
        }
        count++;
    }
    lblStatus.setText(objDictionaryAction.getWord("ACTIONCOLOURWAYS"));
    int height=(int)(objConfiguration.HEIGHT/3);
    int width=(int)(objConfiguration.WIDTH/3);
    byte[][] tilledMatrix = new byte[height][width];
    BufferedImage bufferedImage = new BufferedImage(width*3, height*3,BufferedImage.TYPE_INT_RGB);
    int bands = 3;
    int rgb = 0;
    int combPerPage=1;
	final int COMB_PER_PAGE=9;
    //System.err.println(colorPermutation.length+"Total Combination"+totalComb);
    if(totalComb<=0)
        return;
    /*else if(totalComb==2)
        combPerPage=1;
    else if(totalComb<=6)
        combPerPage=4;
    else if(totalComb<=24)
        combPerPage=9;*/
	// Added on 09/11/2017
    else combPerPage=9;
    final int totalPages=(int)Math.ceil((float)totalComb/(float)combPerPage);
    Pagination objPage=new Pagination(totalPages);
    objPage.setPageFactory(new Callback<Integer, Node>() {
        @Override
        public Node call(Integer pageIndex) {
            if (pageIndex >= totalPages) {
                return null;
            } else {
                ImageView iv =plotShadedDesignPage(colorPermutation, colorIndex, COMB_PER_PAGE, pageIndex.intValue()*COMB_PER_PAGE, intWarpCount, intWeftCount, totalComb);
                return iv;
            }
        }
    });
    
    final int pageDiv=(int)Math.sqrt(combPerPage);
    final int patternW=(width*3)/pageDiv;
    final int patternH=(height*3)/pageDiv;
        
    /*

    for(int x = 0, p = 0; x < height; x++) {
        for(int y = 0, q = 0; y < width; y++) {
            if(x>=objWeave.getIntWeft() && y<objWeave.getIntWarp()){
                 tilledMatrix[x][y] = objWeave.getDesignMatrix()[x%objWeave.getIntWeft()][y];  
            }else if(x<objWeave.getIntWeft() && y>=objWeave.getIntWarp()){
                 tilledMatrix[x][y] = objWeave.getDesignMatrix()[x][y%objWeave.getIntWarp()];  
            }else if(x>=objWeave.getIntWeft() && y>=objWeave.getIntWarp()){
                 tilledMatrix[x][y] = objWeave.getDesignMatrix()[x%objWeave.getIntWeft()][y%objWeave.getIntWarp()];  
            }else{
                 tilledMatrix[x][y] = objWeave.getDesignMatrix()[x][y]; 
            }
        }
    }
    int a=0; // starting number of combination
    int ch=a;
    for(int x = 0, p = 0; x < height; x++) {
        a=ch;
        for(int y = 0, q = 0; y < width; y++) {
    
            for(int i = 0; i < bands; i++) {
                for(int j = 0; j < bands; j++) {                        
                    if(tilledMatrix[x][y]==0){
                        if(i==0)
                            rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getBlue()).brighter().getRGB();
                            //rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getBlue()).brighter().getRGB();
                        else if(i==2)
                            rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getBlue()).darker().getRGB();
                            //rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getBlue()).darker().getRGB();
                        else
                            rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getBlue()).getRGB();
                            //rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getBlue()).getRGB();
                    } else if(tilledMatrix[x][y]==1){
                        if(j==0)
                            rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getBlue()).brighter().getRGB();
                            //rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getBlue()).brighter().getRGB();
                        else if(j==2)
                            rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getBlue()).darker().getRGB();
                            //rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getBlue()).darker().getRGB();
                        else
                            rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getBlue()).getRGB();
                            //rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getBlue()).getRGB();
                    } else {
                        if(i==0)
                            rgb = new java.awt.Color((float)javafx.scene.paint.Color.web("#FF0000").getRed(),(float)javafx.scene.paint.Color.web("#FF0000").getGreen(),(float)javafx.scene.paint.Color.web("#FF0000").getBlue()).brighter().getRGB();
                        else if(i==2)
                            rgb = new java.awt.Color((float)javafx.scene.paint.Color.web("#FF0000").getRed(),(float)javafx.scene.paint.Color.web("#FF0000").getGreen(),(float)javafx.scene.paint.Color.web("#FF0000").getBlue()).darker().getRGB();
                        else
                            rgb = new java.awt.Color((float)javafx.scene.paint.Color.web("#FF0000").getRed(),(float)javafx.scene.paint.Color.web("#FF0000").getGreen(),(float)javafx.scene.paint.Color.web("#FF0000").getBlue()).getRGB();
                    }
                    bufferedImage.setRGB(q+j, p+i, rgb);
                }
            }
            q+=bands;
            if((y%(width/pageDiv))==0&&y!=0){
                a++;
            }
        }
        p+=bands;
        if((x%(height/pageDiv))==0&&x!=0){
            ch+=pageDiv;
        }
    }
    */
    Graphics2D g=bufferedImage.createGraphics();
    BasicStroke bs = new BasicStroke(5);
    g.setStroke(bs);
    for(int z=1; z<=(pageDiv-1); z++){
        g.drawLine(0, z*((height*3)/pageDiv), bufferedImage.getWidth()-1, z*((height*3)/pageDiv));
        g.drawLine(z*((width*3)/pageDiv), 0, z*((width*3)/pageDiv), bufferedImage.getHeight()-1);
    }
    final Stage dialogStage = new Stage();
    dialogStage.initStyle(StageStyle.UTILITY);
    BorderPane popup=new BorderPane();
    final ImageView weaveIV=new ImageView();
    
    int intLength = (int)(((objWeave.getObjConfiguration().getIntDPI()*bufferedImage.getWidth())/objWeave.getIntEPI()));
    int intHeight = (int)(((objWeave.getObjConfiguration().getIntDPI()*bufferedImage.getHeight())/objWeave.getIntPPI()));
    intHeight=(int)(intHeight*(objWeave.getObjConfiguration().WIDTH/intLength));
    intLength=(int)objWeave.getObjConfiguration().WIDTH;
    BufferedImage bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
    Graphics2D g1 = bufferedImageesize.createGraphics();
    g1.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
    g1.dispose();

    weaveIV.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));        
    weaveIV.setFitHeight(objConfiguration.HEIGHT);
    weaveIV.setFitWidth(objConfiguration.WIDTH);
    //popup.setCenter(weaveIV);
    popup.setTop(objPage);
    popup.setId("popup");
    Scene popupScene = new Scene(popup);
    /*
    //For zoom Out weaving pattern
    final KeyCombination zoomOutKC = new KeyCodeCombination(KeyCode.SUBTRACT,KeyCombination.ALT_DOWN);
    //For undo weaving pattern
    final KeyCombination zoomInKC = new KeyCodeCombination(KeyCode.ADD,KeyCombination.ALT_DOWN);
    //For redo weaving pattern
    final KeyCombination normalKC = new KeyCodeCombination(KeyCode.EQUALS,KeyCombination.ALT_DOWN);
    //For redo weaving pattern
    final KeyCombination closeKC = new KeyCodeCombination(KeyCode.ESCAPE,KeyCombination.ALT_DOWN);

    popupScene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler() {
        @Override
        public void handle(Event t) {
            if (closeKC.match((KeyEvent) t)){
                dialogStage.close();
            } else if (normalKC.match((KeyEvent) t)){
                weaveIV.setScaleX(1);
                weaveIV.setScaleY(1);
                weaveIV.setScaleZ(1);
            } else if (zoomInKC.match((KeyEvent) t)){
                weaveIV.setScaleX(weaveIV.getScaleX()*2);
                weaveIV.setScaleY(weaveIV.getScaleY()*2);
                weaveIV.setScaleZ(weaveIV.getScaleZ()*2);
            } else if (zoomOutKC.match((KeyEvent) t)){
                weaveIV.setScaleX(weaveIV.getScaleX()/2);
                weaveIV.setScaleY(weaveIV.getScaleY()/2);
                weaveIV.setScaleZ(weaveIV.getScaleZ()/2);
            }
        }
    });
    popupScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent t) {
            KeyCode key = t.getCode();
            if (key == KeyCode.ESCAPE){
                dialogStage.close();
            } else if (key == KeyCode.ENTER){
                weaveIV.setScaleX(1);
                weaveIV.setScaleY(1);
                weaveIV.setScaleZ(1);
            } else if (key == KeyCode.UP){
                weaveIV.setScaleX(weaveIV.getScaleX()*2);
                weaveIV.setScaleY(weaveIV.getScaleY()*2);
                weaveIV.setScaleZ(weaveIV.getScaleZ()*2);
            } else if (key == KeyCode.DOWN){
                weaveIV.setScaleX(weaveIV.getScaleX()/2);
                weaveIV.setScaleY(weaveIV.getScaleY()/2);
                weaveIV.setScaleZ(weaveIV.getScaleZ()/2);
            }
        }
    });
    
    weaveIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            double mouseX=t.getX();
            double mouseY=t.getY();
            for(int i=1; i<=pageDiv; i++){
                for(int j=1; j<=pageDiv; j++){
                    if((mouseX>=((i-1)*patternW))&&(mouseX<(i*patternW))&&(mouseY>=((j-1)*patternH))&&(mouseY<(j*patternH))){
                        //System.out.println("Selected Pattern: "+(((j-1)*pageDiv)+(i-1)));
                        plotShadedDesignParam(((j-1)*pageDiv)+(i-1), colorPermutation, colorIndex);
                    }
                }
            }
        }
    });
	*/

    popupScene.getStylesheets().add(WeaveView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
    dialogStage.setScene(popupScene);
    dialogStage.setX(-5);
    dialogStage.setY(0);
    dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("ACTIONCOLOURWAYS"));
    dialogStage.showAndWait();

    tilledMatrix=null;
    bufferedImage = null;
    System.gc();
}

public void plotShadedDesignParam(int patternNum, String[][] colorPermutation, int[][] colorIndex){
        //System.out.println("Num is: "+patternNum);
        int intWarpCount=objWeave.getIntWarp();
        int intWeftCount=objWeave.getIntWeft();
        int height=(int)(objConfiguration.HEIGHT/3);
        int width=(int)(objConfiguration.WIDTH/3);
        byte[][] tilledMatrix = new byte[height][width];
        BufferedImage bufferedImage = new BufferedImage(width*3, height*3,BufferedImage.TYPE_INT_RGB);
        int bands = 3;
        int rgb = 0;
        int combPerPage=1;
        final int pageDiv=(int)Math.sqrt(combPerPage);
        
        int a=patternNum; // starting number of combination
        int ch=a;
        for(int x = 0, p = 0; x < height; x++) {
        a=ch;
        for(int y = 0, q = 0; y < width; y++) {
            if(x>=objWeave.getIntWeft() && y<objWeave.getIntWarp()){
                 tilledMatrix[x][y] = objWeave.getDesignMatrix()[x%objWeave.getIntWeft()][y];  
            }else if(x<objWeave.getIntWeft() && y>=objWeave.getIntWarp()){
                 tilledMatrix[x][y] = objWeave.getDesignMatrix()[x][y%objWeave.getIntWarp()];  
            }else if(x>=objWeave.getIntWeft() && y>=objWeave.getIntWarp()){
                 tilledMatrix[x][y] = objWeave.getDesignMatrix()[x%objWeave.getIntWeft()][y%objWeave.getIntWarp()];  
            }else{
                 tilledMatrix[x][y] = objWeave.getDesignMatrix()[x][y]; 
            }
            for(int i = 0; i < bands; i++) {
                for(int j = 0; j < bands; j++) {                        
                    if(tilledMatrix[x][y]==0){
                        if(i==0)
                            rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getBlue()).brighter().getRGB();
                            //rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getBlue()).brighter().getRGB();
                        else if(i==2)
                            rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getBlue()).darker().getRGB();
                            //rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getBlue()).darker().getRGB();
                        else
                            rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getBlue()).getRGB();
                            //rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getBlue()).getRGB();
                    } else if(tilledMatrix[x][y]==1){
                        if(j==0)
                            rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getBlue()).brighter().getRGB();
                            //rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getBlue()).brighter().getRGB();
                        else if(j==2)
                            rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getBlue()).darker().getRGB();
                            //rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getBlue()).darker().getRGB();
                        else
                            rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getBlue()).getRGB();
                            //rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getBlue()).getRGB();
                    } else {
                        if(i==0)
                            rgb = new java.awt.Color((float)javafx.scene.paint.Color.web("#FF0000").getRed(),(float)javafx.scene.paint.Color.web("#FF0000").getGreen(),(float)javafx.scene.paint.Color.web("#FF0000").getBlue()).brighter().getRGB();
                        else if(i==2)
                            rgb = new java.awt.Color((float)javafx.scene.paint.Color.web("#FF0000").getRed(),(float)javafx.scene.paint.Color.web("#FF0000").getGreen(),(float)javafx.scene.paint.Color.web("#FF0000").getBlue()).darker().getRGB();
                        else
                            rgb = new java.awt.Color((float)javafx.scene.paint.Color.web("#FF0000").getRed(),(float)javafx.scene.paint.Color.web("#FF0000").getGreen(),(float)javafx.scene.paint.Color.web("#FF0000").getBlue()).getRGB();
                    }
                    bufferedImage.setRGB(q+j, p+i, rgb);
                }
            }
            q+=bands;
        }
        p+=bands;
    }
        final Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        BorderPane popup=new BorderPane();
        final ImageView weaveIV=new ImageView();
        weaveIV.setImage(SwingFXUtils.toFXImage(bufferedImage, null));        
        weaveIV.setFitHeight(objConfiguration.HEIGHT);
        weaveIV.setFitWidth(objConfiguration.WIDTH);
        popup.setCenter(weaveIV);        
        popup.setId("popup");
        Scene popupScene = new Scene(popup);
        
        //For zoom Out weaving pattern
        final KeyCombination zoomOutKC = new KeyCodeCombination(KeyCode.SUBTRACT,KeyCombination.ALT_DOWN);
        //For undo weaving pattern
        final KeyCombination zoomInKC = new KeyCodeCombination(KeyCode.ADD,KeyCombination.ALT_DOWN);
        //For redo weaving pattern
        final KeyCombination normalKC = new KeyCodeCombination(KeyCode.EQUALS,KeyCombination.ALT_DOWN);
        //For redo weaving pattern
        final KeyCombination closeKC = new KeyCodeCombination(KeyCode.ESCAPE,KeyCombination.ALT_DOWN);

        popupScene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler() {
            @Override
            public void handle(Event t) {
                if (closeKC.match((KeyEvent) t)){
                    dialogStage.close();
                } else if (normalKC.match((KeyEvent) t)){
                    weaveIV.setScaleX(1);
                    weaveIV.setScaleY(1);
                    weaveIV.setScaleZ(1);
                } else if (zoomInKC.match((KeyEvent) t)){
                    weaveIV.setScaleX(weaveIV.getScaleX()*2);
                    weaveIV.setScaleY(weaveIV.getScaleY()*2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()*2);
                } else if (zoomOutKC.match((KeyEvent) t)){
                    weaveIV.setScaleX(weaveIV.getScaleX()/2);
                    weaveIV.setScaleY(weaveIV.getScaleY()/2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()/2);
                }
            }
        });
        popupScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                KeyCode key = t.getCode();
                if (key == KeyCode.ESCAPE){
                    dialogStage.close();
                } else if (key == KeyCode.ENTER){
                    weaveIV.setScaleX(1);
                    weaveIV.setScaleY(1);
                    weaveIV.setScaleZ(1);
                } else if (key == KeyCode.UP){
                    weaveIV.setScaleX(weaveIV.getScaleX()*2);
                    weaveIV.setScaleY(weaveIV.getScaleY()*2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()*2);
                } else if (key == KeyCode.DOWN){
                    weaveIV.setScaleX(weaveIV.getScaleX()/2);
                    weaveIV.setScaleY(weaveIV.getScaleY()/2);
                    weaveIV.setScaleZ(weaveIV.getScaleZ()/2);
                }
            }
        });
        
        popupScene.getStylesheets().add(WeaveView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        dialogStage.setScene(popupScene);
        dialogStage.setX(-5);
        dialogStage.setY(0);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("ACTIONTEXTUREVIEW"));
        dialogStage.showAndWait();
        
        tilledMatrix=null;
        bufferedImage = null;
        System.gc();
    }

	public ImageView plotShadedDesignPage(final String[][] colorPermutation, final int[][] colorIndex, int combPerPage, final int startNumPattern, int intWarpCount, int intWeftCount, final int totalCombinations){
        int height=(int)(objConfiguration.HEIGHT/3);
        int width=(int)(objConfiguration.WIDTH/3);
        byte[][] tilledMatrix = new byte[height][width];
        BufferedImage bufferedImage = new BufferedImage(width*3, height*3,BufferedImage.TYPE_INT_RGB);
        for(int a=0; a<bufferedImage.getWidth(); a++)
            for(int b=0; b<bufferedImage.getHeight(); b++)
                bufferedImage.setRGB(a, b, INT_SOLID_WHITE);
        int bands = 3;
        int rgb = 0;

        final int pageDiv=(int)Math.sqrt(combPerPage);
        final int patternW=(width*3)/pageDiv;
        final int patternH=(height*3)/pageDiv;


        for(int x = 0, p = 0; x < height; x++) {
            for(int y = 0, q = 0; y < width; y++) {
                if(x>=objWeave.getIntWeft() && y<objWeave.getIntWarp()){
                     tilledMatrix[x][y] = objWeave.getDesignMatrix()[x%objWeave.getIntWeft()][y];  
                }else if(x<objWeave.getIntWeft() && y>=objWeave.getIntWarp()){
                     tilledMatrix[x][y] = objWeave.getDesignMatrix()[x][y%objWeave.getIntWarp()];  
                }else if(x>=objWeave.getIntWeft() && y>=objWeave.getIntWarp()){
                     tilledMatrix[x][y] = objWeave.getDesignMatrix()[x%objWeave.getIntWeft()][y%objWeave.getIntWarp()];  
                }else{
                     tilledMatrix[x][y] = objWeave.getDesignMatrix()[x][y]; 
                }
            }
        }
        int a=startNumPattern; // starting number of combination
        int ch=a;
        for(int x = 0, p = 0; x < height; x++) {
            a=ch;
            for(int y = 0, q = 0; y < width; y++) {
                if(a>=totalCombinations)
                    break;
                for(int i = 0; i < bands; i++) {
                    for(int j = 0; j < bands; j++) {                        
                        if(tilledMatrix[x][y]==0){
                            if(i==0)
                                rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getBlue()).brighter().getRGB();
                                //rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getBlue()).brighter().getRGB();
                            else if(i==2)
                                rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getBlue()).darker().getRGB();
                                //rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getBlue()).darker().getRGB();
                            else
                                rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getBlue()).getRGB();
                                //rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][warpCount+(x%weftCount)]).getBlue()).getRGB();
                        } else if(tilledMatrix[x][y]==1){
                            if(j==0)
                                rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getBlue()).brighter().getRGB();
                                //rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getBlue()).brighter().getRGB();
                            else if(j==2)
                                rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getBlue()).darker().getRGB();
                                //rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getBlue()).darker().getRGB();
                            else
                                rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][colorIndex[x%intWeftCount][y%intWarpCount]]).getBlue()).getRGB();
                                //rgb=new java.awt.Color((float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getRed(),(float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getGreen(),(float)javafx.scene.paint.Color.web(colorPermutation[a][y%warpCount]).getBlue()).getRGB();
                        } else {
                            if(i==0)
                                rgb = new java.awt.Color((float)javafx.scene.paint.Color.web("#FF0000").getRed(),(float)javafx.scene.paint.Color.web("#FF0000").getGreen(),(float)javafx.scene.paint.Color.web("#FF0000").getBlue()).brighter().getRGB();
                            else if(i==2)
                                rgb = new java.awt.Color((float)javafx.scene.paint.Color.web("#FF0000").getRed(),(float)javafx.scene.paint.Color.web("#FF0000").getGreen(),(float)javafx.scene.paint.Color.web("#FF0000").getBlue()).darker().getRGB();
                            else
                                rgb = new java.awt.Color((float)javafx.scene.paint.Color.web("#FF0000").getRed(),(float)javafx.scene.paint.Color.web("#FF0000").getGreen(),(float)javafx.scene.paint.Color.web("#FF0000").getBlue()).getRGB();
                        }
                        bufferedImage.setRGB(q+j, p+i, rgb);
                    }
                }
                q+=bands;
                if((y%(width/pageDiv))==0&&y!=0){
                    a++;
                }
            }
            p+=bands;
            if((x%(height/pageDiv))==0&&x!=0){
                ch+=pageDiv;
            }
        }

        Graphics2D g=bufferedImage.createGraphics();
        BasicStroke bs = new BasicStroke(5);
        g.setStroke(bs);
        for(int z=1; z<=(pageDiv-1); z++){
            g.drawLine(0, z*((height*3)/pageDiv), bufferedImage.getWidth()-1, z*((height*3)/pageDiv));
            g.drawLine(z*((width*3)/pageDiv), 0, z*((width*3)/pageDiv), bufferedImage.getHeight()-1);
        }
        
        final ImageView weaveIV=new ImageView();

        int intLength = (int)(((objWeave.getObjConfiguration().getIntDPI()*bufferedImage.getWidth())/objWeave.getIntEPI()));
        int intHeight = (int)(((objWeave.getObjConfiguration().getIntDPI()*bufferedImage.getHeight())/objWeave.getIntPPI()));
        intHeight=(int)(intHeight*(objWeave.getObjConfiguration().WIDTH/intLength));
        intLength=(int)objWeave.getObjConfiguration().WIDTH;
        BufferedImage bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g1 = bufferedImageesize.createGraphics();
        g1.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
        g1.dispose();

        weaveIV.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));        
        weaveIV.setFitHeight(objConfiguration.HEIGHT - 150);
        weaveIV.setFitWidth(objConfiguration.WIDTH);
        
        final double ratio=(double)(objConfiguration.HEIGHT)/(double)(objConfiguration.HEIGHT-150);
        weaveIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                double mouseX=t.getX();
                double mouseY=t.getY()*ratio;
                for(int i=1; i<=pageDiv; i++){
                    for(int j=1; j<=pageDiv; j++){
                        if((mouseX>=((i-1)*patternW))&&(mouseX<(i*patternW))&&(mouseY>=((j-1)*patternH))&&(mouseY<(j*patternH))){
                            if((startNumPattern+((j-1)*pageDiv)+(i-1))<totalCombinations)
                            //System.out.println("Selected Pattern: "+(((j-1)*pageDiv)+(i-1)));
                                plotShadedDesignParam((startNumPattern+((j-1)*pageDiv)+(i-1)), colorPermutation, colorIndex);
                        }
                    }
                }
            }
        });

        return weaveIV;
    }
static int factorial(int n) {
        if (n == 0) {
            return 1;
        }
        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

public void permute(java.util.List<String> arr, int k, List<List<String>> list){
        for(int i = k; i < arr.size(); i++){
            java.util.Collections.swap(arr, i, k);
            permute(arr, k+1, list);
            java.util.Collections.swap(arr, k, i);
        }
        if (k == arr.size() -1){
            //System.out.println(java.util.Arrays.toString(arr.toArray()));
            List<String> l=new ArrayList(arr);
            list.add(l);
        }
    }
/*
private void colourwaysAction(){
    ColorCombination cc=new ColorCombination();
    //System.out.println("warp"+objWeave.getWarpYarn()[0].getStrYarnColor()+"weft"+objWeave.getIntWeft());
    //objWeave.getObjConfiguration().setWarpYarn(objWeave.getWarpYarn());
    //objWeave.getObjConfiguration().setWeftYarn(objWeave.getWeftYarn());
    ArrayList<Integer> warpPattern=new ArrayList<>();
    ArrayList<Integer> weftPattern=new ArrayList<>();
    for(int wp=0; wp<objWeave.getWarpYarn().length; wp++){
        for(int i=0; i<26; i++){
            if(objWeave.getWarpYarn()[wp].equals(objWeave.getObjConfiguration().getYarnPalette()[i])){
                warpPattern.add(i);
                System.out.println("Added Wp: "+i);
            }
        }
    }
    for(int wf=0; wf<objWeave.getWeftYarn().length; wf++){
        for(int i=0; i<26; i++){
            if(objWeave.getWeftYarn()[wf].equals(objWeave.getObjConfiguration().getYarnPalette()[i+26])){
                weftPattern.add(i+26);
                System.out.println("Added Wf: "+(i+26));
            }
        }
    }
    cc.intWarpPattern=convertIntegers(warpPattern);
    
    cc.intWeftPattern=convertIntegers(weftPattern);
    System.out.println(cc.intWarpPattern.length+":::"+cc.intWeftPattern.length);
    //for(int i=0; i<2; i++){
    //    cc.strPalette[i]=objWeave.getWarpYarn()[i].getStrYarnColor();
    //    cc.strPalette[i+26]=objWeave.getWeftYarn()[i].getStrYarnColor();
    //}
    cc.strPalette=objWeave.getObjConfiguration().strThreadPalettes;//objConfiguration.strThreadPaletes;
    cc.niam();
    lblStatus.setText(objDictionaryAction.getWord("ACTIONTEXTUREVIEW"));
    int height=(int)(objConfiguration.HEIGHT/3);
    int width=(int)(objConfiguration.WIDTH/3);
    byte[][] tilledMatrix = new byte[height][width];
    BufferedImage bufferedImage = new BufferedImage(width*3, height*3,BufferedImage.TYPE_INT_RGB);
    int bands = 3;
    int rgb = 0;
    int combPerPage=16;
    int pageDiv=(int)Math.sqrt(combPerPage);

    int a=0; // starting number of combination
    int ch=a;
    for(int x = 0, p = 0; x < height; x++) {
        a=ch;
        for(int y = 0, q = 0; y < width; y++) {
            if(x>=objWeave.getIntWeft() && y<objWeave.getIntWarp()){
                 tilledMatrix[x][y] = objWeave.getDesignMatrix()[x%objWeave.getIntWeft()][y];  
            }else if(x<objWeave.getIntWeft() && y>=objWeave.getIntWarp()){
                 tilledMatrix[x][y] = objWeave.getDesignMatrix()[x][y%objWeave.getIntWarp()];  
            }else if(x>=objWeave.getIntWeft() && y>=objWeave.getIntWarp()){
                 tilledMatrix[x][y] = objWeave.getDesignMatrix()[x%objWeave.getIntWeft()][y%objWeave.getIntWarp()];  
            }else{
                 tilledMatrix[x][y] = objWeave.getDesignMatrix()[x][y]; 
            }
            for(int i = 0; i < bands; i++) {
                for(int j = 0; j < bands; j++) {                        
                    if(tilledMatrix[x][y]==0){
                        if(i==0)
                            rgb=new java.awt.Color((float)javafx.scene.paint.Color.web("#"+cc.paletteCombination[a][26+x%objWeave.getIntWeft()]).getRed(),(float)javafx.scene.paint.Color.web("#"+cc.paletteCombination[a][26+x%objWeave.getIntWeft()]).getGreen(),(float)javafx.scene.paint.Color.web("#"+cc.paletteCombination[a][26+x%objWeave.getIntWeft()]).getBlue()).brighter().getRGB();
                            //rgb = getIntRgbFromColor(255
                                //, Integer.parseInt(cc.paletteCombination[a][x%objWeave.getIntWeft()].substring(0, 2), 16)
                                //, Integer.parseInt(cc.paletteCombination[a][x%objWeave.getIntWeft()].substring(2, 4), 16)
                                //, Integer.parseInt(cc.paletteCombination[a][x%objWeave.getIntWeft()].substring(4, 6), 16));
                        else if(i==2)
                            rgb=new java.awt.Color((float)javafx.scene.paint.Color.web("#"+cc.paletteCombination[a][26+x%objWeave.getIntWeft()]).getRed(),(float)javafx.scene.paint.Color.web("#"+cc.paletteCombination[a][26+x%objWeave.getIntWeft()]).getGreen(),(float)javafx.scene.paint.Color.web("#"+cc.paletteCombination[a][26+x%objWeave.getIntWeft()]).getBlue()).darker().getRGB();
                            //rgb = getIntRgbFromColor(255
                                //, Integer.parseInt(cc.paletteCombination[a][x%objWeave.getIntWeft()].substring(0, 2), 16)
                                //, Integer.parseInt(cc.paletteCombination[a][x%objWeave.getIntWeft()].substring(2, 4), 16)
                                //, Integer.parseInt(cc.paletteCombination[a][x%objWeave.getIntWeft()].substring(4, 6), 16));
                        else
                            rgb=new java.awt.Color((float)javafx.scene.paint.Color.web("#"+cc.paletteCombination[a][26+x%objWeave.getIntWeft()]).getRed(),(float)javafx.scene.paint.Color.web("#"+cc.paletteCombination[a][26+x%objWeave.getIntWeft()]).getGreen(),(float)javafx.scene.paint.Color.web("#"+cc.paletteCombination[a][26+x%objWeave.getIntWeft()]).getBlue()).getRGB();
                            //rgb = getIntRgbFromColor(255
                                //, Integer.parseInt(cc.paletteCombination[a][x%objWeave.getIntWeft()].substring(0, 2), 16)
                                //, Integer.parseInt(cc.paletteCombination[a][x%objWeave.getIntWeft()].substring(2, 4), 16)
                                //, Integer.parseInt(cc.paletteCombination[a][x%objWeave.getIntWeft()].substring(4, 6), 16));
                    } else if(tilledMatrix[x][y]==1){
                        if(j==0)
                            rgb=new java.awt.Color((float)javafx.scene.paint.Color.web("#"+cc.paletteCombination[a][y%objWeave.getIntWarp()]).getRed(),(float)javafx.scene.paint.Color.web("#"+cc.paletteCombination[a][y%objWeave.getIntWarp()]).getGreen(),(float)javafx.scene.paint.Color.web("#"+cc.paletteCombination[a][y%objWeave.getIntWarp()]).getBlue()).brighter().getRGB();
                            //rgb = getIntRgbFromColor(255
                                //, Integer.parseInt(cc.paletteCombination[a][y%objWeave.getIntWarp()].substring(0, 2), 16)
                                //, Integer.parseInt(cc.paletteCombination[a][y%objWeave.getIntWarp()].substring(2, 4), 16)
                                //, Integer.parseInt(cc.paletteCombination[a][y%objWeave.getIntWarp()].substring(4, 6), 16));
                        else if(j==2)
                            rgb=new java.awt.Color((float)javafx.scene.paint.Color.web("#"+cc.paletteCombination[a][y%objWeave.getIntWarp()]).getRed(),(float)javafx.scene.paint.Color.web("#"+cc.paletteCombination[a][y%objWeave.getIntWarp()]).getGreen(),(float)javafx.scene.paint.Color.web("#"+cc.paletteCombination[a][y%objWeave.getIntWarp()]).getBlue()).darker().getRGB();
                            //rgb = getIntRgbFromColor(255
                                //, Integer.parseInt(cc.paletteCombination[a][y%objWeave.getIntWarp()].substring(0, 2), 16)
                                //, Integer.parseInt(cc.paletteCombination[a][y%objWeave.getIntWarp()].substring(2, 4), 16)
                                //, Integer.parseInt(cc.paletteCombination[a][y%objWeave.getIntWarp()].substring(4, 6), 16));
                        else
                            rgb=new java.awt.Color((float)javafx.scene.paint.Color.web("#"+cc.paletteCombination[a][y%objWeave.getIntWarp()]).getRed(),(float)javafx.scene.paint.Color.web("#"+cc.paletteCombination[a][y%objWeave.getIntWarp()]).getGreen(),(float)javafx.scene.paint.Color.web("#"+cc.paletteCombination[a][y%objWeave.getIntWarp()]).getBlue()).getRGB();
                            //rgb = getIntRgbFromColor(255
                                //, Integer.parseInt(cc.paletteCombination[a][y%objWeave.getIntWarp()].substring(0, 2), 16)
                                //, Integer.parseInt(cc.paletteCombination[a][y%objWeave.getIntWarp()].substring(2, 4), 16)
                                //, Integer.parseInt(cc.paletteCombination[a][y%objWeave.getIntWarp()].substring(4, 6), 16));
                    } else {
                        if(i==0)
                            rgb = new java.awt.Color((float)javafx.scene.paint.Color.web("#FF0000").getRed(),(float)javafx.scene.paint.Color.web("#FF0000").getGreen(),(float)javafx.scene.paint.Color.web("#FF0000").getBlue()).brighter().getRGB();
                        else if(i==2)
                            rgb = new java.awt.Color((float)javafx.scene.paint.Color.web("#FF0000").getRed(),(float)javafx.scene.paint.Color.web("#FF0000").getGreen(),(float)javafx.scene.paint.Color.web("#FF0000").getBlue()).darker().getRGB();
                        else
                            rgb = new java.awt.Color((float)javafx.scene.paint.Color.web("#FF0000").getRed(),(float)javafx.scene.paint.Color.web("#FF0000").getGreen(),(float)javafx.scene.paint.Color.web("#FF0000").getBlue()).getRGB();
                    }
                    bufferedImage.setRGB(q+j, p+i, rgb);
                }
            }
            q+=bands;
            if((y%(width/pageDiv))==0){
                a++;
            }
        }
        p+=bands;
        if((x%(height/pageDiv))==0){
            ch+=pageDiv;
        }
    }
    
    Graphics2D g=bufferedImage.createGraphics();
    BasicStroke bs = new BasicStroke(2);
    g.setStroke(bs);
    for(int z=1; z<=(pageDiv-1); z++){
        g.drawLine(0, z*((height*3)/pageDiv), bufferedImage.getWidth()-1, z*((height*3)/pageDiv));
        g.drawLine(z*((width*3)/pageDiv), 0, z*((width*3)/pageDiv), bufferedImage.getHeight()-1);
    }
    final Stage dialogStage = new Stage();
    dialogStage.initStyle(StageStyle.UTILITY);
    BorderPane popup=new BorderPane();
    final ImageView weaveIV=new ImageView();
    weaveIV.setImage(SwingFXUtils.toFXImage(bufferedImage, null));        
    weaveIV.setFitHeight(objConfiguration.HEIGHT);
    weaveIV.setFitWidth(objConfiguration.WIDTH);
    popup.setCenter(weaveIV);        
    popup.setId("popup");
    Scene popupScene = new Scene(popup);

    //For zoom Out weaving pattern
    final KeyCombination zoomOutKC = new KeyCodeCombination(KeyCode.SUBTRACT,KeyCombination.ALT_DOWN);
    //For undo weaving pattern
    final KeyCombination zoomInKC = new KeyCodeCombination(KeyCode.ADD,KeyCombination.ALT_DOWN);
    //For redo weaving pattern
    final KeyCombination normalKC = new KeyCodeCombination(KeyCode.EQUALS,KeyCombination.ALT_DOWN);
    //For redo weaving pattern
    final KeyCombination closeKC = new KeyCodeCombination(KeyCode.ESCAPE,KeyCombination.ALT_DOWN);

    popupScene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler() {
        @Override
        public void handle(Event t) {
            if (closeKC.match((KeyEvent) t)){
                dialogStage.close();
            } else if (normalKC.match((KeyEvent) t)){
                weaveIV.setScaleX(1);
                weaveIV.setScaleY(1);
                weaveIV.setScaleZ(1);
            } else if (zoomInKC.match((KeyEvent) t)){
                weaveIV.setScaleX(weaveIV.getScaleX()*2);
                weaveIV.setScaleY(weaveIV.getScaleY()*2);
                weaveIV.setScaleZ(weaveIV.getScaleZ()*2);
            } else if (zoomOutKC.match((KeyEvent) t)){
                weaveIV.setScaleX(weaveIV.getScaleX()/2);
                weaveIV.setScaleY(weaveIV.getScaleY()/2);
                weaveIV.setScaleZ(weaveIV.getScaleZ()/2);
            }
        }
    });
    popupScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent t) {
            KeyCode key = t.getCode();
            if (key == KeyCode.ESCAPE){
                dialogStage.close();
            } else if (key == KeyCode.ENTER){
                weaveIV.setScaleX(1);
                weaveIV.setScaleY(1);
                weaveIV.setScaleZ(1);
            } else if (key == KeyCode.UP){
                weaveIV.setScaleX(weaveIV.getScaleX()*2);
                weaveIV.setScaleY(weaveIV.getScaleY()*2);
                weaveIV.setScaleZ(weaveIV.getScaleZ()*2);
            } else if (key == KeyCode.DOWN){
                weaveIV.setScaleX(weaveIV.getScaleX()/2);
                weaveIV.setScaleY(weaveIV.getScaleY()/2);
                weaveIV.setScaleZ(weaveIV.getScaleZ()/2);
            }
        }
    });

    popupScene.getStylesheets().add(WeaveView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
    dialogStage.setScene(popupScene);
    dialogStage.setX(-5);
    dialogStage.setY(0);
    dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("ACTIONTEXTUREVIEW"));
    dialogStage.showAndWait();

    tilledMatrix=null;
    bufferedImage = null;
    System.gc();
}*/

private void ZoomNormalAction(){
    try {
        zoomImages(1.00);
        //setScrollBarAtEnd();
        /*boxSize=15;
        plotDesign();
        plotShaft();
        plotPeg();
        plotTieUp();
        plotTradeles();
        plotWarpColor();
        plotWeftColor();
        plotDenting();
        lblStatus.setText(objDictionaryAction.getWord("ACTIONZOOMNORMALVIEW"));*/
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),"Normal View",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}

private void zoomInAction(){
    try {
        zoomImages(1.50);
        //setScrollBarAtEnd();
        /*if(boxSize<=60){
            boxSize*=2;
            plotDesign();
            plotShaft();
            plotPeg();
            plotTieUp();
            plotTradeles();
            plotWarpColor();
            plotWeftColor();
            plotDenting();
            lblStatus.setText(objDictionaryAction.getWord("ACTIONZOOMINVIEW"));
        }else{
            lblStatus.setText(objDictionaryAction.getWord("MAXZOOMIN"));
        }*/
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void zoomOutAction(){
    try {
        zoomImages(1.00);
        //setScrollBarAtEnd();
        /*if(boxSize>=4){
            boxSize/=2;
            plotDesign();
            plotShaft();
            plotPeg();
            plotTieUp();
            plotTradeles();
            plotWarpColor();
            plotWeftColor();
            plotDenting();
            lblStatus.setText(objDictionaryAction.getWord("ACTIONZOOMOUTVIEW"));
        }else{
            lblStatus.setText(objDictionaryAction.getWord("MAXZOOMOUT"));
        }*/
    } catch (Exception ex) {
       new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
       lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}

    private void assignKeyAction(){
        //Assigning shortcut keys to weave editor functionality
        //For new weaving pattern  
        final KeyCombination newKC = new KeyCodeCombination(KeyCode.N,KeyCombination.CONTROL_DOWN);
        //For open weaving pattern from database
        final KeyCombination openKC = new KeyCodeCombination(KeyCode.O,KeyCombination.CONTROL_DOWN);
        //For saving weaving pattern in database
        final KeyCombination saveKC = new KeyCodeCombination(KeyCode.S,KeyCombination.CONTROL_DOWN);
        //For importing weaving pattern
        final KeyCombination importKC = new KeyCodeCombination(KeyCode.I,KeyCombination.CONTROL_DOWN);
        //For exporting weaving pattern
        final KeyCombination exportKC = new KeyCodeCombination(KeyCode.E,KeyCombination.CONTROL_DOWN);
        //For exiting from weave editor
        final KeyCombination closeKC = new KeyCodeCombination(KeyCode.Q,KeyCombination.CONTROL_DOWN);
        //For zoom In weaving pattern
        final KeyCombination zoomInKC = new KeyCodeCombination(KeyCode.ADD,KeyCombination.CONTROL_DOWN);
        //For zoom Out weaving pattern
        final KeyCombination zoomOutKC = new KeyCodeCombination(KeyCode.SUBTRACT,KeyCombination.CONTROL_DOWN);
        //For undo weaving pattern
        final KeyCombination undoKC = new KeyCodeCombination(KeyCode.Z,KeyCombination.CONTROL_DOWN);
        //For redo weaving pattern
        final KeyCombination redoKC = new KeyCodeCombination(KeyCode.Y,KeyCombination.CONTROL_DOWN);
        //For yarn weaving pattern
        final KeyCombination yarnKC = new KeyCodeCombination(KeyCode.T,KeyCombination.CONTROL_DOWN);

        scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler() {
            @Override
            public void handle(Event t) {
                if (newKC.match((KeyEvent) t)) 
                    newAction();
                else if (openKC.match((KeyEvent) t))
                    openAction();
                else if (saveKC.match((KeyEvent) t))
                    saveAction();
                else if (importKC.match((KeyEvent) t))
                    importAction();
                else if (closeKC.match((KeyEvent) t))
                    closeAction();
                else if (zoomInKC.match((KeyEvent) t))
                    zoomInAction();
                else if (zoomOutKC.match((KeyEvent) t))
                    zoomOutAction();
                else if (undoKC.match((KeyEvent) t))
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    //undoAction();  
                else if (redoKC.match((KeyEvent) t))
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    //redoAction();
                else if (yarnKC.match((KeyEvent) t))
                    editYarn();
            }
        });
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                KeyCode key = t.getCode();
                if (key == KeyCode.ESCAPE){
                     
                }
            }
        });
    }
    
    public void initObjWeave(boolean mode){
        //objWeave.setStrThreadPalettes(objWeave.getObjConfiguration().strThreadPalettes);
        populateYarnPalette();
        if(!mode){
            objWeave.setIntWarp(activeGridCol);
            objWeave.setIntWeft(activeGridRow);
            objWeave.setIntWeaveShaft(activeGridRow);
            objWeave.setIntWeaveTreadles(activeGridCol);
            objWeave.setShaftMatrix(new byte[activeGridRow][activeGridCol]);
            objWeave.setTreadlesMatrix(new byte[activeGridRow][activeGridCol]);
            objWeave.setTieupMatrix(new byte[activeGridRow][activeGridCol]);
            objWeave.setPegMatrix(new byte[activeGridRow][activeGridRow]);
            objWeave.setDesignMatrix(new byte[activeGridRow][activeGridCol]);
            byte[][] dentMatrix=new byte[2][activeGridCol];
            for(int c=0; c<activeGridCol; c++){
                dentMatrix[0][c]=(byte)(c%2);
                dentMatrix[1][c]=(byte)((c+1)%2);
            }
            objWeave.setDentMatrix(dentMatrix);
            warpYarn = new Yarn[activeGridCol];
            for(int i=0; i<activeGridCol; i++)
                warpYarn[i] = new Yarn(null, "Warp", objConfiguration.getStrWarpName(), "#"+objConfiguration.getStrWarpColor(), objConfiguration.getIntWarpRepeat(), "A", objConfiguration.getIntWarpCount(), objConfiguration.getStrWarpUnit(), objConfiguration.getIntWarpPly(), objConfiguration.getIntWarpFactor(), objConfiguration.getDblWarpDiameter(), objConfiguration.getIntWarpTwist(), objConfiguration.getStrWarpSence(), objConfiguration.getIntWarpHairness(), objConfiguration.getIntWarpDistribution(), objConfiguration.getDblWarpPrice(), objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"),objConfiguration.getObjUser().getStrUserID(),null);
            objWeave.setWarpYarn(warpYarn);
            weftYarn = new Yarn[activeGridRow];
            for(int i=0; i<activeGridRow; i++)
                weftYarn[i] = new Yarn(null, "Weft", objConfiguration.getStrWeftName(), "#"+objConfiguration.getStrWeftColor(), objConfiguration.getIntWeftRepeat(), "a", objConfiguration.getIntWeftCount(), objConfiguration.getStrWeftUnit(), objConfiguration.getIntWeftPly(), objConfiguration.getIntWeftFactor(), objConfiguration.getDblWeftDiameter(), objConfiguration.getIntWeftTwist(), objConfiguration.getStrWeftSence(), objConfiguration.getIntWeftHairness(), objConfiguration.getIntWeftDistribution(), objConfiguration.getDblWeftPrice(), objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"),objConfiguration.getObjUser().getStrUserID(),null);
            objWeave.setWeftYarn(weftYarn);
        }
        else{
            objWeave.setShaftMatrix(new byte[objWeave.getIntWeaveShaft()][objWeave.getIntWarp()]);
            objWeave.setTreadlesMatrix(new byte[objWeave.getIntWeft()][objWeave.getIntWeaveTreadles()]);
            objWeave.setTieupMatrix(new byte[objWeave.getIntWeaveShaft()][objWeave.getIntWeaveTreadles()]);
            objWeave.setPegMatrix(new byte[objWeave.getIntWeft()][objWeave.getIntWeaveShaft()]);
            objWeave.setDesignMatrix(new byte[objWeave.getIntWeft()][objWeave.getIntWarp()]);
            byte[][] dentMatrix=new byte[2][objWeave.getIntWarp()];
            for(int c=0; c<objWeave.getIntWarp(); c++){
                dentMatrix[0][c]=(byte)(c%2);
                dentMatrix[1][c]=(byte)((c+1)%2);
            }
            objWeave.setDentMatrix(dentMatrix);
            warpYarn = new Yarn[objWeave.getIntWarp()];
            for(int i=0; i<objWeave.getIntWarp(); i++){
                if(i<objWeave.getWarpYarn().length&&objWeave.getWarpYarn()[i]!=null)
                    warpYarn[i]=objWeave.getWarpYarn()[i];
                else
                    warpYarn[i] = new Yarn(null, "Warp", objConfiguration.getStrWarpName(), "#"+objConfiguration.getStrWarpColor(), objConfiguration.getIntWarpRepeat(), "A", objConfiguration.getIntWarpCount(), objConfiguration.getStrWarpUnit(), objConfiguration.getIntWarpPly(), objConfiguration.getIntWarpFactor(), objConfiguration.getDblWarpDiameter(), objConfiguration.getIntWarpTwist(), objConfiguration.getStrWarpSence(), objConfiguration.getIntWarpHairness(), objConfiguration.getIntWarpDistribution(), objConfiguration.getDblWarpPrice(), objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"),objConfiguration.getObjUser().getStrUserID(),null);
            }
            objWeave.setWarpYarn(warpYarn);
            weftYarn = new Yarn[objWeave.getIntWeft()];
            for(int i=0; i<objWeave.getIntWeft(); i++){
                if(i<objWeave.getWeftYarn().length&&objWeave.getWeftYarn()[i]!=null){
                    weftYarn[objWeave.getIntWeft()-1-i]=objWeave.getWeftYarn()[objWeave.getWeftYarn().length-1-i];
                }   
                else{
                    weftYarn[objWeave.getIntWeft()-1-i] = new Yarn(null, "Weft", objConfiguration.getStrWeftName(), "#"+objConfiguration.getStrWeftColor(), objConfiguration.getIntWeftRepeat(), "a", objConfiguration.getIntWeftCount(), objConfiguration.getStrWeftUnit(), objConfiguration.getIntWeftPly(), objConfiguration.getIntWeftFactor(), objConfiguration.getDblWeftDiameter(), objConfiguration.getIntWeftTwist(), objConfiguration.getStrWeftSence(), objConfiguration.getIntWeftHairness(), objConfiguration.getIntWeftDistribution(), objConfiguration.getDblWeftPrice(), objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"),objConfiguration.getObjUser().getStrUserID(),null);
                }
            }   
            objWeave.setWeftYarn(weftYarn);
        }
        
        for(int i=0; i<objWeave.getWarpYarn().length; i++){
            for(int j=0; j<26; j++)
                if(objWeave.getWarpYarn()[i].getStrYarnSymbol().equals(objWeave.getObjConfiguration().getYarnPalette()[j].getStrYarnSymbol()))
                    objWeave.getWarpYarn()[i]=objWeave.getObjConfiguration().getYarnPalette()[j];
        }
        for(int i=0; i<objWeave.getWeftYarn().length; i++){
            for(int j=26; j<52; j++)
                if(objWeave.getWeftYarn()[i].getStrYarnSymbol().equals(objWeave.getObjConfiguration().getYarnPalette()[j].getStrYarnSymbol()))
                    objWeave.getWeftYarn()[i]=objWeave.getObjConfiguration().getYarnPalette()[j];
        }

        populateColorPalette();
        objWeave.setDesignMatrix(getDotDesignMatrix());
        plotDesign();
        plotShaft();
        plotPeg();
        plotTieUp();
        plotTradeles();
        plotWarpColor();
        plotWeftColor();
        plotDenting();
        plotColorPalette();
        
        isNew = true;
        isWorkingMode = true;
        //menuHighlight(null);                
    }
    
    public void updateObjWeave(){
        objWeave.setDesignMatrix(getDotDesignMatrix());
    }
    
    // design image
    
    /**
     * Resets design grid by reading horizontally mirrored dot
     * @param col
     * @param row 
     */
    public void resetActiveDesignGrid(int col, int row){
        if(designImage==null)
            return;
        clearImage(designImage);
        for(int a=0; a<row; a++){
            for(int b=0; b<col; b++){
                fillImageDotPixels(new Point(b, (MAX_GRID_ROW-1)-a), INT_SOLID_WHITE, designImage);
            }
        }
    }
    
    public void updateActiveDesignGrid(int newCol, int newRow){
        if(newCol>activeGridCol){ // column(s) extended
            int addedCol=newCol-activeGridCol;
            for(int a=0; a<activeGridRow; a++){
                for(int b=0; b<addedCol; b++){
                    fillImageDotPixels(new Point(activeGridCol+b, (MAX_GRID_ROW-1)-a), INT_SOLID_WHITE, designImage);
                }
            }
            activeGridCol=newCol;
        }
        else if(newCol<activeGridCol){ // column(s) truncated
            int truncatedCol=activeGridCol-newCol;
            for(int a=0; a<activeGridRow; a++){
                for(int b=0; b<truncatedCol; b++){
                    fillImageDotPixels(new Point(activeGridCol-(b+1), (MAX_GRID_ROW-1)-a), INT_TRANSPARENT, designImage);
                }
            }
            activeGridCol=newCol;
        }
        
        if(newRow>activeGridRow){ // row(s) extended
            int addedRow=newRow-activeGridRow;
            for(int a=0; a<addedRow; a++){
                for(int b=0; b<activeGridCol; b++){
                    fillImageDotPixels(new Point(b, (MAX_GRID_ROW-1)-(activeGridRow+a)), INT_SOLID_WHITE, designImage);
                }
            }
            activeGridRow=newRow;
        }
        else if(newRow<activeGridRow){ // row(s) truncated
            int truncatedRow=activeGridRow-newRow;
            for(int a=0; a<truncatedRow; a++){
                for(int b=0; b<activeGridCol; b++){
                    fillImageDotPixels(new Point(b, (MAX_GRID_ROW-1)-(activeGridRow-a)+1), INT_TRANSPARENT, designImage);
                }
            }
            activeGridRow=newRow;
        }
        hSlider.setValue(activeGridCol);
        vSlider.setValue(activeGridRow);
        objWeave.setIntWarp(activeGridCol);
        objWeave.setIntWeft(activeGridRow);
        if(activeGridRow<=current_row)
            current_row=0;
        if(activeGridCol<=current_col)
            current_col=0;
    }
    
    public void clearImage(BufferedImage image){
        int row=MAX_GRID_ROW;
        int col=MAX_GRID_COL;
        if(image.equals(dentingImage))
            row=2;
        else if(image.equals(warpColorImage))
            row=1;
        else if(image.equals(weftColorImage))
            col=1;
        
        for(int a=0; a<row; a++)
            for(int b=0; b<col; b++)
                fillImageDotPixels(new Point(b, a), INT_TRANSPARENT, image);
    }
    
    public void refreshDesignImage(){
        designIV.setImage(SwingFXUtils.toFXImage(designImage, null));
    }
    
    public void refreshShaftImage(){
        shaftIV.setImage(SwingFXUtils.toFXImage(shaftImage, null));
    }
    
    public void refreshPegImage(){
        pegIV.setImage(SwingFXUtils.toFXImage(pegImage, null));
    }
    
    public void refreshTieUpImage(){
        tieUpIV.setImage(SwingFXUtils.toFXImage(tieUpImage, null));
    }
    
    public void refreshTradelesImage(){
        tradelesIV.setImage(SwingFXUtils.toFXImage(tradelesImage, null));
    }
    
    public void refreshDentingImage(){
        dentingIV.setImage(SwingFXUtils.toFXImage(dentingImage, null));
    }
    
    public void refreshWarpColorImage(){
        warpColorIV.setImage(SwingFXUtils.toFXImage(warpColorImage, null));
    }
    
    public void refreshWeftColorImage(){
        weftColorIV.setImage(SwingFXUtils.toFXImage(weftColorImage, null));
    }
    
    public boolean isGridDotActive(Point p){
        if(p.getX()>=0&&(p.getX()<=(activeGridCol-1))&&(p.getY()>=(MAX_GRID_ROW-activeGridRow))&&p.getY()<MAX_GRID_ROW)
            return true;
        else
            return false;
    }
    
    public byte[][] getDotDesignMatrix(){
        byte[][] designMatrix=new byte[activeGridRow][activeGridCol];
        Point p=new Point();
        for(int r=0; r<activeGridRow; r++){
            for(int c=0; c<activeGridCol; c++){
                p.x=c;
                p.y=(MAX_GRID_ROW-1)-((activeGridRow-1)-r);
                if(getDotColor(p, designImage)==INT_SOLID_BLACK)
                    designMatrix[r][c]=1;
                else if(getDotColor(p, designImage)==INT_SOLID_WHITE)
                    designMatrix[r][c]=0;
            }
        }
        return designMatrix;
    }
    
    public void drawRepeatImageFromMatrix(byte[][] matrix, BufferedImage image){
        if(matrix==null||matrix.length==0||image.equals(tieUpImage))
            return;
        clearImage(image);
        Point p=new Point();
        int row=matrix.length;
        int col=matrix[0].length;
        int maxCountRow=0;
        int maxCountCol=0;
        if(image.equals(designImage)){
            maxCountRow=MAX_GRID_ROW;
            maxCountCol=MAX_GRID_COL;
        }
        else if(image.equals(shaftImage)){
            maxCountRow=row;
            maxCountCol=MAX_GRID_COL;
        }
        else if(image.equals(pegImage)){
            maxCountRow=MAX_GRID_ROW;
            maxCountCol=col;
        }
        else if(image.equals(tradelesImage)){
            maxCountRow=MAX_GRID_ROW;
            maxCountCol=col;
        }
        else if(image.equals(dentingImage)){
            maxCountRow=2;
            maxCountCol=MAX_GRID_COL;
        }
        for(int r=maxCountRow-1; r>=0; r--){
            for(int c=0; c<=maxCountCol-1; c++){
                p.x=c;
                p.y=r;
                if(image.equals(shaftImage)){
                    if(r>(row-1))
                        return;
                    else
                        p.y=(MAX_GRID_ROW-1)-((row-1)-r);
                }
                //if(matrix[(activeGridRow-1)-(((MAX_GRID_ROW-1)-r)%activeGridRow)][c%activeGridCol]==1){
                if(image.equals(dentingImage)){
                    if(matrix[(row-1)-(((maxCountRow-1)-r)%row)][c%col]==1){
                        if(p.y>=0&&p.y<=1&&p.x>=0&&p.x<activeGridCol)
                            fillImageDotPixels(p, INT_SOLID_BLACK, image);
                        else
                            fillImageDotPixels(p, INT_SOLID_GREY, image);
                    }
                    else if(matrix[(row-1)-(((maxCountRow-1)-r)%row)][c%col]==0){
                        if(p.y>=0&&p.y<=1&&p.x>=0&&p.x<activeGridCol)
                            fillImageDotPixels(p, INT_SOLID_WHITE, image);
                        else
                            fillGreyedDotPixels(p, INT_SOLID_WHITE, image);
                    }
                }
                else{
                    if(matrix[(row-1)-(((maxCountRow-1)-r)%row)][c%col]==1){
                        if(isGridDotActive(p))
                            fillImageDotPixels(p, INT_SOLID_BLACK, image);
                        else
                            fillImageDotPixels(p, INT_SOLID_GREY, image);
                    }
                    else if(matrix[(row-1)-(((maxCountRow-1)-r)%row)][c%col]==0){
                        if(isGridDotActive(p))
                            fillImageDotPixels(p, INT_SOLID_WHITE, image);
                        else
                            fillGreyedDotPixels(p, INT_SOLID_WHITE, image);
                    }
                }
            }
        }
        refreshImage(image);
    }
    
    public void refreshImage(BufferedImage image){
        if(image.equals(designImage))
            refreshDesignImage();
        else if(image.equals(shaftImage))
            refreshShaftImage();
        else if(image.equals(pegImage))
            refreshPegImage();
        else if(image.equals(tieUpImage))
            refreshTieUpImage();
        else if(image.equals(tradelesImage))
            refreshTradelesImage();
        else if(image.equals(warpColorImage))
            refreshWarpColorImage();
        else if(image.equals(weftColorImage))
            refreshWeftColorImage();
        else if(image.equals(dentingImage))
            refreshDentingImage();
    }
    
    public void drawImageFromMatrix(byte[][] matrix, BufferedImage image){
        if(matrix==null||matrix.length==0)
            return;
        clearImage(image);
        int row=matrix.length;
        int col=matrix[0].length;
        if(image.equals(designImage)){
            activeGridRow=row;
            activeGridCol=col;
            if(activeGridRow<=current_row)
                current_row=0;
            if(activeGridCol<=current_col)
                current_col=0;
        }
        Point p=new Point();
        for(int r=0; r<row; r++){
            for(int c=0; c<col; c++){
                p.x=c;
                p.y=(MAX_GRID_ROW-1)-((row-1)-r);
                if(image.equals(dentingImage))
                    p.y=r;
                if(matrix[r][c]==1)
                    fillImageDotPixels(p, INT_SOLID_BLACK, image);
                else if(matrix[r][c]==0)
                    fillImageDotPixels(p, INT_SOLID_WHITE, image);
            }
        }
        if(image.equals(designImage)){
            hSlider.setValue(activeGridCol);
            vSlider.setValue(activeGridRow);
        }
        refreshImage(image);
    }
    
    // pixel level operations
    public void fillDotBorder(Point p, BufferedImage image){
        int limit=(int)(10*currentZoomFactor);
        for(int b=1; b<limit-1; b++){
            image.setRGB((int)(10*p.x*currentZoomFactor)+b, (int)(10*p.y*currentZoomFactor)+1, INT_SOLID_RED);
            image.setRGB((int)(10*p.x*currentZoomFactor)+b, (int)(10*p.y*currentZoomFactor)+limit-2, INT_SOLID_RED);
            image.setRGB((int)(10*p.x*currentZoomFactor)+1, (int)(10*p.y*currentZoomFactor)+b, INT_SOLID_RED);
            image.setRGB((int)(10*p.x*currentZoomFactor)+limit-2, (int)(10*p.y*currentZoomFactor)+b, INT_SOLID_RED);
        }
    }
    
    public void invertDotColor(Point p, BufferedImage image){
        if(p.x<0||p.y<0||p.x>=MAX_GRID_COL||p.y>=MAX_GRID_ROW)
            return;
        if(image.getRGB((int)(10*p.getX()*currentZoomFactor)+2, (int)(10*p.getY()*currentZoomFactor)+2)==INT_SOLID_BLACK)
            fillImageDotPixels(p, INT_SOLID_WHITE, image);
        else if(image.getRGB((int)(10*p.getX()*currentZoomFactor)+2, (int)(10*p.getY()*currentZoomFactor)+2)==INT_SOLID_WHITE)
            fillImageDotPixels(p, INT_SOLID_BLACK, image);
    }
    
    public int getDotColor(Point p, BufferedImage image){
        if(p.x<0||p.y<0||p.x>=MAX_GRID_COL||p.y>=MAX_GRID_ROW)
            return INT_SOLID_WHITE;
        return image.getRGB((int)(10*p.getX()*currentZoomFactor)+2, (int)(10*p.getY()*currentZoomFactor)+2);
    }
    
    /**
     * Fills multiple pixels (contained in a dot point) inside an image
     * P.x: col, P.y:row of Dot Point
     * @author Aatif Ahmad Khan
     * @param p Dot point to be filled
     * @param image Image on which Dot point needs to be filled
     * @param color Fill color
     */
    public void fillImageDotPixels(Point p, int color, BufferedImage image){
        int limit=(int)(10*currentZoomFactor);
        for(int a=1; a<limit-1; a++)
            for(int b=1; b<limit-1; b++)
                image.setRGB((int)(10*p.x*currentZoomFactor)+b, (int)(10*p.y*currentZoomFactor)+a, color);
    }
    
    public void fillGreyedDotPixels(Point p, int color, BufferedImage image){
        int limit=(int)(10*currentZoomFactor);
        for(int a=1; a<limit-1; a++)
            for(int b=1; b<limit-1; b++)
                image.setRGB((int)(10*p.x*currentZoomFactor)+b, (int)(10*p.y*currentZoomFactor)+a, color);
        for(int b=1; b<limit-1; b++){
            image.setRGB((int)(10*p.x*currentZoomFactor)+b, (int)(10*p.y*currentZoomFactor)+1, INT_TRANSPARENT);
            image.setRGB((int)(10*p.x*currentZoomFactor)+b, (int)(10*p.y*currentZoomFactor)+limit-2, INT_TRANSPARENT);
            image.setRGB((int)(10*p.x*currentZoomFactor)+1, (int)(10*p.y*currentZoomFactor)+b, INT_TRANSPARENT);
            image.setRGB((int)(10*p.x*currentZoomFactor)+limit-2, (int)(10*p.y*currentZoomFactor)+b, INT_TRANSPARENT);
        }
    }
    
    /**
     * Returns actual dot on grid
     * @param pixelX
     * @param pixelY
     * @return Dot on grid
     */
    public Point getDotFromPixels(int pixelX, int pixelY){
        return new Point((int)(pixelX/(10*currentZoomFactor)), (int)(pixelY/(10*currentZoomFactor)));
    }
    
    // utilities
    
    /**
     * Returns all rectangular corner points coordinates
     * @author Aatif Ahmad Khan
     * @param xIntial drag start
     * @param yInitial drag start
     * @param xFinal drag end
     * @param yFinal drag end
     * @return all four points
     * (x1, y1)--------------(x4, y4)
     *        |              |
     *        |              |
     * (x2, y2)--------------(x3, y3)
     */
    private static int[][] getRectCorner(int xIntial, int yIntial, int xFinal, int yFinal){
        return new int[][]{
            {(xFinal>xIntial?xIntial:xFinal), (yFinal>yIntial?yIntial:yFinal)},
            {(xFinal>xIntial?xIntial:xFinal), (yFinal>yIntial?yFinal:yIntial)},
            {(xFinal>xIntial?xFinal:xIntial), (yFinal>yIntial?yFinal:yIntial)},
            {(xFinal>xIntial?xFinal:xIntial), (yFinal>yIntial?yIntial:yFinal)}
        };
    }
    
    /**
     * Returns solid color alpha=0xFF
     * @param red red value (0-255)
     * @param green value
     * @param blue
     * @return 
     */
    public int getIntRgbFromColor(int alpha, int red, int green, int blue){
        int rgb=alpha;
        rgb=(rgb << 8)+red;
        rgb=(rgb << 8)+green;
        rgb=(rgb << 8)+blue;
        return rgb;
    }
    
    public void checkDotInRow(Point dot){
        int rowInMatrix=(objWeave.getTreadlesMatrix().length-1)-((MAX_GRID_ROW-1)-dot.y);
        for(int c=0; c<objWeave.getTreadlesMatrix()[rowInMatrix].length; c++){
            if(objWeave.getTreadlesMatrix()[rowInMatrix][c]==1){
                dot.x=c;
                invertDotColor(dot, tradelesImage);
                objWeave.getTreadlesMatrix()[rowInMatrix][c]=0;
                break;
            }
        }
    }
    
    public void checkDotInCol(Point dot){
        for(int r=0; r<objWeave.getShaftMatrix().length; r++){
            if(objWeave.getShaftMatrix()[r][dot.x]==1){
                dot.y=(MAX_GRID_ROW-1)-(objWeave.getShaftMatrix().length-1)+r;
                invertDotColor(dot, shaftImage);
                objWeave.getShaftMatrix()[r][dot.x]=0;
                break;
            }
        }
    }
    
    public int getDesignMatrixRow(int dotRow){
        return (activeGridRow-1)-((MAX_GRID_ROW-1)-dotRow);
    }
    
    public void zoomImages(double zoomFactor){
        /*setZoomableScrollPane(designSP, designIV, zoomFactor);
        setZoomableScrollPane(dentingSP, dentingIV, zoomFactor);
        setZoomableScrollPane(shaftSP, shaftIV, zoomFactor);
        setZoomableScrollPane(pegSP, pegIV, zoomFactor);
        setZoomableScrollPane(tieUpSP, tieUpIV, zoomFactor);
        setZoomableScrollPane(tradelesSP, tradelesIV, zoomFactor);
        setZoomableScrollPane(warpColorSP, warpColorIV, zoomFactor);
        setZoomableScrollPane(weftColorSP, weftColorIV, zoomFactor);
        setZoomableScrollPane(hSliderSP, hSlider, zoomFactor);
        setZoomableScrollPane(vSliderSP, vSlider, zoomFactor);
        */
        zoomFactor=zoomFactor/currentZoomFactor;
        designIV.setImage(SwingFXUtils.toFXImage(getZoomedImage(designImage, zoomFactor), null));
        designImage=SwingFXUtils.fromFXImage(designIV.getImage(), null);
        shaftIV.setImage(SwingFXUtils.toFXImage(getZoomedImage(shaftImage, zoomFactor), null));
        shaftImage=SwingFXUtils.fromFXImage(shaftIV.getImage(), null);
        dentingIV.setImage(SwingFXUtils.toFXImage(getZoomedImage(dentingImage, zoomFactor), null));
        dentingImage=SwingFXUtils.fromFXImage(dentingIV.getImage(), null);
        warpColorIV.setImage(SwingFXUtils.toFXImage(getZoomedImage(warpColorImage, zoomFactor), null));
        warpColorImage=SwingFXUtils.fromFXImage(warpColorIV.getImage(), null);
        weftColorIV.setImage(SwingFXUtils.toFXImage(getZoomedImage(weftColorImage, zoomFactor), null));
        weftColorImage=SwingFXUtils.fromFXImage(weftColorIV.getImage(), null);
        pegIV.setImage(SwingFXUtils.toFXImage(getZoomedImage(pegImage, zoomFactor), null));
        pegImage=SwingFXUtils.fromFXImage(pegIV.getImage(), null);
        tieUpIV.setImage(SwingFXUtils.toFXImage(getZoomedImage(tieUpImage, zoomFactor), null));
        tieUpImage=SwingFXUtils.fromFXImage(tieUpIV.getImage(), null);
        tradelesIV.setImage(SwingFXUtils.toFXImage(getZoomedImage(tradelesImage, zoomFactor), null));
        tradelesImage=SwingFXUtils.fromFXImage(tradelesIV.getImage(), null);
        
        hSlider.setPrefWidth(zoomFactor*hSlider.getPrefWidth());
        vSlider.setPrefHeight(zoomFactor*vSlider.getPrefHeight());
        hSliderSP.setPrefWidth(zoomFactor*hSliderSP.getPrefWidth());
        vSliderSP.setPrefHeight(zoomFactor*vSliderSP.getPrefHeight());
        
        designSP.setVmax(designImage.getHeight());
        shaftSP.setVmax(shaftImage.getHeight());
        weftColorSP.setVmax(weftColorImage.getHeight());
        pegSP.setVmax(pegImage.getHeight());
        tieUpSP.setVmax(tieUpImage.getHeight());
        tradelesSP.setVmax(tradelesImage.getHeight());
        vSliderSP.setVmax(vSlider.getPrefHeight());
        
        currentZoomFactor=zoomFactor*currentZoomFactor;
        plotDesign();
        populateNplot();
        setScrollBarAtEnd();
    }
    
    public BufferedImage getZoomedImage(BufferedImage image, double zoomFactor){
        BufferedImage zoomedImage=new BufferedImage((int)(zoomFactor*image.getWidth()), (int)(zoomFactor*image.getHeight()), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g=zoomedImage.createGraphics();
        g.drawImage(image, 0, 0, zoomedImage.getWidth(), zoomedImage.getHeight(), null);
        g.dispose();
        return zoomedImage;
    }
    
    public void setZoomableScrollPane(ScrollPane sp, Node node, double zoomFactor)
    {
        Group contentGroup = new Group();
        Group zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(node);
        sp.setContent(contentGroup);
        Scale scaleTransform = new Scale(zoomFactor, zoomFactor, 0, 0);
        zoomGroup.getTransforms().add(scaleTransform);
    }
    
    public void setScrollBarAtEnd(){
        designSP.setVvalue(designSP.getVmax());
        shaftSP.setVvalue(shaftSP.getVmax());
        weftColorSP.setVvalue(weftColorSP.getVmax());
        pegSP.setVvalue(pegSP.getVmax());
        tieUpSP.setVvalue(tieUpSP.getVmax());
        tradelesSP.setVvalue(tradelesSP.getVmax());
        vSliderSP.setVvalue(vSliderSP.getVmax());
    }
}