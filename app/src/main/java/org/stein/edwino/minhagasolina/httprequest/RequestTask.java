package org.stein.edwino.minhagasolina.httprequest;

import android.os.AsyncTask;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RequestTask extends AsyncTask<HttpRequest, HttpResponse, HttpResponse> {

    private HttpRequest request;

    @Override
    protected HttpResponse doInBackground(HttpRequest... httpRequests) {

        this.request = httpRequests[0];

        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = new HttpResponse();

        try {

            response.originalResponse = httpclient.execute(new HttpGet(this.request.getUri()));

            response.statusCode = response.originalResponse.getStatusLine().getStatusCode();

            if(response.requestResultOk()){

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.originalResponse.getEntity().writeTo(out);
                out.close();
                response.responseText = out.toString();

            } else{
                response.originalResponse.getEntity().getContent().close();
                throw new IOException(response.originalResponse.getStatusLine().getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(HttpResponse response) {
        super.onPostExecute(response);

        if(response.requestResultOk()){
            this.request.handlerCallback.successCallback(response);
        }
        else{
            this.request.handlerCallback.errorCallback(response);
        }
    }
}