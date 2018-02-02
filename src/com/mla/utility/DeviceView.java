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
import static com.mla.utility.SimulatorView.simulatorStage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
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
/**
 *
 * @Designing GUI window for fabric preferences
 * @author Amit Kumar Singh
 * 
 */
public class DeviceView extends Application {
    public static Stage deviceStage;
    BorderPane root;
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;
    
    GridPane deviceGP;
    
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    Device objDevice;
    
    private Menu homeMenu;
    private Menu helpMenu;

    private Button btnRun;
    private Button btnSave;
    private Button btnSaveAs;
    private Button btnDelete;
    private Button btnClear;
    
    int index = -1;
    boolean isNew = false;

    public DeviceView(final Stage primaryStage) {}
    
    public DeviceView(Configuration objConfigurationCall) {
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);

        deviceStage = new Stage(); 
        root = new BorderPane();
        Scene scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(DeviceView.class.getResource(objConfiguration.getStrTemplate()+"/setting.css").toExternalForm());
        
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
        menuBar.prefWidthProperty().bind(deviceStage.widthProperty());
        
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
                scene.getStylesheets().add(DeviceView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        deviceStage.close();
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
                deviceStage.close();    
            }
        });    
        helpMenu.getItems().addAll(helpMenuItem, technicalMenuItem, aboutMenuItem, contactMenuItem, new SeparatorMenuItem(), exitMenuItem);        
        menuBar.getMenus().addAll(homeMenu, helpMenu);
        root.setTop(menuBar);
        
        TabPane tabPane = new TabPane();

        Tab deviceTab = new Tab();
        
        deviceTab.setClosable(false);
        
        deviceTab.setText(objDictionaryAction.getWord("APPLICATIONINTEGRATION"));
        deviceTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPAPPLICATIONINTEGRATION")));
        //deviceTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/application_integration.png"));
        
        deviceGP = new GridPane();
        deviceGP.setId("container");
        //deviceGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //deviceGP.setAlignment(Pos.TOP_CENTER);
        deviceTab.setContent(deviceGP);
        tabPane.getTabs().add(deviceTab);
        
        try{    
            System.gc();
            final ObservableList<Device> deviceData = FXCollections.observableArrayList();

            Label device = new Label(objDictionaryAction.getWord("APPLICATIONINTEGRATION"));
            device.setGraphic(new ImageView(objConfiguration.getStrColour()+"/application_integration.png"));
            device.setId("caption");
            GridPane.setConstraints(device, 0, 0);
            GridPane.setColumnSpan(device, 5);
            deviceGP.getChildren().add(device);

            Separator sepHor1 = new Separator();
            sepHor1.setValignment(VPos.CENTER);
            GridPane.setConstraints(sepHor1, 0, 1);
            GridPane.setColumnSpan(sepHor1, 5);
            deviceGP.getChildren().add(sepHor1);
        
            deviceGP.add(new Label(objDictionaryAction.getWord("APPLICATION")+" "+objDictionaryAction.getWord("NAME")), 0, 2);
            deviceGP.add(new Label(objDictionaryAction.getWord("APPLICATION")+" "+objDictionaryAction.getWord("TYPE")), 2, 2);
            deviceGP.add(new Label(objDictionaryAction.getWord("APPLICATION")+" "+objDictionaryAction.getWord("PATH")), 0, 3);
            
            final TextField name = new TextField();
            deviceGP.add(name, 1, 2);
            final ComboBox type = new ComboBox();
            type.getItems().addAll("Designing S/W","Puncing M/C");  
            type.setValue("Designing S/W");
            deviceGP.add(type, 3, 2);
            final TextField path = new TextField();
            deviceGP.add(path, 1, 3);
            final Button btnBrowse = new Button(objDictionaryAction.getWord("BROWSE"));
            btnBrowse.setGraphic(new ImageView(objConfiguration.getStrColour()+"/browse.png"));
            deviceGP.add(btnBrowse, 2, 3);
            
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
            
            btnSaveAs = new Button(objDictionaryAction.getWord("ADDNEW"));
            btnSave = new Button(objDictionaryAction.getWord("UPDATE"));
            btnDelete = new Button(objDictionaryAction.getWord("DELETE"));
            btnRun = new Button(objDictionaryAction.getWord("RUN"));
            btnClear = new Button(objDictionaryAction.getWord("CLEAR"));
            
            //btnSave.setMaxWidth(Double.MAX_VALUE);
            //btnRun.setMaxWidth(Double.MAX_VALUE);
            //btnClear.setMaxWidth(Double.MAX_VALUE);
            
            btnSaveAs.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
            btnSave.setGraphic(new ImageView(objConfiguration.getStrColour()+"/update.png"));
            btnDelete.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
            btnRun.setGraphic(new ImageView(objConfiguration.getStrColour()+"/preview.png"));            
            btnClear.setGraphic(new ImageView(objConfiguration.getStrColour()+"/clear.png"));
                       
            btnSaveAs.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPADDNEW")));
            btnSave.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPUPDATE")));
            btnDelete.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDELETE")));
            btnRun.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPRUN")));
            btnClear.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLEAR")));
            
            btnSaveAs.setDisable(false);
            btnSave.setDisable(true);
            btnDelete.setDisable(true);
            btnRun.setDisable(false);
            btnClear.setDefaultButton(true);
            
            btnRun.setOnAction(new EventHandler<ActionEvent>() {
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
                        new Logging("SEVERE",SimulatorView.class.getName(),ex.toString(),ex);
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
                            new Logging("SEVERE",SimulatorView.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        } catch (Exception ex) {
                            new Logging("SEVERE",SimulatorView.class.getName(),ex.toString(),ex);
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
                            new Logging("SEVERE",SimulatorView.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        } catch (Exception ex) {
                            new Logging("SEVERE",SimulatorView.class.getName(),ex.toString(),ex);
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
                            new Logging("SEVERE",SimulatorView.class.getName(),ex.toString(),ex);
                            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                        } catch (Exception ex) {
                            new Logging("SEVERE",SimulatorView.class.getName(),ex.toString(),ex);
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
                    btnRun.setDisable(true);
                    btnClear.setDisable(true);
                    
                    index = -1;
                    type.setValue("Designing S/W");
                    name.setText("");
                    path.setText("");
                }
            });
            
            deviceGP.add(btnSaveAs, 0, 5);
            deviceGP.add(btnSave, 1, 5);
            deviceGP.add(btnDelete, 2, 5);
            deviceGP.add(btnRun, 3, 5);
            deviceGP.add(btnClear, 4, 5);
            
            Separator sepHor2 = new Separator();
            sepHor2.setValignment(VPos.CENTER);
            GridPane.setConstraints(sepHor2, 0, 6);
            GridPane.setColumnSpan(sepHor2, 5);
            deviceGP.getChildren().add(sepHor2);
        
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
                            btnSaveAs.setDisable(false);
                            btnSave.setDisable(true);
                            btnDelete.setDisable(true);
                            btnRun.setDisable(false);
                            btnClear.setDisable(false);
                        }else{
                            btnSaveAs.setDisable(true);
                            btnSave.setDisable(true);
                            btnDelete.setDisable(true);
                            btnRun.setDisable(true);
                            btnClear.setDisable(false);
                        }
                    }
                }
            });        
            deviceGP.add(tsHB, 0, 7, 5, 1);
           
            Separator sepHor3 = new Separator();
            sepHor3.setValignment(VPos.CENTER);
            GridPane.setConstraints(sepHor3, 0, 8);
            GridPane.setColumnSpan(sepHor3, 5);
            deviceGP.getChildren().add(sepHor3);
        
            //System.err.print(objFabric.getLstYarn().size());
            TableView<Device> deviceTable = new TableView<Device>();
            deviceTable.setPrefWidth(objConfiguration.WIDTH/1.6);
            
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
                    
                    btnSaveAs.setDisable(true);
                    btnSave.setDisable(false);
                    btnDelete.setDisable(false);
                    btnRun.setDisable(false);
                    btnClear.setDisable(false);
                    oldRB.setSelected(true);
                    //System.out.println(t1.getThreadColor()+"selection "+index+" changed"+Color.web(t1.getThreadColor().substring(1))+":"+Color.valueOf(t1.getThreadColor().substring(1)));                        
                }
            });
        
            ScrollPane container = new ScrollPane();
            container.setPrefSize(objConfiguration.WIDTH/1.6,objConfiguration.HEIGHT/2);    
            container.setContent(deviceTable);
            GridPane.setConstraints(container, 0, 9);
            GridPane.setColumnSpan(container, 5);
            deviceGP.getChildren().add(container);
            
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
        
        Label caption = new Label(objDictionaryAction.getWord("APPLICATIONINTEGRATION")+" "+objDictionaryAction.getWord("UTILITY"));
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
        
        deviceStage.getIcons().add(new Image("/media/icon.png"));
        deviceStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWFABRICSETTINGS")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        //deviceStage.setIconified(true);
        deviceStage.setResizable(false);
        deviceStage.setScene(scene);
        deviceStage.setX(0);
        deviceStage.setY(0);
        deviceStage.show();
        deviceStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
                scene.getStylesheets().add(DeviceView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        deviceStage.close();
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
        new DeviceView(stage);
        new Logging("WARNING",DeviceView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static void main(String[] args) {   
        launch(args);    
    }    
}