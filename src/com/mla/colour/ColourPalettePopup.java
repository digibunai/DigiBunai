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

package com.mla.colour;

import static com.mla.colour.ColorPaletteView.colorStage;
import com.mla.dictionary.DictionaryAction;
import com.mla.fabric.FabricSettingView;
import com.mla.main.Configuration;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Aatif Ahmad Khan
 */
public class ColourPalettePopup {
    public Stage colorStage;
    BorderPane root;
    private Label lblStatus;
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    public String colorCode;
    
    public ColourPalettePopup(Configuration objConfigurationCall) throws SQLException, Exception {   
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);
        
        colorCode="";
        colorStage = new Stage();
        colorStage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if(t1.booleanValue()==false)
                    colorStage.close();
            }
        });
        
        colorStage.initStyle(StageStyle.UNDECORATED);
        colorStage.initModality(Modality.WINDOW_MODAL);
        root = new BorderPane();
        Scene scene = new Scene(root, 220, 200, Color.WHITE);
        scene.getStylesheets().add(ColourPalettePopup.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
        
        lblStatus = new Label(objDictionaryAction.getWord("WELCOMETOCADTOOL"));
        lblStatus.setId("message");
        
        // 7 rows x 8 cols = 56 colors (52 from palette, 4 blank)
        final GridPane colorGP=new GridPane();
        colorGP.setId("subpopup");
        colorGP.setHgap(2);
        colorGP.setVgap(2);
        colorGP.setPadding(new Insets(5, 5, 5, 5));
        //colorGP.setGridLinesVisible(true);
        for(int i=0; i<objConfiguration.getColourPalette().length; i++){
            final Label cLbl=new Label();
            cLbl.setPrefSize(25,25);
            cLbl.setStyle("-fx-background-color:#"+objConfiguration.getColourPalette()[i]+";");
            colorGP.add(cLbl, (i%8), (i/8));
            cLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    colorCode=cLbl.getStyle().substring(cLbl.getStyle().lastIndexOf("#")+1, cLbl.getStyle().lastIndexOf("#")+7);
                    colorStage.close();
                }
            });
        }
        
        Button btnCustomColor=new Button();
        btnCustomColor.setId("btnYes");
        btnCustomColor.setText(objDictionaryAction.getWord("CUSTOMCOLOR"));
        colorGP.add(btnCustomColor, 4, 6, 4, 1);
        btnCustomColor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    ColourPaletteCustom objColourPaletteCustom=new ColourPaletteCustom(objConfiguration);
                    if(objColourPaletteCustom.colorCode.length()==6){
                        colorCode=objColourPaletteCustom.colorCode;
						// code added to reflect custom selected code
                        objConfiguration.getColourPalette()[0]=colorCode;
                        /*if(objColourPaletteCustom.objColour.getStrColorID()!=null&&objColourPaletteCustom.objColour.getStrColorID().length()>0){
                            final Label cLbl=new Label();
                            cLbl.setPrefSize(25,25);
                            cLbl.setStyle("-fx-background-color:#"+colorCode+";");
                            cLbl.setId("#"+colorCode);
                            cLbl.setUserData(objColourPaletteCustom.objColour.getStrColorID());
                            colorGP.add(cLbl, 4, 6);
                            cLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent t) {
                                    colorCode=cLbl.getStyle().substring(cLbl.getStyle().lastIndexOf("#")+1, cLbl.getStyle().lastIndexOf("#")+7);
                                    colorStage.close();
                                }
                            });
                        }*/
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ColourPalettePopup.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        root.setCenter(colorGP);
        colorStage.setScene(scene);
        colorStage.showAndWait();
    }
}
