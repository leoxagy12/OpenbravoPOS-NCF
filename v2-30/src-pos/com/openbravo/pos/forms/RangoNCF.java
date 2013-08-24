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
public class RangoNCF  {

    private int id = 0; //no necesita setearse, se obtiene de la interfaz BD
    private TipoNCF tipo; //init2
    private int numeroRango = 0;

    private int numeroInicial = 0; //init1
    private int numeroFinal = 0; //init1
    private int contador = 0; //init1

    private EstadoRangoNCF estado; //init1

    public RangoNCF(){

        estado = new EstadoRangoNCF();
        tipo = new TipoNCF();
        
    }

    public void init(int numeroInicial, int numeroFinal, String estado){
        this.numeroInicial = numeroInicial;
        this.numeroFinal = numeroFinal;
        this.contador = numeroInicial;
        this.estado.setEstado(estado);
    }
    public void init(int numeroInicial, int numeroFinal, String estado, TipoNCF tipo){
        this.numeroInicial = numeroInicial;
        this.numeroFinal = numeroFinal;
        this.contador = numeroInicial;
        this.estado.setEstado(estado);
        this.tipo = tipo;
    }
    public String getContadorString(){
        return String.valueOf(contador);
    }

    public boolean veriflargoContador(){
        return String.valueOf(this.contador).length() > 8 ? false : true; //retorna true si esta bien,
                                                                          //false si esta MAL, mayor de 8
    }
    
    public boolean verifLimits() {
        return numeroInicial <= numeroFinal ? true : false ;
    }
    public boolean verifConta() {
        return contador >= numeroInicial ? true : false ;
    }
    public boolean verifDisponible() {
        if ( cantidadDisponible() > 0 )
            return true; //tiene disponible no debe de ponerse AGOTADO
        else
            return false; //Se acabo, debe de ponerse AGOTADO y ACTIVADO el proximo INACTIVO si existe...
    }
    public int cantidadDisponible() {
        return numeroFinal - contador + 1 ; // Ej. nF = 500, c = 502 => nf-c+1 = -1 (corremos sin fondo)
    }

    

    public int getId() {
        return id;
    }
    public TipoNCF getTipo() {
        return tipo;
    }
    public int getNumeroRango() {
        return numeroRango;
    }
    public int getNumeroInicial() {
        return numeroInicial;
    }
    public int getNumeroFinal() {
        return numeroFinal;
    }
    public int getContador() {
        return contador;
    }
    public EstadoRangoNCF getEstado() {
        return estado;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setTipo(TipoNCF tipo) {
        this.tipo = tipo;
    }
    public void setNumeroRango(int numeroRango) {
        this.numeroRango = numeroRango;
    }
    public void setNumeroInicial(int numeroInicial) {
        this.numeroInicial = numeroInicial;
    }
    public void setNumeroFinal(int numeroFinal) {
        this.numeroFinal = numeroFinal;
    }
    public void setContador(int contador) {
        this.contador = contador;
    }
    public void setEstado(EstadoRangoNCF estado) {
        this.estado = estado;
    }
    
}
