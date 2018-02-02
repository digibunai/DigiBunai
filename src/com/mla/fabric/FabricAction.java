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
package com.mla.fabric;

import com.mla.colour.Colour;
import com.mla.main.Configuration;
import com.mla.main.DbConnect;
import com.mla.main.IDGenerator;
import com.mla.main.Logging;
import com.mla.yarn.Yarn;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
/**
 * FabricAction Class
 * <p>
 * This class is used for defining model methods for fabric properties.
 *
 * @author Amit Kumar Singh
 * @version %I%, %G%
 * @since   1.0
 * @date 07/01/2016
 * @Designing model method class for fabric
 * @see java.sql.*;
 * @link com.mla.main.DbConnect
 */
public class FabricAction {   
    Connection connection = null; //DbConnect.getConnection();
    Fabric objFabric;
    
    /**
     * FabricAction
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
    public FabricAction() throws SQLException{
        connection = DbConnect.getConnection();
    }
    /**
     * Fabric Class
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
    public FabricAction(boolean isDB) throws SQLException{
        if(isDB)
            connection = DbConnect.getConnection();        
    }
    /**
     * Fabric Class
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
     * @param       objFabricCall Fabric object
     * @param       isDB boolean <code>true</code> if need to get access database connection
     *              <code>false</code> otherwise.
     */
    public FabricAction(Fabric objFabricCall, boolean isDB) throws SQLException{
        if(isDB)
            connection = DbConnect.getConnection();
        //objFabric = objFabricCall;
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
     * clearFabric
     * <p>
     * This method is used for deleting fabric from library.
     *
     * @author      Amit Kumar Singh
     * @version     %I%, %G%
     * @since       1.0
     * @date        07/01/2016
     * @Designing   method is used for deleting fabric from library.
     * @see         java.sql.*;
     * @link        com.mla.main.DbConnect
     * @link        com.mla.main.Logging
     * @exception   Exception
     * @param       strFabricID String Fabric unique id
     * @return      oResult boolean field <code>true</code> if the fabric is removed 
     *              <code>false</code> otherwise.
     */
    public boolean clearFabric(String strFabricID) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        boolean oResult= false;
        String strQuery=null;
        new Logging("INFO",FabricAction.class.getName(),"<<<<<<<<<<< clearFabric() >>>>>>>>>>>",null);
        try {           
            strQuery = "DELETE FROM mla_fabric_library WHERE `ID` = ?;";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, strFabricID);
            oResult = oPreparedStatement.execute();           
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"clearFabric() : "+strQuery,ex);
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
                    new Logging("SEVERE",FabricAction.class.getName(),"clearFabric() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",FabricAction.class.getName(),">>>>>>>>>>> clearFabric() <<<<<<<<<<<"+oResult,null);
        return oResult;
    }
    /**
    * lstImportFabric
    * <p>
    * This method is used for accessing fabric from library based on conditions.
    *
    * @author      Amit Kumar Singh
    * @version     %I%, %G%
    * @since       1.0
    * @date        07/01/2016
    * @Designing   method is used for accessing fabric from library.
    * @see         java.sql.*;
    * @link        com.mla.main.DbConnect
    * @link        com.mla.main.Logging
    * @exception   Exception
    * @param       objFabric Object Fabric
    * @return      lstFabricDeatails List field of fabric
    *              <code>null</code> otherwise.
    */
    public List lstImportFabric(Fabric objFabric) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        List lstFabricDeatails=null, lstFabric;
        new Logging("INFO",FabricAction.class.getName(),"<<<<<<<<<<< lstImportFabric() >>>>>>>>>>>",null);
        try {
            String cond = "(USERID = '"+objFabric.getObjConfiguration().getObjUser().getStrUserID()+"' OR `ACCESS`='"+new IDGenerator().getUserAcess("FABRIC_LIBRARY")+"') ";
            String orderBy ="NAME ";
            if(!(objFabric.getStrCondition().trim().equals(""))) {
                cond += " AND `NAME` LIKE '"+objFabric.getStrCondition().trim()+"%'";
            }
            if(!(objFabric.getStrSearchBy().trim().equals("")) && !(objFabric.getStrSearchBy().trim().equalsIgnoreCase("All Cloth Type"))) {
                cond += " AND `CLOTHTYPE` LIKE '"+objFabric.getStrSearchBy().trim()+"%'";
            }
            if(objFabric.getStrOrderBy().equals("Name")) {
                orderBy = "`NAME`";
            } else if(objFabric.getStrOrderBy().equals("Date")) {
                orderBy = "`UDATE`";
            }
            if(objFabric.getStrDirection().equals("Ascending")) {
                orderBy += " ASC";
            } else if(objFabric.getStrDirection().equals("Descending")) {
                orderBy += " DESC";
            }
            strQuery = "select * from `mla_fabric_library` WHERE "+cond+" ORDER BY "+orderBy;
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            lstFabricDeatails = new ArrayList();
            while(oResultSet.next()) {
                lstFabric = new ArrayList();
                lstFabric.add(oResultSet.getString("ID").toString());
                lstFabric.add(oResultSet.getString("NAME").toString());
                lstFabric.add(oResultSet.getString("CLOTHTYPE").toString());
                lstFabric.add(oResultSet.getString("TYPE").toString());
                lstFabric.add(oResultSet.getDouble("LENGTH"));
                lstFabric.add(oResultSet.getDouble("WIDTH"));
                lstFabric.add(oResultSet.getDouble("ARTWORKLENGTH"));
                lstFabric.add(oResultSet.getDouble("ARTWORKWIDTH"));
                lstFabric.add(oResultSet.getString("ARTWORKID"));
                lstFabric.add(oResultSet.getString("BASEWEAVEID").toString());
                lstFabric.add(oResultSet.getInt("WEFT"));
                lstFabric.add(oResultSet.getInt("WARP"));
                lstFabric.add(oResultSet.getInt("EWEFT"));
                lstFabric.add(oResultSet.getInt("EWARP"));
                lstFabric.add(oResultSet.getInt("SHAFT"));
                lstFabric.add(oResultSet.getInt("HOOKS"));
                lstFabric.add(oResultSet.getInt("HPI"));
                lstFabric.add(oResultSet.getInt("REEDCOUNT"));
                lstFabric.add(oResultSet.getInt("DENTS"));
                lstFabric.add(oResultSet.getInt("TPD"));
                lstFabric.add(oResultSet.getInt("EPI"));
                lstFabric.add(oResultSet.getInt("PPI"));
                lstFabric.add(oResultSet.getInt("PROTECTION"));
                lstFabric.add(oResultSet.getInt("BINDING"));
                lstFabric.add(oResultSet.getString("WARPPATTERNID").toString());
                lstFabric.add(oResultSet.getString("WEFTPATTERNID").toString());
                lstFabric.add(oResultSet.getBytes("ICON"));
                lstFabric.add(oResultSet.getString("FILE").toString());
                lstFabric.add(oResultSet.getString("RDATA").toString());
                lstFabric.add(new IDGenerator().getUserAcessValueData("FABRIC_LIBRARY",oResultSet.getString("ACCESS")));
                lstFabric.add(oResultSet.getTimestamp("UDATE").toString());
                lstFabric.add(oResultSet.getString("USERID").toString());
                lstFabricDeatails.add(lstFabric);
            }
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"lstImportFabricDetails : "+strQuery,ex);
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
                    new Logging("SEVERE",FabricAction.class.getName(),"lstImportFabric() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",FabricAction.class.getName(),">>>>>>>>>>> lstImportFabric() <<<<<<<<<<<"+strQuery,null);
        return lstFabricDeatails;
    }
    
    public void getFabric(Fabric objFabric) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        new Logging("INFO",FabricAction.class.getName(),"<<<<<<<<<<< getFabric() >>>>>>>>>>>",null);
        try {           
            String cond = " 1";
            if(objFabric.getStrFabricID()!="" && objFabric.getStrFabricID()!=null)
                cond += " AND ID='"+objFabric.getStrFabricID()+"'";
            strQuery = "select * from mla_fabric_library WHERE "+cond+" LIMIT 1;";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);  
            if(oResultSet.next()){  
				objFabric.setStrFabricID(oResultSet.getString("ID").toString());
				objFabric.setStrFabricName(oResultSet.getString("NAME").toString());
				objFabric.setStrClothType(oResultSet.getString("CLOTHTYPE").toString());
				objFabric.setStrFabricType(oResultSet.getString("TYPE").toString());
				objFabric.setDblFabricLength(oResultSet.getFloat("LENGTH"));
				objFabric.setDblFabricWidth(oResultSet.getFloat("WIDTH"));
				objFabric.setDblArtworkLength(oResultSet.getFloat("ARTWORKLENGTH"));
				objFabric.setDblArtworkWidth(oResultSet.getFloat("ARTWORKWIDTH"));
				objFabric.setStrArtworkID(oResultSet.getString("ARTWORKID"));
				objFabric.setStrBaseWeaveID(oResultSet.getString("BASEWEAVEID").toString());
				objFabric.setIntWeft(oResultSet.getInt("WEFT"));
				objFabric.setIntWarp(oResultSet.getInt("WARP"));
				objFabric.setIntExtraWeft(oResultSet.getInt("EWEFT"));
				objFabric.setIntExtraWarp(oResultSet.getInt("EWARP"));
				objFabric.setIntShaft(oResultSet.getInt("SHAFT"));
				objFabric.setIntHooks(oResultSet.getInt("HOOKS"));
				objFabric.setIntHPI(oResultSet.getInt("HPI"));
				objFabric.setIntReedCount(oResultSet.getInt("REEDCOUNT"));
				objFabric.setIntDents(oResultSet.getInt("DENTS"));
				objFabric.setIntTPD(oResultSet.getInt("TPD"));
				objFabric.setIntEPI(oResultSet.getInt("EPI"));
				objFabric.setIntPPI(oResultSet.getInt("PPI"));
				objFabric.setIntProtection(oResultSet.getInt("PROTECTION"));
				objFabric.setIntBinding(oResultSet.getInt("BINDING"));
				objFabric.setBlnArtworkAssingmentSize(oResultSet.getBoolean("ARTWORKSIZE"));
				objFabric.setBlnArtworkOutline(oResultSet.getBoolean("ARTWORKOUTLINE"));
				objFabric.setBlnArtworkAspectRatio(oResultSet.getBoolean("ARTWORKASPECTRATIO"));
				objFabric.setStrWarpPatternID(oResultSet.getString("WARPPATTERNID").toString());
				objFabric.setStrWeftPatternID(oResultSet.getString("WEFTPATTERNID").toString());
				objFabric.setStrFabricFile(oResultSet.getString("FILE"));
				objFabric.setStrFabricRData(oResultSet.getString("RDATA"));
				objFabric.setBytFabricIcon(oResultSet.getBytes("ICON"));
				objFabric.setStrFabricDate(oResultSet.getTimestamp("UDATE").toString());
				objFabric.setStrFabricAccess(new IDGenerator().getUserAcessValueData("FABRIC_LIBRARY",oResultSet.getString("ACCESS")));
				readFabric(objFabric,(byte)0);
				readFabric(objFabric,(byte)1);
			}
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"getFabric : "+strQuery,ex);
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
                    new Logging("SEVERE",FabricAction.class.getName(),"getFabric() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",FabricAction.class.getName(),">>>>>>>>>>> getFabric() <<<<<<<<<<<"+strQuery,null);
        return;
    }
    
    public byte setFabric(Fabric objFabric) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        new Logging("INFO",FabricAction.class.getName(),"<<<<<<<<<<< setArtwork() >>>>>>>>>>>",null);
        try {           
            System.out.println("<<<<<< getFabric >>>>>>");
            strQuery = "INSERT INTO `mla_fabric_library` (`ID`, `NAME`, `CLOTHTYPE`, `TYPE`, `LENGTH`, `WIDTH`, `ARTWORKLENGTH`, `ARTWORKWIDTH`, `ARTWORKID`, `BASEWEAVEID`, `WEFT`, `WARP`, `EWEFT`, `EWARP`, `SHAFT`, `HOOKS`, `HPI`, `REEDCOUNT`, `DENTS`, `TPD`, `EPI`, `PPI`, `PROTECTION`, `BINDING`, `WARPPATTERNID`, `WEFTPATTERNID`, `FILE`, `RDATA`, `ICON`, `USERID`, `ACCESS`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            oPreparedStatement = connection.prepareStatement(strQuery);
            writeFabric(objFabric,(byte)0);
            writeFabric(objFabric,(byte)1);
            oPreparedStatement.setString(1, objFabric.getStrFabricID());
            oPreparedStatement.setString(2, objFabric.getStrFabricName());
            oPreparedStatement.setString(3, objFabric.getStrClothType());
            oPreparedStatement.setString(4, objFabric.getStrFabricType());
            oPreparedStatement.setDouble(5, objFabric.getDblFabricLength());
            oPreparedStatement.setDouble(6, objFabric.getDblFabricWidth());
            oPreparedStatement.setDouble(7, objFabric.getDblArtworkLength());
            oPreparedStatement.setDouble(8, objFabric.getDblArtworkWidth());
            oPreparedStatement.setString(9, objFabric.getStrArtworkID());
            oPreparedStatement.setString(10, objFabric.getStrBaseWeaveID());
            oPreparedStatement.setInt(11, objFabric.getIntWeft());
            oPreparedStatement.setInt(12, objFabric.getIntWarp());
            oPreparedStatement.setInt(13, objFabric.getIntExtraWeft());
            oPreparedStatement.setInt(14, objFabric.getIntExtraWarp());
            oPreparedStatement.setInt(15, objFabric.getIntShaft());
            oPreparedStatement.setInt(16, objFabric.getIntHooks());
            oPreparedStatement.setInt(17, objFabric.getIntHPI());
            oPreparedStatement.setInt(18, objFabric.getIntReedCount());
            oPreparedStatement.setInt(19, objFabric.getIntDents());
            oPreparedStatement.setInt(20, objFabric.getIntTPD());
            oPreparedStatement.setInt(21, objFabric.getIntEPI());
            oPreparedStatement.setInt(22, objFabric.getIntPPI());
            oPreparedStatement.setInt(23, objFabric.getIntProtection());
            oPreparedStatement.setInt(24, objFabric.getIntBinding());
            oPreparedStatement.setString(25, objFabric.getStrWarpPatternID());
            oPreparedStatement.setString(26, objFabric.getStrWeftPatternID());
            oPreparedStatement.setString(27,objFabric.getStrFabricFile());
            oPreparedStatement.setString(28,objFabric.getStrFabricRData());
            oPreparedStatement.setBinaryStream(29,new ByteArrayInputStream(objFabric.getBytFabricIcon()),objFabric.getBytFabricIcon().length);
            oPreparedStatement.setString(30, objFabric.getObjConfiguration().getObjUser().getStrUserID());
            oPreparedStatement.setString(31, new IDGenerator().setUserAcessValueData("FABRIC_LIBRARY",objFabric.getStrFabricAccess()));
            oResult = (byte)oPreparedStatement.executeUpdate();              
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"setFabric : "+strQuery,ex);
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
                    new Logging("SEVERE",FabricAction.class.getName(),"setFabric() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",FabricAction.class.getName(),">>>>>>>>>>> setFabric() <<<<<<<<<<<"+strQuery,null);
        return oResult;
    }
    
    public byte resetFabricPermission(String strFabricID, String strAccess) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        new Logging("INFO",FabricAction.class.getName(),"<<<<<<<<<<< resetFabricPermission() >>>>>>>>>>>",null);
        try {
            strQuery = "UPDATE `mla_fabric_library` SET `ACCESS` = ? WHERE `ID`= ?;";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, strAccess);
            oPreparedStatement.setString(2, strFabricID);
            oResult = (byte)oPreparedStatement.executeUpdate();              
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"resetFabricPermission : "+strQuery,ex);
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
                    new Logging("SEVERE",FabricAction.class.getName(),"resetFabricPermission() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",FabricAction.class.getName(),">>>>>>>>>>> resetFabricPermission() <<<<<<<<<<<"+strQuery,null);
        return oResult;
    }
    
    public byte resetFabric(Fabric objFabric) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        new Logging("INFO",FabricAction.class.getName(),"<<<<<<<<<<< resetFabric() >>>>>>>>>>>",null);
        try {           
            strQuery = "UPDATE `mla_fabric_library` SET `ID` = ?, `NAME` = ?, `CLOTHTYPE` = ?, `TYPE` = ?, `LENGTH` = ?, `WIDTH` = ?, `ARTWORKLENGTH` = ?, `ARTWORKWIDTH` = ?, `ARTWORKID` = ?, `BASEWEAVEID` = ?, `WEFT` = ?, `WARP` = ?, `EWEFT` = ?, `EWARP` = ?, `SHAFT` = ?, `HOOKS` = ?, `HPI` = ?, `REEDCOUNT` = ?, `DENTS` = ?, `TPD` = ?, `EPI` = ?, `PPI` = ?, `PROTECTION` = ?, `BINDING` = ?, `WARPPATTERNID` = ?, `WEFTPATTERNID` = ?, `FILE` = ?, `RDATA` = ?, `ICON` = ?, `ACCESS` = ? WHERE `USERID` = ? AND `ID`= ?;";
            oPreparedStatement = connection.prepareStatement(strQuery);
            writeFabric(objFabric,(byte)0);
            writeFabric(objFabric,(byte)1);
            oPreparedStatement.setString(1, objFabric.getStrFabricID());
            oPreparedStatement.setString(2, objFabric.getStrFabricName());
            oPreparedStatement.setString(3, objFabric.getStrClothType());
            oPreparedStatement.setString(4, objFabric.getStrFabricType());
            oPreparedStatement.setDouble(5, objFabric.getDblFabricLength());
            oPreparedStatement.setDouble(6, objFabric.getDblFabricWidth());
            oPreparedStatement.setDouble(7, objFabric.getDblArtworkLength());
            oPreparedStatement.setDouble(8, objFabric.getDblArtworkWidth());
            oPreparedStatement.setString(9, objFabric.getStrArtworkID());
            oPreparedStatement.setString(10, objFabric.getStrBaseWeaveID());
            oPreparedStatement.setInt(11, objFabric.getIntWeft());
            oPreparedStatement.setInt(12, objFabric.getIntWarp());
            oPreparedStatement.setInt(13, objFabric.getIntExtraWeft());
            oPreparedStatement.setInt(14, objFabric.getIntExtraWarp());
            oPreparedStatement.setInt(15, objFabric.getIntShaft());
            oPreparedStatement.setInt(16, objFabric.getIntHooks());
            oPreparedStatement.setInt(17, objFabric.getIntHPI());
            oPreparedStatement.setInt(18, objFabric.getIntReedCount());
            oPreparedStatement.setInt(19, objFabric.getIntDents());
            oPreparedStatement.setInt(20, objFabric.getIntTPD());
            oPreparedStatement.setInt(21, objFabric.getIntEPI());
            oPreparedStatement.setInt(22, objFabric.getIntPPI());
            oPreparedStatement.setInt(23, objFabric.getIntProtection());
            oPreparedStatement.setInt(24, objFabric.getIntBinding());
            oPreparedStatement.setString(25, objFabric.getStrWarpPatternID());
            oPreparedStatement.setString(26, objFabric.getStrWeftPatternID());
            oPreparedStatement.setString(27,objFabric.getStrFabricFile());
            oPreparedStatement.setString(28,objFabric.getStrFabricRData());
            oPreparedStatement.setBinaryStream(29,new ByteArrayInputStream(objFabric.getBytFabricIcon()),objFabric.getBytFabricIcon().length);
            oPreparedStatement.setString(30, new IDGenerator().setUserAcessValueData("FABRIC_LIBRARY",objFabric.getStrFabricAccess()));
            oPreparedStatement.setString(31, objFabric.getObjConfiguration().getObjUser().getStrUserID());
            oPreparedStatement.setString(32, objFabric.getStrFabricID());
            oResult = (byte)oPreparedStatement.executeUpdate();              
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"resetFabric : "+strQuery,ex);
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
                    new Logging("SEVERE",FabricAction.class.getName(),"resetFabric() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",FabricAction.class.getName(),">>>>>>>>>>> resetFabric() <<<<<<<<<<<"+strQuery,null);
        return oResult;
    }
    
