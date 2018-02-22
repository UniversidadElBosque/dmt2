package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Vector;

import juan.example.com.diabetest2.paciente.Evolucion;
import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.administrador.ServicioDT2;

// Autor: Juan David Velásquez Bedoya

public class Observaciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observaciones);

        Observaciones.Consultar co = new Observaciones.Consultar();
        co.execute();
    }

    public void crearObservacion(View v) {
        Intent intento = new Intent(this, CrearObservacion.class);
        this.finish();
        CrearObservacion.t = null;
        startActivity(intento);
    }

    ListView listaP;
    Vector listadoX = new Vector();
    Vector observaciones = new Vector();
    Vector id_elemento = new Vector();

    private class Consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "consultarObservaciones");
                solicitud.addProperty("id", ServicioDT2.idLocal);
                solicitud.addProperty("paciente", Evolucion.id);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/consultarObservaciones", sobre);
                listadoX = (Vector) sobre.getResponse();
            } catch (Exception e) {
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                if (listadoX != null) {
                    //Acomodación de la consulta obtenida
                    int i = 0;
                    while (i < listadoX.size()) {
                        observaciones.add(listadoX.get(i+1));
                        i = i + 2;
                    }
                    i = 0;
                    while (i < listadoX.size()) {
                        id_elemento.add(listadoX.get(i));
                        i = i + 2;
                    }
                    //Llenado de ListView
                    ArrayAdapter aa = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, observaciones);
                    listaP = (ListView) findViewById(R.id.id_lista_observaciones);
                    listaP.setAdapter(aa);

                    listaP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int elegido, long id) {
                            //int victima = Integer.parseInt((String) observaciones.get(elegido));
                            CrearObservacion.t = (String) observaciones.get(elegido);
                            abrirObservacion();
                        }
                    });
                }
            }
        }
    }

    public void abrirObservacion() {
        Intent intento = new Intent(this, CrearObservacion.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento);this.finish(); }
    }

    //Chequear conexion a internet  ----------------------------------
    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
    