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

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import javax.xml.bind.DatatypeConverter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Aatif Ahmad Khan
 */
public class DbUtility {
    
    Connection connection = null; //DbConnect.getConnection();
    
    public DbUtility() throws SQLException{
        connection = DbConnect.getConnection();
    }
    
    public DbUtility(boolean isDB) throws SQLException{
        if(isDB)
            connection = DbConnect.getConnection();        
    }

    public boolean importSQLFileData(String strFilePath) {
        try {
            if (DbConnect.restoreDBfromSQL(strFilePath) == 0) {
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(DbUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    
    
    
//--------------------------Export Fabric ----------------------------
    public String exportFabric(Configuration objConfiguration) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String strData = "";
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< exportFabric() >>>>>>>>>>>",null);
        try {
            String cond = "`mla_fabric_library`.USERID = '"+objConfiguration.getObjUser().getStrUserID()+"' AND `mla_fabric_library`.`ACCESS`!='"+new IDGenerator().getUserAcess("FABRIC_LIBRARY")+"'";
            cond += "  AND `mla_design_library`.USERID = '"+objConfiguration.getObjUser().getStrUserID()+"' AND `mla_design_library`.`ACCESS`!='"+new IDGenerator().getUserAcess("ARTWORK_LIBRARY")+"'";
            cond += "  AND `mla_weave_library`.USERID  = '"+objConfiguration.getObjUser().getStrUserID()+"' AND `mla_weave_library`.`ACCESS` !='"+new IDGenerator().getUserAcess("WEAVE_LIBRARY")+"';";
            strQuery = "SELECT `mla_fabric_library`.`ID`, `mla_fabric_library`.`ARTWORKID`, `mla_fabric_library`.`BASEWEAVEID` FROM `mla_fabric_library` LEFT JOIN `mla_design_library` ON `mla_fabric_library`.ARTWORKID=`mla_design_library` LEFT JOIN `mla_weave_library` ON `mla_fabric_library`.BASEWEAVEID=`mla_weave_library`. WHERE "+cond;
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            while(oResultSet.next()) {
                strData+=exportEachFabric(objConfiguration, oResultSet.getString("ID").toString());                
            }
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"exportFabric : "+strQuery,ex);
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
                    new Logging("SEVERE",DbUtility.class.getName(),"exportFabric() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> exportFabric() <<<<<<<<<<<"+strQuery,null);
        return strData;
    }
    
    
    public String exportEachFabric(Configuration objConfiguration, String strFabricID) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String strData="";
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< exportEachFabric() >>>>>>>>>>>",null);
        try {
            String cond = "ID = '"+strFabricID+"' AND USERID = '"+objConfiguration.getObjUser().getStrUserID()+"' AND `ACCESS`!='"+new IDGenerator().getUserAcess("FABRIC_LIBRARY")+"';";
            strQuery = "SELECT * FROM `mla_fabric_library` WHERE "+cond;
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            while(oResultSet.next()) {
                strData="\n LOCK TABLES `mla_fabric_library` WRITE;";
                strData+="\n REPLACE INTO `mla_fabric_library` VALUES ('"+oResultSet.getString("ID").toString()+"'," 
                        +"'"+oResultSet.getString("NAME")+"'," 
                        +"'"+oResultSet.getString("CLOTHTYPE")+"'," 
                        +"'"+oResultSet.getString("TYPE")+"'," 
                        +"'"+oResultSet.getDouble("LENGTH")+"'," 
                        +"'"+oResultSet.getDouble("WIDTH")+"'," 
                        +"'"+oResultSet.getDouble("ARTWORKLENGTH")+"'," 
                        +"'"+oResultSet.getDouble("ARTWORKWIDTH")+"'," 
                        +"'"+oResultSet.getString("ARTWORKID")+"'," 
                        +"'"+oResultSet.getString("BASEWEAVEID")+"'," 
                        +""+oResultSet.getInt("WEFT")+"," 
                        +""+oResultSet.getInt("WARP")+"," 
                        +""+oResultSet.getInt("EWEFT")+"," 
                        +""+oResultSet.getInt("EWARP")+"," 
                        +""+oResultSet.getInt("SHAFT")+"," 
                        +""+oResultSet.getInt("HOOKS")+"," 
                        +""+oResultSet.getInt("HPI")+"," 
                        +""+oResultSet.getInt("REEDCOUNT")+"," 
                        +""+oResultSet.getInt("DENTS")+"," 
                        +""+oResultSet.getInt("TPD")+"," 
                        +""+oResultSet.getInt("EPI")+"," 
                        +""+oResultSet.getInt("PPI")+"," 
                        +""+oResultSet.getInt("BINDING")+"," 
                        +""+oResultSet.getInt("PROTECTION")+"," 
                        +""+oResultSet.getInt("ARTWORKSIZE")+"," 
                        +"'"+oResultSet.getString("WARPPATTERNID")+"'," 
                        +"'"+oResultSet.getString("WEFTPATTERNID")+"'," 
                        +"'"+oResultSet.getBytes("ICON")+"'," 
                        +"'"+oResultSet.getString("FILE")+"'," 
                        +"'"+oResultSet.getString("RDATA")+"'," 
                        +"'"+oResultSet.getString("ACCESS")+"',"
                        +"'"+oResultSet.getString("USERID")+"',"
                        +"'"+oResultSet.getTimestamp("UDATE")+"');";
                strData+="\n UNLOCK TABLES;";
                
                if(oResultSet.getString("ARTWORKID")!=null)
                    strData+=exportEachDesign(oResultSet.getString("ARTWORKID").toString());
                strData+=exportEachWeave(oResultSet.getString("BASEWEAVEID").toString());
                strData+=exportFabricArtwork(oResultSet.getString("ID").toString());
                strData+=exportFabricPallets(oResultSet.getString("ID").toString());
                strData+=exportFabricThread(oResultSet.getString("ID").toString());
            }
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"exportFabric : "+strQuery,ex);
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
                    new Logging("SEVERE",DbUtility.class.getName(),"exportEachFabric() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> exportEachFabric() <<<<<<<<<<<"+strQuery,null);
        return strData;
    }
    
    public String exportFabricArtwork(String strFabricID) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< exportFabricArtwork() >>>>>>>>>>>",null);
        String strData="\n LOCK TABLES `mla_fabric_artwork` WRITE;";
        try {
            strQuery = "select * from `mla_fabric_artwork` WHERE fabric_id = '"+strFabricID+"';";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            int intFirstRow=0;
            while(oResultSet.next()) {
                strData+="\n REPLACE INTO `mla_fabric_artwork` VALUES";
                String strWeaveId =oResultSet.getString("weave_id");
                strData+="("+oResultSet.getInt("ID")+"," 
                        +"'"+oResultSet.getString("fabric_id").toString()+"'," 
                        +""+oResultSet.getInt("color_id")+"," 
                        +"'"+oResultSet.getString("color")+"'," 
                        +""+oResultSet.getInt("is_background")+"," 
                        +"'"+strWeaveId+"'," 
                        +""+oResultSet.getInt("serial")+");";
                if(strWeaveId!=null)
                    strData+=exportEachWeave(strWeaveId);
                intFirstRow++;
            }
            if(intFirstRow>0)
                strData+=";";
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"exportFabricArtwork : "+strQuery,ex);
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
                    new Logging("SEVERE",DbUtility.class.getName(),"exportFabricArtwork() : Error while closing connection"+e,ex);
                }
            }
        }
        strData+="\n UNLOCK TABLES;";
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> exportFabricArtwork() <<<<<<<<<<<"+strQuery,null);
        return strData;
    }
    
    
    public String exportFabricThread(String strFabricID) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< exportFabricThread() >>>>>>>>>>>",null);
        String strData="\n LOCK TABLES `mla_fabric_thread` WRITE;";
        try {
            strQuery = "select * from `mla_fabric_thread` WHERE fabric_id = '"+strFabricID+"';";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            int intFirstRow=0;
            while(oResultSet.next()) {
                strData+="\n REPLACE INTO `mla_fabric_thread` VALUES";
                String strYarnId =oResultSet.getString("yarn_id");
                strData+="("+oResultSet.getInt("id")+"," 
                        +"'"+oResultSet.getString("fabric_id")+"'," 
                        +"'"+strYarnId+"'," 
                        +"'"+oResultSet.getString("symbol")+"'," 
                        +""+oResultSet.getInt("repeat")+"," 
                        +""+oResultSet.getInt("serial")+");";
                if(strYarnId!=null)
                    strData+=exportEachYarn(strYarnId);
            }
            if(intFirstRow>0)
                strData+=";";
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"exportFabricThread : "+strQuery,ex);
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
                    new Logging("SEVERE",DbUtility.class.getName(),"exportFabricThread() : Error while closing connection"+e,ex);
                }
            }
        }
        strData+="\n UNLOCK TABLES;";
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> exportFabricThread() <<<<<<<<<<<"+strQuery,null);
        return strData;
    }
    
    
    public StringBuffer exportFabricPallets(String strFabricID) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< exportFabricPallets() >>>>>>>>>>>",null);
        StringBuffer strReturn=new StringBuffer();
        strReturn.append("\n LOCK TABLES `mla_fabric_pallets` WRITE;");
        try {
            strQuery = "select * from `mla_fabric_pallets` WHERE fabric_id = '"+strFabricID+"';";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            int intFirstRow=0;
            while(oResultSet.next()) {
                if(intFirstRow==0)
                    strReturn.append("\n REPLACE INTO `mla_fabric_pallets` VALUES");
                else 
                    strReturn.append(",\n");
                
                strReturn.append("('"+oResultSet.getString("fabric_id").toString()+"'," 
                        +"'"+oResultSet.getString("warp_A")+"'," 
                        +"'"+oResultSet.getString("warp_B")+"'," 
                        +"'"+oResultSet.getString("warp_C")+"'," 
                        +"'"+oResultSet.getString("warp_D")+"'," 
                        +"'"+oResultSet.getString("warp_E")+"'," 
                        +"'"+oResultSet.getString("warp_F")+"'," 
                        +"'"+oResultSet.getString("warp_G")+"'," 
                        +"'"+oResultSet.getString("warp_H")+"'," 
                        +"'"+oResultSet.getString("warp_I")+"'," 
                        +"'"+oResultSet.getString("warp_J")+"'," 
                        +"'"+oResultSet.getString("warp_K")+"'," 
                        +"'"+oResultSet.getString("warp_L")+"'," 
                        +"'"+oResultSet.getString("warp_M")+"'," 
                        +"'"+oResultSet.getString("warp_N")+"'," 
                        +"'"+oResultSet.getString("warp_O")+"'," 
                        +"'"+oResultSet.getString("warp_P")+"'," 
                        +"'"+oResultSet.getString("warp_Q")+"'," 
                        +"'"+oResultSet.getString("warp_R")+"'," 
                        +"'"+oResultSet.getString("warp_S")+"'," 
                        +"'"+oResultSet.getString("warp_T")+"'," 
                        +"'"+oResultSet.getString("warp_U")+"'," 
                        +"'"+oResultSet.getString("warp_V")+"'," 
                        +"'"+oResultSet.getString("warp_W")+"'," 
                        +"'"+oResultSet.getString("warp_X")+"'," 
                        +"'"+oResultSet.getString("warp_Y")+"'," 
                        +"'"+oResultSet.getString("warp_Z")+"'," 
                        +"'"+oResultSet.getString("weft_a")+"'," 
                        +"'"+oResultSet.getString("weft_b")+"'," 
                        +"'"+oResultSet.getString("weft_c")+"'," 
                        +"'"+oResultSet.getString("weft_d")+"'," 
                        +"'"+oResultSet.getString("weft_e")+"'," 
                        +"'"+oResultSet.getString("weft_f")+"'," 
                        +"'"+oResultSet.getString("weft_g")+"'," 
                        +"'"+oResultSet.getString("weft_h")+"'," 
                        +"'"+oResultSet.getString("weft_i")+"'," 
                        +"'"+oResultSet.getString("weft_j")+"'," 
                        +"'"+oResultSet.getString("weft_k")+"'," 
                        +"'"+oResultSet.getString("weft_l")+"'," 
                        +"'"+oResultSet.getString("weft_m")+"'," 
                        +"'"+oResultSet.getString("weft_n")+"'," 
                        +"'"+oResultSet.getString("weft_o")+"'," 
                        +"'"+oResultSet.getString("weft_p")+"'," 
                        +"'"+oResultSet.getString("weft_q")+"'," 
                        +"'"+oResultSet.getString("weft_r")+"'," 
                        +"'"+oResultSet.getString("weft_s")+"'," 
                        +"'"+oResultSet.getString("weft_t")+"'," 
                        +"'"+oResultSet.getString("weft_u")+"'," 
                        +"'"+oResultSet.getString("weft_v")+"'," 
                        +"'"+oResultSet.getString("weft_w")+"'," 
                        +"'"+oResultSet.getString("weft_x")+"'," 
                        +"'"+oResultSet.getString("weft_y")+"'," 
                        +"'"+oResultSet.getString("weft_z")+"')");
                intFirstRow++;
            }
            if(intFirstRow>0)
                strReturn.append(";");
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"exportFabricPallets : "+strQuery,ex);
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
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",DbUtility.class.getName(),"exportFabricPallets() : Error while closing connection"+e,ex);
                }
            }
        }
        strReturn.append("\n UNLOCK TABLES;");
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> exportFabricPallets() <<<<<<<<<<<"+strQuery,null);
        return strReturn;
    }
    
