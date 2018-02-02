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
import com.mla.main.Logging;
import com.mla.main.TechnicalView;
import com.mla.main.WindowView;
import com.mla.main.IDGenerator;
import com.mla.main.MessageView;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.text.FontWeight;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
/**
 *
 * @Designing GUI window for weave editor
 * @author Amit Kumar Singh
 * 
 */
public class WeaveViewAmit {
      
    Weave objWeave;
    WeaveAction objWeaveAction;
    Configuration objConfiguration;
    DictionaryAction objDictionaryAction;
            
    public static final String WIF_EXTENSION = ".wif";
    private static final String DRAFT_EXTENSION = ".wsml";
    
    private Stage weaveStage;
    private Stage weaveChildStage;
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
    
    
    
    
    
    
    public WeaveViewAmit(Configuration objConfigurationCall) {
        this.objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);
        
        objWeave = new Weave();
        objWeave.setObjConfiguration(objConfiguration);
        populateColour();
        
        weaveStage = new Stage();
        root = new BorderPane();
        scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(WeaveViewAmit.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm()); 
        
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
                scene.getStylesheets().add(WeaveViewAmit.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                lblStatus.setText(objDictionaryAction.getWord("ACTIONFILE"));
                menuHighlight("FILE");            
            }
        });
        // Edit menu - Toolbar, Color, Composite View, Support Lines, Ruler, Zoom-in, zoom-out
        editMenuLabel = new Label(objDictionaryAction.getWord("EDIT"));
        editMenu = new Menu();
        editMenu.setGraphic(editMenuLabel);
        editMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONEDIT"));
                menuHighlight("EDIT");
            }
        });
        // Edit menu - Toolbar, Color, Composite View, Support Lines, Ruler, Zoom-in, zoom-out
        transformMenuLabel = new Label(objDictionaryAction.getWord("OPERATION"));
        transformMenu = new Menu();
        transformMenu.setGraphic(transformMenuLabel);
        transformMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONOPERATION"));
                menuHighlight("OPERATION");
            }
        });        
        // View menu - Toolbar, Color, Composite View, Support Lines, Ruler, Zoom-in, zoom-out
        viewMenuLabel = new Label(objDictionaryAction.getWord("VIEW"));
        viewMenu = new Menu();
        viewMenu.setGraphic(viewMenuLabel);
        viewMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONVIEW"));
                menuHighlight("VIEW");
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
                weaveStage.close();    
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
        shaftSP.setPrefSize(objConfiguration.WIDTH*.55, objConfiguration.HEIGHT*0.15);
        shaftSP.setId("subpopup");
        shaftSP.setTooltip(new Tooltip(objDictionaryAction.getWord("DRAFTPANE")));
        shaftGP=new GridPane();
        shaftGP.setAlignment(Pos.BOTTOM_LEFT);
        shaftSP.setContent(shaftGP);

        dentingSP = new ScrollPane();
        dentingSP.setPrefSize(objConfiguration.WIDTH*.55, objConfiguration.HEIGHT*0.06);
        dentingSP.setId("subpopup");
        dentingSP.setHbarPolicy(ScrollBarPolicy.NEVER);
        dentingSP.setVbarPolicy(ScrollBarPolicy.NEVER);
        dentingSP.setTooltip(new Tooltip(objDictionaryAction.getWord("DENTINGPANE")));
        dentingGP=new GridPane();
        dentingGP.setAlignment(Pos.CENTER_LEFT);
        dentingSP.setContent(dentingGP);
    
        automaticSP = new ScrollPane();
        automaticSP.setPrefSize(objConfiguration.WIDTH*.30, objConfiguration.HEIGHT*0.21);
        automaticSP.setId("subpopup");
        automaticGP=new GridPane();
        automaticSP.setContent(automaticGP);
        
        weftColorSP = new ScrollPane();
        weftColorSP.setPrefSize(objConfiguration.WIDTH*.02, objConfiguration.HEIGHT*0.35);
        weftColorSP.setId("subpopup");
        weftColorSP.setHbarPolicy(ScrollBarPolicy.NEVER);
        weftColorSP.setVbarPolicy(ScrollBarPolicy.NEVER);
        weftColorSP.setTooltip(new Tooltip(objDictionaryAction.getWord("WEFTCOLORPANE")));
        weftColorGP=new GridPane();
        weftColorGP.setAlignment(Pos.BOTTOM_CENTER);
        weftColorSP.setContent(weftColorGP);
    
        designSP = new ScrollPane();
        designSP.setPrefSize(objConfiguration.WIDTH*.55, objConfiguration.HEIGHT*0.40);
        designSP.setId("subpopup");
        designSP.setTooltip(new Tooltip(objDictionaryAction.getWord("DESIGNPANE")));
        designGP=new GridPane();  
        designGP.setAlignment(Pos.BOTTOM_LEFT);
        designSP.setContent(designGP);
        
        tradelesSP = new ScrollPane();
        tradelesSP.setPrefSize(objConfiguration.WIDTH*.10, objConfiguration.HEIGHT*0.40);
        tradelesSP.setId("subpopup");
        tradelesSP.setTooltip(new Tooltip(objDictionaryAction.getWord("TRADLEPANE")));
        tradelesGP=new GridPane();
        tradelesGP.setAlignment(Pos.BOTTOM_LEFT);
        tradelesSP.setContent(tradelesGP);
        
        tieUpSP = new ScrollPane();
        tieUpSP.setPrefSize(objConfiguration.WIDTH*.10, objConfiguration.HEIGHT*0.40);
        tieUpSP.setId("subpopup");
        tieUpSP.setTooltip(new Tooltip(objDictionaryAction.getWord("TIEUPPANE")));
        tieUpGP=new GridPane();
        tieUpGP.setAlignment(Pos.BOTTOM_LEFT);
        tieUpSP.setContent(tieUpGP);
        
        pegSP = new ScrollPane();
        pegSP.setPrefSize(objConfiguration.WIDTH*.10, objConfiguration.HEIGHT*0.40);
        pegSP.setId("subpopup");
        tieUpSP.setTooltip(new Tooltip(objDictionaryAction.getWord("PEGPANE")));
        pegGP=new GridPane();
        pegGP.setAlignment(Pos.BOTTOM_LEFT);
        pegSP.setContent(pegGP);
    
        warpColorSP = new ScrollPane();
        warpColorSP.setPrefSize(objConfiguration.WIDTH*.55, objConfiguration.HEIGHT*0.04);
        warpColorSP.setId("subpopup");
        warpColorSP.setHbarPolicy(ScrollBarPolicy.NEVER);
        warpColorSP.setVbarPolicy(ScrollBarPolicy.NEVER);
        warpColorGP=new GridPane();
        warpColorGP.setAlignment(Pos.CENTER_LEFT);
        warpColorSP.setContent(warpColorGP);
        
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

        DoubleProperty hsPosition = new SimpleDoubleProperty();
        hsPosition.bind(shaftSP.hvalueProperty());
        hsPosition.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                 designSP.setHvalue((double) arg2);
                 warpColorSP.setHvalue((double) arg2);
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
            }
        }); 
        
        
        objWeave = new Weave();
        objWeave.setObjConfiguration(objConfiguration);
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
        
        containerGP.add(paletteColorSP,0,0,1,4);
        
        containerGP.add(dummyShaftSP,1,0,1,2);
        containerGP.add(weftColorSP, 1,2,1,1);
        
        containerGP.add(shaftSP,2,0,1,1);
        containerGP.add(dentingSP,2,1,1,1);
        containerGP.add(designSP,2,2,1,1); 
        containerGP.add(warpColorSP, 2,3,1,1);
        
        containerGP.add(automaticSP,3,0,3,2);
        containerGP.add(pegSP,3,2,1,1);
        containerGP.add(tieUpSP,4,2,1,1);
        containerGP.add(tradelesSP,5,2,1,1);
        containerGP.add(dummyPegSP,3,3,1,1);
        containerGP.add(dummyTieUpSP,4,3,1,1);
        containerGP.add(dummyTradleSP,5,3,1,1);
        
        //containerGP.setStyle("-fx-padding: 10; -fx-background-color: #5F5E5E;");Id("popup");
        containerGP.setAlignment(Pos.CENTER);
        container.setContent(containerGP);
        root.setCenter(container);
        
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
                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ALERT"));
                BorderPane root = new BorderPane();
                Scene scene = new Scene(root, 300, 100, Color.WHITE);
                scene.getStylesheets().add(WeaveViewAmit.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
        if(objConfiguration.getStrRecentWeave()!=null && objConfiguration.strWindowFlowContext.equalsIgnoreCase("Dashboard")){
            try {
                objWeave = new Weave();
                objWeave.setObjConfiguration(objConfiguration);
                objWeave.setStrWeaveID(objConfiguration.getStrRecentWeave());
                loadWeave();
            } catch (Exception ex) {
                new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        
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
 * @link        WeaveViewAmit
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
    * @link        WeaveViewAmit
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
                new MessageView(objConfiguration);
                if(objConfiguration.getServicePasswordValid()){
                    objConfiguration.setServicePasswordValid(false);
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONNEWFILE"));
                    newAction();
                }
            }
        });
        openFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONOPENFILE"));
                openAction();
            }
        });
        loadFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new MessageView(objConfiguration);
                if(objConfiguration.getServicePasswordValid()){
                    objConfiguration.setServicePasswordValid(false);
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONLOADFILE"));
                    loadAction();
                }
            }
        });
        importFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new MessageView(objConfiguration);
                if(objConfiguration.getServicePasswordValid()){
                    objConfiguration.setServicePasswordValid(false);
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONIMPORTFILE"));
                    importAction();
                }
            }
        });
        saveFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEFILE"));
                saveUpdateAction();//saveAction();
                System.gc();
            }
        });
        saveAsFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEASFILE"));
                objWeave.setStrWeaveAccess(objConfiguration.getObjUser().getUserAccess("WEAVE_LIBRARY"));
                saveUpdateAction();//saveAsAction();
                System.gc();
            }
        });
        exportFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new MessageView(objConfiguration);
                if(objConfiguration.getServicePasswordValid()){
                    objConfiguration.setServicePasswordValid(false);
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONEXPORTFILE"));
                    exportAction();  
                }
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
                new MessageView(objConfiguration);
                if(objConfiguration.getServicePasswordValid()){
                    objConfiguration.setServicePasswordValid(false);
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONPRINTFILE"));
                    printAction();
                }
            }
        });  
        fabricFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new MessageView(objConfiguration);
                if(objConfiguration.getServicePasswordValid()){
                    objConfiguration.setServicePasswordValid(false);
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONUSEINFABRIC"));
                    openFabricEditorAction();
                }
            }
        });
        toolkitVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    populateToolKit();
                } catch (Exception ex) {
                new Logging("SEVERE",WeaveViewAmit.class.getName(),"Undo",ex);
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
 * @link        WeaveViewAmit
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
                new Logging("SEVERE",WeaveViewAmit.class.getName(),"Undo",ex);
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
                new Logging("SEVERE",WeaveViewAmit.class.getName(),"Redo ",ex);
                }
            }
        });
        propertiesEditVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    populateProperties();
                } catch (Exception ex) {
                new Logging("SEVERE",WeaveViewAmit.class.getName(),"Undo",ex);
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
        flow.getChildren().addAll(yarnEditVB,insertWarpEditVB,deleteWarpEditVB,insertWeftEditVB,deleteWeftEditVB,selectEditVB,copyEditVB,pasteEditVB,mirrorVerticalVB,mirrorHorizontalVB,clearEditVB);
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
 * @link        WeaveViewAmit
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
        flow.getChildren().addAll(compositeViewVB,gridViewVB,frontSideViewVB,switchSideViewVB,simulationViewVB,tilledViewVB,zoomInViewVB,normalViewVB,zoomOutViewVB);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
     } 
    
    public void initWeaveValue(){
        try {
            if(!objWeave.getStrWeaveFile().equals("") && objWeave.getStrWeaveFile()!=null){
                objWeaveAction = new WeaveAction();
                objWeaveAction.extractWeaveContent(objWeave);
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
                plotColour();
            } else{
                lblStatus.setText(objDictionaryAction.getWord("INVALIDWEAVE"));
                isWorkingMode = false;
            }
            menuHighlight(null);
            System.gc();
        } catch (SQLException ex) {
            Logger.getLogger(WeaveViewAmit.class.getName()).log(Level.SEVERE, null, ex);
            
            new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            Logger.getLogger(WeaveViewAmit.class.getName()).log(Level.SEVERE, null, ex);
            
            new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
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
            Logger.getLogger(WeaveViewAmit.class.getName()).log(Level.SEVERE, null, ex);
            
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
        }
    }
    
    public void newAction(){
        final Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 300, 200, Color.WHITE);
        scene.getStylesheets().add(WeaveViewAmit.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());

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
                    objWeave.setStrWeaveID(new IDGenerator().getIDGenerator("WEAVE_LIBRARY", objConfiguration.getObjUser().getStrUserID()));
                    objWeave.setStrWeaveType("other");
                    
                    designGP.getChildren().clear();
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

                        populateColour();
                        
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
                        plotColour();
                        dialogStage.close();
                    }else{
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                    }
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        popup.add(btnOK, 1, 4);
        root.setCenter(popup);
        dialogStage.setScene(scene);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("WEAVINGDETAILS"));
        dialogStage.showAndWait();
    }
    
    public void openAction(){
        try {
            objWeave = new Weave();
            objWeave.setObjConfiguration(objConfiguration);
            WeaveImportView objWeaveImportView= new WeaveImportView(objWeave);
            if(objWeave.getStrWeaveID()!=null){
                loadWeave();
            }
        } catch (Exception ex) {
            Logger.getLogger(WeaveViewAmit.class.getName()).log(Level.SEVERE, null, ex);
            
            new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public void loadAction(){
        try {
            if(objConfiguration.getStrRecentWeave()!=null){
                objWeave = new Weave();
                objWeave.setObjConfiguration(objConfiguration);
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
                    objWeave.setStrWeaveID(lstWeave.get(0).toString());
                    loadWeave();
                }
            }
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
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
            new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (IOException ex) {
            new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (SQLException ex) {
            new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } 
    }
    
    public void exportAction(){
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
                new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            } catch (Exception ex) {
                new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            } 
        }else{
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
            new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
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
                lblStatus.setText(objDictionaryAction.getWord("INVALIDWEAVE"));
            }
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
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
            scene.getStylesheets().add(WeaveViewAmit.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());

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
                        BufferedImage bufferedImageesize = new BufferedImage((int)(objWeave.getIntWeft()*boxSize), (int)(objWeave.getIntWarp()*boxSize),BufferedImage.TYPE_INT_RGB);
                        Graphics2D g = bufferedImageesize.createGraphics();
                        g.drawImage(texture, 0, 0, (int)(objWeave.getIntWeft()*boxSize), (int)(objWeave.getIntWarp()*boxSize), null);
                        g.dispose();
                        texture = null;
                        graphics.drawImage(bufferedImageesize, 0, 0, bufferedImageesize.getWidth(), bufferedImageesize.getHeight(), null);
                        return PAGE_EXISTS;                    
                    }
                });
                printjob.print();
            }
        } catch (PrinterException ex) {             
            new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);  
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
                    scene.getStylesheets().add(WeaveViewAmit.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                                new Logging("SEVERE",WeaveViewAmit.class.getName(),"Cloth type change",ex);
                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                            } catch (Exception ex) {
                                new Logging("SEVERE",WeaveViewAmit.class.getName(),"Cloth type change",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"cloth type property change"+ex.toString(),ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
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
        scene.getStylesheets().add(WeaveViewAmit.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        dialogStageParent.setScene(scene);
        dialogStageParent.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("FABRICSETTINGS"));
        dialogStageParent.showAndWait();        
    }
    public void closeAction(){
        weaveStage.close();
    }
   
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
                               /* for(int r=0;r<objWeave.getIntWeaveShaft();r++){
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
                                boolean shaftPresent = false;
                                for(int r=0;r<objWeave.getIntWeaveShaft();r++)
                                    if(objWeave.getShaftMatrix()[r][q]==1)
                                        shaftPresent = true;
                                if(!shaftPresent){
                                    lblbox.setStyle("-fx-background-color: #000000;-fx-border-width: 1;-fx-border-color: black;");
                                    lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, boxSize));
                                    objWeave.getShaftMatrix()[p][q]=1;
                                }
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
                                */
                
                            }
                            if(editMode.equalsIgnoreCase("none")){
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
            new Logging("SEVERE",WeaveViewAmit.class.getName(),"Ploting Shaft",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }                   
    }

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
            new Logging("SEVERE",WeaveViewAmit.class.getName(),"Ploting Tieup",ex);
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
            new Logging("SEVERE",WeaveViewAmit.class.getName(),"Ploting Treadles",ex);
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
            new Logging("SEVERE",WeaveViewAmit.class.getName(),"Ploting Treadles",ex);
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
                                Logger.getLogger(WeaveViewAmit.class.getName()).log(Level.SEVERE, null, ex);
                                new Logging("SEVERE",WeaveViewAmit.class.getName(),"Ploting weave Design",ex);
                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                            }
                        }
                    });
                    designGP.add(lblbox,j,i);
                }    
            }
        } catch (SQLException ex) {
            new Logging("SEVERE",WeaveViewAmit.class.getName(),"Ploting weave Design",ex);
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
                            Logger.getLogger(WeaveViewAmit.class.getName()).log(Level.SEVERE, null, ex);
                            new Logging("SEVERE",WeaveViewAmit.class.getName(),"Ploting weave Design",ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                });
                dentingGP.add(lblbox,j,i);
            }    
        }
    }
   
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
                        lblbox.setStyle("-fx-background-color: #"+objWeave.getObjConfiguration().getColourPalette()[intWarpColor]+";-fx-border-width: 1;  -fx-border-color: black");
                        lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                        objWeave.getWarpYarn()[p]= objWeave.getObjConfiguration().getYarnPalette()[intWarpColor];
                    }
                }
            });
            lblbox.setOnDragDetected(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    /* drag was detected, start a drag-and-drop gesture*/
                    /* allow any transfer mode */
                    Dragboard db = lblbox.startDragAndDrop(TransferMode.ANY);
                    /* Put a string on a dragboard */
                    ClipboardContent content = new ClipboardContent();
                    content.putString(objWeave.getObjConfiguration().getColourPalette()[intWarpColor]);
                    db.setContent(content);
        
                    lblbox.setStyle("-fx-background-color: #"+objWeave.getObjConfiguration().getColourPalette()[intWarpColor]+";-fx-border-width: 1;  -fx-border-color: black");
                    lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                    objWeave.getWarpYarn()[p]= objWeave.getObjConfiguration().getYarnPalette()[intWarpColor];

                    event.consume();
                }
            });
            lblbox.setOnDragOver(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    /* data is dragged over the target */
                    /* accept it only if it is not dragged */ 
                    if (event.getGestureSource() != lblbox && event.getDragboard().hasString()) {
                        lblbox.setStyle("-fx-background-color: #"+objWeave.getObjConfiguration().getColourPalette()[intWarpColor]+";-fx-border-width: 1;  -fx-border-color: black");
                        lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                        objWeave.getWarpYarn()[p]= objWeave.getObjConfiguration().getYarnPalette()[intWarpColor];
                        
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
                    event.consume();
                }
            });
            lblbox.setOnDragEntered(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    /* the drag-and-drop gesture entered the target */
                    /* show to the user that it is an actual gesture target */
                    if (event.getGestureSource() != lblbox && event.getDragboard().hasString()) {
                    
                    }
                    event.consume();
                }
            });
            lblbox.setOnDragExited(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    /* mouse moved away, remove the graphical cues */
                    event.consume();
                }
            });
            lblbox.setOnDragDropped(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    /* data dropped */
                    /* if there is a string data on dragboard, read it and use it */
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    /* let the source know whether the string was successfully
                    * transferred and used */
                    lblbox.setStyle("-fx-background-color: #"+objWeave.getObjConfiguration().getColourPalette()[intWarpColor]+";-fx-border-width: 1;  -fx-border-color: black");
                    lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                    objWeave.getWarpYarn()[p]= objWeave.getObjConfiguration().getYarnPalette()[intWarpColor];

                    event.setDropCompleted(success);
                    event.consume();
                 }
            });
            lblbox.setOnDragDone(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    /* the drag and drop gesture ended */
                    /* if the data was successfully moved, clear it */
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
                        lblbox.setStyle("-fx-background-color: #" + objWeave.getObjConfiguration().getColourPalette()[intWeftColor] + ";-fx-border-width: 1;  -fx-border-color: black");
                        lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                        objWeave.getWeftYarn()[p]= new Yarn(null, "Weft", objConfiguration.getStrWeftName(), "#"+objWeave.getObjConfiguration().getColourPalette()[intWeftColor], objConfiguration.getIntWeftRepeat(), Character.toString((char)(97+intWeftColor-26)), objConfiguration.getIntWeftCount(), objConfiguration.getStrWeftUnit(), objConfiguration.getIntWeftPly(), objConfiguration.getIntWeftFactor(), objConfiguration.getDblWeftDiameter(), objConfiguration.getIntWeftTwist(), objConfiguration.getStrWeftSence(), objConfiguration.getIntWeftHairness(), objConfiguration.getIntWeftDistribution(), objConfiguration.getDblWeftPrice(), objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"),objConfiguration.getObjUser().getStrUserID(),null);
                        objWeave.getWeftYarn()[p]= objWeave.getObjConfiguration().getYarnPalette()[intWeftColor];
                    }
                }
            });
            lblbox.setOnDragDetected(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    /* drag was detected, start a drag-and-drop gesture*/
                    /* allow any transfer mode */
                    Dragboard db = lblbox.startDragAndDrop(TransferMode.ANY);
                    /* Put a string on a dragboard */
                    ClipboardContent content = new ClipboardContent();
                    content.putString(objWeave.getObjConfiguration().getColourPalette()[intWeftColor]);
                    db.setContent(content);
        
                    lblbox.setStyle("-fx-background-color: #"+objWeave.getObjConfiguration().getColourPalette()[intWeftColor]+";-fx-border-width: 1;  -fx-border-color: black");
                    lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                    objWeave.getWeftYarn()[p]= objWeave.getObjConfiguration().getYarnPalette()[intWeftColor];
                    event.consume();
                }
            });
            lblbox.setOnDragOver(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    /* data is dragged over the target */
                    /* accept it only if it is not dragged */ 
                    if (event.getGestureSource() != lblbox && event.getDragboard().hasString()) {
                        lblbox.setStyle("-fx-background-color: #"+objWeave.getObjConfiguration().getColourPalette()[intWeftColor]+";-fx-border-width: 1;  -fx-border-color: black");
                        lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                        objWeave.getWeftYarn()[p]= objWeave.getObjConfiguration().getYarnPalette()[intWeftColor];
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
                    event.consume();
                }
            });
            lblbox.setOnDragEntered(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    /* the drag-and-drop gesture entered the target */
                    /* show to the user that it is an actual gesture target */
                    if (event.getGestureSource() != lblbox && event.getDragboard().hasString()) {
                    
                    }
                    event.consume();
                }
            });
            lblbox.setOnDragExited(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    /* mouse moved away, remove the graphical cues */
                    event.consume();
                }
            });
            lblbox.setOnDragDropped(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    /* data dropped */
                    /* if there is a string data on dragboard, read it and use it */
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    /* let the source know whether the string was successfully
                    * transferred and used */
                    lblbox.setStyle("-fx-background-color: #"+objWeave.getObjConfiguration().getColourPalette()[intWeftColor]+";-fx-border-width: 1;  -fx-border-color: black");
                    lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                    objWeave.getWeftYarn()[p]= objWeave.getObjConfiguration().getYarnPalette()[intWeftColor];
                    event.setDropCompleted(success);
                    event.consume();
                 }
            });
            lblbox.setOnDragDone(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    /* the drag and drop gesture ended */
                    /* if the data was successfully moved, clear it */
                    if (event.getTransferMode() == TransferMode.MOVE) {
                        Dragboard db = event.getDragboard();
                    }
                    event.consume();
                }
            });
            weftColorGP.add(lblbox,0,i);
        }    
    }
   
   private void populateNplot(){
        try{
            objWeaveAction.populateShaft(objWeave);
            objWeaveAction.populateTreadles(objWeave);
            objWeaveAction.populatePegPlan(objWeave);
            objWeaveAction.populateTieUp(objWeave);
            plotShaft();
            plotPeg();
            plotTieUp();
            plotTradeles();
        }catch(Exception ex){
            Logger.getLogger(WeaveViewAmit.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
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
            Logger.getLogger(WeaveViewAmit.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
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
            System.out.println(blocksize+":"+objWeave.getIntWarp()+":"+objWeave.getIntWeft());
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
            g.drawImage(texture, 0, 0, objWeave.getIntWarp()*blocksize, objWeave.getIntWeft()*blocksize, null);
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
            g.drawImage(texture, 0, 0, objWeave.getIntWarp()*blocksize, objWeave.getIntWeaveShaft()*blocksize, null);
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
            g.drawImage(texture, 0, 0, objWeave.getIntWeaveTreadles()*blocksize, objWeave.getIntWeft()*blocksize, null);
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
            g.drawImage(texture, 0, 0, objWeave.getIntWeaveTreadles()*blocksize, objWeave.getIntWeaveShaft()*blocksize, null);
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
            g.drawImage(texture, 0, 0, objWeave.getIntWeaveShaft()*blocksize, objWeave.getIntWeft()*blocksize, null);
            File pegliftFile = new File(path+"_peg.png");
            ImageIO.write(bufferedImageesize, "png", pegliftFile);
            
            File htmlFile = new File(path+".html");
            
            String INITIAL_TEXT = " This is coumputer generated, So no signature required";
            String IMAGE_TEXT = "<img src=\"file:\\\\"+path+"_design.png" + 
             "\" width=\""+objWeave.getIntWeft()*blocksize+"\" height=\""+objWeave.getIntWarp()*blocksize+"\" >"+
                    "<img src=\"file:\\\\"+path+"_drafting.png" + 
             "\" width=\""+objWeave.getIntWeaveShaft()*blocksize+"\" height=\""+objWeave.getIntWarp()*blocksize+"\" >"+
                    "<img src=\"file:\\\\"+path+"_treadles.png" + 
             "\" width=\""+objWeave.getIntWeft()*blocksize+"\" height=\""+objWeave.getIntWeaveTreadles()*blocksize+"\" >"+
                    "<img src=\"file:\\\\"+path+"_tieup.png" + 
             "\" width=\""+objWeave.getIntWeaveShaft()*blocksize+"\" height=\""+objWeave.getIntWeaveTreadles()*blocksize+"\" >"+
                    "<img src=\"file:\\\\"+path+"_peg.png" + 
             "\" width=\""+objWeave.getIntWeft()*blocksize+"\" height=\""+objWeave.getIntWeaveShaft()*blocksize+"\" >";
            
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
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("TREADLE")+"</th><td>"+objWeave.getIntWeaveTreadles()+"</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("PPI")+"</th><td>"+objWeave.getObjConfiguration().getIntPPI()+" / inch</td></tr>");
                bw.newLine();
                bw.write("<tr align=right><th>"+objDictionaryAction.getWord("EPI")+"</th><td>"+objWeave.getObjConfiguration().getIntEPI()+" / inch</td></tr>");
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
            bw.newLine();
            bw.write("<br><b>"+objDictionaryAction.getWord("GRAPHVIEW")+"<b><br>");
            bw.newLine();
            bw.write(IMAGE_TEXT);
            bw.newLine();
            bw.write("<br>"+INITIAL_TEXT+"<br>");
            bw.newLine();
            } catch (IOException ex) {
                new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            } catch (Exception ex) {
                new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Insert Weft",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Delete Weft",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Insert Warp",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Delete Warp",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Select Weave",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Copy Weave",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Paste Weave",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Clear Weave",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Mirror Vertical",ex);
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
                  new Logging("SEVERE",WeaveViewAmit.class.getName(),"Mirror Vertical",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Rotate Clock Wise ",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Anti Rotate Clock Wise ",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Move Right",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Move Left",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Move Up",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Move Down",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Move Right",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Move Left",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Move Up",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Move Down",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Tilt Right ",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Tilt Left ",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Tilt Up ",ex);
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
                   new Logging("SEVERE",WeaveViewAmit.class.getName(),"Tilt Down ",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Inversion ",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Zoom Normal ",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Zoom In ",ex);
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
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"Zoom out ",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        
        Scene scene = new Scene(popup);
        scene.getStylesheets().add(WeaveViewAmit.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        weaveChildStage.setScene(scene);
        weaveChildStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("WINDOWCOLORPALETTE"));
        weaveChildStage.showAndWait();        
    }

   private void plotColour(){
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
            new Logging("SEVERE",WeaveViewAmit.class.getName(),"loadPaletteNames() : ", sqlEx);
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
                        objUtilityAction=new UtilityAction();
                        objUtilityAction.getPalette(objPalette);
                        if(objPalette.getStrThreadPalette()!=null){
                            objWeave.getObjConfiguration().setColourPalette(objPalette.getStrThreadPalette());
                        }
                    }
                    /*String[] tPalette=objColourAction.getPaletteFromName(paletteName);
                    if(tPalette!=null)
                        threadPaletes=tPalette;*/
                    populateYarnPalette();
                    loadColours(); // it sets colorPalates[][] using threadPalettes
                    
                } catch(SQLException sqlEx){
                    new Logging("SEVERE",WeaveViewAmit.class.getName(),"loadColour() : ", sqlEx);
                }
            }
        });
        
        editThreadGP = new GridPane();
        editThreadGP.setAlignment(Pos.CENTER);
        editThreadGP.setHgap(5);
        editThreadGP.setVgap(5);
        editThreadGP.setCursor(Cursor.HAND);
        //editThreadGP.getChildren().clear();
        loadColours(); // it sets colorPalates[][] using threadPalettes
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
                populateColour();
                loadColours();
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
                    type = objWeave.getObjConfiguration().getYarnPalette()[i+26].getStrYarnType();
                    symbol = objWeave.getObjConfiguration().getYarnPalette()[i+26].getStrYarnSymbol();
                    
                    objWeave.getObjConfiguration().getYarnPalette()[i+26]=objWeave.getObjConfiguration().getYarnPalette()[i];
                    
                    objWeave.getObjConfiguration().getYarnPalette()[i+26].setStrYarnType(type);
                    objWeave.getObjConfiguration().getYarnPalette()[i+26].setStrYarnSymbol(symbol);
                }
                populateColour();
                loadColours();
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
                    type = objWeave.getObjConfiguration().getYarnPalette()[i].getStrYarnType();
                    symbol = objWeave.getObjConfiguration().getYarnPalette()[i].getStrYarnSymbol();
                    
                    objWeave.getObjConfiguration().getYarnPalette()[i]=objWeave.getObjConfiguration().getYarnPalette()[i+26];
                    
                    objWeave.getObjConfiguration().getYarnPalette()[i].setStrYarnType(type);
                    objWeave.getObjConfiguration().getYarnPalette()[i].setStrYarnSymbol(symbol);
                }
                populateColour();
                loadColours();
            }
        });
        paletteColorGP.add(btnWeftWarp, 0, 5, 1, 1);
        
    }
   
    private void populateYarnPalette(){
        for(int i=0; i<52; i++){
            objWeave.getObjConfiguration().getYarnPalette()[i].setStrYarnColor("#"+objWeave.getObjConfiguration().getColourPalette()[i]);
        }
    }
    private void populateColour(){
        for(int i=0; i<52; i++){
            objWeave.getObjConfiguration().getColourPalette()[i]=objWeave.getObjConfiguration().getYarnPalette()[i].getStrYarnColor().substring(1, 7);
        }
    }

   private void loadColours(){
        //objWeave.getObjConfiguration().getColourPalette() = objWeave.getObjConfiguration().getColourPalette();
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
        loadColours();
    }
    
   public void editYarnPalette(){
        YarnPaletteView objYarnPaletteView=new YarnPaletteView(objWeave.getObjConfiguration());
        
        populateColour();
        loadColours();
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
        scene.getStylesheets().add(WeaveViewAmit.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        
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
        fabricGP.add(txtWeftShrinkage, 1, 5);
        //
        weaveTab.setContent(popup);
        fabricTab.setContent(fabricGP);
        
        tabPane.getTabs().addAll(weaveTab, fabricTab);
        
        root.add(tabPane, 0, 0, 2, 1);
        
        Button btnOK = new Button(objDictionaryAction.getWord("SUBMIT"));
        btnOK.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSUBMIT")));
        btnOK.setGraphic(new ImageView(objConfiguration.getStrColour()+"/skip.png"));
        btnOK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {  
                
            }
        });
        root.add(btnOK, 0, 1, 1, 1);
        Button btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
        btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
        btnCancel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/skip.png"));
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {  
                dialogStage.close();
            }
        });
        root.add(btnCancel, 1, 1, 1, 1);
        
        dialogStage.setScene(scene);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("WEAVINGDETAILS"));
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
                   new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
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
                   new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
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
             if(isDragBox==0){
                 isDragBox=1;
                 scene.setCursor(Cursor.HAND);
             }else{
                 isDragBox=0;
                 scene.setCursor(Cursor.DEFAULT);
             }
             designGP.getChildren().clear();
             populateDplot();
         } catch (Exception ex) {
           new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
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
            new Logging("SEVERE",WeaveViewAmit.class.getName(),"Move Right",ex);
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
        }
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"Move Right",ex);
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
            populateNplot();
            populateDplot();
       }
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"Mirror Vertical",ex);
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
            populateNplot();
            populateDplot();
       }
    } catch (Exception ex) {
      new Logging("SEVERE",WeaveViewAmit.class.getName(),"Mirror Vertical",ex);
      lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void insertWarpAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONINSERTWARP"));
        objWeaveAction.insertWarp(objWeave, current_col,'W');
        populateNplot();
        populateDplot();
        if(objWeave.getIntWarp()>2){
            deleteWarpEditVB.setDisable(false);
        }
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"insert warp Right",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void deleteWarpAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONDELETEWARP"));
        if(objWeave.getIntWarp()!=current_col+1){
            objWeaveAction.deleteWarp(objWeave, current_col,'W');
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
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"delete warp",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void insertWeftAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONINSERTWEFT"));
        objWeaveAction.insertWeft(objWeave, current_row,'W');
        populateNplot();
        populateDplot();
        if(objWeave.getIntWeft()>2){
            deleteWeftEditVB.setDisable(false);
        }
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"insert weft",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void deleteWeftAction(){        
    try {
        if(objWeave.getIntWeft()!=current_row+1){
            lblStatus.setText(objDictionaryAction.getWord("ACTIONDELETEWEFT"));
            objWeaveAction.deleteWeft(objWeave, current_row,'W');
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
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"delete weft",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void clearAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONCLEARWEAVE"));
        objWeaveAction.clear(objWeave);
        populateNplot();
        populateDplot();                        
     } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"Clear ",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void moveRightAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVERIGHT"));
        objWeaveAction.moveRight(objWeave);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"Move Right",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void moveLeftAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVELEFT"));
        objWeaveAction.moveLeft(objWeave);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"Move Left",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void moveUpAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVEUP"));
        objWeaveAction.moveUp(objWeave);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"Move Up",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void moveDownAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVEDOWN"));
        objWeaveAction.moveDown(objWeave);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"Move Down",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void moveRight8Action(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVERIGHT8"));
        objWeaveAction.moveRight8(objWeave);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"Move Right",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void moveLeft8Action(){        
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVELEFT8"));
        objWeaveAction.moveLeft8(objWeave);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"Move Left",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void moveUp8Action(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVEUP8"));
            objWeaveAction.moveUp8(objWeave);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"Move Up",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void moveDown8Action(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVEDOWN8"));
        objWeaveAction.moveDown8(objWeave);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"Move Down",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void tiltRightAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONTILTRIGHT"));
        objWeaveAction.tiltRight(objWeave);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"Tilt Right ",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void tiltLeftAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONTILTLEFT"));
        objWeaveAction.tiltLeft(objWeave);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"Tilt Left ",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void tiltUpAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONTILTUP"));
        objWeaveAction.tiltUp(objWeave);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"Tilt Up ",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void tiltDownAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONTILTDOWN"));
        objWeaveAction.tiltDown(objWeave);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
       new Logging("SEVERE",WeaveViewAmit.class.getName(),"Tilt Down ",ex);
       lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void rotateClockwiseAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONCLOCKROTATION"));
        objWeaveAction.rotation(objWeave);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"Rotate Clock Wise ",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void rotateAntiClockwiseAction(){
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONANTICLOCKROTATION"));
        objWeaveAction.rotationAnti(objWeave);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"Anti Rotate Clock Wise ",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void inversionAction(){        
    try {
        lblStatus.setText(objDictionaryAction.getWord("ACTIONINVERSION"));
        objWeaveAction.inversion(objWeave);
        populateNplot();
        populateDplot();
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"Inversion ",ex);
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

        intLength = (int)(((objConfiguration.getIntDPI()*objWeave.getIntWarp())/objWeave.getIntEPI()));
        intHeight = (int)(((objConfiguration.getIntDPI()*objWeave.getIntWeft())/objWeave.getIntPPI()));
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

        popupScene.getStylesheets().add(WeaveViewAmit.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        dialogStage.setScene(popupScene);
        dialogStage.setX(-5);
        dialogStage.setY(0);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ACTIONTEXTUREVIEW"));
        dialogStage.showAndWait();

        bufferedImage = null;
        System.gc();
    } catch (SQLException ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"plotFrontSideView() : Error while viewing visulization view",ex);
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

        popupScene.getStylesheets().add(WeaveViewAmit.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        dialogStage.setScene(popupScene);
        dialogStage.setX(-5);
        dialogStage.setY(0);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ACTIONTEXTUREVIEW"));
        dialogStage.showAndWait();

        bufferedImage = null;
        System.gc();
    } catch (SQLException ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"plotFrontSideView() : Error while viewing visulization view",ex);
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

        popupScene.getStylesheets().add(WeaveViewAmit.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        dialogStage.setScene(popupScene);
        dialogStage.setX(-5);
        dialogStage.setY(0);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ACTIONTEXTUREVIEW"));
        dialogStage.showAndWait();

        bufferedImage = null;
        System.gc();
    } catch (SQLException ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"plotFrontSideView() : Error while viewing visulization view",ex);
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
        weaveIV.setImage(SwingFXUtils.toFXImage(bufferedImage, null));        
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

        popupScene.getStylesheets().add(WeaveViewAmit.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        dialogStage.setScene(popupScene);
        dialogStage.setX(-5);
        dialogStage.setY(0);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ACTIONTEXTUREVIEW"));
        dialogStage.showAndWait();

        bufferedImage = null;
        System.gc();
    } catch (SQLException ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"plotFrontSideView() : Error while viewing visulization view",ex);
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

        popupScene.getStylesheets().add(WeaveViewAmit.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        dialogStage.setScene(popupScene);
        dialogStage.setX(-5);
        dialogStage.setY(0);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ACTIONTEXTUREVIEW"));
        dialogStage.showAndWait();

        bufferedImage = null;
        System.gc();
    } catch (SQLException ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"plotFrontSideView() : Error while viewing visulization view",ex);
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
        scene.getStylesheets().add(WeaveViewAmit.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        dialogStage.setScene(scene);
        dialogStage.setX(-5);
        dialogStage.setY(0);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ACTIONTILLEDVIEW"));
        dialogStage.showAndWait();    
        
        tilledMatrix=null;
        System.gc();
}


private void ZoomNormalAction(){
    try {
        boxSize=15;
        plotDesign();
        plotShaft();
        plotPeg();
        plotTieUp();
        plotTradeles();
        plotWarpColor();
        plotWeftColor();
        plotDenting();
        lblStatus.setText(objDictionaryAction.getWord("ACTIONZOOMNORMALVIEW"));
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),"Normal View",ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}

private void zoomInAction(){
    try {
        if(boxSize<=60){
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
        }
    } catch (Exception ex) {
        new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
    }
}
private void zoomOutAction(){
    try {
        if(boxSize>=4){
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
        }
    } catch (Exception ex) {
       new Logging("SEVERE",WeaveViewAmit.class.getName(),ex.toString(),ex);
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
}