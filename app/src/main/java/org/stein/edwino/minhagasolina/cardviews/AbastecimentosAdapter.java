package org.stein.edwino.minhagasolina.cardviews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.stein.edwino.minhagasolina.R;
import org.stein.edwino.minhagasolina.models.Abastecimento;

import java.util.ArrayList;

public class AbastecimentosAdapter extends RecyclerView.Adapter<AbastecimentosViewHolder>{

    protected ArrayList<Abastecimento> dataList;

    public AbastecimentosAdapter(){
        this.dataList = new ArrayList<Abastecimento>();
    }

    public AbastecimentosAdapter(Abastecimento data[]){
        this.dataList = new ArrayList<Abastecimento>();
        for(int i = 0; i < data.length; i++)
            this.dataList.add(data[i]);
    }

    @Override
    public AbastecimentosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.abastecimentos_cardview, parent, false);

        return new AbastecimentosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AbastecimentosViewHolder holder, int position) {
//        holder.teste.setText(this.dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.dataList.size();
    }

    public int getLastPosition(){
        return this.dataList.size() - 1;
    }


    public void addItem(Abastecimento data){
        this.addItem(data, this.dataList.size(), true);
    }

    public void addItem(Abastecimento data, int position){
        this.addItem(data, position, true);
    }

    public void addItem(Abastecimento data, boolean notify){
        this.addItem(data, this.dataList.size(), notify);
    }

    public void addItem(Abastecimento data, int position, boolean notify){
        this.dataList.add(position, data);
        if(notify) this.notifyDataSetChanged();
    }

    public void removeItem(int position){
        this.removeItem(position, true);
    }

    public void removeItem(int position, boolean notify){
        this.dataList.remove(position);
        if(notify) this.notifyDataSetChanged();
    }
}
