package juan.example.com.diabetest2.paciente;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Vector;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.administrador.ServicioDT2;

// Autor: Juan David Velásquez Bedoya

public class Medicamentos extends AppCompatActivity {

    Long id = ServicioDT2.idLocal;
    Button z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);

        z = (Button) findViewById(R.id.id_bt_agregar_medicamento);

        //Verificación del rol para cambio del id
        if(Inicio.rol.contains("profesional")){
            id = Evolucion.id;
            z.setVisibility(View.INVISIBLE);
        }

        Medicamentos.Consultar co = new Medicamentos.Consultar();
        co.execute();
    }

    public void crearMedicamento(View v) {
        Intent intento = new Intent(this, CrearMedicamento.class);
        CrearMedicamento.oldA = null;
        startActivity(intento);
    }

    ListView listaP;
    Vector listadoX = new Vector();
    Vector idfilas = new Vector();
    Vector nombres = new Vector();
    Vector tipos = new Vector();
    Vector dosificaciones = new Vector();
    Vector posologias = new Vector();
    Vector recordatorios = new Vector();
    Vector observaciones = new Vector();
    Vector ultimastomas = new Vector();

    private class Consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "consultarMedicamentos");
                solicitud.addProperty("paciente", id);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/consultarMedicamentos", sobre);
                listadoX = (Vector) sobre.getResponse();
            } catch (Exception e) {
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                if (listadoX != null) {
                    //Acomodación de la consulta obtenida
                    int i = 0;
                    while (i < listadoX.size()) {
                        nombres.add(listadoX.get(i));
                        i = i + 8;
                    }
                    i = 0;
                    while (i < listadoX.size()) {
                        tipos.add(listadoX.get(i+1));
                        i = i + 8;
                    }
                    i = 0;
                    while (i < listadoX.size()) {
                        dosificaciones.add(listadoX.get(i+2));
                        i = i + 8;
                    }
                    i = 0;
                    while (i < listadoX.size()) {
                        posologias.add(listadoX.get(i+3));
                        i = i + 8;
                    }
                    i = 0;
                    while (i < listadoX.size()) {
                        recordatorios.add(listadoX.get(i+4));
                        i = i + 8;
                    }
                    i = 0;
                    while (i < listadoX.size()) {
                        observaciones.add(listadoX.get(i+5));
                        i = i + 8;
                    }
                    i = 0;
                    while (i < listadoX.size()) {
                        ultimastomas.add(listadoX.get(i+6));
                        i = i + 8;
                    }
                    i = 0;
                    while (i < listadoX.size()) {
                        idfilas.add(listadoX.get(i+7));
                        i = i + 8;
                    }

                    //Llenado de ListView
                    ArrayAdapter aa = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, nombres);
                    listaP = (ListView) findViewById(R.id.id_lista_medicamentos);
                    listaP.setAdapter(aa);

                    listaP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int elegido, long id) {
                            CrearMedicamento.oldA = (String) nombres.get(elegido);
                            CrearMedicamento.oldB = (String) tipos.get(elegido);
                            CrearMedicamento.oldC = (String) dosificaciones.get(elegido);
                            CrearMedicamento.oldD = (String) posologias.get(elegido);
                            CrearMedicamento.oldE = (String) recordatorios.get(elegido);
                            CrearMedicamento.oldF = (String) observaciones.get(elegido);
                            CrearMedicamento.oldG = (String) ultimastomas.get(elegido);
                            CrearMedicamento.oldH = (String) idfilas.get(elegido);
                            abrirMedicamento(null);
                        }
                    });
                }
            }
        }
    }

    public void abrirMedicamento(View v) {
        Intent intento = new Intent(this, CrearMedicamento.class);
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
