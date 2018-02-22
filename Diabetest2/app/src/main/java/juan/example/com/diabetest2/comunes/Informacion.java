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

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.ServicioDT2;

// Autor: Juan David Velásquez Bedoya

public class Informacion extends AppCompatActivity {


    Context ctx;
    static Timer ti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);
        ctx = this;

        ti = new Timer();
        Informacion.Tarea tt = new Informacion.Tarea();
        ti.scheduleAtFixedRate(tt, 0, 10000);
    }

    class Tarea extends TimerTask {
        @Override
        public void run() {
            Informacion.consultar co = new Informacion.consultar();
            co.execute();
        }
    }

    //--------------------------------------------------------------------------------------------------
    Vector listadoX = new Vector();
    Vector titulo = new Vector();
    Vector descripcion = new Vector();
    Vector contenido = new Vector();

    private class consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "listaInformacion");
                solicitud.addProperty("id", ServicioDT2.idLocal);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/listaInformacion", sobre);
                listadoX = (Vector) sobre.getResponse();
            } catch (Exception e) {
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                //Acomodación de la consulta obtenida
                titulo.clear();
                descripcion.clear();
                contenido.clear();
                int i = 0;
                if (listadoX != null) {
                    while (i < listadoX.size()) {
                        titulo.add(listadoX.get(i));
                        descripcion.add(listadoX.get(i + 1));
                        contenido.add(listadoX.get(i + 2));
                        i = i + 3;
                    }
                    //Llenado del ListView principal --------------------------------------------------------------------
                    ListView lv = (ListView) findViewById(R.id.id_lista_informacion);
                    //lv.setAdapter(null);
                    lv.setAdapter(new AaInformacion((Activity) ctx,
                            (String[]) titulo.toArray(new String[titulo.size()]),
                            (String[]) descripcion.toArray(new String[descripcion.size()])
                    ));
                    //--------------------------


                    //Selector de item ----------------------------------------------------------------------------------
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            VerInformacion.titulo = (String) titulo.get(position);
                            VerInformacion.descripcion = (String) descripcion.get(position);
                            VerInformacion.contenido = (String) contenido.get(position);
                            abrir(null);
                        }
                    });
                }
            }
        }
    }



    public void abrir(View v) {
        Intent intento = new Intent(this, VerInformacion.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }

    //Chequear conexion a internet  ----------------------------------
    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}