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
import com.mla.fabric.FabricAction;
import com.mla.main.AboutView;
import com.mla.main.Configuration;
import com.mla.main.ContactView;
import com.mla.main.HelpView;
import com.mla.main.Logging;
import com.mla.main.TechnicalView;
import com.mla.main.WindowView;
import java.sql.SQLException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
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
public class ConvertorView extends Application {
    public static Stage convertorStage;
    BorderPane root;
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;
    
    private FabricAction objFabricAction;
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    
    private Menu homeMenu;
    private Menu helpMenu;

    TextField valueCountTxt;
    ComboBox fromCountCB;
    ComboBox toCountCB;
    Label newCountTxt;
    TextField valueMeasureTxt;
    ComboBox fromMeasureCB;
    ComboBox toMeasureCB;
    Label newMeasueTxt;
    TextField valueWeightTxt;
    ComboBox fromWeightCB;
    ComboBox toWeightCB;
    Label newWeightTxt;
    TextField txtEPI;
    TextField txtPPI;
    TextField txtCrimpWarp;
    TextField txtCrimpWeft;
    TextField txtCountWarp;
    ComboBox cbUnitWarp;
    TextField txtCountWeft;
    ComboBox cbUnitWeft;
    Label txtGSM;
   String regExInt="[0-9]+";
   String regExDouble="[0-9]{1,13}(\\.[0-9]*)?";
     
    public ConvertorView(final Stage primaryStage) {}
    
    public ConvertorView(Configuration objConfigurationCall) {
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);

