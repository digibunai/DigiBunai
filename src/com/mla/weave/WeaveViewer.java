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
import com.mla.main.Logging;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
/**
 *
 * @Designing GUI window for user preferences
 * @author Amit Kumar Singh
 * 
 */
public class WeaveViewer extends Application {
    public static Stage weaveStage;
    BorderPane root;
    ImageView weave;    
    
    Weave objWeave = null;
    DictionaryAction objDictionaryAction = null;
    
    byte plotViewActionMode = 2; //1= composite, 2 = front, 3 = rear
 
    public WeaveViewer(final Stage primaryStage) {}
    
    public WeaveViewer(Weave objWeaveCall) {
        objWeave = objWeaveCall;
        objDictionaryAction = new DictionaryAction(objWeave.getObjConfiguration());

        weaveStage = new Stage(); 
        root = new BorderPane();
        Scene scene = new Scene(root, objWeave.getObjConfiguration().WIDTH, objWeave.getObjConfiguration().HEIGHT, Color.WHITE);
        scene.getStylesheets().add(WeaveViewer.class.getResource(objWeave.getObjConfiguration().getStrTemplate()+"/setting.css").toExternalForm());

        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(weaveStage.widthProperty());
       
        // Composite View item
        Menu compositeMenu  = new Menu();
        HBox compositeMenuHB = new HBox();
        compositeMenuHB.getChildren().addAll(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/composite_view.png"),new Label(objDictionaryAction.getWord("COMPOSITEVIEW")));
        compositeMenu.setGraphic(compositeMenuHB);
        final KeyCombination compositeMenuKC = new KeyCodeCombination(KeyCode.C,KeyCombination.CONTROL_DOWN);
        compositeMenuHB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                plotViewActionMode = 1; //1= composite, 2 = front, 3 = rear
                plotViewAction();
            }
        });
        // Front View item;
        Menu frontMenu  = new Menu();
        HBox frontMenuHB = new HBox();
        frontMenuHB.getChildren().addAll(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/front_side.png"),new Label(objDictionaryAction.getWord("VISULIZATIONVIEW")));
        frontMenu.setGraphic(frontMenuHB);
        final KeyCombination frontMenuKC = new KeyCodeCombination(KeyCode.F,KeyCombination.CONTROL_DOWN);
        frontMenuHB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                plotViewActionMode = 2; //1= composite, 2 = front, 3 = rear
                plotViewAction();
            }
        });
        // Switch Side View item;
        Menu rearMenu  = new Menu();
        HBox rearMenuHB = new HBox();
        rearMenuHB.getChildren().addAll(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/switch_side.png"),new Label(objDictionaryAction.getWord("SWITCHSIDEVIEW")));
        rearMenu.setGraphic(rearMenuHB);
        final KeyCombination rearMenuKC = new KeyCodeCombination(KeyCode.R,KeyCombination.CONTROL_DOWN);
        rearMenuHB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                plotViewActionMode = 3; //1= composite, 2 = front, 3 = rear
                plotViewAction();
            }
        });
        // Zoom Normal View menu
        Menu zoomNormalMenu  = new Menu();
        HBox zoomNormalMenuHB = new HBox();
        zoomNormalMenuHB.getChildren().addAll(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/zoom_normal.png"),new Label(objDictionaryAction.getWord("ZOOMNORMALVIEW")));
        zoomNormalMenu.setGraphic(zoomNormalMenuHB);
        final KeyCombination zoomNormalMenuKC = new KeyCodeCombination(KeyCode.ENTER,KeyCombination.CONTROL_DOWN);
        zoomNormalMenuHB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                zoomNormalAction();
            }
        });
        // Zoom In View menu
        Menu zoomInMenu  = new Menu();
        HBox zoomInMenuHB = new HBox();
        zoomInMenuHB.getChildren().addAll(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/zoom_in.png"),new Label(objDictionaryAction.getWord("ZOOMINVIEW")));
        zoomInMenu.setGraphic(zoomInMenuHB);
        final KeyCombination zoomInMenuKC = new KeyCodeCombination(KeyCode.UP,KeyCombination.CONTROL_DOWN);
        zoomInMenuHB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                zoomInAction();
            }
        });
        // Zoom Out View menu
        Menu zoomOutMenu  = new Menu();
        HBox zoomOutMenuHB = new HBox();
        zoomOutMenuHB.getChildren().addAll(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/zoom_out.png"),new Label(objDictionaryAction.getWord("ZOOMOUTVIEW")));
        zoomOutMenu.setGraphic(zoomOutMenuHB);
        final KeyCombination zoomOutMenuKC = new KeyCodeCombination(KeyCode.DOWN,KeyCombination.CONTROL_DOWN);
        zoomOutMenuHB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                zoomOutAction();
            }
        });
        // Zoom Out View menu
        Menu closeMenu  = new Menu();
        HBox closeMenuHB = new HBox();
        closeMenuHB.getChildren().addAll(new ImageView(objWeave.getObjConfiguration().getStrColour()+"/close.png"),new Label(objDictionaryAction.getWord("CLOSE")));
        closeMenu.setGraphic(closeMenuHB);
        final KeyCombination closeMenuKC = new KeyCodeCombination(KeyCode.ESCAPE,KeyCombination.CONTROL_DOWN);
        closeMenuHB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                closeAction();
            }
        });
    
        scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler() {
            @Override
            public void handle(Event t) {
                if (compositeMenuKC.match((KeyEvent) t)) {
                    plotViewActionMode = 1; //1= composite, 2 = front, 3 = rear
                    plotViewAction();
                } else if (frontMenuKC.match((KeyEvent) t)) {
                    plotViewActionMode = 2; //1= composite, 2 = front, 3 = rear
                    plotViewAction();
                } else if (rearMenuKC.match((KeyEvent) t)) {
                    plotViewActionMode = 3; //1= composite, 2 = front, 3 = rear
                    plotViewAction();
                } else if (zoomInMenuKC.match((KeyEvent) t)) {
                    zoomInAction();
                } else if (zoomOutMenuKC.match((KeyEvent) t)) {
                    zoomOutAction();
                } else if (zoomNormalMenuKC.match((KeyEvent) t)) {
                    zoomNormalAction();
                } else if (closeMenuKC.match((KeyEvent) t)) {
                    closeAction();
                }
            }
        });
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                KeyCode key = t.getCode();
                if (key == KeyCode.ESCAPE){
                    closeAction();
                } else if (key == KeyCode.DOWN){
                     zoomOutAction();
                } else if (key == KeyCode.UP){
                     zoomInAction();
                } else if (key == KeyCode.ENTER){
                     zoomNormalAction();
                }
            }
        });
        
        
        menuBar.getMenus().addAll(compositeMenu,frontMenu,rearMenu,zoomInMenu,zoomNormalMenu,zoomOutMenu,closeMenu);
        root.setTop(menuBar);
        
        ScrollPane mycon = new ScrollPane();
        weave = new ImageView();
        //weaveIV.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        weave.setId("container");
        mycon.setContent(weave);
        root.setCenter(mycon);
        plotViewAction();
        
        weaveStage.getIcons().add(new Image("/media/icon.png"));
        weaveStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWSIMULATION")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        //weaveStage.setIconified(true);
        weaveStage.setResizable(false);
        weaveStage.setScene(scene);
        weaveStage.setX(0);
        weaveStage.setY(0);
        weaveStage.show();
    }
    
    private void zoomOutAction(){
        weave.setScaleX(weave.getScaleX()/2);
        weave.setScaleY(weave.getScaleY()/2);
        weave.setScaleZ(weave.getScaleZ()/2);
    }
    
    private void zoomInAction(){
        weave.setScaleX(weave.getScaleX()*2);
        weave.setScaleY(weave.getScaleY()*2);
        weave.setScaleZ(weave.getScaleZ()*2);
    }
    
    private void zoomNormalAction(){
        weave.setScaleX(1);
        weave.setScaleY(1);
        weave.setScaleZ(1);
    }
    
    private void closeAction(){
        weaveStage.close();
        System.gc();
    }
    
    private void plotViewAction(){
        try {
            int intHeight = (int)(objWeave.getObjConfiguration().HEIGHT/3);
            int intLength = (int)(objWeave.getObjConfiguration().WIDTH/3);
            BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
    
            WeaveAction objWeaveAction = new WeaveAction();
            if(plotViewActionMode==3) {
                bufferedImage = objWeaveAction.plotFlipSideView(objWeave, objWeave.getIntWarp(), objWeave.getIntWeft(), intLength, intHeight);
            } else if(plotViewActionMode==2) {
                bufferedImage = objWeaveAction.plotFrontSideView(objWeave, objWeave.getIntWarp(), objWeave.getIntWeft(), intLength, intHeight);
            } else {
                bufferedImage = objWeaveAction.plotCompositeView(objWeave, objWeave.getIntWarp(), objWeave.getIntWeft(), intLength*3, intHeight*3);
            }
            
            BufferedImage srcImage = ImageIO.read(new File(System.getProperty("user.dir")+"/mla/zig_zag.png"));
            BufferedImage dstImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            
            Graphics2D g = dstImage.createGraphics();
            g.drawImage(srcImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
            g.dispose();
            
            srcImage = null;
            System.gc();
            for(int i = 0; i < bufferedImage.getHeight(); i++) {
                for(int j = 0; j < bufferedImage.getWidth(); j++) {
                    if(dstImage.getRGB(j, i)>=-1)
                        dstImage.setRGB(j, i, dstImage.getRGB(j, i));
                    else
                        dstImage.setRGB(j, i, bufferedImage.getRGB(j, i));
                }
            }
            //System.err.println("Len:"+intLength+"Ht:"+intHeight);
            intLength = (int)(((objWeave.getObjConfiguration().getIntDPI()*bufferedImage.getWidth())/objWeave.getIntEPI()));
            intHeight = (int)(((objWeave.getObjConfiguration().getIntDPI()*bufferedImage.getHeight())/objWeave.getIntPPI()));
            
            //if(intLength>objWeave.getObjConfiguration().WIDTH){
            intHeight=(int)(intHeight*(objWeave.getObjConfiguration().WIDTH/intLength));
            intLength=(int)objWeave.getObjConfiguration().WIDTH;
            //}
            
            
            //System.err.println("Len:"+intLength+"Ht:"+intHeight);
            BufferedImage bufferedImageesize = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g1 = bufferedImageesize.createGraphics();
            g1.drawImage(dstImage, 0, 0, intLength, intHeight, null);
            g1.dispose();
            /*int diff=(int)((dstImage.getWidth()-bufferedImageesize.getWidth())/2);
            System.err.println(diff+"DW"+dstImage.getWidth()+"BW"+bufferedImageesize.getWidth()+"M"+(1365-1218));
            for(int i = 0; i < dstImage.getHeight(); i++) {
                for(int j = 0; j < dstImage.getWidth(); j++) {
                    if(j<=diff || j>=(dstImage.getWidth()-(diff+3)))
                        dstImage.setRGB(j, i, -1);
                    else
                        dstImage.setRGB(j, i, bufferedImageesize.getRGB(j-diff, i));
                }
            }
            */
            
            weave.setImage(SwingFXUtils.toFXImage(bufferedImageesize, null));
        } catch (IOException ex) {
            Logger.getLogger(WeaveViewer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(WeaveViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        new WeaveViewer(stage);
        new Logging("WARNING",WeaveViewer.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 /*
  public static void main(String[] args) {   
      launch(args);    
  }
  */
}