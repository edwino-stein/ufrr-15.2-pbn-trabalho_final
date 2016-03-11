package org.stein.edwino.minhagasolina;

import android.content.Intent;
import android.os.Debug;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.stein.edwino.minhagasolina.cardviews.AbastecimentosAdapter;
import org.stein.edwino.minhagasolina.models.Abastecimento;
import org.stein.edwino.minhagasolina.tabs.AbastecimentosTab;
import org.stein.edwino.minhagasolina.tabs.PlaceholderFragment;
import org.stein.edwino.minhagasolina.tabs.SectionsPagerAdapter;
import org.stein.edwino.minhagasolina.tabs.TabListener;
import org.stein.edwino.minhagasolina.util.JsonParser;

public class MainActivity extends AppCompatActivity implements TabListener, TabLayout.OnTabSelectedListener {


    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.d("result", String.valueOf(requestCode) + " - " + String.valueOf(resultCode));

        switch (requestCode) {

            case RequestActivity.READ_ABASTECIMENTOS:

                if(resultCode == RESULT_OK){

                    String response =  data.getStringExtra("response");
                    JsonParser jsonResponse = new JsonParser(response);

                    if(!jsonResponse.isSuccess()){
                        Log.d("Request", "Falhou");
                        return;
                    }

                    int total = jsonResponse.getTotal();
                    Abastecimento[] abastecimentosData = new Abastecimento[total];

                    for (int i = 0; i < total; i++){
                        abastecimentosData[i] = Abastecimento.parseJson(jsonResponse.getData(i));
                        Log.d("model", abastecimentosData[i].toString());
                    }
                }
                else{
                    Log.d("Request", "Falhou");
                }
            break;


        }

    }


    /* ********************************* Comportamento dos menus ********************************* */

    public void onFloatingActionButtonClicked(View view){
//        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
        this.readFromServer();
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
        if(index == SectionsPagerAdapter.ABASTECIMENTOS_TAB){
            this.recyclerView = ((AbastecimentosTab)fragment).recyclerView;
        }
    }

    @Override
    public void onTabStart(PlaceholderFragment fragment, int index){

        if(index == SectionsPagerAdapter.ABASTECIMENTOS_TAB){
            AbastecimentosAdapter adapter = new AbastecimentosAdapter();
            this.recyclerView.setAdapter(adapter);


        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.d("Tab", String.valueOf(tab.getPosition()));
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}

    /* ***************************************** Outros ****************************************** */

    @Override
    public void startActivityForResult(Intent intent, int requestCode){
        intent.putExtra("requestCode", requestCode);
        super.startActivityForResult(intent, requestCode);
    }

    protected AbastecimentosAdapter getRecyclerViewAdapter(){
        if(this.recyclerView == null) return null;
        return (AbastecimentosAdapter) this.recyclerView.getAdapter();
    }

    protected void readFromServer(){
        Intent requestIntent = new Intent("org.stein.edwino.minhagasolina.RequestActivity");
        requestIntent.putExtra("veiculo", 1);
        this.startActivityForResult(requestIntent, RequestActivity.READ_ABASTECIMENTOS);
    }
}
