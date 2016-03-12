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
import org.stein.edwino.minhagasolina.database.DataBase;
import org.stein.edwino.minhagasolina.database.Entity;
import org.stein.edwino.minhagasolina.database.entities.Veiculo;
import org.stein.edwino.minhagasolina.models.Abastecimento;
import org.stein.edwino.minhagasolina.models.VeiculoModel;
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

    private Abastecimento[] abastecimentosData;
    private DataBase dataBaseVeiculos;
    private int tabbedReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.tabbedReady = 0;
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onFloatingActionButtonClicked(view);
            }
        });

        this.dataBaseVeiculos = new DataBase(this, "org.stein.edwino.minhagasolina.database.entities.Veiculo");
    }

    protected void onStart(){
        super.onStart();

        Veiculo veiculo = this.getVeiculo();
        if(veiculo == null){
            this.requestVeiculo(true);
        }

        if(this.abastecimentosData != null){
            this.updateRecyclerView();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    this.abastecimentosData = new Abastecimento[total];

                    for (int i = 0; i < total; i++)
                        this.abastecimentosData[i] = Abastecimento.parseJson(jsonResponse.getData(i));

                    this.updateRecyclerView();
                }
                else{
                    Log.d("Request", "Falhou");
                }

            break;

            case RequestActivity.CREATE_VEICULO:

                if(resultCode != RESULT_OK)
                    Log.d("Request", "Falhou");

                String response =  data.getStringExtra("response");
                JsonParser jsonResponse = new JsonParser(response);

                if(!jsonResponse.isSuccess()){
                    Log.d("Request", "Falhou");
                    return;
                }

                Veiculo veiculo = new Veiculo(VeiculoModel.parseJson(jsonResponse.getOneData()));
                this.dataBaseVeiculos.insert(veiculo);
                this.readFromServer(veiculo);

            break;

            case RequestActivity.READ_VEICULO:

                if(resultCode != RESULT_OK)
                    Log.d("Request", "Falhou");

                String response2 =  data.getStringExtra("response");

                Log.d("teste", response2);
                JsonParser jsonResponse2 = new JsonParser(response2);

                if(!jsonResponse2.isSuccess()){
                    Log.d("Request", "Falhou");
                    return;
                }

                Veiculo veiculo2 = new Veiculo(VeiculoModel.parseJson(jsonResponse2.getOneData()));
                this.dataBaseVeiculos.insert(veiculo2);
                this.readFromServer(veiculo2);

            break;

            case VeiculoActivity.VEICULO_REQUEST:

                if(resultCode != RESULT_OK) return;

                boolean isNovo = data.getBooleanExtra("isNovo", false);

                if(isNovo){

                    Intent requestIntent = new Intent("org.stein.edwino.minhagasolina.RequestActivity");
                    requestIntent.putExtra("descricao", data.getStringExtra("descricao"));
                    requestIntent.putExtra("quilometragem", data.getFloatExtra("quilometragem", 0));
                    this.startActivityForResult(requestIntent, RequestActivity.CREATE_VEICULO);
                }
                else{

                    Intent requestIntent = new Intent("org.stein.edwino.minhagasolina.RequestActivity");
                    requestIntent.putExtra("codigo", data.getStringExtra("codigo"));
                    this.startActivityForResult(requestIntent, RequestActivity.READ_VEICULO);
                }

            break;
        }

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

        if(id == R.id.action_refresh){
            this.readFromServer(this.getVeiculo());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* ********************************** Comportamento das Abas ********************************* */

    public void onAllTabsReady(){
        Veiculo veiculo = this.getVeiculo();

        if(veiculo != null){
            this.readFromServer(veiculo);
        }

    }

    @Override
    public void onTabActivityCreated(PlaceholderFragment fragment, int index) {

        if(index == SectionsPagerAdapter.ABASTECIMENTOS_TAB){
            this.recyclerView = ((AbastecimentosTab)fragment).recyclerView;
        }

    }

    @Override
    public void onTabStart(PlaceholderFragment fragment, int index){

        this.tabbedReady++;

        if(index == SectionsPagerAdapter.ABASTECIMENTOS_TAB){
            AbastecimentosAdapter adapter = new AbastecimentosAdapter();
            this.recyclerView.setAdapter(adapter);
        }

        if(this.tabbedReady >= this.sectionsPagerAdapter.getCount())
            this.onAllTabsReady();
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

    protected void updateRecyclerView(){
        AbastecimentosAdapter adapter = new AbastecimentosAdapter(this.abastecimentosData);
        this.recyclerView.setAdapter(adapter);
    }

    protected AbastecimentosAdapter getRecyclerViewAdapter(){
        if(this.recyclerView == null) return null;
        return (AbastecimentosAdapter) this.recyclerView.getAdapter();
    }

    protected void requestVeiculo(boolean force){
        Intent requestIntent = new Intent("org.stein.edwino.minhagasolina.VeiculoActivity");
        requestIntent.putExtra("force", force);
        this.startActivityForResult(requestIntent, VeiculoActivity.VEICULO_REQUEST);
    }

    protected void readFromServer(Veiculo veiculo){

        if(veiculo == null) return;

        Intent requestIntent = new Intent("org.stein.edwino.minhagasolina.RequestActivity");

        requestIntent.putExtra("veiculo", veiculo.getServerId());
        this.startActivityForResult(requestIntent, RequestActivity.READ_ABASTECIMENTOS);
    }

    protected Veiculo getVeiculo(){

        Entity[] data = this.dataBaseVeiculos.fetchAll();

        if(data.length > 0){
            return (Veiculo) data[0];
        }

        return null;
    }
}
