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
package com.mla.utility;

import com.mla.main.Configuration;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @Designing accessor and mutator function for device
 * @author Amit Kumar Singh
 * 
 */
public class Device {

    private Configuration objConfiguration;
    
    private String strSearchBy;
    private String strCondition;
    private String strOrderBy;
    private String strDirection;
    
    private final SimpleStringProperty strDeviceId;
    private final SimpleStringProperty strDeviceType;
    private final SimpleStringProperty strDeviceName;
    private final SimpleStringProperty strDevicePath;   
    
    private String strDeviceAccess;
    private String strDeviceDate;
   
    public Device(String dId, String dType, String dName, String dPath) {
        this.strDeviceId = new SimpleStringProperty(dId);
        this.strDeviceType = new SimpleStringProperty(dType);
        this.strDeviceName = new SimpleStringProperty(dName);
        this.strDevicePath = new SimpleStringProperty(dPath);
    }

    public String getStrDeviceAccess() {
        return this.strDeviceAccess;
    }
    public void setStrDeviceAccess(String strDeviceAccess) {
        this.strDeviceAccess = strDeviceAccess;
    }
    
    public String getStrDeviceDate() {
        return this.strDeviceDate;
    }
    public void setStrDeviceDate(String strDeviceDate) {
        this.strDeviceDate = strDeviceDate;
    }
    
    public String getStrDeviceId() {
        return strDeviceId.get();
    }
    public void setStrDeviceId(String dId) {
        strDeviceId.set(dId);
    }
    
    public String getStrDeviceType() {
        return strDeviceType.get();
    }
    public void setStrDeviceType(String dType) {
        strDeviceType.set(dType);
    }
    
    public String getStrDeviceName() {
        return strDeviceName.get();
    }
    public void setStrDeviceName(String dName) {
        strDeviceName.set(dName);
    }
    
    public String getStrDevicePath() {
        return strDevicePath.get();
    }
    public void setStrDevicePath(String dPath) {
        strDevicePath.set(dPath);
    }
    
    public void setObjConfiguration(Configuration objConfiguration) {
        this.objConfiguration = objConfiguration;
    }
    public Configuration getObjConfiguration() {
        return objConfiguration;
    }
    
    public String getStrSearchBy() {
        return strSearchBy;
    }
    public void setStrSearchBy(String strSearchBy) {
        this.strSearchBy=strSearchBy;		
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
    
    public String getStrDirection() {
        return strDirection;
    }
    public void setStrDirection(String strDirection) {
        this.strDirection=strDirection;		
    }
}
