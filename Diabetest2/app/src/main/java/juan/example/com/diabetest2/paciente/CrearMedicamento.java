package juan.example.com.diabetest2.paciente;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.administrador.ServicioDT2;

// Autor: Juan David Velásquez Bedoya

public class CrearMedicamento extends AppCompatActivity {

    Long id = ServicioDT2.idLocal;
    TextView a,c,d,f,g;
    Spinner b;
    Switch e;
    boolean e1;
    String a1,b1,c1,d1,f1,g1;
    static String oldA,oldB,oldC,oldD,oldE,oldF,oldG,oldH;
    int op = 0;
    Button y,z;
    TimePicker tp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);

        a = (TextView) findViewById(R.id.id_nombre_medicamento);
        b = (Spinner) findViewById(R.id.id_tipo_presentacion);
        ArrayList <String> tipo = new ArrayList <String>();
        tipo.add("Inyección");
        tipo.add("Capsula");
        tipo.add("Pastilla");
        tipo.add("Pomada");
        tipo.add("Polvo");
        tipo.add("Gotas");
        tipo.add("Jarabe");
        tipo.add("Emulsión");
        tipo.add("Extracto");
        tipo.add("Loción");
        tipo.add("Solución");
        tipo.add("Comprimido");
        tipo.add("Otro");
        ArrayAdapter bb = new  ArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_1, tipo);
        b.setAdapter(bb);
        c = (TextView) findViewById(R.id.id_dosificacion_medicamento);
        d = (TextView) findViewById(R.id.id_posologia);
        e = (Switch) findViewById(R.id.id_recordar_medicamento);
        f = (TextView) findViewById(R.id.id_observaciones_medicamento);
        y = (Button) findViewById(R.id.id_bt_guardar_medicamento);
        z = (Button) findViewById(R.id.id_bt_borrar_medicamento);

        //TimePicker
        tp  = (TimePicker) findViewById(R.id.timePicker);

        //Verificación del rol para cambio del id
        if(Inicio.rol.contains("profesional")){
            id = Evolucion.id;
            y.setVisibility(View.INVISIBLE);
            z.setVisibility(View.INVISIBLE);
        }

        //Llenado en caso de actualización
        if(oldA!=null){
            op = 1;
            a.setText(oldA);
            b.setSelection(tipo.indexOf(oldB));
            c.setText(oldC);
            d.setText(oldD);
            e.setChecked(Integer.parseInt(oldE) == 1);
            f.setText(oldF);
            tp.setCurrentHour(Integer.parseInt(oldG.substring(0,2)));
            tp.setCurrentMinute(Integer.parseInt(oldG.substring(3,5)));
        }

    }


    public void procesar(View v){
        a1 = a.getText().toString();
        b1 = b.getSelectedItem().toString();
        c1 = c.getText().toString();
        d1 = d.getText().toString();
        e1 = e.isChecked();
        f1 = f.getText().toString();

        if(Build.VERSION.SDK_INT < 23){
            int hora = tp.getCurrentHour();
            int minuto = tp.getCurrentMinute();
            g1 = hora+":"+minuto;
        }
        if(a.getText().length()>1) {
            Consultar co = new Consultar();
            co.execute();
        }else {
            Toast.makeText(this, "Falta el nombre del medicamento", Toast.LENGTH_SHORT).show();
        }

    }

    public void desactivarMedicamento(View v){
        op = 2;
        CrearMedicamento.Consultar co = new CrearMedicamento.Consultar();
        co.execute();
    }

    String respuesta = "";
    private class Consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                if(op == 1){
                    SoapObject solicitud = new SoapObject(Inicio.namespace, "actualizarMedicamento");
                    solicitud.addProperty("paciente", id);
                    solicitud.addProperty("a", a1);
                    solicitud.addProperty("b", b1);
                    solicitud.addProperty("c", c1);
                    solicitud.addProperty("d", d1);
                    solicitud.addProperty("e", e1);
                    solicitud.addProperty("f", f1);
                    solicitud.addProperty("g", g1+":00");
                    solicitud.addProperty("h", oldH);
                    SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    sobre.setOutputSoapObject(solicitud);
                    HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                    transporte.call("http://Servicios/actualizarMedicamento", sobre);
                    SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                    respuesta = resultado.toString();
                }else if(op == 2){
                    SoapObject solicitud = new SoapObject(Inicio.namespace, "desactivarMedicamento");
                    solicitud.addProperty("paciente", id);
                    solicitud.addProperty("h", oldH);
                    SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    sobre.setOutputSoapObject(solicitud);
                    HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                    transporte.call("http://Servicios/desactivarMedicamento", sobre);
                    SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                    respuesta = resultado.toString();
                }else {
                    SoapObject solicitud = new SoapObject(Inicio.namespace, "crearMedicamento");
                    solicitud.addProperty("paciente", id);
                    solicitud.addProperty("a", a1);
                    solicitud.addProperty("b", b1);
                    solicitud.addProperty("c", c1);
                    solicitud.addProperty("d", d1);
                    solicitud.addProperty("e", e1);
                    solicitud.addProperty("f", f1);
                    solicitud.addProperty("g", g1+":00");
                    SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    sobre.setOutputSoapObject(solicitud);
                    HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                    transporte.call("http://Servicios/crearMedicamento", sobre);
                    SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                    respuesta = resultado.toString();
                }
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
        Intent intento = new Intent(this, Medicamentos.class);
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
