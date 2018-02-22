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

public class ActividadFisica extends AppCompatActivity {
    SeekBar a,b,c,d, e,f,g,h,i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_fisica);
        Inicio.tiempoInicial();
        a = (SeekBar) findViewById(R.id.sbc1);
        b = (SeekBar) findViewById(R.id.sbc2);
        c = (SeekBar) findViewById(R.id.sbc3);
        d = (SeekBar) findViewById(R.id.sbc4);

        e = (SeekBar) findViewById(R.id.sbc5);
        f = (SeekBar) findViewById(R.id.sbc6);
        g = (SeekBar) findViewById(R.id.sbc7);
        h = (SeekBar) findViewById(R.id.sbc8);
        i = (SeekBar) findViewById(R.id.sbc9);
    }

    int a1,b1,c1,d1, e1,f1,g1,h1,i1;
    public void procesar (View v) {
        a1 = a.getProgress();
        b1 = b.getProgress();
        c1 = c.getProgress();
        d1 = d.getProgress();
        e1 = a.getProgress();
        f1 = b.getProgress();
        g1 = c.getProgress();
        h1 = d.getProgress();
        i1 = a.getProgress();
        ActividadFisica.consultar co = new ActividadFisica.consultar();
        co.execute();
    }

    String respuesta = "";
    private class consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "actividadFisica");
                solicitud.addProperty("paciente", ServicioDT2.idLocal);
                solicitud.addProperty("a", a1);
                solicitud.addProperty("b", b1);
                solicitud.addProperty("c", c1);
                solicitud.addProperty("d", d1);
                solicitud.addProperty("e", a1);
                solicitud.addProperty("f", b1);
                solicitud.addProperty("g", c1);
                solicitud.addProperty("h", d1);
                solicitud.addProperty("i", a1);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/actividadFisica", sobre);
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
        Intent intento = new Intent(this, Aep.class);
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
