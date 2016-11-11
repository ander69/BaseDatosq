package com.example.ander.basedatos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {
    private RadioButton rbPropietarios,rbCoches;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rbPropietarios=(RadioButton)findViewById(R.id.rbPropietarios);
        rbCoches=(RadioButton)findViewById(R.id.rbCoches);

    }

    public void Altas (View v){
        if(rbPropietarios.isChecked()){
            Intent i=new Intent(this,PropietariosABM.class);
            i.putExtra("opcion","altas");
            startActivity(i);
        }
        if(rbCoches.isChecked()){
            Intent i=new Intent(this,CochesABM.class);
            i.putExtra("opcion","altas");
            startActivity(i);
        }

    }
    public void Bajas (View v){
        if(rbPropietarios.isChecked()){
            Intent i=new Intent(this,PropietariosABM.class);
            i.putExtra("opcion","bajas");
            startActivity(i);

        }
        if (rbCoches.isChecked()){
            Intent i=new Intent(this,CochesABM.class);
            i.putExtra("opcion","bajas");
            startActivity(i);

        }
    }
    public void Modificar(View v){
        if(rbPropietarios.isChecked()){
            Intent i=new Intent(this,PropietariosABM.class);
            i.putExtra("opcion","modificar");
            startActivity(i);

        }
        if (rbCoches.isChecked()){
            Intent i=new Intent(this,CochesABM.class);
            i.putExtra("opcion","modificar");
            startActivity(i);

        }

    }
    public void Consultar(View v){
        if(rbPropietarios.isChecked()){
            Intent i=new Intent(this,Consulta.class);
            i.putExtra("opcion","propietarios");
            startActivity(i);

        }
        if (rbCoches.isChecked()){
            Intent i=new Intent(this,Consulta.class);
            i.putExtra("opcion","coches");
            startActivity(i);

        }

    }
}