/*===========================================================================*/
    
    public String[] getFabricPallets(Fabric objFabric) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String[] threadPaletes=null;
        try {           
            System.out.println("<<<<<< getFabricPallets >>>>>>");
            strQuery = "select * from mla_fabric_pallets WHERE fabric_id='"+objFabric.getStrFabricID()+"';";
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
				objFabric.setThreadPaletes(threadPaletes);
			}
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"getFabricPallets : "+strQuery,ex);
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
                    new Logging("SEVERE",FabricAction.class.getName(),"getFabricPallets() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< getFabricPallets >>>>>>");
        return threadPaletes;
    }
    
    public byte setFabricPallets(Fabric objFabric) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        try {           
            System.out.println("<<<<<< setFabricPallets >>>>>>");
            strQuery = "INSERT INTO `mla_fabric_pallets` (`fabric_id`, `warp_A`, `warp_B`, `warp_C`, `warp_D`, `warp_E`, `warp_F`, `warp_G`, `warp_H`, `warp_I`, `warp_J`, `warp_K`, `warp_L`, `warp_M`, `warp_N`, `warp_O`, `warp_P`, `warp_Q`, `warp_R`, `warp_S`, `warp_T`, `warp_U`, `warp_V`, `warp_W`, `warp_X`, `warp_Y`, `warp_Z`, `weft_a`, `weft_b`, `weft_c`, `weft_d`, `weft_e`, `weft_f`, `weft_g`, `weft_h`, `weft_i`, `weft_j`, `weft_k`, `weft_l`, `weft_m`, `weft_n`, `weft_o`, `weft_p`, `weft_q`, `weft_r`, `weft_s`, `weft_t`, `weft_u`, `weft_v`, `weft_w`, `weft_x`, `weft_y`, `weft_z`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, objFabric.getStrFabricID());
            oPreparedStatement.setString(2, objFabric.getThreadPaletes()[0]);
            oPreparedStatement.setString(3, objFabric.getThreadPaletes()[1]);
            oPreparedStatement.setString(4, objFabric.getThreadPaletes()[2]);
            oPreparedStatement.setString(5, objFabric.getThreadPaletes()[3]);
            oPreparedStatement.setString(6, objFabric.getThreadPaletes()[4]);
            oPreparedStatement.setString(7, objFabric.getThreadPaletes()[5]);
            oPreparedStatement.setString(8, objFabric.getThreadPaletes()[6]);
            oPreparedStatement.setString(9, objFabric.getThreadPaletes()[7]);
            oPreparedStatement.setString(10, objFabric.getThreadPaletes()[8]);
            oPreparedStatement.setString(11, objFabric.getThreadPaletes()[9]);
            oPreparedStatement.setString(12, objFabric.getThreadPaletes()[10]);
            oPreparedStatement.setString(13, objFabric.getThreadPaletes()[11]);
            oPreparedStatement.setString(14, objFabric.getThreadPaletes()[12]);
            oPreparedStatement.setString(15, objFabric.getThreadPaletes()[13]);
            oPreparedStatement.setString(16, objFabric.getThreadPaletes()[14]);
            oPreparedStatement.setString(17, objFabric.getThreadPaletes()[15]);
            oPreparedStatement.setString(18, objFabric.getThreadPaletes()[16]);
            oPreparedStatement.setString(19, objFabric.getThreadPaletes()[17]);
            oPreparedStatement.setString(20, objFabric.getThreadPaletes()[18]);
            oPreparedStatement.setString(21, objFabric.getThreadPaletes()[19]);
            oPreparedStatement.setString(22, objFabric.getThreadPaletes()[20]);
            oPreparedStatement.setString(23, objFabric.getThreadPaletes()[21]);
            oPreparedStatement.setString(24, objFabric.getThreadPaletes()[22]);
            oPreparedStatement.setString(25, objFabric.getThreadPaletes()[23]);
            oPreparedStatement.setString(26, objFabric.getThreadPaletes()[24]);
            oPreparedStatement.setString(27, objFabric.getThreadPaletes()[25]);
            oPreparedStatement.setString(28, objFabric.getThreadPaletes()[26]);
            oPreparedStatement.setString(29, objFabric.getThreadPaletes()[27]);
            oPreparedStatement.setString(30, objFabric.getThreadPaletes()[28]);
            oPreparedStatement.setString(31, objFabric.getThreadPaletes()[29]);
            oPreparedStatement.setString(32, objFabric.getThreadPaletes()[30]);
            oPreparedStatement.setString(33, objFabric.getThreadPaletes()[31]);
            oPreparedStatement.setString(34, objFabric.getThreadPaletes()[32]);
            oPreparedStatement.setString(35, objFabric.getThreadPaletes()[33]);
            oPreparedStatement.setString(36, objFabric.getThreadPaletes()[34]);
            oPreparedStatement.setString(37, objFabric.getThreadPaletes()[35]);
            oPreparedStatement.setString(38, objFabric.getThreadPaletes()[36]);
            oPreparedStatement.setString(39, objFabric.getThreadPaletes()[37]);
            oPreparedStatement.setString(40, objFabric.getThreadPaletes()[38]);
            oPreparedStatement.setString(41, objFabric.getThreadPaletes()[39]);
            oPreparedStatement.setString(42, objFabric.getThreadPaletes()[40]);
            oPreparedStatement.setString(43, objFabric.getThreadPaletes()[41]);
            oPreparedStatement.setString(44, objFabric.getThreadPaletes()[42]);
            oPreparedStatement.setString(45, objFabric.getThreadPaletes()[43]);
            oPreparedStatement.setString(46, objFabric.getThreadPaletes()[44]);
            oPreparedStatement.setString(47, objFabric.getThreadPaletes()[45]);
            oPreparedStatement.setString(48, objFabric.getThreadPaletes()[46]);
            oPreparedStatement.setString(49, objFabric.getThreadPaletes()[47]);
            oPreparedStatement.setString(50, objFabric.getThreadPaletes()[48]);
            oPreparedStatement.setString(51, objFabric.getThreadPaletes()[49]);
            oPreparedStatement.setString(52, objFabric.getThreadPaletes()[50]);
            oPreparedStatement.setString(53, objFabric.getThreadPaletes()[51]);
            oResult = (byte)oPreparedStatement.executeUpdate();
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"setFabricPallets : "+strQuery,ex);
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
                    new Logging("SEVERE",FabricAction.class.getName(),"setFabricPallets() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< setFabricPallets >>>>>>");
        return oResult;
    }
    
    public byte resetFabricPallets(Fabric objFabric) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        try {           
            System.out.println("<<<<<< resetFabricPallets >>>>>>");
            strQuery = "UPDATE `mla_fabric_pallets` SET `fabric_id`= ?, `warp_A`= ?, `warp_B`= ?, `warp_C`= ?, `warp_D`= ?, `warp_E`= ?, `warp_F`= ?, `warp_G`= ?, `warp_H`= ?, `warp_I`= ?, `warp_J`= ?, `warp_K`= ?, `warp_L`= ?, `warp_M`= ?, `warp_N`= ?, `warp_O`= ?, `warp_P`= ?, `warp_Q`= ?, `warp_R`= ?, `warp_S`= ?, `warp_T`= ?, `warp_U`= ?, `warp_V`= ?, `warp_W`= ?, `warp_X`= ?, `warp_Y`= ?, `warp_Z`= ?, `weft_a`= ?, `weft_b`= ?, `weft_c`= ?, `weft_d`= ?, `weft_e`= ?, `weft_f`= ?, `weft_g`= ?, `weft_h`= ?, `weft_i`= ?, `weft_j`= ?, `weft_k`= ?, `weft_l`= ?, `weft_m`= ?, `weft_n`= ?, `weft_o`= ?, `weft_p`= ?, `weft_q`= ?, `weft_r`= ?, `weft_s`= ?, `weft_t`= ?, `weft_u`= ?, `weft_v`= ?, `weft_w`= ?, `weft_x`= ?, `weft_y`= ?, `weft_z`= ? WHERE `fabric_id`= ?;";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, objFabric.getStrFabricID());
            oPreparedStatement.setString(2, objFabric.getThreadPaletes()[0]);
            oPreparedStatement.setString(3, objFabric.getThreadPaletes()[1]);
            oPreparedStatement.setString(4, objFabric.getThreadPaletes()[2]);
            oPreparedStatement.setString(5, objFabric.getThreadPaletes()[3]);
            oPreparedStatement.setString(6, objFabric.getThreadPaletes()[4]);
            oPreparedStatement.setString(7, objFabric.getThreadPaletes()[5]);
            oPreparedStatement.setString(8, objFabric.getThreadPaletes()[6]);
            oPreparedStatement.setString(9, objFabric.getThreadPaletes()[7]);
            oPreparedStatement.setString(10, objFabric.getThreadPaletes()[8]);
            oPreparedStatement.setString(11, objFabric.getThreadPaletes()[9]);
            oPreparedStatement.setString(12, objFabric.getThreadPaletes()[10]);
            oPreparedStatement.setString(13, objFabric.getThreadPaletes()[11]);
            oPreparedStatement.setString(14, objFabric.getThreadPaletes()[12]);
            oPreparedStatement.setString(15, objFabric.getThreadPaletes()[13]);
            oPreparedStatement.setString(16, objFabric.getThreadPaletes()[14]);
            oPreparedStatement.setString(17, objFabric.getThreadPaletes()[15]);
            oPreparedStatement.setString(18, objFabric.getThreadPaletes()[16]);
            oPreparedStatement.setString(19, objFabric.getThreadPaletes()[17]);
            oPreparedStatement.setString(20, objFabric.getThreadPaletes()[18]);
            oPreparedStatement.setString(21, objFabric.getThreadPaletes()[19]);
            oPreparedStatement.setString(22, objFabric.getThreadPaletes()[20]);
            oPreparedStatement.setString(23, objFabric.getThreadPaletes()[21]);
            oPreparedStatement.setString(24, objFabric.getThreadPaletes()[22]);
            oPreparedStatement.setString(25, objFabric.getThreadPaletes()[23]);
            oPreparedStatement.setString(26, objFabric.getThreadPaletes()[24]);
            oPreparedStatement.setString(27, objFabric.getThreadPaletes()[25]);
            oPreparedStatement.setString(28, objFabric.getThreadPaletes()[26]);
            oPreparedStatement.setString(29, objFabric.getThreadPaletes()[27]);
            oPreparedStatement.setString(30, objFabric.getThreadPaletes()[28]);
            oPreparedStatement.setString(31, objFabric.getThreadPaletes()[29]);
            oPreparedStatement.setString(32, objFabric.getThreadPaletes()[30]);
            oPreparedStatement.setString(33, objFabric.getThreadPaletes()[31]);
            oPreparedStatement.setString(34, objFabric.getThreadPaletes()[32]);
            oPreparedStatement.setString(35, objFabric.getThreadPaletes()[33]);
            oPreparedStatement.setString(36, objFabric.getThreadPaletes()[34]);
            oPreparedStatement.setString(37, objFabric.getThreadPaletes()[35]);
            oPreparedStatement.setString(38, objFabric.getThreadPaletes()[36]);
            oPreparedStatement.setString(39, objFabric.getThreadPaletes()[37]);
            oPreparedStatement.setString(40, objFabric.getThreadPaletes()[38]);
            oPreparedStatement.setString(41, objFabric.getThreadPaletes()[39]);
            oPreparedStatement.setString(42, objFabric.getThreadPaletes()[40]);
            oPreparedStatement.setString(43, objFabric.getThreadPaletes()[41]);
            oPreparedStatement.setString(44, objFabric.getThreadPaletes()[42]);
            oPreparedStatement.setString(45, objFabric.getThreadPaletes()[43]);
            oPreparedStatement.setString(46, objFabric.getThreadPaletes()[44]);
            oPreparedStatement.setString(47, objFabric.getThreadPaletes()[45]);
            oPreparedStatement.setString(48, objFabric.getThreadPaletes()[46]);
            oPreparedStatement.setString(49, objFabric.getThreadPaletes()[47]);
            oPreparedStatement.setString(50, objFabric.getThreadPaletes()[48]);
            oPreparedStatement.setString(51, objFabric.getThreadPaletes()[49]);
            oPreparedStatement.setString(52, objFabric.getThreadPaletes()[50]);
            oPreparedStatement.setString(53, objFabric.getThreadPaletes()[51]);
            oPreparedStatement.setString(54, objFabric.getStrFabricID());
            oResult = (byte)oPreparedStatement.executeUpdate();
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"resetFabricPallets : "+strQuery,ex);
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
                    new Logging("SEVERE",FabricAction.class.getName(),"resetFabricPallets() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< resetFabricPallets >>>>>>");
        return oResult;
    }
    
    public boolean clearFabricPallets(String strFabricID) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        boolean oResult= false;
        String strQuery=null;
        try {           
            System.out.println("<<<<<< clearFabricPallets >>>>>>");
            strQuery = "DELETE FROM `mla_fabric_pallets` WHERE `fabric_id` = ?;";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, strFabricID);
            oResult = oPreparedStatement.execute();
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"clearFabricPallets : "+strQuery,ex);
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
                    new Logging("SEVERE",FabricAction.class.getName(),"clearFabricPallets() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< clearFabricPallets >>>>>>");
        return oResult;
    }
    
