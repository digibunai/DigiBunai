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

import com.mla.artwork.Artwork;
import com.mla.artwork.ArtworkAction;
import com.mla.artwork.ArtworkView;
import com.mla.cloth.Cloth;
import com.mla.cloth.ClothAction;
import com.mla.colour.Colour;
import com.mla.dictionary.DictionaryAction;
import com.mla.fabric.Fabric;
import com.mla.fabric.FabricAction;
import com.mla.fabric.FabricView;
import com.mla.yarn.Yarn;
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
import com.mla.secure.Security;
import com.mla.user.UserAction;
//import static com.mla.utility.SimulatorView.simulatorStage;
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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javax.media.jai.PlanarImage;
/**
 *
 * @Designing GUI window for fabric preferences
 * @author Amit Kumar Singh
 * 
 */
public class LibraryView extends Application {
    public static Stage libraryStage;
    BorderPane root;
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;
    
    private FabricAction objFabricAction;
    private ArtworkAction objArtworkAction;
    private WeaveAction objWeaveAction;
    private YarnAction objYarnAction;
    private Cloth objCloth;
    private Fabric objFabric;
    private Artwork objArtwork;
    private Weave objWeave;
    private Yarn objYarn;
    private Colour objColour;
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    
    private Menu homeMenu;
    private Menu helpMenu;

    GridPane GP_container;
    
    public LibraryView(final Stage primaryStage) {}
    
    public LibraryView(Configuration objConfigurationCall) {
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);

