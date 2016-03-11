package org.stein.edwino.minhagasolina.cardviews;

import android.os.Debug;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.stein.edwino.minhagasolina.R;
import org.stein.edwino.minhagasolina.models.Abastecimento;

import java.util.ArrayList;

public class AbastecimentosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    protected ArrayList<Abastecimento> dataList;

    public static final int FIRST_TYPE = 0;
    public static final int SIMPLE_TYPE = 1;
    public static final int AMOUNT_TYPE = 2;

    public AbastecimentosAdapter(){
        this.dataList = new ArrayList<Abastecimento>();
    }

    public AbastecimentosAdapter(Abastecimento data[]){
        this.dataList = new ArrayList<Abastecimento>();
        for(int i = 0; i < data.length; i++)
            this.dataList.add(data[i]);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == FIRST_TYPE || viewType == SIMPLE_TYPE){

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.abastecimentos_cardview, parent, false);

            return new AbastecimentosViewHolder(itemView, viewType == SIMPLE_TYPE);
        }

        else {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.abastecimentos_cardview_total, parent, false);

            return new AbastecimentosTotalViewHolder(itemView);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if(holder instanceof AbastecimentosViewHolder) {

            AbastecimentosViewHolder abastecimentosViewHolder = (AbastecimentosViewHolder) holder;
            Abastecimento model = this.dataList.get(position);

            abastecimentosViewHolder.setValorTotal(model.getValorTotal());
            abastecimentosViewHolder.setLitros(model.getLitros());
            abastecimentosViewHolder.setPrecoLitro(model.getPrecoLitro());
            abastecimentosViewHolder.setQuilometragem(model.getQuilometragem());
            abastecimentosViewHolder.setData(model.getData());
        }

        else if(holder instanceof AbastecimentosTotalViewHolder){
            ((AbastecimentosTotalViewHolder) holder).setTotalAbastecimentos(this.getLastPosition() + 1);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(this.dataList.size() <= 0) return AMOUNT_TYPE;

        if(position == 0) return FIRST_TYPE;
        return position <= this.getLastPosition() ? SIMPLE_TYPE : AMOUNT_TYPE;
    }

    @Override
    public int getItemCount() {
        return this.dataList.size() + 1;
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
