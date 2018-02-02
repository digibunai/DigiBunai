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

import com.mla.colour.Colour;
import com.mla.main.Configuration;
import java.util.ArrayList;

/**
 *
 * @author HP
 */
public class Palette {
    
    private Configuration objConfiguration;
    
    private String strSearchBy;
    private String strCondition;
    private String strOrderBy;
    private String strDirection;
    
    private String strPaletteID;
    private String strPaletteName;
    private String strPaletteType;
    private String strPaletteDate;
    private String strPaletteAccess;
    private String[] strThreadPalette;

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
    public String getStrPaletteType() {
        return strPaletteType;
    }
    public void setStrPaletteType(String strPaletteType) {
        this.strPaletteType=strPaletteType;		
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
    public void setStrThreadPalette(String[]strThreadPalette) {
        this.strThreadPalette = strThreadPalette;
    }
    public String[] getStrThreadPalette() {
        return strThreadPalette;
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
