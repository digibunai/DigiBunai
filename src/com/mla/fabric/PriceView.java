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
 * @Designing GUI window for price calculator
 * @author Amit Kumar Singh
 * 
 */
public class PriceView {
   
    private Fabric objFabric;
    private FabricAction objFabricAction;
    private DictionaryAction objDictionaryAction;
    private Stage priceStage;
    GridPane container;
    
    private Button btnCancel;
    
    private TextField materialCostTF;
    private TextField designingCostTF; 
    private TextField punchingCostTF;
    private TextField propertyCostTF; 
    private TextField wagesCostTF;
    private TextField overheadCostTF; 
    private TextField profitTF;
    
    private Label priceLbl;
    
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;
    
    public PriceView(final Stage primaryStage) {  }

    public PriceView(Fabric objFabricCall) {   
        this.objFabric = objFabricCall;
        objDictionaryAction = new DictionaryAction(objFabric.getObjConfiguration());
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 350, 350, Color.WHITE);
        scene.getStylesheets().add(PriceView.class.getResource(objFabric.getObjConfiguration().getStrTemplate()+"/style.css").toExternalForm());
        HBox footContainer = new HBox();
        progressB = new ProgressBar(0);
        progressB.setVisible(false);
        progressI = new ProgressIndicator(0);
        progressI.setVisible(false);
        lblStatus = new Label(objDictionaryAction.getWord("WELCOME"));
        lblStatus.setId("message");
        footContainer.getChildren().addAll(lblStatus,progressB,progressI);
        
        container = new GridPane();
        container.setId("popup");
        container.setVgap(5);
        container.setHgap(5);
        container.setAlignment(Pos.CENTER);
        container.autosize();
        
