package juan.example.com.diabetest2.comunes;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.R;

public class VerHabito extends AppCompatActivity {

    ImageView i;
    TextView ti, descrip, con;
    static String idfila, titulo, descripcion, contenido, imagen;
    Button modificar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_habito);
        i = (ImageView) findViewById(R.id.id_imagen_habito);
        ti = (TextView) findViewById(R.id.id_titulo_habito);
        descrip = (TextView) findViewById(R.id.id_descripcion_habito);
        con = (TextView) findViewById(R.id.id_contenido_habito);
        modificar = (Button) findViewById(R.id.id_bt_modificar_habito);

        Picasso.with(this).load(imagen).fit().noFade().into(i);
        ti.setText(titulo);
        descrip.setText(descripcion);
        con.setText(contenido);

        if (Inicio.rol.contains("paciente") || Inicio.rol.contains("familiar")){
            modificar.setVisibility(View.INVISIBLE);
        }
    }

    public void modificarHabito(View v) {
        Intent intento = new Intent(this, CrearHabito.class);
        CrearHabito.op = 1;
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
