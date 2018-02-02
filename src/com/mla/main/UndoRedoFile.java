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
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
/**
 *
 * @Designing undo redo function
 * @author Amit Kumar Singh
 * 
 */
public class UndoRedoFile {
    private byte nextPointer = 0;
    
    public BufferedImage undo(){
        if(nextPointer>0){}
        nextPointer--;
        String filePath = System.getProperty("user.dir") + "\\mla\\urnedo\\"+nextPointer+".png";
        return loadData(filePath);
    }
    
    public byte canUndo(){
        if(nextPointer<=0)    
            return 0;
        String filePath = System.getProperty("user.dir") + "\\mla\\urnedo\\"+(nextPointer-1)+".png";
        File file = new File(filePath);
        filePath = null;
        if (!file.exists())
            return 0;
        return 1;
    }
    
    public byte canRedo(){
        String filePath = System.getProperty("user.dir") + "\\mla\\urnedo\\"+(nextPointer+1)+".png";
        File file = new File(filePath);
        filePath = null;
        if (!file.exists())
            return 0;
        return 1;
    }
    
    public BufferedImage redo(){
        nextPointer++;        
        String filePath = System.getProperty("user.dir") + "\\mla\\urnedo\\"+nextPointer+".png";
        return loadData(filePath);
    }
    
    private BufferedImage loadData(String filePath){
        File file = new File(filePath);
        if (file.exists())
            try {
                return ImageIO.read(file);
            } catch (IOException ex) {
                Logger.getLogger(UndoRedoFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        file = null;
        filePath = null;
        return null;
    }
    
    public void store(BufferedImage objBufferedImage){
         if(objBufferedImage!=null){
             try {
                 String filePath = System.getProperty("user.dir") + "\\mla\\urnedo\\"+nextPointer+".png";
                 File file = new File(filePath);
                 ImageIO.write(objBufferedImage, "png", file);
                 nextPointer++;
             } catch (IOException ex) {
                 Logger.getLogger(UndoRedoFile.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
        //objBufferedImage = null;
    }
    
    public void clear(){
        nextPointer = 0;
        File file = new File(System.getProperty("user.dir") + "\\mla\\urnedo\\");
        recursiveDelete(file);
        file.mkdir();
        file = null;
    }
    
    private static void recursiveDelete(File file) {
        //to end the recursive loop
        if (!file.exists())
            return;    
        //if directory, go inside and call recursively
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                //call recursively
                recursiveDelete(f);
            }
        }
        //call delete to delete files and empty directory
        file.delete();
        //System.out.println("Deleted file/folder: "+file.getAbsolutePath());
    }
}