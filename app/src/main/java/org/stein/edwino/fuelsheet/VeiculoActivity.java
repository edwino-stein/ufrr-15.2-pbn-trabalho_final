package org.stein.edwino.fuelsheet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class VeiculoActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int VEICULO_REQUEST = 100;

    private boolean isNovo;
    private boolean force;

    private Button cadastrarVeiculoBtn;
    private Button cancelarVeiculoBtn;

    private RadioButton novoRadio;
    private RadioButton resgatarRadio;

    private TextView descricaoLabel;
    private EditText descricaoInput;
    private TextView totalKmLabel;
    private EditText totalKmInput;

    private TextView codigoLabel;
    private EditText codigoInput;

    private View buttonGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiculo);

        this.force = getIntent().getBooleanExtra("force", false);

        novoRadio = (RadioButton) findViewById(R.id.novoRadio);
        novoRadio.setOnClickListener(this);
        resgatarRadio = (RadioButton) findViewById(R.id.resgatarRadio);
        resgatarRadio.setOnClickListener(this);

        cadastrarVeiculoBtn = (Button) findViewById(R.id.cadastrarVeiculoBtn);
        cadastrarVeiculoBtn.setOnClickListener(this);

        cancelarVeiculoBtn = (Button) findViewById(R.id.cancelarVeiculoBtn);
        cancelarVeiculoBtn.setOnClickListener(this);

        descricaoLabel = (TextView) findViewById(R.id.descricaoLabel);
        descricaoInput = (EditText) findViewById(R.id.descricaoInput);
        totalKmLabel = (TextView) findViewById(R.id.totalKmLabel);
        totalKmInput = (EditText) findViewById(R.id.totalKmInput);

        codigoLabel = (TextView) findViewById(R.id.codigoLabel);
        codigoInput = (EditText) findViewById(R.id.codigoInput);

        buttonGroup = findViewById(R.id.buttonGroup);

        if(this.force) setFinishOnTouchOutside(false);
        else cancelarVeiculoBtn.setEnabled(true);

        isNovo = true;
    }


    @Override
    public void onBackPressed() {
        if(!this.force) super.onBackPressed();
    }

    @Override
    public void onClick(View view) {

        if(view == this.resgatarRadio){
            this.resgatarRadio.setChecked(true);
            this.novoRadio.setChecked(false);
            this.setResgatarView();
            this.isNovo = false;
        }

        if(view == this.novoRadio){
            this.resgatarRadio.setChecked(false);
            this.novoRadio.setChecked(true);
            this.setNovoView();
            this.isNovo = true;
        }

        if(view == cadastrarVeiculoBtn){
            this.onCadastrar();
        }

        if(view == this.cadastrarVeiculoBtn){
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    protected void onCadastrar(){

        if(this.isNovo){

            String descricao = this.descricaoInput.getText().toString();
            if(descricao.length() <= 0) {
                Log.d("Veiculo", "O campo descricao não pode ser vazio.");
                return;
            }

            if(this.totalKmInput.getText().toString().length() <= 0){
                Log.d("Veiculo", "O campo quilimetragem não pode ser vazio.");
                return;
            }

            Float quilometragem = Float.parseFloat(this.totalKmInput.getText().toString());

            Intent data = new Intent();
            data.putExtra("isNovo", this.isNovo);
            data.putExtra("descricao", descricao);
            data.putExtra("quilometragem", quilometragem.floatValue());

            setResult(RESULT_OK, data);
            finish();
        }

        else {

            String codigo = this.codigoInput.getText().toString();
            if(codigo.length() <= 0) {
                Log.d("Veiculo", "O campo codigo não pode ser vazio.");
                return;
            }

            Intent data = new Intent();
            data.putExtra("isNovo", this.isNovo);
            data.putExtra("codigo", codigo);

            setResult(RESULT_OK, data);
            finish();
        }

    }

    protected void setNovoView(){
        descricaoLabel.setVisibility(View.VISIBLE);
        descricaoInput.setVisibility(View.VISIBLE);
        totalKmLabel.setVisibility(View.VISIBLE);
        totalKmInput.setVisibility(View.VISIBLE);

        codigoLabel.setVisibility(View.GONE);
        codigoInput.setVisibility(View.GONE);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) buttonGroup.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, R.id.totalKmInput);

    }

    protected void setResgatarView(){
        descricaoLabel.setVisibility(View.GONE);
        descricaoInput.setVisibility(View.GONE);
        totalKmLabel.setVisibility(View.GONE);
        totalKmInput.setVisibility(View.GONE);

        codigoLabel.setVisibility(View.VISIBLE);
        codigoInput.setVisibility(View.VISIBLE);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) buttonGroup.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, R.id.codigoInput);
    }
}
