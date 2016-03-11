package org.stein.edwino.minhagasolina.cardviews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.TextView;

import org.stein.edwino.minhagasolina.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AbastecimentosViewHolder extends RecyclerView.ViewHolder{

    protected View viewRoot;

    protected TextView valorTotal;
    protected TextView litros;
    protected TextView precoLitro;
    protected TextView quilometragem;
    protected TextView dataView;

    public AbastecimentosViewHolder(View itemView, boolean simple) {
        super(itemView);

        this.valorTotal = (TextView) itemView.findViewById(R.id.valorTotal);
        this.litros = (TextView) itemView.findViewById(R.id.litros);
        this.precoLitro = (TextView) itemView.findViewById(R.id.precoLitro);
        this.quilometragem = (TextView) itemView.findViewById(R.id.quilometragem);
        this.dataView = (TextView) itemView.findViewById(R.id.data);

        this.viewRoot = itemView;
        if(simple) this.setSimpleLayout();
    }


    protected String formatFloat(float input){
        return String.format("%.2f", input).replace('.', ',');
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal.setText("R$ " + this.formatFloat(valorTotal));
    }

    public void setLitros(float litros) {
        this.litros.setText(this.formatFloat(litros)+" litros");
    }

    public void setPrecoLitro(float precoLitro) {
        this.precoLitro.setText("R$ "+this.formatFloat(precoLitro));
    }

    public void setQuilometragem(float quilometragem) {
        this.quilometragem.setText(this.formatFloat(quilometragem)+" km");
    }

    public void setData(Date dataView) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        this.dataView.setText(format.format(dataView));
    }

    public void setSimpleLayout(){
        this.viewRoot.findViewById(R.id.separator).setVisibility(View.GONE);
        this.viewRoot.findViewById(R.id.editAbastecimento).setVisibility(View.GONE);
        this.viewRoot.findViewById(R.id.deleteAbastecimento).setVisibility(View.GONE);
    }
}