package servicios;


import java.sql.Timestamp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import conexion.Httpclient;
import conexion.TimestampDeserializer;
import dominio.Expediente;

public class ServiciosExpediente {

	public Expediente crearExpediente(Expediente expediente) {
		Expediente expedientes = new Expediente();
		String url = "http://10.0.2.5:8085/HeraServer/resources/expedientes/create";
		Httpclient connection = new Httpclient();
		Gson gson = new Gson();
		String json = gson.toJson(expediente);  
		String result = connection.SendHttpPost(url, json);
		expedientes = getExpedienteGSON(result);
		return expedientes;
	}
	
	public Expediente obtenerUltimoExpediente() {
		Expediente expediente = new Expediente();
		String url = "http://10.0.2.5:8085/HeraServer/resources/expedientes/last";
		Httpclient connection = new Httpclient();
		String result = connection.getServiceResult(url);
		expediente = getExpedienteGSON(result);
		return expediente;
	}
	
	public Expediente getExpedienteGSON(String result) {
		GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(Timestamp.class, new TimestampDeserializer());
	    Gson gson = gsonBuilder.create();
		Expediente expediente = new Expediente();
		expediente = gson.fromJson(result, Expediente.class);
		return expediente;
	}
	
	
}
