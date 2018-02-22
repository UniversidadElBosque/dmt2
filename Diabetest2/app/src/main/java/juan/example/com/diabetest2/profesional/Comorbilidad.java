package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Switch;
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

public class Comorbilidad extends AppCompatActivity {

    Switch a, b, c, d, e, f, g, h,i, j;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comorbilidad);

        a = (Switch) findViewById(R.id.switch1);
        b = (Switch) findViewById(R.id.switch2);
        c = (Switch) findViewById(R.id.switch3);
        d = (Switch) findViewById(R.id.switch4);
        e = (Switch) findViewById(R.id.switch5);
        f = (Switch) findViewById(R.id.switch6);
        g = (Switch) findViewById(R.id.switch7);
        h = (Switch) findViewById(R.id.switch8);
        i = (Switch) findViewById(R.id.switch9);
        j = (Switch) findViewById(R.id.switch10);
    }

    String respuesta;
    boolean a1, b1, c1, d1, e1, f1, g1, h1, i1, j1;
    public void procesar (View v) {
        a1 = a.isChecked();
        b1 = b.isChecked();
        c1 = c.isChecked();
        d1 = d.isChecked();
        e1 = e.isChecked();
        f1 = f.isChecked();
        g1 = g.isChecked();
        h1 = h.isChecked();
        i1 = i.isChecked();
        j1 = i.isChecked();
        //int rr = (a.isChecked()) ? 1:0;
        Comorbilidad.Consultar co = new Comorbilidad.Consultar();
        co.execute();
    }

    private class Consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "crearComorbilidad");
                solicitud.addProperty("paciente", CrearPaciente.paciente);
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
                solicitud.addProperty("idUsuario", ServicioDT2.idLocal);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/crearComorbilidad", sobre);
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
                Toast.makeText(getApplicationContext(), respuesta, Toast.LENGTH_LONG).show();
                abrir(null);
            }
        }
    }


    //Conector entre actividades
    public void abrir(View v) {
        Intent intento = new Intent(this, MenuProfesional.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); this.finish(); }
    }

    //Advertencia de retroceso
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Toast.makeText(getApplicationContext(), "Por favor complete el diagnóstico!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //Chequear conexion a internet  ----------------------------------
    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
/*
   private RequestQueue colaDeSolicitudes;
    private StringRequest peticion;
    private String url = "http://jdv.com.co/procesosAndroid.php";
    private Switch a, b, c, d, e, f, g, h,i;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comorbilidad);
        ctx = Comorbilidad.this;
        colaDeSolicitudes = Volley.newRequestQueue(ctx);
        a = (Switch) findViewById(R.id_destinatario.switch1);
        b = (Switch) findViewById(R.id_destinatario.switch2);
        c = (Switch) findViewById(R.id_destinatario.switch3);
        d = (Switch) findViewById(R.id_destinatario.switch4);
        e = (Switch) findViewById(R.id_destinatario.switch5);
        f = (Switch) findViewById(R.id_destinatario.switch6);
        g = (Switch) findViewById(R.id_destinatario.switch7);
        h = (Switch) findViewById(R.id_destinatario.switch8);
        i = (Switch) findViewById(R.id_destinatario.switch9);
    }

    public void procesar(View v) {
        peticion = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {public void onResponse(String response) {
                    Log.d("rta_servidor", response);
                    Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show(); }},
                new Response.ErrorListener() {public void onErrorResponse(VolleyError error) {
                    Log.d("error_servidor", error.toString());}
                }) {
            public Map<String, String> getParams()  {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("correo", Inicio.correo.getText().toString());
                parametros.put("clave", a.getText().toString());
                parametros.put("nombres", b.getText().toString());
                parametros.put("apellidos", c.getText().toString());
                parametros.put("sexo", d.getText().toString());
                parametros.put("telefono", e.getText().toString());
                parametros.put("direccion", f.getText().toString());
                parametros.put("pais", g.getText().toString());
                parametros.put("ciudad", h.getText().toString());
                parametros.put("ciudad", i.getText().toString());
                parametros.put("operacion", "us");
                return parametros;
            }
        };
        colaDeSolicitudes.add(peticion);
        abrir(null);
    }
 */