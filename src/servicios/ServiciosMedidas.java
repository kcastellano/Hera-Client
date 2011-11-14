package servicios;


import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import conexion.Httpclient;
import dominio.Medida;



public class ServiciosMedidas {

	public Medida enviarMediciones(Medida medida) {
		Medida usuario = new Medida();
		String url = "http://10.0.2.5:8085/HeraServer/resources/medida/create";
		Httpclient connection = new Httpclient();
		Gson gson = new Gson();
		String json = gson.toJson(medida);  
		String result = connection.SendHttpPost(url, json);
		usuario = getMedidaGSON(result);
		return usuario;
	}
	
	public ArrayList<Medida> obtenerMedidasPorPronostico(String fecha1,String fecha2, String id) {
		ArrayList<Medida> lista = null;
		String url = "http://10.0.2.5:8085/HeraServer/resources/pronosticos/fecha1/"+fecha1+"/estudio/"+id+
			"/fecha2/"+ fecha2;
		Httpclient connection = new Httpclient();
		String result = connection.getServiceResult(url);
		if(result.length() == 4){
			lista = null;
		}
		else{
		lista = getAllMedidaGSON(result);
		}
		return lista;
	}
	
	public ArrayList<Medida> obtenerMedidasPorEstudio(String idEstudio) {
		ArrayList<Medida> lista = null;
		String url = "http://10.0.2.5:8085/HeraServer/resources/medida/medidasEstudio/"+ idEstudio;
		Httpclient connection = new Httpclient();
		String result = connection.getServiceResult(url);
		if(result.length() == 4){
			lista = null;
		}
		else{
		lista = getAllMedidaGSON(result);
		}
		return lista;
	}
	
	public Medida getMedidaGSON(String result) {
		Gson gson = new Gson();
		Medida estudio = new Medida();
		estudio = gson.fromJson(result, Medida.class);
		return estudio;
	}
	
	
	public ArrayList<Medida> getAllMedidaGSON(String result) {
		Gson gson = new Gson();

		JsonElement jsonParser = new JsonParser().parse(result);
		JsonArray info = jsonParser.getAsJsonObject().getAsJsonArray("medida");
		ArrayList<Medida> medidas = new ArrayList<Medida>();
		for (int i = 0; i < info.size(); i++) {
			Medida medida = new Medida();
			medida = gson.fromJson(info.get(i), Medida.class);
			medidas.add(medida);
		}
		return medidas;
	}
}
