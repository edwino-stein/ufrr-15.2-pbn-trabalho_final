package org.stein.edwino.fuelsheet.tabs;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.stein.edwino.fuelsheet.R;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public static final int RELATORIO_TAB = 0;
    public static final int ABASTECIMENTOS_TAB = 1;

    protected PlaceholderFragment[] tabs;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.tabs = new PlaceholderFragment[2];

        this.tabs[this.RELATORIO_TAB] = new RelatorioTab(context, this.RELATORIO_TAB);
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

            case SectionsPagerAdapter.RELATORIO_TAB:
                return this.context.getResources().getString(R.string.tab_relatorio);

            case SectionsPagerAdapter.ABASTECIMENTOS_TAB:
                return this.context.getResources().getString(R.string.tab_abastecimentos);
        }

        return null;
    }
}