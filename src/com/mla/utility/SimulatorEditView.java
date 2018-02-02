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
import com.mla.main.Configuration;
import com.mla.main.Logging;
import com.sun.media.jai.codec.ByteArraySeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.SepiaTone;
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
import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
/**
 * SimulatorEditView Class
 * <p>
 * This class is used for defining UI for artwork assignment in fabric editor.
 *
 * @author Amit Kumar Singh
 * @version %I%, %G%
 * @since   1.0
 * @date 07/01/2016
 * @Designing UI class for artwork assignment in fabric editor.
 * @see java.stage.*;
 * @link com.mla.fabric.FabricView
 */
public class SimulatorEditView {
 
    Simulator objSimulator;
    DictionaryAction objDictionaryAction;
    Configuration objConfiguration;
    
    BufferedImage bufferedImage;
    
    private Stage simulationStage;
    private BorderPane root;    
    private Scene scene;    
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;
    
    private ScrollPane container;
    private ImageView simulationIV;
    private TabPane editToolPane;
    private ScrollPane rightPane;
    
    Button btnApply;
    Button btnCancel;
    
    Label lblDimension;
    CheckBox outlineCB;
    CheckBox artworkCB;
    ComboBox weaveCB;
    ComboBox weftCB;
    ComboBox warpCB;
    
