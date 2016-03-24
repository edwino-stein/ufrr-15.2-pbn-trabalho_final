package org.stein.edwino.fuelsheet.database.entities;

import android.content.ContentValues;
import android.database.Cursor;

import org.stein.edwino.fuelsheet.database.Entity;
import org.stein.edwino.fuelsheet.models.VeiculoModel;

public class Veiculo extends Entity {

    public Veiculo(){}

    public Veiculo(VeiculoModel veiculoModel){
        this.id = -1;
        this.serverId = veiculoModel.id;
        this.descricao = veiculoModel.descricao;
        this.quilometragem = veiculoModel.quilometragem;
    }

    protected int id = -1;
    protected int serverId;
    protected String descricao;
    protected float quilometragem;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(float quilometragem) {
        this.quilometragem = quilometragem;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    protected String tableName = "veiculos";
    protected String[] entityModel = {
        "'id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE",
        "'serverId' INTEGER NOT NULL UNIQUE",
        "'descricao' TEXT NOT NULL",
        "'quilometragem' FLOAT NOT NULL"
    };

    @Override
    public String getTableName() {
        return this.tableName;
    }

    @Override
    protected String[] getEntityModelArray() {
        return this.entityModel;
    }

    @Override
    public void setValues(Cursor c){
        this.id = c.getInt(c.getColumnIndex("id"));
        this.serverId = c.getInt(c.getColumnIndex("serverId"));
        this.descricao = c.getString(c.getColumnIndex("descricao"));
        this.quilometragem = c.getFloat(c.getColumnIndex("quilometragem"));
    }

    @Override
    public ContentValues getValues(){
        ContentValues values = new ContentValues();

        if(this.id >= 0){
            values.put("id", this.id);
        }

        values.put("serverId", this.serverId);
        values.put("descricao", this.descricao);
        values.put("quilometragem", this.quilometragem);

        return values;
    }

}
