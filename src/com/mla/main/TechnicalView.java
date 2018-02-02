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
package com.mla.main;

import com.mla.dictionary.DictionaryAction;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 *
 * @Designing GUI window for technical info
 * @author Amit Kumar Singh
 * 
 */
public class TechnicalView extends Application {
    private Configuration objConfiguration;
    DictionaryAction objDictionaryAction;
 /**
 * TechnicalView(Stage)
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
    public TechnicalView(final Stage primaryStage) {  }
 /**
 * TechnicalView(Configuration)
 * <p>
 * This class is used for prompting about technical information. 
 * 
 * @param       Configuration objConfigurationCall
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         javafx.scene.control.*;
 * @link        Configuration
 */
    public TechnicalView(Configuration objConfigurationCall) {  
        this.objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);
        Stage techStage = new Stage();
        techStage.initStyle(StageStyle.UTILITY);
        techStage.setScene(new Scene(new TextArea("Technical specification for this software (Open Source CAD Tool for Weaving of Banarasi Saree) as followes:\n\n\t Operating System: Windows & Linux \n\t RAM: 1GB \n\t Processor: Dual Core \n\t Java: Java 7.79 & above \n\t Flash Player: Flash Player \n\t Database Engein: MySQL 5.6 \n\n\t Thank You!")));
        //techStage.initOwner(WindowView.windowStage);
        techStage.getIcons().add(new Image("/media/icon.png"));
        techStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWTECHNICAL")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        techStage.showAndWait();
    }
     
    public void start(Stage stage) throws Exception {
        stage.initOwner(WindowView.windowStage);
        new TechnicalView(stage);        
        new Logging("WARNING",TechnicalView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    public static void main(String[] args) {   
      launch(args);    
    }            
}


