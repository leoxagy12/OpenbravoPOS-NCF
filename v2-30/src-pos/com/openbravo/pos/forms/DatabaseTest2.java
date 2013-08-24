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
public class DatabaseTest2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        System.out.println("Database Connection Test 2");
        System.out.println("Estableciendo Session ... ");


        String url = "jdbc:mysql://10.0.0.30:3306/pos03";
        String user = "pos03";
        String pass = "123";

        Session s = null;


        try{
            // register the database driver
            if (isJavaWebStart()) {
                Class.forName("com.mysql.jdbc.Driver", true, Thread.currentThread().getContextClassLoader());
                
            } else {
                ClassLoader cloader = new URLClassLoader(new URL[] {new File("").toURI().toURL()});
                DriverManager.registerDriver(new DriverWrapper((Driver) Class.forName("com.mysql.jdbc.Driver", true, cloader).newInstance()));
                
            }

            s = new Session(url,user,pass);
            System.out.println("Session creada ... ");


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

        DataLogicNCF dataLogicNCF = new DataLogicNCF();

        dataLogicNCF.init(s);

 

        try {
            AsociacionNCF asc2 = (AsociacionNCF) dataLogicNCF.obtenerAsociacionNCF("7659fd61-dad9-45d3-b3dc-e7c7d70c027d");

            if (asc2 != null) {
                EmpresaNCF emp2 = dataLogicNCF.obtenerEmpresaNCF();

                System.out.println("\n\n\n\n\n-----------------------------------------------------------------------\n");

                System.out.println("EmpresaNCF -> " + "\t\n\tNombreComercial: " + emp2.getNombreComercial() + "\t\n\tRazonSocial: " + emp2.getRazonSocial() + "\t\n\tDireccion: " + emp2.getDireccion() + "\t\n\tRNC: " + emp2.getRNC() + "\t\n\tCabeceraNCF: " + emp2.getCabeceraNCF());

                System.out.println("AsociacionNCF -> " + "\t\n\tId: " + asc2.getId() + "\t\n\tReceipt: " + asc2.getReceiptId() + "\t\n\tOriginalNCF: " + asc2.getOriginalNCF() + "\t\n\tRango Id: " + asc2.getRango().getId() + "\t\n\tReferenciado Id: " + asc2.getReferenciadoId() + "\t\n\tCliente RNC: " + asc2.getClienteRNC());

                RangoNCF rang2 = asc2.getRango();
                System.out.println("RangoNCF -> " + "\t\n\tId: " + rang2.getId() + "\t\n\tTipo Id: " + rang2.getTipo().getId() + "\t\n\tNumero Rango: " + rang2.getNumeroRango() + "\t\n\tNumero Inicial: " + rang2.getNumeroInicial() + "\t\n\tNumero Final: " + rang2.getNumeroFinal() + "\t\n\tContador: " + rang2.getContador() + "\t\n\tEstado: " + rang2.getEstado().getEstado());

                TipoNCF tip2 = rang2.getTipo();
                System.out.println("TipoNCF -> " + "\t\n\tId: " + tip2.getId() + "\t\n\tNombre: " + tip2.getNombre() + "\t\n\tDescripcion: " + tip2.getDescripcion() + "\t\n\tValor: " + tip2.getValor() + "\t\n\tRestante Critico: " + tip2.getRestanteCritico() + "\t\n\tEstado: " + tip2.getEstado().getEstado());
                
            } else {
                System.out.println("Asociacion Nula");
            }

            List<TipoNCF> lrs = dataLogicNCF.obtenerListaTiposNCF();

            System.out.println("List<RangoNCF>(lrs).get0.getEstadoString: " + lrs.get(0).getNombre() );

        } catch (BasicException ex) {
            System.out.println("Se genero un error en la obtencion de la empresa: " + ex.getMessage());
        }


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

