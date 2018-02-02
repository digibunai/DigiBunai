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

import com.mla.dictionary.DictionaryAction;
import com.mla.fabric.FabricView;
import com.mla.main.IDGenerator;
import com.mla.main.Logging;
import com.mla.secure.Security;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author Amit Kumar Singh
 */
public class WeaveEditView {
    
    Weave objWeave;
    WeaveAction objWeaveAction;
    DictionaryAction objDictionaryAction;
    
    private Stage weaveStage;
    private BorderPane root;    
    private Scene scene;    
    private Label lblStatus;
    private ProgressBar progressB;
    private ProgressIndicator progressI;
    private ScrollPane weaveSP;
    private GridPane weaveGP;

    boolean isNew = true;
    int boxSize = 15;
    /**
     * WeaveEditView
     * <p>
     * This constructor is used for UX initialization.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   constructor is used for UX initialization.
     * @see         javafx.stage.*;
     * @link        com.mla.fabric.FabricView
     * @throws      SQLException
     * @param       objFabricCall Object of Fabric Class
     */        
    public WeaveEditView(Weave objWeaveCall) {   
        this.objWeave = objWeaveCall;
        objDictionaryAction = new DictionaryAction(objWeave.getObjConfiguration());

        weaveStage = new Stage();
        weaveStage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL
        weaveStage.initStyle(StageStyle.UTILITY);
        VBox parent =new VBox();
        root = new BorderPane();
        root.setId("popup");
        scene = new Scene(root, objWeave.getObjConfiguration().WIDTH/1.5, objWeave.getObjConfiguration().HEIGHT/1.5, Color.WHITE);
        scene.getStylesheets().add(WeaveEditView.class.getResource(objWeave.getObjConfiguration().getStrTemplate()+"/style.css").toExternalForm());
        
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
        
        weaveSP = new ScrollPane();
        weaveSP.setId("subpopup");
        weaveSP.setPrefSize(root.getWidth(),root.getHeight());
        
        weaveGP = new GridPane();
        weaveGP.setAlignment(Pos.CENTER);
        weaveGP.setHgap(0);
        weaveGP.setVgap(0);
        weaveSP.setContent(weaveGP);
        
        //Adding Buttons
        GridPane editToolPane = new GridPane();
        editToolPane.autosize();
        editToolPane.setId("leftPane");
        
        Button browseTP = new Button();
        Button importTP = new Button();
        Button saveTP = new Button();
        Button cancelTP = new Button();
        Button clearTP = new Button();
        Button inversionTP = new Button();
        Button mirrorVerticalTP = new Button();
        Button mirrorHorizontalTP = new Button();
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
        
        browseTP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/browse.png"));
        importTP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/import.png"));
        saveTP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/save.png"));
        cancelTP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/close.png"));
        clearTP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/clear.png"));
        inversionTP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/switch_side.png"));
        mirrorVerticalTP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/vertical_mirror.png"));
        mirrorHorizontalTP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/horizontal_mirror.png"));
        rotateTP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/rotate_90.png"));
        rotateAntiTP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/rotate_anti_90.png"));
        moveRightTP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/move_right.png"));
        moveLeftTP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/move_left.png"));
        moveUpTP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/move_up.png"));
        moveDownTP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/move_down.png"));
        moveRight8TP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/move_right_by_8.png"));
        moveLeft8TP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/move_left_by_8.png"));
        moveUp8TP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/move_up_by_8.png"));
        moveDown8TP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/move_down_by_8.png"));
        tiltRightTP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/tilt_right.png"));
        tiltLeftTP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/tilt_left.png"));
        tiltUpTP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/tilt_up.png"));
        tiltDownTP.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/tilt_down.png"));
                
        browseTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBROWSE")));
        importTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPIMPORT")));
        saveTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSAVE")));
        cancelTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
        clearTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCLEAR")));
        inversionTP.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPINVERSION")));
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
                
        browseTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        importTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        saveTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        cancelTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        clearTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        inversionTP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
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
        
        editToolPane.add(browseTP, 0, 0);
        editToolPane.add(importTP, 1, 0);
        editToolPane.add(saveTP, 0, 1);
        editToolPane.add(cancelTP, 1, 1);
        editToolPane.add(clearTP, 0, 2);
        editToolPane.add(inversionTP, 1, 2);
        editToolPane.add(mirrorVerticalTP, 0, 3);
        editToolPane.add(mirrorHorizontalTP, 1, 3);
        editToolPane.add(rotateTP, 0, 4);
        editToolPane.add(rotateAntiTP, 1, 4);
        editToolPane.add(moveRightTP, 0, 5);
        editToolPane.add(moveLeftTP, 1, 5);
        editToolPane.add(moveUpTP, 0, 6);
        editToolPane.add(moveDownTP, 1, 6);
        editToolPane.add(moveRight8TP, 0, 7);
        editToolPane.add(moveLeft8TP, 1, 7);
        editToolPane.add(moveUp8TP, 0, 8);
        editToolPane.add(moveDown8TP, 1, 8);
        editToolPane.add(tiltRightTP, 0, 9);
        editToolPane.add(tiltLeftTP, 1, 9);
        editToolPane.add(tiltUpTP, 0, 10);
        editToolPane.add(tiltDownTP, 1, 10);
        
        browseTP.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                try {
                    weaveAction("Browse");
                } catch (SQLException ex) {
                    Logger.getLogger(WeaveEditView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        importTP.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                try {
                    weaveAction("Import");
                } catch (SQLException ex) {
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    weaveStage.setOpacity(1);
                    new Logging("SEVERE",WeaveEditView.class.getName(),ex.toString(),ex);
                }
            }
        });
        saveTP.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                try {                
                    weaveAction("Apply");
                } catch (SQLException ex) {
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    weaveStage.setOpacity(1);
                    new Logging("SEVERE",WeaveEditView.class.getName(),ex.toString(),ex);
                }              
            }
        });
        cancelTP.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                try {                
                    weaveAction("Cancel");
                } catch (SQLException ex) {
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    weaveStage.setOpacity(1);
                    new Logging("SEVERE",WeaveEditView.class.getName(),ex.toString(),ex);
                }               
            }
        });
        clearTP.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {                
                try {                       
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONCLEARWEAVE"));
                    objWeaveAction.clear(objWeave);
                    plotEditWeave();
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricView.class.getName(),"clear",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }               
            }
        });
        inversionTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONINVERSION"));
                    objWeaveAction.inversion(objWeave);
                    plotEditWeave();
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricView.class.getName(),"Inversion ",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        mirrorVerticalTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {                       
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONVERTICALMIRROR"));
                    objWeaveAction.mirrorVertical(objWeave);
                    plotEditWeave();
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricView.class.getName(),"Mirror Vertical",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });  
        mirrorHorizontalTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONHORIZENTALMIRROR"));
                    objWeaveAction.mirrorHorizontal(objWeave);
                    plotEditWeave();
                } catch (Exception ex) {
                  new Logging("SEVERE",FabricView.class.getName(),"Mirror Vertical",ex);
                  lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        }); 
        rotateTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONCLOCKROTATION"));
                    objWeaveAction.rotation(objWeave);
                    plotEditWeave();
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricView.class.getName(),"Rotate Clock Wise ",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        }); 
        rotateAntiTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONANTICLOCKROTATION"));
                    objWeaveAction.rotationAnti(objWeave);
                    plotEditWeave();
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricView.class.getName(),"Anti Rotate Clock Wise ",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        moveRightTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVERIGHT"));
                    objWeaveAction.moveRight(objWeave);
                    plotEditWeave();
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricView.class.getName(),"Move Right",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        moveLeftTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVELEFT"));
                    objWeaveAction.moveLeft(objWeave);
                    plotEditWeave();
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricView.class.getName(),"Move Left",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        moveUpTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVEUP"));
                    objWeaveAction.moveUp(objWeave);
                    plotEditWeave();
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricView.class.getName(),"Move Up",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        moveDownTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVEDOWN"));
                    objWeaveAction.moveDown(objWeave);
                    plotEditWeave();
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricView.class.getName(),"Move Down",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
         moveRight8TP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVERIGHT8"));
                    objWeaveAction.moveRight8(objWeave);
                    plotEditWeave();
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricView.class.getName(),"Move Right",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        moveLeft8TP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVELEFT8"));
                    objWeaveAction.moveLeft8(objWeave);
                    plotEditWeave();
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricView.class.getName(),"Move Left",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        moveUp8TP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVEUP8"));
                    objWeaveAction.moveUp8(objWeave);
                    plotEditWeave();
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricView.class.getName(),"Move Up",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        moveDown8TP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONMOVEDOWN8"));
                    objWeaveAction.moveDown8(objWeave);
                    plotEditWeave();
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricView.class.getName(),"Move Down",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        tiltRightTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONTILTRIGHT"));
                    objWeaveAction.tiltRight(objWeave);
                    plotEditWeave();
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricView.class.getName(),"Tilt Right ",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        tiltLeftTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONTILTLEFT"));
                    objWeaveAction.tiltLeft(objWeave);
                    plotEditWeave();
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricView.class.getName(),"Tilt Left ",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        tiltUpTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONTILTUP"));
                    objWeaveAction.tiltUp(objWeave);
                    plotEditWeave();
                } catch (Exception ex) {
                    new Logging("SEVERE",FabricView.class.getName(),"Tilt Up ",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        tiltDownTP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONTILTDOWN"));
                    objWeaveAction.tiltDown(objWeave);
                    plotEditWeave();
                } catch (Exception ex) {
                   new Logging("SEVERE",FabricView.class.getName(),"Tilt Down ",ex);
                   lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });         
        
        root.setRight(editToolPane);        
        root.setCenter(weaveSP);
        root.setBottom(footContainer);
        
        weaveStage.setScene(scene);
        weaveStage.getIcons().add(new Image("/media/icon.png"));
        weaveStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWWEAVEEDITOR")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        weaveStage.setIconified(false);
        weaveStage.setResizable(false);
        
        if(objWeave!=null && objWeave.getStrWeaveID()!=null){
            loadWeave();                    
            weaveStage.showAndWait();
        }else{
            newAction();
        }
    }
    
    /**
     * weaveAction
     * <p>
     * This method is used for execute action triggered by buttons.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for execute action triggered by buttons.
     * @link        com.mla.fabric.Fabric
     * @link        com.mla.main.Logging
     * @throws      SQLException
     * @exception   Exception
     * @param       actionName [String] name of the action
     */
    public void weaveAction(String actionName) throws SQLException {        
        if (actionName.equalsIgnoreCase("Browse")) {  
            lblStatus.setText(objDictionaryAction.getWord("ACTIONBROWSE"));
            weaveStage.setOpacity(0.8);
            Weave objWeaveNew = new Weave();
            objWeaveNew.setObjConfiguration(objWeave.getObjConfiguration());
            WeaveImportView objWeaveImportView= new WeaveImportView(objWeaveNew);
            if(objWeaveNew.getStrWeaveID()!=null){
                objWeave.setStrWeaveID(objWeaveNew.getStrWeaveID());
                objWeaveNew = null;
                System.gc();
                loadWeave();
            }else{
                lblStatus.setText(objDictionaryAction.getWord("NOITEM"));
            }
            weaveStage.setOpacity(1);
        }
        if (actionName.equalsIgnoreCase("Import")) { 
            lblStatus.setText(objDictionaryAction.getWord("ACTIONIMPORT"));
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
                        objWeave.setStrWeaveID(new IDGenerator().getIDGenerator("WEAVE_LIBRARY", objWeave.getObjConfiguration().getObjUser().getStrUserID()));
                        objWeave.setStrWeaveFile(content);
                        isNew = true;
                        initWeaveValue();
                    } else{
                        lblStatus.setText(objDictionaryAction.getWord("INVALIDWEAVE"));
                    }
                }else{
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
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
        if (actionName.equalsIgnoreCase("Apply")) {
            lblStatus.setText(objDictionaryAction.getWord("ACTIONAPPLY"));
            objWeaveAction.populateShaft(objWeave);
            objWeaveAction.populateTreadles(objWeave);
            objWeaveAction.populateTieUp(objWeave);
            objWeaveAction = new WeaveAction();
            if(objWeaveAction.isValidWeave(objWeave)){
                saveUpdateAction();
                System.gc();
                weaveStage.close();
            } else{
                lblStatus.setText(objDictionaryAction.getWord("INVALIDWEAVE"));
            }
        }       
        if (actionName.equalsIgnoreCase("Cancel")) {
            lblStatus.setText(objDictionaryAction.getWord("TOOLTIPACTIONCANCEL"));
            System.gc();
            weaveStage.close();
        }
        weaveStage.setOpacity(1);
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
            objWeave.getObjConfiguration().setStrRecentWeave(objWeave.getStrWeaveID());
            initWeaveValue();
        } catch (Exception ex) {
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
        }
    }
    
    public void initWeaveValue(){
        try {
            if(!objWeave.getStrWeaveFile().equals("") && objWeave.getStrWeaveFile()!=null){
                objWeaveAction = new WeaveAction();
                objWeaveAction.extractWeaveContent(objWeave);
                plotEditWeave();
            } else{
                lblStatus.setText(objDictionaryAction.getWord("ERROR"));
            }
            System.gc();
        } catch (SQLException ex) {
            new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }

    public void newAction(){
        final Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 300, 200, Color.WHITE);
        scene.getStylesheets().add(WeaveView.class.getResource(objWeave.getObjConfiguration().getStrTemplate()+"/style.css").toExternalForm());

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
        
        final CheckBox liftplan=new CheckBox("LiftPlan Mode");
        
        Button btnOK = new Button(objDictionaryAction.getWord("OK"));
        btnOK.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOK")));
        btnOK.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/skip.png"));
        btnOK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {  
                try {
                    int intWeft=Integer.parseInt(txtWeft.getText());
                    int intWarp = Integer.parseInt(txtWarp.getText());
                    int intShaft =Integer.parseInt(txtShaft.getText());
                    int intTreadles =Integer.parseInt(txtTreadles.getText());
                    if(intWeft>30 || intWarp>30 || intShaft>30 || intTreadles>30){
                        lblStatus.setText(objDictionaryAction.getWord("MAXVALUE"));
                    }else if(intWeft>0 && intWarp>0 && intShaft>0 && intTreadles>0){
                        objWeaveAction = new WeaveAction();
                        objWeave.setStrWeaveID(new IDGenerator().getIDGenerator("WEAVE_LIBRARY", objWeave.getObjConfiguration().getObjUser().getStrUserID()));

                        weaveGP.getChildren().clear();
                        objWeave.setShaftMatrix(new byte[intShaft][intWarp]);
                        objWeave.setTieupMatrix(new byte[intShaft][intTreadles]);
                        objWeave.setTreadlesMatrix(new byte[intWeft][intTreadles]);
                        objWeave.setDesignMatrix(new byte[intWeft][intWarp]);
                        
                        objWeave.setIntWeaveShaft(intShaft);
                        objWeave.setIntWeaveTreadles(intTreadles);
                        objWeave.setIntWarp(intWarp);
                        objWeave.setIntWeft(intWeft);
                        
                        //objWeave.setShaftRepeat(new String[intWeft]);
                        objWeave.setBytIsLiftPlan((byte)0);
                        
                        isNew = true;
                        plotEditWeave();
                        
                        dialogStage.close();
                        weaveStage.showAndWait();
                    }else{
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                        dialogStage.close();
                        weaveStage.close();                        
                    }
                } catch (Exception ex) {
                    new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                    dialogStage.close();
                    weaveStage.close();
                }
            }
        });
        popup.add(btnOK, 1, 4);
        root.setCenter(popup);
        dialogStage.setScene(scene);
        dialogStage.setTitle(objDictionaryAction.getWord("PROJECT")+":"+objDictionaryAction.getWord("WEAVINGDETAILS"));
        dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {                                
                dialogStage.close();
                weaveStage.close();
                //clothTypeCB.setValue(st);
                System.gc();
            }
        });
        dialogStage.showAndWait();
    }
    
    
    public void plotEditWeave(){
        try {
            weaveGP.getChildren().clear();
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
                    lblbox.setOnMouseClicked(new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent arg0) {
                            if(objWeave.getDesignMatrix()[p][q]==1){
                                lblbox.setStyle("-fx-background-color: #ffffff; -fx-border-width: 1;  -fx-border-color: black;");
                                lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                                objWeave.getDesignMatrix()[p][q]=0;
                            } else {
                                lblbox.setStyle("-fx-background-color: #000000;-fx-border-width: 1;  -fx-border-color: black");
                                lblbox.setFont(javafx.scene.text.Font.font("Arial", FontWeight.NORMAL, boxSize));
                                objWeave.getDesignMatrix()[p][q]=1;
                            }
                        }
                    });
                    weaveGP.add(lblbox,j,i);
                }    
            }
        } catch (SQLException ex) {
            new Logging("SEVERE",WeaveView.class.getName(),"Ploting weave Design",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }        
    }

    public void saveAction(){
        try {
            objWeaveAction = new WeaveAction();
            if(objWeaveAction.isValidWeave(objWeave)){
                objWeaveAction.singleWeaveContent(objWeave);
                objWeaveAction.injectWeaveContent(objWeave);
                objWeaveAction.getWeaveImage(objWeave);
                //objWeaveAction.getSingleWeaveImage(objWeave);
                objWeaveAction.resetWeave(objWeave);
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
            objWeave.setStrWeaveType("wif");
            objWeaveAction = new WeaveAction();
            objWeave.setStrWeaveID(new IDGenerator().getIDGenerator("WEAVE_LIBRARY", objWeave.getObjConfiguration().getObjUser().getStrUserID()));
            objWeaveAction.singleWeaveContent(objWeave);
            objWeaveAction.injectWeaveContent(objWeave);
            objWeaveAction.getWeaveImage(objWeave);
            //objWeaveAction.getSingleWeaveImage(objWeave);
            objWeaveAction.setWeave(objWeave);
            lblStatus.setText(objWeave.getStrWeaveName()+":"+objDictionaryAction.getWord("DATASAVED"));
            isNew=false;
        } catch (Exception ex) {
            Logger.getLogger(WeaveEditView.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",WeaveView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
      
    // Added 7 Mar 2017 -----------------------------
    public void saveUpdateAction(){
        // if fabric access is Public, we need not show dialog for Save
        if(!isNew && objWeave.getStrWeaveAccess().equalsIgnoreCase("Public")){
            saveAction();
        }
        else{
            objWeave.setStrWeaveAccess(objWeave.getStrWeaveAccess());
            final Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 350, 300, Color.WHITE);
            scene.getStylesheets().add(WeaveView.class.getResource(objWeave.getObjConfiguration().getStrTemplate()+"/style.css").toExternalForm());

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
            if(objWeave.getObjConfiguration().getObjUser().getUserAccess("WEAVE_LIBRARY").equalsIgnoreCase("Public"))
                weaveTG.selectToggle(weavePublicRB);
            else if(objWeave.getObjConfiguration().getObjUser().getUserAccess("WEAVE_LIBRARY").equalsIgnoreCase("Protected"))
                weaveTG.selectToggle(weaveProtectedRB);
            else
                weaveTG.selectToggle(weavePrivateRB);

            final PasswordField passPF= new PasswordField();
            final Label lblAlert = new Label();
            if(objWeave.getObjConfiguration().getBlnAuthenticateService()){
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
            
            Button btnOK = new Button(objDictionaryAction.getWord("OK"));
            btnOK.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOK")));
            btnOK.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/save.png"));
            btnOK.setDefaultButton(true);
            btnOK.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    objWeave.setStrWeaveAccess(weaveTG.getSelectedToggle().getUserData().toString());                                
                    if(objWeave.getObjConfiguration().getBlnAuthenticateService()){
                        if(passPF.getText().length()==0){
                            lblAlert.setText(objDictionaryAction.getWord("NOSERVICEPASSWORD"));
                        } else{
                            if(Security.SecurePassword(passPF.getText(), objWeave.getObjConfiguration().getObjUser().getStrUsername()).equals(objWeave.getObjConfiguration().getObjUser().getStrAppPassword())){   
                                if(isNew){
                                    objWeave.setStrWeaveName(txtName.getText());
                                    objWeave.setStrWeaveCategory(cbType.getValue().toString());
                                }
                                if(isNew)
                                    saveAsAction();
                                else
                                    saveAction();
                                dialogStage.close();
                            } else{
                                lblAlert.setText(objDictionaryAction.getWord("INVALIDSERVICEPASSWORD"));
                            }
                        }
                    } else{   // service password is disabled
                        if(isNew){
                            objWeave.setStrWeaveName(txtName.getText());
                            objWeave.setStrWeaveCategory(cbType.getValue().toString());
                        }
                        if(isNew)
                            saveAsAction();
                        else
                            saveAction();
                        dialogStage.close();
                    }
                }
            });
            popup.add(btnOK, 1, 4);
            Button btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
            btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
            btnCancel.setGraphic(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/close.png"));
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
}