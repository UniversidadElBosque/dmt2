package juan.example.com.diabetest2.comunes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import juan.example.com.diabetest2.R;

// Autor Juan David Velásquez Bedoya

public class AaHabitos extends ArrayAdapter {

    private Activity contextoFinal;
    private String[] imageUrls;
    private String[] titulos;
    private String[] descripciones;

//Constructor
    public AaHabitos(Activity actividadTraida, String[] urlTraido, String[] titulosTraidos, String[] descripcionesTraidas) {
        super(actividadTraida, R.layout.plantilla_recursos, urlTraido);
        this.contextoFinal = actividadTraida;
        this.imageUrls = urlTraido;
        this.titulos = titulosTraidos;
        this.descripciones = descripcionesTraidas;
    }

    @Override
    public View getView(int laPosicion, View nuevaVista, ViewGroup parent) {
            LayoutInflater inflado = contextoFinal.getLayoutInflater();
            nuevaVista = inflado.inflate(R.layout.plantilla_recursos, null, true);

//Captura de los widgets
        TextView titulo = (TextView) nuevaVista.findViewById(R.id.t1);
        ImageView imagen = (ImageView) nuevaVista.findViewById(R.id.id_im2);
        TextView descripcion = (TextView) nuevaVista.findViewById(R.id.t2);
//Reemplazo con los traidos en el constructor

        //----------
        Picasso.with(contextoFinal).load(imageUrls[laPosicion]).fit().noFade().into(imagen);
        titulo.setText(titulos[laPosicion]);
        descripcion.setText(descripciones[laPosicion]);


        return nuevaVista;
    }
}
