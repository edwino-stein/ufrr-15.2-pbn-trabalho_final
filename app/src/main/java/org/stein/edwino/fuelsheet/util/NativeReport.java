package org.stein.edwino.fuelsheet.util;

import org.stein.edwino.fuelsheet.models.Abastecimento;

public abstract class NativeReport {

    static {
        System.loadLibrary("fuelsheet");
    }

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

        report.maiorValor = getMaior(valorTotal, valorTotal.length);
        report.menorValor = getMenor(valorTotal, valorTotal.length);
        report.mediaValor = getMedia(valorTotal, valorTotal.length);
        report.totalValor = getTotal(valorTotal, valorTotal.length);

        report.maiorLitros = getMaior(litros, litros.length);
        report.menorLitros = getMenor(litros, litros.length);
        report.mediaLitros = getMedia(litros, litros.length);

        report.maiorPreco = getMaior(precoLitro, precoLitro.length);
        report.menorPreco = getMenor(precoLitro, precoLitro.length);
        report.mediaPreco = getMedia(precoLitro, precoLitro.length);

        report.quilometrosTotal = getTotal(quilometragem, quilometragem.length);
        report.quilometrosMes = quilometragem[0];

        report.rendimentoMes = getRendimento(quilometragem[0], litros[0]);
        report.rendimentoTotal = getRendimentoTotal(quilometragem, litros, quilometragem.length);

        report.proximo = report.rendimentoTotal * litros[0];

        return report;
    }

    private static native float getMaior(float values[], int size);

    private static native float getMenor(float values[], int size);

    private static native float getTotal(float values[], int size);

    private static native float getMedia(float values[], int size);

    private static native float getRendimento(float quilometros, float litros);

    private static native float getRendimentoTotal(float quilometros[], float litros[], int size);
}