//-----------------------Export Artwork --------------------------
    public String exportEachDesign(String strArtworkID) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String strData ="";
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< exportEachDesign() >>>>>>>>>>>",null);
        try {
            strQuery = "SELECT * FROM `mla_design_library` WHERE ID = '"+strArtworkID+"';";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            while(oResultSet.next()) {
                strData="\n LOCK TABLES `mla_design_library` WRITE;";
                strData+="\n REPLACE INTO `mla_design_library` VALUES ("
                        +"'"+oResultSet.getString("ID").toString()+"'," 
                        +"'"+oResultSet.getString("NAME").toString()+"'," 
                        +"'"+oResultSet.getString("FILE")+"'," 
                        +"'"+oResultSet.getString("BACKGROUND")+"'," 
                        +"'"+oResultSet.getString("ACCESS")+"'," 
                        +"'"+oResultSet.getString("USERID")+"'," 
                        +"'"+oResultSet.getTimestamp("UDATE")+"');";
                strData+="\n UNLOCK TABLES;";
            }
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"exportEachDesign : "+strQuery,ex);
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
                    new Logging("SEVERE",DbUtility.class.getName(),"exportEachDesign() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> exportEachDesign() <<<<<<<<<<<"+strQuery,null);
        return strData;
    }
    
    public String exportEachUserDesign(Configuration objConfiguration, String strArtworkID) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String strData ="";
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< exportEachUserDesign() >>>>>>>>>>>",null);
        try {
            String cond = "ID = '"+strArtworkID+"' AND USERID = '"+objConfiguration.getObjUser().getStrUserID()+"' AND `ACCESS`!='"+new IDGenerator().getUserAcess("ARTWORK_LIBRARY")+"';";
            strQuery = "SELECT * FROM `mla_design_library` WHERE "+cond;
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            while(oResultSet.next()) {
                strData="\n LOCK TABLES `mla_design_library` WRITE;";
                strData+="\n REPLACE INTO `mla_design_library` VALUES ("
                        +"'"+oResultSet.getString("ID").toString()+"'," 
                        +"'"+oResultSet.getString("NAME").toString()+"'," 
                        +"'"+oResultSet.getString("FILE")+"'," 
                        +"'"+oResultSet.getString("BACKGROUND")+"'," 
                        +"'"+oResultSet.getString("ACCESS")+"'," 
                        +"'"+oResultSet.getString("USERID")+"'," 
                        +"'"+oResultSet.getTimestamp("UDATE")+"');";
                strData+="\n UNLOCK TABLES;";
            }
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"exportEachDesign : "+strQuery,ex);
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
                    new Logging("SEVERE",DbUtility.class.getName(),"exportEachDesign() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> exportEachDesign() <<<<<<<<<<<"+strQuery,null);
        return strData;
    }
    public String exportDesign(Configuration objConfiguration) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< exportDesign() >>>>>>>>>>>",null);
        String strData="\n LOCK TABLES `mla_design_library` WRITE;";
        try {
            String cond = "USERID = '"+objConfiguration.getObjUser().getStrUserID()+"' AND `ACCESS`!='"+new IDGenerator().getUserAcess("ARTWORK_LIBRARY")+"';";
            strQuery = "select * from `mla_design_library` WHERE "+cond;
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            int intFirstRow=0;
            while(oResultSet.next()) {
                if(intFirstRow==0)
                    strData+="\n REPLACE INTO `mla_design_library` VALUES";
                else 
                    strData+=",\n";
                
                strData+="('"+oResultSet.getString("ID").toString()+"'," 
                        +"'"+oResultSet.getString("NAME").toString()+"'," 
                        //"'"+oResultSet.getBytes("FILE")+"'," 
                        +"'"+oResultSet.getString("FILE")+"'," 
                        +"'"+oResultSet.getString("BACKGROUND")+"'," 
                        +"'"+oResultSet.getString("ACCESS")+"'," 
                        +"'"+oResultSet.getString("USERID")+"'," 
                        +"'"+oResultSet.getTimestamp("UDATE")+"')";
                intFirstRow++;
            }
            if(intFirstRow>0)
                strData+=";";
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"exportDesign : "+strQuery,ex);
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
                    new Logging("SEVERE",DbUtility.class.getName(),"exportDesign() : Error while closing connection"+e,ex);
                }
            }
        }
        strData+="\n UNLOCK TABLES;";
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> exportDesign() <<<<<<<<<<<"+strQuery,null);
        return strData;
    }
    public String exportAllDesign() {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< exportAllDesign() >>>>>>>>>>>",null);
        String strData="\n LOCK TABLES `mla_design_library` WRITE;";
        try {
            strQuery = "select * from `mla_design_library` WHERE 1;";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            int intFirstRow=0;
            while(oResultSet.next()) {
                if(intFirstRow==0)
                    strData+="\n REPLACE INTO `mla_design_library` VALUES";
                else 
                    strData+=",\n";
                
                strData+="('"+oResultSet.getString("ID").toString()+"'," 
                        +"'"+oResultSet.getString("NAME").toString()+"'," 
                        +"'"+oResultSet.getString("FILE")+"'," 
                        +"'"+oResultSet.getString("BACKGROUND")+"'," 
                        +"'"+oResultSet.getString("ACCESS")+"'," 
                        +"'"+oResultSet.getString("USERID")+"'," 
                        +"'"+oResultSet.getTimestamp("UDATE")+"')";
                intFirstRow++;
            }
            if(intFirstRow>0)
                strData+=";";
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"exportAllDesign : "+strQuery,ex);
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
                    new Logging("SEVERE",DbUtility.class.getName(),"exportAllDesign() : Error while closing connection"+e,ex);
                }
            }
        }
        strData+="\n UNLOCK TABLES;";
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> exportAllDesign() <<<<<<<<<<<"+strQuery,null);
        return strData;
    }

