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

package com.mla.main;

import com.mla.artwork.ArtworkAction;
import com.mla.user.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mla.secure.Security;
/**
 *
 * @author Amit Kumar Singh
 */
public class IDGenerator {

    Connection connection;
    
    public void open() throws SQLException{
        connection = DbConnect.getConnection();
    }
    
    public void close() throws SQLException{
        connection.close();
    }
    
    public String getIDGenerator(String strTable, String strUser){
        Statement oStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        String strNewID=null;
        new Logging("INFO",IDGenerator.class.getName(),"<<<<<<<<<<< getIDGenerator() >>>>>>>>>>>",null);
        try {           
            open();
            strQuery = "SELECT `ID`, `TAG`,`COUNT` FROM `mla_master_sequence` WHERE `TABLE_NMAE`='"+strTable+"';";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);  
            oResultSet.next(); 
            int count = oResultSet.getInt("COUNT")+1;
            strNewID = oResultSet.getString("TAG")+count+strUser;
            
            strQuery = "UPDATE `mla_master_sequence` SET `COUNT`='"+count+"' WHERE ID='"+oResultSet.getInt("ID")+"';";
            oStatement = connection.createStatement();
            oResult = (byte)oStatement.executeUpdate(strQuery);
            close();
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkAction.class.getName(),"getIDGenerator() : "+strQuery,ex);
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
                    new Logging("SEVERE",IDGenerator.class.getName(),"getIDGenerator() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",IDGenerator.class.getName(),">>>>>>>>>>> getIDGenerator() <<<<<<<<<<<"+strQuery,null);
        return strNewID;
    }
    
    public String getUserAcess(String strTable){
        String strSecureText=Security.SecureData(strTable,"Public");
        return strSecureText;
    }    
    
    public String setUserAcess(String strTable){
        String strSecureText=Security.RecureData(strTable,"Public");
        return strSecureText;
    }
    
    public String getUserAcessKey(String strTable, User objUser){
        String strSecureText=Security.RecureData(strTable,objUser.getUserAccess(strTable));
        return strSecureText;
    }
    
    public String setUserAcessKey(String strTable, User objUser){
        String strSecureText=Security.SecureData(strTable,objUser.getUserAccess(strTable));
        return strSecureText;
    }
    
    public String getUserAcessValueData(String strTable,String strAcess){
        String strSecureText=Security.RecureData(strTable,strAcess);
        return strSecureText;
    }
    
    public String setUserAcessValueData(String strTable,String strAcess){
        String strSecureText=Security.SecureData(strTable,strAcess);
        return strSecureText;
    }
    
    private String getTableSequence(String strTable){       
        return null;
    }    
}