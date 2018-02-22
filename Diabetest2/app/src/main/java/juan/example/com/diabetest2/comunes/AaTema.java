package juan.example.com.diabetest2.comunes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import juan.example.com.diabetest2.R;

// Autor: Juan David Velásquez Bedoya

public class AaTema extends ArrayAdapter {

    private Activity contextoFinal;
    private String[] fechas;
    private String[] temas;

    //Constructor
    public AaTema(Activity actividadTraida, String[] fechasTraidas, String[] temasTraidos) {
        super(actividadTraida, R.layout.plantilla_comunidad, fechasTraidas);
        this.contextoFinal = actividadTraida;
        this.fechas = fechasTraidas;
        this.temas = temasTraidos;
    }

    @Override
    public View getView(int laPosicion, View nuevaVista, ViewGroup parent) {
        LayoutInflater inflado = contextoFinal.getLayoutInflater();
        nuevaVista = inflado.inflate(R.layout.plantilla_tema, null, true);

//Captura de los widgets
        TextView tvFechas = (TextView) nuevaVista.findViewById(R.id.id_fecha_mensaje);
        TextView tvTemas = (TextView) nuevaVista.findViewById(R.id.id_tema);
//Reemplazo con los traidos en el constructor
        tvFechas.setText(fechas[laPosicion]);
        tvTemas.setText(temas[laPosicion]);

        return nuevaVista;
    }
}