        convertorStage = new Stage(); 
        root = new BorderPane();
        Scene scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(ConvertorView.class.getResource(objConfiguration.getStrTemplate()+"/setting.css").toExternalForm());
        
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
        menuBar.prefWidthProperty().bind(convertorStage.widthProperty());
        
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
                scene.getStylesheets().add(ConvertorView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        convertorStage.close();
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
                convertorStage.close();    
            }
        });    
        helpMenu.getItems().addAll(helpMenuItem, technicalMenuItem, aboutMenuItem, contactMenuItem, new SeparatorMenuItem(), exitMenuItem);        
        menuBar.getMenus().addAll(homeMenu, helpMenu);
        root.setTop(menuBar);
        
        ScrollPane container = new ScrollPane();
        
        TabPane tabPane = new TabPane();

        Tab yarnCountTab = new Tab();
        Tab measureTab = new Tab();
        Tab weightTab = new Tab();
        Tab gsmTab = new Tab();
		Tab colorTab=new Tab();
        
        yarnCountTab.setClosable(false);
        measureTab.setClosable(false);
        weightTab.setClosable(false);
        gsmTab.setClosable(false);
		colorTab.setClosable(false);
        
        yarnCountTab.setText(objDictionaryAction.getWord("COUNTCONVERSION"));
        measureTab.setText(objDictionaryAction.getWord("LINEARMEASURE"));
        weightTab.setText(objDictionaryAction.getWord("WEIGHTCONVERSION"));
        gsmTab.setText(objDictionaryAction.getWord("GSMCONVERSION"));
		colorTab.setText(objDictionaryAction.getWord("COLORCONVERSION"));
        
        yarnCountTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOUNTCONVERSION")));
        measureTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPLINEARMEASURE")));
        weightTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWEIGHTCONVERSION")));
        gsmTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPGSMCONVERSION")));
		colorTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCOLORCONVERSION")));
        
        //yarnCountTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/tex_calculator.png"));
        //measureTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/linear_calculator.png"));
        //weightTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/weight_calculator.png"));
        //gsmTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/gsm_conversion.png"));
        //colorTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/gsm_conversion.png"));
        
        GridPane yarnCountGP = new GridPane();
        GridPane measureGP = new GridPane();
        GridPane weightGP = new GridPane();
        GridPane gsmGP = new GridPane();
		GridPane colorGP = new GridPane();
        
        yarnCountGP.setId("container");
        measureGP.setId("container");
        weightGP.setId("container");
        gsmGP.setId("container");
		colorGP.setId("container");
        
        //yarnCountGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //measureGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //weightGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //gsmGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //colorGP.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        
        //yarnCountGP.setAlignment(Pos.TOP_CENTER);
        //measureGP.setAlignment(Pos.TOP_CENTER);
        //weightGP.setAlignment(Pos.TOP_CENTER);
        //gsmGP.setAlignment(Pos.TOP_CENTER);
        //colorGP.setAlignment(Pos.TOP_CENTER);
        
        yarnCountTab.setContent(yarnCountGP);
        measureTab.setContent(measureGP);
        weightTab.setContent(weightGP);
        gsmTab.setContent(gsmGP);
		colorTab.setContent(colorGP);
        
        tabPane.getTabs().add(yarnCountTab);
        tabPane.getTabs().add(measureTab);
        tabPane.getTabs().add(weightTab);
        tabPane.getTabs().add(gsmTab);
		tabPane.getTabs().add(colorTab);
        
        container.setContent(tabPane);
        
        //////---------Yarn Count-------//////
        Label yarnCount = new Label(objDictionaryAction.getWord("YARNCOUNTCONVERSION"));
        yarnCount.setGraphic(new ImageView(objConfiguration.getStrColour()+"/tex_calculator.png"));
        yarnCount.setId("caption");
        GridPane.setConstraints(yarnCount, 0, 0);
        GridPane.setColumnSpan(yarnCount, 2);
        yarnCountGP.getChildren().add(yarnCount);
        
        Label valueCountLbl= new Label(objDictionaryAction.getWord("YARNCOUNT")+" :");
        //valueCountLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        yarnCountGP.add(valueCountLbl, 0, 1);
        valueCountTxt = new TextField("1"){
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
        valueCountTxt.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOUNT")));
        yarnCountGP.add(valueCountTxt, 1, 1);

        Label fromCountLbl= new Label(objDictionaryAction.getWord("YARNUNIT")+" :");
        //fromCountLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        yarnCountGP.add(fromCountLbl, 0, 2);
        fromCountCB = new ComboBox();
        fromCountCB.getItems().addAll("Tex","dTex","K tex","Denier (Td)","New Metric (Nm)","Grains/Yard","Woollen (Aberdeen) (Ta)","Woollen (US Grain)","Asbestos (American) (NaA)","Asbestos (English) (NeA)","Cotton bump Yarn (NB)","Glass (UK & USA)","Linen (Set or Dry Spun) (NeL)","Spun Silk (Ns)","Woollen (American Cut) (Nac)","Woollen (American run) (Nar)","Woollen (Yarkshire) (Ny)","Woollen (Worsted) (New)","Linen, Hemp, Jute (Tj)","Micronaire (Mic)","Yards Per Pound (YPP)","English Worsted Count (NeK)","English Cotton (NeC)","New English (Ne)","Numero en puntos (Np)");  
        fromCountCB.setValue("Tex");
        fromCountCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNUNIT")));
        yarnCountGP.add(fromCountCB, 1, 2);
        
        Label toCountLbl= new Label(objDictionaryAction.getWord("YARNUNIT")+" :");
        //toCountLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        yarnCountGP.add(toCountLbl, 0, 3);
        toCountCB = new ComboBox();
        toCountCB.getItems().addAll("Tex","dTex","K tex","Denier (Td)","New Metric (Nm)","Grains/Yard","Woollen (Aberdeen) (Ta)","Woollen (US Grain)","Asbestos (American) (NaA)","Asbestos (English) (NeA)","Cotton bump Yarn (NB)","Glass (UK & USA)","Linen (Set or Dry Spun) (NeL)","Spun Silk (Ns)","Woollen (American Cut) (Nac)","Woollen (American run) (Nar)","Woollen (Yarkshire) (Ny)","Woollen (Worsted) (New)","Linen, Hemp, Jute (Tj)","Micronaire (Mic)","Yards Per Pound (YPP)","English Worsted Count (NeK)","English Cotton (NeC)","New English (Ne)","Numero en puntos (Np)");  
        toCountCB.setValue("Tex");
        toCountCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNUNIT")));
        yarnCountGP.add(toCountCB, 1, 3);
        
        Label newCountLbl= new Label(objDictionaryAction.getWord("YARNCOUNT")+" :");
        newCountLbl.setId("result");
        yarnCountGP.add(newCountLbl, 0, 4);
        newCountTxt = new Label("1");
        //newCountTxt.setId("result");
        yarnCountGP.add(newCountTxt, 1, 4);
        
        valueCountTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase("0")){
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                        valueCountTxt.setText(t);
                    }else {
                        objFabricAction = new FabricAction();
                        double newValue = objFabricAction.convertUnit(fromCountCB.getValue().toString(), toCountCB.getValue().toString(), Float.parseFloat(valueCountTxt.getText()));
                        newCountTxt.setText(Double.toString(newValue));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSCOUNT"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"yarn count property change",ex);
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"yarn count property change",ex);
                }
            }
        });
        fromCountCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase(toCountCB.getValue().toString())){
                        newCountTxt.setText(objDictionaryAction.getWord("ERRORCOUNT"));
                        lblStatus.setText(objDictionaryAction.getWord("ERRORCOUNT"));
                    }else{
                        objFabricAction = new FabricAction();
                        double newValue = objFabricAction.convertUnit(t1, toCountCB.getValue().toString(), Float.parseFloat(valueCountTxt.getText()));
                        newCountTxt.setText(Double.toString(newValue));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSCOUNT"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"count unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }  catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"count unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        toCountCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase(fromCountCB.getValue().toString())){
                        newCountTxt.setText(objDictionaryAction.getWord("ERRORCOUNT"));
                        lblStatus.setText(objDictionaryAction.getWord("ERRORCOUNT"));
                    }else{
                        objFabricAction = new FabricAction();
                        double newValue = objFabricAction.convertUnit(fromCountCB.getValue().toString(), t1, Float.parseFloat(valueCountTxt.getText()));
                        newCountTxt.setText(Double.toString(newValue));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSCOUNT"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"count unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }  catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"count unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        
        //////--------- measure -------//////
        Label measure = new Label(objDictionaryAction.getWord("LINEARMEASURECONVERSION"));
        measure.setGraphic(new ImageView(objConfiguration.getStrColour()+"/linear_calculator.png"));
	measure.setId("caption");
        GridPane.setConstraints(measure, 0, 0);
        GridPane.setColumnSpan(measure, 2);
        measureGP.getChildren().add(measure);
 //
        Label valueMeasureLbl= new Label(objDictionaryAction.getWord("OLD")+" "+objDictionaryAction.getWord("LENGTH")+" :");
        //valueMeasureLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        measureGP.add(valueMeasureLbl, 0, 1);
        valueMeasureTxt = new TextField("1"){
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
        valueMeasureTxt.setTooltip(new Tooltip(objDictionaryAction.getWord("OLD")+" "+objDictionaryAction.getWord("LENGTH")));
        measureGP.add(valueMeasureTxt, 1, 1);

        Label fromMeasureLbl= new Label(objDictionaryAction.getWord("SOURCE")+" "+objDictionaryAction.getWord("UNIT")+" :");
        //fromMeasureLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        measureGP.add(fromMeasureLbl, 0, 2);
        fromMeasureCB = new ComboBox();
        fromMeasureCB.getItems().addAll("Yard","Foot","Inch","Meter","Milimeter");  
        fromMeasureCB.setValue("Inch");
        fromMeasureCB.setTooltip(new Tooltip(objDictionaryAction.getWord("SOURCE")+" "+objDictionaryAction.getWord("UNIT")));
        measureGP.add(fromMeasureCB, 1, 2);
        
        Label toMeasureLbl= new Label(objDictionaryAction.getWord("DESTINATION")+" "+objDictionaryAction.getWord("UNIT")+" :");
        //toMeasureLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        measureGP.add(toMeasureLbl, 0, 3);
        toMeasureCB = new ComboBox();
        toMeasureCB.getItems().addAll("Yard","Foot","Inch","Meter","Milimeter");
        toMeasureCB.setValue("Inch");
        toMeasureCB.setTooltip(new Tooltip(objDictionaryAction.getWord("DESTINATION")+" "+objDictionaryAction.getWord("UNIT")));
        measureGP.add(toMeasureCB, 1, 3);
        
        Label newMeasureLbl= new Label(objDictionaryAction.getWord("NEW")+" "+objDictionaryAction.getWord("LENGTH")+" :");
        newMeasureLbl.setId("result");
        measureGP.add(newMeasureLbl, 0, 4);
        newMeasueTxt = new Label("1");
        //newMeasueTxt.setId("result");
        measureGP.add(newMeasueTxt, 1, 4);
        
        valueMeasureTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase("0")){
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                        valueMeasureTxt.setText(t);
                    }else {
                        objFabricAction = new FabricAction();
                        double newValue = objFabricAction.convertMeasure(fromMeasureCB.getValue().toString(), toMeasureCB.getValue().toString(), Float.parseFloat(valueMeasureTxt.getText()));
                        newMeasueTxt.setText(Double.toString(newValue));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSMEASURE"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"mesaure text property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"mesaure text property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        fromMeasureCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase(toMeasureCB.getValue().toString())){
                        newMeasueTxt.setText(objDictionaryAction.getWord("ERRORMEASURE"));
                        lblStatus.setText(objDictionaryAction.getWord("ERRORMEASURE"));
                    }else{
                        objFabricAction = new FabricAction();
                        double newValue = objFabricAction.convertMeasure(t1, toMeasureCB.getValue().toString(), Float.parseFloat(valueMeasureTxt.getText()));
                        newMeasueTxt.setText(Double.toString(newValue));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSMEASURE"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"mesaure unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }  catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"mesaure unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        toMeasureCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase(fromMeasureCB.getValue().toString())){
                        newMeasueTxt.setText(objDictionaryAction.getWord("ERRORMEASURE"));
                        lblStatus.setText(objDictionaryAction.getWord("ERRORMEASURE"));
                    }else{
                        objFabricAction = new FabricAction();
                        double newValue = objFabricAction.convertMeasure(fromMeasureCB.getValue().toString(), t1, Float.parseFloat(valueMeasureTxt.getText()));
                        newMeasueTxt.setText(Double.toString(newValue));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSMEASURE"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"mesaure unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"mesaure unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        
        //---- weight ----//
        Label weight = new Label(objDictionaryAction.getWord("WEIGHTMEASURECONVERSION"));
        weight.setGraphic(new ImageView(objConfiguration.getStrColour()+"/weight_calculator.png"));
        weight.setId("caption");
        GridPane.setConstraints(weight, 0, 0);
        GridPane.setColumnSpan(weight, 2);
        weightGP.getChildren().add(weight);
        
        Label valueWeightLbl= new Label(objDictionaryAction.getWord("OLD")+" "+objDictionaryAction.getWord("WEIGHT")+" :");
        //valueWeightLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        weightGP.add(valueWeightLbl, 0, 1);
        valueWeightTxt = new TextField("1"){
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
        valueWeightTxt.setTooltip(new Tooltip(objDictionaryAction.getWord("OLD")+" "+objDictionaryAction.getWord("WEIGHT")));
        weightGP.add(valueWeightTxt, 1, 1);

        Label fromWeightLbl= new Label(objDictionaryAction.getWord("SOURCE")+" "+objDictionaryAction.getWord("UNIT")+" :");
        //fromWeightLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        weightGP.add(fromWeightLbl, 0, 2);
        fromWeightCB = new ComboBox();
        fromWeightCB.getItems().addAll("Ounce","Grain","Pound","Kilogram","Gram");  
        fromWeightCB.setValue("Gram");
        fromWeightCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWEIGHT")+" "+objDictionaryAction.getWord("SOURCE")+" "+objDictionaryAction.getWord("UNIT")));
        weightGP.add(fromWeightCB, 1, 2);
        
        Label toWeightLbl= new Label(objDictionaryAction.getWord("DESTINATION")+" "+objDictionaryAction.getWord("UNIT")+" :");
        //toWeightLbl.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        weightGP.add(toWeightLbl, 0, 3);
        toWeightCB = new ComboBox();
        toWeightCB.getItems().addAll("Ounce","Grain","Pound","Kilogram","Gram");  
        toWeightCB.setValue("Gram");
        toWeightCB.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPWEIGHT")+" "+objDictionaryAction.getWord("DESTINATION")+" "+objDictionaryAction.getWord("UNIT")));
        weightGP.add(toWeightCB, 1, 3);
        
        Label newWeightLbl= new Label(objDictionaryAction.getWord("NEW")+" "+objDictionaryAction.getWord("WEIGHT")+" :");
        newWeightLbl.setId("result");
        weightGP.add(newWeightLbl, 0, 4);
        newWeightTxt = new Label("1");
        newWeightTxt.setId("result");
        weightGP.add(newWeightTxt, 1, 4);
        
        valueWeightTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase("0")){
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                        valueWeightTxt.setText(t);
                    }else {
                        objFabricAction = new FabricAction();
                        double newValue = objFabricAction.convertWeight(fromWeightCB.getValue().toString(), toWeightCB.getValue().toString(), Float.parseFloat(valueWeightTxt.getText()));
                        newWeightTxt.setText(Double.toString(newValue));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSWEIGHT"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"Weight text property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"Weight text property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        fromWeightCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase(toWeightCB.getValue().toString())){
                        newWeightTxt.setText(objDictionaryAction.getWord("ERRORWEIGHT"));
                        lblStatus.setText(objDictionaryAction.getWord("ERRORWEIGHT"));
                    }else{
                        objFabricAction = new FabricAction();
                        double newValue = objFabricAction.convertWeight(t1, toWeightCB.getValue().toString(), Float.parseFloat(valueWeightTxt.getText()));
                        newWeightTxt.setText(Double.toString(newValue));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSWEIGHT"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"Weight unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }  catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"Weight unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        toWeightCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase(fromWeightCB.getValue().toString())){
                        newWeightTxt.setText(objDictionaryAction.getWord("ERRORWEIGHT"));
                        lblStatus.setText(objDictionaryAction.getWord("ERRORWEIGHT"));
                    }else{
                        objFabricAction = new FabricAction();
                        double newValue = objFabricAction.convertWeight(fromWeightCB.getValue().toString(), t1, Float.parseFloat(valueWeightTxt.getText()));
                        newWeightTxt.setText(Double.toString(newValue));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSWEIGHT"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"Weight unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"Weight unit property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        
        //===== GSM ======//
        Label gsm = new Label(objDictionaryAction.getWord("GRAMSQUAREMETERCONVERSION"));
        gsm.setGraphic(new ImageView(objConfiguration.getStrColour()+"/gsm_conversion.png"));
        gsm.setId("caption");
        GridPane.setConstraints(gsm, 0, 0);
        GridPane.setColumnSpan(gsm, 2);
        gsmGP.getChildren().add(gsm);
 
        Label lblEPI = new Label(objDictionaryAction.getWord("EPI")+"(in per inch):");
        //lblWeight.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        lblEPI.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEPI")));
        gsmGP.add(lblEPI, 0, 1);
        txtEPI = new TextField(){
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
        txtEPI.setText(Integer.toString(objConfiguration.getIntEPI()));        
        txtEPI.setPromptText(objDictionaryAction.getWord("EPI"));
        txtEPI.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPEPI")));
        gsmGP.add(txtEPI, 1, 1);
                
        Label lblPPI = new Label(objDictionaryAction.getWord("PPI")+" (in per inch):");
        //lblLength.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        lblPPI.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPPI")));
        gsmGP.add(lblPPI, 0, 2);
        txtPPI = new TextField(){
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
        txtPPI.setText(Integer.toString(objConfiguration.getIntPPI()));
        txtPPI.setPromptText(objDictionaryAction.getWord("PPI"));
        txtPPI.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPPPI")));
        gsmGP.add(txtPPI, 1, 2);
        
        Label lblCrimpWarp = new Label(objDictionaryAction.getWord("WARP")+" "+objDictionaryAction.getWord("YARNCRIMP")+" (%)");
        lblCrimpWarp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCRIMP")));
        gsmGP.add(lblCrimpWarp, 0, 3);
        txtCrimpWarp = new TextField(Integer.toString(objConfiguration.getIntWarpCrimp()));
        txtCrimpWarp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCRIMP")));
        gsmGP.add(txtCrimpWarp, 1, 3);
            
        Label lblCrimpWeft = new Label(objDictionaryAction.getWord("WEFT")+" "+objDictionaryAction.getWord("YARNCRIMP")+" (%)");
        lblCrimpWarp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCRIMP")));
        gsmGP.add(lblCrimpWeft, 0, 4);
        txtCrimpWeft = new TextField(Integer.toString(objConfiguration.getIntWeftCrimp()));
        txtCrimpWeft.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCRIMP")));
        gsmGP.add(txtCrimpWeft, 1, 4);                
        
        Label lblCountWarp = new Label(objDictionaryAction.getWord("WARP")+" "+objDictionaryAction.getWord("YARNCOUNT")+" :");
        lblCountWarp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOUNT")));
        gsmGP.add(lblCountWarp, 0, 5);
        txtCountWarp = new TextField(Integer.toString(objConfiguration.getIntWarpCount()));
        txtCountWarp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOUNT")));
        gsmGP.add(txtCountWarp, 1, 5);
        
        Label lblUnitWarp= new Label(objDictionaryAction.getWord("WARP")+" "+objDictionaryAction.getWord("YARNUNIT")+" :");
        //lblUnitWeft.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblUnitWarp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNUNIT")));
        gsmGP.add(lblUnitWarp, 0, 6);
        cbUnitWarp = new ComboBox();
        cbUnitWarp.getItems().addAll("Tex","dTex","K tex","Denier (Td)","New Metric (Nm)","Grains/Yard","Woollen (Aberdeen) (Ta)","Woollen (US Grain)","Asbestos (American) (NaA)","Asbestos (English) (NeA)","Cotton bump Yarn (NB)","Glass (UK & USA)","Linen (Set or Dry Spun) (NeL)","Spun Silk (Ns)","Woollen (American Cut) (Nac)","Woollen (American run) (Nar)","Woollen (Yarkshire) (Ny)","Woollen (Worsted) (New)","Linen, Hemp, Jute (Tj)","Micronaire (Mic)","Yards Per Pound (YPP)","English Worsted Count (NeK)","English Cotton (NeC)","New English (Ne)","Numero en puntos (Np)");  
        cbUnitWarp.setValue(objConfiguration.getStrWarpUnit());
        cbUnitWarp.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNUNIT")));
        gsmGP.add(cbUnitWarp, 1, 6);
        
        Label lblCountWeft = new Label(objDictionaryAction.getWord("WEFT")+" "+objDictionaryAction.getWord("YARNCOUNT")+" :");
        lblCountWeft.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOUNT")));
        gsmGP.add(lblCountWeft, 0, 7);
        txtCountWeft = new TextField(Integer.toString(objConfiguration.getIntWeftCount()));
        txtCountWeft.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNCOUNT")));
        gsmGP.add(txtCountWeft, 1, 7);                
        
        Label lblUnitWeft= new Label(objDictionaryAction.getWord("WEFT")+" "+objDictionaryAction.getWord("YARNUNIT")+" :");
        //lblUnitWeft.setGraphic(new ImageView(objConfiguration.getStrColour()+"/icontooltip.png"));
        lblUnitWeft.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNUNIT")));
        gsmGP.add(lblUnitWeft, 0, 8);
        cbUnitWeft = new ComboBox();
        cbUnitWeft.getItems().addAll("Tex","dTex","K tex","Denier (Td)","New Metric (Nm)","Grains/Yard","Woollen (Aberdeen) (Ta)","Woollen (US Grain)","Asbestos (American) (NaA)","Asbestos (English) (NeA)","Cotton bump Yarn (NB)","Glass (UK & USA)","Linen (Set or Dry Spun) (NeL)","Spun Silk (Ns)","Woollen (American Cut) (Nac)","Woollen (American run) (Nar)","Woollen (Yarkshire) (Ny)","Woollen (Worsted) (New)","Linen, Hemp, Jute (Tj)","Micronaire (Mic)","Yards Per Pound (YPP)","English Worsted Count (NeK)","English Cotton (NeC)","New English (Ne)","Numero en puntos (Np)");  
        cbUnitWeft.setValue(objConfiguration.getStrWeftUnit());
        cbUnitWeft.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPYARNUNIT")));
        gsmGP.add(cbUnitWeft, 1, 8);

        Label lblGSM = new Label(objDictionaryAction.getWord("GSM")+" :");
        lblGSM.setId("result");
        gsmGP.add(lblGSM, 0, 9);
        txtGSM = new Label();
        txtGSM.setId("result");
        gsmGP.add(txtGSM, 1, 9);
        
        try {
            objFabricAction = new FabricAction();
        } catch (SQLException ex) {
            new Logging("SEVERE",UtilityView.class.getName(),"find gsm",ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
        txtGSM.setText(Double.toString(objFabricAction.calculateGSM(objConfiguration)));
                        
        Label lblNote = new Label(objDictionaryAction.getWord("TOOLTIPGSM"));
        lblNote.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPGSM")));
        lblNote.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        gsmGP.add(lblNote, 0, 10, 2, 1);
        
        txtEPI.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase("0") || t1.trim().isEmpty()){
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                        txtEPI.setText(t);
                    }else {
                        if(txtPPI.getText().equalsIgnoreCase("0") || txtPPI.getText().trim().isEmpty())
                            txtPPI.setText(Integer.toString(1));
                        if(txtCountWarp.getText().equalsIgnoreCase("0") || txtCountWarp.getText().trim().isEmpty())
                            txtCountWarp.setText(Integer.toString(1));
                        if(txtCountWeft.getText().equalsIgnoreCase("0") || txtCountWeft.getText().trim().isEmpty())
                            txtCountWeft.setText(Integer.toString(1));
                        if(txtCrimpWarp.getText().equalsIgnoreCase("0") || txtCrimpWarp.getText().trim().isEmpty())
                            txtCrimpWarp.setText(Integer.toString(1));
                        if(txtCrimpWeft.getText().equalsIgnoreCase("0") || txtCrimpWeft.getText().trim().isEmpty())
                            txtCrimpWeft.setText(Integer.toString(1));
                        
                        objConfiguration.setIntEPI(Integer.parseInt(txtEPI.getText()));
                        objConfiguration.setIntPPI(Integer.parseInt(txtPPI.getText()));
                        objConfiguration.setIntWarpCount(Integer.parseInt(txtCountWarp.getText()));
                        objConfiguration.setIntWeftCount(Integer.parseInt(txtCountWeft.getText()));
                        objConfiguration.setIntWarpCrimp(Integer.parseInt(txtCrimpWarp.getText()));
                        objConfiguration.setIntWeftCrimp(Integer.parseInt(txtCrimpWeft.getText()));
                        objConfiguration.setStrWarpUnit(cbUnitWarp.getValue().toString());
                        objConfiguration.setStrWeftUnit(cbUnitWeft.getValue().toString());
                        objFabricAction = new FabricAction();
                        txtGSM.setText(Double.toString(objFabricAction.calculateGSM(objConfiguration)));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSGSM"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        txtPPI.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase("0") || t1.trim().isEmpty()){
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                        txtPPI.setText(t);
                    }else {
                        if(txtEPI.getText().equalsIgnoreCase("0") || txtEPI.getText().trim().isEmpty())
                            txtEPI.setText(Integer.toString(1));
                        if(txtCountWarp.getText().equalsIgnoreCase("0") || txtCountWarp.getText().trim().isEmpty())
                            txtCountWarp.setText(Integer.toString(1));
                        if(txtCountWeft.getText().equalsIgnoreCase("0") || txtCountWeft.getText().trim().isEmpty())
                            txtCountWeft.setText(Integer.toString(1));
                        if(txtCrimpWarp.getText().equalsIgnoreCase("0") || txtCrimpWarp.getText().trim().isEmpty())
                            txtCrimpWarp.setText(Integer.toString(1));
                        if(txtCrimpWeft.getText().equalsIgnoreCase("0") || txtCrimpWeft.getText().trim().isEmpty())
                            txtCrimpWeft.setText(Integer.toString(1));
                        
                        objConfiguration.setIntEPI(Integer.parseInt(txtEPI.getText()));
                        objConfiguration.setIntPPI(Integer.parseInt(txtPPI.getText()));
                        objConfiguration.setIntWarpCount(Integer.parseInt(txtCountWarp.getText()));
                        objConfiguration.setIntWeftCount(Integer.parseInt(txtCountWeft.getText()));
                        objConfiguration.setIntWarpCrimp(Integer.parseInt(txtCrimpWarp.getText()));
                        objConfiguration.setIntWeftCrimp(Integer.parseInt(txtCrimpWeft.getText()));
                        objConfiguration.setStrWarpUnit(cbUnitWarp.getValue().toString());
                        objConfiguration.setStrWeftUnit(cbUnitWeft.getValue().toString());
                        objFabricAction = new FabricAction();
                        txtGSM.setText(Double.toString(objFabricAction.calculateGSM(objConfiguration)));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSGSM"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        txtCountWarp.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase("0") || t1.trim().isEmpty()){
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                        txtCountWarp.setText(t);
                    }else {
                        if(txtEPI.getText().equalsIgnoreCase("0") || txtEPI.getText().trim().isEmpty())
                            txtEPI.setText(Integer.toString(1));
                        if(txtPPI.getText().equalsIgnoreCase("0") || txtPPI.getText().trim().isEmpty())
                            txtPPI.setText(Integer.toString(1));
                        if(txtCountWeft.getText().equalsIgnoreCase("0") || txtCountWeft.getText().trim().isEmpty())
                            txtCountWeft.setText(Integer.toString(1));
                        if(txtCrimpWarp.getText().equalsIgnoreCase("0") || txtCrimpWarp.getText().trim().isEmpty())
                            txtCrimpWarp.setText(Integer.toString(1));
                        if(txtCrimpWeft.getText().equalsIgnoreCase("0") || txtCrimpWeft.getText().trim().isEmpty())
                            txtCrimpWeft.setText(Integer.toString(1));
                        
                        objConfiguration.setIntEPI(Integer.parseInt(txtEPI.getText()));
                        objConfiguration.setIntPPI(Integer.parseInt(txtPPI.getText()));
                        objConfiguration.setIntWarpCount(Integer.parseInt(txtCountWarp.getText()));
                        objConfiguration.setIntWeftCount(Integer.parseInt(txtCountWeft.getText()));
                        objConfiguration.setIntWarpCrimp(Integer.parseInt(txtCrimpWarp.getText()));
                        objConfiguration.setIntWeftCrimp(Integer.parseInt(txtCrimpWeft.getText()));
                        objConfiguration.setStrWarpUnit(cbUnitWarp.getValue().toString());
                        objConfiguration.setStrWeftUnit(cbUnitWeft.getValue().toString());
                        objFabricAction = new FabricAction();
                        txtGSM.setText(Double.toString(objFabricAction.calculateGSM(objConfiguration)));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSGSM"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        txtCountWeft.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase("0") || t1.trim().isEmpty()){
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                        txtCountWeft.setText(t);
                    }else {
                        if(txtEPI.getText().equalsIgnoreCase("0") || txtEPI.getText().trim().isEmpty())
                            txtEPI.setText(Integer.toString(1));
                        if(txtPPI.getText().equalsIgnoreCase("0") || txtPPI.getText().trim().isEmpty())
                            txtPPI.setText(Integer.toString(1));
                        if(txtCountWarp.getText().equalsIgnoreCase("0") || txtCountWarp.getText().trim().isEmpty())
                            txtCountWarp.setText(Integer.toString(1));
                        if(txtCrimpWarp.getText().equalsIgnoreCase("0") || txtCrimpWarp.getText().trim().isEmpty())
                            txtCrimpWarp.setText(Integer.toString(1));
                        if(txtCrimpWeft.getText().equalsIgnoreCase("0") || txtCrimpWeft.getText().trim().isEmpty())
                            txtCrimpWeft.setText(Integer.toString(1));
                        
                        objConfiguration.setIntEPI(Integer.parseInt(txtEPI.getText()));
                        objConfiguration.setIntPPI(Integer.parseInt(txtPPI.getText()));
                        objConfiguration.setIntWarpCount(Integer.parseInt(txtCountWarp.getText()));
                        objConfiguration.setIntWeftCount(Integer.parseInt(txtCountWeft.getText()));
                        objConfiguration.setIntWarpCrimp(Integer.parseInt(txtCrimpWarp.getText()));
                        objConfiguration.setIntWeftCrimp(Integer.parseInt(txtCrimpWeft.getText()));
                        objConfiguration.setStrWarpUnit(cbUnitWarp.getValue().toString());
                        objConfiguration.setStrWeftUnit(cbUnitWeft.getValue().toString());
                        objFabricAction = new FabricAction();
                        txtGSM.setText(Double.toString(objFabricAction.calculateGSM(objConfiguration)));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSGSM"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        txtCrimpWarp.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase("0") || t1.trim().isEmpty()){
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                        txtCrimpWarp.setText(t);
                    }else {
                        if(txtEPI.getText().equalsIgnoreCase("0") || txtEPI.getText().trim().isEmpty())
                            txtEPI.setText(Integer.toString(1));
                        if(txtPPI.getText().equalsIgnoreCase("0") || txtPPI.getText().trim().isEmpty())
                            txtPPI.setText(Integer.toString(1));
                        if(txtCountWarp.getText().equalsIgnoreCase("0") || txtCountWarp.getText().trim().isEmpty())
                            txtCountWarp.setText(Integer.toString(1));
                        if(txtCountWeft.getText().equalsIgnoreCase("0") || txtCountWeft.getText().trim().isEmpty())
                            txtCountWeft.setText(Integer.toString(1));
                        if(txtCrimpWeft.getText().equalsIgnoreCase("0") || txtCrimpWeft.getText().trim().isEmpty())
                            txtCrimpWeft.setText(Integer.toString(1));
                        
                        objConfiguration.setIntEPI(Integer.parseInt(txtEPI.getText()));
                        objConfiguration.setIntPPI(Integer.parseInt(txtPPI.getText()));
                        objConfiguration.setIntWarpCount(Integer.parseInt(txtCountWarp.getText()));
                        objConfiguration.setIntWeftCount(Integer.parseInt(txtCountWeft.getText()));
                        objConfiguration.setIntWarpCrimp(Integer.parseInt(txtCrimpWarp.getText()));
                        objConfiguration.setIntWeftCrimp(Integer.parseInt(txtCrimpWeft.getText()));
                        objConfiguration.setStrWarpUnit(cbUnitWarp.getValue().toString());
                        objConfiguration.setStrWeftUnit(cbUnitWeft.getValue().toString());
                        objFabricAction = new FabricAction();
                        txtGSM.setText(Double.toString(objFabricAction.calculateGSM(objConfiguration)));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSGSM"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        txtCrimpWeft.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if(t1.equalsIgnoreCase("0") || t1.trim().isEmpty()){
                        lblStatus.setText(objDictionaryAction.getWord("ZEROVALUE"));
                        txtCrimpWarp.setText(t);
                    }else {
                        if(txtEPI.getText().equalsIgnoreCase("0") || txtEPI.getText().trim().isEmpty())
                            txtEPI.setText(Integer.toString(1));
                        if(txtPPI.getText().equalsIgnoreCase("0") || txtPPI.getText().trim().isEmpty())
                            txtPPI.setText(Integer.toString(1));
                        if(txtCountWarp.getText().equalsIgnoreCase("0") || txtCountWarp.getText().trim().isEmpty())
                            txtCountWarp.setText(Integer.toString(1));
                        if(txtCountWeft.getText().equalsIgnoreCase("0") || txtCountWeft.getText().trim().isEmpty())
                            txtCountWeft.setText(Integer.toString(1));
                        if(txtCrimpWarp.getText().equalsIgnoreCase("0") || txtCrimpWarp.getText().trim().isEmpty())
                            txtCrimpWarp.setText(Integer.toString(1));
                        
                        objConfiguration.setIntEPI(Integer.parseInt(txtEPI.getText()));
                        objConfiguration.setIntPPI(Integer.parseInt(txtPPI.getText()));
                        objConfiguration.setIntWarpCount(Integer.parseInt(txtCountWarp.getText()));
                        objConfiguration.setIntWeftCount(Integer.parseInt(txtCountWeft.getText()));
                        objConfiguration.setIntWarpCrimp(Integer.parseInt(txtCrimpWarp.getText()));
                        objConfiguration.setIntWeftCrimp(Integer.parseInt(txtCrimpWeft.getText()));
                        objConfiguration.setStrWarpUnit(cbUnitWarp.getValue().toString());
                        objConfiguration.setStrWeftUnit(cbUnitWeft.getValue().toString());
                        objFabricAction = new FabricAction();
                        txtGSM.setText(Double.toString(objFabricAction.calculateGSM(objConfiguration)));
                        lblStatus.setText(objDictionaryAction.getWord("SUCCESSGSM"));
                    }
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        cbUnitWarp.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                     if(txtEPI.getText().equalsIgnoreCase("0") || txtEPI.getText().trim().isEmpty())
                        txtEPI.setText(Integer.toString(1));
                    if(txtPPI.getText().equalsIgnoreCase("0") || txtPPI.getText().trim().isEmpty())
                        txtPPI.setText(Integer.toString(1));
                    if(txtCountWarp.getText().equalsIgnoreCase("0") || txtCountWarp.getText().trim().isEmpty())
                        txtCountWarp.setText(Integer.toString(1));
                    if(txtCountWeft.getText().equalsIgnoreCase("0") || txtCountWeft.getText().trim().isEmpty())
                        txtCountWeft.setText(Integer.toString(1));
                    if(txtCrimpWarp.getText().equalsIgnoreCase("0") || txtCrimpWarp.getText().trim().isEmpty())
                        txtCrimpWarp.setText(Integer.toString(1));
                    if(txtCrimpWeft.getText().equalsIgnoreCase("0") || txtCrimpWeft.getText().trim().isEmpty())
                        txtCrimpWeft.setText(Integer.toString(1));
                    
                    objConfiguration.setIntEPI(Integer.parseInt(txtEPI.getText()));
                    objConfiguration.setIntPPI(Integer.parseInt(txtPPI.getText()));
                    objConfiguration.setIntWarpCount(Integer.parseInt(txtCountWarp.getText()));
                    objConfiguration.setIntWeftCount(Integer.parseInt(txtCountWeft.getText()));
                    objConfiguration.setIntWarpCrimp(Integer.parseInt(txtCrimpWarp.getText()));
                    objConfiguration.setIntWeftCrimp(Integer.parseInt(txtCrimpWeft.getText()));
                    objConfiguration.setStrWarpUnit(cbUnitWarp.getValue().toString());
                    objConfiguration.setStrWeftUnit(cbUnitWeft.getValue().toString());
                    objFabricAction = new FabricAction();
                    txtGSM.setText(Double.toString(objFabricAction.calculateGSM(objConfiguration)));
                    lblStatus.setText(objDictionaryAction.getWord("SUCCESSGSM"));
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
        cbUnitWeft.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                     if(txtEPI.getText().equalsIgnoreCase("0") || txtEPI.getText().trim().isEmpty())
                        txtEPI.setText(Integer.toString(1));
                    if(txtPPI.getText().equalsIgnoreCase("0") || txtPPI.getText().trim().isEmpty())
                        txtPPI.setText(Integer.toString(1));
                    if(txtCountWarp.getText().equalsIgnoreCase("0") || txtCountWarp.getText().trim().isEmpty())
                        txtCountWarp.setText(Integer.toString(1));
                    if(txtCountWeft.getText().equalsIgnoreCase("0") || txtCountWeft.getText().trim().isEmpty())
                        txtCountWeft.setText(Integer.toString(1));
                    if(txtCrimpWarp.getText().equalsIgnoreCase("0") || txtCrimpWarp.getText().trim().isEmpty())
                        txtCrimpWarp.setText(Integer.toString(1));
                    if(txtCrimpWeft.getText().equalsIgnoreCase("0") || txtCrimpWeft.getText().trim().isEmpty())
                        txtCrimpWeft.setText(Integer.toString(1));
                    
                    objConfiguration.setIntEPI(Integer.parseInt(txtEPI.getText()));
                    objConfiguration.setIntPPI(Integer.parseInt(txtPPI.getText()));
                    objConfiguration.setIntWarpCount(Integer.parseInt(txtCountWarp.getText()));
                    objConfiguration.setIntWeftCount(Integer.parseInt(txtCountWeft.getText()));
                    objConfiguration.setIntWarpCrimp(Integer.parseInt(txtCrimpWarp.getText()));
                    objConfiguration.setIntWeftCrimp(Integer.parseInt(txtCrimpWeft.getText()));
                    objConfiguration.setStrWarpUnit(cbUnitWarp.getValue().toString());
                    objConfiguration.setStrWeftUnit(cbUnitWeft.getValue().toString());
                    objFabricAction = new FabricAction();
                    txtGSM.setText(Double.toString(objFabricAction.calculateGSM(objConfiguration)));
                    lblStatus.setText(objDictionaryAction.getWord("SUCCESSGSM"));
                } catch (SQLException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                } catch (UnsupportedOperationException ex) {
                    new Logging("SEVERE",UtilityView.class.getName(),"weight property change",ex);
                    lblStatus.setText(objDictionaryAction.getWord("ERROR"));
                }
            }
        });
