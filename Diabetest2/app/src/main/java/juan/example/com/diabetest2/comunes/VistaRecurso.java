package juan.example.com.diabetest2.comunes;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.R;

public class VistaRecurso extends AppCompatActivity {

    static int idRecurso;
    ImageView i;
    TextView ti, lin, con,fe,au;
    static String titulo, link, contenido, fecha, urlImagen, autor;
    Button modificar,borrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_recurso);

        i = (ImageView) findViewById(R.id.id_imagen_recurso);
        ti = (TextView) findViewById(R.id.id_titulo_recurso_detalle);
        lin = (TextView) findViewById(R.id.id_link_YouTube);
        con = (TextView) findViewById(R.id.id_contenido_recurso);
        fe = (TextView) findViewById(R.id.id_fecha_recurso);
        au = (TextView) findViewById(R.id.id_autor_recurso);
        modificar = (Button) findViewById(R.id.id_morificar_recurso);
        borrar = (Button) findViewById(R.id.id_borrar_recurso);

        Picasso.with(this).load(urlImagen).fit().noFade().into(i);
        ti.setText(titulo);
        lin.setText(link);
        fe.setText(fecha);
        au.setText(autor);
        con.setText(contenido);


        if (Inicio.rol.contains("paciente")){
            modificar.setVisibility(View.INVISIBLE);
            borrar.setVisibility(View.INVISIBLE);
        }

    }

    public void borrar (View view){
        VistaRecurso.borrarDB bo = new VistaRecurso.borrarDB();
        bo.execute();
    }
    String respuesta;
    private class borrarDB extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "recursos");
                solicitud.addProperty("recurso", urlImagen.substring(urlImagen.length() - 13));
                solicitud.addProperty("op", "e");
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/recursos", sobre);
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                respuesta = resultado.toString();
            } catch (Exception e) {   }
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                Toast.makeText(getApplicationContext(), "Recurso borrado!", Toast.LENGTH_SHORT).show();
                abrir(null);
            }
        }
    }

    public void modificar (View view){

    }

    public void abrir(View v) {
        Intent intento = new Intent(this, Recursos.class);
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