//------------------------Export Temp Artworks----------------
    public String exportArtwork(Configuration objConfiguration) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< exportArtwork() >>>>>>>>>>>",null);
        String strData="\n LOCK TABLES `mla_artwork_library` WRITE;";
        try {
            String cond = "USERID = '"+objConfiguration.getObjUser().getStrUserID()+"' AND `ACCESS`!='"+new IDGenerator().getUserAcess("ARTWORK_LIBRARY")+"';";
            strQuery = "select * from `mla_artwork_library` WHERE "+cond;
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            int intFirstRow=0;
            while(oResultSet.next()) {
                if(intFirstRow==0)
                    strData+="\n REPLACE INTO `mla_artwork_library` VALUES";
                else 
                    strData+=",\n";
                
                strData+="('"+oResultSet.getString("ID").toString()+"'," 
                        +"'"+oResultSet.getString("NAME").toString()+"'," 
                        //"'"+oResultSet.getBytes("FILE")+"'," 
                        +"'"+oResultSet.getString("FILE")+"'," 
                        +"'"+oResultSet.getString("BACKGROUND")+"'," 
                        +"'"+oResultSet.getString("ACCESS")+"'," 
                        +"'"+oResultSet.getString("USERID")+"'," 
                        +"'"+oResultSet.getTimestamp("UDATE")+"')";
                intFirstRow++;
            }
            if(intFirstRow>0)
                strData+=";";
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"exportArtwork : "+strQuery,ex);
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
                    new Logging("SEVERE",DbUtility.class.getName(),"exportArtwork() : Error while closing connection"+e,ex);
                }
            }
        }
        strData+="\n UNLOCK TABLES;";
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> exportArtwork() <<<<<<<<<<<"+strQuery,null);
        return strData;
    }