// --- Color Conversion --- //
        final Color_Space_Converter c=new Color_Space_Converter();
        final Color_Space_Converter.ColorSpaceConverter csc=c.new  ColorSpaceConverter();
        
        // ToggleGroup Radio Buttons
        final ToggleGroup colorTG = new ToggleGroup();
        final RadioButton hexRB = new RadioButton(objDictionaryAction.getWord("HEXCODE"));
        hexRB.setToggleGroup(colorTG);
        colorGP.add(hexRB, 0, 0, 2, 1);
        final RadioButton rgbRB = new RadioButton(objDictionaryAction.getWord("RGB"));
        rgbRB.setToggleGroup(colorTG);
        colorGP.add(rgbRB, 0, 2, 2, 1);
        final RadioButton hsbRB = new RadioButton(objDictionaryAction.getWord("HSB"));
        hsbRB.setToggleGroup(colorTG);
        colorGP.add(hsbRB, 0, 6, 2, 1);
        final RadioButton labRB = new RadioButton(objDictionaryAction.getWord("LAB"));
        labRB.setToggleGroup(colorTG);
        colorGP.add(labRB, 0, 10, 2, 1);
        hexRB.setSelected(true);
        
        Label lblHex=new Label(objDictionaryAction.getWord("HEXCODE"));
        final TextField txtHex=new TextField();
        txtHex.setPromptText(objDictionaryAction.getWord("HEXCODE"));
        colorGP.add(lblHex, 0, 1, 1, 1);
        colorGP.add(txtHex, 1, 1, 1, 1);
        
        Label lblRed=new Label(objDictionaryAction.getWord("RED"));
        final TextField txtRed=new TextField();
        txtRed.setPromptText(objDictionaryAction.getWord("RED"));
        Label lblGreen=new Label(objDictionaryAction.getWord("GREEN"));
        final TextField txtGreen=new TextField();
        txtGreen.setPromptText(objDictionaryAction.getWord("GREEN"));
        Label lblBlue=new Label(objDictionaryAction.getWord("BLUE"));
        final TextField txtBlue=new TextField();
        txtBlue.setPromptText(objDictionaryAction.getWord("BLUE"));
        colorGP.add(lblRed, 0, 3, 1, 1);
        colorGP.add(txtRed, 1, 3, 1, 1);
        colorGP.add(lblGreen, 0, 4, 1, 1);
        colorGP.add(txtGreen, 1, 4, 1, 1);
        colorGP.add(lblBlue, 0, 5, 1, 1);
        colorGP.add(txtBlue, 1, 5, 1, 1);
        
        Label lblHue=new Label(objDictionaryAction.getWord("HUE"));
        final TextField txtHue=new TextField();
        txtHue.setPromptText(objDictionaryAction.getWord("HUE"));
        Label lblSaturation=new Label(objDictionaryAction.getWord("SATURATION"));
        final TextField txtSaturation=new TextField();
        txtSaturation.setPromptText(objDictionaryAction.getWord("SATURATION"));
        Label lblBrightness=new Label(objDictionaryAction.getWord("BRIGHTNESS"));
        final TextField txtBrightness=new TextField();
        txtBrightness.setPromptText(objDictionaryAction.getWord("BRIGHTNESS"));
        colorGP.add(lblHue, 0, 7, 1, 1);
        colorGP.add(txtHue, 1, 7, 1, 1);
        colorGP.add(lblSaturation, 0, 8, 1, 1);
        colorGP.add(txtSaturation, 1, 8, 1, 1);
        colorGP.add(lblBrightness, 0, 9, 1, 1);
        colorGP.add(txtBrightness, 1, 9, 1, 1);
        
        Label lblL=new Label(objDictionaryAction.getWord("L PARAM"));
        final TextField txtL=new TextField();
        txtL.setPromptText(objDictionaryAction.getWord("L"));
        Label lblA=new Label(objDictionaryAction.getWord("A PARAM"));
        final TextField txtA=new TextField();
        txtA.setPromptText(objDictionaryAction.getWord("A"));
        Label lblB=new Label(objDictionaryAction.getWord("B PARAM"));
        final TextField txtB=new TextField();
        txtB.setPromptText(objDictionaryAction.getWord("B"));
        colorGP.add(lblL, 0, 11, 1, 1);
        colorGP.add(txtL, 1, 11, 1, 1);
        colorGP.add(lblA, 0, 12, 1, 1);
        colorGP.add(txtA, 1, 12, 1, 1);
        colorGP.add(lblB, 0, 13, 1, 1);
        colorGP.add(txtB, 1, 13, 1, 1);
        
        txtHex.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(hexRB.isSelected()){
                    if(isHexCodeValid(t1)){
                        int[] rgb=hex2rgb(t1);
                        txtRed.setText(String.valueOf(rgb[0]));
                        txtGreen.setText(String.valueOf(rgb[1]));
                        txtBlue.setText(String.valueOf(rgb[2]));
                        double[] hsb=hex2hsb(t1);
                        txtHue.setText(String.valueOf(hsb[0]));
                        txtSaturation.setText(String.valueOf(hsb[1]));
                        txtBrightness.setText(String.valueOf(hsb[2]));
                        double[] lab=csc.RGBtoLAB(rgb);
                        txtL.setText(String.valueOf(lab[0]));
                        txtA.setText(String.valueOf(lab[1]));
                        txtB.setText(String.valueOf(lab[2]));
                    }
                }
            }
        });
        txtRed.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(rgbRB.isSelected()){
                    if(t1!=null && t1.matches(regExInt) && txtGreen.getText().matches(regExInt) && txtBlue.getText().matches(regExInt)){
                        if(Integer.parseInt(t1)>=0 && Integer.parseInt(t1)<=255 && Integer.parseInt(txtGreen.getText())>=0 && Integer.parseInt(txtGreen.getText())<=255 && Integer.parseInt(txtBlue.getText())>=0 && Integer.parseInt(txtBlue.getText())<=255){
                            int[] rgb=new int[]{Integer.parseInt(t1), Integer.parseInt(txtGreen.getText()), Integer.parseInt(txtBlue.getText())};
                            txtHex.setText(rgb2hex(rgb).toUpperCase());
                            double[] hsb=hex2hsb(txtHex.getText());
                            txtHue.setText(String.valueOf(hsb[0]));
                            txtSaturation.setText(String.valueOf(hsb[1]));
                            txtBrightness.setText(String.valueOf(hsb[2]));
                            double[] lab=csc.RGBtoLAB(rgb);
                            txtL.setText(String.valueOf(lab[0]));
                            txtA.setText(String.valueOf(lab[1]));
                            txtB.setText(String.valueOf(lab[2]));
                        }
                    }
                }
            }
        });
        txtGreen.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(rgbRB.isSelected()){
                    if(t1!=null && txtRed.getText().matches(regExInt) && t1.matches(regExInt) && txtBlue.getText().matches(regExInt)){
                        if(Integer.parseInt(txtRed.getText())>=0 && Integer.parseInt(txtRed.getText())<=255 && Integer.parseInt(t1)>=0 && Integer.parseInt(t1)<=255 && Integer.parseInt(txtBlue.getText())>=0 && Integer.parseInt(txtBlue.getText())<=255){
                            int[] rgb=new int[]{Integer.parseInt(txtRed.getText()), Integer.parseInt(t1), Integer.parseInt(txtBlue.getText())};
                            txtHex.setText(rgb2hex(rgb).toUpperCase());
                            double[] hsb=hex2hsb(txtHex.getText());
                            txtHue.setText(String.valueOf(hsb[0]));
                            txtSaturation.setText(String.valueOf(hsb[1]));
                            txtBrightness.setText(String.valueOf(hsb[2]));
                            double[] lab=csc.RGBtoLAB(rgb);
                            txtL.setText(String.valueOf(lab[0]));
                            txtA.setText(String.valueOf(lab[1]));
                            txtB.setText(String.valueOf(lab[2]));
                        }
                    }
                }
            }
        });
        txtBlue.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(rgbRB.isSelected()){
                    if(t1!=null && txtRed.getText().matches(regExInt) && txtGreen.getText().matches(regExInt) && t1.matches(regExInt)){
                        if(Integer.parseInt(txtRed.getText())>=0 && Integer.parseInt(txtRed.getText())<=255 && Integer.parseInt(txtGreen.getText())>=0 && Integer.parseInt(txtGreen.getText())<=255 && Integer.parseInt(t1)>=0 && Integer.parseInt(t1)<=255){
                            int[] rgb=new int[]{Integer.parseInt(txtRed.getText()), Integer.parseInt(txtGreen.getText()), Integer.parseInt(t1)};
                            txtHex.setText(rgb2hex(rgb).toUpperCase());
                            double[] hsb=hex2hsb(txtHex.getText());
                            txtHue.setText(String.valueOf(hsb[0]));
                            txtSaturation.setText(String.valueOf(hsb[1]));
                            txtBrightness.setText(String.valueOf(hsb[2]));
                            double[] lab=csc.RGBtoLAB(rgb);
                            txtL.setText(String.valueOf(lab[0]));
                            txtA.setText(String.valueOf(lab[1]));
                            txtB.setText(String.valueOf(lab[2]));
                        }
                    }
                }
            }
        });
        txtHue.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(hsbRB.isSelected()){
                    if(t1!=null && t1.matches(regExDouble) && txtSaturation.getText().matches(regExDouble) && txtBrightness.getText().matches(regExDouble) && (Double.parseDouble(t1)<0.0 || Double.parseDouble(t1)>=0.0) && Double.parseDouble(txtSaturation.getText())>=0.0 && Double.parseDouble(txtSaturation.getText())<=1.0 && Double.parseDouble(txtBrightness.getText())>=0.0 && Double.parseDouble(txtBrightness.getText())<=1.0){
                        double[] hsb=new double[]{Double.parseDouble(t1), Double.parseDouble(txtSaturation.getText()), Double.parseDouble(txtBrightness.getText())};
                        txtHex.setText(hsb2hex(hsb).toUpperCase());
                        int[] rgb=hex2rgb(txtHex.getText());
                        txtRed.setText(String.valueOf(rgb[0]));
                        txtGreen.setText(String.valueOf(rgb[1]));
                        txtBlue.setText(String.valueOf(rgb[2]));
                        double[] lab=csc.RGBtoLAB(rgb);
                        txtL.setText(String.valueOf(lab[0]));
                        txtA.setText(String.valueOf(lab[1]));
                        txtB.setText(String.valueOf(lab[2]));
                    }
                }
            }
        });
        txtSaturation.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(hsbRB.isSelected()){
                    if(t1!=null && txtHue.getText().matches(regExDouble) && t1.matches(regExDouble) && txtBrightness.getText().matches(regExDouble) && (Double.parseDouble(txtHue.getText())<0.0 || Double.parseDouble(txtHue.getText())>=0.0) && Double.parseDouble(t1)>=0.0 && Double.parseDouble(t1)<=1.0 && Double.parseDouble(txtBrightness.getText())>=0.0 && Double.parseDouble(txtBrightness.getText())<=1.0){
                        double[] hsb=new double[]{Double.parseDouble(txtHue.getText()), Double.parseDouble(t1), Double.parseDouble(txtBrightness.getText())};
                        txtHex.setText(hsb2hex(hsb).toUpperCase());
                        int[] rgb=hex2rgb(txtHex.getText());
                        txtRed.setText(String.valueOf(rgb[0]));
                        txtGreen.setText(String.valueOf(rgb[1]));
                        txtBlue.setText(String.valueOf(rgb[2]));
                        double[] lab=csc.RGBtoLAB(rgb);
                        txtL.setText(String.valueOf(lab[0]));
                        txtA.setText(String.valueOf(lab[1]));
                        txtB.setText(String.valueOf(lab[2]));
                    }
                }
            }
        });
        txtBrightness.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(hsbRB.isSelected()){
                    if(t1!=null && txtHue.getText().matches(regExDouble) && txtSaturation.getText().matches(regExDouble) && t1.matches(regExDouble) && (Double.parseDouble(txtHue.getText())<0.0 || Double.parseDouble(txtHue.getText())>=0.0) && Double.parseDouble(txtSaturation.getText())>=0.0 && Double.parseDouble(txtSaturation.getText())<=1.0 && Double.parseDouble(t1)>=0.0 && Double.parseDouble(t1)<=1.0){
                        double[] hsb=new double[]{Double.parseDouble(txtHue.getText()), Double.parseDouble(txtSaturation.getText()), Double.parseDouble(t1)};
                        txtHex.setText(hsb2hex(hsb).toUpperCase());
                        int[] rgb=hex2rgb(txtHex.getText());
                        txtRed.setText(String.valueOf(rgb[0]));
                        txtGreen.setText(String.valueOf(rgb[1]));
                        txtBlue.setText(String.valueOf(rgb[2]));
                        double[] lab=csc.RGBtoLAB(rgb);
                        txtL.setText(String.valueOf(lab[0]));
                        txtA.setText(String.valueOf(lab[1]));
                        txtB.setText(String.valueOf(lab[2]));
                    }
                }
            }
        });
        txtL.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(labRB.isSelected()){
                    if(t1!=null && t1.matches(regExDouble) && txtA.getText().matches(regExDouble) && txtB.getText().matches(regExDouble) && (Double.parseDouble(t1)>=0.0 || Double.parseDouble(t1)<=100.0) && Double.parseDouble(txtA.getText())>=-128.0 && Double.parseDouble(txtA.getText())<=127.0 && Double.parseDouble(txtB.getText())>=-128.0 && Double.parseDouble(txtB.getText())<=127.0){
                        double[] lab=new double[]{Double.parseDouble(t1), Double.parseDouble(txtA.getText()), Double.parseDouble(txtB.getText())};
                        int[] rgb=csc.LABtoRGB(lab);
                        txtRed.setText(String.valueOf(rgb[0]));
                        txtGreen.setText(String.valueOf(rgb[1]));
                        txtBlue.setText(String.valueOf(rgb[2]));
                        txtHex.setText(rgb2hex(rgb).toUpperCase());
                        double[] hsb=hex2hsb(txtHex.getText());
                        txtHue.setText(String.valueOf(hsb[0]));
                        txtSaturation.setText(String.valueOf(hsb[1]));
                        txtBrightness.setText(String.valueOf(hsb[2]));
                    }
                }
            }
        });
        txtA.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(labRB.isSelected()){
                    if(t1!=null && txtL.getText().matches(regExDouble) && t1.matches(regExDouble) && txtB.getText().matches(regExDouble) && (Double.parseDouble(txtL.getText())>=0.0 || Double.parseDouble(txtL.getText())<=100.0) && Double.parseDouble(t1)>=-128.0 && Double.parseDouble(t1)<=127.0 && Double.parseDouble(txtB.getText())>=-128.0 && Double.parseDouble(txtB.getText())<=127.0){
                        double[] lab=new double[]{Double.parseDouble(txtL.getText()), Double.parseDouble(t1), Double.parseDouble(txtB.getText())};
                        int[] rgb=csc.LABtoRGB(lab);
                        txtRed.setText(String.valueOf(rgb[0]));
                        txtGreen.setText(String.valueOf(rgb[1]));
                        txtBlue.setText(String.valueOf(rgb[2]));
                        txtHex.setText(rgb2hex(rgb).toUpperCase());
                        double[] hsb=hex2hsb(txtHex.getText());
                        txtHue.setText(String.valueOf(hsb[0]));
                        txtSaturation.setText(String.valueOf(hsb[1]));
                        txtBrightness.setText(String.valueOf(hsb[2]));
                    }
                }
            }
        });
        txtB.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(labRB.isSelected()){
                    if(t1!=null && txtL.getText().matches(regExDouble) && txtA.getText().matches(regExDouble) && t1.matches(regExDouble) && (Double.parseDouble(txtL.getText())>=0.0 || Double.parseDouble(txtL.getText())<=100.0) && Double.parseDouble(txtA.getText())>=-128.0 && Double.parseDouble(txtA.getText())<=127.0 && Double.parseDouble(t1)>=-128.0 && Double.parseDouble(t1)<=127.0){
                        double[] lab=new double[]{Double.parseDouble(txtL.getText()), Double.parseDouble(txtA.getText()), Double.parseDouble(t1)};
                        int[] rgb=csc.LABtoRGB(lab);
                        txtRed.setText(String.valueOf(rgb[0]));
                        txtGreen.setText(String.valueOf(rgb[1]));
                        txtBlue.setText(String.valueOf(rgb[2]));
                        txtHex.setText(rgb2hex(rgb).toUpperCase());
                        double[] hsb=hex2hsb(txtHex.getText());
                        txtHue.setText(String.valueOf(hsb[0]));
                        txtSaturation.setText(String.valueOf(hsb[1]));
                        txtBrightness.setText(String.valueOf(hsb[2]));
                    }
                }
            }
        });
        
        //----------------------------
        GridPane bodyContainer = new GridPane();
        bodyContainer.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        bodyContainer.setId("container");
        
        Label caption = new Label(objDictionaryAction.getWord("CONVERSION")+" "+objDictionaryAction.getWord("UTILITY"));
        caption.setId("caption");
        bodyContainer.add(caption, 0, 0, 1, 1);
        
        bodyContainer.add(tabPane, 0, 1, 1, 1);
        /*
        final Separator sepHor = new Separator();
        sepHor.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor, 0, 2);
        GridPane.setColumnSpan(sepHor, 2);
        bodyContainer.getChildren().add(sepHor);
        */
        root.setCenter(bodyContainer);
        
        convertorStage.getIcons().add(new Image("/media/icon.png"));
        convertorStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWFABRICSETTINGS")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        //convertorStage.setIconified(true);
        convertorStage.setResizable(false);
        convertorStage.setScene(scene);
        convertorStage.setX(0);
        convertorStage.setY(0);
        convertorStage.show();
        convertorStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
                scene.getStylesheets().add(ConvertorView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
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
                        convertorStage.close();
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
    
    public int[] hex2rgb(String hexcode){
        if(!isHexCodeValid(hexcode))
            return new int[]{0, 0, 0};
        else
            return new int[]{Integer.valueOf( hexcode.substring( 1, 3 ), 16 )
                    , Integer.valueOf( hexcode.substring( 3, 5 ), 16 )
                    , Integer.valueOf( hexcode.substring( 5, 7 ), 16 )};
    }
    
    public String rgb2hex(int[] rgb){
        if(isRGBinvalid(rgb))
            return "#000000";
        else
            return String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
    }
    
    public String hsb2hex(double[] hsb){
        if(isHSBinvalid(hsb))
            return "#000000";
        else{
            Color hsbColor = Color.hsb(hsb[0], hsb[1], hsb[2]);
            return rgb2hex(new int[]{(int)(hsbColor.getRed()*255)
                    , (int)(hsbColor.getGreen()*255)
                    , (int)(hsbColor.getBlue()*255)});
        }
    }
    
    public double[] hex2hsb(String hexcode){
        if(!isHexCodeValid(hexcode))
            return new double[]{0.0, 0.0, 0.0};
        else{
            int[] rgb=hex2rgb(hexcode);
            Color color=Color.web(hexcode);
            return new double[]{color.getHue(), color.getSaturation(), color.getBrightness()};
        }    
    }
    
    public boolean isRGBinvalid(int[] rgb){
        if(rgb==null || rgb.length!=3 || rgb[0]<0 || rgb[0]>255 || rgb[1]<0 || rgb[1]>255 || rgb[2]<0 || rgb[2]>255)
            return true;
        else
            return false;
    }
    
    public boolean isHSBinvalid(double[] hsb){
        if(hsb==null || hsb.length!=3 || hsb[1]<0.0 || hsb[1]>1.0 || hsb[2]<0.0 || hsb[2]>1.0)
            return true;
        else
            return false;
    }
    
    public boolean isHexCodeValid(String value){
        boolean isValid=false;
        if(value!=null&&value.length()==7){
            int i=0;
            if(value.startsWith("#")){
                for(i=1; i<7; i++){
                    if(!(value.substring(i, i+1).matches("[0-9]*")||value.charAt(i)=='a'
                            ||value.charAt(i)=='b'||value.charAt(i)=='c'||value.charAt(i)=='d'
                            ||value.charAt(i)=='e'||value.charAt(i)=='f'||value.charAt(i)=='A'
                            ||value.charAt(i)=='B'||value.charAt(i)=='C'||value.charAt(i)=='D'
                            ||value.charAt(i)=='E'||value.charAt(i)=='F'))
                        break;
                }
                if(i==7)
                    isValid=true;
            }
        }
        return isValid;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        new ConvertorView(stage);
        new Logging("WARNING",ConvertorView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 /*
  public static void main(String[] args) {   
      launch(args);    
  }
  */
}