#include "../header/NativeReport.h"

float getMaior(float *values, int size){

    float max = values[0];
    int i;

    for(i = 0; i < size; i++){
        if(max < values[i]) max = values[i];
    }

    return max;
}

float getMenor(float *values, int size){

    float min = values[0];
    int i;

    for(i = 0; i < size; i++){
        if(min > values[i]) min = values[i];
    }

    return min;
}

float getTotal(float *values, int size){

    float total = 0;
    int i;

    for(i = 0; i < size; i++){
        total += values[i];
    }

    return total;
}

float getMedia(float *values, int size){

    float total = 0;
    int i;

    for(i = 0; i < size; i++){
        total += values[i];
    }

    return total/i;
}

float getRendimento(float quilometros, float litros){
    return quilometros/litros;
}

float getRendimentoTotal(float *quilometros, float *litros, int size){

    float rendimentoTotal = 0;
    int i;

    for (i = 0; i < size; i++){
        rendimentoTotal += getRendimento(quilometros[i], litros[i]);
    }

    return rendimentoTotal/i;
}