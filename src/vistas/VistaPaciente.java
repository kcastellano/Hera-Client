package vistas;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import modulos.VerUsuario;

import servicios.ServiciosDiagnostico;
import servicios.ServiciosEstudio;
import servicios.ServiciosExpedienteUsuario;
import servicios.ServiciosFactor;
import servicios.ServiciosMedidas;

import configuracion.CustomBaseAdapter;
import dominio.Diagnostico;
import dominio.Estudio;
import dominio.ExpedienteUsuario;
import dominio.Factor;
import dominio.Medida;

import edu.tesis.heraproject.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class VistaPaciente extends Activity {
	private TextView txt;
	private Typeface font;
	private TextView patientTrimester;
	private TextView patientName;
	private String ced;
	private String nombre;
	private String trimestre;
	private String rol;
	private int expedientePaciente;
	private String cedulaFamiliar;
	private String cedulaDoctor;
	private String[] listaPacientes = null;
	private String[] listaEmails = null;
	private Diagnostico diagnostico;
	private Dialog myDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.vista_paciente);
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
		listaPacientes = intent.getStringArrayExtra("Lista");
		listaEmails = intent.getStringArrayExtra("Emails");
		patientName = (TextView) findViewById(R.id.patientName);
		patientName.setText(nombre);

		patientTrimester = (TextView) findViewById(R.id.patientTrimester);
		patientTrimester.setText(trimestre);
		patientTrimester.setTypeface(font);
		ServiciosExpedienteUsuario expediente = new ServiciosExpedienteUsuario();
		ExpedienteUsuario expedienteUsuario = new ExpedienteUsuario();
		expedienteUsuario = expediente.obtenerExpedientePaciente(ced);
		expedientePaciente = expedienteUsuario.getFk_idExpediente();
		int idEstudio = getUltimoEstudioPaciente(expedientePaciente);
		ArrayList<String> listaDiagnostico = new ArrayList<String>();
		ArrayList<String> listaTitulos = new ArrayList<String>();
		if (expedienteUsuario.getCompartir() == 1
				|| rol.equalsIgnoreCase("Doctor")) {
			listaDiagnostico = getDiagnostico(idEstudio);
			listaTitulos = getDiagnosticoTitulos();
		} else {
			listaTitulos = getDiagnosticoTitulos();
			listaDiagnostico = getDiagnostico(0);
		}

		final ListView lv1 = (ListView) findViewById(R.id.ListView01);
		lv1.setAdapter(new CustomBaseAdapter(this, listaDiagnostico,
				listaTitulos));

		if (rol.equalsIgnoreCase("Familiar")) {
			cedulaFamiliar = intent.getStringExtra("CedulaFamiliar");
		}
		if (rol.equalsIgnoreCase("Doctor")) {
			cedulaDoctor = intent.getStringExtra("CedulaDoctor");
		}

		myDialog = new Dialog(this);
		myDialog.setContentView(R.layout.explicacion);
		myDialog.setTitle("M—dulo de Explicaci—n");
		myDialog.setCancelable(true);

		String[] explicacion1 = setExplicacion();

		if (explicacion1 != null) {
			final ListView lista1 = (ListView) myDialog
					.findViewById(R.id.ListView02);
			lista1.setAdapter(new TextAdapter(this, R.layout.filas2,
					R.id.label, explicacion1));
		}
		Button button = (Button) myDialog.findViewById(R.id.botonExplicacion);

		Button.OnClickListener explanationOnClickListener = new Button.OnClickListener() {

			public void onClick(View v) {
				myDialog.dismiss();
			}
		};

		button.setOnClickListener(explanationOnClickListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();

		if (rol.equalsIgnoreCase("Paciente")) {
			inflater.inflate(R.menu.menu_paciente, menu);
		} else if (rol.equalsIgnoreCase("Doctor")) {
			inflater.inflate(R.menu.menu3, menu);
		} else if (rol.equalsIgnoreCase("Familiar")) {
			inflater.inflate(R.menu.menu_familiar, menu);
		}
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.eco:
			Intent intent = new Intent(VistaPaciente.this,
					LineaTiempoPaciente.class);
			intent.putExtra("Cedula", ced);
			intent.putExtra("Nombre", nombre);
			intent.putExtra("Trimestre", trimestre);
			intent.putExtra("Rol", rol);
			intent.putExtra("ListaPacientes", listaPacientes);
			intent.putExtra("ListaEmails", listaEmails);
			intent.putExtra("CedulaDoctor", cedulaDoctor);
			startActivity(intent);
			break;
		case R.id.see_profile:
			Intent intentProfile = new Intent(VistaPaciente.this,
					VerUsuario.class);
			intentProfile.putExtra("Cedula", ced);
			intentProfile.putExtra("Rol", rol);
			intentProfile.putExtra("CedulaFamiliar", cedulaFamiliar);
			startActivity(intentProfile);
			break;

		case R.id.see_familiar:
			Intent intentFamiliar = new Intent(VistaPaciente.this,
					MenuFamiliares.class);
			intentFamiliar.putExtra("Expediente",
					String.valueOf(expedientePaciente));
			startActivity(intentFamiliar);
			break;

		case R.id.info:
			myDialog.show();
			break;
		}

		return true;
	}

	public int getUltimoEstudioPaciente(int expediente) {
		Estudio estudio = new Estudio();
		ServiciosEstudio servicio = new ServiciosEstudio();
		estudio = servicio.obtenerUltimoEstudioPaciente(expediente);
		return estudio.getIdEstudio();

	}

	public ArrayList<String> getDiagnosticoTitulos() {
		ArrayList<String> lista = new ArrayList<String>();
		lista.add("Fecha œltima regla");
		lista.add("Fecha probable de parto");
		lista.add("Semanas de embarazo");
		lista.add("Semanas faltantes");
		ServiciosEstudio servicios = new ServiciosEstudio();
		Estudio est = new Estudio();
		est = servicios.obtenerUltimoEstudioPaciente(expedientePaciente);

		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yy-MM-dd");
		String fpp = formatter.format(est.getFechaEstudio());

		lista.add("Diagnostico al: " + fpp);
		return lista;
	}

	public ArrayList<String> getDiagnostico(int estudio) {
		diagnostico = new Diagnostico();
		ServiciosDiagnostico diagnosticos = new ServiciosDiagnostico();
		diagnostico = diagnosticos.obtenerDiagnosticoPorEstudio(estudio);
		ArrayList<String> lista = new ArrayList<String>();
		ServiciosEstudio servicios = new ServiciosEstudio();
		Estudio est = new Estudio();
		est = servicios.obtenerUltimoEstudioPaciente(expedientePaciente);
		ServiciosExpedienteUsuario serviciosExp = new ServiciosExpedienteUsuario();
		ExpedienteUsuario expediente = new ExpedienteUsuario();
		
		expediente = serviciosExp.obtenerExpedientePaciente(ced);
		if ((diagnostico != null) ) {
			String fur = fechaAproxConcepcion(diagnostico.getEdadGestacional());
			lista.add(fur);

			SimpleDateFormat formatter;
			formatter = new SimpleDateFormat("yy-MM-dd");
			String fpp = formatter.format(diagnostico.getFechaProbableParto());
			lista.add(fpp);

			String edad = String.valueOf(diagnostico.getEdadGestacional());

			lista.add(edad + " semanas");

			Double semanafaltante = 40.0 - diagnostico.getEdadGestacional();
			String semanasFal = String.valueOf(semanafaltante);
			lista.add(semanasFal + " semanas");

			/*
			 * if(rol.equalsIgnoreCase("Familiar") ||
			 * rol.equalsIgnoreCase("Paciente")){
			 * if(!diagnostico.getResultado().equalsIgnoreCase(
			 * "El embarazo esta acorde con la fecha de la ultima regla")){
			 * lista.add(
			 * "Se recomienda a la paciente llevar un mayor seguimiento sobre el desarrollo del embarazo"
			 * ); } } else{
			 */
			lista.add(diagnostico.getResultado());
			// }
		} else {

			lista.add("Sin data");
			lista.add("Sin data");
			lista.add("Sin data");
			lista.add("Sin data");

		}

		return lista;

	}

	public Date fechaProbableDeParto(double GA) {

		int semana = (int) GA;
		int dias = (int) (GA - (int) GA);
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.WEEK_OF_YEAR, -semana);
		cal.add(Calendar.DAY_OF_YEAR, -dias);

		// int mes = (cal.get(Calendar.MONTH) + 1) -3;
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		cal.add(Calendar.WEEK_OF_MONTH, -13);
		int dia = cal.get(Calendar.DATE);
		int mes = (cal.get(Calendar.MONTH) + 1);
		int anio = cal.get(Calendar.YEAR) + 1;

		Calendar fecha = Calendar.getInstance();
		fecha.set(Calendar.YEAR, anio);
		fecha.set(Calendar.MONTH, mes);
		fecha.set(Calendar.DATE, dia);
		System.out.println("Fecha probable de parto: " + dia + "-" + mes + "-"
				+ anio);
		java.sql.Date date = new java.sql.Date(fecha.getTime().getTime());
		return date;
	}

	public String fechaAproxConcepcion(double GA) {

		int semana = (int) GA;
		int dias = (int) (GA - (int) GA);
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.WEEK_OF_YEAR, -semana);
		cal.add(Calendar.DAY_OF_YEAR, -dias);

		String FUR = cal.get(Calendar.DATE) + "-"
				+ (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);

		return FUR;
	}

	public String[] setExplicacion() {
		String[] explicacion = null;
		if (diagnostico != null) {

			ServiciosFactor servicios = new ServiciosFactor();
			ArrayList<Factor> listaFactor = new ArrayList<Factor>();
			int i = 0;
			listaFactor = servicios.obtenerFactorPorTrimestre(String
					.valueOf(trimestre));
			int idEstudio = getUltimoEstudioPaciente(expedientePaciente);

			ServiciosMedidas serviciosMedida = new ServiciosMedidas();
			ArrayList<Medida> listaMedidas = new ArrayList<Medida>();
			listaMedidas = serviciosMedida.obtenerMedidasPorEstudio(String
					.valueOf(idEstudio));

			explicacion = new String[listaFactor.size()];
			for (i = 0; i < listaFactor.size(); i++) {
				String factor = String.valueOf(listaFactor.get(i).getValor());
				String nombreMedida = listaMedidas.get(i).getNombreMedida();
				String medidaSemanas = listaMedidas.get(i)
						.getResultadoSemanas();
				String medidasNumericas = String.valueOf(listaMedidas.get(i)
						.getResultadoNumerico());
				String explicacionValores = null;
				if (!medidaSemanas.equalsIgnoreCase("EMPTY")) {
					explicacionValores = "- La medida " + nombreMedida
							+ " posee el valor " + medidaSemanas
							+ " con una certeza de " + factor + "\n";
					explicacion[i] = explicacionValores;
				} else {
					explicacionValores = "- La medida " + nombreMedida
							+ " posee el valor " + medidasNumericas
							+ " con una certeza de " + factor + "\n";
					explicacion[i] = explicacionValores;
				}
			}

			TextView textoD = (TextView) myDialog
					.findViewById(R.id.diagnosticoLabel);
			textoD.setText("El diagnostico es "
					+ diagnostico.getResultado().toLowerCase()
					+ "con una certeza de "
					+ String.valueOf(diagnostico.getCerteza()));

		}
		return explicacion;
	}

}
