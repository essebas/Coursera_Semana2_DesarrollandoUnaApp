package co.sebasdeveloper.appsolucionsemana2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DataConfirm extends AppCompatActivity {

    TextView txvName;
    TextView txvDate;
    TextView txvTel;
    TextView txvMail;
    TextView txvDescription;

    ArrayList<String>datosUsuario;
    ArrayList<TextView>listTextViews;

    Button btn_edit;
    Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_confirm);
        listTextViews = new ArrayList<>();

        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_edit = (Button) findViewById(R.id.btn_edit);

        listTextViews.add(txvName = (TextView) findViewById(R.id.txv_secondText_name));
        listTextViews.add(txvDate = (TextView) findViewById(R.id.txv_secondText_date));
        listTextViews.add(txvTel = (TextView) findViewById(R.id.txv_secondText_tel));
        listTextViews.add(txvMail = (TextView) findViewById(R.id.txv_secondText_mail));
        listTextViews.add(txvDescription = (TextView) findViewById(R.id.txv_secondText_resume));

        Bundle params = getIntent().getExtras();
        datosUsuario = new ArrayList<>();
        datosUsuario.addAll(params.getStringArrayList(getResources().getString(R.string.pDatosUsuario)));

        for (int i=0;i<listTextViews.size();i++){
            if(datosUsuario.get(i).isEmpty()){
                listTextViews.get(i).setText(getResources().getString(R.string.txv_noData));
                listTextViews.get(i).setTextColor(getResources().getColor(R.color.colorDivider));
            }else {
                listTextViews.get(i).setText(datosUsuario.get(i));
            }
        }

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DataConfirm.this, "Continuaras...", Toast.LENGTH_SHORT).show();
            }
        });

    }

}