/*===========================================================================*/
    
    public void getFabricArtwork(Fabric objFabric) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String[][] colorWeave=null;
        try {           
            System.out.println("<<<<<< getFabricArtwork >>>>>>");
            strQuery = "SELECT DISTINCT * FROM `mla_fabric_artwork` WHERE fabric_id='"+objFabric.getStrFabricID()+"' ORDER BY serial;";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            oResultSet.last();
            System.out.println("Row= "+oResultSet.getRow());
            colorWeave = new String[oResultSet.getRow()][3];
            int i = 0;
            oResultSet.beforeFirst();
            while(oResultSet.next()) {
                if(oResultSet.getInt("is_background")==0)
                    objFabric.setColorArtwork(oResultSet.getString("color"));
                colorWeave[i][0] = oResultSet.getString("color");
                colorWeave[i][1] = oResultSet.getString("weave_id");
                colorWeave[i][2] = Integer.toString(oResultSet.getInt("serial"));
                i++;                
            }
            objFabric.setColorWeave(colorWeave);
            colorWeave = null;
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"getFabricArtwork : "+strQuery,ex);
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
                    new Logging("SEVERE",FabricAction.class.getName(),"getFabricArtwork() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< getFabricArtwork >>>>>>");
    //    return yarns;
    }
    
    public byte setFabricArtwork(Fabric objFabric) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        int is_background =1;
        String strQuery=null;
        try {           
            System.out.println("<<<<<< setFabricArtwork >>>>>>");
            strQuery = "INSERT INTO `mla_fabric_artwork` (`fabric_id`, `color`, `weave_id`, `serial`, `color_id`, `is_background`) VALUES (?,?,?,?,?,?);";
            oPreparedStatement = connection.prepareStatement(strQuery);
            for(int i =0; i<objFabric.getColorWeave().length;i++){
                if(objFabric.getColorWeave()[i][0]!=null && objFabric.getColorWeave()[i][2]!=null){
                    is_background =1;
                    if(objFabric.getColorArtwork().equalsIgnoreCase(objFabric.getColorWeave()[i][0]))
                        is_background =0;
                    oPreparedStatement.setString(1, objFabric.getStrFabricID());
                    oPreparedStatement.setString(2, objFabric.getColorWeave()[i][0]);             
                    oPreparedStatement.setString(3, objFabric.getColorWeave()[i][1]);                
                    oPreparedStatement.setInt(4, Integer.parseInt(objFabric.getColorWeave()[i][2]));
                    oPreparedStatement.setInt(5, i);
                    oPreparedStatement.setInt(6, is_background);
                    oPreparedStatement.addBatch();
                }
            }
            oResult = (byte)oPreparedStatement.executeBatch().length;
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"setFabricArtwork : "+strQuery,ex);
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
                    new Logging("SEVERE",FabricAction.class.getName(),"setFabricArtwork() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< setFabricArtwork >>>>>>");
        return oResult;
    }
    
    public byte resetFabricArtwork(Fabric objFabric) {
        byte oResult= 0;
        try {           
            System.out.println("<<<<<< resetFabricArtwork >>>>>>");
            clearFabricArtwork(objFabric.getStrFabricID());
            connection = DbConnect.getConnection();            
            oResult = (byte)setFabricArtwork(objFabric);
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"resetFabricArtwork",ex);
        } finally {
            try {
                if(connection!=null) {
                    connection.close();
                    connection=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",FabricAction.class.getName(),"resetFabricArtwork() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< resetFabricArtwork >>>>>>");
        return oResult;
    }
    
    public boolean clearFabricArtwork(String strFabricID) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        boolean oResult= false;
        String strQuery=null;
        try {           
            System.out.println("<<<<<< clearFabricArtwork >>>>>>");
            strQuery = "DELETE FROM `mla_fabric_artwork` WHERE `fabric_id` = ?;";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, strFabricID);
            oResult = oPreparedStatement.execute();
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"clearFabricArtwork : "+strQuery,ex);
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
                    new Logging("SEVERE",FabricAction.class.getName(),"clearFabricArtwork() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< clearFabricArtwork >>>>>>");
        return oResult;
    }
/*===========================================================================*/
        
    /*
    public byte setBatchFabricYarn(Fabric objFabric) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        String yarnId = null;
        try {           
            System.out.println("<<<<<< setFabricYarn >>>>>>");
            clearFabricYarn(objFabric);
            connection = DbConnect.getConnection();
            strQuery = "INSERT INTO `fabric_thread` (`fabric_id`, `yarn_id`, `symbol`, `repeat`, `serial`) VALUES (?,?,?,?,?);";
            oPreparedStatement = connection.prepareStatement(strQuery);
            for(int i = 0; i<objFabric.getWarpYarn().length; i++){
                connection = DbConnect.getConnection();
                yarnId = newYarnID(objFabric.getStrAccess());                
                objFabric.getWarpYarn()[i].setThreadId(yarnId);
                connection = DbConnect.getConnection();
                addYarn(objFabric.getWarpYarn()[i]);
                oPreparedStatement.setString(1, objFabric.getStrFabricID());
                oPreparedStatement.setString(2, yarnId);
                oPreparedStatement.setString(3, objFabric.getWarpYarn()[i].getThreadSymbol());
                oPreparedStatement.setInt(4, objFabric.getWarpYarn()[i].getThreadRepeat());
                oPreparedStatement.setInt(5, i);
                oPreparedStatement.addBatch();
            }
            for(int i = 0; i<objFabric.getWeftYarn().length; i++){
                connection = DbConnect.getConnection();
                yarnId = newYarnID(objFabric.getStrAccess());                
                objFabric.getWeftYarn()[i].setThreadId(yarnId);
                connection = DbConnect.getConnection();
                addYarn(objFabric.getWeftYarn()[i]);
                oPreparedStatement.setString(1, objFabric.getStrFabricID());
                oPreparedStatement.setString(2, yarnId);
                oPreparedStatement.setString(3, objFabric.getWeftYarn()[i].getThreadSymbol());
                oPreparedStatement.setInt(4, objFabric.getWeftYarn()[i].getThreadRepeat());
                oPreparedStatement.setInt(5, i);
                oPreparedStatement.addBatch();
            }
            for(int i = 0; i<objFabric.getWarpExtraYarn().length; i++){
                connection = DbConnect.getConnection();
                yarnId = newYarnID(objFabric.getStrAccess());                
                objFabric.getWarpExtraYarn()[i].setThreadId(yarnId);
                connection = DbConnect.getConnection();
                addYarn(objFabric.getWarpExtraYarn()[i]);
                oPreparedStatement.setString(1, objFabric.getStrFabricID());
                oPreparedStatement.setString(2, yarnId);
                oPreparedStatement.setString(3, objFabric.getWarpExtraYarn()[i].getThreadSymbol());
                oPreparedStatement.setInt(4, objFabric.getWarpExtraYarn()[i].getThreadRepeat());
                oPreparedStatement.setInt(5, i);
                oPreparedStatement.addBatch();
            }
            for(int i = 0; i<objFabric.getWeftExtraYarn().length; i++){
                connection = DbConnect.getConnection();
                yarnId = newYarnID(objFabric.getStrAccess());                
                objFabric.getWeftExtraYarn()[i].setThreadId(yarnId);
                connection = DbConnect.getConnection();
                addYarn(objFabric.getWeftExtraYarn()[i]);
                oPreparedStatement.setString(1, objFabric.getStrFabricID());
                oPreparedStatement.setString(2, yarnId);
                oPreparedStatement.setString(3, objFabric.getWeftExtraYarn()[i].getThreadSymbol());
                oPreparedStatement.setInt(4, objFabric.getWeftExtraYarn()[i].getThreadRepeat());
                oPreparedStatement.setInt(5, i);
                oPreparedStatement.addBatch();
            }
            oResult = (byte)oPreparedStatement.executeBatch().length;
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"setFabricYarn() : "+strQuery,ex);
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
                    new Logging("SEVERE",FabricAction.class.getName(),"setFabricYarn() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< setFabricYarn >>>>>>");
        return oResult;
    }
    */
