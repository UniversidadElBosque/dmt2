package juan.example.com.diabetest2.administrador;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import juan.example.com.diabetest2.R;

// Autor: Juan David Velásquez Bedoya

public class Administracion extends AppCompatActivity {

    Context x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administracion);

        x = this;
        Administracion.consultar co = new Administracion.consultar();
        co.execute();
    }

    public void CrearProfesional(View v) {
        Intent intento = new Intent(this, CrearProfesional.class);
        this.finish();
        startActivity(intento);
    }

    ListView listaP;
    Vector listadoX = new Vector();
    Vector usuarios = new Vector();
    Vector id_usuario = new Vector();

    private class consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "usuariosAdmin");
                solicitud.addProperty("op", "consultar");
                solicitud.addProperty("id", 0);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/usuariosAdmin", sobre);
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
                        usuarios.add(listadoX.subList(i, i + 3));
                        i = i + 4;
                    }
                    i = 0;
                    while (i < listadoX.size()) {
                        id_usuario.add(listadoX.get(i));
                        i = i + 4;
                    } //Vector indice coincidente con ListView, id_destinatario del Paciente
                    //Llenado del listView
                    ArrayAdapter aa = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, usuarios);
                    listaP = (ListView) findViewById(R.id.id_lista_usuarios);
                    listaP.setAdapter(aa);

                    listaP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int elegido, long id) {
                            victima = Integer.parseInt((String) id_usuario.get(elegido));
                            cuadroAlerta();
                        }
                    });
                }
            }
        }
    }



    int victima;
    Vector respuesta;
    private class borrar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "usuariosAdmin");
                solicitud.addProperty("op", "borrar");
                solicitud.addProperty("id", victima);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/usuariosAdmin", sobre);
                respuesta = (Vector) sobre.getResponse();
            } catch (Exception e) {}
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                Toast.makeText(getBaseContext(), "Usuario borrado!", Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void cuadroAlerta() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Atención se borrará el usuario!");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Administracion.borrar bo = new Administracion.borrar();
                        bo.execute();
                        Intent intento = new Intent(getApplicationContext(), Administracion.class);
                        finish();
                        startActivity(intento);
                    }
                });

        alertDialogBuilder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



    public void salir(View v){
        try {
            Administracion.CerrarSesion cs = new Administracion.CerrarSesion();
            cs.execute();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------
    //Cerrar sesiones en el servidor
    private class CerrarSesion extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "cerrarSesion");
                solicitud.addProperty("id", ServicioDT2.idLocal);
                solicitud.addProperty("clave", Inicio.b);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/cerrarSesion", sobre);
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
            } catch (Exception e) {}
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
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
                    //Cierre
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                    System.exit(0);
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
