package org.stein.edwino.fuelsheet.httprequest;

import android.text.TextUtils;
import android.util.Log;

import java.net.URLEncoder;

public class HttpRequest {
    protected String uri;

    protected HttpCallback handlerCallback;

    protected RequestTask task;

    public HttpRequest(){
        this.task = new RequestTask();
    }

    public HttpRequest(HttpCallback callback){
        this();
        this.handlerCallback = callback;
    }

    public void send(String uri){

        this.setUri(uri);

        this.handlerCallback.beforeSendCallback();

        this.task.execute(this);

        this.handlerCallback.afterSendCallback();
    }

    public HttpRequest setUri(String uri){

        String splited[] = uri.split("\\?");
        this.uri = splited[0];

        if(splited.length > 1){

            String params[] = splited[1].split("&");
            String param[];

            for(int i = 0; i < params.length; i++){
                param = params[i].split("=");

                params[i] = URLEncoder.encode(param[0]).replace("+", "%20");
                if(param.length > 1)
                    params[i] += "="+URLEncoder.encode(param[1]).replace("+", "%20");
            }

            this.uri += "?" + TextUtils.join("&", params);
        }

        return this;
    }

    public String getUri() {
        return uri;
    }
}
