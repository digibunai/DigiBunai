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

import com.mla.main.DbConnect;
import com.mla.main.IDGenerator;
import com.mla.main.Logging;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @Designing model function for utility
 * @author Amit Kumar Singh
 * 
 */
public class UtilityAction {
 
    Connection connection = null; //DbConnect.getConnection();
    
    public UtilityAction() throws SQLException{
        connection = DbConnect.getConnection();
    }
    
    public void close() throws SQLException{
        connection.close();
    }
    
    /**************************** Fabric Simulation Base ******************************************/
    public boolean setBaseFabricSimultion(Simulator objSimulator) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        new Logging("INFO",UtilityAction.class.getName(),"<<<<<<<<<<< setBaseFabricSimultion() >>>>>>>>>>>",null);
        try {           
            strQuery = "INSERT INTO `mla_fabric_base_library` (`ID`, `NAME`, `FABRICTYPE`, `EPI`, `PPI`, `DPI`, `YARNID`, `ICON`, `ACCESS`, `USERID`) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, objSimulator.getStrBaseFSID());
            oPreparedStatement.setString(2, objSimulator.getStrBaseFSName());
            oPreparedStatement.setString(3, objSimulator.getStrFabricType());
            oPreparedStatement.setInt(4, objSimulator.getIntEPI());
            oPreparedStatement.setInt(5, objSimulator.getIntPPI());
            oPreparedStatement.setInt(6, objSimulator.getIntDPI());
            oPreparedStatement.setString(7, objSimulator.getStrYarnID());
            oPreparedStatement.setBinaryStream(8,new ByteArrayInputStream(objSimulator.getBytBaseFSIcon()),objSimulator.getBytBaseFSIcon().length);
            oPreparedStatement.setString(9, new IDGenerator().setUserAcessKey("FABRIC_BASE_LIBRARY",objSimulator.getObjConfiguration().getObjUser()));
            oPreparedStatement.setString(10, objSimulator.getObjConfiguration().getObjUser().getStrUserID());
            oResult = (byte)oPreparedStatement.executeUpdate();
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityAction.class.getName(),"setBaseFabricSimultion : "+strQuery,ex);
        } finally {
            try {
                if(oResultSet!=null) {
                    oResultSet.close();
                    oResultSet=null;
                }
                if(oPreparedStatement!=null) {
                    oPreparedStatement.close();
                    oPreparedStatement=null;
                }
                if(connection!=null) {
                    connection.close();
                    connection=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",UtilityAction.class.getName(),"setBaseFabricSimultion() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< setBaseFabricSimultion >>>>>>");
        if(oResult==0)
            return false;
        else
            return true;
    }
    
    public boolean resetBaseFabricSimultion(Simulator objSimulator) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        new Logging("INFO",UtilityAction.class.getName(),"<<<<<<<<<<< resetBaseFabricSimultion() >>>>>>>>>>>",null);
        try {           
            strQuery = "UPDATE `mla_fabric_base_library` SET `NAME`=?, `FABRICTYPE`=?, `EPI`=?, `PPI`=?, `DPI`=?, `YARNID`=?, `ICON`=?, `ACCESS`=? WHERE `ID`=?;";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, objSimulator.getStrBaseFSName());
            oPreparedStatement.setString(2, objSimulator.getStrFabricType());
            oPreparedStatement.setInt(3, objSimulator.getIntEPI());
            oPreparedStatement.setInt(4, objSimulator.getIntPPI());
            oPreparedStatement.setInt(5, objSimulator.getIntDPI());
            oPreparedStatement.setString(6, objSimulator.getStrYarnID());
            oPreparedStatement.setBinaryStream(7,new ByteArrayInputStream(objSimulator.getBytBaseFSIcon()),objSimulator.getBytBaseFSIcon().length);
            //oPreparedStatement.setString(8, new IDGenerator().setUserAcessKey("FABRIC_LIBRARY",objSimulator.getObjConfiguration().getObjUser()));
            oPreparedStatement.setString(8, objSimulator.getStrBaseFSAccess());
            oPreparedStatement.setString(9, objSimulator.getStrBaseFSID());
            oResult = (byte)oPreparedStatement.executeUpdate();
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityAction.class.getName(),"resetBaseFabricSimultion : "+strQuery,ex);
        } finally {
            try {
                if(oResultSet!=null) {
                    oResultSet.close();
                    oResultSet=null;
                }
                if(oPreparedStatement!=null) {
                    oPreparedStatement.close();
                    oPreparedStatement=null;
                }
                if(connection!=null) {
                    connection.close();
                    connection=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",UtilityAction.class.getName(),"resetBaseFabricSimultion() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< resetBaseFabricSimultion >>>>>>");
        if(oResult==0)
            return false;
        else
            return true;
    }
    public void getBaseFabricSimultion(Simulator objSimulator) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        try {           
            System.out.println("<<<<<< getBaseFabricSimultion >>>>>>");
            strQuery = "select * from mla_fabric_base_library WHERE ID='"+objSimulator.getStrBaseFSID()+"';";
            System.out.println(strQuery);
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);  
            oResultSet.next();
            objSimulator.setStrBaseFSID(oResultSet.getString("ID"));
            objSimulator.setStrBaseFSName(oResultSet.getString("NAME"));            
            objSimulator.setStrFabricType(oResultSet.getString("FABRICTYPE"));
            objSimulator.setIntEPI(oResultSet.getInt("EPI"));
            objSimulator.setIntPPI(oResultSet.getInt("PPI"));
            objSimulator.setIntDPI(oResultSet.getInt("DPI"));
            objSimulator.setStrYarnID(oResultSet.getString("YARNID"));
            objSimulator.setBytBaseFSIcon(oResultSet.getBytes("ICON"));
            objSimulator.setStrBaseFSDate(oResultSet.getString("UDATE"));
            objSimulator.setStrBaseFSAccess(oResultSet.getString("ACCESS"));
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityAction.class.getName(),"getBaseFabricSimultion : "+strQuery,ex);
        } finally {
            try {
                if(oResultSet!=null) {
                    oResultSet.close();
                    oResultSet=null;
                }
                if(oStatement!=null) {
                    oStatement.close();
                    oStatement=null;
                }
                if(connection!=null) {
                    connection.close();
                    connection=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",UtilityAction.class.getName(),"getBaseFabricSimultion() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< getBaseFabricSimultion >>>>>>");
        return;
    }
    public boolean clearBaseFabricSimultion(String strBaseFSID) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        boolean oResult= false;
        String strQuery=null;
        String yarnId = null;
        try {           
            System.out.println("<<<<<< clearBaseFabricSimultion >>>>>>");
            strQuery = "DELETE FROM `mla_fabric_base_library` WHERE `ID` = ?;";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, strBaseFSID);
            oResult = oPreparedStatement.execute();
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityAction.class.getName(),"clearBaseFabricSimultion : "+strQuery,ex);
        } finally {
            try {
                if(oResultSet!=null) {
                    oResultSet.close();
                    oResultSet=null;
                }
                if(oPreparedStatement!=null) {
                    oPreparedStatement.close();
                    oPreparedStatement=null;
                }
                if(connection!=null) {
                    connection.close();
                    connection=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",UtilityAction.class.getName(),"clearBaseFabricSimultion() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< clearBaseFabricSimultion >>>>>>");
        return oResult;
    }
    public Simulator[] lstBaseFabricSimultion(Simulator objSimulator) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        Simulator[] lstBaseFSDeatails=null;
        new Logging("INFO",UtilityAction.class.getName(),"<<<<<<<<<<< lstBaseFabricSimultion() >>>>>>>>>>>",null);
        try {
            String cond = "(USERID = '"+objSimulator.getObjConfiguration().getObjUser().getStrUserID()+"' OR `ACCESS`='"+new IDGenerator().getUserAcess("FABRIC_BASE_LIBRARY")+"') ";
            String orderBy ="NAME ";
            if(!(objSimulator.getStrBaseFSName().trim().equals(""))) {
                cond += " AND `NAME` LIKE '"+objSimulator.getStrBaseFSName().trim()+"%'";
            }
            if(objSimulator.getIntEPI()>0) {
                cond += " AND `EPI` LIKE '"+objSimulator.getIntEPI()+"%'";
            }
            if(objSimulator.getIntPPI()>0) {
                cond += " AND `PPI` LIKE '"+objSimulator.getIntPPI()+"%'";
            }
            if(!(objSimulator.getStrFabricType().trim().equals("")) && !(objSimulator.getStrFabricType().trim().equalsIgnoreCase("All"))) {
                cond += " AND `FABRICTYPE` LIKE '%"+objSimulator.getStrSearchBy().trim()+"%'";
            }
            if(objSimulator.getStrOrderBy().equals("Name")) {
                orderBy = "`NAME`";
            } else if(objSimulator.getStrOrderBy().equals("Date")) {
                orderBy = "`UDATE`";
            }
            if(objSimulator.getStrDirection().equals("Ascending")) {
                orderBy += " ASC";
            } else if(objSimulator.getStrDirection().equals("Descending")) {
                orderBy += " DESC";
            }
            strQuery = "select * from mla_fabric_base_library WHERE "+cond+" ORDER BY "+orderBy;
            //System.out.println(strQuery);
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            oResultSet.last();
            System.out.println("Row= "+oResultSet.getRow());
            lstBaseFSDeatails = new Simulator[oResultSet.getRow()];
            oResultSet.beforeFirst();
            int count=0;
            while(oResultSet.next()) {
                objSimulator = new Simulator(
                oResultSet.getString("ID").toString(),
                oResultSet.getString("NAME"),
                oResultSet.getString("FABRICTYPE"),
                oResultSet.getString("YARNID"),
                oResultSet.getInt("PPI"),
                oResultSet.getInt("EPI"),
                oResultSet.getInt("DPI"),
                oResultSet.getBytes("ICON"),
                oResultSet.getTimestamp("UDATE").toString(),
                oResultSet.getString("USERID").toString(),
                oResultSet.getString("ACCESS"));
                lstBaseFSDeatails[count++]=objSimulator;
              //  System.out.println("Fabric Data Model : "+oResultSet.getString("ID").toString());
            }
        } catch (Exception ex) {
            //Logger.getLogger(SimulatorImportView.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",UtilityAction.class.getName(),"lstBaseFabricSimultion : "+strQuery,ex);
        } finally {
            try {
                if(oResultSet!=null) {
                    oResultSet.close();
                    oResultSet=null;
                }
                if(oStatement!=null) {
                    oStatement.close();
                    oStatement=null;
                }
                if(connection!=null) {
                    connection.close();
                    connection=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",UtilityAction.class.getName(),"lstBaseFabricSimultion() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",UtilityAction.class.getName(),">>>>>>>>>>> lstBaseFabricSimultion() <<<<<<<<<<<"+strQuery,null);
        return lstBaseFSDeatails;
    }
    
    public Simulator[] retBaseFabricSimultion(Simulator objSimulator) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String id=null, name=null, fabType=null, yarn=null, access=null, date=null, userid=null;
        int pp=0, ep=0, dp=0;
        byte[] icon=null;
        Simulator[] fbs=null;
        new Logging("INFO",UtilityAction.class.getName(),"<<<<<<<<<<< lstBaseFabricSimultion() >>>>>>>>>>>",null);
        try {
            /*String cond = "(USERID = '"+objSimulator.getObjConfiguration().getObjUser().getStrUserID()+"' OR `ACCESS`='"+new IDGenerator().getUserAcess("BASE_SIMULATION_LIBRARY")+"') ";
            String orderBy ="NAME ";
            if(!(objSimulator.getStrCondition().trim().equals(""))) {
                cond += " AND `NAME` LIKE '"+objSimulator.getStrCondition().trim()+"%'";
            }
            if(!(objSimulator.getStrSearchBy().trim().equals("")) && !(objSimulator.getStrSearchBy().trim().equalsIgnoreCase("All"))) {
                cond += " AND `FABRICTYPE` LIKE '%"+objSimulator.getStrSearchBy().trim()+"%'";
            }
            if(objSimulator.getStrOrderBy().equals("Name")) {
                orderBy = "`NAME`";
            } else if(objSimulator.getStrOrderBy().equals("Date")) {
                orderBy = "`UDATE`";
            }
            if(objSimulator.getStrDirection().equals("Ascending")) {
                orderBy += " ASC";
            } else if(objSimulator.getStrDirection().equals("Descending")) {
                orderBy += " DESC";
            }
            strQuery = "select * from mla_fabric_base_library WHERE "+cond+" ORDER BY "+orderBy;*/
            String cond = "(USERID = '"+objSimulator.getObjConfiguration().getObjUser().getStrUserID()+"' OR `ACCESS`='"+new IDGenerator().getUserAcess("FABRIC_BASE_LIBRARY")+"') ";
            String orderBy ="NAME ";
            if(!(objSimulator.getStrBaseFSName().trim().equals(""))) {
                cond += " AND `NAME` LIKE '"+objSimulator.getStrBaseFSName().trim()+"%'";
            }
            if(objSimulator.getIntEPI()>0) {
                cond += " AND `EPI` LIKE '"+objSimulator.getIntEPI()+"%'";
            }
            if(objSimulator.getIntPPI()>0) {
                cond += " AND `PPI` LIKE '"+objSimulator.getIntPPI()+"%'";
            }
            if(!(objSimulator.getStrFabricType().trim().equals("")) && !(objSimulator.getStrFabricType().trim().equalsIgnoreCase("All"))) {
                cond += " AND `FABRICTYPE` LIKE '%"+objSimulator.getStrSearchBy().trim()+"%'";
            }
            if(objSimulator.getStrOrderBy().equals("Name")) {
                orderBy = "`NAME`";
            } else if(objSimulator.getStrOrderBy().equals("Date")) {
                orderBy = "`UDATE`";
            }
            if(objSimulator.getStrDirection().equals("Ascending")) {
                orderBy += " ASC";
            } else if(objSimulator.getStrDirection().equals("Descending")) {
                orderBy += " DESC";
            }
            strQuery = "select * from mla_fabric_base_library WHERE "+cond+" ORDER BY "+orderBy;
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            oResultSet.last();
            System.out.println("Row= "+oResultSet.getRow());
            fbs = new Simulator[oResultSet.getRow()];
            oResultSet.beforeFirst();
            int i = 0;
            while(oResultSet.next()) {
                id=oResultSet.getString("ID").toString();
                name=oResultSet.getString("NAME");
                fabType=oResultSet.getString("FABRICTYPE");
                ep=oResultSet.getInt("EPI");
                pp=oResultSet.getInt("PPI");
                dp=oResultSet.getInt("DPI");
                yarn=oResultSet.getString("YARNID");
                icon=oResultSet.getBytes("ICON");
                access=oResultSet.getString("ACCESS");
                date=oResultSet.getTimestamp("UDATE").toString();
                userid=oResultSet.getString("USERID").toString();
                fbs[i]=new Simulator(id, name, fabType, yarn, pp, ep, dp, icon, date, userid, access);
                i++;
                System.out.println("Fabric Data Model : "+oResultSet.getString("ID").toString());
            }
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityAction.class.getName(),"lstBaseFabricSimultion : "+strQuery,ex);
        } finally {
            try {
                if(oResultSet!=null) {
                    oResultSet.close();
                    oResultSet=null;
                }
                if(oStatement!=null) {
                    oStatement.close();
                    oStatement=null;
                }
                if(connection!=null) {
                    connection.close();
                    connection=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",UtilityAction.class.getName(),"lstBaseFabricSimultion() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",UtilityAction.class.getName(),">>>>>>>>>>> lstBaseFabricSimultion() <<<<<<<<<<<"+strQuery,null);
        return fbs;
    }

    /**************************** Device ******************************************/
    
    
    public Device[] getDevices(Device objDevice) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String deviceId = null, deviceType = null, deviceName = null, devicePath = null;
        Device[] devices = null;
        try {           
            System.out.println("<<<<<< getDevices >>>>>>");
            String cond = "(USERID='"+objDevice.getObjConfiguration().getObjUser().getStrUserID()+"' OR  `ACCESS`='"+new IDGenerator().getUserAcess("DEVICE_LIBRARY")+"') ";
            if(objDevice.getStrDeviceType()!="" && objDevice.getStrDeviceType()!=null)
                cond += " AND TYPE='"+objDevice.getStrDeviceType()+"'";
            strQuery = "select * from mla_user_device WHERE "+cond+";";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            oResultSet.last();
            devices = new Device[oResultSet.getRow()];
            oResultSet.beforeFirst();
            int i = 0;
            while(oResultSet.next()) {
                deviceId=oResultSet.getString("ID").toString();
                deviceType=oResultSet.getString("TYPE").toString();
                deviceName=oResultSet.getString("NAME");
                devicePath=oResultSet.getString("PATH");
                devices[i] = new Device(deviceId, deviceType, deviceName, devicePath);
                i++;
                //System.out.println("Artwork Data Model : "+strArtworkID+"="+strArtworkName+"="+bytArtworkThumbnil+"="+strArtworkDate+"="+strArtworkUser);
            }
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityAction.class.getName(),"getDevices() : "+strQuery,ex);
        } finally {
            try {
                if(oResultSet!=null) {
                    oResultSet.close();
                    oResultSet=null;
                }
                if(oStatement!=null) {
                    oStatement.close();
                    oStatement=null;
                }
                if(connection!=null) {
                    connection.close();
                    connection=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",UtilityAction.class.getName(),"getDevices() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< getDevices >>>>>>");
        return devices;
    }
    
    public void getDevice(Device objDevice) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        try {           
            System.out.println("<<<<<< getDevice >>>>>>");
            String cond = "1";
            if(objDevice.getStrDeviceId()!="" && objDevice.getStrDeviceId()!=null)
                cond += " AND ID='"+objDevice.getStrDeviceId()+"'";
            if(objDevice.getStrDeviceType()!="" && objDevice.getStrDeviceType()!=null)
                cond += " AND TYPE='"+objDevice.getStrDeviceType()+"'";
            if(objDevice.getStrDeviceName()!="" && objDevice.getStrDeviceName()!=null)
                cond += " AND NAME='"+objDevice.getStrDeviceName()+"'";
            strQuery = "select * from mla_user_device WHERE "+cond+" LIMIT 1;";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            while(oResultSet.next()) {
                objDevice.setStrDeviceId(oResultSet.getString("ID"));
                objDevice.setStrDeviceName(oResultSet.getString("NAME"));
                objDevice.setStrDeviceType(oResultSet.getString("TYPE"));
                objDevice.setStrDevicePath(oResultSet.getString("PATH"));
                objDevice.setStrDeviceAccess(oResultSet.getString("ACCESS"));
            }
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityAction.class.getName(),"getDevice() : "+strQuery,ex);
        } finally {
            try {
                if(oResultSet!=null) {
                    oResultSet.close();
                    oResultSet=null;
                }
                if(oStatement!=null) {
                    oStatement.close();
                    oStatement=null;
                }
                if(connection!=null) {
                    connection.close();
                    connection=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",UtilityAction.class.getName(),"getDevice() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< getDevice >>>>>>");
    }
    
    public boolean setDevice(Device objDevice) {
        Statement oStatement =null;
        String strQuery=null;
        byte oResult = 0;
        try {          
            System.out.println("<<<<<< setDevice >>>>>>");
            strQuery = "INSERT INTO `mla_user_device` (`ID`, `TYPE`, `NAME`, `PATH`, `ACCESS`, `USERID`) VALUES ( '"+objDevice.getStrDeviceId()+"', '"+objDevice.getStrDeviceType()+"', '"+objDevice.getStrDeviceName()+"', '"+objDevice.getStrDevicePath()+"', '"+objDevice.getStrDeviceAccess()+"', '"+objDevice.getObjConfiguration().getObjUser().getStrUserID()+"');";
            oStatement = connection.createStatement();
            oResult = (byte)oStatement.executeUpdate(strQuery);
        } catch (Exception ex) {
            Logger.getLogger(UtilityAction.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",UtilityAction.class.getName(),"setDevice : "+strQuery,ex);
        } finally {
            try {                
                if(oStatement!=null) {
                    oStatement.close();
                    oStatement=null;
                }
                if(connection!=null) {
                    connection.close();
                    connection=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",UtilityAction.class.getName(),"setDevice() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< setDevice >>>>>>"+strQuery);
        if(oResult==0)
            return false;
        else
            return true;
    }
    
    public byte resetDevice(Device objDevice) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        try {           
            System.out.println("<<<<<< resetDevice >>>>>>");
            strQuery = "UPDATE `mla_user_device` SET `TYPE`= ?, `NAME`= ?, `PATH`= ? WHERE `USERID`= ? AND `ID`= ?;";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, objDevice.getStrDeviceType());
            oPreparedStatement.setString(2, objDevice.getStrDeviceName());
            oPreparedStatement.setString(3, objDevice.getStrDevicePath());
            oPreparedStatement.setString(4, objDevice.getObjConfiguration().getObjUser().getStrUserID());
            oPreparedStatement.setString(5, objDevice.getStrDeviceId());
            oResult = (byte)oPreparedStatement.executeUpdate();
            System.err.println("Result:"+oResult);
            
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityAction.class.getName(),"resetDevice : "+strQuery,ex);
        } finally {
            try {
                if(oResultSet!=null) {
                    oResultSet.close();
                    oResultSet=null;
                }
                if(oPreparedStatement!=null) {
                    oPreparedStatement.close();
                    oPreparedStatement=null;
                }
                if(connection!=null) {
                    connection.close();
                    connection=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",UtilityAction.class.getName(),"resetDevice() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< resetDevice >>>>>>");
        return oResult;
    }

    public byte clearDevice(Device objDevice) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        try {           
            System.out.println("<<<<<< clearDevice >>>>>>");
            strQuery = "DELETE FROM `mla_user_device` WHERE `USERID`= ? AND `ID`= ?;";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, objDevice.getObjConfiguration().getObjUser().getStrUserID());
            oPreparedStatement.setString(2, objDevice.getStrDeviceId());
            oResult = (byte)oPreparedStatement.executeUpdate();
            //System.err.println("Result:"+oResult);        
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityAction.class.getName(),"clearDevice() : "+strQuery,ex);
        } finally {
            try {
                if(oResultSet!=null) {
                    oResultSet.close();
                    oResultSet=null;
                }
                if(oPreparedStatement!=null) {
                    oPreparedStatement.close();
                    oPreparedStatement=null;
                }
                if(connection!=null) {
                    connection.close();
                    connection=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",UtilityAction.class.getName(),"clearDevice() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< clearDevice >>>>>>");
        return oResult;
    }
/**************************** Palette ******************************************/
    public String[][] getPaletteName(Palette objPalette){
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String[][] paletteName=null;
        int rowCount=0;
        try {           
            strQuery = "SELECT id, name FROM mla_palette_library WHERE type='"+objPalette.getStrPaletteType()+"' AND (userid='"+objPalette.getObjConfiguration().getObjUser().getStrUserID()+"' OR access='"+new IDGenerator().getUserAcess("PALETTE_LIBRARY")+"');";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            if(oResultSet.last())
                paletteName=new String[oResultSet.getRow()][2];
            oResultSet.beforeFirst();
            while(oResultSet.next()) {
                paletteName[rowCount][0]=oResultSet.getString("ID");
                paletteName[rowCount][1]=oResultSet.getString("NAME");
                rowCount++;
            }
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityAction.class.getName(),"getPaletteName() : "+strQuery,ex);
        } finally {
            try {
                if(oResultSet!=null) {
                    oResultSet.close();
                    oResultSet=null;
                }
                if(oStatement!=null) {
                    oStatement.close();
                    oStatement=null;
                }
                if(connection!=null) {
                    connection.close();
                    connection=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",UtilityAction.class.getName(),"getPaletteName() : Error while closing connection"+e,ex);
                }
            }
        }
        return paletteName;
    }
    
    public void getPalette(Palette objPalette) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String[] threadPaletes=null;
        try {           
            strQuery = "select * from mla_palette_library WHERE id='"+objPalette.getStrPaletteID()+"';";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);    
            threadPaletes = new String[52];            
            if(oResultSet.next()){
                threadPaletes[0]=oResultSet.getString("warp_A").toString();
                threadPaletes[1]=oResultSet.getString("warp_B").toString();
                threadPaletes[2]=oResultSet.getString("warp_C").toString();
                threadPaletes[3]=oResultSet.getString("warp_D").toString();
                threadPaletes[4]=oResultSet.getString("warp_E").toString();
                threadPaletes[5]=oResultSet.getString("warp_F").toString();
                threadPaletes[6]=oResultSet.getString("warp_G").toString();
                threadPaletes[7]=oResultSet.getString("warp_H").toString();
                threadPaletes[8]=oResultSet.getString("warp_I").toString();
                threadPaletes[9]=oResultSet.getString("warp_J").toString();
                threadPaletes[10]=oResultSet.getString("warp_K").toString();
                threadPaletes[11]=oResultSet.getString("warp_L").toString();
                threadPaletes[12]=oResultSet.getString("warp_M").toString();
                threadPaletes[13]=oResultSet.getString("warp_N").toString();
                threadPaletes[14]=oResultSet.getString("warp_O").toString();
                threadPaletes[15]=oResultSet.getString("warp_P").toString();
                threadPaletes[16]=oResultSet.getString("warp_Q").toString();
                threadPaletes[17]=oResultSet.getString("warp_R").toString();
                threadPaletes[18]=oResultSet.getString("warp_S").toString();
                threadPaletes[19]=oResultSet.getString("warp_T").toString();
                threadPaletes[20]=oResultSet.getString("warp_U").toString();
                threadPaletes[21]=oResultSet.getString("warp_V").toString();
                threadPaletes[22]=oResultSet.getString("warp_W").toString();
                threadPaletes[23]=oResultSet.getString("warp_X").toString();
                threadPaletes[24]=oResultSet.getString("warp_Y").toString();
                threadPaletes[25]=oResultSet.getString("warp_Z").toString();
                threadPaletes[26]=oResultSet.getString("weft_a").toString();
                threadPaletes[27]=oResultSet.getString("weft_b").toString();
                threadPaletes[28]=oResultSet.getString("weft_c").toString();
                threadPaletes[29]=oResultSet.getString("weft_d").toString();
                threadPaletes[30]=oResultSet.getString("weft_e").toString();
                threadPaletes[31]=oResultSet.getString("weft_f").toString();
                threadPaletes[32]=oResultSet.getString("weft_g").toString();
                threadPaletes[33]=oResultSet.getString("weft_h").toString();
                threadPaletes[34]=oResultSet.getString("weft_i").toString();
                threadPaletes[35]=oResultSet.getString("weft_j").toString();
                threadPaletes[36]=oResultSet.getString("weft_k").toString();
                threadPaletes[37]=oResultSet.getString("weft_l").toString();
                threadPaletes[38]=oResultSet.getString("weft_m").toString();
                threadPaletes[39]=oResultSet.getString("weft_n").toString();
                threadPaletes[40]=oResultSet.getString("weft_o").toString();
                threadPaletes[41]=oResultSet.getString("weft_p").toString();
                threadPaletes[42]=oResultSet.getString("weft_q").toString();
                threadPaletes[43]=oResultSet.getString("weft_r").toString();
                threadPaletes[44]=oResultSet.getString("weft_s").toString();
                threadPaletes[45]=oResultSet.getString("weft_t").toString();
                threadPaletes[46]=oResultSet.getString("weft_u").toString();
                threadPaletes[47]=oResultSet.getString("weft_v").toString();
                threadPaletes[48]=oResultSet.getString("weft_w").toString();
                threadPaletes[49]=oResultSet.getString("weft_x").toString();
                threadPaletes[50]=oResultSet.getString("weft_y").toString();
                threadPaletes[51]=oResultSet.getString("weft_z").toString();
                objPalette.setStrThreadPalette(threadPaletes);
                objPalette.setStrPaletteName(oResultSet.getString("name").toString());
                objPalette.setStrPaletteAccess(new IDGenerator().getUserAcessValueData("PALETTE_LIBRARY",oResultSet.getString("access").toString()));
                objPalette.setStrPaletteDate(oResultSet.getString("udate").toString());
            }
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityAction.class.getName(),"getPallet : "+strQuery,ex);
        } finally {
            try {
                if(oResultSet!=null) {
                    oResultSet.close();
                    oResultSet=null;
                }
                if(oStatement!=null) {
                    oStatement.close();
                    oStatement=null;
                }
                if(connection!=null) {
                    connection.close();
                    connection=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",UtilityAction.class.getName(),"getPallet() : Error while closing connection"+e,ex);
                }
            }
        }
    }
 
    public byte setPalette(Palette objPalette) {
        PreparedStatement oPreparedStatement=null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        try {           
            System.out.println("<<<<<< setFabricPalette >>>>>>");
            strQuery = "INSERT INTO `mla_palette_library` (`id`, `warp_A`, `warp_B`, `warp_C`, `warp_D`, `warp_E`, `warp_F`, `warp_G`, `warp_H`, `warp_I`, `warp_J`, `warp_K`, `warp_L`, `warp_M`, `warp_N`, `warp_O`, `warp_P`, `warp_Q`, `warp_R`, `warp_S`, `warp_T`, `warp_U`, `warp_V`, `warp_W`, `warp_X`, `warp_Y`, `warp_Z`, `weft_a`, `weft_b`, `weft_c`, `weft_d`, `weft_e`, `weft_f`, `weft_g`, `weft_h`, `weft_i`, `weft_j`, `weft_k`, `weft_l`, `weft_m`, `weft_n`, `weft_o`, `weft_p`, `weft_q`, `weft_r`, `weft_s`, `weft_t`, `weft_u`, `weft_v`, `weft_w`, `weft_x`, `weft_y`, `weft_z`, `name`, `access`, `userid`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, objPalette.getStrPaletteID());
            oPreparedStatement.setString(2, objPalette.getStrThreadPalette()[0]);
            oPreparedStatement.setString(3, objPalette.getStrThreadPalette()[1]);
            oPreparedStatement.setString(4, objPalette.getStrThreadPalette()[2]);
            oPreparedStatement.setString(5, objPalette.getStrThreadPalette()[3]);
            oPreparedStatement.setString(6, objPalette.getStrThreadPalette()[4]);
            oPreparedStatement.setString(7, objPalette.getStrThreadPalette()[5]);
            oPreparedStatement.setString(8, objPalette.getStrThreadPalette()[6]);
            oPreparedStatement.setString(9, objPalette.getStrThreadPalette()[7]);
            oPreparedStatement.setString(10, objPalette.getStrThreadPalette()[8]);
            oPreparedStatement.setString(11, objPalette.getStrThreadPalette()[9]);
            oPreparedStatement.setString(12, objPalette.getStrThreadPalette()[10]);
            oPreparedStatement.setString(13, objPalette.getStrThreadPalette()[11]);
            oPreparedStatement.setString(14, objPalette.getStrThreadPalette()[12]);
            oPreparedStatement.setString(15, objPalette.getStrThreadPalette()[13]);
            oPreparedStatement.setString(16, objPalette.getStrThreadPalette()[14]);
            oPreparedStatement.setString(17, objPalette.getStrThreadPalette()[15]);
            oPreparedStatement.setString(18, objPalette.getStrThreadPalette()[16]);
            oPreparedStatement.setString(19, objPalette.getStrThreadPalette()[17]);
            oPreparedStatement.setString(20, objPalette.getStrThreadPalette()[18]);
            oPreparedStatement.setString(21, objPalette.getStrThreadPalette()[19]);
            oPreparedStatement.setString(22, objPalette.getStrThreadPalette()[20]);
            oPreparedStatement.setString(23, objPalette.getStrThreadPalette()[21]);
            oPreparedStatement.setString(24, objPalette.getStrThreadPalette()[22]);
            oPreparedStatement.setString(25, objPalette.getStrThreadPalette()[23]);
            oPreparedStatement.setString(26, objPalette.getStrThreadPalette()[24]);
            oPreparedStatement.setString(27, objPalette.getStrThreadPalette()[25]);
            oPreparedStatement.setString(28, objPalette.getStrThreadPalette()[26]);
            oPreparedStatement.setString(29, objPalette.getStrThreadPalette()[27]);
            oPreparedStatement.setString(30, objPalette.getStrThreadPalette()[28]);
            oPreparedStatement.setString(31, objPalette.getStrThreadPalette()[29]);
            oPreparedStatement.setString(32, objPalette.getStrThreadPalette()[30]);
            oPreparedStatement.setString(33, objPalette.getStrThreadPalette()[31]);
            oPreparedStatement.setString(34, objPalette.getStrThreadPalette()[32]);
            oPreparedStatement.setString(35, objPalette.getStrThreadPalette()[33]);
            oPreparedStatement.setString(36, objPalette.getStrThreadPalette()[34]);
            oPreparedStatement.setString(37, objPalette.getStrThreadPalette()[35]);
            oPreparedStatement.setString(38, objPalette.getStrThreadPalette()[36]);
            oPreparedStatement.setString(39, objPalette.getStrThreadPalette()[37]);
            oPreparedStatement.setString(40, objPalette.getStrThreadPalette()[38]);
            oPreparedStatement.setString(41, objPalette.getStrThreadPalette()[39]);
            oPreparedStatement.setString(42, objPalette.getStrThreadPalette()[40]);
            oPreparedStatement.setString(43, objPalette.getStrThreadPalette()[41]);
            oPreparedStatement.setString(44, objPalette.getStrThreadPalette()[42]);
            oPreparedStatement.setString(45, objPalette.getStrThreadPalette()[43]);
            oPreparedStatement.setString(46, objPalette.getStrThreadPalette()[44]);
            oPreparedStatement.setString(47, objPalette.getStrThreadPalette()[45]);
            oPreparedStatement.setString(48, objPalette.getStrThreadPalette()[46]);
            oPreparedStatement.setString(49, objPalette.getStrThreadPalette()[47]);
            oPreparedStatement.setString(50, objPalette.getStrThreadPalette()[48]);
            oPreparedStatement.setString(51, objPalette.getStrThreadPalette()[49]);
            oPreparedStatement.setString(52, objPalette.getStrThreadPalette()[50]);
            oPreparedStatement.setString(53, objPalette.getStrThreadPalette()[51]);
            oPreparedStatement.setString(54, objPalette.getStrPaletteName());
            oPreparedStatement.setString(55, new IDGenerator().setUserAcessValueData("PALETTE_LIBRARY",objPalette.getStrPaletteAccess()));
            oPreparedStatement.setString(56, objPalette.getObjConfiguration().getObjUser().getStrUserID());
            oResult = (byte)oPreparedStatement.executeUpdate();
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityAction.class.getName(),"setPalette : "+strQuery,ex);
        } finally {
            try {
                if(oResultSet!=null) {
                    oResultSet.close();
                    oResultSet=null;
                }
                if(oPreparedStatement!=null) {
                    oPreparedStatement.close();
                    oPreparedStatement=null;
                }
                if(connection!=null) {
                    connection.close();
                    connection=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",UtilityAction.class.getName(),"setPalette() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< setFabricPalette >>>>>>");
        return oResult;
    }
    
    // update
    public byte resetPalette(Palette objPalette) {
        PreparedStatement oPreparedStatement=null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        try {           
            strQuery = "UPDATE `mla_palette_library` SET `id`=?, `warp_A`=?, `warp_B`=?, `warp_C`=?, `warp_D`=?, `warp_E`=?, `warp_F`=?, `warp_G`=?, `warp_H`=?, `warp_I`=?, `warp_J`=?, `warp_K`=?, `warp_L`=?, `warp_M`=?, `warp_N`=?, `warp_O`=?, `warp_P`=?, `warp_Q`=?, `warp_R`=?, `warp_S`=?, `warp_T`=?, `warp_U`=?, `warp_V`=?, `warp_W`=?, `warp_X`=?, `warp_Y`=?, `warp_Z`=?, `weft_a`=?, `weft_b`=?, `weft_c`=?, `weft_d`=?, `weft_e`=?, `weft_f`=?, `weft_g`=?, `weft_h`=?, `weft_i`=?, `weft_j`=?, `weft_k`=?, `weft_l`=?, `weft_m`=?, `weft_n`=?, `weft_o`=?, `weft_p`=?, `weft_q`=?, `weft_r`=?, `weft_s`=?, `weft_t`=?, `weft_u`=?, `weft_v`=?, `weft_w`=?, `weft_x`=?, `weft_y`=?, `weft_z`=?, `name`=?, `access`=?, `userid`=? WHERE id='"+objPalette.getStrPaletteID()+"';";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, objPalette.getStrPaletteID());
            oPreparedStatement.setString(2, objPalette.getStrThreadPalette()[0]);
            oPreparedStatement.setString(3, objPalette.getStrThreadPalette()[1]);
            oPreparedStatement.setString(4, objPalette.getStrThreadPalette()[2]);
            oPreparedStatement.setString(5, objPalette.getStrThreadPalette()[3]);
            oPreparedStatement.setString(6, objPalette.getStrThreadPalette()[4]);
            oPreparedStatement.setString(7, objPalette.getStrThreadPalette()[5]);
            oPreparedStatement.setString(8, objPalette.getStrThreadPalette()[6]);
            oPreparedStatement.setString(9, objPalette.getStrThreadPalette()[7]);
            oPreparedStatement.setString(10, objPalette.getStrThreadPalette()[8]);
            oPreparedStatement.setString(11, objPalette.getStrThreadPalette()[9]);
            oPreparedStatement.setString(12, objPalette.getStrThreadPalette()[10]);
            oPreparedStatement.setString(13, objPalette.getStrThreadPalette()[11]);
            oPreparedStatement.setString(14, objPalette.getStrThreadPalette()[12]);
            oPreparedStatement.setString(15, objPalette.getStrThreadPalette()[13]);
            oPreparedStatement.setString(16, objPalette.getStrThreadPalette()[14]);
            oPreparedStatement.setString(17, objPalette.getStrThreadPalette()[15]);
            oPreparedStatement.setString(18, objPalette.getStrThreadPalette()[16]);
            oPreparedStatement.setString(19, objPalette.getStrThreadPalette()[17]);
            oPreparedStatement.setString(20, objPalette.getStrThreadPalette()[18]);
            oPreparedStatement.setString(21, objPalette.getStrThreadPalette()[19]);
            oPreparedStatement.setString(22, objPalette.getStrThreadPalette()[20]);
            oPreparedStatement.setString(23, objPalette.getStrThreadPalette()[21]);
            oPreparedStatement.setString(24, objPalette.getStrThreadPalette()[22]);
            oPreparedStatement.setString(25, objPalette.getStrThreadPalette()[23]);
            oPreparedStatement.setString(26, objPalette.getStrThreadPalette()[24]);
            oPreparedStatement.setString(27, objPalette.getStrThreadPalette()[25]);
            oPreparedStatement.setString(28, objPalette.getStrThreadPalette()[26]);
            oPreparedStatement.setString(29, objPalette.getStrThreadPalette()[27]);
            oPreparedStatement.setString(30, objPalette.getStrThreadPalette()[28]);
            oPreparedStatement.setString(31, objPalette.getStrThreadPalette()[29]);
            oPreparedStatement.setString(32, objPalette.getStrThreadPalette()[30]);
            oPreparedStatement.setString(33, objPalette.getStrThreadPalette()[31]);
            oPreparedStatement.setString(34, objPalette.getStrThreadPalette()[32]);
            oPreparedStatement.setString(35, objPalette.getStrThreadPalette()[33]);
            oPreparedStatement.setString(36, objPalette.getStrThreadPalette()[34]);
            oPreparedStatement.setString(37, objPalette.getStrThreadPalette()[35]);
            oPreparedStatement.setString(38, objPalette.getStrThreadPalette()[36]);
            oPreparedStatement.setString(39, objPalette.getStrThreadPalette()[37]);
            oPreparedStatement.setString(40, objPalette.getStrThreadPalette()[38]);
            oPreparedStatement.setString(41, objPalette.getStrThreadPalette()[39]);
            oPreparedStatement.setString(42, objPalette.getStrThreadPalette()[40]);
            oPreparedStatement.setString(43, objPalette.getStrThreadPalette()[41]);
            oPreparedStatement.setString(44, objPalette.getStrThreadPalette()[42]);
            oPreparedStatement.setString(45, objPalette.getStrThreadPalette()[43]);
            oPreparedStatement.setString(46, objPalette.getStrThreadPalette()[44]);
            oPreparedStatement.setString(47, objPalette.getStrThreadPalette()[45]);
            oPreparedStatement.setString(48, objPalette.getStrThreadPalette()[46]);
            oPreparedStatement.setString(49, objPalette.getStrThreadPalette()[47]);
            oPreparedStatement.setString(50, objPalette.getStrThreadPalette()[48]);
            oPreparedStatement.setString(51, objPalette.getStrThreadPalette()[49]);
            oPreparedStatement.setString(52, objPalette.getStrThreadPalette()[50]);
            oPreparedStatement.setString(53, objPalette.getStrThreadPalette()[51]);
            oPreparedStatement.setString(54, objPalette.getStrPaletteName());
            oPreparedStatement.setString(55, new IDGenerator().setUserAcessValueData("PALETTE_LIBRARY",objPalette.getStrPaletteAccess()));
            oPreparedStatement.setString(56, objPalette.getObjConfiguration().getObjUser().getStrUserID());
            oResult = (byte)oPreparedStatement.executeUpdate();
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityAction.class.getName(),"setPalette : "+strQuery,ex);
        } finally {
            try {
                if(oResultSet!=null) {
                    oResultSet.close();
                    oResultSet=null;
                }
                if(oPreparedStatement!=null) {
                    oPreparedStatement.close();
                    oPreparedStatement=null;
                }
                if(connection!=null) {
                    connection.close();
                    connection=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",UtilityAction.class.getName(),"setPalette() : Error while closing connection"+e,ex);
                }
            }
        }
        return oResult;
    }
    
    public String getPaletteIdFromName(String paletteName){
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String[] palette=null;
        try {           
            strQuery = "select id from mla_palette_library where name='"+paletteName+"' LIMIT 1;";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            if(oResultSet.next()) {
                return oResultSet.getString("id").toString();
            }
        } catch (Exception ex) {
            new Logging("SEVERE",UtilityAction.class.getName(),"getPaletteName() : "+strQuery,ex);
        } finally {
            try {
                if(oResultSet!=null) {
                    oResultSet.close();
                    oResultSet=null;
                }
                if(oStatement!=null) {
                    oStatement.close();
                    oStatement=null;
                }
                if(connection!=null) {
                    connection.close();
                    connection=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",UtilityAction.class.getName(),"getPaletteName() : Error while closing connection"+e,ex);
                }
            }
        }
        return null;
    }
    /************************ *************************************************/
    
}