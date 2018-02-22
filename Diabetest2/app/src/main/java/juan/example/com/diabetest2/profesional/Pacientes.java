package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Vector;

import juan.example.com.diabetest2.paciente.Evolucion;
import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.administrador.ServicioDT2;

// Autor: Juan David Velásquez Bedoya

public class Pacientes extends AppCompatActivity {

    ListView listaP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes);

        Pacientes.Consultar co = new Pacientes.Consultar();
        co.execute();
    }


    Vector listadoX = new Vector();
    Vector pacientes = new Vector();
    Vector id_pacientes = new Vector();

    private class Consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "listaPacientes");
                solicitud.addProperty("id", ServicioDT2.idLocal);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/listaPacientes", sobre);
                listadoX = (Vector) sobre.getResponse();
            } catch (Exception e) {}
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                if (listadoX != null) {
                    //Acomodación de la consulta obtenida
                    int i = 0;
                    while (i < listadoX.size()) {
                        pacientes.add(listadoX.get(i+1)+" "+listadoX.get(i+2));
                        i = i + 3;
                    }
                    i = 0;
                    while (i < listadoX.size()) {
                        id_pacientes.add(listadoX.get(i));
                        i = i + 3;
                    } //Vector indice coincidente con ListView, id_destinatario del Paciente
                    //Llenado del listView
                    ArrayAdapter aa = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, pacientes);
                    listaP = (ListView) findViewById(R.id.id_listaPacientes2);
                    listaP.setAdapter(aa);

                    final TextView mititulo = (TextView) findViewById(R.id.id_titulopacientes);
                    listaP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int elegido, long id) {
                            Evolucion.id = (Long.valueOf((String) id_pacientes.get(elegido)));
                            abrir(null);
                        }
                    });
                }
            }
        }
    }

    public void abrir(View v) {
        Intent intento = new Intent(this, Evolucion.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
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
Spinner listaPacientes;
ListView listaMensajes;
int seleccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes);

        //listaPacientes = (Spinner) findViewById(R.id_destinatario.id_listapacientes);
        ArrayList listado = new ArrayList();
        listado.add("paciente 1");
        listado.add("paciente 2");
        listado.add("paciente 3");
        listado.add("paciente 4");
        listado.add("paciente 5");
        listado.add("paciente 6");
        listado.add("paciente 7");
        listado.add("paciente 8");
        listado.add("paciente 9");
        listado.add("paciente 10");
        listado.add("paciente 11");
        listado.add("paciente 12");
        listado.add("paciente 13");
        listado.add("paciente 14");
        listado.add("paciente 15");
        //ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_list_item_1, listado);
        //listaPacientes.setAdapter(aa);

        ArrayAdapter aa2 = new ArrayAdapter(this,android.R.layout.simple_list_item_1, listado);
        listaMensajes = (ListView) findViewById(R.id_destinatario.id_listaPacientes2);
        listaMensajes.setAdapter(aa2);

    }



    public void cuandoClick(View v){
        final TextView mititulo = (TextView) findViewById(R.id_destinatario.id_titulopacientes);

        switch (v.getId()){
            case R.id_destinatario.id_bt_ver: // Id del boton que va a ejecutar la acción
                //mititulo.setText(listaPacientes.getSelectedItem().toString());
                //(Item) arg0.getAdapter().getItem(arg2);
                listaMensajes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id_destinatario) {
                        seleccion = position;
                    }
                });
                mititulo.setText(String.valueOf(seleccion)); //Con el ArrayView


        }
    }
 */