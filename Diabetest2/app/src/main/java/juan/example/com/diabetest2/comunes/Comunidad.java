package juan.example.com.diabetest2.comunes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Vector;

import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.R;

// Autor: Juan David Velásquez Bedoya

public class Comunidad extends AppCompatActivity {
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunidad);

        ctx = this;
        Comunidad.consultar co = new Comunidad.consultar();
        co.execute();

        if(Inicio.rol.contains("paciente")){
            Button crearTema = (Button) findViewById(R.id.button1099);
            crearTema.setVisibility(View.INVISIBLE);
        }
    }

    Vector listadoX = new Vector();
    Vector fechas = new Vector();
    static Vector idAutor = new Vector();
    Vector participante = new Vector();
    Vector mensajes = new Vector();
    Vector tema = new Vector();

    private class consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "comunidad");
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/comunidad", sobre);
                listadoX = (Vector) sobre.getResponse();
            } catch (Exception e) {}
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true){
                //Acomodación de la consulta obtenida
                int i = 0;
                if(listadoX != null) {
                    while (i < listadoX.size()) {
                        fechas.add(listadoX.get(i));
                        tema.add(listadoX.get(i+1));
                        idAutor.add(listadoX.get(i + 2));
                        participante.add(listadoX.get(i + 3));
                        mensajes.add(listadoX.get(i + 4));
                        i = i + 5;
                    }
                    //Llenado del ListView principal --------------------------------------------------------------------

                    ListView lv = (ListView) findViewById(R.id.id_publicaciones);
                    lv.setAdapter(new AaTema((Activity) ctx,
                            (String[]) fechas.toArray(new String[fechas.size()]),
                            (String[]) tema.toArray(new String[tema.size()])
                    ));
                    // Cambio de partes


                    //Selector de item ----------------------------------------------------------------------------------
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            DiscusionComunidad.titulo = (String) tema.get(position);
                            abrir(null);
                        }
                    });
                }
            }
        }
    }

    public void abrir(View v) {
        Intent intento = new Intent(this, DiscusionComunidad.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); this.finish(); }
    }

    public void crearTema(View v) {
        Intent intento = new Intent(this, CrearComunidad.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); this.finish(); }
    }
    //Chequear conexion a internet  ----------------------------------
    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
