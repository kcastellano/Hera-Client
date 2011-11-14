/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;



/**
 *
 * @author ecomobile
 */

public class ExpedienteUsuario {

    private int idExpedienteUsuario;
    private int fk_idExpediente;
    private int fk_idUsuario;
    private String rol_expediente;
    private int compartir;

  

    public ExpedienteUsuario() {
    }

    public ExpedienteUsuario(int idExpedienteUsuario, int fk_idExpediente, int fk_idUsuario, String rol_expediente, int compartir) {
        this.idExpedienteUsuario = idExpedienteUsuario;
        this.fk_idExpediente = fk_idExpediente;
        this.fk_idUsuario = fk_idUsuario;
        this.rol_expediente = rol_expediente;
        this.compartir = compartir;
    }

    
    public int getCompartir() {
		return compartir;
	}

	public void setCompartir(int compartir) {
		this.compartir = compartir;
	}

	public int getFk_idExpediente() {
        return fk_idExpediente;
    }

    public void setFk_idExpediente(int fk_idExpediente) {
        this.fk_idExpediente = fk_idExpediente;
    }

    public int getFk_idUsuario() {
        return fk_idUsuario;
    }

    public void setFk_idUsuario(int fk_idUsuario) {
        this.fk_idUsuario = fk_idUsuario;
    }

    public int getIdExpedienteUsuario() {
        return idExpedienteUsuario;
    }

    public void setIdExpedienteUsuario(int idExpedienteUsuario) {
        this.idExpedienteUsuario = idExpedienteUsuario;
    }

    public String getRol_expediente() {
        return rol_expediente;
    }

    public void setRol_expediente(String rol_expediente) {
        this.rol_expediente = rol_expediente;
    }

}
   