package org.stein.edwino.minhagasolina.cardviews;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.stein.edwino.minhagasolina.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AbastecimentosTotalViewHolder extends RecyclerView.ViewHolder{

    protected TextView totalAbastecimentos;

    public AbastecimentosTotalViewHolder(View itemView) {
        super(itemView);
        this.totalAbastecimentos = (TextView) itemView.findViewById(R.id.totalAbastecimentos);
    }

    public void setTotalAbastecimentos(int totalAbastecimentos) {

        if(totalAbastecimentos <= 0)
            this.totalAbastecimentos.setText("Nenhum abastecimento registrado");

        else if(totalAbastecimentos == 1)
            this.totalAbastecimentos.setText("Um abastecimento registrado");

        else
            this.totalAbastecimentos.setText(String.valueOf(totalAbastecimentos)+" Abastecimentos");
    }
}