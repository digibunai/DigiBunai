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

import java.io.File;
import java.util.ArrayList;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * Added: 28 Feb 2017
 * @author Aatif Ahmad Khan
 */
public class EncryptZip {
    
    /**
     * Description: Exports a Password Protected Zipped Archive of files added to given path.
     * @param zipPath
     * @param filesToAdd
     * @param zipPassword 
     */
    public EncryptZip(String zipPath, ArrayList<File> filesToAdd, String zipPassword){
        try {
            ZipFile zipFile = new ZipFile(zipPath);
            
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); 
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
             
            //Set password
            parameters.setPassword(zipPassword);
             
            zipFile.addFiles(filesToAdd, parameters);
        } 
        catch (ZipException ex) 
        {
            new Logging("SEVERE",EncryptZip.class.getName(),ex.toString(),ex);
        }
    }
    
}
