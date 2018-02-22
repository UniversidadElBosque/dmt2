package juan.example.com.diabetest2.comunes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import juan.example.com.diabetest2.R;

// Autor: Juan David Velásquez Bedoya

public class VerInformacion extends AppCompatActivity {

    static String titulo, descripcion, contenido;
    TextView a,b,c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_informacion);

        a = (TextView) findViewById(R.id.id_titulo_informacion2);
        b = (TextView) findViewById(R.id.id_descripcion_informacion2);
        c = (TextView) findViewById(R.id.id_contenido_informacion2);

        a.setText(titulo);
        b.setText(descripcion);
        c.setText(contenido);

    }
}
