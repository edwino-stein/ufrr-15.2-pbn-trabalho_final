package org.stein.edwino.fuelsheet.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.stein.edwino.fuelsheet.R;

public class RelatorioTab extends PlaceholderFragment {

    public RecyclerView recyclerView;

    public RelatorioTab(){
        super(null, 0);
    }

    public RelatorioTab(Context context, int index){
        super(context, index);
        this.layout = R.layout.relatorio_fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        LinearLayoutManager llm = new LinearLayoutManager(this.context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.relatorioList);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(llm);

        return this.rootView;
    }
}