        Label materialCost = new Label(objDictionaryAction.getWord("MATERIALCOST")+" (rupees)");
        container.add(materialCost, 0, 0);
        try {
            objFabricAction = new FabricAction(false);
            
            objFabric.getObjConfiguration().setDblWarpNumber(objFabricAction.getWarpNumber(objFabric));
            objFabric.getObjConfiguration().setDblWarpLong(objFabricAction.getWarpLong(objFabric));
            objFabric.getObjConfiguration().setDblWarpWeight(objFabricAction.getWarpWeight(objFabric));
            objFabric.getObjConfiguration().setDblWeftNumber(objFabricAction.getWeftNumber(objFabric));
            objFabric.getObjConfiguration().setDblWeftLong(objFabricAction.getWeftLong(objFabric));
            objFabric.getObjConfiguration().setDblWeftWeight(objFabricAction.getWeftWeight(objFabric));
            
            //materialCostTF = new TextField(Double.toString(objFabricAction.getMaterialCost(objFabric)));
            materialCostTF = new TextField(String.format("%.3f", objFabricAction.getMaterialCost(objFabric)));
            materialCostTF.setEditable(false);
            container.add(materialCostTF, 1, 0);
        } catch (SQLException ex) {
            new Logging("SEVERE",PriceView.class.getName(),"calculatePrice() : Error while calculating Price",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
        
        Label designingCost = new Label(objDictionaryAction.getWord("DESIGNINGCOST")+" (rupees)");
        container.add(designingCost, 0, 1);
        //designingCostTF = new TextField(Double.toString(objFabric.getObjConfiguration().getDblDesigningCost()));
        designingCostTF = new TextField(String.format("%.3f", objFabric.getObjConfiguration().getDblDesigningCost()));
        container.add(designingCostTF, 1, 1);
        
        Label punchingCost = new Label(objDictionaryAction.getWord("PUNCHINGCOST")+" (rupees)");
        container.add(punchingCost, 0, 2);
        //punchingCostTF = new TextField(Double.toString(objFabric.getObjConfiguration().getDblPunchingCost()));]
        punchingCostTF = new TextField(String.format("%.3f", objFabric.getObjConfiguration().getDblPunchingCost()));
        container.add(punchingCostTF, 1, 2);
        
        Label propertyCost = new Label(objDictionaryAction.getWord("PROPERTYCOST")+"(rupees)");
        container.add(propertyCost, 0, 3);
        //propertyCostTF = new TextField(Double.toString(objFabric.getObjConfiguration().getDblPropertyCost()));
        propertyCostTF = new TextField(String.format("%.3f", objFabric.getObjConfiguration().getDblPropertyCost()));
        container.add(propertyCostTF, 1, 3);
        
        Label wagesCost = new Label(objDictionaryAction.getWord("WAGES")+" (rupees)");
        container.add(wagesCost, 0, 4);
        //wagesCostTF = new TextField(Double.toString(objFabric.getObjConfiguration().getDblWagesCost()));
        wagesCostTF = new TextField(String.format("%.3f", objFabric.getObjConfiguration().getDblWagesCost()));
        container.add(wagesCostTF, 1, 4);
        
        Label overheadCost = new Label(objDictionaryAction.getWord("OVERHEAD")+" (%)");
        container.add(overheadCost, 0, 5);
        overheadCostTF = new TextField(Integer.toString(objFabric.getObjConfiguration().getIntOverheadCost()));
        container.add(overheadCostTF, 1, 5);
            
        Label profit = new Label(objDictionaryAction.getWord("PROFIT")+" (%)");
        container.add(profit, 0, 6);
        profitTF = new TextField(Integer.toString(objFabric.getObjConfiguration().getIntProfit()));
        container.add(profitTF, 1, 6);
        
        Label price = new Label(objDictionaryAction.getWord("TOTALCOST")+" (rupees)");
        container.add(price, 0, 7);
        priceLbl = new Label();
        container.add(priceLbl, 1, 7);
        
        calculateCost();
        
        materialCostTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                calculateCost();
            }
        }); 
        designingCostTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                calculateCost();
            }
        }); 
        punchingCostTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                calculateCost();
            }
        }); 
        propertyCostTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                calculateCost();
            }
        }); 
        wagesCostTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                calculateCost();
            }
        });
        overheadCostTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                calculateCost();
            }
        }); 
        profitTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                calculateCost();
            }
        });
        calculateCost();
        
        btnCancel = new Button(objDictionaryAction.getWord("CANCEL"));
        btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
        btnCancel.setGraphic(new ImageView(objFabric.getObjConfiguration().getStrColour()+"/close.png"));
        container.add(btnCancel, 1, 12);
                
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                lblStatus.setText(objDictionaryAction.getWord("CLOSEACTION"));
                priceStage.close();
            }
        });        
        root.setCenter(container);
        root.setBottom(footContainer);
        priceStage = new Stage(); 
        priceStage.setScene(scene);
        priceStage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL
        priceStage.initStyle(StageStyle.UTILITY); 
        priceStage.getIcons().add(new Image("/media/icon.png"));
        priceStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWPRICE")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        priceStage.setIconified(false);
        priceStage.setResizable(false);
        priceStage.showAndWait();
    }
    
    public void calculateCost(){
        try {
            if(materialCostTF.getText().toString().equalsIgnoreCase("") || materialCostTF.getText().toString().equalsIgnoreCase(null)){
                lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                materialCostTF.setText(Integer.toString(0));
            }
            if(punchingCostTF.getText().toString().equalsIgnoreCase("") || punchingCostTF.getText().toString().equalsIgnoreCase(null)){
                lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                punchingCostTF.setText(Integer.toString(0));
            }
            if(designingCostTF.getText().toString().equalsIgnoreCase("") || designingCostTF.getText().toString().equalsIgnoreCase(null)){
                lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                designingCostTF.setText(Integer.toString(0));
            }
            if(propertyCostTF.getText().toString().equalsIgnoreCase("") || propertyCostTF.getText().toString().equalsIgnoreCase(null)){
                lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                propertyCostTF.setText(Integer.toString(0));
            }
            if(wagesCostTF.getText().toString().equalsIgnoreCase("") || wagesCostTF.getText().toString().equalsIgnoreCase(null)){
                lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                wagesCostTF.setText(Integer.toString(0));
            }
            if(overheadCostTF.getText().toString().equalsIgnoreCase("") || overheadCostTF.getText().toString().equalsIgnoreCase(null)){
                lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                overheadCostTF.setText(Integer.toString(0));
            } else if(Integer.parseInt(overheadCostTF.getText())<0 || Integer.parseInt(overheadCostTF.getText())>100){
                lblStatus.setText(objDictionaryAction.getWord("PERCENTAGELIMIT"));
                overheadCostTF.setText(Integer.toString(0));
            }
            if(profitTF.getText().toString().equalsIgnoreCase("") || profitTF.getText().toString().equalsIgnoreCase(null)){
                lblStatus.setText(objDictionaryAction.getWord("BALNKINPUT"));
                profitTF.setText(Integer.toString(0));
            } else if(Integer.parseInt(profitTF.getText())<0 || Integer.parseInt(profitTF.getText())>100){
                lblStatus.setText(objDictionaryAction.getWord("PERCENTAGELIMIT"));
                profitTF.setText(Integer.toString(0));
            }
            objFabric.getObjConfiguration().setDblPunchingCost(Float.parseFloat(punchingCostTF.getText()));
            objFabric.getObjConfiguration().setDblDesigningCost(Float.parseFloat(designingCostTF.getText()));
            objFabric.getObjConfiguration().setDblPropertyCost(Float.parseFloat(propertyCostTF.getText()));
            objFabric.getObjConfiguration().setDblWagesCost(Float.parseFloat(wagesCostTF.getText()));
            objFabric.getObjConfiguration().setIntOverheadCost(Integer.parseInt(overheadCostTF.getText()));
            objFabric.getObjConfiguration().setIntProfit(Integer.parseInt(profitTF.getText()));
            objFabricAction = new FabricAction(false);
            //priceLbl.setText(Double.toString(objFabricAction.calculateCost(objFabric)));
            priceLbl.setText(String.format("%.3f", objFabricAction.calculateCost(objFabric)));
        } catch (SQLException ex) {
            new Logging("SEVERE",PriceView.class.getName(),"calculatePrice() : Error while calculating Price",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
        
    public void start(Stage stage) throws Exception {
        stage.initOwner(FabricView.fabricStage);
        new PriceView(stage);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    public static void main(String[] args) {   
      launch(args);    
    } 
}

