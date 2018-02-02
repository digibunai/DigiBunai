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
import com.mla.main.AboutView;
import com.mla.main.Configuration;
import com.mla.main.ContactView;
import com.mla.main.HelpView;
import com.mla.main.IDGenerator;
import static com.mla.main.InstallationView.Constants.CONFIG;
import com.mla.main.Logging;
import com.mla.main.TechnicalView;
import com.mla.secure.Security;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.mail.internet.AddressException;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import javax.mail.internet.InternetAddress;
/**
 * Description:
 *  -Generates User Registration UI Stage.
 *  -Validation of all entered user details.
 *  -Inserts user details into database table "mla_users"
 *  -Inserts data in related tables "mla_user_usergroup_map", "mla_user_configuration", "mla_user_preference" & "mla_user_access"
 * Date Added: 3 Feb 2017
 * @author Aatif Ahmad Khan
 */
public class UserRegistration {
    
    public static Stage userRegistrationStage;
    BorderPane root;
    
    User objUser;
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    private Text lblStatus;

    // comboboxes for showing drop down lists of countries, states and cities
    private ComboBox<Label> countryCB= new ComboBox<>();
    private ComboBox<Label> stateCB= new ComboBox<>();
    private ComboBox<Label> cityCB= new ComboBox<>();
    
    // lists of contries, states and cities to be populated in respective comboboxes
    List lstCountry;
    List lstState;
    List lstCity;
    
    // number of results (different locations) returned by Google Maps Geo Location API call for a queried location
    int resultCount;
    // array of geo locations (formatted_addresses) returned by Google Maps Geo Location API call for a queried location
    String[] geoLocations;
    // array of latitudes and longitudes returned by Google Maps Geo Location API call for a queried location
    double[] lats, lngs;
    // indices of selected country & state in countryCB & stateCB respectively
    // USAGE: state list needs to be populated based on selected country, and city list based on state selected
    int intcountry=0, intstate=0;
        
    public UserRegistration(Configuration objConfigurationCall) {
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);

