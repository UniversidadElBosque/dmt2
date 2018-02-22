package juan.example.com.diabetest2.paciente;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Vector;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.administrador.ServicioDT2;

// Autor: Juan David Velásquez Bedoya

public class Metas extends AppCompatActivity {

    static Long id = ServicioDT2.idLocal;
    Switch a,b,c,d,e,f,g,h,i,j,k;
    boolean op, a1,b1,c1,d1,e1,f1,g1,h1,i1,j1,k1;
    Button z;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metas);

        a = (Switch) findViewById(R.id.id_meta1);
        b = (Switch) findViewById(R.id.id_meta2);
        c = (Switch) findViewById(R.id.id_meta3);
        d = (Switch) findViewById(R.id.id_meta4);
        e = (Switch) findViewById(R.id.id_meta5);
        f = (Switch) findViewById(R.id.id_meta6);
        z = (Button) findViewById(R.id.button27);

        //Verificación del rol para cambio del id
        if(Inicio.rol.contains("profesional")){
            id = Evolucion.id;
            z.setVisibility(View.INVISIBLE);
        }

        op = false;
        Metas.Consultar co = new  Metas.Consultar();
        co.execute();
    }

    public void procesar(View v){
        op = true;
        a1 = a.isChecked();
        b1 = b.isChecked();
        c1 = c.isChecked();
        d1 = d.isChecked();
        e1 = e.isChecked();
        f1 = f.isChecked();

        Metas.Consultar co = new  Metas.Consultar();
        co.execute();
    }

    Vector listadoX = new Vector();
    String respuesta = "";

    private class Consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                if(op == false){
                    SoapObject solicitud = new SoapObject(Inicio.namespace, "consultarMetas");
                    solicitud.addProperty("paciente", id);
                    SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    sobre.setOutputSoapObject(solicitud);
                    HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                    transporte.call("http://Servicios/consultarMetas", sobre);
                    listadoX = (Vector) sobre.getResponse();
                }
                else if(op == true) {
                    SoapObject solicitud = new SoapObject(Inicio.namespace, "crearMetas");
                    solicitud.addProperty("paciente", id);
                    solicitud.addProperty("a", a1);
                    solicitud.addProperty("b", b1);
                    solicitud.addProperty("c", c1);
                    solicitud.addProperty("d", d1);
                    solicitud.addProperty("e", e1);
                    solicitud.addProperty("f", f1);
                    solicitud.addProperty("g", false);
                    solicitud.addProperty("h", false);
                    solicitud.addProperty("i", false);
                    solicitud.addProperty("j", false);
                    SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    sobre.setOutputSoapObject(solicitud);
                    HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                    transporte.call("http://Servicios/crearMetas", sobre);
                    SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                    respuesta = resultado.toString();
                }
            } catch (Exception eee) { Log.e("Error:", String.valueOf(eee)); }
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                if(op == false){
                    //Acomodación de la consulta obtenida
                    int i = 0;
                    if (listadoX != null) {
                        while (i < listadoX.size()) {
                            a.setChecked(listadoX.get(i).toString().contains("1"));
                            b.setChecked(listadoX.get(i+1).toString().contains("1"));
                            c.setChecked(listadoX.get(i+2).toString().contains("1"));
                            d.setChecked(listadoX.get(i+3).toString().contains("1"));
                            e.setChecked(listadoX.get(i+4).toString().contains("1"));
                            f.setChecked(listadoX.get(i+5).toString().contains("1"));
                            i = i + 11;
                        }
                    }
                }else{ Toast.makeText(Metas.this, ""+respuesta, Toast.LENGTH_SHORT).show();  }
            }
        }
    }

}
