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
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;

// Autor: Juan David Velásquez Bedoya

public class DiagnosticoInicial extends AppCompatActivity {

    private EditText tension1,tension2, trigli, hba1c, peso, talla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostico_inicial);


        tension1 = (EditText) findViewById(R.id.id_tension1);
        tension2 = (EditText) findViewById(R.id.id_tension2);
        trigli = (EditText) findViewById(R.id.id_trigliceridos);
        hba1c = (EditText) findViewById(R.id.id_hba1c);
        peso = (EditText) findViewById(R.id.id_peso);
        talla = (EditText) findViewById(R.id.id_talla);
    }

    String respuesta;
    String a;
    String b;
    String c;
    String d;
    String e;
    String f;
    String g;
    public void procesar (View v) {
        a = CrearPaciente.paciente;
        b = tension1.getText().toString();
        c = tension2.getText().toString();
        d = trigli.getText().toString();
        e = hba1c.getText().toString();
        f = peso.getText().toString();
        g = talla.getText().toString();

        if(e.length() == 3){
            DiagnosticoInicial.consultar co = new DiagnosticoInicial.consultar();
            co.execute();
        }else{
            Toast.makeText(getApplicationContext(), "El formato para HbA1c es: #.#", Toast.LENGTH_LONG).show();
        }


    }

    private class consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "di");
                solicitud.addProperty("id", a);
                solicitud.addProperty("tension1", b);
                solicitud.addProperty("tension2", c);
                solicitud.addProperty("trigli", d);
                solicitud.addProperty("hba1c", e);
                solicitud.addProperty("peso", f);
                solicitud.addProperty("talla", g);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/di", sobre);
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
        Intent intento = new Intent(this, Comorbilidad.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); this.finish(); }
    }
    //Chequear conexion a internet  ----------------------------------
    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //Advertencia de retroceso
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Toast.makeText(getApplicationContext(), "Por favor ingrese los datos!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
/*

    private RequestQueue colaDeSolicitudes;
    private StringRequest peticion;
    private String url = "http://jdv.com.co/procesosAndroid.php";
    private EditText imc, tension, trigli, glucemia, peso, talla;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostico_inicial);

        ctx = DiagnosticoInicial.this;
        colaDeSolicitudes = Volley.newRequestQueue(ctx);
        imc = (EditText) findViewById(R.id_destinatario.id_IMC);
        tension = (EditText) findViewById(R.id_destinatario.id_tension);
        trigli = (EditText) findViewById(R.id_destinatario.id_trigliceridos);
        glucemia = (EditText) findViewById(R.id_destinatario.id_glucemiaCa);
        peso = (EditText) findViewById(R.id_destinatario.id_peso);
        talla = (EditText) findViewById(R.id_destinatario.id_talla);
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
                parametros.put("identificacion", CrearPaciente.identificacion.getText().toString());
                parametros.put("imc", imc.getText().toString());
                parametros.put("tension", tension.getText().toString());
                parametros.put("trigli", trigli.getText().toString());
                parametros.put("glucemia", glucemia.getText().toString());
                parametros.put("peso", peso.getText().toString());
                parametros.put("talla", talla.getText().toString());
                parametros.put("op", "di");
                return parametros;
            }
        };
        colaDeSolicitudes.add(peticion);
        abrir(null);
    }

 */