package com.example.ander.basedatos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Consulta extends AppCompatActivity {
    private TextView tvTitulo,tvOpcion;
    private EditText etMatricula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_p);
        tvTitulo=(TextView)findViewById(R.id.tvTitulo);
        tvOpcion=(TextView)findViewById(R.id.tvOpcion);
        etMatricula=(EditText)findViewById(R.id.etMatricula);
        Bundle bundle = getIntent().getExtras();
        String opcion=bundle.getString("opcion");
        tvTitulo.setText("CONSULTA "+opcion.toUpperCase());
        if (opcion.equals("propietarios")){
            tvOpcion.setText("DNI:");
        }
        if (opcion.equals("coches")){
            tvOpcion.setText("Matricula:");
        }

    }
    public void volver (View v){

        finish();
    }
}

