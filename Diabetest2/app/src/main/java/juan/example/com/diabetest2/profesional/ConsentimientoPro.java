package juan.example.com.diabetest2.profesional;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.administrador.ServicioDT2;

// Autor: Juan David Velásquez Bedoya

public class ConsentimientoPro extends AppCompatActivity {

    CheckBox a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consentimiento_pro);
        a = (CheckBox) findViewById(R.id.checkBox_consentimientoPro);
    }

    public void procesar (View v) {
        if (a.isChecked()) {
            ConsentimientoPro.Consultar co = new ConsentimientoPro.Consultar();
            co.execute();
        }else {
            Toast.makeText(getApplicationContext(), "Se debe aceptar el Consentimiento Informado para usar esta App", Toast.LENGTH_LONG).show();
            salir(null);
        }
    }

    private class Consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "consentimiento");
                solicitud.addProperty("id", ServicioDT2.idLocal);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/consentimiento", sobre);
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
        Toast.makeText(getApplicationContext(), "¡Bienvenido!", Toast.LENGTH_LONG).show();
        Intent intento = new Intent(this, MenuProfesional.class);
        this.finish();
        startActivity(intento);
    }
    //Caso de no aceptación
    public void salir(View v) {
        Intent intento = new Intent(this, Inicio.class);
        try {
            FileOutputStream salida = new FileOutputStream(new File(getFilesDir(), "id.dt2"));
            ObjectOutputStream oos = new ObjectOutputStream(salida);
            oos.writeObject(null);
            oos.close();
            FileOutputStream salida2 = new FileOutputStream(new File(getFilesDir(), "correo.dt2"));
            ObjectOutputStream oos2 = new ObjectOutputStream(salida2);
            oos2.writeObject(null);
            oos2.close();
            FileOutputStream salida3 = new FileOutputStream(new File(getFilesDir(), "clave.dt2"));
            ObjectOutputStream oos3 = new ObjectOutputStream(salida3);
            oos3.writeObject(null);
            oos3.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.finish();
        startActivity(intento);
    }
}
