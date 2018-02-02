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

import com.mla.colour.Colour;
import com.mla.fabric.Pattern;
import com.mla.user.User;
import com.mla.utility.Device;
import com.mla.yarn.Yarn;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
/**
 *
 * @Designing accessor and mutator function for configuration
 * @author Amit Kumar Singh
 * 
 */
public class Configuration {
    
    //public String strRoot = Paths.get("src").toAbsolutePath().normalize().toString(); 
    public String strRoot = System.getProperty("user.dir");
    GraphicsDevice objGraphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    public double WIDTH = objGraphicsDevice.getDisplayMode().getWidth();
    public double HEIGHT = objGraphicsDevice.getDisplayMode().getHeight();
            
    private User objUser;
    public String strAccess = "G";
    private int intStackLimit;
    private int intColorLimit;
    private int intTimeLimit;
    public int intBoxSize;
    public String strWindowFlowContext="Dashboard";
    private boolean servicePasswordValid = false;
    private boolean blnAuthenticateService;
    private Process objProcess_ProcessBuilder;
            
    private String strRecentArtwork=null;
    private String strRecentWeave=null;
    public Map<String,String> mapRecentFabric=new HashMap<String,String>(); // body, border, konia, palu, cross border, blouse
	public double garmentZoom;
    public int garmentPinch;
    ArrayList<Yarn> lstYarn = null;
    ArrayList<Pattern> lstPattern = null;
    private Yarn[] warpYarn;
    private Yarn[] weftYarn;
    private Yarn[] warpExtraYarn;
    private Yarn[] weftExtraYarn;
    private Yarn[] yarnPalette;
    private String[] colourPalette;
    
    private String strTemplate;
    private String strMouse;
    private String strColour;
    private String strColourDimmed;
    private String strLanguage;
    private String strResolution;
    private String strMeasurement;
    private String strCurrency;
    private String strBFont;
    private int intBFontSize;
    private String strSFont;
    private int intSFontSize;
    private String strDataPath;
    private String strHelpPath;
    private String strSavePath;
    private String strLogPath;
    private String strErrorPath;
    private String strAllowShare;
    private String strConfName;
    private String strDefaultFabric;
    private String strDefaultArtwork;
    private String strDefaultWeave;
    private String strClothType;
    private String strFabricType;
    private double dblFabricLength;
    private double dblFabricWidth;
    private double dblArtworkLength;
    private double dblArtworkWidth;
    private String strGraphSize;
    private boolean blnErrorGraph;
    private boolean blnCorrectGraph;
    private boolean blnPunchCard;
    private boolean blnMRepeat;
    private boolean blnMPrint;
    private boolean blnArtworkAssingmentSize;
    private boolean blnArtworkOutline;
    private boolean blnArtworkAspectRatio;
    private int intVRepeat;
    private int intHRepeat;
    private int intBinding;
    private int intProtection;
    private int intFloatBind;
    private int intPPI;
    private int intEPI;
    private int intDPI;
    private int intHPI;
    private int intTPD;
    private int intHooks;
    private int intEnds;
    private int intPixs;
    private int intReedCount;
    private int intDents;
    private int intExtraWeft;
    private int intExtraWarp;
    private String strColourType;
    private double dblDesigningCost;
    private double dblPunchingCost;
    private double dblPropertyCost;
    private double dblWagesCost;
    private int intOverheadCost;
    private int intProfit;
    private String strWeftName;
    private String strWeftPattern;
    private String strWeftColor;
    private int intWeftRepeat;
    private int intWeftCount;
    private String strWeftUnit;
    private int intWeftPly;
    private int intWeftFactor;
    private double dblWeftDiameter;
    private int intWeftTwist;
    private String strWeftSence;
    private int intWeftHairness;
    private int intWeftDistribution;
    private double dblWeftPrice;
    private int intWeftCrimp;
    private int intWeftWaste;
    private double dblWeftWeight;
    private double dblWeftLong;
    private double dblWeftNumber;
    private double dblExtraWeftWeight;
    private double dblExtraWeftLong;
    private double dblExtraWeftNumber;
    
    private String strWarpName;
    private String strWarpPattern;
    private String strWarpColor;
    private int intWarpRepeat;
    private int intWarpCount;
    private String strWarpUnit;
    private int intWarpPly;
    private int intWarpFactor;
    private double dblWarpDiameter;
    private int intWarpTwist;
    private String strWarpSence;
    private int intWarpHairness;
    private int intWarpDistribution;  
    private double dblWarpPrice;
    private int intWarpCrimp;
    private int intWarpWaste;
    private double dblWarpWeight;
    private double dblWarpLong;
    private double dblWarpNumber;
    private double dblExtraWarpWeight;
    private double dblExtraWarpLong;
    private double dblExtraWarpNumber;
    
