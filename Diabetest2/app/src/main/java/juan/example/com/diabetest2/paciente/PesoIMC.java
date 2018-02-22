package juan.example.com.diabetest2.paciente;

import android.content.Intent;
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

public class PesoIMC extends AppCompatActivity {

    TextView valor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peso_imc);
        valor = (TextView) findViewById(R.id.id_peso_actualizar);
    }

    public void procesar(View v){
        if(valor.getText().length()>0){h = valor.getText().toString();}
        PesoIMC.Ingresar in = new PesoIMC.Ingresar();
        if(h.length()>0){in.execute();}

    }

    String respuesta;
    String h = null;

private class Ingresar extends AsyncTask<Void, Void, Boolean> {
    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            SoapObject solicitud = new SoapObject(Inicio.namespace, "actualizarPeso");
            solicitud.addProperty("id", ServicioDT2.idLocal);
            solicitud.addProperty("peso", h);
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.setOutputSoapObject(solicitud);
            HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
            transporte.call("http://Servicios/actualizarPeso", sobre);
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
        this.finish();
        startActivity(intento);
    }
}