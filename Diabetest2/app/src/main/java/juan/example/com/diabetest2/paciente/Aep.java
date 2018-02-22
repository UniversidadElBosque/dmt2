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
import android.widget.Switch;
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

public class Aep extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aep);
        a = (SeekBar) findViewById(R.id.id_psi1);
        b = (SeekBar) findViewById(R.id.id_psi2);
        c = (SeekBar) findViewById(R.id.id_psi3);
        d = (SeekBar) findViewById(R.id.id_psi4);

        e = (SeekBar) findViewById(R.id.id_psi5);
        f = (SeekBar) findViewById(R.id.id_psi6);
        g = (SeekBar) findViewById(R.id.id_psi7);

        h = (Switch) findViewById(R.id.switch11);
        i = (Switch) findViewById(R.id.switch12);
        j = (Switch) findViewById(R.id.switch13);
        k = (Switch) findViewById(R.id.switch14);
    }

    SeekBar a,b,c,d ,e,f,g;
    Switch h,i,j,k;
    int a1,b1,c1,d1 ,e1,f1,g1;
    boolean h1,i1,j1,k1;
    public void procesar (View v) {
        a1 = a.getProgress();
        b1 = b.getProgress();
        c1 = c.getProgress();
        d1 = d.getProgress();
        e1 = e.getProgress();
        f1 = f.getProgress();
        g1 = g.getProgress();
        h1 = h.isChecked();
        i1 = i.isChecked();
        j1 = j.isChecked();
        k1 = k.isChecked();
        Aep.consultar co = new Aep.consultar();
        co.execute();
    }


    String respuesta = "";
    private class consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "aep");
                solicitud.addProperty("id", ServicioDT2.idLocal);
                solicitud.addProperty("a", a1);
                solicitud.addProperty("b", b1);
                solicitud.addProperty("c", c1);
                solicitud.addProperty("d", d1);
                solicitud.addProperty("e", e1);
                solicitud.addProperty("f", f1);
                solicitud.addProperty("g", g1);
                solicitud.addProperty("h", h1);
                solicitud.addProperty("i", i1);
                solicitud.addProperty("j", j1);
                solicitud.addProperty("k", k1);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/crearPsicoeducacion", sobre);
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
        Intent intento = new Intent(this, MenuPaciente.class);
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
