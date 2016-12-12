package com.example.dam207.proyectoevaluacion1;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



public class MainActivity extends AppCompatActivity {
    private APPLogin appv;
    public final static String NOMBRE = "nombre";
    public final static String ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        copiarBD();
        xestionarEventos();
    }

    private void copiarBD() {
        String bddestino = "/data/data/" + getPackageName() + "/databases/"
                + APPLogin.NOME_BD;
        File file = new File(bddestino);
        Log.d("DEPURACIÓN", "Ruta archivo BD: " + bddestino);
        if (file.exists()) {
            Toast.makeText(getApplicationContext(), "A BD NON SE VAI COPIAR. XA EXISTE", Toast.LENGTH_LONG).show();
            return; // XA EXISTE A BASE DE DATOS
        }

        String pathbd = "/data/data/" + getPackageName()
                + "/databases/";
        File filepathdb = new File(pathbd);
        filepathdb.mkdirs();

        InputStream inputstream;
        try {
            inputstream = getAssets().open(APPLogin.NOME_BD);
            OutputStream outputstream = new FileOutputStream(bddestino);

            int tamread;
            byte[] buffer = new byte[2048];

            while ((tamread = inputstream.read(buffer)) > 0) {
                outputstream.write(buffer, 0, tamread);
            }

            inputstream.close();
            outputstream.flush();
            outputstream.close();
            Toast.makeText(getApplicationContext(), "BASE DE DATOS COPIADA", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void xestionarEventos() {

                Button btnAbrirBD = (Button) findViewById(R.id.login);
                btnAbrirBD.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        iniciarSesion();
                    }
                });

                Button btnNuevoUsuario = (Button) findViewById(R.id.new_user);
                btnNuevoUsuario.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        nuevoUsuario();
                    }
                });



            }

    void iniciarSesion(){
        String login=((EditText) findViewById(R.id.usuario)).getText().toString();
        String password=((EditText) findViewById(R.id.pass)).getText().toString();
        this.appv = new APPLogin(getApplicationContext());
        SQLiteDatabase sqlLiteDB = appv.getWritableDatabase();
        String consulta= "SELECT * FROM usuario WHERE login='"+login
                +"' AND password='"+password+"'";
        Log.d("DEPURACIÓN", "Consulta: " + consulta);
        Cursor cursor = sqlLiteDB.rawQuery(consulta, null);
        Log.d("DEPURACIÓN", "Nº filas: " + cursor.getCount());
        if (cursor.moveToFirst()){
            Toast.makeText(getApplicationContext(), "Iniciando sesión.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Busqueda.class);
            intent.putExtra(this.NOMBRE,cursor.getString(0));
            intent.putExtra(this.ID, cursor.getString(3));
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "Error de autentificación.", Toast.LENGTH_LONG).show();
        }



    }

    void nuevoUsuario(){
        Toast.makeText(getApplicationContext(), "Formulario de registro de nuevo usuario.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, NuevoUsuario.class);
        startActivity(intent);
        // No se finaliza la Activity en este caso.
    }
}

