package juan.example.com.diabetest2.comunes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;

import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.profesional.MenuProfesional;
import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.ServicioDT2;

// Autor: Juan David Velásquez Bedoya

public class CrearRecurso extends AppCompatActivity {


    Button bt1, bt2;
    ImageView vi;
    private static final int tomarFoto = 1, imagenLeida = 2;
    EditText titulo, mensaje, link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_recurso);
        Inicio.tiempoInicial();

        temporal = (TextView) findViewById(R.id.textView30);

        vi = (ImageView) findViewById(R.id.visorImagen);
        titulo = (EditText) findViewById(R.id.idTituloRecurso);
        mensaje = (EditText) findViewById(R.id.idMensajeRecurso);
        link =(EditText) findViewById(R.id.idLinkRecurso);

        bt1 = (Button) findViewById(R.id.btAgregarImagenVideo);
        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ci = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(ci, tomarFoto);
            }
        });
        bt2 = (Button) findViewById(R.id.btAgregarImagenVideo);
        bt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Seleccionar imagen"), imagenLeida);
            }
        });
    }

    static Uri x;
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == tomarFoto && resultCode == Activity.RESULT_OK) {
            Uri imageUri = data.getData();
            vi.setImageURI(imageUri);
        }
        else if (requestCode == imagenLeida && resultCode == RESULT_OK && null != data) {
            x = data.getData();
            try {
                //Paso de uri a bitmap y cambio de tamaño
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), x);
                Bitmap imagen = Bitmap.createScaledBitmap(bitmap, 500, 400, true);
                bos = new ByteArrayOutputStream();
                imagen.compress(Bitmap.CompressFormat.JPEG, 75, bos);
                bitmapdata = bos.toByteArray();
            }catch (Exception e){e.printStackTrace();}
            vi.setImageURI(x);
        }
    }


    public String rutaRealDeURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    byte[] bitmapdata;
    ByteArrayOutputStream bos;
    TextView temporal;
    int tipo;
    String a1, b1, c1, imagen;
    byte[] arreglo;

    public void procesar (View v) throws Exception {
        a1 = titulo.getText().toString();
        b1 = mensaje.getText().toString();
        c1 = link.getText().toString();
        if (x != null) {
            tipo = 1;
            //Convertir byte[] a string
            imagen = Base64.encodeToString(bitmapdata, Base64.DEFAULT);
            Log.e("IMFORMACIÓN MIDT2: ","Imagen recibida de archivo: "+ bitmapdata.length);
            Log.e("IMFORMACIÓN MIDT2: ","String cargado: "+ imagen.length());
            //SOLUCIÓN ANTERIOR QUE FALLA EN RED MOVIL
            /*
            File archivo = new File(rutaRealDeURI(x));
            // De FileInputStream a Arreglo de byte
            try {
                FileInputStream paquete = new FileInputStream(archivo);
                arreglo = new byte[(int) archivo.length()];
                paquete.read(arreglo);
            } catch (IOException ex) {
                Logger.getLogger(CrearRecurso.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Convertir byte[] a string
            imagen = Base64.encodeToString(arreglo, Base64.DEFAULT);
            */
        }
        /*
        //GUARDAR EN LA SD UN ARCHIVO TRAIDO CON FILE DE UNA RUTA REAL DE UNA URI
        try {
            paquete = new FileInputStream(archivo);
            InputStream is = paquete;
            OutputStream salida = new FileOutputStream("/sdcard/nI1.jpg");
            byte[] arreglo = new byte[1024];
            int largo;
            while ((largo = is.read(arreglo)) > 0) {
                salida.write(arreglo, 0, largo);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CrearRecurso.class.getName()).log(Level.SEVERE, null, ex);
        }
        */ //Toast.makeText(getApplicationContext(), "Resultado:" + paquete.getClass().getName(), Toast.LENGTH_LONG).show();
        Toast.makeText(CrearRecurso.this, "Cargando recurso, por favor espere", Toast.LENGTH_SHORT).show();
        CrearRecurso.Consultar co = new CrearRecurso.Consultar();
        co.execute();
    }

    String respuesta = "";
    private class Consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "crearRecurso");
                solicitud.addProperty("titulo", a1);
                solicitud.addProperty("mensaje", b1);
                solicitud.addProperty("recurso", imagen);
                solicitud.addProperty("link", c1);
                solicitud.addProperty("autor", ServicioDT2.idLocal);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/crearRecurso", sobre);
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                respuesta = resultado.toString();
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
        Intent intento = new Intent(this, MenuProfesional.class);
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
