package servicios;

import java.sql.Date;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import conexion.DateDeserializer;
import conexion.Httpclient;
import dominio.Diagnostico;
import dominio.Factor;
import dominio.Medida;



public class ServiciosFactor {

	public ArrayList<Factor> obtenerFactorPorTrimestre(String trimestre) {
		ArrayList<Factor> factores = new ArrayList<Factor>();
		String trim = trimestre.substring(0,1);
		String url = "http://10.0.2.5:8085/HeraServer/resources/factor/factores/"+trim;
		Httpclient connection = new Httpclient();
		String result = connection.getServiceResult(url);
		factores = getAllFactorGSON(result);
		return factores;
	}
	
	public Factor getFactorGSON(String result) {
		GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
	    Gson gson = gsonBuilder.create();
	    Factor factor = new Factor();
		factor = gson.fromJson(result, Factor.class);
		return factor;
	}
	
	public ArrayList<Factor> getAllFactorGSON(String result) {
		Gson gson = new Gson();

		JsonElement jsonParser = new JsonParser().parse(result);
		JsonArray info = jsonParser.getAsJsonObject().getAsJsonArray("factor");
		ArrayList<Factor> factores = new ArrayList<Factor>();
		for (int i = 0; i < info.size(); i++) {
			Factor factor = new Factor();
			factor = gson.fromJson(info.get(i), Factor.class);
			factores.add(factor);
		}
		return factores;
	}
}
