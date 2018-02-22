package juan.example.com.diabetest2.paciente;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Vector;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;

// Autor: Juan David Velásquez Bedoya

public class DetallePaciente extends AppCompatActivity {
    Context ctx;
    Button enviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_paciente);
        ctx = this;
        consultar co = new consultar();
        co.execute();

        enviar = (Button) findViewById(R.id.id_bt_enviar_informe);
        if(Inicio.rol.contains("paciente")){
            enviar.setVisibility(View.INVISIBLE);
        }
        if(Inicio.rol.contains("familiar")){
            enviar.setVisibility(View.INVISIBLE);
        }
    }



    ListView lv,lv2,lv3,lv4,lv5,lv6,lv7,lv8,lv9,lv10,lv11;
    Vector listadoX = new Vector();
    Vector preguntas = new Vector();
    Vector respuestas = new Vector();

    Vector preguntas2 = new Vector();
    Vector respuestas2 = new Vector();

    Vector preguntas3 = new Vector();
    Vector respuestas3 = new Vector();

    Vector preguntas4 = new Vector();
    Vector respuestas4 = new Vector();

    Vector preguntas5 = new Vector();
    Vector respuestas5 = new Vector();

    Vector preguntas6 = new Vector();
    Vector respuestas6 = new Vector();

    Vector preguntas7 = new Vector();
    Vector respuestas7 = new Vector();

    Vector preguntas8 = new Vector();
    Vector respuestas8 = new Vector();

    Vector preguntas9 = new Vector();
    Vector respuestas9 = new Vector();

    Vector preguntas10 = new Vector();
    Vector respuestas10 = new Vector();

    Vector preguntas11 = new Vector();
    Vector respuestas11 = new Vector();
    private class consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "detallePaciente");
                solicitud.addProperty("id", Evolucion.id);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/detallePaciente", sobre);
                listadoX = (Vector) sobre.getResponse();
            } catch (Exception e) {}
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                preguntas.clear();
                respuestas.clear();
                try {
                    if(listadoX != null) {
                        int i = 1;
                        while (i < 16) { //Datos del usuario
                            preguntas.add(listadoX.get(i));
                            respuestas.add(listadoX.get(i + 1));
                            i = i + 2;
                        }
                        while (i < 28) { //Datos di
                            preguntas2.add(listadoX.get(i));
                            respuestas2.add(listadoX.get(i + 1));
                            i = i + 2;
                        }
                        while (i < 48) { //Datos comorbilidad
                            preguntas3.add(listadoX.get(i));
                            respuestas3.add(listadoX.get(i + 1));
                            i = i + 2;
                        }
                        while (i < 66) { //Datos fyc
                            preguntas4.add(listadoX.get(i));
                            respuestas4.add(listadoX.get(i + 1));
                            i = i + 2;
                        }
                        while (i < 84) { //Datos apoyo social
                            preguntas5.add(listadoX.get(i));
                            respuestas5.add(listadoX.get(i + 1));
                            i = i + 2;
                        }
                        while (i < 104) { //Datos dieta y nutrición
                            preguntas6.add(listadoX.get(i));
                            respuestas6.add(listadoX.get(i + 1));
                            i = i + 2;
                        }
                        while (i < 114) { //Actividad física
                            preguntas7.add(listadoX.get(i));
                            respuestas7.add(listadoX.get(i + 1));
                            i = i + 2;
                        }
                        while (i < 124) { //Datos autocuidado
                            preguntas8.add(listadoX.get(i));
                            respuestas8.add(listadoX.get(i + 1));
                            i = i + 2;
                        }
                        while (i < 134) { //Datos autoeficacia
                            preguntas9.add(listadoX.get(i));
                            respuestas9.add(listadoX.get(i + 1));
                            i = i + 2;
                        }
                        while (i < 142) { //Datos estado de ánimo
                            preguntas10.add(listadoX.get(i));
                            respuestas10.add(listadoX.get(i + 1));
                            i = i + 2;
                        }
                        while (i < (listadoX.size()-1)) { //Datos percepción del riesgo
                            preguntas11.add(listadoX.get(i));
                            respuestas11.add(listadoX.get(i + 1));
                            i = i + 2;
                        }
                        //Llenado de los ListView  --------------------------------------------------------------------
                        lv = (ListView) findViewById(R.id.id_datosUsuario);
                        lv.setAdapter(new AaDetallePaciente((Activity) ctx,
                                (String[]) preguntas.toArray(new String[preguntas.size()]),
                                (String[]) respuestas.toArray(new String[respuestas.size()])
                        ));
                        lv2 = (ListView) findViewById(R.id.id_di);
                        lv2.setAdapter(new AaDetallePaciente((Activity) ctx,
                                (String[]) preguntas2.toArray(new String[preguntas.size()]),
                                (String[]) respuestas2.toArray(new String[respuestas.size()])
                        ));
                        lv3 = (ListView) findViewById(R.id.id_comorbilidad);
                        lv3.setAdapter(new AaDetallePaciente((Activity) ctx,
                                (String[]) preguntas3.toArray(new String[preguntas.size()]),
                                (String[]) respuestas3.toArray(new String[respuestas.size()])
                        ));
                        lv4 = (ListView) findViewById(R.id.id_fyc);
                        lv4.setAdapter(new AaDetallePaciente((Activity) ctx,
                                (String[]) preguntas4.toArray(new String[preguntas.size()]),
                                (String[]) respuestas4.toArray(new String[respuestas.size()])
                        ));
                        lv5 = (ListView) findViewById(R.id.id_apoyo_social);
                        lv5.setAdapter(new AaDetallePaciente((Activity) ctx,
                                (String[]) preguntas5.toArray(new String[preguntas.size()]),
                                (String[]) respuestas5.toArray(new String[respuestas.size()])
                        ));
                        lv6 = (ListView) findViewById(R.id.id_dieta_nutricion);
                        lv6.setAdapter(new AaDetallePaciente((Activity) ctx,
                                (String[]) preguntas6.toArray(new String[preguntas.size()]),
                                (String[]) respuestas6.toArray(new String[respuestas.size()])
                        ));
                        lv7 = (ListView) findViewById(R.id.id_actividad_fisica);
                        lv7.setAdapter(new AaDetallePaciente((Activity) ctx,
                                (String[]) preguntas7.toArray(new String[preguntas.size()]),
                                (String[]) respuestas7.toArray(new String[respuestas.size()])
                        ));
                        lv8 = (ListView) findViewById(R.id.id_autocuidado);
                        lv8.setAdapter(new AaDetallePaciente((Activity) ctx,
                                (String[]) preguntas8.toArray(new String[preguntas.size()]),
                                (String[]) respuestas8.toArray(new String[respuestas.size()])
                        ));

                        lv9 = (ListView) findViewById(R.id.id_autoeficacia);
                        lv9.setAdapter(new AaDetallePaciente((Activity) ctx,
                                (String[]) preguntas9.toArray(new String[preguntas.size()]),
                                (String[]) respuestas9.toArray(new String[respuestas.size()])
                        ));
                        lv10 = (ListView) findViewById(R.id.id_estado_animo);
                        lv10.setAdapter(new AaDetallePaciente((Activity) ctx,
                                (String[]) preguntas10.toArray(new String[preguntas.size()]),
                                (String[]) respuestas10.toArray(new String[respuestas.size()])
                        ));
                        lv11 = (ListView) findViewById(R.id.id_percepcion_riesgo);
                        lv11.setAdapter(new AaDetallePaciente((Activity) ctx,
                                (String[]) preguntas11.toArray(new String[preguntas.size()]),
                                (String[]) respuestas11.toArray(new String[respuestas.size()])
                        ));
                        //Selector de item ----------------------------------------------------------------------------------
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //CrearMensaje.idDestino = Long.parseLong((String) id_remitentes.get(position));
                                //CrearMensaje.destinatario = (String) remitentes.get(position);
                            }
                        });
                    }
                }catch (Exception eee){
                    Log.e("Datos del paciente: ","Eror: "+ eee);}

            }
        }
    }
    public void enviarMail(View v){
        //Envio del correo de notificación
        String mensaje = "DATOS DEL USUARIO\nIdentificación: "+Evolucion.id+"\n";
        int i = 1;

        while (i < 16) { //Datos del usuario
            mensaje = (mensaje+ listadoX.get(i).toString());
            mensaje =(mensaje+ listadoX.get(i + 1).toString()+"\n");
            i = i + 2;
        }
        mensaje = mensaje+"\nDIANÓSTICO INCIAL\n";
        while (i < 28) { //Datos di
            mensaje = (mensaje+ listadoX.get(i).toString());
            mensaje =(mensaje+ listadoX.get(i + 1).toString()+"\n");
            i = i + 2;
        }
        mensaje = mensaje+"\nCOMORBILIDAD\n";
        while (i < 48) { //Datos comorbilidad
            mensaje = (mensaje+ listadoX.get(i).toString());
            mensaje =(mensaje+ listadoX.get(i + 1).toString()+"\n");
            i = i + 2;
        }
        mensaje = mensaje+"\nFACTORES DE RIESGO Y CONOCIMIENTO DE LA ENFERMEDAD\n";
        while (i < 66) { //Datos fyc
            mensaje = (mensaje+ listadoX.get(i).toString());
            mensaje =(mensaje+ listadoX.get(i + 1).toString()+"\n");
            i = i + 2;
        }
        mensaje = mensaje+"\nAPOYO SOCIAL\n";
        while (i < 84) { //Datos apoyo social
            mensaje = (mensaje+ listadoX.get(i).toString());
            mensaje =(mensaje+ listadoX.get(i + 1).toString()+"\n");
            i = i + 2;
        }
        mensaje = mensaje+"\nDIETA Y NUTRICIÓN\n";
        while (i < 104) { //Datos dieta y nutrición
            mensaje = (mensaje+ listadoX.get(i).toString());
            mensaje =(mensaje+ listadoX.get(i + 1).toString()+"\n");
            i = i + 2;
        }
        mensaje = mensaje+"\nACTIVIDAD FÍSICA\n";
        while (i < 114) { //Actividad física
            mensaje = (mensaje+ listadoX.get(i).toString());
            mensaje =(mensaje+ listadoX.get(i + 1).toString()+"\n");
            i = i + 2;
        }
        mensaje = mensaje+"\nAUTOCUIDADO\n";
        while (i < 124) { //Datos autocuidado
            mensaje = (mensaje+ listadoX.get(i).toString());
            mensaje =(mensaje+ listadoX.get(i + 1).toString()+"\n");
            i = i + 2;
        }
        mensaje = mensaje+"\nAUTOEFICACIA\n";
        while (i < 134) { //Datos autoeficacia
            mensaje = (mensaje+ listadoX.get(i).toString());
            mensaje =(mensaje+ listadoX.get(i + 1).toString()+"\n");
            i = i + 2;
        }
        mensaje = mensaje+"\nESTADO DE ÁNIMO\n";
        while (i < 142) { //Datos estado de ánimo
            mensaje = (mensaje+ listadoX.get(i).toString());
            mensaje =(mensaje+ listadoX.get(i + 1).toString()+"\n");
            i = i + 2;
        }
        mensaje = mensaje+"\nPERCEPCIÓN DEL RIESGO\n";
        while (i < (listadoX.size()-1)) { //Datos percepción del riesgo
            mensaje = (mensaje+ listadoX.get(i).toString());
            mensaje =(mensaje+ listadoX.get(i + 1).toString()+"\n");
            i = i + 2;
        }

        Intent y = new Intent(Intent.ACTION_SEND);
        y.setType("message/rfc822");
        y.putExtra(Intent.EXTRA_EMAIL, new String[]{"Destinatario/a"});
        y.putExtra(Intent.EXTRA_SUBJECT, "Informe sobre el paciente");
        y.putExtra(Intent.EXTRA_TEXT, ""+mensaje);
        try {
            startActivity(Intent.createChooser(y, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "¡No hay cliente de correo!, el paciente no sabrá cómo entrar.", Toast.LENGTH_SHORT).show();
        }
    }

}