        userRegistrationStage = new Stage();
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 700, Color.WHITE);
        scene.getStylesheets().add(UserRegistration.class.getResource("/media/login.css").toExternalForm());

        objUser = new User();
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);

        GridPane grid = new GridPane();   
        grid.setId("grid");
        
        HBox hbox = new HBox();
        Text scenetitle = new Text(objDictionaryAction.getWord("REGISTRATION"));
        scenetitle.setFont(Font.font(objConfiguration.getStrBFont(), FontWeight.NORMAL, objConfiguration.getIntBFontSize()));
        scenetitle.setId("welcome-text");
        ImageView sceneicon = new ImageView("/media/icon.png");
        sceneicon.setFitHeight(32);
        sceneicon.setFitWidth(32);
        hbox.getChildren().addAll(sceneicon,scenetitle);
        grid.add(hbox, 0, 0, 4, 1);

        // Label for Name, its corresponding TextField, and validation Label
        Label lblName= new Label(objDictionaryAction.getWord("NAME")+" :");
        grid.add(lblName, 0, 1);
        final TextField txtName = new TextField();
        txtName.setPrefColumnCount(20);
        txtName.setText("");
        txtName.setPromptText(objDictionaryAction.getWord("PROMPTNAME"));
        grid.add(txtName, 1, 1);
        final Label errName= new Label("");
        //errName.setId("actiontarget");
        grid.add(errName, 1, 2);
        txtName.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // validate name when name TextField loses focus
                if(!newValue){ 
                    if(txtName.getText().trim().length()==0){ // name field empty
                        errName.setId("error");
                        errName.setText("Enter your Name");
                    } else if(!txtName.getText().toLowerCase().trim().matches("^[\\p{L} .'-]+$")){ // name field should have valid characters
                        errName.setId("error");
                        errName.setText("Name should use alphabets (a-z) only");
                    } else {   // valid name
                        errName.setId("success");
                        errName.setText("OK");
                    }
                }
            }
        });
        // Label for UserName, its corresponding TextField, and validation Label
        Label lblUsername= new Label(objDictionaryAction.getWord("USERNAME")+" : ");
        grid.add(lblUsername, 2, 1);
        final TextField txtUsername = new TextField();
        txtUsername.setPrefColumnCount(20);
        txtUsername.setText("");
        txtUsername.setPromptText(objDictionaryAction.getWord("PROMPTNAME"));
        grid.add(txtUsername, 3, 1);
        final Label errUsername=new Label("");
        //errUsername.setId("actiontarget");
        grid.add(errUsername, 3, 2);
        txtUsername.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // validate unique username when username TextField loses focus
                if(!newValue){
                    if(txtUsername.getText().trim()==""){               // empty field
                        errUsername.setId("error");
                        errUsername.setText("Enter a Username");
                    } else {
                        try{ // query database table "mla_users" to validate unique username
                            if(new UserAction().isUsernameExisting(txtUsername.getText())){
                                errUsername.setId("success");
                                errUsername.setText("OK");   // valid & unique username
                            }
                            else{    // existing username found in database table "mla_users"
                                errUsername.setId("error");
                                errUsername.setText("Username already exists");
                            }
                        } catch(Exception ex){
                            new Logging("SEVERE",UserRegistration.class.getName(),"Error in validating unique username from database",ex);
                        }
                    }
                }
            }
        });
        // Label for Password, its corresponding TextField, and validation Label
        Label lblPassword= new Label(objDictionaryAction.getWord("PASSWORD"));
        grid.add(lblPassword, 0, 3);
        final PasswordField txtPassword = new PasswordField();
        txtPassword.setPrefColumnCount(20);
        txtPassword.setText("");
        txtPassword.setPromptText(objDictionaryAction.getWord("PROMPTPASSWORD"));
        grid.add(txtPassword, 1, 3);
        final Label errPassword=new Label("");
        //errPassword.setId("actiontarget");
        grid.add(errPassword, 1, 4);
        txtPassword.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // validating password when password PasswordField loses focus
                if(!newValue){  
                    if(txtPassword.getText()==""){
                        errPassword.setId("error");
                        errPassword.setText("Enter a Password");
                    } else if(txtPassword.getText().length()<6){ // smaller length password are weak but acceptable
                        errPassword.setId("error");
                        errPassword.setText("Too Short Password");
                    } else if(!isValidPassword(txtName.getText(), txtUsername.getText(), txtPassword.getText())){ // smaller length password are weak but acceptable
                        errPassword.setId("error");
                        errPassword.setText("Weak Password");
                    } else {  // password ok
                        errPassword.setId("success");
                        errPassword.setText("OK");
                    }
                }
            }
        });        
        // Label for Confirm Password, its corresponding TextField, and validation Label
        Label lblRePassword= new Label(objDictionaryAction.getWord("CONFIRMPASSWORD"));
        grid.add(lblRePassword, 2, 3);
        final PasswordField txtRePassword = new PasswordField();
        txtRePassword.setPrefColumnCount(20);
        txtRePassword.setText("");
        txtRePassword.setPromptText(objDictionaryAction.getWord("PROMPTCONFIRMPASSWORD"));
        grid.add(txtRePassword, 3, 3);
        final Label errRePassword=new Label("");
        //errRePassword.setId("actiontarget");
        grid.add(errRePassword, 3, 4);
        txtRePassword.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // validate Confirm Password field when rePassword Password Field loses focus
                if(!newValue){  
                    if(txtRePassword.getText()==""){
                        errRePassword.setId("error");
                        errRePassword.setText("Enter your Password Again");
                    } else if(!(txtRePassword.getText().equals(txtPassword.getText()))){
                        errRePassword.setId("error");
                        errRePassword.setText("Password does not Match");
                    } else {
                        errRePassword.setId("success");
                        errRePassword.setText("OK");
                    }
                }
            }
        });
        // Label for Password, its corresponding TextField, and validation Label
        Label lblAppPassword= new Label(objDictionaryAction.getWord("SERVICEPASSWORD"));
        grid.add(lblAppPassword, 0, 5);
        final PasswordField txtAppPassword = new PasswordField();
        txtAppPassword.setPrefColumnCount(20);
        txtAppPassword.setText("");
        txtAppPassword.setPromptText(objDictionaryAction.getWord("PROMPTSERVICEPASSWORD"));
        grid.add(txtAppPassword, 1, 5);
        final Label errAppPassword=new Label("");
        //errAppPassword.setId("actiontarget");
        grid.add(errAppPassword, 1, 6);
        txtAppPassword.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // validating password when password PasswordField loses focus
                if(!newValue){  
                    if(txtAppPassword.getText()==""){
                        errAppPassword.setId("error");
                        errAppPassword.setText("Enter a Secondory Password");
                    } else if(!isValidPassword(txtName.getText(), txtUsername.getText(), txtAppPassword.getText())){
                        errAppPassword.setId("error");
                        errAppPassword.setText("Weak Password");
                    } else if(txtAppPassword.getText().length()<6){ // smaller length password are weak but acceptable
                        errAppPassword.setId("error");
                        errAppPassword.setText("Too Short Password");
                    } else {  // password ok
                        errAppPassword.setId("success");
                        errAppPassword.setText("OK");
                    }
                }
            }
        });        
        // Label for Confirm Password, its corresponding TextField, and validation Label
        Label lblReAppPassword= new Label(objDictionaryAction.getWord("CONFIRMPASSWORD"));
        grid.add(lblReAppPassword, 2, 5);
        final PasswordField txtReAppPassword = new PasswordField();
        txtReAppPassword.setPrefColumnCount(20);
        txtReAppPassword.setText("");
        txtReAppPassword.setPromptText(objDictionaryAction.getWord("PROMPTCONFIRMPASSWORD"));
        grid.add(txtReAppPassword, 3, 5);
        final Label errReAppPassword=new Label("");
        //errReAppPassword.setId("actiontarget");
        grid.add(errReAppPassword, 3, 6);
        txtReAppPassword.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // validate Confirm Password field when rePassword Password Field loses focus
                if(!newValue){  
                    if(txtReAppPassword.getText()==""){
                        errReAppPassword.setId("error");
                        errReAppPassword.setText("Enter your Password Again");
                    } else if(!(txtReAppPassword.getText().equals(txtAppPassword.getText()))){
                        errReAppPassword.setId("error");
                        errReAppPassword.setText("Password does not Match");
                    } else {
                        errReAppPassword.setId("success");
                        errReAppPassword.setText("OK");
                    }
                }
            }
        });
        // Label for Email, its corresponding TextField, and validation Label
        Label lblEmail= new Label(objDictionaryAction.getWord("EMAILID")+" :");
        grid.add(lblEmail, 0, 7);
        final TextField txtEmail = new TextField();
        txtEmail.setPrefColumnCount(20);
        txtEmail.setPromptText(objDictionaryAction.getWord("PROMPTEMAILID"));
        grid.add(txtEmail, 1, 7);
        final Label errEmail=new Label("");
        //errEmail.setId("actiontarget");
        grid.add(errEmail, 1, 8);
        txtEmail.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // validating email when Email Text Field loses focus
                if(!newValue){ 
                    if(txtEmail.getText()==""){
                        errEmail.setId("error");
                        errEmail.setText("Enter your Email Address");
                    } else if(!isValidEmailAddress(txtEmail.getText())){
                        errEmail.setId("error");
                        errEmail.setText("Enter valid Email Address");   
                    } else {    // valid email
                        errEmail.setId("success");
                        errEmail.setText("OK");
                    }
                }
            }
        });        
        // Label for Mobile, its corresponding TextField, and validation Label
        Label lblContact= new Label(objDictionaryAction.getWord("CONTACTNUMBER"));
        grid.add(lblContact, 2, 7);
        final TextField txtContact = new TextField();
        txtContact.setPrefColumnCount(20);
        txtContact.setText("");
        txtContact.setPromptText(objDictionaryAction.getWord("PROMPTCONTACTNUMBER"));
        grid.add(txtContact, 3, 7);
        final Label errContact=new Label("");
        //errContact.setId("actiontarget");
        grid.add(errContact, 3, 8);
        txtContact.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // validate contact number when contact Text Field loses focus
                if(!newValue){
                    if(txtContact.getText()==""){// empty contact field
                        errContact.setId("error");
                        errContact.setText("Enter your Mobile No.");
                    } else if(!txtContact.getText().matches("\\d*")){  // contact must have digits only
                        errContact.setId("error");
                        errContact.setText("Mobile No. should be numeric");
                    } else if(!(txtContact.getText().length()==10)){   // 10 digit
                        errContact.setId("error");
                        errContact.setText("Mobile No.should be 10 digits in length");
                    } else {                                          // valid mobile number
                        errContact.setId("success");
                        errContact.setText("OK");
                    }
                }
            }
        });
        
        // Label for GeoTag, its corresponding TextField, and validation Label
        Label lblGeotag= new Label("Nearby: ");
        grid.add(lblGeotag, 0, 9);
        final TextField txtGeotag = new TextField();
        txtGeotag.setPrefColumnCount(20);
        txtGeotag.setText("");
        final ComboBox<String> geotagCB=new ComboBox();
        grid.add(geotagCB, 1, 10);
        geotagCB.setVisible(false);
        geotagCB.setVisibleRowCount(3);
        /**
         * Google Maps Geo Location API is used for retrieving geo location of query location.
         * Http Request is fired, which returns a JSON result.
         * JSON result is parsed using library json-Simple, giving us JSON Object Array for all matching geo locations.
         * We then get our desired objects "formatted_address", "lat" & "lng".
         */
        txtGeotag.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.isEmpty()){  // return when user clears geo Text Field (e.g. using backspace)
                    geotagCB.getItems().clear();
                    geotagCB.setVisible(false);
                    return;
                }
                if(geotagCB.isVisible() && newValue.equalsIgnoreCase(geotagCB.getValue()))
                    return;
                try {
                    newValue = newValue.replace(" ", "%20");    // replace all white spaces with "%20" to make a valid URL
                    URL url=new URL("http://maps.googleapis.com/maps/api/geocode/json?address="+newValue+"&sensor=true");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setInstanceFollowRedirects(false);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/json"); 
                    conn.setRequestProperty("charset", "utf-8");
            
                    if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                    }
                    // reading response of HTTP Request into string "full"
                    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                    String output = "", full = "";
                    while ((output = br.readLine()) != null) {
                        full += output;
                    }
                    resultCount=getAddressFromJson(full);
                    if(resultCount>0){  // add result geo location(s) into geo ComboBox, so user can select one
                        geotagCB.getItems().clear();
                        geotagCB.setVisible(true);
                        geotagCB.getItems().add(0, "Select");
                        for(int a=0; a<resultCount;a++){
                            geotagCB.getItems().add(a+1, geoLocations[a]);
                        }
                        if(resultCount>0)
                            geotagCB.setValue(geotagCB.getItems().get(0));    // default selection is first geo location on list
                    } else{   // if no results are retrieved
                        geotagCB.getItems().clear();
                        geotagCB.setVisible(false);
                        geotagCB.getSelectionModel().clearSelection();
                    }
                    conn.disconnect();
                } catch (MalformedURLException muex) {
                    new Logging("SEVERE",UserRegistration.class.getName(),"URL Malfunction issue while Geo Locating",muex);
                } catch (IOException ioex) {
                    new Logging("SEVERE",UserRegistration.class.getName(),"IO Error while Geo Locating",ioex);
                } catch(Exception ex){
                    new Logging("SEVERE",UserRegistration.class.getName(),"Error while Geo Locating",ex);
                }
            }
        });
        geotagCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // get Add, Lat, Lng from (index of) new value -> arr
                int indexInArr=geotagCB.getSelectionModel().getSelectedIndex();
                if(indexInArr<=0)
                    return;
                objUser.setStrGeoCity(geoLocations[indexInArr-1]);
                objUser.setDblGeoLat(lats[indexInArr-1]);
                objUser.setDblGeoLng(lngs[indexInArr-1]);
                txtGeotag.setText(geoLocations[indexInArr-1]);
            }
        });
        grid.add(txtGeotag, 1, 9);
        final Label errGeotag=new Label("");
        grid.add(errGeotag, 0, 10);
        geotagCB.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){  // validating geo location when geo ComboBox loses focus
                    if(geotagCB.getSelectionModel().getSelectedIndex()>=0)
                        errGeotag.setId("success");
                        errGeotag.setText("OK");
                }
            }
        });
        // Label for Address, its corresponding TextField, and validation Label
        Label lblAddress= new Label("Address: ");
        grid.add(lblAddress, 2, 9);
        final TextField txtAddress = new TextField();
        txtAddress.setPrefColumnCount(20);
        txtAddress.setText("");
        grid.add(txtAddress, 3, 9);
        final Label errAddress=new Label("");
        grid.add(errAddress, 3, 10);
        txtAddress.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    if(txtAddress.getText()==""){    // address field is empty
                        errAddress.setId("error");
                        errAddress.setText("Enter your Address");
                    } else {
                        errAddress.setId("success");
                        errAddress.setText("OK");
                    }
                }
            }
        });
        // Label for Country, its ChoiceBox, and its validation Label
        Label lblCountry= new Label("Country: ");
        grid.add(lblCountry, 0, 11);
        // load list of countries
        loadCountry();
        countryCB.setCellFactory(new Callback<ListView<Label>, ListCell<Label>>() {
            @Override
            public ListCell<Label> call(ListView<Label> p) {
                return new ListCell<Label>() {
                    private final Label cell=new Label();
                    
                    @Override
                    protected void updateItem(Label item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getText());
                        }
                    }
                };
            }
        });
        countryCB.setConverter(new StringConverter<Label>(){
            @Override
            public String toString(Label object) {
                return object.getText();
            }

            @Override
            public Label fromString(String string) {
                return new Label();
            }
        });
        grid.add(countryCB, 1, 11);
        final Label errCountry=new Label("");
        grid.add(errCountry, 1, 12);
        countryCB.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    if(countryCB.getSelectionModel().getSelectedItem().getText()=="Select"){    // no country selected
                        errCountry.setId("error");
                        errCountry.setText("Select your country");
                    } else {  // country is selected
                        intcountry=Integer.parseInt(countryCB.getSelectionModel().getSelectedItem().getUserData().toString());
                        loadState(intcountry);  // update state list for selected country
                        errCountry.setId("success");
                        errCountry.setText("OK");
                    }
                }
            }
        });
        cmbKeyListener(countryCB);
        // Label for State, its ChoiceBox, and its validation Label
        Label lblState= new Label("State: ");
        grid.add(lblState, 2, 11);
        stateCB = new ComboBox<Label>();
        // when user selects a country then load states list based on country selected
        loadState(intcountry);
        stateCB.setCellFactory(new Callback<ListView<Label>, ListCell<Label>>() {
            @Override
            public ListCell<Label> call(ListView<Label> p) {
                return new ListCell<Label>() {
                    private final Label cell=new Label();
                    
                    @Override
                    protected void updateItem(Label item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getText());
                        }
                    }
                };
            }
        });
        stateCB.setConverter(new StringConverter<Label>(){
            @Override
            public String toString(Label object) {
                return object.getText();
            }

            @Override
            public Label fromString(String string) {
                return new Label();
            }
        });
        cmbKeyListener(stateCB);
        grid.add(stateCB, 3, 11);
        final Label errState=new Label("");
        grid.add(errState, 3, 12);
        stateCB.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    if(stateCB.getSelectionModel().getSelectedItem().getText()=="Select"){  // no state selected
                        errState.setId("error");
                        errState.setText("Select your state");
                    } else {
                        intstate=Integer.parseInt(stateCB.getSelectionModel().getSelectedItem().getUserData().toString());
                        loadCity(intstate); // update cities list based on selected state
                        errState.setId("success");
                        errState.setText("OK");
                    }
                }
            }
        });
        // Label for City, its ChoiceBox, and its validation Label
        Label lblCity= new Label("City/District: ");
        grid.add(lblCity, 0, 13);
        cityCB = new ComboBox<Label>();
        // when user selects a state then load cities list based on state selected
        loadCity(intstate);
        cityCB.setCellFactory(new Callback<ListView<Label>, ListCell<Label>>() {
            @Override
            public ListCell<Label> call(ListView<Label> p) {
                return new ListCell<Label>() {
                    private final Label cell=new Label();
                    
                    @Override
                    protected void updateItem(Label item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getText());
                        }
                    }
                };
            }
        });
        grid.add(cityCB, 1, 13);
        final Label errCity=new Label("");
        grid.add(errCity, 1, 14);
        cityCB.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    if(cityCB.getSelectionModel().getSelectedItem().getText()=="Select"){   // no city is selected
                        errCity.setId("error");
                        errCity.setText("Select your city");
                    } else {
                        errCity.setId("success");
                        errCity.setText("OK");
                    }
                }
            }
        });      
        cityCB.setConverter(new StringConverter<Label>(){
            @Override
            public String toString(Label object) {
                return object.getText();
            }

            @Override
            public Label fromString(String string) {
                return new Label();
            }
        });
        cmbKeyListener(cityCB);
        // Label for Pincode, its TextField, and its validation Label
        Label lblPincode= new Label("Pincode: ");
        grid.add(lblPincode, 2, 13);
        final TextField txtPincode = new TextField();
        txtPincode.setPrefColumnCount(20);
        grid.add(txtPincode, 3, 13);
        final Label errPincode=new Label("");
        grid.add(errPincode, 3, 14);
        // validating pincode when pincodeTF loses focus
        txtPincode.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    if(txtPincode.getText()==""){    // empty picode field
                        errPincode.setId("error");
                        errPincode.setText("Enter your Pin Code");
                    } else if(!txtPincode.getText().matches("\\d*")){  // pincode must be numeric
                        errPincode.setId("error");
                        errPincode.setText("Pin Code should be numeric");
                    } else if(!(txtPincode.getText().length()==6)){    // pincode should be 6 digits in length
                        errPincode.setId("error");
                        errPincode.setText("Pin Code should be 6 digits in length");
                    } else {      // if pincode entered is valid
                        errPincode.setId("success");
                        errPincode.setText("OK");
                    }
                }
            }
        });
        
        Hyperlink TnC = new Hyperlink("Disclaimer");
        final CheckBox chkAccept=new CheckBox("I have read the ");
        HBox tncHB=new HBox();
        tncHB.getChildren().add(chkAccept);
        tncHB.getChildren().add(TnC);
        grid.add(tncHB, 0, 15, 2, 1);
        TnC.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                new UserTnCView(objConfiguration);
            }
        });
        
        final Button btnRegister=new Button(objDictionaryAction.getWord("SUBMIT"));
        btnRegister.setGraphic(new ImageView(objConfiguration.getStrColour()+"/save.png"));
        btnRegister.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSUBMIT")));
        btnRegister.setDefaultButton(true);
        btnRegister.setFocusTraversable(true);
        btnRegister.setDisable(true);
        
        Button btnCancel=new Button(objDictionaryAction.getWord("BACK"));
        btnCancel.setGraphic(new ImageView(objConfiguration.getStrColour()+"/close.png"));
        btnCancel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPCANCEL")));
        
        HBox P_buttons = new HBox(10);
        P_buttons.setAlignment(Pos.BOTTOM_RIGHT);
        P_buttons.getChildren().addAll(btnCancel,btnRegister);
        grid.add(P_buttons, 2, 15, 2, 1);

        chkAccept.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue)
                    btnRegister.setDisable(false);
                else
                    btnRegister.setDisable(true);
            }
        });
        
        lblStatus = new Text();
        grid.add(lblStatus, 0, 16, 4, 1);
        lblStatus.setId("actiontarget");      
    
        btnRegister.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                // if all entered details are valid, all corresponding <detail>OK labels will be having text "OK"
                // proceed only when all details are validated
                if(!chkAccept.isSelected()){
                    lblStatus.setText(objDictionaryAction.getWord("ACCEPTLICENSEAGREEMENT"));
                } else if(errName.getText()=="OK" && errUsername.getText()=="OK" && errEmail.getText()=="OK" && errContact.getText()=="OK" &&
                   (errPassword.getText()=="OK" || errPassword.getText()=="Weak Password") && errRePassword.getText()=="OK" && (errAppPassword.getText()=="OK" || errAppPassword.getText()=="Weak Password") && errReAppPassword.getText()=="OK" &&
                   errGeotag.getText()=="OK" && errAddress.getText()=="OK" && errCountry.getText()=="OK" &&
                   errState.getText()=="OK" && errCity.getText()=="OK" && errPincode.getText()=="OK"){
                    try {
                        // generating userid with the mac (hardware) address of machine
                        //byte[] byteAddress=NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
                        //String hwAddress=javax.xml.bind.DatatypeConverter.printHexBinary(byteAddress);
                        objUser.setStrUserID(new IDGenerator().getIDGenerator("USER_LIBRARY", CONFIG.LICENCE));
                        objUser.setStrUsername(txtUsername.getText());
                        objUser.setStrPassword(Security.hashPassword(txtPassword.getText()));
                        objUser.setStrAppPassword(Security.SecurePassword(txtAppPassword.getText(), txtUsername.getText()));
                        objUser.setStrName(txtName.getText());
                        objUser.setStrEmailID(txtEmail.getText());
                        objUser.setStrContactNumber(txtContact.getText());
                        // Add info for geotag--Already Set-----------------------------------------
                        //objConfiguration.getObjUser().setStrGeoCity("Lucknow");
                        //objConfiguration.getObjUser().setDblGeoLat(111);
                        //objConfiguration.getObjUser().setDblGeoLng(11);
                        //-----------
                        objUser.setStrAddress(txtAddress.getText());
                        objUser.setStrCityAddress(cityCB.getConverter().toString(cityCB.getValue()));
                        objUser.setStrStateAddress(stateCB.getConverter().toString(stateCB.getValue()));
                        objUser.setStrCountryAddress(countryCB.getConverter().toString(countryCB.getValue()));
                        objUser.setStrPincodeAddress(txtPincode.getText());
                        UserAction objUserAction = new UserAction();
                        new UserAction().addUserTnC(objUser.getStrUserID());
                        int intUID=objUserAction.addUser(objUser);
                        if(intUID!=-1){     // Record Added in mla_users successfully
                            // adding mla_user_usergroup_map table with same userid
                            new UserAction().addUserGroupMap(intUID, 2); //2 =register
                            new UserAction().addUserGroupMap(intUID, 10); //10 = desginer
                            // adding mla_user_preference table with same userid
                            objConfiguration.intializeConfiguration();
                            objUser.setUserAccess(objConfiguration.getObjUser().getUserAccess());
                            objConfiguration.setObjUser(objUser);
                            new UserAction().addUserPrefrence(objConfiguration);
                            new UserAction().addUserAccess(objConfiguration);
                            // updating mla_user_configuration table with same userid
                            //File[] files = new File("/data/clothsettings/").listFiles();
                            String[] files = new String[7];
                            files[0]=new String("data/clothsettings/Blouse.properties");
                            files[1]=new String("data/clothsettings/Body.properties");
                            files[2]=new String("data/clothsettings/Border.properties");
                            files[3]=new String("data/clothsettings/CrossBorder.properties");
                            files[4]=new String("data/clothsettings/Konia.properties");
                            files[5]=new String("data/clothsettings/Palu.properties");
                            files[6]=new String("data/clothsettings/Skart.properties");
                            
                            //If this pathname does not denote a directory, then listFiles() returns null. 
                            int confNum=1;//CONF#
                            for (String file : files) {
                                if (file!=null) {
                                    objConfiguration.intializeClothConfiguration(file);
                                    objConfiguration.setStrConfName("CONF"+objUser.getStrUserID()+(confNum++));
                                    new UserAction().addUserConfiguration(objConfiguration);
                                }
                            }
                            userRegistrationStage.close();
                            UserView objUserView = new UserView(objConfiguration);
                        } else{
                            lblStatus.setText("Error encountered during User Registration");
                        }
                    } catch (SQLException ex) {
                        lblStatus.setText("Error encountered during User Registration");
                        Logger.getLogger(UserRegistration.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else
                    lblStatus.setText("Please Validate the Data Entered");
            }
        });
        btnCancel.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                userRegistrationStage.close();
                System.gc();
                UserView objUserView = new UserView(objConfiguration);
            }
        });
        
        final ImageView helpIV = new ImageView(objConfiguration.getStrColour()+"/help.png");
        Tooltip.install(helpIV, new Tooltip(objDictionaryAction.getWord("HELP")));
        helpIV.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                helpIV.setImage(new Image(objConfiguration.getStrColourDimmed()+"/help.png"));
                helpIV.setScaleX(1.5);
                helpIV.setScaleY(1.5);
                helpIV.setScaleZ(1.5);
            }
        });
        helpIV.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                helpIV.setImage(new Image(objConfiguration.getStrColour()+"/help.png"));
                helpIV.setScaleX(1);
                helpIV.setScaleY(1);
                helpIV.setScaleZ(1);
            }
        });
        helpIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                HelpView objHelpView = new HelpView(objConfiguration);
            }
        });
        final ImageView technicalIV = new ImageView(objConfiguration.getStrColour()+"/technical_info.png");
        Tooltip.install(technicalIV, new Tooltip(objDictionaryAction.getWord("TECHNICAL")));
        technicalIV.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                technicalIV.setImage(new Image(objConfiguration.getStrColourDimmed()+"/technical_info.png"));
                technicalIV.setScaleX(1.5);
                technicalIV.setScaleY(1.5);
                technicalIV.setScaleZ(1.5);
            }
        });
        technicalIV.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                technicalIV.setImage(new Image(objConfiguration.getStrColour()+"/technical_info.png"));
                technicalIV.setScaleX(1);
                technicalIV.setScaleY(1);
                technicalIV.setScaleZ(1);
            }
        });
        technicalIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                TechnicalView objTechnicalView = new TechnicalView(objConfiguration);
            }
        });
        final ImageView aboutIV = new ImageView(objConfiguration.getStrColour()+"/about_software.png");
        Tooltip.install(aboutIV, new Tooltip(objDictionaryAction.getWord("ABOUTUS")));
        aboutIV.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                aboutIV.setImage(new Image(objConfiguration.getStrColourDimmed()+"/about_software.png"));
                aboutIV.setScaleX(1.5);
                aboutIV.setScaleY(1.5);
                aboutIV.setScaleZ(1.5);
            }
        });
        aboutIV.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                aboutIV.setImage(new Image(objConfiguration.getStrColour()+"/about_software.png"));
                aboutIV.setScaleX(1);
                aboutIV.setScaleY(1);
                aboutIV.setScaleZ(1);
            }
        });
        aboutIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                AboutView objAboutView = new AboutView(objConfiguration);
            }
        });
        final ImageView contactIV = new ImageView(objConfiguration.getStrColour()+"/contact_us.png");
        Tooltip.install(contactIV, new Tooltip(objDictionaryAction.getWord("CONTACTUS")));
        contactIV.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                contactIV.setImage(new Image(objConfiguration.getStrColourDimmed()+"/contact_us.png"));
                contactIV.setScaleX(1.5);
                contactIV.setScaleY(1.5);
                contactIV.setScaleZ(1.5);
            }
        });
        contactIV.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                contactIV.setImage(new Image(objConfiguration.getStrColour()+"/contact_us.png"));
                contactIV.setScaleX(1);
                contactIV.setScaleY(1);
                contactIV.setScaleZ(1);
            }
        });
        contactIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                ContactView objContactView = new ContactView(objConfiguration);
            }
        });

        HBox P_links = new HBox(10);
        P_links.setAlignment(Pos.BOTTOM_CENTER);
        P_links.getChildren().addAll(helpIV, technicalIV, aboutIV, contactIV);
        grid.add(P_links, 0, 17, 4, 1);

        root.setCenter(grid);
        userRegistrationStage.getIcons().add(new Image("/media/icon.png"));
        userRegistrationStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWLOGIN")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        userRegistrationStage.setResizable(false);
        userRegistrationStage.setScene(scene);
        userRegistrationStage.show();
    }
    
    /**
     * Description: Loads list of countries in country ComboBox (countryCB)
     */
    private void loadCountry(){
        try {
            countryCB.getItems().clear();
            Label lblTemp = new Label("Select");
            lblTemp.setUserData(0);
            countryCB.getItems().add(lblTemp);
            countryCB.setValue(lblTemp);
            
            UserAction objUserAction = new UserAction();
            lstCountry = objUserAction.getLstCountry();
            for(int i=0; i<lstCountry.size(); i++){
                List lstTemp = (ArrayList)lstCountry.get(i);
                lblTemp = new Label(lstTemp.get(1).toString());
                lblTemp.setUserData(lstTemp.get(0));
                countryCB.getItems().add(lblTemp);
            }
            userRegistrationStage.close();
        } catch (SQLException ex) {
            new Logging("SEVERE",UserRegistration.class.getName(),"SQLException: Listing Country",ex);
        } catch (Exception ex) {
            new Logging("SEVERE",UserRegistration.class.getName(),"Exception: Listing Country",ex);
        }
    }
    
    /**
     * Description: Loads list of states in state ComboBox (stateCB) based on selected country
     * @param intCountry (selected country)
     */
    private void loadState(int intCountry){
        try {
            stateCB.getItems().clear();
            Label lblTemp = new Label("Select");
            lblTemp.setUserData(0);
            stateCB.getItems().add(lblTemp);
            stateCB.setValue(lblTemp);
            
            UserAction objUserAction = new UserAction();
            lstState = objUserAction.getLstState(intCountry);
            for(int i=0; i<lstState.size(); i++){
                List lstTemp = (ArrayList)lstState.get(i);
                lblTemp = new Label(lstTemp.get(1).toString());
                lblTemp.setUserData(lstTemp.get(0).toString());
                stateCB.getItems().add(lblTemp);
            }
        } catch (SQLException ex) {
            new Logging("SEVERE",UserRegistration.class.getName(),"SQLException: Listing State",ex);
        } catch (Exception ex) {
            new Logging("SEVERE",UserRegistration.class.getName(),"Exception: Listing State",ex);
        }
    }
    
    /**
     * Description: Loads list of cities in city ComboBox (cityCB) based on selected state
     * @param intState (selected state)
     */
    private void loadCity(int intState){
        try {
            cityCB.getItems().clear();
            Label lblTemp = new Label("Select");
            lblTemp.setUserData(0);
            cityCB.getItems().add(lblTemp);
            cityCB.setValue(lblTemp);
            
            UserAction objUserAction = new UserAction();
            lstCity = objUserAction.getLstCity(intState);
            for(int i=0; i<lstCity.size(); i++){
                List lstTemp = (ArrayList)lstCity.get(i);
                lblTemp = new Label(lstTemp.get(1).toString());
                lblTemp.setUserData(lstTemp.get(0).toString());
                cityCB.getItems().add(lblTemp);
            }
        } catch (SQLException ex) {
            new Logging("SEVERE",UserRegistration.class.getName(),"SQLException: Listing city",ex);
        } catch (Exception ex) {
            new Logging("SEVERE",UserRegistration.class.getName(),"Exception: Listing city",ex);
        }
    }
    
    /**
     * Description: Geo Location querying on Google Maps API returns array of json objects for all geo locations found for a query
     * We need to show all these possible geo locations for Auto Complete feature on Geo Location TextField
     * @param json string returned as response of geo location querying
     * @return number of different geo location
     */
    private int getAddressFromJson(String json){
        resultCount=0;
        try{
            JSONParser parser=new JSONParser();
            JSONObject parsed = (JSONObject)parser.parse(json);
            JSONArray arr=(JSONArray) parsed.get("results");
            geoLocations=new String[arr.size()];
            lats=new double[arr.size()];
            lngs=new double[arr.size()];
            for(int a=0; a<arr.size(); a++){
                JSONObject obj=(JSONObject) arr.get(a);
                geoLocations[a]= (String) obj.get("formatted_address");
                JSONObject geometry=(JSONObject) obj.get("geometry");
                JSONObject location=(JSONObject) geometry.get("location");
                lats[a]=(double) location.get("lat");
                lngs[a]=(double) location.get("lng");
            }
            resultCount=arr.size();
        }
        catch(Exception exc){}
        return resultCount;
    }
    /*public boolean isValidEmailAddress(String email){
        boolean valid=true;
        try{
            InternetAddress emailAdd=new InternetAddress(email);
            emailAdd.validate();
        } catch(AddressException ae){
            valid=false;
        }
        return valid;
    }*/
    
    public boolean isValidEmailAddress(String email) {
           String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
           java.util.regex.Matcher m = p.matcher(email);
           return m.matches();
    }
    
    public boolean isValidPassword(String name, String username, String password){
        // check if part of name exists in password
        for(int i=0;(i+4)<name.length();i++)
          if(password.indexOf(name.substring(i,i+4))!=-1)
                return false;
        // check if part of username exists in password
        for(int i=0;(i+4)<username.length();i++)
          if(password.indexOf(username.substring(i,i+4))!=-1)
                return false;
        return true;
    }
    
    public void cmbKeyListener(final ComboBox c){
        final StringBuilder sb=new StringBuilder();
        c.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.UP || event.getCode() == KeyCode.TAB) {
                    return;
                }
                else if (event.getCode() == KeyCode.BACK_SPACE && sb.length() > 0) {
                    sb.deleteCharAt(sb.length()-1);
                }
                else {
                    sb.append(event.getText());
                }
 
                if (sb.length() == 0) 
                    return;
                
                boolean found = false;
                ObservableList items = c.getItems();
                for (int i=0; i<items.size(); i++) {
                    if (event.getCode() != KeyCode.BACK_SPACE && c.getConverter().toString(items.get(i)).toLowerCase().startsWith(sb.toString().toLowerCase())) {
                        ListView lv = ((ComboBoxListViewSkin) c.getSkin()).getListView();
                        lv.getSelectionModel().clearAndSelect(i);           
                        lv.scrollTo(lv.getSelectionModel().getSelectedIndex());
                        found = true;
                        break;
                    }
                }
                if (!found && sb.length() > 0)
                    sb.deleteCharAt(sb.length() - 1);
            }
        });
    }
}
