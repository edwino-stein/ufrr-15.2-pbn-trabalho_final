package org.stein.edwino.fuelsheet.util;


import org.stein.edwino.fuelsheet.models.Abastecimento;

import java.util.Date;

public abstract class JavaReport {

    public static ReportResult calc(Abastecimento[] abastecimentosData){

        float valorTotal[] = new float[abastecimentosData.length];
        float litros[] = new float[abastecimentosData.length];
        float precoLitro[] = new float[abastecimentosData.length];
        float quilometragem[] = new float[abastecimentosData.length];

        ReportResult report = new ReportResult();

        for(int i = 0; i < abastecimentosData.length; i++){
            valorTotal[i] = abastecimentosData[i].getValorTotal();
            litros[i] = abastecimentosData[i].getLitros();
            precoLitro[i] = abastecimentosData[i].getPrecoLitro();
            quilometragem[i] = abastecimentosData[i].getQuilometragem();
        }

        report.ultimo = abastecimentosData[0].getData();

        report.maiorValor = JavaReport.getMaior(valorTotal);
        report.menorValor = JavaReport.getMenor(valorTotal);
        report.mediaValor = JavaReport.getMedia(valorTotal);
        report.totalValor = JavaReport.getTotal(valorTotal);

        report.maiorLitros = JavaReport.getMaior(litros);
        report.menorLitros = JavaReport.getMenor(litros);
        report.mediaLitros = JavaReport.getMedia(litros);

        report.maiorPreco = JavaReport.getMaior(precoLitro);
        report.menorPreco = JavaReport.getMenor(precoLitro);
        report.mediaPreco = JavaReport.getMedia(precoLitro);

        report.quilometrosTotal = JavaReport.getTotal(quilometragem);
        report.quilometrosMes = quilometragem[0];

        report.rendimentoMes = JavaReport.getRendimento(quilometragem[0], litros[0]);
        report.rendimentoTotal = JavaReport.getRendimentoTotal(quilometragem, litros);

        report.proximo = report.rendimentoTotal * litros[0];

        return report;
    }

    private static float getRendimento(float quilometros, float litros){
        return quilometros/litros;
    }

    private static float getRendimentoTotal(float quilometros[], float litros[]){

        float rendimentoTotal = 0;
        int count = 0;

        for (int i = 0; i < quilometros.length; i++){
            rendimentoTotal += JavaReport.getRendimento(quilometros[i], litros[i]);
            count++;
        }

        return rendimentoTotal/count;
    }

    private static float getMaior(float values[]){

        float max = values[0];

        for(int i = 0; i < values.length; i++){
            if(max < values[i]) max = values[i];
        }

        return max;
    }

    private static float getMenor(float values[]){

        float min = values[0];

        for(int i = 0; i < values.length; i++){
            if(min > values[i]) min = values[i];
        }

        return min;
    }

    private static float getTotal(float values[]){

        float total = 0;

        for(int i = 0; i < values.length; i++){
            total += values[i];
        }

        return total;
    }

    private static float getMedia(float values[]){

        float total = 0;
        int count = 0;

        for(int i = 0; i < values.length; i++){
            total += values[i];
            count++;
        }

        return total/count;
    }
}