/*===========================================================================*/
    
    public void readFabric(Fabric objFabric,byte type) {
        String content=null;
        if(type==0)
            content=objFabric.getStrFabricFile();
        else
            content=objFabric.getStrFabricRData();
        StringTokenizer tokenizer = new StringTokenizer(content, "=");
        byte[][] fabricMatrix = new byte[objFabric.getIntWeft()][objFabric.getIntWarp()];
        String token = null;
        StringTokenizer subtokenizer = null;
        int i = 0;
        int j=0;
        while (tokenizer.hasMoreTokens()) {
            if(i==0)
                token = tokenizer.nextToken(); 
            token = tokenizer.nextToken();
            token = token.substring(0, token.length()-Integer.toString(i+1).length());            
            j=0;
            subtokenizer = new StringTokenizer(token.trim(), ",");
            while (subtokenizer.hasMoreTokens()) {
                String subtoken = subtokenizer.nextToken();   
                fabricMatrix[i][j] =Byte.parseByte(subtoken);
                j++;
            }
            i++;
        }
        if(type==0)
            objFabric.setFabricMatrix(fabricMatrix);
        else
            objFabric.setReverseMatrix(fabricMatrix);
        fabricMatrix = null;
        token = null;
        subtokenizer = null;
        i = 0;
        j=0;
    }	

    public void writeFabric(Fabric objFabric,byte type) {
        StringBuffer content=new StringBuffer("");
        byte[][] fabricMatrix = new byte[objFabric.getIntWeft()][objFabric.getIntWarp()];
        if(type==0)
            fabricMatrix =objFabric.getFabricMatrix();
        else
            fabricMatrix =objFabric.getReverseMatrix();
        for(int i=0;i<objFabric.getIntWeft();i++){
            content.append(i+"=");
            for(int j=0; j<objFabric.getIntWarp(); j++){
                if(j==0)
                    content.append(fabricMatrix[i][j]);
                else
                    content.append(","+fabricMatrix[i][j]);
            }
            content.append("\n");
        }
        if(type==0)
            objFabric.setStrFabricFile(content.toString());
        else
            objFabric.setStrFabricRData(content.toString());
        content = null;
        fabricMatrix = null;
    }
    
/**************************** Color *****************************************/
    public List lstImportColor(Colour objColour) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        List lstColorDeatails=null, lstColor;
        new Logging("INFO",FabricAction.class.getName(),"<<<<<<<<<<< lstImportColor() >>>>>>>>>>>",null);            
        try {           
            String cond = "(USERID = '"+objColour.getObjConfiguration().getObjUser().getStrUserID()+"' OR `ACCESS`='"+new IDGenerator().getUserAcess("COLOUR_LIBRARY")+"') ";
            String orderBy ="NAME ";
            if(!(objColour.getStrCondition().trim().equals(""))) {
                cond += " AND (`NAME` LIKE '"+objColour.getStrCondition().trim()+"%'";
                cond += " OR `CODE` LIKE '"+objColour.getStrCondition().trim()+"%')";
            }
            if(!(objColour.getStrSearchBy().trim().equals("")) && !(objColour.getStrSearchBy().trim().equalsIgnoreCase("All"))) {
                cond += " AND `TYPE` LIKE '%"+objColour.getStrSearchBy().trim()+"%'";
            }
            if(objColour.getStrOrderBy().equals("Name")) {
                orderBy = "`NAME`;";
            } else if(objColour.getStrOrderBy().equals("Date")) {
                orderBy = "`UDATE`";
            } else if(objColour.getStrOrderBy().equals("Type")) {
                orderBy = "`TYPE`";
            } else if(objColour.getStrOrderBy().equals("Code")) {
                orderBy = "`CODE`";
            } else if(objColour.getStrOrderBy().equals("Hex Code")) {
                orderBy = "`HEX_CODE`";
            } else if(objColour.getStrOrderBy().equals("Red")) {
                orderBy = "`R_CODE`";
            } else if(objColour.getStrOrderBy().equals("Green")) {
                orderBy = "`G_CODE`";
            } else if(objColour.getStrOrderBy().equals("Blue")) {
                orderBy = "`B_CODE`";
            }
            if(objColour.getStrDirection().equals("Ascending")) {
                orderBy += " ASC";
            } else if(objColour.getStrDirection().equals("Descending")) {
                orderBy += " DESC";
            }
            strQuery = "select * from `mla_colour_library` WHERE "+cond+" ORDER BY "+orderBy;
			if(objColour.getStrLimit()!=null)
                strQuery+=" LIMIT "+objColour.getStrLimit();
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            lstColorDeatails = new ArrayList();            
            while(oResultSet.next()) {
                lstColor = new ArrayList();            
                lstColor.add(oResultSet.getString("ID").toString());
                lstColor.add(oResultSet.getString("NAME"));
                lstColor.add(oResultSet.getString("TYPE"));
                lstColor.add(oResultSet.getInt("R_CODE"));
                lstColor.add(oResultSet.getInt("G_CODE"));
                lstColor.add(oResultSet.getInt("B_CODE"));
                lstColor.add(oResultSet.getString("HEX_CODE"));
                lstColor.add(oResultSet.getString("CODE"));
                lstColor.add(oResultSet.getString("ACCESS"));
                lstColor.add(oResultSet.getString("USERID"));
                lstColor.add(oResultSet.getString("UDATE"));
                lstColorDeatails.add(lstColor);              
            }
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"lstImportColor : "+strQuery,ex);
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
                    new Logging("SEVERE",FabricAction.class.getName(),"lstImportColor() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",FabricAction.class.getName(),">>>>>>>>>>> lstImportColor() <<<<<<<<<<<"+strQuery,null);
        return lstColorDeatails;
    }
    
    /*
    public ArrayList getLstColors(Color objColour) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        ArrayList lstColor=null;
        Color objColourNew = null;
        try {           
            System.out.println("<<<<<< getLstColors >>>>>>");
            String cond = "";
            String orderBy ="NAME ";
            if(!(objColour.getStrCondition().trim().equals(""))) {
                cond += " AND `TYPE` LIKE '"+objColour.getStrCondition().trim()+"%'";
            }
            if(!(objColour.getStrSearchBy().trim().equals(""))) {
                cond += " AND `TYPE` LIKE '%"+objColour.getStrSearchBy().trim()+"%'";
            }
            if(objColour.getStrOrderBy().equals("Name")) {
                orderBy = "`NAME`;";
            } else if(objColour.getStrOrderBy().equals("Hex Code")) {
                orderBy = "`HEX_CODE`";
            } else if(objColour.getStrOrderBy().equals("Code")) {
                orderBy = "`CODE`";
            } else if(objColour.getStrOrderBy().equals("Red")) {
                orderBy = "`R_CODE`";
            } else if(objColour.getStrOrderBy().equals("Green")) {
                orderBy = "`G_CODE`";
            } else if(objColour.getStrOrderBy().equals("Blue")) {
                orderBy = "`B_CODE`";
            }
            strQuery = "select * from `colour_library` WHERE 1 "+cond+" ORDER BY "+orderBy;
            System.err.println("Query: "+strQuery);
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            lstColor = new ArrayList();            
            while(oResultSet.next()) {
                objColourNew = new Color();
                objColourNew.setStrColorID(oResultSet.getString("ID"));
                objColourNew.setStrColorName(oResultSet.getString("NAME"));
                objColourNew.setStrColorType(oResultSet.getString("TYPE"));
                objColourNew.setStrColorHex(oResultSet.getString("HEX_CODE"));
                objColourNew.setStrColorPantone(oResultSet.getString("PANTONE_CODE"));
                objColourNew.setIntR(oResultSet.getInt("R_CODE"));
                objColourNew.setIntG(oResultSet.getInt("G_CODE"));
                objColourNew.setIntB(oResultSet.getInt("B_CODE"));
                lstColor.add(objColourNew);    
            }
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"getLstColors : "+strQuery,ex);
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
                    new Logging("SEVERE",FabricAction.class.getName(),"getLstColors() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< getLstColors >>>>>>");
        return lstColor;
    }
    */
    
    
/**************************** PATTERN *************************************************/    
    
    public boolean setPattern(Pattern objPattern) {
        Statement oStatement =null;
        String strQuery=null;
        byte oResult = 0;
        String id = null, pattern = null;
        int repeat = 0, used = 0, type = 0;
        try {          
            id = objPattern.getStrPatternID();
            pattern = objPattern.getStrPattern();
            type = objPattern.getIntPatternType();
            repeat = objPattern.getIntPatternRepeat();
            used = objPattern.getIntPatternUsed();
            System.out.println("<<<<<< addPattern >>>>>>");
            strQuery = "INSERT INTO `mla_pattern_library` (`ID`, `TYPE`, `PATTERN`, `REPEAT`, `USED`, `ACCESS`, `USERID`) "
                    + "SELECT * FROM "
                    + "(SELECT '"+id+"' as a, '"+type+"' as b, '"+pattern+"' as c, '"+repeat+"' as d, '"+used+"' as e, '"+new IDGenerator().setUserAcessKey("PATTERN_LIBRARY",objPattern.getObjConfiguration().getObjUser())+"' as f, '"+objPattern.getObjConfiguration().getObjUser().getStrUserID()+"' as g) AS tmp WHERE NOT EXISTS "
                    + "(SELECT `type`, `pattern` FROM mla_pattern_library WHERE pattern = '"+pattern+"' AND type = '"+type+"') LIMIT 1;";
            oStatement = connection.createStatement();
            oResult = (byte)oStatement.executeUpdate(strQuery);
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"addPattern : "+strQuery,ex);
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
                    new Logging("SEVERE",FabricAction.class.getName(),"addPattern() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< addPattern >>>>>>");
        if(oResult==0)
            return false;
        else
            return true;
    }
    
    public Pattern getPattern(String strPatternID) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        Pattern objPattern = null;
        try {           
            System.out.println("<<<<<< getPattern >>>>>>");
            strQuery = "select * from mla_pattern_library WHERE ID='"+strPatternID+"';";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);  
            if(oResultSet.next())
				objPattern = new Pattern(oResultSet.getString("ID"),oResultSet.getString("PATTERN"),oResultSet.getInt("TYPE"),oResultSet.getInt("REPEAT"),oResultSet.getInt("USED"),oResultSet.getString("ACCESS"),oResultSet.getString("USERID"),oResultSet.getString("UDATE"));
            
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"getPattern : "+strQuery,ex);
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
                    new Logging("SEVERE",FabricAction.class.getName(),"getPattern() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< getPattern >>>>>>");
        return objPattern;
    }
    
    public ArrayList<Pattern> getAllPattern(byte type) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null, cond = "1";
        String code = null, pattern = null, access = null, user = null, date = null;
        int repeat = 0, used = 0;
        ArrayList<Pattern> lstPattern = null;  
        Pattern objPattern=null;
        try {
            System.out.println("<<<<<< getAllPattern >>>>>>");
            if(type>0) cond += " AND `type`="+type;
            strQuery = "SELECT * FROM `mla_pattern_library` WHERE "+cond+";";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            lstPattern = new ArrayList<Pattern>();            
            while(oResultSet.next()) {
                code = oResultSet.getString("ID").toString();
                pattern = oResultSet.getString("PATTERN").toString();
                repeat = oResultSet.getInt("REPEAT");
                used = oResultSet.getInt("USED");
                access = oResultSet.getString("ACCESS");
                user = oResultSet.getString("USERID");
                date = oResultSet.getString("UDATE").toString();
                lstPattern.add(new Pattern(code, pattern, (int)type, repeat, used, access, user, date));
            }
            objFabric.setLstPattern(lstPattern);
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"getAllPattern : "+strQuery,ex);
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
                    new Logging("SEVERE",FabricAction.class.getName(),"getAllPattern() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< getAllPattern >>>>>>");
        return lstPattern;
    }
    
    public String getPatternAvibilityID(String strPattern, int type) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String strPatternID = null;
        try {           
            System.out.println("<<<<<< getPatternID >>>>>>");
            strQuery = "SELECT ID FROM mla_pattern_library WHERE PATTERN='"+strPattern+"' AND TYPE='"+type+"';";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);  
            if(oResultSet.next())
				strPatternID = oResultSet.getString("ID");
        } catch (Exception ex) {
            new Logging("SEVERE",FabricAction.class.getName(),"getPatternID : "+strQuery,ex);
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
                    new Logging("SEVERE",FabricAction.class.getName(),"getPatternID() : Error while closing connection"+e,ex);
                }
            }
        }
        System.out.println("<<<<<< getPatternID >>>>>>"+strPatternID);
        return strPatternID;
    }
    
