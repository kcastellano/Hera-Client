package vistas;

import java.util.ArrayList;

import servicios.ServiciosExpedienteUsuario;
import servicios.ServiciosUsuario;
import dominio.ExpedienteUsuario;
import dominio.Usuario;
import edu.tesis.heraproject.R;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class LineaTiempoPaciente extends TabActivity implements
		OnTabChangeListener {
	private TextView txt;
	private TextView patient;
	private Typeface font;
	private ProgressDialog dialog;
	private String nombre;
	private String ced;
	private String trimestre;
	private AlertDialog.Builder emailAlert;
	private TabHost tabHost;
	private Usuario usuario;
	private String rol;
	private String[] listaPacientes = null;
	private String[] listaEmails = null;
	private String[] lista = null;
	private String cedulaDoctor;
	private String expediente;
	private ArrayList<Usuario> listaArray;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.linea_tiempo_pacientes);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titulo);

		txt = (TextView) findViewById(R.id.title);
		txt.setText("Hera");
		font = Typeface.createFromAsset(getAssets(), "Chunk.ttf");
		txt.setTypeface(font);

		Intent intent = getIntent();
		ced = intent.getStringExtra("Cedula");
		nombre = intent.getStringExtra("Nombre");
		trimestre = intent.getStringExtra("Trimestre");
		rol = intent.getStringExtra("Rol");

		if (rol.equalsIgnoreCase("Doctor")) {
			cedulaDoctor = intent.getStringExtra("CedulaDoctor");
			listaPacientes = intent.getStringArrayExtra("ListaPacientes");
			listaEmails = intent.getStringArrayExtra("ListaEmails");
		}
		else{
			ServiciosExpedienteUsuario servicios = new ServiciosExpedienteUsuario();
			ExpedienteUsuario expedienteUsuario = new ExpedienteUsuario();
			expedienteUsuario = servicios.obtenerExpedientePaciente(ced);
			expediente = String.valueOf(expedienteUsuario.getFk_idExpediente());
		}
		
		patient = (TextView) findViewById(R.id.patientTimeline);
		patient.setText("Linea de tiempo de " + nombre);

		dialog = new ProgressDialog(this);
		dialog.setIndeterminate(true);
		dialog.setMessage("Espere un momento.");
		dialog.setTitle("Enviando archivo");

		emailAlert = new AlertDialog.Builder(this);
		emailAlert.setTitle("Envio de archivo");
		emailAlert.setPositiveButton("ok", null);
		emailAlert.setCancelable(true);

		usuario = new Usuario();
		usuario = obtenerUsuario(ced);
		tabHost = getTabHost();
		tabHost.setOnTabChangedListener(this);

		TabHost.TabSpec spec;
		Intent intent1;

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent1 = new Intent().setClass(this, TodosEcosonogramas.class);
		intent1.putExtra("Cedula", ced);
		spec = tabHost
				.newTabSpec("Todos")
				.setIndicator("Todos",
						getResources().getDrawable(R.drawable.ic_menu_star))
				.setContent(intent1);
		tabHost.addTab(spec);

		intent1 = new Intent().setClass(this, EcosonogramasPaciente.class);
		intent1.putExtra("Cedula", ced);
		spec = tabHost
				.newTabSpec("Ecosonogramas")
				.setIndicator(
						"Ecosonogramas",
						getResources().getDrawable(
								R.drawable.ic_menu_friendslist))
				.setContent(intent1);
		tabHost.addTab(spec);

		intent1 = new Intent().setClass(this, PronosticoPaciente.class);
		intent1.putExtra("Cedula", ced);
		spec = tabHost
				.newTabSpec("Pronostico")
				.setIndicator(
						"Pron—stico",
						getResources().getDrawable(
								R.drawable.ic_menu_recent_history))
				.setContent(intent1);
		tabHost.addTab(spec);

		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			tabHost.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.drawable.header_background);
		}
		tabHost.getTabWidget().setCurrentTab(1);
		// tabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#35BD45"));
		tabHost.getTabWidget().getChildAt(1)
				.setBackgroundResource(R.drawable.tabs_background);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (rol.equalsIgnoreCase("Paciente") || rol.equalsIgnoreCase("Doctor")) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.menu4, menu);
		}
		return true;

	}

	public void onTabChanged(String tabId) {
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			tabHost.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.drawable.header_background);
		}

		tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
				.setBackgroundResource(R.drawable.tabs_background);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.primerTrimestre:
			Intent intent = new Intent(LineaTiempoPaciente.this,
					PrimerTrimestre.class);
			intent.putExtra("Cedula", ced);
			intent.putExtra("Nombre", nombre);
			intent.putExtra("Trimestre", trimestre);
			intent.putExtra("Rol", rol);
			startActivity(intent);
			break;

		case R.id.segundoTrimestre:
			Intent intentSegundo = new Intent(LineaTiempoPaciente.this,
					SegundoTrimestre.class);
			intentSegundo.putExtra("Cedula", ced);
			intentSegundo.putExtra("Nombre", nombre);
			intentSegundo.putExtra("Trimestre", trimestre);
			intentSegundo.putExtra("Rol", rol);
			startActivity(intentSegundo);
			break;

		case R.id.tercerTrimestre:
			Intent intentTercer = new Intent(LineaTiempoPaciente.this,
					TercerTrimestre.class);
			intentTercer.putExtra("Cedula", ced);
			intentTercer.putExtra("Nombre", nombre);
			intentTercer.putExtra("Trimestre", trimestre);
			intentTercer.putExtra("Rol", rol);
			startActivity(intentTercer);
			break;

		case R.id.add5:
			AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("Envio de expediente");
			alert.setMessage("Ingrese el nombre:");

			if (rol.equalsIgnoreCase("Doctor")) {
				lista = setValoresAutocomplete();
				
			}
			else{
				lista = setListasOrdenadas(expediente);
				
			}

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_dropdown_item_1line, lista);

			final AutoCompleteTextView input = new AutoCompleteTextView(this);
			input.setThreshold(3);
			input.setAdapter(adapter);

			alert.setView(input);

			alert.setPositiveButton("Enviar",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							String value = input.getText().toString();
							String correo = null;
							for (int i = 0; i < lista.length; i++) {
								if (lista[i].equalsIgnoreCase(value)) {
									correo = listaEmails[i];
								}
							}

							if (rol.equalsIgnoreCase("Doctor")) {
								otorgarPermisos();
								sendEmail(correo, cedulaDoctor);
							} else {
								otorgarPermisos();
								sendEmail(correo,
										String.valueOf(usuario.getCedula()));
							}
						}
					});

			alert.show();
			break;
		}

		return true;
	}

	public void sendEmail(String correo, String cedula) {
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		String aEmailList[] = { correo };
		Usuario usuario1 = new Usuario();

		usuario1 = obtenerUsuario(cedula);
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);

		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
				"Compartir ecosonograma");

		emailIntent.setType("plain/text");
		
		

		if (rol.equalsIgnoreCase("Doctor")) {
			emailIntent
					.putExtra(
							android.content.Intent.EXTRA_TEXT,
							usuario1.getNombrePaciente()
									+ " "
									+ usuario1.getApellidoPaciente()
									+ " ha decidido compartir el expediente de la paciente "
									+ usuario.getNombrePaciente()
									+ " "
									+ usuario.getApellidoPaciente()
									+ " con usted. Para revisar la informaci—n por favor"
									+ " ingrese a la aplicaci—n Hera.");
		} else {
			emailIntent
					.putExtra(
							android.content.Intent.EXTRA_TEXT,
							usuario.getNombrePaciente()
									+ " "
									+ usuario.getApellidoPaciente()
									+ " ha decidido compartir su expediente con usted. Para revisar la informaci—n por favor"
									+ " ingrese a la aplicaci—n Hera.");
		}
		startActivity(emailIntent);

	}

	public Usuario obtenerUsuario(String cedula) {
		Usuario usuario1 = new Usuario();
		ServiciosUsuario servicio = new ServiciosUsuario();
		usuario1 = servicio.obtenerUsuarioPorCedula(cedula);

		return usuario1;
	}

	public String[] setValoresAutocomplete() {
		String[] lista = null;
		int size = listaPacientes.length;

		lista = new String[size];

		for (int i = 0; i < listaPacientes.length; i++) {
			String usuario = listaPacientes[i];
			String email = listaEmails[i];

			String valor = usuario + ": " + email;
			lista[i] = valor;
		}

		return lista;
	}

	public String[] setListasOrdenadas(String expediente) {
		ServiciosUsuario servicio = new ServiciosUsuario();
		listaArray = servicio.obtenerFamiliares(expediente);
		String[] lista = null;

		if (listaArray == null) {
			lista = new String[1];
			String registro = "No hay registros de familiares";
			lista[0] = registro;
		} else {
			lista = new String[listaArray.size()];
			listaPacientes = new String[listaArray.size()];
			listaEmails = new String[listaArray.size()];
			for (int i = 0; i < listaArray.size(); i++) {
				listaPacientes[i] = listaArray.get(i).getNombrePaciente() + " "
						+ listaArray.get(i).getApellidoPaciente();
				listaEmails[i] = listaArray.get(i).getEmail();
				
				lista[i] = listaArray.get(i).getNombrePaciente() + " "
				+ listaArray.get(i).getApellidoPaciente() +": " + listaArray.get(i).getEmail();
			}
		}
		return lista;

	}
	
	public boolean agregarExpedienteUsuario(String cedula, String expediente, String rol) {
		boolean validate = false;
		ExpedienteUsuario expedienteUsuario = new ExpedienteUsuario();
		ExpedienteUsuario expedienteUsuarioPaciente = new ExpedienteUsuario();
		ExpedienteUsuario expedienteUsuarioFinal = new ExpedienteUsuario();


		ServiciosExpedienteUsuario serviciosExpedienteUsuario = new ServiciosExpedienteUsuario();
		expedienteUsuarioFinal = serviciosExpedienteUsuario
				.crearExpedienteUsuario(expedienteUsuario);
		
		expedienteUsuarioPaciente.setFk_idExpediente(Integer.valueOf(expediente));
		expedienteUsuarioPaciente.setFk_idUsuario(Integer.valueOf(cedula));
		expedienteUsuarioPaciente.setRol_expediente(rol);
		
		expedienteUsuarioFinal = serviciosExpedienteUsuario
		.crearExpedienteUsuario(expedienteUsuarioPaciente);
		if (expedienteUsuarioFinal != null) {
			validate = true;
		} else {
			validate = false;
		}
		return validate;
	}
	
	public void otorgarPermisos(){
		ServiciosExpedienteUsuario servicios = new ServiciosExpedienteUsuario();
		ExpedienteUsuario expediente = new ExpedienteUsuario();
		
		expediente = servicios.obtenerExpedientePaciente(ced);
		expediente.setCompartir(1);
		
		servicios.actualizarExpedienteUsuario(expediente);
			
	}
}
