package com.example.dam207.proyectoevaluacion1;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

/**
 * Created by dam207 on 24/11/2015.
 */

public class Busqueda extends AppCompatActivity {
    RadioButton pesorb1, pesorb2, pesorb3, alturarb1,alturarb2,alturarb3;
    CheckBox filtrarpeso, filtraraltura;
    Button buscarpokemon;
    String tipo1, tipo2, rbPeso, rbAltura;

    RadioGroup radioGroupPeso, radioGroupAltura;


    public final static String CONSULTA = "consulta";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busqueda);
        Intent intent = getIntent();
        String nuevoTitulo=getResources().getText(R.string.app_name)
                +": "+intent.getExtras().getString(MainActivity.NOMBRE);
        //Cambio para depuración
        /*String nuevoTitulo = getResources().getText(R.string.app_name)
                + ": José López";*/

        setTitle(nuevoTitulo);
        xestionarEventos();
    }

    void xestionarEventos() {
        this.rbPeso="";
        this.rbAltura="";
        this.radioGroupPeso = (RadioGroup) findViewById(R.id.radioGroupPeso);
        this.radioGroupAltura = (RadioGroup) findViewById(R.id.radioGroupAltura);
        this.buscarpokemon = (Button) findViewById(R.id.buscarpokemon);
        this.filtraraltura = (CheckBox) findViewById(R.id.filtraraltura);
        this.filtrarpeso = (CheckBox) findViewById(R.id.filtrarpeso);

        this.buscarpokemon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                buscarPokemon();
            }
        });


        this.filtraraltura.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioGroupAltura.setVisibility(View.VISIBLE);
                } else {
                    radioGroupAltura.setVisibility(View.INVISIBLE);
                }
            }
        });

        this.filtrarpeso.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioGroupPeso.setVisibility(View.VISIBLE);
                } else {
                    radioGroupPeso.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    void buscarPokemon() {
        String nombre = ((EditText) findViewById(R.id.nombre)).getText().toString().trim();
        String numero = ((EditText) findViewById(R.id.numero)).getText().toString().trim();
        this.tipo1 = ((Spinner) findViewById(R.id.rtipo1)).getSelectedItem().toString();
        this.tipo2 = ((Spinner) findViewById(R.id.rtipo2)).getSelectedItem().toString();
        this.pesorb1 = (RadioButton) findViewById(R.id.pesorb1);
        this.pesorb2 = (RadioButton) findViewById(R.id.pesorb2);
        this.pesorb3 = (RadioButton) findViewById(R.id.pesorb3);
        this.alturarb1 = (RadioButton) findViewById(R.id.alturarb1);
        this.alturarb2 = (RadioButton) findViewById(R.id.alturarb2);
        this.alturarb3 = (RadioButton) findViewById(R.id.alturarb3);

        String query = "SELECT Nº AS _id, p.nombre AS NombrePokemon , t1.nombre AS Tipo1, t2.nombre AS Tipo2, " +
                "altura, peso from pokedex p inner join tipos t1 on p.tipo1=t1.codigo_tipo inner join tipos t2 on p.tipo2=t2.codigo_tipo" +
                " WHERE p.nombre LIKE '%" + nombre + "%' AND Nº LIKE '%" + numero + "%' ";

        if(this.pesorb1.isChecked()){
            rbPeso = "AND peso<10 ";
        }else{
            if(this.pesorb2.isChecked()){
                rbPeso = "AND peso BETWEEN 10 AND 50 ";
            }else{
                if(this.pesorb3.isChecked()){
                    rbPeso = "AND peso>50 ";
                }
            }
        }


        if(this.alturarb1.isChecked()){
            rbAltura = "AND altura<1 ";
        }else{
            if(this.alturarb2.isChecked()){
                rbAltura = "AND altura BETWEEN 1 AND 2 ";
            }else{
                if(this.alturarb3.isChecked()){
                    rbAltura = "AND altura>2 ";
                }
            }
        }


        if (filtraraltura.isChecked()) {
            query = query + rbAltura;
        }

        if (filtrarpeso.isChecked()) {
            query = query + rbPeso;
        }



            if (!this.tipo1.equalsIgnoreCase("N/A")) {
                query = query + "AND t1.nombre LIKE '%" + tipo1 + "%' ";
            }

            if (!this.tipo2.equalsIgnoreCase("N/A")) {
                query = query + "AND t2.nombre LIKE '%" + tipo2 + "%' ";
            }

            Log.d("DEPURACIÓN", "Consulta: " + query);
            //Ir a la Activity Resultado de la búsqueda. No se finaliza BusquedaVuelos.
            Intent intent = new Intent(this, ResultadoBusqueda.class);
        intent.putExtra(this.CONSULTA, query);


        startActivity(intent);

        }

    @Override
             public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case R.id.logout:
                //Ir a la ventana de inicio de sesión y finalizar la Activity.
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_busqueda, menu);
        return true;
    }
}

