package dominio;

public class Usuario {
	 private int cedula;
	    private String nombrePaciente;
	    private String apellidoPaciente;
	    private String email;
	    private String rol;
	    private String password;
		public int getCedula() {
			return cedula;
		}
		public void setCedula(int cedula) {
			this.cedula = cedula;
		}
		public String getNombrePaciente() {
			return nombrePaciente;
		}
		public void setNombrePaciente(String nombrePaciente) {
			this.nombrePaciente = nombrePaciente;
		}
		public String getApellidoPaciente() {
			return apellidoPaciente;
		}
		public void setApellidoPaciente(String apellidoPaciente) {
			this.apellidoPaciente = apellidoPaciente;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getRol() {
			return rol;
		}
		public void setRol(String rol) {
			this.rol = rol;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
	    
}
