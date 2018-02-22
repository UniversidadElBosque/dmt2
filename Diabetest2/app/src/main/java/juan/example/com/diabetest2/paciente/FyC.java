package juan.example.com.diabetest2.paciente;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class FyC extends AppCompatActivity {

    EditText cc, or,ca;
    RadioButton a,b,c,d,e,f,g,h,i,j,k,l,m,n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fy_c);

        //SOLO se usan los valores positivos, porque los negativos son por defecto
//Cigarrillos
        a = (RadioButton) findViewById(R.id.Si1);

        cc = (EditText) findViewById(R.id.aCuantos);
        b = (RadioButton) findViewById(R.id.aDiario);
        c = (RadioButton) findViewById(R.id.aSemanal);

        d = (RadioButton) findViewById(R.id.Si2);
//Alcohol
        e = (RadioButton) findViewById(R.id.Si3); //Si bebe
        ca = (EditText) findViewById(R.id.id_cantidad_alcohol);
        f = (RadioButton) findViewById(R.id.Si4);
 //enf toda la vida
        g = (RadioButton) findViewById(R.id.Si5);
//control por medicacion
        h = (RadioButton) findViewById(R.id.Si6);

        or = (EditText) findViewById(R.id.fOrganos);
    }

    boolean a1, b1, c1,d1,e1,f1;
    int cc1 = 0;
    int ca1 = 0;
    String  or1 = "";
    public void procesar (View v) {
        a1 = a.isChecked();
        if (a.isChecked() && b.isChecked()) { //Chequeo de los cigarrillos
            if(cc.getText().toString().length()>0){cc1 = Integer.parseInt(cc.getText().toString()) * 30;}
            Log.e("Chequeo de valor: ", "Cigarros: " + cc1);
        }
        if (a.isChecked() && c.isChecked()) {
            if(cc.getText().toString().length()>0){cc1 = Integer.parseInt(cc.getText().toString()) * 4;}
            Log.e("Chequeo de valor: ", "Cigarros: " + cc1);
        }
        b1 = d.isChecked();
        c1 = e.isChecked();
        if (e.isChecked()) { //Cantidad de alcohol
            ca1 = Integer.parseInt(ca.getText().toString());
        }
        d1 = f.isChecked();
        e1 = g.isChecked();
        f1 = h.isChecked();
        or1 = or.getText().toString();
//Validación previa a consulta a BD
        if (a.isChecked() && (b.isChecked()==false && c.isChecked()==false) ){ Toast.makeText(getApplicationContext(), "Datos incoherentes o incompletos sobre fumar", Toast.LENGTH_LONG).show();}
        else if((b.isChecked()==false && c.isChecked()==false) && cc.getText().toString().length()>0){ Toast.makeText(getApplicationContext(), "Incoherencia: Se ingresaron cigarrillos sin definir frecuencia", Toast.LENGTH_LONG).show();}
        else if(a.isChecked() && cc.getText().toString().length()<1){ Toast.makeText(getApplicationContext(), "Incoherencia: No se indicó la cantidad de cigarrillos", Toast.LENGTH_LONG).show();}
        else if(a.isChecked()==false && cc.getText().toString().length()>0){ Toast.makeText(getApplicationContext(), "Incoherencia: Se ingresaron cigarrillos pero no se fuma", Toast.LENGTH_LONG).show();}
        else {
            FyC.consultar co = new FyC.consultar();
            co.execute();
            //Toast.makeText(getApplicationContext(), "Consultara a bd", Toast.LENGTH_LONG).show();
        }
    }

    String respuesta = "";
    private class consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "crearfyc");
                solicitud.addProperty("paciente", ServicioDT2.idLocal);
                solicitud.addProperty("a", a1); //si fuma
                solicitud.addProperty("b", cc1); // Cuantos fuma
                solicitud.addProperty("c", b1);
                solicitud.addProperty("d", c1); //Si bebe
                solicitud.addProperty("e", ca1); //Cuanto bebe
                solicitud.addProperty("f", d1);
                solicitud.addProperty("g", e1);
                solicitud.addProperty("h", f1);
                solicitud.addProperty("i", or1);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/crearfyc", sobre);
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
        Intent intento = new Intent(this, ApoyoSocial.class);
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
