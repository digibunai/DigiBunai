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

import com.mla.dictionary.DictionaryAction;
import com.mla.main.AboutView;
import com.mla.main.Configuration;
import com.mla.main.ContactView;
import com.mla.main.HelpView;
import com.mla.main.IDGenerator;
import com.mla.main.Logging;
import com.mla.main.MessageView;
import com.mla.main.TechnicalView;
import com.mla.yarn.Yarn;
import com.mla.yarn.YarnImportView;
import com.sun.media.jai.codec.ByteArraySeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;
/**
 *
 * @Designing GUI window for fabric preferences
 * @author Amit Kumar Singh
 * 
 */
public class SimulatorView extends Application {
    public static Stage simulatorStage;
    BorderPane root;
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;
    
    GridPane simulatorGP;
    
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    Simulator objSimulator;
    
    private Menu homeMenu;
    private Menu helpMenu;

    private Button btnSave;
    private Button btnSaveAs;
    private Button btnPreview;
    private Button btnDelete;
    private Button btnClear;
    
    int index = -1;
    boolean isNew = false;

    public SimulatorView(final Stage primaryStage) {}
    
    public SimulatorView(Configuration objConfigurationCall) {
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);

        simulatorStage = new Stage(); 
        root = new BorderPane();
        Scene scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(SimulatorView.class.getResource(objConfiguration.getStrTemplate()+"/setting.css").toExternalForm());
        
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
        menuBar.prefWidthProperty().bind(simulatorStage.widthProperty());
        
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
                scene.getStylesheets().add(SimulatorView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                simulatorStage.close();    
            }
        });    
        helpMenu.getItems().addAll(helpMenuItem, technicalMenuItem, aboutMenuItem, contactMenuItem, new SeparatorMenuItem(), exitMenuItem);        
        menuBar.getMenus().addAll(homeMenu, helpMenu);
        root.setTop(menuBar);
        
        TabPane tabPane = new TabPane();

        Tab simulatorTab = new Tab();
        
        simulatorTab.setClosable(false);
        
        simulatorTab.setText(objDictionaryAction.getWord("SIMULATOR"));
        simulatorTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSIMULATOR")));
        //simulatorTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/simulation.png"));
        
        simulatorGP = new GridPane();
        simulatorGP.setId("container");
        //simulatorGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //simulatorGP.setAlignment(Pos.TOP_CENTER);
        simulatorTab.setContent(simulatorGP);
        tabPane.getTabs().add(simulatorTab);
        
        try{    
            System.gc();
            final ObservableList<Simulator> simulatorData = FXCollections.observableArrayList();
            
            Label simulator = new Label(objDictionaryAction.getWord("SIMULATOR"));
            simulator.setGraphic(new ImageView(objConfiguration.getStrColour()+"/simulation.png"));
            simulator.setId("caption");
            GridPane.setConstraints(simulator, 0, 0);
            GridPane.setColumnSpan(simulator, 5);
            simulatorGP.getChildren().add(simulator);

            Separator sepHor1 = new Separator();
            sepHor1.setValignment(VPos.CENTER);
            GridPane.setConstraints(sepHor1, 0, 1);
            GridPane.setColumnSpan(sepHor1, 5);
            simulatorGP.getChildren().add(sepHor1);
        
            simulatorGP.add(new Label(objDictionaryAction.getWord("SIMULATOR")+" "+objDictionaryAction.getWord("NAME")), 0, 2);
            simulatorGP.add(new Label(objDictionaryAction.getWord("SIMULATOR")+" "+objDictionaryAction.getWord("FABRICTYPE")), 2, 2);
            simulatorGP.add(new Label(objDictionaryAction.getWord("PPI")), 0, 3);
            simulatorGP.add(new Label(objDictionaryAction.getWord("EPI")), 2, 3);
            simulatorGP.add(new Label(objDictionaryAction.getWord("RESOLUTION")), 0, 4);
            simulatorGP.add(new Label(objDictionaryAction.getWord("DPI")), 2, 4);
            simulatorGP.add(new Label(objDictionaryAction.getWord("DEFAULTYARN")), 0, 5);
            simulatorGP.add(new Label(objDictionaryAction.getWord("PATH")), 2, 5);
        
            final TextField simulatorTF = new TextField();    
            simulatorTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPNAME")));
            simulatorGP.add(simulatorTF, 1, 2);
            final ComboBox fabricTypeCB = new ComboBox();
            fabricTypeCB.getItems().addAll("Plain","Kadhua","Fekuwa-Float","Fekuwa-Cutwork","Binding-Irregular","Binding-Regular","Tanchoi","Tissue");        
            fabricTypeCB.setValue("Plain");
            fabricTypeCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPFABRICTYPE")));
            simulatorGP.add(fabricTypeCB, 3, 2);
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
            simulatorGP.add(ppiTF, 1, 3);
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
            simulatorGP.add(epiTF, 3, 3);
            final ComboBox resolutionCB = new ComboBox();
            resolutionCB.getItems().addAll("800x600","1024x768","1152x864","1280x960","1280x1024");   
            String strResolution = objConfiguration.getStrResolution();
            resolutionCB.setValue(strResolution);
            resolutionCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPRESOLUTION")));
            simulatorGP.add(resolutionCB, 1, 4);
            final Label dpiV= new Label(Integer.toString(objConfiguration.getIntDPI()));
            dpiV.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDPI")));
            simulatorGP.add(dpiV, 3, 4);
            final Label yarnDefaultTF = new Label();
            yarnDefaultTF.setText("YARNG1");
            yarnDefaultTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDEFAULTYARN")));
            simulatorGP.add(yarnDefaultTF, 1, 5);    
            final Hyperlink yarnDefaultHL = new Hyperlink();
            yarnDefaultHL.setText(objDictionaryAction.getWord("TOOLTIPCHANGEDEFAULT"));
            yarnDefaultHL.setGraphic(new ImageView(objConfiguration.getStrColour()+"/browse.png"));
            yarnDefaultHL.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDEFAULTFABRIC")));
            simulatorGP.add(yarnDefaultHL, 1, 6);   
            final TextField txtPath = new TextField();
            simulatorGP.add(txtPath, 3, 5);
            final Button btnBrowse = new Button(objDictionaryAction.getWord("BROWSE"));
            btnBrowse.setGraphic(new ImageView(objConfiguration.getStrColour()+"/browse.png"));
            simulatorGP.add(btnBrowse, 3, 6);

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

            btnSaveAs = new Button(objDictionaryAction.getWord("ADDNEW"));
            btnSave = new Button(objDictionaryAction.getWord("UPDATE"));
            btnDelete = new Button(objDictionaryAction.getWord("DELETE"));
            btnPreview = new Button(objDictionaryAction.getWord("PREVIEW"));
            btnClear = new Button(objDictionaryAction.getWord("CLEAR"));
            
            //btnSave.setMaxWidth(Double.MAX_VALUE);
            //btnRun.setMaxWidth(Double.MAX_VALUE);
            //btnClear.setMaxWidth(Double.MAX_VALUE);
            
            btnSaveAs.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
            btnSave.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
            btnDelete.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
            btnPreview.setGraphic(new ImageView(objConfiguration.getStrColour()+"/preview.png"));            
            btnClear.setGraphic(new ImageView(objConfiguration.getStrColour()+"/clear.png"));
            
            btnSaveAs.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPADDNEW")));
            btnSave.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPUPDATE")));
            btnDelete.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDELETE")));
            btnPreview.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPREVIEW")));
            btnClear.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLEAR")));
            
            btnSaveAs.setDisable(false);
            btnSave.setDisable(true);
            btnDelete.setDisable(true);
            btnPreview.setDisable(false);
            btnClear.setDefaultButton(true);
            
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
            btnSave.setOnAction(new EventHandler<ActionEvent>() {
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
            btnSaveAs.setOnAction(new EventHandler<ActionEvent>() {
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
            btnClear.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    System.gc();
                    btnSaveAs.setDisable(true);
                    btnSave.setDisable(true);
                    btnDelete.setDisable(true);
                    btnPreview.setDisable(true);
                    btnClear.setDisable(true);
                    
                    index = -1;
                    simulatorTF.setText("");
                    fabricTypeCB.setValue("Plain");
                    txtPath.setText("");
                }
            });
                    
            simulatorGP.add(btnSaveAs, 0, 7);
            simulatorGP.add(btnSave, 1, 7);
            simulatorGP.add(btnDelete, 2, 7);
            //simulatorGP.add(btnPreview, 3, 7);
            simulatorGP.add(btnClear, 4, 7);
            
            Separator sepHor2 = new Separator();
            sepHor2.setValignment(VPos.CENTER);
            GridPane.setConstraints(sepHor2, 0, 8);
            GridPane.setColumnSpan(sepHor2, 5);
            simulatorGP.getChildren().add(sepHor2);
        
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
                            btnSaveAs.setDisable(false);
                            btnSave.setDisable(true);
                            btnDelete.setDisable(true);
                            btnPreview.setDisable(false);
                            btnClear.setDisable(false);
                        }else{
                            btnSaveAs.setDisable(true);
                            btnSave.setDisable(false);
                            btnDelete.setDisable(true);
                            btnPreview.setDisable(true);
                            btnClear.setDisable(false);
                        }
                    }
                }
            });        
            simulatorGP.add(tsHB, 0, 9, 5, 1);
           
            Separator sepHor3 = new Separator();
            sepHor3.setValignment(VPos.CENTER);
            GridPane.setConstraints(sepHor3, 0, 10);
            GridPane.setColumnSpan(sepHor3, 5);
            simulatorGP.getChildren().add(sepHor3);
        
            //System.err.print(objFabric.getLstYarn().size());
            TableView<Simulator> simulatorTable = new TableView<Simulator>();
            simulatorTable.setPrefWidth(objConfiguration.WIDTH/1.6);
            
            UtilityAction objUtilityAction = new UtilityAction();
            objSimulator = new Simulator(null, "", "", null, 0, 0, 0, null, null, null, null);
            objSimulator.setObjConfiguration(objConfiguration);
            objSimulator.setStrCondition("");
            objSimulator.setStrOrderBy("Name");
            objSimulator.setStrSearchBy("All");
            objSimulator.setStrDirection("Ascending");
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
                    
                    btnSaveAs.setDisable(true);
                    btnSave.setDisable(false);
                    btnDelete.setDisable(false);
                    btnPreview.setDisable(false);
                    btnClear.setDisable(false);
                    oldRB.setSelected(true);
                    //System.out.println(t1.getThreadColor()+"selection "+index+" changed"+Color.web(t1.getThreadColor().substring(1))+":"+Color.valueOf(t1.getThreadColor().substring(1)));                        
                }
            });
            
            ScrollPane container = new ScrollPane();
            container.setPrefSize(objConfiguration.WIDTH/1.6,objConfiguration.HEIGHT/2);    
            container.setContent(simulatorTable);
            GridPane.setConstraints(container, 0, 11);
            GridPane.setColumnSpan(container, 5);
            simulatorGP.getChildren().add(container);
            
        } catch (SQLException ex) {
            new Logging("SEVERE",SimulatorView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",SimulatorView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
        
        GridPane bodyContainer = new GridPane();
        bodyContainer.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        bodyContainer.setId("container");
        
        Label caption = new Label(objDictionaryAction.getWord("SIMULATOR")+" "+objDictionaryAction.getWord("UTILITY"));
        caption.setId("caption");
        bodyContainer.add(caption, 0, 0, 1, 1);
        
        bodyContainer.add(tabPane, 0, 1, 1, 1);
        /*
        final Separator sepHor = new Separator();
        sepHor.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor, 0, 2);
        GridPane.setColumnSpan(sepHor, 1);
        bodyContainer.getChildren().add(sepHor);
        */
        root.setCenter(bodyContainer);
        
        simulatorStage.getIcons().add(new Image("/media/icon.png"));
        simulatorStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWFABRICSETTINGS")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        //simulatorStage.setIconified(true);
        simulatorStage.setResizable(false);
        simulatorStage.setScene(scene);
        simulatorStage.setX(0);
        simulatorStage.setY(0);
        simulatorStage.show();
        simulatorStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
                scene.getStylesheets().add(SimulatorView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
    
    @Override
    public void start(Stage stage) throws Exception {
        new SimulatorView(stage);
        new Logging("WARNING",SimulatorView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static void main(String[] args) {   
        launch(args);    
    }    
}