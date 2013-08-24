/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openbravo.pos.forms;

import com.openbravo.data.loader.*;

import com.openbravo.basic.BasicException;
/**
 *
 * @author root
 */
public class AsociacionNCF  {

    private int id = 0; // no es necesario se asigna cuando se escribe en la BD o se tiene cuando se lee
    private String receiptId = ""; //init1
    private String originalNCF = ""; //crearNCF lo asigna luego de que se ha inicializao ... init()
    private RangoNCF rango; //init1
    private int referenciadoId = 0; //init2
    private String clienteRNC = ""; //init1

    public AsociacionNCF(){

        rango = new RangoNCF();
                
    }

    public void init(String receiptId, RangoNCF rango, String clienteRNC ){
        this.receiptId = receiptId;
        this.rango = rango;
        this.clienteRNC = clienteRNC;
    }
    public void init(String receiptId, RangoNCF rango, String clienteRNC, int referenciadoId ){
        init(receiptId,rango,clienteRNC);
        this.referenciadoId = referenciadoId;
    }

    public String crearNCF(EmpresaNCF e){

        //Se requiere:  Una empresa con cabecera de long valida
        //              Un rango asignado y que este a su vez tenga un tipo con Valor de longitud valida
        //              Se asume que el contador tiene una longitud de 8 digitos

        //Esta funcion no verifica que el rang se agote, selecciona el contador sin apelacion ...

        StringBuffer p1 = new StringBuffer();
        if(e.verifLargoCabeceraNCF() && rango.getTipo().verifValor()) 
            p1.append(e.getCabeceraNCF()).append(rango.getTipo().getValor()).append(formatContador(rango.getContador()));
        else
            return null;

        originalNCF =  p1.toString();
        return originalNCF;
    }
    public boolean verifLargoNCF() {
        return originalNCF.length() == 19 ? true : false ;
    }
    
    public String formatContador(int contador){
        String cadenaCont = String.valueOf(contador);
        StringBuffer relleno = new StringBuffer();

        for(int i = 0; i < ( 8 - cadenaCont.length() ) ; i ++){
            relleno.append("0"); // Si el contador es un dig => 8-1 = 7 ceros, i=0...6, 7 iteraciones agregan 7 ceros ...
        }

        relleno.append(cadenaCont); //le pegamos el contador

        return relleno.toString();
    }



    public int getId() {
        return id;
    }
    public String getReceiptId() {
        return receiptId;
    }
    public String getOriginalNCF() {
        return originalNCF;
    }
    public RangoNCF getRango() {
        return rango;
    }
    public int getReferenciadoId() {
        return referenciadoId;
    }
    public String getClienteRNC() {
        return clienteRNC;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }
    public void setOriginalNCF(String originalNCF) {
        this.originalNCF = originalNCF;
    }
    public void setRango(RangoNCF rango) {
        this.rango = rango;
    }
    public void setReferenciadoId(int referenciadoId) {
        this.referenciadoId = referenciadoId;
    }
    public void setClienteRNC(String clienteRNC) {
        this.clienteRNC = clienteRNC;
    }

}

