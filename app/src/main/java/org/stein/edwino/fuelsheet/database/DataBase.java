package org.stein.edwino.fuelsheet.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

public class DataBase {

    protected SQLiteDatabase dataBase;
    protected String dataBaseName = "minha_gasolina";
    protected String entity;
    protected Context context;
    protected String errorMessage;


    public DataBase(){}

    public DataBase(Context context, String entity){

        this.dataBase = null;
        this.context = context;
        this.entity = entity;

        this.initTable();
    }

    protected void open(){
        this.dataBase = this.context.openOrCreateDatabase(this.dataBaseName, SQLiteDatabase.CREATE_IF_NECESSARY, null);
    }

    protected void close(){

        if(this.dataBase != null){
            this.dataBase.close();
            this.dataBase = null;
        }
    }

    protected boolean tableExistis(String tableName){
        this.open();
        Cursor c = this.dataBase.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='"+tableName+"'", null);

        if(c == null){
            this.close();
            return false;
        }

        if(c.getCount() <= 0){
            this.close();
            return  false;
        }

        this.close();
        return true;
    }

    protected void initTable(){
        Entity entity = this.getEntityInstance();

        if(!this.tableExistis(entity.getTableName())){
            this.open();
            this.dataBase.execSQL("CREATE TABLE " + entity.getTableName() + " (" + entity.getEntityModel() + ")");
            this.close();
        }
    }

    protected Entity getEntityInstance() {

        try {
            Entity e = (Entity) Class.forName(this.entity).newInstance();
            return e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Entity[] fetchAll(){
        Entity entity = this.getEntityInstance();

        if(entity == null){
            return null;
        }

        this.open();
        Cursor queryResult = this.dataBase.rawQuery("SELECT * FROM " + entity.getTableName(), null);

        if(queryResult == null){
            this.close();
            return null;
        }

        Entity[] data = new Entity[queryResult.getCount()];

        if(!queryResult.moveToFirst()){
            this.close();
            return data;
        }

        int i = 0;

        do{
            data[i] = this.getEntityInstance();
            data[i].setValues(queryResult);
            i++;
        }while (queryResult.moveToNext());

        this.close();
        return data;
    }

    public boolean insert(Entity entity){

        this.open();

        try {
            this.dataBase.insertOrThrow(entity.getTableName(), null, entity.getValues());

        }catch (SQLiteConstraintException e){
            e.printStackTrace();
            this.errorMessage = e.getMessage();

            this.close();
            return false;
        }

        this.close();
        return true;
    }

    public Entity[] select(String where){

        this.open();
        Cursor queryResult = this.dataBase.rawQuery("SELECT * FROM " + this.getEntityInstance().getTableName() + " WHERE " + where, null);

        if(queryResult == null){
            this.close();
            return null;
        }

        Entity[] data = new Entity[queryResult.getCount()];

        if(!queryResult.moveToFirst()){
            this.close();
            return data;
        }

        int i = 0;

        do{
            data[i] = this.getEntityInstance();
            data[i].setValues(queryResult);
            i++;
        }while (queryResult.moveToNext());

        this.close();
        return data;
    }

    public boolean delete(String where){

        this.open();
        if(this.dataBase.delete(this.getEntityInstance().getTableName(), where, null) >= 1){
            this.close();
            return true;
        }

        this.close();
        return false;
    }

    public boolean update(Entity entity, String where){

        this.open();
        if(this.dataBase.update(this.getEntityInstance().getTableName(), entity.getValues(), where, null) >= 1){
            this.close();
            return true;
        }

        this.close();
        return false;
    }

    public void clearAll(){

        this.open();
        this.dataBase.execSQL("DROP TABLE " + this.getEntityInstance().getTableName());
        this.close();

        this.initTable();
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
