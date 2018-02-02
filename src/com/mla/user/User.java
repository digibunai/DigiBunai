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

import java.util.Hashtable;

/**
 *
 * @Designing accessor and mutator function for user
 * @author Amit Kumar Singh
 * 
 */
public class User {

    private String strSeesionID;
    private String strUserID;
    private String strUsername;
    private String strPassword;
    private String strSysPassword;
    private String strAppPassword;
    private String strName;
    private String strEmail;
    private String strContactNumber;
    private String strAddress;
    private String strCityAddress;
    private String strStateAddress;
    private String strCountryAddress;
    private String strPincodeAddress;
    private String strGeoCity;
    private double dblGeoLat;
    private double dblGeoLng;
    private String strOrganization;
    private String strDOB;
    private String strGender;
    private String strEducation;
    private String strOccupation;
    private String strError;
    private Hashtable userAccess;
    
    public void setStrSeesionID(String strSeesionID) {
        this.strSeesionID = strSeesionID;
    }
    public String getStrSeesionID() {
        return strSeesionID;
    }
    
    public void setStrUserID(String strUserID) {
        this.strUserID = strUserID;
    }
    public String getStrUserID() {
        return strUserID;
    }
    
    public void setStrUsername(String strUsername) {
        this.strUsername = strUsername;
    }
    public String getStrUsername() {
        return strUsername;
    }
    
    public void setStrPassword(String strPassword) {
        this.strPassword = strPassword;
    }    
    public String getStrPassword() {
        return strPassword;
    }
    
    public void setStrSysPassword(String strSysPassword) {
        this.strSysPassword = strSysPassword;
    }    
    public String getStrSysPassword() {
        return strSysPassword;
    }
    
    public void setStrAppPassword(String strAppPassword) {
        this.strAppPassword = strAppPassword;
    }    
    public String getStrAppPassword() {
        return strAppPassword;
    }
    
    public void setStrName(String strName) {
        this.strName = strName;
    }
    public String getStrName() {
        return strName;
    }
    
    public void setStrEmailID(String strEmail) {
        this.strEmail = strEmail;
    }
    public String getStrEmail() {
        return strEmail;
    }
    
    public void setStrContactNumber(String strContactNumber) {
        this.strContactNumber = strContactNumber;
    }
    public String getStrContactNumber() {
        return strContactNumber;
    }
    
    public void setStrAddress(String strAddress) {
        this.strAddress = strAddress;
    }
    public String getStrAddress() {
        return strAddress;
    }
 
    public void setStrCityAddress(String strCityAddress) {
        this.strCityAddress = strCityAddress;
    }
    public String getStrCityAddress() {
        return strCityAddress;
    }
    
    public void setStrStateAddress(String strStateAddress) {
        this.strStateAddress = strStateAddress;
    }
    public String getStrStateAddress() {
        return strStateAddress;
    }
    
    public void setStrCountryAddress(String strCountryAddress) {
        this.strCountryAddress = strCountryAddress;
    }
    public String getStrCountryAddress() {
        return strCountryAddress;
    }
    
    public void setStrPincodeAddress(String strPincodeAddress) {
        this.strPincodeAddress = strPincodeAddress;
    }
    public String getStrPincodeAddress() {
        return strPincodeAddress;
    }
    
    public void setStrGeoCity(String strGeoCity) {
        this.strGeoCity = strGeoCity;
    }
    public String getStrGeoCity() {
        return strGeoCity;
    }
    
    
    public void setDblGeoLat(double dblGeoLat) {
        this.dblGeoLat = dblGeoLat;
    }
    public double getDblGeoLat() {
        return dblGeoLat;
    }
    
    public void setDblGeoLng(double dblGeoLng) {
        this.dblGeoLng = dblGeoLng;
    }
    public double getDblGeoLng() {
        return dblGeoLng;
    }
    
    public void setStrOrganization(String strOrganization) {
        this.strOrganization = strOrganization;
    }
    public String getStrOrganization() {
        return strOrganization;
    }
    
    public void setStrGender(String strGender) {
        this.strGender = strGender;
    }
    public String getStrGender() {
        return strGender;
    }
    
    public void setStrDOB(String strDOB) {
        this.strDOB = strDOB;
    }    
    public String getStrDOB() {
        return strDOB;
    }
    
    public void setStrEducation(String strEducation) {
        this.strEducation = strEducation;
    }    
    public String getStrEducation() {
        return strEducation;
    }

    public void setStrOccupation(String strOccupation) {
        this.strOccupation = strOccupation;
    }    
    public String getStrOccupation() {
        return strOccupation;
    }
    
    public void setStrError(String strError) {
        this.strError = strError;
    }
    public String getStrError() {
        return strError;
    }   
    
    public Hashtable getUserAccess() {
        return userAccess;
    }
    
    public void setUserAccess(Hashtable userAccess) {
        this.userAccess = userAccess;
    }
    
    public String getUserAccess(String key)  {
        if(key!=null) {
            if(userAccess.containsKey(key)) {
                return (String)userAccess.get(key);
            } else return key;
        } else return key;
    }
    
    public void setUserAccess(String key, String value) {
        if (!userAccess.containsKey(key)) {
            if(!value.equals(null)) {
                userAccess.put(key, value);
            }
        }else{
            userAccess.remove(key);
            setUserAccess(key, value);
        }
    }
}