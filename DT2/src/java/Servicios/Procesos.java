package Servicios;

import com.sun.mail.iap.ByteArray;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * @author Juan David Velásquez Bedoya
 * Actualizado en septiembre de 2017 
 */

@WebService(serviceName = "Procesos")
public class Procesos {
    
    //String destinoRecurso = "D:/Java/DT2/web/Imagenes/"; 
    //String destinoRecurso = "A:/Estudio/Programacion/Java/DT2/web/Imagenes/";
    String destinoRecurso = "C:/Users/grupodesarrollo/Documents/NetBeansProjects/DT2/web/Imagenes/";
   
    public static Connection conexionBD()throws Exception{        
        Class.forName("com.mysql.jdbc.Driver");			
	Connection miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/DT2", "root", "");
    return miConexion;     
    }
     
    Hashtable<Long, Integer> sesion = new Hashtable<>();    
    
    @WebMethod(operationName = "acceso")
    public String acceso(@WebParam(name = "correo") String correo, @WebParam(name = "clave") String clave){
       String resultado = "Datos incorrectos";
       String id = "0";       
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();  
            ResultSet rs = st.executeQuery("select identificacion, rol, aceptaterminos from usuario where correo = '"+ correo +"' and clave = '"+ clave +"'");            
            ArrayList <String> tabla = new ArrayList<String>();
            while (rs.next()){
                tabla.add(rs.getString("identificacion"));
                tabla.add(rs.getString("rol"));
                tabla.add(rs.getString("aceptaterminos"));
                id = tabla.get(0);
            }
            resultado = tabla.get(0) + ";" +tabla.get(1) + ";" +tabla.get(2)+";0";            
            //---------------------------------------
            //---------------------------Revisión de la sesión -----------------            
            if(!id.contentEquals("0")){ 
            sesion.put(Long.parseLong(id), 1);                    
            //------------------------------------------------------------------
            //Verficación si ha respondido la encuesta de la App
            int vEncuesta = 0;
            if (tabla.get(1).contains("profesional")){ 
                ResultSet rs9  = st.executeQuery("select completado from avances where identificacion="+id);
                while (rs9.next()){
                    tabla.add(rs9.getString("completado"));
                }
                //-----------------------------------------------
                //Verificacion para responder con la encuesta
                if(Integer.parseInt(tabla.get(3)) == 0){                    
                    String fecha8 = null;
                    ResultSet rs8  = st.executeQuery("SELECT fecha from avances where identificacion = "+id);
                    while (rs8.next()){ fecha8 = rs8.getString("fecha");} //Fecha de la bd 
                    if(verificarFecha(fecha8)>5){ vEncuesta = 1; }
                }
                //Actualización del contador de accesos o entradas
                st.executeUpdate("update avances set entradas = 2 + entradas - 1 where identificacion ="+ id);
            resultado = tabla.get(0) + ";" +tabla.get(1) + ";" +tabla.get(2)+ ";" +vEncuesta;
            }
            
            
            
            //------------------------------------------------------------------            
            //Verficaciones del familiar
            if (tabla.get(1).contains("familiar")){ 
                ResultSet rs10  = st.executeQuery("select completado from avances where identificacion="+id);
                while (rs10.next()){
                    tabla.add(rs10.getString("completado"));
                }
            rs10  = st.executeQuery("select paciente from tratantes where idfamiliar1 ="+id);
                while (rs10.next()){
                    tabla.add(rs10.getString("paciente"));
                }
            resultado = tabla.get(0) + ";" +tabla.get(1) + ";" +tabla.get(2)+ ";" + tabla.get(3)+ ";" + tabla.get(4);
            //Identificación, rol, aceptaterminos,avance,id del paciente familiar
            }    
            
            
            
            //------------------------------------------------------------------
            //Verificaciones del paciente
            if (tabla.get(1).contains("paciente")){ 
                ResultSet rs1  = st.executeQuery("select completado from avances where identificacion = "+ id);
                while (rs1.next()){
                    tabla.add(rs1.getString("completado"));
                }
                //Actualización del contador de accesos o entradas
                st.executeUpdate("update avances set entradas = 2 + entradas - 1 where identificacion ="+ id);
                
                //-----------------------------------------------
                //Verificacion de fecha para el Animo
                ResultSet rs2  = st.executeQuery("SELECT fecha from animo where paciente = "+id+" order by fecha DESC LIMIT 1");
                String fecha = null;
                while (rs2.next()){ fecha = rs2.getString("fecha");} //Fecha de la bd
                int vAnimo = 0;
                if(verificarFecha(fecha)>0){ vAnimo = 1; }  
                
                //-----------------------------------------------
                //Verificacion de fecha quincenal para el peso
                int vPeso = 0;
                String fecha2 = null;
                ResultSet rs3  = st.executeQuery("SELECT fecha from pesoimc where paciente = "+id+" order by fecha DESC LIMIT 1");                
                while (rs3.next()){ fecha2 = rs3.getString("fecha");} //Fecha de la bd 
                if(verificarFecha(fecha2)>14){ vPeso = 1; }
                
                //-----------------------------------------------
                //Verificación a 3 meses
                int trimestral = 0;
                String fecha3 = null;
                ResultSet rs4  = st.executeQuery("SELECT fecha from hba1c where paciente = "+id+" order by fecha DESC LIMIT 1");                
                while (rs4.next()){ fecha3 = rs4.getString("fecha");} //Fecha de la bd 
                if(verificarFecha(fecha2)>89){ trimestral = 1; }
                
            resultado = tabla.get(0) + ";" +tabla.get(1)+";"+tabla.get(2)+";"+tabla.get(3)+";"+vAnimo+";"+vPeso+";"+trimestral;
            //Este resultado es: identificación, rol, consentimiento informado, avance, si pregunta estado animo , preg peso, preg hba1c, id del paciente para el familiar
            } //Cierre analisis paciente                       
            con.close();
        }} catch (Exception e) {}
        return resultado;
    }

    public int verificarFecha(String fechaBD){
        int respuesta = 0;    
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //formateador                    
        Calendar ca = Calendar.getInstance();
        Calendar actual = Calendar.getInstance();
        Date d = new Date();
        try {
            d = sdf.parse(fechaBD);//Se asigna la fecha de la bd a la variable d
            ca.setTime(d);// Se asigna la fecha de la bd a la variable ca
            int a1,a2,f1,f2;
            a1 = ca.get(Calendar.DAY_OF_MONTH); //De la bd
            a2 = actual.get(Calendar.DAY_OF_MONTH);
            f1 = Integer.parseInt(fechaBD.substring(5, 7));//De la bd
            f2 = actual.get(Calendar.MONTH)+1;
            respuesta = (a2-a1)+(f2-f1)*30;
        } catch (ParseException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return respuesta;    
    }
    
    
    
    @WebMethod(operationName = "cerrarSesion")
    public String cerrarSesion(@WebParam(name = "id") Long id, @WebParam(name = "clave") String clave) {
        String respuesta = "No se pudo cerrar";
        try {              
            Connection con = conexionBD();  
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select clave from usuario where identificacion = "+id);
            String c = null;                
            while (rs.next()){  c = rs.getString("clave");  }
            if(clave.contentEquals(c)){
            sesion.put(id, 0);
                //System.out.println("La sesión --- "+id+" --- se ha cerrado");
                respuesta = "Sesión cerrada";
            }
        } catch (Exception ee){}
        return respuesta;
    }
    
    
    
    @WebMethod(operationName = "crearPaciente")
    public String crearPaciente(@WebParam(name = "correo") String correo, @WebParam(name = "identificacion") String identificacion,@WebParam(name = "id") Long id) {
        String respuesta = "Sesión inválida";
        if((sesion.get(id) != null) && (sesion.get(id) == 1 )){
            if(chequearUsuario(Integer.parseInt(identificacion.trim())) == false){        
                try {                        
                    Connection con = conexionBD();            
                    Statement st = con.createStatement();            
                    st.executeUpdate("insert into usuario values('"+correo+"', 'bosque', "+
                            Integer.parseInt(identificacion.trim())+",'paciente','Paciente nuevo', '', 0, '', 0, '', now(),now(), 1 ,0)");               
                    st.executeUpdate("insert into paciente values("+Long.valueOf(identificacion.trim())+", now(),'', '', 0, '', 0, 0)");
                    st.executeUpdate("insert into tratantes values("+Long.valueOf(identificacion.trim())+", "+id+", now(), 0, '', 0, '', 1)");
                    st.executeUpdate("insert into avances values("+Long.valueOf(identificacion.trim())+",now(), 0,0)");
                    st.executeUpdate("insert into animo values("+Long.valueOf(identificacion.trim())+",now(),0)");
                    st.executeUpdate("insert into notificaciones values("+Long.valueOf(identificacion.trim())+",now(),0,0,0)");
                    st.executeUpdate("insert into metas values("+Long.valueOf(identificacion.trim())+",now(),0,0,0,0,0,0,0,0,0,0)");
                    st.executeUpdate("insert into glucosa values("+Long.valueOf(identificacion.trim())+",now(),0)" );
                    con.close();
                } catch (Exception e) { }
                respuesta = " Paciente creado";
            }else{respuesta = "El paciente ya existe";}
        }
        return respuesta;
    }
    
    
 @WebMethod(operationName = "crearFamiliar")
    public String crearFamiliar(@WebParam(name = "correo") String correo, @WebParam(name = "identificacion") String identificacion,@WebParam(name = "id") Long id) {
        String respuesta = "Sesión inválida";
        if((sesion.get(id) != null) && (sesion.get(id) == 1 )){
            if(chequearUsuario(Integer.parseInt(identificacion.trim())) == false){
                try {                        
                    Connection con = conexionBD();            
                    Statement st = con.createStatement();            
                    st.executeUpdate("insert into usuario values('"+correo+"', '123', "+
                            Integer.parseInt(identificacion.trim())+",'familiar','Familiar nuevo', '', 0, '', 0, '', now(),now(), 1 ,0)"); 
                    st.executeUpdate("insert into tratantes values("+id+", 0, now(), "+Long.valueOf(identificacion.trim())+", '', 0, '', 1)");     
                    st.executeUpdate("insert into notificaciones values("+Long.valueOf(identificacion.trim())+",now(),0,0,0)");
                    con.close();
                } catch (Exception e) { }
                respuesta = " Familiar creado";
            }else{respuesta = "El familiar ya existe";}
        }
        return respuesta;
    }

    //Para evitar la doble creación de pacientes, profesionales o familiares
    public boolean chequearUsuario (long id){
        ArrayList <String> tabla = new ArrayList<String>();
        boolean respuesta = false;
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            ResultSet rs = st.executeQuery("select * from usuario where identificacion = "+id);            
            while (rs.next()){
                tabla.add(rs.getString("identificacion"));
            }
            if(tabla.size() > 0){ respuesta = true;}
        } catch (Exception ee){}
    return respuesta;
    }
    
    
    @WebMethod(operationName = "di")
    public String di(@WebParam(name = "id") String id, @WebParam(name = "tension1") String tension1, @WebParam(name = "tension2") String tension2, @WebParam(name = "trigli") String trigli, @WebParam(name = "hba1c") String hba1c, @WebParam(name = "peso") String peso, @WebParam(name = "talla") String talla) {
        String respuesta = "No se pudo completar";
        try {          
            Connection con = conexionBD();            
            Statement st = con.createStatement(); 
            st.executeUpdate("insert into diagnosticoinicial values("+
                    Long.valueOf(id.trim())+
                    ",now(),"+
                    Integer.parseInt(tension1.trim())+","+
                    Integer.parseInt(tension2.trim())+","+
                    Integer.parseInt(trigli.trim())+","+
                    Float.parseFloat(hba1c.trim())+","+
                    Integer.parseInt(peso.trim())+","+
                    Integer.parseInt(talla.trim())+")");
            //st.executeUpdate("insert into diagnosticoinicial values(105,now(),200,80,175,4.7,150,180)");
            //st.executeUpdate("insert into diagnosticoinicial values("+id+", now(), "+tension1+", "+tension2+", "+trigli+", "+hba1c+", "+peso+", "+talla+")");
            System.out.println("enviado sql");
            
            //Calculo del imc  
            double imcTemp= Double.parseDouble(peso.trim())/((Double.parseDouble(talla.trim())*Double.parseDouble(talla.trim()))/10000);
            int imcFinal = (int) Math.round(imcTemp);
            //Inserción en las tablas de seguimiento o evolución histórica del paciente
            st.executeUpdate("insert into pesoimc values("+Long.valueOf(id.trim())+",now(),"+Integer.parseInt(peso.trim())+","+imcFinal+")");
            st.executeUpdate("insert into trigliceridos values("+Long.valueOf(id.trim())+",now(),"+Integer.parseInt(trigli.trim())+")");
            st.executeUpdate("insert into tension values("+Long.valueOf(id.trim())+",now(),"+tension1+","+tension2+")");
            st.executeUpdate("insert into hba1c values("+Long.valueOf(id.trim())+",now(),"+Float.parseFloat(hba1c.trim())+")");
            
            respuesta = "Ingreso correcto";
            con.close();
        } catch (Exception e) { }
        return respuesta;
    }
   

      
    @WebMethod(operationName = "crearComorbilidad")
    public String crearComorbilidad(@WebParam(name = "paciente") String paciente, @WebParam(name = "a") boolean a, @WebParam(name = "b") boolean b, @WebParam(name = "c") boolean c, @WebParam(name = "d") boolean d, @WebParam(name = "e") boolean e, @WebParam(name = "f") boolean f, @WebParam(name = "g") boolean g, @WebParam(name = "h") boolean h, @WebParam(name = "i") boolean i, @WebParam(name = "j") boolean j, @WebParam(name = "idUsuario") Long usuario) {
        String respuesta = "No se ha insertado";
        try {          
            Connection con = conexionBD();            
            Statement st = con.createStatement();            
            st.executeUpdate("insert into comorbilidad values("+Long.parseLong(paciente.trim())+",now(),"+a+","+b+","+c+","+d+", "+e+","+f+","+g+","+h+","+i+","+j+")"); 
            respuesta = "Paciente Ingresado";
            con.close();
            tf(usuario,"crearPaciente"); //Medición de tiempo de uso
        } catch (Exception ee) { }
        return respuesta;
    }

    
    
    
    @WebMethod(operationName = "listaPacientes")
    public ArrayList listaPacientes(@WebParam(name = "id") long id) {
        ArrayList <String> tabla = new ArrayList<String>();
        ArrayList <String> chiviados = new ArrayList<String>();
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            ResultSet rs = st.executeQuery("select nombres, apellidos, identificacion from usuario, paciente, tratantes where usuario.identificacion = paciente.id AND paciente.id = tratantes.paciente and tratantes.idprofesional = "+id);            
            while (rs.next()){
                tabla.add(rs.getString("identificacion"));
                tabla.add(rs.getString("nombres"));
                tabla.add(rs.getString("apellidos"));
                //Validación de pacientes ------------- evitando los paciente incompletos                                      
                ResultSet rs1 = st2.executeQuery("select * from comorbilidad where paciente = "+rs.getString("identificacion"));                    
                //0 o falso si es un id malo y debe entrar
                boolean ignorar = rs1.next();
                if(ignorar == false){                        
                    //st2.executeUpdate("insert into prueba values("+rs.getString("identificacion")+")");/*  
                    st2.executeUpdate("delete from usuario where identificacion ="+rs.getString("identificacion"));
                    st2.executeUpdate("delete from paciente where id ="+rs.getString("identificacion"));
                    st2.executeUpdate("delete from tratantes where paciente ="+rs.getString("identificacion"));
                    st2.executeUpdate("delete from avances where identificacion ="+rs.getString("identificacion"));
                    st2.executeUpdate("delete from animo where paciente ="+rs.getString("identificacion"));
                    st2.executeUpdate("delete from diagnosticoinicial where paciente ="+rs.getString("identificacion"));
                    st2.executeUpdate("delete from pesoimc where paciente ="+rs.getString("identificacion"));
                    st2.executeUpdate("delete from trigliceridos where id ="+rs.getString("identificacion"));
                    st2.executeUpdate("delete from tension where paciente ="+rs.getString("identificacion"));
                    st2.executeUpdate("delete from hba1c where paciente ="+rs.getString("identificacion"));
                    st2.executeUpdate("delete from notificaciones where id ="+rs.getString("identificacion"));
                    st2.executeUpdate("delete from metas where id ="+rs.getString("identificacion"));

                    tabla.remove(tabla.size()-1);
                    tabla.remove(tabla.size()-1);
                    tabla.remove(tabla.size()-1);
                }   
            } 
            con.close();
        } catch (Exception e) {}
        return tabla;
    }

    
    
    
        @WebMethod(operationName = "usuario")
    public String usuario(@WebParam(name = "id") Long id, @WebParam(name = "clave") String clave, @WebParam(name = "nombres") String nombres, 
            @WebParam(name = "apellidos") String apellidos, @WebParam(name = "sexo") String sexo, @WebParam(name = "telefono") String telefono, 
            @WebParam(name = "direccion") String direccion, @WebParam(name = "ciudad") int ciudad, @WebParam(name = "nacimiento") String nacimiento) {
        String respuesta = "Datos incorrectos";
        
        try {       
            Connection con = conexionBD();            
            Statement st = con.createStatement();            
            st.executeUpdate("update usuario set clave='"+clave+"', nombres='"+nombres+"',apellidos='"+apellidos+"',sexo='"+sexo+"',telefono='"+telefono+
                    "',direccion='"+direccion+"',ciudad="+ciudad+", fechaingreso= now(), estado=1 where usuario.identificacion="+id);
            st.executeUpdate("update paciente set fechanacimiento = '"+nacimiento+"' where id = "+id);
            st.executeUpdate("update avances set completado = 1 where identificacion="+id);            
            con.close();            
            respuesta = "Actualización correcta";
        } catch (Exception e) { }
        return respuesta;
    }
    
    @WebMethod(operationName = "usuarioFamiliar")
    public String usuarioFamiliar(@WebParam(name = "id") Long id, @WebParam(name = "clave") String clave, @WebParam(name = "nombres") String nombres, 
            @WebParam(name = "apellidos") String apellidos, @WebParam(name = "sexo") String sexo, @WebParam(name = "telefono") String telefono, 
            @WebParam(name = "direccion") String direccion, @WebParam(name = "ciudad") int ciudad, @WebParam(name = "nacimiento") String nacimiento) {
        String respuesta = "Datos incorrectos";
        
        try {       
            Connection con = conexionBD();            
            Statement st = con.createStatement();            
            st.executeUpdate("update usuario set clave='"+clave+"', nombres='"+nombres+"',apellidos='"+apellidos+"',sexo='"+sexo+"',telefono='"+telefono+
                    "',direccion='"+direccion+"',ciudad="+ciudad+", fechaingreso= now(), estado=1 where usuario.identificacion="+id);
            st.executeUpdate("update usuario set aceptaterminos = 1 where identificacion="+id);            
            con.close();
            respuesta = "Actualización correcta";
        } catch (Exception e) { }
        return respuesta;
    }
    
    
    @WebMethod(operationName = "crearfyc") //Factores y conocimiento de la enfermedad
    public String crearfyc(@WebParam(name = "paciente") long paciente,@WebParam(name = "a") boolean a, @WebParam(name = "b") int b, @WebParam(name = "c") boolean c, @WebParam(name = "d") boolean d, @WebParam(name = "e") int e, @WebParam(name = "f") boolean f, @WebParam(name = "g") boolean g, @WebParam(name = "h") boolean h, @WebParam(name = "i") String i) {
        String respuesta = "No se ha guardado";
        try {          
            Connection con = conexionBD();            
            Statement st = con.createStatement();            
            st.executeUpdate("insert into fyc values("+paciente+", now(),"+a+","+b+","+c+","+d+","+e+","+f+","+g+","+h+",'"+i+"')");
            st.executeUpdate("update avances set completado = 2 where identificacion="+paciente); 
            respuesta = "Datos guardados";
            con.close();
        } catch (Exception ee) { }
        return respuesta;
    }

    
    
    
    @WebMethod(operationName = "apoyoSocial")
    public String apoyoSocial(@WebParam(name = "paciente") long paciente, @WebParam(name = "a") int a, @WebParam(name = "b") int b, @WebParam(name = "c") int c, @WebParam(name = "d") int d, @WebParam(name = "e") int e, @WebParam(name = "f") int f, @WebParam(name = "g") int g, @WebParam(name = "h") int h) {
        String respuesta = "No se ha guardado";
        try {          
            Connection con = conexionBD();            
            Statement st = con.createStatement();            
            st.executeUpdate("insert into apoyosocial values("+paciente+", now(),"+a+","+b+","+c+","+d+","+e+","+f+","+g+","+h+")");
            st.executeUpdate("update avances set completado = 3 where identificacion="+paciente); 
            respuesta = "Datos guardados";
            con.close();
        } catch (Exception ee) { }
        return respuesta;
    }

    
    
    @WebMethod(operationName = "estiloVida")
    public String crearEstiloVida(@WebParam(name = "paciente") long paciente, @WebParam(name = "a") int a, @WebParam(name = "b") int b, @WebParam(name = "c") int c, @WebParam(name = "d") int d, @WebParam(name = "e") int e, @WebParam(name = "f") int f, @WebParam(name = "g") int g, @WebParam(name = "h") int h, @WebParam(name = "i") int i) {
        String respuesta = "No se ha guardado";
        try {          
            Connection con = conexionBD();            
            Statement st = con.createStatement();            
            st.executeUpdate("insert into estilovida values("+paciente+", now(),"+a+","+b+","+c+","+d+","+e+","+f+","+g+","+h+","+i+")");
            st.executeUpdate("update avances set completado = 4 where identificacion="+paciente); 
            respuesta = "Datos guardados";
            con.close();
            tf(paciente,"ingresoPaciente1"); //Tiempo final que se demoró el paciente
        } catch (Exception ee) { }
        return respuesta;
    }

    
    
    
    @WebMethod(operationName = "actividadFisica") //Actividad física y autocuidado
    public String crearActividadFisica(@WebParam(name = "paciente") long paciente, @WebParam(name = "a") int a, @WebParam(name = "b") int b, @WebParam(name = "c") int c, @WebParam(name = "d") int d, @WebParam(name = "e") int e, @WebParam(name = "f") int f, @WebParam(name = "g") int g, @WebParam(name = "h") int h, @WebParam(name = "i") int i) {
        String respuesta = "No se ha guardado";
        try {          
            Connection con = conexionBD();            
            Statement st = con.createStatement();            
            st.executeUpdate("insert into actividadfisica values("+paciente+", now(),"+a+","+b+","+c+","+d+")");
            st.executeUpdate("insert into autocuidado values("+paciente+", now(),"+e+","+f+","+g+","+h+")");
            st.executeUpdate("update avances set completado = 5 where identificacion="+paciente); 
            respuesta = "Datos guardados";
            con.close();
        } catch (Exception ee) { }
        return respuesta;
    }


   
    @WebMethod(operationName = "aep") // Contine a autoeficacia, estado de ánimo, percepción del riesgo
    public String crearAEP(@WebParam(name = "id") long id, @WebParam(name = "a") int a, @WebParam(name = "b") int b, @WebParam(name = "c") int c, @WebParam(name = "d") int d, @WebParam(name = "e") int e, @WebParam(name = "f") int f, @WebParam(name = "g") int g, @WebParam(name = "h") boolean h, @WebParam(name = "i") boolean i, @WebParam(name = "j") boolean j, @WebParam(name = "k") boolean k) {
        String respuesta = "No se pudo guardar";
        try {
            Connection con = conexionBD();            
            Statement st = con.createStatement(); 
            st.executeUpdate("insert into autoeficacia values("+id+",now(),"+a+","+b+","+c+","+d+")");
            st.executeUpdate("insert into estadoanimo values("+id+",now(),"+e+","+f+","+g+")");
            st.executeUpdate("insert into percepcionriesgo values("+id+",now(),"+h+","+i+","+j+","+k+")");
            st.executeUpdate("update avances set completado = 7 where identificacion="+id);
            respuesta = "Datos guardados!";
            con.close();
            tf(id,"ingresoPaciente2"); //Tiempo final que se demoró el paciente
        } catch (Exception ee) { }
        return respuesta;
    }

    

    @WebMethod(operationName = "crearAnimo")
    public String crearAnimo(@WebParam(name = "id") long id, @WebParam(name = "nivel") int nivel) {
        try {
            Connection con = conexionBD();            
            Statement st = con.createStatement(); 
            st.executeUpdate("insert into animo values("+id+",now(),"+nivel+")");
        } catch (Exception e) { }
        return null;
    }
    

    
    @WebMethod(operationName = "crearRecurso")
    public String crearRecurso(@WebParam(name = "autor") String autor, @WebParam(name = "titulo") String titulo, @WebParam(name = "mensaje") String mensaje, @WebParam(name = "recurso") String recurso, @WebParam(name = "link") String video) {  
        try {            
            Calendar c = new GregorianCalendar();
            String nom = Long.toString(c.getTimeInMillis());
            
            Connection con = conexionBD();            
            Statement st = con.createStatement();            
            st.executeUpdate("insert into recursos values(now(),'"+titulo+"','"+mensaje+"','"+nom+"','"+video+"',"+autor+")");
            st.executeUpdate("update notificaciones set recurso = 2 ");
            
            byte[] decodificar = Base64.decodeBase64(recurso.getBytes());             
            
            OutputStream salida = new FileOutputStream(destinoRecurso + nom); 
            salida.write(decodificar);
            salida.close();
            tf(Long.parseLong(autor),"crearRecurso");
        } catch (Exception e) { }
        return null;
    }
    
    
        @WebMethod(operationName = "modificarRecurso")
    public String modificarRecurso(@WebParam(name = "imgenvieja") String imgenvieja, @WebParam(name = "autor") String autor, @WebParam(name = "titulo") String titulo, @WebParam(name = "mensaje") String mensaje, @WebParam(name = "recurso") String recurso, @WebParam(name = "link") String video) {  
        String respuesta = "No se pudo modificar";
        try {            
            Calendar c = new GregorianCalendar();
            String nom = null;
            if(recurso.length() < 5){ //Caso en el que no se cambia la imagen
                nom = imgenvieja;
            } else{
                nom = Long.toString(c.getTimeInMillis());
                byte[] decodificar = Base64.decodeBase64(recurso.getBytes());             
                //OutputStream salida = new FileOutputStream("D:/Java/DT2/web/Imagenes/" + nom); //
                OutputStream salida = new FileOutputStream(destinoRecurso + nom); 
                salida.write(decodificar);
                salida.close();
            }            
            Connection con = conexionBD();            
            Statement st = con.createStatement();            
            st.executeUpdate("update recursos set fecha=now(),titulo='"+titulo+"',mensaje='"+mensaje+"',recurso='"+nom+"',video='"+video+"',responsable="+autor+" where recurso='"+
                    imgenvieja+"'");
            st.executeUpdate("update notificaciones set recurso = 2 ");
            return respuesta = "Recurso actualizado!";
        } catch (Exception e) { }
        return null;
    }
       
    
      
    @WebMethod(operationName = "recursos")
    public ArrayList recursos(@WebParam(name = "recurso") String recurso, @WebParam(name = "op") String op) {
        ArrayList <String> tabla = new ArrayList<String>();
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();  
            ResultSet rs = st.executeQuery("select * from recursos");            
            if(op.contains("c")){    
            while (rs.next()){
                tabla.add(rs.getString("fecha"));
                tabla.add(rs.getString("titulo"));
                tabla.add(rs.getString("mensaje"));
                tabla.add(rs.getString("recurso"));
                tabla.add(rs.getString("video"));       
                String responsable = "";
                    Statement st2 = con.createStatement(); 
                    ResultSet rs2 = st2.executeQuery("select nombres, apellidos from usuario where identificacion = '"+rs.getString("responsable")+"'");
                    while (rs2.next()){                        
                        responsable = rs2.getString("nombres")+" "+rs2.getString("apellidos");
                    }
                tabla.add(responsable);                
            }
            
            
            } else if(op.contains("e")){
                st.executeUpdate("delete from recursos where recurso = "+recurso);
                tabla.add("Recurso eliminado!");
            }            
            con.close();
        } catch (Exception e) {}
        return tabla;
    }      
        
    
    @WebMethod(operationName = "paciente")
    public ArrayList paciente(@WebParam(name = "id") long id, @WebParam(name = "op") int op) {
        ArrayList <String> tabla = new ArrayList<String>();
        try {
            
            Connection con = conexionBD();  
            Statement st = con.createStatement(); 
            ResultSet rs;
            if(op == 0){ // Caso consulta
                rs = st.executeQuery("select * from usuario, paciente where usuario.identificacion = paciente.id and usuario.identificacion ="+id);
                while (rs.next()){
                    tabla.add(rs.getString("correo"));
                    tabla.add(rs.getString("nombres"));
                    tabla.add(rs.getString("apellidos"));                
                    tabla.add(rs.getString("estado"));
                    tabla.add(rs.getString("telefono"));
                    tabla.add(rs.getString("direccion"));
                    tabla.add(rs.getString("fecharegistro"));
                    tabla.add(rs.getString("fechaingreso"));
                    tabla.add(rs.getString("fechanacimiento"));
                    tabla.add(rs.getString("ocupacion"));
                    tabla.add(rs.getString("estadocivil"));
                    tabla.add(rs.getString("hijos"));
                    tabla.add(rs.getString("sexo"));
                    }            
                con.close();
            }else if(op == 1){ // Caso desactivación del paciente
                st.executeUpdate("update usuario set rol = 'pacienteinactivo' where identificacion ="+id);
                st.executeUpdate("update usuario set clave = 'pacienteinactivo' where identificacion ="+id);
                st.executeUpdate("update usuario set identificacion = "+id+1+" where identificacion ="+id);
                con.close();
                tabla.add("Desactivado permanentemente!");
            }
        } catch (Exception e) {}
        return tabla;
    }

    
    @WebMethod(operationName = "consultarPesoImc")
    public ArrayList consultarPesoImc(@WebParam(name = "id") long id) {
        ArrayList <String> tabla = new ArrayList<String>();
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();  
            ResultSet rs = st.executeQuery("select * from pesoimc where paciente ="+id);                    
            while (rs.next()){
                tabla.add(rs.getString("fecha"));
                tabla.add(rs.getString("peso"));
                tabla.add(rs.getString("imc"));
                }            
            con.close();
        } catch (Exception e) {}
        return tabla;
    }

    
    
    @WebMethod(operationName = "consultarAnimo")
    public ArrayList consultarAnimo(@WebParam(name = "id") long id) {
        ArrayList <String> tabla = new ArrayList<String>();
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();  
            ResultSet rs = st.executeQuery("select * from animo where paciente ="+id);                    
            while (rs.next()){
                tabla.add(rs.getString("fecha"));
                tabla.add(rs.getString("animo"));
                }            
            con.close();
        } catch (Exception e) {}
        return tabla;
    }
    
    
    @WebMethod(operationName = "consultarHba1c")
    public ArrayList consultarHba1c(@WebParam(name = "id") long id) {
        ArrayList <String> tabla = new ArrayList<String>();
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();  
            ResultSet rs = st.executeQuery("select * from hba1c where paciente ="+id);                    
            while (rs.next()){
                tabla.add(rs.getString("fecha"));
                tabla.add(rs.getString("hba1c"));
                }            
            con.close();
        } catch (Exception e) {}
        return tabla;
    }

    
    @WebMethod(operationName = "listaMensajes")
    public ArrayList listaMensajes(@WebParam(name = "id") long id) {
        ArrayList <String> tabla = new ArrayList<String>();
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();  
            ResultSet rs = st.executeQuery("SELECT identificacion, fecha, nombres, apellidos, mensaje from usuario, mensajes where usuario.identificacion = mensajes.id and destinatario = "+id+" order by fecha");
            while (rs.next()){
                tabla.add(rs.getString("identificacion"));// del remitente
                tabla.add(rs.getString("fecha")); 
                tabla.add(rs.getString("nombres"));
                tabla.add(rs.getString("apellidos"));                 
                tabla.add(rs.getString("mensaje"));
                }
            st.executeUpdate("update notificaciones set mensaje = 0 where id = "+id);            
            con.close();
        } catch (Exception e) {}
        return tabla;
    }

    
    @WebMethod(operationName = "crearMensaje")
    public String crearMensaje(@WebParam(name = "id") long id, @WebParam(name = "mensaje") String mensaje, @WebParam(name = "destinatario") long destinatario) {
        String respuesta = "No se pudo enviar";
        try {
            Connection con = conexionBD();            
            Statement st = con.createStatement(); 
            st.executeUpdate("insert into mensajes values("+id+","+destinatario+",'"+mensaje+"',now())");
            st.executeUpdate("insert into mensajes values("+id+","+id+",'"+mensaje+"',now())");            
            st.executeUpdate("update notificaciones set mensaje = 1 where id = "+destinatario);
            respuesta = "Mensaje enviado!";
        } catch (Exception e) { }
        return respuesta;
    }

    
    @WebMethod(operationName = "ciudades")
    public ArrayList ciudades() {
        ArrayList <String> tabla = new ArrayList<String>();
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();  
            ResultSet rs = st.executeQuery("SELECT * from ciudad");
            while (rs.next()){
                tabla.add(rs.getString("id"));// del remitente
                tabla.add(rs.getString("nombre")); 
                tabla.add(rs.getString("pais"));
                }            
            con.close();
        } catch (Exception e) {}
        return tabla;
    }

    
    @WebMethod(operationName = "comunidad")
    public ArrayList comunidad() {
        ArrayList <String> tabla = new ArrayList<String>();
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();  
            ResultSet rs = st.executeQuery("SELECT * from comunidad group by tema");
            while (rs.next()){
                tabla.add(rs.getString("fecha"));// del remitente
                tabla.add(rs.getString("tema")); 
                tabla.add(rs.getString("autor"));
                tabla.add(rs.getString("participante"));                 
                tabla.add(rs.getString("mensaje"));
                }            
            con.close();
        } catch (Exception e) {}
        return tabla;
    }

    
    @WebMethod(operationName = "crearComunidad")
    public String crearComunidad(@WebParam(name = "id") long id, @WebParam(name = "tema") String tema, @WebParam(name = "mensaje") String mensaje) {
        String respuesta = "No se pudo publicar";
        try {
            Connection con = conexionBD();            
            Statement st = con.createStatement();
            st.executeUpdate("insert into comunidad values(now(),'"+tema+"',"+id+",0, '"+mensaje+"')");
                respuesta = "Tema creado!";
            
        } catch (Exception e) { }
        return respuesta;
    }

    
    
    
    @WebMethod(operationName = "crearProfesional")
    public String crearProfesional(@WebParam(name = "nombre") String nombre, @WebParam(name = "cedula") long cedula, @WebParam(name = "email") String email, @WebParam(name = "clave") String clave, @WebParam(name = "telefono") long telefono, @WebParam(name = "apellidos") String apellidos, @WebParam(name = "direccion") String direccion, @WebParam(name = "creador") long creador) {
        String respuesta = "Sesión inválida";
        System.out.println("Estado de la sesión: "+sesion.get(creador));
        if((sesion.get(creador) != null) && (sesion.get(creador) == 1 )){
            if(chequearUsuario(cedula) == false){
                respuesta = "No se pudo crear!";
                try {
                    Connection con = conexionBD();            
                    Statement st = con.createStatement();
                    st.executeUpdate("insert into usuario values('"+email+"','"+clave+"',"+cedula+",'profesional','"+
                            nombre+"','"+apellidos+"',1,'N/A','"+telefono+"','"+direccion+"',now(),now(),1,0)");
                    st.executeUpdate("insert into notificaciones values("+cedula+",now(),0,0,0)");
                    st.executeUpdate("insert into avances values("+cedula+",now(), 0,0)");
                    st.executeUpdate("insert into tiemposdeuso values("+cedula+",now(), 0,0,0,0,0)");
                    respuesta = "Profesional creado!";  
                } catch (Exception e) { }
            } else {respuesta = "El profesional ya existe";}
        }
        return respuesta;
    }

    
    @WebMethod(operationName = "usuariosAdmin")
    public ArrayList usuariosAdmin(@WebParam(name = "op") String op,@WebParam(name = "id") long id) {
        ArrayList <String> tabla = new ArrayList<String>();
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();           
            if(op.contains("consultar")){
                 ResultSet rs = st.executeQuery("select identificacion,rol,nombres,apellidos from usuario");            
                while (rs.next()){
                    tabla.add(rs.getString("identificacion"));
                    tabla.add(rs.getString("rol"));
                    tabla.add(rs.getString("nombres"));
                    tabla.add(rs.getString("apellidos"));                       
                }
            }else if(op.contains("borrar")){
                st.executeUpdate("delete from usuario where identificacion ="+id);
                st.executeUpdate("delete from tratantes where paciente ="+id);
                st.executeUpdate("delete from tratantes where idprofesional ="+id);
                st.executeUpdate("delete from tratantes where idfamiliar1 ="+id);
                st.executeUpdate("delete from notificaciones where id ="+id);                
                st.executeUpdate("delete from avances where identificacion ="+id);     
                
                st.executeUpdate("delete from actividadfisica where paciente ="+id);
                st.executeUpdate("delete from animo where paciente ="+id);
                st.executeUpdate("delete from apoyosocial where paciente ="+id);
                st.executeUpdate("delete from autocuidado where paciente ="+id);
                st.executeUpdate("delete from autoeficacia where paciente ="+id);
                st.executeUpdate("delete from comorbilidad where paciente ="+id);                
                st.executeUpdate("delete from diagnosticoinicial where paciente ="+id);
                st.executeUpdate("delete from estadoanimo where paciente ="+id);
                st.executeUpdate("delete from estilovida where paciente ="+id);
                st.executeUpdate("delete from fyc where paciente ="+id);
                st.executeUpdate("delete from hba1c where paciente ="+id);
                st.executeUpdate("delete from medicamento where paciente ="+id);
                st.executeUpdate("delete from mensajes where destinatario ="+id); 
                st.executeUpdate("delete from mensajes where id ="+id); 
                st.executeUpdate("delete from metas where id ="+id);  
                st.executeUpdate("delete from observaciones where paciente ="+id); 
                st.executeUpdate("delete from percepcionriesgo where paciente ="+id);                
                st.executeUpdate("delete from pesoimc where paciente ="+id);
                st.executeUpdate("delete from trigliceridos where id ="+id);
                st.executeUpdate("delete from tension where paciente ="+id);
                st.executeUpdate("delete from tiemposdeuso where usuario ="+id);  
                st.executeUpdate("delete from paciente where id ="+id);
                st.executeUpdate("delete from glucosa where id ="+id);
                
                
            }
            con.close();
        } catch (Exception e) {}
        return tabla;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "notificador")
    public String notificador(@WebParam(name = "id") long id) {
        /*La notificacion: 
        0 = nada
        1 = mensaje
        2 = recurso nuevo
        3 = recordatorio de cualquier otra cosa general
        */
        String respuesta = "0";
        ArrayList <String> tabla = new ArrayList<String>();
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();           
            ResultSet rs = st.executeQuery("select * from notificaciones where id = "+id);            
                while (rs.next()){
                    tabla.add(rs.getString("id"));
                    tabla.add(rs.getString("fecha"));
                    tabla.add(rs.getString("mensaje"));       
                    tabla.add(rs.getString("recurso")); 
                    tabla.add(rs.getString("recordatorio")); 
                }
            
            respuesta = tabla.get(2)+";"+tabla.get(3)+";"+tabla.get(4);
            st.executeUpdate("update notificaciones set mensaje = 0, recurso = 0, recordatorio = 0 where id = "+id);            
            con.close();
        } catch (Exception e) {}        
        return respuesta;
    }

    
    @WebMethod(operationName = "borrarMensajes")
    public String borrarMensajes(@WebParam(name = "id") long id) {
        String respuesta = "No se han borrado";
        try {
            Connection con = conexionBD();            
            Statement st = con.createStatement(); 
            st.executeUpdate("delete from mensajes where destinatario = "+id);
            respuesta = "Mensajes borrados!";
        } catch (Exception e) { }
        return respuesta;
    }

    
    @WebMethod(operationName = "detallePaciente")
    public ArrayList detallePaciente(@WebParam(name = "id") long id) {
        ArrayList <String> tabla = new ArrayList<String>();
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();           
            ResultSet rs = st.executeQuery("select * from usuario where identificacion = "+id);
        while (rs.next()){
            tabla.add("DATOS GENERALES");
            tabla.add("Email: ");
            tabla.add(rs.getString("correo"));
            tabla.add("Nombres: ");
            tabla.add(rs.getString("nombres"));
            tabla.add("Apellidos: ");
            tabla.add(rs.getString("apellidos"));
            tabla.add("Sexo: ");
            tabla.add(rs.getString("sexo"));
            tabla.add("Dirección: ");
            tabla.add(rs.getString("direccion"));
            tabla.add("Teléfono de contacto: ");
            tabla.add(rs.getString("telefono"));
            tabla.add("Fecha de registro: ");  
            tabla.add(rs.getString("fecharegistro"));
            tabla.add("Fecha de ingreso: "); 
            tabla.add(rs.getString("fechaingreso")); 
        }
        //---Consulta en diagnosticoinicial
        rs = st.executeQuery("select * from diagnosticoinicial where paciente = "+id);
        while (rs.next()){
            //tabla.add("DIANÓSTICO INICIAL ");
            tabla.add("Fecha de la información: ");
            tabla.add(rs.getString("fecha"));
            tabla.add("Tensión arterial: ");
            tabla.add(rs.getString("tension1")+"/"+rs.getString("tension2")+" mm Hg");
            tabla.add("Triglicéridos: ");
            tabla.add(rs.getString("trigliceridos"));
            tabla.add("Hemoglobina glicosilada: ");
            tabla.add(rs.getString("hba1c"));
            tabla.add("Peso en kilogramos: ");
            tabla.add(rs.getString("peso"));
            tabla.add("Talla en centímetros: ");
            tabla.add(rs.getString("talla"));
        }  
         //---Consulta en comorbilidad
        rs = st.executeQuery("select * from comorbilidad where paciente = "+id);
        while (rs.next()){
            //tabla.add("COMORBILIDAD Y COMPLICACIONES ");
            tabla.add("Fecha de la información: ");
            tabla.add(rs.getString("fecha"));
            tabla.add("dislipidemia: ");
            tabla.add(rs.getString("dislipidemia"));
            tabla.add("Hipertension: ");
            tabla.add(rs.getString("hipertension"));
            tabla.add("Enfermedad pulmonar: ");
            tabla.add(rs.getString("enfermedadpulmonar"));
            tabla.add("Hipotiroidismo: ");
            tabla.add(rs.getString("hipotiroidismo"));
            tabla.add("Retinopatia: ");
            tabla.add(rs.getString("retinopatia"));
            tabla.add("Neuropatia: ");
            tabla.add(rs.getString("neuropatia"));
            tabla.add("Pie diabetico: ");
            tabla.add(rs.getString("piediabetico"));
            tabla.add("Enfermedad cardiovascular: ");
            tabla.add(rs.getString("enfermedadcardiovascular"));
            tabla.add("Enfermedad cordical oclusiva: ");
            tabla.add(rs.getString("cordicaloclusiva"));
            //---Consulta en fyc
            rs = st.executeQuery("select * from fyc where paciente = "+id);
        while (rs.next()){
            //tabla.add("FACTORES DE RIESGO Y CONOCIMIENTO SOBRE LA ENFERMEDAD ");
            tabla.add("Fecha de la información: ");
            tabla.add(rs.getString("fecha"));
            tabla.add("Es fumador/a: ");
            tabla.add(rs.getString("fuma"));
            tabla.add("Cantidad de cigarrillos mensuales: ");
            tabla.add(rs.getString("cuantosfuma"));
            tabla.add("Conoce las consecuencias: ");
            tabla.add(rs.getString("consecuenciasfumar"));
            tabla.add("Consume alcohol: ");
            tabla.add(rs.getString("alcohol"));
            tabla.add("Conoce las consecuencias de tomar: ");
            tabla.add(rs.getString("consecuenciasalcohol"));
            tabla.add("¿La diabetes representa una enfermedad para toda la vida?: ");
            tabla.add(rs.getString("dt2todavida"));
            tabla.add("¿Se puede controlar con dieta y medicación?: ");
            tabla.add(rs.getString("controlcondym"));
            tabla.add("Cite dos o más órganos que se vean afectados por la elevación de las cifras de glucemia: ");
            tabla.add(rs.getString("organos"));
        }
        //---Consulta en Apoyo Social
        rs = st.executeQuery("select * from apoyosocial where paciente = "+id);
        while (rs.next()){
            //tabla.add("APOYO SOCIAL ");
            tabla.add("Fecha de la información: ");
            tabla.add(rs.getString("fecha"));
            tabla.add("Mi familia me apoya en el proceso de tratamiento: ");
            tabla.add(rs.getString("familiaapoya"));
            tabla.add("Mis amigos me apoyan en el tratamiento: ");
            tabla.add(rs.getString("amigosapoyan"));
            tabla.add("Me siento satisfecho con la manera en que mi familia me apoya en el tratamiento : ");
            tabla.add(rs.getString("satisfechoapoyado"));
            tabla.add("Tengo con quien hablar sobre mi enfermedad: ");
            tabla.add(rs.getString("puedehablar"));
            tabla.add("Me reuno con mis amigos y familiares?: ");
            tabla.add(rs.getString("reunoamigos"));
            tabla.add("Cuento con personas que se preocupan de lo que me sucede: ");
            tabla.add(rs.getString("sepreocupanpormi"));
            tabla.add("Tengo la posibilidad de hablar de mis problemas: ");
            tabla.add(rs.getString("hablaproblemas"));
            tabla.add("Atiendo consejos útiles cuando me ocurre algún acontecimiento importante: ");
            tabla.add(rs.getString("atiendoconsejos"));            
        }  
        //---Consulta en Estilo de Vida - ahora categorizado como DIETA - NUTRICION
        rs = st.executeQuery("select * from estilovida where paciente = "+id);
        while (rs.next()){
            //tabla.add("DIETA Y NUTRICIÓN ");
            tabla.add("Fecha de la información: ");
            tabla.add(rs.getString("fecha"));
            tabla.add("Podría consumir alimentos cada 3 ó 4 horas todos los días, incluyendo el desayuno: ");
            tabla.add(rs.getString("comercada3o4horas"));
            tabla.add("Soy capaz de continuar con mi dieta cuando tengo que preparar o compartir alimentos con personas que no tienen diabetes: ");
            tabla.add(rs.getString("resistetentacion"));
            tabla.add("Me siento seguro de poder escoger los alimentos apropiados para comer: ");
            tabla.add(rs.getString("eligebien"));
            tabla.add("Consumo tres frutas y dos verduras en mi dieta diaria: ");
            tabla.add(rs.getString("frutas3verduras2"));
            tabla.add("Puedo incluir diariamente en mi dieta verduras, frutas, integrales, arroz integral, avena y lácteos bajos en grasa: ");
            tabla.add(rs.getString("comesano"));
            tabla.add("Tengo fuera de mi dieta los paquetes de alimentos procesados (papitas, chitos..): ");
            tabla.add(rs.getString("nofritos"));
            tabla.add("¿Sigue una dieta especializada para el control de su diabetes?: ");
            tabla.add(rs.getString("dietaespecializada"));
            tabla.add("¿Consume más de una cucharadita de sal al día?: ");
            tabla.add(rs.getString("comesal"));
            tabla.add("Consume alimentos ricos en fibra, como granos, avena, arroz integral: ");
            tabla.add(rs.getString("comefibra"));
        } 
        //---Consulta en actividadfisica y autocuidado
        rs = st.executeQuery("select * from actividadfisica where paciente = "+id);
        while (rs.next()){
            //tabla.add("ACTIVIDAD FÍSICA ");
            tabla.add("Fecha de la información: ");
            tabla.add(rs.getString("fecha"));
            tabla.add("Realizo actividad física  30 minutos al día: ");
            tabla.add(rs.getString("ejercicio30min"));
            tabla.add("Puedo escoger caminar, montar bicicleta, nadar o correr como parte de mi actividad física.: ");
            tabla.add(rs.getString("escogeejercicio"));
            tabla.add("Tengo la posibilidad de realizar actividad física con otras personas.: ");
            tabla.add(rs.getString("ejerciciosconotros"));
            tabla.add("Tengo limitaciones para realizar actividad física: ");
            tabla.add(rs.getString("limitacionesaejercicios"));
        }  
        rs = st.executeQuery("select * from autocuidado where paciente = "+id);
        while (rs.next()){        
            //tabla.add("AUTOCUIDADO ");
            tabla.add("Fecha de la información: ");
            tabla.add(rs.getString("fecha"));
            tabla.add("Invierto tiempo en mi propio cuidado: ");
            tabla.add(rs.getString("tiempoenmicuidado"));
            tabla.add("Busco información y orientación sobre el manejo de mi enfermedad: ");
            tabla.add(rs.getString("buscoinfo"));
            tabla.add("Conózco mis medicamentos y  para que sirven: ");
            tabla.add(rs.getString("conozcomedicamentos"));
            tabla.add("Las medidas que he tomado con respecto ala diabetes permiten garantizar mi bienestar y el de mi familia: ");
            tabla.add(rs.getString("tengoprecaucion"));
        } 
        //---Consulta en autoeficacia, percepcion del riesgo y estado de ánimo
        rs = st.executeQuery("select * from autoeficacia where paciente = "+id);
        while (rs.next()){
            //tabla.add("AUTOEFICACIA ");
            tabla.add("Fecha de la información: ");
            tabla.add(rs.getString("fecha"));
            tabla.add("¿Puedo cumplir metas realistas en el cuidado de mi diabetes?: ");
            tabla.add(rs.getString("metasrealistas"));
            tabla.add("¿Sigo un plan  para alcanzar mis metas del tratamiento?: ");
            tabla.add(rs.getString("metasplan"));
            tabla.add("¿Conozco lo que me motiva para cuidar de mi diabetes?: ");
            tabla.add(rs.getString("conocemotivaciones"));
            tabla.add("¿Estoy en capacidad de aceptar recomendaciones para cuidar de mi diabetes?: ");
            tabla.add(rs.getString("aceptarecomendaciones"));
        }  
        rs = st.executeQuery("select * from estadoanimo where paciente = "+id);
        while (rs.next()){
            //tabla.add("ESTADO DE ÁNIMO ");
            tabla.add("Fecha de la información: ");
            tabla.add(rs.getString("fecha"));
            tabla.add("¿Siente que su estado de ánimo le permite cuidarse? : ");
            tabla.add(rs.getString("puedecuidarse"));
            tabla.add("¿Piensa de manera  optimista sobre su futuro?: ");
            tabla.add(rs.getString("optimistafuturo"));
            tabla.add("¿Se enoja con facilidad?: ");
            tabla.add(rs.getString("seenoja"));
        }
        rs = st.executeQuery("select * from percepcionriesgo where paciente = "+id);
        while (rs.next()){
            //tabla.add("PERCEPCIÓN DEL RIESGO ");
            tabla.add("Fecha de la información: ");
            tabla.add(rs.getString("fecha"));
            tabla.add("¿Cree que  tenga en un momento de su vida tenga un infarto?: ");
            tabla.add(rs.getString("infarto"));
            tabla.add("¿Cree que  tenga en un momento de su vida tenga presión arterial alta?: ");
            tabla.add(rs.getString("presionalta"));
            tabla.add("¿Cree que  tenga en un momento de su vida tenga una caida?:  ");
            tabla.add(rs.getString("caida"));
            tabla.add("¿Cree que  tenga en un momento de su vida tenga una enfermedad del corazón?: ");
            tabla.add(rs.getString("enfermedadcorazon"));
        }
        }  
        con.close();
        } catch (Exception e) {}
        
        return tabla;
    }

    
    @WebMethod(operationName = "consultarRol")
    public String consultarRol(@WebParam(name = "id") long id) {
        ArrayList <String> tabla = new ArrayList<String>();
        String respuesta = null;
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();           
            ResultSet rs = st.executeQuery("SELECT rol from usuario where identificacion ="+id);            
                while (rs.next()){
                    tabla.add(rs.getString("rol"));
                }            
            respuesta = tabla.get(0);            
            con.close();
        } catch (Exception e) {}        
        return respuesta;
    }

    
    @WebMethod(operationName = "consultarTemas")
    public ArrayList consultarTemas(@WebParam(name = "tema") String tema) {
        ArrayList <String> tabla = new ArrayList<String>();
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();  
            ResultSet rs = st.executeQuery("select fecha, nombres, apellidos, mensaje from comunidad, usuario where comunidad.participante = usuario.identificacion and comunidad.tema = '"+tema+"'");
            while (rs.next()){
                tabla.add(rs.getString("fecha"));// del remitente
                    String participante = rs.getString("nombres")+" "+rs.getString("apellidos");
                tabla.add(participante); 
                tabla.add(rs.getString("mensaje"));
                }            
            con.close();
        } catch (Exception e) {}
        return tabla;
    }

    
    @WebMethod(operationName = "participarComunidad")
    public String participarComunidad(@WebParam(name = "id") long id, @WebParam(name = "tema") String tema, @WebParam(name = "mensaje") String mensaje) {
        String respuesta = "No se pudo publicar";
        try {
            Connection con = conexionBD();            
            Statement st = con.createStatement();
            st.executeUpdate("insert into comunidad values(now(),'"+tema+"', 0,"+id+", '"+mensaje+"')");
                respuesta = "Mensaje publicado!";            
        } catch (Exception e) { }
        return respuesta;
    }

    
    @WebMethod(operationName = "borrarTema")
    public String borrarTema(@WebParam(name = "tema") String tema) {
        try {
            Connection con = conexionBD();            
            Statement st = con.createStatement();
            st.executeUpdate("delete from comunidad where tema = '"+tema+"'");            
        } catch (Exception e) { }
        return null;
    }


    @WebMethod(operationName = "consentimiento")
    public String consentimiento(@WebParam(name = "id") long id) {
        try {
            Connection con = conexionBD();            
            Statement st = con.createStatement();
            st.executeUpdate("update usuario set aceptaterminos = 1 where identificacion ="+id);            
        } catch (Exception e) { }
        return null;
    }


        @WebMethod(operationName = "listaInformacion") // Información general sobre la dmt2
    public ArrayList listaInformacion() {
        ArrayList <String> tabla = new ArrayList<String>();
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();  
            ResultSet rs = st.executeQuery("select * from informacion");
            while (rs.next()){
                tabla.add(rs.getString("titulo"));
                tabla.add(rs.getString("descripcion")); 
                tabla.add(rs.getString("contenido"));
                }            
            con.close();
        } catch (Exception e) {}
        return tabla;
    }
    
    

