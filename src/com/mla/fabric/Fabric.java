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

import com.mla.main.Configuration;
import com.mla.yarn.Yarn;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
/**
 * Fabric Class
 * <p>
 * This class is used for defining accessor and mutator methods for Fabric properties.
 *
 * @author Amit Kumar Singh
 * @version %I%, %G%
 * @since   1.0
 * @date 07/01/2016
 * @Designing accessor and mutator methods for Fabric
 */
public class Fabric {
    
    private Configuration objConfiguration;
    
    private String strSearchBy;
    private String strCondition;
    private String strOrderBy;
    private String strDirection;
    
    ArrayList<Yarn> lstYarn = null;
    ArrayList<Pattern> lstPattern = null;
    
    private String strFabricID;
    private String strFabricName;
    private String strFabricType;
    private String strClothType;
    private String strColourType;
    private double dblFabricLength;
    private double dblFabricWidth;
    private double dblArtworkLength;
    private double dblArtworkWidth;
    private String strArtworkID;
    private String strBaseWeaveID;
    private int intExtraWarp;
    private int intExtraWeft;
    private int intWarp;
    private int intWeft;
    private int intHooks;
    private int intPPI;
    private int intEPI;
    private int intHPI;
    private int intReedCount;
    private int intDents;
    private int intTPD;
    private int intShaft;
    private int intProtection;
    private int intBinding;
    private boolean blnArtworkAssingmentSize;
    private boolean blnArtworkOutline;
    private boolean blnArtworkAspectRatio;
    private String strWarpPatternID;
    private String strWeftPatternID;
    private byte[] bytFabricIcon;
    private String strFabricFile;
    private String strFabricRData;
    private String strFabricDate;
    private String strFabricAccess;
    
    private Yarn[] warpYarn;
    private Yarn[] weftYarn;
    private Yarn[] warpExtraYarn;
    private Yarn[] weftExtraYarn;
    private byte [][] baseWeaveMatrix;
    private byte [][] artworkMatrix;  
    private byte [][] fabricMatrix;
    private byte [][] reverseMatrix;
    
    private String[] ThreadPaletes;
    private String[] ExtraThreadPaletes;
    private String[][] colorWeave;
    private String colorArtwork;
    private int colorCountArtwork;
    private BufferedImage yarnBI;
    
    public void setStrFabricID(String strFabricID) {
        this.strFabricID = strFabricID;
    }
    public String getStrFabricID() {
        return strFabricID;
    }
    
    public void setStrFabricName(String strFabricName) {
        this.strFabricName = strFabricName;
    }
    public String getStrFabricName() {
        return strFabricName;
    }
    
    public void setStrFabricType(String strFabricType) {
        this.strFabricType = strFabricType;
    }
    public String getStrFabricType() {
        return strFabricType;
    }
    
    public void setStrClothType(String strClothType) {
        this.strClothType = strClothType;
    }
    public String getStrClothType() {
        return strClothType;
    }
    
