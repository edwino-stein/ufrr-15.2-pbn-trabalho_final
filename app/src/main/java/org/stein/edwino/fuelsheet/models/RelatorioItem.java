package org.stein.edwino.fuelsheet.models;

public class RelatorioItem {

    protected int index;
    protected String label;
    protected String data;

    public RelatorioItem(int index, String label){
        this.index = index;
        this.label = label;
        this.data = "";
    }

    public RelatorioItem(int index, String label, String data){
        this.index = index;
        this.label = label;
        this.data = data;
    }

    public int getIndex() {
        return index;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
