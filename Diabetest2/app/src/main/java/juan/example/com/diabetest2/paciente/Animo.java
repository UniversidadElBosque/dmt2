package juan.example.com.diabetest2.paciente;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.ServicioDT2;

// Autor: Juan David Velásquez Bedoya

public class Animo extends AppCompatActivity {

    SeekBar nivel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animo);
        nivel = (SeekBar) findViewById(R.id.id_nivel_animo);
    }

    int a1;
    public void procesar (View v) {
        a1 = nivel.getProgress();
        guardarAnimo co = new guardarAnimo();
        co.execute();
    }

    String respuesta = "";
    private class guardarAnimo extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "crearAnimo");
                solicitud.addProperty("id", ServicioDT2.idLocal);
                solicitud.addProperty("nivel", a1);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/crearAnimo", sobre);
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
        Intent intento = new Intent(this, MenuPaciente.class);
        startActivity(intento);
        this.finish();
    }
}
