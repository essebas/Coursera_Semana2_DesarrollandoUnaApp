package co.sebasdeveloper.appsolucionsemana2;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.renderscript.ScriptGroup;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener dateSetListener;
    DatePickerDialog.OnDismissListener dismissListener;
    DatePickerDialog datePickerDialog;

    TextInputLayout nameTextInputLayout;
    TextInputLayout dateTextInputLayout;
    TextInputLayout phoneTextInputLayout;
    TextInputLayout emailTextInputLayout;
    TextInputLayout descriptionTextInputLayout;

    TextInputEditText nameTextInputEditText;
    TextInputEditText dateTextInputEditText;
    TextInputEditText phoneTextInputEditText;
    TextInputEditText emailTextInputEditText;
    TextInputEditText descriptionTextInputEditText;

    Button btnNext;

    ArrayList<String>listDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNext = (Button) findViewById(R.id.btn_continue);

        nameTextInputLayout = (TextInputLayout) findViewById(R.id.txi_name);
        dateTextInputLayout = (TextInputLayout) findViewById(R.id.txi_date);
        phoneTextInputLayout = (TextInputLayout) findViewById(R.id.txi_tel);

        nameTextInputEditText = (TextInputEditText) findViewById(R.id.edTxt_name);
        dateTextInputEditText = (TextInputEditText) findViewById(R.id.edTxt_date);
        phoneTextInputEditText = (TextInputEditText) findViewById(R.id.edTxt_tel);
        emailTextInputEditText = (TextInputEditText) findViewById(R.id.edTxt_mail);
        descriptionTextInputEditText = (TextInputEditText) findViewById(R.id.edTxt_resume);


        dateTextInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocultarTeclado();
                showDatePicker(v);
            }
        });


        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar date = Calendar.getInstance();
                date.set(Calendar.YEAR, year);
                date.set(Calendar.MONTH, month);
                date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateTextInputEditText.setText(String.format("%s-%s-%d", ajustarFecha(dayOfMonth), ajustarFecha(month), year));
            }
        };

        dismissListener = new DatePickerDialog.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        };

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean required1 = verificarTextFilds(nameTextInputEditText, nameTextInputLayout, getResources().getString(R.string.txi_error_name));
                boolean required2 = verificarTextFilds(dateTextInputEditText, dateTextInputLayout, getResources().getString(R.string.txi_error_date));
                boolean required3 = verificarTextFilds(phoneTextInputEditText, phoneTextInputLayout, getResources().getString(R.string.txi_error_tel));

                if(!required1 & !required2 & !required3){
                    listDatos = new ArrayList<>();
                    listDatos.add(nameTextInputEditText.getText().toString());
                    listDatos.add(dateTextInputEditText.getText().toString());
                    listDatos.add(phoneTextInputEditText.getText().toString());
                    listDatos.add(emailTextInputEditText.getText().toString());
                    listDatos.add(descriptionTextInputEditText.getText().toString());

                    Intent i = new Intent(MainActivity.this, DataConfirm.class);
                    i.putExtra(getResources().getString(R.string.pDatosUsuario),listDatos);
                    startActivity(i);
                }
            }
        });

    }



    public void showDatePicker(View v){
        DatePickerDialog dialog = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            dialog = new DatePickerDialog(this);
            dialog.setOnDateSetListener(dateSetListener);
            dialog.setOnDismissListener(dismissListener);
            dialog.show();
        }else {
            Calendar calendar = Calendar.getInstance();
            int currentDayMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int currentMonth = calendar.get(Calendar.MONTH);
            int currentYear = calendar.get(Calendar.YEAR);
            datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    dateTextInputEditText.setText(String.format("%s - %s - %d", ajustarFecha(dayOfMonth), ajustarFecha(month), year));
                }
            }, currentYear, currentMonth, currentDayMonth);
            datePickerDialog.show();
        }
    }

    public void ocultarTeclado(){
        View view = MainActivity.this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    public String ajustarFecha(int fecha){
        String salida;
        if (fecha < 10){
            salida = "0"+fecha;
        }else {
            salida = ""+fecha;
        }
        return salida;
    }

    public boolean verificarTextFilds(TextInputEditText view, TextInputLayout view2, String texto){
        if (view.getText().toString().isEmpty()){
            view2.setError(texto);
            view2.setErrorEnabled(true);
            return true;
        }else{
            if(view2.isErrorEnabled()){
                view2.setErrorEnabled(false);
            }
            return false;
        }
    }



}
