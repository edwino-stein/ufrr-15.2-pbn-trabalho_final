#include <stddef.h>
#include <jni.h>
#include "header/NativeReport.h"

JNIEXPORT jfloat JNICALL
Java_org_stein_edwino_fuelsheet_util_NativeReport_getMaior(JNIEnv *env, jclass type, jfloatArray values_, jint size) {

    jfloat *values = (*env)->GetFloatArrayElements(env, values_, NULL);

    float max = getMaior(values, size);

    (*env)->ReleaseFloatArrayElements(env, values_, values, 0);

    return max;
}

JNIEXPORT jfloat JNICALL
Java_org_stein_edwino_fuelsheet_util_NativeReport_getMenor(JNIEnv *env, jclass type, jfloatArray values_, jint size) {

    jfloat *values = (*env)->GetFloatArrayElements(env, values_, NULL);

    float min = getMenor(values, size);

    (*env)->ReleaseFloatArrayElements(env, values_, values, 0);

    return min;
}

JNIEXPORT jfloat JNICALL
Java_org_stein_edwino_fuelsheet_util_NativeReport_getTotal(JNIEnv *env, jclass type, jfloatArray values_, jint size) {

    jfloat *values = (*env)->GetFloatArrayElements(env, values_, NULL);

    float total = getTotal(values, size);

    (*env)->ReleaseFloatArrayElements(env, values_, values, 0);

    return total;
}

JNIEXPORT jfloat JNICALL
Java_org_stein_edwino_fuelsheet_util_NativeReport_getMedia(JNIEnv *env, jclass type, jfloatArray values_, jint size) {

    jfloat *values = (*env)->GetFloatArrayElements(env, values_, NULL);

    float media = getMedia(values, size);

    (*env)->ReleaseFloatArrayElements(env, values_, values, 0);

    return media;
}

JNIEXPORT jfloat JNICALL
Java_org_stein_edwino_fuelsheet_util_NativeReport_getRendimento(JNIEnv *env, jclass type, jfloat quilometros, jfloat litros) {

    return getRendimento(quilometros, litros);
}

JNIEXPORT jfloat JNICALL
Java_org_stein_edwino_fuelsheet_util_NativeReport_getRendimentoTotal(JNIEnv *env, jclass type, jfloatArray quilometros_, jfloatArray litros_, jint size) {

    jfloat *quilometros = (*env)->GetFloatArrayElements(env, quilometros_, NULL);
    jfloat *litros = (*env)->GetFloatArrayElements(env, litros_, NULL);

    float rendimentoTotal = getRendimentoTotal(quilometros, litros, size);

    (*env)->ReleaseFloatArrayElements(env, quilometros_, quilometros, 0);
    (*env)->ReleaseFloatArrayElements(env, litros_, litros, 0);

    return rendimentoTotal;
}