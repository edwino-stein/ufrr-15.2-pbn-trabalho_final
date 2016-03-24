package org.stein.edwino.fuelsheet;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import org.stein.edwino.fuelsheet.util.NativeReport;

public class AboutActivity extends AppCompatActivity {

    protected Switch nativeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView archDisplay = (TextView) findViewById(R.id.archDisplay);
        TextView reportOptimized = (TextView) findViewById(R.id.reportOptimized);

        archDisplay.setText(NativeReport.getArch());
        reportOptimized.setText(NativeReport.isOptimized() ? "Sim" : "Não");

        this.nativeSwitch = (Switch) findViewById(R.id.nativeSwitch);
        this.nativeSwitch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onnNativeSwitchChange();
            }
        });


        SharedPreferences settings = getSharedPreferences("FluelSheet", 0);
        boolean useNative = settings.getBoolean("useNative", false);
        this.nativeSwitch.setChecked(useNative);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onnNativeSwitchChange(){
        boolean checked = this.nativeSwitch.isChecked();
        SharedPreferences.Editor editor = getSharedPreferences("FluelSheet", 0).edit();

        editor.putBoolean("useNative", checked);
        editor.commit();
    }
}
