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
package com.mla.weave;

import com.mla.fabric.Pattern;
import com.mla.main.Configuration;
import com.mla.yarn.Yarn;
import java.util.ArrayList;
/**
 * Weave Class
 * <p>
 * This class is used for defining accessor and mutator methods for weave properties.
 *
 * @author Amit Kumar Singh
 * @version %I%, %G%
 * @since   1.0
 * @date 07/01/2016
 * @Designing accessor and mutator methods for weave
 */
public class Weave {
    
    private Configuration objConfiguration;
    
    private String strSearchBy;
    private String strCondition;
    private String strOrderBy;
    private String strDirection;

    ArrayList<Yarn> lstYarn = null;
    ArrayList<Pattern> lstPattern = null;

    private String strWeaveID;
    private String strWeaveName;
    private String strWeaveType;
    private String strWeaveCategory;
    private String strWeaveFile;
    private String strWeaveDate;
    private String strWeaveAccess;
    private byte bytIsLiftPlan;
    private byte bytIsColor;
    private int intWeaveRepeatX;
    private int intWeaveRepeatY;
    private int intWeaveFloatX;
    private int intWeaveFloatY;
    private int intWeaveShaft;
    private int intWeaveTreadles;
    private int intWeft;
    private int intWarp;
    private int intEPI;
    private int intPPI;
        
    private byte[] bytWeaveThumbnil;
    
    private byte [][]shaftMatrix;
    private byte [][]treadlesMatrix;
    private byte [][]tieupMatrix;
    private byte [][]pegMatrix;
    private byte [][]designMatrix;
    private byte [][]singleMatrix;
    private byte [][]clipMatrix;
    private byte [][]dentMatrix;
    //private String[] strThreadPalettes;

    private Yarn[] warpYarn;
    private Yarn[] weftYarn;
    
    public void setObjConfiguration(Configuration objConfiguration) {
        this.objConfiguration = objConfiguration;
    }
    public Configuration getObjConfiguration() {
        return objConfiguration;
    }
    
    public int getIntWeft() {
        return intWeft;
    }
    public void setIntWeft(int intWeft) {
        this.intWeft=intWeft;		
    }
    
    public int getIntWarp() {
        return intWarp;
    }
    public void setIntWarp(int intWarp) {
        this.intWarp=intWarp;		
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
    
    public byte[][] getShaftMatrix() {
        return shaftMatrix;
    }
    public void setShaftMatrix(byte[][] shaftMatrix) {
        this.shaftMatrix=shaftMatrix;		
    }
    
    public byte[][] getTreadlesMatrix() {
        return treadlesMatrix;
    }
    public void setTreadlesMatrix(byte[][] treadlesMatrix) {
        this.treadlesMatrix=treadlesMatrix;		
    }
    
    public byte[][] getTieupMatrix() {
        return tieupMatrix;
    }
    public void setTieupMatrix(byte[][] tieupMatrix) {
        this.tieupMatrix=tieupMatrix;		
    }
    
    public byte[][] getPegMatrix() {
        return pegMatrix;
    }
    public void setPegMatrix(byte[][] pegMatrix) {
        this.pegMatrix=pegMatrix;		
    }
    
    public byte[][] getDesignMatrix() {
        return designMatrix;
    }
    public void setDesignMatrix(byte[][] designMatrix) {
        this.designMatrix=designMatrix;		
    }
    
    public byte[][] getSingleMatrix() {
        return singleMatrix;
    }
    public void setSingleMatrix(byte[][] singleMatrix) {
        this.singleMatrix=singleMatrix;		
    }    
 
    public byte[][] getClipMatrix() {
        return clipMatrix;
    }
    public void setClipMatrix(byte[][] clipMatrix) {
        this.clipMatrix=clipMatrix;		
    }
    
    public byte[][] getDentMatrix() {
        return dentMatrix;
    }
    public void setDentMatrix(byte[][] dentMatrix) {
        this.dentMatrix=dentMatrix;		
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
    
    public String getStrWeaveID() {
        return strWeaveID;
    }
    public void setStrWeaveID(String strWeaveID) {
        this.strWeaveID=strWeaveID;		
    }
    
    public String getStrWeaveName() {
        return strWeaveName;
    }
    public void setStrWeaveName(String strWeaveName) {
        this.strWeaveName=strWeaveName;		
    }
    
    public String getStrWeaveFile() {
        return strWeaveFile;
    }
    public void setStrWeaveFile(String strWeaveFile) {
        this.strWeaveFile=strWeaveFile;		
    }
    
    public String getStrWeaveType() {
        return strWeaveType;
    }
    public void setStrWeaveType(String strWeaveType) {
        this.strWeaveType=strWeaveType;		
    }
    
    public String getStrWeaveCategory() {
        return strWeaveCategory;
    }
    public void setStrWeaveCategory(String strWeaveCategory) {
        this.strWeaveCategory=strWeaveCategory;		
    }
    
    public String getStrWeaveAccess() {
        return strWeaveAccess;
    }
    public void setStrWeaveAccess(String strWeaveAccess) {
        this.strWeaveAccess=strWeaveAccess;		
    }
    
    public String getStrWeaveDate() {
        return strWeaveDate;
    }
    public void setStrWeaveDate(String strWeaveDate) {
        this.strWeaveDate=strWeaveDate;		
    }
    
    public byte[] getBytWeaveThumbnil() {
        return bytWeaveThumbnil;
    }
    public void setBytWeaveThumbnil(byte[] bytWeaveThumbnil) {
        this.bytWeaveThumbnil=bytWeaveThumbnil;		
    }
    
    public byte getBytIsLiftPlan() {
        return bytIsLiftPlan;
    }
    public void setBytIsLiftPlan(byte bytIsLiftPlan) {
        this.bytIsLiftPlan=bytIsLiftPlan;		
    }
    
    public byte getBytIsColor() {
        return bytIsColor;
    }
    public void setBytIsColor(byte bytIsColor) {
        this.bytIsColor=bytIsColor;		
    }
    
    public int getIntWeaveRepeatX() {
        return intWeaveRepeatX;
    }
    public void setIntWeaveRepeatX(int intWeaveRepeatX) {
        this.intWeaveRepeatX=intWeaveRepeatX;		
    }
    
    public int getIntWeaveRepeatY() {
        return intWeaveRepeatY;
    }
    public void setIntWeaveRepeatY(int intWeaveRepeatY) {
        this.intWeaveRepeatY=intWeaveRepeatY;		
    }
    
    public int getIntWeaveFloatX() {
        return intWeaveFloatX;
    }
    public void setIntWeaveFloatX(int intWeaveFloatX) {
        this.intWeaveFloatX=intWeaveFloatX;		
    }
    
    public int getIntWeaveFloatY() {
        return intWeaveFloatY;
    }
    public void setIntWeaveFloatY(int intWeaveFloatY) {
        this.intWeaveFloatY=intWeaveFloatY;		
    }
    
    public int getIntWeaveShaft() {
        return intWeaveShaft;
    }
    public void setIntWeaveShaft(int intWeaveShaft) {
        this.intWeaveShaft=intWeaveShaft;		
    }
    
    public int getIntWeaveTreadles() {
        return intWeaveTreadles;
    }
    public void setIntWeaveTreadles(int intWeaveTreadles) {
        this.intWeaveTreadles=intWeaveTreadles;		
    }
    /*
    public void setStrThreadPalettes(String[] strThreadPalettes) {
        this.strThreadPalettes = strThreadPalettes;
    }
    public String[] getStrThreadPalettes() {
        return strThreadPalettes;
    }
    */
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
    
    
    
}