@WebMethod(operationName = "actualizarHba1c")
    public String actualizarHba1c(@WebParam(name = "id") Long id, @WebParam(name = "hba1c") String h) {
        String respuesta = "No se pudo completar";
        try {          
            Connection con = conexionBD();            
            Statement st = con.createStatement(); 
            st.executeUpdate("insert into hba1c values("+id+",now(),"+Float.parseFloat(h.trim())+")");            
            respuesta = "Ingreso correcto";
            con.close();
        } catch (Exception e) { }
        return respuesta;
    }

    
    
    @WebMethod(operationName = "actualizarPeso")
    public String actualizarPeso(@WebParam(name = "id") Long id, @WebParam(name = "peso") String p) {
        String respuesta = "No se pudo completar";
        try {          
            Connection con = conexionBD();            
            Statement st = con.createStatement(); 
            ResultSet rs = st.executeQuery("select talla from diagnosticoinicial where paciente = '"+id+"'");
            String talla = null;
            while (rs.next()){    talla = rs.getString("talla");    }            
            //Calculo del imc  
            
            double imcTemp= Double.parseDouble(p.trim())/((Double.parseDouble(talla)*Double.parseDouble(talla))/10000);
            int imcFinal = (int) Math.round(imcTemp);
                        
            st.executeUpdate("insert into pesoimc values("+id+",now(),"+Integer.parseInt(p)+","+imcTemp+")");            
            respuesta = "Ingreso correcto";
            con.close();
        } catch (Exception e) { }
        return respuesta;
    }
    
    
    @WebMethod(operationName = "crearObservacion")
    public String crearObservacion(@WebParam(name = "id") long id, @WebParam(name = "paciente") long paciente,@WebParam(name = "texto") String t) {
        String respuesta = "No se pudo guardar";
        try {
            Connection con = conexionBD();            
            Statement st = con.createStatement();
            st.executeUpdate("insert into observaciones values("+id+","+paciente+",now(),'"+t+"')");
                respuesta = "Información guardada";            
        } catch (Exception e) { }
        return respuesta;
    }
    
    @WebMethod(operationName = "consultarObservaciones") // Información general sobre la dmt2
    public ArrayList consultarObservaciones(@WebParam(name = "id") long id, @WebParam(name = "paciente") long paciente) {
        ArrayList <String> tabla = new ArrayList<String>();
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();  
            ResultSet rs = st.executeQuery("select * from observaciones where id ="+id+" and paciente = "+paciente+" order by fecha");
            while (rs.next()){
                tabla.add(rs.getString("fecha"));
                tabla.add(rs.getString("observacion")); 
                }            
            con.close();
        } catch (Exception ee) {}
        return tabla;
    }
    
    
    @WebMethod(operationName = "actualizarObservacion")
    public String actualizarObservacion(@WebParam(name = "texto") String t,@WebParam(name = "old") String old) {
        String respuesta = "No se pudo actualizar";
        try {
            Connection con = conexionBD();            
            Statement st = con.createStatement();
            st.executeUpdate("update observaciones set observacion ='"+t+"' where observacion ='"+old+"'");
                respuesta = "Información actualizada";            
        } catch (Exception ee) { }
        return respuesta;
    }
    
    
    
     @WebMethod(operationName = "crearMedicamento")
    public void crearMedicamento(@WebParam(name = "paciente") long paciente,@WebParam(name = "a") String a,@WebParam(name = "b") String b,@WebParam(name = "c") int c,@WebParam(name = "d") int d,@WebParam(name = "e") boolean e, @WebParam(name = "f") String f,@WebParam(name = "g") String g) {
        try {
            Connection con = conexionBD();            
            Statement st = con.createStatement();
            System.out.println("El valor de el tiempo es: "+ g);
            st.executeUpdate("insert into medicamento values(null, "+paciente+",now(),'"+a+"','"+b+"',"+c+","+d+","+e+",'"+f+"','"+g+"', 1)");
            con.close();
        } catch (Exception ee) {ee.printStackTrace(); }
    }
    
    @WebMethod(operationName = "consultarMedicamentos") // Información general sobre la dmt2
    public ArrayList consultarMedicamentos(@WebParam(name = "paciente") long paciente) {
        ArrayList <String> tabla = new ArrayList<String>();
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();  
            ResultSet rs = st.executeQuery("select * from medicamento where paciente ="+paciente+" and estado = 1 order by fecha");
            while (rs.next()){                
                tabla.add(rs.getString("nombre"));
                tabla.add(rs.getString("tipo"));
                tabla.add(rs.getString("dosificacion"));
                tabla.add(rs.getString("posologia"));
                tabla.add(rs.getString("recordar"));
                tabla.add(rs.getString("observaciones"));
                tabla.add(rs.getString("ultimatoma"));
                tabla.add(rs.getString("idfila"));
                }            
            con.close();
        } catch (Exception ee) {ee.printStackTrace();}
        return tabla;
    }
    
    
    @WebMethod(operationName = "actualizarMedicamento")
    public void actualizarMedicamento(@WebParam(name = "paciente") long paciente,@WebParam(name = "a") String a,@WebParam(name = "b") String b,@WebParam(name = "c") int c,@WebParam(name = "d") int d,@WebParam(name = "e") boolean e, @WebParam(name = "f") String f,@WebParam(name = "g") String g,@WebParam(name = "h") int h) {
        try {
            Connection con = conexionBD();            
            Statement st = con.createStatement();
            st.executeUpdate("update medicamento set paciente ="+paciente+", fecha = now(),  nombre ='"+a+"', tipo ='"+b+"', dosificacion ="+c+", posologia ="+d+", recordar ="+e+", observaciones = '"+f+"', ultimatoma ='"+g+"' where idfila = "+h);  
            con.close();
        } catch (Exception ee) { ee.printStackTrace();}
    }
    
    @WebMethod(operationName = "desactivarMedicamento")
    public void desactivarMedicamento(@WebParam(name = "paciente") long paciente,@WebParam(name = "h") int h) {
        try {
            Connection con = conexionBD();            
            Statement st = con.createStatement();
            st.executeUpdate("update medicamento set estado = 0 where idfila = "+h); 
        } catch (Exception ee) { ee.printStackTrace();}
    }
    
    
    
        @WebMethod(operationName = "consultarMetas")
    public ArrayList consultarMetas(@WebParam(name = "paciente") long paciente) {
        ArrayList <String> tabla = new ArrayList<String>();
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();  
            ResultSet rs = st.executeQuery("select * from metas where id ="+paciente);
            while (rs.next()){                
                tabla.add(rs.getString("meta1"));
                tabla.add(rs.getString("meta2"));
                tabla.add(rs.getString("meta3"));
                tabla.add(rs.getString("meta4"));
                tabla.add(rs.getString("meta5"));
                tabla.add(rs.getString("meta6"));
                tabla.add(rs.getString("meta7"));
                tabla.add(rs.getString("meta8"));
                tabla.add(rs.getString("meta9"));
                tabla.add(rs.getString("meta10"));
                }            
            con.close();
        } catch (Exception ee) {ee.printStackTrace();}
        return tabla;
    }
    
    
    
    @WebMethod(operationName = "crearMetas")
    public String crearMetas(@WebParam(name = "paciente") long paciente,@WebParam(name = "a") boolean a,@WebParam(name = "b") boolean b,@WebParam(name = "c") boolean c,@WebParam(name = "d") boolean d,@WebParam(name = "e") boolean e, @WebParam(name = "f") boolean f,@WebParam(name = "g") boolean g,@WebParam(name = "h") boolean h,@WebParam(name = "i") boolean i,@WebParam(name = "j") boolean j) {
        String respuesta = "No se pudo guardar!";
        try {
            Connection con = conexionBD();            
            Statement st = con.createStatement();
            st.executeUpdate("update metas set fecha = now(), meta1="+a+", meta2="+b+", meta3="+c+", meta4="+d+", meta5="+e+", meta6="+f+", meta7="+g+", meta8="+h+", meta9 ="+i+", meta10 ="+j+" where id = "+paciente);
            con.close();
            respuesta = "Metas guardadas!";
        } catch (Exception ee) { ee.printStackTrace();}
        return respuesta;
    }
    
    
    
    @WebMethod(operationName = "crearHabito")
    public String crearHabito(@WebParam(name = "titulo") String titulo, @WebParam(name = "descripcion") String descripcion, @WebParam(name = "contenido") String contenido, @WebParam(name = "imagen") String imagen) {  
        String respuesta = "No se pudo cargar la información";
        try {            
            Calendar c = new GregorianCalendar();
            String nom = Long.toString(c.getTimeInMillis());
            Connection con = conexionBD();            
            Statement st = con.createStatement();            
            st.executeUpdate("insert into habitos values (null,'"+titulo+"','"+descripcion+"','"+contenido+"','"+nom+"')");
            st.executeUpdate("update notificaciones set recordatorio = 1 ");            
            byte[] decodificar = Base64.decodeBase64(imagen.getBytes()); 
            OutputStream salida = new FileOutputStream(destinoRecurso + nom); 
            salida.write(decodificar);
            salida.close();
            con.close();
            respuesta = "Información cargada con éxito!";
        } catch (Exception e) { }
        return respuesta;
    }
    
    
        @WebMethod(operationName = "modificarHabito")
    public String modificarHabito(@WebParam(name = "titulo") String titulo, @WebParam(name = "descripcion") String descripcion, @WebParam(name = "contenido") String contenido, @WebParam(name = "imagen") String imagen, @WebParam(name = "idfila") String idfila,@WebParam(name = "imgenvieja") String imgenvieja) {  
        String respuesta = "No se pudo modificar";
        try {            
            Calendar c = new GregorianCalendar();
            String nom = null;
            if(imagen.length() < 5){ //Caso en el que no se cambia la imagen
                nom = imgenvieja;
            } else{
                nom = Long.toString(c.getTimeInMillis());
                byte[] decodificar = Base64.decodeBase64(imagen.getBytes());             
                OutputStream salida = new FileOutputStream(destinoRecurso + nom); 
                salida.write(decodificar);
                salida.close();
            }            
            Connection con = conexionBD();            
            Statement st = con.createStatement();            
            st.executeUpdate("update habitos set titulo='"+titulo+"',descripcion='"+descripcion+"',contenido='"+contenido+"',imagen='"+nom+"' where idfila="+
                    Integer.parseInt(idfila)+"");
            con.close();
            return respuesta = "Hábito actualizado!";
        } catch (Exception e) { }
        return null;
    }
       
    
      
    @WebMethod(operationName = "habitos")
    public ArrayList habitos() {
        ArrayList <String> tabla = new ArrayList<String>();
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();  
            ResultSet rs = st.executeQuery("select * from habitos");    
            while (rs.next()){
                tabla.add(rs.getString("idfila"));
                tabla.add(rs.getString("titulo"));
                tabla.add(rs.getString("descripcion"));
                tabla.add(rs.getString("contenido"));
                tabla.add(rs.getString("imagen"));                
            }            
            con.close();
        } catch (Exception e) {}
        return tabla;
    }  
    
    
    
