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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @Designing accessor and mutator function for logging
 * @author Amit Kumar Singh
 * 
 */
public class Logging {
    /*
    • ALL 	All levels including custom levels.
    • DEBUG 	Designates fine-grained informational events that are most useful to debug an application.
    • ERROR 	Designates error events that might still allow the application to continue running.
    • FATAL 	Designates very severe error events that will presumably lead the application to abort.
    • INFO 	Designates informational messages that highlight the progress of the application at coarse-grained level.
    • OFF 	The highest possible rank and is intended to turn off logging.
    • TRACE 	Designates finer-grained informational events than the DEBUG.
    • WARN 	Designates potentially harmful situations.
    There are seven logging levels provided by the Level class.
    • SEVERE (highest level)
    • WARNING
    • INFO
    • CONFIG
    • FINE
    • FINER
    • FINEST (lowest level)    
    */
    
    
    public String strRoot = System.getProperty("user.dir");
    
    public Logging(String strLevel, String strClassName, String strMethodName, Exception strMessgae){
        try {
            StringBuilder log = new StringBuilder();
            SimpleDateFormat date_format = new SimpleDateFormat("MMM dd,yyyy HH:mm");
            log.append(new Date());
            log.append(" ");
            log.append(strClassName);
            log.append(" ");
            log.append(strMethodName);
            log.append(" ");
            log.append(strLevel);
            log.append(" ");
            log.append(strMessgae);
            log.append(System.getProperty("line.separator"));
            Files.write(Paths.get(strRoot+"\\log\\logging.log"), log.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /*
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    static {  
        try {
            //LOGGER.setLevel(Level.INFO);
            Formatter formatter = new Formatter() {
                @Override
                public String format(LogRecord arg0) {
                    StringBuilder b = new StringBuilder();
                    b.append(new Date());
                    SimpleDateFormat date_format = new SimpleDateFormat("MMM dd,yyyy HH:mm");
                    b.append(" ");
                    b.append(arg0.getSourceClassName());
                    b.append(" ");
                    b.append(arg0.getSourceMethodName());
                    b.append(" ");
                    b.append(arg0.getLevel());
                    b.append(" ");
                    b.append(arg0.getMessage());
                    b.append(System.getProperty("line.separator"));
                    return b.toString();
                }
            };

            Handler fh = new FileHandler(strRoot+"\\error\\logging.log");
            fh.setFormatter(formatter);
            LOGGER.addHandler(fh);

            Handler ch = new ConsoleHandler();
            ch.setFormatter(formatter);
            LOGGER.addHandler(ch);

            LogManager lm = LogManager.getLogManager();
            lm.addLogger(LOGGER);
        
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    */
}