    public void intializeConfiguration() {
        Properties properties = null;
        try {
            properties = new Properties();
            InputStream resourceAsStream =  Configuration.class.getClassLoader().getResourceAsStream("settings.properties");
            if (resourceAsStream != null) {
                properties.load(resourceAsStream);
            }
            this.objUser = new User();
            this.objUser.setUserAccess(new Hashtable());                
            this.objUser.setUserAccess("FABRIC_LIBRARY","Private");
            this.objUser.setUserAccess("ARTWORK_LIBRARY","Private");
            this.objUser.setUserAccess("WEAVE_LIBRARY","Private");
            this.objUser.setUserAccess("CLOTH_LIBRARY","Private");
            this.objUser.setUserAccess("PATTERN_LIBRARY","Private");
            this.objUser.setUserAccess("YARN_LIBRARY","Private");
            this.objUser.setUserAccess("COLOUR_LIBRARY","Private");
            this.objUser.setUserAccess("LANGUAGE_LIBRARY","Private");
            this.objUser.setUserAccess("DEVICE_LIBRARY","Private");
            this.objUser.setUserAccess("FABRIC_BASE_LIBRARY","Private");            
            this.strTemplate = "/media/template/"+properties.getProperty("TEMPLATE", "gold").toLowerCase();
            this.strMouse = "/media/mouse";
            this.strColour = "/media/"+properties.getProperty("COLOUR", "BLUE").toLowerCase();
            this.strColourDimmed = "/media/"+properties.getProperty("COLOURDIMMED", "GRAY").toLowerCase();
            this.strLanguage = properties.getProperty("LANGUAGE", "HINDI");
            this.strResolution = properties.getProperty("RESOLUTION", "1280x960");
            this.strMeasurement = properties.getProperty("MEASUREMENT", "Metric (m-kg)");
            this.strCurrency = properties.getProperty("CURRENCY", "Indian Rupees");
            this.strBFont = properties.getProperty("BIGFONT", "Arial");
            this.intBFontSize = Integer.parseInt(properties.getProperty("BIGFONTSIZE", "11"));
            this.strSFont = properties.getProperty("SMALLFONT", "Arial");
            this.intSFontSize = Integer.parseInt(properties.getProperty("SMALLFONTSIZE", "11"));
            this.strDataPath = properties.getProperty("DATAPATH", "/data/");
            this.strHelpPath = properties.getProperty("HELPPATH", "/help/");
            this.strSavePath = properties.getProperty("SAVEPATH", "C:/");
            this.strLogPath = properties.getProperty("LOGPATH", "/data/log/");
            this.strErrorPath = properties.getProperty("ERRORPATH", "/error/");
            this.intStackLimit = Integer.parseInt(properties.getProperty("STACKTLIMIT", "6"));
            this.intColorLimit = Integer.parseInt(properties.getProperty("COLORLIMIT", "6"));
            this.intTimeLimit = Integer.parseInt(properties.getProperty("TIMELIMIT", "300000"));
            this.blnAuthenticateService = Boolean.parseBoolean(properties.getProperty("AUTHENTICATESERVICE", "false"));
            this.objProcess_ProcessBuilder = null;
            this.servicePasswordValid = Boolean.parseBoolean(properties.getProperty("VALIDSERVICE", "false"));
            this.intBoxSize = Integer.parseInt(properties.getProperty("BOXSIZE", "6"));
            this.strAllowShare = properties.getProperty("ALLOWSHARE", "Y");
            this.intVRepeat = Integer.parseInt(properties.getProperty("VREPEAT", "1"));
            this.intHRepeat = Integer.parseInt(properties.getProperty("HREPEAT", "1"));
            this.blnMRepeat = Boolean.parseBoolean(properties.getProperty("MREPEAT", "false"));
            this.blnMPrint = Boolean.parseBoolean(properties.getProperty("MPRINT", "false"));
            this.blnPunchCard = Boolean.parseBoolean(properties.getProperty("PUNCHCARD", "false"));
            this.blnErrorGraph = Boolean.parseBoolean(properties.getProperty("ERRORGRAPH", "false"));
            this.blnCorrectGraph = Boolean.parseBoolean(properties.getProperty("CORRECTGRAPH", "false"));
            this.blnArtworkAssingmentSize = Boolean.parseBoolean(properties.getProperty("ARTWORKSIZE", "false"));
            this.blnArtworkOutline = Boolean.parseBoolean(properties.getProperty("ARTWORKOUTLINE", "false"));
            this.blnArtworkAspectRatio = Boolean.parseBoolean(properties.getProperty("ARTWORKASPECTRATIO", "false"));            
            this.strGraphSize = properties.getProperty("GRAPHSIZE", "12x12");
            this.intFloatBind = Integer.parseInt(properties.getProperty("FLOATBIND", "3"));
            this.intProtection = Integer.parseInt(properties.getProperty("PROTECTION", "3"));
            this.intBinding = Integer.parseInt(properties.getProperty("BINDING", "3"));   
            this.strConfName = properties.getProperty("CONFNAME", "default_configuration");
            this.strDefaultFabric = properties.getProperty("DEFAULTFABRIC", "FABRICG1");
            this.strDefaultArtwork = properties.getProperty("DEFAULTARTWORK", "ARTWORKG1");
            this.strDefaultWeave = properties.getProperty("DEFAULTWEAVE", "WEAVEG2");
            this.strClothType = properties.getProperty("CLOTHTYPE", "Body");
            this.strFabricType = properties.getProperty("FABRICTYPE", "Plain");
            this.dblFabricLength = Double.parseDouble(properties.getProperty("FABRICLENGTH", "47"));
            this.dblFabricWidth = Double.parseDouble(properties.getProperty("FABRICWIDTH", "256"));
            this.dblArtworkLength = Double.parseDouble(properties.getProperty("ARTWORKLENGTH", "2.6"));
            this.dblArtworkWidth = Double.parseDouble(properties.getProperty("ARTWORKWIDTH", "2.7"));
            this.intPPI = Integer.parseInt(properties.getProperty("PPI", "63"));
            this.intEPI = Integer.parseInt(properties.getProperty("EPI", "60"));
            this.intDPI = Integer.parseInt(properties.getProperty("DPI", "72"));
            this.intHPI = Integer.parseInt(properties.getProperty("HPI", "60"));
            this.intTPD = Integer.parseInt(properties.getProperty("TPD", "30"));
            this.intHooks = Integer.parseInt(properties.getProperty("HOOKS", "240"));
            this.intEnds = Integer.parseInt(properties.getProperty("HOOKS", "240"))*Integer.parseInt(properties.getProperty("TPD", "2"));;
            this.intPixs = Integer.parseInt(properties.getProperty("PICKS", "2457"));
            this.intReedCount = Integer.parseInt(properties.getProperty("REEDCOUNT", "90"));
            this.intDents = Integer.parseInt(properties.getProperty("DENTS", "45"));
            this.intExtraWeft = Integer.parseInt(properties.getProperty("EXTRAWEFT", "0"));
            this.intExtraWarp = Integer.parseInt(properties.getProperty("EXTRAWARP", "0"));
            this.strColourType = properties.getProperty("COLOURTYPE", "Pantone");
            this.dblDesigningCost = Double.parseDouble(properties.getProperty("DESIGNINGCOST", "0"));
            this.dblPunchingCost = Double.parseDouble(properties.getProperty("PUNCHINGCOST", "0"));
            this.dblPropertyCost = Double.parseDouble(properties.getProperty("PROPERTYCOST", "0"));
            this.dblWagesCost = Double.parseDouble(properties.getProperty("WAGESCOST", "0"));
            this.intOverheadCost = Integer.parseInt(properties.getProperty("OVERHEADCOST", "0"));
            this.intProfit = Integer.parseInt(properties.getProperty("PROFIT", "10"));
            this.strWeftName = properties.getProperty("WEFTNAME", "Silk");
            this.strWeftPattern = properties.getProperty("WEFTPATTERN", "1a");
            this.strWeftColor = properties.getProperty("WEFTCOLOR", "000000");
            this.intWeftRepeat = Integer.parseInt(properties.getProperty("WEFTREPEAT", "1"));
            this.intWeftCount = Integer.parseInt(properties.getProperty("WEFTCOUNT", "10"));
            this.strWeftUnit = properties.getProperty("WEFTUNIT", "Tex");
            this.intWeftPly = Integer.parseInt(properties.getProperty("WEFTPLY", "1"));
            this.intWeftFactor = Integer.parseInt(properties.getProperty("WEFTDFACTOR", "18"));
            this.dblWeftDiameter = Double.parseDouble(properties.getProperty("WEFTDIAMETER", "0.03"));
            this.intWeftTwist = Integer.parseInt(properties.getProperty("WEFTTWIST", "0"));
            this.strWeftSence = properties.getProperty("WEFTTSENCE", "0");
            this.intWeftHairness = Integer.parseInt(properties.getProperty("WEFTHAIRNESS", "0"));
            this.intWeftDistribution = Integer.parseInt(properties.getProperty("WEFTHDISTRIBUTION", "0"));             
            this.dblWeftPrice = Double.parseDouble(properties.getProperty("WEFTPRICE", "1.0"));
            this.intWeftCrimp = Integer.parseInt(properties.getProperty("WEFTCRIMP", "0"));  
            this.intWeftWaste = Integer.parseInt(properties.getProperty("WEFTWASTE", "0"));
            this.strWarpName = properties.getProperty("WARPNAME", "Silk");
            this.strWarpPattern = properties.getProperty("WARPPATTERN", "1A");
            this.strWarpColor = properties.getProperty("WARPCOLOR", "ffffff");
            this.intWarpRepeat = Integer.parseInt(properties.getProperty("WARPREPEAT", "1"));
            this.intWarpCount = Integer.parseInt(properties.getProperty("WARPCOUNT", "10"));
            this.strWarpUnit = properties.getProperty("WARPUNIT", "Tex");
            this.intWarpPly = Integer.parseInt(properties.getProperty("WARPPLY", "1"));
            this.intWarpFactor = Integer.parseInt(properties.getProperty("WARPDFACTOR", "18"));
            this.dblWarpDiameter = Double.parseDouble(properties.getProperty("WARPDIAMETER", "0.03"));
            this.intWarpTwist = Integer.parseInt(properties.getProperty("WARPTWIST", "0"));
            this.strWarpSence = properties.getProperty("WARPTSENCE", "0");
            this.intWarpHairness = Integer.parseInt(properties.getProperty("WARPHAIRNESS", "0"));
            this.intWarpDistribution = Integer.parseInt(properties.getProperty("WARPHDISTRIBUTION", "0"));             
            this.dblWarpPrice = Double.parseDouble(properties.getProperty("WARPPRICE", "1.0"));
            this.intWarpCrimp = Integer.parseInt(properties.getProperty("WARPCRIMP", "0"));  
            this.intWarpWaste = Integer.parseInt(properties.getProperty("WARPWASTE", "0"));
            this.mapRecentFabric.put("Body", null);
            this.mapRecentFabric.put("Palu", null);
            this.mapRecentFabric.put("Border", null);
            this.mapRecentFabric.put("Cross Border", null);
            this.mapRecentFabric.put("Blouse", null);
            this.mapRecentFabric.put("Skart", null);
            this.mapRecentFabric.put("Konia", null);
            
            // Added to save context in Cloth Editor
            this.mapRecentFabric.put("ceBody", null);
            this.mapRecentFabric.put("ceBlouse", null);
            this.mapRecentFabric.put("ceSkart", null);
            this.mapRecentFabric.put("Left Pallu", null);
            this.mapRecentFabric.put("Right Pallu", null);
            this.mapRecentFabric.put("Left Border", null);
            this.mapRecentFabric.put("Right Border", null);
            this.mapRecentFabric.put("Left Cross Border", null);
            this.mapRecentFabric.put("Right Cross Border", null);
            this.mapRecentFabric.put("Rightside Left Cross Border", null);
            this.mapRecentFabric.put("Rightside Right Cross Border", null);
			this.garmentPinch=12;
            this.garmentZoom=1.0;
            initYarnPalette();
            initColourPalette();
        } catch (IOException ex) {
            new Logging("SEVERE",Configuration.class.getName(),"intializeConfiguration()",ex);
        }
    }
    
