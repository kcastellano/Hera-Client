package modulos;

import java.util.ArrayList;

import dominio.Estudio;
import dominio.ExpedienteUsuario;
import dominio.Usuario;
import edu.tesis.heraproject.R;
import servicios.ServiciosEstudio;
import servicios.ServiciosExpedienteUsuario;
import servicios.ServiciosUsuario;
import vistas.MenuPacientes;
import vistas.VistaPaciente;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class VerUsuario extends Activity {

	private TextView txt;
	private Typeface font;
	private Button editarUsuario;
	private TextView nombreUsuario;
	private TextView cedulaUsuario;
	private TextView correoUsuario;
	private TextView apellidoUsuario;
	private TextView rolUsuario;
	private TextView header;
	private int cedulaUser;
	private Usuario usuario;
	private String[] listaCedulaPrimer = null;
	private String[] listaCedulaSegundo = null;
	private String[] listaCedulaTercero = null;
	private String rol;
	private String cedula;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.ver_perfil);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titulo);

		txt = (TextView) findViewById(R.id.title);
		txt.setText("Hera");
		font = Typeface.createFromAsset(getAssets(), "Chunk.ttf");
		txt.setTypeface(font);
		editarUsuario = (Button) findViewById(R.id.editarPerfil);

		header = (TextView) findViewById(R.id.textHeaderPerfil);
		nombreUsuario = (TextView) findViewById(R.id.nombrePerfil);
		cedulaUsuario = (TextView) findViewById(R.id.cedulaPerfil);
		correoUsuario = (TextView) findViewById(R.id.correoPerfil);
		apellidoUsuario = (TextView) findViewById(R.id.apellidoPerfil);
		rolUsuario = (TextView) findViewById(R.id.rolPerfil);

		Intent intent = getIntent();
		
		rol = intent.getStringExtra("Rol");
		
		if(rol.equalsIgnoreCase("Familiar")){
			cedula = intent.getStringExtra("CedulaFamiliar");
		}
		else{
			cedula = intent.getStringExtra("Cedula");
		}
		usuario = new Usuario();
		usuario = obtenerUsuario(cedula);

		nombreUsuario.setText(usuario.getNombrePaciente());
		cedulaUsuario.setText(String.valueOf(usuario.getCedula()));
		correoUsuario.setText(usuario.getEmail());
		apellidoUsuario.setText(usuario.getApellidoPaciente());
		rolUsuario.setText(rol);

		cedulaUser = usuario.getCedula();
		
		if (usuario.getRol().equalsIgnoreCase("Doctor")) {
			ImageView image = (ImageView) findViewById(R.id.imageView1);
			image.setImageResource(R.drawable.clinic_doctor_medical);
			header.setText("Dr. " + usuario.getNombrePaciente() + " " + usuario.getApellidoPaciente());
		}
		else if (rol.equalsIgnoreCase("Paciente")) {
			ImageView image = (ImageView) findViewById(R.id.imageView1);
			image.setImageResource(R.drawable.paciente);
			header.setText(usuario.getNombrePaciente() + " " + usuario.getApellidoPaciente());
		}
		else if (rol.equalsIgnoreCase("Familiar")) {
			ImageView image = (ImageView) findViewById(R.id.imageView1);
			image.setImageResource(R.drawable.familiar);
			header.setText(usuario.getNombrePaciente() + " " + usuario.getApellidoPaciente());
		}
		Button.OnClickListener editUserOnClickListener = new Button.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(VerUsuario.this, EditarUsuario.class);
				intent.putExtra("Cedula",String.valueOf(cedulaUser));
				intent.putExtra("Rol",rol);
				startActivity(intent);
			}
		};

		editarUsuario.setOnClickListener(editUserOnClickListener);

	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_home, menu);
		return true;

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.home:
			callMenu();
			break;
		}
		return true;
	}
	
	
	
	public void callMenu(){
		String nombre = usuario.getNombrePaciente() + " " + usuario.getApellidoPaciente();
		if (usuario.getRol().equalsIgnoreCase("Doctor")) {
			ArrayList<Usuario> listaPrimerTrimestre = new ArrayList<Usuario>();
			ArrayList<Usuario> listaSegundoTrimestre = new ArrayList<Usuario>();
			ArrayList<Usuario> listaTercerTrimestre = new ArrayList<Usuario>();

			listaPrimerTrimestre = getPacientesDoctor(usuario.getCedula(), 1);
			listaSegundoTrimestre = getPacientesDoctor(usuario.getCedula(), 2);
			listaTercerTrimestre = getPacientesDoctor(usuario.getCedula(), 3);
			String[] listaPacientesPrimer = null;
			String[] listaPacientesSegundo = null;
			String[] listaPacientesTercero = null;
			listaPacientesPrimer = getPacientesPorTrimestre(
					listaPrimerTrimestre, 1);
			listaPacientesSegundo = getPacientesPorTrimestre(
					listaSegundoTrimestre, 2);
			listaPacientesTercero = getPacientesPorTrimestre(
					listaTercerTrimestre, 3);
			Intent intent = new Intent(VerUsuario.this, MenuPacientes.class);
			intent.putExtra("CedulaDoctor",String.valueOf(usuario.getCedula()));
			intent.putExtra("PrimerTrimestre", listaPacientesPrimer);
			intent.putExtra("SegundoTrimestre", listaPacientesSegundo);
			intent.putExtra("TercerTrimestre", listaPacientesTercero);
			intent.putExtra("CedulasPrimer", listaCedulaPrimer);
			intent.putExtra("CedulasSegundo", listaCedulaSegundo);
			intent.putExtra("CedulasTercero", listaCedulaTercero);
			intent.putExtra("Rol","Doctor");
			startActivity(intent);
		}
		else {
			ServiciosExpedienteUsuario servicios = new ServiciosExpedienteUsuario();
			ExpedienteUsuario expediente1 = new ExpedienteUsuario();
			expediente1 = servicios.obtenerExpedientePaciente(String
					.valueOf(usuario.getCedula()));

			if (expediente1.getRol_expediente().equalsIgnoreCase(
					"Paciente")) {
				int trimestre = getUltimoEstudioTrimestre(expediente1
						.getFk_idExpediente());
				Intent intent = new Intent(VerUsuario.this,
						VistaPaciente.class);
				intent.putExtra("Cedula", String.valueOf(usuario.getCedula()));
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
				Intent intent = new Intent(VerUsuario.this,
						VistaPaciente.class);
				String nombrePaciente = paciente.getNombrePaciente() + " " + paciente.getApellidoPaciente();
				intent.putExtra("Cedula", String.valueOf(paciente.getCedula()));
				intent.putExtra("Nombre", nombrePaciente);
				intent.putExtra("Trimestre", String.valueOf(trimestre)
						+ "¡");
				intent.putExtra("Rol","Familiar");
				intent.putExtra("CedulaFamiliar",String.valueOf(usuario.getCedula()));
				startActivity(intent);
			}

		}
	}
	public Usuario obtenerUsuario(String cedula) {
		Usuario usuario1 = new Usuario();
		ServiciosUsuario servicio = new ServiciosUsuario();
		usuario1 = servicio.obtenerUsuarioPorCedula(cedula);

		return usuario1;
	}
	
	public ArrayList<Usuario> getPacientesDoctor(int cedula, int trimestre) {
		ArrayList<Usuario> lista = null;
		ServiciosUsuario userServices = new ServiciosUsuario();
		lista = userServices.obtenerPacientesPorDoctor(cedula, trimestre);
		return lista;
	}
	
	public int getUltimoEstudioTrimestre(int expediente) {
		Estudio estudio = new Estudio();
		ServiciosEstudio servicio = new ServiciosEstudio();
		estudio = servicio.obtenerUltimoEstudioPaciente(expediente);
		return estudio.getTrimestre();

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
