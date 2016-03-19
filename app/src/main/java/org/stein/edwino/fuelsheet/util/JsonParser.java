package org.stein.edwino.fuelsheet.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonParser {

    protected boolean errorFlag = false;
    protected String errorMessage = "";

    protected boolean success = false;
    protected int total = -1;
    protected JSONObject[] data;
    protected String responseMessage = "";

    public JsonParser(String json){

        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(json);

        } catch (JSONException e) {
            e.printStackTrace();
            this.errorFlag = true;
            this.errorMessage = "Parser error";
        }

        this.setSuccess(jsonObj);

        if(this.success)
            this.setTotal(jsonObj).setData(jsonObj);
    }


    public boolean isSuccess() {
        return success;
    }

    public int getTotal() {
        return total;
    }

    public JSONObject[] getData() {
        return data;
    }

    public JSONObject getOneData(){
        return this.data[0];
    }

    public JSONObject getData(int index) {
        if(index < 0 || index > this.total) return null;
        return data[index];
    }

    protected JsonParser setSuccess(JSONObject jsonObj){

        try {

            this.success = jsonObj.getBoolean("success");

            if(!this.success){
                this.responseMessage = jsonObj.getString("message");
            }

        } catch (JSONException e) {
            e.printStackTrace();
            this.errorFlag = true;
            this.errorMessage = "Parser error";
            this.success = false;
        }

        return this;
    }

    public JsonParser setTotal(JSONObject jsonObj){

        if(!jsonObj.has("total")) {
            return this;
        }

        try {
            this.total = jsonObj.getInt("total");
        } catch (JSONException e) {
            e.printStackTrace();
            this.errorFlag = true;
            this.errorMessage = "Parser error";
            this.total = -1;
        }

        return this;
    }


    public JsonParser setData(JSONObject jsonObj){


        if(this.total < 0){
            this.data = new JSONObject[1];

            try {
                this.data[0] = jsonObj.getJSONObject("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return this;
        }

        JSONArray dataArray = null;

        try {

            dataArray = jsonObj.getJSONArray("data");

        } catch (JSONException e) {
            e.printStackTrace();
            this.errorFlag = true;
            this.errorMessage = "Parser error";
            return this;
        }

        this.data = new JSONObject[this.total];

        for (int i = 0; i < this.data.length; i++) {
            try {
                this.data[i] = (JSONObject) dataArray.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
                this.errorFlag = true;
                this.errorMessage = "Parser error";
            }
        }

        return this;
    }

}
