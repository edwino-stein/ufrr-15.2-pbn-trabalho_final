package org.stein.edwino.fuelsheet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.stein.edwino.fuelsheet.httprequest.HttpCallback;
import org.stein.edwino.fuelsheet.httprequest.HttpRequest;
import org.stein.edwino.fuelsheet.httprequest.HttpResponse;

public class RequestActivity extends Activity implements HttpCallback {

    public static final int READ_ABASTECIMENTOS = 1;
    public static final int CREATE_VEICULO = 2;
    public static final int READ_VEICULO = 3;

    public static final String BASE_URL = "http://192.168.2.4/webservice/index.php";
    public static final String ABASTECIMENTOS_READ_URI = "?controller=abastecimentos&action=read&veiculo={veiculo}";
    public static final String VEICULO_CREATE_URI = "?controller=veiculos&action=create&descricao={descricao}&quilometragem={quilometragem}";
    public static final String VEICULO_READ_URI = "?controller=veiculos&action=read&id={veiculo}";

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

            case CREATE_VEICULO:
                request.send(this.getVeiculoCreateUriUrl(
                    getIntent().getExtras().getString("descricao"),
                    getIntent().getExtras().getFloat("quilometragem")
                ));
            break;

            case READ_VEICULO:
                request.send(this.getVeiculoReadUri(
                    getIntent().getExtras().getString("codigo")
                ));
            break;

            default:
                Log.e("Alerta", "Nenhuma action definida.");
                setResult(RESULT_CANCELED);
                finish();
        }

    }


    @Override
    public void onBackPressed() {}

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

    protected String getVeiculoReadUri(String veiculo){
        String uri = VEICULO_READ_URI.replace("{veiculo}", String.valueOf(veiculo));
        return BASE_URL + uri;
    }

    protected String getVeiculoCreateUriUrl(String descricao, float quilometragem){
        String uri = VEICULO_CREATE_URI.replace("{descricao}", descricao)
                                       .replace("{quilometragem}", String.valueOf(quilometragem));
        return BASE_URL + uri;
    }

    protected int getRequestCode(){
        return getIntent().getExtras().getInt("requestCode", -1);
    }
}
