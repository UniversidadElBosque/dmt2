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

public class Recursos extends AppCompatActivity {


    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recursos);
        ctx = this;

        Recursos.consultar co = new consultar();
        co.execute();
    }


    Vector tablaX = new Vector();
    Vector fechasX = new Vector();
    Vector titulosX = new Vector();
    Vector mensajesX = new Vector();
    Vector recursosX = new Vector();
    Vector videoX = new Vector();
    Vector responsablesX = new Vector();

    private class consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "recursos");
                solicitud.addProperty("recurso", "");
                solicitud.addProperty("op", "c");
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/recursos", sobre);
                tablaX = (Vector) sobre.getResponse();
            } catch (Exception e) {}
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                //Acomodación de la consulta obtenida
                int i = 0;
                if(tablaX != null) {
                    while (i < tablaX.size()) {
                        fechasX.add(tablaX.get(i));
                        titulosX.add(tablaX.get(i + 1));
                        mensajesX.add(tablaX.get(i + 2));
                        recursosX.add(Inicio.urlImagenes + tablaX.get(i + 3));
                        videoX.add(tablaX.get(i + 4));
                        responsablesX.add(tablaX.get(i + 5));
                        i = i + 6;
                    }

                    //Llenado del ListView principal --------------------------------------------------------------------
                    ListView lv = (ListView) findViewById(R.id.listica);
                    lv.setAdapter(new AaRecursos((Activity) ctx,
                            (String[]) recursosX.toArray(new String[recursosX.size()]),
                            (String[]) titulosX.toArray(new String[titulosX.size()]),
                            (String[]) mensajesX.toArray(new String[mensajesX.size()])
                    ));

                    //Selector de item ----------------------------------------------------------------------------------
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //String seleccionado = (String) titulosX.get(+position) + " id_destinatario: " + position;
                            //Toast.makeText(getApplicationContext(), seleccionado, Toast.LENGTH_SHORT).show();
                            //i;
                            //ti, lin, contenido,fe;
                            VerRecurso.titulo = (String) titulosX.get(+position);
                            VerRecurso.link = (String) videoX.get(+position);
                            VerRecurso.contenido = (String) mensajesX.get(+position);
                            VerRecurso.fecha = (String) fechasX.get(+position);
                            VerRecurso.autor = (String) responsablesX.get(+position);
                            VerRecurso.urlImagen = (String) recursosX.get(+position);
                            abrir(null);
                        }
                    });
                }
            }
        }

    }
    public void abrir(View v) {
        Intent intento = new Intent(this, VerRecurso.class);
        startActivity(intento);if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento);this.finish(); }
    }
    //Chequear conexion a internet  ----------------------------------
    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}