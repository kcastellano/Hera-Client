package servicios;

import java.sql.Date;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import conexion.DateDeserializer;
import conexion.Httpclient;
import dominio.Diagnostico;



public class ServiciosDiagnostico {

	public Diagnostico obtenerDiagnosticoPorEstudio(int estudio) {
		Diagnostico diagnostico = new Diagnostico();
		String est = String.valueOf(estudio);
		String url = "http://10.0.2.5:8085/HeraServer/resources/diagnosticos/id/"+est;
		Httpclient connection = new Httpclient();
		String result = connection.getServiceResult(url);
		diagnostico = getDiagnosticoGSON(result);
		return diagnostico;
	}
	
	public Diagnostico getDiagnosticoGSON(String result) {
		GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
	    Gson gson = gsonBuilder.create();
		Diagnostico diagnostico = new Diagnostico();
		diagnostico = gson.fromJson(result, Diagnostico.class);
		return diagnostico;
	}
}
