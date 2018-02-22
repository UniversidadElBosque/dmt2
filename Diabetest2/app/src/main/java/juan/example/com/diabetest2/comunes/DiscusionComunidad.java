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
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Vector;

import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.R;

// Autor: Juan David Velásquez Bedoya

public class DiscusionComunidad extends AppCompatActivity {

    Context ctx;
    static String titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discusion_comunidad);

        ctx = this;
        DiscusionComunidad.Consultar co = new DiscusionComunidad.Consultar();
        co.execute();
        TextView tituloTema = (TextView) findViewById(R.id.id_nombre_tema_foro);
        tituloTema.setText(titulo);

        if(Inicio.rol.contains("paciente")){
            Button borrarTema = (Button) findViewById(R.id.button22);
            borrarTema.setVisibility(View.INVISIBLE);
        }
    }



    Vector listadoX = new Vector();
    Vector fechas = new Vector();
    Vector participante = new Vector();
    Vector mensajes = new Vector();

    private class Consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "consultarTemas");
                solicitud.addProperty("tema", titulo);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/consultarTemas", sobre);
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
                        participante.add(listadoX.get(i + 1));
                        mensajes.add(listadoX.get(i + 2));
                        i = i + 3;
                    }
                    //Llenado del ListView principal --------------------------------------------------------------------

                    ListView lv = (ListView) findViewById(R.id.id_publicaciones);
                    lv.setAdapter(new AaComunidad((Activity) ctx,
                            (String[]) fechas.toArray(new String[fechas.size()]),
                            (String[]) participante.toArray(new String[participante.size()]),
                            (String[]) mensajes.toArray(new String[mensajes.size()])
                    ));
                    // Cambio de partes


                    //Selector de item ----------------------------------------------------------------------------------
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            abrir(null);
                        }
                    });
                }
            }
        }
    }

    public void abrir(View v) {
        Intent intento = new Intent(this, ComentarTema.class);
        this.finish();
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }

    //Chequear conexion a internet  ----------------------------------
    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    //-----------Borrar tema ----------------

    public void borrarTema(View v){
        DiscusionComunidad.BorrarTema di = new DiscusionComunidad.BorrarTema();
        di.execute();
    }
    private class BorrarTema extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "borrarTema");
                solicitud.addProperty("tema", titulo);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/borrarTema", sobre);
                listadoX = (Vector) sobre.getResponse();
            } catch (Exception e) {}
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true){
                salir(null);
            };
        }
    }
    public void salir(View v) {
        Intent intento = new Intent(this, Comunidad.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); this.finish(); }
    }


}
