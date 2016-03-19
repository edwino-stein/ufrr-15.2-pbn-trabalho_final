package org.stein.edwino.fuelsheet.httprequest;


public interface HttpCallback {

    void beforeSendCallback();

    void afterSendCallback();

    void errorCallback(HttpResponse response);

    void successCallback(HttpResponse response);

}
