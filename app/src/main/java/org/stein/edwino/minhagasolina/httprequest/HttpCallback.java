package org.stein.edwino.minhagasolina.httprequest;


public interface HttpCallback {

    void beforeSendCallback();

    void afterSendCallback();

    void errorCallback(HttpResponse response);

    void successCallback(HttpResponse response);

}
