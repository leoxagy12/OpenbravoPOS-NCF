/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openbravo.pos.forms;

import com.openbravo.data.loader.*;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.BeanFactoryDataSingle;
import com.openbravo.basic.BasicException;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

/**
 *
 * @author root
 */
public class DataLogicNCF {

    private Session s;
    
    private TableDefinition m_tablaEmpresaNCF;
    private TableDefinition m_tablaTiposNCF;
    private TableDefinition m_tablaRangosNCF;
    private TableDefinition m_tablaAsociacionesNCF;

    public DataLogicNCF() {

    }

    public void init(Session s){
        this.s = s;

        m_tablaEmpresaNCF = new TableDefinition(s,
            "EmpresaNCF" //Nombre de la tabla
            , new String[] {"NombreComercial", "RazonSocial", "Direccion", "RNC", "CabeceraNCF"} //Nombre de los cambos en la BD
            , new Datas[] {Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING} //Tipos de Datas en cada campo
            , new Formats[] {Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING}
            , new int[] {0}
        );

        m_tablaTiposNCF = new TableDefinition(s,
            "TiposNCF" //Nombre de la tabla
            , new String[] {"Id", "Nombre", "Descripcion", "Valor", "RestanteCritico", "Estado"} //Nombre de los cambos en la BD
            , new Datas[] {Datas.INT, Datas.STRING, Datas.STRING, Datas.STRING, Datas.INT, Datas.STRING} //Tipos de Datas en cada campo
            , new Formats[] {Formats.INT, Formats.STRING, Formats.STRING, Formats.STRING, Formats.INT, Formats.STRING}
            , new int[] {0}
        );


        m_tablaRangosNCF = new TableDefinition(s,
            "RangosNCF" //Nombre de la tabla
            , new String[] {"Id", "Tipo", "NumeroRango","NumeroInicial","NumeroFinal","Contador","Estado"} //Nombre de los cambos en la BD
            , new Datas[] {Datas.INT, Datas.INT, Datas.INT, Datas.INT, Datas.INT, Datas.INT, Datas.STRING} //Tipos de Datas en cada campo
            , new Formats[] {Formats.INT, Formats.INT, Formats.INT, Formats.INT, Formats.INT, Formats.INT, Formats.STRING}
            , new int[] {0}
        );

        m_tablaAsociacionesNCF = new TableDefinition(s,
            "AsociacionesNCF" //Nombre de la tabla
            , new String[]  {"Id"        , "Receipt"     , "OriginalNCF" , "Rango"       , "ReferenciadoNCF" , "ClienteRNC"} //Nombre de los cambos en la BD
            , new Datas[]   {Datas.INT   , Datas.STRING  , Datas.STRING  , Datas.INT     , Datas.INT         , Datas.STRING} //Tipos de Datas en cada campo
            , new Formats[] {Formats.INT , Formats.STRING, Formats.STRING, Formats.INT   , Formats.INT       , Formats.STRING}
            , new int[] {0}
        );

        /*
        Tabla: AsociacionesNCF

        Id                  int(30)
	Receipt             varchar(255)
	OriginalNCF         char(19)
	Rango               int(10)
	ReferenciadoNCF     int(30)
	ClienteRNC          varchar(11)

        Clase: AsociacionNCF
         
        private int id = 0;
        private String receiptId = "";
        private String originalNCF = "";
        private RangoNCF rango;
        private int referenciadoId = 0;
        private String clienteRNC = "";
         *
         */

    }

    
    public void setearEmpresaNCF(EmpresaNCF e) throws BasicException {

        limpiarEmpresaNCF();
        salvarEmpresaNCF(e);

    }
    public void limpiarEmpresaNCF() throws BasicException {

        //TRUNCATE TABLE `EmpresaNCF`

        new PreparedSentence(s, "TRUNCATE TABLE EmpresaNCF", null, null).exec();
        
    }
    private void salvarEmpresaNCF(EmpresaNCF e) throws BasicException {

        m_tablaEmpresaNCF.getInsertSentence().exec(e.getNombreComercial(),e.getRazonSocial(),e.getDireccion(),e.getRNC(),e.getCabeceraNCF());

        
    }
    public EmpresaNCF obtenerEmpresaNCF() throws BasicException {

        List listaEmpresaNCF = m_tablaEmpresaNCF.getListSentence().list();

        if(listaEmpresaNCF.size() != 1)
            return null;
        else {

            Object[] o = (Object[]) listaEmpresaNCF.get(0);

            EmpresaNCF e = new EmpresaNCF();
            e.setNombreComercial((String)o[0]);
            e.setRazonSocial((String)o[1]);
            e.setDireccion((String)o[2]);
            e.setRNC((String)o[3]);
            e.setCabeceraNCF((String)o[4]);

            return e;
        }
        

    }


