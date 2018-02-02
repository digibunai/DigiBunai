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

import com.mla.colour.ColourPaletteCustom;
import com.mla.colour.ColourPalettePopup;
import com.mla.colour.ColourSelector;
import com.mla.dictionary.DictionaryAction;
import com.mla.main.AboutView;
import com.mla.main.Configuration;
import com.mla.main.ContactView;
import com.mla.main.HelpView;
import com.mla.main.Logging;
import com.mla.main.MessageView;
import com.mla.main.TechnicalView;
import com.mla.main.WindowView;
import com.mla.user.UserAction;
import com.mla.utility.Palette;
import com.mla.utility.UtilityAction;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
/**
 *
 * @Designing GUI window for fabric preferences
 * @author Amit Kumar Singh
 * 
 */
public class FabricSettingView extends Application {
    public static Stage fabricSettingStage;
    BorderPane root;
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;
    
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    
    private Menu homeMenu;
    private Menu helpMenu;
    
    private String strDefaultFabric;
    private String strClothType;
    private String strFabricType;
    private double dblFabricLength;
    private double dblFabricWidth;
    private double dblArtworkLength;
    private double dblArtworkWidth;
    private int intPPI;
    private int intEPI;
    private int intHPI;
    private int intTPD;
    private int intHooks;
    private int intEnds;
    private int intPixs;
    private int intReedCount;
    private int intDents;
    private String strWeftColor;
    private int intWeftCount;
    private String strWeftUnit;
    private double dblWeftDiameter;
    private String strWarpColor;
    private int intWarpCount;
    private String strWarpUnit;
    private double dblWarpDiameter;
        
    public FabricSettingView(final Stage primaryStage) {}
    
    public FabricSettingView(Configuration objConfigurationCall) {
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);

