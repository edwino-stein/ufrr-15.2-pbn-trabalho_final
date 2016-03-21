package org.stein.edwino.fuelsheet;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AbastecimentoFormActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int CREATE_ABASTECIMENTO = 200;

    private EditText dateInput;
    private DatePickerDialog datePicker;

    private static final String DATE_FORMAT_1 = "dd/MM/yyyy";
    private static final String DATE_FORMAT_2 = "yyyy-MM-dd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abastecimento_form);

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
    }

    public void setDateInputData(Date date, String format){
        SimpleDateFormat formater = new SimpleDateFormat(format);
        dateInput.setText(formater.format(date));
    }

    public Date getDateInputValue(String format){
        return this.parseDate(this.dateInput.getText().toString(), format);
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
        }
    }

    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onCancel(View v){
        setResult(RESULT_CANCELED);
        finish();
    }
}
