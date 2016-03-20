package org.stein.edwino.fuelsheet.cardviews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.stein.edwino.fuelsheet.R;
import org.stein.edwino.fuelsheet.models.RelatorioItem;


public class RelatorioAdapter extends RecyclerView.Adapter<RelatorioViewHolder> {

    protected static final String[] FIELDS = {
        "Último abastecimento",

        "Maior valor gasto em um abastecimento",
        "Menor valor gasto em um abastecimento",
        "Total gasto em abastecimentos",
        "Média de gastos em abastecimentos",

        "Maior quantidade de combustível em um abastecimento",
        "Menor quantidade de combustível em um abastecimento",
        "Média de combustível em abastecimentos",

        "Maior preço do combustível",
        "Menor preço da combustível",
        "Média do preço da combustível",

        "Quilômetros rodados até o último abastecimento",
        "Quilômetros total rodados pelo veiculo",

        "Média de rendimento do veiculo",
        "Média de rendimento do veiculo do último abastecimento",
        "Previsão em quilômetros de duração do tanque atual"
    };

    public static final int ULTIMO_ABASTECIMENTO            = 0;
    public static final int MAIOR_VALOR_ABASTECIMENTO       = 1;
    public static final int MENOR_VALOR_ABASTECIMENTO       = 2;
    public static final int TOTAL_VALOR_ABASTECIMENTO       = 3;
    public static final int MEDIA_VALOR_ABSTECIMENTO        = 4;
    public static final int MAIOR_QUANTIDADE_COMBUSTIVEL    = 5;
    public static final int MENOR_QUANTIDADE_COMBUSTIVEL    = 6;
    public static final int MEDIA_QUANTIDADE_COMBUSTIVEL    = 7;
    public static final int MAIOR_PRECO                     = 8;
    public static final int MENOR_PRECO                     = 9;
    public static final int MEDIA_PRECO                     = 10;
    public static final int QUILOMETROS_RODADOS             = 11;
    public static final int QUILOMETROS_RODADOS_TOTAL       = 12;
    public static final int MEDIA_REDIMENTO_TOTAL           = 13;
    public static final int MEDIA_REDIMENTO_MES             = 14;
    public static final int PROXIMO_ABASTECIMENTO           = 15;


    protected RelatorioItem[] items;

    public RelatorioAdapter(RelatorioItem[] items){
        this.items = items;
    }

    @Override
    public RelatorioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.relatorio_item_cardview, parent, false);

        return new RelatorioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RelatorioViewHolder holder, int position) {

        holder.label.setText(this.items[position].getLabel());
        holder.data.setText(this.items[position].getData());
    }

    @Override
    public int getItemCount() {
        return this.items.length;
    }

    public RelatorioItem getItem(int position){
        return this.items[position];
    }

    public static RelatorioItem[] createTemplate(){

        RelatorioItem[] items = new RelatorioItem[FIELDS.length];

        for(int i = 0; i < FIELDS.length; i++)
            items[i] = new RelatorioItem(i, FIELDS[i]);

        return items;
    }
}
