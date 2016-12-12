package com.example.dam207.proyectoevaluacion1;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dam207 on 11/12/2015.
 */

public class ResultadoBusqueda extends AppCompatActivity {
    private APPLogin appl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultadosbusqueda);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String nuevoTitulo = "Resultado de la búsqueda";
        setTitle(nuevoTitulo);

        /*Recuperar el código SQL de la consulta construida en Busqueda*/
        Intent intent = getIntent();
        String consulta=intent.getExtras().getString(Busqueda.CONSULTA);
        this.appl = new APPLogin(getApplicationContext());
        SQLiteDatabase sqlLiteDB = appl.getWritableDatabase();

        Cursor cursor = sqlLiteDB.rawQuery(consulta, null);


        ListView resultadoPokemon = (ListView) findViewById(R.id.listView);


        //Añadimos los datos al Adapter y le indicamos donde dibujar cada dato en la fila del Layout
        String[] desdeEstasColumnas = { "_id", "NombrePokemon" ,"Tipo1","Tipo2", "altura", "peso"};
        int[] aEstasViews = {R.id.Nº, R.id.rnombre, R.id.rtipo1, R.id.rtipo2, R.id.raltura, R.id.rpeso};


        BaseAdapter adapter = new ImageCursorAdapter(this, R.layout.listaresultado, cursor, desdeEstasColumnas, aEstasViews);

        resultadoPokemon.setAdapter(adapter);

        if(resultadoPokemon.getAdapter().getCount()<1){
            Toast.makeText(getApplicationContext(), "Ningún pokemon encontrado con esas características", Toast.LENGTH_LONG).show();
        }


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}

