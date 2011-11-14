/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package dominio;


import java.sql.*;



/**
 *
 * @author Khaterine Castellano
 */


public class Estudio {

    private int idEstudio;
    private Timestamp fechaEstudio;
    private int trimestre;
    private int fk_idExpediente;

    public Estudio() {
    }

    public int getFk_idExpediente() {
        return fk_idExpediente;
    }

    public void setFk_idExpediente(int fk_idExpediente) {
        this.fk_idExpediente = fk_idExpediente;
    }

    public int getIdEstudio() {
        return idEstudio;
    }

    public void setIdEstudio(int idEstudio) {
        this.idEstudio = idEstudio;
    }

    public Estudio(int id, Timestamp fechaEstudio, int trimestre) {
        this.idEstudio = id;
        this.fechaEstudio = fechaEstudio;
        this.trimestre = trimestre;
    }

    public Timestamp getFechaEstudio() {
        return fechaEstudio;
    }

    public void setFechaEstudio(Timestamp fechaEstudio) {
        this.fechaEstudio = fechaEstudio;
    }

    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
    }

    @Override
    public String toString() {
        return "Estudio{" + "id=" + idEstudio + ", fecha=" + fechaEstudio + 
                ", trimestre=" + trimestre + '}';
    }
    
    
    
}