    public int getMaxIdTipoNCF() throws BasicException {
        Integer maxId = (Integer) new PreparedSentence(s,"SELECT MAX(Id) FROM TiposNCF",null,SerializerReadInteger.INSTANCE).find();
        if(maxId == null)
            return 0;
        else
            return maxId;
    }
    public TipoNCF setearTipoNCF(TipoNCF t) throws BasicException {
        t.setId(getMaxIdTipoNCF()+1);
        salvarTipoNCF(t);
        return t;
    }
    public void salvarTipoNCF(TipoNCF t) throws BasicException {

        m_tablaTiposNCF.getInsertSentence().exec(t.getId(),t.getNombre(),t.getDescripcion(),t.getValor(),t.getRestanteCritico(),t.getEstado().getEstado());

    }
    public void borrarTipoNCF(TipoNCF t) throws BasicException {

        //DELETE FROM TiposNCF WHERE Id = t.getId();
        new PreparedSentence(s,"DELETE FROM TiposNCF WHERE Id = ?",SerializerWriteInteger.INSTANCE,null).exec(t.getId());

    }
    public void borrarTipoNCF(int idTipo) throws BasicException {

        //DELETE FROM TiposNCF WHERE Id = t.getId();
        new PreparedSentence(s,"DELETE FROM TiposNCF WHERE Id = ?",SerializerWriteInteger.INSTANCE,null).exec(idTipo);

    }
    public void actualizarTipoNCF(TipoNCF t) throws BasicException {

        //UPDATE TiposNCF SET Nombre = ?, Descripcion = ?, Valor = ?, RestanteCritico = ?, Estado = ? WHERE Id = ?;
        new PreparedSentence(s,"UPDATE TiposNCF SET Nombre = ?, Descripcion = ?, Valor = ?, RestanteCritico = ?, Estado = ? WHERE Id = ?",new SerializerWriteBasic(new Datas[]{Datas.STRING, Datas.STRING, Datas.STRING, Datas.INT, Datas.STRING, Datas.INT}),null).exec(t.getNombre(),t.getDescripcion(),t.getValor(),t.getRestanteCritico(),t.getEstado().getEstado(),t.getId());

    }
    public List<TipoNCF> obtenerListaTiposNCF()throws BasicException {

        //UPDATE TiposNCF SET Nombre = ?, Descripcion = ?, Valor = ?, RestanteCritico = ?, Estado = ? WHERE Id = ?;

        SerializerRead tiposRead = new SerializerRead() {
            public Object readValues(DataRead dr) throws BasicException {
                
                TipoNCF tipo = new TipoNCF();

                tipo.setId(dr.getInt(1));
                tipo.setNombre(dr.getString(2));
                tipo.setDescripcion(dr.getString(3));
                tipo.setValor(dr.getString(4));
                tipo.setRestanteCritico(dr.getInt(5));
                
                EstadoTipoNCF esta = new EstadoTipoNCF();
                esta.setEstado(dr.getString(6));
                
                tipo.setEstado(esta);

                return tipo;
            }
        };

        return m_tablaTiposNCF.getListSentence(tiposRead).list();
    }
    public TipoNCF obtenerTipoNCF(int idTipo) throws BasicException {

        List l = obtenerListaTiposNCF();

        for(int i = 0; i < l.size() ; i++) {
            TipoNCF tipo =  (TipoNCF) l.get(i);
            if(tipo.getId() == idTipo)
                return tipo;

        }
        return null;






    }


