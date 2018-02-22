package juan.example.com.diabetest2.comunes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Vector;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.administrador.ServicioDT2;
import juan.example.com.diabetest2.familiar.MenuFamiliar;
import juan.example.com.diabetest2.paciente.FyC;

// Autor: Juan David Velásquez Bedoya

public class Usuario extends AppCompatActivity {

    EditText a, b, c, e, f,g;
    Spinner sx, ci;
    Context ctx;
    //private Calendar calendario; //Se usaban en el dialogo datepicker
    //private int anio, mes, dia; //Se usaban en el dialogo datepicker
    DatePicker dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        Inicio.tiempoInicial();
        ctx = this;

        a = (EditText) findViewById(R.id.id_clavePa);
        b = (EditText) findViewById(R.id.id_nombresPa);
        c = (EditText) findViewById(R.id.id_apellidosPa);

        e = (EditText) findViewById(R.id.id_telefono);
        f = (EditText) findViewById(R.id.id_direccion);
        //g = (EditText) findViewById(R.id.id_fecha_nacimiento);
        dp = (DatePicker) findViewById(R.id.datePicker); //Mi date picker
        sx = (Spinner) findViewById(R.id.id_sexo);
        ci = (Spinner) findViewById(R.id.id_ciudad);

        ArrayList sexos = new ArrayList();
        sexos.add("Masculino");
        sexos.add("Femenino");
        ArrayAdapter bb = new  ArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_1, sexos);
        sx.setAdapter(bb);
        /*
//Date Picker anterior
        calendario = Calendar.getInstance();
        anio = calendario.get(Calendar.YEAR);
        mes = calendario.get(Calendar.MONTH);
        dia = calendario.get(Calendar.DAY_OF_MONTH);

        TextView verPicker = (TextView) findViewById(R.id.id_fecha_nacimiento);
        verPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                definirFecha(null);
            }
        });
//Fin Date picker
        Usuario.consultarCiudades cc = new Usuario.consultarCiudades();
        cc.execute();
        */
    }
//----------------------------------------------------------------------
    //Seleccionador de la fecha anterior
    /*
    public void definirFecha(View view) {
    showDialog(999);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this,myDateListener, anio, mes, dia); }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
         @Override
         public void onDateSet(DatePicker arg0,int arg1, int arg2, int arg3) {
             mostrarFecha(arg1, arg2+1, arg3);
         }
     };
    private void mostrarFecha(int year, int month, int day) {
        g.setText(new StringBuilder().append(year).append("/").append(month).append("/").append(day));
    }
    */
//---------------------------------------------------------------------------
    String a1, b1, c1, d1, e1, f1, g1;
    int ci1;
    public void procesar (View v) {
        a1 = a.getText().toString(); // La clave
        b1 = b.getText().toString(); // Nombres
        c1 = c.getText().toString(); // Apellidos
        e1 = e.getText().toString(); // Teléfono
        f1 = f.getText().toString(); // Dirección
        //g1 = g.getText().toString(); // fecha de nacimiento
        int anio = dp.getYear();
        int mes = dp.getMonth();
        int dia = dp.getDayOfMonth();
        g1 = anio+"-"+mes+"-"+dia;

        d1 = sx.getSelectedItem().toString(); // Sexo
        ci1 = (int) ci.getSelectedItemId() + 1; //Ciudad

        if(a1.isEmpty() || b1.isEmpty() || c1.isEmpty() || d1.isEmpty() || e1.isEmpty() || f1.isEmpty() || g1.isEmpty() || a1.contains(";") ||
                a1.contains("#") || a1.contains(",") || a1.contains("(") || a1.contains("'") || a1.contains("!")|| a1.contains("{")|| a1.contains("}")){
            Toast.makeText(getApplicationContext(), "Por favor ingrese todos los datos", Toast.LENGTH_LONG).show();
        }
        else {
            Usuario.consultar co = new Usuario.consultar();
            co.execute();

        }
    }


    public void cuadroAlerta(View v) {
        if(Inicio.rol.contains("familiar")){ procesar(null);}
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("A continuación encontrará una serie de enunciados relacionados con su estilo de vida, por favor arrastre el indicador de cada enunciado según su criterio, su honestidad permitirá al profesional proporcionarle mejores herramientas para el manejo de su enfermedad.");
            alertDialogBuilder.setPositiveButton("Acepto las condiciones",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            procesar(null);
                        }
                    });

            alertDialogBuilder.setNegativeButton("Cancelar",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //Cierre
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }


    String respuesta = "";
    String proceso = "usuario";
    SoapSerializationEnvelope sobre;
    private class consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                if(Inicio.rol.contains("familiar")){ proceso = "usuarioFamiliar";}
                SoapObject solicitud = new SoapObject(Inicio.namespace, proceso);
                solicitud.addProperty("id", ServicioDT2.idLocal);
                solicitud.addProperty("clave", a1);
                solicitud.addProperty("nombres", b1);
                solicitud.addProperty("apellidos", c1);
                solicitud.addProperty("sexo", d1);
                solicitud.addProperty("telefono", e1);
                solicitud.addProperty("direccion", f1);
                solicitud.addProperty("ciudad", ci1);
                solicitud.addProperty("nacimiento", g1);
                sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/"+proceso, sobre);
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

    Vector listadoX;
    Vector <String> miCiudades = new Vector();;
    private class consultarCiudades extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "ciudades");
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/ciudades", sobre);
                listadoX = (Vector) sobre.getResponse();
            } catch (Exception e) {}
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                int i = 0;
                while(i < listadoX.size()){
                    miCiudades.add((String) listadoX.get(i+1)); // Super ojo que toca instaciar la variable Vector arriba
                    i = i+3; }

                ArrayAdapter aa = new  ArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_1, miCiudades);
                ci.setAdapter(aa);

            }
        }
    }

    public void abrir(View v) {
        Intent intento;
        if(Inicio.rol.contains("familiar")){
            intento = new Intent(this, MenuFamiliar.class);
        }else {
            intento = new Intent(this, FyC.class);
        }
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



/*
private RequestQueue colaDeSolicitudes;
    private StringRequest peticion;
    private String url = "http://jdv.com.co/procesosAndroid.php";
    private EditText a, b, c, d, e, f, g, h;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        ctx = Usuario.this;
        colaDeSolicitudes = Volley.newRequestQueue(ctx);
        a = (EditText) findViewById(R.id_destinatario.id_clave);
        b = (EditText) findViewById(R.id_destinatario.id_nombresPa);
        c = (EditText) findViewById(R.id_destinatario.id_apellidosPa);
        d = (EditText) findViewById(R.id_destinatario.id_sexo);
        e = (EditText) findViewById(R.id_destinatario.id_telefono);
        f = (EditText) findViewById(R.id_destinatario.id_direccion);
        g = (EditText) findViewById(R.id_destinatario.id_pais);
        h = (EditText) findViewById(R.id_destinatario.id_ciudad);
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
                parametros.put("operacion", "us");
                return parametros;
            }
        };
        colaDeSolicitudes.add(peticion);
        abrir(null);
    }

    //Conector entre actividades
    public void abrir(View v) {
        Intent intento = new Intent(this, FyC.class);
        //this.finish();
        startActivity(intento);
    }
 */