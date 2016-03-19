package org.stein.edwino.fuelsheet.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.stein.edwino.fuelsheet.R;

public class AbastecimentosTab extends PlaceholderFragment {

    public RecyclerView recyclerView;

    public AbastecimentosTab(){
        super(null, 0);
    }

    public AbastecimentosTab(Context context, int index){
        super(context, index);
        this.layout = R.layout.abastecimentos_fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        LinearLayoutManager llm = new LinearLayoutManager(this.context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.abastecimentosList);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(llm);

        return this.rootView;
    }
}