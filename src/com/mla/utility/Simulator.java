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
import java.awt.image.BufferedImage;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 * Fabric Base Simulation Class
 * <p>
 * This class is used for defining accessor and mutator methods for Fabric properties.
 *
 * @author Amit Kumar Singh
 * @version %I%, %G%
 * @since   1.0
 * @date 07/01/2016
 * @Designing accessor and mutator methods for Fabric
 */
public class Simulator {
    
    private Configuration objConfiguration;
    
    private String strSearchBy;
    private String strCondition;
    private String strOrderBy;
    private String strDirection;
    
    private String strBaseFSType;
    private BufferedImage yarnBI;
    private BufferedImage bufferedImage;
    
    private SimpleStringProperty strBaseFSID;
    private SimpleStringProperty strBaseFSName;
    private SimpleStringProperty strFabricType;
    private SimpleStringProperty strYarnID;
    private SimpleIntegerProperty intPPI;
    private SimpleIntegerProperty intEPI;
    private SimpleIntegerProperty intDPI;
    private SimpleObjectProperty<byte[]> bytBaseFSIcon;
    private SimpleStringProperty strBaseFSDate;
    private SimpleStringProperty strBaseFSAccess;
    private SimpleStringProperty strUserId;
    
    public Simulator(){
        /*this.strBaseFSID=null;
        this.strBaseFSName=null;
        this.strFabricType=null;
        this.strYarnID=null;
        this.intPPI=null;
        this.intEPI=null;
        this.intDPI=null;
        this.bytBaseFSIcon=null;
        this.strBaseFSDate=null;
        this.strBaseFSAccess=null;
        this.strUserId=null;*/
    }
    
    public Simulator(String id, String name, String fabType, String yarn, int ppi, int epi, int dpi, byte[] icon, String date, String userid, String access){
        this.strBaseFSID=new SimpleStringProperty(id);
        this.strBaseFSName=new SimpleStringProperty(name);
        this.strFabricType=new SimpleStringProperty(fabType);
        this.strYarnID=new SimpleStringProperty(yarn);
        this.intPPI=new SimpleIntegerProperty(ppi);
        this.intEPI=new SimpleIntegerProperty(epi);
        this.intDPI=new SimpleIntegerProperty(dpi);
        this.bytBaseFSIcon=new SimpleObjectProperty<byte[]>(icon);
        this.strBaseFSDate=new SimpleStringProperty(date);
        this.strBaseFSAccess=new SimpleStringProperty(access);
        this.strUserId=new SimpleStringProperty(userid);
    }

    public void setStrBaseFSID(String id) {
        strBaseFSID.set(id);
    }
    public String getStrBaseFSID() {
        return strBaseFSID.get();
    }
    
    public void setStrBaseFSName(String name) {
        strBaseFSName.set(name);
    }
    public String getStrBaseFSName() {
        return strBaseFSName.get();
    }
    
    public void setStrBaseFSType(String strFabricType) {
        this.strBaseFSType = strBaseFSType;
    }
    public String getStrBaseFSType() {
        return strBaseFSType;
    }

    public BufferedImage getYarnBI() {
        return yarnBI;
    }
    public void setYarnBI(BufferedImage yarnBI) {
        this.yarnBI=yarnBI;		
    }
    
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage=bufferedImage;		
    }
    
    public void setStrFabricType(String type) {
        strFabricType.set(type);
    }
    public String getStrFabricType() {
        return strFabricType.get();
    }
    
    public void setIntPPI(int PPI) {
        intPPI.set(PPI);
    }
    public int getIntPPI() {
        return intPPI.get();
    }
    
    public void setIntEPI(int EPI) {
        intEPI.set(EPI);
    }
    public int getIntEPI() {
        return intEPI.get();
    }
    
    public void setIntDPI(int DPI) {
        intDPI.set(DPI);
    }
    public int getIntDPI() {
        return intDPI.get();
    }

    public void setStrYarnID(String yarnID) {
        strYarnID.set(yarnID);
    }
    public String getStrYarnID() {
        return strYarnID.get();
    }
    
    public byte[] getBytBaseFSIcon() {
        return bytBaseFSIcon.get();
    }
    public void setBytBaseFSIcon(byte[] icon) {
        bytBaseFSIcon.set(icon);
    }
    
    public void setStrBaseFSDate(String date) {
        strBaseFSDate.set(date);
    }
    public String getStrBaseFSDate() {
        return strBaseFSDate.get();
    }
    
    public void setStrBaseFSAccess(String access) {
        strBaseFSAccess.set(access);
    }
    public String getStrBaseFSAccess() {
        return strBaseFSAccess.get();
    }
    
    public void setStrUserId(String userid) {
        strBaseFSAccess.set(userid);
    }
    public String getStrUserId() {
        return strUserId.get();
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
