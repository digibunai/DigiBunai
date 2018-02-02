/*
 * Copyright (C) 2017 HP
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

package com.mla.utility;

import com.mla.colour.ColorPaletteView;
import com.mla.colour.Colour;
import com.mla.colour.ColourAction;
import com.mla.dictionary.DictionaryAction;
import com.mla.fabric.FabricAction;
import com.mla.main.AboutView;
import com.mla.main.Configuration;
import com.mla.main.ContactView;
import com.mla.main.HelpView;
import com.mla.main.IDGenerator;
import com.mla.main.Logging;
import com.mla.main.TechnicalView;
import com.mla.yarn.Yarn;
import com.mla.yarn.YarnAction;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
public class PaletteView extends Application {
    public static Stage paletteStage;
    BorderPane root;
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;
    
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    UtilityAction objUtilityAction;
    YarnAction objYarnAction;
    ColourAction objColourAction;
    
    Palette objPalette;
    
    private Menu homeMenu;
    private Menu helpMenu;

    GridPane GP_yarn;
    GridPane GP_colour;
    GridPane allColorGP;
    ScrollPane allColorSP;
    
    ComboBox yarnCB;
    ComboBox colourCB;
    
    private ImageView yarnImage;
    ColorPicker color;
    ComboBox name;
    ComboBox countUnit;
    Label type;
    Label repeat;
    Label symbol;
    Label diameter;
    TextField count;
    TextField ply;
    TextField factor;    
    TextField txtTwistCount;
    TextField txtHairLength;
    TextField txtHairPercentage;
    TextField price;
    ToggleGroup twistScene;
    
    private Button btnSwitchYarn;
    private Button btnWarpToWeftYarn;
    private Button btnWeftToWarpYarn;
    private Button btnClearYarn;
    private Button btnResetYarn;
    private Button btnNewYarn;
    private Button btnSaveYarn;
    private Button btnSaveAsYarn;
    private Button btnDeleteYarn;
    private Button btnNewColour;
    private Button btnSaveColour;
    private Button btnSaveAsColour;
    private Button btnDeleteColour;
    private Button btnSwitchColour;
    private Button btnWarpToWeftColour;
    private Button btnWeftToWarpColour;
    private Button btnClearColour;
    private Button btnResetColour;
    
    private Button btnPreview;
    private Button btnApply;
    
    private BufferedImage bufferedImage;
    int xindex=0, yindex=0, yarnIndex=-1;
    String[][] threadPalette = new String[52][2];
        
    
    public PaletteView(final Stage primaryStage) {}
    
    public PaletteView(Configuration objConfigurationCall) {
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);
        
        objPalette = new Palette();
        objPalette.setObjConfiguration(objConfiguration);
        
        paletteStage = new Stage(); 
        root = new BorderPane();
        Scene scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(PaletteView.class.getResource(objConfiguration.getStrTemplate()+"/setting.css").toExternalForm());
        
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
        menuBar.prefWidthProperty().bind(paletteStage.widthProperty());
        
        homeMenu  = new Menu();
        final HBox homeMenuHB = new HBox();
        homeMenuHB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/home.png"),new Label(objDictionaryAction.getWord("HOME")));
        homeMenu.setGraphic(homeMenuHB);
        homeMenuHB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                final Stage dialogStage = new Stage();
                dialogStage.initStyle(StageStyle.UTILITY);
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.setResizable(false);
                dialogStage.setIconified(false);
                dialogStage.setFullScreen(false);
                dialogStage.setTitle(objDictionaryAction.getWord("ALERT"));
                BorderPane root = new BorderPane();
                Scene scene = new Scene(root, 300, 100, Color.WHITE);
                scene.getStylesheets().add(PaletteView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        paletteStage.close();
                        System.gc();
                        UtilityView objUtilityView = new UtilityView(objConfiguration);
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
                paletteStage.close();    
            }
        });    
        helpMenu.getItems().addAll(helpMenuItem, technicalMenuItem, aboutMenuItem, contactMenuItem, new SeparatorMenuItem(), exitMenuItem);        
        menuBar.getMenus().addAll(homeMenu, helpMenu);
        root.setTop(menuBar);
        
        ScrollPane container = new ScrollPane();
        
        TabPane tabPane = new TabPane();

        Tab yarnTab = new Tab();
        Tab colourTab = new Tab();
        
        yarnTab.setClosable(false);
        colourTab.setClosable(false);
        
        yarnTab.setText(objDictionaryAction.getWord("YARNPALETTE"));
        colourTab.setText(objDictionaryAction.getWord("WINDOWCOLORPALETTE"));
        
        yarnTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNPALETTE")));
        colourTab.setTooltip(new Tooltip(objDictionaryAction.getWord("WINDOWCOLORPALETTE")));
        
        //yarnTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/color_editor.png"));
        //colourTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/color_palette.png"));
        
        GridPane yarnGP = new GridPane();
        GridPane colourGP = new GridPane();
        
        yarnGP.setId("container");
        colourGP.setId("container");
        
        //yarnGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //colourGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        
        //yarnGP.setAlignment(Pos.TOP_CENTER);
        //colourGP.setAlignment(Pos.TOP_CENTER);
        
        yarnTab.setContent(yarnGP);
        colourTab.setContent(colourGP);
        
        //tabPane.getTabs().add(yarnTab);
        tabPane.getTabs().add(colourTab);
        
        //////---------Yarn Count-------//////
        Label yarn = new Label(objDictionaryAction.getWord("YARNPALETTE"));
        yarn.setGraphic(new ImageView(objConfiguration.getStrColour()+"/color_editor.png"));
        yarn.setId("caption");
        GridPane.setConstraints(yarn, 0, 0);
        GridPane.setColumnSpan(yarn, 5);
        yarnGP.getChildren().add(yarn);
        
        // added Palette Name
        Label yarnLbl= new Label(objDictionaryAction.getWord("PALETTE")+" :");
        yarnLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPALETTE")));
        yarnGP.add(yarnLbl, 0, 1, 1, 1);
        yarnCB = new ComboBox();
        yarnCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPALETTE")));
        yarnGP.add(yarnCB, 1, 1, 1, 1);
        loadYarnPaletteNames();
        yarnCB.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                String id=(String)t1;
                objPalette.setStrPaletteID((String)t1);
                objPalette.setStrPaletteName((String)t1);
                objPalette.setStrPaletteType("Yarn");
                yarnAction("Open");
            }
        });
  
        btnNewYarn = new Button(objDictionaryAction.getWord("CREATE"));
        btnSaveYarn = new Button(objDictionaryAction.getWord("SAVE"));
        btnSaveAsYarn = new Button(objDictionaryAction.getWord("SAVEAS"));
        btnDeleteYarn = new Button(objDictionaryAction.getWord("DELETE"));
       
        //btnNewYarn.setMaxWidth(Double.MAX_VALUE);
        //btnSaveYarn.setMaxWidth(Double.MAX_VALUE);
        //btnSaveAsYarn.setMaxWidth(Double.MAX_VALUE);        
        //btnDeleteYarn.setMaxWidth(Double.MAX_VALUE);
        
        btnNewYarn.setGraphic(new ImageView(objConfiguration.getStrColour()+"/new.png"));
        btnSaveYarn.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
        btnSaveAsYarn.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save_as.png"));
        btnDeleteYarn.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
       
        btnNewYarn.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCREATE")));
        btnSaveYarn.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVE")));
        btnSaveAsYarn.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVEAS")));
        btnDeleteYarn.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDELETE")));
        
        btnNewYarn.setDisable(false);
        btnSaveYarn.setDisable(false);
        btnSaveAsYarn.setDisable(false);
        btnDeleteYarn.setDisable(false);

        btnNewYarn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                yarnAction("Create");
            }
        });
        btnSaveYarn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                yarnAction("Save");
            }
        });
        btnSaveAsYarn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                yarnAction("SaveAs");
            }
        });
        btnDeleteYarn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                yarnAction("Delete");
            }
        });

        yarnGP.add(btnNewYarn, 2, 1, 1, 1);
        yarnGP.add(btnSaveYarn, 3, 1, 1, 1);
        yarnGP.add(btnSaveAsYarn, 4, 1, 1, 1);
        yarnGP.add(btnDeleteYarn, 5, 1, 1, 1);
        
        Separator sepHor1 = new Separator();
        sepHor1.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor1, 0, 2);
        GridPane.setColumnSpan(sepHor1, 6);
        yarnGP.getChildren().add(sepHor1);
        
        GP_yarn = new GridPane();
        Label warpYL = new Label(objDictionaryAction.getWord("WARP")+" "+objDictionaryAction.getWord("YARN")+": ");
        warpYL.setFont(Font.font("Arial", 16));
        GP_yarn.add(warpYL, 0, 0);
        Label weftYL = new Label(objDictionaryAction.getWord("WEFT")+" "+objDictionaryAction.getWord("YARN")+": ");
        weftYL.setFont(Font.font("Arial", 16));
        GP_yarn.add(weftYL, 0, 1);
        yarnGP.add(GP_yarn, 0, 3, 6, 1);
        
        btnSwitchYarn = new Button(objDictionaryAction.getWord("SWAPCOLOR"));
        btnWarpToWeftYarn = new Button(objDictionaryAction.getWord("WARPTOWEFT"));
        btnWeftToWarpYarn = new Button(objDictionaryAction.getWord("WEFTTOWARP"));
        btnClearYarn = new Button(objDictionaryAction.getWord("CLEAR"));
        btnResetYarn = new Button(objDictionaryAction.getWord("RESET"));
        
        //btnSwitchYarn.setMaxWidth(Double.MAX_VALUE);
        //btnWarpToWeftYarn.setMaxWidth(Double.MAX_VALUE);
        //btnWeftToWarpYarn.setMaxWidth(Double.MAX_VALUE);
        //btnClearYarn.setMaxWidth(Double.MAX_VALUE);
        //btnResetYarn.setMaxWidth(Double.MAX_VALUE);
        
        btnSwitchYarn.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
        btnWarpToWeftYarn.setGraphic(new ImageView(objConfiguration.getStrColour()+"/move_right.png"));
        btnWeftToWarpYarn.setGraphic(new ImageView(objConfiguration.getStrColour()+"/move_left.png"));
        btnClearYarn.setGraphic(new ImageView(objConfiguration.getStrColour()+"/clear.png"));
        btnResetYarn.setGraphic(new ImageView(objConfiguration.getStrColour()+"/clear.png"));
        
        btnSwitchYarn.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSWAPCOLOR")));
        btnWarpToWeftYarn.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWARPTOWEFT")));
        btnWeftToWarpYarn.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWEFTTOWARP")));
        btnClearYarn.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLEAR")));
        btnResetYarn.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPRESET")));
        
        btnSwitchYarn.setDisable(false);
        btnWarpToWeftYarn.setDisable(false);
        btnWeftToWarpYarn.setDisable(false);
        btnClearYarn.setDisable(false);
        btnResetYarn.setDisable(false);
        
        btnSwitchYarn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                yarnAction("Switch");
            }
        });
        btnWarpToWeftYarn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                yarnAction("WarpWeft");
            }
        });
        btnWeftToWarpYarn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                yarnAction("WeftWarp");
            }
        });
        btnClearYarn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                yarnAction("Clear");
            }
        });
        btnResetYarn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                yarnAction("Reset");
            }
        });
        
        yarnGP.add(btnSwitchYarn, 1, 4, 1, 1);
        yarnGP.add(btnWarpToWeftYarn, 2, 4, 1, 1);
        yarnGP.add(btnWeftToWarpYarn, 3, 4, 1, 1);
        yarnGP.add(btnClearYarn, 4, 4, 1, 1);
        yarnGP.add(btnResetYarn, 5, 4, 1, 1);
        
        Separator sepHor2 = new Separator();
        sepHor2.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor2, 0, 5);
        GridPane.setColumnSpan(sepHor2, 6);
        yarnGP.getChildren().add(sepHor2);
        
        yarnGP.add(new Label(objDictionaryAction.getWord("YARNTYPE")), 0, 6);
        yarnGP.add(new Label(objDictionaryAction.getWord("YARNNAME")), 0, 7);
        yarnGP.add(new Label(objDictionaryAction.getWord("YARNCOUNT")), 0, 8);
        yarnGP.add(new Label(objDictionaryAction.getWord("YARNUNIT")), 0, 9);
        yarnGP.add(new Label(objDictionaryAction.getWord("YARNPLY")), 0, 10);
        yarnGP.add(new Label(objDictionaryAction.getWord("YARNFACTOR")), 0, 11);
        yarnGP.add(new Label(objDictionaryAction.getWord("YARNDIAMETER")), 0, 12); 
        yarnGP.add(new Label(objDictionaryAction.getWord("YARNPRICE")), 0, 13);
        yarnGP.add(new Label(objDictionaryAction.getWord("YARNCOLOR")), 2, 6);
        yarnGP.add(new Label(objDictionaryAction.getWord("YARNREPEAT")), 2, 7);
        yarnGP.add(new Label(objDictionaryAction.getWord("YARNSYMBOL")), 2, 8);
        yarnGP.add(new Label(objDictionaryAction.getWord("YARNSENCE")), 2, 9);          
        yarnGP.add(new Label(objDictionaryAction.getWord("YARNTWIST")), 2, 10);
        yarnGP.add(new Label(objDictionaryAction.getWord("YARNHAIRNESS")), 2, 11);
        yarnGP.add(new Label(objDictionaryAction.getWord("YARNDISTRIBUTION")), 2, 12);
        
        type = new Label();
        yarnGP.add(type, 1, 6);
        name = new ComboBox();
        name.getItems().addAll("Cotton","Silk","Wool","Jute","Linen","Flex","Hemp");  
        name.setValue("Cotton");
        yarnGP.add(name, 1, 7);
        count = new TextField("1"){
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
        yarnGP.add(count, 1, 8);
        countUnit = new ComboBox();
        countUnit.getItems().addAll("Tex","dTex","K tex","Denier (Td)","Nm","Grains/Yard","Woollen (Aberdeen) (Ta)","Woollen (US Grain)","Asbestos (American) (NaA)","Asbestos (English) (NeA)","Cotton bump Yarn (NB)","Glass (UK & USA)","Linen (Set or Dry Spun) (NeL)","Spun Silk (Ns)","Woollen (American Cut) (Nac)","Woollen (American run) (Nar)","Woollen (Yarkshire) (Ny)","Woollen (Worsted) (New)","Linen, Hemp, Jute (Tj)","Micronaire (Mic)","Yards Per Pound (YPP)","English Worsted Count (NeK)","English Cotton (NeC)","Numero en puntos (Np)");  
        countUnit.setValue("Tex");
        yarnGP.add(countUnit, 1, 9);
        ply = new TextField("1"){
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
        yarnGP.add(ply, 1, 10);
        factor = new TextField("1"){
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
        yarnGP.add(factor, 1, 11);        
        diameter= new Label(); 
        yarnGP.add(diameter, 1, 12);
        price = new TextField("0");
        yarnGP.add(price, 1, 13);
        color = new ColorPicker();
        color.setValue(Color.web("#012345"));
        color.setEditable(true);
        yarnGP.add(color, 3, 6);
        repeat = new Label("1");
        yarnGP.add(repeat, 3, 7);
        symbol = new Label("");
        yarnGP.add(symbol, 3, 8);
        HBox tsHB = new HBox();
        twistScene = new ToggleGroup();
        RadioButton otsRB = new RadioButton("O");        
        otsRB.setToggleGroup(twistScene);
        otsRB.setUserData("O");
        otsRB.setSelected(true);
        RadioButton stsRB = new RadioButton("S");
        stsRB.setToggleGroup(twistScene);
        stsRB.setUserData("S");
        RadioButton ztsRB = new RadioButton("Z");
        ztsRB.setToggleGroup(twistScene);
        ztsRB.setUserData("Z");
        tsHB.getChildren().addAll(otsRB,stsRB,ztsRB);
        yarnGP.add(tsHB, 3, 9);
        txtTwistCount = new TextField("0"){
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
        txtTwistCount.setEditable(false);
        yarnGP.add(txtTwistCount, 3, 10);
        txtHairLength = new TextField("0"){
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
        yarnGP.add(txtHairLength, 3, 11);
        txtHairPercentage = new TextField("0"){
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
        yarnGP.add(txtHairPercentage, 3, 12);
        
        color.setOnAction(new EventHandler() {
            @Override
            public void handle(Event t) {
                Color c = color.getValue();
            }
        });
        countUnit.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    FabricAction objFabricAction = new FabricAction();
                    double newCount = objFabricAction.convertUnit(t, t1, Double.parseDouble(count.getText()));
                    count.setText(Double.toString(newCount));
                    newCount = objFabricAction.convertUnit(t1, "Tex", newCount);
                    double newDiameter = objFabricAction.calculateDiameter(newCount,Double.parseDouble(ply.getText()),Double.parseDouble(factor.getText()));
                    diameter.setText(Double.toString(newDiameter));
                } catch (SQLException ex) {
                    new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        count.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    FabricAction objFabricAction = new FabricAction();
                    double newCount = objFabricAction.convertUnit(countUnit.getValue().toString(), "Tex", Double.parseDouble(count.getText()));
                    double newDiameter = objFabricAction.calculateDiameter(newCount,Double.parseDouble(ply.getText()),Double.parseDouble(factor.getText()));
                    diameter.setText(Double.toString(newDiameter));                   
                } catch (SQLException ex) {
                    new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        ply.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    FabricAction objFabricAction = new FabricAction();
                    double newCount = objFabricAction.convertUnit(countUnit.getValue().toString(), "Tex", Double.parseDouble(count.getText()));
                    double newDiameter = objFabricAction.calculateDiameter(newCount,Double.parseDouble(ply.getText()),Double.parseDouble(factor.getText()));
                    diameter.setText(Double.toString(newDiameter));
                } catch (SQLException ex) {
                    new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        factor.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    FabricAction objFabricAction = new FabricAction();
                    double newCount = objFabricAction.convertUnit(countUnit.getValue().toString(), "Tex", Double.parseDouble(count.getText()));
                    double newDiameter = objFabricAction.calculateDiameter(newCount,Double.parseDouble(ply.getText()),Double.parseDouble(factor.getText()));
                    diameter.setText(Double.toString(newDiameter));
                } catch (SQLException ex) {
                    new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        twistScene.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,Toggle old_toggle, Toggle new_toggle) {
              if (twistScene.getSelectedToggle() != null) {
                  char chrFabricTwistScene = twistScene.getSelectedToggle().getUserData().toString().trim().charAt(0);
                  if(chrFabricTwistScene=='S' || chrFabricTwistScene=='Z'){
                      txtTwistCount.setEditable(true);                  
                  }else{
                      txtTwistCount.setText("0");
                      txtTwistCount.setEditable(false);    
                  }
              }
            }
        });
        
        btnPreview = new Button(objDictionaryAction.getWord("PREVIEW"));
        btnApply = new Button(objDictionaryAction.getWord("APPLY"));
        
        //btnPreview.setMaxWidth(Double.MAX_VALUE);
        //btnApply.setMaxWidth(Double.MAX_VALUE);
        
        btnPreview.setGraphic(new ImageView(objConfiguration.getStrColour()+"/preview.png"));
        btnApply.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
        
        btnPreview.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPREVIEW")));
        btnApply.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPUPDATE")));
        
        btnPreview.setDisable(true);
        btnApply.setDisable(true);
        
        btnPreview.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                yarnAction("Preview");
            }
        });
        btnApply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                yarnAction("Update");
            }
        });
        
        yarnGP.add(btnPreview, 1, 14);
        yarnGP.add(btnApply, 2, 14);
        
        yarnImage = new ImageView();
        yarnImage.setFitWidth(123);
        yarnImage.setFitHeight(objConfiguration.HEIGHT/2);
        yarnGP.add(yarnImage, 4, 6, 2, 9);
        
        //////--------- colour -------//////
        Label colour = new Label(objDictionaryAction.getWord("WINDOWCOLORPALETTE"));
        colour.setGraphic(new ImageView(objConfiguration.getStrColour()+"/color_palette.png"));
        colour.setId("caption");
        GridPane.setConstraints(colour, 0, 0);
        GridPane.setColumnSpan(colour, 5);
        colourGP.getChildren().add(colour);
        
        // added Palette Name
        Label colourLbl= new Label(objDictionaryAction.getWord("PALETTE")+" :");
        colourLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPALETTE")));
        colourGP.add(colourLbl, 0, 1, 1, 1);
        colourCB = new ComboBox();
        colourCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPALETTE")));
        colourGP.add(colourCB, 1, 1, 1, 1);
        loadColourPaletteNames();
        
        colourCB.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                if(t1==null)
                    return;
                String id=(String)t1;
                objPalette.setStrPaletteID(id.substring(0, id.indexOf('-')));
                objPalette.setStrPaletteName(id.substring(id.indexOf('-')+1, id.length()));
                objPalette.setStrPaletteType("Colour");
                colourAction("Open");
            }
        });
  
        btnNewColour = new Button(objDictionaryAction.getWord("CREATE"));
        btnSaveColour = new Button(objDictionaryAction.getWord("SAVE"));
        btnSaveAsColour = new Button(objDictionaryAction.getWord("SAVEAS"));
        btnDeleteColour = new Button(objDictionaryAction.getWord("DELETE"));
       
        //btnNewColour.setMaxWidth(Double.MAX_VALUE);
        //btnSaveColour.setMaxWidth(Double.MAX_VALUE);
        //btnSaveAsColour.setMaxWidth(Double.MAX_VALUE);        
        //btnDeleteColour.setMaxWidth(Double.MAX_VALUE);
        
        btnNewColour.setGraphic(new ImageView(objConfiguration.getStrColour()+"/new.png"));
        btnSaveColour.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
        btnSaveAsColour.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save_as.png"));
        btnDeleteColour.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
       
        btnNewColour.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCREATE")));
        btnSaveColour.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVE")));
        btnSaveAsColour.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVEAS")));
        btnDeleteColour.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDELETE")));
        
        btnNewColour.setDisable(false);
        btnSaveColour.setDisable(false);
        btnSaveAsColour.setDisable(false);
        btnDeleteColour.setDisable(false);

        btnNewColour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                colourAction("Create");
            }
        });
        btnSaveColour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                colourAction("Save");
            }
        });
        btnSaveAsColour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                colourAction("SaveAs");
            }
        });
        btnDeleteColour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                colourAction("Delete");
            }
        });

        //colourGP.add(btnNewColour, 2, 1, 1, 1);
        colourGP.add(btnSaveColour, 3, 1, 1, 1);
        colourGP.add(btnSaveAsColour, 4, 1, 1, 1);
        //colourGP.add(btnDeleteColour, 5, 1, 1, 1);
        
        Separator sepHor3 = new Separator();
        sepHor3.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor3, 0, 2);
        GridPane.setColumnSpan(sepHor3, 6);
        colourGP.getChildren().add(sepHor3);
        
        GP_colour = new GridPane();
        Label warpCL = new Label(objDictionaryAction.getWord("WARP")+" "+objDictionaryAction.getWord("COLOUR")+": ");
        warpCL.setFont(Font.font("Arial", 16));
        GP_colour.add(warpCL, 0, 0);
        Label weftCL = new Label(objDictionaryAction.getWord("WEFT")+" "+objDictionaryAction.getWord("COLOUR")+": ");
        weftCL.setFont(Font.font("Arial", 16));
        GP_colour.add(weftCL, 0, 1);
        final Label lblWarp= new Label("#");
        lblWarp.setUserData(-1);
        setColourLabelPropertyEvent(lblWarp);
        GP_colour.add(lblWarp, 28, 0);
        final ColorPicker warpCP = new ColorPicker();
        warpCP.setStyle("-fx-color-label-visible: false ;");
        //weftCP.getStyleClass().add("preview");
        GP_colour.add(warpCP, 27, 0); 
        warpCP.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) { 
                Color color = warpCP.getValue();
                String rgb = String.format( "#%02X%02X%02X",(int)( color.getRed() * 255 ),(int)( color.getGreen() * 255 ),(int)( color.getBlue() * 255 ) );
                lblWarp.setStyle("-fx-background-color: "+rgb+"; -fx-font-size: 10px;");
            }
        });
        final Label lblWeft= new Label("#");
        lblWeft.setUserData(-1);
        setColourLabelPropertyEvent(lblWeft);
        GP_colour.add(lblWeft, 28, 1);        
        final ColorPicker weftCP = new ColorPicker();
        weftCP.setStyle("-fx-color-label-visible: false ;");
        //weftCP.getStyleClass().add("preview");
        GP_colour.add(weftCP, 27, 1); 
        weftCP.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) { 
                Color color = weftCP.getValue();
                String rgb = String.format( "#%02X%02X%02X",(int)( color.getRed() * 255 ),(int)( color.getGreen() * 255 ),(int)( color.getBlue() * 255 ) );
                lblWeft.setStyle("-fx-background-color: "+rgb+"; -fx-font-size: 10px;");
            }
        });
        colourGP.add(GP_colour, 0, 3, 6, 1);
        
        btnSwitchColour = new Button(objDictionaryAction.getWord("SWAPCOLOR"));
        btnWarpToWeftColour = new Button(objDictionaryAction.getWord("WARPTOWEFT"));
        btnWeftToWarpColour = new Button(objDictionaryAction.getWord("WEFTTOWARP"));
        btnClearColour = new Button(objDictionaryAction.getWord("CLEAR"));
        btnResetColour = new Button(objDictionaryAction.getWord("RESET"));
        
        //btnSwitchColour.setMaxWidth(Double.MAX_VALUE);
        //btnWarpToWeftColour.setMaxWidth(Double.MAX_VALUE);
        //btnWeftToWarpColour.setMaxWidth(Double.MAX_VALUE);
        //btnClearColour.setMaxWidth(Double.MAX_VALUE);
        //btnResetColour.setMaxWidth(Double.MAX_VALUE);
        
        btnSwitchColour.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
        btnWarpToWeftColour.setGraphic(new ImageView(objConfiguration.getStrColour()+"/move_right.png"));
        btnWeftToWarpColour.setGraphic(new ImageView(objConfiguration.getStrColour()+"/move_left.png"));
        btnClearColour.setGraphic(new ImageView(objConfiguration.getStrColour()+"/clear.png"));
        btnResetColour.setGraphic(new ImageView(objConfiguration.getStrColour()+"/clear.png"));
        
        btnSwitchColour.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSWAPCOLOR")));
        btnWarpToWeftColour.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWARPTOWEFT")));
        btnWeftToWarpColour.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWEFTTOWARP")));
        btnClearColour.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLEAR")));
        btnResetColour.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPRESET")));
        
        btnSwitchColour.setDisable(false);
        btnWarpToWeftColour.setDisable(false);
        btnWeftToWarpColour.setDisable(false);
        btnClearColour.setDisable(false);
        btnResetColour.setDisable(false);
        
        btnSwitchColour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                colourAction("Switch");
            }
        });
        btnWarpToWeftColour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                colourAction("WarpWeft");
            }
        });
        btnWeftToWarpColour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                colourAction("WeftWarp");
            }
        });
        btnClearColour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                colourAction("Clear");
            }
        });
        btnResetColour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                colourAction("Reset");
            }
        });
        
        colourGP.add(btnSwitchColour, 1, 4, 1, 1);
        colourGP.add(btnWarpToWeftColour, 2, 4, 1, 1);
        colourGP.add(btnWeftToWarpColour, 3, 4, 1, 1);
        colourGP.add(btnClearColour, 4, 4, 1, 1);
        //colourGP.add(btnResetColour, 5, 4, 1, 1);
        
        Separator sepHor4 = new Separator();
        sepHor4.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor4, 0, 5);
        GridPane.setColumnSpan(sepHor4, 6);
        colourGP.getChildren().add(sepHor4);
        
        // added
        final Colour objColour = new Colour();
        objColour.setObjConfiguration(objConfiguration);
        objColour.setStrCondition("");
        objColour.setStrSearchBy("");
        objColour.setStrOrderBy("Type");
        objColour.setStrDirection("Ascending");
        Label colorType= new Label(objDictionaryAction.getWord("COLORTYPE")+" :");
        //colorType.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColor()+"/icontooltip.png"));
        colorType.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOLORTYPE")));
        colourGP.add(colorType, 0, 6, 1, 1);
        final ComboBox colourTypeCB = new ComboBox();
        colourTypeCB.getItems().addAll(getColourType());//("Pantone"/*,"Web Color"*/);
        colourTypeCB.setPromptText("Pantone");
        colourTypeCB.setDisable(false);
        String strColorType = "Pantone";//objFabric.getStrColorType();
        colourTypeCB.setValue(strColorType);
        colourTypeCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOLORTYPE")));
        colourTypeCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objColour.setStrSearchBy(newValue);
                populateColour(objColour); 
            }    
        });
        colourGP.add(colourTypeCB, 1, 6, 1, 1);
        
        Label colourSort = new Label(objDictionaryAction.getWord("SORTBY"));
        colorType.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSORTBY")));
        colourGP.add(colourSort, 2, 6, 1, 1);
        final ComboBox  colourSortCB = new ComboBox();
        colourSortCB.getItems().addAll(/*"Name",*/"Date","Type","Hex Code","Code","Red","Green","Blue");
        colourSortCB.setPromptText(objDictionaryAction.getWord("SORTBY"));
        colourSortCB.setEditable(false); 
        //colourSortCB.setValue("Name"); 
        colourSortCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objColour.setStrOrderBy(newValue);
                populateColour(objColour); 
            }    
        });
        colourGP.add(colourSortCB, 3, 6, 1, 1);
        
        TextField txtSearch=new TextField();
        txtSearch.setPromptText("Search by Name or Code");
        colourGP.add(txtSearch, 4, 6, 2, 1);
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                objColour.setStrCondition(t1);
                populateColour(objColour);
            }
        });
        
        
        allColorSP=new ScrollPane();
        allColorSP.setPrefHeight(400);
        allColorGP=new GridPane();
        populateColour(objColour);
        allColorSP.setContent(allColorGP);
        colourGP.add(allColorSP, 0, 7, 6, 1);
        
        /*
        Label colorType= new Label(objDictionaryAction.getWord("COLORTYPE")+" :");
        //colorType.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColor()+"/icontooltip.png"));
        colorType.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOLORTYPE")));
        container.add(colorType, 0, 0);
        final ComboBox colourTypeCB = new ComboBox();
        colourTypeCB.getItems().addAll("Pantone","Web Color");
        colourTypeCB.setPromptText("Pantone");
        colourTypeCB.setDisable(true);
        String strColorType = "Pantone";//objFabric.getStrColorType();
        colourTypeCB.setValue(strColorType);
        colourTypeCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOLORTYPE")));
        colourTypeCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objColour.setStrCondition(newValue);
                populateColour(); 
            }    
        });
        container.add(colourTypeCB, 1, 0);
        
        Label colourSort = new Label(objDictionaryAction.getWord("SORTBY"));
        colorType.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSORTBY")));
        container.add(colourSort,2,0);
        final ComboBox  colourSortCB = new ComboBox();
        colourSortCB.getItems().addAll("Name","Date","Type","Hex Code","Pantone Code","Red","Green","Blue");
        colourSortCB.setPromptText(objDictionaryAction.getWord("SORTBY"));
        colourSortCB.setEditable(false); 
        //colourSortCB.setValue("Name"); 
        colourSortCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objColour.setStrOrderBy(newValue);
                populateColour(); 
            }    
        });
        container.add(colourSortCB,3,0);
        
        TextField txtSearch=new TextField();
        txtSearch.setPromptText("Search by Name/Hex Code/Pantone Code");
        container.add(txtSearch, 4, 0);
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                objColour.setStrCondition(t1);
                populateColour();
            }
        });
        
        pLbl=new Label[52];
        for(int i=0; i<52; i++){
            pLbl[i]=new Label();
            addDragNDrop(pLbl[i]);
        }
        
        colorSP=new ScrollPane();
        colorGP = new GridPane();
        colorSP.setPrefHeight(400);
        populateColour();
        colorSP.setContent(colorGP);
        container.add(colorSP, 0, 1, 5, 1);
        */
        
        //-------------------------------------------//
        GridPane bodyContainer = new GridPane();
        //bodyContainer.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        bodyContainer.setId("container");
        
        Label caption = new Label(/*objDictionaryAction.getWord("YARN")+"-"+*/objDictionaryAction.getWord("COLOUR")+" "+objDictionaryAction.getWord("PALETTE")+" "+objDictionaryAction.getWord("UTILITY"));
        caption.setId("caption");
        bodyContainer.add(caption, 0, 0, 1, 1);
        
        bodyContainer.add(tabPane, 0, 1, 1, 1);
        /*
        final Separator sepHor = new Separator();
        sepHor.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor, 0, 2);
        GridPane.setColumnSpan(sepHor, 2);
        bodyContainer.getChildren().add(sepHor);
        */
        root.setCenter(bodyContainer);
        
        paletteStage.getIcons().add(new Image("/media/icon.png"));
        paletteStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWFABRICSETTINGS")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        //paletteStage.setIconified(true);
        paletteStage.setResizable(false);
        paletteStage.setScene(scene);
        paletteStage.setX(0);
        paletteStage.setY(0);
        paletteStage.show();
        paletteStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
                scene.getStylesheets().add(PaletteView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        paletteStage.close();
                        System.gc();
                        UtilityView objUtilityView = new UtilityView(objConfiguration);
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
    }
    
    private ArrayList<String> getColourType(){
        ArrayList<String> types=new ArrayList<>();
        try{
            types=new ColourAction().getColourType();
        }
        catch(SQLException sqlEx){
            new Logging("SEVERE",PaletteView.class.getName(),"getColourType() : ", sqlEx);
        }
        return types;
    }
	
    public void populateColour(Colour objColour){
        try {
            allColorGP.getChildren().clear();
            System.gc();
            List lstColor=null;
            FabricAction objFabricAction = new FabricAction();
            List lstColorDeatails = new ArrayList();
            lstColorDeatails = objFabricAction.lstImportColor(objColour);
            if(lstColorDeatails.size()==0){
                allColorGP.add(new Text(objDictionaryAction.getWord("NOVALUE")), 0, 0);
            }else{            
                for (int i=0, k=0; i<lstColorDeatails.size();i++, k+=2){
                    lstColor = (ArrayList)lstColorDeatails.get(i);

                    final Label cLbl = new Label(""+(i+1));
                    cLbl.setPrefSize(25,25);
                    cLbl.setStyle("-fx-background-color:rgb("+lstColor.get(3).toString()+","+lstColor.get(4).toString()+","+lstColor.get(5).toString()+");");
                    cLbl.setUserData(lstColor.get(0).toString());
                    cLbl.setId(lstColor.get(6).toString());
                    String strTooltip = 
                            objDictionaryAction.getWord("NAME")+": "+lstColor.get(1).toString()+"\n"+
                            objDictionaryAction.getWord("TYPE")+": "+lstColor.get(2).toString()+"\n"+
                            objDictionaryAction.getWord("RGB")+": "+lstColor.get(3).toString()+", "+lstColor.get(4).toString()+", "+lstColor.get(5).toString()+"\n"+
                            objDictionaryAction.getWord("HEX")+": "+lstColor.get(6).toString()+"\n"+
                            objDictionaryAction.getWord("CODE")+": "+lstColor.get(7).toString()+"\n"+
                            objDictionaryAction.getWord("BY")+": "+lstColor.get(9).toString()+"\n"+
                            objDictionaryAction.getWord("DATE")+": "+lstColor.get(10).toString();
                    Tooltip tpLbl = new Tooltip(strTooltip);
                    
                    cLbl.setTooltip(tpLbl);
                    cLbl.setOnDragDetected(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent t) {
                            Dragboard dragboard=cLbl.startDragAndDrop(TransferMode.COPY);
                            ClipboardContent content=new ClipboardContent();
                            content.putString(cLbl.getId());
                            dragboard.setContent(content);
                            t.consume();
                        }
                    });
                    allColorGP.add(cLbl, i%30, i/30);
                }
            }
        } catch (SQLException ex) {   
            Logger.getLogger(PaletteView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PaletteView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    
    public void loadColourPaletteNames(){
        colourCB.getItems().clear();
        try{
            objUtilityAction=new UtilityAction();
            objPalette.setStrPaletteType("Colour");
            String[][] paletteNames=objUtilityAction.getPaletteName(objPalette);
            if(paletteNames!=null){
                for(String[] s:paletteNames){
                    colourCB.getItems().add(s[0]+"-"+s[1]);
                }
                colourCB.setValue("Select Palette");
            }
        } catch(SQLException sqlEx){
            new Logging("SEVERE",PaletteView.class.getName(),"loadColourPaletteName() : ", sqlEx);
        }
    }
    
    public void loadYarnPaletteNames(){
        yarnCB.getItems().clear();
        try{
            objUtilityAction=new UtilityAction();
            objPalette.setStrPaletteType("Yarn");
            String[][] paletteNames=objUtilityAction.getPaletteName(objPalette);
            if(paletteNames!=null){
                for(String[] s:paletteNames){
                    yarnCB.getItems().add(s[0]+"-"+s[1]);
                }
            }
        } catch(SQLException ex){
            new Logging("SEVERE",PaletteView.class.getName(),"loadYarnPaletteNames() : ", ex);
        }
    }
    
    public void yarnAction(String actionName) {        
        if (actionName.equalsIgnoreCase("Open")) {
            try {
                objUtilityAction = new UtilityAction();
                objConfiguration.initYarnPalette();
                objUtilityAction.getPalette(objPalette);
                setYarnPalette();
                loadYarnPalette();
            } catch (Exception ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        if (actionName.equalsIgnoreCase("Switch")) {
            try {
                Yarn temp;String type, symbol; 
                for(int i =0; i<26; i++){
                    type = objConfiguration.getYarnPalette()[i].getStrYarnType();
                    symbol = objConfiguration.getYarnPalette()[i].getStrYarnSymbol();
                    
                    objConfiguration.getYarnPalette()[i].setStrYarnType(objConfiguration.getYarnPalette()[i+26].getStrYarnType());
                    objConfiguration.getYarnPalette()[i].setStrYarnSymbol(objConfiguration.getYarnPalette()[i+26].getStrYarnSymbol());
                    
                    objConfiguration.getYarnPalette()[i+26].setStrYarnType(type);
                    objConfiguration.getYarnPalette()[i+26].setStrYarnSymbol(symbol);
                    
                    temp = objConfiguration.getYarnPalette()[i];
                    objConfiguration.getYarnPalette()[i]=objConfiguration.getYarnPalette()[i+26];
                    objConfiguration.getYarnPalette()[i+26]=temp;
                }
                loadYarnPalette();
            } catch (Exception ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        if (actionName.equalsIgnoreCase("WarpWeft")) {
            try {
                String type, symbol;
                for(int i =0; i<26; i++){
                    Yarn objYarn = objConfiguration.getYarnPalette()[i+26];

                    //objYarn.setStrYarnType(objConfiguration.getYarnPalette()[i].getStrYarnType());
                    objYarn.setStrYarnName(objConfiguration.getYarnPalette()[i+26].getStrYarnName());
                    //objYarn.setStrYarnColor(objConfiguration.getYarnPalette()[i].getStrYarnColor());
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
                loadYarnPalette();
            } catch (Exception ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        if (actionName.equalsIgnoreCase("WeftWarp")) {
            try {
                String type, symbol;
                for(int i =0; i<26; i++){
                    Yarn objYarn = objConfiguration.getYarnPalette()[i];

                    //objYarn.setStrYarnType(objConfiguration.getYarnPalette()[i+26].getStrYarnType());
                    objYarn.setStrYarnName(objConfiguration.getYarnPalette()[i].getStrYarnName());
                    //objYarn.setStrYarnColor(objConfiguration.getYarnPalette()[i+26].getStrYarnColor());
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
                loadYarnPalette();
            } catch (Exception ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        if (actionName.equalsIgnoreCase("Clear")) {
            try {
                objConfiguration.initYarnPalette();
                loadYarnPalette();
            } catch (Exception ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        if (actionName.equalsIgnoreCase("Save")) {
            try {
                getYarnPalette();
                objUtilityAction = new UtilityAction();
                objUtilityAction.resetPalette(objPalette);
            } catch (Exception ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        if (actionName.equalsIgnoreCase("SaveAs")) {
            try {
                final Stage dialogStage = new Stage();
                dialogStage.initStyle(StageStyle.UTILITY);
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                GridPane popup=new GridPane();
                popup.setId("popup");
                popup.setHgap(5);
                popup.setVgap(5);

                final TextField txtName = new TextField();
                Label lblName = new Label(objDictionaryAction.getWord("NAME"));
                lblName.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNAME")));
                popup.add(lblName, 0, 0);
                txtName.setPromptText(objDictionaryAction.getWord("TOOLTIPNAME"));
                txtName.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNAME")));
                popup.add(txtName, 1, 0, 2, 1);
                
                final ToggleGroup paletteTG = new ToggleGroup();
                RadioButton palettePublicRB = new RadioButton(objDictionaryAction.getWord("PUBLIC"));
                palettePublicRB.setToggleGroup(paletteTG);
                palettePublicRB.setUserData("Public");
                popup.add(palettePublicRB, 0, 1);
                RadioButton palettePrivateRB = new RadioButton(objDictionaryAction.getWord("PRIVATE"));
                palettePrivateRB.setToggleGroup(paletteTG);
                palettePrivateRB.setUserData("Private");
                popup.add(palettePrivateRB, 1, 1);
                RadioButton paletteProtectedRB = new RadioButton(objDictionaryAction.getWord("PROTECTED"));
                paletteProtectedRB.setToggleGroup(paletteTG);
                paletteProtectedRB.setUserData("Protected");
                popup.add(paletteProtectedRB, 2, 1);
                if(objConfiguration.getObjUser().getUserAccess("PALETTE_LIBRARY").equalsIgnoreCase("Public"))
                    paletteTG.selectToggle(palettePublicRB);
                else if(objConfiguration.getObjUser().getUserAccess("PALETTE_LIBRARY").equalsIgnoreCase("Protected"))
                    paletteTG.selectToggle(paletteProtectedRB);
                else
                    paletteTG.selectToggle(palettePrivateRB);

                Button btnOK = new Button(objDictionaryAction.getWord("SAVE"));
                btnOK.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVE")));
                btnOK.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
                btnOK.setDefaultButton(true);
                btnOK.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) { 
                        objPalette.setStrPaletteID(new IDGenerator().getIDGenerator("PALETTE_LIBRARY", objConfiguration.getObjUser().getStrUserID()));
                        objPalette.setStrPaletteAccess(paletteTG.getSelectedToggle().getUserData().toString());
                        if(txtName.getText()!=null && txtName.getText()!="" && txtName.getText().trim().length()!=0)
                                objPalette.setStrPaletteName(txtName.getText().toString());
                        else
                            objPalette.setStrPaletteName("My Palette");                        
                        objPalette.setStrPaletteType("Yarn");
                        try{
                            getYarnPalette();
                            objUtilityAction = new UtilityAction();
                            objUtilityAction.setPalette(objPalette);
                        } catch(SQLException sqlEx){
                            Logger.getLogger(PaletteView.class.getName()).log(Level.SEVERE, null, sqlEx);
                        }
                        dialogStage.close();
                        loadYarnPaletteNames();                 
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
                scene.getStylesheets().add(PaletteView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                dialogStage.setScene(scene);
                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("SAVE"));
                dialogStage.showAndWait();
            } catch (Exception ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        if (actionName.equalsIgnoreCase("Create")) {
            try {
                objConfiguration.initYarnPalette();
                loadYarnPalette();
                btnSaveYarn.setDisable(true);
            } catch (Exception ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        if (actionName.equalsIgnoreCase("Reset")) {
            try {
                if(btnSaveYarn.isDisable())
                    yarnAction("Create");
                else
                    yarnAction("Open");
            } catch (Exception ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        if (actionName.equalsIgnoreCase("Preview")) {
            try {
                Yarn objYarn = new Yarn(null, type.getText(), name.getValue().toString(), toRGBCode((Color)color.getValue()), Integer.parseInt(repeat.getText()), symbol.getText(), Integer.parseInt(count.getText()), countUnit.getValue().toString(), Integer.parseInt(ply.getText()), Integer.parseInt(factor.getText()), Double.parseDouble(diameter.getText()), Integer.parseInt(txtTwistCount.getText()), twistScene.getSelectedToggle().getUserData().toString(), Integer.parseInt(txtHairLength.getText()), Integer.parseInt(txtHairPercentage.getText()), Double.parseDouble(price.getText()), objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"),objConfiguration.getObjUser().getStrUserID(),null);
                objYarn.setObjConfiguration(objConfiguration);
                YarnAction objYarnAction = new YarnAction();
                yarnImage.setImage(SwingFXUtils.toFXImage(objYarnAction.getYarnImage(objYarn), null));                    
            } catch (SQLException ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            } catch (IOException ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        if (actionName.equalsIgnoreCase("Update")) {
            try {
                if(yarnIndex>=0){
                    Yarn objYarn = objConfiguration.getYarnPalette()[yarnIndex];

                    objYarn.setStrYarnType(type.getText());
                    objYarn.setStrYarnName(name.getValue().toString());
                    objYarn.setStrYarnColor(toRGBCode((Color)color.getValue()));
                    objYarn.setIntYarnRepeat(Integer.parseInt(repeat.getText()));
                    objYarn.setStrYarnSymbol(symbol.getText());
                    objYarn.setIntYarnCount(Integer.parseInt(count.getText()));
                    objYarn.setStrYarnCountUnit(countUnit.getValue().toString());
                    objYarn.setIntYarnPly(Integer.parseInt(ply.getText()));
                    objYarn.setIntYarnDFactor(Integer.parseInt(factor.getText()));
                    objYarn.setDblYarnDiameter(Double.parseDouble(diameter.getText()));
                    objYarn.setIntYarnTwist(Integer.parseInt(txtTwistCount.getText()));
                    objYarn.setStrYarnTModel(twistScene.getSelectedToggle().getUserData().toString());
                    objYarn.setIntYarnHairness(Integer.parseInt(txtHairLength.getText()));
                    objYarn.setIntYarnHProbability(Integer.parseInt(txtHairPercentage.getText()));
                    objYarn.setDblYarnPrice(Double.parseDouble(price.getText()));
                    //objYarn.setStrYarnAccess(objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"));
                    //objYarn.setStrYarnUser(objConfiguration.getObjUser().getStrUserID());
                    //objYarn.setStrYarnDate(null);
                    //objYarn.setObjConfiguration(objConfiguration);

                    objConfiguration.getYarnPalette()[yarnIndex] = objYarn;
                    loadYarnPalette();
                }
            } catch (Exception ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
    }
    
    public void colourAction(String actionName) {        
        if (actionName.equalsIgnoreCase("Open")) {
            try {
                objUtilityAction=new UtilityAction();
                objConfiguration.initColourPalette();
                objUtilityAction.getPalette(objPalette);
                setColourPalette(); // it sets colorPalates[][] using threadPalettes
                loadColourPalette(); // it adds pLbl to container with new values
            } catch (Exception ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        if (actionName.equalsIgnoreCase("Switch")) {
            try {
                String temp; 
                for(int i =0; i<26; i++){
                    temp = threadPalette[i][1];
                    threadPalette[i][1]=threadPalette[i+26][1];
                    threadPalette[i+26][1]=temp;
                }
                loadColourPalette();
            } catch (Exception ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        if (actionName.equalsIgnoreCase("WarpWeft")) {
            try {
                for(int i =0; i<26; i++){
                    threadPalette[i+26][1]=new String(threadPalette[i][1]);
                }
                loadColourPalette();
            } catch (Exception ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        if (actionName.equalsIgnoreCase("WeftWarp")) {
            try {
                for(int i =0; i<26; i++){
                    threadPalette[i][1]=new String(threadPalette[i+26][1]);
                }
                loadColourPalette();
            } catch (Exception ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        if (actionName.equalsIgnoreCase("Clear")) {
            try {
                objConfiguration.initColourPalette();
                for(int i=0; i<52; i++){
                    objConfiguration.getColourPalette()[i]="ffffff";
                    threadPalette[i][1]=objConfiguration.getColourPalette()[i];                    
                }
                loadColourPalette();
            } catch (Exception ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        if (actionName.equalsIgnoreCase("Save")) {
            try {
                getColourPalette();
                objUtilityAction = new UtilityAction();
                objUtilityAction.resetPalette(objPalette);
                lblStatus.setText(objDictionaryAction.getWord("DATASAVED"));
            } catch (Exception ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        if (actionName.equalsIgnoreCase("SaveAs")) {
            try {
                final Stage dialogStage = new Stage();
                dialogStage.initStyle(StageStyle.UTILITY);
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                GridPane popup=new GridPane();
                popup.setId("popup");
                popup.setHgap(5);
                popup.setVgap(5);

                final TextField txtName = new TextField();
                Label lblName = new Label(objDictionaryAction.getWord("NAME"));
                lblName.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNAME")));
                popup.add(lblName, 0, 0);
                txtName.setPromptText(objDictionaryAction.getWord("TOOLTIPNAME"));
                txtName.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNAME")));
                popup.add(txtName, 1, 0, 2, 1);
                
                final ToggleGroup paletteTG = new ToggleGroup();
                RadioButton palettePublicRB = new RadioButton(objDictionaryAction.getWord("PUBLIC"));
                palettePublicRB.setToggleGroup(paletteTG);
                palettePublicRB.setUserData("Public");
                popup.add(palettePublicRB, 0, 1);
                RadioButton palettePrivateRB = new RadioButton(objDictionaryAction.getWord("PRIVATE"));
                palettePrivateRB.setToggleGroup(paletteTG);
                palettePrivateRB.setUserData("Private");
                popup.add(palettePrivateRB, 1, 1);
                RadioButton paletteProtectedRB = new RadioButton(objDictionaryAction.getWord("PROTECTED"));
                paletteProtectedRB.setToggleGroup(paletteTG);
                paletteProtectedRB.setUserData("Protected");
                popup.add(paletteProtectedRB, 2, 1);
                if(objConfiguration.getObjUser().getUserAccess("PALETTE_LIBRARY").equalsIgnoreCase("Public"))
                    paletteTG.selectToggle(palettePublicRB);
                else if(objConfiguration.getObjUser().getUserAccess("PALETTE_LIBRARY").equalsIgnoreCase("Protected"))
                    paletteTG.selectToggle(paletteProtectedRB);
                else
                    paletteTG.selectToggle(palettePrivateRB);

                Button btnOK = new Button(objDictionaryAction.getWord("SAVE"));
                btnOK.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVE")));
                btnOK.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
                btnOK.setDefaultButton(true);
                btnOK.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) { 
                        objPalette.setStrPaletteID(new IDGenerator().getIDGenerator("PALETTE_LIBRARY", objConfiguration.getObjUser().getStrUserID()));
                        objPalette.setStrPaletteAccess(paletteTG.getSelectedToggle().getUserData().toString());
                        if(txtName.getText()!=null && txtName.getText()!="" && txtName.getText().trim().length()!=0)
                                objPalette.setStrPaletteName(txtName.getText().toString());
                        else
                            objPalette.setStrPaletteName("My Palette");                        
                        objPalette.setStrPaletteType("Colour");
                        try{
                            getColourPalette();
                            objUtilityAction = new UtilityAction();
                            objUtilityAction.setPalette(objPalette);
                        } catch(SQLException sqlEx){
                            new Logging("SEVERE",PaletteView.class.getName(),sqlEx.toString(),sqlEx);
                        }
                        dialogStage.close();
                        loadColourPaletteNames(); 
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
                scene.getStylesheets().add(PaletteView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                dialogStage.setScene(scene);
                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+": "+objDictionaryAction.getWord("SAVE"));
                dialogStage.showAndWait();
            } catch (Exception ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        if (actionName.equalsIgnoreCase("Create")) {
            try {
                objConfiguration.initColourPalette();
                loadColourPalette();
                btnSaveColour.setDisable(true);
            } catch (Exception ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
        if (actionName.equalsIgnoreCase("Reset")) {
            try {
                if(btnSaveColour.isDisable())
                    colourAction("Create");
                else
                    colourAction("Open");
            } catch (Exception ex) {
                new Logging("SEVERE",PaletteView.class.getName(),ex.toString(),ex);
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
        }
    }    
    
    private void loadYarnPalette(){
        for(byte i=0; i<(byte)objConfiguration.getYarnPalette().length; i++){
            System.out.println(i+":"+objConfiguration.getYarnPalette()[i].getStrYarnSymbol());
            Label lblC= new Label(objConfiguration.getYarnPalette()[i].getStrYarnSymbol());
            lblC.setUserData(i);
            setYarnLabelPropertyEvent(lblC, objConfiguration.getYarnPalette()[i]);
            GP_yarn.add(lblC, (i%26)+1, (i/26));
        }
    }
    public void setYarnLabelPropertyEvent(final Label lblC, final Yarn objYarn){
        lblC.setText(objYarn.getStrYarnSymbol());
        lblC.setStyle("-fx-background-color: "+objYarn.getStrYarnColor()+"; -fx-font-size: 16; -fx-width:25px; -fx-height:30px; -fx-border-width: 1 1 1 1; -fx-border-color: #000000; ");
        lblC.setPrefSize(30, 30);
        
        lblC.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                color.setValue(Color.web(objYarn.getStrYarnColor()));
                twistScene.equals(objYarn.getStrYarnTModel());
                countUnit.setValue(objYarn.getStrYarnCountUnit());
                type.setText(objYarn.getStrYarnType());
                name.setValue(objYarn.getStrYarnName());
                repeat.setText(Integer.toString(objYarn.getIntYarnRepeat()));
                symbol.setText(objYarn.getStrYarnSymbol());
                diameter.setText(Double.toString(objYarn.getDblYarnDiameter()));
                count.setText(Integer.toString(objYarn.getIntYarnCount()));
                ply.setText(Integer.toString(objYarn.getIntYarnPly()));
                factor.setText(Integer.toString(objYarn.getIntYarnDFactor()));
                txtTwistCount.setText(Integer.toString(objYarn.getIntYarnTwist()));
                txtHairLength.setText(Integer.toString(objYarn.getIntYarnHairness()));
                txtHairPercentage.setText(Integer.toString(objYarn.getIntYarnHProbability()));
                price.setText(Double.toString(objYarn.getDblYarnPrice()));
                yarnIndex = Integer.parseInt(lblC.getUserData().toString());
                System.err.println(symbol.getText()+"*"+objYarn.getStrYarnSymbol());
    
                objYarn.setObjConfiguration(objConfiguration);
                
                btnPreview.setDisable(false);
                btnApply.setDisable(false);
                
                YarnAction objYarnAction;
                try {
                    objYarnAction = new YarnAction();
                    yarnImage.setImage(SwingFXUtils.toFXImage(objYarnAction.getYarnImage(objYarn), null)); 
                } catch (SQLException ex) {
                    Logger.getLogger(PaletteView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PaletteView.class.getName()).log(Level.SEVERE, null, ex);
                }
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
                xindex = (byte)Integer.parseInt(lblC.getUserData().toString());
                event.consume();
            }
        });
        lblC.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                /* accept it only if it is not dragged from the same node 
                 * and if it has a string data */
                if (event.getGestureSource() != lblC && event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
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
                     lblC.setTextFill(Color.GREEN);
                 }
                 event.consume();
            }
        });
        lblC.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
                lblC.setTextFill(Color.BLACK);
                event.consume();
            }
        });
        lblC.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {                    
                   ClipboardContent content = new ClipboardContent();
                   content.putString(lblC.getStyle().substring(lblC.getStyle().lastIndexOf("-fx-background-color:")+21,lblC.getStyle().indexOf(";")).trim());                
                   lblC.setStyle("-fx-background-color: "+db.getString()+"; -fx-font-size: 10; -fx-width:25px; -fx-height:25px;");
                   yindex = (byte)Integer.parseInt(lblC.getUserData().toString());
                   
                   db.setContent(content);                   
                   success = true;                   
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);
                event.consume();
             }
        });
        lblC.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag and drop gesture ended */
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    Dragboard db = event.getDragboard();
                    // swapThreadPatterns(lblC.getStyle().substring(lblC.getStyle().lastIndexOf("-fx-background-color:")+21,lblC.getStyle().indexOf(";")).trim(),db.getString());              
                    lblC.setStyle("-fx-background-color: "+db.getString()+"; -fx-font-size: 10; -fx-width:25px; -fx-height:25px;");
                    
                    Yarn temp;String type, symbol; 
                    System.out.println(xindex+"="+yindex);
                    if(xindex>-1 && yindex>-1){
                            type = objConfiguration.getYarnPalette()[xindex].getStrYarnType();
                            symbol = objConfiguration.getYarnPalette()[xindex].getStrYarnSymbol();

                            objConfiguration.getYarnPalette()[xindex].setStrYarnType(objConfiguration.getYarnPalette()[yindex].getStrYarnType());
                            objConfiguration.getYarnPalette()[xindex].setStrYarnSymbol(objConfiguration.getYarnPalette()[yindex].getStrYarnSymbol());

                            objConfiguration.getYarnPalette()[yindex].setStrYarnType(type);
                            objConfiguration.getYarnPalette()[yindex].setStrYarnSymbol(symbol);

                            temp = objConfiguration.getYarnPalette()[xindex];
                            objConfiguration.getYarnPalette()[xindex]=objConfiguration.getYarnPalette()[yindex];
                            objConfiguration.getYarnPalette()[yindex]=temp;
                    }
                    loadYarnPalette();
                    
                }
                event.consume();
            }
        });
    }
    
    public void setColourPalette(){
        for(int i=0; i<52; i++){
            objConfiguration.getColourPalette()[i]=objPalette.getStrThreadPalette()[i];
        }
        threadPalette[0][0]="A";
        threadPalette[0][1]=objConfiguration.getColourPalette()[0];
        threadPalette[1][0]="B";
        threadPalette[1][1]=objConfiguration.getColourPalette()[1];
        threadPalette[2][0]="C";
        threadPalette[2][1]=objConfiguration.getColourPalette()[2];
        threadPalette[3][0]="D";
        threadPalette[3][1]=objConfiguration.getColourPalette()[3];
        threadPalette[4][0]="E";
        threadPalette[4][1]=objConfiguration.getColourPalette()[4];
        threadPalette[5][0]="F";
        threadPalette[5][1]=objConfiguration.getColourPalette()[5];
        threadPalette[6][0]="G";
        threadPalette[6][1]=objConfiguration.getColourPalette()[6];
        threadPalette[7][0]="H";
        threadPalette[7][1]=objConfiguration.getColourPalette()[7];
        threadPalette[8][0]="I";
        threadPalette[8][1]=objConfiguration.getColourPalette()[8];
        threadPalette[9][0]="J";
        threadPalette[9][1]=objConfiguration.getColourPalette()[9];
        threadPalette[10][0]="K";
        threadPalette[10][1]=objConfiguration.getColourPalette()[10];
        threadPalette[11][0]="L";
        threadPalette[11][1]=objConfiguration.getColourPalette()[11];
        threadPalette[12][0]="M";
        threadPalette[12][1]=objConfiguration.getColourPalette()[12];
        threadPalette[13][0]="N";
        threadPalette[13][1]=objConfiguration.getColourPalette()[13];
        threadPalette[14][0]="O";
        threadPalette[14][1]=objConfiguration.getColourPalette()[14];
        threadPalette[15][0]="P";
        threadPalette[15][1]=objConfiguration.getColourPalette()[15];
        threadPalette[16][0]="Q";
        threadPalette[16][1]=objConfiguration.getColourPalette()[16];
        threadPalette[17][0]="R";
        threadPalette[17][1]=objConfiguration.getColourPalette()[17];
        threadPalette[18][0]="S";
        threadPalette[18][1]=objConfiguration.getColourPalette()[18];
        threadPalette[19][0]="T";
        threadPalette[19][1]=objConfiguration.getColourPalette()[19];
        threadPalette[20][0]="U";
        threadPalette[20][1]=objConfiguration.getColourPalette()[20];
        threadPalette[21][0]="V";
        threadPalette[21][1]=objConfiguration.getColourPalette()[21];
        threadPalette[22][0]="W";
        threadPalette[22][1]=objConfiguration.getColourPalette()[22];
        threadPalette[23][0]="X";
        threadPalette[23][1]=objConfiguration.getColourPalette()[23];
        threadPalette[24][0]="Y";
        threadPalette[24][1]=objConfiguration.getColourPalette()[24];
        threadPalette[25][0]="Z";
        threadPalette[25][1]=objConfiguration.getColourPalette()[25];
        threadPalette[26][0]="a";
        threadPalette[26][1]=objConfiguration.getColourPalette()[26];
        threadPalette[27][0]="b";
        threadPalette[27][1]=objConfiguration.getColourPalette()[27];
        threadPalette[28][0]="c";
        threadPalette[28][1]=objConfiguration.getColourPalette()[28];
        threadPalette[29][0]="d";
        threadPalette[29][1]=objConfiguration.getColourPalette()[29];
        threadPalette[30][0]="e";
        threadPalette[30][1]=objConfiguration.getColourPalette()[30];
        threadPalette[31][0]="f";
        threadPalette[31][1]=objConfiguration.getColourPalette()[31];
        threadPalette[32][0]="g";
        threadPalette[32][1]=objConfiguration.getColourPalette()[32];
        threadPalette[33][0]="h";
        threadPalette[33][1]=objConfiguration.getColourPalette()[33];
        threadPalette[34][0]="i";
        threadPalette[34][1]=objConfiguration.getColourPalette()[34];
        threadPalette[35][0]="j";
        threadPalette[35][1]=objConfiguration.getColourPalette()[35];
        threadPalette[36][0]="k";
        threadPalette[36][1]=objConfiguration.getColourPalette()[36];
        threadPalette[37][0]="l";
        threadPalette[37][1]=objConfiguration.getColourPalette()[37];
        threadPalette[38][0]="m";
        threadPalette[38][1]=objConfiguration.getColourPalette()[38];
        threadPalette[39][0]="n";
        threadPalette[39][1]=objConfiguration.getColourPalette()[39];
        threadPalette[40][0]="o";
        threadPalette[40][1]=objConfiguration.getColourPalette()[40];
        threadPalette[41][0]="p";
        threadPalette[41][1]=objConfiguration.getColourPalette()[41];
        threadPalette[42][0]="q";
        threadPalette[42][1]=objConfiguration.getColourPalette()[42];
        threadPalette[43][0]="r";
        threadPalette[43][1]=objConfiguration.getColourPalette()[43];
        threadPalette[44][0]="s";
        threadPalette[44][1]=objConfiguration.getColourPalette()[44];
        threadPalette[45][0]="t";
        threadPalette[45][1]=objConfiguration.getColourPalette()[45];
        threadPalette[46][0]="u";
        threadPalette[46][1]=objConfiguration.getColourPalette()[46];
        threadPalette[47][0]="v";
        threadPalette[47][1]=objConfiguration.getColourPalette()[47];
        threadPalette[48][0]="w";
        threadPalette[48][1]=objConfiguration.getColourPalette()[48];
        threadPalette[49][0]="x";
        threadPalette[49][1]=objConfiguration.getColourPalette()[49];
        threadPalette[50][0]="y";
        threadPalette[50][1]=objConfiguration.getColourPalette()[50];
        threadPalette[51][0]="z";
        threadPalette[51][1]=objConfiguration.getColourPalette()[51];
    }
    
    private void setYarnPalette() throws SQLException{
        for(int i=0; i<52; i++){
            objConfiguration.getYarnPalette()[i].setStrYarnId(objPalette.getStrThreadPalette()[i]);
            YarnAction objYarnAction = new YarnAction();
            objYarnAction.getYarn(objConfiguration.getYarnPalette()[i]);
        }
    }
    
    private void getColourPalette(){
        for(int i=0; i<52; i++){
            objPalette.getStrThreadPalette()[i]=objConfiguration.getColourPalette()[i];
        }
    }
    
    private void getYarnPalette() throws SQLException{
        for(int i=0; i<52; i++){
            if(objConfiguration.getYarnPalette()[i].getStrYarnId()==null)
                objConfiguration.getYarnPalette()[i].setStrYarnId(new IDGenerator().getIDGenerator("YARN_LIBRARY", objConfiguration.getObjUser().getStrUserID()));
            YarnAction objYarnAction = new YarnAction();
            objYarnAction.setYarn(objConfiguration.getYarnPalette()[i]);
            objPalette.getStrThreadPalette()[i]=objConfiguration.getYarnPalette()[i].getStrYarnId();
        }
    }
    
    private void loadColourPalette(){
        for(byte i=0; i<(byte)objConfiguration.getColourPalette().length; i++){
            //System.out.println(i+":"+threadPalette[i][1]);
            Label lblC= new Label(threadPalette[i][0]);
            lblC.setUserData(i);
            lblC.setStyle("-fx-background-color: #"+threadPalette[i][1]+"; -fx-font-size: 10; -fx-width:25px; -fx-height:25px;");
            setColourLabelPropertyEvent(lblC);
            GP_colour.add(lblC, (i%26)+1, (i/26));
        }
    }
    public void setColourLabelPropertyEvent(final Label lblC){
        lblC.setPrefSize(25, 25);
        lblC.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                Dragboard db = lblC.startDragAndDrop(TransferMode.ANY);

                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(lblC.getStyle().substring(lblC.getStyle().lastIndexOf("-fx-background-color:")+21,lblC.getStyle().indexOf(";")).trim());
                db.setContent(content);
                xindex = (byte)Integer.parseInt(lblC.getUserData().toString());
                event.consume();
            }
        });
        lblC.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                /* accept it only if it is not dragged from the same node 
                 * and if it has a string data */
                if (event.getGestureSource() != lblC && event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
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
                     lblC.setTextFill(Color.GREEN);
                 }
                 event.consume();
            }
        });
        lblC.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
                lblC.setTextFill(Color.BLACK);
                event.consume();
            }
        });
        lblC.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {                    
                   ClipboardContent content = new ClipboardContent();
                   content.putString(lblC.getStyle().substring(lblC.getStyle().lastIndexOf("-fx-background-color:")+21,lblC.getStyle().indexOf(";")).trim());                
                   lblC.setStyle("-fx-background-color: "+db.getString()+"; -fx-font-size: 10; -fx-width:25px; -fx-height:25px;");
                   yindex = (byte)Integer.parseInt(lblC.getUserData().toString());
                   if(event.getTransferMode()==TransferMode.COPY&&yindex>-1){
                       threadPalette[yindex][1]=objConfiguration.getColourPalette()[yindex]=db.getString().substring(db.getString().lastIndexOf("#")+1);
                   }
                   if(xindex==-1 && yindex>-1)
                       objConfiguration.getColourPalette()[yindex]=db.getString().substring(db.getString().lastIndexOf("#")+1);
                   else if(xindex>-1 && yindex==-1)
                       objConfiguration.getColourPalette()[xindex]=content.getString().substring(content.getString().lastIndexOf("#")+1);
                   db.setContent(content);
                   success = true;                   
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);
                event.consume();
             }
        });
        lblC.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag and drop gesture ended */
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    Dragboard db = event.getDragboard();
                    // swapThreadPatterns(lblC.getStyle().substring(lblC.getStyle().lastIndexOf("-fx-background-color:")+21,lblC.getStyle().indexOf(";")).trim(),db.getString());              
                    lblC.setStyle("-fx-background-color: "+db.getString()+"; -fx-font-size: 10; -fx-width:25px; -fx-height:25px;");
                    
                    String temp =null;
                    if(xindex>-1 && yindex>-1){
                        temp = objConfiguration.getColourPalette()[xindex];
                        objConfiguration.getColourPalette()[xindex] = objConfiguration.getColourPalette()[yindex];
                        objConfiguration.getColourPalette()[yindex] = temp;
                    }
                }
                for(int i=0; i<objConfiguration.getColourPalette().length;i++){
                    threadPalette[i][1]=objConfiguration.getColourPalette()[i];
                }
                event.consume();
            }
        });
    }
    
    public String toRGBCode( Color color ){
        return String.format( "#%02X%02X%02X",
            (int)( color.getRed() * 255 ),
            (int)( color.getGreen() * 255 ),
            (int)( color.getBlue() * 255 ) );
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        new PaletteView(stage);
        new Logging("WARNING",PaletteView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static void main(String[] args) {   
        launch(args);    
    }
}