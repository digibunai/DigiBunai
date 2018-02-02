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

package com.mla.colour;

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
public class ColourAction {

    Connection connection = null; //DbConnect.getConnection();
    
    public ColourAction() throws SQLException{
        connection = DbConnect.getConnection();
    }
    
    public void close() throws SQLException{
        connection.close();
    }

    /**************************** Colour ******************************************/
   
    public byte setColour(Colour objColour) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        try {           
            //System.out.println("<<<<<< setColour >>>>>>");
            strQuery = "INSERT INTO `mla_colour_library` (`ID`, `NAME`, `TYPE`, `R_CODE`, `G_CODE`, `B_CODE`, `HEX_CODE`, `CODE`, `ACCESS`, `USERID`) VALUES (?,?,?,?,?,?,?,?,?,?);";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, new IDGenerator().getIDGenerator("COLOUR_LIBRARY", objColour.getObjConfiguration().getObjUser().getStrUserID()));
            oPreparedStatement.setString(2, objColour.getStrColorName());
            oPreparedStatement.setString(3, objColour.getStrColorType());
            oPreparedStatement.setInt(4, objColour.getIntR());
            oPreparedStatement.setInt(5, objColour.getIntG());
            oPreparedStatement.setInt(6, objColour.getIntB());
            oPreparedStatement.setString(7, objColour.getStrColorHex());
            oPreparedStatement.setString(8, objColour.getStrColorCode());
            oPreparedStatement.setString(9, new IDGenerator().setUserAcessKey("COLOUR_LIBRARY",objColour.getObjConfiguration().getObjUser()));
            oPreparedStatement.setString(10, objColour.getObjConfiguration().getObjUser().getStrUserID());
            oResult = (byte)oPreparedStatement.executeUpdate();
        } catch (Exception ex) {
            new Logging("SEVERE",ColourAction.class.getName(),"setColour : "+strQuery,ex);
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
                    new Logging("SEVERE",ColourAction.class.getName(),"setColour() : Error while closing connection"+e,ex);
                }
            }
        }
        //System.out.println(">>>>>> setColour <<<<<<");
        return oResult;
    }
	
    public String updateColourMaster(String name, String city, String userid) {
        PreparedStatement oPreparedStatement =null;
        Statement oStatement=null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String idGen=null;
        try {           
            //System.out.println("<<<<<< setColour >>>>>>");
            //if(city.length()<3)
            //    return idGen;
            //String cityCode=city.substring(0, 3).toUpperCase(); // VAR for varansi
            strQuery="SELECT `ID` from `mla_colour_master` WHERE `NAME`='"+name+"' AND `LOCATION`='"+city+"';";
            oStatement=connection.createStatement();
            oResultSet=oStatement.executeQuery(strQuery);
            if(oResultSet.next())// take its ID
                idGen=oResultSet.getString("ID");
            else{ // generate new id
                strQuery="SELECT count(*) from `mla_colour_master` WHERE `LOCATION`='"+city+"';";
                oResultSet=oStatement.executeQuery(strQuery);
                if(oResultSet.next())
                    idGen=city+"-"+oResultSet.getInt(1);
            }
            if(idGen==null)
                return idGen;
            
            strQuery = "INSERT INTO `mla_colour_master` (`ID`, `NAME`, `USERID`, `LOCATION`, `COUNT`) VALUES (?,?,?,?,?);";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, idGen);
            oPreparedStatement.setString(2, name);
            oPreparedStatement.setString(3, userid);
            oPreparedStatement.setString(4, city);
            oPreparedStatement.setInt(5, 0);
            if(oPreparedStatement.executeUpdate()==1)
                return idGen;
            else
                return null;
        } catch (Exception ex) {
            new Logging("SEVERE",ColourAction.class.getName(),"updateColourMaster : "+strQuery,ex);
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
                    new Logging("SEVERE",ColourAction.class.getName(),"updateColourMaster() : Error while closing connection"+e,ex);
                }
            }
        }
        //System.out.println(">>>>>> setColour <<<<<<");
        return idGen;
    }
    
    public boolean isColorAvailable(Colour objColour) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        boolean isAvailable=false;
        try {           
            String cond = "(USERID = '"+objColour.getObjConfiguration().getObjUser().getStrUserID()+"' OR `ACCESS`='"+new IDGenerator().getUserAcess("COLOUR_LIBRARY")+"') ";
            if(objColour.getStrOrderBy().equals("HEX_CODE")) {
                cond += " AND `HEX_CODE` = '"+objColour.getStrColorHex()+"'";
            }
            else if(objColour.getStrOrderBy().equals("R_CODE")) {
                cond += " AND `R_CODE`="+objColour.getIntR();
                cond += " AND `B_CODE`="+objColour.getIntB();
                cond += " AND `G_CODE`="+objColour.getIntG();
            }
            if(!(objColour.getStrSearchBy().trim().equals("")) && !(objColour.getStrSearchBy().trim().equalsIgnoreCase("All"))) {
                cond += " AND `TYPE` LIKE '%"+objColour.getStrSearchBy().trim()+"%'";
            }
            strQuery = "select * from `mla_colour_library` WHERE "+cond;
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            if(oResultSet.next()) {
                objColour.setStrColorID(oResultSet.getString("ID"));
                objColour.setStrColorName(oResultSet.getString("NAME"));
                objColour.setStrColorType(oResultSet.getString("TYPE"));
                objColour.setIntR(oResultSet.getInt("R_CODE"));
                objColour.setIntG(oResultSet.getInt("G_CODE"));
                objColour.setIntB(oResultSet.getInt("B_CODE"));
                objColour.setStrColorHex(oResultSet.getString("HEX_CODE"));
                objColour.setStrColorCode(oResultSet.getString("CODE"));
                objColour.setStrColorAccess(oResultSet.getString("ACCESS"));
                objColour.setStrColorDate(oResultSet.getString("UDATE"));
                isAvailable=true;
            }
        } catch (Exception ex) {
            new Logging("SEVERE",ColourAction.class.getName(),"isColorAvailable : "+strQuery,ex);
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
                    new Logging("SEVERE",ColourAction.class.getName(),"isColorAvailable() : Error while closing connection"+e,ex);
                }
            }
        }
        return isAvailable;
    }
    
    public int updateColourMasterCount(String id) {
        PreparedStatement oPreparedStatement =null;
        Statement oStatement=null;
        ResultSet oResultSet= null;
        String strQuery=null;
        int count=0;
        byte oResult=0;
        try {           
            //System.out.println("<<<<<< setColour >>>>>>");
            strQuery="SELECT count(*) from `mla_colour_library` WHERE `TYPE`='"+id+"';";
            oStatement=connection.createStatement();
            oResultSet=oStatement.executeQuery(strQuery);
            if(oResultSet.next())// take its ID
                count=oResultSet.getInt(1);
            
            strQuery = "UPDATE `mla_colour_master` SET `COUNT`="+count+" WHERE `ID`='"+id+"';";
            oResult=(byte)oStatement.executeUpdate(strQuery);
            if(oResult!=0)
                return count;
        } catch (Exception ex) {
            new Logging("SEVERE",ColourAction.class.getName(),"updateColourMasterCount : "+strQuery,ex);
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
                    new Logging("SEVERE",ColourAction.class.getName(),"updateColourMasterCount() : Error while closing connection"+e,ex);
                }
            }
        }
        //System.out.println(">>>>>> setColour <<<<<<");
        return count;
    }
    
    public ArrayList<String> getColourType(){
        Statement oStatement=null;
        ResultSet oResultSet= null;
        String strQuery=null;
        ArrayList<String> type=null;
        try {           
            //System.out.println("<<<<<< setColour >>>>>>");
            strQuery="SELECT DISTINCT `TYPE` from `mla_colour_library`;";
            oStatement=connection.createStatement();
            oResultSet=oStatement.executeQuery(strQuery);
            type=new ArrayList<>();
            while(oResultSet.next())
                type.add(oResultSet.getString(1));
        } catch (Exception ex) {
            new Logging("SEVERE",ColourAction.class.getName(),"getColourType : "+strQuery,ex);
        } finally {
            try {
                if(oResultSet!=null) {
                    oResultSet.close();
                    oResultSet=null;
                }
                if(connection!=null) {
                    connection.close();
                    connection=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",ColourAction.class.getName(),"getColourType() : Error while closing connection"+e,ex);
                }
            }
        }
        return type;
    }
}
