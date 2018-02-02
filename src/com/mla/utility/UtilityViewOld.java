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
package com.mla.utility;

import com.mla.artwork.Artwork;
import com.mla.artwork.ArtworkAction;
import com.mla.artwork.ArtworkView;
import com.mla.colour.Colour;
import com.mla.dictionary.DictionaryAction;
import com.mla.fabric.Fabric;
import com.mla.fabric.FabricAction;
import com.mla.fabric.FabricView;
import com.mla.yarn.Yarn;
import com.mla.yarn.YarnImportView;
import com.mla.main.AboutView;
import com.mla.main.Configuration;
import com.mla.main.ContactView;
import com.mla.main.DbUtility;
import com.mla.main.EncryptZip;
import com.mla.main.HelpView;
import com.mla.main.IDGenerator;
import com.mla.main.Logging;
import com.mla.main.MessageView;
import com.mla.main.TechnicalView;
import com.mla.main.WindowView;
import com.mla.secure.Security;
import com.mla.user.UserAction;
import com.mla.weave.Weave;
import com.mla.weave.WeaveAction;
import com.mla.weave.WeaveView;
import com.mla.yarn.YarnAction;
import com.sun.media.jai.codec.ByteArraySeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
/**
 *
 * @Designing GUI window for utility
 * @author Amit Kumar Singh
 * 
 */
public class UtilityViewOld extends Application {
  
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    Fabric objFabric;
    Artwork objArtwork;
    Weave objWeave;
    Device objDevice;
    Colour objColour;
    Simulator objSimulator;
    
    FabricAction objFabricAction;
    ArtworkAction objArtworkAction;
    WeaveAction objWeaveAction;
    
    public static Stage simulatorStage;
    private BorderPane root;
    private Scene scene;
    private ToolBar toolBar;
    private MenuBar menuBar;
    private ScrollPane container;
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;
    
    private GridPane containerGP;
    private GridPane GP_container;
    private Menu homeMenu;
    private Menu conversionMenu;
    private Menu translatorMenu;
    private Menu pluginMenu;
    private Menu utilityMenu;
    private Menu libraryMenu;
    private Menu helpMenu;
    private Label conversionMenuLabel;
    private Label translatorMenuLabel;
    private Label pluginMenuLabel;
    private Label utilityMenuLabel;
    private Label libraryMenuLabel;
    
    private VBox countVB;
    private VBox measureVB;    
    private VBox weightVB;    
    private VBox gsmVB;  
    private VBox quitFileVB;
    private VBox deviceVB;
    private VBox simulatorVB;
    private VBox exportVB;
    private VBox importVB;
    private VBox newTranslatorVB;
    private VBox openTranslatorVB;
    private VBox saveTranslatorVB;
    private VBox saveAsTranslatorVB;
    private VBox fabricLibraryVB;
    private VBox artworkLibraryVB;
    private VBox weaveLibraryVB;
    private VBox clothLibraryVB;
    private VBox colourLibraryVB;
    private VBox pluginVB;
    
    int index = -1;
    boolean isNew = false;
    String currentLanguage="ENGLISH";
    
    public UtilityViewOld(final Stage primaryStage) {}
    
    public UtilityViewOld(Configuration objConfigurationCall) {
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);
    
        simulatorStage = new Stage();
        root = new BorderPane();
        scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(UtilityViewOld.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        
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
        menuBar.prefWidthProperty().bind(simulatorStage.widthProperty());
        topContainer.getChildren().add(menuBar);
        topContainer.getChildren().add(toolBar); 
        topContainer.setId("#topContainer");
        root.setTop(topContainer);
        
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
                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ALERT"));
                BorderPane root = new BorderPane();
                Scene scene = new Scene(root, 300, 100, Color.WHITE);
                scene.getStylesheets().add(UtilityViewOld.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        simulatorStage.close();
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
        // Utility menu - Weave, Calculation, Language
        conversionMenuLabel = new Label(objDictionaryAction.getWord("CONVERSION"));
        conversionMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCONVERSION")));
        conversionMenu = new Menu();
        conversionMenu.setGraphic(conversionMenuLabel);
        conversionMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCONVERSION"));
                menuHighlight("CONVERSION");
            }
        });        
        // Utility menu - Weave, Calculation, Language
        utilityMenuLabel = new Label(objDictionaryAction.getWord("UTILITY"));
        utilityMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPUTILITY")));
        utilityMenu = new Menu();
        utilityMenu.setGraphic(utilityMenuLabel);
        utilityMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONUTILITY"));
                menuHighlight("UTILITY");
            }
        });
        // Utility menu - Weave, Calculation, Language
        libraryMenuLabel = new Label(objDictionaryAction.getWord("LIBRARY"));
        libraryMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPLIBRARY")));
        libraryMenu = new Menu();
        libraryMenu.setGraphic(libraryMenuLabel);
        libraryMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONLIBRARY"));
                menuHighlight("LIBRARY");
            }
        });
        // Utility menu - Weave, Calculation, Language
        pluginMenuLabel = new Label(objDictionaryAction.getWord("PLUGIN"));
        pluginMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPLUGIN")));
        pluginMenu = new Menu();
        pluginMenu.setGraphic(pluginMenuLabel);
        pluginMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONPLUGIN"));
                menuHighlight("PLUGIN");
            }
        });            
        // Utility menu - Weave, Calculation, Language
        translatorMenuLabel = new Label(objDictionaryAction.getWord("TRANSLATOR"));
        translatorMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTRANSLATOR")));
        translatorMenu = new Menu();
        translatorMenu.setGraphic(translatorMenuLabel);
        translatorMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONTRANSLATOR"));
                menuHighlight("TRANSLATOR");
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
                simulatorStage.close();    
            }
        });    
        helpMenu.getItems().addAll(helpMenuItem, technicalMenuItem, aboutMenuItem, contactMenuItem, new SeparatorMenuItem(), exitMenuItem);        
        menuBar.getMenus().addAll(homeMenu, conversionMenu, utilityMenu, libraryMenu, translatorMenu, helpMenu);
        
        container = new ScrollPane();
        container.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        
        containerGP = new GridPane();
        containerGP.setMinSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        containerGP.setId("containerGP");
        
        container.setContent(containerGP);
        root.setCenter(container);
        
        menuHighlight(null);
        simulatorStage.getIcons().add(new Image("/media/icon.png"));
        simulatorStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWUTILITY")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        simulatorStage.setX(-5);
        simulatorStage.setY(0);
        simulatorStage.setResizable(false);
        simulatorStage.setScene(scene);
        simulatorStage.show();
        simulatorStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
                scene.getStylesheets().add(UtilityViewOld.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        simulatorStage.close();
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
        if(strMenu == "PLUGIN"){
            pluginMenu.setStyle("-fx-background-color: linear-gradient(#FADE9A,#FFFFD6);");
            translatorMenu.setStyle("-fx-background-color: linear-gradient(#171613,#5C594A);");
            libraryMenu.setStyle("-fx-background-color: linear-gradient(#171613,#5C594A);");
            utilityMenu.setStyle("-fx-background-color: linear-gradient(#171613,#5C594A);");
            conversionMenu.setStyle("-fx-background-color: linear-gradient(#171613,#5C594A);");
            pluginMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            translatorMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            libraryMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            utilityMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            conversionMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            toolBar.getItems().clear();
            populatePluginToolbar();
        } else if(strMenu == "TRANSLATOR"){
            pluginMenu.setStyle("-fx-background-color: linear-gradient(#171613,#5C594A);");
            translatorMenu.setStyle("-fx-background-color: linear-gradient(#FADE9A,#FFFFD6);");
            libraryMenu.setStyle("-fx-background-color: linear-gradient(#171613,#5C594A);");
            utilityMenu.setStyle("-fx-background-color: linear-gradient(#171613,#5C594A);");
            conversionMenu.setStyle("-fx-background-color: linear-gradient(#171613,#5C594A);");
            pluginMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            translatorMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            libraryMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            utilityMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            conversionMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            toolBar.getItems().clear();
            populateTranslatorToolbar();
        } else if(strMenu == "LIBRARY"){
            pluginMenu.setStyle("-fx-background-color: linear-gradient(#171613,#5C594A);");
            translatorMenu.setStyle("-fx-background-color: linear-gradient(#171613,#5C594A);");
            libraryMenu.setStyle("-fx-background-color: linear-gradient(#FADE9A,#FFFFD6);");
            utilityMenu.setStyle("-fx-background-color: linear-gradient(#171613,#5C594A);");
            conversionMenu.setStyle("-fx-background-color: linear-gradient(#171613,#5C594A);");
            pluginMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            translatorMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            libraryMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            utilityMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            conversionMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            toolBar.getItems().clear();
            populateLibraryToolbar();
        } else if(strMenu == "UTILITY"){
            pluginMenu.setStyle("-fx-background-color: linear-gradient(#171613,#5C594A);");
            translatorMenu.setStyle("-fx-background-color: linear-gradient(#171613,#5C594A);");
            libraryMenu.setStyle("-fx-background-color: linear-gradient(#171613,#5C594A);");
            utilityMenu.setStyle("-fx-background-color: linear-gradient(#FADE9A,#FFFFD6);");
            conversionMenu.setStyle("-fx-background-color: linear-gradient(#171613,#5C594A);");
            pluginMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            translatorMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            libraryMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            utilityMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            conversionMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            toolBar.getItems().clear();
            populateUtilityToolbar();
        }else {
            pluginMenu.setStyle("-fx-background-color: linear-gradient(#171613,#5C594A);");
            translatorMenu.setStyle("-fx-background-color: linear-gradient(#171613,#5C594A);");
            libraryMenu.setStyle("-fx-background-color: linear-gradient(#171613,#5C594A);");
            utilityMenu.setStyle("-fx-background-color: linear-gradient(#171613,#5C594A);");
            conversionMenu.setStyle("-fx-background-color: linear-gradient(#FADE9A,#FFFFD6);");
            pluginMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            translatorMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            libraryMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            utilityMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-text-fill: #FFFFFF;");
            conversionMenuLabel.setStyle("-fx-wrap-text:true; -fx-text-alignment: center; -fx-alignment: CENTER; -fx-font-size: 15pt; -fx-text-fill: #000000;");
            toolBar.getItems().clear();
            populateConversionToolbar();
        }              
    }  
    
 /**
 * populateConversionToolbar
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
     private void populateConversionToolbar(){
        // print File menu
        countVB = new VBox(); 
        Label countVBLbl= new Label(objDictionaryAction.getWord("COUNTCONVERSION"));
        countVBLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOUNTCONVERSION")));
        countVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/tex_calculator.png"), countVBLbl);
        countVB.setPrefWidth(80);
        countVB.getStyleClass().addAll("VBox");
        // print File menu
        measureVB = new VBox(); 
        Label testAlphabateLbl= new Label(objDictionaryAction.getWord("LINEARMEASURE"));
        testAlphabateLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPLINEARMEASURE")));
        measureVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/linear_calculator.png"), testAlphabateLbl);
        measureVB.setPrefWidth(80);
        measureVB.getStyleClass().addAll("VBox");
        // print File menu
        weightVB = new VBox(); 
        Label testWordLbl= new Label(objDictionaryAction.getWord("WEIGHTCONVERSION"));
        testWordLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWEIGHTCONVERSION")));
        weightVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/weight_calculator.png"), testWordLbl);
        weightVB.setPrefWidth(80);
        weightVB.getStyleClass().addAll("VBox");
        // gsmUtility
        gsmVB = new VBox(); 
        Label gsmLbl= new Label(objDictionaryAction.getWord("GSMCONVERSION"));
        gsmLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPGSMCONVERSION")));
        gsmVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/gsm_conversion.png"), gsmLbl);
        gsmVB.setPrefWidth(80);
        gsmVB.getStyleClass().addAll("VBox");         
        // quit File menu
        quitFileVB = new VBox(); 
        Label quitFileLbl= new Label(objDictionaryAction.getWord("CLOSE"));
        quitFileLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLOSE")));
        quitFileVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/quit.png"), quitFileLbl);
        quitFileVB.setPrefWidth(80);
        quitFileVB.getStyleClass().addAll("VBox"); 
        /************ Action Function START *****************/
        countVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {              
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCOUNTCONVERSTION"));
                convertCount();
            }
        }); 
        measureVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {             
                lblStatus.setText(objDictionaryAction.getWord("ACTIONLINEARMEASURE"));
                convertMeasure();
            }
        }); 
        weightVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {               
                lblStatus.setText(objDictionaryAction.getWord("ACTIONWEIGHTCONVERSION"));
                convertWeight();
            }
        }); 
        gsmVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {               
                lblStatus.setText(objDictionaryAction.getWord("ACTIONGSMCONVERSION"));
                convertGSM();
            }
        }); 
        quitFileVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                simulatorStage.close();
                System.gc();
                WindowView objWindowView = new WindowView(objConfiguration);
            }
        });
        /************ Test Function ENDS *****************/    
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(countVB,measureVB,weightVB,gsmVB);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
        convertCount();
    }

     
 /**
 * populateUtilityToolbar
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
     private void populateUtilityToolbar(){
        // device File menu
        deviceVB = new VBox(); 
        Label deviceLbl= new Label(objDictionaryAction.getWord("APPLICATIONINTEGRATION"));
        deviceLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPAPPLICATIONINTEGRATION")));
        deviceVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/application_integration.png"), deviceLbl);
        deviceVB.setPrefWidth(80);
        deviceVB.getStyleClass().addAll("VBox");
        // simulator file item
        simulatorVB = new VBox(); 
        Label simulatorLbl= new Label(objDictionaryAction.getWord("SIMULATOR"));
        simulatorLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSIMULATOR")));
        simulatorVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/simulation.png"), simulatorLbl);
        simulatorVB.setPrefWidth(80);
        simulatorVB.getStyleClass().addAll("VBox");
        // import file item
        importVB = new VBox();
        Label importLbl= new Label(objDictionaryAction.getWord("IMPORTFILE"));
        importLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPIMPORTFILE")));
        importVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/import.png"), importLbl);
        importVB.setPrefWidth(80);
        importVB.getStyleClass().addAll("VBox");
        // export file item
        exportVB = new VBox(); 
        Label exportLbl= new Label(objDictionaryAction.getWord("EXPORTFILE"));
        exportLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEXPORTFILE")));
        exportVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/export.png"), exportLbl);
        exportVB.setPrefWidth(80);
        exportVB.getStyleClass().addAll("VBox");
        /************ Action Function START *****************/
        deviceVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {              
                lblStatus.setText(objDictionaryAction.getWord("ACTIONAPPLICATIONINTEGRATION"));
                deviceModule();
            }
        });
        simulatorVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {              
                lblStatus.setText(objDictionaryAction.getWord("ACTIONSIMULATOR"));
                simulatorModule();
            }
        });
        importVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {              
                lblStatus.setText(objDictionaryAction.getWord("ACTIONIMPORTFILE"));
                importModule();
            }
        });
        exportVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {              
                lblStatus.setText(objDictionaryAction.getWord("ACTIONEXPORTFILE"));
                exportModule();
            }
        });
        /************ Action Function ENDS *****************/    
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(deviceVB,simulatorVB,exportVB,importVB);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
        deviceModule();
    }
    
/**
 * populateTranslatorToolbar
 * <p>
 * Function use for drawing tool bar for menu item translator,
 * and binding events for each tools. 
 * 
 * @exception   (@throws SQLException)
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         javafx.event.*;
 * @link        FabricView
 */
    private void populateTranslatorToolbar(){
        // New file item
        newTranslatorVB = new VBox();
        Label newTranslatorLbl= new Label(objDictionaryAction.getWord("NEWFILE"));
        newTranslatorLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNEWFILE")));
        newTranslatorVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/new.png"), newTranslatorLbl);
        newTranslatorVB.setPrefWidth(80);
        newTranslatorVB.getStyleClass().addAll("VBox");    
        // Open file item
        openTranslatorVB = new VBox(); 
        Label openTranslatorLbl= new Label(objDictionaryAction.getWord("OPENFILE"));
        openTranslatorLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOPENFILE")));
        openTranslatorVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/open.png"), openTranslatorLbl);
        openTranslatorVB.setPrefWidth(80);
        openTranslatorVB.getStyleClass().addAll("VBox");
        // Save file item
        saveTranslatorVB = new VBox(); 
        Label saveTranslatorLbl= new Label(objDictionaryAction.getWord("SAVEFILE"));
        saveTranslatorLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVEFILE")));
        saveTranslatorVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/save.png"), saveTranslatorLbl);
        saveTranslatorVB.setPrefWidth(80);
        saveTranslatorVB.getStyleClass().addAll("VBox");
        if(isNew){
            saveTranslatorVB.getChildren().set(0, new ImageView(objConfiguration.getStrColourDimmed()+"/save.png"));
            //saveTranslatorVB.setVisible(false);
            saveTranslatorVB.setDisable(true);
        }
        // Save As file item
        saveAsTranslatorVB = new VBox(); 
        Label saveAsTranslatorLbl= new Label(objDictionaryAction.getWord("SAVEASFILE"));
        saveAsTranslatorLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVEASFILE")));
        saveAsTranslatorVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/save_as.png"), saveAsTranslatorLbl);
        saveAsTranslatorVB.setPrefWidth(80);
        saveAsTranslatorVB.getStyleClass().addAll("VBox");
        //Action
        newTranslatorVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {              
                lblStatus.setText(objDictionaryAction.getWord("ACTIONNEWFILE"));
                populateLibraryLanguageNew();
            }
        });
        saveTranslatorVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {              
                lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEFILE"));
                saveLibraryLanguage();
            }
        });
        saveAsTranslatorVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {              
                lblStatus.setText(objDictionaryAction.getWord("ACTIONSAVEASFILE"));
                saveAsLibraryLanguage();
            }
        });
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(newTranslatorVB,/*openTranslatorVB,*/saveTranslatorVB,saveAsTranslatorVB);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
        loadLibraryLanguage();
    }
/**
 * populateLibraryToolbar
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
    private void populateLibraryToolbar(){
        // fabric library item
        fabricLibraryVB = new VBox(); 
        Label fabricLibraryLbl= new Label(objDictionaryAction.getWord("FABRICLIBRARY"));
        fabricLibraryLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFABRICLIBRARY")));
        fabricLibraryVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/fabric_library.png"), fabricLibraryLbl);
        fabricLibraryVB.setPrefWidth(80);
        fabricLibraryVB.getStyleClass().addAll("VBox");
        // artwork library item
        artworkLibraryVB = new VBox(); 
        Label artworkLibraryLbl= new Label(objDictionaryAction.getWord("ARTWORKLIBRARY"));
        artworkLibraryLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPARTWORKLIBRARY")));
        artworkLibraryVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/artwork_library.png"), artworkLibraryLbl);
        artworkLibraryVB.setPrefWidth(80);
        artworkLibraryVB.getStyleClass().addAll("VBox");
        // weave library item
        weaveLibraryVB = new VBox(); 
        Label weaveLibraryLbl= new Label(objDictionaryAction.getWord("WEAVELIBRARY"));
        weaveLibraryLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWEAVELIBRARY")));
        weaveLibraryVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/weave_library.png"), weaveLibraryLbl);
        weaveLibraryVB.setPrefWidth(80);
        weaveLibraryVB.getStyleClass().addAll("VBox");
        // weave library item
        clothLibraryVB = new VBox(); 
        Label clothLibraryLbl= new Label(objDictionaryAction.getWord("CLOTHLIBRARY"));
        clothLibraryLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLOTHLIBRARY")));
        clothLibraryVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/simulation.png"), clothLibraryLbl);
        clothLibraryVB.setPrefWidth(80);
        clothLibraryVB.getStyleClass().addAll("VBox");
        // colour library item
        colourLibraryVB = new VBox(); 
        Label colourLibraryLbl= new Label(objDictionaryAction.getWord("COLOURLIBRARY"));
        colourLibraryLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOLOURLIBRARY")));
        colourLibraryVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/color_palette.png"), colourLibraryLbl);
        colourLibraryVB.setPrefWidth(80);
        colourLibraryVB.getStyleClass().addAll("VBox");
        
        /************ Action Function START *****************/
        fabricLibraryVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {              
                lblStatus.setText(objDictionaryAction.getWord("ACTIONFABRICLIBRARY"));
                loadLibraryFabric();
            }
        });
        artworkLibraryVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {              
                lblStatus.setText(objDictionaryAction.getWord("ACTIONARTWORKLIBRARY"));
                loadLibraryArtwork();
            }
        });
        weaveLibraryVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {              
                lblStatus.setText(objDictionaryAction.getWord("ACTIONWEAVELIBRARY"));
                loadLibraryWeave();
            }
        });
        clothLibraryVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {              
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCLOTHLIBRARY"));
                loadLibraryFabric();
            }
        });
        colourLibraryVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {              
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCOLOURLIBRARY"));
                loadLibraryColour();
            }
        });
        /************ Action Function ENDS *****************/    
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(fabricLibraryVB,artworkLibraryVB,weaveLibraryVB,clothLibraryVB,colourLibraryVB);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
        loadLibraryFabric();
    }
        
        