//-----------------------Export Weave --------------------------
    public String exportEachWeave(String strWeaveID) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String strData = "";
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< exportEachWeave() >>>>>>>>>>>",null);
        try {
            strQuery = "SELECT * FROM `mla_weave_library` WHERE `ID`='"+strWeaveID+"';";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            while(oResultSet.next()) {
                strData="\n LOCK TABLES `mla_weave_library` WRITE;";
                strData+="\n REPLACE INTO `mla_weave_library` VALUES ("
                        +"'"+oResultSet.getString("ID").toString()+"'," 
                        +"'"+oResultSet.getString("NAME").toString()+"'," 
                        +"'"+oResultSet.getString("FILE")+"'," 
                        +"'"+oResultSet.getString("ICON")+"'," 
                        +"'"+oResultSet.getString("TYPE")+"'," 
                        +"'"+oResultSet.getString("CATEGORY")+"'," 
                        +""+oResultSet.getInt("IS_LIFTPLAN")+"," 
                        +""+oResultSet.getInt("IS_COLOR")+"," 
                        +""+oResultSet.getInt("SHAFT")+"," 
                        +""+oResultSet.getInt("TREADLE")+"," 
                        +""+oResultSet.getInt("REPEAT_X")+"," 
                        +""+oResultSet.getInt("REPEAT_Y")+"," 
                        +""+oResultSet.getInt("FLOAT_WEFT")+"," 
                        +""+oResultSet.getInt("FLOAT_WARP")+"," 
                        +"'"+oResultSet.getString("ACCESS")+"'," 
                        +"'"+oResultSet.getString("USERID")+"'," 
                        +"'"+oResultSet.getTimestamp("UDATE")+"')";
                strData+="\n UNLOCK TABLES;";            
            }
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"exportEachWeave : "+strQuery,ex);
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
                    new Logging("SEVERE",DbUtility.class.getName(),"exportEachWeave() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> exportEachWeave() <<<<<<<<<<<"+strQuery,null);
        return strData;
    }
    
    public String exportEachUserWeave(Configuration objConfiguration, String strWeaveID) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String strData = "";
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< exportEachUserWeave() >>>>>>>>>>>",null);
        try {
            String cond = "ID = '"+strWeaveID+"' AND USERID = '"+objConfiguration.getObjUser().getStrUserID()+"' AND `ACCESS`!='"+new IDGenerator().getUserAcess("WEAVE_LIBRARY")+"';";
            strQuery = "SELECT * FROM `mla_weave_library` WHERE "+cond;
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            while(oResultSet.next()) {
                strData="\n LOCK TABLES `mla_weave_library` WRITE;";
                strData+="\n REPLACE INTO `mla_weave_library` VALUES ("
                        +"'"+oResultSet.getString("ID").toString()+"'," 
                        +"'"+oResultSet.getString("NAME").toString()+"'," 
                        +"'"+oResultSet.getString("FILE")+"'," 
                        +"'"+oResultSet.getString("ICON")+"'," 
                        +"'"+oResultSet.getString("TYPE")+"'," 
                        +"'"+oResultSet.getString("CATEGORY")+"'," 
                        +""+oResultSet.getInt("IS_LIFTPLAN")+"," 
                        +""+oResultSet.getInt("IS_COLOR")+"," 
                        +""+oResultSet.getInt("SHAFT")+"," 
                        +""+oResultSet.getInt("TREADLE")+"," 
                        +""+oResultSet.getInt("REPEAT_X")+"," 
                        +""+oResultSet.getInt("REPEAT_Y")+"," 
                        +""+oResultSet.getInt("FLOAT_WEFT")+"," 
                        +""+oResultSet.getInt("FLOAT_WARP")+"," 
                        +"'"+oResultSet.getString("ACCESS")+"'," 
                        +"'"+oResultSet.getString("USERID")+"'," 
                        +"'"+oResultSet.getTimestamp("UDATE")+"')";
                strData+="\n UNLOCK TABLES;";            
            }
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"exportEachWeave : "+strQuery,ex);
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
                    new Logging("SEVERE",DbUtility.class.getName(),"exportEachWeave() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> exportEachWeave() <<<<<<<<<<<"+strQuery,null);
        return strData;
    }
    public String exportWeave(Configuration objConfiguration) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< exportWeave() >>>>>>>>>>>",null);
        String strData="\n LOCK TABLES `mla_weave_library` WRITE;";
        try {
            String cond = "USERID = '"+objConfiguration.getObjUser().getStrUserID()+"' AND `ACCESS`!='"+new IDGenerator().getUserAcess("WEAVE_LIBRARY")+"';";
            strQuery = "select * from `mla_weave_library` WHERE "+cond;
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            int intFirstRow=0;
            while(oResultSet.next()) {
                if(intFirstRow==0)
                    strData+="\n REPLACE INTO `mla_weave_library` VALUES";
                else 
                    strData+=",\n";
                
                /*
                Blob blob = oResultSet.getBlob("ICON");  
                String strIcon = blob != null ? new String(blob.getBytes(1, (int) blob.length())) : "";  
                */
                
                /*
                Blob blob = oResultSet.getBlob("ICON");  
                String strIcon = blob != null ? new String(blob.getBytes(1, (int) blob.length()),"UTF-8") : "";  
                */
                
                /*
                String strIcon = decodeCharByteArray(convertBlob(oResultSet.getBlob("ICON")), "UTF-8");
                */
                /*
                String strIcon = DatatypeConverter.printBase64Binary(oResultSet.getBytes("ICON"));
                */
                InputStream bodyOut = oResultSet.getBinaryStream("ICON");
                String fileOut = "BlobOut_"+oResultSet.getString("ID")+".bin";
                saveOutputStream(fileOut,bodyOut);
                bodyOut.close();
                
                String strIcon = readFile(fileOut,StandardCharsets.UTF_8);
                
                //strIcon = bytesToHex(oResultSet.getBytes("ICON"));
                /*
                Blob blob = oResultSet.getBlob("ICON");
                byte[] bdata = blob.getBytes(1, (int) blob.length());
                String strIcon = new String(bdata);
                */
                
                /*
                String savePath = System.getProperty("user.dir") + "\\"+intFirstRow+"backup2.png";
                File file = new File(savePath);
                FileOutputStream output = new FileOutputStream(file);
                InputStream input = oResultSet.getBinaryStream("ICON");
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    output.write(buffer);
                }
                */
            
                strData+="('"+oResultSet.getString("ID").toString()+"'," 
                        +"'"+oResultSet.getString("NAME").toString()+"'," 
                        +"'"+oResultSet.getString("FILE")+"'," 
                        +"'"+strIcon+"'," 
                        //+"'"+oResultSet.getString("ICON")+"'," 
                        +"'"+oResultSet.getString("TYPE")+"'," 
                        +"'"+oResultSet.getString("CATEGORY")+"'," 
                        +""+oResultSet.getInt("IS_LIFTPLAN")+"," 
                        +""+oResultSet.getInt("IS_COLOR")+"," 
                        +""+oResultSet.getInt("SHAFT")+"," 
                        +""+oResultSet.getInt("TREADLE")+"," 
                        +""+oResultSet.getInt("REPEAT_X")+"," 
                        +""+oResultSet.getInt("REPEAT_Y")+"," 
                        +""+oResultSet.getInt("FLOAT_WEFT")+"," 
                        +""+oResultSet.getInt("FLOAT_WARP")+"," 
                        +"'"+oResultSet.getString("ACCESS")+"'," 
                        +"'"+oResultSet.getString("USERID")+"'," 
                        +"'"+oResultSet.getTimestamp("UDATE")+"')";
                intFirstRow++;
            }
            if(intFirstRow>0)
                strData+=";";            
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"exportWeave : "+strQuery,ex);
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
                    new Logging("SEVERE",DbUtility.class.getName(),"exportWeave() : Error while closing connection"+e,ex);
                }
            }
        }
        strData+="\n UNLOCK TABLES;";
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> exportWeave() <<<<<<<<<<<"+strQuery,null);
        return strData;
    }
    
    public String exportAllWeave() {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< exportAllWeave() >>>>>>>>>>>",null);
        String strData="\n LOCK TABLES `mla_weave_library` WRITE;";
        try {
            strQuery = "select * from `mla_weave_library` WHERE 1";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            int intFirstRow=0;
            while(oResultSet.next()) {
                
                if(intFirstRow==0)
                    strData+="\n REPLACE INTO `mla_weave_library` VALUES";
                else 
                    strData+=",\n";
                
                strData+="('"+oResultSet.getString("ID").toString()+"'," 
                        +"'"+oResultSet.getString("NAME").toString()+"'," 
                        +"'"+oResultSet.getString("FILE")+"'," 
                        +"'"+oResultSet.getString("ICON")+"'," 
                        +"'"+oResultSet.getString("TYPE")+"'," 
                        +"'"+oResultSet.getString("CATEGORY")+"'," 
                        +""+oResultSet.getInt("IS_LIFTPLAN")+"," 
                        +""+oResultSet.getInt("IS_COLOR")+"," 
                        +""+oResultSet.getInt("SHAFT")+"," 
                        +""+oResultSet.getInt("TREADLE")+"," 
                        +""+oResultSet.getInt("REPEAT_X")+"," 
                        +""+oResultSet.getInt("REPEAT_Y")+"," 
                        +""+oResultSet.getInt("FLOAT_WEFT")+"," 
                        +""+oResultSet.getInt("FLOAT_WARP")+"," 
                        +"'"+oResultSet.getString("ACCESS")+"'," 
                        +"'"+oResultSet.getString("USERID")+"'," 
                        +"'"+oResultSet.getTimestamp("UDATE")+"')";
                intFirstRow++;
            }
            if(intFirstRow>0)
                strData+=";";            
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"exportAllWeave : "+strQuery,ex);
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
                    new Logging("SEVERE",DbUtility.class.getName(),"exportAllWeave() : Error while closing connection"+e,ex);
                }
            }
        }
        strData+="\n UNLOCK TABLES;";
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> exportAllWeave() <<<<<<<<<<<"+strQuery,null);
        return strData;
    }
 //--------------------- Export Yarn -------------------------------       
 
         
