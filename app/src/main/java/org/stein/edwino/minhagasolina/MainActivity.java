package org.stein.edwino.minhagasolina;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import org.stein.edwino.minhagasolina.tabs.PlaceholderFragment;
import org.stein.edwino.minhagasolina.tabs.SectionsPagerAdapter;
import org.stein.edwino.minhagasolina.tabs.TabListener;

public class MainActivity extends AppCompatActivity implements TabListener, TabLayout.OnTabSelectedListener {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {onFloatingActionButtonClicked(view);}
        });

    }

    /* ********************************* Comportamento dos menus ********************************* */

    public void onFloatingActionButtonClicked(View view){
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* ********************************** Comportamento das Abas ********************************* */

    @Override
    public void onTabActivityCreated(PlaceholderFragment fragment, int index) {
        Log.d("Lifecycle", "onTabActivityCreated");
    }

    @Override
    public void onTabStart(PlaceholderFragment fragment, int index){
        Log.d("Lifecycle", "onTabStart");
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.d("Tab", String.valueOf(tab.getPosition()));
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}

}
