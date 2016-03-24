package org.stein.edwino.fuelsheet.models;

import org.json.JSONException;
import org.json.JSONObject;

public class VeiculoModel {

    public int id;

    public String descricao;

    public float quilometragem;

    public static VeiculoModel parseJson(JSONObject jsonObj){

        VeiculoModel model = new VeiculoModel();

        try {
            model.id = jsonObj.getInt("id");
            model.descricao = jsonObj.getString("descricao");
            model.quilometragem = (float) jsonObj.getDouble("quilometragem");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return model;
    }

}
