package org.stein.edwino.fuelsheet;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.stein.edwino.fuelsheet.exceptions.FormException;
import org.stein.edwino.fuelsheet.models.Abastecimento;
import org.stein.edwino.fuelsheet.util.JsonParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AbastecimentoFormActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int CREATE_ABASTECIMENTO = 200;
    public static final int UPDATE_ABASTECIMENTO = 201;

    private Abastecimento model;
    private int veiculoId;

    private EditText dateInput;
    private DatePickerDialog datePicker;

    private EditText kmTotalInput;
    private EditText preceFuelInput;
    private EditText amountFuelInput;
    private EditText totalPaymentInput;

    private Button submit;

    private static final String DATE_FORMAT_1 = "dd/MM/yyyy";
    private static final String DATE_FORMAT_2 = "yyyy-MM-dd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abastecimento_form);

        this.model = this.getRequestCode() == UPDATE_ABASTECIMENTO ?
                     (Abastecimento) getIntent().getSerializableExtra("model") : null;

        this.veiculoId = getIntent().getIntExtra("veiculo", 0);

        this.dateInput = (EditText) findViewById(R.id.dateInput);
        this.dateInput.setOnClickListener(this);
        this.dateInput.setFocusable(false);
        this.dateInput.setClickable(false);

        Calendar calendar = Calendar.getInstance();
        this.setDateInputData(calendar.getTime(), this.DATE_FORMAT_1);
        this.datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Date date = new Date(year - 1900, month, day);
                SimpleDateFormat formater = new SimpleDateFormat(AbastecimentoFormActivity.DATE_FORMAT_1);
                dateInput.setText(formater.format(date));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        this.kmTotalInput = (EditText) findViewById(R.id.kmTotalInput);
        this.preceFuelInput = (EditText) findViewById(R.id.preceFuelInput);
        this.amountFuelInput = (EditText) findViewById(R.id.amountFuelInput);
        this.totalPaymentInput = (EditText) findViewById(R.id.totalPaymentInput);

        this.submit = (Button) findViewById(R.id.submit);
        this.submit.setOnClickListener(this);
    }


    public void onStart(){
        super.onStart();

        if(this.getRequestCode() == UPDATE_ABASTECIMENTO){
            Log.d("Update Data", this.model.toString());

            this.setDateInputData(this.model.getData(), DATE_FORMAT_1);
            this.kmTotalInput.setText(String.valueOf(this.model.getQuilometragem()));
            this.preceFuelInput.setText(String.valueOf(this.model.getPrecoLitro()));
            this.amountFuelInput.setText(String.valueOf(this.model.getLitros()));
            this.totalPaymentInput.setText(String.valueOf(this.model.getValorTotal()));
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("IntentResult", "requestCode: "+String.valueOf(requestCode) + ", IntentResultCode: " + String.valueOf(resultCode));

        switch (requestCode) {

            case RequestActivity.CREATE_OR_UPDATE_ABASTECIMENTO:

                if(resultCode != RESULT_OK) {
                    this.showDialogErro(
                        getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error)
                    );
                    return;
                }

                String response =  data.getStringExtra("response");
                JsonParser jsonResponse = new JsonParser(response);

                if(!jsonResponse.isSuccess() || jsonResponse.hasError()){
                    this.showDialogErro(
                        getResources().getString(R.string.server_query_error_title),
                        !jsonResponse.hasError() ?
                            jsonResponse.getResponseMessage() :
                            getResources().getString(R.string.server_internal_error)
                    );
                    return;
                }

                Abastecimento model = Abastecimento.parseJson(jsonResponse.getOneData());
                Intent intentResult = new Intent();
                intentResult.putExtra("model", model);

                setResult(RESULT_OK, intentResult);
                finish();

            break;

        }
    }

    public void setDateInputData(Date date, String format){
        SimpleDateFormat formater = new SimpleDateFormat(format);
        dateInput.setText(formater.format(date));
    }

    public Date getDateInputValue(String format){
        return this.parseDate(this.dateInput.getText().toString(), format);
    }

    public float getKmTotalInputValue() throws FormException{

        String sValue = this.kmTotalInput.getText().toString();
        float fValue;

        if(sValue.length() <= 0){
            throw new FormException("O campo \"Quilômetros rodados com o último tanque\" deve ser preenchido.");
        }

        fValue = Float.valueOf(sValue);

        if(fValue <= 0){
            throw new FormException("O campo \"Quilômetros rodados com o último tanque\" deve ser preenchido.");
        }

        return fValue;
    }

    public float getPreceFuelInputValue() throws FormException{

        String sValue = this.preceFuelInput.getText().toString();
        float fValue;

        if(sValue.length() <= 0){
            throw new FormException("O campo \"Preço do combustível\" deve ser preenchido.");
        }

        fValue = Float.valueOf(sValue);

        if(fValue <= 0){
            throw new FormException("O campo \"Preço do combustível\" deve ser preenchido.");
        }

        return fValue;
    }

    public float getAmountFuelInputValue() throws FormException{

        String sValue = this.amountFuelInput.getText().toString();
        float fValue;

        if(sValue.length() <= 0){
            throw new FormException("O campo \"Quantidade (litros) de combustível abastecidos\" deve ser preenchido.");
        }

        fValue = Float.valueOf(sValue);

        if(fValue <= 0){
            throw new FormException("O campo \"Quantidade (litros) de combustível abastecidos\" deve ser preenchido.");
        }

        return fValue;
    }

    public float getTotalPaymentInputValue() throws FormException{

        String sValue = this.totalPaymentInput.getText().toString();
        float fValue;

        if(sValue.length() <= 0){
            throw new FormException("O campo \"Valor total pago\" deve ser preenchido.");
        }

        fValue = Float.valueOf(sValue);

        if(fValue <= 0){
            throw new FormException("O campo \"Valor total pago\" deve ser preenchido.");
        }

        return fValue;
    }

    public static Date parseDate(String dateString, String format){

        SimpleDateFormat formater = new SimpleDateFormat(format);
        Date date = null;

        try {
            date = formater.parse(dateString);
        } catch (ParseException e) {
            return null;
        }

        return date;
    }

    @Override
    public void onClick(View view) {

        if (view == this.dateInput) {
            Date date = this.getDateInputValue(this.DATE_FORMAT_1);
            if(date == null) date = Calendar.getInstance().getTime();
            this.datePicker.updateDate(date.getYear() + 1900, date.getMonth(), date.getDate());
            this.datePicker.show();
            return;
        }

        if(view == this.submit){
            this.onSubmit();
            return;
        }
    }

    protected void onSubmit(){

        Abastecimento model = new Abastecimento();

        model.setId(this.getRequestCode() == UPDATE_ABASTECIMENTO ? this.model.getId() : 0);
        model.setData(this.getDateInputValue(this.DATE_FORMAT_1));
        model.setVeiculo(this.veiculoId);

        try {
            model.setQuilometragem(this.getKmTotalInputValue());
            model.setPrecoLitro(this.getPreceFuelInputValue());
            model.setLitros(this.getAmountFuelInputValue());
            model.setValorTotal(this.getTotalPaymentInputValue());
        } catch (FormException e) {
            Log.d("FormErro", e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        Log.d("Form", "Mandar dados: " + model.toString());
        Intent requestIntent = new Intent("org.stein.edwino.fuelsheet.RequestActivity");
        requestIntent.putExtra("model", model);
        startActivityForResult(requestIntent, RequestActivity.CREATE_OR_UPDATE_ABASTECIMENTO);

    }

    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onCancel(View v){
        setResult(RESULT_CANCELED);
        finish();
    }

    public void startActivityForResult(Intent intent, int requestCode){
        intent.putExtra("requestCode", requestCode);
        super.startActivityForResult(intent, requestCode);
    }

    protected int getRequestCode(){
        return getIntent().getExtras().getInt("requestCode", -1);
    }

    protected void showDialogErro(String title, String msg){
        this.showDialogErro(title, msg, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
    }

    protected void showDialogErro(String title, String msg, DialogInterface.OnClickListener onOk){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(title)
              .setMessage(msg)
              .setPositiveButton("OK", onOk)
              .show();
    }
}
