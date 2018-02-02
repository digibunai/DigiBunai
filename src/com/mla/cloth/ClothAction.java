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

package com.mla.cloth;

import com.mla.fabric.Fabric;
import com.mla.fabric.FabricAction;
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

/**
 *
 * @author Aatif Ahmad Khan
 */
public class ClothAction {
    Connection connection = null;
    
    public ClothAction() throws SQLException{
        connection = DbConnect.getConnection();
    }
    
    public byte setCloth(Cloth objCloth) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        new Logging("INFO",ClothAction.class.getName(),">>>>>>>>>>> setCloth()",null);
        try {           
            strQuery = "INSERT INTO `mla_cloth_library` (`ID`, `NAME`, `DESCRIPTION`, `SNAPSHOT`, `REGION`, `ACCESS`, `USERID`) VALUES (?,?,?,?,?,?,?);";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, objCloth.getStrClothId());
            oPreparedStatement.setString(2, objCloth.getStrClothName());
            oPreparedStatement.setString(3, objCloth.getStrClothDescription());
            oPreparedStatement.setBinaryStream(4,new ByteArrayInputStream(objCloth.getBytClothIcon()),objCloth.getBytClothIcon().length);
            oPreparedStatement.setString(5, objCloth.getStrClothRegion());
            oPreparedStatement.setString(6, new IDGenerator().setUserAcessValueData("CLOTH_LIBRARY",objCloth.getStrClothAccess()));
            oPreparedStatement.setString(7, objCloth.getObjConfiguration().getObjUser().getStrUserID());
            oResult = (byte)oPreparedStatement.executeUpdate();
            // Now insert to mla_cloth_fabric
            if(oResult!=0){
                new Logging("INFO",ClothAction.class.getName(),"setCloth(): entry inserted into library for "+objCloth.getStrClothName(),null);
                for(String s: objCloth.mapComponentFabric.keySet()){
                    if(objCloth.mapComponentFabric.get(s)!=null){
                        strQuery = "INSERT INTO `mla_cloth_fabric` (`CLOTHID`, `CLOTHTYPE`, `FABRICID`) VALUES (?,?,?);";
                        oPreparedStatement = connection.prepareStatement(strQuery);
                        oPreparedStatement.setString(1, objCloth.getStrClothId());
                        oPreparedStatement.setString(2, s);
                        oPreparedStatement.setString(3, objCloth.mapComponentFabric.get(s));
                        oResult = (byte)oPreparedStatement.executeUpdate();
                        if(oResult==0){
                            new Logging("SEVERE",ClothAction.class.getName(),"setCloth(): error while inserting entry for clothtype: "+s,null);
                            break;
                        }
                        new Logging("INFO",ClothAction.class.getName(),"setCloth(): entry inserted for cloth fabric for "+s,null);
                    }
                }
            }
        } catch (Exception ex) {
            new Logging("SEVERE",ClothAction.class.getName(),"setCloth: error while inserting into library caused by "+ex.getCause(),ex);
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
                    new Logging("SEVERE",ClothAction.class.getName(),"setCloth(): error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",ClothAction.class.getName(),"setCloth() >>>>>>>>>>>",null);
        return oResult;
    }
    
    public void getCloth(Cloth objCloth) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        ResultSet resultSet=null;
        String strQuery=null;
        new Logging("INFO",ClothAction.class.getName(),">>>>>>>>>>> getCloth()",null);
        try {           
            String cond = " 1";
            if(objCloth.getStrClothId()!="" && objCloth.getStrClothId()!=null)
                cond += " AND ID='"+objCloth.getStrClothId()+"'";
            strQuery = "select * from mla_cloth_library WHERE "+cond+" LIMIT 1;";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);  
            if(oResultSet.next()){
                objCloth.setStrClothId(oResultSet.getString("ID").toString());
                objCloth.setStrClothName(oResultSet.getString("NAME").toString());
                objCloth.setStrClothDescription(oResultSet.getString("DESCRIPTION").toString());
                objCloth.setBytClothIcon(oResultSet.getBytes("SNAPSHOT"));
                objCloth.setStrClothRegion(oResultSet.getString("REGION").toString());
                objCloth.setStrClothAccess(new IDGenerator().getUserAcessValueData("CLOTH_LIBRARY",oResultSet.getString("ACCESS")));
                objCloth.setStrClothDate(oResultSet.getTimestamp("UDATE").toString());
                new Logging("INFO",ClothAction.class.getName(),"getCloth(): entry fetched from library for "+objCloth.getStrClothName(),null);
                strQuery = "select * from `mla_cloth_fabric` WHERE `CLOTHID`='"+oResultSet.getString("ID").toString()+"';";
                oStatement = connection.createStatement();
                resultSet = oStatement.executeQuery(strQuery);
                while(resultSet.next()){
                    objCloth.mapComponentFabric.put(resultSet.getString("CLOTHTYPE").toString(), resultSet.getString("FABRICID").toString());
                    new Logging("INFO",ClothAction.class.getName(),"getCloth(): entry fetched from cloth fabric for "+resultSet.getString("CLOTHTYPE").toString(),null);
                }
            }
        } catch (Exception ex) {
            new Logging("SEVERE",ClothAction.class.getName(),"getCloth: error while fetching from library caused by "+ex.getCause(),ex);
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
                    new Logging("SEVERE",ClothAction.class.getName(),"getCloth() : error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",ClothAction.class.getName(),"getCloth() >>>>>>>>>>>",null);
        return;
    }
    
    public List lstImportArtwork(Cloth objCloth) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        List lstClothDeatails=null, lstCloth;
        new Logging("INFO",ClothAction.class.getName(),">>>>>>>>>>> lstImportCloth()",null);            
        try {           
            String cond = "(USERID = '"+objCloth.getObjConfiguration().getObjUser().getStrUserID()+"' OR `ACCESS`='"+new IDGenerator().getUserAcess("CLOTH_LIBRARY")+"') ";
            String orderBy ="NAME ";
            if(!(objCloth.getStrCondition().trim().equals(""))) {
                cond += " AND NAME  LIKE '"+objCloth.getStrCondition().trim()+"%'";
            }
            if(objCloth.getStrOrderBy().equals("Name")) {
                orderBy = "NAME ";
            } else if(objCloth.getStrOrderBy().equals("Date")) {
                orderBy = "UDATE";
            }
            if(objCloth.getStrDirection().equals("Ascending")) {
                orderBy += " ASC";
            } else if(objCloth.getStrDirection().equals("Descending")) {
                orderBy += " DESC";
            }
            strQuery = "select * from mla_cloth_library WHERE "+cond+" ORDER BY "+orderBy;
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);    
            lstClothDeatails = new ArrayList();            
            while(oResultSet.next()) {
                lstCloth = new ArrayList();                
                lstCloth.add(oResultSet.getString("ID").toString());
                lstCloth.add(oResultSet.getString("NAME").toString());
                lstCloth.add(oResultSet.getString("DESCRIPTION").toString());
                lstCloth.add(oResultSet.getBytes("SNAPSHOT"));
                lstCloth.add(oResultSet.getString("REGION").toString());
                lstCloth.add(oResultSet.getString("ACCESS").toString());
                lstCloth.add(oResultSet.getString("USERID").toString());
                lstCloth.add(oResultSet.getTimestamp("UDATE").toString());
                lstClothDeatails.add(lstCloth);
            }
        } catch (Exception ex) {
            new Logging("SEVERE",ClothAction.class.getName(),"lstImportCloth() : "+strQuery,ex);
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
                    new Logging("SEVERE",ClothAction.class.getName(),"lstImportCloth() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",ClothAction.class.getName(),"lstImportCloth() >>>>>>>>>>>"+strQuery,null);
        return lstClothDeatails;
    }
}
