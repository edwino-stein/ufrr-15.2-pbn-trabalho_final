package org.stein.edwino.minhagasolina.cardviews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.stein.edwino.minhagasolina.R;

public class AbastecimentosViewHolder extends RecyclerView.ViewHolder{

    public TextView teste;

    public AbastecimentosViewHolder(View itemView) {
        super(itemView);

        this.teste = (TextView) itemView.findViewById(R.id.teste);
    }
}