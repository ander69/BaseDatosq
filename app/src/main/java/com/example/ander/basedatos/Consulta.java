package com.example.ander.basedatos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Consulta extends AppCompatActivity {
    private TextView tvTitulo,tvOpcion;
    private EditText etMatricula;
    private ListView lvLista;
    private  String opcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_p);
        tvTitulo=(TextView)findViewById(R.id.tvTitulo);
        tvOpcion=(TextView)findViewById(R.id.tvOpcion);
        etMatricula=(EditText)findViewById(R.id.etMatricula);
        lvLista = (ListView) findViewById(R.id.lvLista);
        Bundle bundle = getIntent().getExtras();
        opcion=bundle.getString("opcion");
        tvTitulo.setText("CONSULTA "+opcion.toUpperCase());
        if (opcion.equals("propietarios")){
            tvOpcion.setText("DNI:");
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "seguros", null, 1);
            SQLiteDatabase db = admin.getWritableDatabase();
            List<String> arrayPropietarios = new ArrayList<>();
            Cursor fila = db.rawQuery("select nombre, edad, telefono, dni from propietarios", null);
            if(fila.moveToFirst()) {
                boolean primera = true;
                do {
                    if(!primera) {
                        fila.moveToNext();
                    }
                    String texto = "Nombre: "+fila.getString(0)+", edad: "+fila.getString(1)+", telefono: "+fila.getString(2)+", DNI: "+fila.getString(3);
                    arrayPropietarios.add(texto);

                    if(primera) {
                        primera = false;
                    }
                } while(!fila.isLast());
            }
            db.close();

            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayPropietarios);
            lvLista.setAdapter(adaptador);
        }
        if (opcion.equals("coches")){
            tvOpcion.setText("Matricula:");
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "seguros", null, 1);
            SQLiteDatabase db = admin.getWritableDatabase();
            List<String> arrayCoches = new ArrayList<>();
            Cursor fila = db.rawQuery("select matricula, marca, potencia, dni from coches", null);
            if(fila.moveToFirst()) {
                boolean primera = true;
                do {
                    if(!primera) {
                        fila.moveToNext();
                    }

                    String texto = "Matricula: "+fila.getString(0)+", Marca: "+fila.getString(1)+", Potencia: "+fila.getString(2)+", DNI: "+fila.getString(3);
                    arrayCoches.add(texto);

                    if(primera) {
                        primera = false;
                    }
                } while(!fila.isLast());
            }
            db.close();

            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayCoches);
            lvLista.setAdapter(adaptador);
        }

    }

    public void buscar(View v){

        if (opcion.equals("coches")){
            Toast.makeText(this,"aqui",Toast.LENGTH_SHORT).show();
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"seguros", null, 1);
            SQLiteDatabase db = admin.getWritableDatabase();
            String matricula = etMatricula.getText().toString();
            Cursor fila = db.rawQuery("select marca,potencia,dni from coches where matricula='" + matricula + "'",null);
            if (fila.moveToFirst()) {
                for (int i = 0; i < lvLista.getCount(); i++) {
                    if (lvLista.getItemAtPosition(i).toString().equals(matricula)) {
                        lvLista.setSelection(i);
                        break;
                    }
                }
            }
        }

    }
    public void volver (View v){

        finish();
    }
}

