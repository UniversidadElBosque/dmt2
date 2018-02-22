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

public class HabitosSaludables extends AppCompatActivity {

    static Context ctx;
    Button z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitos_saludables);
        ctx = this;
        HabitosSaludables.consultar co = new HabitosSaludables.consultar();
        co.execute();

        z = (Button) findViewById(R.id.id_bt_crear_habito);
        if((Inicio.rol.contains("paciente"))| (Inicio.rol.contains("familiar"))){
            z.setVisibility(View.INVISIBLE);
        }
    }


    Vector tablaX = new Vector();
    Vector titulosX = new Vector();
    Vector descripcionX = new Vector();
    Vector contenidoX = new Vector();
    Vector imagenX = new Vector();
    Vector idfilaX = new Vector();

    private class consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "habitos");
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/habitos", sobre);
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
                        idfilaX.add(tablaX.get(i));
                        titulosX.add(tablaX.get(i+1));
                        descripcionX.add(tablaX.get(i + 2));
                        contenidoX.add(tablaX.get(i + 3));
                        imagenX.add(Inicio.urlImagenes + tablaX.get(i + 4));
                        i = i + 5;
                    }

                    //Llenado del ListView principal --------------------------------------------------------------------
                    ListView lv = (ListView) findViewById(R.id.id_lista_habitos_saludables);
                    lv.setAdapter(new AaHabitos((Activity) ctx,
                            (String[]) imagenX.toArray(new String[imagenX.size()]),
                            (String[]) titulosX.toArray(new String[titulosX.size()]),
                            (String[]) descripcionX.toArray(new String[descripcionX.size()])
                    ));

                    //Selector de item ----------------------------------------------------------------------------------
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            VerHabito.idfila = (String) idfilaX.get(+position);
                            VerHabito.titulo = (String) titulosX.get(+position);
                            VerHabito.descripcion = (String) descripcionX.get(+position);
                            VerHabito.contenido = (String) contenidoX.get(+position);
                            VerHabito.imagen = (String) imagenX.get(+position);
                            abrir(null);
                        }
                    });
                }
            }
        }

    }
    public void abrir(View v) {
        Intent intento = new Intent(this, VerHabito.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }

    public void crearHabito(View v) {
        Intent intento = new Intent(this, CrearHabito.class);
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
