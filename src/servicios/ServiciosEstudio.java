package servicios;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import conexion.Httpclient;
import conexion.TimestampDeserializer;
import dominio.Estudio;

public class ServiciosEstudio {

	public Estudio obtenerUltimoEstudio() {
		Estudio estudio = new Estudio();
		String url = "http://10.0.2.5:8085/HeraServer/resources/estudios/last";
		Httpclient connection = new Httpclient();
		String result = connection.getServiceResult(url);
		estudio = getEstudioGSON(result);
		return estudio;
	}

	public Estudio enviarMediciones(Estudio estudios) {
		Estudio estudio = new Estudio();
		String url = "http://10.0.2.5:8085/HeraServer/resources/estudios/create";
		Httpclient connection = new Httpclient();
		Gson gson = new Gson();
		String json = gson.toJson(estudios);
		String result = connection.SendHttpPost(url, json);
		estudio = getEstudioGSON(result);
		return estudio;
	}

	public Estudio obtenerUltimoEstudioPaciente(int expediente) {
		Estudio estudio = new Estudio();
		String url = "http://10.0.2.5:8085/HeraServer/resources/estudios/paciente/"
				+ expediente;
		Httpclient connection = new Httpclient();
		String result = connection.getServiceResult(url);
		estudio = getEstudioGSON(result);
		return estudio;
	}

	public ArrayList<Estudio> obtenerEstudiosPorPaciente(int expediente) {
		ArrayList<Estudio> lista = null;
		String urlCount = "http://10.0.2.5:8085/HeraServer/resources/estudios/count/estudios/"
				+ expediente;
		Httpclient connection = new Httpclient();
		String resultCount = connection.getServiceResult(urlCount);
		Estudio estudio = new Estudio();
		estudio = getEstudioGSON(resultCount);
		String result = null;
		String url = null;

		if (estudio.getIdEstudio() == 1) {
			url = "http://10.0.2.5:8085/HeraServer/resources/estudios/one/"+ expediente;
			result = connection.getServiceResult(url);
			Estudio estudio1 = new Estudio();
			estudio1 = getEstudioGSON(result);
			lista = new ArrayList<Estudio>();
			lista.add(estudio1);
		} else {
			url = "http://10.0.2.5:8085/HeraServer/resources/estudios/pacienteEstudios/"
			+ expediente;
			result = connection.getServiceResult(url);
			if (result.length() == 4) {
				lista = null;
			} else {
				lista = getAllEstudioGSON(result);
			}
		}
		return lista;
	}

	public Estudio crearEstudio(Estudio estudio) {
		Estudio estudios = new Estudio();
		String url = "http://10.0.2.5:8085/HeraServer/resources/estudios/create";
		Httpclient connection = new Httpclient();
		Gson gson = new Gson();
		String json = gson.toJson(estudio);
		String result = connection.SendHttpPost(url, json);
		estudios = getEstudioGSON(result);
		return estudios;
	}

	public ArrayList<Estudio> getAllEstudioGSON(String result) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class,
				new TimestampDeserializer());
		Gson gson = gsonBuilder.create();
		JsonElement jsonParser = new JsonParser().parse(result);
		ArrayList<Estudio> estudios = new ArrayList<Estudio>();
		JsonArray info = jsonParser.getAsJsonObject().getAsJsonArray("estudio");

		for (int i = 0; i < info.size(); i++) {
			Estudio estudio = new Estudio();
			estudio = gson.fromJson(info.get(i), Estudio.class);
			estudios.add(estudio);
		}
		return estudios;
	}

	public ArrayList<Estudio> obtenerPrimerEstudioTrimestre(int expediente,
			int trimestre) {
		ArrayList<Estudio> lista = null;
		String urlCount = "http://10.0.2.5:8085/HeraServer/resources/estudios/count/idestudio/"
			+ expediente+"/trimestre/"+trimestre; 	
		Httpclient connection = new Httpclient();
		String resultCount = connection.getServiceResult(urlCount);
		Estudio estudio = new Estudio();
		estudio = getEstudioGSON(resultCount);
		String result = null;
		String url = null;

		if (estudio.getIdEstudio() == 1) {
			url = "http://10.0.2.5:8085/HeraServer/resources/estudios/one/"+ expediente;
			result = connection.getServiceResult(url);
			Estudio estudio1 = new Estudio();
			estudio1 = getEstudioGSON(result);
			lista = new ArrayList<Estudio>();
			lista.add(estudio1);
		} else {
			url = "http://10.0.2.5:8085/HeraServer/resources/estudios/expediente/"
				+ expediente + "/trimestre/" + trimestre;
			result = connection.getServiceResult(url);
			if (result.length() == 4) {
				lista = null;
			} else {
				lista = getAllEstudioGSON(result);
			}
		}
		return lista;
	}

	public Estudio getEstudioGSON(String result) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class,
				new TimestampDeserializer());
		Gson gson = gsonBuilder.create();
		Estudio estudio = new Estudio();
		estudio = gson.fromJson(result, Estudio.class);
		return estudio;
	}
}
