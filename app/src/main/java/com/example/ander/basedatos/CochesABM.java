package com.example.ander.basedatos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
//prueba 213487

public class CochesABM extends AppCompatActivity {
    private Button btBuscar;
    private EditText etMatricula,etMarca,etPotencia;
    private Spinner spDni;
    private TextView tvTitulo;
    private  String opcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coches_abm);
        btBuscar=(Button)findViewById(R.id.btBuscar);
        etMatricula=(EditText)findViewById(R.id.etMatricula);
        etMarca=(EditText)findViewById(R.id.etMarca);
        etPotencia=(EditText)findViewById(R.id.etPotencia);
        spDni=(Spinner)findViewById(R.id.spDni);
        tvTitulo=(TextView)findViewById(R.id.tvTitulo);
        Bundle bundle= getIntent().getExtras();
        opcion= bundle.getString("opcion");
        Toast notificacion = Toast.makeText(this, opcion, Toast.LENGTH_SHORT);
        tvTitulo.setText(opcion.toUpperCase());

        if(opcion.equals("altas")){
            btBuscar.setVisibility(View.GONE);

        }
        if(opcion.equals("bajas")){
            etMarca.setEnabled(false);
            etPotencia.setEnabled(false);
            spDni.setEnabled(false);

        }
        if(opcion.equals("modificar")){
            etMarca.setEnabled(false);
            etPotencia.setEnabled(false);
            spDni.setEnabled(false);
        }
        cargarSpinner();
    }


    public void cargarSpinner(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "seguros", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        List<String> listaDni = new ArrayList<>();
        Cursor fila = db.rawQuery("SELECT dni FROM propietarios", null);

        if(fila.getCount() <= 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, new String[]{""});
            spDni.setAdapter(adapter);
            return;
        }
        fila.moveToFirst();
        boolean primer = true;
        do {
            if(primer) {
                primer = false;
            } else {
                fila.moveToNext();
            }

            String dni = fila.getString(0);
            listaDni.add(dni);
        } while(!fila.isLast());
        fila.close();
        db.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, listaDni);
        spDni.setAdapter(adapter);


    }
    public void guardar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "seguros", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        if (opcion.equals("altas")) {
            String matricula = etMatricula.getText().toString();
            String marca = etMarca.getText().toString();
            String potencia = etPotencia.getText().toString();
            String dni = spDni.getSelectedItem().toString();
            Cursor fila = db.rawQuery("select marca,potencia,dni from coches where matricula='" + matricula + "'", null);
            if (fila.moveToFirst()) {
                Toast.makeText(this, "Ya esta en la tabla", Toast.LENGTH_LONG).show();
            } else {
                ContentValues registro = new ContentValues();
                registro.put("matricula", matricula);
                registro.put("marca", marca);
                registro.put("potencia", potencia);
                registro.put("dni", dni);
                db.insert("coches", null, registro);
                etMarca.setText("");
                etPotencia.setText("");
                etMatricula.setText("");
                Toast.makeText(this, "Se cargaron los datos del artículo", Toast.LENGTH_SHORT).show();
            }
        }
        if (opcion.equals("bajas")){
            String matricula = etMatricula.getText().toString();
            int seBorro=db.delete("coches","matricula="+matricula,null);
            etMatricula.setText("");
            etPotencia.setText("");
            etMarca.setText("");
            if (seBorro == 1){
                Toast.makeText(this,"se borro",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"no se borro",Toast.LENGTH_LONG).show();
            }
        }
        if (opcion.equals("modificar")) {
            String matricula = etMatricula.getText().toString();
            String marca = etMarca.getText().toString();
            String potencia = etPotencia.getText().toString();
            String dni = spDni.getSelectedItem().toString();
            ContentValues registro = new ContentValues();
            registro.put("marca", marca);
            registro.put("potencia", potencia);
            registro.put("dni", dni);
            int seModifico = db.update("coches", registro, "matricula='" + matricula + "'",null);
            if (seModifico == 1){
                Toast.makeText(this,"se modifico",Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(this,"no se modifico",Toast.LENGTH_LONG).show();
            }
        }
        db.close();
    }
    public void buscar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"seguros", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String matricula = etMatricula.getText().toString();
        Cursor fila = db.rawQuery("select marca,potencia,dni from coches where matricula='" + matricula + "'",null);
        if (fila.moveToFirst()){
            String marca = fila.getString(0);
            String potencia = fila.getString(1);
            String dni = fila.getString(2);
            etMarca.setText(marca);
            etPotencia.setText(potencia);
            for(int i=0; i<spDni.getCount(); i++) {
                if(spDni.getItemAtPosition(i).toString().equals(dni)) {
                    spDni.setSelection(i, true);
                    break;
                }
            }

            if (opcion.equals("modificar")){
                etPotencia.setEnabled(true);
                etMarca.setEnabled(true);
                spDni.setEnabled(true);
            }
        }else{
            Toast.makeText(this,"no existe",Toast.LENGTH_SHORT).show();

        }
        db.close();
    }
    public void volver (View v){

        finish();
    }
}

