/*
 * Copyright (C) 2016 Media Lab Asia
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
package com.mla.colour;

import com.mla.main.Configuration;
import java.util.ArrayList;
/**
 *
 * @Designing accessor and mutator function for color
 * @author Amit Kumar Singh
 * 
 */
public class Colour {
    
    private Configuration objConfiguration;
    
    private String strSearchBy;
    private String strCondition;
    private String strOrderBy;
    private String strDirection;
    
    ArrayList<Colour> lstColour = null;
    
    private String strPaletteID;
    private String strPaletteName;
    private String strPaletteDate;
    private String strPaletteAccess;
    private String[] strColourPalette;
    
    private String strColorID;
    private String strColorName;
    private String strColorType;
    private String strColorHex;
    private String strColorCode;
    private int intR;
    private int intG;
    private int intB;
    private String strColorAccess;
    private String strColorDate;
	private String strLimit;
    
    public void setStrColorID(String strColorID) {
        this.strColorID = strColorID;
    }
    public String getStrColorID() {
        return strColorID;
    }
    public void setStrColorName(String strColorName) {
        this.strColorName = strColorName;
    }
    public String getStrColorName() {
        return strColorName;
    }
    public void setStrColorType(String strColorType) {
        this.strColorType = strColorType;
    }
    public String getStrColorType() {
        return strColorType;
    }
    public void setStrColorHex(String strColorHex) {
        this.strColorHex = strColorHex;
    }
    public String getStrColorHex() {
        return strColorHex;
    }
    public void setStrColorCode(String strColorCode) {
        this.strColorCode = strColorCode;
    }
    public String getStrColorCode() {
        return strColorCode;
    }
    public void setStrColorDate(String strColorDate) {
        this.strColorDate = strColorDate;
    }
    public String getStrColorDate() {
        return strColorDate;
    }
	public void setStrLimit(String strLimit){
        this.strLimit=strLimit;
    }
    public String getStrLimit(){
        return strLimit;
    }
    public void setIntR(int intR) {
        this.intR = intR;
    }
    public int getIntR() {
        return intR;
    }
    public void setIntG(int intG) {
        this.intG = intG;
    }
    public int getIntG() {
        return intG;
    }
    public void setIntB(int intB) {
        this.intB = intB;
    }
    public int getIntB() {
        return intB;
    }
    public String getStrColorAccess() {
        return strColorAccess;
    }
    public void setStrColorAccess(String strColorAccess) {
        this.strColorAccess=strColorAccess;		
    }
    
    public String getStrPaletteID() {
        return strPaletteID;
    }
    public void setStrPaletteID(String strPaletteID) {
        this.strPaletteID=strPaletteID;		
    }
    public String getStrPaletteName() {
        return strPaletteName;
    }
    public void setStrPaletteName(String strPaletteName) {
        this.strPaletteName=strPaletteName;		
    }
    public String getStrPaletteDate() {
        return strPaletteDate;
    }
    public void setStrPaletteDate(String strPaletteDate) {
        this.strPaletteDate=strPaletteDate;		
    }
    public String getStrPaletteAccess() {
        return strPaletteAccess;
    }
    public void setStrPaletteAccess(String strPaletteAccess) {
        this.strPaletteAccess=strPaletteAccess;		
    }
    public void setStrColourPalette(String[]strColourPalette) {
        this.strColourPalette = strColourPalette;
    }
    public String[] getStrColourPalette() {
        return strColourPalette;
    }
    
    public void setObjConfiguration(Configuration objConfiguration) {
        this.objConfiguration = objConfiguration;
    }
    public Configuration getObjConfiguration() {
        return objConfiguration;
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
