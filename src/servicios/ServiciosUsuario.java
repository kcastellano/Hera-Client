package servicios;

import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import conexion.Httpclient;
import dominio.Usuario;

public class ServiciosUsuario {

	public Usuario obtenerUsuarioPorEmail(String email) {
		Usuario usuario = new Usuario();
		String url = "http://10.0.2.5:8085/HeraServer/resources/usuarios/email/"
				+ email;
		Httpclient connection = new Httpclient();
		String result = connection.getServiceResult(url);
		if (result == null) {
			usuario = null;
		} else {
			usuario = getUsuarioGSON(result);
		}
		return usuario;
	}

	public Usuario obtenerUsuarioPorCedula(String cedula) {
		Usuario usuario = new Usuario();
		String url = "http://10.0.2.5:8085/HeraServer/resources/usuarios/id/"
				+ cedula;
		Httpclient connection = new Httpclient();
		String result = connection.getServiceResult(url);
		if (result == null) {
			usuario = null;
		} else {
			usuario = getUsuarioGSON(result);
		}
		return usuario;
	}

	public Usuario actualizarUsuario(Usuario usuario) {
		Usuario usuarioCreado = new Usuario();
		String url = "http://10.0.2.5:8085/HeraServer/resources/usuarios/modify";
		Httpclient connection = new Httpclient();
		Gson gson = new Gson();
		String json = gson.toJson(usuario);
		String result = connection.SendHttpPut(url, json);
		if (result == null) {
			usuarioCreado = null;
		} else {
			usuarioCreado = getUsuarioGSON(result);
		}
		return usuarioCreado;
	}

	public Usuario crearUsuario(Usuario usuario) {
		Usuario usuarioCreado = new Usuario();
		String url = "http://10.0.2.5:8085/HeraServer/resources/usuarios/create";
		Httpclient connection = new Httpclient();
		Gson gson = new Gson();
		String json = gson.toJson(usuario);
		String result = connection.SendHttpPost(url, json);
		if (result == null) {
			usuarioCreado = null;
		} else {
			usuarioCreado = getUsuarioGSON(result);
		}
		return usuarioCreado;
	}

	public ArrayList<Usuario> obtenerPacientesPorDoctor(int cedula,
			int trimestre) {
		ArrayList<Usuario> lista = null;
		String url = null;
		String urlCount = "http://10.0.2.5:8085/HeraServer/resources/usuarios/count/patients/"
			+ cedula + "/trimester/" + trimestre;
		Httpclient connection = new Httpclient();
		String resultCount = connection.getServiceResult(urlCount);
		Usuario usuario = new Usuario();
		usuario = getUsuarioGSON(resultCount);
		String result = null;

		if(usuario.getCedula() == 1){
			url = "http://10.0.2.5:8085/HeraServer/resources/usuarios/one/patients/"
				+ cedula + "/trimester/" + trimestre;
			result = connection.getServiceResult(url);
			Usuario user = new Usuario();
			user = getUsuarioGSON(result);
			lista = new ArrayList<Usuario>();
			lista.add(user);
		}
		else{
			url = "http://10.0.2.5:8085/HeraServer/resources/usuarios/patients/"
				+ cedula + "/trimester/" + trimestre;
			result = connection.getServiceResult(url);
			if(result.length() == 4){
				lista = null;
			}else{
			lista = getAllUsuarioGSON(result);
			}
		}

	
	return lista;
	}
	
	public ArrayList<Usuario> obtenerFamiliares(String expediente) {
		ArrayList<Usuario> lista = null;
		String url = null;
		String urlCount = "http://10.0.2.5:8085/HeraServer/resources/usuarios/count/familiares/"
			+ expediente;
		Httpclient connection = new Httpclient();
		String resultCount = connection.getServiceResult(urlCount);
		Usuario usuario = new Usuario();
		usuario = getUsuarioGSON(resultCount);
		String result = null;

		if(usuario.getCedula() == 1){
			url = "http://10.0.2.5:8085/HeraServer/resources/usuarios/familiar/"
				+ expediente;
			result = connection.getServiceResult(url);
			Usuario user = new Usuario();
			user = getUsuarioGSON(result);
			lista = new ArrayList<Usuario>();
			lista.add(user);
		}
		else{
			url = "http://10.0.2.5:8085/HeraServer/resources/usuarios/familiares/" + expediente;
			result = connection.getServiceResult(url);
			if(result.length() == 4){
				lista = null;
			}else{
			lista = getAllUsuarioGSON(result);
			}
		}

	
	return lista;
	}

	public Usuario getUsuarioGSON(String result) {
		Gson gson = new Gson();
		Usuario usuario = new Usuario();
		usuario = gson.fromJson(result, Usuario.class);
		return usuario;
	}

	public ArrayList<Usuario> getAllUsuarioGSON(String result) {
		Gson gson = new Gson();
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

			JsonElement jsonParser = new JsonParser().parse(result);
			JsonArray info = jsonParser.getAsJsonObject().getAsJsonArray(
					"usuario");
			
			for (int i = 0; i < info.size(); i++) {
				Usuario usuario = new Usuario();
				usuario = gson.fromJson(info.get(i), Usuario.class);
				usuarios.add(usuario);
			}
		
		return usuarios;
	}
}
