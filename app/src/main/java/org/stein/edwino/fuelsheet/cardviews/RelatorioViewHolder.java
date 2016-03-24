package org.stein.edwino.fuelsheet.cardviews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.stein.edwino.fuelsheet.R;

public class RelatorioViewHolder extends RecyclerView.ViewHolder{

    public TextView label;
    public TextView data;

    public RelatorioViewHolder(View itemView) {
        super(itemView);
        this.label = (TextView) itemView.findViewById(R.id.relatorioLabel);
        this.data = (TextView) itemView.findViewById(R.id.relatorioData);
    }
}