    public void intializeClothConfiguration(String strFilePath) {
        Properties properties = null;
        try {
            properties = new Properties();
            InputStream resourceAsStream =  Configuration.class.getClassLoader().getResourceAsStream(strFilePath);
            if (resourceAsStream != null) {
                properties.load(resourceAsStream);
            }
            this.strGraphSize = properties.getProperty("GRAPHSIZE", "12x12");
            this.intFloatBind = Integer.parseInt(properties.getProperty("FLOATBIND", "3"));
            this.intProtection = Integer.parseInt(properties.getProperty("PROTECTION", "3"));
            this.intBinding = Integer.parseInt(properties.getProperty("BINDING", "3")); 
            this.strConfName = properties.getProperty("CONFNAME", "default_configuration");
            this.strDefaultFabric = properties.getProperty("DEFAULTFABRIC", "FABRICG1");
            this.strDefaultArtwork = properties.getProperty("DEFAULTARTWORK", "ARTWORKG1");
            this.strDefaultWeave = properties.getProperty("DEFAULTWEAVE", "WEAVEG2");
            this.strClothType = properties.getProperty("CLOTHTYPE", "Body");
            this.strFabricType = properties.getProperty("FABRICTYPE", "Plain");
            this.dblFabricLength = Double.parseDouble(properties.getProperty("FABRICLENGTH", "47"));
            this.dblFabricWidth = Double.parseDouble(properties.getProperty("FABRICWIDTH", "256"));
            this.dblArtworkLength = Double.parseDouble(properties.getProperty("ARTWORKLENGTH", "2.6"));
            this.dblArtworkWidth = Double.parseDouble(properties.getProperty("ARTWORKWIDTH", "2.7"));
            this.intPPI = Integer.parseInt(properties.getProperty("PPI", "63"));
            this.intEPI = Integer.parseInt(properties.getProperty("EPI", "60"));
            this.intDPI = Integer.parseInt(properties.getProperty("DPI", "100"));
            this.intHPI = Integer.parseInt(properties.getProperty("HPI", "72"));
            this.intTPD = Integer.parseInt(properties.getProperty("TPD", "2"));
            this.intHooks = Integer.parseInt(properties.getProperty("HOOKS", "240"));
            this.intEnds = Integer.parseInt(properties.getProperty("HOOKS", "240"))*Integer.parseInt(properties.getProperty("TPD", "2"));;
            this.intPixs = Integer.parseInt(properties.getProperty("PICKS", "2457"));
            this.intReedCount = Integer.parseInt(properties.getProperty("REEDCOUNT", "90"));
            this.intDents = Integer.parseInt(properties.getProperty("DENTS", "45"));
            this.intExtraWeft = Integer.parseInt(properties.getProperty("EXTRAWEFT", "0"));
            this.intExtraWarp = Integer.parseInt(properties.getProperty("EXTRAWARP", "0"));
            this.strColourType = properties.getProperty("COLOURTYPE", "Pantone");
            this.dblDesigningCost = Double.parseDouble(properties.getProperty("DESIGNINGCOST", "0"));
            this.dblPunchingCost = Double.parseDouble(properties.getProperty("PUNCHINGCOST", "0"));
            this.dblPropertyCost = Double.parseDouble(properties.getProperty("PROPERTYCOST", "0"));
            this.dblWagesCost = Double.parseDouble(properties.getProperty("WAGESCOST", "0"));
            this.intOverheadCost = Integer.parseInt(properties.getProperty("OVERHEADCOST", "0"));
            this.intProfit = Integer.parseInt(properties.getProperty("PROFIT", "10"));
            this.strWeftName = properties.getProperty("WEFTNAME", "Silk");
            this.strWeftPattern = properties.getProperty("WEFTPATTERN", "1a");
            this.strWeftColor = properties.getProperty("WEFTCOLOR", "000000");
            this.intWeftRepeat = Integer.parseInt(properties.getProperty("WEFTREPEAT", "1"));
            this.intWeftCount = Integer.parseInt(properties.getProperty("WEFTCOUNT", "10"));
            this.strWeftUnit = properties.getProperty("WEFTUNIT", "Tex");
            this.intWeftPly = Integer.parseInt(properties.getProperty("WEFTPLY", "1"));
            this.intWeftFactor = Integer.parseInt(properties.getProperty("WEFTDFACTOR", "18"));
            this.dblWeftDiameter = Double.parseDouble(properties.getProperty("WEFTDIAMETER", "0.03"));
            this.intWeftTwist = Integer.parseInt(properties.getProperty("WEFTTWIST", "0"));
            this.strWeftSence = properties.getProperty("WEFTTSENCE", "0");
            this.intWeftHairness = Integer.parseInt(properties.getProperty("WEFTHAIRNESS", "0"));
            this.intWeftDistribution = Integer.parseInt(properties.getProperty("WEFTHDISTRIBUTION", "0"));             
            this.dblWeftPrice = Double.parseDouble(properties.getProperty("WEFTPRICE", "1.0"));
            this.intWeftCrimp = Integer.parseInt(properties.getProperty("WEFTCRIMP", "0"));  
            this.intWeftWaste = Integer.parseInt(properties.getProperty("WEFTWASTE", "0"));
            this.strWarpName = properties.getProperty("WARPNAME", "Silk");
            this.strWarpPattern = properties.getProperty("WARPPATTERN", "1A");
            this.strWarpColor = properties.getProperty("WARPCOLOR", "ffffff");
            this.intWarpRepeat = Integer.parseInt(properties.getProperty("WARPREPEAT", "1"));
            this.intWarpCount = Integer.parseInt(properties.getProperty("WARPCOUNT", "10"));
            this.strWarpUnit = properties.getProperty("WARPUNIT", "Tex");
            this.intWarpPly = Integer.parseInt(properties.getProperty("WARPPLY", "1"));
            this.intWarpFactor = Integer.parseInt(properties.getProperty("WARPDFACTOR", "18"));
            this.dblWarpDiameter = Double.parseDouble(properties.getProperty("WARPDIAMETER", "0.03"));
            this.intWarpTwist = Integer.parseInt(properties.getProperty("WARPTWIST", "0"));
            this.strWarpSence = properties.getProperty("WARPTSENCE", "0");
            this.intWarpHairness = Integer.parseInt(properties.getProperty("WARPHAIRNESS", "0"));
            this.intWarpDistribution = Integer.parseInt(properties.getProperty("WARPHDISTRIBUTION", "0"));             
            this.dblWarpPrice = Double.parseDouble(properties.getProperty("WARPPRICE", "1.0"));
            this.intWarpCrimp = Integer.parseInt(properties.getProperty("WARPCRIMP", "0"));  
            this.intWarpWaste = Integer.parseInt(properties.getProperty("WARPWASTE", "0"));
            initYarnPalette();
            initColourPalette();
        } catch (IOException ex) {
            new Logging("SEVERE",Configuration.class.getName(),"intializeConfiguration()",ex);
        }
    }
    
