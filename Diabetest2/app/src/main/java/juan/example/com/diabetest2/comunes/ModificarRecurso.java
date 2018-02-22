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
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.administrador.ServicioDT2;
import juan.example.com.diabetest2.profesional.MenuProfesional;

// Autor: Juan David Velásquez Bedoya

public class ModificarRecurso extends AppCompatActivity {


    Button bt1, bt2;
    ImageView vi;
    private static final int tomarFoto = 1, imagenLeida = 2;
    EditText titulo, mensaje, link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_recurso);

        vi = (ImageView) findViewById(R.id.visorImagen2);
        titulo = (EditText) findViewById(R.id.idTituloRecurso2);
        mensaje = (EditText) findViewById(R.id.idMensajeRecurso2);
        link =(EditText) findViewById(R.id.idLinkRecurso2);

        bt1 = (Button) findViewById(R.id.btAgregarImagenVideo2);
        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ci = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(ci, tomarFoto);
            }
        });
        bt2 = (Button) findViewById(R.id.btAgregarImagenVideo2);
        bt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Seleccionar imagen"), imagenLeida);
            }
        });
//Cambio a datos que llegan de la bd  -----------------------------------------
        Picasso.with(this).load(VerRecurso.urlImagen).fit().noFade().into(vi);
        titulo.setText(VerRecurso.titulo);
        link.setText(VerRecurso.link);
        mensaje.setText(VerRecurso.contenido);
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
        Log.e("IMFORMACIÓN MIDT2: ","Imagen recibida : "+ VerRecurso.urlImagen);
        Log.e("IMFORMACIÓN MIDT2: ","Imagen entregada: "+ VerRecurso.urlImagen.substring(VerRecurso.urlImagen.length() -13));
        if (x != null) {
            tipo = 1;
            //Convertir byte[] a string
            imagen = Base64.encodeToString(bitmapdata, Base64.DEFAULT);
            Toast.makeText(ModificarRecurso.this, "Cargando recurso, por favor espere", Toast.LENGTH_SHORT).show();

            ModificarRecurso.consultar co = new ModificarRecurso.consultar();
            co.execute();
        } else{
            imagen = "0";
            ModificarRecurso.consultar co = new ModificarRecurso.consultar();
            co.execute();
        }
    }

    String respuesta = "";
    private class consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "modificarRecurso");
                solicitud.addProperty("imgenvieja", VerRecurso.urlImagen.substring(VerRecurso.urlImagen.length() -13)); //Este id es el nombre de la imagen como identificador en la bd
                solicitud.addProperty("autor", ServicioDT2.idLocal);
                solicitud.addProperty("titulo", a1);
                solicitud.addProperty("mensaje", b1);
                solicitud.addProperty("link", c1);
                solicitud.addProperty("recurso", imagen);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/modificarRecurso", sobre);
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
