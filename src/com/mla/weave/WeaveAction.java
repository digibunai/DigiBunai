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
package com.mla.weave;

import com.mla.fabric.Fabric;
import com.mla.main.DbConnect;
import com.mla.main.Logging;
import com.mla.main.IDGenerator;
import com.mla.yarn.Yarn;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
/**
 *
 * @Designing model function for weave
 * @author Amit Kumar Singh
 * 
 */
public class WeaveAction {
    
    Connection connection = null; //DbConnect.getConnection();
    Weave objWeave;
    
    public WeaveAction() throws SQLException{
        connection = DbConnect.getConnection();
    }
    
    public WeaveAction(boolean isDB) throws SQLException{
        if(isDB)
            connection = DbConnect.getConnection();        
    }
    
    public WeaveAction(Weave objWeaveCall, boolean isDB) throws SQLException{
        if(isDB)
            connection = DbConnect.getConnection();
        objWeave = objWeaveCall;
    }
    
    public int countWeaveUsage(String strWeaveID){
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        int count = 0;
        try {           
            //System.out.println("<<<<<< countWeaveUsage >>>>>>");
            strQuery = "SELECT COUNT(*) from mla_fabric_library WHERE `BASEWEAVEID` = '"+strWeaveID+"' ORDER BY `BASEWEAVEID` DESC;";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);  
            while(oResultSet.next()) {
                count += oResultSet.getInt(1);
            }
            strQuery = "SELECT COUNT(*) from mla_fabric_artwork WHERE `weave_id` = '"+strWeaveID+"' ORDER BY `weave_id` DESC;";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);  
            while(oResultSet.next()) {
                count += oResultSet.getInt(1);
            }
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"countWeaveUsage() : "+strQuery,ex);
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
                    new Logging("SEVERE",WeaveAction.class.getName(),"countWeaveUsage() : Error while closing connection"+e,ex);
                }
            }
        }
        //System.out.println("<<<<<< countWeaveUsage >>>>>>"+count);
        return count;
    }

    public boolean clearWeave(String strWeaveID){
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        boolean oResult= false;
        String strQuery=null;
        try {           
            //System.out.println("<<<<<< clearWeave >>>>>>");
            strQuery = "DELETE FROM mla_weave_library WHERE `ID` = ?;";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, strWeaveID);
            oResult = oPreparedStatement.execute();           
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"clearWeave() : "+strQuery,ex);
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
                    new Logging("SEVERE",WeaveAction.class.getName(),"clearWeave() : Error while closing connection"+e,ex);
                }
            }
        }
        //System.out.println("<<<<<< clearWeave >>>>>>");
        return oResult;
    }
    
    public List lstImportWeave(Weave objWeave) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        List lstWeaveDeatails=null, lstWeave;
        String strWeaveID = null, strWeaveName = null, strWeaveType = null, strWeaveCategory = null, strWeaveDate = null, strWeaveUser = null, strWeaveAccess = null;
        int intIsLiftplan=0, intIsColor=0, intWeaveShaft=0, intWeaveTreadles=0, intWeaveWeftRepeat=0, intWeaveWarpRepeat=0, intWeaveWeftFloat=0, intWeaveWarpFloat=0;
        byte[] bytWeaveThumbnil;
        new Logging("INFO",WeaveAction.class.getName(),"<<<<<<<<<<< lstImportWeave() >>>>>>>>>>>",null);
        try {
            String cond = "(USERID = '"+objWeave.getObjConfiguration().getObjUser().getStrUserID()+"' OR `ACCESS`='"+new IDGenerator().getUserAcess("WEAVE_LIBRARY")+"') ";
            String orderBy ="NAME ";                        
            if(!(objWeave.getStrCondition().trim().equals(""))) {
                cond += " AND NAME LIKE '"+objWeave.getStrCondition().trim()+"%'";
            }
            if(objWeave.getStrSearchBy().equals("All")) {
                cond+=" ";
            } else if(objWeave.getStrSearchBy().equals("Plain")) {
                cond+=" AND CATEGORY='"+objWeave.getStrSearchBy().trim()+"'";
            } else if(objWeave.getStrSearchBy().equals("Twill")) {
                cond+=" AND CATEGORY='"+objWeave.getStrSearchBy().trim()+"'";
            } else if(objWeave.getStrSearchBy().equals("Satin")) {
                cond+=" AND CATEGORY='"+objWeave.getStrSearchBy().trim()+"'";
            } else if(objWeave.getStrSearchBy().equals("Basket")) {
                cond+=" AND CATEGORY='"+objWeave.getStrSearchBy().trim()+"'";
            } else if(objWeave.getStrSearchBy().equals("Sateen")){
                cond+=" AND CATEGORY='"+objWeave.getStrSearchBy().trim()+"'";
            } 
			
            if(objWeave.getStrOrderBy().equals("Name")) {
                orderBy = "NAME";
            } else if(objWeave.getStrOrderBy().equals("Date")) {
                orderBy = "UDATE";
            } else if(objWeave.getStrOrderBy().equals("Shaft")) {
                orderBy = "SHAFT";
            } else if(objWeave.getStrOrderBy().equals("Treadles")) {
                orderBy = "TREADLE";
            } else if(objWeave.getStrOrderBy().equals("Float X")) {
                orderBy = "FLOAT_WEFT";
            }else if(objWeave.getStrOrderBy().equals("Float Y")) {
                orderBy = "FLOAT_WARP";
            }
            if(objWeave.getStrDirection().equals("Ascending")) {
                orderBy += " ASC";
            } else if(objWeave.getStrDirection().equals("Descending")) {
                orderBy += " DESC";
            }
            strQuery = "select * from mla_weave_library WHERE "+cond+" ORDER BY "+orderBy;
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);    
            lstWeaveDeatails = new ArrayList();            
            while(oResultSet.next()) {
                strWeaveID=oResultSet.getString("ID").toString();
                strWeaveName=oResultSet.getString("NAME").toString();
                bytWeaveThumbnil=oResultSet.getBytes("ICON");
                strWeaveType=oResultSet.getString("TYPE");
                strWeaveCategory=oResultSet.getString("CATEGORY");
                intIsLiftplan=oResultSet.getInt("IS_LIFTPLAN");
                intIsColor=oResultSet.getInt("IS_COLOR");
                intWeaveShaft=oResultSet.getInt("SHAFT");
                intWeaveTreadles=oResultSet.getInt("TREADLE");
                intWeaveWeftRepeat=oResultSet.getInt("REPEAT_X");
                intWeaveWarpRepeat=oResultSet.getInt("REPEAT_Y");
                intWeaveWeftFloat=oResultSet.getInt("FLOAT_WEFT");
                intWeaveWarpFloat=oResultSet.getInt("FLOAT_WARP");
                strWeaveDate = oResultSet.getTimestamp("UDATE").toString();
                strWeaveUser = oResultSet.getString("USERID").toString();
                strWeaveAccess = oResultSet.getString("ACCESS").toString();
                
                lstWeave = new ArrayList();
                lstWeave.add(strWeaveID);
                lstWeave.add(strWeaveName);
                lstWeave.add(bytWeaveThumbnil);
                lstWeave.add(strWeaveType);
                lstWeave.add(strWeaveCategory);
                lstWeave.add(intIsLiftplan);
                lstWeave.add(intIsColor);
                lstWeave.add(intWeaveShaft);
                lstWeave.add(intWeaveTreadles);
                lstWeave.add(intWeaveWeftRepeat);
                lstWeave.add(intWeaveWarpRepeat);
                lstWeave.add(intWeaveWeftFloat);
                lstWeave.add(intWeaveWarpFloat);
                lstWeave.add(strWeaveDate);
                lstWeave.add(strWeaveUser);
                lstWeave.add(strWeaveAccess);                
                lstWeaveDeatails.add(lstWeave);
            }
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"lstImportWeave() : "+strQuery,ex);
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
                    new Logging("SEVERE",WeaveAction.class.getName(),"lstImportWeave() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",WeaveAction.class.getName(),">>>>>>>>>>> lstImportWeave() <<<<<<<<<<<"+strQuery,null);
        return lstWeaveDeatails;
    }
    
    public void getWeave(Weave objWeave) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null; 
        new Logging("INFO",WeaveAction.class.getName(),"<<<<<<<<<<< getWeave() >>>>>>>>>>>",null);
        try {  
            String cond = " 1";
            if(objWeave.getStrWeaveID()!="" && objWeave.getStrWeaveID()!=null)
                cond += " AND ID='"+objWeave.getStrWeaveID()+"'";
            strQuery = "select * from mla_weave_library WHERE "+cond+" LIMIT 1;";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery); 
            while(oResultSet.next()) {
                objWeave.setStrWeaveID(oResultSet.getString("ID").toString());
                objWeave.setStrWeaveName(oResultSet.getString("NAME").toString());
                objWeave.setStrWeaveFile(oResultSet.getString("FILE").toString());
                objWeave.setBytWeaveThumbnil(oResultSet.getBytes("ICON"));
                objWeave.setStrWeaveType(oResultSet.getString("TYPE").toString());
                objWeave.setStrWeaveCategory(oResultSet.getString("CATEGORY").toString());
                objWeave.setIntWeaveShaft(oResultSet.getInt("SHAFT"));
                objWeave.setIntWeaveTreadles(oResultSet.getInt("TREADLE"));
                objWeave.setIntWeaveRepeatX(oResultSet.getInt("REPEAT_X"));
                objWeave.setIntWeaveRepeatY(oResultSet.getInt("REPEAT_Y"));
                objWeave.setIntWeaveFloatX(oResultSet.getInt("FLOAT_WEFT"));
                objWeave.setIntWeaveFloatY(oResultSet.getInt("FLOAT_WARP"));
                objWeave.setBytIsLiftPlan(oResultSet.getByte("IS_LIFTPLAN"));
                objWeave.setBytIsColor(oResultSet.getByte("IS_COLOR"));
                objWeave.setStrWeaveAccess(oResultSet.getString("ACCESS"));
                objWeave.setStrWeaveDate(oResultSet.getString("UDATE"));                
            } 
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"getWeave() : "+strQuery,ex);
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
                    new Logging("SEVERE",WeaveAction.class.getName(),"getWeave() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",WeaveAction.class.getName(),">>>>>>>>>>> getWeave() <<<<<<<<<<<"+strQuery,null);        
        return;
    }
    
    public byte setWeave(Weave objWeave) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        new Logging("INFO",WeaveAction.class.getName(),"<<<<<<<<<<< setWeave() >>>>>>>>>>>",null);
        try {
            strQuery = "INSERT INTO mla_weave_library (ID,NAME,FILE,ICON,CATEGORY,SHAFT,TREADLE,REPEAT_X,REPEAT_Y,FLOAT_WEFT,FLOAT_WARP,USERID,ACCESS) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, objWeave.getStrWeaveID());
            oPreparedStatement.setString(2, objWeave.getStrWeaveName());
            oPreparedStatement.setString(3, objWeave.getStrWeaveFile());
            oPreparedStatement.setBytes(4, objWeave.getBytWeaveThumbnil());
            //oPreparedStatement.setBinaryStream(4,new ByteArrayInputStream(objWeave.getBytWeaveThumbnil()),objWeave.getBytWeaveThumbnil().length);
            //oPreparedStatement.setString(5, objWeave.getStrWeaveType());
            oPreparedStatement.setString(5, objWeave.getStrWeaveCategory());
            oPreparedStatement.setInt(6, objWeave.getIntWeaveShaft());
            oPreparedStatement.setInt(7, objWeave.getIntWeaveTreadles());
            oPreparedStatement.setInt(8, objWeave.getIntWeaveRepeatX());
            oPreparedStatement.setInt(9, objWeave.getIntWeaveRepeatY());
            oPreparedStatement.setInt(10, objWeave.getIntWeaveFloatX());
            oPreparedStatement.setInt(11, objWeave.getIntWeaveFloatY());
            oPreparedStatement.setString(12, objWeave.getObjConfiguration().getObjUser().getStrUserID());
            oPreparedStatement.setString(13, new IDGenerator().setUserAcessValueData("WEAVE_LIBRARY",objWeave.getStrWeaveAccess()));
            oResult = (byte)oPreparedStatement.executeUpdate();
        } catch (SQLException ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"setWeave() : "+strQuery,ex);
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
                    new Logging("SEVERE",WeaveAction.class.getName(),"setWeave() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",WeaveAction.class.getName(),">>>>>>>>>>> setWeave() <<<<<<<<<<<"+strQuery,null);
        return oResult;
    }
    
    public byte resetWeave(Weave objWeave){
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        new Logging("INFO",WeaveAction.class.getName(),"<<<<<<<<<<< resetWeave() >>>>>>>>>>>",null);
        try {
            strQuery = "UPDATE mla_weave_library SET NAME = ?, FILE = ?, ICON = ?, CATEGORY = ?, SHAFT = ?, TREADLE = ?, REPEAT_X = ?, REPEAT_Y = ?, FLOAT_WEFT = ?, FLOAT_WARP = ?, ACCESS = ? WHERE USERID = ? AND ID = ?;";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, objWeave.getStrWeaveName());
            oPreparedStatement.setString(2, objWeave.getStrWeaveFile());
            oPreparedStatement.setBytes(3, objWeave.getBytWeaveThumbnil());
            //oPreparedStatement.setBinaryStream(3,new ByteArrayInputStream(objWeave.getBytWeaveThumbnil()),objWeave.getBytWeaveThumbnil().length);
            //oPreparedStatement.setString(4, objWeave.getStrWeaveType());setStrWeaveCategory
            oPreparedStatement.setString(4, objWeave.getStrWeaveCategory());
            oPreparedStatement.setInt(5, objWeave.getIntWeaveShaft());
            oPreparedStatement.setInt(6, objWeave.getIntWeaveTreadles());
            oPreparedStatement.setInt(7, objWeave.getIntWeaveRepeatX());
            oPreparedStatement.setInt(8, objWeave.getIntWeaveRepeatY());
            oPreparedStatement.setInt(9, objWeave.getIntWeaveFloatX());
            oPreparedStatement.setInt(10, objWeave.getIntWeaveFloatY());
            oPreparedStatement.setString(11, new IDGenerator().setUserAcessValueData("WEAVE_LIBRARY",objWeave.getStrWeaveAccess()));
            oPreparedStatement.setString(12, objWeave.getObjConfiguration().getObjUser().getStrUserID());
            oPreparedStatement.setString(13, objWeave.getStrWeaveID());
            oResult = (byte)oPreparedStatement.executeUpdate();
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"resetWeave : "+strQuery,ex);
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
                    new Logging("SEVERE",WeaveAction.class.getName(),"resetWeave() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",WeaveAction.class.getName(),">>>>>>>>>>> resetWeave() <<<<<<<<<<<"+strQuery,null);
        return oResult;
    }
    
    public byte resetWeavePermission(String strWeaveId, String strAccess){
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        new Logging("INFO",WeaveAction.class.getName(),"<<<<<<<<<<< resetWeavePermission() >>>>>>>>>>>",null);
        try {
            strQuery = "UPDATE mla_weave_library SET `ACCESS` = ? WHERE ID = ?;";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, strAccess);
            oPreparedStatement.setString(2, strWeaveId);
            oResult = (byte)oPreparedStatement.executeUpdate();
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"resetWeavePermission : "+strQuery,ex);
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
                    new Logging("SEVERE",WeaveAction.class.getName(),"resetWeavePermission() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",WeaveAction.class.getName(),">>>>>>>>>>> resetWeavePermission() <<<<<<<<<<<"+strQuery,null);
        return oResult;
    }
    //============================================================================
    
    /*
    public String getWeaveContent(Weave objWeave) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String strWeaveFile = null;
        try {    
            connection = DbConnect.getConnection();
            System.out.println("<<<<<< getWeaveContent >>>>>>");
            strQuery = "select FILE from weave_library where ID='"+objWeave.getStrWeaveID().trim()+"';";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery); 
            while(oResultSet.next()) {
                strWeaveFile=oResultSet.getString("FILE").toString();  
            }            
            return strWeaveFile;                 
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"getWeaveContent() : "+strQuery,ex);
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
                    new Logging("SEVERE",WeaveAction.class.getName(),"getWeaveContent() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< getWeaveContent >>>>>>");
        return null;
    }    
    */

    public boolean isValidWeave(Weave objWeave){
        boolean isValid = true;
        for(int i=0; i<objWeave.getIntWeft(); i++){
            int intTemp = 0;
            for(int j=0; j<objWeave.getIntWarp(); j++)
                if(objWeave.getDesignMatrix()[i][j]==0)
                    intTemp++;
            if(intTemp==objWeave.getIntWarp())
                isValid = false;
        }
        for(int j=0; j<objWeave.getIntWarp(); j++){
            int intTemp = 0;
            for(int i=0; i<objWeave.getIntWeft(); i++)
                if(objWeave.getDesignMatrix()[i][j]==0)
                    intTemp++;
            if(intTemp==objWeave.getIntWeft())
                isValid = false;    
        }
        return isValid;
    }
    
    public boolean isValidWeaveContent(String content){
        boolean isValid = true;
        //System.err.println("Hi Amit! I am alive here."+content);
        if(!content.contains("[CONSTRAINT]")){
            if(!content.contains("[WEAVING]"))
                isValid = false;
            if(!content.contains("[WARP]"))
                isValid = false;
            if(!content.contains("[WEFT]"))
                isValid = false;
            if(!content.contains("[THREADING]"))
                isValid = false;
            if(!content.contains("[LIFTPLAN]"))
                if(!content.contains("[TREADLING]") || !content.contains("[TIEUP]"))
                    isValid = false;
        } else{
            isValid = false;
        }        
        if(!content.contains("Treadles="))
            isValid = false;
        if(!content.contains("Shafts="))
            isValid = false;
        return isValid;
    }
    
    
   public void injectWeaveContent(Weave objWeave) {
        try {
            ArrayList<String> lstEntry = new ArrayList();
            if(objWeave.getWarpYarn()!=null && objWeave.getWeftYarn()!=null){
                for(int i=0; i<objWeave.getWarpYarn().length; i++){
                    //add the first color on array
                    if(lstEntry.size()==0){
                        lstEntry.add(objWeave.getWarpYarn()[i].getStrYarnColor());
                    }
                    //check for redudancy
                    else {                
                        if(!lstEntry.contains(objWeave.getWarpYarn()[i].getStrYarnColor()))
                            lstEntry.add(objWeave.getWarpYarn()[i].getStrYarnColor());
                    }
                }
                for(int i=0; i<objWeave.getWeftYarn().length; i++){
                    //add the first color on array
                    if(lstEntry.size()==0){
                        lstEntry.add(objWeave.getWeftYarn()[i].getStrYarnColor());
                    }
                    //check for redudancy
                    else {                
                        if(!lstEntry.contains(objWeave.getWeftYarn()[i].getStrYarnColor()))
                            lstEntry.add(objWeave.getWeftYarn()[i].getStrYarnColor());
                    }
                }
            }
            
            Date date = new Date(); 
            SimpleDateFormat ft = new SimpleDateFormat (" MM/dd/yyyy");
            
            StringBuffer content=new StringBuffer("[WIF]\n");
            content.append("Version=1.0"+"\n");
            content.append("Date= "+ft.format(date)+"\n");
            content.append("Developers=Media Lab Asia"+"\n");
            content.append("Source Program=Media Lab Asia"+"\n");
            content.append("Source Version=1.0"+"\n");
            content.append("[CONTENTS]"+"\n");
            content.append("TEXT=no"+"\n");
            content.append("WEAVING=yes"+"\n");
            content.append("WARP=yes"+"\n");
            content.append("WEFT=yes"+"\n");
            if(objWeave.getBytIsLiftPlan()==0){
                content.append("TIEUP=yes"+"\n");
                content.append("TREADLING=yes"+"\n");
            }else{
                content.append("TIEUP=no"+"\n");
                content.append("TREADLING=no"+"\n");
                content.append("LIFTPLAN=yes"+"\n");
            }
            content.append("THREADING=yes"+"\n");
            if(objWeave.getWarpYarn()!=null && objWeave.getWeftYarn()!=null){
                content.append("COLOR PALETTE=yes"+"\n");
                content.append("COLOR TABLE=yes"+"\n");
                content.append("WARP COLORS=yes"+"\n");
                content.append("WEFT COLORS=yes"+"\n");
            }else{
                content.append("COLOR PALETTE=no"+"\n");
                content.append("COLOR TABLE=no"+"\n");
                content.append("WARP COLORS=no"+"\n");
                content.append("WEFT COLORS=no"+"\n");
            }
            content.append("[WEAVING]"+"\n");
            content.append("Shafts="+objWeave.getIntWeaveShaft()+"\n");
            content.append("Treadles="+objWeave.getIntWeaveTreadles()+"\n");
            content.append("Rising Shed=yes"+"\n");
            content.append("Profile=no"+"\n");
            content.append("[COLOR PALETTE]"+"\n");
            if(lstEntry.size()>0){
                content.append("Entries="+lstEntry.size()+"\n");
            }else{
                content.append("Entries=2"+"\n");
            }
            content.append("Form=RGB"+"\n");
            content.append("Range=0,255"+"\n");
            content.append("[COLOR TABLE]"+"\n");
            if(lstEntry.size()>0){
                for(int i=0; i<lstEntry.size(); i++){
                    content.append((i+1)+"="+
                            Integer.valueOf( lstEntry.get(i).substring( 1, 3 ), 16 )+","+
                            Integer.valueOf( lstEntry.get(i).substring( 3, 5 ), 16 )+","+
                            Integer.valueOf( lstEntry.get(i).substring( 5, 7 ), 16 )+"\n");
                }
            }else{
                content.append("1=255,255,255"+"\n");
                content.append("2=0,0,0"+"\n");
            }
            content.append("[WARP]"+"\n");
            content.append("Threads="+objWeave.getIntWarp()+"\n");
            content.append("Units=Centimeters"+"\n");
            content.append("Spacing=0.0185"+"\n");
            content.append("Thickness=0.0213"+"\n");
            content.append("[WEFT]"+"\n");
            content.append("Threads="+objWeave.getIntWeft()+"\n");
            content.append("Units=Centimeters"+"\n");
            content.append("Spacing=0.0185"+"\n");
            content.append("Thickness=0.0213"+"\n");
            
            int temp=1;
            if(objWeave.getBytIsLiftPlan()==0){
                content.append("[TIEUP]\n");
                for(int y=0;y<objWeave.getIntWeaveTreadles();y++){
                    int count=1;
                    int w=0;
                    for(int x=objWeave.getIntWeaveShaft()-1,z=0;x>=0;x--,z++){
                        if(objWeave.getTieupMatrix()[x][y]==1){
                            if(count!=1) {
                                content.append(","+(z+1));
                                w=1;
                            } else {
                                content.append((temp)+"="+(z+1));    
                                temp++;
                                count++;
                                w=1;
                            }
                        } 
                    }
                    if(w==1)
                        content.append("\n");
                }
                content.append("[TREADLING]\n");
                temp=1;
                for(int y=objWeave.getIntWeft()-1;y>=0;y--){
                    int count=1;
                    int w=0;
                    for(int x=objWeave.getIntWeaveTreadles()-1,m=0;x>=0;x--,m++){
                        if(objWeave.getTreadlesMatrix()[y][m]==1){
                            if(count!=1) {
                                content.append(","+(m+1)+"\n");
                                w=0;
                            } else {
                                content.append((temp)+"="+(m+1));    
                                temp++;
                                count++;
                                w=1;
                                count++;
                            }
                        } 
                    }
                    if(w==1)
                        content.append("\n");
                }
            }else{
                content.append("[LIFTPLAN]\n");
                temp=1;
                for(int y=objWeave.getIntWeft()-1;y>=0;y--){
                    int count=1;
                    int w=0;
                    for(int x=objWeave.getIntWeaveShaft()-1,m=0;x>=0;x--,m++){
                        if(objWeave.getPegMatrix()[y][m]==1){
                            if(count!=1) {
                                content.append(","+(m+1)+"\n");
                                w=0;
                            } else {
                                content.append((temp)+"="+(m+1));    
                                temp++;
                                count++;
                                w=1;
                                count++;
                            }
                        } 
                    }
                    if(w==1)
                        content.append("\n");
                }
            }
            content.append("[THREADING]");
            content.append("\n");
            temp=0;
            for(int y=0;y<objWeave.getIntWarp();y++){
                for(int x=0;x<objWeave.getIntWeaveShaft();x++){
                    if(objWeave.getShaftMatrix()[x][y]==1){
                        content.append((temp+1)+"="+((objWeave.getIntWeaveShaft()-1)-x+1)+"\n");
                        temp++;
                    }
                } 
            }
            content.append("[WARP COLORS]");
            content.append("\n");
            for(int y=0;y<objWeave.getIntWarp();y++)
                if(lstEntry.size()>0){
                    content.append((y+1)+"="+(lstEntry.indexOf(objWeave.getWarpYarn()[y].getStrYarnColor())+1)+"\n");
                }else{
                    content.append((y+1)+"=2\n");
                }
            content.append("[WEFT COLORS]");
            content.append("\n");
            for(int x=0;x<objWeave.getIntWeft();x++)
                if(lstEntry.size()>0){
                    content.append((x+1)+"="+(lstEntry.indexOf(objWeave.getWeftYarn()[x].getStrYarnColor())+1)+"\n");
                }else{
                    content.append((x+1)+"=1\n");
                }
            //System.out.println("Live Data Save:\n"+content.toString());
            objWeave.setStrWeaveFile(content.toString());
        } catch (Exception ex) {
            Logger.getLogger(WeaveAction.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",WeaveAction.class.getName(),"inject content function"+ex.toString(),ex);
        }
    }
   
    public void extractWeaveContent(Weave objWeave) {
        try { 
            String content=objWeave.getStrWeaveFile();
            //System.err.println("Hi Amit! I am alive here."+content);
                
            //reading content
            String weaving_content=null;
            String warp_content=null;
            String weft_content=null;
            String color_content=null;
            
            int intWeft = 0, intWarp = 0, intShaft = 0, intTreadles = 0, intColors=0;
            byte [][]shaftMatrix;
            byte [][]treadlesMatrix;
            byte [][]tieupMatrix;
            byte [][]pegMatrix;
            byte [][]designMatrix;
            //byte [][]liftMatrix;
            String [] colorMatrix;
            Yarn [] warpYarn;
            Yarn [] weftYarn;
            
            StringTokenizer section;
            StringTokenizer subSection;  
            
            section=new StringTokenizer(content,"[");
            while(section.hasMoreTokens()) {
                String sectionTemp=section.nextToken();
                if(sectionTemp.contains("WEAVING]"))
                    weaving_content=sectionTemp.replace("WEAVING]", "");
                if(sectionTemp.contains("WARP]"))
                    warp_content=sectionTemp.replace("WARP]", "");
                if(sectionTemp.contains("WEFT]"))
                    weft_content=sectionTemp.replace("WEFT]", "");
                if(sectionTemp.contains("COLOR PALETTE]"))
                    color_content=sectionTemp.replace("COLOR PALETTE]", "");
                    
            }
            intShaft=Integer.parseInt(weaving_content.substring(weaving_content.indexOf("Shafts=")+7,weaving_content.indexOf("Treadles")).trim());
            intTreadles=Integer.parseInt(weaving_content.substring(weaving_content.indexOf("Treadles=")+9,weaving_content.indexOf("Rising")).trim());
            if(color_content!=null)
                intColors=Integer.parseInt(color_content.substring(color_content.indexOf("Entries=")+8,color_content.indexOf("Form")).trim());
            else
                intColors =2;
            if(warp_content.contains("Units"))
                intWarp=Integer.parseInt(warp_content.substring(warp_content.indexOf("Threads=")+8,warp_content.indexOf("Units")).trim());
            else if(warp_content.contains("Thickness"))
                intWarp=Integer.parseInt(warp_content.substring(warp_content.indexOf("Threads=")+8,warp_content.indexOf("Thickness")).trim());
            else if(warp_content.contains("Spacing"))
                intWarp=Integer.parseInt(warp_content.substring(warp_content.indexOf("Threads=")+8,warp_content.indexOf("Spacing")).trim());
            else if(warp_content.contains("Color"))
                intWarp=Integer.parseInt(warp_content.substring(warp_content.indexOf("Threads=")+8,warp_content.indexOf("Color")).trim());
            else
                intWarp=Integer.parseInt(warp_content.substring(warp_content.indexOf("Threads=")+8).trim());
            if(weft_content.contains("Units"))
                intWeft= Integer.parseInt(weft_content.substring(weft_content.indexOf("Threads=")+8, weft_content.indexOf("Units")).trim());
            else if(weft_content.contains("Thickness"))
                intWarp=Integer.parseInt(weft_content.substring(weft_content.indexOf("Threads=")+8,weft_content.indexOf("Thickness")).trim());
            else if(weft_content.contains("Spacing"))
                intWarp=Integer.parseInt(weft_content.substring(weft_content.indexOf("Threads=")+8,weft_content.indexOf("Spacing")).trim());
            else if(weft_content.contains("Color"))
                intWeft= Integer.parseInt(weft_content.substring(weft_content.indexOf("Threads=")+8, weft_content.indexOf("Color")).trim());
            else
                intWeft=Integer.parseInt(weft_content.substring(weft_content.indexOf("Threads=")+8).trim());
              
            if(content.indexOf("CONSTRAINT")>0){
                objWeave.setBytIsLiftPlan((byte)0);
            }else{
                if(content.indexOf("LIFTPLAN")>0){
                    objWeave.setBytIsLiftPlan((byte)1);
                } else{
                    objWeave.setBytIsLiftPlan((byte)0);
                }
            }
            
            designMatrix =new byte[intWeft][intWarp];
            shaftMatrix =new byte[intShaft][intWarp];
            treadlesMatrix =new byte[intWeft][intTreadles];
            tieupMatrix =new byte[intShaft][intTreadles];
            pegMatrix =new byte[intWeft][intShaft];
            warpYarn=new Yarn[intWarp];
            weftYarn=new Yarn[intWeft];
            colorMatrix=new String[intColors];
            //liftMatrix =new byte[intShaft][intTreadles];
            
            objWeave.setIntWeaveShaft(intShaft);
            objWeave.setIntWeaveTreadles(intTreadles);
            objWeave.setIntWarp(intWarp);
            objWeave.setIntWeft(intWeft);
            
            objWeave.setShaftMatrix(shaftMatrix);
            objWeave.setTreadlesMatrix(treadlesMatrix);
            objWeave.setTieupMatrix(tieupMatrix);
            objWeave.setPegMatrix(pegMatrix);
            objWeave.setDesignMatrix(designMatrix);
            objWeave.setWarpYarn(warpYarn);
            objWeave.setWeftYarn(weftYarn);
            section=new StringTokenizer(content,"[");
            boolean warpcount=false, weftcount=false, colorcount=false;
            while (section.hasMoreTokens()) {
                String sectionTemp = section.nextToken();     
                
                if(sectionTemp.startsWith("COLOR TABLE]")){
                    colorcount=true;
                    sectionTemp = sectionTemp.replace("COLOR TABLE]", "");
                    subSection = new StringTokenizer(sectionTemp, "=");
                    int length =subSection.countTokens();
                    int red=0,green=0,blue=0;
                    int i=1;
                    String token=subSection.nextToken();
                    while (subSection.hasMoreTokens() && i<=intColors) {
                        token = subSection.nextToken();
                        token = token.substring(0, token.length()-Integer.toString(i).length());
                        if(token.indexOf(",")>0){
                            StringTokenizer subtokenizer = new StringTokenizer(token.trim(), ",");
                            //while (subtokenizer.hasMoreTokens()) {
                            red = Integer.parseInt(subtokenizer.nextToken());   
                            green = Integer.parseInt(subtokenizer.nextToken());   
                            blue = Integer.parseInt(subtokenizer.nextToken());   
                            colorMatrix[i-1]=String.format( "#%02X%02X%02X",red,green,blue);
                            //System.out.println(red+":"+green+":"+blue+"="+colorMatrix[i-1]);
                        }
                        i++;                
                    }
                }
                if(!colorcount) {
                    colorMatrix[0]="#000000";
                    colorMatrix[1]="#ffffff";
                }
                
                if(sectionTemp.startsWith("WARP COLORS]")){
                    warpcount =  true;
                    sectionTemp = sectionTemp.replace("WARP COLORS]", "");
                    subSection = new StringTokenizer(sectionTemp, "=");
                    int length =subSection.countTokens();
                    int i=1;
                    String token=null; 
                    ArrayList<String> lstEntry = new ArrayList();
                    while (subSection.hasMoreTokens() && i<length) {
                        token = subSection.nextToken();
                        if(i<=intWarp){
                            token = token.substring(0, token.length()-Integer.toString(i).length()).trim();
                            if(i>1){            
                                //add the first color on array
                                if(lstEntry.size()==0){
                                    lstEntry.add(colorMatrix[Integer.parseInt(token)-1]);
                                    //65 - 96 This gives the character 'A'-'Z'
                                }
                                //check for redudancy
                                else {                
                                    if(!lstEntry.contains(colorMatrix[Integer.parseInt(token)-1]))
                                        lstEntry.add(colorMatrix[Integer.parseInt(token)-1]);
                                }
                                objWeave.getObjConfiguration().getColourPalette()[lstEntry.indexOf(colorMatrix[Integer.parseInt(token)-1])]=colorMatrix[Integer.parseInt(token)-1].substring(1, colorMatrix[Integer.parseInt(token)-1].length());
                                warpYarn[i-2]=new Yarn(null, "Warp", objWeave.getObjConfiguration().getStrWarpName(), colorMatrix[Integer.parseInt(token)-1], objWeave.getObjConfiguration().getIntWarpRepeat(), Character.toString((char)(65+lstEntry.indexOf(colorMatrix[Integer.parseInt(token)-1]))), objWeave.getObjConfiguration().getIntWarpCount(), objWeave.getObjConfiguration().getStrWarpUnit(), objWeave.getObjConfiguration().getIntWarpPly(), objWeave.getObjConfiguration().getIntWarpFactor(), objWeave.getObjConfiguration().getDblWarpDiameter(), objWeave.getObjConfiguration().getIntWarpTwist(), objWeave.getObjConfiguration().getStrWarpSence(), objWeave.getObjConfiguration().getIntWarpHairness(), objWeave.getObjConfiguration().getIntWarpDistribution(), objWeave.getObjConfiguration().getDblWarpPrice(), objWeave.getObjConfiguration().getObjUser().getUserAccess("YARN_LIBRARY"),objWeave.getObjConfiguration().getObjUser().getStrUserID(),null);
                            }
                        }
                        i++;                
                    }
                    token=subSection.nextToken().trim();
                    if(!lstEntry.contains(colorMatrix[Integer.parseInt(token)-1]))
                        lstEntry.add(colorMatrix[Integer.parseInt(token)-1]);
                    objWeave.getObjConfiguration().getColourPalette()[lstEntry.indexOf(colorMatrix[Integer.parseInt(token)-1])]=colorMatrix[Integer.parseInt(token)-1].substring(1, colorMatrix[Integer.parseInt(token)-1].length());
                    warpYarn[intWarp-1]=new Yarn(null, "Warp", objWeave.getObjConfiguration().getStrWarpName(), colorMatrix[Integer.parseInt(token)-1], objWeave.getObjConfiguration().getIntWarpRepeat(), Character.toString((char)(65+lstEntry.indexOf(colorMatrix[Integer.parseInt(token)-1]))), objWeave.getObjConfiguration().getIntWarpCount(), objWeave.getObjConfiguration().getStrWarpUnit(), objWeave.getObjConfiguration().getIntWarpPly(), objWeave.getObjConfiguration().getIntWarpFactor(), objWeave.getObjConfiguration().getDblWarpDiameter(), objWeave.getObjConfiguration().getIntWarpTwist(), objWeave.getObjConfiguration().getStrWarpSence(), objWeave.getObjConfiguration().getIntWarpHairness(), objWeave.getObjConfiguration().getIntWarpDistribution(), objWeave.getObjConfiguration().getDblWarpPrice(), objWeave.getObjConfiguration().getObjUser().getUserAccess("YARN_LIBRARY"),objWeave.getObjConfiguration().getObjUser().getStrUserID(),null);
                    // rearrenging shaft matrix
                    objWeave.setWarpYarn(warpYarn);
                }
                if(!warpcount) {
                    for(int i = 0; i<intWarp; i++)
                        warpYarn[i]=new Yarn(null, "Warp", objWeave.getObjConfiguration().getStrWarpName(), colorMatrix[0], objWeave.getObjConfiguration().getIntWarpRepeat(), "A", objWeave.getObjConfiguration().getIntWarpCount(), objWeave.getObjConfiguration().getStrWarpUnit(), objWeave.getObjConfiguration().getIntWarpPly(), objWeave.getObjConfiguration().getIntWarpFactor(), objWeave.getObjConfiguration().getDblWarpDiameter(), objWeave.getObjConfiguration().getIntWarpTwist(), objWeave.getObjConfiguration().getStrWarpSence(), objWeave.getObjConfiguration().getIntWarpHairness(), objWeave.getObjConfiguration().getIntWarpDistribution(), objWeave.getObjConfiguration().getDblWarpPrice(), objWeave.getObjConfiguration().getObjUser().getUserAccess("YARN_LIBRARY"),objWeave.getObjConfiguration().getObjUser().getStrUserID(),null);
                    objWeave.getObjConfiguration().getColourPalette()[0]=colorMatrix[0].substring(1, colorMatrix[0].length());
                }
                
                if(sectionTemp.startsWith("WEFT COLORS]")){
                    weftcount = true;
                    sectionTemp = sectionTemp.replace("WEFT COLORS]", "");
                    subSection = new StringTokenizer(sectionTemp, "=");
                    int length =subSection.countTokens();
                    int i=1;
                    String token=null; 
                    
                    ArrayList<String> lstEntry = new ArrayList();
                    while (subSection.hasMoreTokens() && i<length) {
                        token = subSection.nextToken();
                        if(i<=intWeft){
                            token = token.substring(0, token.length()-Integer.toString(i).length()).trim();
                            if(i>1){
                                //add the first color on array
                                if(lstEntry.size()==0){
                                    lstEntry.add(colorMatrix[Integer.parseInt(token)-1]);
                                    //97 - 113 This gives the character 'a'-'z'
                                }
                                //check for redudancy
                                else {                
                                    if(!lstEntry.contains(colorMatrix[Integer.parseInt(token)-1]))
                                        lstEntry.add(colorMatrix[Integer.parseInt(token)-1]);
                                }
                                objWeave.getObjConfiguration().getColourPalette()[26+lstEntry.indexOf(colorMatrix[Integer.parseInt(token)-1])]=colorMatrix[Integer.parseInt(token)-1].substring(1, colorMatrix[Integer.parseInt(token)-1].length());
                                weftYarn[i-2]=new Yarn(null, "Weft", objWeave.getObjConfiguration().getStrWeftName(), colorMatrix[Integer.parseInt(token)-1], objWeave.getObjConfiguration().getIntWeftRepeat(), Character.toString((char)(97+lstEntry.indexOf(colorMatrix[Integer.parseInt(token)-1]))), objWeave.getObjConfiguration().getIntWeftCount(), objWeave.getObjConfiguration().getStrWeftUnit(), objWeave.getObjConfiguration().getIntWeftPly(), objWeave.getObjConfiguration().getIntWeftFactor(), objWeave.getObjConfiguration().getDblWeftDiameter(), objWeave.getObjConfiguration().getIntWeftTwist(), objWeave.getObjConfiguration().getStrWeftSence(), objWeave.getObjConfiguration().getIntWeftHairness(), objWeave.getObjConfiguration().getIntWeftDistribution(), objWeave.getObjConfiguration().getDblWeftPrice(), objWeave.getObjConfiguration().getObjUser().getUserAccess("YARN_LIBRARY"),objWeave.getObjConfiguration().getObjUser().getStrUserID(),null);
                            }
                        }
                        i++;                
                    }
                    token=subSection.nextToken().trim();
                    if(!lstEntry.contains(colorMatrix[Integer.parseInt(token)-1]))
                        lstEntry.add(colorMatrix[Integer.parseInt(token)-1]);
                    objWeave.getObjConfiguration().getColourPalette()[26+lstEntry.indexOf(colorMatrix[Integer.parseInt(token)-1])]=colorMatrix[Integer.parseInt(token)-1].substring(1, colorMatrix[Integer.parseInt(token)-1].length());
                    weftYarn[intWeft-1]=new Yarn(null, "Weft", objWeave.getObjConfiguration().getStrWeftName(), colorMatrix[Integer.parseInt(token)-1], objWeave.getObjConfiguration().getIntWeftRepeat(), Character.toString((char)(97+lstEntry.indexOf(colorMatrix[Integer.parseInt(token)-1]))), objWeave.getObjConfiguration().getIntWeftCount(), objWeave.getObjConfiguration().getStrWeftUnit(), objWeave.getObjConfiguration().getIntWeftPly(), objWeave.getObjConfiguration().getIntWeftFactor(), objWeave.getObjConfiguration().getDblWeftDiameter(), objWeave.getObjConfiguration().getIntWeftTwist(), objWeave.getObjConfiguration().getStrWeftSence(), objWeave.getObjConfiguration().getIntWeftHairness(), objWeave.getObjConfiguration().getIntWeftDistribution(), objWeave.getObjConfiguration().getDblWeftPrice(), objWeave.getObjConfiguration().getObjUser().getUserAccess("YARN_LIBRARY"),objWeave.getObjConfiguration().getObjUser().getStrUserID(),null);
                    // rearrenging shaft matrix
                    objWeave.setWeftYarn(weftYarn);
                } 
                if(!weftcount){
                    for(int i = 0; i<intWeft; i++)
                        weftYarn[i]=new Yarn(null, "Weft", objWeave.getObjConfiguration().getStrWeftName(), colorMatrix[1], objWeave.getObjConfiguration().getIntWeftRepeat(), "a",  objWeave.getObjConfiguration().getIntWeftCount(), objWeave.getObjConfiguration().getStrWeftUnit(), objWeave.getObjConfiguration().getIntWeftPly(), objWeave.getObjConfiguration().getIntWeftFactor(), objWeave.getObjConfiguration().getDblWeftDiameter(), objWeave.getObjConfiguration().getIntWeftTwist(), objWeave.getObjConfiguration().getStrWeftSence(), objWeave.getObjConfiguration().getIntWeftHairness(), objWeave.getObjConfiguration().getIntWeftDistribution(), objWeave.getObjConfiguration().getDblWeftPrice(), objWeave.getObjConfiguration().getObjUser().getUserAccess("YARN_LIBRARY"),objWeave.getObjConfiguration().getObjUser().getStrUserID(),null);
                    objWeave.getObjConfiguration().getColourPalette()[26]=colorMatrix[1].substring(1, colorMatrix[1].length());
                }
                
               // Finding shaft matrix
                if(sectionTemp.startsWith("THREADING]")){
                    sectionTemp = sectionTemp.replace("THREADING]", "");
                    subSection = new StringTokenizer(sectionTemp, "=");
                    int length =subSection.countTokens();
                    
                    byte shaft_temp[][]=new byte[intShaft][intWarp];
                    int i=1;
                    String token=null; 
                    
                    while (subSection.hasMoreTokens() && i<length) {
                        token = subSection.nextToken();
                        if(i<=intWarp){
                            token = token.substring(0, token.length()-Integer.toString(i).length());
                            if(i>1)
                                shaft_temp[Integer.parseInt(token.trim())-1][i-2]=1 ; 
                        }
                        i++;                
                    }
                    token=subSection.nextToken();
                    shaft_temp[Integer.parseInt(token.trim())-1][intWarp-1]=1;
                    // rearrenging shaft matrix
                    for(int k=intShaft-1,x=0;x<intShaft;x++,k--)
                        for(int y=0;y<intWarp;y++)
                            shaftMatrix[x][y]=shaft_temp[k][y];
                    objWeave.setShaftMatrix(shaftMatrix);
                }
                
                // Finding treadling matrix
                if(sectionTemp.contains("TREADLING]")){
                    sectionTemp = sectionTemp.replace("TREADLING]", "");
                    subSection = new StringTokenizer(sectionTemp, "=");
                    int length =subSection.countTokens();
                    
                    byte treadles_temp[][]=new byte[intWeft][intTreadles];
                    int i=1;
                    String token=null; 
                    
                    while (subSection.hasMoreTokens() && i<length){
                        token=subSection.nextToken();                    
                        if(i<=intWeft){
                            token = token.substring(0, token.length()-Integer.toString(i).length());
                            if(i>1)
                                treadles_temp[i-2][Integer.parseInt(token.trim())-1] =1;
                        }
                        i++;                
                    }
                    token=subSection.nextToken();
                    treadles_temp[intWeft-1][Integer.parseInt(token.trim())-1]=1;
                    
                    for(int k=intWeft-1,x=0;x<intWeft;x++,k--)
                        for(int y=0;y<intTreadles;y++)
                            treadlesMatrix[x][y]=treadles_temp[k][y];
                    objWeave.setTreadlesMatrix(treadlesMatrix);
                }
                    
                if(sectionTemp.contains("LIFTPLAN]")){
                    sectionTemp = sectionTemp.replace("LIFTPLAN]", "");
                    subSection = new StringTokenizer(sectionTemp, "=");
                    
                    byte peg_temp[][]=new byte[intWeft][intShaft];
                    int i=1;
                    String token=null;
                    
                    while (subSection.hasMoreTokens()) {
                        token = subSection.nextToken();
                        if(i<=intWeft){
                            token = token.substring(0, token.length()-Integer.toString(i).length());
                        if(i>1)
                            if(token.indexOf(",")>0){
                                StringTokenizer subtokenizer = new StringTokenizer(token.trim(), ",");
                                while (subtokenizer.hasMoreTokens()) {
                                    String subtoken = subtokenizer.nextToken();   
                                    peg_temp[i-2][Integer.parseInt(subtoken.trim())-1] =1;
                                }
                            }else{
                                peg_temp[i-2][Integer.parseInt(token.trim())-1]=1 ; 
                            }
                        }
                        i++;                
                    }
                    if(token.indexOf(",")>0){
                        StringTokenizer subtokenizer = new StringTokenizer(token.trim(), ",");
                        while (subtokenizer.hasMoreTokens()) {
                            String subtoken = subtokenizer.nextToken();   
                            peg_temp[intWeft-1][Integer.parseInt(subtoken.trim())-1]=1;
                        }
                    }else
                        peg_temp[intWeft-1][Integer.parseInt(token.trim())-1]=1;

                    for(int k=intWeft-1,x=0;x<intWeft;x++,k--)
                        for(int y=0;y<intTreadles;y++)
                            pegMatrix[x][y]=peg_temp[k][y];
                    objWeave.setPegMatrix(pegMatrix);
                }
                
                if(sectionTemp.contains("TIEUP]")){
                    sectionTemp = sectionTemp.replace("TIEUP]", "");                    
                    subSection = new StringTokenizer(sectionTemp, "=");

                    byte tieup_temp[][]=new byte[intShaft][intTreadles];
                    int i=1, m=0, j=0;
                    String token=null; 
                    int length =subSection.countTokens();

                    while (subSection.hasMoreTokens() && m<length-1) {
                        token = subSection.nextToken(); 
                        m++;
                        if(i<intShaft+1){
                            token = token.substring(0, token.length()-Integer.toString(i).length());
                            if(i>1)
                                if(token.indexOf(",")>0){
                                    StringTokenizer subtokenizer = new StringTokenizer(token.trim(), ",");
                                    while (subtokenizer.hasMoreTokens()) {
                                        String subtoken = subtokenizer.nextToken();   
                                        tieup_temp[Integer.parseInt(subtoken.trim())-1][i-2] =1;
                                    }
                                    j++;
                                }else{
                                    tieup_temp[Integer.parseInt(token.trim())-1][i-2]=1 ;
                                    j++;
                                }
                        }
                        i++;                
                    }
                    token=subSection.nextToken();
                    if(token.indexOf(",")>0){
                        StringTokenizer subtokenizer = new StringTokenizer(token.trim(), ",");
                        while (subtokenizer.hasMoreTokens()) {
                            String subtoken = subtokenizer.nextToken();   
                            tieup_temp[Integer.parseInt(subtoken.trim())-1][j]=1;
                        }
                    } else
                        tieup_temp[Integer.parseInt(token.trim())-1][j]=1;

                    for(int k=intShaft-1,x=0;x<intShaft;x++,k--)
                        for(int y=0;y<intTreadles;y++)
                            tieupMatrix[x][y]=tieup_temp[k][y];
                    objWeave.setTieupMatrix(tieupMatrix);
                } 
                
                if(objWeave.getBytIsLiftPlan()==1){
                    populateDesign(objWeave);
                    populateTreadles(objWeave);
                    populateTieUp(objWeave);
                } else{
                    populateDesign(objWeave);
                    populatePegPlan(objWeave);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(WeaveAction.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",WeaveAction.class.getName(),"extract weave content function"+ex.toString(),ex);            
        }
    }  
    
   public void populateDesign(Weave objWeave) {
       //populate Design from Shaft Peg
       if(objWeave.getBytIsLiftPlan()==1){
            for(int i=0;i<objWeave.getIntWeaveShaft();i++){
                for(int j=0;j<objWeave.getIntWarp();j++) {
                    if(objWeave.getShaftMatrix()[i][j]==1) {
                        for(int k=0;k<objWeave.getIntWeft();k++){
                            objWeave.getDesignMatrix()[k][j]=objWeave.getPegMatrix()[k][i];
                        }
                    }
                }
            }
        } else{ //populate Design from Shaft Tradeles tie-up
            for(int i=0;i<objWeave.getIntWeaveShaft();i++){
                for(int j=0;j<objWeave.getIntWarp();j++) {
                    if(objWeave.getShaftMatrix()[i][j]==1) {
                        for(int k=0;k<objWeave.getIntWeaveTreadles();k++) {
                            if(objWeave.getTieupMatrix()[i][k]==1) {
                                for(int l=0;l<objWeave.getIntWeft();l++){
                                    if(objWeave.getTreadlesMatrix()[l][k]==1) {
                                        objWeave.getDesignMatrix()[l][j]=1;
                                    }
                                }
                            }            
                        }
                    }
                }
            }
        }
    } 
   
   public void populateShaft(Weave objWeave){
        String[] shaftRepeat=new String[objWeave.getIntWarp()];
        ArrayList<String> shafts = new ArrayList<String>();
        String text = "";
        
        for(int q=0;q<objWeave.getIntWarp();q++){
            text = "";
            for(int p=0;p<objWeave.getIntWeft();p++){
                if(objWeave.getDesignMatrix()[p][q]==1)
                    text+=p+",";
            }
            shaftRepeat[q]=text;
            if(text!=""){
                if(shafts.size()==0)                
                    shafts.add(text);
                else {                
                    if(!(shafts.contains(text)))
                        shafts.add(text);
                }    
            }
        }        
        //System.out.println("Total number of shafts= "+shafts.size());
        
        int intWeaveShaft=shafts.size()>0?shafts.size():objWeave.getIntWeaveShaft();
        byte[][] shaftMatrix=new byte[intWeaveShaft][objWeave.getIntWarp()];
        
        for(int i=0; i<shaftRepeat.length;i++){
            if(shaftRepeat[i]!="")
                shaftMatrix[intWeaveShaft-shafts.indexOf(shaftRepeat[i])-1][i]=1;
        }
        shaftRepeat=null;
        shafts = null;
        objWeave.setShaftMatrix(shaftMatrix);
        objWeave.setIntWeaveShaft(intWeaveShaft);
    }

   public void populateTreadles(Weave objWeave){
        String[] treadleRepeat=new String[objWeave.getIntWeft()];
        ArrayList<String> treadles = new ArrayList<String>();
        String text = "";
        
        for(int p=objWeave.getIntWeft()-1;p>=0;p--){
            text = "";
            for(int q=0;q<objWeave.getIntWarp();q++){
                if(objWeave.getDesignMatrix()[p][q]==1)
                    text+=q+",";
            }
            treadleRepeat[p]=text;            
            if(text!=""){
                if(treadles.size()==0)                
                    treadles.add(text);
                else {                
                    if(!(treadles.contains(text)))
                        treadles.add(text);
                }    
            }
        }        
        //System.out.println("Total number of treadles= "+treadles.size());
        
        int intWeaveTreadle=treadles.size()>0?treadles.size():objWeave.getIntWeaveTreadles();
        byte[][] treadlesMatrix=new byte[objWeave.getIntWeft()][intWeaveTreadle];
        
        for(int i=0; i<treadleRepeat.length;i++){
            if(treadleRepeat[i]!="")
                treadlesMatrix[i][treadles.indexOf(treadleRepeat[i])]=1;
        }
        treadleRepeat=null;
        treadles = null;
        objWeave.setTreadlesMatrix(treadlesMatrix);
        objWeave.setIntWeaveTreadles(intWeaveTreadle);
    }
   
   public void populatePegPlan(Weave objWeave){
        ArrayList<String> pegs = new ArrayList<String>();
        String text = "";
        
        int intWeaveShaft = 0;
        for(int q=0;q<objWeave.getIntWarp();q++){
            text = "";
            for(int p=0;p<objWeave.getIntWeft();p++){
                if(objWeave.getDesignMatrix()[p][q]==1)
                    text+=p+",";
            }
            if(pegs.size()==0){
                if(text!="")
                    intWeaveShaft++;
            } else {
                if(text!="" && !(pegs.contains(text))){
                    intWeaveShaft++;
                } else {
                    text = "";
                }
            }
            pegs.add(text);
        }
        //System.out.println("Total number of shafts= "+shafts.size());
        
        //int intWeavePaddle=paddles.size()>0?paddles.size():objWeave.getIntWeavePaddle();
        intWeaveShaft=intWeaveShaft>0?intWeaveShaft:objWeave.getIntWeaveShaft();
        byte[][] pegMatrix=new byte[objWeave.getIntWeft()][intWeaveShaft];
        
        for(int i=0, k=0; i<pegs.size();i++){
            if(!pegs.get(i).equalsIgnoreCase("")){
                for(int j=0; j<objWeave.getIntWeft(); j++)
                    pegMatrix[j][k]=objWeave.getDesignMatrix()[j][i];
                k++;
            }
        }
        
        pegs = null;
        objWeave.setPegMatrix(pegMatrix);
        objWeave.setIntWeaveShaft(intWeaveShaft);
    }
   
   public void populateTieUpNew(Weave objWeave){
        ArrayList<String> pegs = new ArrayList<String>();
        String text = "";
        
        int intWeaveShaft = 0;
        for(int q=0;q<objWeave.getIntWarp();q++){
            text = "";
            for(int p=0;p<objWeave.getIntWeft();p++){
                if(objWeave.getDesignMatrix()[p][q]==1)
                    text+=p+",";
            }
            if(pegs.size()==0){
                if(text!="")
                    intWeaveShaft++;
            } else {
                if(text!="" && !(pegs.contains(text))){
                    intWeaveShaft++;
                } else {
                    text = "";
                }
            }
            pegs.add(text);
        }
        //System.out.println("Total number of shafts= "+shafts.size());
        
        //int intWeavePaddle=paddles.size()>0?paddles.size():objWeave.getIntWeavePaddle();
        intWeaveShaft=intWeaveShaft>0?intWeaveShaft:objWeave.getIntWeaveShaft();
        
        byte[][] pegMatrix=new byte[objWeave.getIntWeft()][intWeaveShaft];
        
        for(int i=0, k=0; i<pegs.size();i++){
            if(!pegs.get(i).equalsIgnoreCase("")){
                for(int j=0; j<objWeave.getIntWeft(); j++)
                    pegMatrix[j][k]=objWeave.getDesignMatrix()[j][i];
                k++;
            }
        }
        
        pegs = new ArrayList<String>();
        text = "";
        
        int intWeaveTradeles = 0;
        for(int q=0;q<objWeave.getIntWeft();q++){
            text = "";
            for(int p=0;p<objWeave.getIntWeaveShaft();p++){
                if(pegMatrix[p][q]==1)
                    text+=p+",";
            }
            if(pegs.size()==0){
                if(text!="")
                    intWeaveTradeles++;
            } else {
                if(text!="" && !(pegs.contains(text))){
                    intWeaveTradeles++;
                } else {
                    text = "";
                }
            }
            pegs.add(text);
        }
        //System.out.println("Total number of shafts= "+shafts.size());
        
        //int intWeavePaddle=paddles.size()>0?paddles.size():objWeave.getIntWeavePaddle();
        intWeaveTradeles=intWeaveTradeles>0?intWeaveTradeles:objWeave.getIntWeaveTreadles();
        
        byte[][] tieupMatrix=new byte[intWeaveTradeles][objWeave.getIntWeaveShaft()];
        
        for(int i=0, k=0; i<pegs.size();i++){
            if(!pegs.get(i).equalsIgnoreCase("")){
                for(int j=0; j<objWeave.getIntWeaveShaft(); j++)
                    tieupMatrix[j][k]=pegMatrix[j][i];
                k++;
            }
        }
        
        pegs = null;
        objWeave.setTieupMatrix(tieupMatrix);
        objWeave.setIntWeaveShaft(intWeaveShaft);
        objWeave.setIntWeaveTreadles(intWeaveTradeles);
    }
   
   public void populateTieUp(Weave objWeave){
        try{
        byte[][] tieupMatrix = new byte[objWeave.getIntWeaveShaft()][objWeave.getIntWeaveTreadles()];
        for(int i=0;i<objWeave.getIntWeaveShaft();i++){
            for(int j=0;j<objWeave.getIntWeaveTreadles();j++){
                tieupMatrix[i][j]=0;
            }
        }
        
        for(int i=0;i<objWeave.getIntWeft();i++){
            for(int j=0;j<objWeave.getIntWarp();j++){
                if(objWeave.getDesignMatrix()[i][j]==1){
                    int count_row[]=new int[objWeave.getIntWeaveTreadles()];
                    int count_column[]=new int[objWeave.getIntWeaveShaft()];
                    int row=0,col=0;
                    for(int k=0;k<objWeave.getIntWeaveTreadles();k++){
                        if(objWeave.getTreadlesMatrix()[i][k]==1)
                            count_row[row++]=k;
                    } 
                    for(int k=0;k<objWeave.getIntWeaveShaft();k++){
                        if(objWeave.getShaftMatrix()[k][j]==1)
                            count_column[col++]=k;
                    }
                    for(int p=0;p<row;p++) {
                        for(int q=0;q<col;q++)
                            tieupMatrix[count_column[q]][count_row[p]]=1;
                    }
                }
            }   
        }
        objWeave.setTieupMatrix(tieupMatrix);
     } catch (Exception ex) {
            Logger.getLogger(WeaveAction.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }

    /*
    public void populatePegPlanOld(Weave objWeave){
        for(int i=0;i<objWeave.getIntWeft();i++){
            for(int j=0;j<objWeave.getIntWeaveTreadles();j++){
                objWeave.getPegMatrix()[i][j]=0;
            }
        }    
        for(int i=0;i<objWeave.getIntWeft();i++){
            for(int j=0;j<objWeave.getIntWarp();j++){
                if(objWeave.getDesignMatrix()[i][j]==1){
                    for(int i1=0;i1<objWeave.getIntWeaveShaft();i1++){
                        for(int j1=0;j1<objWeave.getIntWeaveTreadles();j1++){
                            if(objWeave.getLiftMatrix()[i1][j1]==1){
                                if(objWeave.getShaftMatrix()[i1][j]==1){
                                    objWeave.getPegMatrix()[i][j1]=1;
                                }   
                            }
                        }
                    }
                } 
            }
        }        
    }
      */
    public void populateShaftDesign(Weave objWeave, int x, int y){
        for(int z=0;z<objWeave.getIntWeaveTreadles();z++) {
            if(objWeave.getTieupMatrix()[x][z]==1) {
                for(int t=0;t<objWeave.getIntWeft();t++){  
                    if(objWeave.getTreadlesMatrix()[t][z]==1) {
                        objWeave.getDesignMatrix()[t][y]=objWeave.getShaftMatrix()[x][y];
                    }
                }
            }            
        }
    }
    
    public void populateTieUpDesign(Weave objWeave, int x, int y){
        for(int z=0;z<objWeave.getIntWarp();z++) {
            if(objWeave.getShaftMatrix()[x][z]==1) {
                for(int s=0;s<objWeave.getIntWeft();s++){  
                    if(objWeave.getTreadlesMatrix()[s][y]==1) {
                        objWeave.getDesignMatrix()[s][z]=objWeave.getTieupMatrix()[x][y];
                    }
                }
            }
        }
    }
    
    public void populateTradelesDesign(Weave objWeave, int x, int y){
        for(int z=0;z<objWeave.getIntWeaveShaft();z++) {
            if(objWeave.getTieupMatrix()[z][y]==1) {
                for(int s=0;s<objWeave.getIntWarp();s++){  
                    if(objWeave.getShaftMatrix()[z][s]==1) {
                        objWeave.getDesignMatrix()[x][s]=objWeave.getTreadlesMatrix()[x][y];
                    }
                }
            }            
        }
    }
    
    public void populatePegDesign(Weave objWeave, int x, int y){
        for(int z=0;z<objWeave.getIntWarp();z++){  
            if(objWeave.getShaftMatrix()[objWeave.getIntWeaveShaft()-y-1][z]==1) {
                objWeave.getDesignMatrix()[x][z]=objWeave.getPegMatrix()[x][y];
            }
        }
    }  
    
    public byte[][] populateDenting(Weave objWeave){
        int maxDent=0;
        for(int x=0;x<2;x++){  
            for(int y=0, dent=0;y<objWeave.getIntWarp();y++){  
                if(objWeave.getDentMatrix()[x][y]==1) {
                    dent++;
                }else {
                    if(maxDent<dent){
                        maxDent = dent;
                        dent=0;
                    }
                }
            }
        }
        maxDent = maxDent - 1;
        //System.out.println("Max Dent"+maxDent);
        int avgDent = 0;
        for(int x=0;x<2;x++){  
            
        }
        // for 1
        for(int y=0, dent=0;y<objWeave.getIntWarp();y++){  
            if(objWeave.getDentMatrix()[0][y]==1) {
                dent++;
                avgDent++;
            }else {
                if(maxDent>=dent){
                    for(int z=0;z<maxDent-dent;z++){
                        avgDent++;
                    }
                    dent=0;
                }
            }
        }
        // for 0
        for(int y=0, dent=0;y<objWeave.getIntWarp();y++){  
            if(objWeave.getDentMatrix()[0][y]==0) {
                dent++;
                avgDent++;
            }else {
                if(maxDent>=dent){
                    for(int z=0;z<maxDent-dent;z++){
                        avgDent++;
                    }
                    dent=0;
                }
            }
        }
        //System.out.println("Total Dent"+avgDent);
        byte[][] design_temp = new byte[objWeave.getIntWeft()][avgDent];
        avgDent=0;
        for(int y=0, x=0;y<objWeave.getIntWarp();y++){  
            if(objWeave.getDentMatrix()[x][y]==1) {
                for(int z=0;z<maxDent;z++){  
                    for(int a=0;a<objWeave.getIntWeft();a++,avgDent++){  
                        design_temp[a][avgDent]=objWeave.getDesignMatrix()[a][y];
                    }
                }
            } else{
                x=(x==1)?0:1;
            }
        }
        for(int x=0;x<objWeave.getIntWeft();x++){  
            for(int y=0;y<avgDent;y++){  
                System.out.print(" "+design_temp[x][y]);
            }
            //System.out.println("");
        }
        return design_temp;
    }
    
    
    public void PegPlan(Weave objWeave){
        for(int i=0;i<objWeave.getIntWeft();i++){
            for(int j=0;j<objWeave.getIntWeaveTreadles();j++){
                objWeave.getTreadlesMatrix()[i][j]=0;
            }
        }    
        for(int i=0;i<objWeave.getIntWeaveShaft();i++){
            for(int j=0;j<objWeave.getIntWeaveTreadles();j++){
                objWeave.getTieupMatrix()[i][j]=0;
            }
        }    
        for(int i=objWeave.getIntWeaveShaft()-1;i>=0;i--){
            for(int j=objWeave.getIntWeaveTreadles()-1;j>=0;j--){
                if((objWeave.getIntWeaveShaft()-1)-i==j)
                    objWeave.getTieupMatrix()[i][j]=1;
                else
                    objWeave.getTieupMatrix()[i][j]=0;
            }
        }
        for(int i=0;i<objWeave.getIntWeft();i++){
            for(int j=0;j<objWeave.getIntWarp();j++){
                if(objWeave.getDesignMatrix()[i][j]==1){
                    for(int i1=0;i1<objWeave.getIntWeaveShaft();i1++){
                        for(int j1=0;j1<objWeave.getIntWeaveTreadles();j1++){
                            if(objWeave.getTieupMatrix()[i1][j1]==1){
                                if(objWeave.getShaftMatrix()[i1][j]==1){
                                    objWeave.getTreadlesMatrix()[i][j1]=1;
                                }   
                            }
                        }
                    }
                } 
            }
        }        
    }
    
    public void LiftPlan(Weave objWeave){
        for(int i=0;i<objWeave.getIntWeft();i++){
            for(int j=0;j<objWeave.getIntWeaveTreadles();j++){
                objWeave.getTreadlesMatrix()[i][j]=0;
            }
        }    
        for(int i=0;i<objWeave.getIntWeaveShaft();i++){
            for(int j=0;j<objWeave.getIntWeaveTreadles();j++){
                objWeave.getTieupMatrix()[i][j]=0;
            }
        }
        populateTreadles(objWeave);
        populateTieUp(objWeave);        
    }
    
    
    public void populateShaftTreadles(Weave objWeave, int x, int y){
        if(objWeave.getShaftMatrix()[x][y]==1) {
            for(int z=0;z<objWeave.getIntWeaveTreadles();z++) {
                if(objWeave.getTieupMatrix()[x][z]==1) {
                    for(int t=0;t<objWeave.getIntWeft();t++){  
                        if(objWeave.getTreadlesMatrix()[t][z]==1) {
                            objWeave.getDesignMatrix()[t][y]=1;
                        }
                    }
                }            
             }
         }else{
            for(int z=0;z<objWeave.getIntWeaveTreadles();z++) {
                if(objWeave.getTieupMatrix()[x][z]==1) {
                    for(int t=0;t<objWeave.getIntWeft();t++){  
                        if(objWeave.getTreadlesMatrix()[t][z]==1) {
                            objWeave.getDesignMatrix()[t][y]=0;
                        }
                    }
                }            
             }
        }
    }
    
    
    public void getWeaveImage(Weave objWeave) {   
        try {
            int length = objWeave.getIntWarp();
            int height = objWeave.getIntWeft();
            
            BufferedImage bufferedImage = new BufferedImage(length, height,BufferedImage.TYPE_INT_RGB);        
            for(int x = 0; x < height; x++) {
                for(int y = 0; y < length; y++) {
                    if(objWeave.getDesignMatrix()[x][y]==0)
                        bufferedImage.setRGB(y, x, java.awt.Color.WHITE.getRGB());
                    else
                        bufferedImage.setRGB(y, x, java.awt.Color.BLACK.getRGB());
                }
            }
            //////////////
            int boxsize = 10;
            int linesize = 3;
            String[] data = {"10","10"};
            //System.out.println(Arrays.toString(data));
            
            BufferedImage bufferedImageesize = new BufferedImage((int)(length*(boxsize+linesize)), (int)(height*(boxsize+linesize)) ,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, (int)(length*(boxsize+linesize)), (int)(height*(boxsize+linesize)), null);
            g.setColor(java.awt.Color.RED);
            BasicStroke bs = new BasicStroke(linesize);
            g.setStroke(bs);
            
            for(int x = 0; x < (int)(length*(boxsize+linesize))+1; x+=(int)((boxsize+linesize))) {
                bs = new BasicStroke(linesize);
                g.setStroke(bs);
                g.drawLine(x, 0,  x, (int)(height*(boxsize+linesize)));
            }
            
            for(int y = 0; y < (int)(height*(boxsize+linesize))+1; y+=(int)((boxsize+linesize))) {
                bs = new BasicStroke(linesize);
                g.setStroke(bs);
                g.drawLine(0, y, (int)(length*(boxsize+linesize)), y);
            }
            /*
            for(int x = 0; x < (int)(width*(boxsize+linesize))+1; x+=(int)((boxsize+linesize))) {
                for(int y = 0; y < (int)(height*(boxsize+linesize))+1; y+=(int)((boxsize+linesize))) {
                    if(y%(int)(Integer.parseInt(data[0])*(boxsize+linesize))==0){
                        bs = new BasicStroke(linesize);
                        g.setStroke(bs);
                    }else{
                        bs = new BasicStroke(linesize);
                        g.setStroke(bs);
                    }
                    g.drawLine(y, 0,  y, (int)(width*(boxsize+linesize)));
                }
                if(x%(int)(Integer.parseInt(data[1])*(boxsize+linesize))==0){
                    bs = new BasicStroke(linesize);
                    g.setStroke(bs);
                }else{
                    bs = new BasicStroke(linesize);
                    g.setStroke(bs);
                }
                g.drawLine(0, x, (int)(height*(boxsize+linesize)), x);
            }*/
            g.dispose();
            bufferedImage = null;
            
            length = 111;
            height = 111;
            bufferedImage = new BufferedImage(length, height ,BufferedImage.TYPE_INT_RGB);
            g = bufferedImage.createGraphics();
            g.drawImage(bufferedImageesize, 0, 0, height, length, null);
            g.dispose();
            bufferedImageesize = null;
            ////////////////////////
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();  
            objWeave.setBytWeaveThumbnil(imageInByte);
            imageInByte = null;
            baos.close();
            bufferedImage = null;            
        } catch (IOException ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),ex.toString(),ex);
        }
    }
    
    public void getSingleWeaveImage(Weave objWeave) {   
        try {
            int height = objWeave.getIntWeaveRepeatX();
            int width = objWeave.getIntWeaveRepeatY();
            BufferedImage bufferedImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);        
            for(int x = 0; x < height; x++) {
                for(int y = 0; y < width; y++) {
                    if(objWeave.getSingleMatrix()[height-1-x][y]==0)
                        bufferedImage.setRGB(y, x, java.awt.Color.WHITE.getRGB());
                    else
                        bufferedImage.setRGB(y, x, java.awt.Color.BLACK.getRGB());
                }
            }
            int boxsize = 10;
            //////////////
            BufferedImage bufferedImageesize = new BufferedImage((int)(width*boxsize), (int)(height*boxsize) ,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, (int)(height*boxsize), (int)(width*boxsize), null);
            g.setColor(java.awt.Color.RED);
            BasicStroke bs = new BasicStroke(2);
            g.setStroke(bs);
            
            String[] data = {"10","10"};
            //System.out.println(Arrays.toString(data));
            
            for(int x = 0; x < (int)(width*boxsize)+1; x+=(int)(boxsize)) {
                for(int y = 0; y < (int)(height*boxsize)+1; y+=(int)(boxsize)) {
                    if(y%(int)(Integer.parseInt(data[0])*boxsize)==0){
                        bs = new BasicStroke(2);
                        g.setStroke(bs);
                    }else{
                        bs = new BasicStroke(2);
                        g.setStroke(bs);
                    }
                    g.drawLine(y, 0,  y, (int)(width*boxsize));
                }
                if(x%(int)(Integer.parseInt(data[1])*boxsize)==0){
                    bs = new BasicStroke(2);
                    g.setStroke(bs);
                }else{
                    bs = new BasicStroke(2);
                    g.setStroke(bs);
                }
                g.drawLine(0, x, (int)(height*boxsize), x);
            }
            g.dispose();
            bufferedImage = null;
            ////////////////////////
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImageesize, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();  
            objWeave.setBytWeaveThumbnil(imageInByte);
            imageInByte = null;
            baos.close();
            bufferedImage = null;            
        } catch (IOException ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),ex.toString(),ex);
        }
    }
    
    public void singleWeaveContent(Weave objWeave) {
        String[] shaftRepeat=new String[objWeave.getIntWarp()];
        ArrayList<String> shafts = new ArrayList<String>();
        String text = "";
        
        for(int q=0;q<objWeave.getIntWarp();q++){
            text = "";
            for(int p=0;p<objWeave.getIntWeft();p++){
                if(objWeave.getDesignMatrix()[p][q]==1)
                    text+=p+",";
            }
            shaftRepeat[q]=text;
            if(shafts.size()==0)                
                shafts.add(text);
            else {                
                if(!(shafts.contains(text)))
                    shafts.add(text);
            }    
        }        
        //System.out.println("Total number of shafts= "+shafts.size());
        
        String[] treadleRepeat=new String[objWeave.getIntWeft()];
        ArrayList<String> treadles = new ArrayList<String>();
        
        for(int p=objWeave.getIntWeft()-1;p>=0;p--){
            text = "";
            for(int q=0;q<objWeave.getIntWarp();q++){
                if(objWeave.getDesignMatrix()[p][q]==1)
                    text+=q+",";
            }
            treadleRepeat[p]=text;
            if(treadles.size()==0)                
                treadles.add(text);
            else {                
                if(!(treadles.contains(text)))
                    treadles.add(text);
            }    
        }        
        //System.out.println("Total number of treadles= "+treadles.size());
        byte[][] singleMatrix = new byte[shafts.size()][treadles.size()];
        for(int i=0;i<shafts.size();i++){
            for(int j=0;j<treadles.size();j++){
                singleMatrix[i][j] =  objWeave.getDesignMatrix()[objWeave.getIntWeft()-1-i][j];
            }
        }
        
        int count = 0;
        int max_weft = 1;
        for (int i = 0; i < singleMatrix[0].length; i++) {
            count=0;
            for(int j = 1; j < singleMatrix.length; j++) {
                if (singleMatrix[j][i] ==1) {
                    count++;
                }  else{
                    if (count > max_weft) {
                        max_weft = count;
                    }
                    count = 0;
                }  
            }
        }
        int max_warp = 1;
        for (int i = 0; i < singleMatrix.length; i++) {
            count=0;
            for(int j = 1; j < singleMatrix[i].length; j++) {
                if (singleMatrix[i][j] ==0) {
                    count++;
                }  else{
                    if (count > max_warp) {
                        max_warp = count;
                    }
                    count = 0;
                }
            }
        }
        objWeave.setIntWeaveFloatY(max_weft);
        objWeave.setIntWeaveFloatX(max_warp);
        objWeave.setIntWeaveRepeatX(shafts.size());
        objWeave.setIntWeaveRepeatY(treadles.size());
        
        objWeave.setSingleMatrix(singleMatrix);
    }
  
    public byte[][] repeatMatrix(byte[][] mainMatrix, int WIDTH, int HEIGHT){
        int height=(int)mainMatrix.length;
        int width=(int)mainMatrix[0].length;
        byte[][] repeatMatrix = new byte[HEIGHT][WIDTH];
        for(int i = 0; i < HEIGHT; i++) {
            for(int j = 0; j < WIDTH; j++) {
                if(i>=height && j<width){
                     repeatMatrix[i][j] = mainMatrix[i%height][j];  
                }else if(i<height && j>=width){
                     repeatMatrix[i][j] = mainMatrix[i][j%width];  
                }else if(i>=height && j>=width){
                     repeatMatrix[i][j] = mainMatrix[i%height][j%width];  
                }else{
                     repeatMatrix[i][j] = mainMatrix[i][j]; 
                }                
            }
        } 
        mainMatrix = null;
        HEIGHT = WIDTH = height = width = 0;
        return repeatMatrix;
    }
    
    public void inversion(Weave objWeave){
        try{
        for(int i=0;i<objWeave.getIntWeft();i++){
            for(int j=0;j<objWeave.getIntWarp();j++){
                if(objWeave.getDesignMatrix()[i][j]==1)
                    objWeave.getDesignMatrix()[i][j]=0;
                else if(objWeave.getDesignMatrix()[i][j]==0)
                    objWeave.getDesignMatrix()[i][j]=1;
            }
        }
        }catch(Exception ex){
            Logger.getLogger(WeaveAction.class.getName()).log(Level.SEVERE, null, ex);
            new Logging("SEVERE",WeaveAction.class.getName(),ex.toString(),ex);
        }
    }
    
    public void clear(Weave objWeave){
        objWeave.setShaftMatrix(new byte[objWeave.getIntWeaveShaft()][objWeave.getIntWarp()]);
        objWeave.setTieupMatrix(new byte[objWeave.getIntWeaveShaft()][objWeave.getIntWeaveTreadles()]);
        objWeave.setTreadlesMatrix(new byte[objWeave.getIntWeft()][objWeave.getIntWeaveTreadles()]);
        objWeave.setDesignMatrix(new byte[objWeave.getIntWeft()][objWeave.getIntWarp()]);
        objWeave.getDesignMatrix()[objWeave.getIntWeft()-1][0]=1;
    }
    
    public void checkEmptyWeave(Weave objWeave){
        boolean isEmpty = true;
        for(int i=0; i<objWeave.getIntWeft(); i++)
            for(int j=0; j<objWeave.getIntWarp(); j++)
                if(objWeave.getDesignMatrix()[i][j]==1)
                    isEmpty = false;
        if(isEmpty)
            objWeave.getDesignMatrix()[objWeave.getIntWeft()-1][0]=1;
    }
 
    
    
    public void moveRight(Weave objWeave){
        try {
            byte[][] design_temp = new byte[objWeave.getIntWeft()][objWeave.getIntWarp()];
            for(int i=0;i<objWeave.getIntWeft();i++){
                for(int j=0;j<objWeave.getIntWarp();j++){
                    design_temp[i][(j+1)%objWeave.getIntWarp()]=objWeave.getDesignMatrix()[i][j];
                }
            }
            objWeave.setDesignMatrix(design_temp);
            design_temp=null;
            System.gc();
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"move right function"+ex.toString(),ex);
        }
    }
    
    public void moveLeft(Weave objWeave){
        try {
            byte[][] design_temp = new byte[objWeave.getIntWeft()][objWeave.getIntWarp()];
            for(int i=0;i<objWeave.getIntWeft();i++){
                for(int j=0;j<objWeave.getIntWarp();j++){
                    design_temp[i][(objWeave.getIntWarp()+j-1)%objWeave.getIntWarp()]=objWeave.getDesignMatrix()[i][j];
                }
            }
            objWeave.setDesignMatrix(design_temp);
            design_temp=null;
            System.gc();
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"move left function"+ex.toString(),ex);
        }
    }
    
    public void moveUp(Weave objWeave){
        try {
            byte[][] design_temp = new byte[objWeave.getIntWeft()][objWeave.getIntWarp()];
            for(int i=0;i<objWeave.getIntWeft();i++){
                for(int j=0;j<objWeave.getIntWarp();j++){
                    design_temp[(objWeave.getIntWeft()+i-1)%objWeave.getIntWeft()][j]=objWeave.getDesignMatrix()[i][j];
                }
            }
            objWeave.setDesignMatrix(design_temp);
            design_temp=null;
            System.gc();
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"move up function"+ex.toString(),ex);
        }
    }
    
    public void moveDown(Weave objWeave){
        try {
            byte[][] design_temp = new byte[objWeave.getIntWeft()][objWeave.getIntWarp()];
            for(int i=0;i<objWeave.getIntWeft();i++){
                for(int j=0;j<objWeave.getIntWarp();j++){
                    design_temp[(i+1)%objWeave.getIntWeft()][j]=objWeave.getDesignMatrix()[i][j];
                }
            }
            objWeave.setDesignMatrix(design_temp);
            design_temp=null;
            System.gc();
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"move down function"+ex.toString(),ex);
        }
    }
    
    public void moveRight8(Weave objWeave){
        try {
            byte[][] design_temp = new byte[objWeave.getIntWeft()][objWeave.getIntWarp()];
            for(int i=0;i<objWeave.getIntWeft();i++){
                for(int j=0;j<objWeave.getIntWarp();j++){
                    design_temp[i][(j+8)%objWeave.getIntWarp()]=objWeave.getDesignMatrix()[i][j];
                }
            }
            objWeave.setDesignMatrix(design_temp);
            design_temp=null;
            System.gc();
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"move right by 8 function"+ex.toString(),ex);
        }
    }
    
    public void moveLeft8(Weave objWeave){
        try {
            byte[][] design_temp = new byte[objWeave.getIntWeft()][objWeave.getIntWarp()];
            for(int i=0;i<objWeave.getIntWeft();i++){
                for(int j=0;j<objWeave.getIntWarp();j++){
                    design_temp[i][(objWeave.getIntWarp()+j-8)%objWeave.getIntWarp()]=objWeave.getDesignMatrix()[i][j];
                }
            }
            objWeave.setDesignMatrix(design_temp);
            design_temp=null;
            System.gc();
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"move left by 8 function"+ex.toString(),ex);
        }
    }
    
    public void moveUp8(Weave objWeave){
        try {
            byte[][] design_temp = new byte[objWeave.getIntWeft()][objWeave.getIntWarp()];
            for(int i=0;i<objWeave.getIntWeft();i++){
                for(int j=0;j<objWeave.getIntWarp();j++){
                    design_temp[(objWeave.getIntWeft()+i-8)%objWeave.getIntWeft()][j]=objWeave.getDesignMatrix()[i][j];
                }
            }
            objWeave.setDesignMatrix(design_temp);
            design_temp=null;
            System.gc();
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"move up by 8 function"+ex.toString(),ex);
        }
    }
    
    public void moveDown8(Weave objWeave){
        try {
            byte[][] design_temp = new byte[objWeave.getIntWeft()][objWeave.getIntWarp()];
            for(int i=0;i<objWeave.getIntWeft();i++){
                for(int j=0;j<objWeave.getIntWarp();j++){
                    design_temp[(i+8)%objWeave.getIntWeft()][j]=objWeave.getDesignMatrix()[i][j];
                }
            }
            objWeave.setDesignMatrix(design_temp);
            design_temp=null;
            System.gc();
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"move down by 8 function"+ex.toString(),ex);
        }
    }
    
    public void tiltRight(Weave objWeave){
        try {
            byte[][] design_temp = new byte[objWeave.getIntWeft()][objWeave.getIntWarp()];
            for(int i=0,k=objWeave.getIntWeft()-1;i<objWeave.getIntWeft();i++,k--){
                for(int j=0;j<objWeave.getIntWarp();j++){
                    design_temp[i][(k+j)%objWeave.getIntWarp()]=objWeave.getDesignMatrix()[i][j];
                }
            }
            objWeave.setDesignMatrix(design_temp);
            design_temp=null;
            System.gc();
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"tilt right function"+ex.toString(),ex);
        }
    }
    
    public void tiltLeft(Weave objWeave){
        try {
            byte[][] design_temp = new byte[objWeave.getIntWeft()][objWeave.getIntWarp()];
            for(int i=0;i<objWeave.getIntWeft();i++){
                for(int j=0;j<objWeave.getIntWarp();j++){
                    design_temp[i][Math.abs(j+i+1+objWeave.getIntWarp()-objWeave.getIntWeft())%objWeave.getIntWarp()]=objWeave.getDesignMatrix()[i][j];
                }
            }
            /*
            for(int i=0,k=objWeave.getIntWeft()-1;i<objWeave.getIntWeft();i++,k--){
                for(int j=0;j<objWeave.getIntWarp();j++){
                    design_temp[i][(objWeave.getIntWarp()+k-j)%objWeave.getIntWarp()]=objWeave.getDesignMatrix()[i][j];
                }
            }
            */
            objWeave.setDesignMatrix(design_temp);
            design_temp=null;
            System.gc();
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"tilt left function"+ex.toString(),ex);
        }
    }
    
    public void tiltUp(Weave objWeave){
        try {
            byte[][] design_temp = new byte[objWeave.getIntWeft()][objWeave.getIntWarp()];
            int index=0;
            for(int i=0;i<objWeave.getIntWeft();i++){
                for(int j=0;j<objWeave.getIntWarp();j++){
                    index=objWeave.getIntWeft()+i-j;
                    if(index<0)
                        index+=objWeave.getIntWeft();
                    design_temp[index%objWeave.getIntWeft()][j]=objWeave.getDesignMatrix()[i][j];
                }
            }
            objWeave.setDesignMatrix(design_temp);
            design_temp=null;
            System.gc();
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"tilt up function"+ex.toString(),ex);
        }
    }
    
    public void tiltDown(Weave objWeave){
        try {
            byte[][] design_temp = new byte[objWeave.getIntWeft()][objWeave.getIntWarp()];
            for(int i=0;i<objWeave.getIntWeft();i++){
                for(int j=0;j<objWeave.getIntWarp();j++){
                    design_temp[(i+j)%objWeave.getIntWeft()][j]=objWeave.getDesignMatrix()[i][j];
                }
            }
            objWeave.setDesignMatrix(design_temp);
            design_temp=null;
            System.gc();
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"tilt down function"+ex.toString(),ex);
        }
    }
    
    public void insertWarp(Weave objWeave, int column, char ch){
        byte[][] design_temp= new byte[objWeave.getIntWeft()][objWeave.getIntWarp()+1];
        for(int i=0;i<objWeave.getIntWeft();i++){
            for(int j=0,k=0;j<objWeave.getIntWarp()+1;j++){
                k=j;
                if(j>column)
                    k=j-1;
                design_temp[i][j]=objWeave.getDesignMatrix()[i][k];
            }
        }
        
        Yarn[] warp_yarn=new Yarn[objWeave.getIntWarp()+1];
        byte[][] dent_temp= new byte[2][objWeave.getIntWarp()+1];
        for(int j=0,k=0;j<objWeave.getIntWarp()+1;j++){
            k=j;
            if(j>column)
                k=j-1;
            warp_yarn[j]=objWeave.getWarpYarn()[k];
            dent_temp[0][j]=objWeave.getDentMatrix()[0][k];
            dent_temp[1][j]=objWeave.getDentMatrix()[1][k];
        }
        
        objWeave.setIntWarp(objWeave.getIntWarp()+1);
        objWeave.setDesignMatrix(design_temp);
        objWeave.setWarpYarn(warp_yarn);
        objWeave.setDentMatrix(dent_temp);
        design_temp=null;
        System.gc();
    }
    
    public void deleteWarp(Weave objWeave, int column,char ch){
        byte[][] design_temp= new byte[objWeave.getIntWeft()][objWeave.getIntWarp()-1];
        for(int i=0;i<objWeave.getIntWeft();i++){
            for(int j=0,k=0;j<objWeave.getIntWarp();j++){
                k=j;
                if(j>column)
                    k=j-1;
                design_temp[i][k]=objWeave.getDesignMatrix()[i][j];
            }
        }
        
        Yarn[] warp_yarn=new Yarn[objWeave.getIntWarp()-1];
        byte[][] dent_temp= new byte[2][objWeave.getIntWarp()+1];
        for(int j=0,k=0;j<objWeave.getIntWarp();j++){
            k=j;
            if(j>column)
                k=j-1;
            warp_yarn[k]=objWeave.getWarpYarn()[j];
            dent_temp[0][k]=objWeave.getDentMatrix()[0][j];
            dent_temp[1][k]=objWeave.getDentMatrix()[1][j];
        }
        
        objWeave.setIntWarp(objWeave.getIntWarp()-1);
        objWeave.setDesignMatrix(design_temp);
        objWeave.setWarpYarn(warp_yarn);
        objWeave.setDentMatrix(dent_temp);
        design_temp=null;
        System.gc();
   }
    
    public void insertWeft(Weave objWeave, int row, char ch){
        byte[][] design_temp= new byte[objWeave.getIntWeft()+1][objWeave.getIntWarp()];
        for(int i=0,k=0;i<objWeave.getIntWeft()+1;i++){
            for(int j=0;j<objWeave.getIntWarp();j++){
                k=i;
                if(i>row)
                    k=i-1;
                design_temp[i][j]=objWeave.getDesignMatrix()[k][j];
            }
        }
        Yarn[] weft_yarn=new Yarn[objWeave.getIntWeft()+1];
        for(int i=0,k=0;i<objWeave.getIntWeft()+1;i++){
            k=i;
            if(i>row)
                k=i-1;
            weft_yarn[i]=objWeave.getWeftYarn()[k];
        }
        
        objWeave.setIntWeft(objWeave.getIntWeft()+1);
        objWeave.setDesignMatrix(design_temp);
        objWeave.setWeftYarn(weft_yarn);
        design_temp=null;
        System.gc();
    }
    
    public void deleteWeft(Weave objWeave, int row,char ch){
        byte[][] design_temp= new byte[objWeave.getIntWeft()-1][objWeave.getIntWarp()];
        for(int i=0,k=0;i<objWeave.getIntWeft();i++){
            for(int j=0;j<objWeave.getIntWarp();j++){
                k=i;
                if(i>row)
                    k=i-1;
                design_temp[k][j]=objWeave.getDesignMatrix()[i][j];
            }
        }
        Yarn[] weft_yarn=new Yarn[objWeave.getIntWeft()-1];
        for(int i=0,k=0;i<objWeave.getIntWeft();i++){
            k=i;
            if(i>row)
                k=i-1;
            weft_yarn[k]=objWeave.getWeftYarn()[i];
        }
        
        objWeave.setIntWeft(objWeave.getIntWeft()-1);
        objWeave.setDesignMatrix(design_temp);
        objWeave.setWeftYarn(weft_yarn);
        design_temp=null;
        System.gc();
    }
    
    public void rotation(Weave objWeave){
        try{
            byte[][] design_temp = new byte[objWeave.getIntWarp()][objWeave.getIntWeft()];
            for(int i=0;i<objWeave.getIntWeft();i++){
                for(int j=0;j<objWeave.getIntWarp();j++){
                    design_temp[j][i]=objWeave.getDesignMatrix()[i][j];
                }
            }
            for (int  j = 0,k=0; j < design_temp[0].length/2; j++,k++) {
                for (int i = 0,m=0; i < design_temp.length; i++,m++) {
                    byte x = design_temp[i][j];
                    design_temp[i][j] = design_temp[i][design_temp[0].length -1 -j]; 
                    design_temp[i][design_temp[0].length -1 -j] = x;
                }
            }
            int temp = objWeave.getIntWarp();
            objWeave.setIntWarp(objWeave.getIntWeft());
            objWeave.setIntWeft(temp);
            objWeave.setDesignMatrix(design_temp);
            design_temp=null;
            System.gc();
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"clock rotate function"+ex.toString(),ex);
        }
    }
    
    public void rotationAnti(Weave objWeave){
        try{
            byte[][] design_temp = new byte[objWeave.getIntWarp()][objWeave.getIntWeft()];
            for(int i=0;i<objWeave.getIntWeft();i++){
                for(int j=0;j<objWeave.getIntWarp();j++){
                    design_temp[j][i]=objWeave.getDesignMatrix()[objWeave.getIntWeft()-1-i][objWeave.getIntWarp()-j-1];
                }
            }
            for (int  j = 0,k=0; j < design_temp[0].length/2; j++,k++) {
                for (int i = 0,m=0; i < design_temp.length; i++,m++) {
                    byte x = design_temp[i][j];
                    design_temp[i][j] = design_temp[i][design_temp[0].length -1 -j]; 
                    design_temp[i][design_temp[0].length -1 -j] = x;
                }
            }
            int temp = objWeave.getIntWarp();
            objWeave.setIntWarp(objWeave.getIntWeft());
            objWeave.setIntWeft(temp);
            objWeave.setDesignMatrix(design_temp);
            design_temp=null;
            System.gc();
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"anti clock rotate function"+ex.toString(),ex);
        }
    }
    
    public void mirrorVertical(Weave objWeave){
        try{
            byte[][] design_temp = new byte[objWeave.getIntWeft()][objWeave.getIntWarp()];
            for(int i=0;i<objWeave.getIntWeft();i++){
                for(int j=0;j<objWeave.getIntWarp();j++){
                    design_temp[objWeave.getIntWeft()-i-1][j]=objWeave.getDesignMatrix()[i][j];
                }
            }
            objWeave.setDesignMatrix(design_temp);
            design_temp=null;
            System.gc();
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"mirror vertical function"+ex.toString(),ex);
        }
    }
    
    public void mirrorHorizontal(Weave objWeave){
        try{
            byte[][] design_temp = new byte[objWeave.getIntWeft()][objWeave.getIntWeft()];
            for(int i=0;i<objWeave.getIntWeft();i++){
                for(int j=0;j<objWeave.getIntWarp();j++){
                    design_temp[i][objWeave.getIntWarp()-j-1]=objWeave.getDesignMatrix()[i][j];
                }
            }
            objWeave.setDesignMatrix(design_temp);
            design_temp=null;
            System.gc();
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"mirror horizental function"+ex.toString(),ex);
        }
    }
    
    public void mirrorVertical(Weave objWeave, int rowinitial,int colinitial,int rowfinal,int colfinal){
        try {
            int rows = rowinitial-rowfinal;
            int cols = colinitial-colfinal;
            int temp=0;
            byte[][] designPaste;
            if(rows>0){
                if(cols>0){
                    temp=rowfinal;
                    rowfinal = rowinitial;
                    rowinitial = temp;
                    temp = colfinal;
                    colfinal = colinitial;
                    colinitial = temp;
                }else{
                    temp = rowfinal;
                    rowfinal = rowinitial;
                    rowinitial = temp;
                }
            }else{
                if(cols>0){
                    temp = colfinal;
                    colfinal = colinitial;
                    colinitial = temp;
                }
            }
            rows = rowfinal-rowinitial;
            cols = colfinal-colinitial;
            designPaste=new byte[rows+1][cols+1];
            for(int i=rowinitial,p=0;i<=rowfinal;i++,p++){
                for(int j=colinitial,q=0;j<=colfinal;j++,q++){
                    designPaste[p][q]=objWeave.getDesignMatrix()[i][j];
                }
            }
            for(int i=rowfinal,p=0;i>=rowinitial;i--,p++){
                for(int j=colinitial,q=0;j<=colfinal;j++,q++){
                    objWeave.getDesignMatrix()[i][j]=designPaste[p][q];
                }
            }
            designPaste=null;
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"mirror vertical function"+ex.toString(),ex);
        }
    }
    
    public void mirrorHorizontal(Weave objWeave, int rowinitial,int colinitial,int rowfinal,int colfinal){
        try {
            int rows = rowinitial-rowfinal;
            int cols = colinitial-colfinal;
            int temp=0;
            byte[][] designPaste;
            if(rows>0){
                if(cols>0){
                    temp=rowfinal;
                    rowfinal = rowinitial;
                    rowinitial = temp;
                    temp = colfinal;
                    colfinal = colinitial;
                    colinitial = temp;
                }else{
                    temp = rowfinal;
                    rowfinal = rowinitial;
                    rowinitial = temp;
                }
            }else{
                if(cols>0){
                    temp = colfinal;
                    colfinal = colinitial;
                    colinitial = temp;
                }
            }
            rows = rowfinal-rowinitial;
            cols = colfinal-colinitial;
            designPaste=new byte[rows+1][cols+1];
            for(int i=rowinitial,p=0;i<=rowfinal;i++,p++){
                for(int j=colinitial,q=0;j<=colfinal;j++,q++){
                    designPaste[p][q]=objWeave.getDesignMatrix()[i][j];
                }
            }
            for(int i=rowinitial,p=0;i<=rowfinal;i++,p++){
                for(int j=colfinal,q=0;j>=colinitial;j--,q++){
                    objWeave.getDesignMatrix()[i][j]=designPaste[p][q];
                }
            }
            designPaste=null;
            System.gc();
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"mirror horizental function"+ex.toString(),ex);
        }
   }
    
    public void copy(Weave objWeave, int rowinitial,int colinitial,int rowfinal,int colfinal){
        try {
            int rows = rowinitial-rowfinal;
            int cols = colinitial-colfinal;
            int temp=0;
            byte[][] designPaste;
            if(rows>0){
                if(cols>0){
                    temp=rowfinal;
                    rowfinal = rowinitial;
                    rowinitial = temp;
                    temp = colfinal;
                    colfinal = colinitial;
                    colinitial = temp;
                }else{
                    temp = rowfinal;
                    rowfinal = rowinitial;
                    rowinitial = temp;
                }
            }else{
                if(cols>0){
                    temp = colfinal;
                    colfinal = colinitial;
                    colinitial = temp;
                }
            }
            rows = rowfinal-rowinitial;
            cols = colfinal-colinitial;
            designPaste=new byte[rows+1][cols+1];
            for(int i=rowinitial,p=0;i<=rowfinal;i++,p++){
                for(int j=colinitial,q=0;j<=colfinal;j++,q++){
                    designPaste[p][q]=objWeave.getDesignMatrix()[i][j];
                }
            }
            objWeave.setClipMatrix(designPaste);
            designPaste = null;
            System.gc();
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"copy function"+ex.toString(),ex);
        }
    }
    
    public void paste(Weave objWeave, int row, int column){
        try {
            for(int i=row,p=0;p<objWeave.getClipMatrix().length;i=(i+1)%objWeave.getIntWeft(),p++){
                for(int j=column,q=0;q<objWeave.getClipMatrix()[0].length;j=(j+1)%objWeave.getIntWarp(),q++){
                    objWeave.getDesignMatrix()[i][j]=objWeave.getClipMatrix()[p][q];
                }
            }
        } catch (Exception ex) {
            new Logging("SEVERE",WeaveAction.class.getName(),"Paste function"+ex.toString(),ex);
        }
   }
 
    
    public BufferedImage plotCompositeView(Weave objWeave, int intWarp, int intWeft, int intLength, int intHeight){
        Yarn[] weftYarn = objWeave.getWeftYarn();
        Yarn[] warpYarn = objWeave.getWarpYarn();
        
        int warpCount = warpYarn.length;
        int weftCount = weftYarn.length;
        byte[][] repeatMatrix = new byte[intHeight][intLength];
        int dpi = objWeave.getObjConfiguration().getIntDPI();
//        int warpFactor = dpi/objWeave.getIntEPI();
  //      int weftFactor = dpi/objWeave.getIntPPI();
        
        for(int i = 0; i < intHeight; i++) {
            for(int j = 0; j < intLength; j++) {
                if(i>=intWeft && j<intWarp){
                     repeatMatrix[i][j] = objWeave.getDesignMatrix()[i%intWeft][j];  
                }else if(i<intWeft && j>=intWarp){
                     repeatMatrix[i][j] = objWeave.getDesignMatrix()[i][j%intWarp];  
                }else if(i>=intWeft && j>=intWarp){
                     repeatMatrix[i][j] = objWeave.getDesignMatrix()[i%intWeft][j%intWarp];  
                }else{
                     repeatMatrix[i][j] = objWeave.getDesignMatrix()[i][j]; 
                }                
            }
        }
        
        BufferedImage bufferedImage = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
        int rgb = 0;
        for(int x = 0, p = 0; x < intHeight; x++) {
            for(int y = 0, q = 0; y < intLength; y++) {
                if(repeatMatrix[x][y]==1){
                    rgb = new Color((float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).getRGB();
                } else if(repeatMatrix[x][y]==0){
                    rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRGB();
                } else{
                    rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRGB();
                }
                bufferedImage.setRGB(y, x, rgb);
            }
        }
        repeatMatrix = null;
        return bufferedImage;
    }
    public BufferedImage plotFrontSideView(Weave objWeave, int intWarp, int intWeft, int intLength, int intHeight){
        Yarn[] weftYarn = objWeave.getWeftYarn();
        Yarn[] warpYarn = objWeave.getWarpYarn();
        
        int warpCount = warpYarn.length;
        int weftCount = weftYarn.length;
        byte[][] repeatMatrix = new byte[intHeight][intLength];
        int dpi = objWeave.getObjConfiguration().getIntDPI();
        //int warpFactor = dpi/objWeave.getIntEPI();
        //int weftFactor = dpi/objWeave.getIntPPI();
        
        for(int i = 0; i < intHeight; i++) {
            for(int j = 0; j < intLength; j++) {
                if(i>=intWeft && j<intWarp){
                     repeatMatrix[i][j] = objWeave.getDesignMatrix()[i%intWeft][j];  
                }else if(i<intWeft && j>=intWarp){
                     repeatMatrix[i][j] = objWeave.getDesignMatrix()[i][j%intWarp];  
                }else if(i>=intWeft && j>=intWarp){
                     repeatMatrix[i][j] = objWeave.getDesignMatrix()[i%intWeft][j%intWarp];  
                }else{
                     repeatMatrix[i][j] = objWeave.getDesignMatrix()[i][j]; 
                }                
            }
        } 
        
        BufferedImage bufferedImage = new BufferedImage(intLength*3, intHeight*3,BufferedImage.TYPE_INT_RGB);        
        int bands = 3;
        int rgb = 0;
        for(int x = 0, p = 0; x < intHeight; x++) {
            for(int y = 0, q = 0; y < intLength; y++) {
                for(int i = 0; i < bands; i++) {
                    for(int j = 0; j < bands; j++) {                        
                        if(repeatMatrix[x][y]==1){
                            if(j==0)
                                rgb = new Color((float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).brighter().getRGB();
                            else if(j==2)
                                rgb = new Color((float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).darker().getRGB();
                            else
                                rgb = new Color((float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).getRGB();
                        } else if(repeatMatrix[x][y]==0){
                            if(i==0)
                                rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).brighter().getRGB();
                            else if(i==2)
                                rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).darker().getRGB();
                            else
                                rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRGB();
                        } else{
                            if(i==0)
                                rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).brighter().getRGB();
                            else if(i==2)
                                rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).darker().getRGB();
                            else
                                rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRGB();
                        }
                        bufferedImage.setRGB(q+j, p+i, rgb);
                    }
                }
                q+=bands;
            }
            p+=bands;
        }
        repeatMatrix = null;
        return bufferedImage;
    }
    public BufferedImage plotFlipSideView(Weave objWeave, int intWarp, int intWeft, int intLength, int intHeight){
        Yarn[] weftYarn = objWeave.getWeftYarn();
        Yarn[] warpYarn = objWeave.getWarpYarn();
        
        int warpCount = warpYarn.length;
        int weftCount = weftYarn.length;
        byte[][] repeatMatrix = new byte[intHeight][intLength];
        int dpi = objWeave.getObjConfiguration().getIntDPI();
       // int warpFactor = dpi/objWeave.getIntEPI();
        //int weftFactor = dpi/objWeave.getIntPPI();
        
        for(int i = 0; i < intHeight; i++) {
            for(int j = 0; j < intLength; j++) {
                if(i>=intWeft && j<intWarp){
                     repeatMatrix[i][j] = objWeave.getDesignMatrix()[i%intWeft][j];  
                }else if(i<intWeft && j>=intWarp){
                     repeatMatrix[i][j] = objWeave.getDesignMatrix()[i][j%intWarp];  
                }else if(i>=intWeft && j>=intWarp){
                     repeatMatrix[i][j] = objWeave.getDesignMatrix()[i%intWeft][j%intWarp];  
                }else{
                     repeatMatrix[i][j] = objWeave.getDesignMatrix()[i][j]; 
                }                
            }
        }
        
        BufferedImage bufferedImage = new BufferedImage(intLength*3, intHeight*3, BufferedImage.TYPE_INT_RGB);        
        int bands = 3;
        int rgb = 0;
        for(int x = 0, p = 0; x < intHeight; x++) {
            for(int y = 0, q = 0; y < intLength; y++) {
                for(int i = 0; i < bands; i++) {
                    for(int j = 0; j < bands; j++) {                        
                        if(repeatMatrix[x][y]==0){
                            if(j==0)
                                rgb = new Color((float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).brighter().getRGB();
                            else if(j==2)
                                rgb = new Color((float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).darker().getRGB();
                            else
                                rgb = new Color((float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).getRGB();
                        } else if(repeatMatrix[x][y]==1){
                            if(i==0)
                                rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).brighter().getRGB();
                            else if(i==2)
                                rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).darker().getRGB();
                            else
                                rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRGB();
                        } else{
                            if(i==0)
                                rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).brighter().getRGB();
                            else if(i==2)
                                rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).darker().getRGB();
                            else
                                rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRGB();
                        }
                        bufferedImage.setRGB(q+j, p+i, rgb);
                    }
                }
                q+=bands;
            }
            p+=bands;
        }
        repeatMatrix = null;
        return bufferedImage;
    }
    
    public String toHexfromColor( javafx.scene.paint.Color color ){
        return String.format( "#%02X%02X%02X",
            (int)( color.getRed() * 255 ),
            (int)( color.getGreen() * 255 ),
            (int)( color.getBlue() * 255 ) );
    }

    public String toHexFromRGB( double red, double green, double blue ){
        return String.format( "#%02X%02X%02X",
            (int)( red * 255 ),
            (int)( green * 255 ),
            (int)( blue * 255 ) );
    }
}