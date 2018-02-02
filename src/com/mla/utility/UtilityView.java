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

import com.mla.dictionary.DictionaryAction;
import com.mla.fabric.FabricSettingView;
import com.mla.main.AboutView;
import com.mla.main.Configuration;
import com.mla.main.ContactView;
import com.mla.main.HelpView;
import com.mla.main.Logging;
import com.mla.main.TechnicalView;
import com.mla.main.WindowView;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
/**
 *
 * @Designing GUI window for dashboard
 * @author Amit Kumar Singh
 * 
 */
public class UtilityView extends Application {
    public static Stage utilityStage;
    BorderPane root;
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;

    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    
    private Menu homeMenu;
    private Menu helpMenu;

    
 /**
 * UtilityView(Stage)
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
     public UtilityView(final Stage primaryStage) {}
    
 /**
 * UtilityView(Configuration)
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
    public UtilityView(Configuration objConfigurationCall) {
        
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);

        utilityStage = new Stage(); 
        root = new BorderPane();
        Scene scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(UtilityView.class.getResource(objConfiguration.getStrTemplate()+"/childboard.css").toExternalForm());
    
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
        menuBar.prefWidthProperty().bind(utilityStage.widthProperty());
        
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
                scene.getStylesheets().add(LibraryView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        utilityStage.close();
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
                utilityStage.close();    
            }
        });    
        helpMenu.getItems().addAll(helpMenuItem, technicalMenuItem, aboutMenuItem, contactMenuItem, new SeparatorMenuItem(), exitMenuItem);
        menuBar.getMenus().addAll(homeMenu, helpMenu);
        root.setTop(menuBar);

        //Main Pane
        GridPane containerGP = new GridPane();
        containerGP.setId("mainPane");
        //containerGP.setPrefSize(objConfiguration.WIDTH*0.4, objConfiguration.HEIGHT*0.5);
        containerGP.setAlignment(Pos.CENTER);
        
        // fabric editor
        VBox convertorVB = new VBox(); 
        Label convertorLbl= new Label(objDictionaryAction.getWord("CONVERSION"));
        convertorLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCONVERSION")));
        convertorVB.getChildren().addAll(new ImageView(objConfiguration.getStrTemplate()+"/img/s_converter.png"), convertorLbl);
        convertorVB.setPrefWidth(objConfiguration.WIDTH*0.15);
        convertorVB.setPrefHeight(objConfiguration.HEIGHT*0.25);
        convertorVB.getStyleClass().addAll("VBox");
        convertorVB.setCursor(Cursor.CROSSHAIR);
        containerGP.add(convertorVB, 0, 0);
        // artwork editor
        VBox synchronizationVB = new VBox(); 
        Label synchronizationLbl= new Label(objDictionaryAction.getWord("EXPORT")+" "+objDictionaryAction.getWord("IMPORT"));
        synchronizationLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPARTWORKEDITOR")));
        synchronizationVB.getChildren().addAll(new ImageView(objConfiguration.getStrTemplate()+"/img/s_export_import.png"), synchronizationLbl);
        synchronizationVB.setPrefWidth(objConfiguration.WIDTH*0.15);
        synchronizationVB.setPrefHeight(objConfiguration.HEIGHT*0.25);
        synchronizationVB.getStyleClass().addAll("VBox");
        synchronizationVB.setCursor(Cursor.CROSSHAIR);
        containerGP.add(synchronizationVB, 1, 0);
        // weave editor
        VBox translatorVB = new VBox(); 
        Label translatorLbl= new Label(objDictionaryAction.getWord("TRASLATOR"));
        translatorLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTRASLATOR")));
        translatorVB.getChildren().addAll(new ImageView(objConfiguration.getStrTemplate()+"/img/s_translator.png"), translatorLbl);
        translatorVB.setPrefWidth(objConfiguration.WIDTH*0.15);
        translatorVB.setPrefHeight(objConfiguration.HEIGHT*0.25);
        translatorVB.getStyleClass().addAll("VBox");
        translatorVB.setCursor(Cursor.CROSSHAIR);
        containerGP.add(translatorVB, 2, 0);
        // Save Texture file item
        VBox simulatorVB = new VBox(); 
        Label simulatorLbl= new Label(objDictionaryAction.getWord("SIMULATOR"));
        simulatorLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSIMULATOR")));
        simulatorVB.getChildren().addAll(new ImageView(objConfiguration.getStrTemplate()+"/img/s_simulator.png"), simulatorLbl);
        simulatorVB.setPrefWidth(objConfiguration.WIDTH*0.15);
        simulatorVB.setPrefHeight(objConfiguration.HEIGHT*0.25);
        simulatorVB.getStyleClass().addAll("VBox");
        simulatorVB.setCursor(Cursor.CROSSHAIR);
        containerGP.add(simulatorVB, 3, 0);
        // Save As file item
        VBox deviceVB = new VBox(); 
        Label deviceLbl= new Label(objDictionaryAction.getWord("APPLICATIONINTEGRATION"));
        deviceLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPAPPLICATIONINTEGRATION")));
        deviceVB.getChildren().addAll(new ImageView(objConfiguration.getStrTemplate()+"/img/s_intregration.png"), deviceLbl);
        deviceVB.setPrefWidth(objConfiguration.WIDTH*0.15);
        deviceVB.setPrefHeight(objConfiguration.HEIGHT*0.25);
        deviceVB.getStyleClass().addAll("VBox");
        deviceVB.setCursor(Cursor.CROSSHAIR);
        containerGP.add(deviceVB, 0, 1);
        // Cloth editor item
        VBox libraryVB = new VBox(); 
        Label libraryLbl= new Label(objDictionaryAction.getWord("LIBRARY"));
        libraryLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPLIBRARY")));
        libraryVB.getChildren().addAll(new ImageView(objConfiguration.getStrTemplate()+"/img/s_library.png"), libraryLbl);
        libraryVB.setPrefWidth(objConfiguration.WIDTH*0.15);
        libraryVB.setPrefHeight(objConfiguration.HEIGHT*0.25);
        libraryVB.getStyleClass().addAll("VBox");
        libraryVB.setCursor(Cursor.CROSSHAIR);
        containerGP.add(libraryVB, 1, 1);
        // Save Texture file item
        VBox paletteVB = new VBox(); 
        Label paletteLbl= new Label(objDictionaryAction.getWord("PALETTE"));
        paletteLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPALETTE")));
        paletteVB.getChildren().addAll(new ImageView(objConfiguration.getStrTemplate()+"/img/s_yarn_color_palette.png"), paletteLbl);
        paletteVB.setPrefWidth(objConfiguration.WIDTH*0.15);
        paletteVB.setPrefHeight(objConfiguration.HEIGHT*0.25);
        paletteVB.getStyleClass().addAll("VBox");
        paletteVB.setCursor(Cursor.CROSSHAIR);
        containerGP.add(paletteVB, 2, 1);
        // Cloth editor item
        VBox galleryVB = new VBox(); 
        Label galleryLbl= new Label(objDictionaryAction.getWord("HELP"));
        galleryLbl.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPHELP")));
        galleryVB.getChildren().addAll(new ImageView(objConfiguration.getStrTemplate()+"/img/s_help_support.png"), galleryLbl);
        galleryVB.setPrefWidth(objConfiguration.WIDTH*0.15);
        galleryVB.setPrefHeight(objConfiguration.HEIGHT*0.25);
        galleryVB.getStyleClass().addAll("VBox");
        galleryVB.setCursor(Cursor.CROSSHAIR);
        containerGP.add(galleryVB, 3, 1);
 
        //Add the action to Buttons.
        convertorVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.gc();
                utilityStage.close();
                ConvertorView objConvertorView = new ConvertorView(objConfiguration);
            }
        });
        synchronizationVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.gc();
                utilityStage.close();
                ExportImportView objExportImportView = new ExportImportView(objConfiguration);
            }
        });
        translatorVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.gc();
                utilityStage.close();
                TranslatorView objTranslatorView= new TranslatorView(objConfiguration);
            }
        });
        simulatorVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.gc();
                utilityStage.close();
                SimulatorView objSimulatorView = new SimulatorView(objConfiguration);
            }
        });
        deviceVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.gc();
                utilityStage.close();
                DeviceView objDeviceView = new DeviceView(objConfiguration);
            }
        });
        libraryVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.gc();
                utilityStage.close();
                LibraryView objLibraryView = new LibraryView(objConfiguration);
            }
        });
        paletteVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    System.gc();
                    utilityStage.close();
                    //ColorPaletteEditView objColorPaletteEditView = new ColorPaletteEditView(objConfiguration);
                    //ColorPaletteView objColorPaletteView = new ColorPaletteView(objConfiguration);
                    PaletteView objPaletteView = new PaletteView(objConfiguration);
                } catch (Exception ex) {
                    Logger.getLogger(UtilityView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        galleryVB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.gc();
                utilityStage.close();
                GalleryView objGalleryView = new GalleryView(objConfiguration);
            }
        });
        
        //containerGP.setAlignment(Pos.CENTER);
        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.getChildren().add(containerGP);
        container.setId("container");
        
        root.setLeft(container);
        //root.setCenter(containerGP);
        System.out.println("\u092E\u0940\u093F\u0921\u092F\u093E");
        utilityStage.getIcons().add(new Image("/media/icon.png"));
        utilityStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWDASHBOARD")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        //configurationStage.setIconified(true);
        utilityStage.setResizable(false);
        utilityStage.setScene(scene);
        utilityStage.setX(-5);
        utilityStage.setY(0);
        utilityStage.show();  
        utilityStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
                scene.getStylesheets().add(UtilityView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        utilityStage.close();
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
    
    @Override
    public void start(Stage stage) throws Exception {
        new UtilityView(stage);
        new Logging("WARNING",UtilityView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static void main(String[] args) {   
        launch(args);    
    }  
}