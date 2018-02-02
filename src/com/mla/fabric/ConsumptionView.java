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
package com.mla.fabric;

import com.mla.dictionary.DictionaryAction;
import com.mla.main.Logging;
import java.sql.SQLException;
import java.util.ArrayList;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 *
 * @Designing GUI window for consumption calculator
 * @author Amit Kumar Singh
 * 
 */
public class ConsumptionView {
    
    private Fabric objFabric;
    private FabricAction objFabricAction;
    private DictionaryAction objDictionaryAction;
    private Stage consumptionStage;
    GridPane container;
    
    private Button btnCancel;
    
    private TextField crimpWarpTF;
    private TextField crimpWeftTF;
    private TextField wasteWarpTF; 
    private TextField wasteWeftTF;     
    private Label warpLbl;
    private Label weftLbl;
    private Label extraWarpLbl;
    private Label extraWeftLbl;
    private Label warpLengthLbl;
    private Label weftLengthLbl;
    private Label extraWarpLengthLbl;
    private Label extraWeftLengthLbl;
    private Label extraWarpUsedLengthLbl;
    private Label extraWeftUsedLengthLbl;
    private Label warpWeightLbl;
    private Label weftWeightLbl;
    private Label extraWarpWeightLbl;
    private Label extraWeftWeightLbl;
    private Label extraWarpUsedWeightLbl;
    private Label extraWeftUsedWeightLbl;
    
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;
    
    public ConsumptionView(final Stage primaryStage) {  }

    public ConsumptionView(Fabric objFabricCall) {   
        this.objFabric = objFabricCall;
        objDictionaryAction = new DictionaryAction(objFabric.getObjConfiguration());
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 400, 500, Color.WHITE);
        scene.getStylesheets().add(ConsumptionView.class.getResource(objFabric.getObjConfiguration().getStrTemplate()+"/style.css").toExternalForm());
        
        HBox footContainer = new HBox();
        progressB = new ProgressBar(0);
        progressB.setVisible(false);
        progressI = new ProgressIndicator(0);
        progressI.setVisible(false);
        lblStatus = new Label(objDictionaryAction.getWord("WELCOME"));
        lblStatus.setId("message");
        footContainer.setId("footContainer");
        footContainer.getChildren().addAll(lblStatus,progressB,progressI);
        
        container = new GridPane();
        container.setId("popup");
        container.setVgap(5);
        container.setHgap(5);
        container.setAlignment(Pos.CENTER);
        container.autosize();
        
        Label fabricLength = new Label(objDictionaryAction.getWord("FABRICLENGTH")+"(inch)");
        container.add(fabricLength, 0, 0);
        Label fabricLengthLbl = new Label(Double.toString(objFabric.getDblFabricLength()));
        container.add(fabricLengthLbl, 1, 0);
        
        Label fabricWidth = new Label(objDictionaryAction.getWord("FABRICWIDTH")+"(inch)");
        container.add(fabricWidth, 0, 1);
        Label fabricWidthLbl = new Label(Double.toString(objFabric.getDblFabricWidth()));
        container.add(fabricWidthLbl, 1, 1);
        
        Label epi = new Label(objDictionaryAction.getWord("EPI"));
        container.add(epi, 0, 2);
        Label epihLbl = new Label(Integer.toString(objFabric.getIntEPI()));
        container.add(epihLbl, 1, 2);
        
        Label ppi = new Label(objDictionaryAction.getWord("PPI"));
        container.add(ppi, 0, 3);
        Label ppiLbl = new Label(Integer.toString(objFabric.getIntPPI()));
        container.add(ppiLbl, 1, 3);
        
        Label crimpWarp = new Label(objDictionaryAction.getWord("WARP")+" "+objDictionaryAction.getWord("YARNCRIMP")+" (%)");
        container.add(crimpWarp, 0, 4);
        crimpWarpTF = new TextField(Integer.toString(objFabric.getObjConfiguration().getIntWarpCrimp()));
        container.add(crimpWarpTF, 1, 4);
            
        Label crimpWeft = new Label(objDictionaryAction.getWord("WEFT")+" "+objDictionaryAction.getWord("YARNCRIMP")+" (%)");
        container.add(crimpWeft, 0, 5);
        crimpWeftTF = new TextField(Integer.toString(objFabric.getObjConfiguration().getIntWeftCrimp()));
        container.add(crimpWeftTF, 1, 5);
            