    public void setStrColourType(String strColourType) {
        this.strColourType = strColourType;
    }
    public String getStrColourType() {
        return strColourType;
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
    
    public void setDblArtworkLength(double dblArtworkLength) {
        this.dblArtworkLength = dblArtworkLength;
    }
    public double getDblArtworkLength() {
        return dblArtworkLength;
    }
    
    public void setDblArtworkWidth(double dblArtworkWidth) {
        this.dblArtworkWidth = dblArtworkWidth;
    }
    public double getDblArtworkWidth() {
        return dblArtworkWidth;
    }
    
    public void setStrArtworkID(String strArtworkID) {
        this.strArtworkID = strArtworkID;
    }
    public String getStrArtworkID() {
        return strArtworkID;
    }
    
    public void setStrBaseWeaveID(String strBaseWeaveID) {
        this.strBaseWeaveID = strBaseWeaveID;
    }
    public String getStrBaseWeaveID() {
        return strBaseWeaveID;
    }
    
    public void setIntWeft(int intWeft) {
        this.intWeft = intWeft;
    }
    public int getIntWeft() {
        return intWeft;
    }

    public void setIntWarp(int intWarp) {
        this.intWarp = intWarp;
    }
    public int getIntWarp() {
        return intWarp;
    }
    
    public void setIntExtraWeft(int intExtraWeft) {
        this.intExtraWeft = intExtraWeft;
    }
    public int getIntExtraWeft() {
        return intExtraWeft;
    }

    public void setIntExtraWarp(int intExtraWarp) {
        this.intExtraWarp = intExtraWarp;
    }
    public int getIntExtraWarp() {
        return intExtraWarp;
    }
    
    public void setIntPPI(int intPPI) {
        this.intPPI = intPPI;
    }
    public int getIntPPI() {
        return intPPI;
    }
    
    public void setIntEPI(int intEPI) {
        this.intEPI = intEPI;
    }
    public int getIntEPI() {
        return intEPI;
    }
    
    public void setIntHooks(int intHooks) {
        this.intHooks = intHooks;
    }
    public int getIntHooks() {
        return intHooks;
    }
    
    public void setIntHPI(int intHPI) {
        this.intHPI = intHPI;
    }
    public int getIntHPI() {
        return intHPI;
    }
    
    public void setIntReedCount(int intReedCount) {
        this.intReedCount = intReedCount;
    }
    public int getIntReedCount() {
        return intReedCount;
    }
    
    public void setIntDents(int intDents) {
        this.intDents = intDents;
    }
    public int getIntDents() {
        return intDents;
    }

    public void setIntTPD(int intTPD) {
        this.intTPD = intTPD;
    }
    public int getIntTPD() {
        return intTPD;
    }
    
    public void setIntShaft(int intShaft) {
        this.intShaft = intShaft;
    }
    public int getIntShaft() {
        return intShaft;
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
    
    public void setStrWarpPatternID(String strWarpPatternID) {
        this.strWarpPatternID = strWarpPatternID;
    }
    public String getStrWarpPatternID() {
        return strWarpPatternID;
    }
    
    public void setStrWeftPatternID(String strWeftPatternID) {
        this.strWeftPatternID = strWeftPatternID;
    }
    public String getStrWeftPatternID() {
        return strWeftPatternID;
    }
    
    public byte[] getBytFabricIcon() {
        return bytFabricIcon;
    }
    public void setBytFabricIcon(byte[] bytFabricIcon) {
        this.bytFabricIcon=bytFabricIcon;		
    }
    
    public String getStrFabricFile() {
        return strFabricFile;
    }
    public void setStrFabricFile(String strFabricFile) {
        this.strFabricFile=strFabricFile;		
    }
    
    public String getStrFabricRData() {
        return strFabricRData;
    }
    public void setStrFabricRData(String strFabricRData) {
        this.strFabricRData=strFabricRData;		
    }
    
    public void setStrFabricDate(String strFabricDate) {
        this.strFabricDate = strFabricDate;
    }
    public String getStrFabricDate() {
        return strFabricDate;
    }
    
    public void setStrFabricAccess(String strFabricAccess) {
        this.strFabricAccess = strFabricAccess;
    }
    public String getStrFabricAccess() {
        return strFabricAccess;
    }
    
    public void setObjConfiguration(Configuration objConfiguration) {
        this.objConfiguration = objConfiguration;
    }
    public Configuration getObjConfiguration() {
        return objConfiguration;
    }
    
    public byte[][] getBaseWeaveMatrix() {
        return baseWeaveMatrix;
    }
    public void setBaseWeaveMatrix(byte[][] baseWeaveMatrix) {
        this.baseWeaveMatrix=baseWeaveMatrix;		
    }
    
    public byte[][] getArtworkMatrix() {
        return artworkMatrix;
    }
    public void setArtworkMatrix(byte[][] artworkMatrix) {
        this.artworkMatrix=artworkMatrix;		
    }
    
    public byte[][] getReverseMatrix() {
        return reverseMatrix;
    }
    public void setReverseMatrix(byte[][] reverseMatrix) {
        this.reverseMatrix=reverseMatrix;		
    }
    
    public byte[][] getFabricMatrix() {
        return fabricMatrix;
    }
    public void setFabricMatrix(byte[][] fabricMatrix) {
        this.fabricMatrix=fabricMatrix;		
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
    
    public void setColorWeave(String[][] colorWeave) {
        this.colorWeave = colorWeave;
    }
    public String[][] getColorWeave() {
        return colorWeave;
    }
    
    public void setColorArtwork(String colorArtwork) {
        this.colorArtwork = colorArtwork;
    }
    public String getColorArtwork() {
        return colorArtwork;
    }
    
    public void setColorCountArtwork(int colorCountArtwork) {
        this.colorCountArtwork = colorCountArtwork;
    }
    public int getColorCountArtwork() {
        return colorCountArtwork;
    }
    
    public void setThreadPaletes(String[]ThreadPaletes) {
        this.ThreadPaletes = ThreadPaletes;
    }
    public String[] getThreadPaletes() {
        return ThreadPaletes;
    }
    
    public void setExtraThreadPaletes(String[]ExtraThreadPaletes) {
        this.ExtraThreadPaletes = ExtraThreadPaletes;
    }
    public String[] getExtraThreadPaletes() {
        return ExtraThreadPaletes;
    }
    
    public BufferedImage getYarnBI() {
        return yarnBI;
    }
    public void setYarnBI(BufferedImage yarnBI) {
        this.yarnBI=yarnBI;		
    }
    
    public String getStrCondition() {
        return strCondition;
    }
    public void setStrCondition(String strCondition) {
        this.strCondition=strCondition;		
    }
    
    public String getStrOrderBy() {
        return strOrderBy;
    }
    public void setStrOrderBy(String strOrderBy) {
        this.strOrderBy=strOrderBy;		
    }
    
    public String getStrSearchBy() {
        return strSearchBy;
    }
    public void setStrSearchBy(String strSearchBy) {
        this.strSearchBy=strSearchBy;		
    }
    
    public String getStrDirection() {
        return strDirection;
    }
    public void setStrDirection(String strDirection) {
        this.strDirection=strDirection;		
    }
}