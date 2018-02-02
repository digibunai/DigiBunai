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

import static com.mla.main.InstallationView.Constants.CONFIG;
import com.mla.user.UserView;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
/**
 *
 * @Designing GUI window function for instalation
 * @author Amit Kumar Singh
 * 
 */
public class InstallationView extends Application {
    static Configuration objConfiguration = null;
    
    private static SplashScreen fSplashScreen;  
    private static final String SPLASH_IMAGE = "/media/splash.gif";

  static Stage primaryStage;
  /**
  * Show a simple graphical splash screen, as a quick preliminary to the main screen.
  */
  private static void showSplashScreen(){
    new Logging("INFO",Launcher.class.getName(),"Showing the splash screen.",null);
    fSplashScreen = new SplashScreen(SPLASH_IMAGE);
    fSplashScreen.splash();
  }
  
  /**
  * Display the main window of the application to the user.
  */
  private static void showMainWindow(){
      objConfiguration.intializeConfiguration();                
      new Logging("INFO",Launcher.class.getName(),"Showing the main window.",null);
      UserView objUserView = new UserView(objConfiguration);
  }

  /** 
  * Removes the splash screen. 
  *
  * Invoke this <tt>Runnable</tt> using 
  * <tt>EventQueue.invokeLater</tt>, in order to remove the splash screen
  * in a thread-safe manner.
  */
  private static final class SplashScreenCloser implements Runnable {
    @Override public void run(){
        new Logging("INFO",Launcher.class.getName(),"Closing the splash screen.",null);
      fSplashScreen.dispose();
      fSplashScreen.getOpacity();
      fSplashScreen.hide();
    }
  }
  
  private static void logBasicSystemInfo() {
    new Logging("INFO",Launcher.class.getName(),"Launching the application.",null);
    new Logging("CONFIG",Launcher.class.getName(),"Operating System: " + System.getProperty("os.name") + " " + System.getProperty("os.version"),null);
    new Logging("CONFIG",Launcher.class.getName(),"JRE: " + System.getProperty("java.version"),null);
    new Logging("INFO",Launcher.class.getName(),"Java Launched From: " + System.getProperty("java.home"),null);
    new Logging("CONFIG",Launcher.class.getName(),"Class Path: " + System.getProperty("java.class.path"),null);
    new Logging("CONFIG",Launcher.class.getName(),"Library Path: " + System.getProperty("java.library.path"),null);
    new Logging("CONFIG",Launcher.class.getName(),"Application Name: Bunai 1.0",null);
    new Logging("CONFIG",Launcher.class.getName(),"User Home Directory: " + System.getProperty("user.home"),null);
    new Logging("CONFIG",Launcher.class.getName(),"User Working Directory: " + System.getProperty("user.dir"),null);
    new Logging("INFO",Launcher.class.getName(),"Test INFO logging.",null);
    new Logging("FINE",Launcher.class.getName(),"Test FINE logging.",null);
    new Logging("FINEST",Launcher.class.getName(),"Test FINEST logging.",null);
  }
  
  private void logSystemMemoryInfo(){
    int mb = 1024*1024;
    //Getting the runtime reference from system
    Runtime runtime = Runtime.getRuntime();

    new Logging("INFO",Launcher.class.getName(),"##### Heap utilization statistics [MB] #####",null);
    new Logging("CONFIG",Launcher.class.getName(),"Used Memory:" + ((runtime.totalMemory() - runtime.freeMemory()) / mb),null);
    new Logging("CONFIG",Launcher.class.getName(),"Free Memory:" + (runtime.freeMemory() / mb),null);
    new Logging("INFO",Launcher.class.getName(),"Total Memory:" + (runtime.totalMemory() / mb),null);
    new Logging("INFO",Launcher.class.getName(),"Maximum Available Memory:" + (runtime.maxMemory() / mb),null);		
  }
  
    public enum Constants {
        CONFIG;
        public String USERNAME;
        public String PASSWORD;
        public String EMAIL;
        public String LICENCE;
        public String MACID;
        public String IP;
        public String PORT;
        public String CENTER;
        public String DBHOST;
        public String DBPORT;
        public String DBNAME;
        public String DBPREFIX;
        public String DBUSER;
        public String DBPASSWORD;
        public String MAILSERVER;
        public String MAILPORT;
        public String MAILUSER;
        public String MAILPASSWORD;
        public String MAILAUTH;
        public String MAILSTARTTLS;
        static{
            try{
                Properties properties = new Properties();
                InputStream resourceAsStream =  Configuration.class.getClassLoader().getResourceAsStream("configuration.properties");
                if (resourceAsStream != null) {
                    properties.load(resourceAsStream);
                }
                CONFIG.USERNAME=properties.getProperty("USERNAME", "mlasia");
                CONFIG.PASSWORD=properties.getProperty("PASSWORD", "M1@$!a");
                CONFIG.EMAIL=properties.getProperty("EMAIL", "cadtool@medialabasia.in");
                CONFIG.LICENCE=properties.getProperty("LICENCE", "705A0F667CCF");
                CONFIG.MACID=properties.getProperty("MACID", "70-5A-0F-66-7C-CF");
                CONFIG.IP=properties.getProperty("IP", "127.0.0.1");
                CONFIG.PORT=properties.getProperty("PORT", "8081");
                CONFIG.CENTER=properties.getProperty("CENTER", "10001");
                CONFIG.DBHOST=properties.getProperty("DBHOST", "127.0.0.1");
                CONFIG.DBPORT=properties.getProperty("DBPORT", "3306");
                CONFIG.DBPREFIX=properties.getProperty("DBPREFIX", "mla_");
                CONFIG.DBUSER=properties.getProperty("DBUSER", "openweave");
                CONFIG.DBPASSWORD=properties.getProperty("DBPASSWORD", "ML@sia");
                CONFIG.MAILSERVER=properties.getProperty("MAILSERVER", "smtp.gmail.com");
                CONFIG.MAILPORT=properties.getProperty("MAILPORT", "587");
                CONFIG.MAILUSER=properties.getProperty("MAILUSER", "emailtestmla@gmail.com");
                CONFIG.MAILPASSWORD=properties.getProperty("MAILPASSWORD", "passwordtestmla");
                CONFIG.MAILAUTH=properties.getProperty("MAILAUTH", "true");
                CONFIG.MAILSTARTTLS=properties.getProperty("MAILSTARTTLS", "true");
            }
            catch(IOException ex){
                new Logging("SEVERE",Email.class.getName(),"send(): Error reading parameters from configuration.properties",ex);
            }
        }
    }
    
  @Override
  public void start(final Stage primaryStage) {
    objConfiguration = new Configuration();
    logBasicSystemInfo();
    logSystemMemoryInfo();
    //showSplashScreen();
    showMainWindow();
    //EventQueue.invokeLater(new SplashScreenCloser());
    new Logging("INFO",Launcher.class.getName(),"Launch thread now exiting",null);
    new Logging("WARNING",InstallationView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
  }
  
  public static void main(String[] args) {   
      launch(args);    
  }
}

