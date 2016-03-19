package org.stein.edwino.fuelsheet.httprequest;

import org.apache.http.HttpStatus;

public class HttpResponse {

    public int statusCode = 0;
    public String responseText;
    public org.apache.http.HttpResponse originalResponse;

    public boolean requestResultOk(){
        return this.statusCode == HttpStatus.SC_OK;
    }

}
