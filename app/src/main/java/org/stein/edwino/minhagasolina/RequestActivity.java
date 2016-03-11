package org.stein.edwino.minhagasolina;

import android.app.Activity;
import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.stein.edwino.minhagasolina.httprequest.HttpCallback;
import org.stein.edwino.minhagasolina.httprequest.HttpRequest;
import org.stein.edwino.minhagasolina.httprequest.HttpResponse;

public class RequestActivity extends Activity implements HttpCallback {

    public static final int READ_ABASTECIMENTOS = 1;

    public static final String BASE_URL = "http://192.168.2.4/webservice/index.php";
    public static final String ABASTECIMENTOS_READ_URI = "?controller=abastecimentos&action=read&veiculo={veiculo}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
    }

    @Override
    protected void onStart(){
        super.onStart();

        HttpRequest request = new HttpRequest(this);

        switch (this.getRequestCode()){

            case RequestActivity.READ_ABASTECIMENTOS:
                int veiculo = getIntent().getExtras().getInt("veiculo");
                request.send(this.getAbastecimentosReadUrl(veiculo));
            break;

            default:
                Log.e("Alerta", "Nenhuma action definida.");
                setResult(RESULT_CANCELED);
                finish();
        }

    }


    @Override
    public void beforeSendCallback() {
        setFinishOnTouchOutside(false);
    }

    @Override
    public void afterSendCallback() {}

    @Override
    public void errorCallback(HttpResponse response) {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void successCallback(HttpResponse response) {

        Log.d("requestStatusCode", String.valueOf(response.statusCode));
        Log.d("requestResponse", response.responseText);

        Intent data = new Intent();
        data.putExtra("response", response.responseText);
        data.putExtra("statusCode", response.statusCode);

        setResult(RESULT_OK, data);
        finish();
    }

    protected String getAbastecimentosReadUrl(int veiculo){
        String uri = ABASTECIMENTOS_READ_URI.replace("{veiculo}", String.valueOf(veiculo));
        return BASE_URL + uri;
    }

    protected int getRequestCode(){
        return getIntent().getExtras().getInt("requestCode", -1);
    }
}
