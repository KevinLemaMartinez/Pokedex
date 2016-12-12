package com.example.dam207.proyectoevaluacion1;

/**
 * Created by kevin on 13/12/2015.
 */
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ImageCursorAdapter extends SimpleCursorAdapter {

    private Cursor c;
    private Context context;

    int[] imagearray = {R.drawable.bulbasur, R.drawable.ivysaur, R.drawable.venusaur, R.drawable.charmander,
        R.drawable.charmeleon, R.drawable.charizard, R.drawable.squirtle, R.drawable.wartortle, R.drawable.blastoise, R.drawable.caterpie,
    R.drawable.metapod, R.drawable.butterfree, R.drawable.weedle, R.drawable.kakuna, R.drawable.beedrill, R.drawable.pidgey
            , R.drawable.pidgeotto, R.drawable.pidgeot, R.drawable.rattata, R.drawable.raticate
            , R.drawable.spearow, R.drawable.fearow, R.drawable.ekans, R.drawable.arbok, R.drawable.pikachu
            , R.drawable.raichu, R.drawable.sandshrew, R.drawable.sandslash, R.drawable.nidoranf, R.drawable.nidorino
            , R.drawable.nidoqueen, R.drawable.nidoranm, R.drawable.nidorino, R.drawable.nidoking, R.drawable.clefairy
            , R.drawable.clefable, R.drawable.vulpix, R.drawable.ninetales, R.drawable.jigglypuff, R.drawable.wigglytuff};

    public ImageCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.c = c;
        this.context = context;
    }

    public View getView(int pos, View inView, ViewGroup parent) {
        View v = inView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listaresultado, null);
        }
        this.c.moveToPosition(pos);
        String Nº = this.c.getString(this.c.getColumnIndex("_id"));
        String nombre = this.c.getString(this.c.getColumnIndex("NombrePokemon"));
        String tipo1 = this.c.getString(this.c.getColumnIndex("Tipo1"));
        String tipo2 = this.c.getString(this.c.getColumnIndex("Tipo2"));
        String altura = this.c.getString(this.c.getColumnIndex("altura"));
        String peso = this.c.getString(this.c.getColumnIndex("peso"));

        ImageView iv = (ImageView) v.findViewById(R.id.pokemon);

        iv.setImageResource(imagearray[Integer.parseInt(Nº)-1]);

        TextView numero = (TextView) v.findViewById(R.id.Nº);
        numero.setText(Nº);

        TextView tnombre = (TextView) v.findViewById(R.id.rnombre);
        tnombre.setText(nombre);

        TextView raltura = (TextView) v.findViewById(R.id.raltura);
        raltura.setText(altura+"m");

        TextView rpeso = (TextView) v.findViewById(R.id.rpeso);
        rpeso.setText(peso+"kg");

        TextView rtipo1 = (TextView) v.findViewById(R.id.rtipo1);
        rtipo1.setText(tipo1);

        TextView rtipo2 = (TextView) v.findViewById(R.id.rtipo2);
        rtipo2.setText(tipo2);
        return (v);

         }
    }


