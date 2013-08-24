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
public class TipoNCF{

    private int id = 0;
    private String nombre = "";
    private String descripcion = "";
    private String valor = "";
    private int restanteCritico = 0;
    private EstadoTipoNCF estado;

    public TipoNCF(){

        estado = new EstadoTipoNCF();
        
    }
    public boolean verifValor(){
        return cantDigitosInexactosValor() == 0 ? true : false ;
    }
    public int cantDigitosInexactosValor(){
        return valor.length() - 2; //Si es igual a 0 tiene lo deseado, > 1 sobra ese no. caracteres, < 1 faltan ese no.
    }

    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public int getRestanteCritico() {
        return restanteCritico;
    }
    public String getValor() {
        return valor;
    }
    public EstadoTipoNCF getEstado() {
        return estado;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setRestanteCritico(int restanteCritico) {
        this.restanteCritico = restanteCritico;
    }
    public void setEstado(EstadoTipoNCF estado) {
        this.estado = estado;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }

}
