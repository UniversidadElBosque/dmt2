package juan.example.com.diabetest2.administrador;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import juan.example.com.diabetest2.R;


// Autor: Juan David Velásquez Bedoya

public class CrearProfesional extends AppCompatActivity {

    static EditText a,b,c,d,e,f,g,h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_profesional);

        a = (EditText) findViewById(R.id.id_nombre_profesional);
        b = (EditText) findViewById(R.id.id_apellidos_profesional);
        c = (EditText) findViewById(R.id.id_cedula_profesional);
        d = (EditText) findViewById(R.id.id_correo_profesional);
        e = (EditText) findViewById(R.id.id_clave_profesional);
        f = (EditText) findViewById(R.id.id_telefono_profesional);
        g = (EditText) findViewById(R.id.id_direccion_profesional);
    }


    String respuesta;
    String a1, b1,d1,e1,g1;
    long c1, f1;
    public void procesar (View v) {
        a1 = a.getText().toString();
        b1  = b.getText().toString();
        c1  = Long.parseLong(c.getText().toString());
        d1  = d.getText().toString();
        e1  = e.getText().toString();
        f1  = Long.parseLong(f.getText().toString());
        g1  = g.getText().toString();
        CrearProfesional.crear cr = new CrearProfesional.crear();
        if(probarInternet() == false){
            Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show();
        } else{ cr.execute(); }
    }

    private class crear extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "crearProfesional");
                solicitud.addProperty("nombre", a1);
                solicitud.addProperty("cedula", c1);
                solicitud.addProperty("email", d1);
                solicitud.addProperty("clave", e1);
                solicitud.addProperty("telefono", f1);
                solicitud.addProperty("apellidos", b1);
                solicitud.addProperty("direccion", g1);
                solicitud.addProperty("creador", ServicioDT2.idLocal);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/crearProfesional", sobre);
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                respuesta = resultado.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                if(respuesta.contains("existe")){
                    Toast.makeText(getApplicationContext(), ""+respuesta, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), respuesta, Toast.LENGTH_LONG).show();
                    abrir(null);
                }
            }
        }
    }

    //Chequear conexion a internet  ----------------------------------
    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //Conector entre actividades
    public void abrir(View v) {
        Intent intento = new Intent(this, Administracion.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); this.finish(); }
    }
}


