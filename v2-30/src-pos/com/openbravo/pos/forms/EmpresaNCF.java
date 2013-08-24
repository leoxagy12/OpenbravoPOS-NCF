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
public class EmpresaNCF {

    private String nombreComercial = "";
    private String razonSocial = "";
    private String direccion = "";
    private String RNC = "";
    private String cabeceraNCF = "";

    public EmpresaNCF(){

    }

    public boolean verifLargoCabeceraNCF(){
        return cantDigitosInexactosCabecera() == 0 ? true : false;
    }
    public int cantDigitosInexactosCabecera(){
        return cabeceraNCF.length() - 9; //Si es igual a 0 tiene lo deseado, > 1 sobra ese no. caracteres, < 1 faltan ese no.
    }


    public String getNombreComercial() {
        return nombreComercial;
    }
    public String getRazonSocial() {
        return razonSocial;
    }
    public String getRNC() {
        return RNC;
    }
    public String getDireccion() {
        return direccion;
    }
    public String getCabeceraNCF() {
        return cabeceraNCF;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
    public void setRNC(String RNC) {
        this.RNC = RNC;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public void setCabeceraNCF(String cabeceraNCF) {
        this.cabeceraNCF = cabeceraNCF;
    }





}
