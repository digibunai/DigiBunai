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

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @Designing database connection file
 * @author Amit Kumar Singh
 * 
 */
public class DbConnect {

/*NOTE: Creating Database Constraints*/
private static String dbHostName = "localhost";
private static String dbPort = "3306";
private static String dbName = "bunai";
private static String dbUserName = "openweave";
private static String dbPassword = "ml@sia";
    
 /**
 * getConnection()
 * <p>
 * This Function is used for creation of jdbc connection. 
 * 
 * @param       null
 * @return      Connection
 * @throws      SQLException
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         java.sql.*;
 * @link        null
 */
    public static synchronized Connection getConnection() throws SQLException{
        Connection connection;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //connection = DriverManager.getConnection("jdbc:mysql://192.168.1.30:3306/openweave", "r@m","110019ml@");
            connection = DriverManager.getConnection("jdbc:mysql://"+dbHostName+":"+dbPort+"/"+dbName, dbUserName, dbPassword);
            /*
            InitialContext ctx = new InitialContext();
            //DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/MySQLDS");
            DataSource  ds = (DataSource)ctx.lookup("java:comp/env/jdbc/e-DhanwanthariDS");
            con = ds.getConnection(); 
            */
            return connection;
        } catch (SQLException ex) {
            new Logging("SEVERE",DbConnect.class.getName(),"Error Occured While Getting the Connection"+ex.toString(),ex);
        } catch (Exception ex) {
            new Logging("SEVERE",DbConnect.class.getName(),"Error Occured While Getting the Connection"+ex.toString(),ex);
        }        
        return null;
    }   
 
 /**
 * close()
 * <p>
 * This Function is used for closing of jdbc connection. 
 * 
 * @param       Connection connection
 * @return      Connection
 * @throws      SQLException
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         java.sql.*;
 * @link        null
 */
     public static synchronized void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException ex) {
                new Logging("SEVERE",DbConnect.class.getName(),"Error Occured While Closing the Connection"+ex.toString(),ex);
            }
        }
    }

 /**
 * close()
 * <p>
 * This Function is used for closing of jdbc connection. 
 * 
 * @param       Statement statement, ResultSet resultset
 * @return      void
 * @throws      SQLException
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         java.sql.*;
 * @link        null
 */
    public static synchronized void close(Statement statement, ResultSet resultset) {
        if (resultset != null) {
            try {
                resultset.close();
                resultset = null;
            } catch (SQLException ex) {
                new Logging("SEVERE",DbConnect.class.getName(),"Error Occured While Closing the ResultSet"+ex.toString(),ex);
            }
        }
        if (statement != null) {
            try {
                statement.close();
                statement = null;
            } catch (SQLException ex) {
                new Logging("SEVERE",DbConnect.class.getName(),"Error Occured While Closing the Statement"+ex.toString(),ex);
            }
        }
    }
    
    public static int restoreDBfromSQL(String restoreFilePath) {
        int processComplete = -1;
        try {
            /*NOTE: String s is the mysql file name including the .sql in its name*/
            /*NOTE: Getting path to the Jar file being executed*/
            /*NOTE: YourImplementingClass-> replace with the class executing the code*/
            
            /*NOTE: Used to create a cmd command*/
            /*NOTE: Do not create a single large string, this will cause buffer locking, use string array*/
            String[] executeCmd = new String[]{"mysql", dbName, "-u" + dbUserName, "-p" + dbPassword, "-e", " source " + restoreFilePath};
            
            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            processComplete = runtimeProcess.waitFor();

            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            if (processComplete == 0) {
                System.err.println("Successfully restored from SQL : " + restoreFilePath);
            } else {
                System.err.println("Error at restoring");
            }
        } catch (IOException | InterruptedException | HeadlessException ex) {
            new Logging("SEVERE",DbConnect.class.getName(),"Error Occured While restore db from sql"+ex.getMessage(),ex);
        }
        return processComplete;
    }

    public static int backupDBtoSQL(String backupFilePath) {
        int processComplete = -1;
        try {
            /*NOTE: Getting path to the Jar file being executed*/
            /*NOTE: YourImplementingClass-> replace with the class executing the code*/
            System.out.println("Welcome to Backupdbtosql");
            System.err.println("Path: "+backupFilePath);                     
            /*NOTE: Used to create a cmd command*/
            String executeCmd = "mysqldump -q -u" + dbUserName + " -p" + dbPassword + " --database " + dbName + " -r " + backupFilePath;
            System.err.println("Command: "+executeCmd);
            /*NOTE: Executing the command here*/
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            processComplete = runtimeProcess.waitFor();
            System.err.println("Result Status: "+processComplete);
            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            if (processComplete == 0) {
                System.out.println("Backup Complete");
            } else {
                System.out.println("Backup Failure");
            }
        } catch (IOException | InterruptedException ex) {
            new Logging("SEVERE",DbConnect.class.getName(),"Error Occured While backup db to sql"+ex.getMessage(),ex);
        }
        return processComplete;
    }

}