/**
 * populatePluginToolbar
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
    private void populatePluginToolbar(){
        // artwork edit item
        pluginVB = new VBox(); 
        Label pluginLbl= new Label(objDictionaryAction.getWord("PLUGIN"));
        pluginLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPLUGIN")));
        pluginVB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/open_artwork.png"), pluginLbl);
        pluginVB.setPrefWidth(80);
        pluginVB.getStyleClass().addAll("VBox");
        //Create some Buttons.        
        FlowPane flow = new FlowPane();        
        flow.setPrefWrapLength(objConfiguration.WIDTH*.95);
        flow.getStyleClass().addAll("flow");
        //Add the Buttons to the ToolBar.
        flow.getChildren().addAll(pluginVB);
        flow.setAlignment(Pos.TOP_LEFT);
        toolBar.getItems().addAll(flow);
    }
    
    public void convertCount(){
        containerGP.getChildren().clear();
        Label caption = new Label(objDictionaryAction.getWord("YARNCOUNTCONVERSION"));
        caption.setId("caption");
        GridPane.setConstraints(caption, 0, 0);
        GridPane.setColumnSpan(caption, 2);
        containerGP.getChildren().add(caption);
 
        Separator sepHor1 = new Separator();
        sepHor1.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor1, 0, 1);
        GridPane.setColumnSpan(sepHor1, 2);
        containerGP.getChildren().add(sepHor1);
        
        Label valueCountLbl= new Label(objDictionaryAction.getWord("YARNCOUNT")+" :");
        //valueCountLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        containerGP.add(valueCountLbl, 0, 2);
        final TextField valueCountTxt = new TextField("1"){
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
        valueCountTxt.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOUNT")));
        containerGP.add(valueCountTxt, 1, 2);

        Label fromCountLbl= new Label(objDictionaryAction.getWord("YARNUNIT")+" :");
        //fromCountLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        containerGP.add(fromCountLbl, 0, 3);
        final ComboBox fromCountCB = new ComboBox();
        fromCountCB.getItems().addAll("Tex","dTex","K tex","Denier (Td)","Nm","Grains/Yard","Woollen (Aberdeen) (Ta)","Woollen (US Grain)","Asbestos (American) (NaA)","Asbestos (English) (NeA)","Cotton bump Yarn (NB)","Glass (UK & USA)","Linen (Set or Dry Spun) (NeL)","Spun Silk (Ns)","Woollen (American Cut) (Nac)","Woollen (American run) (Nar)","Woollen (Yarkshire) (Ny)","Woollen (Worsted) (New)","Linen, Hemp, Jute (Tj)","Micronaire (Mic)","Yards Per Pound (YPP)","English Worsted Count (NeK)","English Cotton (NeC)","Numero en puntos (Np)");  
        fromCountCB.setValue("Tex");
        fromCountCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNUNIT")));
        containerGP.add(fromCountCB, 1, 3);
        
        Label toCountLbl= new Label(objDictionaryAction.getWord("YARNUNIT")+" :");
        //toCountLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        containerGP.add(toCountLbl, 0, 4);
        final ComboBox toCountCB = new ComboBox();
        toCountCB.getItems().addAll("Tex","dTex","K tex","Denier (Td)","Nm","Grains/Yard","Woollen (Aberdeen) (Ta)","Woollen (US Grain)","Asbestos (American) (NaA)","Asbestos (English) (NeA)","Cotton bump Yarn (NB)","Glass (UK & USA)","Linen (Set or Dry Spun) (NeL)","Spun Silk (Ns)","Woollen (American Cut) (Nac)","Woollen (American run) (Nar)","Woollen (Yarkshire) (Ny)","Woollen (Worsted) (New)","Linen, Hemp, Jute (Tj)","Micronaire (Mic)","Yards Per Pound (YPP)","English Worsted Count (NeK)","English Cotton (NeC)","Numero en puntos (Np)");  
        toCountCB.setValue("Tex");
        toCountCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNUNIT")));
        containerGP.add(toCountCB, 1, 4);
        
        Label newCountLbl= new Label(objDictionaryAction.getWord("YARNCOUNT")+" :");
        newCountLbl.setId("result");
        containerGP.add(newCountLbl, 0, 5);
        final Label newCountTxt = new Label("1");
        newCountTxt.setId("result");
        containerGP.add(newCountTxt, 1, 5);
        
        valueCountTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase("0")){
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                        valueCountTxt.setText(t);
                    }else {
                        objFabricAction = new FabricAction();
                        double newValue = objFabricAction.convertUnit(fromCountCB.getValue().toString(), toCountCB.getValue().toString(), Double.parseDouble(valueCountTxt.getText()));
                        newCountTxt.setText(Double.toString(newValue));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSCOUNT"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"yarn count property change",ex);
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"yarn count property change",ex);
                }
            }
        });
        fromCountCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase(toCountCB.getValue().toString())){
                        newCountTxt.setText(objDictionaryAction.getWord("ERRORCOUNT"));
                        lblStatus.setText(objDictionaryAction.getWord("ERRORCOUNT"));
                    }else{
                        objFabricAction = new FabricAction();
                        double newValue = objFabricAction.convertUnit(t1, toCountCB.getValue().toString(), Double.parseDouble(valueCountTxt.getText()));
                        newCountTxt.setText(Double.toString(newValue));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSCOUNT"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"count unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }  catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"count unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        toCountCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase(fromCountCB.getValue().toString())){
                        newCountTxt.setText(objDictionaryAction.getWord("ERRORCOUNT"));
                        lblStatus.setText(objDictionaryAction.getWord("ERRORCOUNT"));
                    }else{
                        objFabricAction = new FabricAction();
                        double newValue = objFabricAction.convertUnit(fromCountCB.getValue().toString(), t1, Double.parseDouble(valueCountTxt.getText()));
                        newCountTxt.setText(Double.toString(newValue));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSCOUNT"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"count unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }  catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"count unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
    }
    
    public void convertMeasure(){
        containerGP.getChildren().clear();
        Label caption = new Label(objDictionaryAction.getWord("LINEARMEASURECONVERSION"));
	caption.setId("caption");
        GridPane.setConstraints(caption, 0, 0);
        GridPane.setColumnSpan(caption, 2);
        containerGP.getChildren().add(caption);
 
        Separator sepHor1 = new Separator();
        sepHor1.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor1, 0, 1);
        GridPane.setColumnSpan(sepHor1, 2);
        containerGP.getChildren().add(sepHor1);
        
        Label valueMeasureLbl= new Label(objDictionaryAction.getWord("OLD")+" "+objDictionaryAction.getWord("LENGTH")+" :");
        //valueMeasureLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        containerGP.add(valueMeasureLbl, 0, 2);
        final TextField valueMeasureTxt = new TextField("1"){
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
        valueMeasureTxt.setTooltip(new Tooltip(objDictionaryAction.getWord("OLD")+" "+objDictionaryAction.getWord("LENGTH")));
        containerGP.add(valueMeasureTxt, 1, 2);

        Label fromMeasureLbl= new Label(objDictionaryAction.getWord("SOURCE")+" "+objDictionaryAction.getWord("UNIT")+" :");
        //fromMeasureLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        containerGP.add(fromMeasureLbl, 0, 3);
        final ComboBox fromMeasureCB = new ComboBox();
        fromMeasureCB.getItems().addAll("Yard","Foot","Inch","Meter","Milimeter");  
        fromMeasureCB.setValue("Inch");
        fromMeasureCB.setTooltip(new Tooltip(objDictionaryAction.getWord("SOURCE")+" "+objDictionaryAction.getWord("UNIT")));
        containerGP.add(fromMeasureCB, 1, 3);
        
        Label toMeasureLbl= new Label(objDictionaryAction.getWord("DESTINATION")+" "+objDictionaryAction.getWord("UNIT")+" :");
        //toMeasureLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        containerGP.add(toMeasureLbl, 0, 4);
        final ComboBox toMeasureCB = new ComboBox();
        toMeasureCB.getItems().addAll("Yard","Foot","Inch","Meter","Milimeter");
        toMeasureCB.setValue("Inch");
        toMeasureCB.setTooltip(new Tooltip(objDictionaryAction.getWord("DESTINATION")+" "+objDictionaryAction.getWord("UNIT")));
        containerGP.add(toMeasureCB, 1, 4);
        
        Label newMeasureLbl= new Label(objDictionaryAction.getWord("NEW")+" "+objDictionaryAction.getWord("LENGTH")+" :");
        newMeasureLbl.setId("result");
        containerGP.add(newMeasureLbl, 0, 5);
        final Label newMeasueTxt = new Label("1");
        newMeasueTxt.setId("result");
        containerGP.add(newMeasueTxt, 1, 5);
        
        valueMeasureTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase("0")){
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                        valueMeasureTxt.setText(t);
                    }else {
                        objFabricAction = new FabricAction();
                        double newValue = objFabricAction.convertMeasure(fromMeasureCB.getValue().toString(), toMeasureCB.getValue().toString(), Double.parseDouble(valueMeasureTxt.getText()));
                        newMeasueTxt.setText(Double.toString(newValue));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSMEASURE"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"mesaure text property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"mesaure text property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        fromMeasureCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase(toMeasureCB.getValue().toString())){
                        newMeasueTxt.setText(objDictionaryAction.getWord("ERRORMEASURE"));
                        lblStatus.setText(objDictionaryAction.getWord("ERRORMEASURE"));
                    }else{
                        objFabricAction = new FabricAction();
                        double newValue = objFabricAction.convertMeasure(t1, toMeasureCB.getValue().toString(), Double.parseDouble(valueMeasureTxt.getText()));
                        newMeasueTxt.setText(Double.toString(newValue));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSMEASURE"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"mesaure unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }  catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"mesaure unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        toMeasureCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase(fromMeasureCB.getValue().toString())){
                        newMeasueTxt.setText(objDictionaryAction.getWord("ERRORMEASURE"));
                        lblStatus.setText(objDictionaryAction.getWord("ERRORMEASURE"));
                    }else{
                        objFabricAction = new FabricAction();
                        double newValue = objFabricAction.convertMeasure(fromMeasureCB.getValue().toString(), t1, Double.parseDouble(valueMeasureTxt.getText()));
                        newMeasueTxt.setText(Double.toString(newValue));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSMEASURE"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"mesaure unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"mesaure unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
    }
    
    public void convertWeight(){
        containerGP.getChildren().clear();
        Label caption = new Label(objDictionaryAction.getWord("WEIGHTMEASURECONVERSION"));
        caption.setId("caption");
        GridPane.setConstraints(caption, 0, 0);
        GridPane.setColumnSpan(caption, 2);
        containerGP.getChildren().add(caption);
 
        Separator sepHor1 = new Separator();
        sepHor1.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor1, 0, 1);
        GridPane.setColumnSpan(sepHor1, 2);
        containerGP.getChildren().add(sepHor1);
        
        Label valueWeightLbl= new Label(objDictionaryAction.getWord("OLD")+" "+objDictionaryAction.getWord("WEIGHT")+" :");
        //valueWeightLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        containerGP.add(valueWeightLbl, 0, 2);
        final TextField valueWeightTxt = new TextField("1"){
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
        valueWeightTxt.setTooltip(new Tooltip(objDictionaryAction.getWord("OLD")+" "+objDictionaryAction.getWord("WEIGHT")));
        containerGP.add(valueWeightTxt, 1, 2);

        Label fromWeightLbl= new Label(objDictionaryAction.getWord("SOURCE")+" "+objDictionaryAction.getWord("UNIT")+" :");
        //fromWeightLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        containerGP.add(fromWeightLbl, 0, 3);
        final ComboBox fromWeightCB = new ComboBox();
        fromWeightCB.getItems().addAll("Ounce","Grain","Pound","Kilogram","Gram");  
        fromWeightCB.setValue("Gram");
        fromWeightCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWEIGHT")+" "+objDictionaryAction.getWord("SOURCE")+" "+objDictionaryAction.getWord("UNIT")));
        containerGP.add(fromWeightCB, 1, 3);
        
        Label toWeightLbl= new Label(objDictionaryAction.getWord("DESTINATION")+" "+objDictionaryAction.getWord("UNIT")+" :");
        //toWeightLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        containerGP.add(toWeightLbl, 0, 4);
        final ComboBox toWeightCB = new ComboBox();
        toWeightCB.getItems().addAll("Ounce","Grain","Pound","Kilogram","Gram");  
        toWeightCB.setValue("Gram");
        toWeightCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWEIGHT")+" "+objDictionaryAction.getWord("DESTINATION")+" "+objDictionaryAction.getWord("UNIT")));
        containerGP.add(toWeightCB, 1, 4);
        
        Label newWeightLbl= new Label(objDictionaryAction.getWord("NEW")+" "+objDictionaryAction.getWord("WEIGHT")+" :");
        newWeightLbl.setId("result");
        containerGP.add(newWeightLbl, 0, 5);
        final Label newWeightTxt = new Label("1");
        newWeightTxt.setId("result");
        containerGP.add(newWeightTxt, 1, 5);
        
        valueWeightTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase("0")){
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                        valueWeightTxt.setText(t);
                    }else {
                        objFabricAction = new FabricAction();
                        double newValue = objFabricAction.convertWeight(fromWeightCB.getValue().toString(), toWeightCB.getValue().toString(), Double.parseDouble(valueWeightTxt.getText()));
                        newWeightTxt.setText(Double.toString(newValue));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSWEIGHT"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"Weight text property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"Weight text property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        fromWeightCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase(toWeightCB.getValue().toString())){
                        newWeightTxt.setText(objDictionaryAction.getWord("ERRORWEIGHT"));
                        lblStatus.setText(objDictionaryAction.getWord("ERRORWEIGHT"));
                    }else{
                        objFabricAction = new FabricAction();
                        double newValue = objFabricAction.convertWeight(t1, toWeightCB.getValue().toString(), Double.parseDouble(valueWeightTxt.getText()));
                        newWeightTxt.setText(Double.toString(newValue));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSWEIGHT"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"Weight unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }  catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"Weight unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        toWeightCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase(fromWeightCB.getValue().toString())){
                        newWeightTxt.setText(objDictionaryAction.getWord("ERRORWEIGHT"));
                        lblStatus.setText(objDictionaryAction.getWord("ERRORWEIGHT"));
                    }else{
                        objFabricAction = new FabricAction();
                        double newValue = objFabricAction.convertWeight(fromWeightCB.getValue().toString(), t1, Double.parseDouble(valueWeightTxt.getText()));
                        newWeightTxt.setText(Double.toString(newValue));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSWEIGHT"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"Weight unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"Weight unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
    }
    
    public void convertGSM(){
        containerGP.getChildren().clear();
        Label caption = new Label(objDictionaryAction.getWord("GRAMSQUAREMETERCONVERSION"));
        caption.setId("caption");
        GridPane.setConstraints(caption, 0, 0);
        GridPane.setColumnSpan(caption, 2);
        containerGP.getChildren().add(caption);
 
        Separator sepHor1 = new Separator();
        sepHor1.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor1, 0, 1);
        GridPane.setColumnSpan(sepHor1, 2);
        containerGP.getChildren().add(sepHor1);
        
        Label lblEPI = new Label(objDictionaryAction.getWord("EPI")+"(in per inch):");
        //lblWeight.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        lblEPI.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEPI")));
        containerGP.add(lblEPI, 0, 2);
        final TextField txtEPI = new TextField(){
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
        txtEPI.setText(Integer.toString(objConfiguration.getIntEPI()));        
        txtEPI.setPromptText(objDictionaryAction.getWord("EPI"));
        txtEPI.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEPI")));
        containerGP.add(txtEPI, 1, 2);
                
        Label lblPPI = new Label(objDictionaryAction.getWord("PPI")+" (in per inch):");
        //lblLength.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        lblPPI.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPPI")));
        containerGP.add(lblPPI, 0, 3);
        final TextField txtPPI = new TextField(){
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
        txtPPI.setText(Integer.toString(objConfiguration.getIntPPI()));
        txtPPI.setPromptText(objDictionaryAction.getWord("PPI"));
        txtPPI.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPPI")));
        containerGP.add(txtPPI, 1, 3);                
        
        Label lblCrimpWarp = new Label(objDictionaryAction.getWord("WARP")+" "+objDictionaryAction.getWord("YARNCRIMP")+" (%)");
        lblCrimpWarp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCRIMP")));
        containerGP.add(lblCrimpWarp, 0, 4);
        final TextField txtCrimpWarp = new TextField(Integer.toString(objConfiguration.getIntWarpCrimp()));
        txtCrimpWarp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCRIMP")));
        containerGP.add(txtCrimpWarp, 1, 4);
            
        Label lblCrimpWeft = new Label(objDictionaryAction.getWord("WEFT")+" "+objDictionaryAction.getWord("YARNCRIMP")+" (%)");
        lblCrimpWarp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCRIMP")));
        containerGP.add(lblCrimpWeft, 0, 5);
        final TextField txtCrimpWeft = new TextField(Integer.toString(objConfiguration.getIntWeftCrimp()));
        txtCrimpWeft.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCRIMP")));
        containerGP.add(txtCrimpWeft, 1, 5);                
        
        Label lblCountWarp = new Label(objDictionaryAction.getWord("WARP")+" "+objDictionaryAction.getWord("YARNCOUNT")+" :");
        lblCountWarp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOUNT")));
        containerGP.add(lblCountWarp, 0, 6);
        final TextField txtCountWarp = new TextField(Integer.toString(objConfiguration.getIntWarpCount()));
        txtCountWarp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOUNT")));
        containerGP.add(txtCountWarp, 1, 6);
        
        Label lblUnitWarp= new Label(objDictionaryAction.getWord("WARP")+" "+objDictionaryAction.getWord("YARNUNIT")+" :");
        //lblUnitWeft.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblUnitWarp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNUNIT")));
        containerGP.add(lblUnitWarp, 0, 7);
        final ComboBox cbUnitWarp = new ComboBox();
        cbUnitWarp.getItems().addAll("Tex","dTex","K tex","Denier (Td)","Nm","Grains/Yard","Woollen (Aberdeen) (Ta)","Woollen (US Grain)","Asbestos (American) (NaA)","Asbestos (English) (NeA)","Cotton bump Yarn (NB)","Glass (UK & USA)","Linen (Set or Dry Spun) (NeL)","Spun Silk (Ns)","Woollen (American Cut) (Nac)","Woollen (American run) (Nar)","Woollen (Yarkshire) (Ny)","Woollen (Worsted) (New)","Linen, Hemp, Jute (Tj)","Micronaire (Mic)","Yards Per Pound (YPP)","English Worsted Count (NeK)","English Cotton (NeC)","Numero en puntos (Np)");  
        cbUnitWarp.setValue(objConfiguration.getStrWarpUnit());
        cbUnitWarp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNUNIT")));
        containerGP.add(cbUnitWarp, 1, 7);
                
        Label lblCountWeft = new Label(objDictionaryAction.getWord("WEFT")+" "+objDictionaryAction.getWord("YARNCOUNT")+" :");
        lblCountWeft.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOUNT")));
        containerGP.add(lblCountWeft, 0, 8);
        final TextField txtCountWeft = new TextField(Integer.toString(objConfiguration.getIntWeftCount()));
        txtCountWeft.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOUNT")));
        containerGP.add(txtCountWeft, 1, 8);                
        
        Label lblUnitWeft= new Label(objDictionaryAction.getWord("WEFT")+" "+objDictionaryAction.getWord("YARNUNIT")+" :");
        //lblUnitWeft.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblUnitWeft.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNUNIT")));
        containerGP.add(lblUnitWeft, 0, 9);
        final ComboBox cbUnitWeft = new ComboBox();
        cbUnitWeft.getItems().addAll("Tex","dTex","K tex","Denier (Td)","Nm","Grains/Yard","Woollen (Aberdeen) (Ta)","Woollen (US Grain)","Asbestos (American) (NaA)","Asbestos (English) (NeA)","Cotton bump Yarn (NB)","Glass (UK & USA)","Linen (Set or Dry Spun) (NeL)","Spun Silk (Ns)","Woollen (American Cut) (Nac)","Woollen (American run) (Nar)","Woollen (Yarkshire) (Ny)","Woollen (Worsted) (New)","Linen, Hemp, Jute (Tj)","Micronaire (Mic)","Yards Per Pound (YPP)","English Worsted Count (NeK)","English Cotton (NeC)","Numero en puntos (Np)");  
        cbUnitWeft.setValue(objConfiguration.getStrWeftUnit());
        cbUnitWeft.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNUNIT")));
        containerGP.add(cbUnitWeft, 1, 9);

        Label lblGSM = new Label(objDictionaryAction.getWord("GSM")+" :");
        lblGSM.setId("result");
        containerGP.add(lblGSM, 0, 10);
        final Label txtGSM = new Label();
        txtGSM.setId("result");
        containerGP.add(txtGSM, 1, 10);
        try {
            objFabricAction = new FabricAction();
        } catch (SQLException ex) {
            new Logging("SEVERE",UtilityViewOld.class.getName(),"find gsm",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
        txtGSM.setText(Double.toString(objFabricAction.calculateGSM(objConfiguration)));
                        
        Label lblNote = new Label(objDictionaryAction.getWord("TOOLTIPGSM"));
        lblNote.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPGSM")));
        lblNote.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        containerGP.add(lblNote, 0, 11, 2, 1);
        
        txtEPI.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase("0") || t1.trim().isEmpty()){
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                        txtEPI.setText(t);
                    }else {
                        if(txtPPI.getText().equalsIgnoreCase("0") || txtPPI.getText().trim().isEmpty())
                            txtPPI.setText(Integer.toString(1));
                        if(txtCountWarp.getText().equalsIgnoreCase("0") || txtCountWarp.getText().trim().isEmpty())
                            txtCountWarp.setText(Integer.toString(1));
                        if(txtCountWeft.getText().equalsIgnoreCase("0") || txtCountWeft.getText().trim().isEmpty())
                            txtCountWeft.setText(Integer.toString(1));
                        if(txtCrimpWarp.getText().equalsIgnoreCase("0") || txtCrimpWarp.getText().trim().isEmpty())
                            txtCrimpWarp.setText(Integer.toString(1));
                        if(txtCrimpWeft.getText().equalsIgnoreCase("0") || txtCrimpWeft.getText().trim().isEmpty())
                            txtCrimpWeft.setText(Integer.toString(1));
                        
                        objConfiguration.setIntEPI(Integer.parseInt(txtEPI.getText()));
                        objConfiguration.setIntPPI(Integer.parseInt(txtPPI.getText()));
                        objConfiguration.setIntWarpCount(Integer.parseInt(txtCountWarp.getText()));
                        objConfiguration.setIntWeftCount(Integer.parseInt(txtCountWeft.getText()));
                        objConfiguration.setIntWarpCrimp(Integer.parseInt(txtCrimpWarp.getText()));
                        objConfiguration.setIntWeftCrimp(Integer.parseInt(txtCrimpWeft.getText()));
                        objConfiguration.setStrWarpUnit(cbUnitWarp.getValue().toString());
                        objConfiguration.setStrWeftUnit(cbUnitWeft.getValue().toString());
                        objFabricAction = new FabricAction();
                        txtGSM.setText(Double.toString(objFabricAction.calculateGSM(objConfiguration)));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSGSM"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        txtPPI.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase("0") || t1.trim().isEmpty()){
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                        txtPPI.setText(t);
                    }else {
                        if(txtEPI.getText().equalsIgnoreCase("0") || txtEPI.getText().trim().isEmpty())
                            txtEPI.setText(Integer.toString(1));
                        if(txtCountWarp.getText().equalsIgnoreCase("0") || txtCountWarp.getText().trim().isEmpty())
                            txtCountWarp.setText(Integer.toString(1));
                        if(txtCountWeft.getText().equalsIgnoreCase("0") || txtCountWeft.getText().trim().isEmpty())
                            txtCountWeft.setText(Integer.toString(1));
                        if(txtCrimpWarp.getText().equalsIgnoreCase("0") || txtCrimpWarp.getText().trim().isEmpty())
                            txtCrimpWarp.setText(Integer.toString(1));
                        if(txtCrimpWeft.getText().equalsIgnoreCase("0") || txtCrimpWeft.getText().trim().isEmpty())
                            txtCrimpWeft.setText(Integer.toString(1));
                        
                        objConfiguration.setIntEPI(Integer.parseInt(txtEPI.getText()));
                        objConfiguration.setIntPPI(Integer.parseInt(txtPPI.getText()));
                        objConfiguration.setIntWarpCount(Integer.parseInt(txtCountWarp.getText()));
                        objConfiguration.setIntWeftCount(Integer.parseInt(txtCountWeft.getText()));
                        objConfiguration.setIntWarpCrimp(Integer.parseInt(txtCrimpWarp.getText()));
                        objConfiguration.setIntWeftCrimp(Integer.parseInt(txtCrimpWeft.getText()));
                        objConfiguration.setStrWarpUnit(cbUnitWarp.getValue().toString());
                        objConfiguration.setStrWeftUnit(cbUnitWeft.getValue().toString());
                        objFabricAction = new FabricAction();
                        txtGSM.setText(Double.toString(objFabricAction.calculateGSM(objConfiguration)));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSGSM"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        txtCountWarp.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase("0") || t1.trim().isEmpty()){
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                        txtCountWarp.setText(t);
                    }else {
                        if(txtEPI.getText().equalsIgnoreCase("0") || txtEPI.getText().trim().isEmpty())
                            txtEPI.setText(Integer.toString(1));
                        if(txtPPI.getText().equalsIgnoreCase("0") || txtPPI.getText().trim().isEmpty())
                            txtPPI.setText(Integer.toString(1));
                        if(txtCountWeft.getText().equalsIgnoreCase("0") || txtCountWeft.getText().trim().isEmpty())
                            txtCountWeft.setText(Integer.toString(1));
                        if(txtCrimpWarp.getText().equalsIgnoreCase("0") || txtCrimpWarp.getText().trim().isEmpty())
                            txtCrimpWarp.setText(Integer.toString(1));
                        if(txtCrimpWeft.getText().equalsIgnoreCase("0") || txtCrimpWeft.getText().trim().isEmpty())
                            txtCrimpWeft.setText(Integer.toString(1));
                        
                        objConfiguration.setIntEPI(Integer.parseInt(txtEPI.getText()));
                        objConfiguration.setIntPPI(Integer.parseInt(txtPPI.getText()));
                        objConfiguration.setIntWarpCount(Integer.parseInt(txtCountWarp.getText()));
                        objConfiguration.setIntWeftCount(Integer.parseInt(txtCountWeft.getText()));
                        objConfiguration.setIntWarpCrimp(Integer.parseInt(txtCrimpWarp.getText()));
                        objConfiguration.setIntWeftCrimp(Integer.parseInt(txtCrimpWeft.getText()));
                        objConfiguration.setStrWarpUnit(cbUnitWarp.getValue().toString());
                        objConfiguration.setStrWeftUnit(cbUnitWeft.getValue().toString());
                        objFabricAction = new FabricAction();
                        txtGSM.setText(Double.toString(objFabricAction.calculateGSM(objConfiguration)));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSGSM"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        txtCountWeft.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase("0") || t1.trim().isEmpty()){
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                        txtCountWeft.setText(t);
                    }else {
                        if(txtEPI.getText().equalsIgnoreCase("0") || txtEPI.getText().trim().isEmpty())
                            txtEPI.setText(Integer.toString(1));
                        if(txtPPI.getText().equalsIgnoreCase("0") || txtPPI.getText().trim().isEmpty())
                            txtPPI.setText(Integer.toString(1));
                        if(txtCountWarp.getText().equalsIgnoreCase("0") || txtCountWarp.getText().trim().isEmpty())
                            txtCountWarp.setText(Integer.toString(1));
                        if(txtCrimpWarp.getText().equalsIgnoreCase("0") || txtCrimpWarp.getText().trim().isEmpty())
                            txtCrimpWarp.setText(Integer.toString(1));
                        if(txtCrimpWeft.getText().equalsIgnoreCase("0") || txtCrimpWeft.getText().trim().isEmpty())
                            txtCrimpWeft.setText(Integer.toString(1));
                        
                        objConfiguration.setIntEPI(Integer.parseInt(txtEPI.getText()));
                        objConfiguration.setIntPPI(Integer.parseInt(txtPPI.getText()));
                        objConfiguration.setIntWarpCount(Integer.parseInt(txtCountWarp.getText()));
                        objConfiguration.setIntWeftCount(Integer.parseInt(txtCountWeft.getText()));
                        objConfiguration.setIntWarpCrimp(Integer.parseInt(txtCrimpWarp.getText()));
                        objConfiguration.setIntWeftCrimp(Integer.parseInt(txtCrimpWeft.getText()));
                        objConfiguration.setStrWarpUnit(cbUnitWarp.getValue().toString());
                        objConfiguration.setStrWeftUnit(cbUnitWeft.getValue().toString());
                        objFabricAction = new FabricAction();
                        txtGSM.setText(Double.toString(objFabricAction.calculateGSM(objConfiguration)));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSGSM"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        txtCrimpWarp.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase("0") || t1.trim().isEmpty()){
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                        txtCrimpWarp.setText(t);
                    }else {
                        if(txtEPI.getText().equalsIgnoreCase("0") || txtEPI.getText().trim().isEmpty())
                            txtEPI.setText(Integer.toString(1));
                        if(txtPPI.getText().equalsIgnoreCase("0") || txtPPI.getText().trim().isEmpty())
                            txtPPI.setText(Integer.toString(1));
                        if(txtCountWarp.getText().equalsIgnoreCase("0") || txtCountWarp.getText().trim().isEmpty())
                            txtCountWarp.setText(Integer.toString(1));
                        if(txtCountWeft.getText().equalsIgnoreCase("0") || txtCountWeft.getText().trim().isEmpty())
                            txtCountWeft.setText(Integer.toString(1));
                        if(txtCrimpWeft.getText().equalsIgnoreCase("0") || txtCrimpWeft.getText().trim().isEmpty())
                            txtCrimpWeft.setText(Integer.toString(1));
                        
                        objConfiguration.setIntEPI(Integer.parseInt(txtEPI.getText()));
                        objConfiguration.setIntPPI(Integer.parseInt(txtPPI.getText()));
                        objConfiguration.setIntWarpCount(Integer.parseInt(txtCountWarp.getText()));
                        objConfiguration.setIntWeftCount(Integer.parseInt(txtCountWeft.getText()));
                        objConfiguration.setIntWarpCrimp(Integer.parseInt(txtCrimpWarp.getText()));
                        objConfiguration.setIntWeftCrimp(Integer.parseInt(txtCrimpWeft.getText()));
                        objConfiguration.setStrWarpUnit(cbUnitWarp.getValue().toString());
                        objConfiguration.setStrWeftUnit(cbUnitWeft.getValue().toString());
                        objFabricAction = new FabricAction();
                        txtGSM.setText(Double.toString(objFabricAction.calculateGSM(objConfiguration)));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSGSM"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        txtCrimpWeft.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase("0") || t1.trim().isEmpty()){
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                        txtCrimpWarp.setText(t);
                    }else {
                        if(txtEPI.getText().equalsIgnoreCase("0") || txtEPI.getText().trim().isEmpty())
                            txtEPI.setText(Integer.toString(1));
                        if(txtPPI.getText().equalsIgnoreCase("0") || txtPPI.getText().trim().isEmpty())
                            txtPPI.setText(Integer.toString(1));
                        if(txtCountWarp.getText().equalsIgnoreCase("0") || txtCountWarp.getText().trim().isEmpty())
                            txtCountWarp.setText(Integer.toString(1));
                        if(txtCountWeft.getText().equalsIgnoreCase("0") || txtCountWeft.getText().trim().isEmpty())
                            txtCountWeft.setText(Integer.toString(1));
                        if(txtCrimpWarp.getText().equalsIgnoreCase("0") || txtCrimpWarp.getText().trim().isEmpty())
                            txtCrimpWarp.setText(Integer.toString(1));
                        
                        objConfiguration.setIntEPI(Integer.parseInt(txtEPI.getText()));
                        objConfiguration.setIntPPI(Integer.parseInt(txtPPI.getText()));
                        objConfiguration.setIntWarpCount(Integer.parseInt(txtCountWarp.getText()));
                        objConfiguration.setIntWeftCount(Integer.parseInt(txtCountWeft.getText()));
                        objConfiguration.setIntWarpCrimp(Integer.parseInt(txtCrimpWarp.getText()));
                        objConfiguration.setIntWeftCrimp(Integer.parseInt(txtCrimpWeft.getText()));
                        objConfiguration.setStrWarpUnit(cbUnitWarp.getValue().toString());
                        objConfiguration.setStrWeftUnit(cbUnitWeft.getValue().toString());
                        objFabricAction = new FabricAction();
                        txtGSM.setText(Double.toString(objFabricAction.calculateGSM(objConfiguration)));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSGSM"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        cbUnitWarp.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                     if(txtEPI.getText().equalsIgnoreCase("0") || txtEPI.getText().trim().isEmpty())
                        txtEPI.setText(Integer.toString(1));
                    if(txtPPI.getText().equalsIgnoreCase("0") || txtPPI.getText().trim().isEmpty())
                        txtPPI.setText(Integer.toString(1));
                    if(txtCountWarp.getText().equalsIgnoreCase("0") || txtCountWarp.getText().trim().isEmpty())
                        txtCountWarp.setText(Integer.toString(1));
                    if(txtCountWeft.getText().equalsIgnoreCase("0") || txtCountWeft.getText().trim().isEmpty())
                        txtCountWeft.setText(Integer.toString(1));
                    if(txtCrimpWarp.getText().equalsIgnoreCase("0") || txtCrimpWarp.getText().trim().isEmpty())
                        txtCrimpWarp.setText(Integer.toString(1));
                    if(txtCrimpWeft.getText().equalsIgnoreCase("0") || txtCrimpWeft.getText().trim().isEmpty())
                        txtCrimpWeft.setText(Integer.toString(1));
                    
                    objConfiguration.setIntEPI(Integer.parseInt(txtEPI.getText()));
                    objConfiguration.setIntPPI(Integer.parseInt(txtPPI.getText()));
                    objConfiguration.setIntWarpCount(Integer.parseInt(txtCountWarp.getText()));
                    objConfiguration.setIntWeftCount(Integer.parseInt(txtCountWeft.getText()));
                    objConfiguration.setIntWarpCrimp(Integer.parseInt(txtCrimpWarp.getText()));
                    objConfiguration.setIntWeftCrimp(Integer.parseInt(txtCrimpWeft.getText()));
                    objConfiguration.setStrWarpUnit(cbUnitWarp.getValue().toString());
                    objConfiguration.setStrWeftUnit(cbUnitWeft.getValue().toString());
                    objFabricAction = new FabricAction();
                    txtGSM.setText(Double.toString(objFabricAction.calculateGSM(objConfiguration)));
                    lblStatus.setText(objDictionaryAction.getWord("SUCCESSGSM"));
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        cbUnitWeft.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                     if(txtEPI.getText().equalsIgnoreCase("0") || txtEPI.getText().trim().isEmpty())
                        txtEPI.setText(Integer.toString(1));
                    if(txtPPI.getText().equalsIgnoreCase("0") || txtPPI.getText().trim().isEmpty())
                        txtPPI.setText(Integer.toString(1));
                    if(txtCountWarp.getText().equalsIgnoreCase("0") || txtCountWarp.getText().trim().isEmpty())
                        txtCountWarp.setText(Integer.toString(1));
                    if(txtCountWeft.getText().equalsIgnoreCase("0") || txtCountWeft.getText().trim().isEmpty())
                        txtCountWeft.setText(Integer.toString(1));
                    if(txtCrimpWarp.getText().equalsIgnoreCase("0") || txtCrimpWarp.getText().trim().isEmpty())
                        txtCrimpWarp.setText(Integer.toString(1));
                    if(txtCrimpWeft.getText().equalsIgnoreCase("0") || txtCrimpWeft.getText().trim().isEmpty())
                        txtCrimpWeft.setText(Integer.toString(1));
                    
                    objConfiguration.setIntEPI(Integer.parseInt(txtEPI.getText()));
                    objConfiguration.setIntPPI(Integer.parseInt(txtPPI.getText()));
                    objConfiguration.setIntWarpCount(Integer.parseInt(txtCountWarp.getText()));
                    objConfiguration.setIntWeftCount(Integer.parseInt(txtCountWeft.getText()));
                    objConfiguration.setIntWarpCrimp(Integer.parseInt(txtCrimpWarp.getText()));
                    objConfiguration.setIntWeftCrimp(Integer.parseInt(txtCrimpWeft.getText()));
                    objConfiguration.setStrWarpUnit(cbUnitWarp.getValue().toString());
                    objConfiguration.setStrWeftUnit(cbUnitWeft.getValue().toString());
                    objFabricAction = new FabricAction();
                    txtGSM.setText(Double.toString(objFabricAction.calculateGSM(objConfiguration)));
                    lblStatus.setText(objDictionaryAction.getWord("SUCCESSGSM"));
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityViewOld.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
    }
    public void importModule(){
        try {            
            containerGP.getChildren().clear();
            System.gc();
            
            Label caption = new Label(objDictionaryAction.getWord("IMPORT")+" "+objDictionaryAction.getWord("DATA"));
            caption.setId("caption");
            GridPane.setConstraints(caption, 0, 0);
            GridPane.setColumnSpan(caption, 3);
            containerGP.getChildren().add(caption);

            Separator sepHor1 = new Separator();
            sepHor1.setValignment(VPos.CENTER);
            GridPane.setConstraints(sepHor1, 0, 1);
            GridPane.setColumnSpan(sepHor1, 3);
            containerGP.getChildren().add(sepHor1);
        
            containerGP.add(new Label(objDictionaryAction.getWord("SAVEPATH")), 0, 2);
            final TextField path = new TextField(objConfiguration.strRoot);
            path.setEditable(false);
            containerGP.add(path, 1, 2);
            final Button btnBrowse = new Button(objDictionaryAction.getWord("BROWSE"));
            btnBrowse.setGraphic(new ImageView(objConfiguration.getStrColour()+"/browse.png"));
            containerGP.add(btnBrowse, 2, 2);
            
            Separator sepHor2 = new Separator();
            sepHor2.setValignment(VPos.CENTER);
            GridPane.setConstraints(sepHor2, 0, 3);
            GridPane.setColumnSpan(sepHor2, 3);
            containerGP.getChildren().add(sepHor2);
            
            final Button btnImport;
            final Button btnClear;
            
            btnImport = new Button(objDictionaryAction.getWord("IMPORT"));
            btnClear = new Button(objDictionaryAction.getWord("CLEAR"));
            
            btnImport.setGraphic(new ImageView(objConfiguration.getStrColour()+"/import.png"));
            btnClear.setGraphic(new ImageView(objConfiguration.getStrColour()+"/clear.png"));
                       
            btnImport.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPIMPORT")));
            btnClear.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLEAR")));
            
            btnImport.setDisable(true);
            btnClear.setDisable(false);
            
            containerGP.add(btnImport, 1, 4);
            containerGP.add(btnClear, 2, 4);
            
            btnBrowse.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    FileChooser objFileChooser = new FileChooser();
                    objFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ZIP(.zip)","*.zip"));
                    File selectedFile = objFileChooser.showOpenDialog(simulatorStage);
                    if(selectedFile == null){
                        return;
                    }else{
                        simulatorStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+selectedFile.getAbsolutePath()+"]");                        
                        btnImport.setDisable(false);
                    }
                    path.setText(selectedFile.getAbsolutePath());
                }
            });            
            
            btnImport.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    new MessageView(objConfiguration);
                    if(objConfiguration.getServicePasswordValid()){
                        objConfiguration.setServicePasswordValid(false);
                        if(path.getText()!=null && path.getText().toString().endsWith(".zip")){
                            try{
                                // unzip password protected zip with password "somePassword"
                                String source=path.getText();
                                String destination="c:\\mla_cad\\archive\\";
                                String password="somePassword";
                                File f=new File(destination);
                                if(!f.exists()){
                                    if(!f.mkdir()){
                                        return;
                                    }
                                }
                                ZipFile zipFile = new ZipFile(source);
                                if (zipFile.isEncrypted()) {
                                    zipFile.setPassword(password);
                                }
                                zipFile.extractAll(destination);
                                String[] files =getFolderContents(destination);
                                if(files!=null){
                                    for(String sqlFile:files){
                                        if(sqlFile.endsWith("Exported.sql")){
                                            if(!new DbUtility().importSQLFileData(sqlFile)){
                                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                                return;
                                            }
                                            new File(sqlFile).delete();
                                            System.out.println("SQL File successfully imported!");
                                        }
                                    }
                                    for(String p:files){
                                        String q=p.substring(p.lastIndexOf("\\")+1);
                                        File fil=new File(p);
                                        if(q.startsWith("ARTWORK")){
                                            new DbUtility().updateArtworkBlob(fil);
                                        }
                                        else if(q.startsWith("WEAVE")){
                                            new DbUtility().updateWeaveBlob(fil);
                                        }
                                        else if(q.startsWith("FABRIC")){
                                            new DbUtility().updateFabricBlob(fil);
                                        }
                                        else if(q.startsWith("YARN")){
                                            new DbUtility().updateYarnBlob(fil);
                                        }
                                        fil.delete();
                                    }
                                    lblStatus.setText(objDictionaryAction.getWord("DONE"));
                                }
                            } catch (ZipException zipEx) {
                                new Logging("SEVERE",UtilityViewOld.class.getName(),zipEx.toString(),zipEx);
                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                            } catch (Exception ex) {
                                new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                            }
                        }else{
                            lblStatus.setText(objDictionaryAction.getWord("INVALIDDATA"));
                        }
                    }
                }
            });
            btnClear.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    System.gc();
                    btnImport.setDisable(true);
                    btnClear.setDisable(false);
                }
            });
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityViewOld.class.getName(),"Import data module"+ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }

    /* Get all files from destination Extracted folder */
    public String[] getFolderContents(String folder){
	// returns absolute path of files
        File dir = new File(folder);
        final String[] exts={".sql", ".bin"};
	String[] files=dir.list(new FilenameFilter() { 
            public boolean accept(File dir, String filename){
                for(String e: exts){
                    if(filename.endsWith(e))
                            return true;
                }
                return false;
            }
	});
	
	if(files!=null){
            // returns complete path of files
            for(int i=0; i<files.length; i++){
                    files[i]=folder+files[i];
            }
	}
	return files;
    }
    
    public void exportModule(){
        try {            
            containerGP.getChildren().clear();
            System.gc();
            
            Label caption = new Label(objDictionaryAction.getWord("EXPORT")+" "+objDictionaryAction.getWord("DATA"));
            caption.setId("caption");
            GridPane.setConstraints(caption, 0, 0);
            GridPane.setColumnSpan(caption, 4);
            containerGP.getChildren().add(caption);

            Separator sepHor1 = new Separator();
            sepHor1.setValignment(VPos.CENTER);
            GridPane.setConstraints(sepHor1, 0, 1);
            GridPane.setColumnSpan(sepHor1, 4);
            containerGP.getChildren().add(sepHor1);
        
            final CheckBox fabricDataCB = new CheckBox(objDictionaryAction.getWord("FABRIC")+" "+objDictionaryAction.getWord("DATA"));
            final CheckBox artworkDataCB = new CheckBox(objDictionaryAction.getWord("ARTWORK")+" "+objDictionaryAction.getWord("DATA"));
            final CheckBox weaveDataCB = new CheckBox(objDictionaryAction.getWord("WEAVE")+" "+objDictionaryAction.getWord("DATA"));
            
            fabricDataCB.setGraphic(new ImageView(objConfiguration.getStrColour()+"/fabric_library.png"));
            artworkDataCB.setGraphic(new ImageView(objConfiguration.getStrColour()+"/artwork_library.png"));
            weaveDataCB.setGraphic(new ImageView(objConfiguration.getStrColour()+"/weave_library.png"));
            
            //fabricDataCB.setDisable(true);
            
            containerGP.add(fabricDataCB, 0, 2);
            containerGP.add(artworkDataCB, 0, 3);
            containerGP.add(weaveDataCB, 0, 4);
            
            Separator sepHor2 = new Separator();
            sepHor2.setValignment(VPos.CENTER);
            GridPane.setConstraints(sepHor2, 0, 5);
            GridPane.setColumnSpan(sepHor2, 4);
            containerGP.getChildren().add(sepHor2);
            
            containerGP.add(new Label(objDictionaryAction.getWord("SAVEPATH")), 0, 6);
            final TextField path = new TextField(objConfiguration.strRoot);
            path.setEditable(false);
            containerGP.add(path, 1, 6);
            final Button btnBrowse = new Button(objDictionaryAction.getWord("BROWSE"));
            btnBrowse.setGraphic(new ImageView(objConfiguration.getStrColour()+"/browse.png"));
            containerGP.add(btnBrowse, 2, 6);
            
            btnBrowse.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    DirectoryChooser directoryChooser=new DirectoryChooser();
                    simulatorStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORT")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
                    File selectedDirectory = directoryChooser.showDialog(simulatorStage);
                    if(selectedDirectory == null)
                        return;
                    else
                        simulatorStage.setTitle(objDictionaryAction.getWord("PROJECT")+": ["+selectedDirectory.getAbsolutePath()+"]");    
                    path.setText(selectedDirectory.getPath());
                }
            });            
            
            Separator sepHor3 = new Separator();
            sepHor3.setValignment(VPos.CENTER);
            GridPane.setConstraints(sepHor3, 0, 7);
            GridPane.setColumnSpan(sepHor3, 4);
            containerGP.getChildren().add(sepHor3);
            
            final Button btnExport;
            final Button btnDelete;
            final Button btnExportDelete;
            final Button btnClear;
            
            btnExport = new Button(objDictionaryAction.getWord("EXPORT"));
            btnDelete = new Button(objDictionaryAction.getWord("DELETE"));
            btnExportDelete = new Button(objDictionaryAction.getWord("EXPORTDELETE"));
            btnClear = new Button(objDictionaryAction.getWord("CLEAR"));
            
            btnExport.setGraphic(new ImageView(objConfiguration.getStrColour()+"/export.png"));
            btnExportDelete.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
            btnDelete.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
            btnClear.setGraphic(new ImageView(objConfiguration.getStrColour()+"/clear.png"));
                       
            btnExport.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEXPORT")));
            btnExportDelete.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEXPORTDELETE")));
            btnDelete.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDELETE")));
            btnClear.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLEAR")));
            
            btnExport.setDisable(true);
            btnExportDelete.setDisable(true);
            btnDelete.setDisable(true);
            btnClear.setDisable(false);
            
            fabricDataCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov,
                    Boolean old_val, Boolean new_val) {
                    if(fabricDataCB.isSelected() || artworkDataCB.isSelected() || weaveDataCB.isSelected()){
                        btnExport.setDisable(false);
                        btnDelete.setDisable(false);
                        btnExportDelete.setDisable(false);
                        btnClear.setDisable(false);
                    } else{
                        btnExport.setDisable(true);
                        btnDelete.setDisable(true);
                        btnExportDelete.setDisable(true);
                        btnClear.setDisable(false);
                        lblStatus.setText(objDictionaryAction.getWord("NOITEM"));
                    }
                }
            });
            artworkDataCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov,
                    Boolean old_val, Boolean new_val) {
                    if(fabricDataCB.isSelected() || artworkDataCB.isSelected() || weaveDataCB.isSelected()){
                        btnExport.setDisable(false);
                        btnDelete.setDisable(false);
                        btnExportDelete.setDisable(false);
                        btnClear.setDisable(false);
                    } else{
                        btnExport.setDisable(true);
                        btnDelete.setDisable(true);
                        btnExportDelete.setDisable(true);
                        btnClear.setDisable(false);
                        lblStatus.setText(objDictionaryAction.getWord("NOITEM"));
                    }
                }
            });            
            weaveDataCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov,
                    Boolean old_val, Boolean new_val) {
                    if(fabricDataCB.isSelected() || artworkDataCB.isSelected() || weaveDataCB.isSelected()){
                        btnExport.setDisable(false);
                        btnDelete.setDisable(false);
                        btnExportDelete.setDisable(false);
                        btnClear.setDisable(false);
                    } else{
                        btnExport.setDisable(true);
                        btnDelete.setDisable(true);
                        btnExportDelete.setDisable(true);
                        btnClear.setDisable(false);
                        lblStatus.setText(objDictionaryAction.getWord("NOITEM"));
                    }
                }
            });
            
            btnExport.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    new MessageView(objConfiguration);
                    if(objConfiguration.getServicePasswordValid()){
                        objConfiguration.setServicePasswordValid(false);
                        if(fabricDataCB.isSelected() || artworkDataCB.isSelected() || weaveDataCB.isSelected()){
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                            Date date = new Date();
                            String currentDate = dateFormat.format(date);
                            String savePath = System.getProperty("user.dir");
                            if(path.getText()!=null)
                                savePath = path.getText();
                            savePath = savePath+"\\"+objDictionaryAction.getWord("PROJECT")+"_"+currentDate+"\\";
                            File file = new File(savePath);
                            if (!file.exists()) {
                                if (!file.mkdir())
                                    savePath = System.getProperty("user.dir");
                            }
                            //savePath = savePath + "\\"+currentDate+".sql";
                            
                            String zipFilePath=savePath+"Exported.zip";
                            savePath = savePath + "Exported.sql";
                            ArrayList<File> filesToZip=new ArrayList<>();
                        
                            System.err.println("Path: "+savePath);                     
                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(savePath))) {
                                btnExport.setDisable(false);
                                btnDelete.setDisable(false);
                                btnExportDelete.setDisable(false);
                                btnClear.setDisable(false);
                                String content = "SET NAMES utf8; \n USE bunai; \n SET autocommit = 0; \n";
                                if(fabricDataCB.isSelected())
                                    content += new DbUtility().reExportFabric(objConfiguration, savePath.substring(0, savePath.indexOf("Exported.sql")), filesToZip);
                                    //content += new DbUtility().exportFabric(objConfiguration);
                                if(artworkDataCB.isSelected())
                                    content += new DbUtility().reExportDesign(objConfiguration, savePath.substring(0, savePath.indexOf("Exported.sql")), filesToZip);
                                    //content += new DbUtility().exportArtwork(objConfiguration);
                                if(weaveDataCB.isSelected())
                                    content += new DbUtility().reExportWeave(objConfiguration, savePath.substring(0, savePath.indexOf("Exported.sql")), filesToZip);
                                    //content += new DbUtility().exportWeave(objConfiguration);
                                content+="COMMIT;";
                                bw.write(content);
                                // no need to close it.
                                bw.close();
                                filesToZip.add(new File(savePath));
                                new EncryptZip(zipFilePath, filesToZip, "somePassword");
                                for(int a=0; a<filesToZip.size(); a++)
                                    filesToZip.get(a).delete();
                                System.out.println("Done");
                            } catch (IOException ex) {
                                new Logging("SEVERE",UtilityViewOld.class.getName(),"Export Data"+ex.getMessage(),ex);
                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                            } catch (SQLException ex) {
                                new Logging("SEVERE",UtilityViewOld.class.getName(),"Export Data"+ex.getMessage(),ex);
                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                            }
                            lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+savePath);
                        } else{
                            btnExport.setDisable(true);
                            btnDelete.setDisable(true);
                            btnExportDelete.setDisable(true);
                            btnClear.setDisable(false);
                            lblStatus.setText(objDictionaryAction.getWord("NOITEM"));
                        }
                    }
                }
            });
            btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                   new MessageView(objConfiguration);
                    if(objConfiguration.getServicePasswordValid()){
                        objConfiguration.setServicePasswordValid(false);
                        if(fabricDataCB.isSelected() || artworkDataCB.isSelected() || weaveDataCB.isSelected()){
                            try {
                                btnExport.setDisable(false);
                                btnDelete.setDisable(false);
                                btnExportDelete.setDisable(false);
                                btnClear.setDisable(false);
                            
                                if(fabricDataCB.isSelected()){
                                    objFabric = new Fabric();
                                    objFabric.setObjConfiguration(objConfiguration);
                                    objFabric.setStrCondition("");
                                    objFabric.setStrSearchBy("");
                                    objFabric.setStrOrderBy("Name");
                                    objFabric.setStrDirection("Ascending");
                                    List lstFabric=null;
                                    List lstFabricDeatails = new ArrayList();
                                    objFabricAction = new FabricAction();
                                    lstFabricDeatails = objFabricAction.lstImportFabric(objFabric);
                                    if(lstFabricDeatails.size()>0){
                                        for (int i=0, j = lstFabricDeatails.size(); i<j; i++){
                                            lstFabric = (ArrayList)lstFabricDeatails.get(i);
                                            String strFabricID = lstFabric.get(0).toString();
                                            String strFabricAccess = lstFabric.get(29).toString();
                                            if(!strFabricAccess.equalsIgnoreCase("Public")){
                                                objFabricAction = new FabricAction();
                                                objFabricAction.clearFabricArtwork(strFabricID);
                                                YarnAction objYarnAction = new YarnAction();
                                                objYarnAction.clearFabricYarn(strFabricID);
                                                objFabricAction = new FabricAction();
                                                objFabricAction.clearFabricPallets(strFabricID);
                                                objFabricAction = new FabricAction();
                                                objFabricAction.clearFabric(strFabricID);
                                            }
                                        }
                                    }
                                }if(artworkDataCB.isSelected()){
                                    objArtwork = new Artwork();
                                    objArtwork.setObjConfiguration(objConfiguration);
                                    objArtwork.setStrCondition("");
                                    objArtwork.setStrSearchBy("");
                                    objArtwork.setStrOrderBy("Name");
                                    objArtwork.setStrDirection("Ascending");
                                    List lstArtwork=null;
                                    List lstArtworkDeatails = new ArrayList();
                                    objArtworkAction = new ArtworkAction();
                                    lstArtworkDeatails = objArtworkAction.lstImportArtwork(objArtwork);
                                    if(lstArtworkDeatails.size()>0){
                                        for (int i=0, j = lstArtworkDeatails.size(); i<j;i++){
                                            lstArtwork = (ArrayList)lstArtworkDeatails.get(i);
                                            String strArtworkID = lstArtwork.get(0).toString();
                                            String strArtworkAccess = lstArtwork.get(6).toString();
                                            objArtworkAction = new ArtworkAction();
                                            System.err.println("ID= "+strArtworkID+"\nAccess= "+strArtworkAccess+"\nDefualt= "+new IDGenerator().getUserAcess("ARTWORK_LIBRARY")+"\nSize= "+objArtworkAction.countArtworkUsage(strArtworkID));
                                            if(objArtworkAction.countArtworkUsage(strArtworkID)==0 && !strArtworkAccess.equalsIgnoreCase(new IDGenerator().getUserAcess("ARTWORK_LIBRARY"))){
                                                objArtworkAction = new ArtworkAction();
                                                objArtworkAction.clearArtwork(strArtworkID);
                                            }
                                        }
                                    }
                                }if(weaveDataCB.isSelected()){
                                    objWeave = new Weave();
                                    objWeave.setObjConfiguration(objConfiguration);
                                    objWeave.setStrCondition("");
                                    objWeave.setStrSearchBy("All");
                                    objWeave.setStrOrderBy("Name");
                                    objWeave.setStrDirection("Ascending");
                                    List lstWeave=null;
                                    List lstWeaveDeatails = new ArrayList();
                                    objWeaveAction = new WeaveAction(objWeave,true);
                                    lstWeaveDeatails = objWeaveAction.lstImportWeave(objWeave);
                                    if(lstWeaveDeatails.size()>0){
                                        for (int i=0, j=lstWeaveDeatails.size();i<j;i++){
                                            lstWeave = (ArrayList)lstWeaveDeatails.get(i);
                                            String strWeaveID = lstWeave.get(0).toString();
                                            String strWeaveAccess = lstWeave.get(15).toString();
                                            objWeaveAction = new WeaveAction();
                                            System.err.println("ID= "+strWeaveID+"\nAccess= "+strWeaveAccess+"\nDefualt= "+new IDGenerator().getUserAcess("WEAVE_LIBRARY")+"\nSize= "+objWeaveAction.countWeaveUsage(strWeaveID));
                                            objWeaveAction = new WeaveAction();
                                            if(objWeaveAction.countWeaveUsage(strWeaveID)==0 && !strWeaveAccess.equalsIgnoreCase(new IDGenerator().getUserAcess("WEAVE_LIBRARY"))){
                                                objWeaveAction = new WeaveAction();
                                                objWeaveAction.clearWeave(strWeaveID);
                                            }
                                        }
                                    }
                                }
                                lblStatus.setText(objDictionaryAction.getWord("ACTIONDELETE"));
                            } catch (SQLException ex) {
                                new Logging("SEVERE",UtilityViewOld.class.getName(),"Delete Data"+ex.getMessage(),ex);
                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                            }
                        } else{
                            btnExport.setDisable(true);
                            btnDelete.setDisable(true);
                            btnExportDelete.setDisable(true);
                            btnClear.setDisable(false);
                            lblStatus.setText(objDictionaryAction.getWord("NOITEM"));
                        }
                    }
                }
            });
            btnExportDelete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    new MessageView(objConfiguration);
                    if(objConfiguration.getServicePasswordValid()){
                        objConfiguration.setServicePasswordValid(false);
                        if(fabricDataCB.isSelected() || artworkDataCB.isSelected() || weaveDataCB.isSelected()){
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                            Date date = new Date();
                            String currentDate = dateFormat.format(date);
                            String savePath = System.getProperty("user.dir");
                            if(path.getText()!=null)
                                savePath = path.getText();
                            savePath = savePath+"\\"+objDictionaryAction.getWord("PROJECT")+"_"+currentDate+"\\";
                            File file = new File(savePath);
                            if (!file.exists()) {
                                if (!file.mkdir())
                                    savePath = System.getProperty("user.dir");
                            }
                            //savePath = savePath + "\\"+currentDate+".sql";
                            String zipFilePath=savePath+"Exported.zip";
                            savePath = savePath + "Exported.sql";
                            ArrayList<File> filesToZip=new ArrayList<>();
                            
                            System.err.println("Path: "+savePath);                     
                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(savePath))) {
                                btnExport.setDisable(false);
                                btnDelete.setDisable(false);
                                btnExportDelete.setDisable(false);
                                btnClear.setDisable(false);
                                String content = "SET autocommit = 0;\n";
                                if(fabricDataCB.isSelected())
                                    content += new DbUtility().reExportFabric(objConfiguration, savePath.substring(0, savePath.indexOf("Exported.sql")), filesToZip);
                                    //content += new DbUtility().exportFabric(objConfiguration);
                                if(artworkDataCB.isSelected())
                                    content += new DbUtility().reExportDesign(objConfiguration, savePath.substring(0, savePath.indexOf("Exported.sql")), filesToZip);
                                    //content += new DbUtility().exportArtwork(objConfiguration);
                                if(weaveDataCB.isSelected())
                                    content += new DbUtility().reExportWeave(objConfiguration, savePath.substring(0, savePath.indexOf("Exported.sql")), filesToZip);
                                    //content += new DbUtility().exportWeave(objConfiguration);
                                content+="COMMIT;";
                                bw.write(content);
                                // no need to close it.
                                bw.close();
                                filesToZip.add(new File(savePath));
                                new EncryptZip(zipFilePath, filesToZip, "somePassword");
                                for(int a=0; a<filesToZip.size(); a++)
                                    filesToZip.get(a).delete();
                                System.out.println("Backup Done");
                            
                                if(fabricDataCB.isSelected()){
                                    objFabric = new Fabric();
                                    objFabric.setObjConfiguration(objConfiguration);
                                    objFabric.setStrCondition("");
                                    objFabric.setStrSearchBy("");
                                    objFabric.setStrOrderBy("Name");
                                    objFabric.setStrDirection("Ascending");
                                    List lstFabric=null;
                                    List lstFabricDeatails = new ArrayList();
                                    objFabricAction = new FabricAction();
                                    lstFabricDeatails = objFabricAction.lstImportFabric(objFabric);
                                    if(lstFabricDeatails.size()>0){
                                        for (int i=0, j = lstFabricDeatails.size(); i<j; i++){
                                            lstFabric = (ArrayList)lstFabricDeatails.get(i);
                                            String strFabricID = lstFabric.get(0).toString();
                                            String strFabricAccess = lstFabric.get(29).toString();
                                            if(!strFabricAccess.equalsIgnoreCase("Public")){
                                                objFabricAction = new FabricAction();
                                                objFabricAction.clearFabricArtwork(strFabricID);
                                                YarnAction objYarnAction = new YarnAction();
                                                objYarnAction.clearFabricYarn(strFabricID);
                                                objFabricAction = new FabricAction();
                                                objFabricAction.clearFabricPallets(strFabricID);
                                                objFabricAction = new FabricAction();
                                                objFabricAction.clearFabric(strFabricID);
                                            }
                                        }
                                    }
                                }if(artworkDataCB.isSelected()){
                                    objArtwork = new Artwork();
                                    objArtwork.setObjConfiguration(objConfiguration);
                                    objArtwork.setStrCondition("");
                                    objArtwork.setStrSearchBy("");
                                    objArtwork.setStrOrderBy("Name");
                                    objArtwork.setStrDirection("Ascending");
                                    List lstArtwork=null;
                                    List lstArtworkDeatails = new ArrayList();
                                    objArtworkAction = new ArtworkAction();
                                    lstArtworkDeatails = objArtworkAction.lstImportArtwork(objArtwork);
                                    if(lstArtworkDeatails.size()>0){
                                        for (int i=0, j = lstArtworkDeatails.size(); i<j;i++){
                                            lstArtwork = (ArrayList)lstArtworkDeatails.get(i);
                                            String strArtworkID = lstArtwork.get(0).toString();
                                            String strArtworkAccess = lstArtwork.get(6).toString();
                                            objArtworkAction = new ArtworkAction();
                                            System.err.println("ID= "+strArtworkID+"\nAccess= "+strArtworkAccess+"\nDefualt= "+new IDGenerator().getUserAcess("ARTWORK_LIBRARY")+"\nSize= "+objArtworkAction.countArtworkUsage(strArtworkID));
                                            if(objArtworkAction.countArtworkUsage(strArtworkID)==0 && !strArtworkAccess.equalsIgnoreCase(new IDGenerator().getUserAcess("ARTWORK_LIBRARY"))){
                                                objArtworkAction = new ArtworkAction();
                                                objArtworkAction.clearArtwork(strArtworkID);
                                            }
                                        }
                                    }
                                }if(weaveDataCB.isSelected()){
                                    objWeave = new Weave();
                                    objWeave.setObjConfiguration(objConfiguration);
                                    objWeave.setStrCondition("");
                                    objWeave.setStrSearchBy("All");
                                    objWeave.setStrOrderBy("Name");
                                    objWeave.setStrDirection("Ascending");  
                                    List lstWeave=null;
                                    List lstWeaveDeatails = new ArrayList();
                                    objWeaveAction = new WeaveAction(objWeave,true);
                                    lstWeaveDeatails = objWeaveAction.lstImportWeave(objWeave);
                                    if(lstWeaveDeatails.size()>0){
                                        for (int i=0, j=lstWeaveDeatails.size();i<j;i++){
                                            lstWeave = (ArrayList)lstWeaveDeatails.get(i);
                                            String strWeaveID = lstWeave.get(0).toString();
                                            String strWeaveAccess = lstWeave.get(15).toString();
                                            objWeaveAction = new WeaveAction();
                                            System.err.println("ID= "+strWeaveID+"\nAccess= "+strWeaveAccess+"\nDefualt= "+new IDGenerator().getUserAcess("WEAVE_LIBRARY")+"\nSize= "+objWeaveAction.countWeaveUsage(strWeaveID));
                                            objWeaveAction = new WeaveAction();
                                            if(objWeaveAction.countWeaveUsage(strWeaveID)==0 && !strWeaveAccess.equalsIgnoreCase(new IDGenerator().getUserAcess("WEAVE_LIBRARY"))){
                                                objWeaveAction = new WeaveAction();
                                                objWeaveAction.clearWeave(strWeaveID);
                                            }
                                        }
                                    }
                                }
                                lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+savePath);
                            } catch (IOException ex) {
                                new Logging("SEVERE",UtilityViewOld.class.getName(),"Export and delete Data"+ex.getMessage(),ex);
                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                            } catch (SQLException ex) {
                                new Logging("SEVERE",UtilityViewOld.class.getName(),"Export and Delete Data"+ex.getMessage(),ex);
                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                            }
                        } else{
                            btnExport.setDisable(true);
                            btnDelete.setDisable(true);
                            btnExportDelete.setDisable(true);
                            btnClear.setDisable(false);
                            lblStatus.setText(objDictionaryAction.getWord("NOITEM"));
                        }
                    }
                }
            });
            btnClear.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    System.gc();
                    fabricDataCB.setSelected(false);
                    artworkDataCB.setSelected(false);
                    weaveDataCB.setSelected(false);
                    btnExport.setDisable(true);
                    btnDelete.setDisable(true);
                    btnExportDelete.setDisable(true);
                    btnClear.setDisable(false);
                }
            });
            containerGP.add(btnExport, 0, 8);
            containerGP.add(btnExportDelete, 1, 8);
            containerGP.add(btnDelete, 2, 8);
            containerGP.add(btnClear, 3, 8);            
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }

    public void deviceModule(){
        try {            
            containerGP.getChildren().clear();
            System.gc();
            final ObservableList<Device> deviceData = FXCollections.observableArrayList();
            
            Label caption = new Label(objDictionaryAction.getWord("APPLICATIONINTEGRATION"));
            caption.setId("caption");
            GridPane.setConstraints(caption, 0, 0);
            GridPane.setColumnSpan(caption, 5);
            containerGP.getChildren().add(caption);

            Separator sepHor1 = new Separator();
            sepHor1.setValignment(VPos.CENTER);
            GridPane.setConstraints(sepHor1, 0, 1);
            GridPane.setColumnSpan(sepHor1, 5);
            containerGP.getChildren().add(sepHor1);
        
            containerGP.add(new Label(objDictionaryAction.getWord("APPLICATION")+" "+objDictionaryAction.getWord("NAME")), 0, 2);
            containerGP.add(new Label(objDictionaryAction.getWord("APPLICATION")+" "+objDictionaryAction.getWord("TYPE")), 2, 2);
            containerGP.add(new Label(objDictionaryAction.getWord("APPLICATION")+" "+objDictionaryAction.getWord("PATH")), 0, 3);
            
            final TextField name = new TextField();
            containerGP.add(name, 1, 2);
            final ComboBox type = new ComboBox();
            type.getItems().addAll("Designing S/W","Puncing M/C");  
            type.setValue("Designing S/W");
            containerGP.add(type, 3, 2);
            final TextField path = new TextField();
            containerGP.add(path, 1, 3);
            final Button btnBrowse = new Button(objDictionaryAction.getWord("BROWSE"));
            btnBrowse.setGraphic(new ImageView(objConfiguration.getStrColour()+"/browse.png"));
            containerGP.add(btnBrowse, 2, 3);
            
            btnBrowse.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    FileChooser objFileChooser = new FileChooser();
                    objFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Executable(.exe)","*.exe"));
                    objFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Windows Installer Package(.msi)","*.msi"));
                    objFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Binary Executable (.bin)","*.bin"));
                    File objFile = objFileChooser.showOpenDialog(simulatorStage);
                    System.err.println(objFile);
                    path.setText(objFile.getAbsolutePath());
                    name.setText(objFile.getAbsolutePath().substring(objFile.getAbsolutePath().lastIndexOf("\\"),objFile.getAbsolutePath().lastIndexOf(".")));
                }
            });            
            
            final Button btnAdd;
            final Button btnUpdate;
            final Button btnDelete;
            final Button btnLaunch;
            final Button btnClose;
           
            btnAdd = new Button(objDictionaryAction.getWord("ADDNEW"));
            btnUpdate = new Button(objDictionaryAction.getWord("UPDATE"));
            btnDelete = new Button(objDictionaryAction.getWord("DELETE"));
            btnLaunch = new Button(objDictionaryAction.getWord("RUN"));
            btnClose = new Button(objDictionaryAction.getWord("CLEAR"));
            
            //btnUpdate.setMaxWidth(Double.MAX_VALUE);
            //btnLaunch.setMaxWidth(Double.MAX_VALUE);
            //btnClose.setMaxWidth(Double.MAX_VALUE);
            
            btnAdd.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
            btnUpdate.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
            btnDelete.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
            btnLaunch.setGraphic(new ImageView(objConfiguration.getStrColour()+"/preview.png"));            
            btnClose.setGraphic(new ImageView(objConfiguration.getStrColour()+"/clear.png"));
                       
            btnAdd.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPADDNEW")));
            btnUpdate.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPUPDATE")));
            btnUpdate.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDELETE")));
            btnLaunch.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPRUN")));
            btnClose.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLEAR")));
            
            btnAdd.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
            btnLaunch.setDisable(false);
            btnClose.setDefaultButton(true);
            
            btnLaunch.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        if(!path.getText().isEmpty()){
                            /*
                            Runtime rt = Runtime.getRuntime();
                            Process process_record=new ProcessBuilder("C:\\Users\\Laxita\\Documents\\NetBeansProjects\\vspeech\\src\\sounds\\recorder.exe").start();
                            */
                            Runtime rt = Runtime.getRuntime();
                            Process process_record=new ProcessBuilder(path.getText()).start();
                        }else{
                            lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                        }
                    } catch (IOException ex) {
                        new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
                        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    }
                }
            });
            btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    new MessageView(objConfiguration);
                    if(objConfiguration.getServicePasswordValid()){
                        objConfiguration.setServicePasswordValid(false);
                        try {
                            if(!objDevice.getStrDeviceId().isEmpty()){
                                UtilityAction objUtilityAction = new UtilityAction();
                                objUtilityAction.clearDevice(objDevice);
                                deviceData.remove(index);
                            
                                index = -1;
                                type.setValue("Designing S/W");
                                name.setText("");
                                path.setText("");
                            
                            }else{
                                lblStatus.setText(objDictionaryAction.getWord("WRONGINPUT"));
                            }
                        } catch (SQLException ex) {
                            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        } catch (Exception ex) {
                            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                }
            });
            btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    new MessageView(objConfiguration);
                    if(objConfiguration.getServicePasswordValid()){
                        objConfiguration.setServicePasswordValid(false);
                        try {
                            if(!name.getText().isEmpty() && !path.getText().isEmpty()){
                                objDevice.setStrDeviceType(type.getValue().toString());
                                objDevice.setStrDeviceName(name.getText().replace("\\","\\\\"));
                                objDevice.setStrDevicePath(path.getText().replace("\\","\\\\"));
                                UtilityAction objUtilityAction = new UtilityAction();
                                objUtilityAction.resetDevice(objDevice);
                                deviceData.set(index, objDevice);
                            }else{
                                lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                            }
                        } catch (SQLException ex) {
                            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        } catch (Exception ex) {
                            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                }
            });
            btnAdd.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    new MessageView(objConfiguration);
                    if(objConfiguration.getServicePasswordValid()){
                        objConfiguration.setServicePasswordValid(false);
                        try {
                            if(!name.getText().isEmpty() && !path.getText().isEmpty()){
                                String strDeviceID=new IDGenerator().getIDGenerator("DEVICE_LIBRARY", objConfiguration.getObjUser().getStrUserID());
                                objDevice = new Device(strDeviceID, type.getValue().toString(), name.getText().replace("\\","\\\\"), path.getText().replace("\\","\\\\"));
                                objDevice.setObjConfiguration(objConfiguration);
                                UtilityAction objUtilityAction = new UtilityAction();
                                objUtilityAction.setDevice(objDevice);
                                index = deviceData.size();
                                deviceData.add(index, objDevice);
                            }else{
                                lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                            }
                        } catch (SQLException ex) {
                            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        } catch (Exception ex) {
                            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                }
            });
            btnClose.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    System.gc();
                    btnAdd.setDisable(true);
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                    btnLaunch.setDisable(true);
                    btnClose.setDisable(true);
                    
                    index = -1;
                    type.setValue("Designing S/W");
                    name.setText("");
                    path.setText("");
                }
            });
            
            containerGP.add(btnAdd, 0, 5);
            containerGP.add(btnUpdate, 1, 5);
            containerGP.add(btnDelete, 2, 5);
            containerGP.add(btnLaunch, 3, 5);
            containerGP.add(btnClose, 4, 5);
            
            Separator sepHor2 = new Separator();
            sepHor2.setValignment(VPos.CENTER);
            GridPane.setConstraints(sepHor2, 0, 6);
            GridPane.setColumnSpan(sepHor2, 5);
            containerGP.getChildren().add(sepHor2);
        
            HBox tsHB = new HBox();
            final ToggleGroup actionTG = new ToggleGroup();
            final RadioButton newRB = new RadioButton(objDictionaryAction.getWord("ADDNEW")+" "+objDictionaryAction.getWord("APPLICATION"));        
            newRB.setToggleGroup(actionTG);
            newRB.setUserData("new");
            newRB.setSelected(true);
            final RadioButton oldRB = new RadioButton(objDictionaryAction.getWord("SELECTTO")+" "+objDictionaryAction.getWord("UPDATE"));
            oldRB.setToggleGroup(actionTG);
            oldRB.setUserData("old");
            tsHB.getChildren().addAll(newRB,oldRB);
            tsHB.setStyle("-fx-text-fill: #FF0000;");
            actionTG.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                public void changed(ObservableValue<? extends Toggle> ov,Toggle old_toggle, Toggle new_toggle) {
                    if (actionTG.getSelectedToggle() != null) {
                        String action = actionTG.getSelectedToggle().getUserData().toString().trim();
                        if(action.equalsIgnoreCase("new")){
                            btnAdd.setDisable(false);
                            btnUpdate.setDisable(true);
                            btnDelete.setDisable(true);
                            btnLaunch.setDisable(false);
                            btnClose.setDisable(false);
                        }else{
                            btnAdd.setDisable(true);
                            btnUpdate.setDisable(true);
                            btnDelete.setDisable(true);
                            btnLaunch.setDisable(true);
                            btnClose.setDisable(false);
                        }
                    }
                }
            });        
            containerGP.add(tsHB, 0, 7, 5, 1);
           
            Separator sepHor3 = new Separator();
            sepHor3.setValignment(VPos.CENTER);
            GridPane.setConstraints(sepHor3, 0, 8);
            GridPane.setColumnSpan(sepHor3, 5);
            containerGP.getChildren().add(sepHor3);
        
            //System.err.print(objFabric.getLstYarn().size());
            TableView<Device> deviceTable = new TableView<Device>();
            ScrollPane deviceList = new ScrollPane();
            deviceTable.setPrefSize(objConfiguration.WIDTH/2,objConfiguration.HEIGHT/4);
            
            UtilityAction objUtilityAction = new UtilityAction();
            objDevice = new Device(null, null, null, null);
            objDevice.setObjConfiguration(objConfiguration);
            deviceData.addAll(objUtilityAction.getDevices(objDevice));
            TableColumn selectCol = new TableColumn("Device#");
            TableColumn typeCol = new TableColumn("Type");
            TableColumn nameCol = new TableColumn("Name");
            TableColumn pathCol = new TableColumn("Path");
            selectCol.setCellValueFactory(new PropertyValueFactory<Device,String>("strDeviceId"));
            typeCol.setCellValueFactory(new PropertyValueFactory<Device,String>("strDeviceType"));
            nameCol.setCellValueFactory(new PropertyValueFactory<Device,String>("strDeviceName"));
            pathCol.setCellValueFactory(new PropertyValueFactory<Device,String>("strDevicePath"));
            deviceTable.setItems(deviceData);
            deviceTable.setEditable(true);
            deviceTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            deviceTable.getColumns().addAll(selectCol, typeCol, nameCol, pathCol);
            deviceTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Device>() {
                @Override
                public void changed(ObservableValue<? extends Device> ov, Device t, Device t1) {
                    index = deviceData.indexOf(t1);
                    objDevice = new Device(t1.getStrDeviceId(), t1.getStrDeviceType(), t1.getStrDeviceName(), t1.getStrDevicePath());
                    objDevice.setObjConfiguration(objConfiguration);
                    type.setValue(objDevice.getStrDeviceType());
                    name.setText(objDevice.getStrDeviceName());
                    path.setText(objDevice.getStrDevicePath());
                    
                    btnAdd.setDisable(true);
                    btnUpdate.setDisable(false);
                    btnDelete.setDisable(false);
                    btnLaunch.setDisable(false);
                    btnClose.setDisable(false);
                    oldRB.setSelected(true);
                    //System.out.println(t1.getThreadColor()+"selection "+index+" changed"+Color.web(t1.getThreadColor().substring(1))+":"+Color.valueOf(t1.getThreadColor().substring(1)));                        
                }
            });
            
            deviceList.setContent(deviceTable);
            GridPane.setConstraints(deviceList, 0, 9);
            GridPane.setColumnSpan(deviceList, 5);
            containerGP.getChildren().add(deviceList);
            
        } catch (SQLException ex) {
            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }

    public void simulatorModule(){
        try {            
            containerGP.getChildren().clear();
            System.gc();
            final ObservableList<Simulator> simulatorData = FXCollections.observableArrayList();
            
            Label caption = new Label(objDictionaryAction.getWord("SIMULATOR"));
            caption.setId("caption");
            GridPane.setConstraints(caption, 0, 0);
            GridPane.setColumnSpan(caption, 5);
            containerGP.getChildren().add(caption);

            Separator sepHor1 = new Separator();
            sepHor1.setValignment(VPos.CENTER);
            GridPane.setConstraints(sepHor1, 0, 1);
            GridPane.setColumnSpan(sepHor1, 5);
            containerGP.getChildren().add(sepHor1);
        
            containerGP.add(new Label(objDictionaryAction.getWord("SIMULATOR")+" "+objDictionaryAction.getWord("NAME")), 0, 2);
            containerGP.add(new Label(objDictionaryAction.getWord("SIMULATOR")+" "+objDictionaryAction.getWord("FABRICTYPE")), 2, 2);
            containerGP.add(new Label(objDictionaryAction.getWord("PPI")), 0, 3);
            containerGP.add(new Label(objDictionaryAction.getWord("EPI")), 2, 3);
            containerGP.add(new Label(objDictionaryAction.getWord("RESOLUTION")), 0, 4);
            containerGP.add(new Label(objDictionaryAction.getWord("DPI")), 2, 4);
            containerGP.add(new Label(objDictionaryAction.getWord("DEFAULTYARN")), 0, 5);
            containerGP.add(new Label(objDictionaryAction.getWord("PATH")), 0, 6);
        
            final TextField simulatorTF = new TextField();    
            simulatorTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNAME")));
            containerGP.add(simulatorTF, 1, 2);
            final ComboBox fabricTypeCB = new ComboBox();
            fabricTypeCB.getItems().addAll("Plain","Kadhua","Fekuwa-Float","Fekuwa-Cutwork","Binding-Irregular","Binding-Regular","Tanchoi","Tissue");        
            fabricTypeCB.setValue("Plain");
            fabricTypeCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFABRICTYPE")));
            containerGP.add(fabricTypeCB, 3, 2);
            final TextField ppiTF = new TextField(Integer.toString(objConfiguration.getIntPPI())){
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
            containerGP.add(ppiTF, 1, 3);
            final TextField epiTF = new TextField(Integer.toString(objConfiguration.getIntEPI())){
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
            epiTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEPI")));
            containerGP.add(epiTF, 3, 3);
            final ComboBox resolutionCB = new ComboBox();
            resolutionCB.getItems().addAll("800x600","1024x768","1152x864","1280x960","1280x1024");   
            String strResolution = objConfiguration.getStrResolution();
            resolutionCB.setValue(strResolution);
            resolutionCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPRESOLUTION")));
            containerGP.add(resolutionCB, 1, 4);
            final Label dpiV= new Label(Integer.toString(objConfiguration.getIntDPI()));
            dpiV.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDPI")));
            containerGP.add(dpiV, 3, 4);
            final Label yarnDefaultTF = new Label();
            yarnDefaultTF.setText("YARNG1");
            yarnDefaultTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDEFAULTYARN")));
            containerGP.add(yarnDefaultTF, 1, 5);    
            final Hyperlink yarnDefaultHL = new Hyperlink();
            yarnDefaultHL.setText(objDictionaryAction.getWord("TOOLTIPCHANGEDEFAULT"));
            yarnDefaultHL.setGraphic(new ImageView(objConfiguration.getStrColour()+"/browse.png"));
            yarnDefaultHL.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDEFAULTFABRIC")));
            containerGP.add(yarnDefaultHL, 3, 5);   
            final TextField txtPath = new TextField();
            containerGP.add(txtPath, 1, 6);
            final Button btnBrowse = new Button(objDictionaryAction.getWord("BROWSE"));
            btnBrowse.setGraphic(new ImageView(objConfiguration.getStrColour()+"/browse.png"));
            containerGP.add(btnBrowse, 3, 6);

            resolutionCB.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                    objConfiguration.setStrResolution(t1);
                    int dpi = objConfiguration.findIntDPI();
                    dpiV.setText(Integer.toString(dpi));
                }
            });
            yarnDefaultHL.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        Yarn objYarn = new Yarn(
                                null,
                                "Weft",
                                objConfiguration.getStrWeftName(),
                                "#"+objConfiguration.getStrWeftColor(),
                                objConfiguration.getIntWeftRepeat(),
                                "a",
                                objConfiguration.getIntWeftCount(),
                                objConfiguration.getStrWeftUnit(),
                                objConfiguration.getIntWeftPly(),
                                objConfiguration.getIntWeftFactor(),
                                objConfiguration.getDblWeftDiameter(),
                                objConfiguration.getIntWeftTwist(),
                                objConfiguration.getStrWeftSence(),
                                objConfiguration.getIntWeftHairness(),
                                objConfiguration.getIntWeftDistribution(),
                                objConfiguration.getDblWeftPrice(),
                                objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"),
                                objConfiguration.getObjUser().getStrUserID(),
                                null);
                        objYarn.setObjConfiguration(objConfiguration);
                        objYarn.setStrCondition("");
                        objYarn.setStrOrderBy("");
                        objYarn.setStrSearchBy("");

                        YarnImportView objYarnImportView = new YarnImportView(objYarn);
                        if(objYarn.getStrYarnId()!=null){
                            yarnDefaultTF.setText(objYarn.getStrYarnId());
                            objYarn = null;
                            System.gc();
                        }
                    } catch (Exception ex) {
                        new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
                        System.err.println(ex);
                    }
                }
            });
            btnBrowse.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    FileChooser objFileChooser = new FileChooser();
                    objFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG"));
                    objFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG"));
                    objFileChooser.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("SELECTTO")+" "+objDictionaryAction.getWord("DESIGN"));
                    //objFileChooser.setInitialDirectory(new File(objFabric.getObjConfiguration().strRoot));
                    File objFile = objFileChooser.showOpenDialog(simulatorStage);
                    System.err.println(objFile);
                    txtPath.setText(objFile.getAbsolutePath());
                    simulatorTF.setText(objFile.getAbsolutePath().substring(objFile.getAbsolutePath().lastIndexOf("\\"),objFile.getAbsolutePath().lastIndexOf(".")));
                    try {
                        objSimulator.setBufferedImage(ImageIO.read(objFile));
                    } catch (IOException ex) {
                        Logger.getLogger(UtilityViewOld.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });            

            final Button btnAdd;
            final Button btnUpdate;
            final Button btnDelete;
            final Button btnPreview;
            final Button btnClose;
           
            btnAdd = new Button(objDictionaryAction.getWord("ADDNEW"));
            btnUpdate = new Button(objDictionaryAction.getWord("UPDATE"));
            btnDelete = new Button(objDictionaryAction.getWord("DELETE"));
            btnPreview = new Button(objDictionaryAction.getWord("PREVIEW"));
            btnClose = new Button(objDictionaryAction.getWord("CLEAR"));
            
            //btnUpdate.setMaxWidth(Double.MAX_VALUE);
            //btnPreview.setMaxWidth(Double.MAX_VALUE);
            //btnClose.setMaxWidth(Double.MAX_VALUE);
            
            btnAdd.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
            btnUpdate.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
            btnDelete.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
            btnPreview.setGraphic(new ImageView(objConfiguration.getStrColour()+"/preview.png"));            
            btnClose.setGraphic(new ImageView(objConfiguration.getStrColour()+"/clear.png"));
                       
            btnAdd.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPADDNEW")));
            btnUpdate.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPUPDATE")));
            btnDelete.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDELETE")));
            btnPreview.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPREVIEW")));
            btnClose.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLEAR")));
            
            btnAdd.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
            btnPreview.setDisable(false);
            btnClose.setDefaultButton(true);
            
            btnPreview.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        if(!txtPath.getText().isEmpty()){
                            
                        }else{
                            lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                        }
                    } catch (Exception ex) {
                        new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
                        lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    }
                }
            });
            btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    new MessageView(objConfiguration);
                    if(objConfiguration.getServicePasswordValid()){
                        objConfiguration.setServicePasswordValid(false);
                        try {
                            if(!objSimulator.getStrBaseFSID().isEmpty()){
                                UtilityAction objUtilityAction = new UtilityAction();
                                objUtilityAction.clearBaseFabricSimultion(objSimulator.getStrBaseFSID());
                                simulatorData.remove(index);
                            
                                index = -1;
                                simulatorTF.setText("");
                                fabricTypeCB.setValue("Plain");
                                txtPath.setText("");
                            }else{
                                lblStatus.setText(objDictionaryAction.getWord("WRONGINPUT"));
                            }
                        } catch (SQLException ex) {
                            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        } catch (Exception ex) {
                            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                }
            });
            btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    new MessageView(objConfiguration);
                    if(objConfiguration.getServicePasswordValid()){
                        objConfiguration.setServicePasswordValid(false);
                        try {
                            if(!simulatorTF.getText().isEmpty() && !txtPath.getText().isEmpty()){
                                objSimulator.setStrBaseFSName(simulatorTF.getText().replace("\\","\\\\"));
                                objSimulator.setStrFabricType(fabricTypeCB.getValue().toString());
                                objSimulator.setIntEPI(Integer.parseInt(epiTF.getText()));
                                objSimulator.setIntPPI(Integer.parseInt(ppiTF.getText()));
                                objSimulator.setIntDPI(Integer.parseInt(dpiV.getText()));
                                objSimulator.setStrYarnID(yarnDefaultTF.getText());
                                
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                ImageIO.write(objSimulator.getBufferedImage(), "png", baos);
                                baos.flush();
                                byte[] imageInByte = baos.toByteArray();  
                                objSimulator.setBytBaseFSIcon(imageInByte);
                                imageInByte = null;
                                baos.close();
                                
                                objSimulator.setObjConfiguration(objConfiguration);
                                
                                UtilityAction objUtilityAction = new UtilityAction();
                                objUtilityAction.resetBaseFabricSimultion(objSimulator);
                                simulatorData.set(index, objSimulator);
                            }else{
                                lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(UtilityViewOld.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        } catch (Exception ex) {
                         Logger.getLogger(UtilityViewOld.class.getName()).log(Level.SEVERE, null, ex);
            
                            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }
                    }
                }
            });
            btnAdd.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    new MessageView(objConfiguration);
                    if(objConfiguration.getServicePasswordValid()){
                        objConfiguration.setServicePasswordValid(false);
                        try {
                            if(!simulatorTF.getText().isEmpty() && !txtPath.getText().isEmpty()){
                                String strBaseFSID=new IDGenerator().getIDGenerator("BASE_SIMULATION_LIBRARY", objConfiguration.getObjUser().getStrUserID());
                                objSimulator.setStrBaseFSID(strBaseFSID);
                                objSimulator.setStrBaseFSName(simulatorTF.getText().replace("\\","\\\\"));
                                objSimulator.setStrFabricType(fabricTypeCB.getValue().toString());
                                objSimulator.setIntEPI(Integer.parseInt(epiTF.getText()));
                                objSimulator.setIntPPI(Integer.parseInt(ppiTF.getText()));
                                objSimulator.setIntDPI(Integer.parseInt(dpiV.getText()));
                                objSimulator.setStrYarnID(yarnDefaultTF.getText());
                                
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                ImageIO.write(objSimulator.getBufferedImage(), "png", baos);
                                baos.flush();
                                byte[] imageInByte = baos.toByteArray();  
                                objSimulator.setBytBaseFSIcon(imageInByte);
                                imageInByte = null;
                                baos.close();
            
                                objSimulator.setObjConfiguration(objConfiguration);
                                System.out.println("FABTYPE"+objSimulator.getStrFabricType());
                                UtilityAction objUtilityAction = new UtilityAction();
                                if(objUtilityAction.setBaseFabricSimultion(objSimulator))
                                    lblStatus.setText(objDictionaryAction.getWord("DATASAVED"));
                                System.gc();
                            
                                index = simulatorData.size();
                                simulatorData.add(index, objSimulator);
                            }else{
                                lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(UtilityViewOld.class.getName()).log(Level.SEVERE, null, ex);
                            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        } catch (Exception ex) {
                            Logger.getLogger(UtilityViewOld.class.getName()).log(Level.SEVERE, null, ex);
                            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        }   
                    }
                }
            });
            btnClose.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    System.gc();
                    btnAdd.setDisable(true);
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                    btnPreview.setDisable(true);
                    btnClose.setDisable(true);
                    
                    index = -1;
                    simulatorTF.setText("");
                    fabricTypeCB.setValue("Plain");
                    txtPath.setText("");
                }
            });
            
            containerGP.add(btnAdd, 0, 7);
            containerGP.add(btnUpdate, 1, 7);
            containerGP.add(btnDelete, 2, 7);
            containerGP.add(btnPreview, 3, 7);
            containerGP.add(btnClose, 4, 7);
            
            Separator sepHor2 = new Separator();
            sepHor2.setValignment(VPos.CENTER);
            GridPane.setConstraints(sepHor2, 0, 8);
            GridPane.setColumnSpan(sepHor2, 5);
            containerGP.getChildren().add(sepHor2);
        
            HBox tsHB = new HBox();
            final ToggleGroup actionTG = new ToggleGroup();
            final RadioButton newRB = new RadioButton(objDictionaryAction.getWord("ADDNEW")+" "+objDictionaryAction.getWord("SIMULATOR"));        
            newRB.setToggleGroup(actionTG);
            newRB.setUserData("new");
            newRB.setSelected(true);
            final RadioButton oldRB = new RadioButton(objDictionaryAction.getWord("SELECTTO")+" "+objDictionaryAction.getWord("UPDATE"));
            oldRB.setToggleGroup(actionTG);
            oldRB.setUserData("old");
            tsHB.getChildren().addAll(newRB,oldRB);
            tsHB.setStyle("-fx-text-fill: #FF0000;");
            actionTG.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                public void changed(ObservableValue<? extends Toggle> ov,Toggle old_toggle, Toggle new_toggle) {
                    if (actionTG.getSelectedToggle() != null) {
                        String action = actionTG.getSelectedToggle().getUserData().toString().trim();
                        if(action.equalsIgnoreCase("new")){
                            btnAdd.setDisable(false);
                            btnUpdate.setDisable(true);
                            btnDelete.setDisable(true);
                            btnPreview.setDisable(false);
                            btnClose.setDisable(false);
                        }else{
                            btnAdd.setDisable(true);
                            btnUpdate.setDisable(false);
                            btnDelete.setDisable(true);
                            btnPreview.setDisable(true);
                            btnClose.setDisable(false);
                        }
                    }
                }
            });        
            containerGP.add(tsHB, 0, 9, 5, 1);
           
            Separator sepHor3 = new Separator();
            sepHor3.setValignment(VPos.CENTER);
            GridPane.setConstraints(sepHor3, 0, 10);
            GridPane.setColumnSpan(sepHor3, 5);
            containerGP.getChildren().add(sepHor3);
        
            TableView<Simulator> simulatorTable = new TableView<Simulator>();
            ScrollPane simulatorList = new ScrollPane();
            simulatorTable.setPrefSize(objConfiguration.WIDTH/2,objConfiguration.HEIGHT/4);
            
            
            objSimulator = new Simulator(null, "", "", null, 0, 0, 0, null, null, null, null);
            objSimulator.setObjConfiguration(objConfiguration);
            objSimulator.setStrCondition("");
            objSimulator.setStrOrderBy("Name");
            objSimulator.setStrSearchBy("All");
            objSimulator.setStrDirection("Ascending");
        
            UtilityAction objUtilityAction = new UtilityAction();
            simulatorData.addAll(objUtilityAction.lstBaseFabricSimultion(objSimulator));
            //System.out.println("COUNT:"+new UtilityAction().retBaseFabricSimultion(objSimulator).length);
            //simulatorData.addAll(objUtilityAction.retBaseFabricSimultion(objSimulator));
            TableColumn selectCol = new TableColumn("Simulator#");
            TableColumn nameCol = new TableColumn("Name");
            TableColumn typeCol = new TableColumn("Type");
            TableColumn epiCol = new TableColumn("EPI");
            TableColumn ppiCol = new TableColumn("PPI");
            TableColumn dpiCol = new TableColumn("DPI");
            TableColumn yarnCol = new TableColumn("Yarn");
            selectCol.setCellValueFactory(new PropertyValueFactory<Simulator,String>("strBaseFSID"));
            nameCol.setCellValueFactory(new PropertyValueFactory<Simulator,String>("strBaseFSName"));
            typeCol.setCellValueFactory(new PropertyValueFactory<Simulator,String>("strFabricType"));
            epiCol.setCellValueFactory(new PropertyValueFactory<Simulator,Integer>("intEPI"));
            ppiCol.setCellValueFactory(new PropertyValueFactory<Simulator,Integer>("intPPI"));
            dpiCol.setCellValueFactory(new PropertyValueFactory<Simulator,Integer>("intDPI"));
            yarnCol.setCellValueFactory(new PropertyValueFactory<Simulator,String>("strYarnID"));
            simulatorTable.setItems(simulatorData);
            simulatorTable.setEditable(true);
            simulatorTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            simulatorTable.getColumns().addAll(selectCol, nameCol, typeCol, epiCol, ppiCol, dpiCol, yarnCol);
            simulatorTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Simulator>() {
                @Override
                public void changed(ObservableValue<? extends Simulator> ov, Simulator t, Simulator t1) {
                    index = simulatorData.indexOf(t1);
                    objSimulator = new Simulator(t1.getStrBaseFSID(), t1.getStrBaseFSName(), t1.getStrFabricType()
                        , t1.getStrYarnID(), t1.getIntPPI(), t1.getIntEPI(), t1.getIntDPI(), t1.getBytBaseFSIcon(), t1.getStrBaseFSDate(), t1.getStrUserId(), t1.getStrBaseFSAccess());
                    
                    objSimulator.setObjConfiguration(objConfiguration);
                    objSimulator.setStrCondition("");
                    objSimulator.setStrOrderBy("Name");
                    objSimulator.setStrSearchBy("All");
                    objSimulator.setStrDirection("Ascending");
        
                    objSimulator.setStrBaseFSID(t1.getStrBaseFSID());
                    objSimulator.setStrBaseFSName(t1.getStrBaseFSName());
                    objSimulator.setStrBaseFSType(t1.getStrBaseFSType());
                    objSimulator.setIntEPI(t1.getIntEPI());
                    objSimulator.setIntPPI(t1.getIntPPI());
                    objSimulator.setIntDPI(t1.getIntDPI());
                    objSimulator.setStrYarnID(t1.getStrYarnID());
                    
                    try {
                        byte[] byteToImage=objSimulator.getBytBaseFSIcon();
                        SeekableStream stream = new ByteArraySeekableStream(byteToImage);
                        String[] names = ImageCodec.getDecoderNames(stream);
                        ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                        RenderedImage im = dec.decodeAsRenderedImage();
                        objSimulator.setBufferedImage(PlanarImage.wrapRenderedImage(im).getAsBufferedImage());
                        byteToImage=null;
			File f=new File("bufferedImage.png");
                        ImageIO.write(im, "png", f.getAbsoluteFile());
                        txtPath.setText(f.getAbsolutePath());
                    } catch (IOException ex) {
                        Logger.getLogger(UtilityViewOld.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    simulatorTF.setText(objSimulator.getStrBaseFSName());
                    fabricTypeCB.setValue(objSimulator.getStrBaseFSType());
                    epiTF.setText(Integer.toString(objSimulator.getIntEPI()));
                    ppiTF.setText(Integer.toString(objSimulator.getIntPPI()));
                    dpiV.setText(Integer.toString(objSimulator.getIntDPI()));
                    yarnDefaultTF.setText(objSimulator.getStrYarnID());
                    //txtPath.setText(objDevice.getStrDevicePath());
                    
                    btnAdd.setDisable(true);
                    btnUpdate.setDisable(false);
                    btnDelete.setDisable(false);
                    btnPreview.setDisable(false);
                    btnClose.setDisable(false);
                    oldRB.setSelected(true);
                    //System.out.println(t1.getThreadColor()+"selection "+index+" changed"+Color.web(t1.getThreadColor().substring(1))+":"+Color.valueOf(t1.getThreadColor().substring(1)));                        
                }
            });
            
            simulatorList.setContent(simulatorTable);
            GridPane.setConstraints(simulatorList, 0, 11);
            GridPane.setColumnSpan(simulatorList, 5);
            containerGP.getChildren().add(simulatorList);
            
        } catch (SQLException ex) {
            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }

    public void loadLibraryFabric(){
        containerGP.getChildren().clear();
        System.gc();
        objFabric = new Fabric();
        objFabric.setObjConfiguration(objConfiguration);
        objFabric.setStrCondition("");
        objFabric.setStrSearchBy("");
        objFabric.setStrOrderBy("Name");
        objFabric.setStrDirection("Ascending");
        
        containerGP.add(new Text(objDictionaryAction.getWord("FABRIC")+" "+objDictionaryAction.getWord("NAME")),0,0);
        containerGP.add(new Text(objDictionaryAction.getWord("FABRIC")+" "+objDictionaryAction.getWord("SEARCHBY")),2,0);
        containerGP.add(new Text(objDictionaryAction.getWord("FABRIC")+" "+objDictionaryAction.getWord("SORTBY")),4,0);
        containerGP.add(new Text(objDictionaryAction.getWord("FABRIC")+" "+objDictionaryAction.getWord("SORTDIRCTION")),6,0);
        
        final TextField libName = new TextField();
        libName.setPromptText(objDictionaryAction.getWord("PROMPTNAME"));
        libName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                objFabric.setStrCondition(libName.getText());
                populateLibraryFabric(); 
            }
        });
        ComboBox libSearch = new ComboBox();
        libSearch.getItems().addAll(
            "All Cloth Type",
            "Body",
            "Palu",
            "Border",
            "Cross Border",
            "Blouse",
            "Skart",
            "Konia"
        );
        libSearch.setPromptText(objDictionaryAction.getWord("SEARCHBY"));
        libSearch.setEditable(false); 
        libSearch.setValue("All Cloth Type"); 
        libSearch.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objFabric.setStrSearchBy(newValue);
                populateLibraryFabric();
            }    
        });
        ComboBox libSort = new ComboBox();
        libSort.getItems().addAll(
            "Name",
            "Date"
        );
        libSort.setPromptText(objDictionaryAction.getWord("SORTBY"));
        libSort.setEditable(false); 
        libSort.setValue("Name"); 
        libSort.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objFabric.setStrOrderBy(newValue);
                populateLibraryFabric();
            }    
        });
        ComboBox libDirction = new ComboBox();
        libDirction.getItems().addAll(
            "Ascending",
            "Descending"
        );
        libDirction.setPromptText(objDictionaryAction.getWord("SORTDIRCTION"));
        libDirction.setEditable(false); 
        libDirction.setValue("Ascending"); 
        libDirction.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objFabric.setStrDirection(newValue);
                populateLibraryFabric();
            }    
        });
        
        containerGP.add(libName,1,0);
        containerGP.add(libSearch,3,0);
        containerGP.add(libSort,5,0);
        containerGP.add(libDirction,7,0);
        
        Separator sepHor = new Separator();
        sepHor.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor, 0, 1);
        GridPane.setColumnSpan(sepHor, 8);
        containerGP.getChildren().add(sepHor);
    
        GP_container = new GridPane();         
        GP_container.setAlignment(Pos.TOP_LEFT);
        GP_container.setHgap(20);
        GP_container.setVgap(20);
        GP_container.setPadding(new Insets(1, 1, 1, 1));
        containerGP.add(GP_container,0,2,8,1);
        
        populateLibraryFabric();
        //container.setContent(containerGP);
    }    
    private void populateLibraryFabric(){
        try{
            GP_container.getChildren().clear();
            List lstFabric=null;
            List lstFabricDeatails = new ArrayList();
            objFabricAction = new FabricAction();
            lstFabricDeatails = objFabricAction.lstImportFabric(objFabric);
            if(lstFabricDeatails.size()==0){
                GP_container.add(new Text(objDictionaryAction.getWord("FABRIC")+" - "+objDictionaryAction.getWord("NOVALUE")),0,0);
            }else{
                for (int i=0, j = lstFabricDeatails.size(), k=0; i<j; i++, k+=3){
                    lstFabric = (ArrayList)lstFabricDeatails.get(i);

                    byte[] bytArtworkThumbnil = (byte[])lstFabric.get(26);
                    SeekableStream stream = new ByteArraySeekableStream(bytArtworkThumbnil);
                    String[] names = ImageCodec.getDecoderNames(stream);
                    ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                    RenderedImage im = dec.decodeAsRenderedImage();
                    BufferedImage bufferedImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
                    Image image=SwingFXUtils.toFXImage(bufferedImage, null);
                    ImageView ivFabric = new ImageView(image);
                    ivFabric.setFitHeight(222);
                    ivFabric.setFitWidth(222);
                    stream.close();
                    bufferedImage = null;
                    GP_container.add(ivFabric, k%6, i/2);
                    
                    final String strAccess = lstFabric.get(29).toString();
                    int intUsage=0;
                    //objFabricAction = new objFabricAction();
                    //intUsage = objArtworkAction.countArtworkUsage(lstArtwork.get(0).toString());
                    
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
                            objDictionaryAction.getWord("PERMISSION")+": "+strAccess+"\n"+
                            objDictionaryAction.getWord("BY")+": "+lstFabric.get(31)+"\n"+
                            objDictionaryAction.getWord("DATE")+": "+lstFabric.get(30);
                    final Label lblFabric = new Label(strTooltip);
                    lblFabric.setTooltip(new Tooltip(strTooltip));
                    lblFabric.setId(lstFabric.get(0).toString());
                    lblFabric.setUserData(lstFabric.get(2).toString());
                    GP_container.add(lblFabric, (k+1)%6, i/2);
                    
                    VBox action = new VBox();
                    if(!strAccess.equalsIgnoreCase("Public")){
                        Button btnExport = new Button(objDictionaryAction.getWord("EXPORT"));
                        btnExport.setGraphic(new ImageView(objConfiguration.getStrColour()+"/export.png"));
                        btnExport.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEXPORT")));
                        btnExport.setMaxWidth(Double.MAX_VALUE);
                        btnExport.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                System.gc();
                                final Stage dialogStage = new Stage();
                                dialogStage.initStyle(StageStyle.UTILITY);
                                dialogStage.initModality(Modality.APPLICATION_MODAL);
                                dialogStage.setResizable(false);
                                dialogStage.setIconified(false);
                                dialogStage.setFullScreen(false);
                                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ALERT"));
                                BorderPane root = new BorderPane();
                                Scene scene = new Scene(root, 300, 200, Color.WHITE);
                                scene.getStylesheets().add(UtilityViewOld.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                                final GridPane popup=new GridPane();
                                popup.setId("popup");
                                popup.setHgap(5);
                                popup.setVgap(5);
                                popup.setPadding(new Insets(5, 5, 5, 5));
                                Label lblPassword=new Label(objDictionaryAction.getWord("SERVICEPASSWORD"));
                                lblPassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                popup.add(lblPassword, 0, 0, 2, 1);
                                final PasswordField passPF =new PasswordField();
                                passPF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                passPF.setPromptText(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
                                popup.add(passPF, 0, 1, 2, 1);
                                final Label lblAlert=new Label("");
                                lblAlert.setStyle("-fx-wrap-text:true;");
                                lblAlert.setPrefWidth(250);
                                popup.add(lblAlert, 0, 3, 2, 1);
                                Button btnYes= new Button(objDictionaryAction.getWord("OK"));
                                btnYes.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOK")));
                                btnYes.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
                                btnYes.setDefaultButton(true);
                                btnYes.setOnAction(new EventHandler<ActionEvent>(){
                                    @Override
                                    public void handle(ActionEvent event) {
                                        if(Security.SecurePassword(passPF.getText(), objConfiguration.getObjUser().getStrUsername()).equals(objConfiguration.getObjUser().getStrAppPassword())){
                                            dialogStage.close();
                                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                                            Date date = new Date();
                                            String currentDate = dateFormat.format(date);
                                            String savePath = System.getProperty("user.dir");

                                            DirectoryChooser directoryChooser=new DirectoryChooser();
                                            simulatorStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORT")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
                                            File selectedDirectory = directoryChooser.showDialog(simulatorStage);
                                            if(selectedDirectory != null)
                                                savePath = selectedDirectory.getPath();
                                            savePath = savePath+"\\"+objDictionaryAction.getWord("PROJECT")+"_"+currentDate+"\\";
                                            File file = new File(savePath);
                                            if (!file.exists()) {
                                                if (!file.mkdir())
                                                    savePath = System.getProperty("user.dir");
                                            }
                                            savePath = savePath +currentDate+".sql";
                                            file =new File(savePath);
                                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
											String content = new DbUtility().exportEachFabric(objConfiguration,lblFabric.getId().toString());
                                                bw.write(content);
                                                // no need to close it.
                                                bw.close();
                                                ArrayList<File> filesToZip=new ArrayList<>();
                                                filesToZip.add(file);
                                                String zipFilePath=file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf(".sql"))+".zip";
                                                String passwordToZip = file.getName().substring(0, file.getName().indexOf(".sql"));
                                                new EncryptZip(zipFilePath, filesToZip, passwordToZip);
                                                file.delete();
                                                lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+savePath);
                                            } catch (IOException ex) {
                                                new Logging("SEVERE",UtilityViewOld.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                            } catch (SQLException ex) {
                                                new Logging("SEVERE",UtilityViewOld.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                            }
                                        }
                                        else if(passPF.getText().length()==0){
                                            lblAlert.setText(objDictionaryAction.getWord("NOSERVICEPASSWORD"));
                                        }
                                        else{
                                            lblAlert.setText(objDictionaryAction.getWord("INVALIDSERVICEPASSWORD"));
                                        }
                                    }
                                });
                                popup.add(btnYes, 0, 2);
                                Button btnNo = new Button(objDictionaryAction.getWord("CANCEL"));
                                btnNo.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
                                btnNo.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
                                btnNo.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent t) {
                                        dialogStage.close();
                                        System.gc();
                                    }
                                });
                                popup.add(btnNo, 1, 2);
                                root.setCenter(popup);
                                dialogStage.setScene(scene);
                                dialogStage.showAndWait();  
                                System.gc();
                            }
                        });
                        action.getChildren().add(btnExport);
                    }
                    if(!strAccess.equalsIgnoreCase("Public") && intUsage==0){
                        Button btnDelete = new Button(objDictionaryAction.getWord("DELETE"));
                        btnDelete.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
                        btnDelete.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDELETE")));
                        btnDelete.setMaxWidth(Double.MAX_VALUE);
                        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                System.gc();
                                final Stage dialogStage = new Stage();
                                dialogStage.initStyle(StageStyle.UTILITY);
                                dialogStage.initModality(Modality.APPLICATION_MODAL);
                                dialogStage.setResizable(false);
                                dialogStage.setIconified(false);
                                dialogStage.setFullScreen(false);
                                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ALERT"));
                                BorderPane root = new BorderPane();
                                Scene scene = new Scene(root, 300, 200, Color.WHITE);
                                scene.getStylesheets().add(UtilityViewOld.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                                final GridPane popup=new GridPane();
                                popup.setId("popup");
                                popup.setHgap(5);
                                popup.setVgap(5);
                                popup.setPadding(new Insets(25, 25, 25, 25));
                                popup.add(new ImageView(objConfiguration.getStrColour()+"/stop.png"), 0, 0);
                                
                                Label lblPassword=new Label(objDictionaryAction.getWord("SERVICEPASSWORD"));
                                lblPassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                popup.add(lblPassword, 0, 1, 2, 1);
                                final PasswordField passPF= new PasswordField();
                                passPF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                passPF.setPromptText(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
                                popup.add(passPF, 0, 2, 2, 1);
                                
                                final Label lblAlert = new Label(objDictionaryAction.getWord("ALERTDELETE"));
                                lblAlert.setStyle("-fx-wrap-text:true;");
                                lblAlert.setPrefWidth(250);
                                popup.add(lblAlert, 1, 0);
                                Button btnYes = new Button(objDictionaryAction.getWord("YES"));
                                btnYes.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOK")));
                                btnYes.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
                                btnYes.setDefaultButton(true);
                                btnYes.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent t) {
                                        if(passPF.getText().length()==0){
                                            lblAlert.setText(objDictionaryAction.getWord("NOSERVICEPASSWORD"));
                                        }
                                        else{
                                            if(Security.SecurePassword(passPF.getText(), objConfiguration.getObjUser().getStrUsername()).equals(objConfiguration.getObjUser().getStrAppPassword())){
                                                try {
                                                    objFabricAction = new FabricAction();
                                                    objFabricAction.clearFabric(lblFabric.getId().toString());
                                                    objFabricAction = new FabricAction();
                                                    objFabricAction.clearFabricArtwork(lblFabric.getId().toString());
                                                    YarnAction objYarnAction = new YarnAction();
                                                    objYarnAction.clearFabricYarn(lblFabric.getId().toString());
                                                    objFabricAction = new FabricAction();
                                                    objFabricAction.clearFabricPallets(lblFabric.getId().toString());
                                                    objFabricAction = new FabricAction();
                                                    objFabricAction.clearFabric(lblFabric.getId().toString());
                                                    loadLibraryFabric();
                                                    lblStatus.setText(lblFabric.getId()+" : "+objDictionaryAction.getWord("ACTIONDELETE"));                                            
                                                } catch (SQLException ex) {
                                                    new Logging("SEVERE",UtilityViewOld.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                                } catch (Exception ex) {
                                                    new Logging("SEVERE",UtilityViewOld.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                                }
                                                dialogStage.close();
                                            }
                                            else{
                                                lblAlert.setText(objDictionaryAction.getWord("INVALIDSERVICEPASSWORD"));
                                            }
                                        }
                                        System.gc();
                                    }
                                });
                                popup.add(btnYes, 0, 3);
                                Button btnNo = new Button(objDictionaryAction.getWord("NO"));
                                btnNo.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
                                btnNo.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
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
                                dialogStage.showAndWait();  
                                e.consume();
                                System.gc();
                            }
                        });
                        action.getChildren().add(btnDelete);
                    }
                    if(!strAccess.equalsIgnoreCase("Public") && intUsage==0){
                        Button btnExportDelete = new Button(objDictionaryAction.getWord("EXPORTDELETE"));
                        btnExportDelete.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
                        btnExportDelete.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEXPORTDELETE")));
                        btnExportDelete.setMaxWidth(Double.MAX_VALUE);
                        btnExportDelete.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                System.gc();
                                final Stage dialogStage = new Stage();
                                dialogStage.initStyle(StageStyle.UTILITY);
                                dialogStage.initModality(Modality.APPLICATION_MODAL);
                                dialogStage.setResizable(false);
                                dialogStage.setIconified(false);
                                dialogStage.setFullScreen(false);
                                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ALERT"));
                                BorderPane root = new BorderPane();
                                Scene scene = new Scene(root, 300, 150, Color.WHITE);
                                scene.getStylesheets().add(UtilityViewOld.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                                final GridPane popup=new GridPane();
                                popup.setId("popup");
                                popup.setHgap(5);
                                popup.setVgap(5);
                                popup.setPadding(new Insets(5, 5, 5, 5));
                                Label lblPassword=new Label(objDictionaryAction.getWord("SERVICEPASSWORD"));
                                lblPassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                popup.add(lblPassword, 0, 0, 2, 1);
                                final PasswordField passPF =new PasswordField();
                                passPF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                passPF.setPromptText(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
                                popup.add(passPF, 0, 1, 2, 1);
                                final Label lblAlert=new Label("");
                                lblAlert.setStyle("-fx-wrap-text:true;");
                                lblAlert.setPrefWidth(250);
                                popup.add(lblAlert, 0, 3, 2, 1);
                                Button btnYes = new Button(objDictionaryAction.getWord("OK"));
                                btnYes.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOK")));
                                btnYes.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
                                btnYes.setDefaultButton(true);
                                btnYes.setOnAction(new EventHandler<ActionEvent>(){
                                    @Override
                                    public void handle(ActionEvent event) {
                                        if(Security.SecurePassword(passPF.getText(), objConfiguration.getObjUser().getStrUsername()).equals(objConfiguration.getObjUser().getStrAppPassword())){
                                            dialogStage.close();
                                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                                            Date date = new Date();
                                            String currentDate = dateFormat.format(date);
                                            String savePath = System.getProperty("user.dir");

                                            DirectoryChooser directoryChooser=new DirectoryChooser();
                                            simulatorStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORT")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
                                            File selectedDirectory = directoryChooser.showDialog(simulatorStage);
                                            if(selectedDirectory != null)
                                                savePath = selectedDirectory.getPath();
                                            savePath = savePath+"\\"+objDictionaryAction.getWord("PROJECT")+"_"+currentDate+"\\";
                                            File file = new File(savePath);
                                            if (!file.exists()) {
                                                if (!file.mkdir())
                                                    savePath = System.getProperty("user.dir");
                                            }
                                            savePath = savePath +currentDate+".sql";
                                            file =new File(savePath);
                                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(savePath))) {
                                                String content = new DbUtility().exportEachFabric(objConfiguration,lblFabric.getId().toString());
                                                bw.write(content);
                                                // no need to close it.
                                                //bw.close();
                                                ArrayList<File> filesToZip=new ArrayList<>();
                                                filesToZip.add(file);
                                                String zipFilePath=file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf(".sql"))+".zip";
                                                String passwordToZip = file.getName().substring(0, file.getName().indexOf(".sql"));
                                                new EncryptZip(zipFilePath, filesToZip, passwordToZip);
                                                file.delete();
                                                lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+savePath);
                                                objFabricAction = new FabricAction();
                                                objFabricAction.clearFabric(lblFabric.getId().toString());
                                                objFabricAction = new FabricAction();
                                                objFabricAction.clearFabricArtwork(lblFabric.getId().toString());
                                                YarnAction objYarnAction = new YarnAction();
                                                objYarnAction.clearFabricYarn(lblFabric.getId().toString());
                                                objFabricAction = new FabricAction();
                                                objFabricAction.clearFabricPallets(lblFabric.getId().toString());
                                                objFabricAction = new FabricAction();
                                                objFabricAction.clearFabric(lblFabric.getId().toString());
                                                loadLibraryArtwork();
                                            } catch (IOException ex) {
                                                new Logging("SEVERE",UtilityViewOld.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                            } catch (SQLException ex) {
                                                new Logging("SEVERE",UtilityViewOld.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                            }
                                        }
                                        else if(passPF.getText().length()==0){
                                            lblAlert.setText(objDictionaryAction.getWord("NOSERVICEPASSWORD"));
                                        }
                                        else{
                                            lblAlert.setText(objDictionaryAction.getWord("INVALIDSERVICEPASSWORD"));
                                        }
                                    }
                                });
                                popup.add(btnYes, 0, 2, 1, 1);
                                Button btnNo = new Button(objDictionaryAction.getWord("CANCEL"));
                                btnNo.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
                                btnNo.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
                                btnNo.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent t) {
                                        dialogStage.close();
                                        System.gc();
                                    }
                                });
                                popup.add(btnNo, 1, 2, 1, 1);
                                root.setCenter(popup);
                                dialogStage.setScene(scene);
                                dialogStage.showAndWait();  
                                System.gc();
                            }
                        });
                        action.getChildren().add(btnExportDelete);
                    }
                    if(!strAccess.equalsIgnoreCase("Public")){
                        Button btnPermission = new Button(objDictionaryAction.getWord("CHANGEPERMISSION"));
                        btnPermission.setGraphic(new ImageView(objConfiguration.getStrColour()+"/settings.png"));
                        btnPermission.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCHANGEPERMISSION")));
                        btnPermission.setMaxWidth(Double.MAX_VALUE);
                        btnPermission.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                final Stage dialogStage = new Stage();
                                dialogStage.initStyle(StageStyle.UTILITY);
                                dialogStage.initModality(Modality.APPLICATION_MODAL);
                                dialogStage.setResizable(false);
                                dialogStage.setIconified(false);
                                dialogStage.setFullScreen(false);
                                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ALERT"));
                                BorderPane root = new BorderPane();
                                Scene scene = new Scene(root, 300, 180, Color.WHITE);
                                scene.getStylesheets().add(UtilityViewOld.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                                final GridPane popup=new GridPane();
                                popup.setId("popup");
                                popup.setHgap(5);
                                popup.setVgap(5);
                                popup.setPadding(new Insets(25, 25, 25, 25));
                                
                                Label lblData = new Label(objDictionaryAction.getWord("SELECT")+" "+objDictionaryAction.getWord("PERMISSION"));
                                lblData.setStyle("-fx-wrap-text:true;");
                                lblData.setPrefWidth(250);
                                popup.add(lblData, 0, 0, 3, 1);
                                final ToggleGroup dataTG = new ToggleGroup();
                                RadioButton dataPublicRB = new RadioButton(objDictionaryAction.getWord("PUBLIC"));
                                dataPublicRB.setToggleGroup(dataTG);
                                dataPublicRB.setUserData("Public");
                                popup.add(dataPublicRB, 0, 1);
                                RadioButton dataPrivateRB = new RadioButton(objDictionaryAction.getWord("PRIVATE"));
                                dataPrivateRB.setToggleGroup(dataTG);
                                dataPrivateRB.setUserData("Private");
                                popup.add(dataPrivateRB, 1, 1);
                                RadioButton dataProtectedRB = new RadioButton(objDictionaryAction.getWord("PROTECTED"));
                                dataProtectedRB.setToggleGroup(dataTG);
                                dataProtectedRB.setUserData("Protected");
                                popup.add(dataProtectedRB, 2, 1);
                                if(strAccess.equalsIgnoreCase("Public"))
                                    dataTG.selectToggle(dataPublicRB);
                                else if(strAccess.equalsIgnoreCase("Private"))
                                    dataTG.selectToggle(dataPrivateRB);
                                else 
                                    dataTG.selectToggle(dataProtectedRB);
                                
                                // Added 20 Feb 2017 -----------------------------------------
                                final Label lblPassword=new Label(objDictionaryAction.getWord("SERVICEPASSWORD"));
                                lblPassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                popup.add(lblPassword, 0, 2, 1, 1);
                                final PasswordField passPF=new PasswordField();
                                passPF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                passPF.setPromptText(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
                                popup.add(passPF, 1, 2, 2, 1);
                                // -----------------------------------------------------------
                                
                                final Label lblAlert = new Label(objDictionaryAction.getWord("ALERTWHAT"));
                                lblAlert.setStyle("-fx-wrap-text:true;");
                                lblAlert.setPrefWidth(250);
                                popup.add(lblAlert, 0, 4, 3, 1);
                                
                                Button btnYes = new Button(objDictionaryAction.getWord("OK"));
                                btnYes.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOK")));
                                btnYes.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
                                btnYes.setDefaultButton(true);
                                btnYes.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent t) {
                                        if(passPF.getText().length()==0){
                                            lblAlert.setText(objDictionaryAction.getWord("NOSERVICEPASSWORD"));
                                        }
                                        else{
                                            if(Security.SecurePassword(passPF.getText(), objConfiguration.getObjUser().getStrUsername()).equals(objConfiguration.getObjUser().getStrAppPassword())){
                                                try {
                                                    String strAccessNew = dataTG.getSelectedToggle().getUserData().toString();
                                                    System.err.println(strAccessNew);
                                                    strAccessNew = new IDGenerator().setUserAcessValueData("FABRIC_LIBRARY",strAccessNew);
                                                    objFabricAction = new FabricAction();
                                                    objFabricAction.resetFabricPermission(lblFabric.getId(),strAccessNew);
                                                    dialogStage.close();                                            
                                                    loadLibraryArtwork();
                                                } catch (SQLException ex) {
                                                    new Logging("SEVERE",UtilityViewOld.class.getName(),"Change Permission Data"+ex.getMessage(),ex);
                                                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                                }
                                            }
                                            else{
                                                lblAlert.setText(objDictionaryAction.getWord("INVALIDSERVICEPASSWORD"));
                                            }
                                        }
                                        System.gc();
                                    }
                                });
                                popup.add(btnYes, 0, 3);
                                Button btnNo = new Button(objDictionaryAction.getWord("CANCEL"));
                                btnNo.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
                                btnNo.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
                                btnNo.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent t) {
                                        dialogStage.close();
                                        System.gc();
                                    }
                                });
                                popup.add(btnNo, 2, 3);
                                root.setCenter(popup);
                                dialogStage.setScene(scene);
                                dialogStage.showAndWait();  
                                e.consume();
                                System.gc();
                            }
                        });
                        action.getChildren().add(btnPermission);
                    }
                    Button btnUpdate;
                    if(!strAccess.equalsIgnoreCase("Public") && intUsage==0){
                        btnUpdate = new Button(objDictionaryAction.getWord("UPDATE"));
                        btnUpdate.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
                        btnUpdate.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPUPDATE")));
                    }else{
                        btnUpdate = new Button(objDictionaryAction.getWord("COPY"));
                        btnUpdate.setGraphic(new ImageView(objConfiguration.getStrColour()+"/copy.png"));
                        btnUpdate.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOPY")));
                    }
                    btnUpdate.setMaxWidth(Double.MAX_VALUE);
                    btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            if(strAccess.equalsIgnoreCase("Public")){
                                objConfiguration.setServicePasswordValid(true);
                            } else{
                                new MessageView(objConfiguration);
                            }
                            if(objConfiguration.getServicePasswordValid()){
                                objConfiguration.setServicePasswordValid(false);
                                try {
                                    objConfiguration.setStrClothType(lblFabric.getUserData().toString());
                                    objConfiguration.strWindowFlowContext = "FabricEditor";
                                    UserAction objUserAction = new UserAction();
                                    objUserAction.getConfiguration(objConfiguration);
                                    objConfiguration.clothRepeat();
                                    System.gc();
                                    objConfiguration.mapRecentFabric.put(objConfiguration.getStrClothType(),lblFabric.getId());
                                    simulatorStage.close();
                                    System.gc();
                                    FabricView objFabricView = new FabricView(objConfiguration);
                                    System.gc();
                                } catch (SQLException ex) {
                                    new Logging("SEVERE",UtilityViewOld.class.getName(),"Load Fabric in Editor"+ex.toString(),ex);
                                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                }
                            }
                        }
                    });
                    action.getChildren().add(btnUpdate);
                    GP_container.add(action, (k+2)%6, i/2);
                }
            }
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public void loadLibraryArtwork(){        
        containerGP.getChildren().clear();
        System.gc();
        objArtwork = new Artwork();
        objArtwork.setObjConfiguration(objConfiguration);
        objArtwork.setStrCondition("");
        objArtwork.setStrSearchBy("");
        objArtwork.setStrOrderBy("Name");
        objArtwork.setStrDirection("Ascending");
        
        containerGP.add(new Text(objDictionaryAction.getWord("ARTWORK")+" "+objDictionaryAction.getWord("NAME")),0,0);
        containerGP.add(new Text(objDictionaryAction.getWord("ARTWORK")+" "+objDictionaryAction.getWord("SEARCHBY")),2,0);
        containerGP.add(new Text(objDictionaryAction.getWord("ARTWORK")+" "+objDictionaryAction.getWord("SORTBY")),4,0);
        containerGP.add(new Text(objDictionaryAction.getWord("ARTWORK")+" "+objDictionaryAction.getWord("SORTDIRCTION")),6,0);
        
        final TextField libName = new TextField();
        libName.setPromptText(objDictionaryAction.getWord("PROMPTNAME"));
        libName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                objArtwork.setStrCondition(libName.getText());
                populateLibraryArtwork(); 
            }
        });
        ComboBox libSearch = new ComboBox();
        libSearch.getItems().addAll(
            "All Type"
        );
        libSearch.setPromptText(objDictionaryAction.getWord("SEARCHBY"));
        libSearch.setEditable(false); 
        libSearch.setValue("All Type"); 
        libSearch.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objArtwork.setStrSearchBy(newValue);
                populateLibraryArtwork();
            }    
        });
        ComboBox libSort = new ComboBox();
        libSort.getItems().addAll(
            "Name",
            "Date"
        );
        libSort.setPromptText(objDictionaryAction.getWord("SORTBY"));
        libSort.setEditable(false); 
        libSort.setValue("Name"); 
        libSort.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objArtwork.setStrOrderBy(newValue);
                populateLibraryArtwork();
            }    
        });
        ComboBox libDirction = new ComboBox();
        libDirction.getItems().addAll(
            "Ascending",
            "Descending"
        );
        libDirction.setPromptText(objDictionaryAction.getWord("SORTDIRCTION"));
        libDirction.setEditable(false); 
        libDirction.setValue("Ascending"); 
        libDirction.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objArtwork.setStrDirection(newValue);
                populateLibraryArtwork();
            }    
        });
        
        containerGP.add(libName,1,0);
        containerGP.add(libSearch,3,0);
        containerGP.add(libSort,5,0);
        containerGP.add(libDirction,7,0);
        
        Separator sepHor = new Separator();
        sepHor.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor, 0, 1);
        GridPane.setColumnSpan(sepHor, 8);
        containerGP.getChildren().add(sepHor);
    
        GP_container = new GridPane();         
        GP_container.setAlignment(Pos.TOP_LEFT);
        GP_container.setHgap(20);
        GP_container.setVgap(20);
        GP_container.setPadding(new Insets(1, 1, 1, 1));
        containerGP.add(GP_container,0,2,8,1);
        
        populateLibraryArtwork();
        //container.setContent(containerGP);
    }    
    private void populateLibraryArtwork(){
        try{
            GP_container.getChildren().clear();
            List lstArtwork=null;
            List lstArtworkDeatails = new ArrayList();
            objArtworkAction = new ArtworkAction();
            lstArtworkDeatails = objArtworkAction.lstImportArtwork(objArtwork);
            if(lstArtworkDeatails.size()==0){
                GP_container.getChildren().add(new Text(objDictionaryAction.getWord("ARTWORK")+" - "+objDictionaryAction.getWord("NOVALUE")));
            }else{  
                for (int i=0, j = lstArtworkDeatails.size(), k=0; i<j;i++,k+=3){
                    lstArtwork = (ArrayList)lstArtworkDeatails.get(i);

                    byte[] bytArtworkThumbnil = (byte[])lstArtwork.get(2);
                    SeekableStream stream = new ByteArraySeekableStream(bytArtworkThumbnil);
                    String[] names = ImageCodec.getDecoderNames(stream);
                    ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                    RenderedImage im = dec.decodeAsRenderedImage();
                    BufferedImage bufferedImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
                    Image image=SwingFXUtils.toFXImage(bufferedImage, null);
                    ImageView ivArtwork = new ImageView(image);
                    ivArtwork.setFitHeight(222);
                    ivArtwork.setFitWidth(222);
                    GP_container.add(ivArtwork, k%6, i/2);
                    stream.close();
                    final String strAccess = new IDGenerator().getUserAcessValueData("ARTWORK_LIBRARY",lstArtwork.get(6).toString());
                    int intUsage=0;
                    objArtworkAction = new ArtworkAction();
                    intUsage = objArtworkAction.countArtworkUsage(lstArtwork.get(0).toString());
                                        
                    String strTooltip = 
                            objDictionaryAction.getWord("NAME")+": "+lstArtwork.get(1).toString()+"\n"+
                            objDictionaryAction.getWord("ARTWORKLENGTH")+": "+bufferedImage.getHeight()+"\n"+
                            objDictionaryAction.getWord("ARTWORKWIDTH")+": "+bufferedImage.getWidth()+"\n"+
                            objDictionaryAction.getWord("BACKGROUND")+": "+lstArtwork.get(3).toString()+"\n"+
                            objDictionaryAction.getWord("USED")+": "+intUsage+"\n"+
                            objDictionaryAction.getWord("PERMISSION")+": "+strAccess+"\n"+
                            objDictionaryAction.getWord("BY")+": "+lstArtwork.get(5).toString()+"\n"+
                            objDictionaryAction.getWord("DATE")+": "+lstArtwork.get(4).toString();
                    final Label lblArtwork = new Label(strTooltip);
                    lblArtwork.setId(lstArtwork.get(0).toString());
                    lblArtwork.setTooltip(new Tooltip(strTooltip));
                    bufferedImage = null;
                    GP_container.add(lblArtwork, (k+1)%6, i/2);
                    
                    VBox action = new VBox();
                    if(!strAccess.equalsIgnoreCase("Public")){
                        Button btnExport = new Button(objDictionaryAction.getWord("EXPORT"));
                        btnExport.setGraphic(new ImageView(objConfiguration.getStrColour()+"/export.png"));
                        btnExport.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEXPORT")));
                        btnExport.setMaxWidth(Double.MAX_VALUE);
                        btnExport.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                System.gc();
                                final Stage dialogStage = new Stage();
                                dialogStage.initStyle(StageStyle.UTILITY);
                                dialogStage.initModality(Modality.APPLICATION_MODAL);
                                dialogStage.setResizable(false);
                                dialogStage.setIconified(false);
                                dialogStage.setFullScreen(false);
                                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ALERT"));
                                BorderPane root = new BorderPane();
                                Scene scene = new Scene(root, 300, 200, Color.WHITE);
                                scene.getStylesheets().add(UtilityViewOld.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                                final GridPane popup=new GridPane();
                                popup.setId("popup");
                                popup.setHgap(5);
                                popup.setVgap(5);
                                popup.setPadding(new Insets(5, 5, 5, 5));
                                Label lblPassword=new Label(objDictionaryAction.getWord("SERVICEPASSWORD"));
                                lblPassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                popup.add(lblPassword, 0, 0, 2, 1);
                                final PasswordField passPF =new PasswordField();
                                passPF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                passPF.setPromptText(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
                                popup.add(passPF, 0, 1, 2, 1);
                                final Label lblAlert=new Label("");
                                lblAlert.setStyle("-fx-wrap-text:true;");
                                lblAlert.setPrefWidth(250);
                                popup.add(lblAlert, 0, 3, 2, 1);
                                Button btnYes= new Button(objDictionaryAction.getWord("OK"));
                                btnYes.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOK")));
                                btnYes.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
                                btnYes.setDefaultButton(true);
                                btnYes.setOnAction(new EventHandler<ActionEvent>(){
                                    @Override
                                    public void handle(ActionEvent event) {
                                        if(Security.SecurePassword(passPF.getText(), objConfiguration.getObjUser().getStrUsername()).equals(objConfiguration.getObjUser().getStrAppPassword())){
                                            dialogStage.close();
                                            System.gc();
                                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                                            Date date = new Date();
                                            String currentDate = dateFormat.format(date);
                                            String savePath = System.getProperty("user.dir");

                                            DirectoryChooser directoryChooser=new DirectoryChooser();
                                            simulatorStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORT")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
                                            File selectedDirectory = directoryChooser.showDialog(simulatorStage);
                                            if(selectedDirectory != null)
                                                savePath = selectedDirectory.getPath();
                                            savePath = savePath+"\\"+objDictionaryAction.getWord("PROJECT")+"_"+currentDate+"\\";
                                            File file = new File(savePath);
                                            if (!file.exists()) {
                                                if (!file.mkdir())
                                                    savePath = System.getProperty("user.dir");
                                            }
                                            savePath = savePath +currentDate+".sql";
                                            file =new File(savePath);
                                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(savePath))) {
                                                String content = new DbUtility().exportEachUserDesign(objConfiguration,lblArtwork.getId().toString());
                                                bw.write(content);
                                                // no need to close it.
                                                bw.close();
                                                ArrayList<File> filesToZip=new ArrayList<>();
                                                filesToZip.add(file);
                                                String zipFilePath=file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf(".sql"))+".zip";
                                                String passwordToZip = file.getName().substring(0, file.getName().indexOf(".sql"));
                                                new EncryptZip(zipFilePath, filesToZip, passwordToZip);
                                                file.delete();
                                                lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+savePath);
                                            } catch (IOException ex) {
                                                new Logging("SEVERE",UtilityViewOld.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                            } catch (SQLException ex) {
                                                new Logging("SEVERE",UtilityViewOld.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                            }
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
                                popup.add(btnYes, 0, 2, 1, 1);
                                Button btnNo = new Button(objDictionaryAction.getWord("CANCEL"));
                                btnNo.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
                                btnNo.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
                                btnNo.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent t) {
                                        dialogStage.close();
                                        System.gc();
                                    }
                                });
                                popup.add(btnNo, 1, 2, 1, 1);
                                root.setCenter(popup);
                                dialogStage.setScene(scene);
                                dialogStage.showAndWait();  
                                System.gc();
                            }
                        });
                        action.getChildren().add(btnExport);
                    }
                    if(!strAccess.equalsIgnoreCase("Public") && intUsage==0){
                        Button btnDelete = new Button(objDictionaryAction.getWord("DELETE"));
                        btnDelete.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
                        btnDelete.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDELETE")));
                        btnDelete.setMaxWidth(Double.MAX_VALUE);
                        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                System.gc();
                                final Stage dialogStage = new Stage();
                                dialogStage.initStyle(StageStyle.UTILITY);
                                dialogStage.initModality(Modality.APPLICATION_MODAL);
                                dialogStage.setResizable(false);
                                dialogStage.setIconified(false);
                                dialogStage.setFullScreen(false);
                                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ALERT"));
                                BorderPane root = new BorderPane();
                                Scene scene = new Scene(root, 300, 200, Color.WHITE);
                                scene.getStylesheets().add(UtilityViewOld.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                                final GridPane popup=new GridPane();
                                popup.setId("popup");
                                popup.setHgap(5);
                                popup.setVgap(5);
                                popup.setPadding(new Insets(5, 5, 5, 5));
                                popup.add(new ImageView(objConfiguration.getStrColour()+"/stop.png"), 0, 0);
                                
                                Label lblPassword=new Label(objDictionaryAction.getWord("SERVICEPASSWORD"));
                                lblPassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                popup.add(lblPassword, 0, 1, 2, 1);
                                final PasswordField passPF= new PasswordField();
                                passPF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                passPF.setPromptText(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
                                popup.add(passPF, 0, 2, 2, 1);
                                
                                final Label lblAlert = new Label(objDictionaryAction.getWord("ALERTDELETE"));
                                lblAlert.setStyle("-fx-wrap-text:true;");
                                lblAlert.setPrefWidth(250);
                                popup.add(lblAlert, 1, 0);
                                Button btnYes = new Button(objDictionaryAction.getWord("YES"));
                                btnYes.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOK")));
                                btnYes.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
                                btnYes.setDefaultButton(true);
                                btnYes.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent t) {
                                        if(passPF.getText().length()==0){
                                            lblAlert.setText(objDictionaryAction.getWord("NOSERVICEPASSWORD"));
                                        }
                                        else{
                                            if(Security.SecurePassword(passPF.getText(), objConfiguration.getObjUser().getStrUsername()).equals(objConfiguration.getObjUser().getStrAppPassword())){
                                                try {
                                                    objArtworkAction = new ArtworkAction();
                                                    objArtworkAction.clearArtwork(lblArtwork.getId().toString());
                                                    loadLibraryArtwork();
                                                    lblStatus.setText(lblArtwork.getId()+" : "+objDictionaryAction.getWord("ACTIONDELETE"));                                            
                                                } catch (SQLException ex) {
                                                    new Logging("SEVERE",UtilityViewOld.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                                } catch (Exception ex) {
                                                    new Logging("SEVERE",UtilityViewOld.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                                }
                                                dialogStage.close();
                                            }
                                            else{
                                                lblAlert.setText(objDictionaryAction.getWord("INVALIDSERVICEPASSWORD"));
                                            }
                                        }
                                        System.gc();
                                    }
                                });
                                popup.add(btnYes, 0, 3);
                                Button btnNo = new Button(objDictionaryAction.getWord("NO"));
                                btnNo.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
                                btnNo.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
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
                                dialogStage.showAndWait();  
                                e.consume();
                                System.gc();
                            }
                        });
                        action.getChildren().add(btnDelete);
                    }
                    if(!strAccess.equalsIgnoreCase("Public") && intUsage==0){
                        Button btnExportDelete = new Button(objDictionaryAction.getWord("EXPORTDELETE"));
                        btnExportDelete.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
                        btnExportDelete.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEXPORTDELETE")));
                        btnExportDelete.setMaxWidth(Double.MAX_VALUE);
                        btnExportDelete.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                System.gc();
                                final Stage dialogStage = new Stage();
                                dialogStage.initStyle(StageStyle.UTILITY);
                                dialogStage.initModality(Modality.APPLICATION_MODAL);
                                dialogStage.setResizable(false);
                                dialogStage.setIconified(false);
                                dialogStage.setFullScreen(false);
                                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ALERT"));
                                BorderPane root = new BorderPane();
                                Scene scene = new Scene(root, 300, 200, Color.WHITE);
                                scene.getStylesheets().add(UtilityViewOld.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                                final GridPane popup=new GridPane();
                                popup.setId("popup");
                                popup.setHgap(5);
                                popup.setVgap(5);
                                popup.setPadding(new Insets(5, 5, 5, 5));
                                Label lblPassword=new Label(objDictionaryAction.getWord("SERVICEPASSWORD"));
                                lblPassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                popup.add(lblPassword, 0, 0, 2, 1);
                                final PasswordField passPF =new PasswordField();
                                passPF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                passPF.setPromptText(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
                                popup.add(passPF, 0, 1, 2, 1);
                                final Label lblAlert=new Label("");
                                lblAlert.setStyle("-fx-wrap-text:true;");
                                lblAlert.setPrefWidth(250);
                                popup.add(lblAlert, 0, 3, 2, 1);
                                Button btnYes = new Button(objDictionaryAction.getWord("OK"));
                                btnYes.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOK")));
                                btnYes.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
                                btnYes.setDefaultButton(true);
                                btnYes.setOnAction(new EventHandler<ActionEvent>(){
                                    @Override
                                    public void handle(ActionEvent event) {
                                        if(Security.SecurePassword(passPF.getText(), objConfiguration.getObjUser().getStrUsername()).equals(objConfiguration.getObjUser().getStrAppPassword())){
                                            dialogStage.close();
                                            System.gc();
                                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                                            Date date = new Date();
                                            String currentDate = dateFormat.format(date);
                                            String savePath = System.getProperty("user.dir");

                                            DirectoryChooser directoryChooser=new DirectoryChooser();
                                            simulatorStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORT")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
                                            File selectedDirectory = directoryChooser.showDialog(simulatorStage);
                                            if(selectedDirectory != null)
                                                savePath = selectedDirectory.getPath();
                                            savePath = savePath+"\\"+objDictionaryAction.getWord("PROJECT")+"_"+currentDate+"\\";
                                            File file = new File(savePath);
                                            if (!file.exists()) {
                                                if (!file.mkdir())
                                                    savePath = System.getProperty("user.dir");
                                            }
                                            savePath = savePath +currentDate+".sql";
                                            file =new File(savePath);
                                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(savePath))) {
                                                String content = new DbUtility().exportEachUserDesign(objConfiguration,lblArtwork.getId().toString());
                                                bw.write(content);
                                                // no need to close it.
                                                //bw.close();
                                                ArrayList<File> filesToZip=new ArrayList<>();
                                                filesToZip.add(file);
                                                String zipFilePath=file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf(".sql"))+".zip";
                                                String passwordToZip = file.getName().substring(0, file.getName().indexOf(".sql"));
                                                new EncryptZip(zipFilePath, filesToZip, passwordToZip);
                                                file.delete();
                                                lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+savePath);
                                                objArtworkAction = new ArtworkAction();
                                                objArtworkAction.clearArtwork(lblArtwork.getId().toString());
                                                loadLibraryArtwork();
                                            } catch (IOException ex) {
                                                new Logging("SEVERE",UtilityViewOld.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                            } catch (SQLException ex) {
                                                new Logging("SEVERE",UtilityViewOld.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                            }
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
                                popup.add(btnYes, 0, 2, 1, 1);
                                Button btnNo = new Button(objDictionaryAction.getWord("CANCEL"));
                                btnNo.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
                                btnNo.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
                                btnNo.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent t) {
                                        dialogStage.close();
                                        System.gc();
                                    }
                                });
                                popup.add(btnNo, 1, 2, 1, 1);
                                root.setCenter(popup);
                                dialogStage.setScene(scene);
                                dialogStage.showAndWait();  
                                System.gc();
                            }
                        });
                        action.getChildren().add(btnExportDelete);
                    }
                    if(!strAccess.equalsIgnoreCase("Public")){
                        Button btnPermission = new Button(objDictionaryAction.getWord("CHANGEPERMISSION"));
                        btnPermission.setGraphic(new ImageView(objConfiguration.getStrColour()+"/settings.png"));
                        btnPermission.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCHANGEPERMISSION")));
                        btnPermission.setMaxWidth(Double.MAX_VALUE);
                        btnPermission.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                final Stage dialogStage = new Stage();
                                dialogStage.initStyle(StageStyle.UTILITY);
                                dialogStage.initModality(Modality.APPLICATION_MODAL);
                                dialogStage.setResizable(false);
                                dialogStage.setIconified(false);
                                dialogStage.setFullScreen(false);
                                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ALERT"));
                                BorderPane root = new BorderPane();
                                Scene scene = new Scene(root, 300, 180, Color.WHITE);
                                scene.getStylesheets().add(UtilityViewOld.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                                final GridPane popup=new GridPane();
                                popup.setId("popup");
                                popup.setHgap(5);
                                popup.setVgap(5);
                                popup.setPadding(new Insets(25, 25, 25, 25));
                                
                                Label lblData = new Label(objDictionaryAction.getWord("SELECT")+" "+objDictionaryAction.getWord("PERMISSION"));
                                lblData.setStyle("-fx-wrap-text:true;");
                                lblData.setPrefWidth(250);
                                popup.add(lblData, 0, 0, 3, 1);
                                final ToggleGroup dataTG = new ToggleGroup();
                                RadioButton dataPublicRB = new RadioButton(objDictionaryAction.getWord("PUBLIC"));
                                dataPublicRB.setToggleGroup(dataTG);
                                dataPublicRB.setUserData("Public");
                                popup.add(dataPublicRB, 0, 1);
                                RadioButton dataPrivateRB = new RadioButton(objDictionaryAction.getWord("PRIVATE"));
                                dataPrivateRB.setToggleGroup(dataTG);
                                dataPrivateRB.setUserData("Private");
                                popup.add(dataPrivateRB, 1, 1);
                                RadioButton dataProtectedRB = new RadioButton(objDictionaryAction.getWord("PROTECTED"));
                                dataProtectedRB.setToggleGroup(dataTG);
                                dataProtectedRB.setUserData("Protected");
                                popup.add(dataProtectedRB, 2, 1);
                                if(strAccess.equalsIgnoreCase("Public"))
                                    dataTG.selectToggle(dataPublicRB);
                                else if(strAccess.equalsIgnoreCase("Private"))
                                    dataTG.selectToggle(dataPrivateRB);
                                else 
                                    dataTG.selectToggle(dataProtectedRB);
                                
                                final Label lblPassword=new Label(objDictionaryAction.getWord("SERVICEPASSWORD"));
                                lblPassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                popup.add(lblPassword, 0, 2, 1, 1);
                                final PasswordField passPF=new PasswordField();
                                passPF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                passPF.setPromptText(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
                                popup.add(passPF, 1, 2, 2, 1);
                                                                
                                final Label lblAlert = new Label(objDictionaryAction.getWord("ALERTWHAT"));
                                lblAlert.setStyle("-fx-wrap-text:true;");
                                lblAlert.setPrefWidth(250);
                                popup.add(lblAlert, 0, 4, 3, 1);
                                
                                Button btnYes = new Button(objDictionaryAction.getWord("OK"));
                                btnYes.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOK")));
                                btnYes.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
                                btnYes.setDefaultButton(true);
                                btnYes.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent t) {
                                        if(passPF.getText().length()==0){
                                            lblAlert.setText(objDictionaryAction.getWord("NOSERVICEPASSWORD"));
                                        }
                                        else{
                                            if(Security.SecurePassword(passPF.getText(), objConfiguration.getObjUser().getStrUsername()).equals(objConfiguration.getObjUser().getStrAppPassword())){
                                                try {
                                                    String strAccessNew = dataTG.getSelectedToggle().getUserData().toString();
                                                    System.err.println(strAccessNew);
                                                    strAccessNew = new IDGenerator().setUserAcessValueData("ARTWORK_LIBRARY",strAccessNew);
                                                    objArtworkAction = new ArtworkAction();
                                                    objArtworkAction.resetArtworkPermission(lblArtwork.getId(),strAccessNew);
                                                    dialogStage.close();                                            
                                                    loadLibraryArtwork();
                                                } catch (SQLException ex) {
                                                    new Logging("SEVERE",UtilityViewOld.class.getName(),"Change Permission Data"+ex.getMessage(),ex);
                                                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                                }
                                                System.gc();
                                            }
                                            else{
                                                lblAlert.setText(objDictionaryAction.getWord("INVALIDSERVICEPASSWORD"));
                                            }
                                        }
                                        System.gc();
                                    }
                                });
                                popup.add(btnYes, 0, 3);
                                Button btnNo = new Button(objDictionaryAction.getWord("CANCEL"));
                                btnNo.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
                                btnNo.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
                                btnNo.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent t) {
                                        dialogStage.close();
                                        System.gc();
                                    }
                                });
                                popup.add(btnNo, 2, 3);
                                root.setCenter(popup);
                                dialogStage.setScene(scene);
                                dialogStage.showAndWait();  
                                e.consume();
                                System.gc();
                            }
                        });
                        action.getChildren().add(btnPermission);
                    }
                    Button btnUpdate;
                    if(!strAccess.equalsIgnoreCase("Public") && intUsage==0){
                        btnUpdate = new Button(objDictionaryAction.getWord("UPDATE"));
                        btnUpdate.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
                        btnUpdate.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPUPDATE")));
                    }else{
                        btnUpdate = new Button(objDictionaryAction.getWord("COPY"));
                        btnUpdate.setGraphic(new ImageView(objConfiguration.getStrColour()+"/copy.png"));
                        btnUpdate.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOPY")));
                    }
                    btnUpdate.setMaxWidth(Double.MAX_VALUE);
                    btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            if(strAccess.equalsIgnoreCase("Public")){
                                objConfiguration.setServicePasswordValid(true);
                            } else{
                                new MessageView(objConfiguration);
                            }
                            if(objConfiguration.getServicePasswordValid()){
                                objConfiguration.setServicePasswordValid(false);
                                objConfiguration.strWindowFlowContext = "Dashboard";
                                objConfiguration.setStrRecentArtwork(lblArtwork.getId());
                                simulatorStage.close();
                                System.gc();
                                ArtworkView objArtworkView = new ArtworkView(objConfiguration);
                                System.gc();
                            }
                        }
                    });
                    action.getChildren().add(btnUpdate);
                    
                    Button btnFabric = new Button(objDictionaryAction.getWord("USEINFABRIC"));
                    btnFabric.setGraphic(new ImageView(objConfiguration.getStrColour()+"/fabric_editor.png"));
                    btnFabric.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPAPUSEINFABRIC")));
                    btnFabric.setMaxWidth(Double.MAX_VALUE);
                    btnFabric.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                System.gc();
                                final Stage dialogStage = new Stage();
                                dialogStage.initStyle(StageStyle.UTILITY);
                                dialogStage.initModality(Modality.APPLICATION_MODAL);
                                dialogStage.setResizable(false);
                                dialogStage.setIconified(false);
                                dialogStage.setFullScreen(false);
                                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ALERT"));
                                BorderPane root = new BorderPane();
                                Scene scene = new Scene(root, 300, 200, Color.WHITE);
                                scene.getStylesheets().add(UtilityViewOld.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                                final GridPane popup=new GridPane();
                                popup.setId("popup");
                                popup.setHgap(5);
                                popup.setVgap(5);
                                popup.setPadding(new Insets(5, 5, 5, 5));
                                Label lblPassword=new Label(objDictionaryAction.getWord("SERVICEPASSWORD"));
                                lblPassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                popup.add(lblPassword, 0, 0, 2, 1);
                                final PasswordField passPF =new PasswordField();
                                passPF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                passPF.setPromptText(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
                                popup.add(passPF, 0, 1, 2, 1);
                                final Label lblAlert=new Label("");
                                lblAlert.setStyle("-fx-wrap-text:true;");
                                lblAlert.setPrefWidth(250);
                                popup.add(lblAlert, 0, 3, 2, 1);
                                Button btnYes= new Button(objDictionaryAction.getWord("OK"));
                                btnYes.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOK")));
                                btnYes.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
                                btnYes.setDefaultButton(true);
                                btnYes.setOnAction(new EventHandler<ActionEvent>(){
                                    @Override
                                    public void handle(ActionEvent event) {
                                        if(Security.SecurePassword(passPF.getText(), objConfiguration.getObjUser().getStrUsername()).equals(objConfiguration.getObjUser().getStrAppPassword())){
                                            System.gc();
                                            objConfiguration.strWindowFlowContext = "ArtworkEditor";
                                            objConfiguration.setStrRecentArtwork(lblArtwork.getId().toString());
                                            simulatorStage.close();
                                            FabricView objFabricView = new FabricView(objConfiguration);
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
                                popup.add(btnYes, 0, 2);
                                Button btnNo = new Button(objDictionaryAction.getWord("CANCEL"));
                                btnNo.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
                                btnNo.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
                                btnNo.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent t) {
                                        dialogStage.close();
                                        System.gc();
                                    }
                                });
                                popup.add(btnNo, 1, 2);
                                root.setCenter(popup);
                                dialogStage.setScene(scene);
                                dialogStage.showAndWait();  
                                System.gc();
                            }
                        });
                    action.getChildren().add(btnFabric);
                    
                    GP_container.add(action, (k+2)%6, i/2);
                }
            }
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public void loadLibraryWeave(){
        containerGP.getChildren().clear();
        System.gc();
        objWeave = new Weave();
        objWeave.setObjConfiguration(objConfiguration);
        objWeave.setStrCondition("");
        objWeave.setStrSearchBy("All");
        objWeave.setStrOrderBy("Name");
        objWeave.setStrDirection("Ascending");
        
        containerGP.add(new Text(objDictionaryAction.getWord("WEAVE")+" "+objDictionaryAction.getWord("NAME")),0,0);
        containerGP.add(new Text(objDictionaryAction.getWord("WEAVE")+" "+objDictionaryAction.getWord("SEARCHBY")),2,0);
        containerGP.add(new Text(objDictionaryAction.getWord("WEAVE")+" "+objDictionaryAction.getWord("SORTBY")),4,0);
        containerGP.add(new Text(objDictionaryAction.getWord("WEAVE")+" "+objDictionaryAction.getWord("SORTDIRCTION")),6,0);
        
        final TextField libName = new TextField();
        libName.setPromptText(objDictionaryAction.getWord("PROMPTNAME"));
        libName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                objWeave.setStrCondition(libName.getText());
                populateLibraryWeave(); 
            }
        });
        ComboBox libSearch = new ComboBox();
        libSearch.getItems().addAll(
            "All",
            "Plain",
            "Twill",
            "Satin",
            "Basket",
            "Sateen"
        );
        libSearch.setPromptText(objDictionaryAction.getWord("SEARCHBY"));
        libSearch.setEditable(false); 
        libSearch.setValue("All"); 
        libSearch.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objWeave.setStrSearchBy(newValue);
                populateLibraryWeave();
            }    
        });
        ComboBox libSort = new ComboBox();
        libSort.getItems().addAll(
            "Name",
            "Date",
            "Shaft",
            "Treadles",
            "Float X",
            "Float Y"
        );
        libSort.setPromptText(objDictionaryAction.getWord("SORTBY"));
        libSort.setEditable(false); 
        libSort.setValue("Name"); 
        libSort.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objWeave.setStrOrderBy(newValue);
                populateLibraryWeave();
            }    
        });
        ComboBox libDirction = new ComboBox();
        libDirction.getItems().addAll(
            "Ascending",
            "Descending"
        );
        libDirction.setPromptText(objDictionaryAction.getWord("SORTDIRCTION"));
        libDirction.setEditable(false); 
        libDirction.setValue("Ascending"); 
        libDirction.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objWeave.setStrDirection(newValue);
                populateLibraryWeave();
            }    
        });
        
        containerGP.add(libName,1,0);
        containerGP.add(libSearch,3,0);
        containerGP.add(libSort,5,0);
        containerGP.add(libDirction,7,0);
        
        Separator sepHor = new Separator();
        sepHor.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor, 0, 1);
        GridPane.setColumnSpan(sepHor, 8);
        containerGP.getChildren().add(sepHor);
    
        GP_container = new GridPane();         
        GP_container.setAlignment(Pos.TOP_LEFT);
        GP_container.setHgap(20);
        GP_container.setVgap(20);
        GP_container.setPadding(new Insets(1, 1, 1, 1));
        containerGP.add(GP_container,0,2,8,1);
        
        populateLibraryWeave();
        //container.setContent(containerGP);       
    }
    private void populateLibraryWeave(){
        try{
            GP_container.getChildren().clear();
            List lstWeave=null;
            List lstWeaveDeatails = new ArrayList();
            objWeaveAction = new WeaveAction(objWeave,true);
            lstWeaveDeatails = objWeaveAction.lstImportWeave(objWeave);
            if(lstWeaveDeatails.size()==0){
                GP_container.getChildren().add(new Text(objDictionaryAction.getWord("WEAVE")+" - "+objDictionaryAction.getWord("NOVALUE")));
            }else{            
                System.err.println(lstWeaveDeatails.size());
                for (int i=0, k=0; i<lstWeaveDeatails.size();i++, k+=3){
                    lstWeave = (ArrayList)lstWeaveDeatails.get(i);

                    byte[] bytWeaveThumbnil = (byte[])lstWeave.get(2);
                    SeekableStream stream = new ByteArraySeekableStream(bytWeaveThumbnil);
                    String[] names = ImageCodec.getDecoderNames(stream);
                    ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                    RenderedImage im = dec.decodeAsRenderedImage();
                    BufferedImage bufferedImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
                    Image image=SwingFXUtils.toFXImage(bufferedImage, null);
                    ImageView ivWeave = new ImageView(image);
                    ivWeave.setFitHeight(222);
                    ivWeave.setFitWidth(222);
                    stream.close();
                    bufferedImage = null;
                    GP_container.add(ivWeave, k%6, i/2);
                    final String strAccess = new IDGenerator().getUserAcessValueData("WEAVE_LIBRARY",lstWeave.get(15).toString());
                    int intUsage=0;
                    objWeaveAction = new WeaveAction();
                    intUsage = objWeaveAction.countWeaveUsage(lstWeave.get(0).toString());
                    
                    String strTooltip = 
                                objDictionaryAction.getWord("NAME")+": "+lstWeave.get(1).toString()+"\n"+
                                objDictionaryAction.getWord("SHAFT")+": "+lstWeave.get(7).toString()+"\n"+
                                objDictionaryAction.getWord("TRADELES")+": "+lstWeave.get(8).toString()+"\n"+
                                objDictionaryAction.getWord("WEFTREPEAT")+": "+lstWeave.get(9).toString()+"\n"+
                                objDictionaryAction.getWord("WARPREPEAT")+": "+lstWeave.get(10).toString()+"\n"+
                                objDictionaryAction.getWord("WEFTFLOAT")+": "+lstWeave.get(11).toString()+"\n"+
                                objDictionaryAction.getWord("WARPFLOAT")+": "+lstWeave.get(12).toString()+"\n"+
                                objDictionaryAction.getWord("WEAVECATEGORY")+": "+lstWeave.get(4).toString()+"\n"+
                                objDictionaryAction.getWord("WEAVETYPE")+": "+lstWeave.get(3).toString()+"\n"+
                                objDictionaryAction.getWord("ISLIFTPLAN")+": "+lstWeave.get(5).toString()+"\n"+
                                objDictionaryAction.getWord("ISCOLOR")+": "+lstWeave.get(6).toString()+"\n"+
                                objDictionaryAction.getWord("USED")+": "+intUsage+"\n"+
                                objDictionaryAction.getWord("PERMISSION")+": "+strAccess+"\n"+
                                objDictionaryAction.getWord("BY")+": "+lstWeave.get(14).toString()+"\n"+
                                objDictionaryAction.getWord("DATE")+": "+lstWeave.get(13).toString();
                    final Label lblWeave = new Label(strTooltip);
                    lblWeave.setId(lstWeave.get(0).toString());
                    lblWeave.setTooltip(new Tooltip(strTooltip));
                    GP_container.add(lblWeave, (k+1)%6, i/2);
                    
                    VBox action = new VBox();
                    if(!strAccess.equalsIgnoreCase("Public")){
                        Button btnExport = new Button(objDictionaryAction.getWord("EXPORT"));
                        btnExport.setGraphic(new ImageView(objConfiguration.getStrColour()+"/export.png"));
                        btnExport.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEXPORT")));
                        btnExport.setMaxWidth(Double.MAX_VALUE);
                        btnExport.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                //
                                System.gc();
                                final Stage dialogStage = new Stage();
                                dialogStage.initStyle(StageStyle.UTILITY);
                                dialogStage.initModality(Modality.APPLICATION_MODAL);
                                dialogStage.setResizable(false);
                                dialogStage.setIconified(false);
                                dialogStage.setFullScreen(false);
                                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ALERT"));
                                BorderPane root = new BorderPane();
                                Scene scene = new Scene(root, 300, 200, Color.WHITE);
                                scene.getStylesheets().add(UtilityViewOld.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                                final GridPane popup=new GridPane();
                                popup.setId("popup");
                                popup.setHgap(5);
                                popup.setVgap(5);
                                popup.setPadding(new Insets(5, 5, 5, 5));
                                Label lblPassword=new Label(objDictionaryAction.getWord("SERVICEPASSWORD"));
                                lblPassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                popup.add(lblPassword, 0, 0, 2, 1);
                                final PasswordField passPF =new PasswordField();
                                passPF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                passPF.setPromptText(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
                                popup.add(passPF, 0, 1, 2, 1);
                                final Label lblAlert=new Label("");
                                lblAlert.setStyle("-fx-wrap-text:true;");
                                lblAlert.setPrefWidth(250);
                                popup.add(lblAlert, 0, 3, 2, 1);
                                Button btnYes= new Button(objDictionaryAction.getWord("OK"));
                                btnYes.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOK")));
                                btnYes.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
                                btnYes.setDefaultButton(true);
                                btnYes.setOnAction(new EventHandler<ActionEvent>(){
                                    @Override
                                    public void handle(ActionEvent event) {
                                        if(Security.SecurePassword(passPF.getText(), objConfiguration.getObjUser().getStrUsername()).equals(objConfiguration.getObjUser().getStrAppPassword())){
                                            System.gc();
                                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                                            Date date = new Date();
                                            String currentDate = dateFormat.format(date);
                                            String savePath = System.getProperty("user.dir");

                                            DirectoryChooser directoryChooser=new DirectoryChooser();
                                            simulatorStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORT")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
                                            File selectedDirectory = directoryChooser.showDialog(simulatorStage);
                                            if(selectedDirectory != null)
                                                savePath = selectedDirectory.getPath();
                                            savePath = savePath+"\\"+objDictionaryAction.getWord("PROJECT")+"_"+currentDate+"\\";
                                            File file = new File(savePath);
                                            if (!file.exists()) {
                                                if (!file.mkdir())
                                                    savePath = System.getProperty("user.dir");
                                            }
                                            savePath = savePath+currentDate+".sql";
                                            file =new File(savePath);
                                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(savePath))) {
                                                String content = new DbUtility().exportEachUserWeave(objConfiguration,lblWeave.getId().toString());
                                                bw.write(content);
                                                // no need to close it.
                                                bw.close();
                                                ArrayList<File> filesToZip=new ArrayList<>();
                                                filesToZip.add(file);
                                                String zipFilePath=file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf(".sql"))+".zip";
                                                String passwordToZip = file.getName().substring(0, file.getName().indexOf(".sql"));
                                                new EncryptZip(zipFilePath, filesToZip, passwordToZip);
                                                file.delete();
                                                lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+savePath);
                                            } catch (IOException ex) {
                                                new Logging("SEVERE",UtilityViewOld.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                            } catch (SQLException ex) {
                                                new Logging("SEVERE",UtilityViewOld.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                            }
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
                                popup.add(btnYes, 0, 2, 1, 1);
                                Button btnNo = new Button(objDictionaryAction.getWord("CANCEL"));
                                btnNo.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
                                btnNo.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
                                btnNo.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent t) {
                                        dialogStage.close();
                                        System.gc();
                                    }
                                });
                                popup.add(btnNo, 1, 2, 1, 1);
                                root.setCenter(popup);
                                dialogStage.setScene(scene);
                                dialogStage.showAndWait();  
                                System.gc();
                            }
                        });
                        action.getChildren().add(btnExport);
                    }
                    if(!strAccess.equalsIgnoreCase("Public") && intUsage==0){
                        Button btnDelete = new Button(objDictionaryAction.getWord("DELETE"));
                        btnDelete.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
                        btnDelete.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDELETE")));
                        btnDelete.setMaxWidth(Double.MAX_VALUE);
                        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                System.gc();
                                final Stage dialogStage = new Stage();
                                dialogStage.initStyle(StageStyle.UTILITY);
                                dialogStage.initModality(Modality.APPLICATION_MODAL);
                                dialogStage.setResizable(false);
                                dialogStage.setIconified(false);
                                dialogStage.setFullScreen(false);
                                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ALERT"));
                                BorderPane root = new BorderPane();
                                Scene scene = new Scene(root, 300, 200, Color.WHITE);
                                scene.getStylesheets().add(UtilityViewOld.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                                final GridPane popup=new GridPane();
                                popup.setId("popup");
                                popup.setHgap(5);
                                popup.setVgap(5);
                                popup.setPadding(new Insets(5, 5, 5, 5));
                                popup.add(new ImageView(objConfiguration.getStrColour()+"/stop.png"), 0, 0);
                                
                                Label lblPassword=new Label(objDictionaryAction.getWord("SERVICEPASSWORD"));
                                lblPassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                popup.add(lblPassword, 0, 1, 2, 1);
                                final PasswordField passPF= new PasswordField();
                                passPF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                passPF.setPromptText(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
                                popup.add(passPF, 0, 2, 2, 1);
                                
                                final Label lblAlert = new Label(objDictionaryAction.getWord("ALERTDELETE"));
                                lblAlert.setStyle("-fx-wrap-text:true;");
                                lblAlert.setPrefWidth(250);
                                popup.add(lblAlert, 1, 0);
                                Button btnYes = new Button(objDictionaryAction.getWord("YES"));
                                btnYes.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOK")));
                                btnYes.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
                                btnYes.setDefaultButton(true);
                                btnYes.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent t) {
                                        if(passPF.getText().length()==0){
                                            lblAlert.setText(objDictionaryAction.getWord("NOSERVICEPASSWORD"));
                                        }
                                        else{
                                            if(Security.SecurePassword(passPF.getText(), objConfiguration.getObjUser().getStrUsername()).equals(objConfiguration.getObjUser().getStrAppPassword())){
                                                try {
                                                    objWeaveAction = new WeaveAction();
                                                    objWeaveAction.clearWeave(lblWeave.getId().toString());
                                                    loadLibraryWeave();
                                                    lblStatus.setText(lblWeave.getId()+" : "+objDictionaryAction.getWord("ACTIONDELETE"));
                                                } catch (SQLException ex) {
                                                    new Logging("SEVERE",UtilityViewOld.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                                } catch (Exception ex) {
                                                    new Logging("SEVERE",UtilityViewOld.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                                }
                                                dialogStage.close();
                                                System.gc();
                                            }
                                            else{
                                                lblAlert.setText(objDictionaryAction.getWord("INVALIDSERVICEPASSWORD"));
                                            }
                                        }
                                        System.gc();
                                    }
                                });
                                popup.add(btnYes, 0, 3);
                                Button btnNo = new Button(objDictionaryAction.getWord("NO"));
                                btnNo.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
                                btnNo.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
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
                                dialogStage.showAndWait();  
                                e.consume();
                                System.gc();
                            }
                        });
                        action.getChildren().add(btnDelete);
                    }
                    if(!strAccess.equalsIgnoreCase("Public") && intUsage==0){
                        Button btnExportDelete = new Button(objDictionaryAction.getWord("EXPORTDELETE"));
                        btnExportDelete.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
                        btnExportDelete.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEXPORTDELETE")));
                        btnExportDelete.setMaxWidth(Double.MAX_VALUE);
                        btnExportDelete.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                System.gc();
                                final Stage dialogStage = new Stage();
                                dialogStage.initStyle(StageStyle.UTILITY);
                                dialogStage.initModality(Modality.APPLICATION_MODAL);
                                dialogStage.setResizable(false);
                                dialogStage.setIconified(false);
                                dialogStage.setFullScreen(false);
                                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ALERT"));
                                BorderPane root = new BorderPane();
                                Scene scene = new Scene(root, 300, 200, Color.WHITE);
                                scene.getStylesheets().add(UtilityViewOld.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                                final GridPane popup=new GridPane();
                                popup.setId("popup");
                                popup.setHgap(5);
                                popup.setVgap(5);
                                popup.setPadding(new Insets(5, 5, 5, 5));
                                Label lblPassword=new Label(objDictionaryAction.getWord("SERVICEPASSWORD"));
                                lblPassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                popup.add(lblPassword, 0, 0, 2, 1);
                                final PasswordField passPF =new PasswordField();
                                passPF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                passPF.setPromptText(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
                                popup.add(passPF, 0, 1, 2, 1);
                                final Label lblAlert=new Label("");
                                lblAlert.setStyle("-fx-wrap-text:true;");
                                lblAlert.setPrefWidth(250);
                                popup.add(lblAlert, 0, 3, 2, 1);
                                Button btnYes = new Button(objDictionaryAction.getWord("OK"));
                                btnYes.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOK")));
                                btnYes.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
                                btnYes.setDefaultButton(true);
                                btnYes.setOnAction(new EventHandler<ActionEvent>(){
                                    @Override
                                    public void handle(ActionEvent event) {
                                        if(Security.SecurePassword(passPF.getText(), objConfiguration.getObjUser().getStrUsername()).equals(objConfiguration.getObjUser().getStrAppPassword())){
                                            System.gc();
                                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                                            Date date = new Date();
                                            String currentDate = dateFormat.format(date);
                                            String savePath = System.getProperty("user.dir");

                                            DirectoryChooser directoryChooser=new DirectoryChooser();
                                            simulatorStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORT")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
                                            File selectedDirectory = directoryChooser.showDialog(simulatorStage);
                                            if(selectedDirectory != null)
                                                savePath = selectedDirectory.getPath();
                                            savePath = savePath+"\\"+objDictionaryAction.getWord("PROJECT")+"_"+currentDate+"\\";
                                            File file = new File(savePath);
                                            if (!file.exists()) {
                                                if (!file.mkdir())
                                                    savePath = System.getProperty("user.dir");
                                            }
                                            savePath = savePath +currentDate+".sql";
                                            file =new File(savePath);
                                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(savePath))) {
                                                String content = new DbUtility().exportEachUserWeave(objConfiguration,lblWeave.getId().toString());
                                                bw.write(content);
                                                // no need to close it.
                                                //bw.close();
                                                ArrayList<File> filesToZip=new ArrayList<>();
                                                filesToZip.add(file);
                                                String zipFilePath=file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf(".sql"))+".zip";
                                                String passwordToZip = file.getName().substring(0, file.getName().indexOf(".sql"));
                                                new EncryptZip(zipFilePath, filesToZip, passwordToZip);
                                                file.delete();
                                                lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+savePath);
                                                objWeaveAction = new WeaveAction();
                                                objWeaveAction.clearWeave(lblWeave.getId().toString());
                                                loadLibraryWeave();
                                            } catch (IOException ex) {
                                                new Logging("SEVERE",UtilityViewOld.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                            } catch (SQLException ex) {
                                                new Logging("SEVERE",UtilityViewOld.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                            }
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
                                popup.add(btnYes, 0, 2, 1, 1);
                                Button btnNo = new Button(objDictionaryAction.getWord("CANCEL"));
                                btnNo.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
                                btnNo.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
                                btnNo.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent t) {
                                        dialogStage.close();
                                        System.gc();
                                    }
                                });
                                popup.add(btnNo, 1, 2, 1, 1);
                                root.setCenter(popup);
                                dialogStage.setScene(scene);
                                dialogStage.showAndWait();  
                                System.gc();
                            }
                        });
                        action.getChildren().add(btnExportDelete);
                    }
                    if(!strAccess.equalsIgnoreCase("Public")){
                        Button btnPermission = new Button(objDictionaryAction.getWord("CHANGEPERMISSION"));
                        btnPermission.setGraphic(new ImageView(objConfiguration.getStrColour()+"/settings.png"));
                        btnPermission.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCHANGEPERMISSION")));
                        btnPermission.setMaxWidth(Double.MAX_VALUE);
                        btnPermission.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                final Stage dialogStage = new Stage();
                                dialogStage.initStyle(StageStyle.UTILITY);
                                dialogStage.initModality(Modality.APPLICATION_MODAL);
                                dialogStage.setResizable(false);
                                dialogStage.setIconified(false);
                                dialogStage.setFullScreen(false);
                                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ALERT"));
                                BorderPane root = new BorderPane();
                                Scene scene = new Scene(root, 300, 180, Color.WHITE);
                                scene.getStylesheets().add(UtilityViewOld.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                                final GridPane popup=new GridPane();
                                popup.setId("popup");
                                popup.setHgap(5);
                                popup.setVgap(5);
                                popup.setPadding(new Insets(25, 25, 25, 25));
                                
                                Label lblData = new Label(objDictionaryAction.getWord("SELECT")+" "+objDictionaryAction.getWord("PERMISSION"));
                                lblData.setStyle("-fx-wrap-text:true;");
                                lblData.setPrefWidth(250);
                                popup.add(lblData, 0, 0, 3, 1);
                                final ToggleGroup dataTG = new ToggleGroup();
                                RadioButton dataPublicRB = new RadioButton(objDictionaryAction.getWord("PUBLIC"));
                                dataPublicRB.setToggleGroup(dataTG);
                                dataPublicRB.setUserData("Public");
                                popup.add(dataPublicRB, 0, 1);
                                RadioButton dataPrivateRB = new RadioButton(objDictionaryAction.getWord("PRIVATE"));
                                dataPrivateRB.setToggleGroup(dataTG);
                                dataPrivateRB.setUserData("Private");
                                popup.add(dataPrivateRB, 1, 1);
                                RadioButton dataProtectedRB = new RadioButton(objDictionaryAction.getWord("PROTECTED"));
                                dataProtectedRB.setToggleGroup(dataTG);
                                dataProtectedRB.setUserData("Protected");
                                popup.add(dataProtectedRB, 2, 1);
                                if(strAccess.equalsIgnoreCase("Public"))
                                    dataTG.selectToggle(dataPublicRB);
                                else if(strAccess.equalsIgnoreCase("Private"))
                                    dataTG.selectToggle(dataPrivateRB);
                                else 
                                    dataTG.selectToggle(dataProtectedRB);
                                
                                final Label lblPassword=new Label(objDictionaryAction.getWord("SERVICEPASSWORD"));
                                lblPassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                popup.add(lblPassword, 0, 2, 1, 1);
                                final PasswordField passPF=new PasswordField();
                                passPF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                passPF.setPromptText(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
                                popup.add(passPF, 1, 2, 2, 1);
                                                                
                                final Label lblAlert = new Label(objDictionaryAction.getWord("ALERTWHAT"));
                                lblAlert.setStyle("-fx-wrap-text:true;");
                                lblAlert.setPrefWidth(250);
                                popup.add(lblAlert, 0, 4, 3, 1);
                                
                                Button btnYes = new Button(objDictionaryAction.getWord("OK"));
                                btnYes.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOK")));
                                btnYes.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
                                btnYes.setDefaultButton(true);
                                btnYes.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent t) {
                                        if(passPF.getText().length()==0){
                                            lblAlert.setText(objDictionaryAction.getWord("NOSERVICEPASSWORD"));
                                        }
                                        else{
                                            if(Security.SecurePassword(passPF.getText(), objConfiguration.getObjUser().getStrUsername()).equals(objConfiguration.getObjUser().getStrAppPassword())){
                                                try {
                                                    String strAccessNew = dataTG.getSelectedToggle().getUserData().toString();
                                                    System.err.println(strAccessNew);
                                                    strAccessNew = new IDGenerator().setUserAcessValueData("WEAVE_LIBRARY",strAccessNew);
                                                    objWeaveAction = new WeaveAction();
                                                    objWeaveAction.resetWeavePermission(lblWeave.getId(),strAccessNew);
                                                    loadLibraryWeave();
                                                    dialogStage.close();                                            
                                                } catch (SQLException ex) {
                                                    new Logging("SEVERE",UtilityViewOld.class.getName(),"Change Permission Data"+ex.getMessage(),ex);
                                                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                                }
                                                System.gc();
                                            }
                                            else{
                                                lblAlert.setText(objDictionaryAction.getWord("INVALIDSERVICEPASSWORD"));
                                            }
                                        }
                                        System.gc();
                                    }
                                });
                                popup.add(btnYes, 0, 3);
                                Button btnNo = new Button(objDictionaryAction.getWord("CANCEL"));
                                btnNo.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
                                btnNo.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
                                btnNo.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent t) {
                                        dialogStage.close();
                                        System.gc();
                                    }
                                });
                                popup.add(btnNo, 2, 3);
                                root.setCenter(popup);
                                dialogStage.setScene(scene);
                                dialogStage.showAndWait();  
                                e.consume();
                                System.gc();
                            }
                        });
                        action.getChildren().add(btnPermission);
                    }
                    Button btnUpdate;
                    if(!strAccess.equalsIgnoreCase("Public") && intUsage==0){
                        btnUpdate = new Button(objDictionaryAction.getWord("UPDATE"));
                        btnUpdate.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
                        btnUpdate.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPUPDATE")));
                    }else{
                        btnUpdate = new Button(objDictionaryAction.getWord("COPY"));
                        btnUpdate.setGraphic(new ImageView(objConfiguration.getStrColour()+"/copy.png"));
                        btnUpdate.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOPY")));
                    }
                    btnUpdate.setMaxWidth(Double.MAX_VALUE);
                    btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            if(strAccess.equalsIgnoreCase("Public")){
                                objConfiguration.setServicePasswordValid(true);
                            } else{
                                new MessageView(objConfiguration);
                            }
                            if(objConfiguration.getServicePasswordValid()){
                                objConfiguration.setServicePasswordValid(false);
                                objConfiguration.strWindowFlowContext = "Dashboard";
                                objConfiguration.setStrRecentWeave(lblWeave.getId());
                                simulatorStage.close();
                                System.gc();
                                WeaveView objWeaveView = new WeaveView(objConfiguration);
                                System.gc();
                            }
                        }
                    });
                    action.getChildren().add(btnUpdate);
                    Button btnFabric = new Button(objDictionaryAction.getWord("USEINFABRIC"));
                    btnFabric.setGraphic(new ImageView(objConfiguration.getStrColour()+"/fabric_editor.png"));
                    btnFabric.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPAPUSEINFABRIC")));
                    btnFabric.setMaxWidth(Double.MAX_VALUE);
                    btnFabric.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                System.gc();
                                final Stage dialogStage = new Stage();
                                dialogStage.initStyle(StageStyle.UTILITY);
                                dialogStage.initModality(Modality.APPLICATION_MODAL);
                                dialogStage.setResizable(false);
                                dialogStage.setIconified(false);
                                dialogStage.setFullScreen(false);
                                dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("ALERT"));
                                BorderPane root = new BorderPane();
                                Scene scene = new Scene(root, 300, 200, Color.WHITE);
                                scene.getStylesheets().add(UtilityViewOld.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                                final GridPane popup=new GridPane();
                                popup.setId("popup");
                                popup.setHgap(5);
                                popup.setVgap(5);
                                popup.setPadding(new Insets(5, 5, 5, 5));
                                Label lblPassword=new Label(objDictionaryAction.getWord("SERVICEPASSWORD"));
                                lblPassword.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                popup.add(lblPassword, 0, 0, 2, 1);
                                final PasswordField passPF =new PasswordField();
                                passPF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSERVICEPASSWORD")));
                                passPF.setPromptText(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
                                popup.add(passPF, 0, 1, 2, 1);
                                final Label lblAlert=new Label("");
                                lblAlert.setStyle("-fx-wrap-text:true;");
                                lblAlert.setPrefWidth(250);
                                popup.add(lblAlert, 0, 3, 2, 1);
                                Button btnYes= new Button(objDictionaryAction.getWord("OK"));
                                btnYes.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOK")));
                                btnYes.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
                                btnYes.setDefaultButton(true);
                                btnYes.setOnAction(new EventHandler<ActionEvent>(){
                                    @Override
                                    public void handle(ActionEvent event) {
                                        if(Security.SecurePassword(passPF.getText(), objConfiguration.getObjUser().getStrUsername()).equals(objConfiguration.getObjUser().getStrAppPassword())){
                                            System.gc();
                                            objConfiguration.strWindowFlowContext = "WeaveEditor";
                                            objConfiguration.setStrRecentWeave(lblWeave.getId().toString());
                                            simulatorStage.close();
                                            FabricView objFabricView = new FabricView(objConfiguration);
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
                                popup.add(btnYes, 0, 2);
                                Button btnNo = new Button(objDictionaryAction.getWord("CANCEL"));
                                btnNo.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
                                btnNo.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
                                btnNo.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent t) {
                                        dialogStage.close();
                                        System.gc();
                                    }
                                });
                                popup.add(btnNo, 1, 2);
                                root.setCenter(popup);
                                dialogStage.setScene(scene);
                                dialogStage.showAndWait();  
                                System.gc();
                            }
                        });
                    action.getChildren().add(btnFabric);
                    
                    GP_container.add(action, (k+2)%6, i/2);
                }
            }
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public void loadLibraryColour(){
        containerGP.getChildren().clear();
        System.gc();
        objColour = new Colour();
        objColour.setObjConfiguration(objConfiguration);
        objColour.setStrCondition("");
        objColour.setStrOrderBy("Type");
        objColour.setStrSearchBy("");
        objColour.setStrDirection("Ascending");
        
        containerGP.add(new Text(objDictionaryAction.getWord("COLOUR")+" "+objDictionaryAction.getWord("NAME")),0,0);
        containerGP.add(new Text(objDictionaryAction.getWord("COLOUR")+" "+objDictionaryAction.getWord("SEARCHBY")),2,0);
        containerGP.add(new Text(objDictionaryAction.getWord("COLOUR")+" "+objDictionaryAction.getWord("SORTBY")),4,0);
        containerGP.add(new Text(objDictionaryAction.getWord("COLOUR")+" "+objDictionaryAction.getWord("SORTDIRCTION")),6,0);
        
        final TextField libName = new TextField();
        libName.setPromptText(objDictionaryAction.getWord("PROMPTNAME"));
        libName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                objColour.setStrCondition(libName.getText());
                populateLibraryColour(); 
            }
        });
        ComboBox libSearch = new ComboBox();
        libSearch.getItems().addAll(
            "All",
            "Web Color",
            "Pantone Color"
        );
        libSearch.setPromptText(objDictionaryAction.getWord("SEARCHBY"));
        libSearch.setEditable(false); 
        libSearch.setValue("All"); 
        libSearch.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objColour.setStrSearchBy(newValue);
                populateLibraryColour();
            }    
        });
        ComboBox libSort = new ComboBox();
        libSort.getItems().addAll(
            "Name",
            "Date",
            "Type",
            "Code",
            "Hex Code",
            "Red",
            "Green",
            "Blue"
        );            
        libSort.setPromptText(objDictionaryAction.getWord("SORTBY"));
        libSort.setEditable(false); 
        libSort.setValue("Name"); 
        libSort.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objColour.setStrOrderBy(newValue);
                populateLibraryColour();
            }    
        });
        ComboBox libDirction = new ComboBox();
        libDirction.getItems().addAll(
            "Ascending",
            "Descending"
        );
        libDirction.setPromptText(objDictionaryAction.getWord("SORTDIRCTION"));
        libDirction.setEditable(false); 
        libDirction.setValue("Ascending"); 
        libDirction.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objColour.setStrDirection(newValue);
                populateLibraryColour();
            }    
        });
        
        containerGP.add(libName,1,0);
        containerGP.add(libSearch,3,0);
        containerGP.add(libSort,5,0);
        containerGP.add(libDirction,7,0);
        
        Separator sepHor = new Separator();
        sepHor.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor, 0, 1);
        GridPane.setColumnSpan(sepHor, 8);
        containerGP.getChildren().add(sepHor);
    
        GP_container = new GridPane();         
        GP_container.setAlignment(Pos.TOP_LEFT);
        GP_container.setHgap(20);
        GP_container.setVgap(20);
        GP_container.setPadding(new Insets(1, 1, 1, 1));
        containerGP.add(GP_container,0,2,8,1);
        
        populateLibraryColour();
        
            
        //container.setContent(containerGP);       
    }
    private void populateLibraryColour(){
        try{
            GP_container.getChildren().clear();
            List lstColour=null;
            List lstColourDeatails = new ArrayList();
            objFabricAction = new FabricAction();
            lstColourDeatails = objFabricAction.lstImportColor(objColour);
            if(lstColourDeatails.size()==0){
                GP_container.getChildren().add(new Text(objDictionaryAction.getWord("COLOUR")+" - "+objDictionaryAction.getWord("NOVALUE")));
            }else{            
                for (int i=0, k=0; i<lstColourDeatails.size();i++, k+=2){
                    lstColour = (ArrayList)lstColourDeatails.get(i);

                    Label ivColour = new Label();
                    ivColour.setStyle("-fx-background-color:rgb("+lstColour.get(3).toString()+","+lstColour.get(4).toString()+","+lstColour.get(5).toString()+");");
                    ivColour.setPrefSize(222,222);
                    GP_container.add(ivColour, k%6, i/3);

                    String strTooltip = 
                            objDictionaryAction.getWord("NAME")+": "+lstColour.get(1).toString()+"\n"+
                            objDictionaryAction.getWord("TYPE")+": "+lstColour.get(2).toString()+"\n"+
                            objDictionaryAction.getWord("RGB")+": "+lstColour.get(3).toString()+", "+lstColour.get(4).toString()+", "+lstColour.get(5).toString()+"\n"+
                            objDictionaryAction.getWord("HEX")+": "+lstColour.get(6).toString()+"\n"+
                            objDictionaryAction.getWord("CODE")+": "+lstColour.get(7).toString()+"\n"+
                            objDictionaryAction.getWord("BY")+": "+lstColour.get(9).toString()+"\n"+
                            objDictionaryAction.getWord("DATE")+": "+lstColour.get(10).toString();
                    Label lblColour = new Label(strTooltip);
                    lblColour.setTooltip(new Tooltip(strTooltip));
                    GP_container.add(lblColour, (k+1)%6, i/3);
                }
            }
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
 
    public void loadLibraryLanguage(){
        containerGP.getChildren().clear();
        System.gc();
        
        containerGP.add(new Text(objDictionaryAction.getWord("LANGUAGE")+" "+objDictionaryAction.getWord("NAME")),0,0);
        final ComboBox languageCB = new ComboBox();
		try{
            InputStream resourceAsStream = new FileInputStream(System.getProperty("user.dir")+"/mla/language/langs.properties"); //UtilityViewOld.class.getClassLoader().getResourceAsStream("language/langs.properties");;
            Properties properties=new Properties();
            if (resourceAsStream != null) {
                properties.load(resourceAsStream);
                languageCB.getItems().clear();
                for(Object k:properties.keySet()){
                    String langName=(String)k;
                    languageCB.getItems().add(langName);
                }
            }
        }catch(Exception ex){
            languageCB.getItems().clear();
            languageCB.getItems().addAll("HINDI","ENGLISH");
        }
        languageCB.setPromptText(objDictionaryAction.getWord("SEARCHBY"));
        languageCB.setEditable(false); 
		currentLanguage="ENGLISH";
        languageCB.setValue("ENGLISH"); 
        languageCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
				currentLanguage=newValue;
                populateLibraryLanguage(newValue);
            }    
        });
        containerGP.add(languageCB,1,0);
        
        Separator sepHor = new Separator();
        sepHor.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor, 0, 1);
        GridPane.setColumnSpan(sepHor, 2);
        containerGP.getChildren().add(sepHor);
    
        GP_container = new GridPane();         
        GP_container.setAlignment(Pos.TOP_LEFT);
        GP_container.setHgap(2);
        GP_container.setVgap(2);
        GP_container.setPadding(new Insets(1, 1, 1, 1));
        containerGP.add(GP_container,0,2,2,1);
        
        populateLibraryLanguage("ENGLISH");
    }
 
    private void populateLibraryLanguage(String strLanguage){
        try{
			if(strLanguage=="ENGLISH" || strLanguage=="HINDI"){
                saveTranslatorVB.setDisable(true);
            }
            else
                saveTranslatorVB.setDisable(false);
            GP_container.getChildren().clear();
            List lstLanguage=null;
            List lstLanguageDeatails = new ArrayList();
            lstLanguageDeatails = new DictionaryAction().lstImportLanguage(strLanguage);
            if(lstLanguageDeatails.size()==0){
                GP_container.getChildren().add(new Text(objDictionaryAction.getWord("LANGUAGE")+" - "+objDictionaryAction.getWord("NOVALUE")));
            }else{            
                for (int i=0, k=0; i<lstLanguageDeatails.size();i++, k+=2){
                    lstLanguage = (ArrayList)lstLanguageDeatails.get(i);
                    
                    Label lbl_key=new Label(lstLanguage.get(0).toString());
                    lbl_key.setTextFill(Color.BLUE);

                    Label lbl_unikey=new Label(objDictionaryAction.getWord(lstLanguage.get(0).toString()));
                    lbl_unikey.setFont(Font.font("Tahoma", FontWeight.NORMAL, 11));
                    lbl_unikey.setTextFill(Color.MAGENTA);

                    TextField txt_key=new TextField(lstLanguage.get(1).toString());
                    
                    /*char[] the_unicode_char = new char[1];
                    try {
                        the_unicode_char[0] = (char)Integer.parseInt((String)objDictionaryAction.dictionary.get(key),16);
                        System.out.println(the_unicode_char);
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                    lbl_unikey.setText(new String(the_unicode_char));
                    */
                    

                    GP_container.add(lbl_key, 1, i+1);
                    GP_container.add(txt_key, 2, i+1);
                    GP_container.add(lbl_unikey, 3, i+1);
                }
            }
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
	
	private void populateLibraryLanguageNew(){
        try{
            saveTranslatorVB.setDisable(true);
            newTranslatorVB.setDisable(true);
            GP_container.getChildren().clear();
            List lstLanguage=null;
            List lstLanguageDeatails = new ArrayList();
            lstLanguageDeatails = new DictionaryAction().lstImportLanguage("ENGLISH");
            if(lstLanguageDeatails.size()==0){
                GP_container.getChildren().add(new Text(objDictionaryAction.getWord("LANGUAGE")+" - "+objDictionaryAction.getWord("NOVALUE")));
            }else{            
                for (int i=0, k=0; i<lstLanguageDeatails.size();i++, k+=2){
                    lstLanguage = (ArrayList)lstLanguageDeatails.get(i);
                    
                    Label lbl_key=new Label(lstLanguage.get(0).toString());
                    lbl_key.setTextFill(Color.BLUE);

                    Label lbl_unikey=new Label(objDictionaryAction.getWord(lstLanguage.get(0).toString()));
                    lbl_unikey.setFont(Font.font("Tahoma", FontWeight.NORMAL, 11));
                    lbl_unikey.setTextFill(Color.MAGENTA);

                    TextField txt_key=new TextField("");
                    GP_container.add(lbl_key, 1, i+1);
                    GP_container.add(txt_key, 2, i+1);
                    GP_container.add(lbl_unikey, 3, i+1);
                }
            }
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    private void saveLibraryLanguage(){
        Properties properties=new Properties();
        String key="", value="";
        for(int a=0; a<GP_container.getChildren().size(); a++){
            if(GP_container.getChildren().get(a) instanceof TextField){
                value=((TextField)GP_container.getChildren().get(a)).getText();
                if(value.trim().length()!=0){
                    key=((Label)GP_container.getChildren().get(a-1)).getText();
                }
                else{
                    key=((Label)GP_container.getChildren().get(a-1)).getText();
                    value=objDictionaryAction.getWord(key);
                }
                properties.setProperty(key, value);
            }
        }
        try{
            File f=new File(System.getProperty("user.dir")+"/mla/language/"+currentLanguage+".properties");
            properties.store(new FileOutputStream(f), null);
        }catch(Exception ex){
            new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    private void saveAsLibraryLanguage(){
        // popup enter new language name
        final Stage popup=new Stage();
        popup.setTitle("Save Language");
        GridPane popupGP=new GridPane();
        popupGP.setAlignment(Pos.CENTER);
        popupGP.setHgap(5);
        popupGP.setVgap(10);
        Label lblLang=new Label("Enter Language Name");
        Button btnOK=new Button("Save");
        Button btnCancel=new Button("Cancel");
        final TextField txtLang=new TextField("");
        popupGP.add(lblLang, 0, 0, 2, 1);
        popupGP.add(txtLang, 0, 1, 2, 1);
        popupGP.add(btnOK, 0, 2, 1, 1);
        popupGP.add(btnCancel, 1, 2, 1, 1);
        final Properties properties=new Properties();
        btnOK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(txtLang.getText().length()==0)
                    return;
                String key="", value="";
                for(int a=0; a<GP_container.getChildren().size(); a++){
                    if(GP_container.getChildren().get(a) instanceof TextField){
                        value=((TextField)GP_container.getChildren().get(a)).getText();
                        if(value.trim().length()!=0){
                            key=((Label)GP_container.getChildren().get(a-1)).getText();
                        }
                        else{
                            key=((Label)GP_container.getChildren().get(a-1)).getText();
                            value=objDictionaryAction.getWord(key);
                        }
                        properties.setProperty(key, value);
                    }
                }
                try{
                    File f=new File(System.getProperty("user.dir")+"/mla/language/"+txtLang.getText().toUpperCase()+".properties");
                    f.createNewFile();
                    //properties.store(new FileOutputStream(UtilityViewOld.class.getClassLoader().getResource("language/"+txtLang.getText()+".properties").getPath()), null);
                    properties.store(new FileOutputStream(f), null);
                    Files.write(Paths.get(System.getProperty("user.dir")+"/mla/language/langs.properties"), ("\n"+txtLang.getText().toUpperCase()).getBytes(), StandardOpenOption.APPEND);
                    popup.close();
                }catch(Exception ex){
                    new Logging("SEVERE",UtilityViewOld.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    popup.close();
                }
            }
        });
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popup.close();
            }
        });
        Scene scene=new Scene(popupGP, 300, 200);
        popup.setScene(scene);
        popup.showAndWait();
    }
    @Override
    public void start(Stage stage) throws Exception {
        new UtilityViewOld(stage);
        new Logging("WARNING",UtilityViewOld.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    public static void main(String[] args) {   
      launch(args);    
    }
}
