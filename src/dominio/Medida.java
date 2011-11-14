/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;



/**
 *
 * @author Khaterine Castellano
 */

public class Medida{
    
    private int idMedida;
    private String nombreMedida;
    private String feto;
    private double resultadoNumerico;
    private String resultadoSemanas;
    private String unidad;
    private String tipo;
    private int percentil;
    private int fk_idEstudio;
    private int fk_idExpediente;

    public Medida() {
    }

    public Medida(int idMedida, String nombreMedida, String feto, float resultadoNumerico, String resultadoSemanas, String unidad, String tipo, int percentil, int fk_idEstudio, int fk_idExpediente) {
        this.idMedida = idMedida;
        this.nombreMedida = nombreMedida;
        this.feto = feto;
        this.resultadoNumerico = resultadoNumerico;
        this.resultadoSemanas = resultadoSemanas;
        this.unidad = unidad;
        this.tipo = tipo;
        this.percentil = percentil;
        this.fk_idEstudio = fk_idEstudio;
        this.fk_idExpediente = fk_idExpediente;
    }

    public String getFeto() {
        return feto;
    }

    public void setFeto(String feto) {
        this.feto = feto;
    }

    public int getFk_idEstudio() {
        return fk_idEstudio;
    }

    public void setFk_idEstudio(int fk_idEstudio) {
        this.fk_idEstudio = fk_idEstudio;
    }

    public int getFk_idExpediente() {
        return fk_idExpediente;
    }

    public void setFk_idExpediente(int fk_idExpediente) {
        this.fk_idExpediente = fk_idExpediente;
    }

    public int getIdMedida() {
        return idMedida;
    }

    public void setIdMedida(int idMedida) {
        this.idMedida = idMedida;
    }

    public String getNombreMedida() {
        return nombreMedida;
    }

    public void setNombreMedida(String nombreMedida) {
        this.nombreMedida = nombreMedida;
    }

    public int getPercentil() {
        return percentil;
    }

    public void setPercentil(int percentil) {
        this.percentil = percentil;
    }

    public double getResultadoNumerico() {
        return resultadoNumerico;
    }

    public void setResultadoNumerico(double resultadoNumerico) {
        this.resultadoNumerico = resultadoNumerico;
    }

    public String getResultadoSemanas() {
        return resultadoSemanas;
    }

    public void setResultadoSemanas(String resultadoSemanas) {
        this.resultadoSemanas = resultadoSemanas;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
    
   
}