    public SerializerRead rangoSerRead() {
        return new SerializerRead() {
            public Object readValues(DataRead dr) throws BasicException {

                RangoNCF rango = new RangoNCF();

                rango.setId(dr.getInt(1));
                rango.setTipo(obtenerTipoNCF(dr.getInt(2)));
                rango.setNumeroRango(dr.getInt(3));
                rango.setNumeroInicial(dr.getInt(4));
                rango.setNumeroFinal(dr.getInt(5));
                rango.setContador(dr.getInt(6));

                EstadoRangoNCF esta = new EstadoRangoNCF();
                esta.setEstado(dr.getString(7));

                rango.setEstado(esta);

                return rango;
            }
        };

    }
    public int getMaxIdRangoNCF() throws BasicException {
        Integer maxId = (Integer) new PreparedSentence(s,"SELECT MAX(Id) FROM RangosNCF",null,SerializerReadInteger.INSTANCE).find();

        if(maxId == null)
            return 0;
        else
            return maxId;
    }
    public int getMaxNumeroRangoForTipoId(int idTipo) throws BasicException {
        Integer maxNumRang = (Integer) new PreparedSentence(s,"SELECT MAX(NumeroRango) FROM RangosNCF WHERE Tipo = ?",SerializerWriteInteger.INSTANCE,SerializerReadInteger.INSTANCE).find(idTipo);

        if(maxNumRang == null)
            return 0; //OJO esto implica que no existe un rango para dado tipo
        else
            return maxNumRang;
    }
    public RangoNCF setearRangoNCF(RangoNCF r) throws BasicException {
        r.setId(getMaxIdRangoNCF()+1);
        r.setNumeroRango(getMaxNumeroRangoForTipoId(r.getTipo().getId())+1);
        salvarRangoNCF(r);
        return r;
    }
    public void salvarRangoNCF(RangoNCF r) throws BasicException {

        m_tablaRangosNCF.getInsertSentence().exec(r.getId(),r.getTipo().getId(),r.getNumeroRango(),r.getNumeroInicial(),r.getNumeroFinal(),r.getContador(),r.getEstado().getEstado());

    }
    public void actualizarRangoNCF(RangoNCF r) throws BasicException {


        new PreparedSentence(s,"UPDATE RangosNCF SET Tipo = ?, NumeroRango = ?, NumeroInicial = ?, NumeroFinal = ?, Contador = ?, Estado = ? WHERE Id = ?",
                new SerializerWriteBasic(new Datas[]{Datas.INT, Datas.INT, Datas.INT, Datas.INT, Datas.INT, Datas.STRING, Datas.INT}),null).exec(
                    r.getTipo().getId(),
                    r.getNumeroRango(),
                    r.getNumeroInicial(),
                    r.getNumeroFinal(),
                    r.getContador(),
                    r.getEstado().getEstado(),
                    r.getId()
                );

    }
    public void borrarRangoNCF(RangoNCF r) throws BasicException {

        new PreparedSentence(s,"DELETE FROM RangosNCF WHERE Id = ?",SerializerWriteInteger.INSTANCE,null).exec(r.getId());

    }
    public void borrarRangoNCF(int idRango) throws BasicException {

        new PreparedSentence(s,"DELETE FROM RangosNCF WHERE Id = ?",SerializerWriteInteger.INSTANCE,null).exec(idRango);

    }
    public List<RangoNCF> obtenerListaRangosNCF() throws BasicException {

        SerializerRead rangosRead = rangoSerRead();


        return new PreparedSentence(s,"SELECT * FROM RangosNCF ORDER BY Tipo, NumeroRango",null,rangosRead).list();
        
        
    }
    public RangoNCF obtenerRangoNCF(int idRango) throws  BasicException {

        List l = obtenerListaRangosNCF();

        for(int i = 0; i < l.size() ; i++) {
            RangoNCF rango =  (RangoNCF) l.get(i);
            if(rango.getId() == idRango)
                return rango;

        }
        return null;


    }
    public List<RangoNCF> obtenerListaRangosNCFPorTipo(int idTipo) throws  BasicException {

        List listaTodos = obtenerListaRangosNCF();

        List listaDelTipo = new ArrayList();

        for(int i = 0; i < listaTodos.size() ; i++) {
            RangoNCF rango =  (RangoNCF) listaTodos.get(i);
            if(rango.getTipo().getId() == idTipo)
                listaDelTipo.add(rango);

        }
        return listaDelTipo;


    }
    public List<RangoNCF> obtenerListaRangosNCFPorTipoPorEstado(int idTipo, String estado) throws  BasicException {

        List listaDelTipo = obtenerListaRangosNCFPorTipo(idTipo);

        List listaDelEstado = new ArrayList();

        for(int i = 0; i < listaDelTipo.size() ; i++) {
            RangoNCF rango =  (RangoNCF) listaDelTipo.get(i);
            if( rango.getEstado().getEstado().equals(estado) )
                listaDelEstado.add(rango);

        }
        return listaDelEstado;


    }
    public RangoNCF obtenerRangoNCFPorTipoActivo(int idTipo) throws  BasicException {

        return (RangoNCF) obtenerListaRangosNCFPorTipoPorEstado(idTipo,EstadoRangoNCF.estadoActivo).get(0);

    }
    public RangoNCF obtenerRangoNCFPorTipoPorNumeroRango(int idTipo, int numeroRango) throws  BasicException {

        
        return (RangoNCF) new PreparedSentence(s,"SELECT * FROM RangosNCF WHERE Tipo = ? AND NumeroRango = ?",new SerializerWriteBasic(new Datas[]{Datas.INT, Datas.INT}),rangoSerRead()).find(idTipo,numeroRango);


    }
    public RangoNCF obtenerRangoNCFPorTipoProximoInactivo(int idTipo) throws  BasicException {

        RangoNCF activoActual = (RangoNCF) obtenerListaRangosNCFPorTipoPorEstado(idTipo,EstadoRangoNCF.estadoActivo).get(0);
        RangoNCF inactivoProximo = obtenerRangoNCFPorTipoPorNumeroRango(idTipo,activoActual.getNumeroRango()+1);
        return inactivoProximo;

    }

 
    public SerializerRead asociacionSerRead(){
        return new SerializerRead() {
            public Object readValues(DataRead dr) throws BasicException {

                AsociacionNCF asc = new AsociacionNCF();

                asc.setId(dr.getInt(1));
                asc.setReceiptId(dr.getString(2));
                asc.setOriginalNCF(dr.getString(3));
                asc.setRango(obtenerRangoNCF(dr.getInt(4)));
                asc.setReferenciadoId(dr.getInt(5));
                asc.setClienteRNC(dr.getString(6));


                return asc;
            }
        };
    }
    public int getMaxIdAsociacionNCF() throws BasicException {
        Integer maxId = (Integer) new PreparedSentence(s,"SELECT MAX(Id) FROM AsociacionesNCF",null,SerializerReadInteger.INSTANCE).find();

        if(maxId == null)
            return 0;
        else
            return maxId;
    }
    public AsociacionNCF setearAsociacionNCF(AsociacionNCF a) throws BasicException {
        a.setId(getMaxIdAsociacionNCF()+1);
        salvarAsociacionNCF(a);
        return a;
    }
    public void salvarAsociacionNCF(AsociacionNCF a) throws BasicException {

        m_tablaAsociacionesNCF.getInsertSentence().exec(
                a.getId(),
                a.getReceiptId(),
                a.getOriginalNCF(),
                a.getRango().getId(),
                a.getReferenciadoId(),
                a.getClienteRNC()
                );
        /*
        Tabla: AsociacionesNCF

        Id                  int(30)
	Receipt             varchar(255)
	OriginalNCF         char(19)
	Rango               int(10)
	ReferenciadoNCF     int(30)
	ClienteRNC          varchar(11)

        Clase: AsociacionNCF

        private int id = 0;
        private String receiptId = "";
        private String originalNCF = "";
        private RangoNCF rango;
        private int referenciadoId = 0;
        private String clienteRNC = "";
         *
         */
    }
    public void actualizarAsociacionNCF(AsociacionNCF a) throws BasicException {


        new PreparedSentence(s,"UPDATE AsociacionesNCF SET Receipt = ?, OriginalNCF = ?, Rango = ?, ReferenciadoNCF = ?, ClienteRNC = ? WHERE Id = ?",
                new SerializerWriteBasic(new Datas[]{Datas.STRING, Datas.STRING, Datas.INT, Datas.INT, Datas.STRING, Datas.INT}),null).exec(
                    a.getReceiptId(),
                    a.getOriginalNCF(),
                    a.getRango().getId(),
                    a.getReferenciadoId(),
                    a.getClienteRNC(),
                    a.getId()
                );

    }
    public void borrarAsociacionNCF(AsociacionNCF a) throws BasicException {

        borrarAsociacionNCF(a.getId());

    }
    public void borrarAsociacionNCF(int idAsc) throws BasicException {

        new PreparedSentence(s,"DELETE FROM AsociacionesNCF WHERE Id = ?",SerializerWriteInteger.INSTANCE,null).exec(idAsc);

    }
    public AsociacionNCF obtenerAsociacionNCF(int idAsc) throws  BasicException {

        SerializerRead ascRead = asociacionSerRead();

        return (AsociacionNCF) new PreparedSentence(s,"SELECT * FROM AsociacionesNCF WHERE Id = ?",SerializerWriteInteger.INSTANCE,ascRead).find(idAsc);
    }
    public AsociacionNCF obtenerAsociacionNCF(String receiptId) throws  BasicException {

        SerializerRead ascRead = asociacionSerRead();

        return (AsociacionNCF) new PreparedSentence(s,"SELECT * FROM AsociacionesNCF WHERE Receipt = ?",SerializerWriteString.INSTANCE,ascRead).find(receiptId);
    }

    
}
