package org.stein.edwino.minhagasolina.tabs;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.stein.edwino.minhagasolina.R;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public static final int ABASTECIMENTOS_TAB = 0;

    protected PlaceholderFragment[] tabs;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.tabs = new PlaceholderFragment[1];

        this.tabs[this.ABASTECIMENTOS_TAB] = new AbastecimentosTab(context, this.ABASTECIMENTOS_TAB);
    }

    @Override
    public PlaceholderFragment getItem(int position) {
        return this.tabs[position];
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case SectionsPagerAdapter.ABASTECIMENTOS_TAB:
                return this.context.getResources().getString(R.string.tab_abastecimentos);
        }

        return null;
    }
}