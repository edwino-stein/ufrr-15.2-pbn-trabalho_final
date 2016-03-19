package org.stein.edwino.fuelsheet.httprequest;

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
        this.uri = uri;

        this.handlerCallback.beforeSendCallback();

        this.task.execute(this);

        this.handlerCallback.afterSendCallback();
    }

    public String getUri() {
        return uri;
    }
}
