package juan.example.com.diabetest2.comunes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import juan.example.com.diabetest2.R;

// Autor: Juan David Velásquez Bedoya

public class AaInformacion extends ArrayAdapter {

    private Activity contextoFinal;
    private String[] titulos;
    private String[] descripciones;

    //Constructor
    public AaInformacion(Activity actividadTraida, String[] titulosTraidas, String[] descripcionesTraidos) {
        super(actividadTraida, R.layout.plantilla_informacion, titulosTraidas);
        this.contextoFinal = actividadTraida;
        this.titulos = titulosTraidas;
        this.descripciones = descripcionesTraidos;
    }

    @Override
    public View getView(int laPosicion, View nuevaVista, ViewGroup parent) {
        LayoutInflater inflado = contextoFinal.getLayoutInflater();
        nuevaVista = inflado.inflate(R.layout.plantilla_informacion, null, true);

//Captura de los widgets
        TextView tvtitulos = (TextView) nuevaVista.findViewById(R.id.id_titulo_informacion);
        TextView tvdescripciones = (TextView) nuevaVista.findViewById(R.id.id_descripcion_informacion);
//Reemplazo con los traidos en el constructor
        tvtitulos.setText(titulos[laPosicion]);
        tvdescripciones.setText(descripciones[laPosicion]);

        return nuevaVista;
    }
}
