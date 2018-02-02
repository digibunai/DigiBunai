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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @Designing accessor and mutator function for pattern
 * @author Amit Kumar Singh
 * 
 */
public class Pattern {
    private final SimpleStringProperty strPatternID;
    private final SimpleStringProperty strPattern;
    private final SimpleIntegerProperty intPatternType;
    private final SimpleIntegerProperty intPatternRepeat;
    private final SimpleIntegerProperty intPatternUsed;
    private final SimpleStringProperty strPatternAccess;
    private final SimpleStringProperty strPatternUser;
    private final SimpleStringProperty strPatternDate;
    
    private Configuration objConfiguration;
    
    private String strSearchBy;
    private String strCondition;
    private String strOrderBy;
    private String strDirection;
    
    Pattern(String tpCode, String tpPattern, int tpType, int tpRepeat, int tpUsed, String tpAccess, String tpUser, String tpDate) {
        this.strPatternID = new SimpleStringProperty(tpCode);
        this.strPattern = new SimpleStringProperty(tpPattern);
        this.intPatternType = new SimpleIntegerProperty(tpType);
        this.intPatternRepeat = new SimpleIntegerProperty(tpRepeat);
        this.intPatternUsed = new SimpleIntegerProperty(tpUsed);
        this.strPatternAccess = new SimpleStringProperty(tpAccess);
        this.strPatternUser = new SimpleStringProperty(tpUser);
        this.strPatternDate = new SimpleStringProperty(tpDate);
    }

    public String getStrPatternID() {
        return strPatternID.get();
    }
    public void setStrPatternID(String tpCode) {
        strPatternID.set(tpCode);
    }
    
    public String getStrPattern() {
        return strPattern.get();
    }
    public void setStrPattern(String tpPattern) {
        strPattern.set(tpPattern);
    }
    
    public int getIntPatternType() {
        return intPatternType.get();
    }
    public void setIntPatternType(int tpType) {
        intPatternType.set(tpType);
    }
    
    public int getIntPatternRepeat() {
        return intPatternRepeat.get();
    }
    public void setIntPatternRepeat(int tpRepeat) {
        intPatternRepeat.set(tpRepeat);
    }
    
    public int getIntPatternUsed() {
        return intPatternUsed.get();
    }
    public void setIntPatternUsed(int tpUsed) {
        intPatternUsed.set(tpUsed);
    }
    
    public String getStrPatternAccess() {
        return strPatternAccess.get();
    }
    public void getStrPatternAccess(String tpAccess) {
        strPatternAccess.set(tpAccess);
    }
    
    public String getStrPatternUser() {
        return strPatternUser.get();
    }
    public void setStrPatternUser(String tpUser) {
        strPatternUser.set(tpUser);
    }
    
    public String getStrPatternDate() {
        return strPatternDate.get();
    }
    public void setStrPatternDate(String tpDate) {
        strPatternDate.set(tpDate);
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
}
