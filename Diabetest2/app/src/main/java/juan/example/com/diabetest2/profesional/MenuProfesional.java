package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import juan.example.com.diabetest2.comunes.Mensajes;
import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.comunes.Comunidad;
import juan.example.com.diabetest2.comunes.CrearRecurso;
import juan.example.com.diabetest2.comunes.HabitosSaludables;
import juan.example.com.diabetest2.comunes.Informacion;
import juan.example.com.diabetest2.comunes.Recursos;
import juan.example.com.diabetest2.administrador.ServicioDT2;

// Autor: Juan David Velásquez Bedoya

public class MenuProfesional extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_profesional);

        if(probarInternet() == false){
            Toast.makeText(this, "No hay conexión a internet, muchas funciones no estarán disponibles", Toast.LENGTH_SHORT).show();
        }
        //--------------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

    }
//------- Cerrar sesión --------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cerrar_sesion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.id_boton_salir) {
                if(probarInternet() == false) {
                    Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        MenuProfesional.CerrarSesion cs = new MenuProfesional.CerrarSesion();
                        cs.execute(); //cerrar sesión en servidor
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            return true;
        }

        return super.onOptionsItemSelected(item);
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

//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------

    public void crear(View v) { Intent intento = new Intent(this, CrearPaciente.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }
    public void ver(View v) { Intent intento = new Intent(this, Pacientes.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }
    public void crearRecurso(View v) {
        Intent intento = new Intent(this, CrearRecurso.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }
    public void recursos(View v) { Intent intento = new Intent(this, Recursos.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }
    public void foro(View v) { Intent intento = new Intent(this, Comunidad.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }
    public void mensajes(View v) {  Intent intento = new Intent(this, Mensajes.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }
    public void informacion(View v) { Intent intento = new Intent(this, Informacion.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }
    public void habitos(View v) {  Intent intento = new Intent(this, HabitosSaludables.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }

    //Salida por retroceso
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            this.finishAffinity();
            int pid = android.os.Process.myPid();
            android.os.Process.killProcess(pid);
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
    }
}
