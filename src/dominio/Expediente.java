/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.sql.Timestamp;

/**
 *
 * @author ecomobile
 */
public class Expediente {
    
    private int idExpediente;
    private Timestamp fechaExpediente;

    public Expediente() {
    }

    public Expediente(int id, Timestamp fechaExpediente) {
        this.idExpediente = id;
        this.fechaExpediente = fechaExpediente;
    }

    public Timestamp getFechaExpediente() {
        return fechaExpediente;
    }

    public void setFechaExpediente(Timestamp fechaExpediente) {
        this.fechaExpediente = fechaExpediente;
    }

    public int getidExpediente() {
        return idExpediente;
    }

    public void setidExpediente(int id) {
        this.idExpediente = id;
    }
    
    
}
