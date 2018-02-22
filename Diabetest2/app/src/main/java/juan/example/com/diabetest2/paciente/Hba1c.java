package juan.example.com.diabetest2.paciente;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.administrador.ServicioDT2;

// Autor: Juan David Velásquez Bedoya

public class Hba1c extends AppCompatActivity {

    TextView valor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hba1c);

        valor = (TextView) findViewById(R.id.id_hba1c_actual);
    }
    public void procesar(View v){
        if(valor.getText().length()>0){h = valor.getText().toString();}
        Hba1c.Ingresar in = new Hba1c.Ingresar();
        if(h.length()>0){in.execute();}

    }

    String respuesta;
    String h = null;

    private class Ingresar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "actualizarHba1c");
                solicitud.addProperty("id", ServicioDT2.idLocal);
                solicitud.addProperty("hba1c", h);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/actualizarHba1c", sobre);
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
                    Toast.makeText(getApplicationContext(), ""+respuesta, Toast.LENGTH_LONG).show();
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