        fabricSettingStage = new Stage(); 
        root = new BorderPane();
        Scene scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(FabricSettingView.class.getResource(objConfiguration.getStrTemplate()+"/setting.css").toExternalForm());
        
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
        
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(fabricSettingStage.widthProperty());
        
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
        Label helpMenuLabel = new Label(objDictionaryAction.getWord("SUPPORT"));
        helpMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSUPPORT")));
        Menu helpMenu = new Menu();
        helpMenu.setGraphic(helpMenuLabel);
        MenuItem helpMenuItem = new MenuItem(objDictionaryAction.getWord("HELP"));
        helpMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        helpMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {       
                HelpView objHelpView = new HelpView(objConfiguration);
            }
        });        
        MenuItem technicalMenuItem = new MenuItem(objDictionaryAction.getWord("TECHNICAL"));
        technicalMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/technical_info.png"));
        technicalMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {       
                TechnicalView objTechnicalView = new TechnicalView(objConfiguration);
            }
        });
        MenuItem aboutMenuItem = new MenuItem(objDictionaryAction.getWord("ABOUTUS"));
        aboutMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/about_software.png"));
        aboutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                AboutView objAboutView = new AboutView(objConfiguration);
            }
        });
        MenuItem contactMenuItem = new MenuItem(objDictionaryAction.getWord("CONTACTUS"));
        contactMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/contact_us.png"));
        contactMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ContactView objContactView = new ContactView(objConfiguration);
            }
        });
        MenuItem exitMenuItem = new MenuItem(objDictionaryAction.getWord("EXIT"));
        exitMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/quit.png"));
        exitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.gc();
                fabricSettingStage.close();    
            }
        });    
        helpMenu.getItems().addAll(helpMenuItem, technicalMenuItem, aboutMenuItem, contactMenuItem, new SeparatorMenuItem(), exitMenuItem);        
        menuBar.getMenus().addAll(homeMenu, helpMenu);
        root.setTop(menuBar);
        
        ScrollPane container = new ScrollPane();
        
        TabPane tabPane = new TabPane();

        Tab fabricArtworkTab = new Tab();
        Tab miscellaneousTab = new Tab();
        Tab weftYarnTab = new Tab();
        Tab warpYarnTab = new Tab();
        
        fabricArtworkTab.setClosable(false);
        miscellaneousTab.setClosable(false);
        weftYarnTab.setClosable(false);
        warpYarnTab.setClosable(false);
        
        fabricArtworkTab.setText(objDictionaryAction.getWord("FABRIC")+"/"+objDictionaryAction.getWord("ARTWORK"));
        miscellaneousTab.setText(objDictionaryAction.getWord("MISCELLANEOUS"));
        weftYarnTab.setText(objDictionaryAction.getWord("WEFT")+" "+objDictionaryAction.getWord("THREAD"));
        warpYarnTab.setText(objDictionaryAction.getWord("WARP")+" "+objDictionaryAction.getWord("THREAD"));
        
        fabricArtworkTab.setTooltip(new Tooltip(objDictionaryAction.getWord("FABRIC")+"/"+objDictionaryAction.getWord("ARTWORK")));
        miscellaneousTab.setTooltip(new Tooltip(objDictionaryAction.getWord("MISCELLANEOUS")));
        weftYarnTab.setTooltip(new Tooltip(objDictionaryAction.getWord("WEFT")+" "+objDictionaryAction.getWord("THREAD")));
        warpYarnTab.setTooltip(new Tooltip(objDictionaryAction.getWord("WARP")+" "+objDictionaryAction.getWord("THREAD")));
        
        GridPane fabricArtworkGP = new GridPane();
        GridPane miscellaneousGP = new GridPane();
        GridPane weftYarnGP = new GridPane();
        GridPane warpYarnGP = new GridPane();
        
        fabricArtworkGP.setId("container");
        miscellaneousGP.setId("container");
        weftYarnGP.setId("container");
        warpYarnGP.setId("container");
        
        //fabricArtworkGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //miscellaneousGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //weftYarnGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //warpYarnGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        
        //fabricArtworkGP.setAlignment(Pos.TOP_CENTER);
        //miscellaneousGP.setAlignment(Pos.TOP_CENTER);
        //weftYarnGP.setAlignment(Pos.TOP_CENTER);
        //warpYarnGP.setAlignment(Pos.TOP_CENTER);
        
        fabricArtworkTab.setContent(fabricArtworkGP);
        miscellaneousTab.setContent(miscellaneousGP);
        weftYarnTab.setContent(weftYarnGP);
        warpYarnTab.setContent(warpYarnGP);
        
        tabPane.getTabs().add(fabricArtworkTab);
        tabPane.getTabs().add(miscellaneousTab);
        tabPane.getTabs().add(weftYarnTab);
        tabPane.getTabs().add(warpYarnTab);
        
        container.setContent(tabPane);
        
        //////---------Fabric/Artwork Settings-------//////
        Label clothType= new Label(objDictionaryAction.getWord("CLOTHTYPE")+" :");
        //clothType.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        clothType.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLOTHTYPE")));
        fabricArtworkGP.add(clothType, 0, 0);
        final ComboBox clothTypeCB = new ComboBox();
        
        clothTypeCB.getItems().addAll("Body","Palu","Border","Cross Border","Blouse","Skart","Konia");//("Saree Body","Saree Palu","Saree Border","Saree Cross Border","Saree Blouse","Saree Skart","Saree Konia","Dress Material Body","Dress Material Border","Stole Body","Stole Border","Stole Palu","Dupatta Body","Dupatta Border","Dupatta Palu","Shawl","Gaisar Brocade");
        strClothType = objConfiguration.getStrClothType();
        clothTypeCB.setValue(strClothType);
        clothTypeCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLOTHTYPE")));
        fabricArtworkGP.add(clothTypeCB, 1, 0);    
        
        Label fabricDefault= new Label(objDictionaryAction.getWord("DEFAULTFABRIC")+" :");
        //fabricDefault.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        fabricDefault.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDEFAULTFABRIC")));
        fabricArtworkGP.add(fabricDefault, 0, 1);
        final Label fabricDefaultTF = new Label();
        strDefaultFabric = objConfiguration.getStrDefaultFabric();
        fabricDefaultTF.setText(strDefaultFabric);
        fabricDefaultTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDEFAULTFABRIC")));
        fabricArtworkGP.add(fabricDefaultTF, 1, 1);    

        final Hyperlink fabricDefaultHL = new Hyperlink();
        fabricDefaultHL.setText(objDictionaryAction.getWord("TOOLTIPCHANGEDEFAULT"));
        fabricDefaultHL.setGraphic(new ImageView(objConfiguration.getStrColour()+"/browse.png"));
        fabricDefaultHL.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDEFAULTFABRIC")));
        fabricArtworkGP.add(fabricDefaultHL, 1, 2);   
        
        Label fabricType= new Label(objDictionaryAction.getWord("FABRICTYPE")+" :");
        //fabricType.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        fabricType.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFABRICTYPE")));
        fabricArtworkGP.add(fabricType, 0, 3);
        final ComboBox fabricTypeCB = new ComboBox();
        //fabricTypeCB.getItems().addAll("Plain","Jangla","Tanchoi","Vaskat","Cutwork","Tissue","Butidar");
        fabricTypeCB.getItems().addAll("Plain","Kadhua","Fekuwa-Float","Fekuwa-Cutwork","Binding-Irregular","Binding-Regular","Tanchoi","Tissue");        
        strFabricType = objConfiguration.getStrFabricType();
        fabricTypeCB.setValue(strFabricType);
        fabricTypeCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFABRICTYPE")));
        fabricArtworkGP.add(fabricTypeCB, 1, 3);

        Label fabricLength= new Label(objDictionaryAction.getWord("FABRICLENGTH")+" (inch):");
        //fabricLength.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        fabricLength.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFABRICLENGTH")));
        fabricArtworkGP.add(fabricLength, 0, 4);
        dblFabricLength = objConfiguration.getDblFabricLength();
        final TextField fabricLengthTF = new TextField(Double.toString(dblFabricLength)){
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
        fabricLengthTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFABRICLENGTH")));
        fabricArtworkGP.add(fabricLengthTF, 1, 4);

        Label fabricWidth= new Label(objDictionaryAction.getWord("FABRICWIDTH")+" (inch):");
        //fabricWidth.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        fabricWidth.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFABRICWIDTH")));
        fabricArtworkGP.add(fabricWidth, 0, 5);
        dblFabricWidth = objConfiguration.getDblFabricWidth();
        final TextField fabricWidthTF = new TextField(Double.toString(dblFabricWidth)){
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
        fabricWidthTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFABRICWIDTH")));
        fabricArtworkGP.add(fabricWidthTF, 1, 5);

        Label artworkLength= new Label(objDictionaryAction.getWord("ARTWORKLENGTH")+" (inch):");
        //artworkLength.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        artworkLength.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPARTWORKLENGTH")));
        fabricArtworkGP.add(artworkLength, 0, 6);
        dblArtworkLength = objConfiguration.getDblArtworkLength();
        final Label artworkLengthLbl = new Label(Double.toString(dblArtworkLength));
        artworkLengthLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPARTWORKLENGTH")));
        fabricArtworkGP.add(artworkLengthLbl, 1, 6);

        Label artworkWidth= new Label(objDictionaryAction.getWord("ARTWORKWIDTH")+" (inch):");
        //artworkWidth.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        artworkWidth.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPARTWORKWIDTH")));
        fabricArtworkGP.add(artworkWidth, 0, 7);
        dblArtworkWidth = objConfiguration.getDblArtworkWidth();
        final Label artworkWidthLbl = new Label(Double.toString(dblArtworkWidth));
        artworkWidthLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPARTWORKWIDTH")));
        fabricArtworkGP.add(artworkWidthLbl, 1, 7);
        
        Label graphSize= new Label(objDictionaryAction.getWord("GRAPHSIZE")+" :");
        //graphSize.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        graphSize.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPGRAPHSIZE")));
        fabricArtworkGP.add(graphSize, 0, 8);
        /*
        final ComboBox graphSizeCB = new ComboBox();
        graphSizeCB.getItems().addAll("8x10","8x12","10x12","12x12","12x16","12x20","12x36","12x48","16x16");  
        graphSizeCB.setValue(objConfiguration.getStrGraphSize());
        graphSizeCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPGRAPHSIZE")));
        */
        objConfiguration.setStrGraphSize(12+"x"+(int)((12*objConfiguration.getIntPPI())/objConfiguration.getIntHPI()));
        final Label graphSizeCB = new Label();
        graphSizeCB.setText(objConfiguration.getStrGraphSize());
        graphSizeCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPGRAPHSIZE")));
        fabricArtworkGP.add(graphSizeCB, 1, 8);
    
        Separator sepVertFabric = new Separator();
        sepVertFabric.setOrientation(Orientation.VERTICAL);
        sepVertFabric.setValignment(VPos.CENTER);
        sepVertFabric.setPrefHeight(80);
        GridPane.setConstraints(sepVertFabric, 2, 0);
        GridPane.setRowSpan(sepVertFabric, 9);
        fabricArtworkGP.getChildren().add(sepVertFabric);
        
        Label hooks= new Label(objDictionaryAction.getWord("HOOKS")+" :");
        //hooks.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        hooks.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPHOOKS")));
        fabricArtworkGP.add(hooks, 3, 0);
        intHooks = objConfiguration.getIntHooks();
        final TextField hooksTF = new TextField(Integer.toString(intHooks)){
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
        hooksTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPHOOKS")));
        fabricArtworkGP.add(hooksTF, 4, 0);

        Label ends= new Label(objDictionaryAction.getWord("ENDS")+" :");
        //hooks.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        ends.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPENDS")));
        fabricArtworkGP.add(ends, 3, 1);
        
        try{
            intEnds = objConfiguration.findIntEnds();
        } catch(Exception ex){
            intEnds = objConfiguration.getIntEnds();
        }
        final TextField endsTF = new TextField(Integer.toString(intEnds));    
        endsTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPENDS")));
        endsTF.setEditable(false);
        fabricArtworkGP.add(endsTF, 4, 1);

        Label pixs= new Label(objDictionaryAction.getWord("PIXS")+" :");
        //pixs.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        pixs.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPIXS")));
        fabricArtworkGP.add(pixs, 3, 2);
        intPixs = objConfiguration.getIntPixs();
        final TextField pixsTF = new TextField(Integer.toString(intPixs)){
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
        pixsTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPIXS")));
        fabricArtworkGP.add(pixsTF, 4, 2);

        Label reedCount= new Label(objDictionaryAction.getWord("REEDCOUNT")+" :");
        //reedCount.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        reedCount.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPREEDCOUNT")));
        fabricArtworkGP.add(reedCount, 3, 3);
        intReedCount = objConfiguration.getIntReedCount();
        final TextField reedCountTF = new TextField(Integer.toString(intReedCount)){
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
        reedCountTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPREEDCOUNT")));
        fabricArtworkGP.add(reedCountTF, 4, 3);

        Label dents= new Label(objDictionaryAction.getWord("DENTS")+" :");
        //dents.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        dents.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDENTS")));
        fabricArtworkGP.add(dents, 3, 4);
        intDents = objConfiguration.getIntDents();
        final TextField dentsTF = new TextField(Integer.toString(intDents));    
        dentsTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDENTS")));
        dentsTF.setEditable(false);
        fabricArtworkGP.add(dentsTF, 4, 4);

        Label tpd= new Label(objDictionaryAction.getWord("TPD")+" :");
        //tpd.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        tpd.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTPD")));
        fabricArtworkGP.add(tpd, 3, 5);
        intTPD = objConfiguration.getIntTPD();
        final TextField tpdTF = new TextField(Integer.toString(intTPD)){
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
        tpdTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTPD")));
        fabricArtworkGP.add(tpdTF, 4, 5);

        Label ppi= new Label(objDictionaryAction.getWord("PPI")+" :");
        //ppi.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        ppi.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPPI")));
        fabricArtworkGP.add(ppi, 3, 6);
        intPPI = objConfiguration.getIntPPI();
        final TextField ppiTF = new TextField(Integer.toString(intPPI)){
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
        ppiTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPPI")));
        fabricArtworkGP.add(ppiTF, 4, 6);

        Label hpi= new Label(objDictionaryAction.getWord("HPI")+" :");
        //hpi.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        hpi.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPHPI")));
        fabricArtworkGP.add(hpi, 3, 7);
        intHPI = objConfiguration.getIntHPI();
        final TextField hpiTF = new TextField(Integer.toString(intHPI));
        hpiTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPHPI")));
        hpiTF.setEditable(false);
        fabricArtworkGP.add(hpiTF, 4, 7);

        Label epi= new Label(objDictionaryAction.getWord("EPI")+" :");
        //epi.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        epi.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEPI")));
        fabricArtworkGP.add(epi, 3, 8);
        intEPI = objConfiguration.getIntEPI();
        final TextField epiTF = new TextField(Integer.toString(intEPI));    
        epiTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEPI")));
        epiTF.setEditable(false);
        fabricArtworkGP.add(epiTF, 4, 8);

        //////---------Miscellaneous Settings-------//////
        Label multiplePrint= new Label(objDictionaryAction.getWord("MPRINT")+" :");
        //punchCard.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        multiplePrint.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMPRINT")));
        miscellaneousGP.add(multiplePrint, 0, 0);
        final CheckBox multiplePrintCB = new CheckBox(objDictionaryAction.getWord("SHOWMPRINT"));
        multiplePrintCB.setSelected(objConfiguration.getBlnMPrint());
        multiplePrintCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSHOWMPRINT")));
        miscellaneousGP.add(multiplePrintCB, 1, 0);
        
        Label punchCard= new Label(objDictionaryAction.getWord("PUNCHCARD")+" :");
        //punchCard.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        punchCard.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPUNCHCARD")));
        miscellaneousGP.add(punchCard, 0, 1);
        final CheckBox punchCardCB = new CheckBox(objDictionaryAction.getWord("SHOWPUNCHCARD"));
        punchCardCB.setSelected(objConfiguration.getBlnPunchCard());
        punchCardCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSHOWPUNCHCARD")));
        miscellaneousGP.add(punchCardCB, 1, 1);
        
        Label multipleRepeat= new Label(objDictionaryAction.getWord("MREPEAT")+" :");
        //multipleRepeat.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        multipleRepeat.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPMREPEAT")));
        miscellaneousGP.add(multipleRepeat, 0, 2);
        final CheckBox multipleRepeatCB = new CheckBox(objDictionaryAction.getWord("SHOWMREPEAT"));
        multipleRepeatCB.setSelected(objConfiguration.getBlnMRepeat());
        multipleRepeatCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSHOWMREPEAT")));
        miscellaneousGP.add(multipleRepeatCB, 1, 2);
        
        Label errorGraph= new Label(objDictionaryAction.getWord("EGRAPH")+" :");
        //errorGraph.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        errorGraph.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEGRAPH")));
        miscellaneousGP.add(errorGraph, 0, 3);
        final CheckBox errorGraphCB = new CheckBox(objDictionaryAction.getWord("SHOWERRORGRAPH"));
        errorGraphCB.setSelected(objConfiguration.getBlnErrorGraph());
        errorGraphCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEGRAPH")));
        miscellaneousGP.add(errorGraphCB, 1, 3);
        
        Label correctGraph= new Label(objDictionaryAction.getWord("CGRAPH")+" :");
        //errorGraph.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        correctGraph.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCGRAPH")));
        miscellaneousGP.add(correctGraph, 0, 4);
        final CheckBox correctGraphCB = new CheckBox(objDictionaryAction.getWord("SHOWCORRECTGRAPH"));
        correctGraphCB.setSelected(objConfiguration.getBlnCorrectGraph());
        correctGraphCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCGRAPH")));
        miscellaneousGP.add(correctGraphCB, 1, 4);
                
        Label doubleBind= new Label(objDictionaryAction.getWord("FLOATBINDSIZE")+" :");
        //doubleBind.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        doubleBind.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFLOATBINDSIZE")));
        miscellaneousGP.add(doubleBind, 0, 5);
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
        doubleBindTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFLOATBINDSIZE")));
        miscellaneousGP.add(doubleBindTF, 1, 5);
        
        Label protection= new Label(objDictionaryAction.getWord("PROTECTION")+" :");
        //protection.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        protection.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPROTECTION")));
        miscellaneousGP.add(protection, 0, 6);
        final TextField protectionTF = new TextField(Integer.toString(objConfiguration.getIntProtection())){
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
        protectionTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPROTECTION")));
        miscellaneousGP.add(protectionTF, 1, 6);

        Label binding= new Label(objDictionaryAction.getWord("BINDING")+" :");
        //binding.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        binding.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBINDING")));
        miscellaneousGP.add(binding, 0, 7);
        final TextField bindingTF = new TextField(Integer.toString(objConfiguration.getIntBinding())){
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
        miscellaneousGP.add(bindingTF, 1, 7);
        
        Label vertical= new Label(objDictionaryAction.getWord("VREPEAT")+" :");
        //vertical.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        vertical.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPVREPEAT")));
        miscellaneousGP.add(vertical, 0, 8);
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
        miscellaneousGP.add(verticalTF, 1, 8);
        
        Label horizental= new Label(objDictionaryAction.getWord("HREPEAT")+" :");
        //horizental.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        horizental.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPHREPEAT")));
        miscellaneousGP.add(horizental, 0, 9);
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
        miscellaneousGP.add(horizentalTF, 1, 9);
        
        Separator sepVertMisc = new Separator();
        sepVertMisc.setOrientation(Orientation.VERTICAL);
        sepVertMisc.setValignment(VPos.CENTER);
        sepVertMisc.setPrefHeight(80);
        GridPane.setConstraints(sepVertMisc, 2, 0);
        GridPane.setRowSpan(sepVertMisc, 10);
        miscellaneousGP.getChildren().add(sepVertMisc);
        
        Label colour= new Label(objDictionaryAction.getWord("COLOURTYPE")+" :");
        //colour.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        colour.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOLOURTYPE")));
        miscellaneousGP.add(colour, 3, 0);
        final ComboBox colourCB = new ComboBox();
        //colourCB.getItems().addAll("Web Color","Pantone Color");  
        //colourCB.setValue(objConfiguration.getStrColourType());
        colourCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOLOURTYPE")));
        miscellaneousGP.add(colourCB, 4, 0);
		loadColourPaletteNames(colourCB);
        
        Label designingCost = new Label(objDictionaryAction.getWord("DESIGNINGCOST")+" (rupees)");
        miscellaneousGP.add(designingCost, 3, 1);
        final TextField designingCostTF = new TextField(Double.toString(objConfiguration.getDblDesigningCost()));
        designingCostTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDESIGNINGCOST")));
        miscellaneousGP.add(designingCostTF, 4, 1);
        
        Label punchingCost = new Label(objDictionaryAction.getWord("PUNCHINGCOST")+" (rupees)");
        miscellaneousGP.add(punchingCost, 3, 2);
        final TextField punchingCostTF = new TextField(Double.toString(objConfiguration.getDblPunchingCost()));
        punchingCostTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPUNCHINGCOST")));
        miscellaneousGP.add(punchingCostTF, 4, 2);
        
        Label propertyCost = new Label(objDictionaryAction.getWord("PROPERTYCOST")+"(rupees)");
        miscellaneousGP.add(propertyCost, 3, 3);
        final TextField propertyCostTF = new TextField(Double.toString(objConfiguration.getDblPropertyCost()));
        propertyCostTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPROPERTYCOST")));
        miscellaneousGP.add(propertyCostTF, 4, 3);
        
        Label wagesCost = new Label(objDictionaryAction.getWord("WAGESCOST")+" (rupees)");
        miscellaneousGP.add(wagesCost, 3, 4);
        final TextField wagesCostTF = new TextField(Double.toString(objConfiguration.getDblWagesCost()));
        wagesCostTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWAGESCOST")));
        miscellaneousGP.add(wagesCostTF, 4, 4);
        
        Label overheadCost = new Label(objDictionaryAction.getWord("OVERHEADCOST")+" (%)");
        miscellaneousGP.add(overheadCost, 3, 5);
        final TextField overheadCostTF = new TextField(Integer.toString(objConfiguration.getIntOverheadCost())){
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
        overheadCostTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOTIPOVERHEADCOST")));
        miscellaneousGP.add(overheadCostTF, 4, 5);
            
        Label profit = new Label(objDictionaryAction.getWord("PROFIT")+" (%)");
        miscellaneousGP.add(profit, 3, 6);
        final TextField profitTF = new TextField(Integer.toString(objConfiguration.getIntProfit())){
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
        profitTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPROFIT")));
        miscellaneousGP.add(profitTF, 4, 6);
        
        Label colorLimit = new Label(objDictionaryAction.getWord("COLORLIMIT"));
        miscellaneousGP.add(colorLimit, 3, 7);
        final TextField colorLimitTF = new TextField(Integer.toString(objConfiguration.getIntColorLimit())){
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
        colorLimitTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOLORLIMIT")));
        miscellaneousGP.add(colorLimitTF, 4, 7);
        
        //action == events
        fabricDefaultHL.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Fabric objFabricNew = new Fabric();
                    objFabricNew.setStrSearchBy(clothTypeCB.getValue().toString());
                    objFabricNew.setObjConfiguration(objConfiguration);
                    FabricImportView objFabricImportView= new FabricImportView(objFabricNew);
                    if(objFabricNew.getStrFabricID()!=null){
                        fabricDefaultTF.setText(objFabricNew.getStrFabricID());
                        /*
                        FabricAction objFabricAction = new FabricAction();
                        objFabricAction.getFabric(objFabricNew);
                        fabricTypeCB.setValue(objFabricNew.getStrFabricType());
                        fabricLengthTF.setText(String.valueOf(objFabricNew.getDblFabricLength()));
                        fabricWidthTF.setText(String.valueOf(objFabricNew.getDblFabricWidth()));
                        ppiTF.setText(String.valueOf(objFabricNew.getIntPPI()));
                        epiTF.setText(String.valueOf(objFabricNew.getIntEPI()));
                        hooksTF.setText(String.valueOf(objFabricNew.getIntHooks()));
                        pixsTF.setText(String.valueOf(objFabricNew.getIntWeft()));
                        reedCountTF.setText(String.valueOf(objFabricNew.getIntReedCount()));
                        tpdTF.setText(String.valueOf(objFabricNew.getIntTPD()));
                        artworkLengthLbl.setText(String.valueOf(objFabricNew.getDblArtworkHeight()));
                        artworkWidthLbl.setText(String.valueOf(objFabricNew.getDblArtworkWidth()));
                        protectionTF.setText(String.valueOf(objFabricNew.getIntProtection()));
                        bindingTF.setText(String.valueOf(objFabricNew.getIntBinding()));
                        objConfiguration.clothRepeat();
                        verticalTF.setText(String.valueOf(objConfiguration.getIntVRepeat()));
                        horizentalTF.setText(String.valueOf(objConfiguration.getIntHRepeat()));                    
                        */
                        objFabricNew = null;
                        System.gc();
                    }
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),ex.toString(),ex);
                }
            }
        });
        clothTypeCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
               try {
                    objConfiguration.setStrClothType(t1);
                    UserAction objUserAction = new UserAction();
                    objUserAction.getConfiguration(objConfiguration);
                    fabricTypeCB.setValue(objConfiguration.getStrFabricType());
                    fabricDefaultTF.setText(objConfiguration.getStrDefaultFabric());
                    fabricLengthTF.setText(String.valueOf(objConfiguration.getDblFabricLength()));
                    fabricWidthTF.setText(String.valueOf(objConfiguration.getDblFabricWidth()));
                    ppiTF.setText(String.valueOf(objConfiguration.getIntPPI()));
                    epiTF.setText(String.valueOf(objConfiguration.getIntEPI()));
                    hooksTF.setText(String.valueOf(objConfiguration.getIntHooks()));
                    endsTF.setText(String.valueOf(objConfiguration.getIntEnds()));
                    pixsTF.setText(String.valueOf(objConfiguration.getIntPixs()));
                    reedCountTF.setText(String.valueOf(objConfiguration.getIntReedCount()));
                    tpdTF.setText(String.valueOf(objConfiguration.getIntTPD()));
                    artworkLengthLbl.setText(String.valueOf(objConfiguration.getDblArtworkLength()));
                    artworkWidthLbl.setText(String.valueOf(objConfiguration.getDblArtworkWidth()));
                    protectionTF.setText(String.valueOf(objConfiguration.getIntProtection()));
                    bindingTF.setText(String.valueOf(objConfiguration.getIntBinding()));
                    graphSizeCB.setText(objConfiguration.getStrGraphSize());
                    objConfiguration.clothRepeat();
                    verticalTF.setText(String.valueOf(objConfiguration.getIntVRepeat()));
                    horizentalTF.setText(String.valueOf(objConfiguration.getIntHRepeat()));                    
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"cloth type property change",ex);
                }
            }
        });
        fabricTypeCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                objConfiguration.setStrFabricType(t1);
            }
        });
        hooksTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(Integer.parseInt(hooksTF.getText())>0){
                        objConfiguration.setIntHooks(Integer.parseInt(t1));
                        endsTF.setText(Integer.toString(objConfiguration.findIntEnds()));
                        artworkWidthLbl.setText(Double.toString(objConfiguration.findDblArtworkWidth()));
                    }
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"Hooks property change",ex);
                }
            }
        });    
        pixsTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(Integer.parseInt(pixsTF.getText())>0){
                        objConfiguration.setIntPixs(Integer.parseInt(t1));
                        artworkLengthLbl.setText(Double.toString(objConfiguration.findDblArtworkLength()));
                    }
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"Picks property change",ex);
                }
            }
        });
        reedCountTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(Integer.parseInt(reedCountTF.getText())>0){
                        objConfiguration.setIntReedCount(Integer.parseInt(t1));
                        dentsTF.setText(Integer.toString(objConfiguration.findIntDents()));
                        hpiTF.setText(Integer.toString(objConfiguration.findIntHPI()));
                        epiTF.setText(Integer.toString(objConfiguration.findIntEPI()));
                    }
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"Reed Count property change",ex);
                }
            }
        });
        tpdTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(Integer.parseInt(dentsTF.getText())>0){
                        objConfiguration.setIntTPD(Integer.parseInt(t1));
                        epiTF.setText(Integer.toString(objConfiguration.findIntEPI()));
                        endsTF.setText(Integer.toString(objConfiguration.findIntEnds()));
                    }
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"Threads per dent property change",ex);
                }
            }
        });
        hpiTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(Integer.parseInt(hpiTF.getText())>0 && Integer.parseInt(ppiTF.getText())>0){
                        objConfiguration.setIntHPI(Integer.parseInt(t1));
                        artworkWidthLbl.setText(Double.toString(objConfiguration.findDblArtworkWidth()));
                    }
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"Hooks property change",ex);
                }
            }
        });
        epiTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(Integer.parseInt(epiTF.getText())>0){
                        objConfiguration.setIntEPI(Integer.parseInt(t1));
                        graphSizeCB.setText(objConfiguration.findStrGraphSize());
                    }
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"EPI property change",ex);
                }
            }
        });
        ppiTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(Integer.parseInt(ppiTF.getText())>0){
                        objConfiguration.setIntPPI(Integer.parseInt(t1));
                        artworkLengthLbl.setText(Double.toString(objConfiguration.findDblArtworkLength()));
                        graphSizeCB.setText(objConfiguration.findStrGraphSize());
                    }
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"PPI property change",ex);
                }
            }
        }); 
        
        Label weftName= new Label(objDictionaryAction.getWord("YARNNAME")+" :");
        //weftName.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        weftName.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNNAME")));
        weftYarnGP.add(weftName, 0, 0);
        final ComboBox weftNameCB = new ComboBox();
        weftNameCB.getItems().addAll("Cotton","Silk","Synthetic","Linen","Rayon","Novelty","Fleece","Wool");  
        weftNameCB.setValue(objConfiguration.getStrWeftName());
        weftNameCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNNAME")));
        weftYarnGP.add(weftNameCB, 1, 0); 
        
        Label weftColor= new Label(objDictionaryAction.getWord("YARNCOLOR")+" :");
        //weftColor.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        weftColor.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOLOR")));
        weftYarnGP.add(weftColor, 0, 1);
        /*final ColorPicker weftColorCP = new ColorPicker();
        strWeftColor = objConfiguration.getStrWeftColor();
        weftColorCP.setOnAction(new EventHandler() {
            @Override
            public void handle(Event t) {
                Color c = weftColorCP.getValue();
                strWeftColor  = toRGBCode((Color)weftColorCP.getValue());
                strWeftColor = strWeftColor.substring(1, strWeftColor.length());
            }
        });
        weftColorCP.setValue(Color.web(strWeftColor)); 
        weftColorCP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOLOR")));
        weftYarnGP.add(weftColorCP, 1, 1);*/
		//<Added Date="25/10/2017">
        final ComboBox weftColorCB=new ComboBox();
        weftColorCB.setStyle("-fx-background-color:#"+objConfiguration.getStrWeftColor()+";");
        strWeftColor=objConfiguration.getStrWeftColor();
        weftColorCB.setOnShown(new EventHandler() {
            @Override
            public void handle(Event t) {
                try {
                    ColourSelector objColourSelector=new ColourSelector(objConfiguration);
                    if(objColourSelector.colorCode!=null&&objColourSelector.colorCode.length()>0){
                        weftColorCB.setStyle("-fx-background-color:#"+objColourSelector.colorCode+";");
                        strWeftColor=objColourSelector.colorCode;
                    }
                    weftColorCB.hide();
                    t.consume();
                } catch (Exception ex) {
                    Logger.getLogger(FabricSettingView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        weftColorCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOLOR")));
        weftYarnGP.add(weftColorCB, 1, 1);
        //</Added>
        
        Label weftPattern= new Label(objDictionaryAction.getWord("YARNPATTERN")+" :");
        //weftPattern.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        weftYarnGP.add(weftPattern, 0, 2);
        final Label weftPatternV= new Label(objConfiguration.getStrWeftPattern());
        //weftPatternV.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        weftYarnGP.add(weftPatternV, 1, 2);

        Label weftPrice= new Label(objDictionaryAction.getWord("YARNPRICE")+" (per gram):");
        //weftPrice.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        weftPrice.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNPRICE")));
        weftYarnGP.add(weftPrice, 0, 3);
        final TextField weftPriceTF = new TextField(Double.toString(objConfiguration.getDblWeftPrice())){
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
        weftPriceTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNPRICE")));
        weftYarnGP.add(weftPriceTF, 1, 3);
        
        Label WeftCrimp = new Label(objDictionaryAction.getWord("YARNCRIMP")+" (%)");
        WeftCrimp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCRIMP")));
        weftYarnGP.add(WeftCrimp, 0, 4);
        final TextField weftCrimpTF = new TextField(Integer.toString(objConfiguration.getIntWeftCrimp())){
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
        weftCrimpTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCRIMP")));
        weftYarnGP.add(weftCrimpTF, 1, 4);
            
        Label weftWaste = new Label(objDictionaryAction.getWord("YARNWASTE")+" (%)");
        weftWaste.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNWASTE")));
        weftYarnGP.add(weftWaste, 0, 5);
        final TextField weftWasteTF = new TextField(Integer.toString(objConfiguration.getIntWeftWaste())){
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
        weftWasteTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNWASTE")));
        weftYarnGP.add(weftWasteTF, 1, 5);
        
        Separator sepVertWeft = new Separator();
        sepVertWeft.setOrientation(Orientation.VERTICAL);
        sepVertWeft.setValignment(VPos.CENTER);
        sepVertWeft.setPrefHeight(80);
        GridPane.setConstraints(sepVertWeft, 2, 0);
        GridPane.setRowSpan(sepVertWeft, 9);
        weftYarnGP.getChildren().add(sepVertWeft);
        
        Label weftCount= new Label(objDictionaryAction.getWord("YARNCOUNT")+" :");
        //weftCount.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        weftCount.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOUNT")));
        weftYarnGP.add(weftCount, 3, 0);
        intWeftCount = objConfiguration.getIntWeftCount();
        final TextField weftCountTF = new TextField(Integer.toString(intWeftCount)){
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
        weftCountTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOUNT")));
        weftYarnGP.add(weftCountTF, 4, 0);

        Label weftUnit= new Label(objDictionaryAction.getWord("YARNUNIT")+" :");
        //weftUnit.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        weftUnit.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNUNIT")));
        weftYarnGP.add(weftUnit, 3, 1);
        final ComboBox weftUnitCB = new ComboBox();
        
        weftUnitCB.getItems().addAll("Tex","dTex","K tex","Denier (Td)","New Metric (Nm)","Grains/Yard","Woollen (Aberdeen) (Ta)","Woollen (US Grain)","Asbestos (American) (NaA)","Asbestos (English) (NeA)","Cotton bump Yarn (NB)","Glass (UK & USA)","Linen (Set or Dry Spun) (NeL)","Spun Silk (Ns)","Woollen (American Cut) (Nac)","Woollen (American run) (Nar)","Woollen (Yarkshire) (Ny)","Woollen (Worsted) (New)","Linen, Hemp, Jute (Tj)","Micronaire (Mic)","Yards Per Pound (YPP)","English Worsted Count (NeK)","English Cotton (NeC)","New English (Ne)","Numero en puntos (Np)");  
        strWeftUnit = objConfiguration.getStrWeftUnit();
        weftUnitCB.setValue(strWeftUnit);
        weftUnitCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNUNIT")));
        weftYarnGP.add(weftUnitCB, 4, 1);
    
        Label weftPly= new Label(objDictionaryAction.getWord("YARNPLY")+" :");
        //weftPly.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        weftPly.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNPLY")));
        weftYarnGP.add(weftPly, 3, 2);
        final TextField weftPlyTF = new TextField(Integer.toString(objConfiguration.getIntWeftPly())){
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
        weftPlyTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNPLY")));
        weftYarnGP.add(weftPlyTF, 4, 2);

        Label weftFactor= new Label(objDictionaryAction.getWord("YARNFACTOR")+" :");
        //weftFactor.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        weftFactor.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNFACTOR")));
        weftYarnGP.add(weftFactor, 3, 3);
        final TextField weftFactorTF = new TextField(Integer.toString(objConfiguration.getIntWeftFactor())){
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
        weftFactorTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNFACTOR")));
        weftYarnGP.add(weftFactorTF, 4, 3);    
    
        Label weftDiameter= new Label(objDictionaryAction.getWord("YARNDIAMETER")+" (mm):");
        //weftDiameter.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        weftYarnGP.add(weftDiameter, 3, 4);
        
        try{
            FabricAction objFabricAction = new FabricAction();
            dblWeftDiameter = objFabricAction.calculateDiameter(objConfiguration.getIntWeftCount(),objConfiguration.getIntWeftPly(),objConfiguration.getIntWeftFactor());
        } catch(Exception ex){
            dblWeftDiameter = objConfiguration.getDblWeftDiameter();
        }
        final Label weftDiameterV= new Label(Double.toString(dblWeftDiameter));
        weftYarnGP.add(weftDiameterV, 4, 4);
    
        Label weftTwist = new Label(objDictionaryAction.getWord("YARNTWIST")+" :");
        //weftTwist.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        weftTwist.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNTWIST")));
        weftYarnGP.add(weftTwist, 3, 5);
        final TextField weftTwistTF = new TextField(Integer.toString(objConfiguration.getIntWeftTwist())){
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
        weftTwistTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNTWIST")));
        weftYarnGP.add(weftTwistTF, 4, 5);
    
        Label weftSence = new Label(objDictionaryAction.getWord("YARNSENCE")+" :");
        //weftSence.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        weftSence.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNSENCE")));
        weftYarnGP.add(weftSence, 3, 6);
        final ComboBox weftSenceCB = new ComboBox();
        weftSenceCB.getItems().addAll("O","S","Z");   
        weftSenceCB.setValue(objConfiguration.getStrWeftSence());
        weftSenceCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNSENCE")));
        weftYarnGP.add(weftSenceCB, 4, 6);
    
        Label weftHairness= new Label(objDictionaryAction.getWord("YARNHAIRNESS")+" :");
        //weftHairness.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        weftHairness.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNHAIRNESS")));
        weftYarnGP.add(weftHairness, 3, 7);
        final TextField weftHairnessTF = new TextField(Integer.toString(objConfiguration.getIntWeftHairness())){
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
        weftHairnessTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNHAIRNESS")));
        weftYarnGP.add(weftHairnessTF, 4, 7);
    
        Label weftDistribution= new Label(objDictionaryAction.getWord("YARNDISTRIBUTION")+" :");
        //weftDistribution.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        weftDistribution.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNDISTRIBUTION")));
        weftYarnGP.add(weftDistribution, 3, 8);
        final TextField weftDistributionTF = new TextField(Integer.toString(objConfiguration.getIntWeftDistribution())){
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
        weftDistributionTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNDISTRIBUTION")));
        weftYarnGP.add(weftDistributionTF, 4, 8);
    
        weftUnitCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    FabricAction objFabricAction = new FabricAction();
                    double newCount = objFabricAction.convertUnit(t, t1, Double.parseDouble(weftCountTF.getText()));
                    weftCountTF.setText(Double.toString(newCount));
                    newCount = objFabricAction.convertUnit(t1, "Tex", newCount);
                    double newDiameter = objFabricAction.calculateDiameter(newCount,Double.parseDouble(weftPlyTF.getText()),Double.parseDouble(weftFactorTF.getText()));
                    weftDiameterV.setText(Double.toString(newDiameter));
                } catch (SQLException ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"weft count unit property change",ex);
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"weft count unit property change",ex);
                }
            }
        });
        weftCountTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    FabricAction objFabricAction = new FabricAction();
                    double newCount = objFabricAction.convertUnit(weftUnitCB.getValue().toString(), "Tex", Double.parseDouble(weftCountTF.getText()));
                    double newDiameter = objFabricAction.calculateDiameter(newCount,Double.parseDouble(weftPlyTF.getText()),Double.parseDouble(weftFactorTF.getText()));
                    weftDiameterV.setText(Double.toString(newDiameter));
                } catch (SQLException ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"weft count property change",ex);
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"weft count property change",ex);
                }
            }
        });
        weftPlyTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    FabricAction objFabricAction = new FabricAction();
                    double newCount = objFabricAction.convertUnit(weftUnitCB.getValue().toString(), "Tex", Double.parseDouble(weftCountTF.getText()));
                    double newDiameter = objFabricAction.calculateDiameter(newCount,Double.parseDouble(weftPlyTF.getText()),Double.parseDouble(weftFactorTF.getText()));
                    weftDiameterV.setText(Double.toString(newDiameter));
                } catch (SQLException ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"weft ply property change",ex);
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"weft ply property change",ex);
                }
            }
        });
        weftFactorTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    FabricAction objFabricAction = new FabricAction();
                    double newCount = objFabricAction.convertUnit(weftUnitCB.getValue().toString(), "Tex", Double.parseDouble(weftCountTF.getText()));
                    double newDiameter = objFabricAction.calculateDiameter(newCount,Double.parseDouble(weftPlyTF.getText()),Double.parseDouble(weftFactorTF.getText()));
                    weftDiameterV.setText(Double.toString(newDiameter));
                } catch (SQLException ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"weft factor property change",ex);
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"weft factor property change",ex);
                }
            }
        });    
               
        Label warpName= new Label(objDictionaryAction.getWord("YARNNAME")+" :");
        //warpName.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        warpName.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNNAME")));
        warpYarnGP.add(warpName, 0, 0);
        final ComboBox warpNameCB = new ComboBox();
        warpNameCB.getItems().addAll("Cotton","Silk","Synthetic","Linen","Rayon","Novelty","Fleece","Wool");  
        warpNameCB.setValue(objConfiguration.getStrWarpName());
        warpNameCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNNAME")));
        warpYarnGP.add(warpNameCB, 1, 0);

        Label warpColor= new Label(objDictionaryAction.getWord("YARNCOLOR")+" :");
        //warpColor.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        warpColor.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOLOR")));
        warpYarnGP.add(warpColor, 0, 1);
        /*final ColorPicker warpColorCP = new ColorPicker();
        strWarpColor = objConfiguration.getStrWarpColor();
        warpColorCP.setOnAction(new EventHandler() {
            @Override
            public void handle(Event t) {
                Color c = warpColorCP.getValue();
                strWarpColor  = toRGBCode((Color)warpColorCP.getValue());
                strWarpColor = strWarpColor.substring(1, strWarpColor.length());
            }
        });
        warpColorCP.setValue(Color.web(strWarpColor)); 
        warpColorCP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOLOR")));
        warpYarnGP.add(warpColorCP, 1, 1);*/
		//<Added Date="25/10/2017">
        final ComboBox warpColorCB=new ComboBox();
        warpColorCB.setStyle("-fx-background-color:#"+objConfiguration.getStrWarpColor()+";");
        strWarpColor=objConfiguration.getStrWarpColor();
        warpColorCB.setOnShown(new EventHandler() {
            @Override
            public void handle(Event t) {
                try {
                    ColourSelector objColourSelector=new ColourSelector(objConfiguration);
                    if(objColourSelector.colorCode!=null&&objColourSelector.colorCode.length()>0){
                        warpColorCB.setStyle("-fx-background-color:#"+objColourSelector.colorCode+";");
                        strWarpColor=objColourSelector.colorCode;
                    }
                    warpColorCB.hide();
                    t.consume();
                } catch (Exception ex) {
                    Logger.getLogger(FabricSettingView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        warpColorCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOLOR")));
        warpYarnGP.add(warpColorCB, 1, 1);
        //</Added>
        
        Label warpPattern= new Label(objDictionaryAction.getWord("YARNPATTERN")+" :");
        //warpPattern.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        warpYarnGP.add(warpPattern, 0, 2);
        final Label warpPatternV= new Label(objConfiguration.getStrWarpPattern());
        //warpPatternV.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        warpYarnGP.add(warpPatternV, 1, 2);

        Label warpPrice= new Label(objDictionaryAction.getWord("YARNPRICE")+" (per gram):");
        //warpPrice.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        warpPrice.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNPRICE")));
        warpYarnGP.add(warpPrice, 0, 3);
        final TextField warpPriceTF = new TextField(Double.toString(objConfiguration.getDblWarpPrice())){
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
        warpPriceTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNPRICE")));
        warpYarnGP.add(warpPriceTF, 1, 3);
        
        Label warpCrimp = new Label(objDictionaryAction.getWord("YARNCRIMP")+" (%)");
        warpCrimp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCRIMP")));
        warpYarnGP.add(warpCrimp, 0, 4);
        final TextField warpCrimpTF = new TextField(Integer.toString(objConfiguration.getIntWarpCrimp())){
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
        warpCrimpTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCRIMP")));
        warpYarnGP.add(warpCrimpTF, 1, 4);
            
        Label warpWaste = new Label(objDictionaryAction.getWord("YARNWASTE")+" (%)");
        warpWaste.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNWASTE")));
        warpYarnGP.add(warpWaste, 0, 5);
        final TextField warpWasteTF = new TextField(Integer.toString(objConfiguration.getIntWarpWaste())){
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
        warpWasteTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNWASTE")));
        warpYarnGP.add(warpWasteTF, 1, 5);
        
        Separator sepVertWarp = new Separator();
        sepVertWarp.setOrientation(Orientation.VERTICAL);
        sepVertWarp.setValignment(VPos.CENTER);
        sepVertWarp.setPrefHeight(80);
        GridPane.setConstraints(sepVertWarp, 2, 0);
        GridPane.setRowSpan(sepVertWarp, 9);
        warpYarnGP.getChildren().add(sepVertWarp);
        
        Label warpCount= new Label(objDictionaryAction.getWord("YARNCOUNT")+" :");
        //warpCount.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        warpCount.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOUNT")));
        warpYarnGP.add(warpCount, 3, 0);
        intWarpCount = objConfiguration.getIntWarpCount();
        final TextField warpCountTF = new TextField(Integer.toString(intWarpCount)){
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
        warpCountTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOUNT")));
        warpYarnGP.add(warpCountTF, 4, 0);

        Label warpUnit= new Label(objDictionaryAction.getWord("YARNUNIT")+" :");
        //warpUnit.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        warpUnit.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNUNIT")));
        warpYarnGP.add(warpUnit, 3, 1);
        final ComboBox warpUnitCB = new ComboBox();
        warpUnitCB.getItems().addAll("Tex","dTex","K tex","Denier (Td)","New Metric (Nm)","Grains/Yard","Woollen (Aberdeen) (Ta)","Woollen (US Grain)","Asbestos (American) (NaA)","Asbestos (English) (NeA)","Cotton bump Yarn (NB)","Glass (UK & USA)","Linen (Set or Dry Spun) (NeL)","Spun Silk (Ns)","Woollen (American Cut) (Nac)","Woollen (American run) (Nar)","Woollen (Yarkshire) (Ny)","Woollen (Worsted) (New)","Linen, Hemp, Jute (Tj)","Micronaire (Mic)","Yards Per Pound (YPP)","English Worsted Count (NeK)","English Cotton (NeC)","New English (Ne)","Numero en puntos (Np)");  
        strWarpUnit = objConfiguration.getStrWarpUnit();
        warpUnitCB.setValue(strWarpUnit);
        warpUnitCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNUNIT")));
        warpYarnGP.add(warpUnitCB, 4, 1);

        Label warpPly= new Label(objDictionaryAction.getWord("YARNPLY")+" :");
        //warpPly.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        warpPly.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNPLY")));
        warpYarnGP.add(warpPly, 3, 2);
        final TextField warpPlyTF = new TextField(Integer.toString(objConfiguration.getIntWarpPly())){
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
        warpPlyTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNPLY")));
        warpYarnGP.add(warpPlyTF, 4, 2);

        Label warpFactor= new Label(objDictionaryAction.getWord("YARNFACTOR")+" :");
        //warpFactor.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        warpFactor.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNFACTOR")));
        warpYarnGP.add(warpFactor, 3, 3);
        final TextField warpFactorTF = new TextField(Integer.toString(objConfiguration.getIntWarpFactor())){
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
        warpFactorTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNFACTOR")));
        warpYarnGP.add(warpFactorTF, 4, 3);    

        Label warpDiameter= new Label(objDictionaryAction.getWord("YARNDIAMETER")+" (mm):");
        //warpDiameter.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        warpYarnGP.add(warpDiameter, 3, 4);
        
        try{
            FabricAction objFabricAction = new FabricAction();
            dblWarpDiameter = objFabricAction.calculateDiameter(objConfiguration.getIntWarpCount(),objConfiguration.getIntWarpPly(),objConfiguration.getIntWarpFactor());
        } catch(Exception ex){
            dblWarpDiameter = objConfiguration.getDblWarpDiameter();
        }
        final Label warpDiameterV= new Label(Double.toString(dblWarpDiameter));
        warpYarnGP.add(warpDiameterV, 4, 4);

        Label warpTwist = new Label(objDictionaryAction.getWord("YARNTWIST")+" :");
        //warpTwist.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        warpTwist.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNTWIST")));
        warpYarnGP.add(warpTwist, 3, 5);
        final TextField warpTwistTF = new TextField(Integer.toString(objConfiguration.getIntWarpTwist())){
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
        warpTwistTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNTWIST")));
        warpYarnGP.add(warpTwistTF, 4, 5);

        Label warpSence = new Label(objDictionaryAction.getWord("YARNSENCE")+" :");
        //warpSence.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        warpSence.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNSENCE")));
        warpYarnGP.add(warpSence, 3, 6);
        final ComboBox warpSenceCB = new ComboBox();
        warpSenceCB.getItems().addAll("O","S","Z");   
        warpSenceCB.setValue(objConfiguration.getStrWarpSence());
        warpSenceCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNSENCE")));
        warpYarnGP.add(warpSenceCB, 4, 6);

        Label warpHairness= new Label(objDictionaryAction.getWord("YARNHAIRNESS")+" :");
        //warpHairness.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        warpHairness.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNHAIRNESS")));
        warpYarnGP.add(warpHairness, 3, 7);
        final TextField warpHairnessTF = new TextField(Integer.toString(objConfiguration.getIntWarpHairness())){
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
        warpHairnessTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNHAIRNESS")));
        warpYarnGP.add(warpHairnessTF, 4, 7);

        Label warpDistribution= new Label(objDictionaryAction.getWord("YARNDISTRIBUTION")+" :");
        //warpDistribution.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        warpDistribution.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNDISTRIBUTION")));
        warpYarnGP.add(warpDistribution, 3, 8);
        final TextField warpDistributionTF = new TextField(Integer.toString(objConfiguration.getIntWarpDistribution())){
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
        warpDistributionTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNDISTRIBUTION")));
        warpYarnGP.add(warpDistributionTF, 4, 8);

        
        warpUnitCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    FabricAction objFabricAction = new FabricAction();
                    double newCount = objFabricAction.convertUnit(t, t1, Double.parseDouble(warpCountTF.getText()));
                    warpCountTF.setText(Double.toString(newCount));                
                    newCount = objFabricAction.convertUnit(t1, "Tex", newCount);
                    double newDiameter = objFabricAction.calculateDiameter(newCount,Double.parseDouble(warpPlyTF.getText()),Double.parseDouble(warpFactorTF.getText()));
                    warpDiameterV.setText(Double.toString(newDiameter));
                } catch (SQLException ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"warp count unit property change",ex);
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"warp count unit property change",ex);
                }
            }
        });
        warpCountTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    FabricAction objFabricAction = new FabricAction();
                    double newCount = objFabricAction.convertUnit(warpUnitCB.getValue().toString(), "Tex", Double.parseDouble(warpCountTF.getText()));
                    double newDiameter = objFabricAction.calculateDiameter(newCount,Double.parseDouble(warpPlyTF.getText()),Double.parseDouble(warpFactorTF.getText()));
                    warpDiameterV.setText(Double.toString(newDiameter));
                } catch (SQLException ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"warp count property change",ex);
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"warp count property change",ex);
                }
            }
        });
        warpPlyTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    FabricAction objFabricAction = new FabricAction();
                    double newCount = objFabricAction.convertUnit(warpUnitCB.getValue().toString(), "Tex", Double.parseDouble(warpCountTF.getText()));
                    double newDiameter = objFabricAction.calculateDiameter(newCount,Double.parseDouble(warpPlyTF.getText()),Double.parseDouble(warpFactorTF.getText()));
                    warpDiameterV.setText(Double.toString(newDiameter));
                } catch (SQLException ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"warp ply property change",ex);
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"warp ply property change",ex);
                }
            }
        });
        warpFactorTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    FabricAction objFabricAction = new FabricAction();
                    double newCount = objFabricAction.convertUnit(warpUnitCB.getValue().toString(), "Tex", Double.parseDouble(warpCountTF.getText()));
                    double newDiameter = objFabricAction.calculateDiameter(newCount,Double.parseDouble(warpPlyTF.getText()),Double.parseDouble(warpFactorTF.getText()));
                    warpDiameterV.setText(Double.toString(newDiameter));
                } catch (SQLException ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"warp factor property change",ex);
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",FabricSettingView.class.getName(),"warp factor property change",ex);
                }
            }
        });         
        
        
        GridPane bodyContainer = new GridPane();
        bodyContainer.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        bodyContainer.setId("container");
        
        Label caption = new Label(objDictionaryAction.getWord("FABRIC")+" "+objDictionaryAction.getWord("CONFIGURATION"));
        caption.setId("caption");
        bodyContainer.add(caption, 0, 0, 2, 1);
        
        bodyContainer.add(tabPane, 0, 1, 2, 1);
        
        final Separator sepHor1 = new Separator();
        sepHor1.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor1, 0, 2);
        GridPane.setColumnSpan(sepHor1, 2);
        bodyContainer.getChildren().add(sepHor1);
 
        Button btnApply = new Button(objDictionaryAction.getWord("APPLY"));
        btnApply.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
        btnApply.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPAPPLY")));
        bodyContainer.add(btnApply, 0, 3);
        
        Button btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
        btnCancel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
        btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
        bodyContainer.add(btnCancel, 1, 3);
        
        root.setCenter(bodyContainer);
        
        btnApply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                new MessageView(objConfiguration);
                if(objConfiguration.getServicePasswordValid()){
                   objConfiguration.setServicePasswordValid(false);                
                    objConfiguration.setStrDefaultFabric(fabricDefaultTF.getText().toString());
                    objConfiguration.setStrClothType(clothTypeCB.getValue().toString());
                    objConfiguration.setStrFabricType(fabricTypeCB.getValue().toString());
                    objConfiguration.setDblFabricLength(Double.parseDouble(fabricLengthTF.getText().toString()));
                    objConfiguration.setDblFabricWidth(Double.parseDouble(fabricWidthTF.getText().toString()));
                    objConfiguration.setDblArtworkLength(Double.parseDouble(artworkLengthLbl.getText().toString()));
                    objConfiguration.setDblArtworkWidth(Double.parseDouble(artworkWidthLbl.getText().toString()));
                    objConfiguration.setIntPPI(Integer.parseInt(ppiTF.getText().toString()));
                    objConfiguration.setIntEPI(Integer.parseInt(epiTF.getText().toString()));
                    objConfiguration.setIntHPI(Integer.parseInt(hpiTF.getText().toString()));
                    objConfiguration.setIntTPD(Integer.parseInt(tpdTF.getText().toString()));
                    objConfiguration.setIntHooks(Integer.parseInt(hooksTF.getText().toString()));
                    objConfiguration.setIntEnds(Integer.parseInt(endsTF.getText().toString()));
                    objConfiguration.setIntPixs(Integer.parseInt(pixsTF.getText().toString()));
                    objConfiguration.setIntReedCount(Integer.parseInt(reedCountTF.getText().toString()));
                    objConfiguration.setIntDents(Integer.parseInt(dentsTF.getText().toString()));
                    objConfiguration.setStrWeftName(weftNameCB.getValue().toString());
                    objConfiguration.setStrWeftPattern(weftPatternV.getText().toString());            
                    objConfiguration.setStrWeftColor(strWeftColor);
                    objConfiguration.setIntWeftCount(Integer.parseInt(weftCountTF.getText().toString()));
                    objConfiguration.setStrWeftUnit(weftUnitCB.getValue().toString());
                    objConfiguration.setIntWeftFactor(Integer.parseInt(weftFactorTF.getText().toString()));
                    objConfiguration.setDblWeftDiameter(Double.parseDouble(weftDiameterV.getText().toString()));
                    objConfiguration.setIntWeftTwist(Integer.parseInt(weftTwistTF.getText().toString()));
                    objConfiguration.setStrWeftSence(weftSenceCB.getValue().toString());
                    objConfiguration.setIntWeftHairness(Integer.parseInt(weftHairnessTF.getText().toString()));
                    objConfiguration.setIntWeftDistribution(Integer.parseInt(weftDistributionTF.getText().toString()));
                    objConfiguration.setDblWeftPrice(Double.parseDouble(weftPriceTF.getText().toString()));
                    objConfiguration.setIntWeftCrimp(Integer.parseInt(weftCrimpTF.getText().toString()));
                    objConfiguration.setIntWeftWaste(Integer.parseInt(weftWasteTF.getText().toString()));
                    objConfiguration.setStrWarpName(warpNameCB.getValue().toString());
                    objConfiguration.setStrWarpPattern(warpPatternV.getText().toString());
                    objConfiguration.setStrWarpColor(strWarpColor);
                    objConfiguration.setIntWarpCount(Integer.parseInt(warpCountTF.getText().toString()));
                    objConfiguration.setStrWarpUnit(warpUnitCB.getValue().toString());
                    objConfiguration.setIntWarpFactor(Integer.parseInt(warpFactorTF.getText().toString()));
                    objConfiguration.setDblWarpDiameter(Double.parseDouble(warpDiameterV.getText().toString()));
                    objConfiguration.setIntWarpTwist(Integer.parseInt(warpTwistTF.getText().toString()));
                    objConfiguration.setStrWarpSence(warpSenceCB.getValue().toString());
                    objConfiguration.setIntWarpHairness(Integer.parseInt(warpHairnessTF.getText().toString()));
                    objConfiguration.setIntWarpDistribution(Integer.parseInt(warpDistributionTF.getText().toString()));
                    objConfiguration.setDblWarpPrice(Double.parseDouble(warpPriceTF.getText().toString()));
                    objConfiguration.setIntWarpCrimp(Integer.parseInt(warpCrimpTF.getText().toString()));
                    objConfiguration.setIntWarpWaste(Integer.parseInt(warpWasteTF.getText().toString()));
                    objConfiguration.setIntProfit(Integer.parseInt(profitTF.getText().toString()));
                    objConfiguration.setIntOverheadCost(Integer.parseInt(overheadCostTF.getText().toString()));
                    objConfiguration.setDblDesigningCost(Double.parseDouble(designingCostTF.getText().toString()));
                    objConfiguration.setDblPunchingCost(Double.parseDouble(punchingCostTF.getText().toString()));
                    objConfiguration.setDblPropertyCost(Double.parseDouble(propertyCostTF.getText().toString()));
                    objConfiguration.setDblWagesCost(Double.parseDouble(wagesCostTF.getText().toString()));
                    objConfiguration.setIntBinding(Integer.parseInt(bindingTF.getText().toString()));
                    objConfiguration.setIntProtection(Integer.parseInt(protectionTF.getText().toString()));
                    if(1000/objConfiguration.getIntPixs()>Integer.parseInt(verticalTF.getText().toString()) && Integer.parseInt(verticalTF.getText().toString())>0)
                        objConfiguration.setIntVRepeat(Integer.parseInt(verticalTF.getText().toString()));
                    else
                        objConfiguration.setIntVRepeat(1);//objConfiguration.setIntVRepeat(1000/objConfiguration.getIntPixs());
                    if(1000/objConfiguration.getIntHooks()>Integer.parseInt(horizentalTF.getText().toString()) && Integer.parseInt(horizentalTF.getText().toString())>0)
                        objConfiguration.setIntHRepeat(Integer.parseInt(horizentalTF.getText().toString()));
                    else
                        objConfiguration.setIntHRepeat(1);//objConfiguration.setIntHRepeat(1000/objConfiguration.getIntHooks());
                    objConfiguration.setStrGraphSize(graphSizeCB.getText().toString());
                    objConfiguration.setBlnMPrint(multiplePrintCB.isSelected());
                    objConfiguration.setBlnPunchCard(punchCardCB.isSelected());
                    objConfiguration.setBlnMRepeat(multipleRepeatCB.isSelected());
                    objConfiguration.setBlnErrorGraph(errorGraphCB.isSelected());
                    objConfiguration.setBlnCorrectGraph(correctGraphCB.isSelected());
                    objConfiguration.setIntFloatBind(Integer.parseInt(doubleBindTF.getText()));
                    objConfiguration.setIntColorLimit(Integer.parseInt(colorLimitTF.getText()));
                    try {
                        UtilityAction objUtilityAction=new UtilityAction();
                        String id=(String)colourCB.getValue().toString();
                        
                        if(id!=null){
                            Palette objPalette = new Palette();
                            objPalette.setStrPaletteID(id.substring(0, id.indexOf('-')));
                            objPalette.setStrPaletteName(id.substring(id.indexOf('-')+1, id.length()));
                            objPalette.setStrPaletteType("Colour");
                            objPalette.setObjConfiguration(objConfiguration);
                            objUtilityAction.getPalette(objPalette);
                            if(objPalette.getStrThreadPalette()!=null){
                                objConfiguration.setColourPalette(objPalette.getStrThreadPalette());
                            }
                            //objConfiguration.setStrColourPaletteId(id);
                        }
                        UserAction objUserAction = new UserAction();
                        objUserAction.updateConfiguration(objConfiguration);
                    }catch(SQLException sqlEx){
                        new Logging("SEVERE",FabricSettingView.class.getName(),"change color palette value : "+sqlEx.getMessage(), sqlEx);
                    } 
                    catch (Exception ex) {
                        new Logging("SEVERE",FabricSettingView.class.getName(),"save configuration",ex);
                    }
                    System.gc();
                    fabricSettingStage.close();
                    WindowView objWindowView = new WindowView(objConfiguration);
                }
            }
        });
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
             @Override
            public void handle(ActionEvent e) {
                objConfiguration.setStrClothType(strClothType);
                objConfiguration.setStrFabricType(strFabricType);
                objConfiguration.setStrDefaultFabric(strDefaultFabric);
                objConfiguration.setDblFabricLength(dblFabricLength);
                objConfiguration.setDblFabricWidth(dblFabricWidth);
                objConfiguration.setDblArtworkLength(dblArtworkLength);
                objConfiguration.setDblArtworkWidth(dblArtworkWidth);
                objConfiguration.setIntPPI(intPPI);
                objConfiguration.setIntEPI(intEPI);
                objConfiguration.setIntHPI(intHPI);
                objConfiguration.setIntTPD(intTPD);
                objConfiguration.setIntHooks(intHooks);
                objConfiguration.setIntEnds(intEnds);
                objConfiguration.setIntPixs(intPixs);
                objConfiguration.setIntReedCount(intReedCount);
                objConfiguration.setIntDents(intDents); 
                objConfiguration.setIntWeftCount(intWeftCount);
                objConfiguration.setStrWeftUnit(strWeftUnit);
                objConfiguration.setDblWeftDiameter(dblWeftDiameter);
                objConfiguration.setIntWarpCount(intWarpCount);
                objConfiguration.setStrWarpUnit(strWarpUnit);
                objConfiguration.setDblWarpDiameter(dblWarpDiameter);
                System.gc();
                fabricSettingStage.close();
                WindowView objWindowView = new WindowView(objConfiguration);
            }
        });
        
        fabricSettingStage.getIcons().add(new Image("/media/icon.png"));
        fabricSettingStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWFABRICSETTINGS")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        //fabricSettingStage.setIconified(true);
        fabricSettingStage.setResizable(false);
        fabricSettingStage.setScene(scene);
        fabricSettingStage.setX(0);
        fabricSettingStage.setY(0);
        fabricSettingStage.show();
        fabricSettingStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
                scene.getStylesheets().add(FabricSettingView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        objConfiguration.setStrClothType(strClothType);
                        objConfiguration.setStrFabricType(strFabricType);
                        objConfiguration.setStrDefaultFabric(strDefaultFabric);
                        objConfiguration.setDblFabricLength(dblFabricLength);
                        objConfiguration.setDblFabricWidth(dblFabricWidth);
                        objConfiguration.setDblArtworkLength(dblArtworkLength);
                        objConfiguration.setDblArtworkWidth(dblArtworkWidth);
                        objConfiguration.setIntPPI(intPPI);
                        objConfiguration.setIntEPI(intEPI);
                        objConfiguration.setIntHPI(intHPI);
                        objConfiguration.setIntTPD(intTPD);
                        objConfiguration.setIntHooks(intHooks);
                        objConfiguration.setIntEnds(intEnds);
                        objConfiguration.setIntPixs(intPixs);
                        objConfiguration.setIntReedCount(intReedCount);
                        objConfiguration.setIntDents(intDents); 
                        objConfiguration.setIntWeftCount(intWeftCount);
                        objConfiguration.setStrWeftUnit(strWeftUnit);
                        objConfiguration.setDblWeftDiameter(dblWeftDiameter);
                        objConfiguration.setIntWarpCount(intWarpCount);
                        objConfiguration.setStrWarpUnit(strWarpUnit);
                        objConfiguration.setDblWarpDiameter(dblWarpDiameter);
                        dialogStage.close();
                        fabricSettingStage.close();
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
		final KeyCodeCombination kchA = new KeyCodeCombination(KeyCode.H, KeyCombination.ALT_DOWN); // Home Menu]
        scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if(kchA.match(t)){
                    homeMenuAction();
                }
            }
        });
    }
    
    public void homeMenuAction(){
        final Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setResizable(false);
        dialogStage.setIconified(false);
        dialogStage.setFullScreen(false);
        dialogStage.setTitle(objDictionaryAction.getWord("ALERT"));
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 300, 100, Color.WHITE);
        scene.getStylesheets().add(FabricSettingView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                objConfiguration.setStrClothType(strClothType);
                objConfiguration.setStrFabricType(strFabricType);
                objConfiguration.setStrDefaultFabric(strDefaultFabric);
                objConfiguration.setDblFabricLength(dblFabricLength);
                objConfiguration.setDblFabricWidth(dblFabricWidth);
                objConfiguration.setDblArtworkLength(dblArtworkLength);
                objConfiguration.setDblArtworkWidth(dblArtworkWidth);
                objConfiguration.setIntPPI(intPPI);
                objConfiguration.setIntEPI(intEPI);
                objConfiguration.setIntHPI(intHPI);
                objConfiguration.setIntTPD(intTPD);
                objConfiguration.setIntHooks(intHooks);
                objConfiguration.setIntEnds(intEnds);
                objConfiguration.setIntPixs(intPixs);
                objConfiguration.setIntReedCount(intReedCount);
                objConfiguration.setIntDents(intDents); 
                objConfiguration.setIntWeftCount(intWeftCount);
                objConfiguration.setStrWeftUnit(strWeftUnit);
                objConfiguration.setDblWeftDiameter(dblWeftDiameter);
                objConfiguration.setIntWarpCount(intWarpCount);
                objConfiguration.setStrWarpUnit(strWarpUnit);
                objConfiguration.setDblWarpDiameter(dblWarpDiameter);
                dialogStage.close();
                fabricSettingStage.close();
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
	public void loadColourPaletteNames(ComboBox colourCB){
        colourCB.getItems().clear();
        try{
            UtilityAction objUtilityAction=new UtilityAction();
            Palette objPalette=new Palette();
            objPalette.setObjConfiguration(objConfiguration);
            objPalette.setStrPaletteType("Colour");
            String[][] paletteNames=objUtilityAction.getPaletteName(objPalette);
            if(paletteNames!=null){
                for(String[] s:paletteNames){
                    colourCB.getItems().add(s[0]+"-"+s[1]);
                }
                colourCB.setValue("Select Palette");
            }
        } catch(SQLException sqlEx){
            new Logging("SEVERE",FabricSettingView.class.getName(),"loadColourPaletteName() : ", sqlEx);
        }
    }
    public String toRGBCode( Color color ){
        return String.format( "#%02X%02X%02X",
            (int)( color.getRed() * 255 ),
            (int)( color.getGreen() * 255 ),
            (int)( color.getBlue() * 255 ) );
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        new FabricSettingView(stage);
        new Logging("WARNING",FabricSettingView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  /*
  public static void main(String[] args) {   
      launch(args);    
  }
  */
}