        libraryStage = new Stage(); 
        root = new BorderPane();
        Scene scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(LibraryView.class.getResource(objConfiguration.getStrTemplate()+"/setting.css").toExternalForm());
        
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
        menuBar.prefWidthProperty().bind(libraryStage.widthProperty());
        
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
                        libraryStage.close();
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
                libraryStage.close();    
            }
        });    
        helpMenu.getItems().addAll(helpMenuItem, technicalMenuItem, aboutMenuItem, contactMenuItem, new SeparatorMenuItem(), exitMenuItem);        
        menuBar.getMenus().addAll(homeMenu, helpMenu);
        root.setTop(menuBar);
        
        TabPane tabPane = new TabPane();

        final Tab clothTab = new Tab();
        final Tab fabricTab = new Tab();
        final Tab artworkTab = new Tab();
        final Tab weaveTab = new Tab();
        final Tab yarnTab = new Tab();
        final Tab colourTab = new Tab();
        
        clothTab.setClosable(false);
        fabricTab.setClosable(false);
        artworkTab.setClosable(false);
        weaveTab.setClosable(false);
        yarnTab.setClosable(false);
        colourTab.setClosable(false);
        
        clothTab.setText(objDictionaryAction.getWord("CLOTHLIBRARY"));
        fabricTab.setText(objDictionaryAction.getWord("FABRICLIBRARY"));
        artworkTab.setText(objDictionaryAction.getWord("ARTWORKLIBRARY"));
        weaveTab.setText(objDictionaryAction.getWord("WEAVELIBRARY"));
        yarnTab.setText(objDictionaryAction.getWord("YARNLIBRARY"));
        colourTab.setText(objDictionaryAction.getWord("COLOURLIBRARY"));
        
        clothTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLOTHLIBRARY")));
        fabricTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFABRICLIBRARY")));
        artworkTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPARTWORKLIBRARY")));
        weaveTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWEAVELIBRARY")));
        yarnTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNLIBRARY")));
        colourTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOLOURLIBRARY")));
        
        //clothTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/cloth_library.png"));
        //fabricTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/fabric_library.png"));
        //artworkTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/artwork_library.png"));
        //weaveTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/weave_library.png"));
        //yarnTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/yarn_library.png"));
        //colourTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/color_palette.png"));
        
        GridPane clothGP = new GridPane();
        GridPane fabricGP = new GridPane();
        GridPane artworkGP = new GridPane();
        GridPane weaveGP = new GridPane();
        GridPane yarnGP = new GridPane();
        GridPane colourGP = new GridPane();
        
        clothGP.setId("container");
        fabricGP.setId("container");
        artworkGP.setId("container");
        weaveGP.setId("container");
        yarnGP.setId("container");
        colourGP.setId("container");
        
        //clothGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //fabricGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //artworkGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //weaveGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //yarnGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //colourGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        
        //clothGP.setAlignment(Pos.TOP_CENTER);
        //fabricGP.setAlignment(Pos.TOP_CENTER);
        //artworkGP.setAlignment(Pos.TOP_CENTER);
        //weaveGP.setAlignment(Pos.TOP_CENTER);
        //yarnGP.setAlignment(Pos.TOP_CENTER);
        //colourGP.setAlignment(Pos.TOP_CENTER);
        
        clothTab.setContent(clothGP);
        fabricTab.setContent(fabricGP);
        artworkTab.setContent(artworkGP);
        weaveTab.setContent(weaveGP);
        yarnTab.setContent(yarnGP);
        colourTab.setContent(colourGP);
        
        tabPane.getTabs().add(clothTab);
        tabPane.getTabs().add(fabricTab);
        tabPane.getTabs().add(artworkTab);
        tabPane.getTabs().add(weaveTab);
        tabPane.getTabs().add(yarnTab);
        tabPane.getTabs().add(colourTab);
        
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
                if(newTab == colourTab) {
                    populateLibraryColour();
                }else if(newTab == yarnTab) {
                    populateLibraryYarn();
                }else if(newTab == weaveTab) {
                    populateLibraryWeave();
                }else if(newTab == artworkTab) {
                    populateLibraryArtwork();
                }else if(newTab == fabricTab) {
                    populateLibraryFabric();
                }else if(newTab == clothTab) {
                    populateLibraryCloth();
                }
            }
        });
        
        
        //===== Cloth =====//
        Label cloth = new Label(objDictionaryAction.getWord("CLOTHLIBRARY"));
        cloth.setGraphic(new ImageView(objConfiguration.getStrColour()+"/fabric_library.png"));
        cloth.setId("caption");
        GridPane.setConstraints(cloth, 0, 0);
        GridPane.setColumnSpan(cloth, 8);
        clothGP.getChildren().add(cloth);
       
        objCloth = new Cloth();
        objCloth.setObjConfiguration(objConfiguration);
        objCloth.setStrCondition("");
        objCloth.setStrSearchBy("");
        objCloth.setStrOrderBy("Name");
        objCloth.setStrDirection("Ascending");
        //objCloth.setStrLimit("");
        
        clothGP.add(new Text(objDictionaryAction.getWord("CLOTH")+" "+objDictionaryAction.getWord("NAME")),0,1);
        clothGP.add(new Text(objDictionaryAction.getWord("CLOTH")+" "+objDictionaryAction.getWord("SEARCHBY")),2,1);
        clothGP.add(new Text(objDictionaryAction.getWord("CLOTH")+" "+objDictionaryAction.getWord("SORTBY")),4,1);
        clothGP.add(new Text(objDictionaryAction.getWord("CLOTH")+" "+objDictionaryAction.getWord("SORTDIRCTION")),6,1);
        
        final TextField clothName = new TextField();
        clothName.setPromptText(objDictionaryAction.getWord("PROMPTNAME"));
        clothName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                objCloth.setStrCondition(clothName.getText());
                populateLibraryCloth(); 
            }
        });
        ComboBox clothSearch = new ComboBox();
        clothSearch.getItems().addAll(
            "All Cloth Type",
            "Saree",
            "Duppata",
            "Shwal",
            "Graiser Brocade"
        );
        clothSearch.setPromptText(objDictionaryAction.getWord("SEARCHBY"));
        clothSearch.setEditable(false); 
        clothSearch.setValue("All Cloth Type"); 
        clothSearch.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objCloth.setStrSearchBy(newValue);
                populateLibraryCloth();
            }    
        });
        ComboBox clothSort = new ComboBox();
        clothSort.getItems().addAll(
            "Name",
            "Date"
        );
        clothSort.setPromptText(objDictionaryAction.getWord("SORTBY"));
        clothSort.setEditable(false); 
        clothSort.setValue("Name"); 
        clothSort.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objCloth.setStrOrderBy(newValue);
                populateLibraryCloth();
            }    
        });
        ComboBox clothDirction = new ComboBox();
        clothDirction.getItems().addAll(
            "Ascending",
            "Descending"
        );
        clothDirction.setPromptText(objDictionaryAction.getWord("SORTDIRCTION"));
        clothDirction.setEditable(false); 
        clothDirction.setValue("Ascending"); 
        clothDirction.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objCloth.setStrDirection(newValue);
                populateLibraryCloth();
            }    
        });
        
        clothGP.add(clothName,1,1);
        clothGP.add(clothSearch,3,1);
        clothGP.add(clothSort,5,1);
        clothGP.add(clothDirction,7,1);
        
        Separator clothSH = new Separator();
        clothSH.setValignment(VPos.CENTER);
        GridPane.setConstraints(clothSH, 0, 2);
        GridPane.setColumnSpan(clothSH, 8);
        clothGP.getChildren().add(clothSH);
        
        //===== Fabric =====//
        Label fabric = new Label(objDictionaryAction.getWord("FABRICLIBRARY"));
        fabric.setGraphic(new ImageView(objConfiguration.getStrColour()+"/fabric_library.png"));
        fabric.setId("caption");
        GridPane.setConstraints(fabric, 0, 0);
        GridPane.setColumnSpan(fabric, 8);
        fabricGP.getChildren().add(fabric);
       
        objFabric = new Fabric();
        objFabric.setObjConfiguration(objConfiguration);
        objFabric.setStrCondition("");
        objFabric.setStrSearchBy("");
        objFabric.setStrOrderBy("Name");
        objFabric.setStrDirection("Ascending");
        //objFabric.setStrLimit("");
        
        fabricGP.add(new Text(objDictionaryAction.getWord("FABRIC")+" "+objDictionaryAction.getWord("NAME")),0,1);
        fabricGP.add(new Text(objDictionaryAction.getWord("FABRIC")+" "+objDictionaryAction.getWord("SEARCHBY")),2,1);
        fabricGP.add(new Text(objDictionaryAction.getWord("FABRIC")+" "+objDictionaryAction.getWord("SORTBY")),4,1);
        fabricGP.add(new Text(objDictionaryAction.getWord("FABRIC")+" "+objDictionaryAction.getWord("SORTDIRCTION")),6,1);
        
        final TextField fabricName = new TextField();
        fabricName.setPromptText(objDictionaryAction.getWord("PROMPTNAME"));
        fabricName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                objFabric.setStrCondition(fabricName.getText());
                populateLibraryFabric(); 
            }
        });
        ComboBox fabricSearch = new ComboBox();
        fabricSearch.getItems().addAll(
            "All Cloth Type",
            "Body",
            "Palu",
            "Border",
            "Cross Border",
            "Blouse",
            "Skart",
            "Konia"
        );
        fabricSearch.setPromptText(objDictionaryAction.getWord("SEARCHBY"));
        fabricSearch.setEditable(false); 
        fabricSearch.setValue("All Cloth Type"); 
        fabricSearch.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objFabric.setStrSearchBy(newValue);
                populateLibraryFabric();
            }    
        });
        ComboBox fabricSort = new ComboBox();
        fabricSort.getItems().addAll(
            "Name",
            "Date"
        );
        fabricSort.setPromptText(objDictionaryAction.getWord("SORTBY"));
        fabricSort.setEditable(false); 
        fabricSort.setValue("Name"); 
        fabricSort.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objFabric.setStrOrderBy(newValue);
                populateLibraryFabric();
            }    
        });
        ComboBox fabricDirction = new ComboBox();
        fabricDirction.getItems().addAll(
            "Ascending",
            "Descending"
        );
        fabricDirction.setPromptText(objDictionaryAction.getWord("SORTDIRCTION"));
        fabricDirction.setEditable(false); 
        fabricDirction.setValue("Ascending"); 
        fabricDirction.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objFabric.setStrDirection(newValue);
                populateLibraryFabric();
            }    
        });
        
        fabricGP.add(fabricName,1,1);
        fabricGP.add(fabricSearch,3,1);
        fabricGP.add(fabricSort,5,1);
        fabricGP.add(fabricDirction,7,1);
        
        Separator fabricSH = new Separator();
        fabricSH.setValignment(VPos.CENTER);
        GridPane.setConstraints(fabricSH, 0, 2);
        GridPane.setColumnSpan(fabricSH, 8);
        fabricGP.getChildren().add(fabricSH);
    
        //===== Artwork =====//
        Label artwork = new Label(objDictionaryAction.getWord("ARTWORKLIBRARY"));
        artwork.setGraphic(new ImageView(objConfiguration.getStrColour()+"/artwork_library.png"));
        artwork.setId("caption");
        GridPane.setConstraints(artwork, 0, 0);
        GridPane.setColumnSpan(artwork, 8);
        artworkGP.getChildren().add(artwork);
       
        objArtwork = new Artwork();
        objArtwork.setObjConfiguration(objConfiguration);
        objArtwork.setStrCondition("");
        objArtwork.setStrSearchBy("");
        objArtwork.setStrOrderBy("Name");
        objArtwork.setStrDirection("Ascending");
        //objArtwork.setStrLimit("");
        
        artworkGP.add(new Text(objDictionaryAction.getWord("ARTWORK")+" "+objDictionaryAction.getWord("NAME")),0,1);
        artworkGP.add(new Text(objDictionaryAction.getWord("ARTWORK")+" "+objDictionaryAction.getWord("SEARCHBY")),2,1);
        artworkGP.add(new Text(objDictionaryAction.getWord("ARTWORK")+" "+objDictionaryAction.getWord("SORTBY")),4,1);
        artworkGP.add(new Text(objDictionaryAction.getWord("ARTWORK")+" "+objDictionaryAction.getWord("SORTDIRCTION")),6,1);
        
        final TextField artworkName = new TextField();
        artworkName.setPromptText(objDictionaryAction.getWord("PROMPTNAME"));
        artworkName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                objArtwork.setStrCondition(artworkName.getText());
                populateLibraryArtwork(); 
            }
        });
        ComboBox artworkSearch = new ComboBox();
        artworkSearch.getItems().addAll(
            "All Type"
        );
        artworkSearch.setPromptText(objDictionaryAction.getWord("SEARCHBY"));
        artworkSearch.setEditable(false); 
        artworkSearch.setValue("All Type"); 
        artworkSearch.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objArtwork.setStrSearchBy(newValue);
                populateLibraryArtwork();
            }    
        });
        ComboBox artworkSort = new ComboBox();
        artworkSort.getItems().addAll(
            "Name",
            "Date"
        );
        artworkSort.setPromptText(objDictionaryAction.getWord("SORTBY"));
        artworkSort.setEditable(false); 
        artworkSort.setValue("Name"); 
        artworkSort.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objArtwork.setStrOrderBy(newValue);
                populateLibraryArtwork();
            }    
        });
        ComboBox artworkDirction = new ComboBox();
        artworkDirction.getItems().addAll(
            "Ascending",
            "Descending"
        );
        artworkDirction.setPromptText(objDictionaryAction.getWord("SORTDIRCTION"));
        artworkDirction.setEditable(false); 
        artworkDirction.setValue("Ascending"); 
        artworkDirction.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objArtwork.setStrDirection(newValue);
                populateLibraryArtwork();
            }    
        });
        
        artworkGP.add(artworkName,1,1);
        artworkGP.add(artworkSearch,3,1);
        artworkGP.add(artworkSort,5,1);
        artworkGP.add(artworkDirction,7,1);
        
        Separator artworkSH = new Separator();
        artworkSH.setValignment(VPos.CENTER);
        GridPane.setConstraints(artworkSH, 0, 2);
        GridPane.setColumnSpan(artworkSH, 8);
        artworkGP.getChildren().add(artworkSH);
    
        //===== Weave =====//
        Label weave = new Label(objDictionaryAction.getWord("WEAVELIBRARY"));
        weave.setGraphic(new ImageView(objConfiguration.getStrColour()+"/weave_library.png"));
        weave.setId("caption");
        GridPane.setConstraints(weave, 0, 0);
        GridPane.setColumnSpan(weave, 8);
        weaveGP.getChildren().add(weave);
       
        objWeave = new Weave();
        objWeave.setObjConfiguration(objConfiguration);
        objWeave.setStrCondition("");
        objWeave.setStrSearchBy("All");
        objWeave.setStrOrderBy("Name");
        objWeave.setStrDirection("Ascending");        
        //objWeave.setStrLimit("");
        
        weaveGP.add(new Text(objDictionaryAction.getWord("WEAVE")+" "+objDictionaryAction.getWord("NAME")),0,1);
        weaveGP.add(new Text(objDictionaryAction.getWord("WEAVE")+" "+objDictionaryAction.getWord("SEARCHBY")),2,1);
        weaveGP.add(new Text(objDictionaryAction.getWord("WEAVE")+" "+objDictionaryAction.getWord("SORTBY")),4,1);
        weaveGP.add(new Text(objDictionaryAction.getWord("WEAVE")+" "+objDictionaryAction.getWord("SORTDIRCTION")),6,1);
        
        final TextField weaveName = new TextField();
        weaveName.setPromptText(objDictionaryAction.getWord("PROMPTNAME"));
        weaveName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                objWeave.setStrCondition(weaveName.getText());
                populateLibraryWeave(); 
            }
        });
        ComboBox weaveSearch = new ComboBox();
        weaveSearch.getItems().addAll(
            "All",
            "Plain",
            "Twill",
            "Satin",
            "Basket",
            "Sateen"
        );
        weaveSearch.setPromptText(objDictionaryAction.getWord("SEARCHBY"));
        weaveSearch.setEditable(false); 
        weaveSearch.setValue("All"); 
        weaveSearch.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objWeave.setStrSearchBy(newValue);
                populateLibraryWeave();
            }    
        });
        ComboBox weaveSort = new ComboBox();
        weaveSort.getItems().addAll(
            "Name",
            "Date",
            "Shaft",
            "Treadles",
            "Float X",
            "Float Y"
        );
        weaveSort.setPromptText(objDictionaryAction.getWord("SORTBY"));
        weaveSort.setEditable(false); 
        weaveSort.setValue("Name"); 
        weaveSort.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objWeave.setStrOrderBy(newValue);
                populateLibraryWeave();
            }    
        });
        ComboBox weaveDirction = new ComboBox();
        weaveDirction.getItems().addAll(
            "Ascending",
            "Descending"
        );
        weaveDirction.setPromptText(objDictionaryAction.getWord("SORTDIRCTION"));
        weaveDirction.setEditable(false); 
        weaveDirction.setValue("Ascending"); 
        weaveDirction.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objWeave.setStrDirection(newValue);
                populateLibraryWeave();
            }    
        });
        
        weaveGP.add(weaveName,1,1);
        weaveGP.add(weaveSearch,3,1);
        weaveGP.add(weaveSort,5,1);
        weaveGP.add(weaveDirction,7,1);
        
        Separator weaveSH = new Separator();
        weaveSH.setValignment(VPos.CENTER);
        GridPane.setConstraints(weaveSH, 0, 2);
        GridPane.setColumnSpan(weaveSH, 8);
        weaveGP.getChildren().add(weaveSH);
    
        //===== Yarn =====//
        Label yarn = new Label(objDictionaryAction.getWord("YARNLIBRARY"));
        yarn.setGraphic(new ImageView(objConfiguration.getStrColour()+"/fabric_library.png"));
        yarn.setId("caption");
        GridPane.setConstraints(yarn, 0, 0);
        GridPane.setColumnSpan(yarn, 8);
        yarnGP.getChildren().add(yarn);
       
        objYarn = objYarn = new Yarn(null, "Warp", objConfiguration.getStrWarpName(), "#"+objConfiguration.getStrWarpColor(), objConfiguration.getIntWarpRepeat(), "A", objConfiguration.getIntWarpCount(), objConfiguration.getStrWarpUnit(), objConfiguration.getIntWarpPly(), objConfiguration.getIntWarpFactor(), objConfiguration.getDblWarpDiameter(), objConfiguration.getIntWarpTwist(), objConfiguration.getStrWarpSence(), objConfiguration.getIntWarpHairness(), objConfiguration.getIntWarpDistribution(), objFabric.getObjConfiguration().getDblWarpPrice(), objConfiguration.getObjUser().getUserAccess("YARN_LIBRARY"),objConfiguration.getObjUser().getStrUserID(),null);
        objYarn.setObjConfiguration(objConfiguration);
        objYarn.setStrCondition("");
        objYarn.setStrSearchBy("All");
        objYarn.setStrOrderBy("Name");
        objYarn.setStrDirection("Ascending");        
        //objYarn.setStrLimit("");
        
        yarnGP.add(new Text(objDictionaryAction.getWord("YARN")+" "+objDictionaryAction.getWord("NAME")),0,1);
        yarnGP.add(new Text(objDictionaryAction.getWord("YARN")+" "+objDictionaryAction.getWord("SEARCHBY")),2,1);
        yarnGP.add(new Text(objDictionaryAction.getWord("YARN")+" "+objDictionaryAction.getWord("SORTBY")),4,1);
        yarnGP.add(new Text(objDictionaryAction.getWord("YARN")+" "+objDictionaryAction.getWord("SORTDIRCTION")),6,1);
        
        final TextField yarnName = new TextField();
        yarnName.setPromptText(objDictionaryAction.getWord("PROMPTNAME"));
        yarnName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                objYarn.setStrCondition(yarnName.getText());
                populateLibraryYarn(); 
            }
        });
        ComboBox yarnSearch = new ComboBox();
        yarnSearch.getItems().addAll(
            "All",
            "Cotton",
            "Silk",
            "Wool",
            "Jute",
            "Linen",
            "Flex",
            "Hemp"
        );  
        yarnSearch.setPromptText(objDictionaryAction.getWord("SEARCHBY"));
        yarnSearch.setEditable(false); 
        yarnSearch.setValue("All"); 
        yarnSearch.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objYarn.setStrSearchBy(newValue);
                populateLibraryYarn();
            }    
        });
        ComboBox yarnSort = new ComboBox();
        yarnSort.getItems().addAll(
            "Name",
            "Date",
            "Type",
            "Ply",
            "Diameter",
            "Price"
        );
        yarnSort.setPromptText(objDictionaryAction.getWord("SORTBY"));
        yarnSort.setEditable(false); 
        yarnSort.setValue("Name"); 
        yarnSort.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objYarn.setStrOrderBy(newValue);
                populateLibraryYarn();
            }    
        });
        ComboBox yarnDirction = new ComboBox();
        yarnDirction.getItems().addAll(
            "Ascending",
            "Descending"
        );
        yarnDirction.setPromptText(objDictionaryAction.getWord("SORTDIRCTION"));
        yarnDirction.setEditable(false); 
        yarnDirction.setValue("Ascending"); 
        yarnDirction.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objYarn.setStrDirection(newValue);
                populateLibraryYarn();
            }    
        });
        
        yarnGP.add(yarnName,1,1);
        yarnGP.add(yarnSearch,3,1);
        yarnGP.add(yarnSort,5,1);
        yarnGP.add(yarnDirction,7,1);
        
        Separator yarnSH = new Separator();
        yarnSH.setValignment(VPos.CENTER);
        GridPane.setConstraints(yarnSH, 0, 2);
        GridPane.setColumnSpan(yarnSH, 8);
        yarnGP.getChildren().add(yarnSH);
    
        //===== Colour =====//
        Label colour = new Label(objDictionaryAction.getWord("COLOURLIBRARY"));
        colour.setGraphic(new ImageView(objConfiguration.getStrColour()+"/tex_calculator.png"));
        colour.setId("caption");
        GridPane.setConstraints(colour, 0, 0);
        GridPane.setColumnSpan(colour, 8);
        colourGP.getChildren().add(colour);
       
        objColour = new Colour();
        objColour.setObjConfiguration(objConfiguration);
        objColour.setStrCondition("");
        objColour.setStrOrderBy("Type");
        objColour.setStrSearchBy("");
        objColour.setStrDirection("Ascending");
        //objColour.setStrLimit("");
        
        colourGP.add(new Text(objDictionaryAction.getWord("COLOUR")+" "+objDictionaryAction.getWord("NAME")),0,1);
        colourGP.add(new Text(objDictionaryAction.getWord("COLOUR")+" "+objDictionaryAction.getWord("SEARCHBY")),2,1);
        colourGP.add(new Text(objDictionaryAction.getWord("COLOUR")+" "+objDictionaryAction.getWord("SORTBY")),4,1);
        colourGP.add(new Text(objDictionaryAction.getWord("COLOUR")+" "+objDictionaryAction.getWord("SORTDIRCTION")),6,1);
        
        final TextField colourName = new TextField();
        colourName.setPromptText(objDictionaryAction.getWord("PROMPTNAME"));
        colourName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                objColour.setStrCondition(colourName.getText());
                populateLibraryColour(); 
            }
        });
        ComboBox colourSearch = new ComboBox();
        colourSearch.getItems().addAll(
            "All",
            "Web Color",
            "Pantone Color"
        );
        colourSearch.setPromptText(objDictionaryAction.getWord("SEARCHBY"));
        colourSearch.setEditable(false); 
        colourSearch.setValue("All"); 
        colourSearch.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objColour.setStrSearchBy(newValue);
                populateLibraryColour();
            }    
        });
        ComboBox colourSort = new ComboBox();
        colourSort.getItems().addAll(
            "Name",
            "Date",
            "Type",
            "Code",
            "Hex Code",
            "Red",
            "Green",
            "Blue"
        );            
        colourSort.setPromptText(objDictionaryAction.getWord("SORTBY"));
        colourSort.setEditable(false); 
        colourSort.setValue("Name"); 
        colourSort.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objColour.setStrOrderBy(newValue);
                populateLibraryColour();
            }    
        });
        ComboBox colourDirction = new ComboBox();
        colourDirction.getItems().addAll(
            "Ascending",
            "Descending"
        );
        colourDirction.setPromptText(objDictionaryAction.getWord("SORTDIRCTION"));
        colourDirction.setEditable(false); 
        colourDirction.setValue("Ascending"); 
        colourDirction.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                objColour.setStrDirection(newValue);
                populateLibraryColour();
            }    
        });
        
        colourGP.add(colourName,1,1);
        colourGP.add(colourSearch,3,1);
        colourGP.add(colourSort,5,1);
        colourGP.add(colourDirction,7,1);
        
        Separator colourSH = new Separator();
        colourSH.setValignment(VPos.CENTER);
        GridPane.setConstraints(colourSH, 0, 2);
        GridPane.setColumnSpan(colourSH, 8);
        colourGP.getChildren().add(colourSH);
        
        //--------------------------
        ScrollPane container = new ScrollPane();
        container.setPrefSize(objConfiguration.WIDTH/1.1,objConfiguration.HEIGHT/1.5);
        
        GP_container = new GridPane();         
        GP_container.setAlignment(Pos.TOP_CENTER);
        GP_container.setHgap(20);
        GP_container.setVgap(20);
        GP_container.setPadding(new Insets(1, 1, 1, 1));
        container.setContent(GP_container);
        /*
        clothGP.add(GP_container,0,3,8,1);
        fabricGP.add(GP_container,0,3,8,1);
        artworkGP.add(GP_container,0,3,8,1);
        weaveGP.add(GP_container,0,3,8,1);
        yarnGP.add(GP_container,0,3,8,1);
        colourGP.add(GP_container,0,3,8,1);
        */
        populateLibraryCloth();
        
        GridPane bodyContainer = new GridPane();
        bodyContainer.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        bodyContainer.setId("container");
        
        Label caption = new Label(objDictionaryAction.getWord("LIBRARY")+" "+objDictionaryAction.getWord("UTILITY"));
        caption.setId("caption");
        bodyContainer.add(caption, 0, 0, 1, 1);
        
        bodyContainer.add(tabPane, 0, 1, 1, 1);
        bodyContainer.add(container, 0, 2, 1, 1);
        /*
        final Separator sepHor = new Separator();
        sepHor.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor, 0, 2);
        GridPane.setColumnSpan(sepHor, 1);
        bodyContainer.getChildren().add(sepHor);
        */
        root.setCenter(bodyContainer);
        
        libraryStage.getIcons().add(new Image("/media/icon.png"));
        libraryStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWFABRICSETTINGS")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        //libraryStage.setIconified(true);
        libraryStage.setResizable(false);
        libraryStage.setScene(scene);
        libraryStage.setX(0);
        libraryStage.setY(0);
        libraryStage.show();
        libraryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
                        libraryStage.close();
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
 
    /*Cloth Library*/
    private void populateLibraryCloth(){
        /*try{

            GP_container.getChildren().clear();
                GP_container.add(new Text(objDictionaryAction.getWord("CLOTH")+" - "+objDictionaryAction.getWord("NOVALUE")),0,0);
        } catch(Exception ex){
            
        }*/
        final Cloth objCloth = new Cloth();
        objCloth.setObjConfiguration(objConfiguration);
        objCloth.setStrCondition("");
        objCloth.setStrOrderBy("Name");
        objCloth.setStrDirection("Ascending");
        GP_container.getChildren().clear();
       
        List lstClothDeatails=null, lstCloth;
        BufferedImage bufferedImage = null;
        try {
            ClothAction objClothAction = new ClothAction();
	    lstClothDeatails = objClothAction.lstImportArtwork(objCloth);
            if(lstClothDeatails.size()==0){
                GP_container.add(new Text(objDictionaryAction.getWord("CLOTH")+" - "+objDictionaryAction.getWord("NOVALUE")),0,0);
            }else{ 
                for (int i=0, j = lstClothDeatails.size(); i<j;i++){
                    lstCloth = (ArrayList)lstClothDeatails.get(i);
                    try{
                        SeekableStream stream = new ByteArraySeekableStream((byte[])lstCloth.get(3));
                        String[] names = ImageCodec.getDecoderNames(stream);
                        ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                        RenderedImage im = dec.decodeAsRenderedImage();
                        bufferedImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
                        Image image=SwingFXUtils.toFXImage(bufferedImage, null);
                        String strAccess=new IDGenerator().getUserAcessValueData("CLOTH_LIBRARY",lstCloth.get(5).toString());
                        
                        final ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(222);
                        imageView.setFitWidth(222);
                        imageView.setId(strAccess);
                        imageView.setUserData(lstCloth.get(0).toString());
                        String strTooltip = 
                            objDictionaryAction.getWord("NAME")+": "+lstCloth.get(1).toString()+"\n"+
                            objDictionaryAction.getWord("DESCRIPTION")+": "+lstCloth.get(2)+"\n"+
                            objDictionaryAction.getWord("REGION")+": "+lstCloth.get(4)+"\n"+
                            objDictionaryAction.getWord("PERMISSION")+": "+strAccess+"\n"+
                            objDictionaryAction.getWord("BY")+": "+lstCloth.get(6).toString()+"\n"+
                            objDictionaryAction.getWord("DATE")+": "+lstCloth.get(7).toString();
                        Tooltip toolTip = new Tooltip(strTooltip);
                        Tooltip.install(imageView, toolTip);
                        /*imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {                  
                                if(imageView.getId().equalsIgnoreCase("Public")){
                                    objCloth.getObjConfiguration().setServicePasswordValid(true);
                                } else{
                                    new MessageView(objCloth.getObjConfiguration());
                                }
                                if(objCloth.getObjConfiguration().getServicePasswordValid()){
                                    objCloth.getObjConfiguration().setServicePasswordValid(false);
                                    objCloth.setStrClothId(imageView.getUserData().toString());
                                    System.gc();
                                }
                            }
                        });*/
                        GP_container.add(imageView, i%4, i/4);
                    } catch (Exception ex){
                        Logger.getLogger(LibraryView.class.getName()).log(Level.SEVERE, null, ex);
                        new Logging("SEVERE",LibraryView.class.getName(),"ClothImportView()",ex);
                    }
                }
            }
	} catch (Exception ex) {
            Logger.getLogger(LibraryView.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",LibraryView.class.getName(),"ClothImportView()",ex);
	}

    }
    /*Fabric Library*/
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
                                scene.getStylesheets().add(SimulatorView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                                            libraryStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORT")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
                                            File selectedDirectory = directoryChooser.showDialog(libraryStage);
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
                                                String zipFilePath=file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf(".sql"))+".bun";
                                                String passwordToZip = file.getName().substring(0, file.getName().indexOf(".sql"));
                                                new EncryptZip(zipFilePath, filesToZip, passwordToZip);
                                                file.delete();
                                                lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+savePath);
                                            } catch (IOException ex) {
                                                new Logging("SEVERE",SimulatorView.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                            } catch (SQLException ex) {
                                                new Logging("SEVERE",SimulatorView.class.getName(),"Export Data"+ex.getMessage(),ex);
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
                                scene.getStylesheets().add(SimulatorView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                                                    populateLibraryFabric();
                                                    lblStatus.setText(lblFabric.getId()+" : "+objDictionaryAction.getWord("ACTIONDELETE"));
                                                } catch (SQLException ex) {
                                                    new Logging("SEVERE",SimulatorView.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                                } catch (Exception ex) {
                                                    new Logging("SEVERE",SimulatorView.class.getName(),"Export Data"+ex.getMessage(),ex);
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
                                scene.getStylesheets().add(SimulatorView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                                            libraryStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORT")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
                                            File selectedDirectory = directoryChooser.showDialog(libraryStage);
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
                                                String zipFilePath=file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf(".sql"))+".bun";
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
                                                populateLibraryFabric();
                                            } catch (IOException ex) {
                                                new Logging("SEVERE",SimulatorView.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                            } catch (SQLException ex) {
                                                new Logging("SEVERE",SimulatorView.class.getName(),"Export Data"+ex.getMessage(),ex);
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
                                scene.getStylesheets().add(SimulatorView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                                                    populateLibraryFabric();
                                                } catch (SQLException ex) {
                                                    new Logging("SEVERE",SimulatorView.class.getName(),"Change Permission Data"+ex.getMessage(),ex);
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
                                    libraryStage.close();
                                    System.gc();
                                    FabricView objFabricView = new FabricView(objConfiguration);
                                    System.gc();
                                } catch (SQLException ex) {
                                    new Logging("SEVERE",SimulatorView.class.getName(),"Load Fabric in Editor"+ex.toString(),ex);
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
            new Logging("SEVERE",SimulatorView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    /*Artwork Library*/
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
                                scene.getStylesheets().add(SimulatorView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                                            libraryStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORT")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
                                            File selectedDirectory = directoryChooser.showDialog(libraryStage);
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
                                                String zipFilePath=file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf(".sql"))+".bun";
                                                String passwordToZip = file.getName().substring(0, file.getName().indexOf(".sql"));
                                                new EncryptZip(zipFilePath, filesToZip, passwordToZip);
                                                file.delete();
                                                lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+savePath);
                                            } catch (IOException ex) {
                                                new Logging("SEVERE",SimulatorView.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                            } catch (SQLException ex) {
                                                new Logging("SEVERE",SimulatorView.class.getName(),"Export Data"+ex.getMessage(),ex);
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
                                scene.getStylesheets().add(SimulatorView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                                                    populateLibraryArtwork();
                                                    lblStatus.setText(lblArtwork.getId()+" : "+objDictionaryAction.getWord("ACTIONDELETE"));                                            
                                                } catch (SQLException ex) {
                                                    new Logging("SEVERE",SimulatorView.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                                } catch (Exception ex) {
                                                    new Logging("SEVERE",SimulatorView.class.getName(),"Export Data"+ex.getMessage(),ex);
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
                                scene.getStylesheets().add(SimulatorView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                                            libraryStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORT")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
                                            File selectedDirectory = directoryChooser.showDialog(libraryStage);
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
                                                String zipFilePath=file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf(".sql"))+".bun";
                                                String passwordToZip = file.getName().substring(0, file.getName().indexOf(".sql"));
                                                new EncryptZip(zipFilePath, filesToZip, passwordToZip);
                                                file.delete();
                                                lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+savePath);
                                                objArtworkAction = new ArtworkAction();
                                                objArtworkAction.clearArtwork(lblArtwork.getId().toString());
                                                populateLibraryArtwork();
                                            } catch (IOException ex) {
                                                new Logging("SEVERE",SimulatorView.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                            } catch (SQLException ex) {
                                                new Logging("SEVERE",SimulatorView.class.getName(),"Export Data"+ex.getMessage(),ex);
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
                                scene.getStylesheets().add(SimulatorView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                                                    populateLibraryArtwork();
                                                } catch (SQLException ex) {
                                                    new Logging("SEVERE",SimulatorView.class.getName(),"Change Permission Data"+ex.getMessage(),ex);
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
                                libraryStage.close();
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
                                scene.getStylesheets().add(SimulatorView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                                            libraryStage.close();
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
            new Logging("SEVERE",SimulatorView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    /*Weave Library*/
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
                                scene.getStylesheets().add(SimulatorView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                                            libraryStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORT")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
                                            File selectedDirectory = directoryChooser.showDialog(libraryStage);
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
                                                String zipFilePath=file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf(".sql"))+".bun";
                                                String passwordToZip = file.getName().substring(0, file.getName().indexOf(".sql"));
                                                new EncryptZip(zipFilePath, filesToZip, passwordToZip);
                                                file.delete();
                                                lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+savePath);
                                            } catch (IOException ex) {
                                                new Logging("SEVERE",SimulatorView.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                            } catch (SQLException ex) {
                                                new Logging("SEVERE",SimulatorView.class.getName(),"Export Data"+ex.getMessage(),ex);
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
                                scene.getStylesheets().add(SimulatorView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                                                    populateLibraryWeave();
                                                    lblStatus.setText(lblWeave.getId()+" : "+objDictionaryAction.getWord("ACTIONDELETE"));
                                                } catch (SQLException ex) {
                                                    new Logging("SEVERE",SimulatorView.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                                } catch (Exception ex) {
                                                    new Logging("SEVERE",SimulatorView.class.getName(),"Export Data"+ex.getMessage(),ex);
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
                                scene.getStylesheets().add(SimulatorView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                                            libraryStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWEXPORT")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
                                            File selectedDirectory = directoryChooser.showDialog(libraryStage);
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
                                                String zipFilePath=file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf(".sql"))+".bun";
                                                String passwordToZip = file.getName().substring(0, file.getName().indexOf(".sql"));
                                                new EncryptZip(zipFilePath, filesToZip, passwordToZip);
                                                file.delete();
                                                lblStatus.setText(objDictionaryAction.getWord("EXPORTEDTO")+" "+savePath);
                                                objWeaveAction = new WeaveAction();
                                                objWeaveAction.clearWeave(lblWeave.getId().toString());
                                                populateLibraryWeave();
                                            } catch (IOException ex) {
                                                new Logging("SEVERE",SimulatorView.class.getName(),"Export Data"+ex.getMessage(),ex);
                                                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                                            } catch (SQLException ex) {
                                                new Logging("SEVERE",SimulatorView.class.getName(),"Export Data"+ex.getMessage(),ex);
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
                                scene.getStylesheets().add(SimulatorView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                                                    populateLibraryWeave();
                                                    dialogStage.close();                                            
                                                } catch (SQLException ex) {
                                                    new Logging("SEVERE",SimulatorView.class.getName(),"Change Permission Data"+ex.getMessage(),ex);
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
                                libraryStage.close();
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
                                scene.getStylesheets().add(SimulatorView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                                            libraryStage.close();
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
            new Logging("SEVERE",SimulatorView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    /*Cloth Library*/
    private void populateLibraryYarn(){
        try{
            GP_container.getChildren().clear();
            List lstYarn=null;
            List lstYarnDeatails = new ArrayList();
            objYarnAction = new YarnAction();
            lstYarnDeatails = objYarnAction.lstImportYarn(objYarn);
            if(lstYarnDeatails.size()==0){
                GP_container.getChildren().add(new Text(objDictionaryAction.getWord("YARN")+" - "+objDictionaryAction.getWord("NOVALUE")));
            }else{            
                for (int i=0, k=0; i<lstYarnDeatails.size();i++, k+=2){
                    lstYarn = (ArrayList)lstYarnDeatails.get(i);
                    
                    byte[] bytWeaveThumbnil = (byte[])lstYarn.get(14);
                    SeekableStream stream = new ByteArraySeekableStream(bytWeaveThumbnil);
                    String[] names = ImageCodec.getDecoderNames(stream);
                    ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
                    RenderedImage im = dec.decodeAsRenderedImage();
                    BufferedImage bufferedImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
                    Image image=SwingFXUtils.toFXImage(bufferedImage, null);
                    ImageView ivYarn = new ImageView(image);
                    ivYarn.setFitHeight(222);
                    ivYarn.setFitWidth(222);
                    stream.close();
                    bufferedImage = null;
                    GP_container.add(ivYarn, k%6, i/2);
                    final String strAccess = new IDGenerator().getUserAcessValueData("YARN_LIBRARY",lstYarn.get(15).toString());
                    int intUsage=0;
                    objYarnAction = new YarnAction();
                    intUsage = objYarnAction.countYarnUsage(lstYarn.get(0).toString());
                    
                    String strTooltip =
                                objDictionaryAction.getWord("YARNNAME")+": "+lstYarn.get(1).toString()+"\n"+
                                objDictionaryAction.getWord("YARNTYPE")+": "+lstYarn.get(2).toString()+"\n"+
                                objDictionaryAction.getWord("YARNCOLOR")+": "+lstYarn.get(3).toString()+"\n"+
                                objDictionaryAction.getWord("YARNCOUNT")+": "+lstYarn.get(4).toString()+"\n"+
                                objDictionaryAction.getWord("YARNUNIT")+": "+lstYarn.get(5).toString()+"\n"+
                                objDictionaryAction.getWord("YARNPLY")+": "+lstYarn.get(6).toString()+"\n"+
                                objDictionaryAction.getWord("YARNFACTOR")+": "+lstYarn.get(7).toString()+"\n"+
                                objDictionaryAction.getWord("YARNDIAMETER")+": "+lstYarn.get(8).toString()+"\n"+
                                objDictionaryAction.getWord("YARNSENCE")+": "+lstYarn.get(9).toString()+"\n"+
                                objDictionaryAction.getWord("YARNTWIST")+": "+lstYarn.get(10).toString()+"\n"+
                                objDictionaryAction.getWord("YARNHAIRNESS")+": "+lstYarn.get(11).toString()+"\n"+
                                objDictionaryAction.getWord("YARNDISTRIBUTION")+": "+lstYarn.get(12).toString()+"\n"+
                                objDictionaryAction.getWord("YARNPRICE")+": "+lstYarn.get(13).toString()+"\n"+
                                objDictionaryAction.getWord("USED")+": "+intUsage+"\n"+
                                objDictionaryAction.getWord("PERMISSION")+": "+strAccess+"\n"+
                                objDictionaryAction.getWord("BY")+": "+lstYarn.get(16).toString()+"\n"+
                                objDictionaryAction.getWord("DATE")+": "+lstYarn.get(17).toString();
                    final Label lblYarn = new Label(strTooltip);
                    lblYarn.setTooltip(new Tooltip(strTooltip));
                    lblYarn.setId(lstYarn.get(0).toString());
                    lblYarn.setUserData(lstYarn.get(0).toString());
                    GP_container.add(lblYarn, (k+1)%6, i/2);
                }
            }
        } catch (Exception ex) {
            new Logging("SEVERE",SimulatorView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    /*Colour Library*/
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
            new Logging("SEVERE",SimulatorView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
 

    @Override
    public void start(Stage stage) throws Exception {
        new LibraryView(stage);
        new Logging("WARNING",LibraryView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static void main(String[] args) {   
        launch(args);    
    }
}
        
