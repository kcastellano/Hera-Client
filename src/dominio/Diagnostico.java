package dominio;


import java.sql.Date;
/**
 *
 * @author katecastellano
 */

public class Diagnostico {

    private int idDiagnostico;
    private String resultado;
    private double edadGestacional;
    private int fk_idEstudio;
    private int fk_idExpediente;
    private Date fechaProbableParto;
    private double certeza;

    public double getCerteza() {
        return certeza;
    }

    public void setCerteza(double certeza) {
        this.certeza = certeza;
    }

    public Date getFechaProbableParto() {
        return fechaProbableParto;
    }

    public void setFechaProbableParto(Date fechaProbableParto) {
        this.fechaProbableParto = fechaProbableParto;
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

    public int getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(int idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }
    private int idEstudio;
    private int idExpediente;

    public int getIdEstudio() {
        return idEstudio;
    }

    public void setIdEstudio(int idEstudio) {
        this.idEstudio = idEstudio;
    }

    public int getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }

    
    public Diagnostico(String resultado, double edadGestacional, int idEstudio, int idExpediente) {
        this.resultado = resultado;
        this.edadGestacional = edadGestacional;
        this.idEstudio = idEstudio;
        this.idExpediente = idExpediente;
    }

    public Diagnostico() {
    }

    public Diagnostico(String resultado, double edadGestacional) {
        this.resultado = resultado;
        this.edadGestacional = edadGestacional;
    }

    public double getEdadGestacional() {
        return edadGestacional;
    }

    public void setEdadGestacional(double edadGestacional) {
        this.edadGestacional = edadGestacional;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }



}
