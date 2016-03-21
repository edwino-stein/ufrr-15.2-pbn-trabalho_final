package org.stein.edwino.fuelsheet;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import org.stein.edwino.fuelsheet.cardviews.AbastecimentosAdapter;
import org.stein.edwino.fuelsheet.database.DataBase;
import org.stein.edwino.fuelsheet.database.Entity;
import org.stein.edwino.fuelsheet.database.entities.Veiculo;
import org.stein.edwino.fuelsheet.cardviews.RelatorioAdapter;
import org.stein.edwino.fuelsheet.models.Abastecimento;
import org.stein.edwino.fuelsheet.models.VeiculoModel;
import org.stein.edwino.fuelsheet.tabs.AbastecimentosTab;
import org.stein.edwino.fuelsheet.tabs.PlaceholderFragment;
import org.stein.edwino.fuelsheet.tabs.RelatorioTab;
import org.stein.edwino.fuelsheet.tabs.SectionsPagerAdapter;
import org.stein.edwino.fuelsheet.tabs.TabListener;
import org.stein.edwino.fuelsheet.util.JavaReport;
import org.stein.edwino.fuelsheet.util.JsonParser;
import org.stein.edwino.fuelsheet.util.ReportResult;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements TabListener, TabLayout.OnTabSelectedListener {


    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private RecyclerView resumoRecyclerView;

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
        tabLayout.setOnTabSelectedListener(this);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onFloatingActionButtonClicked(view);
            }
        });
        floatingActionButton.hide();

        this.dataBaseVeiculos = new DataBase(this, "org.stein.edwino.fuelsheet.database.entities.Veiculo");
    }

    protected void onStart(){
        super.onStart();

        Veiculo veiculo = this.getVeiculo();
        if(veiculo == null){
            this.requestVeiculo(true);
        }

        if(this.abastecimentosData != null){
            this.updateRecyclerView();
            this.updateReport();
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
                    this.updateReport();
                }
                else{
                    Log.d("Request", "Falhou");
                }

            break;

            case AbastecimentoFormActivity.CREATE_ABASTECIMENTO:

                if(resultCode != RESULT_OK){
                    Log.d("Request", "Falhou");
                    return;
                }

                Abastecimento newModel = (Abastecimento) data.getSerializableExtra("model");
                Abastecimento oldData[] = this.abastecimentosData;

                this.abastecimentosData = new Abastecimento[this.abastecimentosData.length + 1];
                this.abastecimentosData[0] = newModel;

                for(int i = 0; i < oldData.length; i++){
                    this.abastecimentosData[i + 1] = oldData[i];
                }

                this.updateRecyclerView();
                this.updateReport();

                Snackbar.make(this.floatingActionButton, "Abastecimento registrado com sucesso.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            break;

            case AbastecimentoFormActivity.UPDATE_ABASTECIMENTO:

                if(resultCode != RESULT_OK){
                    Log.d("Request", "Falhou");
                    return;
                }

                Abastecimento updatedModel = (Abastecimento) data.getSerializableExtra("model");
                this.abastecimentosData[0] = updatedModel;

                this.updateRecyclerView();
                this.updateReport();

                Snackbar.make(this.floatingActionButton, "Abastecimento atualizado com sucesso.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            break;

            case RequestActivity.DELETE_ABASTECIMENTO:

                if(resultCode != RESULT_OK){
                    Log.d("Request", "Falhou");
                    return;
                }

                Abastecimento _oldData[] = this.abastecimentosData;
                this.abastecimentosData = new Abastecimento[this.abastecimentosData.length - 1];

                for(int i = 1; i < _oldData.length; i++){
                    this.abastecimentosData[i - 1] = _oldData[i];
                }

                this.updateRecyclerView();
                this.updateReport();

                Snackbar.make(this.floatingActionButton, "Abastecimento foi excluido com sucesso.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

                    Intent requestIntent = new Intent("org.stein.edwino.fuelsheet.RequestActivity");
                    requestIntent.putExtra("descricao", data.getStringExtra("descricao"));
                    requestIntent.putExtra("quilometragem", data.getFloatExtra("quilometragem", 0));
                    this.startActivityForResult(requestIntent, RequestActivity.CREATE_VEICULO);
                }
                else{

                    Intent requestIntent = new Intent("org.stein.edwino.fuelsheet.RequestActivity");
                    requestIntent.putExtra("codigo", data.getStringExtra("codigo"));
                    this.startActivityForResult(requestIntent, RequestActivity.READ_VEICULO);
                }

            break;
        }

    }

    /* ********************************* Comportamento dos menus ********************************* */

    public void onFloatingActionButtonClicked(View view){
        Intent requestIntent = new Intent("org.stein.edwino.fuelsheet.AbastecimentoFormActivity");
        requestIntent.putExtra("veiculo", this.getVeiculo().getId());
        startActivityForResult(requestIntent, AbastecimentoFormActivity.CREATE_ABASTECIMENTO);
    }

    public void onUpdateAbastecimento(View view){
        Intent requestIntent = new Intent("org.stein.edwino.fuelsheet.AbastecimentoFormActivity");
        requestIntent.putExtra("model", this.abastecimentosData[0]);
        requestIntent.putExtra("veiculo", this.getVeiculo().getId());
        startActivityForResult(requestIntent, AbastecimentoFormActivity.UPDATE_ABASTECIMENTO);
    }

    public void onDeleteAbastecimento(View view){

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Excluir o abastecimento")
              .setIcon(R.drawable.ic_delete_black)
              .setMessage("Você tem certeza que deseja excluir o registro do último abastecimento?");

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Intent requestIntent = new Intent("org.stein.edwino.fuelsheet.RequestActivity");
                requestIntent.putExtra("abastecimento", abastecimentosData[0].getId());
                requestIntent.putExtra("veiculo", getVeiculo().getId());
                startActivityForResult(requestIntent, RequestActivity.DELETE_ABASTECIMENTO);
            }
        });

        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        dialog.show();
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
        if(veiculo != null && this.abastecimentosData == null){
            this.readFromServer(veiculo);
        }
    }

    @Override
    public void onTabActivityCreated(PlaceholderFragment fragment, int index) {

        if(index == SectionsPagerAdapter.ABASTECIMENTOS_TAB){
            this.recyclerView = ((AbastecimentosTab)fragment).recyclerView;
        }

        if(index == SectionsPagerAdapter.RELATORIO_TAB){
            this.resumoRecyclerView = ((RelatorioTab)fragment).recyclerView;
        }

    }

    @Override
    public void onTabStart(PlaceholderFragment fragment, int index){

        this.tabbedReady++;

        if(index == SectionsPagerAdapter.ABASTECIMENTOS_TAB){
            AbastecimentosAdapter adapter = new AbastecimentosAdapter();
            this.recyclerView.setAdapter(adapter);
        }

        if(index == SectionsPagerAdapter.RELATORIO_TAB){
            this.resumoRecyclerView.setAdapter(new RelatorioAdapter(RelatorioAdapter.createTemplate()));
        }

        if(this.tabbedReady >= this.sectionsPagerAdapter.getCount())
            this.onAllTabsReady();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        if(tab.getPosition() == SectionsPagerAdapter.RELATORIO_TAB)
            this.floatingActionButton.hide();
        else
            this.floatingActionButton.show();

        this.viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}

    /* ***************************************** Outros ****************************************** */

    public void updateReport(){

        Veiculo veiculo = this.getVeiculo();
        RelatorioAdapter adapter = this.getResumoRecyclerViewAdapter();

        if(veiculo == null || this.abastecimentosData == null || this.abastecimentosData.length <= 0){
            this.setRelatorioInicial(true);
            return;
        }

        this.setRelatorioInicial(false);
        ReportResult report = JavaReport.calc(this.abastecimentosData);

        adapter.getItem(RelatorioAdapter.ULTIMO_ABASTECIMENTO).setData(ReportResult.formatData(report.ultimo));

        adapter.getItem(RelatorioAdapter.MAIOR_VALOR_ABASTECIMENTO).setData("R$ "+ReportResult.formatFloat(report.maiorValor));
        adapter.getItem(RelatorioAdapter.MENOR_VALOR_ABASTECIMENTO).setData("R$ "+ReportResult.formatFloat(report.menorValor));
        adapter.getItem(RelatorioAdapter.TOTAL_VALOR_ABASTECIMENTO).setData("R$ "+ReportResult.formatFloat(report.totalValor));
        adapter.getItem(RelatorioAdapter.MEDIA_VALOR_ABSTECIMENTO).setData("R$ "+ReportResult.formatFloat(report.mediaValor));

        adapter.getItem(RelatorioAdapter.MAIOR_QUANTIDADE_COMBUSTIVEL).setData(ReportResult.formatFloat(report.maiorLitros)+" litros");
        adapter.getItem(RelatorioAdapter.MENOR_QUANTIDADE_COMBUSTIVEL).setData(ReportResult.formatFloat(report.menorLitros)+" litros");
        adapter.getItem(RelatorioAdapter.MEDIA_QUANTIDADE_COMBUSTIVEL).setData(ReportResult.formatFloat(report.menorLitros)+" litros");

        adapter.getItem(RelatorioAdapter.MAIOR_PRECO).setData("R$ "+ReportResult.formatFloat(report.maiorPreco)+" por litro");
        adapter.getItem(RelatorioAdapter.MENOR_PRECO).setData("R$ "+ReportResult.formatFloat(report.menorPreco)+" por litro");
        adapter.getItem(RelatorioAdapter.MEDIA_PRECO).setData("R$ "+ReportResult.formatFloat(report.mediaPreco)+" por litro");

        adapter.getItem(RelatorioAdapter.QUILOMETROS_RODADOS).setData(ReportResult.formatFloat(report.quilometrosMes)+" km");
        adapter.getItem(RelatorioAdapter.QUILOMETROS_RODADOS_TOTAL).setData(ReportResult.formatFloat(veiculo.getQuilometragem() + report.quilometrosTotal)+" km");

        adapter.getItem(RelatorioAdapter.MEDIA_REDIMENTO_MES).setData(ReportResult.formatFloat(report.rendimentoMes)+" km por litro");
        adapter.getItem(RelatorioAdapter.MEDIA_REDIMENTO_TOTAL).setData(ReportResult.formatFloat(report.rendimentoTotal)+" km por litro");

        adapter.getItem(RelatorioAdapter.PROXIMO_ABASTECIMENTO).setData(ReportResult.formatFloat(report.proximo)+" km");

        adapter.notifyDataSetChanged();
    }

    protected void setRelatorioInicial(boolean inicial){
        ((RelatorioTab) this.sectionsPagerAdapter.getItem(SectionsPagerAdapter.RELATORIO_TAB)).setInicial(inicial);
    }

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

    protected RelatorioAdapter getResumoRecyclerViewAdapter(){
        if(this.resumoRecyclerView == null) return null;
        return (RelatorioAdapter) this.resumoRecyclerView.getAdapter();
    }

    protected void requestVeiculo(boolean force){
        Intent requestIntent = new Intent("org.stein.edwino.fuelsheet.VeiculoActivity");
        requestIntent.putExtra("force", force);
        this.startActivityForResult(requestIntent, VeiculoActivity.VEICULO_REQUEST);
    }

    protected void readFromServer(Veiculo veiculo){

        if(veiculo == null) return;

        Intent requestIntent = new Intent("org.stein.edwino.fuelsheet.RequestActivity");

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
