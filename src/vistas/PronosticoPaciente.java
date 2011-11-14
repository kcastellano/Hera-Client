package vistas;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import servicios.ServiciosDiagnostico;
import servicios.ServiciosEstudio;
import servicios.ServiciosExpedienteUsuario;
import dominio.Diagnostico;
import dominio.Estudio;
import dominio.ExpedienteUsuario;
import edu.tesis.heraproject.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class PronosticoPaciente extends Activity {
	public final static String ITEM_TITLE = "title";
	public final static String ITEM_CAPTION = "caption";
	private String ced;

	// SectionHeaders
	private final static String[] trimestres = new String[] {
			"Primer Trimestre", "Segundo Trimestre", "Tercer Trimestre" };

	// Section Contents
	private String[] pronosticosPrimerTrimestre = null;
	private String[] pronosticosSegundoTrimestre = null;
	private String[] pronosticosTercerTrimestre = null;
	private Integer[] semanaPrimerTrimestre = null;
	private Integer[] semanaSegundoTrimestre = null;
	private Integer[] semanaTercerTrimestre = null;
	private int fecha1Primer;
	private int fecha1Segundo;
	private int fecha1Tercer;
	private int idEstudioPrimer;
	private int idEstudioSegundo;
	private int idEstudioTercer;

	// MENU - ListView

	// Adapter for ListView Contents
	private SeparadorLista adapter;

	// ListView Contents
	private ListView journalListView;

	public Map<String, ?> createItem(String title, String caption) {
		Map<String, String> item = new HashMap<String, String>();
		item.put(ITEM_TITLE, title);
		item.put(ITEM_CAPTION, caption);
		return item;
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		// Sets the View Layer
		Intent intent = getIntent();
		ced = intent.getStringExtra("Cedula");

		setContentView(R.layout.ecos_paciente);

		// Interactive Tools
		final ArrayAdapter<String> journalEntryAdapter = new ArrayAdapter<String>(
				this, R.layout.item_lista_ecos);

		setListasEstudiosPaciente(ced);

		int size1 = pronosticosPrimerTrimestre.length;
		int size2 = pronosticosSegundoTrimestre.length;
		int size3 = pronosticosTercerTrimestre.length;
		boolean[] valores1 = setValores(size1);
		boolean[] valores2 = setValores(size2);
		boolean[] valores3 = setValores(size3);
		String[] listaNumeros1 = setNumeros(size1);
		String[] listaNumeros2 = setNumeros(size2);
		String[] listaNumeros3 = setNumeros(size3);

		// Create the ListView Adapter
		adapter = new SeparadorLista(this);

		ArrayAdapter listadapter1 = new ImageAdapter(this, R.layout.filas,
				R.id.label, R.id.label1, R.id.image1,
				pronosticosPrimerTrimestre, listaNumeros1, valores1);
		ArrayAdapter listadapter2 = new ImageAdapter(this, R.layout.filas,
				R.id.label, R.id.label1, R.id.image1,
				pronosticosSegundoTrimestre, listaNumeros2, valores2);
		ArrayAdapter listadapter3 = new ImageAdapter(this, R.layout.filas,
				R.id.label, R.id.label1, R.id.image1,
				pronosticosTercerTrimestre, listaNumeros3, valores3);
		// Add Sections

		adapter.addSection(trimestres[0], listadapter1);
		adapter.addSection(trimestres[1], listadapter2);
		adapter.addSection(trimestres[2], listadapter3);

		// Get a reference to the ListView holder
		journalListView = (ListView) this.findViewById(R.id.list_journal);

		// Set the adapter on the ListView holder
		journalListView.setAdapter(adapter);

		// Listen for Click events
		journalListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long duration) {
				String trimestre = (String) adapter.getSection(position);
				String header = (String) adapter.getItem(position);
				Integer item = (Integer) adapter.getPosition(position);
				int fecha2 = 0;
				int fecha1 = 0;
				int id = 0;
				
				if(trimestre.equalsIgnoreCase("Primer Trimestre")){
					fecha2 = semanaPrimerTrimestre[item];
					fecha1 = fecha1Primer;
					id = idEstudioPrimer;
				}
				else if (trimestre.equalsIgnoreCase("Segundo Trimestre")) {
					fecha2 = semanaSegundoTrimestre[item];
					fecha1 = fecha1Segundo;
					id = idEstudioSegundo;
				}
				else if (trimestre.equalsIgnoreCase("Tercer Trimestre")){
					fecha2 = semanaTercerTrimestre[item];
					fecha1 = fecha1Tercer;
					id = idEstudioTercer;
				}

				if ((fecha2 != 0) && (trimestre.equalsIgnoreCase("Primer Trimestre"))) {
					Intent intent = new Intent(PronosticoPaciente.this,
							VistaPacientePPronostico.class);
					intent.putExtra("fecha1",String.valueOf(fecha1));
					intent.putExtra("fecha2", String.valueOf(fecha2));
					intent.putExtra("id",String.valueOf(id));
					intent.putExtra("header", header);
					startActivity(intent);
				}
				else if (fecha2 != 0) {
					Intent intent = new Intent(PronosticoPaciente.this,
							VistaPacienteSTPronostico.class);
					intent.putExtra("fecha1",String.valueOf(fecha1));
					intent.putExtra("fecha2", String.valueOf(fecha2));
					intent.putExtra("id",String.valueOf(id));
					intent.putExtra("header", header);
					startActivity(intent);
				}
			}
		});
	}

	public void setListasEstudiosPaciente(String cedula) {
		ServiciosEstudio servicios = new ServiciosEstudio();
		ServiciosDiagnostico serviciosDiagnostico = new ServiciosDiagnostico();
		ServiciosExpedienteUsuario serviciosExpedienteUsuario = new ServiciosExpedienteUsuario();
		ArrayList<Estudio> listaEstudios = new ArrayList<Estudio>();
		ExpedienteUsuario expediente = new ExpedienteUsuario();
		Estudio estudio = new Estudio();
		expediente = serviciosExpedienteUsuario
				.obtenerExpedientePaciente(cedula);

		ArrayList<Estudio> primerTrimestre = new ArrayList<Estudio>();
		ArrayList<Estudio> segundoTrimestre = new ArrayList<Estudio>();
		ArrayList<Estudio> tercerTrimestre = new ArrayList<Estudio>();

		listaEstudios = servicios.obtenerEstudiosPorPaciente(expediente
				.getFk_idExpediente());

		primerTrimestre = servicios.obtenerPrimerEstudioTrimestre(
				expediente.getFk_idExpediente(), 1);
		segundoTrimestre = servicios.obtenerPrimerEstudioTrimestre(
				expediente.getFk_idExpediente(), 2);
		tercerTrimestre = servicios.obtenerPrimerEstudioTrimestre(
				expediente.getFk_idExpediente(), 3);

		if (primerTrimestre != null) {
			Diagnostico diagnostico = new Diagnostico();
			if (primerTrimestre.size() == 1) {
				diagnostico = serviciosDiagnostico
						.obtenerDiagnosticoPorEstudio(primerTrimestre.get(0)
								.getIdEstudio());
			} else {
				diagnostico = serviciosDiagnostico
						.obtenerDiagnosticoPorEstudio(primerTrimestre.get(1)
								.getIdEstudio());
			}

			if (diagnostico != null) {
				int inicio = (int) diagnostico.getEdadGestacional();

				int semana = (int) inicio;
				Calendar cal = Calendar.getInstance();
				String fpp = null;
				SimpleDateFormat formatter;
				formatter = new SimpleDateFormat("MM/yy/dd");
				if (primerTrimestre.size() == 1) {
					fpp = formatter.format(primerTrimestre.get(0)
							.getFechaEstudio());
					idEstudioPrimer = primerTrimestre.get(0).getIdEstudio();
				} else {
					fpp = formatter.format(primerTrimestre.get(1)
							.getFechaEstudio());
					idEstudioPrimer = primerTrimestre.get(1).getIdEstudio();
				}
				java.util.Date date = new java.util.Date(fpp);

				cal.setTime(date);
				int size = 13 - semana;
				pronosticosPrimerTrimestre = new String[size];
				semanaPrimerTrimestre = new Integer[size];
				int start = 0;
				fecha1Primer = semana;
				String fecha = null;
				String fecha2 = null;
				for (int i = semana; i < 13; i++) {
					fecha = cal.get(Calendar.DATE - 1) + "/"
							+ (cal.get(Calendar.MONTH) + 1) + "/"
							+ cal.get(Calendar.YEAR);
					cal.add(Calendar.DATE, +5);
					fecha2 = cal.get(Calendar.DATE) + "/"
							+ (cal.get(Calendar.MONTH) + 1) + "/"
							+ cal.get(Calendar.YEAR);
					pronosticosPrimerTrimestre[start] = "Semana "
							+ String.valueOf(i) + " " + fecha + " al " + fecha2;
					semanaPrimerTrimestre[start] = i;
					cal.add(Calendar.DATE, +2);
					start++;
					fecha = fecha2;
				}
			} else {
				pronosticosPrimerTrimestre = new String[1];
				semanaPrimerTrimestre = new Integer[1];
				pronosticosPrimerTrimestre[0] = "No hay data";
				semanaPrimerTrimestre[0] = 0;
			}
		} else {
			pronosticosPrimerTrimestre = new String[1];
			semanaPrimerTrimestre = new Integer[1];
			pronosticosPrimerTrimestre[0] = "No hay data";
			semanaPrimerTrimestre[0] = 0;
		}

		if (segundoTrimestre != null) {
			Diagnostico diagnostico = new Diagnostico();

			if (segundoTrimestre.size() == 1) {
				diagnostico = serviciosDiagnostico
						.obtenerDiagnosticoPorEstudio(segundoTrimestre.get(0)
								.getIdEstudio());
			} else {
				diagnostico = serviciosDiagnostico
						.obtenerDiagnosticoPorEstudio(segundoTrimestre.get(1)
								.getIdEstudio());
			}

			if (diagnostico != null) {
				int inicio = (int) diagnostico.getEdadGestacional();

				int semana = (int) inicio;
				Calendar cal = Calendar.getInstance();

				SimpleDateFormat formatter;
				formatter = new SimpleDateFormat("MM/yy/dd");
				String fpp = null;

				if (segundoTrimestre.size() == 1) {
					fpp = formatter.format(segundoTrimestre.get(0)
							.getFechaEstudio());
					idEstudioSegundo = segundoTrimestre.get(0).getIdEstudio();
				} else {
					fpp = formatter.format(segundoTrimestre.get(1)
							.getFechaEstudio());
					idEstudioSegundo = segundoTrimestre.get(1).getIdEstudio();
				}
				java.util.Date date = new java.util.Date(fpp);

				cal.setTime(date);
				int size = 28 - semana;
				fecha1Segundo = semana;
				
				pronosticosSegundoTrimestre = new String[size];
				semanaSegundoTrimestre = new Integer[size];
				String fecha = null;
				String fecha2 = null;
				int start = 0;
				for (int i = semana; i < 28; i++) {
					fecha = cal.get(Calendar.DATE - 1) + "/"
							+ (cal.get(Calendar.MONTH) + 1) + "/"
							+ cal.get(Calendar.YEAR);
					cal.add(Calendar.DATE, +5);
					fecha2 = cal.get(Calendar.DATE) + "/"
							+ (cal.get(Calendar.MONTH) + 1) + "/"
							+ cal.get(Calendar.YEAR);
					pronosticosSegundoTrimestre[start] = "Semana "
							+ String.valueOf(i) + " " + fecha + " al " + fecha2;
					cal.add(Calendar.DATE, +2);
					semanaSegundoTrimestre[start] = i;
					start++;
					fecha = fecha2;
				}
			} else {
				pronosticosSegundoTrimestre = new String[1];
				semanaSegundoTrimestre = new Integer[1];
				pronosticosSegundoTrimestre[0] = "No hay data";
				semanaSegundoTrimestre[0] = 0;
			}
		} else {
			pronosticosSegundoTrimestre = new String[1];
			semanaSegundoTrimestre = new Integer[1];
			pronosticosSegundoTrimestre[0] = "No hay data";
			semanaSegundoTrimestre[0] = 0;
		}

		if (tercerTrimestre != null) {
			Diagnostico diagnostico = new Diagnostico();

			if (tercerTrimestre.size() == 1) {
				diagnostico = serviciosDiagnostico
						.obtenerDiagnosticoPorEstudio(tercerTrimestre.get(0)
								.getIdEstudio());
			} else {
				diagnostico = serviciosDiagnostico
						.obtenerDiagnosticoPorEstudio(tercerTrimestre.get(1)
								.getIdEstudio());
			}

			if (diagnostico != null) {
				int inicio = (int) diagnostico.getEdadGestacional();

				int semana = (int) inicio;
				Calendar cal = Calendar.getInstance();

				SimpleDateFormat formatter;
				formatter = new SimpleDateFormat("MM/yy/dd");
				String fpp = null;
				if (tercerTrimestre.size() == 1) {
					fpp = formatter.format(tercerTrimestre.get(0)
							.getFechaEstudio());
					idEstudioTercer = tercerTrimestre.get(0).getIdEstudio();
				}
				else{
					fpp = formatter.format(tercerTrimestre.get(1)
							.getFechaEstudio());
					idEstudioTercer = tercerTrimestre.get(1).getIdEstudio();
				}
				java.util.Date date = new java.util.Date(fpp);

				cal.setTime(date);
				int size = 40 - semana;
				fecha1Tercer= semana;
				pronosticosTercerTrimestre = new String[size];
				semanaTercerTrimestre = new Integer[size];
				int start = 0;
				String fecha = null;
				String fecha2 = null;
				
				for (int i = semana; i < 40; i++) {
					fecha = cal.get(Calendar.DATE - 1) + "/"
							+ (cal.get(Calendar.MONTH) + 1) + "/"
							+ cal.get(Calendar.YEAR);
					cal.add(Calendar.DATE, +5);
					fecha2 = cal.get(Calendar.DATE) + "/"
							+ (cal.get(Calendar.MONTH) + 1) + "/"
							+ cal.get(Calendar.YEAR);
					pronosticosTercerTrimestre[start] = "Semana "
							+ String.valueOf(i) + " " + fecha + " al " + fecha2;
					cal.add(Calendar.DATE, +2);
					semanaTercerTrimestre[start] = i;
					start++;
					fecha = fecha2;
				}
			} else {
				pronosticosTercerTrimestre = new String[1];
				semanaTercerTrimestre = new Integer[1];
				pronosticosTercerTrimestre[0] = "No hay data";
				semanaTercerTrimestre[0] = 0;
			}
		} else {
			pronosticosTercerTrimestre = new String[1];
			semanaTercerTrimestre = new Integer[1];
			pronosticosTercerTrimestre[0] = "No hay data";
			semanaTercerTrimestre[0] = 0;
		}

	}

	public boolean[] setValores(int cantidad) {
		boolean[] valores = new boolean[cantidad];

		for (int i = 0; i < cantidad; i++) {
			boolean val = true;
			valores[i] = val;
		}
		return valores;
	}

	public String[] setNumeros(int cantidad) {
		String[] valores = new String[cantidad];

		for (int i = 0; i < cantidad; i++) {
			valores[i] = "";
		}
		return valores;
	}

}
