package modulos;

import java.util.ArrayList;
import java.util.Calendar;

import servicios.ServiciosEstudio;
import servicios.ServiciosExpediente;
import servicios.ServiciosExpedienteUsuario;
import servicios.ServiciosUsuario;
import vistas.MenuPacientes;
import dominio.Estudio;
import dominio.Expediente;
import dominio.ExpedienteUsuario;
import dominio.Usuario;
import edu.tesis.heraproject.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AgregarPaciente extends Activity {

	private TextView txt;
	private Typeface font;
	private String nombre;
	private String apellido;
	private String cedula;
	private String trimestre;
	private String correo;
	private String password;
	private Button nuevoPaciente;
	private EditText nombrePaciente;
	private EditText cedulaPaciente;
	private EditText trimestrePaciente;
	private EditText apellidoPaciente;
	private EditText correoPaciente;
	private AlertDialog.Builder usuarioAlert1;
	private AlertDialog.Builder usuarioAlert2;
	private String cedulaDoctor;
	private ProgressDialog dialogo;
	private String[] listaCedulaPrimer = null;
	private String[] listaCedulaSegundo = null;
	private String[] listaCedulaTercero = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.agregar_paciente);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titulo);

		txt = (TextView) findViewById(R.id.title);
		txt.setText("Hera");
		font = Typeface.createFromAsset(getAssets(), "Chunk.ttf");
		txt.setTypeface(font);

		Intent intent = getIntent();
		cedulaDoctor = intent.getStringExtra("Cedula");
		nuevoPaciente = (Button) findViewById(R.id.agregarPaciente);

		nombrePaciente = (EditText) findViewById(R.id.nombrePaciente);
		cedulaPaciente = (EditText) findViewById(R.id.cedulaPaciente);
		apellidoPaciente = (EditText) findViewById(R.id.apellidoPaciente);
		trimestrePaciente = (EditText) findViewById(R.id.trimestrePaciente);
		correoPaciente = (EditText) findViewById(R.id.correoPaciente);
		
		usuarioAlert1 = new AlertDialog.Builder(this);
		usuarioAlert1.setTitle("Agregar usuario");
		usuarioAlert1.setCancelable(true);
		usuarioAlert1.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						callMenuPatients();
					}
				});

		usuarioAlert2 = new AlertDialog.Builder(this);
		usuarioAlert2.setTitle("Agregar usuario");
		usuarioAlert2.setCancelable(true);
		usuarioAlert2.setPositiveButton("Ok", null);

		Button.OnClickListener createUserOnClickListener = new Button.OnClickListener() {

			public void onClick(View v) {
				CallWebServiceTask task = new CallWebServiceTask();
				task.applicationContext = AgregarPaciente.this;
				nombre = nombrePaciente.getText().toString();
				apellido = apellidoPaciente.getText().toString();
				cedula = cedulaPaciente.getText().toString();
				trimestre = trimestrePaciente.getText().toString();
				correo = correoPaciente.getText().toString();
				task.execute(cedula, trimestre);
			}
		};

		dialogo = new ProgressDialog(this);
		dialogo.setIndeterminate(true);
		dialogo.setTitle("Crear paciente");
		dialogo.setMessage("Espere un momento");

		nuevoPaciente.setOnClickListener(createUserOnClickListener);

	}

	public boolean validarUsuarioPorCedula(String cedula) {
		boolean resultado = false;
		ServiciosUsuario servicios = new ServiciosUsuario();
		Usuario usuario = new Usuario();
		usuario = servicios.obtenerUsuarioPorCedula(cedula);
		if (usuario != null) {
			usuarioAlert2.setMessage("El usuario ya existe.");
			usuarioAlert2.create().show();

		} else {
			resultado = agregarUsuario();
		}
		return resultado;
	}

	public boolean agregarUsuario() {
		boolean validate = false;
		Usuario usuario = new Usuario();
		Usuario usuarioCreado = new Usuario();
		usuario.setCedula(Integer.valueOf(cedula));
		usuario.setNombrePaciente(nombre);
		usuario.setApellidoPaciente(apellido);
		usuario.setEmail(correo);
		password = "1234";
		usuario.setPassword(password);
		usuario.setRol("Usuario");

		ServiciosUsuario servicio = new ServiciosUsuario();
		usuarioCreado = servicio.crearUsuario(usuario);
		if (usuarioCreado == null) {
			usuarioAlert2.setMessage("El usuario no pudo ser agregado.");
			usuarioAlert2.create().show();
			validate = false;
		} else {

			boolean resultado1 = agregarExpediente();
			boolean resultado2 = agregarExpedienteUsuario();
			boolean resultado3 = agregarEstudio();
			if (resultado1 && resultado2 && resultado3) {

				validate = true;
			} else {

				validate = false;
			}

		}
		return validate;
	}

	public boolean agregarExpediente() {
		boolean validate = false;
		Expediente expediente = new Expediente();
		Expediente expedienteFinal = new Expediente();
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(
				now.getTime());

		expediente.setFechaExpediente(currentTimestamp);

		ServiciosExpediente servicios = new ServiciosExpediente();
		expedienteFinal = servicios.crearExpediente(expediente);

		if (expedienteFinal != null) {
			validate = true;
		} else {
			validate = false;
		}
		return validate;
	}

	public boolean agregarExpedienteUsuario() {
		boolean validate = false;
		ExpedienteUsuario expedienteUsuario = new ExpedienteUsuario();
		ExpedienteUsuario expedienteUsuarioPaciente = new ExpedienteUsuario();
		ExpedienteUsuario expedienteUsuarioFinal = new ExpedienteUsuario();
		Expediente expediente = new Expediente();
		ServiciosExpediente servicios = new ServiciosExpediente();
		expediente = servicios.obtenerUltimoExpediente();

		expedienteUsuario.setFk_idExpediente(expediente.getidExpediente());
		expedienteUsuario.setFk_idUsuario(Integer.valueOf(cedulaDoctor));
		String rol = "Doctor";
		expedienteUsuario.setRol_expediente(rol);
		expedienteUsuario.setCompartir(1);

		ServiciosExpedienteUsuario serviciosExpedienteUsuario = new ServiciosExpedienteUsuario();
		expedienteUsuarioFinal = serviciosExpedienteUsuario
				.crearExpedienteUsuario(expedienteUsuario);
		
		expedienteUsuarioPaciente.setFk_idExpediente(expediente.getidExpediente());
		expedienteUsuarioPaciente.setFk_idUsuario(Integer.valueOf(cedula));
		expedienteUsuarioPaciente.setRol_expediente("Paciente");
		expedienteUsuario.setCompartir(0);
		
		expedienteUsuarioFinal = serviciosExpedienteUsuario
		.crearExpedienteUsuario(expedienteUsuarioPaciente);
		if (expedienteUsuarioFinal != null) {
			validate = true;
		} else {
			validate = false;
		}
		return validate;
	}

	public boolean agregarEstudio() {
		boolean validate = false;
		Estudio estudio = new Estudio();
		Estudio estudioFinal = new Estudio();
		Expediente expediente = new Expediente();
		ServiciosExpediente servicios = new ServiciosExpediente();
		expediente = servicios.obtenerUltimoExpediente();

		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(
				now.getTime());

		estudio.setFechaEstudio(currentTimestamp);
		estudio.setTrimestre(Integer.valueOf(trimestrePaciente.getText()
				.toString()));
		estudio.setFk_idExpediente(expediente.getidExpediente());

		ServiciosEstudio servicio = new ServiciosEstudio();
		estudioFinal = servicio.crearEstudio(estudio);
		if (estudioFinal != null) {
			validate = true;
		} else {
			validate = false;
		}
		return validate;
	}
	

	public void callMenuPatients() {
		ArrayList<Usuario> listaPrimerTrimestre = new ArrayList<Usuario>();
		ArrayList<Usuario> listaSegundoTrimestre = new ArrayList<Usuario>();
		ArrayList<Usuario> listaTercerTrimestre = new ArrayList<Usuario>();
		int cedulaDoc = Integer.valueOf(cedulaDoctor);
		listaPrimerTrimestre = getPacientesDoctor(cedulaDoc, 1);
		listaSegundoTrimestre = getPacientesDoctor(cedulaDoc, 2);
		listaTercerTrimestre = getPacientesDoctor(cedulaDoc, 3);
		String[] listaPacientesPrimer = null;
		String[] listaPacientesSegundo = null;
		String[] listaPacientesTercero = null;
		listaPacientesPrimer = getPacientesPorTrimestre(
				listaPrimerTrimestre, 1);
		listaPacientesSegundo = getPacientesPorTrimestre(
				listaSegundoTrimestre, 2);
		listaPacientesTercero = getPacientesPorTrimestre(
				listaTercerTrimestre, 3);
		Intent intent = new Intent(AgregarPaciente.this,
				MenuPacientes.class);
		intent.putExtra("Cedula", String.valueOf(cedula));
		intent.putExtra("PrimerTrimestre", listaPacientesPrimer);
		intent.putExtra("SegundoTrimestre", listaPacientesSegundo);
		intent.putExtra("TercerTrimestre", listaPacientesTercero);
		intent.putExtra("CedulasPrimer", listaCedulaPrimer);
		intent.putExtra("CedulasSegundo", listaCedulaSegundo);
		intent.putExtra("CedulasTercero", listaCedulaTercero);
		intent.putExtra("CedulaDoctor", cedulaDoctor);
		intent.putExtra("Rol","Doctor");
		startActivity(intent);
	}

	public class CallWebServiceTask extends AsyncTask<String, Void, Integer> {

		protected Context applicationContext;
		int resultado = 0;

		@Override
		protected void onPreExecute() {
			dialogo.show();
		}

		protected void onPostExecute(Integer resultado) {
			dialogo.cancel();
			if (resultado == 1) {
				usuarioAlert1.setMessage("El usuario fue agregado con exito.");
				usuarioAlert1.create().show();
			} else {
				usuarioAlert2.setMessage("El usuario no pudo ser agregado.");
				usuarioAlert2.create().show();
			}
		}


		@Override
		protected Integer doInBackground(String... params) {
			String cedula = params[0];
			if (validarUsuarioPorCedula(cedula) == true) {
				resultado = 1;
			} else {
				resultado = -1;
			}
			return resultado;
		}

	}

	public ArrayList<Usuario> getPacientesDoctor(int cedula, int trimestre) {
		ArrayList<Usuario> lista = null;
		ServiciosUsuario userServices = new ServiciosUsuario();
		lista = userServices.obtenerPacientesPorDoctor(cedula, trimestre);
		return lista;
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
				for (int i = 0; i < lista.size(); i++) {
					Usuario usuario = new Usuario();
					usuario = lista.get(i);
					String nombre = usuario.getNombrePaciente() + " "
							+ usuario.getApellidoPaciente();
					listaPacientes[i] = nombre;
					listaCedulaPrimer[i] = String.valueOf(usuario.getCedula());
				}
			}
			break;
		case 2:
			if (lista == null) {
				listaPacientes = null;
			} else {
				listaCedulaSegundo = new String[lista.size()];
				for (int i = 0; i < lista.size(); i++) {
					Usuario usuario = new Usuario();
					usuario = lista.get(i);
					String nombre = usuario.getNombrePaciente() + " "
							+ usuario.getApellidoPaciente();
					listaPacientes[i] = nombre;
					listaCedulaSegundo[i] = String.valueOf(usuario.getCedula());
				}
			}
			break;
		case 3:
			if (lista == null) {
				listaPacientes = null;
			} else {
				listaCedulaTercero = new String[lista.size()];
				for (int i = 0; i < lista.size(); i++) {
					Usuario usuario = new Usuario();
					usuario = lista.get(i);
					String nombre = usuario.getNombrePaciente() + " "
							+ usuario.getApellidoPaciente();
					listaPacientes[i] = nombre;
					listaCedulaTercero[i] = String.valueOf(usuario.getCedula());
				}
			}
			break;
		default:
			break;
		}

		return listaPacientes;
	}

}
