package org.stein.edwino.fuelsheet.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportResult {

    public long timeStamp;

    public Date ultimo;

    public float maiorValor;
    public float menorValor;
    public float totalValor;
    public float mediaValor;

    public float maiorLitros;
    public float menorLitros;
    public float mediaLitros;

    public float maiorPreco;
    public float menorPreco;
    public float mediaPreco;

    public float quilometrosMes;
    public float quilometrosTotal;

    public float rendimentoMes;
    public float rendimentoTotal;

    public float proximo;

    public static String formatData(Date data){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(data);
    }

    public static String formatFloat(float valor){
        return String.format("%.2f", valor).replace('.', ',');
    }
}
