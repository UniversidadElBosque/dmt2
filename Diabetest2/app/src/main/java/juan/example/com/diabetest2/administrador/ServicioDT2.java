package juan.example.com.diabetest2.administrador;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.comunes.CrearRecurso;

// Autor: Juan David Velásquez Bedoya

public class ServicioDT2 extends Service {

    private static final String TAG = "Atención";
    public static Context ctx;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate(){
        super.onCreate();
    }
    public void onStart(Intent intent, int startId){
        Log.i(TAG,"*********El servicio MiDT2 a comenzado**********");
        persistirID();
        chequeador();
        //this.stopSelf();  //En caso de querer acabarlo al terminar algo
    }
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG,"Servicio MiDT2 Terminado");
    }

//----------------------------------------------------------------------------
    public void chequeador(){
        Timer ti = new Timer();
        tarea tt = new tarea();
        ti.scheduleAtFixedRate(tt,0,60000*4); // Caso de iniciar en 0, reperir cada X minutos
    }

    class tarea extends TimerTask{
        @Override
        public void run(){

            if(probarInternet() == true){
                consultar co = new consultar();
                co.execute();
            }
        }
    }
//----------------------------------------------------------------------------
    //Chequear conexion a internet
    public Boolean probarInternet() {
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int valor = p.waitFor();
            boolean alcanzable = (valor == 0);
            return alcanzable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
//-----------------------------------------------------------------------------

    public void persistirID() {
        Log.i(TAG, "Id que llega de la bd: " + idLocal);
        if (idLocal != null) {
            try {
    /*
            OutputStream salida = new FileOutputStream("/sdcard/id.dt2");
            byte[] arreglo = String.valueOf(Inicio.id).getBytes();
            salida.write(arreglo);
    */
                //FileOutputStream salida = openFileOutput("id.dt2", Context.MODE_PRIVATE);

                FileOutputStream salida = new FileOutputStream(new File(getFilesDir(), "id.dt2"));
                ObjectOutputStream oos = new ObjectOutputStream(salida);
                oos.writeObject(idLocal);
                oos.close();

                FileOutputStream salida2 = new FileOutputStream(new File(getFilesDir(), "correo.dt2"));
                ObjectOutputStream oos2 = new ObjectOutputStream(salida2);
                oos2.writeObject(Inicio.a);
                oos2.close();

                FileOutputStream salida3 = new FileOutputStream(new File(getFilesDir(), "clave.dt2"));
                ObjectOutputStream oos3 = new ObjectOutputStream(salida3);
                oos3.writeObject(Inicio.b);
                oos3.close();


            } catch (java.io.IOException ex) {
                Logger.getLogger(CrearRecurso.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    public void leerID()  {
        try {
    /*
            File archivo = new File("/sdcard/id.dt2");
            FileInputStream paquete = new FileInputStream(archivo);
            //ObjectInputStream miArreglo = new ObjectInputStream(paquete);
            byte[] arreglo = new byte[(int) archivo.length()];
            paquete.read(arreglo);
            idLocal = Long.valueOf(arreglo.);

     */
            // DESERIALIZANDO OBJETOS SIMPLES
            FileInputStream in = openFileInput("id.dt2");
            ObjectInputStream ois = new ObjectInputStream(in);
            Object valor = ois.readObject();
            ois.close( );
            Log.i(TAG,"Id obtenido de archivo: "+valor);
            idLocal = Long.valueOf(valor.toString().trim());

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
        }
    }



//Consulta de notificaciones
static String respuesta = "";
    public static Long idLocal;
    public static String nombreEspacio = Inicio.namespace;
    public static String url = Inicio.url;
    private class consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                leerID();
                //--------------- verificacion de variables
                Log.i(TAG, String.valueOf(idLocal));
                Log.i(TAG,nombreEspacio);
                Log.i(TAG,url);
                //------------------
                SoapObject solicitud = new SoapObject(nombreEspacio, "notificador");
                solicitud.addProperty("id", idLocal);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(url);
                transporte.call("http://Servicios/notificador", sobre);
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                respuesta = resultado.toString();
            } catch (Exception e) {}
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                Log.i(TAG,"codigo de notificación obtenido: "+respuesta);
                if(respuesta.contains("1")) {
                    notificacion();
                }else if(respuesta.contains("2")) {
                    notificacion2();
                }else if(respuesta.contains("3")) {
                    notificacion3();
                }
            }
        }
    }


    //Notificacion mensaje
    int notificationID = 1;
    protected void notificacion(){
        Intent i = new Intent(this, Inicio.class);
        i.putExtra("notificationID", notificationID);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        CharSequence ticker ="Mensaje nuevo de MiDT2";
        CharSequence contentTitle = "Mensaje de MiDT2";
        CharSequence contentText = "Entra ahora para ver el mensaje!";
        Notification noti = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.user_comment)
                .addAction(R.drawable.comment, ticker, pendingIntent)
                .setVibrate(new long[] {100, 250, 100, 500})
                .build();
        nm.notify(notificationID, noti);
    }
//-----------------------------------------------------------------------------

    //Notificacion recurso
    protected void notificacion2(){
        Intent i = new Intent(this, Inicio.class);
        i.putExtra("notificationID", notificationID);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        CharSequence ticker ="Hay un nuevo recurso in MiDT2";
        CharSequence contentTitle = "Nuevo recurso de apoyo en MiDT2";
        CharSequence contentText = "Entra ahora para conocerlo!";
        Notification noti = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.user_comment)
                .addAction(R.drawable.comment, ticker, pendingIntent)
                .setVibrate(new long[] {100, 250, 100, 500})
                .build();
        nm.notify(notificationID, noti);
    }
//-----------------------------------------------------------------------------
//Notificacion recordatorio
    protected void notificacion3(){
        Intent i = new Intent(this, Inicio.class);
        i.putExtra("notificationID", notificationID);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        CharSequence ticker ="Mensaje nuevo de MiDT2";
        CharSequence contentTitle = "Mensaje de MiDT2";
        CharSequence contentText = "Entra ahora para ver el mensaje!";
        Notification noti = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.user_comment)
                .addAction(R.drawable.comment, ticker, pendingIntent)
                .setVibrate(new long[] {100, 250, 100, 500})
                .build();
        nm.notify(notificationID, noti);
    }
//-----------------------------------------------------------------------------




}
