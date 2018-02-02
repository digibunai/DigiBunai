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

/**
 *
 * @author Amit Kumar Singh
 */

import java.awt.EventQueue;

/**
* Launch the application using an older version of a splash screen.
*
*<P>Perform tasks in this order :
*<ul>
* <li>log basic system information 
* <li>promptly show a splash screen upon startup
* <li>show the main screen
* <li>remove the splash screen once the main screen is shown
*</ul>
*
* These tasks are performed in a thread-safe manner.
*/
public final class Launcher { 

  /**
  * Launch the application and display the main window.
  *
  * @param aArgs are ignored by this application, and may take any value.
  */
  public static void main (String args[]) {
    
    /*
    * Implementation Note:
    *
    * Note that the launch thread of any GUI application is in effect an initial 
    * worker thread - it is not the event dispatch thread, where the bulk of processing
    * takes place. Thus, once the launch thread realizes a window, then the launch 
    * thread should almost always manipulate such a window through 
    * EventQueue.invokeLater. (This is done for closing the splash 
    * screen, for example.)
    */
    
    //verifies that assertions are on:
    //  assert(false) : "Test";
    
    logBasicSystemInfo();
    showSplashScreen();
    showMainWindow();
    EventQueue.invokeLater(new SplashScreenCloser());
    new Logging("INFO",Launcher.class.getName(),"Launch thread now exiting",null);
  }

  // PRIVATE 
  
  private static SplashScreen fSplashScreen;
  
  private static final String SPLASH_IMAGE = "StocksMonitor.gif";

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
      new Logging("INFO",Launcher.class.getName(),"Showing the main window.",null);
      InstallationView objInstallationView = new InstallationView();
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
    }
  }
  
  private static void logBasicSystemInfo() {
    new Logging("INFO",Launcher.class.getName(),"Launching the application.",null);
    new Logging("CONFIG",Launcher.class.getName(),
            "Operating System: " + System.getProperty("os.name") + " " + 
            System.getProperty("os.version"),null);
    
    new Logging("CONFIG",Launcher.class.getName(),"JRE: " + System.getProperty("java.version"),null);
    new Logging("INFO",Launcher.class.getName(),"Java Launched From: " + System.getProperty("java.home"),null);
    new Logging("CONFIG",Launcher.class.getName(),"Class Path: " + System.getProperty("java.class.path"),null);
    new Logging("CONFIG",Launcher.class.getName(),"Library Path: " + System.getProperty("java.library.path"),null);
    new Logging("CONFIG",Launcher.class.getName(),"JRE Version: " + System.getProperty("java.runtime.version"),null);
    new Logging("CONFIG",Launcher.class.getName(),"JVM Bit size: " + System.getProperty("sun.arch.data.model"),null);
    new Logging("CONFIG",Launcher.class.getName(),"Application Name: openweave 1.0 alpha",null);
    new Logging("CONFIG",Launcher.class.getName(),"User Home Directory: " + System.getProperty("user.home"),null);
    new Logging("CONFIG",Launcher.class.getName(),"User Working Directory: " + System.getProperty("user.dir"),null);
    new Logging("INFO",Launcher.class.getName(),"Test INFO logging.",null);
    new Logging("FINE",Launcher.class.getName(),"Test FINE logging.",null);
    new Logging("FINEST",Launcher.class.getName(),"Test FINEST logging.",null);
  }
} 