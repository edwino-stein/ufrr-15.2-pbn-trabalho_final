package org.stein.edwino.minhagasolina.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class PlaceholderFragment extends Fragment {

    protected int layout;
    protected View rootView;
    protected Context context;
    protected int index;

    public PlaceholderFragment(Context context, int index) {
        this.context = context;
        this.index = index;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        this.rootView = inflater.inflate(this.layout, container, false);
        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        if(this.context instanceof TabListener){
            ((TabListener) this.context).onTabActivityCreated(this, this.index);
        }
    }

    public void onStart(){
        super.onStart();
        if(this.context instanceof TabListener){
            ((TabListener) this.context).onTabStart(this, this.index);
        }
    }
}