public String exportEachYarn(String strYarnID) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String strData = "";
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< exportEachYarn() >>>>>>>>>>>",null);
        try {
            strQuery = "SELECT * FROM `mla_yarn_library` WHERE `ID`"+strYarnID;
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            while(oResultSet.next()) {
                strData="\n LOCK TABLES `mla_yarn_library` WRITE;";
                strData+="\n REPLACE INTO `mla_yarn_library` VALUES ("
                        +"'"+oResultSet.getString("ID").toString()+"'," 
                        +"'"+oResultSet.getString("TYPE")+"'," 
                        +"'"+oResultSet.getString("NAME")+"'," 
                        +"'"+oResultSet.getString("COLOR")+"'," 
                        +"'"+oResultSet.getInt("COUNT")+"'," 
                        +"'"+oResultSet.getString("UNIT")+"'," 
                        +""+oResultSet.getInt("PLY")+"," 
                        +""+oResultSet.getInt("DFACTOR")+","                                 
                        +""+oResultSet.getString("DIAMETER")+"," 
                        +""+oResultSet.getInt("TWIST")+"," 
                        +""+oResultSet.getString("TMODEL")+"," 
                        +""+oResultSet.getInt("HAIRNESS")+"," 
                        +""+oResultSet.getInt("HPROBABILITY")+"," 
                        +""+oResultSet.getString("PRICE")+"," 
                        +""+oResultSet.getString("ICON")+","
                        +"'"+oResultSet.getString("ACCESS")+"'," 
                        +"'"+oResultSet.getString("USERID")+"'," 
                        +"'"+oResultSet.getTimestamp("UDATE")+"');";
                strData+="\n UNLOCK TABLES;";            
            }
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"exportEachYarn : "+strQuery,ex);
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
                    new Logging("SEVERE",DbUtility.class.getName(),"exportEachYarn() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> exportEachYarn() <<<<<<<<<<<"+strQuery,null);
        return strData;
    }
    
    public String exportEachUserYarn(Configuration objConfiguration, String strYarnID) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String strData = "";
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< exportEachUserYarn() >>>>>>>>>>>",null);
        try {
            String cond = "ID = '"+strYarnID+"' AND USERID = '"+objConfiguration.getObjUser().getStrUserID()+"' AND `ACCESS`!='"+new IDGenerator().getUserAcess("YARN_LIBRARY")+"';";
            strQuery = "SELECT * FROM `mla_yarn_library` WHERE "+cond;
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            while(oResultSet.next()) {
                strData="\n LOCK TABLES `mla_yarn_library` WRITE;";
                strData+="\n REPLACE INTO `mla_yarn_library` VALUES ("
                        +"'"+oResultSet.getString("ID").toString()+"'," 
                        +"'"+oResultSet.getString("TYPE")+"'," 
                        +"'"+oResultSet.getString("NAME")+"'," 
                        +"'"+oResultSet.getString("COLOR")+"'," 
                        +"'"+oResultSet.getInt("COUNT")+"'," 
                        +"'"+oResultSet.getString("UNIT")+"'," 
                        +""+oResultSet.getInt("PLY")+"," 
                        +""+oResultSet.getInt("DFACTOR")+","                                 
                        +""+oResultSet.getString("DIAMETER")+"," 
                        +""+oResultSet.getInt("TWIST")+"," 
                        +""+oResultSet.getString("TMODEL")+"," 
                        +""+oResultSet.getInt("HAIRNESS")+"," 
                        +""+oResultSet.getInt("HPROBABILITY")+"," 
                        +""+oResultSet.getString("PRICE")+"," 
                        +""+oResultSet.getString("ICON")+","
                        +"'"+oResultSet.getString("ACCESS")+"'," 
                        +"'"+oResultSet.getString("USERID")+"'," 
                        +"'"+oResultSet.getTimestamp("UDATE")+"');";
                strData+="\n UNLOCK TABLES;";            
            }
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"exportEachYarn : "+strQuery,ex);
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
                    new Logging("SEVERE",DbUtility.class.getName(),"exportEachYarn() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> exportEachYarn() <<<<<<<<<<<"+strQuery,null);
        return strData;
    }

    
 //----------------------------------------------------------------
    public byte[] convertBlob(Blob blob) {
	if(blob==null)return null;
	try {
	    InputStream in = blob.getBinaryStream();
	    int len = (int) blob.length(); //read as long	    
            long pos = 1; //indexing starts from 1
	    byte[] bytes = blob.getBytes(pos, len);
            in.close();
	    return bytes;	    
         } catch (Exception e) {}
	 return null;
    }
    
    public String decodeCharByteArray(byte[] inputArray, String charSet) { //Ex charSet="US-ASCII"
  	Charset theCharset = Charset.forName(charSet);
	CharsetDecoder decoder = theCharset.newDecoder();
	ByteBuffer theBytes = ByteBuffer.wrap(inputArray);
	CharBuffer inputArrayChars = null;
	try {
            inputArrayChars = decoder.decode(theBytes);
	} catch (CharacterCodingException e) {}
	return inputArrayChars.toString();
    }
 
