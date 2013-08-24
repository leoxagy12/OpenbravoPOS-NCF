/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openbravo.pos.forms;

import java.util.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import javax.imageio.ImageIO;
import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.*;
import com.openbravo.format.Formats;
import com.openbravo.pos.util.ThumbNailBuilder;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;



import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.openbravo.basic.BasicException;



import java.lang.Class;



/**
 *
 * @author leomar
 */
public class DatabaseTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        System.out.println("Database Connection Test");
        System.out.println("Estableciendo Session ... ");


        String url = "jdbc:mysql://10.0.0.30:3306/pos03";
        String user = "pos03";
        String pass = "123";

        Session s = null;


        try{
            // register the database driver
            if (isJavaWebStart()) {
                Class.forName("com.mysql.jdbc.Driver", true, Thread.currentThread().getContextClassLoader());
                System.out.println("isJavaWebStart");
            } else {
                ClassLoader cloader = new URLClassLoader(new URL[] {new File("").toURI().toURL()});
                DriverManager.registerDriver(new DriverWrapper((Driver) Class.forName("com.mysql.jdbc.Driver", true, cloader).newInstance()));
                System.out.println("!isJavaWebStart");
            }
        
            s = new Session(url,user,pass);



        } catch (SQLException eSQL) {
            System.out.println("Se genero un error en la creacion de session (1): " + eSQL.getMessage());
        } catch (ClassNotFoundException eCNF) {
            System.out.println("Se genero un error en la creacion de session (2): " + eCNF.getMessage());
        } catch (InstantiationException e) {
            System.out.println("Se genero un error en la creacion de session (3): " + e.getMessage());
        } catch (IllegalAccessException eIA) {
            System.out.println("Se genero un error en la creacion de session (4): " + eIA.getMessage());
        } catch (MalformedURLException eMURL) {
            System.out.println("Se genero un error en la creacion de session (5): " + eMURL.getMessage());
        }

        try{
            System.out.println("Session methods invocations: " +
                    "\n\t s.getURL(): " + s.getURL() + 
                    "\n\t s.toString(): " + s.toString() + 
                    "\n\t s.getConnection().getMetaData().getDatabaseProductName(): " + s.getConnection().getMetaData().getDatabaseProductName() );

            System.out.println("");
        }catch(SQLException eSQL){

        }

        /*
        try{

            String sentencia = "SELECT * FROM  ? ";
            //String t1 = "RANGOS";
            
            PreparedStatement stmt = s.getConnection().prepareStatement(sentencia);
            System.out.println("Se creo el PreparedStatement: " + sentencia);

            //stmt.setString(1, t1);
            //System.out.println("Se seteo el parametro t1: " + t1);

            //PreparedSentencePars psp = new PreparedSentencePars(stmt); NO SE PUEDE, ES PRIVADA


            System.out.println("Procedemos a ejecutar el PreparedStatement:");

            if(stmt.execute())
            {
                System.out.println("");
                System.out.println("Se ha generado un ResultSet:");
                ResultSet rs = stmt.getResultSet();

                System.out.println("");
                
                ResultSetMetaData md = rs.getMetaData();
                for (int i = 0; i < md.getColumnCount(); i++) {
                    System.out.print(md.getColumnName(i + 1) + "\t");
                }
                
                while(rs.next())
                {

                    System.out.println("");
                    System.out.print(new Integer(rs.getInt(1)).toString() + "\t");
                    System.out.print(new Integer(rs.getInt(2)).toString() + "\t");
                    System.out.print(new Integer(rs.getInt(3)).toString() + "\t");
                    System.out.print(new Integer(rs.getInt(4)).toString() + "\t");
                    System.out.print(new Integer(rs.getInt(5)).toString() + "\t");
                    System.out.print(new Integer(rs.getInt(6)).toString() + "\t");
                    System.out.print(new Integer(rs.getInt(7)).toString() + "\t");
                }

                System.out.println("");
                

            }
            else
            {
                System.out.println("Fue un update count");
            }

       


        }catch(SQLException eSQL){
            System.out.println("Se genero una excepcion en la ejecucion de la sentencia de prueba: " + eSQL.getMessage() );
        }
  */

        /*System.out.println("Procedemos a ejecutar instrucciones de prueba. ");

        //1------------------------------------------------------

        System.out.println("Ej. 1: Version de la aplicacion");
        SentenceFind s1 = new PreparedSentence(s, "SELECT VERSION FROM APPLICATIONS WHERE ID = ?", SerializerWriteString.INSTANCE, SerializerReadString.INSTANCE);

        try{
        System.out.println("Procedemos a ejecutar: (String)m_version.find(1);");
        String version = (String) s1.find(AppLocal.APP_ID);
        System.out.println("Version de la Aplicacion: "+ version);

        }catch(BasicException e){
        System.out.println("Se genero una excepcion en la ejecucion de la sentencia de prueba: " + e.getMessage() );
        }

        //2------------------------------------------------------

        System.out.println("Ej. 2: Nombre Tipo de Estado NCF segun ID (Single row out, Single cols out, Single in)");
        SentenceFind s2 = new PreparedSentence(s, "SELECT NOMBRE FROM ESTADOSRANGOS WHERE ID = ?", SerializerWriteInteger.INSTANCE, SerializerReadString.INSTANCE);

        try{
        System.out.println("Procedemos a ejecutar: ");
        String estado = (String) s2.find(1);

        System.out.println("Nombre Tipo de Estado NCF: "+ estado);

        }catch(BasicException e){
        System.out.println("Se genero una excepcion en la ejecucion de la sentencia de prueba: " + e.getMessage() );
        }

        //3------------------------------------------------------

        System.out.println("Ej. 3:  Id - Nombre - Descripcion de EstadoRangosNCF segun ID (Single row out, Mult cols out, Single in)");
        SentenceFind s3 = new PreparedSentence(s, "SELECT ID, NOMBRE, DESCRIPCION FROM ESTADOSRANGOS WHERE ID = ?", SerializerWriteInteger.INSTANCE, new SerializerReadBasic(new Datas[] { Datas.INT, Datas.STRING, Datas.STRING } ) );

        try{
        System.out.println("Procedemos a ejecutar: ");
        Object[] est = (Object[]) s3.find(3);

        System.out.println("ID->"+  est[0] + " | NOMBRE->" + est[1] + " | DESC->" + est[2]);

        //para saber el tipo ->   .getClass().getName(); INTEGER - STRING - STRING ....

        }catch(BasicException e){
        System.out.println("Se genero una excepcion en la ejecucion de la sentencia de prueba: " + e.getMessage() );
        }

        //4------------------------------------------------------

        System.out.println("Ej. 4:  ID del rango segun ID ESTADO y ID Tipo de Comprobante  (Single row out, Single cols out, Multiple in)");
        SentenceFind s4 = new PreparedSentence(s, "SELECT ID FROM RANGOS WHERE TIPO = ? AND ESTADO = ?", new SerializerWriteBasic(new Datas[] { Datas.INT, Datas.INT }), SerializerReadInteger.INSTANCE  );

        try{

        System.out.println("Procedemos a ejecutar: ");
        //Integer id_rango = (Integer) s4.find(1,1);

        Object id_rango = s4.find(1,1);

        System.out.println("ID Rango seleccionado: "+ id_rango);

        }catch(BasicException e){
        System.out.println("Se genero una excepcion en la ejecucion de la sentencia de prueba: " + e.getMessage() );
        }

        //5------------------------------------------------------

        System.out.println("Ej. 5:  ID - TIPO - ESTADO del rango segun ESTADO y TIPO  (Single row out, Multiple cols out, Multiple in)");
        SentenceFind s5 = new PreparedSentence(s, "SELECT ID, TIPO, ESTADO FROM RANGOS WHERE TIPO = ? AND ESTADO = ?", new SerializerWriteBasic(new Datas[] { Datas.INT, Datas.INT }), new SerializerReadBasic(new Datas[] {Datas.INT, Datas.INT, Datas.INT } )  );

        try{

        System.out.println("Procedemos a ejecutar: ");
        Object[] result = (Object[]) s5.find(1,1);

        System.out.println("RANGO.ID->"+  result[0] + " | RANGO.TIPO->" + result[1] + " | RANGO.ESTADO->" + result[2]);

        }catch(BasicException e){
        System.out.println("Se genero una excepcion en la ejecucion de la sentencia de prueba: " + e.getMessage() );
        }

        //6------------------------------------------------------

        System.out.println("Ej. 6:  ID - TIPO - ESTADO del rango segun ESTADO y TIPO  (Multiple row out, Multiple cols out, Multiple in)");
        SentenceList s6 = new PreparedSentence(s, "SELECT ID, TIPO, ESTADO FROM RANGOS WHERE TIPO = ? AND ESTADO = ?", new SerializerWriteBasic(new Datas[] { Datas.INT, Datas.INT }), new SerializerReadBasic(new Datas[] {Datas.INT, Datas.INT, Datas.INT } )  );

        try{

        System.out.println("Procedemos a ejecutar: ");
        List tabla =  s6.list(1,2);

        for(int i = 0; i < tabla.size() ; i++) {

        Object[] fila = (Object[]) tabla.get(i);
        System.out.println("RANGO.ID->"+  fila[0] + " | RANGO.TIPO->" + fila[1] + " | RANGO.ESTADO->" + fila[2]);
        }
        }
        catch(BasicException e){
        System.out.println("Se genero una excepcion en la ejecucion de la sentencia de prueba: " + e.getMessage() );
        }


        //7------------------------------------------------------

        final ThumbNailBuilder tnb = new ThumbNailBuilder(32, 32, "com/openbravo/images/yast_sysadmin.png");

        SerializerRead peopleread = new SerializerRead() {
        public Object readValues(DataRead dr) throws BasicException {
        return new AppUser(
        dr.getString(1),
        dr.getString(2),
        dr.getString(3),
        dr.getString(4),
        dr.getString(5),
        new ImageIcon(tnb.getThumbNail(ImageUtils.readImage(dr.getBytes(6)))));
        }
        };


        System.out.println("Ej. 7:  Inicializar objetos con una tabla y mostrar algunos datos de lo mismos (Multiple objects out,  Cero in)");
        SentenceList s7 = new PreparedSentence(s, "SELECT ID, NAME, APPPASSWORD, CARD, ROLE, IMAGE FROM PEOPLE WHERE VISIBLE = " + s.DB.TRUE(), null, peopleread  );

        try{

        System.out.println("Procedemos a ejecutar: ");
        List personas =  s7.list();

        for(int i = 0; i < personas.size() ; i++) {

        AppUser persona =  (AppUser) personas.get(i);
        System.out.println("Nombre persona -> "+  persona.getName() + "\n\t\t  Rol de persona -> " + persona.getRole() + "\n\t\t  Info -> " + persona.getUserInfo().getName());

        }




        }catch(BasicException e){
        System.out.println("Se genero una excepcion en la ejecucion de la sentencia de prueba: " + e.getMessage() );
        }*/

        //8-----Insertar fiulas en tablas dado atributosa de objeto-----------------------------------------------------------

        /*

         System.out.println("\n\n\nPrueba de nueva clase creada: RangoNCF");
        RangoNCF r1 = new RangoNCF();
        
        r1.setId(10);
        r1.setEstado(1);
        r1.setTipo(1);
        r1.setNumeroRango(10);
        r1.setNumeroInicial(3001);
        r1.setNumeroFinal(3500);
        r1.setContador(3001);
        r1.setEstado(2);
        
        System.out.println("Antes de grabar en la BD");
        System.out.println("ID -> " + r1.getId() + " | TIPO -> " + r1.getTipo() + " | NUMERORANGO -> " + r1.getNumeroRango() + " | NUMEROINICIAL -> " + r1.getNumeroInicial() + " | NUMEROFINAL -> " + r1.getNumeroFinal() + " | CONTADOR -> " +  r1.getContador() + " | ESTADO -> " + r1.getEstado() );
        
        //SentenceExec insertarRango = new PreparedSentence(s, "INSERT INTO RANGOS ( ID, TIPO, NUMERORANGO, NUMEROINICIAL, NUMEROFINAL, CONTADOR, ESTADO ) VALUES ( ? , ? , ? , ? , ? , ? , ? )", SerializerWriteBuilder.INSTANCE );
        
        //SentenceExec modificarRango = new PreparedSentence(s, "UPDATE INTO RANGOS ( ID, TIPO, NUMERORANGO, NUMEROINICIAL, NUMEROFINAL, CONTADOR, ESTADO ) VALUES ( ? , ? , ? , ? , ? , ? , ? )", SerializerWriteBuilder.INSTANCE );
        
        try {
        //insertarRango.exec(r1);
        //modificarRango.exec(r1);
        } catch (BasicException e){
        System.out.println("Se genero una excepcion en la ejecucion de la sentencia: " + e.getMessage() );
        }

         */

        //8-----Insertar fiulas en tablas dado atributosa de objeto-----------------------------------------------------------

        System.out.println("\n\n\nLectura de la base de datos de RangoNCF");
        //RangoNCF r1 = new RangoNCF();

        //System.out.println("Antes de leer de la BD");
        //System.out.println("ID -> " + r1.getId() + " | TIPO -> " + r1.getTipo() + " | NUMERORANGO -> " + r1.getNumeroRango() + " | NUMEROINICIAL -> " + r1.getNumeroInicial() + " | NUMEROFINAL -> " + r1.getNumeroFinal() + " | CONTADOR -> " +  r1.getContador() + " | ESTADO -> " + r1.getEstado() );

        SentenceList leerRangos = new PreparedSentence(s, "SELECT * FROM RANGOS", null, new SerializerReadClass(RangoNCF.class) );

        try {
            List listaRangos = leerRangos.list();

            for(int i = 0 ; i < listaRangos.size() ; i++)
            {
                RangoNCF r1 = (RangoNCF) listaRangos.get(i);
                System.out.println("ID -> " + r1.getId() + " | TIPO -> " + r1.getTipo() + " | NUMERORANGO -> " + r1.getNumeroRango() + " | NUMEROINICIAL -> " + r1.getNumeroInicial() + " | NUMEROFINAL -> " + r1.getNumeroFinal() + " | CONTADOR -> " +  r1.getContador() + " | ESTADO -> " + r1.getEstado() );

            }
            
        } catch (BasicException e){
            System.out.println("Se genero una excepcion en la ejecucion de la sentencia: " + e.getMessage() );
        }

        
        AsociacionNCF asc = new AsociacionNCF();
        RangoNCF rangoAsc = new RangoNCF();
        //TipoRangoNCF tipoRango = new TipoRangoNCF();
        EstadoRangoNCF estadoRango = new EstadoRangoNCF();
        
        

    }

    private static boolean isJavaWebStart() {

        try {
            Class.forName("javax.jnlp.ServiceManager");
            return true;
        } catch (ClassNotFoundException ue) {
            return false;
        }
    }
}

