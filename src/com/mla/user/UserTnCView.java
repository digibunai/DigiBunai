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
package com.mla.user;

import com.mla.dictionary.DictionaryAction;
import com.mla.main.Configuration;
import com.mla.main.Logging;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Added: 6 Mar 2017
 * @author Aatif Ahmad Khan
 */
public class UserTnCView {
    DictionaryAction objDictionaryAction=null;
    public UserTnCView(Configuration objConfiguration){
        final Stage primaryStage=new Stage();
        objDictionaryAction=new DictionaryAction(objConfiguration);
        primaryStage.setTitle(objDictionaryAction.getWord("LICENSEAGREEMENTTITLE"));
        GridPane grid=new GridPane();
        grid.setVgap(10);
        grid.setHgap(5);
        Label caption=new Label(objDictionaryAction.getWord("LICENSEAGREEMENTCAPTION"));
        grid.add(caption, 0, 0, 2, 1);
        WebView license=new WebView();
        license.setPrefSize(600, 440);
        license.setCursor(Cursor.DEFAULT);
        //TextArea license=new TextArea();
        //license.setPrefColumnCount(50);
        //license.setPrefRowCount(15);
        //license.setWrapText(true);
        //license.setEditable(false);
        grid.add(license, 0, 1, 2, 1);
        //String latestTnCId=null;
        try{
            UserAction objUserAction=new UserAction();
            license.getEngine().load("file:///"+objUserAction.getLatestLicenseContent().getAbsolutePath());
            //latestTnCId=objUserAction.getLatestLicenseId();
        }
        catch(SQLException ex){
            new Logging("ERROR", UserTnCView.class.getName(), "Error fetching database data", ex);
        }
        Button btnCancel=new Button(objDictionaryAction.getWord("CLOSE"));
        btnCancel.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
                System.gc();
            }
        });
        grid.add(btnCancel, 1, 3);
        BorderPane root=new BorderPane();
        root.setCenter(grid);
        Scene scene=new Scene(root, 500, 400, Color.WHITE);
        scene.getStylesheets().add(UserView.class.getResource("/media/login.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }
    
}
