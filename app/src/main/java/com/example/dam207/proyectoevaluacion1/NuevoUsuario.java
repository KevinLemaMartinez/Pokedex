package com.example.dam207.proyectoevaluacion1;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class NuevoUsuario extends AppCompatActivity {

    private int año, mes, dia;
    private static final int TIPO_DIALOGO =0;
    private APPLogin appv;
    private static DatePickerDialog.OnDateSetListener oyenteSelectorFecha;
    private TextView fech_nac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String nuevoTitulo = getResources().getText(R.string.app_name).toString();
        setTitle(nuevoTitulo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        xestionarEventos();
        calendario();

    }

    public void calendario(){
        fech_nac= (TextView) findViewById(R.id.fech_nac);
        Calendar calendario= Calendar.getInstance();
        año=calendario.get(Calendar.YEAR);
        mes=calendario.get(Calendar.MONTH);
        dia=calendario.get(Calendar.DAY_OF_MONTH);
        mostrarFecha();
        oyenteSelectorFecha= new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                año=year;
                mes=monthOfYear;
                dia=dayOfMonth;
                mostrarFecha();
            }

        };
    }

    public void mostrarFecha (){
        fech_nac.setText(año+"/"+(mes+1)+"/"+dia);
    }

    @Override
    protected Dialog onCreateDialog(int id){
        switch (id){
            case 0:
                return new DatePickerDialog(this, oyenteSelectorFecha, 1990, 1, 1);
        }
        return null;
    }

    public void mostrarCalendario(View control){
        showDialog(TIPO_DIALOGO);
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

    void xestionarEventos() {
        Button btnNuevoUsuario = (Button) findViewById(R.id.registrar);

        btnNuevoUsuario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                crearUsuario();
            }
        });
    }


    void crearUsuario() {
        boolean validacion = true;

        CheckBox terminos=(CheckBox)findViewById(R.id.checkBox);
        String nombre = ((EditText) findViewById(R.id.nombre)).getText().toString().trim();
        String login = ((EditText) findViewById(R.id.login)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.password)).getText().toString().trim();
        String email = ((EditText) findViewById(R.id.email)).getText().toString().trim();
        String direccion = ((EditText) findViewById(R.id.direccion)).getText().toString().trim();
        String fech_nac = ((TextView) findViewById(R.id.fech_nac)).getText().toString().trim();
        String sexo = ((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString();

     if (nombre.isEmpty()){
          validacion=false;
         Toast.makeText(getApplicationContext(), "Nombre incorrecto ", Toast.LENGTH_LONG).show();
     }
        if(login.isEmpty()||login.length()<3 || login.length()>10){
            validacion=false;
            Toast.makeText(getApplicationContext(), "El usuario debe de contener entre 3 y 10 caracteres ", Toast.LENGTH_LONG).show();

        }

        if(password.isEmpty()||!password.matches(".*[abcdfghijklmnñopqrstvwxyz].*")||!password.matches(".*[0123456789].*")){
            validacion=false;
            Toast.makeText(getApplicationContext(), "Contraseña no válida, debe contener letras y numeros y no ser inferior a 4 caracteres", Toast.LENGTH_LONG).show();
        }

        if(!terminos.isChecked()){
            validacion=false;
            Toast.makeText(getApplicationContext(), "Debe aceptar los términos y condiciones", Toast.LENGTH_LONG).show();
        }

        if (validacion) {
            this.appv = new APPLogin(getApplicationContext());
            SQLiteDatabase sqlLiteDB = appv.getWritableDatabase();
            String consulta = "INSERT INTO usuario (nombre ,login, password, email, direccion, fecha_nac, sexo) " +
                    "VALUES ('" + nombre + "','" + login + "','" + password + "','" + email + "','" + direccion + "','" +
                    fech_nac + "','" + sexo + "')";
            Log.d("DEPURACIÓN", "Consulta: " + consulta);
            sqlLiteDB.execSQL(consulta);
            Toast.makeText(getApplicationContext(), "Nuevo usuario registrado.", Toast.LENGTH_LONG).show();

            //Comprobación de la lista de usuarios
            String usuario = "";
            Cursor cursor = sqlLiteDB.rawQuery("select * from usuario", null);
            if (cursor.moveToFirst()) {                // Se non ten datos xa non entra
                while (!cursor.isAfterLast()) {     // Quédase no bucle ata que remata de percorrer o cursor. Fixarse que leva un ! (not) diante
                    usuario += " " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4)
                            + " " + cursor.getString(5) + " " + cursor.getString(6);
                    cursor.moveToNext();
                }
            }
            Log.d("DEPURACÍON", "Resultado inserción: " + usuario);

            //Ir a la ventana de inicio de sesión y finalizar la Activity.
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }





    }
}
