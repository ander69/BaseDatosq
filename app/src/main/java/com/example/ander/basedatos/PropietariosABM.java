package com.example.ander.basedatos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class PropietariosABM extends AppCompatActivity {
    private Button btBuscar,btGuardar;
    private EditText etNombre,etDni,etEdad,etTelefono;
    private TextView tvTitulo;
    private String opcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propietarios_abm);
        tvTitulo=(TextView)findViewById(R.id.tvTitulo);
        btBuscar=(Button)findViewById(R.id.btBuscar);
        btGuardar=(Button)findViewById(R.id.btGuardar);
        etNombre=(EditText)findViewById(R.id.etNombre);
        etDni=(EditText)findViewById(R.id.etDni);
        etEdad=(EditText)findViewById(R.id.etEdad);
        etTelefono=(EditText)findViewById(R.id.etTelefono);
        Bundle bundle = getIntent().getExtras();
        opcion=bundle.getString("opcion");
        Toast notificacion= Toast.makeText(this,opcion,Toast.LENGTH_LONG);
        tvTitulo.setText(opcion.toUpperCase());

        if (opcion.equals("altas")){
            btBuscar.setVisibility(View.GONE);
        }
        if (opcion.equals("bajas")){
            etNombre.setEnabled(false);
            etEdad.setEnabled(false);
            etTelefono.setEnabled(false);
            btGuardar.setEnabled(false);
        }
        if(opcion.equals("modificar")){
            etNombre.setEnabled(false);
            etEdad.setEnabled(false);
            etTelefono.setEnabled(false);
            btGuardar.setEnabled(false);
        }

    }

    public void guardar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "seguros", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        if (opcion.equals("altas")){
            String nombre = etNombre.getText().toString();
            String dni = etDni.getText().toString();
            String edad = etEdad.getText().toString();
            String telefono= etTelefono.getText().toString();
            Cursor fila = db.rawQuery("select nombre,edad,telefono from propietarios where dni="+ dni,null);
            if (fila.moveToFirst()){
                Toast.makeText(this,"ya esta en la tabla",Toast.LENGTH_SHORT).show();
            }else{
                ContentValues registro = new ContentValues();
                registro.put("nombre", nombre);
                registro.put("dni", dni);
                registro.put("edad", edad);
                registro.put("telefono", telefono);
                db.insert("propietarios", null, registro);
                etNombre.setText("");
                etDni.setText("");
                etEdad.setText("");
                etTelefono.setText("");
                Toast.makeText(this,"Se cargaron los datos del artículo",Toast.LENGTH_LONG).show();
                }
        }
        if (opcion.equals("bajas")){
            String dni = etDni.getText().toString();
            int seBorro=db.delete("propietarios","dni="+dni,null);
            etDni.setText("");
            etTelefono.setText("");
            etEdad.setText("");
            etNombre.setText("");
            if (seBorro == 1)
                Toast.makeText(this,"se borro",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this,"no se borro",Toast.LENGTH_LONG).show();
        }
        if(opcion.equals("modificar")){
            String nombre = etNombre.getText().toString();
            String edad = etEdad.getText().toString();
            String telefono = etTelefono.getText().toString();
            String dni = etDni.getText().toString();
            ContentValues registro = new ContentValues();
            registro.put("nombre", nombre);
            registro.put("edad", edad);
            registro.put("telefono", telefono);
            int seModifico = db.update("propietarios", registro, "dni="+dni,null);
            if (seModifico == 1)
                Toast.makeText(this,"se modifico",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this,"no se modifico",Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    public void buscar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"seguros", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String dni = etDni.getText().toString();
        Cursor fila = db.rawQuery("select nombre,edad,telefono from propietarios where dni="+ dni,null);
        if (fila.moveToFirst()){
            etNombre.setText(fila.getString(0));
            etEdad.setText(fila.getString(1));
            etTelefono.setText(fila.getString(2));
            if (opcion.equals("modificar")) {
                etNombre.setEnabled(true);
                etEdad.setEnabled(true);
                etTelefono.setEnabled(true);
                btGuardar.setEnabled(true);
            }else if(opcion.equals("bajas")){
                btGuardar.setEnabled(true);
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