    public void initYarnPalette() {
        yarnPalette = new Yarn[52];
        Yarn objYarn;
        for(int i=0, j=26; i<26; i++,j++){
            objYarn = new Yarn(null, "Warp", this.strWarpName, "#ffffff", this.intWarpRepeat, Character.toString((char)(65+i)), this.intWarpCount, this.strWarpUnit, this.intWarpPly, this.intWarpFactor, this.dblWarpDiameter, this.intWarpTwist, this.strWarpSence, this.intWarpHairness, this.intWarpDistribution, this.dblWarpPrice, this.objUser.getUserAccess("YARN_LIBRARY"),this.objUser.getStrUserID(),null);
            objYarn.setObjConfiguration(this);
            yarnPalette[i] = objYarn;
            objYarn = new Yarn(null, "Weft", this.strWeftName, "#ffffff", this.intWeftRepeat, Character.toString((char)(97+i)), this.intWeftCount, this.strWeftUnit, this.intWeftPly, this.intWeftFactor, this.dblWeftDiameter, this.intWeftTwist, this.strWeftSence, this.intWeftHairness, this.intWeftDistribution, this.dblWeftPrice, this.objUser.getUserAccess("YARN_LIBRARY"),this.objUser.getStrUserID(),null);
            objYarn.setObjConfiguration(this);
            yarnPalette[j] = objYarn;
        }
    }
    
    public void initColourPalette() {
        colourPalette = new String[]{"000000","161616", "B40431", "B4045F", "B40486", "B404AE", "8904B1", "5F04B4", "3104B4", "0404B4", "0431B4", "045FB4", "0489B1", "04B4AE", "04B486", "04B45F", "04B431", "04B404", "31B404", "5FB404", "86B404", "AEB404", "B18904", "B45F04", "B43104", "FFFFFF", "000000","BBA677", "B40431", "B4045F", "B40486", "B404AE", "8904B1", "5F04B4", "3104B4", "0404B4", "0431B4", "045FB4", "0489B1", "04B4AE", "04B486", "04B45F", "04B431", "04B404", "31B404", "5FB404", "86B404", "AEB404", "B18904", "B45F04", "B40404", "FFFFFF"}; 
    }
    
    public void setObjUser(User objUser) {
        this.objUser = objUser;
    }
    public User getObjUser() {
        return objUser;
    }
    
    public void setWarpYarn(Yarn[] warpYarn) {
        this.warpYarn = warpYarn;
    }
    public Yarn[] getWarpYarn() {
        return warpYarn;
    }
    
    public void setWeftYarn(Yarn[] weftYarn) {
        this.weftYarn = weftYarn;
    }
    public Yarn[] getWeftYarn() {
        return weftYarn;
    }
    
