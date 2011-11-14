package servicios;


import com.google.gson.Gson;

import conexion.Httpclient;
import dominio.ExpedienteUsuario;

public class ServiciosExpedienteUsuario {

	public ExpedienteUsuario obtenerExpedientePaciente(String cedula) {
		ExpedienteUsuario expediente = new ExpedienteUsuario();
		String url = "http://10.0.2.5:8085/HeraServer/resources/expedienteusuario/expediente/"+cedula;
		Httpclient connection = new Httpclient();
		String result = connection.getServiceResult(url);
		expediente = getExpedienteUsuarioGSON(result);
		return expediente;
	}
	
	public ExpedienteUsuario obtenerPacientePorExpediente(String expediente) {
		ExpedienteUsuario expediente1 = new ExpedienteUsuario();
		String url = "http://10.0.2.5:8085/HeraServer/resources/expedienteusuario/expedienteUsuario/"+ expediente;
		Httpclient connection = new Httpclient();
		String result = connection.getServiceResult(url);
		expediente1 = getExpedienteUsuarioGSON(result);
		return expediente1;
	}
	
	public ExpedienteUsuario crearExpedienteUsuario(ExpedienteUsuario expediente) {
		ExpedienteUsuario expedientes = new ExpedienteUsuario();
		String url = "http://10.0.2.5:8085/HeraServer/resources/expedienteusuario/create";
		Httpclient connection = new Httpclient();
		Gson gson = new Gson();
		String json = gson.toJson(expediente);  
		String result = connection.SendHttpPost(url, json);
		expedientes = getExpedienteUsuarioGSON(result);
		return expedientes;
	}
	
	
	public ExpedienteUsuario getExpedienteUsuarioGSON(String result) {
		Gson gson = new Gson();
		ExpedienteUsuario estudio = new ExpedienteUsuario();
		estudio = gson.fromJson(result, ExpedienteUsuario.class);
		return estudio;
	}
	
	
	public ExpedienteUsuario actualizarExpedienteUsuario(ExpedienteUsuario expedienteUsuario) {
		ExpedienteUsuario expedienteUsuarioCreado = new ExpedienteUsuario();
		String url = "http://10.0.2.5:8085/HeraServer/resources/expedienteusuario/modify";
		Httpclient connection = new Httpclient();
		Gson gson = new Gson();
		String json = gson.toJson(expedienteUsuario);
		String result = connection.SendHttpPut(url, json);
		if (result == null) {
			expedienteUsuarioCreado = null;
		} else {
			expedienteUsuarioCreado = getExpedienteUsuarioGSON(result);
		}
		return expedienteUsuarioCreado;
	}
	
	
}
