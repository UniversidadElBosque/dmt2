package juan.example.com.diabetest2.paciente;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.ServicioDT2;

// Autor: Juan David Velásquez Bedoya

public class ApoyoSocial extends AppCompatActivity {
    SeekBar a,b,c,d, e,f,g,h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apoyo_social);
        a = (SeekBar) findViewById(R.id.sb1);
        b = (SeekBar) findViewById(R.id.sb2);
        c = (SeekBar) findViewById(R.id.sb3);
        d = (SeekBar) findViewById(R.id.sb4);

        e = (SeekBar) findViewById(R.id.sb5);
        f = (SeekBar) findViewById(R.id.sb6);
        g = (SeekBar) findViewById(R.id.sb7);
        h = (SeekBar) findViewById(R.id.sb8);
    }

    int a1,b1,c1,d1,e1,f1,g1,h1;
    public void procesar (View v) {
        a1 = a.getProgress();
        b1 = b.getProgress();
        c1 = c.getProgress();
        d1 = d.getProgress();

        e1 = e.getProgress();
        f1 = f.getProgress();
        g1 = g.getProgress();
        h1 = h.getProgress();
        ApoyoSocial.consultar co = new ApoyoSocial.consultar();
        co.execute();
    }


    String respuesta = "";
    private class consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "apoyoSocial");
                solicitud.addProperty("paciente", ServicioDT2.idLocal);
                solicitud.addProperty("a", a1);
                solicitud.addProperty("b", b1);
                solicitud.addProperty("c", c1);
                solicitud.addProperty("d", d1);

                solicitud.addProperty("e", e1);
                solicitud.addProperty("f", f1);
                solicitud.addProperty("g", g1);
                solicitud.addProperty("h", h1);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/apoyoSocial", sobre);
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                respuesta = resultado.toString();
            } catch (Exception e) {   }
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                Toast.makeText(getApplicationContext(), ""+ respuesta, Toast.LENGTH_LONG).show();
                abrir(null);
            }
        }
    }


    public void abrir(View v) {
        Intent intento = new Intent(this, EstiloVida.class);
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