    public void setWarpExtraYarn(Yarn[] warpExtraYarn) {
        this.warpExtraYarn = warpExtraYarn;
    }
    public Yarn[] getWarpExtraYarn() {
        return warpExtraYarn;
    }
    
    public void setWeftExtraYarn(Yarn[] weftExtraYarn) {
        this.weftExtraYarn = weftExtraYarn;
    }
    public Yarn[] getWeftExtraYarn() {
        return weftExtraYarn;
    }
    
    public void setYarnPalette(Yarn[] yarnPalette) {
        this.yarnPalette = yarnPalette;
    }
    public Yarn[] getYarnPalette() {
        return yarnPalette;
    }
    
    public void setColourPalette(String[] colourPalette) {
        this.colourPalette = colourPalette;
    }
    public String[] getColourPalette() {
        return colourPalette;
    }
    
    public void setLstYarn(ArrayList<Yarn> lstYarn) {
        this.lstYarn = lstYarn;
    }
    public ArrayList<Yarn> getLstYarn() {
        return lstYarn;
    }
    
    public void setLstPattern(ArrayList<Pattern> lstPattern) {
        this.lstPattern = lstPattern;
    }
    public ArrayList<Pattern> getLstPattern() {
        return lstPattern;
    }
    
    public void setMapRecentFabric(Map<String,String> mapRecentFabric) {
        this.mapRecentFabric = mapRecentFabric;
    }
    public Map<String,String> getMapRecentFabric() {
        return mapRecentFabric;
    }
    
    public void setStrRecentArtwork(String strRecentArtwork) {
        this.strRecentArtwork = strRecentArtwork;
    }
    public String getStrRecentArtwork() {
        return strRecentArtwork;
    }
    
    public void setStrRecentWeave(String strRecentWeave) {
        this.strRecentWeave = strRecentWeave;
    }
    public String getStrRecentWeave() {
        return strRecentWeave;
    }
    
    public void setStrTemplate(String strTemplate) {
        this.strTemplate = strTemplate;
    }
    public String getStrTemplate() {
        return strTemplate;
    }
    
    public void setStrMouse(String strMouse) {
        this.strMouse = strMouse;
    }
    public String getStrMouse() {
        return strMouse;
    }
    
    public void setStrColour(String strColour) {
        this.strColour = strColour;
    }
    public String getStrColour() {
        return strColour;
    }
    
    public void setStrColourDimmed(String strColourDimmed) {
        this.strColourDimmed = strColourDimmed;
    }
    public String getStrColourDimmed() {
        return strColourDimmed;
    }
    
    public void setStrLanguage(String strLanguage) {
        this.strLanguage = strLanguage;
    }
    public String getStrLanguage() {
        return strLanguage;
    }
    
    public void setStrResolution(String strResolution) {
        this.strResolution = strResolution;
    }
    public String getStrResolution() {
        return strResolution;
    }
    
    public void setStrMeasurement(String strMeasurement) {
        this.strMeasurement = strMeasurement;
    }
    public String getStrMeasurement() {
        return strMeasurement;
    }
    
    public void setStrCurrency(String strCurrency) {
        this.strCurrency = strCurrency;
    }
    public String getStrCurrency() {
        return strCurrency;
    }
    
    public void setStrBFont(String strBFont) {
        this.strBFont = strBFont;
    }
    public String getStrBFont() {
        return strBFont;
    }
    
    public void setIntBFontSize(int intBFontSize) {
        this.intBFontSize = intBFontSize;
    }
    public int getIntBFontSize() {
        return intBFontSize;
    }
    
    public void setStrSFont(String strSFont) {
        this.strSFont = strSFont;
    }
    public String getStrSFont() {
        return strSFont;
    }
    
    public void setIntSFontSize(int intSFontSize) {
        this.intSFontSize = intSFontSize;
    }
    public int getIntSFontSize() {
        return intSFontSize;
    }
    
    public void setStrDataPath(String strDataPath) {
        this.strDataPath = strDataPath;
    }
    public String getStrDataPath() {
        return strDataPath;
    }
    
    public void setStrHelpPath(String strHelpPath) {
        this.strHelpPath = strHelpPath;
    }
    public String getStrHelpPath() {
        return strHelpPath;
    }
    
    public void setStrSavePath(String strSavePath) {
        this.strSavePath = strSavePath;
    }
    public String getStrSavePath() {
        return strSavePath;
    }
    
    public void setStrLogPath(String strLogPath) {
        this.strLogPath = strLogPath;
    }
    public String getStrLogPath() {
        return strLogPath;
    }
    
    public void setStrErrorPath(String strErrorPath) {
        this.strErrorPath = strErrorPath;
    }
    public String getStrErrorPath() {
        return strErrorPath;
    }
    
    public void setIntStackLimit(int intStackLimit) {
        this.intStackLimit = intStackLimit;
    }
    public int getIntStackLimit() {
        return intStackLimit;
    }
    
    public void setIntColorLimit(int intColorLimit) {
        this.intColorLimit = intColorLimit;
    }
    public int getIntColorLimit() {
        return intColorLimit;
    }
    
    public void setIntTimeLimit(int intTimeLimit) {
        this.intTimeLimit = intTimeLimit;
    }
    public int getIntTimeLimit() {
        return intTimeLimit;
    }
    
    public void setBlnAuthenticateService(boolean blnAuthenticateService){
        this.blnAuthenticateService=blnAuthenticateService;
    }
    public boolean getBlnAuthenticateService(){
        return blnAuthenticateService;
    }
    
    public void setObjProcessProcessBuilder(Process objProcess_ProcessBuilder){
        this.objProcess_ProcessBuilder = objProcess_ProcessBuilder;
    }
    public Process getObjProcessProcessBuilder(){
        return objProcess_ProcessBuilder;
    }
    
    // Added 21 Feb 2017 (for PopUp Service Password Dailog)--
    public void setServicePasswordValid(boolean value){
        this.servicePasswordValid=value;
    }
    public boolean getServicePasswordValid(){
        return servicePasswordValid;
    }
    
    
    public void setIntBoxSize(int intBoxSize) {
        this.intBoxSize = intBoxSize;
    }
    public int getIntBoxSize() {
        return intBoxSize;
    }    
            
    public void setStrAllowShare(String strAllowShare) {
        this.strAllowShare = strAllowShare;
    }
    public String getStrAllowShare() {
        return strAllowShare;
    }
    
    public void setIntDPI(int intDPI) {
        this.intDPI = intDPI;
    }
    public int getIntDPI() {
        return intDPI;
    }
    public int findIntDPI() {
        intDPI = Integer.parseInt(strResolution.substring(0 , strResolution.indexOf("x")))/17;
        return intDPI = 100;
    }
    