        Label wasteWarp = new Label(objDictionaryAction.getWord("WARP")+" "+objDictionaryAction.getWord("YARNWASTE")+" (%)");
        container.add(wasteWarp, 0, 6);
        wasteWarpTF = new TextField(Integer.toString(objFabric.getObjConfiguration().getIntWarpWaste()));
        container.add(wasteWarpTF, 1, 6);
        
        Label wasteWeft = new Label(objDictionaryAction.getWord("WEFT")+" "+objDictionaryAction.getWord("YARNWASTE")+" (%)");
        container.add(wasteWeft, 0, 7);
        wasteWeftTF = new TextField(Integer.toString(objFabric.getObjConfiguration().getIntWeftWaste()));
        container.add(wasteWeftTF, 1, 7);
        
        Label warp = new Label(objDictionaryAction.getWord("WARPYARNCONSUMPTION"));
        container.add(warp, 0, 8);
        warpLbl = new Label();
        container.add(warpLbl, 1, 8);
                
        Label warpLength = new Label(objDictionaryAction.getWord("WARPYARNLONG")+" (meter)");
        container.add(warpLength, 0, 9);
        warpLengthLbl = new Label();
        container.add(warpLengthLbl, 1, 9);
        
        Label warpWeight = new Label(objDictionaryAction.getWord("WARPYARNWEIGHT")+" (gram)");
        container.add(warpWeight, 0, 10);
        warpWeightLbl = new Label();
        container.add(warpWeightLbl, 1, 10);
        
        Label weft = new Label(objDictionaryAction.getWord("WEFTYARNCONSUMPTION"));
        container.add(weft, 0, 11);
        weftLbl = new Label();
        container.add(weftLbl, 1, 11);
        
        Label weftLength = new Label(objDictionaryAction.getWord("WEFTYARNLONG")+" (meter)");
        container.add(weftLength, 0, 12);
        weftLengthLbl = new Label();
        container.add(weftLengthLbl, 1, 12);
        
        Label weftWeight = new Label(objDictionaryAction.getWord("WEFTYARNWEIGHT")+" (gram)");
        container.add(weftWeight, 0, 13);
        weftWeightLbl = new Label();
        container.add(weftWeightLbl, 1, 13);
        
        Label extraWeft = new Label(objDictionaryAction.getWord("EXTRAWEFTYARNCONSUMPTION"));
        container.add(extraWeft, 0, 14);
        extraWeftLbl = new Label();
        container.add(extraWeftLbl, 1, 14);
        
        Label extraWeftLength = new Label(objDictionaryAction.getWord("EXTRAWEFTYARNLONG")+" (meter)");
        container.add(extraWeftLength, 0, 15);
        extraWeftLengthLbl = new Label();
        container.add(extraWeftLengthLbl, 1, 15);
        
        Label extraWeftWeight = new Label(objDictionaryAction.getWord("EXTRAWEFTYARNWEIGHT")+" (gram)");
        container.add(extraWeftWeight, 0, 16);
        extraWeftWeightLbl = new Label();
        container.add(extraWeftWeightLbl, 1, 16);
        
        Label extraWeftUsedLength = new Label(objDictionaryAction.getWord("EXTRAWEFTYARNUSEDLONG")+" (meter)");
        container.add(extraWeftUsedLength, 0, 17);
        extraWeftUsedLengthLbl = new Label();
        container.add(extraWeftUsedLengthLbl, 1, 17);
        
        Label extraWeftUsedWeight = new Label(objDictionaryAction.getWord("EXTRAWEFTYARNUSEDWEIGHT")+" (gram)");
        container.add(extraWeftUsedWeight, 0, 18);
        extraWeftUsedWeightLbl = new Label();
        container.add(extraWeftUsedWeightLbl, 1, 18);
        
        crimpWarpTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                calculateWarpConsumption();
                //calculateExtraWarpConsumption();
            }
        });
        crimpWeftTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                calculateWeftConsumption();
                calculateExtraWeftConsumption();
            }
        });
        wasteWarpTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                calculateWarpConsumption();
                //calculateExtraWarpConsumption();
            }
        });
        wasteWeftTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                calculateWeftConsumption();
                calculateExtraWeftConsumption();
            }
        });
        calculateWarpConsumption();
        calculateWeftConsumption();
        //calculateExtraWarpConsumption();
        calculateExtraWeftConsumption();
        
        btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
        btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
        btnCancel.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColour()+"/close.png"));
        container.add(btnCancel, 1, 19);
                
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                lblStatus.setText(objDictionaryAction.getWord("ACTIONCANCEL"));
                consumptionStage.close();
            }
        });        
        root.setCenter(container);
        root.setBottom(footContainer);
        consumptionStage = new Stage(); 
        consumptionStage.setScene(scene);       
        consumptionStage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL
        consumptionStage.initStyle(StageStyle.UTILITY); 
        consumptionStage.getIcons().add(new Image("/media/icon.png"));
        consumptionStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWCONSUMPTION")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        consumptionStage.setIconified(false);
        consumptionStage.setResizable(false);
        consumptionStage.showAndWait();
    }
    
    public void calculateWarpConsumption(){
        try {
            if(crimpWarpTF.getText().toString().equalsIgnoreCase("") || crimpWarpTF.getText().toString().equalsIgnoreCase(null)){
                lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                crimpWarpTF.setText(Integer.toString(0));
            } else if(Integer.parseInt(crimpWarpTF.getText())<0 || Integer.parseInt(crimpWarpTF.getText())>100){
                lblStatus.setText(objDictionaryAction.getWord("PERCENTAGELIMIT"));
                crimpWarpTF.setText(Integer.toString(0));
            }
            if(wasteWarpTF.getText().toString().equalsIgnoreCase("") || wasteWarpTF.getText().toString().equalsIgnoreCase(null)){
                lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                wasteWarpTF.setText(Integer.toString(0));
            } else if(Integer.parseInt(wasteWarpTF.getText())<0 || Integer.parseInt(wasteWarpTF.getText())>100){
                lblStatus.setText(objDictionaryAction.getWord("PERCENTAGELIMIT"));
                wasteWarpTF.setText(Integer.toString(0));
            }
            
            objFabric.getObjConfiguration().setIntWarpCrimp(Integer.parseInt(crimpWarpTF.getText()));
            objFabric.getObjConfiguration().setIntWarpWaste(Integer.parseInt(wasteWarpTF.getText()));
            
            objFabricAction = new FabricAction(false);
            
            objFabric.getObjConfiguration().setDblWarpNumber(objFabricAction.getWarpNumber(objFabric));
            //warpLbl.setText(Double.toString(objFabric.getObjConfiguration().getDblWarpNumber()));
            warpLbl.setText(String.format("%.3f", objFabric.getObjConfiguration().getDblWarpNumber()));
            
            objFabric.getObjConfiguration().setDblWarpLong(objFabricAction.getWarpLong(objFabric));
            //warpLengthLbl.setText(Double.toString(objFabric.getObjConfiguration().getDblWarpLong()));
            warpLengthLbl.setText(String.format("%.3f", objFabric.getObjConfiguration().getDblWarpLong()));
            
            objFabric.getObjConfiguration().setDblWarpWeight(objFabricAction.getWarpWeight(objFabric));
            //warpWeightLbl.setText(Double.toString(objFabric.getObjConfiguration().getDblWarpWeight()));
            warpWeightLbl.setText(String.format("%.3f", objFabric.getObjConfiguration().getDblWarpWeight()));
        } catch (SQLException ex) {
            new Logging("SEVERE",ConsumptionView.class.getName(),"calculateConsumption() : Error while calculating consumption",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
     
    public void calculateWeftConsumption(){
        try {            
            if(crimpWeftTF.getText().toString().equalsIgnoreCase("") || crimpWeftTF.getText().toString().equalsIgnoreCase(null)){
                lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                crimpWeftTF.setText(Integer.toString(0));
            } else if(Integer.parseInt(crimpWeftTF.getText())<0 || Integer.parseInt(crimpWeftTF.getText())>100){
                lblStatus.setText(objDictionaryAction.getWord("PERCENTAGELIMIT"));
                crimpWeftTF.setText(Integer.toString(0));
            }
            if(wasteWeftTF.getText().toString().equalsIgnoreCase("") || wasteWeftTF.getText().toString().equalsIgnoreCase(null)){
                lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                wasteWeftTF.setText(Integer.toString(0));
            } else if(Integer.parseInt(wasteWeftTF.getText())<0 || Integer.parseInt(wasteWeftTF.getText())>100){
                lblStatus.setText(objDictionaryAction.getWord("PERCENTAGELIMIT"));
                wasteWeftTF.setText(Integer.toString(0));
            }
            
            objFabric.getObjConfiguration().setIntWeftCrimp(Integer.parseInt(crimpWeftTF.getText()));
            objFabric.getObjConfiguration().setIntWeftWaste(Integer.parseInt(wasteWeftTF.getText()));
            
            objFabricAction = new FabricAction(false);
            
            objFabric.getObjConfiguration().setDblWeftNumber(objFabricAction.getWeftNumber(objFabric));
            //weftLbl.setText(Double.toString(objFabric.getObjConfiguration().getDblWeftNumber()));
            weftLbl.setText(String.format("%.3f", objFabric.getObjConfiguration().getDblWeftNumber()));
            
            objFabric.getObjConfiguration().setDblWeftLong(objFabricAction.getWeftLong(objFabric));
            //weftLengthLbl.setText(Double.toString(objFabric.getObjConfiguration().getDblWeftLong()));
            weftLengthLbl.setText(String.format("%.3f", objFabric.getObjConfiguration().getDblWeftLong()));
            
            objFabric.getObjConfiguration().setDblWeftWeight(objFabricAction.getWeftWeight(objFabric));
            //weftWeightLbl.setText(Double.toString(objFabric.getObjConfiguration().getDblWeftWeight()));            
            weftWeightLbl.setText(String.format("%.3f", objFabric.getObjConfiguration().getDblWeftWeight()));            
        } catch (SQLException ex) {
            new Logging("SEVERE",ConsumptionView.class.getName(),"calculateConsumption() : Error while calculating consumption",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    public void calculateExtraWeftConsumption(){
        double extraWeft = 0;
        int lineCount = 0;
        
        ArrayList<Byte> lstEntry = null;
        for (int i = 0; i < objFabric.getIntWeft(); i++){
            lstEntry = new ArrayList();
            for (int j = 0; j < objFabric.getIntWarp(); j++){
                //add the first color on array
                if(lstEntry.size()==0 && objFabric.getFabricMatrix()[i][j]!=1)
                    lstEntry.add(objFabric.getFabricMatrix()[i][j]);
                //check for redudancy
                else {                
                    if(!lstEntry.contains(objFabric.getFabricMatrix()[i][j]) && objFabric.getFabricMatrix()[i][j]!=1)
                        lstEntry.add(objFabric.getFabricMatrix()[i][j]);
                }
            }
            lineCount+=lstEntry.size();
        }
        lineCount -=objFabric.getIntWeft();
        for(int i=0;i<objFabric.getColorWeave().length;i++){
            if(objFabric.getColorWeave()[i][1]!=null){
                if(objFabric.getColorCountArtwork()<=Integer.parseInt(objFabric.getColorWeave()[i][2]))
                    extraWeft += Double.parseDouble(objFabric.getColorWeave()[i][3]);
            }
        }
        
        objFabric.getObjConfiguration().setDblExtraWeftNumber(lineCount*objFabric.getObjConfiguration().getDblWeftNumber()/objFabric.getIntWeft());
        //extraWeftLbl.setText(Double.toString(objFabric.getObjConfiguration().getDblExtraWeftNumber()));
        extraWeftLbl.setText(String.format("%.3f", objFabric.getObjConfiguration().getDblExtraWeftNumber()));

        objFabric.getObjConfiguration().setDblExtraWeftLong(objFabricAction.getExtraWeftLong(objFabric));
        //extraWeftLengthLbl.setText(Double.toString(objFabric.getObjConfiguration().getDblExtraWeftLong()));
        extraWeftLengthLbl.setText(String.format("%.3f", objFabric.getObjConfiguration().getDblExtraWeftLong()));
        //extraWeftUsedLengthLbl.setText(Double.toString(objFabric.getObjConfiguration().getDblWeftLong()*extraWeft/100));
        extraWeftUsedLengthLbl.setText(String.format("%.3f", objFabric.getObjConfiguration().getDblWeftLong()*extraWeft/100));

        objFabric.getObjConfiguration().setDblExtraWeftWeight(objFabricAction.getExtraWeftWeight(objFabric));
        //extraWeftWeightLbl.setText(Double.toString(objFabric.getObjConfiguration().getDblExtraWeftWeight()));            
        extraWeftWeightLbl.setText(String.format("%.3f", objFabric.getObjConfiguration().getDblExtraWeftWeight()));
        //extraWeftUsedWeightLbl.setText(Double.toString(objFabric.getObjConfiguration().getDblWeftWeight()*extraWeft/100));
        extraWeftUsedWeightLbl.setText(String.format("%.3f", objFabric.getObjConfiguration().getDblWeftWeight()*extraWeft/100));
    }
            
    public void start(Stage stage) throws Exception {
        stage.initOwner(FabricView.fabricStage);
        new ConsumptionView(stage);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    public static void main(String[] args) {   
      launch(args);    
    } 
}