//@WebMethod(operationName = "borrarBD")
    public void borrarBD() {  
        String respuesta = "No se pudo cargar la información";
        try {            
            Connection con = conexionBD();            
            Statement st = con.createStatement();            
            st.executeUpdate("delete from trigliceridos where 1");
            st.executeUpdate("delete from tratantes where 1");
            st.executeUpdate("delete from tension where 1");
            st.executeUpdate("delete from recursos where 1");
            st.executeUpdate("delete from pesoimc where 1");
            st.executeUpdate("delete from percepcionriesgo where 1");
            st.executeUpdate("delete from paciente where 1");
            st.executeUpdate("delete from observaciones where 1");
            st.executeUpdate("delete from notificaciones where 1");
            st.executeUpdate("delete from metas where 1");
            st.executeUpdate("delete from mensajes where 1");
            st.executeUpdate("delete from medicamento where 1");
            //st.executeUpdate("delete from informacion where 1");
            st.executeUpdate("delete from hba1c where 1");
            //st.executeUpdate("delete from habitos where 1");
            st.executeUpdate("delete from fyc where 1");
            st.executeUpdate("delete from estilovida where 1");
            st.executeUpdate("delete from estadoanimo where 1");
            st.executeUpdate("delete from diagnosticoinicial where 1");
            st.executeUpdate("delete from comunidad where 1");
            st.executeUpdate("delete from comorbilidad where 1");
            st.executeUpdate("delete from avances where 1");
            st.executeUpdate("delete from autoeficacia where 1");
            st.executeUpdate("delete from autocuidado where 1");
            st.executeUpdate("delete from apoyosocial where 1");
            st.executeUpdate("delete from animo where 1");
            st.executeUpdate("delete from actividadfisica where 1");
            st.executeUpdate("delete from usuario where rol = 'paciente' or rol = 'familiar' or rol = 'profesional'");
            con.close();
        } catch (Exception e) { }
    }
    
    
    //Estadísticas  ------------------------------------------------------------
    Hashtable<Long, Long> tiempos = new Hashtable<>();
    
    @WebMethod(operationName = "ti")    
    public void ti(@WebParam(name = "id") Long id) { 
        tiempos.put(id,System.currentTimeMillis()); //El id y el tiempo en que entra en la actividad
    }
            
    public void tf(Long id, String nombreMedicion) { 
        try {            
            Connection con = conexionBD();            
            Statement st = con.createStatement();
            //Caso 1: Paciente desde usuario hasta estilo de vida
            if(nombreMedicion.contains("ingresoPaciente1")){            
                st.executeUpdate("insert into tiemposdeuso values ("+id+",now(),0,"+(System.currentTimeMillis()-tiempos.get(id))/1000+",0,0,0)");
            }
            //Caso 2: Paciente desde Actividad física hasta AEP
            if(nombreMedicion.contains("ingresoPaciente2")){            
                st.executeUpdate("insert into tiemposdeuso values ("+id+",now(),0,0,"+(System.currentTimeMillis()-tiempos.get(id))/1000+",0,0)");
            }
            //Caso 3: Creación de paciente
            if(nombreMedicion.contains("crearPaciente")){            
                st.executeUpdate("insert into tiemposdeuso values ("+id+",now(),"+(System.currentTimeMillis()-tiempos.get(id))/1000+",0,0,0,0)");
            }
            //Caso 4: Creación de recurso
            if(nombreMedicion.contains("crearRecurso")){            
                st.executeUpdate("insert into tiemposdeuso values ("+id+",now(),0,0,0,"+(System.currentTimeMillis()-tiempos.get(id))/1000+",0)");
            }
        } catch (Exception e) { }
    }
    
    @WebMethod(operationName = "encuestaPro")
    public void encuestaPro(@WebParam(name = "id") long id) {
        try {
            Connection con = conexionBD();            
            Statement st = con.createStatement();
            st.executeUpdate("update avances set completado = 2 where identificacion ="+id ); //Poner en 2 completado de un profesional no activa más la encuesta
            con.close();
        } catch (Exception ee) { ee.printStackTrace();}
    }
    
    @WebMethod(operationName = "crearGlucosa")
    public void crearGlucosa(@WebParam(name = "id") long id,@WebParam(name = "glucosa") int glucosa) {
        try {
            Connection con = conexionBD();            
            Statement st = con.createStatement();
            st.executeUpdate("insert into glucosa values("+id+",now(),"+glucosa+")" );
            con.close();
        } catch (Exception ee) { ee.printStackTrace();}
    }
    
    @WebMethod(operationName = "consultarGlucosa")
    public ArrayList consultarGlucosa(@WebParam(name = "id") long id) {
        ArrayList <String> tabla = new ArrayList<String>();
        try {  
            Connection con = conexionBD();  
            Statement st = con.createStatement();  
            ResultSet rs = st.executeQuery("select * from glucosa where id ="+id+" order by fechahora");                    
            while (rs.next()){
                tabla.add(rs.getString("fechahora"));
                tabla.add(rs.getString("glucosa"));
                }            
            con.close();
        } catch (Exception e) {}
        return tabla;
    }
    
}