    public void setStrClothType(String strClothType) {
        this.strClothType = strClothType;
    }
    public String getStrClothType() {
        return strClothType;
    }
    public void clothRepeat(){
        /*
        Body Horizontal           6
        Body Vertical             2 
        Palu Horizontal           2
        Palu Vertical             2
        Border Horizontal         6
        Broder Vertical           1
        Cross Border Horizontal   1
        Cross Border Vertical     2
        Blouse Horizontal         2
        Blouse Vertical           2
        Konia Horizontal         1
        Konia Vertical           1
        */
        if(strClothType.equalsIgnoreCase("Body")){
            this.setIntVRepeat(2);
            this.setIntHRepeat(6);
        } else if(strClothType.equalsIgnoreCase("Palu")){
            this.setIntVRepeat(2);
            this.setIntHRepeat(2);
        } else if(strClothType.equalsIgnoreCase("Border")){
            this.setIntVRepeat(2);
            this.setIntHRepeat(1);
        } else if(strClothType.equalsIgnoreCase("Cross Border")){
            this.setIntVRepeat(1);
            this.setIntHRepeat(2);
        } else if(strClothType.equalsIgnoreCase("Blouse")){
            this.setIntVRepeat(2);
            this.setIntHRepeat(2);
        } else if(strClothType.equalsIgnoreCase("Skart")){
            this.setIntVRepeat(2);
            this.setIntHRepeat(1);
        } else if(strClothType.equalsIgnoreCase("Konia")){
            this.setIntVRepeat(1);
            this.setIntHRepeat(1);
        } else{
            this.setIntVRepeat(2);
            this.setIntHRepeat(1);
        }
        if(dblArtworkLength>(dblFabricLength/2))
            this.setIntVRepeat(1);
        if(dblArtworkWidth>(dblFabricWidth/2))
            this.setIntHRepeat(1);
    }
    
    public void setIntVRepeat(int intVRepeat) {
        this.intVRepeat = intVRepeat;
    }
    public int getIntVRepeat() {
        return intVRepeat;
    }
    
    public void setIntHRepeat(int intHRepeat) {
        this.intHRepeat = intHRepeat;
    }
    public int getIntHRepeat() {
        return intHRepeat;
    }
        
    public void setBlnMRepeat(boolean blnMRepeat) {
        this.blnMRepeat = blnMRepeat;
    }
    public boolean getBlnMRepeat() {
        return blnMRepeat;
    }
    
    public void setBlnMPrint(boolean blnMPrint) {
        this.blnMPrint = blnMPrint;
    }
    public boolean getBlnMPrint() {
        return blnMPrint;
    }
    
    public void setBlnPunchCard(boolean blnPunchCard) {
        this.blnPunchCard = blnPunchCard;
    }
    public boolean getBlnPunchCard() {
        return blnPunchCard;
    }  
    
    public void setBlnErrorGraph(boolean blnErrorGraph) {
        this.blnErrorGraph = blnErrorGraph;
    }
    public boolean getBlnErrorGraph() {
        return blnErrorGraph;
    }
    
    public void setBlnCorrectGraph(boolean blnCorrectGraph) {
        this.blnCorrectGraph = blnCorrectGraph;
    }
    public boolean getBlnCorrectGraph() {
        return blnCorrectGraph;
    }  
    
    public void setStrGraphSize(String strGraphSize) {
        this.strGraphSize = strGraphSize;
    }
    public String getStrGraphSize() {
        return strGraphSize;
    }
    public String findStrGraphSize() {
        strGraphSize = 12+"x"+(int)((12*intPPI)/intEPI);
        return strGraphSize;
    }
    
    public void setStrFabricType(String strFabricType) {
        this.strFabricType = strFabricType;
    }
    public String getStrFabricType() {
        return strFabricType;
    }
    
    public void setStrConfName(String strConfName) {
        this.strConfName = strConfName;
    }
    public String getStrConfName() {
        return strConfName;
    }
    
    public void setStrDefaultFabric(String strDefaultFabric) {
        this.strDefaultFabric = strDefaultFabric;
    }
    public String getStrDefaultFabric() {
        return strDefaultFabric;
    }
    
    public void setStrDefaultArtwork(String strDefaultArtwork) {
        this.strDefaultArtwork = strDefaultArtwork;
    }
    public String getStrDefaultArtwork() {
        return strDefaultArtwork;
    }
    
    public void setStrDefaultWeave(String strDefaultWeave) {
        this.strDefaultWeave = strDefaultWeave;
    }
    public String getStrDefaultWeave() {
        return strDefaultWeave;
    }
    
    public void setDblFabricLength(double dblFabricLength) {
        this.dblFabricLength = dblFabricLength;
    }
    public double getDblFabricLength() {
        return dblFabricLength;
    }
    
    public void setDblFabricWidth(double dblFabricWidth) {
        this.dblFabricWidth = dblFabricWidth;
    }
    public double getDblFabricWidth() {
        return dblFabricWidth;
    }
    
    public void setIntPixs(int intPixs) {
        this.intPixs = intPixs;
    }
    public int getIntPixs() {
        return intPixs;
    }
    
    public void setIntPPI(int intPPI) {
        this.intPPI = intPPI;
    }
    public int getIntPPI() {
        return intPPI;
    }
    /*public int findIntPPI() {
        intPPI = (int)(dblArtworkLength*intPixs);
        return intPPI;
    }*/
    
    public void setDblArtworkLength(double dblArtworkLength) {
        this.dblArtworkLength = dblArtworkLength;
    }
    public double getDblArtworkLength() {
        return dblArtworkLength;
    }
    public double findDblArtworkLength() {
        dblArtworkLength = ((double) intPixs)/intPPI;
        dblArtworkLength = dblArtworkLength*10000;
        dblArtworkLength = Math.round(dblArtworkLength);
        dblArtworkLength = dblArtworkLength/10000;
        return dblArtworkLength;
    }
    
    public void setIntHooks(int intHooks) {
        this.intHooks = intHooks;
    }
    public int getIntHooks() {
        return intHooks;
    }
    
    public void setIntEnds(int intEnds) {
        this.intEnds = intEnds;
    }
    public int getIntEnds() {
        return intEnds;
    }
    public int findIntEnds() {
        intEnds = intHooks*intTPD;
        return intEnds;
    }
    
    public void setIntReedCount(int intReedCount) {
        this.intReedCount = intReedCount;
    }
    public int getIntReedCount() {
        return intReedCount;
    }
    /*public int findIntReedCount() {
        intReedCount = (2*intEPI)/intDents;
        return intReedCount;
    }*/
    
    public void setIntTPD(int intTPD) {
        this.intTPD = intTPD;
    }
    public int getIntTPD() {
        return intTPD;
    }
    /*public int findIntTPD() {
        intTPD = (2*intEPI)/intReedCount;
        return intDents;
    }*/
    
