package com.example.proyectomarvel;

import androidx.appcompat.app.AppCompatActivity;

import android.R.layout;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toast mensaje;
    private Spinner spiner;
    private ListView listview;
    private ArrayList<String> nombreH, nombreR, descripcion, foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nombreR = new ArrayList<String>();
        nombreH = new ArrayList<String>();
        descripcion = new ArrayList<String>();
        foto = new ArrayList<String>();
        spiner = findViewById(R.id.spinner);


        Tarea T = new Tarea();
        T.execute("http://huasteco.tiburcio.mx/marvel.php");

        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String fot =""+foto.get(position);

                try {
                    startActivity(new Intent( MainActivity.this, ResultadoActivity.class).putExtra("nombreH",nombreH.get(position)).putExtra("nombreR",nombreR.get(position))
                            .putExtra("descripcion", descripcion.get(position)).putExtra("foto",foto.get(position))  );
                }
                catch (Exception e)
                {
                    mensaje = Toast.makeText(getApplicationContext(), "Error "+ e, Toast.LENGTH_LONG);
                    mensaje.show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });

    }

    public class Tarea extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String salida = ConexionWeb(strings[0]);
            return salida;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONArray PMarvel = new JSONArray(s);
                for (int i =0;i<PMarvel.length();i++){
                    JSONObject ObjMarvel = PMarvel.getJSONObject(i);

                    String Personaje = ObjMarvel.getString("nombreH");
                    String NombreReal = ObjMarvel.getString("nombreR");
                    String Descripcion = ObjMarvel.getString("descripcion");
                    String url = ObjMarvel.getString("foto");

                    nombreH.add(Personaje);
                    nombreR.add(NombreReal);
                    descripcion.add(Descripcion);
                    foto.add(url);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, layout.simple_spinner_dropdown_item, nombreH);
                spiner.setAdapter(adapter);
            }
            catch (Exception e){
                mensaje = Toast.makeText(getApplicationContext(), "Error de lectura", Toast.LENGTH_LONG);
                mensaje.show();

            }
        }
    }

    String ConexionWeb(String direccion) {

        String pagina="";
        try {
            URL url = new URL(direccion);

            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();


            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conexion.getInputStream()));

                String linea = reader.readLine();

                while (linea != null) {
                    pagina += linea + "\n";
                    linea = reader.readLine();
                }
                reader.close();

            } else {
                pagina += "ERROR: " + conexion.getResponseMessage() + "\n";
            }
            conexion.disconnect();
        }
        catch (Exception e){
            pagina+=e.getMessage();
        }
        return pagina;
    }
}




