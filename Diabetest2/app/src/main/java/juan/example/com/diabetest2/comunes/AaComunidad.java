package juan.example.com.diabetest2.comunes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import juan.example.com.diabetest2.R;


// Autor: Juan David Velásquez Bedoya

public class AaComunidad extends ArrayAdapter {

    private Activity contextoFinal;
    private String[] fechas;
    private String[] remitentes;
    private String[] mensajes;

    //Constructor
    public AaComunidad(Activity actividadTraida, String[] fechasTraidas, String[] remitentesTraidos, String[] mensajesTraidos) {
        super(actividadTraida, R.layout.plantilla_comunidad, fechasTraidas);
        this.contextoFinal = actividadTraida;
        this.fechas = fechasTraidas;
        this.remitentes = remitentesTraidos;
        this.mensajes = mensajesTraidos;
    }

    @Override
    public View getView(int laPosicion, View nuevaVista, ViewGroup parent) {
        LayoutInflater inflado = contextoFinal.getLayoutInflater();
        nuevaVista = inflado.inflate(R.layout.plantilla_comunidad, null, true);

//Captura de los widgets
        TextView tvFechas = (TextView) nuevaVista.findViewById(R.id.id_fecha_mensaje);
        TextView tvRemitentes = (TextView) nuevaVista.findViewById(R.id.id_remitente_mensaje);
        TextView tvMensajes = (TextView) nuevaVista.findViewById(R.id.id_mensaje_mensajes);
        TextView tvTemas = (TextView) nuevaVista.findViewById(R.id.id_tema);
//Reemplazo con los traidos en el constructor
        tvFechas.setText(fechas[laPosicion]);
        tvRemitentes.setText(remitentes[laPosicion]);
        tvMensajes.setText(mensajes[laPosicion]);

        return nuevaVista;
    }
}
