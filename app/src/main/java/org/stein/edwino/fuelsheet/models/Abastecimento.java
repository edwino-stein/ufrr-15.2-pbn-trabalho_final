package org.stein.edwino.fuelsheet.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Abastecimento {

    protected int id;

    protected float valorTotal;

    protected float litros;

    protected float precoLitro;

    protected float quilometragem;

    protected Date data;

    protected Date criadoEm;

    protected int veiculo;

    public String toString(){
        return "id: "+this.id+"; valorTotal: "+this.valorTotal +"; litros: "+this.litros+"; precoLitro: "+this.precoLitro+"; quilometragem: "+this.quilometragem+"; data: "+this.data+"; criadoEm: "+this.criadoEm;
    }

    public static Abastecimento parseJson(JSONObject jsonObj){

        Abastecimento model = new Abastecimento();

        try {
            model.setId(jsonObj.getInt("id"));
            model.setValorTotal((float) jsonObj.getDouble("valorTotal"));
            model.setLitros((float) jsonObj.getDouble("litros"));
            model.setPrecoLitro((float) jsonObj.getDouble("precoLitro"));
            model.setQuilometragem((float) jsonObj.getDouble("quilometragem"));
            model.setData(jsonObj.getJSONObject("data").getString("date"));
            model.setCriadoEm(jsonObj.getJSONObject("criadoEm").getString("date"));
            model.setVeiculo(jsonObj.getInt("veiculo"));
        } catch (JSONException e) {
            e.printStackTrace();
            return model;
        }

        return model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public float getLitros() {
        return litros;
    }

    public void setLitros(float litros) {
        this.litros = litros;
    }

    public float getPrecoLitro() {
        return precoLitro;
    }

    public void setPrecoLitro(float precoLitro) {
        this.precoLitro = precoLitro;
    }

    public float getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(float quilometragem) {
        this.quilometragem = quilometragem;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setData(String data) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            this.data = format.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            this.data = null;
        }
    }

    public Date getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Date criadoEm) {
        this.criadoEm = criadoEm;
    }

    public void setCriadoEm(String criadoEm) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            this.criadoEm = format.parse(criadoEm);
        } catch (ParseException e) {
            e.printStackTrace();
            this.criadoEm = null;
        }
    }

    public int getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(int veiculo) {
        this.veiculo = veiculo;
    }
}
