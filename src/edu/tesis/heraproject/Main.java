package edu.tesis.heraproject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import servicios.ServiciosEstudio;
import servicios.ServiciosExpedienteUsuario;
import servicios.ServiciosUsuario;
import vistas.MenuPacientes;
import vistas.VistaPaciente;
import modulos.AgregarUsuario;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import dominio.*;

public class Main extends Activity {
	private Button login;
	private Button newUser;
	private TextView txt;
	private Typeface font;
	private EditText username;
	private EditText password;
	private AlertDialog.Builder emailAlert;
	private ProgressDialog dialog;
	private String user = null;
	private String pass = null;
	private String[] listaCedulaPrimer = null;
	private String[] listaCedulaSegundo = null;
	private String[] listaCedulaTercero = null;
	private String[] listaEmailPrimer = null;
	private String[] listaEmailSegundo = null;
	private String[] listaEmailTercero = null;
	private int cedula;
	private String nombre;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.iniciar_sesion);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titulo);

		txt = (TextView) findViewById(R.id.title);
		txt.setText("Hera");
		font = Typeface.createFromAsset(getAssets(), "Chunk.ttf");
		txt.setTypeface(font);
		login = (Button) findViewById(R.id.login);
		newUser = (Button) findViewById(R.id.crearUsuario);
		username = (EditText) findViewById(R.id.email);
		password = (EditText) findViewById(R.id.password);
		emailAlert = new AlertDialog.Builder(this);
		emailAlert.setTitle("Inicio sesion");
		emailAlert.setPositiveButton("Ok", null);
		emailAlert.setCancelable(true);
		dialog = new ProgressDialog(this);
		dialog.setIndeterminate(true);
		dialog.setTitle("Iniciando sesi—n");
		dialog.setMessage("Espere un momento");

		Button.OnClickListener validateUserOnClickListener = new Button.OnClickListener() {

			public void onClick(View v) {
				CallWebServiceTask task = new CallWebServiceTask();
				task.applicationContext = Main.this;
				user = username.getText().toString();
				pass = password.getText().toString();
				task.execute(user, pass);
			}
		};

		Button.OnClickListener createUserOnClickListener = new Button.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(Main.this, AgregarUsuario.class);
				startActivity(intent);
			}
		};

		login.setOnClickListener(validateUserOnClickListener);
		newUser.setOnClickListener(createUserOnClickListener);
	}

	public class CallWebServiceTask extends AsyncTask<String, Void, Integer> {

		protected Context applicationContext;
		int resultado = 0;

		@Override
		protected void onPreExecute() {
			dialog.show();
		}

		protected void onPostExecute(Integer resultado) {
			dialog.cancel();
			if (resultado == -1) {
				emailAlert
						.setMessage("El formato del correo no es v‡lido.Por favor ingrese de nuevo el correo electr—nico");
				emailAlert.create().show();
			} else if (resultado == -2) {
				emailAlert.setMessage("El usuario no existe.");
				emailAlert.create().show();

			} else if (resultado == -3) {
				emailAlert
						.setMessage("El usuario y la contrase–a no coinciden.\n Por favor ingrese de nuevo los datos");
				emailAlert.create().show();
			} else {
				ServiciosUsuario userServices = new ServiciosUsuario();
				Usuario usuario = new Usuario();
				usuario = userServices.obtenerUsuarioPorEmail(user);
				nombre = usuario.getNombrePaciente() + " "
						+ usuario.getApellidoPaciente();
				cedula = usuario.getCedula();

				if (usuario.getRol().equalsIgnoreCase("Doctor")) {
					ArrayList<Usuario> listaPrimerTrimestre = new ArrayList<Usuario>();
					ArrayList<Usuario> listaSegundoTrimestre = new ArrayList<Usuario>();
					ArrayList<Usuario> listaTercerTrimestre = new ArrayList<Usuario>();

					listaPrimerTrimestre = getPacientesDoctor(resultado, 1);
					listaSegundoTrimestre = getPacientesDoctor(resultado, 2);
					listaTercerTrimestre = getPacientesDoctor(resultado, 3);
					String[] listaPacientesPrimer = null;
					String[] listaPacientesSegundo = null;
					String[] listaPacientesTercero = null;
					listaPacientesPrimer = getPacientesPorTrimestre(
							listaPrimerTrimestre, 1);
					listaPacientesSegundo = getPacientesPorTrimestre(
							listaSegundoTrimestre, 2);
					listaPacientesTercero = getPacientesPorTrimestre(
							listaTercerTrimestre, 3);
					Intent intent = new Intent(Main.this, MenuPacientes.class);
					intent.putExtra("CedulaDoctor", String.valueOf(cedula));
					intent.putExtra("PrimerTrimestre", listaPacientesPrimer);
					intent.putExtra("SegundoTrimestre", listaPacientesSegundo);
					intent.putExtra("TercerTrimestre", listaPacientesTercero);
					intent.putExtra("CedulasPrimer", listaCedulaPrimer);
					intent.putExtra("CedulasSegundo", listaCedulaSegundo);
					intent.putExtra("CedulasTercero", listaCedulaTercero);
					intent.putExtra("EmailPrimer", listaEmailPrimer);
					intent.putExtra("EmailSegundo", listaEmailSegundo);
					intent.putExtra("EmailTercero", listaEmailTercero);
					
					intent.putExtra("Rol", "Doctor");
					startActivity(intent);
				} else {
					ServiciosExpedienteUsuario servicios = new ServiciosExpedienteUsuario();
					ExpedienteUsuario expediente1 = new ExpedienteUsuario();
					expediente1 = servicios.obtenerExpedientePaciente(String
							.valueOf(cedula));

					if (expediente1.getRol_expediente().equalsIgnoreCase(
							"Paciente")) {
						int trimestre = getUltimoEstudioTrimestre(expediente1
								.getFk_idExpediente());
						Intent intent = new Intent(Main.this,
								VistaPaciente.class);
						intent.putExtra("Cedula", String.valueOf(cedula));
						intent.putExtra("Nombre", nombre);
						intent.putExtra("Trimestre", String.valueOf(trimestre)
								+ "¡");
						intent.putExtra("Rol", "Paciente");
						startActivity(intent);
					} else {
						ServiciosExpedienteUsuario servicios2 = new ServiciosExpedienteUsuario();
						ExpedienteUsuario expediente2 = new ExpedienteUsuario();
						expediente2 = servicios2.obtenerPacientePorExpediente(String.valueOf(expediente1.getFk_idExpediente())); 
						int trimestre = getUltimoEstudioTrimestre(expediente1
								.getFk_idExpediente());
						ServiciosUsuario servicioUsuario = new ServiciosUsuario();
						Usuario paciente = new Usuario();
						paciente = servicioUsuario.obtenerUsuarioPorCedula(String.valueOf(expediente2.getFk_idUsuario()));
						Intent intent = new Intent(Main.this,
								VistaPaciente.class);
						String nombrePaciente = paciente.getNombrePaciente() + " " + paciente.getApellidoPaciente();
						intent.putExtra("Cedula", String.valueOf(paciente.getCedula()));
						intent.putExtra("Nombre", nombrePaciente);
						intent.putExtra("Trimestre", String.valueOf(trimestre)
								+ "¡");
						intent.putExtra("Rol","Familiar");
						intent.putExtra("CedulaFamiliar",String.valueOf(cedula));
						startActivity(intent);
					}

				}
			}
		}

		@Override
		protected Integer doInBackground(String... params) {
			String username = params[0];
			String password = params[1];
			if ((checkEmailCorrect(username) == true)
					&& (password.length() != 0)) {
				resultado = authenticateUser(user, pass);
			} else {
				resultado = -1;
			}
			return resultado;
		}

	}

	public int authenticateUser(String email, String password) {
		ServiciosUsuario userServices = new ServiciosUsuario();
		Usuario usuario = new Usuario();
		usuario = userServices.obtenerUsuarioPorEmail(email);
		int resultado = 0;
		if (usuario == null) {
			resultado = -2;
		} else if (!usuario.getPassword().equalsIgnoreCase(password)) {
			resultado = -3;
		} else if (usuario.getPassword().equalsIgnoreCase(password)) {
			resultado = usuario.getCedula();
		}
		return resultado;
	}

	public ArrayList<Usuario> getPacientesDoctor(int cedula, int trimestre) {
		ArrayList<Usuario> lista = null;
		ServiciosUsuario userServices = new ServiciosUsuario();
		lista = userServices.obtenerPacientesPorDoctor(cedula, trimestre);
		return lista;
	}

	public boolean checkEmailCorrect(String Email) {

		String pttn = "^\\D.+@.+\\.[a-z]+";
		Pattern p = Pattern.compile(pttn);
		Matcher m = p.matcher(Email);

		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public String[] getPacientesPorTrimestre(ArrayList<Usuario> lista,
			int trimestre) {
		String[] listaPacientes = null;
		if (lista == null) {
			listaPacientes = new String[1];
		} else {
			listaPacientes = new String[lista.size()];
		}
		switch (trimestre) {
		case 1:
			if (lista == null) {
				listaPacientes = null;
			} else {
				listaCedulaPrimer = new String[lista.size()];
				listaEmailPrimer = new String[lista.size()];
				for (int i = 0; i < lista.size(); i++) {
					Usuario usuario = new Usuario();
					usuario = lista.get(i);
					String nombre = usuario.getNombrePaciente() + " "
							+ usuario.getApellidoPaciente();
					listaPacientes[i] = nombre;
					listaCedulaPrimer[i] = String.valueOf(usuario.getCedula());
					listaEmailPrimer[i] = usuario.getEmail();
				}
			}
			break;
		case 2:
			if (lista == null) {
				listaPacientes = null;
			} else {
				listaCedulaSegundo = new String[lista.size()];
				listaEmailSegundo = new String[lista.size()];
				for (int i = 0; i < lista.size(); i++) {
					Usuario usuario = new Usuario();
					usuario = lista.get(i);
					String nombre = usuario.getNombrePaciente() + " "
							+ usuario.getApellidoPaciente();
					listaPacientes[i] = nombre;
					listaCedulaSegundo[i] = String.valueOf(usuario.getCedula());
					listaEmailSegundo[i] = usuario.getEmail();
				}
			}
			break;
		case 3:
			if (lista == null) {
				listaPacientes = null;
			} else {
				listaCedulaTercero = new String[lista.size()];
				listaEmailTercero = new String[lista.size()];
				for (int i = 0; i < lista.size(); i++) {
					Usuario usuario = new Usuario();
					usuario = lista.get(i);
					String nombre = usuario.getNombrePaciente() + " "
							+ usuario.getApellidoPaciente();
					listaPacientes[i] = nombre;
					listaCedulaTercero[i] = String.valueOf(usuario.getCedula());
					listaEmailTercero[i] = usuario.getEmail();
				}
			}
			break;
		default:
			break;
		}

		return listaPacientes;
	}

	public int getUltimoEstudioTrimestre(int expediente) {
		Estudio estudio = new Estudio();
		ServiciosEstudio servicio = new ServiciosEstudio();
		estudio = servicio.obtenerUltimoEstudioPaciente(expediente);
		return estudio.getTrimestre();

	}

}