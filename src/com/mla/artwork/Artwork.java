/*
 * Copyright (C) 2017 Digital India Corporation (formerly Media Lab Asia)
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
package com.mla.artwork;

import com.mla.main.Configuration;
/**
 * Artwork Class
 * <p>
 * This class is used for defining accessor and mutator methods for artwork properties.
 *
 * @author Amit Kumar Singh
 * @version %I%, %G%
 * @since   1.0
 * @date 07/01/2016
 * @Designing accessor and mutator methods for artwork
 */
public class Artwork {
    private Configuration objConfiguration;
    
    private String strSearchBy;
    private String strCondition;
    private String strOrderBy;
    private String strDirection;
    
    private String strArtworkId;
    private String strArtworkName;
    private String strArtworkBackground;
    private String strArtworkAccess;
    private String strArtworkDate;
    private byte[] bytArtworkThumbnil;

    /**
     * setObjConfiguration
     * <p>
     * mutator(setter) methods for setting data,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @param       Configuration objConfiguration
     */    
    public void setObjConfiguration(Configuration objConfiguration) {
        this.objConfiguration = objConfiguration;
    }
    /**
     * getObjConfiguration
     * <p>
     * Accessor(getter) methods for getting data value,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @return      Configuration
     */    
    public Configuration getObjConfiguration() {
        return objConfiguration;
    }
    /**
     * setStrArtworkId
     * <p>
     * mutator(setter) methods for setting data,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @param       String strArtworkId
     */    
    public void setStrArtworkId(String strArtworkId) {
        this.strArtworkId=strArtworkId;		
    }
    /**
     * getStrArtworkId
     * <p>
     * Accessor(getter) methods for getting data value,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @return      String
     */    
    public String getStrArtworkId() {
        return strArtworkId;
    }
    /**
     * setStrArtworkName
     * <p>
     * mutator(setter) methods for setting data,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @param       String strArtworkName
     */    
    public void setStrArtworkName(String strArtworkName) {
        this.strArtworkName=strArtworkName;		
    }
    /**
     * getStrArtworkName
     * <p>
     * Accessor(getter) methods for getting data value,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @return      String
     */    
    public String getStrArtworkName() {
        return strArtworkName;
    }
    /**
     * setStrArtworkBackground
     * <p>
     * mutator(setter) methods for setting data,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @param       String strArtworkBackground
     */    
    public void setStrArtworkBackground(String strArtworkBackground) {
        this.strArtworkBackground=strArtworkBackground;		
    }    
    /**
     * getStrArtworkBackground
     * <p>
     * Accessor(getter) methods for getting data value,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @return      String
     */    
    public String getStrArtworkBackground() {
        return strArtworkBackground;
    }
    /**
     * setStrArtworkAccess
     * <p>
     * mutator(setter) methods for setting data,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @param       String strArtworkAccess
     */    
    public void setStrArtworkAccess(String strArtworkAccess) {
        this.strArtworkAccess=strArtworkAccess;		
    }
    /**
     * getStrArtworkAccess
     * <p>
     * Accessor(getter) methods for getting data value,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @return      String
     */    
    public String getStrArtworkAccess() {
        return strArtworkAccess;
    }
    /**
     * setStrArtworkDate
     * <p>
     * mutator(setter) methods for setting data,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @param       String strArtworkDate
     */    
    public void setStrArtworkDate(String strArtworkDate) {
        this.strArtworkDate=strArtworkDate;		
    }
    /**
     * getStrArtworkDate
     * <p>
     * Accessor(getter) methods for getting data value,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @return      String
     */    
    public String getStrArtworkDate() {
        return strArtworkDate;
    }
    /**
     * setBytArtworkThumbnil
     * <p>
     * mutator(setter) methods for setting data,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @param       byte[] setBytArtworkThumbnil
     */    
    public void setBytArtworkThumbnil(byte[] bytArtworkThumbnil) {
        this.bytArtworkThumbnil=bytArtworkThumbnil;		
    }
    /**
     * getBytArtworkThumbnil
     * <p>
     * Accessor(getter) methods for getting data value,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @return      byte[]
     */    
    public byte[] getBytArtworkThumbnil() {
        return bytArtworkThumbnil;
    }
    /**
     * setStrSearchBy
     * <p>
     * mutator(setter) methods for setting data,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @param       String strSearchBy
     */    
    public void setStrSearchBy(String strSearchBy) {
        this.strSearchBy=strSearchBy;		
    }
    /**
     * getStrSearchBy
     * <p>
     * Accessor(getter) methods for getting data value,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @return      String
     */    
    public String getStrSearchBy() {
        return strSearchBy;
    }
    /**
     * setStrCondition
     * <p>
     * mutator(setter) methods for setting data,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @param       String strCondition
     */    
    public void setStrCondition(String strCondition) {
        this.strCondition=strCondition;		
    }
    /**
     * getStrCondition
     * <p>
     * Accessor(getter) methods for getting data value,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @return      String
     */    
    public String getStrCondition() {
        return strCondition;
    }
    /**
     * setStrOrderBy
     * <p>
     * mutator(setter) methods for setting data,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @param       String strOrderBy
     */    
    public void setStrOrderBy(String strOrderBy) {
        this.strOrderBy=strOrderBy;		
    }
    /**
     * getStrOrderBy
     * <p>
     * Accessor(getter) methods for getting data value,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @return      String
     */    
    public String getStrOrderBy() {
        return strOrderBy;
    }
    /**
     * setStrDirection
     * <p>
     * mutator(setter) methods for setting data,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @param       String strDirection
     */    
    public void setStrDirection(String strDirection) {
        this.strDirection=strDirection;		
    }
    /**
     * getStrDirection
     * <p>
     * Accessor(getter) methods for getting data value,
     * 
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @return      String
     */    
    public String getStrDirection() {
        return strDirection;
    }    
}