package juan.example.com.diabetest2.comunes;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;

import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.ServicioDT2;

// Autor: Juan David Velásquez Bedoya

public class CrearMensaje extends AppCompatActivity {

    public static long idDestino;
    public static String destinatario;
    TextView a1;
    EditText b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_mensaje);

        a1 = (TextView) findViewById(R.id.id_para);
        a1.setText(destinatario);
        b1 = (EditText) findViewById(R.id.id_mensaje_enviar);
    }

String mensaje;
    public void procesar (View v) throws IOException {
        mensaje = b1.getText().toString();
        CrearMensaje.consultar co = new CrearMensaje.consultar();
        co.execute();
    }

    String respuesta = "";
    private class consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "crearMensaje");
                solicitud.addProperty("id", ServicioDT2.idLocal);
                solicitud.addProperty("mensaje", mensaje);
                solicitud.addProperty("destinatario", idDestino);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/crearMensaje", sobre);
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                respuesta = resultado.toString();
            } catch (Exception e) {}
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                Toast.makeText(CrearMensaje.this, ""+ respuesta, Toast.LENGTH_SHORT).show();

                abrir(null);
            }
        }
    }

    public void abrir(View v) {
        Intent intento = new Intent(this, Mensajes.class);
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
