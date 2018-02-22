package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import juan.example.com.diabetest2.paciente.Evolucion;
import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.administrador.ServicioDT2;

// Autor: Juan David Velásquez Bedoya

public class CrearObservacion extends AppCompatActivity {

    Button a;
    TextView b;
    public static String t;
    String old;
    int op = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_observacion);

        b = (TextView) findViewById(R.id.id_texto_observacion);
        if(t != null){
            b.setText(t);
            op = 1; //Cambio a actualizar
            old = t;
        }
    }

    public void procesar(View v){
        if(b.getText().length()>0){t = b.getText().toString();}
        CrearObservacion.Consultar co = new  CrearObservacion.Consultar();
        co.execute();
    }

    String respuesta = "";
    private class Consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                if(op == 1){
                    SoapObject solicitud = new SoapObject(Inicio.namespace, "actualizarObservacion");
                    solicitud.addProperty("texto", t);
                    solicitud.addProperty("old", old);
                    SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    sobre.setOutputSoapObject(solicitud);
                    HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                    transporte.call("http://Servicios/actualizarObservacion", sobre);
                    SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                    respuesta = resultado.toString();
                }else {
                    SoapObject solicitud = new SoapObject(Inicio.namespace, "crearObservacion");
                    solicitud.addProperty("id", ServicioDT2.idLocal);
                    solicitud.addProperty("paciente", Evolucion.id);
                    solicitud.addProperty("texto", t);
                    SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    sobre.setOutputSoapObject(solicitud);
                    HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                    transporte.call("http://Servicios/crearObservacion", sobre);
                    SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                    respuesta = resultado.toString();
                }
            } catch (Exception e) {   }
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                abrir(null);
            }
        }
    }
    public void abrir(View v) {
        Intent intento = new Intent(this, Observaciones.class);
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
