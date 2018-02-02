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

import com.mla.main.Logging;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @Designing undo redo function
 * @author Amit Kumar Singh
 * 
 */
public class UndoRedo {
    private List<String> commands;
    private List<Object> datas;
    private int nextPointer = 0;
    private String strTag;
    private Object objData;
    
    public UndoRedo(){
        commands = new ArrayList<>();
        datas = new ArrayList<>();
    }
    public void setStrTag(String objString){
        this.strTag= objString;
    }
    public String getStrTag(){
        return this.strTag;
    }
    
    public void setObjData(Object objObject){
        this.objData= objObject;
    }
    public Object getObjData(){
        return this.objData;
    }
    
    public void doCommand(String objString, Object objObject) {
        List<String> newStringList = new ArrayList<>(nextPointer + 1);
        for(int k = 0; k < nextPointer; k++) {
            newStringList.add(commands.get(k));
        }
        newStringList.add(objString);
        commands = newStringList;
        
        List<Object> newObjectList = new ArrayList<>(nextPointer + 1);
        for(int k = 0; k < nextPointer; k++) {
            newObjectList.add(datas.get(k));
        }
        newObjectList.add(objObject);
        datas = newObjectList;
        
        nextPointer++;
    }

    public boolean canUndo() {
        return nextPointer > 0;
    }
    
    public boolean canRedo() {
        return nextPointer < commands.size();
    }

    public void undo() {
        if(canUndo()) {
            nextPointer--;
            setStrTag(commands.get(nextPointer));
            setObjData(datas.get(nextPointer));
         } else {
            new Logging("SEVERE",UndoRedo.class.getName(),"Cannot undo"+nextPointer, new IllegalStateException("Cannot undo"+nextPointer));
         }
    }

    public void redo() {
        if(canRedo()) {
            setStrTag(commands.get(nextPointer));
            setObjData(datas.get(nextPointer));
            nextPointer++;
        } else {
            new Logging("SEVERE",UndoRedo.class.getName(),"Cannot redo"+nextPointer, new IllegalStateException("Cannot redo"+nextPointer)); 
        }
    }
}