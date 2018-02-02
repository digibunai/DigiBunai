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
import javafx.scene.Cursor;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.stage.StageStyle;
/**
 *
 * @Designing GUI window for help
 * @author Amit Kumar Singh
 * 
 */
public class HelpView extends Application {
    private Configuration objConfiguration;
    DictionaryAction objDictionaryAction;
	
 /**
 * HelpView(Stage)
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
    public HelpView(final Stage primaryStage) {  }

 /**
 * HelpView(Configuration)
 * <p>
 * This class is used for prompting about Help information. 
 * 
 * @param       Configuration objConfigurationCall
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         javafx.stage.*;
 * @link        FabricView
 */
    public HelpView(Configuration objConfigurationCall) {  
        this.objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);
        WebView contentHelp = new WebView();
        /*
        final WebEngine webEngine = contentHelp.getEngine();
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
                public void changed(ObservableValue ov, State oldState, State newState) {
                    if (newState == State.SUCCEEDED) {
                        fabricStage.setTitle(webEngine.getLocation());
                    }
                }
            });
        webEngine.load("file:///" + objConfiguration.strRoot+"\\help\\Movement-pointer_or_click.swf");
        */
        contentHelp.setPrefSize(888, 666);
        contentHelp.setCursor(Cursor.CLOSED_HAND);
        contentHelp.getEngine().load("file:///" + objConfiguration.strRoot+"\\help\\"+objConfiguration.getStrLanguage()+".html");
        System.out.println("file:///" + objConfiguration.strRoot+"\\help\\"+objConfiguration.getStrLanguage()+".html");
        Stage helpStage = new Stage();
        helpStage.initStyle(StageStyle.UTILITY);
        //helpStage.initOwner(WindowView.windowStage);
        helpStage.setScene(new Scene(contentHelp));
        helpStage.getIcons().add(new Image("/media/icon.png"));
        helpStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWUSERMANUAL")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        helpStage.showAndWait();
    }
     
    public void start(Stage stage) throws Exception {
        stage.initOwner(WindowView.windowStage);
        new HelpView(stage);        
        new Logging("WARNING",HelpView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    public static void main(String[] args) {   
      launch(args);    
    }            
}