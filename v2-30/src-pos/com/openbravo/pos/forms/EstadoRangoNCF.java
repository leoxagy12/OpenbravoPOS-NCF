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
public class EstadoRangoNCF extends EstadoNCF  {

    public final static String estadoActivo = "ACTIVO";         // el qeu esta en uso
    public final static String estadoInactivo = "INACTIVO";     // cuando el rango puede usarse pero hay un anterior enel proceso
    public final static String estadoProhibido = "PROHIBIDO";   //Cuando no se quiere utilizar esta numeracion
    public final static String estadoAgotado = "AGOTADO";       //se agoto su conteo

    public EstadoRangoNCF(){

    }

}
