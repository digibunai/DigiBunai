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

package com.mla.cloth;

import com.mla.main.Configuration;
import java.util.HashMap;
import java.util.Map;
/**
 * Cloth Class
 * <p>
 * This class is used for defining accessor and mutator methods for cloth.
 *
 * @author Aatif Ahmad Khan
 * @added 27 Oct 2017
 */
public class Cloth {
    
    private Configuration objConfiguration;
    
    private String strSearchBy;
    private String strCondition;
    private String strOrderBy;
    private String strDirection;
    
    private String strClothId;
    private String strClothName;
    private String strClothDescription;
    private byte[] bytClothIcon;
    private String strClothRegion;
    private String strClothAccess;
    private String strClothDate;
    
    // Body -> ceBody, Skart -> ceSkart, Blouse -> ceBlouse
    public Map<String,String> mapComponentFabric=new HashMap<String,String>(){
        {
            put("Body", null);
            put("Left Border", null);
            put("Right Border", null);
            put("Left Cross Border", null);
            put("Right Cross Border", null);
            put("Rightside Left Cross Border", null);
            put("Rightside Right Cross Border", null);
            put("Left Pallu", null);
            put("Right Pallu", null);
            put("Blouse", null);
            put("Skart", null);
        }
    };

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
    
    public String getStrClothId() {
        return strClothId;
    }
    public void setStrClothId(String strClothId) {
        this.strClothId=strClothId;		
    }
    
    public String getStrClothName() {
        return strClothName;
    }
    public void setStrClothName(String strClothName) {
        this.strClothName=strClothName;		
    }
    
    public String getStrClothDescription() {
        return strClothDescription;
    }
    public void setStrClothDescription(String strClothDescription) {
        this.strClothDescription=strClothDescription;		
    }
    
    public byte[] getBytClothIcon() {
        return bytClothIcon;
    }
    public void setBytClothIcon(byte[] bytClothIcon) {
        this.bytClothIcon=bytClothIcon;		
    }
    
    public String getStrClothRegion() {
        return strClothRegion;
    }
    public void setStrClothRegion(String strClothRegion) {
        this.strClothRegion=strClothRegion;		
    }
    
    public String getStrClothAccess() {
        return strClothAccess;
    }
    public void setStrClothAccess(String strClothAccess) {
        this.strClothAccess=strClothAccess;		
    }
    
    public String getStrClothDate() {
        return strClothDate;
    }
    public void setStrClothDate(String strClothDate) {
        this.strClothDate=strClothDate;		
    }
}