    /**
     * SimulatorEditView
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
     * @param       objConfigurationCall Object of Configuration Class
     */        
    public SimulatorEditView(Configuration objConfigurationCall, BufferedImage objBufferedImage) {   
        this.objConfiguration = objConfigurationCall;
        this.bufferedImage = objBufferedImage;
        objSimulator = new Simulator(null, null, null, null, 0, 0, 0, null, null, null, null);
        objSimulator.setObjConfiguration(objConfiguration);
        objSimulator.setStrCondition("");
        objSimulator.setStrSearchBy("");
        objSimulator.setStrDirection("Ascending");
        objSimulator.setStrOrderBy("Name");
 
        objDictionaryAction = new DictionaryAction(objConfiguration);

        simulationStage = new Stage();
        simulationStage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL
        simulationStage.initStyle(StageStyle.UTILITY);
        VBox parent =new VBox();
        root = new BorderPane();
        //root.setId("popup");
        scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(SimulatorEditView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        
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
        
        container = new ScrollPane();
        //container.setId("subpopup");
        //container.setPrefSize(objConfiguration.WIDTH,objConfiguration.HEIGHT*2/3);
        container.setPrefWidth(objConfiguration.WIDTH);
        //container.setPrefSize(root.getWidth(),root.getHeight());
        simulationIV = new ImageView();
        simulationIV.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        simulationIV.setPreserveRatio(true);
        simulationIV.setFitWidth(objConfiguration.WIDTH);
        container.setContent(simulationIV);
        root.setCenter(container);
        
        ScrollPane rightPane = new ScrollPane();
        rightPane.setPrefWidth(objConfiguration.WIDTH);
        //rightPane.setPrefSize(objConfiguration.WIDTH,objConfiguration.HEIGHT/3);
        editToolPane = new TabPane();
        editToolPane.setPrefWidth(objConfiguration.WIDTH);
        
        rightPane.setContent(editToolPane);
        root.setTop(rightPane);
        
        populateEditPane();
        
        simulationStage.setScene(scene);
        simulationStage.getIcons().add(new Image("/media/icon.png"));
        simulationStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWSIMULATION")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        simulationStage.setIconified(false);
        simulationStage.setResizable(false);        
        simulationStage.setX(-5);
        simulationStage.setY(0);
        simulationStage.showAndWait();
    } 
    
    public void plotSimulationView(){
        try {
            lblStatus.setText(objDictionaryAction.getWord("GETSIMULATION2DVIEW"));
            int intHeight = bufferedImage.getHeight();
            int intLength = bufferedImage.getWidth();
            String srcImageName=System.getProperty("user.dir")+"/mla/";  //fabric colored red_tshirt white-cloth green-fabric
            
            UtilityAction objUtilityAction = new UtilityAction();
            objUtilityAction.getBaseFabricSimultion(objSimulator);
            byte[] bytBaseThumbnil=objSimulator.getBytBaseFSIcon();
            SeekableStream stream = new ByteArraySeekableStream(bytBaseThumbnil);
            String[] names = ImageCodec.getDecoderNames(stream);
            ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
            RenderedImage im = dec.decodeAsRenderedImage();
            BufferedImage srcImage = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
            bytBaseThumbnil=null;
            
            // resize image by tilling
            BufferedImage myImage = new BufferedImage((int)(intLength), (int)(intHeight),BufferedImage.TYPE_INT_RGB);
            /*
            Graphics2D g = myImage.createGraphics();
            g.drawImage(srcImage, 0, 0, (int)(intLength), (int)(intHeight), null);
            g.dispose();
            srcImage = null;
            */            
            for(int i = 0; i < intHeight; i++) {
                for(int j = 0; j < intLength; j++) {
                    if(i>=srcImage.getHeight() && j<srcImage.getWidth()){
                        myImage.setRGB(j, i, srcImage.getRGB(j, i%srcImage.getHeight()));
                    }else if(i<srcImage.getHeight() && j>=srcImage.getWidth()){
                        myImage.setRGB(j, i, srcImage.getRGB(j%srcImage.getWidth(), i));
                    }else if(i>=srcImage.getHeight() && j>=srcImage.getWidth()){
                        myImage.setRGB(j, i, srcImage.getRGB(j%srcImage.getWidth(), i%srcImage.getHeight()));
                    }else{
                        myImage.setRGB(j, i, srcImage.getRGB(j, i));
                    }
                }
            }
            srcImage = null;
            
            BufferedImage simulationImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            int rgb = 0;
            for(int x = 0; x < intHeight; x++) {
                for(int y = 0; y < intLength; y++) {
                    int pixel = myImage.getRGB(y, x);
                    int alpha = (pixel >> 24) & 0xff;
                    int red   = (pixel >>16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue  =  pixel & 0xff;
                    //if(red!=255 || green!=255 || blue!=255){
                    // Convert RGB to HSB
                    float[] hsb = java.awt.Color.RGBtoHSB(red, green, blue, null);
                    float hue = hsb[0];
                    float saturation = hsb[1];
                    float brightness = hsb[2];
                    hsb = null;

                    int npixel = bufferedImage.getRGB(y, x);
                    int nalpha = (npixel >> 24) & 0xff;
                    int nred   = (npixel >>16) & 0xff;
                    int ngreen = (npixel >> 8) & 0xff;
                    int nblue  =  npixel & 0xff;

                    hsb = java.awt.Color.RGBtoHSB(nred, ngreen, nblue, null);

                    hue = hsb[0];
                    saturation = hsb[1];
                    // Convert HSB to RGB value
                    rgb = java.awt.Color.HSBtoRGB(hue, saturation, brightness);
                    //}else{
                    //  rgb = pixel;
                    //}
                    simulationImage.setRGB(y, x, rgb);
                    /*
                    int pixels = simulationImage.getRGB(y,x);
                    pixels = (pixels & 0xff) >>> 6;
                    simulationImage.setRGB(y, x, pixels);
                     */
                }
            }
            
            ImageIO.write(simulationImage, "png", new File(System.getProperty("user.dir")+"/mla/simulation.png"));

            simulationIV.setImage(SwingFXUtils.toFXImage(simulationImage, null));
            simulationImage=null;
            lblStatus.setText(objDictionaryAction.getWord("GOTSIMULATION2DVIEW"));                
            
        } catch (SQLException ex) {
            Logger.getLogger(SimulatorEditView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            new Logging("SEVERE",SimulatorEditView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        } catch (Exception ex) {
            new Logging("SEVERE",SimulatorEditView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }     
    }
    
    
    /**
     * colorPanel
     * <p>
     * This method is used for creating UX for color panel.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for creating UX for color panel.
     * @link        com.mla.fabric.Fabric
     * @link        com.mla.main.Logging
     */
    public void populateEditPane(){
        editToolPane.setVisible(true);
        System.gc();
        
        Tab settingsTab = new Tab();
        Tab propertiesTab = new Tab();
        Tab colorTab = new Tab();
        Tab lightTab = new Tab();
        
        settingsTab.setClosable(false);
        propertiesTab.setClosable(false);
        colorTab.setClosable(false);
        lightTab.setClosable(false);
        
        settingsTab.setText(objDictionaryAction.getWord("Settings"));
        propertiesTab.setText(objDictionaryAction.getWord("PROPERTIESADJUST"));
        colorTab.setText(objDictionaryAction.getWord("COLORADJUST"));
        lightTab.setText(objDictionaryAction.getWord("Light Effect"));
        
        settingsTab.setTooltip(new Tooltip(objDictionaryAction.getWord("Settings")));
        propertiesTab.setTooltip(new Tooltip(objDictionaryAction.getWord("PROPERTIESADJUST")));
        colorTab.setTooltip(new Tooltip(objDictionaryAction.getWord("COLORADJUST")));
        lightTab.setTooltip(new Tooltip(objDictionaryAction.getWord("Light Effect")));
        
        GridPane settingsGP = new GridPane();
        GridPane propertiesGP = new GridPane();
        GridPane colorGP = new GridPane();
        GridPane lightGP = new GridPane();
        
        //settingsGP.setId("container");
        //propertiesGP.setId("container");
        //colorGP.setId("container");
        //lightGP.setId("container");
        
        settingsGP.setId("subpopup");
        propertiesGP.setId("subpopup");
        colorGP.setId("subpopup");
        lightGP.setId("subpopup");
        
        settingsGP.setVgap(10);
        propertiesGP.setVgap(10);
        colorGP.setVgap(10);
        lightGP.setVgap(10);
        
        settingsGP.setHgap(10);
        propertiesGP.setHgap(10);
        colorGP.setHgap(10);
        lightGP.setHgap(10);
        
        //settingsGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //propertiesGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //colorGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //lightGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        
        //settingsGP.setAlignment(Pos.TOP_CENTER);
        //propertiesGP.setAlignment(Pos.TOP_CENTER);
        //colorGP.setAlignment(Pos.TOP_CENTER);
        //lightGP.setAlignment(Pos.TOP_CENTER);
        
        settingsTab.setContent(settingsGP);
        propertiesTab.setContent(propertiesGP);
        colorTab.setContent(colorGP);
        lightTab.setContent(lightGP);
        
        editToolPane.getTabs().add(settingsTab);
        editToolPane.getTabs().add(propertiesTab);
        editToolPane.getTabs().add(colorTab);
        editToolPane.getTabs().add(lightTab);
        
        
        Label baseDefault= new Label(objDictionaryAction.getWord("DEFAULTBASEFABRIC")+" :");
        //baseDefault.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        baseDefault.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDEFAULTBASEFABRIC")));
        final Hyperlink baseDefaultHL = new Hyperlink();
        baseDefaultHL.setText(objDictionaryAction.getWord("CHANGEDEFAULT"));
        baseDefaultHL.setGraphic(new ImageView(objSimulator.getObjConfiguration().getStrColour()+"/browse.png"));
        baseDefaultHL.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDEFAULTBASEFABRIC")));
        final Label baseDefaultTF = new Label();
        baseDefaultTF.setText("BASEFSG1");
        baseDefaultTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDEFAULTBASEFABRIC")));
        settingsGP.add(baseDefault, 0, 0);
        settingsGP.add(baseDefaultHL, 1, 0);    
        settingsGP.add(baseDefaultTF, 2, 0);   
        baseDefaultHL.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Simulator objSimulatorNew = new Simulator(null, null, null, null, 0, 0, 0, null, null, null, null);
                    objSimulatorNew.setObjConfiguration(objSimulator.getObjConfiguration());
                    objSimulatorNew.setStrCondition("");
                    objSimulatorNew.setStrSearchBy("");
                    objSimulatorNew.setStrDirection("Ascending");
                    objSimulatorNew.setStrOrderBy("Name");
            
                    //fabricTypeCB.getItems().addAll("default","cotton","nylon","silk","woolen");
                    SimulatorImportView objSimulatorImportView = new SimulatorImportView(objSimulatorNew);
                    
                    if(objSimulatorNew.getStrBaseFSID()!=null){
                        baseDefaultTF.setText(objSimulatorNew.getStrBaseFSID());
                        objSimulatorNew = null;
                        objSimulator.setStrBaseFSID(baseDefaultTF.getText());
                        plotSimulationView();
                        System.gc();
                    }
                } catch (Exception ex) {
                    new Logging("SEVERE",SimulatorEditView.class.getName(),ex.toString(),ex);
                }
            }
        });
        
        Label lblZoom= new Label(objDictionaryAction.getWord("ZOOMLEVEL")+" :");
        //lblZoom.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblZoom.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPZOOMLEVEL")));
        final Slider zoomSlider = new Slider(0.1, 10, 1);    
        zoomSlider.setShowTickLabels(true);
        zoomSlider.setShowTickMarks(true);
        zoomSlider.setBlockIncrement(0.1);
        zoomSlider.setMajorTickUnit(5);
        zoomSlider.setMinorTickCount(1);
        final Label zoomTF = new Label();
        zoomTF.setText(Double.toString(zoomSlider.getValue()));
        zoomTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPZOOMLEVEL")));
        settingsGP.add(lblZoom, 3, 0);
        settingsGP.add(zoomSlider, 4, 0);    
        settingsGP.add(zoomTF, 5, 0);   
        zoomSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                zoomTF.setText(String.format("%.2f", new_val));
                simulationIV.setPreserveRatio(true);
                simulationIV.setSmooth(true);
                simulationIV.setScaleX(new_val.doubleValue());
                simulationIV.setScaleY(new_val.doubleValue());
                simulationIV.setScaleZ(new_val.doubleValue());
            }
        });
        /*
        Label lblTransparent= new Label(objDictionaryAction.getWord("TRASPARENT")+" :");
        //lblTransparent.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblTransparent.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTRASPARENT")));
        final Slider transparentSlider = new Slider(0, 1, 1);    
        transparentSlider.setShowTickLabels(true);
        transparentSlider.setShowTickMarks(true);
        transparentSlider.setBlockIncrement(0.1);
        final Label transparentTF = new Label();
        transparentTF.setText(Double.toString(transparentSlider.getValue()));
        transparentTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPTRASPARENT")));
        settingsGP.add(lblTransparent, 6, 0);
        settingsGP.add(transparentSlider, 7, 0);    
        settingsGP.add(transparentTF, 8, 0);   
        transparentSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                try {
                    transparentTF.setText(String.format("%.2f", new_val));
                    BufferedImage texture = SwingFXUtils.fromFXImage(simulationIV.getImage(), null);
                    ArtworkAction objArtworkAction = new ArtworkAction();
                    texture = objArtworkAction.getImageTransparent(texture,new_val.floatValue());
                    simulationIV.setImage(SwingFXUtils.toFXImage(texture, null));
                } catch (SQLException ex) {
                    new Logging("SEVERE",SimulatorEditView.class.getName(),ex.toString(),ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
            
        Label lblErosion= new Label(objDictionaryAction.getWord("EROSION")+" :");
        //lblErosion.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblErosion.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEROSION")));
        final Slider erosionSlider = new Slider(1, 10, 1);    
        erosionSlider.setShowTickLabels(true);
        erosionSlider.setShowTickMarks(true);
        erosionSlider.setBlockIncrement(1);
        final Label erosionTF = new Label();
        erosionTF.setText(Double.toString(erosionSlider.getValue()));
        erosionTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEROSION")));
        settingsGP.add(lblErosion, 9, 0);
        settingsGP.add(erosionSlider, 10, 0);    
        settingsGP.add(erosionTF, 11, 0);   
        erosionSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                erosionTF.setText(String.format("%.2f", new_val));
                int erosion_size = new_val.intValue();
                
                //System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
                Mat source = bufferedImageToMat(SwingFXUtils.fromFXImage(simulationIV.getImage(), null));
                Mat destination = new Mat(source.rows(),source.cols(),source.type());
                destination = source;
         
                Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(2*erosion_size + 1, 2*erosion_size+1));
                Imgproc.erode(source, destination, element);
                Highgui.imwrite("erosion.jpg", destination);
                
                simulationIV.setImage(SwingFXUtils.toFXImage(MatToBufferedImage(destination), null));
            }
        });
        Label lblZoom= new Label(objDictionaryAction.getWord("ZOOM")+" :");
        //lblZoom.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblZoom.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPZOOM")));
        final Slider zoomSlider = new Slider(0.1, 10, 1);    
        zoomSlider.setShowTickLabels(true);
        zoomSlider.setShowTickMarks(true);
        zoomSlider.setBlockIncrement(0.1);
        final Label zoomTF = new Label();
        zoomTF.setText(Double.toString(zoomSlider.getValue()));
        zoomTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPZOOM")));
        settingsGP.add(lblZoom, 3, 0);
        settingsGP.add(zoomSlider, 4, 0);    
        settingsGP.add(zoomTF, 5, 0);   
        zoomSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                erosionTF.setText(String.format("%.2f", new_val));
                int dilation_size = new_val.intValue();
                
                //System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
                Mat source = bufferedImageToMat(SwingFXUtils.fromFXImage(simulationIV.getImage(), null));
                Mat destination = new Mat(source.rows(),source.cols(),source.type());
                destination = source;
                
                Mat element1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(2*dilation_size + 1, 2*dilation_size+1));
                Imgproc.dilate(source, destination, element1);
                Highgui.imwrite("dilation.jpg", destination);
                
                simulationIV.setImage(SwingFXUtils.toFXImage(MatToBufferedImage(destination), null));
            }
        });
        */    
        
        Label lblOpacity= new Label(objDictionaryAction.getWord("OPACITY")+" :");
        //lblOpacity.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblOpacity.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOPACITY")));
        final Slider opacitySlider = new Slider(0, 1, 1);    
        opacitySlider.setShowTickLabels(true);
        opacitySlider.setShowTickMarks(true);
        opacitySlider.setBlockIncrement(0.1);
        opacitySlider.setMajorTickUnit(0.5);
        //opacitySlider.setMinorTickCount(0.1);
        final Label opacityTF = new Label();
        opacityTF.setText(Double.toString(opacitySlider.getValue()));
        opacityTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPOPACITY")));
        propertiesGP.add(lblOpacity, 0, 0);
        propertiesGP.add(opacitySlider, 1, 0);    
        propertiesGP.add(opacityTF, 2, 0);   
        propertiesGP.setOpacity(1);
        opacitySlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    opacityTF.setText(String.format("%.2f", new_val));
                    simulationIV.setOpacity(new_val.doubleValue());
            }
        });
        
        final SepiaTone sepiaEffect = new SepiaTone();
        Label lblsepiaTone= new Label(objDictionaryAction.getWord("SEPIATONE")+" :");
        //lblsepiaTone.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblsepiaTone.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSEPIATONE")));
        final Slider sepiaToneSlider = new Slider(0, 1, 0);    
        sepiaToneSlider.setShowTickLabels(true);
        sepiaToneSlider.setShowTickMarks(true);
        sepiaToneSlider.setBlockIncrement(0.1);
        sepiaToneSlider.setMajorTickUnit(0.5);
        //sepiaToneSlider.setMinorTickCount(0.1);
        final Label sepiaToneTF = new Label();
        sepiaToneTF.setText(Double.toString(sepiaToneSlider.getValue()));
        sepiaToneTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSEPIATONE")));
        propertiesGP.add(lblsepiaTone, 3, 0);
        propertiesGP.add(sepiaToneSlider, 4, 0);    
        propertiesGP.add(sepiaToneTF, 5, 0);   
        sepiaEffect.setLevel(0);
        sepiaToneSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    sepiaToneTF.setText(String.format("%.2f", new_val));
                    sepiaEffect.setLevel(new_val.doubleValue());
            }
        });
        
        final Bloom bloomEffect = new Bloom();
        Label lblBloom= new Label(objDictionaryAction.getWord("BLOOM")+" :");
        //lblBloom.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblBloom.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBLOOM")));
        final Slider bloomSlider = new Slider(0, 1, 1);    //0.3
        bloomSlider.setShowTickLabels(true);
        bloomSlider.setShowTickMarks(true);
        bloomSlider.setBlockIncrement(0.1);
        bloomSlider.setMajorTickUnit(0.5);
        //bloomSlider.setMinorTickCount(0.1);
        final Label bloomTF = new Label();
        bloomTF.setText(Double.toString(bloomSlider.getValue()));
        bloomTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBLOOM")));
        propertiesGP.add(lblBloom, 6, 0);
        propertiesGP.add(bloomSlider, 7, 0);    
        propertiesGP.add(bloomTF, 8, 0);   
        bloomEffect.setThreshold(1);
        bloomSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    bloomTF.setText(String.format("%.2f", new_val));
                    bloomEffect.setThreshold(new_val.doubleValue());
            }
        });
        
        final GaussianBlur gaussianBlur = new GaussianBlur();
        Label lblGaussianBlur= new Label(objDictionaryAction.getWord("GAUSSIANBLUR")+" :");
        //lblGaussianBlur.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblGaussianBlur.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPGAUSSIANBLUR")));
        final Slider gaussianBlurSlider = new Slider(0, 63, 0); //10    
        gaussianBlurSlider.setShowTickLabels(true);
        gaussianBlurSlider.setShowTickMarks(true);
        gaussianBlurSlider.setMajorTickUnit(10);
        gaussianBlurSlider.setMinorTickCount(5);
        gaussianBlurSlider.setBlockIncrement(1);
        final Label gaussianBlurTF = new Label();
        gaussianBlurTF.setText(Double.toString(gaussianBlurSlider.getValue()));
        gaussianBlurTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPGAUSSIANBLUR")));
        propertiesGP.add(lblGaussianBlur, 9, 0);
        propertiesGP.add(gaussianBlurSlider, 10, 0);    
        propertiesGP.add(gaussianBlurTF, 11, 0);   
        gaussianBlur.setRadius(0);
        gaussianBlurSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    gaussianBlurTF.setText(Integer.toString(new_val.intValue()));
                    gaussianBlur.setRadius(new_val.intValue());
            }
        });
        
        
        final ColorAdjust colorAdjust = new ColorAdjust();
        
        Label lblColorBrightness= new Label(objDictionaryAction.getWord("BRIGHTNESS")+" :");
        //lblColorBrightness.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblColorBrightness.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBRIGHTNESS")));
        final Slider colorBrightnessSlider = new Slider(-1.0, 1.0, 0.0);    
        colorBrightnessSlider.setShowTickLabels(true);
        colorBrightnessSlider.setShowTickMarks(true);
        colorBrightnessSlider.setBlockIncrement(0.1);
        colorBrightnessSlider.setMajorTickUnit(0.5);
        //colorBrightnessSlider.setMinorTickCount(0.5);
        final Label colorBrightnessTF = new Label();
        colorBrightnessTF.setText(Double.toString(colorBrightnessSlider.getValue()));
        colorBrightnessTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPBRIGHTNESS")));
        colorGP.add(lblColorBrightness, 0, 0);
        colorGP.add(colorBrightnessSlider, 1, 0);    
        colorGP.add(colorBrightnessTF, 2, 0);   
        colorBrightnessSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    colorBrightnessTF.setText(String.format("%.2f", new_val));
                    colorAdjust.setBrightness(new_val.doubleValue());
            }
        });
        
        Label lblColorContrast= new Label(objDictionaryAction.getWord("CONTRAST")+" :");
        //lblColorContrast.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblColorContrast.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCONTRAST")));
        final Slider colorContrastSlider = new Slider(-1.0, 1.0, 0.0);    
        colorContrastSlider.setShowTickLabels(true);
        colorContrastSlider.setShowTickMarks(true);
        colorContrastSlider.setBlockIncrement(0.1);
        colorContrastSlider.setMajorTickUnit(0.5);
        //colorContrastSlider.setMinorTickCount(0.1);
        final Label colorContrastTF = new Label();
        colorContrastTF.setText(Double.toString(colorContrastSlider.getValue()));
        colorContrastTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCONTRAST")));
        colorGP.add(lblColorContrast, 3, 0);
        colorGP.add(colorContrastSlider, 4, 0);    
        colorGP.add(colorContrastTF, 5, 0);   
        colorContrastSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    colorContrastTF.setText(String.format("%.2f", new_val));
                    colorAdjust.setContrast(new_val.doubleValue());
            }
        });
        
        Label lblColorHue= new Label(objDictionaryAction.getWord("HUE")+" :");
        //lblColorHue.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblColorHue.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPHUE")));
        final Slider colorHueSlider = new Slider(-1.0, 1.0, 0.0);    
        colorHueSlider.setShowTickLabels(true);
        colorHueSlider.setShowTickMarks(true);
        colorHueSlider.setBlockIncrement(0.1);
        colorHueSlider.setMajorTickUnit(0.5);
        //colorHueSlider.setMinorTickCount(0.1);
        final Label colorHueTF = new Label();
        colorHueTF.setText(Double.toString(colorHueSlider.getValue()));
        colorHueTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPHUE")));
        colorGP.add(lblColorHue, 6, 0);
        colorGP.add(colorHueSlider, 7, 0);    
        colorGP.add(colorHueTF, 8, 0);   
        colorHueSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    colorHueTF.setText(String.format("%.2f", new_val));
                    colorAdjust.setHue(new_val.doubleValue());
            }
        });
        
        Label lblColorSaturation= new Label(objDictionaryAction.getWord("SATURATION")+" :");
        //lblColorSaturation.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblColorSaturation.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSATURATION")));
        final Slider colorSaturationSlider = new Slider(-1.0, 1.0, 0.0);    
        colorSaturationSlider.setShowTickLabels(true);
        colorSaturationSlider.setShowTickMarks(true);
        colorSaturationSlider.setBlockIncrement(0.1);
        colorSaturationSlider.setMajorTickUnit(0.5);
        //colorSaturationSlider.setMinorTickCount(0.1);
        final Label colorSaturationTF = new Label();
        colorSaturationTF.setText(Double.toString(colorSaturationSlider.getValue()));
        colorSaturationTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSATURATION")));
        colorGP.add(lblColorSaturation, 9, 0);
        colorGP.add(colorSaturationSlider, 10, 0);    
        colorGP.add(colorSaturationTF, 11, 0);   
        colorSaturationSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    colorSaturationTF.setText(String.format("%.2f", new_val));
                    colorAdjust.setSaturation(new_val.doubleValue());
            }
        });

        
        
        final Light.Distant light = new Light.Distant();
        /*
        light.setAzimuth(-135.0); //45
        light.setElevation //45
        */
        final Lighting lighting = new Lighting();
        //lighting.setLight(light);
        
        Label lblLightSurfaceScale= new Label(objDictionaryAction.getWord("SURFACESCALE")+" :");
        //lblLightSurfaceScale.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblLightSurfaceScale.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSURFACESCALE")));
        final Slider lightSurfaceScaleSlider = new Slider(0, 10, 0);    //1.5
        lightSurfaceScaleSlider.setShowTickLabels(true);
        lightSurfaceScaleSlider.setShowTickMarks(true);
        lightSurfaceScaleSlider.setMajorTickUnit(5);
        lightSurfaceScaleSlider.setMinorTickCount(1);
        lightSurfaceScaleSlider.setBlockIncrement(0.5);
        final Label lightSurfaceScaleTF = new Label();
        lightSurfaceScaleTF.setText(Double.toString(lightSurfaceScaleSlider.getValue()));
        lightSurfaceScaleTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSURFACESCALE")));
        lightGP.add(lblLightSurfaceScale, 0, 0);
        lightGP.add(lightSurfaceScaleSlider, 1, 0);    
        lightGP.add(lightSurfaceScaleTF, 2, 0);   
        lighting.setSurfaceScale(0);
        lightSurfaceScaleSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    lightSurfaceScaleTF.setText(String.format("%.2f", new_val));
                    lighting.setSurfaceScale(new_val.doubleValue());
            }
        });
        
        Label lblLightSpecularExponent= new Label(objDictionaryAction.getWord("SpecularExponent")+" :");
        //lblLightSpecularExponent.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblLightSpecularExponent.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSPECULAREXPONENT")));
        final Slider lightSpecularExponentSlider = new Slider(0, 40, 40);    //20
        lightSpecularExponentSlider.setShowTickLabels(true);
        lightSpecularExponentSlider.setShowTickMarks(true);
        lightSpecularExponentSlider.setMajorTickUnit(10);
        lightSpecularExponentSlider.setMinorTickCount(5);
        lightSpecularExponentSlider.setBlockIncrement(1);
        final Label lightSpecularExponentTF = new Label();
        lightSpecularExponentTF.setText(Double.toString(lightSpecularExponentSlider.getValue()));
        lightSpecularExponentTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSPECULAREXPONENT")));
        lightGP.add(lblLightSpecularExponent, 3, 0);
        lightGP.add(lightSpecularExponentSlider, 4, 0);    
        lightGP.add(lightSpecularExponentTF, 5, 0);   
        lighting.setSpecularExponent(40);
        lightSpecularExponentSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    lightSpecularExponentTF.setText(String.format("%.2f", new_val));
                    lighting.setSpecularExponent(new_val.doubleValue());
            }
        });
        
        Label lblLightSpecularConstant= new Label(objDictionaryAction.getWord("SPECULARCONSTANT")+" :");
        //lblLightSpecularConstant.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblLightSpecularConstant.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSPECULARCONSTANT")));
        final Slider lightSpecularConstantSlider = new Slider(0, 2, 0);    //0.3
        lightSpecularConstantSlider.setShowTickLabels(true);
        lightSpecularConstantSlider.setShowTickMarks(true);
        lightSpecularConstantSlider.setMajorTickUnit(1);
        //lightSpecularConstantSlider.setMinorTickCount(0.5);
        lightSpecularConstantSlider.setBlockIncrement(0.1);
        final Label lightSpecularConstantTF = new Label();
        lightSpecularConstantTF.setText(Double.toString(lightSpecularConstantSlider.getValue()));
        lightSpecularConstantTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSPECULARCONSTANT")));
        lightGP.add(lblLightSpecularConstant, 6, 0);
        lightGP.add(lightSpecularConstantSlider, 7, 0);    
        lightGP.add(lightSpecularConstantTF, 8, 0);   
        lighting.setSpecularConstant(0);
        lightSpecularConstantSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    lightSpecularConstantTF.setText(String.format("%.2f", new_val));
                    lighting.setSpecularConstant(new_val.doubleValue());
            }
        });
        
        Label lblLightDiffuseConstant= new Label(objDictionaryAction.getWord("DIFFUSECONSTANT")+" :");
        //lblLightDiffuseConstant.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblLightDiffuseConstant.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDIFFUSECONSTANT")));
        final Slider lightDiffuseConstantSlider = new Slider(0, 2, 2);    //1
        lightDiffuseConstantSlider.setShowTickLabels(true);
        lightDiffuseConstantSlider.setShowTickMarks(true);
        lightDiffuseConstantSlider.setBlockIncrement(0.1);
        lightDiffuseConstantSlider.setMajorTickUnit(1);
        //lightDiffuseConstantSlider.setMinorTickCount(0.5);
        final Label lightDiffuseConstantTF = new Label();
        lightDiffuseConstantTF.setText(Double.toString(lightDiffuseConstantSlider.getValue()));
        lightDiffuseConstantTF.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPDIFFUSECONSTANT")));
        lightGP.add(lblLightDiffuseConstant, 9, 0);
        lightGP.add(lightDiffuseConstantSlider, 10, 0);    
        lightGP.add(lightDiffuseConstantTF, 11, 0);   
        lighting.setDiffuseConstant(2);
        lightDiffuseConstantSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    lightDiffuseConstantTF.setText(String.format("%.2f", new_val));
                    lighting.setDiffuseConstant(new_val.doubleValue());
            }
        });
        
        colorAdjust.setInput(lighting);
        gaussianBlur.setInput(colorAdjust);
        bloomEffect.setInput(gaussianBlur);
        sepiaEffect.setInput(bloomEffect);
        /*
        simulationIV.setEffect(gaussianBlur);
        simulationIV.setEffect(bloomEffect);
        simulationIV.setEffect(sepiaEffect);
        simulationIV.setEffect(colorAdjust);
        simulationIV.setEffect(lighting);
        */
        simulationIV.setEffect(sepiaEffect);
    }
    
    public static Mat bufferedImageToMat(BufferedImage bufferImage) {
        Mat mat = new Mat(bufferImage.getHeight(), bufferImage.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) bufferImage.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
    }
    
    public static BufferedImage MatToBufferedImage(Mat mat) {
        BufferedImage bufferImage = null;
        if ( mat != null ) { 
            int cols = mat.cols();  
            int rows = mat.rows();  
            int elemSize = (int)mat.elemSize();  
            byte[] data = new byte[cols * rows * elemSize];          
            int type;  
            mat.get(0, 0, data);  
            switch (mat.channels()) {  
            case 1:  
                type = BufferedImage.TYPE_BYTE_GRAY;  
                break;  
            case 3:  
                type = BufferedImage.TYPE_3BYTE_BGR;  
                // bgr to rgb  
                byte b;  
                for(int i=0; i<data.length; i=i+3) {  
                    b = data[i];  
                    data[i] = data[i+2];  
                    data[i+2] = b;  
                }  
                break;  
            default:  
                return null;  
            }  

            bufferImage = new BufferedImage(cols, rows, type);
            bufferImage.getRaster().setDataElements(0, 0, cols, rows, data);
        } else { // mat was null
            bufferImage = null;
        }
        return bufferImage;
    }
}