public static String readFile(String path, Charset encoding) {
    byte[] encoded= null;
    try{
        encoded = Files.readAllBytes(Paths.get(path));
    } catch (Exception e) {
      e.printStackTrace();
    }
    //return new String(encoded, encoding);
    return new String(encoded);
}
    
    public static void saveOutputStream(String name, InputStream body) {
    int c;
    try {
      OutputStream f = new FileOutputStream(name);
      while ((c=body.read())>-1) {
        f.write(c);
      }
      f.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
 
    public static String bytesToHex(byte[] bytes) {
    char[] hexArray = "0123456789ABCDEF".toCharArray();
    char[] hexChars = new char[bytes.length * 2];
    for ( int j = 0; j < bytes.length; j++ ) {
        int v = bytes[j] & 0xFF;
        hexChars[j * 2] = hexArray[v >>> 4];
        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
    }
    return new String(hexChars);
}
    
// Added 1 Mar 2017 ------------------------------------------
    public void updateFabricBlob(File blobFile){
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        FileInputStream fis=null;
        byte[] blobBytes=null;
        try {
            blobBytes=new byte[(int)blobFile.length()];
            strQuery = "UPDATE `mla_fabric_library` set `ICON` = ? where `ID` = '"+blobFile.getName().substring(0, blobFile.getName().indexOf(".png"))+"';";
            PreparedStatement pstmt = connection.prepareStatement(strQuery);
            fis=new FileInputStream(blobFile);
            fis.read(blobBytes);
            pstmt.setBytes(1, blobBytes);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"insertFabricBlob()"+strQuery,ex);
        } finally {
            try {
                new Logging("INFO",DbUtility.class.getName(),"insertFabricBlob() connection closed",null);
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
                new Logging("SEVERE",DbUtility.class.getName(),"inserFabricBlob() error closing connection",ex);
            }
        }
    }
    
    public void updateArtworkBlob(File blobFile){
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        FileInputStream fis=null;
        byte[] blobBytes=null;
        try {
            blobBytes=new byte[(int)blobFile.length()];
            System.err.println("File:"+blobFile.getName().substring(0, blobFile.getName().indexOf(".png")));
            strQuery = "UPDATE `mla_design_library` set `FILE` = ? where `ID` = '"+blobFile.getName().substring(0, blobFile.getName().indexOf(".png"))+"';";
            PreparedStatement pstmt = connection.prepareStatement(strQuery);
            fis=new FileInputStream(blobFile);
            fis.read(blobBytes);
            pstmt.setBytes(1, blobBytes);
            System.err.println(pstmt.executeUpdate());
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"insertDesignBlob()"+strQuery,ex);
        } finally {
            try {
                new Logging("INFO",DbUtility.class.getName(),"insertDesignBlob() connection closed",null);
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
                new Logging("SEVERE",DbUtility.class.getName(),"inserDesignBlob() error closing connection",ex);
            }
        }
    }
    
    public void updateWeaveBlob(File blobFile){
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        FileInputStream fis=null;
        byte[] blobBytes=null;
        try {
            blobBytes=new byte[(int)blobFile.length()];
            strQuery = "UPDATE `mla_weave_library` set `ICON` = ? where `ID` = '"+blobFile.getName().substring(0, blobFile.getName().indexOf(".png"))+"';";
            PreparedStatement pstmt = connection.prepareStatement(strQuery);
            fis=new FileInputStream(blobFile);
            fis.read(blobBytes);
            pstmt.setBytes(1, blobBytes);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"insertWeaveBlob()"+strQuery,ex);
        } finally {
            try {
                new Logging("INFO",DbUtility.class.getName(),"insertWeaveBlob() connection closed",null);
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
                new Logging("SEVERE",DbUtility.class.getName(),"insertWeaveBlob() error closing connection",ex);
            }
        }
    }
    
    public void updateYarnBlob(File blobFile){
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        FileInputStream fis=null;
        byte[] blobBytes=null;
        try {
            blobBytes=new byte[(int)blobFile.length()];
            strQuery = "UPDATE `mla_yarn_library` set `ICON` = ? where `ID` = '"+blobFile.getName().substring(0, blobFile.getName().indexOf(".png"))+"';";
            PreparedStatement pstmt = connection.prepareStatement(strQuery);
            fis=new FileInputStream(blobFile);
            fis.read(blobBytes);
            pstmt.setBytes(1, blobBytes);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"updateYarnBlob()"+strQuery,ex);
        } finally {
            try {
                new Logging("INFO",DbUtility.class.getName(),"updateYarnBlob() connection closed",null);
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
                new Logging("SEVERE",DbUtility.class.getName(),"updateYarnBlob() error closing connection",ex);
            }
        }
    }
    
    public StringBuffer reExportWeave(Configuration objConfiguration, String savePath, ArrayList<File> filesToZip) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        InputStream bodyOut=null;
        StringBuffer strReturn=new StringBuffer();
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< reExportWeave() >>>>>>>>>>>",null);
        strReturn.append("\n LOCK TABLES `mla_weave_library` WRITE;");
        try {
            String cond = "USERID = '"+objConfiguration.getObjUser().getStrUserID()+"' AND `ACCESS`!='"+new IDGenerator().getUserAcess("WEAVE_LIBRARY")+"';";
            strQuery = "select * from `mla_weave_library` WHERE "+cond;
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            int intFirstRow=0;
            while(oResultSet.next()) {
                if(intFirstRow==0)
                    strReturn.append("\n REPLACE INTO `mla_weave_library` VALUES");
                else 
                    strReturn.append(",\n");
                
                bodyOut = oResultSet.getBinaryStream("ICON");
                String fileOut = savePath+oResultSet.getString("ID")+".png";
                BufferedImage bufferedImage=ImageIO.read(bodyOut);
                File pngFile=new File(fileOut);
                ImageIO.write(bufferedImage, "png", pngFile);
                filesToZip.add(pngFile);
                strReturn.append("('"+oResultSet.getString("ID").toString()+"'," 
                        +"'"+oResultSet.getString("NAME").toString()+"'," 
                        +"'"+oResultSet.getString("FILE")+"'," 
                        +"'"+""+"'," 
                        //+"'"+oResultSet.getString("ICON")+"'," 
                        +"'"+oResultSet.getString("TYPE")+"'," 
                        +"'"+oResultSet.getString("CATEGORY")+"'," 
                        +""+oResultSet.getInt("IS_LIFTPLAN")+"," 
                        +""+oResultSet.getInt("IS_COLOR")+"," 
                        +""+oResultSet.getInt("SHAFT")+"," 
                        +""+oResultSet.getInt("TREADLE")+"," 
                        +""+oResultSet.getInt("REPEAT_X")+"," 
                        +""+oResultSet.getInt("REPEAT_Y")+"," 
                        +""+oResultSet.getInt("FLOAT_WEFT")+"," 
                        +""+oResultSet.getInt("FLOAT_WARP")+"," 
                        +"'"+oResultSet.getString("ACCESS")+"'," 
                        +"'"+oResultSet.getString("USERID")+"'," 
                        +"'"+oResultSet.getTimestamp("UDATE")+"')");
                intFirstRow++;
            }
            if(intFirstRow>0)
                strReturn.append(";");            
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"reExportWeave : "+strQuery,ex);
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
                if(bodyOut!=null){
                    bodyOut.close();
                    bodyOut=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",DbUtility.class.getName(),"reExportWeave() : Error while closing connection"+e,ex);
                }
            }
        }
        strReturn.append("\n UNLOCK TABLES;");
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> reExportWeave() <<<<<<<<<<<"+strQuery,null);
        return strReturn;
    }
    
    public StringBuffer reExportDesign(Configuration objConfiguration, String savePath, ArrayList<File> filesToZip) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        InputStream bodyOut=null;
        StringBuffer strReturn=new StringBuffer();
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< reExportDesign() >>>>>>>>>>>",null);
        strReturn.append("\n LOCK TABLES `mla_design_library` WRITE;");
        try {
            String cond = "USERID = '"+objConfiguration.getObjUser().getStrUserID()+"' AND `ACCESS`!='"+new IDGenerator().getUserAcess("ARTWORK_LIBRARY")+"';";
            strQuery = "select * from `mla_design_library` WHERE "+cond;
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            int intFirstRow=0;
            while(oResultSet.next()) {
                if(intFirstRow==0)
                    strReturn.append("\n REPLACE INTO `mla_design_library` VALUES");
                else 
                    strReturn.append(",\n");
                
                bodyOut = oResultSet.getBinaryStream("FILE");
                String fileOut = savePath+oResultSet.getString("ID")+".png";
                BufferedImage bufferedImage=ImageIO.read(bodyOut);
                File pngFile=new File(fileOut);
                ImageIO.write(bufferedImage, "png", pngFile);
                filesToZip.add(pngFile);
                
                strReturn.append("('"+oResultSet.getString("ID").toString()+"'," 
                        +"'"+oResultSet.getString("NAME").toString()+"'," 
                        //"'"+oResultSet.getBytes("FILE")+"'," 
                        +"'"+""+"'," 
                        +"'"+oResultSet.getString("BACKGROUND")+"'," 
                        +"'"+oResultSet.getString("ACCESS")+"'," 
                        +"'"+oResultSet.getString("USERID")+"'," 
                        +"'"+oResultSet.getTimestamp("UDATE")+"')");
                intFirstRow++;
            }
            if(intFirstRow>0)
                strReturn.append(";");
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"reExportDesign : "+strQuery,ex);
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
                if(bodyOut!=null){
                    bodyOut.close();
                    bodyOut=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",DbUtility.class.getName(),"reExportDesign() : Error while closing connection"+e,ex);
                }
            }
        }
        strReturn.append("\n UNLOCK TABLES;");
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> reExportDesign() <<<<<<<<<<<"+strQuery,null);
        return strReturn;
    }
    
    public StringBuffer reExportFabric(Configuration objConfiguration, String savePath, ArrayList<File> filesToZip) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        StringBuffer strReturn = new StringBuffer();
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< reExportFabric() >>>>>>>>>>>",null);
        try {
            String cond = "`mla_fabric_library`.`USERID` = '"+objConfiguration.getObjUser().getStrUserID()+"' AND `mla_fabric_library`.`ACCESS`!='"+new IDGenerator().getUserAcess("FABRIC_LIBRARY")+"';";
            //cond += "  AND `mla_design_library`.USERID = '"+objConfiguration.getObjUser().getStrUserID()+"' AND `mla_design_library`.`ACCESS`!='"+new IDGenerator().getUserAcess("ARTWORK_LIBRARY")+"'";
            //cond += "  AND `mla_weave_library`.USERID  = '"+objConfiguration.getObjUser().getStrUserID()+"' AND `mla_weave_library`.`ACCESS` !='"+new IDGenerator().getUserAcess("WEAVE_LIBRARY")+"';";
            strQuery = "SELECT `mla_fabric_library`.`ID`, `mla_fabric_library`.`ARTWORKID`, `mla_fabric_library`.`BASEWEAVEID` FROM `mla_fabric_library` LEFT JOIN `mla_design_library` ON `mla_fabric_library`.`ARTWORKID`=`mla_design_library`.`ID` LEFT JOIN `mla_weave_library` ON `mla_fabric_library`.`BASEWEAVEID`=`mla_weave_library`.`ID` WHERE "+cond;
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            while(oResultSet.next()) {
                strReturn.append(reExportEachFabric(objConfiguration, oResultSet.getString("ID").toString(), savePath, filesToZip));
            }
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"reExportFabric : "+strQuery,ex);
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
                    new Logging("SEVERE",DbUtility.class.getName(),"reExportFabric() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> reExportFabric() <<<<<<<<<<<"+strQuery,null);
        return strReturn;
    }
    
    public StringBuffer reExportEachFabric(Configuration objConfiguration, String strFabricID, String savePath, ArrayList<File> filesToZip) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        StringBuffer strReturn=new StringBuffer();
        InputStream bodyOut=null;
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< reExportEachFabric() >>>>>>>>>>>",null);
        try {
            String cond = "ID = '"+strFabricID+"' AND USERID = '"+objConfiguration.getObjUser().getStrUserID()+"' AND `ACCESS`!='"+new IDGenerator().getUserAcess("FABRIC_LIBRARY")+"';";
            strQuery = "SELECT * FROM `mla_fabric_library` WHERE "+cond;
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            while(oResultSet.next()) {
                strReturn.append("\n LOCK TABLES `mla_fabric_library` WRITE;");
                
                bodyOut = oResultSet.getBinaryStream("ICON");
                String fileOut = savePath+oResultSet.getString("ID")+".png";
                BufferedImage bufferedImage=ImageIO.read(bodyOut);
                File pngFile=new File(fileOut);
                ImageIO.write(bufferedImage, "png", pngFile);
                filesToZip.add(pngFile);
                
                strReturn.append("\n REPLACE INTO `mla_fabric_library` VALUES ('"+oResultSet.getString("ID").toString()+"'," 
                        +"'"+oResultSet.getString("NAME")+"'," 
                        +"'"+oResultSet.getString("CLOTHTYPE")+"'," 
                        +"'"+oResultSet.getString("TYPE")+"'," 
                        +""+oResultSet.getDouble("LENGTH")+"," 
                        +""+oResultSet.getDouble("WIDTH")+"," 
                        +""+oResultSet.getDouble("ARTWORKLENGTH")+"," 
                        +""+oResultSet.getDouble("ARTWORKWIDTH")+"," 
                        +"'"+oResultSet.getString("ARTWORKID")+"'," 
                        +"'"+oResultSet.getString("BASEWEAVEID")+"'," 
                        +""+oResultSet.getInt("WEFT")+"," 
                        +""+oResultSet.getInt("WARP")+"," 
                        +""+oResultSet.getInt("EWEFT")+"," 
                        +""+oResultSet.getInt("EWARP")+"," 
                        +""+oResultSet.getInt("SHAFT")+"," 
                        +""+oResultSet.getInt("HOOKS")+"," 
                        +""+oResultSet.getInt("HPI")+"," 
                        +""+oResultSet.getInt("REEDCOUNT")+"," 
                        +""+oResultSet.getInt("DENTS")+"," 
                        +""+oResultSet.getInt("TPD")+"," 
                        +""+oResultSet.getInt("EPI")+"," 
                        +""+oResultSet.getInt("PPI")+"," 
                        +""+oResultSet.getInt("BINDING")+"," 
                        +""+oResultSet.getInt("PROTECTION")+"," 
                        +""+oResultSet.getInt("ARTWORKSIZE")+"," 
                        +""+oResultSet.getInt("ARTWORKOUTLINE")+"," 
                        +""+oResultSet.getInt("ARTWORKASPECTRATIO")+","
                        +"'"+oResultSet.getString("WARPPATTERNID")+"'," 
                        +"'"+oResultSet.getString("WEFTPATTERNID")+"'," 
                        +"'"+""+"'," 
                        +"'"+oResultSet.getString("FILE")+"'," 
                        +"'"+oResultSet.getString("RDATA")+"'," 
                        +"'"+oResultSet.getString("ACCESS")+"',"
                        +"'"+oResultSet.getString("USERID")+"',"
                        +"'"+oResultSet.getTimestamp("UDATE")+"',"
                        +"'"+oResultSet.getString("PALETTEID")+"');");
                strReturn.append("\n UNLOCK TABLES;");
                if(oResultSet.getString("ARTWORKID")!=null)
                    strReturn.append(reExportEachDesign(oResultSet.getString("ARTWORKID").toString(), savePath, filesToZip));
                strReturn.append(reExportEachWeave(oResultSet.getString("BASEWEAVEID").toString(), savePath, filesToZip));
                strReturn.append(reExportFabricArtwork(oResultSet.getString("ID").toString(), savePath, filesToZip));
                strReturn.append(exportFabricPallets(oResultSet.getString("ID").toString()));
                strReturn.append(reExportFabricThread(oResultSet.getString("ID").toString(), savePath, filesToZip));
            }
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"reExportEachFabric : "+strQuery,ex);
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
                if(bodyOut!=null){
                    bodyOut.close();
                    bodyOut=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",DbUtility.class.getName(),"reExportEachFabric() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> reExportEachFabric() <<<<<<<<<<<"+strQuery,null);
        return strReturn;
    }
    
    public StringBuffer reExportEachDesign(String strArtworkID, String savePath, ArrayList<File> filesToZip) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        StringBuffer strReturn =new StringBuffer();
        InputStream bodyOut=null;
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< reExportEachDesign() >>>>>>>>>>>",null);
        try {
            strQuery = "SELECT * FROM `mla_design_library` WHERE ID = '"+strArtworkID+"';";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            while(oResultSet.next()) {
                strReturn.append("\n LOCK TABLES `mla_design_library` WRITE;");
                bodyOut = oResultSet.getBinaryStream("FILE");
                String fileOut = savePath+oResultSet.getString("ID")+".png";
                BufferedImage bufferedImage=ImageIO.read(bodyOut);
                File pngFile=new File(fileOut);
                ImageIO.write(bufferedImage, "png", pngFile);
                filesToZip.add(pngFile);
                
                strReturn.append("\n REPLACE INTO `mla_design_library` VALUES ("
                        +"'"+oResultSet.getString("ID").toString()+"'," 
                        +"'"+oResultSet.getString("NAME").toString()+"'," 
                        +"'"+""+"'," 
                        +"'"+oResultSet.getString("BACKGROUND")+"'," 
                        +"'"+oResultSet.getString("ACCESS")+"'," 
                        +"'"+oResultSet.getString("USERID")+"'," 
                        +"'"+oResultSet.getTimestamp("UDATE")+"');");
                strReturn.append("\n UNLOCK TABLES;");
            }
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"reExportEachDesign : "+strQuery,ex);
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
                if(bodyOut!=null){
                    bodyOut.close();
                    bodyOut=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",DbUtility.class.getName(),"reExportEachDesign() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> reExportEachDesign() <<<<<<<<<<<"+strQuery,null);
        return strReturn;
    }
    
    public StringBuffer reExportEachWeave(String strWeaveID, String savePath, ArrayList<File> filesToZip) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        StringBuffer strReturn = new StringBuffer();
        InputStream bodyOut=null;
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< reExportEachWeave() >>>>>>>>>>>",null);
        try {
            strQuery = "SELECT * FROM `mla_weave_library` WHERE `ID`='"+strWeaveID+"';";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            while(oResultSet.next()) {
                strReturn.append("\n LOCK TABLES `mla_weave_library` WRITE;");
                bodyOut = oResultSet.getBinaryStream("ICON");
                String fileOut = savePath+oResultSet.getString("ID")+".png";
                BufferedImage bufferedImage=ImageIO.read(bodyOut);
                File pngFile=new File(fileOut);
                ImageIO.write(bufferedImage, "png", pngFile);
                filesToZip.add(pngFile);
                strReturn.append("\n REPLACE INTO `mla_weave_library` VALUES ("
                        +"'"+oResultSet.getString("ID").toString()+"'," 
                        +"'"+oResultSet.getString("NAME").toString()+"'," 
                        +"'"+oResultSet.getString("FILE")+"'," 
                        +"'"+""+"'," 
                        +"'"+oResultSet.getString("TYPE")+"'," 
                        +"'"+oResultSet.getString("CATEGORY")+"'," 
                        +""+oResultSet.getInt("IS_LIFTPLAN")+"," 
                        +""+oResultSet.getInt("IS_COLOR")+"," 
                        +""+oResultSet.getInt("SHAFT")+"," 
                        +""+oResultSet.getInt("TREADLE")+"," 
                        +""+oResultSet.getInt("REPEAT_X")+"," 
                        +""+oResultSet.getInt("REPEAT_Y")+"," 
                        +""+oResultSet.getInt("FLOAT_WEFT")+"," 
                        +""+oResultSet.getInt("FLOAT_WARP")+"," 
                        +"'"+oResultSet.getString("ACCESS")+"'," 
                        +"'"+oResultSet.getString("USERID")+"'," 
                        +"'"+oResultSet.getTimestamp("UDATE")+"');");
                strReturn.append("\n UNLOCK TABLES;");            
            }
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"reExportEachWeave : "+strQuery,ex);
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
                if(bodyOut!=null){
                    bodyOut.close();
                    bodyOut=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",DbUtility.class.getName(),"reExportEachWeave() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> reExportEachWeave() <<<<<<<<<<<"+strQuery,null);
        return strReturn;
    }
    
    public StringBuffer reExportFabricArtwork(String strFabricID, String savePath, ArrayList<File> filesToZip) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< reExportFabricArtwork() >>>>>>>>>>>",null);
        StringBuffer strReturn=new StringBuffer();
        strReturn.append("\n LOCK TABLES `mla_fabric_artwork` WRITE;");
        try {
            strQuery = "select * from `mla_fabric_artwork` WHERE fabric_id = '"+strFabricID+"';";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            int intFirstRow=0;
            while(oResultSet.next()) {
                strReturn.append("\n REPLACE INTO `mla_fabric_artwork` VALUES");
                String strWeaveId =oResultSet.getString("weave_id");
                strReturn.append("('"+oResultSet.getString("fabric_id").toString()+"'," 
                        +""+oResultSet.getInt("color_id")+"," 
                        +"'"+oResultSet.getString("color")+"'," 
                        +"'"+oResultSet.getString("PERCENTAGE")+"'," 
                        +""+oResultSet.getInt("is_background")+"," 
                        +"'"+strWeaveId+"'," 
                        +""+oResultSet.getInt("serial")+");");
                if(strWeaveId!=null)
                    strReturn.append(reExportEachWeave(strWeaveId, savePath, filesToZip));
                intFirstRow++;
            }
            if(intFirstRow>0)
                strReturn.append(";");
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"reExportFabricArtwork : "+strQuery,ex);
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
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",DbUtility.class.getName(),"reExportFabricArtwork() : Error while closing connection"+e,ex);
                }
            }
        }
        strReturn.append("\n UNLOCK TABLES;");
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> reExportFabricArtwork() <<<<<<<<<<<"+strQuery,null);
        return strReturn;
    }
    
    public StringBuffer reExportFabricThread(String strFabricID, String savePath, ArrayList<File> filesToZip) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< reExportFabricThread() >>>>>>>>>>>",null);
        StringBuffer strReturn=new StringBuffer();
        strReturn.append("\n LOCK TABLES `mla_fabric_thread` WRITE;");
        try {
            strQuery = "select * from `mla_fabric_thread` WHERE fabric_id = '"+strFabricID+"';";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            int intFirstRow=0;
            while(oResultSet.next()) {
                strReturn.append("\n REPLACE INTO `mla_fabric_thread` VALUES");
                String strYarnId =oResultSet.getString("yarn_id");
                strReturn.append("('"+oResultSet.getString("fabric_id")+"'," 
                        +"'"+strYarnId+"'," 
                        +"'"+oResultSet.getString("symbol")+"'," 
                        +""+oResultSet.getInt("repeat")+"," 
                        +""+oResultSet.getInt("serial")+");");
                if(strYarnId!=null)
                    strReturn.append(reExportEachYarn(strYarnId, savePath, filesToZip));
            }
            if(intFirstRow>0)
                strReturn.append(";");
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"reExportFabricThread : "+strQuery,ex);
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
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",DbUtility.class.getName(),"reExportFabricThread() : Error while closing connection"+e,ex);
                }
            }
        }
        strReturn.append("\n UNLOCK TABLES;");
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> reExportFabricThread() <<<<<<<<<<<"+strQuery,null);
        return strReturn;
    }
    
    public StringBuffer reExportEachYarn(String strYarnID, String savePath, ArrayList<File> filesToZip) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        StringBuffer strReturn = new StringBuffer();
        InputStream bodyOut=null;
        new Logging("INFO",DbUtility.class.getName(),"<<<<<<<<<<< reExportEachYarn() >>>>>>>>>>>",null);
        try {
            strQuery = "SELECT * FROM `mla_yarn_library` WHERE `ID`='"+strYarnID+"';";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            while(oResultSet.next()) {
                strReturn.append("\n LOCK TABLES `mla_yarn_library` WRITE;");
                bodyOut = oResultSet.getBinaryStream("ICON");
                String fileOut = savePath+oResultSet.getString("ID")+".png";
                BufferedImage bufferedImage=ImageIO.read(bodyOut);
                File pngFile=new File(fileOut);
                ImageIO.write(bufferedImage, "png", pngFile);
                filesToZip.add(pngFile);
                strReturn.append("\n REPLACE INTO `mla_yarn_library` VALUES ("
                        +"'"+oResultSet.getString("ID").toString()+"'," 
                        +"'"+oResultSet.getString("TYPE")+"'," 
                        +"'"+oResultSet.getString("NAME")+"'," 
                        +"'"+oResultSet.getString("COLOR")+"'," 
                        +""+oResultSet.getInt("COUNT")+"," 
                        +"'"+oResultSet.getString("UNIT")+"'," 
                        +""+oResultSet.getInt("PLY")+"," 
                        +""+oResultSet.getInt("DFACTOR")+","                                 
                        +"'"+oResultSet.getString("DIAMETER")+"'," 
                        +""+oResultSet.getInt("TWIST")+"," 
                        +"'"+oResultSet.getString("TMODEL")+"'," 
                        +""+oResultSet.getInt("HAIRNESS")+"," 
                        +""+oResultSet.getInt("HPROBABILITY")+"," 
                        +""+oResultSet.getDouble("PRICE")+"," 
                        +"'"+""+"',"
                        +"'"+oResultSet.getString("ACCESS")+"'," 
                        +"'"+oResultSet.getString("USERID")+"'," 
                        +"'"+oResultSet.getTimestamp("UDATE")+"');");
                strReturn.append("\n UNLOCK TABLES;");            
            }
        } catch (Exception ex) {
            new Logging("SEVERE",DbUtility.class.getName(),"reExportEachYarn : "+strQuery,ex);
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
                if(bodyOut!=null){
                    bodyOut.close();
                    bodyOut=null;
                }
            } catch (Exception ex) {
                try {
                    DbConnect.close(connection);                
                } catch (Exception e) {
                    new Logging("SEVERE",DbUtility.class.getName(),"reExportEachYarn() : Error while closing connection"+e,ex);
                }
            }
        }
        new Logging("INFO",DbUtility.class.getName(),">>>>>>>>>>> reExportEachYarn() <<<<<<<<<<<"+strQuery,null);
        return strReturn;
    }
// ------------------------End of Code Added    
}