    public void setIntDents(int intDents) {
        this.intDents = intDents;
    }
    public int getIntDents() {
        return intDents;
    }
    public int findIntDents() {
        intDents = (int)(intReedCount/2);
        return intDents;
    }
    
    public void setIntHPI(int intHPI) {
        this.intHPI = intHPI;
    }
    public int getIntHPI() {
        return intHPI;
    }
    public int findIntHPI() {
        intHPI = (int)(intReedCount/2);
        //intHPI = (int)(intHooks/dblArtworkWidth);
        return intHPI;
    }
    
    public void setIntEPI(int intEPI) {
        this.intEPI = intEPI;
    }
    public int getIntEPI() {
        return intEPI;
    }
    public int findIntEPI() {
        intEPI = (int)(intReedCount*intTPD/2);
        return intEPI;
    }
    
    public void setDblArtworkWidth(double dblArtworkWidth) {
        this.dblArtworkWidth = dblArtworkWidth;
    }
    public double getDblArtworkWidth() {
        return dblArtworkWidth;
    }
    public double findDblArtworkWidth() {
        dblArtworkWidth = ((double) intHooks)/intHPI;
        dblArtworkWidth = dblArtworkWidth*10000;
        dblArtworkWidth = Math.round(dblArtworkWidth);
        dblArtworkWidth = dblArtworkWidth/10000;
        return dblArtworkWidth;
    }
    
    
    public void setIntFloatBind(int intFloatBind) {
        this.intFloatBind = intFloatBind;
    }
    public int getIntFloatBind() {
        return intFloatBind;
    }
    
    public void setIntProtection(int intProtection) {
        this.intProtection = intProtection;
    }
    public int getIntProtection() {
        return intProtection;
    }
    
    public void setIntBinding(int intBinding) {
        this.intBinding = intBinding;
    }
    public int getIntBinding() {
        return intBinding;
    }
    
    public void setBlnArtworkAssingmentSize(boolean blnArtworkAssingmentSize) {
        this.blnArtworkAssingmentSize = blnArtworkAssingmentSize;
    }
    public boolean getBlnArtworkAssingmentSize() {
        return blnArtworkAssingmentSize;
    }
    
    public void setBlnArtworkOutline(boolean blnArtworkOutline) {
        this.blnArtworkOutline = blnArtworkOutline;
    }
    public boolean getBlnArtworkOutline() {
        return blnArtworkOutline;
    }
    
    public void setBlnArtworkAspectRatio(boolean blnArtworkAspectRatio) {
        this.blnArtworkAspectRatio = blnArtworkAspectRatio;
    }
    public boolean getBlnArtworkAspectRatio() {
        return blnArtworkAspectRatio;
    }
    
    public void setStrColourType(String strColourType) {
        this.strColourType = strColourType;
    }
    public String getStrColourType() {
        return strColourType;
    }

    public void setDblDesigningCost(double dblDesigningCost) {
        this.dblDesigningCost = dblDesigningCost;
    }
    public double getDblDesigningCost() {
        return dblDesigningCost;
    }
    
    public void setDblPunchingCost(double dblPunchingCost) {
        this.dblPunchingCost = dblPunchingCost;
    }
    public double getDblPunchingCost() {
        return dblPunchingCost;
    }
    
    public void setDblPropertyCost(double dblPropertyCost) {
        this.dblPropertyCost = dblPropertyCost;
    }
    public double getDblPropertyCost() {
        return dblPropertyCost;
    }
    
    public void setDblWagesCost(double dblWagesCost) {
        this.dblWagesCost = dblWagesCost;
    }
    public double getDblWagesCost() {
        return dblWagesCost;
    }
    
    public void setIntOverheadCost(int intOverheadCost) {
        this.intOverheadCost = intOverheadCost;
    }
    public int getIntOverheadCost() {
        return intOverheadCost;
    }
    
    public void setIntProfit(int intProfit) {
        this.intProfit = intProfit;
    }
    public int getIntProfit() {
        return intProfit;
    }
            
    public void setStrWeftName(String strWeftName) {
        this.strWeftName = strWeftName;
    }
    public String getStrWeftName() {
        return strWeftName;
    }

    public void setStrWeftPattern(String strWeftPattern) {
        this.strWeftPattern = strWeftPattern;
    }
    public String getStrWeftPattern() {
        return strWeftPattern;
    }
    
    public void setStrWeftColor(String strWeftColor) {
        this.strWeftColor = strWeftColor;
    }
    public String getStrWeftColor() {
        return strWeftColor;
    }
    
    public void setIntWeftRepeat(int intWeftRepeat) {
        this.intWeftRepeat = intWeftRepeat;
    }
    public int getIntWeftRepeat() {
        return intWeftRepeat;
    }
    
    public void setIntWeftCount(int intWeftCount) {
        this.intWeftCount = intWeftCount;
    }
    public int getIntWeftCount() {
        return intWeftCount;
    }
    
    public void setStrWeftUnit(String strWeftUnit) {
        this.strWeftUnit = strWeftUnit;
    }
    public String getStrWeftUnit() {
        return strWeftUnit;
    }
    
    public void setIntWeftPly(int intWeftPly) {
        this.intWeftPly = intWeftPly;
    }
    public int getIntWeftPly() {
        return intWeftPly;
    }
    
    public void setIntWeftFactor(int intWeftFactor) {
        this.intWeftFactor = intWeftFactor;
    }
    public int getIntWeftFactor() {
        return intWeftFactor;
    }
    
    public void setDblWeftDiameter(double dblWeftDiameter) {
        this.dblWeftDiameter = dblWeftDiameter;
    }
    public double getDblWeftDiameter() {
        return dblWeftDiameter;
    }
    
    public void setIntWeftTwist(int intWeftTwist) {
        this.intWeftTwist = intWeftTwist;
    }
    public int getIntWeftTwist() {
        return intWeftTwist;
    }
    
    public void setStrWeftSence(String strWeftSence) {
        this.strWeftSence = strWeftSence;
    }
    public String getStrWeftSence() {
        return strWeftSence;
    }
    
    public void setIntWeftHairness(int intWeftHairness) {
        this.intWeftHairness = intWeftHairness;
    }
    public int getIntWeftHairness() {
        return intWeftHairness;
    }
    
    public void setIntWeftDistribution(int intWeftDistribution) {
        this.intWeftDistribution = intWeftDistribution;
    }
    public int getIntWeftDistribution() {
        return intWeftDistribution;
    }
    
    public void setDblWeftPrice(double dblWeftPrice) {
        this.dblWeftPrice = dblWeftPrice;
    }
    public double getDblWeftPrice() {
        return dblWeftPrice;
    }    
    
