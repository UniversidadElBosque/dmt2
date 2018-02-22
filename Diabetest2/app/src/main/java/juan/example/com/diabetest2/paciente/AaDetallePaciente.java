package juan.example.com.diabetest2.paciente;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import juan.example.com.diabetest2.R;

// Autor: Juan David Velásquez Bedoya

public class AaDetallePaciente extends ArrayAdapter {

    private Activity contextoFinal;
    private String[] preguntas;
    private String[] respuestas;

    //Constructor
    public AaDetallePaciente(Activity actividadTraida, String[] preguntasTraidas, String[] respuestasTraidos) {
        super(actividadTraida, R.layout.plantilla_detalle_paciente, preguntasTraidas);
        this.contextoFinal = actividadTraida;
        this.preguntas = preguntasTraidas;
        this.respuestas = respuestasTraidos;
    }

    @Override
    public View getView(int laPosicion, View nuevaVista, ViewGroup parent) {
        LayoutInflater inflado = contextoFinal.getLayoutInflater();
        nuevaVista = inflado.inflate(R.layout.plantilla_detalle_paciente, null, true);

//Captura de los widgets
        TextView tvpreguntas = (TextView) nuevaVista.findViewById(R.id.id_pregunta);
        TextView tvrespuestas = (TextView) nuevaVista.findViewById(R.id.id_respuesta);
//Reemplazo con los traidos en el constructor
        tvpreguntas.setText(preguntas[laPosicion]);
        tvrespuestas.setText(respuestas[laPosicion]);

        return nuevaVista;
    }
}
