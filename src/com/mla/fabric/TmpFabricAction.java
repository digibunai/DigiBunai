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

import com.mla.main.DbConnect;
import com.mla.main.IDGenerator;
import com.mla.main.Logging;
import com.mla.yarn.Yarn;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;

/**
 * Action class for saving, retrieving temporary fabric
 * @see FabricAction
 * @since 0.8.3
 * @author Aatif Ahmad Khan
 */
public class TmpFabricAction {
    
    Connection connection=null;
    Fabric objFabric;
    
    public TmpFabricAction() throws SQLException{
        connection = DbConnect.getConnection();
    }
    
    /* tmp_fabric_library */
    
    public void getTmpFabric(Fabric objFabric) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        try {           
            String cond = " 1";
            if(objFabric.getStrFabricID()!="" && objFabric.getStrFabricID()!=null)
                cond += " AND ID='"+objFabric.getStrFabricID()+"'";
            strQuery = "select * from tmp_fabric_library WHERE "+cond+" LIMIT 1;";
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
                objFabric.setStrFabricAccess(oResultSet.getString("ACCESS"));
                readFabric(objFabric,(byte)0);
                readFabric(objFabric,(byte)1);
            }
        } catch (Exception ex) {
            new Logging("SEVERE",TmpFabricAction.class.getName(),"getTmpFabric : "+strQuery,ex);
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
                    new Logging("SEVERE",TmpFabricAction.class.getName(),"getTmpFabric() : Error while closing connection"+e,ex);
                }
            }
        }
        return;
    }
    
    public byte setTmpFabric(Fabric objFabric) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        try {           
            strQuery = "INSERT INTO `tmp_fabric_library` (`ID`, `NAME`, `CLOTHTYPE`, `TYPE`, `LENGTH`, `WIDTH`, `ARTWORKLENGTH`, `ARTWORKWIDTH`, `ARTWORKID`, `BASEWEAVEID`, `WEFT`, `WARP`, `EWEFT`, `EWARP`, `SHAFT`, `HOOKS`, `HPI`, `REEDCOUNT`, `DENTS`, `TPD`, `EPI`, `PPI`, `PROTECTION`, `BINDING`, `WARPPATTERNID`, `WEFTPATTERNID`, `FILE`, `RDATA`, `ICON`, `USERID`, `ACCESS`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
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
            oPreparedStatement.setString(31, new IDGenerator().setUserAcessKey("FABRIC_LIBRARY",objFabric.getObjConfiguration().getObjUser()));
            oResult = (byte)oPreparedStatement.executeUpdate();              
        } catch (Exception ex) {
            new Logging("SEVERE",TmpFabricAction.class.getName(),"setTmpFabric : "+strQuery,ex);
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
                    new Logging("SEVERE",TmpFabricAction.class.getName(),"setTmpFabric() : Error while closing connection"+e,ex);
                }
            }
        }
        return oResult;
    }
    
    /* tmp_fabric_pallets */
    
    public String[] getTmpFabricPallets(Fabric objFabric) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String[] threadPaletes=null;
        try {           
            strQuery = "select * from tmp_fabric_pallets WHERE fabric_id='"+objFabric.getStrFabricID()+"';";
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
            new Logging("SEVERE",TmpFabricAction.class.getName(),"getTmpFabricPallets : "+strQuery,ex);
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
                    new Logging("SEVERE",TmpFabricAction.class.getName(),"getTmpFabricPallets() : Error while closing connection"+e,ex);
                }
            }
        }
        return threadPaletes;
    }
    
    public byte setTmpFabricPallets(Fabric objFabric) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        try {           
            strQuery = "INSERT INTO `tmp_fabric_pallets` (`fabric_id`, `warp_A`, `warp_B`, `warp_C`, `warp_D`, `warp_E`, `warp_F`, `warp_G`, `warp_H`, `warp_I`, `warp_J`, `warp_K`, `warp_L`, `warp_M`, `warp_N`, `warp_O`, `warp_P`, `warp_Q`, `warp_R`, `warp_S`, `warp_T`, `warp_U`, `warp_V`, `warp_W`, `warp_X`, `warp_Y`, `warp_Z`, `weft_a`, `weft_b`, `weft_c`, `weft_d`, `weft_e`, `weft_f`, `weft_g`, `weft_h`, `weft_i`, `weft_j`, `weft_k`, `weft_l`, `weft_m`, `weft_n`, `weft_o`, `weft_p`, `weft_q`, `weft_r`, `weft_s`, `weft_t`, `weft_u`, `weft_v`, `weft_w`, `weft_x`, `weft_y`, `weft_z`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
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
            new Logging("SEVERE",TmpFabricAction.class.getName(),"setTmpFabricPallets : "+strQuery,ex);
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
                    new Logging("SEVERE",TmpFabricAction.class.getName(),"setTmpFabricPallets() : Error while closing connection"+e,ex);
                }
            }
        }
        return oResult;
    }
    
    /* tmp_fabric_artwork */
    
    public void getTmpFabricArtwork(Fabric objFabric) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String[][] colorWeave=null;
        try {           
            strQuery = "SELECT * FROM `tmp_fabric_artwork` WHERE fabric_id='"+objFabric.getStrFabricID()+"' ORDER BY serial;";
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
                //colorWeave[i][3] = oResultSet.getString("PERCENTAGE");
                i++;                
            }
            objFabric.setColorWeave(colorWeave);
            colorWeave = null;
        } catch (Exception ex) {
            new Logging("SEVERE",TmpFabricAction.class.getName(),"getTmpFabricArtwork : "+strQuery,ex);
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
                    new Logging("SEVERE",TmpFabricAction.class.getName(),"getTmpFabricArtwork() : Error while closing connection"+e,ex);
                }
            }
        }
    }
    
    public byte setTmpFabricArtwork(Fabric objFabric) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        int is_background =1;
        String strQuery=null;
        try {           
            strQuery = "INSERT INTO `tmp_fabric_artwork` (`fabric_id`, `color`, `weave_id`, `serial`, `color_id`, `is_background`) VALUES (?,?,?,?,?,?);";
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
            new Logging("SEVERE",TmpFabricAction.class.getName(),"setTmpFabricArtwork : "+strQuery,ex);
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
                    new Logging("SEVERE",TmpFabricAction.class.getName(),"setTmpFabricArtwork() : Error while closing connection"+e,ex);
                }
            }
        }
        return oResult;
    }
    
    /* NOT NEEDED as mla_yarn_library will be used */
    /* tmp_yarn_library */
    
    public byte setYarn(Yarn objYarn) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        try {           
            strQuery = "INSERT INTO `tmp_yarn_library` (`ID`, `TYPE`, `NAME`, `COLOR`, `COUNT`, `UNIT`, `PLY`, `DFACTOR`, `DIAMETER`, `TWIST`, `TMODEL`, `HAIRNESS`, `HPROBABILITY`, `PRICE`, `ICON`, `ACCESS`, `USERID`) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            getYarnIcon(objYarn);
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, objYarn.getStrYarnId());
            oPreparedStatement.setString(2, objYarn.getStrYarnType());
            oPreparedStatement.setString(3, objYarn.getStrYarnName());
            oPreparedStatement.setString(4, objYarn.getStrYarnColor());
            oPreparedStatement.setInt(5, objYarn.getIntYarnCount());
            oPreparedStatement.setString(6, objYarn.getStrYarnCountUnit());
            oPreparedStatement.setInt(7, objYarn.getIntYarnPly());
            oPreparedStatement.setInt(8, objYarn.getIntYarnDFactor());
            oPreparedStatement.setDouble(9, Double.parseDouble(String.format("%.3f", objYarn.getDblYarnDiameter())));
            oPreparedStatement.setInt(10, objYarn.getIntYarnTwist());
            oPreparedStatement.setString(11, objYarn.getStrYarnTModel());
            oPreparedStatement.setInt(12, objYarn.getIntYarnHairness());
            oPreparedStatement.setInt(13, objYarn.getIntYarnHProbability());
            oPreparedStatement.setDouble(14, objYarn.getDblYarnPrice());
            oPreparedStatement.setBinaryStream(15,new ByteArrayInputStream(objYarn.getBytYarnThumbnil()),objYarn.getBytYarnThumbnil().length);
            oPreparedStatement.setString(16, new IDGenerator().setUserAcessKey("YARN_LIBRARY",objYarn.getObjConfiguration().getObjUser()));
            oPreparedStatement.setString(17, objYarn.getObjConfiguration().getObjUser().getStrUserID());
            oResult = (byte)oPreparedStatement.executeUpdate();
        } catch (Exception ex) {
            new Logging("SEVERE",TmpFabricAction.class.getName(),"setYarn : "+strQuery,ex);
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
                    new Logging("SEVERE",TmpFabricAction.class.getName(),"setYarn() : Error while closing connection"+e,ex);
                }
            }
        }
        return oResult;
    }
    
    public void getYarnIcon(Yarn objYarn){
        try {
            int npixel = new java.awt.Color((float)javafx.scene.paint.Color.web(objYarn.getStrYarnColor()).getRed(),(float)javafx.scene.paint.Color.web(objYarn.getStrYarnColor()).getGreen(),(float)javafx.scene.paint.Color.web(objYarn.getStrYarnColor()).getBlue()).getRGB();
            int nred   = (npixel & 0x00ff0000) >> 16;
            int ngreen = (npixel & 0x0000ff00) >> 8;
            int nblue  =  npixel & 0x000000ff;
            int hair = objYarn.getIntYarnHairness();
            int percentage = objYarn.getIntYarnHProbability();
            int twist = objYarn.getIntYarnTwist();
            char model = objYarn.getStrYarnTModel().charAt(0);
            
            BufferedImage srcImage = ImageIO.read(new File(System.getProperty("user.dir")+"/mla/yarn.jpg")); //colored red_tshirt white-cloth green-fabric
            
            int intLength = 111;//yarnImage.getHeight();;
            int intHeight = 111;//yarnImage.getWidth();
            BufferedImage myImage = new BufferedImage((int)(intLength), (int)(intHeight),BufferedImage.TYPE_INT_RGB);
            Graphics2D g = myImage.createGraphics();
            g.drawImage(srcImage, 0, 0, (int)(intLength), (int)(intHeight), null);
            g.dispose();
            srcImage = null;
            
            BufferedImage yarnImage = new BufferedImage(intLength, intHeight,BufferedImage.TYPE_INT_RGB);
            int rgb = 0;
            for(int x = 0; x < intHeight; x++) {
                for(int y = 0; y < intLength; y++) {
                    int pixel = myImage.getRGB(y, x);
                    int alpha = (pixel >> 24) & 0xff;
                    int red   = (pixel >>16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue  =  pixel & 0xff;
                    //if(red!=255 || green!=255 || blue!=255){
                    // Convert RGB to HSB
                    float[] hsb = java.awt.Color.RGBtoHSB(red, green, blue, null);
                    float hue = hsb[0];
                    float saturation = hsb[1];
                    float brightness = hsb[2];
                    hsb = null;
                    hsb = java.awt.Color.RGBtoHSB(nred, ngreen, nblue, null);
                    hue = hsb[0];
                    saturation = hsb[1];
                    // Convert HSB to RGB value
                    rgb = java.awt.Color.HSBtoRGB(hue, saturation, brightness);
                    //}else{
                    //rgb = pixel;
                    //}
                    yarnImage.setRGB(y, x, rgb);
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(yarnImage, "png", baos);
            objYarn.setBytYarnThumbnil(baos.toByteArray());
        } catch (IOException ex) {
            new Logging("SEVERE",TmpFabricAction.class.getName(),"getYarnIcon() : Error while creating yarn image"+ex,ex);
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
            new Logging("SEVERE",TmpFabricAction.class.getName(),ex.toString(),ex);
        }
    }
    
    /* tmp_fabric_thread */
    
    public Yarn[] getTmpFabricYarn(Fabric objFabric,String type) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        Yarn[] yarns=null;
        try {           
            strQuery = "SELECT * FROM `tmp_yarn_library` LEFT JOIN `tmp_fabric_thread` ON `tmp_fabric_thread`.`yarn_id` = `tmp_yarn_library`.`id` WHERE fabric_id='"+objFabric.getStrFabricID()+"' AND type='"+type+"' ORDER BY `serial` ASC;";
            //SELECT t.yarn_id, t.repeat, yarn_library.* FROM fabric_thread t JOIN generator_256 i ON i.n between 1 and t.repeat JOIN yarn_library on yarn_library.id = t.yarn_id order by t.yarn_id, i.n;
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);
            oResultSet.last();
            System.out.println("Row= "+oResultSet.getRow());
            yarns = new Yarn[oResultSet.getRow()];
            int i = 0;
            oResultSet.beforeFirst();
            while(oResultSet.next()) {
                yarns[i] = new Yarn(
                        oResultSet.getString("ID").toString(),
                        type,
                        oResultSet.getString("NAME").toString(),
                        oResultSet.getString("COLOR").toString(),
                        oResultSet.getInt("REPEAT"),
                        oResultSet.getString("SYMBOL").toString(),
                        oResultSet.getInt("COUNT"),
                        oResultSet.getString("UNIT").toString(),
                        oResultSet.getInt("PLY"),
                        oResultSet.getInt("DFACTOR"),
                        Float.parseFloat(oResultSet.getString("DIAMETER").toString()),
                        oResultSet.getInt("TWIST"),
                        oResultSet.getString("TMODEL").toString(),
                        oResultSet.getInt("HAIRNESS"),
                        oResultSet.getInt("HPROBABILITY"),
                        Float.parseFloat(oResultSet.getString("PRICE").toString()),
                        oResultSet.getString("ACCESS"),
                        oResultSet.getString("USERID"),
                        oResultSet.getTimestamp("UDATE").toString()
                );
                i++;
            }
        } catch (Exception ex) {
            new Logging("SEVERE",TmpFabricAction.class.getName(),"getTmpFabricYarn : "+strQuery,ex);
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
                    new Logging("SEVERE",TmpFabricAction.class.getName(),"getTmpFabricYarn() : Error while closing connection"+e,ex);
                }
            }
        }
        return yarns;
    }
    
    public byte setTmpFabricYarn(String fabricId,Yarn objYarn,int i) {
        PreparedStatement oPreparedStatement =null;
        ResultSet oResultSet= null;
        byte oResult= 0;
        String strQuery=null;
        String yarnId = null;
        try {           
            strQuery = "INSERT INTO `tmp_fabric_thread` (`fabric_id`, `yarn_id`, `symbol`, `repeat`,`serial`) VALUES (?,?,?,?,?);";
            oPreparedStatement = connection.prepareStatement(strQuery);
            oPreparedStatement.setString(1, fabricId);
            oPreparedStatement.setString(2, objYarn.getStrYarnId());
            oPreparedStatement.setString(3, objYarn.getStrYarnSymbol());
            oPreparedStatement.setInt(4, objYarn.getIntYarnRepeat());
            oPreparedStatement.setInt(5, i);
            oResult = (byte)oPreparedStatement.executeUpdate();
        } catch (Exception ex) {
            new Logging("SEVERE",TmpFabricAction.class.getName(),"addTmpFabricThread : "+strQuery,ex);
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
                    new Logging("SEVERE",TmpFabricAction.class.getName(),"addTmpFabricThread() : Error while closing connection"+e,ex);
                }
            }
        }
        return oResult;
    }
    
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
    
    public byte setTmpFabricOrientation(String fabridId, String component
            , int componentWidth, int componentHeight
            , int repeatWidth, int repeatHeight
            , int tmpWarp, int tmpWeft, int rotationAngle, String repeatMode){
        Statement oStatement = null;
        String strQuery= null;
        byte oResult= 0;
        try {           
            strQuery = "INSERT INTO `mla_fabric_orientation` (`FABRICID`, `COMPONENT`, `COMPONENTWIDTH`, `COMPONENTHEIGHT`, `IMAGEWIDTH`, `IMAGEHEIGHT`,`TMPWARP`, `TMPWEFT`, `ROTATIONANGLE`, `REPEATMODE`) VALUES ('"+
                    fabridId+"', '"+component+"', '"+componentWidth+"', '"+componentHeight+"', '"+repeatWidth+"', '"+repeatHeight+"', '"+tmpWarp+"', '"+tmpWeft+"', '"+rotationAngle+"', '"+repeatMode+"');";
            oStatement = connection.createStatement();
            oResult = (byte)oStatement.executeUpdate(strQuery);
        } catch (Exception ex) {
            new Logging("SEVERE",TmpFabricAction.class.getName(),"setTmpFabricOrientation : "+strQuery,ex);
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
                    new Logging("SEVERE",TmpFabricAction.class.getName(),"setTmpFabricOrientation() : Error while closing connection"+e,ex);
                }
            }
        }
        return oResult;
    }
    
    public String[] getTmpFabricOrientation(String fabricId, String component) {
        Statement oStatement =null;
        ResultSet oResultSet= null;
        String strQuery=null;
        String[] values=null;
        try {           
            strQuery = "select * from mla_fabric_orientation WHERE FABRICID='"+fabricId+"' AND COMPONENT='"+component+"';";
            oStatement = connection.createStatement();
            oResultSet = oStatement.executeQuery(strQuery);    
            values = new String[10];            
            if(oResultSet.next()){
                values[0]=oResultSet.getString("FABRICID").toString();
                values[1]=oResultSet.getString("COMPONENT").toString();
                values[2]=String.valueOf(oResultSet.getInt("COMPONENTWIDTH"));
                values[3]=String.valueOf(oResultSet.getInt("COMPONENTHEIGHT"));
                values[4]=String.valueOf(oResultSet.getInt("IMAGEWIDTH"));
                values[5]=String.valueOf(oResultSet.getInt("IMAGEHEIGHT"));
                values[6]=String.valueOf(oResultSet.getInt("TMPWARP"));
                values[7]=String.valueOf(oResultSet.getInt("TMPWEFT"));
                values[8]=String.valueOf(oResultSet.getInt("ROTATIONANGLE"));
                values[9]=oResultSet.getString("REPEATMODE").toString();
            }
        } catch (Exception ex) {
            new Logging("SEVERE",TmpFabricAction.class.getName(),"getTmpFabricOrientation : "+strQuery,ex);
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
                    new Logging("SEVERE",TmpFabricAction.class.getName(),"getTmpFabricOrientation() : Error while closing connection"+e,ex);
                }
            }
        }
        return values;
    }
    
    // insert (if not available in existing yarns) else don't insert
    public byte setTmpYarn(Yarn objYarn) {
        PreparedStatement oPreparedStatement =null;
        String strQuery=null;
        byte oResult = 0;
        try {
            strQuery = "INSERT INTO `mla_yarn_library` (`ID`, `TYPE`, `NAME`, `COLOR`, `COUNT`, `UNIT`, `PLY`, `DFACTOR`, `DIAMETER`, `TWIST`, `TMODEL`, `HAIRNESS`, `HPROBABILITY`, `PRICE`, `ICON`, `ACCESS`, `USERID`) "
                    + "SELECT * FROM "
                    + "(SELECT ? as a, ? as b, ? as c, ? as d, ? as e, ? as f, ? as g, ? as h, ? as i, ? as j, ? as k, ? as l, ? as m, ? as n, ? as o, ? as p, ? as q) AS tmp WHERE NOT EXISTS "
                    + "(SELECT `TYPE`, `NAME`, `COLOR`, `COUNT`, `UNIT`, `PLY`, `DFACTOR`, `DIAMETER`, `TWIST`, `TMODEL`, `HAIRNESS`, `HPROBABILITY`, `PRICE` FROM mla_yarn_library WHERE TYPE = ? AND NAME = ? AND COLOR = ? AND COUNT = ? AND UNIT = ? AND PLY = ? AND DFACTOR = ? AND DIAMETER = ? AND TWIST = ? AND TMODEL = ? AND HAIRNESS = ? AND HPROBABILITY = ? AND PRICE = ?) LIMIT 1;";
            oPreparedStatement = connection.prepareStatement(strQuery);
            
            oPreparedStatement.setString(1, objYarn.getStrYarnId());
            oPreparedStatement.setString(2, objYarn.getStrYarnType());
            oPreparedStatement.setString(3, objYarn.getStrYarnName());
            oPreparedStatement.setString(4, objYarn.getStrYarnColor());
            oPreparedStatement.setInt(5, objYarn.getIntYarnCount());
            oPreparedStatement.setString(6, objYarn.getStrYarnCountUnit());
            oPreparedStatement.setInt(7, objYarn.getIntYarnPly());
            oPreparedStatement.setInt(8, objYarn.getIntYarnDFactor());
            oPreparedStatement.setDouble(9, Double.parseDouble(String.format("%.3f", objYarn.getDblYarnDiameter())));
            oPreparedStatement.setInt(10, objYarn.getIntYarnTwist());
            oPreparedStatement.setString(11, objYarn.getStrYarnTModel());
            oPreparedStatement.setInt(12, objYarn.getIntYarnHairness());
            oPreparedStatement.setInt(13, objYarn.getIntYarnHProbability());
            oPreparedStatement.setDouble(14, objYarn.getDblYarnPrice());
            oPreparedStatement.setBinaryStream(15,new ByteArrayInputStream(objYarn.getBytYarnThumbnil()),objYarn.getBytYarnThumbnil().length);
            oPreparedStatement.setString(16, new IDGenerator().setUserAcessKey("YARN_LIBRARY",objYarn.getObjConfiguration().getObjUser()));
            oPreparedStatement.setString(17, objYarn.getObjConfiguration().getObjUser().getStrUserID());
            oPreparedStatement.setString(18, objYarn.getStrYarnType());
            oPreparedStatement.setString(19, objYarn.getStrYarnName());
            oPreparedStatement.setString(20, objYarn.getStrYarnColor());
            oPreparedStatement.setInt(21, objYarn.getIntYarnCount());
            oPreparedStatement.setString(22, objYarn.getStrYarnCountUnit());
            oPreparedStatement.setInt(23, objYarn.getIntYarnPly());
            oPreparedStatement.setInt(24, objYarn.getIntYarnDFactor());
            oPreparedStatement.setDouble(25, objYarn.getDblYarnDiameter());
            oPreparedStatement.setInt(26, objYarn.getIntYarnTwist());
            oPreparedStatement.setString(27, objYarn.getStrYarnTModel());
            oPreparedStatement.setInt(28, objYarn.getIntYarnHairness());
            oPreparedStatement.setInt(29, objYarn.getIntYarnHProbability());
            oPreparedStatement.setDouble(30, objYarn.getDblYarnPrice());
            
            oResult = (byte)oPreparedStatement.executeUpdate(strQuery);
        } catch (Exception ex) {
            new Logging("SEVERE",TmpFabricAction.class.getName(),"setTmpYarn : "+strQuery,ex);
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
                    new Logging("SEVERE",TmpFabricAction.class.getName(),"setTmpYarn() : Error while closing connection"+e,ex);
                }
            }
        }
        return oResult;
    }
}