    public void setIntWeftCrimp(int intWeftCrimp) {
        this.intWeftCrimp = intWeftCrimp;
    }
    public int getIntWeftCrimp() {
        return intWeftCrimp;
    }
    
    public void setIntWeftWaste(int intWeftWaste) {
        this.intWeftWaste = intWeftWaste;
    }
    public int getIntWeftWaste() {
        return intWeftWaste;
    }
    
    public void setDblWeftWeight(double dblWeftWeight) {
        this.dblWeftWeight = dblWeftWeight;
    }
    public double getDblWeftWeight() {
        return dblWeftWeight;
    }    
    
    public void setDblWeftLong(double dblWeftLong) {
        this.dblWeftLong = dblWeftLong;
    }
    public double getDblWeftLong() {
        return dblWeftLong;
    }
    
    public void setDblWeftNumber(double dblWeftNumber) {
        this.dblWeftNumber = dblWeftNumber;
    }
    public double getDblWeftNumber() {
        return dblWeftNumber;
    }
    
    public void setDblExtraWeftWeight(double dblExtraWeftWeight) {
        this.dblExtraWeftWeight = dblExtraWeftWeight;
    }
    public double getDblExtraWeftWeight() {
        return dblExtraWeftWeight;
    }
    public void setDblExtraWeftLong(double dblExtraWeftLong) {
        this.dblExtraWeftLong = dblExtraWeftLong;
    }
    public double getDblExtraWeftLong() {
        return dblExtraWeftLong;
    }
    public void setDblExtraWeftNumber(double dblExtraWeftNumber) {
        this.dblExtraWeftNumber = dblExtraWeftNumber;
    }
    public double getDblExtraWeftNumber() {
        return dblExtraWeftNumber;
    }
    public void setIntExtraWeft(int intExtraWeft) {
        this.intExtraWeft = intExtraWeft;
    }
    public int getIntExtraWeft() {
        return intExtraWeft;
    }

    public void setStrWarpName(String strWarpName) {
        this.strWarpName = strWarpName;
    }
    public String getStrWarpName() {
        return strWarpName;
    }
    public void setStrWarpPattern(String strWarpPattern) {
        this.strWarpPattern = strWarpPattern;
    }
    public String getStrWarpPattern() {
        return strWarpPattern;
    }
    public void setStrWarpColor(String strWarpColor) {
        this.strWarpColor = strWarpColor;
    }
    public String getStrWarpColor() {
        return strWarpColor;
    }
    public void setIntWarpRepeat(int intWarpRepeat) {
        this.intWarpRepeat = intWarpRepeat;
    }
    public int getIntWarpRepeat() {
        return intWarpRepeat;
    }
    public void setIntWarpCount(int intWarpCount) {
        this.intWarpCount = intWarpCount;
    }
    public int getIntWarpCount() {
        return intWarpCount;
    }
    public void setStrWarpUnit(String strWarpUnit) {
        this.strWarpUnit = strWarpUnit;
    }
    public String getStrWarpUnit() {
        return strWarpUnit;
    }
    public void setIntWarpPly(int intWarpPly) {
        this.intWarpPly = intWarpPly;
    }
    public int getIntWarpPly() {
        return intWarpPly;
    }
    public void setIntWarpFactor(int intWarpFactor) {
        this.intWarpFactor = intWarpFactor;
    }
    public int getIntWarpFactor() {
        return intWarpFactor;
    }
    public void setDblWarpDiameter(double dblWarpDiameter) {
        this.dblWarpDiameter = dblWarpDiameter;
    }
    public double getDblWarpDiameter() {
        return dblWarpDiameter;
    }
    public void setIntWarpTwist(int intWarpTwist) {
        this.intWarpTwist = intWarpTwist;
    }
    public int getIntWarpTwist() {
        return intWarpTwist;
    }
    public void setStrWarpSence(String strWarpSence) {
        this.strWarpSence = strWarpSence;
    }
    public String getStrWarpSence() {
        return strWarpSence;
    }
    public void setIntWarpHairness(int intWarpHairness) {
        this.intWarpHairness = intWarpHairness;
    }
    public int getIntWarpHairness() {
        return intWarpHairness;
    }
    public void setIntWarpDistribution(int intWarpDistribution) {
        this.intWarpDistribution = intWarpDistribution;
    }
    public int getIntWarpDistribution() {
        return intWarpDistribution;
    }
    public void setDblWarpPrice(double dblWarpPrice) {
        this.dblWarpPrice = dblWarpPrice;
    }
    public double getDblWarpPrice() {
        return dblWarpPrice;
    }
    public void setIntWarpCrimp(int intWarpCrimp) {
        this.intWarpCrimp = intWarpCrimp;
    }
    public int getIntWarpCrimp() {
        return intWarpCrimp;
    }
    public void setIntWarpWaste(int intWarpWaste) {
        this.intWarpWaste = intWarpWaste;
    }
    public int getIntWarpWaste() {
        return intWarpWaste;
    }
    public void setDblWarpWeight(double dblWarpWeight) {
        this.dblWarpWeight = dblWarpWeight;
    }
    public double getDblWarpWeight() {
        return dblWarpWeight;
    }
    public void setDblWarpLong(double dblWarpLong) {
        this.dblWarpLong = dblWarpLong;
    }
    public double getDblWarpLong() {
        return dblWarpLong;
    }
    public void setDblWarpNumber(double dblWarpNumber) {
        this.dblWarpNumber = dblWarpNumber;
    }
    public double getDblWarpNumber() {
        return dblWarpNumber;
    }
    
    public void setDblExtraWarpWeight(double dblExtraWarpWeight) {
        this.dblExtraWarpWeight = dblExtraWarpWeight;
    }
    public double getDblExtraWarpWeight() {
        return dblExtraWarpWeight;
    }
    public void setDblExtraWarpLong(double dblExtraWarpLong) {
        this.dblExtraWarpLong = dblExtraWarpLong;
    }
    public double getDblExtraWarpLong() {
        return dblExtraWarpLong;
    }
    public void setDblExtraWarpNumber(double dblExtraWarpNumber) {
        this.dblExtraWarpNumber = dblExtraWarpNumber;
    }
    public double getDblExtraWarpNumber() {
        return dblExtraWarpNumber;
    }
    public void setIntExtraWarp(int intExtraWarp) {
        this.intExtraWarp = intExtraWarp;
    }
    public int getIntExtraWarp() {
        return intExtraWarp;
    }
	public void setGarmentPinch(int garmentPinch) {
        this.garmentPinch = garmentPinch;
    }
    public int getGarmentPinch() {
        return garmentPinch;
    }
    public void setGarmentZoom(double garmentZoom) {
        this.garmentZoom = garmentZoom;
    }
    public double getGarmentZoom() {
        return garmentZoom;
    }
}