/****************************************************************************/    
    public double calculateDiameter(double count, double ply, double factor){
        double diameter = (double) (Math.sqrt(count * ply)/factor);
        /*DecimalFormat df = new DecimalFormat("##.0000");        
        return Double.parseDouble(df.format(diameter));*/
        diameter = diameter*10000;
        diameter = Math.round(diameter);
        diameter = diameter/10000;
        //newValue = Math.round(newValue*10000)/10000;
        return diameter;
    }
    
    public double calculateGSM(Configuration objConfiguration){
        System.err.println(objConfiguration.getStrWeftUnit()+ "=Tex=" +objConfiguration.getIntWeftCount());
        System.err.println(convertUnit(objConfiguration.getStrWeftUnit(), "Tex", objConfiguration.getIntWeftCount()));
        double gsm = (double) ((applyPercentage(objConfiguration.getIntPPI()*convertUnit(objConfiguration.getStrWeftUnit(), "Tex", objConfiguration.getIntWeftCount()),objConfiguration.getIntWeftCrimp())+applyPercentage(objConfiguration.getIntEPI()*convertUnit(objConfiguration.getStrWarpUnit(), "Tex", objConfiguration.getIntWarpCount()),objConfiguration.getIntWarpCrimp()))/25.4);
        gsm = gsm*10000;
        gsm = Math.round(gsm);
        gsm = gsm/10000;
        //gsm = Math.round(gsm*10000)/10000;
        return gsm;
    }
    public double calculateGSMOld(double weight, double length, double width){
        double gsm = weight/(length*width);
        gsm = gsm*10000;
        gsm = Math.round(gsm);
        gsm = gsm/10000;
        //gsm = Math.round(gsm*10000)/10000;
        return gsm;
    }
    
    public double convertUnit(String oldUnit, String newUnit, double oldValue){
        double newValue = oldValue;
        /*
        //==============old
        1 Tex = 1 Tex				
        1 Tex = 1000 Nm			
        1 Tex = 591 NeC			
        1 Tex = 886 NeW			
        1 Tex = 571 Np			
        1 Tex = 1938 Ny			
        1 Tex = 1654 NeL		
        1 Tex = 0.029 Nes		
        1 Tex = 9 Td			
        1 Tex = 310 Run			
        1 Tex = 10 dTex			
        1 Tex = 496054 YPP	
        1 Tex = 9 Denier        
        
        if(oldUnit.equalsIgnoreCase("Tex")){
            if(newUnit.equalsIgnoreCase("Tex"))
                newValue = oldValue;
            else if(newUnit.equalsIgnoreCase("Nm"))
                newValue = 1000*oldValue;
            else if(newUnit.equalsIgnoreCase("NeC"))
                newValue = 591*oldValue;
            else if(newUnit.equalsIgnoreCase("NeW"))
                newValue = 886*oldValue;
            else if(newUnit.equalsIgnoreCase("Np"))
                newValue = 571*oldValue;
            else if(newUnit.equalsIgnoreCase("Ny"))
                newValue = 1938*oldValue;
            else if(newUnit.equalsIgnoreCase("NeL"))
                newValue = 1654*oldValue;
            else if(newUnit.equalsIgnoreCase("Nes"))
                newValue = 0.029*oldValue;
            else if(newUnit.equalsIgnoreCase("Td"))
                newValue = 9*oldValue;
            else if(newUnit.equalsIgnoreCase("Run"))
                newValue = 310*oldValue;
            else if(newUnit.equalsIgnoreCase("dTex"))
                newValue = 10*oldValue;
            else if(newUnit.equalsIgnoreCase("YPP"))
                newValue = 496054*oldValue;
            else if(newUnit.equalsIgnoreCase("Denier"))
                newValue = 9*oldValue;
        } else if(oldUnit.equalsIgnoreCase("Nm")){
            newValue = oldValue/1000;
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("NeC")){
            newValue = oldValue/591;
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("NeW")){
            newValue = oldValue/886;
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Np")){
            newValue = oldValue/571;
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Ny")){
            newValue = oldValue/1938;
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("NeL")){
            newValue = oldValue/1654;
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Nes")){
            newValue = oldValue/0.029;
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Td")){
            newValue = oldValue/9;
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Run")){
            newValue = oldValue/310;
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("dTex")){
            newValue = oldValue/10;
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("YPP")){
            newValue = oldValue/496054;
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Denier")){
            newValue = oldValue/9;
            newValue = convertUnit("Tex",newUnit,newValue);
        }
        //===============new
        1 Tex	10 dTex
        1 Tex	0.0010 K tex
        1 Tex	9 Denier (Td)
        1 Tex	1000 New Metric (Nm)
        1 Tex	0.014112 Grains/Yard
        1 Tex	0.0290 Woollen (Aberdeen) (Ta)
        1 Tex	0.282 Woollen (US Grain)
        1 Tex	4960.55 Asbestos (American) (NaA)
        1 Tex	9921.09 Asbestos (English) (NeA)
        1 Tex	31003.43 Cotton bump Yarn (NB)
        1 Tex	4960.55 Glass (UK & USA)
        1 Tex	1653.52 Linen (Set or Dry Spun) (NeL)
        1 Tex	590.54 Spun Silk (Ns)
        1 Tex	1653.52 Woollen (American Cut) (Nac)
        1 Tex	310.03 Woollen (American run) (Nar)
        1 Tex	1937.71 Woollen (Yarkshire) (Ny)
        1 Tex	885.81 Woollen (Worsted) (New)
        1 Tex	0.290 Linen, Hemp, Jute (Tj)
        1 Tex	25.41 Micronaire (Mic)
        1 Tex	496.055 Yards Per Pound (YPP)
        1 Tex	882.80 English Worsted Count (NeK)
        1 Tex	590.5412 English Cotton (NeC)
        1 Tex	590.5412 New English (Ne)
        1 Tex   ? Numero en puntos (Np)
        */
        if(oldUnit.equalsIgnoreCase("Tex")){
            if(newUnit.equalsIgnoreCase("Tex"))
                newValue = oldValue;
            else if(newUnit.equalsIgnoreCase("dTex"))
                newValue = 10*oldValue;
            else if(newUnit.equalsIgnoreCase("K tex"))
                newValue = 591*oldValue;
            else if(newUnit.equalsIgnoreCase("Woollen (Worsted) (New)"))
                newValue = (double) (885.81*oldValue);
            else if(newUnit.equalsIgnoreCase("Denier (Td)"))
                newValue = 9*oldValue;
            else if(newUnit.equalsIgnoreCase("New Metric (Nm)"))
                newValue = 1000*oldValue;
            else if(newUnit.equalsIgnoreCase("Grains/Yard"))
                newValue = (double) (0.014112*oldValue);
            else if(newUnit.equalsIgnoreCase("Woollen (Aberdeen) (Ta)"))
                newValue = (double) (0.029*oldValue);
            else if(newUnit.equalsIgnoreCase("Woollen (US Grain)"))
                newValue = (double) (0.282*oldValue);
            else if(newUnit.equalsIgnoreCase("Asbestos (American) (NaA)"))
                newValue = (double) (4960.55*oldValue);
            else if(newUnit.equalsIgnoreCase("Asbestos (English) (NeA)"))
                newValue = (double) (9921.09*oldValue);
            else if(newUnit.equalsIgnoreCase("Cotton bump Yarn (NB)"))
                newValue = (double) (31003.43*oldValue);
            else if(newUnit.equalsIgnoreCase("Glass (UK & USA)"))
                newValue = (double) (4960.55*oldValue);
            else if(newUnit.equalsIgnoreCase("Linen (Set or Dry Spun) (NeL)"))
                newValue = (double) (1653.52*oldValue);
            else if(newUnit.equalsIgnoreCase("Spun Silk (Ns)"))
                newValue = (double) (590.54*oldValue);
            else if(newUnit.equalsIgnoreCase("Woollen (American Cut) (Nac)"))
                newValue = (double) (1653.52*oldValue);
            else if(newUnit.equalsIgnoreCase("Woollen (American run) (Nar)"))
                newValue = (double) (310.03*oldValue);
            else if(newUnit.equalsIgnoreCase("Woollen (Yarkshire) (Ny)"))
                newValue = (double) (1937.71*oldValue);
            else if(newUnit.equalsIgnoreCase("Linen, Hemp, Jute (Tj)"))
                newValue = (double) (0.290*oldValue);
            else if(newUnit.equalsIgnoreCase("Micronaire (Mic)"))
                newValue = (double) (25.41*oldValue);
            else if(newUnit.equalsIgnoreCase("Yards Per Pound (YPP)"))
                newValue = (double) (496.055*oldValue);
            else if(newUnit.equalsIgnoreCase("English Worsted Count (NeK)"))
                newValue = (double) (882.8*oldValue);
            else if(newUnit.equalsIgnoreCase("English Cotton (NeC)"))
                newValue = (double) (590.5412*oldValue);
            else if(newUnit.equalsIgnoreCase("New English (Ne)"))
                newValue = (double) (590.5412*oldValue);
            else if(newUnit.equalsIgnoreCase("Numero en puntos (Np)"))
                newValue = 571*oldValue;
        } else if(oldUnit.equalsIgnoreCase("dTex")){
            newValue = oldValue/10;
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("K tex")){
            newValue = (double) (oldValue/0.0010);
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Denier (Td)")){
            newValue = oldValue/9;
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("New Metric (Nm)")){
            newValue = oldValue/1000;
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Grains/Yard")){
            newValue = (double) (oldValue/0.014112);
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Woollen (Aberdeen) (Ta)")){
            newValue = (double) (oldValue/0.0290);
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Woollen (US Grain)")){
            newValue = (double) (oldValue/0.282);
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Asbestos (American) (NaA)")){
            newValue = (double) (oldValue/4960.55);
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Asbestos (English) (NeA)")){
            newValue = (double) (oldValue/9921.09);
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Cotton bump Yarn (NB)")){
            newValue = (double) (oldValue/31003.43);
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Glass (UK & USA)")){
            newValue = (double) (oldValue/4960.55);
            newValue = convertUnit("Tex",newUnit,newValue);
        }  else if(oldUnit.equalsIgnoreCase("Linen (Set or Dry Spun) (NeL)")){
            newValue = (double) (oldValue/1653.52);
            newValue = convertUnit("Tex",newUnit,newValue);
        }  else if(oldUnit.equalsIgnoreCase("Spun Silk (Ns)")){
            newValue = (double) (oldValue/590.54);
            newValue = convertUnit("Tex",newUnit,newValue);
        }  else if(oldUnit.equalsIgnoreCase("Woollen (American Cut) (Nac)")){
            newValue = (double) (oldValue/1653.52);
            newValue = convertUnit("Tex",newUnit,newValue);
        }  else if(oldUnit.equalsIgnoreCase("Woollen (American run) (Nar)")){
            newValue = (double) (oldValue/310.03);
            newValue = convertUnit("Tex",newUnit,newValue);
        }  else if(oldUnit.equalsIgnoreCase("Woollen (Yarkshire) (Ny)")){
            newValue = (double) (oldValue/1937.71);
            newValue = convertUnit("Tex",newUnit,newValue);
        }  else if(oldUnit.equalsIgnoreCase("Woollen (Worsted) (New)")){
            newValue = (double) (oldValue/885.81);
            newValue = convertUnit("Tex",newUnit,newValue);
        }  else if(oldUnit.equalsIgnoreCase("Linen, Hemp, Jute (Tj)")){
            newValue = (double) (oldValue/0.290);
            newValue = convertUnit("Tex",newUnit,newValue);            
        }  else if(oldUnit.equalsIgnoreCase("Micronaire (Mic)")){
            newValue = (double) (oldValue/25.41);
            newValue = convertUnit("Tex",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Yards Per Pound (YPP)")){
            newValue = (double) (oldValue/496.055);
            newValue = convertUnit("Tex",newUnit,newValue);
        }  else if(oldUnit.equalsIgnoreCase("English Worsted Count (NeK)")){
            newValue = (double) (oldValue/882.80);
            newValue = convertUnit("Tex",newUnit,newValue);
        }  else if(oldUnit.equalsIgnoreCase("English Cotton (NeC)")){
            newValue = (double) (oldValue/590.5412);
            newValue = convertUnit("Tex",newUnit,newValue);
        }  else if(oldUnit.equalsIgnoreCase("New English (Ne)")){
            newValue = (double) (oldValue/590.5412);
            newValue = convertUnit("Tex",newUnit,newValue);
        }  else if(oldUnit.equalsIgnoreCase("Numero en puntos (Np)")){
            newValue = oldValue/571;
            newValue = convertUnit("Tex",newUnit,newValue);
        }
        
        newValue = newValue*10000;
        newValue = Math.round(newValue);
        newValue = newValue/10000;
        //newValue = Math.round(newValue*10000)/10000;
        return newValue;
    }
    
    public double convertMeasure(String oldUnit, String newUnit, double oldValue){
        double newValue = oldValue;
        /*
        1 Inch = 0.0278 Yard
        1 Inch = 0.0833 Foot
        1 Inch = 1 Inch
        1 Inch = 0.0254 Meter
        1 Inch = 25.4 Milimeter   
        */
        if(oldUnit.equalsIgnoreCase("Inch")){
            if(newUnit.equalsIgnoreCase("Inch"))
                newValue = oldValue;
            else if(newUnit.equalsIgnoreCase("Yard"))
                newValue = oldValue/36;
            else if(newUnit.equalsIgnoreCase("Foot"))
                newValue = oldValue/12;
            else if(newUnit.equalsIgnoreCase("Meter"))
                newValue = (double) (oldValue/39.37);
            else if(newUnit.equalsIgnoreCase("Milimeter"))
                newValue = (double) (oldValue*25.4);
        } else if(oldUnit.equalsIgnoreCase("Yard")){
            newValue = oldValue*36;
            newValue = convertMeasure("Inch",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Foot")){
            newValue = oldValue*12;
            newValue = convertMeasure("Inch",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Meter")){
            newValue = (double) (oldValue*39.37);
            newValue = convertMeasure("Inch",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Milimeter")){
            newValue = (double) (oldValue/25.4);
            newValue = convertMeasure("Inch",newUnit,newValue);
        }
        newValue = newValue*10000;
        newValue = Math.round(newValue);
        newValue = newValue/10000;
        //newValue = Math.round(newValue*10000)/10000;
        return newValue;
    }
    
    public double convertWeight(String oldUnit, String newUnit, double oldValue){
        double newValue = oldValue;
        /*
        1 Gram = 0.0353 Ounce
        1 Gram = 15.4324 Grain
        1 Gram = 0.0022 Pound
        1 Gram = 0.001 Kilogram
        1 Gram = 1 Gram   
        */
        if(oldUnit.equalsIgnoreCase("Gram")){
            if(newUnit.equalsIgnoreCase("Gram"))
                newValue = oldValue;
            else if(newUnit.equalsIgnoreCase("Ounce"))
                newValue = (double) (oldValue*0.0353);
            else if(newUnit.equalsIgnoreCase("Grain"))
                newValue = (double) (oldValue*15.4324);
            else if(newUnit.equalsIgnoreCase("Pound"))
                newValue = (double) (oldValue*0.0022);
            else if(newUnit.equalsIgnoreCase("Kilogram"))
                newValue = (double) (oldValue*0.001);
        } else if(oldUnit.equalsIgnoreCase("Ounce")){
            newValue = (double) (oldValue*28.33);
            newValue = convertWeight("Gram",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Grain")){
            newValue = (double) (oldValue*0.0648);
            newValue = convertWeight("Gram",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Pound")){
            newValue = (double) (oldValue*453.592);
            newValue = convertWeight("Gram",newUnit,newValue);
        } else if(oldUnit.equalsIgnoreCase("Kilogram")){
            newValue = oldValue*1000;
            newValue = convertWeight("Gram",newUnit,newValue);
        }
        newValue = newValue*10000;
        newValue = Math.round(newValue);
        newValue = newValue/10000;
        //newValue = Math.round(newValue*10000)/10000;
        return newValue;
    }
    
    public double getWarpNumber(Fabric objFabric){
        return objFabric.getIntEPI()*objFabric.getDblFabricWidth();
    }
    
    public double getWarpLong(Fabric objFabric){
        double warpLong = objFabric.getIntEPI()*objFabric.getDblFabricWidth()*convertMeasure("Inch","Meter",objFabric.getDblFabricLength());
        warpLong = applyPercentage(warpLong,objFabric.getObjConfiguration().getIntWarpCrimp());
        warpLong = applyPercentage(warpLong,objFabric.getObjConfiguration().getIntWarpWaste());
        return warpLong; 
    }
    
    public double getWarpWeight(Fabric objFabric){
        double warpWeight = objFabric.getObjConfiguration().getIntWarpCount()*objFabric.getIntEPI()*objFabric.getDblFabricWidth()*convertMeasure("Inch","Meter",objFabric.getDblFabricLength())/9000;
        warpWeight = applyPercentage(warpWeight,objFabric.getObjConfiguration().getIntWarpCrimp());
        warpWeight = applyPercentage(warpWeight,objFabric.getObjConfiguration().getIntWarpWaste());
        return warpWeight;
    }
    
    public double getWeftNumber(Fabric objFabric){
        return objFabric.getIntPPI()*objFabric.getDblFabricLength();
    }
    
    public double getWeftLong(Fabric objFabric){
        double weftLong = objFabric.getIntPPI()*objFabric.getDblFabricLength()*convertMeasure("Inch","Meter",objFabric.getDblFabricWidth());
        weftLong = applyPercentage(weftLong,objFabric.getObjConfiguration().getIntWeftCrimp());
        weftLong = applyPercentage(weftLong,objFabric.getObjConfiguration().getIntWeftWaste());
        return weftLong;
    }
    
    public double getWeftWeight(Fabric objFabric){
        double weftWeight = objFabric.getObjConfiguration().getIntWeftCount()*objFabric.getIntPPI()*objFabric.getDblFabricLength()*convertMeasure("Inch","Meter",objFabric.getDblFabricWidth())/9000;
        weftWeight = applyPercentage(weftWeight,objFabric.getObjConfiguration().getIntWeftCrimp());
        weftWeight = applyPercentage(weftWeight,objFabric.getObjConfiguration().getIntWeftWaste());
        return weftWeight;
    }
    
    public double getExtraWeftLong(Fabric objFabric){
        double weftLong = objFabric.getObjConfiguration().getDblExtraWeftNumber()*convertMeasure("Inch","Meter",objFabric.getDblFabricWidth());
        weftLong = applyPercentage(weftLong,objFabric.getObjConfiguration().getIntWeftCrimp());
        weftLong = applyPercentage(weftLong,objFabric.getObjConfiguration().getIntWeftWaste());
        return weftLong;
    }
    
    public double getExtraWeftWeight(Fabric objFabric){
        double weftWeight = objFabric.getObjConfiguration().getIntWeftCount()*objFabric.getObjConfiguration().getDblExtraWeftNumber()*convertMeasure("Inch","Meter",objFabric.getDblFabricWidth())/9000;
        weftWeight = applyPercentage(weftWeight,objFabric.getObjConfiguration().getIntWeftCrimp());
        weftWeight = applyPercentage(weftWeight,objFabric.getObjConfiguration().getIntWeftWaste());
        return weftWeight;
    }
    
    public double applyPercentage(double oldValue, int percentage){
        return oldValue*(100+percentage)/100;
    }
    
    public double getMaterialCost(Fabric objFabric){
        double price = objFabric.getObjConfiguration().getDblWeftPrice() * objFabric.getObjConfiguration().getDblWeftWeight();
        price += objFabric.getObjConfiguration().getDblWarpPrice() * objFabric.getObjConfiguration().getDblWarpWeight();
        price += objFabric.getObjConfiguration().getDblWeftPrice() * objFabric.getObjConfiguration().getDblExtraWeftWeight();
        //price +=objFabric.getObjConfiguration().getDblWarpPrice() * objFabric.getObjConfiguration().getDblExtraWarpWeight();
        return price;
    }
    
    public double calculateCost(Fabric objFabric){
        double price = (objFabric.getObjConfiguration().getDblWeftPrice()*objFabric.getObjConfiguration().getDblWeftWeight())+(objFabric.getObjConfiguration().getDblWarpPrice()*objFabric.getObjConfiguration().getDblWarpWeight());
        price = price+objFabric.getObjConfiguration().getDblDesigningCost()+objFabric.getObjConfiguration().getDblPunchingCost()+objFabric.getObjConfiguration().getDblPropertyCost()+objFabric.getObjConfiguration().getDblWagesCost();
        price = applyPercentage(price,objFabric.getObjConfiguration().getIntOverheadCost());
        price = applyPercentage(price,objFabric.getObjConfiguration().getIntProfit());
        return price;
    }
    
    private static int gcd(int a, int b){
        while (b > 0) {
            int temp = b;
            b = a % b; // % is remainder
            a = temp;
        }
        return a;
    }
    
    private static int gcd(int[] input){
        int result = input[0];
        for(int i = 1; i < input.length; i++) result = gcd(result, input[i]);
        return result;
    }
    
    private int lcm(int a, int b){
        return a * (b / gcd(a, b));
    }
    
    private int lcm(int[] input){
        int result = input[0];
        for(int i = 1; i < input.length; i++) 
            result = lcm(result, input[i]);
        return result;
    }
//===========================================================
    
    public BufferedImage plotGridView(Fabric objFabric, int intWarp, int intWeft){
        BufferedImage bufferedImage = new BufferedImage(intWarp*3, intWeft*3,BufferedImage.TYPE_INT_RGB);        
        int bands = 3;
        int rgb = 0;
        for(int x = 0, p = 0; x < intWeft; x++) {
            for(int y = 0, q = 0; y < intWarp; y++) {
                for(int i = 0; i < bands; i++) {
                    for(int j = 0; j < bands; j++) {                        
                        if(objFabric.getFabricMatrix()[x][y]==0){
                            if(i==0)
                                rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).brighter().getRGB();
                            else if(i==2)
                                rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).darker().getRGB();
                            else
                                rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).getRGB();
                        } else if(objFabric.getFabricMatrix()[x][y]==1){
                            if(j==0)
                                rgb = new Color((float)javafx.scene.paint.Color.web("#000000").getRed(),(float)javafx.scene.paint.Color.web("#000000").getGreen(),(float)javafx.scene.paint.Color.web("#000000").getBlue()).brighter().getRGB();
                            else if(j==2)
                                rgb = new Color((float)javafx.scene.paint.Color.web("#000000").getRed(),(float)javafx.scene.paint.Color.web("#000000").getGreen(),(float)javafx.scene.paint.Color.web("#000000").getBlue()).darker().getRGB();
                            else
                                rgb = new Color((float)javafx.scene.paint.Color.web("#000000").getRed(),(float)javafx.scene.paint.Color.web("#000000").getGreen(),(float)javafx.scene.paint.Color.web("#000000").getBlue()).getRGB();
                        } else {
                            if(i==0)
                                rgb = new Color((float)javafx.scene.paint.Color.web("#FF0000").getRed(),(float)javafx.scene.paint.Color.web("#FF0000").getGreen(),(float)javafx.scene.paint.Color.web("#FF0000").getBlue()).brighter().getRGB();
                            else if(i==2)
                                rgb = new Color((float)javafx.scene.paint.Color.web("#FF0000").getRed(),(float)javafx.scene.paint.Color.web("#FF0000").getGreen(),(float)javafx.scene.paint.Color.web("#FF0000").getBlue()).darker().getRGB();
                            else
                                rgb = new Color((float)javafx.scene.paint.Color.web("#FF0000").getRed(),(float)javafx.scene.paint.Color.web("#FF0000").getGreen(),(float)javafx.scene.paint.Color.web("#FF0000").getBlue()).getRGB();
                        }
                        bufferedImage.setRGB(q+j, p+i, rgb);
                    }
                }
                q+=bands;
            }
            p+=bands;
        }
        return bufferedImage;
    }
    
    public byte[][] plotGraphErrorMatrix(Fabric objFabric, int intFloatBind){
        byte[][] errorMatrix = new byte[objFabric.getIntWeft()][objFabric.getIntWarp()];
        for(int x = 0; x < objFabric.getIntWeft(); x++) {
            int count = 1;
            int temp = objFabric.getFabricMatrix()[x][0];
            for(int y = 0; y < objFabric.getIntWarp(); y++) {
                if(temp==objFabric.getFabricMatrix()[x][y])
                    count++;
                else{
                    count = 0;
                    temp = objFabric.getFabricMatrix()[x][y];
                }
                if(count>=intFloatBind)
                    errorMatrix[x][y] = -1;
            }
        }
        return errorMatrix;
    }
    public void plotGraphErrorCorrection(Fabric objFabric, int intFloatBind){
        for(int x = 0; x < objFabric.getIntWeft(); x++) {
            int count = 1;
            byte temp = objFabric.getFabricMatrix()[x][0];
            for(int y = 0; y < objFabric.getIntWarp(); y++) {
                if(temp==objFabric.getFabricMatrix()[x][y])
                    count++;
                else {
                    count = 0;
                    temp = objFabric.getFabricMatrix()[x][y];
                }
                if(count>=intFloatBind){
                    objFabric.getReverseMatrix()[x][y]=objFabric.getFabricMatrix()[x][y];
                    if(temp==1)
                        objFabric.getFabricMatrix()[x][y]=0;
                    else
                        objFabric.getFabricMatrix()[x][y]=1;
                    objFabric.getReverseMatrix()[x][y]=temp;
                    count = 0;
                }
            }
        }
        return;
    }
    
    public BufferedImage plotGraphDobbyView(Fabric objFabric, int intWarp, int intWeft){
        BufferedImage bufferedImage = new BufferedImage(intWarp, intWeft,BufferedImage.TYPE_INT_RGB);        
        int rgb = 0;
        for(int x = 0, p = 0; x < intWeft; x++) {
            for(int y = 0, q = 0; y < intWarp; y++) {
                if(objFabric.getFabricMatrix()[x][y]==0){
                    rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).getRGB();
                } else {
                    rgb = new Color((float)javafx.scene.paint.Color.web("#000000").getRed(),(float)javafx.scene.paint.Color.web("#000000").getGreen(),(float)javafx.scene.paint.Color.web("#000000").getBlue()).getRGB();
                }
                bufferedImage.setRGB(y, x, rgb);
            }
        }
        return bufferedImage;
    }
    public BufferedImage plotGraphJaquardView(Fabric objFabric, int intWarp, int intWeft){
        BufferedImage bufferedImage = new BufferedImage(intWarp, intWeft,BufferedImage.TYPE_INT_RGB);        
        
        Yarn[] weftYarn = objFabric.getWeftYarn();
        Yarn[] warpYarn = objFabric.getWarpYarn();
        Yarn[] weftExtraYarn = objFabric.getWeftExtraYarn();
        Yarn[] warpExtraYarn = objFabric.getWarpExtraYarn();
        
        int warpCount = warpYarn.length;
        int weftCount = weftYarn.length;
        int weftExtraCount = objFabric.getIntExtraWeft();
        int warpExtraCount = objFabric.getIntExtraWarp();
        
        int rgb = 0;
        for(int x = 0, p = 0; x < intWeft; x++) {
            for(int y = 0, q = 0; y < intWarp; y++) {
                if(objFabric.getFabricMatrix()[x][y]==1)
                    rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).getRGB();
                else if(objFabric.getFabricMatrix()[x][y]==0)
                    rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).getRGB();
                else if(weftExtraCount>0){
                    for(int a=0; a<weftExtraCount; a++)
                        if(objFabric.getFabricMatrix()[x][y]==(a+2))
                            rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getRGB();
                }
                else
                    rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).getRGB();
                bufferedImage.setRGB(y, x, rgb);
            }
        }
        
        return bufferedImage;
    }
    public BufferedImage plotGraphJaquardMachineView(Fabric objFabric, int intWarp, int intWeft){
        BufferedImage bufferedImage = new BufferedImage(intWarp*(objFabric.getIntExtraWeft()+2), intWeft,BufferedImage.TYPE_INT_RGB);        
        
        Yarn[] weftYarn = objFabric.getWeftYarn();
        Yarn[] warpYarn = objFabric.getWarpYarn();
        Yarn[] weftExtraYarn = objFabric.getWeftExtraYarn();
        Yarn[] warpExtraYarn = objFabric.getWarpExtraYarn();
        
        int warpCount = warpYarn.length;
        int weftCount = weftYarn.length;
        int weftExtraCount = objFabric.getIntExtraWeft();
        int warpExtraCount = objFabric.getIntExtraWarp();
        
        int rgb = 0;
        for(int x = 0; x < intWeft; x++) {
            for(int y = 0; y < intWarp; y++) {
                if(objFabric.getFabricMatrix()[x][y]==1)
                    rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).getRGB();
                else if(objFabric.getFabricMatrix()[x][y]==0)
                    rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).getRGB();
                else if(weftExtraCount>0){
                    for(int a=0; a<weftExtraCount; a++)
                        if(objFabric.getFabricMatrix()[x][y]==(a+2))
                            rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getRGB();
                }
                else
                    rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).getRGB();
                bufferedImage.setRGB(y, x, rgb);
            }
        }
        for(int x = 0; x < intWeft; x++) {
            for(int y = 0; y < intWarp; y++) {
                if(objFabric.getFabricMatrix()[x][y]==1)
                    rgb = new Color((float)javafx.scene.paint.Color.web("#000000").getRed(),(float)javafx.scene.paint.Color.web("#000000").getGreen(),(float)javafx.scene.paint.Color.web("#000000").getBlue()).getRGB();
                else if(objFabric.getFabricMatrix()[x][y]==0)
                    rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).getRGB();
                else if(weftExtraCount>0){
                    for(int a=0; a<weftExtraCount; a++)
                        if(objFabric.getFabricMatrix()[x][y]==(a+2))
                            rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).getRGB();
                }
                else
                    rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).getRGB();
                bufferedImage.setRGB(y+intWarp, x, rgb);
            }
        }
        for(int x = 0, p = 0; x < intWeft; x++) {
            for(int y = 0, q = 0; y < intWarp; y++) {
                if(weftExtraCount>0){
                    for(int a=0; a<weftExtraCount; a++)
                        if(objFabric.getFabricMatrix()[x][y]==(a+2)){
                            rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getRGB();
                            bufferedImage.setRGB(y+(intWarp*(a+2)), x, rgb);
                        }else{
                            rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).getRGB();
                            bufferedImage.setRGB(y+(intWarp*(a+2)), x, rgb);
                        }
                }                
            }
        }
        
        return bufferedImage;
    }
    public BufferedImage plotGraphJaquardMachineView1(Fabric objFabric, int intWarp, int intWeft){
        BufferedImage bufferedImage = new BufferedImage(intWarp*(objFabric.getIntExtraWeft()+2), intWeft,BufferedImage.TYPE_INT_RGB);        
        
        Yarn[] weftYarn = objFabric.getWeftYarn();
        Yarn[] warpYarn = objFabric.getWarpYarn();
        Yarn[] weftExtraYarn = objFabric.getWeftExtraYarn();
        Yarn[] warpExtraYarn = objFabric.getWarpExtraYarn();
        
        int warpCount = warpYarn.length;
        int weftCount = weftYarn.length;
        int weftExtraCount = objFabric.getIntExtraWeft();
        int warpExtraCount = objFabric.getIntExtraWarp();
        
        int rgb = 0;
        for(int x = 0, p = 0; x < intWeft; x++) {
            for(int y = 0, q = 0; y < intWarp; y++) {
                if(objFabric.getFabricMatrix()[x][y]==1){
                    rgb = new Color((float)javafx.scene.paint.Color.web("#000000").getRed(),(float)javafx.scene.paint.Color.web("#000000").getGreen(),(float)javafx.scene.paint.Color.web("#000000").getBlue()).getRGB();
                    bufferedImage.setRGB(y, x, rgb);
                }
                else if(objFabric.getFabricMatrix()[x][y]==0){
                    rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).getRGB();
                    bufferedImage.setRGB(y, x, rgb);
                }
                else if(weftExtraCount>0){
                    for(int a=0; a<weftExtraCount; a++)
                        if(objFabric.getFabricMatrix()[x][y]==(a+2)){
                            rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getRGB();
                            bufferedImage.setRGB(y+(intWeft*(a+1)), x, rgb);
                        }
                }
                else{
                    rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).getRGB();
                    bufferedImage.setRGB(y, x, rgb);
                }
                
            }
        }
        
        return bufferedImage;
    }
    public BufferedImage plotJaquardGraphView(Fabric objFabric, int extraWeft){
        BufferedImage bufferedImage = new BufferedImage(objFabric.getIntWarp(), objFabric.getIntWeft(),BufferedImage.TYPE_INT_RGB);        
        
        Yarn[] weftYarn = objFabric.getWeftYarn();
        Yarn[] warpYarn = objFabric.getWarpYarn();
        Yarn[] weftExtraYarn = objFabric.getWeftExtraYarn();
        Yarn[] warpExtraYarn = objFabric.getWarpExtraYarn();
        
        int warpCount = warpYarn.length;
        int weftCount = weftYarn.length;
        int weftExtraCount = objFabric.getIntExtraWeft();
        int warpExtraCount = objFabric.getIntExtraWarp();
        
        int rgb = 0;
        for(int x = 0, p = 0; x < objFabric.getIntWeft(); x++) {
            for(int y = 0, q = 0; y < objFabric.getIntWarp(); y++) {
                if(weftExtraCount>0 && objFabric.getFabricMatrix()[x][y]==(extraWeft+2))
                    rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[extraWeft].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[extraWeft].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[extraWeft].getStrYarnColor()).getBlue()).getRGB();
                else
                    rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).getRGB();
                bufferedImage.setRGB(y, x, rgb);
            }                
        }
        return bufferedImage;
    }
    
    public BufferedImage plotCompositeView(Fabric objFabric, int intWarp, int intWeft, int intLength, int intHeight){
        Yarn[] weftYarn = objFabric.getWeftYarn();
        Yarn[] warpYarn = objFabric.getWarpYarn();
        Yarn[] weftExtraYarn = objFabric.getWeftExtraYarn();
        Yarn[] warpExtraYarn = objFabric.getWarpExtraYarn();
        System.out.println(warpYarn.length);
        int warpCount = warpYarn.length;
        int weftCount = weftYarn.length;
        int weftExtraCount = objFabric.getIntExtraWeft();
        int warpExtraCount = objFabric.getIntExtraWarp();
        
        byte[][] repeatMatrix = new byte[intHeight][intLength];
        int dpi = objFabric.getObjConfiguration().getIntDPI();
        int warpFactor = dpi/objFabric.getIntEPI();
        int weftFactor = dpi/objFabric.getIntPPI();
        
        for(int i = 0; i < intHeight; i++) {
            for(int j = 0; j < intLength; j++) {
                if(i>=intWeft && j<intWarp){
                     repeatMatrix[i][j] = objFabric.getFabricMatrix()[i%intWeft][j];  
                }else if(i<intWeft && j>=intWarp){
                     repeatMatrix[i][j] = objFabric.getFabricMatrix()[i][j%intWarp];  
                }else if(i>=intWeft && j>=intWarp){
                     repeatMatrix[i][j] = objFabric.getFabricMatrix()[i%intWeft][j%intWarp];  
                }else{
                     repeatMatrix[i][j] = objFabric.getFabricMatrix()[i][j]; 
                }                
            }
        } 
        BufferedImage bufferedImage = new BufferedImage(intLength, intHeight,BufferedImage.TYPE_INT_RGB);        
        int rgb = 0;
        for(int x = 0; x < intHeight; x++) {
            for(int y = 0; y < intLength; y++) {
                if(repeatMatrix[x][y]==1)
                    rgb = new Color((float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).getRGB(); 
                else if(repeatMatrix[x][y]==0)
                    rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRGB(); 
                else if(weftExtraCount>0){
                    for(int a=0; a<weftExtraCount; a++)
                        if(repeatMatrix[x][y]==(a+2))
                            rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getRGB();
                }
                else
                    rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRGB(); 
                bufferedImage.setRGB(y, x, rgb);
            }
        }
        repeatMatrix = null;
        return bufferedImage;        
    }
    
    public BufferedImage plotSwitchSideView(Fabric objFabric, int intWarp, int intWeft, int intLength, int intHeight){
        Yarn[] weftYarn = objFabric.getWeftYarn();
        Yarn[] warpYarn = objFabric.getWarpYarn();
        Yarn[] weftExtraYarn = objFabric.getWeftExtraYarn();
        Yarn[] warpExtraYarn = objFabric.getWarpExtraYarn();
        
        int warpCount = warpYarn.length;
        int weftCount = weftYarn.length;
        int weftExtraCount = objFabric.getIntExtraWeft();
        int warpExtraCount = objFabric.getIntExtraWarp();
        byte[][] repeatMatrix = new byte[intHeight][intLength];
        int dpi = objFabric.getObjConfiguration().getIntDPI();
        int warpFactor = dpi/objFabric.getIntEPI();
        int weftFactor = dpi/objFabric.getIntPPI();
        
        for(int i = 0; i < intHeight; i++) {
            for(int j = 0; j < intLength; j++) {
                if(i>=intWeft && j<intWarp){
                     repeatMatrix[i][j] = objFabric.getReverseMatrix()[i%intWeft][j];  
                }else if(i<intWeft && j>=intWarp){
                     repeatMatrix[i][j] = objFabric.getReverseMatrix()[i][j%intWarp];  
                }else if(i>=intWeft && j>=intWarp){
                     repeatMatrix[i][j] = objFabric.getReverseMatrix()[i%intWeft][j%intWarp];  
                }else{
                     repeatMatrix[i][j] = objFabric.getReverseMatrix()[i][j]; 
                }                
            }
        } 
        
        BufferedImage bufferedImage = new BufferedImage(intLength*3, intHeight*3,BufferedImage.TYPE_INT_RGB);        
        int bands = 3;
        int rgb = 0;
        for(int x = 0, p = 0; x < intHeight; x++) {
            for(int y = intLength-1, q = 0; 0 <= y; y--) {
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
                        } else if(weftExtraCount>0){
                            for(int a=0; a<weftExtraCount; a++){
                                if(repeatMatrix[x][y]==(a+2)){
                                    if(i==0)
                                        rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).brighter().getRGB();
                                    else if(i==2)
                                        rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).darker().getRGB();
                                    else
                                        rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getRGB();
                                }
                            }
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
    public BufferedImage plotFlipSideView(Fabric objFabric, int intWarp, int intWeft, int intLength, int intHeight){
        Yarn[] weftYarn = objFabric.getWeftYarn();
        Yarn[] warpYarn = objFabric.getWarpYarn();
        Yarn[] weftExtraYarn = objFabric.getWeftExtraYarn();
        Yarn[] warpExtraYarn = objFabric.getWarpExtraYarn();
        
        int warpCount = warpYarn.length;
        int weftCount = weftYarn.length;
        int weftExtraCount = objFabric.getIntExtraWeft();
        int warpExtraCount = objFabric.getIntExtraWarp();
        byte[][] repeatMatrix = new byte[intHeight][intLength];
        int dpi = objFabric.getObjConfiguration().getIntDPI();
        int warpFactor = dpi/objFabric.getIntEPI();
        int weftFactor = dpi/objFabric.getIntPPI();
        
        for(int i = 0; i < intHeight; i++) {
            for(int j = 0; j < intLength; j++) {
                if(i>=intWeft && j<intWarp){
                     repeatMatrix[i][j] = objFabric.getReverseMatrix()[i%intWeft][j];  
                }else if(i<intWeft && j>=intWarp){
                     repeatMatrix[i][j] = objFabric.getReverseMatrix()[i][j%intWarp];  
                }else if(i>=intWeft && j>=intWarp){
                     repeatMatrix[i][j] = objFabric.getReverseMatrix()[i%intWeft][j%intWarp];  
                }else{
                     repeatMatrix[i][j] = objFabric.getReverseMatrix()[i][j]; 
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
                        } else if(weftExtraCount>0){
                            for(int a=0; a<weftExtraCount; a++){
                                if(repeatMatrix[x][y]==(a+2)){
                                    if(i==0)
                                        rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).brighter().getRGB();
                                    else if(i==2)
                                        rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).darker().getRGB();
                                    else
                                        rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getRGB();
                                }
                            }
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
    public BufferedImage plotFrontSideView(Fabric objFabric, int intWarp, int intWeft, int intLength, int intHeight){
        Yarn[] weftYarn = objFabric.getWeftYarn();
        Yarn[] warpYarn = objFabric.getWarpYarn();
        Yarn[] weftExtraYarn = objFabric.getWeftExtraYarn();
        Yarn[] warpExtraYarn = objFabric.getWarpExtraYarn();
        
        int warpCount = warpYarn.length;
        int weftCount = weftYarn.length;
        int weftExtraCount = objFabric.getIntExtraWeft();
        int warpExtraCount = objFabric.getIntExtraWarp();
        byte[][] repeatMatrix = new byte[intHeight][intLength];
        int dpi = objFabric.getObjConfiguration().getIntDPI();
        int warpFactor = dpi/objFabric.getIntEPI();
        int weftFactor = dpi/objFabric.getIntPPI();
        
        for(int i = 0; i < intHeight; i++) {
            for(int j = 0; j < intLength; j++) {
                if(i>=intWeft && j<intWarp){
                     repeatMatrix[i][j] = objFabric.getFabricMatrix()[i%intWeft][j];  
                }else if(i<intWeft && j>=intWarp){
                     repeatMatrix[i][j] = objFabric.getFabricMatrix()[i][j%intWarp];  
                }else if(i>=intWeft && j>=intWarp){
                     repeatMatrix[i][j] = objFabric.getFabricMatrix()[i%intWeft][j%intWarp];  
                }else{
                     repeatMatrix[i][j] = objFabric.getFabricMatrix()[i][j]; 
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
                        } else if(weftExtraCount>0){
                            for(int a=0; a<weftExtraCount; a++){
                                if(repeatMatrix[x][y]==(a+2)){
                                    if(i==0)
                                        rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).brighter().getRGB();
                                    else if(i==2)
                                        rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).darker().getRGB();
                                    else
                                        rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getRGB();
                                }
                            }
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
    public BufferedImage plotCrossSectionView(Fabric objFabric, byte[][] repeatMatrix, int intLength, int intHeight){
        Yarn[] weftYarn = objFabric.getWeftYarn();
        Yarn[] warpYarn = objFabric.getWarpYarn();
        Yarn[] weftExtraYarn = objFabric.getWeftExtraYarn();
        Yarn[] warpExtraYarn = objFabric.getWarpExtraYarn();
        
        int warpCount = warpYarn.length;
        int weftCount = weftYarn.length;
        int weftExtraCount = objFabric.getIntExtraWeft();
        int warpExtraCount = objFabric.getIntExtraWarp();
        int dpi = objFabric.getObjConfiguration().getIntDPI();
        int warpFactor = dpi/objFabric.getIntEPI();
        int weftFactor = dpi/objFabric.getIntPPI();
        
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
                        } else if(weftExtraCount>0){
                            for(int a=0; a<weftExtraCount; a++){
                                if(repeatMatrix[x][y]==(a+2)){
                                    if(i==0)
                                        rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).brighter().getRGB();
                                    else if(i==2)
                                        rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).darker().getRGB();
                                    else
                                        rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getRGB();
                                }
                            }
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
    
    public BufferedImage plotCrossSectionGraphView(Fabric objFabric, byte[][] repeatMatrix, int intLength, int intHeight){
        Yarn[] weftYarn = objFabric.getWeftYarn();
        Yarn[] warpYarn = objFabric.getWarpYarn();
        Yarn[] weftExtraYarn = objFabric.getWeftExtraYarn();
        Yarn[] warpExtraYarn = objFabric.getWarpExtraYarn();
        
        int warpCount = warpYarn.length;
        int weftCount = weftYarn.length;
        int weftExtraCount = objFabric.getIntExtraWeft();
        int warpExtraCount = objFabric.getIntExtraWarp();
        int dpi = objFabric.getObjConfiguration().getIntDPI();
        int warpFactor = dpi/objFabric.getIntEPI();
        int weftFactor = dpi/objFabric.getIntPPI();
        
        BufferedImage bufferedImage = new BufferedImage(intLength, intHeight,BufferedImage.TYPE_INT_RGB);        
        int rgb = 0;
        for(int x = 0; x < intHeight; x++) {
            for(int y = 0; y < intLength; y++) {
                if(repeatMatrix[x][y]<=1){
                    rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).getRGB();
                } else if(weftExtraCount>0){
                    for(int a=0; a<weftExtraCount; a++){
                        if(repeatMatrix[x][y]==(a+2)){
                            rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getRGB();
                        }
                    }
                } else{
                    rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).getRGB();
                }
                bufferedImage.setRGB(y, x, rgb);
            }
        }
        repeatMatrix = null;
        return bufferedImage;        
    }
    public BufferedImage plotCrossSectionBaseGraphView(Fabric objFabric, byte[][] repeatMatrix, int intLength, int intHeight){
        Yarn[] weftYarn = objFabric.getWeftYarn();
        Yarn[] warpYarn = objFabric.getWarpYarn();
        Yarn[] weftExtraYarn = objFabric.getWeftExtraYarn();
        Yarn[] warpExtraYarn = objFabric.getWarpExtraYarn();
        
        int warpCount = warpYarn.length;
        int weftCount = weftYarn.length;
        int weftExtraCount = objFabric.getIntExtraWeft();
        int warpExtraCount = objFabric.getIntExtraWarp();
        int dpi = objFabric.getObjConfiguration().getIntDPI();
        int warpFactor = dpi/objFabric.getIntEPI();
        int weftFactor = dpi/objFabric.getIntPPI();
        
        BufferedImage bufferedImage = new BufferedImage(intLength, intHeight,BufferedImage.TYPE_INT_RGB);        
        int rgb = 0;
        for(int x = 0; x < intHeight; x++) {
            for(int y = 0; y < intLength; y++) {
                if(repeatMatrix[x][y]==1){
                    rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).getRGB();
                } else if(repeatMatrix[x][y]<=1){
                    rgb = new Color((float)javafx.scene.paint.Color.web("#000000").getRed(),(float)javafx.scene.paint.Color.web("#000000").getGreen(),(float)javafx.scene.paint.Color.web("#000000").getBlue()).getRGB();
                } else if(weftExtraCount>0){
                    for(int a=0; a<weftExtraCount; a++){
                        if(repeatMatrix[x][y]==(a+2)){
                            rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getRGB();
                        }
                    }
                } else{
                    rgb = new Color((float)javafx.scene.paint.Color.web("#000000").getRed(),(float)javafx.scene.paint.Color.web("#000000").getGreen(),(float)javafx.scene.paint.Color.web("#000000").getBlue()).getRGB();
                }
                bufferedImage.setRGB(y, x, rgb);
            }
        }
        repeatMatrix = null;
        return bufferedImage;        
    }
    
    public BufferedImage plotFabricEditOLD(byte[][] editMatrix,int bands, int intLength,int intHeight){
        BufferedImage bufferedImage = new BufferedImage(intLength*bands, intHeight*bands,BufferedImage.TYPE_INT_RGB);        
        int rgb = 0;
        for(int x = 0, p = 0; x < intHeight; x++) {
            for(int y = 0, q = 0; y < intLength; y++) {
                for(int i = 0; i < bands; i++) {
                    for(int j = 0; j < bands; j++) {                        
                        if(editMatrix[x][y]==1){
                            rgb = new Color((float)javafx.scene.paint.Color.web("#000000").getRed(),(float)javafx.scene.paint.Color.web("#000000").getGreen(),(float)javafx.scene.paint.Color.web("#000000").getBlue()).getRGB();
                        } else if(editMatrix[x][y]==0){
                            rgb = new Color((float)javafx.scene.paint.Color.web("#FFFFFF").getRed(),(float)javafx.scene.paint.Color.web("#FFFFFF").getGreen(),(float)javafx.scene.paint.Color.web("#FFFFFF").getBlue()).getRGB();
                        } else if(editMatrix[x][y]>1){
                            rgb = new Color((float)javafx.scene.paint.Color.web("#0000FF").getRed(),(float)javafx.scene.paint.Color.web("#0000FF").getGreen(),(float)javafx.scene.paint.Color.web("#0000FF").getBlue()).getRGB();
                        } else{
                            rgb = new Color((float)javafx.scene.paint.Color.web("#FF0000").getRed(),(float)javafx.scene.paint.Color.web("#FF0000").getGreen(),(float)javafx.scene.paint.Color.web("#FF0000").getBlue()).getRGB();
                        }
                        bufferedImage.setRGB(q+j, p+i, rgb);
                    }
                }
                q+=bands;
            }
            p+=bands;
        }
        editMatrix = null;
        return bufferedImage;
    }
    
    public BufferedImage plotFabricEdit(Fabric objFabric, byte[][] editMatrix,int bands, int intLength,int intHeight){
        Yarn[] weftYarn = objFabric.getWeftYarn();
        Yarn[] warpYarn = objFabric.getWarpYarn();
        Yarn[] weftExtraYarn = objFabric.getWeftExtraYarn();
        Yarn[] warpExtraYarn = objFabric.getWarpExtraYarn();
        
        int warpCount = warpYarn.length;
        int weftCount = weftYarn.length;
        int weftExtraCount = objFabric.getIntExtraWeft();
        int warpExtraCount = objFabric.getIntExtraWarp();
        
        BufferedImage bufferedImage = new BufferedImage(intLength*bands, intHeight*bands,BufferedImage.TYPE_INT_RGB);        
        int rgb = 0;
        for(int x = 0, p = 0; x < intHeight; x++) {
            for(int y = 0, q = 0; y < intLength; y++) {
                for(int i = 0; i < bands; i++) {
                    for(int j = 0; j < bands; j++) {                        
                        if(editMatrix[x][y]==1)
                            rgb = new Color((float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).getRGB(); 
                        else if(editMatrix[x][y]==0)
                            rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRGB(); 
                        else if(editMatrix[x][y]>1){
                            for(int a=0; a<weftExtraCount; a++)
                                if(editMatrix[x][y]==(a+2))
                                    rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getRGB();
                        }
                        else
                            rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRGB(); 
                        bufferedImage.setRGB(q+j, p+i, rgb);
                    }
                }
                q+=bands;
            }
            p+=bands;
        }
        weftYarn = null;
        warpYarn = null;
        weftExtraYarn = null;
        warpExtraYarn = null;
        editMatrix = null;
        System.gc();
        
        return bufferedImage;        
    }
    
    public BufferedImage plotFabricEditGrid(Fabric objFabric, byte[][] editMatrix,int bands, int intLength,int intHeight){
        Yarn[] weftYarn = objFabric.getWeftYarn();
        Yarn[] warpYarn = objFabric.getWarpYarn();
        Yarn[] weftExtraYarn = objFabric.getWeftExtraYarn();
        Yarn[] warpExtraYarn = objFabric.getWarpExtraYarn();
        
        int warpCount = warpYarn.length;
        int weftCount = weftYarn.length;
        int weftExtraCount = objFabric.getIntExtraWeft();
        int warpExtraCount = objFabric.getIntExtraWarp();
        
        BufferedImage bufferedImage = new BufferedImage(intLength*bands, intHeight*bands,BufferedImage.TYPE_INT_RGB);        
        int rgb = 0;
        for(int x = 0, p = 0; x < intHeight; x++) {
            for(int y = 0, q = 0; y < intLength; y++) {                
                for(int i = 0; i < bands; i++) {
                    for(int j = 0; j < bands; j++) {                        
                        if(editMatrix[x][y]==1)
                            rgb = new Color((float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).getRGB(); 
                        else if(editMatrix[x][y]==0)
                            rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRGB(); 
                        else if(editMatrix[x][y]>1){
                            for(int a=0; a<weftExtraCount; a++)
                                if(editMatrix[x][y]==(a+2))
                                    rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getRGB();
                        }
                        else
                            rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRGB(); 
                        bufferedImage.setRGB(q+j, p+i, rgb);
                    }
                }
                q+=bands;
            }
            p+=bands;            
        }
        for(int x = 0; x < intHeight; x++) {
            rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRGB(); 
            bufferedImage.setRGB(0, x, rgb);
        }
        
        weftYarn = null;
        warpYarn = null;
        weftExtraYarn = null;
        warpExtraYarn = null;
        editMatrix = null;
        System.gc();
        
        return bufferedImage;        
    }
    
    public void fabricImageOLD(Fabric objFabric, int intWeft, int intWarp) {   
        try {
            Yarn[] weftYarn = objFabric.getWeftYarn();
            Yarn[] warpYarn = objFabric.getWarpYarn();
            Yarn[] weftExtraYarn = objFabric.getWeftExtraYarn();
            Yarn[] warpExtraYarn = objFabric.getWarpExtraYarn();

            int warpCount = warpYarn.length;
            int weftCount = weftYarn.length;
            int weftExtraCount = objFabric.getIntExtraWeft();
            int warpExtraCount = objFabric.getIntExtraWarp();
            int dpi = objFabric.getObjConfiguration().getIntDPI();
            int warpFactor = dpi/objFabric.getIntEPI();
            int weftFactor = dpi/objFabric.getIntPPI();

            BufferedImage bufferedImage = new BufferedImage(intWarp*3, intWeft*3,BufferedImage.TYPE_INT_RGB);        
            int bands = 3;
            int rgb = 0;
            for(int x = 0, p = 0; x < intWeft; x++) {
                for(int y = 0, q = 0; y < intWarp; y++) {
                    for(int i = 0; i < bands; i++) {
                        for(int j = 0; j < bands; j++) {                        
                            if(objFabric.getFabricMatrix()[x][y]==1){
                                if(j==0)
                                    rgb = new Color((float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).brighter().getRGB();
                                else if(j==2)
                                    rgb = new Color((float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).darker().getRGB();
                                else
                                    rgb = new Color((float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).getRGB();
                            } else if(objFabric.getFabricMatrix()[x][y]==0){
                                if(j==0)
                                    rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).brighter().getRGB();
                                else if(j==2)
                                    rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).darker().getRGB();
                                else
                                    rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRGB();
                            } else if(weftExtraCount>0){
                                for(int a=0; a<weftExtraCount; a++){
                                    if(objFabric.getFabricMatrix()[x][y]==(a+2)){
                                        if(i==0)
                                            rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).brighter().getRGB();
                                        else if(i==2)
                                            rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).darker().getRGB();
                                        else
                                            rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getRGB();
                                    }
                                }
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
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();  
            objFabric.setBytFabricIcon(imageInByte);
            imageInByte = null;
            baos.close();
            bufferedImage = null;            
        } catch (IOException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
        }
    }
    
    public void fabricImage(Fabric objFabric, int intWeft, int intWarp) {   
        try {
            Yarn[] weftYarn = objFabric.getWeftYarn();
            Yarn[] warpYarn = objFabric.getWarpYarn();
            Yarn[] weftExtraYarn = objFabric.getWeftExtraYarn();
            Yarn[] warpExtraYarn = objFabric.getWarpExtraYarn();

            int warpCount = warpYarn.length;
            int weftCount = weftYarn.length;
            int weftExtraCount = objFabric.getIntExtraWeft();
            int warpExtraCount = objFabric.getIntExtraWarp();
            int dpi = objFabric.getObjConfiguration().getIntDPI();
            int warpFactor = dpi/objFabric.getIntEPI();
            int weftFactor = dpi/objFabric.getIntPPI();

            BufferedImage bufferedImage = new BufferedImage(intWarp, intWeft, BufferedImage.TYPE_INT_RGB);        
            
            int rgb = 0;
            for(int x = 0; x < intWeft; x++) {
                for(int y = 0; y < intWarp; y++) {
                    if(objFabric.getFabricMatrix()[x][y]==1)
                        rgb = new Color((float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(warpYarn[y%warpCount].getStrYarnColor()).getBlue()).getRGB(); 
                    else if(objFabric.getFabricMatrix()[x][y]==0)
                        rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRGB(); 
                    else if(weftExtraCount>0){
                        for(int a=0; a<weftExtraCount; a++)
                            if(objFabric.getFabricMatrix()[x][y]==(a+2))
                                rgb = new Color((float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftExtraYarn[a].getStrYarnColor()).getBlue()).getRGB();
                    }
                    else
                        rgb = new Color((float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(weftYarn[x%weftCount].getStrYarnColor()).getBlue()).getRGB(); 
                    bufferedImage.setRGB(y, x, rgb);
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();  
            objFabric.setBytFabricIcon(imageInByte);
            imageInByte = null;
            baos.close();
            bufferedImage = null;            
        } catch (IOException ex) {
            new Logging("SEVERE",FabricView.class.getName(),ex.toString(),ex);
        }
    }
}
