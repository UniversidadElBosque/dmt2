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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;

import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.profesional.MenuProfesional;
import juan.example.com.diabetest2.R;

public class CrearHabito extends AppCompatActivity {

    Button bt1, bt2;
    ImageView vi;
    private static final int tomarFoto = 1, imagenLeida = 2;
    EditText titulo, descripcion, contenido;
    static int op = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_habito);

        vi = (ImageView) findViewById(R.id.visorImagen2);
        titulo = (EditText) findViewById(R.id.id_titulo_habito2);
        descripcion = (EditText) findViewById(R.id.id_descripcion_habito2);
        contenido =(EditText) findViewById(R.id.id_contenido_habito2);

        bt1 = (Button) findViewById(R.id.btAgregarImagen);
        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ci = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(ci, tomarFoto);
            }
        });
        bt2 = (Button) findViewById(R.id.btAgregarImagen);
        bt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Seleccionar imagen"), imagenLeida);
            }
        });
        if(op==1){
            titulo.setText(VerHabito.titulo);
            descripcion.setText(VerHabito.descripcion);
            contenido.setText(VerHabito.contenido);
            Picasso.with(this).load(VerHabito.imagen).fit().noFade().into(vi);
        }

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
    int tipo;
    String a1, b1, c1, imagen;

    public void procesar (View v) throws Exception {
        a1 = titulo.getText().toString();
        b1 = descripcion.getText().toString();
        c1 = contenido.getText().toString();
        if (x != null) {
            tipo = 1;
            //Convertir byte[] a string
            imagen = Base64.encodeToString(bitmapdata, Base64.DEFAULT);
            Log.e("IMFORMACIÓN MIDT2: ", "Imagen recibida de archivo: " + bitmapdata.length);
            Log.e("IMFORMACIÓN MIDT2: ", "String cargado: " + imagen.length());
        }
        Toast.makeText(CrearHabito.this, "Cargando... por favor espere", Toast.LENGTH_SHORT).show();
        CrearHabito.Consultar co = new CrearHabito.Consultar();
        co.execute();
    }

    String respuesta = "";
    private class Consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                if(op==1){
                    SoapObject solicitud = new SoapObject(Inicio.namespace, "modificarHabito");
                    solicitud.addProperty("titulo", a1);
                    solicitud.addProperty("descripcion", b1);
                    solicitud.addProperty("contenido", c1);
                    solicitud.addProperty("imagen", imagen);
                    solicitud.addProperty("idfila", VerHabito.idfila);
                    solicitud.addProperty("imagenvieja", VerHabito.imagen.substring(VerHabito.imagen.length() -13));
                    SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    sobre.setOutputSoapObject(solicitud);
                    HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                    transporte.call("http://Servicios/modificarHabito", sobre);
                    SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                    respuesta = resultado.toString();
                }else {
                    SoapObject solicitud = new SoapObject(Inicio.namespace, "crearHabito");
                    solicitud.addProperty("titulo", a1);
                    solicitud.addProperty("descripcion", b1);
                    solicitud.addProperty("contenido", c1);
                    solicitud.addProperty("imagen", imagen);
                    SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    sobre.setOutputSoapObject(solicitud);
                    HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                    transporte.call("http://Servicios/crearHabito", sobre);
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
        Intent intento = new Intent(this, HabitosSaludables.class);
        if(op==1){
            intento = new Intent(this, MenuProfesional.class);
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
