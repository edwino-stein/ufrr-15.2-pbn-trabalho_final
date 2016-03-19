package org.stein.edwino.fuelsheet.database;

import android.content.ContentValues;
import android.database.Cursor;

public abstract class Entity {

    public abstract String getTableName();
    protected abstract String[] getEntityModelArray();
    public abstract void setValues(Cursor c);
    public abstract ContentValues getValues();

    public String getEntityModel(){

        String model = "";
        String[] entityModel = this.getEntityModelArray();

        for(int i = 0; i < entityModel.length; i++){
            model += entityModel[i];

            if(i < entityModel.length - 1){
                model += ", ";
            }
        }

        return model;
    }

}
