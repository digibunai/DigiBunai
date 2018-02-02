/*
 * Copyright (C) 2017 Digital India Corporation (formerly Media Lab Asia)
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
package com.mla.artwork;

import com.mla.main.CannyEdgeDetector;
import com.mla.main.DbConnect;
import com.mla.main.Logging;
import com.mla.main.IDGenerator;
import ij.process.MedianCut;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.swt.graphics.Point;
/**
 * ArtworkAction Class
 * <p>
 * This class is used for defining model methods for artwork properties.
 *
 * @author Amit Kumar Singh
 * @version %I%, %G%
 * @since   1.0
 * @date 07/01/2016
 * @Designing model method class for artwork
 * @see java.sql.*;
 * @link com.mla.main.DbConnect
 */
public class ArtworkAction {    
    Connection connection = null; //DbConnect.getConnection();
    Artwork objArtwork;
    
    /**
     * ArtworkAction
     * <p>
     * This constructor is used for creating database connection.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   constructor is used for creating database connection.
     * @see         java.sql.*;
     * @link        com.mla.main.DbConnect
     * @throws      SQLException
     */
    public ArtworkAction() throws SQLException{
        connection = DbConnect.getConnection();
    }
    /**
     * Artwork Class
     * <p>
     * This constructor is used for creating database connection.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   constructor is used for creating database connection.
     * @see         java.sql.*;
     * @link        com.mla.main.DbConnect
     * @throws      SQLException
     * @param       isDB boolean <code>true</code> if need to get access database connection
     *              <code>false</code> otherwise.
     */
    public ArtworkAction(boolean isDB) throws SQLException{
        if(isDB)
            connection = DbConnect.getConnection();
    }    
    /**
     * Artwork Class
     * <p>
     * This constructor is used for creating database connection.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   constructor is used for creating database connection.
     * @see         java.sql.*;
     * @link        com.mla.main.DbConnect
     * @throws      SQLException
     * @param       objArtworkCall Artwork object
     * @param       isDB boolean <code>true</code> if need to get access database connection
     *              <code>false</code> otherwise.
     */
    public ArtworkAction(Artwork objArtworkCall, boolean isDB) throws SQLException{
        if(isDB)
            connection = DbConnect.getConnection();
        objArtwork = objArtworkCall;
    }    
    /**
     * close
     * <p>
     * This method is used for destroying database connection.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for closing database connection.
     * @see         java.sql.*;
     * @throws      SQLException
     */
    public void close() throws SQLException{
        connection.close();
    }    
    /**
     * countArtworkUsage
     * <p>
     * This method is used for counting usage of artwork in fabric.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for counting usage of artwork in fabric.
     * @see         java.sql.*;
     * @link        com.mla.main.DbConnect
     * @link        com.mla.main.Logging
     * @exception   Exception
     * @param       strArtworkID [String] Artwork unique id
     * @return      count [Integer] return total number of times artwork is used 
     *              <code>0</code> otherwise.
     */
    public int countArtworkUsage(String strArtworkID){
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        int count = 0;
        new Logging("INFO",ArtworkAction.class.getName(),"<<<<<<<<<<< countArtworkUsage() >>>>>>>>>>>",null);
        try {           
            strQuery = "SELECT COUNT(*) from mla_fabric_library WHERE `ARTWORKID` = '"+strArtworkID+"' ORDER BY `ID` DESC;";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);  
            while(oResultSet.next()) {
                count = oResultSet.getInt(1);
            }
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkAction.class.getName(),"countArtworkUsage() : "+strQuery,ex);
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
                    new Logging("SEVERE",ArtworkAction.class.getName(),"countArtworkUsage() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",ArtworkAction.class.getName(),">>>>>>>>>>> countArtworkUsage() <<<<<<<<<<<"+count,null);
        return count;
    }    
    /**
     * clearArtwork
     * <p>
     * This method is used for deleting artwork from library.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for deleting artwork from library.
     * @see         java.sql.*;
     * @link        com.mla.main.DbConnect
     * @link        com.mla.main.Logging
     * @exception   Exception
     * @param       strArtworkID String Artwork unique id
     * @return      oResult boolean field <code>true</code> if the artwork is removed 
     *              <code>false</code> otherwise.
     */
    public boolean clearArtwork(String strArtworkID){
        PreparedStatement oPreparedStatement =null;
        boolean oResult= false;
        String strQuery=null;
        new Logging("INFO",ArtworkAction.class.getName(),"<<<<<<<<<<< clearArtwork() >>>>>>>>>>>",null);
        try {           
            strQuery = "DELETE FROM mla_design_library WHERE `ID` = ?;";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, strArtworkID);
            oResult = oPreparedStatement.execute();           
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkAction.class.getName(),"clearArtwork() : "+strQuery,ex);
        } finally {
            try {
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
                    new Logging("SEVERE",ArtworkAction.class.getName(),"clearArtwork() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",ArtworkAction.class.getName(),">>>>>>>>>>> clearArtwork() <<<<<<<<<<<"+oResult,null);
        return oResult;
    }
    /**
     * lstImportArtwork
     * <p>
     * This method is used for accessing artwork from library based on conditions.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for accessing artwork from library.
     * @see         java.sql.*;
     * @link        com.mla.main.DbConnect
     * @link        com.mla.main.Logging
     * @exception   Exception
     * @param       objArtwork Object Artwork
     * @return      lstArtworkDeatails List field of artwork 
     *              <code>null</code> otherwise.
     */
    public List lstImportArtwork(Artwork objArtwork) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        List lstArtworkDeatails=null, lstArtwork;
        new Logging("INFO",ArtworkAction.class.getName(),"<<<<<<<<<<< lstImportArtwork() >>>>>>>>>>>",null);            
        try {           
            String cond = "(USERID = '"+objArtwork.getObjConfiguration().getObjUser().getStrUserID()+"' OR `ACCESS`='"+new IDGenerator().getUserAcess("ARTWORK_LIBRARY")+"') ";
            String orderBy ="NAME ";
            if(!(objArtwork.getStrCondition().trim().equals(""))) {
                cond += " AND NAME  LIKE '"+objArtwork.getStrCondition().trim()+"%'";
            }
            if(objArtwork.getStrOrderBy().equals("Name")) {
                orderBy = "NAME ";
            } else if(objArtwork.getStrOrderBy().equals("Date")) {
                orderBy = "UDATE";
            }
            if(objArtwork.getStrDirection().equals("Ascending")) {
                orderBy += " ASC";
            } else if(objArtwork.getStrDirection().equals("Descending")) {
                orderBy += " DESC";
            }
            strQuery = "select * from mla_design_library WHERE "+cond+" ORDER BY "+orderBy;
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);    
            lstArtworkDeatails = new ArrayList();            
            while(oResultSet.next()) {
                lstArtwork = new ArrayList();                
                lstArtwork.add(oResultSet.getString("ID").toString());
                lstArtwork.add(oResultSet.getString("NAME").toString());
                lstArtwork.add(oResultSet.getBytes("FILE"));
                lstArtwork.add(oResultSet.getString("BACKGROUND"));
                lstArtwork.add(oResultSet.getTimestamp("UDATE").toString());
                lstArtwork.add(oResultSet.getString("USERID").toString());
                lstArtwork.add(oResultSet.getString("ACCESS").toString());
                lstArtworkDeatails.add(lstArtwork);
            }
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkAction.class.getName(),"lstImportArtwork() : "+strQuery,ex);
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
                    new Logging("SEVERE",ArtworkAction.class.getName(),"lstImportArtwork() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",ArtworkAction.class.getName(),">>>>>>>>>>> lstImportArtwork() <<<<<<<<<<<"+strQuery,null);
        return lstArtworkDeatails;
    }
    /**
     * getArtwork
     * <p>
     * This method is used for accessing artwork details from library.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for accessing artwork details from library.
     * @see         java.sql.*;
     * @link        com.mla.main.DbConnect
     * @link        com.mla.main.Logging
     * @exception   Exception
     * @param       objArtwork Object Artwork
     */
    public void getArtwork(Artwork objArtwork) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        new Logging("INFO",ArtworkAction.class.getName(),"<<<<<<<<<<< getArtwork() >>>>>>>>>>>",null);
        try {
            String cond = "1 ";
            if(objArtwork.getStrArtworkId()!="" && objArtwork.getStrArtworkId()!=null)
                cond += " AND ID='"+objArtwork.getStrArtworkId().trim()+"'";
            strQuery = "SELECT * FROM mla_design_library WHERE "+cond+" LIMIT 1;";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);    
            while(oResultSet.next()) {
                objArtwork.setStrArtworkId(oResultSet.getString("ID").toString());
                objArtwork.setStrArtworkName(oResultSet.getString("NAME").toString());
                objArtwork.setBytArtworkThumbnil(oResultSet.getBytes("FILE"));
                objArtwork.setStrArtworkBackground(oResultSet.getString("BACKGROUND"));
                objArtwork.setStrArtworkDate(oResultSet.getTimestamp("UDATE").toString());
                objArtwork.setStrArtworkAccess(oResultSet.getString("ACCESS").toString());                
            }                       
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkAction.class.getName(),"getArtwork() : "+strQuery,ex);
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
                    new Logging("SEVERE",ArtworkAction.class.getName(),"getArtwork() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",ArtworkAction.class.getName(),">>>>>>>>>>> getArtwork() <<<<<<<<<<<"+strQuery,null);
        return;
    }
    /**
     * setArtwork
     * <p>
     * This method is used for inserting artwork to library.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for inserting artwork to library.
     * @see         java.sql.*;
     * @link        com.mla.main.DbConnect
     * @link        com.mla.main.Logging
     * @exception   Exception
     * @param       objArtwork Object Artwork
     * @return      oResult byte field <code>1</code>if inserted data
     *              <code>0</code> otherwise.
     */
    public byte setArtwork(Artwork objArtwork) {
        PreparedStatement oPreparedStatement =null;
        byte oResult= 0;
        String strQuery=null;
        new Logging("INFO",ArtworkAction.class.getName(),"<<<<<<<<<<< setArtwork() >>>>>>>>>>>",null);
        try {           
            strQuery = "INSERT INTO mla_design_library (ID,NAME,FILE,BACKGROUND,USERID,ACCESS) VALUES (?,?,?,?,?,?);";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, objArtwork.getStrArtworkId());
            oPreparedStatement.setString(2, objArtwork.getStrArtworkName());
            oPreparedStatement.setBinaryStream(3,new ByteArrayInputStream(objArtwork.getBytArtworkThumbnil()),objArtwork.getBytArtworkThumbnil().length);
            oPreparedStatement.setString(4, objArtwork.getStrArtworkBackground());
            oPreparedStatement.setString(5, objArtwork.getObjConfiguration().getObjUser().getStrUserID());
            oPreparedStatement.setString(6, new IDGenerator().setUserAcessValueData("ARTWORK_LIBRARY",objArtwork.getStrArtworkAccess()));
            oResult = (byte)oPreparedStatement.executeUpdate();
        } catch (SQLException ex) {
            new Logging("SEVERE",ArtworkAction.class.getName(),"setArtwork() : "+strQuery,ex);
        } finally {
            try {
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
                    new Logging("SEVERE",ArtworkAction.class.getName(),"setArtwork() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",ArtworkAction.class.getName(),">>>>>>>>>>> setArtwork() <<<<<<<<<<<"+strQuery,null);
        return oResult;
    }    
    /**
     * setTempArtwork
     * <p>
     * This method is used for inserting artwork to library.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for inserting artwork to library.
     * @see         java.sql.*;
     * @link        com.mla.main.DbConnect
     * @link        com.mla.main.Logging
     * @exception   Exception
     * @param       objArtwork Object Artwork
     * @return      oResult byte field <code>1</code>if inserted data
     *              <code>0</code> otherwise.
     */
    public byte setTempArtwork(Artwork objArtwork) {
        PreparedStatement oPreparedStatement =null;
        byte oResult= 0;
        String strQuery=null;
        new Logging("INFO",ArtworkAction.class.getName(),"<<<<<<<<<<< setTempArtwork() >>>>>>>>>>>",null);
        try {           
            strQuery = "INSERT INTO mla_artwork_library (ID,NAME,FILE,BACKGROUND,USERID,ACCESS) VALUES (?,?,?,?,?,?);";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, objArtwork.getStrArtworkId());
            oPreparedStatement.setString(2, objArtwork.getStrArtworkName());
            oPreparedStatement.setBinaryStream(3,new ByteArrayInputStream(objArtwork.getBytArtworkThumbnil()),objArtwork.getBytArtworkThumbnil().length);
            oPreparedStatement.setString(4, objArtwork.getStrArtworkBackground());
            oPreparedStatement.setString(5, objArtwork.getObjConfiguration().getObjUser().getStrUserID());
            oPreparedStatement.setString(6, new IDGenerator().setUserAcessValueData("ARTWORK_LIBRARY",objArtwork.getStrArtworkAccess()));
            oResult = (byte)oPreparedStatement.executeUpdate();
        } catch (SQLException ex) {
            new Logging("SEVERE",ArtworkAction.class.getName(),"setTempArtwork() : "+strQuery,ex);
        } finally {
            try {
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
                    new Logging("SEVERE",ArtworkAction.class.getName(),"setTempArtwork() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",ArtworkAction.class.getName(),">>>>>>>>>>> setTempArtwork() <<<<<<<<<<<"+strQuery,null);
        return oResult;
    }
    /**
     * resetArtwork
     * <p>
     * This method is used for updating artwork to library.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for updating artwork to library.
     * @see         java.sql.*;
     * @link        com.mla.main.DbConnect
     * @link        com.mla.main.Logging
     * @exception   Exception
     * @param       objArtwork Object Artwork
     * @return      oResult byte field <code>1</code>if inserted data
     *              <code>0</code> otherwise.
     */
    public byte resetArtwork(Artwork objArtwork){
        PreparedStatement oPreparedStatement =null;
        byte oResult= 0;
        String strQuery=null;
        new Logging("INFO",ArtworkAction.class.getName(),"<<<<<<<<<<< resetArtwork() >>>>>>>>>>>",null);
        try {           
            strQuery = "UPDATE `mla_design_library` SET `NAME` = ?, `FILE` = ?, `BACKGROUND` = ?, `ACCESS` = ? WHERE `USERID`= ? AND `ID`= ?;";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, objArtwork.getStrArtworkName());
            oPreparedStatement.setBinaryStream(2,new ByteArrayInputStream(objArtwork.getBytArtworkThumbnil()),objArtwork.getBytArtworkThumbnil().length);
            oPreparedStatement.setString(3, objArtwork.getStrArtworkBackground());
            oPreparedStatement.setString(4, new IDGenerator().setUserAcessValueData("ARTWORK_LIBRARY",objArtwork.getStrArtworkAccess()));
            oPreparedStatement.setString(5, objArtwork.getObjConfiguration().getObjUser().getStrUserID());
            oPreparedStatement.setString(6, objArtwork.getStrArtworkId());
            oResult = (byte)oPreparedStatement.executeUpdate();              
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkAction.class.getName(),"resetArtwork : "+strQuery,ex);
        } finally {
            try {
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
                    new Logging("SEVERE",ArtworkAction.class.getName(),"resetArtwork() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",ArtworkAction.class.getName(),">>>>>>>>>>> resetArtwork() <<<<<<<<<<<"+strQuery,null);
        return oResult;
    }
    /**
     * resetArtworkPermission
     * <p>
     * This method is used for updating artwork permission in library.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for updating artwork permission in library.
     * @see         java.sql.*;
     * @link        com.mla.main.DbConnect
     * @link        com.mla.main.Logging
     * @exception   Exception
     * @param       strArtworkID String Artwork unique id
     * @param       strAccess String Artwork access
     * @return      oResult byte field <code>1</code>if inserted data
     *              <code>0</code> otherwise.
     */    
    public byte resetArtworkPermission(String strArtworkID, String strAccess){
        PreparedStatement oPreparedStatement =null;
        byte oResult= 0;
        String strQuery=null;
        new Logging("INFO",ArtworkAction.class.getName(),"<<<<<<<<<<< resetArtworkPermission() >>>>>>>>>>>",null);
        try {           
            strQuery = "UPDATE `mla_design_library` SET `ACCESS` = ? WHERE `ID`= ?;";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, strAccess);
            oPreparedStatement.setString(2, strArtworkID);
            oResult = (byte)oPreparedStatement.executeUpdate();              
        } catch (Exception ex) {
            new Logging("SEVERE",ArtworkAction.class.getName(),"resetArtworkPermission : "+strQuery,ex);
        } finally {
            try {
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
                    new Logging("SEVERE",ArtworkAction.class.getName(),"resetArtworkPermission() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",ArtworkAction.class.getName(),">>>>>>>>>>> resetArtworkPermission() <<<<<<<<<<<"+strQuery,null);
        return oResult;
    }
/*========== END OF DATABASE METHODS ==========*/

    /**
     * getImageColor
     * <p>
     * This method is used for extracting distinct color list from a image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for extracting distinct color list from a image.
     * @see         java.awt.*;
     * @link        java.awt.image.BufferedImage
     * @link        com.mla.main.Logging
     * @param       bufferedImage BufferedImage
     * @return      lstColor ArrayList field list of colors used in image
     *              <code>null</code> otherwise.
     */    
    public ArrayList getImageColor(BufferedImage bufferedImage) {	  
        ArrayList<Color> lstColor = new ArrayList<Color>();
        int intWidth = bufferedImage.getWidth();
        int intHeight = bufferedImage.getHeight();
              
        for(int y = 0; y < intHeight; y++) {
            for(int x = 0; x < intWidth; x++) {
                int pixel = bufferedImage.getRGB(x, y);     
                int red   = (pixel & 0x00ff0000) >> 16;
                int green = (pixel & 0x0000ff00) >> 8;
                int blue  =  pixel & 0x000000ff;                    
                Color color = new Color(red,green,blue);     
                //add the first color on array
                if(lstColor.size()==0)                
                    lstColor.add(color);          
                //check for redudancy
                else {                
                    if(!(lstColor.contains(color)))
                        lstColor.add(color);
                }               
            }
        }        
        return lstColor;
    }
    /**
     * getImageColor
     * <p>
     * This method is used for extracting distinct color list from a image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for extracting distinct color list from a image.
     * @see         java.awt.*;
     * @link        java.awt.image.BufferedImage
     * @link        com.mla.main.Logging
     * @param       bufferedImage BufferedImage
     * @return      lstColor ArrayList field list of colors used in image
     *              <code>null</code> otherwise.
     */    
    public List getImageColorDetails(BufferedImage bufferedImage) {	  
        List lstColorDetails = new ArrayList();
        ArrayList<Color> lstColor = new ArrayList<Color>();
        ArrayList<Double> lstPercentage = new ArrayList<Double>();
        int intWidth = bufferedImage.getWidth();
        int intHeight = bufferedImage.getHeight();
              
        for(int y = 0; y < intHeight; y++) {
            for(int x = 0; x < intWidth; x++) {
                int pixel = bufferedImage.getRGB(x, y);     
                int red   = (pixel & 0x00ff0000) >> 16;
                int green = (pixel & 0x0000ff00) >> 8;
                int blue  =  pixel & 0x000000ff;                    
                Color color = new Color(red,green,blue);     
                //add the first color on array
                if(lstColor.size()==0){
                    lstColor.add(color);          
                    lstPercentage.add(1.0);
                }
                //check for redudancy
                else {                
                    if(!(lstColor.contains(color))){
                        lstColor.add(color);
                        lstPercentage.add(1.0);
                    }else{
                        lstPercentage.set(lstColor.indexOf(color),lstPercentage.get(lstColor.indexOf(color))+1);
                    }                        
                }               
            }
        }
        for(int i=0; i<lstColor.size(); i++){
            lstPercentage.set(i, (100*lstPercentage.get(i))/(intHeight*intWidth));
            List tempList = new ArrayList();
            tempList.add(lstColor.get(i));
            tempList.add(lstPercentage.get(i));
            lstColorDetails.add(tempList);
        }
        return lstColorDetails;
    }
    /**
     * getImageColor
     * <p>
     * This method is used for extracting distinct color list from a image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for extracting distinct color list from a image.
     * @see         java.awt.*;
     * @link        java.awt.image.BufferedImage
     * @link        com.mla.main.Logging
     * @param       bufferedImage BufferedImage
     * @return      lstColor ArrayList field list of colors used in image
     *              <code>null</code> otherwise.
     */    
    public double getImageColorPercentage(BufferedImage bufferedImage,String strColor) {	  
        int intWidth = bufferedImage.getWidth();
        int intHeight = bufferedImage.getHeight();
        double dblPercentage = 0;
        for(int y = 0; y < intHeight; y++) {
            for(int x = 0; x < intWidth; x++) {
                int pixel = bufferedImage.getRGB(x, y);     
                int red   = (pixel & 0x00ff0000) >> 16;
                int green = (pixel & 0x0000ff00) >> 8;
                int blue  =  pixel & 0x000000ff;    
                if(strColor.equalsIgnoreCase(String.format("#%02X%02X%02X",red,green,blue)))
                   dblPercentage ++;            
            }
        }
        dblPercentage = (100*dblPercentage)/(intHeight*intWidth);
        return dblPercentage;
    }
    /**
     * getImageColorBorder
     * <p>
     * This method is used for extracting distinct color border from a image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for extracting distinct color border from a image.
     * @see         java.awt.*;
     * @link        java.awt.image.BufferedImage
     * @link        com.mla.main.Logging
     * @param       bufferedImage BufferedImage
     * @return      borderImage BufferedImage border colors used in image
     */    
    public BufferedImage getImageColorBorder(BufferedImage bufferedImage) {	  
        int intWidth = bufferedImage.getWidth();
        int intHeight = bufferedImage.getHeight();
        BufferedImage borderImage = new BufferedImage(intWidth, intHeight, bufferedImage.getType());
        
        for(int y = 0; y < intHeight; y++) {
            for(int x = 0; x < intWidth; x++) {
                int pixel = bufferedImage.getRGB(x, y);     
                if(x==0){ //first row
                    borderImage.setRGB(x, y, pixel);
                }else if(x==intWidth-1){ //last row
                    borderImage.setRGB(x, y, pixel);
                }else {
                    if(y==0){ // first coloumn
                        borderImage.setRGB(x, y, pixel);
                    }else if(y==intHeight-1){ // last coloumn
                        borderImage.setRGB(x, y, pixel);
                    } else {
                        int pixelLeft = bufferedImage.getRGB(x-1, y);
                        int pixelRight = bufferedImage.getRGB(x+1, y);
                        int pixelUp = bufferedImage.getRGB(x, y-1);
                        int pixelDown = bufferedImage.getRGB(x, y+1);
                        if(pixel!=pixelLeft || pixel!=pixelRight || pixel!=pixelUp || pixel!=pixelDown)
                            borderImage.setRGB(x, y, pixel);
                    }
                }
            }
        }        
        return borderImage;
    }
    /**
     * getImageColorBorderSketch
     * <p>
     * This method is used for extracting distinct color border sketch from a image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for extracting distinct color border sketch from a image.
     * @see         java.awt.*;
     * @link        java.awt.image.BufferedImage
     * @link        com.mla.main.Logging
     * @param       bufferedImage BufferedImage
     * @return      borderImage BufferedImage border colors used in image
     */    
    public BufferedImage getImageColorBorderSketch(BufferedImage bufferedImage) {	  
        int intWidth = bufferedImage.getWidth();
        int intHeight = bufferedImage.getHeight();
        BufferedImage borderImage = new BufferedImage(intWidth, intHeight, BufferedImage.TYPE_INT_ARGB);
        for(int y = 0; y < intHeight; y++) {
            for(int x = 0; x < intWidth; x++) {
                int pixel = bufferedImage.getRGB(x, y);     
                if(x==0){ //first row
                    borderImage.setRGB(x, y, pixel);
                }else if(x==intWidth-1){ //last row
                    borderImage.setRGB(x, y, pixel);
                }else {
                    if(y==0){ // first coloumn
                        borderImage.setRGB(x, y, pixel);
                    }else if(y==intHeight-1){ // last coloumn
                        borderImage.setRGB(x, y, pixel);
                    } else {
                        int pixelLeft = bufferedImage.getRGB(x-1, y);
                        int pixelRight = bufferedImage.getRGB(x+1, y);
                        int pixelUp = bufferedImage.getRGB(x, y-1);
                        int pixelDown = bufferedImage.getRGB(x, y+1);
                        if(pixel!=pixelLeft || pixel!=pixelRight || pixel!=pixelUp || pixel!=pixelDown)
                            borderImage.setRGB(x, y, pixel);
                        else
                            borderImage.setRGB(x, y, -1);
                    }
                }
            }
        }
        return borderImage;
    }
    /**
     * getImageColorBorderOld
     * <p>
     * This method is used for extracting distinct color border from a image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for extracting distinct color border from a image.
     * @see         java.awt.*;
     * @link        java.awt.image.BufferedImage
     * @link        com.mla.main.Logging
     * @param       bufferedImage BufferedImage
     * @return      borderImage BufferedImage border colors used in image
     */    
    public BufferedImage getImageColorBorderOld(BufferedImage bufferedImage) {	  
        ArrayList<Color> lstColor = new ArrayList<Color>();
        int intWidth = bufferedImage.getWidth();
        int intHeight = bufferedImage.getHeight();
        
         //create the detector
        CannyEdgeDetector detector = new CannyEdgeDetector();
        //adjust its parameters as desired
        detector.setLowThreshold(0.5f);
        detector.setHighThreshold(1f);
        //apply it to an image
        detector.setSourceImage(bufferedImage);
        detector.process();
        BufferedImage borderImage = detector.getEdgesImage();
        //return borderImage;
        
        //added by me for coloured border
        for(int y = 0; y < intHeight; y++) {
            for(int x = 0; x < intWidth; x++) {
                int pixel = borderImage.getRGB(x, y);
                if(pixel>=-1)
                    borderImage.setRGB(x, y, bufferedImage.getRGB(x, y));
            }
        }        
        return borderImage;
    }
    /**
     * getMatrixColorBorder
     * <p>
     * This method is used for extracting distinct color border from a image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for extracting distinct color border from a image.
     * @see         java.awt.*;
     * @link        java.awt.image.BufferedImage
     * @link        com.mla.main.Logging
     * @param       bufferedImage BufferedImage
     * @return      borderImage BufferedImage border colors used in image
     */    
    public BufferedImage getMatrixColorBorder(BufferedImage bufferedImage) {	  
        int intWidth = bufferedImage.getWidth();
        int intHeight = bufferedImage.getHeight();
        byte[][] shapeMatrix=new byte[bufferedImage.getWidth()][bufferedImage.getHeight()];
        
        BufferedImage borderImage = new BufferedImage(intWidth, intHeight, bufferedImage.getType());
        
        for(int y = 0; y < intHeight; y++) {
            for(int x = 0; x < intWidth; x++) {
                int pixel = bufferedImage.getRGB(x, y);     
                if(x==0){ //first row
                    borderImage.setRGB(x, y, pixel);
                    shapeMatrix[y][x]=1;
                }else if(x==intWidth-1){ //last row
                    borderImage.setRGB(x, y, pixel);
                    shapeMatrix[y][x]=1;
                }else {
                    if(y==0){ // first coloumn
                        borderImage.setRGB(x, y, pixel);
                        shapeMatrix[y][x]=1;
                    }else if(y==intHeight-1){ // last coloumn
                        borderImage.setRGB(x, y, pixel);
                        shapeMatrix[y][x]=1;
                    } else {
                        int pixelLeft = bufferedImage.getRGB(x-1, y);
                        int pixelRight = bufferedImage.getRGB(x+1, y);
                        int pixelUp = bufferedImage.getRGB(x, y-1);
                        int pixelDown = bufferedImage.getRGB(x, y+1);
                        if(pixel!=pixelLeft || pixel!=pixelRight || pixel!=pixelUp || pixel!=pixelDown){
                            borderImage.setRGB(x, y, pixel);
                            shapeMatrix[y][x]=1;
                        }
                    }
                }
            }
        }        
        return borderImage;
    }
    /**
     * getArtworkBoundary
     * <p>
     * This method is used for extracting shape of a pixel color from a image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for extracting shape of a pixel color from a image.
     * @see         java.awt.*;
     * @link        java.awt.image.BufferedImage
     * @link        com.mla.main.Logging
     * @param       bufferedImage BufferedImage
     * @param       x Integer
     * @param       y Integer
     * @return      byte[] of image shape
     */    
    public static byte[][] getArtworkBoundary(int x, int y, BufferedImage bufferedImage){
        int originalColor=bufferedImage.getRGB(x, y);
        byte[][] shapeMatrix=new byte[bufferedImage.getWidth()][bufferedImage.getHeight()];
        boolean[][] traversed=new boolean[bufferedImage.getWidth()][bufferedImage.getHeight()];
        Queue<Point> q=new LinkedList<>();
        q.add(new Point(x, y));
        while(!q.isEmpty()){
            Point p=q.remove();
            if(floodFill(bufferedImage, traversed, shapeMatrix, p.x, p.y, originalColor)){
                q.add(new Point(p.x-1, p.y));
                q.add(new Point(p.x+1, p.y));
                q.add(new Point(p.x, p.y-1));
                q.add(new Point(p.x, p.y+1));
            }
        }
        return shapeMatrix;
    }    
    public static boolean floodFill(BufferedImage bufferedImage, boolean[][] pointTraversed, byte[][] shapeMatrix, int x, int y, int originalColor ){
        if(x<0||y<0||x>(bufferedImage.getWidth()-1)||y>(bufferedImage.getHeight()-1))
            return false;
        if(pointTraversed[x][y])
            return false;
        if(bufferedImage.getRGB(x, y)!=originalColor){
            // if want to have boundary only uncomment @1 and @2
            // @1 bufferedImage.setRGB(x, y, newColor);
            pointTraversed[x][y]=true;
            return false;
        }
        //bufferedImage.setRGB(x, y, /*@2 originalColor*/newColor);
        shapeMatrix[x][y]=1;
        pointTraversed[x][y]=true;
        return true;
    }    
    /**
     * reduceColors
     * <p>
     * This method is used for reducing image color from image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for reducing image color from image.
     * @see         java.awt.*;
     * @link        java.awt.image.BufferedImage
     * @link        java.awt.Image
     * @param       bufferedImage BufferedImage
     * @param       intMaxColor Integer number of color for reduced image 
     * @return      reducedImage BufferedImage field
     */
    public BufferedImage reduceColors(BufferedImage bufferedImage, int intMaxColor) {    
        int[] pixel = new int[bufferedImage.getHeight()*bufferedImage.getWidth()]; 
        for(int y = 0, z=-1; y < bufferedImage.getHeight(); y++) {
            for(int x = 0; x < bufferedImage.getWidth(); x++) {
                z=z+1;
                pixel[z] = bufferedImage.getRGB(x, y);
            }
        }        
        MedianCut mc = new MedianCut(pixel, bufferedImage.getWidth(), bufferedImage.getHeight());
        java.awt.Image image = mc.convert(intMaxColor);
        // Create a buffered image with transparency
        BufferedImage reducedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        // Draw the image on to the buffered image
        Graphics2D bGr = reducedImage.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();
        // Return the buffered image
        return reducedImage;
    }
    /**
     * rectifyImage
     * <p>
     * This method is used for reducing image color, size etc.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for reducing image color, size etc.
     * @see         java.awt.*;
     * @link        java.awt.image.BufferedImage
     * @link        java.awt.Image
     * @param       bufferedImage BufferedImage
     * @param       intColorLimit Integer number of color for reduced image 
     * @return      reducedImage BufferedImage field
     */
    public BufferedImage rectifyImage(BufferedImage bufferedImage, int intColorLimit){
        /*
        //5 MB = 5242880, 4 MB = 4194304, 3 MB = 3145728; 2 MB = 2097152; 1 MB = 1048576
        int intWidth = bufferedImage.getWidth();
        int intHeight = bufferedImage.getHeight();
        if(intWidth*intHeight>3145728){
            if(intHeight>intWidth){
                intHeight = (intHeight>1000)?1000:intHeight;
                intWidth = (int)intHeight*(bufferedImage.getWidth()/bufferedImage.getHeight());
            }else{
                intWidth = (intWidth>1000)?1000:intWidth;
                intHeight = (int)intHeight*(bufferedImage.getHeight()/bufferedImage.getWidth());
            }
            BufferedImage bufferedImageesize;
            bufferedImageesize = new BufferedImage(intWidth,intHeight,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImageesize.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intWidth, intHeight, null);
            g.dispose();
            bufferedImage = bufferedImageesize;
            bufferedImageesize = null;
        }
        */
        /*
        Image Compression    
                //Get image writers
		Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName("jpg");

		if (!imageWriters.hasNext())
			throw new IllegalStateException("Writers Not Found!!");

		ImageWriter imageWriter = (ImageWriter) imageWriters.next();
		ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
		imageWriter.setOutput(imageOutputStream);

		ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();

		//Set the compress quality metrics
		imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		imageWriteParam.setCompressionQuality(imageQuality);

		//Created image
		imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);

		// close all streams
		inputStream.close();
		outputStream.close();
		imageOutputStream.close();
		imageWriter.dispose();
            */
            if(getImageColor(bufferedImage).size()>intColorLimit){
               bufferedImage = reduceColors(bufferedImage,intColorLimit);
            }
            return bufferedImage;
        } 
    /**
     * quantize
     * <p>
     * This method is used for reducing image color from image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for reducing image color from image.
     * @link        java.awt.image.BufferedImage
     * @param       bufferedImage BufferedImage
     * @return      bufferedImage BufferedImage field
     */
    public BufferedImage quantize(BufferedImage bufferedImage) {
        int intWidth = bufferedImage.getWidth();
        int intHeight = bufferedImage.getHeight();
        for(int y = 0; y < intHeight; y++) {
            for(int x = 0; x < intWidth; x++) {
                int pixel = bufferedImage.getRGB(x, y);     
                int red   = (pixel & 0x00ff0000) >> 16;
                int green = (pixel & 0x0000ff00) >> 8;
                int blue  =  pixel & 0x000000ff;   
                int rgb = (int)(red & 0xE0 | (green & 0xE0)>>3 | (blue & 0xC0)>>6); 
                bufferedImage.setRGB(x, y, rgb);
            }
        }
        return bufferedImage;
    }
    /**
     * getImageBlackWhite
     * <p>
     * This method is used for converting colored image to black & white image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for converting colored image to black & white image.
     * @link        java.awt.image.BufferedImage
     * @param       bufferedImage BufferedImage
     * @return      imageBW BufferedImage field
     */
    public BufferedImage getImageBlackWhite(BufferedImage bufferedImage) {	  
        int intWidth = bufferedImage.getWidth();
        int intHeight = bufferedImage.getHeight();
        BufferedImage imageBW = new BufferedImage(intWidth, intHeight,BufferedImage.TYPE_INT_RGB);
        for(int y = 0; y < intHeight; y++) {
            for(int x = 0; x < intWidth; x++) {
                int pixel = bufferedImage.getRGB(x, y);     
                int red   = (pixel & 0x00ff0000) >> 16;
                int green = (pixel & 0x0000ff00) >> 8;
                int blue  =  pixel & 0x000000ff;  
                float avgcol = (red+green+blue)/3;
                if(avgcol<=125)
                    imageBW.setRGB(x, y, Color.BLACK.getRGB());
                else
                    imageBW.setRGB(x, y, Color.WHITE.getRGB());                             
            }
        }
        return imageBW;
    }
    /**
     * getImageGray
     * <p>
     * This method is used for converting colored image to gray scale image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for converting colored image to gray scale image.
     * @link        java.awt.image.BufferedImage
     * @param       bufferedImage BufferedImage
     * @return      imageGray BufferedImage field
     */
    public BufferedImage getImageGray(BufferedImage bufferedImage) throws IOException {	  
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();   
        BufferedImage imageGray = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                Color c = new Color(bufferedImage.getRGB(j, i));
                int red = (int)(c.getRed() * 0.299);
                int green = (int)(c.getGreen() * 0.587);
                int blue = (int)(c.getBlue() *0.114);
                Color newColor = new Color(red+green+blue,red+green+blue,red+green+blue);
                imageGray.setRGB(j,i,newColor.getRGB());
            }
        }
        //bufferedImage = null;
        //System.gc();
        return imageGray;
        /*
        ImageFilter filter = new GrayFilter(true, 50);  
        ImageProducer producer = new FilteredImageSource(bufferedImage.getSource(), filter);  
        java.awt.Image mage = Toolkit.getDefaultToolkit().createImage(producer);  
        BufferedImage imageGray = new BufferedImage(mage.getWidth(null), mage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        // Draw the image on to the buffered image
        Graphics2D bGr = imageGray.createGraphics();
        bGr.drawImage(mage, 0, 0, null);
        bGr.dispose();
        return imageGray;
        */
    }
    /**
     * getImageGray
     * <p>
     * This method is used for vertical mirroring a image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for vertical mirroring a image.
     * @link        java.awt.image.BufferedImage
     * @param       bufferedImage BufferedImage
     * @return      imageVM BufferedImage field
     */
    public BufferedImage getImageVerticalMirror(BufferedImage bufferedImage) {	  
        BufferedImage imageVM = new BufferedImage(bufferedImage.getWidth()*2, bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for(int y = 0; y < bufferedImage.getHeight(); y++){
            for(int lx = 0, rx = bufferedImage.getWidth()*2 - 1; lx < bufferedImage.getWidth(); lx++, rx--){
                //lx starts from the left side of the image
                //rx starts from the right side of the image
                //get source pixel value
                int p = bufferedImage.getRGB(lx, y);
                //set mirror image pixel value - both left and right
                imageVM.setRGB(lx, y, p);
                imageVM.setRGB(rx, y, p);
            }
        }
        return imageVM;
    }
    /**
     * getImageGray
     * <p>
     * This method is used for horizontal mirroring a image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for horizontal mirroring a image.
     * @link        java.awt.image.BufferedImage
     * @param       bufferedImage BufferedImage
     * @return      imageHM BufferedImage field
     */
    public BufferedImage getImageHorizontalMirror(BufferedImage bufferedImage) throws IOException {	  
        BufferedImage imageHM = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight()*2, BufferedImage.TYPE_INT_ARGB);
        for(int ty = 0, by=bufferedImage.getHeight()*2-1; ty < bufferedImage.getHeight(); ty++,by--){
            for(int x = 0; x < bufferedImage.getWidth(); x++){
                //ty starts from the top side of the image
                //bx starts from the bottom side of the image
                //get source pixel value
                int p = bufferedImage.getRGB(x, ty);
                //set mirror image pixel value - both left and right
                imageHM.setRGB(x, ty, p);
                imageHM.setRGB(x, by, p);
            }
        }
        return imageHM;
    }
    /**
     * getImageRotation
     * <p>
     * This method is used for horizontal mirroring a image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for horizontal mirroring a image.
     * @link        java.awt.image.BufferedImage
     * @param       bufferedImage BufferedImage
     * @param       rotation String type of rotation
     * @return      imageR BufferedImage field
     */
    public BufferedImage getImageRotation(BufferedImage bufferedImage, String rotation) {
        int intWidth = bufferedImage.getWidth();
        int intHeight = bufferedImage.getHeight();
        BufferedImage imageR = new BufferedImage(intHeight, intWidth, BufferedImage.TYPE_INT_RGB);
        double theta;
        switch (rotation) {
            case "CLOCKWISE":
                theta = Math.PI / 2;
                break;
            case "COUNTERCLOCKWISE":
                theta = -Math.PI / 2;
                break;
            default:
                throw new AssertionError();
        }
        AffineTransform xform = new AffineTransform();
        xform.translate(0.5*intHeight, 0.5*intWidth);
        xform.rotate(theta);
        xform.translate(-0.5*intWidth, -0.5*intHeight);
        Graphics2D g = (Graphics2D) imageR.createGraphics();
        g.drawImage(bufferedImage, xform, null);
        g.dispose();
        return imageR;
    }
    /**
     * getImageClockwiseRotateByAngle
     * <p>
     * This method is used for horizontal mirroring a image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for horizontal mirroring a image.
     * @link        java.awt.image.BufferedImage
     * @param       bufferedImage BufferedImage
     * @param       angle Integer angle of rotation
     * @return      imageR BufferedImage field
     */
    public BufferedImage getImageClockwiseRotateByAngle(BufferedImage bufferedImage, int angle) {	  
        double sin = Math.abs(Math.sin(Math.toRadians(angle))),
        cos = Math.abs(Math.cos(Math.toRadians(angle)));
        int intWidth = bufferedImage.getWidth(null);
        int intHeigth = bufferedImage.getHeight(null);        
        int intWidthNew = (int) Math.floor(intWidth*cos + intHeigth*sin);
        int intHeigthNew = (int) Math.floor(intHeigth*cos + intWidth*sin);
        BufferedImage imageR = new BufferedImage(intWidthNew, intHeigthNew, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = imageR.createGraphics();
        g.translate((intWidthNew-intWidth)/2, (intHeigthNew-intHeigth)/2);
        g.rotate(Math.toRadians(angle), intWidth/2, intHeigth/2);
        g.drawRenderedImage(bufferedImage, null);
        g.dispose();
        return imageR;
    }
    /**
     * getImageHue
     * <p>
     * This method is used for horizontal mirroring a image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for horizontal mirroring a image.
     * @link        java.awt.image.BufferedImage
     * @param       bufferedImage BufferedImage
     * @param       angle Integer angle of rotation
     * @return      imageR BufferedImage field
     */
    public BufferedImage getImageHue(BufferedImage bufferedImage, float hue) {	  
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        WritableRaster raster = bufferedImage.getRaster();
        BufferedImage imageHue = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        WritableRaster resRast = imageHue.getRaster();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(Color.HSBtoRGB(hue, 0.7f, 0.7f));
                int[] pixels = raster.getPixel(x, y, (int[]) null);
                pixels[0] = color.getRed();
                pixels[1] = color.getGreen();
                pixels[2] = color.getBlue();
                resRast.setPixel(x, y, pixels);
            }
        }
        return imageHue;
    }
    /**
     * getImageBrightness
     * <p>
     * This method is used for horizontal mirroring a image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for horizontal mirroring a image.
     * @link        java.awt.image.BufferedImage
     * @param       bufferedImage BufferedImage
     * @param       angle Integer angle of rotation
     * @return      imageR BufferedImage field
     */
    public BufferedImage getImageBrightness(BufferedImage bufferedImage, float brightness) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        WritableRaster raster = bufferedImage.getRaster();
        BufferedImage imageB = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        WritableRaster resRast = imageB.getRaster();
        RescaleOp op = new RescaleOp(brightness, 0, null);
        resRast = op.filter(raster, resRast);
        return imageB;
    }
    /**
     * getImageRepeat
     * <p>
     * This method is used for horizontal & vertical repeats of a image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for horizontal & vertical repeats of a image.
     * @link        java.awt.image.BufferedImage
     * @param       bufferedImage BufferedImage
     * @param       VRepeat Integer vertical repeat number
     * @param       HRepeat Integer horizontal repeat number
     * @return      imageR BufferedImage field
     */
    public BufferedImage getImageRepeat(BufferedImage bufferedImage, int VRepeat, int HRepeat) {
        BufferedImage imageR = new BufferedImage(bufferedImage.getWidth()*HRepeat, bufferedImage.getHeight()*VRepeat, BufferedImage.TYPE_INT_ARGB);
        int pixcel = 0;
        for(int i = 0; i < bufferedImage.getHeight()*VRepeat; i++) {
            for(int j = 0; j < bufferedImage.getWidth()*HRepeat; j++) {
                if(i>=bufferedImage.getHeight() && j<bufferedImage.getWidth()){
                    pixcel = bufferedImage.getRGB(j, i%bufferedImage.getHeight());
                }else if(i<bufferedImage.getHeight() && j>=bufferedImage.getWidth()){
                    pixcel = bufferedImage.getRGB(j%bufferedImage.getWidth(), i);
                }else if(i>=bufferedImage.getHeight() && j>=bufferedImage.getWidth()){
                    pixcel = bufferedImage.getRGB(j%bufferedImage.getWidth(), i%bufferedImage.getHeight());
                }else{
                    pixcel = bufferedImage.getRGB(j, i);
                } 
                imageR.setRGB(j, i, pixcel);
            }
        }
        return imageR;
    }
    
    /**
     * getImageGray
     * <p>
     * This method is used for horizontal mirroring a image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for horizontal mirroring a image.
     * @link        java.awt.image.BufferedImage
     * @param       bufferedImage BufferedImage
     * @return      imageHM BufferedImage field
     */
    public BufferedImage getImageTransparent(BufferedImage bufferedImage, float dblOpacity ) {	  
        BufferedImage imageT = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) imageT.getGraphics();
        g2d.setComposite(AlphaComposite.SrcOver.derive(dblOpacity)); 
        // set the transparency level in range 0.0f - 1.0f 
        g2d.drawImage(bufferedImage, 0, 0, null);
        return imageT;
    }
    /**
     * getImageToMatrix
     * <p>
     * This method is used for converting image to integer matrix.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for converting image to integer matrix.
     * @link        java.awt.image.BufferedImage
     * @param       bufferedImage BufferedImage
     * @return      imageMatrix Integer Array field
     */
    public int[][] getImageToMatrix(BufferedImage bufferedImage) {	  
        int intWidth = bufferedImage.getWidth();
        int intHeight = bufferedImage.getHeight();        
        int[][] imageMatrix = new int[intHeight][intWidth];        
        // Get color list
        ArrayList<Color> lstColor = getImageColor(bufferedImage);
        // create image matrix        
        for(int y = 0; y < intHeight; y++) {
            for(int x = 0; x < intWidth; x++) {
                int pixel = bufferedImage.getRGB(x, y);     
                int red   = (pixel & 0x00ff0000) >> 16;
                int green = (pixel & 0x0000ff00) >> 8;
                int blue  =  pixel & 0x000000ff;                    
                Color color = new Color(red,green,blue);     
                for(int z=0; z<lstColor.size(); z++){
                    if(lstColor.get(z).equals(color)){
                        imageMatrix[y][x] = z;                           
                    }
                }
            }
        }
        return imageMatrix;
    }
    /**
     * getImageFromMatrix
     * <p>
     * This method is used for converting integer matrix to image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for converting integer matrix to image.
     * @link        java.awt.image.BufferedImage
     * @param       imageMatrix Integer Array
     * @param       lstColor ArrayList
     * @return      bufferedImage BufferedImage field
     */
    public BufferedImage getImageFromMatrix(int[][] imageMatrix, ArrayList<Color> lstColor) {	  
        int intWidth = imageMatrix[0].length;
        int intHeight = imageMatrix.length;
        BufferedImage bufferedImage = new BufferedImage(intWidth, intHeight, BufferedImage.TYPE_INT_RGB);
        //create image from integer matrix and color list
        for(int y = 0; y < intHeight; y++) {
            for(int x = 0; x < intWidth; x++) {                                   
                for(int z=0; z<lstColor.size(); z++){
                    if(imageMatrix[y][x] == z){
                       bufferedImage.setRGB(x, y, lstColor.get(z).getRGB()); 
                    }
                }
            }
        }
        return bufferedImage;
    }
    /**
     * invertMatrix
     * <p>
     * This method is used for inverting matrix.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for inverting matrix.
     * @param       byteMatrix byte Array
     * @return      byteMatrix byte Array
     */
    public byte[][] invertMatrix(byte[][] byteMatrix){
        int intHeight=(int)byteMatrix.length;
        int intWidth=(int)byteMatrix[0].length;
        for(int i = 0; i < intHeight; i++) {
            for(int j = 0; j < intWidth; j++) {
                if(byteMatrix[i][j]==1)
                    byteMatrix[i][j] = 0; 
                else if(byteMatrix[i][j]==0)
                    byteMatrix[i][j] = 1;
            }
        }
        return byteMatrix;
    }
    /**
     * repeatMatrix
     * <p>
     * This method is used for repeating matrix.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for repeating matrix.
     * @param       byteMatrix byte Array
     * @param       HEIGHT Integer
     * @param       WIDTH Integer
     * @return      repeatMatrix byte Array
     */
    public byte[][] repeatMatrix(byte[][] byteMatrix, int HEIGHT, int WIDTH){
        int intHeight=(int)byteMatrix.length;
        int intWidth=(int)byteMatrix[0].length;
        byte[][] repeatMatrix = new byte[HEIGHT][WIDTH];
        for(int i = 0; i < HEIGHT; i++) {
            for(int j = 0; j < WIDTH; j++) {
                if(i>=intHeight && j<intWidth){
                     repeatMatrix[i][j] = byteMatrix[i%intHeight][j];  
                }else if(i<intHeight && j>=intWidth){
                     repeatMatrix[i][j] = byteMatrix[i][j%intWidth];  
                }else if(i>=intHeight && j>=intWidth){
                     repeatMatrix[i][j] = byteMatrix[i%intHeight][j%intWidth];  
                }else{
                     repeatMatrix[i][j] = byteMatrix[i][j]; 
                }                
            }
        } 
        byteMatrix = null;
        HEIGHT = WIDTH = intHeight = intWidth = 0;
        return repeatMatrix;
    }
    /**
     * designRepeat
     * <p>
     * This method is used for repeating image.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for repeating image.
     * @param       bufferedImage BufferedImage
     * @param       vAlign Integer
     * @param       hAling Integer
     * @param       vRepeat Integer
     * @param       hRepeat Integer
     * @return      bufferedImage BufferedImage
     */
    public BufferedImage designRepeat(BufferedImage bufferedImage, int vAlign, int hAling, int vRepeat, int hRepeat){
        try {
            int intLength = bufferedImage.getWidth();
            int intHeight = bufferedImage.getHeight();
            // get image resize
            BufferedImage texture = new BufferedImage(intLength, intHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = texture.createGraphics();
            g.drawImage(bufferedImage, 0, 0, intLength, intHeight, null);
            g.dispose();
            // get image to matrix
            int [][] rgbMatrix=new int[intLength][intHeight];
            for(int x=0; x<intLength; x++){
                for(int y=0; y<intHeight; y++){
                    rgbMatrix[x][y]=texture.getRGB(x, y);
                }
            }
            //orentation and alingment
            int WIDTH=intLength*vAlign;
            int HEIGHT=intHeight*hAling;
            int[][] bufferRGBN=new int[WIDTH][HEIGHT];
            if(vAlign < hAling){// 1/2 1/3 1/4 1/5 1/6
                WIDTH=intLength*(1+(int)(vAlign/hAling));
                HEIGHT=intHeight*hAling;
                bufferRGBN=new int[WIDTH][HEIGHT];
                int ny=0;
                for(double z=0; z<1; z=z+((double)vAlign/hAling)){
                    for(int y=0; y<intHeight; y++,ny++){
                        for(int x=0; x<WIDTH; x++){
                            bufferRGBN[x%WIDTH][ny%HEIGHT]=rgbMatrix[(x+(int)(intLength*z))%intLength][y%intHeight];
                        }
                    }
                }   
            }else if(vAlign > hAling){ // 2/1 3/1 4/1 5/1 6/1
                WIDTH=intLength*vAlign;
                HEIGHT=intHeight*(1+(int)(hAling/vAlign));
                bufferRGBN=new int[WIDTH][HEIGHT];
                int nx=0;
                for(double z=0; z<1; z=z+((double)hAling/vAlign)){
                    for(int x=0; x<intLength; x++,nx++){
                        for(int y=0; y<HEIGHT; y++){                    
                            bufferRGBN[nx%WIDTH][y%HEIGHT]=rgbMatrix[x%intLength][(y+(int)(intHeight*z))%intHeight];
                        }
                    }
                }   
            }else{// 1/1
                for(int x=0; x<WIDTH; x++){
                    for(int y=0; y<HEIGHT; y++){
                        bufferRGBN[x%WIDTH][y%HEIGHT]=rgbMatrix[x%intLength][y%intHeight];
                    }
                }
            }
            //repeat matrix
            intLength = bufferedImage.getWidth()*hRepeat;
            intHeight = bufferedImage.getHeight()*vRepeat;
            rgbMatrix=new int[intLength][intHeight];
            for(int i = 0; i < intLength; i++) {
                for(int j = 0; j < intHeight; j++) {
                    if(i>=WIDTH && j<HEIGHT){
                        rgbMatrix[i][j] = bufferRGBN[i%WIDTH][j];
                    }else if(i<WIDTH && j>=HEIGHT){
                        rgbMatrix[i][j] = bufferRGBN[i][j%HEIGHT];
                    }else if(i>=WIDTH && j>=HEIGHT){
                        rgbMatrix[i][j] = bufferRGBN[i%WIDTH][j%HEIGHT];
                    }else{
                        rgbMatrix[i][j] = bufferRGBN[i][j];
                    }
                }
            }
            //get image from matrix
            bufferedImage = new BufferedImage(intLength,intHeight,BufferedImage.TYPE_INT_RGB);
            for(int x=0; x<intLength; x++){
                for(int y=0; y<intHeight; y++){
                    bufferedImage.setRGB(x, y,rgbMatrix[x][y]);
                }                
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ArtworkAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bufferedImage;
    }    
}