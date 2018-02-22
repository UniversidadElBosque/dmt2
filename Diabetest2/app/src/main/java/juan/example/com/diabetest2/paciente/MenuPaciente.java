package juan.example.com.diabetest2.paciente;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.comunes.Comunidad;
import juan.example.com.diabetest2.comunes.HabitosSaludables;
import juan.example.com.diabetest2.comunes.Informacion;
import juan.example.com.diabetest2.comunes.Mensajes;
import juan.example.com.diabetest2.comunes.Recursos;
import juan.example.com.diabetest2.administrador.ServicioDT2;


// Autor: Juan David Velásquez Bedoya

public class MenuPaciente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_paciente);

        if(probarInternet() == false){
            Toast.makeText(this, "No hay conexión a internet, muchas funciones no estarán disponibles", Toast.LENGTH_SHORT).show();
        }
        //--------------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
    }

//Menú escondido
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_paciente, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.id_boton_salir) {

            MenuPaciente.CerrarSesion cs = new MenuPaciente.CerrarSesion();
            cs.execute();

            return true;
        }
        //Segunda opción del menu  -----------------------------------------------------------------
        if (id == R.id.id_invitar_familiar) {
            if(probarInternet() == false){
                Toast.makeText(getApplicationContext(), "No hay conexión a internet", Toast.LENGTH_SHORT).show();
            } else{ invitarFamiliar(null); }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------

    // Cuadro advertencia familiar
public void invitarFamiliar(View v) {
    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
    alerta.setMessage("Invitar a un familiar, permite que alguien de su confianza pueda observar las recomendaciones que usted recibe de su psicólogo/a y participar en el proceso del apoyo a su enfermedad, para esto solo debe ingresar la identificación y correo de esa persona a continuación.");
    alerta.setPositiveButton("Continuar",
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    ingresarIdFamiliar(null);
                }
            });

    alerta.setNegativeButton("Cancelar",
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    //Código de Cierre
                }
            });

    AlertDialog alertDialog = alerta.create();
    alertDialog.show();
}
// Ingreso del id del familiar
public void ingresarIdFamiliar(View v){
    // input del correo
    final AlertDialog.Builder alerta = new AlertDialog.Builder(this);
    alerta.setMessage("Ingrese la identificación de la persona");
    final EditText idFamiliar = new EditText(this);
    alerta.setView(idFamiliar);
    alerta.setPositiveButton("Invitar", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            b = idFamiliar.getText().toString().trim();
            ingresarCorreoFamiliar(null);

        }
    });
    alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            dialog.cancel();
        }
    });
    alerta.show();
}
public void ingresarCorreoFamiliar(View v){
        // input del correo
        final AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage("Ingrese el correo para poder notificarle");

        final EditText entrada = new EditText(this);

        alerta.setView(entrada);
        alerta.setPositiveButton("Invitar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                a = entrada.getText().toString().trim();

                //Envio del correo de notificación
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{a});
                i.putExtra(Intent.EXTRA_SUBJECT, "Invitación para apoyar en el cuidado de la diabetes tipo 2");
                i.putExtra(Intent.EXTRA_TEXT   , "Reciba un cordial saludo, usted recibe este mensaje debido a que ha sido escogido para apoyar el proceso del cuidado de una persona que padece diabetes mellitus tipo 2, para ello ha sido invitado a participar y conocer el tratamiento que esta recibiendo y poder ayudar en los cuidados necesarios para el paciente, solo debe descargar la App MiDT2 con psicoeducación, escribir su correo en el campo de usuario y como clave temporal sera: 123  Muchas gracias por su participación y apoyo, \n La App, se puede descargar aquí:\nhttps://drive.google.com/drive/folders/0B2iBPDx1zU9vZnQzMmg3S2daaWM?usp=sharing\n\nCordialmente\n\n  Equipo de investigación de los programas de Ingeniería de Sistemas y Psicología Universidad El Bosque");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "¡No hay cliente de correo!, el paciente no sabrá cómo entrar.", Toast.LENGTH_SHORT).show();
                }
                MenuPaciente.CrearFamiliar co = new MenuPaciente.CrearFamiliar();
                co.execute();
            }
        });
        alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alerta.show();
    }

//Crear Familiar---------------------------------------------------------------------------------------
    static String paciente;
    String respuesta;
    String a, b;

    private class CrearFamiliar extends AsyncTask<Void, Void, Boolean> {
    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            SoapObject solicitud = new SoapObject(Inicio.namespace, "crearFamiliar");
            solicitud.addProperty("correo", a);
            solicitud.addProperty("identificacion", b); //del familiar
            solicitud.addProperty("id", ServicioDT2.idLocal);  // del paciente
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.setOutputSoapObject(solicitud);
            HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
            transporte.call("http://Servicios/crearPaciente", sobre);
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
            if(respuesta.contains("existe")){
                Toast.makeText(getApplicationContext(), ""+respuesta, Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(), respuesta, Toast.LENGTH_LONG).show();
            }
        }
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

    //Chequear conexion a internet  ----------------------------------
    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // Enlaces
    public void recursos(View v) { Intent intento = new Intent(this, Recursos.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }
    public void Evolucion(View v) { Intent intento = new Intent(this, Evolucion.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }
    public void foro(View v) { Intent intento = new Intent(this, Comunidad.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }
    public void mensajes(View v) { Intent intento = new Intent(this, Mensajes.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }
    public void informacion(View v) { Intent intento = new Intent(this, Informacion.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }
    public void abrirHabitos(View v) {Intent intento = new Intent(this, HabitosSaludables.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }
    public void abrirMedicamentos(View v) {Intent intento = new Intent(this, Medicamentos.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }
    public void abrirMetas(View v) {Intent intento = new Intent(this, Metas.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }

}
